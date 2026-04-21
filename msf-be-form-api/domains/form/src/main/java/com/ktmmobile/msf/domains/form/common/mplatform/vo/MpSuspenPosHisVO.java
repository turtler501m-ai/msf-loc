package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpBkInfoDto;
import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MpSuspenPosHisVO extends CommonXmlVO{

    private String subStatus;					//현재상태 A: 개통, S: 일시정지
    private String reckonFromDate;			//시작일자
    private String susCnt;						//고객정지횟수 - 일시정지 누적 횟수
    private String susDays;					//일시정지일수 - 일시정지 누적 일수
    private String remainSusCnt;			//잔여정지일수 - 일시정지 잔여 횟수
    private String solSusDays;				//이용정지기간일수 - 이용정지 누적 일수
    private String remainOgDays;			//착신정지가능일수 -발신정지 가능 일수
    private String m2mRemainSusCnt;		//m2m정지 잔여횟수 - 정지사유코드가 CS01(=M2M고객요청에의한 정지)	일반정지 잔여횟수와 별도로 Counting된 정지 잔여 수
    private String expectSpDate;		    //일시정지만료예정일

    private List<MpBkInfoDto> itemList;
    @Override
    public void parse() throws ParseException {
        this.subStatus					= XmlParse.getChildValue(this.body, "subStatus");
        this.reckonFromDate		= XmlParse.getChildValue(this.body, "reckonFromDate");
        this.susCnt						= XmlParse.getChildValue(this.body, "susCnt");
        this.susDays					= XmlParse.getChildValue(this.body, "susDays");
        this.remainSusCnt			= XmlParse.getChildValue(this.body, "remainSusCnt");
        this.solSusDays				= XmlParse.getChildValue(this.body, "solSusDays");
        this.remainOgDays			= XmlParse.getChildValue(this.body, "remainOgDays");
        this.m2mRemainSusCnt	= XmlParse.getChildValue(this.body, "m2mRemainSusCnt");
        this.expectSpDate	= XmlParse.getChildValue(this.body, "expectSpDate");

        List<Element> bkInfoList = XmlParse.getChildElementList(this.body, "bkInfoDto");
        itemList = new ArrayList<MpBkInfoDto>();
        for(Element item : bkInfoList){
            MpBkInfoDto vo = new MpBkInfoDto();
            vo.setRemainSusCnt(XmlParse.getChildValue(item, "remainSusCnt"));
            vo.setColSusDays(XmlParse.getChildValue(item, "colSusDays"));
            vo.setRemainOgDays(XmlParse.getChildValue(item, "remainOgDays"));
            vo.setCsaActivityRsnDesc(XmlParse.getChildValue(item, "csaActivityRsnDesc"));
            itemList.add(vo);
        }
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getReckonFromDate() {
        return reckonFromDate;
    }

    public void setReckonFromDate(String reckonFromDate) {
        this.reckonFromDate = reckonFromDate;
    }

    public String getSusCnt() {
        return susCnt;
    }

    public void setSusCnt(String susCnt) {
        this.susCnt = susCnt;
    }

    public String getSusDays() {
        return susDays;
    }

    public void setSusDays(String susDays) {
        this.susDays = susDays;
    }

    public String getRemainSusCnt() {
        return remainSusCnt;
    }

    public void setRemainSusCnt(String remainSusCnt) {
        this.remainSusCnt = remainSusCnt;
    }

    public String getSolSusDays() {
        return solSusDays;
    }

    public void setSolSusDays(String solSusDays) {
        this.solSusDays = solSusDays;
    }

    public String getRemainOgDays() {
        return remainOgDays;
    }

    public void setRemainOgDays(String remainOgDays) {
        this.remainOgDays = remainOgDays;
    }

    public String getM2mRemainSusCnt() {
        return m2mRemainSusCnt;
    }

    public void setM2mRemainSusCnt(String m2mRemainSusCnt) {
        this.m2mRemainSusCnt = m2mRemainSusCnt;
    }

    public List<MpBkInfoDto> getItemList() {
        return itemList;
    }
    public void setItemList(List<MpBkInfoDto> itemList) {
        this.itemList = itemList;
    }

    public String getExpectSpDate() {
        return expectSpDate;
    }

    public void setExpectSpDate(String expectSpDate) {
        this.expectSpDate = expectSpDate;
    }



}
