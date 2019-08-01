
package com.aihulk.tech.decision.component.mvc.data;


import com.google.common.base.Strings;

final class CastUtil {
    static String castString(Object property) {
        return castString(property, "");
    }

    private static String castString(Object property, String defaultValue) {
        return property != null ? String.valueOf(property) : defaultValue;
    }

    static double castDouble(Object property) {
        return castDouble(property, 0);
    }

    private static double castDouble(Object property, double defaultValue) {
        double value = defaultValue;
        if (property != null) {
            String string = castString(property);
            if (!Strings.isNullOrEmpty(string)) {
                try {
                    value = Double.parseDouble(string);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    static long castLong(Object property) {
        return castLong(property, 0);
    }

    private static long castLong(Object property, long defaultValue) {
        long value = defaultValue;
        if (property != null) {
            String string = castString(property);
            if (!Strings.isNullOrEmpty(string)) {
                try {
                    value = Long.parseLong(string);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    static int castInt(Object property) {
        return castInt(property, 0);
    }

    private static int castInt(Object property, int defaultValue) {
        int value = defaultValue;
        if (property != null) {
            String string = castString(property);
            if (!Strings.isNullOrEmpty(string)) {
                try {
                    value = Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    static boolean castBoolean(Object property) {
        return castBoolean(property, false);
    }

    private static boolean castBoolean(Object property, boolean defaultValue) {
        boolean value = defaultValue;
        if (property != null)
            value = Boolean.parseBoolean(castString(property));
        return value;
    }
}
