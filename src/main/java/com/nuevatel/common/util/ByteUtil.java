/**
 * 
 */
package com.nuevatel.common.util;

/**
 * @author asalazar
 *
 */
public final class ByteUtil {

    private ByteUtil() {
        // To prevent instatiation.
    }

    public static Byte tryParse(String rawValue) {
        if (StringUtils.isBlank(rawValue)) {
            // No Parseable
            return null;
        }

        try {
            return Byte.parseByte(rawValue);
        } catch (Throwable ex) {
            return null;
        }
    }
}
