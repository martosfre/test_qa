package com.java.eshop.eshop.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static Date getInitialDate(Long initialDate){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(new Date(initialDate).toInstant(), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        Instant initialInstant = startOfDay.atZone(ZoneId.systemDefault()).toInstant();
        return new Date(initialInstant.toEpochMilli());
    }

    public static Date getFinishDate(Long finishDate){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(new Date(finishDate).toInstant(), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        Instant endInstant = endOfDay.atZone(ZoneId.systemDefault()).toInstant();
        return new Date(endInstant.toEpochMilli());
    }

}
