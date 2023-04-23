package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.model.Ads;

import java.util.List;

public interface RepositoryAds extends JpaRepository<Ads, Long> {

    List<Ads> findByTitleContainingIgnoreCase(String text);

    List<Ads> findAllByUsers_Username(String username);

    @Modifying
    @Query(value = "UPDATE ads a SET ad_image_id = ?2 WHERE a.id = ?1", nativeQuery = true)
    void updateAdImage(Long id, Long imageId);

}
