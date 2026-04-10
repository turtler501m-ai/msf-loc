<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%  
//  	response.sendRedirect("/iww/common/loginForm.ce");
%>

<script type='text/javascript'>
 	alert('권한이 없는 화면입니다.\n(' + '${pRequestParamMap.noAuthUrl}' + ')');
// 	window.history.back()

</script>	


