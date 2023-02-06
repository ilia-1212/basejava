package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MM/yyyy");
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String toHtml(LocalDate dt) {

        if (dt == null) {
            return "";
        } else if (dt.equals(LocalDate.of(3000, 1, 1))) {
            return "Сейчас";
        }
        return dt.format(FMT);
    }

    public static LocalDate fromHtml(String dt) {
        if (WebUtil.isEmpty(dt) || "Сейчас".equals(dt)) {
            return NOW;
        }

        return LocalDate.parse("01/" + dt,  DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
