---
name: ito-analysis
description: DS-08 ITO 소스 분석 엑셀(.xlsx)을 분석번호 단위로 마크다운 문서로 변환. "분석 제외" 항목 자동 제거, 계층구조(URL→Controller→Service→DAO→테이블) 표현, 주요내용/처리순서 섹션 자동 생성.
trigger: 사용자가 "ITO 소스분석 엑셀", "DS-08", "분석번호 NNNN 정리", "ITO 소스 분석 문서 만들어줘" 등을 언급할 때 자동 참조.
---

# ITO 소스 분석 문서화 스킬 가이드

## 0. 자동 처리 원칙 ★

분析번호를 받으면 **중간에 확인·선택·질문 없이 끝까지 실행**한다.

- 엑셀 파일이 여러 개면 → 가장 최신 파일 자동 선택
- 출력 파일명 Z0N 번호 → `.doc/asis/` 기존 파일 목록 보고 자동 부여
- 분析 제外 항목 → 자동 제거, 빈 섹션도 자동 정리
- 주요내용/처리순서/처리요약 → 추출 결과 바탕으로 자동 작성

**실행 완료 후 결과(파일 경로·섹션 수)만 보고**한다.

---



## 1. 목적

`스마트서식지-DS-08-ITO소스분석_vX.X_YYYYMMDD.xlsx`의 **AS-IS 소스 분석** 시트를
분석번호(예: `8001`, `8002`) 단위로 읽어 마크다운 문서로 변환한다.

출력 파일 위치: `.doc/asis/Z0N.DS-08-ITO소스분석_{분석번호}.md`

---

## 2. 엑셀 시트 구조

| 열(col) | 헤더 | 설명 |
|---------|------|------|
| B (1) | 분석번호 | `8001-01`, `8001-02` 등. 비어 있으면 이전 번호 유지 (병합셀) |
| C (2) | JSP/JS | 화면 파일 경로. 분석번호 행에만 표시 |
| D (3) | URL | 요청 URL. 비어있으면 이전 URL 유지 (계층 구조) |
| E (4) | Controller | Controller.java 메서드명. 비어있으면 이전 Controller 유지 |
| F (5) | Service | Service 메서드명 |
| G (6) | DAO/SQL | Mapper 메서드명 + XML 파일명 |
| H (7) | 테이블/API | FROM/UPDATE/INSERT 대상 테이블 또는 M플랫폼 코드 |
| I (8) | TO-BE 처리 로직 | 스마트서식지 TO-BE 영향도 분석 |

### 계층 관계

```
분석번호 (## 제목)
  └─ JSP/JS
       └─ URL (### 제목)
            └─ Controller (#### 제목)
                 └─ Service (- 항목)
                      └─ DAO/SQL + 테이블/API + TO-BE (들여쓰기 항목)
```

---

## 3. 필터링 규칙

### 제거 대상 (분석 제외)

TO-BE 열에 **`분석 제외`** 텍스트가 포함된 행은 문서에서 완전히 제거한다.

```
"분석 제외 - M전산 자체 로직"  →  제거
"분석 제외"                    →  제거
```

### 유지 대상

| TO-BE 내용 | 처리 |
|------------|------|
| `o 스마트신청서App 데이터 -> M포탈 ... insert 처리` | **유지** — 핵심 연동 내용 |
| `o M포탈 ... 데이터 -> 스마트서식지App 테이블로 현행화` | **유지** — TO-BE 작업 필요 |
| `영향도 없음 - M전산 자체 테이블` | **유지** — 영향도 명시 |
| 빈값 (DAO/테이블만 있는 행) | **유지** — 테이블 정보 보존 |

### 빈 섹션 제거

- 모든 Service/DAO 항목이 제거된 Controller → Controller 섹션도 제거
- 모든 Controller가 제거된 URL → URL 섹션도 제거

---

## 4. 출력 문서 구조

```markdown
# 스마트서식지 DS-08 ITO 소스 분석 — 분석번호 {NNNN}

> 원본: `파일명.xlsx` · AS-IS 소스 분석 시트
> 전달일: MM/DD  |  분석 제외(M전산 자체 로직) 항목 제거됨

---

## {NNNN}-01

**JSP/JS**: `경로`

### 주요내용
(요약표 + 핵심 테이블 목록)

### 처리순서
(code block — URL별 DAO/테이블 흐름)

---

### `URL 경로`

#### Controller: `클래스명 - 메서드명 | 설명`

- **Service**: `서비스메서드`
  - **DAO/SQL**: `매퍼메서드` / `XML파일`
  - **테이블/API**: `테이블명@DB링크`
  - **TO-BE**: 영향도 내용
```

---

## 5. Node.js 변환 스크립트 패턴

엑셀 변환은 아래 패턴을 사용한다. (`xlsx` 패키지 사전 설치 필요)

