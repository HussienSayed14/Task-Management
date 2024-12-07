package com.vodafone.Task_Manager.util;

import java.sql.Date;
import java.sql.Timestamp;

public class DateService {

    public static Date getCurrentDate(){
        return new Date(System.currentTimeMillis());
    }

    public static Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }
}
