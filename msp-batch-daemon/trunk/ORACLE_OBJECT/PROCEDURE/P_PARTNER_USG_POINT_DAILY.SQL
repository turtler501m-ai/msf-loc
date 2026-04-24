create or replace PROCEDURE          P_PARTNER_USG_POINT_DAILY ( 
    I_USG_YM   IN VARCHAR2 
    , O_CODE    OUT VARCHAR2 
    , O_MSG     OUT VARCHAR2 
) 
 
/******************************************************************************* 
 * NAME:       P_PARTNER_USG_POINT_DAILY 
 * PURPOSE:    제휴사 사용량 및 청구/수납 계산 PROCEDURE () 매일 생성
 * 
 * REVISIONS: 
 * Ver        Date        Author           Description 
 * ---------  ----------  ---------------  ------------------------------------ 
 * 1.0        2016-02-18                   1. Created this procedure. 
 * 2.0        2020-09-14  송정섭           구글PLAY 제휴 추가
 * 2.1        2021-01-14  송정섭           대리점,모집경로,USIM접점 추가
 * 3.0        2021-10-07  송정섭           not exists에 SVC_CNTR_NO -> CONTRACT_NUM
 ******************************************************************************/ 

IS 

-- 사용월 조회 
S_DT_CNT    NUMBER(2) := TO_NUMBER(TO_CHAR(LAST_DAY(TO_DATE(I_USG_YM,'YYYYMM')), 'DD')); 
-- 마지막일자 조회 
S_LAST_DT   VARCHAR(8) := TO_CHAR(LAST_DAY(TO_DATE(I_USG_YM,'YYYYMM')), 'YYYYMMDD'); 
-- 청구월 조회 
S_BILL_YM   VARCHAR2(6) := TO_CHAR(ADD_MONTHS(TO_DATE(I_USG_YM, 'YYYYMM'), 1), 'YYYYMM'); 

CURSOR C_POINT_LIST IS 
    SELECT  DISTINCT 
              B.PARTNER_ID 
            , A.CONTRACT_NUM 
            , C.SVC_CNTR_NO 
            , B.ADD_SVC_CD AS RATE_CD 
            , B.POINT_VAL 
            , 'X' AS INSR_CD 
    FROM    MSP_JUO_FEATURE_INFO A
           ,MSP_PARTNER_ADD_SVC_MST B
           ,MSP_JUO_SUB_INFO C
    WHERE  TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') BETWEEN A.APP_START_DD
                                                AND      A.APP_END_DATE
    AND    A.SOC = B.ADD_SVC_CD    
    AND    A.CONTRACT_NUM = C.CONTRACT_NUM
    AND    B.USE_YN = 'Y'
    AND    C.SUB_STATUS = 'A' 
    AND    B.PARTNER_ID = 'googleplay' 
    AND    TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') BETWEEN B.STRT_DATE
                                               AND     B.END_DATE
    AND    NOT EXISTS ( SELECT 1 FROM MSP_PARTNER_POINT_MST AA
                         WHERE AA.PARTNER_ID = B.PARTNER_ID
                         AND   AA.USG_YM = I_USG_YM
                         AND   AA.CONTRACT_NUM = C.CONTRACT_NUM 
                         AND   AA.RATE_CD = B.ADD_SVC_CD
                       )
    UNION ALL
    SELECT  B.PARTNER_ID 
            , A.CONTRACT_NUM 
            , D.SVC_CNTR_NO 
            , C.PRMT_ID AS RATE_CD 
            , C.POINT_VAL 
            , 'X' AS INSR_CD 
    FROM    MSP_JUO_FEATURE_INFO A
            , MSP_PARTNER_RATE_MST B 
            , MSP_PARTNER_PRMT_MST C
            , MSP_JUO_SUB_INFO D
    WHERE  TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') BETWEEN  A.APP_START_DD
                                                AND      A.APP_END_DATE
    AND     A.CONTRACT_NUM = D.CONTRACT_NUM
    AND     A.CONTRACT_NUM NOT IN (SELECT CD_VAL FROM CMN_GRP_CD_MST WHERE GRP_ID = 'CMN0067') 
    AND     D.SUB_STATUS = 'A' 
    AND     A.SOC = B.RATE_CD 
    AND     B.PARTNER_ID = 'googleplay' 
    AND     B.USE_YN = 'Y' 
    AND     B.POINT_TYPE = DECODE(FN_GET_MDL_CHK(A.CONTRACT_NUM), 'Y', 'PT01', 'PT02') 
    AND     C.PARTNER_ID = B.PARTNER_ID
    AND     C.RATE_CD = B.RATE_CD
    AND     C.POINT_VAL > 0
    AND     D.LST_COM_ACTV_DATE BETWEEN C.STRT_DATE/*개통일기준*/
                                AND     C.END_DATE
    AND     TO_CHAR(SYSDATE,'DD') = '20'                                         
    AND     MONTHS_BETWEEN(TO_DATE(TO_CHAR(SYSDATE,'YYYYMM'),'YYYYMM'),TO_DATE(SUBSTR(D.LST_COM_ACTV_DATE,1,6),'YYYYMM')) BETWEEN 1/*개통일기준*/
                                                                                                                          AND     C.PRVD_CYCL
    AND    NVL(C.ORGN_ID,'-1') = DECODE(C.ORGN_ID,NULL,'-1',NVL(D.OPEN_AGNT_CD,'-1'))
    AND    NVL(C.ON_OFF_TYPE,'-1') = DECODE(C.ON_OFF_TYPE,NULL,'-1',NVL(D.ON_OFF_TYPE,'-1'))
    AND    NVL(C.USIM_ORGN_ID,'-1') = DECODE(C.USIM_ORGN_ID,NULL,'-1',(SELECT ORGN_ID AS USIM_ORGN_ID
                                                                         FROM MSP_PARTNER_USIM_MST
                                                                        WHERE USIM_NO = D.ICC_ID
                                                                        ))
    AND    NOT EXISTS ( SELECT 1 FROM MSP_PARTNER_POINT_MST AA
                         WHERE AA.PARTNER_ID = B.PARTNER_ID
                         AND   AA.USG_YM = I_USG_YM
                         AND   AA.CONTRACT_NUM = D.CONTRACT_NUM 
                         AND   AA.RATE_CD = C.PRMT_ID
                       )

    ; 

