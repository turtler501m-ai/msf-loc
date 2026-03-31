-- MSF 코드 기초 데이터 (서비스변경 업무 카테고리)
-- 기준: SvcChgBaseSvcImpl.getMockCategories()

-- MSF_CD_GROUP_BAS
INSERT INTO MSF_CD_GROUP_BAS (GROUP_CD, GROUP_NM, SVC_TGT_CD, USE_YN, SORT_SEQ)
VALUES
  ('BASIC',     '기본 서비스',       'BASIC',   'Y', 1),
  ('RATE',      '요금제/부가서비스', 'RATE',    'Y', 2),
  ('COMBINE',   '결합서비스',        'COMBINE', 'Y', 3),
  ('PAUSE_GRP', '일시/분실정지',     'PAUSE',   'Y', 4),
  ('INFO_CHG',  '가입정보 변경',     'INFO',    'Y', 5)
ON CONFLICT (GROUP_CD) DO NOTHING;

-- MSF_CD_DTL
INSERT INTO MSF_CD_DTL (DTL_CD, GROUP_CD, DTL_CD_NM, CONCURRENT_BLOCK_YN, IMAGING_YN, USE_YN, SORT_SEQ)
VALUES
  ('WIRELESS_BLOCK', 'BASIC',     '무선데이터차단 서비스',       'N', 'Y', 'Y', 1),
  ('INFO_LIMIT',     'BASIC',     '정보료 상한금액 설정/변경',   'N', 'Y', 'Y', 2),
  ('ADDITION',       'BASIC',     '부가서비스 신청/변경',        'N', 'Y', 'Y', 3),
  ('RATE_CHANGE',    'RATE',      '요금제 변경',                 'Y', 'Y', 'Y', 1),
  ('INSURANCE',      'RATE',      '단말보험 가입',               'N', 'Y', 'Y', 2),
  ('ANY_SOLO',       'COMBINE',   '아무나 SOLO 결합',            'N', 'Y', 'Y', 1),
  ('DATA_SHARING',   'COMBINE',   '데이터쉐어링 가입/해지',      'N', 'Y', 'Y', 2),
  ('LOST_RESTORE',   'PAUSE_GRP', '분실복구/일시정지해제 신청',  'Y', 'Y', 'Y', 1),
  ('USIM_CHANGE',    'INFO_CHG',  'USIM 변경',                   'N', 'Y', 'Y', 1),
  ('NUM_CHANGE',     'INFO_CHG',  '번호변경',                    'Y', 'Y', 'Y', 2)
ON CONFLICT (DTL_CD) DO NOTHING;
