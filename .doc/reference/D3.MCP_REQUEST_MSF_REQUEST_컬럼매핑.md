# MCP_REQUEST → MSF_REQUEST 테이블 컬럼 매핑

> 기준: ASIS M포탈(MCP_REQUEST) → TOBE MSF(MSF_REQUEST)
> MSP(ktisMvno) DB링크: `@DL_MCP` 로 MCP_REQUEST 직접 INSERT/SELECT
> 작성일: 2026-03-19

---

## 1. MSF_REQUEST ← MCP_REQUEST

| ASIS (MCP_REQUEST)            | TOBE (MSF_REQUEST)              | 비고 |
|-------------------------------|---------------------------------|------|
| REQUEST_KEY                   | REQUEST_KEY                     | PK, Sequence |
| MANAGER_CODE                  | MANAGER_CD                      | 명칭 통일 (_CODE → _CD) |
| AGENT_CODE                    | AGENT_CD                        | 명칭 통일 |
| SERVICE_TYPE                  | SERVICE_TYPE_CD                 | _CD 접미사 추가 |
| OPER_TYPE                     | OPER_TYPE_CD                    | _CD 접미사 추가 |
| CSTMR_TYPE                    | CSTMR_TYPE_CD                   | _CD 접미사 추가 |
| ON_OFF_TYPE                   | ON_OFF_TYPE_CD                  | _CD 접미사 추가 |
| REQ_BUY_TYPE                  | REQ_BUY_TYPE_CD                 | _CD 접미사 추가 |
| ONLINE_AUTH_TYPE              | ONLINE_AUTH_TYPE_CD             | _CD 접미사 추가 |
| ONLINE_AUTH_INFO              | AUTH_INFO                       | 명칭 단순화 |
| RES_CODE                      | RES_CD                          | _CODE → _CD |
| RES_MSG                       | RES_MSG                         | 동일 |
| RES_NO                        | RES_NO                          | 동일 |
| PSTATE                        | PROC_CD                         | 처리상태 |
| REQUEST_STATE_CODE            | (없음 → PROC_CD 통합)           | TOBE에서 PROC_CD로 통합 |
| FILE01                        | FILE_NM                         | 명칭 변경 |
| FILE01_MASK                   | FILE_MASK_NM                    | 명칭 변경 |
| SCAN_ID                       | SCAN_ID                         | 동일 |
| RIP                           | CRET_IP                         | 명칭 변경 (등록IP) |
| SYS_RDATE                     | REG_DT / CRET_DT                | 등록일시 |
| OPEN_REQ_DATE                 | REQ_DATE                        | 명칭 변경 |
| SHOP_CD                       | SHOP_CD                         | 동일 |
| SHOP_NM                       | SHOP_NM                         | 동일 |
| REAL_SHOP_NM                  | REAL_SHOP_NM                    | 동일 |
| CNTPNT_SHOP_ID                | CNTPNT_SHOP_CD                  | _ID → _CD |
| CPNT_ID                       | CPNT_ID                         | 동일 |
| MEMO                          | MEMO                            | 동일 |
| REC_YN                        | REC_YN                          | 동일 |
| SO_CD                         | SO_CD                           | 동일 |
| APP_FORM_YN                   | APP_FORM_YN                     | 동일 |
| APP_FORM_XML_YN               | APP_FORM_XML_YN                 | 동일 |
| CONTRACT_NUM                  | (없음 → 서비스별 하위 테이블)   | 해지/서비스변경 하위 테이블로 이동 |
| CLAUSE_PRI_COLLECT_FLAG       | CLAUSE_PRI_COLLECT_YN           | _FLAG → _YN |
| CLAUSE_PRI_OFFER_FLAG         | CLAUSE_PRI_OFFER_YN             | _FLAG → _YN |
| CLAUSE_ESS_COLLECT_FLAG       | CLAUSE_TERM_YN                  | 명칭 변경 (필수약관) |
| CLAUSE_PRI_AD_FLAG            | CLAUSE_MARKETING_YN             | 명칭 변경 |
| CLAUSE_CONFIDENCE_FLAG        | CLAUSE_CUSTOM_INFO_YN           | 명칭 변경 |
| CLAUSE_JEHU_FLAG              | (MSF_REQUEST_CLAUSE 테이블)     | 약관동의 하위 테이블로 분리 |
| CLAUSE_INSURANCE_FLAG         | (MSF_CUST_REQUEST_INSR)         | 보험정보 하위 테이블로 분리 |
| CLAUSE_INSR_PROD_FLAG         | (MSF_CUST_REQUEST_INSR)         | 보험정보 하위 테이블로 분리 |
| INSR_CD                       | (MSF_CUST_REQUEST_INSR)         | 보험정보 하위 테이블로 분리 |
| INSR_PROD_CD                  | (MSF_CUST_REQUEST_INSR)         | 보험정보 하위 테이블로 분리 |
| FAXYN                         | (MSF_CUST_REQUEST_JOIN_FORM)    | 수신방법 하위 테이블로 분리 |
| FAXNUM                        | (MSF_CUST_REQUEST_JOIN_FORM)    | 수신방법 하위 테이블로 분리 |
| CRET_ID / RVISN_ID            | CRET_ID / AMD_ID                | ASIS rvisn → TOBE amd(수정) |
| RVISN_DTTM                    | AMD_DT                          | 수정일시 |
| PROMOTION_CD                  | (없음)                          | TOBE 미사용 |
| DIS_PRMT_ID                   | (없음)                          | TOBE 미사용 |
| OPEN_MARKET_REFERER           | (없음)                          | TOBE 미사용 |
| HUB_ORDER_SEQ                 | (없음)                          | TOBE 미사용 |
| EID / IMEI1 / IMEI2           | (MSF_REQUEST_SALE)              | 판매정보 하위 테이블로 분리 |
| USIM_KINDS_CD                 | (없음)                          | TOBE 미사용 (단말유형은 REQ_BUY_TYPE_CD로 처리) |
| PERSONAL_INFO_COLLECT_AGREE   | CLAUSE_PRI_COLLECT_YN           | 통합 |
| OTHERS_TRNS_AGREE             | CLAUSE_PRI_OFFER_YN             | 통합 |
| CLAUSE_SENSI_COLLECT_FLAG     | (MSF_REQUEST_CLAUSE 테이블)     | 약관동의 하위 테이블로 분리 |
| CLAUSE_SENSI_OFFER_FLAG       | (MSF_REQUEST_CLAUSE 테이블)     | 약관동의 하위 테이블로 분리 |
| CLAUSE_PARTNER_OFFER_FLAG     | (MSF_REQUEST_CLAUSE 테이블)     | 약관동의 하위 테이블로 분리 |
| FATH_TRG_YN                   | (없음 또는 MSF_REQUEST_CLAUSE)  | FATH 서비스 약관 |
| CLAUSE_FATH_FLAG              | (MSF_REQUEST_CLAUSE 테이블)     | 약관동의 하위 테이블로 분리 |
| FATH_TRANSAC_ID               | (없음)                          | TOBE 미사용 |
| PRNTS_CONTRACT_NO             | (MSF_REQUEST_SALE)              | 판매정보 하위 테이블로 분리 |
| COMBINE_SOLO_TYPE             | (없음)                          | TOBE 미사용 |
| COMBINE_SOLO_FLAG             | (없음)                          | TOBE 미사용 |

