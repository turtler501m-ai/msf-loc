const XLSX = require('xlsx');
const fs = require('fs');
const path = require('path');

const wb = XLSX.read(fs.readFileSync(path.join(__dirname, 'MMSP-AN-01-개발과제및기능목록_20260326_분석사항추가.xlsx')));
const ws = wb.Sheets[wb.SheetNames[0]];
const rows = XLSX.utils.sheet_to_json(ws, { header: 1, defval: '' });

// Fixed column indices based on Excel structure (rows 1-2 merged headers)
const CI = {
  gubun: 1,       // 구분
  depth1: 2,      // 1Depth
  depth2: 3,      // 2Depth
  depth3: 4,      // 3Depth
  screenNo: 5,    // 화면번호
  screenNm: 6,    // 화면명
  screenType: 7,  // 화면구분
  detail: 8,      // 상세 기능
  system: 9,      // 시스템
  dev: 10,        // 개발 (개발/삭제/보류)
  desc: 11,       // 기능설명
  interop: 12,    // 연동/연계
  table: 13,      // 테이블사용
  decision: 14,   // 의사결정
  itoNo: 15,      // ITO AS-IS 분석번호
  ref: 16,        // 참고자료
  devDir: 17,     // 개발방향 참고 및 참고소스
  migration: 18,  // 이행데이터
  reqId: 19,      // 요구사항ID
};

// Parse data rows (starting from row index 3)
const data = [];
for (let i = 3; i < rows.length; i++) {
  const r = rows[i];
  if (!r || r.every(c => !c && c !== 0)) continue;
  const obj = {};
  for (const [key, idx] of Object.entries(CI)) {
    const v = r[idx];
    obj[key] = v !== undefined && v !== null ? String(v).trim() : '';
  }
  if (!obj.gubun && !obj.detail && !obj.desc && !obj.dev) continue;
  obj._rowIdx = i + 1;
  data.push(obj);
}

// Statistics
const stats = { total: data.length, dev: 0, del: 0, hold: 0 };
data.forEach(r => {
  if (r.dev.includes('삭제')) stats.del++;
  else if (r.dev.includes('보류')) stats.hold++;
  else stats.dev++;
});

// Group by ITO analysis number
const itoGroups = {};
const noItoRows = [];
data.forEach((r) => {
  const ito = r.itoNo;
  if (!ito || ito === '/' || ito === '-' || ito === 'X' || ito === '신규' || ito === '') {
    noItoRows.push(r);
  } else {
    const parts = ito.split(/[\r\n,]+/).map(s => s.trim()).filter(Boolean);
    parts.forEach(num => {
      const clean = num.split(/\s*[-–]\s/)[0].trim();
      if (clean === '신규' || clean === '/' || clean === '') {
        noItoRows.push(r);
        return;
      }
      if (!itoGroups[clean]) itoGroups[clean] = [];
      itoGroups[clean].push(r);
    });
  }
});

// Sort ITO keys numerically
const itoKeys = Object.keys(itoGroups).sort((a, b) => {
  const matchA = a.match(/^(\d+)/);
  const matchB = b.match(/^(\d+)/);
  if (matchA && matchB) {
    const diff = parseInt(matchA[1]) - parseInt(matchB[1]);
    if (diff !== 0) return diff;
  }
  return a.localeCompare(b, 'ko');
});

const excludedItems = data.filter(r => r.dev.includes('삭제'));
const heldItems = data.filter(r => r.dev.includes('보류'));

// Helpers
function esc(s) { return s ? s.replace(/\|/g, '\\|').replace(/\r?\n/g, ' ') : ''; }
function nl(s) { return s ? s.replace(/\r\n/g, '\n').replace(/\r/g, '\n') : ''; }

// Extract short API label from 연동/연계 field
function shortApi(interop) {
  if (!interop || interop === '/') return '';
  // Extract API codes like X20, Y04, m플랫폼, etc.
  const codes = interop.match(/[XYxMm]\d+|m플랫폼|m전산|KOS/g);
  if (codes) return codes.join(', ');
  return interop.replace(/\r?\n/g, ', ').substring(0, 40);
}

// Extract brief action from 기능설명
function stepDesc(r) {
  const detail = r.detail || '';
  const desc = nl(r.desc);
  const api = r.interop && r.interop !== '/' ? shortApi(r.interop) : '';
  const tbl = r.table && r.table !== '/' ? r.table.replace(/\r?\n/g, ', ').substring(0, 40) : '';

  let line = detail;

  // Build → chain: API → table
  const chain = [];
  if (api) chain.push(api);
  if (tbl) chain.push(tbl);

  if (chain.length > 0) {
    line += ' → ' + chain.join(' → ');
  }
  return line;
}

