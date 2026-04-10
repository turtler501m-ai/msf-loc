package com.ktis.msp.org.bpamgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.util.List;

public class BpaMgmtUploadInfoVO extends BaseVo {

    private String bpaId;
    private Integer seq;
    private String uploadFileNm;
    private String contractNum;
    private String svcCntrNo;
    private String useYn;
    private String regstId;
    private String regstDttm;
    private String rvisnId;
    private String rvisnDttm;

    private List<BpaMgmtUploadInfoVO> items;

    public String getRegstDttm() {return regstDttm;}
    public void setRegstDttm(String regstDttm) {this.regstDttm = regstDttm;}
    public String getBpaId() {return bpaId;}
    public void setBpaId(String bpaId) {this.bpaId = bpaId;}
    public Integer getSeq() {return seq;}
    public void setSeq(Integer seq) {this.seq = seq;}
    public String getUploadFileNm() {return uploadFileNm;}
    public void setUploadFileNm(String uploadFileNm) {this.uploadFileNm = uploadFileNm;}
    public String getContractNum() {return contractNum;}
    public void setContractNum(String contractNum) {this.contractNum = contractNum;}
    public String getSvcCntrNo() {return svcCntrNo;}
    public void setSvcCntrNo(String svcCntrNo) {this.svcCntrNo = svcCntrNo;}
    public String getUSE_YN() {return useYn;}
    public void setUSE_YN(String useYn) {this.useYn = useYn;}
    public String getRegstId() {return regstId;}
    public void setRegstId(String regstId) {this.regstId = regstId;}
    public String getRvisnId() {return rvisnId;}
    public void setRvisnId(String rvisnId) {this.rvisnId = rvisnId;}
    public String getRvisnDttm() {return rvisnDttm;}
    public void setRVISN_DTTM(String rvisnDttm) {this.rvisnDttm = rvisnDttm;}
    public List<BpaMgmtUploadInfoVO> getItems() {
        return items;
    }

    public void setItems(List<BpaMgmtUploadInfoVO> items) {
        this.items = items;
    }

    public void setRvisnDttm(String rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
