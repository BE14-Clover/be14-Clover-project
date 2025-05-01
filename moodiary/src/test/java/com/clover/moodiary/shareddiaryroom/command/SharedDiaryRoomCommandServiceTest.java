package com.clover.moodiary.shareddiaryroom.command;

import com.clover.moodiary.shareddiaryroom.command.entity.SharedDiaryRoom;
import com.clover.moodiary.shareddiaryroom.command.repository.SharedDiaryRoomRepository;
import com.clover.moodiary.shareddiaryroom.command.service.SharedDiaryRoomCommandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SharedDiaryRoomCommandServiceTest {

    @Autowired
    private SharedDiaryRoomCommandService sharedDiaryRoomCommandService;

    @Autowired
    private SharedDiaryRoomRepository sharedDiaryRoomRepository;

    @ParameterizedTest
    @DisplayName("공유 일기방 생성 테스트")
    @ValueSource(ints = {1})
    public void createSharedDiaryRoomTest(int userId1) {
        // 변경된 createRoom(userId) 방식에 맞춤
        Integer roomId = sharedDiaryRoomCommandService.createRoom(userId1).getRoomId();

        SharedDiaryRoom room = sharedDiaryRoomRepository.findById(roomId).orElse(null);
        Assertions.assertNotNull(room);
        Assertions.assertEquals(userId1, room.getUserId1());
        Assertions.assertNull(room.getUserId2());
    }

    @ParameterizedTest
    @DisplayName("공유 일기방 입장 테스트")
    @ValueSource(ints = {2})
    public void enterSharedDiaryRoomTest(int userId2) {
        // 1번 유저가 먼저 생성
        Integer roomId = sharedDiaryRoomCommandService.createRoom(1).getRoomId();

        // 2번 유저가 입장 (DTO 없이 userId 직접 전달)
        sharedDiaryRoomCommandService.enterRoom(roomId, userId2);

        SharedDiaryRoom room = sharedDiaryRoomRepository.findById(roomId).orElse(null);
        Assertions.assertNotNull(room);
        Assertions.assertEquals(1, room.getUserId1());
        Assertions.assertEquals(userId2, room.getUserId2());
    }
}
