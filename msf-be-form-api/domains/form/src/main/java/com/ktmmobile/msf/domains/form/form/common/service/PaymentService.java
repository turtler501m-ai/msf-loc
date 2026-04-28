package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.common.code.CustomerType;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscCrdtCardAthnInDto;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.CrdtCardAuthRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
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

    //청구계정아이디 조회
    public MspJuoBanInfoResponse verifyBillInfo(MspJuoBanInfoCondition condition) {
        String cstmrTypeCd = condition.getCstmrTypeCd(); //parameter : "I"(내국인성인, 내국인미성년자, 외국인, 외국인미성년자)
        CustomerType cstmrType = CustomerType.valueOf(cstmrTypeCd); //customerType enum 변환
        condition.setCustomerType(cstmrType.getSubCode());
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

        //입력값 민감정보 암호화처리
        try {
            crdtCardNo = EncryptUtil.ace256Dec(crdtCardNo); //카드번호
            brthDate = EncryptUtil.ace256Dec(brthDate); //생년월일
        } catch (CryptoException e) {
            crdtCardNo = "";
            brthDate = "";
        }

        //신용카드번호 인증 조회 호출
        MoscCrdtCardAthnInDto moscCrdtCardAthnIn = null;
        try {
            //X91 MP호출
            moscCrdtCardAthnIn = msfMplatFormService.moscCrdtCardAthnInfo(crdtCardNo, crdtCardTermDay, brthDate, custNm);
            if (moscCrdtCardAthnIn.isSuccess()) {
                if ("Y".equals(moscCrdtCardAthnIn.getTrtResult())) {
                    crdtCardKindCd = moscCrdtCardAthnIn.getCrdtCardKindCd();
                    crdtCardKindNm = "";

                    //서비스 연동이력 확인하여 저장
                    //
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
                        // 카드 인증 이력 존재여부 확인 >>  인증이력 존재여부 확인하는 이유는 뭘까?
                        /*if (0 < certService.getModuTypeStepCnt("card", ncType)) {
                            // 카드 관련 스텝 초기화
                            CertDto certDto = new CertDto();
                            certDto.setModuType("card");
                            certDto.setCompType("G");
                            certDto.setNcType(ncType);
                            certService.getCertInfo(certDto);
                        }*/

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

        //return rtnMap;

        return null;
    }

    //계좌번호인증
    /*public Map<String, Object> accountCheck(MspJuoSubInfoCondition condition) {
        //JuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(condition);
        return null;
    }*/
}
