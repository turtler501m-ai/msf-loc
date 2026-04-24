package com.ktis.msp.batch.job.cmn.usrobjhst.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;

@Service
public class JdbcService extends BaseService {

	// MSP USER OBJECT 변경 이력 확인
	public int selectMspUserObject(String owner) throws Exception{
		
//		String strDBUrl = "jdbc:oracle:thin:@10.21.28.28:1521:MSPDEV";
    	String strDBUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=10.21.28.203)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.21.28.205)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=MSP)(GLOBAL_NAME=MSP)(failover_mode=(type=session)(method=basic))))";
    	String strDBID = owner;
    	String strDBPW = "ktmm0601!!";
    	
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		int result = 0;

		try {
			conn = DriverManager.getConnection(strDBUrl,strDBID,strDBPW);
			String query = 
					 "SELECT COUNT(*) AS COUNT "
					+ " FROM USER_OBJECTS A "
					+ " 	 , USER_SOURCE B "
					+ "WHERE A.OBJECT_NAME = B.NAME "
				    + "  AND TO_CHAR(A.LAST_DDL_TIME,'YYYYMMDD') BETWEEN TO_CHAR(SYSDATE-7,'YYYYMMDD') AND TO_CHAR(SYSDATE,'YYYYMMDD') "  
			;

			pst = conn.prepareStatement(query);

			rs = pst.executeQuery();
			
			while(rs.next()){
				result = rs.getInt("COUNT");
			}

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	// MSP USER OBJECT 변경 이력 INSERT
	public void insertMspUserObjectHst(String owner) throws Exception{

//		String strDBUrl = "jdbc:oracle:thin:@10.21.28.28:1521:MSPDEV";
		String strDBUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=10.21.28.203)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.21.28.205)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=MSP)(GLOBAL_NAME=MSP)(failover_mode=(type=session)(method=basic))))";
    	String strDBID = owner;
    	String strDBPW = "ktmm0601!!";
    	
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = DriverManager.getConnection(strDBUrl,strDBID,strDBPW);
			String query = 
					  "INSERT INTO CMN_USER_OBJECT_HST "
					+ "( "
					+ "SELECT A.OBJECT_NAME "
					+ "		  , ? "
					+ "		  , A.OBJECT_ID "
					+ "		  , A.OBJECT_TYPE "
					+ "		  , A.CREATED "
					+ "		  , A.LAST_DDL_TIME "
					+ "		  , B.LINE "
					+ "		  , B.TEXT "
					+ "		  , 'BATCH' "
					+ "		  , SYSDATE "
					+ "		  , 'BATCH' "
					+ "		  , SYSDATE "
					+ "  FROM USER_OBJECTS A "
					+ "       , USER_SOURCE B "
					+ " WHERE A.OBJECT_NAME = B.NAME "
					+ "   AND TO_CHAR(A.LAST_DDL_TIME,'YYYYMMDD') BETWEEN TO_CHAR(SYSDATE-7,'YYYYMMDD') AND TO_CHAR(SYSDATE,'YYYYMMDD') "
					+ ") " 
			;
			
			pst = conn.prepareStatement(query);
			int idx = 1;
			pst.setString(idx++, owner);

			pst.executeUpdate();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}	
	
