package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormOsstServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoListXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.common.dto.McpRequestOsstRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MnpOsstRequest;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.*;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeWriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FormCommService {

    private final MsfMplatFormOsstServerAdapter mplatFormOsstServerAdapter;
    private final FormCommReadMapper formCommReadMapper;
    private final FormCommWriteMapper formCommWriteMapper;
    private final NewChangeReadMapper newChangeReadMapper;
    private final McpApiClient mcpApiClient;
    private final ProductSmartInfoReadMapper productSmartInfoReadMapper;
    private final McpRequestWriteMapper mcpRequestWriteMapper;
    private final NewChangeWriteMapper newChangeWriteMapper;


    //private final CommonCodeReader commonCodeReader;
    //private final CommonCodeGroups commonCodeGroups;

    //SQ_RCP_RES_NO_01 생성 ( MSF_REQUEST.RES_NO )
    public String generateResNo() {
        //return formCommMapper.generateResNo();
        return newChangeReadMapper.generateSmartResNo(); //스마트에서 오픈전까지만 임시로 사용
    }

    //SQ_RCP_REQUEST_KEY_01 생성 ( MSF_REQUEST.REQUEST_KEY )
    public long generateRequestKey() {
        //return formCommMapper.generateRequestKey();
        return newChangeReadMapper.generateSmartRequestKey(); //스마트에서 오픈전까지만 임시로 사용
    }

    //NMCP_CUST_REQUEST_SEQ 생성
    public long getCustRequestSeq() {
        //return formCommMapper.getCustRequestSeq();
        return newChangeReadMapper.getSmartCustRequestSeq(); //스마트에서 오픈전까지만 임시로 사용
    }

    /**
     * 사용자조직에 해당하는 대리점 조회
     */
    public AgentInfoResponse getAgentList2(AgentInfoRequest request) {
        AgentInfoResponse responseDto = formCommReadMapper.selectAgentInfo2(request);
        return responseDto;
    }

    //@@삭제필요@@
    public List<AgentInfoResponse> getAgentList(AgentInfoRequest request) {
        List<AgentInfoResponse> responseDto = formCommReadMapper.selectAgentInfo(request);
        return responseDto;
    }

    /**
     * MP호출 :: 희망번호 조회
     **/
    public MPhoneNoListXmlVO selectPhoneNoList(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException {
        MPhoneNoListXmlVO mPhoneNoListXmlVO = new MPhoneNoListXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        mplatFormOsstServerAdapter.callService(param, mPhoneNoListXmlVO, 100000);
        return mPhoneNoListXmlVO;
    }

    /**
     * MP호출 :: 희망번호 예약
     **/
    public MSimpleOsstXmlVO sendOsstService(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException, McpMplatFormException {
        MSimpleOsstXmlVO simpleOsstXmlVO = new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlVO, 100000);
        return simpleOsstXmlVO;
    }

    /**
     * MP호출 :: 번호이동 사전동의 요청
     */
    public MSimpleOsstXmlVO sendOsstService(MnpOsstRequest osstReqDto, String eventCd) throws SelfServiceException, SocketTimeoutException {
        MSimpleOsstXmlVO simpleOsstXmlVO = new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();

        param.put("appEventCd", eventCd);
        param.put("npTlphNo", osstReqDto.getNpTlphNo());
        param.put("moveCompany", osstReqDto.getBchngNpCommCmpnCd());
        param.put("cstmrType", osstReqDto.getCustTypeCd());
        param.put("selfCertType", osstReqDto.getCustIdntNoIndCd());
        param.put("custIdntNo", osstReqDto.getCustIdntNo());
        param.put("cstmrName", osstReqDto.getCustNm());
        param.put("cntpntShopId", osstReqDto.getSlsCmpnCd());

        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlVO, 100000);
        return simpleOsstXmlVO;
    }


    //부정사용주장 단말 확인 (메인 메소드) - imei 갯수만큼 돌겠지요. 해봤자 2개니까~~ 최대 2회
    //사용 : 휴대폰 일련번호 유효성체크 / eSIM 유효성체크
    public boolean checkAbuseImeiList(List<String> imeis) {
        boolean isAbuse = false;

        for (String imei : imeis) {
            if (StringUtils.isEmpty(imei)) {
                continue;
            }

            //부정사용주장 단말 확인 후 부정사용단말인 경우 NMCP_ABUSE_IMEI_HIST 테이블에 저장
            isAbuse = this.existsAbuseImei(imei);
            if (isAbuse) {
                this.saveAbuseImeiHist(imei);
                break;
            }
        }
        return isAbuse;
    }

    //부정사용주장 단말 조회 >> 어디로 따로 옮겨야하려나?
    //apiInterfaceServer + "/appform/existsAbuseImei" >> parameter :: iccId
    //사용 : 휴대폰 일련번호 유효성체크
    private boolean existsAbuseImei(String imei) {
        boolean exits = false;
        exits = mcpApiClient.post(
                "/appform/existsAbuseImei",
                imei.toString(),
                Boolean.class
        );
        return exits;
    }

    //부정사용 주장 저장
    //사용 : 휴대폰 일련번호 유효성체크
    private void saveAbuseImeiHist(String imei) {
        //스마트 로그인한 사용자 아이디로 저장필요
        String userId = ""; //개발기준 USER_ID 빈값으로 필수값 아님
        AbuseImeiHistDto abuseImeiHistDto = new AbuseImeiHistDto();
        abuseImeiHistDto.setImei(imei);
        //abuseImeiHistDto.setAccessIp(ipstatisticService.getClientIp()); //ipstatisticService 를 공통으로 처리할 필요가 있음. 에러발생으로 주석처리
        abuseImeiHistDto.setAccessIp("192.168.0.1");
        abuseImeiHistDto.setUserId(userId);
        formCommWriteMapper.insertAbuseImeiHist(abuseImeiHistDto);

        /*UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession != null) {
            userId = userSession.getUserId();
        }*/

        /*AbuseImeiHistDto abuseImeiHistDto = new AbuseImeiHistDto();
        abuseImeiHistDto.setImei(imei);
        abuseImeiHistDto.setAccessIp(ipstatisticService.getClientIp());
        abuseImeiHistDto.setUserId(userId);
        esimDao.insertAbuseImeiHist(abuseImeiHistDto);*/
    }

    //불량유심 사용제한 확인
    //사용 : USIM 유효성체크
    public int getFailUsims(String iccId) {
        int failUsimCnt = 0;
        failUsimCnt = mcpApiClient.post(
                "/storeUsim/failUsim",
                iccId.toString(),
                int.class
        );
        return failUsimCnt;

        //apiInterfaceServer + "/storeUsim/failUsim" >> parameter :: iccId
        //건수를 받음.
    }

    //불량유심 사용제한한 사용자 업데이트 (스마트에도 해당이 되려나? 사용자는 실제 가입하려는 사람이 아니라 판매자인데..)
    //사용 : USIM 유효성체크
    public int setFailUsims(String iccId) {
        int updateFailUsim = 0;
        updateFailUsim = mcpApiClient.post(
                "/storeUsim/updateFailUsim",
                iccId.toString(),
                int.class
        );
        return updateFailUsim;

        //apiInterfaceServer + "/storeUsim/updateFailUsim" >> parameter :: iccId
        //건수를 받음.
    }

    //명의도용 추가피해 방지를 위한 유심재사용 확인
    //사용 : USIM 유효성체크
    public int checkValidUsimNo(String iccId) {
        int vaildUsimCnt = 0;
        vaildUsimCnt = mcpApiClient.post(
                "/appform/checkValidUsimNo",
                iccId.toString(),
                int.class
        );
        return vaildUsimCnt;

        //apiInterfaceServer + "//appform/checkValidUsimNo" >> parameter :: iccId
        //건수를 받음.
    }

    //USIM 접점코드 조회
    //사용 : USIM 유효성체크
    public String getUsimOrgnId(String iccId) {
        String orgnId = "";
        orgnId = mcpApiClient.post(
                "/msp/sellUsimMgmtOrgnId",
                iccId.toString(),
                String.class
        );
        return orgnId;

        //apiInterfaceServer + "/msp/sellUsimMgmtOrgnId" >> parameter :: iccId
        //건수를 받음.
    }

    //MCP_REQUEST_OSST 에서 조건에 맞는 건수 확인하기
    //as-is : appformSvc.requestOsstCount(mcpRequestOsstDto);
    public int getMcpRequestOsstCount(McpRequestOsstRequest request) {
        return formCommReadMapper.selectOsstCount(request);
    }

    //MCP_REQUEST_OSST 에서 OSST_ORD_NO 조회
    public String getOsstOrdNo(McpRequestOsstRequest request) {
        return formCommReadMapper.selectOsstOrdNo(request);
    }

    //MCP_REQUEST_OSST INSERT
    public boolean setMcpRequestOsst(McpRequestOsstDto mcpRequestOsstDto) {
        return formCommWriteMapper.insertMcpRequestOsst(mcpRequestOsstDto);
    }

    //MCP_REQUEST INSERT & UPDATE
    public boolean mergeMcpRequest(McpRequestOsstRequest request) {
        return formCommWriteMapper.mergeMcpRequest(request);
    }

    //MP호출 : 신규가입 희망번호 목록 조회
    public MPhoneNoListXmlVO getPhoneNoList(String resNo, String eventCd) throws SelfServiceException, SocketTimeoutException {
        MPhoneNoListXmlVO mPhoneNoListXmlVO = new MPhoneNoListXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        mplatFormOsstServerAdapter.callService(param, mPhoneNoListXmlVO, 100000);
        return mPhoneNoListXmlVO;
    }

    //MP호출 : 신규가입 희망번호 예약 또는 취소
    public MSimpleOsstXmlVO sendOsstService(String resNo, String eventCd, String gubun) throws SelfServiceException, SocketTimeoutException {
        MSimpleOsstXmlVO simpleOsstXmlVO = new MSimpleOsstXmlVO();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("appEventCd", eventCd);
        param.put("resNo", resNo);
        param.put("gubun", gubun);
        mplatFormOsstServerAdapter.callService(param, simpleOsstXmlVO, 100000);
        return simpleOsstXmlVO;
    }


    //개통전 사전체크
    public Map<String, Object> checkOsstPreCheck(NewChangeInfoRequest request) {
        boolean isValidOsstPreCheck = false; //데이터 유효성체크
        String resNo = ""; //고객포탈에 데이터 확인 및 저장
        Map<String, Object> osstPreCheckRtnMap = new HashMap<>(); //개통전 사전체크 진행결과

        //0. 개통전 사전체크 0단계 : 데이터 유효성체크
        isValidOsstPreCheck = this.checkOsstPreCheckData(request);
        if (!isValidOsstPreCheck) { //유효하지 않은 신청서 데이터
            return osstPreCheckRtnMap;
        }

        //1. 개통전 사전체크 1단계 : 예약번호로 이벤트코드 PC0 존재여부 확인 후 고객포탈 데이타에 저장하고 예약번호 리턴
        resNo = this.validPreCheck(request);
        request.setResNo(resNo);
        /*if (!StringUtils.isEmpty(resNo)) { //유효하지 않은 예약번호
            return osstPreCheckRtnMap;
        }*/

        //2. 개통전 사전체크 2단계
        osstPreCheckRtnMap = this.reqPreOpenCheck(request);

        return osstPreCheckRtnMap;
    }

    //개통전 사전체크 0단계 : 데이타 유효성체크
    public boolean checkOsstPreCheckData(NewChangeInfoRequest request) {
        boolean result = true;
        return result;
        //1. 개통전 사전체크 여부 확인은 MSF_REQUEST_TEMP.RES_NO 값 조회
        //   없을 경우 MSF_REQUEST_TEMP.RES_NO 값 생성
        //2. 동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회
        //   추후 처리
        //3. 이력정보 저장
        //   ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        //4. eSIM 정보 체크
        //   데이타 설정 , eSIM 정보로 부정사용주장단말 확인
        //5. 인증 정보 확인??
        //   미성년자의 나이 등 체크.
        //6. 유심비 /  가입비 설정
        //7. 정책에서 할인요금 조회에서 등록
        //8. 접점코드로 대리점 코드 조회
    }

    //OSST 테이블 데이터 조회 (PrgrStatCd : 서비스코드가 입력됨)
    public int getOsstCount(McpRequestOsstRequest request) {
        int osstCount = 0;
        if (StringUtils.hasText(request.getPrgrStatCd())) {
            osstCount = formCommReadMapper.selectOsstCount(request);
        }

        return osstCount;
    }

    //개통전 사전체크 1단계 : 고객포탈에 데이타 저장
    public String validPreCheck(NewChangeInfoRequest request) {
        String resNo = newChangeReadMapper.getMsfResNo(request.getRequestKey());
        if (StringUtils.hasText(resNo)) {
            return resNo;
        } else {
            resNo = formCommReadMapper.generateResNo();
        }

        int osstCount = 0;
        McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setMvnoOrdNo(resNo);
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK);
        //번호이동 및 신규가입일 경우에 개통전 사전체크 진행여부 확인
        if (Constants.OPER_TYPE_MOVE_NUM.equals(request.getOperTypeCd())
                || Constants.OPER_TYPE_NEW.equals(request.getOperTypeCd())) {
            osstCount = this.getOsstCount(mcpRequestOsstRequest);
        }

        //개통전 사전체크 진행을 위한 데이타 저장
        if (osstCount > 0) {
            return resNo;
        } else {
            MsfRequestRecord record = MsfRequestRecord.requestToRecord(request);

            McpRequestVo mcpRequestVo = new McpRequestVo();
            McpRequestCstmrVo mcpRequestCstmrVo = new McpRequestCstmrVo();

            BeanUtils.copyProperties(record.msfRequestVo(), mcpRequestVo);
            BeanUtils.copyProperties(record.msfRequestCstmrVo(), mcpRequestCstmrVo);
            //mcpRequestWriteMapper.insertMcpRequest(mcpRequestVo); //MCP_REQUEST
            //mcpRequestWriteMapper.insertMcpRequestCstmr(mcpRequestCstmrVo); //MCP_REQUEST_CSTMR
        }

        return resNo;
    }

    //개통전 사전체크 2단계 : M플랫폼으로 PC0 호출
    public Map<String, Object> reqPreOpenCheck(NewChangeInfoRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //String globalNoNp1 = request.getGlobalNoNp1();
        //String globalNoNp2 = request.getGlobalNoNp2();

        //개통전 사전체크
        //   as-is : /appform/reqPreOpenCheckAjax.do 에서 appformSvc.saveSimpleAppform(appformReqDto); 를 통해
        //           고객포탈의 MCP_REQUEST , MCP_REQUEST_CSTMR , MCP_REQUEST_SALEINFO , MCP_REQUEST_MOVE 데이타를 저장
        //           사전체크 진행 시 prx 에서 mcp-api 로 조회 ( /mPlatform/getXmlMessagePC0 ) 하여 PC0 호출함.
        //           RES_NO 는 고객포탈 데이타 저장 시 생성하여 가지고 가서 저장해둔다.
        //   to-be : 개통전 사전체크는 스마트 고객정보 저장 시 진행하도록 한다.
        //           연동되는 내용은 번호이동 및 희망번호 관련 정보외에는 변경되지 않을 고객정보와 이용약관동의의 동의여부 정도로 보이기 때문.
        //           [0] 고객단계에서 스마트 저장 시 고객포탈에도 데이타를 저장하고 개통전 사전체크를 진행한다.
        //           [1] 고객단계에서 개통전 사전체크를 진행한다는 가정하에 번호이동 사전동의 시에는 신청서번호로 예약번호를 조회하도록 한다.
        //           [2] 조회된 예약번호로 MCP_REQUEST_OSST 테이블에 해당 이벤트코드 존재여부를 확인한다.

        String resNo = request.getResNo();
        System.out.println("resNo ======================== : " + resNo);
        //신청서 예약번호가 제대로 넘어오지 않은 경우
        if (!StringUtils.hasText(resNo)) {
            return null;
        }

        //사전체크 및 고객생성 요청(PC0)
        MSimpleOsstXmlVO simpleOsstXmlVO = new MSimpleOsstXmlVO();
        try {
            if (StringUtils.hasText(resNo)) {
                simpleOsstXmlVO = this.sendOsstService(resNo, Constants.EVENT_CODE_PRE_CHECK);

                if (simpleOsstXmlVO.isSuccess()) {
                    //rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    //rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
                    //rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());

                    //개통전 사전체크용 데이터 저장이 완료되었다면 스마트에도 예약번호 업데이트
                    //request.setResNo(resNo);
                    //newChangeWriteMapper.updateMsfRequestInfo(request);
                } else {
                    //rtnMap.put("RESULT_CODE", "0001");
                    //rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                    //rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                    //rtnMap.put("ERROR_NE_MSG", simpleOsstXmlVO.getResultCode());
                }
            }
        } catch (McpMplatFormException e) {
            //서비스 연동 이력 정보 저장 처리
//            rtnMap.put("RESULT_CODE", "9997");
//            rtnMap.put("ERROR_MSG", "response massage is null.");
//            rtnMap.put("OSST_RESULT_CODE", "-1");//이력 정보 저장 처리
//            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
//            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
//            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
//            mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
//            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
//            mcpIpStatisticDto.setPrcsSbst("Exception[McpMplatFormException] ");
//            mcpIpStatisticDto.setParameter("RES_NO[" + rtnAppformReqDto.getResNo() + "]");
//            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
//            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
//            return rtnMap;
        } catch (SocketTimeoutException e) {
            //서비스 연동 이력 정보 저장 처리
//            rtnMap.put("RESULT_CODE", "9999");
//            rtnMap.put("ERROR_MSG", "SocketTimeout");
//            rtnMap.put("OSST_RESULT_CODE", "-2");
//            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
//            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
//            mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
//            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
//            mcpIpStatisticDto.setPrcsSbst("Exception[SocketTimeoutException] ");
//            mcpIpStatisticDto.setParameter("RES_NO[" + rtnAppformReqDto.getResNo() + "]");
//            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
//            return rtnMap;
        } catch (SelfServiceException e) {
            //서비스 연동 이력 정보 저장 처리
//            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
//            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
//            rtnMap.put("RESULT_CODE", "9998");
//            rtnMap.put("ERROR_MSG", e.getMessage());

            //메세지에 따른 resultCode 변경 처리
//            String resultCode = e.getResultCode();
//            String message = e.getMessageNe();
//            if ("ITL_SST_E1020".equals(resultCode) && message.contains("BF1039")) {
//                resultCode = "ITL_SST_E1020_01";
//            } else if ("ITL_SST_E1020".equals(resultCode) && message.contains("BF2001")) {
//                resultCode = "ITL_SST_E1020_02";
//            } else if ("ITL_SST_E1020".equals(resultCode) && message.contains("BS0000")) {
//                resultCode = "ITL_SST_E1020_03";
//            }

//            rtnMap.put("OSST_RESULT_CODE", resultCode);
//            rtnMap.put("ERROR_NE_MSG", message);
        } catch (Exception e) {
            //서비스 연동 이력 정보 저장 처리
//            rtnMap.put("RESULT_CODE", "-2");
//            rtnMap.put("ERROR_MSG", "Exception");
//            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요..");
//            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
//            mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
//            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
//            mcpIpStatisticDto.setPrcsSbst("Exception[Exception] ");
//            mcpIpStatisticDto.setParameter("RES_NO[" + rtnAppformReqDto.getResNo() + "]");
//            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
//            return rtnMap;
        }

        //성공했을때만 업데이트이나~ 임시로 무조건 저장
        request.setResNo(resNo);
        newChangeWriteMapper.updateMsfRequestInfo(request);

        return rtnMap;
    }

