package com.example.courses.service;

import com.example.courses.model.CourseRegistered;
import com.example.courses.utils.Utils;
import lombok.Getter;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

import static com.example.courses.utils.Utils.getCurrencyCourseNumber;

@Getter
@Repository
public class CoursesStorage {

    private final HashMap<String, CircularFifoQueue<CourseRegistered>> storage = new HashMap<>();

    CoursesStorage() {
        Utils.getSupportedCurrencies().forEach((c) -> storage.put(c,
                new CircularFifoQueue<>(getCurrencyCourseNumber())));
    }
}
