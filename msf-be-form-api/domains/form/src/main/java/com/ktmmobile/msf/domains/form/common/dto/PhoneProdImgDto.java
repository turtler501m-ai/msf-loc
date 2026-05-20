package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhoneProdImgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prodId;          // 상품 id
    private String sntyColorCd;     // 색상type 코드
    private String atribVal;        // 색상tyle text label

    /*
    private List<PhoneProdImgDetailDto> phoneProdImgDetailDtoListReg;

    public List<PhoneProdImgDetailDto> getPhoneProdImgDetailDtoListReg() {
        return phoneProdImgDetailDtoListReg;
    }

    public void setPhoneProdImgDetailDtoListReg(
        List<PhoneProdImgDetailDto> phoneProdImgDetailDtoListReg
    ) {
        this.phoneProdImgDetailDtoListReg = phoneProdImgDetailDtoListReg;
    }
    */

    /*
    public List<PhoneProdImgDetailDto> getPhoneProdImgDetailDtoList() {
        List<PhoneProdImgDetailDto> newList = new ArrayList<PhoneProdImgDetailDto>();

        for (int i = 1; i < 5; i++) {
            PhoneProdImgDetailDto rt = getSntyColorType("0" + i);
            if (rt == null) {
                PhoneProdImgDetailDto newItem = new PhoneProdImgDetailDto();
                newItem.setProdId(prodId);
                newItem.setImgTypeCd("0" + i);
                newItem.setSntyColorCd(sntyColorCd);
                newList.add(newItem);
            } else {
                newList.add(rt);
            }
        }
        return newList;
    }
    */

    /*
    private PhoneProdImgDetailDto getSntyColorType(String imgTypeCd) {
        if (phoneProdImgDetailDtoList != null) {
            for (PhoneProdImgDetailDto t: phoneProdImgDetailDtoList) {
                if (t.getImgTypeCd().equals(imgTypeCd)) {
                    return t;
                }
            }
        }
        return null;
    }
    */

    /*
    public void setPhoneProdImgDetailDtoList(
        List<PhoneProdImgDetailDto> phoneProdImgDetailDtoList
    ) {
        this.phoneProdImgDetailDtoList = phoneProdImgDetailDtoList;
    }
    */
}
