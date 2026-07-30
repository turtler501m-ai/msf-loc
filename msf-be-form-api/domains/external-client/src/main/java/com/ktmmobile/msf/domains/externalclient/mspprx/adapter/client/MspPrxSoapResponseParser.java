package com.ktmmobile.msf.domains.externalclient.mspprx.adapter.client;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.exception.MspPrxClientException;

@Component
class MspPrxSoapResponseParser {

    MspPrxSoapResponse parse(String responseXml) {
        if (!StringUtils.hasText(responseXml)) {
            throw new MspPrxClientException("PRX 응답 XML이 비어 있습니다.");
        }

        Element operation = firstElementChild(soapBody(parseDocument(responseXml)));
        if (operation == null) {
            throw new MspPrxClientException("PRX SOAP Body에 응답 노드가 없습니다.");
        }

        Element returnElement = firstChildElementByName(operation, "return");
        if (returnElement == null) {
            throw new MspPrxClientException("PRX 응답에 return 노드가 없습니다.");
        }

        Map<String, String> bizHeader = textChildren(firstChildElementByName(returnElement, "bizHeader"));
        Map<String, String> commHeader = textChildren(firstChildElementByName(returnElement, "commHeader"));
        Map<String, Object> payload = payload(returnElement);

        return new MspPrxSoapResponse(null, localName(operation), bizHeader, commHeader, payload, responseXml);
    }

    private Document parseDocument(String responseXml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
            return factory.newDocumentBuilder()
                .parse(new ByteArrayInputStream(responseXml.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new MspPrxClientException("PRX 응답 XML 파싱에 실패했습니다.", e);
        }
    }

    private Element soapBody(Document document) {
        NodeList nodes = document.getElementsByTagNameNS("*", "Body");
        if (nodes.getLength() == 0 || !(nodes.item(0) instanceof Element body)) {
            throw new MspPrxClientException("PRX 응답에 SOAP Body가 없습니다.");
        }
        return body;
    }

    private Map<String, Object> payload(Element returnElement) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Element child: childElements(returnElement)) {
            String name = localName(child);
            if ("bizHeader".equals(name) || "commHeader".equals(name)) {
                continue;
            }
            put(result, name, toValue(child));
        }
        return result;
    }

    private Object toValue(Element element) {
        List<Element> children = childElements(element);
        if (children.isEmpty()) {
            return element.getTextContent() == null ? "" : element.getTextContent().trim();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        for (Element child: children) {
            put(result, localName(child), toValue(child));
        }
        return result;
    }

    private Map<String, String> textChildren(Element element) {
        if (element == null) {
            return Map.of();
        }

        Map<String, String> result = new LinkedHashMap<>();
        for (Element child: childElements(element)) {
            result.put(localName(child), child.getTextContent() == null ? "" : child.getTextContent().trim());
        }
        return result;
    }

    private void put(Map<String, Object> target, String name, Object value) {
        target.compute(name, (_, previous) -> {
            if (previous == null) {
                return value;
            }

            List<Object> values = previous instanceof List<?> previousValues
                ? new ArrayList<>(previousValues)
                : new ArrayList<>(List.of(previous));
            values.add(value);
            return values;
        });
    }

    private Element firstChildElementByName(Element parent, String name) {
        for (Element child: childElements(parent)) {
            if (name.equals(localName(child))) {
                return child;
            }
        }
        return null;
    }

    private Element firstElementChild(Element parent) {
        List<Element> children = childElements(parent);
        return children.isEmpty() ? null : children.getFirst();
    }

    private List<Element> childElements(Element parent) {
        List<Element> result = new ArrayList<>();
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element element) {
                result.add(element);
            }
        }
        return result;
    }

    private String localName(Element element) {
        String localName = element.getLocalName();
        return localName == null ? element.getNodeName() : localName;
    }
}
