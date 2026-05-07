package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.form.common.code.CstmrType;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfRequestRecord;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeAdditionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionResponse;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeFieldMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeWriteMapper;

@Service
@RequiredArgsConstructor
public class NewChangeService {

    private final FormCommService formCommService;
    private final NewChangeReadMapper newChangeReadMapper;
    private final NewChangeWriteMapper newChangeWriteMapper;
    private final FormCommReadMapper formCommReadMapper;
    private final McpRequestWriteMapper mcpRequestWriteMapper;


    //가입조건조회
    public SubscriptionResponse getEligibilityCheck(SubscriptionRequest request) {
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();

        //1년이내 이내 사용회선 조회
        int actYearCnt = this.getActYearCnt(request);
        //subscriptionResponse.setYearActCnt(actYearCnt);
        //1년이내 해지
        int cancelYearCnt = this.getCancelYearCnt(request);
        //subscriptionResponse.setYearCanCnt(cancelYearCnt);
        //당월개통회선
        int actThisMonthCnt = this.getActThisMonthCnt(request);
        //subscriptionResponse.setThisMonthActCnt(actThisMonthCnt);
        //미납조회
        int unpaidCnt = this.getUnpaidCnt(request);
        //subscriptionResponse.setDelinqStatusCnt(unpaidCnt);
        //전체 개통 회선
        int actTotalCnt = this.getActTotalCnt(request);
        //subscriptionResponse.setTotActCnt(actTotalCnt);

        //가입제한
        String subscriptionRestrictionsYn = "Y";
        //가입한도
        String subscriptionLimitYn = "Y";
        //미납
        String unPaidYn = "Y";
        //상습해지이력
        String historyOfCancellationYn = "Y";
        //할부할인
        String installmentDiscountYn = "Y";

        //고객유형별 처리 : NA , NM , FN , FM , JP , GO
        CstmrType cstmrTypeCd = request.getCstmrTypeCd();
        switch (cstmrTypeCd) {
            case CstmrType.NATIVE_ADULT:
                break;
            case CstmrType.NATIVE_MINOR:
                break;
            case CstmrType.FOREIGN_ADULT:
                break;
            case CstmrType.FOREIGN_MINOR:
                break;
            case CstmrType.JURIDICAL_PERSON:
                break;
            case CstmrType.GOVERNMENT_ORGANIZATION:
                break;
            default:
                break;
        }

        subscriptionResponse.setSubscriptionRestrictionsYn(subscriptionRestrictionsYn);
        subscriptionResponse.setSubscriptionLimitYn(subscriptionLimitYn);
        subscriptionResponse.setUnPaidYn(unPaidYn);
        subscriptionResponse.setHistoryOfCancellationYn(historyOfCancellationYn);
        subscriptionResponse.setInstallmentDiscountYn(installmentDiscountYn);

        return subscriptionResponse;
    }

