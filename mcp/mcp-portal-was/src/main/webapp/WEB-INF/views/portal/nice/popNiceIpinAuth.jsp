<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
        	if("${referer}"==null || "${referer}"==""){
        		$("#param_r1").val(opener.document.location);
        	}
        	document.form_chk.submit();
        })
        </script>
        
    </head>
    <body>
        
        <form name="form_chk" method="post" action="https://cert.vno.co.kr/ipin.cb" >
            
            <input type="hidden" name="m" value="pubmain">                      <!-- 필수 데이타로, 누락하시면 안됩니다. -->
		    <input type="hidden" name="enc_data" value="${EncodeData}">       <!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
		    
		    <!-- 업체에서 응답받기 원하는 데이타를 설정하기 위해 사용할 수 있으며, 인증결과 응답시 해당 값을 그대로 송신합니다.
		         해당 파라미터는 추가하실 수 없습니다. -->
		    <input type="hidden" name="param_r1" id="param_r1" value="${referer}">
		    <input type="hidden" name="param_r2" id="param_r2" value="I">
		    <input type="hidden" name="param_r3" id="param_r3" value="${sCheck }">
		</form>

    </body>
</html>