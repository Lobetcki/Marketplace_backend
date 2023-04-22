package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.commentsDTO.CommentsDTO;
import ru.skypro.homework.dto.commentsDTO.ResponseWrapperCommentDTO;
import ru.skypro.homework.exception.MarketNotFoundException;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comments;
import ru.skypro.homework.repositories.RepositoryAds;
import ru.skypro.homework.repositories.RepositoryComments;
import ru.skypro.homework.repositories.RepositoryUsers;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceComments {

    private final RepositoryComments repositoryComments;
    private final RepositoryAds repositoryAds;
    private final RepositoryUsers repositoryUsers;

    public ServiceComments(RepositoryComments repositoryComments,
                           RepositoryAds repositoryAds, RepositoryUsers repositoryUsers) {
        this.repositoryComments = repositoryComments;
        this.repositoryAds = repositoryAds;
        this.repositoryUsers = repositoryUsers;
    }

    // Получить комментарии объявления
    public ResponseWrapperCommentDTO getCommentsByAdId(Long id) {
        List<CommentsDTO> listDTO = repositoryComments.findAllByAdsId(id)
                .stream().map(CommentsDTO::fromCommentsDTO)
                .collect(Collectors.toList());
        return ResponseWrapperCommentDTO.fromDTO(listDTO);
    }

    // Добавить комментарий к объявлению
    public CommentsDTO addComment(Long adId,
                                  CommentsDTO commentDTO,
                                  Authentication authentication) {
        Ads ads = repositoryAds.findById(adId)
                .orElseThrow(MarketNotFoundException::new);
        Comments comments = commentDTO.toComments();
        comments.setAds(ads);
        comments.setCreatedAtDate(Instant.now());
        comments.setUsers(repositoryUsers.findByUsernameIgnoreCase(
                                            authentication.getName()));
        repositoryComments.save(comments);
        return CommentsDTO.fromCommentsDTO(comments);
    }

    // Удалить комментарий
    public boolean deleteComment(Long adId, Long commentId) {
        Comments comments = repositoryComments.findByIdAndAdsId(commentId, adId)
                .orElseThrow(MarketNotFoundException::new);
        repositoryComments.delete(comments);
        return true;
    }

    // Обновить комментарий
    public CommentsDTO updateComment(Long adId, Long commentId,
                                     CommentsDTO commentDTO) {
        Comments comments = repositoryComments.findByIdAndAdsId(commentId, adId)
                .orElseThrow(MarketNotFoundException::new);
        comments.setText(commentDTO.getText());
        commentDTO = CommentsDTO.fromCommentsDTO(comments);
        repositoryComments.save(comments);
        System.out.println(commentDTO);
        return commentDTO;
    }
}
