package com.urise.webapp.storage.serializer;


import com.urise.webapp.model.*;
import com.urise.webapp.util.ConsumerWithExeption;
import com.urise.webapp.util.VoidWithExeption;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new ObjectOutputStream(os))) {
            writer.writeUTF(r.getUuid());
            writer.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            writeWithExeption(contacts.entrySet(), writer, contactEntry -> {
                writer.writeUTF(contactEntry.getKey().name());
                writer.writeUTF(contactEntry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            writeWithExeption(sections.entrySet(), writer, sectionEntry -> {
                SectionType sectionKey = sectionEntry.getKey();
                writer.writeUTF(sectionKey.name());

                switch (sectionKey) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection textSection = (TextSection) sectionEntry.getValue();
                        writer.writeUTF(textSection.getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = (ListSection) sectionEntry.getValue();

                        writeWithExeption(getCollection(listSection::getItems), writer, item -> {
                            writer.writeUTF((String) item);
                        });

                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganizationSection organizationSection = (OrganizationSection) sectionEntry.getValue();

                        writeWithExeption(getCollection(organizationSection::getOrganizations), writer, orgObj -> {
                            Organization org = (Organization) orgObj;
                            writer.writeUTF(org.getHomePage().getName());
                            writeStrNan(org.getHomePage().getUrl(), writer);

                            writeWithExeption(getCollection(org::getPositions), writer, posObj -> {
                                Organization.Position pos = (Organization.Position) posObj;
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

            readWithExeption(reader, () -> {
                resume.addContact(ContactType.valueOf(reader.readUTF()), reader.readUTF());
            });

            readWithExeption(reader, () -> {
                String sectionStr = reader.readUTF();

                switch (SectionType.valueOf(sectionStr)) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(SectionType.valueOf(sectionStr), new TextSection(reader.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int itemsSize = reader.readInt();
//                        List<String> items = new ArrayList<>();
//                        for (int j = 0; j < itemsSize; j++) {
//                            items.add(reader.readUTF());
//                        }

                        List<String> items11 = (List) getCollection( () -> {
                            List<String> items1 = new ArrayList<>();
                            for (int j = 0; j < itemsSize; j++) {
                                try {
                                    items1.add(reader.readUTF());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            return items1;
                        });

                        resume.addSection(SectionType.valueOf(sectionStr), new ListSection(items11));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = new ArrayList<>();

                        readWithExeption(reader, () -> {
                            String homePageName = reader.readUTF();
                            String homePageURL = readStrNan(reader);

                            List<Organization.Position> positions = new ArrayList<>();

                            readWithExeption(reader, () -> {
                                String positionTitle = reader.readUTF();
                                String positionDescription = readStrNan(reader);
                                LocalDate positionStartDate = LocalDate.parse(reader.readUTF());
                                LocalDate positionEndDate = LocalDate.parse(reader.readUTF());

                                positions.add(new Organization.Position(positionStartDate, positionEndDate, positionTitle, positionDescription));
                            });

                            organizations.add(new Organization(new Link(homePageName, homePageURL), positions));
                        });
                        resume.addSection(SectionType.valueOf(sectionStr), new OrganizationSection(organizations));
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

    private static <T> void writeWithExeption(Collection<T> collection, DataOutputStream dos, ConsumerWithExeption<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.accept(t);
        }
    }

    private static void readWithExeption(DataInputStream dis, VoidWithExeption consumer) throws IOException {
        int collectionSize = dis.readInt();
        for (int i = 0; i < collectionSize; i++) {
            consumer.accept();
        }
    }

    private static <T> Collection<T> getCollection(Supplier sup) throws IOException {
        return (Collection<T>) sup.get();
    }

    private static <T,R> Collection<R> setCollection(Function<T,R> sup) throws IOException {
        T t = null;
        return (Collection<R>) sup.apply(t);
    }
}