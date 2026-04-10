package com.ktis.msp.org.mnfctmgmt;



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

import com.ktis.msp.org.mnfctmgmt.service.MnfctMgmtService;
import com.ktis.msp.org.mnfctmgmt.vo.MnfctMgmtVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:egovframework/spring/context-common.xml",
		"classpath:egovframework/spring/context-test-datasource.xml",
		"classpath:egovframework/spring/context-test-transaction.xml",
		"classpath:egovframework/spring/context-mapper.xml",
		"classpath:egovframework/spring/context-idgen.xml",
		"classpath:egovframework/spring/context-aspect.xml"
		})
public class MnfctMgmtTest extends TestCase {

	Logger logger = LogManager.getLogger(MnfctMgmtTest.class);


	/** 제조사/공급사 서비스 */
	@Autowired
	private MnfctMgmtService mnfctMgmtService;
	
	
    @SuppressWarnings("unchecked")
	@Test
    public void testMnfctMgmtList() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※제조사 테스트 시작※※※※※※※※※※※※※※※※※");

			MnfctMgmtVo mnfctMgmtVo = new MnfctMgmtVo();
			
			List<?> listMnfctMgmts = mnfctMgmtService.selectMngctServiceList(mnfctMgmtVo);
			
			logger.debug("※※※※※※※※※※※※※※※※※ COUNT = " + listMnfctMgmts.size());
			
			for (int i = 0; i < listMnfctMgmts.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) listMnfctMgmts.get(i);
				logger.debug("※※※※※※※※※※※※※※※※※" + map.toString());
			}
			
			logger.debug("※※※※※※※※※※※※※※※※※제조사 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }

//    @Test
//    public void testOrgMgmtSelectCnt() throws Exception
//    {
//		try
//		{
//
//			logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");
//
//			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
//			
//			int resultCnt = orgMgmtService.listCntOrgMgmt(orgMgmtVO);
//			
//			logger.debug("※※※※※※※※※※※※※※※※※resultCnt 건수 : " + resultCnt);
//			
//			logger.debug("※※※※※※※※※※※※※※※※※조직 테스 종료※※※※※※※※※※※※※※※※※");
//		}
//		catch(Exception e)
//		{
//			logger.debug(e);
//		}
//    }
//
//
    @Test
    public void testOrgMgmtInsert() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※제조사/공급사 테스트 시작※※※※※※※※※※※※※※※※※");

			MnfctMgmtVo mnfctMgmtVo = new MnfctMgmtVo();
			
			mnfctMgmtVo.setMnfctNm("맥슨통신");
			
			int resultCnt = mnfctMgmtService.insertMnfctMgmt(mnfctMgmtVo);
			
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
