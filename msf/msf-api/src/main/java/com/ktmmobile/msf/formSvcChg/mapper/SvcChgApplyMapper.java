package com.ktmmobile.msf.formSvcChg.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 서비스변경 신청서 저장 MyBatis 매퍼.
 * MSF_REQUEST_SVC_CHG (마스터) + MSF_REQUEST_SVC_CHG_DTL (상세) INSERT.
 */
@Mapper
public interface SvcChgApplyMapper {

    /**
     * 서비스변경 신청 마스터 INSERT.
     * params: custType, managerCd, agentCd, ctn, memo, name
     * 결과: params["requestKey"] 에 생성된 REQUEST_KEY 반환 (selectKey)
     */
    void insertSvcChgRequest(Map<String, Object> params);

    /**
     * 서비스변경 신청 상세 INSERT.
     * params: requestKey, svcTgtCd, svcChgTypeCd, procTypeCd,
     *         prodId, prodNm, addtionInfo, reqUsimSn, chgMobileNo, releasePwd
     * 결과: params["dtlSeq"] 에 생성된 시퀀스 반환 (selectKey)
     */
    void insertSvcChgRequestDtl(Map<String, Object> params);
}
