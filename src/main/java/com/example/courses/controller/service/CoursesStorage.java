package com.example.courses.controller.service;

import com.example.courses.controller.utils.Utils;
import com.example.courses.model.CourseRegistered;
import lombok.Getter;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Getter
@Repository
public class CoursesStorage {

    private HashMap<String, CircularFifoQueue<CourseRegistered>> storage = new HashMap<>();

    CoursesStorage() {
        Utils.getSupportedCurrencies().stream().forEach((c) -> storage.put(c,
                new CircularFifoQueue<>(Utils.getCurrencyCourseNumber())));
    }
}
