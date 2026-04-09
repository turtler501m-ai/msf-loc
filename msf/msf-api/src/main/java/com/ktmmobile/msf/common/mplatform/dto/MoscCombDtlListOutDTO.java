package com.ktmmobile.msf.common.mplatform.dto;

import java.text.ParseException;

import com.ktmmobile.msf.common.util.DateTimeUtil;

public class MoscCombDtlListOutDTO {
    private String svcContDivNm; //상품구분
    private String prodNm;	//상품명
    private String svcNo;	//서비스번호
    private String combEngtPerdMonsNum;	//	(결합)약정기간
    private String combEngtExpirDt;	//	(결합)만료예정일
    private String unSvcNo;

    public String getSvcContDivNm() {
        return svcContDivNm;
    }
    public void setSvcContDivNm(String svcContDivNm) {
        this.svcContDivNm = svcContDivNm;
    }
    public String getProdNm() {
        return prodNm;
    }
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
    public String getSvcNo() {
        return svcNo;
    }
    public void setSvcNo(String svcNo) {
        this.svcNo = svcNo;
    }
    public String getCombEngtPerdMonsNum() {
        return combEngtPerdMonsNum;
    }
    public void setCombEngtPerdMonsNum(String combEngtPerdMonsNum) {
        this.combEngtPerdMonsNum = combEngtPerdMonsNum;
    }
    public String getCombEngtExpirDt() {
        return combEngtExpirDt;
    }
    public void setCombEngtExpirDt(String combEngtExpirDt) {
        this.combEngtExpirDt = combEngtExpirDt;
    }
    public String getUnSvcNo() {
        return unSvcNo;
    }
    public void setUnSvcNo(String unSvcNo) {
        this.unSvcNo = unSvcNo;
    }


    public String getCombEngtExpirDtFormat() throws ParseException {
        if (combEngtExpirDt.length() > 7) {
            return DateTimeUtil.changeFormat(combEngtExpirDt,"yyyyMMdd","yyyy.MM.dd") ;
        } else {
            return combEngtExpirDt ;
        }
    }

    public String getCombEngtExpirDtFormat2() throws ParseException {
        if (combEngtExpirDt.length() > 7) {
            return DateTimeUtil.changeFormat(combEngtExpirDt,"yyyyMMdd","yyyy-MM-dd") ;
        } else {
            return combEngtExpirDt ;
        }
    }

    //확인필요
    public String getCombTypeNm() {
        if (svcContDivNm.indexOf("KIS") >-1) {
            return "kt M모바일";
        } else if (svcContDivNm.indexOf("Mobile") >-1){
            return "<b>KT 모바일</b>" ;
        }else if (svcContDivNm.indexOf("IPTV") >-1){
            return "<b>KT TV</b>" ;
        }  else {
            return "<b>KT 인터넷</b>" ;
        }
    }

    public String getCombTypeSmNm() {
        if (svcContDivNm.indexOf("KIS") >-1) {
            return "kt M모바일";
        } else {
            return "KT" ;
        }
    }

    public String getCombTypeSmNm2() {
        if (svcContDivNm.indexOf("KIS") >-1) {
            return "모바일";
        } else if (svcContDivNm.indexOf("Mobile") >-1){
            return "모바일" ;
        }else if (svcContDivNm.indexOf("IPTV") >-1){
            return "<b>TV</b>" ;
        } else {
            return "인터넷" ;
        }
    }



    /*
    <moscCombDtlListOutDTO>
      <combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum>
      <prodNm>데이터 ON 비디오 플러스</prodNm>
      <svcContDivNm>Mobile</svcContDivNm>
      <svcNo>01073654807</svcNo>
    </moscCombDtlListOutDTO>
    <moscCombDtlListOutDTO>
      <combEngtPerdMonsNum>약정없음</combEngtPerdMonsNum>
      <prodNm>5G 모두다 맘껏 110GB+(지니뮤직 FREE)</prodNm>
      <svcContDivNm>Mobile(KIS)</svcContDivNm>
      <svcNo>01027954807</svcNo>
    </moscCombDtlListOutDTO>
     와 같이 M모바일은 Mobile(KIS), KT는 Mobile로 확인됩니다.
     */

}
