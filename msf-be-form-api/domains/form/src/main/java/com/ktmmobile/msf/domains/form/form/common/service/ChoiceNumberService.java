package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoListXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChoiceNumberService {

    private final FormCommService formCommService;
    //private final MsfMplatFormOsstServerAdapter mplatFormOsstServerAdapter;

    //신규가입 희망번호 조회는 개통사전체크요청이 필수

    /**
     * 신규가입 희망번호 조회
     * osst.simple.open.url=http://10.20.28.38:7006/mPlatform/simpleOpenServiceCall.do
     * osst.simple.open.url=http://211.253.137.11:9627/mPlatform/simpleOpenServiceCall.do
     * osst.simple.open.url=http://10.20.28.87:7006/mPlatform/simpleOpenServiceCall.do
     * /mPlatform/getRequestInfo >> MCP_REQUEST 등록정보 조회 후 서비스 호출을 함.
     * /mPlatform/getXmlMessageNU1 또는 /mPlatform/getXmlMessageNU2 호출 하기전 데이타셋은 MCP_REQUEST,MCP_REQUEST_OSST 를 조회하여 데이타 가져옴
     * prx 에서 Mplatform 연동 후 MCP_REQUEST_OSST 결과 저장함.
     * 세션에 연동정보 저장은 못 찾겠네.. 흠..
     * MCP_REQUEST_OSST 테이블의 MVNO_ORD_NO 값은 여러개 가능. 즉 GROUP BY 가능
     * MCP_REQUEST_OSST 는 호출전 REQUEST INSERT, 호출후 RESPONSE INSERT
     **/
    //public Map<String, Object> searchNumber(McpRequestDto mcpRequestDto) {
    public Map<String, Object> getSearchNumber(NewChangeInfoRequest request) {
        //Parameter 정보
        //resNo
        //requestKey
        //reqWantFnNo, reqWantMnNo , reqWantRnNo
        //화면에서 입력한 희망번호 4자리는 어디에 저장되는지 확인필요

        System.out.println("request.getRequestKey() ======================== " + request.getRequestKey());
        System.out.println("request.getReqWantFnNo() ======================== " + request.getReqWantFnNo());
        System.out.println("request.getResNo() ======================== " + request.getResNo());
        System.out.println("Constants.EVENT_CODE_SEARCH_NUMBER ======================== " + Constants.EVENT_CODE_SEARCH_NUMBER);

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //0. 기존 local 영역은 일단 삭제함.
        //1. 개통사전체크요청 확인
        // sessAppformReqDto = SessionUtils.getAppformSession(); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
        // if (sessAppformReqDto == null) {
        //      throw new McpCommonJsonException("0003", ExceptionMsgConstant.F_BIND_EXCEPTION);
        // }
        // searchCnt = SessionUtils.getCntSession(); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
        // if (searchCnt > 19) {
        //      throw new McpCommonJsonException("0004", ExceptionMsgConstant.OVER_LIMIT_EXCEPTION);
        // }

        //2. MCP_REQUEST_OSST 호출 이력확인 (RES_NO , PRGR_STAT_CD) - 신규가입 희망번호 조회가능횟수 조회
        // >> prx 의 SimpleOpenServiceCall 호출 후 호출결과를 MCP_REQUEST_OSST 테이블에 INSERT 처리하므로 해당 결과여부 확인.
        // >> resNo가 없다고 오류가 나진 않음. 최초에는 없고 호출 후 세션에 저장하고 언제 세션에서 날리지?
        // >> 세션에 저장하기 보다는 requestKey 로 조회해야하나? 흠...
        McpRequestOsstDto mcpRequestOsstVo = new McpRequestOsstDto();
        //mcpRequestOsstVo.setMvnoOrdNo(sessAppformReqDto.getResNo()); //예약번호 생성이 MSF? MCP?
        mcpRequestOsstVo.setMvnoOrdNo(request.getResNo());
        mcpRequestOsstVo.setPrgrStatCd(Constants.EVENT_CODE_SEARCH_NUMBER);
        int tryCount = formCommService.getMcpRequestOsstCount(mcpRequestOsstVo);
        System.out.println("tryCount ======================== " + tryCount);
        if (tryCount > 24) { //24건보다 많으면 안됨.
            throw new McpCommonJsonException("0004", ExceptionMsgConstant.OVER_LIMIT_EXCEPTION);
        }

        //3. MCP_REQUEST MERGE (희망번호 저장)
        //SELECT REQ_WANT_FN_NO, REQ_WANT_MN_NO, REQ_WANT_RN_NO FROM MCP_REQUEST;
        //AGENT_CODE , OPER_TYPE , REQUEST_KEY 와 희망번호가 저장되어야 함.
        McpRequestDto mcpRequestDto = new McpRequestDto();

        //@@ 임시 (꼭 삭제!!!) :: MCP_REQUEST 저장을 위한 parameter SET ----------------
        // >> 추후 저장된 데이타를 가져오거나 프론트에서 보내거나 결정이 필요함.
        String managerCode = "0";
        String agentCode = "0";
        String serviceType = "0";
        String operType = "0";
        String cstmrType = "0";
        String pstate = "0";
        String onOffType = "0";
        mcpRequestDto.setManagerCode(managerCode);
        mcpRequestDto.setAgentCode(agentCode);
        mcpRequestDto.setServiceType(serviceType);
        mcpRequestDto.setOperType(operType);
        mcpRequestDto.setCstmrType(cstmrType);
        mcpRequestDto.setPstate(pstate);
        mcpRequestDto.setOnOffType(onOffType);
        //@@ 임시 (꼭 삭제!!!) :: MCP_REQUEST 저장을 위한 parameter SET ----------------

        //msfRequestOsstDto.setRequestKey(sessAppformReqDto.getRequestKey());
        mcpRequestDto.setRequestKey(request.getRequestKey());
        mcpRequestDto.setReqWantNumber(request.getReqWantFnNo());
        //mcpRequestDto.setReqWantNumber2(request.getReqWantMnNo());
        //mcpRequestDto.setReqWantNumber3(request.getReqWantRnNo());
        if (!formCommService.mergeMcpRequest(mcpRequestDto)) { //
            throw new McpCommonJsonException("0004", ExceptionMsgConstant.DB_EXCEPTION);
        }

        //4. MP호출
        MPhoneNoListXmlVO mPhoneNoListXmlVO = null;
        try {
            //mPhoneNoListXmlVO = appformSvc.getPhoneNoList(sessAppformReqDto.getResNo(), EVENT_CODE_SEARCH_NUMBER);
            mPhoneNoListXmlVO = formCommService.getPhoneNoList(request.getResNo(), Constants.EVENT_CODE_SEARCH_NUMBER);
            if (mPhoneNoListXmlVO.isSuccess()) {
                if (mPhoneNoListXmlVO.getList() != null && !mPhoneNoListXmlVO.getList().isEmpty()) {
                    //SessionUtils.saveCntSession(++searchCnt); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
                    //rtnMap.put("SEARCH_CNT", searchCnt);
                    rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
                    rtnMap.put("RESULT_OBJ_LIST", mPhoneNoListXmlVO.getList());
                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                }
            } else {
                rtnMap.put("RESULT_CODE", "0002");
                rtnMap.put("RESULT_XML", mPhoneNoListXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", mPhoneNoListXmlVO.getResultCode());
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeoutException.");
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("ERROR_MSG", e.getMessage());
        }

        //5. 서비스호출이 정상이라면 스마트 테이블에 저장?
        //  MSF_REQUEST_OSST ? , MSF_REQUEST ? , MSF_REQUEST_TEMP ? 에 저장
        //  MSF_REQUEST_OSST 는 안해도 되겠지?
        if (rtnMap.get("RESULT_CODE").equals(Constants.AJAX_SUCCESS)) {
            //MCP_REQUEST 조회해와서 MSF_REQUEST_TEMP 에는 업데이트?? 또는 호출 전 MSF_REQUEST_TEMP 테이블에 희망번호만 업데이트?
            //MCP_REQUEST_OSST 조회해와서 MSF_REQUEST_OSST 에 추가
        }

        //@@ prx 오픈전까지 강제 성공처리
        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        //rtnMap.put("RESULT_OBJ_LIST", mPhoneNoListXmlVO.getList());

        return rtnMap;
    }


    /**
     * 신규가입 희망번호 예약
     * MCP_REQUEST_OSST 테이블에 선택한
     * 전화번호, 암호화된 전화번호, 할당대리점ID, 전화번호상태코드, 번호소유통신사사업자코드, 개통서비스구분코드 등을 저장 후 MP호출
     **/
    //public Map<String, Object> setNumber(McpRequestOsstDto request) {
    public Map<String, Object> setChoiseNumber(NewChangeInfoRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //0. 기존 local 영역은 일단 삭제함.
        //1. 개통사전체크요청 확인
        // AppformReqDto sessAppformReqDto = SessionUtils.getAppformSession();
        //if (sessAppformReqDto == null) {
        //    throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        //}

        //2. MP 호출 전 MCP_REQUEST_OSST 테이블에 데이타 저장하기 위한 parameter 설정
        //McpRequestOsstDto sessRequestOsstDto = SessionUtils.getOsstDtoSession();
        /*if (sessRequestOsstDto == null) {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }*/
        McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        mcpRequestOsstDto.setMvnoOrdNo(request.getResNo());
        //mcpRequestOsstDto.setOsstOrdNo(request.getOsstOrdNo()); //MP에서 리턴받은 값
        mcpRequestOsstDto.setPrgrStatCd(Constants.EVENT_CODE_NUMBER_REG);
        //mcpRequestOsstDto.setAsgnAgncId(request.getAgentCode());
        mcpRequestOsstDto.setOpenSvcIndCd("03"); //03 고정 (3G)
        mcpRequestOsstDto.setIfType(Constants.WORK_CODE_RES);
        mcpRequestOsstDto.setRsltCd(Constants.OSST_SUCCESS);

        //request 객체 수정할 필요있음.
        //@@ 강제처리 ================================================
        mcpRequestOsstDto.setMvnoOrdNo(request.getResNo());
        mcpRequestOsstDto.setOsstOrdNo("");
        mcpRequestOsstDto.setPrgrStatCd("");
        mcpRequestOsstDto.setRsltCd("");
        mcpRequestOsstDto.setTlphNoStatCd("");
        mcpRequestOsstDto.setAsgnAgncId("");
        mcpRequestOsstDto.setTlphNoOwnCmpnCd("");
        mcpRequestOsstDto.setOpenSvcIndCd("");
        mcpRequestOsstDto.setEncdTlphNo("");
        mcpRequestOsstDto.setTlphNo("");
        mcpRequestOsstDto.setNstepGlobalId("");
        //@@ 강제처리 ================================================

        //3. MP 호출
        if (formCommService.setMcpRequestOsst(mcpRequestOsstDto)) { //선택한 전화번호 정보를 MCP_REQUEST_OSST 에 저장함.
            MSimpleOsstXmlVO simpleOsstXmlVO = null;
            //번호예약(NU2)
            try {
                Thread.sleep(3000);
                simpleOsstXmlVO = formCommService.sendOsstService(request.getResNo(), Constants.EVENT_CODE_NUMBER_REG, Constants.WORK_CODE_RES);
                if (simpleOsstXmlVO.isSuccess()) {
                    //rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
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
                return rtnMap;

            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG", e.getMessage());
                return rtnMap;
            } catch (InterruptedException e) {
                //logger.error("Exception e : {}", e.getMessage());
            }
        } else {
            //throw new McpCommonJsonException("9997", DB_EXCEPTION);
        }

        //4. 서비스호출이 정상이라면 스마트 테이블에 저장?
        //  MSF_REQUEST_OSST ? , MSF_REQUEST ? , MSF_REQUEST_TEMP ? 에 저장
        //  MSF_REQUEST_OSST 는 안해도 되겠지?
        if (rtnMap.get("RESULT_CODE").equals(Constants.AJAX_SUCCESS)) {
            //MCP_REQUEST 조회해와서 MSF_REQUEST_TEMP 에는 업데이트?? 또는 호출 전 MSF_REQUEST_TEMP 테이블에 희망번호만 업데이트?
            //MCP_REQUEST_OSST 조회해와서(?) MSF_REQUEST_OSST 에 추가
        }

        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * 신규가입 희망번호 취소
     */
    //public Map<String, Object> cancelNumberAjax(AppformReqDto appformReqDto) {
    public Map<String, Object> cancelChoiseNumber(NewChangeInfoRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //1. 개통사전체크요청 확인
        /*AppformReqDto sessAppformReqDto = SessionUtils.getAppformSession();
        if (sessAppformReqDto == null) {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }*/

        //2. 번호예약(NU2) 취소
        MSimpleOsstXmlVO simpleOsstXmlVO = null;
        try {
            //simpleOsstXmlVO = appformSvc.sendOsstService(sessAppformReqDto.getResNo(), EVENT_CODE_NUMBER_REG, WORK_CODE_RES_CANCEL);
            simpleOsstXmlVO = formCommService.sendOsstService(request.getResNo(), Constants.EVENT_CODE_NUMBER_REG, Constants.WORK_CODE_RES);
            if (simpleOsstXmlVO.isSuccess()) {
                //rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
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
            return rtnMap;

        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("ERROR_MSG", e.getMessage());
            return rtnMap;
        }

        //3. 서비스호출이 정상이라면 스마트 테이블에 저장?
        //  MSF_REQUEST_OSST ? , MSF_REQUEST ? , MSF_REQUEST_TEMP ? 에 저장
        //  MSF_REQUEST_OSST 는 안해도 되겠지?
        if (rtnMap.get("RESULT_CODE").equals(Constants.AJAX_SUCCESS)) {
            //MCP_REQUEST 조회해와서 MSF_REQUEST_TEMP 에는 업데이트?? 또는 호출 전 MSF_REQUEST_TEMP 테이블에 희망번호만 업데이트?
            //MCP_REQUEST_OSST 조회해와서(?) MSF_REQUEST_OSST 에 추가
        }

        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        return rtnMap;
    }


}
