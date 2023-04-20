package ru.skypro.homework.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.AdsFullDTO;
import ru.skypro.homework.dto.adsDTO.CreateAdsDTO;
import ru.skypro.homework.dto.adsDTO.ResponseWrapperAdsDTO;
import ru.skypro.homework.service.ServiceAds;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class ControllerAds {

    private final ServiceAds serviceAds;

    public ControllerAds(ServiceAds serviceAds) {
        this.serviceAds = serviceAds;
    }

    // Получить все объявления
//    @GetMapping
//    public ResponseEntity<ResponseWrapperAdsDTO> getAllAds() {
//        ResponseWrapperAdsDTO adsDTO = serviceAds.getAllAds();
//        return ResponseEntity.ok(adsDTO);
//    }

    // Поиск объявлений по названию
    @GetMapping
    public ResponseEntity<ResponseWrapperAdsDTO> getAdsByTitle(
                                @RequestParam() String text) {
        if (text == null || text.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(serviceAds.getAdsByTitle(text));
    }

    // Добавить объявление
    @PostMapping//(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDTO> createAd(
            @RequestBody CreateAdsDTO createAdsDTO,
            //@RequestParam("image") MultipartFile image,
            Authentication authentication) {
        AdsDTO adsDTO = serviceAds.createAd(authentication, createAdsDTO//, image
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(adsDTO);
    }

    // Получить информацию об объявлении
    @GetMapping("/{id}")
    public ResponseEntity<AdsFullDTO> getAdById(@PathVariable Long id) {
        AdsFullDTO adFullDTO = serviceAds.getAdById(id);
        if (adFullDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adFullDTO);
    }

    // Удалить объявление
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Long id) {
        serviceAds.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    // Обновить информацию об объявлении
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDTO> updateAd(
            @PathVariable Long id,
            @RequestBody CreateAdsDTO createAdsDTO) {
        AdsDTO adsDTO = serviceAds.updateAd(id, createAdsDTO);
        if (adsDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adsDTO);
    }

    // Получить объявления авторизованного пользователя
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDTO> getAdsByCurrentUser(
            Authentication authentication
        ){
        ResponseWrapperAdsDTO adsDTOs = serviceAds.getAdsByCurrentUser(authentication);
        return ResponseEntity.ok(adsDTOs);
    }

    // Обновить картинку объявления
    @PatchMapping("/{id}/image")
    public ResponseEntity<byte[]> updateAdImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image) {
        byte[] updatedImage = serviceAds.updateAdImage(id, image);
        if (updatedImage == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(updatedImage, headers, HttpStatus.OK);
    }
}
