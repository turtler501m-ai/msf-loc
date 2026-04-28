package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.OsstReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NumberPortableService {

    private FormCommService formCommService;
    //private IpstatisticService ipstatisticService;

    /**
     * 번호이동 사전동의 요청 : NP1
     **/
    public Map<String, Object> requestNpPreCheck(OsstReqDto osstReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //1-3. 본인인증 확인
        // 스마트는 신분증 확인여부 체크????
        /*NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();

        if (sessNiceRes == null) {
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[sessNiceRes NUll] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }*/

        // ============ STEP START ============
        // 이름, 생년월일
        // 유효성체크 >> 공통으로 요청해보자
        /*String[] certKey = {"urlType", "name", "birthDate"};
        String[] certValue = {"chkNpForm", osstReqDto.getCustNm(), EncryptUtil.ace256Enc(osstReqDto.getCustIdntNo())};

        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            rtnMap.put("RESULT_CODE", "STEP01");
            rtnMap.put("ERROR_NE_MSG", vldReslt.get("RESULT_DESC"));
            return rtnMap;
        }*/
        // ============ STEP END ============

        // (번이) 동일 번호이동 휴대폰번호로 사전체크 시도 이력 존재시 → 실패처리
        // >> mnpPreCheckLimit 는 사전체크에서도 있음. 정리가 필요함.
        // >> 고객포탈 AppformMapper.getPreCheckTryCnt 로 쿼리로 처리하고 있음.
        // >> 테이블은 MCP_REQUEST_OSST 로 예약번호로 해당 서비스 예를 들어 NP1 이 시간안에 몇건인지?
        // >> 그럼 예약번호가 계속 동일해야하는데 그건 해당 고객포탈에서 사용자가 자기 계정 로그인해서 한거라... 우린 어떻게 처리하지?
        /*Map<String, Object> chkMap = appformSvc.mnpPreCheckLimit(osstReqDto.getNpTlphNo());
        if (!AJAX_SUCCESS.equals(chkMap.get("RESULT_CODE"))) {
            return chkMap;
        }*/

        MSimpleOsstXmlVO simpleOsstXmlVO = null;

        //번호이동 사전동의 요청(NP1)
        /*try {
            simpleOsstXmlVO = formCommService.sendOsstService(osstReqDto, EVENT_CODE_NP_PRE_CHECK);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("GLOBAL_NO", simpleOsstXmlVO.getGlobalNo());

                // ============ STEP START ============
                // 유효성체크... 여기도 공통으로 처리해야하나.. 이걸 그냥 가져와야하나..
                *//*certKey = new String[]{"urlType", "mobileNo"};
                certValue = new String[]{"reqNpForm", osstReqDto.getNpTlphNo()};
                certService.vdlCertInfo("C", certKey, certValue);*//*
                // ============ STEP END ============
            } else { //서비스 호출 실패
                //2.세션 CERT_SEQ 가져오기
                long crtSeq = SessionUtils.getCertSession();
                //이력 정보 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
                //mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
                mcpIpStatisticDto.setTrtmRsltSmst(crtSeq + "");
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
            long crtSeq = SessionUtils.getCertSession();
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
            //mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setTrtmRsltSmst(crtSeq + "");
            mcpIpStatisticDto.setPrcsSbst("Exception[McpMplatFormException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);


            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
        } catch (SocketTimeoutException e) {
            //이력 정보 저장 처리
            long crtSeq = SessionUtils.getCertSession();
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
            //mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setTrtmRsltSmst(crtSeq + "");
            mcpIpStatisticDto.setPrcsSbst("Exception[SocketTimeoutException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
            return rtnMap;

        } catch (SelfServiceException e) {

            //메세지에 따른 resultCode 변경 처리
            String resultCode = e.getResultCode();
            String message = e.getMessageNe();

            rtnMap.put("GLOBAL_NO", e.getGlobalNo());

            if ("ITL_SST_E1014".equals(resultCode)) {
                //성공처리
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

                // ============ STEP START ============
                certKey = new String[]{"urlType", "mobileNo"};
                certValue = new String[]{"reqNpForm", osstReqDto.getNpTlphNo()};
                certService.vdlCertInfo("C", certKey, certValue);
                // ============ STEP END ============

                return rtnMap;
            } else {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG", e.getMessage());
            }

            //session에 저장한 서식지 정보 초기화
            SessionUtils.saveAppformDto(null);
            if ("ITL_SST_E1018".equals(resultCode) && message.indexOf("가입제한자") > -1) {
                resultCode = "ITL_SST_E1018_01";
            } else if ("ITL_SST_E1018".equals(resultCode) && message.indexOf("미납고객") > -1) {
                resultCode = "ITL_SST_E1018_02";
            }

            rtnMap.put("OSST_RESULT_CODE", resultCode);
            rtnMap.put("ERROR_NE_MSG", message);
            return rtnMap;
        }*/

        return rtnMap;
    }

    /**
     * 번호이동 사전동의 결과조회 : NP3
     **/
    public Map<String, Object> requestNpAgree(OsstReqDto osstReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

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

        return rtnMap;
    }

    /**
     * 번호이동 사전동의 결과조회 : NP2
     * 고객포탈은 사용하지 않는 것으로 기록되어 있음. reqPayOpnAjax.do URI 를 조회해도 나오진 않음.
     **/
    //public Map<String, Object> reqPayOpn(McpRequestMoveDto requestMoveDto, AppformReqDto appformReqDto) {
    public Map<String, Object> requestPayOpn(NewChangeInfoRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        /*if ("LOCAL".equals(serverName)) {
            try {
                Thread.sleep(15 * 1000);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                return rtnMap;
            } catch (InterruptedException e) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            // *********************** STG 환경 강제로 성공 처리 시작
        } else if ("STG".equals(serverName)) {
            try {
                Thread.sleep(15 * 1000);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                return rtnMap;
            } catch (InterruptedException e) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }
            // *********************** STG 환경 강제로 성공 처리 끝

        }*/

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

        return rtnMap;
    }


}
