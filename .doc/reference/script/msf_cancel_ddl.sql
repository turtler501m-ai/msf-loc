-- =============================================================
-- 서비스해지 신청서 관련 DB 준비 스크립트 (PostgreSQL)
-- 작성: 2026-03-24
-- =============================================================
-- 주의: MSF_REQUEST_CANCEL 테이블과 SQ_REQUEST_KEY 시퀀스는
--       msf_create_tables.sql 에 이미 정의되어 있음.
--       이 파일은 누락된 경우에만 실행.
-- =============================================================

-- 시퀀스 (msf_create_tables.sql 라인 159에 이미 정의됨)
CREATE SEQUENCE IF NOT EXISTS SQ_REQUEST_KEY
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO CYCLE;

-- 해지 신청서 테이블 (msf_create_tables.sql 라인 1707에 이미 정의됨)
-- 직접 실행 시 msf_create_tables.sql의 전체 스크립트를 참조할 것.

-- 중복 접수 체크용 인덱스 (없는 경우 추가)
CREATE INDEX IF NOT EXISTS IDX_MSF_REQUEST_CANCEL_MOBILE
    ON MSF_REQUEST_CANCEL (CANCEL_MOBILE_NO, PROC_CD);

-- =============================================================
-- 테이블 컬럼 매핑 (TOBE 저장 필드)
-- =============================================================
-- MANAGER_CD       : '0' 고정 (TOBE 미사용)
-- AGENT_CD         : 대리점코드 (FormSvcCnclStep1 선택값)
-- OPER_TYPE_CD     : 'CC' (Cancel Consult) 고정
-- CSTMR_TYPE_CD    : 고객유형 (NA/NM/FN/FNM/JP/PP)
-- CANCEL_MOBILE_NO : 해지 휴대폰번호 (숫자만)
-- CONTRACT_NUM     : 계약번호 (NCN, 인증 후 자동 입력)
-- CSTMR_NM         : 고객명
-- RECEIVE_WAY_CD   : 'E'(이메일) or 'P'(우편) — 이메일 입력 여부로 결정
-- RECEIVE_MOBILE_NO: 연락받을번호 (= CANCEL_MOBILE_NO)
-- CANCEL_USE_COMPANY_CD : 해지후 사용 통신사 (KTM/KT/SKT/LGT/ETC)
-- PAY_AMT          : 잔여사용요금 (X18 sumAmt)
-- PNLT_AMT         : 위약금 (현재 null, 별도 M플랫폼 연동 예정)
-- LAST_SUM_AMT     : 최종정산 = PAY_AMT + PNLT_AMT + INSTAMT_MNTH_AMT
-- INSTAMT_MNTH_AMT : 단말기 분납잔액 (현재 null)
-- BENEFIT_AGREE_YN : 'Y' 고정 (동의 후 저장)
-- CLAUSE_CNTR_DEL_YN : 'Y' 고정
-- ETC_AGREE_YN     : 'Y' 고정
-- MEMO             : 메모
-- PROC_CD          : 'RC' (접수) 고정
-- REGST_ID / CRET_ID : 'MFORM' 고정
