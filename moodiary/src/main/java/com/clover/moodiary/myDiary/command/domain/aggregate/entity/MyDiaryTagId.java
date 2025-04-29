package com.clover.moodiary.myDiary.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyDiaryTagId implements Serializable {

    @Column(name = "my_diary_id")
    private Integer myDiaryId;

    @Column(name = "tag_id")
    private Integer tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDiaryTagId that = (MyDiaryTagId) o;
        return Objects.equals(myDiaryId, that.myDiaryId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myDiaryId, tagId);
    }

}
