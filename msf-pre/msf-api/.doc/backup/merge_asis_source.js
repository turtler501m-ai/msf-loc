/**
 * 개발기능목록_담당_ASIS분석.md 에 화면~연동 컬럼을 개발기능목록_담당_분석.md 기준으로 채우고, 줄바꿈은 <br> 로 처리
 */
const fs = require('fs');
const path = require('path');
const asisPath = path.join(__dirname, '개발기능목록_담당_ASIS분석.md');
const refPath = path.join(__dirname, '개발기능목록_담당_분석.md');

const asisLines = fs.readFileSync(asisPath, 'utf8').split(/\r?\n/);
const refLines = fs.readFileSync(refPath, 'utf8').split(/\r?\n/);

function toBr(s) {
  if (!s || !s.trim()) return s;
  return s
    .replace(/\s*\/\s*/g, '<br>')
    .replace(/\s*;\s+/g, '<br>');
}

function parseRow(line) {
  const parts = [];
  let cur = '';
  let inCell = false;
  for (let i = 0; i < line.length; i++) {
    const c = line[i];
    if (c === '|') {
      parts.push(cur);
      cur = '';
      inCell = false;
    } else {
      cur += c;
      inCell = true;
    }
  }
  if (cur !== '') parts.push(cur);
  return parts;
}

const out = [asisLines[0]]; // header
for (let i = 1; i < asisLines.length; i++) {
  const asisRow = parseRow(asisLines[i]);
  const refRow = i < refLines.length ? parseRow(refLines[i]) : [];
  if (asisRow.length < 9) {
    out.push(asisLines[i]);
    continue;
  }
  // ASIS: 0=empty, 1=순번, 2=기능번호, ... 8=기능설명. (이미 || 로 깨진 행은 1이 빈칸이므로 2~9 사용)
  const base = asisRow[1] === '' && asisRow[2] !== '' ? asisRow.slice(2, 10) : asisRow.slice(1, 9);
  const prefix = base.length >= 8 ? base : asisRow.slice(1, 9);
  let suffix;
  if (refRow.length >= 15) {
    suffix = [
      toBr((refRow[9] || '').trim()),
      toBr((refRow[10] || '').trim()),
      toBr((refRow[11] || '').trim()),
      toBr((refRow[12] || '').trim()),
      toBr((refRow[13] || '').trim()),
      toBr((refRow[14] || '').trim())
    ];
  } else {
    suffix = (asisRow.slice(9).length >= 6) ? asisRow.slice(9, 15) : ['', '', '', '', '', ''];
  }
  out.push('|' + prefix.join('|') + '|' + suffix.join('|') + '|');
}

fs.writeFileSync(asisPath, out.join('\n'), 'utf8');
console.log('Done. Wrote', asisPath);