---

## 2. MSF_REQUEST_CSTMR ← MCP_REQUEST_CSTMR

| ASIS (MCP_REQUEST_CSTMR)      | TOBE (MSF_REQUEST_CSTMR)            | 비고 |
|-------------------------------|-------------------------------------|------|
| REQUEST_KEY                   | REQUEST_KEY                         | PK/FK |
| CSTMR_NAME                    | CSTMR_NM                            | _NAME → _NM |
| CSTMR_NATIVE_RRN              | CSTMR_NATIVE_RRN                    | 동일 (암호화) |
| CSTMR_FOREIGNER_NATION        | CSTMR_FOREIGNER_NATION              | 동일 |
| CSTMR_FOREIGNER_PN            | CSTMR_FOREIGNER_PN                  | 동일 (암호화) |
| CSTMR_FOREIGNER_RRN           | CSTMR_FOREIGNER_RRN                 | 동일 (암호화) |
| CSTMR_FOREIGNER_SDATE         | CSTMR_FOREIGNER_VDATE_START_DATE    | 명칭 변경 |
| CSTMR_FOREIGNER_EDATE         | CSTMR_FOREIGNER_VDATE_END_DATE      | 명칭 변경 |
| CSTMR_FOREIGNER_DOD           | CSTMR_FOREIGNER_BIRTH               | 명칭 변경 (생년월일) |
| CSTMR_PRIVATE_CNAME           | CSTMR_PRIVATE_CNAME                 | 동일 |
| CSTMR_PRIVATE_NUMBER          | CSTMR_PRIVATE_BIZ_NO                | 명칭 변경 |
| CSTMR_JURIDICAL_CNAME         | CSTMR_JURIDICAL_CNAME               | 동일 |
| CSTMR_JURIDICAL_RRN           | CSTMR_JURIDICAL_RRN                 | 동일 (암호화) |
| CSTMR_JURIDICAL_NUMBER        | CSTMR_JURIDICAL_BIZ_NO              | 명칭 변경 |
| CSTMR_TEL_FN                  | (없음)                              | TOBE 미사용 (유선전화 미지원) |
| CSTMR_TEL_MN                  | (없음)                              | TOBE 미사용 |
| CSTMR_TEL_RN                  | (없음)                              | TOBE 미사용 |
| CSTMR_MOBILE_FN               | (MSF_CUST_REQUEST_JOIN_FORM)        | 수신방법 테이블로 분리 |
| CSTMR_MOBILE_MN               | (MSF_CUST_REQUEST_JOIN_FORM)        | 수신방법 테이블로 분리 |
| CSTMR_MOBILE_RN               | (MSF_CUST_REQUEST_JOIN_FORM)        | 수신방법 테이블로 분리 |
| CSTMR_POST                    | (MSF_CUST_REQUEST_JOIN_FORM)        | 수신방법 테이블로 분리 |
| CSTMR_ADDR                    | (MSF_CUST_REQUEST_JOIN_FORM)        | 수신방법 테이블로 분리 |
| CSTMR_ADDR_DTL                | (MSF_CUST_REQUEST_JOIN_FORM)        | 수신방법 테이블로 분리 |
| CSTMR_BILL_SEND_CODE          | (MSF_REQUEST_BILL_REQ)              | 청구신청 테이블로 분리 |
| CSTMR_MAIL                    | (MSF_CUST_REQUEST_JOIN_FORM)        | 수신방법 테이블로 분리 |
| OTHERS_PAYMENT_AG             | MSF_REQUEST_BILL_REQ.OTHERS_PAYMENT_YN | 청구신청 테이블로 분리 |
| OTHERS_PAYMENT_NM             | MSF_REQUEST_BILL_REQ.OTHERS_PAYMENT_NM | 동일 |
| OTHERS_PAYMENT_RRN            | MSF_REQUEST_BILL_REQ.OTHERS_PAYMENT_RRN | 동일 (암호화) |
| OTHERS_PAYMENT_RELATION       | MSF_REQUEST_BILL_REQ.OTHERS_PAYMENT_REL_TYPE_CD | 명칭 변경 |
| SELF_INQRY_AGRM_YN            | MSF_REQUEST_AGENT.MINOR_AGENT_SELF_INQRY_AGRM_YN | 대리인 테이블로 분리 |
| SELF_CERT_TYPE                | MSF_REQUEST_AGENT.MINOR_AGENT_SELF_CERT_TYPE_CD | 대리인 테이블로 분리 |
| SELF_ISSU_EXPR_DT             | MSF_REQUEST_AGENT.MINOR_AGENT_SELF_ISSU_EXPR_DATE | 대리인 테이블로 분리 |
| SELF_ISSU_NUM                 | MSF_REQUEST_AGENT.MINOR_AGENT_SELF_ISSU_NO | 대리인 테이블로 분리 (암호화) |
| SELF_CSTMR_CI                 | (없음)                              | CI는 TOBE 미사용 |
| SYS_RDATE                     | CRET_DT                             | 생성일시 |
| RVISN_ID                      | AMD_ID                              | 수정자ID |
| RVISN_DTTM                    | AMD_DT                              | 수정일시 |

