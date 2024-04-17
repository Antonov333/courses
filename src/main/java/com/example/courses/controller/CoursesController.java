package com.example.courses.controller;

import com.example.courses.model.CourseDto;
import com.example.courses.model.CourseRegistered;
import com.example.courses.model.ResponseMessage;
import com.example.courses.service.CoursesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getCourse")
    @Operation(summary = "Получение самого нового курса выбранной валюты")
    public ResponseEntity<CourseRegistered> getLatestCourse(
            @Parameter(name = "currencyId", description = "Трехсимвольный код валюты") @RequestParam(name = "currencyId") String currencyId) {
        return ResponseEntity.ok(coursesService.getLatest(currencyId));
    }

    @GetMapping("/getCourseMax5")
    @Operation(summary = "Получение 5 последних самых высоких курсов выбранной валюты")
    public ResponseEntity<List<CourseRegistered>> getMax5(
            @Parameter(name = "currencyId", description = "Трехсимвольный код валюты") @RequestParam(name = "currencyId") String currencyId) {
        return ResponseEntity.ok(coursesService.getMax5(currencyId));
    }

    //TODO: consider how to manager if several courses of same currency have same time but different values

    @GetMapping("/getCourseExtremum3")
    @Operation(summary = "Получение трех наивысших пиков курсов заданной валюты")
    public ResponseEntity<List<CourseRegistered>> getCourseExtremum3(
            @Parameter(name = "currencyId", description = "Трехсимвольный код валюты") @RequestParam(name = "currencyId") String currencyId) {
        return ResponseEntity.ok(coursesService.getCourseExtremum3(currencyId));
    }
}