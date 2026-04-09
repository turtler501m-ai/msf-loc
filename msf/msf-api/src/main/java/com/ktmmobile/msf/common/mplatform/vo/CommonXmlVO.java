package com.ktmmobile.msf.common.mplatform.vo;

import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import java.io.IOException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jdom.Element;
import org.jdom.JDOMException;
import com.ktmmobile.msf.common.exception.McpCommonException;
import com.ktmmobile.msf.common.exception.SelfServiceException;
import com.ktmmobile.msf.common.util.StringUtil;
import com.ktmmobile.msf.common.util.XmlParse;

public abstract class CommonXmlVO implements ParseVO{
    protected final static String HEADER = "commHeader";
    protected final static String BODY = "outDto";
    protected final static String RESULTCODE = "responseType";
    protected final static String RESULTCODE_REAL = "responseCode";

    protected final static String RETURN = "return";
    public final static String RESULTCODE_SUCCESS = "N";


    protected String resultCode;
    protected String responseXml;
    protected String subBodyStr = "body";
    protected String enckey;
    protected String svcName        ;//안내 메시지
    protected String svcMsg ;
    protected boolean isSuccess = false;
    protected Element body;
    protected Element root;
    protected String globalNo ;

    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getResponseXml() {
        return responseXml;
    }
    public void setResponseXml(String responseXml) {
        this.responseXml = responseXml;
    }
    public boolean isSuccess() {
        return isSuccess;
    }
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public Element getBody() {
        return body;
    }
    public void setBody(Element body) {
        this.body = body;
    }
    public String getEnckey() {
        return enckey;
    }
    public void setEnckey(String enckey) {
        this.enckey = enckey;
    }
    public String getSubBodyStr() {
        return subBodyStr;
    }
    public void setSubBodyStr(String subBodyStr) {
        this.subBodyStr = subBodyStr;
    }
    public String getSvcName() {
        return svcName;
    }
    public void setSvcName(String svcName) {
        this.svcName = svcName;
    }
    public String getSvcMsg() {
        return svcMsg;
    }
    public void setSvcMsg(String svcMsg) {
        this.svcMsg = svcMsg;
    }
    public String getGlobalNo() {
        return globalNo;
    }
    public void setGlobalNo(String globalNo) {
        this.globalNo = globalNo;
    }

    public void toResponseParse() throws JDOMException, IOException {
        // TEST
        Element root = XmlParse.getRootElement("<?xml version=\"1.0\" encoding=\"euc-kr\"?>"+this.responseXml);
        Element rtn = XmlParse.getReturnElement(root);

        Element commHeader = XmlParse.getChildElement(rtn, HEADER);
        this.resultCode= XmlParse.getChildValue(commHeader, RESULTCODE);
        this.globalNo= XmlParse.getChildValue(commHeader, "globalNo");
        this.isSuccess = StringUtil.equals(resultCode, CommonXmlVO.RESULTCODE_SUCCESS);

        if(this.isSuccess) {
            this.root = rtn;
            this.body = XmlParse.getChildElement(rtn, BODY);

            try {
                this.parse();
                this.svcMsg = "성공";
            } catch (Exception e) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }
        } else {
            this.svcMsg = XmlParse.getChildValue(commHeader, "responseBasic");
            this.resultCode= XmlParse.getChildValue(commHeader, RESULTCODE_REAL);
            throw new SelfServiceException(this.resultCode,XmlParse.getChildValue(commHeader, "responseBasic") , this.globalNo);
            //throw new SelfServiceException(this.resultCode+";;;"+XmlParse.getChildValue(commHeader, "responseBasic"));
        }

    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
