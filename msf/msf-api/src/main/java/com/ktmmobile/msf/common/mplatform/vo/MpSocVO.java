package com.ktmmobile.msf.common.mplatform.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpSocVO {
    private String socDescription;//부가서비스명
    private String socRateValue;//이용요금
    private String effectiveDate;//신청일자
    private String effectiveEndDate;//종료일
    private String socRateVatValue;//VAT포함이용요금
    private String soc;//부가서비스코드
    private String memo;//기타정보
    private int socRateVat;//VAT포함이용요금
    private String vatYn;
    private String updateFlag; //변경가능한 부가서비스 여부
    private String prodHstSeq; //상품일련번호
    private String paramSbst; //부가파람
    private String shareMainCtn; //대표회선 번호(서브상품 > 대표회선 계약번호로 get)
    private List<String> shareSubCtnList; //서브회선 번호(대표상품 > 서브회선 계약번호로 get)

    /** 부가파람 */
    private String strtDt; //시작일시
    private String endDt; //종료일시
    private String endDttm; //종료일시 + 1초 (시간을 보여주는 경우 H-1시 59분 59초가 아닌 H시를 보여주기 위해)
    private List<String> shareSubContidList; //서브회선 가입 계약번호
    private String shareMainContid; //대표상품 가입자 계약번호
    private String shareMainProdHstSeq; //대표상품 가입 일련번호
    private String prdcSrlNo; //상품일련번호
    private String alwFlg; //무제한 미제공 국가 및 비 제휴가 망 자동 사용여부


    /** 해지 가능 여부 */
    private String onlineCanYn;

    /** 해지 안내 문구 */
    private String canCmnt;

    private static final Logger logger = LoggerFactory.getLogger(MpSocVO.class);

    public void parseParamSbst() {
        String[] paramList = paramSbst.split("\\|");
        List<String> contidList = new ArrayList<String>();

        for(String param : paramList) {
            String[] paramSplit = param.split("\\=");
            String paramName = "";
            String paramVal = "";

            if(paramSplit.length > 1) {
                paramName = paramSplit[0];
                paramVal = paramSplit[1];
            } else {
                continue;
            }

            switch(paramName) {
                case "STRT_DT":
                    strtDt = StringUtils.rightPad(paramVal, 14, '0');
                    break;
                case "END_DT":
                    try {
                        endDt = StringUtils.rightPad(paramVal, 14, '0');
                        SimpleDateFormat dateForm = new SimpleDateFormat("yyyyMMddHHmmss");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dateForm.parse(endDt));
                        cal.add(Calendar.SECOND, 1);
                        endDttm = dateForm.format(cal.getTime());
                    } catch(ParseException e) {
                         logger.error("ParseException e : {}", e.getMessage());
                    }
                    break;
                case "SHARE_SUB_CONTID1":
                case "SHARE_SUB_CONTID2":
                case "SHARE_SUB_CONTID3":
                case "SHARE_SUB_CONTID4":
                    if(!"".equals(paramVal) && paramVal != null) {
                        contidList.add(paramVal);
                        this.setShareSubContidList(contidList);
                    }
                    break;
                case "SHARE_MAIN_CONTID":
                    shareMainContid = paramVal;
                    break;
                case "SHARE_MAIN_PROD_HST_SEQ":
                    shareMainProdHstSeq = paramVal;
                    break;
                case "PRDC_SRL_NO":
                    prdcSrlNo = paramVal;
                    break;
                case "ALW_FLG":
                    alwFlg = paramVal;
                    break;
                default :
            }
        }

    }

    public String getOnlineCanYn() {
        return onlineCanYn;
    }
    public void setOnlineCanYn(String onlineCanYn) {
        this.onlineCanYn = onlineCanYn;
    }
    public String getCanCmnt() {
        return canCmnt;
    }
    public void setCanCmnt(String canCmnt) {
        this.canCmnt = canCmnt;
    }
    public String getSocDescription() {
        return socDescription;
    }
    public void setSocDescription(String socDescription) {
        this.socDescription = socDescription;
    }
    public String getSocRateValue() {
        return socRateValue;
    }
    public void setSocRateValue(String socRateValue) {
        this.socRateValue = socRateValue;
    }
    public String getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    public String getEffectiveEndDate() {
        return effectiveEndDate;
    }
    public void setEffectiveEndDate(String effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }
    public String getSocRateVatValue() {
        return socRateVatValue;
    }
    public void setSocRateVatValue(String socRateVatValue) {
        this.socRateVatValue = socRateVatValue;
    }
    public String getSoc() {
        return soc;
    }
    public void setSoc(String soc) {
        this.soc = soc;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public int getSocRateVat() {
        return socRateVat;
    }
    public void setSocRateVat(int socRateVat) {
        this.socRateVat = socRateVat;
    }
    public String getUpdateFlag() {
        return updateFlag;
    }
    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }
    public String getVatYn() {
        return vatYn;
    }
    public void setVatYn(String vatYn) {
        this.vatYn = vatYn;
    }
    public String getProdHstSeq() {
        return prodHstSeq;
    }
    public void setProdHstSeq(String prodHstSeq) {
        this.prodHstSeq = prodHstSeq;
    }
    public String getParamSbst() {
        return paramSbst;
    }
    public void setParamSbst(String paramSbst) {
        this.paramSbst = paramSbst;
    }

    public List<String> getShareSubCtnList() {
        return shareSubCtnList;
    }

    public void setShareSubCtnList(List<String> shareSubCtnList) {
        this.shareSubCtnList = shareSubCtnList;
    }

    public String getStrtDt() {
        return strtDt;
    }

    public void setStrtDt(String strtDt) {
        this.strtDt = strtDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public String getShareMainContid() {
        return shareMainContid;
    }

    public void setShareMainContid(String shareMainContid) {
        this.shareMainContid = shareMainContid;
    }

    public List<String> getShareSubContidList() {
        return shareSubContidList;
    }

    public void setShareSubContidList(List<String> shareSubContidList) {
        this.shareSubContidList = shareSubContidList;
    }

    public String getShareMainProdHstSeq() {
        return shareMainProdHstSeq;
    }

    public void setShareMainProdHstSeq(String shareMainProdHstSeq) {
        this.shareMainProdHstSeq = shareMainProdHstSeq;
    }

    public String getPrdcSrlNo() {
        return prdcSrlNo;
    }

    public void setPrdcSrlNo(String prdcSrlNo) {
        this.prdcSrlNo = prdcSrlNo;
    }

    public String getAlwFlg() {
        return alwFlg;
    }

    public void setAlwFlg(String alwFlg) {
        this.alwFlg = alwFlg;
    }

    public String getShareMainCtn() {
        return shareMainCtn;
    }

    public void setShareMainCtn(String shareMainCtn) {
        this.shareMainCtn = shareMainCtn;
    }

    @Override
    public String toString() {
        return "MpSocVO [socDescription=" + socDescription + ", socRateValue=" + socRateValue + ", effectiveDate="
                + effectiveDate + ", effectiveEndDate=" + effectiveEndDate + ", socRateVatValue=" + socRateVatValue
                + ", soc=" + soc + ", memo=" + memo + ", socRateVat=" + socRateVat + ", vatYn=" + vatYn
                + ", updateFlag=" + updateFlag + ", prodHstSeq=" + prodHstSeq + ", paramSbst=" + paramSbst
                + ", shareSubCtnList=" + shareSubCtnList + ", strtDt=" + strtDt + ", endDt=" + endDt + ", endDttm="
                + endDttm + ", shareSubContidList=" + shareSubContidList + ", shareMainContid=" + shareMainContid
                + ", shareMainProdHstSeq=" + shareMainProdHstSeq + ", prdcSrlNo=" + prdcSrlNo + ", alwFlg=" + alwFlg
                + ", onlineCanYn=" + onlineCanYn + ", canCmnt=" + canCmnt + "]";
    }
}
