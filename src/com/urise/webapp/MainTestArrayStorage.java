package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.FileStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static Storage ARRAY_STORAGE = new FileStorage(new File("./src/com/urise/webapp/filestorage"));

    public static void main(String[] args) throws IOException {
        Resume r1 = new Resume("uuid4", "Сентяков Илья А");
        Resume r2 = new Resume("uuid1", "Петров Дима А");
        Resume r3 = new Resume("uuid5", "Петров Андрей В");
        Resume r4 = new Resume("uuid4", "Летов Дима В");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        printAll();

        //System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getFullName()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STORAGE.update(r4);

       // System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r2.getUuid());
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
