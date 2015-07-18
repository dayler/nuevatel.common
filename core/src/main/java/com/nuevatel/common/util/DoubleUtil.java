package com.nuevatel.common.util;

/**
 * Contains common operation for handle Doubles.
 *
 * @author clvelarde
 */
public class DoubleUtil {
    /**
     * Parse a String to its Double representation. If the operation not succeeded returns null.
     *
     * @param rawValue The String to parse.
     * @return The Double representation of the String. If the operation not succeeded returns null.
     */
    public static Double tryParse(String rawValue, Double defaultValue) {
        if (StringUtils.isBlank(rawValue)) {
            // No Parseable.
            return defaultValue;
        }

        try {
            // Try to parse.
            return Double.parseDouble(rawValue);
        } catch (NumberFormatException ex) {
            // No Parseable.
            return defaultValue;
        }
    }

    public static Double tryParse(String rawValue) {
        return tryParse(rawValue, null);
    }
}
