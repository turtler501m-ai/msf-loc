package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.JuoSubInfoDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscInqrUsimUsePsblOutDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscBfacChkOmdIntmVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmMdlSpecInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmOrrgInfoVO;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.EsimRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.EsimResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneSerialRequest;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.MsfWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfUploadPhoneInfoVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeInfoRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SimInfoService {

    private final MsfMplatFormServerAdapter msfMplatFormServerAdapter;
    private final MsfMplatFormService msfMplatFormService;
    private final IpStatisticService ipstatisticService;

    private final ProductInfoService productInfoService;
    private final FormCommService formCommService;

    private final MsfWriteMapper msfWriteMapper;


    /**
     * 휴대폰 일련번호 유효성체크
     *
     * @param condition
     * @return
     */
    //public Map<String, Object> verifyPhoneSerialNumberInfo(ProductSearchCondition condition) {
    public FormResponse<Map<String, Object>> verifyPhoneSerialNumberInfo(PhoneSerialRequest condition) {
        // resultCode "0000" 정상
        // resultCode "2000" 재고없음
        // resultCode "3000" 부정사용주장 단말이다
        // resultCode "1000" 기기원부조회 실패

        Map<String, Object> rtnMap = new HashMap<>();
        String resultCode = "0000";
        String resultMessage = "사용 가능한 휴대폰 일련번호 입니다.";

        //1. 단말 재고조회 ( 휴대폰목록조회에서 사용하는 것과 같은걸 사용. 추후 분리여부 검토필요)
        //   스마트 단말관리자에서 매장코드(STOR_CD), 단말일련번호(PROD_SN), 단말코드(PROD_ID)로 IMEI 추출
        //   단말목록조회에서는 로그인사용자의 매장 보유 재고 단말코드를 가져와서 단말목록조회 조건절에 추가하여 사용
        //   휴대폰 일련번호 유효성체크에서 매장재고 조회는 조건절이 추가됨 (휴대폰코드와 휴대폰일련번호)으로 쿼리를 분리하거나 해야하나?
        //   @ IMEI 는 USIM 용 하나만 리턴하게 하는지 확인필요함.
        //parameter 1 - 로그인세션의 매장코드?(stor_cd) 대리점코드?(agent_cd) >> 현재는 매장코드로 조회함.
        //parameter 2 - 입력값 : prodId (선택한 휴대폰 상품코드 (고객포탈 관리코드) )
        //parameter 3 - 입력값 : prodSn (휴대폰일련번호)
        String imei = productInfoService.getPhoneInventory(condition);
        System.out.println("imei: " + imei);

        if (!StringUtils.hasText(imei)) {
            rtnMap.put("RESULT_CODE", ResponseMessage.VALID_PHONE_SERIAL_MISS);
            rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_PHONE_SERIAL_MISS.getMessage());
        }

        //2. 부정사용주장 단말확인
        boolean isValidImei = formCommService.checkAbuseImeiList(Arrays.asList(imei, ""));
        if (isValidImei) { // true 일때 부정사용단말이다.
            rtnMap.put("RESULT_CODE", ResponseMessage.VALID_PHONE_SERIAL_ABUSE);
            rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_PHONE_SERIAL_ABUSE.getMessage());
        }
        //비정상
        /*{
            "orgnId": "V000001105",
                "prodSn": "R3CR412D000", //359130335333144
                "prodId": "4993"
        }*/
        //정상
        /*{
            "orgnId": "V000001105",
                "prodSn": "R3CR412D001", // 359130335333143
                "prodId": "4994"
        }*/

        //3. 기기원부조회 (Y13)
        String indCd = "2"; // 1:단말모델ID,단말일련번호 조회 , 2:IMEI 조회 , 5:단말모델ID, 실물일련번호
        // >> indCd 값  constant 또는 enum 처리필요함.
        MoscRetvIntmOrrgInfoVO moscRetvIntmOrrgInfoVO = new MoscRetvIntmOrrgInfoVO();
        try {
            moscRetvIntmOrrgInfoVO = msfMplatFormService.moscRetvIntmOrrgInfo(indCd, imei);
            if (moscRetvIntmOrrgInfoVO == null || !moscRetvIntmOrrgInfoVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", ResponseMessage.VALID_PHONE_SERIAL_FAIL);
                rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_PHONE_SERIAL_FAIL.getMessage());
                //returnCode = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getResultCode(), "");
                //returnMsg = moscRetvIntmOrrgInfoVO.getSvcMsg();
            }
        } catch (SelfServiceException e) {
            //logger.info("SelfServiceException 13");
            //resultCode = moscRetvIntmOrrgInfoVO.getResultCode();
            //resultMessage = moscRetvIntmOrrgInfoVO.getSvcMsg();
        } catch (Exception e) {
            //logger.info("Exception 13");
            //resultCode = moscRetvIntmOrrgInfoVO.getResultCode();
            //resultMessage = moscRetvIntmOrrgInfoVO.getSvcMsg();
        }

        //String indCd, String imei, int uploadPhoneSrlNo, String code, String eid
        //EsimSvcImpl.getY13(String indCd, String imei, int uploadPhoneSrlNo, String code, String eid)

        rtnMap.put("RESULT_CODE", ResponseMessage.VALID_PHONE_SERIAL_SUCCESS);
        rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_PHONE_SERIAL_SUCCESS.getMessage());
        //return rtnMap;

        return FormResponse.of(ResponseMessage.VALID_PHONE_SERIAL_SUCCESS);
        //return FormResponse.of(ResponseMessage.VALID_PHONE_SERIAL_SUCCESS, rtnMap);
    }

    /**
     * USIM 정보 유효성체크
     *
     * @param request
     * @return
     */
    //
    //고객포탈 URI : /msp/moscIntmMgmtAjax.do
    public FormResponse<Map<String, Object>> verifyUsimInfo(MspJuoSubInfoRequest request) {
        //1. 불량유심 사용 제한
        //2. 명의도용 추가피해 방지를 위한 유심재사용 확인
        //3. USIM 유효성체크 (X85)

        // rtnCode "0000" 정상
        // rtnCode "0100" 유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.
        // rtnCode "0200" 유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.
        // rtnCode "0300" 유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.

        Map<String, Object> rtnMap = new HashMap<>();
        String orgnId = ""; //유심의 접점코드(ORGN_ID)를 조회?

        //유심 재고조회는 관리자에서 안보임. 추후 있다면 추가필요함.
        //1. 불량유심 사용제한
        int failUsimCnt = 0;
        failUsimCnt = formCommService.getFailUsims(request.getIccId());
        if (failUsimCnt > 0) { //불량유심 사용제한에 포함된 경우 사용자정보 업데이트 - 스마트에도 필요한지 검토필요함.
            formCommService.setFailUsims(request.getIccId());
            //rtnMap.put("RESULT_CODE", ResponseMessage.VALID_USIM_FAIL);
            //rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_USIM_FAIL.getMessage());
            //rtnCode = "0100";
            //rtnMessage = "유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.";
            return FormResponse.of(ResponseMessage.VALID_USIM_FAIL, rtnMap);
        }

        //2. 명의도용 추가피해 방지를 위한 유심재사용 확인
        int checkValidUsimCount = 0;
        checkValidUsimCount = formCommService.checkValidUsimNo(request.getIccId());
        if (checkValidUsimCount > 0) {
            //rtnMap.put("RESULT_CODE", ResponseMessage.VALID_USIM_FAIL);
            //rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_USIM_FAIL.getMessage());
            //rtnCode = "0200";
            //rtnMessage = "유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.";
            return FormResponse.of(ResponseMessage.VALID_USIM_FAIL, rtnMap);
        }

        //3. USIM 유효성체크 (X85)
        MoscInqrUsimUsePsblOutDTO moscInqrUsimUsePsblOutDTO = new MoscInqrUsimUsePsblOutDTO();
        if (failUsimCnt == 0 && checkValidUsimCount == 0) {
            System.out.println("failUsimCnt : ========== " + failUsimCnt);
            System.out.println("checkValidUsimCount : ========== " + checkValidUsimCount);
            try {
                JuoSubInfoDto juoSubInfoDto = new JuoSubInfoDto();
                juoSubInfoDto.setIccId(request.getIccId());
                moscInqrUsimUsePsblOutDTO = msfMplatFormService.moscIntmMgmtSO(juoSubInfoDto);
            } catch (SocketTimeoutException e) {
                System.out.println("moscInqrUsimUsePsblOutDTO : " + moscInqrUsimUsePsblOutDTO.toString());
            }

            if (moscInqrUsimUsePsblOutDTO == null) {
                //rtnMap.put("RESULT_CODE", ResponseMessage.VALID_USIM_FAIL);
                //rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_USIM_FAIL.getMessage());
                return FormResponse.of(ResponseMessage.VALID_USIM_FAIL, rtnMap);
                //rtnCode = "0300";
                //rtnMessage = "유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.";
            } else {
                if ("Y".equals(moscInqrUsimUsePsblOutDTO.getPsblYn())) {
                    //USIM 접점코드(ORGN_ID) 조회
                    orgnId = formCommService.getUsimOrgnId(request.getIccId());

                    rtnMap.put("RESULT_CODE", ResponseMessage.VALID_USIM_SUCCESS);
                    rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_USIM_SUCCESS.getMessage());
                    rtnMap.put("USIM_ORGN_ID", orgnId);
                }
            }
        }

        //@ 삭제필요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! PRX 실제 연동전까지만~
        //rtnMap.put("RESULT_CODE", ResponseMessage.VALID_USIM_SUCCESS);
        //rtnMap.put("RESULT_MESSAGE", ResponseMessage.VALID_USIM_SUCCESS.getMessage());
        rtnMap.put("USIM_ORGN_ID", orgnId);
        //return rtnMap;
        return FormResponse.of(ResponseMessage.VALID_USIM_SUCCESS, rtnMap);
    }

    /**
     * eSIM 정보 유효성체크
     *
     * @param
     * @return
     */
    public FormResponse<EsimResponse> verifyEsimInfo(EsimRequest request) {
        //1. 입력값 확인 : 휴대폰 모델명, eid, imei1, imei2
        //2. 휴대폰 모델명으로 판매점 재고 유효성체크???
        //3. 부정사용주장 단말 확인
        //4. 단말정보 업로드
        //5. eSIM 유효성체크

        EsimResponse responseDto = new EsimResponse();

        //1. 입력값 확인 : eid, imei1, imei2 는 Request 에서 @NotBlank 로 유효성체크
        String eid = request.getEid();
        String imei1 = request.getImei1();
        String imei2 = request.getImei2();
        String phoneModelId = request.getPhoneModelId();

        //2. 판매점 재고 확인
        PhoneSerialRequest phoneSerialRequest = new PhoneSerialRequest();
        phoneSerialRequest.setOrgnId("V000001105"); //변경필요 :: 세션에서 가져와야함.
        phoneSerialRequest.setProdId(phoneModelId);
        String responseImei = productInfoService.getPhoneInventory(phoneSerialRequest);
        //imei 가 필요하진 않지만 핸드폰 일련번호 유효성체크에서 사용하는 걸 그대로 사용
        //imie1과 imie2 를 받아오는데 새로운 쿼리와 서비스를 만들어야 하려나

        if (!StringUtils.hasText(responseImei)) {
            return FormResponse.of(ResponseMessage.VALID_ESIM_OUTOFSTOCK, responseDto);
        }

        //3. 부정사용주장 단말 확인
        boolean isValidImei = formCommService.checkAbuseImeiList(Arrays.asList(imei1, imei2));
        if (isValidImei) { // true 일때 부정사용단말이다.
            return FormResponse.of(ResponseMessage.VALID_ESIM_ABUSE, responseDto);
        }

        //4. 단말정보 업로드
        int uploadPhoneSrlNo = this.msfUploadPhoneInfo(eid, imei1, imei2);
        if (uploadPhoneSrlNo <= 0) {
            return FormResponse.of(ResponseMessage.VALID_ESIM_UPLOAD_FAIL, responseDto);
        }
        responseDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);

        //5. 업로드 정보로 eSIM 유효성체크
        responseDto = this.eSimChk(request);

        return FormResponse.of(ResponseMessage.VALID_ESIM_SUCCESS, responseDto);
    }

    //--------------------------- [eSIM] 여기서부터는 공통으로 빼야할 사항으로 보임 START ------------------------------
    //Y13 - MplatFormService 로 이동해야함.
    //Y12 - MplatFormService 로 이동해야함.
    //Y14 - MplatFormService 로 이동해야함.
    //Y15 - MplatFormService 로 이동해야함.

    //핸드폰정보 업로드
    //prntsContractNo : 모회선 계약번호은 eSIM Watch
    //private int msfUploadPhoneInfo(String eid, String imei1, String imei2, String prntsContractNo) {
    private int msfUploadPhoneInfo(String eid, String imei1, String imei2) {
        int uploadPhoneSrlNo = 0;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        MsfUploadPhoneInfoVo msfUploadPhoneInfoVo = new MsfUploadPhoneInfoVo();

        try {
            String userId = "";
            String accessIp = ipstatisticService.getClientIp();
            UserSessionDto userSession = SessionUtils.getUserCookieBean();
            if (userSession != null) {
                userId = userSession.getUserId();
            }

            msfUploadPhoneInfoVo.setReqModelNm("");
            msfUploadPhoneInfoVo.setModelId("");
            msfUploadPhoneInfoVo.setReqPhoneSn("");
            //msfUploadPhoneInfoVo.setReqUsimSn("");
            msfUploadPhoneInfoVo.setEid(eid);
            msfUploadPhoneInfoVo.setImei1(imei1);
            msfUploadPhoneInfoVo.setImei2(imei2);
            msfUploadPhoneInfoVo.setUploadPhoneImgNm("");
            msfUploadPhoneInfoVo.setAccessIp(accessIp);
            msfUploadPhoneInfoVo.setUserId(userId);
            msfUploadPhoneInfoVo.setPrntsContractNum("");
            uploadPhoneSrlNo = msfWriteMapper.insertMsfUploadPhoneInfo(msfUploadPhoneInfoVo);

        } catch (DataAccessException e) {
        } catch (Exception e) {
        }

        return uploadPhoneSrlNo;
    }

    //eSIM 유효성체크
    public EsimResponse eSimChk(EsimRequest reqDto) {
        EsimResponse resDto = new EsimResponse();
        try {
            String indCd = "";
            String imei1 = reqDto.getImei1();
            String imei2 = reqDto.getImei2();
            String eid = reqDto.getEid();
            int uploadPhoneSrlNo = reqDto.getUploadPhoneSrlNo();
            String returnCode = "";
            String returnMsg = "";

            Map<String, Object> hmY12 = new HashMap<String, Object>();
            Map<String, Object> hmY13 = new HashMap<String, Object>();
            Map<String, Object> hmY14 = new HashMap<String, Object>();

            MoscRetvIntmMdlSpecInfoVO moscRetvIntmMdlSpecInfoVO = null; // Y12
            MoscRetvIntmOrrgInfoVO moscRetvIntmOrrgInfoVO = null; // Y13
            MoscBfacChkOmdIntmVO moscBfacChkOmdIntmVO = null; // Y14

            // 1. Imei2 Y13기기원부 조회
            indCd = "2"; // 2:imei 조회
            //hmY13 = this.getY13(indCd, imei2, uploadPhoneSrlNo, "0000", eid);
            returnCode = (String) hmY13.get("returnCode");

            // 1-1.imei2 원부조회 성공
            if ("N".equals(returnCode)) {

                moscRetvIntmOrrgInfoVO = (MoscRetvIntmOrrgInfoVO) hmY13.get("moscRetvIntmOrrgInfoVO");
                String lastIntmStatCd = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getLastIntmStatCd(), ""); // 최종기기상태코드
                String openRstrYn = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getOpenRstrYn(), ""); // 개통제한여부
                String eUiccId = StringUtil.NVL(moscRetvIntmOrrgInfoVO.geteUiccId(), "");
                String intmMdlId = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getIntmMdlId(), "");
                String intmSrlNo = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getIntmSrlNo(), "");
                String modelNm = "";

                if (("01".equals(lastIntmStatCd) || "30".equals(lastIntmStatCd)) && "N".equals(openRstrYn)) {
                    if (!"".equals(eUiccId)) {
                        String moveTlcmIndCd = "";
                        String moveCmncGnrtIndCd = "";
                        if (eUiccId.equals(eid)) {

                            indCd = "1";
                            //hmY12 = this.getY12(indCd, intmMdlId, uploadPhoneSrlNo, "1000", eid);
                            returnCode = (String) hmY12.get("returnCode");
                            returnMsg = (String) hmY12.get("returnMsg");
                            if (!"N".equals(returnCode)) {
                                resDto.setResultCode("1000Y12");
                                resDto.setResultMsg(returnMsg);
                                return resDto;
                            }

                            moscRetvIntmMdlSpecInfoVO = (MoscRetvIntmMdlSpecInfoVO) hmY12.get("moscRetvIntmMdlSpecInfoVO");
                            /*List<SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                            if (specSbstList != null && !specSbstList.isEmpty()) {
                                for (SpecSbstDto dto : specSbstList) {
                                    String intmSpecTypeCd = StringUtil.NVL(dto.getIntmSpecTypeCd(), "");
                                    if ("110".equals(intmSpecTypeCd)) { // Y:자급제 // N: SKT/LG/KT
                                        moveTlcmIndCd = dto.getIntmSpecSbst();
                                    } else if ("111".equals(intmSpecTypeCd)) { // N:5G아님 //  Y:5G
                                        moveCmncGnrtIndCd = dto.getIntmSpecSbst();
                                    }
                                }
                            }*/
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
                        String wrkjobDivCd = "E";
                        //hmY14 = this.getY14(wrkjobDivCd, imei2, "", uploadPhoneSrlNo, "2000", eid);
                        returnCode = (String) hmY14.get("returnCode");
                        returnMsg = (String) hmY14.get("returnMsg");
                        if (!"N".equals(returnCode)) {
                            resDto.setResultCode("2000Y14");
                            resDto.setResultMsg(returnMsg);
                            return resDto;
                        }
                        moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                        String trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                        String trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");
                        String y14intmMdlId = moscBfacChkOmdIntmVO.getIntmModelId();
                        String y14modelNm = moscBfacChkOmdIntmVO.getIntmModelNm();
                        if (!"Y".equals(trtResult)) {
                            resDto.setResultCode("2001");
                            resDto.setResultMsg(trtMsg);
                            return resDto;
                        }

                        //Y14
                        wrkjobDivCd = "C";
                        //hmY14 = this.getY14(wrkjobDivCd, imei1, imei2, uploadPhoneSrlNo, "2000", eid);
                        returnCode = (String) hmY14.get("returnCode");
                        returnMsg = (String) hmY14.get("returnMsg");
                        if (!"N".equals(returnCode)) {
                            resDto.setResultCode("2000Y14C-1");
                            resDto.setResultMsg(returnMsg);
                            return resDto;
                        }
                        moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                        trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                        trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");
                        String chTrtMsg = trtMsg.replaceAll(" ", "");

                        if ("Y".equals(trtResult) || chTrtMsg.indexOf("듀얼심결합상태") < 0) {
                            resDto.setResultCode("1000Y14C-2");
                            resDto.setResultMsg(trtMsg);
                            return resDto;
                        }

                        // Y12
                        indCd = "1";
                        //hmY12 = this.getY12(indCd, intmMdlId, uploadPhoneSrlNo, "2000", eid);
                        returnCode = (String) hmY12.get("returnCode");
                        returnMsg = (String) hmY12.get("returnMsg");
                        if (!"N".equals(returnCode)) {
                            resDto.setResultCode("2000Y12");
                            resDto.setResultMsg(returnMsg);
                            return resDto;
                        }
                        String moveTlcmIndCd = "";
                        String moveCmncGnrtIndCd = "";
                        String moveCd = "";
                        moscRetvIntmMdlSpecInfoVO = (MoscRetvIntmMdlSpecInfoVO) hmY12.get("moscRetvIntmMdlSpecInfoVO");
                        /*List<SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                        if (specSbstList != null && !specSbstList.isEmpty()) {
                            for (SpecSbstDto dto : specSbstList) {
                                String intmSpecTypeCd = StringUtil.NVL(dto.getIntmSpecTypeCd(), "");
                                if ("110".equals(intmSpecTypeCd)) { // Y:자급제 // N: SKT/LG/KT
                                    moveTlcmIndCd = dto.getIntmSpecSbst();
                                } else if ("111".equals(intmSpecTypeCd)) { // N:5G아님 //  Y:5G
                                    moveCmncGnrtIndCd = dto.getIntmSpecSbst();
                                }
                            }
                        }*/
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
                //hmY13 = this.getY13(indCd, imei1, uploadPhoneSrlNo, "34000", eid);
                returnCode = (String) hmY13.get("returnCode");
                returnMsg = (String) hmY13.get("returnMsg");

                if ("N".equals(returnCode)) { // 1-2-1 imei1 로 원부조회 성공

                    String lastIntmStatCd = "";
                    String intmSrlNo = "";
                    String modelId = "";
                    String modelNm = "";
                    String modelIdOther = "";
                    String modelNmOther = "";
                    String intmSrlNoOther = "";

                    moscRetvIntmOrrgInfoVO = (MoscRetvIntmOrrgInfoVO) hmY13.get("moscRetvIntmOrrgInfoVO");
                    lastIntmStatCd = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getLastIntmStatCd(), "");
                    intmSrlNo = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getIntmSrlNo(), "");
                    modelId = moscRetvIntmOrrgInfoVO.getIntmMdlId();

                    // y14
                    String wrkjobDivCd = "A";
                    //hmY14 = this.getY14(wrkjobDivCd, imei2, "", uploadPhoneSrlNo, "34000", eid);
                    returnCode = (String) hmY14.get("returnCode");
                    returnMsg = (String) hmY14.get("returnMsg");
                    if (!"N".equals(returnCode)) {
                        resDto.setResultCode("3000Y14");
                        resDto.setResultMsg(returnMsg);
                        return resDto;
                    }
                    moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                    modelIdOther = moscBfacChkOmdIntmVO.getIntmModelId();  //<=== Y14 모델 아이디
                    modelNmOther = moscBfacChkOmdIntmVO.getIntmModelNm();  //<=== Y14 모델 명
                    intmSrlNoOther = moscBfacChkOmdIntmVO.getIntmSeq();

                    String trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                    String trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");

                    if (!"Y".equals(trtResult)) {
                        resDto.setResultCode("3001");
                        resDto.setResultMsg(trtMsg);
                        return resDto;
                    }

                    // y12
                    indCd = "1";
                    //hmY12 = this.getY12(indCd, modelId, uploadPhoneSrlNo, "34000", eid);
                    returnCode = (String) hmY12.get("returnCode");
                    returnMsg = (String) hmY12.get("returnMsg");
                    if (!"N".equals(returnCode)) {
                        resDto.setResultCode("2000Y12");
                        resDto.setResultMsg(returnMsg);
                        return resDto;
                    }

                    String moveTlcmIndCd = "";
                    String moveCmncGnrtIndCd = "";
                    String moveCd = "";
                    moscRetvIntmMdlSpecInfoVO = (MoscRetvIntmMdlSpecInfoVO) hmY12.get("moscRetvIntmMdlSpecInfoVO");
                    /*List<SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                    if (specSbstList != null && !specSbstList.isEmpty()) {
                        for (SpecSbstDto dto : specSbstList) {
                            String intmSpecTypeCd = StringUtil.NVL(dto.getIntmSpecTypeCd(), "");
                            if ("110".equals(intmSpecTypeCd)) { // Y:자급제 // N: SKT/LG/KT
                                moveTlcmIndCd = dto.getIntmSpecSbst();
                            } else if ("111".equals(intmSpecTypeCd)) { // N:5G아님 //  Y:5G
                                moveCmncGnrtIndCd = dto.getIntmSpecSbst();
                            }
                        }
                    }*/
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

                    String wrkjobDivCd = "A";
                    //hmY14 = this.getY14(wrkjobDivCd, imei1, "", uploadPhoneSrlNo, "56000", eid);
                    returnCode = (String) hmY14.get("returnCode");
                    returnMsg = (String) hmY14.get("returnMsg");

                    if (!"N".equals(returnCode)) {

                        resDto.setResultCode("5000Y14");
                        resDto.setResultMsg(returnMsg);
                        return resDto;
                    }

                    moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                    String trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                    String trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");
                    if (!"Y".equals(trtResult)) {
                        resDto.setResultCode("5001");
                        resDto.setResultMsg(trtMsg);
                        return resDto;
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
            //logger.info("error=>" + e.getMessage());
        } catch (Exception e) {
            //logger.info("error=>" + e.getMessage());
        }

        return resDto;
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
    //--------------------------- [eSIM] 여기서부터는 공통으로 빼야할 사항으로 보임 END ------------------------------

}
