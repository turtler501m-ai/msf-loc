/**
 * 개발기능목록_담당_TOBE분석.md 신규 생성
 * - ASIS와 동일한 구조(순번~기능 설명 및 개발 사항 | 화면 | Controller | Service | Dao | Mapper | 연동)
 * - 앞 8컬럼: ASIS에서 복사
 * - 화면~연동 6컬럼: mform(TOBE) 기준으로 작성 (개발기능목록_담당.md '관련 M포털 소스'에서 mform-web 추출, mform-api Controller/Service, 연동은 ASIS와 동일)
 * - 셀 내 줄바꿈: <br>
 */
const fs = require('fs');
const path = require('path');
const asisPath = path.join(__dirname, '개발기능목록_담당_ASIS분석.md');
const giganPath = path.join(__dirname, '개발기능목록_담당.md');
const outPath = path.join(__dirname, '개발기능목록_담당_TOBE분석.md');

const asisLines = fs.readFileSync(asisPath, 'utf8').split(/\r?\n/);
const giganLines = fs.readFileSync(giganPath, 'utf8').split(/\r?\n/);

const MFORM_CONTROLLER = 'mform-api/.../MformV1Controller.java';
const MFORM_SERVICE = 'mform-api/.../MplatFormService.java';

function parseRow(line) {
  const parts = [];
  let cur = '';
  for (let i = 0; i < line.length; i++) {
    const c = line[i];
    if (c === '|') {
      parts.push(cur);
      cur = '';
    } else {
      cur += c;
    }
  }
  if (cur !== '') parts.push(cur);
  return parts;
}

/** 개발기능목록_담당.md 한 행에서 '관련 M포털 소스' 컬럼 추출 (마지막 컬럼, 셀 안에 | 가 있을 수 있음) */
function getRelatedSource(parts) {
  // 헤더 컬럼 수 20개 -> 인덱스 1~19. 마지막 컬럼이 19번이므로 19부터 끝까지 합침
  if (parts.length <= 19) return '';
  return parts.slice(19).join('|').trim();
}

/** '관련 M포털 소스' 문자열에서 mform-web 경로만 추출, 여러 개면 <br>로 연결 */
function extractMformWeb(sourceStr) {
  if (!sourceStr || !sourceStr.trim()) return '-';
  const segments = sourceStr.split(/\s*\|\s*|\s*;\s*/).map(s => s.trim()).filter(Boolean);
  const mformPaths = segments.filter(s => s.includes('mform-web'));
  if (mformPaths.length === 0) return '-';
  return mformPaths.join('<br>');
}

const out = [asisLines[0]]; // header
// 개발기능목록_담당.md 데이터 행: 9번째 줄(인덱스 8)부터
let giganDataStart = -1;
for (let k = 0; k < giganLines.length; k++) {
  const line = giganLines[k];
  if (/^\|서식지App\|/.test(line)) {
    giganDataStart = k;
    break;
  }
}
if (giganDataStart < 0) giganDataStart = 8;

for (let i = 1; i < asisLines.length; i++) {
  const asisRow = parseRow(asisLines[i]);
  if (asisRow.length < 9) {
    out.push(asisLines[i]);
    continue;
  }
  const base = asisRow[1] === '' && asisRow[2] !== '' ? asisRow.slice(2, 10) : asisRow.slice(1, 9);
  const prefix = base.length >= 8 ? base : asisRow.slice(1, 9);
  const asisSuffix = asisRow.slice(9);
  const asisYeondong = (asisSuffix.length >= 6) ? (asisSuffix[5] || '-') : '-';

  const giganIdx = giganDataStart + (i - 1);
  let 화면 = '-';
  if (giganIdx < giganLines.length) {
    const giganParts = parseRow(giganLines[giganIdx]);
    const relatedSource = getRelatedSource(giganParts);
    화면 = extractMformWeb(relatedSource);
  }

  const suffix = [
    화면,
    MFORM_CONTROLLER,
    MFORM_SERVICE,
    '-',
    '-',
    asisYeondong
  ];
  out.push('|' + prefix.join('|') + '|' + suffix.join('|') + '|');
}

fs.writeFileSync(outPath, out.join('\n'), 'utf8');
console.log('Done. Wrote', outPath);
