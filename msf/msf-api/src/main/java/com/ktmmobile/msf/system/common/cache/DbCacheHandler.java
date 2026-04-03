package com.ktmmobile.msf.system.common.cache;

import javax.annotation.PostConstruct;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ktmmobile.msf.system.common.exception.McpCommonException;

public class DbCacheHandler {
    @Deprecated
    private static Logger logger = LoggerFactory.getLogger(DbCacheHandler.class);

    private CacheManager cacheManager;

    private Ehcache cache;

    private String cacheName;

    public boolean put(String key, Object value) {
        Element element = new Element(key, value);
        try {
            cache.put(element);
        } catch(Exception ex) {
            throw new McpCommonException(ex);
        }
        return true;
    }

    public Element getElement(String key) {
        return cache.get(key);
    }

    public Object getValue(String key) {
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return element.getObjectValue();
    }

    public boolean replace(String key, Object value) {
        Element element = new Element(key, value);
        cache.replace(element);
        return true;
    }

    public Ehcache getCache() {
        return cache;
    }
    /**
     * 의존성 주입 완료 후 cacheName으로 cache 객체 조회해서 참조
     * jUnit 사용시 .... PostConstruct 주석 처리
     */
   @PostConstruct
    public void setCache() {
        try {
            cache = cacheManager.getEhcache(cacheName);
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
