<%@ page import="com.ktmmobile.mcp.common.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String[] mobileKeyWords = {"iPhone", "iPod", "BlackBerry", "Android", "Windows CE", "LG", "MOT", "SAMSUNG", "SonyEricsson"};
	String userAgent = request.getHeader("user-agent");
	boolean isMobile = false;
	if(userAgent != null) {
		for(String str : mobileKeyWords){
			if(userAgent.indexOf(str) > 0){
				isMobile = true;
				break;
			}
		}
	}

	String sendUrl = "";
	String strQuery = request.getQueryString() != null ?  "?" + request.getQueryString().replaceAll("\r","").replaceAll("\n","") : "";

	if(request.getServerName().startsWith("sim.")){
		//response.sendRedirect("/usim/usimRegi.do");
		response.sendRedirect("/main.do");
	} else if(request.getServerName().startsWith("ktmmobile.com") && isMobile){
		sendUrl = "/m/main.do" + strQuery;
		response.sendRedirect(sendUrl);
	} else if(request.getServerName().startsWith("m.ktmmobile.com") && isMobile){
		sendUrl = "/m/main.do" + strQuery;
		response.sendRedirect(sendUrl);
	} else if(request.getServerName().startsWith("www.ktmmobile.com") && isMobile){
		sendUrl = "/m/main.do" + strQuery;
		response.sendRedirect(sendUrl);
	} else if(request.getServerName().startsWith("internettv.")){
		response.sendRedirect("/wire/wireMain.do");  //유선 사이트 이동
	} else if(request.getServerName().startsWith("nmcp.ktmmobile.com") && isMobile){
		sendUrl = "/m/main.do" + strQuery;
		response.sendRedirect(sendUrl);
	} else {
		sendUrl = "/main.do" + strQuery;
		response.sendRedirect(sendUrl);
	}
%>
