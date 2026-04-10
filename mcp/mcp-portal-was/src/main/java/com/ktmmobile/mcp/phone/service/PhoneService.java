package com.ktmmobile.mcp.phone.service;

import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.phone.dto.CommonSearchDto;
import com.ktmmobile.mcp.phone.dto.PhoneProdBasDto;
import com.ktmmobile.mcp.phone.dto.PhoneSntyBasDto;

public interface PhoneService {

    /**
    * @Description :상품 리스트 페이지 성능개선을 위한 한번의 질의로
    * 리스트값을 가져온다.
    * @param commonSearchDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 4.
    */
    public List<PhoneProdBasDto> listPhoneProdBasForFrontOneQuery(CommonSearchDto commonSearchDto);

    /**
    * @Description :
    * prodId 값으로 상품정보를 가져온다.
    * 상품정보에 속해있는 속성정보와 상품이미정보를 동시에 가져온다.
    * @param phoneProdBasDto
    * @return 상품정보
    * @Author : ant
    * @Create Date : 2016. 1. 8.
    */
    public PhoneProdBasDto findPhoneProdBasDtoByProdId(PhoneProdBasDto phoneProdBasDto);

    /**
    * @Description : 핸드폰 상품 LTE,3G 별 개수를 가져온다.
    *
    * @param commonSearchDto 구분값 파라메터로 중고여부 (shandYn) 값을 필요하다.
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 5.
    */
    public Map<String,Integer> findLte3GphoneCount(CommonSearchDto commonSearchDto);

    List<PhoneSntyBasDto> selectProdBasSnty(PhoneSntyBasDto phoneProdBasDto);
}
