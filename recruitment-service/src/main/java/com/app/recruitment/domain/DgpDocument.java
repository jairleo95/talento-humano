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
@Table("dgp_document")
public class DgpDocument {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("requisition_id")
    private UUID requisitionId;

    @Column("filename")
    private String filename;

    @Column("content_type")
    private String contentType;

    @Column("description")
    private String description;

    @Column("uri")
    private String uri;

    @Column("size_bytes")
    private Long sizeBytes;

    @Column("created_at")
    private Instant createdAt;
}
