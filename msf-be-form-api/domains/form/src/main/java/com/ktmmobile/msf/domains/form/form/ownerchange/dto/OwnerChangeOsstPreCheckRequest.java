package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import lombok.Data;

@Data
public class OwnerChangeOsstPreCheckRequest {

    private BaseInfo baseInfo;
    private RcvCustInfo rcvCustInfo;
    private RcvBillAcntInfo rcvBillAcntInfo;
    private PrdcList prdcList;

    /**
     * baseInfo(작업기준정보)
     */
    @Data
    public static class BaseInfo {

        private String mvnoOrdNo;            // MVNO 오더 번호
        private String slsCmpnCd;            // 판매회사코드
        private String custNo;               // 고객번호
        private String svcContId;            // 계약번호
        private String tlphNo;               // 전화번호
        private String mcnStatRsnCd;         // 명변 사유코드
        private String usimSuccYn;           // USIM 승계 여부
        private String iccId;                // USIM 일련번호
        private String realUseCustNm;        // 실사용고객명
        private String realUseCustBrthDate;  // 실사용자 생년월일
    }

    /**
     * rcvCustInfo(양수인 고객정보)
     */
    @Data
    public static class RcvCustInfo {

        private String custTypeCd;               // 양수인 고객유형코드
        private String custIdntNoIndCd;          // 고객식별번호구분코드
        private String custIdntNo;               // 고객식별번호
        private String crprNo;                   // 법인번호
        private String custNm;                   // 고객명
        private String myslAgreYn;               // 본인동의여부
        private String nativeRlnamAthnEvdnPprCd; // 실명인증증빙서류코드
        private String athnRqstcustCntplcNo;     // 인증요청고객연락처번호
        private String rsdcrtIssuDate;           // 주민등록증발급일자
        private String lcnsNo;                   // 면허번호
        private String lcnsRgnCd;                // 면허지역코드
        private String mrtrPrsnNo;               // 유공자번호
        private String nationalityCd;            // 국적코드
        private String fornBrthDate;             // 외국인생일일자
        private String crdtInfoAgreYn;           // 신용정보동의여부
        private String indvInfoInerPrcuseAgreYn; // 개인정보내부활용동의여부
        private String cnsgInfoAdvrRcvAgreYn;    // 위탁정보광고수신동의여부
        private String othcmpInfoAdvrRcvAgreYn;  // 타사정보광고수신동의여부
        private String othcmpInfoAdvrCnsgAgreYn; // 타사정보광고위탁동의여부
        private String grpAgntBindSvcSbscAgreYn; // 그룹사결합서비스가입동의여부
        private String cardInsrPrdcAgreYn;       // 카드보험상품동의여부
        private String olhRailCprtAgreYn;        // OLLEH철도제휴동의여부
        private String olhShckWibroRlfAgreYn;    // OLLEH쇼킹와이브로안심동의여부
        private String olngDscnHynmtrAgreYn;     // 주유할인현대자동차동의여부
        private String olhCprtPntAgreYn;         // OLLEH제휴포인트동의여부
        private String dwoCprtPntAgreYn;         // 대우제휴포인트동의여부
        private String wlfrDscnAplyAgreYn;       // 복지할인신청동의여부
        private String spamPrvdAgreYn;           // 스팸제공동의여부
        private String prttlpStlmUseAgreYn;      // 이동전화결제이용동의여부
        private String prttlpStlmPwdUseAgreYn;   // 이동전화결제비밀번호이용동의여부
        private String wrlnTlphNo;               // 유선전화번호
        private String rprsPrsnNm;               // 대표자명
        private String upjnCd;                   // 업종코드
        private String bcuSbst;                  // 업태내용
        private String zipNo;                    // 우편번호
        private String fndtCntplcSbst;           // 기본연락처내용
        private String mntCntplcSbst;            // 상세연락처내용
        private String brthDate;                 // 생일일자
        private String brthNnpIndCd;             // 생일음양구분코드
        private String jobCd;                    // 직업코드
        private String emlAdrsNm;                // 이메일주소명
        private String lstdIndCd;                // 상장구분코드
        private String emplCnt;                  // 사원수
        private String slngAmt;                  // 매출액
        private String cptlAmnt;                 // 자본금액
        private String crprUpjnCd;               // 법인업종코드
        private String crprBcuSbst;              // 법인업태내용
        private String crprZipNo;                // 법인우편번호
        private String crprFndtCntplcSbst;       // 법인기본연락처내용
        private String crprMntCntplcSbst;        // 법인상세연락처내용
        private String custInfoChngYn;           // 고객정보변경여부
        private String agntCustNm;               // 법정대리인 성명
        private String agntCustIdfyNoType;       // 법정대리인 식별번호 종류
        private String agntIdfyNoVal;            // 법정대리인 고객식별번호
        private String agntPersonSexDiv;         // 법정대리인 성별
        private String agntAgreYn;               // 법정대리인 정보조회 동의여부
        private String agntTelAthn;              // 법정대리인 연락처 종류
        private String agntTelNo;                // 대리인 연락처
        private String agntTypeCd;               // 법정대리인 유형
        private String agntNationalityCd;        // 법정대리인 국적코드
        private String agntRsdcrtIssuDate;       // 법정대리인 식별번호 발급일자
        private String agntRltnCd;               // 대리인관계코드
        private String agntBrthDate;             // 대리인생일일자
        private String agntRlnamAthnEvdnPprCd;   // 법정대리인실명인증증빙서류코드
        private String agntLicnsRgnCd;           // 법정대리인 면허지역코드
        private String agntLicnsNo;              // 법정대리인 면허번호
        private String homeTlphNo;               // 자택전화번호
        private String myslfAthnYn;              // 본인인증여부
        private String ipinCi;                   // 본인인증(CI)
        private String onlineAthnDivCd;          // 본인인증 수단
        private String fnncDealAgreeYn;          // 금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의
        private String photoAthnTxnSeq;          // 사진인증내역일련번호
        private String fathTransacId;            // 안면인증 트랜잭션 아이디
        private String cpntId;                   // 접점아이디
        private String indvLoInfoPrvAgreeYn;     // 개인위치정보제공동의여부
    }

