package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.MapFullNameStorage;
import com.urise.webapp.storage.Storage;

public class MainTestListStorage {
   final static Storage ARRAY_STORAGE = new MapFullNameStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume("52", "Илья");
        Resume resume2 = new Resume("51", "Дима");
        Resume resume3 = new Resume("50" ,"Дима");

        ARRAY_STORAGE.save(resume1);
        ARRAY_STORAGE.save(resume2);
        ARRAY_STORAGE.save(resume3);

        System.out.println("Get resume 1: " + ARRAY_STORAGE.get(resume1.getFullName()));
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
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
