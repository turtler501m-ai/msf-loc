/**
 *
 */
package com.ktmmobile.msf.form.ownerchange.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ktmmobile.msf.form.ownerchange.dto.MyNameChgReqDto;

/**
 * @author ANT_FX700_02
 *
 */
@Repository
public class MyNameChgDaoImpl implements MyNameChgDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertNmcpCustReqMst(MyNameChgReqDto myNameChgReqDto) {
		return sqlSessionTemplate.insert("MyNameChgMapper.insertNmcpCustReqMst", myNameChgReqDto);
	}

	@Override
	public int insertNmcpCustReqNameChg(MyNameChgReqDto myNameChgReqDto) {
		return sqlSessionTemplate.insert("MyNameChgMapper.insertNmcpCustReqNameChg", myNameChgReqDto);
	}

	@Override
	public int insertNmcpCustReqNameChgAgent(MyNameChgReqDto myNameChgReqDto) {
		return sqlSessionTemplate.insert("MyNameChgMapper.insertNmcpCustReqNameChgAgent", myNameChgReqDto);
	}

	@Override
	public int updateNmcpCustReqMst(MyNameChgReqDto myNameChgReqDto) {
		return sqlSessionTemplate.insert("MyNameChgMapper.updateNmcpCustReqMst", myNameChgReqDto);
	}

	@Override
	public int updateNmcpCustReqNameChg(MyNameChgReqDto myNameChgReqDto) {
		return sqlSessionTemplate.insert("MyNameChgMapper.updateNmcpCustReqNameChg", myNameChgReqDto);
	}

}
