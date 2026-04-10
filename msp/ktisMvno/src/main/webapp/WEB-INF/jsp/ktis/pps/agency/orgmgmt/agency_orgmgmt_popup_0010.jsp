<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%  request.setCharacterEncoding("UTF-8");
    String formName = StringUtils.isEmpty(request.getParameter("formName")) ? "form" : request.getParameter("formName");
    String itemName4ZipCd = StringUtils.isEmpty(request.getParameter("itemName4ZipCd")) ? "zipcd" : request.getParameter("itemName4ZipCd");
    String itemName4Addr1 = StringUtils.isEmpty(request.getParameter("itemName4Addr1")) ? "addr1" : request.getParameter("itemName4Addr1");
    String itemName4Addr2 = StringUtils.isEmpty(request.getParameter("itemName4Addr2")) ? "addr2" : request.getParameter("itemName4Addr2");

	String inputYn = request.getParameter("inputYn"); 
	String roadFullAddr = request.getParameter("roadFullAddr");
	String roadAddrPart1 = request.getParameter("roadAddrPart1"); 
	String roadAddrPart2 = request.getParameter("roadAddrPart2"); 
	String engAddr = request.getParameter("engAddr"); 
	String jibunAddr = request.getParameter("jibunAddr"); 
	String zipNo = request.getParameter("zipNo"); 
	String addrDetail = request.getParameter("addrDetail"); 
%>
</head>
<script language="javascript">
function init(){
	var url = location.href;

	var confmKey = "bnVsbDIwMTQxMDAxMTU0MjQ1";
	var inputYn= "<%=inputYn%>";
	if(inputYn != "Y"){
		document.form.confmKey.value = confmKey;
		document.form.returnUrl.value = url;
		document.form.action="http://www.juso.go.kr/addrlink/addrLinkUrl.do"; //인터넷망
		document.form.submit();
	}else{
		 var formName = "<%=formName%>";
		 
 		 var selectedData = { "<%=itemName4Addr1%>":"<%=roadAddrPart1%>", "<%=itemName4Addr2%>":"<%=addrDetail%>", "<%=itemName4ZipCd%>":"<%=zipNo%>"};
 		 
		 opener.mvno.ui.callback4ZipCd(formName ,selectedData);
		 window.close();
	}
}

</script>
<body onload="init();">
	<form id="form" name="form" method="post">
		<input type="hidden" id="confmKey" name="confmKey" value=""/>
		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
	</form>
</body>
</html>