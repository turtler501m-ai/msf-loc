package com.ktmmobile.msf.domains.form.form.termination.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;

@Repository
public class CancelConsultRepositoryImpl {

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    public int countCancelConsult(CancelConsultDto cancelConsultDto) {
        return sqlSessionTemplate.selectOne("CancelConsultMapper.countCancelConsult", cancelConsultDto);
    }

    public int insertNmcpCustReqMst(CancelConsultDto cancelConsultDto) {
        return sqlSessionTemplate.insert("CancelConsultMapper.insertNmcpCustReqMst", cancelConsultDto);
    }

    public int insertCancelConsult(CancelConsultDto cancelConsultDto) {
        return sqlSessionTemplate.insert("CancelConsultMapper.insertCancelConsult", cancelConsultDto);
    }

    public List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto) {
        return sqlSessionTemplate.selectList("CancelConsultMapper.selectCancelConsultList", cancelConsultDto);
    }
}

