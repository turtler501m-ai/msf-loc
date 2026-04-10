# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**MSF (스마트서식지, Smart Form)** — 대리점용 모바일 고객 서비스 신청 시스템. 기존 M포탈(MCP)과 **동일 기능**을 **신규 개발**한 대체 시스템이며, M플랫폼/KT(KOS) 연동 스펙은 동일하게 구현.

- **mcp/** : 기존 M포탈 (레거시, 참조용)
- **msp/** : M플랫폼 연계처리 (레거시, 참조용)
- **msf-api/** : 신규 백엔드 (Java 11, Spring Boot 2.7, MyBatis, PostgreSQL)
- **msf-web/** : 신규 프론트엔드 (Vue 3, Vite, Tailwind CSS 4)

연동 아키텍처:
```
[대리점] msf-web (port 9480)
    ↓ /api → proxy
msf-api (port 8081, /api/v1)
    ↓
M플랫폼 / M전산(PostgreSQL)
    ↓
KT(KOS)
```

## Commands

### Backend (msf-api)

```bash
# 빌드
mvn -DskipTests package

# 실행
mvn spring-boot:run
# 또는
java -jar target/mform-api-0.1.0-SNAPSHOT.jar

# DB 초기화 (PostgreSQL)
createdb -U postgres mform
psql -U postgres -d mform -f ktm/doc/tabdoc/kcf_tab_create1.sql
```

DB 접속: `localhost:5432/mform` (user: postgres / pw: postgres 또는 `DB_PASSWORD` 환경변수)
테스트 계정: 홍길동 / 01012345678

### Frontend (msf-web)

```bash
npm install
npm run dev        # 개발 서버 (port 9480, /api → localhost:8081 프록시)
npm run build
npm run lint       # oxlint + eslint
npm run format     # prettier
```

## Backend Architecture (msf-api)

루트 패키지: `com.ktmmobile.msf`

### 도메인 패키지 구조

| 패키지 | 역할 |
|--------|------|
| `formComm` | 공통 API: 가입자정보조회, 동시처리체크 |
| `formSvcChg` | 서비스변경 (부가서비스, 요금상품) |
| `formOwnChg` | 명의변경 |
| `formSvcCncl` | 서비스해지 |
| `common.mplatform` | M플랫폼 HTTP 연동 어댑터 (X01, X20, Y04 등) |

각 도메인은 `controller / dto / service / mapper` 레이어로 구성.

### 의존성 방향

- `formComm` → `common.mplatform` 만 참조
- 각 업무 패키지 → `formComm.dto/service` 참조 가능, 업무 패키지 간 직접 참조 금지 (DTO 제외)

### 네이밍 규칙

- `XXXController.java` : 내부용 / `XXXRestController.java` : 연동용
- `XXXSvc.java` / `XXXSvcImpl.java` : 서비스 인터페이스/구현체
- 요청 DTO: `XXXReqDto`, 응답 VO: `XXXResVO`
- 메서드: `insert/save/update/delete/select/selectList` + 서비스명칭

### 주요 API 흐름

**가입자정보조회**: `POST /api/v1/{domain}/join-info`
→ `JoinInfoSvc.joinInfo()` → M전산(ContractInfoMapper) → Y04(인증) → X01(가입정보) → `JoinInfoResVO`

**업무 API 공통 요청 바디**: `SvcChgInfoDto` (name, ncn, ctn, custId)

### M플랫폼 연동 코드 (MplatFormSvc)

| 코드 | 기능 |
|------|------|
| X01 | 가입정보조회 (perMyktfInfo) |
| X20 | 가입중 부가서비스 조회 |
| X21/X38 | 부가서비스 신청/해지 |
| X19 | 요금상품 변경 |
| X88/X89/X90 | 요금상품 예약/예약취소 |
| Y04 | 휴대폰 인증 (contractValdChk) |

`application.properties`에서 `LOCAL` 모드 설정 시 Mock 응답 반환.

## Frontend Architecture (msf-web)

### 라우팅

- `/mobile/:domain/:service` → `components/{domain}/{service}.vue` 동적 로드
- `/mobile/complete/:domain` → 완료 화면

도메인: `addition`(부가서비스변경), `cancel`(서비스해지), `change`(서비스변경), `ident`(명의변경)

### 상태관리 (Pinia stores, `src/stores/`)

| 스토어 | 역할 |
|--------|------|
| `msf_comp_loading` | 전역 로딩 오버레이 |
| `msf_menu` | 메뉴/네비게이션 |
| `service_change_form` | 서비스변경 폼 데이터 |
| `ident_form` | 명의변경 폼 데이터 |
| `cancel_form` | 서비스해지 폼 데이터 |

### API 클라이언트 (`src/api/`)

- `msf.js` : 기본 fetch 래퍼 (`msfFetch`, `msfPost`, `msfGet`)
- `serviceChange.js` : 서비스변경/부가서비스 API
- `ident.js` : 명의변경 API
- `cancel.js` : 서비스해지 API

### 기술 스택

- JavaScript (TypeScript 미사용)
- Vue 3.5, Vue Router 5, Pinia
- Vite 7, Tailwind CSS 4
- reka-ui (컴포넌트 라이브러리), @vueuse/core

## Development Notes

- **분석/계획 문서**는 `.doc/` 폴더에 `.md` 파일로 생성
- M포탈 소스 코드 내용 동일하게 개발; 연동 API 스펙(요청/응답 형식) 동일하게 구현
- 기능 목록 및 연동 스펙은 `.doc/` 내 문서 참조

## Development Rules (개발 시 반드시 체크)

### 1. 신규 @Mapper 패키지 생성 시 — @MapperScan 등록 필수

새 도메인의 `@Mapper` 인터페이스를 생성할 때 **반드시** `MsfApplication.java`의 `@MapperScan`에 패키지를 추가한다.
누락 시 Spring Bean 등록 실패(`NoSuchBeanDefinitionException`)로 서버 기동 오류 발생.

```java
// MsfApplication.java
@MapperScan({
    "com.ktmmobile.msf.formComm.mapper",
    "com.ktmmobile.msf.formSvcChg.mapper",
    "com.ktmmobile.msf.formOwnChg.mapper",
    "com.ktmmobile.msf.formSvcCncl.mapper"
    // ← 신규 도메인 mapper 패키지 추가
})
```

### 2. Windows에서 API 테스트 — curl 대신 Node.js 사용

Windows 환경에서 `curl`로 한글이 포함된 JSON을 전송하면 UTF-8 인코딩 오류가 발생한다.
**Node.js `http.request()`를 사용**한다.

```js
// api-test.js 예시
const http = require('http');
const body = JSON.stringify({ name: "홍길동", ctn: "01012345678" });
const req = http.request(
  { hostname: 'localhost', port: 8081, path: '/api/v1/cancel/eligible', method: 'POST',
    headers: { 'Content-Type': 'application/json; charset=utf-8', 'Content-Length': Buffer.byteLength(body) }},
  res => { let d=''; res.on('data', c=>d+=c); res.on('end', ()=>console.log(d)); }
);
req.write(body); req.end();
```

### 3. 서버 재시작 절차 (JAR 잠금 해제)

실행 중인 서버가 JAR 파일을 점유하므로, 재빌드 전 반드시 종료해야 한다.

```bash
# 1. java 프로세스 강제 종료 (Windows)
taskkill //F //IM java.exe

# 2. 빌드
cd msf-api && mvn -DskipTests package

# 3. 서버 실행
java -jar target/mform-api-0.1.0-SNAPSHOT.jar
```

### 4. 코드 변경 후 문서 업데이트

코드 구현·수정 후 `.doc/` 문서를 함께 업데이트한다.

| 변경 내용 | 업데이트 문서 |
|-----------|--------------|
| 새 Controller/Service/Mapper 구현 | `15.개발기능목록_ASIS_TOBE_기능분석명세서.md` |
| 새 도메인 패키지 또는 API 경로 변경 | `18.요구사항ID별_ASIS_TOBE_개발진행명세.md` |
| 미구현 → 구현 완료 상태 변경 | 18번 문서 해당 절 + 20절(미구현 항목) |