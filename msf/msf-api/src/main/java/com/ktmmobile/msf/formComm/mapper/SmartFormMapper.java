package com.ktmmobile.msf.formComm.mapper;

import com.ktmmobile.msf.formComm.dto.SmartFormOsstDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 데이터쉐어링 신규 개통 신청서 저장 MyBatis 매퍼.
 * MSF_REQUEST_SVC_CHG + MSF_REQUEST_OSST 를 사용.
 * ASIS AppformDao (insertMcpRequestOsst / getRequestOsst / requestOsstCount 등) 와 동일.
 */
@Mapper
public interface SmartFormMapper {

    /**
     * MSF_REQUEST_SVC_CHG 데이터쉐어링 신청 INSERT.
     * params 에 ncn, custId, agentCode, cntpntShopId, reqUsimSn 포함.
     * 결과: params["requestKey"] 에 PK 반환.
     */
    void insertShareDataRequest(Map<String, Object> params);

    /**
     * MSF_REQUEST_OSST INSERT.
     */
    void insertMsfRequestOsst(SmartFormOsstDto dto);

    /**
     * MSF_REQUEST_OSST 조회 (requestKey 기준).
     */
    SmartFormOsstDto getMsfRequestOsst(@Param("requestKey") Long requestKey);

    /**
     * MSF_REQUEST_OSST UPDATE (prgrStatCd, rsltCd, osstOrdNo 등).
     */
    void updateMsfRequestOsst(SmartFormOsstDto dto);

    /**
     * MSF_REQUEST_OSST COUNT (requestKey + prgrStatCd 조건).
     * ASIS requestOsstCount 와 동일.
     */
    int requestOsstCount(SmartFormOsstDto dto);

    /**
     * MSF_REQUEST_SVC_CHG 조회 (requestKey 기준).
     * 모회선 NCN (SVC_CNTR_NO), agentCode 등 조회용.
     */
    Map<String, Object> getShareDataRequest(@Param("requestKey") Long requestKey);

    /**
     * MSF_REQUEST_SVC_CHG 완료 처리 UPDATE (PROC_CD, OPEN_NO 등).
     */
    void updateShareDataRequest(Map<String, Object> params);
}
