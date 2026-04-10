# 52. 서비스변동 (부가서비스) 타시스템 인터페이스 분석서

## 1. 개요
본 문서는 `msf-api` 프로젝트의 **서비스 변동(부가서비스 가입/해지/조회)** 기능 수행 시 타시스템(M플랫폼, MSP, 공통 모듈 등)과 연동하는 인터페이스 규격 및 비즈니스 목적을 분석하여 정의합니다. 
분석 기준 소스코드는 변환된 `MsfRegSvcController` 및 `MsfRegSvcServiceImpl` 입니다.

## 2. 타시스템 연동 요약

| 연동 시스템 (Target) | 방향 | 업무/통신 유형 | 주요 연동 기능 (전문/API) |
| --- | --- | --- | --- |
| **M플랫폼 (KOS)** | Outbound | EAI/Rest 송수신 | X97, X38, Y25 |
| **MSP (마스터 정보)** | Outbound | DB 연동/내부 조회 | MSP_RATE_MST@DL_MSP |
| **인증/포인트 시스템** | Outbound | 내부 서비스 간 호출 | 인증 STEP 검증, 포인트 차감 (미이관) |

## 3. 세부 연동 인터페이스 분석

### 3.1. M플랫폼 연동 (기간망 KOS 전문 송수신)

M플랫폼은 이동통신 가입자의 실시간 부가서비스(SOC) 상태를 관리하고 제어하는 핵심 기간계(KOS) 시스템으로, 다음과 같은 3개의 주요 전문을 사용합니다.

*   **X97 (가입중인 부가서비스 상세 조회)**
    *   **호출 위치**: `selectMyAddSvcList` (이용중 조회), `selectAddSvcInfoDto` (가입가능목록 기준)
    *   **요청 파라미터**: `ncn`(계약번호), `ctn`(전화번호), `custId`(고객번호)
    *   **응답 데이터**: 가입 중인 SOC 목록, 적용 이력, 기타 상세 정보 (`MpAddSvcInfoParamDto`)
    *   **업무 목적**:
        1. 고객이 현재 가입 중인 부가서비스 목록 화면 표출.
        2. 전체 가입 가능 부가서비스 목록 가공 시, 기가입 여부(`useYn`) 식별 기준 데이터로 활용.
    *   **개선/변경 사항 (TOBE 연동 표준화)**: ASIS의 경우 가입가능 목록 조회에 단순 SOC만 반환하는 `X20` 전문을 사용했으나, TOBE에서는 상세 이력이 포함된 `X97`로 통합/교체하여 연동 복잡도를 낮췄습니다.

*   **X38 (부가서비스 해지)**
    *   **호출 위치**: `moscRegSvcCanChg`
    *   **요청 파라미터**: `ncn`, `ctn`, `custId`, `soc`(부가서비스 코드), `prodHstSeq`(상품이력번호 - 선택)
    *   **업무 목적**: 가입된 부가서비스의 즉시 해지 요청.
    *   **특이 사항**: 로밍 서비스와 같이 동일한 SOC를 여러 차례 가입/해지할 수 있는 경우, 특정 이력 건을 식별하여 해지(`moscRegSvcCanChgSeq`, 상품이력번호 필수)하도록 분기 처리되어 있습니다.

*   **Y25 (부가서비스 다건 신청 및 변경)**
    *   **호출 위치**: `regSvcChg`
    *   **요청 파라미터**: `ncn`, `ctn`, `custId`, `soc`(신청 대상 코드), `ftrNewParam`(부가정보)
    *   **업무 목적**: 서버에 신규 부가서비스 가입을 요청하는 전문.
    *   **개선/변경 사항**: 기존 단건 처리 용도였던 `X21` 전문을, TOBE 표준 전문이자 다건 처리(Multi) 및 복합 처리를 지원하는 `Y25`(상품변경처리)로 교체하였습니다. '선해지 후 가입' 같은 특수한 로밍 및 변경 프로세스는 내부 플래그(`flag="Y"`) 검사 후 먼저 `X38`을 수행하고 `Y25`로 가입시킵니다.

### 3.2. MSP 연동 (마스터 정책 사전 검증)

이동통신 메타 정보 및 온라인 업무 처리 규칙을 담고 있는 시스템으로, 사전에 M플랫폼 호출 가부를 필터링하는 정책/규칙 서버 역할을 합니다.

