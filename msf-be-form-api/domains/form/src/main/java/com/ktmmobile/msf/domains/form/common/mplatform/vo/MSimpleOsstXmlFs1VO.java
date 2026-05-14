package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

@Getter
@Setter
@NoArgsConstructor
public class MSimpleOsstXmlFs1VO extends CommonXmlVO {

    String frmpapId; //서식지아이디	"서식지아이디(ex.0x62E50320B59E11EE8A320080C74455C600)"
    String titl; //제목
    String mngmAgncId; //관리대리점코드
    String mngmAgncNm; //관리대리점명
    String onlineCustTrtSttusChgCd; //신청유형코드	* 코드정의서 오더유형 코드(ODER_TYPE_CD) 참조
    String wapplRegDate; //서식지 등록일시	yyyyMMddHHmmss
    String frmpapRegPathCd; //판매경로코드명	002
    String fxdformIngrsCdNm; //판매경로명	스캔
    String userId; //판매자아이디
    String userNm; //판매자명		Y
    String cntpntCd; //접점코드
    String cntpntNm; //접점코드명
    String custIdntNoIndCd; //명의자 식별구분코드	"1 : 주민등록번호 4 : 외국인등록번호"
    String custTypeCd; //명의자 고객유형	1 : 개인
    String nflCustNm; //명의자 고객명		Y
    String nflCustIdfyNo; //명의자 식별번호		Y
    String custNm; //고객명	서식지 신청고객명	Y
    String realEvdnDataInd; //실명인증 증빙자료구분	* 코드정의서 실명인증증빙 코드(NATIVE_RLNAM_ATHN_EVDN_PPR_CD) 참조
    String realCustIdntNo; //실명인증 식별번호		Y
    String realIssuDate; //실명인증 발급일자	yyyyMMdd
    String opnYn; //개통여부
    String svcApyTrtSttusCd; //처리상태코드	1: 접수, 2: 진행, 3: 완료, 4: 취소
    String svcContId; //서비스계약아이디
    String saleCmpnId; //사업자코드
    String photoAthnDecideCd; //사진인증판정코드	"KAIT 결과값으로 성공/스킵/실패를 판정한 결과 SUCC : 진위성공 SKIP : 스킵 FAIL : 실패 사진인증판정코드(photoAthnDecideCd) 성공/스킵인 경우에만 이후 오더처리 진행 가능 * 사진인증판정 실패인 경우에도 사진진위인증 안정화기간인 경우에는 처리 가능합니다."
    String photoAthnSkipCd; //사진인증생략코드	"E : 미처리(개통) - KAIT 전체스킵, 주민등록증 스킵, 운전면허증스킵, 외국인스킵 S : 당일 성공 -당일 동일대리점, 동일신분증, 동일 고객일때 당일 성공 B : 스킵권한자(개통무관권한)"
    String photoAthnErrCd; //사진인증오류코드	"KAIT 응답코드명 : 에러코드 사진인증오류코드 Sheet 참고"
    String photoAthnResltDtlCd; //사진인증결과상세코드	"KAIT 응답코드명 : 결과코드(불가사유) 사진인증결과상세코드 Sheet 참고"
    String fathDecideCd; //안면인증최종결과코드 "SUCC : 성공 - OSST오더 처리 가능 SKIP : 스킵 - OSST 오더 처리 가능 FAIL : 실패 - 안면인증최종결과코드 실패인 경우에도 안정화 기간인 경우에는 처리 가능 WAIT : 안면인증 진행중 - MIS 안면인증 완료통지 대기중. 해당 상태에서는 OSST 업무 처리 불가"
    String fathSkipCd; //안면인증생략코드 "A: MVNO스킵 B: 무관개통권한 권한자 C: 기기변경취소 E: 안면인증 미처리 스킵 R: 재처리 성공 X: 재처리 실패"
    String fathResltCd; //안면인증결과코드	연동규격서 MIS결과 쉬트 참고
    String fathResltMsgSbst; //안면인증결과메시지내용	연동규격서 MIS결과 쉬트 참고

    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.frmpapId = XmlParse.getChildValue(this.body, "frmpapId"); //서식지아이디	"서식지아이디(ex.0x62E50320B59E11EE8A320080C74455C600)"
        this.titl = XmlParse.getChildValue(this.body, "titl"); //제목
        this.mngmAgncId = XmlParse.getChildValue(this.body, "mngmAgncId"); //관리대리점코드
        this.mngmAgncNm = XmlParse.getChildValue(this.body, "mngmAgncNm"); //관리대리점명
        this.onlineCustTrtSttusChgCd = XmlParse.getChildValue(this.body, "onlineCustTrtSttusChgCd"); //신청유형코드	* 코드정의서 오더유형 코드(ODER_TYPE_CD) 참조
        this.wapplRegDate = XmlParse.getChildValue(this.body, "wapplRegDate"); //서식지 등록일시	yyyyMMddHHmmss
        this.frmpapRegPathCd = XmlParse.getChildValue(this.body, "frmpapRegPathCd"); //판매경로코드명	002
        this.fxdformIngrsCdNm = XmlParse.getChildValue(this.body, "fxdformIngrsCdNm"); //판매경로명	스캔
        this.userId = XmlParse.getChildValue(this.body, "userId"); //판매자아이디
        this.userNm = XmlParse.getChildValue(this.body, "userNm"); //판매자명
        this.cntpntCd = XmlParse.getChildValue(this.body, "cntpntCd"); //접점코드
        this.cntpntNm = XmlParse.getChildValue(this.body, "cntpntNm"); //접점코드명
        this.custIdntNoIndCd = XmlParse.getChildValue(this.body, "custIdntNoIndCd"); //명의자 식별구분코드	"1 : 주민등록번호 4 : 외국인등록번호"
        this.custTypeCd = XmlParse.getChildValue(this.body, "custTypeCd"); //명의자 고객유형	1 : 개인
        this.nflCustNm = XmlParse.getChildValue(this.body, "nflCustNm"); //명의자 고객명		Y
        this.nflCustIdfyNo = XmlParse.getChildValue(this.body, "nflCustIdfyNo"); //명의자 식별번호		Y
        this.custNm = XmlParse.getChildValue(this.body, "custNm"); //고객명	서식지 신청고객명	Y
        this.realEvdnDataInd = XmlParse.getChildValue(this.body, "realEvdnDataInd"); //실명인증 증빙자료구분	* 코드정의서 실명인증증빙 코드(NATIVE_RLNAM_ATHN_EVDN_PPR_CD) 참조
        this.realCustIdntNo = XmlParse.getChildValue(this.body, "realCustIdntNo"); //실명인증 식별번호		Y
        this.realIssuDate = XmlParse.getChildValue(this.body, "realIssuDate"); //실명인증 발급일자	yyyyMMdd
        this.opnYn = XmlParse.getChildValue(this.body, "opnYn"); //개통여부
        this.svcApyTrtSttusCd = XmlParse.getChildValue(this.body, "svcApyTrtSttusCd"); //처리상태코드	1: 접수, 2: 진행, 3: 완료, 4: 취소
        this.svcContId = XmlParse.getChildValue(this.body, "svcContId"); //서비스계약아이디
        this.saleCmpnId = XmlParse.getChildValue(this.body, "saleCmpnId"); //사업자코드
        this.photoAthnDecideCd = XmlParse.getChildValue(this.body,
            "photoAthnDecideCd"); //사진인증판정코드	"KAIT 결과값으로 성공/스킵/실패를 판정한 결과 SUCC : 진위성공 SKIP : 스킵 FAIL : 실패 사진인증판정코드(photoAthnDecideCd) 성공/스킵인 경우에만 이후 오더처리 진행 가능 * 사진인증판정 실패인 경우에도 사진진위인증 안정화기간인 경우에는 처리 가능합니다."
        this.photoAthnSkipCd = XmlParse.getChildValue(this.body,
            "photoAthnSkipCd"); //사진인증생략코드	"E : 미처리(개통) - KAIT 전체스킵, 주민등록증 스킵, 운전면허증스킵, 외국인스킵 S : 당일 성공 -당일 동일대리점, 동일신분증, 동일 고객일때 당일 성공 B : 스킵권한자(개통무관권한)"
        this.photoAthnErrCd = XmlParse.getChildValue(this.body, "photoAthnErrCd"); //사진인증오류코드	"KAIT 응답코드명 : 에러코드 사진인증오류코드 Sheet 참고"
        this.photoAthnResltDtlCd = XmlParse.getChildValue(this.body,
            "photoAthnResltDtlCd"); //사진인증결과상세코드	"KAIT 응답코드명 : 결과코드(불가사유) 사진인증결과상세코드 Sheet 참고"
        this.fathDecideCd = XmlParse.getChildValue(this.body,
            "fathDecideCd"); //안면인증최종결과코드 "SUCC : 성공 - OSST오더 처리 가능 SKIP : 스킵 - OSST 오더 처리 가능 FAIL : 실패 - 안면인증최종결과코드 실패인 경우에도 안정화 기간인 경우에는 처리 가능 WAIT : 안면인증 진행중 - MIS 안면인증 완료통지 대기중. 해당 상태에서는 OSST 업무 처리 불가"
        this.fathSkipCd = XmlParse.getChildValue(this.body,
            "fathSkipCd"); //안면인증생략코드 "A: MVNO스킵 B: 무관개통권한 권한자 C: 기기변경취소 E: 안면인증 미처리 스킵 R: 재처리 성공 X: 재처리 실패"
        this.fathResltCd = XmlParse.getChildValue(this.body, "fathResltCd"); //안면인증결과코드	연동규격서 MIS결과 쉬트 참고
        this.fathResltMsgSbst = XmlParse.getChildValue(this.body, "fathResltMsgSbst"); //안면인증결과메시지내용	연동규격서 MIS결과 쉬트 참고
    }


}
