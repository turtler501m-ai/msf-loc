create or replace PROCEDURE         P_PARTNER_USG_POINT ( 
    I_USG_YM    IN VARCHAR2 
    , O_CODE    OUT VARCHAR2 
    , O_MSG     OUT VARCHAR2 
) 
 
/******************************************************************************* 
 * NAME:       P_PARTNER_USG_POINT 
 * PURPOSE:    제휴사 사용량 및 청구/수납 계산 PROCEDURE () 
 * 
 * REVISIONS: 
 * Ver        Date        Author           Description 
 * ---------  ----------  ---------------  ------------------------------------ 
 * 1.0        2016-02-18                   1. Created this procedure. 
 * 1.1        2017-03-28  이상직           동부화재 정산 추가 
 * 1.2        2017-05-29  이상직           당월 일수와 사용일수 비교 처리 
 * 1.3        2017-07-26  이상직           동부화재 정산시 가입일은 사용일수에서 제외 
 * 1.4        2017-08-29  이상직           고객ID 추가         
 * 1.5        2017-11-16  이상직           롯데멤버스 제휴 추가 
 * 1.6        2018-01-15  이상직           오류메세지 처리 추가 
 * 1.7        2018-05-03  이상직           [SRM18042781298]기프티쇼 포인트연동 기능 개선 
 *                                         기프티쇼 명의변경 발생시 최종 사용자에게 포인트 지급  
 * 1.8        2018-08-27  이상직           [SRM18072675707]선택형 5종 단체보험 출시 관련 전산 프로세스 구축 
 *                                         정산 프로세스 변경 
 * 1.9        2019-07-30  정현주           [SRM00000000000]롯데멤버스 제휴 포인트 변경 
 ******************************************************************************/ 
 
IS 
 
-- 사용월 조회 
S_DT_CNT    NUMBER(2) := TO_NUMBER(TO_CHAR(LAST_DAY(TO_DATE(I_USG_YM,'YYYYMM')), 'DD')); 
-- 마지막일자 조회 
S_LAST_DT   VARCHAR(8) := TO_CHAR(LAST_DAY(TO_DATE(I_USG_YM,'YYYYMM')), 'YYYYMMDD'); 
-- 청구월 조회 
S_BILL_YM   VARCHAR2(6) := TO_CHAR(ADD_MONTHS(TO_DATE(I_USG_YM, 'YYYYMM'), 1), 'YYYYMM'); 
 
