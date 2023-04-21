package ru.skypro.homework.dto.adsDTO;

import lombok.Data;
import ru.skypro.homework.model.Ads;

@Data
public class AdsDTO {

    private Long author;
    private String image;
    private Long pk;
    private Integer price;
    private String title;

    public static AdsDTO fromDTO(Ads ads) {
        AdsDTO adsDTO = new AdsDTO();

        adsDTO.setAuthor(ads.getUsers().getId());
        adsDTO.setPk(ads.getId());
        adsDTO.setPrice(ads.getPrice());
        adsDTO.setTitle(ads.getTitle());
        if (ads.getAdImage() == null) {
            adsDTO.setImage("No image");
        } else {
            adsDTO.setImage("/ads/me/image/"
                    + ads.getAdImage().getId());
        }
        return adsDTO;
    }
}
