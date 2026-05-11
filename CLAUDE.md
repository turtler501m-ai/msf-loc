# CLAUDE.md

**MSF (스마트서식지)** — 대리점용 모바일 고객 서비스 신청 시스템. M포탈(MCP)을 대체하는 신규 개발 시스템.

> 실행 옵션: `--dangerously-skip-permissions`

## 폴더 구조

| 폴더 | 역할 |
|------|------|
| `mcp/` | ASIS M포탈 (레거시, ASIS 참조용 — mcp-portal-was Java+Mapper만) |
| `msp/`, `msc-prx/`, `msp-batch-daemon/` | M플랫폼 레거시 (참조용) |
| `msf-be-form-api/` | **TOBE 백엔드** (Java 25, Spring Boot 4.0, Gradle 멀티모듈, MyBatis) |
| `msf-form-web/` | **TOBE 프론트엔드** (Vue 3, Vite, SCSS, port 7080, API → localhost:8080) |

## 핵심 개발 원칙

1. **기존 파일에 추가** — 신규 파일 생성 전 반드시 사용자에게 확인 요청
2. **ASIS 삭제 금지** — 주석: `// [ASIS] {기능 설명} — {제외 이유}`
3. **HTTP POST만** — PUT/PATCH/DELETE 금지
4. **URL 패턴**: `/list` `/get` `/register` `/modify` `/remove`
5. **@Mapper 신규 시** — `FormApiApplication.java`의 `@MapperScan`에 패키지 추가 필수 (누락 시 서버 기동 실패)
6. **Windows API 테스트** — curl 대신 Node.js `http.request()` 사용 (한글 UTF-8 인코딩 문제)
7. **커밋 금지** — 명시적 요청 시에만 commit/push 실행

## 상세 참조 (필요 시 Read 도구로 읽을 것)

| 목적 | 경로 |
|------|------|
| 기술스택·패턴·주의사항 전체 | `.claude/skills/ktm-smartform/SKILL.md` |
| ASIS→TOBE 변환 규칙 | `.claude/skills/asis-tobe-convert/SKILL.md` |
| TOBE 패키지구조·개발규칙 | `.claude/skills/smart_dev_change/SKILL.md` |
| 백엔드 빌드·DB접속정보 | `msf-be-form-api/CLAUDE.md` |
| 개발 진행 현황 | `.claude/skills/ktm-smartform/dev-status.md` |
| 전체 프로젝트 구조 | `.doc/11.MSF_프로젝트_전체구조.md` |
| ASIS 서비스변경 분석 | `.doc/asis/Z11.DS-08-ITO소스분析_서비스변경.md` |

## MSF-LOC → MSF-GIT 머지 주의

PowerShell `>`·`Set-Content`·`[IO.File]::WriteAllText()` 기본 인코딩으로 Java/Vue/JS 파일 쓰지 말 것.
UTF-16LE(FF FE) 저장 → `unmappable character (0xFF)` 컴파일 오류 발생 이력 있음.

- 텍스트 직접 쓸 때: `[IO.File]::WriteAllText($path, $text, [Text.UTF8Encoding]::new($false))`
- 머지 후 확인: BOM검사 + `rg "^(<<<<<<<|>>>>>>>)"` 충돌마커 + `.\gradlew.bat :domains:form:compileJava`

## 빠른 실행

```bash
# 백엔드 (port 8080)
cd msf-be-form-api && ./gradlew :app-boot:bootRun

# 프론트엔드 (port 7080)
cd msf-form-web && npm run dev

# 서버 재시작 시 java 프로세스 먼저 종료
taskkill //F //IM java.exe
```

## DEV DB

Smart Form DEV DB:

```text
jdbc:postgresql://211.184.227.24:45432/msf_core
Username: smartform_dev
Password: dev!!12form
```

MSP DB:

```text
jdbc:oracle:thin:@10.220.71.231:2521:MSPDEV
Username: MSP_WAS
Password: ktmm0601!!
```