---

## 3. MSF_CUST_REQUEST_MST ← NMCP_CUST_REQUEST_MST

| ASIS (NMCP_CUST_REQUEST_MST)  | TOBE (MSF_CUST_REQUEST_MST)     | 비고 |
|-------------------------------|---------------------------------|------|
| CUST_REQ_SEQ                  | REQUEST_KEY                     | PK 명칭 변경 (MSF_REQUEST.REQUEST_KEY 공유) |
| REQ_TYPE                      | REQ_TYPE_CD                     | _CD 접미사 추가 |
| USERID                        | USER_ID                         | 명칭 변경 |
| CSTMR_NAME                    | CSTMR_NM                        | _NAME → _NM |
| MOBILE_NO                     | MOBILE_NO                       | 동일 |
| CSTMR_NATIVE_RRN              | CSTMR_NATIVE_RRN                | 동일 (암호화) |
| CONTRACT_NUM                  | CONTRACT_NUM                    | 동일 |
| CSTMR_TYPE                    | CSTMR_TYPE_CD                   | _CD 접미사 추가 |
| ONLINE_AUTH_TYPE              | ONLINE_AUTH_TYPE_CD             | _CD 접미사 추가 |
| ONLINE_AUTH_INFO              | ONLINE_AUTH_INFO                | 동일 |
| ETC_MOBILE                    | ETC_MOBILE_NO                   | 명칭 변경 |
| CRET_ID                       | CRET_ID                         | 동일 |
| SYS_RDATE                     | CRET_DT                         | 생성일시 |

