package ru.skypro.homework.dto.adsDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseWrapperAdsDTO {
    private Integer count;
    private List<AdsDTO> results;

    public static ResponseWrapperAdsDTO fromDTO(List<AdsDTO> list) {
        ResponseWrapperAdsDTO result = new ResponseWrapperAdsDTO();
        result.setResults(list);
        result.setCount(list.size());
        return result;
    }
}
