package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.formComm.dto.AgencyShopResVO;
import com.ktmmobile.msf.formComm.dto.CardCheckReqDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

import java.util.List;
import java.util.Map;

/**
 * 업무공통 서비스 인터페이스 — 인터페이스 연계 없는 공통 기능.
 * M플랫폼/외부 API 연동이 없는 내부 로직·DB 조회만 담당.
 * 인터페이스 연계 서비스(Y04/X01/X85/NICE 등)는 SvcChgInfoSvc 참조.
 */
public interface FormCommSvc {

    /**
     * 대리점/판매점 목록 조회.
     * 서비스변경·명의변경·서비스해지 전 업무 공용.
     * LOCAL: Mock 반환 / 운영: MSF_AGENT_SHOP_INFO 테이블 조회.
     */
    List<AgencyShopResVO> getAgencyList();

    /**
     * IF_0007 카드번호 유효성 체크.
     * ASIS myNameChg.js checkCardNumber() Luhn Algorithm + 유효기간 검증을 서버사이드로 이전.
     * birthDate + custNm 입력 시 X91 M플랫폼 카드 실인증 추가 수행.
     */
    Map<String, Object> checkCard(CardCheckReqDto req);

    /**
     * N7003 — 개통일자 조회.
     * MSP_JUO_SUB_INFO(LST_COM_ACTV_DATE) 직접 조회. 공통 업무.
     * LOCAL: Mock 반환 / 운영: DB 조회 후 yyyy.MM.dd 포맷 변환.
     * @param req ncn(계약번호) 포함 요청 DTO
     * @return { success, activationDate(yyyy.MM.dd), activationDateRaw(YYYYMMDD) }
     */
    Map<String, Object> getActivationDate(SvcChgInfoDto req);

    /**
     * N7002 — 접점코드(ORGN_ID)로 대리점 코드·정보 조회.
     * ASIS: AppformMapper.xml selectAgentCode (ORG_ORGN_INFO_MST@DL_MSP).
     * LOCAL: Mock 반환 / 운영: DB 조회.
     * @param cntpntShopId 접점코드 (ORGN_ID)
     * @return Map (KT_ORG_ID, ORGN_NM, ROADN_ADR_ZIPCD, ROADN_ADR_BAS_SBST, ROADN_ADR_DTL_SBST)
     */
    Map<String, Object> getAgentCode(String cntpntShopId);
}
