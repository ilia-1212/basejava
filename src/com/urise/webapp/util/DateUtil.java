package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String toHtml(LocalDate dt) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("LLLL yyyy");
        if (dt == null) {
            return "";
        } else if (dt.equals(LocalDate.of(3000, 1, 1))) {
            return "Сейчас";
        }
        return dt.format(fmt);
    }
}
