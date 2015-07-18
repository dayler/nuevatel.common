package com.nuevatel.common.util;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Contains common operations to handle Strings.
 *
 * @author asalazar
 */
public class StringUtils {

    public static final String END_LINE = System.getProperty("line.separator");

    /**
     * Define empty string.
     */
    public static final String EMPTY = "";

    /**
     * Indicates is the string is empty or null
     *
     * @param str The string to evaluate.
     * @return True if the string is empty, false for null.
     */
    public static boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Indicates if the string is not empty, null or blank
     *
     * @param str The string to evaluate
     * @return <b>false</b> if the string is empty, <b>true</b> for null or whitespace
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Indicates if the string is empty, null or blank
     *
     * @param str The string to evaluate
     * @return true if the string is empty, false for null or whitespace
     */
    public static boolean isBlank(String str) {
        if (isEmptyOrNull(str)) {
            return true;
        }

        // Goes through whole string
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (!Character.isWhitespace(chr) && chr != 0) {
                // If at least one of its chars is not whitespace
                return false;
            }
        }

        return true;
    }

    /**
     * Compare two strings in safe way. <br/>
     * 
     * When is comparing two strings i.e. str1 and str2 using str1.equals(str2) and str1 is
     * <b>null</b> NullPointer exception is throws, this method fix that problem, and you can
     * compare those strings either some of one is <b>null</b>. This method is more expensive that
     * single <b>equals</b>.
     * 
     * @param str1 First string.
     * @param str2 Second string.
     * @return <b>true</b> if both strings are the same.
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }

        if (str2 == null) {
            return false;
        }

        if (str1 == null) {
            return false;
        }

        return str1.equals(str2);
    }

    /**
     * Join elements into string in which each element is separated by <b>separator</b>.
     * 
     * @param elements Array of elements to join.
     * @param separator Separator element.
     * 
     * @return String in which each element of <b>elements</b> is separated by <b>separator</b>.
     */
    public static <T> String join(T[] elements, String separator) {
        return join(Arrays.asList(elements), separator);
    }

    /**
     * 
     * @param elements Elements to join
     * @param separator Separator f each item, of the string.
     * 
     * @return joined string with all string representation of <b>elements</b>, separated by 
     * <b>separator</b>
     */
    public static <T> String join(Iterable<T> elements, String separator) {
        Parameters.checkNull(elements, "elements");
        Iterator<T> it = elements.iterator();
        StringBuffer buff = new StringBuffer();
        boolean hasNext = it.hasNext();

        while (hasNext) {
            buff.append(it.next());
            hasNext = it.hasNext();

            // The last element will not append the separator.
            if (hasNext) {
                buff.append(separator);
            }
        }

        return buff.toString();
    }
}
