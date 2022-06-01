package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println("getName = " + field.getName());
        System.out.println("get = " + field.get(r));
        field.set(r, "new_uuid");
        // to do  invoke r.toString via refl
        System.out.println("get = " + field.get(r));
    }
}
