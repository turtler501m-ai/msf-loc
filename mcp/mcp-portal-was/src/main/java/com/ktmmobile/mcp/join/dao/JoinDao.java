package com.ktmmobile.mcp.join.dao;

import com.ktmmobile.mcp.common.dto.db.McpMrktHistDto;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.join.dto.UserAgentDto;


public interface JoinDao {

	public int idCheck(String checkId);

	public int insertMemberInfo(JoinDto joinDto);

	public int dupleChk(String pin);
	
    /**
     * <pre>
     * 설명     : 회원정보 법정대리인 정보 INSERT
     * @return
     * </pre>
     */
    public boolean insetUserAgent(UserAgentDto userAgent) ;

	public int insertSnsInfo(JoinDto joinDto);

	public JoinDto getPinToUserInfo(String pin);
	
	public JoinDto getUserToPinInfo(String userId);

	public int dormancyDupleChk(String pin);

	public int updateClausePriAdFlag(JoinDto joinDto);

    void updateMrktHist(McpMrktHistDto mrktHist);

	void insertMrktHist(McpMrktHistDto mcpMrktHistDto);
}
