package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * M플랫폼 응답 XML 기반 VO 추상 클래스. ASIS CommonXmlVO 와 동일 구조.
 * XmlParseUtil(org.w3c.dom) 사용.
 */
public abstract class CommonXmlVO {

    protected static final String RESULTCODE_SUCCESS = "N";

    protected String responseXml;
    protected String resultCode;
    protected String globalNo;
    protected String svcMsg;
    protected boolean isSuccess = false;
    protected Element body;

    public void setResponseXml(String responseXml) {
        this.responseXml = responseXml;
    }

    public String getResponseXml() {
        return responseXml;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getGlobalNo() {
        return globalNo;
    }

    public String getSvcMsg() {
        return svcMsg;
    }

    /**
     * ASIS toResponseParse() 와 동일.
     * responseXml 파싱 → commHeader(resultCode, globalNo) 확인 → isSuccess 판정 → parse() 호출.
     */
    public void toResponseParse() {
        try {
            Document doc = XmlParseUtil.getDocument(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + this.responseXml);
            Element rtn = XmlParseUtil.getReturnElement(doc);

            Element commHeader = XmlParseUtil.getChildElement(rtn, "commHeader");
            this.resultCode = XmlParseUtil.getChildValue(commHeader, "responseType");
            this.globalNo = XmlParseUtil.getChildValue(commHeader, "globalNo");
            this.isSuccess = RESULTCODE_SUCCESS.equals(this.resultCode);

            if (this.isSuccess) {
                this.body = XmlParseUtil.getChildElement(rtn, "outDto");
                try {
                    this.parse();
                    this.svcMsg = "성공";
                } catch (Exception e) {
                    this.svcMsg = "parse 오류: " + e.getMessage();
                }
            } else {
                this.svcMsg = XmlParseUtil.getChildValue(commHeader, "responseBasic");
                this.resultCode = XmlParseUtil.getChildValue(commHeader, "responseCode");
            }
        } catch (Exception e) {
            this.isSuccess = false;
            this.svcMsg = "XML 파싱 오류: " + e.getMessage();
        }
    }

    /** 응답 body 파싱. 서브클래스에서 구현. */
    protected abstract void parse() throws Exception;
}
