# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

> 전체 구조 상세: `.doc/11.MSF_프로젝트_전체구조.md`
> 구현 현황 상세: `.doc/51.MSF_개발진행사항_현행화.md`
> 실행 시 옵션: `--dangerously-skip-permissions`

## 프로젝트 개요

**MSF (스마트서식지)** — 대리점용 모바일 고객 서비스 신청 시스템.
기존 M포탈(MCP)과 동일 기능을 신규 개발한 대체 시스템. M플랫폼/KT(KOS) 연동 스펙 동일하게 구현.

| 폴더 | 역할 |
|------|------|
| `mcp/` | 기존 M포탈 (레거시, ASIS 참조용) |
| `msp/` | M플랫폼 연계처리 (레거시, 참조용) |
| `msf-be-form-api/` | **현행 TOBE 백엔드** (Java 11, Spring Boot 4.0, Gradle 멀티모듈, MyBatis, PostgreSQL) |
| `msf-form-web/` | **현행 TOBE 프론트엔드** (Vue 3, Vite, SCSS, port 7080, API → localhost:8080) |
| `msf/msf-api/` | 구 백엔드 프로토타입 (Spring Boot 2.7, Maven, 참조용) |
| `msf/msf-web/` | 구 프론트엔드 프로토타입 (Vue 3, Vite, Tailwind CSS 4, 참조용) |
| `msf-pre/` | msf-api 초기 프로토타입 (구 패키지 구조: formComm/formSvcChg/formOwnChg/formSvcCncl, 참조용) |



## 개발 원칙

- **기존 파일에 추가**하는 방식으로 개발. 신규 파일 생성 시 반드시 사용자에게 확인 요청.
- ASIS 로직은 삭제 금지 — 반드시 주석 처리: `// [ASIS] {기능 설명} — {제외 이유}`
- ASIS 주요 참조 문서: `.doc/asis/Z01.MCP_폴더구조_분석.md`, `.doc/asis/Z11.DS-08-ITO소스분析_서비스변경.md`

## 기본 URL 액션 경로

| 동작 | 경로 접미사 |
|------|------------|
| 목록 조회 | `/list` |
| 상세 조회 | `/get` |
| 등록 | `/register` |
| 수정 | `/modify` |
| 삭제 | `/remove` |

## 도메인 레이어 구조 (각 업무 도메인 공통)

```
├── controller        # 컨트롤러 클래스
├── dto               # DTO
├── repository        # 리포지토리 인터페이스
│   ├── msp           #   M포탈 Oracle 데이터소스 (XxxMapper)
│   └── smartform     #   스마트서식지 PostgreSQL 데이터소스 (XxxMapper)
└── service           # 서비스 클래스
```



## 중요 참조문서로 설계 개발시에 반드시 참조할것


.doc/
├── 11.MSF_프로젝트_전체구조.md
├── 12.MSF_백엔드_패키지구조_변경계획.md
├── 32.데이터쉐어링_TOBE_처리순서_참고.md
├── 33.부가서비스신청해지_TOBE_처리순서_참고.md
├── 41.부가서비스_TOBE_변환계획_참고.md
├── 51.MSF_개발진행사항_현행화.md
├── 52_Service_Change_Interface_Analysis.md

├── asis/                                                   # ASIS 분석 문서
│   ├── Z01.MCP_폴더구조_분석.md
│   ├── Z02.AppformController_mcp-api_vs_mcp-portal-was_분석.md
│   ├── Z03.reqInsr_안심보험가입_ASIS_전체분析.md
│   ├── Z04.데이터쉐어링_ASIS_전체분析.md
│   ├── Z05.DS-08-ITO소스분析_전체.md
│   ├── Z10.스마트서식지-DS-09-서비스해지처리_연동설계_v1.0_20260325.md
│   └── Z11.DS-08-ITO소스분析_서비스변경.md

