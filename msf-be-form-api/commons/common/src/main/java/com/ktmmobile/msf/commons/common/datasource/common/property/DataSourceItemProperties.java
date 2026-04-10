package com.ktmmobile.msf.commons.common.datasource.common.property;

import com.zaxxer.hikari.HikariConfig;

public record DataSourceItemProperties(
    String url,
    String username,
    String password,
    boolean readOnly,
    HikariConfig hikari
) {

}
