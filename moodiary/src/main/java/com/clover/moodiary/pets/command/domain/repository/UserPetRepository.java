package com.clover.moodiary.pets.command.domain.repository;

import com.clover.moodiary.pets.command.domain.aggregate.entity.UserPetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPetRepository extends JpaRepository<UserPetEntity, Integer> {

}