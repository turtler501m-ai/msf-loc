<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title>Mstore SSO 연동</title>
        <meta name="title" content="kt M모바일" />
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <script type="text/javascript" >
            document.addEventListener("DOMContentLoaded", () => {

                 var resultCd  = '${resultCd}';
                 var isMobile = '${isMobile}';
                 var errorCause = '${errorCause}';
                 var landingUrl = '${landingUrl}';

                if(resultCd == "0000"){
                    var lc;
                    if (landingUrl) {
                        lc = "?lc=" + encodeURIComponent(landingUrl);
                    } else {
                        lc = "";
                    }

                    document.mstoreSSOForm.method= "post";
                    document.mstoreSSOForm.action = "${mstoreCertUrl}/sso/loginSsoGateQAY" + lc;
                    document.mstoreSSOForm.submit();
                }else{
                    if(isMobile =='Y') location.href = "/m/point/mstoreErrorView.do?resultCd="+resultCd+"&errorCause="+errorCause;
                    else location.href = "/point/mstoreErrorView.do?resultCd="+resultCd+"&errorCause="+errorCause;
                }
            });
        </script>
    </head>

    <body>
        <form name="mstoreSSOForm">
            <input type="hidden" name="cmpyNo" value="${cmpyNo}">
            <input type="hidden" name="mbrGrdCd" value="${mbrGrdCd}">
            <input type="hidden" name="memID" value="${memId}">
            <input type="hidden" name="lstComActvDate" value="${lstComActvDate}">
        </form>
    </body>

</html>

