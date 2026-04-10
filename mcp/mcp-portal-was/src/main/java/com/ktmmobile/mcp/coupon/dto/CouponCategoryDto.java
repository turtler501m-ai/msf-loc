package com.ktmmobile.mcp.coupon.dto;

import java.io.Serializable;
import java.util.List;

import com.ktmmobile.mcp.common.util.PageInfoBean;
import org.springframework.web.multipart.MultipartFile;

public class CouponCategoryDto implements Serializable{
    private static final long serialVersionUID = 1L;

    private String coupnCtgCd;
    private String coupnCtgNo;
    private String coupnCtgNm;
    private String coupnCtgUniqCd;
    private String coupnDivCd;
    private String coupnDivNm;
    private String coupnCtgBasDesc;
    private String coupnOutsideVendrNm;
    private String pointExchPosblYn;
    private String exchPoint;
    private String coupnAmt;
    private String coupnQnty;
    private String sortOdrg;
    private String smsSndPosblYn;
    private String useYn;
    private String pstngStartDate;
    private String pstngEndDate;
    private String cretIp; // 등록아이피
    private String cretId; // 생성자
    private String searchValue; // 검색어
    private String searchType; // 검색 유형
    private String tmpWorkSeq;
    private int coupnQntyCnt;
    /***20230316 wooki 추가 start****/
    private String tpChrg; //유료,무료 구분
    private String avPrd; //사용가능기간
    private String usePlc; //사용처
    private String linkPc; //링크URL(PC)
    private String linkMo; //링크URL(MO)
    private String thumbImgPc; //썸네일이미지경로(PC)
    private String detailImgPc; //상세이미지경로(PC)
    private String thumbImgMo; //썸네일이미지경로(MO)
    private String detailImgMo; //상세이미지경로(MO)
    private MultipartFile thumbImgPcFile; //썸네일이미지파일(PC)
    private MultipartFile thumbImgMoFile; //썸네일이미지파일(MO)
    private String leftQnty;  //남은수량
    private String dnStan;    //쿠폰발급기준
    private String coupnNo; //쿠폰번호
    private String smsRcvNo; //휴대폰번호
    private String coupnGiveDate; //쿠폰지급일자
    private String imgContentPc; //상세이미지(PC)
    private String imgContentMo; //상세이미지(Mo)
    private String imgDesc; //썸네일이미지설명(alt)
    private String userId; //홈페이지아이디
    private String useStartDate; //쿠폰사용 시작일
    private String useEndDate; //쿠폰사용 종료일
    /***20230316 wooki 추가 end****/
    private String couponCategory;
    private List<String> coupnCtgCdList;
    private List<String> smsSndPosblYnList;
    private List<String> coupnCtgNmList;
    private String downPossibleYn; //쿠폰다운로드 가능여부
    private String userDivision; // 회원구분

    private String benefitDesc;
    private String benefitDescDtl;
    private String usageWay;
    private String usageDesc;
    private String contact;
    private String  rateCd;
    private String couponLabel; //쿠폰 라벨

