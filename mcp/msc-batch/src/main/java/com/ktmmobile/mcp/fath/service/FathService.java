package com.ktmmobile.mcp.fath.service;

import com.ktmmobile.mcp.acen.dao.AcenDao;
import com.ktmmobile.mcp.acen.dto.AcenDto;
import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.MPhoneNoListXmlVO;
import com.ktmmobile.mcp.common.mplatform.dto.MPhoneNoVo;
import com.ktmmobile.mcp.common.mplatform.dto.MSimpleOsstXmlVO;
import com.ktmmobile.mcp.common.mplatform.dto.McpRequestOsstDto;
import com.ktmmobile.mcp.common.mplatform.service.MplatFormService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.HttpClientUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.fath.dao.FathDao;
import com.ktmmobile.mcp.fath.dto.FathDto;
import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.ktmmobile.mcp.common.constant.Constants.*;

@Service
public class FathService {

    @Value("${api.interface.server}")
    private String apiInterfaceServer;
    private static final Logger logger = LoggerFactory.getLogger(FathService.class);

    @Autowired
    private FathDao fathDao;

    @Autowired
    private MplatFormService mplatFormService;

    @Value("${ext.url}")
    private String extUrl;

    @Value("${SERVER_NAME}")
    private String serverName;

    /**
     * 설명 : 셀프안면인증 URL 발송 대상조회 
     * @Date : 2025.12.19
     */
    public List<FathDto> selectFathSelfTrg() {
        return fathDao.selectFathSelfTrg();
    }
    
    /** 셀프안면인증 URL 발급 */
    public void insertFathSelfUrl(FathDto fathDto){
        fathDao.insertFathSelfUrl(fathDto);
    }

    /** 신청서 진행상태 변경 */
    public void updateReqStateCode(String resNo){
        fathDao.updateReqStateCode(resNo);
    }
    

}
