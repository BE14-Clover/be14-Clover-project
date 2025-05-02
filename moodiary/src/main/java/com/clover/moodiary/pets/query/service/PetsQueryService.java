package com.clover.moodiary.pets.query.service;

import com.clover.moodiary.pets.query.dto.PetsQueryDTO;

public interface PetsQueryService {
    PetsQueryDTO getCurrentUserPet(Integer userId);
}