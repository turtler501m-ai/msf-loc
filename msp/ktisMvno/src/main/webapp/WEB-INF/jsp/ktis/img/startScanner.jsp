<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<HTML>
<HEAD>
<TITLE>▒▒▒▒▒▒ 이미지 인터페이스 ▒▒▒▒▒▒</TITLE>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">

/* Java Script StringBuffer Definition */
var StringBuffer = function()
{
    this.buffer = new Array();
}

StringBuffer.prototype.append = function(obj)
{
     this.buffer.push(obj);
}

StringBuffer.prototype.toString = function()
{
     return this.buffer.join("");
}

window.onload = function()
{


	var installResult = false; //이미지 모듈이 제대로 설치되었는지 여부
	var version = "${scanInfoVo.version}";
	var CNPECMWindowsWebInterface;
	var installVersion = version;
	var nowVersion = "";

	/* [1] 버전 확인(버전 상이 or 미설치인 경우 설치 화면으로 이동) */
	try
	{
		  var sbObject = new StringBuffer();
	    sbObject.append("<object id='CNPECMWindowsWebInterface'");
	    sbObject.append("classid='clsid:EE70C460-5BA1-4BE5-ACC5-CEC481B43FF6' width='0%'");
	    sbObject.append("height='0%'>");
	    sbObject.append("</object>");

	    document.all['activeXArea'].innerHTML = sbObject.toString();

	    CNPECMWindowsWebInterface =  document.getElementById("CNPECMWindowsWebInterface");

	    //버전 확인
	    nowVersion = CNPECMWindowsWebInterface.GetVersion();

	    //if(installVersion == "undefined" || installVersion == null )
	    //{
	    //	installVersion = "";
	    //}

	    if(nowVersion == installVersion)
	    {
	    	installResult = true;
	    }
	}
	catch(e)
	{
		installResult = false;

		var errorMSG = new StringBuffer();
		var errorCode = e.number & 0xFFF;

		errorMSG.append("Error Message: " + e.message);
		errorMSG.append("\n");
		errorMSG.append("Error Code: " + errorCode);

		if(errorCode == 438)
		{
			//alert("본 서버를 신뢰할 수 있는 사이트로 브라우저에 등록하십시오!");
		}
		else
		{
			alert(errorMSG.toString());
		}
	}

	if(!installResult)
	{
		alert("설치 프로그램을 다운로드하여 프로그램을 설치하십시오!");
		//var url = "http://scn.ktmmobile.com/webscan/ui/downloadInstallProgram.jsp";
		var url = "${scanInfoVo.downloadUrl}";
		window.location.href = url;  
		//window.location.href = "./installProgram.exe"
		//self.close();
		return;
	}

	/* [2] ActiveX 정보 설정 */
	/* var serverIP = "http://scndev.ktmmobile.com"; // https://scndev.ktmmobile.com // 이미지 서버 IP or Domain Name
	var serverPort = "80"; // 443 // 이미지 서버 접속 Port

	var userID = "user01"; //사용자 아이디
	var userNM = "홍길동"; //사용자 이름
	var orgType = "D"; //조직 유형 - S:판매점, D:대리점, K:개통센터
	var orgOid = "EMD0003"; //소속 조직 아이디
	var orgNM = "신촌판매점"; //소속 조직명 */

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
	

//	var serverIP = "https://scndev.ktmmobile.com"; // https://scndev.ktmmobile.com // 이미지 서버 IP or Domain Name
//	var serverPort = "";


	var userID = "${scanInfoVo.userId}";//사용자 아이디
	var userNM = "${scanInfoVo.userNm}"; //사용자 이름
	var orgType = "${scanInfoVo.orgType}"; //조직 유형 - S:판매점, D:대리점, K:개통센터
	var orgOid = "${scanInfoVo.orgOid}"; //소속 조직 아이디
	var orgNM = "${scanInfoVo.orgNm}"; //소속 조직명

	var scanId = "${scanInfoVo.scanId}";
	
	var resNo = "${scanInfoVo.resNo}";
	var RegUserName = "${scanInfoVo.regUserName}";
	
	//alert("name : " + RegUserName + "  " + "scanId: " + scanId);
	
  //이미지 서버 정보 설정
  CNPECMWindowsWebInterface.SetServer(serverIP); // 이미지 서버 IP or Domain Name
  CNPECMWindowsWebInterface.SetPort(serverPort); // 이미지 서버 접속 Port

  //사용자 및 소속 조직 정보 설정
  CNPECMWindowsWebInterface.SetUserId(userID); //사용자 아이디
  CNPECMWindowsWebInterface.SetUserName(userNM); //사용자 이름

  CNPECMWindowsWebInterface.SetOrganizationType(orgType); //조직 유형 - S:판매점, D:대리점, K:개통센터
  CNPECMWindowsWebInterface.SetOrganizationId(orgOid); //소속 조직 아이디
  CNPECMWindowsWebInterface.SetOrganizationName(orgNM); //소속 조직명

  CNPECMWindowsWebInterface.SetRES_NO(resNo); //예약번호 설정
  CNPECMWindowsWebInterface.SetSCAN_ID(scanId); //scan ID -> 생성해서 보내주는 데이터
  CNPECMWindowsWebInterface.SetCUST_NM(RegUserName);
  
  /* [3] 스캔 프로그램 실행 */
  CNPECMWindowsWebInterface.ExecuteProgram();
  window.close();
};

</SCRIPT>
</HEAD>

<BODY>
<div id="activeXArea"></div>
</BODY>
</HTML>
