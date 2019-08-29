package com.cycloneboy.springcloud.travelnote.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Create by  sl on 2019-08-03 10:48
 */
public class TimeUtils {

    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_TIME_FORMAT_NO_SECOND = "yy-MM-dd HH:mm";


//    public static DateTime parseDateTime(String time, String format) {
//        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
//        DateTime dateTime = fmt.parseDateTime(time);
//        return dateTime;
//    }


    public static LocalDateTime parseDateTime(String time, String format) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(time, fmt);
        return dateTime;
    }

}
