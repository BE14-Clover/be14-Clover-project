package com.clover.moodiary.shareddiaryroom.query.mapper;

import com.clover.moodiary.shareddiaryroom.query.dto.SharedDiaryRoomResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SharedDiaryRoomMapper {

    List<SharedDiaryRoomResponse> findRoomByUserId(Integer userId);
}
