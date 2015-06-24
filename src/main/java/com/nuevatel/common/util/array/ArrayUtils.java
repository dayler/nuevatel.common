/**
 * 
 */
package com.nuevatel.common.util.array;

import java.util.Arrays;
import java.util.List;

/**
 * @author asalazar
 *
 */
public class ArrayUtils {

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(T... args) {
        if (args == null) {
            return null;
        }

        List<T>tmp = Arrays.asList(args);
        Object[] arr = tmp.toArray();

        return (T[]) arr;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T>lst) {
        if (lst == null) {
            return null;
        }

        Object[] arr = new Object[lst.size()];
        return (T[]) lst.toArray(arr);
    }


    /**
     * Use {@link Arrays#binarySearch(byte[], byte)}.  
     * 
     * @param array Sorted array
     * @param key Element to find
     * @return Index of <b>key</b>. If not exist returns <b>-((insertion point) -1)</b>
     */
    public static <T> int contains(T[] array, T key) {  
        Arrays.sort(array);  
        return Arrays.binarySearch(array, key);
   }

    /**
     *
     * @param array Array from which get the Element at index position. It cannot be {@code null}.
     * @param atIndex Index to find element.
     * @param <T> Type of array.
     * @return Element from array at index position, if the index exceeded the length of array, it returns <b>null</b>.
     */
    public static <T> T get(T[] array, int atIndex) {
        if (array.length - 1 < atIndex) {
            return null;
        }

        return array[atIndex];
    }
}
