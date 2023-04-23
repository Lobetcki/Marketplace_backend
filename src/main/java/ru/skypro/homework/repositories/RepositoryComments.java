package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Comments;

import java.util.List;
import java.util.Optional;

public interface RepositoryComments extends JpaRepository<Comments, Long> {
    List<Comments> findAllByAdsId(Long id);

    Optional<Comments> findByIdAndAdsId(Long id, Long adId);
}