// Build 처리순서 요약 for one ITO group (new format)
// Returns array of lines
function buildSummaryBlock(itoNo, items) {
  const devItems = items.filter(r => !r.dev.includes('삭제'));
  const lines = [];

  if (devItems.length === 0) {
    lines.push('ㅇ [' + itoNo + '] — 전체 제외 (삭제)');
    return lines;
  }

  // Title line: ㅇ [기능명 = 공통 상위 기능 or 첫 번째 항목] [분석번호]
  // Use 1Depth + 2Depth or first item's detail as function group name
  const firstItem = devItems[0];
  const groupName = (firstItem.depth2 || firstItem.depth1 || firstItem.detail || '').trim();
  const titleDetail = devItems.length === 1 ? firstItem.detail : groupName;

  // Get unique API info
  const apis = new Set();
  devItems.forEach(r => {
    if (r.interop && r.interop !== '/') {
      const a = shortApi(r.interop);
      if (a) apis.add(a);
    }
  });
  const apiSuffix = apis.size > 0 ? ' — ' + [...apis].join(', ') : '';

  lines.push('ㅇ ' + titleDetail + ' [' + itoNo + ']' + apiSuffix);

  devItems.forEach((r, i) => {
    const desc = stepDesc(r);
    lines.push(' ' + (i+1) + ') ' + desc);
  });

  return lines;
}

const out = [];
function w(s) { out.push(s); }

// ====== 문서 헤더 ======
w('# G1. 스마트서식지-DS-08-ITO소스분석');
w('');
w('> **원본파일:** MMSP-AN-01-개발과제및기능목록_20260326_분석사항추가.xlsx  ');
w('> **시트:** ' + wb.SheetNames[0] + '  ');
w('> **기준일:** 2026-03-27  ');
w('> **총 항목:** ' + data.length + '건 (ITO분석번호 그룹: ' + itoKeys.length + '개, 신규/미부여: ' + noItoRows.length + '건)');
w('');
w('---');
w('');

// ====== 컬럼 구성 ======
w('## 컬럼 구성');
w('');
w('| 컬럼 | 설명 |');
w('|------|------|');
w('| 구분 | 대분류 (서식지App, 서비스변경 등) |');
w('| 1Depth / 2Depth / 3Depth | 기능 계층 분류 |');
w('| 화면번호 | 화면 식별 번호 (S1xxxxxxxx) |');
w('| 화면명 | 화면 명칭 |');
w('| 화면구분 | Pg=페이지, P=팝업, L=레이어팝업, T=탭메뉴, Link=링크 |');
w('| 상세기능 | 기능 상세 설명 |');
w('| 시스템 | Web / Server / Batch |');
w('| 개발 | 개발/삭제/보류 상태 |');
w('| 기능설명 | 상세 처리 로직 및 소스 설명 |');
w('| 연동/연계 | M플랫폼 API 코드 (Y04, X01 등) |');
w('| 테이블사용 | 관련 DB 테이블 |');
w('| 의사결정 | 개발 방향 관련 결정사항 |');
w('| ITO AS-IS 분석번호 | ITO 소스 분석 참조 번호 |');
w('| 참고자료 | 화면캡처, 설계서 등 |');
w('| 개발방향 참고 및 참고소스 | TOBE 개발 방향, AS-IS 참고 소스 |');
w('| 이행데이터 | 데이터 이행 필요 여부 |');
w('| 요구사항ID | 요구사항 추적 ID |');
w('');
w('---');
w('');

// ====== 통계 ======
w('## 통계 요약');
w('');
w('| 구분 | 건수 |');
w('|------|------|');
w('| 전체 기능 항목 | ' + stats.total + '건 |');
w('| **개발 대상** | **' + stats.dev + '건** |');
w('| 삭제(제외) | ' + stats.del + '건 |');
w('| 보류 | ' + stats.hold + '건 |');
w('');

// ITO 번호별 통계 테이블
w('### ITO분석번호별 항목 수');
w('');
w('| 분석번호 | 항목수 | 개발 | 삭제 | 보류 |');
w('|---------|--------|------|------|------|');
itoKeys.forEach(k => {
  const items = itoGroups[k];
  let d=0, x=0, h=0;
  items.forEach(r => { if(r.dev.includes('삭제')) x++; else if(r.dev.includes('보류')) h++; else d++; });
  w('| ' + k + ' | ' + items.length + ' | ' + d + ' | ' + x + ' | ' + h + ' |');
});
w('| (신규/미부여) | ' + noItoRows.length + ' | - | - | - |');
w('');
w('---');
w('');

// ====== 제외 항목 요약 ======
w('## 제외 항목 요약 (삭제)');
w('');
w('| 순서 | 구분 | 1Depth | 상세기능 | ITO분석번호 | 의사결정/사유 |');
w('|------|------|--------|---------|------------|-------------|');
excludedItems.forEach((r, i) => {
  w('| ' + (i+1) + ' | ' + esc(r.gubun) + ' | ' + esc(r.depth1) + ' | ' + esc(r.detail) + ' | ' + esc(r.itoNo) + ' | ' + esc(r.decision) + ' |');
});
w('');