/////////////////////////////////////////////////////////////////////////////////////////////////

	// MCP USER OBJECT 변경 이력 확인
	public int selectMcpUserObject(String owner) throws Exception{
		
//		String strDBUrl = "jdbc:oracle:thin:@10.220.71.231:1521:MCPDEV";
    	String strDBUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=10.220.71.239)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.220.71.239)(PORT=1522)))(CONNECT_DATA=(SERVICE_NAME=MCP)(GLOBAL_NAME=MCP)(failover_mode=(type=session)(method=basic))))";
    	String strDBID = owner;
    	String strDBPW = "ktmm0601!!";
    	
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		int result = 0;

		try {
			conn = DriverManager.getConnection(strDBUrl,strDBID,strDBPW);
			String query = 
					 "SELECT COUNT(*) AS COUNT "
					+ " FROM USER_OBJECTS A "
					+ " 	 , USER_SOURCE B "
					+ "WHERE A.OBJECT_NAME = B.NAME "
				    + "  AND TO_CHAR(A.LAST_DDL_TIME,'YYYYMMDD') BETWEEN TO_CHAR(SYSDATE-7,'YYYYMMDD') AND TO_CHAR(SYSDATE,'YYYYMMDD') " 
			;

			pst = conn.prepareStatement(query);

			rs = pst.executeQuery();
			
			if(rs.next()){
				result = rs.getInt("COUNT");
			}

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	// MCP USER OBJECT 변경 이력 INSERT
	public void insertMcpUserObjectHst(String owner) throws Exception{

//		String strDBUrl = "jdbc:oracle:thin:@10.220.71.231:1521:MCPDEV";
		String strDBUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=10.220.71.239)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.220.71.239)(PORT=1522)))(CONNECT_DATA=(SERVICE_NAME=MCP)(GLOBAL_NAME=MCP)(failover_mode=(type=session)(method=basic))))";
    	String strDBID = owner;
    	String strDBPW = "ktmm0601!!";
    	
		Connection conn = null;
		PreparedStatement pst = null;
		
		List<Map<String,Object>> resultMap = selectMcpUserObjectHst(owner);

		for(int i=0; i<resultMap.size(); i++){
			try {
				conn = DriverManager.getConnection(strDBUrl,strDBID,strDBPW);
				
				String query = 
						  "INSERT INTO CMN_USER_OBJECT_HST@DL_MSP "
						+ "( "
						+ "		OBJECT_NAME "
						+ "		, OWNER "
						+ "		, OBJECT_ID "
						+ "		, OBJECT_TYPE "
						+ "		, CREATED "
						+ "		, LAST_DDL_TIME "
						+ "		, SOURCE_LINE "
						+ "		, SOURCE_TEXT "
						+ "		, REGST_ID "
						+ "		, REGST_DTTM "   
						+ "		, RVISN_ID "
						+ "		, RVISN_DTTM "
						+ ") VALUES ( "
						+ "		? "
						+ "		, ? "
						+ "		, ? "
						+ "		, ? "
						+ "		, TO_DATE(?,'YYYYMMDDHH24MISS') "
						+ "		, TO_DATE(?,'YYYYMMDDHH24MISS') "
						+ "		, ? "
						+ "		, ? "
						+ "		, ? "
						+ "		, TO_DATE(?,'YYYYMMDDHH24MISS') "
						+ "		, ? "
						+ "		, TO_DATE(?,'YYYYMMDDHH24MISS') "
						+ ") " 
				;
				
				pst = conn.prepareStatement(query);
				int idx = 1;
				pst.setString(idx++, (String) resultMap.get(i).get("OBJECT_NAME"));
				pst.setString(idx++, owner);
				pst.setString(idx++, (String) resultMap.get(i).get("OBJECT_ID"));
				pst.setString(idx++, (String) resultMap.get(i).get("OBJECT_TYPE"));
				pst.setString(idx++, (String) resultMap.get(i).get("CREATED"));
				pst.setString(idx++, (String) resultMap.get(i).get("LAST_DDL_TIME"));
				pst.setString(idx++, (String) resultMap.get(i).get("SOURCE_LINE"));
				pst.setString(idx++, (String) resultMap.get(i).get("SOURCE_TEXT"));
				pst.setString(idx++, (String) resultMap.get(i).get("REGST_ID"));
				pst.setString(idx++, (String) resultMap.get(i).get("REGST_DTTM"));
				pst.setString(idx++, (String) resultMap.get(i).get("RVISN_ID"));
				pst.setString(idx++, (String) resultMap.get(i).get("RVISN_DTTM"));
				
				pst.executeUpdate();
				
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
		
	}	
		
	// MCP USER OBJECT 변경 이력 INSERT
	public List<Map<String,Object>> selectMcpUserObjectHst(String owner) throws Exception{

//		String strDBUrl = "jdbc:oracle:thin:@10.220.71.231:1521:MCPDEV";
		String strDBUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=10.220.71.239)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.220.71.239)(PORT=1522)))(CONNECT_DATA=(SERVICE_NAME=MCP)(GLOBAL_NAME=MCP)(failover_mode=(type=session)(method=basic))))";
    	String strDBID = owner;
    	String strDBPW = "ktmm0601!!";
    	
    	Map<String, Object> resultMap = null;
    	List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(strDBUrl,strDBID,strDBPW);
			String query = 
					  "SELECT A.OBJECT_NAME "
					+ "		  , A.OBJECT_ID "
					+ "		  , A.OBJECT_TYPE "
					+ "		  , TO_CHAR(A.CREATED,'YYYYMMDDHH24MISS') AS CREATED "
					+ "		  , TO_CHAR(A.LAST_DDL_TIME,'YYYYMMDDHH24MISS') AS LAST_DDL_TIME "
					+ "		  , B.LINE "
					+ "		  , B.TEXT "
					+ "		  , 'BATCH' AS REGST_ID "
					+ "		  , TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') AS REGST_DTTM "
					+ "		  , 'BATCH' AS RVISN_ID "
					+ "		  , TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') AS RVISN_DTTM "
					+ "  FROM USER_OBJECTS A "
					+ "       , USER_SOURCE B "
					+ " WHERE A.OBJECT_NAME = B.NAME "
					+ "   AND TO_CHAR(A.LAST_DDL_TIME,'YYYYMMDD') BETWEEN TO_CHAR(SYSDATE-7,'YYYYMMDD') AND TO_CHAR(SYSDATE,'YYYYMMDD') "
			;
			
			pst = conn.prepareStatement(query);

			rs = pst.executeQuery();
			
			
			while(rs.next()){
				resultMap = new HashMap<String, Object>();
				
				resultMap.put("OBJECT_NAME",rs.getString("OBJECT_NAME"));
				resultMap.put("OBJECT_ID",rs.getString("OBJECT_ID"));
				resultMap.put("OBJECT_TYPE",rs.getString("OBJECT_TYPE"));
				resultMap.put("CREATED",rs.getString("CREATED"));
				resultMap.put("LAST_DDL_TIME",rs.getString("LAST_DDL_TIME"));
				resultMap.put("SOURCE_LINE",rs.getString("LINE"));
				resultMap.put("SOURCE_TEXT",rs.getString("TEXT"));
				resultMap.put("REGST_ID",rs.getString("REGST_ID"));
				resultMap.put("REGST_DTTM",rs.getString("REGST_DTTM"));
				resultMap.put("RVISN_ID",rs.getString("RVISN_ID"));
				resultMap.put("RVISN_DTTM",rs.getString("RVISN_DTTM"));
				
				resultList.add(resultMap);
			}

			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return resultList;
		
	}	
		
	
}
