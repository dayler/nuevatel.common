package com.nuevatel.common.util;

/**
 * Contains common operation for handle Integers.
 *
 * @author asalazar
 */
public final class IntegerUtil {

    private IntegerUtil() {
        // No op.
    }

    /**
     * Parse a String to its Integer representation. If the operation not succeeded returns null.
     *
     * @param rawValue The String to parse.
     * @return The Integer representation of the String. If the operation not succeeded returns null.
     */
    public static Integer tryParse(String rawValue) {
        if (StringUtils.isBlank(rawValue)) {
            // No Parseable.
            return null;
        }

        try {
            // Try to parse.
            return Integer.parseInt(rawValue);
        } catch (Throwable ex) {
            // No Parseable.
            return null;
        }
    }

    /**
     * @param rawInt Integer to convert into String.
     * 
     * @return String representation of rawInt.
     */
    public static String toString(Integer rawInt) {
        if (rawInt == null) {
            return null;
        }

        return Integer.toString(rawInt);
    }

    /**
     *
     * @param val Integer value to convert
     * @return Byte representation for the Integer value, <code>null</code> if val is <code>null</code>.
     */
    public static Byte toByteValue(Integer val) {
        return toByteValue(val, null);
    }

    /**
     *
     * @param val Integer value to convert.
     * @param defaultVal Default val to return if val is <code>null</code>.
     * @return Byte representation for the Integer value, <code>defaultVal</code> if val is <code>null</code>.
     */
    public static Byte toByteValue(Integer val, Byte defaultVal) {
        if (val == null) {
            return defaultVal;
        }
        return val.byteValue();
    }
}
