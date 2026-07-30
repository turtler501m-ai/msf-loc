package com.ktmmobile.msf.domains.externalclient.mspprx.support.util;

import java.io.StringWriter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * JAXB 기반 XML 변환 유틸리티
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XmlConvertUtils {

    private static final XmlMapper XML_MAPPER = XmlMapper.builder()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .build();

    /**
     * Java 객체의 XML 문자열 변환
     *
     * @param object 변환할 객체
     * @param <T> 변환 대상 타입
     * @return XML 문자열
     */
    public static <T> String convertObjectToXml(T object) {
        if (object == null) {
            return "";
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(object, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            throw new IllegalStateException("XML 변환 중 오류가 발생했습니다. 대상 클래스: " + object.getClass().getName(), e);
        }
    }

    public static <T> T xmlReturnParser(String xml, Class<T> clazz) {
        JsonNode root = XML_MAPPER.readTree(xml.getBytes());
        JsonNode returnNode = root.findValue("return");
        return XML_MAPPER.treeToValue(returnNode, clazz);
    }
}
