package com.yuankunluo.bonmovie.data.tools;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by yuank on 2017-06-22.
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

}
