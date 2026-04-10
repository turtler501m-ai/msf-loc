<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<%  

request.setCharacterEncoding("UTF-8");
String scanId = URLDecoder.decode(request.getParameter("scanId") == null ? "" : request.getParameter("scanId") ,"UTF-8");


%>
<script language="javascript">
function init(){
	 alert("parent2.jsp : " + "<%=scanId%>");
	 alert(opener.document.getElementsByName("reqUsimSn")[0].value);
	
	 opener.document.getElementsByName("spcCode")[0].value = "<%=scanId%>";
	
}

</script>

<body onload="init();">
	<form id="form" name="form" method="post">
		
		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
	</form>


</body>
</html>