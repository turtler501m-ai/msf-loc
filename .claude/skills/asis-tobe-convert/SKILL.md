---
name: asis-tobe-convert
description: ASIS(mcp) → TOBE(msf) 패키지 변환 스킬. form/servicechange 등 레거시 파일을 TOBE 구조로 이관할 때 자동 참조. "변환", "이관", "Msf 붙여줘", "TOBE로" 언급 시 적용.
trigger: ASIS 파일을 TOBE로 변환·이관하는 작업 시 항상 참조.
---

# ASIS → TOBE 변환 스킬

> 기준 문서: `.doc/12.MSF_백엔드_패키지구조_변경계획.md`
> 현행화: 2026-04-11 (실제 코드베이스 패키지 구조 반영)

---

## 1. 패키지 매핑

### 1-1. 시스템 공통

### 1-2. 업무 서식 (form)

| ASIS (mcp) | TOBE (msf) | 폴더 | Java 패키지 |
|------------|------------|------|------------|
| `mcp/order` + `mcp/phone` + `mcp/usim` | `form/common` | `form/common/` | `com.ktmmobile.msf.domains.form.form.common` |
| `mcp/appform` | `form/newchange` | `form/newchange/` | `com.ktmmobile.msf.domains.form.form.newchange` |
| `mcp/content` + `mcp/mypage`(서비스변경) | `form/servicechange` | `form/servicechange/` | `com.ktmmobile.msf.domains.form.form.servicechange` |
| `mcp/mypage`(명의변경) | `form/ownerchange` | `form/ownerchange/` | `com.ktmmobile.msf.domains.form.form.ownerchange` |
| `mcp/mypage`(해지) | `form/termination` | `form/termination/` | `com.ktmmobile.msf.domains.form.form.termination` |

### 1-3. import 변환표

| ASIS import | TOBE import |
|-------------|-------------|
| `com.ktmmobile.mcp.content.*` | `com.ktmmobile.msf.domains.form.form.servicechange.*` |
| `com.ktmmobile.mcp.mypage.*` | `com.ktmmobile.msf.domains.form.form.servicechange.*` (또는 ownerchange/termination) |
| `com.ktmmobile.mcp.appform.*` | `com.ktmmobile.msf.domains.form.form.newchange.*` |
| `com.ktmmobile.mcp.common.*` | `com.ktmmobile.msf.domains.form.common.*` |
| `com.ktmmobile.mcp.cert.*` | `com.ktmmobile.msf.domains.form.system.cert.*` |


---

## 2. 네이밍 규칙

### ~~2-1. `Msf` 접두사 — Controller·Service에만 적용~~ (비활성)

<!--
MsfXxxController.java       ← REST 컨트롤러 (@RestController)
MsfXxxSvc.java              ← 서비스 인터페이스
MsfXxxSvcImpl.java          ← 서비스 구현체 (@Service)

- DAO / Mapper / DTO 는 Msf 접두사 불필요
- ASIS @Controller → TOBE @RestController 로 교체
- ASIS 메서드에 Model, HttpSession, @RequestMapping → TOBE @PostMapping / @GetMapping + @RequestBody 로 교체
-->

> **[비활성]** Msf 접두사 규칙 적용 보류 — 현재 ASIS 원본 클래스명 유지 방식으로 개발 중

### 2-2. ASIS↔TOBE 함수명 일치 원칙 (수정내역 추적 용이)

ASIS와 TOBE의 함수명을 최대한 동일하게 유지하여 diff 비교 및 변환 이력 추적이 쉽도록 한다.

| 레이어 | 규칙 | 예시 |
|--------|------|------|
| **Service (인터페이스·Impl)** | ASIS 함수명 그대로 유지. 파라미터·반환값 시그니처만 TOBE 타입으로 교체. | `selectmyAddSvcList` → `selectMyAddSvcList` (대소문자 정규화만) |
| **Controller** | ASIS 함수명에서 `Ajax` 접미사만 제거. URL은 REST 경로로 변경. | `myAddSvcListAjax` → `myAddSvcList` |

```java
// ASIS Service
MpAddSvcInfoParamDto selectmyAddSvcList(String ncn, String ctn, String custId);

// TOBE Service — 함수명 유지, 시그니처만 교체
AdditionMyListResVO selectMyAddSvcList(AdditionReqDto req);

// ASIS Controller
@RequestMapping("/mypage/myAddSvcListAjax.do")
public Map<String,Object> myAddSvcListAjax(HttpServletRequest request, ...)

// TOBE Controller — "Ajax" 제거, URL을 REST로 변경
@PostMapping("/api/v1/addition/my-list")
public ResponseEntity<AdditionMyListResVO> myAddSvcList(@RequestBody AdditionReqDto req)
```

**주의**: TOBE 메서드에 새 이름을 붙이지 않는다. `cancelAddSvc`, `regAddSvc` 같은 신규 명칭보다 ASIS 원본인 `moscRegSvcCanChg`, `regSvcChg` 를 그대로 사용한다.

