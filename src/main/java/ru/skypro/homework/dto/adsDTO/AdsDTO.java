package ru.skypro.homework.dto.adsDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;

@Data
public class AdsDTO {

    private Long authorId;
    private String imageAdUrl;
    private Long pk;
    private Integer price;
    private String title;

    public static AdsDTO fromDTO(Ads ads){
        AdsDTO adsDTO = new AdsDTO();

        adsDTO.setAuthorId(ads.getUser().getId());
        adsDTO.setPk(ads.getAdId());
        adsDTO.setPrice(ads.getPrice());
        adsDTO.setTitle(ads.getTitle());
        if (ads.getAdImage() == null) {
            Image image = new Image();
            image.setUrl("No image");
            ads.setAdImage(image);
        } else {
            adsDTO.setImageAdUrl(ads.getAdImage().getUrl());
        }
        return adsDTO;
    }
}
