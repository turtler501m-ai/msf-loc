<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title>스마트로 결제 결과</title>
        <meta name="title" content="kt M모바일" />
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" >
        
            $(document).ready(function(){
                var selfDlvryIdx = $("#selfDlvryIdx").val();
                 if (selfDlvryIdx.indexOf("F") > -1) {
                     $("#selfDlvryIdx").val("0");
                 }

                if('${result.ResultCode}' == '3001' || '${result.ResultCode}' == 'KP00' || '${result.ResultCode}' == 'NP00' || '${result.ResultCode}' == 'PP00'){
                    $('#frm').attr('action', '${result.succUrl}');
                    $('#frm').submit();
                }else{
                    alert('${result.msg}');
                    var requestKey = $('#requestKeyTemp').val();
                    $('#frm').attr('action', '${result.failUrl}');
                    $('#requestKey').val(requestKey);
                    $('#frm').submit();
                }
            });
        </script>
    </head>
    <body>
        <form name="frm" id="frm" method="post">
            <input type="hidden" id="selfDlvryIdx" name="selfDlvryIdx" value="${result.Moid}"/>
            <input type="hidden" id="requestKey" name="requestKey" value="${result.requestKey}"/>
            <input type="hidden" id="requestKeyTemp" name="requestKeyTemp" value="${result.requestKeyTemp}"/>
        </form>
    </body>
</html>