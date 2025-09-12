package com.example.entitlements.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CBDC_PERMISSION_M")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {
    @Id
    @Column(name = "PERMISSION_ID")
    private Long id;

    @Column(name = "PERMISSION_NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;
}
