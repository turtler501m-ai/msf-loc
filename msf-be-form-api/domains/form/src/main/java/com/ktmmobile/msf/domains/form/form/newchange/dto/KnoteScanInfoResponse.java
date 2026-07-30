package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * KNOTE 신분증 스캔 목록 조회 - 응답
 * 2026.05.
 */

/**
 * KNOTE 신분증 스캔 목록 조회 Response
 */
@Getter
@Setter
@NoArgsConstructor
public class KnoteScanInfoResponse {

    //MSF_REQUEST - 스마트 신규/변경 >> 신분증스캔 정보
    String knoteIdentityScanCstmrNm; //KNOTE신분증고객명 >> Knote 항목 :: custNm
    String knoteIdentityEssNo; //KNOTE신분증식별번호 >> Knote 항목 :: realCustIdntNo
    String knoteIdentityTypeCd; //KNOTE신분증유형코드 >> Knote 항목 :: realEvdnDataInd
    String knoteIdentityScanDt; //KNOTE신분증스캔일시 >> Knote 항목 ::
    String knoteScanId; //KNOTE신분증스캔번호 >> Knote 항목 :: frmpapId
    String frmpapId; //

    String custIdntNoIndCd; //명의자 식별구분코드	"1 : 주민등록번호 4 : 외국인등록번호"
    String custTypeCd; //명의자 고객유형	1 : 개인
    //String nflCustNm; //명의자 고객명		Y
    //String nflCustIdfyNo; //명의자 식별번호		Y
    String custNm; //고객명	서식지 신청고객명	Y
    String realEvdnDataInd; //실명인증 증빙자료구분	* 코드정의서 실명인증증빙 코드(NATIVE_RLNAM_ATHN_EVDN_PPR_CD) 참조
    String realCustIdntNo; //실명인증 식별번호		Y
    String realIssuDate; //실명인증 발급일자	yyyyMMdd
    String opnYn; //개통여부
    String svcApyTrtSttusCd; //처리상태코드	1: 접수, 2: 진행, 3: 완료, 4: 취소
    String svcContId; //서비스계약아이디


    //MSF_REQUEST_CSTMR - 스마트 신규/변경 >> 고객정보
    /*String cstmrNm; //내외국인 성인 고객명
    String cstmrNativeRrn; //내국인 성인 고객식별번호
    String cstmrForeignerRrn; //외국인 성인 고객식별번호
    String cstmrJuridicalUserNm; //법인(또는 공공) 실사용자명
    String cstmrJuridicalBirth; //법인(또는 공공) 실사용자생년월일*/

    //MSF_REQUEST_AGENT - 스마트 신규/변경 >> 법정대리인 정보
    /*String minorAgentNm; //미성년자법정대리인성명
    String minorAgentRrn; //미성년자법정대리인등록번호*/


    //서식지 목록조회 (FS0) 응답
    /*String onlineCustTrtSttusChgCd;    //온라인고객처리상태변경코드	* 코드정의서 오더유형 코드(ODER_TYPE_CD) 참조
    String custIdntNo;    //고객식별번호	생년월일+성별(yyMMdd+성별코드)
    String custNm;        //고객명
    String wapplRegDate;        //신청서등록일시	yyyyMMddHHmmss
    String frmpapId;    //서식지아이디	"서식지아이디 (ex.0x62E50320B59E11EE8A320080C74455C600)"
    String custTypeNm; //고객유형 타입명	1: 개인
    String custIdntNoIndCd; //실명인증 증빙자료구분코드	"1: 주민등록번호 5: 외국인등록번호"
    String apyTypeCd; //신청유형코드	고정값 "Z" (=지류서식지)
    String slsCmpnCd; //사업자코드
    String slsNm; //사업자명
    String svcApyTrtStatCd; //처리상태코드	1: 접수, 2: 진행, 3: 완료, 4: 취소
    String fxdformIngrsPath1Cd; //서식유입경로코드	002 : 스캔
    String cntpntCd; //접점코드
    String mngmAgncId; //관리대리점코드
    String cntpntNm; //접점명*/

