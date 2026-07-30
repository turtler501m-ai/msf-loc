package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreCheckRequest {

    //String tmpStepCd; //신청서 복사하기에서 임시저장 step 값
    //String preCheck; //신청서번호 존재여부 확인을 위한 변수 (구비서류, 안면인식 등에 request_key 미리 생성이슈)

    private String resNo; //예약번호

    private Long requestKey; //신청서 일련번호

    private String tempYn; //임시저장 테이블을 검색할지 원천 테이블을 검색할지 구분
    private String reqBuyTypeCd; //휴대폰인 경우 MM, USIM 인 경우 UU

    private String operTypeCd; //업무유형코드 (NAC3, MNP3, HDN3)
    private String preCheckTypeCd;//개통요청유형 (PC0/FPC0/HC0/FHC0)
    private String knoteScanId; //Knote Scan ID 정보
    private String fathTransacId; //FATH_TRANSAC_ID

    //특별판매정책번호 추출을 위한 파라미터
    //private String reqBuyTypeCd; //구매유형 (휴대폰 MM , 유심 UU)
    //private String operTypeCd; //업무유형코드 (NAC3, MNP3, HDN3)
    private String serviceTypeCd; //서비스 유형코드 PO (후불) , PP (선불) >> 스마트는 후불기준으로만 진행하는 프로젝트 (추후 추가될 수도 있겠죠)
    private String usimKindsCd; //유심종류
    private String sprtTypeCd; //지원금 유형코드 (KD, PM, SM)
    private String modelMonthly; //단말할부개월수
    private Long enggMnthCnt; //약정개월수
    private String plcyType; //정책코드? PLCY_TYPE

    private String customerType; //고객유형
    private String customerName; //고객명
    private String customerRrn; //고객식별번호
    private String customerTelNo; //고객핸드폰번호
    private String appAgncCd; //kt대리점코드

    //String managerCd; //로그인 사용자
    //String agentCd; //대리점코드
    //String shopCd; //판매점코드

    //String procCd; //
}
