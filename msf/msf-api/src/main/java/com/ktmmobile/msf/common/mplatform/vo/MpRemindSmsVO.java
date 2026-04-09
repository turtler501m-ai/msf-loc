package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParse;

public class MpRemindSmsVO extends CommonXmlVO {

    private String smsRcvBlckYn;  //sms수신차단 Y/N
    private String resultCd;      //응답코드 00:성공, 99:실패
    private String resultMsg;     //응답메세지 성공:null, 실패: 응답메시지

    @Override
    public void parse()  {
        this.smsRcvBlckYn = XmlParse.getChildValue(this.body, "smsRcvBlckYn");
        this.resultCd = XmlParse.getChildValue(this.body, "resultCd");
        this.resultMsg = XmlParse.getChildValue(this.body, "resultMsg");
    }

    public String getSmsRcvBlckYn() {
        return smsRcvBlckYn;
    }

    public void setSmsRcvBlckYn(String smsRcvBlckYn) {
        this.smsRcvBlckYn = smsRcvBlckYn;
    }

    public String getResultCd() {
        return resultCd;
    }

    public void setResultCd(String resultCd) {
        this.resultCd = resultCd;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

}
