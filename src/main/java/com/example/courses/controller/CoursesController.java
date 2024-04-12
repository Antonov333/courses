package com.example.courses.controller;

import com.example.courses.controller.service.CoursesService;
import com.example.courses.mapper.CoursesMapper;
import com.example.courses.model.CourseDto;
import com.example.courses.model.CourseRegistered;
import com.example.courses.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("courses")
public class CoursesController {

    final private CoursesService coursesService;

    @PostMapping("/regCourses")
    public ResponseEntity<ResponseMessage> regCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(coursesService.regCourse(courseDto));
    }

    @PostMapping("/view-regcourse")
    public ResponseEntity<CourseRegistered> viewRegCourse(@RequestBody CourseDto courseDto) {
        CourseRegistered courseRegistered = CoursesMapper.INSTANCE.getCourseRegistered(courseDto);
        courseRegistered.setRegistrationTime(LocalDateTime.now());
        return ResponseEntity.ok(courseRegistered);
    }

}