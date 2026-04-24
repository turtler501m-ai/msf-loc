create or replace procedure    P_DEV_DB_DATA_MASKING(   
      O_CODE    OUT VARCHAR2 
    , O_MSG     OUT VARCHAR2 
) 
 
/******************************************************************************* 
 * NAME:       P_DEV_DB_DATA_MASKING 
 * PURPOSE:    개발 DB DATA MASKING
 * 
 * REVISIONS: 
 * Ver        Date        Author           Description 
 * ---------  ----------  ---------------  ------------------------------------ 
 * 1.0        2021-11-24  송정섭           생성
 ******************************************************************************/ 

IS 

BEGIN

    EXECUTE IMMEDIATE'ALTER SESSION FORCE PARALLEL DML PARALLEL 20';

    O_CODE := '0001';
    O_MSG := '가입신청_대리인';

    --가입신청_대리인
    UPDATE 
            MSP_REQUEST_AGENT A
    SET     MINOR_AGENT_NAME     = CASE WHEN MINOR_AGENT_NAME IS NOT NULL THEN '이관고객' ELSE '' END
            , MINOR_AGENT_RRN    = CASE WHEN MINOR_AGENT_RRN IS NOT NULL THEN SUBSTR(CRYPTO_AES256.DEC_AES(MINOR_AGENT_RRN), 1, 7) || '******' ELSE '' END
            , MINOR_AGENT_TEL_FN = CASE WHEN MINOR_AGENT_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , MINOR_AGENT_TEL_MN = CASE WHEN MINOR_AGENT_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , MINOR_AGENT_TEL_RN = CASE WHEN MINOR_AGENT_TEL_RN IS NOT NULL THEN '0000' ELSE '' END

            , JRDCL_AGENT_NAME   = CASE WHEN JRDCL_AGENT_NAME IS NOT NULL THEN '이관고객' ELSE '' END
--            , JRDCL_AGENT_RRN    = CASE WHEN JRDCL_AGENT_RRN IS NOT NULL THEN SUBSTR(CRYPTO_AES256.DEC_AES(JRDCL_AGENT_RRN), 1, 7) || '******' ELSE '' END
            , JRDCL_AGENT_TEL_FN = CASE WHEN JRDCL_AGENT_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , JRDCL_AGENT_TEL_MN = CASE WHEN JRDCL_AGENT_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , JRDCL_AGENT_TEL_RN = CASE WHEN JRDCL_AGENT_TEL_RN IS NOT NULL THEN '0000' ELSE '' END

            , ENTRUST_REQ_NM     = CASE WHEN ENTRUST_REQ_NM IS NOT NULL THEN '이관고객' ELSE '' END
            , ENTRUST_RES_NM     = CASE WHEN ENTRUST_RES_NM IS NOT NULL THEN '이관고객' ELSE '' END
--            , ENTRUST_RES_RRN    = CASE WHEN ENTRUST_RES_RRN IS NOT NULL THEN SUBSTR(CRYPTO_AES256.DEC_AES(ENTRUST_RES_RRN), 1, 7) || '******' ELSE '' END
            , ENTRUST_RES_TEL_FN = CASE WHEN ENTRUST_RES_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , ENTRUST_RES_TEL_MN = CASE WHEN ENTRUST_RES_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , ENTRUST_RES_TEL_RN = CASE WHEN ENTRUST_RES_TEL_RN IS NOT NULL THEN '0000' ELSE '' END
    ;
    commit;

    UPDATE 
            MSP_REQUEST_AGENT A
    SET     MINOR_AGENT_NAME     = CASE WHEN MINOR_AGENT_NAME IS NOT NULL THEN '이관고객' ELSE '' END
            , MINOR_AGENT_RRN    = CASE WHEN MINOR_AGENT_RRN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(MINOR_AGENT_RRN) ELSE '' END
            , MINOR_AGENT_TEL_FN = CASE WHEN MINOR_AGENT_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , MINOR_AGENT_TEL_MN = CASE WHEN MINOR_AGENT_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , MINOR_AGENT_TEL_RN = CASE WHEN MINOR_AGENT_TEL_RN IS NOT NULL THEN '0000' ELSE '' END

            , JRDCL_AGENT_NAME   = CASE WHEN JRDCL_AGENT_NAME IS NOT NULL THEN '이관고객' ELSE '' END
--            , JRDCL_AGENT_RRN    = CASE WHEN JRDCL_AGENT_RRN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(JRDCL_AGENT_RRN) ELSE '' END
            , JRDCL_AGENT_TEL_FN = CASE WHEN JRDCL_AGENT_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , JRDCL_AGENT_TEL_MN = CASE WHEN JRDCL_AGENT_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , JRDCL_AGENT_TEL_RN = CASE WHEN JRDCL_AGENT_TEL_RN IS NOT NULL THEN '0000' ELSE '' END

            , ENTRUST_REQ_NM     = CASE WHEN ENTRUST_REQ_NM IS NOT NULL THEN '이관고객' ELSE '' END
            , ENTRUST_RES_NM     = CASE WHEN ENTRUST_RES_NM IS NOT NULL THEN '이관고객' ELSE '' END
