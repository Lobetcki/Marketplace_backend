package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.skypro.homework.model.Ads;

import java.util.List;

public interface RepositoryAds extends JpaRepository<Ads, Long>, JpaSpecificationExecutor<Ads> {

    // Поиск объявлений по названию
    List<Ads> findByTitleContainingIgnoreCase(String text);

    Ads findByTitle(String title);
}
