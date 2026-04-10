package com.ktmmobile.msf.commons.common.utils.log;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import com.ktmmobile.msf.commons.common.exception.CommonException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogUtils {

    public static void infoSimpleException(Logger log, String message, Throwable e) {
        if (log.isInfoEnabled()) {
            String formattedMessage = getFormattedMessage(message, e);
            log.info(formattedMessage);
        }
    }

    public static void warnSimpleException(Logger log, String message, Throwable e) {
        if (log.isWarnEnabled()) {
            String formattedMessage = getFormattedMessage(message, e);
            log.warn(formattedMessage);
        }
    }

    public static void errorSimpleException(Logger log, String message, Throwable e) {
        if (log.isErrorEnabled()) {
            String formattedMessage = getFormattedMessage(message, e);
            log.error(formattedMessage);
        }
    }

    private static String getFormattedMessage(String message, Throwable e) {
        return String.format("%s%n%n%s: %s%n", message, e.getClass().getName(), e.getMessage());
    }

    public static void logException(Logger log, CommonException e, String message) {
        switch (e.getLogLevel()) {
            case WARNING -> log.warn(message, e);
            case INFO -> log.info(message, e);
            case DEBUG -> log.debug(message, e);
            case TRACE -> log.trace(message, e);
            default -> log.error(message, e);
        }
    }
}
