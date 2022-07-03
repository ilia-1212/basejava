package com.urise.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private EnumMap<ContactType, String> contacts;
    private EnumMap<SectionType, Section> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName must not be null");
    }

    public Resume(String uuid, String fullName, EnumMap<ContactType, String> contacts, EnumMap<SectionType, Section> sections) {
        this(uuid, fullName);
        this.contacts = contacts;
        this.sections = sections;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setContacts(EnumMap<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public void setSections(EnumMap<SectionType, Section> sections) {
        this.sections = sections;
    }

    public void show() {
        System.out.println(fullName + "\n");

        for (Map.Entry contact : this.contacts.entrySet()) {
            if (contact.getKey().toString().equals("TEL") ||
                    contact.getKey().toString().equals("SKYPE") ||
                    contact.getKey().toString().equals("EMAIL")
            ) {
                System.out.println(((ContactType) contact.getKey()).getTitle() + ":" + contact.getValue());
            }
        }

        for (Map.Entry contact : this.contacts.entrySet()) {
            if (!contact.getKey().toString().equals("TEL") ||
                    !contact.getKey().toString().equals("SKYPE") ||
                    !contact.getKey().toString().equals("EMAIL")
            ) {
                System.out.println(((ContactType) contact.getKey()).getTitle() + " url = " + contact.getValue());
            }
        }
        System.out.print("\n");

        for (Map.Entry section : this.sections.entrySet()) {
                System.out.println(((SectionType) section.getKey()).getTitle());
                System.out.println(section.getValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}
