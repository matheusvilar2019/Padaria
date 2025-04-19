package Util;

public class Validador {
    public static boolean isInteger(String str) {
        if (str == null || str.isBlank()) return false;

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
