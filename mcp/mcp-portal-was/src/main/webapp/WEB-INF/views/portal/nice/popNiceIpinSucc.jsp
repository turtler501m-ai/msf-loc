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
            var errorCode = $("#errorCode").val();

            if (errorCode = '0000') {
            	var rtnObj = {
                     reqSeq: ""
                   , resSeq: ""
                   , authType: "I"
               }

               rtnObj.reqSeq = $("#reqSeq").val();
               rtnObj.resSeq = $("#resSeq").val();

     			if(opener != null) {
	                opener.fnNiceIpinCert(rtnObj);
	                self.close();
				} else {
					parent.fnNiceIpinCert();
				}

            } else {
            	var errorMsg = $("#errorMsg").val();
            	alert(errorMsg);

     			if(opener != null) {
                    self.close();
    			} else {
    				parent.fnNiceIpinCertErr();
    			}
            }
            
            
        })
        </script>
        
    </head>
    <body>
    	<input type="hidden" id="reqSeq" name="reqSeq" value="${NiceRes.reqSeq}"/>
        <input type="hidden" id="resSeq" name="resSeq" value="${NiceRes.resSeq}"/>
        <input type="hidden" id="errorCode" name="errorCode" value="${ErrorCode}"/>
        <input type="hidden" id="errorMsg" name="errorMsg" value="${ErrorMsg}"/>
    </body>
</html>