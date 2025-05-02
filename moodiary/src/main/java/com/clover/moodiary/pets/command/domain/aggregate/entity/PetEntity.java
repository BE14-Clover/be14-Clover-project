package com.clover.moodiary.pets.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {

    @Id
    private Integer id;  // pet_id

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(name = "pet_img", nullable = false)
    private String petImg;
}