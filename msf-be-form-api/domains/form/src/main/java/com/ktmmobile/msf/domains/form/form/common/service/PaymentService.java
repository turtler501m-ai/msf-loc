package com.ktmmobile.msf.domains.form.form.common.service;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiAccountCheckRequest;
import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiAccountCheckResponse;
import com.ktmmobile.msf.domains.externalclient.nice.application.port.out.NiceApiClient;
import com.ktmmobile.msf.domains.form.common.code.CstmrType;
import com.ktmmobile.msf.domains.form.common.code.OperType;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.CrdtCardAuthRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.NiceAccountRequest;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SubscriptionRequest;
import com.ktmmobile.msf.domains.form.form.newchange.service.NewChangeValidCheckService;

/**
 * 요금납부방법 인증 서비스
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final MspPrxClient mspPrxClient;
    private final AuthInfoReadMapper authInfoReadMapper;
    private final NewChangeValidCheckService newChangeValidCheckService;
    private final NiceApiClient niceApiClient;

    // @Value("${ext.url}")
    // private String extUrl;
    //
    // @Value("${NICE_UID_PASSWORD}")
    // private String niceUidPassword;


    /**
     * 청구계정아이디 조회
     *
     * @param : 고객유형(cstmrTypeCd), 고객명(customerLinkName), 고객식별번호(customerSsn), 청구번호(ban)
     * @return : MspJuoBanInfoResponse : 납부방법(blBillingMethod), 고객아이디(customerId), 계약번호(contractNum), 청구번호(ban)
     */
    public FormResponse<MspJuoBanInfoResponse> verifyBillInfo(MspJuoBanInfoRequest request) {

        log.debug("getCustomerType: {}, getCustomerSsn: {}, getCustomerLinkName: {}, getBan: {}",
            request.getCustomerType(),
            request.getCustomerSsn(),
            request.getCustomerLinkName(),
            request.getBan());
        //고객식별번호(CustomerSsn) : 내국인은 내국인식별번호, 외국인은 외국인식별번호, 법인 및 공공기관은 법인번호로 전달됨.
        //CUSTOMER_SSN : 암호화값으로 비교하고 , DRIVR_LICNS_NO 또는 TAX_ID : 암호화되지 않은 값
        //입력값 유효성체크 (필수값 : 고객유형, 고객명, 고객식별번호, 청구번호)
        if (!StringUtils.hasText(request.getCustomerSsn()) || !StringUtils.hasText(request.getCustomerLinkName()) || !StringUtils.hasText(request.getBan())) {
            return FormResponse.of(ResponseMessage.VALID_BAN_NEED_MORE_INPUT);
        }

        //청구계정아이디 조회
        MspJuoBanInfoResponse data = authInfoReadMapper.verifyBillInfo(request);
        if (data == null) {
            return FormResponse.of(ResponseMessage.VALID_BAN_FAIL);
        }
        return FormResponse.of(ResponseMessage.VALID_BAN_SUCCESS, data);
    }

    //

    /**
     * 신용카드 인증 (X91)
     */
    @SuppressWarnings("PMD.EmptyControlStatement")
    public FormResponse<Map<String, Object>> crdtCardAthnInfo(CrdtCardAuthRequest request) {
        Map<String, Object> rtnMap = new HashMap<>();

        //parameter
        //crdtCardNo : 카드번호
        //crdtCardTermDay : 카드유효기간
        //custNm : 카드소유자명
        //brthDate : 카드소유자 생년월일
        //ncType :
        //othersPaymentYn : 타인납부로 요청 : Y(타인납부)  N(본인납부)
        //customerSsn : 개통이력확인을 위한 고객 식별번호

        //MSF_REQUEST_BILL_REQ
        //REQ_CARD_NM	신용카드명의자명
        //REQ_CARD_RRN	신청정보신용카드명의자주민번호
        //REQ_CARD_COMPANY_CD	신청정보신용카드카드사코드
        //REQ_CARD_NO	신용카드번호
        //REQ_CARD_YY	신청정보신용카드유효년
        //REQ_CARD_MM	신청정보신용카드유효월

        //parameter
        //operTypeCd : 가입유형
        //cstmrTypeCd : 고객유형
        //CSTMR_NATIVE_RRN (내국인) , CSTMR_FOREIGNER_RRN (외국인) // 법인은 법인등록번호 또는 사업자번호, 공공기관은 기관코드
        //othersPaymentYn : 타인납부여부
        //reqCardNm
        //reqCardRrn
        //reqCardCompanyCd
        //reqCardNo
        //reqCardYy
        //reqCardMm

        //타인납부요청일 경우 고객의 식별번호로 개통이력확인
        //고객포탈은 신용카드번호 인증 시 "최초 요금 납부등록은<br/> 가입자 본인 명의의 카드/계좌로만 가능합니다.<br/>재 확인 후 시도 바랍니다." 라는 메세지가 있음.
        //고객포탈은 신용카드번호 인증 시 서비스 연동이력 저장은 없음. M플랫폼으로 이동 시에는 저장이 있겠으나 고객포탈 자체로 이력저장은 보이지 않음.
        //하지만, 스마트는 서비스 연동이력 및 시스템 로그? 가 저장되어야 할 것 같음.
        String cstmrTypeCd = request.getCstmrTypeCd();
        String operTypeCd = request.getOperTypeCd(); //가입유형 (타인납부요청의 개통이력 확인은 신규가입 및 번호이동만 해당됨)
        String othersPaymentYn = request.getOthersPaymentYn();
        String customerSsn = request.getCustomerSsn();
        String totalOpenYn = "N"; //개통이력여부

        log.debug("신용카드 유효성체크 >> cstmrTypeCd: {}, operTypeCd: {}, othersPaymentYn: {}, customerSsn: {}",
            cstmrTypeCd,
            operTypeCd,
            othersPaymentYn,
            customerSsn);

        //유효성체크 (내국인 성인 및 외국인 성인은 개통이력조회를 위해 주민번호 또는 외국인등록번호 확인
        if ("Y".equals(othersPaymentYn) && (cstmrTypeCd.equals(CstmrType.NATIVE_ADULT.getCode()) || cstmrTypeCd.equals(CstmrType.FOREIGN_ADULT.getCode()))
            && (customerSsn == null || "".equals(customerSsn))) { //타인납부
            return FormResponse.of(ResponseMessage.VALID_CREDIT_NEED_MORE_INPUT, rtnMap);
        }

        //신용카드는 실제 서비스에서는 미성년자는 불가이므로 주석 2026.07.14
        //본인납부 신용카드는 미성년자는 불가처리
        //if ("N".equals(othersPaymentYn) && (cstmrTypeCd.equals(CstmrType.NATIVE_MINOR.getCode()) || cstmrTypeCd.equals(CstmrType.FOREIGN_MINOR.getCode()))) { //본인납부
        //    return FormResponse.of(ResponseMessage.VALID_CREDIT_CANNOT_CHECK, rtnMap);
        //}

        //타인납부 AND (신규가입 , 번호이동) AND (내국인성인 , 외국인성인)
        if ("Y".equals(othersPaymentYn) && (OperType.NEW_ACTIVATION.getCode().equals(operTypeCd) || OperType.MOBILE_NUMBER_PORTABILITY.getCode()
            .equals(operTypeCd)) && (CstmrType.NATIVE_ADULT.getCode()
            .equals(cstmrTypeCd) || CstmrType.FOREIGN_ADULT.getCode().equals(cstmrTypeCd))) {

            //타인납부 신용카드는 내국인 성인과 외국인 성인일 때만 개통이력존재 여부 확인
            SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
            subscriptionRequest.setCustomerSsn(customerSsn);
            totalOpenYn = newChangeValidCheckService.getOpenHistoryYn(subscriptionRequest);

            if ("N".equals(totalOpenYn)) { //개통이력존재하지 않을경우에 진행불가
                return FormResponse.of(ResponseMessage.VALID_CREDIT_FAIL_OTHERS);
            }
        }

        log.debug(
            "★ 신용카드번호 유효성체크 (prx-X91) ★ crdtCardNo: {}, crdtCardTermYear: {}, crdtCardTermMonth: {}, custNm: {}, brthDate: {}, othersPaymentYn: {}",
            request.getCrdtCardNo(),
            request.getCrdtCardTermYear(),
            request.getCrdtCardTermMonth(),
            request.getCustNm(),
            request.getBrthDate(),
            request.getOthersPaymentYn());

        //여기는 나중에 주석 풀어야하는 사항 아님.
        String crdtCardNo = request.getCrdtCardNo(); //카드번호 (암호화 필수)
        String crdtCardTermDay = "20" + request.getCrdtCardTermYear() + request.getCrdtCardTermMonth(); //카드유효기간
        String custNm = request.getCustNm(); //카드소유주명
        String brthDate = request.getBrthDate(); // //카드소유자 생년월일 (암호화 필수)

        //입력값 유효성 체크
        if (!StringUtils.hasText(crdtCardNo) || !StringUtils.hasText(request.getCrdtCardTermYear()) || !StringUtils.hasText(request.getCrdtCardTermMonth()) || !StringUtils.hasText(
            custNm) || !StringUtils.hasText(brthDate)) {
            return FormResponse.of(ResponseMessage.VALID_CREDIT_NEED_MORE_INPUT, rtnMap);
        }

        //PRX 호출결과
        String globalNo = ""; //PRX 연동 결과 - globalNo
        String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
        String responseCode = ""; //PRX 연동 결과 - responseCode
        String responseBasic = ""; //

        String trtResult = "";
        String trtMsg = "";
        String crdtCardKindCd = ""; //카드사 코드 (리턴으로 받음)
        String crdtCardKindNm = ""; //카드사명 (리턴받은걸로 공통코드에서 찾음)

        Map<String, String> params = new HashMap<>();
        params.put("appEventCd", "X91");
        params.put("crdtCardTermDay", crdtCardTermDay);
        params.put("crdtCardNo", crdtCardNo); //암호화 적용할 경우 responseType E (ITL_COM_E0003) 발생. 암호화한 값이 허용되지 않는 값이라고 리턴해줌
        params.put("brthDate", brthDate); //암호화 적용할 경우 responseType E (ITL_COM_E0003) 발생. 암호화한 값이 허용되지 않는 값이라고 리턴해줌
        params.put("custNm", custNm); //암호화 여부가 적용이 되는건지 확실치 않음. 암호화하든 안하든 동일한 결과를 줌.
        //responseType N , trtResult N 으로 "카드 발급 미등록" 이라고 메세지를 리턴해줌

        //여기는 나중에 주석 풀어야하는 사항 아님.
        //params.put("crdtCardNo", KisaSeedUtils.encrypt(request.getCrdtCardNo()));
        //params.put("custNm", KisaSeedUtils.encrypt(request.getCustNm()));
        //params.put("brthDate", KisaSeedUtils.encrypt(request.getBrthDate()));

        MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder().parameters(params).build();
        MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callService(mspPrxFormRequest);

        if (mspPrxSoapResponse == null) { //결과 없는 경우
            return null;
        } else { //결과 있는 경우
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode();
            responseBasic = mspPrxSoapResponse.responseBasic();

            trtResult = mspPrxSoapResponse.payloadValue("outDto", "trtResult").map(Object::toString).orElse("");
            trtMsg = mspPrxSoapResponse.payloadValue("outDto", "trtMsg").map(Object::toString).orElse("");
            crdtCardKindCd = mspPrxSoapResponse.payloadValue("outDto", "crdtCardKindCd").map(Object::toString).orElse("");
            crdtCardKindNm = mspPrxSoapResponse.payloadValue("outDto", "crdtCardNm").map(Object::toString).orElse("");

            log.debug(
                "★ 신용카드 유효성 검증 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}, trtResult: {}, trtMsg: {}, crdtCardKindCd: {}, crdtCardKindNm: {}",
                responseType,
                responseCode,
                responseBasic,
                globalNo,
                trtResult,
                trtMsg,
                crdtCardKindCd,
                crdtCardKindNm);

            // trtResult 가 Y 가 아닐 경우 실패 처리
            if (!"Y".equals(trtResult)) {
                return FormResponse.of(ResponseMessage.VALID_CREDIT_FAIL);
            }

            //신용카드코드로 신용카드명 매칭정보 조회하기
            //CommonCodesRequest commonCodesRequest = CommonCodesRequest.withIncludeAll("CRD"); //조건설정
            //CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(commonCodesRequest); // 공통코드 조회 요청
            //List<CommonCodeData> crdCodesList = commonCodeGroups.getSingleGroup();
            //String crdtCardKindCode = crdtCardKindCd.toString();
            //crdtCardKindCd = crdCodesList
            //    .stream()
            //    .filter(crdCodes -> crdtCardKindCode.contains(crdCodes.detail().etcValue1())).toString();
            //log.debug("crdtCardKindCode: {}", crdtCardKindCode);

            //Optional<CommonCodeData> crdtCardKindObj = CommonCodeData.get(crdCodesList, crdtCardKindCode); // 특정 공통코드 그룹의 특정 DTL_CD 내용 가져오기

            rtnMap.put("crdtCardKindCd", crdtCardKindCd); //카드사코드
            //rtnMap.put("crdtCardKindNm", crdtCardKindNm); //카드사명 - 저장하는 부분 없어 주석처리함.
            rtnMap.put("trtMsg", trtMsg);

            return FormResponse.of(ResponseMessage.VALID_CREDIT_SUCCESS, rtnMap);
        }
    }

    /**
     * 계좌번호 인증
     */
    @SuppressWarnings("PMD.EmptyControlStatement")
    public FormResponse<Map<String, Object>> accountCheck(NiceAccountRequest niceAccountRequest) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //참고
        //단말할부기간이 일시납일 경우에는 계좌번호 인증은 보이지 않도록 프론트에서 막아둠
        //현재는 그와 관련한 유효성체크를 서버에서 처리하는 사항은 없음.

        /* 확인용도 주석 처리 */
        //strGbn                   : 1:개인, 2:사업자
        //private String svcGbn    ; //업무구분(전문참조) >> 5: 소유주 확인, 2: 예금주명 확인, 4: 계좌 유효성 확인
        //private String service   ; //서비스구분 >> 1: 소유주 확인, 2: 예금주명 확인, 3: 계좌 유효성 확인
        //private String svcCls    ; //내-외국인구분 ???
        //name                     : 계좌소유주명
        //private String resId     ; //주민번호(사업자 번호,법인번호)
        //private String bankCode  ; //은행코드(전문참조)
        //private String accountNo ; //계좌번호
        //inqRsn                   : 조회사유 - 10:회원가입 20:기존회원가입 30:성인인증 40:비회원확인 90:기타사유

        String operTypeCd = niceAccountRequest.getOperTypeCd(); //가입유형 구분 (NAC3, MNP3, HDN3)
        String cstmrTypeCd = niceAccountRequest.getCstmrTypeCd(); //고객구분
        //String cstmrNm = niceAccountRequest.getCstmrNm(); //가입하려고 하는 고객명
        //String customerSsn = niceAccountRequest.getCustomerSsn(); //가입하려고 하는 고객식별번호 - 개통이력확인을 위한 고객 식별번호 //CSTMR_NATIVE_RRN, CSTMR_FOREIGNER_RRN
        String reqBankCd = niceAccountRequest.getReqBankCd(); //신청정보계좌이체은행코드
        String reqAccountNo = niceAccountRequest.getReqAccountNo(); //계좌번호
        String reqAccountNm = niceAccountRequest.getReqAccountNm(); //계좌예금주명
        String reqAccountRrn = niceAccountRequest.getReqAccountRrn(); //신청정보계좌이체예금주주민번호
        String othersPaymentYn = niceAccountRequest.getOthersPaymentYn(); //타인납부여부 Y(타인납부)  N(본인납부)
        String totalOpenYn = "N"; //개통이력여부
        String customerSsn = niceAccountRequest.getCustomerSsn(); //타인납부여부 Y(타인납부)  N(본인납부)

        //NICE 연동 parameter
        String service = "2";
        String customerType = "1"; // 고객구분: 1-개인, 2-사업자
        String identityNumber = ""; // 주민번호/사업자번호/법인번호. 개인은 생년월일 6자리
        String name = ""; // 계좌주명
        String bankCode = ""; // 은행코드 (NICE 전문 기준)
        String accountNo = ""; // 계좌번호. 하이픈(-) 제거 후 전달
        String serviceType = "4"; // 업무구분: 5-계좌소유주확인, 2-계좌성명확인, 4-계좌유효성확인
        String orderNo = ""; // 주문번호. 미입력 시 빌더에서 생성
        String serviceClass = "1"; // 내/외국인구분
        String inquiryReason = "90"; // 조회사유: 10-회원가입, 20-기존회원확인, 30-성인인증, 40-비회원확인, 90-기타

        if (cstmrTypeCd.equals(CstmrType.JURIDICAL_PERSON.getCode()) || cstmrTypeCd.equals(CstmrType.GOVERNMENT_ORGANIZATION.getCode())) {
            customerType = "2"; //고객 구분
            identityNumber = reqAccountRrn; // 주민번호/사업자번호/법인번호. 개인은 생년월일 6자리
        } else {
            identityNumber = reqAccountRrn.substring(0, 6); // 주민번호/사업자번호/법인번호. 개인은 생년월일 6자리
            if (cstmrTypeCd.equals(CstmrType.FOREIGN_ADULT.getCode()) || cstmrTypeCd.equals(CstmrType.FOREIGN_MINOR.getCode())) {
                serviceClass = "2"; //내외국인 구분
            }
        }
        bankCode = reqBankCd; // 은행코드 (NICE 전문 기준)
        accountNo = reqAccountNo; // 계좌번호. 하이픈(-) 제거 후 전달
        name = reqAccountNm; // 계좌주명

        log.debug(
            "operTypeCd: {}, cstmrTypeCd: {}, service: {}, customerType: {}, identityNumber: {}, name: {}, bankCode: {}, accountNo: {}, serviceType: {}, orderNo: {}, serviceClass: {}, inquiryReason: {}",
            operTypeCd,
            cstmrTypeCd,
            service,
            customerType,
            identityNumber,
            name,
            bankCode,
            accountNo,
            serviceType,
            orderNo,
            serviceClass,
            inquiryReason);

        //유효성체크 (내국인 성인 및 외국인 성인은 개통이력조회를 위해 주민번호 또는 외국인등록번호 확인) - 2026.07.14
        if ("Y".equals(othersPaymentYn) && (cstmrTypeCd.equals(CstmrType.NATIVE_ADULT.getCode()) || cstmrTypeCd.equals(CstmrType.FOREIGN_ADULT.getCode()))
            && (customerSsn == null || "".equals(customerSsn))) { //타인납부
            return FormResponse.of(ResponseMessage.VALID_ACCOUNT_NEED_MORE_INPUT, rtnMap);
        }

        //타인납부 AND (신규가입 OR 번호이동) AND (내국인성인 OR 외국인성인) - 2026.07.14
        if ("Y".equals(othersPaymentYn) && (OperType.NEW_ACTIVATION.getCode().equals(operTypeCd) || OperType.MOBILE_NUMBER_PORTABILITY.getCode()
            .equals(operTypeCd)) && (CstmrType.NATIVE_ADULT.getCode()
            .equals(cstmrTypeCd) || CstmrType.FOREIGN_ADULT.getCode().equals(cstmrTypeCd))) {

            SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
            subscriptionRequest.setCustomerSsn(niceAccountRequest.getCustomerSsn());
            totalOpenYn = newChangeValidCheckService.getOpenHistoryYn(subscriptionRequest);

            if ("N".equals(totalOpenYn)) { //개통이력존재하지 않을경우에 진행불가
                return FormResponse.of(ResponseMessage.VALID_ACCOUNT_FAIL_OTHERS);
            }
        }

        //입력값 확인
        //if (!StringUtils.hasText(operTypeCd) || !StringUtils.hasText(cstmrTypeCd) || !StringUtils.hasText(service) || !StringUtils.hasText(serviceType)
        //    || !StringUtils.hasText(customerType) || !StringUtils.hasText(identityNumber) || !StringUtils.hasText(name) || !StringUtils.hasText(
        //    bankCode) || !StringUtils.hasText(accountNo)
        //    || !StringUtils.hasText(serviceClass) || !StringUtils.hasText(inquiryReason)) {
        //    return FormResponse.of(ResponseMessage.VALID_ACCOUNT_NEED_MORE_INPUT, rtnMap);
        //}

        //타인납부요청일 경우 고객의 식별번호로 개통이력확인
        //if ("Y".equals(othersPaymentYn) && (OperType.NEW_ACTIVATION.getCode().equals(operTypeCd) || OperType.MOBILE_NUMBER_PORTABILITY.getCode()
        //    .equals(operTypeCd))) {
        //    //내국인성인,내국인미성년자, 외국인성인,외국인미성년자 유효성체크
        //    if ((customerSsn == null || "".equals(
        //        customerSsn)) && !cstmrTypeCd.equals(CstmrType.JURIDICAL_PERSON.getCode()) && !cstmrTypeCd.equals(CstmrType.GOVERNMENT_ORGANIZATION.getCode())) {
        //        return FormResponse.of(ResponseMessage.VALID_ACCOUNT_NEED_MORE_INPUT, rtnMap);
        //    }
        //
        //    //개통이력조회 (법인과 공공기관 제외처리)
        //    if (!cstmrTypeCd.equals(CstmrType.JURIDICAL_PERSON.getCode()) && !cstmrTypeCd.equals(CstmrType.GOVERNMENT_ORGANIZATION.getCode())) {
        //        SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
        //        subscriptionRequest.setCustomerSsn(niceAccountRequest.getCustomerSsn());
        //        totalOpenYn = newChangeValidCheckService.getOpenHistoryYn(subscriptionRequest);
        //
        //        if ("N".equals(totalOpenYn)) { //개통이력존재하지 않을경우에 진행불가
        //            return FormResponse.of(ResponseMessage.VALID_ACCOUNT_FAIL_OTHERS);
        //        }
        //    }
        //}

        try {
            NiceApiAccountCheckRequest niceApiAccountCheckRequest = new NiceApiAccountCheckRequest(
                service
                , customerType
                , identityNumber
                , name
                , bankCode
                , accountNo
                , serviceType
                , orderNo
                , serviceClass
                , inquiryReason
            );

            NiceApiAccountCheckResponse response = niceApiClient.checkAccount(niceApiAccountCheckRequest);
            log.debug("response.orderNo: {}, response.resultCode: {}, response.resultMessage: {}, response.success: {}"
                , response.orderNo(), response.resultCode(), response.resultMessage(), response.success());

            boolean niceSuccess = response.success();
            String niceResultCode = response.resultCode();
            String niceResultMessage = response.resultMessage();
            //String niceOrderNo = response.orderNo();

            rtnMap.put("RESULT_CODE", niceResultCode);
            rtnMap.put("RESULT_MESSAGE", niceResultMessage);

            if (niceSuccess && "0000".equals(niceResultCode)) { //성공
                return FormResponse.of(ResponseMessage.VALID_ACCOUNT_SUCCESS);
            }
        } catch (Exception e) {
            throw new SimpleDomainException("accountCheck() 오류 발생", e);
        }

        return FormResponse.of(ResponseMessage.VALID_ACCOUNT_FAIL);
        //rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_ACCOUNT_SUCCESS.getMessage());
    }
}
