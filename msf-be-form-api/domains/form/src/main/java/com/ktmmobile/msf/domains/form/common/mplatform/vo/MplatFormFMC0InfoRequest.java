package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpBaseRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class MplatFormFMC0InfoRequest extends MpBaseRequest {

    private BaseInfo baseInfo;
    private RcvCustInfo rcvCustInfo;
    private RcvBillAcntInfo rcvBillAcntInfo;
    private PrdcInfo prdcInfo;
    private InFrmpapDto inFrmpapDto;

    // =========================
    // baseInfo
    // =========================
    @Data
    public static class BaseInfo {

        private String mvnoOrdNo;           // MVNO 오더 번호
        private String slsCmpnCd;           // 판매회사코드
        private String custNo;              // 고객번호
        private String svcContId;           // 계약번호
        private String tlphNo;              // 전화번호
        private String mcnStatRsnCd;        // 명변 사유코드
        private String usimSuccYn;          // USIM 승계 여부
        private String iccId;               // USIM 일련번호
        private String realUseCustNm;       // 실사용고객명
        private String realUseCustBrthDate; // 실사용자 생년월일
    }
    // =========================

    // =========================
    // rcvCustInfo
    // =========================
    @Data
    public static class RcvCustInfo {

        private String custTypeCd;              // 양수인 고객유형코드
        private String custIdntNoIndCd;         // 고객식별번호구분코드
        private String custIdntNo;              // 고객식별번호
        private String crprNo;                  // 법인번호
        private String custNm;                  // 고객명
        private String myslAgreYn;              // 본인동의여부
        private String nativeRlnamAthnEvdnPprCd;// 실명인증증빙서류코드
        private String athnRqstcustCntplcNo;    // 인증요청고객연락처번호
        private String rsdcrtIssuDate;          // 주민등록증발급일자
        private String lcnsNo;                  // 면허번호
        private String lcnsRgnCd;               // 면허지역코드
        private String mrtrPrsnNo;              // 유공자번호
        private String nationalityCd;            // 국적코드
        private String fornBrthDate;            // 외국인생일일자
        private String crdtInfoAgreYn;          // 신용정보동의여부
        private String indvInfoInerPrcuseAgreYn;// 개인정보내부활용동의여부
        private String cnsgInfoAdvrRcvAgreYn;   // 위탁정보광고수신동의여부
        private String othcmpInfoAdvrRcvAgreYn; // 타사정보광고수신동의여부
        private String othcmpInfoAdvrCnsgAgreYn;// 타사정보광고위탁동의여부
        private String grpAgntBindSvcSbscAgreYn;// 그룹사결합서비스가입동의여부
        private String cardInsrPrdcAgreYn;      // 카드보험상품동의여부
        private String olngDscnHynmtrAgreYn;    // 주유할인동의
        private String wlfrDscnAplyAgreYn;      // 복지할인신청동의
        private String spamPrvdAgreYn;          // 스팸제공동의
        private String prttlpStlmUseAgreYn;     // 이동전화결제이용동의
        private String prttlpStlmPwdUseAgreYn;  // 이동전화결제비밀번호동의
        private String wrlnTlphNo;              // 유선전화번호
        private String rprsPrsnNm;              // 대표자명
        private String upjnCd;                  // 업종코드
        private String bcuSbst;                 // 업태내용
        private String zipNo;                   // 우편번호
        private String fndtCntplcSbst;          // 기본연락처
        private String mntCntplcSbst;           // 상세연락처
        private String brthDate;                // 생일일자
        private String brthNnpIndCd;            // 음양구분
        private String jobCd;                   // 직업코드
        private String emlAdrsNm;               // 이메일주소
        private String lstdIndCd;               // 상장구분코드
        private String emplCnt;                 // 사원수
        private String slngAmt;                 // 매출액
        private String cptlAmnt;                // 자본금
        private String crprUpjnCd;              // 법인업종코드
        private String crprBcuSbst;             // 법인업태
        private String crprZipNo;               // 법인우편번호
        private String crprFndtCntplcSbst;      // 법인기본주소
        private String crprMntCntplcSbst;       // 법인상세주소
        private String custInfoChngYn;          // 고객정보변경여부
        private String agntCustNm;              // 대리인명
        private String agntCustIdfyNoType;     // 대리인 식별번호 타입
        private String agntIdfyNoVal;          // 대리인 식별번호
        private String agntPersonSexDiv;       // 대리인 성별
        private String agntAgreYn;             // 대리인 동의여부
        private String agntTelAthn;            // 대리인 연락처종류
        private String agntTelNo;              // 대리인 연락처
        private String agntTypeCd;             // 대리인 유형
        private String agntNationalityCd;      // 대리인 국적
        private String agntRsdcrtIssuDate;     // 대리인 발급일자
        private String agntRltnCd;             // 대리인관계코드
        private String agntBrthDate;           // 대리인생일
        private String agntRlnamAthnEvdnPprCd; // 대리인 실명인증서류
        private String agntLicnsRgnCd;         // 대리인 면허지역
        private String agntLicnsNo;            // 대리인 면허번호
        private String homeTlphNo;             // 자택전화
        private String fnncDealAgreeYn;        // 금융거래동의
        private String indvLoInfoPrvAgreeYn;   // 위치정보동의
    }
    // =========================

    // =========================
    // rcvBillAcntInfo
    // =========================
    @Data
    public static class RcvBillAcntInfo {

        private String rqsshtPprfrmCd;     // 청구서양식코드
        private String rqsshtTlphNo;        // 청구서 전화번호
        private String rqsshtEmlAdrsNm;     // 청구서 이메일
        private String billZipNo;           // 청구 우편번호
        private String billFndtCntplcSbst;  // 청구 기본주소
        private String billMntCntplcSbst;   // 청구 상세주소
        private String blpymMthdCd;         // 납부방법
        private String duedatDateIndCd;     // 납기일자구분
        private String crdtCardExprDate;    // 카드만료일
        private String crdtCardKindCd;      // 카드종류
        private String bankCd;              // 은행코드
        private String blpymMthdIdntNo;     // 납부식별번호
        private String blpymCustNm;         // 납부고객명
        private String blpymCustIdntNo;     // 납부고객식별번호
        private String blpymMthdIdntNoHideYn;// 납부식별숨김
        private String bankSkipYn;          // 은행스킵여부
        private String agreIndCd;           // 동의코드
        private String myslAthnTypeCd;      // 인증타입
        private String billAtchExclYn;      // 청구첨부제외
        private String rqsshtTlphNoHideYn;  // 청구전화숨김
        private String rqsshtDsptYn;        // 청구발송여부
        private String enclBillTrmnYn;      // 동봉청구해지
    }
    // =========================

    // =========================
    // prdcInfo
    // =========================
    @Data
    public static class PrdcInfo {

        private String prdcCd;              // 상품코드
        private String prdcTypeCd;          // 상품타입
        private String ftrNewParam;         // 상품파람
    }
    // =========================

    // =========================
    // inFrmpapDto
    // =========================
    @Data
    public static class InFrmpapDto {

        private String cntpntCd;            // 접점코드
        private String frmpapId;            // 서식지아이디
    }
    // =========================

}
