<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title> - 나이스 본인인증</title>
        <meta name="title" content="kt M모바일" />
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" >
        $(document).ready(function(){
            alert("본인인증에 실패하였습니다.["+$("#ErrorMsg").val()+"]\n다시 시도 하시기 바랍니다.");
			if(opener != null) {
				self.close();
			} else {
				parent.fnNiceCertErr();
			}
        })
        </script>
        
    </head>
    <body>
        <input type="hidden" id="ErrorMsg" name="ErrorMsg" value="${ErrorMsg}">
    </body>
</html>