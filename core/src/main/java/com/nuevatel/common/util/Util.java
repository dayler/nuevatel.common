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
     * Executes delegate if obj == null
     *
     * @param obj
     * @param delegate
     * @param <T> No primitive type
     */
    public static <T> void ifNull(T obj, Delegate delegate) {
        if (obj == null) {
            delegate.execute();
        }
    }

    /**
     *
     * @param obj
     * @param defaultVal
     * @return <b>defaultVal</b> if obj is not null. <b>null</b> if obj is null.
     */
    public static <T> T ifNotNull(T obj, T defaultVal) {
        return obj == null ? obj : defaultVal;
    }

    /**
     * Executes delegate if obj is not null
     *
     * @param obj
     * @param delegate
     * @param <T> No primitive type
     */
    public static <T> void ifNotNull(T obj, Delegate delegate) {
        if (obj != null) {
            delegate.execute();
        }
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
    
    /**
     * If condition is <code>true</code> execute delegate. In other case do nothing.
     * 
     * @param condition
     * @param delegate
     */
    public static void assertTrue(boolean condition, Delegate delegate) {
        if (condition) {
            delegate.execute();
        }
    }
    
    /**
     * If condition is <code>false</code> execute delegate. In other case do nothing.
     * 
     * @param condition
     * @param delegate
     */
    public static void assertFalse(boolean condition, Delegate delegate) {
        if (!condition) {
            delegate.execute();
        }
    }
}
