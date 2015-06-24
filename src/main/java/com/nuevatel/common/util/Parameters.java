package com.nuevatel.common.util;

/**
 * Helper class to execute validations on parameters value.
 *
 * @author asalazar
 */
public final class Parameters {

    /**
     * Throws {@link IllegalArgumentException} if "param" is null
     *
     * @param param     The parameter to evaluate.
     * @param paramName The parameter name.
     * @throws IllegalArgumentException if "param" is null.
     */
    public static void checkNull(Object param, String paramName) {
        if (param == null) {
            throw new IllegalArgumentException(String.format("The parameter: %s is null", paramName));
        }
    }

    /**
     * Throws {@link IllegalArgumentException} if "param" is null, empty string or blank string.
     *
     * @param param     The parameter to evaluate.
     * @param paramName The parameter name.
     * @throws IllegalArgumentException if "param" is null, empty string or blank string.
     */
    public static void checkBlankString(String param, String paramName) {
        if (StringUtils.isBlank(param)) {
            throw new IllegalArgumentException(String.format("The parameter: %s is empty or null", paramName));
        }
    }

    /**
     * Throws {@link IllegalArgumentException} if "param" is negative number.
     *
     * @param param     Parameter to evaluate.
     * @param paramName The parameter name.
     * @throws IllegalArgumentException if 'param' is a negative number;
     */
    public static void checkIntNegative(int param, String paramName) {
        if (param < 0) {
            throw new IllegalArgumentException(String.format("Parameter: %s in negative.", paramName));
        }
    }

    /**
     * Throws {@link IllegalArgumentException} if "param" is negative number.
     *
     * @param param     Parameter to evaluate.
     * @param paramName The parameter name.
     * @throws IllegalArgumentException if 'param' is a negative number;
     */
    public static void checkLongNegative(long param, String paramName) {
        if (param < 0) {
            throw new IllegalArgumentException(String.format("Parameter: %s in negative.", paramName));
        }
    }

    /**
     * Throws {@link IllegalArgumentException} if 'expr' is false.
     *
     * @param expr      Result of the expression to check;
     * @param predicate Predicate to describe the exception, it is used only if the 'expr' is false.
     * @throws IllegalArgumentException if 'expr' is false.
     */
    public static void checkAssert(boolean expr, String predicate) {
        if (!expr) {
            throw new IllegalArgumentException(predicate);
        }
    }
}
