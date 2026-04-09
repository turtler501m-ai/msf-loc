package com.ktmmobile.msf.common.mplatform.vo;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.common.util.StringUtil;
import com.ktmmobile.msf.common.util.XmlParse;

public class MpTelTotalUseTimeMobileVO extends CommonXmlVO{

    private List<ItemTelVO> itemTelList;
    private List<ItemVoiceVO> itemVoiceList;
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

    private String pBunGunNm = "MB";

    public String getPBunGunNm() {
		return pBunGunNm;
	}
	public void setPBunGunNm(String pBunGunNm) {
		this.pBunGunNm = pBunGunNm;
	}


    @Override
    public void parse() {
        Element userInfo = XmlParse.getChildElement(this.body, "totUseTimeCntDto");
        this.monthPay = XmlParse.getChildValue(userInfo, "strSvcNameSms");

        List<Element> itemTel = XmlParse.getChildElementList(this.body, "totalUseTimeDto");
        itemTelList = new ArrayList<ItemTelVO>();
        for(Element item : itemTel){
            ItemTelVO vo = new ItemTelVO();
            String bunGun = XmlParse.getChildValue(item, "strBunGun");
            vo.setStrSvcName(XmlParse.getChildValue(item, "strSvcName"));
            vo.setStrCtnSecs(getDataCal(XmlParse.getChildValue(item, "strCtnSecs"), bunGun));
            vo.setStrSecsToRate(getDataCal(XmlParse.getChildValue(item, "strSecsToRate"), bunGun));
            vo.setStrSecsToAmt(getDataCal(XmlParse.getChildValue(item, "strSecsToAmt"), bunGun));
            vo.setStrFreeMinCur(getDataCal(XmlParse.getChildValue(item, "strFreeMinCur"), bunGun));
            vo.setStrFreeMinRoll(getDataCal(XmlParse.getChildValue(item, "strFreeMinRoll"), bunGun));
            vo.setStrFreeMinTotal(getDataCal(XmlParse.getChildValue(item, "strFreeMinTotal"), bunGun));
            vo.setStrFreeMinTotalInt(StringUtil.toInteger(XmlParse.getChildValue(item, "strFreeMinTotal")));
            vo.setStrFreeMinUse(getDataCal(XmlParse.getChildValue(item, "strFreeMinUse"), bunGun));
            vo.setStrFreeMinUseInt(StringUtil.toInteger(XmlParse.getChildValue(item, "strFreeMinUse")));
            vo.setStrCtnSecsInt(StringUtil.toInteger(XmlParse.getChildValue(item, "strCtnSecs")));
            vo.setStrSecsToRateInt(StringUtil.toInteger(XmlParse.getChildValue(item, "strSecsToRate")));
            vo.setStrFreeMinRemain(getDataCal(XmlParse.getChildValue(item, "strFreeMinRemain"), bunGun));
            vo.setBunGunNm(getBunGunNm(XmlParse.getChildValue(item, "strBunGun")));
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

    public List<ItemTelVO> getItemTelList() {
        return itemTelList;
    }
    public void setItemTelList(List<ItemTelVO> itemTelList) {
        this.itemTelList = itemTelList;
    }
    public List<ItemVoiceVO> getItemVoiceList() {
        return itemVoiceList;
    }
    public void setItemVoiceList(List<ItemVoiceVO> itemVoiceList) {
        this.itemVoiceList = itemVoiceList;
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

    public static class ItemTelVO {
        private String strSvcName;//서비스명
        private String strCtnSecs;//총 통화시간
        private String strSecsToRate;//과금대상 통화시간
        private String strSecsToAmt;//과금대상 금액
        private String strFreeMinCur;//당월 무료통화
        private String strFreeMinRoll;//이월 무료통화
        private String strFreeMinTotal;//총 무료통화
        private String strFreeMinUse;//사용한 무료통화
        private String strFreeMinRemain;//남은 무료통화
        private int strCtnSecsInt=0;
        private int strSecsToRateInt=0;
        private int strFreeMinUseInt = 0;
        private int strFreeMinTotalInt = 0;
        private String bunGunNm ;

        public String getBunGunNm() {
			return bunGunNm;
		}
		public void setBunGunNm(String bunGunNm) {
			this.bunGunNm = bunGunNm;
		}
		public int getStrCtnSecsInt() {
			return strCtnSecsInt;
		}
		public void setStrCtnSecsInt(int strCtnSecsInt) {
			this.strCtnSecsInt = strCtnSecsInt;
		}
		public int getStrSecsToRateInt() {
			return strSecsToRateInt;
		}
		public void setStrSecsToRateInt(int strSecsToRateInt) {
			this.strSecsToRateInt = strSecsToRateInt;
		}
		public String getStrSvcName() {
            return strSvcName;
        }
        public void setStrSvcName(String strSvcName) {
            this.strSvcName = strSvcName;
        }
        public String getStrCtnSecs() {
            return strCtnSecs;
        }
        public void setStrCtnSecs(String strCtnSecs) {
            this.strCtnSecs = strCtnSecs;
        }
        public String getStrSecsToRate() {
            return strSecsToRate;
        }
        public void setStrSecsToRate(String strSecsToRate) {
            this.strSecsToRate = strSecsToRate;
        }
        public String getStrSecsToAmt() {
            return strSecsToAmt;
        }
        public void setStrSecsToAmt(String strSecsToAmt) {
            this.strSecsToAmt = strSecsToAmt;
        }
        public String getStrFreeMinCur() {
            return strFreeMinCur;
        }
        public void setStrFreeMinCur(String strFreeMinCur) {
            this.strFreeMinCur = strFreeMinCur;
        }
        public String getStrFreeMinRoll() {
            return strFreeMinRoll;
        }
        public void setStrFreeMinRoll(String strFreeMinRoll) {
            this.strFreeMinRoll = strFreeMinRoll;
        }
        public String getStrFreeMinTotal() {
            return strFreeMinTotal;
        }
        public void setStrFreeMinTotal(String strFreeMinTotal) {
            this.strFreeMinTotal = strFreeMinTotal;
        }
        public String getStrFreeMinUse() {
            return strFreeMinUse;
        }
        public void setStrFreeMinUse(String strFreeMinUse) {
            this.strFreeMinUse = strFreeMinUse;
        }
        public String getStrFreeMinRemain() {
            return strFreeMinRemain;
        }
        public void setStrFreeMinRemain(String strFreeMinRemain) {
            this.strFreeMinRemain = strFreeMinRemain;
        }
        public int getStrFreeMinUseInt() {
            return strFreeMinUseInt;
        }
        public void setStrFreeMinUseInt(int strFreeMinUseInt) {
            this.strFreeMinUseInt = strFreeMinUseInt;
        }
        public int getStrFreeMinTotalInt() {
            return strFreeMinTotalInt;
        }
        public void setStrFreeMinTotalInt(int strFreeMinTotalInt) {
            this.strFreeMinTotalInt = strFreeMinTotalInt;
        }

    }

    public class ItemVoiceVO{
        private String strSvcName;//서비스명
        private String strFreeMinCur;//당월 무료통화
        private String strFreeMinRoll;//이월 무료통화
        private String strFreeMinTotal;//총 무료통화
        private String strFreeMinUse;//사용한 무료통화
        private String strFreeMinRemain;//남은 무료통화
        private String strPeriod2;//평일휴일구분
        private String strCtnSecs;//당월 무료통화
        private String strFreeMinRoll2;//이월 무료통화
        private String strFreeMinTotal2;//총 무료통화
        private String strFreeMinUse2;//사용한 무료통화
        private String strFreeMinRemain2;//남은 무료통화
        public String getStrSvcName() {
            return strSvcName;
        }
        public void setStrSvcName(String strSvcName) {
            this.strSvcName = strSvcName;
        }
        public String getStrFreeMinCur() {
            return strFreeMinCur;
        }
        public void setStrFreeMinCur(String strFreeMinCur) {
            this.strFreeMinCur = strFreeMinCur;
        }
        public String getStrFreeMinRoll() {
            return strFreeMinRoll;
        }
        public void setStrFreeMinRoll(String strFreeMinRoll) {
            this.strFreeMinRoll = strFreeMinRoll;
        }
        public String getStrFreeMinTotal() {
            return strFreeMinTotal;
        }
        public void setStrFreeMinTotal(String strFreeMinTotal) {
            this.strFreeMinTotal = strFreeMinTotal;
        }
        public String getStrFreeMinUse() {
            return strFreeMinUse;
        }
        public void setStrFreeMinUse(String strFreeMinUse) {
            this.strFreeMinUse = strFreeMinUse;
        }
        public String getStrFreeMinRemain() {
            return strFreeMinRemain;
        }
        public void setStrFreeMinRemain(String strFreeMinRemain) {
            this.strFreeMinRemain = strFreeMinRemain;
        }
        public String getStrPeriod2() {
            return strPeriod2;
        }
        public void setStrPeriod2(String strPeriod2) {
            this.strPeriod2 = strPeriod2;
        }
        public String getStrCtnSecs() {
            return strCtnSecs;
        }
        public void setStrCtnSecs(String strCtnSecs) {
            this.strCtnSecs = strCtnSecs;
        }
        public String getStrFreeMinRoll2() {
            return strFreeMinRoll2;
        }
        public void setStrFreeMinRoll2(String strFreeMinRoll2) {
            this.strFreeMinRoll2 = strFreeMinRoll2;
        }
        public String getStrFreeMinTotal2() {
            return strFreeMinTotal2;
        }
        public void setStrFreeMinTotal2(String strFreeMinTotal2) {
            this.strFreeMinTotal2 = strFreeMinTotal2;
        }
        public String getStrFreeMinUse2() {
            return strFreeMinUse2;
        }
        public void setStrFreeMinUse2(String strFreeMinUse2) {
            this.strFreeMinUse2 = strFreeMinUse2;
        }
        public String getStrFreeMinRemain2() {
            return strFreeMinRemain2;
        }
        public void setStrFreeMinRemain2(String strFreeMinRemain2) {
            this.strFreeMinRemain2 = strFreeMinRemain2;
        }
    }

    public String getDataCal(String val, String type) {
        String temp=val;
        if (val == null || val.isEmpty() || val.equals("0") || type == null || type.isEmpty()){
            return temp;
        }else if(val.equals("999999999")){
        	temp = "기본제공";
        	return temp;
        }else if(type.equals("P")){
        	//MB단위로 수정, 소수점 첫번째자리 까지 반올림 으로수정
        	if((Double)(Integer.parseInt(val) * 0.5)/1024 > 1000) {
                temp = String.valueOf( Math.round((Double)(Integer.parseInt(val) * 0.5)/1024/1024*10d) / 10d);
                this.pBunGunNm = "GB";
        	} else {
                temp = String.valueOf( Math.round((Double)(Integer.parseInt(val) * 0.5)/1024*10d) / 10d);
                this.pBunGunNm = "MB";
        	}
        }else if(type.equals("V")){
            //초로 넘어오는 값을 분으로 계산 추가 type.equals("V") 주석이였음 20151103
            if(!val.equals("0")){
            	temp = ""+(Integer.parseInt(val) / 60);
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
//        else if(val.equals("P")) bunGunNm = "GB";
        else if(val.equals("P")) bunGunNm = this.pBunGunNm;
        else if(val.equals("D")) bunGunNm = "건";
//        else if(val.equals("V")) bunGunNm = "분";
        else if(val.equals("V")) bunGunNm = "분";
        else if(val.equals("T")) bunGunNm = "%";
        else if(val.equals("U")) bunGunNm = "초";
        else if(val.equals("R")) bunGunNm = "알";

        return bunGunNm;
    }
}
