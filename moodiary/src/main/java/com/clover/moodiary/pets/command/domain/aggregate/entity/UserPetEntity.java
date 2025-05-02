package com.clover.moodiary.pets.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_pet")
@Getter
@NoArgsConstructor
public class UserPetEntity {

    @Id
    @Column(name = "user_id")
    private Integer userId;  // PK = userId (회원 1명당 한 줄)

    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    public void updatePet(Integer petId) {
        this.petId = petId;
    }
}