    /**
     * rcvBillAcntInfo(양수인 청구계정정보)
     */
    @Data
    public static class RcvBillAcntInfo {

        private String rqsshtPprfrmCd;       // 청구서양식코드
        private String rqsshtTlphNo;         // 청구서 발송 전화번호
        private String rqsshtEmlAdrsNm;      // 청구서이메일주소명
        private String billZipNo;            // 청구우편번호
        private String billFndtCntplcSbst;   // 청구기본연락처내용
        private String billMntCntplcSbst;    // 청구상세연락처내용
        private String blpymMthdCd;          // 납부방법코드
        private String duedatDateIndCd;      // 납기일자구분코드
        private String crdtCardExprDate;     // 신용카드만기일자
        private String crdtCardKindCd;       // 신용카드종류코드
        private String bankCd;               // 은행코드
        private String blpymMthdIdntNo;      // 납부방법식별번호
        private String blpymCustNm;          // 납부고객명
        private String blpymCustIdntNo;      // 납부고객식별번호
        private String blpymMthdIdntNoHideYn; // 납부방법식별번호숨김여부
        private String bankSkipYn;           // 은행건너뛰기여부
        private String agreIndCd;            // 동의자료코드
        private String myslAthnTypeCd;       // 본인인증타입코드
        private String billAtchExclYn;       // 청구첨부제외여부
        private String rqsshtTlphNoHideYn;   // 청구서전화번호숨김여부
        private String rqsshtDsptYn;         // 청구서발송여부
        private String enclBillTrmnYn;       // 동봉청구해지여부
    }

    /**
     * prdcList
     */
    @Data
    public static class PrdcList {

        private String prdcCd;               // 상품코드
        private String prdcTypeCd;           // 상품타입코드
        private String ftrNewParam;          // 상품 파람
    }
}
