# 개발 완료 / 미완성 현황 (2026-04-13 기준)

> ⚠️ 이전 파일럿(2026-03-25) 기준 항목은 하단 [파일럿 이력]에 보존.
> 현재 `msf-be-form-api` 실제 소스 기준으로 재정리.

---

## 구현 완료 — 백엔드

| 구분 | 기능 | 핵심 클래스 | API 경로 |
|------|------|------------|---------|
| 부가서비스 | 조회·신청·해지 (X97/Y25/X38) | `MsfRegSvcController` | `POST /api/v1/addition/{my-list,available-list,cancel,reg}` |
| 서비스해지 | 잔여요금 조회 (X18) | `MsfCancelConsultController.getRemainCharge` | `POST /api/v1/cancel/remain-charge` |
| KTM 고객인증 | KTM 고객 인증 (MSP DB) | `FormCommController` | `POST /ktmmember/auth` |
| 공통코드 | MSP 코드 목록 | `MspCdController` | `POST /mspcode/list` |
| 공통코드 | NMCP 코드 목록 | `NmcpCdController` | `POST /mcpcode/list` |
| M플랫폼 공통 | 전 API 코드 함수 (29개) | `MsfMplatFormService` | — |

---

## 구현 완료 — 프론트엔드

| 도메인 | 구현 파일 | 상태 |
|--------|---------|------|
| 서비스해지 Step1~3 | `TerminationCustomer.vue`, `TerminationProduct.vue`, `TerminationAgreement.vue` | ✅ |
| 해지 스토어 | `msf_termination.js` (`/api/v1/cancel/remain-charge` 연동) | ✅ |
| 서비스변경 화면 | `ServiceChangeCustomer.vue`, `ServiceChangeProduct.vue`, `ServiceChangeAgreement.vue` | ✅ |
| 명의변경 화면 | `OwnerChangeCustomer.vue`, `OwnerChangeProduct.vue`, `OwnerChangeAgreement.vue` | ✅ |
| 신규가입변경 화면 | `NewChangeCustomer.vue`, `NewChangeProduct.vue`, `NewChangeAgreement.vue` | ✅ |
| API 클라이언트 | `msf.api.js` (단일 클라이언트 `api.post()`) | ✅ |
| Pinia 스토어 | `msf_step.js`, `msf_menu.js`, `msf_termination.js` 등 | ✅ |

---

## ASIS 복사 상태 — TOBE REST 전환 필요

> 아래 컨트롤러는 ASIS 소스 복사 상태 (`@Controller` + `.do` URL). `@RestController` + `/api/v1/*` 전환 필요.

| 우선순위 | 컨트롤러 | 목표 TOBE API | M플랫폼 코드 |
|--------|---------|-------------|-----------|
| 1 | `MyNameChgController` | `POST /api/v1/owner-change/{eligible,grantor-check,check-telno,apply}` | X01/X83/MC0/MP0 |
| 1 | `MsfFarPricePlanController` | `POST /api/v1/service-change/rate/{list,pre-check,change,reservation,reservation/reg,reservation/cancel}` | X19/X84/X88/X89/X90 |
| 1 | `MsfPauseController` | `POST /api/v1/service-change/{pause,lost}/{info,apply,release}` | X25/X26/X28/X30/X33/X35 |
| 2 | `MsfUsimSelfChgController` | `POST /api/v1/service-change/usim/{check,change}` | X85/UC0 |
| 2 | `MsfCombineController` | `POST /api/v1/service-change/combine/{rate-info,check,reg}` | X87/Y44 |
| 2 | `MsfMyShareDataController` | `POST /api/v1/service-change/data-sharing/{info,apply}` | X69/X70/X71 |
| 2 | `MsfCancelConsultController` | `POST /api/v1/cancel/{eligible,apply}` + `@RestController` 전환 | — |
| 3 | `MsfMyOllehController` | `POST /api/v1/service-change/number/{list,change}` (현재 전체 주석) | NU1/NU2/X32 |
| 3 | `MsfCustRequestController` | `POST /api/v1/service-change/insurance/{info,apply}` | — |

