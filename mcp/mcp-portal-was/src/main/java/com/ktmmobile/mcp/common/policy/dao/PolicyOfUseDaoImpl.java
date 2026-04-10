package com.ktmmobile.mcp.common.policy.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.board.dto.FileDownloadDto;


@Repository
public class PolicyOfUseDaoImpl implements PolicyOfUseDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public FileDownloadDto getPolicyFile(int attrSeq) {
		return sqlSessionTemplate.selectOne("PolicyOfUseMapper.getPolicyFile", attrSeq);
	}

}
