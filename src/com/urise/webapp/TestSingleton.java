package com.urise.webapp;

import com.urise.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    private TestSingleton() {
    }

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }


    public static void main(String[] args) {
        enum Singleton {
            INSTANCE
        }

        TestSingleton.getInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.ordinal());

        for (SectionType st : SectionType.values()) {
            System.out.println(st.getTitle());
        }
    }
}