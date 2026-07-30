package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeEformReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeMpReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;

@Service
@RequiredArgsConstructor
public class NewChangeDBSelectService {

    private final NewChangeReadMapper newChangeReadMapper;
    private final NewChangeEformReadMapper newChangeEformReadMapper;
    private final NewChangeMpReadMapper newChangeMpReadMapper;

    //MSF_REQUEST 조회
    public MsfRequestVo getMsfRequestInfo(NewChangeRequest request) {

        return newChangeReadMapper.selectMsfRequestInfo(request);
    }

    public MsfRequestVo getMsfRequestEformInfo(NewChangeRequest request) {

        return newChangeEformReadMapper.selectMsfRequestEformInfo(request);
    }

    //public MsfRequestVo getMsfRequestMpInfo(NewChangeRequest request) {
    //    return newChangeMpReadMapper.selectMsfRequestMpInfo(request);
    //}

    //MSF_REQUEST_CSTMR 조회
    public MsfRequestCstmrVo getMsfRequestCstmrInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestCstmrInfo(request);
    }

    public MsfRequestCstmrVo getMsfRequestCstmrEformInfo(NewChangeRequest request) {
        return newChangeEformReadMapper.selectMsfRequestCstmrEformInfo(request);
    }

    //public MsfRequestCstmrVo getMsfRequestCstmrMpInfo(NewChangeRequest request) {
    //    return newChangeMpReadMapper.selectMsfRequestCstmrMpInfo(request);
    //}

    //MSF_REQUEST_AGENT 조회
    public MsfRequestAgentVo getMsfRequestAgentInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestAgentInfo(request);
    }

    public MsfRequestAgentVo getMsfRequestAgentEformInfo(NewChangeRequest request) {
        return newChangeEformReadMapper.selectMsfRequestAgentEformInfo(request);
    }

    //public MsfRequestAgentVo getMsfRequestAgentMpInfo(NewChangeRequest request) {
    //    return newChangeMpReadMapper.selectMsfRequestAgentMpInfo(request);
    //}

    //MSF_REQUEST_SALE 조회
    public MsfRequestSaleVo getMsfRequestSaleInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestSaleInfo(request);
    }

    public MsfRequestSaleVo getMsfRequestSaleEformInfo(NewChangeRequest request) {
        return newChangeEformReadMapper.selectMsfRequestSaleEformInfo(request);
    }

    //public MsfRequestSaleVo getMsfRequestSaleMpInfo(NewChangeRequest request) {
    //    return newChangeMpReadMapper.selectMsfRequestSaleMpInfo(request);
    //}

    //MSF_REQUEST_BILL_REQ 조회
    public MsfRequestBillReqVo getMsfRequestBillReqInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestBillReqInfo(request);
    }

    public MsfRequestBillReqVo getMsfRequestBillReqEformInfo(NewChangeRequest request) {
        return newChangeEformReadMapper.selectMsfRequestBillReqEformInfo(request);
    }
    //public MsfRequestBillReqVo getMsfRequestBillReqMpInfo(NewChangeRequest request) {
    //    return newChangeMpReadMapper.selectMsfRequestBillReqMpInfo(request);
    //}

    //MSF_REQUEST_MOVE 조회
    public MsfRequestMoveVo getMsfRequestMoveInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestMoveInfo(request);
    }

    public MsfRequestMoveVo getMsfRequestMoveEformInfo(NewChangeRequest request) {
        return newChangeEformReadMapper.selectMsfRequestMoveEformInfo(request);
    }

    //public MsfRequestMoveVo getMsfRequestMoveMpInfo(NewChangeRequest request) {
    //    return newChangeMpReadMapper.selectMsfRequestMoveMpInfo(request);
    //}

    //MSF_REQUEST_DVC_CHG 조회
    public MsfRequestDvcChgVo getMsfRequestDvcChgInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestDvcChgInfo(request);
    }

    public MsfRequestDvcChgVo getMsfRequestDvcChgEformInfo(NewChangeRequest request) {
        return newChangeEformReadMapper.selectMsfRequestDvcChgEformInfo(request);
    }

    //public MsfRequestDvcChgVo getMsfRequestDvcChgMpInfo(NewChangeRequest request) {
    //    return newChangeMpReadMapper.selectMsfRequestDvcChgMpInfo(request);
    //}

    //MSF_REQUEST_ADDITION 조회
    public List<MsfRequestAdditionVo> getMsfRequestAdditionInfo(NewChangeRequest request) {
        List<MsfRequestAdditionVo> additionList = newChangeReadMapper.selectMsfRequestAdditionInfo(request);
        return additionList;
    }

    public List<MsfRequestAdditionVo> getMsfRequestAdditionEformInfo(NewChangeRequest request) {
        List<MsfRequestAdditionVo> additionList = newChangeEformReadMapper.selectMsfRequestAdditionEformInfo(request);
        return additionList;
    }

    //public List<MsfRequestAdditionVo> getMsfRequestAdditionMpInfo(NewChangeRequest request) {
    //    List<MsfRequestAdditionVo> additionList = newChangeMpReadMapper.selectMsfRequestAdditionMpInfo(request);
    //    return additionList;
    //}

}
