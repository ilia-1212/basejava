package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ListStorage;
import com.urise.webapp.storage.Storage;

public class MainTestListStorage {
   final static Storage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) throws CloneNotSupportedException {
        Resume resume1 = new Resume("52");
        Resume resume2 = new Resume("51");
        Resume resume3 = new Resume("512");

        ARRAY_STORAGE.save(resume1);
        ARRAY_STORAGE.save(resume2);
        ARRAY_STORAGE.save(resume3);

        System.out.println("Get resume 1: " + ARRAY_STORAGE.get(resume1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

      //  System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
      //  ARRAY_STORAGE.update(new Resume("dummy"));
        printAll();
        ARRAY_STORAGE.delete(resume1.getUuid());
      //  ARRAY_STORAGE.delete("dummy");
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
