package com.example.courses.mapper;

import com.example.courses.model.CourseDto;
import com.example.courses.model.CourseRegistered;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoursesMapper {
    CoursesMapper INSTANCE = Mappers.getMapper(CoursesMapper.class);

    CourseRegistered getCourseRegistered(CourseDto courseDto);

}
