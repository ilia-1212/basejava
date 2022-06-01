package com.urise.webapp;

import com.urise.webapp.exception.ExistStorageException;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

public class MainString {
    public static void main(String[] args) {
        String[] str_arr = new String[]{"1", "2", "3", "4", "5"};
        // String res = "";
        StringBuilder sb1 = new StringBuilder();
        for (String str : str_arr) {
            //res += str + " ";
            sb1.append(str).append("-");
        }
        System.out.println(sb1.toString());

        String str1 = "abc";
        String str3 = "c";
        String str2 = ("ab" + str3).intern();
        System.out.println(str1 == str2);
        System.out.println(str1.equals(str2));


        SortedMap<String, Charset> charsetsMap = Charset.availableCharsets();
        System.out.println("Charsets available: " + charsetsMap.size());
        for (String name : charsetsMap.keySet()) {
            Charset charset = charsetsMap.get(name);
            StringBuffer sb = new StringBuffer();
            sb.append(name);
            sb.append(" (");
            for (Iterator<String> aliases = charset.aliases().iterator(); aliases.hasNext(); ) {
                sb.append(aliases.next());
                if (aliases.hasNext())
                    sb.append(",");
            }
            sb.append(")");
            System.out.println(sb.toString());
        }

        try {
            method();
        } catch (ExistStorageException e) {
            e.getStackTrace();
            System.out.println("err " + e.getMessage());
        }
    }

    private static void method() {
        System.out.println("run method");
        throw new ExistStorageException("ddd");
    }
}
