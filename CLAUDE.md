# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

> 전체 구조 상세: `.doc/11.MSF_프로젝트_전체구조.md`
> 실행 시 옵션: `--dangerously-skip-permissions`

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

## 중요 참조문서로 설계 개발시에 반드시 참조할것

.doc/
├── 11.MSF_프로젝트_전체구조.md
├── 12.MSF_백엔드_패키지구조_변경계획.md

├── asis/                                                   # ASIS 분석 문서
│   ├── Z01.MCP_폴더구조_분석.md
│   ├── Z11.DS-08-ITO소스분析_서비스변경.md

├── reference/                                              # 연동 설계 문서
│   ├── D1.스마트서식지-DS-05-테이블정의서_V1.0_20260318.md
│   ├── D2.테이블매핑_20260319.md
│   ├── D3.MCP_REQUEST_MSF_REQUEST_컬럼매핑.md
│   ├── E1.스마트서식지-DS-08-ITO소스분析_v1_0_20260324.md
│   └── G1.스마트서식지-DS-08-ITO소스분析.md
├── uidoc/                                                  # UI 설계 문서
│   ├── MMSP-DS-02-명의변경_신청서_UI설계서.md
│   ├── MMSP-DS-02-서비스변경_신청서_UI설계서.md
│   ├── MMSP-DS-02-서비스해지_신청서_UI설계서.md
│   └── MMSP-DS-02-서비스해지_신청서_UI설계서_20260324.md
├── .backup/                                                # PDF/PPTX 원본 파일 및 스크립트 백업
└── old/                                                    # 구버전 분석 문서 아카이브 (60개+)


## TOBE 개발시 반드시 참고

- 접두사 `Msf`  콘드롤,서비스명 변경 (현재 완료)
- ASIS 로직 처리 원칙 섹션 추가:
  - TOBE 무관 로직은 **삭제 금지**, 반드시 주석 처리
  - 주석 형식: `// [ASIS] {기능 설명} — {제외 이유}`








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
테이블은 기존 테이블 생성된것 으로 개발해야하고 테이블 레이아웃 수정시 반드시 확인절차를 거칠것  

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

| 패키지 | 역할 | 주요 경로 |
|--------|------|---------|
| `form.common` | 공통: 주문/폰/USIM 조회, 레거시 컨트롤러 | `/order/*`, `/m/product/*`, `/usim/*` |
| `form.newchange` | 신규가입·변경 신청서 처리 (AppformController 등) | `/appForm/*`, `/appform/*` |
| `form.ownerchange` | 명의변경 | `/mypage/myNameChg*` |
| `form.servicechange` | 서비스변경: 마이페이지·부가서비스·요금제·결합·데이터쉐어링 등 | `/api/v1/addition/*`, `/mypage/*` |
| `form.termination` | 서비스해지 상담 | `/mypage/cancelConsult*` |
| `system.common` | 시스템 공통: 코드/캐시/예외/상수/M플랫폼 어댑터/유틸 (`mplatform/`, `mspservice/`, `commCode/`, `cache/`, `constants/`, `exception/`, `util/`) | - |
| `system.cert` | 본인인증 | `/cert/*` |
| `system.faceauth` | 안면인증 | `/fath/*` |

각 도메인: `controller / dao / dto / service` 레이어 구성. 일부 도메인은 `mapper/`(MyBatis XML 전용) · `constant/` 추가 존재.

MyBatis 패턴: `XxxDaoImpl.java`에서 `SqlSession` 주입 방식 사용. `@Mapper` 인터페이스 방식은 미사용 — `@MapperScan`은 신규 Mapper 인터페이스 생성 시 대비용.

MyBatis XML 위치: `src/main/resources/mapper/formComm/`, `formOwnChg/`, `formSvcChg/`, `formSvcCncl/` (도메인 약칭 폴더명 사용).

의존성: `form.* → system.common.mplatform` / `form.ownerchange, form.termination → form.servicechange.dto/service` / 업무 패키지 간 직접 참조 금지

### HTTP 메서드 규칙

백엔드 API는 **GET / POST 두 가지만 사용**한다. PUT / PATCH / DELETE 사용 금지.

| 메서드 | 용도 |
|--------|------|
| `GET`  | 단순 조회 (파라미터가 없거나 path variable만 사용) |
| `POST` | 조회·저장·변경·삭제 등 나머지 모든 처리 |

### 네이밍 규칙

- Controller: `XXXController`(내부용) / `MsfXXXController`(서비스변경 영역)
- Service: `XxxSvc.java` / `XxxSvcImpl.java` 또는 `XxxService.java` / `XxxServiceImpl.java`
- DAO: `XxxDao.java` / `XxxDaoImpl.java`
- DTO: `XXXReqDto`(요청) / `XXXResVO` 또는 `XXXDto`(응답)
- Mapper XML: `insert/save/update/delete/select/selectList` + 서비스명칭

