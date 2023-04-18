package ru.skypro.homework.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @GetMapping
    public ResponseEntity<ResponseWrapperAdsDTO> getAllAds() {
        ResponseWrapperAdsDTO adsDTO = serviceAds.getAllAds();
        return ResponseEntity.ok(adsDTO);
    }

    // Добавить объявление
    @PostMapping
    public ResponseEntity<AdsDTO> createAd(
            @RequestBody CreateAdsDTO createAdsDTO,
            @RequestPart("image") MultipartFile image) {
        AdsDTO adsDTO = serviceAds.createAd(createAdsDTO, image);
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
    public ResponseEntity deleteAd(@PathVariable Long id) {
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
    public ResponseEntity<ResponseWrapperAdsDTO> getAdsByCurrentUser() {
        ResponseWrapperAdsDTO adsDTOs = serviceAds.getAdsByCurrentUser();
        return ResponseEntity.ok(adsDTOs);
    }


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
