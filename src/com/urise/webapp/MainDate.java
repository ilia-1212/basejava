package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainDate {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis() - date.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        System.out.println(calendar.getTime());

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        System.out.println(ld + ":" + lt + "; " + ldt);

        SimpleDateFormat sdf  = new SimpleDateFormat("YY-YY-dd");
        System.out.println(sdf.format(date));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YY_MM_dd");
        System.out.println(dtf.format(ldt));


    }
}
