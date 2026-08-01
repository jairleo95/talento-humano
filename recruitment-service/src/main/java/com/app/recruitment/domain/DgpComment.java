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
@Table("dgp_comment")
public class DgpComment {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("requisition_id")
    private UUID requisitionId;

    @Column("user_id")
    private String userId;

    @Column("username")
    private String username;

    @Column("content")
    private String content;

    @Column("created_at")
    private Instant createdAt;
}
