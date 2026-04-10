package com.ktis.msp.org.userinfomgmt;



import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:egovframework/spring/context-aspect.xml",
        "classpath:egovframework/spring/context-common.xml",
        "classpath:egovframework/spring/context-config.xml",
        "classpath:egovframework/spring/context-crypto.xml",
        "classpath:egovframework/spring/context-datasource.xml",
        "classpath:egovframework/spring/context-idgen.xml",
        "classpath:egovframework/spring/context-mapper.xml",
        "classpath:egovframework/spring/context-properties.xml",
        "classpath:egovframework/spring/context-transaction.xml"
		})
public class UserInfoMgmtTest extends TestCase {

	Logger logger = LogManager.getLogger(UserInfoMgmtTest.class);


	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	
    @SuppressWarnings("unchecked")
	@Test
    public void testUserInfoMgmtList() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※사용자 테스트 시작※※※※※※※※※※※※※※※※※");

			UserInfoMgmtVo userInfoMgmtVo = new UserInfoMgmtVo();
			
			List<?> listMnfctMgmts = userInfoMgmtService.selectUserInfoMgmtList(userInfoMgmtVo);
			
			logger.debug("※※※※※※※※※※※※※※※※※ COUNT = " + listMnfctMgmts.size());
			
			for (int i = 0; i < listMnfctMgmts.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) listMnfctMgmts.get(i);
				logger.debug("※※※※※※※※※※※※※※※※※" + map.toString());
			}
			
			logger.debug("※※※※※※※※※※※※※※※※※사용자 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }

    @Test
    public void testUserEhr() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※EHR연동 테스트 시작※※※※※※※※※※※※※※※※※");

//			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
			String userId = "SYSTEM";
			
//			userInfoMgmtService.ehrProcess(userId);
			
			logger.debug("※※※※※※※※※※※※※※※※※EHR연동 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }


    @Test
    public void testUserInfoMgmtInsert() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※제조사/공급사 테스트 시작※※※※※※※※※※※※※※※※※");

			UserInfoMgmtVo userInfoMgmtVo = new UserInfoMgmtVo();
			
			userInfoMgmtVo.setUsrId("116901110");
			userInfoMgmtVo.setUsrNm("장익준");
			
			int resultCnt = userInfoMgmtService.insertUserInfoMgmt(userInfoMgmtVo);
			
			logger.debug("※※※※※※※※※※※※※※※※※resultCnt 건수 : " + resultCnt);
			logger.debug("※※※※※※※※※※※※※※※※※제조사/공급사 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }
//    
//    @Test
//    public void testOrgMgmtUpdate() throws Exception
//    {
//		try
//		{
//
//			logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");
//
//			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
//			
//			orgMgmtVO.setOrgnId("KT00000003");
//			orgMgmtVO.setOrgnNm("장익준조직");
//			
//			int resultCnt = orgMgmtService.updateOrgMgmt(orgMgmtVO);
//			
//			logger.debug("※※※※※※※※※※※※※※※※※resultCnt 건수 : " + resultCnt);
//			logger.debug("※※※※※※※※※※※※※※※※※조직 테스 종료※※※※※※※※※※※※※※※※※");
//		}
//		catch(Exception e)
//		{
//			logger.debug(e);
//		}
//    }
//    
//    @Test
//    public void testOrgMgmtDelete() throws Exception
//    {
//		try
//		{
//
//			logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");
//
//			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
//			
//			orgMgmtVO.setOrgnId("KT00000003");
//			
//			int resultCnt = orgMgmtService.deleteOrgMgmt(orgMgmtVO);
//			
//			logger.debug("※※※※※※※※※※※※※※※※※resultCnt 건수 : " + resultCnt);
//			logger.debug("※※※※※※※※※※※※※※※※※조직 테스 종료※※※※※※※※※※※※※※※※※");
//		}
//		catch(Exception e)
//		{
//			logger.debug(e);
//		}
//    }
}
