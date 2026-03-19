# msf-api 패키지 구조 (구체)

연동(공통)과 업무 단을 구분한 구조. 루트 패키지: `com.ktmmobile.msf`.

---

## 0. 디렉터리 개요

```
msf-api/
├── src/main/java/com/ktmmobile/msf/
│   ├── MsfApplication.java
│   ├── common/mplatform/           # M플랫폼 연동
│   │   ├── MplatFormSvc.java, MplatFormServerAdapter.java
│   │   └── vo/                     # X01, X20, Y04 응답 VO
│   ├── formComm/                   # 연동·공통
│   │   ├── controller/, dto/, service/, mapper/
│   ├── formSvcChg/                # 서비스변경
│   │   ├── controller/, dto/, service/, mapper/
│   ├── formOwnChg/                # 명의변경
│   │   ├── controller/, dto/, service/, mapper/
│   └── formSvcCncl/               # 서비스해지
│       ├── controller/, dto/, service/
├── src/main/resources/
│   ├── application.properties
│   ├── db/README.md
│   └── mapper/
│       ├── ContractInfoMapper.xml
│       ├── formSvcChg/SvcChgBaseMapper.xml
│       └── OwnChgMapper.xml
```

---

## 1. 공통 영역 (formComm)

**역할**: 가입자정보조회·동시처리체크 등 **여러 업무에서 공통 사용**. Base path: `/api/v1`.

### Controller

| 클래스 | 메서드 | API 경로 | 요청 DTO | 응답 |
|--------|--------|----------|----------|------|
| **SvcChgRestController** | POST | `/join-info` | JoinInfoReqDto | JoinInfoResVO |
| | POST | `/ident/join-info` | JoinInfoReqDto | JoinInfoResVO |
| | POST | `/cancel/join-info` | JoinInfoReqDto | JoinInfoResVO |
| | POST | `/addition/join-info` | JoinInfoReqDto | JoinInfoResVO |
| | POST | `/service-change/concurrent-check` | ConcurrentCheckReqDto | ConcurrentCheckResVO |

### Service

| 인터페이스 | 구현체 | 역할 |
|------------|--------|------|
| JoinInfoSvc | JoinInfoSvcImpl | M전산(ContractInfoMapper) → Y04(휴대폰인증) → X01(가입정보) → JoinInfoResVO |

### DTO

| 파일 | 용도 |
|------|------|
| JoinInfoReqDto | 가입자정보 조회 요청 (name, ctn 등) |
| JoinInfoResVO | 가입자정보 조회 응답 |
| SvcChgInfoDto | 고객 컨텍스트 (name, ncn, ctn, custId) — 업무 API 공통 요청 바디 |
| ConcurrentCheckReqDto | 동시 처리 불가 체크 요청 (selectedCodes) |
| ConcurrentCheckResVO | 동시 처리 불가 체크 응답 (valid) |
| ContractInfoDto | M전산 계약정보 조회 결과 |

### Mapper

| 인터페이스 | XML | 용도 |
|------------|-----|------|
| ContractInfoMapper | resources/mapper/ContractInfoMapper.xml | M전산 계약정보 (msp_juo_sub_info) |

---

## 2. 업무 영역

### 2.1 formSvcChg (서비스변경)

#### Controllers

| 클래스 | Base path | API (메서드·경로) | 요청 | 응답 |
|--------|------------|-------------------|------|------|
| **SvcChgRegController** | `/api/v1` | POST `/addition/current` | SvcChgInfoDto(optional) | AdditionCurrentResVO |
| | | POST `/addition/reg` | AdditionRegReqDto | Map |
| | | POST `/addition/cancel` | AdditionCancelReqDto | Map |
| **SvcChgBaseController** | `/api/v1/service-change/base` | GET `/targets` | — | SvcChgBaseTargetResVO |
| | | POST `/check` | SvcChgBaseCheckReqDto | SvcChgBaseCheckResVO |
| **FarPriceController** | `/api/v1/service-change/far-price` | POST `/list` | SvcChgInfoDto | FarPriceListResVO |
| | | POST `/reservation` | SvcChgInfoDto | FarPriceReservationResVO |
| | | POST `/change` | FarPriceChangeReqDto | FarPriceChangeResVO |
| | | POST `/reservation/cancel` | SvcChgInfoDto | FarPriceChangeResVO |

#### Services

| 인터페이스 | 구현체 | 역할 |
|------------|--------|------|
| SvcChgRegSvc | SvcChgRegSvcImpl | 부가서비스 현재/신청/해지 (X20, X21, X38) |
| SvcChgBaseSvc | SvcChgBaseSvcImpl | 서비스변경 대상 조회, 서비스 체크 유효성 |
| FarPriceSvc | FarPriceSvcImpl | 요금상품 목록/예약/변경/예약취소 (X19, X88, X89, X90) |

#### DTO (요청·응답 규칙: ReqDto / ResVO)