--            , ENTRUST_RES_RRN    = CASE WHEN ENTRUST_RES_RRN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(ENTRUST_RES_RRN) ELSE '' END
            , ENTRUST_RES_TEL_FN = CASE WHEN ENTRUST_RES_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , ENTRUST_RES_TEL_MN = CASE WHEN ENTRUST_RES_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , ENTRUST_RES_TEL_RN = CASE WHEN ENTRUST_RES_TEL_RN IS NOT NULL THEN '0000' ELSE '' END
    ;
    commit;

    O_CODE := '0002';
    O_MSG := '가입신청_명의변경';
    --가입신청_명의변경
    UPDATE 
            MSP_REQUEST_CHANGE A
    SET     NAME_CHANGE_NM       = CASE WHEN NAME_CHANGE_NM IS NOT NULL THEN '이관고객' ELSE '' END
            , NAME_CHANGE_TEL_FN = CASE WHEN NAME_CHANGE_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , NAME_CHANGE_TEL_MN = CASE WHEN NAME_CHANGE_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , NAME_CHANGE_TEL_RN = CASE WHEN NAME_CHANGE_TEL_RN IS NOT NULL THEN '0000' ELSE '' END

            , NAME_CHANGE_RRN    = CASE WHEN NAME_CHANGE_RRN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(NAME_CHANGE_RRN), 1, 7) || '******') ELSE '' END
    ;
    commit;

    O_CODE := '0003';
    O_MSG := '가입신청_고객정보';
    --가입신청_고객정보
    UPDATE 
            MSP_REQUEST_CSTMR A
    SET     CSTMR_NAME              = CASE WHEN CSTMR_NAME IS NOT NULL THEN '이관고객' ELSE '' END
            , CSTMR_NATIVE_RRN      = CASE WHEN CSTMR_NATIVE_RRN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(CSTMR_NATIVE_RRN), 1, 7) || '******') ELSE '' END
            , CSTMR_FOREIGNER_PN    = ''
            , CSTMR_FOREIGNER_RRN   = CASE WHEN CSTMR_FOREIGNER_RRN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(CSTMR_FOREIGNER_RRN), 1, 7) || '******') ELSE '' END
            , CSTMR_PRIVATE_CNAME   = CASE WHEN CSTMR_PRIVATE_CNAME IS NOT NULL THEN '이관고객' ELSE '' END
            , CSTMR_JURIDICAL_CNAME = CASE WHEN CSTMR_JURIDICAL_CNAME IS NOT NULL THEN '이관고객' ELSE '' END
            , CSTMR_PRIVATE_NUMBER  = ''
            , CSTMR_JURIDICAL_RRN   = CASE WHEN CSTMR_JURIDICAL_RRN IS NOT NULL THEN SUBSTR(CSTMR_JURIDICAL_RRN, 1, 7) || '******' ELSE '' END
            , CSTMR_JURIDICAL_NUMBER = ''
            , CSTMR_TEL_FN          = CASE WHEN CSTMR_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , CSTMR_TEL_MN          = CASE WHEN CSTMR_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , CSTMR_TEL_RN          = CASE WHEN CSTMR_TEL_RN IS NOT NULL THEN '0000' ELSE '' END

            , CSTMR_MOBILE_FN       = CASE WHEN CSTMR_MOBILE_FN IS NOT NULL THEN '000' ELSE '' END
            , CSTMR_MOBILE_MN       = CASE WHEN CSTMR_MOBILE_MN IS NOT NULL THEN '0000' ELSE '' END
            , CSTMR_MOBILE_RN       = CASE WHEN CSTMR_MOBILE_RN IS NOT NULL THEN '0000' ELSE '' END

            , CSTMR_ADDR            = SUBSTR(CSTMR_ADDR,1,15)
            , CSTMR_ADDR_DTL        = ''

            , CSTMR_MAIL            = ''
            , CSTMR_RECEIVE_TEL_FN  = CASE WHEN CSTMR_RECEIVE_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , CSTMR_RECEIVE_TEL_NM  = CASE WHEN CSTMR_RECEIVE_TEL_NM IS NOT NULL THEN '0000' ELSE '' END
            , CSTMR_RECEIVE_TEL_RN  = CASE WHEN CSTMR_RECEIVE_TEL_RN IS NOT NULL THEN '0000' ELSE '' END

            , OTHERS_PAYMENT_NM     = CASE WHEN OTHERS_PAYMENT_NM IS NOT NULL THEN '이관고객' ELSE '' END
            , OTHERS_PAYMENT_RRN    = CASE WHEN OTHERS_PAYMENT_RRN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(OTHERS_PAYMENT_RRN), 1, 7) || '******') ELSE '' END
            , OTHERS_PAYMENT_RNM    = CASE WHEN OTHERS_PAYMENT_NM IS NOT NULL THEN '이관고객' ELSE '' END
    ;
    commit;

    O_CODE := '0004';
    O_MSG := '가입신청_배송정보';
    --가입신청_배송정보
    UPDATE  
            MSP_REQUEST_DLVRY A
    SET     DLVRY_NAME         = CASE WHEN DLVRY_NAME IS NOT NULL THEN '이관고객' ELSE '' END
            , DLVRY_TEL_FN     = CASE WHEN DLVRY_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , DLVRY_TEL_MN     = CASE WHEN DLVRY_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , DLVRY_TEL_RN     = CASE WHEN DLVRY_TEL_RN IS NOT NULL THEN '0000' ELSE '' END

            , DLVRY_MOBILE_FN  = CASE WHEN DLVRY_MOBILE_FN IS NOT NULL THEN '000' ELSE '' END
            , DLVRY_MOBILE_MN  = CASE WHEN DLVRY_MOBILE_MN IS NOT NULL THEN '0000' ELSE '' END
            , DLVRY_MOBILE_RN  = CASE WHEN DLVRY_MOBILE_RN IS NOT NULL THEN '0000' ELSE '' END

            , DLVRY_ADDR = SUBSTR(DLVRY_ADDR,1,15)
            , DLVRY_ADDR_DTL   = ''
    ;
    commit;

    O_CODE := '0005';
    O_MSG := '계약 정보';
    --계약 정보
    UPDATE  
            MSP_REQUEST_DTL A
    SET     MINOR_AGENT_NAME  = CASE WHEN MINOR_AGENT_NAME IS NOT NULL THEN '이관고객' ELSE '' END
            , MINOR_AGENT_TEL = CASE WHEN MINOR_AGENT_TEL IS NOT NULL THEN '00000000000' ELSE '' END
    ;
    commit;

    O_CODE := '0006';
    O_MSG := '가입신청_번호이동정보';
    --가입신청_번호이동정보
    UPDATE  
            MSP_REQUEST_MOVE A
    SET      MOVE_MOBILE_FN   = CASE WHEN MOVE_MOBILE_FN IS NOT NULL THEN '000' ELSE '' END
            , MOVE_MOBILE_MN  = CASE WHEN MOVE_MOBILE_MN IS NOT NULL THEN '0000' ELSE '' END
            , MOVE_MOBILE_RN  = CASE WHEN MOVE_MOBILE_RN IS NOT NULL THEN '0000' ELSE '' END

            , REQ_GUIDE_FN    = CASE WHEN REQ_GUIDE_FN IS NOT NULL THEN '000' ELSE '' END
            , REQ_GUIDE_RN    = CASE WHEN REQ_GUIDE_RN IS NOT NULL THEN '0000' ELSE '' END
            , REQ_GUIDE_MN    = CASE WHEN REQ_GUIDE_MN IS NOT NULL THEN '0000' ELSE '' END
    ;
    commit;

    O_CODE := '0007';
    O_MSG := '가입신청_청구정보';
    --가입신청_청구정보
    UPDATE  
            MSP_REQUEST_REQ A
    SET     REQ_ACCOUNT_NAME       = CASE WHEN REQ_ACCOUNT_NAME IS NOT NULL THEN '이관고객' ELSE '' END
            , REQ_ACCOUNT_RRN      = CASE WHEN REQ_ACCOUNT_RRN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(REQ_ACCOUNT_RRN), 1, 7) || '******') ELSE '' END
            , REQ_ACCOUNT_NUMBER   = ''
            , REQ_CARD_NAME        = CASE WHEN REQ_CARD_NAME IS NOT NULL THEN '이관고객' ELSE '' END
            , REQ_CARD_RRN         = CASE WHEN REQ_CARD_RRN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(REQ_CARD_RRN), 1, 7) || '******') ELSE '' END
            , REQ_CARD_NO          = ''

            , REQ_PAY_OTHER_TEL_FN = CASE WHEN REQ_PAY_OTHER_TEL_FN IS NOT NULL THEN '000' ELSE '' END
            , REQ_PAY_OTHER_TEL_MN = CASE WHEN REQ_PAY_OTHER_TEL_MN IS NOT NULL THEN '0000' ELSE '' END
            , REQ_PAY_OTHER_TEL_RN = CASE WHEN REQ_PAY_OTHER_TEL_RN IS NOT NULL THEN '0000' ELSE '' END
    ;
    commit;


    O_CODE := '0009';
    O_MSG := '서식지 정보 EMV_SCAN_MST';

    update 
            EMV_SCAN_MST set
    CUST_NM = DECODE(CUST_NM,NULL,NULL,'이관고객')
    ,DOC_TITLE = DECODE(DOC_TITLE,NULL,NULL,'이관고객_가입')
    ;
    commit;

    O_CODE := '0010';
    O_MSG := '계약 현행화 정보';
    --계약 현행화 정보
    UPDATE  
            MSP_JUO_SUB_INFO A
    SET     SUBSCRIBER_NO          = '00000000000'
            , USER_SSN               = CASE WHEN USER_SSN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(USER_SSN), 1, 7) || '******') ELSE '' END
            , SUB_ADR_PRIMARY_LN   = SUBSTR(SUB_ADR_PRIMARY_LN,1,15)
            , SUB_ADR_SECONDARY_LN = ''
            , SUB_LINK_NAME        = CASE WHEN SUB_LINK_NAME IS NOT NULL THEN '이관고객' ELSE '' END
    ;
    commit;

    O_CODE := '0011';
    O_MSG := '계약 현행화 이력 정보';
    --계약 현행화 이력 정보
    UPDATE  
            MSP_JUO_SUB_INFO_HIST A
    SET     SUBSCRIBER_NO          = '00000000000'
            , USER_SSN               = CASE WHEN USER_SSN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(USER_SSN), 1, 7) || '******') ELSE '' END
            , SUB_ADR_PRIMARY_LN   = SUBSTR(SUB_ADR_PRIMARY_LN,1,15)
            , SUB_ADR_SECONDARY_LN = ''
            , SUB_LINK_NAME        = CASE WHEN SUB_LINK_NAME IS NOT NULL THEN '이관고객' ELSE '' END
    ;
    commit;


    -------------------------------------------------------------------

    O_CODE := '0012';
    O_MSG := '고객 현행화 정보';
    -- 다회선 계약 유무 체크 
    --고객 현행화 정보
    UPDATE  
            MSP_JUO_CUS_INFO A
    SET     CUSTOMER_SSN                = CASE WHEN CUSTOMER_SSN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(CUSTOMER_SSN), 1, 7) || '******') ELSE '' END
            , DRIVR_LICNS_NO            = ''
            , TAX_ID                    = ''
            , CUSTOMER_ADR_PRIMARY_LN   = SUBSTR(CUSTOMER_ADR_PRIMARY_LN,1,15)
            , CUSTOMER_ADR_SECONDARY_LN = ''
            , CUSTOMER_LINK_NAME        = CASE WHEN CUSTOMER_LINK_NAME IS NOT NULL THEN '이관고객' ELSE '' END
            , PREV_CUSTOMER_SSN         = CASE WHEN PREV_CUSTOMER_SSN IS NOT NULL THEN SUBSTR(PREV_CUSTOMER_SSN,1, 7) || '******' ELSE '' END
            , PREV_CUSTOMER_NAME        = CASE WHEN PREV_CUSTOMER_NAME IS NOT NULL THEN '이관고객' ELSE '' END
    ;
    commit;


    O_CODE := '0013';
    O_MSG := '고객 현행화 이력정보';
    --고객 현행화 이력정보
    UPDATE  
            MSP_JUO_CUS_INFO_HIST A
    SET     CUSTOMER_SSN                = CASE WHEN CUSTOMER_SSN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(CUSTOMER_SSN), 1, 7) || '******') ELSE '' END
            , DRIVR_LICNS_NO            = ''
            , TAX_ID                    = ''
            , CUSTOMER_ADR_PRIMARY_LN   = SUBSTR(CUSTOMER_ADR_PRIMARY_LN,1,15)
            , CUSTOMER_ADR_SECONDARY_LN = ''
            , CUSTOMER_LINK_NAME        = CASE WHEN CUSTOMER_LINK_NAME IS NOT NULL THEN '이관고객' ELSE '' END
            , PREV_CUSTOMER_SSN         = CASE WHEN PREV_CUSTOMER_SSN IS NOT NULL THEN SUBSTR(PREV_CUSTOMER_SSN,1, 7) || '******' ELSE '' END
            , PREV_CUSTOMER_NAME        = CASE WHEN PREV_CUSTOMER_NAME IS NOT NULL THEN '이관고객' ELSE '' END
    ;
    commit;
    
    -------------------------------------------------------------------------------

    O_CODE := '0014';
    O_MSG := '청구 현황 정보';
    --청구 현황 정보
    UPDATE  
            MSP_JUO_BAN_INFO A
    SET     BANK_ACCT_HOLDER_ID      = CASE WHEN BANK_ACCT_HOLDER_ID IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(BANK_ACCT_HOLDER_ID), 1, 7) || '******') ELSE '' END
            , BAN_ADR_PRIMARY_LN     = SUBSTR(BAN_ADR_PRIMARY_LN,1,15)
            , BAN_ADR_SECONDARY_LN = ''
            , BAN_LINK_NAME        = CASE WHEN BAN_LINK_NAME IS NOT NULL THEN '이관고객' ELSE '' END
    ;
    commit;

    O_CODE := '0015';
    O_MSG := '청구 현황 이력정보';
    --청구 현황 이력정보
    UPDATE  
            MSP_JUO_BAN_INFO_HIST A
    SET     BANK_ACCT_HOLDER_ID      = CASE WHEN BANK_ACCT_HOLDER_ID IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(BANK_ACCT_HOLDER_ID), 1, 7) || '******') ELSE '' END
            , BAN_ADR_PRIMARY_LN     = SUBSTR(BAN_ADR_PRIMARY_LN,1,15)
            , BAN_ADR_SECONDARY_LN = ''
            , BAN_LINK_NAME        = CASE WHEN BAN_LINK_NAME IS NOT NULL THEN '이관고객' ELSE '' END
    ;
    commit;

    O_CODE := '0016';
    O_MSG := '단말보험연동전송정보';
    --단말보험 해지자 masking

    --단말보험연동전송정보
    /* transferMapper.updateIntmInsrIfOut */
    UPDATE 
            	MSP_INTM_INSR_IF_OUT
       SET 	CTN = '00000000000',
            CUST_NM = '이관고객',
            BAN_ADDR = NULL,
            MINOR_AGENT_NAME = CASE WHEN MINOR_AGENT_NAME IS NOT NULL THEN '이관고객' ELSE NULL END
    ;
    commit;

    O_CODE := '0017';
    O_MSG := '단말보험보상연동정보';
    --단말보험보상연동정보
    UPDATE 
            MSP_INTM_INSR_CMPN_MST_IF
       SET 	CTN = '00000000000',
            CUST_NM = '이관고객'
    ;
    commit;

    O_CODE := '0018';
    O_MSG := '단말보험보상연동정보tmp';

    update 
            MSP_INTM_INSR_CMPN_MST_IF_TMP 
       SET 	CTN = '00000000000'
            ,CUST_NM = '이관고객'
            ,DPST_NM = '이관고객'
            ,ACNT_NO = ''
    ;
    commit;

    O_CODE := '0019';
    O_MSG := '단말보험가입자';

    --단말보험가입자
    UPDATE 
            MSP_INTM_INSR_MEMBER A
       SET 	CTN = '00000000000',
            CUST_NM = '해지고객'
    ;

    commit;
    ---------------------------------------------------------------------------------------------------------------------------------------

    O_CODE := '0020';
    O_MSG := '직권해지대상자료';

    update 
            CLS_AHRT_TMNT_TRGT_DATA set
    CUST_NM = DECODE(CUST_NM,NULL,NULL,'이관고객')
    ,BNKACN_NO = ''
    ,ADDRESS = substr(ADDRESS,1,15)
    ,EMP_NM = DECODE(EMP_NM,NULL,NULL,'이관고객')
    ,TEL_NO_1 = DECODE(TEL_NO_1,NULL,NULL,'00000000000')
    ;
    commit;

    O_CODE := '0021';
    O_MSG := '세금계산서정보';

    update 
            CLS_TAXBILL_INFO set
    SUPLIR_BIZ_REG_NUM = ''
    ,SUPLIR_RPRSEN_NM = DECODE(SUPLIR_RPRSEN_NM,NULL,NULL,'이관고객')
    ,SUPLIR_NM = DECODE(SUPLIR_NM,NULL,NULL,'이관고객')
    ,SUPLIR_ADDR = substr(SUPLIR_ADDR,1,15)
    ,SPPLD_BIZ_REG_NUM = ''
    ,SPPLD_RPRSEN_NM = DECODE(SPPLD_RPRSEN_NM,NULL,NULL,'이관고객')
    ,SPPLD_NM = DECODE(SPPLD_NM,NULL,NULL,'이관고객')
    ,SPPLD_ADDR =  substr(SPPLD_ADDR,1,15)
    ;
    commit;

    O_CODE := '0022';
    O_MSG := '엑셀 다운로드 내역';

    update 
            CMN_EXCL_DNLD_HST set
    USR_NM = DECODE(USR_NM,NULL,NULL,'이관고객')
    ;
    commit;

    O_CODE := '0023';
    O_MSG := '[금결원]증거파일';

    update 
            CMN_KFTC_EVIDENCE set
    DPST_PRSN_NM = DECODE(DPST_PRSN_NM,NULL,NULL,'이관고객')
    ;
    commit;

    O_CODE := '0024';
    O_MSG := '[금결원]증거파일 이력';

    update 
            CMN_KFTC_NON_BIND_FILE_HST set
    DPST_PRSN_NM = DECODE(DPST_PRSN_NM,NULL,NULL,'이관고객')
    ,DPST_ACNT_NUM = ''
    ;
    commit;


    O_CODE := '0025';
    O_MSG := 'SGI단말기보상청구 단말계약';

    update 
            CMN_SGIH_DMND set
    CSTMR_NAME = DECODE(CSTMR_NAME,NULL,NULL,'이관고객')
    ,INDV_ENTR_NO = ''
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,BILL_ADDR_SUBSCRIBER_NO = DECODE(BILL_ADDR_SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,RESIDENCE_ADDR = SUBSTR(RESIDENCE_ADDR,1,15)
    ,RESIDENCE_ADDR_DTL = ''
    ,BILL_ADDR = SUBSTR(BILL_ADDR,1,15)
    ,BILL_ADDR_DTL = ''
    ,minor_name = decode(minor_name,null,null,'이관고객')
    --,minor_ssn = substr(minor_ssn,1,7)
    ;
    commit;


    O_CODE := '0026';
    O_MSG := 'SGI단말기보상청구 과납내역';

    update 
            CMN_SGIH_OVERPYM set
    CSTMR_NAME = DECODE(CSTMR_NAME,NULL,NULL,'이관고객')
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,USER_SSN =  DECODE(USER_SSN,NULL,NULL,  substr(USER_SSN,1, 7) || '******')
    ;
    commit;

    O_CODE := '0027';
    O_MSG := 'SGI단말기보상청구 불능처리';

    update 
            CMN_SGIH_REJECT set
    CSTMR_NAME = DECODE(CSTMR_NAME,NULL,NULL,'이관고객')
    ,SUBSCRIBER_NO =  DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ;
    commit;

    O_CODE := '0028';
    O_MSG := '약관발송대상자관리';

    update 
            CMN_TERMS_SEND_MST set
    NAME = DECODE(NAME,NULL,NULL,'이관고객')
    ,MOBILE_NO = DECODE(MOBILE_NO,NULL,NULL,'00000000000')
    ,EMAIL = ''
    ;
    commit;

    O_CODE := '0029';
    O_MSG := 'CMN_TERMS_SMS_SEND_MST';

    update 
            CMN_TERMS_SMS_SEND_MST set
    NAME = DECODE(NAME,NULL,NULL,'이관고객')
    ,MOBILE_NO = DECODE(MOBILE_NO,NULL,NULL,'00000000000')
    ;
    commit;

    O_CODE := '0030';
    O_MSG := '사용자 이력';

    /* 핸드폰 전화번호 제거 로직 제외 */
    update 
            CMN_USR_HST set
    usr_nm =  DECODE(usr_nm,NULL,NULL,'이관고객')
    ;
    commit;

    O_CODE := '0031';
    O_MSG := '사용자';

    /* 핸드폰 전화번호 제거 로직 제외 */
    update 
            CMN_USR_MST set
    usr_nm = DECODE(usr_nm,NULL,NULL,'이관고객')
    ;
    commit;


    O_CODE := '0032';
    O_MSG := '일반회원해지자관리';
    --일반회원해지자관리
    update 
            CMN_WEBUSER_CAN_MST set
    NAME = DECODE(NAME,NULL,NULL,'이관고객')
    ,MOBILE_NO = DECODE(MOBILE_NO,NULL,NULL,'00000000000')
    ,ADDRESS = SUBSTR(ADDRESS,1,15)
    ,ADDRESS_DTL = ''
    ;


    commit;


    O_CODE := '0033';
    O_MSG := 'MVNO사용자정보';

    update 
            MDS_USR_INFO set
    USER_NM = DECODE(USER_NM,NULL,NULL,'이관고객')
    ;
    commit;

    O_CODE := '0034';
    O_MSG := '채권유동화';

    update 
            MSP_BOND_ASSET set
    USER_SSN = CASE WHEN USER_SSN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(USER_SSN), 1, 7) || '******') ELSE '' END
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,SUB_LINK_NAME = DECODE(SUB_LINK_NAME,NULL,NULL,'이관고객')
    ,SUB_ADR_PRIMARY_LN = substr(SUB_ADR_PRIMARY_LN,1,15)
    ,TAX_ID = ''
    ;
    commit;


    O_CODE := '0035';
    O_MSG := 'MSP_BOND_ASSET_RECALC';

    update 
            MSP_BOND_ASSET_RECALC A set
    SUB_LINK_NAME = DECODE(SUB_LINK_NAME,NULL,NULL,'이관고객')
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,USER_SSN = CASE WHEN USER_SSN IS NOT NULL THEN 'um4GviW+xpJ3fgaLCV3a+A==' ELSE '' END
    ,TAX_ID = ''
    ,SUB_ADR_PRIMARY_LN = substr(SUB_ADR_PRIMARY_LN,1,15)
    ;

    commit;

    O_CODE := '0036';
    O_MSG := '신용정보 동의서';

    update 
            MSP_CREDIT_AGREEMENT set
    CUST_NAME = DECODE(CUST_NAME,NULL,NULL,'이관고객')
    ,REG_NAME =  DECODE(REG_NAME,NULL,NULL,'이관고객')
    ,TEL_NO = DECODE(TEL_NO,NULL,NULL,'00000000000')
    ;
    commit;

    O_CODE := '0037';
    O_MSG := 'MSP_DIRDVRY_USIM_MST';

    update 
            MSP_DIRDVRY_USIM_MST set
    DVRY_ADR_PRIMARY_LN = substr(DVRY_ADR_PRIMARY_LN,1,15)
    ,DVRY_ADR_SECONDARY_LN = ''
    ,RCV_CUST_CTN = DECODE(RCV_CUST_CTN,NULL,NULL,'00000000000')
    ;
    commit;


    O_CODE := '0038';
    O_MSG := '사은품 프로모션 대상 고객';

    update 
            MSP_GIFT_PRMT_CUSTOMER set
    SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,TEL_FN1 = DECODE(TEL_FN1,NULL,NULL,'000')
    ,TEL_MN1 = DECODE(TEL_MN1,NULL,NULL,'0000')
    ,TEL_RN1 = DECODE(TEL_RN1,NULL,NULL,'0000')
    ,TEL_FN2 = DECODE(TEL_FN2,NULL,NULL,'000')
    ,TEL_MN2 = DECODE(TEL_MN2,NULL,NULL,'0000')
    ,TEL_RN2 = DECODE(TEL_RN2,NULL,NULL,'0000')
    ,ADDR = SUBSTR(ADDR,1,15)
    ,ADDR_DTL = ''
    ,SUB_LINK_NAME = DECODE(SUB_LINK_NAME,NULL,NULL,'이관고객')
    ,MNG_NM = DECODE(MNG_NM,NULL,NULL,'이관고객')
    ;

    commit;



    O_CODE := '0039';
    O_MSG := '단말보험가입자';

    update 
            MSP_INTM_INSR_MEMBER set
    CUST_NM = DECODE(CUST_NM,NULL,NULL,'이관고객')
    ,CTN = DECODE(CTN,NULL,NULL,'00000000000')
    ;
    commit;

    O_CODE := '0040';
    O_MSG := '통신자료제공요청삭제이력';

    update 
            MSP_INVST_REQ_HIST set
    CSTMR_NM = DECODE(CSTMR_NM,NULL,NULL,'이관고객')
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,USER_SSN = CASE WHEN USER_SSN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(USER_SSN), 1, 7) || '******') ELSE '' END
    ,REQ_USER = DECODE(REQ_USER,NULL,NULL,'이관고객')
    ,APP_USER = DECODE(APP_USER,NULL,NULL,'이관고객')
    ,CSTMR_ADDR = SUBSTR(CSTMR_ADDR,1,15)
    ,EMAIL = ''
    ;
    commit;


    O_CODE := '0041';
    O_MSG := '통신자료제공요청';

    update 
            MSP_INVST_REQ_MST set
    CSTMR_NM = DECODE(CSTMR_NM,NULL,NULL,'이관고객')
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,USER_SSN = CASE WHEN USER_SSN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(USER_SSN), 1, 7) || '******') ELSE '' END
    ,REQ_USER = DECODE(REQ_USER,NULL,NULL,'이관고객')
    ,APP_USER = DECODE(APP_USER,NULL,NULL,'이관고객')
    ,CSTMR_ADDR = SUBSTR(CSTMR_ADDR,1,15)
    ,EMAIL = ''
    ;
    commit;


    commit;


    O_CODE := '0042';
    O_MSG := 'USIM발주사별배송지정보';

    UPDATE 
            MSP_M2M_ADDR SET
    ADDR_DTL  = ''
    ,ADDR = SUBSTR(ADDR,1,15)
    ,MNG_NM = DECODE(MNG_NM,NULL,NULL,'이관고객')
    ,TEL_FN1 = DECODE(TEL_FN1,NULL,NULL,'000')
    ,TEL_MN1 = DECODE(TEL_MN1,NULL,NULL,'0000')
    ,TEL_RN1 = DECODE(TEL_RN1,NULL,NULL,'0000')
    ,TEL_FN2 = DECODE(TEL_FN2,NULL,NULL,'000')
    ,TEL_MN2 = DECODE(TEL_MN2,NULL,NULL,'0000')
    ,TEL_RN2 = DECODE(TEL_RN2,NULL,NULL,'0000')
    ;
    commit;


    O_CODE := '0043';
    O_MSG := 'USIM주문정보';

    UPDATE 
            MSP_M2M_ORD_MST SET
    MNG_NM =   DECODE(MNG_NM,NULL,NULL,'이관고객')
    ,ADDR_DTL = ''
    ,ADDR = SUBSTR(ADDR,1,15)
    ,TEL_FN1 = DECODE(TEL_FN1,NULL,NULL,'000')
    ,TEL_MN1 = DECODE(TEL_MN1,NULL,NULL,'0000')
    ,TEL_RN1 = DECODE(TEL_RN1,NULL,NULL,'0000')
    ,TEL_FN2 = DECODE(TEL_FN2,NULL,NULL,'000')
    ,TEL_MN2 = DECODE(TEL_MN2,NULL,NULL,'0000')
    ,TEL_RN2 = DECODE(TEL_RN2,NULL,NULL,'0000')
    ,DLV_TEL_NO = DECODE(DLV_TEL_NO,NULL,NULL,'00000000000')
    ;

    commit;


    O_CODE := '0044';
    O_MSG := '미래에셋 광고제휴';

    UPDATE 
            MSP_MIRAE_ASSET_IF SET
    CUST_NM =  DECODE(CUST_NM,NULL,NULL,'이관고객')
    ;
    commit;


    O_CODE := '0045';
    O_MSG := '보험가입자연동정보';

    UPDATE 
            MSP_PARTNER_DONGBU_01 SET
    CUST_NM = DECODE(CUST_NM,NULL,NULL,'이관고객')
    ,CTN = DECODE(CTN,NULL,NULL,'00000000000')
    ,MINOR_AGENT_NM = DECODE(MINOR_AGENT_NM,NULL,NULL,'이관고객')
    ,MINOR_AGENT_TEL = DECODE(MINOR_AGENT_TEL,NULL,NULL,'00000000000')
    ;
    commit;

    O_CODE := '0046';
    O_MSG := '제휴사 관리';

    UPDATE 
            MSP_PARTNER_MST SET
    MANAGER_NM = DECODE(MANAGER_NM,NULL,NULL,'이관고객')
    ;
    commit;


    O_CODE := '0047';
    O_MSG := '해지이력테이블';

    UPDATE 
            MSP_REENTRANCE_MST SET
    SUB_LINK_NAME = DECODE(SUB_LINK_NAME,NULL,NULL,'이관고객')
    ,C_SUB_LINK_NAME = DECODE(C_SUB_LINK_NAME,NULL,NULL,'이관고객')
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ;




    commit;

    O_CODE := '0048';
    O_MSG := 'SMS전화번호그룹상세';

    UPDATE 
            MSP_SMS_PHONE_GRP_DTL SET
    USR_NM = DECODE(USR_NM,NULL,NULL,'이관고객')
    ,MBLPHN_NUM = LPAD(ROWNUM,11,'0')
    ;
    commit;


    O_CODE := '0049';
    O_MSG := 'SMS전화번호그룹상세';

    UPDATE 
            MSP_SMS_PHONE_GRP_DTL_HST SET
    USR_NM = DECODE(USR_NM,NULL,NULL,'이관고객')
    ,MBLPHN_NUM =  DECODE(MBLPHN_NUM,NULL,NULL,'00000000000')
    ;
    commit;

    O_CODE := '0050';
    O_MSG := '사업자이관동의MST';

    UPDATE 
            MSP_SO_TRNS_AGRM_MST SET
    CUST_NM = DECODE(CUST_NM,NULL,NULL,'이관고객')
    ,SVC_TEL_NO =  DECODE(SVC_TEL_NO,NULL,NULL,'00000000000')
    ;

    commit;

    O_CODE := '0051';
    O_MSG := '담보관리';

    update 
            ORG_MRTG_MGMT_MST set
    OWNR_NM =  DECODE(OWNR_NM,NULL,NULL,'이관고객')
    ,BLD_OWNR =  DECODE(BLD_OWNR,NULL,NULL,'이관고객')
    ,DPSTR =  DECODE(DPSTR,NULL,NULL,'이관고객')
    ,PRPSEN_NM =  DECODE(PRPSEN_NM,NULL,NULL,'이관고객')
    ,PRRTY_NM1 =  DECODE(PRRTY_NM1,NULL,NULL,'이관고객')
    ,PRRTY_NM2 =  DECODE(PRRTY_NM2,NULL,NULL,'이관고객')
    ,PRRTY_NM3 =  DECODE(PRRTY_NM3,NULL,NULL,'이관고객')
    ,ACNT_NUM = ''
    ,DPSTR_ORD_REG_NUM = DECODE(DPSTR_ORD_REG_NUM,NULL,NULL,SUBSTR(DPSTR_ORD_REG_NUM,1,6)||'*******')
    ;
    commit;

    O_CODE := '0052';
    O_MSG := '조직정보';

    update 
            ORG_ORGN_CHG_HST set
    ARNO_ADR_BAS_SBST = substr(ARNO_ADR_BAS_SBST,1,15)
    ,ARNO_ADR_DTL_SBST =  ''
    ,ARNO_ADR_BJ_NM =  ''
    ,ROADN_ADR_BAS_SBST = substr(ROADN_ADR_BAS_SBST,1,15)
    ,ROADN_ADR_DTL_SBST =  ''
    ,ROADN_ADR_ROAD_NM =  substr(ROADN_ADR_ROAD_NM,1,15)
    ,ADR_RFRN_SBST = ''
    ;
    commit;

    O_CODE := '0053';
    O_MSG := '조직정보이력';

    update 
            ORG_ORGN_INFO_HST set 
    RPRSEN_NM =  DECODE(RPRSEN_NM,NULL,NULL,'이관고객')
    ,RPRSEN_RRNUM = CASE WHEN RPRSEN_RRNUM IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(RPRSEN_RRNUM), 1, 7) || '******') ELSE '' END
    ,TELNUM = DECODE(TELNUM,NULL,NULL,'00000000000')
    ,FAX = DECODE(FAX,NULL,NULL,'00000000000')
    ,ADDR1 = SUBSTR(ADDR1,1,15)
    ,ADDR2 = ''
    ,RESPN_PRSN_NM = DECODE(RESPN_PRSN_NM,NULL,NULL,'이관고객')
    ,BIZ_REG_NUM = ''
    ,CORP_REG_NUM = ''
    ,RESPN_PRSN_NUM = DECODE(RESPN_PRSN_NUM,NULL,NULL,'이관고객')
    ,RESPN_PRSN_MBLPHN = DECODE(RESPN_PRSN_MBLPHN,NULL,NULL,'00000000000')
    ,RESPN_PRSN_EMAIL = ''
    ;
    commit;

    O_CODE := '0054';
    O_MSG := '조직정보';

    update 
            ORG_ORGN_INFO_MST set 
    RPRSEN_NM =  DECODE(RPRSEN_NM,NULL,NULL,'이관고객')
    ,RPRSEN_RRNUM = CASE WHEN RPRSEN_RRNUM IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(RPRSEN_RRNUM), 1, 7) || '******') ELSE '' END
    ,TELNUM = DECODE(TELNUM,NULL,NULL,'00000000000')
    ,FAX = DECODE(FAX,NULL,NULL,'00000000000')
    ,ADDR1 = SUBSTR(ADDR1,1,15)
    ,ADDR2 = ''
    ,RESPN_PRSN_NM = DECODE(RESPN_PRSN_NM,NULL,NULL,'이관고객')
    ,BIZ_REG_NUM = ''
    ,CORP_REG_NUM = ''
    ,RESPN_PRSN_NUM = DECODE(RESPN_PRSN_NUM,NULL,NULL,'이관고객')
    ,RESPN_PRSN_MBLPHN = DECODE(RESPN_PRSN_MBLPHN,NULL,NULL,'00000000000')
    ,RESPN_PRSN_EMAIL = ''
    ;
    commit;

    O_CODE := '0055';
    O_MSG := 'ORG_ORGN_INFO_MST_20190717';

    update 
            ORG_ORGN_INFO_MST_20190717 set 
    RPRSEN_NM =  DECODE(RPRSEN_NM,NULL,NULL,'이관고객')
    ,RPRSEN_RRNUM = CASE WHEN RPRSEN_RRNUM IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(RPRSEN_RRNUM), 1, 7) || '******') ELSE '' END
    ,TELNUM = DECODE(TELNUM,NULL,NULL,'00000000000')
    ,FAX = DECODE(FAX,NULL,NULL,'00000000000')
    ,ADDR1 = SUBSTR(ADDR1,1,15)
    ,ADDR2 = ''
    ,RESPN_PRSN_NM = DECODE(RESPN_PRSN_NM,NULL,NULL,'이관고객')
    ,BIZ_REG_NUM = ''
    ,CORP_REG_NUM = ''
    ,RESPN_PRSN_NUM = DECODE(RESPN_PRSN_NUM,NULL,NULL,'이관고객')
    ,RESPN_PRSN_MBLPHN = DECODE(RESPN_PRSN_MBLPHN,NULL,NULL,'00000000000')
    ,RESPN_PRSN_EMAIL = ''
    ;

    commit;

    --drop table ORG_ORGN_INFO_MST_20191209;

    --drop table ORG_ORGN_INFO_MST_TEMP;


    O_CODE := '0056';
    O_MSG := '판매정책';

    update 
            ORG_SALE_PLCY_MST set
    ENFRC_CNFM_PRSN = DECODE(ENFRC_CNFM_PRSN,NULL,NULL,'이관고객')
    ;
    commit;

    O_CODE := '0057';
    O_MSG := '개통및 개통월기준 고객정보테이블';

    update 
            PPS_AGENT_STM_OPEN set
    SUB_LINK_NAME = DECODE(SUB_LINK_NAME,NULL,NULL,'이관고객')
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,USER_SSN = CASE WHEN USER_SSN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(USER_SSN), 1, 7) || '******') ELSE '' END
    ;
    commit;

    O_CODE := '0058';
    O_MSG := '해지요청내역';

    update 
            PPS_CAN_USR_QUEUE set
    SUB_LINK_NAME = DECODE(SUB_LINK_NAME,NULL,NULL,'이관고객')
    ,SUBSCRIBER_NO= DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ;
    commit;


    O_CODE := '0059';
    O_MSG := 'PPS_CUSTOMER_DIARY';

    update 
            PPS_CUSTOMER_DIARY set
    REQ_USER_NAME = DECODE(REQ_USER_NAME,NULL,NULL,'이관고객')
    ;
    commit;


    O_CODE := '0060';
    O_MSG := 'JUO관련 테이블 INSERT나 UPDATE시 역추적을 위해 HISTORY저장하는 테이블';

    update 
            PPS_CUSTOMER_KT set 
    SUBSCRIBER_NO_OLD = DECODE(SUBSCRIBER_NO_OLD,NULL,NULL,'00000000000')
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,BAN_ADR_PRIMARY_LN = SUBSTR(BAN_ADR_PRIMARY_LN,1,15)
    ,BAN_ADR_SECONDARY_LN =  ''
    ,BAN_LINK_NAME = DECODE(BAN_LINK_NAME,NULL,NULL,'이관고객')
    ,BANK_ACCT_HOLDER_ID = DECODE(BANK_ACCT_HOLDER_ID,NULL,NULL,'561**********')
    ,CUSTOMER_ADR_PRIMARY_LN = SUBSTR(BAN_ADR_PRIMARY_LN,1,15)
    ,CUSTOMER_ADR_SECONDARY_LN = ''
    ,CUSTOMER_SSN = DECODE(CUSTOMER_SSN,NULL,NULL,substr(CUSTOMER_SSN,1,7)||'******')
    ,CUSTOMER_LINK_NAME = DECODE(CUSTOMER_LINK_NAME,NULL,NULL,'이관고객')
    ,DRIVR_LICNS_NO = ''
    ,SUB_ADR_PRIMARY_LN = SUBSTR(SUB_ADR_PRIMARY_LN,1,15)
    ,SUB_ADR_SECONDARY_LN = ''
    ,SUB_LINK_NAME = DECODE(SUB_LINK_NAME,NULL,NULL,'이관고객')
    ,TAX_ID = ''
    ,USER_SSN = DECODE(USER_SSN,NULL,NULL, substr(USER_SSN,1,7)||'******')
    ;

    commit;


    O_CODE := '0061';
    O_MSG := 'P_JUO_FEATURE_INFO 호출시 INPUT값 저장';

    update 
            PPS_RCG_REAL_CMS set
    BANK_PEOPLE_ID = CASE WHEN BANK_PEOPLE_ID IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(BANK_PEOPLE_ID), 1, 7) || '******') ELSE '' END
    ,BANK_USER_NAME = DECODE(BANK_USER_NAME,NULL,NULL,'이관고객')
    ;
    commit;

    O_CODE := '0062';
    O_MSG := 'PPS_RCG_REAL_CMS_SETUP';

    update 
            PPS_RCG_REAL_CMS_SETUP set
    CMS_ACCOUNT = ''
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ,CMS_USER_NAME = DECODE(CMS_USER_NAME,NULL,NULL,'이관고객')
    ,CMS_USER_SSN = CASE WHEN CMS_USER_SSN IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(CMS_USER_SSN), 1, 7) || '******') ELSE '' END
    ;
    commit;

    O_CODE := '0063';
    O_MSG := 'PPS_RCG_REAL_CMS_TEMP';

    update 
            PPS_RCG_REAL_CMS_TEMP set
    BANK_ACCOUNT = ''
    ,BANK_PEOPLE_ID = CASE WHEN BANK_PEOPLE_ID IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(BANK_PEOPLE_ID), 1, 7) || '******') ELSE '' END
    ,BANK_USER_NAME = DECODE(BANK_USER_NAME,NULL,NULL,'이관고객')
    ;
    commit;

    O_CODE := '0064';
    O_MSG := 'PPS_RCG_VAC';

    update 
            PPS_RCG_VAC set
    VACC_NO = ''
    ,CUST_NAME = DECODE(CUST_NAME,NULL,NULL,'이관고객')
    ,REQUESTER = DECODE(REQUESTER,NULL,NULL,'이관고객')
    ;
    commit;

    O_CODE := '0065';
    O_MSG := '실시간 CMS 청구할 데이타 (cmsd 데몬일 읽어서 효성과 연동) 처리결과는 RCG_CMS Table에 저장, 잔액출금은 customer_main의 Trigger에서 발생';

    update 
            PPS_REAL_PAYINFO_CMS set 
    BANK_ACCOUNT = ''
    ,BANK_USER_NAME = DECODE(BANK_USER_NAME,NULL,NULL,'이관고객')
    ,BANK_PEOPLE_ID = CASE WHEN BANK_PEOPLE_ID IS NOT NULL THEN CRYPTO_AES256.ENC_AES(SUBSTR(CRYPTO_AES256.DEC_AES(BANK_PEOPLE_ID), 1, 7) || '******') ELSE '' END
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ;
    commit;

    O_CODE := '0066';
    O_MSG := '실시간 CMS 청구할 데이타 (cmsd 데몬일 읽어서 효성과 연동) 처리결과는 RCG_CMS Table에 저장, 잔액출금은 customer_main의 Trigger에서 발생';

    update 
            PPS_REAL_PAYINFO_CMS_LOG set 
    BANK_ACCOUNT = ''
    ,BANK_USER_NAME = DECODE(BANK_USER_NAME,NULL,NULL,'이관고객')
    ,BANK_PEOPLE_ID = DECODE(BANK_PEOPLE_ID ,NULL,NULL, 'mplAuoXXmM7pwO5iJNZDZA=='  )
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ;
    commit;

    O_CODE := '0067';
    O_MSG := '실시간선불정산대상, 일단위로 BATCH돌림 ';

    update 
            PPS_REAL_STATS_CDR set
    SUB_LINK_NAME = DECODE(SUB_LINK_NAME,NULL,NULL,'이관고객')
    ;

    commit;

    O_CODE := '0068';
    O_MSG := '선불CDR, 충전 월통계(월단위로 BATCH돌림)  ';

    update 
             PPS_STATS_CDR a set 
    SUB_LINK_NAME = DECODE(SUB_LINK_NAME,NULL,NULL,'이관고객')
    ;
    commit;


    O_CODE := '0069';
    O_MSG := '가상계좌 입금 내역 정보(Socket 연동, 20180528 개발), 연동업체 CyberCVS(옛 KIBNET) ';

    update 
            PPS_VIR_ACCOUNT_KIBNET set
    REM_CUST_NAME = DECODE(REM_CUST_NAME,NULL,NULL,'이관고객')
    ,REM_BANK_ACCOUNT = ''
    ,VACC_NO = ''
    ,SUBSCRIBER_NO = DECODE(SUBSCRIBER_NO,NULL,NULL,'00000000000')
    ;

    commit;


    EXECUTE IMMEDIATE'ALTER SESSION DISABLE PARALLEL DML';


    O_CODE := '0008';
    O_MSG := '서식지 정보 EMV_SCAN_MST';

    --서식지 정보

    UPDATE  
            EMV_SCAN_MST@DL_MCP A
    SET     CUST_NM = '이관고객'
           ,DOC_TITLE = REPLACE(DOC_TITLE,CUST_NM,'이관고객')
    ;

    commit;

    O_CODE := '0000';
    O_MSG := 'SUCCESS';
    


EXCEPTION 
WHEN OTHERS THEN 
    
    O_CODE := O_CODE || '-999'; 
    O_MSG := O_MSG || ' ' || SQLERRM; 
    --ROLLBACK; 
    RETURN; 
END;