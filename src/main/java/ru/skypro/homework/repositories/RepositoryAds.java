package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.skypro.homework.model.Ads;
public interface RepositoryAds extends JpaRepository<Ads, Long>, JpaSpecificationExecutor<Ads> {

}
