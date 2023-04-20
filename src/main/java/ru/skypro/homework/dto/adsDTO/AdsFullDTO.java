package ru.skypro.homework.dto.adsDTO;

import lombok.Data;
import ru.skypro.homework.model.Ads;

@Data
public class AdsFullDTO {

    private Long pk;
    private String description;
    private Integer price;
    private String title;

    private String adImageUrl;

    private String username;
    private String authorFirstName;
    private String authorLastName;
    private String phone;

    public static AdsFullDTO fromAdsFullDTO(Ads ads) {
        AdsFullDTO adsFullDTO = new AdsFullDTO();

        adsFullDTO.setPk(ads.getAdId());
        adsFullDTO.setDescription(ads.getDescription());
        adsFullDTO.setPrice(ads.getPrice());
        adsFullDTO.setTitle(ads.getTitle());
        if (ads.getAdImage() == null) {
            adsFullDTO.setAdImageUrl("No image");
        } else {
            adsFullDTO.setAdImageUrl(ads.getAdImage().getUrl());
        }
        adsFullDTO.setUsername(ads.getUser().getUsername());
        adsFullDTO.setAuthorFirstName(ads.getUser().getFirstName());
        adsFullDTO.setAuthorLastName(ads.getUser().getLastName());
        adsFullDTO.setPhone(ads.getUser().getPhone());

        return adsFullDTO;
    }
}
