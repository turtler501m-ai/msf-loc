package com.ktmmobile.msf.form.servicechange.controller;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.form.newchange.dto.JuoSubInfoDto;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageUserService;
import com.ktmmobile.msf.form.servicechange.service.MsfNicePinService;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.NiceLogDto;
import com.ktmmobile.msf.system.common.util.EncryptUtil;
import com.ktmmobile.msf.system.common.util.SessionUtils;

@Controller
public class MsfNicePinController {

    private static Logger logger = LoggerFactory.getLogger(MsfNicePinController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    MsfNicePinService nicePinService;

    @Autowired
    MsfMypageUserService mypageUserService;

    @Autowired
    CertService certService;

    @Autowired
    MsfMypageSvc msfMypageSvc;

    /**
     * 설명 : 고객 ci 조회 prx 연동
     * @Date : 2024.01.08
     * @return
     */
    @RequestMapping(value="/auth/getNicePinCiAjax.do")
    @ResponseBody
    public Map<String,String> getNicePinCi(HttpServletRequest request, NiceLogDto niceLogDto
                                         , @RequestParam(value= "contractNum", required = false) String contractNum) {


        String lContractNum = contractNum;

        Map<String, String> rtnMap = new HashMap<>();
        // 2025-08-05 기변의 경우 세션에 담아둔 주민번호로 파라미터 SET
        JuoSubInfoDto changeAut = SessionUtils.getChangeAutCookieBean();
        if (changeAut != null && !StringUtils.isBlank(changeAut.getContractNum())) {
            if(Constants.CSTMR_TYPE_NM.equals(changeAut.getCustomerType())) {
                niceLogDto.setnBirthDate(changeAut.getCustomerSsn());
            } else {
                niceLogDto.setParamR2(changeAut.getCustomerSsn().substring(6));
            }
        }


        // 2024-12-17 인가된 사용자 체크
        Map<String, String> rtnChkAuthMap = new HashMap<>();
        if (StringUtils.isBlank(lContractNum)) { // esimWatch 는 패스
            if ("1".equals(niceLogDto.getNcType())) { // 미성년자
                rtnChkAuthMap = msfMypageSvc.checkAuthUser(niceLogDto.getnName(), niceLogDto.getnBirthDate());
            } else { // 그외
                rtnChkAuthMap = msfMypageSvc.checkAuthUser(niceLogDto.getName(), niceLogDto.getParamR1() + niceLogDto.getParamR2());
            }
            if (!"0000".equals(rtnChkAuthMap.get("returnCode"))) {
                return rtnChkAuthMap;
            }
        }

        // 2024-12-03 기변체크
        if (changeAut != null && !StringUtils.isBlank(changeAut.getContractNum())) {
            lContractNum = changeAut.getContractNum();
        }

        // 인자값 확인
        if (StringUtils.isBlank(lContractNum)) {
            if (StringUtils.isBlank(niceLogDto.getName())
                || StringUtils.isBlank(niceLogDto.getParamR1())
                || StringUtils.isBlank(niceLogDto.getParamR2())) {
                rtnMap.put("returnCode", "0001");
                rtnMap.put("returnMsg", F_BIND_EXCEPTION);
                return rtnMap;
            }
            // 생년월일 세팅
            niceLogDto.setBirthDate(niceLogDto.getParamR1() + niceLogDto.getParamR2());
        } else {
            // 2024-12-03 esimWatch, 기변일때
            if ("1".equals(niceLogDto.getNcType())) {
                // 2024-12-03 미성년자 일때 넘어온 정보로 셋팅
                if (StringUtils.isBlank(niceLogDto.getName())
                    || StringUtils.isBlank(niceLogDto.getParamR1())
                    || StringUtils.isBlank(niceLogDto.getParamR2())) {
                    rtnMap.put("returnCode", "0001");
                    rtnMap.put("returnMsg", F_BIND_EXCEPTION);
                    return rtnMap;
                }
                // 생년월일 세팅
                niceLogDto.setBirthDate(niceLogDto.getParamR1() + niceLogDto.getParamR2());
            } else {
                String certName = "";
                String certUserSsn = "";
                if (changeAut != null) {
                    // 2024-12-03 기변 내국인, 외국인 일때 DB값으로 세팅
                    if (StringUtils.isBlank(niceLogDto.getName())
                            || StringUtils.isBlank(niceLogDto.getParamR1())
                            || StringUtils.isBlank(niceLogDto.getParamR2())) {
                        rtnMap.put("returnCode", "0001");
                        rtnMap.put("returnMsg", F_BIND_EXCEPTION);
                        return rtnMap;
                    }
                    // 생년월일 세팅
                    niceLogDto.setBirthDate(niceLogDto.getParamR1() + niceLogDto.getParamR2());
                    // STEP 비교 데이터 셋팅
                    certName = niceLogDto.getName();
                    certUserSsn = EncryptUtil.ace256Enc(niceLogDto.getBirthDate());
                } else {
                    // 2024-12-03 esimWatch 내국인, 외국인 계약번호로 찾아온 고객정보 셋팅
                    Map<String, String> resOjb= mypageUserService.selectContractObj("","",lContractNum) ;

                    if (resOjb == null){
                        rtnMap.put("returnCode", "0001");
                        rtnMap.put("returnMsg", F_BIND_EXCEPTION);
                        return rtnMap;
                    }

                    // 사용자 주민번호 복호화 처리
                    String decUserSsn = "";
                    try{
                        decUserSsn = EncryptUtil.ace256Dec(resOjb.get("USER_SSN"));
                    } catch (CryptoException e) {
                        rtnMap.put("returnCode", "0002");
                        rtnMap.put("returnMsg", COMMON_EXCEPTION);
                        return rtnMap;
                    }
                    // 이름, 생년월일 세팅
                    niceLogDto.setName(resOjb.get("SUB_LINK_NAME"));
                    niceLogDto.setBirthDate(decUserSsn);
                    // STEP 비교 데이터 셋팅
                    certName = resOjb.get("SUB_LINK_NAME");
                    certUserSsn = resOjb.get("USER_SSN");
                }

                // ============ STEP START ============
                // 인증종류, 법정대리인구분, 이름, 생년월일, 계약번호
                String[] certKey= {"urlType", "moduType", "ncType", "name", "birthDate", "contractNum"};
                String[] certValue= {"chkNicePinInfo", "nicePin", niceLogDto.getNcType(), certName, certUserSsn, lContractNum};

                Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    rtnMap.put("returnCode", "STEP01");
                    rtnMap.put("returnMsg", vldReslt.get("RESULT_DESC"));
                    return rtnMap;
                }
                // ============ STEP END ============
            }
        }

        // PRX 연동
        Map<String, String> ciMap = nicePinService.getNicePinCi(niceLogDto);

        // prx 연동 결과 확인
        if("0000".equals(ciMap.get("returnCode"))){
            // 고객 ci조회 성공
            rtnMap.put("returnCode", AJAX_SUCCESS);

        }else{
            // 고객 ci조회 실패
            rtnMap.put("returnCode", ciMap.get("returnCode"));
            rtnMap.put("returnMsg", ciMap.get("returnMsg"));
        }

        return rtnMap;
    }

}
