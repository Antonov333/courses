package com.example.courses.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Registered currency rate model
 */
@Data
public class CourseRegistered {
    private LocalDateTime registrationTime;
    private String currencyId;
    private float currencyVal;
}
