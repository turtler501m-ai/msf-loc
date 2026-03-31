# MVNO-OSST-API 연동규격서

**문서명** : M-Platform에서 MVNO사업자 자료제공  
**버전** : v4.48  
**방향** : MVNO ↔ KT M-Platform

---

## 1. 개요

### 1.1 목적

본 문서는 KT와 제휴한 MVNO사업자 영업전산 시스템의 업무처리를 위한 연동 I/F 세부내역을 기술합니다.  
본 문서에 기술된 내용은 KT영업전산에 수용된 단순MVNO사업자를 대상으로 합니다.

### 1.2 범위

본 문서는 KT영업전산에 수용된 MVNO업무 중 신규 개통관련 업무, 지능망직권해지 업무에 한정됩니다.

### 1.3 제약사항

1. OSST API를 통한 신규개통/번호이동 개통 시 미성년자 개통은 불가
2. OSST API를 통한 번호이동 개통 처리 시 번호이동수수료 및 타사미청구금액 카드즉납이 불가
3. OSST API를 통한 기기변경은 우수기변, 일반기변 만 가능

### 1.4 MVNO사업자와 MNO(KT)의 기능 R&R

| 구분 | 기능 | 관련업무 | MVNO | MNO(KT) |
|---|---|---|---|---|
| 연동 | 사전 체크 및 고객 생성 | 개통 가능 여부 사전 체크 | 연동전문 발송 | 대리점 별, 고객 별 개통 가능 여부 체크 및 고객 미 존재시 고객 생성 |
|  | 개통 및 수납 | 개통 | 연동전문 발송 | 수납 및 개통 |
|  | 개통 오더 처리 결과 연동 | 개통 오더 처리 결과 연동 | 개통 오더 처리 결과 수신 | 사전체크 및 고객생성 & 개통 및 수납 작업 처리 결과 연동 |
|  | 접수 후 진행 상태 조회 | 오더 진행 상태 조회 | 연동전문 발송 | 사전체크 및 고객생성 & 개통 및 수납, 지능망직권해지 작업 진행 상태 조회 |
|  | 지능망직권해지 접수 및 처리 | 지능망직권해지 가능 상태 조회 및 직권해지 | 연동전문 발송 | 정지 상태 선불고객의 지능망직권해지 처리 |
|  | 해지 접수 및 처리 | 해지 가능 상태 조회 및 해지 | 연동전문 발송 | 일반 계약 고객의 해지 처리 |
|  | 해지 오더 처리 결과 연동 | 해지 오더 처리 결과 연동 | 해지 오더 처리 결과 수신 | 해지 & 지능망직권해지 작업 처리 결과 연동 |
| 기준정보 | 번호자원 | 개통 | 연동전문에 의한 번호예약 및 개통업무 수행 | 번호자원관리주체 |

### 1.5 KT M-Platform 연동 환경

| 서비스 | 테스트 환경 IP | 테스트 Port | 운영 IP | 운영 Port |
|---|---|---|---|---|
| Web Service | 10.217.38.131 | 7006 | 10.220.172.141 | 7006 |
| TCP | — | — | — | — |

### 1.6 Protocol 별 연동 표준

| Protocol | 항목 | 표준 | 상세설명 |
|---|---|---|---|
| Web Service | Protocol | https | 고객정보보안을 위하여 https로 연동 |
| | Encoding | UTF-8 | ko_KR.UTF-8 |
| | Message | SOAP | WSDL을 통한 Web Service Interface 지원: SOAP 1.1 |
| TCP/IP | Transaction | Sync | Request / Response를 실시간으로 연동 |
| | Encoding | KSC5601 | ko_KR.KSC5601 |
| | Record Length | Fixed | 고정형이 아닌 경우는 개별 서비스의 별도 기준에 따름 |
| | Column Padding | Space | Char: 우측 공백 처리, Number: 좌측 공백 처리 |

---

## 2. 변경 이력

| No | 변경일자 | 변경대상 | 변경내용 | 변경자 | 비고 |
|---|---|---|---|---|---|
| 1 | 2017-08-17 | 최초작성 | 초안 작성 | 이영효 |  |
| 2 | 2018-01-26 | 개통및수납(OP0) | 청구서양식코드(rqsshtPprfrmCd): 모바일MMS(MB) 코드 추가,   청구서전화번호(rqsshtTlphNo) 필드값 추가 | 이상호 | v2.1 DR-2018-02717 [MVNO] 청구서 발송형태 모바일(MMS) 추가 |
| 3 | 2018-04-05 | 개통및수납(OP0) | 청구서전화번호(rqsshtTlphNo) 내용 수정 | 이상호 | v2.2 2018년 M-Platform 개선 과제 |
| 4 | 2018-04-17 | 사전체크 및 고객생성(PC0) 개통및수납(OP0) 번호이동 사전동의 요청(NP1) 번호이동 납부주장 요청(NP2) 개통처리결과 | 번호이동 개통을 위한 기존 연동 수정 및 신규 연동 추가 | 정소희 | MVNE플랫폼 고도화 프로젝트 인수 |
| 5 | 2018-05-08 | 사전체크, 개통및 수납 | 효성 개통 프로세스를 위한 input ATM_SEQ_NO(atm 일련번호) 추가 | 최현규 | DR-2018-16725 노틸러스효성 ATM - M플랫폼 연동을 위한 개발 요청 |
| 6 | 2018-05-09 | 사전동의 결과조회(NP3) | 번호이동 사전동의 요청에 대한 결과 조회 신규 연동 추가 | 이현하 | DR-2018-25799 개통간소화 번호이동 사전동의 요청 결과 처리 |
| 7 | 2018-05-29 | ET0, ET1 신규 서비스 추가 | 효성 개통 프로세스를 위하여 업무시간외 개통접수건 사업자 전송(ET0), 휴일여부 조회(ET1) 서비스를 추가 | 최현규 | DR-2018-25800   효성 ATM 개통 접수 기능 수용 위한 설계 및 개발 |
| 8 | 2018-07-02 | 개통및수납(OP0) | 번호이동단말기분납지속구분코드 상세설명 기재 타사 단말할부금 = 0  후불 -> 선불 일때, "2"(분납지속 LMS미청구)만 가능  ESLE,      "1"(완납) 만 가능  타사 단말할부금 > 0  후불 -> 후불 일때, 선택가능(1,2,3)  ELSE,     "2"(분납지속 LMS미청구)만 가능 | 최현규 |  |
| 9 | 2018-07-02 | 기기변경(HC0) 기기변경취소(HP0) | 기기변경/기기변경 취소 연동규격 추가 | 이영효 | MVNE플랫폼 고도화 프로젝트 인수 |
| 10 | 2019-02-22 | 해지(EP01) | 해지 API 삭제 | 이영효 | MVNO사업팀 이영세차장 요청 건 |
| 11 | 2019-08-22 | 해지(EP01) | 해지 API 복구 | 이영효 |  |
| 12 | 2019-09-27 | 해지(EP0), M2M 해지(EP0) | (1)M2M 해지(MP0) -> M2M 해지(EP0) 변경 (2)smsRcvYn(SMS수신여부) M(필수값) -> O 변경 | 이영효 | v2.8 DR-2019-38531 [MVNO] 개통간소화 해지 기능 활성화 |
| 13 | 2019-10-18 | 일시정지가능여부조회(X27) 일시정지해제가능여부조회(X28) 부정사용 일시정지신청(X65) 부정사용 일시정지해제신청(X66) | 신규 API 추가 | 이상호 | v2.9 DR-2019-49429 [MVNO] [한국케이블텔레콤] 부정사용에 의한 이용정지해제 업무(코드) 개통간소화셀프케어 수용 개발 |
| 14 | 2020-10-27 | 기기변경/기기변경취소 처리(HP0) 기기변경사전체크/저장 결과 수신 | 약정유예 여부 컬럼 추가 및 TCP 결과 처리 메시지 추가 | 이상호 | v3.0 DR-2020-53348 [MVNO] [에이씨엔코리아_유한(ACN)] OSST 약정유예 개발 요청 |
| 15 | 2021-02-08 | 사전체크(PC0)  개통및수납(OP0) | 미성년자 개통 관련 연동 항목 추가 | 유지현 | v3.1 DR-2020-70448 [MVSE]미성년자 셀프개통 기능 개발 |
| 16 | 2022-02-09 | 유심변경(UC0) | 신규 API 추가 | 최현규 | 셀프개통 고도화 프로젝트 |
| 17 | 2022-04-14 | 명의변경 개통처리결과(신규) | 명의변경 관련 sheet 3개 추가 개통처리결과(신규) sheet 추가 | 최현규 | 셀프개통 고도화 프로젝트 |
| 18 | 2022-06-28 | 개통및수납(OP0) 개통처리결과(기존_듀얼단말) 개통처리결과(신규_듀얼단말) | eSIM단말 처리관련 항목 추가 개통처리결과(듀얼단말) sheet 2개 추가 |  | eSIM인프라구축(MVNO) 프로젝트 |
| 19 | 2022-09-30 | 기기변경/기기변경취소 사전체크(HC0) 해지취소 사전체크(RC0) 해지취소 처리(RP0) 명의변경사전체크(MC0) 명의변경(MP0) 유심변경(UC0) | eSIM단말 처리관련 일부 항목정의 변경 |  | v3.8 eSIM인프라구축(MVNO) 프로젝트 |
| 20 | 2022-11-03 | OSST개통서식지등록(OF) OSST개통서식지파일연동 | OSST개통서식지등록(OF) - 접근구분코드 항목 추가 OSST개통서식지파일연동 - 접근구분코드 항목 추가 및 대고객 통합 채널 파일추가 | 최현규 | v3.9 DR-2022-54679 [MVNO] 마이알뜰폰 셀프개통 서식지 자료 생성 개발 요청 |
| 21 | 2022-12-05 | OSST개통서식지등록(OF) OSST개통서식지파일연동 | OSST개통서식지등록(OF) - SIM구분코드 항목 추가 OSST개통서식지파일연동 - SIM구분코드 항목 추가 | 김효순 | v4.0 DR-2022-63893 [MVNO] 마이알뜰폰 셀프개통 서식지 자료 수정 요청의 건 |
| 22 | 2023-03-05 | OSST개통서식지등록(OF) OSST개통서식지파일연동 | OSST개통서식지등록(OF) - 모회선전화번호, 모회선청구서양식코드추가 OSST개통서식지파일연동 - 모회선전화번호, 모회선청구서양식코드추가 | 최현규 | v4.1 개선과제 왓치 개통 서식지 관련 항목추가 |
| 23 | 2023-04-12 | OSST개통서식지등록(OF) OSST개통서식지파일연동 | OSST개통서식지등록(OF) - 사용자 IP 항목 추가 OSST개통서식지파일연동 - 사용자 IP 항목 추가 | 황유성 | v4.2 개선과제 사용자IP 관련 항목 추가 |
| 24 | 2023-03-05 | 문서 전체 | 규격서 템플릿 통일 | 이영효 | v4.23 |
| 25 | 2023-08-04 | 기기변경사전체크(HC0) 개통(OP0) OSST개통서식지등록(OF) | 기기변경/취소 사전체크(HC0) - 기기일련번호 암복호화 필드 정의해제 기기변경(HP0) - 가입내역서수신이메일주소명 암복호화 정의 개통(OP0) - 가입내역서수신이메일주소명 암복호화 정의 OSST개통서식지등록(OF) - 카카오인증서(04), 네이버인증서(05) 추가                                   접근구분코드 필드값 옵션에서 필수값으로 변경 번호조회(NU1) - 주민번호 필드 미사용으로 암호화 여부에서 제외 | 황유성 | v4.24 |
| 26 | 2023-08-28 | 번호이동개통(OP0) | AS-IS                            TO-BE npCcaardOwnrIdfyNo => npCcardOwnrIdfyNo |  |  |
| 27 | 2023-12-07 | 사전체크 및 고객생성(PC0) | 과기부 고시개정으로 24년 1월1일 이후 인증항목 삭제 예정. athnItemCd 인증항목코드  (번호이동시 필수 -> 삭제) athnSbst 인증항목값 | 김효순 | DR-2023-49665 과기부 유무선 번호 이동성 고시개정 관련 고객정보 및 번호이동 인증항목 변경  v4.26 |
| 28 | 2024-01-17 | 문서 전체 | API 연동규격 비고 문구 일부 수정 | 이영효 | v4.27 |
| 29 | 2024-01-24 | 서식지 조회 서식지 목록조회 서식지 상태변경 서식지 개통사전체크 서식지 개통요청 서식지 번호이동 사전체크 서식지 번호이동 요청 | 지류 서식지 신규개통, 번호이동을 위한 신규 API 제공 | 황유성 | v4.28 |
| 30 | 2024-03-06 | 개통&번호이동사전체크(PC0) 기기변경사전체크(HC0) | PC0/HC0 본인인증여부,아이핀CI,본인인증수단 INPUT필드 추가 | 김효순 | DR-2024-07224 [MVNO] 셀프개통(개통&번호이동) 전문 추가(CI) 및 처리 로직 개선 개발 요청의 건 v4.29 |
| 31 | 2024-03-06 | 미납에의한 직권해지(AP0) | 신규 API 추가 | 김효순 | DR-2024-00508 [MVNO] 데이터쉐어링&데이터투게더 모회선 해지(직권해지 포함) 처리 개선 개발 요청의 건 v4.29 |
| 32 | 2024-04-04 | 명의변경사전체크(MC0) | MC0 본인인증여부,아이핀CI,본인인증수단 INPUT필드 추가 | 김효순 | DR-2024-12299 [MVNO] 알뜰폰 신분증스캔 기능 구축관련 온라인서식지 개선 개발 요청의 건(규격 항목 추가 및 CI값 검증 기능) v4.30 |
| 33 | 2024-04-04 | 서식지 사전체크 및 고객생성(신규, 번호이동) (FPC0) 서식지 개통요청(신규,번호이동) (FOP0) | frmpapId (서식지아이디) 필수 -> 선택적 필수로 변경 K-NOTE 장애 이슈 외에는 필수 | 김효순 | DR-2024-12753 [MVNE플랫폼] MVNO 판매점 스캔 및 지류서식지 정보처리 v4.30 |
| 34 | 2024-04-04 | 무서식지대상조회 (FS3) 무서식지 후첨부처리 (FS4) | 신규 API 추가 | 김효순 | DR-2024-12753 [MVNE플랫폼] MVNO 판매점 스캔 및 지류서식지 정보처리 v4.30 |
| 35 | 2024-04-04 | 온라인서식지 등록처리(FS5) | 신규 API 추가 | 황유성 | DR_2024-20494 [MVNO] 온라인서식지 개통관련 KOS에서 MP로 기능이관을 위한 개발 요청의 건 v4.30 |
| 36 | 2024-04-04 | 서식지 우수기변 사전체크(FHC0) 서식지 우수기변 처리(FHP0) 서식지 명의변경 사전체크(FMC0) 서식지 명의변경 처리(FMP0) | 신규 API 추가 | 황우진 | DR-2024-12754 [MVNE플랫폼] MVNO Order 처리로직 변경개발 |
| 37 | 2024-05-01 | OSST개통서식지등록(OF) OSST개통서식지파일연동 | OSST개통서식지등록(OF) -가입인증이동전화번호/가입시연락처 항목 추가, 본인인증수단 코드 변경 OSST개통서식지파일연동 - 가입인증이동전화번호/가입시연락처 파일추가, 본인인증수단 코드 변경 | 김효순 | DR-2024-21619 제휴채널 개통서식지 등록 API 추가 필드 수록 및 파일 생성 요청의 건 v.4.31 |
| 38 | 2024-06-03 | 부정사용 일시정지신청(X65) | 스팸메모코드 허용값 추가 | 남윤지 | v4.32 DR-2024-32730 [MVNO] 이용정지 코드 추가 반영 개선 개발 요청의 건(SKY 요청사항) |
| 39 | 2024-06-21 | 사전체크 및 고객생성 신규,번호이동(PC0) 명의변경사전체크(MC0) 서식지 사전체크 및 고객생성(신규, 번호이동) (FPC0) 서식지 명의변경 사전체크(FMC0) | 법정대리인 실명인증증빙서류코드 / 법정대리인 면허지역코드 / 법정대리인 면허번호 항목추가 법정대리인 식별번호 발급일자 코멘트 추가 누락항목 재 기입: 법정대리인 국적코드 | 김효순 | v4.32 |
| 40 | 2024-06-21 | 온라인서식지 등록처리(FS5) | APLSHT_RGST_DATE (신청서등록일자) 옵션 -> 필수로 변경 (현재 일자만 입력 가능) CNTPNT_SHOP_ID (접점코드) 옵션 -> 필수로 변경 (자릿수 10자리) NOW_CUST_TLPH_NO (현재가입사업자코드) 옵션 -> 조건부 필수로 변경 (자릿수 3자리) | 주효원 | v4.33 |
| 41 | 2024-07-16 | 온라인서식지 CI 조회 | 신규 API 추가 | 황우진 | v4.34 |
| 42 | 2024-06-24 | 기타서식지 목록조회(EFS0) 기타첨부(EFS1) 기타첨부 결과조회(EFS2) 기타첨부 해지(EFS3) | 신규 API 추가 | 황유성 | v4.35 |
| 43 | 2024-08-07 | 사전체크 및 고객생성(PC0) 서식지 사전체크(신규,번호이동)(FPC0) 명의변경 사전체크(MC0) 서식지 명의변경 사전체크(FMC0) | fnncDealAgreeYn 항목 추가 | 황우진 | v4.36 |
| 44 | 2024-09-02 | 사전체크 및 고객생성(PC0) 서식지 사전체크(신규,번호이동)(FPC0) 명의변경 사전체크(MC0) 서식지 명의변경 사전체크(FMC0) | rqsshtPprfrmCd 청구서 발송형태 코드 LC 삭제 | 최현규 | v4.37 |
| 45 | 2024-08-07 | 사전체크 및 고객생성(PC0) 명의변경 사전체크(MC0) 기기변경/기기변경취소 사전체크(HC0) 서식지 상태조회(FS1) KAIT 사진진위확인 요청(FS7) | photoAthnTxnSeq 항목 추가(PC0, MC0, HC0) photoAthnDecideCd 등 4개 항목 추가(FS1) 신규 API 추가(FS7) | 황우진 | v4.38 |
| 46 | 2025-02-13 | KAIT 사진진위확인 요청(FS7) | retryPhotoAthnTxnSeq 항목 추가 | 황우진 | v4.39 |
| 46 | 2025-02-13 | 개통및수납(OP0) 명의변경사전체크(MC0) | blpymCustIdntNo 납부고객식별번호 필드 상세 설명 추가 납부고객 유형이 개인인 경우 생년월일(yyyyMMdd) 입력 법인이나 공공기관의 경우 사업자번호 입력 | 황유성 | v4.39 |
| 47 | 2025-03-10 | 사전체크 및 고객생성(PC0) 명의변경 사전체크(MC0) 기기변경/기기변경취소 사전체크(HC0) 서식지 사전체크(신규,번호이동)(FPC0) 서식지 기기변경 사전체크(FHC0) 서식지 명의변경 사전체크(FMC0) | 실명인증증빙서류코드에 주민등록증 발급신청서(REGAP) 설명 추가 | 황우진 | v4.40 |
| 48 | 2025-06-12 | 개통및수납(OP0) 서식지 개통및수납(FOP0) | 법인 개통 시 agntRltnCd 대리인관계코드의 일회성대리인(06) 코드 입력 불가하도록 변경 | 남윤지 | v4.41 DR-2025-30017 모바일 개통시 법인대리인 관리 기능 개발 |
| 49 | 2025-07-01 | 개통및수납(OP0) 명의변경 사전체크(MC0) 서식지 개통및수납(FOP0) 서식지 명의변경 사전체크(FMC0) | 납기일자구분코드 허용 코드 변경 납부방법코드(blpymMthdCd)가 계좌자동이체(D)인 경우 납기일자구분코드(duedatDateIndCd) 허용 코드 수정 | 김민수 | v4.42 DR-2025-34766 [MVNO] MP 납기일 변경 개선 개발 요청의 건 |
| 50 | 2025-08-06 | 사전체크 및 고객생성(PC0) 명의변경 사전체크(MC0) 서식지 사전체크(신규,번호이동)(FPC0) 서식지 명의변경 사전체크(FMC0) 온라인서식지 등록(FS5) | 개인정보 활용동의 4개 항목 삭제(PC0, MC0, FPC0, FMC0) 개인정보 활용동의 7개 항목 추가/5개 항목 삭제(FS5) | 남윤지 | v4.43 DR-2025-34416 [MVNO] 개인정보제공동의 항목 현행화용 신규 전문&온라인서식지 등록 전문내 항목 추가 개발 요청의 건(KIS 요청) |
| 51 | 2025-09-26 | 사전체크 및 고객생성(PC0) | dlvrSeqNo 항목 추가 | 남윤지 | v4.44 DR-2025-46368 배송형 개통 신규 프로세스 구축 |
| 52 | 2025-10-31 | 유심무상교체 접수 가능 여부조회(T01) 유심무상교체 접수(T02) | 신규 API 추가 | 황유성 | v4.45 DR-2025-59710 [MVNO] 유심교체를 위한 프로세스 구축(신청, 대상자 등) |
| 53 | 2025-12-02 | 고객 안면인증 대상 여부 조회(FT0) 고객 안면인증 URL 요청(FS8) 고객 안면인증 내역 조회(FS9) 안면인증 완료 통지 PUSH 사전체크 및 고객생성(PC0) 우수기변 사전체크(HC0) 명의변경 사전체크(MC0) 개통서식지등록(OF) OSST개통서식지파일연동 온라인 서식지 등록(FS5) 고객 안면인증 대상 여부 조회(FT0) 고객 안면인증 URL 요청(FS8) | 신규 API (FT0, FS8, FS9) 및 안면인증 완료 통지 PUSH 추가 OSST 오더 사전체크 INPUT fathTransacId(안면인증트랜잭션아이디)값 추가 (PC0, MC0, HC0) OSST개통서식지등록(OF) 및 OSST개통서식지파일연동 안면인증 항목 추가 온라인서식지(FS5) INPUT fathTransacId(안면인증트랜잭션아이디)값 추가 | 김미현 | v4.46 DR-2025-52749 부정가입 방지를 위한 안면인증 프로세스 도입 |
| 54 | 2025-12-30 | OSST개통서식지등록(OF) 고객 안면인증 내역 조회(FS9) | 신분증 사진 이미지, 신분증 사본 이미지 삭제 | 남윤지 | v4.47 MVNO채널 개통 건에 대한 안면인증 이미지 파일 전송 기능 구현 |
| 55 | 2026-01-07 | 온라인 서식지 등록(FS5) 사전체크 및 고객생성(PC0) 우수기변 사전체크(HC0) 명의변경 사전체크(MC0) 고객 안면인증 대상 여부 조회(FT0) 고객 안면인증 URL 요청(FS8) 고객 안면인증 내역 조회(FS9) | 1. 온라인 서식지 등록(FS5) INPUT 컬럼  - M2M_HNDSET_YN(M2M단말여부), FATH_RGLS_ENV_TEST_YN(안면인증상용환경테스트여부 추가  2. OSST 오더 업무 사전체크(PC0/HC0/MC0) INPUT 컬럼 추가 - cpntId(접점아이디)  3. OSST개통서식지등록(OF) INPUT 필드 제거 -  idcardPhotoImg(신분증사진), idcardCopiesImg(신분증 사본)  4. 고객 안면인증 내역 조회(FS9) OUPUT 필드 제거  - idcardPhotoImg(신분증사진), idcardCopiesImg(신분증 사본)  5. 안면인증대상여부조회(FT0) INPUT 컬럼 추가 - cpntId(접점아이디) - fathRglsEnvTestYn(안면인증상용환경테스트여부)  6. 고객 안면인증 URL 요청(FS8)  INPUT 컬럼 추가 - fathRglsEnvTestYn(안면인증상용환경테스트여부) | 김미현 | v4.47 DR-2026-01412 [MVNO] (MP&KOS-GW)온라인 서식지 접수 받을때 m2m컬럼 추가해서 m2m인 경우는 안면인증 없이 접수처리 개선 개발 요청  DR-2026-01302 [MVNO] 접점코드 기준 안면인증 시행미시행 관리  DR-2026-01301 [MVNO] MVNO 비대면 개통 안면인증 프로세스 적용 전 PRD환경 검증 프로세스 개발 |
| 56 | 2026-01-30 | 사전체크 및 고객생성(PC0) 서식지 사전체크(신규,번호이동)(FPC0) 명의변경 사전체크(MC0) 서식지 명의변경 사전체크(FMC0) 온라인 서식지 등록(FS5) | 1. PC0, FPC0, MC0, FMC0 INPUT 컬럼 추가 - indvLoInfoPrvAgreeYn  2. FS5 INPUT 컬럼 추가 - INDV_LO_INFO_PRV_AGREE_YN | 김민수 | v.4.48 DR-2025-67502 [엠모바일/12월] 우선순위0 |
| 57 | 2026-01-30 | 고객 안면인증 내역 조회(FS9) | 1. OUTPUT 컬럼 추가 - fathExpiredYn(안면인증만료여부), fathEfctDayNum(안면인증유효일수), fathExpiredDate(안면인증만료일자), ontmUrlEfctDt(일회성URL유효일시)  2. MIS결과코드 현행화  3. 안면인증 대상여부 조회(FT0) & 안면인증 URL요청(FS8) OUTPUT trtResltCd(처리결과코드) 내용 추가 - CD04 추가  4. 안면인증 SKIP API 추가(FT1) | 김미현 | v.4.48 DR-2026-03259 [2단계] 부정가입 방지를 위한 안면인증 프로세스 도입 배포용SR |

---

## 3. 연동 시나리오

### 3.1 공통 필수항목

모든 연동메시지에 공통적으로 적용되는 필수 항목입니다.

| 항목 | Valid-Value | 상세설명 |
|---|---|---|
| 송신일 | YYYYMMDDHH24MISS | MVNO사업자가 KT M-Platform에 메시지를 송신한 년월일시분초 |
| 사업자코드 | 문자 3자리 | KTOA에서 부여한 3자리 사업자식별코드 |
| 대리점코드 | 문자 6자리 | KT에서 사업자에게 유일하게 부여한 대리점코드 |
| appEventCd | 문자 3자리 | 각 API의 Event Code (ex. PC0, NU1 등) |

### 3.2 신규 개통 시나리오

```
MVNO                    KT M-Platform
  |                          |
  |-- PC0 사전체크/고객생성 -->|
  |<-- 응답(osstOrdNo 등) -----|
  |                          |
  |-- NU1 번호조회 ---------->|
  |<-- 사용가능 번호목록 -------|
  |                          |
  |-- NU2 번호예약 ---------->|
  |<-- 예약확인 --------------|
  |                          |
  |-- OP0 개통및수납 -------->|
  |<-- 접수결과 --------------|
  |                          |
  |<-- 개통처리결과(TCP) ------|  (비동기 결과 수신)
```

### 3.3 번호이동 개통 시나리오

```
MVNO                    KT M-Platform
  |-- NP1 사전동의 요청 ----->|
  |<-- 응답 -----------------|
  |                          |
  |-- NP3 사전동의 결과조회 -->|  (필요시)
  |<-- 결과 -----------------|
  |                          |
  |-- NP2 납부주장 요청 ----->|  (미납 고객인 경우)
  |<-- 응답 -----------------|
  |                          |
  |-- PC0 사전체크/고객생성 -->|  (osstNpPrePrc)
  |<-- 응답 -----------------|
  |                          |
  |-- NU1 번호조회 ---------->|
  |-- NU2 번호예약 ---------->|
  |                          |
  |-- OP0 개통및수납 -------->|  (osstNpOpenPrc)
  |<-- 접수결과 --------------|
  |<-- 개통처리결과(TCP) ------|
```

### 3.4 기기변경(HCN) 시나리오

```
MVNO                    KT M-Platform
  |-- HC0 기기변경 사전체크 -->|
  |<-- 응답(osstOrdNo 등) -----|
  |                          |
  |-- NU2 번호예약 ---------->|  (번호변경 시)
  |                          |
  |-- HP0 기기변경 처리 ----->|
  |<-- 접수결과 --------------|
  |<-- 기변결과(TCP) ---------|
```

### 3.5 명의변경 시나리오

```
MVNO                    KT M-Platform
  |-- MC0 명의변경 사전체크 -->|
  |<-- 응답 -----------------|
  |                          |
  |-- MP0 명의변경 처리 ----->|
  |<-- 접수결과 --------------|
  |<-- 명의변경처리결과(TCP) --|
```

---

## 4. 암호화

### 4.1 암호화 대상

민감정보를 포함한 연동 규격 필드에 적용합니다.

### 4.2 암/복호화 알고리즘

- **알고리즘** : SEED 128 (KISA 제공)
- **암호화** : SEED 암호화 후 BASE64.encode 하여 전송
- **복호화** : 암호화된 문자열을 BASE64.decode 후 SEED 복호화

### 4.3 암호화 대상 필드

| Event Code | 서비스 | 항목명 | IN/OUT | 설명 |
|---|---|---|---|---|
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.custIdntNo` | IN | 고객식별번호 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.custNm` | IN | 고객이름 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.crprNo` | IN | 법인번호 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.athnRqstcustCntplcNo` | IN | 인증요청고객연락처번호 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.lcnsNo` | IN | 면허번호 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.mrtrPrsnNo` | IN | 유공자번호 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.fornBrthDate` | IN | 외국인의생년월일 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.wrlnTlphNo` | IN | 유선전화번호 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.tlphNo` | IN | 핸드폰전화 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.rprsPrsnNm` | IN | 대표자명 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.fndtCntplcSbst` | IN | 기본연락처내용 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.mntCntplcSbst` | IN | 상세연락처내용 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.brthDate` | IN | 생년월일 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.emlAdrsNm` | IN | 이메일주소 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.agntCustNm` | IN | 법정대리인 성명 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.agntIdfyNoVal` | IN | 법정대리인 고객식별번호 |
| PC0 | OsstSvcPrcSO.osstPrePrc | `inDto.agntTelNo` | IN | 법정대리인 연락처 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.agntCustNm` | IN | 법정대리인 성명 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.agntIdfyNoVal` | IN | 법정대리인 고객식별번호 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.agntTelNo` | IN | 법정대리인 연락처 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.athnRqstcustCntplcNo` | IN | 인증요청고객연락처번호 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.brthDate` | IN | 생년월일 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.crprFndtCntplcSbst` | IN | 법인기본연락처내용 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.crprMntCntplcSbst` | IN | 법인상세연락처내용 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.crprNo` | IN | 법인번호 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.custIdntNo` | IN | 고객식별번호 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.custNm` | IN | 고객이름 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.emlAdrsNm` | IN | 이메일주소 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.fndtCntplcSbst` | IN | 기본연락처내용 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.fornBrthDate` | IN | 외국인의생년월일 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.lcnsNo` | IN | 면허번호 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.mntCntplcSbst` | IN | 상세연락처내용 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.mrtrPrsnNo` | IN | 유공자번호 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.rprsPrsnNm` | IN | 대표자명 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.tlphNo` | IN | 핸드폰전화 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inDto.wrlnTlphNo` | IN | 유선전화번호 |
| PC0 | OsstNpSvcPrcSO.osstNpPrePrc | `inNpDto.npTlphNo` | IN | 번호이동전화번호 |
| NU1 | OsstSvcNoSO.inqrOsstSvcNoInfo | `inDto.inqrSvcNoInfoInDTO.custIdntNo` | IN | 주민번호 |
| NU1 | OsstSvcNoSO.inqrOsstSvcNoInfo | `outDto.svcNoList.tlphNo` | OUT | 전화번호 |
| NU2 | OsstSvcNoSO.resvOsstTlphNo | `inDto.tlphNo` | IN | 전화번호 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.rqsshtTlphNo` | IN | 청구서전화번호 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.rqsshtEmlAdrsNm` | IN | 청구서이메일주소 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.billFndtCntplcSbst` | IN | 청구서기본연락처내용 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.billMntCntplcSbst` | IN | 청구상세연락처내용 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.blpymMthdIdntNo` | IN | 납부방법식별번호 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.blpymCustNm` | IN | 납부고객명 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.blpymCustIdntNo` | IN | 납부고객식별번호 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.realUseCustNm` | IN | 실사용고객명 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.iccId` | IN | icc아이디 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.eUiccId` | IN | EID |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.motliSvcNo` | IN | 모회선전화번호 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.agntBrthDate` | IN | 대리인 생일일자 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.agntCustNm` | IN | 대리인성명 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.homeTlphNo` | IN | 자택전화번호 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.wrkplcTlphNo` | IN | 직장전화번호 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.prttlpNo` | IN | 대리인의 휴대전화번호 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.tlphNo` | IN | 전화번호 |
| OP0 | OsstSvcPrcSO.osstOpenPrc | `inDto.sbscPrtlstRcvEmlAdrsNm` | IN | 가입내역서수신이메일주소명 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.agntBrthDate` | IN | 대리인 생일일자 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.agntCustNm` | IN | 대리인성명 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.billFndtCntplcSbst` | IN | 청구서기본연락처내용 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.billMntCntplcSbst` | IN | 청구상세연락처내용 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.blpymCustIdntNo` | IN | 납부고객식별번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.blpymCustNm` | IN | 납부고객명 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.blpymMthdIdntNo` | IN | 납부방법식별번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.eUiccId` | IN | EID |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.homeTlphNo` | IN | 자택전화번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.iccId` | IN | iccId |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.motliSvcNo` | IN | 모회선전화번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.prttlpNo` | IN | 대리인의 휴대전화번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.realUseCustNm` | IN | 실사용고객명 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.rqsshtEmlAdrsNm` | IN | 청구서이메일주소 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.rqsshtTlphNo` | IN | 청구서전화번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.sbscPrtlstRcvEmlAdrsNm` | IN | 가입내역서수신이메일주소명 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.tlphNo` | IN | 전화번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inDto.wrkplcTlphNo` | IN | 직장전화번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inNpDto.npCcardNo` | IN | 번호이동 카드번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inNpDto.npCcardOwnrIdfyNo` | IN | 번호이동카드명의인식별번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inNpDto.npHndsetInstaLmsTlphNo` | IN | 번호이동단말기할부금분납지속LMS청구수신번호 |
| OP0 | OsstNpSvcPrcSO.osstNpOpenPrc | `inNpDto.rfundBankBnkacnNo` | IN | 번호이동환불계좌번호 |
| NP1 | OsstNpSvcPrcSO.osstNpBfacAgree | `inDto.npTlphNo` | IN | 번호이동전화번호 |
| NP1 | OsstNpSvcPrcSO.osstNpBfacAgree | `inDto.custIdntNo` | IN | 고객식별번호 |
| NP1 | OsstNpSvcPrcSO.osstNpBfacAgree | `inDto.custNm` | IN | 고객명 |
| NP2 | OsstNpSvcPrcSO.osstNpPayAsert | `inDto.npTlphNo` | IN | 번호이동전화번호 |
| NP3 | OsstNpSvcPrcSO.osstNpBfacAgreeRpyRetv | `inDto.telNo` | IN | 번호이동전화번호 |
| CP0 | OsstCanSvcPrcSO.osstCanPps2Prc | `inDto.ctn` | IN | 사용자 전화번호 |
| EP0 | OsstCanSvcPrcSO.osstCanPrc | `inDto.ctn` | IN | 고객전화번호 |
| EP0 | OsstCanSvcPrcSO.osstCanPrc | `inDto.cntplcNo` | IN | 연락처번호 |
| EP0 | OsstCanSvcPrcSO.osstCanM2mPrc | `inDto.ncn` | IN | 서비스계약번호 |
| EP0 | OsstCanSvcPrcSO.osstCanM2mPrc | `inDto.ctn` | IN | 고객전화번호 |
| EP0 | OsstCanSvcPrcSO.osstCanM2mPrc | `inDto.cntplcNo` | IN | 연락처번호 |
| ET0 | OsstSvcPrcSO.osstDataMailSend | `inDto.custNm` | IN | 고객명 |
| ET0 | OsstSvcPrcSO.osstDataMailSend | `inDto.custCntplcNo` | IN | 고객 연락처 |
| ET0 | OsstSvcPrcSO.osstDataMailSend | `outDto.custNm` | OUT | 고객명 |
| ET0 | OsstSvcPrcSO.osstDataMailSend | `outDto.custCntplcNo` | OUT | 고객 연락처 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.tlphNo` | IN | 전화번호 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.custIdntNo` | IN | 고객식별번호 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.custNm` | IN | 고객명 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.lcnsNo` | IN | 면허번호 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.mrtrPrsnNo` | IN | 유공자번호 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.fornBrthDate` | IN | 외국인생일일자 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.athnRqstcustCntplcNo` | IN | 인증요청고객연락처번호 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.agntBrthDate` | IN | 대리인생년월일 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.intmSrlNo` | IN | 기기일련번호 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.iccId` | IN | USIM 일련번호 |
| HC0 | OsstIcgMainPrcsSO.icgPreChk | `inDto.eUiccId` | IN | EID |
| HC0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcsPreChk | `inDto.tlphNo` | IN | 전화번호 |
| HC0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcsPreChk | `inDto.custIdntNo` | IN | 고객식별번호 |
| HC0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcsPreChk | `inDto.custNm` | IN | 고객명 |
| HC0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcsPreChk | `inDto.lcnsNo` | IN | 면허번호 |
| HC0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcsPreChk | `inDto.fornBrthDate` | IN | 외국인생일일자 |
| HC0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcsPreChk | `inDto.athnRqstcustCntplcNo` | IN | 인증요청고객연락처번호 |
| HC0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcsPreChk | `inDto.agntBrthDate` | IN | 대리인생년월일 |
| HC0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcsPreChk | `inDto.iccId` | IN | USIM 일련번호 |
| HC0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcsPreChk | `inDto.eUiccId` | IN | EID |
| HP0 | OsstIcgMainPrcsSO.icgPrcTrt | `inDto.tlphNo` | IN | 전화번호 |
| HP0 | OsstIcgCnclMainPrcsSO.icgCnclMainPrcs | `inDto.tlphNo` | IN | 전화번호 |
| UC0 | OsstUsimChgPrcSO.usimChgPrc | `inDto.tlphNo` | IN | 전화번호 |
| UC0 | OsstUsimChgPrcSO.usimChgPrc | `inDto.iccId` | IN | USIM 일련번호 |
| RC0 | OsstRclPrcsSO.rclPreChk | `inDto.custId` | IN | 고객아이디 |
| RC0 | OsstRclPrcsSO.rclPreChk | `inDto.tlphNo` | IN | 전화번호 |
| RC0 | OsstRclPrcsSO.rclPreChk | `inDto.agntBrthDate` | IN | 대리인생년월일 |
| RC0 | OsstRclPrcsSO.rclPreChk | `inDto.iccId` | IN | USIM 일련번호 |
| RP0 | OsstRclPrcsSO.rclPrcsTrt | `inDto.tlphNo` | IN | 전화번호 |
| RP0 | OsstRclPrcsSO.rclPrcsTrt | `inDto.agntBrthDate` | IN | 대리인생년월일 |
| RP0 | OsstRclPrcsSO.rclPrcsTrt | `inDto.iccId` | IN | USIM 일련번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.baseInfo.iccId` | IN | USIM 일련번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.baseInfo.tlphNo` | IN | 전화번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.baseInfo.realUseCustNm` | IN | 실사용고객명 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.baseInfo.realUseCustBrthDate` | IN | 실사용 생년월일 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.baseInfo.cntplcNo` | IN | 연락처번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.custIdntNo` | IN | 고객식별번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.custNm` | IN | 고객명 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.athnRqstcustCntplcNo` | IN | 인증고객연락처번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.lcnsNo` | IN | 면호번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.mrtrPrsnNo` | IN | 유공자번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.fornBrthDate` | IN | 외국인생일일자 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.wrlnTlphNo` | IN | 유선전화번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.rprsPrsnNm` | IN | 대표자명 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.fndtCntplcSbst` | IN | 기본연락처내용 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.mntCntplcSbst` | IN | 상세연락처내용 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.brthDate` | IN | 생일일자 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.emlAdrsNm` | IN | 이메일주소 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.crprFndtCntplcSbst` | IN | 법인기본연락처내용 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.crprMntCntplcSbst` | IN | 법인상세연락처내용 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.agntCustNm` | IN | 법정대리인성명 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.agntIdfyNoVal` | IN | 법정대리인 고객식별번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.agntTelNo` | IN | 법정대리인 연락처 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.agntBrthDate` | IN | 법정대리인 생년월일 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvCustInfo.homeTlphNo` | IN | 집전화번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvBillAcntInfo.rqsshtTlphNo` | IN | 청구서 발송 전화번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvBillAcntInfo.rqsshtEmlAdrsNm` | IN | 청구서 이메일주소명 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvBillAcntInfo.billFndtCntplcSbst` | IN | 청수서기본연락처내용 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvBillAcntInfo.billMntCntplcSbst` | IN | 청구상세연락처내용 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvBillAcntInfo.blpymMthdIdntNo` | IN | 납부방법식별번호 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvBillAcntInfo.blpymCustNm` | IN | 납부고객명 |
| MC0 | OsstMcnChgPrcSO.osstMcnPrechk | `inDto.rcvBillAcntInfo.blpymCustIdntNo` | IN | 납부고객식별번호 |
| MP0 | OsstMcnChgPrcSO.osstMcnChg | `inDto.baseInfo.iccId` | IN | USIM 일련번호 |
| MP0 | OsstMcnChgPrcSO.osstMcnChg | `inDto.baseInfo.tlphNo` | IN | 전화번호 |
| MP0 | OsstMcnChgPrcSO.osstMcnChg | `inDto.baseInfo.sbscPrtlstRcvEmlAdrsNm` | IN | 가입내역서수신이메일주소명 |
| MP0 | OsstMcnChgPrcSO.osstMcnChg | `inDto.baseInfo.chgRqsshtEmlAdrsNm` | IN | 청구서이메일주소명 |
| MP0 | OsstMcnChgPrcSO.osstMcnChg | `inDto.baseInfo.realUseCustNm` | IN | 실제사용자명 |
| MP0 | OsstMcnChgPrcSO.osstMcnChg | `inDto.baseInfo.realUseCustBrthDate` | IN | 실제사용자 생년월일 |
| MP0 | OsstMcnChgPrcSO.osstMcnChg | `inDto.baseInfo.cntplcNo` | IN | 연락처번호 |
| OF | OsstFrmPapIfSendSO.osstFrmPapIfSend | `inDto.reqTelNo` | IN | 개통전화번호 |
| OF | OsstFrmPapIfSendSO.osstFrmPapIfSend | `inDto.niceCertKey` | IN | CI값 |
| OF | OsstFrmPapIfSendSO.osstFrmPapIfSend | `inDto.emlAdrsNm` | IN | 이메일주소 |
| OF | OsstFrmPapIfSendSO.osstFrmPapIfSend | `inDto.blpymMthdIdntNo` | IN | 납부방법식별번호 |
| OF | OsstFrmPapIfSendSO.osstFrmPapIfSend | `inDto.billAccId` | IN | 청구계정 ID |
| OF | OsstFrmPapIfSendSO.osstFrmPapIfSend | `inDto.motliSvcNo` | IN | 모회선전화번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.custIdntNo` | IN | 고객식별번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.custNm` | IN | 고객이름 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.crprNo` | IN | 법인번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.athnRqstcustCntplcNo` | IN | 인증요청고객연락처번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.lcnsNo` | IN | 면허번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.mrtrPrsnNo` | IN | 유공자번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.fornBrthDate` | IN | 외국인의생년월일 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.wrlnTlphNo` | IN | 유선전화번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.tlphNo` | IN | 핸드폰전화 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.rprsPrsnNm` | IN | 대표자명 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.fndtCntplcSbst` | IN | 기본연락처내용 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.mntCntplcSbst` | IN | 상세연락처내용 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.brthDate` | IN | 생년월일 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.emlAdrsNm` | IN | 이메일주소 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.agntCustNm` | IN | 법정대리인 성명 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.agntIdfyNoVal` | IN | 법정대리인 고객식별번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.agntTelNo` | IN | 법정대리인 연락처 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.agntCustNm` | IN | 법정대리인 성명 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.agntIdfyNoVal` | IN | 법정대리인 고객식별번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.agntTelNo` | IN | 법정대리인 연락처 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.athnRqstcustCntplcNo` | IN | 인증요청고객연락처번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.brthDate` | IN | 생년월일 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.crprFndtCntplcSbst` | IN | 법인기본연락처내용 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.crprMntCntplcSbst` | IN | 법인상세연락처내용 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.crprNo` | IN | 법인번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.custIdntNo` | IN | 고객식별번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.custNm` | IN | 고객이름 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.emlAdrsNm` | IN | 이메일주소 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.fndtCntplcSbst` | IN | 기본연락처내용 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.fornBrthDate` | IN | 외국인의생년월일 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.lcnsNo` | IN | 면허번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.mntCntplcSbst` | IN | 상세연락처내용 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.mrtrPrsnNo` | IN | 유공자번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.rprsPrsnNm` | IN | 대표자명 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.tlphNo` | IN | 핸드폰전화 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.wrlnTlphNo` | IN | 유선전화번호 |
| FPC0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inNpDto.npTlphNo` | IN | 번호이동전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.rqsshtTlphNo` | IN | 청구서전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.rqsshtEmlAdrsNm` | IN | 청구서이메일주소 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.billFndtCntplcSbst` | IN | 청구서기본연락처내용 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.billMntCntplcSbst` | IN | 청구상세연락처내용 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.blpymMthdIdntNo` | IN | 납부방법식별번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.blpymCustNm` | IN | 납부고객명 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.blpymCustIdntNo` | IN | 납부고객식별번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.realUseCustNm` | IN | 실사용고객명 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.iccId` | IN | icc아이디 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.eUiccId` | IN | EID |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.motliSvcNo` | IN | 모회선전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.agntBrthDate` | IN | 대리인 생일일자 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.agntCustNm` | IN | 대리인성명 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.homeTlphNo` | IN | 자택전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.wrkplcTlphNo` | IN | 직장전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.prttlpNo` | IN | 대리인의 휴대전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.tlphNo` | IN | 전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapPrePrc | `inDto.sbscPrtlstRcvEmlAdrsNm` | IN | 가입내역서수신이메일주소명 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.agntBrthDate` | IN | 대리인 생일일자 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.agntCustNm` | IN | 대리인성명 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.billFndtCntplcSbst` | IN | 청구서기본연락처내용 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.billMntCntplcSbst` | IN | 청구상세연락처내용 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.blpymCustIdntNo` | IN | 납부고객식별번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.blpymCustNm` | IN | 납부고객명 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.blpymMthdIdntNo` | IN | 납부방법식별번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.eUiccId` | IN | EID |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.homeTlphNo` | IN | 자택전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.iccId` | IN | iccId |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.motliSvcNo` | IN | 모회선전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.prttlpNo` | IN | 대리인의 휴대전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.realUseCustNm` | IN | 실사용고객명 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.rqsshtEmlAdrsNm` | IN | 청구서이메일주소 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.rqsshtTlphNo` | IN | 청구서전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.sbscPrtlstRcvEmlAdrsNm` | IN | 가입내역서수신이메일주소명 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.tlphNo` | IN | 전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inDto.wrkplcTlphNo` | IN | 직장전화번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inNpDto.npCcardNo` | IN | 번호이동 카드번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inNpDto.npCcardOwnrIdfyNo` | IN | 번호이동카드명의인식별번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inNpDto.npHndsetInstaLmsTlphNo` | IN | 번호이동단말기할부금분납지속LMS청구수신번호 |
| FOP0 | OsstFrmpapSvcPrcSO.osstFrmpapNpPrePrc | `inNpDto.rfundBankBnkacnNo` | IN | 번호이동환불계좌번호 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.tlphNo` | IN | 전화번호 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.custIdntNo` | IN | 고객식별번호 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.custNm` | IN | 고객명 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.lcnsNo` | IN | 면허번호 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.mrtrPrsnNo` | IN | 유공자번호 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.fornBrthDate` | IN | 외국인생일일자 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.athnRqstcustCntplcNo` | IN | 인증요청고객연락처번호 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.agntBrthDate` | IN | 대리인생년월일 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.intmSrlNo` | IN | 기기일련번호 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.iccId` | IN | USIM 일련번호 |
| FHC0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPreChk | `inDto.eUiccId` | IN | EID |
| FHP0 | OsstFrmpapIcgMainPrcsSO.icgFrmpapPrcTrt | `inDto.tlphNo` | IN | 전화번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.baseInfo.iccId` | IN | USIM 일련번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.baseInfo.tlphNo` | IN | 전화번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.baseInfo.realUseCustNm` | IN | 실사용고객명 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.baseInfo.realUseCustBrthDate` | IN | 실사용 생년월일 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.baseInfo.cntplcNo` | IN | 연락처번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.custIdntNo` | IN | 고객식별번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.custNm` | IN | 고객명 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.athnRqstcustCntplcNo` | IN | 인증고객연락처번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.lcnsNo` | IN | 면호번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.mrtrPrsnNo` | IN | 유공자번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.fornBrthDate` | IN | 외국인생일일자 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.wrlnTlphNo` | IN | 유선전화번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.rprsPrsnNm` | IN | 대표자명 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.fndtCntplcSbst` | IN | 기본연락처내용 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.mntCntplcSbst` | IN | 상세연락처내용 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.brthDate` | IN | 생일일자 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.emlAdrsNm` | IN | 이메일주소 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.crprFndtCntplcSbst` | IN | 법인기본연락처내용 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.crprMntCntplcSbst` | IN | 법인상세연락처내용 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.agntCustNm` | IN | 법정대리인성명 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.agntIdfyNoVal` | IN | 법정대리인 고객식별번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.agntTelNo` | IN | 법정대리인 연락처 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.agntBrthDate` | IN | 법정대리인 생년월일 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvCustInfo.homeTlphNo` | IN | 집전화번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvBillAcntInfo.rqsshtTlphNo` | IN | 청구서 발송 전화번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvBillAcntInfo.rqsshtEmlAdrsNm` | IN | 청구서 이메일주소명 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvBillAcntInfo.billFndtCntplcSbst` | IN | 청수서기본연락처내용 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvBillAcntInfo.billMntCntplcSbst` | IN | 청구상세연락처내용 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvBillAcntInfo.blpymMthdIdntNo` | IN | 납부방법식별번호 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvBillAcntInfo.blpymCustNm` | IN | 납부고객명 |
| FMC0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnPrechk | `inDto.rcvBillAcntInfo.blpymCustIdntNo` | IN | 납부고객식별번호 |
| FMP0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnChg | `inDto.baseInfo.iccId` | IN | USIM 일련번호 |
| FMP0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnChg | `inDto.baseInfo.tlphNo` | IN | 전화번호 |
| FMP0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnChg | `inDto.baseInfo.sbscPrtlstRcvEmlAdrsNm` | IN | 가입내역서수신이메일주소명 |
| FMP0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnChg | `inDto.baseInfo.chgRqsshtEmlAdrsNm` | IN | 청구서이메일주소명 |
| FMP0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnChg | `inDto.baseInfo.realUseCustNm` | IN | 실제사용자명 |
| FMP0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnChg | `inDto.baseInfo.realUseCustBrthDate` | IN | 실제사용자 생년월일 |
| FMP0 | OsstFrmpapMcnChgPrcSO.osstFrmpapMcnChg | `inDto.baseInfo.cntplcNo` | IN | 연락처번호 |

---

## 5. 공통 Header

Out-Bound 서비스(MVNO → M-Platform) 연동 시 모든 전문에 포함되는 공통 Header입니다.

### 5.1 bizHeader

| seq | 항목(영문) | 항목(한글) | MVNO 처리 | KTMP 처리 | 비고 |
|---|---|---|---|---|---|
| 1 | `appEntrPrsnId` | 사업자코드 | O |  | KT에서 부여한 3자리 사업자코드 |
| 2 | `appAgncCd` | 대리점 코드 | O |  | KT에서 부여한 대리점 코드. 사업자는 1개 이상의 대리점코드를 가짐 |
| 3 | `appEventCd` | 업무코드 | O |  | ex) PC0, NU1 등 |
| 4 | `appSendDateTime` | MVNO사업자가 송신한 시간 | O |  | YYYYMMDDHH24MISS |
| 5 | `appRecvDateTime` | M-Platform에서 응답한 시간 |  | O | YYYYMMDDHH24MISS |
| 6 | `appLgDateTime` | M-Platform Log처리 시간 |  | O | YYYYMMDDHH24MISS |
| 7 | `appNstepUserId` | KOS연동용 항목 | O |  | KT에서 부여한 KOS UI 접속 계정 |
| 8 | `appOrderId` | 오더ID |  |  | 미사용 |

