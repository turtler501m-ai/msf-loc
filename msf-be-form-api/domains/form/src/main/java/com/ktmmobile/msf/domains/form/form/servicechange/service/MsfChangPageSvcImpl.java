package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.ChangPageRepositoryImpl;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Service
public class MsfChangPageSvcImpl implements MsfChangPageSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfChangPageSvcImpl.class);

    @Autowired
    private ChangPageRepositoryImpl changPageRepositoryImpl;

    // [ASIS] interface 호출 소스 보관
    // @Value("${api.interface.server}")
    // private String apiInterfaceServer;

    @Override
    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        logger.debug("[MsfChangPage][selectMspAddInfo] start: ncn={}, queryId={}",
            svcCntrNo, "ChangPageMapper.selectMspAddInfo");
        try {
            // 기존 interface 호출 소스 보관
            // String callUrl = apiInterfaceServer + "/mypage/mspAddInfo";
            // RestTemplate restTemplate = new RestTemplate();
            // MspJuoAddInfoDto response = restTemplate.postForObject(callUrl, svcCntrNo, MspJuoAddInfoDto.class);
            MspJuoAddInfoDto response = changPageRepositoryImpl.selectMspAddInfo(svcCntrNo);
            logger.debug("[MsfChangPage][selectMspAddInfo] response: ncn={}, hasBody={}, remainPay={}, remainMonth={}",
                svcCntrNo, response != null, response != null ? response.getRemainPay() : null, response != null ? response.getRemainMonth() : null);
            return response;
        } catch (Exception e) {
            logger.error("[MsfChangPage][selectMspAddInfo] error: ncn={}, queryId={}",
                svcCntrNo, "ChangPageMapper.selectMspAddInfo", e);
            throw e;
        }
    }

    @Override
    public List<McpUserCntrMngDto> selectCntrList(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto != null) {
            params.put("customerId", userSessionDto.getCustomerId());
        }

        // [ASIS] interface 호출 소스 보관
        // RestTemplate restTemplate = new RestTemplate();
        // McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/changePage/cntrList", params, McpUserCntrMngDto[].class);
        // List<McpUserCntrMngDto> list = Optional.ofNullable(resultList).filter(r -> r.length != 0)
        //     .map(Arrays::asList).orElse(null);
        List<McpUserCntrMngDto> list = changPageRepositoryImpl.selectCntrList(params);

        if (list != null) {
            for (McpUserCntrMngDto dto : list) {
                String strUnUserSSn = dto.getUnUserSSn();
                dto.setAge(Integer.toString(getAge(strUnUserSSn)));
                if (strUnUserSSn != null && strUnUserSSn.length() > 5) {
                    dto.setBirth(strUnUserSSn.substring(0, 6));
                } else if (strUnUserSSn != null) {
                    dto.setBirth(strUnUserSSn);
                }
            }
        }
        return list;
    }

    @Override
    public McpUserCntrMngDto selectCntrListNoLogin(String contractNum) {
        if (contractNum == null || "".equals(contractNum)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setSvcCntrNo(contractNum);
        return selectCntrListNoLogin(userCntrMngDto);
    }

    @Override
    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        if (userCntrMngDto.getSvcCntrNo() == null && userCntrMngDto.getCntrMobileNo() == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        // [ASIS] interface 호출 소스 보관
        // RestTemplate restTemplate = new RestTemplate();
        // return restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);
        return changPageRepositoryImpl.selectCntrListNoLogin(userCntrMngDto);
    }

    private static int getAge(String idNum) {
        String today = "";
        String birthday = "";
        int myAge = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        today = formatter.format(new Date());
        if (idNum != null && idNum.trim().length() == 13) {
            if (idNum.charAt(6) == '1' || idNum.charAt(6) == '2' || idNum.charAt(6) == '5' || idNum.charAt(6) == '6') {
                birthday = "19" + idNum.substring(0, 6);
            } else if (idNum.charAt(6) == '*') {
                return -1;
            } else {
                birthday = "20" + idNum.substring(0, 6);
            }
        } else {
            return 0;
        }
        myAge = Integer.parseInt(today.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4));
        if (Integer.parseInt(today.substring(4)) < Integer.parseInt(birthday.substring(4))) myAge = myAge - 1;
        return myAge;
    }
}