- **ReqDto**: SvcChgBaseCheckReqDto, FarPriceChangeReqDto, AdditionRegReqDto, AdditionCancelReqDto (후二者는 SvcChgInfoDto 상속)
- **ResVO**: SvcChgBaseTargetResVO, SvcChgBaseCheckResVO, FarPriceListResVO, FarPriceReservationResVO, FarPriceChangeResVO, AdditionCurrentResVO
- **기타 DTO**: SvcChgBaseCategoryDto, SvcChgBaseCategoryWithOptionsDto, SvcChgBaseOptionDto, FarPriceItemDto 등

#### Mapper

| 인터페이스 | XML | 용도 |
|------------|-----|------|
| SvcChgBaseMapper | mapper/formSvcChg/SvcChgBaseMapper.xml | 서비스변경 코드/옵션 조회 |

---

### 2.2 formOwnChg (명의변경)

#### Controller

| 클래스 | Base path | API (메서드·경로) | 요청 | 응답 |
|--------|------------|-------------------|------|------|
| **OwnChgController** | `/api/v1` | POST `/ident/eligible` | SvcChgInfoDto | Map(eligible) |
| | | POST `/ident/addition/current` | SvcChgInfoDto | AdditionCurrentResVO |
| | | POST `/ident/apply` | OwnChgApplyDto | OwnChgApplyVO |
| | | POST `/ident/grantor-req-chk` | OwnChgGrantorReqChkReqDto | OwnChgGrantorReqChkVO |
| | | POST `/ident/check-tel-no` | OwnChgCheckTelNoReqDto | OwnChgCheckTelNoVO |

#### Services

| 인터페이스 | 구현체 | 역할 |
|------------|--------|------|
| OwnChgInfoSvc | OwnChgInfoSvcImpl | 명의변경 가능 여부, 위임인 요건체크, 전화번호 확인 |
| OwnChgAddSvc | OwnChgAddSvcImpl | 부가 현재 조회 (formSvcChg 재사용) |
| OwnChgApplySvc | OwnChgApplySvcImpl | 명의변경 신청 처리 |

#### DTO

- OwnChgApplyDto, OwnChgApplyVO, OwnChgGrantorReqChkReqDto, OwnChgGrantorReqChkVO, OwnChgCheckTelNoReqDto, OwnChgCheckTelNoVO, OwnChgTrnsInsertDto, OwnChgTrnsfeInsertDto 등

#### Mapper

| 인터페이스 | XML | 용도 |
|------------|-----|------|
| OwnChgMapper | resources/mapper/OwnChgMapper.xml | 명의변경 DB 처리 |

---

### 2.3 formSvcCncl (서비스해지)

#### Controller

| 클래스 | Base path | API (메서드·경로) | 요청 | 응답 |
|--------|------------|-------------------|------|------|
| **CancelController** | `/api/v1` | POST `/cancel/eligible` | SvcChgInfoDto | Map(eligible) |
| | | POST `/cancel/remain-charge` | SvcChgInfoDto | Map(remainCharge, penalty, installmentRemain) |
| | | POST `/cancel/apply` | CancelApplyReqDto | CancelApplyVO |

#### Services

| 인터페이스 | 구현체 | 역할 |
|------------|--------|------|
| CancelApplySvc | CancelApplySvcImpl | 서비스해지 신청 처리 |

#### DTO

- CancelApplyReqDto, CancelApplyVO

---

## 3. 공통 인프라 (common.mplatform)

| 클래스 | 역할 |
|--------|------|
| **MplatFormSvc** | X01(perMyktfInfo 가입정보), X20(가입중 부가서비스), Y04(contractValdChk 휴대폰 인증) |
| **MplatFormServerAdapter** | 실제 M플랫폼 HTTP 호출 (LOCAL 제외 시) |
| **vo** | MpPerMyktfInfoVO, MpAddSvcInfoDto, SvcChgValdChkVO, CommonXmlVO 등 |

---

## 4. 연동 흐름 요약

```
[가입자정보·동시체크]
  POST /api/v1/join-info (또는 /ident/join-info, /cancel/join-info, /addition/join-info)
  → SvcChgRestController → JoinInfoSvc.joinInfo()
  → M전산(ContractInfoMapper) → Y04 → X01 → JoinInfoResVO

[업무 API]
  요청 바디 고객 컨텍스트: SvcChgInfoDto (name, ncn, ctn, custId)
  → 각 도메인 Controller → Service → (M전산/DB, M플랫폼)
```

---

## 5. 의존성 방향

- **formComm**: `common.mplatform` 만 참조. formSvcChg / formOwnChg / formSvcCncl 참조 없음.
- **formSvcChg, formOwnChg, formSvcCncl**: 필요 시 `formComm.dto`, `formComm.service` 참조. 업무 패키지 간 참조 없음 (formOwnChg → formSvcChg.dto AdditionCurrentResVO 등 일부 DTO만 참조).
