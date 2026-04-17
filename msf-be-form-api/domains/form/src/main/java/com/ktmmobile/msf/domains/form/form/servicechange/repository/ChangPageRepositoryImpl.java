package com.ktmmobile.msf.domains.form.form.servicechange.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;

@Repository
public class ChangPageRepositoryImpl {

    @Autowired
    @Qualifier("sqlSession2")
    private SqlSessionTemplate sqlSession2;

    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        return sqlSession2.selectOne("ChangPageMapper.selectMspAddInfo", svcCntrNo);
    }

    public List<McpUserCntrMngDto> selectCntrList(Map<String, String> params) {
        return sqlSession2.selectList("ChangPageMapper.selectCntrList", params);
    }

    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        return sqlSession2.selectOne("ChangPageMapper.selectCntrListNoLogin", userCntrMngDto);
    }
}
