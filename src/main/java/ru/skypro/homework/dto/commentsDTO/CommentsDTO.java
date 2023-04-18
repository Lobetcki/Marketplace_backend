package ru.skypro.homework.dto.commentsDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CommentsDTO {

    public Integer pkCommentsId;
    public Instant createdAtDate;
    public String text;

    public Long authorId; // userId
    public String authorFirstName;
    public String authorImageUrl;


}
