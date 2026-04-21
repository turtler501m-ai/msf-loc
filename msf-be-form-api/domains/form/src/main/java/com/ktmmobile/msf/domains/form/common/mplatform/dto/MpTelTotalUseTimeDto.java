package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpTelVO;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.common.util.XmlParse;

public class MpTelTotalUseTimeDto extends CommonXmlVO {

    private List<MpTelVO> itemTelList;
    private String monthPay;//사용중인 요금상품명

    private String total;//합계
    private String iFreeMinCurSum;//당월 무툐 통화 합계
    private String iFreeMinRollSum;//이월 무료 통화 합계
    private String iFreeMinTotalSum;//총 무료 통화 합계
    private String iFreeMinUseSum;//사용한 무료 통화 합계
    private String iFreeMinRemainSum;//남은 무료 통화 합계

    private String strSvcNameSms;//서비스명
    private String strFreeSmsCur;//당월 무료 문자
    private String strFreeSmsRoll;//이월 무료 문자
    private String strFreeSmsTotal;//총 무료 문자
    private String strFreeSmsUse;//사용한 무료 문자
    private String strFreeSmsRemain;//남은 무료 문자
    private String totalTot;//합계

    private String strFreeSmsCurTot;//당월 무료 문자 합계
    private String strFreeSmsRollTot;//이월 무료 문자 합계
    private String strFreeSmsTotalTot;//총 무료 문자 합계
    private String strFreeSmsUseTot;//사용한 무료 문자 합계
    private String strFreeSmsRemainTot;//남은 무료 문자 합계

    private List<MpTelVO> itemTelVOListData;
    private List<MpTelVO> itemTelVOListEtc;
    private List<MpTelVO> itemTelVOListVoice;
    private List<MpTelVO> itemTelVOListSms;
    private List<MpTelVO> itemTelVOListRoaming;
    private boolean useTimeSvcLimit=true;

    @Override
    public void parse() {
        Element userInfo = XmlParse.getChildElement(this.body, "totUseTimeCntDto");
        this.monthPay = XmlParse.getChildValue(userInfo, "strSvcNameSms");

        List<Element> itemTel = XmlParse.getChildElementList(this.body, "totalUseTimeDto");
        itemTelList = new ArrayList<MpTelVO>();
        for(Element item : itemTel){
            MpTelVO vo = new MpTelVO();
            String bunGun = XmlParse.getChildValue(item, "strBunGun");
            vo.setBunGunNm(getBunGunNm(XmlParse.getChildValue(item, "strBunGun")));
            vo.setStrSvcName(XmlParse.getChildValue(item, "strSvcName"));
            vo.setStrCtnSecs(getDataCal(XmlParse.getChildValue(item, "strCtnSecs"), bunGun));
            vo.setStrSecsToRate(getDataCal(XmlParse.getChildValue(item, "strSecsToRate"), bunGun));
            vo.setStrSecsToAmt(getDataCal(XmlParse.getChildValue(item, "strSecsToAmt"), bunGun));
            vo.setStrFreeMinCur(getDataCal(XmlParse.getChildValue(item, "strFreeMinCur"), bunGun));
            vo.setStrFreeMinRoll(getDataCal(XmlParse.getChildValue(item, "strFreeMinRoll"), bunGun));
            vo.setStrFreeMinTotal(getDataCal(XmlParse.getChildValue(item, "strFreeMinTotal"), bunGun));
            vo.setStrFreeMinTotalInt(StringUtil.toInteger(StringUtil.NVL(XmlParse.getChildValue(item, "strFreeMinTotal"),"0")));
            vo.setStrFreeMinUse(getDataCal(XmlParse.getChildValue(item, "strFreeMinUse"), bunGun));
            vo.setStrFreeMinUseInt(StringUtil.toInteger(StringUtil.NVL(XmlParse.getChildValue(item, "strFreeMinUse"),"0")));
            vo.setStrCtnSecsInt(StringUtil.toInteger(StringUtil.NVL(XmlParse.getChildValue(item, "strCtnSecs"),"0")));
            vo.setStrSecsToRateInt(StringUtil.toInteger(StringUtil.NVL(XmlParse.getChildValue(item, "strSecsToRate"),"0")));
            vo.setStrFreeMinRemain(getDataCal(XmlParse.getChildValue(item, "strFreeMinRemain"), bunGun));
            vo.setStrFreeMinRemainInt(StringUtil.toInteger(StringUtil.NVL(XmlParse.getChildValue(item, "strFreeMinRemain"),"0")));
            itemTelList.add(vo);
        }

        Element voiceCallDetailTot = XmlParse.getChildElement(this.body, "voiceCallDetailTotDto");
        this.total = XmlParse.getChildValue(voiceCallDetailTot, "total");
        this.iFreeMinCurSum = XmlParse.getChildValue(voiceCallDetailTot, "iFreeMinCurSum");
        this.iFreeMinRollSum = XmlParse.getChildValue(voiceCallDetailTot, "iFreeMinRollSum");
        this.iFreeMinTotalSum = XmlParse.getChildValue(voiceCallDetailTot, "iFreeMinTotalSum");
        this.iFreeMinUseSum = XmlParse.getChildValue(voiceCallDetailTot, "iFreeMinUseSum");
        this.iFreeMinRemainSum = XmlParse.getChildValue(voiceCallDetailTot, "iFreeMinRemainSum");

        Element msgInfo = XmlParse.getChildElement(this.body, "totUseTimeCntDto");
        this.strSvcNameSms = XmlParse.getChildValue(msgInfo, "strSvcNameSms");
        this.strFreeSmsCur = XmlParse.getChildValue(msgInfo, "strFreeSmsCur");
        this.strFreeSmsRoll = XmlParse.getChildValue(msgInfo, "strFreeSmsRoll");
        this.strFreeSmsTotal = XmlParse.getChildValue(msgInfo, "strFreeSmsTotal");
        this.strFreeSmsUse = XmlParse.getChildValue(msgInfo, "strFreeSmsUse");
        this.strFreeSmsRemain = XmlParse.getChildValue(msgInfo, "strFreeSmsRemain");

        Element msgInfoTot = XmlParse.getChildElement(this.body, "totUseTimeCntTotDto");
        this.totalTot = XmlParse.getChildValue(msgInfoTot, "total");
        this.strFreeSmsCurTot = XmlParse.getChildValue(msgInfoTot, "strFreeSmsCur");
        this.strFreeSmsRollTot = XmlParse.getChildValue(msgInfoTot, "strFreeSmsRoll");
        this.strFreeSmsTotalTot = XmlParse.getChildValue(msgInfoTot, "strFreeSmsTotal");
        this.strFreeSmsUseTot = XmlParse.getChildValue(msgInfoTot, "strFreeSmsUse");
        this.strFreeSmsRemainTot = XmlParse.getChildValue(msgInfoTot, "strFreeSmsRemain");
    }

