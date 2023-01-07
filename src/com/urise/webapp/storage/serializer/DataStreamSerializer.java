package com.urise.webapp.storage.serializer;


import com.urise.webapp.model.*;
import com.urise.webapp.util.ConsumerWithExeption;
import com.urise.webapp.util.SupplierWithExeption;
import com.urise.webapp.util.VoidWithExeption;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new ObjectOutputStream(os))) {
            writer.writeUTF(r.getUuid());
            writer.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(contacts.entrySet(), writer, contactEntry -> {
                writer.writeUTF(contactEntry.getKey().name());
                writer.writeUTF(contactEntry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            writeWithException(sections.entrySet(), writer, sectionEntry -> {
                SectionType sectionKey = sectionEntry.getKey();
                writer.writeUTF(sectionKey.name());

                switch (sectionKey) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection textSection = (TextSection) sectionEntry.getValue();
                        writer.writeUTF(textSection.getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = (ListSection) sectionEntry.getValue();
                        writeWithException(listSection.getItems(), writer, item -> writer.writeUTF(item));

                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganizationSection organizationSection = (OrganizationSection) sectionEntry.getValue();

                        writeWithException(organizationSection.getOrganizations(), writer, org -> {
                            writer.writeUTF(org.getHomePage().getName());
                            writeStrNan(org.getHomePage().getUrl(), writer);

                            writeWithException(org.getPositions(), writer, pos -> {
                                writer.writeUTF(pos.getTitle());
                                writeStrNan(pos.getDescription(), writer);
                                writer.writeUTF(pos.getStartDate().toString());
                                writer.writeUTF(pos.getEndDate().toString());
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream reader = new DataInputStream(new ObjectInputStream(is))) {
            Resume resume = new Resume(reader.readUTF(), reader.readUTF());

            readWithException(reader, () -> {
                resume.addContact(ContactType.valueOf(reader.readUTF()), reader.readUTF());
            });

            readWithException(reader, () -> {
                String sectionStr = reader.readUTF();

                switch (SectionType.valueOf(sectionStr)) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(SectionType.valueOf(sectionStr), new TextSection(reader.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        resume.addSection(SectionType.valueOf(sectionStr), new ListSection(readListWithException(reader, () -> reader.readUTF())));
                    }
                    case EXPERIENCE, EDUCATION -> {

                        resume.addSection(SectionType.valueOf(sectionStr),
                                new OrganizationSection(
                                        readListWithException(reader, () -> {
                                            String homePageName = reader.readUTF();
                                            String homePageURL = readStrNan(reader);

                                            return new Organization(new Link(homePageName, homePageURL),
                                                    readListWithException(reader, () -> {
                                                        String positionTitle = reader.readUTF();
                                                        String positionDescription = readStrNan(reader);
                                                        LocalDate positionStartDate = LocalDate.parse(reader.readUTF());
                                                        LocalDate positionEndDate = LocalDate.parse(reader.readUTF());

                                                        return new Organization.Position(positionStartDate, positionEndDate, positionTitle, positionDescription);
                                                    })
                                            );
                                        })
                                )
                        );
                    }
                }
            });
            return resume;
        }
    }

    private static void writeStrNan(String str, DataOutputStream dos) throws IOException {
        dos.writeUTF((!(str == null)) ? str : "");
    }

    private static String readStrNan(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        if (str.length() == 0) {
            str = null;
        }
        return str;
    }

    private static <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerWithExeption<T> consumerWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumerWriter.accept(t);
        }
    }

    private static void readWithException(DataInputStream dis, VoidWithExeption voidReader) throws IOException {
        int itemsSize = dis.readInt();
        for (int i = 0; i < itemsSize; i++) {
            voidReader.accept();
        }
    }

    private static <T> List<T> readListWithException(DataInputStream dis, SupplierWithExeption<T> supplierReader) throws IOException {
        int itemsSize = dis.readInt();

        List<T> items = new ArrayList<>();
        for (int i = 0; i < itemsSize; i++) {
            T item = supplierReader.get();
            items.add(item);
        }
        return items;
    }
}