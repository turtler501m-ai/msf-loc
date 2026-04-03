package com.ktmmobile.msf.system.common.dto;

import java.io.Serializable;

/**
 * MVNO 결합
 * X77
 *
 */
public class MoscCombReqDto implements Serializable  {

    private static final long serialVersionUID = 1L;

    private String custId;
    private String ncn;
    private String ctn;

    // X77
    private String cmbStndSvcNo;//	결합서비스번호	20	C	"KT 회선일 경우 필수 모바일 회선: 전화번호 인터넷: ID "
    private String combSrchId; //결합 모회선 조회값	10	O	"MVNO회선은 전화번호 KT 회선은 이름
    private String svcIdfyNo;	//서비스 확인 번호	20	C	생년월일 ex) 19821120 (KT회선일 경우 필수)
    private String sexCd;	//성별코드	1	C	1: 남성, 2: 여성 (KT 회선일 경우 필수)
    private String combSvcNoCd; //결합대상 회선번호 combSvcNoCd(결합대상 회선 사업자 구분)이 "K" 인 경우 필수 모바일: 회선번호 인터넷: KT 인터넷ID
    private String sameCustKtRetvYn; // 동일명의로 가입된 KT 회선 정보 조회 여부 	Y: 조회 / null: 조회하지 않음
                                     //(Y 입력 시 아래 항목 무시됨)
                                     //	combSvcNoCd 결합 모회선 사업자 구분 코드 / combSrchId 결합 모회선 조회값 / svcIdfyNo 서비스 확인 번호 / sexCd 성별코드 / cmbStndSvcNo 결합서비스번호

    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getNcn() {
        return ncn;
    }
    public void setNcn(String ncn) {
        this.ncn = ncn;
    }
    public String getCtn() {
        return ctn;
    }
    public void setCtn(String ctn) {
        this.ctn = ctn;
    }
    public String getCombSrchId() {
        return combSrchId;
    }
    public void setCombSrchId(String combSrchId) {
        this.combSrchId = combSrchId;
    }
    public String getSvcIdfyNo() {
        return svcIdfyNo;
    }
    public void setSvcIdfyNo(String svcIdfyNo) {
        this.svcIdfyNo = svcIdfyNo;
    }
    public String getSexCd() {
        return sexCd;
    }
    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }
    public String getCmbStndSvcNo() {
        return cmbStndSvcNo;
    }
    public void setCmbStndSvcNo(String cmbStndSvcNo) {
        this.cmbStndSvcNo = cmbStndSvcNo;
    }
    public String getCombSvcNoCd() {
        return combSvcNoCd;
    }
    public void setCombSvcNoCd(String combSvcNoCd) {
        this.combSvcNoCd = combSvcNoCd;
    }
    public String getSameCustKtRetvYn() {
        return sameCustKtRetvYn;
    }
    public void setSameCustKtRetvYn(String sameCustKtRetvYn) {
        this.sameCustKtRetvYn = sameCustKtRetvYn;
    }

}
