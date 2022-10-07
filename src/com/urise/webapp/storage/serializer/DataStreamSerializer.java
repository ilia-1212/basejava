package com.urise.webapp.storage.serializer;


import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer  {
    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new ObjectOutputStream(os))) {
            writer.writeUTF(r.getUuid());
            writer.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            writer.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                writer.writeUTF(entry.getKey().name());
                writer.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            writer.writeInt(sections.size());

            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                writer.writeUTF(entry.getKey().name());

                switch (entry.getKey() ) {
                    case OBJECTIVE, PERSONAL -> {
                        TextSection textSection = (TextSection) entry.getValue();
                        writer.writeUTF(textSection.getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = (ListSection) entry.getValue();
                        writer.writeInt(listSection.getItems().size());
                        for (String item: listSection.getItems()) {
                            writer.writeUTF(item);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
                        writer.writeInt(organizationSection.getOrganizations().size());
                        for (Organization org: organizationSection.getOrganizations()) {
                            writer.writeUTF(org.getHomePage().getName());
                            writeStrNan(org.getHomePage().getUrl(), writer);

                            writer.writeInt(org.getPositions().size());
                            for(Organization.Position position : org.getPositions()) {
                                writer.writeUTF(position.getTitle());
                                writeStrNan(position.getDescription(), writer);
                                writer.writeUTF(position.getStartDate().toString()) ;
                                writer.writeUTF(position.getEndDate().toString());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream reader = new DataInputStream(new ObjectInputStream(is))) {
            Resume resume;

            resume = new Resume(reader.readUTF(), reader.readUTF());
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
                    case  EXPERIENCE, EDUCATION-> {
                        int orgsSize = reader.readInt();
                        String homePageName;
                        String homePageURL;
                        LocalDate positionStartDate;
                        LocalDate positionEndDate;
                        String positionTitle;
                        String positionDescription;
                        int positionSize;
                        List<Organization> organizations = new ArrayList<>();
                        for(int j = 0;  j < orgsSize; j++) {
                            homePageName = reader.readUTF();
                            homePageURL = readStrNan(reader);
                            positionSize = reader.readInt();
                            List<Organization.Position> positions = new ArrayList<>();

                            for(int k = 0; k < positionSize; k++) {
                                positionTitle = reader.readUTF();
                                positionDescription = readStrNan(reader);
                                positionStartDate = LocalDate.parse(reader.readUTF());
                                positionEndDate  = LocalDate.parse(reader.readUTF());

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
        if (!(str == null)) {
           dos.writeUTF(str);
       } else {
            dos.writeUTF("");
        }
    }

    private static String readStrNan(DataInputStream dis ) throws IOException {
        String str = dis.readUTF();
        if (str.length() == 0) {
            str = null;
       }
        return str;
    }
}