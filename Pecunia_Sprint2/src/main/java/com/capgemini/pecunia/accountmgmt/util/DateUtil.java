package com.capgemini.pecunia.accountmgmt.util;

import com.capgemini.pecunia.accountmgmt.exceptions.IncorrectDateException;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date toDate(String pattern, String dateText) {
        DateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse(dateText);
            return date;
        } catch (Exception e) {
            throw new IncorrectDateException("incorrect date=" + dateText + " pattern=" + pattern);
        }

    }

    public static String toString(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        String dateText = format.format(date);
        return dateText;
    }
}
