---
name: smart_dev_change
description: MSF 스마트서식지 TOBE 개발 규칙 — 패키지 구조, 임포트 규칙, 도메인별 개발 패턴, 세션 중 확인된 요구사항 누적 관리.
trigger: msf-be-form-api / msf-form-web 코드 작성·수정 시 자동 참조. "tobe 개발", "패키지 구조", "import 오류" 언급 시.
---

# MSF TOBE 개발 스킬 가이드

> 최초 작성: 2026-04-01 세션 기준 (패키지 구조 확정 + import 일괄 수정)
> 현행화: 2026-04-11 (실제 코드베이스 구조 반영)

---

## 1. 패키지 구조 (확정)

### 1.1 실제 패키지 ↔ 폴더 대응 (최종 확정 — 2026-04-11)

| 실제 파일 경로 (domains/form/src/main/java/) | 실제 패키지 |
|----------------------------------------------|-----------|
| `com/ktmmobile/msf/appboot/FormApiApplication.java` | `com.ktmmobile.msf.appboot` |
| `com/ktmmobile/msf/domains/form/common/**` | `com.ktmmobile.msf.domains.form.common` |
| `com/ktmmobile/msf/domains/form/common/mplatform/**` | `com.ktmmobile.msf.domains.form.common.mplatform` |
| `com/ktmmobile/msf/domains/form/common/mspservice/**` | `com.ktmmobile.msf.domains.form.common.mspservice` |
| `com/ktmmobile/msf/domains/form/common/util/**` | `com.ktmmobile.msf.domains.form.common.util` |
| `com/ktmmobile/msf/domains/form/form/common/**` | `com.ktmmobile.msf.domains.form.form.common` |
| `com/ktmmobile/msf/domains/form/form/newchange/**` | `com.ktmmobile.msf.domains.form.form.newchange` |
| `com/ktmmobile/msf/domains/form/form/servicechange/**` | `com.ktmmobile.msf.domains.form.form.servicechange` |
| `com/ktmmobile/msf/domains/form/form/ownerchange/**` | `com.ktmmobile.msf.domains.form.form.ownerchange` |
| `com/ktmmobile/msf/domains/form/form/termination/**` | `com.ktmmobile.msf.domains.form.form.termination` |
| `com/ktmmobile/msf/domains/form/form/appform_d/**` | `com.ktmmobile.msf.domains.form.form.appform_d` |
| `com/ktmmobile/msf/domains/form/system/cert/**` | `com.ktmmobile.msf.domains.form.system.cert` |
| `com/ktmmobile/msf/domains/form/system/faceauth/**` | `com.ktmmobile.msf.domains.form.system.faceauth` |
| `com/ktmmobile/msf/domains/form/login/**` | `com.ktmmobile.msf.domains.form.login` |
| `com/ktmmobile/msf/domains/form/main/**` | `com.ktmmobile.msf.domains.form.main` |
| `com/ktmmobile/msf/domains/form/mobileapp/**` | `com.ktmmobile.msf.domains.form.mobileapp` |

### 1.2 구버전 패키지명 (사용 금지 — 이미 일괄 수정됨)

아래 패키지명은 과거 레거시/중간 구조. 새 파일 작성 또는 import 시 절대 사용하지 않는다.

| 금지 (구버전) | 대체 (현행) |
|-------------|-----------|
| `com.ktmmobile.msf.form.*` (구버전 — domains 없음) | `com.ktmmobile.msf.domains.form.*` |
| `com.ktmmobile.msf.common.*` (구버전 — domains 없음) | `com.ktmmobile.msf.domains.form.common.*` |
| `com.ktmmobile.form.*` (중간 단계) | `com.ktmmobile.msf.domains.form.*` |
| `com.ktmmobile.common.*` (중간 단계) | `com.ktmmobile.msf.domains.form.common.*` |
| `com.ktmmobile.mcp.content.controller` | `com.ktmmobile.msf.domains.form.form.servicechange.controller` |
| `com.ktmmobile.mcp.content.dto` | `com.ktmmobile.msf.domains.form.form.servicechange.dto` |
| `com.ktmmobile.mcp.content.service` | `com.ktmmobile.msf.domains.form.form.servicechange.service` |
| `com.ktmmobile.mcp.common.constants.*` | `com.ktmmobile.msf.domains.form.common.constants.*` |
| `com.ktmmobile.mcp.cmmn.constants.*` | `com.ktmmobile.msf.domains.form.common.constants.*` |

> ASIS MCP 레거시 참조(`com.ktmmobile.mcp.*` 임포트)는 변환 대상 — ASIS 로직 처리 원칙에 따라 주석 처리 후 TOBE 패키지로 교체.

---

## 2. 도메인별 레이어 구성

각 도메인은 `controller / dao / dto / mapper / service(인터페이스+Impl)` 계층으로 구성.

MyBatis: `XxxDaoImpl.java`에서 `SqlSession` 주입 방식 사용 (`@Mapper` 인터페이스 없음).