### 5.2 commHeader

| seq | 항목(영문) | 항목(한글) | MVNO 처리 | KTMP 처리 | 비고 |
|---|---|---|---|---|---|
| 1 | `globalNo` | Global No | O |  | bizHeader의 appNstepUserId값 + yyyyMMddHHmmss(14자리) + MILLISECOND(3자리) |
| 2 | `encYn` | Encoding 여부 |  |  | 미사용 |
| 3 | `responseType` | 응답유형 |  | O | N: 성공, S: 시스템에러, E: Biz에러 |
| 4 | `responseCode` | 에러상세코드 |  | O |  |
| 5 | `responseLogcd` | 응답로그코드 |  |  | 미사용 |

---

## 6. 인터페이스 목록

### 6.1 Out-Bound Service (MVNO → KT M-Platform)

| Event Code | Interface Name | Service Name | Operation Name | Description |
|---|---|---|---|---|
| - | Header |  | `` | 전문에 수록되는 공통 Header 정보 |
| PC0 | 사전체크 및 고객생성 | OsstSvcPrcService / OsstNpSvcPrcService | `osstPrePrc / osstNpPrePrc` | 개통 처리를 위한 사전체크 및 고객정보 생성 |
| NU1 | 번호조회 | OsstSvcNoService | `inqrOsstSvcNoInfo` | 개통 및 번호변경 등의 업무수행 시 사용가능한 번호자원을 조회 |
| NU2 | 번호예약/취소 | OsstSvcNoService | `resvOsstTlphNo` | 개통/번호변경에 사용될 전화번호에 대하여 예약처리 수행 |
| OP0 | 개통 및 수납 | OsstSvcPrcService / OsstNpSvcPrcService | `osstOpenPrc / osstNpOpenPrc` | 사전체크 및 고객생성과 번호예약 이후 개통을 위한 실제 업무처리 수행 |
| NP1 | 번호이동 사전동의 요청 | OsstNpSvcPrcService | `osstNpBfacAgree` | 번호이동 가입시 번호이동 사전동의 요청(0160) 전문 송신처리 |
| NP2 | 납부주장 요청 | OsstNpSvcPrcService | `osstNpPayAsert` | 번호이동 미납고객인 경우 미납금액에 대한 납부주장을 처리 |
| NP3 | 사전동의 결과조회 | OsstNpSvcPrcService | `osstNpBfacAgreeRpyRetv` | 번호이동 가입시 번호이동 사전동의 요청(0160)에 대한 응답전문(170) 결과 조회 |
| ST1 | 상태조회 | OsstSvcPrcService | `osstPrcSch` | 오더 진행 상태 및 결과를 조회 |
| CP0 | 지능망 직권해지 | OsstCanSvcPrcService | `osstCanPps2Prc` | 지능망 서비스 가입고객 중 정지상태 고객에 대한 직권해지 처리 |
| EP0 | 해지 | OsstCanSvcPrcService | `osstCanPrc` | 일반요금제 가입고객 회선에 대한 해지 가능여부 체크 및 해지 처리 |
| EP0 | M2M 해지 | OsstCanSvcPrcService | `osstCanM2mPrc` | M2M 요금제 가입고객 회선에 대한 해지 가능여부 체크 및 해지 처리 |
| AP0 | 미납 직권해지처리 | OsstAthrtCanService | `trtAthrtCan` | 미납에 의한 직권해지처리 |
| ET0 | 업무시간외 개통접수건 사업자 전송 | OsstSvcPrcService | `osstDataMailSend` | 업무시간외 개통접수건에 대하여 각 사업자에게 개통 접수 DATA 메일 전송 |
| ET1 | 휴일여부 조회 | OsstSvcPrcService | `inqrOsstActHolyDay` | 개통가능한 날인지 체크 |
| HC0 | 기기변경/기기변경취소 사전체크 | OsstIcgMainPrcsService / OsstIcgCnclMainPrcsService | `icgPreChk / icgCnclMainPrcsPreChk` | 기기변경/기기변경취소 처리를 위한 부정가입 실명인증 및 사전체크 |
| HP0 | 기기변경/기기변경취소 처리 | OsstIcgMainPrcsService / OsstIcgCnclMainPrcsService | `icgPrcTrt / icgCnclMainPrcs` | 기기변경/기기변경취소 사전체크 후 실제 업무처리 수행 |
| X27 | 일시정지가능여부조회 | MoscSuspenService | `moscSuspenPosInfo` | 계약상태 등 일시정지 가능여부 정보 조회 |
| X28 | 일시정지해제가능여부조회 | MoscSuspenService | `moscSuspenCnlPosInfo` | 일시정지해제 가능여부 조회 |
| X65 | 부정사용 일시정지신청 | MoscSuspenService | `moscNegativeSuspenChg` | 부정사용 일시정지 처리 수행 |
| X66 | 부정사용 일시정지해제신청 | MoscSuspenService | `moscNegativeSuspenCnlChg` | 부정사용 일시정지 해제처리 수행 |
| UC0 | 유심변경 | OsstUsimChgPrcService | `usimChgPrc` | 유심변경 사전체크 및 유심 변경 처리 |
| RC0 | 해지취소 사전체크 | OsstRclPrcsService | `rclPreChk` | 해지된 회선에 대한 해지취소 가능여부 사전체크 |
| RP0 | 해지취소 처리 | OsstRclPrcsService | `rclPrcsTrt` | 해지취소 업무처리 수행 |
| MC0 | 명의변경사전체크 | OsstMcnMgmtService | `osstMcnPrechk` | 명의변경 사전체크 및 양수인 고객 및 청구계정 생성 |
| MP0 | 명의변경 | OsstMcnMgmtService | `osstMcnChg` | 명의변경 처리 |
| OF | 개통서식지등록 | OsstFrmPapIfSendService | `osstFrmPapIfSend` | 채널 개통 후 개통서식지 등록 서비스 |
| FS0 | 서식지 목록조회 | OsstFrmpapService | `osstFrmpapListRetv` | MVNO 사업자의 판매점에서 등록한 지류서식지 대상 목록조회 |
| FS1 | 서식지 상태조회 | OsstFrmpapService | `osstFrmpapRetv` | Osst 지류서식지 개통업무처리 대상의 서식지 상태 조회 |
| FS2 | 서식지 상태변경 | OsstFrmpapService | `osstFrmpapStatChg` | Osst 지류서식지 개통처리 진행을 위한 서식지 상태 변경 서비스 |
| FS3 | 무서식지대상조회 | OsstNthnFrmpapAtcTrtService | `retvOsstNthnFrmpapTgt` | 무서식지대상(=서식지 후첨부대상) 조회 (조회시작일시 기준 최대 30일) |
| FS4 | 무서식지첨부처리 | OsstNthnFrmpapAtcTrtService | `trtOsstNthnFrmpapAtc` | 무서식지로 생성된 오더에 한해 후첨부처리 가능 (단건처리) |
| FS5 | 온라인 서식지 등록 | OsstOnlineFrmpapService | `osstOnlineFrmpapReg` | 온라인서식지 등록 업무처리 수행 |
| FS6 | 온라인 서식지 CI 조회 | OsstOnlineFrmpapService | `osstOnlineFrmpapIpinRetv` | 온라인서식지 등록 후 KOS 사전체크시 자체검증수행을 위한 IPIN CI 조회 |
| FS7 | KAIT 사진진위확인 요청 | OsstFrmpapService | `photoAthnRqt` | MVNO사업자 금융스캐너를 통한 신분증 스캔시 KAIT 사진진위확인 요청 |
| FPC0 | 서식지 사전체크(신규,번호이동) | OsstFrmpapSvcPrcSOService | `osstFrmpapPrePrc / osstFrmpapNpPrePrc` | 신분증스캐너를 사용하여 고객 정보등록처리를 진행한 사업자의 개통사전체크 및 고객생성 |
| FOP0 | 서식지 개통(신규,번호이동) | OsstFrmpapSvcPrcSOService | `osstFrmpapOpenPrc / osstFrmpapNpOpenPrc` | 서식지 사전체크 및 고객생성과 번호예약 이후 개통을 위한 실제 업무처리 수행 |
| FHC0 | 서식지 기기변경 사전체크 | OsstFrmpapIcgMainPrcsService | `icgFrmpapPreChk` | 신분증스캐너를 사용하여 고객 정보등록처리를 진행한 사업자의 기기변경 사전체크 |
| FHP0 | 서식지 기기변경 처리 | OsstFrmpapIcgMainPrcsService | `icgFrmpapPrcTrt` | 신분증스캐너를 사용하여 고객 정보등록처리를 진행한 사업자의 기기변경 처리 |
| FMC0 | 서식지 명의변경 사전체크 | OsstFrmpapMcnChgPrcService | `osstFrmpapMcnPrechk` | 신분증스캐너를 사용하여 고객 정보등록처리를 진행한 사업자의 명의변경 사전체크 |
| FMP0 | 서식지 명의변경 처리 | OsstFrmpapMcnChgPrcService | `osstFrmpapMcnChg` | 신분증스캐너를 사용하여 고객 정보등록처리를 진행한 사업자의 명의변경 처리 |
| EFS0 | 기타 서식지 목록조회 | OsstEtcFrmpapService | `osstEtcFrmpapListRetv` | 기타첨부 가능대상 서식지 목록조회 |
| EFS1 | 기타첨부 | OsstEtcFrmpapService | `osstEtcFrmpapTrt` | 기타서식지 등록처리 |
| EFS2 | 기타첨부 결과조회 | OsstEtcFrmpapService | `osstEtcFrmpapAtcStatRetv` | 조회한 고객에 등록된 서식지 목록조회 |
| EFS3 | 기타첨부 해지 | OsstEtcFrmpapService | `osstEtcFrmpapTrmn` | 조회한 고객의 기타첨부 서식지를 해제하는 서비스 |
| T01 | 유심무상교체 접수가능 여부조회 | OsstUsimChgAcceptService | `retvUsimChgAcceptPsbl` | 무상으로 유심변경 가능한지 조회하는 기능 |
| T02 | 유심무상교체 신청내역 전송 | OsstUsimChgAcceptService | `usimChgAccept` | 무상으로 유심변경 접수 처리 |
| FS8 | 고객 안면인증 URL 요청 | OsstCustFathMgmtService | `custFathUrlRqt` | MVNO사업자 가입 하려는 고객의 안면인증 요청을 위한 URL을 요청 |
| FS9 | 고객 안면인증 내역 조회 | OsstCustFathMgmtService | `custFathTxnRetv` | MVNO사업자 가입 하려는 고객의 안면인증 내역을 조회 |
| FT0 | 고객 안면인증 대상 여부 조회 | OsstCustFathMgmtService | `custFathTgtYnRetv` | MVNO사업자 가입 하려는 고객이 안면인증 대상인지 조회 |
| FT1 | 고객 안면인증 SKIP 요청 | OsstCustFathMgmtService | `custFathTxnSkipReq` | MVNO사업자 가입 하려는 고객이 안면인증을 SKIP 처리 |

### 6.2 In-Bound Service (KT M-Platform → MVNO)

> TCP Socket 방식으로 처리 결과를 비동기 수신

| Interface Name | Description |
|---|---|
| 개통처리결과(기존) | M-Platform → MVNO 로 사전체크 및 개통 처리 결과가 Socket방식으로 연동 |
| 개통처리결과(신규) | M-Platform → MVNO 로 사전체크 및 개통 처리 결과가 Socket방식으로 연동 |
| 해지처리결과 | M-Platform → MVNO 로 해지 API (CP0, EP0) 처리 결과가 Socket방식으로 연동 |
| 기변결과 | M-Platform → MVNO 로 기기변경 사전체크 및 기기변경 처리 결과가 Socket방식으로 연동 |
| 기변취소결과 | M-Platform → MVNO 로 기기변경취소 사전체크 및 기기변경 취소 결과가 Socket방식으로 연동 |
| 유심변경결과 | M-Platform → MVNO 로 유심변경처리결과 Socket방식으로 연동 |
| 해지취소결과 | M-Platform → MVNO 로 해지취소 사전체크 및 해지취소 처리결과 Socket방식으로 연동 |
| 명의변경처리결과 | M-Platform → MVNO 로 명의변경 처리결과 Socket방식으로 연동 |
| 안면인증 완료 통지 PUSH | 안면인증 완료 통지 push (KT→MVNO사업자) |

---

## 7. API 상세 명세

### 7.1 Out-Bound APIs

### PC0 — 사전체크 및 고객생성

