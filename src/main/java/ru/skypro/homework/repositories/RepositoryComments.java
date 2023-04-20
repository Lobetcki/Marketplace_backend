package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Comments;

import java.util.List;

public interface RepositoryComments extends JpaRepository<Comments, Long> {
    List<Comments> findAllByAdsId(Long id);

    boolean deleteByIdAndAdsId(Long commentId, Long adId);
}
