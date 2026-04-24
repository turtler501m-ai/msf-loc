package com.ktis.msp.batch.job.cmn.usrobjhst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.usrobjhst.mapper.UserObjHstMapper;

@Service
public class UserObjHstService extends BaseService  {
	
	@Autowired
	private UserObjHstMapper userObjHstMapper;

	@Autowired
	private JdbcService jdbcService;
	
//    @Transactional(rollbackFor=Exception.class)
	public void userObjHstInsert() throws MvnoServiceException {
    	LOGGER.info(generateLogMsg("================================================================="));
    	LOGGER.info(generateLogMsg("DB USER OBJECT 변경 이력 INSERT START"));
    	LOGGER.info(generateLogMsg("================================================================="));

		int ddlCnt = 0;
		String owner = "";
		try {

			ddlCnt = userObjHstMapper.selectUserObject();
			LOGGER.info("1. MSP_WAS 변경된 OBJECT : " + ddlCnt + "건");
			if(ddlCnt > 0){
				userObjHstMapper.insertUserObjectHst();				
			}

	    	LOGGER.info(generateLogMsg("MSP_WAS OBJECT 변경 이력 INSERT END"));
	    	
	    	
	    	owner = "MSP_MNG";
	    	ddlCnt = jdbcService.selectMspUserObject(owner);
			LOGGER.info("2. MSP_MNG 변경된 OBJECT : " + ddlCnt + "건");
			if(ddlCnt > 0){
				jdbcService.insertMspUserObjectHst("MSP_MNG");				
			}

	    	LOGGER.info(generateLogMsg("MSP_MNG OBJECT 변경 이력 INSERT END"));


	    	owner = "MCP_WAS";
	    	ddlCnt = jdbcService.selectMcpUserObject(owner);
			LOGGER.info("3. MCP_WAS 변경된 OBJECT : " + ddlCnt + "건");
			if(ddlCnt > 0){
				jdbcService.insertMcpUserObjectHst("MCP_WAS");				
			}

	    	LOGGER.info(generateLogMsg("MCP_WAS OBJECT 변경 이력 INSERT END"));
	    	

	    	owner = "MCP_MNG";
	    	ddlCnt = jdbcService.selectMcpUserObject(owner);
			LOGGER.info("4. MCP_MNG 변경된 OBJECT : " + ddlCnt + "건");
			if(ddlCnt > 0){
				jdbcService.insertMcpUserObjectHst("MCP_MNG");				
			}

	    	LOGGER.info(generateLogMsg("MCP_MNG OBJECT 변경 이력 INSERT END"));
	    	
		} catch (Exception e) {
			throw new MvnoServiceException("USER OBJECT 변경 이력 에러", e);
		}
	}
}
