package com.app.contract.domain;

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
@Table("contract_attachment")
public class ContractAttachment {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("contract_id")
    private UUID contractId;

    @Column("filename")
    private String filename;

    @Column("content_type")
    private String contentType;

    @Column("uri")
    private String uri;

    @Column("size_bytes")
    private Long sizeBytes;

    @Column("checksum")
    private String checksum;

    @Column("created_at")
    private Instant createdAt;
}
