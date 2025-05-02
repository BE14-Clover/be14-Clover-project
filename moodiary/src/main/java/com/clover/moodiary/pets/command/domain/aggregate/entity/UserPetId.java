package com.clover.moodiary.pets.command.domain.aggregate.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class UserPetId implements Serializable {
    private Integer userId;
    private Integer petId;
}