package com.example.courses.controller.utils;

import com.example.courses.model.ResponseMessage;

public class Utils {
    public static ResponseMessage successMessage() {
        return new ResponseMessage(0, "SUCCESS");
    }

    public static ResponseMessage getResponseMessage(int errorCode, String errorMessage) {
        return new ResponseMessage(errorCode, errorMessage);
    }
}
