package com.ktmmobile.msf.domains.form.form.common.repository;

import com.ktmmobile.msf.domains.form.form.common.repository.smartform.MsfRequestWriteMapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MsfRequestRepositoryImpl {

    private final MsfRequestWriteMapper msfRequestWriteMapper;

    public int insertMsfRequestCancel(MsfRequestCancelVo vo) {
        return msfRequestWriteMapper.insertMsfRequestCancel(vo);
    }

    public int insertMsfRequestCstmr(MsfRequestCstmrVo vo) {
        return msfRequestWriteMapper.insertMsfRequestCstmr(vo);
    }

    public int insertMsfRequestAgent(MsfRequestAgentVo vo) {
        return msfRequestWriteMapper.insertMsfRequestAgent(vo);
    }

    public int insertMsfRequestAddition(MsfRequestAdditionVo vo) {
        return msfRequestWriteMapper.insertMsfRequestAddition(vo);
    }

    public int insertMsfRequestBillReq(MsfRequestBillReqVo vo) {
        return msfRequestWriteMapper.insertMsfRequestBillReq(vo);
    }

    public int insertMsfRequestClause(MsfRequestClauseVo vo) {
        return msfRequestWriteMapper.insertMsfRequestClause(vo);
    }

    public int insertMsfRequestDoc(MsfRequestDocVo vo) {
        return msfRequestWriteMapper.insertMsfRequestDoc(vo);
    }

    public int insertMsfRequestDvcChg(MsfRequestDvcChgVo vo) {
        return msfRequestWriteMapper.insertMsfRequestDvcChg(vo);
    }

    public int insertMsfRequestJoinForm(MsfRequestJoinFormVo vo) {
        return msfRequestWriteMapper.insertMsfRequestJoinForm(vo);
    }

    public int insertMsfRequestMove(MsfRequestMoveVo vo) {
        return msfRequestWriteMapper.insertMsfRequestMove(vo);
    }

    public int insertMsfRequestMst(MsfRequestMstVo vo) {
        return msfRequestWriteMapper.insertMsfRequestMst(vo);
    }

    public int insertMsfRequestNameChg(MsfRequestNameChgVo vo) {
        return msfRequestWriteMapper.insertMsfRequestNameChg(vo);
    }

    public int insertMsfRequestNameTrns(MsfRequestNameTrnsVo vo) {
        return msfRequestWriteMapper.insertMsfRequestNameTrns(vo);
    }

    public int insertMsfRequestRec(MsfRequestRecVo vo) {
        return msfRequestWriteMapper.insertMsfRequestRec(vo);
    }

    public int insertMsfRequestSale(MsfRequestSaleVo vo) {
        return msfRequestWriteMapper.insertMsfRequestSale(vo);
    }

    public int insertMsfRequestState(MsfRequestStateVo vo) {
        return msfRequestWriteMapper.insertMsfRequestState(vo);
    }

    public int insertMsfRequestSvcChgDtl(MsfRequestSvcChgDtlVo vo) {
        return msfRequestWriteMapper.insertMsfRequestSvcChgDtl(vo);
    }

    public int insertMsfRequestSvcChg(MsfRequestSvcChgVo vo) {
        return msfRequestWriteMapper.insertMsfRequestSvcChg(vo);
    }

    public int insertMsfRequest(MsfRequestVo vo) {
        return msfRequestWriteMapper.insertMsfRequest(vo);
    }
}