*   **MSP_RATE_MST (요금제/부가서비스 마스터 참조)**
    *   **호출/참조 위치**: `selectMyAddSvcList`(조회 시 표출용), `moscRegSvcCanChg`(해지 시 검증용)
    *   **업무 로직 데이터**: `onlineCanYn`(온라인 해지 가능 여부), `canCmnt`(해지 불가 안내 문구)
    *   **업무 목적**: 
        1. **안내 및 전시 차원**: 고객이 사용 중인 부가서비스 중 "앱(온라인)에서 직접 해지할 수 없는 상품"인지 판별하여 버튼을 비활성화하고, 고객센터로 유도하는 문구를 함께 제공합니다.
        2. **사전 방어 (Pre-validation)**: 해지 요청이 들어올 때 M플랫폼의 `X38` 전문을 호출하기 전, 해당 SOC가 온라인 해지가 가능한지 재검증합니다. 요금제에 종속된 부가서비스 등은 원천 차단됩니다.
    *   **특이 케이스 예외 처리**: "포인트할인 부가서비스 (`REG_SVC_CD_4`)" 같이 업무 정책상 강력하게 온라인 해지를 금지하는 항목은 응답값과 무관하게 하드코딩으로 `onlineCanYn="N"` 처리하여 강제 차단하고 있습니다. MSF 내부 규칙이 MSP 마스터 규칙보다 우선하도록 설계됨.

### 3.3. 향후 연동 및 미이관 부분 (TODO)

현재 MSA 분리로 인해 아직 이관 및 연결되지 않아 시스템상 주석 처리 혹은 끊겨 있는 타시스템 연동부입니다.

*   **공통 인증 / 고객 계정 보안 검증**
    *   **기존 업무**: 과금/요금이 발생하는 부가서비스의 조회 및 변경 시, 현재 세션이 충분한 인증 수준(최소 STEP 3)을 거쳤는지 확인하고, 타가입자 번호(CTN/NCN) 도용을 막는 검증(`CertService`, `vldCertInfo`).
    *   **TOBE 방향**: 공통 인증 모듈 API 또는 API Gateway Layer에서의 인가 플로우에 인증 검증 이관 필요.
*   **통합 포인트 / 혜택 정산 연동**
    *   **기존 업무**: 포인트 할인 부가서비스 신청 시, 해당 사용자의 잔여 포인트를 조회하고 동시에 사용 포인트를 차감 정산(`pointService.editPoint`).
    *   **TOBE 방향**: 멤버십/포인트 도메인 분리 시 REST API 또는 Kafka 같은 메시지 큐를 통한 비동기 포인트 차감 보상 트랜잭션 연동 구성 필요.

---

## 5. X18 잔여요금·위약금 조회 — 서비스해지 TOBE 전환 구현 계획

> 작성일: 2026-04-10 | 해당 엔드포인트: `POST /api/v1/cancel/remain-charge`

### 5.1 현재 구현 상태 (2026-04-10 기준)

| 파일 | 상태 | 비고 |
|------|------|------|
| `termination/controller/MsfCancelConsultController.java` | 레거시 `@Controller` 활성 | JSP 반환 방식, X18 미연동 |
| `termination/service/MsfCancelConsultSvc.java` | DB 3개 메서드만 존재 | `getRemainCharge` 없음 |
| `termination/service/MsfMsfCancelConsultSvcImpl.java` | DB insert/select 구현만 | X18 연동 없음 |
| `common/mplatform/MsfMplatFormService.java` | `farRealtimePayInfo()` 이미 존재 | X18 전문 호출 가능 상태 |
| `common/mplatform/vo/MpFarRealtimePayInfoVO.java` | 응답 파싱 VO 존재 | searchDay/searchTime/sumAmt/list |

### 5.2 ASIS 처리 흐름 (참조)

```
(MCP 레거시) ChargeController.farPricePlanView()
  → MsfChargeService.farRealtimePayInfo(ncn, ctn, custId)
      → MsfMplatFormService.farRealtimePayInfo(ncn, ctn, custId)  [X18 전문]
          → MpFarRealtimePayInfoVO 파싱
              ├── searchDay    : "20260410"           (조회 기준 날짜, yyyyMMdd)
              ├── searchTime   : "2026.04.01 ~ 04.10" (조회 기간 문자열, 가공 후)
              ├── sumAmt       : "15,000"             (당월요금계)
              └── list         : [{gubun, payment}, ...]
                  ※ "원단위절사금액" 항목은 list 구성 시 제외 (ASIS 동일)
```

### 5.3 TOBE 구현 계획

#### 신규/수정 파일 목록

| 파일 | 종류 | 설명 |
|------|------|------|
| `termination/dto/RemainChargeReqDto.java` | **신규** | 요청 DTO (`ncn`, `ctn`, `custId`) |
| `termination/dto/RemainChargeResVO.java` | **신규** | 응답 VO (`success`, `searchDay`, `searchTime`, `sumAmt`, `items`) |
| `termination/controller/MsfSvcCnclRestController.java` | **신규** | TOBE REST 컨트롤러 — 레거시 컨트롤러와 분리 생성 |
| `termination/service/MsfCancelConsultSvc.java` | **수정** | `getRemainCharge(RemainChargeReqDto)` 메서드 추가 |
| `termination/service/MsfMsfCancelConsultSvcImpl.java` | **수정** | `getRemainCharge()` — X18 연동 구현 |

