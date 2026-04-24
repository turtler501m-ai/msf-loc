create or replace PROCEDURE         P_MDS_CPNT_USR(
         i_DATE          IN VARCHAR2
        ,O_CODE        OUT VARCHAR2
        ,O_MSG         OUT VARCHAR2
 )IS   
 
/*============================================================================================ 
 * 프로그램 명 : M유통 판매점 정보를 UPDATE 및 INSERT
 * 프로그램 ID : P_MDS_CPNT_USR                                     
 * 프로그램설명: 1. M유통 판매점조회
                 2. M유통 판매점을 조회해서 usr테이블에 넣는다.
 *============================================================================================ 
 * 대상테이블  : 테이블명               | 테이블설명
 *               ----------------------   --------------------------
 *               CMN_USR_MST            | 사용자정보
 *               MDS_AGNCY_MNGR_HST     | 대리점이력 HIST 
 *              
 * 연동파일명 : KIS_OG_AGNCY_MNGR_HST_YYYYMMDD.DAT
 *============================================================================================ 
 * 수정일자   수정자 수정내용
 * ---------- ------ --------------------------------------------------------
 * 2019-03-06 권오승 최초 생성
 * 2021-05-13 송정섭 M2M USIM 유통망인지 확인 및 사용자그룹 G-PSCM2M 지정
 ===========================================================================================*/
CURSOR V_NAME IS

 --현행화된 조직에서 판매점 ID를 조회
SELECT ORGN_ID , ORGN_NM 
  FROM ORG_ORGN_INFO_MST
 WHERE TYPE_CD = '30' 
   AND TYPE_DTL_CD1 = 'ST'
   AND ORGN_ID LIKE 'V%'
;

--SELECT USR_ID , MBLPHN_NUM FROM cmn_usr_mst WHERE usr_id like 'V%'
--MINUS
--SELECT ORG_ID , MNGR_MPHON_NO FROM MDS_AGNCY_MNGR_HST WHERE FILE_NM = 'KIS_OG_AGNCY_MNGR_HST_'||i_DATE||'.DAT';


    v_mds_mphon_no              VARCHAR2(12);  --전문 휴대전화번호
    v_usr_tel                   VARCHAR2(12);  --현재 휴대전화번호
    
    v_usr_id                    VARCHAR2(11);  --조직ID
    v_usr_nm                   VARCHAR2(90);  --조직명
    
    c_cnt                       NUMBER;
    c_mphon_cnt                 NUMBER;
    c_usr_chg                   NUMBER;
    
    c_del_rsn                   NUMBER;
    c_filechk                   NUMBER;
    
    --M전산 USR테이블 전화번호가 NULL인 건수
    c_null_tel                  NUMBER;
    
    v_err_code                  NUMBER;         
    v_err_msg                   VARCHAR2(255);  
    
    V_PROC_NM           VARCHAR2(100);  --프로시저명
    V_PROC_ST           VARCHAR2(18);   --프로시저시작일자
    C_PROC_ED           VARCHAR2(18);   --프로시저종료일자
    C_PROC_CNT          NUMBER;         --처리건수
    
    v_m2mcnt         NUMBER;            --M2M USIM 유통망인지 확인
    V_GRP_ID         VARCHAR2(20);      --사용자그룹
    
