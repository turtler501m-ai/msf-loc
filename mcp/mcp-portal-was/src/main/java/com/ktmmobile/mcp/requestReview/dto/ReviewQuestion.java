package com.ktmmobile.mcp.requestReview.dto;

import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

import java.util.Date;
import java.util.List;

import static com.ktmmobile.mcp.common.constants.Constants.REQ_BUY_TYPE_PHONE;
import static com.ktmmobile.mcp.common.constants.Constants.REQ_BUY_TYPE_USIM;

public class ReviewQuestion {

    private static final long serialVersionUID = 4222718022084560257L;


    private String questionDesc;
    private String questionMm;
    private String answerDesc;

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getQuestionMm() {
        return questionMm;
    }

    public void setQuestionMm(String questionMm) {
        this.questionMm = questionMm;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }
}
