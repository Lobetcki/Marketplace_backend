package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.commentsDTO.CommentsDTO;
import ru.skypro.homework.dto.commentsDTO.ResponseWrapperCommentDTO;
import ru.skypro.homework.service.ServiceComments;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class ControllerComments {

    private final ServiceComments serviceComments;

    public ControllerComments(ServiceComments serviceComments) {
        this.serviceComments = serviceComments;
    }

    // Получить комментарии объявления
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDTO> getCommentsByAdId(@PathVariable Long id) {
        ResponseWrapperCommentDTO commentsListDTO = serviceComments.getCommentsByAdId(id);
        return ResponseEntity.ok(commentsListDTO);
    }

    // Добавить комментарий к объявлению
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO> addComment(@PathVariable Long id,
                                                  @RequestBody CommentsDTO commentDTO) {
        CommentsDTO commentsDTO = serviceComments.addComment(id, commentDTO);
        return ResponseEntity.ok(commentsDTO);
    }

    // Удалить комментарий
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long adId,
                                        @PathVariable Long commentId) {
        if (serviceComments.deleteComment(adId, commentId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Обновить комментарий
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentsDTO> updateComment(@PathVariable Long adId,
                                                     @PathVariable Long commentId,
                                                     @RequestBody CommentsDTO commentDTO) {
        CommentsDTO commentUpdateDTO = serviceComments.updateComment(adId, commentId, commentDTO);
        if (commentUpdateDTO != null) {
            return ResponseEntity.ok(commentUpdateDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
