package com.clover.moodiary.shareddiaryroom.command.service;

import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomRequest;
import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.command.dto.EnterSharedDiaryRoomRequest;
import com.clover.moodiary.shareddiaryroom.command.entity.SharedDiaryRoom;
import com.clover.moodiary.shareddiaryroom.command.repository.SharedDiaryRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SharedDiaryRoomServiceCommandImpl implements SharedDiaryRoomCommandService {

    private final SharedDiaryRoomRepository sharedDiaryRoomRepository;

    @Override
    @Transactional
    public CreateSharedDiaryRoomResponse createRoom(CreateSharedDiaryRoomRequest request) {
        SharedDiaryRoom room = new SharedDiaryRoom(request.getUserId1());
        SharedDiaryRoom savedRoom = sharedDiaryRoomRepository.save(room);
        return new CreateSharedDiaryRoomResponse(savedRoom.getId());
    }

    @Override
    @Transactional
    public void enterRoom(EnterSharedDiaryRoomRequest request) {
        SharedDiaryRoom room = sharedDiaryRoomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));

        if(room.getUserId1().equals(request.getUserId())){
            throw new IllegalArgumentException("이미 방에 입장해 있습니다.");
        }

        if (room.getUserId2() != null) {
            throw new IllegalArgumentException("이미 입장한 공유 일기방입니다.");
        }

        room.setUserId2(request.getUserId());
        sharedDiaryRoomRepository.save(room);
    }
}
