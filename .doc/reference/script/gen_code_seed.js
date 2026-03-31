/**
 * MMSP-DS-04-코드_정의서_전체_20260323.md 에서
 * INSERT SQL 블록을 직접 추출하여 실행 가능한 SQL 파일로 변환
 */
const fs = require('fs');
const path = require('path');

const mdPath = path.join(__dirname, '..', 'MMSP-DS-04-코드_정의서_전체_20260323.md');
const outPath = path.join(__dirname, 'msf_cd_seed_from_doc.sql');

const text = fs.readFileSync(mdPath, 'utf8');

// ```sql ... ``` 블록에서 INSERT INTO MSF_CD_ 로 시작하는 구문 추출
const sqlBlockRe = /```sql\n([\s\S]*?)```/g;
const grpInserts = [];
const dtlInserts = [];

let m;
while ((m = sqlBlockRe.exec(text)) !== null) {
    const block = m[1];
    const stmts = block.split('\n').map(l => l.trim()).filter(l => l.startsWith('INSERT INTO MSF_CD_'));
    for (const stmt of stmts) {
        if (stmt.startsWith('INSERT INTO MSF_CD_GROUP_BAS')) {
            // CD_GROUP_ID 컬럼 위치 찾기: VALUES ( , 'CD_GROUP_ID', ... → 첫 번째 빈 자리 보정
            // 원본 SQL에 CD_GROUP_ID 값이 첫 번째 ,로 비어있음 → 두 번째 값이 실제 ID
            // 예: VALUES ( , 'TMP_STEP_CD', '임시저장...' ...
            // → CD_GROUP_ID = 두 번째 값, CD_GROUP_NM = 세 번째 값
            // 이를 올바른 컬럼 순서로 재구성
            const fixed = fixGroupInsert(stmt);
            if (fixed) grpInserts.push(fixed);
        } else if (stmt.startsWith('INSERT INTO MSF_CD_DTL')) {
            const fixed = fixDtlInsert(stmt);
            if (fixed) dtlInserts.push(fixed);
        }
    }
}

/**
 * 원본: INSERT INTO MSF_CD_GROUP_BAS ( CD_GROUP_ID, CD_GROUP_NM, ... ) VALUES ( , 'TMP_STEP_CD', '임시저장단계코드', ... )
 * → 첫 번째 VALUES 항목이 빈 값(,) → CD_GROUP_NM 값(두 번째)이 실제 CD_GROUP_ID
 * → CD_GROUP_NM 값(세 번째)이 실제 CD_GROUP_NM
 */
function fixGroupInsert(stmt) {
    try {
        // VALUES ( , 'id', 'nm', ... ) 파싱
        const valMatch = stmt.match(/VALUES\s*\(([\s\S]+)\)/i);
        if (!valMatch) return null;
        const vals = parseValues(valMatch[1]);
        // vals[0]='' (빈), vals[1]=cd_group_nm_as_id, vals[2]=cd_group_nm, vals[3]=cd_group_desc
        // vals[4]=expnsn1, vals[5]=expnsn2, vals[6]=expnsn3, vals[7]=cd_group_cd
        // vals[8]=use_yn, vals[9]=pstng_start_date, vals[10]=pstng_end_date
        // vals[11]=cret_ip, vals[12]=cret_dt(NOW()), vals[13]=cret_id ...
        const cdGroupId   = vals[1] || '';
        const cdGroupNm   = vals[2] || '';
        const cdGroupCd   = vals[7] || '00';
        const useYn       = (vals[8] || 'Y').substring(0, 1);
        const pstngStart  = vals[9] || '20260301000000';
        const pstngEnd    = vals[10] || '99991231235959';
        if (!cdGroupId) return null;
        return `INSERT INTO MSF_CD_GROUP_BAS (CD_GROUP_ID, CD_GROUP_NM, CD_GROUP_CD, USE_YN, PSTNG_START_DATE, PSTNG_END_DATE) VALUES (${q(cdGroupId)}, ${q(cdGroupNm)}, ${q(cdGroupCd)}, ${q(useYn)}, ${q(pstngStart)}, ${q(pstngEnd)}) ON CONFLICT (CD_GROUP_ID) DO UPDATE SET CD_GROUP_NM=EXCLUDED.CD_GROUP_NM;`;
    } catch(e) {
        return null;
    }
}

/**
 * 원본: INSERT INTO MSF_CD_DTL ( CD_GROUP_ID, DTL_CD, DTL_CD_NM, DTL_CD_ABBR_NM, DTL_CD_DESC, UP_GRP_CD, SORT_ODRG, EXPNSN_STR_VAL1, EXPNSN_STR_VAL2, EXPNSN_STR_VAL3, IMG_NM, USE_YN, PSTNG_START_DATE, ... )
 *          VALUES ( , 'TMP_STEP_CD', '0', '가입 선택', '', '', '', 11, '', '', '', '', 'Y', ... )
 * vals[0]='' (빈), vals[1]=cd_group_id, vals[2]=dtl_cd, vals[3]=dtl_cd_nm
 * vals[4]=abbr_nm, vals[5]=desc, vals[6]=up_grp_cd, vals[7]=sort_odrg
 * vals[8]=expnsn1, vals[9]=expnsn2, vals[10]=expnsn3, vals[11]=img_nm
 * vals[12]=use_yn, vals[13]=pstng_start, vals[14]=pstng_end
 */
