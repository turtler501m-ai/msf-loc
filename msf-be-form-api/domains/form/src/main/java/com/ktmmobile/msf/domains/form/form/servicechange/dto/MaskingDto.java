package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 임성수
 *
 */
public class MaskingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    //마스킹해제 일련번호
    private long maskingReleaseSeq;
    //유저아이디
    private String userId;
    //마스킹해제 일련번호
    private String authType;
    //ci
    private String ci;
    //마스킹해제  시작시간
    private Date unmaskingStratDt;
    //마스킹해제 신청일
    private String cretDd;
    //처리 IP
    private String accessIp;
    //신청자 ID
    private String cretId;
    //수정자 ID
    private String amdId;
    //신청일시
    private Date cretDt;
    //수정일시
    private Date amdDt;


    //일련번호
    private long seq;
    //마스킹해제정보
    private String unmaskingInfo;
    //처리페이지URL
    private String accessUrl;

    public long getMaskingReleaseSeq() {
        return maskingReleaseSeq;
    }
    public void setMaskingReleaseSeq(long maskingReleaseSeq) {
        this.maskingReleaseSeq = maskingReleaseSeq;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAuthType() {
        return authType;
    }
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    public String getCi() {
        return ci;
    }
    public void setCi(String ci) {
        this.ci = ci;
    }
    public Date getUnmaskingStratDt() {
        return unmaskingStratDt;
    }
    public void setUnmaskingStratDt(Date unmaskingStratDt) {
        this.unmaskingStratDt = unmaskingStratDt;
    }
    public String getCretDd() {
        return cretDd;
    }
    public void setCretDd(String cretDd) {
        this.cretDd = cretDd;
    }
    public String getAccessIp() {
        return accessIp;
    }
    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getAmdId() {
        return amdId;
    }
    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
    public Date getCretDt() {
        return cretDt;
    }
    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }
    public Date getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }
    public long getSeq() {
        return seq;
    }
    public void setSeq(long seq) {
        this.seq = seq;
    }
    public String getUnmaskingInfo() {
        return unmaskingInfo;
    }
    public void setUnmaskingInfo(String unmaskingInfo) {
        this.unmaskingInfo = unmaskingInfo;
    }
    public String getAccessUrl() {
        return accessUrl;
    }
    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
}