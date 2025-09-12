package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CBDC_INIT_OWNER_T")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiativeOwner {
    @Id
    @Column(name = "INITIATIVE_OWNER_ID")
    private Long id;

    @Column(name = "INITIATIVE_ID")
    private Long initiativeId;

    @Column(name = "OWNER_ID")
    private String ownerId;
}
