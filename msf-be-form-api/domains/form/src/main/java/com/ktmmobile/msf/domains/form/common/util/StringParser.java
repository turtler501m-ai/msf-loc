package com.ktmmobile.msf.domains.form.common.util;

public class StringParser {
       public String convertHtmlchars(String htmlstr)  // 내용중 HTML 툭수기호인 문자를 HTML 특수기호 형식으로 변환
        {
            String convert = "";
            convert = htmlstr.replace( "<", "&lt;");
            convert = htmlstr.replace( ">", "&gt;");
            convert = htmlstr.replace( "\"", "&quot;");
            convert = htmlstr.replace("&nbsp;", "&amp;nbsp;");
            return convert;
        }
}
