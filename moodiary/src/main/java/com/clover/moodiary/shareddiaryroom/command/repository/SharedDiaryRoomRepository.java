package com.clover.moodiary.shareddiaryroom.command.repository;

import com.clover.moodiary.shareddiaryroom.command.entity.SharedDiaryRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedDiaryRoomRepository extends JpaRepository<SharedDiaryRoom, Integer> {
}
