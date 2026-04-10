package com.ktis.mcpif.common;

public class XmlUtil {
    public static String createXmlElement(String field, String value) {
        String valueNotNull = value == null ? "" : value;

        StringBuffer sb = new StringBuffer();
        sb.append("<").append(field).append(">");
        sb.append(valueNotNull.replaceAll("&", "<![CDATA[&]]>"));
        sb.append("</").append(field).append(">");
        return sb.toString();
    }

    public static String createXmlElementIfNotEmpty(String field, String value) {
        if (StringUtil.isEmpty(value)) {
            return "";
        }

        return createXmlElement(field, value);
    }
}