### 2.1 common (도메인 공통)

```
com.ktmmobile.msf.domains.form.common
  controller/  FCommonController, NiceCertifyController
  dao/
  dto/         공통 DTO 다수
  mplatform/   MsfMplatFormService, MsfMplatFormServerAdapter,
               MsfMplatFormOsstServerAdapter, MsfMplatFormOsstWebServerAdapter,
               MsfMplatFormOsstWebService
               vo/   — MpXxx* VO 다수
               dto/
  mspservice/  — M플랫폼 비동기 서비스 처리
  util/        XmlParseUtil 등
  cache/
  commCode/
  constants/
  exception/
  legacy/
  mapper/      CommCodeMapper.xml, CommonMapper.xml
```

### 2.2 form/common (폼 공통)

```
com.ktmmobile.msf.domains.form.form.common
  controller/
  dao/
  dto/
  mapper/      MaskingMapper.xml, NiceLogMapper.xml, OrderMapper.xml
  service/
```

### 2.3 form/newchange (신규가입·변경)

```
com.ktmmobile.msf.domains.form.form.newchange
  controller/  AppformController, EsimController
  dao/
  dto/
  mapper/      AppFormMapper.xml
  service/
  util/
```

### 2.4 form/servicechange (서비스변경)

```
com.ktmmobile.msf.domains.form.form.servicechange
  controller/  MsfChargeController, MsfCombineController, MsfCustRequestController,
               MsfFarPricePlanController, MsfMyinfoController, MsfMyOllehController,
               MsfMypageController, MsfMyShareDataController, MsfNicePinController,
               MsfPauseController, MsfRegSvcController, MsfUsimSelfChgController
  dao/
  dto/         다수
  mapper/      CustRequestMapper.xml, MyCombinationMapper.xml, MypageMapper.xml, RegSvcMapper.xml
  service/     MsfChargeService(Impl), MsfChildInfoService(Impl), MsfCustRequestScanService(Impl),
               MsfCustRequestService(Impl), MsfFarPricePlanService(Impl), MsfMaskingSvc(Impl),
               MsfMPayViewService(Impl), MsfMyCombinationSvc(Impl), MsfMyinfoService(Impl),
               MsfMypageSvc(Impl), MsfMypageUserService(Impl), MsfMyShareDataSvc(Impl),
               MsfNicePinService(Impl), MsfOwnPhoNumService(Impl), MsfPayInfoService(Impl),
               MsfPaywayService(Impl), MsfPrvCommDataService(Impl), MsfRealTimePayService(Impl),
               MsfRegSvcService(Impl)
```

### 2.5 form/ownerchange (명의변경)

```
com.ktmmobile.msf.domains.form.form.ownerchange
  controller/  MyNameChgController
  dao/
  dto/
  mapper/      MyNameChgMapper.xml
  service/
```

### 2.6 form/termination (서비스해지)

```
com.ktmmobile.msf.domains.form.form.termination
  controller/  MsfCancelConsultController
  dao/         CancelConsultDao (인터페이스), CancelConsultDaoImpl
  dto/
  mapper/      CancelConsultMapper.xml
  service/     MsfCancelConsultSvc (인터페이스), MsfCancelConsultSvcImpl
```

### 2.7 M플랫폼 어댑터 (common/mplatform)

```
com.ktmmobile.msf.domains.form.common.mplatform
  MsfMplatFormService.java       — M플랫폼 연동 메서드 전체 (Y04/X01/X18~X90 등)
  MsfMplatFormServerAdapter      — HTTP 송수신
  MsfMplatFormOsstServerAdapter  — OSST 연동
  MsfMplatFormOsstWebServerAdapter
  MsfMplatFormOsstWebService
  vo/                            — MpXxx* VO 다수
  dto/
```

---

## 3. MyBatis XML 위치 규칙

XML mapper는 `resources/` 가 아닌 **Java 소스 폴더 내 `mapper/` 서브폴더**에 배치.

```
domains/form/src/main/java/com/ktmmobile/msf/domains/form/
  common/mapper/
    CommCodeMapper.xml
    CommonMapper.xml
  form/common/mapper/
    MaskingMapper.xml
    NiceLogMapper.xml
    OrderMapper.xml
  form/newchange/mapper/
    AppFormMapper.xml
  form/ownerchange/mapper/
    MyNameChgMapper.xml
  form/servicechange/mapper/
    CustRequestMapper.xml
    MyCombinationMapper.xml
    MypageMapper.xml
    RegSvcMapper.xml
  form/termination/mapper/
    CancelConsultMapper.xml
  system/cert/mapper/
    CertMapper.xml
  system/faceauth/mapper/
    FathMapper.xml
```

XML namespace는 반드시 실제 패키지 경로와 일치시킨다.
예: `namespace="com.ktmmobile.msf.domains.form.form.termination.mapper.CancelConsultMapper"`

---

## 4. 프론트엔드 현재 구조

