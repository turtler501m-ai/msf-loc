package com.ktmmobile.msf.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * @Class Name : PhoneProdImgDto
 * @Description : 상품의 색상별 이미지 Dto
 * 상품의 측면별(앞,뒤,옆,모바일) 이미지파일정보를 리스트형 멤버 필드로 가지고있다.
 *
 * @author : ant
 * @Create Date : 2016. 1. 6.
 */
public class PhoneProdImgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 상품 id */
    private String prodId;

    /** 색상type 코드  */
    private String sntyColorCd;

    /** 색상tyle text label */
    private String atribVal;

    /** 색상별 상세 */
    private List<PhoneProdImgDetailDto> phoneProdImgDetailDtoList;

    /** 색상별 상세 등록시 */
    private List<PhoneProdImgDetailDto> phoneProdImgDetailDtoListReg;

    public List<PhoneProdImgDetailDto> getPhoneProdImgDetailDtoListReg() {
        return phoneProdImgDetailDtoListReg;
    }

    public void setPhoneProdImgDetailDtoListReg(
            List<PhoneProdImgDetailDto> phoneProdImgDetailDtoListReg) {
        this.phoneProdImgDetailDtoListReg = phoneProdImgDetailDtoListReg;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getSntyColorCd() {
        return sntyColorCd;
    }

    public void setSntyColorCd(String sntyColorCd) {
        this.sntyColorCd = sntyColorCd;
    }

    public String getAtribVal() {
        return atribVal;
    }

    public void setAtribVal(String atribVal) {
        this.atribVal = atribVal;
    }

    /**
    * @Description : 상품측면별 이미지가 존재하지 않더라도 빈 오브젝트를 생성해서 채워준다.
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 8.
    */
    public List<PhoneProdImgDetailDto> getPhoneProdImgDetailDtoList() {
        List<PhoneProdImgDetailDto> newList = new ArrayList<PhoneProdImgDetailDto>();

        for (int i = 1; i < 5; i++) {
            PhoneProdImgDetailDto rt = getSntyColorType("0"+i);
            if (rt == null) {
                PhoneProdImgDetailDto newItem = new PhoneProdImgDetailDto();
                newItem.setProdId(prodId);
                newItem.setImgTypeCd("0"+i);
                newItem.setSntyColorCd(sntyColorCd);
                newList.add(newItem);
            } else {
                newList.add(rt);
            }
        }
        return newList;
    }

    /**
    * @Description : 현재 phoneProdImgDetailDtoList 필드에 값중에서
    * 요청들어온 이미지사이드측면이 존재할경우 해당 값을 리턴한다.
    * @param imgTypeCd
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 8.
    */
    private PhoneProdImgDetailDto getSntyColorType(String imgTypeCd) {
        if (phoneProdImgDetailDtoList != null) {
            for (PhoneProdImgDetailDto t : phoneProdImgDetailDtoList) {
                if (t.getImgTypeCd().equals(imgTypeCd)) {
                    return t;
                }
            }
        }
        return null;
    }

    public void setPhoneProdImgDetailDtoList(
            List<PhoneProdImgDetailDto> phoneProdImgDetailDtoList) {
        this.phoneProdImgDetailDtoList = phoneProdImgDetailDtoList;
    }

}
