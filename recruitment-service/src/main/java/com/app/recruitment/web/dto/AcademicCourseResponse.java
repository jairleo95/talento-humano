package com.app.recruitment.web.dto;

import java.util.UUID;

public record AcademicCourseResponse(
        UUID id,
        UUID chargeId,
        String campus,
        String courseName,
        String groupNumber,
        String schedule,
        Double hours,
        String courseCondition,
        String courseType
) {
}