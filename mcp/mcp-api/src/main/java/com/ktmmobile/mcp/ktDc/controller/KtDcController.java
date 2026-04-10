package com.ktmmobile.mcp.ktDc.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.ktDc.dto.KtDcDto;
import com.ktmmobile.mcp.ktDc.mapper.KtDcMapper;

@RestController
public class KtDcController {

    private static final Logger logger = LoggerFactory.getLogger(KtDcController.class);

    @Autowired
    KtDcMapper ktDcMapper;

    /**
     * 부가서비스명 조회
     * @param GiftPromotionBas
     * @return
     */
    @RequestMapping(value = "/ktDc/selectRateNmList", method = RequestMethod.POST)
    public List<KtDcDto> selectRateNmList(@RequestBody KtDcDto ktDcDto){


        List<KtDcDto> result = null;
        try {
            // Database 에서 조회함.
            result = ktDcMapper.selectRateNmList(ktDcDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return result;
    }


}