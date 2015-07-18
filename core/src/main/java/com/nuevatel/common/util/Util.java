package com.nuevatel.common.util;

/**
 * Common methods for general purpose.
 * 
 * @author asalazar
 */
public class Util {

    /**
     * 
     * @param obj
     * @param defaultVal
     * @return <b>defaultVal</b> if obj is null. <b>obj</b> if obj is not null.
     */
    public static <T> T ifNull(T obj, T defaultVal) {
        return obj == null ? defaultVal : obj;
    }

    /**
     * Safe cast, return null if the object cannot be casteable.
     * 
     * @param clazz Class to cast
     * @param obj Object to cast
     * @return Object casted to clazz. <b>null</b> if obj is not clazz.
     */
    public static <T> T castAs(Class<T> clazz, Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            // Try cast.
            if (clazz.isInstance(obj)) {
                return clazz.cast(obj);
            }

        } catch (ClassCastException ex) {
            // No op.
        }

        return null;
    }

    /**
     * 
     * @return Return current execution runtime path. <b>This method only works for Java SE.</b>
     */
    public static String runtimePath() {
        return System.getProperty("user.dir");
    }
}
