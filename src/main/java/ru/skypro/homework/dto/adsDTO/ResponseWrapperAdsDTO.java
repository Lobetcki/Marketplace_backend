package ru.skypro.homework.dto.adsDTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.model.Ads;

import java.util.ArrayList;
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
