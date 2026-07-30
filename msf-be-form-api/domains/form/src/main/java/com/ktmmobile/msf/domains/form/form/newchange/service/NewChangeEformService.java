package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfRequestEformRecord;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeEformInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeEformFieldMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewChangeEformService {

    private final NewChangeService newChangeService;
    private final NewChangeDBSelectService newChangeDBSelectService;
    private final CommonCodeReader commonCodeReader;
    private final AgencyCacheReader agencyCacheReader;
    //private final NewChangeEformFieldMapper newChangeEformFieldMapper;


    public NewChangeEformInfoResponse getNewChangeRequestEformInfo(NewChangeRequest request) {

        //임시저장 진입일 경우 세션의 아이디 일치 여부 및 신청서 작성완료여부 체크
        //Long requestKey = request.getRequestKey();
        //NewChangeRequest newChangeRequest = new NewChangeRequest();
        //newChangeRequest.setRequestKey(requestKey);
        //if (requestKey != null) {
        //    String newChangeFormStep = newChangeService.getNewChangeFormStep(newChangeRequest);
        //    int newChangeFormStepInt = Integer.parseInt(newChangeFormStep);
        //    //if (newChangeFormStepInt < 1 || newChangeFormStepInt > 3) {
        //    //    return null;
        //    //}
        //}

        MsfRequestEformRecord msfRequestEformRecord = this.getNewChangeEformInfo(request);
        NewChangeEformInfoResponse response = NewChangeEformFieldMapper.INSTANCE.toNewChangeEformInfoResponse(msfRequestEformRecord);

        CommonCodesRequest param = CommonCodesRequest.of(List.of("BNK","CRD", "NSC", "AGR", "NATIONLIST", "RCP0021"), true, false);
        CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(param);
        response.setCommonCode(msfRequestEformRecord, commonCodeGroups);

        //선택한 대리점코드
        if(msfRequestEformRecord.msfRequestVo().getCntpntShopCd() != null){
            String cntpntShopCd = StringUtil.NVL(msfRequestEformRecord.msfRequestVo().getCntpntShopCd(), "");
            if(!"".equals(cntpntShopCd)){
                //AgencyCacheReader 에서 불러오기
                Optional<AgencyCache> agentInfo = agencyCacheReader.getAgency(cntpntShopCd);
                agentInfo.ifPresent(response::setAgentInfo);
            }
        }
        return response;
    }

    //신청서 EFORM 데이타 조회 - 신청서 확인 버튼 클릭
    //(신청서 작성완료에서도 내부적으로 호출해야한다면 테이블은 분기되는 로직이 필요함)
    public MsfRequestEformRecord getNewChangeEformInfo(NewChangeRequest request) {

        return new MsfRequestEformRecord(
            newChangeDBSelectService.getMsfRequestEformInfo(request), //MSF_REQUEST_TEMP
            newChangeDBSelectService.getMsfRequestCstmrEformInfo(request), //MSF_REQUEST_CSTMR_TEMP
            newChangeDBSelectService.getMsfRequestAgentEformInfo(request), //MSF_REQUEST_AGENT_TEMP
            newChangeDBSelectService.getMsfRequestSaleEformInfo(request), //MSF_REQUEST_SALE_TEMP
            newChangeDBSelectService.getMsfRequestBillReqEformInfo(request), //MSF_REQUEST_BILL_REQ_TEMP
            newChangeDBSelectService.getMsfRequestMoveEformInfo(request), //MSF_REQUEST_MOVE_TEMP
            newChangeDBSelectService.getMsfRequestDvcChgEformInfo(request) //MSF_REQUEST_DVC_CHG_TEMP
            //newChangeSelectService.getMsfRequestAdditionEformInfo(request) //MSF_REQUEST_ADDITION_TEMP
        );
    }


    //eForm 생성을 위한 유효성 검증 및 데이터 저장 그리고 데이터 조회하여 전달
    //public FormResponse<NewChangeEformResponse> eformNewChangeSet(NewChangeInfoRequest request) {
    //    NewChangeEformResponse eformResponse = new NewChangeEformResponse();
    //    //NewChangeInfoResponse response = new NewChangeInfoResponse();
    //
    //    //0. 신청서 번호 확인
    //    if (request.getRequestKey() == null || request.getRequestKey() <= 0) {
    //        return FormResponse.of(ResponseMessage.F_BIND_EXCEPTION, eformResponse);
    //    }
    //
    //    // 프론트에서 신청서 저장 후 eForm 생성을 호출하기로 하여 저장은 처리하지 않음.
    //    //1. 신청서 데이타 유효성검증
    //    //  this.checkNewChangeInfoData
    //    //2. 신청서 저장 (tmp_stat_cd : 3) 확인
    //    //this.saveAppformInfo(request);
    //
    //    //3. 신청서 데이타 조회
    //    NewChangeRequest newChangeRequest = new NewChangeRequest();
    //    newChangeRequest.setRequestKey(request.getRequestKey());
    //    eformResponse = this.setNewChangeEformData(newChangeRequest);
    //
    //    //response = this.setNewChangeEformData(newChangeRequest);
    //    //return FormResponse.of(ResponseMessage.SUCCESS, response);
    //    //MsfRequestRecord msfRequestRecord = this.getNewChangeInfo(request);
    //    //return NewChangeFieldMapper.INSTANCE.toNewChangeInfoResponse(msfRequestRecord);
    //
    //    return FormResponse.of(ResponseMessage.SUCCESS, eformResponse);
    //}

    //eForm 생성을 위한 데이타 조회
    //public NewChangeEformResponse setNewChangeEformData(NewChangeRequest request) {
    //
    //    return this.getNewChangeRequestInfo(request);
    //}
    //public NewChangeInfoResponse setNewChangeEformData(NewChangeRequest request) {
    //    return this.getNewChangeRequestInfo(request);
    //}

    //public NewChangeEformResponse setNewChangeRequestInfo(NewChangeRequest request) {
    //
    //    //임시저장 진입일 경우 세션의 아이디 일치 여부 및 신청서 작성완료여부 체크
    //    Long requestKey = request.getRequestKey();
    //    NewChangeRequest newChangeRequest = new NewChangeRequest();
    //    newChangeRequest.setRequestKey(requestKey);
    //    if (requestKey != null) {
    //        String newChangeFormStep = this.getNewChangeFormStep(newChangeRequest);
    //        int newChangeFormStepInt = Integer.parseInt(newChangeFormStep);
    //        if (newChangeFormStepInt < 1 || newChangeFormStepInt > 3) {
    //            return null;
    //        }
    //    }
    //
    //    MsfRequestEformRecord msfRequestEformRecord = this.getNewChangeInfoToEform(request);
    //    return NewChangeFieldMapper.INSTANCE.toNewChangeInfoResponse(msfRequestEformRecord);
    //}
}
