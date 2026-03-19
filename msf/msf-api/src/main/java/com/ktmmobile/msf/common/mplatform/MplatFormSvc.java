package com.ktmmobile.msf.common.mplatform;

import com.ktmmobile.msf.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.common.mplatform.vo.MpDataSharingResVO;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscCombDtlResVO;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscSubMstCombChgRes;
import com.ktmmobile.msf.common.mplatform.vo.MpNumberChangeVO;
import com.ktmmobile.msf.common.mplatform.vo.MpPcsLostCnlChgVO;
import com.ktmmobile.msf.common.mplatform.vo.MpPcsLostInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpPhoneNoListVO;
import com.ktmmobile.msf.common.mplatform.vo.MpSuspenChgVO;
import com.ktmmobile.msf.common.mplatform.vo.MpSuspenCnlChgInVO;
import com.ktmmobile.msf.common.mplatform.vo.MpSuspenCnlPosInfoInVO;
import com.ktmmobile.msf.common.mplatform.vo.MpSuspenPosHisVO;
import com.ktmmobile.msf.common.mplatform.vo.MpUsimChangeVO;
import com.ktmmobile.msf.common.mplatform.vo.MpUsimCheckVO;
import com.ktmmobile.msf.common.mplatform.vo.SvcChgValdChkVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * M플랫폼 연동 서비스. 규칙: service.서비스명칭Svc.
 * 기존 MCP MplatFormService 와 동일 구조.
 * getParamMap → (LOCAL 이면 getVo / 아니면 adapter.callService) → vo 반환.
 */
@Service
public class MplatFormSvc {

    private static final Logger logger = LoggerFactory.getLogger(MplatFormSvc.class);

    /** X01 가입정보조회 (ASIS perMyktfInfo) */
    public static final String APP_EVENT_CD_X01 = "X01";
    public static final String APP_EVENT_CD_X20 = "X20";
    /** X26 일시정지이력조회 */
    public static final String APP_EVENT_CD_X26 = "X26";
    /** X28 일시정지해제가능여부조회 */
    public static final String APP_EVENT_CD_X28 = "X28";
    /** X30 일시정지해제신청 */
    public static final String APP_EVENT_CD_X30 = "X30";
    /** X33 분실신고가능여부조회 */
    public static final String APP_EVENT_CD_X33 = "X33";
    /** X35 분실신고 해제(분실복구) */
    public static final String APP_EVENT_CD_X35 = "X35";
    /** Y04 계약정보 유효성체크 (휴대폰 인증) */
    public static final String APP_EVENT_CD_Y04 = "Y04";
    /** X87 결합 서비스 계약 조회 */
    public static final String APP_EVENT_CD_X87 = "X87";
    /** Y44 서브회선 마스터 결합 신청 (아무나 SOLO) */
    public static final String APP_EVENT_CD_Y44 = "Y44";
    /** X69 데이터쉐어링 사전체크·가입가능대상 조회 */
    public static final String APP_EVENT_CD_X69 = "X69";
    /** X70 데이터쉐어링 가입(결합)/해지 */
    public static final String APP_EVENT_CD_X70 = "X70";
    /** X71 데이터쉐어링 결합 중인 대상 조회 */
    public static final String APP_EVENT_CD_X71 = "X71";
    /** X21 부가서비스 신청 */
    public static final String APP_EVENT_CD_X21 = "X21";
    /** X38 부가서비스 해지 */
    public static final String APP_EVENT_CD_X38 = "X38";
    /** X32 번호변경 */
    public static final String APP_EVENT_CD_X32 = "X32";
    /** X85 USIM 유효성 체크 */
    public static final String APP_EVENT_CD_X85 = "X85";
    /** UC0 USIM 변경 */
    public static final String APP_EVENT_CD_UC0 = "UC0";
    /** Y24 상품변경 사전체크 (단말보험 가입가능여부 포함) */
    public static final String APP_EVENT_CD_Y24 = "Y24";
    /** NU1 희망번호 조회 */
    public static final String APP_EVENT_CD_NU1 = "NU1";
    /** NU2 희망번호 예약/취소 */
    public static final String APP_EVENT_CD_NU2 = "NU2";

    @Autowired
    private MplatFormServerAdapter mplatFormServerAdapter;

