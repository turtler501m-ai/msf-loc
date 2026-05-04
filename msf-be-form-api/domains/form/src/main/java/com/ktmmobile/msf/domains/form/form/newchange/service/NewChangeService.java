package com.ktmmobile.msf.domains.form.form.newchange.service;

import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.vo.*;
import com.ktmmobile.msf.domains.form.form.newchange.dto.*;
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

    //MSF_REQUEST_MOVE 조회
    public MsfRequestMoveVo getMsfRequestMoveInfo(NewChangeRequest condition) {
        return newChangeReadMapper.selectMsfRequestMoveInfo(condition);
    }

    //MSF_REQUEST_DVC_CHG 조회
    public MsfRequestDvcChgVo getMsfRequestDvcChgInfo(NewChangeRequest condition) {
        return newChangeReadMapper.selectMsfRequestDvcChgInfo(condition);
    }

    //신청서 조회
    public MsfRequestRecord getNewChangeInfo(NewChangeRequest condition) {

        return new MsfRequestRecord(
                this.getMsfRequestInfo(condition), //MSF_REQUEST
                this.getMsfRequestAgentInfo(condition), //MSF_REQUEST_CSTMR
                this.getMsfRequestCstmrInfo(condition), //MSF_REQUEST_AGENT
                this.getMsfRequestSaleInfo(condition), //MSF_REQUEST_SALE
                this.getMsfRequestBillReqInfo(condition), //MSF_REQUEST_BILL_REQ
                this.getMsfRequestMoveInfo(condition), //MSF_REQUEST_MOVE
                this.getMsfRequestDvcChgInfo(condition) //MSF_REQUEST_DVC_CHG
        );
    }

    //신청서 상세 조회 (NewChangeInfoRequest 형태로 반환)
    public NewChangeInfoResponse getNewChangeRequestInfo(NewChangeRequest condition) {
        MsfRequestRecord msfRequestRecord = this.getNewChangeInfo(condition);
        return NewChangeFieldMapper.INSTANCE.toNewChangeInfoResponse(msfRequestRecord);
    }

    //신청서 저장
    @Transactional
    public FormResponse<NewChangeResponse> saveAppformInfo(NewChangeInfoRequest request) {

        //신청서 저장 start


        //기기변경사유 - 변환처리
        //부가서비스 - 변환처리

        //신청서 유효성체크 start
        //단말/요금제로 예상금액 재계산~~~ 데이터저장
        //
        //신청서 유효성체크 end



        if (request.getRequestKey() == null) {

            //신청서번호 생성
            request.setRequestKey(formCommService.generateRequestKey());

            MsfRequestRecord record = MsfRequestRecord.requestToRecord(request);

            //INSERT
            newChangeWriteMapper.insertMsfRequestTemp(record.msfRequestVo()); //MSF_REQUEST
            newChangeWriteMapper.insertMsfRequestAgentTemp(record.msfRequestAgentVo()); //MSF_REQUEST_AGENT
            newChangeWriteMapper.insertMsfRequestCstmrTemp(record.msfRequestCstmrVo()); //MSF_REQUEST_CSTMR
            newChangeWriteMapper.insertMsfRequestSaleTemp(record.msfRequestSaleVo()); //MSF_REQUEST_SALE
            newChangeWriteMapper.insertMsfRequestBillReqTemp(record.msfRequestBillReqVo()); //MSF_REQUEST_BILL_REQ
            newChangeWriteMapper.insertMsfRequestMoveTemp(record.msfRequestMoveVo());
            newChangeWriteMapper.insertMsfRequestDvcChgTemp(record.msfRequestDvcChgVo());
        } else {
            MsfRequestRecord record = MsfRequestRecord.requestToRecord(request);
            //UPDATE
            newChangeWriteMapper.updateMsfRequestTemp(record.msfRequestVo());
            newChangeWriteMapper.updateMsfRequestAgentTemp(record.msfRequestAgentVo());
            newChangeWriteMapper.updateMsfRequestCstmrTemp(record.msfRequestCstmrVo());
            newChangeWriteMapper.updateMsfRequestSaleTemp(record.msfRequestSaleVo());
            newChangeWriteMapper.updateMsfRequestBillReqTemp(record.msfRequestBillReqVo());
            newChangeWriteMapper.updateMsfRequestMoveTemp(record.msfRequestMoveVo());
            newChangeWriteMapper.updateMsfRequestDvcChgTemp(record.msfRequestDvcChgVo());
        }
        //신청서 저장 end

        //return Long.toString(requestKey);
        //return FormResponse.of(ResponseMessage.SUCCESS, Long.toString(requestKey));

        NewChangeResponse response = new NewChangeResponse();
        response.setRequestKey(request.getRequestKey());
        return FormResponse.of(ResponseMessage.SUCCESS, response);
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
