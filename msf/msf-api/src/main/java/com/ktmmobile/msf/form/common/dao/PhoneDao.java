package com.ktmmobile.msf.form.common.dao;

import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.form.common.dto.CommonSearchDto;
import com.ktmmobile.msf.form.common.dto.PhoneMspDto;
import com.ktmmobile.msf.form.common.dto.PhoneProdBasDto;
import com.ktmmobile.msf.form.common.dto.PhoneSntyBasDto;

/*
 * naming rule
 * -prefix
 * 단일객체    find
 * 리스트객체 list
 * 카운트       count
 * sql id 메소드명과 동일하게
 *
*/
/**
 * @Class Name : PhoneDao
 * @Description : 핸드폰관리 DAO I/F
 *
 * @author : ant
 * @Create Date : 2015. 12. 31.
 */
public interface PhoneDao {
    /**
    * @Description : Msp 핸드폰 상품 리스트 조회
    * @param param
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 4.
    */
    public List<PhoneMspDto> listPHoneMspDto(CommonSearchDto param);

    /**
    * @Description : 한개의 상품 정보를 조회 한다.
    * @param phoneProdBasDto 상품 조회 조건
    * 현재 prodId 한개의 조건만 사용중
    * @return PhoneProdBasDto 상품정보
    * @Author : ant
    * @Create Date : 2016. 1. 8.
    */
    public PhoneProdBasDto findPhoneProd(PhoneProdBasDto phoneProdBasDto);


    /**
    * @Description : 상품리스트하고 대표 색상이미지를 가져온다.
    * @param commonSearchDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 4.
    */
    public List<PhoneProdBasDto> listPhoneProdBasForFrontOneQuery(
            CommonSearchDto commonSearchDto);

    /**
    * @Description :
    * @param commonSearchDto
    * @return
    * @Author : ant
    * @Create Date : 2016. 2. 5.
    */
    public Map<String, Integer> findLte3GphoneCount(
            CommonSearchDto commonSearchDto);

    List<PhoneSntyBasDto> selectProdBasSnty(PhoneSntyBasDto phoneProdBasDto);
}
