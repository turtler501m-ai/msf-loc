package com.ktmmobile.msf.domains.form.form.termination.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;

@Repository
public class CancelPageRepositoryImpl {

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    @Qualifier("sqlSession2")
    private SqlSessionTemplate sqlSession2;

    public Long nextRequestKey() {
        return sqlSessionTemplate.selectOne("CancelPageMapper.nextRequestKey");
    }

    public int insertRequestCancel(TerminationApplyReqDto dto) {
        return sqlSessionTemplate.insert("CancelPageMapper.insertRequestCancel", dto);
    }

    public int selectPrePayment(String contractNum) {
        Integer count = sqlSession2.selectOne("MspCancelPageMapper.selectPrePayment", contractNum);
        return count == null ? 0 : count;
    }
}

