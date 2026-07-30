package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.CstmrType;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MnpOsstRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MnpOsstResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNP1Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNP3Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNP3Response;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;

import static com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType.NUMBER_PORTABLE_REQ;
import static com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType.NUMBER_PORTABLE_RESULT;

@Slf4j
@Service
@RequiredArgsConstructor
public class NumberPortableService {

    private final MsfMcpOsstPrxService msfMcpOsstPrxService;
    private final NewChangeReadMapper newChangeReadMapper;

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
        //MnpOsstResponse responseDto = new MnpOsstResponse();
        log.debug(
            "★ 번호이동 사전동의 요청 ★ npTlphNo: {}, bchngNpCommCmpnCd: {}, slsCmpnCd: {}, cstmrTypeCd: {}, indvBizrYn: {}, custIdntNoIndCd: {}, custIdntNo: {}, crprNo: {}, custNm: {}, fornBrthDate: {}, agentCd: {}",
            request.getNpTlphNo(),
            request.getBchngNpCommCmpnCd(),
            request.getSlsCmpnCd(),
            request.getCstmrTypeCd(),
            request.getIndvBizrYn(),
            request.getCustIdntNoIndCd(),
            request.getCustIdntNo(),
            request.getCrprNo(),
            request.getCustNm(),
            request.getFornBrthDate(),
            request.getAgentCd());

        //대리점코드는 화면에서 선택한 값을 우선적으로 처리하고 없다면 로그인 세션값을 가져오도록 함.
        //String agentCd = request.getAgentCd();
        Long requestKey = request.getRequestKey();


        //parameter ( MnpOsstRequest )
        //requestKey            : 신청서 일련번호
        //NpTlphNo              : [암호화] 번호이동 전화번호 >> 01098761234
        //BchngNpCommCmpnCd     : 변경전번호이동사업자코드 ( NP_COMM_CMPN_CD ) >> SKT
        //CustTypeCd            : 고객유형코드 ( CUST_TYPE_CD ) - 실제는 그렇지만 우린 그렇게 안 보냄
        //cstmrTypeCd           : front 에서 전달하는 값
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

        //1. 번호이동 테이블 내에 특정기간 존재여부 확인 >> 처리해야함.
        //   요청한 핸드폰번호(moveMobileNo)로 특정기간(limitDay) 내에 MCP_REQUEST , MCP_REQUEST_MOVE 테이블에 존재여부 확인
        //   신청기간 내에 없을 경우 개통전 사전체크 (PC0)는 확인하지 않고 번호이동 사전체크 요청을 진행함.

        //2. 번호이동 사전체크 요청 여부 확인 >> NP2 에서만 사용하여 주석처리
        //   요청한 핸드폰번호(moveMobileNo)로 특정기간(limitDay) 내에 MCP_REQUEST_OSST 테이블에 존재여부 확인
        //   MCP_REQUEST_OSST.MVNO_ORD_NO 확인
        //Map<String, Object> chkMap = this.mnpPreCheckLimit(request.getNpTlphNo());
        //if (!Constants.AJAX_SUCCESS.equals(chkMap.get("RESULT_CODE"))) {
        //    return FormResponse.of(ResponseMessage.VALID_REQ_NP_PRECHECK_SUCCESS, responseDto);
        //}

        // 개통전 사전체크 안한 경우 (MCP_REQUEST 및 MCP_REQUEST_OSST 에 데이타 없음)
        // 1. REQUEST_KEY >> FRONT 에서 넘김
        // 2. RES_NO >>
        // 개통전 사전체크 한 경우 (MCP_REQUEST 및 MCP_REQUEST_OSST 에 데이타 있음)
        // 1. REQUEST_KEY >>
        // 2. MVNO_ORD_NO >>
        // 3. OSST_ORD_NO >>

