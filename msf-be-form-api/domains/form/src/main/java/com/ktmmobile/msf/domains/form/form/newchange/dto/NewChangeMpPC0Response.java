package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

@Getter
@Setter
@NoArgsConstructor
public class NewChangeMpPC0Response {

    private Long requestKey;
    private String reqBuyType;
    private String operTypeCd;

    //InDto
    private String mvnoOrdNo; //MVNO 오더 번호
    private String slsCmpnCd = "KIS"; //판매회사코드
    private String custTypeCd; //고객유형코드
    private String custIdntNoIndCd; //고객식별번호구분코드

    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String custIdntNo; //고객식별번호

    private String crprNo; //법인번호
    private String custNm; //고객명
    private String cntrUseCd; //계약용도코드
    private String instYn; //할부여부
    private String scnhndPhonInstYn; //중고폰할부여부
    private String myslAgreYn; //본인동의여부
    private String nativeRlnamAthnEvdnPprCd; //실명인증증빙서류코드
    private String athnRqstcustCntplcNo; //인증요청고객연락처번호
    private String rsdcrtIssuDate; //주민등록증발급일자
    private String lcnsNo; //면허번호
    private String lcnsRgnCd; //면허지역코드
    private String mrtrPrsnNo; //유공자번호
    private String nationalityCd; //국적코드
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
    private String wrlnTlphNo; //유선전화번호
    private String tlphNo; //유선전화번호
    private String rprsPrsnNm; //대표자명
    private String upjnCd; //업종코드
    private String bcuSbst; //업태내용
    private String zipNo; //우편번호
    private String fndtCntplcSbst; //기본연락처내용
    private String mntCntplcSbst; //상세연락처내용
    private String brthDate; //생일일자
    private String brthNnpIndCd; //생일음양구분코드
    private String jobCd; //직업코드
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
    private String agntCustNm; //법정대리인 성명
    private String agntCustIdfyNoType; //법정대리인 식별번호 종류
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String agntIdfyNoVal; //법정대리인 고객식별번호
    private String agntPersonSexDiv; //법정대리인 성별
    private String agntAgreYn; //법정대리인 정보조회 동의여부
    private String agntTelAthn; //법정대리인 연락처 종류
    private String agntTelNo; //법정대리인 연락처
    private String agntTypeCd; //법정대리인 유형
    private String agntRsdcrtIssuDate; //법정대리인 식별번호 발급일자
    private String agntRlnamAthnEvdnPprCd; //법정대리인실명인증증빙서류코드
    private String agntLicnsRgnCd; //법정대리인 면허지역코드
    private String agntLicnsNo; //법정대리인 면허번호
    private String agntNationalityCd; //법정대리인 국적코드
    private String fnncDealAgreeYn; //금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의
    private String indvLoInfoPrvAgreeYn; //개인위치정보제공동의여부

    //FPC0
    private String mngmAgncId; //관리대리점 아이디
    private String cntpntCd; //접점코드
    private String frmpapId; //서식지 아이디
    //private String frmpapUseYn; //삭제요청 2026.06.26
    private String iselfFrmpapYn; //자체 서식지 여부 2026.07.14 - 자체 서식지 사용하는 경우 필수 Y: 자체 서식지 사용

    //PC0
    private String cpntId;
    private String photoAthnTxnSeq;
    private String fathTransacId;
    private String onlineAthnDivCd;
    private String myslfAthnYn;
    private String ipinCi;
    private String dlvrSeqNo;


    //InNpDto
    private String athnItemCd; //
    private String athnSbst; //
    private String bchngNpCommCmpnCd; //
    //private String crprNo; //
    //private String custIdntNo; //
    //private String custIdntNoIndCd; //
    //private String custNm; //
    //private String custTypeCd; //
    private String indvBizrYn; //
    private String npRstrtnContYn; //
    private String npTlphNo; //
    private String oderTypeCd; //
    //private String slsCmpnCd; //
    private String ytrpaySoffAgreYn; //

}