### M플랫폼 연동 코드 (MsfMplatFormService)

| 코드 | 기능 |
|------|------|
| Y04 | 휴대폰 인증 (contractValdChk) |
| X01 | 가입정보조회 (perMyktfInfo) |
| X18 | 잔여요금·위약금 조회 (서비스해지) |
| X19 | 요금상품 변경 |
| X20 | 가입중 부가서비스 조회 |
| X21/X38 | 부가서비스 신청/해지 |
| X26/X28/X30/X33/X35 | 분실복구·일시정지해제 |
| X32 | 번호변경 |
| X69/X70/X71 | 데이터쉐어링 조회/신청/해지 |
| X84 | 요금제 사전체크 |
| X85/UC0 | USIM 변경 |
| X87/Y44 | 아무나 SOLO 결합 |
| X88/X89/X90 | 요금상품 예약/예약조회/예약취소 |
| MC0 | 명의변경 사전체크 |
| MP0 | 명의변경 후처리 |
| NU1/NU2 | 변경 가능 번호 조회/번호 변경 신청 |

`application.properties`에서 `SERVER_NAME=LOCAL` 시 Mock 응답 반환.

---

## 프론트엔드 아키텍처 (msf-web)

### 라우팅

- `/mobile/:domain/:service` → `components/{컴포넌트폴더}/{service}.vue` 동적 로드
- `/mobile/complete/:domain` → 완료 화면 (`ServiceCompleteView.vue`)

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

- **모든 문서는 `.doc/` 폴더에서 관리** (소스코드 외 문서는 `.doc/` 외부에 생성 금지)
  - 개발 진행·기능 명세 `.md` 문서 → `.doc/` (루트: 15, 18, 30, 40번 등)
  - ASIS 참조 분석 문서 → `.doc/asis/` (14, 21, 22번 등)
  - 내부 인터페이스 설계서, 테이블정의서 등 → `.doc/reference/`
  - SQL 스크립트 (DDL 등) → `.doc/reference/script/`
  - UI 설계서 → `.doc/uidoc/`
  - 구 문서 아카이브 → `.doc/old/`
- M포탈 소스와 동일하게 개발; 연동 API 스펙(요청/응답 형식) 동일하게 구현
- 기능 목록 및 연동 스펙은 `.doc/` 내 문서 참조
- **상세 개발 가이드**: `.claude/skills/ktm-smartform/SKILL.md` (기술스택·패턴·주의사항)
- **ASIS→TOBE 변환 규칙**: `.claude/skills/asis-tobe-convert/SKILL.md` (패키지 매핑·Sf 네이밍·변환 절차)
- **TOBE 개발 규칙**: `.claude/skills/smart_dev_change/SKILL.md` (확정 패키지구조·임포트규칙·세션 누적 요구사항)
- **구현 현황**: `.claude/skills/ktm-smartform/dev-status.md`
- **컨텍스트 최적화**: `.claudeignore`로 레거시 정적 리소스·빌드 산출물 제외됨

---

## 개발 규칙 (반드시 체크)

### 1. 신규 @Mapper 인터페이스 생성 시 — @MapperScan 등록 필수

현재 프로젝트는 `XxxDaoImpl`에서 `SqlSession` 주입 방식을 사용하며 `@Mapper` 인터페이스는 없다.
신규로 `@Mapper` 인터페이스를 만들 경우 **반드시** `MsfApplication.java`의 `@MapperScan`에 패키지 추가.
누락 시 `NoSuchBeanDefinitionException`으로 서버 기동 실패.

```java
@MapperScan({
    "com.ktmmobile.msf.form.common.mapper",
    "com.ktmmobile.msf.form.newchange.mapper",
    "com.ktmmobile.msf.form.servicechange.mapper",
    "com.ktmmobile.msf.form.ownerchange.mapper",
    "com.ktmmobile.msf.form.termination.mapper"
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

### 4. ASIS → TOBE DB 테이블명 변환 규칙

ASIS Mapper SQL 포팅 시 테이블 출처에 따라 처리 방법이 다르다.

| ASIS 테이블 | 접두사 | TOBE 처리 |
|------------|--------|----------|
| M포탈 DB   | `MCP_*`, `NMCP_*` | `MSF_*` 로 접두사 교체 (MSF DB 동일 구조) |
| M플랫폼 DB | `MSP_*` | `MSP_*@DL_MSP` DB링크 그대로 사용 |

예시: `NMCP_CUST_REQUEST_MST` → `MSF_CUST_REQUEST_MST` / `MCP_REQUEST` → `MSF_REQUEST` / `MSP_INTM_INSR_MST` → `MSP_INTM_INSR_MST@DL_MSP`

전체 테이블 매핑 목록: `.doc/reference/테이블매핑_20260319.md` 참조