    //서식지 상태조회 (FS1) 응답
    //String frmpapId; //서식지아이디	"서식지아이디(ex.0x62E50320B59E11EE8A320080C74455C600)"
    String titl; //제목
    //String mngmAgncId; //관리대리점코드
    String mngmAgncNm; //관리대리점명
    //String onlineCustTrtSttusChgCd; //신청유형코드	* 코드정의서 오더유형 코드(ODER_TYPE_CD) 참조
    //String wapplRegDate; //서식지 등록일시	yyyyMMddHHmmss
    String frmpapRegPathCd; //판매경로코드명	002
    String fxdformIngrsCdNm; //판매경로명	스캔
    String userId; //판매자아이디
    String userNm; //판매자명		Y
    //String cntpntCd; //접점코드
    //String cntpntNm; //접점코드명
    //String custIdntNoIndCd; //명의자 식별구분코드	"1 : 주민등록번호 4 : 외국인등록번호"
    //String custTypeCd; //명의자 고객유형	1 : 개인
    String nflCustNm; //명의자 고객명		Y
    String nflCustIdfyNo; //명의자 식별번호		Y
    //String custNm; //고객명	서식지 신청고객명	Y
    //String realEvdnDataInd; //실명인증 증빙자료구분	* 코드정의서 실명인증증빙 코드(NATIVE_RLNAM_ATHN_EVDN_PPR_CD) 참조
    //String realCustIdntNo; //실명인증 식별번호		Y
    //String realIssuDate; //실명인증 발급일자	yyyyMMdd
    //String opnYn; //개통여부
    //String svcApyTrtSttusCd; //처리상태코드	1: 접수, 2: 진행, 3: 완료, 4: 취소
    //String svcContId; //서비스계약아이디
    String saleCmpnId; //사업자코드
    String photoAthnDecideCd; //사진인증판정코드	"KAIT 결과값으로 성공/스킵/실패를 판정한 결과 SUCC : 진위성공 SKIP : 스킵 FAIL : 실패 사진인증판정코드(photoAthnDecideCd) 성공/스킵인 경우에만 이후 오더처리 진행 가능 * 사진인증판정 실패인 경우에도 사진진위인증 안정화기간인 경우에는 처리 가능합니다."
    String photoAthnSkipCd; //사진인증생략코드	"E : 미처리(개통) - KAIT 전체스킵, 주민등록증 스킵, 운전면허증스킵, 외국인스킵 S : 당일 성공 -당일 동일대리점, 동일신분증, 동일 고객일때 당일 성공 B : 스킵권한자(개통무관권한)"
    String photoAthnErrCd; //사진인증오류코드	"KAIT 응답코드명 : 에러코드 사진인증오류코드 Sheet 참고"
    String photoAthnResltDtlCd; //사진인증결과상세코드	"KAIT 응답코드명 : 결과코드(불가사유) 사진인증결과상세코드 Sheet 참고"
    String fathDecideCd; //안면인증최종결과코드 "SUCC : 성공 - OSST오더 처리 가능 SKIP : 스킵 - OSST 오더 처리 가능 FAIL : 실패 - 안면인증최종결과코드 실패인 경우에도 안정화 기간인 경우에는 처리 가능 WAIT : 안면인증 진행중 - MIS 안면인증 완료통지 대기중. 해당 상태에서는 OSST 업무 처리 불가"
    String fathSkipCd; //안면인증생략코드 "A: MVNO스킵 B: 무관개통권한 권한자 C: 기기변경취소 E: 안면인증 미처리 스킵 R: 재처리 성공 X: 재처리 실패"
    String fathResltCd; //안면인증결과코드	연동규격서 MIS결과 쉬트 참고
    String fathResltMsgSbst; //안면인증결과메시지내용	연동규격서 MIS결과 쉬트 참고

}
