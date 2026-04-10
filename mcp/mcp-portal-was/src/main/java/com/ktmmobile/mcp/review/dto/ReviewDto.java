package com.ktmmobile.mcp.review.dto;

import com.ktmmobile.mcp.common.util.NmcpServiceUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReviewDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 이미지 리스트 정보 */
    List<ReviewImgDto> reviewImgList;

    /* 리뷰 시퀀스 */
    private int reviewSeq;

    /* 계약번호 */
    private String contractNum;

    /* 포탈ID */
    private String userId;

    /* 상품후기 */
    private String reviewContent;

    /* 사용후기 종류: 공통코드 */
    private String reviewType;

    /* 사용후기 상세: 공통코드 */
    private String reviewTypeDtl;

    /* 공지여부 */
    private String notiYn;

    /* SNS 공유 URL */
    private String snsInfo;

    /* 당첨내용 */
    private String prizeRnk;

    /* 상태여부: 1(표현), 0(미표현) */
    private String useYn;

    /* 등록아이피 */
    private String rIp;

    /* 작성자 */
    private String regstNm;
    private String mkRegstNm;

    /* 등록자 */
    private String regstId;

    /* 등록일시 */
    private Date regstDttm;

    /* 등록일 */
    private String regstDt;

    private String phone;
    private String name;

    /* 검색 기능 */
    private String searchCategory;
    private String searchValue;

    private List<String> contractNumList;
    private String subLinkName;
    private int totalCount = 0;
    private int imgSeq = -1;

    /* 우수후기 */
    private String bestYn;

    private String startDate;
    private String endDate;

    private String img1;
    private String img2;
    private String img3;
    private String img4;

    /* 내 후기 여부 */
    private String isOwn;

    /* 비교날짜 */
    private Date baseDate;

    public List<ReviewImgDto> getReviewImgList() {
        return reviewImgList;
    }

    public void setReviewImgList(List<ReviewImgDto> reviewImgList) {
        this.reviewImgList = reviewImgList;
    }

    public int getReviewSeq() {
        return reviewSeq;
    }

    public void setReviewSeq(int reviewSeq) {
        this.reviewSeq = reviewSeq;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewTitle() {
        if (reviewContent != null) {
            // 개행 문자 제거
            String value = reviewContent.replaceAll("(\r\n|\r|\n|\n\r)", " ");
            int valueInt = value.length();
            if (valueInt > 30) {
                return value.substring(0, 30)+"...";
            } else {
                return value;
            }
        } else {
            return "";
        }
    }

    public String getReviewType() {
        return reviewType;
    }

    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }

    public String getReviewTypeDtl() {
        return reviewTypeDtl;
    }

    public void setReviewTypeDtl(String reviewTypeDtl) {
        this.reviewTypeDtl = reviewTypeDtl;
    }

    public String getReviewTypeNm() { return NmcpServiceUtils.getCodeNm("McashReviewEvent", reviewTypeDtl);
    }

    public String getNotiYn() {
        return notiYn;
    }

    public void setNotiYn(String notiYn) {
        this.notiYn = notiYn;
    }

    public String getNotiYnTxt() {
        if ("Y".equals(notiYn)) {
            return "<span class='noticeY'>공지</span>";
        } else {
            return this.reviewSeq + "";
        }
    }

    public String getSnsInfo() {
        return snsInfo;
    }

    public void setSnsInfo(String snsInfo) {
        this.snsInfo = snsInfo;
    }

    public String getPrizeRnk() {
        return prizeRnk;
    }

    public void setPrizeRnk(String prizeRnk) {
        this.prizeRnk = prizeRnk;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getUseYnTxt() {
        if ("Y".equals(useYn)) {
            return "<button class='active_Y'></button>";
        } else {
            return "<button class='active_N'></button>";
        }
    }

    public String getrIp() {
        return rIp;
    }

    public void setrIp(String rIp) {
        this.rIp = rIp;
    }

    public String getRegstNm() {
        return regstNm;
    }

    public void setRegstNm(String regstNm) {
        this.regstNm = regstNm;
    }

    /*
     * 3개월이상이면 이름비노출
     */
    public String getMkRegstNm() {
        if (regstDttm != null && baseDate != null) {
            int compare = regstDttm.compareTo(baseDate);
            if (compare < 0) {
                return "";
            } else {
                return mkRegstNm + "님";
            }
        }

        return mkRegstNm + "님";
    }

    public void setMkRegstNm(String mkRegstNm) {
        this.mkRegstNm = mkRegstNm;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public Date getRegstDttm() {
        return regstDttm;
    }

    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
    }

    public String getRegstDt() {
        return regstDt;
    }

    public void setRegstDt(String regstDt) {
        this.regstDt = regstDt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public List<String> getContractNumList() {
        return contractNumList;
    }

    public void setContractNumList(List<String> contractNumList) {
        this.contractNumList = contractNumList;
    }

    public String getSubLinkName() {
        return subLinkName;
    }

    public void setSubLinkName(String subLinkName) {
        this.subLinkName = subLinkName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getImgSeq() {
        return imgSeq;
    }

    public void setImgSeq(int imgSeq) {
        this.imgSeq = imgSeq;
    }

    public String getBestYn() {
        return bestYn;
    }

    public void setBestYn(String bestYn) {
        this.bestYn = bestYn;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public Date getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    public String getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(String isOwn) {
        this.isOwn = isOwn;
    }
}
