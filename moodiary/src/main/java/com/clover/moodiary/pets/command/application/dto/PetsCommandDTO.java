package com.clover.moodiary.pets.command.application.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PetsCommandDTO {
    private Integer id;
    private String petName;
    private String petImg;
}