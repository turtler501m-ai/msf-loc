---
name: asis-tobe-convert
description: ASIS(mcp) → TOBE(msf) 패키지 변환 스킬. form/servicechange 등 레거시 파일을 TOBE 구조로 이관할 때 자동 참조. "변환", "이관", "Sf 붙여줘", "TOBE로" 언급 시 적용.
trigger: ASIS 파일을 TOBE로 변환·이관하는 작업 시 항상 참조.
---

# ASIS → TOBE 변환 스킬

> 기준 문서: `.doc/12.MSF_백엔드_패키지구조_변경계획.md`

---

## 1. 패키지 매핑

### 1-1. 시스템 공통

| ASIS (mcp) | TOBE (msf) |
|------------|------------|
| `mcp/app` | `msf/login` |
| `mcp/cs` | `msf/main` |
| `mcp/common` | `msf/common` |
| `mcp/fath` | `msf/fath` |
| `mcp/cert` | `msf/cert` |

### 1-2. 업무 서식 (form)

| ASIS (mcp) | TOBE (msf) | 폴더 | Java 패키지 |
|------------|------------|------|------------|
| `mcp/order` + `mcp/phone` + `mcp/usim` | `msf/form/common` | `form/common/` | `form.common` |
| `mcp/appform` | `msf/form/newchange` | `form/newchange/` | `form.newchange` |
| `mcp/content` + `mcp/mypage`(서비스변경) | `msf/form/servicechange` | `form/servicechange/` | `form.servicechange` |
| `mcp/mypage`(명의변경) | `msf/form/ownerchange` | `form/ownerchange/` | `form.ownerchange` |
| `mcp/mypage`(해지) | `msf/form/termination` | `form/termination/` | `form.termination` |

### 1-3. import 변환표

| ASIS import | TOBE import |
|-------------|-------------|
| `com.ktmmobile.mcp.content.*` | `com.ktmmobile.msf.form.servicechange.*` |
| `com.ktmmobile.mcp.mypage.*` | `com.ktmmobile.msf.form.servicechange.*` (또는 ownerchange/termination) |
| `com.ktmmobile.mcp.appform.*` | `com.ktmmobile.msf.form.newchange.*` |
| `com.ktmmobile.mcp.common.*` | `com.ktmmobile.msf.common.*` |
| `com.ktmmobile.mcp.fath.*` | `com.ktmmobile.msf.fath.*` |
| `com.ktmmobile.mcp.cert.*` | `com.ktmmobile.msf.cert.*` |

---

## 2. 네이밍 규칙

### 2-1. `Sf` 접두사 — Controller·Service에만 적용

```
SfXxxController.java       ← REST 컨트롤러 (@RestController)
SfXxxSvc.java              ← 서비스 인터페이스
SfXxxSvcImpl.java          ← 서비스 구현체 (@Service)
```

- **DAO / Mapper / DTO** 는 Sf 접두사 불필요
- ASIS `@Controller` → TOBE `@RestController` 로 교체
- ASIS 메서드에 `Model`, `HttpSession`, `@RequestMapping` → TOBE `@PostMapping` / `@GetMapping` + `@RequestBody` 로 교체

### 2-2. HTTP 메서드 규칙

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
package com.ktmmobile.msf.form.servicechange.service;
```

### Step 3 — import 일괄 교체

```
com.ktmmobile.mcp.  →  com.ktmmobile.msf.
mcp/content/        →  form/servicechange/
mcp/mypage/         →  form/servicechange/ (또는 ownerchange/termination)
```

미이관 패키지(ktds.crypto, nl.captcha 등) import는 해당 스텁 클래스 참조 또는 pom.xml exclude 처리.

### Step 4 — 클래스명에 Sf 추가 (Controller·Service만)

```java
// ASIS
public class MyShareDataController { ... }
public interface MyShareDataSvc { ... }

