package com.example.courses.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Результате выполнения операции с кодом завершения и сообщением")
public class ResponseMessage {
    int errCode;
    String errMsg;
}
