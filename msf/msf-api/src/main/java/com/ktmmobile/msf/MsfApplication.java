package com.ktmmobile.msf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 서식지App Back (스마트서식지) 메인.
 * 패키지 규칙: com.ktmmobile.msf
 */
@SpringBootApplication
@EnableScheduling
@MapperScan({"com.ktmmobile.msf.formComm.mapper", "com.ktmmobile.msf.formSvcChg.mapper", "com.ktmmobile.msf.formOwnChg.mapper", "com.ktmmobile.msf.formSvcCncl.mapper"})
public class MsfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfApplication.class, args);
    }
}
