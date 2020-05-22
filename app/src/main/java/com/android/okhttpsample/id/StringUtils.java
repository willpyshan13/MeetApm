

package com.android.okhttpsample.id;

import java.util.regex.Pattern;

public class StringUtils {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;

    public StringUtils() {
    }

    public static boolean isNull(String str) {
        return str == null || str.length() == 0 || str.equals("null");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean contains(String str, String... searchStr) {
        if (str != null && searchStr != null) {
            String[] var2 = searchStr;
            int var3 = searchStr.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String s = var2[var4];
                if (str.contains(s)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * @deprecated
     */
    public static String clean(String str) {
        return str == null ? "" : str.trim();
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static int indexOfDifference(String str1, String str2) {
        if (str1 == str2) {
            return -1;
        } else if (str1 != null && str2 != null) {
            int i;
            for (i = 0; i < str1.length() && i < str2.length() && str1.charAt(i) == str2.charAt(i); ++i) {
                ;
            }

            return i >= str2.length() && i >= str1.length() ? -1 : i;
        } else {
            return 0;
        }
    }

    public static int indexOfDifference(String[] strs) {
        if (strs != null && strs.length > 1) {
            boolean anyStringNull = false;
            boolean allStringsNull = true;
            int arrayLen = strs.length;
            int shortestStrLen = 2147483647;
            int longestStrLen = 0;

            int firstDiff;
            for (firstDiff = 0; firstDiff < arrayLen; ++firstDiff) {
                if (strs[firstDiff] == null) {
                    anyStringNull = true;
                    shortestStrLen = 0;
                } else {
                    allStringsNull = false;
                    shortestStrLen = Math.min(strs[firstDiff].length(), shortestStrLen);
                    longestStrLen = Math.max(strs[firstDiff].length(), longestStrLen);
                }
            }

            if (allStringsNull || longestStrLen == 0 && !anyStringNull) {
                return -1;
            } else if (shortestStrLen == 0) {
                return 0;
            } else {
                firstDiff = -1;

                for (int stringPos = 0; stringPos < shortestStrLen; ++stringPos) {
                    char comparisonChar = strs[0].charAt(stringPos);

                    for (int arrayPos = 1; arrayPos < arrayLen; ++arrayPos) {
                        if (strs[arrayPos].charAt(stringPos) != comparisonChar) {
                            firstDiff = stringPos;
                            break;
                        }
                    }

                    if (firstDiff != -1) {
                        break;
                    }
                }

                return firstDiff == -1 && shortestStrLen != longestStrLen ? shortestStrLen : firstDiff;
            }
        } else {
            return -1;
        }
    }

    public static String getCommonPrefix(String[] strs) {
        if (strs != null && strs.length != 0) {
            int smallestIndexOfDiff = indexOfDifference(strs);
            return smallestIndexOfDiff == -1 ? (strs[0] == null ? "" : strs[0]) : (smallestIndexOfDiff == 0 ? "" : strs[0]
                    .substring(0, smallestIndexOfDiff));
        } else {
            return "";
        }
    }

    public static int getLevenshteinDistance(String s, String t) {
        if (s != null && t != null) {
            int n = s.length();
            int m = t.length();
            if (n == 0) {
                return m;
            } else if (m == 0) {
                return n;
            } else {
                if (n > m) {
                    String tmp = s;
                    s = t;
                    t = tmp;
                    n = m;
                    m = tmp.length();
                }

                int[] p = new int[n + 1];
                int[] d = new int[n + 1];

                int i;
                for (i = 0; i <= n; p[i] = i++) {
                    ;
                }

                for (int j = 1; j <= m; ++j) {
                    char t_j = t.charAt(j - 1);
                    d[0] = j;

                    for (i = 1; i <= n; ++i) {
                        int cost = s.charAt(i - 1) == t_j ? 0 : 1;
                        d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
                    }

                    int[] _d = p;
                    p = d;
                    d = _d;
                }

                return p[n];
            }
        } else {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    public static boolean startsWith(String str, String prefix) {
        return startsWith(str, prefix, false);
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return startsWith(str, prefix, true);
    }

    private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
        return str != null && prefix != null ? (prefix.length() > str.length() ? false : str.regionMatches(ignoreCase, 0, prefix, 0, prefix
                .length())) : str == null && prefix == null;
    }

    public static boolean endsWith(String str, String suffix) {
        return endsWith(str, suffix, false);
    }

    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return endsWith(str, suffix, true);
    }

    private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
        if (str != null && suffix != null) {
            if (suffix.length() > str.length()) {
                return false;
            } else {
                int strOffset = str.length() - suffix.length();
                return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
            }
        } else {
            return str == null && suffix == null;
        }
    }

    public static String filterUnMeanZero(String editText) {
        String newText = "";
        int pointIndex = editText.indexOf(".");
        if (pointIndex > 0) {
            String s1 = editText.substring(0, pointIndex);
            s1 = fillZeroBeforePoint(s1, true);
            String s2 = editText.substring(pointIndex);
            if (isNotBlank(s2) && s2.length() > 1) {
                s2 = editText.substring(pointIndex + 1);
                if (Double.parseDouble(s2) == 0.0D) {
                    s2 = "";
                } else if (s2.length() > 1) {
                    s2 = "." + fillZeroAfterPoint(s2);
                } else {
                    s2 = editText.substring(pointIndex);
                }
            }

            if (s1.equals("0") && s2.equals("")) {
                newText = "";
            } else {
                newText = s1 + s2;
            }
        } else {
            newText = fillZeroBeforePoint(editText, false);
        }

        return newText;
    }

    private static String fillZeroBeforePoint(String value, boolean hasPoint) {
        String newText = value;
        if (value.length() == 1) {
            if (value.equals("0")) {
                if (hasPoint) {
                    value = "0";
                } else {
                    value = "";
                }
            }

            newText = value;
        } else {
            char[] chars = value.toCharArray();

            for (int index = 0; index < chars.length - 1 && chars[index] == 48; ++index) {
                newText = value.substring(index + 1, value.length());
            }
        }

        return newText;
    }

    private static String fillZeroAfterPoint(String value) {
        String newText = value;
        char[] chars = value.toCharArray();

        for (int index = chars.length - 1; index > 0 && chars[index] == 48; --index) {
            newText = value.substring(0, index);
        }

        return newText;
    }

    public static int getStringCount(String s) {
        if (isEmpty(s)) {
            return 0;
        } else {
            int length = 0;

            for (int i = 0; i < s.length(); ++i) {
                int ascii = Character.codePointAt(s, i);
                if (ascii >= 0 && ascii <= 255) {
                    ++length;
                } else {
                    length += 2;
                }
            }

            return length;
        }
    }

    public static int getInt(String value) {
        try {
            return value != null && !value.equals("") && !value.equals("null") ? Integer.valueOf(value)
                                                                                        .intValue() : 0;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 0;
        }
    }

    public static float getFloat(String value) {
        try {
            return Float.valueOf(value).floatValue();
        } catch (Exception var2) {
            var2.printStackTrace();
            return 0.0F;
        }
    }

    public static String getString(int value) {
        try {
            return String.valueOf(value);
        } catch (Exception var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static String getString(float value) {
        try {
            return String.valueOf(value);
        } catch (Exception var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static boolean isNumber(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }

    public static boolean hasNumAndLetter(String str) {
        boolean bl = false;
        if (isEmpty(str)) {
            return bl;
        } else {
            if (Pattern.compile("(?i)[a-z]").matcher(str).find() && Pattern.compile("(?i)[0-9]")
                                                                           .matcher(str)
                                                                           .find()) {
                bl = true;
            }

            return bl;
        }
    }

    public static boolean hasSpace(String str) {
        boolean has = false;
        if (!isEmpty(str)) {
            has = str.contains(" ");
        }

        return has;
    }

    public static long getLong(String value) {
        try {
            return Long.valueOf(value).longValue();
        } catch (Exception var2) {
            var2.printStackTrace();
            return 0L;
        }
    }

    public static Boolean getBoolean(String value) {
        try {
            return Boolean.valueOf(value);
        } catch (Exception var2) {
            var2.printStackTrace();
            return Boolean.valueOf(false);
        }
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();

        for (int i = 0; i < c.length; ++i) {
            if (c[i] == 12288) {
                c[i] = 32;
            } else if (c[i] > '\uff00' && c[i] < '｟') {
                c[i] -= 'ﻠ';
            }
        }

        return new String(c);
    }

    public static String buildString(Object... str) {
        int size = str.length;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; ++i) {
            if (str[i] != null) {
                builder.append(String.valueOf(str[i]));
            }
        }

        return builder.toString();
    }

    public static boolean equals(String address, String s) {
        return s.equals(address);
    }
}