    //1년이내 이내 사용회선 조회
    public int getActYearCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectActYearCnt(request);
    }

    //1년이내 해지
    public int getCancelYearCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectCancelYearCnt(request);
    }

    //당월개통회선
    public int getActThisMonthCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectActThisMonthCnt(request);
    }

    //미납조회
    public int getUnpaidCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectUnpaidCnt(request);
    }

    //전체 개통 회선
    public int getActTotalCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectActTotalCnt(request);
    }

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

    //신청서 조회
    public MsfRequestRecord getNewChangeInfo(NewChangeRequest request) {

        return new MsfRequestRecord(
            this.getMsfRequestInfo(request), //MSF_REQUEST
            this.getMsfRequestAgentInfo(request), //MSF_REQUEST_CSTMR
            this.getMsfRequestCstmrInfo(request), //MSF_REQUEST_AGENT
            this.getMsfRequestSaleInfo(request), //MSF_REQUEST_SALE
            this.getMsfRequestBillReqInfo(request), //MSF_REQUEST_BILL_REQ
            this.getMsfRequestMoveInfo(request), //MSF_REQUEST_MOVE
            this.getMsfRequestDvcChgInfo(request), //MSF_REQUEST_DVC_CHG
            this.getMsfRequestAdditionInfo(request) //MSF_REQUEST_ADDITION
        );
    }

    //신청서 상세 조회 (NewChangeInfoRequest 형태로 반환)
    public NewChangeInfoResponse getNewChangeRequestInfo(NewChangeRequest request) {
        MsfRequestRecord msfRequestRecord = this.getNewChangeInfo(request);
        return NewChangeFieldMapper.INSTANCE.toNewChangeInfoResponse(msfRequestRecord);
    }


    //신청서 저장
    @Transactional
    public FormResponse<NewChangeResponse> saveAppformInfo(NewChangeInfoRequest request) {

        //기기변경사유 - 변환처리

        //신청서 유효성체크 start
        //단말/요금제로 예상금액 재계산~~~ 데이터저장
        //신청서 유효성체크 end


        //==== 개통전 사전체크 START =====//
        boolean isFirst = false;
        if (request.getRequestKey() == null) {
            isFirst = true;
            //신청서번호 생성
            request.setRequestKey(formCommService.generateRequestKey());
        }
        //스마트 request 에서 고객포탈 request 로 컬럼 변경되는 것들 변환처리
        //AS-IS : @RequestMapping(value = "/appform/reqPreOpenCheckAjax.do")
        Map<String, Object> osstRtnMap = formCommService.checkOsstPreCheck(request);
        //==== 개통전 사전체크 END =====//

        //부가서비스 request & delete : 저장 전 처리
        //@@ 추후 따로 빼자
        StringBuilder nameBuilder = new StringBuilder();
        String reqAdditionListNm = "";
        long reqAdditionPrice = 0L;

        List<MsfRequestAdditionVo> additionDtoList = new ArrayList<>();
        List<NewChangeAdditionRequest> msfAdditionList = request.getAdditionList();
        if (msfAdditionList != null && !msfAdditionList.isEmpty()) {
            if (msfAdditionList.size() > 0) {
                for (NewChangeAdditionRequest dto: msfAdditionList) {
                    MsfRequestAdditionVo msfRequestAdditionVo = new MsfRequestAdditionVo();
                    msfRequestAdditionVo.setRequestKey(request.getRequestKey());
                    msfRequestAdditionVo.setAdditionId(dto.getRateCd());
                    msfRequestAdditionVo.setAdditionNm(dto.getRateNm());
                    msfRequestAdditionVo.setRantal(dto.getBaseAmt());
                    msfRequestAdditionVo.setCretId("82311998"); //@@변경필수@@ -- 로그인 완료 후 처리필요
                    msfRequestAdditionVo.setCretIp("127.0.0.1"); //@@변경필수@@ -- 음...
                    additionDtoList.add(msfRequestAdditionVo);

                    if (nameBuilder.length() > 0) {
                        nameBuilder.append(",");
                    }
                    nameBuilder.append(dto.getRateNm().trim());
                    if (dto.getBaseAmt() > 0) {
                        reqAdditionPrice += dto.getBaseAmt();
                    }
                }
                reqAdditionListNm = nameBuilder.toString();
                request.setReqAdditionListNm(reqAdditionListNm);
                request.setReqAdditionPrice(reqAdditionPrice);
            }
        }
        newChangeWriteMapper.deleteMsfAdditionTemp(request.getRequestKey());

        //if (request.getRequestKey() == null) {
        if (isFirst) {
            MsfRequestRecord record = MsfRequestRecord.requestToRecord(request);

            //INSERT
            newChangeWriteMapper.insertMsfRequestTemp(record.msfRequestVo()); //MSF_REQUEST
            newChangeWriteMapper.insertMsfRequestAgentTemp(record.msfRequestAgentVo()); //MSF_REQUEST_AGENT
            newChangeWriteMapper.insertMsfRequestCstmrTemp(record.msfRequestCstmrVo()); //MSF_REQUEST_CSTMR
            newChangeWriteMapper.insertMsfRequestSaleTemp(record.msfRequestSaleVo()); //MSF_REQUEST_SALE
            newChangeWriteMapper.insertMsfRequestBillReqTemp(record.msfRequestBillReqVo()); //MSF_REQUEST_BILL_REQ
            newChangeWriteMapper.insertMsfRequestMoveTemp(record.msfRequestMoveVo()); //MSF_REQUEST_MOVE
            newChangeWriteMapper.insertMsfRequestDvcChgTemp(record.msfRequestDvcChgVo()); //MSF_REQUEST_DVC_CHG

            //부가서비스
            if (msfAdditionList != null && !msfAdditionList.isEmpty() && msfAdditionList.size() > 0) {
                newChangeWriteMapper.insertAdditionInfoListTemp(additionDtoList); //MSF_REQUEST_ADDITION
            }
        } else {
            MsfRequestRecord record = MsfRequestRecord.requestToRecord(request);

            //UPDATE
            newChangeWriteMapper.updateMsfRequestTemp(record.msfRequestVo()); //MSF_REQUEST
            newChangeWriteMapper.updateMsfRequestAgentTemp(record.msfRequestAgentVo()); //MSF_REQUEST_AGENT
            newChangeWriteMapper.updateMsfRequestCstmrTemp(record.msfRequestCstmrVo()); //MSF_REQUEST_CSTMR
            newChangeWriteMapper.updateMsfRequestSaleTemp(record.msfRequestSaleVo()); //MSF_REQUEST_SALE
            newChangeWriteMapper.updateMsfRequestBillReqTemp(record.msfRequestBillReqVo()); //MSF_REQUEST_BILL_REQ
            newChangeWriteMapper.updateMsfRequestMoveTemp(record.msfRequestMoveVo()); //MSF_REQUEST_MOVE
            newChangeWriteMapper.updateMsfRequestDvcChgTemp(record.msfRequestDvcChgVo()); //MSF_REQUEST_DVC_CHG
            if (msfAdditionList != null && !msfAdditionList.isEmpty() && msfAdditionList.size() > 0) {
                newChangeWriteMapper.insertAdditionInfoListTemp(additionDtoList); //MSF_REQUEST_ADDITION
            }

        }
        //신청서 저장 end

        //저장 성공 후 신청서번호 Return
        NewChangeResponse response = new NewChangeResponse();
        response.setRequestKey(request.getRequestKey());
        return FormResponse.of(ResponseMessage.SUCCESS, response);
    }


}
