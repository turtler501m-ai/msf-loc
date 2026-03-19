package com.ktmmobile.msf.common.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * M플랫폼 응답 XML 파싱 유틸. MCP XmlParse 와 동일 목적 (org.w3c.dom 사용).
 */
public final class XmlParseUtil {

    private XmlParseUtil() {}

    public static Document getDocument(String xml) throws Exception {
        return DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    /** return 요소 찾기 (SOAP 루트 또는 document 루트 하위) */
    public static Element getReturnElement(Document doc) {
        doc.getDocumentElement().normalize();
        Element root = doc.getDocumentElement();
        if ("return".equals(localName(root))) {
            return root;
        }
        NodeList list = doc.getElementsByTagName("return");
        return list.getLength() > 0 ? (Element) list.item(0) : null;
    }

    public static Element getChildElement(Element parent, String tagName) {
        if (parent == null) return null;
        NodeList list = parent.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE && tagName.equals(localName((Element) n))) {
                return (Element) n;
            }
        }
        return null;
    }

    public static String getChildValue(Element parent, String tagName) {
        Element child = getChildElement(parent, tagName);
        if (child == null) return "";
        String v = child.getTextContent();
        return v != null ? v.trim() : "";
    }

    /** 자식 요소 중 tagName 인 것만 목록 반환 (직계 자식만) */
    public static List<Element> getChildElementList(Element parent, String tagName) {
        List<Element> result = new ArrayList<>();
        if (parent == null) return result;
        NodeList list = parent.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE && tagName.equals(localName((Element) n))) {
                result.add((Element) n);
            }
        }
        return result;
    }

    private static String localName(Element e) {
        String name = e.getLocalName();
        return name != null ? name : e.getTagName();
    }
}