| 항목 | 내용 |
|---|---|
| Service Name | `OsstSvcPrcService / OsstNpSvcPrcService` |
| Operation Name | `osstPrePrc / osstNpPrePrc` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mvnoOrdNo` | MVNO 오더 번호 | 14 | M |  | MVNO에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `slsCmpnCd` | 판매회사코드 | 3 | M |  |  |
| 3 | `custTypeCd` | 고객유형코드 | 2 | M |  | * 코드정의서 참조(CUST_TYPE_CD) |
| 4 | `custIdntNoIndCd` | 고객식별번호구분코드 | 2 | M |  | * 코드정의서 참조(CUST_IDNT_NO_IND_CD) |
| 5 | `custIdntNo` | 고객식별번호 | 20 | M | Y |  |
| 6 | `crprNo` | 법인번호 | 13 | C | Y | 법인인 경우 필수 |
| 7 | `custNm` | 고객명 | 60 | M | Y |  |
| 8 | `cntrUseCd` | 계약용도코드 | 2 | M |  | R : 일반 I : 지능망 선불 |
| 9 | `instYn` | 할부여부 | 1 | M |  | Y : 할부 N : 즉납 |
| 10 | `scnhndPhonInstYn` | 중고폰할부여부 | 1 | M |  | Y : MVNO중고폰  * Default : N |
| 11 | `myslAgreYn` | 본인동의여부 | 1 | M |  | Y:동의, N:미동의 |
| 12 | `nativeRlnamAthnEvdnPprCd` | 실명인증증빙서류코드 | 5 | C |  | * 코드정의서 참조(NATIVE_RLNAM_ATHN_EVDN_PPR_CD) |
| 13 | `athnRqstcustCntplcNo` | 인증요청고객연락처번호 | 12 | M | Y |  |
| 14 | `rsdcrtIssuDate` | 주민등록증발급일자 | 8 | C |  | 개인 내국인 : 주민등록증, 장애인등록증, 국가유공자증, 운전면허증 발급일자 개인 외국인 : 외국인등록증 발급일자 법인, 공공기관 : 사업자 교부일자 |
| 15 | `lcnsNo` | 면허번호 | 20 | C | Y | 실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 |
| 16 | `lcnsRgnCd` | 면허지역코드 | 2 | C |  | 실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 *코드정의서 참조 |
| 17 | `mrtrPrsnNo` | 유공자번호 | 10 | C | Y | 실명인증증빙서류코드가 MERIT(국가유공자)인 경우 필수 |
| 18 | `nationalityCd` | 국적코드 | 3 | C |  | 외국인 실명 인증 시 필수 *코드정의서 참조(NATIONNALITY_CD) |
| 19 | `fornBrthDate` | 외국인생일일자 | 8 | C | Y | 외국인-'04'(여권)인 경우 필수 (YYYYMMDD) |
| 20 | `crdtInfoAgreYn` | 신용정보동의여부 | 1 | M |  | 신용정보조회 및 이용에 대한 동의  * "Y" 로 고정 |
| 21 | `indvInfoInerPrcuseAgreYn` | 개인정보내부활용동의여부 | 1 | M |  | 개인정보수집및 활용에 동의  * "Y" 로 고정 |
| 22 | `cnsgInfoAdvrRcvAgreYn` | 위탁정보광고수신동의여부 | 1 | M |  | 고객 편의제공을 위한 이용 및 취급위탁, 정보/광고 수신동의 Y:동의 N:미동의 |
| 23 | `othcmpInfoAdvrRcvAgreYn` | 타사정보광고수신동의여부 | 1 | M |  | 타사로부터 의뢰 받은 정보/광고 수신동의 Y:동의 N:미동의 |
| 24 | `othcmpInfoAdvrCnsgAgreYn` | 타사정보광고위탁동의여부 | 1 | M |  | 타사로부터 의뢰 받은 정보/광고전송을 위한 개인정보 취급위탁 동의 Y:동의 N:미동의 |
| 25 | `grpAgntBindSvcSbscAgreYn` | 그룹사결합서비스가입동의여부 | 1 | M |  | 그룹사 결합서비스 가입 정보 Y:동의 N:미동의 |
| 26 | `cardInsrPrdcAgreYn` | 카드보험상품동의여부 | 1 | M |  | 카드발급 또는 보험상품 가입정보 Y:동의 N:미동의 |
| 27 | `olhRailCprtAgreYn` | OLLEH철도제휴동의여부 | 1 | M |  | 올레제휴팩 가입, 코레일 제휴요금제 가입 정보 Y:동의 N:미동의 |
| 28 | `olhShckWibroRlfAgreYn` | OLLEH쇼킹와이브로안심동의여부 | 1 | M |  | 올레폰안심플랜,올레폰케어쇼킹안심 Y:동의 N:미동의 |
| 29 | `olngDscnHynmtrAgreYn` | 주유할인현대자동차동의여부 | 1 | M |  | LTE제휴요금제 제휴종료 고객확인 Y:동의 N:미동의 |
| 30 | `olhCprtPntAgreYn` | OLLEH제휴포인트동의여부 | 1 | M |  | 올레 신 제휴 포인트 동의 Y:동의 N:미동의 |
| 31 | `dwoCprtPntAgreYn` | 대우제휴포인트동의여부 | 1 | M |  | 대우증권 통신비 지원 서비스 동의 Y:동의 N:미동의 |
| 32 | `wlfrDscnAplyAgreYn` | 복지할인신청동의여부 | 1 | M |  | 복지할인 신청 동의 Y:동의 N:미동의 |
| 33 | `spamPrvdAgreYn` | 스팸제공동의여부 | 1 | M |  | 스팸정보를 KISA 제공 동의 Y:동의 N:미동의 |
| 34 | `prttlpStlmUseAgreYn` | 이동전화결제이용동의여부 | 1 | M |  | 휴대폰결제이용에대한 동의  Y:동의 N:미동의  * 법인,공공기관 인 경우 "N" 고정 |
| 35 | `prttlpStlmPwdUseAgreYn` | 이동전화결제비밀번호이용동의여부 | 1 | M |  | 휴대폰결제비밀번호이용동의여부 Y:동의 N:미동의  * 이동전화결제이용동의여부가 Y(동의)인 경우 선택 가능 |
| 36 | `wrlnTlphNo` | 유선전화번호 | 12 | M | Y | 개인은 자택/직장 전화번호, 법인과 공공기관은 사업장 전화번호 |
| 37 | `tlphNo` | 전화번호 | 11 | O |  |  |
| 38 | `rprsPrsnNm` | 대표자명 | 60 | O | Y | 법인 및 공공기관의 대표자명 |
| 39 | `upjnCd` | 업종코드 | 6 | C |  | 법인과 공공기관 필수 *코드정의서 참조(UPJN_CD) |
| 40 | `bcuSbst` | 업태내용 | 100 | C |  | 법인과 공공기관 필수 |
| 41 | `zipNo` | 우편번호 | 6 | C |  | 법인과 공공기관 필수 |
| 42 | `fndtCntplcSbst` | 기본연락처내용 | 200 | C | Y | 법인과 공공기관 필수(신주소) |
| 43 | `mntCntplcSbst` | 상세연락처내용 | 100 | C | Y | 법인과 공공기관 필수(신주소) |
| 44 | `brthDate` | 생일일자 | 8 | C | Y | 개인만 입력 (YYYYMMDD) |
| 45 | `brthNnpIndCd` | 생일음양구분코드 | 1 | O |  | 0: 양력 1: 음력 |
| 46 | `jobCd` | 직업코드 | 2 | O |  | 개인만 입력 *코드정의서 참조(JOB_CD) |
| 47 | `emlAdrsNm` | 이메일주소명 | 100 | O | Y | 이메일주소명 (도메인포함) |
| 48 | `lstdIndCd` | 상장구분코드 | 2 | O |  | 법인과 공공기관만 입력 1: 상장, 2: 비상장 |
| 49 | `emplCnt` | 사원수 | 9 | O |  | 법인과 공공기관만 입력 |
| 50 | `slngAmt` | 매출액 | 15 | O |  | 법인과 공공기관만 입력 |
| 51 | `cptlAmnt` | 자본금액 | 15 | O |  | 법인과 공공기관만 입력 |
| 52 | `crprUpjnCd` | 법인업종코드 | 6 | O |  | 법인과 공공기관만 입력 *코드정의서 참조(UPJN_CD) |
| 53 | `crprBcuSbst` | 법인업태내용 | 100 | O |  | 법인과 공공기관만 입력 |
| 54 | `crprZipNo` | 법인우편번호 | 6 | O |  | 법인과 공공기관만 입력 |
| 55 | `crprFndtCntplcSbst` | 법인기본연락처내용 | 200 | O |  | 법인과 공공기관만 입력(신주소) |
| 56 | `crprMntCntplcSbst` | 법인상세연락처내용 | 100 | O |  | 법인과 공공기관만 입력(신주소) |
| 57 | `custInfoChngYn` | 고객정보변경여부 | 1 | M |  | 고객정보 변경 시 필수값이 아닌 항목에 대해 Y:변경, N:변경한함 |
| 58 | `m2mHndsetYn` | M2M단말여부 | 1 | C |  | M2M단말인 경우 Y Y: M2M단말, N: 일반단말 |
| 59 | `agntCustNm` | 법정대리인 성명 | 60 | C | Y | 미성년자 개통일 경우 필수 |
| 60 | `agntCustIdfyNoType` | 법정대리인 식별번호 종류 | 1 | C |  | 미성년자 개통일 경우 필수 1 (주민번호) , 4(외국인등록번호) |
| 61 | `agntIdfyNoVal` | 법정대리인 고객식별번호 | 20 | C | Y | 고객식별번호 |
| 62 | `agntPersonSexDiv` | 법정대리인 성별 | 1 | C |  | 미성년자 개통일 경우 필수 1 : 남자, 2 : 여자 |
| 63 | `agntAgreYn` | 법정대리인 정보조회 동의여부 | 1 | C |  | 미성년자 개통일 경우 필수 Y:동의 N:미동의 |
| 64 | `agntTelAthn` | 법정대리인 연락처 종류 | 1 | C |  | 미성년자 개통일 경우 필수 M : 모바일, I : 자택 |
| 65 | `agntTelNo` | 법정대리인 연락처 | 20 | C | Y | ex) 01098841542 |
| 66 | `agntTypeCd` | 법정대리인 유형 | 2 | C |  | 미성년자 개통일 경우 필수 01(부), 02(모), 03(후견인), 04(연대보증인) |
| 67 | `agntRsdcrtIssuDate` | 법정대리인 식별번호 발급일자 | 8 | C |  | 미성년자 개통일 경우 필수 ex)20200105 법정대리인실명인증증빙서류코드에 따라  주민등록번호 / 외국인등록번호 /  운전면허 발급일자 필수 |
| 68 | `agntRlnamAthnEvdnPprCd` | 법정대리인실명인증증빙서류코드 | 5 | C |  | 주민등록증 : REGID 외국인등록번호 : FORGN 운전면허증 : DRIVE 주민등록증발급신청서 : REGAP 법정대리인의 경우 실명인증증비서류코드 필수. |
| 69 | `agntLicnsRgnCd` | 법정대리인 면허지역코드 | 2 | C |  | 법정대리인실명인증증빙서류코드가 DRIVE 인 경우 면허번호지역코드 필수 *코드정의서 참조 (LCNS_RGN_CD) |
| 70 | `agntLicnsNo` | 법정대리인 면허번호 | 20 | C | Y | 법정대리인실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 |
| 71 | `agntNationalityCd` | 법정대리인 국적코드 | 3 | C |  | 법정대리인실명인증증빙서류코드가 FORGN 인 경우 국적코드 필수 미입력시 미성년자 국적코드로 대체되어 업무처리 진행 됨. *코드정의서 참조(NATIONNALITY_CD) |
| 72 | `oderTypeCd` | 오더유형코드 | 3 | M |  | *코드정의서 참조(AC_ITG_ODER_TYPE_CD) 신규개통 : NAC 무선번호이동 : MNP |
| 73 | `npTlphNo` | 번호이동전화번호 | 11 | C | Y | 번호이동가입 필수 |
| 74 | `indvBizrYn` | 개인사업자여부 | 1 | C |  | 번호이동가입 필수 Y:개인사업자 |
| 75 | `bchngNpCommCmpnCd` | 변경전번호이동사업자코드 | 3 | C |  | 번호이동가입 필수 *코드정의서 참조 (KOS.AC_NP_COMM_CMPN_BAS) |
| 76 | `npRstrtnContYn` | 번호이동제한예외여부 | 1 | C |  | 번호이동가입 필수 Y:예외 N:대상아님 |
| 77 | `ytrpaySoffAgreYn` | 해지미환급금 상계동의여부 | 1 | C |  | 번호이동가입 필수 (계약유형이 선불인 경우에는 미동의) Y:동의 N:미동의 |
| 78 | `atmSeqNo` | atm일련번호 | 9 | C |  | 효성에서 ATM기기에서 가입시 ATM기기 일련번호  - 효성 ATM 기기만 필수값 |
| 79 | `myslfAthnYn` | 본인인증여부 | 1 | C |  | 본인인증여부 Y/N |
| 80 | `ipinCi` | 본인인증(CI) | 200 | C | Y | 본인인증 후 취득한 CI 값 |
| 81 | `onlineAthnDivCd` | 본인인증(CI) | 2 | C |  | 신용카드, 네이버, 카카오 등 본인인증 수단  - 코드로 연동, 확정 후 제공 10 공인인증 20 신용카드인증 30 휴대폰인증 60 카카오페이인증 61 PASS계좌인증 70 네이버인증 71 기타인증(PAYCO, TOSS 등) 이 인증 외에 다른 인증수단은 규제기관(KAIT)의 확인 필요 |
| 82 | `fnncDealAgreeYn` | 금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의 | 1 | O |  | 금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의 미입력시 미동의로 연동 Y:동의 N:미동의 |
| 83 | `photoAthnTxnSeq` | 사진인증내역일련번호 | 15 | C |  | 금융스캐너 사용시 KAIT 사진진위인증(FS7) 요청 후 성공/스킵한 사진인증내역일련번호 |
| 84 | `dlvrSeqNo` | 유심대면배송 주문접수번호 | 16 | C |  | 유심대면배송을 통한 개통인 경우 필수 |
| 85 | `fathTransacId` | 안면인증 트랜잭션 아이디 | 30 | C |  | 안면인증 트랜잭션 아이디 |
| 86 | `cpntId` | 접점아이디 | 10 | M |  | 접점아이디 |
| 87 | `indvLoInfoPrvAgreeYn` | 개인위치정보제공동의여부 | 1 | O |  | 개인위치정보 제3자 제공 동의 미입력시 미동의로 연동 Y:동의 N:미동의 *대상사업자 엠모바일(KIS). 이외 사업자 입력 시 미동의로 연동 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | O |  | M-Platform 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `rslt` | 처리 결과 | 1 | M |  | S: 접수정상, F:접수에러 |
| 3 | `rsltMsg` | 처리 결과 메시지 |  | O |  | 처리결과 에러시 메시지 |

### NU1 — 번호조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstSvcNoService` |
| Operation Name | `inqrOsstSvcNoInfo` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST오더번호 | 14 | M |  | 처리중인 오더 번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `asgnAgncId` | 할당 대리점 ID | 7 | O |  | 각 MVNO사의 대표 대리점 ID |
| 3 | `asgnAgncYn` | 할당번호조회여부 | 1 | C |  | 대리점할당번호 조회 필요 시 "Y" 수록 |
| 4 | `cntryCd` | 국가코드 | 3 | M |  | * 코드정의서 참조(NATIONNALITY_CD) |
| 5 | `custNo` | 고객번호 | 20 | M |  | KT영업전산 내 고객번호 * 개통 사전체크 프로세스에서 취득한 custId |
| 6 | `inqrBase` | 조회페이지 | 5 | M |  | 예시) "5" 수록 시 조회 결과의 6번째 번호부터 inqrCascnt(조회카운트)만큼 번호가 리스트로 응답 |
| 7 | `inqrCascnt` | 조회카운트 | 5 | M |  | 최대 10건 |
| 8 | `nowSvcIndCd` | 2G,3G구분 | 2 | M |  | 03 고정 |
| 9 | `searchGubun` | 조회구분 코드 | 1 | M |  | 1 : 예약번호 조회 2 : 희망전화번호 3 : Aging 전화번호 |
| 10 | `arPrGubun` | 예약/선호번호 구분코드 | 2 | C |  | searchGubun(조회구분코드) = 1 인 경우 필수 고정 값 입력 : AR |
| 11 | `tlphNoChrcCd` | 전화번호특성코드 | 3 | M |  | 고정 값 입력 : GEN |
| 12 | `tlphNoIndCd` | 전화번호구분코드 | 3 | M |  | 고정 값 입력 : 01 |
| 13 | `tlphNoPtrn` | 번호조회패턴 | 11 | M |  | 희망하는 뒷 4자리를 기재하여 조회 예시) 010____5678 [Aging번호 조회] 전체번호 기재 예시) 01056781234 [M2M번호 조회]  예시)012-21__-____ |
| 14 | `tlphNoUseCd` | 번호사용용도코드 | 3 | M |  | 일반 : R 선불 : I |
| 15 | `tlphNoUseMntCd` | 번호사용상세사유코드 | 3 | C |  | tlphNoUseCd(번호사용용도코드) = I 인 경우 필수 고정 값 입력 : FUK |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `svcNoList` | tlphNoStatCd | 전화번호 상태코드 | 2 | M |  |
| 14 | `lastPageYn` |  | 마지막 페이지 여부 |  |  |  |

### NU2 — 번호예약/취소

| 항목 | 내용 |
|---|---|
| Service Name | `OsstSvcNoService` |
| Operation Name | `resvOsstTlphNo` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST오더번호 | 14 | M |  | 처리중인 오더 번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `gubun` | 업무구분코드 | 11 | M |  | RSV : 예약 RRS : 예약취소 |
| 3 | `tlphNo` | 전화번호 | 11 | M | Y |  |
| 4 | `custNo` | 고객번호 | 20 | M |  |  |
| 7 | `tlphNoStatChngRsnCd` | 전화번호상태변경사유코드 | 3 | M |  | RSV : 예약 RRS : 예약취소 |
| 8 | `tlphNoStatCd` | 전화번호상태코드 | 2 | M |  | tlphNoStatChngRsnCd = RSV의 경우 AR tlphNoStatChngRsnCd = RRS의 경우 AA |
| 9 | `custTypeCd` | 고객유형코드 | 1 | M |  | * 코드정의서 참조(CUST_TYPE_CD) |
| 10 | `nowSvcIndCd` | 현서비스구분코드 | 2 | M |  | 고정 값 입력 : 03 |
| 13 | `encdTlphNo` | 암호화전화번호 | 24 | C |  | NU1(번호 조회)를 통해 취득한 encdTlphNo |
| 14 | `mpngTlphNoYn` | 매핑전화번호여부 | 1 | O |  | 미사용 컬럼 |
| 15 | `asgnagncId` | 할당대리점아이디 | 7 | O |  | 각 MVNO사의 대표 대리점 ID |

### OP0 — 개통 및 수납

| 항목 | 내용 |
|---|---|
| Service Name | `OsstSvcPrcService / OsstNpSvcPrcService` |
| Operation Name | `osstOpenPrc / osstNpOpenPrc` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `custNo` | 고객번호 | 9 | M |  | * 개통 사전체크 프로세스에서 취득한 custId |
| 3 | `billAcntNo` | 청구계정번호 | 11 | O |  | 고객이 보유한 기존 청구계정에 합산청구 시 기재 |
| 4 | `rqsshtPprfrmCd` | 청구서양식코드 | 2 | C |  | 신규 청구계정 생성(billAcntNo != null) 시 필수 LX : 우편 LC : 우편 + 이메일 CB : 이메일 MB : 모바일MMS * 코드정의서 참조(RQSSHT_PPRFRM_CD) |
| 5 | `rqsshtTlphNo` | 청구서 발송 전화번호 | 11 | C | Y | 신규 청구계정 생성(billAcntNo != null) && 청구서양식코드(rqsshtPprfrmCd)가 MMS 인 경우 필수 * 동일사업자 동일명의 전화번호(개통 신청 전화번호 포함) 기재 |
| 6 | `rqsshtEmlAdrsNm` | 청구서이메일주소명 | 100 | C | Y | 신규 청구계정 생성(billAcntNo != null) && 청구서양식코드(rqsshtPprfrmCd)가 LC 또는 CB인 경우 필수 * 이메일 입력 시 보안메일, 이벤트수신여부 자동 셋팅('Y') |
| 7 | `billZipNo` | 청구우편번호 | 6 | C |  | 신규 청구계정 생성(billAcntNo != null) 시 필수 |
| 8 | `billFndtCntplcSbst` | 청구지 주소 | 200 | C | Y | 신규 청구계정 생성(billAcntNo != null) 시 필수 * 신수조 입력 |
| 9 | `billMntCntplcSbst` | 청구지 주소 - 상세 | 100 | C | Y | 신규 청구계정 생성(billAcntNo != null) 시 필수 * 신수조 입력 |
| 10 | `blpymMthdCd` | 납부방법코드 | 1 | C |  | 신규 청구계정 생성(billAcntNo != null) 시 필수 C: 신용카드, D: 계좌자동이체, R: 지로 |
| 11 | `duedatDateIndCd` | 납기일자구분코드 | 2 | C |  | 신규 청구계정 생성(billAcntNo != null) 시 필수 납부방법코드(blpymMthdCd)가 D인 경우 27, 99 불가 납부방법코드(blpymMthdCd)가 R인 경우 99 불가 *코드정의서 참조(DUEDAT_DATE_IND_CD) |
| 12 | `crdtCardExprDate` | 신용카드만기일자 | 6 | C |  | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 인 경우 필수 YYYYMM 형식으로 입력 |
| 13 | `crdtCardKindCd` | 신용카드종류코드 | 2 | C |  | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 인 경우 필수 *코드정의서 참조(CRDT_CARD_KIND_CD) |
| 14 | `bankCd` | 은행코드 | 7 | C |  | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 D 인 경우 필수 *코드정의서 참조(BANK_CD) |
| 15 | `blpymMthdIdntNo` | 납부방법식별번호 | 20 | C | Y | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 필수 카드번호 또는 계좌번호 기재 |
| 16 | `blpymCustNm` | 납부고객명 | 60 | O | Y | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 기재 *미 입력 시 사전체크에 입력된 정보로 사용 |
| 17 | `blpymCustIdntNo` | 납부고객식별번호 | 20 | O | Y | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 기재 *미 입력 시 사전체크에 입력된 정보로 사용 납부고객 유형이 개인인 경우 생년월일(yyyyMMdd) 입력 법인이나 공공기관의 경우 사업자번호 입력 |
| 18 | `blpymMthdIdntNoHideYn` | 납부방법식별번호숨김여부 | 1 | O |  | 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 기재  Y:숨김, N:노출 |
| 19 | `bankSkipYn` | 은행건너뛰기여부 | 1 | O |  | 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 기재  Y: 건너뛰기, N:건너뛰지않음 * 미사용 컬럼 |
| 20 | `agreIndCd` | 동의자료코드 | 2 | C |  | 납부방법코드(blpymMthdCd)가 D 인 경우 필수 01: 서면, 02: 공인인증, 03:일반인증, 04: 녹취, 05:ARS |
| 21 | `myslAthnTypeCd` | 본인인증타입코드 | 2 | C |  | 동의자료코드(agreIndCd)가 03 인 경우 필수  01: SMS, 02: iPin, 03: 신용카드, 04: 범용공인인증 |
| 22 | `billAtchExclYn` | 청구서 동봉물 첨부제외여부 | 1 | O |  | Y:동봉물 제외, N:동봉물 포함 |
| 23 | `rqsshtTlphNoHideYn` | 청구서 전화번호 숨김여부 | 1 | O |  | Y:숨김, N:노출 |
| 24 | `rqsshtDsptYn` | 청구서발송여부 | 1 | O |  | Y: 발송안함, N:발송 |
| 25 | `enclBillTrmnYn` | 동봉 청구 해지여부 | 1 | O |  | Y:동봉청구해지, N:해지안함 |
| 26 | `realUseCustNm` | 실사용고객명 | 60 | C | Y | 법인,공공기관인 경우 필수  - 실사용자명 |
| 27 | `mngmAgncId` | 관리대리점아이디 | 10 | M |  |  |
| 28 | `cntpntCd` | 접점코드 | 10 | M |  |  |
| 29 | `slsPrsnId` | 판매자아이디 | 15 | O |  |  |
| 30 | `iccId` | ICC아이디 | 19 | C | Y | USIM ICC_ID. 실물유심으로 개통시에 필수(eSIM사용==N일 경우 필수) eSIM사용 == Y일 경우 NULL 로 연동 |
| 31 | `eUiccId` | EID | 40 | C | Y | eSIM용. eSIM사용==Y일 경우 필수. 실물유심일경우 NULL연동 |
| 32 | `intmMdlId` | 기기모델아이디 | 8 | C |  | USIM단독개통의 경우 입력 불필요. eSIM사용 단말일 경우 필수 |
| 33 | `intmSrlNo` | 기기일련번호 | 19 | C |  | USIM단독개통의 경우 입력 불필요. eSIM사용 단말일 경우 필수 |
| 34 | `eSimOpenYn` | eSIM개통여부 | 1 | C |  | Y:eSIM사용, N:eSIM미사용(default) |
| 35 | `usimOpenYn` | 유심개통여부 | 1 | M |  | Y:USIM단독개통, N:default  (실물유심사용시에만 유효. eSIM은 단독개통 없음) |
| 36 | `motliSvcNo` | 모회선전화번호 | 11 | C | Y | 애플워치 개통시 필수. 연결할 모회선 전화번호. (원넘버부가 자동가입) |
| 37 | `spclSlsNo` | 특별판매번호 | 5 | M |  | 판매정책번호 |
| 38 | `agntRltnCd` | 대리인관계코드 | 2 | C |  | 법인, 미성년인 경우 필수 03: 법정대리인(미성년일 경우), 04: 위탁대리인, 06: 일회성대리인 |
| 39 | `agntBrthDate` | 대리인생일일자 | 8 | C | Y | 법인, 미성년인 경우 필수 |
| 40 | `agntCustNm` | 대리인고객명 | 60 | C | Y | 법인, 미성년인 경우 필수 |
| 41 | `homeTlphNo` | 자택전화번호 | 12 | C | Y | 법인인 경우 필수 |
| 42 | `wrkplcTlphNo` | 직장전화번호 | 12 | O | Y |  |
| 43 | `prttlpNo` | 이동전화번호 | 11 | C | Y |  |
| 44 | `prdcCd` | 상품코드 | 9 | M |  | 상품타입코드가 요금제(P)일 경우 필수  - 가입 하려는 상품 코드  - 상품타입코드(prdcTypeCd) 와 함께 Multi 로 연동 (샘플참조) |
| 45 | `prdcTypecd` | 상품타입코드 | 1 | M |  | P: 요금제, R: 부가서비스  - 상품코드(prdcCd) 와 함께 Multi 로 연동 (샘플참조) |
| 46 | `ftrNewParam` | 상품 파람 | 1 | C |  |  |
| 47 | `spnsDscnTypeCd` | 스폰서할인유형코드 | 2 | C |  | MVNO 스폰서2(=단통법시행후 생성 스폰서)의 스폰서 지원금 유형코드 KD : Basic Course, PM :단말지원(요금할인) *코드정의서 참조(SPNS_DSCN_TYPE_CD) |
| 48 | `agncSupotAmnt` | 대리점지원금액 | 15 | C |  |  |
| 49 | `enggMnthCnt` | 약정개월수 | 2 | C |  | 스폰서의 경우 필수 |
| 50 | `hndsetInstAmnt` | 단말기분납금액 | 15 | C |  | 단말할부 시 필수 (단위: 100원) |
| 51 | `hndsetPrpyAmnt` | 단말기선납금액 | 15 | C |  | 단말할부시 선납금액 존재 할 경우 필수  (단말가격 - 분납가격) |
| 52 | `instMnthCnt` | 분납개월수 | 2 | C |  | 단말할부 시 필수 |
| 53 | `usimPymnMthdCd` | USIM 수납방법코드 | 1 | M |  | R:즉납, B:후청구, N:비구매 *코드정의서 참조(수납방법코드) *eSIM개통시 즉납불가 |
| 54 | `sbscstPymnMthdCd` | 가입비 수납방법코드 | 1 | M |  | R:즉납, I:할부, P:면제 *코드정의서 참조(수납방법코드) |
| 55 | `sbscstImpsExmpRsnCd` | 가입비면제사유코드 | 2 | C |  | 가입비수납방법코드가 면제인 경우 필수  면제가 아닌 경우 Null로 연동  면제인 경우 '37'로 수록 |
| 56 | `bondPrsrFeePymnMthdCd` | 채권보전료수납방법코드 | 1 | C |  | 채권보전료를 납부 시 필수  R:즉납, B:익월청구 *코드정의서 참조(수납방법코드) |
| 57 | `tlphNo` | 전화번호 | 11 | M | Y | 실제 개통 전화번호 번호이동가입시에는 번호이동전화번호 |
| 58 | `sbscPrtlstRcvEmlAdrsNm` | 가입내역서수신이메일주소명 | 100 | O | Y |  |
| 59 | `npFeePayMethCd` | 번호이동수수료 수납방법코드 | 1 | C |  | 번호이동가입인 경우 필수 즉시납부(N), 후청구(Y) |
| 60 | `npBillMethCd` | 타사미청구금액 청구방법코드 | 1 | C |  | 번호이동가입인 경우 필수 즉시납부(N), 후청구(Y), 전사업자후청구(X) 전사업자 혹은 후사업자의 계약용도가 선불인경우는 전사업자후청구(X)만 가능 |
| 61 | `npHndsetInstaDuratDivCd` | 번호이동단말기분납지속구분코드 | 1 | C |  | 번호이동가입인 경우 필수 완납(1), 분납지속(LMS미청구)(2), 분납지속(LMS청구)(3)  타사 단말할부금 = 0  후불 -> 선불 일때, "2"(분납지속 LMS미청구)만 가능  ESLE,      "1"(완납) 만 가능  타사 단말할부금 > 0  후불 -> 후불 일때, 선택가능(1,2,3)  ELSE,     "2"(분납지속 LMS미청구)만 가능 |
| 62 | `rfundNpBankCd` | 번호이동환불은행코드 | 3 | O |  | *코드정의서참조 (CW_COMN_CD_BAS.CD_GROUP_ENG_NM = 'NP_BANK_CD') |
| 63 | `rfundBankBnkacnNo` | 번호이동환불계좌번호 | 20 | O | Y |  |
| 64 | `npTotRmnyAmt` | 번호이동총수납금액 | 15 | C |  | 번호이동가입인 경우 필수 납부금액이 없는 경우에는 0 |
| 65 | `npCashRmnyAmt` | 번호이동현금수납액 | 15 | C |  | 번호이동가입인 경우 필수 납부금액이 없는 경우에는 0 |
| 66 | `npCcardRmnyAmt` | 번호이동카드수납액 | 15 | C |  | 번호이동수납방법이 카드인 경우 필수 납부금액이 없는 경우에는 0 |
| 67 | `npRmnyMethCd` | 번호이동수납방법코드 | 2 | C |  | 번호이동가입시 납부금액이 있는 경우 현금(01), 현금+신용카드(03), 신용카드(02) |
| 68 | `npHndsetInstaLmsTlphNo` | 번호이동단말기분납문자명세서전화번호 | 11 | C | Y | 번호이동가입시 단말기분납지속(LMS청구)인 경우 필수 |
| 69 | `npCcardNo` | 번호이동카드번호 | 16 | C | Y | 번호이동수납방법이 카드인 경우 필수 |
| 70 | `npCcardExpirYm` | 번호이동카드유효기간(만기년월) | 4 | C |  | 번호이동수납방법이 카드인 경우 필수 |
| 71 | `npInslMonsNum` | 번호이동할부개월수 | 2 | C |  | 번호이동수납방법이 카드인 경우 필수 |
| 72 | `npCcardSnctTypeCd` | 번호이동결제유형코드 | 1 | C |  | 번호이동수납방법이 카드인 경우 필수 자동입력(2)/입력확인(1) |
| 73 | `npCcaardOwnrIdfyNo` | 번호이동카드명의인식별번호 | 10 | C | Y | 번호이동수납방법이 카드인 경우 필수 생년월일/사업자번호 |
| 74 | `npSgntInfo` | 번호이동서명정보 |  | C |  | 번호이동수납방법이 카드인 경우 필수 |
| 75 | `atmSeqNo` | atm일련번호 | 9 | C |  | 효성에서 ATM기기에서 가입시 ATM기기 일련번호 *MVNO사업자 무관 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rslt` | 처리 결과 | 1 | M |  | S: 접수정상, F:접수에러 |
| 2 | `rsltMsg` | 처리 결과 메시지 |  |  |  | 처리 결과 메시지 |

### NP1 — 번호이동 사전동의 요청

| 항목 | 내용 |
|---|---|
| Service Name | `OsstNpSvcPrcService` |
| Operation Name | `osstNpBfacAgree` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `npTlphNo` | 번호이동 전화번호 | 11 | M | Y |  |
| 2 | `bchngNpCommCmpnCd` | 변경전번호이동사업자코드 | 3 | M |  | * 코드정의서 참조(NP_COMM_CMPN_CD) |
| 3 | `slsCmpnCd` | 판매회사코드 | 3 | M |  |  |
| 4 | `custTypeCd` | 고객유형코드 | 2 | M |  | * 코드정의서 참조(CUST_TYPE_CD) |
| 5 | `indvBizrYn` | 개인사업자 여부 | 1 | M |  | Y : 개인사업자 *Default : N |
| 6 | `custIdntNoIndCd` | 고객식별번호구분코드 | 2 | M |  | * 코드정의서 참조(CUST_IDNT_NO_IND_CD) |
| 7 | `custIdntNo` | 고객식별번호 | 20 | C | Y |  |
| 8 | `crprNo` | 법인번호 | 13 | C |  | 법인인 경우 필수 |
| 9 | `custNm` | 고객명 | 60 | M | Y |  |

### NP2 — 납부주장 요청

| 항목 | 내용 |
|---|---|
| Service Name | `OsstNpSvcPrcService` |
| Operation Name | `osstNpPayAsert` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST오더번호 | 14 | M |  | 처리중인 오더 번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `slsCmpnCd` | 판매회사코드 | 3 | M |  |  |
| 3 | `npTlphNo` | 번호이동 전화번호 | 11 | M | Y |  |
| 4 | `payAsertDt` | 납부주장일자 | 8 | M |  | 미납금의 납부일자 |
| 5 | `payAsertAmt` | 납부주장금액 | 15 | M |  | 고객이 납부한 미납금액 |
| 6 | `payMethCd` | 납부방법코드 | 5 | M |  | "은행자동이체", "카드자동이체", "지로", "무통장입금", "KT청구" 5개 항목을 1 or 0으로 구분하여 5자리로 수록 ex. 은행자동이체+지로인 경우 10100      카드자동이체+무통장입금인 경우 01010 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | O |  | 처리중인 오더 번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `rslt` | 납부주장 결과 | 1 | M |  | S : 성공, F : 실패 |
| 3 | `rsltMsg` | 납부주장 결과 메시지 |  | O |  | 납부주장 실패시 실패사유 메시지 |

### NP3 — 사전동의 결과조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstNpSvcPrcService` |
| Operation Name | `osstNpBfacAgreeRpyRetv` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `telNo` | 전화번호 | 11 | M | Y | 번호이동할 전화번호 |
| 2 | `bchngNpCommCmpnCd` | 변경전번호이동사업자코드 | 3 | M |  | 번호이동가입 필수 *코드정의서 참조 (NP_COMM_CMPN_CD) |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rsltCd` | 결과 코드 | 1 | M |  | S: 성공, F: 실패, Y: 사전동의 요청 진행 중 |
| 2 | `rsltMsg` | 결과 메시지 | 100 | O |  | 처리 실패 시 KT영업전산에서 응답받은 메시지 |

### ST1 — 상태조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstSvcPrcService` |
| Operation Name | `osstPrcSch` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호  ex) YYYYMMDD+seq6자리 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mvnoOrdNo` | MVNO 오더 번호 | 14 | M |  | MVNO 에서 생성한 오더번호 (ex. YYYYMMDD+Seq6자리) |

### CP0 — 지능망 직권해지

| 항목 | 내용 |
|---|---|
| Service Name | `OsstCanSvcPrcService` |
| Operation Name | `osstCanPps2Prc` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  | OSST 에서 생성한 오더번호   ex) YYYYMMDD+seq6자리 |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | O |  | OSST 에서 생성한 오더번호   ex) YYYYMMDD+seq6자리 |
| 2 | `rslt` | 처리 결과 | 1 | M |  | S: 접수정상, F:접수에러 |
| 3 | `rsltMsg` | 처리 결과 메시지 |  | O |  | 처리결과 에러시 메시지 |

### EP0 — 해지

| 항목 | 내용 |
|---|---|
| Service Name | `OsstCanSvcPrcService` |
| Operation Name | `osstCanPrc / osstCanM2mPrc` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `cntplcNo` | 연락받을 회선번호 | 11 | M | Y | 해지처리하는 번호와 동일한 연락처 번호 입력 불가. (단, 14일 이내 번호이동개통취소 데이터는 허용) ** 연락처 번호 유효성 확인 - 시작번호: 010,지역번호,070만 허용 - 중간번호: 중간번호가 0혹은 1로 시작하는 번호일 시, 처리 불가. 예) 0100xxxxxxx 혹은 0101xxxxxxx -> 불가 - 연락처 길이: 무선연락처의 경우, 중간번호(4)끝번호(4) / 유선연락처의 경우, 중간번호+끝번호(7 or 8) - 골드번호 사용 불가 |
| 5 | `itgOderWhyCd` | 해지사유코드 | 4 | M |  | *코드표 참조 |
| 6 | `aftmnIncInCd` | 해지후성향코드 | 2 | M |  | *코드표 참조 |
| 7 | `apyRelTypeCd` | 고객접촉매체코드 | 2 | M |  | *코드표 참조 |
| 8 | `custTchMediCd` | 신청관계유형코드 | 2 | M |  | *코드표 참조 |
| 9 | `smsRcvYn` | SMS수신여부 | 1 | O |  | SMS 수신여부(Y/N) |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호  ex) YYYYMMDD+seq6자리 |
| 2 | `rslt` | 처리 결과 | 1 | M |  | S: 접수정상, F:접수에러 |
| 3 | `rsltMsg` | 처리 결과 메시지 |  | O |  | 처리결과 에러시 메시지 |

### EP0(M2M) — M2M 해지

| 항목 | 내용 |
|---|---|
| Service Name | `OsstCanSvcPrcService` |
| Operation Name | `osstCanM2mPrc` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M | Y |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `cntplcNo` | 연락받을 회선번호 | 11 | M | Y | 해지처리하는 번호와 동일한 연락처 번호 입력 불가. (단, 14일 이내 번호이동개통취소 데이터는 허용) ** 연락처 번호 유효성 확인 - 시작번호: 010,지역번호,070만 허용 - 중간번호: 중간번호가 0혹은 1로 시작하는 번호일 시, 처리 불가. 예) 0100xxxxxxx 혹은 0101xxxxxxx -> 불가 - 연락처 길이: 무선연락처의 경우, 중간번호(4)끝번호(4) / 유선연락처의 경우, 중간번호+끝번호(7 or 8) - 골드번호 사용 불가 |
| 5 | `itgOderWhyCd` | 해지 사유코드 | 4 | M |  | *코드표 참조 |
| 6 | `aftmnIncInCd` | 해지 후 성향코드 | 2 | M |  | *코드표 참조 |
| 7 | `apyRelTypeCd` | 고객접촉 매체코드 | 2 | M |  | *코드표 참조 |
| 8 | `custTchMediCd` | 신청관계 유형코드 | 2 | M |  | *코드표 참조 |
| 9 | `smsRcvYn` | SMS 수신여부 | 1 | O |  | SMS 수신여부(Y/N) |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호   ex) YYYYMMDD+seq6자리 |
| 2 | `rslt` | 처리 결과 | 1 | M |  | S: 접수정상, F:접수에러 |
| 3 | `rsltMsg` | 처리 결과 메시지 |  | O |  | 처리결과 에러시 메시지 |

### AP0 — 미납 직권해지처리

| 항목 | 내용 |
|---|---|
| Service Name | `OsstAthrtCanService` |
| Operation Name | `trtAthrtCan` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `athrtTrtmTypeCd` | 해지사유코드 | 4 | M |  | NP01 : 미납에 의한 직권해지 NP04 : 직권해지-고액연체 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rsltCd` | 처리 결과 | 1 | M |  | S: 접수정상, F:접수에러 |
| 2 | `rsltMsg` | 처리 결과 메시지 |  | O |  | 처리결과 에러시 메시지 |

### ET0 — 업무시간외 개통접수건 사업자 전송

