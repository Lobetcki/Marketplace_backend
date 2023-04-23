package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.service.ServiceAds;

import javax.validation.Valid;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class ControllerAds {

    private final ServiceAds serviceAds;

    public ControllerAds(ServiceAds serviceAds) {
        this.serviceAds = serviceAds;
    }

    @GetMapping
    @Operation(
            summary = "Получить все объявления", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseWrapperAdsDTO.class))})
            }
    )
    public ResponseEntity<ResponseWrapperAdsDTO> getAllAds() {
        ResponseWrapperAdsDTO adsDTO = serviceAds.getAllAds();
        return ResponseEntity.ok(adsDTO);
    }

    @GetMapping("/source")
    @Operation(
            summary = "Поиск объявлений по названию", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseWrapperAdsDTO.class))})
            }
    )
    public ResponseEntity<ResponseWrapperAdsDTO> getAdsByTitle(
            @RequestParam() String text) {
        if (text == null || text.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(serviceAds.getAdsByTitle(text));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Добавить объявление", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Created",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreateAdsDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    public ResponseEntity<AdsDTO> createAd(@RequestPart("properties") CreateAdsDTO createAdsDTO,
                                           @Valid
                                           @RequestPart("image") MultipartFile imageFile,
                                           Authentication authentication) {
        AdsDTO adsDTO = serviceAds.createAd(createAdsDTO, imageFile, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(adsDTO);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить информацию об объявлении", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdsFullDTO.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    public ResponseEntity<AdsFullDTO> getAdById(@PathVariable Long id) {
        AdsFullDTO adFullDTO = serviceAds.getAdById(id);
        if (adFullDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adFullDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить объявление", tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            }
    )
    @PreAuthorize("adsServiceImpl.getAdsById(#id).getEmail()" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteAd(@PathVariable Long id,
                                      Authentication authentication) {
        serviceAds.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить информацию об объявлении",tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreateAdsDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PreAuthorize("adsServiceImpl.getAdsById(#id).getEmail()" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    public ResponseEntity<AdsDTO> updateAd(
            @PathVariable Long id,
            @RequestBody CreateAdsDTO createAdsDTO) {
        AdsDTO adsDTO = serviceAds.updateAd(id, createAdsDTO);
        if (adsDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adsDTO);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Получить объявления авторизованного пользователя", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseWrapperAdsDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            }
    )
    public ResponseEntity<ResponseWrapperAdsDTO> getAdsByCurrentUser(
            Authentication authentication
    ) {
        ResponseWrapperAdsDTO adsDTOs = serviceAds.getAdsByCurrentUser(authentication);
        return ResponseEntity.ok(adsDTOs);
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Обновить картинку объявления",tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PreAuthorize("adsServiceImpl.getAdsById(#id).getEmail()" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    public ResponseEntity<byte[]> updateAdImage(@PathVariable("id") Long adid,
                                                @RequestParam("image") MultipartFile imageFile) {

        return ResponseEntity.ok(serviceAds.updateAdImage(adid, imageFile).getBytes());
    }

    // Вернуть аватарку пользователя или картинку объявления
    @GetMapping(value = "/me/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    @Operation(hidden = true)
    public byte[] getImage(@PathVariable("id") Long id) {
        return serviceAds.getImage(id);
    }

}
