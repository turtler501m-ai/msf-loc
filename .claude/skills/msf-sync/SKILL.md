---
name: msf-sync
description: MSF-GIT(원격 git) ↔ MSF-LOC(로컬 실행 환경) 양방향 동기화 절차. GIT→LOC 반영 및 LOC→GIT 반영 작업 안내.
trigger: "동기화", "git 반영", "LOC 미반영", "GIT에 반영", "반영해야할", "화면 개발사항 확인", "msf-sync", "GIT 상태" 언급 시 참조
---

# MSF-GIT ↔ MSF-LOC 동기화 스킬

> 최초 작성: 2026-04-22 | 최종 업데이트: 2026-04-22  
> **현재 LOC 반영 기준**: `msf-form-web` `5ef0505` / `msf-be-form-api` `dd6230f` (2026-04-22 반영 완료)

---

## 0. 개발 환경 구조

```
MSF-LOC (C:\MSF-LOC\workspace\)          MSF-GIT (C:\MSF-GIT\workspace\)
  git 계정: turtler501m@gmail.com    ↔      git 계정: 82312001@kt.com (KT)
  로컬 개발·테스트 환경                       KT GitLab 원격 저장소 (develop 브랜치)
  BE port: 8180                             BE port: 8080
  FE port: 7080                             FE port: 7080
```

**작업 흐름**:
- `GIT → LOC`: MSF-GIT 최신 코드 → MSF-LOC에 반영 (KT 팀 변경사항 수신)
- `LOC → GIT`: MSF-LOC 개발·테스트 완료 → MSF-GIT으로 복사 → KT GitLab push

| 환경 | 폴더 | git 계정 | 역할 |
|------|------|---------|------|
| **MSF-LOC** | `C:\MSF-LOC\workspace\` | turtler501m@gmail.com | 로컬 개발·테스트 |
| **MSF-GIT** | `C:\MSF-GIT\workspace\` | 82312001@kt.com | KT GitLab 원격 저장소 연동 |

> ⚠️ **git 저장(commit/push)은 절대 자동으로 하지 않는다.** 사용자가 명시적으로 요청할 때만 실행.

---

## 1. 개념 정의

| 용어 | 의미 |
|------|------|
| **MSF-GIT** | KT GitLab 원격 저장소에 커밋된 최신 코드 (`develop` 브랜치 기준), git 계정 82312001@kt.com |
| **MSF-LOC** | 로컬 개발·테스트 환경 (`C:\MSF-LOC\workspace\`), git 계정 turtler501m@gmail.com |
| **동기화 갭** | GIT에는 코드가 있으나 LOC에 미반영된 항목, 또는 LOC에서 개발 완료 후 GIT에 미반영된 항목 |

**갭 유형**:
- `FE완료/BE미연결` — 화면·컴포넌트는 있으나 BE API 미구현 또는 FE에서 미호출
- `BE완료/FE미연결` — BE API는 있으나 FE에서 호출 코드가 없거나 WIP
- `양쪽 WIP` — FE·BE 모두 개발 중, 아직 연결 불가
- `GIT변경/LOC빌드필요` — 코드 변경됐으나 로컬에 재빌드·재시작 안 된 상태

---

## 2. 동기화 상태 확인 방법

```bash
# FE 최신 커밋
cd msf-form-web && git log --oneline -10

# BE 최신 커밋
cd msf-be-form-api && git log --oneline -10

# 특정 날짜 이후 신규 파일
git log --name-status --after="YYYY-MM-DD" | grep "^A"

