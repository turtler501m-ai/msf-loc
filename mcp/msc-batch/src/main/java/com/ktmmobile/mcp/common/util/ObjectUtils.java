/*=================================================
 * KT Copyright
 =================================================*/
package com.ktmmobile.mcp.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * @파일명	: ObjectUtils.java
 * @날짜		: 2015. 6. 19.
 * @작성자	: kmh
 * @설명		: 객체 컨트롤 관련
 */
public class ObjectUtils {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    /**
     * Object를 문자열로 변환
     * @param object
     * @return
     */
    public static String convertObjectToString(Object object) {
        StopWatch timer = new StopWatch();
        timer.start();

        String resultString = convertObjectToString(object, true);

        timer.stop();

        return resultString;
    }

    /**
     * Object를 문자열로 반환 - 출력 포맷 지정
     * @param object
     * @param stringStyle
     * @return
     */
    public static String convertObjectToString(Object object, boolean stringStyle) {
        StringBuffer objectString = new StringBuffer();
        String fieldName = "";
        String fieldType = "";

        if (object == null) {
            return "";
        }

        // LOG는 제외
        if (object instanceof Logger || object instanceof org.apache.commons.logging.Log) {
            return "";
        }

        // Locale은 내부에 또 Locale이 있어 StackOverflow 에러가 나므로 그냥 객체의 toString 반환으로 종료
        if(object instanceof Locale) {
            return object.toString();
        }

        // Object 타입 확인
        if (isValue(object)) {
            objectString.append(object);
        } else if (object.getClass().isArray()) {

            boolean isFirst = true;
            objectString.append("Array@");

            int arrayLength = Array.getLength(object);

            objectString.append(arrayLength).append("(");

            for (int i = 0; i < arrayLength; i++) {
                if (!isFirst) {
                    objectString.append(", ");
                }
                if (!isFirst && stringStyle) {
                    objectString.append("\n");
                }
                objectString.append(convertObjectToString(Array.get(object, i), stringStyle)).append("\n");
                isFirst = false;
            }
            objectString.append(")");
            objectString.append("\n");
        } else if (object instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) object;
            Set<?> mapKeySet = map.keySet();
            boolean isFirst = true;

            objectString.append("Map@");
            objectString.append(map.size()).append("{");

            for (Object key : mapKeySet.toArray()) {
                Object obj = map.get(key);

                if (!isFirst) {
                    objectString.append(", ");
                }
                if (!isFirst && stringStyle) {
                    objectString.append("\n");
                }

                objectString.append(key).append("=");

                if (isValue(obj)) {
                    objectString.append(obj);
                } else {
                    objectString.append(convertObjectToString(obj, stringStyle)).append("\n");
                }

                isFirst = false;
            }
            objectString.append("}");
            objectString.append("\n");
        } else if (object instanceof List) {
            objectString.append("List@");
            objectString.append(((List<?>) object).size()).append("(");

            boolean isFirst = true;
            for (Object obj : ((List<?>) object).toArray()) {
                if (!isFirst) {
                    objectString.append(", ");
                }
                if (!isFirst && stringStyle) {
                    objectString.append("\n");
                }
                if (isValue(obj)) {
                    objectString.append(obj);
                } else {
                    objectString.append(convertObjectToString(obj, stringStyle)).append("\n");
                }

                isFirst = false;
            }
            objectString.append(")");
            objectString.append("\n");
        } else if(object.getClass().isEnum()) {
            objectString.append(object);
        } else {
            objectString.append("[");
            boolean isFirst = true;
            for (Field fld : object.getClass().getDeclaredFields()) {
                fieldName = fld.getName();
                fieldType = fld.getType().getName();
                Class<?> clazz = fld.getType();

                fld.setAccessible(true);
                if (!isFirst) {
                    objectString.append(", ");
                }
                if (!isFirst && stringStyle) {
                    objectString.append("\n");
                }

                try {
                    // 필드명 추출, 필드 객체 추출 - 타입 확인
                    Object fieldObject = fld.get(object);
                    objectString.append(fieldName).append("=");

                    if (clazz.isPrimitive() || fieldType.startsWith("java.lang.String")) {
                        objectString.append(fieldObject);
                    } else {
                        objectString.append(convertObjectToString(fieldObject, stringStyle));
                    }
                } catch (IllegalArgumentException e) {
                    logger.debug("IllegalArgumentException");
                } catch (IllegalAccessException e) {
                    logger.debug("IllegalAccessException");
                }

                isFirst = false;
            }
            objectString.append("]");
        }

        return objectString.toString();
    }

    /**
     * Object가 Primitive Wrapper Class, String 인지 확인
     * @param obj
     * @return
     */
    private static boolean isValue(Object obj) {
        return obj instanceof String || obj instanceof Integer || obj instanceof Character
                || obj instanceof Double || obj instanceof Byte || obj instanceof Long
                || obj instanceof Float || obj instanceof Short || obj instanceof Boolean;
    }

    /**
     * Object의 필드 값을 반환
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFiledValue(Object object, String fieldName) {

        if (object == null || StringUtils.isBlank(fieldName)) {
            return null;
        }

        try {

            for (Field field : object.getClass().getDeclaredFields()) {
                String objFieldName = field.getName();
//				String objFieldType = field.getType().getName();

                if (fieldName.equals(objFieldName)) {
                    field.setAccessible(true);
                    return field.get(object);
                }
            }
//			Field field = object.getClass().getField(fieldName); // public 필드만 조회 가능함
        } catch (IllegalArgumentException e) {
            logger.debug("ERROR IllegalArgumentException");
        } catch (IllegalAccessException e) {
            logger.debug("ERROR IllegalAccessException");
        } catch (SecurityException e1) {
            logger.debug("ERROR SecurityException");
        }

        return null;
    }

    /**
     * Object의 필드에 값을 설정
     * @param object
     * @param fieldName
     * @param fieldValue
     * @param initValue
     * @return
     */
    public static boolean setFiledValue(Object object, String fieldName, Object fieldValue, String initValue) {

        if (object == null || StringUtils.isBlank(fieldName)) {
            return false;
        }

        try {

            for (Field field : object.getClass().getDeclaredFields()) {
                String objFieldName = field.getName();
//				String objFieldType = field.getType().getName();

                // TODO 값을 설정할 object의 필드 타입과 설정할 value 의 타입 확인 및 변환
                if (fieldName.equals(objFieldName)) {
                    field.setAccessible(true);

                    if (field.getType() == String.class) {
                        field.set(object, fieldValue.toString());
                    } else {
                        field.set(object, fieldValue);
                    }
                    return true;
                }
            }
        } catch (IllegalArgumentException e) {
            logger.debug("ERROR IllegalArgumentException");
        } catch (IllegalAccessException e) {
            logger.debug("ERROR IllegalAccessException");
        } catch (SecurityException e1) {
            logger.debug("ERROR SecurityException");
        }

        return false;
    }


}
