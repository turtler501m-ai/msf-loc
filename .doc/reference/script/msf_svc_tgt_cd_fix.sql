-- SVC_TGT_CD 상세코드를 프론트엔드 ID 기준 문자열로 재입력
-- EXPNSN_STR_VAL1 = CONCURRENT_BLOCK_YN, EXPNSN_STR_VAL2 = IMAGING_YN
-- MSF_SVC_CHG_CATEGORY / MSF_SVC_CHG_OPTION 커스텀 테이블 삭제

-- ── 1. 기존 SVC_TGT_CD 상세코드 삭제 후 재입력 ──
DELETE FROM MSF_CD_DTL WHERE CD_GROUP_ID = 'SVC_TGT_CD';

INSERT INTO MSF_CD_DTL (CD_GROUP_ID, DTL_CD, DTL_CD_NM, SORT_ODRG, EXPNSN_STR_VAL1, EXPNSN_STR_VAL2, USE_YN, PSTNG_START_DATE, PSTNG_END_DATE) VALUES
  ('SVC_TGT_CD', 'WIRELESS_BLOCK', '무선데이터차단 서비스',       1,  'N', 'Y', 'Y', '20260301000000', '99991231235959'),
  ('SVC_TGT_CD', 'INFO_LIMIT',     '정보료 상한금액 설정/변경',   2,  'N', 'Y', 'Y', '20260301000000', '99991231235959'),
  ('SVC_TGT_CD', 'ADDITION',       '부가서비스 신청/변경',        3,  'N', 'Y', 'Y', '20260301000000', '99991231235959'),
  ('SVC_TGT_CD', 'RATE_CHANGE',    '요금제 변경',                 4,  'Y', 'Y', 'Y', '20260301000000', '99991231235959'),
  ('SVC_TGT_CD', 'INSURANCE',      '단말보험 가입',               5,  'N', 'Y', 'Y', '20260301000000', '99991231235959'),
  ('SVC_TGT_CD', 'ANY_SOLO',       '아무나 SOLO 결합',            6,  'N', 'Y', 'Y', '20260301000000', '99991231235959'),
  ('SVC_TGT_CD', 'DATA_SHARING',   '데이터쉐어링 가입/해지',      7,  'N', 'Y', 'Y', '20260301000000', '99991231235959'),
  ('SVC_TGT_CD', 'LOST_RESTORE',   '분실복구/일시정지해제 신청',  8,  'Y', 'Y', 'Y', '20260301000000', '99991231235959'),
  ('SVC_TGT_CD', 'USIM_CHANGE',    'USIM 변경',                   9,  'N', 'Y', 'Y', '20260301000000', '99991231235959'),
  ('SVC_TGT_CD', 'NUM_CHANGE',     '번호변경',                    10, 'Y', 'Y', 'Y', '20260301000000', '99991231235959');

-- ── 2. 커스텀 테이블 삭제 ──
DROP TABLE IF EXISTS MSF_SVC_CHG_OPTION;
DROP TABLE IF EXISTS MSF_SVC_CHG_CATEGORY;
