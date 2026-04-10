package com.ktmmobile.msf.commons.common.datasource.common.property;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.util.Assert;

import com.ktmmobile.msf.commons.common.datasource.common.data.DataSourceGroupType;
import com.ktmmobile.msf.commons.common.datasource.common.data.DataSourceItemType;

public record DataSourceGroupProperties(
    DataSourceGroupType type,
    DataSourceItemProperties writer,
    DataSourceItemProperties reader,
    DataSourceItemProperties standalone,
    HikariConfig hikari
) {

    public DataSourceItemProperties ofItemType(DataSourceItemType itemType) {
        Assert.notNull(itemType, "itemType is required");
        return switch (itemType) {
            case WRITER -> writer;
            case READER -> reader;
            case STANDALONE -> standalone;
        };
    }
}
