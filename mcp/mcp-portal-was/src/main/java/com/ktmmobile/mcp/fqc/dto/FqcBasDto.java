package com.ktmmobile.mcp.fqc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FqcBasDto implements Serializable {

    private static final long serialVersionUID = 0xa46696b4bf17d9f2L;

    private List<FqcDltDto> fqcDltDtoList;

    private FqcPlcyBasDto fqcPlcyBas;  //정책

    /** 참여 일련번호 */
    private long fqcSeq =-1;
    /** 프리퀀시 정책 코드 */
    private String fqcPlcyCd;
    /** 회원아이디 */
    private String userId;
    /** 혜택코드 */
    private String bnfCd;
    /** 설명 */
    private String fqcDesc;
    /** 등록일 YYYYMMDD */
    private String sysRdateDd;
    /** 시작일 */
    private String strtDttm;
    /** 종료일 */
    private String endDttm;

    /** 등록아이피 */
    private String rip;
    /** 등록자 ID */
    private String regstId;
    /** 수정자 ID */
    private String rvisnId;


    /** 검색용 인자 값들 ... */

    /** 계약번호 */
    private String contractNum ;

    /** 개통일 */
    private String openDate ;

    /** 수요금제 코드 */
    private String socCode ;

    /** 로그인 한 사용자 정보 */
    private String custId ="" ;     // 로그인 한 사용자 정보

    /** 로그인 한 사용자 정보 */
    private String ctn = ""  ;

    /** 수정일시 */
    private Date rvisnDttm;


    public List<FqcDltDto> getFqcDltDtoList() {
        return fqcDltDtoList;
    }

    public void setFqcDltDtoList(List<FqcDltDto> fqcDltDtoList) {
        this.fqcDltDtoList = fqcDltDtoList;
    }

    public long getFqcSeq() {
        return fqcSeq;
    }

    public void setFqcSeq(long fqcSeq) {
        this.fqcSeq = fqcSeq;
    }

    public String getFqcPlcyCd() {
        return fqcPlcyCd;
    }

    public void setFqcPlcyCd(String fqcPlcyCd) {
        this.fqcPlcyCd = fqcPlcyCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBnfCd() {
        return bnfCd;
    }

    public void setBnfCd(String bnfCd) {
        this.bnfCd = bnfCd;
    }

    public String getFqcDesc() {
        return fqcDesc;
    }

    public void setFqcDesc(String fqcDesc) {
        this.fqcDesc = fqcDesc;
    }

    public String getSysRdateDd() {
        return sysRdateDd;
    }

    public void setSysRdateDd(String sysRdateDd) {
        this.sysRdateDd = sysRdateDd;
    }

    public String getStrtDttm() {
        return strtDttm;
    }

    public void setStrtDttm(String strtDttm) {
        this.strtDttm = strtDttm;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getSocCode() {
        return socCode;
    }

    public void setSocCode(String socCode) {
        this.socCode = socCode;
    }

    public FqcPlcyBasDto getFqcPlcyBas() {
        return fqcPlcyBas;
    }

    public void setFqcPlcyBas(FqcPlcyBasDto fqcPlcyBas) {
        this.fqcPlcyBas = fqcPlcyBas;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public Date getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public int getMsnCnt() {
        /** 미션 수행 건수 */
        int msnCnt = 0;

        if (fqcDltDtoList !=null && fqcDltDtoList.size() > 0) {
            for (FqcDltDto dlt : fqcDltDtoList) {
                String stateCode = dlt.getStateCode();
                if ("02".equals(dlt.getStateCode())) {
                    msnCnt++ ;
                }
            }
        }

        return msnCnt;
    }
}