### 2-3. HTTP 메서드 규칙

| 용도 | 메서드 |
|------|--------|
| 단순 조회 (파라미터 없거나 path variable만) | `GET` |
| 조회·저장·변경·삭제 등 나머지 | `POST` |

PUT / PATCH / DELETE **사용 금지**

---

## 3. 변환 절차 (파일 1개 기준)

### Step 1 — 파일 위치 결정

1. ASIS 파일이 속한 `mcp/{도메인}` 확인
2. 위 패키지 매핑표에서 대응 TOBE 폴더 결정
3. `form/servicechange/`, `form/newchange/` 등 TOBE 폴더에 파일 생성

### Step 2 — package 선언 수정

```java
// ASIS
package com.ktmmobile.mcp.content.service;

// TOBE
package com.ktmmobile.msf.domains.form.form.servicechange.service;
```

```java
// ASIS
package com.ktmmobile.mcp.mypage.service;  // 해지 관련

// TOBE
package com.ktmmobile.msf.domains.form.form.termination.service;
```

### Step 3 — import 일괄 교체

```
com.ktmmobile.mcp.content.   →  com.ktmmobile.msf.domains.form.form.servicechange.
com.ktmmobile.mcp.mypage.    →  com.ktmmobile.msf.domains.form.form.servicechange.
                                (또는 ownerchange. / termination. — 업무 맥락에 따라)
com.ktmmobile.mcp.appform.   →  com.ktmmobile.msf.domains.form.form.newchange.
com.ktmmobile.mcp.common.    →  com.ktmmobile.msf.domains.form.common.
com.ktmmobile.mcp.cert.      →  com.ktmmobile.msf.domains.form.system.cert.
```

미이관 패키지(ktds.crypto, nl.captcha 등) import는 해당 스텁 클래스 참조 또는 빌드 exclude 처리.

### Step 5 — MVC → REST 전환 (Controller)

| ASIS 패턴 | TOBE 패턴                                                     |
|-----------|-------------------------------------------------------------|
| `@Controller` | `@RestController`                                           |
| `@RequestMapping("/xxx.do")` | `@PostMapping("/api/v1/Msfxxx")`                            |
| `String doXxx(Model model, HttpSession session)` | `ResponseEntity<XxxResDto> xxx(@RequestBody XxxReqDto req)` |
| `model.addAttribute(...)` → view 반환 | `return ResponseEntity.ok(resDto)`                          |
| `SessionUtils.getUserCookieBean()` | 요청 파라미터에서 직접 수신                                             |
| `return "redirect:..."` | `return ResponseEntity.ok(...)` with redirectUrl 필드         |

### Step 6 — Mapper 배치

| 분류 | 배치 위치 |
|------|----------|
| DaoImpl (SqlSession 주입) | `form/{업무}/dao/XxxDaoImpl.java` |
| XML 매퍼 | `form/{업무}/mapper/XxxMapper.xml` (Java 소스 폴더 내) |

XML namespace 예:
```xml
<mapper namespace="com.ktmmobile.msf.domains.form.form.termination.mapper.CancelConsultMapper">
```

새 `@Mapper` 인터페이스 도입 시 반드시 `FormApiApplication.java` `@MapperScan`에 패키지 추가.

---

## 4. ASIS 로직 처리 원칙

### 4-1. TOBE 무관 로직 — 삭제 금지, 반드시 COMMENT 처리

TOBE 변환 시 현재 구현과 무관하거나 미이관 기능의 로직은 **절대 삭제하지 않는다.**
반드시 주석으로 감싸고 이유를 명시한다.

```java
// [ASIS] 포인트 처리 — 포인트 기능 미이관, 추후 구현
// MspRateMstDto mspRateMstDto = fCommonSvc.getMspRateMst(soc);
// if (mspRateMstDto != null) {
//     pointService.editPoint(custId, soc);
// }

// [ASIS] 인증 로그 — 공통 미구현 (31번 문서 §1-3 참조)
// certService.getStepCnt(ncn, ctn);
// certService.vdlCertInfo(ncn, ctn, custId);

// [ASIS] 세션 기반 사용자 조회 — Stateless REST로 전환, 요청 바디에서 직접 수신
// UserSessionDto userSession = SessionUtils.getUserCookieBean();
// String ncn = userSession.getNcn();
```

### 4-2. COMMENT 작성 형식

```java
// [ASIS] {기능 설명} — {제외 이유}
// 원본 코드 ...
```

| 제외 이유 유형 | 표기 예시 |
|-------------|---------|
| 미이관 패키지 의존 | `포인트 기능 미이관` |
| 공통 미구현 | `공통 미구현 (31번 §1-3)` |
| Stateless 전환 | `Stateless REST 전환` |
| View 렌더링 불필요 | `REST 응답으로 대체` |
| 기구현으로 대체 | `join-info 기구현으로 대체` |

---