function fixDtlInsert(stmt) {
    try {
        const valMatch = stmt.match(/VALUES\s*\(([\s\S]+)\)/i);
        if (!valMatch) return null;
        const vals = parseValues(valMatch[1]);
        const cdGroupId  = vals[1] || '';
        const dtlCd      = vals[2] || '';
        const dtlCdNm    = vals[3] || '';
        const abbrNm     = vals[4] || '';
        const dtlCdDesc  = vals[5] || '';
        const upGrpCd    = vals[6] || '';
        const sortOdrg   = parseInt(vals[7]) || 0;
        const expnsn1    = vals[8] || '';
        const expnsn2    = vals[9] || '';
        const expnsn3    = vals[10] || '';
        const useYn      = (vals[12] || 'Y').substring(0, 1);
        const pstngStart = vals[13] || '20260301000000';
        const pstngEnd   = vals[14] || '99991231235959';
        if (!cdGroupId || !dtlCd) return null;
        return `INSERT INTO MSF_CD_DTL (CD_GROUP_ID, DTL_CD, DTL_CD_NM, DTL_CD_ABBR_NM, DTL_CD_DESC, UP_GRP_CD, SORT_ODRG, EXPNSN_STR_VAL1, EXPNSN_STR_VAL2, EXPNSN_STR_VAL3, USE_YN, PSTNG_START_DATE, PSTNG_END_DATE) VALUES (${q(cdGroupId)}, ${q(dtlCd)}, ${q(dtlCdNm)}, ${q(abbrNm)}, ${q(dtlCdDesc)}, ${q(upGrpCd)}, ${sortOdrg}, ${q(expnsn1)}, ${q(expnsn2)}, ${q(expnsn3)}, ${q(useYn)}, ${q(pstngStart)}, ${q(pstngEnd)}) ON CONFLICT (CD_GROUP_ID, DTL_CD) DO UPDATE SET DTL_CD_NM=EXCLUDED.DTL_CD_NM, SORT_ODRG=EXCLUDED.SORT_ODRG;`;
    } catch(e) {
        return null;
    }
}

/** VALUES 괄호 안을 파싱: 쉼표 구분, 문자열은 '' 이스케이프 처리 */
function parseValues(valStr) {
    const result = [];
    let cur = '';
    let inStr = false;
    let i = 0;
    while (i < valStr.length) {
        const ch = valStr[i];
        if (ch === "'" && !inStr) {
            inStr = true; cur += ch; i++;
        } else if (ch === "'" && inStr) {
            if (valStr[i+1] === "'") { cur += "''"; i += 2; } // escaped quote
            else { inStr = false; cur += ch; i++; }
        } else if (ch === ',' && !inStr) {
            result.push(cur.trim());
            cur = '';
            i++;
        } else {
            cur += ch; i++;
        }
    }
    result.push(cur.trim());
    // 문자열 값에서 따옴표 제거
    return result.map(v => {
        if (v === '' || v === 'NULL' || v === 'null') return '';
        if (v.startsWith("'") && v.endsWith("'")) return v.slice(1, -1).replace(/''/g, "'");
        if (v === 'NOW()') return '';
        return v;
    });
}

function q(s) {
    return "'" + (s || '').replace(/'/g, "''") + "'";
}

// 중복 제거 (같은 INSERT 여러 번 나올 수 있음)
const uniqueGrp = [...new Map(grpInserts.filter(Boolean).map(s => [s.split("VALUES")[1], s])).values()];
const uniqueDtl = [...new Map(dtlInserts.filter(Boolean).map(s => [s.split("VALUES")[1], s])).values()];

const sql = `-- MSF_CD_GROUP_BAS / MSF_CD_DTL 초기 데이터
-- 출처: MMSP-DS-04-코드_정의서_전체_20260323.md (INSERT SQL 블록 직접 추출)
-- 생성: gen_code_seed.js

-- ============================================================
-- MSF_CD_GROUP_BAS (${uniqueGrp.length}건)
-- ============================================================
${uniqueGrp.join('\n')}

-- ============================================================
-- MSF_CD_DTL (${uniqueDtl.length}건)
-- ============================================================
${uniqueDtl.join('\n')}
`;

fs.writeFileSync(outPath, sql, 'utf8');
console.log(`그룹 ${uniqueGrp.length}건, 상세 ${uniqueDtl.length}건 → ${outPath}`);
