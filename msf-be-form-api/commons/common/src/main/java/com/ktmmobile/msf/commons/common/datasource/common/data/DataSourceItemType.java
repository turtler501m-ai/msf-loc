package com.ktmmobile.msf.commons.common.datasource.common.data;

import lombok.Getter;

@Getter
public enum DataSourceItemType {
    WRITER("Writer"),
    READER("Reader"),
    STANDALONE("Standalone");

    private final String roleName;
    private final String itemName;

    DataSourceItemType(String roleName) {
        this.roleName = roleName;
        this.itemName = roleName.toLowerCase();
    }
}