    public String getCoupnCtgCd() {
        return coupnCtgCd;
    }
    public void setCoupnCtgCd(String coupnCtgCd) {
        this.coupnCtgCd = coupnCtgCd;
    }
    public String getCoupnCtgNo() {
        return coupnCtgNo;
    }
    public void setCoupnCtgNo(String coupnCtgNo) {
        this.coupnCtgNo = coupnCtgNo;
    }
    public String getCoupnCtgNm() {
        return coupnCtgNm;
    }
    public void setCoupnCtgNm(String coupnCtgNm) {
        this.coupnCtgNm = coupnCtgNm;
    }
    public String getCoupnDivCd() {
        return coupnDivCd;
    }
    public void setCoupnDivCd(String coupnDivCd) {
        this.coupnDivCd = coupnDivCd;
    }
    public String getCoupnDivNm() {
        return coupnDivNm;
    }
    public void setCoupnDivNm(String coupnDivNm) {
        this.coupnDivNm = coupnDivNm;
    }
    public String getCoupnCtgBasDesc() {
        return coupnCtgBasDesc;
    }
    public void setCoupnCtgBasDesc(String coupnCtgBasDesc) {
        this.coupnCtgBasDesc = coupnCtgBasDesc;
    }
    public String getCoupnOutsideVendrNm() {
        return coupnOutsideVendrNm;
    }
    public void setCoupnOutsideVendrNm(String coupnOutsideVendrNm) {
        this.coupnOutsideVendrNm = coupnOutsideVendrNm;
    }
    public String getCoupnQnty() {
        return coupnQnty;
    }
    public void setCoupnQnty(String coupnQnty) {
        this.coupnQnty = coupnQnty;
    }
    public String getSortOdrg() {
        return sortOdrg;
    }
    public void setSortOdrg(String sortOdrg) {
        this.sortOdrg = sortOdrg;
    }
    public String getUseYn() {
        return useYn;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public String getPstngStartDate() {
        return pstngStartDate;
    }
    public void setPstngStartDate(String pstngStartDate) {
        this.pstngStartDate = pstngStartDate;
    }
    public String getPstngEndDate() {
        return pstngEndDate;
    }
    public void setPstngEndDate(String pstngEndDate) {
        this.pstngEndDate = pstngEndDate;
    }
    public String getCretIp() {
        return cretIp;
    }
    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getSearchValue() {
        return searchValue;
    }
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    public String getSearchType() {
        return searchType;
    }
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getCoupnCtgUniqCd() {
        return coupnCtgUniqCd;
    }
    public void setCoupnCtgUniqCd(String coupnCtgUniqCd) {
        this.coupnCtgUniqCd = coupnCtgUniqCd;
    }
    public String getPointExchPosblYn() {
        return pointExchPosblYn;
    }
    public void setPointExchPosblYn(String pointExchPosblYn) {
        this.pointExchPosblYn = pointExchPosblYn;
    }
    public String getExchPoint() {
        return exchPoint;
    }
    public void setExchPoint(String exchPoint) {
        this.exchPoint = exchPoint;
    }
    public String getCoupnAmt() {
        return coupnAmt;
    }
    public void setCoupnAmt(String coupnAmt) {
        this.coupnAmt = coupnAmt;
    }
    public String getSmsSndPosblYn() {
        return smsSndPosblYn;
    }
    public void setSmsSndPosblYn(String smsSndPosblYn) {
        this.smsSndPosblYn = smsSndPosblYn;
    }
    public String getTmpWorkSeq() {
        return tmpWorkSeq;
    }
    public void setTmpWorkSeq(String tmpWorkSeq) {
        this.tmpWorkSeq = tmpWorkSeq;
    }
    public int getCoupnQntyCnt() {
        return coupnQntyCnt;
    }
    public void setCoupnQntyCnt(int coupnQntyCnt) {
        this.coupnQntyCnt = coupnQntyCnt;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public String getTpChrg() {
        return tpChrg;
    }
    public void setTpChrg(String tpChrg) {
        this.tpChrg = tpChrg;
    }
    public String getAvPrd() {
        return avPrd;
    }
    public void setAvPrd(String avPrd) {
        this.avPrd = avPrd;
    }
    public String getUsePlc() {
        return usePlc;
    }
    public void setUsePlc(String usePlc) {
        this.usePlc = usePlc;
    }
    public String getLinkPc() {
        return linkPc;
    }
    public void setLinkPc(String linkPc) {
        this.linkPc = linkPc;
    }
    public String getLinkMo() {
        return linkMo;
    }
    public void setLinkMo(String linkMo) {
        this.linkMo = linkMo;
    }
    public String getThumbImgPc() {
        return thumbImgPc;
    }
    public void setThumbImgPc(String thumbImgPc) {
        this.thumbImgPc = thumbImgPc;
    }
    public String getDetailImgPc() {
        return detailImgPc;
    }
    public void setDetailImgPc(String detailImgPc) {
        this.detailImgPc = detailImgPc;
    }
    public String getThumbImgMo() {
        return thumbImgMo;
    }
    public void setThumbImgMo(String thumbImgMo) {
        this.thumbImgMo = thumbImgMo;
    }
    public String getDetailImgMo() {
        return detailImgMo;
    }
    public void setDetailImgMo(String detailImgMo) {
        this.detailImgMo = detailImgMo;
    }
    public MultipartFile getThumbImgPcFile() {
        return thumbImgPcFile;
    }
    public void setThumbImgPcFile(MultipartFile thumbImgPcFile) {
        this.thumbImgPcFile = thumbImgPcFile;
    }
    public MultipartFile getThumbImgMoFile() {
        return thumbImgMoFile;
    }
    public void setThumbImgMoFile(MultipartFile thumbImgMoFile) {
        this.thumbImgMoFile = thumbImgMoFile;
    }
    public String getLeftQnty() {
        return leftQnty;
    }
    public void setLeftQnty(String leftQnty) {
        this.leftQnty = leftQnty;
    }
    public String getDnStan() {
        return dnStan;
    }
    public void setDnStan(String dnStan) {
        this.dnStan = dnStan;
    }
    public String getCoupnNo() {
        return coupnNo;
    }
    public void setCoupnNo(String coupnNo) {
        this.coupnNo = coupnNo;
    }
    public String getSmsRcvNo() {
        return smsRcvNo;
    }
    public void setSmsRcvNo(String smsRcvNo) {
        this.smsRcvNo = smsRcvNo;
    }
    public String getCoupnGiveDate() {
        return coupnGiveDate;
    }
    public void setCoupnGiveDate(String coupnGiveDate) {
        this.coupnGiveDate = coupnGiveDate;
    }
    public String getImgContentPc() {
        return imgContentPc;
    }
    public void setImgContentPc(String imgContentPc) {
        this.imgContentPc = imgContentPc;
    }
    public String getImgContentMo() {
        return imgContentMo;
    }
    public void setImgContentMo(String imgContentMo) {
        this.imgContentMo = imgContentMo;
    }
    public String getImgDesc() {
        return imgDesc;
    }
    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUseEndDate() {
        return useEndDate;
    }
    public void setUseEndDate(String useEndDate) {
        this.useEndDate = useEndDate;
    }
    public String getUseStartDate() {
        return useStartDate;
    }
    public void setUseStartDate(String useStartDate) {
        this.useStartDate = useStartDate;
    }
    public String getCouponCategory() {
        return couponCategory;
    }
    public void setCouponCategory(String couponCategory) {
        this.couponCategory = couponCategory;
    }
    public List<String> getCoupnCtgCdList() {
        return coupnCtgCdList;
    }

    public void setCoupnCtgCdList(List<String> coupnCtgCdList) {
        this.coupnCtgCdList = coupnCtgCdList;
    }

    public List<String> getSmsSndPosblYnList() {
        return smsSndPosblYnList;
    }

    public void setSmsSndPosblYnList(List<String> smsSndPosblYnList) {
        this.smsSndPosblYnList = smsSndPosblYnList;
    }

    public List<String> getCoupnCtgNmList() {
        return coupnCtgNmList;
    }

    public void setCoupnCtgNmList(List<String> coupnCtgNmList) {
        this.coupnCtgNmList = coupnCtgNmList;
    }

    public String getDownPossibleYn() {
        return downPossibleYn;
    }

    public void setDownPossibleYn(String downPossibleYn) {
        this.downPossibleYn = downPossibleYn;
    }

    public String getUserDivision() {
        return userDivision;
    }

    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    public String getBenefitDesc() {
        return benefitDesc;
    }

    public void setBenefitDesc(String benefitDesc) {
        this.benefitDesc = benefitDesc;
    }

    public String getBenefitDescDtl() {
        return benefitDescDtl;
    }

    public void setBenefitDescDtl(String benefitDescDtl) {
        this.benefitDescDtl = benefitDescDtl;
    }

    public String getUsageWay() {
        return usageWay;
    }

    public void setUsageWay(String usageWay) {
        this.usageWay = usageWay;
    }

    public String getUsageDesc() {
        return usageDesc;
    }

    public void setUsageDesc(String usageDesc) {
        this.usageDesc = usageDesc;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getRateCd() {
        return rateCd;
    }
    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }

    public String getCouponLabel() {
        return couponLabel;
    }

    public void setCouponLabel(String couponLabel) {
        this.couponLabel = couponLabel;
    }
}
