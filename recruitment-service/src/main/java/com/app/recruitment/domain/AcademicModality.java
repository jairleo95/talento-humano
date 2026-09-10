package com.app.recruitment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("academic_modality")
public class AcademicModality {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("code")
    private String code;

    @Column("name")
    private String name;

    @Column("sub_modality")
    private String subModality;

    @Column("sort_order")
    private Integer sortOrder;

    @Column("is_active")
    private Boolean isActive;

    @Column("created_at")
    private Instant createdAt;
}
