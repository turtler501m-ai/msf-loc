<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<% response.addHeader("P3P","CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\""); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title> - 나이스 본인인증</title>
        <meta name="title" content="kt M모바일" />
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" >
        $(document).ready(function(){
        	document.form_chk.submit();
        })
        </script>
        
    </head>
    <body>
        
        <form name="form_chk" method="post" action="https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb" >		    
			<input type="hidden" id="m" name="m" value="service" />
			<input type="hidden" id="token_version_id" name="token_version_id" value="${tokenVersionId}" />
			<input type="hidden" id="enc_data" name="enc_data" value="${encData}"/>
			<input type="hidden" id="integrity_value" name="integrity_value" value="${integrityValue}"/>
		</form>

    </body>
</html>