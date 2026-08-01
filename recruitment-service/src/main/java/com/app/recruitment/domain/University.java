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
@Table("university")
public class University {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("name")
    private String name;

    @Column("short_name")
    private String shortName;

    @Column("created_at")
    private Instant createdAt;
}
