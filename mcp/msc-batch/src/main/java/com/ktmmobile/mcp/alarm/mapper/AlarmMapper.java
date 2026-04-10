package com.ktmmobile.mcp.alarm.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.alarm.dto.AlarmDto;

@Repository
public class AlarmMapper {

	@Autowired
	@Qualifier(value="bootoraSqlSession")
	private SqlSession bootoraSqlSession;

	public List<AlarmDto> selectNotiInfo(){
		return bootoraSqlSession.selectList("AlarmMapper.selectNotiInfo");
	}

	public void updateNotiInfo(AlarmDto dto){
		bootoraSqlSession.update("AlarmMapper.updateNotiInfo", dto);
	};

	public void  deleteNotiInfo(){
		bootoraSqlSession.delete("AlarmMapper.deleteNotiInfo");
	};


}
