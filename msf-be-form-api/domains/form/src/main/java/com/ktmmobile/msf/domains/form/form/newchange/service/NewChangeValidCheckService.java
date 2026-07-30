package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.common.code.CstmrType;
import com.ktmmobile.msf.domains.form.common.code.OperType;
import com.ktmmobile.msf.domains.form.common.code.ReqBuyType;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.JuoSubInfoDto;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.dto.UsimMspRateDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.constant.PhoneConstant;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.UsimBasDto;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfUploadPhoneInfoVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.BulkCorporateInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.BulkCorporateInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionResponse;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.util.NewChangeFormUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewChangeValidCheckService {

    private final FormCommReadMapper formCommReadMapper;
    private final AgencyCacheReader agencyCacheReader;
    private final MspApiDirectRepository mspApiDirectRepository;
    private final CommonCodeReader commonCodeReader;
    private final NewChangeReadMapper newChangeReadMapper;
    private final AuthInfoReadMapper authInfoReadMapper;


    //내국인, 내국인 미성년자, 외국인, 외국인 미성년자는 사람으로 구분
    private static final Set<String> PERSONAL_CUSTOMER_TYPES = Set.of("NA", "NM", "FN", "FM");


    //로그인 사용자 세팅
    public void setAgentUserInfo(NewChangeInfoRequest request) {
        //4. 로그인 사용자 정보 체크 ( agencyCacheReader 확인 등 )
        String managerCd = ""; //매니저코드 : 화면에서 선택한 대리점 조직의 담당자ID
        String managerNm = ""; //매니저명 : 화면에서 선택한 대리점 조직의 담당자명
        String agentCd = ""; //대리점코드 : 화면에서 선택한 대리점 조직의 조직ID
        String agentNm = ""; //대리점명 : 화면에서 선택한 대리점 조직의 조직명
        String cntpntShopCd = ""; //채널판매점코드 : 화면에서 선택한 대리점 조직의 조직ID
        String cntpntShopNm = ""; //채널판매점명 : 화면에서 선택한 대리점 조직의 조직명
        String shopCd = ""; //판매점코드 : 로그인 계정의 판매조직ID
        String shopNm = ""; //판매점명 : 로그인 계정의 판매조직명
        //String cpntId = ""; //판매점코드 : 로그인 계정의 판매조직ID
        //String cpntNm = ""; //판매점명 : 로그인 계정의 판매조직명
        //String realShopNm = ""; //실판매점명 : 로그인 계정의 판매조직명

        //선택한 대리점코드
        agentCd = request.getAgentCd(); //로그인 세션에서 불러온 값 세팅 제외함. 추후 필요하다면 추가.

        //AgencyCacheReader 에서 불러오기
        Optional<AgencyCache> agentInfo = agencyCacheReader.getAgency(agentCd);
        if (agentInfo.isPresent()) {
            agentCd = agentInfo.get().ktOrganizationId();
            agentNm = agentInfo.get().organizationName();
            cntpntShopCd = agentInfo.get().organizationId();
            cntpntShopNm = agentInfo.get().organizationName();
            //managerCd = agentInfo.get().respnPrsnId();
            //managerNm = agentInfo.get().respnPrsnNm();
            //agentInfo.get().representativeTelephone(); //REP_TEL_NO
            //agentInfo.get().telephone(); //TELNUM

            //if (managerCd == null) { //강제처리 - AgencyCacheReader 에 respnPrsnId , respnPrsnNm 값이 없는게 많음.
            //    managerCd = AuthenticationUtils.getUser().getUserId();
            //    managerNm = AuthenticationUtils.getUser().getUserName();
            //}

            //2026.07.20 - 로그인 사용자로 저장 요청
            managerCd = AuthenticationUtils.getUser().getUserId();
            managerNm = AuthenticationUtils.getUser().getUserName();
        }
        shopCd = AuthenticationUtils.getShopCode(); //로그인 세션의 판매점코드
        shopNm = AuthenticationUtils.getShopName(); //로그인 세션의 판매점명

        //request 값 세팅
        request.setManagerCd(managerCd); //매니저코드 : 화면에서 선택한 대리점 조직의 담당자ID
        request.setManagerNm(managerNm); //매니저명 : 화면에서 선택한 대리점 조직의 담당자명
        request.setAgentCd(agentCd); //대리점코드 : 화면에서 선택한 대리점 조직의 조직ID
        request.setAgentNm(agentNm); //대리점명 : 화면에서 선택한 대리점 조직의 조직명
        request.setCntpntShopCd(cntpntShopCd); //채널판매점코드 : 화면에서 선택한 대리점 조직의 조직ID
        request.setCntpntShopNm(cntpntShopNm); //채널판매점명 : 화면에서 선택한 대리점 조직의 조직명
        request.setShopCd(shopCd); //판매점코드 : 로그인 계정의 판매조직ID
        request.setShopNm(shopNm); //판매점명 : 로그인 계정의 판매조직명
        request.setCpntId(shopCd); //판매점코드 : 로그인 계정의 판매조직ID
        request.setCpntNm(shopNm); //판매점명 : 로그인 계정의 판매조직명
        request.setRealShopNm(shopNm); //실판매점명 : 로그인 계정의 판매조직명
    }

    //신청서 입력값 자릿수 체크
    //public FormResponse<NewChangeResponse> checkInputLength(NewChangeInfoRequest request) {
    public boolean checkInputLength(NewChangeInfoRequest request) {
        boolean isValid = true;
        if (PERSONAL_CUSTOMER_TYPES.contains(request.getCstmrTypeCd())) { //
            if (hasTextAndExceededByteLength(request.getCstmrNm(), 60)
                || hasTextAndExceededByteLength(request.getMinorAgentNm(), 60)) {
                isValid = false;
                //return FormResponse.of(ResponseMessage.VALID_CSTMR_NAME_LENGTH_NOT_CORRECT);
            }
        } else {
            if (hasTextAndExceededByteLength(request.getCstmrJuridicalCname(), 60)
                || hasTextAndExceededByteLength(request.getCstmrJuridicalRepNm(), 60)
                || hasTextAndExceededByteLength(request.getCstmrJuridicalUserNm(), 60)) {
                isValid = false;
                //return FormResponse.of(ResponseMessage.VALID_CSTMR_NAME_LENGTH_NOT_CORRECT);
            }
        }

        //운전면허번호
        if (StringUtils.hasText(request.getDriveLicnsNo())
            && hasTextAndExceededByteLength(request.getDriveLicnsNo(), 12)) {
            isValid = false;
        }

        //업태
        if (StringUtils.hasText(request.getBcuSbst())
            && hasTextAndExceededByteLength(request.getBcuSbst(), 50)) {
            isValid = false;
        }

        // 이메일
        if (StringUtils.hasText(request.getCstmrEmailAdr())
            && hasTextAndExceededByteLength(request.getCstmrEmailAdr(), 200)) {
            isValid = false;
            //return FormResponse.of(ResponseMessage.VALID_EMAIL_LENGTH_NOT_CORRECT);
        }

        // 번호이동 인증번호
        if ("MNP3".equals(request.getOperTypeCd())
            && hasTextAndNotMatchedByteLength(request.getMoveAuthNo(), 4)) {
            isValid = false;
            //return FormResponse.of(ResponseMessage.VALID_MNP3_MOVE_AUTH_NO_NOT_CORRECT);
        }

        //신분증 스캔 유형 값 자릿수 체크 (MSF_REQUEST.IDENTITY_TYPE_CD : 신분증유형코드)
        if (hasTextAndExceededByteLength(request.getIdentityTypeCd(), 2)) {
            isValid = false;
        }

        //return FormResponse.of(ResponseMessage.SUCCESS);
        return isValid;
    }

    //length 가 크면 안되는 경우
    private boolean hasTextAndExceededByteLength(String value, int maxLength) {
        return StringUtils.hasText(value)
            && value.getBytes(StandardCharsets.UTF_8).length > maxLength;
    }

    //length 가 동일해야 하는 경우
    private boolean hasTextAndNotMatchedByteLength(String value, int exactLength) {
        return StringUtils.hasText(value)
            && value.getBytes(StandardCharsets.UTF_8).length != exactLength;
    }

    /**
     * 신청서 유효성체크
     */
    public boolean checkNewChangeForm(NewChangeInfoRequest request) {
        boolean isValid = true;

        //인자값 확인 - 휴대폰/유심
        String reqBuyType = request.getReqBuyTypeCd();
        if (!Constants.REQ_BUY_TYPE_PHONE.equals(reqBuyType) && !Constants.REQ_BUY_TYPE_USIM.equals(reqBuyType)) {
            isValid = false;
            log.debug("reqBuyType ========= 입력값 없음");
            //바인딩 처리중 오류가 발생하였습니다.(구매타입)
            //return FormResponse.of(ResponseMessage.VALID_BINDING_REQBUYTYPE_FAIL);
        }

        String cstmrTypeCd = request.getCstmrTypeCd();
        int age = 0;
        if (CstmrType.NATIVE_MINOR.getCode().equals(cstmrTypeCd) || CstmrType.FOREIGN_MINOR.getCode().equals(cstmrTypeCd)) { //미성년자
            if (CstmrType.NATIVE_MINOR.getCode().equals(cstmrTypeCd)) {
                age = NewChangeFormUtil.getAge(request.getCstmrNativeRrn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            } else if (CstmrType.FOREIGN_MINOR.getCode().equals(cstmrTypeCd)) {
                age = NewChangeFormUtil.getAge(request.getCstmrForeignerRrn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            }
            if (19 <= age || 1 > age) {
                isValid = false;
                log.debug("★ 미성년자 주민번호가 아닙니다. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> age: {}", age);
                //청소년 주민등록 번호가 아닙니다.
                //return FormResponse.of(ResponseMessage.REGNO_TEEN_FAIL);
            }
        } else if (CstmrType.NATIVE_ADULT.getCode().equals(cstmrTypeCd) || CstmrType.FOREIGN_ADULT.getCode().equals(cstmrTypeCd)) { //성인
            if (CstmrType.NATIVE_ADULT.getCode().equals(cstmrTypeCd)) {
                age = NewChangeFormUtil.getAge(request.getCstmrNativeRrn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            } else if (CstmrType.FOREIGN_ADULT.getCode().equals(cstmrTypeCd)) {
                age = NewChangeFormUtil.getAge(request.getCstmrForeignerRrn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            }
            if (19 > age) {
                isValid = false;
                log.debug("★ 성인 주민번호가 아닙니다. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> age: {}", age);
                //성인 주민등록 번호가 아닙니다.
                //return FormResponse.of(ResponseMessage.REGNO_ADULT_FAIL);
            }
        }

        //기기변경 유효성체크 진행 및 서비스계약번호 저장 처리 - 추가필요!!!!!!!!!!!!!!!!!!!!!
        if (OperType.HANDSET_CHANGE.getCode().equals(request.getOperTypeCd()) || OperType.HANDSET_EXCHANGE.getCode()
            .equals(request.getOperTypeCd())) {
            MspJuoSubInfoRequest mspJuoSubInfoRequest = new MspJuoSubInfoRequest();
            mspJuoSubInfoRequest.setCstmrType(request.getCstmrTypeCd()); //고객구분
            mspJuoSubInfoRequest.setCustomerLinkName(request.getCstmrNm()); //고객명
            mspJuoSubInfoRequest.setCustomerMobileNo(request.getOpenNo()); //기기변경 인증번호는 Open_no 에 저장하도록 함.

            //화면에서 고객인증은 쿼리에서 조회 후 서비스에서 식별번호를 확인하지만 이미 인증한 정보라서 서비스 계약번호만 조회해서 저장하는 과정 처리를 위해 추가
            MspJuoSubInfoResponse memberInfo = authInfoReadMapper.selectKtmCustomer(mspJuoSubInfoRequest); //고객조회

            if (memberInfo == null) {
                isValid = false;
                log.debug("★ 기기변경 신청서 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 고객이 조회되지 않습니다. ");
            } else {
                request.setContractNum(memberInfo.getContractNum()); //기기변경 서비스계약번호 저장처리 (FHC0  호출 시 필요함)
                request.setCustId(memberInfo.getCustomerId()); //기기변경 고객아이디 저장처리 (FHC0  호출 시 필요하여 기기변경 사전체크 시 조회해서 가져와야하므로 컬럼추가 - 20260713)
            }
        }

        // 11. 골드 번호 체크 (신규가입) - 예약한 경우~ 처리해야해서 주석처리함.
        //if (OperType.NEW_ACTIVATION.getCode().equals(request.getOperTypeCd())) {
        //    String goldNumbersChk = this.containsGoldNumbers(Arrays.asList(request.getReqWantFnNo(),
        //        request.getReqWantMnNo(),
        //        request.getReqWantRnNo()));
        //    if ("1000".equals(goldNumbersChk)) {
        //        isValid = false;
        //        //입력하신 가입희망번호 중 골드번호가 포함되어 있습니다. 희망번호 수정 후 다시 시도 부탁드립니다.
        //        //return FormResponse.of(ResponseMessage.VALID_CONTAINS_GOLD_NUMBER_FAIL);
        //    }
        //}

        // 7. esim 유효성 체크
        // 휴대폰 이미지 업로드 정보 조회
        log.debug("request.getUploadPhoneSrlNo() : " + request.getUploadPhoneSrlNo());
        //long uploadPhoneSrlNo = 0L;
        MsfUploadPhoneInfoVo uploadEPhone = new MsfUploadPhoneInfoVo();
        if (request.getUploadPhoneSrlNo() != null) {
            uploadEPhone = newChangeReadMapper.selectMsfUploadPhoneInfo(request.getUploadPhoneSrlNo());

            if (StringUtils.hasText(uploadEPhone.getModelId())) { //조회 정보가 있다고 받단하는 경우
                String esimChk = this.fnSetDataOfeSim(request, uploadEPhone);
                if ("1000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 이미지 일련번호가 존재하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_SRL_NO_FAIL);
                }
                if ("2000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 EID 정보가 없습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EID_FAIL);
                }
                if ("3000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 EID 정보가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_EID_FAIL);
                }
                if ("4000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 IMEI1 정보가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_IMEI1_FAIL);
                }
                if ("5000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 IMEI2 정보가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_IMEI2_FAIL);
                }
                if ("6000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 일련번호가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_REQPHONESN_FAIL);
                }
                if ("7000".equals(esimChk)) {
                    isValid = false;
                    //기기 모델아이디가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_ESIMPHONEID_FAIL);
                }
            }
        }

        return isValid;
    }

    public boolean checkNewChangeForm2(NewChangeInfoRequest request) {
        boolean isValid = true;

        //[스마트-적용] 인자값 확인 - 휴대폰/유심
        String reqBuyType = request.getReqBuyTypeCd();
        if (!Constants.REQ_BUY_TYPE_PHONE.equals(reqBuyType) && !Constants.REQ_BUY_TYPE_USIM.equals(reqBuyType)) {
            isValid = false;
            //바인딩 처리중 오류가 발생하였습니다.(구매타입)
            //return FormResponse.of(ResponseMessage.VALID_BINDING_REQBUYTYPE_FAIL);
        }

        //[스마트-적용]5. 고객유형으로 나이 체크 (미성년자와 성인)
        String cstmrTypeCd = request.getCstmrTypeCd();
        if (CstmrType.NATIVE_MINOR.getCode().equals(cstmrTypeCd) || CstmrType.FOREIGN_MINOR.getCode().equals(cstmrTypeCd)) {
            // 미성년자
            int age = NewChangeFormUtil.getAge(request.getCstmrNativeRrn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            if (19 <= age || 1 > age) {
                isValid = false;
                //청소년 주민등록 번호가 아닙니다.
                //return FormResponse.of(ResponseMessage.REGNO_TEEN_FAIL);
            }
        } else if (CstmrType.NATIVE_ADULT.getCode().equals(cstmrTypeCd) || CstmrType.FOREIGN_ADULT.getCode().equals(cstmrTypeCd)) {
            int age = NewChangeFormUtil.getAge(request.getCstmrNativeRrn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            if (19 > age) {
                isValid = false;
                //성인 주민등록 번호가 아닙니다.
                //return FormResponse.of(ResponseMessage.REGNO_ADULT_FAIL);
            }
        }

        /* 사용 하게 되면 주석 처리 해제 할 것
        // 6. 신분증 인증 및 스캔정보 체크
        KnoteScanInfoRequest knoteScanInfoRequest = new KnoteScanInfoRequest();
        knoteScanInfoRequest.setAgentCd(request.getAgentCd());
        knoteScanInfoRequest.setFrmpapId(request.getKnoteScanId());

        // 6-1. KNOTE 신분증 목록조회 >> 서식지 목록조회 - FS0
        FormResponse<MSimpleOsstXmlFs0VO> idListResponse = authInfoService.getIdList(knoteScanInfoRequest);
        if("4444".equals(idListResponse.resCode())){
            //요청하신 정보가 존재하지 않습니다.
            return FormResponse.of(ResponseMessage.NO_DATA);
        }
        // 6-2. KNOTE 신분증 상태조회 >> 서식지 상태조회 - FS1
        FormResponse<KnoteScanInfoResponse> knoteScanInfoResponse = authInfoService.checkIdStatus(knoteScanInfoRequest);
        if("4444".equals(knoteScanInfoResponse.resCode())){
            //요청하신 정보가 존재하지 않습니다.
            return FormResponse.of(ResponseMessage.NO_DATA);
        } else if("1000".equals(knoteScanInfoResponse.resCode())){
            //신분증 상태 조회에 실패하였습니다.
            return FormResponse.of(ResponseMessage.VALID_KNOTE_SCAN_STATUS_FAIL);
        } else if("1001".equals(knoteScanInfoResponse.resCode())){
            //서식지 정보가 존재하지 않습니다.
            return FormResponse.of(ResponseMessage.VALID_KNOTE_SCAN_STATUS_NO_DATE);
        }
        */

        // 8. 예외 요금제 체크
        CommonCodesRequest param = CommonCodesRequest.of(List.of(Constants.GROUP_CODE_EXCEPTION_LIST_PRICE_CD), true, false);
        CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(param);
        String exceptionPriceCd = commonCodeGroups.getSimple(Constants.GROUP_CODE_EXCEPTION_LIST_PRICE_CD, request.getSocCode()).title();
        // 예외 요금제가 아니고 직영, 신규개통, 상담사 신청서, 유심있음 일 때에만
        if ("".equals(StringUtil.NVL(exceptionPriceCd, ""))
            && Constants.CONTPNT_SHOP_ID_MSHOP.equals(request.getCntpntShopCd())
            && Constants.OPER_TYPE_NEW.equals(request.getOperTypeCd())
            && Arrays.asList("0", "3").contains(request.getOnOffTypeCd())
            && (StringUtils.hasText(request.getReqUsimSn()) || "09".equals(request.getUsimKindsCd()))
        ) {
            // 9. 신규개통 이력 체크
            SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
            subscriptionRequest.setCustomerSsn(request.getCstmrNativeRrn());
            subscriptionRequest.setSearchType("NEW_ACCOUNT");
            String totalOpenYn = this.getOpenHistoryYn(subscriptionRequest);
            if ("Y".equals(StringUtil.NVL(totalOpenYn, ""))) {
                isValid = false;
                // 신규가입은 명의당 30일이내 1회선만 가입 가능합니다.
                //return FormResponse.of(ResponseMessage.VALID_SELF_LIMIT_FAIL);
            }
        }

        //[스마트-적용] 7. esim 유효성 체크
        // 휴대폰 이미지 업로드 정보 조회
        log.debug("request.getUploadPhoneSrlNo() : " + request.getUploadPhoneSrlNo());
        //long uploadPhoneSrlNo = 0L;
        MsfUploadPhoneInfoVo uploadEPhone = new MsfUploadPhoneInfoVo();
        if (request.getUploadPhoneSrlNo() != null) {
            uploadEPhone = newChangeReadMapper.selectMsfUploadPhoneInfo(request.getUploadPhoneSrlNo());

            if (StringUtils.hasText(uploadEPhone.getModelId())) { //조회 정보가 있다고 받단하는 경우
                String esimChk = this.fnSetDataOfeSim(request, uploadEPhone);
                if ("1000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 이미지 일련번호가 존재하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_SRL_NO_FAIL);
                }
                if ("2000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 EID 정보가 없습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EID_FAIL);
                }
                if ("3000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 EID 정보가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_EID_FAIL);
                }
                if ("4000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 IMEI1 정보가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_IMEI1_FAIL);
                }
                if ("5000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 IMEI2 정보가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_IMEI2_FAIL);
                }
                if ("6000".equals(esimChk)) {
                    isValid = false;
                    //휴대폰 일련번호가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_REQPHONESN_FAIL);
                }
                if ("7000".equals(esimChk)) {
                    isValid = false;
                    //기기 모델아이디가 일치하지 않습니다.
                    //return FormResponse.of(ResponseMessage.VALID_ESIM_EQ_ESIMPHONEID_FAIL);
                }
            }
        }
        //McpUploadPhoneInfoDto uploadEPhone = formCommReadMapper.getUploadPhoneInfo(request);

        // 10. 청구서 발송 유형 체크 - 필요사항인지 검토필요
        //if (uploadEPhone != null && uploadEPhone.getPrntsContractNum() != null) {
        //if (request.getUploadPhoneSrlNo() != null && StringUtils.hasText(uploadEPhone.getPrntsContractNum())) {
        //    HashMap<String, String> paramMap = new HashMap<String, String>();
        //    paramMap.put("name", "");
        //    paramMap.put("mobileNo", "");
        //    paramMap.put("contractNum", uploadEPhone.getPrntsContractNum());
        //
        //    //기존 계약정보 조회
        //    Map<String, String> resObj = formCommReadMapper.selectContractObj(paramMap);
        //    if (resObj == null) {
        //        isValid = false;
        //        //계약번호 정보를 확인 할 수 없습니다.
        //        //return FormResponse.of(ResponseMessage.VALID_BILL_CONTRACT_CHECK_FAIL);
        //    }
        //    String svcCntrNo = resObj.get("SVC_CNTR_NO");
        //    String cntrMobileNo = resObj.get("SUBSCRIBER_NO");
        //    String custId = resObj.get("CUSTOMER_ID");
        //    if (!StringUtils.hasText(svcCntrNo)) {
        //        svcCntrNo = uploadEPhone.getPrntsContractNum();
        //    }
        //    try {
        //        //X49 이메일 청구서 정보 조회 - 해야한다면 추후 추가필요
        //        MpMoscBilEmailInfoInVO moscBilEmailInfo = msfMplatFormService.kosMoscBillInfo(svcCntrNo, cntrMobileNo, custId);
        //        if (moscBilEmailInfo == null) {
        //            isValid = false;
        //            //청구서 정보를 확인할 수 없습니다.
        //            //return FormResponse.of(ResponseMessage.VALID_BILL_CHECK_FAIL);
        //        }
        //    } catch (SelfServiceException e) {
        //        log.debug("[getChangInfoView] 납부방법/명세서 조회 실패: {}", e.getMessage());
        //    } catch (Exception e) {
        //        log.debug("[getChangInfoView] 납부방법/명세서 조회 오류", e);
        //    }
        //}

        //[스마트-적용] 11. 골드 번호 체크 (신규가입)
        if (OperType.NEW_ACTIVATION.getCode().equals(request.getOperTypeCd())) {
            String goldNumbersChk = containsGoldNumbers(Arrays.asList(request.getReqWantFnNo(), request.getReqWantMnNo(), request.getReqWantRnNo()));
            if ("1000".equals(goldNumbersChk)) {
                isValid = false;
                //입력하신 가입희망번호 중 골드번호가 포함되어 있습니다. 희망번호 수정 후 다시 시도 부탁드립니다.
                //return FormResponse.of(ResponseMessage.VALID_CONTAINS_GOLD_NUMBER_FAIL);
            }
        }

        // 12. 유심비 / 가입비 설정
        String cntpntShopId = request.getCntpntShopCd();
        UsimBasDto usimBasDtoParm = new UsimBasDto();
        usimBasDtoParm.setOrgnId(cntpntShopId);
        usimBasDtoParm.setOperType(request.getOperTypeCd());
        //usimBasDtoParm.setDataType(request.getPrdtSctnCd()); 확인필요
        usimBasDtoParm.setRateCd(request.getSocCode());
        usimBasDtoParm.setReqBuyType(request.getReqBuyTypeCd());

        //NFC 유심 처리
        if ("08".equals(request.getUsimKindsCd())) {
            usimBasDtoParm.setDataType(Constants.DTL_CD_USIM_NFC);
        }

        //eSIM 처리
        if ("09".equals(request.getUsimKindsCd())) {
            //eSIM
            usimBasDtoParm.setPrdtIndCd("10");
            /**
             * ㅇ 이슈사항 : M전산에서 스마트 워치 직접개통(OSST개통) 시 하기와 같은 alert 발생             *
             * ㅇ 발생 오류코드 : 3107  [DEFKTF] 상품은 [애플워치 단말기에서는 휴대폰결제 비밀번호서비스 부가서비스를 가입할 수 없습니다.]사유로 가입이 불가 합니다.
             * ㅇ 확인된 원인
             *  1) PC0(사전체크) 전문의 하기 두 값이 Y로 연동 될 경우 MP에서 '휴대폰결제 비밀번호서비스(MPAYPSSWD)를 자동으로 가입 시킴.
             *  2) 휴대폰결제 비밀번호서비스(MPAYPSSWD) 부가서비스와 스마트워치는 베타관계로, 부가서비스로 자동 가입으로 인한 개통 실패 발생
             * ※ 현재 포탈에서 신청서 작성 시 하기 값을 Null값으로 셋팅하고 있으나 M전산에서는 하기 값이 Null일 경우 "Y"로 신청서 생성 중
             * 개통 이동전화결제이용동의여부 무조건 N으로 설정
             */
            request.setPhonePaymentYn("N");

        } else {
            usimBasDtoParm.setPrdtIndCd(request.getUsimKindsCd());
        }

        //SIM 정보 조회
        Map<String, String> simInfoMap = this.getSimInfo(usimBasDtoParm);
        if (simInfoMap == null) {
            isValid = false;
            //return FormResponse.of(ResponseMessage.VALID_BINDING_USIM_INFO_FAIL);
        }
        long intJoinPrice = Long.parseLong(simInfoMap.get("JOIN_PRICE")); //가입비 - 저 아래쪽에서 처리하고 있음.
        long intUsimPrice = Long.parseLong(simInfoMap.get("SIM_PRICE")); //유심비 - 저 아래쪽에서 처리하고 있음.


        //1-2. 기기변경 신청서의 인증한 핸드폰번호가 정상인지 재확인
        //if (Constants.OPER_TYPE_CHANGE.equals(request.getOperTypeCd()) || Constants.OPER_TYPE_EXCHANGE.equals(request.getOperTypeCd())) {
        if (OperType.HANDSET_CHANGE.getCode().equals(request.getOperTypeCd()) || OperType.HANDSET_EXCHANGE.getCode()
            .equals(request.getOperTypeCd())) {
            //JuoSubInfoDto sesionJuoSub = SessionUtils.getChangeAutCookieBean();
            JuoSubInfoDto juoSubInfoDto = null; //고객유형, 핸드폰번호, 식별번호로 조회해서 담아야함.
            String ctn = request.getCstmrMobileFnNo() + request.getCstmrMobileMnNo() + request.getCstmrMobileRnNo();
            //String birthday = request.getBirthDate(); //고객인증한 생년월일 확인필요
            String birthday = request.getCstmrNativeBirth(); //고객인증한 생년월일
            if (Constants.CSTMR_TYPE_NM.equals(request.getCstmrTypeCd())) {
                String strTemp = request.getCstmrNativeRrn();
                if (strTemp.length() > 5) {
                    birthday = strTemp.substring(0, 6);
                } else {
                    birthday = strTemp;
                }
            }

            //
            if (juoSubInfoDto == null
                || !juoSubInfoDto.getCustomerSsn().contains(birthday)
                || !juoSubInfoDto.getSubscriberNo().equals(ctn)) {
                isValid = false;
                //기기변경 휴대폰 번호가 인증한 정보와 일치하지 않습니다.
                //return FormResponse.of(ResponseMessage.VALID_BINDING_CHANGE_AUT_FAIL);
            }

            //계약 번호 설정
            request.setContractNum(juoSubInfoDto.getContractNum());

            //금액 정보 설정
            if (!StringUtils.hasText(request.getModelSalePolicyCd())
                //|| !StringUtils.hasText(request.getPrdtSctnCd()) 확인필요
                || !StringUtils.hasText(request.getCntpntShopCd())
                || !StringUtils.hasText(request.getOperTypeCd())
                || !StringUtils.hasText(request.getSocCode())
                || "null".equals(request.getSocCode())) {
                isValid = false;
                //바인딩 처리중 오류가 발생하였습니다.(금액 정보 설정)
                //return FormResponse.of(ResponseMessage.VALID_BINDING_PRICE_FAIL);
            }

            //가입비 납부방법(0 : 면제, 1:일시납, 2: 분납(3개월)   <=== TODO : 확인 필요
            //가입비 납부방법(1 면제 , 2 일시납, 3 3개월분납) <== MSP확인
            request.setJoinPayMthdCd("1");
            request.setJoinPriceTypeCd("P"); // 가입비납부유형(R:완납, I:분납 , P:면제) */
            // 현재 유심 유심번호 설정
            if ("iccId".equals(request.getReqUsimSn())) {
                request.setReqUsimSn(juoSubInfoDto.getIccId());
                request.setUsimPriceTypeCd("N");
            } else {
                request.setUsimPrice(intUsimPrice);
            }

            //usimKind 06 미발송  _ 기존 유심 사용 ..
            if ("06".equals(request.getUsimKindsCd())) {
                request.setUsimPayMthdCd("1");
                request.setUsimPrice(0L);
            }

        } else {
            if (ReqBuyType.MOBILE.getCode().equals(reqBuyType)) { //핸드폰
                if (!StringUtils.hasText(request.getModelSalePolicyCd())
                    //|| !StringUtils.hasText(request.getRprsPrdtId()) 확인필요
                    || !StringUtils.hasText(request.getCntpntShopCd())
                    || !StringUtils.hasText(request.getOperTypeCd())
                    || !StringUtils.hasText(request.getSocCode())
                    || "null".equals(request.getSocCode())) {
                    isValid = false;
                    //return FormResponse.of(ResponseMessage.VALID_BINDING_REQ_BUY_TYPE_PHONE_FAIL);
                }
            } else {
                if (!StringUtils.hasText(request.getCntpntShopCd())
                    || !StringUtils.hasText(request.getOperTypeCd())
                    || !StringUtils.hasText(request.getSocCode())
                    || "null".equals(request.getSocCode())) {
                    isValid = false;
                    //return FormResponse.of(ResponseMessage.VALID_BINDING_REQ_BUY_TYPE_USIM_FAIL);
                }
            }

            //가입비 유심비 설정
            /** 가입비 납부방법
             * 1 면제
             * 2 일시납
             * 3 3개월분납
             * 22년... 8월 23일 .. 세희 과장님 하고.. 통화 정리 함..
             * 고객에서 면제 라고 표현 하고 실제로.. M모바일에서 대납 처리 함....
             */
            if ("N".equals(simInfoMap.get("JOIN_IS_PAY"))) {
                request.setJoinPayMthdCd("2");
                request.setJoinPrice(0L);
            } else {
                //가입비가 0원이면 면제 아니면 3개월 분납
                if (intJoinPrice > 0L) {
                    request.setJoinPayMthdCd("3");
                    request.setJoinPrice(intJoinPrice);
                } else {
                    request.setJoinPayMthdCd("2");
                    request.setJoinPrice(0L);
                }
            }

            //NFC확인 필요.....TO_DO
            if ("N".equals(simInfoMap.get("SIM_IS_PAY"))) {
                request.setUsimPayMthdCd("1");
                request.setUsimPrice(0L);
            } else {
                //유심비가 0원이면 면제 아니면 3개월분납
                if (intUsimPrice > 0) {
                    request.setUsimPayMthdCd("3");
                    request.setUsimPrice(intUsimPrice);
                } else {
                    request.setUsimPayMthdCd("1");
                    request.setUsimPrice(0L);
                }
            }
        }

        return isValid;
    }

    /**
     * Esim 체크
     */
    private String fnSetDataOfeSim(NewChangeInfoRequest newChangeInfoRequest, MsfUploadPhoneInfoVo uploadEPhone) {

        // ESIM 이 아니면 체크 스킵
        if (!"09".equals(newChangeInfoRequest.getUsimKindsCd())) {
            return "0000";
        }

        // 휴대폰 이미지 일련번호 체크
        if (newChangeInfoRequest.getUploadPhoneSrlNo() < 1) {
            return "1000";
        }

        // 휴대폰 EID 정보 체크
        if (uploadEPhone == null || !StringUtils.hasText(uploadEPhone.getEid())) {
            return "2000";
        }

        // 모 회선 계약번호 체크
        if (StringUtils.hasText(uploadEPhone.getPrntsContractNum())) {
            if (StringUtil.NVL(uploadEPhone.getEid(), "").equals(StringUtil.NVL(newChangeInfoRequest.getEid(), ""))) {
                return "3000";
            }
            if (StringUtil.NVL(uploadEPhone.getImei1(), "").equals(StringUtil.NVL(newChangeInfoRequest.getImei1(), ""))) {
                return "4000";
            }
            if (StringUtil.NVL(uploadEPhone.getImei2(), "").equals(StringUtil.NVL(newChangeInfoRequest.getImei2(), ""))) {
                return "5000";
            }
            if (StringUtil.NVL(uploadEPhone.getReqPhoneSn(), "").equals(StringUtil.NVL(newChangeInfoRequest.getReqPhoneSn(), ""))) {
                return "6000";
            }
            if (StringUtil.NVL(uploadEPhone.getModelId(), "").equals(StringUtil.NVL(newChangeInfoRequest.getEsimPhoneId(), ""))) {
                return "7000";
            }
        } else {
            newChangeInfoRequest.setEid(uploadEPhone.getEid());
            newChangeInfoRequest.setImei1(uploadEPhone.getImei1());
            newChangeInfoRequest.setImei2(uploadEPhone.getImei2());
            newChangeInfoRequest.setReqPhoneSn(uploadEPhone.getReqPhoneSn());
            newChangeInfoRequest.setEsimPhoneId(uploadEPhone.getModelId());
        }
        return "0000";
    }

    private String containsGoldNumbers(List<String> reqWantNumbers) {
        CommonCodesRequest commonCodesRequest = CommonCodesRequest.withIncludeAll("GoldNumberList"); //조건설정
        CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(commonCodesRequest); // 공통코드 조회 요청
        List<CommonCodeData> crdCodes = commonCodeGroups.get("GoldNumberList");
        List<String> goldNumberList = crdCodes.stream()
            .map(CommonCodeData::title)
            .toList();

        List<String> filteredNumbers = reqWantNumbers.stream()
            .filter(goldNumberList::contains)
            .toList();

        if (!filteredNumbers.isEmpty()) {
            return "1000";
        }
        return "0000";
    }

    private Map<String, String> getSimInfo(UsimBasDto usimBasDto) {
        if (!StringUtils.hasText(usimBasDto.getOperType()) || !StringUtils.hasText(usimBasDto.getDataType())) {
            return null;
        }

        if (!StringUtils.hasText(usimBasDto.getOrgnId())) {
            usimBasDto.setOrgnId(Constants.CONTPNT_SHOP_ID_MSHOP);
        }

        //1. 심비 , 가입비 확인
        String joinPrice = "0";
        String simPrice = "0";

        //eSIM
        if ("10".equals(usimBasDto.getPrdtIndCd()) || "11".equals(usimBasDto.getPrdtIndCd())) {
            usimBasDto.setDataType("ESIM");
        }

        //가입비 , SIM 조회
        List<UsimMspRateDto> usimPriceList = selectJoinUsimPriceNew(usimBasDto);

        if (usimPriceList != null && !usimPriceList.isEmpty()) {
            joinPrice = usimPriceList.getFirst().getJoinPrice();
            simPrice = usimPriceList.getFirst().getUsimPrice();
        }

        //NFC 경우 값이 없음..
        //        else {
        //            throw new McpCommonJsonException("0004" ,USIM_PRICE_EXCEPTION);
        //        }

        //단말구매로 인입시 제외, portal DB로 유심비 한번더 체크
        if (!"MM".equals(usimBasDto.getReqBuyType())) {

            //eSIM 아닌고  LTE일때.. 공통 코드에서 조회
            if (!"10".equals(usimBasDto.getPrdtIndCd()) && PhoneConstant.LTE_FOR_MSP.equals(usimBasDto.getDataType())) {
                simPrice = NmcpServiceUtils.getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE, Constants.DTL_CD_OBJ_BASE);
            } else if (!"10".equals(usimBasDto.getPrdtIndCd()) && PhoneConstant.FIVE_G_FOR_MSP.equals(usimBasDto.getDataType())) {   // 5G도 공통 코드에서 조회 처리
                simPrice = NmcpServiceUtils.getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE, Constants.DTL_CD_OBJ_BASE);
            } else if (PhoneConstant.NFC_FOR_MSP.equals(usimBasDto.getDataType())) {
                simPrice = NmcpServiceUtils.getCodeNm(Constants.GROUP_CODE_DIRECT_USIM_PRICE, Constants.DTL_CD_OBJ_BASE);
            }
        }


        //2. 판매 요금제 별 가입비/유심비 면제 여부 조회
        String joinIsPay = "N";
        String simIsPay = "N";
        String nfcSimIsPay = "N";

        //eSIM
        if ("10".equals(usimBasDto.getPrdtIndCd()) || "11".equals(usimBasDto.getPrdtIndCd())) {
            //공통코드에서 확인
            joinIsPay = NmcpServiceUtils.getCodeNm("Constant", "eSimJoinIsPay");
            if ("Y".equals(joinIsPay)) {
                joinIsPay = "Y";
            } else {
                joinIsPay = "N";
            }

            simIsPay = NmcpServiceUtils.getCodeNm("Constant", "eSimIsPay");
            if ("Y".equals(simIsPay)) {
                simIsPay = "Y";
            } else {
                simIsPay = "N";
            }
        } else if (Constants.CONTPNT_SHOP_ID_MSHOP.equals(usimBasDto.getOrgnId())) {
            //직영접점
            NmcpCdDtlDto simPriceObj = NmcpServiceUtils.getCodeNmDto(Constants.GROUP_CODE_USIM_PRICE_INFO, usimBasDto.getRateCd());
            if (simPriceObj != null) {
                if ("Y".equals(simPriceObj.getExpnsnStrVal1())) {
                    joinIsPay = "Y";
                } else {
                    joinIsPay = "N";
                }

                if ("Y".equals(simPriceObj.getExpnsnStrVal2())) {
                    simIsPay = "Y";
                } else {
                    simIsPay = "N";
                }

                if ("Y".equals(simPriceObj.getExpnsnStrVal3())) {
                    nfcSimIsPay = "Y";
                } else {
                    nfcSimIsPay = "N";
                }

            }
        } else {

            //기본값이.. 납부... 로 변경....
            joinIsPay = "Y";
            simIsPay = "Y";
            nfcSimIsPay = "Y";

            NmcpCdDtlDto simPriceObj = NmcpServiceUtils.getCodeNmDto(Constants.GROUP_CODE_MARKET_JOIN_USIM_INFO, usimBasDto.getOrgnId());
            if (simPriceObj != null) {
                if ("N".equals(simPriceObj.getExpnsnStrVal1())) {
                    joinIsPay = "N";
                } else {
                    joinIsPay = "N";
                }

                if ("N".equals(simPriceObj.getExpnsnStrVal2())) {
                    simIsPay = "N";
                } else {
                    simIsPay = "N";
                }

                if ("N".equals(simPriceObj.getExpnsnStrVal3())) {
                    nfcSimIsPay = "N";
                } else {
                    nfcSimIsPay = "N";
                }
            }
        }

        HashMap<String, String> rtnMap = new HashMap<String, String>();
        rtnMap.put("SIM_PRICE", simPrice);
        rtnMap.put("JOIN_PRICE", joinPrice);
        if (Constants.DTL_CD_USIM_NFC.equals(usimBasDto.getDataType())) {
            rtnMap.put("SIM_IS_PAY", nfcSimIsPay);
        } else {
            rtnMap.put("SIM_IS_PAY", simIsPay);
        }
        rtnMap.put("JOIN_IS_PAY", joinIsPay);
        rtnMap.put("NFC_SIM_IS_PAY", nfcSimIsPay);

        return rtnMap;
    }

    private List<UsimMspRateDto> selectJoinUsimPriceNew(UsimBasDto usimBasDto) {
        return mspApiDirectRepository.query("/storeUsim/joinUsimPriceNew", usimBasDto, List.class);
    }

    /**
     * 가입조건조회
     */
    public SubscriptionResponse getEligibilityCheck(SubscriptionRequest request) {
        if ("HDN3".equals(request.getOperTypeCd())) {
            return null;
        }
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        CstmrType cstmrTypeCd = request.getCstmrTypeCd();

        log.debug("-------------------------------------------------------------------");
        log.debug("CstmrTypeCd: {}", request.getCstmrTypeCd());
        log.debug("cstmr: {}", request.getCustomerSsn());
        log.debug("-------------------------------------------------------------------");

        //1년이내 사용회선 조회
        int actYearCnt = this.getActYearCnt(request);
        subscriptionResponse.setYearActCnt(actYearCnt);
        //1년이내 해지
        int cancelYearCnt = this.getCancelYearCnt(request);
        subscriptionResponse.setYearCanCnt(cancelYearCnt);
        //당월개통회선
        int actThisMonthCnt = this.getActThisMonthCnt(request);
        subscriptionResponse.setThisMonthActCnt(actThisMonthCnt);
        //미납조회
        int unpaidCnt = this.getUnpaidCnt(request);
        subscriptionResponse.setDelinqStatusCnt(unpaidCnt);
        //전체 개통 회선
        int actTotalCnt = this.getActTotalCnt(request);
        subscriptionResponse.setTotActCnt(actTotalCnt);

        //가입제한 (5월 세째주 KTM모바일에서 조건 전달해주기로 함)
        String subscriptionRestrictionsYn = "Y";
        String subscriptionRestrictionsResultMessage = "가능";
        //가입한도
        String subscriptionLimitYn = "";
        String subscriptionLimitResultMessage = "";
        //미납
        String unPaidYn = "";
        String unPaidResultMessage = "";
        //상습해지이력
        String historyOfCancellationYn = "Y";
        String historyOfCancellationResultMessage = "가능";
        //할부할인 (5월 세째주 KTM모바일에서 조건 전달해주기로 함)
        String installmentDiscountYn = "Y";
        String installmentDiscountResultMessage = "가능";

        //가입한도 >> 고객유형별 처리 : NA , NM , FN , FM , JP , GO
        switch (cstmrTypeCd) {
            case CstmrType.NATIVE_ADULT:
            case CstmrType.NATIVE_MINOR:
            case CstmrType.JURIDICAL_PERSON:
            case CstmrType.GOVERNMENT_ORGANIZATION:
                if (actThisMonthCnt >= 2 || actTotalCnt >= 3) {
                    subscriptionLimitYn = "N"; //가입한도
                    subscriptionLimitResultMessage = "불가능(??회선 사용중)"; //가입제한 메세지
                } else {
                    subscriptionLimitYn = "Y"; //가입한도
                    subscriptionLimitResultMessage = "가능(3회선 중 " + actTotalCnt + " 회선 사용중)"; //가입제한 메세지
                }
                break;
            case CstmrType.FOREIGN_ADULT:
            case CstmrType.FOREIGN_MINOR:
                if (actThisMonthCnt >= 1 || actTotalCnt >= 2) {
                    subscriptionLimitYn = "N"; //가입한도
                    subscriptionLimitResultMessage = "불가능(??회선 사용중)"; //가입제한 메세지
                } else {
                    subscriptionLimitYn = "Y"; //가입한도
                    subscriptionLimitResultMessage = "가능(2회선 중 " + actTotalCnt + " 회선 사용중)"; //가입제한 메세지
                }
                break;
            default:
                break;
        }

        //미납 >> 고객유형별 처리 : NA , NM , FN , FM , JP , GO
        if (unpaidCnt > 0) {
            unPaidYn = "N";
            unPaidResultMessage = "불가능(동일 고객 미납 보유)";
        } else {
            unPaidYn = "Y";
            unPaidResultMessage = "가능";
        }

        //상습해지이력 (상단에 기본값 설정함)
        if (actYearCnt == 3 && cancelYearCnt >= 1) {
            historyOfCancellationYn = "N";
            historyOfCancellationResultMessage = "3회선 사용 중 1회선 해지 후 1년 이내 가입 시 가입 불가능";
            //외국인은 2회선 한도라서 표시를 따로 해야하지 않을까? 또는 가입가능회선 중 1회선 해지 후 1년 이내 가입 시 가입 불가능
        } else if (actYearCnt == 2 && cancelYearCnt >= 1 && actTotalCnt == 1) {
            historyOfCancellationResultMessage = "가능";
        } else if (cancelYearCnt >= 1 && actTotalCnt >= 1) {
            historyOfCancellationYn = "N";
            historyOfCancellationResultMessage = "가입불가";
        }

        //subscriptionRestrictionsYn = "Y";
        //subscriptionLimitYn = "Y";
        //unPaidYn = "Y";
        //historyOfCancellationYn = "Y";
        //installmentDiscountYn = "Y";

        subscriptionResponse.setSubscriptionRestrictionsYn(subscriptionRestrictionsYn);
        subscriptionResponse.setSubscriptionRestrictionsResultMessage(subscriptionRestrictionsResultMessage);
        subscriptionResponse.setSubscriptionLimitYn(subscriptionLimitYn); //가입한도 조회 결과
        subscriptionResponse.setSubscriptionLimitResultMessage(subscriptionLimitResultMessage); //가입한도 조회 결과 메세지
        subscriptionResponse.setUnPaidYn(unPaidYn); //미납 조회 결과
        subscriptionResponse.setUnPaidResultMessage(unPaidResultMessage); //미납 조회 결과
        subscriptionResponse.setHistoryOfCancellationYn(historyOfCancellationYn); //상습해지이력 조회 결과
        subscriptionResponse.setHistoryOfCancellationResultMessage(historyOfCancellationResultMessage); //상습해지이력 조회 결과 메세지
        subscriptionResponse.setInstallmentDiscountYn(installmentDiscountYn);
        subscriptionResponse.setInstallmentDiscountResultMessage(installmentDiscountResultMessage);

        return subscriptionResponse;
    }

    /**
     * 1년이내 이내 사용회선 조회
     */
    public int getActYearCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectActYearCnt(request);
    }

    //public int getActYearCount(SubscriptionRequest request) {
    //    SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
    //    int actYearCnt = this.getActYearCnt(request);
    //    subscriptionResponse.setYearActCnt(actYearCnt);
    //    return actYearCnt;
    //}

    /**
     * 1년이내 해지
     */
    public int getCancelYearCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectCancelYearCnt(request);
    }

    //public int getCancelYearCount(SubscriptionRequest request) {
    //    SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
    //    int cancelYearCnt = this.getCancelYearCnt(request);
    //    subscriptionResponse.setYearCanCnt(cancelYearCnt);
    //    return cancelYearCnt;
    //}

    /**
     * 당월개통회선
     */
    public int getActThisMonthCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectActThisMonthCnt(request);
    }

    //public int getActThisMonthCount(SubscriptionRequest request) {
    //    SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
    //    int actThisMonthCnt = this.getActThisMonthCnt(request);
    //    subscriptionResponse.setThisMonthActCnt(actThisMonthCnt);
    //    return actThisMonthCnt;
    //}

    /**
     * 미납조회
     */
    public int getUnpaidCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectUnpaidCnt(request);
    }

    //public int getUnpaidCount(SubscriptionRequest request) {
    //    SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
    //    int unpaidCnt = this.getUnpaidCnt(request);
    //    subscriptionResponse.setDelinqStatusCnt(unpaidCnt);
    //    return unpaidCnt;
    //}

    /**
     * 전체 개통 회선
     */
    public int getActTotalCnt(SubscriptionRequest request) {
        return formCommReadMapper.selectActTotalCnt(request);
    }

    //public int getActTotalCount(SubscriptionRequest request) {
    //    SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
    //    int actTotalCnt = this.getActTotalCnt(request);
    //    subscriptionResponse.setTotActCnt(actTotalCnt);
    //    return actTotalCnt;
    //}

    /**
     * 개통이력조회
     */
    public FormResponse<String> getOpenHistory(SubscriptionRequest request) {
        String openHistoryYn = this.getOpenHistoryYn(request);
        return FormResponse.of(ResponseMessage.SUCCESS, openHistoryYn);
    }

    /**
     * 개통이력 존재여부 확인
     */
    public String getOpenHistoryYn(SubscriptionRequest request) {
        String openHistoryYn = "N";
        if ("HDN3".equals(request.getOperTypeCd()) || "HCN3".equals(request.getOperTypeCd())) {
            openHistoryYn = "Y"; //기기변경은 개통이력존재함.
        } else {
            int rtnValue = formCommReadMapper.selectOpenHistory(request);
            if (rtnValue > 0) {
                openHistoryYn = "Y"; //후불 개통이력 존재함.
            }
        }

        return openHistoryYn;
    }


    /**
     * 대량 법인 개통 가입조건조회
     */
    public FormResponse<BulkCorporateInfoResponse> getBulkCorporateOpenCheck(BulkCorporateInfoRequest request) {
        BulkCorporateInfoResponse response = this.getBulkCorporateOpenInfo(request);
        //int limitCount = 0;
        //int volumeMobileNoQnty = 0;

        if (response != null && "Y".equals(response.getCanBulkCorporateConditionYn())) {
            return FormResponse.of(ResponseMessage.VALID_BULK_CORPORATE_SUCCESS, response);
        } else {
            return FormResponse.of(ResponseMessage.VALID_BULK_CORPORATE_FAIL, response);
        }
    }

    /**
     * 대량 법인 개통 정보조회
     */
    public BulkCorporateInfoResponse getBulkCorporateOpenInfo(BulkCorporateInfoRequest request) {
        BulkCorporateInfoResponse bulkCorporateInfoResponse = new BulkCorporateInfoResponse();
        BulkCorporateInfoResponse responseSbscLimit = new BulkCorporateInfoResponse();
        BulkCorporateInfoResponse responseMsfRequest = new BulkCorporateInfoResponse();

        int corporateLimitQnty = 0; //가입가능 회선수
        int openCount = 0; //개통건수
        int completeCount = 0; //작성완료건수
        int limitCount = 0;
        int volumeMobileNoQnty = Optional.ofNullable(request.getVolumeMobileNoQnty()).orElse(0);

        String canBulkCorporateConditionYn = "N"; //대량법인 개통 신청서 작성가능여부
        String canBulkCorporateOpenYn = "N"; //대량법인 개통 가능여부
        String useStartDate = "";
        String useEndDate = "";

        String cstmrTypeCd = request.getCstmrTypeCd(); //고객유형
        String operTypeCd = request.getOperTypeCd(); //가입유형
        String agentCd = request.getAgentCd(); //화면에서 선택한 대리점코드
        String cpntId = request.getCpntId(); //로그인사용자의 판매점코드
        if (!StringUtils.hasText(cpntId)) {
            cpntId = AuthenticationUtils.getShopCode(); //로그인한 사용자의 판매점코드
        }

        //법인 , 신규개통 신청서 작성여부 체크
        if (CstmrType.JURIDICAL_PERSON.getCode().equals(cstmrTypeCd) && OperType.NEW_ACTIVATION.getCode().equals(operTypeCd)) {
            request.setAgentCd(agentCd); //대리점코드
            request.setCpntId(cpntId); //판매점코드

            //1. 가입가능회선수 조회 - MSF_STOR_OPEN_LIMIT_TXN
            responseSbscLimit = newChangeReadMapper.selectCorporateLimitQnty(request);
            if (responseSbscLimit != null) {
                corporateLimitQnty = responseSbscLimit.getSbscLmtQnty(); //대량 법인 개통 가능건수
                useStartDate = responseSbscLimit.getUseStartDate(); //
                useEndDate = responseSbscLimit.getUseEndDate(); //
                if (corporateLimitQnty > 0) {
                    canBulkCorporateOpenYn = "Y"; //대량 법인 개통가능여부
                }
            }

            //2. 작성완료 및 개통완료 건수 조회 - MSF_REQUEST
            if ("Y".equals(canBulkCorporateOpenYn)) {
                request.setUseStartDate(useStartDate);
                request.setUseEndDate(useEndDate);
                responseMsfRequest = newChangeReadMapper.selectCorporateOpenInfo(request);
                if (responseMsfRequest != null) {
                    openCount = responseMsfRequest.getOpenCount(); //개통건수
                    completeCount = responseMsfRequest.getCompleteCount(); //작성완료건수
                }
            }

            //3. 가입가능회선수와 개통진행요청건수 비교
            if (volumeMobileNoQnty > 0) {
                if ("Y".equals(canBulkCorporateOpenYn) && corporateLimitQnty > 0) {
                    limitCount = (corporateLimitQnty - (openCount + completeCount));
                }

                //대량 개통 법인 신청서 작성가능여부
                if (limitCount >= volumeMobileNoQnty) {
                    canBulkCorporateConditionYn = "Y";
                }
            }
        }

        log.debug(
            "getBulkCorporateOpenInfo >> canBulkCorporateConditionYn: {}, canBulkCorporateOpenYn: {}, corporateLimitQnty: {}, openCount: {}, completeCount: {}, limitCount: {}",
            canBulkCorporateConditionYn,
            canBulkCorporateOpenYn,
            corporateLimitQnty,
            openCount,
            completeCount,
            limitCount);

        bulkCorporateInfoResponse.setCanBulkCorporateConditionYn(canBulkCorporateConditionYn);//대량 법인 개통 대상여부
        bulkCorporateInfoResponse.setCanBulkCorporateOpenYn(canBulkCorporateOpenYn); //대량 법인 개통 대상여부
        bulkCorporateInfoResponse.setSbscLmtQnty(corporateLimitQnty); //대량 법인 개통 가능건수 - DB입력값
        bulkCorporateInfoResponse.setLimitCount(limitCount); //작성가능건수
        bulkCorporateInfoResponse.setOpenCount(openCount); //개통건수
        bulkCorporateInfoResponse.setCompleteCount(completeCount); //작성완료건수

        return bulkCorporateInfoResponse;
    }


    //2. 동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회
    //   추후 처리
    //3. 이력정보 저장
    //   ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
    //4. eSIM 정보 체크
    //   데이타 설정 , eSIM 정보로 부정사용주장단말 확인
    //5. 인증 정보 확인??
    //   미성년자의 나이 등 체크.
    //6. 유심비 /  가입비 설정
    //7. 정책에서 할인요금 조회에서 등록
    //8. 접점코드로 대리점 코드 조회


}
