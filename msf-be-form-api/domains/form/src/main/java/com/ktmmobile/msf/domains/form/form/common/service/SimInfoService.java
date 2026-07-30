package com.ktmmobile.msf.domains.form.form.common.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformServiceType;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.JuoSubInfoDto;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormXmlSelfcareRequest;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscBfacChkOmdIntmVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmMdlSpecInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmOrrgInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscTrtOmdIntmVO;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.RateInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.ProductInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgDtlVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfUploadPhoneInfoVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.UsimRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.UsimResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormY13Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormY15Request;
import com.ktmmobile.msf.domains.form.form.newchange.repository.smartform.NewChangeWriteMapper;
import com.ktmmobile.msf.domains.form.form.newchange.service.ProductInfoService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimInfoService {

    private final MspPrxClient mspPrxClient;
    private final McpApiClient mcpApiClient;
    private final MsfMcpOsstPrxService msfMcpOsstPrxService;

    private final ProductInfoService productInfoService;
    private final FormCommService formCommService;
    private final EsimCheckService esimCheckService;
    private final NewChangeWriteMapper newChangeWriteMapper;
    private final ProductInfoReadMapper productInfoReadMapper;

    /**
     * 휴대폰 일련번호 유효성체크
     */
    //public Map<String, Object> verifyPhoneSerialNumberInfo(ProductSearchCondition condition) {
    public FormResponse<Map<String, Object>> verifyPhoneSerialNumberInfo(ProductInventoryRequest productInventoryRequest) {

        log.debug("★ 휴대폰 일련번호 유효성체크 ★ modelId: {}, prodSn: {}, agentCd: {}",
            productInventoryRequest.getModelId(),
            productInventoryRequest.getProdSn(),
            productInventoryRequest.getAgentCd());

        // resultCode "0000" 정상
        // resultCode "2000" 재고없음
        // resultCode "3000" 부정사용주장 단말이다
        // resultCode "1000" 기기원부조회 실패

        Map<String, Object> rtnMap = new HashMap<>();
        //String resultCode = "0000";
        //String resultMessage = "사용 가능한 휴대폰 일련번호 입니다.";

        //1. 단말 재고조회 ( 휴대폰목록조회에서 사용하는 것과 같은걸 사용. 추후 분리여부 검토필요)
        //   스마트 단말관리자에서 매장코드(STOR_CD), 단말일련번호(PROD_SN), 단말코드(PROD_ID)로 IMEI 추출
        //   단말목록조회에서는 로그인사용자의 매장 보유 재고 단말코드를 가져와서 단말목록조회 조건절에 추가하여 사용
        //   휴대폰 일련번호 유효성체크에서 매장재고 조회는 조건절이 추가됨 (휴대폰코드와 휴대폰일련번호)으로 쿼리를 분리하거나 해야하나?
        //   @ IMEI 는 USIM 용 하나만 리턴하게 하는지 확인필요함.
        //parameter 1 - 로그인세션의 매장코드?(stor_cd) 대리점코드?(agent_cd) >> 현재는 매장코드로 조회함.
        //parameter 2 - 입력값 : prodId (선택한 휴대폰 상품코드 (고객포탈 관리코드) )
        //parameter 3 - 입력값 : prodSn (휴대폰일련번호)

        //String storCd = AuthenticationUtils.getShopCode(); //로그인 사용자의 매장코드
        String agentCd = productInventoryRequest.getAgentCd(); //신청서 작성 시 선택한 대리점코드
        String prodId = productInventoryRequest.getModelId(); //선택한 단말
        String prodSn = productInventoryRequest.getProdSn(); //휴대폰 일련번호
        //String imeiTest = productInventoryRequest.getImeiTest(); //임시변수임. prx 오류로 IMEI 를 입력해서 Y13 호출하도록 함.
        if (!StringUtils.hasText(prodId) || !StringUtils.hasText(prodSn)) {
            return FormResponse.of(ResponseMessage.VALID_PHONE_SERIAL_NODATA, rtnMap);
        }
        productInventoryRequest.setAgentCd(agentCd); //선택한 대리점코드
        productInventoryRequest.setProdId(prodId); //선택한 단말코드
        productInventoryRequest.setProdSn(prodSn); //입력한 휴대폰 일련번호

        boolean hasPhone = productInfoService.getPhoneInventoryCount(productInventoryRequest);

        if (!hasPhone) { //재고없음.
            rtnMap.put("RESULT_CODE", ResponseMessage.VALID_PHONE_SERIAL_NODATA);
            rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_PHONE_SERIAL_NODATA.getMessage());
            return FormResponse.of(ResponseMessage.VALID_PHONE_SERIAL_NODATA, rtnMap);
        }

        //2. 부정사용주장 단말확인 - 매장재고에서 IMEI 를 제외해서 주석처리.
        //boolean isValidImei = formCommService.checkAbuseImeiList(Arrays.asList(imei, ""));
        //if (isValidImei) { // true 일때 부정사용단말이다.
        //    rtnMap.put("RESULT_CODE", ResponseMessage.VALID_PHONE_SERIAL_ABUSE);
        //    rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_PHONE_SERIAL_ABUSE.getMessage());
        //    return FormResponse.of(ResponseMessage.VALID_PHONE_SERIAL_ABUSE, rtnMap);
        //}

        //3. 기기원부조회 (Y13)
        log.debug("★ 휴대폰 일련번호 유효성체크 (prx-Y13) ★ indCd: 1, intmMdlId: {}, intmSrlNo: {}", prodId, prodSn);

        MplatFormY13Request y13Request = MplatFormY13Request.builder()
            .indCd("1")
            .intmMdlId(prodId)
            .intmSrlNo(prodSn)
            .build();

        MplatFormXmlSelfcareRequest mplatFormXmlSelfcareRequest = MplatFormXmlSelfcareRequest.builder().build();

        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlSelfService(
            List.of(y13Request),
            MplatformServiceType.Y13,
            mplatFormXmlSelfcareRequest
        );

        String globalNo = ""; //PRX 연동 결과 - globalNo
        String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
        String responseCode = ""; //PRX 연동 결과 - responseCode
        String responseBasic = ""; //

        if (mspPrxSoapResponse == null) { //결과 없는 경우
            return FormResponse.of(ResponseMessage.VALID_PHONE_SERIAL_FAIL);
        } else { //결과 있는 경우
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode(); //responseType 값이 E 일 경우 responseCode 값이 넘어오는 것으로 확인됨.
            responseBasic = mspPrxSoapResponse.responseBasic();

            log.debug("★ 휴대폰 일련번호 유효성체크 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
                responseType,
                responseCode,
                responseBasic,
                globalNo);
            if ("N".equals(responseType)) { //연동 성공
                String lastIntmStatCd = mspPrxSoapResponse.payloadValue("outDto", "lastIntmStatCd").map(Object::toString).orElse("");
                String openRstrYn = mspPrxSoapResponse.payloadValue("outDto", "openRstrYn").map(Object::toString).orElse("");
                //String eUiccId = moscRetvIntmOrrgInfoVO.geteUiccId(); //eSIM IMEI일 경우 eid 값 존재
                //String intmSrlNo = moscRetvIntmOrrgInfoVO.getIntmSrlNo(); //기기일련번호(조회구분이 1, 5 이면필수)
                //String intmMdlId = moscRetvIntmOrrgInfoVO.getIntmMdlId(); //기기모델아이디 (조회구분이 1,5 이면필수)

                log.debug("lastIntmStatCd: {}", lastIntmStatCd);
                log.debug("uploadPhoneSrlNo: {}", productInventoryRequest.getUploadPhoneSrlNo());
                //정상
                if (("01".equals(lastIntmStatCd) || "30".equals(lastIntmStatCd)) && "N".equals(openRstrYn)) {
                    //lastIntmStatCd : 01  사용대기–신규입고및반납하여판매/임대가가능한기기
                    //lastIntmStatCd : 10  사용중–현재기기가개통되어사용
                    //lastIntmStatCd : 20  정지 - N-RDS에서는사용하지않으며, 코드등록되어있지않음.
                    //lastIntmStatCd : 30  해지 - 고장수리, 일시정지, 가입계약해지등
                    //lastIntmStatCd : 40  폐기 - 파손/불량등의사유로더이상사용불가한상태

                    //LGS_PRDT_SRL_MST 테이블의 prod_id 값과 연동 후 modelId 값이 달라 조건을 현재로서 주석해제 하기가 어려움.
                    //if (intmMdlId.equals(request.getModelId())) { //parameter 로 넘어온 modelId 와 PRX 연동결과 modelID 가 동일한 경우 정상

                    //휴대폰일련번호 유효성체크가 성공하면 eSIM 의 경우 uploadPhoneSrlNo 로 휴대폰 일련번호 업데이트
                    String uploadPhoneSrlNoStr = productInventoryRequest.getUploadPhoneSrlNo(); //eSIM 일 경우 단말정보 저장
                    if (StringUtils.hasText(uploadPhoneSrlNoStr)) {
                        long uploadPhoneSrlNo = Long.parseLong(uploadPhoneSrlNoStr);
                        MsfUploadPhoneInfoVo msfUploadPhoneInfoVo = new MsfUploadPhoneInfoVo();
                        msfUploadPhoneInfoVo.setReqPhoneSn(prodSn);
                        msfUploadPhoneInfoVo.setUploadPhoneSrlNo(uploadPhoneSrlNo);
                        uploadPhoneSrlNo = newChangeWriteMapper.updateMsfUploadPhoneInfo(msfUploadPhoneInfoVo);
                        log.debug("eSIM 단말의 경우 일련번호 업데이트 :: uploadPhoneSrlNo : {}", uploadPhoneSrlNo);
                    }

                    return FormResponse.of(ResponseMessage.VALID_PHONE_SERIAL_SUCCESS);
                }

                rtnMap.put("RESULT_CODE", ResponseMessage.VALID_PHONE_SERIAL_FAIL.getCode());
                rtnMap.put("RESULT_MSG", responseBasic);
                return FormResponse.of(ResponseMessage.VALID_PHONE_SERIAL_FAIL, rtnMap);
            }
        }

        return FormResponse.of(ResponseMessage.VALID_PHONE_SERIAL_FAIL);
    }

    /**
     * USIM 정보 유효성체크
     */
    //고객포탈 URI : /msp/moscIntmMgmtAjax.do
    public FormResponse<Map<String, Object>> verifyUsimInfo(UsimRequest request) {
        log.debug("★ USIM 유효성체크 ★ iccId: {}, agentCd: {}", request.getIccId(), request.getAgentCd());
        //필수 입력값 유효성체크
        //0. 매장재고 조회
        //1. 불량유심 사용 제한
        //2. 명의도용 추가피해 방지를 위한 유심재사용 확인
        //3. USIM 유효성체크 (X85)

        // rtnCode "0000" 정상
        // rtnCode "0100" 유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.
        // rtnCode "0200" 유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.
        // rtnCode "0300" 유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.

        Map<String, Object> rtnMap = new HashMap<>();
        //ProductInventoryRequest productInventoryRequest = new ProductInventoryRequest();

        String iccId = request.getIccId(); //USIM 의 ICCID 값
        String agentCd = request.getAgentCd(); //선택한 대리점코드
        //boolean hasSim = request.isHasSim(); //보유유심여부를 넘겨줌

        //선택한 대리점이 없음.
        if (agentCd == null || agentCd.equals("")) {
            return FormResponse.of(ResponseMessage.VALID_USIM_NO_DATA); //
        }

        //입력값 유효성체크
        if (!StringUtils.hasText(iccId)) {
            return FormResponse.of(ResponseMessage.VALID_USIM_NO_DATA); //
        }

        //0. 매장재고 조회 - KT조직코드, 상품일련번호(휴대폰 또는 유심의 Serial Number)
        //USIM 의 매장재고 조회 제외 요청 ( 2026.07.13 )
        //boolean rtnValue = true; //유심구매인 경우 재고여부 확인하여 리턴하려고 하는 변수
        //if (!hasSim) { //보유유심이 아닌 경우에만 재고조회를 하도록 함.
        //    productInventoryRequest.setAgentCd(agentCd);//화면에서 선택한 대리점코드
        //    productInventoryRequest.setProdSn(iccId); //USIM일련번호
        //    rtnValue = productInfoService.getPhoneInventoryCount(productInventoryRequest);
        //
        //    if (!rtnValue) {
        //        return FormResponse.of(ResponseMessage.VALID_USIM_NO_DATA); //
        //    }
        //}

        //1. 불량유심 사용제한
        Integer failUsimCnt = mcpApiClient.post(
            "/storeUsim/failUsim",
            iccId,
            int.class
        );
        log.debug("★ USIM 유효성체크 ★ 불량유심 사용 제한 >> failUsimCnt: {}", failUsimCnt);
        if (failUsimCnt > 0) {
            //불량유심 사용제한에 포함된 경우 사용자정보 업데이트 - 스마트에도 필요한지 검토필요함.
            JuoSubInfoDto juoSubInfoDto = new JuoSubInfoDto();
            juoSubInfoDto.setCustomerId(AuthenticationUtils.getUser().getUserId());
            juoSubInfoDto.setIccId(iccId);
            mcpApiClient.post(
                "/storeUsim/updateFailUsim",
                juoSubInfoDto,
                int.class
            );
            log.debug("★ USIM 유효성체크 ★ 불량유심 사용 제한 업데이트는 하지 않음. >> /storeUsim/updateFailUsim: ", failUsimCnt);

            //"유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.";
            return FormResponse.of(ResponseMessage.VALID_USIM_FAIL, rtnMap);
        }

        //int failUsimCnt = 0;
        //failUsimCnt = formCommService.getFailUsims(request.getIccId());
        //if (failUsimCnt > 0) { //불량유심 사용제한에 포함된 경우 사용자정보 업데이트 - 스마트에도 필요한지 검토필요함.
        //    formCommService.setFailUsims(request.getIccId());
        //    //rtnMap.put("RESULT_CODE", ResponseMessage.VALID_USIM_FAIL);
        //    //rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_USIM_FAIL.getMessage());
        //    //rtnCode = "0100";
        //    //rtnMessage = "유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.";
        //    return FormResponse.of(ResponseMessage.VALID_USIM_FAIL, rtnMap);
        //}

        //2. 명의도용 추가피해 방지를 위한 유심재사용 확인
        Integer checkValidUsimCount = mcpApiClient.post(
            "/appform/checkValidUsimNo",
            iccId,
            int.class
        );

        log.debug("★ USIM 유효성체크 ★ 명의도용 추가피해 방지를 위한 유심재사용 확인 >> checkValidUsimCount: {}", checkValidUsimCount);
        if (checkValidUsimCount > 0) {
            return FormResponse.of(ResponseMessage.VALID_USIM_FAIL, rtnMap);
        }

        //int checkValidUsimCount = 0;
        //checkValidUsimCount = formCommService.checkValidUsimNo(request.getIccId());
        //if (checkValidUsimCount > 0) {
        //    //rtnMap.put("RESULT_CODE", ResponseMessage.VALID_USIM_FAIL);
        //    //rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_USIM_FAIL.getMessage());
        //    //rtnCode = "0200";
        //    //rtnMessage = "유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.";
        //    return FormResponse.of(ResponseMessage.VALID_USIM_FAIL, rtnMap);
        //}

        //3. USIM 유효성체크 (X85)
        String orgnId = ""; //USIM의 조직코드 조회한 결과값
        String psblYn = "";
        String rsltMsg = "";

        String globalNo = ""; //PRX 연동 결과 - globalNo
        String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
        String responseCode = ""; //PRX 연동 결과 - responseCode
        String responseBasic = "";

        log.debug("★ USIM 유효성체크 (prx-X85) ★ iccid: {}", request.getIccId());
        Map<String, String> params = new HashMap<>();
        params.put("appEventCd", "X85");
        params.put("iccid", request.getIccId());

        MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder().parameters(params).build();
        MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callService(mspPrxFormRequest);

        if (mspPrxSoapResponse == null) { //결과 없는 경우
            return FormResponse.of(ResponseMessage.VALID_USIM_FAIL);
        } else { //결과 있는 경우
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode();
            responseBasic = mspPrxSoapResponse.responseBasic();

            log.debug("★ USIM 유효성체크 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
                responseType,
                responseCode,
                responseBasic,
                globalNo);
            if ("N".equals(responseType)) { //연동 성공

                psblYn = mspPrxSoapResponse.payloadValue("outDto", "psblYn").map(Object::toString).orElse("");
                rsltMsg = mspPrxSoapResponse.payloadValue("outDto", "rsltMsg").map(Object::toString).orElse("");
                log.debug("★ USIM 유효성체크 결과 >> psblYn: {}, rsltMsg: {}", psblYn, rsltMsg);

                if ("Y".equals(psblYn)) {
                    rtnMap.put("RESULT_CODE", ResponseMessage.SUCCESS);
                    //USIM 접점코드(ORGN_ID) 조회
                    //기존 고객포탈은 MSP_PARTNER_USIM_MST 테이블에서 iccId에 매칭된 orgn_id 값 가져와 front 로 넘기고 front 에[서 usimOrgnId 를 세팅함. 저장을 따로 하지는 않는 것 같음.
                    //스마트는 java 에서 로그인 세션과 비교해야하나?
                    orgnId = formCommService.getUsimOrgnId(request.getIccId());
                    log.debug("유심 유효성체크가 성공하고 /msp/sellUsimMgmtOrgnId 에서 유심일련번호로 조직코드를 조회함. 해당 값을 어디에 써야할까요? 스마트는 PASS 인가?", orgnId);
                    rtnMap.put("USIM_ORGN_ID", orgnId);
                    rtnMap.put("REQ_USIM_NM", productInfoService.getUsimModelNm(request.getIccId()));

                    return FormResponse.of(ResponseMessage.VALID_USIM_SUCCESS, rtnMap);
                } else {
                    rtnMap.put("RESULT_CODE", ResponseMessage.VALID_USIM_FAIL.getCode());
                    rtnMap.put("RESULT_MESSAGE", rsltMsg);
                }
                //} else if ("E".equals(responseType)) { //연동 에러
            } else {
                return FormResponse.of(ResponseMessage.VALID_USIM_FAIL);

            }
        }

        return FormResponse.of(ResponseMessage.VALID_USIM_FAIL);
    }

    /**
     * eSIM 정보 유효성체크
     */
    public FormResponse<EsimResponse> verifyEsimInfo(EsimRequest request) {

        log.debug("★ eSIM 유효성체크 ★ eid: {}, imei1: {}, imei2: {}, modelId: {}, reqModelNm: {}, agentCd: {}",
            request.getEid(),
            request.getImei1(),
            request.getImei2(),
            request.getModelId(),
            request.getReqModelNm(),
            request.getAgentCd());

        EsimResponse responseDto = new EsimResponse();

        //1. 입력값 확인 : 휴대폰 모델명, eid, imei1, imei2
        //2. 휴대폰 모델명으로 판매점 재고 유효성체크 >> 제거 (2026-06-18)
        //3. 부정사용주장 단말 확인
        //4. 단말정보 업로드
        //5. eSIM 유효성체크

        //String storCd = AuthenticationUtils.getShopCode(); //
        //String agentCd = request.getAgentCd();
        //if (agentCd == null || agentCd.equals("")) {
        //    AgentInfoRequest agentInfoRequest = new AgentInfoRequest();
        //    List<AgentInfoResponse> agentInfoResponseList = formCommService.getAgentList(agentInfoRequest);
        //    agentCd = agentInfoResponseList.get(0).getOrgnId();
        //}

        //1. 입력값 확인 : eid, imei1, imei2 는 Request 에서 @NotBlank 로 유효성체크
        String eid = request.getEid().trim();
        String imei1 = request.getImei1().trim();
        String imei2 = request.getImei2().trim();
        String modelId = request.getModelId() != null ? request.getModelId().trim() : "";
        if (!StringUtils.hasText(modelId) && request.getModel() != null && StringUtils.hasText(request.getModel())) {
            modelId = request.getModel().trim();
        }
        String reqModelNm = request.getReqModelNm() != null ? request.getReqModelNm().trim() : "";
        //String phoneModelId = request.getPhoneModelId();
        if (!StringUtils.hasText(eid) || !StringUtils.hasText(imei1) || !StringUtils.hasText(imei2) || !StringUtils.hasText(modelId)) {
            return FormResponse.of(ResponseMessage.VALID_ESIM_NEED_INPUT, responseDto);
        }

        //2. 재고 확인 - eSIM 은 휴대폰 일련번호 확인이 필수이므로 재고확인 주석처리 >> 2026-06-18
        //ProductInventoryRequest productInventoryRequest = new ProductInventoryRequest();
        //productInventoryRequest.setStorCd(storCd);
        //사용자가 선택한 대리점 조직이 아니라, 선택한 대리점의 KT조직으로 재고관리 (2026-06-17)
        //Optional<AgencyCache> agentInfo = agencyCacheReader.getAgency(agentCd);
        //if (agentInfo.isPresent()) {
        //    agentCd = agentInfo.get().ktOrganizationId();
        //}
        //productInventoryRequest.setAgentCd(agentCd);//대리점코드
        //productInventoryRequest.setProdId(modelId);
        //String responseImei = productInfoService.getPhoneInventory(productInventoryRequest);
        // IMEI 가 필요하진 않지만 핸드폰 일련번호 유효성체크에서 사용하는 걸 그대로 사용
        // IMEI1 , IMEI2 를 받아오는데 새로운 쿼리와 서비스를 만들어야 하려나
        //if (!StringUtils.hasText(responseImei)) { //판매점 재고에서 찾은 IMEI 정보 확인
        //    return FormResponse.of(ResponseMessage.VALID_ESIM_OUTOFSTOCK, responseDto);
        //}

        //3. 부정사용주장 단말 확인
        boolean isValidImei = formCommService.checkAbuseImeiList(Arrays.asList(imei1, imei2));
        if (isValidImei) { // true 일때 부정사용단말이다.
            return FormResponse.of(ResponseMessage.VALID_ESIM_ABUSE, responseDto);
        }

        //4. eSIM 유효성체크를 먼저 실행
        log.debug("★ eSIM 유효성체크 (prx) ★ eid: {}, imei1: {}, imei2: {}", request.getEid(), request.getImei1(), request.getImei2());
        responseDto = this.eSimInfoCheck(request);

        log.debug("★ eSIM 유효성체크 결과 ★ responseDto.getResultCode(): {}, responseDto.getResultMsg(): {}",
            responseDto.getResultCode(),
            responseDto.getResultMsg());

        //5. 유효성체크 결과가 정상("Y", "1000", "2000", "3000", "4000", "5000", "6000")일 때만 단말정보 저장 (Insert)
        List<String> successCodes = Arrays.asList("Y", "1000", "2000", "3000", "4000", "5000", "6000");
        if (successCodes.contains(responseDto.getResultCode())) {
            MsfUploadPhoneInfoVo msfUploadPhoneInfoVo = new MsfUploadPhoneInfoVo();
            msfUploadPhoneInfoVo.setEid(eid);
            msfUploadPhoneInfoVo.setImei1(imei1);
            msfUploadPhoneInfoVo.setImei2(imei2);
            msfUploadPhoneInfoVo.setModelId(StringUtils.hasText(responseDto.getModelId()) ? responseDto.getModelId() : modelId);
            msfUploadPhoneInfoVo.setReqModelNm(StringUtils.hasText(responseDto.getModelNm()) ? responseDto.getModelNm() : reqModelNm);
            msfUploadPhoneInfoVo.setReqPhoneSn(StringUtils.hasText(responseDto.getIntmSrlNo()) ? responseDto.getIntmSrlNo() : "");

            int uploadPhoneSrlNo = this.msfUploadPhoneInfo(msfUploadPhoneInfoVo);
            if (uploadPhoneSrlNo <= 0) {
                return FormResponse.of(ResponseMessage.VALID_ESIM_UPLOAD_FAIL, responseDto);
            }
            responseDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);

            return FormResponse.of(ResponseMessage.VALID_ESIM_SUCCESS, responseDto);
        } else {
            return FormResponse.of(ResponseMessage.VALID_ESIM_FAIL, responseDto);
        }
    }

    /**
     * 핸드폰 정보 업로드
     * //prntsContractNo : 모회선 계약번호은 eSIM Watch >> parameter 에서 제외함.
     */
    //private int msfUploadPhoneInfo(String eid, String imei1, String imei2, String prntsContractNo) {
    @SuppressWarnings("PMD.EmptyCatchBlock")
    //private int msfUploadPhoneInfo(String eid, String imei1, String imei2, String modelId, String reqModelNm, int eSimUploadPhoneSrlNo) {
    private int msfUploadPhoneInfo(MsfUploadPhoneInfoVo msfUploadPhoneInfoVo) {

        int uploadPhoneSrlNo = 0;
        if (msfUploadPhoneInfoVo.getUploadPhoneSrlNo() > 0) {
            uploadPhoneSrlNo = newChangeWriteMapper.updateMsfUploadPhoneInfo(msfUploadPhoneInfoVo);
        } else {
            String userId = AuthenticationUtils.getUser().getUserId();
            String accessIp = RequestUtils.getClientIp();

            msfUploadPhoneInfoVo.setReqModelNm(msfUploadPhoneInfoVo.getReqModelNm());
            msfUploadPhoneInfoVo.setModelId(msfUploadPhoneInfoVo.getModelId());
            msfUploadPhoneInfoVo.setReqPhoneSn(StringUtils.hasText(msfUploadPhoneInfoVo.getReqPhoneSn()) ? msfUploadPhoneInfoVo.getReqPhoneSn() : "");
            msfUploadPhoneInfoVo.setEid(msfUploadPhoneInfoVo.getEid());
            msfUploadPhoneInfoVo.setImei1(msfUploadPhoneInfoVo.getImei1());
            msfUploadPhoneInfoVo.setImei2(msfUploadPhoneInfoVo.getImei2());
            msfUploadPhoneInfoVo.setAccessIp(accessIp);
            msfUploadPhoneInfoVo.setUserId(userId);
            msfUploadPhoneInfoVo.setUploadPhoneImgNm("");
            msfUploadPhoneInfoVo.setPrntsContractNum("");
            uploadPhoneSrlNo = newChangeWriteMapper.insertMsfUploadPhoneInfo(msfUploadPhoneInfoVo);
        }

        return uploadPhoneSrlNo;
    }

    /**
     * eSIM 유효성체크를 위한 MP 연동
     */
    //public FormResponse<EsimResponse> eSimInfoCheck(EsimRequest reqDto) {
    public EsimResponse eSimInfoCheck(EsimRequest reqDto) {
        EsimResponse resDto = new EsimResponse();
        try {
            String indCd = "";
            String imei1 = reqDto.getImei1();
            String imei2 = reqDto.getImei2();
            String eid = reqDto.getEid();
            int uploadPhoneSrlNo = reqDto.getUploadPhoneSrlNo();
            String returnCode = "";
            String returnMsg = "";

            //Map<String, Object> hmY12 = new HashMap<String, Object>();
            //Map<String, Object> hmY13 = new HashMap<String, Object>();
            //Map<String, Object> hmY14 = new HashMap<String, Object>();

            FormResponse<MoscRetvIntmMdlSpecInfoVO> getY12 = null;
            FormResponse<MoscRetvIntmOrrgInfoVO> getY13 = null;
            FormResponse<MoscBfacChkOmdIntmVO> getY14 = null;

            MoscRetvIntmMdlSpecInfoVO moscRetvIntmMdlSpecInfoVO = null; // Y12
            MoscRetvIntmOrrgInfoVO moscRetvIntmOrrgInfoVO = null; // Y13
            MoscBfacChkOmdIntmVO moscBfacChkOmdIntmVO = null; // Y14

            // 1. Imei2 Y13기기원부 조회
            indCd = "2"; // 2:imei 조회
            getY13 = esimCheckService.checkY13(indCd, imei2, uploadPhoneSrlNo, "0000", eid);
            returnCode = getY13.resCode();

            // 1-1.imei2 원부조회 성공
            if ("0000".equals(returnCode)) {
                moscRetvIntmOrrgInfoVO = getY13.resData();
                String lastIntmStatCd = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getLastIntmStatCd(), ""); // 최종기기상태코드
                String openRstrYn = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getOpenRstrYn(), ""); // 개통제한여부
                String eUiccId = StringUtil.NVL(moscRetvIntmOrrgInfoVO.geteUiccId(), "");
                String intmMdlId = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getIntmMdlId(), "");
                String intmSrlNo = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getIntmSrlNo(), "");
                String modelNm = "";

                if (("01".equals(lastIntmStatCd) || "30".equals(lastIntmStatCd) || "10".equals(lastIntmStatCd)) && "N".equals(openRstrYn)) {
                    if (!"".equals(eUiccId)) {
                        String moveTlcmIndCd = "";
                        String moveCmncGnrtIndCd = "";
                        if (eUiccId.equals(eid)) {

                            indCd = "1";
                            getY12 = esimCheckService.checkY12(indCd, intmMdlId);
                            returnCode = getY12.resCode();
                            returnMsg = getY12.resMessage();

                            //hmY12 = this.getY12(indCd, intmMdlId, uploadPhoneSrlNo, "1000", eid);
                            //returnCode = (String) hmY12.get("returnCode");
                            //returnMsg = (String) hmY12.get("returnMsg");
                            if (!"0000".equals(returnCode)) {
                                resDto.setResultCode("1000Y12");
                                resDto.setResultMsg(returnMsg);
                                return resDto;
                            }

                            //moscRetvIntmMdlSpecInfoVO = (MoscRetvIntmMdlSpecInfoVO) hmY12.get("moscRetvIntmMdlSpecInfoVO");
                            moscRetvIntmMdlSpecInfoVO = getY12.resData();
                            List<MoscRetvIntmMdlSpecInfoVO.SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                            if (specSbstList != null && !specSbstList.isEmpty()) {
                                for (MoscRetvIntmMdlSpecInfoVO.SpecSbstDto dto: specSbstList) {
                                    String intmSpecTypeCd = StringUtil.NVL(dto.getIntmSpecTypeCd(), "");
                                    if ("110".equals(intmSpecTypeCd)) { // Y:자급제 // N: SKT/LG/KT
                                        moveTlcmIndCd = dto.getIntmSpecSbst();
                                    } else if ("111".equals(intmSpecTypeCd)) { // N:5G아님 //  Y:5G
                                        moveCmncGnrtIndCd = dto.getIntmSpecSbst();
                                    }
                                }
                            }
                            modelNm = moscRetvIntmMdlSpecInfoVO.getIntmMdlNm();

                            resDto.setModelId(intmMdlId); // y13
                            resDto.setModelNm(modelNm); // y12
                            resDto.setIntmSrlNo(intmSrlNo); // y13
                            resDto.setMoveTlcmIndCd(moveTlcmIndCd);
                            resDto.setMoveCmncGnrtIndCd(moveCmncGnrtIndCd);
                            resDto.setResultCode("1000");
                        } else {
                            resDto.setModelId(intmMdlId); // y13
                            resDto.setModelNm(modelNm); // y12
                            resDto.setIntmSrlNo(intmSrlNo); // y13
                            resDto.setMoveTlcmIndCd(moveTlcmIndCd);
                            resDto.setMoveCmncGnrtIndCd(moveCmncGnrtIndCd);
                            resDto.setResultCode("1002");
                            resDto.setResultMsg("사용자알림문구1");
                        }

                    } else {
                        //Y14
                        //hmY14 = this.getY14(wrkjobDivCd, imei2, "", uploadPhoneSrlNo, "2000", eid);
                        //returnCode = (String) hmY14.get("returnCode");
                        //returnMsg = (String) hmY14.get("returnMsg");

                        String wrkjobDivCd = "A";
                        getY14 = esimCheckService.checkY14(wrkjobDivCd, imei2, "");
                        returnCode = getY14.resCode();
                        returnMsg = getY14.resMessage();

                        if (!"0000".equals(returnCode)) {
                            resDto.setResultCode("2000Y14");
                            resDto.setResultMsg(returnMsg);
                            return resDto;
                        }

                        //moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                        moscBfacChkOmdIntmVO = getY14.resData();
                        String trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                        String trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");
                        String y14intmMdlId = moscBfacChkOmdIntmVO.getIntmModelId();
                        String y14modelNm = moscBfacChkOmdIntmVO.getIntmModelNm();
                        if (!"Y".equals(trtResult)) {
                            if (trtMsg.contains("기 등록") || trtMsg.contains("기등록")) {
                                resDto.setModelId(StringUtils.hasText(y14intmMdlId) ? y14intmMdlId : intmMdlId);
                                resDto.setModelNm(StringUtils.hasText(y14modelNm) ? y14modelNm : reqDto.getReqModelNm());
                                resDto.setIntmSrlNo(intmSrlNo);
                                resDto.setResultCode("Y");
                                resDto.setResultMsg(trtMsg);
                                return resDto;
                            }

                            EsimDto esimDto = new EsimDto();
                            esimDto.setWrkjobDivCd(wrkjobDivCd);
                            esimDto.setModelNm(y14modelNm);
                            esimDto.setModelId(y14intmMdlId);
                            esimDto.setIntmSrlNo(intmSrlNo);
                            esimDto.setEid(eid);
                            esimDto.setImei1(imei1);
                            esimDto.setImei2(imei2);

                            FormResponse<MoscTrtOmdIntmVO> y15Response = checkY15(esimDto);
                            if (y15Response != null && y15Response.resData() != null && "Y".equals(y15Response.resData().getTrtResult())) {
                                resDto.setModelId(y14intmMdlId);
                                resDto.setModelNm(y14modelNm);
                                resDto.setIntmSrlNo(intmSrlNo);
                                resDto.setResultCode("Y");
                                resDto.setResultMsg(y15Response.resData().getTrtMsg());
                                return resDto;
                            } else {
                                resDto.setResultCode("2001");
                                resDto.setResultMsg(y15Response != null && y15Response.resData() != null
                                    ? y15Response.resData().getTrtMsg()
                                    : trtMsg);
                                return resDto;
                            }
                        }

                        //Y14
                        //hmY14 = this.getY14(wrkjobDivCd, imei1, imei2, uploadPhoneSrlNo, "2000", eid);
                        //returnCode = (String) hmY14.get("returnCode");
                        //returnMsg = (String) hmY14.get("returnMsg");

                        wrkjobDivCd = "C";
                        getY14 = esimCheckService.checkY14(wrkjobDivCd, imei1, imei2);
                        returnCode = getY14.resCode();
                        returnMsg = getY14.resMessage();

                        if (!"0000".equals(returnCode)) {
                            resDto.setResultCode("2000Y14C-1");
                            resDto.setResultMsg(returnMsg);
                            return resDto;
                        }

                        //moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                        moscBfacChkOmdIntmVO = getY14.resData();
                        trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                        trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");
                        String chTrtMsg = trtMsg.replaceAll(" ", "");

                        if ("Y".equals(trtResult) || chTrtMsg.indexOf("듀얼심결합상태") < 0) {
                            resDto.setResultCode("1000Y14C-2");
                            resDto.setResultMsg(trtMsg);
                            return resDto;
                        }

                        // Y12
                        //hmY12 = this.getY12(indCd, intmMdlId, uploadPhoneSrlNo, "2000", eid);
                        //returnCode = (String) hmY12.get("returnCode");
                        //returnMsg = (String) hmY12.get("returnMsg");

                        indCd = "1";
                        getY12 = esimCheckService.checkY12(indCd, intmMdlId);
                        returnCode = getY12.resCode();
                        returnMsg = getY12.resMessage();

                        if (!"0000".equals(returnCode)) {
                            resDto.setResultCode("2000Y12");
                            resDto.setResultMsg(returnMsg);
                            return resDto;
                        }
                        String moveTlcmIndCd = "";
                        String moveCmncGnrtIndCd = "";
                        String moveCd = "";

                        //moscRetvIntmMdlSpecInfoVO = (MoscRetvIntmMdlSpecInfoVO) hmY12.get("moscRetvIntmMdlSpecInfoVO");
                        moscRetvIntmMdlSpecInfoVO = getY12.resData();
                        List<MoscRetvIntmMdlSpecInfoVO.SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                        if (specSbstList != null && !specSbstList.isEmpty()) {
                            for (MoscRetvIntmMdlSpecInfoVO.SpecSbstDto dto: specSbstList) {
                                String intmSpecTypeCd = StringUtil.NVL(dto.getIntmSpecTypeCd(), "");
                                if ("110".equals(intmSpecTypeCd)) { // Y:자급제 // N: SKT/LG/KT
                                    moveTlcmIndCd = dto.getIntmSpecSbst();
                                } else if ("111".equals(intmSpecTypeCd)) { // N:5G아님 //  Y:5G
                                    moveCmncGnrtIndCd = dto.getIntmSpecSbst();
                                }
                            }
                        }
                        modelNm = moscRetvIntmMdlSpecInfoVO.getIntmMdlNm();
                        moveCd = StringUtil.NVL(moscRetvIntmMdlSpecInfoVO.getMoveTlcmIndCd(), "K"); // SKT=S ,LG=L ,KT=NULL , 그외:O , KT 를 K로 넣겠음

                        resDto.setModelId(y14intmMdlId); // y14
                        resDto.setModelNm(y14modelNm); // y14
                        resDto.setIntmSrlNo(intmSrlNo); // y13
                        resDto.setMoveTlcmIndCd(moveTlcmIndCd);
                        resDto.setMoveCmncGnrtIndCd(moveCmncGnrtIndCd);
                        resDto.setMoveCd(moveCd);
                        resDto.setResultCode("2000");
                        return resDto;
                    }
                } else {
                    if ("10".equals(lastIntmStatCd)) {
                        resDto.setResultCode("1010");
                    } else if ("40".equals(lastIntmStatCd)) {
                        resDto.setResultCode("1040");
                    } else {
                        resDto.setResultCode("1001");
                    }
                    resDto.setResultMsg("사용자문구2");
                }

                return resDto;

            } else { // 1-2. imei2로 원부조회 실패

                indCd = "2";
                getY13 = esimCheckService.checkY13(indCd, imei1, uploadPhoneSrlNo, "34000", eid);
                returnCode = getY13.resCode();
                returnMsg = getY13.resMessage();

                //hmY13 = this.getY13(indCd, imei1, uploadPhoneSrlNo, "34000", eid);
                //returnCode = (String) hmY13.get("returnCode");
                //returnMsg = (String) hmY13.get("returnMsg");

                if ("0000".equals(returnCode)) { // 1-2-1 imei1 로 원부조회 성공

                    String lastIntmStatCd = "";
                    String intmSrlNo = "";
                    String modelId = "";
                    String modelNm = "";
                    String modelIdOther = "";
                    String modelNmOther = "";
                    String intmSrlNoOther = "";

                    //moscRetvIntmOrrgInfoVO = (MoscRetvIntmOrrgInfoVO) hmY13.get("moscRetvIntmOrrgInfoVO");
                    moscRetvIntmOrrgInfoVO = getY13.resData();
                    lastIntmStatCd = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getLastIntmStatCd(), "");
                    intmSrlNo = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getIntmSrlNo(), "");
                    modelId = moscRetvIntmOrrgInfoVO.getIntmMdlId();

                    // y14
                    String wrkjobDivCd = "A";
                    getY14 = esimCheckService.checkY14(wrkjobDivCd, imei2, "");
                    returnCode = getY14.resCode();
                    returnMsg = getY14.resMessage();
                    //hmY14 = this.getY14(wrkjobDivCd, imei2, "", uploadPhoneSrlNo, "34000", eid);
                    //returnCode = (String) hmY14.get("returnCode");
                    //returnMsg = (String) hmY14.get("returnMsg");

                    if (!"0000".equals(returnCode)) {
                        resDto.setResultCode("3000Y14");
                        resDto.setResultMsg(returnMsg);
                        return resDto;
                    }

                    //moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                    moscBfacChkOmdIntmVO = getY14.resData();
                    modelIdOther = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmModelId(), "");
                    if (!StringUtils.hasText(modelIdOther)) {
                        modelIdOther = reqDto.getModelId();
                    }
                    modelNmOther = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmModelNm(), "");
                    if (!StringUtils.hasText(modelNmOther)) {
                        modelNmOther = reqDto.getReqModelNm();
                    }
                    intmSrlNoOther = moscBfacChkOmdIntmVO.getIntmSeq();

                    String trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                    String trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");

                    if (!"Y".equals(trtResult)) {
                        if (trtMsg.indexOf("기 등록") >= 0 || trtMsg.indexOf("기등록") >= 0) {
                            resDto.setModelId(StringUtils.hasText(modelIdOther) ? modelIdOther : modelId);
                            resDto.setModelNm(StringUtils.hasText(modelNmOther) ? modelNmOther : reqDto.getReqModelNm());
                            resDto.setIntmSrlNo(intmSrlNo);
                            resDto.setResultCode("Y");
                            resDto.setResultMsg(trtMsg);
                            return resDto;
                        }

                        EsimDto esimDto = new EsimDto();
                        esimDto.setWrkjobDivCd(wrkjobDivCd);
                        esimDto.setModelNm(modelNmOther);
                        esimDto.setModelId(modelIdOther);
                        esimDto.setIntmSrlNo(intmSrlNoOther);
                        esimDto.setEid(eid);
                        esimDto.setImei1(imei1);
                        esimDto.setImei2(imei2);

                        FormResponse<MoscTrtOmdIntmVO> y15Response = checkY15(esimDto);
                        if (y15Response != null && y15Response.resData() != null && "Y".equals(y15Response.resData().getTrtResult())) {
                            resDto.setModelId(modelIdOther);
                            resDto.setModelNm(modelNmOther);
                            resDto.setIntmSrlNo(intmSrlNoOther);
                            resDto.setResultCode("Y");
                            resDto.setResultMsg(y15Response.resData().getTrtMsg());
                            return resDto;
                        } else {
                            resDto.setResultCode("3001");
                            resDto.setResultMsg(y15Response != null && y15Response.resData() != null ? y15Response.resData().getTrtMsg() : trtMsg);
                            return resDto;
                        }
                    }

                    // y12
                    //hmY12 = this.getY12(indCd, modelId, uploadPhoneSrlNo, "34000", eid);
                    //returnCode = (String) hmY12.get("returnCode");
                    //returnMsg = (String) hmY12.get("returnMsg");

                    indCd = "1";
                    getY12 = esimCheckService.checkY12(indCd, modelId);
                    returnCode = getY12.resCode();
                    returnMsg = getY12.resMessage();

                    if (!"0000".equals(returnCode)) {
                        resDto.setResultCode("2000Y12");
                        resDto.setResultMsg(returnMsg);
                        return resDto;
                    }

                    String moveTlcmIndCd = "";
                    String moveCmncGnrtIndCd = "";
                    String moveCd = "";

                    //moscRetvIntmMdlSpecInfoVO = (MoscRetvIntmMdlSpecInfoVO) hmY12.get("moscRetvIntmMdlSpecInfoVO");
                    moscRetvIntmMdlSpecInfoVO = getY12.resData();
                    List<MoscRetvIntmMdlSpecInfoVO.SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                    if (specSbstList != null && !specSbstList.isEmpty()) {
                        for (MoscRetvIntmMdlSpecInfoVO.SpecSbstDto dto: specSbstList) {
                            String intmSpecTypeCd = StringUtil.NVL(dto.getIntmSpecTypeCd(), "");
                            if ("110".equals(intmSpecTypeCd)) { // Y:자급제 // N: SKT/LG/KT
                                moveTlcmIndCd = dto.getIntmSpecSbst();
                            } else if ("111".equals(intmSpecTypeCd)) { // N:5G아님 //  Y:5G
                                moveCmncGnrtIndCd = dto.getIntmSpecSbst();
                            }
                        }
                    }
                    modelNm = moscRetvIntmMdlSpecInfoVO.getIntmMdlNm();
                    moveCd = StringUtil.NVL(moscRetvIntmMdlSpecInfoVO.getMoveTlcmIndCd(), "K"); // SKT=S ,LG=L ,KT=NULL , 그외:O , KT 를 K로 넣겠음

                    resDto.setModelId(modelId); // y13
                    resDto.setModelNm(modelNm); // y12
                    resDto.setModelIdOther(modelIdOther); // y14
                    resDto.setModelNmOther(modelNmOther); // y14
                    resDto.setIntmSrlNo(intmSrlNo); // y13
                    resDto.setIntmSrlNoOther(intmSrlNoOther); // y14
                    resDto.setMoveTlcmIndCd(moveTlcmIndCd);
                    resDto.setMoveCmncGnrtIndCd(moveCmncGnrtIndCd);
                    resDto.setMoveCd(moveCd);

                    if ("10".equals(lastIntmStatCd)) {

                        // 화면으로 이동하여 인증받기
                        resDto.setResultCode("4000");
                        return resDto;
                    } else {

                        resDto.setResultCode("3000");
                        return resDto;
                    }
                } else { // 1-2-2

                    //hmY14 = this.getY14(wrkjobDivCd, imei1, "", uploadPhoneSrlNo, "56000", eid);
                    //returnCode = (String) hmY14.get("returnCode");
                    //returnMsg = (String) hmY14.get("returnMsg");

                    String wrkjobDivCd = "A";
                    getY14 = esimCheckService.checkY14(wrkjobDivCd, imei1, "");
                    returnCode = getY14.resCode();
                    returnMsg = getY14.resMessage();

                    if (!"0000".equals(returnCode)) {
                        resDto.setResultCode("5000Y14");
                        resDto.setResultMsg(returnMsg);
                        return resDto;
                    }

                    //moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                    moscBfacChkOmdIntmVO = getY14.resData();
                    String trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                    String trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");
                    if (!"Y".equals(trtResult)) {
                        if (trtMsg.contains("기 등록") || trtMsg.contains("기등록")) {
                            String y14ModelId = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmModelId(), "");
                            if (!StringUtils.hasText(y14ModelId)) {
                                y14ModelId = reqDto.getModelId();
                            }
                            String y14ModelNm = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmModelNm(), "");
                            if (!StringUtils.hasText(y14ModelNm)) {
                                y14ModelNm = reqDto.getReqModelNm();
                            }
                            resDto.setModelId(y14ModelId);
                            resDto.setModelNm(y14ModelNm);
                            resDto.setIntmSrlNo(moscBfacChkOmdIntmVO.getIntmSeq());
                            resDto.setResultCode("Y");
                            resDto.setResultMsg(trtMsg);
                            return resDto;
                        }

                        EsimDto esimDto = new EsimDto();
                        esimDto.setWrkjobDivCd(wrkjobDivCd);
                        esimDto.setModelNm(reqDto.getReqModelNm());
                        String y14ModelId = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmModelId(), "");
                        if (!StringUtils.hasText(y14ModelId)) {
                            y14ModelId = reqDto.getModelId();
                        }
                        esimDto.setModelId(y14ModelId);
                        esimDto.setIntmSrlNo(moscBfacChkOmdIntmVO.getIntmSeq());
                        esimDto.setEid(eid);
                        esimDto.setImei1(imei1);
                        esimDto.setImei2(imei2);

                        FormResponse<MoscTrtOmdIntmVO> y15Response = checkY15(esimDto);
                        if (y15Response != null && y15Response.resData() != null && "Y".equals(y15Response.resData().getTrtResult())) {
                            resDto.setModelId(y14ModelId);
                            resDto.setModelNm(reqDto.getReqModelNm());
                            resDto.setIntmSrlNo(moscBfacChkOmdIntmVO.getIntmSeq());
                            resDto.setResultCode("Y");
                            resDto.setResultMsg(y15Response.resData().getTrtMsg());
                            return resDto;
                        } else {
                            resDto.setResultCode("5001");
                            resDto.setResultMsg(y15Response != null && y15Response.resData() != null ? y15Response.resData().getTrtMsg() : trtMsg);
                            return resDto;
                        }
                    }

                    String intmModelId = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmModelId(), "");
                    String intmModelNm = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmModelNm(), "");
                    //					String euiccId = StringUtil.NVL(moscBfacChkOmdIntmVO.getEuiccId(),"");
                    String intmSeq = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmSeq(), "");
                    resDto.setModelId(intmModelId);
                    resDto.setModelNm(intmModelNm);
                    resDto.setIntmSrlNo(intmSeq);

                    if ("".equals(intmModelId)) {
                        // 화면으로 reutn 해서 기기모델id 작성으로 return
                        // 그리고 나서 기기모델 id 찍고 작성한다음 프로세스 진행하기
                        resDto.setResultCode("6000");
                        return resDto;
                    } else {

                        resDto.setResultCode("5000");
                        return resDto;
                    }
                }

            }
        } catch (SelfServiceException e) {
            log.error(e.getMessage());
            //logger.info("error=>" + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            //logger.info("error=>" + e.getMessage());
        }

        return resDto;
    }

    /**
     * OMD 단말 처리
     * PRX 호출 (Y15)
     */
    public FormResponse<MoscTrtOmdIntmVO> checkY15(EsimDto esimDto) {
        MoscTrtOmdIntmVO moscTrtOmdIntmVO = new MoscTrtOmdIntmVO();

        String wrkjobDivCd = StringUtil.NVL(esimDto.getWrkjobDivCd(), "");
        String intmModelNm = StringUtil.NVL(esimDto.getModelNm(), "");
        String intmModelId = StringUtil.NVL(esimDto.getModelId(), "");
        String intmSeq = StringUtil.NVL(esimDto.getIntmSrlNo(), "");
        String wifiMacAdr = StringUtil.NVL(esimDto.getWifiMacAdr(), "");
        String intmEtcPurpDivCd = StringUtil.NVL(esimDto.getIntmEtcPurpDivCd(), "");

        // wrkjobDivCd = A일 경우 기기기타용도구분코드 'O' setting
        if ("A".equals(wrkjobDivCd)) {
            intmEtcPurpDivCd = "O";
        }

        String euiccId = StringUtil.NVL(esimDto.getEid(), "");
        String trtDivCd = StringUtil.NVL(esimDto.getTrtDivCd(), "");
        String imei1 = StringUtil.NVL(esimDto.getImei1(), "");
        String imei2 = StringUtil.NVL(esimDto.getImei2(), "");
        String birthday = StringUtil.NVL(esimDto.getBirthday(), "");
        String sexDiv = StringUtil.NVL(esimDto.getSexDiv(), "");
        String ctn = StringUtil.NVL(esimDto.getCtn(), "");

        log.debug("★ prx-Y15 input ★ esimDto: {}", esimDto.toString());

        MplatFormY15Request y15Request = MplatFormY15Request.builder()
            .wrkjobDivCd(wrkjobDivCd)
            .intmModelNm(intmModelNm)
            .intmModelId(intmModelId)
            .intmSeq(intmSeq)
            .wifiMacAdr(wifiMacAdr)
            .intmEtcPurpDivCd(intmEtcPurpDivCd)
            .euiccId(euiccId)
            .trtDivCd(trtDivCd)
            .imei(imei1)
            .imei2(imei2)
            .birthday(birthday)
            .sexDiv(sexDiv)
            .ctn(ctn)
            .build();

        MplatFormXmlSelfcareRequest mplatFormXmlSelfcareRequest = MplatFormXmlSelfcareRequest.builder().build();

        String globalNo = "";
        String responseType = "";
        String responseCode = "";
        String responseBasic = "";

        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlSelfService(
            List.of(y15Request),
            MplatformServiceType.Y15,
            mplatFormXmlSelfcareRequest
        );

        if (mspPrxSoapResponse == null) { //결과 없는 경우
            return FormResponse.of(ResponseMessage.VALID_ESIM_Y15_FAIL, moscTrtOmdIntmVO);
        } else { //결과 있는 경우
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode(); //responseType 값이 E 일 경우 responseCode 값이 넘어오는 것으로 확인됨.
            responseBasic = mspPrxSoapResponse.responseBasic();

            ObjectMapper objectMapper = new ObjectMapper();
            log.debug("★ Y15 PRX 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
                responseType,
                responseCode,
                responseBasic,
                globalNo);
            if ("N".equals(responseType)) { //연동 성공
                Map<String, Object> rtnMap = mspPrxSoapResponse.payloadValue("outDto")
                    .filter(Map.class::isInstance)
                    .map(Map.class::cast)
                    .orElse(Collections.emptyMap());

                if (!rtnMap.isEmpty()) {
                    moscTrtOmdIntmVO = objectMapper.convertValue(rtnMap, MoscTrtOmdIntmVO.class);
                }
                return FormResponse.of(ResponseMessage.VALID_ESIM_Y15_SUCCESS, moscTrtOmdIntmVO);
            } else {
                log.debug("moscTrtOmdIntmVO.getTrtResult(): {}, moscTrtOmdIntmVO.getTrtMsg(): {}",
                    moscTrtOmdIntmVO.getTrtResult(),
                    moscTrtOmdIntmVO.getTrtMsg());

                return FormResponse.of(ResponseMessage.VALID_ESIM_Y15_FAIL, moscTrtOmdIntmVO);
            }
        }
    }


    //eSIM DATA 정보 설정
    public void fnSetDataOfeSim(NewChangeInfoRequest request) {

        /*if (!"09".equals(request.getUsimKindsCd())) {
            return;
        }*/

        //핸드폰정보가 업로드된 파일 일련번호
        /*if (Integer.parseInt(request.getUploadPhoneSrlNo()) < 1) {
            //throw new McpCommonJsonException("3001", PHONE_EID_NULL_EXCEPTION);
        }*/
        //핸드폰정보가 업로드된 파일 일련번호로 확인
        //McpUploadPhoneInfoDto uploadEPhone = appformSvc.getUploadPhoneInfo(request.getUploadPhoneSrlNo());
        //데이타가 없으면 안돼! 처리.
        /*if (uploadEPhone == null || StringUtils.isBlank(uploadEPhone.getEid())) {
            throw new McpCommonJsonException("3001", PHONE_EID_NULL_EXCEPTION);
        }*/
        //데이타가 있으면 아래와 같이 처리 (고객포탈의 watch 부분은 삭제)
        /*if (StringUtils.isBlank(uploadEPhone.getPrntsContractNo())) {
            //일반 eSIM
            //eSIM 정보 설정
            request.setEid(uploadEPhone.getEid());
            request.setImei1(uploadEPhone.getImei1());
            request.setImei2(uploadEPhone.getImei2());
            request.setReqPhoneSn(uploadEPhone.getReqPhoneSn());
            request.setEsimPhoneId(uploadEPhone.getModelId());
        }*/
    }

    public FormResponse<UsimResponse> getPrice(@Valid UsimRequest request) {
        MsfRequestSvcChgDtlVo vo = new MsfRequestSvcChgDtlVo();
        vo.setSocCd(request.getSoc());
        RateInfoResponse rateInfoResponse = productInfoReadMapper.selectRateInfo(vo);

        if (rateInfoResponse == null) {
            return FormResponse.of(ResponseMessage.NO_DATA);
        }

        // 가입비 및 유심비 조회
        String dataType = StringUtils.hasText(rateInfoResponse.getDataType()) ? rateInfoResponse.getDataType() : "LTE";
        PriceJoinUsimRequest priceJoinUsimRequest = new PriceJoinUsimRequest();
        priceJoinUsimRequest.setDataType(dataType);
        priceJoinUsimRequest.setPriceGubun("NAC3" + dataType);
        PriceJoinUsimResponse priceJoinUsimResponse = productInfoReadMapper.selectJoinUsimPrice(priceJoinUsimRequest);
        UsimResponse usimResponse = new UsimResponse();
        usimResponse.setUsimPrice(priceJoinUsimResponse.getSimPrice());

        return FormResponse.of(ResponseMessage.SUCCESS, usimResponse);
    }
    //--------------------------- [eSIM] 여기서부터는 공통으로 빼야할 사항으로 보임 END ------------------------------

}
