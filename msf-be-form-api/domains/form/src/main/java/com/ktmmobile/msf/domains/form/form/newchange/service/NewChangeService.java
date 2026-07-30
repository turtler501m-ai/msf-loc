package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.code.FormStepType;
import com.ktmmobile.msf.domains.form.common.code.OperType;
import com.ktmmobile.msf.domains.form.common.code.ReqBuyType;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.AppformReqDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestRecDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSalePlcyMstInfoDto;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.ProductInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.RateInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestRecVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSaleVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.FormStatusRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.FormStatusResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MpPreCheckRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MpPreCheckResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MsfRequestRecord;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeDefaultResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;
import com.ktmmobile.msf.domains.form.form.newchange.field.NewChangeFieldMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.NewChangeMspReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.NewChangeMspWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeMpReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeWriteMapper;
import com.ktmmobile.msf.domains.shared.form.common.generate.application.port.out.GenerateKeyRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewChangeService {

    private final FormCommService formCommService;
    private final ProductInfoService productInfoService;
    private final NewChangeDBSelectService newChangeDBSelectService;
    private final NewChangeFormSave newChangeFormSave;
    private final MpPreCheckService mpPreCheckService;
    private final ProductSmartInfoWriteMapper productSmartInfoWriteMapper;

    private final NewChangeReadMapper newChangeReadMapper;
    private final NewChangeWriteMapper newChangeWriteMapper;
    private final GenerateKeyRepository generateKeyRepository;
    private final NewChangeMspWriteMapper newChangeMspWriteMapper;
    private final NewChangeMspReadMapper newChangeMspReadMapper;
    private final NewChangeMpReadMapper newChangeMpReadMapper;

    //신청서 조회
    public MsfRequestRecord getNewChangeInfo(NewChangeRequest request) {

        return new MsfRequestRecord(
            newChangeDBSelectService.getMsfRequestInfo(request), //MSF_REQUEST
            newChangeDBSelectService.getMsfRequestCstmrInfo(request), //MSF_REQUEST_CSTMR
            newChangeDBSelectService.getMsfRequestAgentInfo(request), //MSF_REQUEST_AGENT
            newChangeDBSelectService.getMsfRequestSaleInfo(request), //MSF_REQUEST_SALE
            newChangeDBSelectService.getMsfRequestBillReqInfo(request), //MSF_REQUEST_BILL_REQ
            newChangeDBSelectService.getMsfRequestMoveInfo(request), //MSF_REQUEST_MOVE
            newChangeDBSelectService.getMsfRequestDvcChgInfo(request), //MSF_REQUEST_DVC_CHG
            newChangeDBSelectService.getMsfRequestAdditionInfo(request) //MSF_REQUEST_ADDITION
        );
    }


    // 최초 진입 시 초기값 설정
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

            //2026.05.26
            //ProductInfoRequest phoneInfoRequest = new ProductInfoRequest();
            //PhoneInfoResponse phoneInfoResponse = new PhoneInfoResponse();
            //List<PhoneInfoResponse> phoneInfoResponseList = productInfoService.getPhoneList(phoneInfoRequest);
            //log.debug("phoneInfoResponseList =================== ", phoneInfoResponseList.toString());
            //PhoneInfoResponse defaultPhoneInfo = phoneInfoResponseList.stream().findFirst().orElse(null);
            //
            //String agentCd = AuthenticationUtils.getAgentCode(); //로그인 사용자의 조직코드
            //String setPlcyTypeCd = "N"; //고정값:위탁온라인(N)?? @@확인필요@@
            //String prodId = defaultPhoneInfo.getProdId(); //상품아이디 - 3208
            //String prodNm = defaultPhoneInfo.getProdNm(); //상품명 - 갤럭시 A21s
            //String modelId = defaultPhoneInfo.getModelId(); //단말 모델아이디 - K7025076
            //String reqModelNm = defaultPhoneInfo.getReqModelNm(); //모델명 - SM-A217NK
            //String modelSalePolicyCd = defaultPhoneInfo.getSalePlcyCd(); //단말 출고가 정책코드 - N2022011018381
            //
            //String modelMonthly = "24"; //단말 할부개월수 [고정]
            //String sprtTypeCd = "KD"; //지원금유형코드 [고정]
            //Long enggMnthCnt = 24L; //약정개월수 [고정]
            //
            //String sntyCapacCd = "01"; //단품용량코드 :: modelCapacityCd
            //String sntyCapacNm = "32GB"; //단품용량코드 :: modelCapacityNm
            //String sntyColorCd = "01"; //색상코드 :: modelColorCd
            //String sntyColorNm = "블루"; //색상명 :: modelColorNm
            //String reqModelColor = "BL"; //모델색상
            //
            //String socCode = "PL209L168"; //요금제코드
            //String socNm = "LTE 데이터 실속 1GB/100분"; //요금제명

            //String agentCd = AuthenticationUtils.getAgentCode(); //로그인 사용자의 조직코드
            String agentCd = ""; //최초진입시에는 대리점코드 없음.
            AgentInfoRequest agentInfoRequest = new AgentInfoRequest();
            List<AgentInfoResponse> agentInfoResponseList = formCommService.getAgentList(agentInfoRequest);
            if (agentInfoResponseList == null) {
                return null;
            } else {
                agentCd = agentInfoResponseList.getFirst().getOrgnId();
            }

            String setPlcyTypeCd = "N"; //고정값:위탁온라인(N)?? @@확인필요@@
            String modelMonthly = "24"; //단말 할부개월수 [고정]
            String sprtTypeCd = "KD"; //지원금유형코드 [고정]
            Long enggMnthCnt = 24L; //약정개월수 [고정]
            String modelSalePolicyCd = ""; //판매정책코드
            String prodId = ""; //상품아이디 - 3208
            String prodNm = ""; //상품명 - 갤럭시 A21s
            String modelId = ""; //단말 모델아이디 - K7025076
            String reqModelNm = ""; //모델명 - SM-A217NK
            String socCode = ""; //요금제코드
            String socNm = ""; //요금제명
            //String rprsPrdtId = ""; //대표단말코드
            String sntyColorCd = ""; //색상코드(02)
            String reqModelColor = ""; //모델색상 (WE)
            String sntyCapacCd = ""; //단말용량코드
            String sntyCapacNm = ""; //단말용량명

            ProductInfoRequest productInfoRequest = new ProductInfoRequest();
            productInfoRequest.setPlcyTypeCd(setPlcyTypeCd); //판매정책코드
            productInfoRequest.setOrgnId(agentCd); //로그인 세션의 사용자 조직코드
            productInfoRequest.setReqBuyTypeCd(ReqBuyType.MOBILE); //단말로 고정
            productInfoRequest.setOperTypeCd(OperType.MOBILE_NUMBER_PORTABILITY.getCode()); //단말로 고정
            productInfoRequest.setModelMonthly(modelMonthly);

            //1. 판매정책조회

            List<MspSalePlcyMstInfoDto> salePlcyList = productInfoService.getMspSalePlcyMstList(productInfoRequest);
            if (!salePlcyList.isEmpty()) {
                modelSalePolicyCd = salePlcyList
                    .stream()
                    .findFirst()
                    .map(MspSalePlcyMstInfoDto::getSalePlcyCd)
                    .orElse("");
            }

            if (!modelSalePolicyCd.isEmpty()) {
                log.debug("modelSalePolicyCd ========== ", modelSalePolicyCd);
            }

            //2. 단말 조회
            List<PhoneInfoResponse> phoneInfoList = new ArrayList<>();
            if (StringUtils.hasText(modelSalePolicyCd)) {
                phoneInfoList = productInfoService.getPhoneList(productInfoRequest);
                if (!phoneInfoList.isEmpty()) {
                    PhoneInfoResponse phoneInfoFirst = phoneInfoList.stream()
                        .findFirst()
                        .orElse(new PhoneInfoResponse());
                    prodId = phoneInfoFirst.getProdId(); //msf_request.PROD_ID :: 상품아이디 (3308)
                    prodNm = phoneInfoFirst.getProdNm(); //msf_request.PROD_NM :: 상품명 (갤럭시 A32)
                    modelId = phoneInfoFirst.getModelId(); //msf_request_sale.MODEL_ID :: 단말 모델아이디 (K7004226) - 대표단말 아이디
                    reqModelNm = phoneInfoFirst.getReqModelNm(); //MSF_REQUEST.REQ_MODEL_NM :: 모델명 (SM-A325NK)
                    //rprsPrdtId = modelId; //대표단말이겠지? ㅎ
                    //sntyCapacCd = phoneInfoFirst.getModelCapacityCd(); //단품용량코드(02)
                    //sntyColorCd = phoneInfoFirst.getModelColorCd(); //색상코드(02)
                    //reqModelColor = phoneInfoFirst.getreq //모델색상 (WE)
                }
            }

            //단말 색상조회
            //List<PhoneModelColorResponse> prdtColorList = new ArrayList<>();
            //if (StringUtils.hasText(rprsPrdtId)) {
            //    productInfoRequest.setRprsPrdtId(rprsPrdtId); //대표단말코드
            //    prdtColorList = productInfoService.getPrdtColorList(productInfoRequest);
            //    if (!prdtColorList.isEmpty() && !prdtColorList.isEmpty()) {
            //        PhoneModelColorResponse prdtColorInfoFirst = prdtColorList.stream()
            //            .findFirst()
            //            .orElse(null);
            //        sntyColorCd = prdtColorInfoFirst.getModelColorCd(); //색상코드(02)
            //        reqModelColor = prdtColorInfoFirst.getModelColorNm(); //모델색상 (WE)
            //    }
            //}

            //단말 용량조회
            //List<PhoneModelCapacityResponse> prdtCapacityList = new ArrayList<>();
            //if (StringUtils.hasText(prodId)) {
            //    productInfoRequest.setProdId(prodId); //단말코드 숫자 3자리
            //    prdtCapacityList = productInfoService.getPrdtCapacityList(productInfoRequest);
            //    if (!prdtCapacityList.isEmpty() && !prdtCapacityList.isEmpty()) {
            //        PhoneModelCapacityResponse prdtCapacityInfoFirst = prdtCapacityList.stream()
            //            .findFirst()
            //            .orElse(null);
            //        sntyCapacCd = prdtCapacityInfoFirst.getModelCapacityCd(); //
            //        sntyCapacNm = prdtCapacityInfoFirst.getModelCapacityNm(); //
            //    }
            //}

            //3. 요금제 조회
            List<RateInfoResponse> rateInfoDtoList = new ArrayList<>();
            if (StringUtils.hasText(modelSalePolicyCd)) { //판매정책코드가 없는 경우 조회하지 않도록 함. 기본값을 휴대폰으로 설정하므로?
                //rateInfoDtoList = productInfoService.getRateList(productInfoRequest);
                rateInfoDtoList = productInfoService.getRateListByCategory(productInfoRequest);
                if (!rateInfoDtoList.isEmpty()) {
                    RateInfoResponse rateInfoFirst = rateInfoDtoList.stream()
                        .findFirst()
                        .orElse(new RateInfoResponse());
                    socCode = rateInfoFirst.getRateCd();
                    socNm = rateInfoFirst.getRateNm();
                }
            }

            response.setAgentCd(agentCd); //로그인 사용자의 조직코드
            response.setProdId(prodId); //상품아이디 - 3208
            response.setProdNm(prodNm); //상품명 - 갤럭시 A21s
            response.setReqModelNm(reqModelNm); //모델명 - SM-A217NK
            response.setModelId(modelId); //단말 모델아이디 - K7025076
            response.setModelSalePolicyCd(modelSalePolicyCd); //판매정책코드
            response.setModelMonthly(modelMonthly); //단말 할부개월수 [고정]
            response.setSprtTypeCd(sprtTypeCd); //지원금유형코드 [고정]
            response.setEnggMnthCnt(enggMnthCnt); //약정개월수 [고정]
            response.setSocCode(socCode); //요금제코드
            response.setSocNm(socNm); //요금제명
            response.setSntyColorCd(sntyColorCd); //단말색상
            response.setSntyColorNm(reqModelColor); //단말색상
            //response.setReqModelColor(reqModelColor); //단말색상
            response.setSntyCapacCd(sntyCapacCd); //단말용량코드
            response.setSntyCapacNm(sntyCapacNm); //단말용량명
        }
        return response;
    }

    public void initNewChangeIdentityColumns(MsfRequestRecord msfRequestRecord) {
        msfRequestRecord.msfRequestVo().setKnoteIdentityScanCstmrNm("");
        msfRequestRecord.msfRequestVo().setKnoteIdentityEssNo("");
        msfRequestRecord.msfRequestVo().setKnoteIdentityTypeCd("");
        //msfRequestRecord.msfRequestVo().setKnoteIdentityScanDt("");
        msfRequestRecord.msfRequestVo().setKnoteScanId("");
        msfRequestRecord.msfRequestVo().setFathTrgYn("");
        msfRequestRecord.msfRequestVo().setFathTrgIdentityCertTypeCd("");
        msfRequestRecord.msfRequestVo().setFathTransacId("");
        msfRequestRecord.msfRequestVo().setFathCmpltNtfyDate("");
        msfRequestRecord.msfRequestVo().setFathTelNo("");
        msfRequestRecord.msfRequestVo().setFathMobileFnNo("");
        msfRequestRecord.msfRequestVo().setFathMobileMnNo("");
        msfRequestRecord.msfRequestVo().setFathMobileRnNo("");
        msfRequestRecord.msfRequestVo().setAuthInfo("");
        msfRequestRecord.msfRequestVo().setIdentityTypeCd("");
        msfRequestRecord.msfRequestVo().setIdentityIssuDate("");
        msfRequestRecord.msfRequestVo().setIdentityIssuRegion("");
        msfRequestRecord.msfRequestVo().setSelfIssuNo("");
        msfRequestRecord.msfRequestVo().setDriveLicnsNo("");
    }

    /**
     * 신청서 상세 조회 (NewChangeInfoRequest 형태로 반환)
     */
    public NewChangeInfoResponse getNewChangeRequestInfo(NewChangeRequest request) {

        //신청서번호가 없을 경우 RETURN
        if (request.getRequestKey() == null) {
            return null;
        }

        //신청서번호가 있을 경우, 임시저장 진입일 경우 세션의 아이디 일치 여부 확인
        NewChangeInfoRequest newChangeInfoRequest = new NewChangeInfoRequest();
        newChangeInfoRequest.setRequestKey(request.getRequestKey());
        newChangeInfoRequest.setTmpStepCd(FormStepType.COMPLETE_STEP.getCode()); //작성완료단계
        if (request.getRequestKey() != null) {
            int newChangeFormCount = newChangeFormSave.getNewChangeForm(newChangeInfoRequest);

            //본인 작성 신청서 아님.
            if (newChangeFormCount == 0) {
                return null;
            }
        }

        //신청서번호가 본인 정상인 경우
        request.setTempYn("Y"); //임시저장 테이블을 조회하도록 함.
        request.setShowAll("Y"); //전체 컬럼

        //신청서 저장 데이타 조회
        MsfRequestRecord msfRequestRecord = this.getNewChangeInfo(request);

        //KNOTE 신분증 상태 변경 처리 (R) :: KNOTE 신분증유형코드가 있고 KNOTE 신분증아이디가 존재하면 실행 - 2026.07.14
        log.debug("신청서 진입 시 KNOTE신분증아이디(msfRequestRecord.msfRequestVo().getKnoteScanId()):  {}", msfRequestRecord.msfRequestVo().getKnoteScanId());
        if (StringUtils.hasText(msfRequestRecord.msfRequestVo().getKnoteIdentityTypeCd()) && StringUtils.hasText(msfRequestRecord.msfRequestVo()
            .getKnoteScanId())) {
            FormStatusRequest formStatusRequest = getFormStatusRequest(request, msfRequestRecord);
            FormStatusResponse formStatusResponse = mpPreCheckService.callFS2(formStatusRequest);

            //KNOTE신분증아이디 상태변경 실패 - 유효하지 않은 신분증의 상태변경 실패까지 처리하지 않아도 될듯
            if (!"N".equals(formStatusResponse.getRsltCd())) {
                log.debug("knote서식지 상태변경 결과 : ", formStatusResponse.getRsltCd() + "_" + formStatusResponse.getRsltMsg());
            }
        }

        //초기화 (민감정보)
        this.initNewChangeIdentityColumns(msfRequestRecord);

        //유심종류 프론트로 데이타 변경 처리
        String usimKindsCd = msfRequestRecord.msfRequestVo().getUsimKindsCd();
        if (StringUtils.hasText(usimKindsCd) && ("02".equals(usimKindsCd) || "07".equals(usimKindsCd) || "08".equals(usimKindsCd))) {
            msfRequestRecord.msfRequestVo().setUsimKindsCd("02"); //유심구매로 처리
        }

        //예약번호 신규생성 - 2026.07.21
        if (msfRequestRecord != null) {
            //msfRequestRecord.msfRequestVo().setResNo(generateKeyRepository.getGeneratedResNo());
            String newResNo = generateKeyRepository.getGeneratedResNo();
            NewChangeRequest newChangeRequest = new NewChangeRequest();
            newChangeRequest.setResNo(newResNo);
            newChangeRequest.setRequestKey(request.getRequestKey());
            log.debug("신청서 진입 시 예약번호 재발급 >> request.getRequestKey(): {}, resNo: {}", request.getRequestKey(), newResNo);
            newChangeWriteMapper.updateMsfRequestResNo(newChangeRequest);
            msfRequestRecord.msfRequestVo().setResNo(newResNo);
        }

        //조회한 신청서 데이타 리턴
        return NewChangeFieldMapper.INSTANCE.toNewChangeInfoResponse(msfRequestRecord);
    }

    /**
     * KNOTE신분증 복구
     */
    private static @NonNull FormStatusRequest getFormStatusRequest(NewChangeRequest request, MsfRequestRecord msfRequestRecord) {
        FormStatusRequest formStatusRequest = new FormStatusRequest();
        formStatusRequest.setRequestKey(request.getRequestKey());
        formStatusRequest.setOperTypeCd(msfRequestRecord.msfRequestVo().getOperTypeCd()); //가입유형
        formStatusRequest.setResNo(msfRequestRecord.msfRequestVo().getResNo()); //신청서 예약번호
        formStatusRequest.setKnoteScanId(msfRequestRecord.msfRequestVo().getKnoteScanId()); //KNOTE 신분증아이디
        formStatusRequest.setFrmpapStatCd("R"); //신분증 복구
        return formStatusRequest;
    }

    //신청서 저장 여부 판단 (로그인 사용자 정보)
    //public String getNewChangeFormStep(NewChangeRequest request) {
    //    NewChangeRequest newChangeRequest = new NewChangeRequest();
    //    newChangeRequest.setRequestKey(request.getRequestKey());
    //    newChangeRequest.setShopCd(AuthenticationUtils.getShopCode());
    //    //newChangeRequest.setManagerCd(AuthenticationUtils.getUser().getUserId());
    //    //newChangeRequest.setAgentCd(AuthenticationUtils.getAgentCode());
    //    return newChangeReadMapper.selectNewChangeFormStep(newChangeRequest);
    //}


    /**
     * 신청서 복사하기 ( to _temp 테이블로 )
     */
    @Transactional
    public FormResponse<NewChangeResponse> copyForm(NewChangeRequest request) {
        NewChangeResponse response = new NewChangeResponse();
        //String resNo = ""; //
        //String sbscProdCd = ""; //
        //String tempYn = ""; //임시저장인지 접수완료인지
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
            msfRequestVo = newChangeDBSelectService.getMsfRequestInfo(request);
            if (msfRequestVo == null) { //작성완료 데이터가 있어야 함.
                return FormResponse.of(ResponseMessage.F_BIND_EXCEPTION, response);
            }

            //2. requestKey 로 MSF_REQUEST_TEMP 에서 데이타 조회하여 유효성검증 및 데이타 추출
            //   [1] 작성자아이디와 세션의 작성자아이디 일치여부 확인
            //   [2] MSF_REQUEST_TEMP 테이블의 TMP_STAT_CD 값은 신청서 확인 (EFORM) 시 "3" 값으로 저장 후 연동함으로 상태값 확인
            //   [3] RES_NO 와 SBSC_PROD_CD 는 MSF_REQUEST_STATE 테이블에 저장해야하므로 여기서 추출
            //request.setTempYn("Y"); //MSF_REQUEST_TEMP 테이블 조회를 위한 설정
            //msfRequestVo = newChangeSelectService.getMsfRequestInfo(request);
            //resNo = msfRequestVo.getResNo();
            //sbscProdCd = msfRequestVo.getSbscProCd(); //가입진행코드는 신청서확인 시 00 으로 처리하여 가져옴 (00 접수대기)

            //신청서번호 생성
            //request.setNewRequestKey(formCommService.generateRequestKey());
            request.setNewRequestKey(generateKeyRepository.getGeneratedRequestKey()); //2026.06.30 변경
            //request.setResNo(generateKeyRepository.getGeneratedResNo()); //2026.07.20 추가, 2026.07.21 변경 : 임시저장진입(/newchange/get) 시에도 예약번호 생성하여 처리하므로 주석처리
            request.setProcCd(""); //2026.07.20 변경
            request.setSbscProCd("99"); //2026.07.20 변경
            request.setProSttusCd("99"); //2026.07.20 변경
            request.setTmpStepCd(FormStepType.AGREE_STEP.getCode()); //동의단계로 처리하나 화면에서는 상품단계까지 보임.

            //3. 신청서 정보 저장 ( INSERT INTO ~ SELECT ) 8종 테이블
            //BeanUtils.copyProperties(mcpRequestDto, request);
            //MSF_REQUEST.PRO_STTUS_CD --  MCP_REQUEST.PSTATE // 진행상태코드
            //MSF_REQUEST.SBSC_PRO_CD --   MCP_REQUEST.REQUEST_STATE_CODE // 가입진행코드
            /*newChangeWriteMapper.insertMsfRequest(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestCstmr(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestAgent(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestSale(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestBillReq(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestMove(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestDvcChg(request.getRequestKey());
            newChangeWriteMapper.insertMsfRequestAddition(request.getRequestKey());*/
            newChangeWriteMapper.insertCopyMsfRequest(request);
            newChangeWriteMapper.insertCopyMsfRequestCstmr(request);
            newChangeWriteMapper.insertCopyMsfRequestAgent(request);
            newChangeWriteMapper.insertCopyMsfRequestSale(request);
            newChangeWriteMapper.insertCopyMsfRequestBillReq(request);
            newChangeWriteMapper.insertCopyMsfRequestMove(request);
            newChangeWriteMapper.insertCopyMsfRequestDvcChg(request);
            newChangeWriteMapper.insertCopyMsfRequestAddition(request);

            //4. 개통전 사전체크 진행 - 신규가입/번호이동/기기변경
            //   완료되면 5번의 MSF_REQUEST_STATE 테이블에 최종 저장

            //5. MSF_REQUEST_STATE 테이블 저장
            /*Long requestStateSeq = formCommService.generateRequestStateSeq();
            MsfRequestStateVo msfRequestStateVo = new MsfRequestStateVo();
            msfRequestStateVo.setRequestKey(request.getRequestKey());
            msfRequestStateVo.setRequestStateKey(requestStateSeq);
            msfRequestStateVo.setResNo(resNo); //요건 가져오는 것 처리 필요함.
            msfRequestStateVo.setSbscProCd(sbscProdCd); //가입진행코드 : 00 접수대기
            newChangeWriteMapper.insertMsfRequestState(msfRequestStateVo);*/

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
        response.setRequestKey(request.getNewRequestKey());

        return FormResponse.of(ResponseMessage.SUCCESS, response);
    }

    /**
     * 작성완료 ( from _temp 테이블 )
     */
    //배포할때 아래 라인 있는 곳의 주석풀고 트랜잭션도 풀어주세요~
    //return FormResponse.of(ResponseMessage.VALID_SAVE_FAIL_PRE_CHECK); //개통전 사전체크 실패 - 배포할때 풀어야해요.
    @BusinessContextBoundary
    //@Transactional
    @SuppressWarnings("PMD.EmptyControlStatement")
    public FormResponse<NewChangeResponse> completeAppformInfo(NewChangeRequest request) {
        NewChangeResponse newChangeResponse = new NewChangeResponse();
        MsfRequestVo msfRequestVo = new MsfRequestVo();

        //String rsltCd = "";
        //String rsltMsg = "";

        //String preCheckRsltCd = ""; //개통전 사전체크 메세지를 따로 보여야할 경우 사용하기 위함.
        //String preCheckRsltMsg = ""; //개통전 사전체크 메세지를 따로 보여야할 경우 사용하기 위함.

        //MSF_REQUEST 132 PROC_CD 처리결과 o 코드관리(M포탈) (신청:RQ,처리:CP,반려:BK/공통코드:CL01)
        //String procCd = "RQ";

        //@NotBlank 로 처리안되어 아래와 같이 처리
        if (request.getRequestKey() == null || request.getRequestKey() <= 0) {
            return FormResponse.of(ResponseMessage.F_BIND_EXCEPTION, newChangeResponse);
        } else {
            //0. 신청서 작성 중에 고객영역 다음단계에서 저장 시 희망번호 조회 및 사전동의 요청의
            //   필수 전단계로 고객생성 및 개통전 사전체크 진행을 위해
            //   MCP_REQUEST 테이블에 미리 저장하여 MP를 통한 PC0 서비스를 호출함.
            //   MCP_REQUEST_OSST 테이블에 서비스 호출결과를 마지막에 저장함. (이건 언제 MSF_REQUEST_OSST 테이블에 저장하지?)

            //1. requestKey 로 MSF_REQUEST 로 저장된 내역이 있는지 확인
            //   존재한다면 잘못된 요청으로 처리
            request.setTempYn("N"); //MSF_REQUEST 테이블 조회를 위한 설정
            msfRequestVo = newChangeDBSelectService.getMsfRequestInfo(request);
            log.debug("임시테이블에서 원본테이블로 저장이 되어 있는지 확인 >> true 이면 정상, false 라면 비정상 : ", (msfRequestVo == null));
            if (msfRequestVo != null) {
                return FormResponse.of(ResponseMessage.F_BIND_EXCEPTION, newChangeResponse);
            }

            //[작성완료] 개통전 사전체크 진행여부 체크 START ------------------------------------------------------------------
            //임시저장 테이블에서 데이타 조회해서 진행 : 신규가입, 번호이동, 기기변경 공통사항
            request.setTempYn("Y"); //MSF_REQUEST_TEMP 테이블 조회를 위한 설정
            request.setShowAll("Y"); //MSF_REQUEST_TEMP 테이블 조회를 위한 설정
            msfRequestVo = newChangeDBSelectService.getMsfRequestInfo(request);
            MpPreCheckRequest mpPreCheckRequest = null;
            if (msfRequestVo != null) {
                mpPreCheckRequest = new MpPreCheckRequest();
                mpPreCheckRequest.setRequestKey(request.getRequestKey());
                mpPreCheckRequest.setOperTypeCd(msfRequestVo.getOperTypeCd());
                mpPreCheckRequest.setResNo(msfRequestVo.getResNo());
                mpPreCheckRequest.setKnoteScanId(msfRequestVo.getKnoteScanId());
                if (OperType.NEW_ACTIVATION.getCode().equals(msfRequestVo.getOperTypeCd()) || OperType.MOBILE_NUMBER_PORTABILITY.getCode()
                    .equals(msfRequestVo.getOperTypeCd())) {
                    mpPreCheckRequest.setPrgrStatCd("PC2");
                } else {
                    mpPreCheckRequest.setPrgrStatCd("HC2");
                }
                log.debug("[작성완료] 개통전 사전체크 진행여부 체크 >> RequestKey: {}, OperTypeCd: {}, ResNo: {}, KnoteScanId: {}, PrgrStatCd: {}",
                    mpPreCheckRequest.getRequestKey(),
                    mpPreCheckRequest.getOperTypeCd(),
                    mpPreCheckRequest.getResNo(),
                    mpPreCheckRequest.getKnoteScanId(),
                    mpPreCheckRequest.getPrgrStatCd());
            }

            //개통전 사전체크 진행결과 확인
            MpPreCheckResponse mpPreCheckResponse = mpPreCheckService.getNewChangeMpPreCheckResult(mpPreCheckRequest);
            if (mpPreCheckResponse != null) {
                newChangeResponse.setPreCheckResultCd(mpPreCheckResponse.getRsltCd()); // S(성공), F(실패)
                newChangeResponse.setPreCheckResultMsg(mpPreCheckResponse.getRsltMsg());

                log.debug("개통전 사전체크 진행결과 확인 >> request.getRequestKey(): {}, preCheckRsltCd: {}, preCheckRsltMsg: {}",
                    request.getRequestKey(),
                    mpPreCheckResponse.getRsltCd(),
                    mpPreCheckResponse.getRsltMsg());

                //서식지 상태변경 START :: FS2 -------------------------
                //개통전 사전체크 실패 , knote 신분증아이디 있고, 안면인증아이디 없을 때만 FS2 복구 하도록 함
                if ("F".equals(mpPreCheckResponse.getRsltCd()) && StringUtils.hasText(request.getKnoteScanId()) && !StringUtils.hasText(request.getFathTransacId())) { //S:성공, F:실패
                    String frmpapStatCd = "R"; //복구
                    FormStatusRequest formStatusRequest = new FormStatusRequest();
                    formStatusRequest.setRequestKey(mpPreCheckRequest.getRequestKey());
                    formStatusRequest.setOperTypeCd(request.getOperTypeCd()); //가입유형
                    formStatusRequest.setResNo(request.getResNo()); //신청서 예약번호
                    formStatusRequest.setKnoteScanId(request.getKnoteScanId());
                    formStatusRequest.setFrmpapStatCd(frmpapStatCd);

                    FormStatusResponse formStatusResponse = new FormStatusResponse();
                    formStatusResponse = mpPreCheckService.callFS2(formStatusRequest);
                    log.debug("개통전 사전체크 진행결과 실패일 때 서식지 복구처리 >> rsltCd: {}, rsltMsg: {}",
                        formStatusResponse.getRsltCd(),
                        formStatusResponse.getRsltMsg());

                    newChangeResponse.setRsltCd("F");
                    newChangeResponse.setRsltMsg("개통전 사전체크 결과 실패입니다. 내용은 [" + mpPreCheckResponse.getRsltMsg() + "] 입니다.");
                    //최종 오픈 시에는 실패할 경우 MSF 및 MCP 연동 불가 // 6번~8번까지 진행하지 않음으로 처리예정
                }
                //서식지 상태변경 END :: FS2 ---------------------------
            }
            //[작성완료] 개통전 사전체크 진행여부 체크 END --------------------------------------------------------------------

            //1-1 녹취파일 request & delete : 저장 전 처리
            newChangeWriteMapper.deleteMsfRequestRecTemp(request.getRequestKey());
            List<MsfRequestRecVo> msfRequestRecVoList = new ArrayList<>();
            List<MsfRequestRecDto> msfRequestRecList = request.getMsfRequestRecList();
            MsfRequestRecVo msfRequestRecVo = new MsfRequestRecVo();
            if (msfRequestRecList != null && !msfRequestRecList.isEmpty()) {
                for (MsfRequestRecDto recDto: msfRequestRecList) {
                    msfRequestRecVo = new MsfRequestRecVo();
                    msfRequestRecVo.setRequestKey(request.getRequestKey());
                    msfRequestRecVo.setRecFilePathNm(recDto.getRecFilePathNm());
                    msfRequestRecVo.setRecFileNm(recDto.getRecFileNm());
                    msfRequestRecVoList.add(msfRequestRecVo);
                }
            }

            //녹취정보 저장
            String recYn = "";
            if (msfRequestRecList != null && !msfRequestRecList.isEmpty() && msfRequestRecList.size() > 0) {
                //MSF_REQUEST_REC
                newChangeWriteMapper.insertMsfRequestRecListTemp(msfRequestRecVoList);

                //녹취여부는 작성완료에서 저장하고 MCP 연동도 되어야하므로 연동전에 temp 테이블에 저장이 되어야 함.
                recYn = "Y";
            }

            //MSF 임시테이블에 마지막 저장 ~> 개통전사전체크 ~> MCP 에 신청서번호로 조회해서 연동되므로 마지막 저장 처리
            NewChangeRequest newChangeRequest = new NewChangeRequest();
            newChangeRequest.setRequestClose("N"); //작성완료 항목으로 만료처리 시 parameter 로만 사용
            newChangeRequest.setRecYn(recYn); //녹취여부 저장
            newChangeRequest.setScanId(request.getScanId()); //녹취완료 후 eForm 이미지의 ID
            newChangeRequest.setFileNm(request.getFileNm());
            newChangeRequest.setFileMaskNm(request.getFileMaskNm());
            newChangeRequest.setRequestKey(request.getRequestKey());
            newChangeWriteMapper.updateMsfRequestTempClose(newChangeRequest);

            //2. requestKey 로 MSF_REQUEST_TEMP 에서 데이타 조회하여 유효성검증 및 데이타 추출
            //   [1] 작성자아이디와 세션의 작성자아이디 일치여부 확인
            //   [2] MSF_REQUEST_TEMP 테이블의 TMP_STAT_CD 값은 신청서 확인 (EFORM) 시 "3" 값으로 저장 후 연동함으로 상태값 확인
            //   [3] RES_NO 와 SBSC_PROD_CD 는 MSF_REQUEST_STATE 테이블에 저장해야하므로 여기서 추출
            request.setShowAll("Y"); //전체 컬럼 조회
            request.setTempYn("Y"); //MSF_REQUEST_TEMP 테이블 조회를 위한 설정
            msfRequestVo = newChangeDBSelectService.getMsfRequestInfo(request);
            BusinessContextHolder.setParentScanId(msfRequestVo.getParentScanId());
            //resNo = msfRequestVo.getResNo();
            //sbscProdCd = msfRequestVo.getSbscProCd(); //가입진행코드는 신청서확인 시 00 으로 처리하여 가져옴 (00 접수대기)

            //[작성완료] 개통전 사전체크 START ------------------------------------------------------------------------------
            //newChangeRequest = newChangeMpReadMapper.selectMsfPreCheckInfoRequest(request.getRequestKey());
            //MpPreCheckRequest mpPreCheckRequest = new MpPreCheckRequest();
            //mpPreCheckRequest.setRequestKey(request.getRequestKey()); //MSF_REQUEST.request_key
            //mpPreCheckRequest.setResNo(newChangeRequest.getResNo()); //MSF_REQUEST.RES_NO
            //mpPreCheckRequest.setKnoteScanId(newChangeRequest.getKnoteScanId()); //KNOTE 서식지 아이디
            //mpPreCheckRequest.setFathTransacId(newChangeRequest.getFathTransacId()); //안면인증 트랜잭션 아이디
            //mpPreCheckRequest.setOperTypeCd(request.getOperTypeCd()); //업무유형
            //mpPreCheckRequest.setAgentCd(newChangeRequest.getAgentCd()); //Header 값으로 보낼 관리자할 대리점코드 (요건 변환해야해)
            //
            //Map<String, String> rtnMapPreCheck = mpPreCheckService.getNewChangeMpPreCheck(mpPreCheckRequest);
            //if (rtnMapPreCheck != null) {
            //    preCheckRsltCd = rtnMapPreCheck.get("rsltCd");
            //    preCheckRsltMsg = rtnMapPreCheck.get("rsltMsg");
            //    newChangeResponse.setRsltMsg(preCheckRsltMsg);
            //    log.debug("개통전 사전체크 >> preCheckRsltCd: {}, preCheckRsltMsg: {}", preCheckRsltCd, preCheckRsltMsg);
            //}
            //[작성완료] 개통전 사전체크 END --------------------------------------------------------------------------------

            // 2-1. 평생할인 프로모션 요금 및 ID 선조회하여 임시 테이블 저장
            List<String> prmtIds = null;
            String prmtId = null;
            Long promoBaseAmt = null;
            try {
                MsfRequestSaleVo msfRequestSaleVo = newChangeDBSelectService.getMsfRequestSaleInfo(request);
                if (msfRequestVo != null && msfRequestSaleVo != null) {
                    MspSaleSubsdMstRequest promoRequest = new MspSaleSubsdMstRequest();
                    promoRequest.setModelId(msfRequestSaleVo.getModelId());
                    promoRequest.setRateCd(msfRequestSaleVo.getSocCode());
                    promoRequest.setAgrmTrm(msfRequestSaleVo.getEnggMnthCnt() != null ? String.valueOf(msfRequestSaleVo.getEnggMnthCnt()) : "0");
                    promoRequest.setReqBuyTypeCd(msfRequestVo.getReqBuyTypeCd());
                    promoRequest.setUsimKindsCd(msfRequestVo.getUsimKindsCd());
                    promoRequest.setSprtTp(msfRequestSaleVo.getSprtTypeCd());
                    promoRequest.setOperTypeCd(msfRequestVo.getOperTypeCd());
                    promoRequest.setModelMonthly(msfRequestSaleVo.getModelMonthly());
                    promoRequest.setAgentCd(msfRequestVo.getAgentCd());

                    prmtIds = productInfoService.getDisPrmtId(promoRequest);
                    if (prmtIds != null && !prmtIds.isEmpty()) {
                        prmtId = prmtIds.getFirst();
                        if (StringUtils.hasText(prmtId)) {
                            // MSF_REQUEST_TEMP 업데이트
                            newChangeWriteMapper.updateMsfRequestPrmtIdTemp(request.getRequestKey(), prmtId);
                            // 프로모션 요금 합계 조회 후 MSF_REQUEST_SALE_TEMP 업데이트
                            promoBaseAmt = newChangeMspReadMapper.selectPromoBaseAmt(prmtIds);
                            if (promoBaseAmt != null) {
                                newChangeWriteMapper.updateMsfRequestSalePrmtAmtTemp(request.getRequestKey(), promoBaseAmt);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("평생할인 프로모션 정보 선조회 및 임시테이블 저장 중 오류 발생", e);
            }

            //3. 신청서 정보 저장 ( INSERT INTO ~ SELECT ) 8종 테이블
            //MSF_REQUEST.PRO_STTUS_CD --  MCP_REQUEST.PSTATE // 진행상태코드
            //MSF_REQUEST.SBSC_PRO_CD --   MCP_REQUEST.REQUEST_STATE_CODE // 가입진행코드
            newChangeFormSave.setMsfComplete(request.getRequestKey());

            //4. 신청서 데이타 조회하여 MCP VO 로 고객포탈로 저장
            try {
                newChangeFormSave.setMcpComplete(request.getRequestKey());

                // 평생할인 프로모션 ID 테이블 INSERT
                try {
                    if (msfRequestVo != null && StringUtils.hasText(prmtId)) {
                        AppformReqDto appformReqDto = new AppformReqDto();
                        appformReqDto.setApdSeq("");
                        appformReqDto.setResNo(msfRequestVo.getResNo());
                        appformReqDto.setRequestKey(msfRequestVo.getRequestKey());
                        appformReqDto.setPrmtId(prmtId);
                        appformReqDto.setEvntCd("NAC");
                        appformReqDto.setCntpntShopId(msfRequestVo.getAgentCd());
                        appformReqDto.setPrmtAgntCd(msfRequestVo.getAgentCd());
                        MsfRequestSaleVo msfRequestSaleVo = newChangeDBSelectService.getMsfRequestSaleInfo(request);
                        appformReqDto.setSocCode(msfRequestSaleVo != null ? msfRequestSaleVo.getSocCode() : "");
                        appformReqDto.setOnOffType("F");
                        appformReqDto.setOperType(msfRequestVo.getOperTypeCd());
                        appformReqDto.setReqBuyType(msfRequestVo.getReqBuyTypeCd());
                        appformReqDto.setCretId(AuthenticationUtils.getUser() != null ? AuthenticationUtils.getUser().getUserId() : "MCP");

                        newChangeMspWriteMapper.insertDisPrmtApd(appformReqDto);
                        log.info("평생할인 프로모션 정보 저장 완료: apdSeq={}, prmtId={}", appformReqDto.getApdSeq(), prmtId);
                    }
                } catch (Exception e) {
                    log.error("평생할인 프로모션 정보 저장 중 오류 발생", e);
                    throw e;
                }
            } catch (Exception e) {
                newChangeFormSave.setMsfDelete(request.getRequestKey());
                throw new SimpleDomainException("신청서 작성완료에 실패했습니다. 사유: " + e.getMessage());
            }

            //완료되면 5번의 MSF_REQUEST_STATE 테이블에 최종 저장
            //5. MSF_REQUEST_STATE 테이블 저장
            //Long requestStateSeq = formCommService.generateRequestStateSeq();
            //MsfRequestStateVo msfRequestStateVo = new MsfRequestStateVo();
            //msfRequestStateVo.setRequestKey(request.getRequestKey());
            //msfRequestStateVo.setRequestStateKey(requestStateSeq);
            //msfRequestStateVo.setResNo(resNo); //요건 가져오는 것 처리 필요함.
            //msfRequestStateVo.setSbscProCd(sbscProdCd); //가입진행코드 : 00 접수대기
            //newChangeWriteMapper.insertMsfRequestState(msfRequestStateVo);

            //6. 접수완료 후 임시저장 테이블 만료처리 (MSF_REQUEST_TEMP.TMP_STEP_CD  값을 4로 처리함)
            newChangeRequest = new NewChangeRequest();
            newChangeRequest.setRequestClose("Y"); //작성완료 항목으로 만료처리 시 parameter 로만 사용
            newChangeRequest.setTmpStepCd("4"); //작성완료일 때 상태값
            newChangeRequest.setRequestKey(request.getRequestKey());
            newChangeWriteMapper.updateMsfRequestTempClose(newChangeRequest);

            //7. 접수완료 후 상태변경
            newChangeRequest = new NewChangeRequest();
            String procCd = "RQ"; //처리결과 - MCP 연동항목이라면 윗쪽으로 올려야함
            String proSttusCd = "01"; //진행상태코드 - MCP 연동항목이라면 윗쪽으로 올려야함
            String sbscProCd = "01"; //가입진행코드 - MCP 연동항목이라면 윗쪽으로 올려야함
            newChangeRequest.setProcCd(procCd);
            newChangeRequest.setProSttusCd(proSttusCd);
            newChangeRequest.setSbscProCd(sbscProCd);
            newChangeRequest.setRequestKey(request.getRequestKey());
            newChangeWriteMapper.updateMsfRequestInfo(newChangeRequest);
            log.debug("신청서 접수완료 상태변경 >> procCd: {}, proSttusCd: {}, sbscProCd: {}", procCd, proSttusCd, sbscProCd);

            //8. 접수완료 후 재고정리
            //신청서 등록 완료한 경우 휴대폰/USIM 재고 MSF_PROD_STOR_INVENTORY_TXN 재고 데이터 ‘접수완료’로 변경 처리
            //use_sttus_cd  = ‘R’ ----(N : 미사용 / R : 접수완료 / A : 사용완료)
            ProductInventoryRequest productInventoryRequest = new ProductInventoryRequest();
            String agentCd = msfRequestVo.getAgentCd();
            String reqBuyTypeCd = msfRequestVo.getReqBuyTypeCd(); //상품유형 : MM / UU
            String usimKindsCd = msfRequestVo.getUsimKindsCd(); //유심종류
            String reqPhoneSn = msfRequestVo.getReqPhoneSn(); //핸드폰 일련번호
            String reqUsimSn = msfRequestVo.getReqUsimSn(); //유심 일련번호 (구매한 경우에만)

            productInventoryRequest.setUseSttusCd("R"); //접수완료
            productInventoryRequest.setAgentCd(agentCd); //신청서에 저장된 조직코드
            if (ReqBuyType.MOBILE.getCode().equals(reqBuyTypeCd)) { //휴대폰 일련번호 접수완료 처리
                productInventoryRequest.setProdSn(reqPhoneSn);
                productSmartInfoWriteMapper.updateMsfProdStorInventoryTxn(productInventoryRequest);
                log.debug("휴대폰 재고상태 변경 >> reqPhoneSn: {}", reqPhoneSn);
            }
            if (!"06".equals(usimKindsCd) && !"".equals(usimKindsCd)) { //유심 접수완료 처리
                productInventoryRequest.setProdSn(reqUsimSn);
                productSmartInfoWriteMapper.updateMsfProdStorInventoryTxn(productInventoryRequest);
                log.debug("유심 재고상태 변경 >> reqPhoneSn: {}", reqPhoneSn);
            }
        }

        //최종
        return FormResponse.of(ResponseMessage.SUCCESS, newChangeResponse);
        //if ("S".equals(preCheckRsltCd)) {
        //    return FormResponse.of(ResponseMessage.SUCCESS, newChangeResponse);
        //} else {
        //    return FormResponse.of(ResponseMessage.VALID_SAVE_FAIL_PRE_CHECK, newChangeResponse);
        //}


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

    }


}
