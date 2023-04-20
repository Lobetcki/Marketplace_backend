package ru.skypro.homework.dto.commentsDTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.ResponseWrapperAdsDTO;

import java.util.List;

@Getter
@Setter
public class ResponseWrapperCommentDTO {
    private Integer countComments;
    private List<CommentsDTO> CommentsDTOList;

    public static ResponseWrapperCommentDTO fromDTO(List<CommentsDTO> list) {
        ResponseWrapperCommentDTO result = new ResponseWrapperCommentDTO();
        result.setCommentsDTOList(list);
        result.setCountComments(list.size());
        return result;
    }
}
