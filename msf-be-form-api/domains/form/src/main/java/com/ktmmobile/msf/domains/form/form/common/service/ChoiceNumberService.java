package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChoiceNumberService {

    //private final MsfMplatFormOsstServerAdapter mplatFormOsstServerAdapter;
    
    /**
     * 희망번호 조회
     **/
    public Map<String, Object> getSearchNumber(NewChangeInfoRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //개통사전체크 여부 확인 :: 요놈이 문제여~~~~~
        // >> formCommService.reqPreOpenCheck(request)
        // >> 개통사전체크 진행 시 RES_NO ( MVNO_ORD_NO ) 를 request 값에 전달해야함.
        //RES_NO 등록여부 확인
        // >> MCP_REQUEST_OSST
        //희망번호 저장
        // >> MCP_REQUEST 에 저장해야함. 필요. MSF_REQEST 에도 저장해야하나

        /*AppformReqDto sessAppformReqDto = null;
        int searchCnt = 0;

        //개통사전체크요청 확인
        sessAppformReqDto = SessionUtils.getAppformSession(); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
        if (sessAppformReqDto == null) {
            throw new McpCommonJsonException("0003", ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        searchCnt = SessionUtils.getCntSession(); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
        if (searchCnt > 19) {
            throw new McpCommonJsonException("0004", ExceptionMsgConstant.OVER_LIMIT_EXCEPTION);
        }

        //방어 로직 추가
        MsfRequestOsstVo msfRequestOsstVo = new MsfRequestOsstVo();
        msfRequestOsstVo.setMvnoOrdNo(sessAppformReqDto.getResNo());
        msfRequestOsstVo.setPrgrStatCd(Constants.EVENT_CODE_SEARCH_NUMBER);
        int tryCount = newChangeService.selectMsfRequestOsstCount(msfRequestOsstVo);

        if (tryCount > 24) {
            throw new McpCommonJsonException("0004", ExceptionMsgConstant.OVER_LIMIT_EXCEPTION);
        }

        //희망번호 저장
        msfRequestVo.setRequestKey(sessAppformReqDto.getRequestKey());
        if (!newChangeService.updateMsfRequest(msfRequestVo)) {
            throw new McpCommonJsonException("0004", ExceptionMsgConstant.DB_EXCEPTION);
        }

        MPhoneNoListXmlVO mPhoneNoListXmlVO = null;
        try {
            mPhoneNoListXmlVO = this.selectPhoneNoList(sessAppformReqDto.getResNo(), Constants.EVENT_CODE_SEARCH_NUMBER);
            if (mPhoneNoListXmlVO.isSuccess()) {
                if (mPhoneNoListXmlVO.getList() != null && !mPhoneNoListXmlVO.getList().isEmpty()) {
                    //SessionUtils.saveCntSession(++searchCnt); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
                    rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
                    rtnMap.put("RESULT_OBJ_LIST", mPhoneNoListXmlVO.getList());
                    rtnMap.put("SEARCH_CNT", searchCnt);
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
        }*/

        return rtnMap;
    }


    /**
     * 희망번호 예약
     **/
    public Map<String, Object> setChoiseNumber(NewChangeInfoRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        /*if ("LOCAL".equals(serverName)) {
            try {
                Thread.sleep(5000);
            } catch (IllegalArgumentException e) {
                logger.error("[registerNumber] Exception e : {}", e.getMessage());
            } catch (Exception e) {
                logger.error("[registerNumber] Exception e : {}", e.getMessage());
            }

            rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
            return rtnMap;
        }*/

        //개통사전체크요청 확인
        // >> 스마트에서 해당 시점에 개통사전체크요청을 어떻게 확인하지? 동일하게 세션으로 관리해야하나? 세션은 날리는 시점을 정해야하는데...
        /*AppformReqDto sessAppformReqDto = SessionUtils.getAppformSession(); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
        if (sessAppformReqDto == null) {
            throw new McpCommonJsonException("0003", ExceptionMsgConstant.F_BIND_EXCEPTION);
        }*/

        //OSST ??? 요것도 세션에서 확인해야함.
        /*MsfRequestOsstVo sessRequestOsstDto = SessionUtils.getOsstDtoSession(); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
        if (sessRequestOsstDto == null) {
            throw new McpCommonJsonException("0003", ExceptionMsgConstant.F_BIND_EXCEPTION);
        }
        msfRequestOsstVo.setMvnoOrdNo(sessAppformReqDto.getResNo());
        msfRequestOsstVo.setOsstOrdNo(sessRequestOsstDto.getOsstOrdNo());
        msfRequestOsstVo.setPrgrStatCd(Constants.EVENT_CODE_NUMBER_REG);
        msfRequestOsstVo.setAsgnAgncId(sessAppformReqDto.getAgentCode());
        msfRequestOsstVo.setOpenSvcIndCd("03"); //03 고정 (3G)
        msfRequestOsstVo.setIfTypeCd(Constants.WORK_CODE_RES);
        msfRequestOsstVo.setRsltCd(Constants.OSST_SUCCESS);*/

        //MSF_REQUEST_OSST 테이블에 저장해야하고 아마 MCP_REQUEST_OSST 에도 저장해야하는 시점.
        //저장이 정상이라면 sendOsstService 를 통해서 희망번호 예약 서비스를 호출함.
        /*if (newChangeService.insertMsfRequestOsst(msfRequestOsstVo)) {
            MSimpleOsstXmlVO simpleOsstXmlVO = null;
            // 희망번호 예약(NU2)
            try {
                Thread.sleep(3000);
                //res_no 값과 희망번호예약이라는 코드를 넘겨서
                simpleOsstXmlVO = this.sendOsstService(sessAppformReqDto.getResNo(),
                        Constants.EVENT_CODE_NUMBER_REG,
                        Constants.WORK_CODE_RES);
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
                return rtnMap;

            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG", e.getMessage());
                return rtnMap;
            } catch (InterruptedException e) {
                logger.error("Exception e : {}", e.getMessage());
            }

        } else {
            throw new McpCommonJsonException("9997", ExceptionMsgConstant.DB_EXCEPTION);
        }*/

        return rtnMap;
    }

    /**
     * 희망번호 취소
     */
    public Map<String, Object> cancelChoiseNumber(NewChangeInfoRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //개통사전체크요청 확인
        // >> 스마트에서 해당 시점에 개통사전체크요청을 어떻게 확인하지? 동일하게 세션으로 관리해야하나? 세션은 날리는 시점을 정해야하는데...
        /*AppformReqDto sessAppformReqDto = SessionUtils.getAppformSession(); //-----> 스마트신청서 : 세션을 프레임웍에 맞춰서 교체해야함
        if (sessAppformReqDto == null) {
            throw new McpCommonJsonException("0003", ExceptionMsgConstant.F_BIND_EXCEPTION);
        }*/

        //MSimpleOsstXmlVO simpleOsstXmlVO = null;
        //번호예약(NU2) 취소
        // >> sendOsstService 예약과 동일한 서비스 호출
        /*try {
            simpleOsstXmlVO = this.sendOsstService(sessAppformReqDto.getResNo(), EVENT_CODE_NUMBER_REG, WORK_CODE_RES_CANCEL);
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
            return rtnMap;

        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("ERROR_MSG", e.getMessage());
            return rtnMap;
        }*/

        //rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        return rtnMap;
    }


}
