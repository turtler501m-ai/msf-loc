package com.ktmmobile.mcp.cert.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CertDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;
    
    public String getCertDelDay() {
    	return bootoraSqlSession.selectOne("CertMapper.getCertDelDay");
    }

    public int deleteCertFailData(int delDay) {
        return bootoraSqlSession.delete("CertMapper.deleteCertFailData", delDay);
    }
}
