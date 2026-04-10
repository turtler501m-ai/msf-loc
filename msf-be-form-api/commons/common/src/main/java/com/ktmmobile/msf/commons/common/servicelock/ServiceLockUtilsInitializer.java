//package com.ktmmobile.msf.common.common.servicelock;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Component;
//import lombok.RequiredArgsConstructor;
//
//import com.ktmmobile.msf.common.common.service.RedisService;
//import com.ktmmobile.msf.common.common.servicelock.data.ServiceLock;
//
//@RequiredArgsConstructor
//@Component
//public class ServiceLockUtilsInitializer implements InitializingBean {
//
//    private final RedisService<ServiceLock> redisService;
//
//    @Override
//    public void afterPropertiesSet() {
//        ServiceLockUtils.initialize(redisService);
//    }
//}
