package ru.skypro.homework.dto.commentsDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseWrapperCommentDTO {
    private Integer countComments;
    private List<CommentsDTO> CommentsDTOList;

    public ResponseWrapperCommentDTO() {
        countComments = CommentsDTOList.size();
    }
}
