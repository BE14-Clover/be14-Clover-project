package com.clover.moodiary.gptapi.command.service;

import com.clover.moodiary.gptapi.command.dto.SaveGptResultCommand;
import org.springframework.stereotype.Service;

@Service
public class GptCommandService {

    public void saveGptResult(SaveGptResultCommand command) {
        // 여기서 DB에 저장 로직 수행
        // 예시로 출력만
        System.out.println("Saving GPT Result: " + command.getResult());

        // 실제로는 JPA Repository.save() 호출
    }
}