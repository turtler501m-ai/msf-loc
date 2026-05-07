package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoListXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.form.common.dto.McpRequestOsstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.SearchNumberRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.SearchNumberResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceNumberService {

    private final FormCommService formCommService;
    private final NewChangeReadMapper newChangeReadMapper;
    private final FormCommWriteMapper formCommWriteMapper;
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
    public FormResponse<SearchNumberResponse> getSearchNumber(SearchNumberRequest request) {
        //Parameter 정보 : requestKey, reqWantNumber

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        SearchNumberResponse searchNumberResponse = new SearchNumberResponse(); //Return DTO 설정
        McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();

        //1. 개통전 사전체크 진행여부 확인을 위해 신청서번호(request_key) 로 예약번호 (res_no) 값을 조회한다.
        //    개통전 사전체크가 완료되면 MCP_REQUEST_OSST 테이블에도 저장하고 스마트 MSF_REQUEST_TEMP 테이블에는 스마트 프로세스에서 업데이트 처리하기 때문이다.
        //    스마트에서 조회된 RES_NO 값으로 MCP_REQUEST_OSST 테이블에서 개통전 사전체크(PC0) 로 저장 여부를 조회한다.
        String resNo = newChangeReadMapper.getMsfResNo(request.getRequestKey());
        mcpRequestOsstRequest.setMvnoOrdNo(resNo);
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK);
        resNo = "955336"; //@@삭제필수@@

        //2. 개통전 사전체크 호출 여부 확인
        int osstCount = formCommService.getOsstCount(mcpRequestOsstRequest);
        osstCount = 1; //@@삭제필요@@ : prx 호출 전이므로 강제처리
        if (osstCount == 0) {
            return null; //개통전 사전체크를 진행해야 함. 흠.......
        }
        //3. OSST_ORD_NO 조회 >> NU1 호출 시 MCP-API 에서 호출해서 가져가서 필요없는 항목
        /*mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setMvnoOrdNo(resNo); //개통전 사전체크가 정상적인 경우 위에서 호출해서 처리함.
        String osstNo = formCommService.getOsstOrdNo(mcpRequestOsstRequest);*/

        //4. MCP_REQUEST_OSST 호출 이력확인 (RES_NO , PRGR_STAT_CD) - 신규가입 희망번호 조회가능횟수 조회
        // >> prx 의 SimpleOpenServiceCall 호출 후 호출결과를 MCP_REQUEST_OSST 테이블에 INSERT 처리하므로 해당 결과여부 확인.
        // >> resNo가 없다고 오류가 나진 않음. 최초에는 없고 호출 후 세션에 저장하고 언제 세션에서 날리지?
        // >> 세션에 저장하기 보다는 requestKey 로 조회해야하나? 흠...
        mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setMvnoOrdNo(resNo); //개통전 사전체크가 정상적인 경우 위에서 호출해서 처리함.
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_SEARCH_NUMBER);
        int tryCount = formCommService.getMcpRequestOsstCount(mcpRequestOsstRequest);
        searchNumberResponse.setTryCount(tryCount);
        if (tryCount > 24) { //24건보다 많으면 안됨.
            //throw new McpCommonJsonException("0004", OVER_LIMIT_EXCEPTION);
            return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_OVER_LIMIT, searchNumberResponse);
        }

        //5. MCP_REQUEST 테이블에 희망번호 정보 저장 @@처리필요@@
        //@@ PRX 되면 처리해야하는데... 그건 사전체크 시 데이타 정상 저장되도록해야함.
        //AGENT_CODE , OPER_TYPE , REQUEST_KEY 와 희망번호가 저장되어야 함. - 실제 서비스 호출 시 확인필요
        /*McpRequestDto mcpRequestDto = new McpRequestDto();
        mcpRequestDto.setRequestKey(request.getRequestKey());
        mcpRequestDto.setReqWantNumber(request.getReqWantNumber());
        if (!formCommWriteMapper.updateMcpRequest(mcpRequestDto)) {
            return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_OVER_LIMIT, searchNumberResponse);
        }*/

        //6. MP호출
        //appformSvc.getPhoneNoList(sessAppformReqDto.getResNo(), EVENT_CODE_SEARCH_NUMBER);
        MPhoneNoListXmlVO mPhoneNoListXmlVO = null;
        try {
            mPhoneNoListXmlVO = formCommService.getPhoneNoList(resNo, Constants.EVENT_CODE_SEARCH_NUMBER);
            if (mPhoneNoListXmlVO.isSuccess()) {
                if (mPhoneNoListXmlVO.getList() != null && !mPhoneNoListXmlVO.getList().isEmpty()) {
                    //SessionUtils.saveCntSession(++searchCnt); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
                    //rtnMap.put("SEARCH_CNT", searchCnt);
                    //rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
                    //rtnMap.put("RESULT_OBJ_LIST", mPhoneNoListXmlVO.getList());

                    //searchNumberResponse.setMPhoneNoList(mPhoneNoListXmlVO.getList());
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

        //7. 서비스호출이 정상이라면 스마트 테이블에 저장?
        //  MSF_REQUEST_OSST ? , MSF_REQUEST ? , MSF_REQUEST_TEMP ? 에 저장
        //  MSF_REQUEST_OSST 는 안해도 되겠지?
        if (rtnMap.get("RESULT_CODE").equals(Constants.AJAX_SUCCESS)) {
            //MCP_REQUEST 조회해와서 MSF_REQUEST_TEMP 에는 업데이트?? 또는 호출 전 MSF_REQUEST_TEMP 테이블에 희망번호만 업데이트?
            //MCP_REQUEST_OSST 조회해와서 MSF_REQUEST_OSST 에 추가
        }

        //@@ prx 오픈전까지 강제 성공처리 @@삭제필요!!!!
        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);

        List<MarketInfo> marketList = List.of(
                new MarketInfo("KTF", "01025679878", "010-2567-9878", "kGBQFD/q0YBTCuaJvb6rgw=="),
                new MarketInfo("KTF", "01097839878", "010-9783-9878", "H9tDkJ36z9JTCuaJvb6rgw=="),
                new MarketInfo("KTF", "01027069878", "010-2706-9878", "seZbgXBQKS9TCuaJvb6rgw=="),
                new MarketInfo("KTF", "01029149878", "010-2914-9878", "i6LgwCy3xeVTCuaJvb6rgw=="),
                new MarketInfo("KTF", "01029699878", "010-2969-9878", "DAjv7I6wyc5TCuaJvb6rgw=="),
                new MarketInfo("KTF", "01033949878", "010-3394-9878", "+W1l/smHQ4hTCuaJvb6rgw=="),
                new MarketInfo("KTF", "01042099878", "010-4209-9878", "3JG5FtGdDQFTCuaJvb6rgw=="),
                new MarketInfo("KTF", "01042679878", "010-4267-9878", "QwV/WGoneHRTCuaJvb6rgw=="),
                new MarketInfo("KTF", "01042699878", "010-4269-9878", "MVO48rc6Qm5TCuaJvb6rgw=="),
                new MarketInfo("KTF", "01043759878", "010-4375-9878", "VAhJwziDq71TCuaJvb6rgw=="),
                new MarketInfo("KTF", "01051479878", "010-5147-9878", "/AJOp4tu75ZTCuaJvb6rgw=="),
                new MarketInfo("KTF", "01065039878", "010-6503-9878", "PXO0EzqBeB9TCuaJvb6rgw=="),
                new MarketInfo("KTF", "01066819878", "010-6681-9878", "dIXUuSLYre9TCuaJvb6rgw=="),
                new MarketInfo("KTF", "01074669878", "010-7466-9878", "Bpu2TFJP0SpTCuaJvb6rgw=="),
                new MarketInfo("KTF", "01026139878", "010-2613-9878", "jcps9Xo34jFTCuaJvb6rgw==")
        );

        //rtnMap.put("RESULT_OBJ_LIST", marketList);
        //rtnMap.put("RESULT_OBJ_LIST", mPhoneNoListXmlVO.getList());
        //return rtnMap;

        searchNumberResponse.setMarketList(marketList);
        return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_SUCCESS, searchNumberResponse);
    }


    /**
     * 신규가입 희망번호 예약
     * MCP_REQUEST_OSST 테이블에 선택한
     * 전화번호, 암호화된 전화번호, 할당대리점ID, 전화번호상태코드, 번호소유통신사사업자코드, 개통서비스구분코드 등을 저장 후 MP호출
     **/
    //public Map<String, Object> setNumber(McpRequestOsstDto request) {
    public FormResponse<SearchNumberResponse> setChoiseNumber(SearchNumberRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        SearchNumberResponse searchNumberResponse = new SearchNumberResponse(); //Return DTO 설정
        McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();

        //1. 개통전 사전체크 진행여부 확인을 위해 신청서번호(request_key) 로 예약번호 (res_no) 값을 조회한다.
        //    개통전 사전체크가 완료되면 MCP_REQUEST_OSST 테이블에도 저장하고 스마트 MSF_REQUEST_TEMP 테이블에는 스마트 프로세스에서 업데이트 처리하기 때문이다.
        //    스마트에서 조회된 RES_NO 값으로 MCP_REQUEST_OSST 테이블에서 개통전 사전체크(PC0) 로 저장 여부를 조회한다.
        String resNo = newChangeReadMapper.getMsfResNo(request.getRequestKey());
        mcpRequestOsstRequest.setMvnoOrdNo(resNo);
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK);
        resNo = "955336"; //@@삭제필수@@

        //2. 개통전 사전체크 호출 여부 확인
        int osstCount = formCommService.getOsstCount(mcpRequestOsstRequest);
        osstCount = 1; //@@삭제필요@@ : prx 호출 전이므로 강제처리
        if (osstCount == 0) {
            return null; //개통전 사전체크를 진행해야 함. 흠.......
        }
        //3. OSST_ORD_NO 조회 >> NU1 호출 시 MCP-API 에서 호출해서 가져가서 필요없는 항목
        /*mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setMvnoOrdNo(resNo); //개통전 사전체크가 정상적인 경우 위에서 호출해서 처리함.
        String osstNo = formCommService.getOsstOrdNo(mcpRequestOsstReqvuest);*/
        String osstNo = "955336"; //@@삭제필수@@
        String agentCode = "";

        //MCP_REQUEST_OSST 테이블에 저장하기 위해
        McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        mcpRequestOsstDto.setMvnoOrdNo(resNo);
        mcpRequestOsstDto.setOsstOrdNo(osstNo);
        mcpRequestOsstDto.setPrgrStatCd(Constants.EVENT_CODE_NUMBER_REG);
        mcpRequestOsstDto.setAsgnAgncId(agentCode);
        mcpRequestOsstDto.setOpenSvcIndCd("03"); //03 고정
        mcpRequestOsstDto.setTlphNoStatCd("AR"); //"tlphNoStatChngRsnCd = RSV의 경우 AR , tlphNoStatChngRsnCd = RRS의 경우 AA"
        mcpRequestOsstDto.setTlphNoOwnCmpnCd(request.getTlphNoOwnCmpnCd());
        mcpRequestOsstDto.setEncdTlphNo(request.getEncdTlphNo());
        mcpRequestOsstDto.setTlphNo(request.getTlpNo());
        mcpRequestOsstDto.setOpenSvcIndCd("");
        mcpRequestOsstDto.setNstepGlobalId("");
        mcpRequestOsstDto.setIfType(Constants.WORK_CODE_RES);
        mcpRequestOsstDto.setRsltCd(Constants.OSST_SUCCESS);

        //3. MP 호출
        if (formCommService.setMcpRequestOsst(mcpRequestOsstDto)) {
            MSimpleOsstXmlVO simpleOsstXmlVO = null;
            //번호예약(NU2)
            try {
                Thread.sleep(3000);
                simpleOsstXmlVO = formCommService.sendOsstService(resNo, Constants.EVENT_CODE_NUMBER_REG, Constants.WORK_CODE_RES);
                if (simpleOsstXmlVO.isSuccess()) {
                    rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
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
                //return rtnMap;

            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG", e.getMessage());
                //return rtnMap;
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

        //rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        //return rtnMap;

        return FormResponse.of(ResponseMessage.VALID_RESERVE_NUMBER_SUCCESS, null);
    }

    /**
     * 신규가입 희망번호 취소
     */
    //public Map<String, Object> cancelNumberAjax(AppformReqDto appformReqDto) {
    public FormResponse<SearchNumberResponse> cancelChoiseNumber(NewChangeInfoRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        SearchNumberResponse searchNumberResponse = new SearchNumberResponse(); //Return DTO 설정
        McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();

        //1. 개통전 사전체크 진행여부 확인을 위해 신청서번호(request_key) 로 예약번호 (res_no) 값을 조회한다.
        //    개통전 사전체크가 완료되면 MCP_REQUEST_OSST 테이블에도 저장하고 스마트 MSF_REQUEST_TEMP 테이블에는 스마트 프로세스에서 업데이트 처리하기 때문이다.
        //    스마트에서 조회된 RES_NO 값으로 MCP_REQUEST_OSST 테이블에서 개통전 사전체크(PC0) 로 저장 여부를 조회한다.
        String resNo = newChangeReadMapper.getMsfResNo(request.getRequestKey());
        mcpRequestOsstRequest.setMvnoOrdNo(resNo);
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK);
        resNo = "955336"; //@@삭제필수@@

        //2. 개통전 사전체크 호출 여부 확인
        int osstCount = formCommService.getOsstCount(mcpRequestOsstRequest);
        osstCount = 1; //@@삭제필요@@ : prx 호출 전이므로 강제처리
        if (osstCount == 0) {
            return null; //개통전 사전체크를 진행해야 함. 흠.......
        }
        //3. OSST_ORD_NO 조회 >> NU1 호출 시 MCP-API 에서 호출해서 가져가서 필요없는 항목
        /*mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setMvnoOrdNo(resNo); //개통전 사전체크가 정상적인 경우 위에서 호출해서 처리함.
        String osstNo = formCommService.getOsstOrdNo(mcpRequestOsstReqvuest);*/
        String osstNo = "955336"; //@@삭제필수@@
        String agentCode = "";

        //4. MP 호출
        MSimpleOsstXmlVO simpleOsstXmlVO = null;

        //번호예약(NU2) 취소
        try {
            simpleOsstXmlVO = formCommService.sendOsstService(resNo, Constants.EVENT_CODE_NUMBER_REG, Constants.WORK_CODE_RES_CANCEL);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
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
            // return rtnMap;

        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("ERROR_MSG", e.getMessage());
            //return rtnMap;
        }

        //4. 서비스호출이 정상이라면 스마트 테이블에 저장?
        //  MSF_REQUEST_OSST ? , MSF_REQUEST ? , MSF_REQUEST_TEMP ? 에 저장
        //  MSF_REQUEST_OSST 는 안해도 되겠지?
        if (rtnMap.get("RESULT_CODE").equals(Constants.AJAX_SUCCESS)) {
            //MCP_REQUEST 조회해와서 MSF_REQUEST_TEMP 에는 업데이트?? 또는 호출 전 MSF_REQUEST_TEMP 테이블에 희망번호만 업데이트?
            //MCP_REQUEST_OSST 조회해와서(?) MSF_REQUEST_OSST 에 추가
        }

        //rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        //return rtnMap;

        return FormResponse.of(ResponseMessage.VALID_CANCEL_NUMBER_SUCCESS, null);
    }


}
