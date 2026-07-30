package com.ktmmobile.msf.domains.form.form.common.service;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscBfacChkOmdIntmVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmMdlSpecInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmOrrgInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscTrtOmdIntmVO;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimDto;
import java.util.List;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformServiceType;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormXmlSelfcareRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormY12Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormY14Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormY15Request;


/**
 * eSIM 유효성체크를 하기 위한 연동 Y12, Y13, Y14, Y15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EsimCheckService {

    private final MspPrxClient mspPrxClient;
    private final MsfMcpOsstPrxService msfMcpOsstPrxService;

    /**
     * 단말기 스펙정보 조회
     * PRX 호출 (Y12)
     */
    public FormResponse<MoscRetvIntmMdlSpecInfoVO> checkY12(String indCd, String intmMdlId) {
        MoscRetvIntmMdlSpecInfoVO moscRetvIntmMdlSpecInfoVO = new MoscRetvIntmMdlSpecInfoVO();

        log.debug("★ prx-Y12 input ★ indCd: {}, intmMdlId: {}", indCd, intmMdlId);

        MplatFormY12Request y12Request = MplatFormY12Request.builder()
            .indCd(indCd)
            .intmMdlId(intmMdlId)
            .build();

        MplatFormXmlSelfcareRequest mplatFormXmlSelfcareRequest = MplatFormXmlSelfcareRequest.builder().build();

        String globalNo = "";
        String responseType = "";
        String responseCode = "";
        String responseBasic = "";

        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlSelfService(
            List.of(y12Request),
            MplatformServiceType.Y12,
            mplatFormXmlSelfcareRequest
        );

        if (mspPrxSoapResponse == null) { //결과 없는 경우
            return FormResponse.of(ResponseMessage.VALID_ESIM_Y12_FAIL, moscRetvIntmMdlSpecInfoVO);
        } else { //결과 있는 경우
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode(); //responseType 값이 E 일 경우 responseCode 값이 넘어오는 것으로 확인됨.
            responseBasic = mspPrxSoapResponse.responseBasic();

            ObjectMapper objectMapper = new ObjectMapper();
            log.debug("★ Y12 PRX 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
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
                    moscRetvIntmMdlSpecInfoVO = objectMapper.convertValue(rtnMap, MoscRetvIntmMdlSpecInfoVO.class);
                } else {
                    return FormResponse.of(ResponseMessage.VALID_ESIM_Y13_FAIL, moscRetvIntmMdlSpecInfoVO);
                }

                //moscRetvIntmMdlSpecInfoVO = mspPrxSoapResponse.payloadObject("outDto")
                //    .map(obj -> objectMapper.convertValue(obj, MoscRetvIntmMdlSpecInfoVO.class))
                //    .orElse(new MoscRetvIntmMdlSpecInfoVO());
            } else {
                log.debug("★ Y12 PRX 결과[에러발생] >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
                    responseType,
                    responseCode,
                    responseBasic,
                    globalNo);

                return FormResponse.of(ResponseMessage.VALID_ESIM_Y12_FAIL, moscRetvIntmMdlSpecInfoVO);
            }

            //서비스 호출 로그 저장
            //McpEsimOmdTraceDto mcpEsimOmdTraceDto = new McpEsimOmdTraceDto();
            //mcpEsimOmdTraceDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            //mcpEsimOmdTraceDto.setGlobalNo(globalNo);
            //mcpEsimOmdTraceDto.setEventCode("Y12");
            //mcpEsimOmdTraceDto.setPrcsSbst(returnMsg);
            //mcpEsimOmdTraceDto.setRsltCd(returnCode);
            //mcpEsimOmdTraceDto.setAccessIp(RequestUtils.getClientIp());
            //mcpEsimOmdTraceDto.setAccessUrl(request.getRequestURI());
            //mcpEsimOmdTraceDto.setTrtmRsltSmst(code);
            //mcpEsimOmdTraceDto.setEid(eid);
            //int result = esimDao.insertMcpEsimOmdTrace(mcpEsimOmdTraceDto);

            return FormResponse.of(ResponseMessage.VALID_ESIM_Y12_SUCCESS, moscRetvIntmMdlSpecInfoVO);
        }
    }

    /**
     * 단말기 스펙정보 조회
     * PRX 호출 (Y13)
     */
    public FormResponse<MoscRetvIntmOrrgInfoVO> checkY13(String indCd, String imei, int uploadPhoneSrlNo, String code, String eid) {
        MoscRetvIntmOrrgInfoVO moscRetvIntmOrrgInfoVO = new MoscRetvIntmOrrgInfoVO();

        Map<String, String> params = new HashMap<>();
        params.put("appEventCd", "Y13");
        params.put("indCd", indCd); //조회구분코드 : 1:단말모델ID,단말일련번호 조회 , 2:IMEI 조회 , 5:단말모델ID, 실물일련번호
        //params.put("intmMdlId", request.getModelId()); //조회구분 1,5 이면 필수
        //params.put("intmSrlNo", request.getProdSn()); //조회구분 1,5 이면 필수
        params.put("intmUniqIdntNo", imei); //조회구분 2 이면 필수 intmIdfyNo => intmUniqIdntNo 컬럼명 변경 20220801

        log.debug("★ prx-Y13 input ★ indCd: {}, imei: {}", indCd, imei);

        String globalNo = ""; //PRX 연동 결과 - globalNo
        String responseType = ""; //PRX 연동 결과 - responseType (N / E 등)
        String responseCode = ""; //PRX 연동 결과 - responseCode
        String responseBasic = ""; //

        MspPrxFormRequest mspPrxFormRequest = MspPrxFormRequest.builder().parameters(params).build();
        MspPrxSoapResponse mspPrxSoapResponse = mspPrxClient.callService(mspPrxFormRequest);

        if (mspPrxSoapResponse == null) { //결과 없는 경우
            return FormResponse.of(ResponseMessage.VALID_ESIM_Y13_FAIL, moscRetvIntmOrrgInfoVO);
        } else { //결과 있는 경우
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode(); //responseType 값이 E 일 경우 responseCode 값이 넘어오는 것으로 확인됨.
            responseBasic = mspPrxSoapResponse.responseBasic();

            ObjectMapper objectMapper = new ObjectMapper();
            log.debug("★ Y13 PRX 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
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
                    moscRetvIntmOrrgInfoVO = objectMapper.convertValue(rtnMap, MoscRetvIntmOrrgInfoVO.class);
                } else {
                    return FormResponse.of(ResponseMessage.VALID_ESIM_Y13_FAIL, moscRetvIntmOrrgInfoVO);
                }

                //moscRetvIntmOrrgInfoVO = mspPrxSoapResponse.payloadObject("outDto")
                //    .map(obj -> objectMapper.convertValue(obj, MoscRetvIntmOrrgInfoVO.class))
                //    .orElse(new MoscRetvIntmOrrgInfoVO());
            } else {
                log.debug("★ Y13 PRX 결과[에러발생] >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
                    responseType,
                    responseCode,
                    responseBasic,
                    globalNo);

                return FormResponse.of(ResponseMessage.VALID_ESIM_Y13_FAIL, moscRetvIntmOrrgInfoVO);
            }

            //서비스 호출 로그 저장
            //String globalNo = moscRetvIntmOrrgInfoVO.getGlobalNo();
            //McpEsimOmdTraceDto mcpEsimOmdTraceDto = new McpEsimOmdTraceDto();
            //mcpEsimOmdTraceDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            //mcpEsimOmdTraceDto.setGlobalNo(globalNo);
            //mcpEsimOmdTraceDto.setEventCode("Y13");
            //mcpEsimOmdTraceDto.setPrcsSbst(returnMsg);
            //mcpEsimOmdTraceDto.setRsltCd(returnCode);
            //mcpEsimOmdTraceDto.setAccessIp(RequestUtils.getClientIp());
            //mcpEsimOmdTraceDto.setAccessUrl(request.getRequestURI());
            //mcpEsimOmdTraceDto.setTrtmRsltSmst(code);
            //mcpEsimOmdTraceDto.setEid(eid);
            //int result = esimDao.insertMcpEsimOmdTrace(mcpEsimOmdTraceDto);

            return FormResponse.of(ResponseMessage.VALID_ESIM_Y13_SUCCESS, moscRetvIntmOrrgInfoVO);
        }
    }

    /**
     * OMD 단말 처리 사전체크
     * PRX 호출 (Y14)
     */
    public FormResponse<MoscBfacChkOmdIntmVO> checkY14(
        String wrkjobDivCd,
        String imei1,
        String imei2
    ) {
        MoscBfacChkOmdIntmVO moscBfacChkOmdIntmVO = new MoscBfacChkOmdIntmVO();

        log.debug("★ prx-Y14 input ★ wrkjobDivCd: {}, imei1: {}, imei2: {}", wrkjobDivCd, imei1, imei2);

        MplatFormY14Request y14Request = MplatFormY14Request.builder()
            .wrkjobDivCd(wrkjobDivCd)
            .imei(imei1)
            .imei2(imei2)
            .build();

        MplatFormXmlSelfcareRequest mplatFormXmlSelfcareRequest = MplatFormXmlSelfcareRequest.builder().build();

        String globalNo = "";
        String responseType = "";
        String responseCode = "";
        String responseBasic = "";

        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlSelfService(
            List.of(y14Request),
            MplatformServiceType.Y14,
            mplatFormXmlSelfcareRequest
        );

        if (mspPrxSoapResponse == null) { //결과 없는 경우
            return FormResponse.of(ResponseMessage.VALID_ESIM_Y14_FAIL, moscBfacChkOmdIntmVO);
        } else { //결과 있는 경우
            globalNo = mspPrxSoapResponse.globalNo();
            responseType = mspPrxSoapResponse.responseType();
            responseCode = mspPrxSoapResponse.responseCode(); //responseType 값이 E 일 경우 responseCode 값이 넘어오는 것으로 확인됨.
            responseBasic = mspPrxSoapResponse.responseBasic();

            ObjectMapper objectMapper = new ObjectMapper();
            log.debug("★ Y14 PRX 결과 >> responseType: {}, responseCode: {}, responseBasic: {}, globalNo: {}",
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
                    moscBfacChkOmdIntmVO = objectMapper.convertValue(rtnMap, MoscBfacChkOmdIntmVO.class);
                }
                return FormResponse.of(ResponseMessage.VALID_ESIM_Y14_SUCCESS, moscBfacChkOmdIntmVO);
            } else {
                log.debug("moscBfacChkOmdIntmVO.getTrtResult(): {}, moscBfacChkOmdIntmVO.getTrtMsg(): {}",
                    moscBfacChkOmdIntmVO.getTrtResult(),
                    moscBfacChkOmdIntmVO.getTrtMsg());

                return FormResponse.of(ResponseMessage.VALID_ESIM_Y14_FAIL, moscBfacChkOmdIntmVO);
            }

            //서비스 호출 로그 저장
            //String globalNo = moscBfacChkOmdIntmVO.getGlobalNo();
            //McpEsimOmdTraceDto mcpEsimOmdTraceDto = new McpEsimOmdTraceDto();
            //mcpEsimOmdTraceDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            //mcpEsimOmdTraceDto.setGlobalNo(globalNo);
            //mcpEsimOmdTraceDto.setEventCode("Y14");
            //mcpEsimOmdTraceDto.setPrcsSbst(returnMsg);
            //mcpEsimOmdTraceDto.setRsltCd(returnCode);
            //mcpEsimOmdTraceDto.setAccessIp(RequestUtils.getClientIp());
            //mcpEsimOmdTraceDto.setAccessUrl(request.getRequestURI());
            //mcpEsimOmdTraceDto.setTrtmRsltSmst(code);
            //mcpEsimOmdTraceDto.setEid(eid);
            //int result = esimDao.insertMcpEsimOmdTrace(mcpEsimOmdTraceDto);

            // MCP_UPLOAD_PHONE_INFO 업데이트
            //try {
            //McpUploadPhoneInfoDto mcpUploadPhoneInfoDto = new McpUploadPhoneInfoDto();
            //mcpUploadPhoneInfoDto.setEvntCd("Y14");
            //mcpUploadPhoneInfoDto.setRsltCd(returnCode);
            //mcpUploadPhoneInfoDto.setRsltYn(rsltYn);
            //mcpUploadPhoneInfoDto.setRsltMsg(returnMsg);
            //mcpUploadPhoneInfoDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            //int result = esimDao.updateServiceRst(mcpUploadPhoneInfoDto);

            //return FormResponse.of(ResponseMessage.VALID_ESIM_Y14_SUCCESS, moscBfacChkOmdIntmVO);
        }
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


}
