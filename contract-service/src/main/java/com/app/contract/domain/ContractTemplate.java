package com.app.contract.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("contract_template")
public class ContractTemplate {

    @Id
    private UUID id;

    @Column("name")
    private String name;

    @Column("version")
    private Integer version;

    @Column("content")
    private String content;

    @Column("file_name")
    private String fileName;

    @Column("status")
    private String status;

    @Column("created_at")
    private Instant createdAt;

    @Column("created_by")
    private String createdBy;
}
