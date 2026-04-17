package com.ktmmobile.msf.appboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktmmobile.msf.commons.common.data.constant.CommonBaseConst;

@SpringBootApplication(scanBasePackages = CommonBaseConst.BASE_PACKAGE)
@ConfigurationPropertiesScan(basePackages = CommonBaseConst.BASE_PACKAGE)
public class FormApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(FormApiApplication.class);

    @Autowired
    private Environment env;

    static void main(String[] args) {
        SpringApplication.run(FormApiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String port    = env.getProperty("server.port", "8080");
        String profile = String.join(", ", env.getActiveProfiles());
        String appName = env.getProperty("spring.application.name", "form-api");

        logger.info("================================================");
        logger.info("  {} 서버 기동 완료", appName);
        logger.info("  Port    : {}", port);
        logger.info("  Profile : {}", profile.isEmpty() ? "default" : profile);
        logger.info("  URL     : http://localhost:{}", port);
        logger.info("================================================");
    }

}
