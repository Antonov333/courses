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
        // as registration time is as now, so this course is newest, so simply add course to storage
        coursesStorage.getStorage().get(courseRegistered.getCurrencyId()).add(courseRegistered);

        return successMessage();
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
            LocalDateTime oldestTime = coursesStorage.getStorage().get(c.getCurrencyId()).get(0).getRegTime();
            if (oldestTime.isAfter(c.getRegTime())) {
                continue; // пропускаем слишком старый курс
            }


        }



        return successMessage();
    }
}
