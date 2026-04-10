#!/bin/bash
set -euo pipefail

sqlplus -s MSP_MNG/temp1234@//localhost:1521/FREEPDB1 <<'SQL'
WHENEVER SQLERROR EXIT SQL.SQLCODE

DECLARE
    v_table_count NUMBER := 0;
BEGIN
    SELECT COUNT(*)
      INTO v_table_count
      FROM user_tables
     WHERE table_name = 'CMN_BOARD';

    IF v_table_count = 0 THEN
        EXECUTE IMMEDIATE '
            CREATE TABLE CMN_BOARD (
                BOARD_CD    VARCHAR2(3)   NOT NULL,
                SRL_NUM     NUMBER(15)    NOT NULL,
                TITLE       VARCHAR2(200) NOT NULL,
                CNTT        VARCHAR2(4000),
                DEL_YN      VARCHAR2(1)   NOT NULL,
                POST_STRT_DT VARCHAR2(8),
                POST_END_DT  VARCHAR2(8),
                ORGN_TP_CD1 VARCHAR2(1),
                ORGN_TP_CD2 VARCHAR2(1),
                ORGN_TP_CD3 VARCHAR2(1),
                ORGN_TP_CD4 VARCHAR2(1),
                ORGN_TP_CD5 VARCHAR2(1),
                REGST_ID    VARCHAR2(10),
                REGST_DTTM  DATE,
                RVISN_ID    VARCHAR2(10),
                RVISN_DTTM  DATE,
                CONSTRAINT PK_CMN_BOARD PRIMARY KEY (SRL_NUM)
            )';
    END IF;
END;
/

MERGE INTO CMN_BOARD target
USING (
    SELECT 'B10' AS BOARD_CD, 1615 AS SRL_NUM, '고객센터 등록권한' AS TITLE, 'Sample Content 1615' AS CNTT, 'N' AS DEL_YN,
           '20220304' AS POST_STRT_DT, '20221231' AS POST_END_DT, 'A' AS ORGN_TP_CD1, NULL AS ORGN_TP_CD2, NULL AS ORGN_TP_CD3, NULL AS ORGN_TP_CD4, NULL AS ORGN_TP_CD5,
           'SYSTEM' AS REGST_ID, TO_DATE('2022-03-04 16:06:01', 'YYYY-MM-DD HH24:MI:SS') AS REGST_DTTM, 'SYSTEM' AS RVISN_ID, TO_DATE('2022-03-04 16:06:01', 'YYYY-MM-DD HH24:MI:SS') AS RVISN_DTTM
      FROM dual
    UNION ALL
    SELECT 'B10', 1614, '고객센터테스트', 'Sample Content 1614', 'Y',
           '20220304', '20221231', 'A', NULL, NULL, NULL, NULL,
           'SYSTEM', TO_DATE('2022-03-04 13:41:14', 'YYYY-MM-DD HH24:MI:SS'), 'SYSTEM', TO_DATE('2022-03-04 13:41:14', 'YYYY-MM-DD HH24:MI:SS')
      FROM dual
    UNION ALL
    SELECT 'B10', 1613, '고객센터테스트', 'Sample Content 1613', 'Y',
           '20220304', '20221231', 'A', NULL, NULL, NULL, NULL,
           'SYSTEM', TO_DATE('2022-03-04 13:25:04', 'YYYY-MM-DD HH24:MI:SS'), 'SYSTEM', TO_DATE('2022-03-04 13:25:04', 'YYYY-MM-DD HH24:MI:SS')
      FROM dual
    UNION ALL
    SELECT 'B10', 1612, '20220304고객센터업무게시판', 'Sample Content 1612', 'Y',
           '20220304', '20221231', 'A', NULL, NULL, NULL, NULL,
           'SYSTEM', TO_DATE('2022-03-04 08:53:29', 'YYYY-MM-DD HH24:MI:SS'), 'SYSTEM', TO_DATE('2022-03-04 08:53:29', 'YYYY-MM-DD HH24:MI:SS')
      FROM dual
    UNION ALL
    SELECT 'B10', 1611, '고객센터에서 작성한 테스트', 'Sample Content 1611', 'N',
           '20220303', '20221231', 'A', NULL, NULL, NULL, NULL,
           'SYSTEM', TO_DATE('2022-03-03 16:31:34', 'YYYY-MM-DD HH24:MI:SS'), 'SYSTEM', TO_DATE('2022-03-03 16:31:34', 'YYYY-MM-DD HH24:MI:SS')
      FROM dual
) source
ON (target.SRL_NUM = source.SRL_NUM)
WHEN MATCHED THEN
    UPDATE SET
        target.BOARD_CD = source.BOARD_CD,
        target.TITLE = source.TITLE,
        target.CNTT = source.CNTT,
        target.DEL_YN = source.DEL_YN,
        target.POST_STRT_DT = source.POST_STRT_DT,
        target.POST_END_DT = source.POST_END_DT,
        target.ORGN_TP_CD1 = source.ORGN_TP_CD1,
        target.ORGN_TP_CD2 = source.ORGN_TP_CD2,
        target.ORGN_TP_CD3 = source.ORGN_TP_CD3,
        target.ORGN_TP_CD4 = source.ORGN_TP_CD4,
        target.ORGN_TP_CD5 = source.ORGN_TP_CD5,
        target.REGST_ID = source.REGST_ID,
        target.REGST_DTTM = source.REGST_DTTM,
        target.RVISN_ID = source.RVISN_ID,
        target.RVISN_DTTM = source.RVISN_DTTM
WHEN NOT MATCHED THEN
    INSERT (
        BOARD_CD, SRL_NUM, TITLE, CNTT, DEL_YN,
        POST_STRT_DT, POST_END_DT,
        ORGN_TP_CD1, ORGN_TP_CD2, ORGN_TP_CD3, ORGN_TP_CD4, ORGN_TP_CD5,
        REGST_ID, REGST_DTTM, RVISN_ID, RVISN_DTTM
    )
    VALUES (
        source.BOARD_CD, source.SRL_NUM, source.TITLE, source.CNTT, source.DEL_YN,
        source.POST_STRT_DT, source.POST_END_DT,
        source.ORGN_TP_CD1, source.ORGN_TP_CD2, source.ORGN_TP_CD3, source.ORGN_TP_CD4, source.ORGN_TP_CD5,
        source.REGST_ID, source.REGST_DTTM, source.RVISN_ID, source.RVISN_DTTM
    );

EXIT;
SQL
