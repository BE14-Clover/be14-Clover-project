package com.clover.moodiary.pets.command.application.service;

import com.clover.moodiary.pets.command.domain.aggregate.entity.PetEntity;
import com.clover.moodiary.pets.command.domain.aggregate.entity.UserPetEntity;
import com.clover.moodiary.pets.command.domain.repository.PetRepository;
import com.clover.moodiary.pets.command.domain.repository.UserPetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetsCommandServiceImpl implements PetsCommandService {

    private final UserPetRepository userPetRepository;
    private final PetRepository petRepository;

    @Override
    @Transactional
    public void updateUserPet(Integer userId, Integer petId) {
        // 1️⃣ user_pet 가져오기
        UserPetEntity userPet = userPetRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원의 펫 정보가 없습니다."));

        // 2️⃣ pet 테이블 검증
        PetEntity pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 펫입니다."));

        // 3️⃣ user_pet 테이블의 pet_id 업데이트
        userPet.updatePet(petId);
    }
}