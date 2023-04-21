package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Image;

public interface RepositoryImage extends JpaRepository<Image, String> {

}
