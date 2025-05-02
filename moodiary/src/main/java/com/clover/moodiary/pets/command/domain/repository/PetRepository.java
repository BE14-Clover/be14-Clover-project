package com.clover.moodiary.pets.command.domain.repository;

import com.clover.moodiary.pets.command.domain.aggregate.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity, Integer> {
}