/**
 * 
 */
package com.nuevatel.common.util;

import java.util.regex.Pattern;

/**
 * Evaluate wilcard expression.
 * 
 * @author asalazar
 *
 */
public final class WildcardUtils {

    private WildcardUtils() {
        // No op. Prevent instantiation.
    }

    /**
     * 
     * @param wildcard Wildcard expression.
     * @return Equivalent regexp string for the wildcard expression.
     */
    public static String wildcardToRegex(String wildcard) {
        StringBuilder buffer = new StringBuilder(wildcard.length());
        buffer.append('^');

        for (int i = 0; i < wildcard.length(); i++ ) {
            char singleChar = wildcard.charAt(i);
            String regexToAppend = WildcardCharHelper.buildRegEx(singleChar);
            buffer.append(regexToAppend);
        }

        buffer.append('$');

        return buffer.toString();
    }
    
    /**
     * 
     * @param wildcard Pattern.
     * @param toEvaluate String to evaluate.
     * 
     * @return True if toEvaluate match with wildcard
     */
    public static boolean matches(String wildcard, String toEvaluate) {
        if (StringUtils.isBlank(toEvaluate)) {
            return false;
        }

        String regex = WildcardUtils.wildcardToRegex(wildcard);

        return Pattern.matches(regex, toEvaluate);
    }

    /**
     * Convert a single wildcard to regex equivalent.
     */
    private enum WildcardCharHelper {
        DOT_ASTERISK(new char[]{'*'}) {
            @Override
            protected String regex(char wildcardChar) {
                return ".*";
            }
        },

        DOT(new char[]{'?'}) {
            @Override
            protected String regex(char wildcardChar) {
                return ".*";
            }
        },

        BACKSLASH(new char[]{'(', ')', '[', ']', '$', '^', '.', '{', '}', '|', '\\'}) {
            // "\\"
            @Override
            protected String regex(char wildcardChar) {
                return "\\" + new String(new char[]{wildcardChar});
            }
        },

        NONE(new char[]{});

        private char[] wildcardChar;

        private WildcardCharHelper(char[] wildcardChar) {
            this.wildcardChar = wildcardChar;
        }

        protected String regex(char wildcardChar) {
            return new String(new char[] {wildcardChar});
        }

        public static String buildRegEx(char wildcardChar) {
            WildcardCharHelper helper = valueOf(wildcardChar);

            return helper.regex(wildcardChar);
        }

        public static WildcardCharHelper valueOf(char wildcardChar) {
            for (WildcardCharHelper helper : values()) {
                char[] chars = helper.wildcardChar;

                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == wildcardChar) {
                        return helper;
                    }
                }
            }

            return NONE;
        }
    }
}
