---
name: smart_dev_change
description: MSF 스마트서식지 TOBE 개발 규칙 — 패키지 구조, 임포트 규칙, 도메인별 개발 패턴, 세션 중 확인된 요구사항 누적 관리.
trigger: msf-api / msf-web 코드 작성·수정 시 자동 참조. "tobe 개발", "패키지 구조", "import 오류" 언급 시.
---

# MSF TOBE 개발 스킬 가이드

> 최초 작성: 2026-04-01 세션 기준 (패키지 구조 확정 + import 일괄 수정)

---

## 1. 패키지 구조 (확정)

### 1.1 실제 패키지 ↔ 폴더 대응 (최종 확정 — 2026-04-01)

| 실제 파일 경로 | 실제 패키지 |
|--------------|-----------|
| `com/ktmmobile/msf/MsfApplication.java` | `com.ktmmobile.msf` |
| `com/ktmmobile/msf/form/common/**` | `com.ktmmobile.msf.form.common` |
| `com/ktmmobile/msf/form/newchange/**` | `com.ktmmobile.msf.form.newchange` |
| `com/ktmmobile/msf/form/servicechange/**` | `com.ktmmobile.msf.form.servicechange` |
| `com/ktmmobile/msf/form/ownerchange/**` | `com.ktmmobile.msf.form.ownerchange` |
| `com/ktmmobile/msf/form/termination/**` | `com.ktmmobile.msf.form.termination` |
| `com/ktmmobile/msf/common/mplatform/**` | `com.ktmmobile.msf.common.mplatform` |
| `com/ktmmobile/msf/common/util/**` | `com.ktmmobile.msf.common.util` |
| `com/ktmmobile/msf/system/cert/**` | `com.ktmmobile.msf.system.cert` |
| `com/ktmmobile/msf/system/faceauth/**` | `com.ktmmobile.msf.system.faceauth` |

### 1.2 구버전 패키지명 (사용 금지 — 이미 일괄 수정됨)

아래 패키지명은 과거 레거시/중간 구조. 새 파일 작성 또는 import 시 절대 사용하지 않는다.

| 금지 (구버전) | 대체 (현행) |
|-------------|-----------|
| `com.ktmmobile.form.*` (중간 단계) | `com.ktmmobile.msf.form.*` |
| `com.ktmmobile.common.*` (중간 단계) | `com.ktmmobile.msf.common.*` |
| `com.ktmmobile.mcp.content.controller` | `com.ktmmobile.msf.form.servicechange.controller` |
| `com.ktmmobile.mcp.content.dto` | `com.ktmmobile.msf.form.servicechange.dto` |
| `com.ktmmobile.mcp.content.service` | `com.ktmmobile.msf.form.servicechange.service` |
| `com.ktmmobile.mcp.common.constants.*` | `com.ktmmobile.msf.common.constants.*` |
| `com.ktmmobile.mcp.cmmn.constants.*` | `com.ktmmobile.msf.common.constants.*` |

> `SmtFormController.java`의 `com.ktmmobile.mcp.*` 임포트(appform/cert/common 등)는 ASIS MCP 레거시 참조 — 별도 처리 대상.

---

## 2. 도메인별 레이어 구성

각 도메인은 `controller / dto / mapper / service(인터페이스+Impl)` 4계층으로 구성.

### 2.1 formComm (공통)

```
com.ktmmobile.msf.form.common
  controller/  FormCommController, FormCommRestController, SmartFormController,
               SvcChgRestController  [SmtFormController — 미완성, MCP 레거시]
  dto/         SvcChgInfoDto (공통 요청 바디), AgencyShopResVO, ConcurrentCheck*,
               ContractInfoDto, FormSend*, McpFarPriceDto, SmartForm*, SvcChgInfo*
  mapper/      ContractInfoMapper, FormMypageMapper, FormSendMapper,
               SmartFormMapper, SvcAppFormMapper
  service/     FormCommSvc, FormCommRestSvc, FormMypageSvc, SmartFormSvc,
               SmtFormSvc, SvcChgInfoSvc
               [SmtFormController — 미완성, MCP 레거시 임포트 잔존]
```

### 2.2 svcChg (서비스변경)

