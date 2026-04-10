package com.ktmmobile.mcp.common.mplatform.vo;

import java.text.ParseException;

import org.jdom.Element;

import com.ktmmobile.mcp.common.util.MaskingUtil;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.common.util.XmlParse;

public class MpMoscBilEmailInfoInVO extends CommonXmlVO{

    private String billTypeCd; //청구서발송유형
    private String email; //이메일주소
    private String maskedEmail; // 마스킹 처리된 이메일주소
    private String sendGubun; //명세서발송여부
    private String securMailYn; //보안메일여부
    private String ecRcvAgreYn; //이벤트수신동의여부
    private String ctn; //청구서발송전화번호
    private String slsCmpnCd; //청구서발송무선통신사업자
    private String adrCustNm; //주소명의자
    private String adrBasSbst; //구주소
    private String adrDtlSbst; //구주소상세
    private String adrZipCd; //구주소 우편번호
    private String rdAdrBasSbst; //신주소
    private String rdAdrDtlSbst; //신주소상세
    private String rdAdrZipCd; //신주소 우편번호

    @Override
    public void parse() throws ParseException {

        Element outMmsDto = XmlParse.getChildElement(this.body, "outMmsDto");
        Element outEmailDto = XmlParse.getChildElement(this.body, "outEmailDto");
        Element outMailDto = XmlParse.getChildElement(this.body, "outMailDto");
        Element outInfoDto = null;

        if (outMmsDto != null) { //MMS명세서일경우
            outInfoDto = outMmsDto;
        } else if (outEmailDto != null) { //이메일명세서일경우
            outInfoDto = outEmailDto;
        } else if (outMailDto != null) { //종이청구서일경우(우편)
            outInfoDto = outMailDto;
        }

        if (outInfoDto != null) {
            this.setBillTypeCd(XmlParse.getChildValue(outInfoDto, "billTypeCd"));

            String ctn = XmlParse.getChildValue(outInfoDto, "ctn");

            // this.setEmail(XmlParse.getChildValue(outInfoDto, "email"));
            // 이메일 마스킹 적용 2022.10.05
            String email= XmlParse.getChildValue(outInfoDto, "email");

            // 마스킹해제 인증 안했을시
            if(SessionUtils.getMaskingSession() == 0) {
                if (StringUtil.isNotEmpty(ctn)) {
                    this.setCtn(MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(ctn)));
                }
                if(StringUtil.isNotEmpty(email)) {
                    this.setMaskedEmail(MaskingUtil.getMaskedEmail2(email));
                }
            }else {
                if (StringUtil.isNotEmpty(ctn)) {
                    this.setCtn(StringUtil.getMobileFullNum(StringUtil.NVL(ctn,"")));
                }

                if(StringUtil.isNotEmpty(email)) {
                    this.setMaskedEmail(email);
                }
            }


            this.setEmail(email);
            this.setSlsCmpnCd(XmlParse.getChildValue(outInfoDto, "slsCmpnCd"));
            this.setSendGubun(XmlParse.getChildValue(outInfoDto, "sendGubun"));
            this.setSecurMailYn(XmlParse.getChildValue(outInfoDto, "securMailYn"));
            this.setEcRcvAgreYn(XmlParse.getChildValue(outInfoDto, "ecRcvAgreYn"));
            this.setAdrCustNm(XmlParse.getChildValue(outInfoDto, "adrCustNm"));
            this.setAdrBasSbst(XmlParse.getChildValue(outInfoDto, "adrBasSbst"));
            this.setAdrDtlSbst(XmlParse.getChildValue(outInfoDto, "adrDtlSbst"));
            this.setAdrZipCd(XmlParse.getChildValue(outInfoDto, "adrZipCd"));
            this.setRdAdrBasSbst(XmlParse.getChildValue(outInfoDto, "rdAdrBasSbst"));
            this.setRdAdrDtlSbst(XmlParse.getChildValue(outInfoDto, "rdAdrDtlSbst"));
            this.setRdAdrZipCd(XmlParse.getChildValue(outInfoDto, "rdAdrZipCd"));
        }
    }



    public String getMaskedEmail() {
        return maskedEmail;
    }
    public void setMaskedEmail(String maskedEmail) {
        this.maskedEmail = maskedEmail;
    }

    public String getBillTypeCd() {
        return billTypeCd;
    }
    public void setBillTypeCd(String billTypeCd) {
        this.billTypeCd = billTypeCd;
    }
    public String getEmail() {
        if (email == null) {
            return "";
        }
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSendGubun() {
        return sendGubun;
    }
    public void setSendGubun(String sendGubun) {
        this.sendGubun = sendGubun;
    }
    public String getSecurMailYn() {
        return securMailYn;
    }
    public void setSecurMailYn(String securMailYn) {
        this.securMailYn = securMailYn;
    }
    public String getEcRcvAgreYn() {
        return ecRcvAgreYn;
    }
    public void setEcRcvAgreYn(String ecRcvAgreYn) {
        this.ecRcvAgreYn = ecRcvAgreYn;
    }
    public String getCtn() {
        return ctn;
    }
    public void setCtn(String ctn) {
        this.ctn = ctn;
    }
    public String getSlsCmpnCd() {
        return slsCmpnCd;
    }
    public void setSlsCmpnCd(String slsCmpnCd) {
        this.slsCmpnCd = slsCmpnCd;
    }
    public String getAdrCustNm() {
        return adrCustNm;
    }
    public void setAdrCustNm(String adrCustNm) {
        this.adrCustNm = adrCustNm;
    }
    public String getAdrBasSbst() {
        return adrBasSbst;
    }
    public void setAdrBasSbst(String adrBasSbst) {
        this.adrBasSbst = adrBasSbst;
    }
    public String getAdrDtlSbst() {
        return adrDtlSbst;
    }
    public void setAdrDtlSbst(String adrDtlSbst) {
        this.adrDtlSbst = adrDtlSbst;
    }
    public String getAdrZipCd() {
        return adrZipCd;
    }
    public void setAdrZipCd(String adrZipCd) {
        this.adrZipCd = adrZipCd;
    }
    public String getRdAdrBasSbst() {
        return rdAdrBasSbst;
    }
    public void setRdAdrBasSbst(String rdAdrBasSbst) {
        this.rdAdrBasSbst = rdAdrBasSbst;
    }
    public String getRdAdrDtlSbst() {
        return rdAdrDtlSbst;
    }
    public void setRdAdrDtlSbst(String rdAdrDtlSbst) {
        this.rdAdrDtlSbst = rdAdrDtlSbst;
    }
    public String getRdAdrZipCd() {
        return rdAdrZipCd;
    }
    public void setRdAdrZipCd(String rdAdrZipCd) {
        this.rdAdrZipCd = rdAdrZipCd;
    }

    @Override
    public String toString() {
        return "MpMoscBilEmailInfoInVO [billTypeCd=" + billTypeCd + ", email="
                + email + ", sendGubun=" + sendGubun + ", securMailYn="
                + securMailYn + ", ecRcvAgreYn=" + ecRcvAgreYn + ", ctn=" + ctn
                + ", slsCmpnCd=" + slsCmpnCd + ", adrCustNm=" + adrCustNm
                + ", adrBasSbst=" + adrBasSbst + ", adrDtlSbst=" + adrDtlSbst
                + ", adrZipCd=" + adrZipCd + ", rdAdrBasSbst=" + rdAdrBasSbst
                + ", rdAdrDtlSbst=" + rdAdrDtlSbst + ", rdAdrZipCd="
                + rdAdrZipCd + "]";
    }


}