---

## 4. MSF_REQUEST_CANCEL ← NMCP_CUST_REQUEST_MST (서비스해지)

| ASIS (NMCP_CUST_REQUEST_MST 일부) | TOBE (MSF_REQUEST_CANCEL)       | 비고 |
|-----------------------------------|---------------------------------|------|
| SCAN_ID                           | SCAN_ID                         | 동일 |
| CONTRACT_NUM                      | CONTRACT_NUM                    | 동일 |
| CSTMR_TYPE                        | CSTMR_TYPE_CD                   | _CD 접미사 추가 |
| ONLINE_AUTH_TYPE                  | IDENTITY_TYPE_CD                | 인증유형 |
| SOC (해지SOC)                     | CANCEL_USE_COMPANY_CD           | 해지 사업자코드 |
| REQ_PAY_TYPE                      | (MSF_REQUEST_BILL_REQ)          | 청구신청 테이블로 분리 |

---

## 5. MSF_REQUEST_NAME_CHG ← NMCP_CUST_REQUEST_NAME_CHG

| ASIS (NMCP_CUST_REQUEST_NAME_CHG) | TOBE (MSF_REQUEST_NAME_CHG)     | 비고 |
|-----------------------------------|---------------------------------|------|
| CUST_REQ_SEQ                      | REQUEST_KEY                     | PK |
| SCAN_ID                           | (MSF_REQUEST에 통합)            | 마스터에 보관 |
| CSTMR_NAME                        | (MSF_REQUEST_CSTMR)             | 고객정보 테이블 |
| CSTMR_TYPE                        | CSTMR_TYPE_CD                   | _CD 접미사 추가 |
| SOC / SOC_NM                      | (없음)                          | TOBE에서 명의변경 시 SOC 불필요 |
| CLAUSE_CNTR_DEL_FLAG              | (MSF_REQUEST_CLAUSE)            | 약관동의 테이블로 분리 |
| REQ_INFO_CHG_YN                   | (없음)                          | TOBE 미사용 |
| REQ_PAY_TYPE                      | (MSF_REQUEST_BILL_REQ)          | 청구신청 테이블로 분리 |
| REQ_BANK                          | MSF_REQUEST_BILL_REQ.REQ_BANK_CD | 청구신청 테이블 |
| REQ_ACCOUNT_NUMBER                | MSF_REQUEST_BILL_REQ.REQ_ACCOUNT_NO | 암호화 |
| REQ_CARD_COMPANY                  | MSF_REQUEST_BILL_REQ.REQ_CARD_COMPANY_CD | |
| REQ_CARD_NO                       | MSF_REQUEST_BILL_REQ.REQ_CARD_NO | 암호화 |
| PROC_CD                           | (MSF_REQUEST.PROC_CD)           | 마스터 테이블로 통합 |
| CRET_ID                           | CRET_ID                         | 동일 |
| CRET_DTTM                         | CRET_DT                         | |

