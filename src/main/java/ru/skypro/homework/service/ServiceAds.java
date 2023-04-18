package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.AdsFullDTO;
import ru.skypro.homework.dto.adsDTO.CreateAdsDTO;
import ru.skypro.homework.dto.adsDTO.ResponseWrapperAdsDTO;

@Service
public class ServiceAds {

                            // Получить все объявления
    public ru.skypro.homework.dto.adsDTO.ResponseWrapperAdsDTO getAllAds() {
        return null;
    }
                            // Добавить объявление
    public AdsDTO createAd(CreateAdsDTO createAdsDTO, MultipartFile image) {
        return null;
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

