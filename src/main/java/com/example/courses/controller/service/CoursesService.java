package com.example.courses.controller.service;

import com.example.courses.model.CourseDto;
import com.example.courses.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Currency;

import static com.example.courses.controller.utils.Utils.getResponseMessage;
import static com.example.courses.controller.utils.Utils.successMessage;

@Service
public class CoursesService {

    final private Logger logger = LoggerFactory.getLogger("CourseService Logger");

    /**
     * Method creates course entity and keep it in storage
     *
     * @param courseDto nullable, but error message will return in such a case
     * @return message about result of operation
     */
    public ResponseMessage regCourse(CourseDto courseDto) {
        if (!courseDtoIsOk(courseDto)) {
            return getResponseMessage(1, "Получены некорректные данные курса валюты");
        }
//TODO: implement code to register and keep course data in collection
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

}