```
com.ktmmobile.msf.form.servicechange
  controller/  Apply, Base, Combine, FarPrice, Insr, Number, Pause, Reg,
               ShareData, SmtShareData, Usim (11개)
  dto/         24종 (SvcChgApplyReqDto, SvcChgBaseCheckReqDto, UsimChangeReqDto 등)
  mapper/      Apply, Base, Combine, Insr, Reg (5개)
  dto/         SmtShareDataReqDto, SmtShareDataResDto  [MyShareData* → SmtShareData* 리네임 진행 중]
  service/     SmtShareDataSvc, SmtInfoService, SmtPageService, Apply, Base, Combine, FarPrice,
               Insr, Number, Pause, Reg, ShareData, Usim (13쌍)
```

### 2.3 ownChg (명의변경)

```
com.ktmmobile.msf.form.ownerchange
  controller/  OwnChgController
  dto/         OwnChgApplyDto/VO, OwnChgCheckTelNoReqDto/VO,
               OwnChgCustReqInsertDto, OwnChgGrantorReqChkReqDto/VO,
               OwnChgSyncItemDto, OwnChgTrnsInsertDto, OwnChgTrnsfeInsertDto
  mapper/      OwnChgMapper
  service/     OwnChgAddSvc, OwnChgApplySvc, OwnChgInfoSvc (3쌍)
```

### 2.4 svcCncl (서비스해지)

```
com.ktmmobile.msf.form.termination
  controller/  SvcCnclController
  dto/         McpCancelRegisterReqDto/ResVO, SvcCnclApplyDto/VO,
               SvcCnclConsultReqDto, SvcCnclDetailResVO, SvcCnclProcReqDto,
               SvcCnclRemainChargeResVO, SvcCnclSyncItemDto
  mapper/      SvcCnclMapper  [SvcCnclInsertDto가 mapper 폴더에 혼재 — DTO 이동 필요]
  service/     McpCancelRegisterSvc, SvcCnclSvc (2쌍)
```

### 2.5 common (M플랫폼 어댑터)

```
com.ktmmobile.msf.common.mplatform
  MsfMplatFormService.java       — M플랫폼 연동 메서드 전체 (Y04/X01/X18~X90 등)
  MsfMplatFormServerAdapter      — HTTP 송수신
  MsfMplatFormOsstServerAdapter  — OSST 연동
  vo/                            — MpXxx* VO 34종
  dto/                           — MoscXxx* DTO
com.ktmmobile.msf.common.util
  XmlParseUtil.java
com.ktmmobile.msf.common.mspservice
  — M플랫폼 비동기 서비스 처리
```

---

## 3. MyBatis XML 위치 규칙

```
src/main/resources/mapper/
  formComm/     ContractInfoMapper.xml, FormMypageMapper.xml, FormSendMapper.xml,
                SmartFormMapper.xml, SvcAppFormMapper.xml
  formOwnChg/   OwnChgMapper.xml
  formSvcChg/   SvcChgApplyMapper.xml, SvcChgBaseMapper.xml, SvcChgCombineMapper.xml,
                SvcChgInsrMapper.xml, SvcChgRegMapper.xml
  formSvcCncl/  SvcCnclMapper.xml
```

XML namespace는 반드시 실제 Mapper 인터페이스 패키지와 일치시킨다.
예: `namespace="com.ktmmobile.form.common.mapper.ContractInfoMapper"`

---

## 4. 프론트엔드 현재 구조

```
msf-web/src/
  layouts/BaseLayout.vue          ← 신규 추가됨 (layouts 폴더 분리)
  views/                          McpHomeView, McpNotFoundView, McpServiceDetailView,
                                  MsfDetailView, MsfStepView, ServiceCompleteView
  components/
    formComm/   MsfNumberTheftBlockPop, MsfPrevPayCostPop, MsfRoamingAllDayPop, MsfTmBlockPop
    formSvcChg/ Step1~3
    formOwnChg/ Step1~3
    formSvcCncl/Step1~3
    layouts/    McpMainLayout, McpMenu, McpStepService, MsfBreadcrumb, MsfContainer,
                MsfErrorComp, MsfHeader, MsfLoadingComp, MsfSideNav(Item), MsfTitleBar
    commons/    McpAdditionEditPop, McpNoticeList, McpNumberSearchPop, McpPostcodePop,
                McpPrevPayCostPop, McpTermsPop
    ui/         alert-dialog/*, button/*, dialog/*  (reka-ui 래퍼)
  api/          msf.js, common.js, serviceChange.js, ident.js, cancel.js
  stores/       cancel_form, ident_form, msf_comp_loading, msf_menu, msf_step, service_change_form
```

