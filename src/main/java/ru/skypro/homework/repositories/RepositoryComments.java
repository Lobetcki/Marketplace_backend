package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.skypro.homework.model.Comments;

public interface RepositoryComments extends JpaRepository<Comments, Long>, JpaSpecificationExecutor<Comments> {
}
