package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCancelVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestClauseVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDocVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestJoinFormVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameTrnsVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestRecVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestStateVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgDtlVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;

@Mapper
public interface MsfRequestWriteMapper {

    int insertMsfRequestCancel(MsfRequestCancelVo vo);

    int insertMsfRequestCstmr(MsfRequestCstmrVo vo);

    int insertMsfRequestAgent(MsfRequestAgentVo vo);

    int insertMsfRequestAddition(MsfRequestAdditionVo vo);

    int insertMsfRequestBillReq(MsfRequestBillReqVo vo);

    int insertMsfRequestClause(MsfRequestClauseVo vo);

    int insertMsfRequestDoc(MsfRequestDocVo vo);

    int insertMsfRequestDvcChg(MsfRequestDvcChgVo vo);

    int insertMsfRequestJoinForm(MsfRequestJoinFormVo vo);

    int insertMsfRequestMove(MsfRequestMoveVo vo);

    int insertMsfRequestMst(MsfRequestMstVo vo);

    int insertMsfRequestNameChg(MsfRequestNameChgVo vo);

    int insertMsfRequestNameTrns(MsfRequestNameTrnsVo vo);

    int insertMsfRequestRec(MsfRequestRecVo vo);

    int insertMsfRequestSale(MsfRequestSaleVo vo);

    int insertMsfRequestState(MsfRequestStateVo vo);

    int insertMsfRequestSvcChgDtl(MsfRequestSvcChgDtlVo vo);

    int insertMsfRequestSvcChg(MsfRequestSvcChgVo vo);

    int insertMsfRequest(MsfRequestVo vo);
}