| 항목 | 내용 |
|---|---|
| Service Name | `OsstSvcPrcService` |
| Operation Name | `osstDataMailSend` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `slsCmpnCd` | 판매회사코드 | 3 | M |  |  |
| 2 | `custCntplcNo` | 고객 연락처 | 11 | M | Y | 고객 연락처 |
| 3 | `custNm` | 고객명 | 60 | M | Y | 고객명 |
| 4 | `actRqstTime` | 개통 요청 접수 시간 | 14 | M |  | 개통 요청 접수 시간 |
| 5 | `atmSeqNo` | atm일련번호 | 9 | M |  | 효성에서 ATM기기에서 가입시 ATM기기 일련번호  - 효성 ATM 기기만 필수값 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `slsCmpnCd` | 판매회사코드 | 3 | M |  |  |
| 2 | `custCntplcNo` | 고객 연락처 | 11 | M | Y | 고객 연락처 |
| 3 | `custNm` | 고객명 | 60 | M | Y | 고객명 |
| 4 | `actRqstTime` | 개통 요청 접수 시간 | 14 | M |  | 개통 요청 접수 시간 |
| 5 | `atmSeqNo` | atm일련번호 | 9 | M |  | 효성에서 ATM기기에서 가입시 ATM기기 일련번호  - 효성 ATM 기기만 필수값 |

### ET1 — 휴일여부 조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstSvcPrcService` |
| Operation Name | `inqrOsstActHolyDay` |

### HC0 — 기기변경/기기변경취소 사전체크

| 항목 | 내용 |
|---|---|
| Service Name | `OsstIcgMainPrcsService / OsstIcgCnclMainPrcsService` |
| Operation Name | `icgPreChk(기기변경 사전체크)  OsstIcgMainPrcsService / icgCnclMainPrcsPreChk(기기변경취소 사전체크)  OsstIcgCnclMainPrcsService` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mvnoOrdNo` | MVNO 오더 번호 | 14 |  |  | 우수기변:M / 일반기변:M / 기변취소:M | MVNO에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `custNo` | 고객번호 | 9 |  |  | 우수기변:M / 일반기변:M / 기변취소:M |
| 3 | `svcContId` | 서비스계약번호 | 15 |  |  | 우수기변:M / 일반기변:M / 기변취소:M |
| 4 | `tlphNo` | 전화번호 | 11 |  | Y | 우수기변:M / 일반기변:M / 기변취소:M | 실제 기기변경 전화번호 |
| 5 | `oderTypeCd` | 오더유형코드 | 3 |  |  | 우수기변:M / 일반기변:M / 기변취소:M | H01 : 우수고객기변 H11 : 일반기변기기변경 H07 : 기기변경취소 |
| 6 | `instYn` | 단말기할부여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 Y:할부, N:즉납  * 오더유형코드가 우수고객기변(H01) 만 해당 그 외 Y이면 오류 |
| 7 | `indvInfoInerPrcuseAgreYn` | 개인정보내부활용동의여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 개인정보수집및 활용에 동의 (반드시 Y로 입력되어야 함) |
| 8 | `crdtInfoAgreYn` | 신용정보동의여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 신용정보조회 및 이용에 대한 동의 (반드시 Y로 입력되어야 함) |
| 9 | `spamPrvdAgreYn` | 스팸제공동의여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 스팸정보를 KISA 제공 동의 Y:동의 N:미동의 |
| 10 | `custIdntNoIndCd` | 고객식별번호구분코드 | 2 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 * 코드정의서 참조 |
| 11 | `custIdntNo` | 고객식별번호 | 20 |  | Y | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 |
| 12 | `crprNo` | 법인번호 | 13 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 법인인 경우 필수 |
| 13 | `custNm` | 고객명 | 60 |  | Y | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 |
| 14 | `rsdcrtIssuDate` | 주민등록증발급일자 | 8 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 개인 내국인 : 주민등록증, 장애인등록증, 국가유공자증, 운전면허증 발급일자 개인 외국인 : 외국인등록증 발급일자 법인, 공공기관 : 사업자 교부일자 |
| 15 | `nativeRlnamAthnEvdnPprCd` | 실명인증증빙서류코드 | 5 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 * 코드정의서 참조 |
| 16 | `lcnsNo` | 면허번호 | 20 |  | Y | 우수기변:C | 오더유형코드가 'H01'이고 실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 |
| 17 | `lcnsRgnCd` | 면허지역코드 | 2 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 *코드정의서 참조 |
| 18 | `mrtrPrsnNo` | 유공자번호 | 10 |  | Y | 우수기변:C | 오더유형코드가 'H01'이고 실명인증증빙서류코드가 MERIT(국가유공자)인 경우 필수 |
| 19 | `nationalityCd` | 국적코드 | 3 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 고객식별구분코드가 외국인인 경우 필수 *코드정의서 참조 |
| 20 | `fornBrthDate` | 외국인생일일자 | 8 |  | Y | 우수기변:C | 오더유형코드가 'H01'이고 실명인증증빙서류코드가 PPORT(외국인여권)인 경우 필수 * 형식 : YYYYMMDD |
| 21 | `athnRqstcustCntplcNo` | 인증요청고객연락처번호 | 12 |  | Y | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 |
| 22 | `myslAgreYn` | 본인동의여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 Y:동의, N:미동의 |
| 23 | `agntRltnCd` | 대리인관계코드 | 2 |  |  | 우수기변:C / 일반기변:C / 기변취소:C | 오더유형코드가 'H01','H11','H07'이고 법인인 경우 필수 (04: 위탁대리인  만 수록) *코드정의서 참조 |
| 24 | `agntBrthDate` | 대리인생년월일 | 6 |  | Y | 우수기변:C / 일반기변:C / 기변취소:C | 오더유형코드가 'H01','H11','H07'이고 법인인 경우 필수(YYMMDD) |
| 25 | `intmModelId` | 기기모델아이디 | 11 |  |  | 우수기변:M / 일반기변:M | 오더유형코드가 'H01','H11'인 경우 필수 |
| 26 | `intmSrlNo` | 기기일련번호 | 30 |  |  | 우수기변:M / 일반기변:M | 오더유형코드가 'H01','H11'인 경우 필수 |
| 27 | `prdcCd` | 상품코드 | 9 |  |  | 우수기변:C / 일반기변:C / 기변취소:C | 상품타입코드가 요금제(P)일 경우 필수  - 가입 하려는 상품 코드  - 상품타입코드(prdcTypeCd) 와 함께 Multi 로 연동 (샘플참조) |
| 28 | `prdcTypeCd` | 상품타입코드 | 1 |  |  | 우수기변:O / 일반기변:O / 기변취소:O | P: 요금제, R: 부가서비스  - 상품코드(prdcCd) 와 함께 Multi 로 연동 (샘플참조) |
| 29 | `iccId` | USIM 일련번호 | 30 |  | Y | 우수기변:C / 일반기변:C | 오더유형코드가 'H01','H11'인 경우 USIM변경시 필수 esimUseYn ='Y'일 경우 iccId NULL 연동 |
| 30 | `spclSlsNo` | 판매정책번호 | 9 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 |
| 31 | `spnsrTermTypeCd` | 스폰서약정 유형코드 | 3 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 KM1 : 알뜰폰스폰서 MC1 : 스마트스폰서(알뜰폰) MC2 : 스마트할인(알뜰폰) |
| 32 | `enggMnthCnt` | 약정 개월 수 | 2 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 스폰서 약정 개월 수 |
| 33 | `saleEngtOptnCd` | 할인 유형 코드 | 2 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 스폰서약정 유형코드가 KM1(알뜰폰스폰서)인 경우 KD : 베이직코스(공시지원금), PM : 요금할인(월25%할인) |
| 34 | `esimUseYn` | eSIM 사용여부 | 1 |  |  | 우수기변:C / 일반기변:C | 기변 단말의 eSIM 사용여부 (기변취소시 불필요) |
| 35 | `euiccId` | EID | 40 |  | Y | 우수기변:C / 일반기변:C | eSIM용. eSIM사용==Y일 경우 필수, 실물유심 사용일 경우 [euiccId NULL 연동] (기변취소시 불필요) |
| 36 | `myslfAthnYn` | 본인인증여부 | 1 |  |  | 우수기변:C | 본인인증여부 Y/N |
| 37 | `ipinCi` | 본인인증(CI) | 200 |  | Y | 우수기변:C | 본인인증 후 취득한 CI 값 (NICE) |
| 38 | `onlineAthnDivCd` | 본인인증 수단 | 2 |  |  | 우수기변:C | 신용카드, 네이버, 카카오 등 본인인증 수단  - 코드로 연동, 확정 후 제공 10 공인인증 20 신용카드인증 30 휴대폰인증 60 카카오페이인증 61 PASS계좌인증 70 네이버인증 71 기타인증(PAYCO, TOSS 등) 이 인증 외에 다른 인증수단은 규제기관(KAIT)의 확인 필요 |
| 39 | `photoAthnTxnSeq` | 사진인증내역일련번호 | 15 |  |  | 우수기변:C | 금융스캐너 사용시 KAIT 사진진위인증(FS7) 요청 후 성공/스킵한 사진인증내역일련번호 |
| 40 | `fathTransacId` | 안면인증 트랜잭션 아이디 | 30 |  |  | 우수기변:C | 안면인증 트랜잭션 아이디 |
| 41 | `cpntId` | 접점아이디 | 10 |  |  | 우수기변:M | 접점아이디 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | O |  | M-Platform 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `rslt` | 처리 결과 | 4 | M |  | 0000:성공, 그외 오류코드 |
| 3 | `rsltMsg` | 처리 결과 메시지 | 200 | O |  | 처리결과 메시지 |

### HP0 — 기기변경/기기변경취소 처리

| 항목 | 내용 |
|---|---|
| Service Name | `OsstIcgMainPrcsService / OsstIcgCnclMainPrcsService` |
| Operation Name | `icgPrcTrt(기기변경) / icgCnclMainPrcs(기기변경취소)` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 |  |  | 우수기변:M / 일반기변:M / 기변취소:M | OSST 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `custNo` | 고객번호 | 9 |  |  | 우수기변:M / 일반기변:M / 기변취소:M |
| 3 | `tlphNo` | 전화번호 | 11 |  | Y | 우수기변:M / 일반기변:M / 기변취소:M | 실제 기기변경 전화번호 |
| 4 | `svcContId` | 서비스계약번호 | 15 |  |  | 우수기변:M / 일반기변:M / 기변취소:M |
| 5 | `oderTypeCd` | 오더유형코드 | 3 |  |  | 우수기변:M / 일반기변:M / 기변취소:M | H01 : 우수고객기변 H11 : 일반기변기기변경 H07 : 기기변경취소 |
| 6 | `instYn` | 단말기할부여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 Y : 할부, N : 즉납  * 우수고객기변(H01) 만 해당 그 외 Y이면 오류 |
| 7 | `hndsetInstAmnt` | 단말기분납금액 | 15 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 단말기할부여부가 'Y'인 경우 필수 * 단위: 100원 |
| 8 | `hndsetPrpyAmnt` | 단말기선납금액 | 15 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 단말기할부여부가 'Y' 인 경우 선납금액 입력 선납금 = 단말가격 - 분납가격 |
| 9 | `instMnthCnt` | 단말기할부개월수 | 2 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 단말할부 시 필수 |
| 10 | `usimPymnMthdCd` | USIM 수납방법 코드 | 1 |  |  | 우수기변:M / 일반기변:M | 오더유형코드가 'H01','H11'인 경우 필수 B:후청구, N:비구매 *코드정의서 참조(수납방법코드) |
| 11 | `sbscPrtlstRcvEmlAdrsNm` | 가입내역서수신이메일주소명 | 100 |  | Y | 우수기변:O / 일반기변:O | 오더유형코드가 'H01','H11'인 경우 해당 미 입력 시 메일 발송이 안됨 |
| 12 | `cntpntCd` | 접점코드 | 10 |  |  | 우수기변:M / 일반기변:M | 오더유형코드가 'H01','H11'인 경우 필수 |
| 13 | `slsPrsnId` | 판매자아이디 | 15 |  |  | 우수기변:O | 오더유형코드가 'H01'인 경우 |
| 14 | `spnsrTermTypeCd` | 스폰서약정 유형코드 | 3 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 KM1 : 알뜰폰스폰서 MC1 : 스마트스폰서(알뜰폰) MC2 : 스마트할인(알뜰폰) |
| 15 | `saleEngtOptnCd` | 할인 유형 코드 | 2 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 스폰서약정 유형코드가 KM1(알뜰폰스폰서)인 경우 KD : 베이직코스(공시지원금), PM : 요금할인(월25%할인) |
| 16 | `agncSupotAmnt` | 대리점지원금 | 6 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 스폰서약정 유형코드가 KM1(알뜰폰스폰서)인 경우 KD : 베이직코스, TD : 심플코스,이면 필수 또는 0 |
| 17 | `enggMnthCnt` | 약정 개월 수 | 2 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 스폰서 약정 개월 수 |
| 18 | `engtGraceYn` | 약정유예 여부 | 1 |  |  | 우수기변:O | 오더유형코드가 'H01'인 경우 Y: 약정유예 , N: 약정유예 안함 미 입력 시 "N" 처리 됨 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rslt` | 처리 결과 | 4 | M |  | 0000:성공, 그외 오류코드 |
| 2 | `rsltMsg` | 처리 결과 메시지 | 200 |  |  | 처리 결과 메시지 |

### X27 — 일시정지가능여부조회

| 항목 | 내용 |
|---|---|
| Service Name | `MoscSuspenService` |
| Operation Name | `moscSuspenPosInfo` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `clntIp` | Client IP | 20 | M |  |  |
| 5 | `clntUsrId` | 사용자 User ID | 50 | M |  |  |
| 6 | `stopRsnCd` | 일시정지사유코드 | 3 | M |  | DBL3: 이상고액정지 MP02 : 불법사용이용정지 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `ctnStatus` | 계약상태 | 1 | M |  | A: 개통, S: 일시정지 |
| 2 | `rsltInd` | 일시정지상태 | 1 | O |  | Y: 가능, N: 이외불가 |
| 3 | `rsltMsg` | 일시정지메시지 | 100 | O |  |  |
| 4 | `insurMsg` | (보험성)안내메시지 | 100 | O |  |  |

### X28 — 일시정지해제가능여부조회

| 항목 | 내용 |
|---|---|
| Service Name | `MoscSuspenService` |
| Operation Name | `moscSuspenCnlPosInfo` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `clntIp` | Client IP | 20 | M |  |  |
| 5 | `clntUsrId` | 사용자 User ID | 50 | M |  |  |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rsltInd` | 일시정지 가능여부 | 1 | O |  | Y: 가능, N: 불가 |
| 2 | `rsltMsg` | 일시정지메시지 | 100 | O |  |  |
| 3 | `subStatusRsnCode` | 전화번호 상태사유코드 | 5 | O |  | AR02 : 군입대-발착신정지 AR06 : 군입대-장기발착신 CR01 : 고객요청-발신정지 CR02 : 고객요청-발착신정지 FS02 : 해외체류-발착신정지 FS06 : 해외체류-장기발착신 SNP1: 미납에 의한 일시정지 DBL3: 이상고액정지 MP02 : 불법사용이용정지 |
| 4 | `sndarvStatCd` | 발/착신구분코드 | 5 | O |  | 01: 발신정지, 02: 착신정지, 03: 발착신정지 |
| 5 | `rsnDesc` | 사유코드설명 | 100 | O |  | 전화번호상태사유코드+"-"+발착신상태코드 |

### X65 — 부정사용 일시정지신청

| 항목 | 내용 |
|---|---|
| Service Name | `MoscSuspenService` |
| Operation Name | `moscNegativeSuspenChg` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `clntIp` | Client IP | 20 | M |  |  |
| 5 | `clntUsrId` | 사용자 User ID | 50 | M |  |  |
| 6 | `reasonCode` | 발착신 상태코드 | 2 | M |  | 99: 정지없음, 01: 발신정지, 02: 착신정지, 03: 착발신정지 |
| 7 | `stopRsnCd` | 일시정지사유코드 | 3 | M |  | DBL3: 이상고액정지 MP02 : 불법사용이용정지 |
| 8 | `userMemo` | 사용자 메모 | 100 | O |  |  |
| 9 | `cpDateYn` | 일시정지 기간여부 | 1 | M |  | N : 즉시 이용정지 처리 |
| 10 | `spamMemoCd` | 스팸메모코드 | 4 | C |  | 일시정지사유코드: MP02 일 때 필수 - M009: 불법대부광고 - M010: 청소년 유해매체물(성매매 암시) - M011: 금융사기 - M014: 피싱사기 - M015: 통신사기(회선+단말차단-KAIT연동) - M016: 부정이용-과기부요청 |

### X66 — 부정사용 일시정지해제신청

| 항목 | 내용 |
|---|---|
| Service Name | `MoscSuspenService` |
| Operation Name | `moscNegativeSuspenCnlChg` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `clntIp` | Client IP | 20 | M |  |  |
| 5 | `clntUsrId` | 사용자 User ID | 50 | M |  |  |

### UC0 — 유심변경

| 항목 | 내용 |
|---|---|
| Service Name | `OsstUsimChgPrcService` |
| Operation Name | `usimChgPrc` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mvnoOrdNo` | MVNO 오더 번호 | 14 | M |  | MVNO에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `custNo` | 고객번호 | 9 | M |  |  |
| 3 | `tlphNo` | 전화번호 | 11 | M | Y | 실제 Sim변경 전화번호 |
| 4 | `svcContId` | 서비스계약번호 | 15 | M |  |  |
| 5 | `oderTypeCd` | 오더유형코드 | 3 | M |  | H38 : Sim변경 고정값 |
| 6 | `usimPymnMthdCd` | SIM 수납방법 코드 | 1 | M |  | R:즉납, B:후청구, N:비구매 *코드정의서 참조(수납방법코드) *해지된 USIM의 경우 비구매만 선택가능 *USIM변경 사유코드가 고장-사업자귀책이고 14일 이내 재변경인 경우 비구매만 선택 가능 *eSim의 경우 후청구(B) 고정 |
| 7 | `iccId` | SIM 일련번호 | 30 | C | Y | *eSim인 경우 해당 필드값 Null 로 연동 |
| 8 | `cntpntCd` | 접점코드 | 10 | M |  |  |
| 9 | `slsPrsnId` | 판매자아이디 | 15 | O |  |  |
| 10 | `usimChgRsnCd` | SIM변경 사유코드 | 2 | M |  | 37:일반 41:고장-고객귀책 42:고장-사업자귀책 33:분실 *eSim인 경우 해당 필드값 37 로 고정 |
| 14 | `custTchMediCd` | 신청방법 | 2 | M |  | 01 : 전화 02 : FAX  03 : 우편 04 : 창구방문 07 : E-MAIL 14 : 직원방문접수 20 : iPad 접수 21 : 스마트서식지 접수 MB : M-KOS 접수 22 : BPM 청약 23 : 유선전자신청서 EA : 이채널접수(유통망) EC : 채널접수(사이버) EF : 온라인서식지 |
| 15 | `apyRelTypeCd` | 신청자와의 관계 | 2 | M |  | 01 : 본인 11 : 대리인 12 : 대표자 05 : KT직원 06 : 유지보수업체직원 10 : 키맨 13 : 채권담당자 |
| 16 | `apyrNm` | 신청자명 | 100 | M |  |  |
| 17 | `apyrCntcNo` | 신청자 휴대폰번호 | 11 | M |  |  |
| 18 | `prdcCd` | 상품코드 | 9 | O |  | 상품타입코드가 요금제(P)일 경우 필수  - 가입 하려는 상품 코드  - 상품타입코드(prdcTypeCd) 와 함께 Multi 로 연동 (샘플참조) |
| 19 | `prdcTypeCd` | 상품타입코드 | 1 | O |  | P: 요금제, R: 부가서비스  - 상품코드(prdcCd) 와 함께 Multi 로 연동 (샘플참조) |
| 20 | `ftrNewParam` | 상품 부가 파람 | 500 | O |  | 상품 부가 파람 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | O |  | M-Platform 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `rsltCd` | 처리 결과 | 1 | M |  | S : 성공, F : 실패 |
| 3 | `rsltMsg` | 처리 결과 메시지 | 200 | O |  | 처리결과 메시지 |

### RC0 — 해지취소 사전체크

| 항목 | 내용 |
|---|---|
| Service Name | `OsstRclPrcsService` |
| Operation Name | `rclPreChk(해지취소사전체크)` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mvnoOrdNo` | MVNO 오더 번호 | 14 | M |  | MVNO에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `custId` | 고객ID | 11 | M |  |  |
| 3 | `svcContId` | 서비스계약ID | 20 | M |  |  |
| 4 | `tlphNo` | 전화번호 | 11 | M | Y |  |
| 5 | `rclTypeCd` | 해지취소유형코드 | 3 | M |  | 사전체크:RC0 |
| 6 | `rclWhyCd` | 해지취소사유코드 | 8 | M |  | *해지취소사유코드 참조 |
| 7 | `agntRltnCd` | 대리인관계코드 | 2 | C |  | 법인, 미성년인 경우 필수  03: 법정대리인(미성년일 경우), 04: 위탁대리인, 06: 일회성대리인 |
| 8 | `agntBrthDate` | 대리인생년월일 | 6 | C | Y | 법인 또는 미성년자 필수 |
| 9 | `iccId` | ICC아이디 | 19 | O | Y | USIM ICC_ID 실물유심 변경하여 해지복구 처리시에만 입력.  eSIM계약일 경우 NULL로 연동 |
| 10 | `usimPymnMthdCd` | USIM 수납방법코드 | 1 | O |  | R:즉납, B:후청구, N:비구매 *코드정의서 참조(수납방법코드) 실물유심 변경 복구 처리시에만 즉납/후청구 수납방법 입력.  eSIM계약일 경우 NULL로 연동 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호  ex) YYYYMMDD+seq6자리 |
| 2 | `rslt` | 처리 결과 | 1 | M |  | S: 접수정상, F:접수에러 |
| 3 | `rsltMsg` | 처리 결과 메시지 |  | O |  | 처리결과 메시지 (정상 또는 에러) |

### RP0 — 해지취소 처리

| 항목 | 내용 |
|---|---|
| Service Name | `OsstRclPrcsService` |
| Operation Name | `rclPrcsTrt(해지취소)` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호  ex) YYYYMMDD+seq6자리 |
| 2 | `custId` | 고객ID | 11 | M |  |  |
| 3 | `svcContId` | 서비스계약ID | 20 | M |  |  |
| 4 | `tlphNo` | 전화번호 | 11 | M | Y |  |
| 5 | `rclTypeCd` | 해지취소유형코드 | 3 | M |  | 해지취소처리:RP0 |
| 6 | `rclWhyCd` | 해지취소사유코드 | 8 | M |  | *해지취소사유코드 참조 |
| 7 | `agntRltnCd` | 대리인관계코드 | 2 | C |  | 법인, 미성년인 경우 필수  03: 법정대리인(미성년일 경우), 04: 위탁대리인, 06: 일회성대리인 |
| 8 | `agntBrthDate` | 대리인생년월일 | 6 | C | Y | 법인 또는 미성년자 필수 |
| 9 | `iccId` | ICC아이디 | 19 | O | Y | USIM ICC_ID 실물유심 변경하여 해지복구 처리시에만 입력.  eSIM계약일 경우 NULL로 연동 |
| 10 | `usimPymnMthdCd` | USIM 수납방법코드 | 1 | O |  | R:즉납, B:후청구, N:비구매 *코드정의서 참조(수납방법코드) 실물유심 변경하여 해지복구 처리시에만 입력.  eSIM계약일 경우 NULL로 연동 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호  ex) YYYYMMDD+seq6자리 |
| 2 | `rslt` | 처리 결과 | 1 | M |  | S: 접수정상, F:접수에러 |
| 3 | `rsltMsg` | 처리 결과 메시지 |  | O |  | 처리결과 메시지 (정상 또는 에러) |

### MC0 — 명의변경사전체크

| 항목 | 내용 |
|---|---|

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `baseInfo (작업기준정보)` | mvnoOrdNo | MVNO 오더 번호 |  | M | 우수기변:MVNO에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) / 일반기변:1 / 기변취소:osstOrdNo |
| 11 | `rcvCustInfo (양수인 고객정보)` | custTypeCd | 양수인 고객유형코드 |  | M | 우수기변:* 코드정의서 참조 |
| 87 | `rcvBillAcntInfo(양수인 청구계정정보)` | rqsshtPprfrmCd | 청구서양식코드 |  | M | 우수기변:청구계정번호가 없으면 필수
우편(LX), 우편+이메일(LC), 이메일(CB) 명세서 가능
*2018.03.01부터 사용
모바일MMS(MB) 명세서 가능
*코드정의서 참조 |
| 109 | `prdcList` | prdcCd | 상품코드 |  | M | 우수기변:요금제 코드 : 요금제 변경이 없을 경우 null
 - 변경하려는 요금제 코드 |

### MP0 — 명의변경

| 항목 | 내용 |
|---|---|

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `baseInfo` | osstOrdNo | OSST 오더 번호 |  | M | 우수기변:OSST 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) / 일반기변:1 / 기변취소:rslt |

### OF — 개통서식지등록

| 항목 | 내용 |
|---|---|
| Service Name | `OsstFrmPapIfSendService` |
| Operation Name | `osstFrmPapIfSend` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrderNo` | OSST오더번호 | 20 | M |  |  |
| 2 | `mvnoOrderNo` | MVNO오더번호 | 20 | M |  |  |
| 3 | `slsCmpnCd` | 판매회사코드 | 3 | M |  |  |
| 4 | `trTime` | 개통요청일시 | 20 | M |  |  |
| 5 | `trNo` | 거래고유번호 | 20 | M |  |  |
| 6 | `reqTelNo` | 개통전화번호 | 20 | M | Y |  |
| 7 | `niceCertKey` | CI값 | 88 | M | Y |  |
| 8 | `orderTypeCd` | 개통유형 | 3 | M |  | *코드정의서 참조(AC_ITG_ODER_TYPE_CD) - 신규개통 : NAC - 무선번호이동 : MNP |
| 9 | `mnpStatus` | 번호이동진행상태코드 | 2 | C |  | 번호이동시 필수 '01','번호이동사전동의요청','02','동의접수SOAP응답' '03','사전동의결과조회','04','사전동의결과SOAP응답' ''05','사전체크요청','06','사전체크SOAP응답' ,'07','사전체크TCP응답' ''08','납부주장요청','09','납부결과SOAP응답' ,'10','개통요청','11','개통SOAP응답' ,'12','개통TCP응답','13','완료' |
| 10 | `status` | 진행상태코드 | 2 | M |  | 01','사전체크요청','02','사전체크SOAP응답' ,'03','사전체크TCP응답' ,'04','번호조회/예약요청','05','번호예약응답' ,'06','개통요청','07','개통SOAP응답' ,'08','개통TCP응답','09','완료' |
| 11 | `custType` | 본인인증방식 | 10 | M |  | - 주민등록증 : REGID - 운전면허증: DRIVE - 장애인등록증 : HANDI - 국가유공자증 : MERIT - 외국인등록증 : FORGN |
| 12 | `myslAthnTypeCd` | 본인인증수단 | 2 | M |  | - 신용카드 : 01 - 범용인증서 : 02 - toss인증서 : 03 - 카카오인증서 : 04 - 네이버인증서 : 05 10 공인인증 20 신용카드인증 30 휴대폰인증 60 카카오페이인증 61 PASS계좌인증 70 네이버인증 71 기타인증(PAYCO, TOSS 등) 이 인증 외에 다른 인증수단은 규제기관(KAIT)의 확인 필요 |
| 13 | `cntpntCd` | 개통접점코드 | 10 | M |  |  |
| 14 | `emlAdrsNm` | 이메일주소 | 100 | M | Y |  |
| 15 | `rqsshtPprfrmCd` | 청구서양식코드 | 2 | C |  | 청구계정ID가 null일 경우 필수 *코드정의서 참조 - 우편 : LX - 이메일 : CB - 모바일MMS : MB |
| 16 | `bchngNpCommCmpnCd` | 변경전번호이동사업자코드 | 3 | C |  | *코드정의서 참조  번호이동가입 필수 |
| 17 | `athnItemCd` | 번호이동인증항목코드 | 1 | C |  | 번호이동시 필수 *코드정의서 참조 - 핸드폰 일련번호 - 요금납부 계좌 - 신용카드 |
| 18 | `athnSbst` | 인증항목값 | 4 | C |  | 번호이동가입시 인증항목이 지로가 아닌 경우 필수 번호이동 인증유형 뒷 4자리 |
| 19 | `blpymMthdCd` | 납부방법코드 | 1 | C |  | 청구계정ID가 null일 경우 필수 *코드정의서 참조 - 신용카드 : C - 계좌자동이체 : D - 지로 : R |
| 20 | `crdtCardExprDate` | 신용카드만기일자 | 6 | C |  | 청구계정ID가 null이고 납부방법이 카드면 필수 - YYYYMM 형식으로 입력 |
| 21 | `crdtCardKindCd` | 신용카드종류코드 | 2 | C |  | *코드정의서 참조 청구계정ID가 null이고 납부방법이 카드면 필수 |
| 22 | `bankCd` | 은행코드 | 7 | C |  | *코드정의서 참조 청구계정ID가 null이고 납부방법이 자동이체이면 필수 |
| 23 | `blpymMthdIdntNo` | 납부방법식별번호 | 20 | C | Y | 청구계정ID가 null이고 납부방법이 카드나 자동이체이면 필수 - 카드번호 또는 계좌번호 |
| 24 | `channelCd` | 채널사업자코드 | 3 | M |  | TOS : 토스 KMV : 대고객 통합채널 |
| 25 | `accessIndCd` | 접근구분코드 | 1 | M |  | M : 모바일 P : PC E : 기타 |
| 26 | `billAccId` | 청구계정ID | 11 | O | Y | 청구계정 ID |
| 27 | `simDivCd` | SIM구분코드 | 1 | M |  | SIM구분코드(U/E) |
| 28 | `motliRqsshtPprfrmCd` | 모회선 청구서양식코드 | 2 | C |  | 왓치개통일 경우 필수 *코드정의서 참조 - 우편 : LX - 이메일 : CB - 모바일MMS : MB |
| 29 | `motliSvcNo` | 모회선 전화번호 | 11 | C | Y | 왓치개통일 경우 필수 |
| 30 | `watchTypeCd` | 워치유형코드 | 1 | C |  | 왓치개통일 경우 필수 그외는 null  - A : 애플워치  - S : 갤럭시워치  - E : 기타 |
| 31 | `clntIp` | 사용자IP | 15 | O |  |  |
| 32 | `sbscAthnMovTelNo` | 가입인증이동전화번호 | 11 | O | Y | 본인인증수단시 사용한 가입인증이동전화번호 |
| 33 | `sbscCntplcNo` | 가입시연락처번호 | 20 | O | Y | 가입시연락처번호 |
| 34 | `fathTransacId` | 안면인증 트랜잭션 아이디 | 30 | C |  | 안면인증 트랜잭션 ID |
| 35 | `idcardPhotoImg` | 신분증사진 | … | C | Y | 신분증 사진(고객 얼굴사진) (Base64 인코딩) |
| 36 | `idcardCopiesImg` | 신분증 사본 | … | C | Y | 신분증 사본 (Base64 인코딩)    > 실물신분증 이용 : OCR 스캔된 신분증 이미지    > 모바일 신분증 이용 : 확인증 이미지   > 대면 신분증스캐너 이용 : 전달하지 않음 ““ |
| 37 | `mblIdcardQrImg` | 모바일신분증 QR | 100 | C | Y | 모바일 신분증 QR   > PASS앱 모바일 신분증 이용 시 MPM-QR    > 행안부 모바일 신분증 이용 시 A2A Profile |

### FS0 — 서식지 목록조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstFrmpapService` |
| Operation Name | `osstFrmpapListRetv` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mngmAgncId` | 관리대리점코드 | 10 |  |  | 우수기변:List / 일반기변:1.0 / 기변취소:onlineCustTrtSttusChgCd | 개통요청 대리점코드 |
| 2 | `cntpntCd` | 접점코드 | 10 |  |  | 일반기변:2.0 / 기변취소:custIdntNo | 개통요청 접점코드 |
| 3 | `retvStrtDt` | 조회시작일시 | 8 |  |  | 일반기변:3.0 / 기변취소:custNm | yyyyMMdd(현재일시보다 큰 경우 불가) |
| 4 | `retvEndDt` | 조회종료일시 | 8 |  |  | 일반기변:4.0 / 기변취소:wapplRegDate | yyyyMMdd(현재일시보다 큰 경우 불가) |
| 5 | `svcApyTrtStatCd` | 처리상태코드 | 8 |  |  | 일반기변:5.0 / 기변취소:frmpapId | 1 : 접수, 2: 진행, 3: 완료, 4: 취소 Null 로 입력시 전체 목록조회 |
| 6 | `retvSeq` | 조회시작번호 | 5 |  |  | 일반기변:6.0 / 기변취소:custTypeNm | 조회시작번호(ex. 0) List 에 나오는 최초 Row의 번호 미 입력시 0 |
| 7 | `retvCascnt` | 조회건수 | 5 |  |  | 일반기변:7.0 / 기변취소:custIdntNoIndCd | 조회건수(ex. 40) => Max 40 List 에 나오는 데이터 Row 수 미 입력시 40 |

### FS1 — 서식지 상태조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstFrmpapService` |
| Operation Name | `osstFrmpapRetv` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mngmAgncId` | 관리대리점코드 | 10 |  |  | 우수기변:1 / 일반기변:frmpapId / 기변취소:서식지아이디 | 개통요청 대리점코드 |
| 2 | `cntpntCd` | 접점코드 | 10 |  |  | 우수기변:2 / 일반기변:titl / 기변취소:제목 | 개통요청 접점코드 |
| 3 | `frmpapId` | 서식지아이디 | 100 |  |  | 우수기변:3 / 일반기변:mngmAgncId / 기변취소:관리대리점코드 |

### FS2 — 서식지 상태변경

| 항목 | 내용 |
|---|---|
| Service Name | `OsstFrmpapService` |
| Operation Name | `osstFrmpapStatChg` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mngmAgncId` | 관리대리점코드 | 10 | M |  | 개통요청 대리점코드 |
| 2 | `cntpntCd` | 접점코드 | 10 | M |  | 개통요청 접점코드 |
| 3 | `frmpapId` | 서식지아이디 | 100 | M |  |  |
| 4 | `frmpapStatCd` | 서식지상태변경코드 | 1 | M |  | P : 진행 R : 복구(진행중인 상태를 접수로 변경) C : 취소 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rsltCd` | 처리결과코드 |  |  |  |  |
| 2 | `rsltMsg` | 처리결과메세지 |  |  |  |  |

### FS3 — 무서식지대상조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstNthnFrmpapAtcTrtService` |
| Operation Name | `retvOsstNthnFrmpapTgt` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `oderTrtOrgId` | 오더처리조직아이디 | 15 |  |  | 우수기변:List / 일반기변:1.0 / 기변취소:oderTypeCd | 오더처리조직아이디 (개통/번호이동/기변/명변) =대리점코드 |
| 2 | `oderTypeCd` | 오더유형코드 | 3 |  |  | 일반기변:2.0 / 기변취소:itgFrmpapSeq | NAC: 신규개통 MNP: 번호이동 MCN: 명의변경 HCN: 기기변경 * 코드정의서 오더유형 코드(ODER_TYPE_CD) 참조 |
| 3 | `tlphNo` | 전화번호 | 11 |  | Y | 일반기변:3.0 / 기변취소:custNm |
| 4 | `retvStDt` | 조회시작일시 | 8 |  |  | 일반기변:4.0 / 기변취소:tlphNo | yyyyMMdd(현재일시보다 큰 경우 불가) |
| 5 | `retvFnsDt` | 조회종료일시 | 8 |  |  | 일반기변:5.0 / 기변취소:svcCntrId | yyyyMMdd(현재일시보다 큰 경우 불가) *조회기간 최대 30일 |
| 6 | `oderTrtId` | 오더처리자아이디 | 15 |  |  | 일반기변:6.0 / 기변취소:oderCretDt | 오더처리자 아이디(=KOS ID) |
| 7 | `retvSeq` | 조회시작번호 | 5 |  |  | 일반기변:7.0 / 기변취소:sysTrtrId | 조회시작번호(ex. 0) List 에 나오는 최초 Row의 번호 미 입력시 0 |
| 8 | `retvCascnt` | 조회건수 | 5 |  |  |  | 조회건수(ex. 30) => Max 30 List 에 나오는 데이터 Row 수 미 입력시 30 |

