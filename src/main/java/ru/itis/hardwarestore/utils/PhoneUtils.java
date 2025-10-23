package ru.itis.hardwarestore.utils;

public class PhoneUtils {
    public static String normalizePhone(String phone) {
        if (phone == null) {
            return null;
        }

        String digits = phone.replaceAll("[^0-9]", "");
        if (digits.length() == 10 && digits.startsWith("9")) {
            return "+7" + digits;
        } else if (digits.length() == 11) {
            if (digits.startsWith("8") && digits.charAt(1) == '9') {
                return "+7" + digits.substring(1);
            } else if (digits.startsWith("7") && digits.charAt(1) == '9') {
                return "+7" + digits.substring(1);
            }
        } else if (digits.length() == 12 && digits.startsWith("7") && digits.charAt(1) == '9') {
            return "+7" + digits.substring(1);
        }

        return null;
    }
}
