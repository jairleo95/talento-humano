package com.app.recruitment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("academic_course")
public class AcademicCourse {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("charge_id")
    private UUID chargeId;

    @Column("campus")
    private String campus;

    @Column("course_name")
    private String courseName;

    @Column("group_number")
    private String groupNumber;

    @Column("schedule")
    private String schedule;

    @Column("hours")
    private Double hours;

    @Column("course_condition")
    private String courseCondition;

    @Column("course_type")
    private String courseType;
}