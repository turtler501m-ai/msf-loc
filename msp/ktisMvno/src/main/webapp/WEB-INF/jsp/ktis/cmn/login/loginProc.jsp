<%@ page language="java" contentType ="text/html; charset=UTF-8" session="false" %>
<%@page import="org.apache.commons.lang.StringUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%

response.setHeader("Pragma","No-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0);
response.setHeader ("Cache-Control", "no-cache");
/*
 ******************************************************************************************
 * PROGRAM ID   :   
 * Description  :   
 * Input Parm   :
 * Output Parm  :
 * Include FILE :
 * Using Table  :
 * Return Value :
 * Sub Function :
 * SE Name        Description                                             Date
 * ------------   -----------------------------------------------------   -----------
 * nicos
 ****************************************************************************************
 */

/******************************************************************************************
 * INCLUDE
 ******************************************************************************************/


String msg = StringUtils.defaultString((String)request.getAttribute("msg"),"");
String uri = StringUtils.defaultString((String)request.getAttribute("uri"),"");
String passChange = StringUtils.defaultString((String)request.getAttribute("passChange"),"");

//System.out.println(">>>>>>>>>>>>>>>msg:" + msg );
//System.out.println(">>>>>>>>>>>>>>>uri:" + uri );
 
%>
<form name="ceform" id="ceform"  method="post"></form>
<script >
    var JSP = {        
        passChange : "<%=passChange%>"
    };
<%
		
	if ( !"".equals(msg)){
%>
     	alert("<%= msg %>");
<%
	}
%>
    var f=document.ceform;
    if(JSP.passChange == 'Y'){
        f.action = "<c:url value='/main.do?' />"  + 'passChange=<%=passChange%>' ;
    } 
    else {
    	f.action = "<c:url value='/main.do' />";
    }
    f.target = "_top";
    f.submit();
</script>
  
