package com.ktmmobile.msf.appboot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.support.property.CommonPublicUrlProperties;
import com.ktmmobile.msf.commons.common.utils.env.EnvironmentUtils;
import com.ktmmobile.msf.commons.common.utils.env.SpringCustomProperties;

@Slf4j
@Profile({"local", "dev"})
@RequiredArgsConstructor
@Component
public class ApplicationInformationLogger {

    private final SpringCustomProperties springCustomProperties;
    private final CommonPublicUrlProperties commonPublicUrlProperties;

    @EventListener(ApplicationReadyEvent.class)
    public void logApplicationInformation() {
        String appName = springCustomProperties.applicationName();
        String appNameAbbr = springCustomProperties.applicationNameAbbreviated();
        String publicUrl = commonPublicUrlProperties.formService();

        log.info("================================================");
        log.info("  App Name  : {}({})", appName, appNameAbbr);
        log.info("  Public URL: {}", publicUrl);
        log.info("  Profile   : {}", EnvironmentUtils.getActiveProfile().getCode());
        log.info("================================================");
    }
}
