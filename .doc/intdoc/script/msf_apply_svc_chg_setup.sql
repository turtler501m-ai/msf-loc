-- ============================================================
-- 서비스변경 신청서 DB 설정
-- 실행 필수 조건: msf_create_tables.sql 실행 완료 후
-- 작성일: 2026-03-23
-- ============================================================

-- 1. MSF_REQUEST_SVC_CHG_DTL 상세 일련번호 시퀀스 생성
CREATE SEQUENCE IF NOT EXISTS SQ_REQUEST_SVC_CHG_DTL_SEQ START 1;

-- 2. SVC_TGT_CD 컬럼 VARCHAR(2) → VARCHAR(20) 확장
--    (WIRELESS_BLOCK, RATE_CHANGE 등 문자열 코드 저장 필요)
ALTER TABLE MSF_REQUEST_SVC_CHG_DTL ALTER COLUMN SVC_TGT_CD TYPE VARCHAR(20);

-- 확인
SELECT column_name, data_type, character_maximum_length
FROM information_schema.columns
WHERE table_name = 'msf_request_svc_chg_dtl'
  AND column_name = 'svc_tgt_cd';
