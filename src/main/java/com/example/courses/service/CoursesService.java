package com.example.courses.service;

import com.example.courses.mapper.CoursesMapper;
import com.example.courses.model.CourseDto;
import com.example.courses.model.CourseRegistered;
import com.example.courses.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static com.example.courses.utils.Utils.*;

@RequiredArgsConstructor
@Service
public class CoursesService {

    final private CoursesStorage coursesStorage;

    final private Logger logger = LoggerFactory.getLogger("CourseService Logger");

    /**
     * Method creates course entity and keep it in storage
     *
     * @param courseDto nullable, but error message will return in such a case
     * @return message about result of operation
     */
    public ResponseMessage regCourse(CourseDto courseDto) {

        ResponseMessage responseMessage = checkCourseDto(courseDto);
        if (responseMessage.getErrCode() != 0) {
            return responseMessage; // Something went wrong, so return message with explanation
        }
// Registering and keeping course data in collection
        LocalDateTime registrationTime = LocalDateTime.now();
        CourseRegistered courseRegistered = CoursesMapper.INSTANCE.getCourseRegistered(courseDto);
        courseRegistered.setRegTime(registrationTime);
        return coursesStorage.saveCourseRegistered(courseRegistered);
    }

    /**
     * @param courseDto provided course DTO, nullable
     * @return SUCCESS response message if argument DTO has supported currency code and correct value or currency rate,
     * or error message with explanation of reason
     */
    private ResponseMessage checkCourseDto(CourseDto courseDto) {
        if (courseDto == null) {
            return getResponseMessage(1, "Получен null DTO");
        }

        if (courseDto.getCurrencyVal() <= 0.0f) {
            return getResponseMessage(1, "Значение курса неположительное");
        }

        if (!(getSupportedCurrencies().contains(courseDto.getCurrencyId()))) {
            return getResponseMessage(1, "Валюта не поддерживается");
        }

        return successMessage();
    }

    /**
     * Сохранение массива данных курсов валют, полученного из эндпойнта
     *
     * @param courseData массив данных курсов, полученный из эндпойнта, nullable
     * @return сообщение
     */
    public ResponseMessage loadCoursesData(CourseRegistered[] courseData) {
        if (courseData == null) {
            return getResponseMessage(1,
                    "Аргумент null");
        }
        if (courseData.length == 0) {
            return getResponseMessage(1,
                    "Получен пустой массив");
        }

        // Обрабатываем массив
        int totalCountOfCourses = courseData.length;
        int countOfLoadedCourses = 0;

        for (CourseRegistered c : courseData) {
            if (c == null) {
                continue;
            }
            if (checkCourseDto(CoursesMapper.INSTANCE.getCourseDto(c)).getErrCode() != 0) {
                continue;
            }
            coursesStorage.saveCourseRegistered(c);
            countOfLoadedCourses++;
        }

        return getResponseMessage(Integer.min(1, totalCountOfCourses - countOfLoadedCourses),
                "Получено курсов валют: " + totalCountOfCourses + ". Сохранено: " + countOfLoadedCourses);
    }


    /**
     * Get latest course of given currency
     *
     * @param currencyId nullable currency code of 3 chars
     * @return
     */
    public CourseRegistered getLatest(String currencyId) {
        CourseRegistered course = new CourseRegistered();
        if (!currencyIsSupported(currencyId)) { //wrong currencyId provided, so null returned
            return course;
        }
        HashMap<LocalDateTime, CourseRegistered> courses = coursesStorage.getStorage().get(currencyId).getCourseVsTime();
        List<LocalDateTime> timeKeys = courses.keySet().stream().sorted().toList();

        course = courses.get(timeKeys.get(timeKeys.size() - 1)); // retrieving latest course of given currency
        return course;
    }

    public List<CourseRegistered> getMax5(String currencyId) {
        List<CourseRegistered> list5 = new ArrayList<>();
        CourseRegistered[] max5 = new CourseRegistered[5];
        if (!currencyIsSupported(currencyId)) { //wrong currencyId provided, so empty array returned
            return list5;
        }

        List<CourseRegistered> courses = coursesStorage.getStorage().get(currencyId)
                .getCourseVsTime().values().stream().toList();

        courses.sort(Comparator.comparing(CourseRegistered::getCurrencyVal, Comparator.reverseOrder()));
        list5 = courses.stream().limit(5).toList();

        return list5;
    }
}
