package com.example.entitlements.entity;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionMapId implements Serializable {
    private Long roleId;
    private Long permissionId;
}
