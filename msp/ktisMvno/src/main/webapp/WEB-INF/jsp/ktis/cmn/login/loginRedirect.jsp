<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%  
//System.out.println(">>>>>>>>>>>>>>>>>>>>>>> loginRedirect.jsp");
//  	response.sendRedirect("/iww/common/loginForm.ce");
%>

<script type='text/javascript'>
 	alert('세션이 종료 되었습니다. 다시 로그인 하여 주시기 바랍니다.');
	try{
		opener.location.href="<c:url value='/cmn/login/loginForm.do'/>";
		self.close();
	}catch(e){
		location.href="<c:url value='/cmn/login/loginForm.do'/>";
	}
</script>


