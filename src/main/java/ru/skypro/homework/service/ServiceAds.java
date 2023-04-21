package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.AdsFullDTO;
import ru.skypro.homework.dto.adsDTO.CreateAdsDTO;
import ru.skypro.homework.dto.adsDTO.ResponseWrapperAdsDTO;
import ru.skypro.homework.exception.MarketNotFoundException;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repositories.RepositoryAds;
import ru.skypro.homework.repositories.RepositoryImage;
import ru.skypro.homework.repositories.RepositoryUsers;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceAds {

    private final RepositoryAds repositoryAds;
    private final RepositoryUsers repositoryUsers;
    private final RepositoryImage repositoryImage;

    public ServiceAds(RepositoryAds repositoryAds,
                      RepositoryUsers repositoryUsers,
                      RepositoryImage repositoryImage) {
        this.repositoryAds = repositoryAds;
        this.repositoryUsers = repositoryUsers;
        this.repositoryImage = repositoryImage;
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
    public AdsDTO createAd(CreateAdsDTO createAdsDTO,
                           MultipartFile imageFile,
                           Authentication authentication) {
        try {
            Ads ads = createAdsDTO.toAds();
//            Image imageAds = serviceImage.toImage(image);
            Image image = new Image();
            image.setBytes(imageFile.getBytes());
            repositoryImage.save(image);
            ads.setAdImage(image);
            ads.setUsers(repositoryUsers.findByUsernameIgnoreCase("Asd@Asd.com"
                    // authentication.getName()
            ));
            repositoryAds.save(ads);
            System.out.println(ads.getId());
//            ads = repositoryAds.findByTitle(createAdsDTO.getTitle());
            return AdsDTO.fromDTO(ads);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Поиск объявлений по названию
    public ResponseWrapperAdsDTO getAdsByTitle(String text) {
        List<AdsDTO> list = repositoryAds.findByTitleContainingIgnoreCase(text)
                .stream().map(AdsDTO::fromDTO)
                .collect(Collectors.toList());
        return ResponseWrapperAdsDTO.fromDTO(list);
    }

    // Получить информацию об объявлении
    public AdsFullDTO getAdById(Long id) {
        return AdsFullDTO.fromAdsFullDTO(
                repositoryAds.findById(id).orElseThrow(MarketNotFoundException::new));
    }

    // Удалить объявление
    public void deleteAd(Long id) {
        repositoryAds.deleteById(id);
    }

    // Обновить информацию об объявлении
    public AdsDTO updateAd(Long id, CreateAdsDTO createAdsDTO) {
        Ads ads = repositoryAds.findById(id).orElseThrow(MarketNotFoundException::new);
        ads.setDescription(createAdsDTO.getDescription());
        ads.setPrice(createAdsDTO.getPrice());
        ads.setTitle(createAdsDTO.getTitle());
        repositoryAds.save(ads);
        return AdsDTO.fromDTO(ads);
    }

    // Получить объявления авторизованного пользователя
    public ResponseWrapperAdsDTO getAdsByCurrentUser(
            Authentication authentication) {
//        Users user = repositoryUsers.findByUsername("Asd@Asd.com");

        List<AdsDTO> adsList = repositoryAds.findAllByUsers_Username("Asd@Asd.com")
                .stream().map(AdsDTO::fromDTO)
                .collect(Collectors.toList());
        return ResponseWrapperAdsDTO.fromDTO(adsList);
    }

    // Обновить картинку объявления
    public byte[] updateAdImage(Long id, MultipartFile imageFile) {
        try {
            Ads ads = repositoryAds.findById(id).orElseThrow(MarketNotFoundException::new);
            repositoryImage.delete(ads.getAdImage());
            Image image = new Image();
            image.setBytes(imageFile.getBytes());
            repositoryImage.save(image);
//            Long imageUrl = ;
            ads.setAdImage(image);
            repositoryAds.save(ads);

            return image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Возврат фото
    public byte[] getImage(Long id) {
        return repositoryImage.findById(id)
                .orElseThrow(MarketNotFoundException::new).getBytes();
    }
}

