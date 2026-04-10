<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
<HEAD>
<TITLE>▒▒▒▒▒▒ 이미지 뷰어 ▒▒▒▒▒▒</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<!--
function ActiveXObjectTag(id)
{
  this.id           = id;
  this.param        = new Array();
  this.att		      = new Array();
  this.setParam     = setParam;
  this.setAttribute = setAttribute;
  this.show   = show;

  function setParam(name, value)
  {
    this.param[this.param.length] = { name:name, value:value };
  }

  function setAttribute(name, value)
  {
    this.att[this.att.length] = { name:name, value:value };
  }

  function show()
  {
    var strobj = "";

    strobj = "<OBJECT ID='" + this.id + "' ";
    for(var i=0; i<this.att.length; i++)
    {
      strobj +=  this.att[i].name + "= '" + this.att[i].value + "' ";
    }
    strobj += " >";

    for(var i=0; i<this.param.length; i++)
    {
      strobj += "<PARAM NAME='" + this.param[i].name + "' VALUE='" + this.param[i].value + "'>";
    }
    strobj += "</OBJECT>";

    document.writeln(strobj);
  }
}
-->
</SCRIPT>
</HEAD>

<BODY onFocus="javascript:checkActiveX()">

<form action="" name="frm" id="frm">
	<input type="hidden" name="scanId" id="scanId" value="${scanInfoVo.scanId}">
	<input type="hidden" name="callType" id="callType" value="${scanInfoVo.callType}">
	<input type="hidden" name="resNo" id="resNo" value="${scanInfoVo.resNo}">
		
	<input type="hidden" name="fileId" id="fileId" value="2a0ec29a896749399800b6d5c854632d">
	<div>
		<table>
			<tr>
				<td width="10%">
					<c:if test="${scanInfoVo.requestStateCode ne '21'}">
						<input type="button" name="imageDel" id="imageDel" value="이미지 삭제" onclick="imageDelete()">
					</c:if>
					<c:if test="${scanInfoVo.requestStateCode eq '21' and scanInfoVo.orgType eq 'K'}">
						<input type="button" name="imageDel" id="imageDel" value="이미지 삭제" onclick="imageDelete()">
					</c:if>
				</td>
				<td width="90%" style="text-align: right;">
					서식지 등록일자 : ${scanInfoVo.rgstDt}
				</td>
			</tr>
		</table>
	</div>
</form>

<SCRIPT language="JavaScript" type="text/javascript">
   var category = new ActiveXObjectTag('CNPECMWindowsWebInterface');
   category.setAttribute('classid', 'clsid:'+'EE70C460-5BA1-4BE5-ACC5-CEC481B43FF6');
   category.setAttribute('width', '100%');
   category.setAttribute('height', '95%');
   category.show();
</SCRIPT>


</BODY>
</HTML>


<SCRIPT language="JavaScript" type="text/javascript">
	
		/* [2] ActiveX 정보 설정 */
		var serverIP = "${scanInfoVo.url}"; // https://scndev.ktmmobile.com // 이미지 서버 IP or Domain Name
		var serverPort = ""; // 443 // 이미지 서버 접속 Port	
		var portType = "${scanInfoVo.port}";
		if(portType == "80") {
			serverPort = "80";
		}else if(portType == "443") {
			serverPort = "443";
		}else {
			serverPort = "443";
		}
		
		//var serverIP = "${scanInfoVo.url}"; // https://scndev.ktmmobile.com // 이미지 서버 IP or Domain Name
		//var serverPort = "443";
		
		var userID = "${scanInfoVo.userId}";//사용자 아이디
		var userNM = "${scanInfoVo.userNm}"; //사용자 이름
		var orgType = "${scanInfoVo.orgType}"; //조직 유형 - S:판매점, D:대리점, K:개통센터
		var orgOid = "${scanInfoVo.orgOid}"; //소속 조직 아이디
		var orgNM = "${scanInfoVo.orgNm}"; //소속 조직명
		
		var scanId = "${scanInfoVo.scanId}";
		var callType = "${scanInfoVo.callType}";

		//이미지 서버 정보 설정
		CNPECMWindowsWebInterface.SetServer(serverIP); // 이미지 서버 IP or Domain Name
		CNPECMWindowsWebInterface.SetPort(serverPort); // 이미지 서버 접속 Port
		
		//사용자 및 소속 조직 정보 설정
		CNPECMWindowsWebInterface.SetUserId(userID); //사용자 아이디
		CNPECMWindowsWebInterface.SetUserName(userNM); //사용자 이름
		
		CNPECMWindowsWebInterface.SetOrganizationType(orgType); //조직 유형 - 0:판매점, 1:대리점, 2:콜센터
		CNPECMWindowsWebInterface.SetOrganizationId(orgOid); //소속 조직 아이디
		CNPECMWindowsWebInterface.SetOrganizationName(orgNM); //소속 조직명
		//alert('4');
		/* [3] 이미지 뷰어 실행(건 전체) */
		CNPECMWindowsWebInterface.ClearViewer(); //이미지 뷰어 초기화
		
		CNPECMWindowsWebInterface.SetPrintAuthority(1); //이미지 뷰어 프린트 권한 - 0:권한없음, 1:권한있음
		CNPECMWindowsWebInterface.SetDBMSType(callType);//MSP->S/ MCP ->C
		CNPECMWindowsWebInterface.LoadServerImage(scanId); //서버 이미지 로딩, SCAN_ID 전달
		CNPECMWindowsWebInterface.SetZoom(2); //이미지 표시 방법 - 2:가로맞춤, 3:세로맞춤, 1:전체맞춤
		
		
	function checkActiveX()
	{

		var installedCustom = false;
		var CNPECMWindowsWebInterface;
		var nowVersion = "";
		//CNPECMWindowsWebInterface =  document.getElementById("CNPECMWindowsWebInterface");
		//nowVersion = CNPECMWindowsWebInterface.GetVersion();
		
		try
		{
			CNPECMWindowsWebInterface =  document.getElementById("CNPECMWindowsWebInterface");
			nowVersion = CNPECMWindowsWebInterface.GetVersion();
			
			if(nowVersion == "${scanInfoVo.version}") {
				installedCustom = true;
			}
		}
		catch(ex)
		{
			installedCustom = false;
		}

		if(installedCustom == false)
		{
			alert("설치 프로그램을 다운로드하여 프로그램을 설치하십시오!");
			//var url = "http://scn.ktmmobile.com/webscan/ui/downloadInstallProgram.jsp";
			var url = "${scanInfoVo.downloadUrl}";
			window.location.href = url;  
			return;	
	  }

   }
		
	function imageDelete() {
		
		document.frm.action = "/appForm/imageDelete.do";
		document.frm.method = "post";
		frm.submit();
	}
</SCRIPT>

<SCRIPT language="JavaScript">
function document.CNPECMWindowsWebInterface::ThumbnailClickEvent(FILE_ID)
{
	document.getElementById("fileId").value = 	FILE_ID;
}
</SCRIPT> 
