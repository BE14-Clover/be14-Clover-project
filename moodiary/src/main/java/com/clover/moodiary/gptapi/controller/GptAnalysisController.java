// package com.clover.moodiary.gptapi.controller;

// import com.clover.moodiary.common.dto.CustomResponse;
// import com.clover.moodiary.gptapi.command.dto.GptRequestDto;
// import com.clover.moodiary.gptapi.service.GptAnalysisService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import reactor.core.publisher.Mono;

// @RestController
// @RequestMapping("/api/gpt")
// @RequiredArgsConstructor
// public class GptAnalysisController {

//         private final GptAnalysisService gptAnalysisService;

//         @PostMapping
//         public Mono<ResponseEntity<CustomResponse<String>>> analyzeDiary(@RequestBody GptRequestDto request) {
//                 return gptAnalysisService.analyzeDiary(request)
//                                 .map(response -> ResponseEntity.ok(CustomResponse.success(response)))
//                                 .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError()
//                                                 .body(CustomResponse.fail("감정 분석 실패: " + e.getMessage()))));
//         }
// }

package com.clover.moodiary.gptapi.controller;

import com.clover.moodiary.gptapi.command.dto.GptRequestDto;
import com.clover.moodiary.gptapi.command.dto.GptResponseDto;
import com.clover.moodiary.gptapi.service.GptAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
public class GptAnalysisController {

        private final GptAnalysisService gptAnalysisService;

        @PostMapping("/analyze")
        public Mono<GptResponseDto> analyzeDiary(@RequestBody GptRequestDto requestDto) {
                return gptAnalysisService.analyzeDiary(requestDto);
        }
}