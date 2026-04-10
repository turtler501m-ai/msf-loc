package com.ktmmobile.mcp.join.service;

import com.ktmmobile.mcp.common.dto.db.McpMrktHistDto;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.join.dto.UserAgentDto;

import java.util.List;


public interface JoinSvc {

	public int idCheck(String checkId);

	public void insertMemberInfo(JoinDto joinDto);

	public int dupleChk(String pin);
	
    /**
     * <pre>
     * 설명     : 회원정보 법정대리인 정보 INSERT
     * @return
     * </pre>
     */
    public boolean insetUserAgent(UserAgentDto userAgent) ;

	public void insertSnsInfo(JoinDto joinDto);

	public JoinDto getPinToUserInfo(String pin);
	
	public JoinDto getUserToPinInfo(String userId);

	public int dormancyDupleChk(String dupInfo);

	public int updateClausePriAdFlag(JoinDto joinDto);

	void insertUpdateMrktHist(JoinDto joinDto);
}
