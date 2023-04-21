package ru.skypro.homework.dto.commentsDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseWrapperCommentDTO {
    private Integer count;
    private List<CommentsDTO> results;

    public static ResponseWrapperCommentDTO fromDTO(List<CommentsDTO> list) {
        ResponseWrapperCommentDTO result = new ResponseWrapperCommentDTO();
        result.setResults(list);
        result.setCount(list.size());
        return result;
    }
}