# 브랜치별 진행 상황
git branch -a
```

---

## 3. BE 모듈 구조 (2026-04-22 현재 실제)

> ⚠️ 51.md의 BE 구조는 구버전. 실제 구조는 아래와 같이 재편됨 (2026-04-09~22).

```
msf-be-form-api/
├── app-boot/                   ← Spring Boot 진입점
├── commons/
│   ├── auditing/               ← 자동 Auditing 기능 (04-10 신규)
│   ├── client/                 ← HTTP 클라이언트 모듈 (04-19 신규)
│   ├── common/                 ← 공통 유틸·상수·설정
│   ├── file/                   ← 파일 업로드/다운로드 모듈 (04-19 신규)
│   └── mybatis/                ← MyBatis 설정·인터셉터
├── domains/
│   ├── commoncode/             ← 공통코드 조회 (04-13 신규, 계속 개선 중)
│   ├── form/                   ← 업무 도메인 (신규가입/서비스변경/해지 등)
│   ├── login/                  ← 로그인·기기인증 (04-09 신규)
│   ├── mobileapp/              ← 모바일앱 연동 (04-13 신규)
│   ├── policy/                 ← 패스워드 정책 (04-16 신규)
│   └── shared/                 ← 공유 기능: 주소검색·SMS·약관 (04-14~ 신규)
└── external/
    └── websecurity/            ← Spring Security 설정