CURSOR C_POINT_LIST IS 
    SELECT  B.PARTNER_ID 
            , A.CONTRACT_NUM 
            , A.SVC_CNTR_NO 
            , A.RATE_CD 
            , B.POINT_VAL 
            , 'X' AS INSR_CD 
            , SUM(A.USG_DT_CNT) OVER(PARTITION BY A.CONTRACT_NUM, A.SVC_CNTR_NO, A.RATE_CD) AS USG_DT_CNT 
    FROM    MSP_CNTR_USG_INFO A 
            , MSP_PARTNER_RATE_MST B 
    WHERE   A.USG_YM = I_USG_YM 
    AND     A.SUB_STATUS = 'A' 
    AND     A.CONTRACT_NUM NOT IN (SELECT CD_VAL FROM CMN_GRP_CD_MST WHERE GRP_ID = 'CMN0067') 
    AND     A.RATE_CD = B.RATE_CD 
    AND     B.PARTNER_ID = 'jejuair' 
    AND     B.USE_YN = 'Y' 
    AND     B.POINT_TYPE = DECODE(FN_GET_MDL_CHK(A.CONTRACT_NUM), 'Y', 'PT01', 'PT02') 
    UNION ALL 
    SELECT  B.PARTNER_ID 
            , A.CONTRACT_NUM 
            , A.SVC_CNTR_NO 
            , A.RATE_CD 
            , B.POINT_VAL 
            , 'X' AS INSR_CD 
            , SUM(A.USG_DT_CNT) OVER(PARTITION BY A.CONTRACT_NUM, A.SVC_CNTR_NO, A.RATE_CD) AS USG_DT_CNT 
    FROM    MSP_CNTR_USG_INFO A 
            , MSP_PARTNER_RATE_MST B 
    WHERE   A.USG_YM = I_USG_YM 
    AND     A.SUB_STATUS = 'A' 
    AND     A.CONTRACT_NUM NOT IN (SELECT CD_VAL FROM CMN_GRP_CD_MST WHERE GRP_ID = 'CMN0067') 
    AND     A.RATE_CD = B.RATE_CD 
    AND     B.PARTNER_ID = 'tmoney' 
    AND     B.USE_YN = 'Y' 
    AND     B.POINT_TYPE = DECODE(FN_GET_MDL_CHK(A.CONTRACT_NUM), 'Y', 'PT01', 'PT02') 
    UNION ALL 
    SELECT  B.PARTNER_ID 
            , A.CONTRACT_NUM 
            , A.SVC_CNTR_NO 
            , A.RATE_CD 
            , CASE WHEN SUM(A.USG_DT_CNT) OVER(PARTITION BY A.CONTRACT_NUM, A.SVC_CNTR_NO) >= S_DT_CNT AND A.END_DT = S_LAST_DT THEN B.POINT_VAL 
                   WHEN A.END_DT = S_LAST_DT AND NOT EXISTS(SELECT 1 FROM MSP_PARTNER_POINT_MST WHERE PARTNER_ID = B.PARTNER_ID AND SVC_CNTR_NO = A.SVC_CNTR_NO AND USG_YM < A.USG_YM) THEN B.POINT_VAL  
                   ELSE 0  
              END AS POINT_VAL 
            , 'X' AS INSR_CD 
            , SUM(A.USG_DT_CNT) OVER(PARTITION BY A.CONTRACT_NUM, A.SVC_CNTR_NO, A.RATE_CD) AS USG_DT_CNT 
    FROM    MSP_CNTR_USG_INFO A 
            , MSP_PARTNER_RATE_MST B 
    WHERE   A.USG_YM = I_USG_YM 
    AND     A.SUB_STATUS = 'A' 
    AND     A.CONTRACT_NUM NOT IN (SELECT CD_VAL FROM CMN_GRP_CD_MST WHERE GRP_ID = 'CMN0067') 
    AND     A.RATE_CD = B.RATE_CD 
    AND     B.PARTNER_ID = 'gifti' 
    AND     B.USE_YN = 'Y' 
    AND     B.POINT_TYPE = DECODE(FN_GET_MDL_CHK(A.CONTRACT_NUM), 'Y', 'PT01', 'PT02') 
    UNION ALL 
    SELECT PARTNER_ID 
           , CONTRACT_NUM 
           , SVC_CNTR_NO 
           , RATE_CD 
           , CASE WHEN RATE_CD = 'KISLIFU10' AND APP_START_DD < '20190723000000' AND APP_START_DD IS NOT NULL THEN 3000 ELSE POINT_VAL END POINT_VAL 
           , INSR_CD 
           , USG_DT_CNT 
      FROM ( 
            SELECT  B.PARTNER_ID 
                    , A.CONTRACT_NUM 
                    , A.SVC_CNTR_NO 
                    , A.RATE_CD 
                    , B.POINT_VAL 
                    , 'X' AS INSR_CD 
                    , SUM(A.USG_DT_CNT) OVER(PARTITION BY A.CONTRACT_NUM, A.SVC_CNTR_NO, A.RATE_CD) AS USG_DT_CNT 
                    , (SELECT MAX(C.APP_START_DD) 
                         FROM MSP_JUO_FEATURE_INFO C 
                        WHERE A.CONTRACT_NUM = C.CONTRACT_NUM 
                          AND A.RATE_CD = C.SOC) AS APP_START_DD 
            FROM    MSP_CNTR_USG_INFO A 
                    , MSP_PARTNER_RATE_MST B 
            WHERE   A.USG_YM = I_USG_YM 
            AND     SUB_STATUS = 'A' 
            AND     A.CONTRACT_NUM NOT IN (SELECT CD_VAL FROM CMN_GRP_CD_MST WHERE GRP_ID = 'CMN0067') 
            AND     A.RATE_CD = B.RATE_CD 
            AND     B.PARTNER_ID = 'lpoint' 
            AND     B.USE_YN = 'Y' 
            AND     B.POINT_TYPE = 'PT03' 
           ) AA 
    UNION ALL 
    SELECT  A.PARTNER_ID 
            , A.CONTRACT_NUM 
            , A.SVC_CNTR_NO 
            , A.RATE_CD 
            , A.POINT_VAL 
            , A.INSR_CD 
            , SUM(A.USG_DT_CNT) OVER(PARTITION BY A.CONTRACT_NUM, A.SVC_CNTR_NO) AS USG_DT_CNT 
    FROM    ( 
        SELECT  DISTINCT 
                B.PARTNER_ID 
                , A.CONTRACT_NUM 
                , A.SVC_CNTR_NO 
                , A.RATE_CD 
                , TO_NUMBER(D.ETC1) AS POINT_VAL 
                , D.CD_VAL AS INSR_CD 
                , A.USG_DT_CNT 
        FROM    MSP_CNTR_USG_INFO A 
                , MSP_PARTNER_RATE_MST B 
                , MSP_INSR_MEMBER_MST C 
                , CMN_GRP_CD_MST D 
        WHERE   A.USG_YM = I_USG_YM 
        AND     A.SUB_STATUS = 'A' 
        AND     A.CONTRACT_NUM NOT IN (SELECT CD_VAL FROM CMN_GRP_CD_MST WHERE GRP_ID = 'CMN0067') 
        AND     A.RATE_CD = B.RATE_CD 
        AND     B.PARTNER_ID = 'dongbu' 
        AND     B.USE_YN = 'Y' 
        AND     B.POINT_TYPE = 'PT03' 
        AND     A.CONTRACT_NUM = C.CONTRACT_NUM 
        AND     A.SVC_CNTR_NO = C.SVC_CNTR_NO 
        AND     A.USG_YM BETWEEN SUBSTR(C.STRT_DTTM, 1, 6) AND SUBSTR(C.END_DTTM, 1, 6) 
        AND     C.INSR_CD IN (SELECT CD_VAL FROM CMN_GRP_CD_MST WHERE GRP_ID = 'CMN0246' AND USG_YN = 'Y' AND ETC2 = 'N') 
        AND     D.GRP_ID = 'CMN0246' 
        AND     D.USG_YN = 'Y' 
        AND     D.CD_VAL = (CASE WHEN TRUNC(MONTHS_BETWEEN(TO_DATE(S_LAST_DT, 'YYYYMMDD'), TO_DATE(C.BIRTH_DT, 'YYYYMMDD'))/12) < 15 THEN 'A1' 
                                 WHEN TRUNC(MONTHS_BETWEEN(TO_DATE(S_LAST_DT, 'YYYYMMDD'), TO_DATE(C.BIRTH_DT, 'YYYYMMDD'))/12) >= 70 THEN 'A2' 
                                 ELSE 'A0' END) 
    ) A 
    UNION ALL 
    SELECT  DISTINCT 
            'dongbu' AS PARTNER_ID 
            , A.CONTRACT_NUM 
            , A.SVC_CNTR_NO 
            , A.RATE_CD 
            , TO_NUMBER(B.ETC1) AS POINT_VAL 
            , A.INSR_CD 
            , CASE WHEN MAX(SUBSTR(A.END_DTTM, 1, 8)) >= S_LAST_DT THEN TO_DATE(S_LAST_DT, 'YYYYMMDD') 
                   ELSE TO_DATE(MAX(SUBSTR(A.END_DTTM, 1, 8)), 'YYYYMMDD') END 
            - CASE WHEN MIN(SUBSTR(A.STRT_DTTM, 1, 8)) > I_USG_YM || '01' THEN TO_DATE(MIN(SUBSTR(A.STRT_DTTM, 1, 8)), 'YYYYMMDD') 
                   ELSE TO_DATE(I_USG_YM || '01', 'YYYYMMDD') END 
            + 1 AS USG_DT_CNT 
    FROM    MSP_INSR_MEMBER_MST A 
            , CMN_GRP_CD_MST B 
    WHERE   I_USG_YM BETWEEN SUBSTR(A.STRT_DTTM, 1, 6) AND SUBSTR(A.END_DTTM, 1, 6) 
    AND     B.GRP_ID = 'CMN0246' 
    AND     B.CD_VAL = A.INSR_CD 
    AND     B.USG_YN = 'Y' 
    AND     B.ETC2 = 'Y' 
    GROUP BY A.CONTRACT_NUM, A.SVC_CNTR_NO, A.RATE_CD, A.INSR_CD, B.ETC1 
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
            , V_USG_DT 
            ; 
        EXIT WHEN C_POINT_LIST%NOTFOUND; 
         
        -- 변수 초기화 
        V_CHK           := 0; 
        V_ADJ_POINT     := 0; 
        V_PAY_POINT     := 0; 
        V_PAY_YN        := 'N'; 
        V_SEND_FLAG     := 'B'; 
        V_PAY_RESULT    := 'B'; 
        V_USG_DT        := (CASE WHEN V_USG_DT > S_DT_CNT THEN S_DT_CNT ELSE V_USG_DT END); 
         
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
         
        IF V_PARTNER_ID IN ('jejuair', 'tmoney') THEN 
            -- 제주항공, 티머니 정산
            V_CAL_POINT := ROUND(V_POINT_VAL * V_USG_DT / S_DT_CNT, 0); 
            V_REQ_POINT := V_CAL_POINT; 
            
        ELSIF V_PARTNER_ID = 'gifti' THEN 
