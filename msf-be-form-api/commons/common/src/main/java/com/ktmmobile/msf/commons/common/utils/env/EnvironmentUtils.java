package com.ktmmobile.msf.commons.common.utils.env;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.env.Environment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnvironmentUtils {

    @Getter
    private static Environment environment;

    private static List<EnvironmentProfile> profiles;


    public static void initialize(Environment environment) {
        EnvironmentUtils.environment = environment;
        setProfiles(environment.getActiveProfiles());
    }

    private static void setProfiles(String[] activeProfiles) {
        profiles = new ArrayList<>();
        for (String activeProfile: activeProfiles) {
            EnvironmentProfile profile = EnvironmentProfile.valueOfCode(activeProfile);
            if (profile != null) {
                profiles.add(profile);
            }
        }
        validateIfProfileExists();
    }

    private static void validateIfProfileExists() {
        if (profiles.isEmpty()) {
            throw new IllegalStateException("No active profiles");
        }
    }

    public static EnvironmentProfile getActiveProfile() {
        return profiles.getFirst();
    }

    public static EnvironmentProfile getLocalProfile() {
        return EnvironmentProfile.getLocal();
    }

    public static String getLocalProfileCode() {
        return getLocalProfile().getCode();
    }

    public static boolean isLocal() {
        return getActiveProfile().isLocal();
    }

    public static boolean isDevelopment() {
        return getActiveProfile().isDevelopment();
    }

    public static boolean isStaging() {
        return getActiveProfile().isStaging();
    }

    public static boolean isProduction() {
        return getActiveProfile().isProduction();
    }

    public static String getProperty(String variableName) {
        return environment.getProperty(variableName);
    }
}
