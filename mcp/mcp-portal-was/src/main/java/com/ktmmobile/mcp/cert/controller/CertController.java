package com.ktmmobile.mcp.cert.controller;

import com.ktmmobile.mcp.cert.dto.CertDto;
import com.ktmmobile.mcp.cert.service.CertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;


@Controller
public class CertController {

    private static final Logger logger = LoggerFactory.getLogger(CertController.class);
    
    @Autowired
    private CertService certSvc;

    
    /**
     * @설명 : 본인인증 정보 검증(인증한 정보와 사용자가 입력한 정보 일치 여부 확인)
     * @Author : wooki
     * @Date : 2023.11.30
     * @param  CertDto
     * @return Map<String, Object>
     */
    @RequestMapping(value="/auth/getCertInfo.do")
    @ResponseBody
    public Map<String,Object> getCertInfo(CertDto certDto) {

        Map<String,Object> rtnMap = new HashMap<String, Object>();
        
        rtnMap = certSvc.getCertInfo(certDto);
        
        return rtnMap;
    }  
}
