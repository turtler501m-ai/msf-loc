package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceResDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCrdtCardAthnInDto;
import com.ktmmobile.msf.domains.form.common.service.NiceCertifySvc;
import com.ktmmobile.msf.domains.form.common.service.NiceLogSvc;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.CrdtCardAuthRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.NiceAccountRequest;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import com.ktmmobile.msf.domains.form.system.cert.dto.CertDto;
import com.ktmmobile.msf.domains.form.system.cert.service.CertService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.Arrays;
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
    private final NiceCertifySvc niceCertifySvc;
    private final NiceLogSvc nicelog;
    private final CertService certService;

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
        MspJuoBanInfoResponse data = authInfoReadMapper.verifyBillInfo(request);
        if (data == null) {
            return FormResponse.of(ResponseMessage.VALID_BAN_FAIL);
        }
        return FormResponse.of(ResponseMessage.VALID_BAN_SUCCESS, data);
    }

    //

    /**
     * 신용카드 인증 (X91)
     *
     * @param : 카드번호(crdtCardNo), 카드유효기간(crdtCardTermDay), 카드소유자명(custNm), 카드소유자 생년월일(brthDate), ??(ncType)
     * @return
     */
    public FormResponse<Map<String, Object>> crdtCardAthnInfo(CrdtCardAuthRequest request) {
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

        //고객포탈 기준으로 민감정보 (생년월일, 카드번호) 암호화하지 않고 보냄. 하단의 복호화가 안됨.
        /*try {
            crdtCardNo = EncryptUtil.ace256Dec(crdtCardNo); //카드번호
            brthDate = EncryptUtil.ace256Dec(brthDate); //생년월일
        } catch (CryptoException e) {
            brthDate = "";
        }*/
        //고객포탈은 신용카드번호 인증 시 "최초 요금 납부등록은<br/> 가입자 본인 명의의 카드/계좌로만 가능합니다.<br/>재 확인 후 시도 바랍니다." 라는 메세지가 있음.
        //고객포탈은 신용카드번호 인증 시 서비스 연동이력 저장은 없음. M플랫폼으로 이동 시에는 저장이 있겠으나 고객포탈 자체로 이력저장은 보이지 않음.
        //하지만, 스마트는 서비스 연동이력 및 시스템 로그? 가 저장되어야 할 것 같음.

        //신용카드번호 인증 조회 호출
        MoscCrdtCardAthnInDto moscCrdtCardAthnIn = null;
        try {
            //X91 MP호출
            moscCrdtCardAthnIn = msfMplatFormService.moscCrdtCardAthnInfo(crdtCardNo, crdtCardTermDay, brthDate, custNm);
            if (moscCrdtCardAthnIn.getTrtResult() != null && moscCrdtCardAthnIn.isSuccess()) {
                //if (moscCrdtCardAthnIn.isSuccess()) {
                if ("Y".equals(moscCrdtCardAthnIn.getTrtResult())) {
                    crdtCardKindCd = moscCrdtCardAthnIn.getCrdtCardKindCd();
                    crdtCardKindNm = "";

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
        //rtnMap.put("RESULT_CODE", ResponseMessage.VALID_CREDIT_SUCCESS);
        //rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_CREDIT_SUCCESS.getMessage());
        //rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 성공하였습니다.");

        rtnMap.put("CRDT_CARD_CODE_NM", crdtCardKindNm); //카드사명 넘겨아하나. 확인필요.

        return FormResponse.of(ResponseMessage.VALID_CREDIT_SUCCESS);
        //return FormResponse.of(ResponseMessage.VALID_CREDIT_SUCCESS, rtnMap);
    }

    //계좌번호인증
    public FormResponse<Map<String, Object>> accountCheck(NiceAccountRequest niceAccountRequest, HttpServletRequest request) {
        String result = null;
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

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

        //Request DTO 를 NiceResDto 에서 NiceAccountRequest 로 변경하도록 처리예정 (추후 협의필요)
        NiceResDto niceResDto = new NiceResDto();
        BeanUtils.copyProperties(niceResDto, niceAccountRequest);
        result = niceCertifySvc.checkNiceAccount(niceResDto);

        String[] results = result.split("\\|");

        NiceLogDto nicelogDto = new NiceLogDto();
        nicelogDto.setnReferer(request.getHeader("referer"));
        nicelogDto.setnAuthType("A");

        //if (results != null && results.length > 0 && "0000".equals(results[1])) {
        if (results != null && results.length > 1 && "0000".equals(results[1])) {
            //인증성공
            //나이스 로그 기록 20160403
            nicelogDto.setnResult("O");
            nicelog.insert(request, niceResDto, nicelogDto);
            rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);

            // ============ STEP START ============
            Map<String, String> resultMap = certService.isAuthStepApplyUrl(request);
            if ("Y".equals(resultMap.get("isAuthStep"))) {

                String ncType = "";
                if (request.getParameter("ncType") != null) ncType = request.getParameter("ncType");

                // account인증 이력 존재여부 확인
                if (0 < certService.getModuTypeStepCnt("account", ncType)) {
                    // 계좌인증 관련 스텝 초기화
                    CertDto certDto = new CertDto();
                    certDto.setModuType("account");
                    certDto.setCompType("G");
                    certDto.setNcType(ncType);
                    certService.getCertInfo(certDto);
                }

                // 인증종류, 대리인구분, 계좌번호, 은행코드, 이름
                String[] certKey = {"urlType", "moduType", "ncType", "reqAccountNumber", "reqBank", "name"};
                String[] certValue = {"chkAccount", "account", ncType, EncryptUtil.ace256Enc(niceResDto.getAccountNo()), niceResDto.getBankCode(), niceResDto.getName()};

                // service가 3인 경우 이름 필수x
                if ("3".equals(niceResDto.getService())) {
                    certKey = Arrays.copyOfRange(certKey, 0, 5);
                    certService.vdlCertInfo("C", certKey, certValue);
                } else {
                    certService.vdlCertInfo("C", certKey, certValue);

                    // 인증종류, 대리인구분, 이름
                    certKey = new String[]{"urlType", "moduType", "ncType", "name"};
                    certValue = new String[]{"compAccount", "account", ncType, niceResDto.getName()};

                    Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
                    if (!Constants.AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                        throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
                    }
                }
            }
            // ============ STEP END ============

        } else {
            //인증실패
            //나이스 로그 기록 20160403 나이스 에로코드가있을때만 로그에 넣는다
            nicelogDto.setnResult("X");
            nicelog.insert(request, niceResDto, nicelogDto);
        }

        rtnMap.put("RESULT_CODE", ResponseMessage.VALID_ACCOUNT_SUCCESS);
        rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_ACCOUNT_SUCCESS.getMessage());

        return FormResponse.of(ResponseMessage.VALID_ACCOUNT_SUCCESS);
    }
}
