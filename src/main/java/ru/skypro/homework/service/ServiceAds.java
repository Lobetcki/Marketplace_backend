package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.AdsFullDTO;
import ru.skypro.homework.dto.adsDTO.CreateAdsDTO;
import ru.skypro.homework.dto.adsDTO.ResponseWrapperAdsDTO;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.exception.UsersNotFoundException;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repositories.RepositoryAds;
import ru.skypro.homework.repositories.RepositoryUsers;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceAds {

    private final RepositoryAds repositoryAds;
    private final RepositoryUsers repositoryUsers;

    public ServiceAds(RepositoryAds repositoryAds, RepositoryUsers repositoryUsers) {
        this.repositoryAds = repositoryAds;
        this.repositoryUsers = repositoryUsers;
    }

//    // Получить все объявления
//    public ResponseWrapperAdsDTO getAllAds() {
//        ResponseWrapperAdsDTO wrAdsDTO = new ResponseWrapperAdsDTO();
//        wrAdsDTO.setAdsDTOList(repositoryAds.findAll()
//                .stream().map(AdsDTO::fromDTO)
//                .collect(Collectors.toList()));
//        return wrAdsDTO;
//    }
                            // Добавить объявление
    public AdsDTO createAd(Authentication authentication,
                           CreateAdsDTO createAdsDTO//, MultipartFile image
    ) {
        Ads ads = createAdsDTO.toAds();
        ads.setAdImage(null);
        ads.setUser(repositoryUsers.findByUsername("Asd@Asd.com"
               // authentication.getName()
                                                    ));
        repositoryAds.save(ads);
        ads = repositoryAds.findByTitle(createAdsDTO.getTitle());
        return AdsDTO.fromDTO(ads);
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
                repositoryAds.findById(id).orElseThrow(UsersNotFoundException::new));
    }

    // Удалить объявление
    public void deleteAd(Long id) {
        repositoryAds.deleteById(id);
    }

    // Обновить информацию об объявлении
    public AdsDTO updateAd(Long id, CreateAdsDTO createAdsDTO) {
        Ads ads = repositoryAds.findById(id).orElseThrow(UsersNotFoundException::new);
        ads.setDescription(createAdsDTO.getDescription());
        ads.setPrice(createAdsDTO.getPrice());
        ads.setTitle(createAdsDTO.getTitle());
        repositoryAds.save(ads);
        return AdsDTO.fromDTO(ads);
    }

    // Получить объявления авторизованного пользователя

    public ResponseWrapperAdsDTO getAdsByCurrentUser(
            Authentication authentication){
        Users user = repositoryUsers.findByUsername("Asd@Asd.com");

        List<AdsDTO> adsList = repositoryAds.findByUserId(user.getId())
                .stream().map(AdsDTO::fromDTO)
                .collect(Collectors.toList());
        return ResponseWrapperAdsDTO.fromDTO(adsList);
    }


    // Обновить картинку объявления

    public byte[] updateAdImage(Long id, MultipartFile image) {
        return null;
    }
}

