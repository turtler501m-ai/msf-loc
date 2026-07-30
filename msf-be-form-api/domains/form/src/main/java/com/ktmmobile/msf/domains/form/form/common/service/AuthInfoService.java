package com.ktmmobile.msf.domains.form.form.common.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormOsstServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.KnoteScanInfoFs0Vo;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlFs0VO;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeCustomerInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormX01SelfCareInRequest;
import com.ktmmobile.msf.domains.form.form.termination.repository.CancelPageRepositoryImpl;

/**
 * KTM모바일 고객인증, 신분증 목록 조회
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthInfoService {

    private final McpApiClient mcpApiClient;
    private final MspPrxClient mspPrxClient;
    private final AuthInfoReadMapper authInfoReadMapper;
    private final MsfMplatFormOsstServerAdapter mplatFormOsstServerAdapter;
    private final MsfMcpOsstPrxService msfMcpOsstPrxService;
    private final AgencyCacheReader agencyCacheReader;
    private final CancelPageRepositoryImpl cancelPageRepository;

    //@Autowired
    private final MspApiDirectRepository mspApiDirectRepository;

    //KTM모바일 고객인증
    public FormResponse<MspJuoSubInfoResponse> getKtmMemberInfo(MspJuoSubInfoRequest request) {
        log.debug("★ KTM모바일 고객인증 ★ customerLinkName: {}, customerSsn: {}, subscriberNo: {}",
            request.getCustomerLinkName(),
            request.getCustomerSsn(),
            request.getSubscriberNo());

        //전화번호 parameter 명 변경으로 예외처리
        if (StringUtils.hasText(request.getCustomerMobileNo())) {
            request.setCustomerMobileNo(request.getSubscriberNo());
        }
        MspJuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(request); //고객조회

        if (data == null) {
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_FAIL);
        }
        return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_SUCCESS, data);
    }

    /**
     * 신규변경 고객인증
     */
    public FormResponse<NewChangeCustomerInfoResponse> authNewChangeAuthInfo(MspJuoSubInfoRequest request) {
        NewChangeCustomerInfoResponse rtnData = new NewChangeCustomerInfoResponse();
        log.debug("★ KTM모바일 고객인증 ★ REQUEST >> cstmrType: {}, cstmrSsn: {}, customerLinkName: {}, customerMobileNo: {}, cstmrJuridicalRrn: {}",
            request.getCstmrType(),
            request.getCstmrSsn(),
            request.getCustomerLinkName(),
            request.getCustomerMobileNo(),
            request.getCstmrJuridicalRrn());

        String cstmrType = request.getCstmrType(); //고객유형
        String cstmrSsn = request.getCstmrSsn(); //고객식별번호
        String customerLinkName = request.getCustomerLinkName(); //고객명
        String customerMobileNo = request.getCustomerMobileNo();
        String cstmrJuridicalRrn = request.getCstmrJuridicalRrn(); //법인 또는 공공기관의 법인번호 - 쿼리에서 법인번호에 해당하는 컬럼을 안가져오고 있음. 음.....
        //if (!StringUtils.hasText(cstmrType) || !StringUtils.hasText(cstmrSsn) || !StringUtils.hasText(customerLinkName) || !StringUtils.hasText(customerMobileNo)) {
        if (!StringUtils.hasText(cstmrType) || !StringUtils.hasText(customerLinkName) || !StringUtils.hasText(customerMobileNo)) {
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_NECESSARY);
        }

        //1. 고객조회
        String custId = ""; //고객아이디
        String customerSsn = ""; //고객식별번호
        String ncn = ""; //서비스계약번호
        String ctn = ""; //가입핸드폰번호
        String drivrLicnsNo = "";
        //String taxId = "";
        MspJuoSubInfoResponse memberInfo = authInfoReadMapper.selectKtmCustomer(request); //고객조회
        log.debug("memberInfo : ", memberInfo);

        if (memberInfo == null) {
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_NOT_FOUND);
        }

        log.debug("★ KTM모바일 고객인증 ★ [1] 고객조회 : RESPONSE >> CustomerId: {}, ContractNum: {}, SubscriberNo: {}",
            memberInfo.getCustomerId(), memberInfo.getContractNum(), memberInfo.getSubscriberNo());
        if (!StringUtils.hasText(memberInfo.getCustomerId())) { //개발 기준이나 고객아이디가 없는 정상 고객은 없음.
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_NOT_FOUND);
        } else {
            customerSsn = memberInfo.getCustomerSsn(); //고객유형 B의 경우 TAX_ID 와 동일한 값을 암호화하여 저장하고 있음.
            drivrLicnsNo = memberInfo.getDrivrLicnsNo(); //법인번호가 저장되고 있음. 암호화하지 않은 값
            //taxId = memberInfo.getTaxId(); //사업자번호가 저장되고 있음. 암호호하지 않은 값
            custId = memberInfo.getCustomerId();
            ncn = memberInfo.getContractNum();
            ctn = memberInfo.getSubscriberNo();
            if (!StringUtils.hasText(custId) || !StringUtils.hasText(ncn) || !StringUtils.hasText(ctn)) {
                return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_NOT_FOUND); //조회된 데이타가 없다면 리턴
            } else {
                //request parameter 와 고객조회 식별번호 비교
                log.debug("★ KTM모바일 고객인증 : 결과비교 >> customerSsn: {}, cstmrSsn: {} ", customerSsn, cstmrSsn);
                if ("JP".equals(cstmrType) && drivrLicnsNo != null && !drivrLicnsNo.equals(cstmrJuridicalRrn)) { //법인고객의 법인번호 비교 - 테스트 데이타 없음으로 확인불가
                    log.debug("결과비교 >> cstmrType: {}, cstmrJuridicalRrn: {}, drivrLicnsNo: {}", cstmrType, cstmrJuridicalRrn, drivrLicnsNo);
                    return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_NOT_FOUND);
                }
                if (!customerSsn.equals(cstmrSsn)) { //고객정보가 맞지 않음.
                    log.debug("결과비교 >> customerSsn: {} ", customerSsn);
                    return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_NOT_FOUND);
                }
                //법인과 공공기관은 법인번호로 값을 주는 경우에 처리예정
                //if ((StringUtils.hasText(cstmrJuridicalRrn)) && (CstmrType.JURIDICAL_PERSON.getCode().equalsIgnoreCase(cstmrType) || CstmrType.GOVERNMENT_ORGANIZATION.getCode().equalsIgnoreCase(cstmrType))) {
                //비교로직 추가
                //}
            }
        }

        //2. 가입계약정보 조회
        MplatFormX01SelfCareInRequest mpX01SelfCareInRequest = new MplatFormX01SelfCareInRequest();
        Map<String, String> rtnSubscriptionInfo = new HashMap<>();
        mpX01SelfCareInRequest.setNcn(ncn); //계약번호
        mpX01SelfCareInRequest.setCtn(ctn); //전화번호
        mpX01SelfCareInRequest.setCustId(custId); //고객아이디

        rtnSubscriptionInfo = this.getCustomerSubscriptionInfo(mpX01SelfCareInRequest);
        log.debug("★ KTM모바일 고객인증 ★ [2] 가입계약정보조회(X01) : RESPONSE >> (rtnSubscriptionInfo == null): {}",
            (rtnSubscriptionInfo == null) ? null : rtnSubscriptionInfo.toString());
        String email = ""; //이메일주소
        String addr = ""; //주소
        String homeTel = ""; //전화번호
        String initActivationDate = ""; //가입일

        if (rtnSubscriptionInfo == null) {
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_NOT_FOUND_CONTRACT); //고객정보가 존재하지 않습니다.
        } else {
            email = rtnSubscriptionInfo.get("email"); //이메일주소
            //addr = rtnSubscriptionInfo.get("addr"); //주소
            homeTel = rtnSubscriptionInfo.get("homeTel"); //전화번호
            initActivationDate = rtnSubscriptionInfo.get("initActivationDate"); //가입일
            log.debug("★ KTM모바일 고객인증 ★ [2] 가입계약정보조회(X01) : RESPONSE >> email: {}, addr: {}, homeTel: {}. initActivationDate: {}",
                email, addr, homeTel, initActivationDate);
        }
        rtnData.setEmail(email); //이메일주소
        //rtnData.setAddr(addr); //주소
        rtnData.setHomeTel(homeTel); //전화번호
        rtnData.setInitActivationDate(initActivationDate); //가입일

        //3. 가입중 요금제 조회
        McpUserCntrMngDto contractList = mspApiDirectRepository.query("/mypage/socDesc", ncn, McpUserCntrMngDto.class);
        String soc = "";
        String rateNm = "";
        log.debug("★ KTM모바일 고객인증 ★ [3] 가입중 요금제 조회 (mypage/socDesc)  : RESPONSE >> (contractList == null): {}",
            (contractList == null) ? "null" : contractList.toString());
        if (contractList == null) {
            log.debug("가입중 요금제 조회가 되지 않습니다");
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_NOT_FOUND_CONTRACT); //계약정보가 존재하지 않습니다.
        } else {
            soc = contractList.getSoc();
            rateNm = contractList.getRateNm();
            log.debug("★ KTM모바일 고객인증 ★ [3] 가입중 요금제 조회 (mypage/socDesc)  : RESPONSE >> soc: {}, rateNm: {}", soc, rateNm);
        }
        rtnData.setSocCode(soc);
        rtnData.setSocNm(rateNm);

        //4. 현재 신청서 서비스해지, 명의변경, 기기변경이 진행중인 신청서가 있는지 확인
        boolean inProgressForm = cancelPageRepository.existsInProgressApplicationByMobileNo(request.getCustomerMobileNo());
        log.debug("서비스해지, 명의변경, 기기변경 진행중인 신청서가 있는지 확인 >> 일단 확인은 주석처리", inProgressForm);
        if (inProgressForm) {
            log.warn("[checkInProgressApplication] fail: in-progress application exists, mobileNo={}", request.getSubscriberNo());
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_CANNOT_APPLY); //현재 진행중인 신청서가 있어 신청할 수 없습니다.
        }

        //기기변경 인증결과
        if (rtnData == null) { //요게 의미가 있는건지. ㅋㅋㅋ
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_FAIL);
        }
        return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_SUCCESS, rtnData);
    }

    //X01 - 가입정보 (Subscription information)
    //public MplatFormX01OutDtoResponse getCustomerSubscriptionInfo(MplatFormX01SelfCareInRequest request) {
    public Map<String, String> getCustomerSubscriptionInfo(MplatFormX01SelfCareInRequest request) {
        Map<String, String> rtnSubscriptionInfo = new HashMap<>();
        //MplatFormX01OutDtoResponse data = new MplatFormX01OutDtoResponse();

        String globalNo = ""; //PRX 연동 결과 - globalNo
        String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
        String responseCode = ""; //PRX 연동 결과 - responseCode
        String responseBasic = "";

        Map<String, String> params = new HashMap<>();
        params.put("appEventCd", "X01");
        params.put("custId", request.getCustId()); //고객아이디
        params.put("ncn", request.getNcn()); //계약번호
        params.put("ctn", request.getCtn()); //전화번호
        params.put("clntIp", RequestUtils.getClientIp()); //Client IP
        params.put("clntUsrId", AuthenticationUtils.getUser().getUserId()); //사용자 User ID

        MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder().parameters(params).build();
        MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callService(mspPrxFormRequest);

        if (mspPrxSoapResponse == null) { //결과 없는 경우
            return null;
        } else { //결과 있는 경우
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode();
            responseBasic = mspPrxSoapResponse.responseBasic();

            log.debug("★ 가입정보조회 결과 (X01) 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
                responseType,
                responseCode,
                responseBasic,
                globalNo);

            if ("N".equals(responseType)) { //연동 성공
                String email = mspPrxSoapResponse.payloadValue("outDto", "email").map(Object::toString).orElse("");
                String addr = mspPrxSoapResponse.payloadValue("outDto", "addr").map(Object::toString).orElse("");
                String homeTel = mspPrxSoapResponse.payloadValue("outDto", "homeTel").map(Object::toString).orElse("");
                String initActivationDate = mspPrxSoapResponse.payloadValue("outDto", "initActivationDate").map(Object::toString).orElse("");

                rtnSubscriptionInfo.put("email", email);
                rtnSubscriptionInfo.put("addr", addr);
                rtnSubscriptionInfo.put("homeTel", homeTel);
                rtnSubscriptionInfo.put("initActivationDate", initActivationDate);
            }
        }

        return rtnSubscriptionInfo;
    }

    //X23 - 납부정보 (Payment information)
    //public Map<String, String> getCustomerPaymentInfo(MplatFormX23SelfCareInRequest request) {
    //    Map<String, String> rtnPaymentInfo = new HashMap<>();
    //
    //    String globalNo = ""; //PRX 연동 결과 - globalNo
    //    String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
    //    String responseCode = ""; //PRX 연동 결과 - responseCode
    //    String responseBasic = "";
    //
    //    Map<String, String> params = new HashMap<>();
    //    params.put("appEventCd", "X23");
    //    params.put("custId", request.getCustId()); //고객아이디
    //    params.put("ncn", request.getNcn()); //계약번호
    //    params.put("ctn", request.getCtn()); //전화번호
    //    params.put("clntIp", RequestUtils.getClientIp()); //Client IP
    //    params.put("clntUsrId", AuthenticationUtils.getUser().getUserId()); //사용자 User ID
    //
    //    MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder().parameters(params).build();
    //    MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callService(mspPrxFormRequest);
    //
    //    if (mspPrxSoapResponse == null) { //결과 없는 경우
    //        return null;
    //    } else { //결과 있는 경우
    //        globalNo = mspPrxSoapResponse.globalNo();
    //        responseType = mspPrxSoapResponse.responseType();
    //        responseCode = mspPrxSoapResponse.responseCode();
    //        responseBasic = mspPrxSoapResponse.responseBasic();
    //
    //        log.debug("★ 가입정보조회 결과 (X23) 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
    //            responseType,
    //            responseCode,
    //            responseBasic,
    //            globalNo);
    //
    //        if ("N".equals(responseType)) { //연동 성공
    //            String payMethod = mspPrxSoapResponse.payloadValue("outDto", "payMethod").map(Object::toString).orElse(""); //납부방법
    //            String payBizrCd = mspPrxSoapResponse.payloadValue("outDto", "payBizrCd").map(Object::toString).orElse(""); //간편결제사업자코드
    //            String billCycleDueDay = mspPrxSoapResponse.payloadValue("outDto", "billCycleDueDay").map(Object::toString).orElse(""); //납부일
    //            String blBankName = mspPrxSoapResponse.payloadValue("outDto", "blBankName").map(Object::toString).orElse(""); //은행명
    //            String blBankAcctNo = mspPrxSoapResponse.payloadValue("outDto", "blBankAcctNo").map(Object::toString).orElse(""); //계좌번호
    //            String bankAcctHolderName = mspPrxSoapResponse.payloadValue("outDto", "bankAcctHolderName").map(Object::toString).orElse(
    //                ""); //납부자명
    //            String blAddr = mspPrxSoapResponse.payloadValue("outDto", "blAddr").map(Object::toString).orElse(""); //청구지 주소
    //            String creditCardName = mspPrxSoapResponse.payloadValue("outDto", "creditCardName").map(Object::toString).orElse(""); //카드사명
    //            String prevCardNo = mspPrxSoapResponse.payloadValue("outDto", "prevCardNo").map(Object::toString).orElse(""); //카드번호
    //            String prevExpirDt = mspPrxSoapResponse.payloadValue("outDto", "prevExpirDt").map(Object::toString).orElse(""); //카드만료기간
    //            String jointBillWithKt = mspPrxSoapResponse.payloadValue("outDto", "jointBillWithKt").map(Object::toString).orElse(""); //KT합산구분 아이디
    //            String payTmsCd = mspPrxSoapResponse.payloadValue("outDto", "payTmsCd").map(Object::toString).orElse(""); //납부회차
    //
    //            rtnPaymentInfo.put("payMethod", payMethod);
    //            rtnPaymentInfo.put("payBizrCd", payBizrCd);
    //            rtnPaymentInfo.put("billCycleDueDay", billCycleDueDay);
    //            rtnPaymentInfo.put("blBankName", blBankName);
    //            rtnPaymentInfo.put("blBankAcctNo", blBankAcctNo);
    //            rtnPaymentInfo.put("bankAcctHolderName", bankAcctHolderName);
    //            rtnPaymentInfo.put("blAddr", blAddr);
    //            rtnPaymentInfo.put("creditCardName", creditCardName);
    //            rtnPaymentInfo.put("prevCardNo", prevCardNo);
    //            rtnPaymentInfo.put("prevExpirDt", prevExpirDt);
    //            rtnPaymentInfo.put("joinBillWithKt", jointBillWithKt);
    //            rtnPaymentInfo.put("payTmsCd", payTmsCd);
    //        }
    //    }
    //
    //    return rtnPaymentInfo;
    //}

    //Y02 - 가입중인 요금제정보 (PricePlan information)
    //public Map<String, String> getCustomerPricePlanInfo(MplatFormY02SelfCareInRequest request) {
    //    Map<String, String> rtnPricePlanInfo = new HashMap<>();
    //
    //    String globalNo = ""; //PRX 연동 결과 - globalNo
    //    String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
    //    String responseCode = ""; //PRX 연동 결과 - responseCode
    //    String responseBasic = "";
    //
    //    Map<String, String> params = new HashMap<>();
    //    params.put("appEventCd", "Y02");
    //    params.put("custId", request.getCustId()); //고객아이디
    //    params.put("ncn", request.getNcn()); //계약번호
    //    params.put("ctn", request.getCtn()); //전화번호
    //    params.put("clntIp", "127.0.0.1"); //Client IP
    //    params.put("clntUsrId", AuthenticationUtils.getUser().getUserId()); //사용자 User ID
    //
    //    MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder().parameters(params).build();
    //    MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callService(mspPrxFormRequest);
    //
    //    if (mspPrxSoapResponse == null) { //결과 없는 경우
    //        return null;
    //    } else { //결과 있는 경우
    //        globalNo = mspPrxSoapResponse.globalNo();
    //        responseType = mspPrxSoapResponse.responseType();
    //        responseCode = mspPrxSoapResponse.responseCode();
    //        responseBasic = mspPrxSoapResponse.responseBasic();
    //
    //        log.debug("★ 가입정보조회 결과 (Y02) 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
    //            responseType,
    //            responseCode,
    //            responseBasic,
    //            globalNo);
    //
    //        if ("N".equals(responseType)) { //연동 성공
    //            String efctStDt = mspPrxSoapResponse.payloadValue("outDto", "efctStDt").map(Object::toString).orElse(""); //납부방법
    //            String famtTarifAmt = mspPrxSoapResponse.payloadValue("outDto", "famtTarifAmt").map(Object::toString).orElse(""); //간편결제사업자코드
    //            String prodId = mspPrxSoapResponse.payloadValue("outDto", "prodId").map(Object::toString).orElse(""); //납부일
    //            String prodNm = mspPrxSoapResponse.payloadValue("outDto", "prodNm").map(Object::toString).orElse(""); //은행명
    //
    //            rtnPricePlanInfo.put("efctStDt", efctStDt);
    //            rtnPricePlanInfo.put("famtTarifAmt", famtTarifAmt);
    //            rtnPricePlanInfo.put("prodId", prodId);
    //            rtnPricePlanInfo.put("prodNm", prodNm);
    //        }
    //    }
    //
    //    return rtnPricePlanInfo;
    //}


    // 서비스변경/해지 전용 인증.
    // 신규/기기변경 인증은 CONTRACT_NUM 중심이지만, 서비스변경/해지는 후속 조회에서 NCN(SVC_CNTR_NO)이 필요하므로 분리한다.
    public FormResponse<MspJuoSubInfoResponse> getServiceChangeAuthInfo(MspJuoSubInfoRequest request) {
        log.debug("서비스변경/해지 휴대폰 인증 customerLinkName: {}, customerSsn: {}, subscriberNo: {}",
            request.getCustomerLinkName(),
            request.getCustomerSsn(),
            request.getSubscriberNo());

        String subStatus = authInfoReadMapper.selectServiceChangeCustomerSubStatus(request);
        if (!StringUtils.hasText(subStatus)) {
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_NOT_FOUND);
        }
        if ("C".equals(subStatus)) {
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_TERMINATED);
        }

        MspJuoSubInfoResponse data = authInfoReadMapper.selectServiceChangeCustomer(request);

        if (data == null) {
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_FAIL);
        }
        return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_SUCCESS, data);
    }


    /**
     * Knote 신분증 목록 조회 (서식지 목록조회 FS0)
     */
    @BusinessContextBoundary
    public FormResponse<MSimpleOsstXmlFs0VO> getIdList(KnoteScanInfoRequest knoteScanInfoRequest) {
        BusinessContextHolder.setParentScanId(knoteScanInfoRequest.getParentScanId());

        log.debug("★ Knote 신분증 목록 조회 ★ agentCd: {}", knoteScanInfoRequest.getAgentCd());

        ObjectMapper objectMapper = new ObjectMapper();
        MSimpleOsstXmlFs0VO knoteScanIdList = new MSimpleOsstXmlFs0VO();
        //KnoteScanInfoRequest request = new KnoteScanInfoRequest();

        String operTypeCd = knoteScanInfoRequest.getOperTypeCd();
        if (StringUtils.hasText(operTypeCd)) {
            //knote목록에는 우수기변 구분없이 HCN으로 오는듯합니다.2026.07.02
            if ("HDN3".equals(operTypeCd)) {
                operTypeCd = "HCN";
            } else {
                operTypeCd = operTypeCd.substring(0, operTypeCd.length() - 1);
            }

        } else {
            return FormResponse.of(ResponseMessage.SUCCESS, knoteScanIdList);
        }

        String ktOrgId = ""; //KT조직아이디
        String storCd = AuthenticationUtils.getShopCode(); //로그인 사용자에 매핑된 판매점코드
        String agentCd = knoteScanInfoRequest.getAgentCd(); //판매점이 선택한 대리점코드

        //KT조직코드 Cache 에서 가져오기
        Optional<AgencyCache> agentInfo = agencyCacheReader.getAgency(agentCd);
        if (agentInfo.isPresent()) {
            ktOrgId = agentInfo.get().ktOrganizationId();
        }

        String requestScanStartDate = ""; //스캔 시작일시
        String requestScanEndDate = ""; //스캔 시작일시
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        requestScanStartDate = today.minusDays(1).format(formatter);
        requestScanEndDate = today.format(formatter);
        //requestScanStartDate = today.minusDays(10).format(formatter); //개통전 사전체크에서 유효기간이 지났다고 나옴. 흠...

        //request.setMngmAgncId(ktOrgId); //개통요청 대리점코드
        //request.setCntpntCd(storCd); //개통요청 접점코드 : Optional
        //request.setRetvStrtDt(requestScanStartDate); //조회시작일시
        //request.setRetvEndDt(requestScanEndDate); //조회종료일시
        //request.setSvcApyTrtStatCd("1"); //처리상태조회 1:접수, 2:진행, 3:완료, 4:취소
        //request.setRetvSeq("0"); //Optional :: 미 입력 시 0
        //request.setSvcApyTrtStatCd("40"); //Optional :: 미 입력시 40

        //log.debug("★ Knote 신분증 목록 조회 (prx) ★ mngmAgncId: {}, cntpntCd: {}, retvStrtDt: {}, retvEndDt: {}, svcApyTrtStatCd: {}",
        //    request.getMngmAgncId(),
        //    request.getCntpntCd(),
        //    request.getRetvStrtDt(),
        //    request.getRetvEndDt(),
        //    request.getSvcApyTrtStatCd());

        String globalNo = ""; //PRX 연동 결과 - globalNo
        String responseType = ""; //PRX 연동 결과 - responseType (N / E / S 등)
        String responseCode = ""; //PRX 연동 결과 - responseCode
        String responseBasic = "";

        Map<String, String> params = new HashMap<>();
        params.put("appEventCd", "FS0");
        params.put("mngmAgncId", ktOrgId); //신분증 목록조회를 위한 KT조직코드
        params.put("cntpntCd", storCd); //신분증 목록조회를 위한 판매점접점
        params.put("retvStrtDt", requestScanStartDate);
        params.put("retvEndDt", requestScanEndDate);
        params.put("svcApyTrtStatCd", "1"); //1 : 접수, 2: 진행, 3: 완료, 4: 취소 >> Null 로 입력시 전체 목록조회
        //params.put("retvSeq", "0");
        //params.put("retvCascnt", "40");
        //responseType: E, responseCode: ITL_FMP_E0001, responseBasic: 가입대리점 정보가 존재하지 않습니다., globalNo: 9122533020260527171134098

        log.debug("★ Knote 신분증 목록 조회 (prx) ★ appEventCd: {}, mngmAgncId: {}, cntpntCd: {}, retvStrtDt: {}, retvEndDt: {}, svcApyTrtStatCd: {}",
            params.get("appEventCd"),
            params.get("mngmAgncId"),
            params.get("cntpntCd"),
            params.get("retvStrtDt"),
            params.get("retvEndDt"),
            params.get("svcApyTrtStatCd"));

        //MplatFormFS0Request mplatFormFS0Request = new MplatFormFS0Request();
        //MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(mplatFormFS0Request),
        //    FRMPAP_ID_LIST_SEARCH.getEventCd(), "1111");

        MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder().parameters(params).build();
        MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callSimpleOpenService(mspPrxFormRequest);

        if (mspPrxSoapResponse == null) { //연동결과 실패.
            return FormResponse.of(ResponseMessage.NO_DATA);
        } else { //연동결과 성공
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode(); //responseType 값이 E 일 경우 responseCode 값이 넘어오는 것으로 확인됨.
            responseBasic = mspPrxSoapResponse.responseBasic();

            log.debug("★ 연동결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
                responseType,
                responseCode,
                responseBasic,
                globalNo);
            if ("N".equals(responseType)) {
                String finalOperTypeCd = operTypeCd;
                List<Object> tmpList = mspPrxSoapResponse.payloadList("outDto").orElse(Collections.emptyList());
                //List<KnoteScanInfoFs0Vo> knoteScanList = null;
                List<KnoteScanInfoFs0Vo> knoteScanList = new ArrayList<>();
                if (tmpList == null || tmpList.isEmpty()) { //등록된 서식지가 없음.
                    //단건 처리
                    Map<String, Object> rtnMap = mspPrxSoapResponse.payloadValue("outDto")
                        .filter(Map.class::isInstance)
                        .map(Map.class::cast)
                        .orElse(Collections.emptyMap());

                    if (!rtnMap.isEmpty()) {
                        log.debug("rtnMap: {}", rtnMap.toString());
                        log.debug("frmpapId: {}", rtnMap.get("frmpapId"));
                        log.debug("custNm: {}", rtnMap.get("custNm"));
                        log.debug("custIdntNo: {}", rtnMap.get("custIdntNo"));

                        KnoteScanInfoFs0Vo vo = new KnoteScanInfoFs0Vo();
                        if (finalOperTypeCd.equals(rtnMap.get("onlineCustTrtSttusChgCd").toString())) {
                            vo.setCustNm(rtnMap.get("custNm").toString());
                            vo.setCustIdntNo(rtnMap.get("custIdntNo").toString());
                            vo.setApyTypeCd(rtnMap.get("apyTypeCd").toString());
                            vo.setFxdformIngrsPath1Cd(rtnMap.get("fxdformIngrsPath1Cd").toString());
                            vo.setOnlineCustTrtSttusChgCd(rtnMap.get("onlineCustTrtSttusChgCd").toString());
                            vo.setCustIdntNoIndCd(rtnMap.get("custIdntNoIndCd").toString());
                            vo.setSvcApyTrtStatCd(rtnMap.get("svcApyTrtStatCd").toString());
                            vo.setCntpntCd(rtnMap.get("cntpntCd").toString());
                            vo.setFrmpapId(rtnMap.get("frmpapId").toString());
                            knoteScanList.add(vo);
                            knoteScanIdList.setList(knoteScanList);
                        }
                    } else {
                        return FormResponse.of(ResponseMessage.SUCCESS, knoteScanIdList);
                    }

                } else {//등록된 서식지 있음.
                    //다건 처리
                    knoteScanList = tmpList.stream()
                        .map(tmpInfo -> {
                            Map<String, String> map = (Map<String, String>) tmpInfo;
                            KnoteScanInfoFs0Vo vo = objectMapper.convertValue(map, KnoteScanInfoFs0Vo.class);
                            //vo.setCustNm(vo.getCustNm() + "_" + vo.getOnlineCustTrtSttusChgCd());
                            return vo;
                        })
                        .filter(vo -> vo.getOnlineCustTrtSttusChgCd().equalsIgnoreCase(finalOperTypeCd))
                        .toList();
                    log.debug("knoteScanList: {}", knoteScanList);
                    knoteScanIdList.setList(knoteScanList);
                }
            }
        }
        //MSimpleOsstXmlFs0VO knoteScanIdList = new MSimpleOsstXmlFs0VO();
        return FormResponse.of(ResponseMessage.SUCCESS, knoteScanIdList);
    }

    /**
     * Knote 신분증 상태 조회 (서식지 상태조회 FS1)
     */
    public FormResponse<KnoteScanInfoResponse> checkIdStatus(KnoteScanInfoRequest knoteScanInfoRequest) {
        KnoteScanInfoResponse knoteScanInfoResponse = new KnoteScanInfoResponse();
        log.debug("★ Knote 신분증 상태 조회 ★ mngmAgncId: {}, cntpntCd: {}, frmpapId: {}",
            knoteScanInfoRequest.getAgentCd(),
            knoteScanInfoRequest.getCntpntCd(),
            knoteScanInfoRequest.getFrmpapId());

        //선택한 신분증이 없음.
        String frmpapId = knoteScanInfoRequest.getFrmpapId();
        if (!StringUtils.hasText(frmpapId)) {
            return FormResponse.of(ResponseMessage.NO_DATA);
        }

        String storCd = AuthenticationUtils.getShopCode(); //로그인세션의 매장코드
        String agentCd = knoteScanInfoRequest.getAgentCd(); //신분증 선택해서 처리하므로 세션 확인하지 않음.
        String ktOrgId = ""; //KT조직코드 조회

        //KT조직코드 조회
        Optional<AgencyCache> agentInfo = agencyCacheReader.getAgency(agentCd);
        if (agentInfo.isPresent()) {
            ktOrgId = agentInfo.get().ktOrganizationId();
        }

        if (!StringUtils.hasText(ktOrgId)) {
            return FormResponse.of(ResponseMessage.NO_DATA);
        }

        String globalNo = ""; //PRX 연동 결과 - globalNo
        String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
        String responseCode = ""; //PRX 연동 결과 - responseCode
        //String responseBasic = "";

        //요청 parameter
        Map<String, String> params = new HashMap<>();
        params.put("appEventCd", "FS1");
        params.put("frmpapId", knoteScanInfoRequest.getFrmpapId()); //암호화 필요하지 않음.
        params.put("mngmAgncId", ktOrgId); //
        params.put("cntpntCd", storCd); //

        //PRX FS1 호출
        MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder().parameters(params).build();
        MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callSimpleOpenService(mspPrxFormRequest);

        //서비스 연동 이력 저장 - 1. 호출

        //PRX 호출 응답 처리
        if (mspPrxSoapResponse == null) { //결과 없는 경우
            // 서비스 연동 이력 저장 - 2. 결과 - 응답없음
            return FormResponse.of(ResponseMessage.NO_DATA);
        } else { //결과 있는 경우
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode(); //responseType 값이 E 일 경우 responseCode 값이 넘어오는 것으로 확인됨.
            //responseBasic = mspPrxSoapResponse.responseBasic();

            // 서비스 연동 이력 저장 - 3. 결과 - 응답있음
            log.debug("★ 서식지 상태조회 >> responseType: {}, responseCode: {}, globalNo: {}", responseType, responseCode, globalNo);

            ObjectMapper objectMapper = new ObjectMapper();
            if ("N".equals(responseType)) { //연동 성공
                knoteScanInfoResponse = mspPrxSoapResponse.payloadObject("outDto")
                    .map(obj -> objectMapper.convertValue(obj, KnoteScanInfoResponse.class))
                    .orElse(new KnoteScanInfoResponse());

                //String nflCustNm = knoteScanInfoResponse.getNflCustNm(); //명의자 고객명
                //String nflCustIdfyNo = knoteScanInfoResponse.getNflCustIdfyNo(); //명의자 식별번호
                //String frmpapId_compare = knoteScanInfoResponse.getFrmpapId(); //서식지아이디
                //String custIdntNoIndCd = knoteScanInfoResponse.getCustIdntNoIndCd(); //명의자 식별구분코드
                //String custTypeCd = knoteScanInfoResponse.getCustTypeCd(); //명의자 고객유형
                //String opnYn = knoteScanInfoResponse.getOpnYn(); //개통여부
                //String svcApyTrtSttusCd = knoteScanInfoResponse.getSvcApyTrtSttusCd(); //처리상태코드

                return FormResponse.of(ResponseMessage.VALID_KNOTE_SCAN_STATUS_SUCCESS, knoteScanInfoResponse);
            } else if ("S".equals(responseType)) { //
                if ("ITL_SYS_E1204".equals(responseCode)) { //CRIS 응답 오류[에러코드:07, 에러메세지:null]
                    return FormResponse.of(ResponseMessage.VALID_KNOTE_SCAN_STATUS_FAIL, knoteScanInfoResponse);
                }
                return FormResponse.of(ResponseMessage.VALID_KNOTE_SCAN_STATUS_FAIL, knoteScanInfoResponse);
            } else if ("E".equals(responseType)) { //
                //responseCode: ITL_FMP_E0001
                //responseBasic: 서식지 정보가 존재하지 않습니다.
                if ("ITL_FMP_E0001".equals(responseCode)) {
                    return FormResponse.of(ResponseMessage.VALID_KNOTE_SCAN_STATUS_NO_DATE, knoteScanInfoResponse);
                }
                return FormResponse.of(ResponseMessage.VALID_KNOTE_SCAN_STATUS_FAIL, knoteScanInfoResponse);
            }

            //frmpapId	서식지아이디
            //titl	제목
            //mngmAgncId    	관리대리점코드
            //mngmAgncNm	관리대리점명
            //onlineCustTrtSttusChgCd  	신청유형코드
            //wapplRegDate    	서식지 등록일시
            //frmpapRegPathCd	판매경로코드명
            //fxdformIngrsCdNm	판매경로명
            //userId	판매자아이디
            //userNm	판매자명
            //cntpntCd	접점코드
            //cntpntNm	접점코드명
            //custIdntNoIndCd	명의자 식별구분코드
            //custTypeCd	명의자 고객유형
            //nflCustNm	명의자 고객명
            //nflCustIdfyNo	명의자 식별번호
            //custNm	고객명
            //realEvdnDataInd	실명인증 증빙자료구분
            //realCustIdntNo	실명인증 식별번호
            //realIssuDate	실명인증 발급일자
            //opnYn	개통여부
            //svcApyTrtSttusCd	처리상태코드
            //svcContId	서비스계약아이디
            //saleCmpnId	사업자코드
            //photoAthnDecideCd	사진인증판정코드
            //photoAthnSkipCd	사진인증생략코드
            //photoAthnErrCd	사진인증오류코드
            //photoAthnResltDtlCd	사진인증결과상세코드
            //fathDecideCd	안면인증최종결과코드
            //fathSkipCd	안면인증생략코드
            //fathResltCd	안면인증결과코드
            //fathResltMsgSbst	안면인증결과메시지내용
        }

        return FormResponse.of(ResponseMessage.NO_DATA, knoteScanInfoResponse);
    }

}
