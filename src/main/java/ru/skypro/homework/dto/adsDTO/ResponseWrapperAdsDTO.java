package ru.skypro.homework.dto.adsDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseWrapperAdsDTO {
    private Integer countAds;
    private List<AdsDTO> adsDTOList;

    public ResponseWrapperAdsDTO() {
        countAds = adsDTOList.size();
    }
}