├── reference/                                              # 연동 설계 문서
│   ├── A1.표준용어사전_20211130_v0.9.md
│   ├── B1.MMSP-DS-04-코드_정의서_전체_20260323.md
│   ├── C1.MMSP-DS-06-인터페이스_설계서_20260325.md          # 외부 연동 인터페이스
│   ├── C2.스마트서식지-DS-06-인터페이스_설계서_내부API_v1_0.md # 내부 API 설계서
│   ├── C3.연동규격서_MVNO-OSST-API_v4_48.md                # 데이터쉐어링 OSST 연동
│   ├── D1.스마트서식지-DS-05-테이블정의서_V1.0_20260318.md
│   ├── D2.테이블매핑_20260319.md
│   ├── D3.MCP_REQUEST_MSF_REQUEST_컬럼매핑.md
│   ├── E1.스마트서식지-DS-08-ITO소스분析_v1_0_20260324.md
│   ├── G1.스마트서식지-DS-08-ITO소스분析.md
│   └── script/                                             # DDL 스크립트
├── uidoc/                                                  # UI 설계 문서
│   ├── MMSP-DS-02-명의변경_신청서_UI설계서.md
│   ├── MMSP-DS-02-서비스변경_신청서_UI설계서.md
│   ├── MMSP-DS-02-서비스해지_신청서_UI설계서.md
│   └── MMSP-DS-02-서비스해지_신청서_UI설계서_20260324.md
├── .backup/                                                # PDF/PPTX 원본 파일 및 스크립트 백업
└── old/                                                    # 구버전 분석 문서 아카이브 (60개+)


## TOBE 개발시 반드시 참고

- 접두사 `Msf` 컨트롤러·서비스명 변경 (현재 완료)
- TOBE 무관 ASIS 로직은 **삭제 금지**, 반드시 주석 처리
- 주석 형식: `// [ASIS] {기능 설명} — {제외 이유}`








## 실행 명령어

### Backend

```bash
cd msf-be-form-api

# 빌드
./gradlew build -x test

# 실행 (port 8080)
./gradlew :app-boot:bootRun
# 또는
java -jar app-boot/build/libs/app-boot-1.0.0.jar
```

DB: `localhost:5432/msf` (user: postgres / pw: postgres 또는 `application-private.yaml`에서 설정)
테이블은 기존 테이블 생성된 것으로 개발. 테이블 레이아웃 수정 시 반드시 확인절차를 거칠 것.

### Frontend

```bash
cd msf-form-web
npm install
npm run dev      # port 7080, VITE_MSF_API_URL → localhost:8080
npm run build
npm run lint     # oxlint + eslint (--fix)
npm run format   # prettier
```

### 테스트

```bash
cd msf-be-form-api
./gradlew test                     # 전체 테스트
./gradlew :commons:common:test     # 모듈별 테스트
```

---

## 백엔드 아키텍처 (msf-be-form-api — 현행 개발 대상)

### Gradle 멀티모듈 구조

```
msf-be-form-api/
├── app-boot/           # Spring Boot 진입점 (FormApiApplication.java, port 8080)
├── commons/
│   ├── common/         # 공통 라이브러리 (com.ktmmobile.msf.commons.common)
│   └── auditing/       # 감사 로그
├── domains/
│   └── form/           # 핵심 업무 도메인 (com.ktmmobile.msf.domains.form)
└── external/
    ├── mybatis/        # MyBatis 설정
    └── websecurity/    # 웹 보안
```

### 도메인 패키지 (`com.ktmmobile.msf.domains.form`)

| 패키지 | 역할 |
|--------|------|
| `common/` | M플랫폼 어댑터, MSP서비스, 공통코드, 캐시, 예외, 유틸, 레거시 DTO |
| `form/common/` | 폼 공통 컨트롤러·서비스·DAO |
| `form/newchange/` | 신규가입·변경 신청서 |
| `form/ownerchange/` | 명의변경 |
| `form/servicechange/` | 서비스변경 (부가서비스·요금제·결합·데이터쉐어링) |
| `form/termination/` | 서비스해지 |
| `form/appform_d/` | 헥사고날 아키텍처 패턴 (adapter/application/domain) |

