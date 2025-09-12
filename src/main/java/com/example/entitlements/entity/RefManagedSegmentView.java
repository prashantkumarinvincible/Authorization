package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CBDC_REF_MANAGED_SEGMENT_V")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefManagedSegmentView {
    @Id
    @Column(name = "MAN_SEGMENT_ID")
    private String id;

    @Column(name = "MAN_SEGMENT_NAME")
    private String name;

    @Column(name = "MAN_SEGMENT_PARENT_ID")
    private String parentId;

    @Column(name = "SEGMENT_LVL")
    private Integer segmentLvl;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "HIERARCHY_STRING")
    private String hierarchyString;

    @Column(name = "HAS_CHILDREN")
    private Integer hasChildren;
}
