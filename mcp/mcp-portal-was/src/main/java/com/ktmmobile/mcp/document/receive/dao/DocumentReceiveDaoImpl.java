package com.ktmmobile.mcp.document.receive.dao;

import com.ktmmobile.mcp.document.receive.dto.DocRcvDetailDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvItemDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvUrlOtpDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DocumentReceiveDaoImpl implements DocumentReceiveDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public DocRcvDetailDto getDocRcvDetail(String docRcvId) {
        return sqlSessionTemplate.selectOne("DocumentReceiveMapper.getDocRcvDetail", docRcvId);
    }

    @Override
    public int increaseUrlOtpFailCount(DocRcvUrlOtpDto docRcvUrlOtpDto) {
        return sqlSessionTemplate.update("DocumentReceiveMapper.increaseUrlOtpFailCount", docRcvUrlOtpDto);
    }

    @Override
    public int updateUrlOtpAuth(DocRcvUrlOtpDto docRcvUrlOtpDto) {
        return sqlSessionTemplate.update("DocumentReceiveMapper.updateUrlOtpAuth", docRcvUrlOtpDto);
    }

    @Override
    public DocRcvUrlOtpDto getDocRcvUrlOtp(DocRcvUrlOtpDto docRcvUrlOtpDto) {
        return sqlSessionTemplate.selectOne("DocumentReceiveMapper.getDocRcvUrlOtp", docRcvUrlOtpDto);
    }

    @Override
    public int insertDocRcvUrlOtp(DocRcvUrlOtpDto docRcvUrlOtpDto) {
        return sqlSessionTemplate.insert("DocumentReceiveMapper.insertDocRcvUrlOtp", docRcvUrlOtpDto);
    }

    @Override
    public List<DocRcvItemDto> getDocRcvItemList(String docRcvId) {
        return sqlSessionTemplate.selectList("DocumentReceiveMapper.getDocRcvItemList", docRcvId);
    }

    @Override
    public DocRcvUrlOtpDto getLastDocRcvUrlOtp(String docRcvId) {
        return sqlSessionTemplate.selectOne("DocumentReceiveMapper.getLastDocRcvUrlOtp", docRcvId);
    }

    @Override
    public int insertLogRequest(Map<String, Object> paramMap) {
        return sqlSessionTemplate.insert("DocumentReceiveMapper.insertLogRequest", paramMap);
    }
}
