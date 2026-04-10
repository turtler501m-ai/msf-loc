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
String shopCd = URLDecoder.decode(request.getParameter("shopCd") == null ? "" : request.getParameter("shopCd") ,"UTF-8");
//System.out.print(scanId+shopCd);

%>
<script language="javascript">
function init(){
	//alert("dataResult : " + "<%=scanId%>" + "        " + "<%=shopCd%>");
	try{
		 opener.document.getElementsByName("scanIdBySearch")[0].value = "<%=scanId%>";
		 opener.document.getElementsByName("shopCdBySearch")[0].value = "<%=shopCd%>";
		 //opener.document.getElementsByName("spcCode")[0].value = "<%=scanId%>";
	}catch(e){
		 window.close();
	}
	 window.close();
}

</script>

<body onload="init();">
	<form id="form" name="form" method="post">
		
		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
	</form>


</body>
</html>