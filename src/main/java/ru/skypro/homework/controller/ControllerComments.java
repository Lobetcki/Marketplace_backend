package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.commentsDTO.CommentsDTO;
import ru.skypro.homework.dto.commentsDTO.ResponseWrapperCommentDTO;
import ru.skypro.homework.service.ServiceComments;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class ControllerComments {

    private final ServiceComments serviceComments;

    public ControllerComments(ServiceComments serviceComments) {
        this.serviceComments = serviceComments;
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Получить комментарии объявления", tags = "Комментарии")
    public ResponseEntity<ResponseWrapperCommentDTO> getCommentsByAdId(@PathVariable Long id) {
        ResponseWrapperCommentDTO commentsListDTO = serviceComments.getCommentsByAdId(id);
        return ResponseEntity.ok(commentsListDTO);
    }

    @PostMapping("/{id}/comments")
    @Operation(
            summary = "Добавление нового комментария к объявлению", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentsDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    public ResponseEntity<CommentsDTO> addComment(@PathVariable Long id,
                                                  @RequestBody CommentsDTO commentDTO,
                                                  Authentication authentication) {
        CommentsDTO commentsDTO = serviceComments.addComment(id, commentDTO, authentication);
        return ResponseEntity.ok(commentsDTO);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @Operation(
            summary = "Удалить комментарий", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @PreAuthorize("commentServiceImpl.getCommentById(#commentId).getAuthor().username" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteComment(@PathVariable Long adId,
                                        @PathVariable Long commentId) {
        if (serviceComments.deleteComment(adId, commentId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @Operation(
            summary = "Обновить комментарий", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentsDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @PreAuthorize("commentServiceImpl.getCommentById(#commentId).getAuthor().username" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
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
