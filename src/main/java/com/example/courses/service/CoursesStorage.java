package com.example.courses.service;

import com.example.courses.model.CourseRegistered;
import com.example.courses.model.ResponseMessage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

import static com.example.courses.utils.Utils.*;

@Getter
@Repository
public class CoursesStorage {
    @Value("${storage.capacity}")
    private int capacity;

    private final HashMap<String, CoursesWithTiming> storage = new HashMap<>();

    CoursesStorage() {
        getSupportedCurrencies().forEach((c) -> storage.put(c,
                new CoursesWithTiming()));
    }

    /**
     * Сохраняет данные курса валюты в хранилище
     *
     * @param courseRegistered cущность курса валюты, NotNull
     * @return сообщение о результате выполнения операции
     */
    ResponseMessage saveCourseRegistered(CourseRegistered courseRegistered) {

        final String currencyId = courseRegistered.getCurrencyId();

        final LocalDateTime regTime = courseRegistered.getRegTime();
        if (storage.get(currencyId).getCourseVsTime().size() < capacity) {
            storage.get(currencyId).getCourseVsTime()
                    .put(regTime, courseRegistered);
            return successMessage();
        }

        LocalDateTime[] timeKeys = storage.get(currencyId)
                .getCourseVsTime().keySet().toArray(new LocalDateTime[0]);
        Arrays.sort(timeKeys);

        if (regTime.isBefore(timeKeys[0])) {
            return getResponseMessage(1, "Получено устаревшее значение курса валюты");
        }
        storage.get(currencyId).getCourseVsTime().remove(timeKeys[0]); // remove oldest course
        storage.get(currencyId).getCourseVsTime().put(regTime, courseRegistered); // keep new course in storage
        return successMessage();
    }

}
