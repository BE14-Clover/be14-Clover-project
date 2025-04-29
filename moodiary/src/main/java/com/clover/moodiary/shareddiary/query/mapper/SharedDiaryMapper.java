package com.clover.moodiary.shareddiary.query.mapper;

import com.clover.moodiary.shareddiary.query.dto.SharedDiaryResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SharedDiaryMapper {

    List<SharedDiaryResponse> findDiaryByRoomId(Integer roomId);
}
