package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "CBDC_USER_ROLE_T")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {
    @Id
    @Column(name = "USER_ROLE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOE_ID")
    private UserProfile user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCESS_ROLE_ID")
    private AccessRole role;

    @OneToMany(mappedBy = "userRole", fetch = FetchType.LAZY)
    private Set<UserRoleHierarchySegment> hierarchySegments;
}