```
msf-form-web/src/
  layouts/          MsfBaseLayout.vue
  views/            MsfFormView, MsfMainView, MsfLoginView, MsfDeviceAuthView,
                    MsfDeviceRegisterView, MsfExtraView, MsfNotFoundView
  components/
    form/
      common/       Msf* 공통 폼 컴포넌트 30+개
      newchange/    NewChangeCustomer, NewChangeProduct, NewChangeAgreement
      ownerchange/  OwnerChangeCustomer, OwnerChangeProduct, OwnerChangeAgreement
      servicechange/ ServiceChangeCustomer, ServiceChangeProduct, ServiceChangeAgreement
      termination/  TerminationCustomer, TerminationProduct, TerminationAgreement
    layouts/        MsfBottomNav(Item), MsfContainer, MsfErrorComp, MsfHeader,
                    MsfLoadingComp, MsfStepIndicator, MsfTitleBar
    extra/          mobileapp/, receipt/, simplerequest/, tempsave/
  libs/
    api/            msf.api.js
    ui/
      base/         MsfButton, MsfCheckbox, MsfCheckboxGroup, MsfChip, MsfFlag,
                    MsfIcon, MsfInput, MsfPopover, MsfRadio, MsfRadioGroup,
                    MsfSelect, MsfTextarea
      block/        MsfAgreementGroup, MsfAgreementItem, MsfAlertDialog, MsfBox,
                    MsfButtonGroup, MsfCollapse, MsfCustomScroll, MsfDialog,
                    MsfFormGroup, MsfGrid, MsfGridItem, MsfPagination,
                    MsfSearchBox, MsfStack, MsfTable, MsfTableList, MsfTextList,
                    MsfTitleArea
  hooks/            useAlert, useAuthButton, useGlobalScroll, useScrollLock
  stores/           msf_menu, msf_step, msf_newchange, msf_termination, msf_user, counter
```

---

## 5. 개발 시 필수 체크리스트

### 신규 Java 파일 작성 시

- [ ] `package` 선언 — 실제 폴더 경로 기준 (`com.ktmmobile.msf.domains.form.*`)
- [ ] `import` — 구버전 `com.ktmmobile.form.*` / `com.ktmmobile.common.*` / `com.ktmmobile.mcp.*` / `com.ktmmobile.msf.form.*` (domains 없는 것) 사용 금지
- [ ] 신규 `@Mapper` 인터페이스 → `FormApiApplication.java`의 `@MapperScan`에 패키지 추가
- [ ] MyBatis XML namespace — 실제 패키지명과 일치 (`com.ktmmobile.msf.domains.form.*`)

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
  `formComm` → `form.common`, `formOwnChg` → `form.ownerchange`, `formSvcChg` → `form.servicechange`, `formSvcCncl` → `form.termination`
- **최종 패키지 루트 확정**: `com.ktmmobile.msf.domains.form` (진입점은 `com.ktmmobile.msf.appboot`)
- **네이밍 변경 — 컨트롤러·서비스에 `Msf` 접두사 적용 완료**
- **클래스명 파일명 일치 작업 완료**

### 2026-04-11 (현행화)

- **실제 루트 패키지 확인**: `com.ktmmobile.msf.domains.form` (이전 문서의 `com.ktmmobile.msf.form.*` 은 구버전 — 사용 금지)
- **진입점**: `FormApiApplication.java` — `com.ktmmobile.msf.appboot` 패키지
- **MyBatis 방식 확인**: `XxxDaoImpl`에서 `SqlSession` 직접 주입 (`@Mapper` 인터페이스 없음). XML mapper는 Java 소스 폴더 내 `mapper/` 서브폴더에 위치
- **servicechange 서비스 파일 다수**: `MsfChargeService`, `MsfChildInfoService`, `MsfCustRequestScanService`, `MsfCustRequestService`, `MsfFarPricePlanService`, `MsfMaskingSvc`, `MsfMPayViewService`, `MsfMyCombinationSvc`, `MsfMyinfoService`, `MsfMypageSvc`, `MsfMypageUserService`, `MsfMyShareDataSvc`, `MsfNicePinService`, `MsfOwnPhoNumService`, `MsfPayInfoService`, `MsfPaywayService`, `MsfPrvCommDataService`, `MsfRealTimePayService`, `MsfRegSvcService`
- **termination 구조**: `MsfCancelConsultController` / `CancelConsultDao(Impl)` / `MsfCancelConsultSvc(Impl)` — 서비스해지 해지상담 기능 구현됨
- **프론트엔드 스토어**: `msf_menu`, `msf_step`, `msf_newchange`, `msf_termination`, `msf_user` (counter 포함 6개)
- **프론트엔드 UI**: 자체 라이브러리 `libs/ui/` (base/ + block/) — reka-ui 미사용
- **프론트엔드 API**: `libs/api/msf.api.js` 단일 파일 (구버전 `api/` 폴더 다수 파일 구조 아님)
- **SCSS 사용**: Tailwind CSS 제거, SCSS로 스타일 작성
