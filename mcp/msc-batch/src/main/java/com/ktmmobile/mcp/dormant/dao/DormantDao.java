package com.ktmmobile.mcp.dormant.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.dormant.dto.DormantDto;


@Repository
public class DormantDao {

	@Autowired
	@Qualifier(value="bootoraSqlSession")
	private SqlSession bootoraSqlSession;

	@Autowired
	@Qualifier(value="booteventSqlSession")
	private SqlSession booteventSqlSession;

	public List<DormantDto> selectDormantList() {
		return bootoraSqlSession.selectList("DormantMapper.selectDormantList");
	}

	public int deleteAssociateMember() {
		return bootoraSqlSession.delete("DormantMapper.deleteAssociateMember");
	}

	public int updateAssociateMember() {
		return bootoraSqlSession.update("DormantMapper.updateAssociateMember");
	}

	public List<DormantDto> selectDormantRegularList() {
		return bootoraSqlSession.selectList("DormantMapper.selectDormantRegularList");
	}

	public int insertDormantRegularMember(String userid) {
		return bootoraSqlSession.insert("DormantMapper.insertDormantRegularMember", userid);
	}

	public int deleteDormantRegularMember(String userid) {
		return bootoraSqlSession.delete("DormantMapper.deleteDormantRegularMember", userid);
	}

}