### FS4 — 무서식지첨부처리

| 항목 | 내용 |
|---|---|
| Service Name | `OsstNthnFrmpapAtcTrtService` |
| Operation Name | `trtOsstNthnFrmpapAtc` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `itgFrmpapSeq` | 통합서식지일련번호 | 10 | M |  | 무서식지 오더 데이터 조회(FS3)를 통해 취득 ex)20240323102606X667387804 |
| 2 | `oderTypeCd` | 오더유형코드 | 3 | M |  | NAC 신규개통 MNP 번호이동 MCN 명의변경 HCN 기기변경 * 코드정의서 오더유형 코드(ODER_TYPE_CD) 참조 |
| 3 | `tlphNo` | 전화번호 | 11 | M | Y |  |
| 4 | `frmpapId` | 서식지아이디 | 100 | M |  | 서식지목록조회(FS0) 로 취득 ex)0xF28B1D20E7EE11EE9BD20080C74455C600 |
| 5 | `scanDt` | 스캔일자 | 8 | M |  | YYYYMMDD 현재일 보다 큰 경우 불가) 신분증 스캔일자 |
| 6 | `oderTrtOrgId` | 오더처리 대리점 아이디 | 15 | M |  | 오더처리 대리점 아이디 |
| 7 | `oderTrtId` | 처리자 아이디 | 15 | M |  | 오더처리자 아이디(=KOS ID) |
| 8 | `svcContId` | 서비스계약번호 | 11 | M |  | 서비스계약아이디 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rsltCd` | 처리결과코드 |  |  |  |  |
| 2 | `rsltMsg` | 처리결과메세지 |  |  |  |  |

### FS5 — 온라인 서식지 등록

| 항목 | 내용 |
|---|---|
| Service Name | `OsstOnlineFrmpapService` |
| Operation Name | `osstOnlineFrmpapReg` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `SRL_NO` | 일련번호 |  | M |  | o NNN(3자리)+SEQ( 7자리) * 코드정의서 KAIT연동코드(KAIT_TECOM_CD) 참조 o NNN : KAIT연동코드 A제외한 숫자 3자리    SEQ : 사업자 채번 1000001 ( 7자리 )    예)EVK : 1191000001 |
| 2 | `SLS_CMPN_CD` | 판매회사코드 |  | M |  | 사업자코드 3자리 |
| 3 | `CUST_IDNT_NO` | 고객식별번호 |  | M | Y | 고객식별번호구분코드(CUST_IDNT_NO_IND_CD)에 따른 식별번호 |
| 4 | `CUST_IDNT_NO_IND_CD` | 고객식별번호구분코드 |  | M |  | 01: 주민등록번호(개인, 개인사업자) 02: 사업자등록번호 03: 법인등록번호(법인, 공공기관) 04: 여권번호 05: 외국인등록번호(외국인) 06: 사회보장번호/DOD 11: 공관원신분증번호 |
| 5 | `PRDC_CD` | 상품코드 |  | O |  | 요금제 코드 |
| 6 | `HNDSET_MDL_ID` | 단말기모델아이디 |  | O |  |  |
| 7 | `IND_TYPE_CD` | 구분타입코드 |  | O |  | HCN3: 기기변경 HDN3: 보상기변3G MNP3: 번호이동 NAC3: 개통 |
| 8 | `CUST_NO` | 고객번호 |  | O |  |  |
| 9 | `CUST_NM` | 고객명 |  | M | Y |  |
| 10 | `CUST_DTL_TYPE_CD` | 고객세부유형코드 |  | M |  | F: 외국인 F1: 순수외국인 F2: 재외국인 F3: 외국국적동포(시민권자) N1: 내국인(내국인, 법인) N2: 재외국민 |
| 11 | `CRPR_NO` | 법인번호 |  | O |  |  |
| 12 | `CUST_TYPE_CD` | 고객유형코드 |  | M |  | B: 법인사업자(법인 / 공공기관) I: 개인고객(개인 / 개인사업자/ 외국인) |
| 13 | `WLFR_CD` | 복지코드 |  | O |  | 00: 해당없음 85: 아동복지기관         86: 청각/언어장애자      87: 청각/언어상이자      88: 국가유공자 (상이자)  89: 장애인               90: 국가,독립유공(유족)  91: 거택보호대상자       92: 광주민주화운동부상자 93: 정신지체/발달장애    94: 기초생활수급자       95: 차상위계층           99: 장애인/국가유공자 |
| 14 | `WRLN_TLPH_NO` | 유선전화번호 |  | O | Y | 4-4-4 포멧, 앞에 0 패딩 예) 0317778888 --> 003107778888 |
| 15 | `TLPH_NO` | 전화번호 |  | O | Y | 개통 시 사용할 희망번호 ex) 01033334444 |
| 16 | `EML_ADRS_NM` | 이메일주소명 |  | O | Y | 청구서 수신할 고객의 이메일 주소ex)aa@bbb.com |
| 17 | `EML_BILL_AGRE_YN` | 이메일청구동의여부 |  | O |  | Y/N |
| 18 | `BILL_ADRS_DONG_NM` | 청구주소명 |  | O | Y | ex)서울 강남구 도곡동 |
| 19 | `BILL_ADRS_NM` | 청구주소명 - 상세 |  | O | Y | 232-1 xx 아파트 xx동xx호 |
| 20 | `BILL_ADRS_ZIP_NO` | 청구주소우편번호 |  | O | Y | ex)482050 |
| 21 | `BLPYM_MTHD_CD` | 납부방법코드 |  | O |  | C: 신용카드 D: 은행계좌 자동이체 R: 지로 |
| 22 | `BLPYM_BANK_NM` | 납부은행명 |  | O |  | * 코드정의서 납부은행명 코드(BLPYM_BANK_NM) 참조 ex)040000, BC |
| 23 | `BLPYM_BNKACN_NO` | 납부계좌번호 |  | O | Y | ex)1234678131 |
| 24 | `BLPYM_CUST_NM` | 납부고객명 |  | O | Y | 납부계좌 예금주명 ex)홍길동 |
| 25 | `BLPYM_CUST_INHB_RGST_NO` | 납부고객주민등록번호 |  | O | Y | 납부고객 유형에 따른 식별번호 개인 - 주민등록증의 생년월일 6자리 법인 - 법인번호 외국인 - 외국인등록증의 생년월일 6자리 ex)831111 |
| 26 | `CRDT_CARD_NO` | 신용카드번호 |  | O | Y | 납부방법이 카드인 경우  ex)949019024xxxxxxx |
| 27 | `EFCT_YM` | 유효년월 |  | O |  | 납부방법이 카드인 경우 ex)201308 |
| 28 | `SBSCST_TYPE_CD` | 가입비유형코드 |  | O |  | I: 분납 P: 면제 R: 완납 |
| 29 | `HNDSET_BILL_TYPE_CD` | 단말기청구유형코드 |  | O |  | 할부개월수  ex) 0, 24 |
| 30 | `USIM_BILL_TYPE_CD` | usim청구유형코드 |  | O |  | * 면제 불가 R: 즉시납부 B: 후청구 N: 비구매 |
| 31 | `CUST_FVRT_1_TLPH_NO` | 고객선호1전화번호 |  | O |  | *번호이동 시 Null 1순위 고객 희망번호 뒤 4자리 ex) 1234 |
| 32 | `CUST_FVRT_2_TLPH_NO` | 고객선호2전화번호 |  | O |  | *번호이동 시 Null 2순위 고객 희망번호 뒤 4자리 ex) 1234 |
| 33 | `NOW_SBSC_TLCM_CD` | 현재가입사업자코드 |  | C |  | * 번호이동인 경우 필수 (3자리) * 코드정의서 납부은행명 코드(NP_COMM_CMPN_CD) 참조 ex) K02: 에넥스텔레콤 |
| 34 | `NOW_CUST_TLPH_NO` | 현재고객전화번호 |  | O | Y | 현재 고객이 사용중인 전화번호 ex)01066668888 |
| 35 | `ATHN_ITEM_CD` | 인증항목코드 |  | O |  | 24년1월1일부로 미사용 컬럼 |
| 36 | `ATHN_TRTM_CD` | 인증처리코드 |  | O |  | 24년1월1일부로 미사용 컬럼 |
| 37 | `YET_BILL_BANK_NM` | 미청구은행명 |  | O |  | 번호이동 고객의 미청구금을 계좌이체로 수납시 사용 * 코드정의서 납부은행명 코드(BLPYM_BANK_NM) 참조 ex)040000, BC |
| 38 | `YET_BILL_BANK_BNKACN_NO` | 미청구은행계좌번호 |  | O | Y | 번호이동 고객의 미청구금을 계좌이체로 수납시 사용 |
| 39 | `YET_BILL_BLPYM_CUST_NM` | 미청구납부고객명 |  | O | Y | 번호이동 고객의 미청구금을 계좌이체로 수납시 사용 은행 예금주 명 |
| 40 | `YET_BILL_BANK_INHB_RGST_NO` | 미청구납부고객주민등록번호 |  | O | Y | 번호이동 고객의 미청구금을 계좌이체로 수납시 사용 은행 예금주 주민번호 |
| 41 | `MSN` | 단말기일련번호 |  | O |  | ex)013978 |
| 42 | `ICC_ID` | ICC아이디 |  | O | Y | 개통시 사용할 단말기의 ICC_ID ex)89823009080060xxxxx |
| 43 | `APLY_TRTM_STAT_CD` | 신청처리상태코드 |  | O |  | 미사용, KOS에서 부여 |
| 44 | `AGNC_ID` | 대리점아이디 |  | M |  | 개통하는 대리점코드 (7자리만 입력 가능) ex)HA11040 |
| 45 | `APLSHT_CNCL_YN` | 신청서취소여부 |  | O |  | 신청서 취소 여부 ex) Y/N |
| 46 | `APLSHT_RGST_DATE` | 신청서등록일자 |  | M |  | 필수입력값, 현재 일자만 입력 (날짜형식 : yyyyMMdd) 과거일, 미래일 -> 입력불가 |
| 47 | `SVC_CNTR_NO` | 서비스계약번호 |  | O |  | 미사용, KOS에서 부여 |
| 48 | `INDV_INFO_INER_PRCUSE_AGRE_YN` | 개인정보내부활용동의여부 |  | O |  | 개인정보수집 및 활용에 동의 *'Y'로 고정 |
| 49 | `INDV_INFO_EXTR_PRVD_AGRE_YN` | 개인정보외부제공동의여부 |  | O |  |  |
| 50 | `ADVR_RCV_AGRE_YN` | 광고수신동의여부 |  | O |  |  |
| 51 | `SPCL_SLS_NO` | 판매정책 |  | O |  | 온라인서식을 통한 서비스 개통 시 입력되는 판매번호(특판)  ex)P5422, G4736 * MVNO 영업기획팀에서 생성 후 전달 |
| 52 | `INDV_INFO_CPRT_PRVD_AGRE_YN` | 개인정보제휴제공동의여부 |  | O |  | 제휴사에 개인정보를 제공하는가에 대한 동의 여부 |
| 53 | `CNTPNT_SHOP_ID` | 접점코드 |  | M |  | 필수 입력값, 온라인 가입 신청 시 관련 채널점 아이디 (10자리만 입력 가능) ex)1000557270 |
| 54 | `AGNT_INHB_RGST_NO` | 대리인주민등록번호 |  | O | Y | 법정대리인 주민등록번호 |
| 55 | `AGNT_NM` | 대리인명 |  | O | Y | 법정대리인 이름 |
| 56 | `AGNT_WRKJOB_IND_CD` | 대리인업무구분코드 |  | O |  | 01: 부 02: 모 03: 후견인 04: 연대보증인 05: 가족 06: 친척 07: 친구 08: 회사동료 10: 그외 11: 한정위탁 12: 지정위탁 |
| 57 | `AGNT_ZIP_NO` | 대리인우편번호 |  | O | Y | 법정대리인 주소의 우편번호 ex)410755 |
| 58 | `AGNT_ADRS_NM` | 대리인주소명 |  | O | Y | 법정대리인 주소 (시/동/면) ex)서울 강서구 외발산동 |
| 59 | `AGNT_MNT_ADRS_NM` | 대리인상세주소명 |  | O | Y | 법정대리인 주소 상세 내용 |
| 60 | `AGNT_HOUS_TLPH_NO` | 대리인집전화번호 |  | O | Y | 법정대리인 유선전화번호 |
| 61 | `AGNT_PRTTLP_NO` | 대리인이동전화번호 |  | O | Y | 법정대리인 이동전화번호 |
| 62 | `BIND_SVC_IND_CD` | 결합서비스구분코드 |  | O |  | 미사용 |
| 63 | `SNGL_SVC_IND_CD` | 단일서비스구분코드 |  | O |  | 세대구분코드(미사용) |
| 64 | `INGR_1_CD` | 유입1코드 |  | O |  | 미사용 |
| 65 | `INGR_2_CD` | 유입2코드 |  | O |  | 미사용 |
| 66 | `INGR_CD_NM` | 유입코드명 |  | O |  | 미사용 |
| 67 | `REAL_USER_INHB_RGST_NO` | 실사용자주민등록번호 |  | O | Y | 법인개통시 실사용자 주민등록번호 |
| 68 | `REAL_USER_NM` | 실사용자명 |  | O | Y | 법인개통시 실사용자명 |
| 69 | `FEE_DSCN_AGRE_YN` | 요금할인동의여부 |  | O |  | 쇼킹 스폰서 가입시 요금할인 동의 여부 |
| 70 | `YET_BILL_STLM_MTHD_CD` | 미청구결제방법코드 |  | O |  | 번호이동고객의 미청구결제방법 1: 현금 2: 카드 3: WP카드 4: 현금과 카드 |
| 71 | `YET_BILL_STLM_CARD_NO` | 미청구결제카드번호 |  | O | Y | 번호이동고객의 미청구금을 카드로 수납시 사용 |
| 72 | `YET_BILL_STLM_INHB_RGST_NO` | 미청구결제주민등록번호 |  | O | Y | 번호이동 고객의 미청구금을 계좌이체로 수납시 사용 신용카드 소유주 주민등록번호 |
| 73 | `YET_BILL_STLM_EXPR_MM` | 미청구결제만기월 |  | O |  | 번호이동 고객의 미청구금을 계좌이체로 수납시 사용 신용카드 유효기간 |
| 74 | `YET_BILL_STLM_INST_YN` | 미청구결제할부여부 |  | O |  | 번호이동 고객의 미청구금을 계좌이체로 수납시 사용 신용카드 할부여부 |
| 75 | `YET_BILL_STLM_INST_TMSCNT` | 미청구결제할부횟수 |  | O |  | 신용카드 할부횟수 |
| 76 | `SALORG_ID` | 판매점아이디 |  | O |  | 판매점/접점 정보 *접점코드(CNTPNT_SHOP_ID)와 동일값 |
| 77 | `MEMO_SBST` | 메모내용 |  | O |  | 온라인 개통시 인증방식을 저장할수 있는 메모내용 (모젠은 NULL) |
| 78 | `BIZR_RGST_NO` | 사업자등록번호 |  | O |  | 사업자번호 |
| 79 | `RQSSHT_DSPT_TYPE_CD` | 청구서발송유형코드 |  | O |  | AB: 이메일+모바일 AP: 스마트(앱) CB: 이메일명세서 FX: FAX LC: 우편+이메일 LM: 문자(해지대상) LX: 우편(종이)명세서 MB: 모바일(MMS)명세서 NH: 농협스마트고지 |
| 80 | `ENCR_PRSN_ID` | 권유자아이디 |  | O |  | 온라인 개통시 권유한 사람의 아이디 |
| 81 | `SUPOT_PRSN_ID` | 지원자아이디 |  | O |  | 온라인 개통시 판매직원(비영업직)의 아이디 |
| 82 | `TRMN_YET_RPYM_AMT_YN` | 해지미환급금액여부 |  | O |  | 해지미환급액에 대한 동의 여부 ex) Y: 동의, N: 미동의 * 번호이동시에만 사용 |
| 83 | `CYBR_RQSSHT_SECUR_YN` | 사이버청구서보안여부 |  | O |  | 사이버청구서의 보안여부 ex) Y: 보안, N: 그외 |
| 84 | `USIM_PYMN_MTHD_CD` | USIM수납방법코드 |  | O |  | I 선불지능망 G 선불캐쉬 R 일반후불 * 유심단독개통이면 I 또는 G 그외 R |
| 85 | `USIM_UNIQ_OPEN_YN` | USIM단독개통여부 |  | O |  | USIM단독개통여부 ex) Y: 단독개통, N: 그외 |
| 86 | `OLH_CLUB_SCOR` | OLLEH클럽점수 |  | O |  | 올레클럽점수(별) |
| 87 | `INFO_ADVR_USE_AGRE_YN` | 정보광고이용동의여부 |  | O |  | 정보광고 전송을 위한 개인정보 이용 동의 여부  ex) Y: 동의, N: 비동의 |
| 88 | `INFO_ADVR_RCV_AGRE_YN` | 정보광고수신동의여부 |  | O |  | 정보광고 수신 동의여부 ex) Y: 동의, N: 비동의 |
| 89 | `BOND_PRSR_FEE_CD` | 채권보전료코드 |  | O |  | R: 즉납(면제) B: 후납 |
| 90 | `CLCR_TLPH_NO` | 합산전화번호 |  | O | Y | 인터넷, 집전화, 와이브로 합산 등 납부방법에 따라 사용하며 은행이체 시 null값 |
| 91 | `CLCR_ID` | 합산아이디 |  | O |  | 인터넷, 집전화, 와이브로 합산 등 납부방법에 따라 사용하며 은행이체 시 null값 |
| 92 | `CAR_MKNG_AGNT_CD` | 차량제조사코드 |  | O |  | 05: 현대자동차 07: 모젠 * MOZEN 일경우만 들어옴 타 사업자는 NULL |
| 93 | `ENGG_MNTH_CNT` | 약정개월수 |  | O |  | 스폰서 계약 시 스폰서 지원을 받기 위해 약정한 의무 사용 기간 |
| 94 | `INST_MNTH_CNT` | 할부개월수 |  | O |  | 스폰서유형이 단말월할부를 지원할 경우 단말 할부 개월수 |
| 95 | `HNDSET_DSCN_1_AMNT` | 단말기할인1금액 |  | O |  | 스폰서 계약시 단말기 선할인 금액 |
| 96 | `HNDSET_DSCN_2_AMNT` | 단말기할인2금액 |  | O |  | 스폰서(프로모션할인) 계약시 단말기 선할인 금액 |
| 97 | `USIM_TYPE_DIV_CD` | 유심타입구분코드 |  | O |  | E: ESIM U or 값 없음: 일반유심 |
| 98 | `MY_SELF_ATHN_YN` | 본인인증여부 |  | O |  | 본인인증여부가 N or Null인 경우 오더처리 불가 Y인 경우 신청서의 CI와 개통 명의자 CI비교하여 불일치 시 오더처리 불가 |
| 99 | `IPIN_CI` | 본인인증(CI값) |  | O | Y | 본인인증 CI 값(max 88자리) |
| 100 | `ONLINE_ATHN_DIV_CD` | 본인인증수단 |  | O |  | 10: 공인인증 20: 신용카드인증 30: 휴대폰인증 60: 카카오페이인증 61: PASS계좌인증 70: 네이버인증 71: 기타인증(PAYCO, TOSS 등) * 이 인증수단 외에 다른 인증수단은 KAIT 확인 필요 |
| 101 | `SUBSC_TYPE_CD` | 구독유형코드 |  | O |  |  |
| 102 | `CRDT_INFO_AGRE_YN` | 신용정보동의여부 |  | O |  | 신용정보조회 및 이용에 대한 동의 *'Y'로 고정 |
| 103 | `GPCOM_COMSVC_SBSC_AGREE_YN` | 그룹사결합서비스가입동의여부 |  | O |  | 그룹사 결합서비스 가입 정보 Y:동의 N:미동의 |
| 104 | `CARD_INSUR_PROD_AGREE_YN` | 카드보험상품동의여부 |  | O |  | 카드발급 또는 보험상품 가입정보 Y:동의 N:미동의 |
| 105 | `CSGN_INFO_ADVM_RCV_AGREE_YN` | 위탁정보광고수신동의여부 |  | O |  | 고객 편의제공을 위한 이용 및 취급위탁, 정보/광고 수신동의 Y:동의 N:미동의 |
| 106 | `OTCOM_INFO_ADVM_RCV_AGREE_YN` | 타사정보광고수신동의여부 |  | O |  | 타사로부터 의뢰 받은 정보/광고 수신동의 Y:동의 N:미동의 |
| 107 | `OTCOM_INFO_ADVM_CSGN_AGREE_YN` | 타사정보광고위탁동의여부 |  | O |  | 타사로부터 의뢰 받은 정보/광고전송을 위한 개인정보 취급위탁 동의 Y:동의 N:미동의 |
| 108 | `FNNC_DEAL_AGREE_YN` | 금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의 |  | O |  | 금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의 Y:동의 N:미동의 |
| 109 | `FATH_TRANSAC_ID` | 안면인증 트랜잭션 아이디 | 30 | C |  | 안면인증 트랜잭션 아이디 |
| 110 | `M2M_HNDSET_YN` | M2M단말여부 | 1 | C |  | M2M단말여부 - Y 로 연동 시, M2M 회선으로 판단 - N 혹은 NULL 로 연동 시, M2M 회선 아닌것으로 판단 |
| 111 | `FATH_RGLS_ENV_TEST_YN` | 안면인증상용환경테스트여부 | 1 | C |  | 안면인증 미대상 사업자이나 해당 값 Y면, 안면인증 대상 사업자인 것 처럼 처리. Y 외의 값은 무시됨. - Y로 수록 시, 안면인증 트랜잭션 아이디 값 필수. |
| 112 | `INDV_LO_INFO_PRV_AGREE_YN` | 개인위치정보제공동의여부 | 1 | O |  | 개인위치정보 제3자 제공 동의 미입력시 미동의로 연동 Y:동의 N:미동의 *대상사업자 엠모바일(KIS). 이외 사업자 입력 시 미동의로 연동 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `SVC_STATUS` | 결과코드 | M |  |  | 00: 성공 99: 실패 |
| 2 | `ERR_MSG` | 결과처리메세지 | O |  |  | ex) 성공 실패 |

### FS6 — 온라인 서식지 CI 조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstOnlineFrmpapService` |
| Operation Name | `osstOnlineFrmpapIpinRetv` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `srlNo` | 일련번호 |  | M |  | 온라인서식지 등록의 SRL_NO NNN(3자리)+SEQ(7자리) |
| 2 | `slsCmpnCd` | 판매회사코드 |  | M |  | 온라인서식지 등록의 SLS_CMPN_CD 사업자코드 3자리 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `ipinCi` | 본인인증(CI값) |  | O | Y | 본인인증 CI값 사전체크 후 실명인증 수행한 CI 있을때만 존재 법정대리인은 법정대리인 기준 CI 제공 |
| 2 | `rsltCd` | 결과코드 |  | M |  | 결과코드 - 00 : 성공 - 99 : 실패 |

### FS7 — KAIT 사진진위확인 요청

| 항목 | 내용 |
|---|---|
| Service Name | `OsstFrmpapService` |
| Operation Name | `photoAthnRqt` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `photoAthnRetvDivCd` | 사진인증조회구분코드 | 5 | M |  | REGID : 주민등록증 DRIVE : 운전면허증 FORGN : 외국인등록증/거소신고증(미정) PPORT : 외국인여권(미정)  주민등록증 구분 - REGID : 주민등록증을 통해 조회 (발급일자포함) FORGN, PPORT, HANDI, MERIT 는 추후 대상 확장 시 고려 국가보훈등록증(2023년 6월 부터 통합) HANDI : 장애인등록증(미정) MERIT : 국가보훈등록증(미정) |
| 2 | `photoAthnIndvDivCd` | 사진인증개인구분코드 | 2 | M |  | 01 : 본인(명의자) 02 : 대리인 03 : 법정대리인 |
| 3 | `photoAthnSvcDivCd` | 사진인증서비스구분코드 | 3 | M |  | MOB : 모바일 ETC : 기타 |
| 4 | `photoAthnSbscChCd` | 사진인증가입채널코드 | 1 | M |  | W : 온라인가입(홈페이지를 통한 가입) D : 오프라인가입(대리점 등을 통한 가입) |
| 5 | `photoAthnSbscDivCd` | 사진인증가입구분코드 | 1 | M |  | 1 : 신규가입 2 : 번호이동 3 : 명의변경 4 : 기기변경 5 : 고객정보변경 |
| 6 | `photoAthnRetvPotimCd` | 사진인증조회시점코드 | 2 | M |  | BE : 개통전 AF : 개통후 |
| 7 | `photoAthnAgreeDivYn` | 사진인증동의구분여부 | 1 | M |  | Y or N  ※ N일 경우 조회불가 |
| 8 | `photoAthnConnIpadr` | 사진인증접속IP주소 | 39 | M |  | IPv4(15) 192.168.100.100 IPv6(39) FF00:FF00:FF00:FF00:FF00:FF00:FF00:FF00 |
| 9 | `photoAthnAgncyId` | 사진인증대리점아이디 | 20 | M |  |  |
| 10 | `photoAthnRetvPrsnId` | 사진인증조회자아이디 | 15 | M |  |  |
| 11 | `photoAthnAgncyNm` | 사진인증대리점명 | 90 | M |  |  |
| 12 | `photoAthnSalerCd` | 사진인증판매자코드 | 20 | C |  | 판매점 개통시 필수, 대리점은 공백처리 통신사 부여 판매점 접점코드(P코드) |
| 13 | `photoAthnCustIdfyNo` | 사진인증고객식별번호 | 24 | M | Y | 주민등록번호/외국인등록번호 등 |
| 14 | `photoAthnFnmNm` | 사진인증성명 | 150 | C | Y | 개인인경우에만 사용 특수기호(-, .), 아라비아 숫자 등 포함 가능 ※ 법무부 요청으로 FORGN(외국인) 성명에 (-, .) 제외 후 진위확인 처리  행안부자료(REGID,HANDI)는 공백제거 후 처리  외국인등록증(FORGN) 성명은 법무부에서 공백제거후 후 처리, 대문자만가능 여권(PPORT)는 법무부에서 성명(39) 첫1자리만 체크 면허증(DRIVE) 성명 공백까지 정확해야함 |
| 15 | `photoAthnIssDate` | 사진인증발급일자 | 8 | M |  | DRIVE'일때는 2014.7.9일 이후 자료만 발급일자 확인가능 FORGN, HANDI, MERIT 는 추후 대상 확장 시 고려 |
| 16 | `photoAthnDriveLicnsNo` | 사진인증운전면허번호 | 24 | C | Y | FORGN'일때 가입채널 온라인인경우('W') 진위확인 요청시 발급일자 SKIP ex) 서울 00-123456-00 => 110012345600 |
| 17 | `photoAthnEngCitizCd` | 사진인증영문국적코드 | 3 | C |  | 외국인여권일때만 사용 직접입력도 가능토록 설계 별도 Sheet 참조 |
| 18 | `photoAthnBthday` | 사진인증생년월일 | 8 | C | Y | 외국인여권일때만 사용 810000과 같이 특수한 경우 있음 |
| 19 | `photoAthnRwomNo` | 사진인증보훈번호 | 10 | C |  | MERIT 는 추후 대상 확장 시 고려 |
| 20 | `photoAthnImgSize` | 사진인증이미지크기 | 8 | M |  | 전송할 이미지 사이즈 byte( ex: 00082872) |
| 21 | `photoAthnImgFileSbst` | 사진인증이미지파일내용 |  | M | Y | 사진 스캔 이미지 규격 - 증명 사진 3*4 규격 (규격 환산시 : 400*533)  - 해상도 300dpi, 확장자 jpg - 최소 : 42KB 이상 - 최대 : 66KB 이하 권장  ※ 필요 시 이미지 압축 |
| 22 | `retryPhotoAthnTxnSeq` | 재처리 사진인증내역일련번호 | 15 | C |  | KAIT 사진진위확인 요청(FS7)시 응답으로 받은 사진인증일련번호 중 재처리할 일련번호 해당 값 없을시 신규 요청건으로 처리 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `photoAthnTxnSeq` | 사진인증내역일련번호 | 15 |  |  | 오더 처리시 사진진위인증 검증으로 사용하는 일련번호 |
| 2 | `photoAthnDt` | 사진인증일시 | 14 |  |  |  |
| 3 | `photoAthnSkipCd` | 사진인증생략코드 | 2 |  |  | E : 미처리(개통) - KAIT 전체스킵, 주민등록증 스킵, 운전면허증스킵, 외국인스킵 S : 당일 성공 -당일 동일대리점, 동일신분증, 동일 고객일때 당일 성공 B : 스킵권한자(개통무관권한) |
| 4 | `photoAthnSvcApyNo` | 사진인증서비스신청번호 | 32 |  |  |  |
| 5 | `photoAthnResltCd` | 사진인증결과코드 | 1 |  |  | KAIT 응답코드명 : 처리결과 G : 정상 N : 동의 거부로 인한 조회불가 E : 에러 |
| 6 | `photoAthnErrCd` | 사진인증오류코드 | 2 |  |  | KAIT 응답코드명 : 에러코드 사진인증오류코드 Sheet 참고 |
| 7 | `photoAthnTruYn` | 사진인증진위여부 | 1 |  |  | KAIT 응답코드명 : 진위여부 Y(성공) / N(불가) |
| 8 | `photoAthnResltDtlCd` | 사진인증결과상세코드 | 4 |  |  | KAIT 응답코드명 : 결과코드(불가사유) 사진인증결과상세코드 Sheet 참고 |
| 9 | `photoAthnSeq` | 사진인증일련번호 | 64 |  |  | KAIT 응답코드명 : 일련번호 |
| 10 | `photoAthnSojnQualfCd` | 사진인증체류자격코드 | 3 |  |  | KAIT 응답코드명 : 체류자격 |
| 11 | `photoAthnExpDate` | 사진인증만료일자 | 8 |  |  | KAIT 응답코드명 : 만료일자 |
| 12 | `photoAthnSclpstChgDate` | 사진인증신분변경일자 | 8 |  |  | KAIT 응답코드명 : 신분변경일자 |
| 13 | `photoAthnTruConfLnkNo` | 사진인증진위확인연계번호 | 24 |  |  |  |
| 14 | `photoAthnDecideCd` | 사진인증판정코드 | 4 |  |  | KAIT 결과값으로 성공/스킵/실패를 판정한 결과 SUCC : 성공 SKIP : 스킵 FAIL : 실패  사진인증판정코드(photoAthnDecideCd) 성공/스킵인 경우에만 이후 오더처리 진행 가능 * 사진인증판정 실패인 경우에도 사진진위인증 안정화기간인 경우에는 처리 가능합니다. |

### FPC0 — 서식지 사전체크(신규,번호이동)

