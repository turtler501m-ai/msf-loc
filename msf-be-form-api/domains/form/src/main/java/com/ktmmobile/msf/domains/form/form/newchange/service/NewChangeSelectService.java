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
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;

@Service
@RequiredArgsConstructor
public class NewChangeSelectService {

    private final NewChangeReadMapper newChangeReadMapper;


    //MSF_REQUEST 조회
    public MsfRequestVo getMsfRequestInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestInfo(request);
    }

    //MSF_REQUEST_CSTMR 조회
    public MsfRequestCstmrVo getMsfRequestCstmrInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestCstmrInfo(request);
    }

    //MSF_REQUEST_AGENT 조회
    public MsfRequestAgentVo getMsfRequestAgentInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestAgentInfo(request);
    }

    //MSF_REQUEST_SALE 조회
    public MsfRequestSaleVo getMsfRequestSaleInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestSaleInfo(request);
    }

    //MSF_REQUEST_BILL_REQ 조회
    public MsfRequestBillReqVo getMsfRequestBillReqInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestBillReqInfo(request);
    }

    //MSF_REQUEST_MOVE 조회
    public MsfRequestMoveVo getMsfRequestMoveInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestMoveInfo(request);
    }

    //MSF_REQUEST_DVC_CHG 조회
    public MsfRequestDvcChgVo getMsfRequestDvcChgInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestDvcChgInfo(request);
    }

    //MSF_REQUEST_ADDITION 조회
    public List<MsfRequestAdditionVo> getMsfRequestAdditionInfo(NewChangeRequest request) {
        List<MsfRequestAdditionVo> additionList = newChangeReadMapper.selectMsfRequestAdditionInfo(request);
        return additionList;
    }

    /*public List<MsfRequestAdditionVo> getMsfRequestAdditionInfo(NewChangeRequest request) {
        return newChangeReadMapper.selectMsfRequestAdditionInfo(request);
    }*/
}
