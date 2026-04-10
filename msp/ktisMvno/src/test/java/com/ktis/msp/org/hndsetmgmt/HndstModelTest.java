package com.ktis.msp.org.hndsetmgmt;



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

import com.ktis.msp.org.hndsetmgmt.service.HndstModelService;
import com.ktis.msp.org.hndsetmgmt.vo.HndstModelVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:egovframework/spring/context-common.xml",
		"classpath:egovframework/spring/context-properties.xml",
		"classpath:egovframework/spring/context-test-datasource.xml",
		"classpath:egovframework/spring/context-test-transaction.xml",
		"classpath:egovframework/spring/context-mapper.xml",
		"classpath:egovframework/spring/context-idgen.xml",
		"classpath:egovframework/spring/context-aspect.xml"
		})
public class HndstModelTest extends TestCase {

	Logger logger = LogManager.getLogger(HndstModelTest.class);


	@Autowired
	private HndstModelService hndstModelService;
	
	
    @SuppressWarnings("unchecked")
	@Test
    public void testHndstModelList() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※제조사 테스트 시작※※※※※※※※※※※※※※※※※");

			HndstModelVo hndstModelVo = new HndstModelVo();
			
			List<?> listMnfctMgmts = hndstModelService.selectHndstModelList(hndstModelVo);
			
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
    public void testHndstModelInsert() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※제조사/공급사 테스트 시작※※※※※※※※※※※※※※※※※");

			HndstModelVo hndstModelVo = new HndstModelVo();
			
			hndstModelVo.setPrdtId("iPhone5");
			hndstModelVo.setPrdtNm("아이폰5");
			hndstModelVo.setOldYn("Y");
			hndstModelVo.setMnfctId("7");
			
			int resultCnt = hndstModelService.insertHndstModel(hndstModelVo);
			
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
