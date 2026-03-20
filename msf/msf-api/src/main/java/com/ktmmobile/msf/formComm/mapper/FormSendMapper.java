package com.ktmmobile.msf.formComm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 서식지 SCAN 전송용 DB 접근 Mapper.
 * ASIS: CustRequestDao 에서 FormSend 관련 메서드 포팅.
 */
@Mapper
public interface FormSendMapper {

    /**
     * 명의변경 서식지 기본정보 조회.
     * ASIS: CustRequestDao.getReqNmChgData(custRequestSeq)
     * FROM: MSF_CUST_REQUEST_MST(A) + MSF_REQUEST_NAME_CHG(B) + MSF_REQUEST_NAME_TRNS(C)
     */
    Map<String, Object> getReqNmChgData(@Param("custReqSeq") Long custReqSeq);

    /**
     * 해지상담 서식지 기본정보 조회.
     * ASIS: CustRequestDao.getCancelConsultData(custRequestSeq)
     * FROM: MSF_CUST_REQUEST_MST(A) + NMCP_NFL_CNCL_APY(B)
     */
    Map<String, Object> getCancelConsultData(@Param("custReqSeq") Long custReqSeq);

    /**
     * 서식지 그룹별 좌표 정보 조회.
     * ASIS: CustRequestDao.getAppFormPointList(groupCode)
     * FROM: MSF_APP_FORM_INFO(A) + MSF_APP_FORM_MST(B)
     */
    List<Map<String, Object>> getAppFormPointList(@Param("groupCode") String groupCode);

    /**
     * 명의변경 테이블 SCAN_ID 업데이트.
     * ASIS: CustRequestDao.updateReqNmChgScanId()
     */
    int updateNameChgScanId(@Param("scanId") String scanId, @Param("custReqSeq") Long custReqSeq);

    /**
     * 해지 테이블 SCAN_ID 업데이트.
     * ASIS: CustRequestDao.updateCancelReqScanId()
     */
    int updateCancelScanId(@Param("scanId") String scanId, @Param("custReqSeq") Long custReqSeq);
}
