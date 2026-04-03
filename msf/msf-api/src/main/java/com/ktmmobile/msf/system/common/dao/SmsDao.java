package com.ktmmobile.msf.system.common.dao;

import java.util.Map;


public interface SmsDao {

	/**
	 * 
	 * @param map
	 * @return
	 */
	public int selectMcpApplyCount(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public int selectMspApplyCount(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public String selectFindIdList(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public String selectPrepayment(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public String selectContractNoList(Map<String, Object> map);
	
}
