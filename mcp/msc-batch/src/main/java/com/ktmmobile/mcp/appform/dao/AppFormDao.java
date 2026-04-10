package com.ktmmobile.mcp.appform.dao;

import com.ktmmobile.mcp.appform.dto.AppFormDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppFormDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession sqlSession;


    public List<AppFormDto> selectDeleteMaterials() {
        List<AppFormDto> test = this.sqlSession.selectList("AppFormMapper.selectDeleteMaterials");
        return test;
    }

    public int deleteMcpRequest(String requestKey) {
        int test = this.sqlSession.delete("AppFormMapper.deleteMcpRequest", requestKey);
        return test;
    }

    public int deleteMcpRequestCstmr(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestCstmr", requestKey);
    }

    public int deleteMcpRequestSaleinfo(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestSaleinfo", requestKey);
    }

    public int deleteMcpRequestDlvry(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestDlvry", requestKey);
    }

    public int deleteMcpRequestReq(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestReq", requestKey);
    }

    public int deleteMcpRequestMove(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestMove", requestKey);
    }

    public int deleteMcpRequestPayment(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestPayment", requestKey);
    }

    public int deleteMcpRequestAgent(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestAgent", requestKey);
    }

    public int deleteMcpRequestState(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestState", requestKey);
    }

    public int deleteMcpRequestChange(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestChange", requestKey);
    }

    public int deleteMcpRequestDvcChg(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestDvcChg", requestKey);
    }

    public int deleteMcpRequestAddition(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestAddition", requestKey);
    }

    public int deleteMcpRequestOsst(String resNo) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestOsst", resNo);
    }

    public int deleteMcpRequestKtInter(String resNo) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestKtInter", resNo);
    }

    public int deleteMcpRequestDtl(String requestKey) {
        return this.sqlSession.delete("AppFormMapper.deleteMcpRequestDtl", requestKey);
    }
}
