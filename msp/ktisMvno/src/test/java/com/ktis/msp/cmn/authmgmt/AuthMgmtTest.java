package com.ktis.msp.cmn.authmgmt;

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

import com.ktis.msp.cmn.authmgmt.service.AuthMgmtService;
import com.ktis.msp.org.orgmgmt.OrgMgmtTest;

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
public class AuthMgmtTest extends TestCase {

    Logger logger = LogManager.getLogger(OrgMgmtTest.class);


    /** 메뉴관리 서비스 */
    @Autowired
    private AuthMgmtService authMgmtService;


    @SuppressWarnings("unchecked")
    @Test
    public void testAuthMgmtList() throws Exception
    {
        try
        {

            logger.debug("※※※※※※※※※※※※※※※※※조직 테스트 시작※※※※※※※※※※※※※※※※※");

            List<?> resultList = authMgmtService.treeMenuAllList();
            
            Map<String, Object> resultMap = new HashMap<String, Object>();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            Map<String, Object> returnMap = new HashMap<String, Object>();
            
            resultMap.put("code", "OK");
            resultMap.put("msg", "");
            
            dataMap.put("rows", resultList);
            
            resultMap.put("data", dataMap);
            returnMap.put("result",resultMap);            
            logger.debug("※※※※※※※※※※※※※※※※※" + returnMap.toString());
            
            logger.debug("※※※※※※※※※※※※※※※※※메뉴 전체 트리 테스트 종료※※※※※※※※※※※※※※※※※");
        }
        catch(Exception e)
        {
            logger.debug(e);
        }
    }

}
