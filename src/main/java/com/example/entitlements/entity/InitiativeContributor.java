package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CBDC_INIT_CONTRIBUTOR_T")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiativeContributor {
    @Id
    @Column(name = "INIT_CONTRIBUTOR_ID")
    private Long id;

    @Column(name = "INITIATIVE_ID")
    private Long initiativeId;

    @Column(name = "CONTRIBUTOR_ID")
    private String contributorId;
}
