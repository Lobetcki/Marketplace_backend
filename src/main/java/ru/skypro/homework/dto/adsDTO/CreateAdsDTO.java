package ru.skypro.homework.dto.adsDTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.model.Ads;

@Getter
@Setter
public class CreateAdsDTO {

    private String description;
    private Integer price;
    private String title;

    public Ads toAds() {
        Ads ads = new Ads();
        ads.setDescription(this.getDescription());
        ads.setPrice(this.getPrice());
        ads.setTitle(this.getTitle());
        return ads;
    }



}
