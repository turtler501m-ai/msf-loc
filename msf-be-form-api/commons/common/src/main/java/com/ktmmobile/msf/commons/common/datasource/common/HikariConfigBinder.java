package com.ktmmobile.msf.commons.common.datasource.common;

import com.zaxxer.hikari.HikariConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HikariConfigBinder {

    public static HikariConfig bind(Binder binder, String... hikariPrefixes) {
        Assert.notNull(binder, "binder is required");

        HikariConfig mergedConfig = new HikariConfig();
        if (hikariPrefixes == null) {
            return mergedConfig;
        }
        for (String prefix: hikariPrefixes) {
            if (!StringUtils.hasText(prefix)) {
                continue;
            }
            binder.bind(prefix, Bindable.ofInstance(mergedConfig));
        }
        return mergedConfig;
    }
}
