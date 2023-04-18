package ru.skypro.homework.dto.adsDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdsDTO {

    private Integer authorId;
    private String imageAdUrl;
    private Long pkAdId;
    private Integer price;
    private String title;

}