// ====== 보류 항목 요약 ======
if (heldItems.length > 0) {
  w('## 보류 항목 요약');
  w('');
  w('| 순서 | 구분 | 1Depth | 상세기능 | ITO분석번호 | 의사결정/사유 |');
  w('|------|------|--------|---------|------------|-------------|');
  heldItems.forEach((r, i) => {
    w('| ' + (i+1) + ' | ' + esc(r.gubun) + ' | ' + esc(r.depth1) + ' | ' + esc(r.detail) + ' | ' + esc(r.itoNo) + ' | ' + esc(r.decision) + ' |');
  });
  w('');
}
w('---');
w('');

// ====== 전체 항목 목록 ======
w('## 전체 항목 목록');
w('');
w('| 순서 | 구분 | 1Depth | 2Depth | 상세기능 | 화면구분 | 개발 | ITO번호 | 연동 | 테이블 | 요구사항ID |');
w('|------|------|--------|--------|---------|---------|------|---------|------|--------|-----------|');
data.forEach((r, i) => {
  const dm = r.dev.includes('삭제') ? '~~삭제~~' : r.dev.includes('보류') ? '**보류**' : r.dev;
  w('| ' + (i+1) + ' | ' + esc(r.gubun) + ' | ' + esc(r.depth1) + ' | ' + esc(r.depth2) + ' | ' + esc(r.detail) + ' | ' + esc(r.screenType) + ' | ' + dm + ' | ' + esc(r.itoNo) + ' | ' + esc(r.interop) + ' | ' + esc(r.table) + ' | ' + esc(r.reqId) + ' |');
});
w('');
w('---');
w('');

// ====== TOBE 처리순서 요약 (상세 분석 앞에 위치) ======
w('## TOBE 처리순서 요약 (ITO분석번호별)');
w('');
w('> 각 분석번호의 개발 항목을 처리 순서대로 기술합니다.  ');
w('> 형식: `ㅇ 기능명 [분석번호]`  →  `N) 상세기능 → 연동API → 테이블`');
w('');

itoKeys.forEach(itoNo => {
  const items = itoGroups[itoNo];
  const lines = buildSummaryBlock(itoNo, items);
  lines.forEach(l => w(l));
  w('');
});

// 신규 항목 요약
w('ㅇ [신규] — ITO분석번호 미부여 항목');
noItoRows.filter(r => !r.dev.includes('삭제')).forEach((r, i) => {
  w(' ' + (i+1) + ') ' + stepDesc(r));
});
w('');
w('---');
w('');

// ====== 상세 분석 내용 ======
w('## 상세 분석 내용 (ITO분석번호별)');
w('');

itoKeys.forEach(itoNo => {
  const items = itoGroups[itoNo];
  const allExcl = items.every(r => r.dev.includes('삭제'));

  w('## [' + itoNo + ']' + (allExcl ? ' — ~~제외~~' : ''));
  w('');

  items.forEach((r, si) => {
    const isExcl = r.dev.includes('삭제');
    const isHeld = r.dev.includes('보류');

    if (items.length > 1) {
      w('### (' + (si+1) + ') ' + (r.detail || r.screenNm || '-'));
      w('');
    }

    if (isExcl) {
      w('> **제외(삭제)** — ' + (r.decision || '불필요'));
      w('');
    } else if (isHeld) {
      w('> **보류** — ' + (r.decision || '의사결정 대기'));
      w('');
    }

    w('| 항목 | 내용 |');
    w('|------|------|');
    if (r.gubun) w('| 구분 | ' + esc(r.gubun) + ' |');
    if (r.depth1) w('| 1Depth | ' + esc(r.depth1) + ' |');
    if (r.depth2) w('| 2Depth | ' + esc(r.depth2) + ' |');
    if (r.depth3) w('| 3Depth | ' + esc(r.depth3) + ' |');
    if (r.screenNo) w('| 화면번호 | ' + esc(r.screenNo) + ' |');
    if (r.screenNm) w('| 화면명 | ' + esc(r.screenNm) + ' |');
    if (r.screenType) w('| 화면구분 | ' + esc(r.screenType) + ' |');
    w('| 상세기능 | ' + esc(r.detail) + ' |');
    if (r.system) w('| 시스템 | ' + esc(r.system) + ' |');
    w('| 개발상태 | ' + esc(r.dev) + ' |');
    if (r.interop && r.interop !== '/') w('| 연동/연계 | ' + esc(r.interop) + ' |');
    if (r.table && r.table !== '/') w('| 테이블 | ' + esc(r.table) + ' |');
    if (r.migration && r.migration !== '/') w('| 이행데이터 | ' + esc(r.migration) + ' |');
    if (r.reqId) w('| 요구사항ID | ' + esc(r.reqId) + ' |');
    w('');

    const descText = nl(r.desc);
    if (descText && descText !== '/') {
      w('**기능설명 / 처리 로직:**');
      w('');
      w('```');
      w(descText);
      w('```');
      w('');
    }

    const interopText = nl(r.interop);
    if (interopText && interopText !== '/') {
      w('**연동/연계 API:**');
      w('');
      w('```');
      w(interopText);
      w('```');
      w('');
    }

    const tableText = nl(r.table);
    if (tableText && tableText !== '/') {
      w('**테이블/API:**');
      w('');
      w('```');
      w(tableText);
      w('```');
      w('');
    }

    const devDirText = nl(r.devDir);
    if (devDirText && devDirText !== '/') {
      w('**처리 로직 / 변환 처리 (개발방향):**');
      w('');
      w('```');
      w(devDirText);
      w('```');
      w('');
    }

    const decText = nl(r.decision);
    if (decText && decText !== '/') {
      w('**의사결정:**');
      w('');
      w('```');
      w(decText);
      w('```');
      w('');
    }

    const refText = nl(r.ref);
    if (refText && refText !== '/') {
      w('**참고자료:**');
      w('');
      w(refText);
      w('');
    }

    w('---');
    w('');
  });
});

