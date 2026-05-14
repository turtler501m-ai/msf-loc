package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import lombok.Data;
import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

@Data
public class OsstMcnChgPrecheckResponse extends CommonXmlVO {

    private String osstOrdNo; // OSST 오더 번호
    private String rslt; // 처리 결과
    private String rsltMsg; // 처리 결과 메시지

    @Override public void parse() throws UnsupportedEncodingException, ParseException {
        Element item = this.body;
        this.osstOrdNo = XmlParse.getChildValue(item, "osstOrdNo");
        this.rsltMsg = XmlParse.getChildValue(item, "rsltMsg");
        this.rsltMsg = XmlParse.getChildValue(item, "rsltMsg");
    }
}