각 업무 도메인 레이어: `controller / dao / dto / service / mapper`

MyBatis: `XxxDaoImpl.java`에서 `SqlSession` 주입 방식 사용.

### M플랫폼 연동 코드

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

### HTTP 메서드 규칙

백엔드 API는  POST 만 사용**한다. PUT / PATCH / DELETE 사용 금지.

| 메서드 | 용도 |
|--------|------|
| `POST` | 조회·저장·변경·삭제 등 나머지 모든 처리 |

---

## 프론트엔드 아키텍처 (msf-form-web — 현행 개발 대상)

### 컴포넌트 구조 (`src/components/`)

| 폴더 | 역할 |
|------|------|
| `form/common/` | 공통 컴포넌트 |
| `form/newchange/` | 신규가입·변경 신청서 |
| `form/ownerchange/` | 명의변경 신청서 |
| `form/servicechange/` | 서비스변경 |
| `form/termination/` | 서비스해지 |
| `extra/` | 영수증·간편신청·임시저장·모바일앱 등 부가 화면 |
| `layouts/` | 레이아웃 컴포넌트 |

### 상태관리 (`src/stores/`)

| 스토어 | 역할 |
|--------|------|
| `msf_menu` | 메뉴/네비게이션 |
| `msf_step` | 스텝 진행 관리 |
| `msf_newchange` | 신규가입·변경 폼 데이터 |
| `msf_termination` | 서비스해지 폼 데이터 |
| `msf_user` | 사용자 세션 |

### 기술 스택

JavaScript (TypeScript 미사용) / Vue 3 / Vue Router / Pinia / Vite / SCSS / unplugin-vue-components (자동 임포트)

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
cd msf-be-form-api && ./gradlew :app-boot:bootRun
# 또는
java -jar app-boot/build/libs/app-boot-1.0.0.jar
```

### 4. DEV DB 직접 조회 — Node.js pg 사용

DEV PostgreSQL에 직접 쿼리할 때 사용 (`npm install pg` 최초 1회 필요).

```
Host:     211.184.227.24
Port:     45432
Database: msf_core
Schema:   smartform
User:     smartform_dev
Password: dev!!12form
```

```js
const { Client } = require('pg');
const client = new Client({
  host: '211.184.227.24', port: 45432, database: 'msf_core',
  user: 'smartform_dev', password: 'dev!!12form', ssl: false
});
client.connect()
  .then(() => client.query(`SELECT * FROM smartform.msf_request_cancel LIMIT 5`))
  .then(res => { console.log(JSON.stringify(res.rows, null, 2)); return client.end(); })
  .catch(err => { console.error('ERR:', err.message); process.exit(1); });
```

PowerShell에서 실행 시 스크립트를 파일로 저장 후 실행:
```powershell
$script | Out-File -Encoding utf8 "$env:TEMP\dbquery.js"
node "$env:TEMP\dbquery.js"
```

### 5. ASIS → TOBE DB 테이블명 변환 규칙

ASIS Mapper SQL 포팅 시 테이블 출처에 따라 처리 방법이 다르다.

| ASIS 테이블 | 접두사 | TOBE 처리 |
|------------|--------|----------|
| M포탈 DB   | `MCP_*`, `NMCP_*` | `MSF_*` 로 접두사 교체 (MSF DB 동일 구조) |
| M플랫폼 DB | `MSP_*` | `MSP_*@DL_MSP` DB링크 그대로 사용 |

예시: `NMCP_CUST_REQUEST_MST` → `MSF_CUST_REQUEST_MST` / `MCP_REQUEST` → `MSF_REQUEST` / `MSP_INTM_INSR_MST` → `MSP_INTM_INSR_MST@DL_MSP`

전체 테이블 매핑 목록: `.doc/reference/테이블매핑_20260319.md` 참조

