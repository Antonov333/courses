package com.example.courses.utils;

import com.example.courses.model.ResponseMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    final private static Set<String> supportedCurrencies = new HashSet<>(Arrays.asList("USD", "EUR"));
    final private static int currencyCourseNumber = 100;

    public static Set<String> getSupportedCurrencies() {
        return supportedCurrencies;
    }

    public static int getCurrencyCourseNumber() {
        return currencyCourseNumber;
    }

    public static ResponseMessage successMessage() {
        return new ResponseMessage(0, "SUCCESS");
    }

    public static ResponseMessage getResponseMessage(int errorCode, String errorMessage) {
        return new ResponseMessage(errorCode, errorMessage);
    }


}
