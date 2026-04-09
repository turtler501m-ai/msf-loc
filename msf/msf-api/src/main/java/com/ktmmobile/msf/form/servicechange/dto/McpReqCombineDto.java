package com.ktmmobile.msf.form.servicechange.dto;

import java.util.Date;
import com.ktmmobile.msf.common.util.StringMakerUtil;

public class McpReqCombineDto {

    private String reqSeq;
    private String combTypeCd;
    private String mCtn;
    private String mCustNm;
    private String mCustBirth;
    private String mSexCd;
    private String mSvcCntrNo;
    private String mRateCd;
    private String mRateNm;
    private String mRateAdsvcCd;
    private String mRateAdsvcNm;
    private String combSvcNo;
    private String combSvcCntrNo;
    private String combCustNm;
    private String combBirth;
    private String combSexCd;
    private String combSocCd;
    private String combSocNm;
    private String combRateAdsvcCd;
    private String combRateAdsvcNm;

    /*    결합대상 (01: 본인, 02: 가족, 03: 타인)     */
    private String combTgtTypeCd;
    private String rsltCd;
    private String rsltMemo;
    private String sysDt;
    private Date regDt;


    public String getmCustBirth() {
        return mCustBirth;
    }
    public void setmCustBirth(String mCustBirth) {
        this.mCustBirth = mCustBirth;
    }
    public String getmSexCd() {
        return mSexCd;
    }
    public void setmSexCd(String mSexCd) {
        this.mSexCd = mSexCd;
    }
    public String getCombBirth() {
        return combBirth;
    }
    public void setCombBirth(String combBirth) {
        this.combBirth = combBirth;
    }
    public String getReqSeq() {
        return reqSeq;
    }
    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }
    public String getCombTypeCd() {
        return combTypeCd;
    }

    public String getCombTypeNm() {
        //결합유형 (01: ktM+ktM, 02: ktM+kt무선, 03: ktM+kt유선)';
        if ("01".equals(combTypeCd)) {
            return "kt m결합" ;
        } else if ("02".equals(combTypeCd)) {
            return "<b>KT 모바일</b>" ;
        } else if ("03".equals(combTypeCd)) {
            return "<b>KT 인터넷</b>" ;
        } else {
            return combTypeCd;
        }

    }


    public void setCombTypeCd(String combTypeCd) {
        this.combTypeCd = combTypeCd;
    }
    public String getmCtn() {
        return mCtn;
    }


    public String getmCtnMaker() {
        if (mCtn == null) {
            return "";
        }

        return StringMakerUtil.getPhoneNum(mCtn);
    }







    public void setmCtn(String mCtn) {
        this.mCtn = mCtn;
    }
    public String getmCustNm() {
        return mCustNm;
    }
    public void setmCustNm(String mCustNm) {
        this.mCustNm = mCustNm;
    }
    public String getmSvcCntrNo() {
        return mSvcCntrNo;
    }
    public void setmSvcCntrNo(String mSvcCntrNo) {
        this.mSvcCntrNo = mSvcCntrNo;
    }
    public String getmRateCd() {
        return mRateCd;
    }
    public void setmRateCd(String mRateCd) {
        this.mRateCd = mRateCd;
    }
    public String getmRateNm() {
        return mRateNm;
    }
    public void setmRateNm(String mRateNm) {
        this.mRateNm = mRateNm;
    }
    public String getmRateAdsvcCd() {
        return mRateAdsvcCd;
    }
    public void setmRateAdsvcCd(String mRateAdsvcCd) {
        this.mRateAdsvcCd = mRateAdsvcCd;
    }
    public String getmRateAdsvcNm() {
        return mRateAdsvcNm;
    }
    public void setmRateAdsvcNm(String mRateAdsvcNm) {
        this.mRateAdsvcNm = mRateAdsvcNm;
    }
    public String getCombSvcNo() {
        return combSvcNo;
    }


    public String getCombSvcNoMaker() {
        if (combSvcNo == null) {
            return "";
        }

        if ("03".equals(combTypeCd)) {   //결합유형 (01: ktM+ktM, 02: ktM+kt무선, 03:* ktM+kt유선)
            return StringMakerUtil.getUserId(combSvcNo);
        } else {
            return StringMakerUtil.getPhoneNum(combSvcNo);
        }

    }


    public void setCombSvcNo(String combSvcNo) {
        this.combSvcNo = combSvcNo;
    }






    public String getCombSvcCntrNo() {
        return combSvcCntrNo;
    }
    public void setCombSvcCntrNo(String combSvcCntrNo) {
        this.combSvcCntrNo = combSvcCntrNo;
    }
    public String getCombCustNm() {
        return combCustNm;
    }
    public void setCombCustNm(String combCustNm) {
        this.combCustNm = combCustNm;
    }
    public String getCombSexCd() {
        return combSexCd;
    }
    public void setCombSexCd(String combSexCd) {
        this.combSexCd = combSexCd;
    }
    public String getCombSocCd() {
        return combSocCd;
    }
    public void setCombSocCd(String combSocCd) {
        this.combSocCd = combSocCd;
    }
    public String getCombSocNm() {
        return combSocNm;
    }
    public void setCombSocNm(String combSocNm) {
        this.combSocNm = combSocNm;
    }
    public String getCombRateAdsvcCd() {
        return combRateAdsvcCd;
    }
    public void setCombRateAdsvcCd(String combRateAdsvcCd) {
        this.combRateAdsvcCd = combRateAdsvcCd;
    }
    public String getCombRateAdsvcNm() {
        return combRateAdsvcNm;
    }
    public void setCombRateAdsvcNm(String combRateAdsvcNm) {
        this.combRateAdsvcNm = combRateAdsvcNm;
    }
    public String getCombTgtTypeCd() {
        return combTgtTypeCd;
    }
    public void setCombTgtTypeCd(String combTgtTypeCd) {
        this.combTgtTypeCd = combTgtTypeCd;
    }
    public String getRsltCd() {
        return rsltCd;
    }
    public void setRsltCd(String rsltCd) {
        this.rsltCd = rsltCd;
    }

    public String getRsltNm() {
        //  승인여부, R:승인대기, N:미제출, S:승인완료, B:승인반려, C:신청취소,, H:임의보류   승인대기와 승인반려 두 상황이 노출됨
        if ("R".equals(rsltCd)) {
            return "승인대기" ;
        } else if ("B".equals(rsltCd)) {
            return "승인반려" ;
        } else if ("N".equals(rsltCd)) {
            return "미제출" ;
        } else if ("S".equals(rsltCd)) {
            return "승인완료" ;
        } else if ("C".equals(rsltCd)) {
            return "신청취소" ;
        } else if ("H".equals(rsltCd)) {
            return "임의보류" ;
        } else {
            return rsltCd;
        }
    }


    public String getRsltMemo() {
        return rsltMemo;
    }
    public void setRsltMemo(String rsltMemo) {
        this.rsltMemo = rsltMemo;
    }
    public String getSysDt() {
        return sysDt;
    }
    public void setSysDt(String sysDt) {
        this.sysDt = sysDt;
    }
    public Date getRegDt() {
        return regDt;
    }
    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }



}
