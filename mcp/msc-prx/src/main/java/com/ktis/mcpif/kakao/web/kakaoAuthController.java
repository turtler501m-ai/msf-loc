package com.ktis.mcpif.kakao.web;

import com.ktis.mcpif.kakao.service.kakaAuthService;
import com.ktis.mcpif.kakao.vo.KakaoDto;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Controller
public class kakaoAuthController {

    final private Logger logger = Logger.getLogger(getClass());

    @Resource(name = "kakaAuthService")
    private kakaAuthService kakaAuthService;

    @ResponseBody
    @RequestMapping("/kakaAuth.do")
    public String reqeustKakaAuth(KakaoDto kakaoDto) throws NoSuchAlgorithmException, IOException, KeyManagementException, JSONException {

        return this.kakaAuthService.cellKakaoCertify(kakaoDto);
    }

    @ResponseBody
    @RequestMapping(value = "/kakaVerify.do")
    public String kakaVerify(KakaoDto kakaoDto) throws NoSuchAlgorithmException, IOException, KeyManagementException, JSONException {
        logger.info("kakao verify txId: " + kakaoDto.getTxId());
        return this.kakaAuthService.selectKakaVerify(kakaoDto);
    }


}
