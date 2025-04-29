package com.clover.moodiary.shareddiaryroom.command.service;

import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomRequest;
import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.command.entity.SharedDiaryRoom;
import com.clover.moodiary.shareddiaryroom.command.repository.SharedDiaryRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedDiaryRoomServiceImpl implements SharedDiaryRoomService {

    private final SharedDiaryRoomRepository sharedDiaryRoomRepository;

    @Override
    @Transactional
    public CreateSharedDiaryRoomResponse createRoom(CreateSharedDiaryRoomRequest request) {
        SharedDiaryRoom room = new SharedDiaryRoom(request.getUserId1());
        SharedDiaryRoom savedRoom = sharedDiaryRoomRepository.save(room);
        return new CreateSharedDiaryRoomResponse(savedRoom.getId());
    }
}
