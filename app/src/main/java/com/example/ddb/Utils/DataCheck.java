package com.example.ddb.Utils;

public class DataCheck {
    public static String normalizePhoneNumber(String phoneNumber) throws WrongPhoneNumber {
        if (!phoneNumber.matches("(((05)|(\\+?(9725)))[0-9]{8})"))
            throw new WrongPhoneNumber();

        phoneNumber = phoneNumber.replaceFirst("(05)", "+9725");
        return phoneNumber;
    }

    public static class WrongPhoneNumber extends Exception {
    }
}
