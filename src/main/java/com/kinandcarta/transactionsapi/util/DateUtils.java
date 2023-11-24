package com.kinandcarta.transactionsapi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private DateUtils() {
    }

    public static String formatEpochDateAsString(long date, String pattern) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(LocalDate.ofEpochDay(date));
    }
}