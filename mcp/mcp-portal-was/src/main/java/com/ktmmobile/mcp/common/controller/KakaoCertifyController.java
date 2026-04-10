package com.ktmmobile.mcp.common.controller;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.KakaoDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.service.KakaoCertifyService;
import com.ktmmobile.mcp.common.service.NiceCertifySvc;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.AUTH_TYPE_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;


@Controller
public class KakaoCertifyController {
    private static Logger logger = LoggerFactory.getLogger(KakaoCertifyController.class);

    @Autowired
    private KakaoCertifyService kakaoCertifyService;

    @Autowired
    private NiceCertifySvc niceCertifySvc;


    @RequestMapping(value = {"/kakaoAuthPop.do", "/m/kakaoAuthPop.do"} )
    public String kakaoChangeover(Model model, @RequestParam(value="prcType", required=false, defaultValue="PARTIAL") String prcType){

        String rtnPage= "/portal/popup/kakaoAuthPop";  // 카카오 인증창
        String grpCd= "pSimpleAuth";

        if(!"P".equals(NmcpServiceUtils.getPlatFormCd())){
            rtnPage= "/mobile/popup/kakaoAuthPop";
            grpCd= "mSimpleAuth";
        }

        // 카카오 인증 이용가능여부 확인 (DB통제 페이지만)
        if("COMMON".equalsIgnoreCase(prcType)){
            NmcpCdDtlDto cdDtlDto= NmcpServiceUtils.getCodeNmDto(grpCd, "K");
            if(cdDtlDto == null || !DateTimeUtil.checkValidDate(cdDtlDto.getPstngStartDate(), cdDtlDto.getPstngEndDate())){
                throw new McpCommonException(AUTH_TYPE_EXCEPTION);
            }
        }

        // SMS 인증 완료 여부 확인 - 신규셀프개통인 경우만
        if(!niceCertifySvc.preChkSimpleAuth()){
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        return rtnPage;
    }

    @RequestMapping(value = {"/kakaoCert.do", "/m/kakaoCert.do"})
    @ResponseBody
    public Map<String, Object> callKakaoIdentityCert(KakaoDto kakaoDto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, JSONException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        logger.info("kakao ready userNm : " + kakaoDto.getName());
        logger.info("kakao ready brithdaty : " + kakaoDto.getBirthday());
        logger.info("kakao ready phone : " + kakaoDto.getPhone_No());
        Map<String, Object> reMap = new HashMap<>();
        if (ObjectUtils.isNotEmpty(kakaoDto)) {
            kakaoDto.setCertifyType(Constants.KAKAO_IDENTITY_VERIFICATION);
            reMap = this.kakaoCertifyService.cellKakaoCertify(kakaoDto);   // kakao key 생성 api
        }
        logger.info("kakao ready reMap : " + reMap.toString());
        return reMap;
    }

    @RequestMapping(value = {"/kakaoCertVerify.do", "/m/kakaoCertVerify.do"})
    @ResponseBody
    public Map<String, Object> selectCertVerify(KakaoDto kakaoDto) throws JSONException, IOException, InvalidAlgorithmParameterException, InvalidKeyException {

        logger.info("kakao verify txid : " + kakaoDto.getTxId());
        Map<String, Object> reMap = new HashMap<>();
        if (StringUtils.isNoneEmpty(kakaoDto.getTxId())) {
            reMap = this.kakaoCertifyService.selectCertVerify(kakaoDto);
        }
        logger.info("kakao verify reMap : " + reMap.toString());
        return reMap;
    }
}
