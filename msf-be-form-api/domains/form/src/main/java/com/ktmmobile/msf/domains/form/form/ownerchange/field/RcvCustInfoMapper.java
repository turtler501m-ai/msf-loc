package com.ktmmobile.msf.domains.form.form.ownerchange.field;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RcvCustInfoMapper {

    @Mapping(target = "custTypeCd", source = "cstmrTypeCd")                       // 고객유형 (필수)
    // @Mapping(target = "custIdntNoIndCd", source = "custIdntNoIndCd")             // 고객식별구분 (필수)
    @Mapping(target = "custIdntNo", source = "cstmrNativeRrn")                   // 고객식별번호 (필수)
    @Mapping(target = "crprNo", source = "cstmrJuridicalRrn")                    // 법인번호 C
    @Mapping(target = "custNm", source = "cstmrNm")                              // 고객명 (필수)
    // @Mapping(target = "myslAgreYn", source = "myslAgreYn")                       // 본인동의여부 (필수)
    // @Mapping(target = "nativeRlnamAthnEvdnPprCd", source = "nativeRlnamAthnEvdnPprCd") // 실명인증서류 C
    // @Mapping(target = "athnRqstcustCntplcNo", source = "athnRqstcustCntplcNo")   // 인증연락처 (필수)
    @Mapping(target = "rsdcrtIssuDate", source = "teIdentityIssuDate")               // 주민등록증 발급일 C
    @Mapping(target = "lcnsNo", source = "teDriveLicnsNo")                               // 면허번호 C
    @Mapping(target = "lcnsRgnCd", source = "teIdentityIssuRegion")                         // 면허지역 C
    // @Mapping(target = "mrtrPrsnNo", source = "mrtrPrsnNo")                       // 유공자번호 C
    @Mapping(target = "nationalityCd", source = "cstmrForeignerCountryCd")       // 국적코드 C
    @Mapping(target = "fornBrthDate", source = "cstmrForeignerBirth")            // 외국인생년월일 C
    // ===== 동의 정보 =====
    // @Mapping(target = "crdtInfoAgreYn", source = "crdtInfoAgreYn")                      // 신용정보동의여부 (필수)
    // @Mapping(target = "indvInfoInerPrcuseAgreYn", source = "indvInfoInerPrcuseAgreYn") // 개인정보내부활용동의여부 (필수)
    // @Mapping(target = "cnsgInfoAdvrRcvAgreYn", source = "cnsgInfoAdvrRcvAgreYn")        // 위탁정보광고수신동의여부 (필수)
    // @Mapping(target = "othcmpInfoAdvrRcvAgreYn", source = "othcmpInfoAdvrRcvAgreYn")    // 타사정보광고수신동의여부 (필수)
    // @Mapping(target = "othcmpInfoAdvrCnsgAgreYn", source = "othcmpInfoAdvrCnsgAgreYn")  // 타사정보광고위탁동의여부 (필수)
    // @Mapping(target = "grpAgntBindSvcSbscAgreYn", source = "grpAgntBindSvcSbscAgreYn")  // 그룹사결합서비스가입동의여부 (필수)
    // @Mapping(target = "cardInsrPrdcAgreYn", source = "cardInsrPrdcAgreYn")              // 카드보험상품동의여부 (필수)
    // @Mapping(target = "olngDscnHynmtrAgreYn", source = "olngDscnHynmtrAgreYn")          // 주유할인현대자동차동의여부 (필수)
    // @Mapping(target = "wlfrDscnAplyAgreYn", source = "wlfrDscnAplyAgreYn")              // 복지할인신청동의여부 (필수)
    // @Mapping(target = "spamPrvdAgreYn", source = "spamPrvdAgreYn")                      // 스팸제공동의여부 (필수)
    // @Mapping(target = "prttlpStlmUseAgreYn", source = "prttlpStlmUseAgreYn")            // 이동전화결제이용동의여부 (필수)
    // @Mapping(target = "prttlpStlmPwdUseAgreYn", source = "prttlpStlmPwdUseAgreYn")      // 이동전화결제비밀번호이용동의여부 (필수)

    @Mapping(target = "wrlnTlphNo", source = "trnsTrnsfeMobileNo") // 유선전화번호 (필수)
    @Mapping(target = "rprsPrsnNm", source = "cstmrJuridicalRepNm") // 대표자명
    @Mapping(target = "upjnCd", source = "upjnCd") // 업종코드
    @Mapping(target = "bcuSbst", source = "bcuSbst") // 업태내용    (법인과 공공기관 필수)
    @Mapping(target = "zipNo", source = "cstmrZipcd") // 우편번호   (법인과 공공기관 필수)
    @Mapping(target = "fndtCntplcSbst", source = "cstmrAdr") // 기본연락처내용 (법인과 공공기관 필수)
    @Mapping(target = "mntCntplcSbst", source = "cstmrAdrDtl") // 상세연락처내용   (법인과 공공기관 필수)

    @Mapping(target = "brthDate", source = "cstmrNativeBirth") // 생일일자
    // @Mapping(target = "brthNnpIndCd", source = "brthNnpIndCd") // 생일음양구분코드
    // @Mapping(target = "jobCd", source = "jobCd") // 직업코드
    @Mapping(target = "emlAdrsNm", source = "cstmrEmailAdr") // 이메일주소명

    // @Mapping(target = "lstdIndCd", source = "lstdIndCd") // 상장구분코드
    // @Mapping(target = "emplCnt", source = "emplCnt") // 사원수
    // @Mapping(target = "slngAmt", source = "slngAmt") // 매출액
    // @Mapping(target = "cptlAmnt", source = "cptlAmnt") // 자본금액

    // @Mapping(target = "crprUpjnCd", source = "crprUpjnCd") // 법인업종코드
    // @Mapping(target = "crprBcuSbst", source = "crprBcuSbst") // 법인업태내용
    // @Mapping(target = "crprZipNo", source = "crprZipNo") // 법인우편번호
    // @Mapping(target = "crprFndtCntplcSbst", source = "crprFndtCntplcSbst") // 법인기본연락처내용
    // @Mapping(target = "crprMntCntplcSbst", source = "crprMntCntplcSbst") // 법인상세연락처내용

    // @Mapping(target = "custInfoChngYn", source = "custInfoChngYn") // 고객정보변경여부 (필수)

    @Mapping(target = "agntCustNm", source = "minorAgentNm") // 법정대리인 성명
    @Mapping(target = "agntCustIdfyNoType", source = "minorAgentSelfCertTypeCd") // 법정대리인 식별번호 종류
    // @Mapping(target = "agntIdfyNoVal", source = "agntIdfyNoVal") // 법정대리인 고객식별번호
    @Mapping(target = "agntPersonSexDiv", source = "minorAgentGenderCd") // 법정대리인 성별
    @Mapping(target = "agntAgreYn", source = "minorAgentSelfInqryAgrmYn") // 법정대리인 정보조회 동의여부
    // @Mapping(target = "agntTelAthn", source = "agntTelAthn") // 법정대리인 연락처 종류
    @Mapping(target = "agntTelNo", source = "minorAgentTelFnNo") // 대리인 연락처
    // @Mapping(target = "agntTypeCd", source = "agntTypeCd") // 법정대리인 유형
    // @Mapping(target = "agntNationalityCd", source = "agntNationalityCd") // 법정대리인 국적코드
    // @Mapping(target = "agntRsdcrtIssuDate", source = "agntRsdcrtIssuDate") // 법정대리인 식별번호 발급일자
    @Mapping(target = "agntRltnCd", source = "minorAgentRelTypeCd") // 대리인관계코드
    @Mapping(target = "agntBrthDate", source = "minorAgentBirth") // 대리인생일일자
        // @Mapping(target = "agntRlnamAthnEvdnPprCd", source = "agntRlnamAthnEvdnPprCd") // 법정대리인 실명인증 증빙서류코드
        // @Mapping(target = "agntLicnsRgnCd", source = "agntLicnsRgnCd") // 법정대리인 면허지역코드
        // @Mapping(target = "agntLicnsNo", source = "agntLicnsNo") // 법정대리인 면허번호

        // @Mapping(target = "homeTlphNo", source = "homeTlphNo") // 자택전화번호
        // @Mapping(target = "fnncDealAgreeYn", source = "fnncDealAgreeYn") // 금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의
        // @Mapping(target = "indvLoInfoPrvAgreeYn", source = "indvLoInfoPrvAgreeYn") // 개인위치정보제공동의여부
    MplatFormFMC0InfoRequest.RcvCustInfo toRcvCustInfo(MsfRequestNameChgVo source);
}
