package com.ktmmobile.mcp.ktPolicy.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.common.dto.PersonalPolicyDto;
import com.ktmmobile.mcp.ktPolicy.dto.PolicyDto;

/**
 * @Class Name : PolicySvcImpl
 * @Description : 정책 및 약관 Dao 구현클래스
 *
 * @author : ant
 * @Create Date : 2016. 2. 26
 */

@Repository
public class PolicyDaoImpl implements PolicyDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int getTotalCount(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return (Integer)sqlSessionTemplate.selectOne("PolicyMapper.getTotalCount", policyDto);
	}

	@Override
	public List<PolicyDto> policyBoardList(PolicyDto policyDto, int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("PolicyMapper.policyBoardList", policyDto, new RowBounds(skipResult, maxResult));
	}

	@Override
	public List<PolicyDto> privacyBoardList(PolicyDto policyDto,int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("PolicyMapper.privacyBoardList", policyDto, new RowBounds(skipResult, maxResult));
	}

	@Override
	public List<PolicyDto> youngBoardList(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("PolicyMapper.youngBoardList", policyDto);
	}
	
	@Override
	public List<PolicyDto> getPolicyGuide(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("PolicyMapper.getPolicyGuide", policyDto);
	}

	@Override
	public int getPriTotalCount(PolicyDto policyDto) {
		// TODO Auto-generated method stub
		return (Integer)sqlSessionTemplate.selectOne("PolicyMapper.getPriTotalCount", policyDto);
	}

	@Override
	public PolicyDto viewPrivacyBoard(PolicyDto policyDto) {
		
		return sqlSessionTemplate.selectOne("PolicyMapper.viewPrivacyBoard", policyDto);
	}

	@Override
	public PolicyDto viewKtPolicy(PolicyDto policyDto) {
		return sqlSessionTemplate.selectOne("PolicyMapper.viewKtPolicy", policyDto);
	}

	@Override
	public PolicyDto privacyViewPop(PolicyDto policyDto) {		
		return sqlSessionTemplate.selectOne("PolicyMapper.privacyViewPop", policyDto);
	}
	
	/**
	 * 개인정보 취급방침 조회수 증가
	 */
	@Override
	public void histPlusCount(PersonalPolicyDto personalPolicyDto) {
		sqlSessionTemplate.update("PolicyMapper.hitCntPlus", personalPolicyDto);
	}

}
