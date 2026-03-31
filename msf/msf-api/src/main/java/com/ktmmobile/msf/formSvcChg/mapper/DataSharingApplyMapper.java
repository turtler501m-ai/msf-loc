package com.ktmmobile.msf.formSvcChg.mapper;

import com.ktmmobile.msf.formSvcChg.dto.DataSharingOsstDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 데이터쉐어링 신규 개통 신청서 Mapper.
 * ASIS AppformMapper 의 데이터쉐어링 관련 SQL 을 TOBE 테이블(MSF_*) 기준으로 이식.
 */
@Mapper
public interface DataSharingApplyMapper {

    /**
     * MSF_REQUEST INSERT — 데이터쉐어링 신규개통 신청서 저장.
     * SQ_REQUEST_KEY 로 requestKey 생성 후 params["requestKey"] 반환.
     * params["resNo"] 도 생성됨 (requestKey 를 14자리 좌패딩).
     */
    void insertMsfRequest(Map<String, Object> params);

    /**
     * MSF_REQUEST_OSST INSERT — OSST 연동 초기 레코드 생성.
     */
    void insertMsfRequestOsst(DataSharingOsstDto dto);

    /**
     * MSF_REQUEST_OSST UPDATE — 단계별 진행상태·결과 업데이트.
     */
    void updateMsfRequestOsst(DataSharingOsstDto dto);

    /**
     * MSF_REQUEST_OSST SELECT — requestKey 로 OSST 정보 조회.
     */
    DataSharingOsstDto getMsfRequestOsst(Long requestKey);

    /**
     * MSF_REQUEST SELECT — requestKey 로 신청서 조회.
     */
    Map<String, Object> getMsfRequest(Long requestKey);

    /**
     * MSF_REQUEST UPDATE — 개통번호(OPEN_NO), USIM 등 업데이트.
     */
    void updateMsfRequest(Map<String, Object> params);
}
