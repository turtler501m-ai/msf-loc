<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="jsver" value="${nmcp:getJsver()}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <c:choose>
        <c:when test="${nmcp:getPlatFormCd() eq 'P'}">
            <link rel="stylesheet" type="text/css" href="/resources/css/portal/styles.css" />
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" type="text/css" href="/resources/css/mobile/styles.css" />
        </c:otherwise>
    </c:choose>
    <title>kt M모바일 공식 다이렉트몰</title>
</head>
<body>


    <div class="ly-wrap">
    </div>


    <script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resources/js/nmcpCommon.js"></script>
    <c:choose>
        <c:when test="${nmcp:getPlatFormCd() eq 'P'}">
            <script type="text/javascript" src="/resources/js/portal/ktm.ui.js"></script>
            <script type="text/javascript" src="/resources/js/portal/nmcpCommonComponent.js"></script>
        </c:when>
        <c:otherwise>
            <script type="text/javascript" src="/resources/js/mobile/ktm.ui.js"></script>
            <script type="text/javascript" src="/resources/js/mobile/nmcpCommonComponent.js"></script>
        </c:otherwise>
    </c:choose>

    <script>
        let MCP = new NmcpCommonComponent();
        var errMsg      = '${errMsg}';
        var redirectUrl  = '${redirectUrl}';

        window.addEventListener('load', function() {
            MCP.alert(errMsg,function (){
                if (redirectUrl == '') {
                    if(history.length > 1){
                        history.go(-1);
                    }
                } else {
                    location.href = redirectUrl;
                }
            });
        });

    </script>


</body>
</html>
