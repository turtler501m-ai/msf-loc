package com.ktmm.tcp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

public class DBManager {
	protected static final Logger logger = Logger.getLogger(ConnectionPool.class);
	
	private Connection conn;
	
	public void init(String dbDriver) throws ClassNotFoundException {
		
		// 드라이버를 로딩한다.
		Class.forName(dbDriver);
	}
	
	public void connect(String dbUrl, String dbUser, String dbPw) throws SQLException {
		
		conn = null;
		
		conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
		
		//return conn;
	}
	
	/**
	 * TCP연동로그 생성
	 * @param data
	 * @throws SQLException 
	 */
	public void insertOsstIfLog(String data) {
		PreparedStatement pstmt = null;
		
		try{
			String query = "INSERT INTO MSP_OSST_IF_LOG (IF_DTTM, IF_TYPE, IF_DATA) VALUES (TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), 'TCP', ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, data);
			
			pstmt.executeUpdate();
			
			conn.commit();
		}catch(SQLException e1){
			e1.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 연동이력 생성
	 * @param vo
	 */
	public void insertRequestOsst(DataVO vo){
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			
			sb.append("INSERT INTO MCP_REQUEST_OSST@DL_MCP (");
			sb.append("\n").append("MVNO_ORD_NO");
			sb.append("\n").append(", SEQ");
			sb.append("\n").append(", OSST_ORD_NO");
			sb.append("\n").append(", PRGR_STAT_CD");
			sb.append("\n").append(", CUST_ID");
			sb.append("\n").append(", SVC_CNTR_NO");
			sb.append("\n").append(", RSLT_CD");
			sb.append("\n").append(", RSLT_MSG");
			sb.append("\n").append(", RSLT_DT");
			sb.append("\n").append(", NSTEP_GLOBAL_ID");
			sb.append("\n").append(", PRDC_CHK_NOTI_MSG");
			sb.append("\n").append(", NP_BCNTR_TYPE_CD");
			sb.append("\n").append(", NP_FEE");
			sb.append("\n").append(", NP_NCHRG_AMT");
			sb.append("\n").append(", NP_PNLT_AMT");
			sb.append("\n").append(", NP_UNPAY_AMT");
			sb.append("\n").append(", NP_HNDST_INST_AMT");
			sb.append("\n").append(", NP_PREPAY_AMT");
			sb.append("\n").append(", NP_BASE_CHRG_AMT");
			sb.append("\n").append(", NP_NTNL_CHRG_AMT");
			sb.append("\n").append(", NP_INTL_CHRG_AMT");
			sb.append("\n").append(", NP_ADD_CHRG_AMT");
			sb.append("\n").append(", NP_ETC_CHRG_AMT");
			sb.append("\n").append(", NP_VAT");
			sb.append("\n").append(", NP_RMN_STRT_DT");
			sb.append("\n").append(", NP_RMN_END_DT");
			sb.append("\n").append(", DCLA_DEED_EFT_DT");
			sb.append("\n").append(", SBSC_LMT_QNTY");
			sb.append("\n").append(", SBSC_CIRCUIT_NUM");
			sb.append("\n").append(", DLNQ_AMT");
			sb.append("\n").append(", EICC_ID");
			sb.append("\n").append(", IF_TYPE");
			sb.append("\n").append(", REGST_DTTM");
			sb.append("\n").append(") VALUES ( ");
			sb.append("\n").append("?");		/* MVNO_ORD_NO */
			sb.append("\n").append(", NVL((SELECT MAX(SEQ) + 1 FROM MCP_REQUEST_OSST@DL_MCP WHERE MVNO_ORD_NO = ?), 1)");	/* SEQ */
			sb.append("\n").append(", ?");	/* OSST_ORD_NO */
			sb.append("\n").append(", ?");	/* PRGR_STAT_CD */
			sb.append("\n").append(", ?");	/* CUST_ID */
			sb.append("\n").append(", ?");	/* SVC_CNTR_NO */
			sb.append("\n").append(", ?");	/* RSLT_CD */
			sb.append("\n").append(", ?");	/* RSLT_MSG */
			sb.append("\n").append(", ?");	/* RSLT_DT */
			sb.append("\n").append(", ?");	/* NSTEP_GLOBAL_ID */
			sb.append("\n").append(", ?");	/* PRDC_CHK_NOTI_MSG */
			sb.append("\n").append(", ?");	/* NP_BCNTR_TYPE_CD */
			sb.append("\n").append(", ?");	/* NP_FEE */
			sb.append("\n").append(", ?");	/* NP_NCHRG_AMT */
			sb.append("\n").append(", ?");	/* NP_PNLT_AMT */
			sb.append("\n").append(", ?");	/* NP_UNPAY_AMT */
			sb.append("\n").append(", ?");	/* NP_HNDST_INST_AMT */
			sb.append("\n").append(", ?");	/* NP_PREPAY_AMT */
			sb.append("\n").append(", ?");	/* NP_BASE_CHRG_AMT */
			sb.append("\n").append(", ?");	/* NP_NTNL_CHRG_AMT */
			sb.append("\n").append(", ?");	/* NP_INTL_CHRG_AMT */
			sb.append("\n").append(", ?");	/* NP_ADD_CHRG_AMT */
			sb.append("\n").append(", ?");	/* NP_ETC_CHRG_AMT */
			sb.append("\n").append(", ?");	/* NP_VAT */
			sb.append("\n").append(", ?");	/* NP_RMN_STRT_DT */
			sb.append("\n").append(", ?");	/* NP_RMN_END_DT */
			sb.append("\n").append(", ?");	/* DCLA_DEED_EFT_DT */
			sb.append("\n").append(", ?");	/* SBSC_LMT_QNTY */
			sb.append("\n").append(", ?");	/* SBSC_CIRCUIT_NUM */
			sb.append("\n").append(", ?");	/* DLNQ_AMT */
			sb.append("\n").append(", ?");	/* EICC_ID */
			sb.append("\n").append(", 'TCP'");	/* IF_TYPE */
			sb.append("\n").append(", SYSDATE");
			sb.append("\n").append(")");
			
//			logger.debug("sql=" + sb.toString());
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getMvnoOrdNo());
			pstmt.setString(2, vo.getMvnoOrdNo());
			pstmt.setString(3, vo.getOsstOrdNo());
			pstmt.setString(4, vo.getPrgrStatCd());
			pstmt.setString(5, vo.getCustId());
			pstmt.setString(6, vo.getSvcCntrNo());
			pstmt.setString(7, vo.getRsltCd());
			pstmt.setString(8, vo.getRsltMsg());
			pstmt.setString(9, vo.getRsltDt());
			pstmt.setString(10, vo.getNstepGlobalId());
			pstmt.setString(11, vo.getRemark());
			pstmt.setString(12, vo.getNpBchngCmpnCntrTypeCd());
			pstmt.setString(13, vo.getNpFee());
			pstmt.setString(14, vo.getNpNchrgAmt());
			pstmt.setString(15, vo.getNpPnltAmt());
			pstmt.setString(16, vo.getNpUnpayAmt());
			pstmt.setString(17, vo.getNpHndstInstAmt());
			pstmt.setString(18, vo.getNpPrepayAmt());
			pstmt.setString(19, vo.getNpBaseChrgAmt());
			pstmt.setString(20, vo.getNpNtnlChrgAmt());
			pstmt.setString(21, vo.getNpIntlChrgAmt());
			pstmt.setString(22, vo.getNpAddChrgAmt());
			pstmt.setString(23, vo.getNpEtcChrgAmt());
			pstmt.setString(24, vo.getNpVat());
			pstmt.setString(25, vo.getNpRmnStrtDt());
			pstmt.setString(26, vo.getNpRmnEndDt());
			pstmt.setString(27, vo.getDclaDeedEftDt());
			pstmt.setString(28, vo.getSbscLmtQnty());
			pstmt.setString(29, vo.getSbscCircuitNum());
			pstmt.setString(30, vo.getDlnqAmt());
			pstmt.setString(31, vo.getEiccId());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
//				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 부가서비스 개통처리
	 * @param vo
	 */
	public void updateRequestAddition(DataVO vo){
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		
		
		try {
			StringBuilder sb = new StringBuilder();
			
			sb.append("UPDATE  MCP_REQUEST_ADDITION@DL_MCP");
			sb.append("\n").append("SET     PROC_YN = DECODE(?, '0000', 'Y', '')");
			sb.append("\n").append("WHERE   REQUEST_KEY = (SELECT REQUEST_KEY FROM MCP_REQUEST@DL_MCP WHERE RES_NO = TO_CHAR(TO_NUMBER(?)))");
			sb.append("\n").append("AND     PROC_YN = 'P'");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getRsltCd());
			pstmt.setString(2, vo.getMvnoOrdNo());
			
			logger.debug("updateRequestAddition start ================================================================");
			logger.debug("sql=" + sb.toString());
			logger.debug("getRsltCd=" + vo.getRsltCd());
			logger.debug("getMvnoOrdNo=" + vo.getMvnoOrdNo());
			logger.debug("updateRequestAddition end   ================================================================");
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e1) {
//			e1.printStackTrace();
			logger.error(e1.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 진행상태 UPDATE
	 * @param vo
	 */
	public void updateRequestState(DataVO vo){
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		
		
		try {
			StringBuilder sb = new StringBuilder();
			
			sb.append("UPDATE  MCP_REQUEST@DL_MCP");
			sb.append("\n").append("SET     REQUEST_STATE_CODE = (CASE WHEN ? = 'PC2' AND ? != '0000' THEN '30'"); 
			sb.append("\n").append("                                   ELSE (CASE WHEN ? = 'OP2' AND ? = '0000' THEN '21' ELSE '31' END) END)");
			sb.append("\n").append("        , CONTRACT_NUM = (CASE WHEN ? = 'OP2' AND ? = '0000' THEN ? ELSE '' END)");
			sb.append("\n").append("        , RVISN_DTTM = SYSDATE");
			sb.append("\n").append("WHERE   REQUEST_KEY = (SELECT REQUEST_KEY FROM MCP_REQUEST@DL_MCP WHERE RES_NO = TO_CHAR(TO_NUMBER(?)))");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getPrgrStatCd());
			pstmt.setString(2, vo.getRsltCd());
			pstmt.setString(3, vo.getPrgrStatCd());
			pstmt.setString(4, vo.getRsltCd());
			pstmt.setString(5, vo.getPrgrStatCd());
			pstmt.setString(6, vo.getRsltCd());
			pstmt.setString(7, vo.getSvcCntrNo());
			pstmt.setString(8, vo.getMvnoOrdNo());
			
			logger.debug("updateRequestState start ===================================================================");
			logger.debug("sql=" + sb.toString());
			logger.debug("getPrgrStatCd=" + vo.getPrgrStatCd());
			logger.debug("getMvnoOrdNo=" + vo.getMvnoOrdNo());
			logger.debug("getSvcCntrNo=" + vo.getSvcCntrNo());
			logger.debug("getRsltCd=" + vo.getRsltCd());
			logger.debug("updateRequestState end   ===================================================================");
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e1) {
//			e1.printStackTrace();
			logger.error(e1.getMessage());
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
//				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 선불직권해지
	 * @param param
	 */
	public void updatePpsCanUsrMst(Map<String, String> param) {
		
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE PPS_CAN_USR_MST SET TCP_RSLT_KEY=?");
			sb.append(", TCP_RSLT_CD=?");
			sb.append(", TCP_RSLT_MSG=?");
			sb.append(", TCP_RSLT_DTTM=SYSDATE");
			sb.append(", RVISN_ID='TCP'");
			sb.append(", RVISN_DTTM=SYSDATE");
			sb.append(" WHERE CONTRACT_NUM=?");
			sb.append(" AND CUSTOMER_ID=?");
			sb.append(" AND SUBSCRIBER_NO=?");
			
			logger.debug("선불직권해지=" + sb.toString());
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, param.get("OSST_ORD_NO"));
			pstmt.setString(2, param.get("RSLT_CD"));
			pstmt.setString(3, param.get("RSLT_MSG"));
			pstmt.setString(4, param.get("SVC_CNTR_NO"));
			pstmt.setString(5, param.get("CUST_NO"));
			pstmt.setString(6, param.get("TLPH_NO"));
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 선불직권해지프로시져CALL
	 * @param param
	 */
	public void callPpsCanUsrRes(Map<String, String> param) {
		try {
			CallableStatement cs = conn.prepareCall("{CALL P_PPS_CAN_USR_RES(?,?,?,'','','','',?,?,?)}");
			cs.setString(1, "TCP");
			cs.setString(2, param.get("SVC_CNTR_NO"));
			cs.setString(3, param.get("CUST_NO"));
			cs.setString(4, param.get("OSST_ORD_NO"));
			cs.setString(5, param.get("RSLT_CD"));
			cs.setString(6, param.get("RSLT_MSG"));
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 연결해제
	 */
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * [SRM18102259494] 개통간소화 개통 요청 후 지연시 KOS개통으로 인한 신청서 상태 변경
	 * 개통간소화 MP 지연으로 KOS 전산에서 개통시 신청서 상태변경되지 않도록 체크
	 */
	public String getOsstDelayOpenYn(String mvnoOrdNo) {
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		String contractNum = null;
		ResultSet rs = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			
			sb.append("SELECT  B.CONTRACT_NUM");
			sb.append("\n").append("FROM    MCP_REQUEST@DL_MCP A"); 
			sb.append("\n").append("        , MSP_JUO_SUB_INFO B");
			sb.append("\n").append("WHERE   A.RES_NO = TO_CHAR(TO_NUMBER(?))");
			sb.append("\n").append("AND     A.PSTATE = '00'");
			sb.append("\n").append("AND     B.SUB_STATUS <> 'C'");
			sb.append("\n").append("AND     (A.RES_NO = B.SRL_IF_ID OR A.REQ_USIM_SN = B.ICC_ID)");
			sb.append("\n").append("AND     NOT EXISTS (SELECT AA.RES_NO");
			sb.append("\n").append("                      FROM MCP_REQUEST@DL_MCP AA");
			sb.append("\n").append("                         , MSP_JUO_SUB_INFO BB");
			sb.append("\n").append("                     WHERE AA.REQ_USIM_SN = BB.ICC_ID");
			sb.append("\n").append("                       AND BB.ICC_ID = B.ICC_ID");
			sb.append("\n").append("                       AND AA.REQUEST_STATE_CODE = '21')");
			sb.append("\n").append("AND     ROWNUM = 1");
			
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, mvnoOrdNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				contractNum = rs.getString(1);
			}
			
			logger.debug("getOsstDelayOpenYn start ===================================================================");
			logger.debug("sql=" + sb.toString());
			logger.debug("getMvnoOrdNo=" + mvnoOrdNo);
			logger.debug("계약번호=" + contractNum);
			logger.debug("getOsstDelayOpenYn end   ===================================================================");
			
			rs.close();
			
		} catch (SQLException e1) {
//			e1.printStackTrace();
			logger.error(e1.getMessage());
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
//				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		
		return contractNum;
	}

	/**
	 * [SRM18102259494] 개통간소화 개통 요청 후 지연시 KOS개통으로 인한 신청서 상태 변경
	 * 개통간소화 MP 지연으로 KOS 전산에서 개통시 신청서 상태변경되지 않도록 체크
	 */
	public int selelctKtOrderId( Map<String, String> param) {
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		int KtOderId = 0;
		
		ResultSet rs = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			
			sb.append("SELECT COUNT(*)");
			sb.append("\n").append("FROM    MCP_REQUEST_NOW_DLVRY@DL_MCP"); 
			sb.append("\n").append("WHERE   KT_ORD_ID = ?");			
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, param.get("KT_ORDER_ID"));
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				KtOderId = rs.getInt(1);
			}
			
			logger.debug("selelctKtOrderId start ===================================================================");
			logger.debug("sql=" + sb.toString());
			logger.debug("KtOderId=" + KtOderId);
			logger.debug("selelctKtOrderId end   ===================================================================");
			
			rs.close();
			
		} catch (SQLException e1) {
//			e1.printStackTrace();
			logger.error(e1.getMessage());
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
//				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		
		return KtOderId;
	}

	/**
	 * 유심 셀프 변경 UC0
	 */
	public int selelctKtOrderIdUC0( Map<String, String> param) {
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		int KtOderId = 0;
		
		ResultSet rs = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			
			sb.append("SELECT COUNT(*)");
			sb.append("\n").append("FROM    MCP_SELF_USIM_CHG@DL_MCP");
			sb.append("\n").append("WHERE   SVC_CONT_ID = ?");
			sb.append("\n").append("AND   CUST_NO = ?");
			sb.append("\n").append("AND   TLPH_NO = ?");
			sb.append("\n").append("AND   OSST_ORD_NO = ?");
			sb.append("\n").append("AND   PRGR_STAT_CD = 'UC0'");			
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, param.get("SVC_CNTR_NO"));
			pstmt.setString(2, param.get("CUST_NO"));
			pstmt.setString(3, param.get("TLPH_NO"));
			pstmt.setString(4, param.get("OSST_ORD_NO"));
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				KtOderId = rs.getInt(1);
			}
			
			logger.debug("MVNO_ORD_NO start ===================================================================");
			logger.debug("sql=" + sb.toString());
			logger.debug("MVNO_ORD_NO=" + KtOderId);
			logger.debug("MVNO_ORD_NO end   ===================================================================");
			
			rs.close();
			
		} catch (SQLException e1) {
			logger.error(e1.getMessage());
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		
		return KtOderId;
	}
	
	/**
	 * 유심즉시배송 상테 업데이트
	 * @param param
	 */
	public void updateDvryDirMst(Map<String, String> param) {
		
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE MCP_REQUEST_NOW_DLVRY@DL_MCP SET DELIVERY_ORDER_ID =?");
			sb.append(", DLVRY_STATE_CODE =?");
			sb.append(", ORDER_STAT_RSN_DESC=?");
			sb.append(", RE_BIZ_ORG_CD=?");
			sb.append(", RE_ACCEPT_YN=?");
			sb.append(", CHANNEL_CD=?");
			sb.append(" WHERE KT_ORD_ID=?");
			
			logger.debug("유심즉시배송=" + sb.toString());
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, param.get("DELIVERY_ORDER_ID"));
			pstmt.setString(2, "0" + param.get("ORDER_STAT_CD"));
			pstmt.setString(3, param.get("ORDER_STAT_RSN_DESC"));
			pstmt.setString(4, param.get("BIZ_ORG_CD").trim());
			pstmt.setString(5, param.get("RE_ACCEPT_YN").trim());
			pstmt.setString(6, param.get("CHANNEL_CD").trim()); // 20220610 TOSS 채널 추가
			pstmt.setString(7, param.get("KT_ORDER_ID"));
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 유심셀프변경 업데이트
	 * @param param
	 */
	public void updateUC0(Map<String, String> param) {
		
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE MCP_SELF_USIM_CHG@DL_MCP SET PRGR_STAT_CD =?");
			sb.append(", TRGT_ATRIB_SBST=?");
			sb.append(", TRGT_FALU_MSG=?");
			sb.append(", TRGT_INSUR_MSG=?");
			sb.append(", RSLT_CD=?");
			sb.append(", RSLT_MSG=?");
			sb.append(", AMD_DT=SYSDATE");
			sb.append(" WHERE SVC_CONT_ID = ?");		
			sb.append("   AND   CUST_NO = ?");	
			sb.append("   AND   TLPH_NO = ?");
			sb.append("   AND   OSST_ORD_NO = ?");
			sb.append("   AND   PRGR_STAT_CD = 'UC0'");
			
			logger.debug("유심셀프변경=" + sb.toString());
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, param.get("PRGR_STAT_CD").trim());
			pstmt.setString(2, param.get("TRGT_ATRIB_SBST").trim());
			pstmt.setString(3, param.get("TRGT_FALU_MSG").trim());
			pstmt.setString(4, param.get("TRGT_INSUR_MSG").trim());
			pstmt.setString(5, param.get("RSLT_CD").trim());
			pstmt.setString(6, param.get("RSLT_MSG").trim());
			pstmt.setString(7, param.get("SVC_CNTR_NO"));
			pstmt.setString(8, param.get("CUST_NO"));
			pstmt.setString(9, param.get("TLPH_NO"));
			pstmt.setString(10, param.get("OSST_ORD_NO"));

			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 유심즉시배송 HIST INSERT
	 * @param param
	 */
	public void insertDvryDirHist(Map<String, String> param) {
		
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO MSP_DIRDVRY_USIM_HST (");
			sb.append("SEQ , KT_ORD_ID , DVRY_BUSI_ORD_ID , DLVRY_STATE_CODE ,RESULT_CD, REGST_DTTM ,  REQ_DATA , RECV_DATA, ORDER_STAT_RSN_DESC, RE_BIZ_ORG_CD, RE_ACCEPT_YN  )");
			sb.append("VALUES (");
			sb.append("NVL((SELECT MAX(SEQ) + 1 FROM MSP_DIRDVRY_USIM_HST WHERE KT_ORD_ID = ?), 1)");
			sb.append(",?");
			sb.append(",?");
			sb.append(",?");
			sb.append(",?");
			sb.append(",SYSDATE");
			sb.append(",?");
			sb.append(",?");
			sb.append(",?");
			sb.append(",?"); // 2022.03.25 컬럼 추가
			sb.append(",?)"); // 2022.03.25 컬럼 추가

			logger.debug("유심즉시배송HIST=" + sb.toString());
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, param.get("KT_ORDER_ID"));
			pstmt.setString(2, param.get("KT_ORDER_ID"));
			pstmt.setString(3, param.get("DELIVERY_ORDER_ID"));
			pstmt.setString(4, "0" + param.get("ORDER_STAT_CD"));
			pstmt.setString(5, param.get("RESULT_CD"));
			pstmt.setString(6, param.get("REQ_DATA"));
			pstmt.setString(7, param.get("RECV_DATA"));
			pstmt.setString(8, param.get("ORDER_STAT_RSN_DESC"));
			pstmt.setString(9, param.get("BIZ_ORG_CD").trim()); // 2022.03.25 컬럼 추가
			pstmt.setString(10, param.get("RE_ACCEPT_YN")); // 2022.03.25 컬럼 추가
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 바로배송  채널 데이터 등록(TOSS)
	 * @param param
	 */
	public void insertDvryChannel(Map<String, String> param) {
		
		// Statement를 가져온다.
		PreparedStatement pstmt = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO MCP_REQUEST_NOW_DLVRY@DL_MCP");
			sb.append("( SELF_DLVRY_IDX, KT_ORD_ID, DELIVERY_ORDER_ID, ORDER_STAT_CD, ORDER_STAT_RSN_DESC, BIZ_ORG_CD, RE_ACCEPT_YN, DLVRY_TEL_FN, DLVRY_TEL_MN, DLVRY_TEL_RN, DLVRY_POST, DLVRY_ADDR, DLVRY_ADDR_DTL,\r\n"
					+ "      CUST_INFO_AGREE_YN, RSV_ORDER_YN, RSV_ORDER_DT, ORDER_REQ_MSG, DLVRY_NAME, ENTY, ENTX, UNTACT, CHANNEL_CD, USIM_PROD_ID, CSTMR_NATIVE_RRN, CSTMR_NAME)");
			sb.append("VALUES (");
			sb.append("SQ_REQUEST_SELF_DLVR_KEY.NEXTVAL@DL_MCP, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '01', '0000000000000', ?) ");
			
			logger.debug("바로배송 채널 =" + sb.toString());
			
			String strKtOrderId = param.get("KT_ORDER_ID");   //KT 오더 ID
			String strDeliveryOrderId = param.get("DELIVERY_ORDER_ID");   //배달업체 오더 ID
			String strOrderStatCd = param.get("ORDER_STAT_CD");   //배달 상태 코드
			String strOrderStatRsnDesc = param.get("ORDER_STAT_RSN_DESC");   //배달 상태 변경 상세
			String strBizOrgCd = param.get("BIZ_ORG_CD");   //배달업체 코드
			String strReAcceptYn = param.get("RE_ACCEPT_YN");   //재접수 여부
			String strOrderRcvTlphNo = param.get("ORDER_RCV_TLPH_NO");   //수령고객연락처
			String strZipNo = param.get("ZIP_NO");   //우편번호
			String strTargetAddr1 = param.get("TARGET_ADDR1");   //기본주소
			String strTargetAddr2 = param.get("TARGET_ADDR2");   //상세주소
			String strCustInfoAgreeYn = param.get("CUST_INFO_AGREE_YN");   //개인정보제공동의 여부
			String strRsvOrderYn = param.get("RSV_ORDER_YN");   //배달예약여부
			String strRsvOrderDt = param.get("RSV_ORDER_DT");   //배달희망시간
			String strOrderReqMsg = param.get("ORDER_REQ_MSG");   //배달요청메세지
			String strCustNm = param.get("CUST_NM");   //고객명
			String strTargetAddrLat = param.get("TARGET_ADDR_LAT");   //고객 위치 좌표. decimal 형. 위도 (WGS84)
			String strTargetAddrLng = param.get("TARGET_ADDR_LNG");   //고객 위치 좌표. decimal 형. 경도 (WGS84)
			String strUntact = param.get("UNTACT");   //비대면 여부
			String strChannelCd = param.get("CHANNEL_CD");   //접수채널
			
			
//			String strAddrTypeCd = param.get("ADDR_TYPE_CD");   //주소유형 미사용
			
			String telFn = "";
			String telMn = "";
			String telRn = "";
			if(strOrderRcvTlphNo.length() == 11) {
				telFn = strOrderRcvTlphNo.substring(0, 3);
				telMn = strOrderRcvTlphNo.substring(3, 7);
				telRn = strOrderRcvTlphNo.substring(7, 11); 
			}
					
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, strKtOrderId.trim()); //KT 오더 ID
			pstmt.setString(2, strDeliveryOrderId.trim()); //배달업체 오더 ID
			pstmt.setString(3, strOrderStatCd.trim()); //배달 상태 코드
			pstmt.setString(4, strOrderStatRsnDesc.trim()); //배달 상태 변경 상세
			pstmt.setString(5, strBizOrgCd.trim()); //배달업체 코드
			pstmt.setString(6, strReAcceptYn.trim()); //재접수 여부
			pstmt.setString(7, telFn.trim()); //수령고객연락처 앞 3자리
			pstmt.setString(8, telMn.trim()); //수령고객연락처 중간 4자리
			pstmt.setString(9, telRn.trim()); //수령고객연락처 뒤 4자리
			pstmt.setString(10, strZipNo.trim()); //우편번호
			pstmt.setString(11, strTargetAddr1.trim()); //기본주소
			pstmt.setString(12, strTargetAddr2.trim()); //상세주소
			pstmt.setString(13, strCustInfoAgreeYn.trim()); //개인정보제공동의 여부
			pstmt.setString(14, strRsvOrderYn.trim()); //배달예약여부
			pstmt.setString(15, strRsvOrderDt.trim()); //배달희망시간
			pstmt.setString(16, strOrderReqMsg.trim()); //배달요청메세지
			pstmt.setString(17, strCustNm.trim()); //고객명
			pstmt.setString(18, strTargetAddrLat.trim()); //고객 위치 좌표. decimal 형. 위도 (WGS84)
			pstmt.setString(19, strTargetAddrLng.trim()); //고객 위치 좌표. decimal 형. 경도 (WGS84)
			pstmt.setString(20, strUntact.trim()); //비대면 여부
			pstmt.setString(21, strChannelCd.trim()); //접수채널
			pstmt.setString(22, strCustNm.trim()); //고객명
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *  EP0(해지 요청) 처리 결과 UPDATE
	 */
	public void updateMspCanTrg(Map<String, String> param) {

		// Statement를 가져온다.
		PreparedStatement pstmt = null;

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE MSP_CAN_REQ");
			sb.append("   SET TCP_PRGR_STAT_CD = ?");
			sb.append("       , TCP_RSLT_CD = ?");
			sb.append("       , TCP_RSLT_MSG = ?");
			sb.append("       , TCP_RSLT_DTTM = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')");
			sb.append(" WHERE SVC_CNTR_NO = ?");
			sb.append("   AND OSST_ORD_NO = ?");

			logger.debug("EP0(해지 요청) 처리 결과 UPDATE=" + sb.toString());

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, param.get("PRGR_STAT_CD"));
			pstmt.setString(2, param.get("RSLT_CD"));
			pstmt.setString(3, param.get("RSLT_MSG"));
			pstmt.setString(4, param.get("SVC_CNTR_NO"));
			pstmt.setString(5, param.get("OSST_ORD_NO"));

			pstmt.executeUpdate();

			conn.commit();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

    public String selectMvnoOrdNo(Map<String, String> param) {
        // Statement를 가져온다.
        PreparedStatement pstmt = null;
        String mvnoOrdNo = null;

        ResultSet rs = null;

        try {
            StringBuilder sb = new StringBuilder();

            sb.append("SELECT MVNO_ORD_NO FROM MCP_REQUEST_OSST@DL_MCP ");
            sb.append("\n").append("WHERE OSST_ORD_NO = ? ");
            sb.append("\n").append("AND PRGR_STAT_CD = 'MC0' ");
            sb.append("\n").append("AND ROWNUM = 1 ");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, param.get("OSST_ORD_NO"));

            rs = pstmt.executeQuery();
            while(rs.next()) {
                mvnoOrdNo = rs.getString(1);
            }

            logger.debug("selectMvnoOrdNo start ===================================================================");
            logger.debug("sql=" + sb.toString());
            logger.debug("MVNO_ORD_NO=" + mvnoOrdNo);
            logger.debug("selectMvnoOrdNo end   ===================================================================");

            rs.close();

        } catch (SQLException e1) {
            logger.error(e1.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }

        return mvnoOrdNo;
    }

    public void updateNameChg(DataVO vo){
        // Statement를 가져온다.
        PreparedStatement pstmt = null;

        try {
            StringBuilder sb = new StringBuilder();

            //양수인청구계정번호 update
            //osst 처리결과에 따라 진행상태 변경
            //rsltCd : RQ(요청), CP(처리), BK(반려)
            //stateCd : 04(사전체크오류), 05(명의변경대기), 06(명의변경오류), 07(명의변경완료)
            String rsltCd = "RQ";
            String stateCd = "05";

            //사전체크결과일때
            if("MC2".equals(vo.getPrgrStatCd())) {
                if(!"0000".equals(vo.getRsltCd())) {
                    stateCd = "04";
                }
            }else if("MP2".equals(vo.getPrgrStatCd())) {
                if("0000".equals(vo.getRsltCd())) {
                    stateCd = "07";
                }else {
                    rsltCd = "BK";
                    stateCd = "06";
                }
            }

            if("MC2".equals(vo.getPrgrStatCd())) {
                sb.append("UPDATE NMCP_CUST_REQUEST_NAME_CHG@DL_MCP ");
                sb.append("\n").append("SET PROC_CD = ? ");
                sb.append("\n").append(", MCN_STATE_CODE = ? ");
                sb.append("\n").append(", RCV_CUST_NO = ? ");
                sb.append("\n").append(", RCV_BILL_ACNT_NO = ? ");
                sb.append("\n").append(", RVISN_ID = 'OSST' ");
                sb.append("\n").append(", RVISN_DTTM = SYSDATE ");
                sb.append("\n").append("WHERE MCN_RES_NO = LTRIM(?, '0') ");

                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, rsltCd);
                pstmt.setString(2, stateCd);
                pstmt.setString(3, vo.getRcvCustNo());
                pstmt.setString(4, vo.getRcvBillAcntNo());
                pstmt.setString(5, vo.getMvnoOrdNo());
            }else {
                sb.append("UPDATE NMCP_CUST_REQUEST_NAME_CHG@DL_MCP ");
                sb.append("\n").append("SET PROC_CD = ? ");
                sb.append("\n").append(", MCN_STATE_CODE = ? ");
                sb.append("\n").append(", RVISN_ID = 'OSST' ");
                sb.append("\n").append(", RVISN_DTTM = SYSDATE ");
                sb.append("\n").append("WHERE MCN_RES_NO = LTRIM(?, '0') ");

                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, rsltCd);
                pstmt.setString(2, stateCd);
                pstmt.setString(3, vo.getMvnoOrdNo());
            }

            logger.debug("updateNameChg start ================================================================");
            logger.debug("sql=" + sb.toString());
            logger.debug("getRsltCd=" + vo.getRsltCd());
            logger.debug("getMvnoOrdNo=" + vo.getMvnoOrdNo());
            logger.debug("updateNameChg end   ================================================================");

            pstmt.executeUpdate();

            conn.commit();

        } catch (SQLException e1) {
            logger.error(e1.getMessage());
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}