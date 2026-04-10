package com.ktis.msp.org.powertmntmgmt;



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

import com.ktis.msp.org.powertmntmgmt.service.PowerTmntMgmtService;
import com.ktis.msp.org.powertmntmgmt.vo.PowerTmntMgmtVo;

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
public class PowerTmntMgmtTest extends TestCase {

	Logger logger = LogManager.getLogger(PowerTmntMgmtTest.class);


	@Autowired
	private PowerTmntMgmtService powerTmntMgmtService;
	
	
    @SuppressWarnings("unchecked")
	@Test
    public void testPowerTmntMgmtList() throws Exception
    {
		try
		{

			logger.debug("※※※※※※※※※※※※※※※※※직권해지 테스트 시작※※※※※※※※※※※※※※※※※");

			PowerTmntMgmtVo powerTmntMgmtVo = new PowerTmntMgmtVo();
			
			List<?> listPowerTmntMgmts = powerTmntMgmtService.selectPowerTmntMgmtList(powerTmntMgmtVo);
			
			logger.debug("※※※※※※※※※※※※※※※※※ COUNT = " + listPowerTmntMgmts.size());
			
			for (int i = 0; i < listPowerTmntMgmts.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) listPowerTmntMgmts.get(i);
				logger.debug("※※※※※※※※※※※※※※※※※" + map.toString());
			}
			
			logger.debug("※※※※※※※※※※※※※※※※※직권해지 테스 종료※※※※※※※※※※※※※※※※※");
		}
		catch(Exception e)
		{
			logger.debug(e);
		}
    }
}
