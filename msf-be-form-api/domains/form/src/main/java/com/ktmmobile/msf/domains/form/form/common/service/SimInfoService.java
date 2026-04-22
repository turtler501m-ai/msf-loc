package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormServerAdapter;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscInqrUsimUsePsblOutDTO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscBfacChkOmdIntmVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmMdlSpecInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MoscRetvIntmOrrgInfoVO;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.PhoneSerialCondition;
import com.ktmmobile.msf.domains.form.form.newchange.dto.EsimDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SimInfoService {

    private final MsfMplatFormServerAdapter msfMplatFormServerAdapter;
    private final ProductInfoService productInfoService;

    //휴대폰 일련번호 유효성체크
    //public Map<String, Object> verifyPhoneSerialNumberInfo(ProductSearchCondition condition) {
    public Map<String, Object> verifyPhoneSerialNumberInfo(PhoneSerialCondition condition) {
        //1. 단말 재고조회 ( 휴대폰목록조회에서 사용하는 것과 같은걸 사용. 추후 분리여부 검토필요)
        //     단말목록조회에서는 로그인사용자의 매장 보유 재고 단말코드를 가져와서 단말목록조회 조건절에 추가하여 사용
        //     휴대폰 일련번호 유효성체크에서 매장재고 조회는 조건절이 추가됨 (휴대폰코드와 휴대폰일련번호)으로 쿼리를 분리하거나 해야하나?
        //parameter 1 - 로그인세션의 매장코드?(stor_cd) 대리점코드?(agent_cd) >> 현재는 매장코드로 조회함.
        //parameter 2 - 입력값 : prodId (선택한 휴대폰 상품코드 (고객포탈 관리코드) )
        //parameter 3 - 입력값 : prodSn (휴대폰일련번호)

        String imei = productInfoService.getPhoneInventory(condition);
        System.out.println("imei: " + imei);
        //단말재고관리 테이블의 IMEI 가 하나만 들어가 있음. 맞나???? 두개 있어야하는게 아닌지???
        // rtnCode "0000" 정상
        // rtnCode "0100" 재고없음
        // rtnCode "0200" 부정사용주장 단말이다
        // rtnCode "0300" 기기원부조회 실패
        Map<String, Object> rtnMap = new HashMap<>();
        String rtnCode = "0000";
        String rtnMessage = "사용 가능한 휴대폰 일련번호 입니다.";

        if (!StringUtils.hasText(imei)) {
            rtnCode = "0100";
            rtnCode = "유효하지 않은 휴대폰 일련번호 입니다.";
        }

        //2. 부정사용주장 단말확인
        boolean isValidImei = checkAbuseImeiList(Arrays.asList(imei, ""));
        if (isValidImei) {
            rtnCode = "0200";
            rtnMessage = "유효하지 않은 휴대폰 일련번호 입니다."; //부정사용주장 단말이다
        }

        //3. 기기원부조회 (Y13)
        //String indCd, String imei, int uploadPhoneSrlNo, String code, String eid
        //EsimSvcImpl.getY13(String indCd, String imei, int uploadPhoneSrlNo, String code, String eid)
        //rtnCode = "0300";
        //rtnCode = "유효하지 않은 휴대폰 일련번호 입니다.";

        rtnMap.put("rtnCode", rtnCode);
        rtnMap.put("rtnMessage", rtnMessage);
        return rtnMap;
    }

    //USIM 정보 유효성체크
    //고객포탈 URI : /msp/moscIntmMgmtAjax.do
    public Map<String, Object> verifyUsimInfo(MspJuoSubInfoCondition condition) {
        Map<String, Object> rtnMap = new HashMap<>();
        String rtnCode = "";
        String rtnMessage = "";
        String orgnId = ""; //유심의 접점코드(ORGN_ID)를 조회?

        //유심 재고조회는 관리자에서 안보임. 추후 있다면 추가필요함.
        //1. 불량유심 사용제한
        int failUsimCnt = 0;
        failUsimCnt = this.getFailUsims(condition.getIccId());
        if (failUsimCnt > 0) { //불량유심 사용제한에 포함된 경우 사용자정보 업데이트 - 스마트에도 필요한지 검토필요함.
            this.setFailUsims(condition.getIccId());
            rtnCode = "0100";
            rtnMessage = "유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.";
        }

        //2. 명의도용 추가피해 방지를 위한 유심재사용 확인
        int checkValidUsimCount = 0;
        checkValidUsimCount = this.checkValidUsimNo(condition.getIccId());
        if (checkValidUsimCount > 0) {
            rtnCode = "0200";
            rtnMessage = "유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.";
        }

        //3. USIM 유효성체크 (X85)
        MoscInqrUsimUsePsblOutDTO moscInqrUsimUsePsblOutDTO = new MoscInqrUsimUsePsblOutDTO();
        if (failUsimCnt == 0 && checkValidUsimCount == 0) {
            try {
                moscInqrUsimUsePsblOutDTO = this.moscIntmMgmtSO(condition);
            } catch (SocketTimeoutException e) {
            }

            if (moscInqrUsimUsePsblOutDTO == null) {
                rtnCode = "0300";
                rtnMessage = "유효하지 않은 USIM 번호 입니다. \n사용 불가한 USIM 입니다. \n새 USIM을 구매하여 재 시도 바랍니다.";
            } else {
                if ("Y".equals(moscInqrUsimUsePsblOutDTO.getPsblYn())) {
                    //USIM 접점코드(ORGN_ID) 조회
                    orgnId = this.getUsimOrgnId(condition.getIccId());

                    rtnCode = "0000";
                    rtnMessage = "사용 가능한 USIM 번호 입니다.";
                }
                //잠시 밖으로 둠. prx 연결하면 삭제할 것
                //orgnId = "V000001105";
                //rtnCode = "0000";
                //rtnMessage = "사용 가능한 USIM 번호 입니다.";
            }
        }

        rtnMap.put("rtnCode", rtnCode);
        rtnMap.put("rtnMessage", rtnMessage);
        rtnMap.put("USIM_ORGN_ID", orgnId);
        return rtnMap;
    }

    //eSIM 정보 유효성체크
    //public Map<String, Object> verifyEsimInfo(ProductSearchCondition condition) {
    public Map<String, Object> verifyEsimInfo(EsimDto esimDto) {
        Map<String, Object> rtnMap = new HashMap<>();
        String rtnCode = "";
        String rtnMessage = "";

        EsimDto resDto = new EsimDto();
        String eid = esimDto.getEid();
        String imei1 = esimDto.getImei1();
        String imei2 = esimDto.getImei2();

        if (this.checkAbuseImeiList(Arrays.asList(imei1, imei2))) {
            EsimDto abuseResDto = new EsimDto();
            abuseResDto.setResultCode("9901");
            rtnMap.put("resDto", abuseResDto);
            return rtnMap;
        }

        int uploadPhoneSrlNo = this.msfUploadPhoneInfo(eid, imei1, imei2);
        esimDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
        resDto = this.eSimChk(esimDto);
        resDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);

        rtnMap.put("resDto", resDto);
        return rtnMap;
    }


    //--------------------------- [USIM] 여기서부터는 공통으로 빼야할 사항으로 보임 START ------------------------------
    //불량유심 사용제한 확인
    public int getFailUsims(String iccId) {
        //apiInterfaceServer + "/storeUsim/failUsim" >> parameter :: iccId
        //건수를 받음.
        return 0;
    }

    //불량유심 사용제한한 사용자 업데이트 (스마트에도 해당이 되려나? 사용자는 실제 가입하려는 사람이 아니라 판매자인데..)
    public void setFailUsims(String iccId) {
        //apiInterfaceServer + "/storeUsim/updateFailUsim" >> parameter :: iccId
        //건수를 받음.
    }

    //명의도용 추가피해 방지를 위한 유심재사용 확인
    public int checkValidUsimNo(String iccId) {
        int vaildUsimCnt = 0;
        //apiInterfaceServer + "//appform/checkValidUsimNo" >> parameter :: iccId
        //건수를 받음.
        return vaildUsimCnt;
    }

    //USIM 접점코드 조회
    public String getUsimOrgnId(String iccId) {
        String orgnId = "";
        //apiInterfaceServer + "/msp/sellUsimMgmtOrgnId" >> parameter :: iccId
        //건수를 받음.
        return orgnId;
    }

    //USIM 정보 유효성체크 - MplatFormService 로 이동해야함.
    public MoscInqrUsimUsePsblOutDTO moscIntmMgmtSO(MspJuoSubInfoCondition condition) throws SocketTimeoutException {
        //public MoscInqrUsimUsePsblOutDTO moscIntmMgmtSO(JuoSubInfoDto condition) throws SocketTimeoutException {
        MoscInqrUsimUsePsblOutDTO moscInqrUsimUsePsblOutDTO = new MoscInqrUsimUsePsblOutDTO();

        HashMap<String, String> param = new HashMap<String, String>();
        String iccId = StringUtil.NVL(condition.getIccId(), "");
        param.put("appEventCd", "X85");
        param.put("iccid", iccId);

        msfMplatFormServerAdapter.callService(param, moscInqrUsimUsePsblOutDTO, 30000);
        return moscInqrUsimUsePsblOutDTO;
    }

    //--------------------------- [USIM] 여기서부터는 공통으로 빼야할 사항으로 보임 END ------------------------------
    //--------------------------- [eSIM] 여기서부터는 공통으로 빼야할 사항으로 보임 START ------------------------------
    //부정사용주장 단말 확인 (메인 메소드) - imei 갯수만큼 돌겠지요. 해봤자 2개니까~~ 최대 2회
    public boolean checkAbuseImeiList(List<String> imeis) {
        boolean isAbuse = false;

        for (String imei : imeis) {
            if (StringUtils.isEmpty(imei)) {
                continue;
            }

            isAbuse = this.existsAbuseImei(imei);
            if (isAbuse) {
                this.saveAbuseImeiHist(imei);
                break;
            }
        }
        return isAbuse;
    }

    //부정사용주장 단말 조회
    private boolean existsAbuseImei(String imei) {
        boolean exits = false;
        //apiInterfaceServer + "/appform/existsAbuseImei" >> parameter :: iccId
        return exits;
    }

    //부정사용주장 단말일 경우 NMCP_ABUSE_IMEI_HIST 이력저장 >> 해야겠죠??
    private void saveAbuseImeiHist(String imei) {
        //스마트 로그인한 사용자 아이디로 저장필요
        /*String userId = "";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession != null) {
            userId = userSession.getUserId();
        }*/

        /*AbuseImeiHistDto abuseImeiHistDto = new AbuseImeiHistDto();
        abuseImeiHistDto.setImei(imei);
        abuseImeiHistDto.setAccessIp(ipstatisticService.getClientIp());
        abuseImeiHistDto.setUserId(userId);
        esimDao.insertAbuseImeiHist(abuseImeiHistDto);*/
    }

    //Y13 - MplatFormService 로 이동해야함.
    //Y12 - MplatFormService 로 이동해야함.
    //Y14 - MplatFormService 로 이동해야함.
    //Y15 - MplatFormService 로 이동해야함.

    private int msfUploadPhoneInfo(String eid, String imei1, String imei2) {
        return msfUploadPhoneInfo(eid, imei1, imei2, "");
    }

    private int msfUploadPhoneInfo(String eid, String imei1, String imei2, String prntsContractNo) {

        int uploadPhoneSrlNo = 0;
        /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        McpUploadPhoneInfoDto mcpUploadPhoneInfoDto = new McpUploadPhoneInfoDto();

        try {
            String userId = "";
            String accessIp = ipstatisticService.getClientIp();
            UserSessionDto userSession = SessionUtils.getUserCookieBean();
            if (userSession != null) {
                userId = userSession.getUserId();
            }

            mcpUploadPhoneInfoDto.setReqModelName("");
            mcpUploadPhoneInfoDto.setModelId("");
            mcpUploadPhoneInfoDto.setReqPhoneSn("");
            mcpUploadPhoneInfoDto.setReqUsimSn("");
            mcpUploadPhoneInfoDto.setEid(eid);
            mcpUploadPhoneInfoDto.setImei1(imei1);
            mcpUploadPhoneInfoDto.setImei2(imei2);
            mcpUploadPhoneInfoDto.setUploadPhoneImg("");
            mcpUploadPhoneInfoDto.setAccessIp(accessIp);
            mcpUploadPhoneInfoDto.setUserId(userId);
            mcpUploadPhoneInfoDto.setPrntsContractNo(prntsContractNo);
            uploadPhoneSrlNo = eSimSvc.insertMcpUploadPhoneInfoDto(mcpUploadPhoneInfoDto);//테스트 입니다...

        } catch (DataAccessException e) {
        } catch (Exception e) {
        }*/

        return uploadPhoneSrlNo;
    }

    public EsimDto eSimChk(EsimDto reqDto) {
        EsimDto resDto = new EsimDto();
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
    //--------------------------- [eSIM] 여기서부터는 공통으로 빼야할 사항으로 보임 END ------------------------------

}
