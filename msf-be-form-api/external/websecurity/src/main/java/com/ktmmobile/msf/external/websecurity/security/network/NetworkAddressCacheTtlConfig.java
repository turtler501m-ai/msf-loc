package com.ktmmobile.msf.external.websecurity.security.network;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NetworkAddressCacheTtlConfig implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        java.security.Security.setProperty("networkaddress.cache.ttl", "1");
        java.security.Security.setProperty("networkaddress.cache.negative.ttl", "3");

        log.info("networkaddress.cache.ttl:{}, networkaddress.cache.negative.ttl:{}",
            java.security.Security.getProperty("networkaddress.cache.ttl"),
            java.security.Security.getProperty("networkaddress.cache.negative.ttl"));
    }
}
