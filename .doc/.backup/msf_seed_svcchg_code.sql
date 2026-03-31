-- MSF 서비스변경 코드 전용 테이블 생성 및 초기 데이터
-- SvcChgBaseMapper.xml 컬럼 구조에 맞게 신규 생성
-- 기존 MSF_CD_GROUP_BAS / MSF_CD_DTL 은 MCP 범용 코드 테이블로 구조가 다름

-- ──────────────────────────────────────────────────
-- 1. 서비스변경 카테고리 테이블
-- ──────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS MSF_SVC_CHG_CATEGORY (
    GROUP_CD   VARCHAR(30)  NOT NULL,
    GROUP_NM   VARCHAR(150) NOT NULL,
    SVC_TGT_CD VARCHAR(30),
    USE_YN     CHAR(1)      NOT NULL DEFAULT 'Y',
    SORT_SEQ   INT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_msf_svc_chg_category PRIMARY KEY (GROUP_CD)
);

-- ──────────────────────────────────────────────────
-- 2. 서비스변경 옵션 상세 테이블
-- ──────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS MSF_SVC_CHG_OPTION (
    DTL_CD               VARCHAR(30)  NOT NULL,
    GROUP_CD             VARCHAR(30)  NOT NULL,
    DTL_CD_NM            VARCHAR(150) NOT NULL,
    CONCURRENT_BLOCK_YN  CHAR(1)      NOT NULL DEFAULT 'N',
    IMAGING_YN           CHAR(1)      NOT NULL DEFAULT 'Y',
    USE_YN               CHAR(1)      NOT NULL DEFAULT 'Y',
    SORT_SEQ             INT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_msf_svc_chg_option PRIMARY KEY (DTL_CD),
    CONSTRAINT fk_svc_chg_opt_grp FOREIGN KEY (GROUP_CD)
        REFERENCES MSF_SVC_CHG_CATEGORY(GROUP_CD)
);

-- ──────────────────────────────────────────────────
-- 3. 카테고리 초기 데이터
-- ──────────────────────────────────────────────────
INSERT INTO MSF_SVC_CHG_CATEGORY (GROUP_CD, GROUP_NM, SVC_TGT_CD, USE_YN, SORT_SEQ) VALUES
  ('BASIC',     '기본 서비스',       'BASIC',   'Y', 1),
  ('RATE',      '요금제/부가서비스', 'RATE',    'Y', 2),
  ('COMBINE',   '결합서비스',        'COMBINE', 'Y', 3),
  ('PAUSE_GRP', '일시/분실정지',     'PAUSE',   'Y', 4),
  ('INFO_CHG',  '가입정보 변경',     'INFO',    'Y', 5)
ON CONFLICT (GROUP_CD) DO NOTHING;

-- ──────────────────────────────────────────────────
-- 4. 옵션 초기 데이터
-- ──────────────────────────────────────────────────
INSERT INTO MSF_SVC_CHG_OPTION (DTL_CD, GROUP_CD, DTL_CD_NM, CONCURRENT_BLOCK_YN, IMAGING_YN, USE_YN, SORT_SEQ) VALUES
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
