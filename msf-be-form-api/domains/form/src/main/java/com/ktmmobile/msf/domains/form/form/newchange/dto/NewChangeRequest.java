package com.ktmmobile.msf.domains.form.form.newchange.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestRecDto;

/**
 * 신규/변경 작성신청서 select request parameter 정의
 */
@Getter
@Setter
@NoArgsConstructor
public class NewChangeRequest {

    //boolean isSaved; //고객스텝 저장 완료 여부
    //boolean isVerified; //인증 완료 여부
    //String msfRequestKey; //쓰는데가 있나......

    private String showAll; //전체 컬럼 조회용

    private String knoteScanId;
    private String fathTransacId;
    private String operTypeCd;
    private Long newRequestKey; //신청서 복사하기에서 사용함.
    private String tmpStepCd; //신청서 복사하기에서 임시저장 step 값
    private String preCheck; //신청서번호 존재여부 확인을 위한 변수 (구비서류, 안면인식 등에 request_key 미리 생성이슈)
    private String resNo; //예약번호

    //@NotBlank
    private Long requestKey;

    private String tempYn; //임시저장 테이블을 검색할지 원천 테이블을 검색할지 구분
    private String reqBuyTypeCd; //휴대폰인 경우 MM, USIM 인 경우 UU

    private String managerCd; //로그인 사용자
    private String agentCd; //대리점코드
    private String shopCd; //판매점코드

    private String formTypeCd; // 서식지 유형 코드

    //작성완료 시점에 처리할 사항
    private String procCd; //처리결과 : 신청(RQ) , 처리(CP) , 반려(BK) >> 공통코드 그룹아이디 : CL01
    private String proSttusCd; //진행상태코드 : 작성완료(01) , 개통대기(20) , 개통완료 (21) >> 공통코드 그룹아이디 : PSTATE00 ~ 05
    private String sbscProCd; //가입진행코드 : 접수(00) , 접수완료(01) , 개통대리(20) , 개통완료(21) >> 공통코드 그룹아이디 : OPP

    //따로 빼야할수도 있음.
    //개통전사전체크 (기기변경) 이전에 신청서 정보 조회용 (간략버전으로 고객아이디, 서비스계약번호 조회와 특별판매번호 조회를 위한 항목들)
    //private Long requestKey;
    //private String reqBuyTypeCd;
    private String serviceTypeCd;
    private String usimKindsCd;
    private String sprtTypeCd;
    private String modelMonthly;
    private Long enggMnthCnt;
    private String cstmrTypeCd;
    private String cstmrNm;
    private String cstmrNativeRrn;
    private String cstmrJuridicalCname;
    private String cstmrJuridicalRrn;
    private String cstmrForeignerRrn;
    private String openNo;
    private String cstmrTelFnNo;
    private String cstmrTelMnNo;
    private String cstmrTelRnNo;

    //
    private String contractNum; //계약번호

    //녹취저장
    List<MsfRequestRecDto> msfRequestRecList;
    private String scanId; //eForm Sign 에서 전달해주는 document ID
    private String recYn; //녹취여부 - 작성완료 시 처리해야하므로 여기에만 있음.
    private String fileNm;
    private String fileMaskNm;

    private String requestClose; //신청서 작성완료

}
