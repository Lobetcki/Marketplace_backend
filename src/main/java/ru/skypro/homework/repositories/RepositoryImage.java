package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Image;

import java.util.Optional;

public interface RepositoryImage extends JpaRepository<Image, String> {

    Optional<Image> findById(Long id);

    void deleteAllByBytes(byte[] image);
}
