package com.clover.moodiary.pets.query.mapper;

import com.clover.moodiary.pets.query.dto.PetsQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PetsMapper {
    PetsQueryDTO findPetByUserId(@Param("userId") Integer userId);
}