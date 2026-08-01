package com.app.identity.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("role_privilege")
public class RolePrivilege {

    @Id
    private UUID id;

    @Column("role_id")
    private UUID roleId;

    @Column("privilege_id")
    private UUID privilegeId;

    @Column("sort_order")
    private Integer sortOrder;

    @Column("is_active")
    private Boolean isActive;
}
