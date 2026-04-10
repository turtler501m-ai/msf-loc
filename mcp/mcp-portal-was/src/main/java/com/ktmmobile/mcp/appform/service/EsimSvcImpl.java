package com.ktmmobile.mcp.appform.service;

import com.ktmmobile.mcp.appform.dao.EsimDao;
import com.ktmmobile.mcp.appform.dto.AbuseImeiHistDto;
import com.ktmmobile.mcp.appform.dto.EsimDto;
import com.ktmmobile.mcp.appform.dto.McpEsimOmdTraceDto;
import com.ktmmobile.mcp.appform.dto.McpUploadPhoneInfoDto;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.vo.MoscBfacChkOmdIntmVO;
import com.ktmmobile.mcp.common.mplatform.vo.MoscRetvIntmMdlSpecInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MoscRetvIntmMdlSpecInfoVO.SpecSbstDto;
import com.ktmmobile.mcp.common.mplatform.vo.MoscRetvIntmOrrgInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MoscTrtOmdIntmVO;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EsimSvcImpl implements EsimSvc {

    private static final Logger logger = LoggerFactory.getLogger(EsimSvcImpl.class);

    @Autowired
    private MplatFormService mplatFormService;

    @Autowired
    private EsimDao esimDao;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int insertMcpUploadPhoneInfoDto(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto) {
        return esimDao.insertMcpUploadPhoneInfoDto(mcpUploadPhoneInfoDto);
    }

    @Override
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
            hmY13 = this.getY13(indCd, imei2, uploadPhoneSrlNo, "0000", eid);
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
                            hmY12 = this.getY12(indCd, intmMdlId, uploadPhoneSrlNo, "1000", eid);
                            returnCode = (String) hmY12.get("returnCode");
                            returnMsg = (String) hmY12.get("returnMsg");
                            if (!"N".equals(returnCode)) {
                                resDto.setResultCode("1000Y12");
                                resDto.setResultMsg(returnMsg);
                                return resDto;
                            }


                            moscRetvIntmMdlSpecInfoVO = (MoscRetvIntmMdlSpecInfoVO) hmY12.get("moscRetvIntmMdlSpecInfoVO");
                            List<SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                            if (specSbstList != null && !specSbstList.isEmpty()) {
                                for (SpecSbstDto dto : specSbstList) {
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
                        String wrkjobDivCd = "E";
                        hmY14 = this.getY14(wrkjobDivCd, imei2, "", uploadPhoneSrlNo, "2000", eid);
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
                        hmY14 = this.getY14(wrkjobDivCd, imei1, imei2, uploadPhoneSrlNo, "2000", eid);
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
                        hmY12 = this.getY12(indCd, intmMdlId, uploadPhoneSrlNo, "2000", eid);
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
                        List<SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                        if (specSbstList != null && !specSbstList.isEmpty()) {
                            for (SpecSbstDto dto : specSbstList) {
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
                hmY13 = this.getY13(indCd, imei1, uploadPhoneSrlNo, "34000", eid);
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
                    hmY14 = this.getY14(wrkjobDivCd, imei2, "", uploadPhoneSrlNo, "34000", eid);
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
                    hmY12 = this.getY12(indCd, modelId, uploadPhoneSrlNo, "34000", eid);
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
                    List<SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                    if (specSbstList != null && !specSbstList.isEmpty()) {
                        for (SpecSbstDto dto : specSbstList) {
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

                    String wrkjobDivCd = "A";
                    hmY14 = this.getY14(wrkjobDivCd, imei1, "", uploadPhoneSrlNo, "56000", eid);
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
            logger.info("error=>" + e.getMessage());
        }  catch (Exception e) {
            logger.info("error=>" + e.getMessage());
        }

        return resDto;
    }


    @Override
    public EsimDto omdReg(EsimDto reqDto) {

        logger.info("omdReg reqDto==>" + ObjectUtils.convertObjectToString(reqDto));


        EsimDto resDto = new EsimDto();
        String indCd = "";
        String returnCode = "";
        String returnMsg = "";
        String trtResult = "";
        String trtMsg = "";
        String resultCode = StringUtil.NVL(reqDto.getResultCode(), "");
        String imei1 = reqDto.getImei1();
        String imei2 = reqDto.getImei2();
        String eid = reqDto.getEid();
        int uploadPhoneSrlNo = reqDto.getUploadPhoneSrlNo();
        String modelId = reqDto.getModelId();
        String modelNm = reqDto.getModelNm();

        String modelIdOther = reqDto.getModelIdOther();
        String modelNmOther = reqDto.getModelNmOther();
        String intmSrlNoOther = reqDto.getIntmSrlNoOther();

        String intmSrlNo = reqDto.getIntmSrlNo();
        String moveTlcmIndCd = reqDto.getMoveTlcmIndCd();
        String moveCmncGnrtIndCd = reqDto.getMoveCmncGnrtIndCd();
        String moveCd = StringUtil.NVL(reqDto.getMoveCd(), ""); // 2000번만

        logger.error("Call omdReg uploadPhoneSrlNo==>" + uploadPhoneSrlNo);


        // 이중클릭 방지
        try {
            McpUploadPhoneInfoDto chkDto = esimDao.doubleChk(uploadPhoneSrlNo);
            if (chkDto != null) {
                String rstCd = StringUtil.NVL(chkDto.getRsltCd(), "");
                if ("S".equals(rstCd)) {
                    resDto.setResultCode("0000");
                    return resDto;
                }
            }
        } catch(DataAccessException e) {
            logger.info("DataAccessException");
        } catch (Exception e) {
            logger.info("error");
        }

        if ("K".equals(moveCd)) { // K이면 null인거임. 구분이 안됨.. 파라미터 변조인지 KT 인지..
            moveCd = "";
        }

        Map<String, Object> hmY15 = new HashMap<String, Object>();
        Map<String, Object> hmY12 = new HashMap<String, Object>();
        Map<String, Object> hmY14 = new HashMap<String, Object>();


        MoscTrtOmdIntmVO moscTrtOmdIntmVO = null; //15
        MoscRetvIntmMdlSpecInfoVO moscRetvIntmMdlSpecInfoVO = null; // 12
        MoscBfacChkOmdIntmVO moscBfacChkOmdIntmVO = null; // 14

        if ("1000".equals(resultCode)) {


            String wrkjobDivCd = "C";
            hmY14 = this.getY14(wrkjobDivCd, imei1, imei2, uploadPhoneSrlNo, "1000", eid);
            returnCode = (String) hmY14.get("returnCode");
            returnMsg = (String) hmY14.get("returnMsg");
            if (!"N".equals(returnCode)) {
                resDto.setResultCode("1000Y14C-1");
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
            resDto = this.updateMcpUploadPhoneInfo(reqDto); // 모델id/시리얼번호는 parameter로

            // db저장
        } else if ("2000".equals(resultCode)) {

            reqDto.setWrkjobDivCd("E");
            reqDto.setTrtDivCd("");
            reqDto.setImei1(imei2);
            reqDto.setImei2("");
//            reqDto.setIntmEtcPurpDivCd(moveCd);
            reqDto.setIntmEtcPurpDivCd("");
            hmY15 = this.getY15(reqDto, "2000E", eid);
            returnCode = (String) hmY15.get("returnCode");
            returnMsg = (String) hmY15.get("returnMsg");
            if (!"N".equals(returnCode)) {
                resDto.setResultCode("2000Y15-1");
                resDto.setResultMsg(returnMsg);
                return resDto;
            }
            moscTrtOmdIntmVO = (MoscTrtOmdIntmVO) hmY15.get("moscTrtOmdIntmVO");
            trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
            trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");
            if (!"Y".equals(trtResult)) {
                resDto.setResultCode("2001");
                resDto.setResultMsg(trtMsg);
                return resDto;
            }
            resDto = this.updateMcpUploadPhoneInfo(reqDto);

        } else if ("3000".equals(resultCode) || "4000".equals(resultCode)) {

            EsimDto y15CDto = new EsimDto();
            if ("4000".equals(resultCode)) { // 필수..
                NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
                if (sessNiceRes == null) {
                    resDto.setResultCode("CERTERROR");
                    resDto.setResultMsg("본인인증");
                    return resDto;
                }
                String birthDate = StringUtil.NVL(sessNiceRes.getBirthDate(), "");
                if (birthDate.length() > 7) {
                    birthDate = birthDate.substring(2, 8);
                }
                String gender = StringUtil.NVL(sessNiceRes.getGender(), ""); // 1:남자,0:여자
                if ("0".equals(gender)) {
                    gender = "2"; // 서비스에서 1:남자,2:여자
                }
                String mobileNo = StringUtil.NVL(sessNiceRes.getsMobileNo(), "");
                y15CDto.setBirthday(birthDate);
                y15CDto.setSexDiv(gender);
                y15CDto.setCtn(mobileNo);
            }

            // imei2 원부 등록
            EsimDto y15ADto = new EsimDto();
            y15ADto.setWrkjobDivCd("A");
            /**
             * y15ADto.setModelId(modelId);
             * y15ADto.setModelNm(modelNm);
             */
            y15ADto.setModelId(modelIdOther);
            y15ADto.setModelNm(modelNmOther);

            y15ADto.setIntmSrlNo(intmSrlNo);
            y15ADto.setEid(eid);
            y15ADto.setImei1(imei2);
            y15ADto.setImei2("");
            y15ADto.setIntmEtcPurpDivCd("O");
            y15ADto.setCmpnCdIfVal("KIS");
            y15ADto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            hmY15 = this.getY15(y15ADto, "34000A", eid);
            returnCode = (String) hmY15.get("returnCode");
            returnMsg = (String) hmY15.get("returnMsg");
            if (!"N".equals(returnCode)) {
                resDto.setResultCode("34000Y15-1");
                resDto.setResultMsg(returnMsg);
                return resDto;
            }

            // y15
            moscTrtOmdIntmVO = (MoscTrtOmdIntmVO) hmY15.get("moscTrtOmdIntmVO");
            trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
            trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");
            String y15ModelId = StringUtil.NVL(moscTrtOmdIntmVO.getIntmModelId(), "");
            if (!"Y".equals(trtResult)) {
                resDto.setResultCode("3401");
                resDto.setResultMsg(trtMsg);
                return resDto;
            }

            // imei2 원부 수정
            if (!y15ModelId.equals(modelId)) { // imei1 모델ID 과 등록한 모델ID가 다른경우가 있음

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    logger.info("error sleep");
                }
                EsimDto y15UDto = new EsimDto();
                y15UDto.setWrkjobDivCd("U");

                //y13, Y12
                y15UDto.setModelNm(modelNm);
                y15UDto.setModelId(modelId);
                y15UDto.setIntmSrlNo(intmSrlNo);
                y15UDto.setIntmEtcPurpDivCd(moveCd);
                y15UDto.setEid(eid);
                y15UDto.setTrtDivCd("1");
                y15UDto.setImei1(imei2);
                y15UDto.setImei2("");
                y15UDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
                hmY15 = this.getY15(y15UDto, "34000U", eid);
                returnCode = (String) hmY15.get("returnCode");
                returnMsg = (String) hmY15.get("returnMsg");
                if (!"N".equals(returnCode)) {
                    resDto.setResultCode("34000Y15-U-1");
                    resDto.setResultMsg(returnMsg);
                    return resDto;
                }

                moscTrtOmdIntmVO = (MoscTrtOmdIntmVO) hmY15.get("moscTrtOmdIntmVO");
                trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
                trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");
                if (!"Y".equals(trtResult)) {
                    resDto.setResultCode("34000Y15-U-2");
                    resDto.setResultMsg(trtMsg);
                    return resDto;
                }
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.info("error sleep");
            }
            // 듀얼심결합
            y15CDto.setWrkjobDivCd("C");
            //y13, Y12
            y15CDto.setModelId(modelId);
            y15CDto.setModelNm(modelNm);


            y15CDto.setImei1(imei1);
            y15CDto.setImei2(imei2);
            y15CDto.setIntmEtcPurpDivCd("");
            y15CDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            hmY15 = this.getY15(y15CDto, "34000C", eid);
            returnCode = (String) hmY15.get("returnCode");
            returnMsg = (String) hmY15.get("returnMsg");
            if (!"N".equals(returnCode)) {
                resDto.setResultCode("34000Y15-C-1");
                resDto.setResultMsg(returnMsg);
                return resDto;
            }

            moscTrtOmdIntmVO = (MoscTrtOmdIntmVO) hmY15.get("moscTrtOmdIntmVO");
            trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
            trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");
            if (!"Y".equals(trtResult)) {
                resDto.setResultCode("34000Y15-C-2");
                resDto.setResultMsg(trtMsg);
                return resDto;
            }
            reqDto.setIntmSrlNo(StringUtil.NVL(intmSrlNoOther, imei2)); // imei2 y14
            resDto = this.updateMcpUploadPhoneInfo(reqDto);

        } else if ("5000".equals(resultCode) || "6000".equals(resultCode)) {

            String imei1ModelNm = "";
            String imei1ModelId = "";
            String imei2ModelNm = "";


            // imei1 원부등록
            reqDto.setWrkjobDivCd("A");
            reqDto.setImei1(imei1);
            reqDto.setImei2("");
            reqDto.setCmpnCdIfVal("KIS");
            reqDto.setIntmEtcPurpDivCd("O");
            reqDto.setEid("");
            hmY15 = this.getY15(reqDto, "56000A", eid);
            returnCode = (String) hmY15.get("returnCode");
            returnMsg = (String) hmY15.get("returnMsg");
            if (!"N".equals(returnCode)) {
                resDto.setResultCode("56000Y15-1");
                resDto.setResultMsg(returnMsg);
                return resDto;
            }

            moscTrtOmdIntmVO = (MoscTrtOmdIntmVO) hmY15.get("moscTrtOmdIntmVO");
            trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
            trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");
            if (!"Y".equals(trtResult)) {
                resDto.setResultCode("56000Y15-2");
                resDto.setResultMsg(trtMsg);
                return resDto;
            }
            imei1ModelNm = StringUtil.NVL(moscTrtOmdIntmVO.getIntmModelNm(), "");
            imei1ModelId = StringUtil.NVL(moscTrtOmdIntmVO.getIntmModelId(), "");

            // imei2 원부등록
            reqDto.setWrkjobDivCd("A");
            reqDto.setImei1(imei2);
            reqDto.setImei2("");
            reqDto.setCmpnCdIfVal("KIS");
            reqDto.setIntmEtcPurpDivCd("O");
            reqDto.setEid(eid);
            hmY15 = this.getY15(reqDto, "56000A", eid);
            returnCode = (String) hmY15.get("returnCode");
            returnMsg = (String) hmY15.get("returnMsg");
            if (!"N".equals(returnCode)) {
                resDto.setResultCode("56000Y15-3");
                resDto.setResultMsg(returnMsg);
                return resDto;
            }

            moscTrtOmdIntmVO = (MoscTrtOmdIntmVO) hmY15.get("moscTrtOmdIntmVO");
            trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
            trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");
            if (!"Y".equals(trtResult)) {
                resDto.setResultCode("56000Y15-4");
                resDto.setResultMsg(trtMsg);
                return resDto;
            }

            imei2ModelNm = StringUtil.NVL(moscTrtOmdIntmVO.getIntmModelNm(), "");

            // 	modelNm 비교
            if ("".equals(imei1ModelNm) || !imei1ModelNm.equals(imei2ModelNm)) {
                resDto.setResultCode("56000Y15-4");
                resDto.setResultMsg(returnMsg);
                return resDto;
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.info("error sleep");
            }
            // imei1+imei2 듀얼심 결합
            reqDto.setModelId(imei1ModelId);
            reqDto.setModelNm(imei1ModelNm);
            reqDto.setWrkjobDivCd("C");
            reqDto.setImei1(imei1);
            reqDto.setImei2(imei2);
            reqDto.setCmpnCdIfVal("");
            reqDto.setIntmEtcPurpDivCd("");
            reqDto.setEid("");
            hmY15 = this.getY15(reqDto, "56000C", eid);
            returnCode = (String) hmY15.get("returnCode");
            returnMsg = (String) hmY15.get("returnMsg");
            if (!"N".equals(returnCode)) {
                resDto.setResultCode("56000Y15-5");
                resDto.setResultMsg(returnMsg);
                return resDto;
            }

            moscTrtOmdIntmVO = (MoscTrtOmdIntmVO) hmY15.get("moscTrtOmdIntmVO");
            trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
            trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");

            if (!"Y".equals(trtResult)) {
                resDto.setResultCode("56000Y15-6");
                resDto.setResultMsg(trtMsg);
                return resDto;
            }

            // Y12
            indCd = "1";
            hmY12 = this.getY12(indCd, modelId, uploadPhoneSrlNo, "56000", eid);
            returnCode = (String) hmY12.get("returnCode");
            returnMsg = (String) hmY12.get("returnMsg");
            if (!"N".equals(returnCode)) {
                resDto.setResultCode("56000Y12-1");
                resDto.setResultMsg(returnMsg);
                return resDto;
            }
            moscRetvIntmMdlSpecInfoVO = (MoscRetvIntmMdlSpecInfoVO) hmY12.get("moscRetvIntmMdlSpecInfoVO");
            List<SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
            if (specSbstList != null && !specSbstList.isEmpty()) {
                for (SpecSbstDto dto : specSbstList) {
                    String intmSpecTypeCd = StringUtil.NVL(dto.getIntmSpecTypeCd(), "");
                    if ("110".equals(intmSpecTypeCd)) { // Y:자급제 // N: SKT/LG/KT
                        moveTlcmIndCd = dto.getIntmSpecSbst();
                    } else if ("111".equals(intmSpecTypeCd)) { // N:5G아님 //  Y:5G
                        moveCmncGnrtIndCd = dto.getIntmSpecSbst();
                    }
                }
            }
            reqDto.setIntmSrlNo(imei2);
            reqDto.setMoveTlcmIndCd(moveTlcmIndCd);
            reqDto.setMoveCmncGnrtIndCd(moveCmncGnrtIndCd);
            resDto = this.updateMcpUploadPhoneInfo(reqDto); // id/nameY15우선순위 ,시리얼:y13,통신사코드 : y12
        }

        return resDto;
    }


    private EsimDto updateMcpUploadPhoneInfo(EsimDto reqDto) {
return updateMcpUploadPhoneInfo(reqDto,"S");
    }

    private EsimDto updateMcpUploadPhoneInfo(EsimDto reqDto , String RstCd) {
        EsimDto resDto = new EsimDto();
        int uploadPhoneSrlNo = reqDto.getUploadPhoneSrlNo();
        String modelId = reqDto.getModelId();
        String modelNm = reqDto.getModelNm();
        String intmSrlNo = reqDto.getIntmSrlNo();
        String moveTlcmIndCd = reqDto.getMoveTlcmIndCd();
        String moveCmncGnrtIndCd = reqDto.getMoveCmncGnrtIndCd();

        try {
            McpUploadPhoneInfoDto mcpUploadPhoneInfoDto = new McpUploadPhoneInfoDto();
            mcpUploadPhoneInfoDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            mcpUploadPhoneInfoDto.setModelId(modelId);
            mcpUploadPhoneInfoDto.setReqModelName(modelNm);
            mcpUploadPhoneInfoDto.setReqPhoneSn(intmSrlNo);
            mcpUploadPhoneInfoDto.setMoveTlcmIndCd(moveTlcmIndCd);
            mcpUploadPhoneInfoDto.setMoveCmncGnrtIndCd(moveCmncGnrtIndCd);
            mcpUploadPhoneInfoDto.setRstCd(RstCd);
            int result = esimDao.updateMcpUploadPhoneInfoDto(mcpUploadPhoneInfoDto);

            if (result > 0) {
                resDto.setResultCode("0000");
            } else {
                resDto.setResultCode("DBERROR");
            }
        } catch(DataAccessException e) {
            resDto.setResultCode("DataAccessException");
        } catch (Exception e) {
            resDto.setResultCode("DBERROR");
        }

        return resDto;
    }


    @Override
    public EsimDto niceAuthChk(EsimDto reqDto) {
        EsimDto resDto = new EsimDto();
        String resultCode = "4001";

        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        logger.info("sessNiceRes==>" + ObjectUtils.convertObjectToString(sessNiceRes));
        if (sessNiceRes == null) {
            resDto.setResultCode(resultCode);
            return resDto;
        }
        String cstmrName = "";
        String cstmrNativeRrn01 = "";
        String name = "";
        String birthDate = "";
        try {
            name = sessNiceRes.getName();
            birthDate = StringUtil.NVL(sessNiceRes.getBirthDate(), "");
            cstmrName = reqDto.getCstmrName();
            cstmrNativeRrn01 = reqDto.getCstmrNativeRrn01();
            if (birthDate.length() > 7) {
                birthDate = birthDate.substring(2, 8);
            }

            if (birthDate.equals(cstmrNativeRrn01) && name.equals(cstmrName)) {
                resultCode = "0000";
            }
        } catch(IndexOutOfBoundsException e) {
            logger.info("IndexOutOfBoundsException");
        } catch (Exception e) {
            logger.info("Exception");
        }
        resDto.setResultCode(resultCode);
        return resDto;
    }

    private Map<String, Object> getY12(String indCd, String intmMdlId, int uploadPhoneSrlNo, String code, String eid) {


        Map<String, Object> hm = new HashMap<String, Object>();
        String returnCode = "N";
        String returnMsg = "";

        MoscRetvIntmMdlSpecInfoVO moscRetvIntmMdlSpecInfoVO = null;
        try {
            moscRetvIntmMdlSpecInfoVO = mplatFormService.moscRetvIntmMdlSpecInfo(indCd, intmMdlId);
            if (moscRetvIntmMdlSpecInfoVO == null || !moscRetvIntmMdlSpecInfoVO.isSuccess()) {
                returnCode = StringUtil.NVL(moscRetvIntmMdlSpecInfoVO.getResultCode(), "");
                returnMsg = moscRetvIntmMdlSpecInfoVO.getSvcMsg();
            }
        } catch (SelfServiceException e) {
            logger.info("Exception SelfServiceException");
            returnCode = moscRetvIntmMdlSpecInfoVO.getResultCode();
            returnMsg = moscRetvIntmMdlSpecInfoVO.getSvcMsg();
        }  catch (Exception e) {
            logger.info("Exception 12");
            returnCode = moscRetvIntmMdlSpecInfoVO.getResultCode();
            returnMsg = moscRetvIntmMdlSpecInfoVO.getSvcMsg();
        }

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String globalNo = moscRetvIntmMdlSpecInfoVO.getGlobalNo();

            McpEsimOmdTraceDto mcpEsimOmdTraceDto = new McpEsimOmdTraceDto();
            mcpEsimOmdTraceDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            mcpEsimOmdTraceDto.setGlobalNo(globalNo);
            mcpEsimOmdTraceDto.setEventCode("Y12");
            mcpEsimOmdTraceDto.setPrcsSbst(returnMsg);
            mcpEsimOmdTraceDto.setRsltCd(returnCode);
            mcpEsimOmdTraceDto.setAccessIp(ipstatisticService.getClientIp());
            mcpEsimOmdTraceDto.setAccessUrl(request.getRequestURI());
            mcpEsimOmdTraceDto.setTrtmRsltSmst(code);
            mcpEsimOmdTraceDto.setEid(eid);
            int result = esimDao.insertMcpEsimOmdTrace(mcpEsimOmdTraceDto);
        } catch(DataAccessException e) {
            logger.error("Y12 insertMcpEsimOmdTrace error= {}", e.getMessage());
        } catch (Exception e) {
            logger.info("Y12 insertMcpEsimOmdTrace error");
        }

        logger.info("Y12 returnCode==>" + returnCode);
        logger.info("Y12 returnMsg==>" + returnMsg);

        hm.put("returnCode", returnCode);
        hm.put("returnMsg", returnMsg);
        hm.put("moscRetvIntmMdlSpecInfoVO", moscRetvIntmMdlSpecInfoVO);
        return hm;
    }

    private Map<String, Object> getY13(String indCd, String imei, int uploadPhoneSrlNo, String code, String eid) {


        Map<String, Object> hm = new HashMap<String, Object>();
        String returnCode = "N";
        String returnMsg = "";

        MoscRetvIntmOrrgInfoVO moscRetvIntmOrrgInfoVO = null;

        try {

            moscRetvIntmOrrgInfoVO = mplatFormService.moscRetvIntmOrrgInfo(indCd, imei);
            if (moscRetvIntmOrrgInfoVO == null || !moscRetvIntmOrrgInfoVO.isSuccess()) {
                returnCode = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getResultCode(), "");
                returnMsg = moscRetvIntmOrrgInfoVO.getSvcMsg();
            }
        } catch (SelfServiceException e) {
            logger.info("SelfServiceException 13");
            returnCode = moscRetvIntmOrrgInfoVO.getResultCode();
            returnMsg = moscRetvIntmOrrgInfoVO.getSvcMsg();
        } catch (Exception e) {
            logger.info("Exception 13");
            returnCode = moscRetvIntmOrrgInfoVO.getResultCode();
            returnMsg = moscRetvIntmOrrgInfoVO.getSvcMsg();
        }

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String globalNo = moscRetvIntmOrrgInfoVO.getGlobalNo();

            McpEsimOmdTraceDto mcpEsimOmdTraceDto = new McpEsimOmdTraceDto();
            mcpEsimOmdTraceDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            mcpEsimOmdTraceDto.setGlobalNo(globalNo);
            mcpEsimOmdTraceDto.setEventCode("Y13");
            mcpEsimOmdTraceDto.setPrcsSbst(returnMsg);
            mcpEsimOmdTraceDto.setRsltCd(returnCode);
            mcpEsimOmdTraceDto.setAccessIp(ipstatisticService.getClientIp());
            mcpEsimOmdTraceDto.setAccessUrl(request.getRequestURI());
            mcpEsimOmdTraceDto.setTrtmRsltSmst(code);
            mcpEsimOmdTraceDto.setEid(eid);
            int result = esimDao.insertMcpEsimOmdTrace(mcpEsimOmdTraceDto);
        } catch(DataAccessException e) {
            logger.error("Y13 insertMcpEsimOmdTrace error", e.getMessage());
        } catch (Exception e) {
            logger.info("Y13 insertMcpEsimOmdTrace error");
        }


        logger.info("Y13 returnCode==>" + returnCode);
        logger.info("Y13 returnMsg==>" + returnMsg);

        hm.put("returnCode", returnCode);
        hm.put("returnMsg", returnMsg);
        hm.put("moscRetvIntmOrrgInfoVO", moscRetvIntmOrrgInfoVO);
        return hm;
    }

    private Map<String, Object> getY14(String wrkjobDivCd, String imei1, String imei2, int uploadPhoneSrlNo, String code, String eid) {


        Map<String, Object> hm = new HashMap<String, Object>();
        String returnCode = "N";
        String returnMsg = "";
        String rsltYn = "Y";

        MoscBfacChkOmdIntmVO moscBfacChkOmdIntmVO = null;
        try {
            moscBfacChkOmdIntmVO = mplatFormService.moscBfacChkOmdIntm(wrkjobDivCd, imei1, imei2);
            if (moscBfacChkOmdIntmVO == null || !moscBfacChkOmdIntmVO.isSuccess()) {
                returnCode = StringUtil.NVL(moscBfacChkOmdIntmVO.getResultCode(), "");
                returnMsg = moscBfacChkOmdIntmVO.getSvcMsg();
                rsltYn = "N";
            } else {
                String trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                String trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");
                if (!"Y".equals(trtResult)) {
                    returnCode = "N|N";
                    rsltYn = "N";
                }
                returnMsg = trtMsg;
            }
        } catch (SelfServiceException e) {
            logger.info("SelfServiceException 14");
            rsltYn = "N";
            returnCode = moscBfacChkOmdIntmVO.getResultCode();
            returnMsg = moscBfacChkOmdIntmVO.getSvcMsg();
        }  catch (Exception e) {
            logger.info("Exception 14");
            rsltYn = "N";
            returnCode = moscBfacChkOmdIntmVO.getResultCode();
            returnMsg = moscBfacChkOmdIntmVO.getSvcMsg();
        }

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String globalNo = moscBfacChkOmdIntmVO.getGlobalNo();

            McpEsimOmdTraceDto mcpEsimOmdTraceDto = new McpEsimOmdTraceDto();
            mcpEsimOmdTraceDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            mcpEsimOmdTraceDto.setGlobalNo(globalNo);
            mcpEsimOmdTraceDto.setEventCode("Y14");
            mcpEsimOmdTraceDto.setPrcsSbst(returnMsg);
            mcpEsimOmdTraceDto.setRsltCd(returnCode);
            mcpEsimOmdTraceDto.setAccessIp(ipstatisticService.getClientIp());
            mcpEsimOmdTraceDto.setAccessUrl(request.getRequestURI());
            mcpEsimOmdTraceDto.setTrtmRsltSmst(code);
            mcpEsimOmdTraceDto.setEid(eid);
            int result = esimDao.insertMcpEsimOmdTrace(mcpEsimOmdTraceDto);
        } catch(DataAccessException e) {
            logger.info("Y14 insertMcpEsimOmdTrace DataAccessException");
        } catch (Exception e) {
            logger.info("Y14 insertMcpEsimOmdTrace error");
        }

        // MCP_UPLOAD_PHONE_INFO 업데이트
        try {

            McpUploadPhoneInfoDto mcpUploadPhoneInfoDto = new McpUploadPhoneInfoDto();
            mcpUploadPhoneInfoDto.setEvntCd("Y14");
            mcpUploadPhoneInfoDto.setRsltCd(returnCode);
            mcpUploadPhoneInfoDto.setRsltYn(rsltYn);
            mcpUploadPhoneInfoDto.setRsltMsg(returnMsg);
            mcpUploadPhoneInfoDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            int result = esimDao.updateServiceRst(mcpUploadPhoneInfoDto);

        } catch(DataAccessException e) {
            logger.info("Y14 insertMcpEsimOmdTrace DataAccessException");
        } catch (Exception e) {
            logger.info("Y14 updateServiceRstl error");
        }

        logger.info("Y14 returnCode==>" + returnCode);
        logger.info("Y14 returnMsg==>" + returnMsg);
        if ("N|N".equals(returnCode)) {
            returnCode = "N";
        }
        hm.put("returnCode", returnCode);
        hm.put("returnMsg", returnMsg);
        hm.put("moscBfacChkOmdIntmVO", moscBfacChkOmdIntmVO);
        return hm;
    }

    private Map<String, Object> getY15(EsimDto esimDto, String code, String eid) {

        Map<String, Object> hm = new HashMap<String, Object>();
        String returnCode = "N";
        String returnMsg = "";
        String rsltYn = "Y";

        int uploadPhoneSrlNo = esimDto.getUploadPhoneSrlNo();

        MoscTrtOmdIntmVO moscTrtOmdIntmVO = null;
        try {
            moscTrtOmdIntmVO = mplatFormService.moscTrtOmdIntm(esimDto);
            if (moscTrtOmdIntmVO == null || !moscTrtOmdIntmVO.isSuccess()) {
                returnCode = StringUtil.NVL(moscTrtOmdIntmVO.getResultCode(), "");
                returnMsg = moscTrtOmdIntmVO.getSvcMsg();
                rsltYn = "N";
            } else {
                String trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
                String trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");
                if (!"Y".equals(trtResult)) {
                    returnCode = "N|N";
                    rsltYn = "N";
                }
                returnMsg = trtMsg;
            }
        } catch (SelfServiceException e) {
            logger.info("SelfServiceException 15");
            rsltYn = "N";
            returnCode = moscTrtOmdIntmVO.getResultCode();
            returnMsg = moscTrtOmdIntmVO.getSvcMsg();
        }  catch (Exception e) {
            logger.info("Exception 15");
            rsltYn = "N";
            returnCode = moscTrtOmdIntmVO.getResultCode();
            returnMsg = moscTrtOmdIntmVO.getSvcMsg();
        }

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String globalNo = moscTrtOmdIntmVO.getGlobalNo();

            McpEsimOmdTraceDto mcpEsimOmdTraceDto = new McpEsimOmdTraceDto();
            mcpEsimOmdTraceDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            mcpEsimOmdTraceDto.setGlobalNo(globalNo);
            mcpEsimOmdTraceDto.setEventCode("Y15");
            mcpEsimOmdTraceDto.setPrcsSbst(returnMsg);
            mcpEsimOmdTraceDto.setRsltCd(returnCode);
            mcpEsimOmdTraceDto.setAccessIp(ipstatisticService.getClientIp());
            mcpEsimOmdTraceDto.setAccessUrl(request.getRequestURI());
            mcpEsimOmdTraceDto.setTrtmRsltSmst(code);
            mcpEsimOmdTraceDto.setEid(eid);
            int result = esimDao.insertMcpEsimOmdTrace(mcpEsimOmdTraceDto);
        } catch(DataAccessException e) {
            logger.error("Y15 insertMcpEsimOmdTrace error\"= {}", e.getMessage());
        } catch (Exception e) {
            logger.info("Y15 insertMcpEsimOmdTrace error");
        }

        // MCP_UPLOAD_PHONE_INFO 업데이트
        try {

            McpUploadPhoneInfoDto mcpUploadPhoneInfoDto = new McpUploadPhoneInfoDto();
            mcpUploadPhoneInfoDto.setEvntCd("Y15");
            mcpUploadPhoneInfoDto.setRsltCd(returnCode);
            mcpUploadPhoneInfoDto.setRsltYn(rsltYn);
            mcpUploadPhoneInfoDto.setRsltMsg(returnMsg);
            mcpUploadPhoneInfoDto.setUploadPhoneSrlNo(uploadPhoneSrlNo);
            int result = esimDao.updateServiceRst(mcpUploadPhoneInfoDto);

        } catch (SelfServiceException e) {
            logger.info("Y14 updateServiceRstl error");
        } catch (Exception e) {
            logger.info("Y14 updateServiceRstl error");
        }

        logger.info("Y15 returnCode==>" + returnCode);
        logger.info("Y15 returnMsg==>" + returnMsg);
        if ("N|N".equals(returnCode)) {
            returnCode = "N";
        }
        hm.put("returnCode", returnCode);
        hm.put("returnMsg", returnMsg);
        hm.put("moscTrtOmdIntmVO", moscTrtOmdIntmVO);
        return hm;
    }


    @Override
    public EsimDto eSimWatch(EsimDto reqDto) {

        AuthSmsDto prntsAut = new AuthSmsDto();
        prntsAut.setMenu("eSimWatch");
        prntsAut = SessionUtils.getAuthSmsBean(prntsAut);

        if (prntsAut == null || StringUtils.isBlank(prntsAut.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_SESSION_EXCEPTION);
        }

        EsimDto resDto = new EsimDto();
        try {

            String indCd = "";
            String imei1 = reqDto.getImei1();
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
            hmY13 = this.getY13(indCd, imei1, uploadPhoneSrlNo, "0000", eid);
            returnCode = (String) hmY13.get("returnCode");

            // 1-1.imei2 원부조회 성공
            if ("N".equals(returnCode)) {
                moscRetvIntmOrrgInfoVO = (MoscRetvIntmOrrgInfoVO) hmY13.get("moscRetvIntmOrrgInfoVO");
                String lastIntmStatCd = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getLastIntmStatCd(), ""); // 최종기기상태코드
                String openRstrYn = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getOpenRstrYn(), ""); // 개통제한여부
                String eUiccId = StringUtil.NVL(moscRetvIntmOrrgInfoVO.geteUiccId(), "");
                String intmMdlId = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getIntmMdlId(), "");
                String intmSrlNo = StringUtil.NVL(moscRetvIntmOrrgInfoVO.getIntmSrlNo(), "");    //기기일련번호
                String modelNm = "";

                if (("01".equals(lastIntmStatCd) || "30".equals(lastIntmStatCd)) && "N".equals(openRstrYn)) {
                    if (!"".equals(eUiccId)) {

                        String moveTlcmIndCd = "";
                        String moveCmncGnrtIndCd = "";
                        if (eUiccId.equals(eid)) {
                            indCd = "1";
                            hmY12 = this.getY12(indCd, intmMdlId, uploadPhoneSrlNo, "1000", eid);
                            returnCode = (String) hmY12.get("returnCode");
                            returnMsg = (String) hmY12.get("returnMsg");
                            if (!"N".equals(returnCode)) {
                                resDto.setResultCode("1000E2");
                                resDto.setResultMsg(returnMsg);
                                return resDto;
                            }

                            moscRetvIntmMdlSpecInfoVO = (MoscRetvIntmMdlSpecInfoVO) hmY12.get("moscRetvIntmMdlSpecInfoVO");
                            List<SpecSbstDto> specSbstList = moscRetvIntmMdlSpecInfoVO.getSpecSbstList();
                            if (specSbstList != null && !specSbstList.isEmpty()) {
                                for (SpecSbstDto dto : specSbstList) {
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
                            resDto.setUploadPhoneSrlNo(reqDto.getUploadPhoneSrlNo());

                            /*
                            * 모회선 내국인
                            * 그리고 납부방법 지로 가 아닌경우
                            * */
                            String strResultCode = "1000";
                            if (!Constants.CSTMR_TYPE_NA.equals(prntsAut.getCstmrType()) ) {
                                strResultCode = "9999E1";
                                resDto = this.updateMcpUploadPhoneInfo(resDto, "N");
                            } else if (prntsAut.getReqPayType().equals("R")) {
                                strResultCode = "9999E2";
                                resDto = this.updateMcpUploadPhoneInfo(resDto,"N");
                            } else {
                                strResultCode = "1000";
                                resDto = this.updateMcpUploadPhoneInfo(resDto);
                            }

                            if ("0000".equals(resDto.getResultCode())) {
                                resDto.setResultCode(strResultCode);
                            } else {
                                throw new McpCommonException("9999", ExceptionMsgConstant.DB_EXCEPTION);
                            }


                        } else {
                            resDto.setModelId(intmMdlId); // y13
                            resDto.setModelNm(modelNm); // y12
                            resDto.setIntmSrlNo(intmSrlNo); // y13
                            resDto.setMoveTlcmIndCd(moveTlcmIndCd);
                            resDto.setMoveCmncGnrtIndCd(moveCmncGnrtIndCd);
                            resDto.setResultCode("1000E3");
                            resDto.setResultMsg("사용자알림문구1");
                        }
                    } else {
                        /*
                         * eUiccId 존재 하지 않음
                         * Y14 사전체크 후
                         * Y15 등록 처리
                         */
                        //Y14
                        String wrkjobDivCd = "E";
                        hmY14 = this.getY14(wrkjobDivCd, imei1, "", uploadPhoneSrlNo, "2000", eid);
                        returnCode = (String) hmY14.get("returnCode");
                        returnMsg = (String) hmY14.get("returnMsg");
                        if (!"N".equals(returnCode)) {
                            resDto.setResultCode("2000E1");
                            resDto.setResultMsg(returnMsg);
                            return resDto;
                        }
                        moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                        String trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                        String trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");
                        String y14intmMdlId = moscBfacChkOmdIntmVO.getIntmModelId();
                        String y14modelNm = moscBfacChkOmdIntmVO.getIntmModelNm();
                        String y14intmSeq = moscBfacChkOmdIntmVO.getIntmSeq();

                        if (!"Y".equals(trtResult)) {
                            resDto.setResultCode("2000E2");
                            resDto.setResultMsg(trtMsg);
                            return resDto;
                        }

                        /*
                         * Y15 등록 처리
                         * EID 등록처리
                         * TEST방법?????????
                         * wrkjobDivCd	작업구분코드	E : 듀얼심 EID 변경
                         */
                        reqDto.setWrkjobDivCd("E");
                        reqDto.setModelId(y14intmMdlId);
                        reqDto.setModelNm(y14modelNm);
                        reqDto.setIntmSrlNo(y14intmSeq);
                        reqDto.setTrtDivCd("");
                        reqDto.setImei2("");
                        reqDto.setIntmEtcPurpDivCd("");

                        Map<String, Object> hmY15 = new HashMap<String, Object>();
                        MoscTrtOmdIntmVO moscTrtOmdIntmVO = null; //15

                        hmY15 = this.getY15(reqDto, "2000E", eid);
                        returnCode = (String) hmY15.get("returnCode");
                        returnMsg = (String) hmY15.get("returnMsg");
                        if (!"N".equals(returnCode)) {
                            resDto.setResultCode("2000E3");
                            resDto.setResultMsg(returnMsg);
                            return resDto;
                        }
                        moscTrtOmdIntmVO = (MoscTrtOmdIntmVO) hmY15.get("moscTrtOmdIntmVO");
                        trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
                        trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");
                        if (!"Y".equals(trtResult)) {
                            resDto.setResultCode("2000E4");
                            resDto.setResultMsg(trtMsg);
                            return resDto;
                        } else {
                            resDto.setResultMsg("SUCCESS");
                        }

                        /*
                         * 모회선 내국인
                         * 그리고 납부방법 지로 가 아닌경우
                         * */
                        String strResultCode = "2000";
                        if (Constants.CSTMR_TYPE_NM.equals(prntsAut.getCstmrType()) ) {
                            strResultCode = "9999E1";
                            resDto = this.updateMcpUploadPhoneInfo(reqDto, "N");
                        } else if (prntsAut.getReqPayType().equals("R")) {
                            strResultCode = "9999E2";
                            resDto = this.updateMcpUploadPhoneInfo(reqDto,"N");
                        } else {
                            strResultCode = "2000";
                            resDto = this.updateMcpUploadPhoneInfo(reqDto);
                        }

                        if ("0000".equals(resDto.getResultCode())) {
                            resDto.setResultCode(strResultCode);
                        } else {
                            throw new McpCommonException("9999", ExceptionMsgConstant.DB_EXCEPTION);
                        }

                    }
                } else {
                    if ("10".equals(lastIntmStatCd)) {
                        resDto.setResultCode("1000E11");
                    } else if ("40".equals(lastIntmStatCd)) {
                        resDto.setResultCode("1000E12");
                    } else {
                        resDto.setResultCode("1000E1");
                    }
                    resDto.setResultMsg("사용자문구2");
                }
            } else {  // 1-2. imei2로 원부조회 실패

                String wrkjobDivCd = "A";
                hmY14 = this.getY14(wrkjobDivCd, imei1, "", uploadPhoneSrlNo, "56000", eid);
                returnCode = (String) hmY14.get("returnCode");
                returnMsg = (String) hmY14.get("returnMsg");

                if (!"N".equals(returnCode)) {
                    resDto.setResultCode("5000E1");
                    resDto.setResultMsg(returnMsg);
                    return resDto;
                }

                moscBfacChkOmdIntmVO = (MoscBfacChkOmdIntmVO) hmY14.get("moscBfacChkOmdIntmVO");
                String trtResult = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtResult(), "");
                String trtMsg = StringUtil.NVL(moscBfacChkOmdIntmVO.getTrtMsg(), "");

                if (!"Y".equals(trtResult)) {
                    resDto.setResultCode("5000E2");
                    resDto.setResultMsg(trtMsg);
                    return resDto;
                }

                String intmModelId = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmModelId(), "");
                String intmModelNm = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmModelNm(), "");
                String intmSeq = StringUtil.NVL(moscBfacChkOmdIntmVO.getIntmSeq(), "");


                resDto.setModelId(intmModelId);
                resDto.setModelNm(intmModelNm);
                resDto.setIntmSrlNo(intmSeq);

                if ("".equals(intmModelId)) {
                    resDto.setResultCode("5000E3");
                    resDto.setResultMsg(trtMsg);
                    return resDto;
                } else {
                    /*
                     * Y15 등록 처리
                     * EID 등록처리
                     * TEST방법?????????
                     * wrkjobDivCd	작업구분코드	A : 등록
                     */
                    reqDto.setModelId(intmModelId);
                    reqDto.setModelNm(intmModelNm);
                    reqDto.setIntmSrlNo(intmSeq);
                    reqDto.setWrkjobDivCd("A");
                    reqDto.setTrtDivCd("");
                    reqDto.setImei2("");
                    reqDto.setIntmEtcPurpDivCd("O");

                    Map<String, Object> hmY15 = new HashMap<String, Object>();
                    MoscTrtOmdIntmVO moscTrtOmdIntmVO = null; //15

                    hmY15 = this.getY15(reqDto, "5000E", eid);
                    returnCode = (String) hmY15.get("returnCode");
                    returnMsg = (String) hmY15.get("returnMsg");
                    if (!"N".equals(returnCode)) {
                        resDto.setResultCode("5000E4");
                        resDto.setResultMsg(returnMsg);
                        return resDto;
                    }
                    moscTrtOmdIntmVO = (MoscTrtOmdIntmVO) hmY15.get("moscTrtOmdIntmVO");
                    trtResult = StringUtil.NVL(moscTrtOmdIntmVO.getTrtResult(), "");
                    trtMsg = StringUtil.NVL(moscTrtOmdIntmVO.getTrtMsg(), "");

                    if (!"Y".equals(trtResult)) {
                        resDto.setResultCode("5000E5");
                        resDto.setResultMsg(trtMsg);
                        return resDto;
                    } else {
                        resDto.setResultMsg("SUCCESS");
                    }

                    /**
                     * REQ_PHONE_SN
                     * Y14에서 조회값이 없을 경우
                     * imei 값으로 설정
                     */
                    resDto.setIntmSrlNo(StringUtil.NVL(intmSeq, imei1));
                    resDto.setUploadPhoneSrlNo(reqDto.getUploadPhoneSrlNo());
                    /*
                     * 모회선 내국인
                     * 그리고 납부방법 지로 가 아닌경우
                     * */
                    String strResultCode = "5000";
                    if (Constants.CSTMR_TYPE_NM.equals(prntsAut.getCstmrType()) ) {
                        strResultCode = "9999E1";
                        resDto = this.updateMcpUploadPhoneInfo(resDto, "N");
                    } else if (prntsAut.getReqPayType().equals("R")) {
                        strResultCode = "9999E2";
                        resDto = this.updateMcpUploadPhoneInfo(resDto,"N");
                    } else {
                        strResultCode = "5000";
                        resDto = this.updateMcpUploadPhoneInfo(resDto);
                    }

                    if ("0000".equals(resDto.getResultCode())) {
                        resDto.setResultCode(strResultCode);
                    } else {
                        throw new McpCommonException("9999", ExceptionMsgConstant.DB_EXCEPTION);
                    }
                }
            }


        } catch (SelfServiceException e) {
            logger.info("error=>" + e.getMessage());
        } catch (Exception e) {
            logger.info("error=>" + e.getMessage());
        }


        return resDto;
    }

    @Override
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

    private boolean existsAbuseImei(String imei) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiInterfaceServer + "/appform/existsAbuseImei", imei, Boolean.class);
    }

    private void saveAbuseImeiHist(String imei) {
        String userId = "";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession !=null) {
            userId = userSession.getUserId();
        }

        AbuseImeiHistDto abuseImeiHistDto = new AbuseImeiHistDto();
        abuseImeiHistDto.setImei(imei);
        abuseImeiHistDto.setAccessIp(ipstatisticService.getClientIp());
        abuseImeiHistDto.setUserId(userId);
        esimDao.insertAbuseImeiHist(abuseImeiHistDto);
    }
}
