package com.ktmmobile.msf.domains.form.form.newchange.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.form.common.dto.McpRequestOsstRequest;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestOsstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestOsstVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeNUInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeNUInfoResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SearchNumberRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.SearchNumberResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.InqrSvcNoInfoInDTO;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNU1Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNU1Response;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormNU2Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.ResvTlphNoInDTO;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.FormCommWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestOsstWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.MsfRequestOsstWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeMpReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeReadMapper;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeWriteMapper;

import static com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType.CHOICE_NUMBER_CANCEL;
import static com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType.CHOICE_NUMBER_RESERVE;
import static com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType.CHOICE_NUMBER_SEARCH;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_NUMBER_REG;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.OSST_SUCCESS;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.WORK_CODE_RES;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChoiceNumberService {

    private final MspPrxClient mspPrxClient;
    private final FormCommService formCommService;
    private final NewChangeReadMapper newChangeReadMapper;
    private final NewChangeWriteMapper newChangeWriteMapper;
    private final FormCommWriteMapper formCommWriteMapper;
    private final MsfMcpOsstPrxService msfMcpOsstPrxService;
    private final NewChangeMpReadMapper newChangeMpReadMapper;
    private final McpRequestOsstWriteMapper mcpRequestOsstWriteMapper;
    private final MsfRequestOsstWriteMapper msfRequestOsstWriteMapper;
    private final AgencyCacheReader agencyCacheReader;

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
     * 고객포탈은 희망번호 저장 : MCP_REQUEST.REQ_WANT_NUMBER >> 스마트에서 직접 PRX 호출한다면 MSF_REQUEST 테이블에 저장하려면 컬럼 추가 필요.
     **/
    @SuppressWarnings({"PMD.EmptyControlStatement", "PMD.MisplacedNullCheck"})
    public FormResponse<List<MplatFormNU1Response.OutDto.SvcNoList>> getSearchNumber(SearchNumberRequest request) {
        log.debug("★ 신규가입 희망번호 조회 ★ reqWantNumber: {}", request.getReqWantNumber());

        //0. 입력 데이타 검증
        Long requestKey = request.getRequestKey();
        String reqWantNumber = request.getReqWantNumber();
        if (reqWantNumber.length() != 4) {
            return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_NOT_CORRECT); //희망번호 입력 값 4자리를 입력해 주세요.
        }

        //1. MCP_REQUEST_OSST 에서 MVNO_ORD_NO 추출 ( REQUEST_KEY )
        McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setRequestKey(requestKey);
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK); //PC0
        //mcpRequestOsstRequest.setRsltCd(Constants.OSST_SUCCESS); //OSST 연동 성공 - PC2 에만 옴
        String resNo = formCommService.getMvnoOrdNo(mcpRequestOsstRequest);

        //1.   MCP_REQUEST_OSST 에서 OSST_ORD_NO 추출 ( MVNO_ORD_NO )
        //1-1. 개통전 사전체크 확인 (MCP_REQUEST_OSST.PRGR_STAT_CD : PC2 의 RSLT_CD : 0000 일 경우 정상으로 처리
        mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PC_RESULT); //PC2
        mcpRequestOsstRequest.setRsltCd(Constants.OSST_SUCCESS); //OSST 연동 성공 - PC2 에만 옴
        mcpRequestOsstRequest.setMvnoOrdNo(resNo);
        int osstCount = formCommService.getOsstCount(mcpRequestOsstRequest);
        if (osstCount == 0) {
            return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_NEED_PRECHECK); //개통전 사전체크를 진행해주세요.
        }
        //1.2   OSST_ORD_NO 추출
        //String osstOrdNo = formCommService.getOsstOrdNo(mcpRequestOsstRequest);

        //2 신규가입 희망번호 조회용 항목 조회 쿼리 실행 ( RES_NO == MVNO_ORD_NO )
        NewChangeNUInfoResponse newChangeNUInfoResponse = formCommService.getXmlMessageNU1(resNo);

        //2-1 신규가입 희망번호 MP 호출 DATA SET
        MplatFormNU1Request mplatFormNU1Request = new MplatFormNU1Request();
        mplatFormNU1Request.setOsstOrdNo(newChangeNUInfoResponse.getOsstOrdNo());
        InqrSvcNoInfoInDTO inqrSvcNoInfoInDTO = new InqrSvcNoInfoInDTO();
        inqrSvcNoInfoInDTO.setAsgnAgncId(newChangeNUInfoResponse.getAsgnAgncId()); //AA00364
        inqrSvcNoInfoInDTO.setAsgnAgncYn(newChangeNUInfoResponse.getAsgnAgncYn());
        inqrSvcNoInfoInDTO.setCntryCd(newChangeNUInfoResponse.getCntryCd());
        inqrSvcNoInfoInDTO.setCustNo(newChangeNUInfoResponse.getCustNo());
        inqrSvcNoInfoInDTO.setInqrBase(newChangeNUInfoResponse.getInqrBase());
        inqrSvcNoInfoInDTO.setInqrCascnt(newChangeNUInfoResponse.getInqrCascnt());
        inqrSvcNoInfoInDTO.setNowSvcIndCd(newChangeNUInfoResponse.getNowSvcIndCd());
        inqrSvcNoInfoInDTO.setSearchGubun(newChangeNUInfoResponse.getSearchGubun());
        inqrSvcNoInfoInDTO.setArPrGubun(newChangeNUInfoResponse.getArPrGubun());
        inqrSvcNoInfoInDTO.setTlphNoChrcCd(newChangeNUInfoResponse.getTlphNoChrcCd());
        inqrSvcNoInfoInDTO.setTlphNoIndCd(newChangeNUInfoResponse.getTlphNoIndCd());
        inqrSvcNoInfoInDTO.setTlphNoPtrn("010____" + request.getReqWantNumber());
        inqrSvcNoInfoInDTO.setTlphNoUseCd(newChangeNUInfoResponse.getTlphNoUseCd());
        inqrSvcNoInfoInDTO.setTlphNoUseMntCd(newChangeNUInfoResponse.getTlphNoUseMntCd());
        mplatFormNU1Request.setInqrSvcNoInfoInDTO(inqrSvcNoInfoInDTO);

        //2-2 신규가입 희망번호 MP 호출 - NU1
        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(mplatFormNU1Request),
            CHOICE_NUMBER_SEARCH.getEventCd(), resNo);

        //2-3 신규가입 희망번호 MP 호출 결과 DATA SET
        List<MplatFormNU1Response.OutDto.SvcNoList> phoneNumberList = null;
        try {
            MplatFormNU1Response mplatFormNU1Response = XmlConvertUtils.xmlReturnParser(mspPrxSoapResponse.rawXml(), MplatFormNU1Response.class);
            if (mplatFormNU1Response.getCommHeader().isSuccess()) {
                phoneNumberList = mplatFormNU1Response.getOutDto().getSvcNoListAll();
            } else {
                return FormResponse.of(ResponseMessage.NO_DATA);
            }
        } catch (Exception _) {
            return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_EXCEPTION);
        }

        return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_SUCCESS, phoneNumberList);
    }

    /**
     * 신규가입 희망번호 예약
     * MCP_REQUEST_OSST 테이블에 선택한
     * 전화번호, 암호화된 전화번호, 할당대리점ID, 전화번호상태코드, 번호소유통신사사업자코드, 개통서비스구분코드 등을 저장 후 MP호출
     **/
    //2-1 골드번호 입력 확인 (저장할때 골드번호를 체크함. 흠.. 번호가 세자리 다 들어가야함. 공통코드 확인해봐야함)
    //    if (OPER_TYPE_NEW.equals("NAC3")) { //if (OPER_TYPE_NEW.equals(request.getOperTypeCd())) {
    //    //appformSvc.containsGoldNumbers(Arrays.asList(request.getReqWantNumber(),
    //    //    request.getReqWantNumber2(),
    //    //    request.getReqWantNumber3()));
    //}
    //GoldNumberList - NMCP_CD_DTL
    //CommCodeMapper.getCodeList
    @SuppressWarnings("PMD.EmptyControlStatement")
    public FormResponse<SearchNumberResponse> setChoiseNumber(SearchNumberRequest request) {
        //log.debug("★ 신규가입 희망번호 예약 ★ tlphNo: {}, encdTlphNo: {}", request.getTlpNo(), request.getEncdTlphNo());
        log.debug("★ 신규가입 희망번호 예약 ★ requestKey: {}, tlphNoOwnCmpnCd: {}, tlphNo: {}, encdTlphNo: {}",
            request.getRequestKey(),
            request.getTlphNoOwnCmpnCd(),
            request.getTlpNo(),
            request.getEncdTlphNo());

        //0. 입력 데이타 검증
        Long requestKey = request.getRequestKey();
        String tlpNo = request.getTlpNo();
        String encdTlphNo = request.getEncdTlphNo();
        String tlphNoOwnCmpnCd = request.getTlphNoOwnCmpnCd();
        if (!StringUtils.hasText(tlpNo) || !StringUtils.hasText(encdTlphNo)) {
            return FormResponse.of(ResponseMessage.VALID_RESERVE_NUMBER_FAIL); //희망번호 예약을 위한 번호를 선택해 주세요.
        }

        //2. MP호출을 위한 데이타 추출
        //2.1 MCP_REQUEST_OSST 테이블에서 정보 추출 : 만약에 넘어온 값에 있다면 그걸 사용하면 되긴하는데, 일단 추출
        McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setRequestKey(requestKey);
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PC_RESULT); //PC2
        mcpRequestOsstRequest.setRsltCd("0000");
        String resNo = formCommService.getMvnoOrdNo(mcpRequestOsstRequest);

        //1. 개통전 사전체크 확인 (MCP_REQUEST_OSST.PRGR_STAT_CD : PC2 의 RSLT_CD : 0000 일 경우 정상으로 처리
        mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PC_RESULT); //PC2
        mcpRequestOsstRequest.setMvnoOrdNo(resNo);
        mcpRequestOsstRequest.setRsltCd("0000");
        int osstCount = formCommService.getOsstCount(mcpRequestOsstRequest);
        if (osstCount == 0) {
            return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_NEED_PRECHECK); //개통전 사전체크를 진행해주세요.
        }
        String osstOrdNo = formCommService.getOsstOrdNo(mcpRequestOsstRequest);

        //1. 개통전 사전체크 확인 (MCP_REQUEST_OSST.PRGR_STAT_CD : PC2 의 RSLT_CD : 0000 일 경우 정상으로 처리
        //McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();
        //mcpRequestOsstRequest.setRequestKey(requestKey);
        //mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PC_RESULT); //PC2
        //mcpRequestOsstRequest.setRsltCd("0000");
        //int osstCount = formCommService.getMcpRequestOsstCount(mcpRequestOsstRequest);
        //if (osstCount == 0) {
        //    //return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_NEED_PRECHECK); //개통전 사전체크를 진행해주세요.
        //}

        //2. MP호출을 위한 데이타 추출
        //2.1 MCP_REQUEST_OSST 테이블에서 정보 추출 : 만약에 넘어온 값에 있다면 그걸 사용하면 되긴하는데, 일단 추출
        //String resNo = formCommService.getMvnoOrdNo(mcpRequestOsstRequest);

        //2.2 MCP_REQUEST_OSST 테이블에서 OSST_ORD_NO 추출
        //mcpRequestOsstRequest = new McpRequestOsstRequest();
        //mcpRequestOsstRequest.setRsltCd("0000");
        //mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PC_RESULT); //PC2
        //mcpRequestOsstRequest.setMvnoOrdNo(resNo);
        //String osstOrdNo = formCommService.getOsstOrdNo(mcpRequestOsstRequest);

        //MCP_REQUEST_OSST 테이블에 저장 - NU2 예약정보 미리저장 (고객포탈 내용 적용)
        McpRequestOsstVo mcpRequestOsstVo = new McpRequestOsstVo();
        mcpRequestOsstVo.setMvnoOrdNo(resNo);
        mcpRequestOsstVo.setPrgrStatCd(EVENT_CODE_NUMBER_REG);
        mcpRequestOsstVo.setOsstOrdNo(osstOrdNo);
        mcpRequestOsstVo.setOpenSvcIndCd("03");
        mcpRequestOsstVo.setIfType(WORK_CODE_RES);
        mcpRequestOsstVo.setRsltCd(OSST_SUCCESS);
        mcpRequestOsstVo.setTlphNoOwnCmpnCd(tlphNoOwnCmpnCd);
        mcpRequestOsstVo.setTlphNo(tlpNo);
        mcpRequestOsstVo.setEncdTlphNo(encdTlphNo);
        mcpRequestOsstWriteMapper.insertMcpRequestOsst(mcpRequestOsstVo);

        //MSF_REQUEST_OSST 테이블에 저장 - NU2 예약정보 미리저장 (고객포탈 내용 적용)
        MsfRequestOsstVo msfRequestOsstVo = new MsfRequestOsstVo();
        msfRequestOsstVo.setMvnoOrdNo(resNo);
        msfRequestOsstVo.setPrgrStatCd(EVENT_CODE_NUMBER_REG);
        msfRequestOsstVo.setOsstOrdNo(osstOrdNo);
        msfRequestOsstVo.setOpenSvcIndCd("03");
        msfRequestOsstVo.setIfTypeCd(WORK_CODE_RES);
        msfRequestOsstVo.setRsltCd(OSST_SUCCESS);
        msfRequestOsstVo.setTlphNoOwnCmpnCd(tlphNoOwnCmpnCd);
        msfRequestOsstVo.setTlphNo(tlpNo);
        msfRequestOsstVo.setEncdTlphNo(encdTlphNo);
        msfRequestOsstWriteMapper.insertMsfRequestOsst(msfRequestOsstVo);

        //2.2 신규가입 희망번호 예약용 항목 조회 쿼리 실행
        NewChangeNUInfoRequest newChangeNUInfoRequest = new NewChangeNUInfoRequest();
        newChangeNUInfoRequest.setResNo(resNo);
        newChangeNUInfoRequest.setGubun("RSV");
        NewChangeNUInfoResponse newChangeNUInfoResponse = formCommService.getXmlMessageNU2(newChangeNUInfoRequest);

        //2.3 신규가입 희망번호 예약 MP 호출 DATA SET
        if (newChangeNUInfoResponse != null) { //임시.. 왜 데이타가 없냐고요 - 정상되면 주석 풀어야함.
            MplatFormNU2Request mplatFormNU2Request = new MplatFormNU2Request();
            mplatFormNU2Request.setOsstOrdNo(newChangeNUInfoResponse.getOsstOrdNo());
            ResvTlphNoInDTO resvTlphNoInDTO = new ResvTlphNoInDTO();
            resvTlphNoInDTO.setGubun(newChangeNUInfoResponse.getGubun());
            resvTlphNoInDTO.setTlphNo(newChangeNUInfoResponse.getTlphNo());
            resvTlphNoInDTO.setCustNo(newChangeNUInfoResponse.getCustNo());
            resvTlphNoInDTO.setTlphNoStatChngRsnCd(newChangeNUInfoResponse.getTlphNoStatChngRsnCd());
            resvTlphNoInDTO.setTlphNoStatCd(newChangeNUInfoResponse.getTlphNoStatCd());
            resvTlphNoInDTO.setCustTypeCd(newChangeNUInfoResponse.getCustTypeCd());
            resvTlphNoInDTO.setNowSvcIndCd(newChangeNUInfoResponse.getNowSvcIndCd());
            resvTlphNoInDTO.setEncdTlphNo(newChangeNUInfoResponse.getEncdTlphNo());
            resvTlphNoInDTO.setMpngTlphNoYn(newChangeNUInfoResponse.getMpngTlphNoYn());
            //resvTlphNoInDTO.setAsgnAgncId(newChangeNUInfoResponse.getAsgnAgncId());
            mplatFormNU2Request.setResvTlphNoInDTO(resvTlphNoInDTO);

            //2.4 신규가입 번호예약 MP 호출 - NU2
            MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(mplatFormNU2Request),
                CHOICE_NUMBER_RESERVE.getEventCd(), resNo);

            //2.5 신규가입 번호예약 MP 호출 결과 DATA SET
            String globalNo = "";
            String responseType = "";
            String responseCode = "";
            String responseBasic = "";

            if (mspPrxSoapResponse == null) { //연동결과 실패.
                return FormResponse.of(ResponseMessage.NO_DATA);
            } else { //연동결과 성공
                globalNo = mspPrxSoapResponse.globalNo();
                responseType = mspPrxSoapResponse.responseType();
                responseCode = mspPrxSoapResponse.responseCode(); //responseType 값이 E 일 경우 responseCode 값이 넘어오는 것으로 확인됨.
                responseBasic = mspPrxSoapResponse.responseBasic();

                log.debug("★ 연동결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
                    responseType,
                    responseCode,
                    responseBasic,
                    globalNo);

                if (!"N".equals(responseType)) {
                    return FormResponse.of(ResponseMessage.VALID_RESERVE_NUMBER_FAIL); //실패
                }
            }
        }

        return FormResponse.of(ResponseMessage.VALID_RESERVE_NUMBER_SUCCESS); //성공
    }


    /**
     * 신규가입 희망번호 취소
     */
    //public Map<String, Object> cancelNumberAjax(AppformReqDto appformReqDto) {
    @SuppressWarnings("PMD.EmptyControlStatement")
    public FormResponse<SearchNumberResponse> cancelChoiseNumber(SearchNumberRequest request) {
        log.debug("★ 신규가입 희망번호 취소 ★ requestKey: {}, tlphNoOwnCmpnCd: {}, tlphNo: {}, encdTlphNo: {}",
            request.getRequestKey(),
            request.getTlphNoOwnCmpnCd(),
            request.getTlpNo(),
            request.getEncdTlphNo());

        //0. 입력 데이타 검증
        Long requestKey = request.getRequestKey();
        if (!StringUtils.hasText(requestKey.toString())) {
            return FormResponse.of(ResponseMessage.VALID_CANCEL_NUMBER_FAIL); //희망번호 취소 실패
        }

        //1. 개통전 사전체크 확인 (MCP_REQUEST_OSST.PRGR_STAT_CD : PC2 의 RSLT_CD : 0000 일 경우 정상으로 처리
        McpRequestOsstRequest mcpRequestOsstRequest = new McpRequestOsstRequest();
        mcpRequestOsstRequest.setRequestKey(requestKey);
        mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PC_RESULT); //PC2
        mcpRequestOsstRequest.setRsltCd("0000");
        int osstCount = formCommService.getOsstCount(mcpRequestOsstRequest);
        if (osstCount == 0) {
            //return FormResponse.of(ResponseMessage.VALID_SEARCH_NUMBER_NEED_PRECHECK); //개통전 사전체크를 진행해주세요.
        }

        //2. MP호출을 위한 데이타 추출
        //2.1 MCP_REQUEST_OSST 테이블에서 정보 추출 : 만약에 넘어온 값에 있다면 그걸 사용하면 되긴하는데, 일단 추출
        String resNo = formCommService.getMvnoOrdNo(mcpRequestOsstRequest);

        //2.2 MCP_REQUEST_OSST 테이블에서 OSST_ORD_NO 추출
        //mcpRequestOsstRequest = new McpRequestOsstRequest();
        //mcpRequestOsstRequest.setRsltCd("0000");
        //mcpRequestOsstRequest.setPrgrStatCd(Constants.EVENT_CODE_PC_RESULT); //PC2
        //mcpRequestOsstRequest.setMvnoOrdNo(resNo);
        //String osstOrdNo = formCommService.getOsstOrdNo(mcpRequestOsstRequest);

        //String osstOrdNo = formCommService.getOsstOrdNo(mcpRequestOsstRequest);

        //2.2 신규가입 희망번호 취소 항목 조회 쿼리 실행
        NewChangeNUInfoRequest newChangeNUInfoRequest = new NewChangeNUInfoRequest();
        newChangeNUInfoRequest.setResNo(resNo);
        newChangeNUInfoRequest.setGubun("RRS");
        NewChangeNUInfoResponse newChangeNUInfoResponse = formCommService.getXmlMessageNU2(newChangeNUInfoRequest);

        //2.3 신규가입 희망번호 취소 MP 호출 DATA SET
        if (newChangeNUInfoResponse != null) { //임시.. 왜 데이타가 없냐고요 - 정상되면 주석 풀어야함.
            MplatFormNU2Request mplatFormNU2Request = new MplatFormNU2Request();
            mplatFormNU2Request.setOsstOrdNo(newChangeNUInfoResponse.getOsstOrdNo());
            ResvTlphNoInDTO resvTlphNoInDTO = new ResvTlphNoInDTO();
            resvTlphNoInDTO.setGubun(newChangeNUInfoResponse.getGubun());
            resvTlphNoInDTO.setTlphNo(newChangeNUInfoResponse.getTlphNo());
            resvTlphNoInDTO.setCustNo(newChangeNUInfoResponse.getCustNo());
            resvTlphNoInDTO.setTlphNoStatChngRsnCd(newChangeNUInfoResponse.getTlphNoStatChngRsnCd());
            resvTlphNoInDTO.setTlphNoStatCd(newChangeNUInfoResponse.getTlphNoStatCd());
            resvTlphNoInDTO.setCustTypeCd(newChangeNUInfoResponse.getCustTypeCd());
            resvTlphNoInDTO.setNowSvcIndCd(newChangeNUInfoResponse.getNowSvcIndCd());
            resvTlphNoInDTO.setEncdTlphNo(newChangeNUInfoResponse.getEncdTlphNo());
            resvTlphNoInDTO.setMpngTlphNoYn(newChangeNUInfoResponse.getMpngTlphNoYn());
            //resvTlphNoInDTO.setAsgnAgncId(newChangeNUInfoResponse.getAsgnAgncId());
            mplatFormNU2Request.setResvTlphNoInDTO(resvTlphNoInDTO);

            //2.4 신규가입 번호취소 MP 호출 - NU2
            MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(mplatFormNU2Request),
                CHOICE_NUMBER_CANCEL.getEventCd(), resNo);

            //2.5 신규가입 번호취소 MP 호출 결과 DATA SET
            String globalNo = "";
            String responseType = "";
            String responseCode = "";
            String responseBasic = "";

            if (mspPrxSoapResponse == null) { //연동결과 실패.
                return FormResponse.of(ResponseMessage.NO_DATA);
            } else { //연동결과 성공
                globalNo = mspPrxSoapResponse.globalNo();
                responseType = mspPrxSoapResponse.responseType();
                responseCode = mspPrxSoapResponse.responseCode(); //responseType 값이 E 일 경우 responseCode 값이 넘어오는 것으로 확인됨.
                responseBasic = mspPrxSoapResponse.responseBasic();

                log.debug("★ 연동결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
                    responseType,
                    responseCode,
                    responseBasic,
                    globalNo);

                if (!"N".equals(responseType)) {
                    return FormResponse.of(ResponseMessage.VALID_CANCEL_NUMBER_FAIL); //실패
                }
            }
        }

        return FormResponse.of(ResponseMessage.VALID_CANCEL_NUMBER_SUCCESS); //강제성공
    }

}