| 항목 | 내용 |
|---|---|
| Service Name | `OsstFrmpapSvcPrcSOService` |
| Operation Name | `osstFrmpapPrePrc / osstFrmpapNpPrePrc` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mvnoOrdNo` | MVNO 오더 번호 | 14 |  |  | 우수기변:1 / 일반기변:osstOrdNo / 기변취소:OSST 오더 번호 | MVNO에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `slsCmpnCd` | 판매회사코드 | 3 |  |  | 우수기변:2 / 일반기변:rslt / 기변취소:처리 결과 |
| 3 | `custTypeCd` | 고객유형코드 | 2 |  |  | 우수기변:3 / 일반기변:rsltMsg / 기변취소:처리 결과 메시지 | * 코드정의서 참조(CUST_TYPE_CD) |
| 4 | `custIdntNoIndCd` | 고객식별번호구분코드 | 2 |  |  |  | * 코드정의서 참조(CUST_IDNT_NO_IND_CD) |
| 5 | `custIdntNo` | 고객식별번호 | 20 |  | Y |  |
| 6 | `crprNo` | 법인번호 | 13 |  | Y |  | 법인인 경우 필수 |
| 7 | `custNm` | 고객명 | 60 |  | Y |  |
| 8 | `cntrUseCd` | 계약용도코드 | 2 |  |  |  | R : 일반 I : 지능망 선불 |
| 9 | `instYn` | 할부여부 | 1 |  |  |  | Y : 할부 N : 즉납 |
| 10 | `scnhndPhonInstYn` | 중고폰할부여부 | 1 |  |  |  | Y : MVNO중고폰  * Default : N |
| 11 | `myslAgreYn` | 본인동의여부 | 1 |  |  |  | Y:동의, N:미동의 |
| 12 | `nativeRlnamAthnEvdnPprCd` | 실명인증증빙서류코드 | 5 |  |  |  | * 코드정의서 참조(NATIVE_RLNAM_ATHN_EVDN_PPR_CD) |
| 13 | `athnRqstcustCntplcNo` | 인증요청고객연락처번호 | 12 |  | Y |  |
| 14 | `rsdcrtIssuDate` | 주민등록증발급일자 | 8 |  |  |  | 개인 내국인 : 주민등록증, 장애인등록증, 국가유공자증, 운전면허증 발급일자 개인 외국인 : 외국인등록증 발급일자 법인, 공공기관 : 사업자 교부일자 |
| 15 | `lcnsNo` | 면허번호 | 20 |  | Y |  | 실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 |
| 16 | `lcnsRgnCd` | 면허지역코드 | 2 |  |  |  | 실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 *코드정의서 참조 |
| 17 | `mrtrPrsnNo` | 유공자번호 | 10 |  | Y |  | 실명인증증빙서류코드가 MERIT(국가유공자)인 경우 필수 |
| 18 | `nationalityCd` | 국적코드 | 3 |  |  |  | 외국인 실명 인증 시 필수 *코드정의서 참조(NATIONNALITY_CD) |
| 19 | `fornBrthDate` | 외국인생일일자 | 8 |  | Y |  | 외국인-'04'(여권)인 경우 필수 (YYYYMMDD) |
| 20 | `crdtInfoAgreYn` | 신용정보동의여부 | 1 |  |  |  | 신용정보조회 및 이용에 대한 동의  * "Y" 로 고정 |
| 21 | `indvInfoInerPrcuseAgreYn` | 개인정보내부활용동의여부 | 1 |  |  |  | 개인정보수집및 활용에 동의  * "Y" 로 고정 |
| 22 | `cnsgInfoAdvrRcvAgreYn` | 위탁정보광고수신동의여부 | 1 |  |  |  | 고객 편의제공을 위한 이용 및 취급위탁, 정보/광고 수신동의 Y:동의 N:미동의 |
| 23 | `othcmpInfoAdvrRcvAgreYn` | 타사정보광고수신동의여부 | 1 |  |  |  | 타사로부터 의뢰 받은 정보/광고 수신동의 Y:동의 N:미동의 |
| 24 | `othcmpInfoAdvrCnsgAgreYn` | 타사정보광고위탁동의여부 | 1 |  |  |  | 타사로부터 의뢰 받은 정보/광고전송을 위한 개인정보 취급위탁 동의 Y:동의 N:미동의 |
| 25 | `grpAgntBindSvcSbscAgreYn` | 그룹사결합서비스가입동의여부 | 1 |  |  |  | 그룹사 결합서비스 가입 정보 Y:동의 N:미동의 |
| 26 | `cardInsrPrdcAgreYn` | 카드보험상품동의여부 | 1 |  |  |  | 카드발급 또는 보험상품 가입정보 Y:동의 N:미동의 |
| 27 | `olhRailCprtAgreYn` | OLLEH철도제휴동의여부 | 1 |  |  |  | 올레제휴팩 가입, 코레일 제휴요금제 가입 정보 Y:동의 N:미동의 |
| 28 | `olhShckWibroRlfAgreYn` | OLLEH쇼킹와이브로안심동의여부 | 1 |  |  |  | 올레폰안심플랜,올레폰케어쇼킹안심 Y:동의 N:미동의 |
| 29 | `olngDscnHynmtrAgreYn` | 주유할인현대자동차동의여부 | 1 |  |  |  | LTE제휴요금제 제휴종료 고객확인 Y:동의 N:미동의 |
| 30 | `olhCprtPntAgreYn` | OLLEH제휴포인트동의여부 | 1 |  |  |  | 올레 신 제휴 포인트 동의 Y:동의 N:미동의 |
| 31 | `dwoCprtPntAgreYn` | 대우제휴포인트동의여부 | 1 |  |  |  | 대우증권 통신비 지원 서비스 동의 Y:동의 N:미동의 |
| 32 | `wlfrDscnAplyAgreYn` | 복지할인신청동의여부 | 1 |  |  |  | 복지할인 신청 동의 Y:동의 N:미동의 |
| 33 | `spamPrvdAgreYn` | 스팸제공동의여부 | 1 |  |  |  | 스팸정보를 KISA 제공 동의 Y:동의 N:미동의 |
| 34 | `prttlpStlmUseAgreYn` | 이동전화결제이용동의여부 | 1 |  |  |  | 휴대폰결제이용에대한 동의  Y:동의 N:미동의  * 법인,공공기관 인 경우 "N" 고정 |
| 35 | `prttlpStlmPwdUseAgreYn` | 이동전화결제비밀번호이용동의여부 | 1 |  |  |  | 휴대폰결제비밀번호이용동의여부 Y:동의 N:미동의  * 이동전화결제이용동의여부가 Y(동의)인 경우 선택 가능 |
| 36 | `wrlnTlphNo` | 유선전화번호 | 12 |  | Y |  | 개인은 자택/직장 전화번호, 법인과 공공기관은 사업장 전화번호 |
| 37 | `tlphNo` | 전화번호 | 11 |  |  |  |
| 38 | `rprsPrsnNm` | 대표자명 | 60 |  | Y |  | 법인 및 공공기관의 대표자명 |
| 39 | `upjnCd` | 업종코드 | 6 |  |  |  | 법인과 공공기관 필수 *코드정의서 참조(UPJN_CD) |
| 40 | `bcuSbst` | 업태내용 | 100 |  |  |  | 법인과 공공기관 필수 |
| 41 | `zipNo` | 우편번호 | 6 |  |  |  | 법인과 공공기관 필수 |
| 42 | `fndtCntplcSbst` | 기본연락처내용 | 200 |  | Y |  | 법인과 공공기관 필수(신주소) |
| 43 | `mntCntplcSbst` | 상세연락처내용 | 100 |  | Y |  | 법인과 공공기관 필수(신주소) |
| 44 | `brthDate` | 생일일자 | 8 |  | Y |  | 개인만 입력 (YYYYMMDD) |
| 45 | `brthNnpIndCd` | 생일음양구분코드 | 1 |  |  |  | 0: 양력 1: 음력 |
| 46 | `jobCd` | 직업코드 | 2 |  |  |  | 개인만 입력 *코드정의서 참조(JOB_CD) |
| 47 | `emlAdrsNm` | 이메일주소명 | 100 |  | Y |  | 이메일주소명 (도메인포함) |
| 48 | `lstdIndCd` | 상장구분코드 | 2 |  |  |  | 법인과 공공기관만 입력 1: 상장, 2: 비상장 |
| 49 | `emplCnt` | 사원수 | 9 |  |  |  | 법인과 공공기관만 입력 |
| 50 | `slngAmt` | 매출액 | 15 |  |  |  | 법인과 공공기관만 입력 |
| 51 | `cptlAmnt` | 자본금액 | 15 |  |  |  | 법인과 공공기관만 입력 |
| 52 | `crprUpjnCd` | 법인업종코드 | 6 |  |  |  | 법인과 공공기관만 입력 *코드정의서 참조(UPJN_CD) |
| 53 | `crprBcuSbst` | 법인업태내용 | 100 |  |  |  | 법인과 공공기관만 입력 |
| 54 | `crprZipNo` | 법인우편번호 | 6 |  |  |  | 법인과 공공기관만 입력 |
| 55 | `crprFndtCntplcSbst` | 법인기본연락처내용 | 200 |  |  |  | 법인과 공공기관만 입력(신주소) |
| 56 | `crprMntCntplcSbst` | 법인상세연락처내용 | 100 |  |  |  | 법인과 공공기관만 입력(신주소) |
| 57 | `custInfoChngYn` | 고객정보변경여부 | 1 |  |  |  | 고객정보 변경 시 필수값이 아닌 항목에 대해 Y:변경, N:변경한함 |
| 58 | `m2mHndsetYn` | M2M단말여부 | 1 |  |  |  | M2M단말인 경우 Y Y: M2M단말, N: 일반단말 |
| 59 | `agntCustNm` | 법정대리인 성명 | 60 |  | Y |  | 미성년자 개통일 경우 필수 |
| 60 | `agntCustIdfyNoType` | 법정대리인 식별번호 종류 | 1 |  |  |  | 미성년자 개통일 경우 필수 1 (주민번호) , 4(외국인등록번호) |
| 61 | `agntIdfyNoVal` | 법정대리인 고객식별번호 | 20 |  | Y |  | 고객식별번호 |
| 62 | `agntPersonSexDiv` | 법정대리인 성별 | 1 |  |  |  | 미성년자 개통일 경우 필수 1 : 남자, 2 : 여자 |
| 63 | `agntAgreYn` | 법정대리인 정보조회 동의여부 | 1 |  |  |  | 미성년자 개통일 경우 필수 Y:동의 N:미동의 |
| 64 | `agntTelAthn` | 법정대리인 연락처 종류 | 1 |  |  |  | 미성년자 개통일 경우 필수 M : 모바일, I : 자택 |
| 65 | `agntTelNo` | 법정대리인 연락처 | 20 |  | Y |  | ex) 01098841542 |
| 66 | `agntTypeCd` | 법정대리인 유형 | 2 |  |  |  | 미성년자 개통일 경우 필수 01(부), 02(모), 03(후견인), 04(연대보증인) |
| 67 | `agntRsdcrtIssuDate` | 법정대리인 식별번호 발급일자 | 8 |  |  |  | 미성년자 개통일 경우 필수 ex)20200105  법정대리인실명인증증빙서류코드에 따라  주민등록번호 / 외국인등록번호 /  운전면허 발급일자 필수 |
| 68 | `agntRlnamAthnEvdnPprCd` | 법정대리인실명인증증빙서류코드 | 5 |  |  |  | 주민등록증 : REGID 외국인등록번호 : FORGN 운전면허증 : DRIVE 주민등록증발급신청서 : REGAP 법정대리인의 경우 실명인증증비서류코드 필수. |
| 69 | `agntLicnsRgnCd` | 법정대리인 면허지역코드 | 2 |  |  |  | 법정대리인실명인증증빙서류코드가 DRIVE 인 경우 면허번호지역코드 필수 *코드정의서 참조 (LCNS_RGN_CD) |
| 70 | `agntLicnsNo` | 법정대리인 면허번호 | 20 |  | Y |  | 법정대리인실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 |
| 71 | `agntNationalityCd` | 법정대리인 국적코드 | 3 |  |  |  | 법정대리인실명인증증빙서류코드가 FORGN 인 경우 국적코드 필수 미입력시 미성년자 국적코드로 대체되어 업무처리 진행 됨. *코드정의서 참조(NATIONNALITY_CD) |
| 71 | `oderTypeCd` | 오더유형코드 | 3 |  |  | 우수기변:번호이동시 입력필드 | *코드정의서 참조(AC_ITG_ODER_TYPE_CD) 신규개통 : NAC 무선번호이동 : MNP |
| 72 | `npTlphNo` | 번호이동전화번호 | 11 |  | Y |  | 번호이동가입 필수 |
| 73 | `indvBizrYn` | 개인사업자여부 | 1 |  |  |  | 번호이동가입 필수 Y:개인사업자 |
| 74 | `bchngNpCommCmpnCd` | 변경전번호이동사업자코드 | 3 |  |  |  | 번호이동가입 필수 *코드정의서 참조 (KOS.AC_NP_COMM_CMPN_BAS) |
| 75 | `npRstrtnContYn` | 번호이동제한예외여부 | 1 |  |  |  | 번호이동가입 필수 Y:예외 N:대상아님 |
| 76 | `ytrpaySoffAgreYn` | 해지미환급금 상계동의여부 | 1 |  |  |  | 번호이동가입 필수 (계약유형이 선불인 경우에는 미동의) Y:동의 N:미동의 |
| 77 | `atmSeqNo` | atm일련번호 | 9 |  |  |  | 효성에서 ATM기기에서 가입시 ATM기기 일련번호  - 효성 ATM 기기만 필수값 |
| 78 | `mngmAgncId` | 관리대리점 아이디 | 10 |  |  |  | 개통요청 대리점 아아디 |
| 79 | `cntpntCd` | 접점코드 | 10 |  |  |  | 개통요청 접점코드 |
| 80 | `frmpapId` | 서식지 아이디 | 100 |  |  |  | 파일전송 장애발생 시 Null |
| 81 | `fnncDealAgreeYn` | 금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의 | 1 |  |  |  | 금융거래시 KT 고객 추가 금융 혜택을 위한 정보 제공 동의 미입력시 미동의로 연동 Y:동의 N:미동의 |
| 82 | `indvLoInfoPrvAgreeYn` | 개인위치정보제공동의여부 | 1 |  |  | 우수기변:신규 추가 | 개인위치정보 제3자 제공 동의 미입력시 미동의로 연동 Y:동의 N:미동의 *대상사업자 엠모바일(KIS). 이외 사업자 입력 시 미동의로 연동 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 14 | `O` |  | M-Platform 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |  |  |  |
| 1 | `M` |  | S: 접수정상, F:접수에러 |  |  |  |

### FOP0 — 서식지 개통(신규,번호이동)

| 항목 | 내용 |
|---|---|
| Service Name | `OsstFrmpapSvcPrcSOService` |
| Operation Name | `osstFrmpapOpenPrc / osstFrmpapNpOpenPrc` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `custNo` | 고객번호 | 9 | M |  | * 개통 사전체크 프로세스에서 취득한 custId |
| 3 | `billAcntNo` | 청구계정번호 | 11 | O |  | 고객이 보유한 기존 청구계정에 합산청구 시 기재 |
| 4 | `rqsshtPprfrmCd` | 청구서양식코드 | 2 | C |  | 신규 청구계정 생성(billAcntNo != null) 시 필수 LX : 우편 LC : 우편 + 이메일 CB : 이메일 MB : 모바일MMS * 코드정의서 참조(RQSSHT_PPRFRM_CD) |
| 5 | `rqsshtTlphNo` | 청구서 발송 전화번호 | 11 | C | Y | 신규 청구계정 생성(billAcntNo != null) && 청구서양식코드(rqsshtPprfrmCd)가 MMS 인 경우 필수 * 동일사업자 동일명의 전화번호(개통 신청 전화번호 포함) 기재 |
| 6 | `rqsshtEmlAdrsNm` | 청구서이메일주소명 | 100 | C | Y | 신규 청구계정 생성(billAcntNo != null) && 청구서양식코드(rqsshtPprfrmCd)가 LC 또는 CB인 경우 필수 * 이메일 입력 시 보안메일, 이벤트수신여부 자동 셋팅('Y') |
| 7 | `billZipNo` | 청구우편번호 | 6 | C |  | 신규 청구계정 생성(billAcntNo != null) 시 필수 |
| 8 | `billFndtCntplcSbst` | 청구지 주소 | 200 | C | Y | 신규 청구계정 생성(billAcntNo != null) 시 필수 * 신수조 입력 |
| 9 | `billMntCntplcSbst` | 청구지 주소 - 상세 | 100 | C | Y | 신규 청구계정 생성(billAcntNo != null) 시 필수 * 신수조 입력 |
| 10 | `blpymMthdCd` | 납부방법코드 | 1 | C |  | 신규 청구계정 생성(billAcntNo != null) 시 필수 C: 신용카드, D: 계좌자동이체, R: 지로 |
| 11 | `duedatDateIndCd` | 납기일자구분코드 | 2 | C |  | 신규 청구계정 생성(billAcntNo != null) 시 필수 납부방법코드(blpymMthdCd)가 D인 경우 27, 99 불가 납부방법코드(blpymMthdCd)가 R인 경우 99 불가 *코드정의서 참조(DUEDAT_DATE_IND_CD) |
| 12 | `crdtCardExprDate` | 신용카드만기일자 | 6 | C |  | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 인 경우 필수 YYYYMM 형식으로 입력 |
| 13 | `crdtCardKindCd` | 신용카드종류코드 | 2 | C |  | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 인 경우 필수 *코드정의서 참조(CRDT_CARD_KIND_CD) |
| 14 | `bankCd` | 은행코드 | 7 | C |  | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 D 인 경우 필수 *코드정의서 참조(BANK_CD) |
| 15 | `blpymMthdIdntNo` | 납부방법식별번호 | 20 | C | Y | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 필수 카드번호 또는 계좌번호 기재 |
| 16 | `blpymCustNm` | 납부고객명 | 60 | O | Y | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 기재 *미 입력 시 사전체크에 입력된 정보로 사용 |
| 17 | `blpymCustIdntNo` | 납부고객식별번호 | 20 | O | Y | 신규 청구계정 생성(billAcntNo != null) && 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 기재 *미 입력 시 사전체크에 입력된 정보로 사용 |
| 18 | `blpymMthdIdntNoHideYn` | 납부방법식별번호숨김여부 | 1 | O |  | 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 기재  Y:숨김, N:노출 |
| 19 | `bankSkipYn` | 은행건너뛰기여부 | 1 | O |  | 납부방법코드(blpymMthdCd)가 C 또는 D 인 경우 기재  Y: 건너뛰기, N:건너뛰지않음 * 미사용 컬럼 |
| 20 | `agreIndCd` | 동의자료코드 | 2 | C |  | 납부방법코드(blpymMthdCd)가 D 인 경우 필수 01: 서면, 02: 공인인증, 03:일반인증, 04: 녹취, 05:ARS |
| 21 | `myslAthnTypeCd` | 본인인증타입코드 | 2 | C |  | 동의자료코드(agreIndCd)가 03 인 경우 필수  01: SMS, 02: iPin, 03: 신용카드, 04: 범용공인인증 |
| 22 | `billAtchExclYn` | 청구서 동봉물 첨부제외여부 | 1 | O |  | Y:동봉물 제외, N:동봉물 포함 |
| 23 | `rqsshtTlphNoHideYn` | 청구서 전화번호 숨김여부 | 1 | O |  | Y:숨김, N:노출 |
| 24 | `rqsshtDsptYn` | 청구서발송여부 | 1 | O |  | Y: 발송안함, N:발송 |
| 25 | `enclBillTrmnYn` | 동봉 청구 해지여부 | 1 | O |  | Y:동봉청구해지, N:해지안함 |
| 26 | `realUseCustNm` | 실사용고객명 | 60 | C | Y | 법인,공공기관인 경우 필수  - 실사용자명 |
| 27 | `mngmAgncId` | 관리대리점아이디 | 10 | M |  | 사전체크시 입력한 대리점ID와 동일 |
| 28 | `cntpntCd` | 접점코드 | 10 | M |  | 사전체크시 입력한 접점코드와 동일 |
| 29 | `slsPrsnId` | 판매자아이디 | 15 | O |  |  |
| 30 | `iccId` | ICC아이디 | 19 | C | Y | USIM ICC_ID. 실물유심으로 개통시에 필수(eSIM사용==N일 경우 필수) eSIM사용 == Y일 경우 NULL 로 연동 |
| 31 | `eUiccId` | EID | 40 | C | Y | eSIM용. eSIM사용==Y일 경우 필수. 실물유심일경우 NULL연동 |
| 32 | `intmMdlId` | 기기모델아이디 | 8 | C |  | USIM단독개통의 경우 입력 불필요. eSIM사용 단말일 경우 필수 |
| 33 | `intmSrlNo` | 기기일련번호 | 19 | C |  | USIM단독개통의 경우 입력 불필요. eSIM사용 단말일 경우 필수 |
| 34 | `eSimOpenYn` | eSIM개통여부 | 1 | C |  | Y:eSIM사용, N:eSIM미사용(default) |
| 35 | `usimOpenYn` | 유심개통여부 | 1 | M |  | Y:USIM단독개통, N:default  (실물유심사용시에만 유효. eSIM은 단독개통 없음) |
| 36 | `motliSvcNo` | 모회선전화번호 | 11 | C | Y | 애플워치 개통시 필수. 연결할 모회선 전화번호. (원넘버부가 자동가입) |
| 37 | `spclSlsNo` | 특별판매번호 | 5 | M |  | 판매정책번호 |
| 38 | `agntRltnCd` | 대리인관계코드 | 2 | C |  | 법인, 미성년인 경우 필수 03: 법정대리인(미성년일 경우), 04: 위탁대리인, 06: 일회성대리인 |
| 39 | `agntBrthDate` | 대리인생일일자 | 8 | C | Y | 법인, 미성년인 경우 필수 |
| 40 | `agntCustNm` | 대리인고객명 | 60 | C | Y | 법인, 미성년인 경우 필수 |
| 41 | `homeTlphNo` | 자택전화번호 | 12 | C | Y | 법인인 경우 필수 |
| 42 | `wrkplcTlphNo` | 직장전화번호 | 12 | O | Y |  |
| 43 | `prttlpNo` | 이동전화번호 | 11 | C | Y |  |
| 44 | `prdcCd` | 상품코드 | 9 | M |  | 상품타입코드가 요금제(P)일 경우 필수  - 가입 하려는 상품 코드  - 상품타입코드(prdcTypeCd) 와 함께 Multi 로 연동 (샘플참조) |
| 45 | `prdcTypecd` | 상품타입코드 | 1 | M |  | P: 요금제, R: 부가서비스  - 상품코드(prdcCd) 와 함께 Multi 로 연동 (샘플참조) |
| 46 | `ftrNewParam` | 상품 파람 | 1 | C |  |  |
| 47 | `spnsDscnTypeCd` | 스폰서할인유형코드 | 2 | C |  | MVNO 스폰서2(=단통법시행후 생성 스폰서)의 스폰서 지원금 유형코드 KD : Basic Course, PM :단말지원(요금할인) *코드정의서 참조(SPNS_DSCN_TYPE_CD) |
| 48 | `agncSupotAmnt` | 대리점지원금액 | 15 | C |  |  |
| 49 | `enggMnthCnt` | 약정개월수 | 2 | C |  | 스폰서의 경우 필수 |
| 50 | `hndsetInstAmnt` | 단말기분납금액 | 15 | C |  | 단말할부 시 필수 (단위: 1,000원) |
| 51 | `hndsetPrpyAmnt` | 단말기선납금액 | 15 | C |  | 단말할부시 선납금액 존재 할 경우 필수  (단말가격 - 분납가격) |
| 52 | `instMnthCnt` | 분납개월수 | 2 | C |  | 단말할부 시 필수 |
| 53 | `usimPymnMthdCd` | USIM 수납방법코드 | 1 | M |  | R:즉납, B:후청구, N:비구매 *코드정의서 참조(수납방법코드) *eSIM개통시 즉납불가 |
| 54 | `sbscstPymnMthdCd` | 가입비 수납방법코드 | 1 | M |  | R:즉납, I:할부, P:면제 *코드정의서 참조(수납방법코드) |
| 55 | `sbscstImpsExmpRsnCd` | 가입비면제사유코드 | 2 | C |  | 가입비수납방법코드가 면제인 경우 필수  면제가 아닌 경우 Null로 연동  면제인 경우 '37'로 수록 |
| 56 | `bondPrsrFeePymnMthdCd` | 채권보전료수납방법코드 | 1 | C |  | 채권보전료를 납부 시 필수  R:즉납, B:익월청구 *코드정의서 참조(수납방법코드) |
| 57 | `tlphNo` | 전화번호 | 11 | M | Y | 실제 개통 전화번호 번호이동가입시에는 번호이동전화번호 |
| 58 | `sbscPrtlstRcvEmlAdrsNm` | 가입내역서수신이메일주소명 | 100 | O | Y |  |
| 59 | `npFeePayMethCd` | 번호이동수수료 수납방법코드 | 1 | C |  | 번호이동가입인 경우 필수 즉시납부(N), 후청구(Y) |
| 60 | `npBillMethCd` | 타사미청구금액 청구방법코드 | 1 | C |  | 번호이동가입인 경우 필수 즉시납부(N), 후청구(Y), 전사업자후청구(X) 전사업자 혹은 후사업자의 계약용도가 선불인경우는 전사업자후청구(X)만 가능 |
| 61 | `npHndsetInstaDuratDivCd` | 번호이동단말기분납지속구분코드 | 1 | C |  | 번호이동가입인 경우 필수 완납(1), 분납지속(LMS미청구)(2), 분납지속(LMS청구)(3)  타사 단말할부금 = 0  후불 -> 선불 일때, "2"(분납지속 LMS미청구)만 가능  ESLE,      "1"(완납) 만 가능  타사 단말할부금 > 0  후불 -> 후불 일때, 선택가능(1,2,3)  ELSE,     "2"(분납지속 LMS미청구)만 가능 |
| 62 | `rfundNpBankCd` | 번호이동환불은행코드 | 3 | O |  | *코드정의서참조 (CW_COMN_CD_BAS.CD_GROUP_ENG_NM = 'NP_BANK_CD') |
| 63 | `rfundBankBnkacnNo` | 번호이동환불계좌번호 | 20 | O | Y |  |
| 64 | `npTotRmnyAmt` | 번호이동총수납금액 | 15 | C |  | 번호이동가입인 경우 필수 납부금액이 없는 경우에는 0 |
| 65 | `npCashRmnyAmt` | 번호이동현금수납액 | 15 | C |  | 번호이동가입인 경우 필수 납부금액이 없는 경우에는 0 |
| 66 | `npCcardRmnyAmt` | 번호이동카드수납액 | 15 | C |  | 번호이동수납방법이 카드인 경우 필수 납부금액이 없는 경우에는 0 |
| 67 | `npRmnyMethCd` | 번호이동수납방법코드 | 2 | C |  | 번호이동가입시 납부금액이 있는 경우 현금(01), 현금+신용카드(03), 신용카드(02) |
| 68 | `npHndsetInstaLmsTlphNo` | 번호이동단말기분납문자명세서전화번호 | 11 | C | Y | 번호이동가입시 단말기분납지속(LMS청구)인 경우 필수 |
| 69 | `npCcardNo` | 번호이동카드번호 | 16 | C | Y | 번호이동수납방법이 카드인 경우 필수 |
| 70 | `npCcardExpirYm` | 번호이동카드유효기간(만기년월) | 4 | C |  | 번호이동수납방법이 카드인 경우 필수 |
| 71 | `npInslMonsNum` | 번호이동할부개월수 | 2 | C |  | 번호이동수납방법이 카드인 경우 필수 |
| 72 | `npCcardSnctTypeCd` | 번호이동결제유형코드 | 1 | C |  | 번호이동수납방법이 카드인 경우 필수 자동입력(2)/입력확인(1) |
| 73 | `npCcaardOwnrIdfyNo` | 번호이동카드명의인식별번호 | 10 | C | Y | 번호이동수납방법이 카드인 경우 필수 생년월일/사업자번호 |
| 74 | `npSgntInfo` | 번호이동서명정보 |  | C |  | 번호이동수납방법이 카드인 경우 필수 |
| 75 | `atmSeqNo` | atm일련번호 | 9 | C |  | 효성에서 ATM기기에서 가입시 ATM기기 일련번호 *MVNO사업자 무관 |
| 76 | `frmpapId` | 서식지 아이디 | 100 | M |  | 파일전송 장애발생 시 Null |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rslt` | 처리 결과 | 1 | M |  | S: 접수정상, F:접수에러 |
| 2 | `rsltMsg` | 처리 결과 메시지 |  |  |  | 처리 결과 메시지 |

### FHC0 — 서식지 기기변경 사전체크

| 항목 | 내용 |
|---|---|
| Service Name | `OsstFrmpapIcgMainPrcsService` |
| Operation Name | `icgFrmpapPreChk` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `mvnoOrdNo` | MVNO 오더 번호 | 14 |  |  | 우수기변:M / 일반기변:1 / 기변취소:osstOrdNo | MVNO에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `custNo` | 고객번호 | 9 |  |  | 우수기변:M / 일반기변:2 / 기변취소:rslt |
| 3 | `svcContId` | 서비스계약번호 | 15 |  |  | 우수기변:M / 일반기변:3 / 기변취소:rsltMsg |
| 4 | `tlphNo` | 전화번호 | 11 |  | Y | 우수기변:M | 실제 기기변경 전화번호 |
| 5 | `oderTypeCd` | 오더유형코드 | 3 |  |  | 우수기변:M | * 서식지 기기변경 처리는 우수고객기변(H01)만 가능 H01 : 우수고객기변 H11 : 일반기변기기변경 H07 : 기기변경취소 |
| 6 | `instYn` | 단말기할부여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 Y:할부, N:즉납  * 오더유형코드가 우수고객기변(H01) 만 해당 그 외 Y이면 오류 |
| 7 | `indvInfoInerPrcuseAgreYn` | 개인정보내부활용동의여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 개인정보수집및 활용에 동의 (반드시 Y로 입력되어야 함) |
| 8 | `crdtInfoAgreYn` | 신용정보동의여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 신용정보조회 및 이용에 대한 동의 (반드시 Y로 입력되어야 함) |
| 9 | `spamPrvdAgreYn` | 스팸제공동의여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 스팸정보를 KISA 제공 동의 Y:동의 N:미동의 |
| 10 | `custIdntNoIndCd` | 고객식별번호구분코드 | 2 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 * 코드정의서 참조 |
| 11 | `custIdntNo` | 고객식별번호 | 20 |  | Y | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 |
| 12 | `crprNo` | 법인번호 | 13 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 법인인 경우 필수 |
| 13 | `custNm` | 고객명 | 60 |  | Y | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 |
| 14 | `rsdcrtIssuDate` | 주민등록증발급일자 | 8 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 개인 내국인 : 주민등록증, 장애인등록증, 국가유공자증, 운전면허증 발급일자 개인 외국인 : 외국인등록증 발급일자 법인, 공공기관 : 사업자 교부일자 |
| 15 | `nativeRlnamAthnEvdnPprCd` | 실명인증증빙서류코드 | 5 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 * 코드정의서 참조 |
| 16 | `lcnsNo` | 면허번호 | 20 |  | Y | 우수기변:C | 오더유형코드가 'H01'이고 실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 |
| 17 | `lcnsRgnCd` | 면허지역코드 | 2 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 실명인증증빙서류코드가 DRIVE(운전면허증)인 경우 필수 *코드정의서 참조 |
| 18 | `mrtrPrsnNo` | 유공자번호 | 10 |  | Y | 우수기변:C | 오더유형코드가 'H01'이고 실명인증증빙서류코드가 MERIT(국가유공자)인 경우 필수 |
| 19 | `nationalityCd` | 국적코드 | 3 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 고객식별구분코드가 외국인인 경우 필수 *코드정의서 참조 |
| 20 | `fornBrthDate` | 외국인생일일자 | 8 |  | Y | 우수기변:C | 오더유형코드가 'H01'이고 실명인증증빙서류코드가 PPORT(외국인여권)인 경우 필수 * 형식 : YYYYMMDD |
| 21 | `athnRqstcustCntplcNo` | 인증요청고객연락처번호 | 12 |  | Y | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 |
| 22 | `myslAgreYn` | 본인동의여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 Y:동의, N:미동의 |
| 23 | `agntRltnCd` | 대리인관계코드 | 2 |  |  | 우수기변:C | 오더유형코드가 'H01','H11','H07'이고 법인인 경우 필수 (04: 위탁대리인  만 수록) *코드정의서 참조 |
| 24 | `agntBrthDate` | 대리인생년월일 | 6 |  | Y | 우수기변:C | 오더유형코드가 'H01','H11','H07'이고 법인인 경우 필수(YYMMDD) |
| 25 | `intmModelId` | 기기모델아이디 | 11 |  |  | 우수기변:M | 오더유형코드가 'H01','H11'인 경우 필수 |
| 26 | `intmSrlNo` | 기기일련번호 | 30 |  |  | 우수기변:M | 오더유형코드가 'H01','H11'인 경우 필수 |
| 27 | `prdcCd` | 상품코드 | 9 |  |  | 우수기변:C | 상품타입코드가 요금제(P)일 경우 필수  - 가입 하려는 상품 코드  - 상품타입코드(prdcTypeCd) 와 함께 Multi 로 연동 (샘플참조) |
| 28 | `prdcTypeCd` | 상품타입코드 | 1 |  |  | 우수기변:O | P: 요금제, R: 부가서비스  - 상품코드(prdcCd) 와 함께 Multi 로 연동 (샘플참조) |
| 29 | `iccId` | USIM 일련번호 | 30 |  | Y | 우수기변:C | 오더유형코드가 'H01','H11'인 경우 USIM변경시 필수 esimUseYn ='Y'일 경우 iccId NULL 연동 |
| 30 | `spclSlsNo` | 판매정책번호 | 9 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 |
| 31 | `spnsrTermTypeCd` | 스폰서약정 유형코드 | 3 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 KM1 : 알뜰폰스폰서 MC1 : 스마트스폰서(알뜰폰) MC2 : 스마트할인(알뜰폰) |
| 32 | `enggMnthCnt` | 약정 개월 수 | 2 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 스폰서 약정 개월 수 |
| 33 | `saleEngtOptnCd` | 할인 유형 코드 | 2 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 스폰서약정 유형코드가 KM1(알뜰폰스폰서)인 경우 KD : 베이직코스(공시지원금), PM : 요금할인(월25%할인) |
| 34 | `esimUseYn` | eSIM 사용여부 | 1 |  |  | 우수기변:C | 기변 단말의 eSIM 사용여부 (기변취소시 불필요) |
| 35 | `euiccId` | EID | 40 |  | Y | 우수기변:C | eSIM용. eSIM사용==Y일 경우 필수, 실물유심 사용일 경우 [euiccId NULL 연동] (기변취소시 불필요) |
| 36 | `cntpntCd` | 접점코드 | 10 |  |  |  | 기변요청 접점코드 |
| 37 | `frmpapId` | 서식지 아이디 | 100 |  |  |  |

### FHP0 — 서식지 기기변경 처리

| 항목 | 내용 |
|---|---|
| Service Name | `OsstFrmpapIcgMainPrcsService` |
| Operation Name | `icgFrmpapPrcTrt` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 |  |  | 우수기변:M / 일반기변:1 / 기변취소:rslt | OSST 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `custNo` | 고객번호 | 9 |  |  | 우수기변:M / 일반기변:2 / 기변취소:rsltMsg |
| 3 | `tlphNo` | 전화번호 | 11 |  | Y | 우수기변:M | 실제 기기변경 전화번호 |
| 4 | `svcContId` | 서비스계약번호 | 15 |  |  | 우수기변:M |
| 5 | `oderTypeCd` | 오더유형코드 | 3 |  |  | 우수기변:M | * 서식지 기기변경은 우수고객기변(H01)만 가능 H01 : 우수고객기변 H11 : 일반기변기기변경 H07 : 기기변경취소 |
| 6 | `instYn` | 단말기할부여부 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 Y : 할부, N : 즉납  * 우수고객기변(H01) 만 해당 그 외 Y이면 오류 |
| 7 | `hndsetInstAmnt` | 단말기분납금액 | 15 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 단말기할부여부가 'Y'인 경우 필수 * 단위: 100원 |
| 8 | `hndsetPrpyAmnt` | 단말기선납금액 | 15 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 단말기할부여부가 'Y' 인 경우 선납금액 입력 선납금 = 단말가격 - 분납가격 |
| 9 | `instMnthCnt` | 단말기할부개월수 | 2 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 단말할부 시 필수 |
| 10 | `usimPymnMthdCd` | USIM 수납방법 코드 | 1 |  |  | 우수기변:M | 오더유형코드가 'H01','H11'인 경우 필수 B:후청구, N:비구매 *코드정의서 참조(수납방법코드) |
| 11 | `sbscPrtlstRcvEmlAdrsNm` | 가입내역서수신이메일주소명 | 100 |  | Y | 우수기변:O | 오더유형코드가 'H01','H11'인 경우 해당 미 입력 시 메일 발송이 안됨 |
| 12 | `cntpntCd` | 접점코드 | 10 |  |  | 우수기변:M | 오더유형코드가 'H01','H11'인 경우 필수 |
| 13 | `slsPrsnId` | 판매자아이디 | 15 |  |  | 우수기변:O | 오더유형코드가 'H01'인 경우 |
| 14 | `spnsrTermTypeCd` | 스폰서약정 유형코드 | 3 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 KM1 : 알뜰폰스폰서 MC1 : 스마트스폰서(알뜰폰) MC2 : 스마트할인(알뜰폰) |
| 15 | `saleEngtOptnCd` | 할인 유형 코드 | 2 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 스폰서약정 유형코드가 KM1(알뜰폰스폰서)인 경우 KD : 베이직코스(공시지원금), PM : 요금할인(월25%할인) |
| 16 | `agncSupotAmnt` | 대리점지원금 | 6 |  |  | 우수기변:C | 오더유형코드가 'H01'이고 스폰서약정 유형코드가 KM1(알뜰폰스폰서)인 경우 KD : 베이직코스, TD : 심플코스,이면 필수 또는 0 |
| 17 | `enggMnthCnt` | 약정 개월 수 | 2 |  |  | 우수기변:M | 오더유형코드가 'H01'인 경우 필수 스폰서 약정 개월 수 |
| 18 | `engtGraceYn` | 약정유예 여부 | 1 |  |  | 우수기변:O | 오더유형코드가 'H01'인 경우 Y: 약정유예 , N: 약정유예 안함 미 입력 시 "N" 처리 됨 |
| 19 | `frmpapId` | 서식지 아이디 | 100 |  |  |  |

### FMC0 — 서식지 명의변경 사전체크

