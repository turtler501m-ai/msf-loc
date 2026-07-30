package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
//@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormHC0InDtoRequest {

    private String mvnoOrdNo; //MVNO 오더 번호
    private String custNo; //고객번호
    private String svcContId; //서비스계약번호

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String tlphNo; //전화번호
    private String oderTypeCd; //오더유형코드
    private String instYn; //단말기할부여부
    private String indvInfoInerPrcuseAgreYn; //개인정보내부활용동의여부
    private String crdtInfoAgreYn; //신용정보동의여부
    private String spamPrvdAgreYn; //스팸제공동의여부
    private String custIdntNoIndCd; //고객식별번호구분코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custIdntNo; //고객식별번호
    private String crprNo; //법인번호

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custNm; //고객명
    private String rsdcrtIssuDate; //주민등록증발급일자
    private String nativeRlnamAthnEvdnPprCd; //실명인증증빙서류코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String lcnsNo; //면허번호
    private String lcnsRgnCd; //면허지역코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String mrtrPrsnNo; //유공자번호
    private String nationalityCd; //국적코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String fornBrthDate; //외국인생일일자

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String athnRqstcustCntplcNo; //인증요청고객연락처번호
    private String myslAgreYn; //본인동의여부
    private String agntRltnCd; //대리인관계코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String agntBrthDate; //대리인생년월일
    private String intmModelId; //기기모델아이디
    private String intmSrlNo; //기기일련번호
    private String prdcCd; //상품코드
    private String prdcTypeCd; //상품타입코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String iccId; //USIM 일련번호
    private String spclSlsNo; //판매정책번호
    private String spnsrTermTypeCd; //스폰서약정 유형코드
    private String enggMnthCnt; //약정 개월 수
    private String saleEngtOptnCd; //할인 유형 코드
    private String esimUseYn; //eSIM 사용여부

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String euiccId; //EID
    private String myslfAthnYn; //본인인증여부

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String ipinCi; //본인인증(CI)
    private String onlineAthnDivCd; //본인인증 수단
    private String photoAthnTxnSeq; //사진인증내역일련번호
    private String fathTransacId; //안면인증 트랜잭션 아이디
    private String cpntId; //접점아이디
}
