package com.example.courses.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Registered currency rate model
 */
@Data
@Schema(description = "Сущность курса валюты")
public class CourseRegistered {
    private LocalDateTime regTime;
    private String currencyId;
    private float currencyVal;
}
