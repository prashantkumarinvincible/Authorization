package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CBDC_INITIATIVE_T")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Initiative {
    @Id
    @Column(name = "INITIATIVE_ID")
    private Long id;

    @Column(name = "STATUS_ID")
    private Long statusId;

    @Column(name = "DSMT_MS_ID")
    private String managedSegmentId;

    @Column(name = "SPONSOR_ID")
    private String sponsorId;

    @Column(name = "CREATED_BY")
    private String createdBy;
}
