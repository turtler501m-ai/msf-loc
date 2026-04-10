package com.ktmmobile.mcp.cs.dto;

import java.io.Serializable;
import java.util.List;

public class BookingUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String csResSeq;
    private String regstDt;   //등록일
    private String regstDttm; //등록일시
    private String csResDt;   //예약일
    private String csResTm;   //예약 시간 코드
    private String usrId;     //사용자 아이디
    private String contractNum;  //계약번호
    private String vocFir;      //voc대
    private String vocSec;      //voc중
    private String vocThi;      //voc소
    private String vocDtl;      //voc상세
    private String csSubject;   //문의 제목
    private String csContent;   //문의 내용
    private String csStatCd;    //처리결과(VOC0007)
    private String apId;
    private String endDttm;
    private String usrNm;      //사용자 이름
    private String unSvcNo;     //사용자 폰번호
    private String delYn;
    private int skipResult;
    private int maxResult;

    private List<String> contractNumList;


    //페이징
    private int pagingPosition;		//<<  <   1 2 3 4 5   >  >>
    private int pagingStartNo;		//페이지네이션 시작 변수
    private int pagingEndNo;		//페이지네이션 끝 변수
    private int pagingStart;		//페이지 처음 <<
    private int pagingFront;		//페이지 앞을호 <
    private int pagingNext;			//페이지 다음 >
    private int pagingEnd;			//페이지 마지막 >>
    private int pagingSize;			//페이지 사이즈>>
    private int pageNo;

    private String userDivision;



    public String getUnSvcNo() {
        return unSvcNo;
    }

    public void setUnSvcNo(String unSvcNo) {
        this.unSvcNo = unSvcNo;
    }


    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getCsResSeq() {
        return csResSeq;
    }


    public String getUsrNm() {
        return usrNm;
    }

    public void setUsrNm(String usrNm) {
        this.usrNm = usrNm;
    }


    public int getSkipResult() {
        return skipResult;
    }

    public void setSkipResult(int skipResult) {
        this.skipResult = skipResult;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPagingPosition() {
        return pagingPosition;
    }

    public void setPagingPosition(int pagingPosition) {
        this.pagingPosition = pagingPosition;
    }

    public int getPagingStartNo() {
        return pagingStartNo;
    }

    public void setPagingStartNo(int pagingStartNo) {
        this.pagingStartNo = pagingStartNo;
    }

    public int getPagingEndNo() {
        return pagingEndNo;
    }

    public void setPagingEndNo(int pagingEndNo) {
        this.pagingEndNo = pagingEndNo;
    }

    public int getPagingStart() {
        return pagingStart;
    }

    public void setPagingStart(int pagingStart) {
        this.pagingStart = pagingStart;
    }

    public int getPagingFront() {
        return pagingFront;
    }

    public void setPagingFront(int pagingFront) {
        this.pagingFront = pagingFront;
    }

    public int getPagingNext() {
        return pagingNext;
    }

    public void setPagingNext(int pagingNext) {
        this.pagingNext = pagingNext;
    }

    public int getPagingEnd() {
        return pagingEnd;
    }

    public void setPagingEnd(int pagingEnd) {
        this.pagingEnd = pagingEnd;
    }

    public int getPagingSize() {
        return pagingSize;
    }

    public void setPagingSize(int pagingSize) {
        this.pagingSize = pagingSize;
    }

    public void setCsResSeq(String csResSeq) {
        this.csResSeq = csResSeq;
    }

    public String getRegstDt() {
        return regstDt;
    }

    public void setRegstDt(String regstDt) {
        this.regstDt = regstDt;
    }

    public String getRegstDttm() {
        return regstDttm;
    }

    public void setRegstDttm(String regstDttm) {
        this.regstDttm = regstDttm;
    }

    public String getCsResDt() {
        return csResDt;
    }

    public void setCsResDt(String csResDt) {
        this.csResDt = csResDt;
    }

    public String getCsResTm() {
        return csResTm;
    }

    public void setCsResTm(String csResTm) {
        this.csResTm = csResTm;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getVocFir() {
        return vocFir;
    }

    public void setVocFir(String vocFir) {
        this.vocFir = vocFir;
    }

    public String getVocSec() {
        return vocSec;
    }

    public void setVocSec(String vocSec) {
        this.vocSec = vocSec;
    }

    public String getVocThi() {
        return vocThi;
    }

    public void setVocThi(String vocThi) {
        this.vocThi = vocThi;
    }

    public String getVocDtl() {
        return vocDtl;
    }

    public void setVocDtl(String vocDtl) {
        this.vocDtl = vocDtl;
    }

    public String getCsSubject() {
        return csSubject;
    }

    public void setCsSubject(String csSubject) {
        this.csSubject = csSubject;
    }

    public String getCsContent() {
        return csContent;
    }

    public void setCsContent(String csContent) {
        this.csContent = csContent;
    }

    public String getCsStatCd() {
        return csStatCd;
    }

    public void setCsStatCd(String csStatCd) {
        this.csStatCd = csStatCd;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public List<String> getContractNumList() {
        return contractNumList;
    }

    public void setContractNumList(List<String> contractNumList) {
        this.contractNumList = contractNumList;
    }

    public String getUserDivision() {
        return userDivision;
    }

    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }

    @Override
    public String toString() {
        return "BookingUserDto{" +
                "csResSeq='" + csResSeq + '\'' +
                ", regstDt='" + regstDt + '\'' +
                ", regstDttm='" + regstDttm + '\'' +
                ", csResDt='" + csResDt + '\'' +
                ", csResTm='" + csResTm + '\'' +
                ", usrId='" + usrId + '\'' +
                ", contractNum='" + contractNum + '\'' +
                ", vocFir='" + vocFir + '\'' +
                ", vocSec='" + vocSec + '\'' +
                ", vocThi='" + vocThi + '\'' +
                ", vocDtl='" + vocDtl + '\'' +
                ", csSubject='" + csSubject + '\'' +
                ", csContent='" + csContent + '\'' +
                ", csStatCd='" + csStatCd + '\'' +
                ", apId='" + apId + '\'' +
                ", endDttm='" + endDttm + '\'' +
                ", usrNm='" + usrNm + '\'' +
                ", unSvcNo='" + unSvcNo + '\'' +
                ", delYn='" + delYn + '\'' +
                ", skipResult=" + skipResult +
                ", maxResult=" + maxResult +
                ", contractNumList=" + contractNumList +
                ", pagingPosition=" + pagingPosition +
                ", pagingStartNo=" + pagingStartNo +
                ", pagingEndNo=" + pagingEndNo +
                ", pagingStart=" + pagingStart +
                ", pagingFront=" + pagingFront +
                ", pagingNext=" + pagingNext +
                ", pagingEnd=" + pagingEnd +
                ", pagingSize=" + pagingSize +
                ", pageNo=" + pageNo +
                '}';
    }
}
