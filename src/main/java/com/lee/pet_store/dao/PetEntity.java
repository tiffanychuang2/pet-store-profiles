package com.lee.pet_store.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pets")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pet_name", nullable = true)
    private String petName;

    @Column(name = "pet_type", nullable = true)
    private String petType;
}
