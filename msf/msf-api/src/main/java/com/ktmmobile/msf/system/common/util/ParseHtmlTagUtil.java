package com.ktmmobile.msf.system.common.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;

public class ParseHtmlTagUtil {
    public static String parseTag(String str) {
        String value = str;
        if (value != null) {
            // Normalize
            value = Normalizer.normalize(value, Form.NFKC);

            // Avoid null characters
            value = value.replaceAll("", "");
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",
                    Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // HTML transformation characters
            value = org.springframework.web.util.HtmlUtils.htmlEscape(value);
            // SQL injection characters
            value = StringEscapeUtils.escapeSql(value);
        }
        return value;
    }

    public static String replace(String original, String oldstr, String newstr) {
        StringBuffer convert = new StringBuffer();

        int pos = 0;
        int begin = 0;
        pos = original.indexOf(oldstr);

        if (pos == -1) { return original;}

        while (pos != -1) {
            convert.append(original.substring(begin, pos) );
            convert.append(newstr);
            begin = pos + oldstr.length();
            pos = original.indexOf(oldstr, begin);
        }

        convert.append(original.substring(begin));
        return convert.toString();
    }

    public static String convertHtmlchars(String htmlstr) {
        String convert = "";
        convert = replace(htmlstr, "&lt;", "<");
        convert = replace(convert, "&gt;", ">");
        convert = replace(convert, "&quot;", "\"");
        convert = replace(convert, "&amp;nbsp;", "&nbsp;");

        return convert;
    }


    public static String percentToEscape(String percent) {
        if(percent == null) return null;
        String convert = "";
        convert = replace(percent, "!", "!!");
        convert = replace(convert, "%", "!%");
        convert = replace(convert, "_", "!_");

        return convert;
    }

    public static String escapeToPercent(String percent) {
        if(percent == null) return null;
        String convert = "";
        convert = replace(percent, "!_", "_");
        convert = replace(convert, "!%", "%");
        convert = replace(convert, "!!", "!");

        return convert;
    }

}