--            -- 명의변경 체크 
--            IF V_CONTRACT_NUM <> V_SVC_CNTR_NO THEN 
--                DBMS_OUTPUT.PUT_LINE('명의변경체크 ==========================='); 
--                 
--                SELECT  COUNT(1) 
--                    INTO V_CHK 
--                FROM    MSP_CNTR_USG_INFO A 
--                WHERE   A.USG_YM = I_USG_YM 
--                AND     A.END_DT = S_LAST_DT 
--                AND     A.CONTRACT_NUM = V_CONTRACT_NUM 
--                AND     A.SVC_CNTR_NO = V_SVC_CNTR_NO 
--                ; 
--                 
--                -- 명의변경시 이전 사용자인 경우 
--                IF V_CHK = 0 THEN 
--                    V_CAL_POINT := 0; 
--                END IF; 
--            ELSE 
--                DBMS_OUTPUT.PUT_LINE('요금제변경체크 ========================='); 
--                DBMS_OUTPUT.PUT_LINE('CONTRACT_NUM=' || V_CONTRACT_NUM); 
--                DBMS_OUTPUT.PUT_LINE('SVC_CNTR_NO=' || V_SVC_CNTR_NO); 
--                 
--                -- 요금제변경 체크 
--                BEGIN 
--                    SELECT  A.RATE_CD 
--                            , B.POINT_VAL 
--                            , SUM(A.USG_DT_CNT) OVER(PARTITION BY A.CONTRACT_NUM, A.SVC_CNTR_NO, A.RATE_CD) AS USG_DT_CNT 
--                        INTO V_CHK_RATE, V_CHK_POINT, V_CHK_USG_DT 
--                    FROM    MSP_CNTR_USG_INFO A 
--                            , MSP_PARTNER_RATE_MST B 
--                    WHERE   A.USG_YM = I_USG_YM 
--                    AND     A.CONTRACT_NUM = V_CONTRACT_NUM 
--                    AND     A.SVC_CNTR_NO = V_SVC_CNTR_NO 
--                    AND     A.SUB_STATUS = 'A' 
--                    AND     A.END_DT = TO_CHAR(LAST_DAY(TO_DATE(A.USG_YM, 'YYYYMM')), 'YYYYMMDD') 
--                    AND     A.RATE_CD = B.RATE_CD 
--                    AND     B.PARTNER_ID = 'gifti' 
--                    AND     B.USE_YN = 'Y' 
--                    AND     B.POINT_TYPE = DECODE(FN_GET_MDL_CHK(A.CONTRACT_NUM), 'Y', 'PT01', 'PT02') 
--                    ; 
--                EXCEPTION 
--                    WHEN NO_DATA_FOUND THEN 
--                        V_CAL_POINT := 0; 
--                    WHEN OTHERS THEN 
--                        DBMS_OUTPUT.PUT_LINE('ERR_CODE=' || SQLCODE); 
--                        DBMS_OUTPUT.PUT_LINE('ERR_MSG=' || SQLERRM); 
--                END; 
--                 
--                IF V_CHK_USG_DT IS NOT NULL THEN 
--                    IF V_CHK > 0 THEN 
--                        V_CAL_POINT := V_POINT_VAL; 
--                    ELSIF  
--                    IF (V_CHK_POINT <> V_POINT_VAL AND V_CHK_USG_DT < 20) OR (V_CHK_POINT = V_POINT_VAL AND V_CHK_USG_DT = V_USG_DT) THEN 
--                        V_POINT_VAL := V_POINT_VAL; 
--                    ELSIF V_CHK_POINT <> V_POINT_VAL AND V_CHK_USG_DT >= 20 THEN 
--                        V_POINT_VAL := V_CHK_POINT; 
--                    ELSIF V_CHK_POINT = V_POINT_VAL THEN 
--                        V_POINT_VAL := 0; 
--                    END IF; 
--                END IF; 
--            END IF; 
             
            V_CAL_POINT := V_POINT_VAL; 
            V_REQ_POINT := V_CAL_POINT; 
 
        ELSIF V_PARTNER_ID = 'lpoint' THEN 
            -- 엘포인트 정산 
            V_CAL_POINT := TRUNC(V_POINT_VAL * V_USG_DT / S_DT_CNT, -1); 
            V_REQ_POINT := V_CAL_POINT; 
             
        ELSIF V_PARTNER_ID = 'dongbu' THEN 
            -- 동부화재 정산 ( 당월 가입자경우 익일부터 정산 ) 
            BEGIN 
                SELECT  DISTINCT 
                        CASE WHEN MAX(SUBSTR(A.STRT_DTTM, 1, 6)) OVER(PARTITION BY A.CONTRACT_NUM) < I_USG_YM THEN 
                                CASE WHEN V_USG_DT = S_DT_CNT THEN 0 
                                     WHEN MAX(SUBSTR(A.END_DTTM, 1, 6)) OVER(PARTITION BY A.CONTRACT_NUM) = I_USG_YM THEN 0 
                                     WHEN B.SUB_STATUS = 'C' AND SUBSTR(B.SUB_STATUS_DATE, 1, 6) = I_USG_YM THEN 0 
                                     ELSE 1 END 
                             ELSE 1 END AS CHK 
                    INTO V_CHK 
                FROM    MSP_INSR_MEMBER_MST A 
                        , MSP_JUO_SUB_INFO B 
                WHERE   A.SVC_CNTR_NO = V_SVC_CNTR_NO 
                AND     I_USG_YM BETWEEN SUBSTR(A.STRT_DTTM, 1, 6) AND SUBSTR(A.END_DTTM, 1, 6) 
                AND     A.CONTRACT_NUM = B.CONTRACT_NUM 
                ; 
            EXCEPTION 
                WHEN OTHERS THEN 
                    DBMS_OUTPUT.PUT_LINE('일수 계산오류 ======================'); 
                    DBMS_OUTPUT.PUT_LINE('ERR_MSG = ' || SQLERRM); 
                    DBMS_OUTPUT.PUT_LINE('PARTNER_ID = ' || V_PARTNER_ID); 
                    DBMS_OUTPUT.PUT_LINE('CONTRACT_NUM = ' || V_CONTRACT_NUM); 
                    DBMS_OUTPUT.PUT_LINE('SVC_CNTR_NO = ' || V_SVC_CNTR_NO); 
                    ROLLBACK; 
                    RETURN; 
            END; 
 
            -- 청구자료여부 조회 
            BEGIN 
                SELECT  (CASE WHEN NOT EXISTS (SELECT 1 FROM CLS_BILL_PYMN_DATA WHERE SVC_CNTR_NO = V_SVC_CNTR_NO AND BILL_RFLC_IND_CD = 'B' AND PYMN_CD = 'INV' AND ROWNUM = 1) THEN 'P' ELSE 'N' END) AS PAY_YN 
                    INTO V_PAY_YN 
                FROM    DUAL 
                ; 
            EXCEPTION 
                WHEN OTHERS THEN 
                    DBMS_OUTPUT.PUT_LINE('청구자료 조회오류 ======================'); 
                    DBMS_OUTPUT.PUT_LINE('ERR_MSG = ' || SQLERRM); 
                    DBMS_OUTPUT.PUT_LINE('PARTNER_ID = ' || V_PARTNER_ID); 
                    DBMS_OUTPUT.PUT_LINE('CONTRACT_NUM = ' || V_CONTRACT_NUM); 
                    DBMS_OUTPUT.PUT_LINE('SVC_CNTR_NO = ' || V_SVC_CNTR_NO); 
                    ROLLBACK; 
                    RETURN; 
            END; 
             
            SELECT  DECODE(V_PAY_YN, 'P', 'R', 'B') AS SEND_FLAG 
                    , DECODE(V_PAY_YN, 'P', 'X', 'B') AS PAY_RESULT 
                INTO V_SEND_FLAG, V_PAY_RESULT 
            FROM    DUAL 
            ; 
             
            V_USG_DT := V_USG_DT - V_CHK; 
            V_CAL_POINT := ROUND(V_POINT_VAL * V_USG_DT/S_DT_CNT); 
            V_REQ_POINT := V_CAL_POINT; 
        END IF; 
         
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
            , V_CAL_POINT 
            , V_ADJ_POINT 
            , V_REQ_POINT 
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
 
END P_PARTNER_USG_POINT ;