    public List<MpTelVO> getItemTelList() {
        return itemTelList;
    }
    public void setItemTelList(List<MpTelVO> itemTelList) {
        this.itemTelList = itemTelList;
    }

    public String getMonthPay() {
        return monthPay;
    }
    public void setMonthPay(String monthPay) {
        this.monthPay = monthPay;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getiFreeMinCurSum() {
        return iFreeMinCurSum;
    }
    public void setiFreeMinCurSum(String iFreeMinCurSum) {
        this.iFreeMinCurSum = iFreeMinCurSum;
    }
    public String getiFreeMinRollSum() {
        return iFreeMinRollSum;
    }
    public void setiFreeMinRollSum(String iFreeMinRollSum) {
        this.iFreeMinRollSum = iFreeMinRollSum;
    }
    public String getiFreeMinTotalSum() {
        return iFreeMinTotalSum;
    }
    public void setiFreeMinTotalSum(String iFreeMinTotalSum) {
        this.iFreeMinTotalSum = iFreeMinTotalSum;
    }
    public String getiFreeMinUseSum() {
        return iFreeMinUseSum;
    }
    public void setiFreeMinUseSum(String iFreeMinUseSum) {
        this.iFreeMinUseSum = iFreeMinUseSum;
    }
    public String getiFreeMinRemainSum() {
        return iFreeMinRemainSum;
    }
    public void setiFreeMinRemainSum(String iFreeMinRemainSum) {
        this.iFreeMinRemainSum = iFreeMinRemainSum;
    }
    public String getStrSvcNameSms() {
        return strSvcNameSms;
    }
    public void setStrSvcNameSms(String strSvcNameSms) {
        this.strSvcNameSms = strSvcNameSms;
    }
    public String getStrFreeSmsCur() {
        return strFreeSmsCur;
    }
    public void setStrFreeSmsCur(String strFreeSmsCur) {
        this.strFreeSmsCur = strFreeSmsCur;
    }
    public String getStrFreeSmsRoll() {
        return strFreeSmsRoll;
    }
    public void setStrFreeSmsRoll(String strFreeSmsRoll) {
        this.strFreeSmsRoll = strFreeSmsRoll;
    }
    public String getStrFreeSmsTotal() {
        return strFreeSmsTotal;
    }
    public void setStrFreeSmsTotal(String strFreeSmsTotal) {
        this.strFreeSmsTotal = strFreeSmsTotal;
    }
    public String getStrFreeSmsUse() {
        return strFreeSmsUse;
    }
    public void setStrFreeSmsUse(String strFreeSmsUse) {
        this.strFreeSmsUse = strFreeSmsUse;
    }
    public String getStrFreeSmsRemain() {
        return strFreeSmsRemain;
    }
    public void setStrFreeSmsRemain(String strFreeSmsRemain) {
        this.strFreeSmsRemain = strFreeSmsRemain;
    }
    public String getTotalTot() {
        return totalTot;
    }
    public void setTotalTot(String totalTot) {
        this.totalTot = totalTot;
    }
    public String getStrFreeSmsCurTot() {
        return strFreeSmsCurTot;
    }
    public void setStrFreeSmsCurTot(String strFreeSmsCurTot) {
        this.strFreeSmsCurTot = strFreeSmsCurTot;
    }
    public String getStrFreeSmsRollTot() {
        return strFreeSmsRollTot;
    }
    public void setStrFreeSmsRollTot(String strFreeSmsRollTot) {
        this.strFreeSmsRollTot = strFreeSmsRollTot;
    }
    public String getStrFreeSmsTotalTot() {
        return strFreeSmsTotalTot;
    }
    public void setStrFreeSmsTotalTot(String strFreeSmsTotalTot) {
        this.strFreeSmsTotalTot = strFreeSmsTotalTot;
    }
    public String getStrFreeSmsUseTot() {
        return strFreeSmsUseTot;
    }
    public void setStrFreeSmsUseTot(String strFreeSmsUseTot) {
        this.strFreeSmsUseTot = strFreeSmsUseTot;
    }
    public String getStrFreeSmsRemainTot() {
        return strFreeSmsRemainTot;
    }
    public void setStrFreeSmsRemainTot(String strFreeSmsRemainTot) {
        this.strFreeSmsRemainTot = strFreeSmsRemainTot;
    }

    public boolean isUseTimeSvcLimit() {
        return useTimeSvcLimit;
    }

    public void setUseTimeSvcLimit(boolean useTimeSvcLimit) {
        this.useTimeSvcLimit = useTimeSvcLimit;
    }

    public List<MpTelVO> getItemTelVOListData() {
        return itemTelVOListData;
    }

    public void setItemTelVOListData(List<MpTelVO> itemTelVOListData) {
        this.itemTelVOListData = itemTelVOListData;
    }

    public List<MpTelVO> getItemTelVOListEtc() {
        return itemTelVOListEtc;
    }

    public void setItemTelVOListEtc(List<MpTelVO> itemTelVOListEtc) {
        this.itemTelVOListEtc = itemTelVOListEtc;
    }

    public List<MpTelVO> getItemTelVOListVoice() {
        return itemTelVOListVoice;
    }

    public void setItemTelVOListVoice(List<MpTelVO> itemTelVOListVoice) {
        this.itemTelVOListVoice = itemTelVOListVoice;
    }

    public List<MpTelVO> getItemTelVOListSms() {
        return itemTelVOListSms;
    }

    public void setItemTelVOListSms(List<MpTelVO> itemTelVOListSms) {
        this.itemTelVOListSms = itemTelVOListSms;
    }

    public List<MpTelVO> getItemTelVOListRoaming() {
        return itemTelVOListRoaming;
    }

    public void setItemTelVOListRoaming(List<MpTelVO> itemTelVOListRoaming) {
        this.itemTelVOListRoaming = itemTelVOListRoaming;
    }

    public String getDataCal(String val, String type) {
        String temp=val;
        if (val == null || val.isEmpty() || val.equals("0") || type == null || type.isEmpty()){
            return temp;
        }
        else if(val.equals("999999999")){
            temp = "999999999";
            return temp;
        }
        else if(type.equals("P")){
            //MB단위로 수정, 소수점 첫번째자리 까지 반올림 으로수정
            temp = String.valueOf( Math.round((Double)(Integer.parseInt(val) * 0.5)/1024*10d) / 10d);
        }else if(type.equals("V") || type.equals("U")){
            //초로 넘어오는 값을 분으로 계산 추가 type.equals("V") 주석이였음 20151103
            if(!val.equals("0")){
                if((Integer.parseInt(val) / 60)/60 == 0){
                    temp = ((Integer.parseInt(val) / 60))+"분"+((Integer.parseInt(val) % 60));
                }else{
                    temp = ((Integer.parseInt(val) / 60)/60+"시간"+(Integer.parseInt(val) / 60)%60+"분"+Integer.parseInt(val) % 60);
                }
            }else{
                temp = val;
            }
        }
        return temp;
    }

    public String getBunGunNm(String val) {
        String bunGunNm = null;

        if (val == null) return bunGunNm;
        else if(val.equals("W")) bunGunNm = "원";
        else if(val.equals("P")) bunGunNm = "MB";
        else if(val.equals("D")) bunGunNm = "건";
//        else if(val.equals("V")) bunGunNm = "분";
        else if(val.equals("V")) bunGunNm = "초";
        else if(val.equals("T")) bunGunNm = "%";
        else if(val.equals("U")) bunGunNm = "초";
        else if(val.equals("R")) bunGunNm = "알";

        return bunGunNm;
    }


}
