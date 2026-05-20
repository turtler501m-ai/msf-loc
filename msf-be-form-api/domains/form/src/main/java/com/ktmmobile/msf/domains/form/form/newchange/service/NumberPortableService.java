package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.form.common.dto.MnpOsstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MnpOsstResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class NumberPortableService {

    private final FormCommService formCommService;
    private final NewChangeReadMapper newChangeReadMapper;
    private final McpRequestReadMapper mcpRequestReadMapper;
    private final IpStatisticService ipstatisticService;

    //private IpstatisticService ipstatisticService;

    /**
     * 번호이동 사전동의 요청 : NP1
     **/
    //        npTlphNo: $.trim($("#moveMobile").val())
    //        ,bchngNpCommCmpnCd: mpCode
    //        ,slsCmpnCd: $("#cntpntShopId").val()
    //        ,custIdntNoIndCd: "01"
    //        ,custIdntNo: custIdntNo
    //        ,custNm: $.trim($("#cstmrName").val())
    //        ,custTypeCd: cstmrType
    public FormResponse<MnpOsstResponse> requestNpPreCheck(MnpOsstRequest request) {
        log.debug(
            "★ 번호이동 사전동의 요청 ★ npTlphNo: {}, bchngNpCommCmpnCd: {}, slsCmpnCd: {}, custTypeCd: {}, indvBizrYn: {}, custIdntNoIndCd: {}, custIdntNo: {}, crprNo: {}, custNm: {}, fornBrthDate: {}",
            request.getNpTlphNo(),
            request.getBchngNpCommCmpnCd(),
            request.getSlsCmpnCd(),
            request.getCustTypeCd(),
            request.getIndvBizrYn(),
            request.getCustIdntNoIndCd(),
            request.getCustIdntNo(),
            request.getCrprNo(),
            request.getCustNm(),
            request.getFornBrthDate());

        MnpOsstResponse responseDto = new MnpOsstResponse();
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //parameter ( MnpOsstRequest )
        //requestKey            : 신청서 일련번호
        //NpTlphNo              : [암호화] 번호이동 전화번호 >> 01098761234
        //BchngNpCommCmpnCd     : 변경전번호이동사업자코드 ( NP_COMM_CMPN_CD ) >> SKT
        //CustTypeCd            : 고객유형코드 ( CUST_TYPE_CD )
        //custIdntNoIndCd	    : 고객식별번호구분코드 ( CUST_IDNT_NO_IND_CD ) :: RCP2006 >> 01
        //crprNo	            : 법인번호
        //custNm	            : [암호화] 고객명 >> 홍길동
        //custIdntNo	        : [암호화] 고객식별번호 >> 6601011234567
        //indvBizrYn	        : 개인사업자 여부 ( Y / N ) >> 기본값 N
        //slsCmpnCd             : 판매회사코드 >> INL :: 고정같음

        //RCP2006	01	주민등록증
        //RCP2006	02	운전면허증
        //RCP2006	03	장애인등록증
        //RCP2006	04	국가유공자증
        //RCP2006	05	여권(외국인)
        //RCP2006	06	외국인등록증
        //RCP2006	07	국내거소신고증

        //0. 입력 데이타 검증


        //1. 번호이동 테이블 내에 특정기간 존재여부 확인
        //   요청한 핸드폰번호(moveMobileNo)로 특정기간(limitDay) 내에 MCP_REQUEST , MCP_REQUEST_MOVE 테이블에 존재여부 확인
        //   신청기간 내에 없을 경우 개통전 사전체크 (PC0)는 확인하지 않고 번호이동 사전체크 요청을 진행함.
        //2. 번호이동 사전체크 요청 여부 확인
        //   요청한 핸드폰번호(moveMobileNo)로 특정기간(limitDay) 내에 MCP_REQUEST_OSST 테이블에 존재여부 확인
        //   MCP_REQUEST_OSST.MVNO_ORD_NO 확인
        Map<String, Object> chkMap = this.mnpPreCheckLimit(request.getNpTlphNo());
        if (!Constants.AJAX_SUCCESS.equals(chkMap.get("RESULT_CODE"))) {
            //return chkMap;
            return FormResponse.of(ResponseMessage.VALID_REQ_NP_PRECHECK_SUCCESS, responseDto);
        }

        //3. 번호이동 사전동의 요청 (NP1)
        MSimpleOsstXmlVO simpleOsstXmlVO = null;
        try {
            simpleOsstXmlVO = formCommService.sendOsstService(request, Constants.EVENT_CODE_NP_PRE_CHECK);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
                rtnMap.put("GLOBAL_NO", simpleOsstXmlVO.getGlobalNo());

            } else { //서비스 호출 실패
                //2.세션 CERT_SEQ 가져오기
                /*long crtSeq = SessionUtils.getCertSession();
                //이력 정보 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
                //mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
                mcpIpStatisticDto.setTrtmRsltSmst(crtSeq + "");
                mcpIpStatisticDto.setPrcsSbst("Exception[simpleOsstXmlVO.isNotSuccess] ");
                mcpIpStatisticDto.setParameter("NpTlphN[" + request.getNpTlphNo() + "],moveCompany[" + request.getBchngNpCommCmpnCd() + "],cstmrType[" + request.getCustTypeCd() + "]");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);*/

                //서비스 호출하면 주석 풀것!!!!
                /*rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.[1]");*/
            }
        } catch (McpMplatFormException e) {
            //이력 정보 저장 처리
            //2.세션 CERT_SEQ 가져오기
            /*long crtSeq = SessionUtils.getCertSession();
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
            //mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setTrtmRsltSmst(crtSeq + "");
            mcpIpStatisticDto.setPrcsSbst("Exception[McpMplatFormException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + request.getNpTlphNo() + "],moveCompany[" + request.getBchngNpCommCmpnCd() + "],cstmrType[" + request.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);*/

            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.[2]");
        } catch (SocketTimeoutException e) {
            //이력 정보 저장 처리
            /*long crtSeq = SessionUtils.getCertSession();
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
            //mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setTrtmRsltSmst(crtSeq + "");
            mcpIpStatisticDto.setPrcsSbst("Exception[SocketTimeoutException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + request.getNpTlphNo() + "],moveCompany[" + request.getBchngNpCommCmpnCd() + "],cstmrType[" + request.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);*/

            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.[3]");
            return FormResponse.of(ResponseMessage.VALID_REQ_NP_PRECHECK_FAIL, responseDto);
            //return rtnMap;

        } catch (SelfServiceException e) {

            //메세지에 따른 resultCode 변경 처리
            String resultCode = e.getResultCode();
            String message = e.getMessageNe();

            rtnMap.put("GLOBAL_NO", e.getGlobalNo());

            if ("ITL_SST_E1014".equals(resultCode)) {
                //성공처리
                rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
                //return rtnMap;
                return FormResponse.of(ResponseMessage.VALID_REQ_NP_PRECHECK_SUCCESS, responseDto);
            } else {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG", e.getMessage());
            }

            //session에 저장한 서식지 정보 초기화
            //            SessionUtils.saveAppformDto(null);
            //            if ("ITL_SST_E1018".equals(resultCode) && message.indexOf("가입제한자") > -1) {
            //                resultCode = "ITL_SST_E1018_01";
            //            } else if ("ITL_SST_E1018".equals(resultCode) && message.indexOf("미납고객") > -1) {
            //                resultCode = "ITL_SST_E1018_02";
            //            }

            //rtnMap.put("OSST_RESULT_CODE", resultCode);
            //rtnMap.put("ERROR_NE_MSG", message);
            //return rtnMap;

            responseDto.setOsstResultCode(resultCode);
            return FormResponse.of(ResponseMessage.VALID_REQ_NP_PRECHECK_SUCCESS, responseDto);
        }

        //@@삭제필요@@ prx 오픈전까지 무조건 정상
        //rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        //rtnMap.put("GLOBAL_NO", simpleOsstXmlVO.getGlobalNo());
        //return rtnMap;

        responseDto.setGlobalNo("123456789");
        return FormResponse.of(ResponseMessage.VALID_REQ_NP_PRECHECK_SUCCESS, responseDto);
    }

    /**
     * 번호이동 사전동의 결과조회 : NP3
     **/
    //        npTlphNo: $.trim($("#moveMobile").val())
    //        ,bchngNpCommCmpnCd: mpCode
    //        ,slsCmpnCd: $("#cntpntShopId").val()
    //        ,custIdntNoIndCd: "01"
    //        ,custIdntNo: custIdntNo
    //        ,custNm: $.trim($("#cstmrName").val())
    //        ,custTypeCd: cstmrType
    public FormResponse<MnpOsstResponse> requestNpAgree(MnpOsstRequest osstReqDto) {
        log.debug("★ 번호이동 사전동의 결과조회 ★ npTlphNo: {}, bchngNpCommCmpnCd: {}", osstReqDto.getNpTlphNo(), osstReqDto.getBchngNpCommCmpnCd());

        MnpOsstResponse responseDto = new MnpOsstResponse();
        if ("01098761234".equals(osstReqDto.getNpTlphNo())) {
            return FormResponse.of(ResponseMessage.VALID_REQ_NP_AGREE_FAIL, responseDto); //실패
        }

        return FormResponse.of(ResponseMessage.VALID_REQ_NP_AGREE_SUCCESS, responseDto); //성공

        //번호이동 사전동의 결과조회 리턴값이 S 가 아니면 번호이동 사전동의 실패 화면이 뜬다.

        //HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //1-3. 본인인증 확인
        // >> 스마트는 신분증 확인 체크?
        /*NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();

        if (sessNiceRes == null) {
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP3_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[sessNiceRes NUll] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }*/

        //MSimpleOsstXmlVO simpleOsstXmlVO = null;
        //사전동의 결과조회(NP3)
        /*try {
            simpleOsstXmlVO = formCommService.sendOsstService(osstReqDto, EVENT_CODE_NP_ARREE);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("RSLT_CD", simpleOsstXmlVO.getRsltCd());
                rtnMap.put("RSLT_MSG", simpleOsstXmlVO.getRsltMsg());
                rtnMap.put("GLOBAL_NO", simpleOsstXmlVO.getGlobalNo());

                if ("LOCAL".equals(serverName)) {
                    rtnMap.put("RSLT_CD", "S");
                    rtnMap.put("GLOBAL_NO", "12345678");
                    return rtnMap;
                }
            } else {
                //2.세션 CERT_SEQ 가져오기
                long crtSeq = SessionUtils.getCertSession();
                //이력 정보 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("NP3_ERROR");
                mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
                mcpIpStatisticDto.setPrcsSbst("Exception[simpleOsstXmlVO.isNotSuccess] ");
                mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
            }
        } catch (McpMplatFormException e) {
            //이력 정보 저장 처리
            //2.세션 CERT_SEQ 가져오기
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP3_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[McpMplatFormException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
        } catch (SocketTimeoutException e) {
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP3_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[SocketTimeoutException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
            return rtnMap;

        } catch (SelfServiceException e) {

            if ("LOCAL".equals(serverName)) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("RSLT_CD", "S");
                rtnMap.put("RSLT_MSG", "성공");
                rtnMap.put("GLOBAL_NO", "12345678");
                return rtnMap;
            }

            //메세지에 따른 resultCode 변경 처리
            String resultCode = e.getResultCode();
            String message = e.getMessageNe();
            rtnMap.put("OSST_RESULT_CODE", resultCode);
            rtnMap.put("ERROR_NE_MSG", message);
            return rtnMap;
        }*/

        //return rtnMap;
    }


    /**
     * 번호이동 납부주장 요청 : NP2
     * 고객포탈은 사용하지 않는 것으로 기록되어 있음. reqPayOpnAjax.do URI 를 조회해도 나오진 않음.
     **/
    //public Map<String, Object> reqPayOpn(McpRequestMoveDto requestMoveDto, AppformReqDto appformReqDto) {
    public FormResponse<MnpOsstResponse> requestPayOpn(MnpOsstRequest request) {
        log.debug("★ 번호이동 납부주장 요청 ★ osstOrdNo: {}, slsCmpnCd: {}, npTlphNo: {}, payAsertDt: {}, payAsertAmt: {}, payMethCd: {}",
            request.getOsstOrdNo(),
            request.getSlsCmpnCd(),
            request.getNpTlphNo(),
            request.getPayAsertAmt(),
            request.getPayMethCd());

        MnpOsstResponse responseDto = new MnpOsstResponse();
        return FormResponse.of(ResponseMessage.VALID_REQ_NP_PAY_OPEN_SUCCESS, responseDto);

        //Key 받는거 정리해야함. 아래 코드는 스마트에서 추가함.
        //MsfRequestMoveVo msfRequestMoveVo = new MsfRequestMoveVo();
        //msfRequestMoveVo.setRequestKey(request.getRequestKey());

        /*requestMoveDto.setRequestKey(appformReqDto.getRequestKey());
        if (appformSvc.updateMcpRequestMove(requestMoveDto)) {
            MSimpleOsstXmlVO simpleOsstXmlVO = null;
            ////번호이동 사전동의 요청(NP2)
            try {
                Thread.sleep(3000);
                simpleOsstXmlVO = appformSvc.sendOsstService(appformReqDto.getResNo(), EVENT_CODE_NP_REQ_PAY);
                if (simpleOsstXmlVO.isSuccess()) {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                    rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                    rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                }
            } catch (McpMplatFormException e) {
                rtnMap.put("RESULT_CODE", "9997");
                rtnMap.put("ERROR_MSG", "response massage is null.");
            } catch (SocketTimeoutException e) {
                rtnMap.put("RESULT_CODE", "9999");
                rtnMap.put("ERROR_MSG", "SocketTimeout");
                return rtnMap;

            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG", e.getMessage());
                return rtnMap;
            } catch (InterruptedException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }*/

        //return rtnMap;
    }


    /**
     * 번호이동 사전체크 일 건수 제한
     **/
    public Map<String, Object> mnpPreCheckLimit(String moveMobileNo) {

        Map<String, Object> rtnMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        List<String> resNoList = new ArrayList<>();

        // 제한시간(분) 조회
        int limitDay = 0;
        int limitCnt = 0;
        String fAlertMsg = "";

        //NmcpCdDtlDto limitDto = NmcpServiceUtils.getCodeNmDto(Constants.CMM_PERIOD_LIMIT, "MnpDayLimit");
        /*if (limitDto != null) {
            limitDay = Integer.parseInt(StringUtil.NVL(limitDto.getExpnsnStrVal1(), "0"));
            limitCnt = Integer.parseInt(StringUtil.NVL(limitDto.getExpnsnStrVal2(), "0"));
            fAlertMsg = limitDto.getExpnsnStrVal3();

            // 동일 번호이동전화번호 신청서 조회
            paramMap.put("limitDay", limitDay);
            paramMap.put("moveMobileNo", moveMobileNo);
            resNoList = mcpRequestReadMapper.getResNoByMoveMobileNum(paramMap);
        }*/

        //공통코드 불러와서 처리할 것!!!!!!!!!!!!!!!!!!!!
        NmcpCdDtlDto limitDto = new NmcpCdDtlDto();
        limitDay = Integer.parseInt("3");
        limitCnt = Integer.parseInt("10");
        fAlertMsg = "번호이동 사전동의~~~ ";

        // 동일 번호이동전화번호 신청서 조회
        paramMap.put("limitDay", limitDay);
        paramMap.put("moveMobileNo", moveMobileNo);
        resNoList = mcpRequestReadMapper.getResNoByMoveMobileNum(paramMap);

        // 특정기간 내 신청건 없음 → 성공처리
        if (resNoList.isEmpty()) {
            rtnMap.put("RESULT_CODE", "00000");
            return rtnMap;
        }

        // 사전체크 시도 이력 확인
        paramMap.put("resNoList", resNoList);
        paramMap.put("prgrStatCd", Constants.EVENT_CODE_PRE_CHECK);
        int tryCnt = mcpRequestReadMapper.getPreCheckTryCnt(paramMap);


        // 실패이력 저장
        //        McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
        //        mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
        //        mcpIpStatisticDto.setTrtmRsltSmst(moveMobileNo);
        //        mcpIpStatisticDto.setPrcsSbst("Exception[PC0_DAY_LIMIT]");
        //        mcpIpStatisticDto.setParameter("MOVE_MOBILE_NUM[" + moveMobileNo + "] TRY_CNT[" + tryCnt + "] LIMIT_CNT[" + limitCnt + "]");
        //        ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

        //        rtnMap.put("RESULT_CODE", "-9999");
        //        rtnMap.put("ERROR_MSG", "PC0_TIME_LIMIT");
        //        rtnMap.put("ERROR_NE_MSG", fAlertMsg);

        rtnMap.put("RESULT_CODE", "0000");

        return rtnMap;
    }

}
