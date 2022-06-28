package com.urise.webapp.model;

import java.util.Random;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume /*implements Comparable<Resume>*/ {

    // Unique identifier
    private String uuid;
    private String fullName;

    public Resume() {
        this(UUID.randomUUID().toString(), null);
        Random rnd = new Random();

        String randomString = rnd.ints(97, 123).
                limit(10).collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append ).toString();
        this.fullName = randomString;
    }
    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid + ":" + fullName;
    }

//    @Override
//    public int compareTo(Resume o) {
//        return uuid.compareTo(o.uuid);
//    }
}
