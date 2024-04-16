package com.example.courses.service;

import com.example.courses.model.CourseRegistered;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Currency courses are kept in Map, there key is time of registration and value is registered course entity
 * It is intended to keep courses of one currency only, i.e. all courseRegistered.getCurrencyId() must be same
 */
@Data
public class CoursesWithTiming {
    private final HashMap<LocalDateTime, CourseRegistered> courseVsTime = new HashMap<>();
}
