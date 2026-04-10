<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="com.ubireport.common.util.StrUtil,java.net.*,javax.net.ssl.HttpsURLConnection,java.io.*" %>
<%
	// 보안을 위해 설치 후 임시로 false로 변경해서 결과 확인 후 소스 배포 시 무조건 true로 변경해야함
	boolean refererCheck = false;
	String referer = StrUtil.nvl(request.getHeader("referer"), "");	// REFERER 가져오기
	if( refererCheck && (referer.equals("") || referer.indexOf(request.getServerName()) == -1) ) { 	// REFERER 체크(브라우저에서 직접 호출 방지)
		out.clear();
		out.print("비정상적인 접근입니다.");
		return;
	}

	//웹어플리케이션명
	String appName = StrUtil.nvl(request.getContextPath(), "");
	if( appName.indexOf("/") == 0 ) {
		appName = appName.substring(1, appName.length());
	}	

	//웹어플리케이션 Root URL, ex)http://localhost:8080/myapp
	String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + ((appName.length() == 0)?"":"/") + appName;

	//UI에서 호출될 때 필요한 정보
	//String file = StrUtil.nvl(request.getParameter("file"), "ubi_sample.jrf");
	//String arg = StrUtil.encrypt64(StrUtil.nvl(request.getParameter("arg"), "user#홍길동#company#유비디시전#"),"UTF-8");
	//String resid = StrUtil.nvl(request.getParameter("resid"), "UBIHTML");
	
	String sUbiToken = (String)session.getAttribute("ubiToken");
	//String tokenUrl = "http://10.21.28.12:8180/" + "/UbiServer?cHJvY2lk=TOKEN&useUbiToken=true&reqtype=-1";
	String tokenUrl = "http://localhost:" + "8180" + "/UbiServer?cHJvY2lk=TOKEN&useUbiToken=true&reqtype=-1";
	URL url = new URL(tokenUrl);
	
    // Open a connection
    if(sUbiToken == null || sUbiToken.equals("")) {
	    HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
	
	    // Set the request method
	    urlcon.setRequestMethod("POST");
	
	    // Optional: Set timeouts, request headers etc.
	    // con.setConnectTimeout(5000);
	    // con.setReadTimeout(5000);
	
	    // If POST request, write output to con.getOutputStream()
	
	    // Get the response code
	    int status = urlcon.getResponseCode();
	    // Read the response
	    BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
	    String inputLine;
	    StringBuffer content = new StringBuffer();
	    while ((inputLine = in.readLine()) != null) {
	        content.append(inputLine);
	    }
	    String ubiToken = content.toString();
		session.setAttribute("ubiToken", ubiToken);
	    in.close();
    }
/*
	System.out.println("[appUrl] " + appUrl);
	System.out.println("[file] " + file);
	System.out.println("[arg] " + arg);
	System.out.println("[resid] " + resid);
*/
	out.clearBuffer();
%>
<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.5,user-scalable=yes">
<title>UbiReport Viewer</title>
<link rel="stylesheet" type="text/css" href="/ubi4/css/ubieform.css" />
<!--[if IE]><script src='./js/ubiexcanvas.js'></script><![endif]-->
<!-- script src='./js/fabric.js'></script-->
<script src='/ubi4/js/ubicommon.js'></script>
<script src='/ubi4/js/ubihtml.js'></script>
<script src='/ubi4/js/msg.js'></script>
<script src='/ubi4/js/ubinonax.js'></script>
<script src='/ubi4/js/ubieform.js'></script>

<script language='javascript'>
<!--

var appName = '<%= appName %>';
var jrf = '${jrf}';											// 리포트파일명.
var arg = '${arg}';	// 아규먼트
var res_id = '${res_id}';	
var appUrl = self.location.protocol + '//' + self.location.host + (appName.length==0?'':'/') + appName;

var ubiHtmlViewer = null;
var ubiParams = {
	"appurl": appUrl
	,"key": "<%= session.getId() %>" 
	,"issvg": "true" 
	,"jrffile": jrf
	,"arg": arg
	,"resid": res_id
	,"isencrypt64" : "false"
	,"ubiToken" : "<%=session.getAttribute("ubiToken")%>"
};

var ubiEvents ={
	/*
	 "reportevent.previewend": ubiReportPreviwEnd
	,"reportevent.printend": ubiReportPrintEnd
	,"reportevent.exportend": ubiReportExportEnd
	,"reportevent.printClicked": ubiReportPrintClicked
	,"reportevent.exportClicked": ubiReportExportClicked
	*/
	/*
	"eformevent.previewend": ubiEformPreviewEnd
	,"eformevent.saveend": ubiEformSaveEnd
	*/
};

function ubiStart() {
	ubiHtmlViewer = UbiLoad(ubiParams, ubiEvents);
	
	/* report toolbar control api */
	//try { ubiHtmlViewer.setUserSaveList('Image,Pdf,Docx,Xls,Pptx,Hml,Cell'); }catch(e){}
	//try { ubiHtmlViewer.setUserPrintList('Ubi,Html,Pdf'); }catch(e){}
	//try { ubiHtmlViewer.setVisibleToolbar('INFO', false); }catch(e){}
};

function ubiReportPreviwEnd() {
	console.debug('ubiReportPreviwEnd......');
};

function ubiReportPrintEnd() {
	console.debug('ubiReportPrintEnd......');
};

function ubiReportExportEnd() {
	console.debug('ubiReportExportEnd......');
};

function ubiReportPrintClicked() {
	console.debug('ubiReportPrintClicked......');
};

function ubiReportExportClicked() {
	console.debug('ubiReportExportClicked......');
};

function ubiEformPreviewEnd() {
	console.debug('ubiEformPreviewEnd......');
};

function ubiEformSaveEnd(file) {
	console.debug('ubiEformSaveEnd......' + file);
};

//-->
</script>
</head>
<body onload='ubiStart()'>
</body>
</html>
