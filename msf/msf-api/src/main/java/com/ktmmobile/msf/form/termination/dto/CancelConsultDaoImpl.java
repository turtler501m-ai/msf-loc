package com.ktmmobile.msf.form.servicechange.dao;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.form.servicechange.dto.CancelConsultDto;

@Repository
public class CancelConsultDaoImpl implements CancelConsultDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int countCancelConsult(CancelConsultDto cancelConsultDto) {
        return sqlSessionTemplate.selectOne("CancelConsultMapper.countCancelConsult",cancelConsultDto);
    }

    @Override
    public int insertNmcpCustReqMst(CancelConsultDto cancelConsultDto) {
        return sqlSessionTemplate.insert("CancelConsultMapper.insertNmcpCustReqMst",cancelConsultDto);
    }

    @Override
    public int insertCancelConsult(CancelConsultDto cancelConsultDto) {
        return sqlSessionTemplate.insert("CancelConsultMapper.insertCancelConsult",cancelConsultDto);
    }

    @Override
    public List<CancelConsultDto> selectCancelConsultList(CancelConsultDto cancelConsultDto) {
        return sqlSessionTemplate.selectList("CancelConsultMapper.selectCancelConsultList",cancelConsultDto);
    }

}
