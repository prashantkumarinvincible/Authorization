package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "CBDC_ACCESS_ROLE_M")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessRole {
    @Id
    @Column(name = "ACCESS_ROLE_ID")
    private Long id;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "CBDC_ROLE_PERMISSION_MAP_M",
        joinColumns = @JoinColumn(name = "ROLE_ID"),
        inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID")
    )
    private Set<Permission> permissions;
}