| 항목 | 내용 |
|---|---|

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `baseInfo (작업기준정보)` | mvnoOrdNo | MVNO 오더 번호 |  | M | 우수기변:MVNO에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) / 일반기변:1 / 기변취소:osstOrdNo |
| 11 | `rcvCustInfo (양수인 고객정보)` | custTypeCd | 양수인 고객유형코드 |  | M | 우수기변:* 코드정의서 참조 |
| 80 | `rcvBillAcntInfo(양수인 청구계정정보)` | rqsshtPprfrmCd | 청구서양식코드 |  | M | 우수기변:청구계정번호가 없으면 필수
우편(LX), 우편+이메일(LC), 이메일(CB) 명세서 가능
*2018.03.01부터 사용
모바일MMS(MB) 명세서 가능
*코드정의서 참조 |
| 102 | `prdcInfo` | prdcCd | 상품코드 |  | M | 우수기변:요금제 코드 : 요금제 변경이 없을 경우 null
 - 변경하려는 요금제 코드 |
| 105 | `inFrmpapDto` | cntpntCd | 접점 코드 |  | M | 우수기변:명의변경 요청 접점 코드 |

### FMP0 — 서식지 명의변경 처리

| 항목 | 내용 |
|---|---|

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `baseInfo` | osstOrdNo | OSST 오더 번호 |  | M | 우수기변:OSST 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) / 일반기변:1 / 기변취소:rslt |
| 17 | `inFrmpapDto` | frmpapId | 서식지 아이디 |  | M |  |

### EFS0 — 기타 서식지 목록조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstEtcFrmpapService` |
| Operation Name | `osstEtcFrmpapListRetv` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `retvStrtDt` | 조회시작일시 | 8 |  |  | 우수기변:List / 일반기변:1.0 / 기변취소:custNm | yyyyMMdd (현재일시보다 큰 경우 불가) |
| 2 | `retvEndDt` | 조회종료일시 | 8 |  |  | 일반기변:2.0 / 기변취소:orgnNm | yyyyMMdd (현재일시보다 큰 경우 불가) |
| 3 | `oderType` | 업무유형 | 3 |  |  | 일반기변:3.0 / 기변취소:oderType | ET1 : (기타) SUS : (정지) > 추후오픈 대상 서비스 CCN : (번호변경) > 추후오픈 대상 서비스 CAN : (해지) > 추후오픈 대상 서비스 |
| 4 | `othrTrtmPrsnCvrYn` | 타처리자 서식지 조회여부 | 1 |  |  | 일반기변:4.0 / 기변취소:docCnt | Y : 포함 N : 미포함 타처리가 등록한 서식지 조회가능여부 |
| 5 | `mngmAgncId` | 서식지등록 대리점아이디 | 20 |  |  | 일반기변:5.0 / 기변취소:wapplRegDate |
| 6 | `retvSeq` | 조회시작번호 | 5 |  |  | 일반기변:6.0 / 기변취소:fxdformIngrsCdNm | 조회시작번호(ex. 0) List에 나오는 최초 Row의 번호 미 입력시 0 |
| 7 | `retvCascnt` | 조회건수 | 5 |  |  | 일반기변:7.0 / 기변취소:userNm | 조회건수(ex. 30) List에 나오는 데이터 Row 수 미 입력시 30 |

### EFS1 — 기타첨부

| 항목 | 내용 |
|---|---|
| Service Name | `OsstEtcFrmpapService` |
| Operation Name | `osstEtcFrmpapTrt` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `frmpapId` | 서식지아이디 | 100 | M |  | ex ) 0x648BA7B031F411EF8F D80080C74455C600 |
| 5 | `mngmAgncId` | 서식지등록 대리점아이디 | 20 | M |  |  |
| 6 | `othrTrtmPrsnCvrYn` | 타처리자 서식지 처리여부 | 1 | M |  | Y : 가능 N : 불가능 타처리자가 등록한 서식지 기타첨부 여부 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1.0 | `rsltCd` | 처리결과코드 |  | M |  | 00 : 성공, 99 : 실패 |
| 2.0 | `rsltMsg` | 처리결과메세지 |  | M |  |  |

### EFS2 — 기타첨부 결과조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstEtcFrmpapService` |
| Operation Name | `osstEtcFrmpapAtcStatRetv` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 |  |  | 우수기변:List / 일반기변:1.0 / 기변취소:itgOderTypeNm |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 |  |  | 일반기변:2.0 / 기변취소:custNm |
| 3 | `ctn` | 사용자 전화번호 | 11 |  | Y | 일반기변:3.0 / 기변취소:svcContDivNm |
| 4 | `retvSeq` | 조회시작번호 | 8 |  |  | 일반기변:4.0 / 기변취소:svcNo | 조회시작번호(ex. 0) List에 나오는 최초 Row의 번호 미 입력시 0 |
| 5 | `retvCascnt` | 조회건수 | 8 |  |  | 일반기변:5.0 / 기변취소:mskRcprId | 조회건수(ex. 30) List에 나오는 데이터 Row 수 미 입력시 30 |

### EFS3 — 기타첨부 해지

| 항목 | 내용 |
|---|---|
| Service Name | `OsstEtcFrmpapService` |
| Operation Name | `osstEtcFrmpapTrmn` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `frmpapId` | 서식지아이디 | 100 | M |  | ex) 0x648BA7B031F411EF8F D80080C74455C600 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1.0 | `rsltCd` | 처리결과코드 |  | M |  | 00 : 성공, 99 : 실패 |
| 2.0 | `rsltMsg` | 처리결과메세지 |  | M |  |  |

### T01 — 유심무상교체 접수가능 여부조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstUsimChgAcceptSO` |
| Operation Name | `retvUsimChgAcceptPsbl` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `clntIp` | Client IP | 20 | M |  |  |
| 5 | `clntUsrId` | 사용자 User ID | 50 | M |  |  |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rsltCd` | 결과코드 | 2 | M |  | 00:가능 99:불가 |
| 2 | `rsltMsg` | 결과메세지 | 100 | O |  | 불가시 불가 사유 메시지 |
| 3 | `openDt` | 최초개통일자 | 8 | M |  | YYYYMMDD |
| 4 | `usimChgDt` | 최근 유심 변경 일자 | 14 | M |  | YYYYMMDDHH24MISS |
| 5 | `acceptDt` | 유심무상교체 신청 일시 | 14 | O |  | YYYYMMDDHH24MISS |

### T02 — 유심무상교체 신청내역 전송

| 항목 | 내용 |
|---|---|
| Service Name | `OsstUsimChgAcceptSO` |
| Operation Name | `usimChgAccept` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `custId` | 고객번호 | 9 | M |  |  |
| 2 | `ncn` | 사용자 서비스계약번호 | 9 | M |  |  |
| 3 | `ctn` | 사용자 전화번호 | 11 | M | Y |  |
| 4 | `clntIp` | Client IP | 20 | M |  |  |
| 5 | `clntUsrId` | 사용자 User ID | 50 | M |  |  |
| 6 | `acceptDt` | 유심무상교체 신청일시 | 14 | M |  | 과거일자도 가능, YYYYMMDDHH24MISS |
| 7 | `acceptChCd` | 신청채널 | 4 | M |  | HOME : 사업자 홈페이지 CENT : 사업자 고객센터 ORGD : 사업자 대리점 |
| 8 | `deliveryDivCd` | 배송방식 | 4 | M |  | DLVR : 바로배송유심  MAKT : 바로유심  POST : 사업자배송 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rsltCd` | 결과코드 | 2 | M |  | 00:접수성공 99:접수실패 |
| 2 | `rsltMsg` | 결과메세지 | 100 | O |  | 접수 실패시 사유 메시지 |

### FS8 — 고객 안면인증 URL 요청

| 항목 | 내용 |
|---|---|

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `CustFathInfoDTO` | orgId | 대리점아이디 |  | M | 일반기변:1 / 기변취소:urlAdr |
| 11 | `PhotoAthnRqtInDTO (대면 개통(onlineOfflnDivCd=OFFLINE)이고, MIS에서 사진인증이 필요한 경우(photoAthnNcstYn=Y) 해당 DTO 하위 항목 필수)` | photoAthnRqtDivCd | 사진인증요청구분코드 |  | C | 우수기변:01 : 개통전
02 : 개통후 재처리
    사후재처리의 일환으로서 신규개통, 명변등 개통이후에 신분증이 변경되었거나 
    재처리를 할 때
11 : CDM재처리
12 : 오더재처리 |

### FS9 — 고객 안면인증 내역 조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstCustFathMgmtService` |
| Operation Name | `custFathTxnRetv` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `fathTransacId` | 안면인증트랜잭션아이디 | 30 |  |  | 우수기변:1 / 일반기변:fathTransacId / 기변취소:안면인증트랜잭션아이디 | 안면인증트랜잭션아이디 |
| 2 | `frmpapId` | 서식지아이디 | 100 |  |  | 우수기변:2 / 일반기변:fathProgrStepCd / 기변취소:안면인증진행단계코드 | 서식지 ID |
| 3 | `retvDivCd` | 조회 구분 코드 | 1 |  |  | 우수기변:3 / 일반기변:frmpapId / 기변취소:서식지아이디 | 1 : 안면인증 트랜잭션 아이디로 조회 2 : 서식지아이디의 최종 안면인증 내역 조회 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 30 | `M` |  | 안면인증트랜잭션아이디 |  |  |  |
| 2 | `M` |  | 00 URL생성 01 고객거리제한확인 02 안면인증 서식지 아이디 맵핑 03 MIS사진인증수행, 이미지전송 04 MIS안면인증결과수신 05 서비스계약아이디 맵핑 06 안면인증 서식지아이디 매핑해제(서식지첨부해제 등) 07 서비스계약아이디 매핑해제(기변취소,명변취소등) 08 MIS얼굴이미지요청 09 서식지아이디 초기화 |  |  |  |
| 100 | `O` |  | 서식지ID |  |  |  |

### FT0 — 고객 안면인증 대상 여부 조회

| 항목 | 내용 |
|---|---|
| Service Name | `OsstCustFathMgmtService` |
| Operation Name | `custFathTgtYnRetv` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `onlineOfflnDivCd` | 온라인오프라인구분코드 | 100 | M |  | 온라인 오프라인 구분코드 ONLINE - 온라인 채널(비대면) OFFLINE - 오프라인 채널(대면) |
| 2 | `orgId` | 대리점아이디 | 100 | M |  |  |
| 3 | `retvCdVal` | 조회코드값 | 100 | M |  | 신분증유형코드 REGID - 주민등록증 DRIVE - 운전면허증 HANDI - 장애인등록증 MERIT - 국가유공자증 NAMEC - 대한민국여권 |
| 4 | `cpntId` | 접점아이디 | 10 | M |  |  |
| 5 | `fathRglsEnvTestYn` | 안면인증상용환경테스트여부 | 1 | C |  | onlineOfflnDivCd=ONLINE(비대면)이고, 안면인증 미대상 사업자이나 해당 값 Y면, 안면인증 대상 사업자인 것 처럼 처리. Y 외의 값은 무시됨. |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `trtResltCd` | 처리결과코드 | 4 | M |  | 0000 안면인증 필수 CD01 안면인증 불필요 - 오픈일자 미도래 CD02 안면인증 불필요 - 안면인증 스킵 권한 보유자 CD03 안면인증 미오픈 대면 사업자 CD04 안면인증 불필요 - MIS 장애 스킵 개통 불가 CD05 안면인증 필수 - MIS 장애 스킵 CD06 안면인증 불필요 - 안면인증 미대상 접점 CD07 안면인증 불필요 - 안면인증 미대상 신분증 CD08 안면인증 불필요 - 안면인증 실패 스킵 권한 보유자 CD09 안면인증 불필요 - 대리인 개통 안면인증 스킵 권한 보유자   * 안면인증 필수 = url요청(FS8)을 반드시 호출 하고 오더 처리 시 안면인증트랜잭션ID 수록  * 응답값 CD02 / CD08 / CD09 의 경우, 온라인 개통/명변/우수기변 시 KOS를 통해서 처리 필수(MP 통해 오더 업무 처리 불가)  * 응답값 CD04 의 경우, MIS장애로 오더 업무 불가한 상태.  이후 MIS장애가 해소되면 안면인증 대상여부 조회 재수행 필수. |
| 2 | `trtResltSbst` | 처리결과내용 | 2000 | M |  | 안면인증 미대상 사유 |
| 3 | `stbznPerdYn` | 안정화기간여부 | 1 | M |  | 안면인증 내역 실패케이스 오더 업무처리 가능 여부 Y : 안정화 기간 적용 N : 안정화 기간 미적용 |

### FT1 — 고객 안면인증 SKIP 요청

| 항목 | 내용 |
|---|---|
| Service Name | `OsstCustFathMgmtService` |
| Operation Name | `custFathTxnSkipReq` |

**Request (MVNO → M-Platform)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `fathTransacId` | 안면인증트랜잭션아이디 | 30 | C |  | 안면인증트랜잭션아이디 |

**Response (M-Platform → MVNO)**

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `rsltCd` | 결과코드 | 4 | M |  | 0000 : 정상 0001 : M플랫폼을 통해 처리한 안면인증만 SKIP처리가 가능합니다. 0002 : 이미 SKIP처리된 안면인증 트랜잭션 ID 입니다. 0003 : 존재하지 않는 안면인증 트랜잭션 ID 입니다. 0004 : 안정화 기간만 SKIP이 가능합니다. 0005 : 안면인증 대기중인 상태만 SKIP 처리가 가능합니다. |

---

### 7.2 In-Bound 처리결과 (TCP Socket)

> KT M-Platform에서 MVNO로 TCP Socket 방식으로 비동기 전송하는 처리결과 전문입니다.

#### 개통처리결과(기존)

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `mvnoOrdNo` | MVNO 오더 번호 | 14 | M |  | MVNO 에서 생성한 오더번호 (ex. YYYYMMDD+Seq6자리) |
| 3 | `prgrStatCd` | 진행상태코드 | 3 | M |  | PC0: 사전체크접수, PC1:사전체크 중, PC2: 사전체크완료 OP0: 개통 접수, OP1: 개통 진행 중, OP2: 개통작업 완료 CP0: 지능망직권해지접수, CP1: 지능망직권해지 진행 중, CP2: 지능망직권해지 완료 |
| 4 | `custId` | 고객번호 | 9 | O |  |  |
| 5 | `svcCntrNo` | 서비스계약번호 | 9 | O |  |  |
| 6 | `rsltCd` | 처리결과코드 | 4 | M |  | 0000 : 정상 3060 : 가입내역안내서 발송에러(KOS 전산 개통완료) 3070 : OTA 발송 에러(KOS 전산 개통완료) 1000~ : 수신정보 오류 (ex. 필수항목 누락등) 2000~ : 사전체크 KOS 연동 중 오류 (ex. 미납고객등) 3000~ : 개통 KOS 연동 중 오류 (ex. 개통 불가 고객) 9000~ : 시스템 오류 (KOS 연동 NW 오류등) |
| 7 | `rsltMsg` | 처리결과내용 | 500 | M |  | 처리결과 메시지(정상 or 에러) |
| 8 | `rsltDt` | 처리일시 | 14 | M |  | YYYYMMDDHH24MISS |
| 9 | `nstepGlobalId` | NSTEP 에러 Global ID | 25 | O |  | KOS연동 중 에러 발생 시 Global_No |
| 10 | `prdcChkNotiMsg` | 상품체크안내메시지 | 1500 | O |  | 가입상품에 대한 안내메시지 - 상품코드,메시지\|상품코드,메시지… (“\|” 으로 구분)   (ex. AAAAAAAAA,해당 요금상품으로 변경할 경우 결합상품 할인이 자동으로 해지됩니다.\|BBBBBBBBB,완전자유존 가입을 감사드립니다. 완전자유존에서 링투유 음악을 무료로 설정하기 위해 링투유 가입 바랍니다.) |
| 11 | `npBchngCmpnCntrTypeCd` | 번호이동변경전사업자계약유형코드 | 1 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 0:선불, 1:후불 |
| 12 | `npFee` | 번호이동수수료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 |
| 13 | `npNchrgChageAmt` | 번호이동타사미청구금액 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 14 | `npPenltAmt` | 번호이동위약금 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 15 | `npNpayAmt` | 번호이동미납금액 | 15 | O |  | 번호이동가입이고 NP인증 실패 사유가 미납고객인 경우 번호이동 NP인증결과 |
| 16 | `npHndsetInslAmt` | 번호이동단말기할부금 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 17 | `npPrepayAmt` | 번호이동선납금액 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 18 | `npBasChage` | 번호이동기본료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 19 | `npNtnlTlkChage` | 번호이동국내통화료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 20 | `npIntlTlkChage` | 번호이동국제통화료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 21 | `npAdtnUseChage` | 번호이동부가사용료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 22 | `npEtcUseChage` | 번호이동기타사용료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 23 | `npVat` | 번호이동부가세 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 24 | `npRmnyTgtStDt` | 번호이동수납대상시작일자 | 8 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 25 | `npRmnyTgtFnsDt` | 번호이동수납대상종료일자 | 8 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |

#### 개통처리결과(신규)

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `osstOrdNo` | OSST 오더 번호 | 14 | M |  | OSST 에서 생성한 오더번호 (ex. YYYYMMDD+seq6자리) |
| 2 | `mvnoOrdNo` | MVNO 오더 번호 | 14 | M |  | MVNO 에서 생성한 오더번호 (ex. YYYYMMDD+Seq6자리) |
| 3 | `prgrStatCd` | 진행상태코드 | 3 | M |  | PC0: 사전체크접수, PC1:사전체크 중, PC2: 사전체크완료 OP0: 개통 접수, OP1: 개통 진행 중, OP2: 개통작업 완료 CP0: 지능망직권해지접수, CP1: 지능망직권해지 진행 중, CP2: 지능망직권해지 완료 |
| 4 | `custId` | 고객번호 | 9 | O |  |  |
| 5 | `svcCntrNo` | 서비스계약번호 | 9 | O |  |  |
| 6 | `rsltCd` | 처리결과코드 | 4 | M |  | 0000 : 정상 3060 : 가입내역안내서 발송에러(KOS 전산 개통완료) 3070 : OTA 발송 에러(KOS 전산 개통완료) 1000~ : 수신정보 오류 (ex. 필수항목 누락등) 2000~ : 사전체크 KOS 연동 중 오류 (ex. 미납고객등) 3000~ : 개통 KOS 연동 중 오류 (ex. 개통 불가 고객) 7001~7004: 실명인증 오류 신규 추가. [실명인증 실패 응답코드 구분] 쉬트 참조 9000~ : 시스템 오류 (KOS 연동 NW 오류등) |
| 7 | `rsltMsg` | 처리결과내용 | 500 | M |  | 처리결과 메시지(정상 or 에러) |
| 8 | `rsltDt` | 처리일시 | 14 | M |  | YYYYMMDDHH24MISS |
| 9 | `nstepGlobalId` | NSTEP 에러 Global ID | 25 | O |  | KOS연동 중 에러 발생 시 Global_No |
| 10 | `prdcChkNotiMsg` | 상품체크안내메시지 | 1500 | O |  | 가입상품에 대한 안내메시지 - 상품코드,메시지\|상품코드,메시지… (“\|” 으로 구분)   (ex. AAAAAAAAA,해당 요금상품으로 변경할 경우 결합상품 할인이 자동으로 해지됩니다.\|BBBBBBBBB,완전자유존 가입을 감사드립니다. 완전자유존에서 링투유 음악을 무료로 설정하기 위해 링투유 가입 바랍니다.) |
| 11 | `npBchngCmpnCntrTypeCd` | 번호이동변경전사업자계약유형코드 | 1 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 0:선불, 1:후불 |
| 12 | `npFee` | 번호이동수수료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 |
| 13 | `npNchrgChageAmt` | 번호이동타사미청구금액 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 14 | `npPenltAmt` | 번호이동위약금 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 15 | `npNpayAmt` | 번호이동미납금액 | 15 | O |  | 번호이동가입이고 NP인증 실패 사유가 미납고객인 경우 번호이동 NP인증결과 |
| 16 | `npHndsetInslAmt` | 번호이동단말기할부금 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 17 | `npPrepayAmt` | 번호이동선납금액 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 18 | `npBasChage` | 번호이동기본료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과 |
| 19 | `npNtnlTlkChage` | 번호이동국내통화료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 20 | `npIntlTlkChage` | 번호이동국제통화료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 21 | `npAdtnUseChage` | 번호이동부가사용료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 22 | `npEtcUseChage` | 번호이동기타사용료 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 23 | `npVat` | 번호이동부가세 | 15 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 24 | `npRmnyTgtStDt` | 번호이동수납대상시작일자 | 8 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 25 | `npRmnyTgtFnsDt` | 번호이동수납대상종료일자 | 8 | O |  | 번호이동가입이고 NP인증 응답이 성공인 경우 번호이동 NP인증결과_타사미청구금액 상세내역 |
| 26 | `dclaDeedEfctDate` | 외국인 체류 만료 일자 | 8 | O |  | YYYYMMDD |
| 27 | `sbscLmtQnty` | 가입한도수량 | 9 | O |  | 가입한도수량 |
| 28 | `sbscCircuitNum` | 현재가입수량 | 9 | O |  | 현재 가입 수량 |
| 29 | `dlnqAmt` | 미납금액 | 9 | O |  | 미납금액 |
| 30 | `eIccId` | eSIM ICC ID | 19 | O |  | eSIM개통처리시 download 된 ICC ID |

#### 해지처리결과

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `motSize` | 전문길이 | 4 | M |  | 전문의 전체 자릿수 (한글은 2byte로 처리) |
| 2 | `motType` | 전문종류 | 4 | M |  | CPRC: 해지사전체크 CRST : 해지 |
| 3 | `motSbst` | 전문내용 |  | M |  | key=value 구조, 구분자: \| ex) OSST_ORD_NO=20160527123456\|PRGR_STAT_CD=CP2 \|CUST_NO=63302296\|SVC_CNTR_NO=641161719\|TLPH_NO=01023541238\| RSLT_CD=0000\|RSLT_MSG=결과메시지  * 전문예시 참조  참고사항: PRGR_STAT_CD(진행상태코드) 값:    EP0 : 해지 접수   EP1: 해지 사전체크 중   EP2:  해지 사전체크 실패   EP3:  해지 처리 중   EP4:  해지 처리/실패 완료 |

#### 기변결과

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `motSize` | 전문길이 | 4 | M |  | 전문의 전체 자릿수 (한글은 2byte로 처리) |
| 2 | `motType` | 전문종류 | 4 | M |  | HC0N: 기기변경사전체크 HP0N: 기기변경저장 |
| 3 | `motSbst` | 전문내용 |  | M |  | key=value 구조, 구분자: \|  ex) OSST_ORD_NO=20160527123456\|PRG_STAT_CD=HC0 \|CUST_NO=63302296\|SVC_CNTR_NO=641161719\|TLPH_NO=01023541238\|RSLT_CD=0000\|RSLT_MSG=결과메시지  * 전문예시 참조 |
| 1 | `OSST_ORD_NO` | OSST주문번호 | 14 | M |  |  |
| 2 | `PRGR_STAT_CD` | 처리상태 | 3 | M |  |  |
| 3 | `CUST_NO` | 고객번호 | 9 | M |  |  |
| 4 | `SVC_CNTR_NO` | 서비스계약번호 | 9 | M |  |  |
| 5 | `TLPH_NO` | 서비스번호 | 11 | M |  |  |
| 6 | `TRGT_ATRIB_SBST` | 사전체크 결과 | 1 | C |  | 사전체크일경우 N : 불가, Y : 가능 |
| 7 | `TRGT_FALU_MSG` | 사전체크 불가 메시지 | 1000 | C |  | 사전체크 결과 'N'인경우 불가 메시지 |
| 8 | `TRGT_INSUR_MSG` | 사전체크 안내 메시지 | 1000 | C |  | 사전체크 결과 'Y'인경우 안내 메시지 |
| 10 | `GARN_ICOM_INSL_POSBL_CIRCUIT` | 할부 가능 회선수 | 2 | C |  | 할부 가능 회선수 |
| 11 | `GARN_ICOM_CIRCUIT_WHOLE` | 할부 전체 회선수 | 2 | C |  | 할부 전체 회선수 |
| 12 | `GARN_ICOM_CIRCUIT_KT` | 할부 회선수 자사_KT | 2 | C |  | 할부 회선수 자사_KT |
| 13 | `GARN_ICOM_CIRCUIT_OTCOM` | 할부 회선수 타사 | 2 | C |  | 할부 회선수 타사 |
| 13 | `ENGG_PNLT_AMNT` | 약정위약금 | 9 | C |  | 약정위약금 |
| 18 | `RSLT_CD` | 결과코드 | 4 | M |  |  |
| 19 | `RSLT_MSG` | 결과메시지 | 100 | M |  |  |
| 20 | `MSTR_TOT_AMT` | 위약금 부과금액 - 총금액 | 9 | O |  | 유예 불가하여 부과되는 금액 |
| 21 | `MSTR_INTM_PENLT` | 위약금 부과금액 - 지원금 위약금 | 9 | O |  | 유예 불가하여 부과되는 금액 |
| 22 | `MSTR_INTM_GRACE_PENLT` | 위약금 부과금액 - 지원금 위약금(유예) | 9 | O |  | 유예 불가하여 부과되는 금액 |
| 23 | `MSTR_CHAGE_DC_PENLT` | 위약금 부과금액 - 요금위약금 | 9 | O |  | 유예 불가하여 부과되는 금액 |
| 24 | `MSTR_CHAGE_DC_GRACE_PENLT` | 위약금 부과금액 - 요금 위약금(유예) | 9 | O |  | 유예 불가하여 부과되는 금액 |
| 25 | `MSTR_BFDC_AMT` | 위약금 부과금액 - 단말기 할인2/선할인 | 9 | O |  | 유예 불가하여 부과되는 금액 |
| 26 | `GRACE_TOT_AMT` | 위약금 유예금액 - 총금액 | 9 | O |  | 유예 후 잔여 약정기간 내 해지시 부과 |
| 27 | `GRACE_INTM_PENLT` | 위약금 유예금액 - 지원금 위약금 | 9 | O |  | 유예 후 잔여 약정기간 내 해지시 부과 |
| 28 | `GRACE_CHAGE_DC_PENLT` | 위약금 유예금액 - 요금 위약금 | 9 | O |  | 유예 후 잔여 약정기간 내 해지시 부과 |
| 29 | `ENGT_GRACE_POSBL_YN` | 약정 유예 여부 | 1 | O |  | Y: 가능, N: 불가 |
| 30 | `ENGT_GRACE_FALU_MSG` | 약정 유예 불가 사유 | 100 | O |  |  |
| 31 | `eIccId` | eSIM ICC ID | 19 | O |  | eSIM개통처리시 download 된 ICC ID |

#### 기변취소결과

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `motSize` | 전문길이 | 4 | M |  | 전문의 전체 자릿수 (한글은 2byte로 처리) |
| 2 | `motType` | 전문종류 | 4 | M |  | HC0C: 기기변경취소사전체크 HP0C : 기기변경취소저장 |
| 3 | `motSbst` | 전문내용 |  | M |  | key=value 구조, 구분자: \|  ex) OSST_ORD_NO=20160527123456\|PRG_STAT_CD=HP2 \|CUST_NO=63302296\|SVC_CNTR_NO=641161719\|TLPH_NO=01023541238\|RSLT_CD=0000\|RSLT_MSG=결과메시지  * 전문예시 참조 |
| 1 | `OSST_ORD_NO` | OSST주문번호 | 14 | M |  |  |
| 2 | `PRGR_STAT_CD` | 처리상태 | 3 | M |  |  |
| 3 | `CUST_NO` | 고객번호 | 9 | M |  |  |
| 4 | `SVC_CNTR_NO` | 서비스계약번호 | 9 | M |  |  |
| 5 | `TLPH_NO` | 서비스번호 | 11 | M |  |  |
| 6 | `TRGT_ATRIB_SBST` | 사전체크 결과 | 1 | M |  | N : 불가, Y : 안내 |
| 7 | `TRGT_FALU_MSG` | 사전체크 불가 메시지 | 1000 | C |  | 사전체크 결과 'N'인경우 불가 메시지 |
| 7 | `TRGT_INSUR_MSG` | 사전체크 안내 메시지 | 1000 | C |  | 사전체크 결과 'Y'인경우 안내 메시지 |
| 8 | `RSLT_CD` | 결과코드 | 4 | M |  |  |
| 9 | `RSLT_MSG` | 결과메시지 | 100 | M |  |  |

#### 유심변경결과

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `motSize` | 전문길이 | 4 | M |  | 전문의 전체 자릿수 (한글은 2byte로 처리) |
| 2 | `motType` | 전문종류 | 4 | M |  | UC0N: 유심변경 사전체크 및 처리 결과 |
| 3 | `motSbst` | 전문내용 |  | M |  | key=value 구조, 구분자: \|  ex) OSST_ORD_NO=20160527123456\|PRGR_STAT_CD=HC0\|CUST_NO=63302296\|SVC_CNTR_NO=641161719\|TLPH_NO=01023541238\|RSLT_CD=0000\|RSLT_MSG=결과메시지  * 전문예시 참조 |
| 1 | `OSST_ORD_NO` | OSST주문번호 | 14 | M |  |  |
| 2 | `PRGR_STAT_CD` | 처리상태 | 3 | M |  |  |
| 3 | `CUST_NO` | 고객번호 | 9 | M |  |  |
| 4 | `SVC_CNTR_NO` | 서비스계약번호 | 9 | M |  |  |
| 5 | `TLPH_NO` | 서비스번호 | 11 | M |  |  |
| 6 | `TRGT_ATRIB_SBST` | 사전체크 결과 | 1 | C |  | 사전체크일경우 N : 불가, Y : 가능 |
| 7 | `TRGT_FALU_MSG` | 사전체크 불가 메시지 | 1000 | C |  | 사전체크 결과 'N'인경우 불가 메시지 |
| 8 | `TRGT_INSUR_MSG` | 사전체크 안내 메시지 | 1000 | C |  | 사전체크 결과 'Y'인경우 안내 메시지 |
| 9 | `ICC_ID` | ICC_ID | 19 | C |  | *eSim 변경시에만 ICC_ID 제공 |
| 10 | `RSLT_CD` | 결과코드 | 4 | M |  |  |
| 11 | `RSLT_MSG` | 결과메시지 | 100 | M |  |  |

#### 해지취소결과

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `motSize` | 전문길이 | 4 | M |  | 전문의 전체 자릿수 (한글은 2byte로 처리) |
| 2 | `motType` | 전문종류 | 4 | M |  | RC0N: 해지취소사전체크 RP0N : 해지취소 |
| 3 | `motSbst` | 전문내용 |  | M |  | key=value 구조, 구분자: \|  ex) OSST_ORD_NO=20160527123456\|PRGR_STAT_CD=RC2\| CUST_ID=63302296\|SVC_CONT_ID=641161719\|TLPH_NO=01023541238\|RSLT_CD=0000\|RSLT_MSG=결과메시지  * 전문예시 참조 |
| 1 | `OSST_ORD_NO` | OSST오더번호 | 14 | M |  |  |
| 2 | `PRGR_STAT_CD` | 처리상태 | 3 | M |  | RC0: 해지취소사전체크접수 RC1:사전체크처리중 RC2:사전체크완료 RP0 : 해지취소접수 RP1:해지취소처리중  RP2:처리완료 |
| 3 | `CUST_ID` | 고객ID | 11 | M |  |  |
| 4 | `SVC_CNTR_ID` | 서비스계약ID | 20 | M |  |  |
| 5 | `TLPH_NO` | 전화번호 | 11 | M |  |  |
| 6 | `RSLT_CD` | 결과코드 | 4 | M |  | 0000 : 정상 |
| 7 | `RSLT_MSG` | 결과메시지 | 100 | M |  | 결과 내용 |
| 8 | `TRGT_ATRIB_SBST` | 사전체크 결과 | 1 | C |  | 해지취소 사전체크일경우 N : 불가, Y : 가능 |
| 9 | `TRGT_FALU_MSG` | 사전체크 불가 메시지 | 1000 | C |  | 해지취소사전체크 결과 'N'인경우 불가 메시지 |
| 10 | `TRGT_INSUR_MSG` | 사전체크 안내 메시지 | 2000 | C |  | 해지취소사전체크 결과 'Y'인경우 안내 메시지 |
| 11 | `PREPAY_ITEM` | 해지선납 항목 | 1000 | O |  | 해지시 즉납처리한 요금항목. (해지선납금/단말기할부금…) 구분자 '&' 로 선납항목구분. * 해지선납업무정의참조=> ex) PREPAY_ITEM=해지선납금&단말기할부금&위약금 |
| 12 | `PREPAY_AMT` | 해지선납 금액 | 500 | O |  | ex) PREPAY_AMT=15400&150000&67000 |
| 13 | `PREPAY_MTHD` | 해지선납 방법 | 1000 | O |  | ex) PREPAY_PYMN_MTHD=현금&신용카드&신용카드 |
| 14 | `SPNSR_NM` | 스폰서명 | 100 |  |  |  |
| 15 | `SPNSR_ST_DT` | 스폰서 가입일 | 8 |  |  | ex) 20220213 |
| 16 | `SPNSR_PERD` | 스폰서 기간 | 2 |  |  | 가입개월수 |
| 17 | `SPNSR_PENLT_AMT` | 스폰서 위약금 | 15 |  |  |  |
| 18 | `SPNSR_PYMN_MTHD` | 스폰서 (해지시) 수납방법 | 1 |  |  | R:즉납, B:후청구 *코드정의서 참조(수납방법코드) |

#### 명의변경처리결과

