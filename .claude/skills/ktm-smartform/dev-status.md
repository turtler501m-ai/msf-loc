# 개발 완료 / 미완성 현황 (2026.03.17 기준)

## 개발 완료 기능

| 기능 | 프론트 | 백엔드 | 비고 |
|------|--------|--------|------|
| 휴대폰 인증 (Y04+X01) | ✅ ChangeTypeCust.vue | ✅ JoinInfoSvcImpl | M전산 DB 필요 |
| 서비스 유형 선택 | ✅ ChangeTypeCust.vue | ✅ SvcChgBaseController | - |
| 부가서비스 조회/신청/해지 (X20/X21/X38) | ✅ ChangeProd.vue | ✅ SvcChgRegController | REQ_010202 완료 |
| 추가/삭제 팝업 | ✅ McpAdditionEditPop.vue | - | X20 실데이터 연동 |
| 요금제 변경 (X19/X88/X89/X90) | ✅ ChangeProd.vue | ✅ FarPriceController | - |
| 분실복구/일시정지해제 (X26/X28/X30/X35) | ✅ ChangeProd.vue | ✅ SvcChgPauseController | - |
| 데이터쉐어링 (X69/X71/X70) | ✅ ChangeProd.vue | ✅ SvcChgDataSharingController | - |
| 아무나 SOLO 결합 (X87/Y44) | ✅ ChangeProd.vue | ✅ SvcChgCombineController | - |
| 특수 부가 팝업 (불법TM/번호도용/로밍) | ✅ Mcp*Pop.vue | - | 프론트 완료 |
| 번호 검색 팝업 | ✅ McpNumberSearchPop.vue | ❌ Mock 상태 | X32 백엔드 미구현 |
| 동의 화면 구조 | ✅ ChangeAgree.vue | ❌ apply API 미구현 | - |
| 명의변경 고객/상품/동의 | ✅ Ident*.vue | △ OwnChgController 뼈대 | MC0/MP0 미연동 |
| 서비스해지 고객/동의 | ✅ Cancel*.vue | △ CancelController 뼈대 | 사전체크 미구현 |

---

## 즉시 개발 필요 항목 (우선순위 순)

### 🔴 우선순위 1 — 서비스변경 완성

**① 신청서 저장 API (apply)**
- 파일: `mform-api formSvcChg/` 신규
- `POST /api/v1/service-change/apply`
- 처리: `MSF_SVC_CHG_REQUEST` 테이블 저장 + 선택 서비스별 M플랫폼 API 순차 호출
- ASIS 참조: `AppformController.saveAppformDb()` → `AppformSvcImpl.saveAppform()`

**② ChangeAgree.vue `doSubmit()` 구현**
- 파일: `mform-web/src/components/change/ChangeAgree.vue` (현재 `console.log`만 있음)
- apply API 호출
- 성공 → `router.push('service-complete')`
- 실패 → 에러 메시지

**③ 번호변경 백엔드 (X32)**
- 파일: `mform-api formSvcChg/controller/SvcChgNumChangeController.java` (신규)
- `POST /api/v1/service-change/num-change/search`
- `POST /api/v1/service-change/num-change/apply`
- ASIS 참조: `AppformController.searchNumberAjax()`, `setNumberAjax()`
- `MplatFormSvc.numChgeList(X32)`, `numChgeChg(X32)`

### 🟡 우선순위 2 — 명의변경 실연동

**④ MC0/MP0 M플랫폼 연동**
- 파일: `mform-api formOwnChg/service/OwnChgInfoSvcImpl.java`
- `eligible()` → MC0 명의변경 사전체크 실연동
- ASIS 참조: `MyNameChgServiceImpl.myNameChgRequest()`
- `MplatFormSvc`에 MC0/MP0 메서드 추가

**⑤ 명의변경 신청서 저장 (DB)**
- 파일: `OwnChgApplySvcImpl.java` + `OwnChgMapper.xml`
- `NMCP_CUST_REQUEST_MST`, `NMCP_CUST_REQUEST_NAME_CHG` 테이블 INSERT
- 현재 뼈대만 존재

**⑥ 신용정보·납부방법 검증**
- 신규 Controller 필요:
  - IF_0033 신용정보 조회
  - IF_0016 계좌번호 유효성
  - IF_0017 신용카드 유효성

### 🟠 우선순위 3 — 서비스해지 실연동

**⑦ 잔여요금/위약금/분납금 조회**
- 파일: `CancelAmtInfoController.java` (신규)
- `POST /api/v1/cancel/remain-charge` (뼈대 있음)
- `POST /api/v1/cancel/penalty-charge` (신규)
- `POST /api/v1/cancel/installment` (신규)

**⑧ 서비스해지 사전체크 + 최종 신청**
- 파일: `CancelApplySvcImpl.java`
- `cancelEligible()` 실로직 구현
- `apply()` DB 저장 + M플랫폼 전송

### 🟢 우선순위 4 — 부가 기능

| # | 항목 | 비고 |
|---|------|------|
| ⑨ | 대리점 목록 조회 API | `GET /api/v1/service-change/base/branches`, MSF_STORE_INFO 조회 |
| ⑩ | 단말보험 (SvcChgInsrController) | - |
| ⑪ | USIM 변경 (SvcChgUsimController — X85/UC0) | - |
| ⑫ | 우편번호 API 연동 (McpPostcodePop.vue — Daum API 등) | - |
| ⑬ | eFormSign 서명 연동 (ChangeAgree.vue, IdentAgree.vue, CancelAgree.vue) | - |
| ⑭ | K-NOTE 이미징 전송 | - |
| ⑮ | 카카오/SMS 알림 발송 (IF_0030/IF_0031) | - |
