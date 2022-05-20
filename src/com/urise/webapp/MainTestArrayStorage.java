package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

import java.util.Arrays;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid7");
        Resume r3 = new Resume();
        r3.setUuid("uuid5");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        printAll();

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        System.out.println("update: uuid5 - uuid9");
        ARRAY_STORAGE.update("uuid5","uuid9");
        printAll();

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println(Arrays.toString(ARRAY_STORAGE.getAll()));
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
