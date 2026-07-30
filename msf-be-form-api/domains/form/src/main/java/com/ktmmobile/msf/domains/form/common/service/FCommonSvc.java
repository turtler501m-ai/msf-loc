package com.ktmmobile.msf.domains.form.common.service;

import java.util.List;

import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : FCommonSvc.java
 * 날짜     : 2016. 1. 25. 오후 5:11:45
 * 작성자   : papier
 * 설명     : 공용 @Service
 * </pre>
 */
public interface FCommonSvc {

    /**
     * <pre>
     * 설명     : 공통코드 Cahe 처리
     * @param nmcpCdDtlDto
     * @return
     * @return: List<NmcpCdDtlDto>
     * </pre>
     */
    void getCodeCahe();

    /**
     * <pre>
     * 설명     : 공통코드 조회 한다.
     * @param nmcpCdDtlDto
     * @return
     * @return: List<NmcpCdDtlDto>
     * </pre>
     */
    List<NmcpCdDtlDto> getCodeList(NmcpCdDtlDto nmcpCdDtlDto) ;

    /**
     * <pre>
     * 설명     : 코드명 조회
     * @param nmcpCdDtlDto
     * @return
     * @return: NmcpCdDtlDto
     * </pre>
     */
    NmcpCdDtlDto getCodeNm(NmcpCdDtlDto nmcpCdDtlDto) ;


    /**
     * <pre>
     * 설명     : 요금제 정보 조회
     * @param rateCd
     * @return
     * @return: MspRateMstDto
     * </pre>
     */
    MspRateMstDto getMspRateMst(String rateCd) ;

    /**
     * <pre>
     * 설명 : 상세코드 조회 (사용여부/기간 조건없이 조회)
     * @param cdGroupId
     * @return List<NmcpCdDtlDto>
     * </pre>
     */
    List<NmcpCdDtlDto> getAllDtlCdList(String cdGroupId);
}