        //RES_NO 추출 (개통전 사전체크 안한 경우)
        //MSF_REQUEST_TEMP 에서 RES_NO 추출 ( REQUEST_KEY )
        String resNo = newChangeReadMapper.getMsfResNo(requestKey);

        //RES_NO 추출 (개통전 사전체크 한 경우)
        //MCP_REQUEST_OSST 에서 MVNO_ORD_NO 추출 ( REQUEST_KEY )
        //McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();
        //mcpRequestOsstRequest.setRequestKey(request.getRequestKey());
        //mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK); //PC0
        ////mcpRequestOsstRequest.setRsltCd(Constants.OSST_SUCCESS); //OSST 연동 성공 - PC2 에만 옴
        //String resNo = formCommService.getMvnoOrdNo(mcpRequestOsstRequest);

        //custTypeCd : SELECT ETC2, CD_VAL, CD_DSC FROM CMN_GRP_CD_MST@DL_MSP WHERE GRP_ID = 'RCP9007';
        //NA(내국인), NM(내국인(미성년자)), FN(외국인), 코드없음(외국인 미성년자), JP(법인사업자), NE(기타), 코드없음(공공기관), PP(개인사업자)
        //스마트 고객유형 구분코드 'CSTMR_TYPE_CD' 는 외국인 미성년자 FM, 공공기관 GO 는 치환해서 연동
        //String cstmrTypeCd = request.getCstmrTypeCd();
        //if (cstmrTypeCd.equals(CstmrType.FOREIGN_MINOR)) {
        //    cstmrTypeCd = CstmrType.FOREIGN_ADULT.getCode();
        //} else if (cstmrTypeCd.equals(CstmrType.GOVERNMENT_ORGANIZATION)) {
        //    cstmrTypeCd = "NE";
        //}

        //고객유형별 CSTMR_TYPE_CD >> CUST_TYPE_CD 변환
        String cstmrTypeCd = request.getCstmrTypeCd();
        String custTypeCd = "";
        if (cstmrTypeCd.equals(CstmrType.JURIDICAL_PERSON.getCode())) {
            custTypeCd = "B";
        } else if (cstmrTypeCd.equals(CstmrType.GOVERNMENT_ORGANIZATION.getCode())) {
            custTypeCd = "G";
        } else {
            custTypeCd = "I";
        }

        //번호이동 사전동의 요청 (NP1)
        String globalNo = ""; //PRX 연동 결과 - globalNo
        String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
        String responseCode = ""; //PRX 연동 결과 - responseCode
        String responseBasic = "";

