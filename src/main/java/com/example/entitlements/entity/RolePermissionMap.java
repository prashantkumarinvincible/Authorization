package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CBDC_ROLE_PERMISSION_MAP_M")
@IdClass(RolePermissionMapId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissionMap {
    @Id
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Id
    @Column(name = "PERMISSION_ID")
    private Long permissionId;

    @Column(name = "IS_ACTIVE")
    private Integer isActive;
}
