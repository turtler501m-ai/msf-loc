package com.ktmmobile.mcp.common.startup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ktmmobile.mcp.common.service.FCommonSvc;

@Configuration
public class StartUpApplication {
    private static final Logger logger = LoggerFactory.getLogger(StartUpApplication.class);

    @Autowired
    private FCommonSvc fCommonSvc;

    @PostConstruct
    public void contextInitialized() {

        logger.debug("");
        logger.debug("******************************************************************");
        logger.debug("* context initialized");
        logger.debug("* 1. bean inject to static class field");
        logger.debug("* 2. db data load to cache");
        logger.debug("******************************************************************");
        logger.debug("");


        /** jUnit 사용시 ....  주석 처리 */
        /**************************************************************/
        fCommonSvc.prdMnpCmpnList(); //번호이동 사업자 조회 후 공통코드 업데이트 처리
        fCommonSvc.getCodeCahe();	//공통코드
        fCommonSvc.getMenuCahe();	//메뉴
        fCommonSvc.getMenuAuthCahe();	//메뉴권한
        fCommonSvc.getBannerCahe();	//배너
        fCommonSvc.getBannerApdCahe();	//배너APD
        fCommonSvc.getPopupCahe();	//팝업
        fCommonSvc.getMenuUrlNotiCahe();	//작업공지
        fCommonSvc.getAcesAlwdCahe();	//작업공지우회
        fCommonSvc.getBannerTextCahe();	//배너APD
        fCommonSvc.getBannerFloatCahe(); //이벤트 플로팅 배너
        fCommonSvc.getRateAdsvcGdncVersionCache(); //요금제 안내 데이터 버전
        /**************************************************************/

    }

    @PreDestroy
    public void contextdestroyed() {
        logger.debug("Context Destroyed");
    }
}
