package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Ads;

import java.util.List;

public interface RepositoryAds extends JpaRepository<Ads, Long> {

    List<Ads> findByTitleContainingIgnoreCase(String text);

    Ads findByTitle(String title);

    List<Ads> findAllByUsers_Username(String username);
}
