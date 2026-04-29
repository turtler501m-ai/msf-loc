package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCrdtCardAthnInDto;
import com.ktmmobile.msf.domains.form.form.common.dto.CrdtCardAuthRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 요금납부방법 인증 서비스
 **/
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AuthInfoReadMapper authInfoReadMapper;
    private final MsfMplatFormService msfMplatFormService;
    private final CommonCodeReader commonCodeReader;

    // @Value("${ext.url}")
    // private String extUrl;
    //
    // @Value("${NICE_UID_PASSWORD}")
    // private String niceUidPassword;

    //청구계정아이디 조회
    public MspJuoBanInfoResponse verifyBillInfo(MspJuoBanInfoRequest condition) {
        //String cstmrTypeCd = condition.getCstmrTypeCd(); //parameter : "I"(내국인성인, 내국인미성년자, 외국인, 외국인미성년자)
        //CustomerType cstmrType; //customerType enum 변환
        //condition.setCustomerType(condition.getCstmrTypeCd().getCode()); //NA, FA
        MspJuoBanInfoResponse data = authInfoReadMapper.verifyBillInfo(condition);
        return data;

        /*String cstmrTypeCd = condition.getCstmrTypeCd(); //parameter : "I"(내국인성인, 내국인미성년자, 외국인, 외국인미성년자)
        CustomerType customerType = CustomerType.valueOf(cstmrTypeCd); //customerType enum 변환
        if (CustomerType.JP.equals(cstmrTypeCd)) {
            cstmrTypeCd = "B"; //법인
        } else if (CustomerType.GO.equals(cstmrTypeCd)) {
            cstmrTypeCd = "G"; //공공기관
        }
        condition.setCustomerType(cstmrTypeCd);
        MspJuoBanInfoResponse data = authInfoReadMapper.verifyBillInfo(condition);
        return data;*/
    }

    //신용카드 인증 (X91)
    public Map<String, Object> crdtCardAthnInfo(CrdtCardAuthRequest request) {
        Map<String, Object> rtnMap = new HashMap<>();
        //parameter
        //crdtCardNo : 카드번호
        //crdtCardTermDay : 카드유효기간
        //custNm : 카드소유자명
        //brthDate : 카드소유자 생년월일
        //ncType :
        String crdtCardNo = request.getCrdtCardNo(); //카드번호 (암호화 필수)
        String crdtCardTermDay = request.getCrdtCardTermYear() + request.getCrdtCardTermMonth(); //카드유효기간
        String custNm = request.getCustNm(); //카드소유주명
        String brthDate = request.getBrthDate(); // //카드소유자 생년월일 (암호화 필수)
        String crdtCardKindCd = ""; //카드사 코드 (리턴으로 받음)
        String crdtCardKindNm = ""; //카드사명 (리턴받은걸로 공통코드에서 찾음)

        System.out.println("crdtCardNo : " + crdtCardNo);
        System.out.println("crdtCardTermDay : " + crdtCardTermDay);
        System.out.println("brthDate : " + brthDate);
        System.out.println("custNm : " + custNm);
        System.out.println("====================================================================");

        //입력값 민감정보 복호화? 암호화는 하지도 않는데?
        /*try {
            crdtCardNo = EncryptUtil.ace256Dec(crdtCardNo); //카드번호
            brthDate = EncryptUtil.ace256Dec(brthDate); //생년월일
        } catch (CryptoException e) {
            System.out.println("CryptoException ======================== " + e.getMessage());
            crdtCardNo = "";
            brthDate = "";
        }*/

        System.out.println("crdtCardNo : " + crdtCardNo);
        System.out.println("crdtCardTermDay : " + crdtCardTermDay);
        System.out.println("brthDate : " + brthDate);
        System.out.println("custNm : " + custNm);

        //신용카드번호 인증 조회 호출
        MoscCrdtCardAthnInDto moscCrdtCardAthnIn = null;
        try {
            //X91 MP호출
            moscCrdtCardAthnIn = msfMplatFormService.moscCrdtCardAthnInfo(crdtCardNo, crdtCardTermDay, brthDate, custNm);
            System.out.println("moscCrdtCardAthnIn.getTrtResult =================================== " + moscCrdtCardAthnIn.getTrtResult());
            if (moscCrdtCardAthnIn.getTrtResult() != null && moscCrdtCardAthnIn.isSuccess()) {
                //if (moscCrdtCardAthnIn.isSuccess()) {
                if ("Y".equals(moscCrdtCardAthnIn.getTrtResult())) {
                    crdtCardKindCd = moscCrdtCardAthnIn.getCrdtCardKindCd();
                    crdtCardKindNm = "";

                    //서비스 연동이력 확인하여 저장
                    String globalNo = "99999999999999999999";
                    String trtMsg = "";
                    //String crdtCardKindCd = "";
                    String crdtCardNm = "";

                    /*rtnMap.put("GLOBAL_NO", moscCrdtCardAthnIn.getGlobalNo());
                    rtnMap.put("TRT_MSG", moscCrdtCardAthnIn.getTrtMsg());
                    rtnMap.put("CRDT_CARD_KIND_CD", crdtCardKindCd);
                    rtnMap.put("CRDT_CARD_NM", moscCrdtCardAthnIn.getCrdtCardNm());*/

                    //카드사목록은 DTL_CD 및 DTL_CD_NM 카드사명, EXPNSN_STR_VAL1 에 카드사코드가 들어감. 그래서 아래와 같은
                    CommonCodesRequest crdRequest = CommonCodesRequest.withFull("CRD"); //조건설정
                    CommonCodeGroups commonCodeCrdGroups = commonCodeReader.getCommonCodes(crdRequest); //M전산,고객포탈,스마트에서 CRD 그룹코드를 모두 조회
                    List<CommonCodeData> crdtCardList = commonCodeCrdGroups.get("CRD"); //실제 CRD 그룹코드를 조회
                    if (crdtCardList != null) {
                        for (CommonCodeData crdtCardInfo : crdtCardList) {
                            if (crdtCardInfo.detail().etcValue1().equals(crdtCardKindCd)) {
                                crdtCardKindNm = crdtCardInfo.code(); // 이상하다... 정말 이상하다..
                                break;
                            }
                        }
                    }
                    System.out.println("crdtCardKindNm >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : " + crdtCardKindNm);

                    //카드사명이 있는 경우에만 로그에 쌓아?
                    if (!"".equals(crdtCardKindNm)) {
                        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 1. 서비스 호출 이력에 저장
                        // 카드 인증 이력 존재여부 확인 >>  인증이력 존재여부 확인하는 이유는 뭘까?
                        /*if (0 < certService.getModuTypeStepCnt("card", ncType)) {
                            // 카드 관련 스텝 초기화
                            CertDto certDto = new CertDto();
                            certDto.setModuType("card");
                            certDto.setCompType("G");
                            certDto.setNcType(ncType);
                            certService.getCertInfo(certDto);
                        }*/

                        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 2. 유효성체크를 해야할듯한데~~~~
                        // 인증종류, 대리인구분, 카드번호, 카드유효기간(년도+월), 카드회사코드, 주민번호, 이름
                        // >> 유효성체크
                        /*String[] certKey = {"urlType", "moduType", "ncType", "reqCardNo", "crdtCardTermDay", "reqCardCompany"
                                , "birthDate", "name"};
                        String[] certValue = {"chkCard", "card", ncType, appformReqDto.getReqCardNo(), crdtCardTermDay, crdtCardKindNm
                                , appformReqDto.getReqCardRrn(), appformReqDto.getReqCardName()};
                        certService.vdlCertInfo("C", certKey, certValue);*/

                        // 인증종류, 대리인구분, 주민번호, 이름
                        // >> 유효성체크
                        /*certKey = new String[] {"urlType", "moduType", "ncType", "birthDate", "name"};
                        certValue = new String[] {"compCard", "card", ncType, appformReqDto.getReqCardRrn(), appformReqDto.getReqCardName()};

                        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
                        if (!Constants.AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                            rtnMap.put("RESULT_CODE", "STEP01");
                            rtnMap.put("ALTER_MSG", vldReslt.get("RESULT_DESC"));
                            return rtnMap;
                        }*/

                        //인증성공 처리
                        /*rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
                        rtnMap.put("CRDT_CARD_CODE_NM", crdtCardKindNm);
                        rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 성공하였습니다.");*/
                    } else {
                        //인증한 카드사코드가 존재하지 않음. 이런 경우 리턴처리가 맞나?
                        /*rtnMap.put("RESULT_CODE", "00003");
                        rtnMap.put("TRT_MSG", "공통코드(CRD) 카드 정보가 없습니다. ");
                        rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 실패하였습니다.<br/>다른 카드로 변경하여 등록 해 주세요. ");*/
                    }
                } else {
                    //신용카드번호 인증조회 시 인증실패가 된 경우
                    String trtMsg = moscCrdtCardAthnIn.getTrtMsg();
                    /*if (trtMsg.contains("주민번호")) {
                        rtnMap.put("RESULT_CODE", "00004");
                        rtnMap.put("GLOBAL_NO", moscCrdtCardAthnIn.getGlobalNo());
                        rtnMap.put("TRT_MSG", trtMsg);
                        rtnMap.put("ALTER_MSG", "최초 요금 납부등록은<br/> 가입자 본인 명의의 카드/계좌로만 가능합니다.<br/>재 확인 후 시도 바랍니다.");
                    } else {
                        rtnMap.put("RESULT_CODE", "00002");
                        rtnMap.put("GLOBAL_NO", moscCrdtCardAthnIn.getGlobalNo());
                        rtnMap.put("TRT_MSG", trtMsg);
                        rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 실패하였습니다.<br/>신용카드 정보 확인 후 다시 입력 해 주세요. ");
                    }*/
                }
            } else {
                /*rtnMap.put("RESULT_CODE", "00001");
                rtnMap.put("GLOBAL_NO", moscCrdtCardAthnIn.getGlobalNo());
                //rtnMap.put("ALTER_MSG", "처리중인 업무가 있습니다. 잠시 후 다시 시도해 주시기 바랍니다..");
                rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 실패하였습니다.<br/>신용카드 정보 확인 후 다시 입력 해 주세요.");*/
            }
        } catch (SocketTimeoutException e) {
            /*rtnMap.put("RESULT_CODE", "99999");
            rtnMap.put("TRT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
            rtnMap.put("ALTER_MSG", "처리중인 업무가 있습니다. 잠시 후 다시 시도해 주시기 바랍니다.");*/
        }

        //@@ prx 오픈전까지 강제 성공처리
        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        rtnMap.put("CRDT_CARD_CODE_NM", crdtCardKindNm);
        rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 성공하였습니다.");

        return rtnMap;
    }

    //계좌번호인증
    /*public Map<String, Object> accountCheck(MspJuoSubInfoCondition condition) {
        //JuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(condition);

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        Random numGen = null;
        try {
            numGen = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new McpCommonException("NoSuchAlgorithmException");
        }

        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
        String strOrderNo = sdf.format(new java.util.Date()) + (Math.round(numGen.nextDouble() * 10000000000L) + ""); //주문번호 : 매 요청마다 중복되지 않도록 유의

        CommonHttpClient client = new CommonHttpClient(extUrl + "/rlnmCheck.do");
        NameValuePair[] data = {
            new NameValuePair("niceUid", Constants.NICE_UID),
            new NameValuePair("svcPwd", niceUidPassword),
            new NameValuePair("service", "3"),  //서비스구분 1=계좌소유주확인 2=계좌성명확인 3=계좌유효성확인
            new NameValuePair("strGbn", "1"),  // 1 : 개인, 2: 사업자
            //new NameValuePair("strResId", condition.getResId()), //주민번호(사업자 번호,법인번호)
            //new NameValuePair("strNm", StringUtil.substringByBytes(niceResDto.getName(), 0, 60)), //계좌소유주명
            //new NameValuePair("strBankCode", niceResDto.getBankCode()), //은행코드(전문참조)
            //new NameValuePair("strAccountNo", niceResDto.getAccountNo()), //계좌번호
            //new NameValuePair("svcGbn", niceResDto.getSvcGbn()),   //업무구분(전문참조)
            //new NameValuePair("strOrderNo", strOrderNo),
            //new NameValuePair("svc_cls", niceResDto.getSvcCls()),  //내-외국인구분
            new NameValuePair("inq_rsn", "10") // 조회사유 - 10:회원가입 20:기존회원가입 30:성인인증 40:비회원확인 90:기타사유
        };
        try {
            result = client.postUtf8(data);
            //result = client.post(data, "UTF-8");
        } catch (SocketTimeoutException e) {
            throw new McpCommonException("SocketTimeoutException");
        }

        //data:"service=3&svcGbn=4&strBankCode=" + $('#reqBankTmp').val() + "&strAccountNo=" +  $('#reqAccountNumberTmp').val() + "&USERNM=" + $('#reqAccountNameTmp').val(),

       *//*
        * 확인용도 주석 처리
        private String service   ; //서비스구분 1=계좌소유주확인 2=계좌성명확인 3=계좌유효성확인
        private String resId     ; //주민번호(사업자 번호,법인번호)
        private String bankCode  ; //은행코드(전문참조)
        private String accountNo ; //계좌번호
        private String svcGbn    ; //업무구분(전문참조)
        private String svcCls    ; //내-외국인구분

        service 서비스구분 1: 소유주 확인, 2: 예금주명 확인, 3: 계좌 유효성 확인
        svcGbn 업무구분 5: 소유주 확인, 2: 예금주명 확인, 4: 계좌 유효성 확인
        *//*

        String[] results = result.split("\\|");

        // NiceLogDto nicelogDto = new NiceLogDto();
        // nicelogDto.setnReferer(request.getHeader("referer"));
        // nicelogDto.setnAuthType("A");
        //
        // if (results != null && results.length > 0 && "0000".equals(results[1])) {
        //     //인증성공
        //     //나이스 로그 기록
        //     nicelogDto.setnResult("O");
        //     newChangeService.insertNiceLog(request, niceResDto, nicelogDto);
        //     rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        //
        // } else {
        //     //인증실패
        //     //나이스 로그 기록 : 나이스 에로코드가있을때만 로그에 넣는다
        //     nicelogDto.setnResult("X");
        //     newChangeService.insertNiceLog(request, niceResDto, nicelogDto);
        //     rtnMap.put("RESULT_CODE", "0001");
        // }

        return null;
    }*/
}