#### 요청/응답 구조

```json
// 요청: POST /api/v1/cancel/remain-charge
{
  "ncn": "100000001",
  "ctn": "01012345678",
  "custId": "CUST001"
}

// 응답 (성공)
{
  "success": true,
  "searchDay": "20260410",
  "searchTime": "2026.04.01 ~ 04.10",
  "sumAmt": "15,000",
  "items": [
    { "gubun": "월정액",    "payment": "10,000" },
    { "gubun": "부가서비스", "payment": "5,000"  }
  ]
}

// 응답 (실패 / X18 오류)
{
  "success": false,
  "message": "잔여요금 조회 중 오류가 발생했습니다."
}
```

#### `RemainChargeResVO` 필드 정의

| 필드 | 타입 | 설명 | 매핑 원본 (`MpFarRealtimePayInfoVO`) |
|------|------|------|--------------------------------------|
| `success` | boolean | 조회 성공 여부 | — |
| `searchDay` | String | 조회 기준 날짜 (yyyyMMdd) | `getSearchDay()` |
| `searchTime` | String | 조회 기간 문자열 | `getSearchTime()` (가공 후 — "2026.04.01 ~ 04.10") |
| `sumAmt` | String | 당월요금 합계 | `getSumAmt()` |
| `items` | `List<Item>` | 요금 항목 목록 | `getList()` (`"원단위절사금액"` 제외) |
| `items[].gubun` | String | 요금 항목명 | `RealFareVO.getGubun()` |
| `items[].payment` | String | 요금 금액 (원 포함 문자열) | `RealFareVO.getPayment()` |

#### `MsfMsfCancelConsultSvcImpl.getRemainCharge()` 처리 순서

1. `ncn` / `ctn` 필수값 검증 — 미입력 시 `RemainChargeResVO.fail()` 반환
2. `msfMplatFormService.farRealtimePayInfo(ncn, ctn, custId)` 호출 (X18 전문)
3. `vo.isSuccess()` 체크 — false이면 fail 반환
4. `searchDay` substring(6,8) → useDay 추출 (ASIS 동일 처리)
5. `vo.getList()`에서 `"원단위절사금액"` 항목 제외 후 `items` 구성
6. `RemainChargeResVO` 빌드 후 반환
7. 예외 처리:
   - `SelfServiceException` — `logger.info("X18 ERROR")` 후 `success: false` 반환
   - `SocketTimeoutException` — 동일
   - `Exception` — 동일

#### Controller 전환 방향

| 구분 | 클래스 | 방식 |
|------|--------|------|
| 레거시 유지 | `MsfCancelConsultController` | `@Controller` — JSP 방식 유지 (ASIS 하위 호환) |
| **TOBE 신규** | `MsfSvcCnclRestController` | `@RestController @RequestMapping("/api/v1/cancel")` |

`MsfSvcCnclRestController` 포함 엔드포인트 (X18 포함 해지 전체):

| 메서드 | 엔드포인트 | 서비스 메서드 | 연동 |
|--------|-----------|-------------|------|
| POST | `/api/v1/cancel/remain-charge` | `getRemainCharge()` | X18 |
| POST | `/api/v1/cancel/join-info` | (별도 구현) | X01 |
| POST | `/api/v1/cancel/apply` | `cancelConsultRequest()` | DB |

### 5.4 LOCAL 목업 처리

`MsfMplatFormService` 내 `serverLocation == "LOCAL"` 분기:
```java
case 18: // 실시간요금조회 mock XML 자동 반환
    getVo(18, vo);
```
별도 목업 파일 작성 불필요 — 기존 LOCAL 분기 그대로 활용.

---

## 4. 기타: 시스템 안전성을 위한 내부 데이터 제어

시스템 간 데이터 제어 과정에서 클라이언트 측 혼란을 막기 위해 자체적으로 개입하는 백엔드 규칙입니다.

*   **더미 서비스 원천 필터링**: '아무나SOLO' 요금제 등 가입 시 내부 관리를 위해 M플랫폼에서 자동으로 내려오는 결합 전용 더미 부가서비스(예: `PL249Q800`)는, M플랫폼 `X97` 연동 직후 서비스 레이어에서 강제로 `removeIf` 시켜 고객 조회 화면이나 프론트엔드 데이터에 쓰레기값이 넘어가지 않도록 강력하게 통제합니다.
