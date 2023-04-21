package ru.skypro.homework.dto.adsDTO;

import lombok.Data;
import ru.skypro.homework.model.Ads;

@Data
public class AdsFullDTO {

    private Long pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;

    private String image;

    private String phone;
    private Integer price;
    private String title;

    public static AdsFullDTO fromAdsFullDTO(Ads ads) {
        AdsFullDTO adsFullDTO = new AdsFullDTO();

        adsFullDTO.setPk(ads.getId());
        adsFullDTO.setDescription(ads.getDescription());
        adsFullDTO.setPrice(ads.getPrice());
        adsFullDTO.setTitle(ads.getTitle());
        if (ads.getAdImage() == null) {
            adsFullDTO.setImage("No image");
        } else {
            adsFullDTO.setImage("/ads/me/image/"
                    + ads.getAdImage().getId());
        }
        adsFullDTO.setEmail(ads.getUsers().getUsername());
        adsFullDTO.setAuthorFirstName(ads.getUsers().getFirstName());
        adsFullDTO.setAuthorLastName(ads.getUsers().getLastName());
        adsFullDTO.setPhone(ads.getUsers().getPhone());
        return adsFullDTO;
    }
}
