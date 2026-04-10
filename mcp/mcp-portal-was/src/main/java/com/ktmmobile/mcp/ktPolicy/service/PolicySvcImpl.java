package com.ktmmobile.mcp.ktPolicy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.common.dto.PersonalPolicyDto;
import com.ktmmobile.mcp.ktPolicy.dao.PolicyDao;
import com.ktmmobile.mcp.ktPolicy.dto.PolicyDto;

/**
 * @Class Name : PolicySvcImpl
 * @Description : 정책 및 약관 구현체
 *
 * @author : ant
 * @Create Date : 2016. 2. 26
 */
@Service
public class PolicySvcImpl implements PolicySvc{

	@Autowired
	PolicyDao policyDao;

	@Override
	public int getTotalCount(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return policyDao.getTotalCount(policyDto);
	}

	@Override
	public List<PolicyDto> policyBoardList(PolicyDto policyDto, int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		return policyDao.policyBoardList(policyDto,skipResult, maxResult);
	}

	@Override
	public List<PolicyDto> privacyBoardList(PolicyDto policyDto, int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		return policyDao.privacyBoardList(policyDto,skipResult, maxResult);
	}

	@Override
	public List<PolicyDto> youngBoardList(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return policyDao.youngBoardList(policyDto);
	}
	
	@Override
	public List<PolicyDto> getPolicyGuide(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return policyDao.getPolicyGuide(policyDto);
	}

	@Override
	public int getPriTotalCount(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return policyDao.getPriTotalCount(policyDto);
	}

	@Override
	public PolicyDto viewPrivacyBoard(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return policyDao.viewPrivacyBoard(policyDto);
	}

	@Override
	public PolicyDto viewKtPolicy(PolicyDto policyDto) {
		return policyDao.viewKtPolicy(policyDto);
	}

	@Override
	public PolicyDto privacyViewPop(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return policyDao.privacyViewPop(policyDto);
	}

	@Override
	public void histPlusCount(PersonalPolicyDto personalPolicyDto) {
		policyDao.histPlusCount(personalPolicyDto);
	}

}
