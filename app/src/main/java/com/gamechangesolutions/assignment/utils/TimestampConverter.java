package com.gamechangesolutions.assignment.utils;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * {@link TimestampConverter} is used for room entity to convert String from/to {@link Date} data type
 */
public class TimestampConverter {
    @TypeConverter
    public static Date fromTime(String value) {
        DateFormat df = new SimpleDateFormat(Constants.TIME_STAMP_FORMAT);
        try {
            return df.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    @TypeConverter
    public static String toDateString(Date date) {
        DateFormat df = new SimpleDateFormat(Constants.TIME_STAMP_FORMAT);
        return df.format(date);
    }
}
