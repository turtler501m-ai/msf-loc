package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.commons.common.exception.NotFoundException;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.exception.McpMplatFormException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormOsstServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MPhoneNoListXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlVO;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AbuseImeiHistDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.OsstReqDto;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormCommService {

    private final MsfMplatFormOsstServerAdapter mplatFormOsstServerAdapter;
    private final FormCommReadMapper formCommReadMapper;
    private final FormCommWriteMapper formCommWriteMapper;
    private final NewChangeReadMapper newChangeReadMapper;
    private final McpApiClient mcpApiClient;

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

    //사용자조직에 해당하는 대리점 조회
    public AgentInfoDto getAgentInfo(String shopOrgnId) {
        return Optional.ofNullable(formCommReadMapper.selectAgentInfo(shopOrgnId))
                .orElseThrow(() -> new NotFoundException("조회하고자 하는 매장코드를 입력해주세요. shopOrgnId:" + shopOrgnId));
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
     * MP호출 :: 번호이동 사전동의 결과조회
     */
    public MSimpleOsstXmlVO sendOsstService(OsstReqDto osstReqDto, String eventCd) throws SelfServiceException, SocketTimeoutException {
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

    //MCP_REQUEST_OSST 조회
    //as-is : appformSvc.requestOsstCount(mcpRequestOsstDto);
    public int getMcpRequestOsstCount(McpRequestOsstDto mcpRequestOsstDto) {
        return formCommReadMapper.selectOsstCount(mcpRequestOsstDto);
    }

    //MCP_REQUEST_OSST INSERT
    public boolean setMcpRequestOsst(McpRequestOsstDto mcpRequestOsstDto) {
        return formCommWriteMapper.insertMcpRequestOsst(mcpRequestOsstDto);
    }

    //MCP_REQUEST INSERT & UPDATE
    public boolean mergeMcpRequest(McpRequestDto request) {
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


    //MCP_REQUEST 업데이트
    //as-is : appformSvc.updateMcpRequest(mcpRequestDto)
    /*public boolean setMcpRequest(McpRequestDto request) {

    }*/

    //개통사전체크
    public Map<String, Object> reqPreOpenCheck(NewChangeInfoRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //String globalNoNp1 = request.getGlobalNoNp1();
        //String globalNoNp2 = request.getGlobalNoNp2();

        //개통사전체크요청여부 확인은
        // >> 스마트는 고객이 판매자가 로그인해서 처리하므로 세션으로 확인은 불가하므로 세션정보 없는 경우 영역만 참고하여 처리해야함.
        // 1. 010 신규 유심 셀프개통인 경우, 동일아이피 차단 확인 :: 셀프진행 시 주석해제 후 처리할 것 (조건절도 그때 수정)
        /*if (OPER_TYPE_NEW.equals(request.getOperTypeCd()) && !"09".equals(request.getUsimKindsCd())) {
            int nacSelfIp = appformSvc.getNacSelfCount();
            if (nacSelfIp > 0) {
                throw new McpCommonJsonException("IP_FAIL", NAC_SELF_IP_EXCEPTION);
            }
        }*/
        // 2. 동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회 :: 고객포탈 공통코드 가져오는 것 확인해서 변경해야 함.
        /*int limitReqFormCnt = 100;
        //String limitReqFormCntStr = NmcpServiceUtils.getCodeNm("Constant", "LimitReqFormCnt");
        Optional<CommonCodeData> formTypeCd2 = commonCodeGroups.get("Constant", "LimitReqFormCnt");
        String limitReqFormCntStr = formTypeCd2.get().detail().toString();

        if (limitReqFormCntStr != null && !limitReqFormCntStr.equals("")) {
            try {
                limitReqFormCnt = Integer.parseInt(limitReqFormCntStr);
            } catch (NumberFormatException e) {
                limitReqFormCnt = 100;
            }
        }*/

        //이력이 100 이상이면 체크하지 않음.
        /*int checkLimitOpenFormCount = -1;
        if (limitReqFormCnt < 100) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                //mcp-api 연결 ( 스마트로 옮긴 서비스 호출로 처리해야함. )
                //checkLimitOpenFormCount = restTemplate.postForObject(apiInterfaceServer + "/appform/checkLimitOpenFormCount", appformReqDto, Integer.class);
            } catch (RestClientException e) {
                //이력 정보 저장 처리 >> 공통으로 처리예정
                *//*McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
                mcpIpStatisticDto.setPrcsSbst("9996[RestClientException] ");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                throw new McpCommonJsonException("9996", "API연동 오류 /appform/checkLimitOpenFormCount");*//*
            } catch (Exception e) {
                //이력 정보 저장 처리 >> 공통으로 처리예정
                *//*McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
                mcpIpStatisticDto.setPrcsSbst("9996[Exception] ");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                throw new McpCommonJsonException("9996", "API연동 오류 /appform/checkLimitOpenFormCount");*//*
            }
        }*/

        //이력 정보 저장 처리 ( DB 에서 조회한 정보와 공통코드로 정의한 정보 비교하여 로그 저장 )
        //if (checkLimitOpenFormCount >= limitReqFormCnt) {
            /*McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("FORM_OPEN_LIMIT");
            mcpIpStatisticDto.setPrcsSbst("fCnt[" + checkLimitOpenFormCount + "]lCnt[" + limitReqFormCnt + "]");
            mcpIpStatisticDto.setParameter("COUNT[" + checkLimitOpenFormCount + "]CUSTOMER_SSN[" + appformReqDto.getCstmrNativeRrn() + "]CSTMR_FOREIGNER_RRN[" + appformReqDto.getCstmrForeignerRrn() + "]");
            mcpIpStatisticDto.setTrtmRsltSmst("CHECK");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            rtnMap.put("OSST_RESULT_CODE", "-1");
            rtnMap.put("ERROR_NE_MSG", OVER_LIMIT_OPEN_FORM_EXCEPTION);
            return rtnMap;*/
        //}

        //eSIM 정보 확인 및 설정
        // >> 사전개통동의에서 업로드되는 eSIM 정보까지 불러와서 데이타 세팅을 하는 이유는?
        // >> 스마트에서는 eSIM 이미지 등록해서 임시저장으로 들어오면 어떻게 해야하지?
        //fnSetDataOfeSim(appformReqDto);  >> simInfoService.fnSetDataOfeSim(NewChangeInfoRequest)

        //eSIM 정보로 부정사용주장단말 확인
        /*if (this.checkAbuseImeiList(Arrays.asList(request.getImei1(), request.getImei2()))) {
            //throw new McpCommonJsonException("9901", ESIM_SELF_ABUSE_IMEI_EXCEPTION);
        }*/

        //USIM 또는 eSIM 신규셀프개통 휴대폰 인증 정보확인
        // >> 현재 해당사항은 없어보임.
        /*if (StringUtil.isEmpty(request.getPrntsContractNo())
                && Constants.OPER_TYPE_NEW.equals(request.getOperTypeCd())) {

            // sms 인증 DB 조회 >> 스마트는
            *//*if (StringUtil.isEmpty(request.getReqSeq()) || StringUtil.isEmpty(request.getResSeq())) {
                throw new McpCommonJsonException("0004", NICE_CERT_EXCEPTION);
            }

            NiceLogDto niceLogDto = new NiceLogDto();
            niceLogDto.setReqSeq(appformReqDto.getReqSeq());
            niceLogDto.setResSeq(appformReqDto.getResSeq());
            niceLogDto.setLimitMinute(90);  // 90분 이내의 이력 조회
            smsNiceLogDto = nicelog.getMcpNiceHistWithTime(niceLogDto);

            if (smsNiceLogDto == null) {
                throw new McpCommonJsonException("0004", NICE_CERT_EXCEPTION);
            }*//*
        }*/

        //request.setOnlineAuthInfo("ReqNo:" + sessNiceRes.getReqSeq() + ", ResNo:" + sessNiceRes.getResSeq()); //요것도 조회해오든 여튼 처리해야함.
        //request.setSelfCstmrCi(sessNiceRes.getConnInfo()); //해당사항 없어보임.
        //request.setRip(ipstatisticService.getClientIp()); //Client IP 로 설정필요함.

        //셀프개통 시 동일 명의의 경우 90일 이내 이력 확인 시 차단
        //기존 본인인증시 스크립트에서 체크하나 이후 사전체크 ,개통요청 시 한번 더 체크
        //AppformReqDto rtnAppformReq = appformSvc.getLimitForm(appformReqDto);
        // >> MCP-API 호출 : /appform/limitForm >> 이동한 스마트 경로 호출하여 rtnAppformReq 값 받아오기
        // >> request 가 고객CI 정보임.. 흠...  고객CI정보에 대한 개통  정보 추출 [다회선 제한 기능]
        /*if (rtnAppformReq != null) {
            throw new McpCommonJsonException("9902", SELF_LIMIT_EXCEPTION);
        }*/

        //회원 아이디 설정 >> 고객포탈와 다르게 스마트도 판매자의 아이디?
        /*UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto != null) {
            appformReqDto.setCretId(userSessionDto.getUserId());
        } else {
            appformReqDto.setCretId(NON_MEMBER_ID);
        }*/

        //유심비 /  가입비 설정
        /*String cntpntShopCd = request.getCntpntShopCd();

        UsimBasDto usimBasDtoParm = new UsimBasDto();
        usimBasDtoParm.setOrgnId(cntpntShopCd);
        usimBasDtoParm.setOperType(request.getOperTypeSmall());
        usimBasDtoParm.setDataType(request.getPrdtSctnCd());
        usimBasDtoParm.setRateCd(request.getSocCode());
        //eSIM 처리
        if ("09".equals(request.getUsimKindsCd())) {
            //eSIM
            usimBasDtoParm.setPrdtIndCd("10");
        } else {
            usimBasDtoParm.setPrdtIndCd(request.getUsimKindsCd());
        }*/

        //SIM 관련정보 세팅
        // >> getSimInfo 요 아이도 확인해야하는데, 개통전사전체크 라서 그런데 유심관련 정보 확인이 많다.
        /*Map<String, String> simInfoMap = usimService.getSimInfo(usimBasDtoParm);

        int intJoinPrice = Integer.parseInt(simInfoMap.get("JOIN_PRICE"));
        int intUsimPrice = Integer.parseInt(simInfoMap.get("SIM_PRICE"));

        //eSIM 처리
        if ("09".equals(request.getUsimKindsCd()) || "11".equals(appformReqDto.getUsimKindsCd())) {
            //eSIM
            appformReqDto.setUsimPayMthdCd("3");
            appformReqDto.setUsimPrice(intUsimPrice);
        } else {
            appformReqDto.setUsimPayMthdCd("1");
            appformReqDto.setUsimPrice(0);
        }*/

        /** 가입비 납부방법
         * 1 면제
         * 2 일시납
         * 3 3개월분납
         * 22년... 8월 23일 .. 세희 과장님 하고.. 통화 정리 함..
         * 고객에서 면제 라고 표현 하고 실제로.. M모바일에서 대납 처리 함....
         * 셀프개통은... 1[면제로] 처리
         */
        /*if ("N".equals(simInfoMap.get("JOIN_IS_PAY"))) {
            request.setJoinPayMthdCd("1");
            request.setJoinPrice(0);
        } else {
            //가입비가 0원이면 면제 아니면 3개월 분납
            if (intJoinPrice > 0) {
                request.setJoinPayMthdCd("3");
                request.setJoinPrice(intJoinPrice);
            } else {
                request.setJoinPayMthdCd("1");
                request.setJoinPrice(0);
            }
        }

        //유심구매
        if (REQ_BUY_TYPE_USIM.equals(request.getReqBuyTypeCd()) && !orgnId.equals(cntpntShopId)) {
            NmcpCdDtlDto nmcpCdOnlineDto = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_OFF_ONLINE_LIST, request.getCntpntShopId());
            if (nmcpCdOnlineDto != null && !StringUtils.isBlank(nmcpCdOnlineDto.getExpnsnStrVal1())) {
                request.setCntpntShopId(nmcpCdOnlineDto.getExpnsnStrVal1());
                request.setOpenMarketReferer(nmcpCdOnlineDto.getExpnsnStrVal2());
            }
        }*/

        //정책에서 할인요금 조회에서 등록
        /*if (!StringUtils.isBlank(request.getModelSalePolicyCd())) {
            MspSaleSubsdMstDto mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
            mspSaleSubsdMstDto.setForFrontFastYn("Y");
            mspSaleSubsdMstDto.setSalePlcyCd(request.getModelSalePolicyCd()); //정책코드
            mspSaleSubsdMstDto.setPrdtId(request.getModelId());  //대표상품 아이디
            mspSaleSubsdMstDto.setOldYn("N");
            mspSaleSubsdMstDto.setOrgnId(request.getCntpntShopCd());
            mspSaleSubsdMstDto.setOperType(request.getOperTypeCd());//가입유형
            mspSaleSubsdMstDto.setNoArgmYn("Y");
            mspSaleSubsdMstDto.setAgrmTrm(String.valueOf(request.getEnggMnthCnt()));//입력받은 할부기간을 약정기간
            mspSaleSubsdMstDto.setRateCd(request.getSocCode());//요금제코드

            RestTemplate restTemplate2 = new RestTemplate();
            MspSaleSubsdMstDto[] chargeTemList = restTemplate2.postForObject(apiInterfaceServer + "/msp/mspSaleSubsdMstList", mspSaleSubsdMstDto, MspSaleSubsdMstDto[].class);
            List<MspSaleSubsdMstDto> chargeList = Arrays.asList(chargeTemList);

            if (chargeList != null && chargeList.size() > 0) {
                MspSaleSubsdMstDto saleSubsdMst = chargeList.get(0);
                request.setDcAmt(saleSubsdMst.getDcAmt());  //기본할인금액
                request.setAddDcAmt(saleSubsdMst.getAddDcAmt());  //추가할인금액
                request.setSprtTp(saleSubsdMst.getSprtTp());  //지원금유형
            }
        }*/

        //2. 정보 설정
        //접점코드로 대리점 코드 조회
        /*String agentCode = appformSvc.getAgentCode(request.getCntpntShopCd());

        if (StringUtils.isBlank(agentCode)) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("ERROR_NE_MSG", "대리점 코드가 존재 하지 않습니다.");
            return rtnMap;
        } else {
            request.setAgentCd(agentCode);
        }
        request.setProdType(PhoneConstant.PROD_TYPE_COMMON);
        request.setManagerCode("M0001");
        request.setRid("-");
        request.setViewFlag("Y");
        request.setRequestStateCode("00");

        if (StringUtils.isBlank(request.getShopCd())) {
            request.setShopCd(request.getCntpntShopCd());
        }*/

        //번호이동의 경우
        /*if (Constants.OPER_TYPE_MOVE_NUM.equals(request.getOperTypeCd())) {
            //공통코드 expnsnStrVal3 값이 Y 일때 분납지속(LMS미청구) AD2로 저장 또는
            //기본값은 분납지속(LMS청구)(AD) 저장 처리
            NmcpCdDtlDto moveCompanyObj = NmcpServiceUtils.getCodeNmDto(Constants.WIRE_SERVICE_CODE, request.getMoveCompany());
            if (moveCompanyObj != null) {
                if ("Y".equals(moveCompanyObj.getExpnsnStrVal3())) {
                    //request.setMoveAllotmentStat(Constants.MOVE_ALLOTMENT_STAT_CODE2);
                    request.setMoveAllotmentSttusCd(Constants.MOVE_ALLOTMENT_STAT_CODE2);
                } else {
                    //request.setMoveAllotmentStat(Constants.MOVE_ALLOTMENT_STAT_CODE1);
                    request.setMoveAllotmentSttusCd(Constants.MOVE_ALLOTMENT_STAT_CODE1);
                }
            }
        }*/

        /**
         * ㅇ 이슈사항 : M전산에서 스마트 워치 직접개통(OSST개통) 시 하기와 같은 alert 발생             *
         * ㅇ 발생 오류코드 : 3107  [DEFKTF] 상품은 [애플워치 단말기에서는 휴대폰결제 비밀번호서비스 부가서비스를 가입할 수 없습니다.]사유로 가입이 불가 합니다.
         * ㅇ 확인된 원인
         *  1) PC0(사전체크) 전문의 하기 두 값이 Y로 연동 될 경우 MP에서 '휴대폰결제 비밀번호서비스(MPAYPSSWD)를 자동으로 가입 시킴.
         *  2) 휴대폰결제 비밀번호서비스(MPAYPSSWD) 부가서비스와 스마트워치는 베타관계로, 부가서비스로 자동 가입으로 인한 개통 실패 발생
         * ※ 현재 포탈에서 신청서 작성 시 하기 값을 Null값으로 셋팅하고 있으나 M전산에서는 하기 값이 Null일 경우 "Y"로 신청서 생성 중
         * 셀프 개통 이동전화결제이용동의여부 무조건 N으로 설정
         */
        request.setPhonePaymentYn("N");

        /* 직영 평생할인 프로모션 ID 가져오기 */
        /*String prmtId = appformSvc.getChrgPrmtId(request);
        if (!StringUtils.isBlank(prmtId)) appformReqDto.setPrmtId(prmtId);*/

        /*String cpntId = SessionUtils.getFathSession().getCpntId();
        if (StringUtils.isEmpty(cpntId)) {
            appformReqDto.setCpntId(appformReqDto.getCntpntShopId());
        } else {
            appformReqDto.setCpntId(cpntId);
        }*/

        // (번이) 사전동의 예외 통신사인 경우 NP를 호출하지 않기 때문에 [번호이동전화번호] 값을 가진 본인인증 STEP이 누락됨
        //        → 사전체크 시도 시 해당 STEP 추가
        /*String moveMobileNo = request.getMoveMobileFnNo() + request.getMoveMobileMnNo() + request.getMoveMobileRnNo();
        if (Constants.OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType())) {
            //NmcpCdDtlDto npNscException = NmcpServiceUtils.getCodeNmDto(NP_NSC_EXCEPTION, request.getMpCode());
            // >> 위 공통코드 값은 확인필요함. 모르겠네.
            if (npNscException != null) {
                String[] certKey = new String[]{"urlType", "mobileNo"};
                String[] certValue = new String[]{"reqNpForm", moveMobileNo};
                certService.vdlCertInfo("E", certKey, certValue);
            }
        }*/

        //사전체크 정보 확인 >> [step별 검증] 셀프개통 사전체크 최종 정보 확인
        // >> certService.vdlCertInfo 데이타 검증 서비스를 공통으로 관리하는 방안 검토 필요
        /*Map<String, String> crtResult = appformSvc.crtSaveSimpleAppFormInfo(request);
        if (!AJAX_SUCCESS.equals(crtResult.get("RESULT_CODE"))) {
            rtnMap.put("RESULT_CODE", "STEP01");
            rtnMap.put("ERROR_NE_MSG", crtResult.get("RESULT_DESC"));
            return rtnMap;
        }*/

        // (번이) 동일 번호이동 휴대폰번호로 사전체크 시도 이력 존재시 → 실패처리
        /*if (Constants.OPER_TYPE_MOVE_NUM.equals(request.getOperTypeCd())) {
            Map<String, Object> chkMap = appformSvc.mnpPreCheckLimit(moveMobileNo);
            if (!AJAX_SUCCESS.equals(chkMap.get("RESULT_CODE"))) {
                return chkMap;
            }
        }

        //-------------------------- MSF 테이블 저장 START -------------------------------
        //저장 정보 Session 저장 처리
        // >> saveSimpleAppform 에서 신청서 일련번호 생성하고 예약번호 생성함.
        // >> 위에서 설정한 모든 정보로 DB 저장함. 이미 그 전체 저장된 정보가 있을텐데~ 그건 temp 이므로 원천에 저장해야겠네.
        rtnAppformReqDto = appformSvc.saveSimpleAppform(request);
        rtnAppformReqDto.setNiceLogDto(smsNiceLogDto);
        SessionUtils.saveAppformDto(rtnAppformReqDto);
        */
        //-------------------------- MSF 테이블 저장 END ----------------------------------

        //기존에 신청한 정보가 있다의 처리는 고객포탈은 세션으로 갖고 있으므로 해당 정보로 처리하지만 스마트는 해당사항 없으므로 PASS
        // >> 그래서 조건절이 전체 한 Tab 앞으로 감.

        // 사전동의 예외 통신사인 경우 사전동의 방어로직 통과처리
        /*if (OPER_TYPE_MOVE_NUM.equals(rtnAppformReqDto.getOperType())) {
            NmcpCdDtlDto npNscException = NmcpServiceUtils.getCodeNmDto(NP_NSC_EXCEPTION, appformReqDto.getMpCode());
            if (npNscException == null) {

                *//*
         * 번호 이동
         * NP1 실행 여부 확인
         * NP1 MVNO_ORD_NO GlobalId insert 처리
         *//*
                if ("".equals(globalNoNp1)) {
                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
                    mcpIpStatisticDto.setPrcsSbst("1001[NP1_NULL_EXCEPTION] ");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                    throw new McpCommonJsonException("1001", "번호이동 사전동의 요청(NP1) 정보가 없습니다. ");
                } else {
                    McpRequestOsstDto mcpRequestOsst = new McpRequestOsstDto();
                    mcpRequestOsst.setMvnoOrdNo(rtnAppformReqDto.getResNo());
                    mcpRequestOsst.setNstepGlobalId(globalNoNp1);
                    mcpRequestOsst.setPrgrStatCd(EVENT_CODE_NP_PRE_CHECK);
                    mcpRequestOsst.setRsltCd(OSST_SUCCESS);
                    //NP1 MVNO_ORD_NO GlobalId insert 처리
                    appformSvc.insertMcpRequestOsst(mcpRequestOsst);
                }

                *//*
         * 번호 이동
         * NP3 실행 여부 확인
         * NP4 MVNO_ORD_NO GlobalId insert 처리
         *//*
                if ("".equals(globalNoNp3)) {
                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
                    mcpIpStatisticDto.setPrcsSbst("1002[NP3_NULL_EXCEPTION] ");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                    throw new McpCommonJsonException("1001", "번호이동 사전동의 요청(NP1) 정보가 없습니다. ");
                } else {
                    McpRequestOsstDto mcpRequestOsst = new McpRequestOsstDto();
                    mcpRequestOsst.setMvnoOrdNo(rtnAppformReqDto.getResNo());
                    mcpRequestOsst.setNstepGlobalId(globalNoNp3);
                    mcpRequestOsst.setPrgrStatCd(EVENT_CODE_NP_ARREE);
                    mcpRequestOsst.setRsltCd(OSST_SUCCESS);
                    //NP1 MVNO_ORD_NO GlobalId insert 처리
                    appformSvc.insertMcpRequestOsst(mcpRequestOsst);
                }
            }
        }*/

        //MSimpleOsstXmlVO simpleOsstXmlVO = null;
        /*
         * 방어 로직 추가
         * PC2  0000
         * EVENT_CODE 사전체크 및 고객생성 결과 확인(PC2) 존재하며
         */
        /*McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
        mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_PC_RESULT);
        //mcpRequestOsstDto.setRsltCd("0000");
        int tryCount = appformSvc.requestOsstCount(mcpRequestOsstDto);

        if (tryCount > 0) {
            //성공 처리
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());

            // 010셀프개통대상 해피콜 처리
            NiceLogDto niceLogRtnDto= rtnAppformReqDto.getNiceLogDto();
            if(niceLogRtnDto != null){
                niceLogRtnDto.setRequestKey(rtnAppformReqDto.getRequestKey()+"");
                nicelog.insertSelfSmsAuth(niceLogRtnDto);
            }

            return rtnMap;
        }*/

        //PC0 ITL_SST_E0002
        /*
         * 접수받은 MVNO 접수번호 가 있습니다.
         * 성공 처리
         */
        /*tryCount = 0;
        mcpRequestOsstDto.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK);
        mcpRequestOsstDto.setRsltCd("ITL_SST_E0002");
        tryCount = appformSvc.requestOsstCount(mcpRequestOsstDto);

        if (tryCount > 0) {
            //성공 처리
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());

            // 010셀프개통대상 해피콜 처리
            NiceLogDto niceLogRtnDto= rtnAppformReqDto.getNiceLogDto();
            if(niceLogRtnDto != null){
                niceLogRtnDto.setRequestKey(rtnAppformReqDto.getRequestKey()+"");
                nicelog.insertSelfSmsAuth(niceLogRtnDto);
            }

            return rtnMap;
        }*/

        ////사전체크 및 고객생성 요청(PC0)
        /*try {
            Thread.sleep(3000);
            simpleOsstXmlVO = appformSvc.sendOsstService(rtnAppformReqDto.getResNo(), Constants.EVENT_CODE_PRE_CHECK);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
                rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());

                // 010셀프개통대상 해피콜 처리
                NiceLogDto niceLogRtnDto = rtnAppformReqDto.getNiceLogDto();
                if (niceLogRtnDto != null) {
                    niceLogRtnDto.setRequestKey(rtnAppformReqDto.getRequestKey() + "");
                    nicelog.insertSelfSmsAuth(niceLogRtnDto);
                }

            } else {
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                rtnMap.put("ERROR_NE_MSG", simpleOsstXmlVO.getResultCode());
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("OSST_RESULT_CODE", "-1");//이력 정보 저장 처리
            if ("LOCAL".equals(serverName)) {
                //테스트 용...
                rtnMap.put("OSST_RESULT_CODE", "ITL_SST_E1020_03");
            }
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[McpMplatFormException] ");
            mcpIpStatisticDto.setParameter("RES_NO[" + rtnAppformReqDto.getResNo() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
            return rtnMap;
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            rtnMap.put("OSST_RESULT_CODE", "-2");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");

            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[SocketTimeoutException] ");
            mcpIpStatisticDto.setParameter("RES_NO[" + rtnAppformReqDto.getResNo() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            return rtnMap;
        } catch (SelfServiceException e) {
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("ERROR_MSG", e.getMessage());

            //메세지에 따른 resultCode 변경 처리
            String resultCode = e.getResultCode();
            String message = e.getMessageNe();
            if ("ITL_SST_E1020".equals(resultCode) && message.contains("BF1039")) {
                resultCode = "ITL_SST_E1020_01";
            } else if ("ITL_SST_E1020".equals(resultCode) && message.contains("BF2001")) {
                resultCode = "ITL_SST_E1020_02";
            } else if ("ITL_SST_E1020".equals(resultCode) && message.contains("BS0000")) {
                resultCode = "ITL_SST_E1020_03";
            }

            rtnMap.put("OSST_RESULT_CODE", resultCode);
            rtnMap.put("ERROR_NE_MSG", message);

            if ("LOCAL".equals(serverName)) {
                int searchCnt = 0;
                searchCnt = SessionUtils.getCntSession();
                SessionUtils.saveCntSession(++searchCnt);

                rtnMap.put("OSST_RESULT_CODE", "ITL_SST_E1020_03");
                //강제 성공 처리
                *//*if (searchCnt > 1) {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
                    rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
                }*//*
            }
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "-2");
            rtnMap.put("ERROR_MSG", "Exception");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요..");

            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[Exception] ");
            mcpIpStatisticDto.setParameter("RES_NO[" + rtnAppformReqDto.getResNo() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            return rtnMap;
        }*/


        return rtnMap;
    }


}
