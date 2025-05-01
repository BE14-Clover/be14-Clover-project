package com.clover.moodiary.shareddiaryroom.command;

import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomRequest;
import com.clover.moodiary.shareddiaryroom.command.dto.EnterSharedDiaryRoomRequest;
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
    public void createSharedDiaryRoomTest(int userId1){

        CreateSharedDiaryRoomRequest request = new CreateSharedDiaryRoomRequest(userId1);

        Integer roomId = sharedDiaryRoomCommandService.createRoom(request).getRoomId();

        SharedDiaryRoom room = sharedDiaryRoomRepository.findById(roomId).orElse(null);
        Assertions.assertNotNull(room);
        Assertions.assertEquals(userId1, room.getUserId1());
        Assertions.assertNull(room.getUserId2());
    }

    @ParameterizedTest
    @DisplayName("공유 일기방 입장 테스트")
    @ValueSource(ints = {2})
    public void enterSharedDiaryRoomTest(int userId2) {

        CreateSharedDiaryRoomRequest createRequest = new CreateSharedDiaryRoomRequest(1);
        Integer roomId = sharedDiaryRoomCommandService.createRoom(createRequest).getRoomId();

        EnterSharedDiaryRoomRequest enterRequest = new EnterSharedDiaryRoomRequest(roomId, userId2);
        sharedDiaryRoomCommandService.enterRoom(enterRequest);

        SharedDiaryRoom room = sharedDiaryRoomRepository.findById(roomId).orElse(null);
        Assertions.assertNotNull(room);
        Assertions.assertEquals(1,room.getUserId1());
        Assertions.assertEquals(userId2,room.getUserId2());
    }
}
