package com.ktmmobile.msf.domains.form.form.common.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0FrmInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.MsfRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.MsfRequestWriteMapper;
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
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormInfoResponse;

@Repository
@RequiredArgsConstructor
public class MsfRequestRepositoryImpl {

    private final MsfRequestWriteMapper msfRequestWriteMapper;
    private final MsfRequestReadMapper msfRequestReadMapper;

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

    public int insertMsfRequestNameChg(MsfRequestNameChgVo vo) {
        return msfRequestWriteMapper.insertMsfRequestNameChg(vo);
    }

    public int insertMsfRequestNameTrns(MsfRequestNameChgVo vo) {
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

    public boolean existsMsfRequestSvcChg(Long requestKey) {
        return requestKey != null && msfRequestReadMapper.countMsfRequestSvcChg(requestKey) > 0;
    }

    public int updateMsfRequestSvcChgProcCd(MsfRequestSvcChgVo vo) {
        return msfRequestWriteMapper.updateMsfRequestSvcChgProcCd(vo);
    }

    public int updateMsfRequestSvcChgScanId(MsfRequestSvcChgVo vo) {
        return msfRequestWriteMapper.updateMsfRequestSvcChgScanId(vo);
    }

    public int updateMsfRequestSvcChgDtlScanId(MsfRequestSvcChgDtlVo vo) {
        return msfRequestWriteMapper.updateMsfRequestSvcChgDtlScanId(vo);
    }

    public int updateMsfRequestSvcChgDtlSocScanId(MsfRequestSvcChgDtlVo vo) {
        return msfRequestWriteMapper.updateMsfRequestSvcChgDtlSocScanId(vo);
    }

    public int updateMsfRequestSvcChgDtlProcResult(MsfRequestSvcChgDtlVo vo) {
        return msfRequestWriteMapper.updateMsfRequestSvcChgDtlProcResult(vo);
    }

    public int insertMsfRequest(MsfRequestVo vo) {
        return msfRequestWriteMapper.insertMsfRequest(vo);
    }

    public int insertMsfRequestOsst(MsfRequestOsstVo vo) {
        return msfRequestWriteMapper.insertMsfRequestOsst(vo);
    }

    public OwnerChangeFormInfoResponse selectMsfRequestOwnerChgInfo(Long requestKey) {
        return msfRequestReadMapper.selectMsfRequestOwnerChgInfo(requestKey);
    }

    public int deleteMsfRequestNameChg(Long requestKey) { return msfRequestWriteMapper.deleteMsfRequestNameChg(requestKey); }

    public int deleteMsfRequestNameTrns(Long requestKey) { return msfRequestWriteMapper.deleteMsfRequestNameTrns(requestKey); }

    public int deleteMsfRequestCstmr(Long requestKey) { return msfRequestWriteMapper.deleteMsfRequestCstmr(requestKey); }

    public int deleteMsfRequestAgent(Long requestKey) { return msfRequestWriteMapper.deleteMsfRequestAgent(requestKey); }

    public int deleteMsfRequestBillReq(Long requestKey) { return msfRequestWriteMapper.deleteMsfRequestBillReq(requestKey); }

    public int deleteMsfRequestJoinForm(Long requestKey) { return msfRequestWriteMapper.deleteMsfRequestJoinForm(requestKey); }

    public int deleteMsfRequestDoc(Long requestKey) { return msfRequestWriteMapper.deleteMsfRequestDoc(requestKey); }

    public int deleteMsfRequestRec(Long requestKey) { return msfRequestWriteMapper.deleteMsfRequestRec(requestKey); }

    public MplatFormFMC0FrmInfoResponse selectMsfFMC0(MsfRequestNameChgVo request) { return msfRequestReadMapper.selectMsfFMC0(request); }

}
