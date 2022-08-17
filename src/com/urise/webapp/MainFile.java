package com.urise.webapp;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MainFile {
    public static void main(String[] args) throws IOException {
        Path dir_path = Paths.get("./src/com/urise/webapp");
        String[] paths = dir_path.toFile().list();

        Files.walkFileTree(dir_path, new SimpleFileVisitor<Path>() {

            String getDirectoryName(Path dir) {
                int count = dir.getNameCount() - dir_path.getNameCount() + 1;
                count += dir.getFileName().toString().length();
                String text = String.format("%" + count + "s", dir.toFile().isDirectory() ? '[' + dir.getFileName().toString() + ']' : dir.getFileName().toString());
                text = text.replaceAll("[\\s]", "-");
                return text;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println(getDirectoryName(dir));
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(getDirectoryName(file));
                return super.visitFile(file, attrs);
            }
        });
    }
}