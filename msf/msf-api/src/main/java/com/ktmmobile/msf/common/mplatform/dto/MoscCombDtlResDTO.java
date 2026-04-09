package com.ktmmobile.msf.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.common.util.StringUtil;
import com.ktmmobile.msf.common.util.XmlParse;

/**
 * 결합조회 x87
**/
public class MoscCombDtlResDTO  extends com.ktmmobile.msf.common.mplatform.vo.CommonXmlNoSelfServiceException{

    private String combTypeNm;		//결합유형
    private String combProdNm;		//결합상품
    private String engtPerdMonsNum;		//약정기간
    private String combDcTypeNm;	   //할인정보(결합)
    private String combDcTypeDtl;		//할인정보(총액할인)

    private List<MoscCombDtlListOutDTO> combList;



    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        List<Element> itemList = XmlParse.getChildElementList(this.body, "moscCombDtlListOutDTO");

        this.combTypeNm = XmlParse.getChildValue(this.body, "combTypeNm");
        this.combProdNm = XmlParse.getChildValue(this.body, "combProdNm");
        this.engtPerdMonsNum = XmlParse.getChildValue(this.body, "engtPerdMonsNum");
        this.combDcTypeNm = XmlParse.getChildValue(this.body, "combDcTypeNm");
        this.combDcTypeDtl = XmlParse.getChildValue(this.body, "combDcTypeDtl");


        combList = new ArrayList<MoscCombDtlListOutDTO>();

         for(Element item : itemList){

             MoscCombDtlListOutDTO vo = new MoscCombDtlListOutDTO();
             String svcContDivNm = StringUtil.NVL(XmlParse.getChildValue(item, "svcContDivNm"),"");
             vo.setSvcContDivNm(svcContDivNm);
             vo.setProdNm(XmlParse.getChildValue(item, "prodNm"));
             vo.setSvcNo(XmlParse.getChildValue(item, "svcNo"));
             vo.setCombEngtPerdMonsNum(XmlParse.getChildValue(item, "combEngtPerdMonsNum"));
             vo.setCombEngtExpirDt(XmlParse.getChildValue(item, "combEngtExpirDt"));
             combList.add(vo);
         }
    }

    public String getCombTypeNm() {
        return combTypeNm;
    }

    public void setCombTypeNm(String combTypeNm) {
        this.combTypeNm = combTypeNm;
    }

    public String getCombProdNm() {
        return combProdNm;
    }

    public void setCombProdNm(String combProdNm) {
        this.combProdNm = combProdNm;
    }

    public String getEngtPerdMonsNum() {
        return engtPerdMonsNum;
    }

    public void setEngtPerdMonsNum(String engtPerdMonsNum) {
        this.engtPerdMonsNum = engtPerdMonsNum;
    }

    public String getCombDcTypeNm() {
        return combDcTypeNm;
    }

    public void setCombDcTypeNm(String combDcTypeNm) {
        this.combDcTypeNm = combDcTypeNm;
    }

    public String getCombDcTypeDtl() {
        return combDcTypeDtl;
    }

    public void setCombDcTypeDtl(String combDcTypeDtl) {
        this.combDcTypeDtl = combDcTypeDtl;
    }

    public List<MoscCombDtlListOutDTO> getCombList() {
        return combList;
    }

    public void setCombList(List<MoscCombDtlListOutDTO> combList) {
        this.combList = combList;
    }





}
