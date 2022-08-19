package com.urise.webapp;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainPathFile {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:\\workspace\\basejava\\.gitignore");
        FileSystem fs =  path.getFileSystem();

        System.out.println("fs toString = " + fs.toString());
        System.out.println("isAbsolute = " + path.isAbsolute());
        System.out.println("getFileName = " + path.getFileName());
        System.out.println("toAbsolutePath = " + path.toAbsolutePath().toString());
        System.out.println("getRoot = " + path.getRoot());
        System.out.println("getParent = " + path.getParent());
        System.out.println("getNameCount = " + path.getNameCount());
        System.out.println("getName = " + path.getName(0));
        System.out.println("subpath = " + path.subpath(0, 1));
        System.out.println("path toString = " + path.toString());
        System.out.println("path getNameCount= " + path.getNameCount());

        Path realPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
        System.out.println("path toRealPath = " + realPath.toString());

        String originalPath = "D:\\workspace\\basejava\\src\\com\\urise\\webapp\\filestorage";
        Path path1 = Paths.get(originalPath);
        System.out.println("path1 = " + path1);
        Path path2 = path1.normalize();
        System.out.println("path2 = " + path2);
        Path path3 = path1.relativize(path2);
        System.out.println("path3 = " + path3);
        Path path4 = path1.resolve(".gitignore");
        System.out.println(path4);

    }
}