```javascript
const XLSX = require('xlsx');
const fs = require('fs');

const wb = XLSX.readFile('엑셀파일경로.xlsx');
const ws = wb.Sheets['AS-IS 소스 분석'];
const range = XLSX.utils.decode_range(ws['!ref']);

// 분석번호 범위 탐색
let startRow = -1, endRow = -1;
for (let r = 3; r <= range.e.r; r++) {
  const cell = ws[XLSX.utils.encode_cell({r, c: 1})];
  const val = cell ? String(cell.v) : '';
  if (val.startsWith(TARGET_NUM)) { if (startRow < 0) startRow = r; }
  else if (startRow >= 0 && val && !val.startsWith(TARGET_NUM)) { endRow = r - 1; break; }
}
if (endRow < 0) endRow = range.e.r;

// 행 추출 및 필터링
function isExcluded(tobe) { return tobe && tobe.includes('분석 제외'); }

// 계층 구조로 파싱 후 Markdown 생성
// → sections[] > urls[] > controllers[] > services[] > entries[]
```

---

## 6. 주요내용/처리순서 작성 기준

각 분석번호 섹션 상단에 작성한다.

### 주요내용 포함 항목

1. **한 줄 요약** — 화면의 핵심 기능 설명
2. **요약표** — Controller / Mapper XML / 핵심 테이블 / TO-BE 영향도
3. **영향도 분류**:
   - `영향도 없음` — M전산 자체 테이블만 조회
   - `현행화 처리 필요` — MSF ↔ M포탈 테이블 동기화 필요
   - `기준 미확정` — 처리 일자 기준 등 미결 사항 있음

### 처리순서 포함 항목

- code block (```) 안에 번호 매긴 단계로 작성
- 각 단계: `URL` → `Mapper메서드` → `테이블명  DML종류`
- 영향도 없는 항목도 포함하되 `(영향도 없음)` 표시

### 처리요약 포함 항목

처리순서 코드블록 바로 다음에 위치. **1~2줄 분량**으로 각 번호당 핵심 테이블과 DML 흐름만 압축 기술.

- 형식: `1) 주어(URL/기능) + 핵심 테이블명 + DML 종류 (SELECT/INSERT/UPDATE) + 특이사항 1줄`
- 분析번호 내 URL이 많을 경우 URL별이 아닌 **기능 묶음 단위**로 번호를 매긴다
- `영향도 없음` 항목은 "스마트서식지 TO-BE 작업 없음"으로 명시
- 예시:
  ```
  1) 목록 조회를 `테이블A` + `테이블B`에서 조회하며, ~한다.
  2) 상태 처리는 테이블C INSERT → 테이블D UPDATE 순으로 진행한다.
  ```

---

### 관련테이블 포함 항목

처리요약 코드블록 바로 다음에 위치.

- 섹션명: `### 관련테이블`
- 열 구성: `테이블명 (TOBE)` | `ASIS 원본` | `DML` | `용도`
- **TOBE 테이블명 변환 규칙** (`.doc/reference/D2.테이블매핑_20260319.md` 기준):
  - `NMCP_*` / `MCP_*` → `MSF_*` (접두사 교체)
  - `MSP_*` (M플랫폼) → `MSP_*@DL_MSP` (DB링크 추가, 읽기전용 명시)
  - `EMV_SCAN_FILE@DL_MCP` → `MSF_EMV_SCAN_FILE` 등 동일 패턴 적용
- 영향도 없음 항목도 포함하되 용도란에 `(영향도 없음)` 표시
- 분析번호 전체 URL에 걸쳐 사용된 테이블을 합산하여 중복 제거 후 기술

---


## 7. 실행 절차

사용자가 특정 분석번호 문서화를 요청하면:

1. `.doc/asis/` 폴더의 xlsx 파일 경로 확인
2. Node.js 스크립트로 해당 분석번호 행 범위 추출
3. `분석 제외` 항목 필터링 후 계층 구조 파싱
4. Markdown 뼈대 생성 (주요내용/처리순서는 `_(위치)_` placeholder)
5. 추출된 URL/Controller/Service 목록을 분석하여 주요내용·처리순서 작성
6. `.doc/asis/Z0N.DS-08-ITO소스분석_{분석번호}.md` 저장

파일 번호(Z0N)는 기존 `.doc/asis/` 파일 목록 확인 후 다음 번호 사용.

---

## 8. 관련 파일

| 파일 | 설명 |
|------|------|
| `.doc/asis/스마트서식지-DS-08-ITO소스분석_v*.xlsx` | 원본 엑셀 |
| `.doc/asis/Z05.DS-08-ITO소스분석_전체.md` | 8001 변환 결과 (참고용) |
| `.doc/reference/D2.테이블매핑_20260319.md` | ASIS↔TOBE 테이블명 변환 기준 |
