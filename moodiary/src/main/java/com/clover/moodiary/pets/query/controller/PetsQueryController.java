package com.clover.moodiary.pets.query.controller;

import com.clover.moodiary.pets.query.dto.PetsQueryDTO;
import com.clover.moodiary.pets.query.service.PetsQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetsQueryController {

    private final PetsQueryService petsQueryService;

    @GetMapping("/current")
    public ResponseEntity<PetsQueryDTO> getCurrentPet() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) auth.getPrincipal();


        log.info("[getCurrentPet] 요청 userId={}", userId);

        PetsQueryDTO dto = petsQueryService.getCurrentUserPet(userId);

        if (dto == null) {
            log.warn("[getCurrentPet] 해당 userId={}에 등록된 펫이 없습니다.", userId);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }
}