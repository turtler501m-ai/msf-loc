package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

@EqualsAndHashCode(callSuper = true)
@Data
public class MpSuspenCnlPosInfoInVO extends CommonXmlVO {

    private Map<String, String> map;
    private String rsltInd; //일시정지해제 가능여부(Y: 가능)
    private String rsltMsg; //일시정지메시지
    private String subStatusRsnCode; //전화번호 상태사유코드
    private String sndarvStatCd; //발/착신구분코드
    private String rsnDesc; //사유코드설명
    private String subStatusDate; //일시정지일자
    private String ctnStatus; //전화번호상태

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.rsltInd = XmlParse.getChildValue(this.body, "rsltInd");                        //일시정지 가능여부
        this.rsltMsg = XmlParse.getChildValue(this.body, "rsltMsg");                        //일시정지메시지
        this.subStatusRsnCode = XmlParse.getChildValue(this.body, "subStatusRsnCode");      //전화번호 상태사유코드
        this.sndarvStatCd = XmlParse.getChildValue(this.body, "sndarvStatCd");              //발/착신구분코드
        this.rsnDesc = XmlParse.getChildValue(this.body, "rsnDesc");                        //사유코드설명
        this.subStatusDate = XmlParse.getChildValue(this.body, "subStatusDate");            //일시정지일자
        this.ctnStatus = XmlParse.getChildValue(this.body, "ctnStatus");                    //전화번호상태
    }

}
