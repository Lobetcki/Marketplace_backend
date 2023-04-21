package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.AdsFullDTO;
import ru.skypro.homework.dto.adsDTO.CreateAdsDTO;
import ru.skypro.homework.dto.adsDTO.ResponseWrapperAdsDTO;
import ru.skypro.homework.service.ServiceAds;

import javax.validation.Valid;
import java.io.IOException;

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

    // Поиск объявлений по названию
    @GetMapping("/source")
    public ResponseEntity<ResponseWrapperAdsDTO> getAdsByTitle(
            @RequestParam() String text) {
        if (text == null || text.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(serviceAds.getAdsByTitle(text));
    }

    // Добавить объявление
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDTO> createAd(@RequestPart("properties") CreateAdsDTO createAdsDTO,
                                           @Valid
                                           @RequestPart("image") MultipartFile imageFile,
                                           Authentication authentication) throws IOException {
        AdsDTO adsDTO = serviceAds.createAd(createAdsDTO, imageFile, authentication);
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
    @PreAuthorize("adsServiceImpl.getAdsById(#id).getEmail()" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
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
    ) {
        ResponseWrapperAdsDTO adsDTOs = serviceAds.getAdsByCurrentUser(authentication);
        return ResponseEntity.ok(adsDTOs);
    }

    // Обновить картинку объявления
    @PreAuthorize("adsServiceImpl.getAdsById(#id).getEmail()" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateAdImage(@PathVariable Long adid,
                                                @RequestParam MultipartFile imageFile) {

        return ResponseEntity.ok(serviceAds.updateAdImage(adid, imageFile).getBytes());
    }

    // Вернуть аватарку объявления
    @GetMapping(value = "/me/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(@PathVariable("id") Long id) throws IOException {
        return serviceAds.getImage(id);
    }

}
