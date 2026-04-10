/**
 *
 */
package com.ktmmobile.msf.domains.form.form.servicechange.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.servicechange.dto.MPayViewDto;

/**
 * @author hsy
 *
 */
@Repository
public class MPayViewDaoImpl implements MPayViewDao{

	private static Logger logger = LoggerFactory.getLogger(MPayViewDaoImpl.class);
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    

	/**
	 * 소액결제 내역
	 */
	@Override
	public List<MPayViewDto> selectMPayList(MPayViewDto mPayViewDto) {
		return sqlSessionTemplate.selectList("MPayViewMapper.selectMPayList", mPayViewDto);
	}

	/**
	 * 소액결제 조회 날짜 리스트
	 */
	@Override
	public List<Map<String, String>> getDateListFromOpening() {
		return sqlSessionTemplate.selectList("MPayViewMapper.getDateListFromOpening");
	}

	/**
	 * 소액결제 차단여부 조회
	 */
	@Override
	public String getMpayAgree(MPayViewDto mPayViewDto) {
		return sqlSessionTemplate.selectOne("MPayViewMapper.getMpayAgree", mPayViewDto);
	}

	/**
	 * 소액결제 한도조정 내역 조회
	 */
	@Override
	public String getMpayAdjAmt(MPayViewDto mPayViewDto) {
		return sqlSessionTemplate.selectOne("MPayViewMapper.getMpayAdjAmt", mPayViewDto);
	}

	/**
	 * 최근 결제내역 1건 조회하여 최근 소액결제 한도 확인
	 */
	@Override
	public String getLastTmonLmtAmt(MPayViewDto mPayViewDto) {
		return sqlSessionTemplate.selectOne("MPayViewMapper.getLastTmonLmtAmt", mPayViewDto);
	}

   
}
