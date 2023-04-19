package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.AdsFullDTO;
import ru.skypro.homework.dto.adsDTO.CreateAdsDTO;
import ru.skypro.homework.dto.adsDTO.ResponseWrapperAdsDTO;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.repositories.RepositoryAds;
import ru.skypro.homework.repositories.RepositoryUsers;

import java.util.stream.Collectors;

@Service
public class ServiceAds {

    private final RepositoryAds repositoryAds;
    private final RepositoryUsers repositoryUsers;

    public ServiceAds(RepositoryAds repositoryAds, RepositoryUsers repositoryUsers) {
        this.repositoryAds = repositoryAds;
        this.repositoryUsers = repositoryUsers;
    }

    // Получить все объявления
    public ResponseWrapperAdsDTO getAllAds() {
        ResponseWrapperAdsDTO wrAdsDTO = new ResponseWrapperAdsDTO();
        wrAdsDTO.setAdsDTOList(repositoryAds.findAll()
                .stream().map(AdsDTO::fromDTO)
                .collect(Collectors.toList()));
        return wrAdsDTO;
    }
                            // Добавить объявление
    public AdsDTO createAd(Authentication authentication,
                           CreateAdsDTO createAdsDTO //, Image image
    ) {
        Ads ads = createAdsDTO.toAds();
//        ads.setAdImage(image);
        ads.setUser(repositoryUsers.findByUsername(authentication.getName()));

        return null;
    }

    // Поиск объявлений по названию
    public ResponseWrapperAdsDTO getAdsByTitle(String text) {
        ResponseWrapperAdsDTO wrAdsDTO = new ResponseWrapperAdsDTO();
        wrAdsDTO.setAdsDTOList(repositoryAds.findByTitleContainingIgnoreCase(text)
                .stream().map(AdsDTO::fromDTO)
                .collect(Collectors.toList()));
        return wrAdsDTO;
    }

    // Получить информацию об объявлении

    public AdsFullDTO getAdById(Long id) {
        return null;
    }
    // Удалить объявление

    public boolean deleteAd(Long id) {
        return false;
    }
    // Обновить информацию об объявлении

    public AdsDTO updateAd(Long id, CreateAdsDTO createAdsDTO) {
        return null;
    }
    // Получить объявления авторизованного пользователя

    public ResponseWrapperAdsDTO getAdsByCurrentUser() {
        return null;
    }
    // Обновить картинку объявления

    public byte[] updateAdImage(Long id, MultipartFile image) {
        return null;
    }
}

