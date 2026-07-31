package com.app.identity.domain;

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
@Table("user_account")
public class UserAccount {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("username")
    private String username;

    @Column("email")
    private String email;

    @Column("enabled")
    private Boolean enabled;

    @Column("created_at")
    private Instant createdAt;
}
