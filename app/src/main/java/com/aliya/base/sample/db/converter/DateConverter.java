package com.aliya.base.sample.db.converter;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Room 指定的类型转换器
 *
 * @author a_liYa
 * @date 2019/3/6 下午4:45.
 * @see TypeConverter
 */
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
