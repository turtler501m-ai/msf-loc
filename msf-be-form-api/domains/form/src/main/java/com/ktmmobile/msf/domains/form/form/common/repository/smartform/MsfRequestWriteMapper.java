package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCancelVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDocVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestJoinFormVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestOsstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestRecVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestStateVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgDtlVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;

@AutoAuditing
@Mapper
public interface MsfRequestWriteMapper {

    int insertMsfRequestCancel(MsfRequestCancelVo vo);

    int insertMsfRequestCstmr(MsfRequestCstmrVo vo);

    int insertMsfRequestAgent(MsfRequestAgentVo vo);

    int insertMsfRequestAddition(MsfRequestAdditionVo vo);

    int insertMsfRequestBillReq(MsfRequestBillReqVo vo);

    int insertMsfRequestDoc(MsfRequestDocVo vo);

    int insertMsfRequestDvcChg(MsfRequestDvcChgVo vo);

    int insertMsfRequestJoinForm(MsfRequestJoinFormVo vo);

    int insertMsfRequestMove(MsfRequestMoveVo vo);

    int insertMsfRequestNameChg(MsfRequestNameChgVo vo);

    int insertMsfRequestNameTrns(MsfRequestNameChgVo vo);

    int insertMsfRequestRec(MsfRequestRecVo vo);

    int insertMsfRequestSale(MsfRequestSaleVo vo);

    int insertMsfRequestState(MsfRequestStateVo vo);

    @AutoAuditing(value = true)
    int insertMsfRequestSvcChgDtl(MsfRequestSvcChgDtlVo vo);

    @AutoAuditing(value = true)
    int insertMsfRequestSvcChg(MsfRequestSvcChgVo vo);

    int updateMsfRequestSvcChgProcCd(MsfRequestSvcChgVo vo);

    /** 서비스변경 지연 업로드: MSF_REQUEST_SVC_CHG.SCAN_ID 업데이트 */
    int updateMsfRequestSvcChgScanId(MsfRequestSvcChgVo vo);

    /** 서비스변경 지연 업로드: MSF_REQUEST_SVC_CHG_DTL.SCAN_ID 업데이트 (SVC_TGT_CD별) */
    int updateMsfRequestSvcChgDtlScanId(MsfRequestSvcChgDtlVo vo);

    /** 서비스변경 지연 업로드: MSF_REQUEST_SVC_CHG_DTL.SCAN_ID 업데이트 (SVC_TGT_CD + SOC_CD별) */
    int updateMsfRequestSvcChgDtlSocScanId(MsfRequestSvcChgDtlVo vo);

    /** 서비스변경 상세 처리결과 업데이트 */
    int updateMsfRequestSvcChgDtlProcResult(MsfRequestSvcChgDtlVo vo);

    int insertMsfRequest(MsfRequestVo vo);

    int deleteMsfRequestNameChg(Long requestKey);

    int deleteMsfRequestNameTrns(Long requestKey);

    int deleteMsfRequestCstmr(Long requestKey);

    int deleteMsfRequestAgent(Long requestKey);

    int deleteMsfRequestBillReq(Long requestKey);

    int deleteMsfRequestJoinForm(Long requestKey);

    int deleteMsfRequestDoc(Long requestKey);

    int deleteMsfRequestRec(Long requestKey);

    int insertMsfRequestOsst(MsfRequestOsstVo vo);
}
