package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * @Class Name : ResponseSuccessDto
 * @Description : 성공 응답처리시 View 단으로 보내는 Dto
 *
 * @author : ant
 * @Create Date : 2016. 1. 6.
 */
public class ResponseSuccessDto implements Serializable  {

    private static final long serialVersionUID = 1L;


    /** 요청한 시간 */
    private Date requestTime  ;

     /** 성공 메세지 */
    private String successMsg;

    /** redirect 할 URL */
    private String redirectUrl;

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }



}
