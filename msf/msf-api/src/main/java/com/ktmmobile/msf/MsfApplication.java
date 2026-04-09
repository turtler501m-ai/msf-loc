package com.ktmmobile.msf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({
    "com.ktmmobile.msf.system.common.mapper",
    "com.ktmmobile.msf.common.mapper",
    "com.ktmmobile.msf.form.newchange.mapper",
    "com.ktmmobile.msf.form.servicechange.mapper",
    "com.ktmmobile.msf.form.ownerchange.mapper",
    "com.ktmmobile.msf.form.termination.mapper"
})
public class MsfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfApplication.class, args);
    }
}