---

## 미구현 항목

| 항목 | 비고 |
|------|------|
| SCAN 서버 / 포시에스 서식지 전송 | NC/CC/IS reqType — 포시에스 전환 방향 확정 필요 |
| eFormSign 전자서명 연동 | 동의 화면 서명 처리 |
| K-NOTE 이미징 전송 | 신청서 이미지 전송 |
| SMS/카카오 알림 (IF_0030/031) | 신청 완료 알림 |
| 인증 로그 (`MSF_CRT_VLD_DTL`) | 전 도메인 공통 미구현 |
| 마스킹 해제 로그 (`MSF_MASK_RELES_RQT_HST`) | 전 도메인 공통 미구현 |
| 개통 대리점 조회 (N7004) | `POST /api/v1/comm/channel-info` |
| BAN 유효성 체크 (N7001) | `POST /api/v1/owner-change/ban-check` |
| 신청서 조회·이력 | 관리자/대리점 목록 화면 |

---

## 주요 DB 테이블

| 도메인 | 테이블 | 전환 상태 |
|--------|--------|---------|
| 부가서비스 | M플랫폼 직접 처리 (DB 저장 없음) | — |
| 요금제변경 | M플랫폼 직접 처리 (DB 저장 없음) | — |
| 명의변경 | `MSF_CUST_REQUEST_MST`, `MSF_REQUEST_NAME_CHG` | 🔄 ASIS `NMCP_*` → `MSF_*` 전환 확인 필요 |
| 서비스해지 | `MSF_REQUEST_CANCEL` | 🔄 ASIS 복사 상태 |
| 안심보험 | `MSF_CUST_REQUEST_MST`, `MSF_CUST_REQUEST_INSR` | 🔄 ASIS 복사 상태 |
| 결합 | `MSF_REQUEST_COMBINE` | ⚠️ DB 저장 여부 확인 필요 |
| USIM 셀프변경 | `MSF_SELF_USIM_CHG` | ⚠️ DB 저장 여부 확인 필요 |

---

## [이력] 파일럿 완료 항목 (2026-03-25, msf/msf-api 기준)

> 파일럿 코드베이스(`msf/msf-api`)에서 완료된 항목. 현재 `msf-be-form-api`에 미반영 — TOBE 재적용 필요.

| REQ | 기능 | 파일럿 클래스 | 비고 |
|-----|------|-------------|------|
| REQ_010201 | 서비스변경 메인·고객·동시처리 | `SvcChgBaseController`, `SvcChgRestController` | 재적용 필요 |
| REQ_010202 | 무선데이터차단 (X20/X21/X38) | `SvcChgRegController` | 재적용 필요 |
| REQ_010204 | 부가서비스 신청/변경 | `SvcChgRegController` | ✅ `MsfRegSvcController`로 재적용 완료 |
| REQ_010205 | 요금제 변경 (X19/X84/X88~X90) | `FarPriceController` | 재적용 필요 |
| REQ_010206 | 번호변경 (NU1/NU2/X32) | `SvcChgNumberController` | 재적용 필요 |
| REQ_010207 | 분실복구/일시정지해제 | `SvcChgPauseController` | 재적용 필요 |
| REQ_010210 | USIM 변경 (X85/UC0) | `SvcChgUsimController` | 재적용 필요 |
| REQ_010211 | 데이터쉐어링 (X69/X70/X71) | `SvcChgDataSharingController` | 재적용 필요 |
| REQ_010212 | 아무나 SOLO 결합 (X87/Y44) | `SvcChgCombineController` | 재적용 필요 |
| REQ_010213 | 서비스변경 통합 신청·DB저장 | `SvcChgApplyController` | 재적용 필요 |
| REQ_010301 | 명의변경 신청 (MC0/MP0) | `OwnChgController` | 재적용 필요 |
| REQ_010302 | 명의변경 DB저장 + MP0 | `OwnChgApplySvcImpl` | 재적용 필요 |
| REQ_010401 | 서비스해지 신청 (X18) | `SvcCnclController` | ✅ `getRemainCharge` 부분 재적용 완료 |