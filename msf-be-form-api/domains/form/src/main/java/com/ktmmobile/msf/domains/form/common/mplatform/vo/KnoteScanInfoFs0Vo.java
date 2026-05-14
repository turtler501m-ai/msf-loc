package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KnoteScanInfoFs0Vo {

    String onlineCustTrtSttusChgCd;    //온라인고객처리상태변경코드	* 코드정의서 오더유형 코드(ODER_TYPE_CD) 참조
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
    String cntpntNm; //접점명


}
