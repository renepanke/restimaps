package io.github.renepanke.restimaps.lang;

public class Strings {

    public static boolean isBlank(final String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isNotBlank(final String string) {
        return !isBlank(string);
    }
}
