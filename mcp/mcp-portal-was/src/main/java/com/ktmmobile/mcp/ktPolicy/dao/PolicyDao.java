package com.ktmmobile.mcp.ktPolicy.dao;

import java.util.List;

import com.ktmmobile.mcp.common.dto.PersonalPolicyDto;
import com.ktmmobile.mcp.ktPolicy.dto.PolicyDto;

public interface PolicyDao {

	public int getTotalCount(PolicyDto policyDto);

	public List<PolicyDto> policyBoardList(PolicyDto policyDto, int skipResult, int maxResult);

	public List<PolicyDto> privacyBoardList(PolicyDto policyDto, int skipResult, int maxResult);

	public List<PolicyDto> youngBoardList(PolicyDto policyDto);
	
	public List<PolicyDto> getPolicyGuide(PolicyDto policyDto);

	public int getPriTotalCount(PolicyDto policyDto);

	public PolicyDto viewPrivacyBoard(PolicyDto policyDto);


	/**
	 * 이용약관 상세
	 * @param stpltSeq
	 * @return
	 */
	public PolicyDto viewKtPolicy(PolicyDto policyDto);

	/**
	 * 개인정보 취급방침 상세팝업
	 * @param stpltSeq
	 * @return
	 */
	public PolicyDto privacyViewPop(PolicyDto policyDto);

	public void histPlusCount(PersonalPolicyDto personalPolicyDto);

}
