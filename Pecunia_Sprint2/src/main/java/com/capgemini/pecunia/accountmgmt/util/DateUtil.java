package com.capgemini.pecunia.accountmgmt.util;

import com.capgemini.pecunia.accountmgmt.exceptions.IncorrectDateException;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String PATTERN="yyyy-MM-dd";

    public static Date toDate(String pattern, String dateText) {
        DateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse(dateText);
            return date;
        } catch (Exception e) {
            throw new IncorrectDateException("incorrect date=" + dateText + " pattern=" + pattern);
        }

    }

    public static Date toDate(String dateText) {
       return toDate(PATTERN,dateText);

    }

    public static String toString(Date date) {
        DateFormat format = new SimpleDateFormat(PATTERN);
        String dateText = format.format(date);
        return dateText;
    }

    public static String toString(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        String dateText = format.format(date);
        return dateText;
    }
}
