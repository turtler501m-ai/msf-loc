# CLAUDE.md

이 파일은 Claude Code가 이 저장소에서 작업할 때 참조하는 프로젝트 가이드입니다.

> 전체 구조 상세: `.doc/11.MSF_프로젝트_전체구조.md`

---

## 프로젝트 개요

**MSF (스마트서식지)** — 대리점용 모바일 고객 서비스 신청 시스템.
기존 M포탈(MCP)과 동일 기능을 신규 개발한 대체 시스템. M플랫폼/KT(KOS) 연동 스펙 동일하게 구현.

| 폴더 | 역할 |
|------|------|
| `mcp/` | 기존 M포탈 (레거시, ASIS 참조용) |
| `msp/` | M플랫폼 연계처리 (레거시, 참조용) |
| `msf/msf-api/` | 신규 백엔드 (Java 11, Spring Boot 2.7, MyBatis, PostgreSQL) |
| `msf/msf-web/` | 신규 프론트엔드 (Vue 3, Vite, Tailwind CSS 4) |

연동 아키텍처:
```
msf-web (port 9480)  →[/api proxy]→  msf-api (port 8081, /api/v1)
    → M전산 DB (PostgreSQL/Oracle)
    → M플랫폼 HTTP → KT(KOS)
```

---

## 실행 명령어

### Backend

```bash
cd msf/msf-api

# 빌드
mvn -DskipTests package

# 실행
mvn spring-boot:run
# 또는
java -jar target/mform-api-0.1.0-SNAPSHOT.jar
```

DB: `localhost:5432/msf` (user: postgres / pw: postgres 또는 `DB_PASSWORD` 환경변수)
테스트 계정: 홍길동 / 01012345678

### Frontend

```bash
cd msf/msf-web
npm install
npm run dev      # port 9480, /api → localhost:8081
npm run build
npm run lint     # oxlint + eslint
npm run format   # prettier
```

---

## 백엔드 아키텍처 (msf-api)

루트 패키지: `com.ktmmobile.msf`

### 도메인 패키지

| 패키지 | 역할 | API 경로 |
|--------|------|---------|
| `formComm` | 공통: 가입자정보조회, 동시처리체크 | `/api/v1/{domain}/join-info` |
| `formSvcChg` | 서비스변경 (부가서비스·요금상품) | `/api/v1/addition/*`, `/api/v1/service-change/*` |
| `formOwnChg` | 명의변경 | `/api/v1/ident/*` |
| `formSvcCncl` | 서비스해지 | `/api/v1/cancel/*` |
| `common.mplatform` | M플랫폼 HTTP 연동 어댑터 | - |

각 도메인: `controller / dto / service / mapper` 레이어 구성.

의존성: `formComm → common.mplatform` / 업무 패키지 → `formComm.dto/service` / 업무 패키지 간 직접 참조 금지

### 네이밍 규칙

- Controller: `XXXController`(내부용) / `XXXRestController`(연동용)
- Service: `XXXSvc.java` / `XXXSvcImpl.java`
- DTO: `XXXReqDto`(요청) / `XXXResVO`(응답)
- Mapper 메서드: `insert/save/update/delete/select/selectList` + 서비스명칭

### 주요 API 흐름

**가입자정보조회**: `POST /api/v1/{domain}/join-info`
→ `JoinInfoSvc.joinInfo()` → M전산(ContractInfoMapper) → Y04(인증) → X01(가입정보) → `JoinInfoResVO`

**공통 요청 바디**: `SvcChgInfoDto` (name, ncn, ctn, custId)

### M플랫폼 연동 코드 (MplatFormSvc)

| 코드 | 기능 |
|------|------|
| Y04 | 휴대폰 인증 (contractValdChk) |
| X01 | 가입정보조회 (perMyktfInfo) |
| X20 | 가입중 부가서비스 조회 |
| X21/X38 | 부가서비스 신청/해지 |
| X19 | 요금상품 변경 |
| X88/X89/X90 | 요금상품 예약/예약취소 |

`application.properties`에서 `SERVER_NAME=LOCAL` 시 Mock 응답 반환.

---

## 프론트엔드 아키텍처 (msf-web)

### 라우팅

- `/mobile/:domain/:service` → `components/{domain}/{service}.vue` 동적 로드
- `/mobile/complete/:domain` → 완료 화면

도메인: `addition`(부가서비스변경), `cancel`(서비스해지), `change`(서비스변경), `ident`(명의변경)

### 상태관리 (`src/stores/`)

| 스토어 | 역할 |
|--------|------|
| `msf_comp_loading` | 전역 로딩 오버레이 |
| `msf_menu` | 메뉴/네비게이션 |
| `msf_step` | 스텝 진행 관리 |
| `service_change_form` | 서비스변경 폼 데이터 |
| `ident_form` | 명의변경 폼 데이터 |
| `cancel_form` | 서비스해지 폼 데이터 |

### API 클라이언트 (`src/api/`)

- `msf.js` : 기본 fetch 래퍼 (msfPost, msfGet)
- `serviceChange.js` / `ident.js` / `cancel.js`

### 기술 스택

JavaScript (TypeScript 미사용) / Vue 3.5 / Vue Router 5 / Pinia 3 / Vite 7 / Tailwind CSS 4 / reka-ui / axios / ag-grid-vue3

---

## 개발 노트

- **분석/계획 문서**는 `.doc/` 폴더에 `.md` 파일로 생성
- M포탈 소스와 동일하게 개발; 연동 API 스펙(요청/응답 형식) 동일하게 구현
- 기능 목록 및 연동 스펙은 `.doc/` 내 문서 참조
- **컨텍스트 최적화**: `.claudeignore`로 레거시 정적 리소스·빌드 산출물 제외됨

---

## 개발 규칙 (반드시 체크)

### 1. 신규 @Mapper 패키지 생성 시 — @MapperScan 등록 필수

새 도메인 `@Mapper` 생성 시 **반드시** `MsfApplication.java`의 `@MapperScan`에 추가.
누락 시 `NoSuchBeanDefinitionException`으로 서버 기동 실패.

```java
@MapperScan({
    "com.ktmmobile.msf.formComm.mapper",
    "com.ktmmobile.msf.formSvcChg.mapper",
    "com.ktmmobile.msf.formOwnChg.mapper",
    "com.ktmmobile.msf.formSvcCncl.mapper"
    // ← 신규 도메인 mapper 패키지 추가
})
```

### 2. Windows API 테스트 — curl 대신 Node.js 사용

Windows에서 `curl`로 한글 JSON 전송 시 UTF-8 인코딩 오류 발생. Node.js `http.request()` 사용.

```js
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

```bash
taskkill //F //IM java.exe          # java 프로세스 종료
cd msf/msf-api && mvn -DskipTests package
java -jar target/mform-api-0.1.0-SNAPSHOT.jar
```

### 4. 코드 변경 후 문서 업데이트

| 변경 내용 | 업데이트 문서 |
|-----------|--------------|
| 새 Controller/Service/Mapper 구현 | `15.개발기능목록_ASIS_TOBE_기능분석명세서.md` |
| 새 도메인 패키지 또는 API 경로 변경 | `18.요구사항ID별_ASIS_TOBE_개발진행명세.md` |
| 미구현 → 구현 완료 상태 변경 | 18번 문서 해당 절 업데이트 |
