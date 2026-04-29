package com.ktmmobile.msf.domains.form.form.newchange.service;

import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.vo.*;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfNewChangeInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeFieldMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeWriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NewChangeService {

    private final FormCommService formCommService;
    private final NewChangeReadMapper newChangeReadMapper;
    private final NewChangeWriteMapper newChangeWriteMapper;
    //private final FormType formType;

    //MSF_REQUEST 조회
    public MsfRequestVo getMsfRequestInfo(NewChangeRequest condition) {
        return newChangeReadMapper.selectMsfRequestInfo(condition);
    }

    //MSF_REQUEST_CSTMR 조회
    public MsfRequestCstmrVo getMsfRequestCstmrInfo(NewChangeRequest condition) {
        return newChangeReadMapper.selectMsfRequestCstmrInfo(condition);
    }

    //MSF_REQUEST_AGENT 조회
    public MsfRequestAgentVo getMsfRequestAgentInfo(NewChangeRequest condition) {
        return newChangeReadMapper.selectMsfRequestAgentInfo(condition);
    }

    //MSF_REQUEST_SALE 조회
    public MsfRequestSaleVo getMsfRequestSaleInfo(NewChangeRequest condition) {
        return newChangeReadMapper.selectMsfRequestSaleInfo(condition);
    }

    //MSF_REQUEST_BILL_REQ 조회
    public MsfRequestBillReqVo getMsfRequestBillReqInfo(NewChangeRequest condition) {
        return newChangeReadMapper.selectMsfRequestBillReqInfo(condition);
    }

    //신청서 조회
    public MsfNewChangeInfoDto getNewChangeInfo(NewChangeRequest condition) {
        MsfNewChangeInfoDto msfNewchangeInfoDto = new MsfNewChangeInfoDto();

        String msfRequestKey = "";
        String formTypeCd = "";
        Integer requestKey;
        MsfRequestVo msfRequestVo = new MsfRequestVo();
        MsfRequestCstmrVo msfRequestCstmrVo = new MsfRequestCstmrVo();
        MsfRequestAgentVo msfRequestAgentVo = new MsfRequestAgentVo();
        MsfRequestSaleVo msfRequestSaleVo = new MsfRequestSaleVo();
        MsfRequestBillReqVo msfRequestBillReqVo = new MsfRequestBillReqVo();

        if (StringUtils.hasText(condition.getMsfRequestKey())) {
            msfRequestKey = condition.getMsfRequestKey();
            condition.setRequestKey(Long.parseLong(msfRequestKey));

            msfRequestVo = this.getMsfRequestInfo(condition); //MSF_REQUEST
            msfRequestCstmrVo = this.getMsfRequestCstmrInfo(condition); //MSF_REQUEST_CSTMR
            msfRequestAgentVo = this.getMsfRequestAgentInfo(condition); //MSF_REQUEST_AGENT
            msfRequestSaleVo = this.getMsfRequestSaleInfo(condition); //MSF_REQUEST_SALE
            msfRequestBillReqVo = this.getMsfRequestBillReqInfo(condition); //MSF_REQUEST_BILL_REQ


        }

        //ModelMapper modelMapper = new ModelMapper();
        //msfNewchangeInfoDtoList.add(msfRequestDto);
        msfNewchangeInfoDto.setMsfRequestVo(msfRequestVo);
        msfNewchangeInfoDto.setMsfRequestCstmrVo(msfRequestCstmrVo);
        msfNewchangeInfoDto.setMsfRequestAgentVo(msfRequestAgentVo);
        //msfNewchangeInfoDto.add(msfNewchangeInfoDto);

        return msfNewchangeInfoDto;
    }

    //신청서 상세 조회 (NewChangeInfoRequest 형태로 반환)
    public NewChangeInfoResponse getNewChangeRequestInfo(NewChangeRequest condition) {
        MsfNewChangeInfoDto msfNewChangeInfoDto = this.getNewChangeInfo(condition);
        return NewChangeFieldMapper.INSTANCE.toNewChangeInfoResponse(msfNewChangeInfoDto);
    }

    //신청서 저장
    @Transactional
    public String saveAppformInfo(NewChangeInfoRequest request) {
        long requestKey = 0;

        //신청서 유효성체크 start
        //신청서 유효성체크 end

        //신청서 저장 start
        //신청서번호 생성
        if (request.getRequestKey() == null) {
            requestKey = formCommService.generateRequestKey();
            request.setRequestKey(requestKey);
        }

        MsfRequestVo msfRequestVo = NewChangeFieldMapper.INSTANCE.toMsfRequestVo(request);
        MsfRequestAgentVo msfRequestAgentVo = NewChangeFieldMapper.INSTANCE.toMsfRequestAgentVo(request);
        MsfRequestCstmrVo msfRequestCstmrVo = NewChangeFieldMapper.INSTANCE.toMsfRequestCstmrVo(request);
        MsfRequestSaleVo msfRequestSaleVo = NewChangeFieldMapper.INSTANCE.toMsfRequestSaleVo(request);
        MsfRequestBillReqVo msfRequestBillReqVo = NewChangeFieldMapper.INSTANCE.toMsfRequestBillReqVo(request);
        MsfRequestMoveVo msfRequestMoveVo = NewChangeFieldMapper.INSTANCE.toMsfRequestMoveVo(request);
        //부가서비스

        if (requestKey != 0) {
            //INSERT
            newChangeWriteMapper.insertMsfRequestTemp(msfRequestVo); //MSF_REQUEST
            newChangeWriteMapper.insertMsfRequestAgentTemp(msfRequestAgentVo); //MSF_REQUEST_AGENT
            newChangeWriteMapper.insertMsfRequestCstmrTemp(msfRequestCstmrVo); //MSF_REQUEST_CSTMR
            newChangeWriteMapper.insertMsfRequestSaleTemp(msfRequestSaleVo); //MSF_REQUEST_SALE
            newChangeWriteMapper.insertMsfRequestBillReqTemp(msfRequestBillReqVo); //MSF_REQUEST_BILL_REQ
            newChangeWriteMapper.insertMsfRequestMoveTemp(msfRequestMoveVo);
        } else {
            //UPDATE
            request.setRequestKey(request.getRequestKey());
            newChangeWriteMapper.updateMsfRequestTemp(msfRequestVo);
            newChangeWriteMapper.updateMsfRequestAgentTemp(msfRequestAgentVo);
            newChangeWriteMapper.updateMsfRequestCstmrTemp(msfRequestCstmrVo);
            newChangeWriteMapper.updateMsfRequestSaleTemp(msfRequestSaleVo);
            newChangeWriteMapper.updateMsfRequestBillReqTemp(msfRequestBillReqVo);
            newChangeWriteMapper.updateMsfRequestMoveTemp(msfRequestMoveVo);
        }
        //신청서 저장 end

        return Long.toString(requestKey);
    }


    /**
     * 번호이동 사전체크 일 건수 제한
     **/
    public Map<String, Object> mnpPreCheckLimit(String moveMobileNo) {

        Map<String, Object> rtnMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        List<String> resNoList = new ArrayList<>();

        // 제한시간(분) 조회
        int limitDay = 0;
        int limitCnt = 0;
        String fAlertMsg = "";

        /*NmcpCdDtlDto limitDto = NmcpServiceUtils.getCodeNmDto(CMM_PERIOD_LIMIT, "MnpDayLimit");

        if (limitDto != null) {
            limitDay = Integer.parseInt(StringUtil.NVL(limitDto.getExpnsnStrVal1(), "0"));
            limitCnt = Integer.parseInt(StringUtil.NVL(limitDto.getExpnsnStrVal2(), "0"));
            fAlertMsg = limitDto.getExpnsnStrVal3();

            // 동일 번호이동전화번호 신청서 조회
            paramMap.put("limitDay", limitDay);
            paramMap.put("moveMobileNo", moveMobileNo);
            resNoList = appformDao.getResNoByMoveMobileNum(paramMap);
        }

        // 특정기간 내 신청건 없음 → 성공처리
        if (resNoList.isEmpty()) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        // 사전체크 시도 이력 확인
        paramMap.put("resNoList", resNoList);
        paramMap.put("prgrStatCd", Constants.EVENT_CODE_PRE_CHECK);
        int tryCnt = appformDao.getPreCheckTryCnt(paramMap);

        if (limitCnt == 0 || tryCnt < limitCnt) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        // 실패이력 저장
        McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
        mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
        mcpIpStatisticDto.setTrtmRsltSmst(moveMobileNo);
        mcpIpStatisticDto.setPrcsSbst("Exception[PC0_DAY_LIMIT]");
        mcpIpStatisticDto.setParameter("MOVE_MOBILE_NUM[" + moveMobileNo + "] TRY_CNT[" + tryCnt + "] LIMIT_CNT[" + limitCnt + "]");
        ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);*/

        rtnMap.put("RESULT_CODE", "-9999");
        rtnMap.put("ERROR_MSG", "PC0_TIME_LIMIT");
        rtnMap.put("ERROR_NE_MSG", fAlertMsg);
        return rtnMap;
    }

}
