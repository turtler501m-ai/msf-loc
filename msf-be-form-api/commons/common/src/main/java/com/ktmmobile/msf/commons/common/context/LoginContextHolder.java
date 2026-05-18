package com.ktmmobile.msf.commons.common.context;

import java.util.Optional;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginContextHolder {

    private static final ThreadLocal<LoginContext> CONTEXT = new ThreadLocal<>();

    public static Optional<String> getUserId() {
        return Optional.ofNullable(CONTEXT.get())
            .map(LoginContext::userId)
            .filter(StringUtils::hasText);
    }

    public static void setUserId(String userId) {
        if (!StringUtils.hasText(userId)) {
            clear();
            return;
        }
        CONTEXT.set(new LoginContext(userId));
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static void runWithUserId(String userId, Runnable runnable) {
        withUserId(userId, () -> {
            runnable.run();
            return null;
        });
    }

    public static <T> T withUserId(String userId, Supplier<T> supplier) {
        LoginContext previous = CONTEXT.get();
        setUserId(userId);
        try {
            return supplier.get();
        } finally {
            restore(previous);
        }
    }

    public static <T> T callWithUserId(String userId, LoginContextCallable<T> callable) throws Throwable {
        LoginContext previous = CONTEXT.get();
        setUserId(userId);
        try {
            return callable.call();
        } finally {
            restore(previous);
        }
    }

    private static void restore(LoginContext previous) {
        if (previous == null) {
            clear();
            return;
        }
        CONTEXT.set(previous);
    }

    @FunctionalInterface
    public interface LoginContextCallable<T> {

        T call() throws Throwable;
    }

    private record LoginContext(String userId) {
    }
}
