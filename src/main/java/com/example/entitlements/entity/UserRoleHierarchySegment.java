package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CBDC_USER_ROLE_HIERARCHY_MG_SEG_T")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleHierarchySegment {
    @Id
    @Column(name = "USER_ROLE_HIERCHY_MG_SEG_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ROLE_ID")
    private UserRole userRole;

    @Column(name = "DSMT_MANSEG_NODE_ID")
    private String managedSegmentId;
}
