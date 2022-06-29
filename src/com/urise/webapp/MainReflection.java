package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, CloneNotSupportedException {
        Resume r = new Resume("NAME");
        Class<? extends Resume> classResume = r.getClass();
        Field field = classResume.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println("get Field Name = " + field.getName());
        System.out.println("get Field Value = " + field.get(r));
        field.set(r, "new_uuid");
        System.out.println("get Field (new)Value = " + field.get(r));

        Method method = Class.forName(classResume.getCanonicalName()).getMethod("toString");
        String result = (String) method.invoke(r, null);
        System.out.println(result);
    }
}
