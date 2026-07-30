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
public class MplatFormPC0InDtoRequest {

    private String mvnoOrdNo; //MVNO 오더 번호
    private String slsCmpnCd = "KIS"; //판매회사코드
    private String custTypeCd; //고객유형코드
    private String custIdntNoIndCd; //고객식별번호구분코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custIdntNo; //고객식별번호
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String crprNo; //법인번호

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custNm; //고객명
    private String cntrUseCd; //계약용도코드
    private String instYn; //할부여부
    private String scnhndPhonInstYn; //중고폰할부여부
    private String myslAgreYn; //본인동의여부
    private String nativeRlnamAthnEvdnPprCd; //실명인증증빙서류코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String athnRqstcustCntplcNo; //인증요청고객연락처번호
    private String rsdcrtIssuDate; //주민등록증발급일자

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String lcnsNo; //면허번호
    private String lcnsRgnCd; //면허지역코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String mrtrPrsnNo; //유공자번호
    private String nationalityCd; //국적코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String fornBrthDate; //외국인생일일자
    private String crdtInfoAgreYn; //신용정보동의여부
    private String indvInfoInerPrcuseAgreYn; //개인정보내부활용동의여부
    private String cnsgInfoAdvrRcvAgreYn; //위탁정보광고수신동의여부
    private String othcmpInfoAdvrRcvAgreYn; //타사정보광고수신동의여부
    private String othcmpInfoAdvrCnsgAgreYn; //타사정보광고위탁동의여부
    private String grpAgntBindSvcSbscAgreYn; //그룹사결합서비스가입동의여부
    private String cardInsrPrdcAgreYn; //카드보험상품동의여부
    private String olngDscnHynmtrAgreYn; //주유할인현대자동차동의여부
    private String wlfrDscnAplyAgreYn; //복지할인신청동의여부
    private String spamPrvdAgreYn; //스팸제공동의여부
    private String prttlpStlmUseAgreYn; //이동전화결제이용동의여부
    private String prttlpStlmPwdUseAgreYn; //이동전화결제비밀번호이용동의여부

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String wrlnTlphNo; //유선전화번호

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String rprsPrsnNm; //대표자명
    private String upjnCd; //업종코드
    private String bcuSbst; //업태내용
    private String zipNo; //우편번호

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String fndtCntplcSbst; //기본연락처내용
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String mntCntplcSbst; //상세연락처내용

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String brthDate; //생일일자
    private String brthNnpIndCd; //생일음양구분코드
    private String jobCd; //직업코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String emlAdrsNm; //이메일주소명
    private String lstdIndCd; //상장구분코드
    private String emplCnt; //사원수
    private String slngAmt; //매출액
    private String cptlAmnt; //자본금액
    private String crprUpjnCd; //법인업종코드
    private String crprBcuSbst; //법인업태내용
    private String crprZipNo; //법인우편번호
    private String crprFndtCntplcSbst; //법인기본연락처내용
    private String crprMntCntplcSbst; //법인상세연락처내용
    private String custInfoChngYn; //고객정보변경여부
    private String m2mHndsetYn; //M2M단말여부

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String agntCustNm; //법정대리인 성명
    private String agntCustIdfyNoType; //법정대리인 식별번호 종류

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String agntIdfyNoVal; //법정대리인 고객식별번호
    private String agntPersonSexDiv; //법정대리인 성별
    private String agntAgreYn; //법정대리인 정보조회 동의여부
    private String agntTelAthn; //법정대리인 연락처 종류

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String agntTelNo; //법정대리인 연락처
    private String agntTypeCd; //법정대리인 유형
    private String agntRsdcrtIssuDate; //법정대리인 식별번호 발급일자
    private String agntRlnamAthnEvdnPprCd; //법정대리인실명인증증빙서류코드
    private String agntLicnsRgnCd; //법정대리인 면허지역코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String agntLicnsNo; //법정대리인 면허번호
    private String agntNationalityCd; //법정대리인 국적코드

    private String fnncDealAgreeYn; //금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의
    private String indvLoInfoPrvAgreeYn; //개인위치정보제공동의여부

    private String myslfAthnYn; //본인인증여부

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String ipinCi; // 본인인증(CI)

    private String onlineAthnDivCd; //본인인증 수단

    private String photoAthnTxnSeq; //사진인증내역일련번호
    private String dlvrSeqNo;  //유심대면배송 주문접수번호
    private String fathTransacId; //안면인증 트랜잭션 아이디
    private String cpntId; //접점아이디

}