//            mcpRequestVo.setManagerCode(record.msfRequestVo().getManagerCd());
//            mcpRequestVo.setAgentCode(record.msfRequestVo().getAgentCd());
//            mcpRequestVo.setServiceType(record.msfRequestVo().getServiceTypeCd());
//            mcpRequestVo.setReqBuyType(record.msfRequestVo().getReqBuyTypeCd());
//            mcpRequestVo.setOperType(record.msfRequestVo().getOperTypeCd());
//            mcpRequestVo.setCstmrType(record.msfRequestVo().getCstmrTypeCd());
//            mcpRequestVo.setResCode("");
//            mcpRequestVo.setResMsg("");
//            mcpRequestVo.setResNo("");
//            mcpRequestVo.setClausePriCollectFlag(record.msfRequestVo().getClausePriCollectYn());
//            mcpRequestVo.setClauseConfidenceFlag(record.msfRequestVo().getClauseConfidenceYn());
//            mcpRequestVo.setClause5gCoverageFlag(record.msfRequestVo().getClause5gCoverageYn());
//            mcpRequestVo.setClauseEssCollectFlag(record.msfRequestVo().getClauseEssCollectYn());
//            mcpRequestVo.setClauseFathFlag(record.msfRequestVo().getClauseFathYn());
//            mcpRequestVo.setClauseEssCollectFlag(record.msfRequestVo().getClauseEssCollectYn());
//            mcpRequestVo.setClauseInsrProdFlag(record.msfRequestVo().getClauseInsrProdYn());
//            mcpRequestVo.setClauseJehuFlag(record.msfRequestVo().getClauseJehuYn());
//            mcpRequestVo.setClausePartnerOfferFlag(record.msfRequestVo().getClausePartnerOfferYn());
//            mcpRequestVo.setClausePriCollectFlag(record.msfRequestVo().getClausePriCollectYn());
//            mcpRequestVo.setClauseFinanceFlag(record.msfRequestVo().getClauseFinanceYn());
//            mcpRequestVo.setClauseInsuranceFlag(record.msfRequestVo().getClauseInsuranceYn());
//            mcpRequestVo.setClauseSensiOfferFlag(record.msfRequestVo().getClauseSensiOfferYn());
//            mcpRequestVo.setClausePriOfferFlag(record.msfRequestVo().getClausePriOfferYn());
//            mcpRequestVo.setClauseMpps35Flag(record.msfRequestVo().getClauseMpps35Yn());
//            mcpRequestVo.setClausePriAdFlag(record.msfRequestVo().getClausePriAdYn());
//            mcpRequestVo.setClausePriTrustFlag(record.msfRequestVo().getClausePriTrustYn());
//            mcpRequestVo.setClauseRentalModelCp(record.msfRequestVo().getClauseRentalModelCpYn());
//            mcpRequestVo.setClauseRentalService(record.msfRequestVo().getClauseRentalServiceYn());
//            mcpRequestVo.setClauseRentalModelCpPr(record.msfRequestVo().getClauseRentalModelCpPrYn());
//            mcpRequestVo.setOnlineAuthType("");
//            mcpRequestVo.setOnlineAuthInfo("");
//            mcpRequestVo.setPstate("");
//            mcpRequestVo.setRequestStateCode("");
//            mcpRequestVo.setOpenNo("");
//            mcpRequestVo.setFile01("");
//            mcpRequestVo.setFile01Mask("");
//            mcpRequestVo.setFaxyn("");
//            mcpRequestVo.setFaxnum("");
//            mcpRequestVo.setScanId("");
//            mcpRequestVo.setOnOffType("");
//            mcpRequestVo.setRip("");
//            mcpRequestVo.setOpenReqDate("");
//            mcpRequestVo.setReqWantNumber("");
//            mcpRequestVo.setReqWantNumber2("");
//            mcpRequestVo.setReqWantNumber3("");
//            mcpRequestVo.setReqModelName("");
//            mcpRequestVo.setReqModelColor("");
//            mcpRequestVo.setReqPhoneSn("");
//            mcpRequestVo.setReqUsimSn("");
//            mcpRequestVo.setReqUsimName("");
//            mcpRequestVo.setReqPayType("");
//            mcpRequestVo.setReqAddition("");
//            mcpRequestVo.setReqAdditionPrice("");
//            mcpRequestVo.setShopCd("");
//            mcpRequestVo.setShopNm("");
//            mcpRequestVo.setContractNum("");
//            mcpRequestVo.setEtcSpecial("");
//            mcpRequestVo.setPstateReasonEtc("");
//            mcpRequestVo.setPhonePayment("");
//            mcpRequestVo.setCntpntShopId("");
//            mcpRequestVo.setProdId("");
//            mcpRequestVo.setSntyColorCd("");
//            mcpRequestVo.setSntyCapacCd("");
//            mcpRequestVo.setInsrCd("");
//@Column(name = "req_buy_type") // DB의 컬럼명을 여기에 적습니다.
//private String reqBuyTypeCd;


}