```

### BE 도메인 컨트롤러 현황 (실제 활성)

| 모듈 | 컨트롤러 | 주요 API | 상태 |
|------|---------|---------|------|
| `domains/login` | `LoginController` | POST /login | ✅ 구현 완료 |
| `domains/mobileapp` | `AppController` | POST /app/intro, /app/regist | ✅ 구현 완료 |
| `domains/commoncode` | `CommonCodeController` | POST /commoncode/* | ✅ 구현 완료 |
| `domains/shared` | `SearchAddressController` | POST /address/search | ✅ 구현 완료 (04-21 API키 수정) |
| `domains/shared` | `CommonSmsController` | POST /sms/send, /sms/verify | ✅ 구현 완료 (04-22 수정) |
| `domains/shared` | `TermsController` | POST /terms/list | ✅ 구현 완료 |
| `domains/form/newchange` | `NewChangeController` | POST /api/form/newchange/* | ⚠️ WIP (단순저장 04-21) |
| `domains/form/common` | `ProductController` | GET /product/* | ⚠️ 개발 중 |
| `domains/form/common` | `SimController` | POST /sim/* | ⚠️ 개발 중 |
| `domains/form/servicechange` | `MsfChangePageController` | POST /mypage/* (레거시 경로) | ⚠️ 구조 정리 중 |
| `domains/form/termination` | `MsfCancelPageController` | POST /cancel/* | ⚠️ WIP |

> ⚠️ **51.md 오류 정정**: 아래 항목은 2026-04-17 `appform_d` 패키지 삭제로 더 이상 존재하지 않음.  
> ~~`FormCommController` POST /ktmmember/auth~~  
> ~~`MspCdController` POST /mspcode/list~~  
> ~~`NmcpCdController` POST /mcpcode/list~~  
> → 공통코드는 `CommonCodeController`로 통합됨.

---

## 4. 현재 동기화 갭 현황 (2026-04-22)

### 4.1 FE 완료 / BE 연결 필요 항목

| # | 화면/기능 | FE 파일 | BE 상태 | 할 일 |
|---|---------|---------|---------|------|
| 1 | 로그인 | `MsfLoginView.vue` | ✅ BE 완료 | FE에서 `/login` API 호출 코드 연결 |
| 2 | 기기 등록 | `MsfDeviceRegisterView.vue` | ✅ BE 완료 | FE에서 `/app/regist` API 연결 |
| 3 | 기기 인증 | `MsfDeviceAuthView.vue`, `MsfDeviceAuthNumber.vue` | ✅ BE 완료 (04-22) | FE SMS 인증 `/sms/send`, `/sms/verify` 연결 |
| 4 | SMS 번호인증 공통 | `MsfMobileAuthNumber.vue` | ✅ BE 완료 (04-22) | FE 컴포넌트 API URL 파라미터 최종 확인 |
| 5 | 주소 검색 | `MsfAddressSearchPop.vue` | ✅ BE 완료 (04-21) | FE URL 변경 적용 확인 (a2dde0f에서 URL 변경함) |
| 6 | 약관 조회 | `MsfAgreementGroup.vue` | ✅ BE 완료 | FE에서 `/terms/list` API 연결 |
| 7 | 공통코드 조회 | `comn.utils.js` `getCommonCode()` | ✅ BE 완료 | FE 호출 URL을 새 `CommonCodeController` 경로로 확인 |
| 8 | 신규가입·변경 Step1~3 | `NewChange*.vue` | ⚠️ BE WIP | 단순저장 WIP 완료 후 연결 |
| 9 | 단말/요금제 조회 | `MsfDevicePlanInfo.vue`, `NewChangeProduct.vue` | ⚠️ BE 개발 중 | `ProductController` 완료 후 연결 |

### 4.2 BE 완료 / FE 미연결 항목

| # | 기능 | BE 컨트롤러/API | FE 연결 상태 |
|---|------|--------------|------------|
| 1 | 앱 초기 정보 | `AppController` POST /app/intro | FE에서 미호출 (앱 시작시 필요) |
| 2 | 공통코드 (신규 URL) | `CommonCodeController` | FE `comn.utils.js` URL 확인 필요 |

### 4.3 양쪽 WIP — LOC 연결 불가

| # | 기능 | FE | BE | 예상 완료 |
|---|------|----|----|--------|
| 1 | 신규가입·변경 저장 | `NewChangeAgreement.vue` | `NewChangeController` WIP | BE 저장 로직 완료 후 |
| 2 | 청구계정ID 조회 | `MsfBillingInfo.vue` | 청구계정아이디조회 WIP | BE 완료 후 |
| 3 | 서비스변경 | `ServiceChange*.vue` | `MsfChangePageController` 구조 정리 중 | - |
| 4 | 서비스해지 | `Termination*.vue` | `MsfCancelPageController` WIP | - |
| 5 | 명의변경 | `OwnerChange*.vue` | BE 미착수 | - |

### 4.4 FE 완료 / BE 미착수

| # | 화면 | FE 컴포넌트 |
|---|------|-----------|
| 1 | 무선데이터차단 | `MsfWirelessDataBlock` |
| 2 | 정보료 상한금액 | `MsfInfoChargeLimit` |
| 3 | 요금제 변경 | `MsfChargePlanChange` |
| 4 | 번호변경 | `MsfNewJoinTelePhoneNumber` (서비스변경) |
| 5 | 분실복구/일시정지해제 | `MsfUnpauseRequest` |
| 6 | 단말보험 가입 | `MsfDeviceInsuranceJoin` |
| 7 | USIM 변경 | `MsfSimInfo` (서비스변경) |
| 8 | 데이터쉐어링 | `MsfDataSharingJoinAndCancel` |
| 9 | 아무나SOLO 결합 | `MsfCombineSolo` |
| 10 | 설정화면 | `MsfSettingView` |
| 11 | 부가기능(receipt/tempsave/simple) | `ReceiptPage`, `TempSavePage`, `SimpleRequestPage` |

---

## 5. 즉시 반영 가능 항목 (우선순위 순)

> BE 구현이 완료되어 FE 연결만 하면 LOC에서 동작 가능한 항목.

| 우선순위 | 기능 | 작업 내용 |
|---------|------|---------|
| 🔴 1순위 | 로그인 | `MsfLoginView` → BE `/login` API 연결 |
| 🔴 1순위 | SMS 인증 | `MsfMobileAuthNumber`, `MsfDeviceAuthNumber` → BE `/sms/*` API 연결 |
| 🔴 1순위 | 공통코드 | `comn.utils.js` → `CommonCodeController` URL 확인·연결 |
| 🟡 2순위 | 주소 검색 | `MsfAddressSearchPop` URL 변경 적용 확인 |
| 🟡 2순위 | 약관 조회 | `MsfAgreementGroup` → BE `/terms/list` 연결 |
| 🟡 2순위 | 기기 등록/인증 | `MsfDeviceRegisterView` → BE `/app/regist` 연결 |

---

## 6. GIT에서 새로 추가된 BE 공통 모듈 (LOC 빌드 시 영향)

> 아래 모듈이 신규 추가돼 LOC 빌드에 포함됨. 서버 재시작 시 정상 기동 확인 필요.

| 모듈 | 추가일 | 역할 |
|------|--------|------|
| `commons/client/` | 04-19 | 외부 HTTP 클라이언트 (주소검색·MCP-API 등에 사용) |
| `commons/file/` | 04-19 | 파일 업로드/다운로드 |
| `commons/auditing/` | 04-10 | CUD 쿼리 자동 Auditing |
| `domains/policy/` | 04-16 | 패스워드 VO·유효성 검사 |
| `domains/shared/` | 04-14 | 주소검색·SMS·약관 공유 기능 |
| `domains/commoncode/` | 04-13 | 공통코드 캐시·조회 |
| `domains/mobileapp/` | 04-13 | 앱 초기화·기기 등록 |
| `domains/login/` | 04-09 | 로그인·JWT 처리 |

---

## 7. FE 신규 컴포넌트 현황 (51.md 미반영)

### 7.1 UI 라이브러리 추가/변경

| 구분 | 신규 | 변경/삭제 |
|------|------|---------|
| base | `MsfSwitch`, `MsfFileButton` | `MsfInput`(아이콘), `MsfFlag`(스타일), `MsfButton`(minWidth props), `MsfCheckboxGroup`/`MsfRadioGroup`/`MsfSelect`/`MsfChip` (groupCode props) |
| block | `MsfAccordion`, `MsfTab` | `MsfAlertDialog`(br 줄바꿈), `MsfAgreementGroup`(필수여부) |
| 복합입력 | `MsfMobileInput`, `MsfAddressInput`, `MsfBizRegInput`, `MsfEmailInput`, `MsfRegNoInput`, `MsfTelInput` | ~~`MsfPhoneInput`~~ 삭제 → `MsfMobileInput` |

### 7.2 form/common 신규 컴포넌트

| 분류 | 컴포넌트 목록 |
|------|------------|
| 서비스변경(9종) | `MsfServiceChangeSelection`, `MsfWirelessDataBlock`, `MsfInfoChargeLimit`, `MsfValueAdditonalServiceReqChg`, `MsfChargePlanChange`, `MsfUnpauseRequest`, `MsfDeviceInsuranceJoin`, `MsfDataSharingJoinAndCancel`, `MsfCombineSolo` |
| 서비스해지(4종) | `MsfCancelPhoneNumber`, `MsfCancelRequest`, `MsfCancelSettlement`, `MsfCustomerJoinType` |
| 명의변경(1종) | `MsfChargePlanInfo` |
| 공통(3종) | `MsfMobileAuthNumber`(SMS), `MsfAddressSearchPop`, `MsfRealtimeChargeInfoModal` |
| 팝업(14종) | `MsfAddressSearchModal`, `MsfAppConfirmModal`, `MsfAppViewerModal`, `MsfIdCardListModal`, `MsfMnpAuthFailModal`, `MsfNewNumberSearchModal`, `MsfPasswordInputModal`, `MsfRequiredDocModal`, `MsfTermsDetailModal`, `MsfVasManageModal`, `MsfFaceAuthModal`, `MsfIdCardScanModal`, `MsfMobileIdModal`, `MsfRealtimeChargeInfoModal` |
| 삭제 | ~~`MsfSIMInfo.vue`~~ → `MsfSimInfo.vue` |

### 7.3 Pinia 스토어 추가

| 파일 | 용도 |
|------|------|
| `msf_serviceChange.js` | 서비스변경 폼 |
| `msf_ownerChange.js` | 명의변경 폼 |
| `msf_termination.js` | 서비스해지 폼 |
| `msf_alert.js` | Alert 전역 (hook→store 전환) |

### 7.4 뷰/라우터/유틸 추가

| 항목 | 내용 |
|------|------|
| `MsfSettingView.vue` | `/setting` 라우터 신규 |
| `FormCommonGuideView.vue` | `/form-common-guide` |
| `FormGuideView.vue` | `/form-guide` |
| `comn.utils.js` | 구 `useCommonCode.js` 통합 이동 |
| `string.utils.js` | `validateMobile()`, `validateTel()` 추가 |
| `comp.utils.js` | `showAlert()`, `showConfirm()` 추가 |

---

## 8. 동기화 작업 절차

1. **현재 GIT/LOC 상태 확인** — 두 repo 최신 커밋 확인
2. **이 스킬 섹션 4 대조** — 변경사항 있으면 갱신
3. **섹션 5(즉시 반영 가능) 중 우선순위 선택**
4. **작업 완료 후 상태 업데이트** — ⚠️/❌ → ✅

---

## 9. 연결 작업 패턴

### FE API 호출 추가

```javascript
// src/libs/api/msf.api.js 의 post() 사용
import { post } from '@/libs/api/msf.api'

const result = await post('/sms/send', { ctn: formData.value.ctn })
```

### 스텝 컴포넌트 테스트용 코드 제거 시점

API 연결 완료 후 아래 블록 삭제:
```html
<!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
<div class="ut-mt-50"> ... </div>
```

### LOC 서버 재시작 (신규 모듈 추가 후)

```bash
taskkill //F //IM java.exe
cd msf-be-form-api
./gradlew :app-boot:bootJar
java -jar app-boot/build/libs/app-boot-*.jar
```

---

## 10. 스킬 업데이트 규칙

- **연결 완료 시**: 섹션 4 해당 항목 ❌→⚠️→✅, 헤더 날짜 갱신
- **새 FE 커밋 확인 시**: 섹션 7 갱신
- **새 BE API 확인 시**: 섹션 3, 4 갱신
- **git pull 후 BE 모듈 추가 확인 시**: 섹션 6 갱신
- **GIT→LOC 파일 반영 완료 시**: 섹션 12 이력 추가, 헤더 "현재 LOC 반영 기준" 커밋 갱신
- **LOC→GIT 파일 반영 완료 시**: 섹션 12 이력 추가
- **스킬 파일 변경 시**: MSF-GIT과 MSF-LOC 양쪽 모두 동일하게 업데이트

---

## 11. GIT → LOC 동기화 작업 절차

### 작업 순서

```bash
# 1. LOC 마지막 반영 기준 커밋 확인 (섹션 헤더)
# 2. GIT 최신 커밋 확인
cd /c/MSF-GIT/workspace/msf-form-web && git log --oneline -5
cd /c/MSF-GIT/workspace/msf-be-form-api && git log --oneline -5

# 3. LOC 반영 기준 이후 변경 파일 목록 추출
cd /c/MSF-GIT/workspace/msf-form-web
git log --name-only --format="" --after="YYYY-MM-DD HH:MM" | grep -v "^$" | sort -u

cd /c/MSF-GIT/workspace/msf-be-form-api
git log --name-only --format="" --after="YYYY-MM-DD HH:MM" | grep -v "^$" | sort -u

# 4. ★ 신규 파일 누락 방지 — 패키지(폴더) 단위 파일 목록 비교 (변경된 모듈 대상)
#    GIT에 있고 LOC에 없는 파일 확인
diff <(find /c/MSF-GIT/workspace/msf-be-form-api/<모듈경로> -name "*.java" | sed 's|.*msf-be-form-api/||' | sort) \
     <(find /c/MSF-LOC/workspace/msf-be-form-api/<모듈경로> -name "*.java" | sed 's|.*msf-be-form-api/||' | sort)
# "<" 로 시작하는 줄 = GIT에만 있는 파일 → LOC에 복사 필요
# ">" 로 시작하는 줄 = LOC에만 있는 파일 → GIT 기준 삭제 검토

# 5. YAML 설정 파일은 diff 로 먼저 비교
diff /c/MSF-GIT/workspace/msf-be-form-api/<yaml파일> \
     /c/MSF-LOC/workspace/msf-be-form-api/<yaml파일>

# 6. 파일 복사 (GIT → LOC)
cp /c/MSF-GIT/workspace/msf-form-web/<파일> \
   /c/MSF-LOC/workspace/msf-form-web/<파일>

# 7. GIT에서 삭제된 파일은 LOC에서도 삭제
git show <커밋> --name-status | grep "^D"
rm /c/MSF-LOC/workspace/msf-be-form-api/<삭제파일>

# 8. BE 재빌드 (Java 파일 변경 시 필수)
cd /c/MSF-LOC/workspace/msf-be-form-api
./gradlew :app-boot:bootJar
```

### 주의사항

- `application-private.yaml` 은 LOC 전용 설정 (DB 접속정보 등) — **절대 덮어쓰지 말 것**
- LOC BE 포트는 **8180** (GIT은 8080) — 포트 관련 설정 파일 복사 시 확인
- `application-*-local.yaml` 파일은 LOC 전용 설정이 있을 수 있으므로 diff 먼저 확인

### ★ LOC 개발 파일 덮어쓰기 방지 — 반드시 사전 확인

GIT→LOC 복사 전, **LOC에서 이미 개발 중인 파일**은 GIT 버전으로 덮어쓰면 LOC 개발 내용이 유실된다.

**확인 방법**: 복사 대상 파일이 git에 수정 이력(modified)이 있으면 반드시 diff 확인 후 결정.

```bash
# LOC에서 수정된 파일 목록 확인
cd /c/MSF-LOC/workspace/msf-be-form-api && git status
cd /c/MSF-LOC/workspace/msf-form-web   && git status   # msf-form-web은 상위에서 확인

# 복사 대상 파일이 LOC에서 수정됐는지 확인
git diff --stat <파일경로>
# 삭제 줄(-)이 많으면 → LOC 개발 내용이 더 많다는 뜻 → 덮어쓰기 금지
# 추가 줄(+)만 있으면 → HEAD보다 working tree가 더 많다 → LOC 개발본이 안전
```

**덮어써서 유실됐을 때 복원**:
```bash
git restore <파일경로>   # HEAD(커밋된 LOC 개발본)으로 복원
```

**실사례 (2026-04-22)** — GIT→LOC 동기화 중 아래 파일들이 GIT 버전으로 덮어써져 LOC 개발 내용 유실:

| 파일 | 유실 규모 | 조치 |
|------|---------|------|
| `msf-form-web/src/stores/msf_termination.js` | ~142줄 (X18 API, resetStep, apiCompleteApplication 등) | `git restore`로 복원 |
| `msf-form-web/.../termination/TerminationCustomer.vue` | ~318줄 | `git restore`로 복원 |
| `msf-form-web/.../termination/TerminationProduct.vue` | 대규모 | `git restore`로 복원 |
| `msf-form-web/.../termination/TerminationAgreement.vue` | 일부 | `git restore`로 복원 |
| `msf-form-web/.../servicechange/ServiceChangeCustomer.vue` | ~450줄 | `git restore`로 복원 |

→ **GIT→LOC 동기화 시 `domains/form/`, `form/termination/`, `form/servicechange/` 하위 파일은 특히 주의.**

---

### ★ 빌드 오류 실사례 (2026-04-22)

GIT→LOC 동기화 후 빌드 실패 2건 발생. **변경된 파일만 복사하고 신규 파일을 누락한 것이 원인.**

| 오류 | 원인 | 조치 |
|------|------|------|
| `CommonCodeData` 타입 충돌 | LOC `application.dto`에 GIT에 없는 `CommonCodeData.java`, `CommonCodeGroups.java` 잔존 | 두 파일 삭제 |
| `ExternalServiceProperties` cannot find symbol | `commons/client/support/` 하위 신규 파일 4개 미복사 | GIT에서 복사 |

**누락됐던 파일 4개** (`commons/client` 모듈):
```
support/properties/ExternalServiceProperties.java
support/properties/InternalServiceProperties.java
support/properties/ServiceProperties.java
support/interceptor/QueryParamHttpClientInterceptor.java
```

→ **git log의 변경 파일 목록만 보면 신규 추가 파일을 놓칠 수 있다.**  
→ 모듈 단위로 `find` + `diff` 비교(4번 단계)를 반드시 수행할 것.

---

## 12. LOC → GIT 동기화 작업 절차

> MSF-LOC에서 개발·테스트 완료 후 MSF-GIT(KT GitLab)에 반영하는 절차.

### 작업 순서

```bash
# 1. LOC 변경 파일 목록 확인
cd /c/MSF-LOC/workspace/msf-be-form-api
git status
git diff --name-only HEAD

cd /c/MSF-LOC/workspace/msf-form-web
git status
git diff --name-only HEAD

# 2. 설정 파일은 반드시 diff 먼저 (포트·DB 설정 오염 방지)
diff /c/MSF-LOC/workspace/msf-be-form-api/commons/common/src/main/resources/application-common.yaml \
     /c/MSF-GIT/workspace/msf-be-form-api/commons/common/src/main/resources/application-common.yaml

# 3. 소스 파일 복사 (LOC → GIT)
cp /c/MSF-LOC/workspace/msf-be-form-api/<변경파일> \
   /c/MSF-GIT/workspace/msf-be-form-api/<변경파일>

cp /c/MSF-LOC/workspace/msf-form-web/<변경파일> \
   /c/MSF-GIT/workspace/msf-form-web/<변경파일>

# 4. GIT에서 빌드 확인
cd /c/MSF-GIT/workspace/msf-be-form-api
./gradlew build -x test

# 5. GIT에서 커밋 (사용자가 명시적으로 요청할 때만)
cd /c/MSF-GIT/workspace/msf-be-form-api
git add <파일들>
git commit -m "커밋 메시지"
```

### 복사 제외 파일 (LOC 전용 설정 — GIT에 반영 금지)

| 파일 | 이유 |
|------|------|
| `application-private.yaml` | LOC DB 접속 정보 (포트·계정 다름) |
| `application-common-local.yaml` | LOC 포트 8180 등 환경 전용 설정 |
| `application-*-local.yaml` | 로컬 환경 오버라이드 전체 |

### 설정 파일 포트 확인

| 설정 항목 | MSF-LOC | MSF-GIT |
|---------|---------|---------|
| BE 서버 포트 | **8180** | **8080** |
| FE API 타겟 | `localhost:8180` | `localhost:8080` |

### 스킬 파일 동기화

LOC→GIT 반영 시 이 SKILL.md도 함께 복사:
```bash
cp /c/MSF-LOC/workspace/.claude/skills/msf-sync/SKILL.md \
   /c/MSF-GIT/workspace/.claude/skills/msf-sync/SKILL.md
```

---

## 13. 동기화 이력

| 날짜 | 방향 | 내용 | FE 기준 커밋 | BE 기준 커밋 |
|------|------|------|------------|------------|
| 2026-04-22 | GIT→LOC | FE 28개 + BE 34개 복사, LOC 구버전 파일 1개 삭제 (`AuthSmsRequest.java`) | `5ef0505` | `dd6230f` |
| 2026-04-22 | GIT→LOC (빌드오류 수정) | 누락 파일 4개 복사(`commons/client`), 잘못된 중복 파일 2개 삭제(`commoncode/application.dto`) | `5ef0505` | `dd6230f` |
