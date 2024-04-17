package com.example.courses.controller;

import com.example.courses.mapper.CoursesMapper;
import com.example.courses.model.CourseDto;
import com.example.courses.model.CourseRegistered;
import com.example.courses.service.CoursesStorage;
import com.example.courses.service.CoursesWithTiming;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/temp")
@Tag(name = "Temp controller for development purposes")
public class TempController {

    private final CoursesStorage coursesStorage;

    @PostMapping("/view-registered-course")
    public ResponseEntity<CourseRegistered> viewRegCourse(@RequestBody CourseDto courseDto) {
        CourseRegistered courseRegistered = CoursesMapper.INSTANCE.getCourseRegistered(courseDto);
        courseRegistered.setRegTime(LocalDateTime.now());
        return ResponseEntity.ok(courseRegistered);
    }

    @GetMapping("/view-capacity")
    public ResponseEntity<Integer> viewCapacity() {
        return ResponseEntity.ok(coursesStorage.getCapacity());
    }

    @GetMapping("view-storage")
    public ResponseEntity<HashMap<String, CoursesWithTiming>> viewStorage() {
        return new ResponseEntity<>(coursesStorage.getStorage(), HttpStatus.OK);
    }

    //TODO: delete TempController upon development completed

}