BEGIN
     --파일체크로직
    BEGIN
        --테스트 시 필요
        --DBMS_OUTPUT.PUT_LINE('TIME1= ' || TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'));
        SELECT COUNT(*) INTO c_filechk FROM MDS_AGNCY_MNGR_HST 
        WHERE 
        FILE_NM = 'KIS_OG_AGNCY_MNGR_HST_'||i_DATE||'.DAT';
    EXCEPTION
    WHEN OTHERS THEN
         v_err_msg := SQLERRM;
         v_err_code := SQLCODE;
                 
         P_ORG_RESULT_MST(V_PROC_ST ,'', V_PROC_NM , v_err_code, v_err_msg , '', v_usr_id , '' , '' , '' , 'I' , 'CMN_USR_MST');
    END;
    --파일이 들어오지 않으면 프로시저종료
    IF c_filechk = 0 THEN 
    -- 테스트 시 필요
    -- DBMS_OUTPUT.PUT_LINE('TIME1-1 = 사용자정보파일X ' || TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'));
        RETURN;
    END IF;


--    DBMS_OUTPUT.PUT_LINE('TIME2= ' || TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'));
    DELETE FROM MDS_AGNCY_MNGR_MST;
    
    
--    DBMS_OUTPUT.PUT_LINE('TIME3= ' || TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'));
    INSERT INTO MDS_AGNCY_MNGR_MST
        ( ORG_ID, EFCT_ST_DT, EFCT_FNS_DT, AGNCY_MNGR_DIV_CD, MNGR_MPHON_NO )
    SELECT ORG_ID, EFCT_ST_DT, EFCT_FNS_DT, AGNCY_MNGR_DIV_CD, MNGR_MPHON_NO
      FROM MDS_AGNCY_MNGR_HST
     WHERE FILE_NM = 'KIS_OG_AGNCY_MNGR_HST_'||i_DATE||'.DAT' 
       AND EFCT_FNS_DT = '99991231000000'
       AND MNGR_MPHON_NO IS NOT NULL;
    
-- DBMS_OUTPUT.PUT_LINE('TIME4= ' || TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'));
    OPEN V_NAME;  
    O_CODE := '1000';
    C_PROC_CNT :=0;
    V_PROC_NM := 'P_MDS_CPNT_USR';
    V_PROC_ST := TO_CHAR(SYSDATE , 'YYYYMMDDhh24miss');
    P_ORG_RESULT_MST(V_PROC_ST , '' , V_PROC_NM , O_CODE , '프로시저시작' , C_PROC_CNT , '', '','','30' , '' , '');
    LOOP         
        FETCH V_NAME INTO    
              v_usr_id , v_usr_nm;
        EXIT WHEN V_NAME%NOTFOUND;           
        
        
        --조직명 특수문자 제거= &,<,>,"
        v_usr_nm := REGEXP_REPLACE(v_usr_nm , '&|<|>|"', '');
        O_CODE := '1000';
        
        --CMN_USR_MST 테이블에 ID가 있는지 조회
        SELECT COUNT(*)
          INTO c_cnt 
          FROM CMN_USR_MST 
         WHERE USR_ID = v_usr_id;
        
        --전화번호조회
        BEGIN
              SELECT COUNT(*)
                    ,MNGR_MPHON_NO
                INTO c_mphon_cnt , v_mds_mphon_no
                FROM MDS_AGNCY_MNGR_MST
               WHERE ORG_ID = v_usr_id
               GROUP BY MNGR_MPHON_NO;
            EXCEPTION
                 WHEN OTHERS THEN --조회결과가 없으면 SKIP
                    v_err_msg := NULL;
                    v_err_msg := v_err_msg||'예외무시:['||SQLCODE||']['||SQLERRM||']';
          END;

           --연락처가 2건 이상일 경우 경영자와 실무자 둘 중에 실무자 전화번호 추출
           BEGIN
               IF c_mphon_cnt > 1 THEN  
                    --전화번호 초기화
                    v_mds_mphon_no := '';
                    --실무자 전화번호 조회
                    SELECT MNGR_MPHON_NO
                      INTO v_mds_mphon_no 
                      FROM MDS_AGNCY_MNGR_MST 
                     WHERE ORG_ID = v_usr_id
                       AND AGNCY_MNGR_DIV_CD = '02' --실무자
                       AND ROWNUM = 1;
                END IF;
            EXCEPTION
                 WHEN OTHERS THEN --조회결과가 없으면 SKIP
                    v_err_msg := NULL;
                    v_err_msg := v_err_msg||'예외무시:['||SQLCODE||']['||SQLERRM||']';
            END;
        
        --없으면 전화번호 및 사용유무 조회해서 INSERT
        IF c_cnt = 0 THEN
        
            --전화번호조회            
            BEGIN
                 INSERT /*+ append_values */ INTO CMN_USR_MST
                    (   USR_ID,
                        ORGN_ID,
                        ATTC_SCTN_CD,
                        USR_NM,
                        PASS,
                        MBLPHN_NUM,
                        USG_YN,
                        PASS_LAST_RVISN_DT,
                        PASS_INIT,
                        REGST_ID,
                        REGST_DTTM
                    ) VALUES (
                        v_usr_id,
                        v_usr_id,
                        '30',
                        CASE WHEN LENGTHB(v_usr_nm) > 50
                        THEN SUBSTR(v_usr_nm , 0,16)
                        ELSE v_usr_nm
                        END , --자릿수 자르기
                        '8cd3e60775d1a8254d33e51fbf4cd811701205ec70d7bbc0f9167d9e94ab8c012f642a57ca80568d51e26a15df860f5627267828a8a20c9d550e0c9d54054aea',
                        v_mds_mphon_no,
                        'Y',
                        TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),
                        'Y',
                        'SYSTEM',
                        SYSDATE
                    );
                EXCEPTION
                  WHEN OTHERS THEN
                    v_err_msg := SQLERRM;
                    v_err_code := SQLCODE;
                    
                    P_ORG_RESULT_MST(V_PROC_ST ,'', V_PROC_NM , v_err_code, v_err_msg , '', v_usr_id , '' , '' , '' , 'I' , 'CMN_USR_MST');
                END;
                
                /*USER HIST INSERT */
                BEGIN
                 
                    INSERT INTO CMN_USR_HST(    
                        USR_ID,
                        SEQ_NUM,
                        ATTC_SCTN_CD,
                        USR_NM,
                        PASS,
                        TEL_NUM,
                        MBLPHN_NUM,
                        PASS_ERR_NUM,
                        PRES_BIZ,
                        EMAIL,
                        POS,
                        ODTY,
                        ADDR,
                        DTL_ADDR,
                        ZIPCD,
                        FAX,
                        PASS_ATMT_NUM,
                        DEL_YN,
                        REGST_ID,
                        REGST_DTTM,
                        RVISN_ID,
                        RVISN_DTTM,
                        DEPT,
                        IP_INFO,
                        MAC_ADDR,
                        USG_YN,
                        USG_STRT_DT,
                        USG_END_DT,
                        PASS_LAST_RVISN_DT,
                        MNGR_RULE,
                        MSK_AUTH_YN,
                        ORGN_ID,
                        PASS_INIT,
                        DEL_RSN,
                        KOS_ID
                    ) SELECT 
                        USR_ID,
                        (SELECT NVL(MAX(TO_NUMBER (SEQ_NUM))+1,1) FROM CMN_USR_HST WHERE USR_ID = v_usr_id),
                        ATTC_SCTN_CD,
                        USR_NM,
                        PASS,
                        TEL_NUM,
                        MBLPHN_NUM,
                        PASS_ERR_NUM,
                        PRES_BIZ,
                        EMAIL,
                        POS,
                        ODTY,
                        ADDR,
                        DTL_ADDR,
                        ZIPCD,
                        FAX,
                        PASS_ATMT_NUM,
                        DEL_YN,
                        REGST_ID,
                        REGST_DTTM,
                        v_usr_id,
                        SYSDATE,
                        DEPT,
                        IP_INFO,
                        MAC_ADDR,
                        USG_YN,
                        USG_STRT_DT,
                        USG_END_DT,
                        PASS_LAST_RVISN_DT,
                        MNGR_RULE,
                        MSK_AUTH_YN,
                        ORGN_ID,
                        PASS_INIT,
                        DEL_RSN,
                        KOS_ID
                     FROM CMN_USR_MST
                     WHERE USR_ID = v_usr_id;
                EXCEPTION
                  WHEN OTHERS THEN
                    v_err_msg := SQLERRM;
                    v_err_code := SQLCODE;
                    P_ORG_RESULT_MST(V_PROC_ST ,'', V_PROC_NM , v_err_code, v_err_msg , '', v_usr_id , '' , '' , '' , 'I' , 'CMN_USR_HST');
                END;
                
                /*M2M USIM 유통망인지 확인 */

                SELECT count(*)
                INTO   v_m2mcnt
                FROM   ORG_ORGN_INFO_MST A --판매점
                     , MDS_AGNT_INFO B  --조직
                WHERE a.orgn_id = v_usr_id
                AND   b.FILE_NM = 'KIS_OG_AGNCY_BAS_'|| i_DATE || '.DAT'
                AND   A.kt_org_id = B.org_id
                and   b.CONT_MCTG_CRCLT_CH_CD = 'E0200' --특수
                and   b.CONT_CRCLT_CH_CD     = 'E0211'  --특판(B2B)
                ;	
                
                IF v_m2mcnt = 0 THEN
                    V_GRP_ID := 'G-PSC';
                ELSE
                    V_GRP_ID := 'G-PSCM2M';
                
                END IF;
                
                /*CMN_USG_GRP_ASGN_MST INSERT */
                BEGIN
                   INSERT INTO CMN_USG_GRP_ASGN_MST
                    (
                        USR_ID,
                        GRP_ID,
                        REGST_ID,
                        REGST_DTTM
                    )
                    VALUES( 
                        v_usr_id,
                        V_GRP_ID,
                        'SYSTEM',
                        SYSDATE
                    );
                EXCEPTION
                  WHEN OTHERS THEN
                    v_err_msg := SQLERRM;
                    v_err_code := SQLCODE;
                    
                    P_ORG_RESULT_MST(V_PROC_ST ,'', V_PROC_NM , v_err_code, v_err_msg , '', v_usr_id , '' , '' , '' , 'I' , 'CMN_USG_GRP_ASGN_MST');
                END;
              
               /*CMN_USG_GRP_ASGN_HST INSERT */
                BEGIN
                   INSERT INTO CMN_USG_GRP_ASGN_HST
                    (   USR_ID,
                        GRP_ID,
                        REGST_ID,
                        REGST_DTTM,
                        SEQ_NUM,
                        OPER_TPYE
                    )
                    VALUES
                    (    v_usr_id,
                        V_GRP_ID,
                        'SYSTEM',
                        SYSDATE,
                        (SELECT NVL(MAX(TO_NUMBER(SEQ_NUM))+1,1) FROM CMN_USG_GRP_ASGN_HST WHERE USR_ID = v_usr_id),
                        'I'
                    );
                EXCEPTION
                  WHEN OTHERS THEN
                    v_err_msg := SQLERRM;
                    v_err_code := SQLCODE;
                    
                    P_ORG_RESULT_MST(V_PROC_ST ,'', V_PROC_NM , v_err_code, v_err_msg , '', v_usr_id , '' , '' , '' , 'I' , 'CMN_USG_GRP_ASGN_HST');
                END;
        
        --있으면 전화번호 및 조회해서 UPDATE유무 체크
        ELSIF c_cnt > 0 THEN
            --update 유무체크
            SELECT COUNT(*) INTO c_usr_chg
                FROM CMN_USR_MST 
                WHERE USR_ID = v_usr_id
                  AND MBLPHN_NUM = v_mds_mphon_no
                  AND USR_NM = CASE WHEN LENGTHB(v_usr_nm) > 50
                         THEN SUBSTR(v_usr_nm , 0,16)
                         ELSE v_usr_nm
                         END;
             IF c_usr_chg = 0 THEN
                -- USG_YN=S 사용유무 업데이트 X
                    SELECT COUNT(*) INTO c_del_rsn
                    FROM CMN_USR_MST 
                    WHERE USR_ID = v_usr_id
                      AND USG_YN = 'S';
                      
             IF c_del_rsn > 0 THEN
                        BEGIN
                            UPDATE /* +BYPASS_JUVC*/ CMN_USR_MST SET
                             USR_NM = CASE WHEN LENGTHB(v_usr_id) > 50
                             THEN SUBSTR(v_usr_id , 0,16)
                             ELSE v_usr_id
                             END   
                            ,MBLPHN_NUM = v_mds_mphon_no
                            ,RVISN_ID = 'SYSTEM'
                            ,RVISN_DTTM = SYSDATE
                            WHERE USR_ID = v_usr_id;
                         EXCEPTION
                          WHEN OTHERS THEN
                            v_err_msg := SQLERRM;
                            v_err_code := SQLCODE;
                            
                            P_ORG_RESULT_MST(V_PROC_ST ,'', V_PROC_NM , v_err_code, v_err_msg , '', v_usr_id , '' , '' , '' , 'U' , 'CMN_USR_MST');
                        END;
                     ELSIF c_del_rsn = 0 THEN
                         BEGIN
                            UPDATE /* +BYPASS_JUVC*/ CMN_USR_MST SET
                             USR_NM = CASE WHEN LENGTHB(v_usr_nm) > 50
                             THEN SUBSTR(v_usr_nm , 0,16)
                             ELSE v_usr_nm
                             END   
                            ,MBLPHN_NUM = v_mds_mphon_no
                            ,RVISN_ID = 'SYSTEM'
                            ,RVISN_DTTM = SYSDATE
                            WHERE USR_ID = v_usr_id;
                         EXCEPTION
                          WHEN OTHERS THEN
                            v_err_msg := SQLERRM;
                            v_err_code := SQLCODE;
                            P_ORG_RESULT_MST(V_PROC_ST ,'', V_PROC_NM , v_err_code, v_err_msg , '', v_usr_id , '' , '' , '' , 'U' , 'CMN_USR_MST');
                        END;
                   END IF;
               END IF;     
            END IF;
     END LOOP;
     
        IF O_CODE = '1000' THEN
            O_MSG := '정상처리';
        ELSE
            O_CODE := '-1';
            O_MSG := '처리실패';
        END IF;
        
        C_PROC_ED := TO_CHAR(SYSDATE , 'YYYYMMDDhh24miss'); 
        P_ORG_RESULT_MST('' , C_PROC_ED , V_PROC_NM , O_CODE , O_MSG , C_PROC_CNT , '', '','CS','30' , '' , 'CMN_USR_MST');    
              
    CLOSE V_NAME;
    commit;
    --CMN_USR_MST 테이블에 전화번호가 1건이라도 NULL로 업데이트 될 경우 COMMIT하지 않고 RETURN
--    SELECT COUNT(*) INTO c_null_tel
--    FROM CMN_USR_MST WHERE MBLPHN_NUM IS NULL AND ATTC_SCTN_CD = '30';
--    
--    IF c_null_tel > 0 THEN  --판매점 전화번호가 NULL이 1건이라도 있으면 RETURN
--        INSERT INTO ORG_RESULT_MST
--        (PROC_DATE, PROC_ST_DATE, PROC_ED_DATE, PROC_NM, CODE, CODE_DTL, PROC_CNT, PRAM_ORG_ID, ORG_STATUS, ORG_TYPE, ORG_TYPE_DTL, sql_div, table_nm )
--        VALUES (SYSDATE, TO_CHAR(SYSDATE , 'YYYYMMDDhh24miss'), TO_CHAR(SYSDATE , 'YYYYMMDDhh24miss')
--        , P_MDS_CPNT_USR,   '', '전화번호null존재' , i_proc_cnt ,i_parm_org, v_org_status ,v_org_type , v_org_type_dtl , i_sql_div , i_table_nm);
--        RETURN;     
--    ELSIF c_null_tel = 0 THEN --판매점 전화번호가 NULL이 없을 경우에만 COMMIT
--        COMMIT;
--    END IF;
    
    --DBMS_OUTPUT.PUT_LINE('O_CODE= ' || O_CODE);
    --DBMS_OUTPUT.PUT_LINE('O_MSG = ' || O_MSG);
 
    --DBMS_OUTPUT.PUT_LINE('TIME5= ' || TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'));

EXCEPTION           
WHEN OTHERS THEN           
    v_err_code := SQLCODE;
    v_err_msg := SQLERRM;
    --DBMS_OUTPUT.PUT_LINE('에러 번호 : ' || TO_CHAR(v_err_code));
    --DBMS_OUTPUT.PUT_LINE('에러 내용 : ' || v_err_msg);
    O_MSG := SUBSTR(SQLERRM, 1, 1000);
--    DBMS_OUTPUT.PUT_LINE('O_CODE= ' || O_CODE);
--    DBMS_OUTPUT.PUT_LINE('O_MSG = ' || O_MSG);
    ROLLBACK;
    RETURN;
    
END P_MDS_CPNT_USR;