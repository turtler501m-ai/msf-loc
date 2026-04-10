package com.ktmmobile.msf.domains.form.common.cache;

import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class DbCacheHandler {
    @Deprecated
    private static Logger logger = LoggerFactory.getLogger(DbCacheHandler.class);

    @Autowired
    private CacheManager cacheManager;

    private Cache cache;

    private String cacheName = "dbCache";

    public boolean put(String key, Object value) {
        try {
            cache.put(key, value);
        } catch(Exception ex) {
            throw new McpCommonException(ex);
        }
        return true;
    }

    // [ASIS] EhCache Element 반환 — Spring Cache ValueWrapper로 대체
    public Cache.ValueWrapper getElement(String key) {
        return cache.get(key);
    }

    public Object getValue(String key) {
        Cache.ValueWrapper wrapper = cache.get(key);
        if (wrapper == null) {
            return null;
        }
        return wrapper.get();
    }

    public boolean replace(String key, Object value) {
        cache.put(key, value);
        return true;
    }

    public Cache getCache() {
        return cache;
    }

    /**
     * 의존성 주입 완료 후 cacheName으로 cache 객체 조회해서 참조
     * jUnit 사용시 .... PostConstruct 주석 처리
     */
    @PostConstruct
    public void setCache() {
        try {
            cache = cacheManager.getCache(cacheName);
        } catch (Exception ex) {
            throw new McpCommonException(ex);
        }
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
