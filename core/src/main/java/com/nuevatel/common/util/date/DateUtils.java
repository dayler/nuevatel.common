package com.nuevatel.common.util.date;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author asalazar
 */
public final class DateUtils {

    private static final int CONVERSION_FACTOR_TO_DAYS = 86400000;

    public static long getNowTime() {
        return new Date().getTime();
    }

    /**
     * Creates new instance of java.util.Date based on timestamp.
     *
     * @param timestamp
     * @return
     */
    public static Date timestampSqlToDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return new Date(timestamp.getTime());
    }

    /**
     * 
     * @param date Date to convert to days.
     * @return date on its days representation.
     */
    public static long toDays(Date date) {
        return date.getTime() / CONVERSION_FACTOR_TO_DAYS;
    }
}
