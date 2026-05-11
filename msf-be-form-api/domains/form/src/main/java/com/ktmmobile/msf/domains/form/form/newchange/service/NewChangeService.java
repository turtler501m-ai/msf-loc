package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.code.CstmrType;
import com.ktmmobile.msf.domains.form.common.code.ReqBuyType;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.ProductInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.RateInfoDto;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.service.ProductInfoService;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestBillReqVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestDvcChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMoveVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestStateVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfRequestRecord;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeAdditionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeDefaultResponse;
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
    private final ProductInfoService productInfoService;


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

        if ("9901013456789".equals(request.getCustomerSsn())) {
            subscriptionRestrictionsYn = "N";
            subscriptionLimitYn = "N";
            unPaidYn = "N";
            historyOfCancellationYn = "N";
            installmentDiscountYn = "N";
        }

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
            this.getMsfRequestCstmrInfo(request), //MSF_REQUEST_CSTMR
            this.getMsfRequestAgentInfo(request), //MSF_REQUEST_AGENT
            this.getMsfRequestSaleInfo(request), //MSF_REQUEST_SALE
            this.getMsfRequestBillReqInfo(request), //MSF_REQUEST_BILL_REQ
            this.getMsfRequestMoveInfo(request), //MSF_REQUEST_MOVE
            this.getMsfRequestDvcChgInfo(request), //MSF_REQUEST_DVC_CHG
            this.getMsfRequestAdditionInfo(request) //MSF_REQUEST_ADDITION
        );
    }

    // 최초 진입 시 초기값 설정
    //public NewChangeInfoResponse getNewChangeDefaultInfo(NewChangeRequest request) {
    public NewChangeDefaultResponse getNewChangeDefaultInfo(NewChangeRequest request) {
        //최초진입 시 기본 값 설정
        NewChangeDefaultResponse response = new NewChangeDefaultResponse();
        if (request.getRequestKey() == null) {
            //신청서 진입 시 초기값은 상품유형 휴대폰, 고객유형 내국인성인, 휴대폰 및 요금은 조회 후 첫번째 값으로 설정

            //판매정책조회로 휴대폰 목록조회 결과에서 첫번째꺼
            //선택된 첫번째 휴대폰에서 첫번째꺼 용량
            //선택된 첫번째 휴대폰에서 첫번째꺼 색상
            //요금제 목록조회 결과에서 첫번째꺼
            //약정기간 : 24개월
            //단말기 할부기간 : 24개월
            //할인유형 : 단말할인
            //휴대폰 / USIM 선택에 따라 ~~~~~ 요금제 조회... 단말/..........

            String prodId = "3208"; //상품아이디
            String prodNm = "갤럭시 A21S"; //상품명
            String modelId = "K7025076"; //단말 모델아이디
            String reqModelNm = "SM-A217NK"; //모델명
            String sntyCapacCd = "02"; //단품용량코드 :: modelCapacityCd
            String sntyCapacNm = "32GB"; //단품용량코드 :: modelCapacityNm
            String sntyColorCd = "02"; //색상코드 :: modelColorCd
            String sntyColorNm = "화이트"; //색상명 :: modelColorNm
            String reqModelColor = "WE"; //모델색상

            String modelSalePolicyCd = "N2022011018381"; //단말 출고가 정책코드
            //String modelSalePolicyCd = ""; //단말 출고가 정책코드
            //String modelId = "K7032824"; //모델아이디
            String modelMonthly = "24"; //단말 할부개월수 [고정]
            //Long realMdlInstamt = 1155000L; //실제단말할부원금
            //Long modelPrice = 1050000L; //단말출고가
            //Long modelPriceVat = 105000L; //단말 출고가 부가세
            //Long modelSprt = 72000L; //공시지원금
            //Long modelDiscount1 = 0L; //제조사장려금
            //Long modelDiscount3 = 72000L; //대리점보조금
            //Long modelInstamt = 603000L; //단말 할부원금
            //Long hndsetSalePrice = 603000L; //단말기 판매가
            String sprtTypeCd = "KD"; //지원금유형코드 [고정]
            //Long dcAmt = 0L; //할인금액
            //Long addDcAmt = 0L; //추가할인금액
            //Long maxApdSprt = 72000L; //추가지원금(MAX)
            Long enggMnthCnt = 24L; //약정개월수 [고정]
            String socCode = "PL209L168"; //요금제코드
            String socNm = "LTE 데이터 실속 1GB/100분"; //요금제명
            //String socNm = ""; //요금제명
            //Long socBaseChrgAmt = 18000L; //요금제 기본료

            //1. 판매정책조회
            ProductInfoRequest productInfoRequest = new ProductInfoRequest();
            productInfoRequest.setPlcyTypeCd("N"); //고정값:위탁온라인(N)?? @@확인필요@@
            productInfoRequest.setOrgnId("1100014062"); //세션으로 변경필수!!!!!!!!!!!!!!!!!!!!!!!!!!!
            if (!StringUtils.hasText(request.getReqBuyTypeCd())) {
                productInfoRequest.setReqBuyTypeCd(ReqBuyType.MOBILE); //단말로 고정
            }
            //판매정책조회
            /*modelSalePolicyCd = productInfoService.getMspSalePlcyMstList(productInfoRequest)
            .stream()
            .findFirst()
            .map(MspSalePlcyMstInfoDto::getSalePlcyCd)
            .orElse(null);*/

            //2. 단말 조회
            List<PhoneInfoResponse> phoneInfoList = new ArrayList<>();
            /*if (StringUtils.hasText(modelSalePolicyCd)) {
                phoneInfoList = productInfoService.getPhoneList(productInfoRequest);
                if (!phoneInfoList.isEmpty()) {
                    PhoneInfoResponse phoneInfoFirst = phoneInfoList.stream()
                        .findFirst()
                        .orElse(null);
                    prodId = phoneInfoFirst.getProdId(); //msf_request.PROD_ID :: 상품아이디 (3308)
                    prodNm = phoneInfoFirst.getProdNm(); //msf_request.PROD_NM :: 상품명 (갤럭시 A32)
                    modelId = phoneInfoFirst.getModelId(); //msf_request_sale.MODEL_ID :: 단말 모델아이디 (K7004226) - 대표단말 아이디
                    reqModelNm = phoneInfoFirst.getReqModelNm(); //MSF_REQUEST.REQ_MODEL_NM :: 모델명 (SM-A325NK)
                    //sntyCapacCd = phoneInfoFirst.getModelCapacityCd(); //단품용량코드(02)
                    //sntyColorCd = phoneInfoFirst.getModelColorCd(); //색상코드(02)
                    //reqModelColor = phoneInfoFirst.getreq //모델색상 (WE)
                }
            }*/

            //prod_id :: 고객포탈 상품아이디
            //prod_nm :: 단말 상품명
            //model_id :: 단말 모델아이디
            //req_model_nm :: 단말 모델명

            //3. 요금제 조회
            List<RateInfoDto> rateInfoDtoList = productInfoService.getRateList(productInfoRequest);
            /*if (!rateInfoDtoList.isEmpty()) {
                if (!rateInfoDtoList.isEmpty()) {
                    RateInfoDto rateInfoFirst = rateInfoDtoList.stream()
                        .findFirst()
                        .orElse(null);
                    socCode = rateInfoFirst.getRateCd();
                    socNm = rateInfoFirst.getRateNm();
                }
            }*/

            response.setProdId(prodId);
            //response.setProdNm(prodNm);
            response.setProdNm(prodNm);
            response.setReqModelNm(reqModelNm);
            response.setSntyCapacCd(sntyCapacCd);
            response.setSntyCapacNm(sntyCapacNm); //db 저장 컬럼 없음.
            response.setSntyColorCd(sntyColorCd);
            response.setSntyColorNm(sntyColorNm); //db 저장 컬럼 없음.

            response.setReqModelColor(reqModelColor);
            response.setModelSalePolicyCd(modelSalePolicyCd);
            response.setModelId(modelId);
            response.setModelMonthly(modelMonthly);
            response.setSprtTypeCd(sprtTypeCd);
            response.setEnggMnthCnt(enggMnthCnt);
            response.setSocCode(socCode);
            response.setSocNm(socNm);
        }
        return response;
    }

    //신청서 상세 조회 (NewChangeInfoRequest 형태로 반환)
    public NewChangeInfoResponse getNewChangeRequestInfo(NewChangeRequest request) {
        MsfRequestRecord msfRequestRecord = this.getNewChangeInfo(request);
        return NewChangeFieldMapper.INSTANCE.toNewChangeInfoResponse(msfRequestRecord);

        /*if (request.getRequestKey() == null) {
            NewChangeDefaultResponse newChangeDefaultResponse = this.getNewChangeDefaultInfo(request);
            return newChangeDefaultResponse;
        } else {
            MsfRequestRecord msfRequestRecord = this.getNewChangeInfo(request);
            return NewChangeFieldMapper.INSTANCE.toNewChangeInfoResponse(msfRequestRecord);
        }*/
    }

    //eForm 생성을 위한 유효성 검증 및 데이터 저장 그리고 데이터 조회하여 전달
    public FormResponse<NewChangeInfoResponse> eformNewChangeSet(NewChangeInfoRequest request) {
        NewChangeInfoResponse response = new NewChangeInfoResponse();
        //0. 신청서 번호 확인
        if (request.getRequestKey() == null || request.getRequestKey() <= 0) {
            return FormResponse.of(ResponseMessage.F_BIND_EXCEPTION, response);
        }

        //1. 신청서 데이타 유효성검증
        //  this.checkNewChangeInfoData

        //2. 신청서 저장 (tmp_stat_cd : 3)
        //  this.saveAppformInfo(request);

        //3. 신청서 데이타 조회
        NewChangeRequest newChangeRequest = new NewChangeRequest();
        newChangeRequest.setRequestKey(request.getRequestKey());
        response = this.setNewChangeEformData(newChangeRequest);

        return FormResponse.of(ResponseMessage.SUCCESS, response);
        //MsfRequestRecord msfRequestRecord = this.getNewChangeInfo(request);
        //return NewChangeFieldMapper.INSTANCE.toNewChangeInfoResponse(msfRequestRecord);
    }

    //eForm 생성을 위한 데이타 조회
    public NewChangeInfoResponse setNewChangeEformData(NewChangeRequest request) {
        return this.getNewChangeRequestInfo(request);
    }

    //신청서 데이타 유효성 검증
    public NewChangeResponse checkNewChangeInfoData(NewChangeInfoRequest request) {
        NewChangeResponse response = new NewChangeResponse();
        return response;
    }

    //신청서 작성자 유효성 검증
    public boolean checkFormUser(long requestKey) {
        NewChangeRequest newChangeRequest = new NewChangeRequest();
        boolean isValid = false;

        //세션정보의 사용자, 대리점, 판매점조직 코드비교하여 정상여부 판단
        newChangeRequest.setRequestKey(requestKey);
        newChangeRequest.setManagerCd(AuthenticationUtils.getUser().getUserId());
        newChangeRequest.setAgentCd(AuthenticationUtils.getAgentCode());
        newChangeRequest.setShopCd(AuthenticationUtils.getShopCode());
        Integer newChangeFormCnt = newChangeReadMapper.checkFormUser(newChangeRequest);
        if (newChangeFormCnt > 0) {
            isValid = true;
        }
        return isValid;
    }

    //신청서 저장
    @Transactional
    public FormResponse<NewChangeResponse> saveAppformInfo(NewChangeInfoRequest request) {
        //임시저장일 경우 저장된 신청서의 아이디와 세션의 아이디 일치 여부 확인
        //임시저장, 신청서확인, 작성완료에 모두 공통으로 사용할 method 만들어서 공통으로 처리할 것
        boolean isValid = true;
        if (request.getRequestKey() != null) {
            isValid = this.checkFormUser(request.getRequestKey());
        }

        //유효하지 않을 경우 처리
        if (!isValid) {
            return FormResponse.of(ResponseMessage.NO_DATA);
        }

        //기기변경사유 - 변환처리

        //신청서 유효성체크 start
        //단말/요금제로 예상금액 재계산~~~ 데이터저장
        //신청서 유효성체크 end

        //작성자 정보 DATA SET
        //로그인 정보로 세션에서 조회
        String managerCd = AuthenticationUtils.getUser().getUserId();
        String managerNm = AuthenticationUtils.getUser().getUserName();
        String agentCd = AuthenticationUtils.getAgentCode();
        String agentNm = AuthenticationUtils.getAgentName();
        String shopCd = AuthenticationUtils.getShopCode();
        String shopNm = AuthenticationUtils.getShopName();

        request.setManagerCd(managerCd); //사용자아아디
        request.setManagerNm(managerNm); //사용자명
        request.setAgentCd(agentCd); //대리점코드
        request.setAgentNm(agentNm); //대리점명
        request.setShopCd(shopCd); //판매점코드
        request.setShopNm(shopNm); //판매점명


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
                    msfRequestAdditionVo.setAdditionId(dto.getAdditionId());
                    msfRequestAdditionVo.setAdditionNm(dto.getAdditionNm());
                    msfRequestAdditionVo.setRantal(dto.getRantal());
                    additionDtoList.add(msfRequestAdditionVo);

                    if (nameBuilder.length() > 0) {
                        nameBuilder.append(",");
                    }
                    nameBuilder.append(dto.getAdditionNm().trim());
                    if (dto.getRantal() > 0) {
                        reqAdditionPrice += dto.getRantal();
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

            //신청서 확인 시 가입진행코드는 접수(00) 으로 세팅
            if ("3".equals(request.getTmpStepCd())) {
                record.msfRequestVo().setSbscProCd("00"); //가입진행코드
                record.msfRequestVo().setProSttusCd(""); //진행상태코드
            }

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

    @Transactional
    public FormResponse<NewChangeResponse> completeAppformInfo(NewChangeRequest request) {
        NewChangeResponse response = new NewChangeResponse();
        String resNo = ""; //
        String sbscProdCd = ""; //
        String tempYn = ""; //임시저장인지 접수완료인지
        MsfRequestVo msfRequestVo = new MsfRequestVo();

        //@NotBlank 로 처리안되어 아래와 같이 처리
        if (request.getRequestKey() == null || request.getRequestKey() <= 0) {
            return FormResponse.of(ResponseMessage.F_BIND_EXCEPTION, response);
        } else {
            //0. 신청서 작성 중에 고객영역 다음단계에서 저장 시 희망번호 조회 및 사전동의 요청의
            //   필수 전단계로 고객생성 및 개통전 사전체크 진행을 위해
            //   MCP_REQUEST 테이블에 미리 저장하여 MP를 통한 PC0 서비스를 호출함.
            //   MCP_REQUEST_OSST 테이블에 서비스 호출결과를 마지막에 저장함. (이건 언제 MSF_REQUEST_OSST 테이블에 저장하지?)

            //1. requestKey 로 MSF_REQUEST 로 저장된 내역이 있는지 확인
            //   존재한다면 잘못된 요청으로 처리
            request.setTempYn("N"); //MSF_REQUEST 테이블 조회를 위한 설정
            msfRequestVo = this.getMsfRequestInfo(request);
            if (msfRequestVo != null) {
                return FormResponse.of(ResponseMessage.F_BIND_EXCEPTION, response);
            }

            //2. requestKey 로 MSF_REQUEST_TEMP 에서 데이타 조회하여 유효성검증 및 데이타 추출
            //   [1] 작성자아이디와 세션의 작성자아이디 일치여부 확인
            //   [2] MSF_REQUEST_TEMP 테이블의 TMP_STAT_CD 값은 신청서 확인 (EFORM) 시 "3" 값으로 저장 후 연동함으로 상태값 확인
            //   [3] RES_NO 와 SBSC_PROD_CD 는 MSF_REQUEST_STATE 테이블에 저장해야하므로 여기서 추출
            request.setTempYn("Y"); //MSF_REQUEST_TEMP 테이블 조회를 위한 설정
            msfRequestVo = this.getMsfRequestInfo(request);
            resNo = msfRequestVo.getResNo();
            sbscProdCd = msfRequestVo.getSbscProCd(); //가입진행코드는 신청서확인 시 00 으로 처리하여 가져옴 (00 접수대기)

            //3. 신청서 정보 저장 ( INSERT INTO ~ SELECT ) 8종 테이블
            //BeanUtils.copyProperties(mcpRequestDto, request);
            //MSF_REQUEST.PRO_STTUS_CD --  MCP_REQUEST.PSTATE // 진행상태코드
            //MSF_REQUEST.SBSC_PRO_CD --   MCP_REQUEST.REQUEST_STATE_CODE // 가입진행코드
            newChangeWriteMapper.insertMsfRequest(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestCstmr(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestAgent(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestSale(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestBillReq(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestMove(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestDvcChg(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestAddition(request.getRequestKey());

            //4. 개통전 사전체크 진행 - 신규가입/번호이동/기기변경
            //   완료되면 5번의 MSF_REQUEST_STATE 테이블에 최종 저장

            //5. MSF_REQUEST_STATE 테이블 저장
            Long requestStateSeq = formCommService.generateRequestStateSeq();
            MsfRequestStateVo msfRequestStateVo = new MsfRequestStateVo();
            msfRequestStateVo.setRequestKey(request.getRequestKey());
            msfRequestStateVo.setRequestStateKey(requestStateSeq);
            msfRequestStateVo.setResNo(resNo); //요건 가져오는 것 처리 필요함.
            msfRequestStateVo.setSbscProCd(sbscProdCd); //가입진행코드 : 00 접수대기
            newChangeWriteMapper.insertMsfRequestState(msfRequestStateVo);

            //6. 모든 과정이 정상이면 return 해야할 정보 추출
            //MsfRequestCstmrVo msfRequestCstmrVo = this.getMsfRequestCstmrInfo(request); //고객정보 확인
            //response.setRequestKey(msfRequestVo.getRequestKey()); //신청서번호
            //response.setFormType("1"); //신규/변경 신청서
            //response.setCstmrNm(msfRequestCstmrVo.getCstmrNm()); //고객명
            //response.setCstmrMobileNo(msfRequestCstmrVo.getCstmrMobileFnNo() + msfRequestCstmrVo.getCstmrMobileMnNo() + msfRequestCstmrVo.getCstmrMobileRnNo()); //고객연락처

            //# 고객포탈 최종 저장 시 관련 테이블 목록
            //  /appForm/saveAppformAjax.do
            //  MCP_REQUEST_OSST 는 언제 저장하고 MSF_REQUEST_OSST 에는 언제 업데이트 해야하는지 시점 결정필요.
            //  여기서는 저장하는 건 없음.
            //[1] MCP_REQUEST               MSF_REQUEST             가입신청
            //[2] MCP_REQUEST_CSTMR         MSF_REQUEST_CSTMR       고객정보
            //[3] MCP_REQUEST_AGENT         MSF_REQUEST_AGENT       대리인
            //[4] MCP_REQUEST_MOVE          MSF_REQUEST_MOVE        번호이동
            //MCP_REQUEST_PAYMENT       선불충전
            //[5] MCP_REQUEST_SALEINFO      MSF_REQUEST_SALE        판매정보
            //MCP_REQUEST_CHANGE        명의변경
            //MCP_REQUEST_DLVRY         배송정보
            //[6] MCP_REQUEST_REQ           MSF_REQUEST_BILL_REQ    청구정보
            //MCP_REQUEST_PAY_INFO      결제정보
            //[7] MCP_REQUEST_DVC_CHG       MSF_REQUEST_DVC_CHG     기변사유
            //[8] MCP_REQUEST_ADDITION      MSF_REQUEST_ADDITION    부가서비스
            //MCP_REQUEST_KT_INTER      KT인터넷관련정보
            //NMCP_GIFT_REQ_TXN         사은품정보
            //MCP_REQUEST_STATE         MSF_REQUEST_STATE       진행상태
            //NMCP_REQUEST_APD
            //NMCP_REQUEST_APD_DLVRY
            //NMCP_REQUEST_APD_SALEINFO
            //NMCP_REQUEST_APD_STATE
            //MCP_REQUEST_COMMEND
            //MCP_REQUEST_DTL           신청서 상세 정보 등록
        }

        /*  MCP_REQUEST.PSTATE
            코드값	명칭
            00	정상
            10	고객취소
            20	관리자삭제
            30	예약번호삭제
            40	관리자삭제(유심번호미입력)
        */
        /*
         * MCP_REQUEST.REQUEST_STATE_CODE // MCP_REQUEST_STATE.REQUEST_STATE_CODE 코드값 명칭
         * 00 접수대기   01 접수   02 해피콜   03 신청서배송   04 신청서배송완료   07 배송대기(퀵)   08 배송대기(소화물)
         *   09 배송대기(택배)   10 배송중   11 배송완료   13 배송완료(유심등록완료)   20 개통대기   21 개통완료  
         *  30 사전체크오류   31 개통처리오류  
         */

        //return FormResponse.of(ResponseMessage.SUCCESS, response);
        return FormResponse.of(ResponseMessage.SUCCESS);
    }


}
