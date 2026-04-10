package com.ktmmobile.msf.domains.form.common.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;


@Repository
public class SmsDaoImpl implements SmsDao{

	private static final Logger logger = LoggerFactory.getLogger(SmsDaoImpl.class);

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Value("${api.interface.server}")
    private String apiInterfaceServer;
	
	@Override
	public int selectMcpApplyCount(Map<String, Object> map) {
		return (Integer)sqlSessionTemplate.selectOne("smsSql.selectMcpApplyCount", map);
	}

	@Override
	public int selectMspApplyCount(Map<String, Object> map) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(apiInterfaceServer + "/sms/mspApplyCount", map, Integer.class); // smsSql.selectMspApplyCount
	}

	@Override
	public String selectFindIdList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectPrepayment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectContractNoList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
