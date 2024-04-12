package com.example.courses.controller;

import com.example.courses.controller.service.CoursesService;
import com.example.courses.model.CourseDto;
import com.example.courses.model.ResponseMessage;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("courses")
public class CoursesController {

    final private CoursesService coursesService;

    @PostMapping("/regCourses")
    public ResponseEntity<ResponseMessage> regCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(coursesService.regCourse(courseDto));
    }

}