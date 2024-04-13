package com.example.courses.controller.service;

import com.example.courses.mapper.CoursesMapper;
import com.example.courses.model.CourseDto;
import com.example.courses.model.CourseRegistered;
import com.example.courses.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Currency;

import static com.example.courses.controller.utils.Utils.*;

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
            return responseMessage;
        }
// Registering and keeping course data in collection
        LocalDateTime registrationTime = LocalDateTime.now();
        CourseRegistered courseRegistered = CoursesMapper.INSTANCE.getCourseRegistered(courseDto);
        courseRegistered.setRegistrationTime(registrationTime);
        coursesStorage.getStorage().get(courseRegistered.getCurrencyId()).add(courseRegistered);

        return successMessage();
    }

    /**
     * Method checks whether provided course data is correct
     * @param courseDto nullable
     * @return true if OK
     */
    boolean courseDtoIsOk(CourseDto courseDto) {
        // Check null argument
        if (courseDto == null) {
            return false;
        }

        //Check whether currency rate value is correct
        if (courseDto.getCurrencyVal() <= 0.0f) {
            return false;
        }

        //Check whether currency code is known
        return (Currency.getAvailableCurrencies()
                .contains(Currency.getInstance(courseDto.getCurrencyId())));
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
}
