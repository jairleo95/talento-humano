package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AcademicCourseRequest(
        String campus,
        @NotBlank String courseName,
        String groupNumber,
        String schedule,
        Double hours,
        String courseCondition,
        String courseType
) {
}