        MplatFormNP1Request mplatFormNP1Request = new MplatFormNP1Request();
        mplatFormNP1Request.setNpTlphNo(request.getNpTlphNo());
        mplatFormNP1Request.setBchngNpCommCmpnCd(request.getBchngNpCommCmpnCd());
        //mplatFormNP1Request.setSlsCmpnCd("INL"); //INL : ITL_SST_E0001(String), 작업 요청 판매회사 코드와 입력 판매회사 코드가 다릅니다.
        mplatFormNP1Request.setSlsCmpnCd("KIS"); //KIS : 정상
        mplatFormNP1Request.setCustTypeCd(custTypeCd);
        mplatFormNP1Request.setIndvBizrYn(request.getIndvBizrYn());
        mplatFormNP1Request.setCustIdntNoIndCd(request.getCustIdntNoIndCd());
        mplatFormNP1Request.setCustIdntNo(request.getCustIdntNo());
        mplatFormNP1Request.setCrprNo(request.getCrprNo());
        mplatFormNP1Request.setCustNm(request.getCustNm());
        mplatFormNP1Request.setFornBrthDate(request.getFornBrthDate());

        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(mplatFormNP1Request),
            NUMBER_PORTABLE_REQ.getEventCd(), resNo);

        responseType = mspPrxSoapResponse.responseType(); //PRX 연동 결과 - responseType (N / E 등)
        responseCode = mspPrxSoapResponse.responseCode(); //PRX 연동 결과 - responseCode
        responseBasic = mspPrxSoapResponse.responseBasic();
        globalNo = mspPrxSoapResponse.globalNo(); //PRX 연동 결과 - globalNo

        log.debug("responseType: {}, responseCode:{}, responseBasic: {}, globalNo: {}", responseType, responseCode, responseBasic, globalNo);

        if ("N".equals(responseType)) {
            return FormResponse.of(ResponseMessage.VALID_REQ_NP_PRECHECK_SUCCESS);
        }
        return FormResponse.of(ResponseMessage.VALID_REQ_NP_PRECHECK_FAIL);
    }

    /**
     * 번호이동 사전동의 결과조회 : NP3
     **/
    public FormResponse<MnpOsstResponse> requestNpAgree(MnpOsstRequest request) {
        //MnpOsstResponse responseDto = new MnpOsstResponse();

        log.debug("★ 번호이동 사전동의 결과조회 ★ npTlphNo: {}, bchngNpCommCmpnCd: {}, agentCd: {}",
            request.getNpTlphNo(),
            request.getBchngNpCommCmpnCd(),
            request.getAgentCd());

        String agentCd = request.getAgentCd(); //대리점코드는 화면에서 선택한 값으로 처리
        Long requestKey = request.getRequestKey();
        String npTlphNo = request.getNpTlphNo();
        //String bchngNpCommCmpnCd = request.getBchngNpCommCmpnCd();

        //0. 입력 데이타 검증
        if (requestKey < 0L || !StringUtils.hasText(agentCd) || npTlphNo.length() < 11) {
            return FormResponse.of(ResponseMessage.VALID_REQ_NP_AGREE_FAIL); //
        }

        //RES_NO 추출 (개통전 사전체크 안한 경우)
        //MSF_REQUEST_TEMP 에서 RES_NO 추출 ( REQUEST_KEY )
        String resNo = newChangeReadMapper.getMsfResNo(requestKey);

        //RES_NO 추출 (개통전 사전체크 한 경우)
        //MCP_REQUEST_OSST 에서 MVNO_ORD_NO 추출 ( REQUEST_KEY )
        //McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();
        //mcpRequestOsstRequest.setRequestKey(request.getRequestKey());
        //mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK); //PC0
        // //mcpRequestOsstRequest.setRsltCd(Constants.OSST_SUCCESS); //OSST 연동 성공 - PC2 에만 옴
        //String resNo = formCommService.getMvnoOrdNo(mcpRequestOsstRequest);

        //번호이동 사전동의 요청 (NP3)
        String globalNo = ""; //PRX 연동 결과 - globalNo
        String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
        String responseCode = ""; //PRX 연동 결과 - responseCode
        String responseBasic = "";

        MplatFormNP3Request mplatFormNP3Request = new MplatFormNP3Request();
        mplatFormNP3Request.setTelNo(request.getNpTlphNo());
        mplatFormNP3Request.setBchngNpCommCmpnCd(request.getBchngNpCommCmpnCd());

        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(mplatFormNP3Request),
            NUMBER_PORTABLE_RESULT.getEventCd(), resNo);

        responseType = mspPrxSoapResponse.responseType(); //PRX 연동 결과 - responseType (N / E 등)
        responseCode = mspPrxSoapResponse.responseCode(); //PRX 연동 결과 - responseCode
        responseBasic = mspPrxSoapResponse.responseBasic();
        globalNo = mspPrxSoapResponse.globalNo(); //PRX 연동 결과 - globalNo

        log.debug("responseType: {}, responseCode:{}, responseBasic: {}, globalNo: {}", responseType, responseCode, responseBasic, globalNo);

        String rsltCd = "";
        String rsltMsg = "";
        if ("N".equals(responseType)) {
            String rawXml = "";
            MplatFormNP3Response mplatFormNP3Response = new MplatFormNP3Response();
            log.info("requestNpAgree 호출 결과 rawXml: {}", rawXml);
            try {
                rawXml = mspPrxSoapResponse.rawXml();
                mplatFormNP3Response = XmlConvertUtils.xmlReturnParser(rawXml, MplatFormNP3Response.class);

                if ("E".equals(responseType)) { //연동 오류
                    rsltCd = responseCode;
                    rsltMsg = responseBasic;
                } else {
                    rsltCd = mplatFormNP3Response.getOutDto().getRsltCd();
                    rsltMsg = mplatFormNP3Response.getOutDto().getRsltMsg();
                }

                log.debug("rsltCd: {}, rsltMsg: {}", rsltCd, rsltMsg);
            } catch (Exception e) {
                throw new SimpleDomainException("requestNpAgree() 오류 발생", e);
            }

            if ("S".equals(rsltCd)) { //성공
                return FormResponse.of(ResponseMessage.VALID_REQ_NP_AGREE_SUCCESS); //번호이동 사전동의 결과 성공

                //번호이동 사전동의(NP3) 성공한 경우, FS2 호출 및 개통전 사전체크(FPC0) 호출
                //String preCheckFormStep = "Y"; //개통전 사전체크 진행하는 단계이다.
                //NewChangeRequest newChangeRequest = newChangeMpReadMapper.selectMsfPreCheckInfoRequest(request.getRequestKey());
                //log.debug(
                //    "newChangeRequest >> requestKey: {}, resNo: {}, knoteScanId: {}, fathTransacId: {}, operTypeCd: {}, agentCd: {}, getBchngNpCommCmpnCd: {}",
                //    requestKey,
                //    newChangeRequest.getResNo(),
                //    newChangeRequest.getKnoteScanId(),
                //    newChangeRequest.getFathTransacId(),
                //    OperType.MOBILE_NUMBER_PORTABILITY.getCode(),
                //    newChangeRequest.getAgentCd(),
                //    request.getBchngNpCommCmpnCd());
                //
                //MpPreCheckRequest mpPreCheckRequest = new MpPreCheckRequest();
                //mpPreCheckRequest.setPreCheckFormStep(preCheckFormStep);
                //mpPreCheckRequest.setRequestKey(requestKey);
                //mpPreCheckRequest.setResNo(newChangeRequest.getResNo()); //MSF_REQUEST.RES_NO 에 저장될 값
                //mpPreCheckRequest.setKnoteScanId(newChangeRequest.getKnoteScanId()); //KNOTE 서식지 아이디
                //mpPreCheckRequest.setFathTransacId(newChangeRequest.getFathTransacId()); //안면인증 트랜잭션 아이디
                //mpPreCheckRequest.setOperTypeCd(OperType.MOBILE_NUMBER_PORTABILITY.getCode()); //업무유형
                //mpPreCheckRequest.setMngmAgncId(newChangeRequest.getAgentCd()); //Header 값으로 보낼 관리자할 대리점코드 (요건 변환해야해)
                //mpPreCheckRequest.setBchngNpCommCmpnCd(request.getBchngNpCommCmpnCd()); //번호이동 신청서의 개통전 사전체크에 항목
                //
                //log.debug(
                //    "mpPreCheckRequest >> getPreCheckFormStep: {}, getRequestKey: {}, getResNo: {}, getKnoteScanId: {}, getFathTransacId: {}, getOperTypeCd: {}, getMngmAgncId: {}, getBchngNpCommCmpnCd: {}",
                //    mpPreCheckRequest.getPreCheckFormStep(),
                //    mpPreCheckRequest.getRequestKey(),
                //    mpPreCheckRequest.getResNo(),
                //    mpPreCheckRequest.getKnoteScanId(),
                //    mpPreCheckRequest.getFathTransacId(),
                //    mpPreCheckRequest.getOperTypeCd(),
                //    mpPreCheckRequest.getMngmAgncId(),
                //    mpPreCheckRequest.getBchngNpCommCmpnCd());
                //
                //Map<String, String> rtnMapPreCheck = mpPreCheckService.getNewChangeMpPreCheck(mpPreCheckRequest);
                //if ("S".equals(rtnMapPreCheck.get("rsltCd"))) { //S:성공, F:실패
                //    return FormResponse.of(ResponseMessage.VALID_REQ_NP_AGREE_SUCCESS); //번호이동 사전동의 결과는 성공이고 개통전 사전체크도 성공
                //} else {
                //    return FormResponse.of(ResponseMessage.VALID_REQ_NP_AGREE_FAIL); //번호이동 사전동의 결과는 성공이나 개통전 사전체크는 실패
                //}

            } else if ("Y".equals(rsltCd)) { //사전동의 요청 진행중
                return FormResponse.of(ResponseMessage.VALID_REQ_NP_AGREE_IN_PROGRESS);
            } else { //실패
                return FormResponse.of(ResponseMessage.VALID_REQ_NP_AGREE_FAIL);
            }

        }
        return FormResponse.of(ResponseMessage.VALID_REQ_NP_AGREE_FAIL);
    }


    /**
     * 번호이동 사전체크 일 건수 제한
     **/
    //public Map<String, Object> mnpPreCheckLimit(String moveMobileNo) {
    //
    //    Map<String, Object> rtnMap = new HashMap<>();
    //    Map<String, Object> paramMap = new HashMap<>();
    //    List<String> resNoList = new ArrayList<>();
    //
    //    // 제한시간(분) 조회
    //    int limitDay = 0;
    //    int limitCnt = 0;
    //    String fAlertMsg = "";
    //
    //    //SELECT expnsn_str_val1, expnsn_str_val2
    //    //FROM NMCP_CD_DTL
    //    //WHERE cd_group_id='CmmPeriodLimit'
    //    //AND dtl_cd='MnpDayLimit'
    //    //AND use_yn='Y' ;
    //
    //    //공통코드그룹 CmmPeriodLimit , DTL_CD = MnpDayLimit 에서 조회
    //    CommonCodesRequest commonCodesRequest = CommonCodesRequest.withIncludeAll("CmmPeriodLimit"); //조건설정
    //    CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(commonCodesRequest); // 공통코드 조회 요청
    //    Optional<CommonCodeData> cmnPeriodLimit = commonCodeGroups.get("CmmPeriodLimit", "MnpDayLimit"); // 특정 공통코드 그룹의 특정 DTL_CD 내용 가져오기
    //    if (cmnPeriodLimit.isPresent()) {
    //        CommonCodeData.Detail detail = cmnPeriodLimit.get().detail();
    //        limitDay = Integer.parseInt(StringUtil.NVL(detail.etcValue1(), "0"));
    //        limitCnt = Integer.parseInt(StringUtil.NVL(detail.etcValue2(), "0"));
    //        fAlertMsg = detail.etcValue3();
    //    }
    //    log.debug("limitDay: {}, limitCnt: {}, fAlertMsg: {}", limitDay, limitCnt, fAlertMsg);
    //
    //    // 동일 번호이동전화번호 신청서 조회
    //    paramMap.put("limitDay", limitDay);
    //    paramMap.put("moveMobileNo", moveMobileNo);
    //    //resNoList = mcpRequestReadMapper.getResNoByMoveMobileNum(paramMap); //MCP에서 조회
    //    resNoList = newChangeReadMapper.getMsfResNoByMoveMobileNum(paramMap); //MSF에서 조회
    //    log.debug("resNoList: {}", resNoList.toString());
    //
    //    // 특정기간 내 신청건 없음 → 성공처리
    //    if (resNoList.isEmpty()) {
    //        rtnMap.put("RESULT_CODE", "00000");
    //        return rtnMap;
    //    }
    //
    //    // 사전체크 시도 이력 확인
    //    paramMap.put("resNoList", resNoList);
    //    paramMap.put("prgrStatCd", Constants.EVENT_CODE_PRE_CHECK);
    //    //int tryCnt = mcpRequestReadMapper.getPreCheckTryCnt(paramMap); //MCP에서 조회
    //    int tryCnt = newChangeReadMapper.getMsfPreCheckTryCnt(paramMap); //MSF에서 조회
    //
    //    if (limitCnt == 0 || tryCnt < limitCnt) {
    //        rtnMap.put("RESULT_CODE", "0000");
    //        return rtnMap;
    //    }
    //
    //    // 실패이력 저장
    //    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
    //    mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
    //    mcpIpStatisticDto.setTrtmRsltSmst(moveMobileNo);
    //    mcpIpStatisticDto.setPrcsSbst("Exception[PC0_DAY_LIMIT]");
    //    mcpIpStatisticDto.setParameter("MOVE_MOBILE_NUM[" + moveMobileNo + "] TRY_CNT[" + tryCnt + "] LIMIT_CNT[" + limitCnt + "]");
    //    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
    //
    //    rtnMap.put("RESULT_CODE", "-9999");
    //    rtnMap.put("ERROR_MSG", "PC0_TIME_LIMIT");
    //    rtnMap.put("ERROR_NE_MSG", fAlertMsg);
    //    return rtnMap;
    //}

    /**
     * 번호이동 납부주장 요청 : NP2
     * 고객포탈은 사용하지 않는 것으로 기록되어 있음. reqPayOpnAjax.do URI 를 조회해도 나오진 않음.
     **/
    //public Map<String, Object> reqPayOpn(McpRequestMoveDto requestMoveDto, AppformReqDto appformReqDto) {
    //public FormResponse<MnpOsstResponse> requestPayOpn(MnpOsstRequest osstReqDto) {
    //    MnpOsstResponse responseDto = new MnpOsstResponse();
    //
    //    log.debug("★ 번호이동 납부주장 요청 ★ osstOrdNo: {}, slsCmpnCd: {}, npTlphNo: {}, payAsertDt: {}, payAsertAmt: {}, payMethCd: {}",
    //        osstReqDto.getOsstOrdNo(),
    //        osstReqDto.getSlsCmpnCd(),
    //        osstReqDto.getNpTlphNo(),
    //        osstReqDto.getPayAsertAmt(),
    //        osstReqDto.getPayMethCd(),
    //        osstReqDto.getAgentCd());
    //
    //    //대리점코드는 화면에서 선택한 값을 우선적으로 처리하고 없다면 로그인 세션값을 가져오도록 함.
    //    String agentCd = osstReqDto.getAgentCd();
    //    if (agentCd == null || agentCd.equals("")) {
    //        agentCd = AuthenticationUtils.getAgentCode();
    //    }
    //
    //    //요청 parameter
    //    Map<String, String> params = new HashMap<>();
    //    params.put("appEventCd", Constants.EVENT_CODE_NP_REQ_PAY);
    //    //appAgncCd 헤더값을 cntpntShopId 값으로 조회해서 세팅 :: SELECT KT_ORG_ID FROM ORG_ORGN_INFO_MST@DL_MSP WHERE ORGN_ID = '1100019353';
    //    params.put("cntpntShopId", agentCd); //agentCd : 선택값
    //    params.put("osstOrdNo", osstReqDto.getOsstOrdNo());
    //    params.put("slsCmpnCd", osstReqDto.getSlsCmpnCd());
    //    params.put("npTlphNo", osstReqDto.getNpTlphNo());
    //    params.put("payAsertDt", osstReqDto.getPayAsertDt());
    //    params.put("payAsertAmt", osstReqDto.getPayAsertAmt());
    //    params.put("payMethCd", osstReqDto.getPayMethCd());
    //
    //    return FormResponse.of(ResponseMessage.VALID_REQ_NP_PAY_OPEN_SUCCESS, responseDto);
    //}


}
