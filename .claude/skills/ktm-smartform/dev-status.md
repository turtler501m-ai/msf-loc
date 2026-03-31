# 개발 완료 / 미완성 현황 (2026-03-25 기준)

## 구현 완료 — 백엔드

| REQ | 기능 | 핵심 클래스 | API 경로 |
|-----|------|------------|---------|
| REQ_010201 | 서비스변경 메인·고객·동시처리 | SvcChgBaseController, SvcChgRestController | `/api/v1/change/join-info`, `/api/v1/service-change/concurrent-check` |
| REQ_010202 | 무선데이터차단 (X20/X21/X38) | SvcChgRegController / SvcChgRegSvcImpl | `/api/v1/addition/current`, `/api/v1/addition/apply`, `/api/v1/addition/cancel` |
| REQ_010203 | 정보료 상한 (X20 기반) | SvcChgRegSvcImpl | - |
| REQ_010204 | 부가서비스 신청/변경 (X20/X21/X38) | SvcChgRegController / SvcChgRegSvcImpl | - |
| REQ_010205 | 요금제 변경 (X19/X84/X88/X89/X90) | FarPriceController / FarPriceSvcImpl | `/api/v1/service-change/far-price/*` |
| REQ_010206 | 번호변경 (NU1/NU2/X32) | SvcChgNumberController / SvcChgNumberSvcImpl | `/api/v1/service-change/num-change/*` |
| REQ_010207 | 분실복구/일시정지해제 (X26/X28/X30/X33/X35) | SvcChgPauseController / SvcChgPauseSvcImpl | `/api/v1/service-change/pause/*` |
| REQ_010208 | 단말보험 가입 (X21 + DB저장) | SvcChgInsrController / SvcChgInsrSvcImpl | `/api/v1/service-change/insr/*` |
| REQ_010210 | USIM 변경 (X85/UC0) | SvcChgUsimController / SvcChgUsimSvcImpl | `/api/v1/service-change/usim/*` |
| REQ_010211 | 데이터쉐어링 (X69/X70/X71) | SvcChgDataSharingController / SvcChgDataSharingSvcImpl | `/api/v1/service-change/data-sharing/*` |
| REQ_010212 | 아무나 SOLO 결합 (X87/Y44) | SvcChgCombineController / SvcChgCombineSvcImpl | `/api/v1/service-change/combine/*` |
| REQ_010213 | 서비스변경 통합 신청·DB저장 | SvcChgApplyController / SvcChgApplySvcImpl / SvcChgApplyMapper | `POST /api/v1/service-change/apply` |
| REQ_010301 | 명의변경 신청 (MC0 연동 완료) | OwnChgController | `/api/v1/ident/eligible`, `/api/v1/ident/apply` |
| REQ_010302 | 명의변경 DB저장 + MP0 | OwnChgApplySvcImpl | 3테이블 저장 + MP0 연동 완료 |
| REQ_010401 | 서비스해지 신청 (X18/eligible/apply) | SvcCnclController / SvcCnclSvcImpl / SvcCnclMapper | `/api/v1/cancel/eligible`, `/api/v1/cancel/remain-charge`, `/api/v1/cancel/apply` |

---

## 구현 완료 — 프론트엔드

| 대상 | 파일 | 비고 |
|------|------|------|
| 서비스변경 Step1~3 | FormSvcChgStep1~3.vue | 고객정보·상품선택·동의서명 3단계 |
| 명의변경 Step1~3 | FormOwnChgStep1~3.vue | 양도인·양수인·USIM·동의서명 3단계 |
| 서비스해지 Step1~3 | FormSvcCnclStep1~3.vue | 고객정보·잔여요금·동의서명 3단계 + UI설계서 GAP개선(약관동의·법인·대리인성별·개통일자·Step3 등록confirm) |
| API 클라이언트 | serviceChange.js, ident.js, cancel.js, msf.js | 전 도메인 완료 |
| Pinia 스토어 | service_change_form.js, ident_form.js, cancel_form.js, msf_step.js, msf_menu.js, msf_comp_loading.js | 전 도메인 완료 |
| 팝업 컴포넌트 | McpNumberSearchPop.vue, McpAdditionEditPop.vue, McpTmBlockPop.vue 등 8개 | 번호검색·부가서비스편집·로밍 팝업 |

---

## 미구현 / 잔여 항목

| REQ | 기능 | 비고 |
|-----|------|------|
| REQ_010903/906 | 신청서 조회·미리보기·이력 | 도메인별 View/Report Controller 미생성 |
| - | eFormSign 전자서명 연동 | ChangeAgree.vue, IdentAgree.vue, CancelAgree.vue |
| - | K-NOTE 이미징 전송 | - |
| - | 카카오/SMS 알림 발송 (IF_0030/IF_0031) | - |
| - | 우편번호 API (McpPostcodePop.vue) | Daum API 등 |
| - | 신규가입 (FormSvcChg→신규) | UI설계서 11번 (v1.0_20260322) |

---

## 주요 DB 테이블 (저장 대상)

| 도메인 | 테이블 | 비고 |
|--------|--------|------|
| 서비스변경 | MSF_REQUEST_SVC_CHG, MSF_REQUEST_SVC_CHG_DTL | PROC_CD='RQ' |
| 명의변경 | MSF_CUST_REQUEST_MST (명의변경 타입), MSF_NFL_CHG_TRNS, MSF_NFL_CHG_TRNSFE | |
| 서비스해지 | MSF_REQUEST_CANCEL | |
| 단말보험 | MSF_CUST_REQUEST_MST, MSF_CUST_REQUEST_INSR | |
