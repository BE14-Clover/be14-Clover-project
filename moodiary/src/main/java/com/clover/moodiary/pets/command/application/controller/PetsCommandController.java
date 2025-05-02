package com.clover.moodiary.pets.command.application.controller;

import com.clover.moodiary.pets.command.application.dto.PetsCommandDTO;
import com.clover.moodiary.pets.command.application.service.PetsCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetsCommandController {

    private final PetsCommandService petsCommandService;

    @PostMapping("/update")
    public ResponseEntity<String> updatePets(@RequestBody PetsCommandDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) auth.getPrincipal();

        log.info("[updatePets] 요청: userId={}, petId={}", userId, dto.getId());

        petsCommandService.updateUserPet(userId, dto.getId());

        return ResponseEntity.ok("펫이 변경되었습니다.");
    }
}