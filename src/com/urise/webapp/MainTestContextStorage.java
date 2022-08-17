package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.ContextStorage;

import java.io.IOException;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestContextStorage {
    final static ContextStorage contextStorage = new ContextStorage();
    public static void main(String[] args) throws IOException {

        contextStorage.setContextStorage(new ArrayStorage());

        Resume r1 = new Resume("uuid4", "Сентяков Илья А");
        Resume r2 = new Resume("uuid1", "Петров Дима А");
        Resume r3 = new Resume("uuid5", "Петров Андрей В");
        Resume r4 = new Resume("uuid4", "Летов Дима В");

        contextStorage.save(r1);
        contextStorage.save(r2);
        contextStorage.save(r3);
        printAll();

        System.out.println("Get r1: " + contextStorage.get(r1.getFullName()));
        System.out.println("Size: " + contextStorage.size());

        contextStorage.update(r4);

        //System.out.println("Get dummy: " + contextStorage.get("dummy"));

        printAll();
        contextStorage.delete(r1.getFullName());
        printAll();
        contextStorage.clear();
        printAll();

        System.out.println("Size: " + contextStorage.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : contextStorage.getAllSorted()) {
            System.out.println(r);
        }
    }
}
