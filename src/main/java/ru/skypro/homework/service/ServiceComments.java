package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.commentsDTO.CommentsDTO;
import ru.skypro.homework.dto.commentsDTO.ResponseWrapperCommentDTO;
import ru.skypro.homework.exception.UsersNotFoundException;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comments;
import ru.skypro.homework.repositories.RepositoryAds;
import ru.skypro.homework.repositories.RepositoryComments;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceComments {

    private final RepositoryComments repositoryComments;
    private final RepositoryAds repositoryAds;

    public ServiceComments(RepositoryComments repositoryComments,
                           RepositoryAds repositoryAds) {
        this.repositoryComments = repositoryComments;
        this.repositoryAds = repositoryAds;
    }


    // Получить комментарии объявления
    public ResponseWrapperCommentDTO getCommentsByAdId(Long id) {
        List<CommentsDTO> listDTO = repositoryComments.findAllByAdsId(id)
                .stream().map(CommentsDTO::fromCommentsDTO)
                .collect(Collectors.toList());
        return ResponseWrapperCommentDTO.fromDTO(listDTO);
    }

    // Добавить комментарий к объявлению
    public CommentsDTO addComment(Long adId, CommentsDTO commentDTO) {
        Ads ads = repositoryAds.findById(adId).orElseThrow(UsersNotFoundException::new);
        if (ads == null) {
            throw new UsersNotFoundException();
        }
        Comments comments = commentDTO.toComments();
        comments.setAds(ads);
        comments.setUsers(ads.getUsers());
        repositoryComments.save(comments);
        return CommentsDTO.fromCommentsDTO(
                repositoryComments.findByText(commentDTO.getText()));
    }

    // Удалить комментарий
    public boolean deleteComment(Long adId, Long commentId) {
        Comments comments = repositoryComments.findByIdAndAdsId(commentId, adId)
                .orElseThrow(UsersNotFoundException::new);
        repositoryComments.delete(comments);
        return true;
    }

    // Обновить комментарий
    public CommentsDTO updateComment(Long adId, Long commentId,
                                     CommentsDTO commentDTO) {
        Comments comments = repositoryComments.findByIdAndAdsId(commentId, adId)
                .orElseThrow(UsersNotFoundException::new);
        comments.setText(commentDTO.getText());
        repositoryComments.save(comments);
        return commentDTO;
    }
}
