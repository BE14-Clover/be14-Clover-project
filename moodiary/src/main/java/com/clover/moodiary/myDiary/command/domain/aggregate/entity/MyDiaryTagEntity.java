package com.clover.moodiary.myDiary.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "my_diary_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyDiaryTagEntity {

    @EmbeddedId
    private MyDiaryTagId id;

    @MapsId("myDiaryId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_diary_id")
    private MyDiaryEntity myDiary;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private TagEntity tag;
}