package ru.skypro.homework.dto.adsDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdsFullDTO {

    private Integer pkAdId;
    private String description;
    private Integer price;
    private String title;

    private String adImageUrl;

    private String loginEmail;
    private String authorFirstName;
    private String authorLastName;
    private String phone;
}