    /** 기존 MCP 와 동일: LOCAL 이면 Mock, 아니면 실제 M플랫폼 호출 */
    @Value("${SERVER_NAME:}")
    private String serverLocation;

    @Value("${mplatform.user-id:}")
    private String mplatformUserId;

    /**
     * X01 가입정보조회. ASIS MplatFormService.perMyktfInfo() 와 동일.
     * ncn, ctn, custId 로 M플랫폼 호출 → addr, email, homeTel, initActivationDate 반환.
     */
    public MpPerMyktfInfoVO perMyktfInfo(String ncn, String ctn, String custId) {
        MpPerMyktfInfoVO vo = new MpPerMyktfInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X01);
        if ("LOCAL".equals(serverLocation)) {
            getVo(1, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * X20 가입중 부가서비스 조회. 기존 MCP getAddSvcInfoDto() 와 동일.
     */
    public MpAddSvcInfoDto getAddSvcInfoDto(String ncn, String ctn, String custId) {
        MpAddSvcInfoDto vo = new MpAddSvcInfoDto();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X20);

        if ("LOCAL".equals(serverLocation)) {
            getVo(20, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * X21 부가서비스 신청. 기존 MCP MplatFormService.regSvcChg() 와 동일.
     */
    public MpRegSvcChgVO regSvcChg(String ncn, String ctn, String custId, String soc, String ftrNewParam) {
        MpRegSvcChgVO vo = new MpRegSvcChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X21);
        param.put("soc", soc != null ? soc : "");
        param.put("ftrNewParam", ftrNewParam != null ? ftrNewParam : "");
        if ("LOCAL".equals(serverLocation)) {
            getVo(21, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * X38 부가서비스 해지. 기존 MCP MplatFormService.moscRegSvcCanChg() 와 동일.
     */
    public MpMoscRegSvcCanChgInVO moscRegSvcCanChg(String ncn, String ctn, String custId, String soc) {
        MpMoscRegSvcCanChgInVO vo = new MpMoscRegSvcCanChgInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X38);
        param.put("soc", soc != null ? soc : "");
        if ("LOCAL".equals(serverLocation)) {
            getVo(38, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo);
        }
        return vo;
    }

    /**
     * 기존 MCP getParamMap() 동일: ncn, ctn, custId, userid, appEventCd.
     * ASIS MplatFormService.getParamMap() 키 규칙 준수 (userid 소문자).
     */
    private HashMap<String, String> getParamMap(String ncn, String ctn, String custId, String eventCd) {
        HashMap<String, String> param = new HashMap<>();
        String userId = mplatformUserId != null ? mplatformUserId : "";
        param.put("ncn", ncn != null ? ncn : "");
        param.put("ctn", ctn != null ? ctn : "");
        param.put("custId", custId != null ? custId : "");
        param.put("userid", userId);
        param.put("appEventCd", eventCd);
        return param;
    }

    /**
     * 테스트를 위해 정상 리턴을 강제. 기존 MCP getVo(int param, CommonXmlVO vo) 동일.
     * case 1: X01 가입정보조회 Mock, case 20: X20 가입중부가서비스조회 Mock
     */
    private void getVo(int param, CommonXmlVO vo) {
        String responseXml;
        if (param == 1) {
            responseXml = "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X01</appEventCd><appSendDateTime>20160112153248</appSendDateTime><appRecvDateTime>20160112153246</appRecvDateTime><appLgDateTime>20160112153246</appLgDateTime><appNstepUserId>91060728</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><addr>인천 옹진군 영흥면 선재로34번길 141 </addr><email>bluemoor9521@naver.com</email><homeTel>01075116741</homeTel><initActivationDate>20140807163028</initActivationDate></outDto></return>";
        } else if (param == 20) {
            responseXml = "<return><bizHeader><appEntrPrsnId>KIS</appEntrPrsnId><appAgncCd>AA00364</appAgncCd><appEventCd>X20</appEventCd><appSendDateTime>20220331105448</appSendDateTime><appRecvDateTime>20220331105446</appRecvDateTime><appLgDateTime>20220331105446</appLgDateTime><appNstepUserId>91225330</appNstepUserId><appOrderId></appOrderId></bizHeader><commHeader><globalNo>9122533020220331105042626</globalNo><encYn></encYn><responseType>N</responseType><responseCode></responseCode><responseLogcd></responseLogcd><responseTitle></responseTitle><responseBasic></responseBasic><langCode></langCode><filler></filler></commHeader><outDto><outDto><effectiveDate>20211110144758</effectiveDate><prodHstSeq>300000783571478</prodHstSeq><soc>PL19AS353</soc><socDescription>M 요금할인 5000(VAT포함)</socDescription><socRateValue>-4,546 WON</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731236</prodHstSeq><soc>MPAYBLOCK</soc><socDescription>휴대폰결제 이용거부</socDescription><socRateValue>Free</socRateValue></outDto><outDto><effectiveDate>20191031174605</effectiveDate><prodHstSeq>300000444731240</prodHstSeq><soc>NOSPAM3</soc><socDescription>정보제공사업자번호차단</socDescription><socRateValue>Free</socRateValue></outDto></outDto></return>";
        } else if (param == 21) {
            responseXml = "<return><bizHeader><appEventCd>X21</appEventCd></bizHeader><commHeader><globalNo>9122533020220311145132712</globalNo><responseType>N</responseType><responseCode></responseCode><responseBasic></responseBasic></commHeader></return>";
        } else if (param == 38) {
            responseXml = "<return><bizHeader><appEventCd>X38</appEventCd></bizHeader><commHeader><globalNo>9106072820160112153243531</globalNo><responseType>N</responseType><responseCode></responseCode><responseBasic></responseBasic></commHeader></return>";
        } else {
            responseXml = "<return><commHeader><responseType>N</responseType></commHeader></return>";
        }
        vo.setResponseXml(responseXml);
        vo.toResponseParse();
    }

    /**
     * X26 일시정지 이력 조회.
     */
    public MpSuspenPosHisVO suspenPosHis(String ncn, String ctn, String custId, String termGubun) {
        MpSuspenPosHisVO vo = new MpSuspenPosHisVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X26);
        param.put("termGubun", termGubun != null ? termGubun : "");
        if ("LOCAL".equals(serverLocation)) {
            getVo(26, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * X28 일시정지 해제 가능 여부 조회.
     */
    public MpSuspenCnlPosInfoInVO suspenCnlPosInfo(String ncn, String ctn, String custId) {
        MpSuspenCnlPosInfoInVO vo = new MpSuspenCnlPosInfoInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X28);
        if ("LOCAL".equals(serverLocation)) {
            getVo(28, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * X30 일시정지 해제 신청.
     *
     * @param pwdType       PP: 일시정지 암호, CP: 개인정보 암호
     * @param cpPwdInsert   해제 비밀번호(4~8자리)
     * @return 성공 여부
     */
    public boolean suspenCnlChgIn(String ncn, String ctn, String custId, String pwdType, String cpPwdInsert) {
        MpSuspenCnlChgInVO vo = new MpSuspenCnlChgInVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X30);
        param.put("pwdType", pwdType != null ? pwdType : "PP");
        param.put("strPwdNumInsert", cpPwdInsert != null ? cpPwdInsert : "");
        if ("LOCAL".equals(serverLocation)) {
            getVo(30, vo);
            return vo.isSuccess();
        }
        return mplatFormServerAdapter.callService(param, vo, 30000) && vo.isSuccess();
    }

    /**
     * X33 분실신고 가능 여부 조회.
     */
    public MpPcsLostInfoVO pcsLostInfo(String ncn, String ctn, String custId) {
        MpPcsLostInfoVO vo = new MpPcsLostInfoVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X33);
        if ("LOCAL".equals(serverLocation)) {
            getVo(33, vo);
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * X35 분실신고 해제(분실복구) 신청.
     *
     * @param pwdType        PP: 일시정지 암호, CP: 개인정보 암호
     * @param strPwdNumInsert 4~8자리 비밀번호
     * @return 성공 여부
     */
    public boolean pcsLostCnlChg(String ncn, String ctn, String custId, String strPwdNumInsert, String pwdType) {
        MpPcsLostCnlChgVO vo = new MpPcsLostCnlChgVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X35);
        param.put("strPwdNumInsert", strPwdNumInsert != null ? strPwdNumInsert : "");
        param.put("pwdType", pwdType != null ? pwdType : "PP");
        if ("LOCAL".equals(serverLocation)) {
            getVo(35, vo);
            return vo.isSuccess();
        }
        return mplatFormServerAdapter.callService(param, vo, 30000) && vo.isSuccess();
    }

    /**
     * X87 결합 서비스 계약 조회. 아무나 SOLO 체크 시 기존 결합 유무(IS_COMBIN) 판단에 사용.
     */
    public MpMoscCombDtlResVO moscCombSvcInfoList(String custId, String ncn, String ctn) {
        MpMoscCombDtlResVO vo = new MpMoscCombDtlResVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X87);
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType></commHeader><outDto><combTypeNm/><combProdNm/><engtPerdMonsNum/><combDcTypeNm/><combDcTypeDtl/></outDto></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * Y44 서브회선 마스터 결합 신청 (아무나 SOLO 결합 처리).
     *
     * @param mstSvcContId 마스터 서비스 계약 ID (공통코드 MasterCombineLineInfo DTL_CD)
     */
    public MpMoscSubMstCombChgRes moscSubMstCombChg(String ncn, String ctn, String custId, String mstSvcContId) {
        MpMoscSubMstCombChgRes vo = new MpMoscSubMstCombChgRes();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_Y44);
        param.put("mstSvcContId", mstSvcContId != null ? mstSvcContId : "");
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType></commHeader><outDto><resultCd>0000</resultCd><resultMsg>성공</resultMsg></outDto></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * X69 데이터쉐어링 사전체크 및 가입 가능 대상 조회.
     */
    public MpDataSharingResVO moscDataSharingChk(String custId, String ncn, String ctn) {
        MpDataSharingResVO vo = new MpDataSharingResVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X69);
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType></commHeader><outDto><outDataSharingDto><rsltInd>Y</rsltInd><svcNo>01000000000</svcNo><efctStDt/><rsltMsg/></outDataSharingDto></outDto></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * X70 데이터쉐어링 가입(결합) 또는 해지.
     *
     * @param opmdSvcNo     데이터쉐어링 대상 전화번호 (가입 시 개통된 번호, 해지 시 해지할 번호)
     * @param opmdWorkDivCd A: 결합(가입), C: 해지
     */
    public boolean moscDataSharingSave(String custId, String ncn, String ctn, String opmdSvcNo, String opmdWorkDivCd) {
        SvcChgValdChkVO vo = new SvcChgValdChkVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X70);
        param.put("opmdSvcNo", opmdSvcNo != null ? opmdSvcNo : "");
        param.put("opmdWorkDivCd", "A".equals(opmdWorkDivCd) || "C".equals(opmdWorkDivCd) ? opmdWorkDivCd : "A");
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType></commHeader></return>");
            vo.toResponseParse();
            return true;
        }
        return mplatFormServerAdapter.callService(param, vo, 30000) && vo.isSuccess();
    }

    /**
     * NU1 희망번호 조회. ASIS AppformSvcImpl.getPhoneNoList() 와 동일.
     * wantNo: 희망하는 번호 패턴(끝 4자리 등)
     */
    public MpPhoneNoListVO requestPhoneNoList(String ncn, String ctn, String custId, String wantNo) {
        MpPhoneNoListVO vo = new MpPhoneNoListVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_NU1);
        param.put("wantNo", wantNo != null ? wantNo : "");
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType><globalNo>nu1mock001</globalNo></commHeader><outDto><outDto><tlphNo>01012341234</tlphNo></outDto><outDto><tlphNo>01056785678</tlphNo></outDto></outDto></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * NU2 희망번호 예약. ASIS sendOsstService(NU2) 와 동일.
     * tlphNo: 예약할 전화번호
     */
    public SvcChgValdChkVO reservePhoneNo(String ncn, String ctn, String custId, String tlphNo) {
        SvcChgValdChkVO vo = new SvcChgValdChkVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_NU2);
        param.put("tlphNo", tlphNo != null ? tlphNo : "");
        param.put("workDivCd", "A");  // A: 예약
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType><globalNo>nu2mock001</globalNo></commHeader></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * NU2 희망번호 예약 취소. ASIS sendOsstService(NU2, WORK_CODE_RES_CANCEL) 와 동일.
     */
    public SvcChgValdChkVO cancelReservedPhoneNo(String ncn, String ctn, String custId, String tlphNo) {
        SvcChgValdChkVO vo = new SvcChgValdChkVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_NU2);
        param.put("tlphNo", tlphNo != null ? tlphNo : "");
        param.put("workDivCd", "C");  // C: 취소
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType><globalNo>nu2cncl001</globalNo></commHeader></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * X32 번호변경 처리. ASIS MplatFormService.numChgeChg() 와 동일.
     * newTlphNo: 변경할 새 번호
     */
    public MpNumberChangeVO numChgeChg(String ncn, String ctn, String custId, String newTlphNo) {
        MpNumberChangeVO vo = new MpNumberChangeVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X32);
        param.put("newTlphNo", newTlphNo != null ? newTlphNo : "");
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType><globalNo>x32mock001</globalNo></commHeader><outDto><newTlphNo>" + newTlphNo + "</newTlphNo></outDto></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * X85 USIM 유효성 체크. ASIS MplatFormService.moscIntmMgmtSO() 와 동일.
     * usimNo: USIM 번호(ICCID)
     */
    public MpUsimCheckVO moscIntmMgmtSO(String ncn, String ctn, String custId, String usimNo) {
        MpUsimCheckVO vo = new MpUsimCheckVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X85);
        param.put("usimNo", usimNo != null ? usimNo : "");
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType><globalNo>x85mock001</globalNo></commHeader><outDto><usimNo>" + usimNo + "</usimNo><usimSts>01</usimSts><usimStsCd>정상</usimStsCd></outDto></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * UC0 USIM 변경 처리. ASIS usimChangeUC0() 와 동일.
     * newUsimNo: 새 USIM 번호(ICCID)
     */
    public MpUsimChangeVO usimChangeUC0(String ncn, String ctn, String custId, String newUsimNo) {
        MpUsimChangeVO vo = new MpUsimChangeVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_UC0);
        param.put("newUsimNo", newUsimNo != null ? newUsimNo : "");
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType><globalNo>uc0mock001</globalNo></commHeader></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * Y24 상품변경 사전체크. 단말보험 가입가능여부 확인에 사용.
     * soc: 보험 SOC 코드
     */
    public SvcChgValdChkVO chkMoscCombSvcInfo(String ncn, String ctn, String custId, String soc) {
        SvcChgValdChkVO vo = new SvcChgValdChkVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_Y24);
        param.put("soc", soc != null ? soc : "");
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType></commHeader></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * X71 데이터쉐어링 결합 중인 대상 조회.
     */
    public MpDataSharingResVO mosharingList(String custId, String ncn, String ctn) {
        MpDataSharingResVO vo = new MpDataSharingResVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_X71);
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType></commHeader><outDto></outDto></return>");
            vo.toResponseParse();
        } else {
            mplatFormServerAdapter.callService(param, vo, 30000);
        }
        return vo;
    }

    /**
     * Y04 계약정보 유효성체크 (기기변경/서비스변경/명의변경/서비스해지 휴대폰 인증).
     * ASIS와 동일 구조: getParamMap(ncn, ctn, custId, "Y04") 후 ip( clientIp ) 추가.
     * MCP saveMplateSvcLog 에서 ip·url·mdlInd 보강하는 패턴과 동일.
     *
     * @param clientIp  요청 클라이언트 IP (M플랫폼 전달용, param "ip")
     * @param cIntUsrId 사용자 아이디 (있으면 userid override, 없으면 getParamMap 기본값)
     * @param ncn       사용자 서비스 계약번호 (M전산 조회값)
     * @param ctn       사용자 전화번호(휴대폰)
     * @param custId    고객번호 (M전산 조회값)
     * @return 유효 시 true
     */
    public boolean contractValdChk(String clientIp, String cIntUsrId, String ncn, String ctn, String custId) {
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, APP_EVENT_CD_Y04);
        if (cIntUsrId != null && !cIntUsrId.isEmpty()) {
            param.put("userid", cIntUsrId);
        }
        param.put("ip", clientIp != null ? clientIp : "");

        SvcChgValdChkVO vo = new SvcChgValdChkVO();
        if ("LOCAL".equals(serverLocation)) {
            vo.setResponseXml("<return><commHeader><responseType>N</responseType><responseCode>0</responseCode></commHeader></return>");
            vo.toResponseParse();
            return true;
        }
        boolean ok = mplatFormServerAdapter.callService(param, vo);
        if (!ok) return false;
        return vo.isSuccess();
    }
}
