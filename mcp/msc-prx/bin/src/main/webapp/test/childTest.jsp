<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>

<script type="text/javascript">

<%
	request.setCharacterEncoding("UTF-8");
	String scanId = request.getParameter("scanId");


%>



</script>


<script language="javascript">
function init(){

	
	alert('test');
	var enUserName = encodeURI(encodeURIComponent("한글"));
	
	document.form.action="http://mspdev.ktism.com/scan/parent2.jsp?scanId="+enUserName; //인터넷망
	document.form.submit();
}

</script>

<body onload="init();">
	<form id="form" name="form" method="post">
		<input type="hidden" id="scanId" name="scanId" value="11qqwwee"/>
		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
	</form>



</body>
</html>