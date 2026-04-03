package com.ktmmobile.msf.form.newchange.dao;

import com.ktmmobile.msf.form.newchange.dto.AbuseImeiHistDto;
import com.ktmmobile.msf.form.newchange.dto.McpEsimOmdTraceDto;
import com.ktmmobile.msf.form.newchange.dto.McpUploadPhoneInfoDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EsimDaoImpl implements EsimDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;

	@Override
    public int insertMcpUploadPhoneInfoDto(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto) {
    	int uploadPhoneSrlNo = 0;
    	int result = 0;
    	result = sqlSessionTemplate.insert("EsimMapper.insertMcpUploadPhoneInfoDto", mcpUploadPhoneInfoDto);
    	if(result > 0) {
    		uploadPhoneSrlNo = mcpUploadPhoneInfoDto.getUploadPhoneSrlNo();
    	}
        return uploadPhoneSrlNo;
    }

	@Override
	public int updateMcpUploadPhoneInfoDto(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto) {
		return  sqlSessionTemplate.update("EsimMapper.updateMcpUploadPhoneInfoDto", mcpUploadPhoneInfoDto);
	}

	@Override
	public int insertMcpEsimOmdTrace(McpEsimOmdTraceDto mcpEsimOmdTraceDto) {
		return  sqlSessionTemplate.insert("EsimMapper.insertMcpEsimOmdTrace", mcpEsimOmdTraceDto);
	}

	@Override
	public int updateServiceRst(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto) {
		return  sqlSessionTemplate.update("EsimMapper.updateServiceRst", mcpUploadPhoneInfoDto);
	}

	@Override
    public McpUploadPhoneInfoDto doubleChk(int uploadPhoneSrlNo){
        return sqlSessionTemplate.selectOne("EsimMapper.doubleChk", uploadPhoneSrlNo);
    }

	@Override
	public int insertAbuseImeiHist(AbuseImeiHistDto abuseImeiHistDto) {
		return sqlSessionTemplate.insert("EsimMapper.insertAbuseImeiHist", abuseImeiHistDto);
	}

}