// TOBE
public class SfMyShareDataController { ... }
public interface SfMyShareDataSvc { ... }
```

### Step 5 — MVC → REST 전환 (Controller)

| ASIS 패턴 | TOBE 패턴 |
|-----------|-----------|
| `@Controller` | `@RestController` |
| `@RequestMapping("/xxx.do")` | `@PostMapping("/api/v1/xxx")` |
| `String doXxx(Model model, HttpSession session)` | `ResponseEntity<XxxResDto> xxx(@RequestBody XxxReqDto req)` |
| `model.addAttribute(...)` → view 반환 | `return ResponseEntity.ok(resDto)` |
| `SessionUtils.getUserCookieBean()` | 요청 파라미터에서 직접 수신 |
| `return "redirect:..."` | `return ResponseEntity.ok(...)` with redirectUrl 필드 |

### Step 6 — Mapper 배치

| 분류 | 배치 위치 |
|------|----------|
| 업무 Mapper (`@Mapper` 인터페이스) | `form/{업무}/mapper/` |
| XML 매퍼 | `resources/mapper/form/{업무}/` |

새 `@Mapper` 생성 시 반드시 `MsfApplication.java` `@MapperScan`에 패키지 추가.

---

## 4. 레이어 템플릿

### 4-1. REST Controller

```java
package com.ktmmobile.msf.form.servicechange.controller;

import com.ktmmobile.msf.form.servicechange.dto.XxxReqDto;
import com.ktmmobile.msf.form.servicechange.dto.XxxResDto;
import com.ktmmobile.msf.form.servicechange.service.SfXxxSvc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SfXxxController {

    private final SfXxxSvc sfXxxSvc;

    @PostMapping("/xxx/action")
    public ResponseEntity<XxxResDto> action(@RequestBody XxxReqDto req) {
        return ResponseEntity.ok(sfXxxSvc.action(req));
    }
}
```

### 4-2. Service Interface

```java
package com.ktmmobile.msf.form.servicechange.service;

import com.ktmmobile.msf.form.servicechange.dto.XxxReqDto;
import com.ktmmobile.msf.form.servicechange.dto.XxxResDto;

public interface SfXxxSvc {
    XxxResDto action(XxxReqDto req);
}
```

### 4-3. Service Impl

```java
package com.ktmmobile.msf.form.servicechange.service;

import com.ktmmobile.msf.common.mplatform.MplatFormService;
import com.ktmmobile.msf.form.servicechange.dto.XxxReqDto;
import com.ktmmobile.msf.form.servicechange.dto.XxxResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SfXxxSvcImpl implements SfXxxSvc {

    private final MplatFormService mplatFormService;

    @Override
    public XxxResDto action(XxxReqDto req) {
        // M플랫폼 호출
        return mplatFormService.xxxCall(req.getNcn(), req.getCtn(), req.getCustId());
    }
}
```

---

## 5. ASIS 파일 처리 방침

`form/servicechange/` 안에는 구(舊) 임시 이관 파일과 신규 TOBE 파일이 혼재함.

| 구분 | 파일 위치 | 처리 |
|------|----------|------|
| 구 임시 이관 파일 (Sf 접두사 없음) | `form/servicechange/**` | 유지 (참조용, 이관 완료 후 일괄 삭제) |
| 신규 TOBE 파일 (Sf 접두사 있음) | `form/servicechange/**` | 적극 개발 (컴파일 대상) |

- ASIS 파일은 **삭제하지 말고** 이관 완료 후 일괄 처리
- 신규 TOBE 파일은 동일 폴더(`form/servicechange/`) 안에 `Sf` 접두사로 구분

---

## 6. @MapperScan 등록 목록 (MsfApplication.java)

```java
@MapperScan({
    "com.ktmmobile.msf.common.mapper",
    "com.ktmmobile.msf.form.common.mapper",
    "com.ktmmobile.msf.form.newchange.mapper",
    "com.ktmmobile.msf.form.servicechange.mapper",
    "com.ktmmobile.msf.form.ownerchange.mapper",
    "com.ktmmobile.msf.form.termination.mapper"
})
```

신규 도메인 Mapper 패키지 추가 시 반드시 여기에 등록. 누락 시 서버 기동 실패.

---

## 7. 주의사항

- `form/{업무}` → `form/comm` 단방향 참조만 허용. 업무 패키지 간 직접 참조 금지.
- ASIS `SessionUtils` 의존 로직은 REST 파라미터로 대체 (userId, ncn, ctn 등 요청 바디에서 직접 수신).
- ASIS `NmcpServiceUtils`, `EncryptUtil` 등 미이관 공통 유틸 직접 참조 금지 — 필요 시 별도 TOBE 유틸로 재구현.
- 테이블명: `MCP_*` / `NMCP_*` → `MSF_*` 교체. `MSP_*` 는 그대로 유지.
