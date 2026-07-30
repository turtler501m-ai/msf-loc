package com.ktmmobile.msf.batchcore.support.properties;

import org.springframework.batch.core.repository.dao.AbstractJdbcBatchMetadataDao;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "batch-core")
public record BatchCoreProperties(
    Jdbc jdbc
) {

    public Jdbc jdbc() {
        return jdbc == null ? Jdbc.defaults() : jdbc;
    }

    public record Jdbc(
        String tablePrefix
    ) {

        static Jdbc defaults() {
            return new Jdbc(AbstractJdbcBatchMetadataDao.DEFAULT_TABLE_PREFIX);
        }

        public String tablePrefix() {
            return tablePrefix == null || tablePrefix.isBlank()
                ? AbstractJdbcBatchMetadataDao.DEFAULT_TABLE_PREFIX
                : tablePrefix;
        }
    }
}
