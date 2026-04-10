<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<%  

	request.setCharacterEncoding("UTF-8");

	String fileNm = URLDecoder.decode(request.getParameter("fileNm") == null ? "" : request.getParameter("fileNm") ,"UTF-8");
	String fileNm2 = URLDecoder.decode(request.getParameter("fileNm2") == null ? "" : request.getParameter("fileNm2") ,"UTF-8");
    String filePath = request.getParameter("filePath");
    String fullName = "";
	
	fullName = filePath + fileNm;
%>

<audio controls="" preload="" autoplay="" loop="">
  <source src=<%=fullName%> type="audio/mp3"/>
</audio>
