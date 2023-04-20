package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.commentsDTO.CommentsDTO;
import ru.skypro.homework.dto.commentsDTO.ResponseWrapperCommentDTO;

@Service
public class ServiceComments {

                    // Получить комментарии объявления
    public ResponseWrapperCommentDTO getCommentsByAdId(Long id) {

        return new ResponseWrapperCommentDTO();
    }

                    // Добавить комментарий к объявлению
    public CommentsDTO addComment(Long adId, CommentsDTO commentDTO) {

        return null;
    }

                    // Удалить комментарий
    public boolean deleteComment(Long adId, Long commentId) {
        return false;
    }

                    // Обновить комментарий
    public CommentsDTO updateComment(Long adId, Long commentId, CommentsDTO commentDTO) {
        return null;
    }
}
