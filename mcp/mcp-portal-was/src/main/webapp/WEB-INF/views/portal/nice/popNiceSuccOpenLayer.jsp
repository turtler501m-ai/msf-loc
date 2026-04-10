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

            var rtnObj = {
                  reqSeq:""
                , resSeq:""
                , authType:""
                , hasSuccess:""
            }

            rtnObj.reqSeq = $("#reqSeq").val();
            rtnObj.resSeq = $("#resSeq").val();
            rtnObj.authType = $("#authType").val();
            rtnObj.hasSuccess = $("#hasSuccess").val();

            var nextUrl = $("#nextUrl").val();

            if(opener != null) {
                var resultMsg = opener.fnNiceCertOpenLayer(rtnObj);

                if (resultMsg == "1") {
                    alert("본인인증에 성공 하였습니다.");
                    if (nextUrl != "") location.href= nextUrl;
                    else self.close();

                } else {
                    if (resultMsg != null) alert(resultMsg);
                    self.close();
                }

            } else {
                parent.fnNiceCertOpenLayer(rtnObj);
            }
        })
        </script>



    </head>
    <body>
        <input type="hidden" id="reqSeq" name="reqSeq" value="${NiceRes.reqSeq}">
        <input type="hidden" id="resSeq" name="resSeq" value="${NiceRes.resSeq}">
        <input type="hidden" id="authType" name="authType" value="${NiceRes.authType}">
        <input type="hidden" id="hasSuccess" name="hasSuccess" value="${NiceRes.param_r1}">
        <input type="hidden" id="nextUrl" name="nextUrl" value="${NextUrl}">
    </body>
</html>