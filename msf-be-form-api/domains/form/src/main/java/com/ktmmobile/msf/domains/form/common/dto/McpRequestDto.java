package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;                // 가입신청_키
    private String operType;                // 업무구분
    private String operTypeNm;
    private String cstmrType;              // 고객구분
    private String cstmrTypeNm;
    private String resCode;                 // 예약등록_코드
    private String resMsg;                  // 예약등록_메세지
    private String resNo;                   // 예약등록_예약번호
    private String clausePriCollectFlag;    // 약관_개인정보_수집_동의
    private String clausePriOfferFlag;      // 약관_개인정보_제공_동의
    private String clauseEssCollectFlag;    // 약관_고유식별정보_수집이용제공_동의
    private String clausePriTrustFlag;      // 약관_개인정보_위탁_동의
    private String clausePriAdFlag;         // 약관_개인정보_광고전송_동의
    private String clauseConfidenceFlag;    // 약관_신용정보_이용_동의
    private String clauseJehuFlag;          // 제휴_서비스동의
    private String clauseMpps35Flag;        // M PPS 35 제약사항 동의
    private String clauseFinanceFlag;       // 개인(신용)정보 처리 및 보험가입 동의
    private String onlineAuthType;          // 온라인_인증방식 (C:카드, X:범용, M:모바일, P:서식지)
    private String onlineAuthInfo;          // 온라인_인증정보
    private String pstate;                  // 신청서_상태
    private String requestStateCode;        // 가입진행_코드
    private String openNo;                  // 개통번호
    private String file01;                  // 파일01
    private String file01Mask;              // 파일01_마스크
    private String faxyn;                   // 팩스사용여부
    private String faxnum;                  // 팩스번호
    private String scanId;                  // 스캐너아이디
    private String onOffType;               // 온라인오프라인구분
    private String rip;                     // 등록아이피
    private Date openReqDate;
    private Date sysRdate;                  // 등록일시
    private String reqWantNumber;
    private String reqWantNumber2;
    private String reqWantNumber3;
    private String reqBuyType;
    private String reqModelName;
    private String reqModelColor;
    private String reqPhoneSn;
    private String reqUsimSn;
    private String reqPayType;
    private String reqAddition;
    private String shopCd;
    private String appFormYn;
    private Date reqInDay;                  // 가입신청일
    private String contractNum;
    private String etcSpecial;              // 기타/특약사항
    private String reqUsimName;             // USIM 모델명
    private String phonePayment;            // 휴대폰결제 이용여부
    private long reqAdditionPrice;          // 부가서비스금액
    private String appFormXmlYn;
    private String spcCode;
    private String cntpntShopId;            // 채널점아이디_판매점코드
    private String shopUsmId;
    private String memo;
    private String recYn;                   // 녹취여부
    private String openMarketReferer;
    private String soCd;                    // 사업자코드(I:KTIS, M:M모바일)
    private String nwBlckAgrmYn;            // 네트워크차단동의여부
    private String appBlckAgrmYn;           // 어플리케이션차단동의여부
    private String appCd;                   // APP구분코드
    private String managerCode;             // 매니저_코드
    private String agentCode;               // 대리점_코드
    private String serviceType;             // 서비스구분
    private String prodId;                  // 상품아이디
    private String cretId;                  // 생성자아이디(로그인아이디)
    private String bannerCd;                // 배너코드
    private String sntyColorCd;             // 단말기모델아이디_색상검색용
    private String sntyCapacCd;             // 단품용량코드
    private String prodType;                // 상품 분류 (일반:01, 0원 상품:02)
    private String clauseRentalService;     // 중고렌탈 프로그램 서비스 이용에 대한 동의서
    private String clauseRentalModelCp;     // 단말배상금 안내사항
    private String clauseRentalModelCpPr;   // 단말배상금(부분파손) 안내사항
    private String promotionCd;             // 프로모션코드
    private String prodNm;                  // 상품명
    private String insrCd;                  // DB선택보험
    private String clauseInsuranceFlag;
    private String insrProdCd;              // 단말보험 CD
    private String clauseInsrProdFlag;      // 단말보험가입동의
    private String insrAuthInfo;            // 단말보험인증정보
    private String clause5gCoverageFlag;    // 5g 커버리지 확인 및 가입 동의
    private String ktmReferer;              // 최초유입경 인자값
    private String usimKindsCd;             // 유심종류(RCP2035) 06

}
