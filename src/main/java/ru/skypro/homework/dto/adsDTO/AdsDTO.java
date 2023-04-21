package ru.skypro.homework.dto.adsDTO;

import lombok.Data;
import ru.skypro.homework.model.Ads;

@Data
public class AdsDTO {

    private Long authorId;
    private String imageAdUrl;
    private Long pk;
    private Integer price;
    private String title;

    public static AdsDTO fromDTO(Ads ads) {
        AdsDTO adsDTO = new AdsDTO();

        adsDTO.setAuthorId(ads.getUsers().getId());
        adsDTO.setPk(ads.getId());
        adsDTO.setPrice(ads.getPrice());
        adsDTO.setTitle(ads.getTitle());
        if (ads.getAdImage() == null) {
            adsDTO.setImageAdUrl("No image");
        } else {
            adsDTO.setImageAdUrl("https://avatar/"
                    + ads.getAdImage().getUrl());
        }
        return adsDTO;
    }
}
