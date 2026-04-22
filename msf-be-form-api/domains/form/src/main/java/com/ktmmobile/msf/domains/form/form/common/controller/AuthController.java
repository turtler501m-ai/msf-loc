package com.ktmmobile.msf.domains.form.form.common.controller;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.service.AuthInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class AuthController {

    private final AuthInfoService authInfoService;

    //KTM 고객인증 (핸드폰번호, 고객명) :: 추후 common 패키지로 이동하여 구성될 예정. 공통 controller 명칭 구상중?
    @PostMapping("/ktmmember/auth")
    public CommonResponse<MspJuoSubInfoResponse> authKtmMember(@RequestBody @Validated MspJuoSubInfoCondition condition) {
        return ResponseUtils.ok(authInfoService.getJuoSubInfo(condition));
    }

    //청구계정아이디 조회 (고객포탈:x) >> 타인가능?
    @PostMapping("/verifyBillInfo")
    //1. KTM모바일 고객인증 >> 핸드폰번호, 고객명
    //2. 청구계정아이디 조회
    public CommonResponse<MspJuoBanInfoResponse> verifyBillInfo(@RequestBody @Validated MspJuoBanInfoCondition condition) {
        return ResponseUtils.ok(authInfoService.verifyBillInfo(condition));
    }
    /*SELECT *
            --EXPIRATION_DATE
    FROM MSP_JUO_FEATURE_INFO
    WHERE
            CONTRACT_NUM = '626380439'
            --AND SOC = #{soc}
        --AND SYSDATE BETWEEN TO_DATE(EFFECTIVE_DATE, 'YYYYMMDDHH24MISS')
        --	AND ADD_MONTHS(TO_DATE(EXPIRATION_DATE, 'YYYYMMDDHH24MISS'),#{intAddMonths})
    AND ROWNUM = 1
    */

    //신용카드인증 (고객포탈:/crdtCardAthnInfoAjax.do)
    @PostMapping("/crdtCardAthnInfo")
    public CommonResponse<Map<String, Object>> crdtCardAthnInfo(@RequestBody @Validated MspJuoSubInfoCondition condition) {
        return ResponseUtils.ok(authInfoService.crdtCardAthnInfo(condition));
    }

    //계좌번호인증 (고객포탈:/nice/accountCheckAjax.do)
    @PostMapping("/accountCheck")
    public CommonResponse<Map<String, Object>> accountCheck(@RequestBody @Validated MspJuoSubInfoCondition condition) {
        return ResponseUtils.ok(authInfoService.accountCheck(condition));
    }


}
