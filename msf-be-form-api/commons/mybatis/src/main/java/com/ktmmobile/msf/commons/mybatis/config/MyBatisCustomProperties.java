package com.ktmmobile.msf.commons.mybatis.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
public class MyBatisCustomProperties extends MybatisProperties {

    private final List<String> typeHandlersPackages = new ArrayList<>();
    private final List<String> typeAliasesPackages = new ArrayList<>();
    private final QueryId queryId = new QueryId();
    private final Datasource datasource = new Datasource();

    @Override
    public String getTypeHandlersPackage() {
        return String.join(",", typeHandlersPackages);
    }

    @Override
    public String getTypeAliasesPackage() {
        return String.join(",", typeAliasesPackages);
    }

    public boolean queryIdEnabled() {
        return queryId.enabled;
    }

    public String queryIdPrefix() {
        return queryId.prefix;
    }

    public List<String> smartformMapperScanPackages() {
        return datasource.smartform.mapperScanPackages;
    }

    public List<String> smartformMapperLocations() {
        return datasource.smartform.mapperLocations;
    }

    public List<String> mspMapperScanPackages() {
        return datasource.msp.mapperScanPackages;
    }

    public List<String> mspMapperLocations() {
        return datasource.msp.mapperLocations;
    }

    @Getter
    @Setter
    public static class QueryId {

        private boolean enabled;
        private String prefix = "";
    }

    @Getter
    @Setter
    public static class Datasource {

        private MapperSettings smartform = new MapperSettings();
        private MapperSettings msp = new MapperSettings();
    }

    @Getter
    @Setter
    public static class MapperSettings {

        private List<String> mapperScanPackages = new ArrayList<>();
        private List<String> mapperLocations = new ArrayList<>();
    }
}