/******************************************************************************* 
 * 변수선언 
 ******************************************************************************/ 
V_PARTNER_ID        VARCHAR2(10); 
V_CONTRACT_NUM      VARCHAR2(10); 
V_SVC_CNTR_NO       VARCHAR2(10); 
V_RATE_CD           VARCHAR2(10); 
V_INSR_CD           VARCHAR2(2); 
V_POINT_VAL         NUMBER; 
V_USG_DT           NUMBER; 
-- INSERT시 사용되는 변수 
V_CUSTOMER_ID       VARCHAR2(10); 
V_CAL_POINT         NUMBER; 
V_ADJ_POINT         NUMBER; 
V_REQ_POINT         NUMBER; 
V_PAY_POINT         NUMBER; 
V_SEND_FLAG         VARCHAR2(1); 
V_PAY_YN            VARCHAR2(1); 
V_PAY_RESULT        VARCHAR2(1); 
V_CHK               NUMBER; 

BEGIN 
    O_CODE := '0000'; 
    O_MSG := '정상처리되었습니다.'; 

    --DELETE FROM MSP_PARTNER_POINT_MST WHERE USG_YM = I_USG_YM; 
    --COMMIT; 

    OPEN C_POINT_LIST; 
    LOOP 
        FETCH C_POINT_LIST INTO 
            V_PARTNER_ID 
            , V_CONTRACT_NUM 
            , V_SVC_CNTR_NO 
            , V_RATE_CD 
            , V_POINT_VAL 
            , V_INSR_CD 
            ; 
        EXIT WHEN C_POINT_LIST%NOTFOUND; 

        -- 변수 초기화 
        V_CHK           := 0; 
        V_ADJ_POINT     := 0; 
        V_PAY_POINT     := 0; 
        V_PAY_YN        := 'Y'; 
        V_SEND_FLAG     := 'R'; 
        V_PAY_RESULT    := 'X'; 

        -- 고객ID 조회 
        BEGIN 
            SELECT  DISTINCT CUSTOMER_ID 
                INTO V_CUSTOMER_ID 
            FROM    MSP_JUO_SUB_INFO_HIST 
            WHERE   CONTRACT_NUM = V_CONTRACT_NUM 
            AND     SVC_CNTR_NO = V_SVC_CNTR_NO 
            AND     CUSTOMER_ID IS NOT NULL 
            ; 
        EXCEPTION 
            WHEN OTHERS THEN 
                DBMS_OUTPUT.PUT_LINE('고객ID 조회오류 : ' || O_CODE); 
                DBMS_OUTPUT.PUT_LINE('고객ID 조회내용 : ' || O_MSG); 
                V_CUSTOMER_ID := '000000000'; 
        END; 

        INSERT INTO MSP_PARTNER_POINT_MST ( 
            PARTNER_ID 
            , BILL_YM 
            , CONTRACT_NUM 
            , SVC_CNTR_NO 
            , CUSTOMER_ID 
            , RATE_CD 
            , INSR_CD 
            , USG_YM 
            , USG_DT_CNT 
            , CAL_POINT 
            , ADJ_POINT 
            , REQ_POINT 
            , PAY_POINT 
            , SEND_FLAG 
            , FULL_PAY_YN 
            , PAY_RESULT 
            , REGST_ID 
            , REGST_DTTM 
            , RVISN_ID 
            , RVISN_DTTM 
        ) VALUES ( 
            V_PARTNER_ID 
            , S_BILL_YM 
            , V_CONTRACT_NUM 
            , V_SVC_CNTR_NO 
            , V_CUSTOMER_ID 
            , V_RATE_CD 
            , V_INSR_CD 
            , I_USG_YM 
            , V_USG_DT 
            , V_POINT_VAL 
            , V_ADJ_POINT 
            , V_POINT_VAL 
            , V_PAY_POINT 
            , V_SEND_FLAG 
            , V_PAY_YN 
            , V_PAY_RESULT 
            , 'POINT' 
            , SYSDATE 
            , 'POINT' 
            , SYSDATE 
        ) 
        ; 

    END LOOP; 

    COMMIT; 

    CLOSE C_POINT_LIST; 

    DBMS_OUTPUT.PUT_LINE('O_CODE=' || O_CODE); 

EXCEPTION 
    WHEN OTHERS THEN 
        DBMS_OUTPUT.PUT_LINE('에러 번호 : ' || TO_CHAR(SQLCODE)); 
        DBMS_OUTPUT.PUT_LINE('에러 내용 : ' || SQLERRM); 
        DBMS_OUTPUT.PUT_LINE('PARTNER_ID : ' || V_PARTNER_ID); 
        DBMS_OUTPUT.PUT_LINE('CONTRACT_NUM : ' || V_CONTRACT_NUM); 
        DBMS_OUTPUT.PUT_LINE('SVC_CNTR_NO : ' || V_SVC_CNTR_NO); 
        ROLLBACK; 
        RETURN; 

END "P_PARTNER_USG_POINT_DAILY" ;