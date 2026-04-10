package com.ktmmobile.mcp.send.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.send.dto.SendScanDto;

@Repository
public class SendScanMapper {

	@Autowired
	@Qualifier(value="bootoraSqlSession")
	private SqlSession bootoraSqlSession;

	public List<SendScanDto> selectSendScanList(){
		return bootoraSqlSession.selectList("SendScanMapper.selectSendScanList");
	}

}
