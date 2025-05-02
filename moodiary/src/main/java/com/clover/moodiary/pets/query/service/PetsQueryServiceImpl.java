package com.clover.moodiary.pets.query.service;

import com.clover.moodiary.pets.query.dto.PetsQueryDTO;
import com.clover.moodiary.pets.query.mapper.PetsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetsQueryServiceImpl implements PetsQueryService {

    private final PetsMapper petsMapper;

    @Override
    public PetsQueryDTO getCurrentUserPet(Integer userId) {
        return petsMapper.findPetByUserId(userId);
    }
}