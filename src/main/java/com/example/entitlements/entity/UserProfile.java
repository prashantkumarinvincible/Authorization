package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "CBDC_USER_PROFILE_T")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    @Id
    @Column(name = "SOE_ID")
    private String soeId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IS_ACTIVE")
    private Integer isActive;

    @Column(name = "DEFAULT_MG_SEG_ID")
    private String defaultManagedSegmentId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserRole> roles;
}
