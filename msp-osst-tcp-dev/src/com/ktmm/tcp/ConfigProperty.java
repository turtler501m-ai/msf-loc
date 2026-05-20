package com.ktmm.tcp;

public class ConfigProperty {
	
	// --- DB 접속정보
	private String dbdriver;
	private String dburl;
	private String dbuser;
	private String dbpass;
	
	// 개발 DB 접속정보로 세팅 신규 PRX 장비용 CIP
	public void InitDev() {
		this.dbdriver = "oracle.jdbc.driver.OracleDriver";
		this.dburl = "jdbc:oracle:thin:@10.21.28.28:1521:MSPDEV";
		this.dbuser = "MSP_WAS";
		this.dbpass = "ktmm0601!!";
	}
	
	// 운영 DB 접속정보로 세팅 신규 PRX 장비용 CIP
	public void InitProd() {
		this.dbdriver = "oracle.jdbc.driver.OracleDriver";
		this.dburl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=10.21.28.203)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.21.28.205)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=MSP)(GLOBAL_NAME=MSP)(failover_mode=(type=session)(method=basic))))";
		this.dbuser = "MSP_WAS";
		this.dbpass = "ktmm0601!!";
	}
	
	// 개발 DB 접속정보로 세팅 구 PRX 장비용
//	public void InitDev() {
//		this.dbdriver = "oracle.jdbc.driver.OracleDriver";
//		this.dburl = "jdbc:oracle:thin:@10.220.71.231:2521:MSPDEV";
//		this.dbuser = "MSP_WAS";
//		this.dbpass = "ktmm0601!!";
//	}
	
	// 운영 DB 접속정보로 세팅 구 PRX 장비용
//	public void InitProd() {
//		this.dbdriver = "oracle.jdbc.driver.OracleDriver";
//		this.dburl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=10.220.71.239)(PORT=1523))(ADDRESS=(PROTOCOL=TCP)(HOST=10.220.71.239)(PORT=1524)))(CONNECT_DATA=(SERVICE_NAME=MSP)(GLOBAL_NAME=MSP)(failover_mode=(type=session)(method=basic))))";
//		this.dbuser = "MSP_WAS";
//		this.dbpass = "ktmm0601!!";
//	}
	
	/**
	 * @return the dbdriver
	 */
	public String getDbdriver() {
		return dbdriver;
	}

	/**
	 * @param dbdriver the dbdriver to set
	 */
	public void setDbdriver(String dbdriver) {
		this.dbdriver = dbdriver;
	}

	/**
	 * @return the dburl
	 */
	public String getDburl() {
		return dburl;
	}

	/**
	 * @param dburl the dburl to set
	 */
	public void setDburl(String dburl) {
		this.dburl = dburl;
	}

	/**
	 * @return the dbuser
	 */
	public String getDbuser() {
		return dbuser;
	}

	/**
	 * @param dbuser the dbuser to set
	 */
	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}

	/**
	 * @return the dbpass
	 */
	public String getDbpass() {
		return dbpass;
	}

	/**
	 * @param dbpass the dbpass to set
	 */
	public void setDbpass(String dbpass) {
		this.dbpass = dbpass;
	}
	
	
}
