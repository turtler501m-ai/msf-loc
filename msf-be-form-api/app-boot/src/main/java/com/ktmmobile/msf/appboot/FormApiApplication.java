package com.ktmmobile.msf.appboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import com.ktmmobile.msf.commons.common.data.constant.CommonBaseConst;

@SpringBootApplication(scanBasePackages = CommonBaseConst.BASE_PACKAGE)
@ConfigurationPropertiesScan(basePackages = CommonBaseConst.BASE_PACKAGE)
public class FormApiApplication {

    static void main(String[] args) {
        SpringApplication.run(FormApiApplication.class, args);
    }

}
