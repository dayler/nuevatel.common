package com.nuevatel.common.util.date;

import com.nuevatel.common.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.TimeZone;

/**
 * Parse and give format for the dates.
 *
 * @author asalazar
 */
public enum DateFormatter {

    /**
     * Date formatter for 'yyyy-MM-dd'.
     */
    SHORT("yyyy-MM-dd"),

    /**
     * Date formatetr for 'yyyy-MM-dd HH:mm'
     */
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),

    /**
     * Date formatter for 'yyyy-MM-dd HH:mm:ss'.
     */
    LONG("yyyy-MM-dd HH:mm:ss"),

    /**
     * Date formatter for 'yyyy-MM-dd HH:mm:ss' in UTC.
     */
    LONG_UTC("yyyy-MM-dd HH:mm:ss", "UTC"),

    CUSTOM() {

        /**
         * {@inheritDoc}
         */
        @Override
        public String format(Date date) {
            return StringUtils.EMPTY;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Date parse(String strDate) {
            return null;
        }
    },

    /**
     * Date formatter for all available formats, defined in this enumerable.
     */
    ALL() {
        @Override
        public Date parse(String strDate) {
            // Define patterns.
            EnumSet<DateFormatter> allDateFormatters = EnumSet.allOf(DateFormatter.class);
            Date result = null;

            for (DateFormatter dateFormatter : allDateFormatters) {
                if (!StringUtils.isBlank(dateFormatter.pattern)
                        && (result = dateFormatter.parse(strDate)) != null) {
                    break;
                }
            }

            return result;
        }
    };

    /**
     * Date formatter, provide the strategy to parse or format Dates.
     */
    private SimpleDateFormat formatter;

    private String pattern;

    private DateFormatter() {
        // No op
    }

    /**
     * Datefromatter constructor. Initialize the formatter with its pattern.
     *
     * @param pattern Date time pattern.
     */
    private DateFormatter(String pattern) {
        formatter = new SimpleDateFormat(pattern);
        this.pattern = pattern;
    }

    /**
     * 
     * @return Patter of formatter date.
     */
    public String pattern() {
        return pattern;
    }

    /**
     * Dateformatter constructor with pattern and timezone.
     *
     * @param pattern  Date time pattern.
     * @param timeZone Time zone string.
     */
    private DateFormatter(String pattern, String timeZone) {
        this(pattern);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    /**
     * Format date time in its string representation. If the date is null returns empty string.
     *
     * @param date Date time to format.
     * @return String representation of the date time. Return 'empty string ' if the date time is null.
     */
    public String format(Date date) {
        if (date != null) {
            return formatter.format(date);
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * @param date Date to format.
     * @param pattern Date time pattern.
     * 
     * @return Date on its string representation.
     */
    public String format(Date date, String pattern) {
        if (date != null && StringUtils.isNotBlank(pattern)) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        }

        return StringUtils.EMPTY;
    }

    /**
     * Get date time from its string representation.
     *
     * @param strDate String date representation to parse.
     * @return Date time from its string representation. Returns null if the string cannot be parsed.
     */
    public Date parse(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        Date result = null;

        try {
            result = formatter.parse(strDate);
        } catch (ParseException ex) {
            // No op
        }

        return result;
    }

    /**
     * 
     * @param strDate Date to format.
     * @param pattern Date time pattern.
     * 
     * @return Date time from its string representation. Returns <b>null</b> if the string cannot 
     * be parsed.
     */
    public Date parse(String strDate, String pattern) {
        if (StringUtils.isNotBlank(strDate) && StringUtils.isNotBlank(pattern)) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);

            try {
                return formatter.parse(strDate);
            } catch (ParseException e) {
                // No op.
            }
        }

        return null;
    }
}
