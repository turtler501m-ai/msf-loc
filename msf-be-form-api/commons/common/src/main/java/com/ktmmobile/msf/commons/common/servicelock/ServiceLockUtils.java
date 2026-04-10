//package com.ktmmobile.msf.common.common.servicelock;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//import com.ktmmobile.msf.common.common.service.RedisService;
//import com.ktmmobile.msf.common.common.servicelock.data.ServiceLock;
//import com.ktmmobile.msf.common.common.servicelock.data.ServiceLockType;
//
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//public class ServiceLockUtils {
//
//    public static final Duration LOCK_TIMEOUT = Duration.ofSeconds(10);
//    private static final String DELIMITER = ":";
//
//    private static RedisService<ServiceLock> redisService;
//
//    static void initialize(RedisService<ServiceLock> redisService) {
//        ServiceLockUtils.redisService = redisService;
//    }
//
//    public static ServiceLock createLockKey(ServiceLockType lockType, Object... lockKeys) {
//        return ServiceLock.of(lockType.getCode(), concat(lockKeys));
//    }
//
//    private static String concat(Object[] lockKeys) {
//        return Arrays.stream(lockKeys)
//            .map(lockKey -> {
//                if (Number.class.isAssignableFrom(lockKey.getClass())) {
//                    return String.valueOf(((Number) lockKey).longValue());
//                }
//                return lockKey.toString();
//            }).collect(Collectors.joining(DELIMITER));
//    }
//
//    public static boolean tryLock(ServiceLock lock) {
//        String lockKey = lock.getLockTypeKey();
//        if (redisService.hasKey(lockKey)) {
//            return false;
//        }
//        return redisService.setValueIfAbsent(lockKey, lock, LOCK_TIMEOUT);
//    }
//
//    public static void unLock(ServiceLock lock) {
//        redisService.delete(lock.getLockTypeKey());
//    }
//
//    public static boolean tryLock(ServiceLockType lockType, Object... lockKeys) {
//        return tryLock(lockType, concat(lockKeys));
//    }
//
//    public static boolean tryLock(ServiceLockType lockType, String lockKey) {
//        ServiceLock lock = ServiceLock.of(lockType.getCode(), lockKey);
//        String lockTypeKey = lock.getLockTypeKey();
//        if (redisService.hasKey(lockTypeKey)) {
//            return false;
//        }
//        return redisService.setValueIfAbsent(lockTypeKey, lock, LOCK_TIMEOUT);
//    }
//
//    public static void unLock(ServiceLockType lockType, Object... lockKeys) {
//        unLock(lockType, concat(lockKeys));
//    }
//
//    public static void unLock(ServiceLockType lockType, String lockKey) {
//        ServiceLock lock = ServiceLock.of(lockType.getCode(), lockKey);
//        String lockTypeKey = lock.getLockTypeKey();
//        redisService.delete(lockTypeKey);
//    }
//}
