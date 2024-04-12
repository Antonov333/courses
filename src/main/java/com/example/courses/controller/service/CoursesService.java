package com.example.courses.controller.service;

import com.example.courses.model.CourseDto;
import com.example.courses.model.ResponseMessage;
import org.springframework.stereotype.Service;

import static com.example.courses.controller.utils.Utils.getResponseMessage;
import static com.example.courses.controller.utils.Utils.successMessage;

@Service
public class CoursesService {
    public ResponseMessage regCourse(CourseDto courseDto) {
        ResponseMessage responseMessage;
        if (courseDtoIsOk(courseDto)) {
            responseMessage = successMessage();
        } else {
            responseMessage = getResponseMessage(1, "Получены некорректные данные курса");
        }
        return responseMessage;
    }

    boolean courseDtoIsOk(CourseDto courseDto) {
        // Реализуем простейшую проверку
        if (courseDto == null) {
            return false;
        }
        if (courseDto.getCurrencyId().isEmpty() | (courseDto.getCurrencyVal() <= 0.0f)) {
            return false;
        }
        return true;
    }


}
