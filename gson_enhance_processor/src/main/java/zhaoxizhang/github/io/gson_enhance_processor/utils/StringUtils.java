package zhaoxizhang.github.io.gson_enhance_processor.utils;

import javax.annotation.Nonnull;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/26
 */
public class StringUtils {

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static String firstLowerCase(@Nonnull String charSequence) {
        if (isEmpty(charSequence)) {
            throw new IllegalArgumentException("charSequence is empty or null");
        }
        int length;
        length = charSequence.length();
        if (length > 1) {
            return ((String) charSequence).substring(0, 1).toLowerCase() + ((String) charSequence).substring(1);
        } else {
            return ((String) charSequence).substring(0, 1).toLowerCase();
        }
    }

    public static CharSequence firstUpperCase(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            throw new IllegalArgumentException("charSequence is empty or null");
        }
        int length;
        if (charSequence instanceof String) {
            length = charSequence.length();
            if (length > 1) {
                return ((String) charSequence).substring(0, 1).toUpperCase() + ((String) charSequence).substring(1);
            } else {
                return ((String) charSequence).substring(0, 1).toUpperCase();
            }
        } else {
            // TODO
            return charSequence;
        }
    }
}
