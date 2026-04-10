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

<script language="javascript">
function heaven_control(cmd)
{
	switch (cmd){
		case "play" :{
			if (ucc_player.url == '') return;
			ucc_player.play();
			break;
		}
		case "stop" :{
			ucc_player.stop();
			break;
		}
		case "pause" :{
			if (ucc_player.PlayState ==2) ucc_player.pause();
			break;
		}
	}
}
</script>

<object id="ucc_player" width=220 height=167 classid='clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95'>
 <param name='Filename' value= '<%=fileNm2%>' >
 <param name='ShowControls' value='0'>
 <param name="AutoStart" value="0">
</object>
<br></br>
	<div class="page-header">
		<h1><%=fileNm2%></h1><br></br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="javascript:heaven_control('play')"> <input type="image" src="/images/etc/wav_adult_play.png" /></a> &nbsp;&nbsp;&nbsp;
		<a href="#" onclick="javascript:heaven_control('pause')">  <input type="image" src="/images/etc/wav_adult_pause.png" /></a> &nbsp;&nbsp;&nbsp;
		<a href="#" onclick="heaven_control('stop')">  <input type="image" src="/images/etc/wav_adult_stop.png" /></a> 
	</div>
</html>