| seq | 항목(영문) | 항목(한글) | 크기 | 구분 | 암호화 | 비고 |
|---|---|---|---|---|---|---|
| 1 | `motSize` | 전문길이 | 4 | M |  | 전문의 전체 자릿수 (한글은 2byte로 처리) |
| 2 | `motType` | 전문종류 | 4 | M |  | MC0N: 명의변경사전체크 MP0N: 명의변경저장 |
| 3 | `motSbst` | 전문내용 |  | M |  | key=value 구조, 구분자: \|  ex) OSST_ORD_NO=20160527123456\|PRGR_STAT_CD=MC2\| CUST_NO=63302296\|SVC_CNTR_NO=641161719\|TLPH_NO=01023541238\|RSLT_CD=0000\|RSLT_MSG=결과메시지  * 전문예시 참조 |
| 1 | `OSST_ORD_NO` | OSST주문번호 | 14 | M |  |  |
| 2 | `PRGR_STAT_CD` | 처리상태 | 3 | M |  |  |
| 3 | `RCV_CUST_NO` | 양수인 고객번호 | 9 | M |  |  |
| 4 | `RCV_BILL_ACNT_NO` | 양수인 청구계정버번호 | 12 | M |  |  |
| 5 | `SVC_CNTR_NO` | 서비스계약번호 | 9 | M |  |  |
| 6 | `NEW_SVC_CNTR_NO` | 명변후 서비스 계약번호 | 9 | C |  | 명변처리 결과 성공시만 전송 |
| 7 | `TLPH_NO` | 서비스번호 | 11 | M |  |  |
| 8 | `RSLT_CD` | 결과코드 | 4 | M |  | 0000 : 성공, 그외 오류 밑에 표 참조 |
| 9 | `RSLT_MSG` | 결과메시지 | 100 | M |  |  |
| 10 | `CUST_TRGT_FALU_MSG` | 양도인 사전체크 불가 메시지 | 1000 | C |  | [&] 로 구분하여 처리됨. Ex)  문자,데이터,음성통화의 3개월 사용이력이 없습니다.[&]해당번호는 불법 매매 사이트에 등록된 번호로 업무처리 불가합니다. |
| 11 | `CUST_TRGT_INSUR_MSG` | 양도인 사전체크 안내 메시지 | 1000 | C |  | [&] 로 구분하여 처리됨. Ex)  OTC 요금제는 해지/명변시 당일 취소 불가능하니 유의바랍니다.[&]M2M 약정요금할인 정보를 확인 하세요[&]당월사용요금이 있습니다. |
| 12 | `RCV_CUST_TRGT_FALU_MSG` | 양수인 사전체크 불가 메시지 | 1000 | C |  | [&] 로 구분하여 처리됨. Ex)  영유아 개통불가^만4세 미만 영유아 명의의 개통이 불가합니다.[&]선호번호 사용제한. |
| 13 | `RCV_CUST_TRGT_INSUR_MSG` | 양수인 사전체크 안내 메시지 | 1000 | C |  | [&] 로 구분하여 처리됨. Ex)  가입한도 체크.[&]할부가능여부 체크 |
| 14 | `PRDC_TRGT_FALU_MSG` | 양도인 상품 사전체크 불가 메시지 | 1000 | C |  | [&] 로 구분하여 처리됨. |
| 15 | `PRDC_TRGT_INSUR_MSG` | 양도인 상품 사전체크 안내 메시지 | 1000 | C |  | [&] 로 구분하여 처리됨. |
| 14 | `RCV_PRDC_TRGT_FALU_MSG` | 양수인 상품 사전체크 불가 메시지 | 1000 | C |  | [&] 로 구분하여 처리됨. |
| 15 | `RCV_PRDC_TRGT_INSUR_MSG` | 양수인 상품 사전체크 안내 메시지 | 1000 | C |  | [&] 로 구분하여 처리됨. |
| 16 | `USIM_USE_PSBL_YN` | USIM 사용 가능 여부 | 1 | C |  | USIM 사용 가능 여부 |
| 17 | `USIM_SALE_UCOST` | USIM 판매가 | 9 | C |  | USIM 판매 가격 |
| 18 | `USIM_SUCC_YN` | USIM 승계 여부 | 1 | M |  | USIM 승계 : Y, USIM 비승계 : N |
| 19 | `SPNSR_SALE_ENGT_NM` | 스폰서 판매약정명 | 100 | O |  | 스폰서 판매약정명 |
| 20 | `SPNSR_ENGT_APLY_ST_DT` | 스폰서 약정 시작일 | 8 | O |  | 스폰서 약정 시작일 |
| 21 | `SPNSR_ENGT_EXPIR_PAM_DT` | 스폰서 약정 만료일 | 8 | O |  | 스폰서 약정 만료일 |
| 22 | `SPNSR_RMND_ENGT_DAY_NUM` | 스폰서 잔여 약정 일수 | 9 | O |  | 스폰서 잔여 약정 일수 |
| 23 | `SPNSR_DC_SUPRT_AMT` | 스폰서 할인지원금 | 9 | O |  | 스폰서 할인지원금 |
| 24 | `SDS_SALE_ENGT_TYPE_NM` | 심플할인 약정유형명 | 9 | O |  | 심플할인 약정유형명 |
| 25 | `SDS_ENGT_APLY_ST_DT` | 심플할인 약정 시작일 | 8 | O |  | 심플할인 약정 시작일 |
| 26 | `SDS_ENGT_EXPIR_PAM_DT` | 심플할인 약정 만료일 | 8 | O |  | 심플할인 약정 만료일 |
| 27 | `SDS_ENGT_PERD_MONS_NUM` | 심플할인 약정 개월수 | 9 | O |  | 심플할인 약정 개월수 |
| 28 | `SDS_CHAGE_DC_APLY_YN` | 요금할인 적용 여부 | 1 | O |  | 요금할인 적용 여부 |
| 29 | `M2M_ENGT_YEAR_NUM` | M2M 약정기간 | 9 | O |  | M2M 약정기간 |
| 30 | `M2M_DC_APLY_ST_DT` | M2M 약정 시작 일시 | 8 | O |  | M2M 약정 시작 일시 |
| 31 | `M2M_ENGT_USE_DAY_NUM` | M2M 약정 사용 일수 | 9 | O |  | M2M 약정 사용 일수 |
| 32 | `RQSSHT_EML_CHNG_YN` | 양도인 청구서 수령 방법 변경 여부 | 1 | O |  | 양도인 청구서 수령 방법 변경 여부 |

---

## 8. 부록

### 8.1 실명인증 실패 응답코드

| 항목1 | 항목2 | 항목3 | 항목4 | 항목5 | 항목6 |
|---|---|---|---|---|---|
|  | /* 실명인증 실패 시 응답코드 구분*/ |  |  |  |  |
|  | 기존에 모두 "2202"로 관리되던 구분코드 7001~7004까지 그룹화하여 구분 |  |  |  |  |
|  | 처리코드 | 처리메세지 |  | 처리코드 | 처리메세지 |
|  | 7001 | 실명인증실패.서비스이름 오류 |  | 7003 | 실명인증실패.주민번호 오류 |
|  | 7001 | 실명인증실패.서비스신청번호 오류 |  | 7003 | 실명인증실패.성명오류 |
|  | 7001 | 실명인증실패.조회일자/시간 오류 |  | 7003 | 실명인증실패.사망자 |
|  | 7001 | 실명인증실패.이용기관코드 오류 |  | 7003 | 실명인증실패.분실 주민등록증 |
|  | 7001 | 실명인증실패.조회구분 오류 |  | 7003 | 실명인증실패.발급일자 불일치 |
|  | 7001 | 실명인증실패.개인구분 오류 |  | 7003 | 실명인증실패.없는 주민등록번호 |
|  | 7001 | 실명인증실패.서비스구분 오류 |  | 7003 | 실명인증실패.사용할수 없는 주민등록증 |
|  | 7001 | 실명인증실패.가입채널 오류 |  | 7003 | 실명인증실패.분실신고접수 주민등록증 |
|  | 7001 | 실명인증실패.가입구분 오류 |  | 7003 | 실명인증실패.분실 |
|  | 7001 | 실명인증실패.조회시점 오류 |  | 7003 | 실명인증실패.주민번호 매칭 실패 |
|  | 7001 | 실명인증실패.동의구분(본인/대리인) 오류 |  | 7003 | 실명인증실패.성명 매칭 실패 |
|  | 7001 | 실명인증실패.동의구분(법정대리인) 오류 |  | 7003 | 실명인증실패.발행일자 매칭 실패 |
|  | 7001 | 실명인증실패.접속IP 오류 |  | 7003 | 실명인증실패.사망 취소된 면허 |
|  | 7001 | 실명인증실패.대리점ID 오류 |  | 7003 | 실명인증실패.없는 면허번호 |
|  | 7001 | 실명인증실패.조회자ID 오류 |  | 7003 | 실명인증실패.재발급된 면허 |
|  | 7001 | 실명인증실패.대리점명 오류 |  | 7003 | 실명인증실패.취소된 면허 |
|  | 7001 | 실명인증실패.판매점코드 오류 |  | 7003 | 실명인증실패.잘못된 면허 번호 |
|  | 7001 | 실명인증실패.법무부 동시접속 제한 오류 |  | 7003 | 실명인증실패.데이터오류 |
|  | 7001 | 실명인증실패.보유기관(행자부)시스템 오류 |  | 7003 | 실명인증실패.개인사업자 |
|  | 7001 | 실명인증실패.보유기관(경찰청)시스템 오류 |  | 7003 | 실명인증실패.폐휴업 |
|  | 7001 | 실명인증실패.보유기관(법무부)시스템 오류 |  | 7003 | 실명인증실패.미등록 |
|  | 7001 | 실명인증실패.보유기관(법원행정처)시스템 오류 |  | 7003 | 실명인증실패.상호명 불일치 |
|  | 7001 | 실명인증실패.보유기관(국세청)시스템 오류 |  | 7003 | 실명인증실패.주민번호미확인 |
|  | 7001 | 실명인증실패.보유기관(보건복지부)시스템 오류 |  | 7003 | 실명인증실패.성명미확인 |
|  | 7001 | 실명인증실패.보유기관(보훈처)시스템 오류 |  | 7003 | 실명인증실패.장애인자격미확인 |
|  | 7001 | 실명인증실패.보유기관(행공센)시스템 오류 |  | 7003 | 실명인증실패.최종발급일자미확인 |
|  | 7001 | 실명인증실패.보유기관(행공센)시스템 오류 |  | 7003 | 실명인증실패.일치 |
|  | 7001 | 실명인증실패.정의되지않은 결과메시지 회신 |  | 7003 | 실명인증실패.체류기간 도과 |
|  | 7001 | 실명인증실패.법무부 컨넥션 오류 |  | 7003 | 실명인증실패.자료없음 |
|  | 7001 | 실명인증실패.암복호화 오류 |  | 7003 | 실명인증실패.비유효등록번호 |
|  | 7001 | 실명인증실패.PORT 오류 |  | 7003 | 실명인증실패.불일치 |
|  | 7001 | 실명인증실패.KAIT 연계시스템 오류 |  | 7003 | 실명인증실패.불일치(복수자료) |
|  | 7001 | 실명인증실패.KAIT 통신시스템 오류 |  | 7003 | 실명인증실패.장기체류외국인 |
|  | 7001 | 실명인증실패.기타오류 |  | 7003 | 실명인증실패.발급일자 불일치,등록(거소)증 없음 |
|  | 7002 | 실명인증실패.업무시간 외 요청 |  | 7003 | 실명인증실패.분실,발급취소된 등록증 |
|  | 7003 | 실명인증실패.주민번호(외국인등록번호 등) 오류 |  | 7003 | 실명인증실패.당일입국자 |
|  | 7003 | 실명인증실패.성명 오류 |  | 7003 | 실명인증실패.신분변동 |
|  | 7003 | 실명인증실패.발급일자 오류 |  | 7003 | 실명인증실패.등록자격변경 |
|  | 7003 | 실명인증실패.운전면허번호 오류 |  | 7003 | 실명인증실패.국민이외국 국적최득 |
|  | 7003 | 실명인증실패.영문국적코드 오류 |  | 7003 | 실명인증실패.등록(면제)자격변경 |
|  | 7003 | 실명인증실패.생년월일 오류 |  | 7003 | 실명인증실패.완전출국 |
|  | 7003 | 실명인증실패.법정대리인 주민번호 오류 |  | 7003 | 실명인증실패.사망 |
|  | 7003 | 실명인증실패.법정대리인 성명 오류 |  | 7003 | 실명인증실패.한국국적 취득/회복 |
|  | 7003 | 실명인증실패.법인등록번호 오류 |  | 7003 | 실명인증실패.복수국적 |
|  | 7003 | 실명인증실패.상호명 오류 |  | 7003 | 실명인증실패.거소신고자격변경 |
|  | 7003 | 실명인증실패.보훈번호 오류 |  | 7003 | 실명인증실패.영주귀국 |
|  | 7003 | 실명인증실패.법정대리인 관계확인 미성년자 가입연령 미만 오류 |  | 7003 | 실명인증실패.8000(오류내용없음) |
|  | 7003 | 실명인증실패.법정대리인 관계확인 미성년자 연령 초과 오류 |  | 7003 | 실명인증실패.자격변경 |
|  | 7003 | 실명인증실패.법정대리인 관계확인 외국인정보입력 오류 |  | 7003 | 실명인증실패.등록자격변경 |
|  |  |  |  | 7003 | 실명인증실패.국민이외국 국적취득 |
|  |  |  |  | 7003 | 실명인증실패.등록(면제)자격변경 |
|  |  |  |  | 7003 | 실명인증실패.완전출국 |
|  |  |  |  | 7003 | 실명인증실패.사망 |
|  |  |  |  | 7003 | 실명인증실패.한국국적 취득/회복 |
|  |  |  |  | 7003 | 실명인증실패.복수국적 |
|  |  |  |  | 7003 | 실명인증실패.거소신고자격변경 |
|  |  |  |  | 7003 | 실명인증실패.영주귀국 |
|  |  |  |  | 7003 | 실명인증실패.자녀주민등록번호오류 |
|  |  |  |  | 7003 | 실명인증실패.자녀성명불일치 |
|  |  |  |  | 7003 | 실명인증실패.사망자(자녀) |
|  |  |  |  | 7003 | 실명인증실패.부(모)주민등록번호오류 |
|  |  |  |  | 7003 | 실명인증실패.부(모)성명불일치 |
|  |  |  |  | 7003 | 실명인증실패.사망자(부모) |
|  |  |  |  | 7003 | 실명인증실패.부모-자녀관계미성립 |
|  |  |  |  | 7004 | 실명인증실패.발급일자 5회오류 |

### 8.2 사진인증 오류코드

| 항목1 | 항목2 | 항목3 | 항목4 |
|---|---|---|---|
|  | 에러코드 | 세부사항 | 예시 |
|  |  | 정상 | 정상시 공백 |
|  | 31 | 서비스이름 오류 | 정의되지않은 정보 입력 또는 공백시 |
|  | 32 | 서비스신청번호 오류 | 공백시 |
|  | 33 | 조회일자/시간 오류 | 자릿수가 맞지않거나 숫자가 아닌 정보 입력시 |
|  | 34 | 이용기관코드 오류 | 협회에서 부여되지않은 코드입력 또는 공백시 |
|  | 35 | 조회구분 오류 | " |
|  | 36 | 개인구분 오류 | " |
|  | 37 | 서비스구분 오류 | " |
|  | 38 | 가입채널 오류 | " |
|  | 39 | 가입구분 오류 | " |
|  | 40 | 조회시점 오류 | " |
|  | 41 | 동의구분(본인/대리인) 오류 | " |
|  | 43 | 접속IP 오류 | 형식에 맞지않은 정보 입력시 |
|  | 44 | 대리점ID 오류 | 공백시 |
|  | 45 | 조회자ID 오류 | 공백시 |
|  | 46 | 대리점명 오류 | 공백시 |
|  | 47 | 판매점코드 오류 | 미정의 |
|  | 49 | 업무시간 외 요청 |  |
|  | 51 | 주민번호(외국인등록번호 등) 오류 암호화KEY값 오류 | 자릿수가 맞지않거나 숫자가 아닌 정보 입력시 (주민등록번호, 외국인등록번호 13자리 사업자등록번호 10자리 여권번호는 자리제한없음(규격없음)) |
|  | 52 | 성명 오류 | 공백인 경우 |
|  | 53 | 발급일자 오류 | 자릿수가 맞지않거나 숫자가 아닌 정보 입력시 |
|  | 54 | 운전면허번호 오류 | 운전면허번호 조회일때 형식에 맞지않은 정보 입력시 |
|  | 55 | 영문국적코드 오류 | 외국인여권조회일때 형식에 맞지않은 정보 입력시 |
|  | 56 | 생년월일 오류 | 외국인여권조회일때 형식에 맞지않은 정보 입력시 |
|  | 61 | 보훈번호 오류 | 국가유공자증 조회일때 형식에 맞지않은 정보입력시 |
|  | 69 | 법무부 동시접속 제한 | 각 통신사별로 법무부 동시접속에 제한을 둔다. |
|  | 81 | 보유기관(행안부)시스템 오류 | NAMEC (주민등록번호, 성명 일치여부 확인) 서비스장애발생 REGID(주민등록증) 서비스장애발생  [추가협의전까진 행안부자료이용되는 서비스] COURT(법정대리인 관계확인) 서비스장애발생 HANDI(장애인등록증) 서비스장애발생 MERIT(국가유공자 10종) 서비스장애발생 |
|  | 82 | 보유기관(경찰청)시스템 오류 | DRIVE(운전면허증) 서비스장애발생 |
|  | 83 | 보유기관(법무부)시스템 오류 | FORGN(외국인등록증/거소신고증) 서비스장애발생 PPORT(외국인여권) 서비스장애발생 |
|  | 86 | 보유기관(보건복지부)시스템 오류 | [추후 연계시]-HANDI(장애인등록증) 서비스장애발생 |
|  | 87 | 보유기관(보훈처)시스템 오류 | [추후 연계시]-MERIT(국가유공자 10종) 서비스장애발생 |
|  | 88 | 연동기관(행공센)시스템 오류 | 행공센시스템오류등을 통한 전체 서비스장애발생 |
|  | 89 | 연동기관(행공센) 연동오류 | 행공센시스템과의 연동오류등을 통한 전체 서비스장애발생 |
|  | 90 | (법무부) 정의되지않은 결과메시지 회신 |  |
|  | 95 | 암복호화 오류 | SEED 복호화 실패 |
|  | 96 | PORT 오류 | 타통신사포트사용으로 오류발생 |
|  | 97 | KAIT 연계시스템 오류 | 전체 서비스장애발생 |
|  | 98 | KAIT 통신시스템 오류 | 전체 서비스장애발생 |
|  | 99 | 기타오류 | 전체 서비스장애발생 |

### 8.3 사진인증 결과 상세코드

| 항목1 | 항목2 | 항목3 | 항목4 | 항목5 |
|---|---|---|---|---|
| 주민등록증 | 운전면허증 | 외국인등록증/거소신고증(예정) (7) |  | 외국인 여권(예정) (7) |
| 주민번호_발급일자포함 REGID  (2) | 운전면허번호 DRIVE (3) | 외국인등록증 FORGN |  | 외국인여권(7) PPORT |
|  |  | 통신사 전달코드내용 | 대리점 표시화면내용 |  |
| ResidentInfoService | NgtDefDrvLicenseService | ForeignerRegisterService |  | NgtDefPassportService |
| 통신사 | 통신사 | 통신사 |  | 통신사 |
| 0001 : 정상 | 0001 : 정상 | 0001 : 정상 | 정상 | 0001 : 정상 |
| 2003 : 분실 주민등록증 | 3003 : 분실 | 7002 : 체류기간 도과 | 자료없음 | 7002: 체류기간 도과 |
| 2004 : 습득 주민등록증 | 3011 : 사진매칭실패 | 7003 : 자료없음 | 자료없음 | 7003 : 없음(여권번호,생년월일,국적) |
| 2005 : 회수 주민등록증 | 3019 : 사진특징점 없음 | 7004 : 비유효등록번호 | 자료없음 | 7005 : 불일치(성명) |
| 2011 : 사진매칭실패 | 3031 : 주민번호 매칭 실패 | 7005 : 불일치 | 불일치 | 7006 : 복수자료 |
| 2019 : 사진특징점 없음 | 3032 : 성명 매칭 실패 | 7006 : 불일치(복수자료) | 불일치 | 7007 : 장기체류외국인(정상) |
| 2031 : 주민번호 매칭 실패 | 3033 : 발행일자 매칭 실패 | 7008 : 발급일자 불일치,           등록(거소)증 없음 | 불일치 | 7011 : 당일입국자(정상) |
| 2032 : 성명 매칭 실패 | 3035 : 사망 취소된 면허 | 7009 : 분실, 발급취소된 등록증 | 자료없음 |  |
| 2033 : 발급일자 매칭 실패 | 3039 : 없는 면허번호 | 7301 : 신분변동 | 자료없음 |  |
| 2035 : 사망 또는 말소자 | 3040 : 재발급된 면허 | 7306 : 완전출국 | 자료없음 |  |
|  | 3041 : 취소된 면허 | 7307 : 사망 | 자료없음 |  |
|  | 3042 : 잘못된 면허 번호 | 7312 : 영주귀국 | 자료없음 |  |
|  | 3088 : 데이터오류 | 일부메시지의 경우 대리점에는 다른메시지로 화면출력 |  |  |
|  | (면허번호->주민번호->성명->발급일자) 순으로 확인 |  |  |  |

### 8.4 MIS 결과코드 (안면인증)

| 항목1 | 항목2 | 항목3 | 항목4 | 항목5 |
|---|---|---|---|---|
|  |  | msg | 설명(rusrMsg) | 비고 |
|  | 기본 오류 코드 |  |  |  |
|  | 0000 | 성공 |  |  |
|  | 1001 | 필수 파라미터 누락 | 필수 파라미터 누락으로 검증에 실패하였습니다. 신원확인을 처음부터 다시 진행해 주세요. |  |
|  | 1002 | 파라미터 유효성 오류 | 파라미터 유효성 검증에 실패하였습니다. 신원확인을 처음부터 다시 진행해 주세요. |  |
|  | 1003 | 신원인증 시간초과 | 신원확인 진행 시간이 초과되었어요. 신원확인을 처음부터 다시 진행해 주세요. |  |
|  | 1004 | 유효하지 않은 요청 | 유효하지 않은 요청입니다. |  |
|  | 1007 | 요청 서명 검증 실패 | 검증에 실패하였습니다. 신원확인을 처음부터 다시 진행해 주세요. |  |
|  | 1008 | 요청 timestamp 값 오류 ( 잘못된 시간 설정, 오래된 요청 등) | 시간설정이 잘못되었거나 인증 시간이 초과되었습니다. 신원확인을 처음부터 다시 진행해 주세요. |  |
|  | 1009 | 트랜잭션 아이디 값 오류 | 요청 정보가 일치하지 않습니다. 신원확인을 처음부터 다시 진행해 주세요. |  |
|  | 1010 | clientId 불일치 | 요청 정보가 일치하지 않습니다. 신원확인을 처음부터 다시 진행해 주세요. |  |
|  | 1011 | encSerial 불일치 | 요청 정보가 일치하지 않습니다. 신원확인을 처음부터 다시 진행해 주세요. |  |
|  | 공통오류코드 |  |  |  |
|  | 1101 | 암호화 오류 | 암호화 도중 오류가 발생하였습니다. |  |
|  | 1102 | 복호화 오류 | 복호화 도중 오류가 발생하였습니다. |  |
|  | 1103 | 시스템 임계치 초과 | 서비스 사용량이 많습니다.  잠시 후 다시 시도해 주시기 바랍니다. |  |
|  | 1104 | 사용자 요청에 의한 신원확인 종료 | 사용자의 요청으로 신원확인이 종료되었습니다. |  |
|  | 1105 | 신원확인 종료 | 해당 신원 확인은 이미 종료되어 더 이상 진행할 수 없습니다 |  |
|  | 신원확인 시작요청 오류 코드 |  |  |  |
|  | 2100 | 중계 요청 사업자 관계 검증 실패 |  | 요청 사업자와 중계 사업자의 관계 검증 실패 |
|  | 2101 | 중계 토큰 누락 |  | relayToken이 전달되지 않음 |
|  | 2102 | 중계 토큰 디코딩 오류 |  | relayToken 복호화 중 오류 발생 |
|  | 2103 | 중계 클라이언트 아이디 누락 |  | 중계 요청에 clientId가 없음 |
|  | 2104 | 중계 암호화 키 일련번호 누락 |  | 중계 요청에 암호화 serial 번호 누락 |
|  | 2105 | 중계 트랜잭션 아이디 누락 |  | relayTxId가 전달되지 않음 |
|  | 2106 | 중계 JWT 검증 여부 누락 |  | relay 요청의 JWT 검증 여부 값 누락 |
|  | 2107 | 중계 사업자 코드 누락 |  | relay 요청의 businessCode가 없음 |
|  | 2108 | 중계 신분증 진위확인 여부 누락 |  | relay 요청에 idVerifyYn이 없음 |
|  | 2109 | 중계 신분증 진위확인 트랜잭션 ID 누락 |  |  |
|  | 2110 | 중계 신분증 스캐너 ID 누락 |  |  |
|  | 2111 | 중계 사업자 암호화 키 정보 없음 |  | 등록된 중계 사업자의 암호화 키 데이터 없음 |
|  | 2112 | 중계 암호화 키가 사용 불가 |  | 암호화 키가 ‘사용 가능’ 상태가 아님 |
|  | 2113 | 중계 사업자 암호화 키 만료 |  | 암호화 키 유효기간이 만료됨 |
|  | 2114 | 중계 사업자 정보 없음 |  | 요청한 중계 사업자 정보가 존재하지 않음 |
|  | 2115 | 중계 미승인 사업자 |  | 승인되지 않은 중계 사업자 |
|  | 2116 | 중계 사업자 사용 불가 |  | 중계 사업자가 비활성화 상태 |
|  | 2117 | 중계 토큰 서명 검증 실패 |  | relayToken 서명(Signature) 검증 실패 |
|  | 2118 | 중계 사업자 조회 불가 |  | 중계 사업자 정보를 조회하지 못함 |
|  | 2119 | 중계 토큰 CallSystem 최대 1000자 길이 초과 |  | 중계 토큰 callSystem 항목 길이 제한 |
|  | 2120 | 중계 토큰 스캐너 ID TYPE 누락 |  | 중계 토큰 스캐너 ID TYPE 누락 |
|  | 2121 | 중계 토큰 스캐너 ID 정보 제공 여부 누락 |  | 중계 토큰 스캐너 ID 정보 제공 여부 누락 |
|  | 2151 | 요청 토큰 누락 |  | requestToken이 전달되지 않음 |
|  | 2152 | 요청 토큰 디코딩 오류 |  | requestToken 복호화 중 오류 발생 |
|  | 2153 | 요청 클라이언트 아이디 누락 |  | 요청 데이터에 clientId가 없음 |
|  | 2154 | 요청 암호화 키 일련번호 누락 |  | 요청 데이터에 암호화 serial 번호 누락 |
|  | 2155 | 요청 사업자 정보 없음 |  | 등록된 요청 사업자 정보 없음 |
|  | 2156 | 요청 사업자 암호화 키 정보 없음 |  | 요청 사업자의 암호화 키 데이터 없음 |
|  | 2157 | 요청 암호화 키가 사용 불가 |  | 요청 암호화 키 상태가 유효하지 않음 |
|  | 2158 | 요청 사업자 암호화 키 만료 |  | 암호화 키 유효기간이 만료됨 |
|  | 2159 | 요청 미승인 사업자 |  | 승인되지 않은 요청 사업자 |
|  | 2160 | 요청 사업자 사용 불가 |  | 요청 사업자가 비활성화 상태 |
|  | 2161 | 요청 사업자 조회 불가 |  | 사업자 조회 실패 |
|  | 2162 | 요청 토큰 서명 검증 실패 |  | requestToken 서명(Signature) 검증 실패 |
|  | 2163 | 요청 사업자 코드 불일치 |  | 중계 토큰의 요청 사업자 코드와 요청 토큰의 정보가 불일치 |
|  | 2167 | 요청 토큰 독립전산 여부 유효하지 않음 |  | 독립전산 여부에 따라 N/Y 값 검증 실패 |
|  | 2170 | 중복된 트랜잭션 ID |  |  |
|  | 2171 | 트랜잭션 양식 포맷 오류 |  |  |
|  | 2180 | 스캐너 신분증 정보 조회 실패 |  | 스캐너 신분증 정보 조회 실패 (현: KT만 ) |
|  | 2199 | MIS 이용 제한 |  | MIS 특별한 이유로 서비스 차단 설정된 경우 |
|  | 신원확인 완료 정보 전달 |  |  |  |
|  | 2911 | OCR 사본 |  |  |
|  | 2912 | OCR 신분증 종류 불일치 |  |  |
|  | 2913 | OCR 사용자가 직접 종료 (USER_CANCEL) |  |  |
|  | 2914 | OCR 웹/앱 환경에 의한 종료 (PROCESS_CANCEL) |  |  |
|  | 2915 | OCR정보 형식 오류 |  |  |
|  | 2916 | OCR재시도 횟수 초과 |  |  |
|  | 2920 | 영업 전산(KAIT) 연동 실패 |  |  |
|  | 2921 | KAIT 장애 및 중단 |  |  |
|  | 2922 | KAIT 동의 거부로 인한 조회불가 |  |  |
|  | 2923 | KAIT 오류 |  |  |
|  | 2924 | KAIT 진위 확인 불가 |  |  |
|  | 2931 | 안면 인증 부정 행위 |  |  |
|  | 2932 | 안면 인증 신분증 사진과 불일치 |  |  |
|  | 2933 | 안면 인증 인식 불가 |  |  |
|  | 2934 | 안면 인증 사용자가 직접 종료 (USER_CANCEL) |  |  |
|  | 2935 | 안면 인증 웹/앱 환경에 의한 종료 (PROCESS_CANCEL) |  |  |
|  | 2936 | 안면 인증 재시도 횟수 초과 |  | 재시도 횟수 - 3회 |
|  | 2940 | PASS QR 생성 오류 |  |  |
|  | 2950 | PASS 모바일 신분증 관련 오류 |  |  |
|  | 2960 | 행안부 모바일 신분증 관련 오류 |  |  |
|  | 2998 | 신원 확인 인증 타임 아웃 |  |  |
|  | 2999 | 신원 확인 시스템 오류 |  |  |
|  | 스캐너 정보 조회 요청 (통신사 제공) |  |  |  |
|  | 2210 | 해당 TXID의 스캐너 신분증 정보 없음 |  |  |
|  | 신분증 사진진위확인 요청(with KAIT) (통신사 제공) |  |  |  |
|  | 2424 | 영업 전산(KAIT) 연동 실패 | KAIT 연동 실패로 진위확인이 불가합니다. |  |
|  | 2425 | KAIT 장애 및 중단 | KAIT 장애로 진위확인이 불가합니다. |  |
|  | 2426 | KAIT 동의 거부로 인한 조회불가 | KAIT 동의 거부로 조회에 실패하였습니다. |  |
|  | 2427 | KAIT 오류 | KAIT 오류로 진위확인이 불가합니다. |  |
|  | 2428 | KAIT 진위 확인 불가 | KAIT 진위확인이 불가합니다. |  |
|  | 시스템 오류, 기타 오류 코드 |  |  |  |
|  | 9404 | 존재하지 않는 페이지 |  |  |
|  | 9999 | 기타 시스템 오류 |  | 시스템 처리 중 예기치 않은 문제 발생 |

### 8.5 OSST 개통서식지 파일 연동규격

| NO | 항목명 | 컬럼명 | 길이 | 필수여부 | 비고1 | 비고2 | 비고3 | 비고4 |
|---|---|---|---|---|---|---|---|---|

### 8.6 유심무상교체 신청접수 파일 연동규격

| NO | 항목명 | 컬럼명 | 길이 | 필수여부 | 비고 |
|---|---|---|---|---|---|

### 8.7 안면인증 완료 통지 PUSH 연동규격

| NO | 항목명 | 컬럼명 | 길이 | 필수여부 | 비고 |
|---|---|---|---|---|---|
| 기능구분명 |  | 안면인증 완료 통지 PUSH |  |  |  |
| 연동방식 |  | REST |  |  |  |
| PATH |  | 사업자에서 KT에 해당 서비스의 endpoint ulr을 제공 하며 HTTP 메소드는 POST로 지정 |  |  |  |
| FROM TO |  | KT -> MVNO사업자 |  |  |  |
| HEADER 정보 |  | Content-Type="application/json;charset=utf-8" |  |  |  |
| Request |  |  |  |  |  |
| 번호 | ID | 타입 | 길이 | 필수 | 암호화 |
| CustFathResltReqDTO |  | 완료통지 정보 |  |  |  |
| 1 | fathTransacId | String | 30 | Y |  |
| 2 | slsCmpcCd | String | 3 | Y |  |
| 3 | retvCdVal | String | 1 | Y |  |
| 4 | custNm | String | 100 | Y | Y |
| 5 | custIdfyNo | String | 40 | Y | Y |
| 6 | issDateVal | String | 8 | Y |  |
| 7 | driveLicnsNo | String | 20 | Y | Y |
| 8 | idcardPhotoImg | String | … | Y | Y |
| 9 | idcardCopiesImg | String | … | Y | Y |
| 10 | mblIdcardQrImg | String | 100 | Y | Y |
| 11 | idcardConfWay | String | 40 | Y |  |
| 12 | distRstrtnYn | String | 1 | Y |  |
| 13 | fathProgrStepCd | String | 2 | Y |  |
| 14 | fathCmpltNtfyDt | String | 14 | Y |  |
| 15 | fathUrlRqtDt | String | 14 | Y |  |
| 16 | fathResltCd | String | 4 | Y |  |
| 17 | fathResltSbst | String | 200 | Y |  |
| 18 | fathRqtrId | String |  |  |  |
| 18 | bthday | String |  |  |  |
| PhotoAthnResltReqDTO |  | 완료통지 사진진위 정보(대면개통) |  |  |  |
| 1 | photoAthnTxnSeq | String | 30 |  |  |
| 2 | photoAthnDt | String | 14 |  |  |
| 3 | photoAthnDecideCd | String | 4 |  |  |
| 4 | photoAthnResltCd | String | 1 |  |  |
| 5 | photoAthnResltDtlCd | String | 200 |  |  |
| 6 | userId | String | 20 |  |  |
| Response |  |  |  |  |  |
| 번호 | ID | 타입 | 길이 | 필수 | 암호화 |
| CustFathResltResDTO |  | 완료통지 응답 |  |  |  |
| 1 | resltCd | String | 4 | Y |  |
| 2 | resltSbst | String | 200 | Y |  |
| 1. 안면인증 상태에 대한 콜백처리가 불필요한 경우, 구현할 필요가 없습니다. 2. 콜백처리시, 1번의 시도후 성공, 실패와 상관없이 재처리 하지않습니다. |  |  |  |  |  |
| Request 예시 |  |  |  |  |  |
| {     "custFathResltReqDTO": {         "fathTransacId": "MIS0204202512081110439381951",         "retvCdVal": "I",         "custNm": "차은우",         "custIdfyNo": "0001013V01143",         "issDate": "20251001",         "driveLicnsNo":"",         "idcardFaceImg": "",         "idcardImgCopy": "",         "photoAthnQr": "",         "fathWayCd": "PASS",         "distRstrtnYn": "N",         "fathDate": "20251001153023",         "bthday":"960925"     } } |  |  |  |  |  |
| Response 예시 |  |  |  |  |  |
| {     "custFathResltResDTO": {         "fathTransacId": "MIS0204202512081110439381951",         "resltCd": "0000",         "resltSbst": ""         } } |  |  |  |  |  |
| 안면인증 이미지 파일 |  |  |  |  |  |
| 파일수집경로 |  | /GW_REPO/ftp/각사업자디렉토리/FATH |  |  |  |
| 파일 수집 주기 |  | 매시간 |  |  |  |
| 사업자별 파일 명 예시 |  |  |  |  |  |
| 사업자코드 |  | 파일명 |  |  |  |
| 사업자 |  | 각사업자코드_안면인증트랜잭션아이디_FACE_생성일(YYYYMMDD).TIF |  |  |  |
|  |  | 각사업자코드_안면인증트랜잭션아이디_FACE_생성일(YYYYMMDD).TIF.FIN |  |  |  |
|  |  | 각사업자코드_안면인증트랜잭션아이디_IDCARD_생성일(YYYYMMDD).TIF |  |  |  |
|  |  | 각사업자코드_안면인증트랜잭션아이디_IDCARD_생성일(YYYYMMDD).TIF.FIN |  |  |  |
| 채널(ex.대고객채널/모요) |  | 채널코드S_각사업자코드_안면인증트랜잭션아이디_FACE_생성일(YYYYMMDD).TIF |  |  |  |
|  |  | 채널코드S_각사업자코드_안면인증트랜잭션아이디_FACE_생성일(YYYYMMDD).TIF.FIN |  |  |  |
|  |  | 채널코드S_각사업자코드_안면인증트랜잭션아이디_IDCARD_생성일(YYYYMMDD).TIF |  |  |  |
|  |  | 채널코드S_각사업자코드_안면인증트랜잭션아이디_IDCARD_생성일(YYYYMMDD).TIF.FIN |  |  |  |
