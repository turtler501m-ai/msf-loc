package com.ktmmobile.mcp.apppush.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.apppush.dto.AppPushDto;
import com.ktmmobile.mcp.apppush.mapper.AppPushMapper;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;

@RestController
public class AppPushController {

     private static final Logger logger = LoggerFactory.getLogger(AppPushController.class);

    @Autowired
    AppPushMapper appPushMapper;


    /**
     * 앱푸시 대상자 insert
     * @param appPushDto
     * @return
     */
    @RequestMapping(value = "/push/insertSendPushAll", method = RequestMethod.POST)
    public int insertSendPushAll(@RequestBody AppPushDto appPushDto) {
        int result = 0;

        try {
            result = appPushMapper.insertSendPushAll(appPushDto);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return result;

    }


}
