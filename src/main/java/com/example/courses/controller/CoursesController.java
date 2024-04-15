package com.example.courses.controller;

import com.example.courses.model.CourseDto;
import com.example.courses.model.CourseRegistered;
import com.example.courses.model.ResponseMessage;
import com.example.courses.service.CoursesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("courses")
@Tag(name = "Courses Controller", description = "Контроллер для обработки данных обменных курсов валют")
public class CoursesController {

    final private CoursesService coursesService;

    @PostMapping("/regCourses")
    @Operation(summary = "Эндпойнт для получения данных обменного курса валют")
    public ResponseEntity<ResponseMessage> regCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(coursesService.regCourse(courseDto));
    }

    @PostMapping("/loadData")
    @Operation(summary = "Загрузка массива курсов")
    public ResponseEntity<ResponseMessage> loadData(@RequestBody CourseRegistered[] courseData) {
        return ResponseEntity.ok(coursesService.loadCoursesData(courseData));
    }

}