---

## 5. 개발 시 필수 체크리스트

### 신규 Java 파일 작성 시

- [ ] `package` 선언 — 실제 폴더 경로 기준 (`com.ktmmobile.msf.form.*` / `com.ktmmobile.msf.common.*`)
- [ ] `import` — 구버전 `com.ktmmobile.form.*` / `com.ktmmobile.common.*` / `com.ktmmobile.mcp.*` 사용 금지
- [ ] 신규 `@Mapper` 인터페이스 → `MsfApplication.java`의 `@MapperScan`에 패키지 추가
- [ ] MyBatis XML namespace — 실제 패키지명과 일치

### API 메서드 규칙

- GET: 파라미터 없거나 path variable만
- POST: 조회·저장·변경·삭제 등 나머지 전부
- PUT / PATCH / DELETE 사용 금지

### DB 테이블명 규칙

| ASIS | TOBE |
|------|------|
| `MCP_*`, `NMCP_*` | `MSF_*` (접두사 교체) |
| `MSP_*` | `MSP_*@DL_MSP` (DB링크 그대로) |

---

## 6. 세션 중 확인된 요구사항 (지속 업데이트)

### 2026-04-01

- **패키지 구조 변경 완료**: 구버전 `com.ktmmobile.msf.*` 패키지명 전체를 실제 폴더 경로 기반 패키지로 일괄 수정.
  `formComm` → `form.common`, `formOwnChg` → `form.ownChg`, `formSvcChg` → `form.svcChg`, `formSvcCncl` → `form.svcCncl`
- **프론트 구조 변경**: `src/layouts/BaseLayout.vue` 신규 추가 (기존 `components/layouts/` 와 별도 레벨로 분리)
- **네이밍 변경 — `MyXXX` → `SmtXXX`**: 클래스명 접두사 `My` 를 `Smt` 로 변경.
  완료: `SmtShareDataController`, `SmtInfoService(Impl)`, `SmtPageService(Impl)`, `SmtFormSvc(Impl)`, `SmtFormController`
  잔여(미리네임): `MyShareDataSvc(Impl)`, `MyShareDataReqDto`, `MyShareDataResDto` — 추후 `SmtShareData*` 로 통일 필요.
  `SmtShareDataController` `com.ktmmobile.mcp.content.*` 레거시 임포트 → `com.ktmmobile.form.*` 로 수정 완료.

### 2026-04-01 (이번 세션)

- **exception 패키지 이관**: `com.ktmmobile.mcp.common.exception` → `com.ktmmobile.common.exception` (이후 msf로 재이관)
- **최종 패키지 구조 확정**:
  - 루트: `com.ktmmobile.msf` (기존 `com.ktmmobile.form`, `com.ktmmobile.common` 를 `com.ktmmobile.msf.form`, `com.ktmmobile.msf.common` 로 통합)
  - 전체 파일(196개) package 선언 + import 일괄 수정 완료
  - `com.ktmmobile.mcp.*` → `com.ktmmobile.msf.*` 전체 치환 완료
  - cert 패키지: `com.ktmmobile.mcp.cert.*` → `com.ktmmobile.msf.cert.*`
- **클래스명 파일명 일치 작업 완료**:
  - `AppformSvc` → `SmtFormSvc`, `AppformSvcImpl` → `SmtFormSvcImpl`, `AppformController` → `SmtFormController`
  - `MyinfoService(Impl)` → `SmtInfoService(Impl)` (form/svcChg), `SmtinfoService(Impl)` (SmtPage)
  - `MypageService(Impl)` → `SmtPageService(Impl)` (form/svcChg), `SmtpageService(Impl)` (SmtPage)
  - `MyPageSearchDto` → `SmtPageSearchDto`, `MyShareDataResDto` → `SmtShareDataResDto`
- **pom.xml 의존성 추가**: `org.jdom:jdom:1.1.3`, `commons-lang3`, `commons-lang:2.6`, `commons-collections4:4.4`
- **mplatform_bak 빌드 제외**: maven-compiler-plugin exclude 설정 (`com/ktmmobile/msf/common/mplatform_bak/**`)
- **빌드 잔여 오류 (허용)**: `mplatform/` ASIS 레거시 VO·Controller·IpStatisticService 29개 — TOBE 핵심 도메인은 오류 없음
