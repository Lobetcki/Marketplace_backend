package ru.skypro.homework.dto.adsDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseWrapperAdsDTO {
    private Integer countAds;
    private List<AdsDTO> adsDTOList;

    public static ResponseWrapperAdsDTO fromDTO(List<AdsDTO> list) {
        ResponseWrapperAdsDTO result = new ResponseWrapperAdsDTO();
        result.setAdsDTOList(list);
        result.setCountAds(list.size());
        return result;
    }
}
