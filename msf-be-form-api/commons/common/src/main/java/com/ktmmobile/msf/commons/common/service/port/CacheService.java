package com.ktmmobile.msf.commons.common.service.port;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Value/Hash 형태의 캐시 저장소 접근 포트
 *
 * @param <T> 캐시 값 타입
 */
public interface CacheService<T> {

    /** Value 캐시에 값을 저장 */
    void setValue(String key, T value);

    /** Value 캐시에 만료 시간과 함께 값을 저장 */
    void setValue(String key, T value, Duration timeout);

    /** Value 캐시에 만료 일자와 함께 값을 저장 */
    void setValue(String key, T value, LocalDate expireDate);

    /** Key가 없을 때만 Value 캐시에 값을 저장 */
    boolean setValueIfAbsent(String key, T value);

    /** Key가 없을 때만 Value 캐시에 만료 시간과 함께 값을 저장 */
    boolean setValueIfAbsent(String key, T value, Duration timeout);

    /** Hash 캐시에 전체 값을 저장 */
    void setValues(String key, Map<String, T> values);

    /** Hash 캐시에 전체 값을 만료 시간과 함께 저장 */
    void setValues(String key, Map<String, T> values, Duration timeout);

    /** Hash 캐시에 단일 값을 저장 */
    void setValue(String key, String hashKey, T value);

    /** Hash 캐시에 단일 값을 만료 시간과 함께 저장 */
    void setValue(String key, String hashKey, T value, Duration timeout);

    /** Hash 캐시의 전체 값을 교체 */
    void replaceValues(String key, Map<String, T> values);

    /** Hash 캐시의 전체 값을 만료 시간과 함께 교체 */
    void replaceValues(String key, Map<String, T> values, Duration timeout);

    /** Value 캐시 값을 조회 */
    T getValue(String key);

    /** Hash 캐시의 단일 값을 조회 */
    T getValue(String key, String hashKey);

    /** Hash 캐시에서 요청한 Hash Key 목록에 해당하는 값을 조회 */
    Map<String, T> getHashValues(String key, Collection<String> hashKeys);

    /** Hash 캐시의 전체 엔트리를 조회 */
    Map<String, T> getEntries(String key);

    /** 패턴과 일치하는 Value 캐시 값을 조회 */
    List<T> getValues(String pattern);

    /** 패턴과 일치하는 Value 캐시 값을 지정한 개수만큼 조회 */
    List<T> getValues(String pattern, int limit);

    /** Value 또는 Hash 캐시 Key 존재 여부를 조회 */
    boolean hasKey(String key);

    /** Hash 캐시의 Hash Key 존재 여부를 조회 */
    boolean hasKey(String key, String hashKey);

    /** Value 또는 Hash 캐시를 삭제 */
    void delete(String key);

    /** Value 캐시 값이 일치할 때만 삭제 */
    boolean deleteIfValueEquals(String key, T value);

    /** Hash 캐시의 단일 값을 삭제 */
    void delete(String key, String hashKey);

    /** 여러 캐시 Key를 삭제 */
    void deleteAll(List<String> keys);

    /** Value 캐시 숫자 값을 1 증가 */
    long increment(String key);

    /** Value 캐시 숫자 값을 1 증가하고 만료 시간을 설정 */
    long increment(String key, Duration timeout);

    /** Value 캐시 숫자 값을 지정한 값만큼 증가 */
    long increment(String key, long delta);

    /** Value 캐시 숫자 값을 지정한 값만큼 증가하고 만료 시간을 설정 */
    long increment(String key, long delta, Duration timeout);

    /** Value 캐시 숫자 값을 1 증가하고 만료 일자를 설정 */
    long increment(String key, LocalDate expireDate);

    /** Value 캐시 숫자 값을 지정한 값만큼 증가하고 만료 일자를 설정 */
    long increment(String key, long delta, LocalDate expireDate);

    /** Value 캐시 숫자 값을 1 감소 */
    long decrement(String key);

    /** Value 캐시 숫자 값을 지정한 값만큼 감소 */
    long decrement(String key, long delta);
}
