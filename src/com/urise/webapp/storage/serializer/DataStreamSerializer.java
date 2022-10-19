package com.urise.webapp.storage.serializer;


import com.urise.webapp.model.*;
import com.urise.webapp.util.ConsumerWithExeption;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer  {
    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new ObjectOutputStream(os))) {
            writer.writeUTF(r.getUuid());
            writer.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            writeWithExeption(contacts.entrySet(), writer, entry -> {
                writer.writeUTF(entry.getKey().name());
                writer.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            writeWithExeption(sections.entrySet(), writer, entry -> {
                SectionType sectionKey = entry.getKey();
                writer.writeUTF(sectionKey.name());

                switch (sectionKey) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection textSection = (TextSection) entry.getValue();
                        writer.writeUTF(textSection.getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = (ListSection) entry.getValue();
                        writeWithExeption(listSection.getItems(), writer, item -> {
                            writer.writeUTF(item);
                        });

                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganizationSection organizationSection = (OrganizationSection) entry.getValue();

                        writeWithExeption(organizationSection.getOrganizations(), writer, org -> {
                            writer.writeUTF(org.getHomePage().getName());
                            writeStrNan(org.getHomePage().getUrl(), writer);

                            writeWithExeption(org.getPositions(), writer, pos -> {
                                writer.writeUTF(pos.getTitle());
                                writeStrNan(pos.getDescription(), writer);
                                writer.writeUTF(pos.getStartDate().toString()) ;
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
            int contactsSize = reader.readInt();

            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(reader.readUTF()), reader.readUTF());
            }

            int sectionsSize = reader.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                String sectionStr = reader.readUTF();

                switch (SectionType.valueOf(sectionStr)) {
                    case  PERSONAL,OBJECTIVE -> resume.addSection(SectionType.valueOf(sectionStr), new TextSection(reader.readUTF()));
                    case  ACHIEVEMENT, QUALIFICATIONS -> {
                        int itemsSize = reader.readInt();
                        List<String> items = new ArrayList<>();
                        for(int j = 0;  j < itemsSize; j++) {
                            items.add(reader.readUTF());
                        }
                        resume.addSection(SectionType.valueOf(sectionStr), new ListSection(items));
                    }
                    case  EXPERIENCE, EDUCATION -> {
                        int orgsSize = reader.readInt();
                        List<Organization> organizations = new ArrayList<>();
                        for(int j = 0;  j < orgsSize; j++) {
                            String homePageName = reader.readUTF();
                            String homePageURL = readStrNan(reader);
                            int positionSize = reader.readInt();
                            List<Organization.Position> positions = new ArrayList<>();

                            for(int k = 0; k < positionSize; k++) {
                                String positionTitle = reader.readUTF();
                                String positionDescription = readStrNan(reader);
                                LocalDate positionStartDate = LocalDate.parse(reader.readUTF());
                                LocalDate positionEndDate  = LocalDate.parse(reader.readUTF());

                                positions.add(new Organization.Position(positionStartDate, positionEndDate, positionTitle, positionDescription));
                            }
                            organizations.add(new Organization( new Link(homePageName, homePageURL), positions));
                        }
                        resume.addSection(SectionType.valueOf(sectionStr), new OrganizationSection(organizations));
                    }
                }
            }
            return resume;
        }
    }
    private static void writeStrNan(String str, DataOutputStream dos ) throws IOException {
        dos.writeUTF((!(str == null)) ? str : "");
    }

    private static String readStrNan(DataInputStream dis ) throws IOException {
        String str = dis.readUTF();
        if (str.length() == 0) {
            str = null;
       }
        return str;
    }

    private static <T> void writeWithExeption(Collection<T> collection, DataOutputStream dos, ConsumerWithExeption<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.accept( t );
        }
    }
}