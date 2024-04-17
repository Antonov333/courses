package com.example.courses.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Объект передачи значений полей сущности курса валюты")
public class CourseDto {
    private String currencyId;
    private float currencyVal;
}