---

## 6. MSF_CUST_REQUEST_INSR ← MCP_REQUEST (보험 관련 컬럼 분리)

| ASIS (MCP_REQUEST 컬럼)        | TOBE (MSF_CUST_REQUEST_INSR)    | 비고 |
|--------------------------------|---------------------------------|------|
| REQUEST_KEY                    | REQUEST_KEY                     | FK |
| INSR_CD                        | INSR_CD                         | 보험코드 |
| INSR_PROD_CD                   | INSR_PROD_CD                    | 단말보험상품코드 |
| CLAUSE_INSURANCE_FLAG          | CLAUSE_INSURANCE_YN             | _FLAG → _YN |
| CLAUSE_INSR_PROD_FLAG          | CLAUSE_INSR_PROD_YN             | _FLAG → _YN |

---

## 7. DB링크 사용 테이블 목록

ASIS에서 DB링크(`@DL_MSP`, `@DL_MCP`)로 사용하던 테이블을 TOBE에서도 동일하게 사용.

| DB링크              | 테이블                        | 용도                    | TOBE 사용 여부 |
|---------------------|-------------------------------|-------------------------|----------------|
| `@DL_MSP`           | `MSP_INTM_INSR_MST`           | 단말보험 상품 마스터    | ✅ 동일 사용   |
| `@DL_MSP`           | `MSP_INTM_INSR_REL`           | 단말보험 상품-요금제 관계 | ✅ 동일 사용 |
| `@DL_MSP`           | `MSP_JUO_SUB_INFO`            | 가입자 기본정보 (인증용) | ✅ 동일 사용 (로컬은 msp_juo_sub_info) |
| `@DL_MSP`           | `MSP_REQUEST_DTL`             | 요금상품 정보 조회      | ✅ 동일 사용   |
| `@DL_MCP`           | `MCP_REQUEST`                 | M포탈 신청서 (읽기)     | MSF_REQUEST로 대체 (별도 DB) |

> **주의**: `@DL_MSP` DB링크는 운영(Oracle) 환경에서만 동작.
> 로컬 개발 시 `SERVER_NAME=LOCAL` → Mock 응답 사용 (MplatFormSvc 패턴 동일).

---

## 8. 네이밍 규칙 변경 요약

| ASIS 패턴           | TOBE 패턴         | 예시 |
|---------------------|-------------------|------|
| `_CODE`             | `_CD`             | MANAGER_CODE → MANAGER_CD |
| `_FLAG`             | `_YN`             | CLAUSE_TERM_FLAG → CLAUSE_TERM_YN |
| `_TYPE`             | `_TYPE_CD`        | OPER_TYPE → OPER_TYPE_CD |
| `_NAME`             | `_NM`             | CSTMR_NAME → CSTMR_NM |
| `RVISN_ID`          | `AMD_ID`          | 수정자ID |
| `RVISN_DTTM`        | `AMD_DT`          | 수정일시 |
| `SYS_RDATE`         | `CRET_DT`         | 생성일시 |
| `RIP`               | `CRET_IP`         | 등록IP |
