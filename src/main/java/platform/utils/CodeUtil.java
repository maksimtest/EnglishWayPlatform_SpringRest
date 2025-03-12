package platform.utils;

import java.util.UUID;

public class CodeUtil {

    public static String getCode(boolean isActive) {
        char firstLetter = (char) ('a' + (int) (Math.random() * 20));
        return firstLetter +
               (isActive ? "r" : "p") +
               UUID.randomUUID().toString();
    }

    public static boolean isRegistrationCode(String code) {
        return code != null && code.length() > 2 && code.charAt(1) == 'r';
    }
}
