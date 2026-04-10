package com.ktis.msp.org.orgmgmt;



import java.util.HashMap;
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

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.org.orgmgmt.service.OrgMgmtService;
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtVO;

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
public class OrgMgmtTest extends TestCase {

	Logger logger = LogManager.getLogger(OrgMgmtTest.class);


	/** 조직관리 서비스 */
	@Autowired
	private OrgMgmtService orgMgmtService;


    @SuppressWarnings("unchecked")
	@Test
    public void testOrgMgmtList() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");

			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
			orgMgmtVO.setOrgnId("1");
			
			List<?> listOrgMgmts = orgMgmtService.listOrgMgmt(orgMgmtVO);
			
			Map<String, Object> map = (Map<String, Object>) listOrgMgmts.get(0);
			
			logger.debug("※※※※※※※※※※※※※※※※※" + map.toString());
			
			logger.debug("※※※※※※※※※※※※※※※※※조직 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }

    
    @Test
    public void testOrgMgmtList2() throws Exception
    {
        try
        {

            logger.debug("※※※※※※※※※※※※※※※※※조직 트리 테스트 시작※※※※※※※※※※※※※※※※※");

            OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
//            orgMgmtVO.setId("1");
//            orgMgmtVO.setOrgnNm("유통");
            
            List<?> resultList = orgMgmtService.treeListOrgMgmt(orgMgmtVO);
            
            Map<String, Object> resultMap = new HashMap<String, Object>();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            Map<String, Object> returnMap = new HashMap<String, Object>();
            
            resultMap.put("code", "OK");
            resultMap.put("msg", "");
            
            dataMap.put("rows", resultList);
            
            if(KtisUtil.isEmpty(orgMgmtVO.getId())){
                dataMap.put("parent","0");    
            } else {
                dataMap.put("parent",orgMgmtVO.getId());
            }
            
            resultMap.put("data", dataMap);
            returnMap.put("result",resultMap);
            
            logger.debug("※※※※※※※※※※※※※※※※※" + returnMap.toString());
            
            logger.debug("※※※※※※※※※※※※※※※※※조직 트리 테스트 종료※※※※※※※※※※※※※※※※※");
        }
        catch(Exception e)
        {
            logger.debug(e);
        }
    }
    
    
    @Test
    public void testOrgMgmtSelectCnt() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");

			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
			
			int resultCnt = orgMgmtService.listCntOrgMgmt(orgMgmtVO);
			
			logger.debug("※※※※※※※※※※※※※※※※※resultCnt 건수 : " + resultCnt);
			
			logger.debug("※※※※※※※※※※※※※※※※※조직 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }


    @Test
    public void testOrgMgmtInsert() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");

			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
			
			orgMgmtVO.setOrgnId("KT00000003");
			orgMgmtVO.setOrgnNm("JUNIT 조직");
			
			int resultCnt = orgMgmtService.insertOrgMgmt(orgMgmtVO);
			
			logger.debug("※※※※※※※※※※※※※※※※※resultCnt 건수 : " + resultCnt);
			logger.debug("※※※※※※※※※※※※※※※※※조직 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }
    
    @Test
    public void testOrgMgmtUpdate() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");

			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
			
			orgMgmtVO.setOrgnId("KT00000001");
			orgMgmtVO.setOrgnNm("장익준조직");
			
			int resultCnt = orgMgmtService.updateOrgMgmt(orgMgmtVO);
			
			logger.debug("※※※※※※※※※※※※※※※※※resultCnt 건수 : " + resultCnt);
			logger.debug("※※※※※※※※※※※※※※※※※조직 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }
    
    @Test
    public void testOrgMgmtDelete() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");

			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
			
			orgMgmtVO.setOrgnId("KT00000003");
			
			int resultCnt = orgMgmtService.deleteOrgMgmt(orgMgmtVO);
			
			logger.debug("※※※※※※※※※※※※※※※※※resultCnt 건수 : " + resultCnt);
			logger.debug("※※※※※※※※※※※※※※※※※조직 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }
    
    //조직 이력 건수 조회
    @Test
    public void testOrgMgmtSelectCntHst() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");

			OrgMgmtVO orgMgmtVO = new OrgMgmtVO();
			
			orgMgmtVO.setOrgnId("1");
			int resultCnt = orgMgmtService.listCntOrgMgmtHst(orgMgmtVO);
			
			logger.debug("※※※※※※※※※※※※※※※※※resultCnt 건수 : " + resultCnt);
			
			logger.debug("※※※※※※※※※※※※※※※※※조직 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }    
}
