package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    private static File[] diveToDir(File file) {
        if (!file.isDirectory()) {
           System.out.println("    file " + file.getName());
            return null;
        } else {
            System.out.println("Directory " + file.getName());
        }

        File[] list = file.listFiles();
        for(File fl : list) {
            diveToDir(fl);
        }
        return list;
    }

    public static void main(String[] args){
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        for (String name : Objects.requireNonNull(list)) {
            System.out.println(name);
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        diveToDir(new File(".\\src"));
    }
}
