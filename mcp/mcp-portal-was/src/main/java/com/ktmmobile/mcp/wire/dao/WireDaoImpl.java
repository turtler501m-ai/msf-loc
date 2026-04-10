/**
 *
 */
package com.ktmmobile.mcp.wire.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.wire.dto.NmcpWireCounselDto;
import com.ktmmobile.mcp.wire.dto.NmcpWireProdDtlDto;

@Repository
public class WireDaoImpl implements WireDao{

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<NmcpWireProdDtlDto> selectWireProdDtlList(NmcpWireProdDtlDto nmcpWireProdDtlDto) {
		return sqlSessionTemplate.selectList("WireProdMapper.selectWireProdDtlList", nmcpWireProdDtlDto);
	}

	@Override
	public NmcpWireProdDtlDto selectWireProdCont(String wireProdCd) {
		return sqlSessionTemplate.selectOne("WireProdMapper.selectWireProdCont", wireProdCd);
	}

	@Override
	public NmcpWireProdDtlDto selectWireProdDtl(NmcpWireProdDtlDto nmcpWireProdDtlDto) {
		return sqlSessionTemplate.selectOne("WireProdMapper.selectWireProdDtl", nmcpWireProdDtlDto);
	}

	@Override
	public void insertNmcpWireCounsel(NmcpWireCounselDto nmcpWireCounselDto) {
		sqlSessionTemplate.insert("WireProdMapper.insertNmcpWireCounsel", nmcpWireCounselDto);
	}

}
