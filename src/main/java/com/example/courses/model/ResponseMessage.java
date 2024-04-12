package com.example.courses.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage {
    int errCode;
    String errMsg;
}
