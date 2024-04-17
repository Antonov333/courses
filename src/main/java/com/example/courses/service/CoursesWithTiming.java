package com.example.courses.service;

import com.example.courses.model.CourseRegistered;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Currency courses are kept in Map, there key is time of registration and value is registered course entity
 * It is intended to keep courses of one currency only, i.e. all courseRegistered.getCurrencyId() must be same
 */
@Data
@Schema(description = "Хранилище курсов валют на основе HashMap, где ключом является время регистрации курса валюты, с которым связывается сущность курса валюты")
public class CoursesWithTiming {
    /**
     * Map (LocalDateTime, {@link CourseRegistered})
     */
    private final HashMap<LocalDateTime, CourseRegistered> courseVsTime = new HashMap<>();
}
