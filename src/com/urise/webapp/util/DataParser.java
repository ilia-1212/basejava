package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataParser  {
    public static Resume read(DataInputStream reader) throws IOException {
        Resume resume;
        String uuid = readStr(reader);
        String fullName = readStr(reader);

        resume = new Resume(uuid, fullName);
        int contactsSize = reader.readInt();

        for (int i = 0; i < contactsSize; i++) {
            resume.addContact(ContactType.valueOf(reader.readUTF()), reader.readUTF());
        }

        int sectionsSize = reader.readInt();
        String sectiontClassStr;
        String sectionStr;
        for (int i = 0; i < sectionsSize; i++) {
            sectiontClassStr = readStr(reader);
            sectionStr = readStr(reader);

            if (sectiontClassStr.equals(TextSection.class.getName())) {
                resume.addSection(SectionType.valueOf(sectionStr), new TextSection(reader.readUTF()));
            } else if (sectiontClassStr.equals(ListSection.class.getName())) {
                int itemsSize = reader.readInt();
                List<String> items = new ArrayList<>();
                for(int j = 0;  j < itemsSize; j++) {
                    items.add(reader.readUTF());
                }
                resume.addSection(SectionType.valueOf(sectionStr), new ListSection(items));
            } else if (sectiontClassStr.equals(OrganizationSection.class.getName())) {
                int orgsSize = reader.readInt();
                String homePageName;
                String homePageURL;
                LocalDate positionStartDate;
                LocalDate positionEndDate;
                String positionTitle;
                String positionDescription;
                int positionSize;
                List<Organization> orgs = new ArrayList<>();
                for(int j = 0;  j < orgsSize; j++) {
                    homePageName = readStr(reader);
                    homePageURL = readStr(reader);
                    positionSize = reader.readInt();
                    Organization.Position position;
                    List<Organization.Position> positions = new ArrayList<>();

                    for(int k = 0; k < positionSize; k++) {
                        positionTitle = readStr(reader);
                        positionDescription = readStr(reader);
                        positionStartDate = LocalDate.parse(reader.readUTF());
                        positionEndDate  = LocalDate.parse(reader.readUTF());

                        positions.add(new Organization.Position(positionStartDate, positionEndDate, positionTitle, positionDescription));
                    }
                    orgs.add(new Organization( new Link(homePageName, homePageURL), positions));
                }
                resume.addSection(SectionType.valueOf(sectionStr), new OrganizationSection(orgs));
            }
      }

        return resume;
    }

    public static void write(Resume object, DataOutputStream writer) throws IOException {
        writer.writeUTF(object.getUuid());
        writer.writeUTF(object.getFullName());

        Map<ContactType, String> contacts = object.getContacts();
        writer.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            writer.writeUTF(entry.getKey().name());
            writer.writeUTF(entry.getValue());
        }

        Map<SectionType, Section> sections = object.getSections();
        writer.writeInt(sections.size());

        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            writer.writeUTF(entry.getValue().getClass().getName());
            writer.writeUTF(entry.getKey().name());

            if (entry.getValue() instanceof TextSection) {
                TextSection textSection = (TextSection) entry.getValue();
                writer.writeUTF(textSection.getContent());
            } else if (entry.getValue() instanceof ListSection) {
                ListSection listSection = (ListSection) entry.getValue();
                writer.writeInt(listSection.getItems().size());
                for (String item: listSection.getItems()) {
                    writeStr(item, writer);
                }
            } else if (entry.getValue() instanceof OrganizationSection organizationSection) {
                writer.writeInt(organizationSection.getOrganizations().size());
                for (Organization org: organizationSection.getOrganizations()) {
                    writeStr(org.getHomePage().getName(), writer);
                    writeStr(org.getHomePage().getUrl(), writer);

                    writer.writeInt(org.getPositions().size());
                    for(Organization.Position position : org.getPositions()) {
                        writeStr(position.getTitle(), writer);
                        writeStr(position.getDescription(), writer);
                        writeStr(position.getStartDate().toString(), writer) ;
                        writeStr(position.getEndDate().toString(), writer);
                    }
                }
            }
        }
    }

    private static void writeStr(String str, DataOutputStream dos ) throws IOException {
        if (!(str == null || str.length() == 0)) {
            dos.writeUTF(str);
        } else {
            dos.writeUTF("");
        }
    }

    private static String readStr(DataInputStream dis ) throws IOException {
        String str = dis.readUTF();
        if (str == null || str.length() == 0 || ("").equals(str)) {
            str = null;
        }
        return str;
    }
}