// 신규 항목 상세
w('## [신규] — ITO분석번호 미부여 항목');
w('');
noItoRows.forEach((r, i) => {
  w('### (' + (i+1) + ') ' + (r.detail || r.screenNm || '-'));
  w('');

  if (r.dev.includes('삭제')) {
    w('> **제외(삭제)** — ' + (r.decision || '불필요'));
    w('');
  } else if (r.dev.includes('보류')) {
    w('> **보류** — ' + (r.decision || '의사결정 대기'));
    w('');
  }

  w('| 항목 | 내용 |');
  w('|------|------|');
  if (r.gubun) w('| 구분 | ' + esc(r.gubun) + ' |');
  if (r.depth1) w('| 1Depth | ' + esc(r.depth1) + ' |');
  if (r.depth2) w('| 2Depth | ' + esc(r.depth2) + ' |');
  if (r.depth3) w('| 3Depth | ' + esc(r.depth3) + ' |');
  if (r.screenNo) w('| 화면번호 | ' + esc(r.screenNo) + ' |');
  if (r.screenNm) w('| 화면명 | ' + esc(r.screenNm) + ' |');
  if (r.screenType) w('| 화면구분 | ' + esc(r.screenType) + ' |');
  w('| 상세기능 | ' + esc(r.detail) + ' |');
  if (r.system) w('| 시스템 | ' + esc(r.system) + ' |');
  w('| 개발상태 | ' + esc(r.dev) + ' |');
  if (r.interop && r.interop !== '/') w('| 연동/연계 | ' + esc(r.interop) + ' |');
  if (r.table && r.table !== '/') w('| 테이블 | ' + esc(r.table) + ' |');
  if (r.migration && r.migration !== '/') w('| 이행데이터 | ' + esc(r.migration) + ' |');
  if (r.reqId) w('| 요구사항ID | ' + esc(r.reqId) + ' |');
  w('');

  const descText = nl(r.desc);
  if (descText && descText !== '/') {
    w('**기능설명 / 처리 로직:**');
    w('');
    w('```');
    w(descText);
    w('```');
    w('');
  }
  const interopText = nl(r.interop);
  if (interopText && interopText !== '/') {
    w('**연동/연계 API:**');
    w('');
    w('```');
    w(interopText);
    w('```');
    w('');
  }
  const tableText = nl(r.table);
  if (tableText && tableText !== '/') {
    w('**테이블/API:**');
    w('');
    w('```');
    w(tableText);
    w('```');
    w('');
  }
  const devDirText = nl(r.devDir);
  if (devDirText && devDirText !== '/') {
    w('**처리 로직 / 변환 처리 (개발방향):**');
    w('');
    w('```');
    w(devDirText);
    w('```');
    w('');
  }
  const decText = nl(r.decision);
  if (decText && decText !== '/') {
    w('**의사결정:**');
    w('');
    w('```');
    w(decText);
    w('```');
    w('');
  }
  const refText = nl(r.ref);
  if (refText && refText !== '/') {
    w('**참고자료:**');
    w('');
    w(refText);
    w('');
  }

  w('---');
  w('');
});

const content = out.join('\n');
const outPath = path.join(__dirname, 'G1.스마트서식지-DS-08-ITO소스분석.md');
fs.writeFileSync(outPath, content, 'utf-8');
console.log('Done! Written ' + content.length + ' chars, ' + out.length + ' lines');
