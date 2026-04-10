<%@ page language = "java" contentType = "text/html;charset=UTF-8" %>




<script language=javascript>

var openwin;

function pay(frm)
{
	// MakePayMessage()를 호출함으로써 플러그인이 화면에 나타나며, Hidden Field
	// 에 값들이 채워지게 됩니다. 일반적인 경우, 플러그인은 결제처리를 직접하는 것이
	// 아니라, 중요한 정보를 암호화 하여 Hidden Field의 값들을 채우고 종료하며,
	// 다음 페이지인 INIsecureresult.php로 데이터가 포스트 되어 결제 처리됨을 유의하시기 바랍니다.

	if(document.ini.clickcontrol.value == "enable")
	{
		
		if(document.ini.goodname.value == "")  // 필수항목 체크 (상품명, 상품가격, 구매자명, 구매자 이메일주소, 구매자 전화번호)
		{
			alert("상품명이 빠졌습니다. 필수항목입니다.");
			fn_hideLoading();
			return false;
		}
		else if(document.ini.buyername.value == "")
		{
			alert("구매자명이 빠졌습니다. 필수항목입니다.");
			fn_hideLoading();
			return false;
		} 
		else if(document.ini.buyeremail.value == "")
		{
			alert("구매자 이메일주소가 빠졌습니다. 필수항목입니다.");
			fn_hideLoading();
			return false;
		}
		else if(document.ini.buyertel.value == "")
		{
			alert("구매자 전화번호가 빠졌습니다. 필수항목입니다.");
			fn_hideLoading();
			return false;
		}
		else if(ini_IsInstalledPlugin() == false)  // 플러그인 설치유무 체크
		{
			alert("\n이니페이 플러그인 128이 설치되지 않았습니다. \n\n안전한 결제를 위하여 이니페이 플러그인 128의 설치가 필요합니다. \n\n다시 설치하시려면 Ctrl + F5키를 누르시거나 메뉴의 [보기/새로고침]을 선택하여 주십시오.");
			fn_hideLoading();
			return false;
		}
		else
		{
			/******
			 * 플러그인이 참조하는 각종 결제옵션을 이곳에서 수행할 수 있습니다.
			 * (자바스크립트를 이용한 동적 옵션처리)
			 */
			 
						 
			if (MakePayMessage(frm))
			{
				disable_click();
				goOrder();
				return true;
			}
			else
			{
				fn_hideLoading();
				if(IsPluginModule()) {
					alert("결제를 취소하셨습니다.");
				}
				return false;
			}
		}
	}
	else
	{
		return false;
	}
}


function enable_click()
{
	document.ini.clickcontrol.value = "enable"
	pay(document.ini);
}

function disable_click()
{
	document.ini.clickcontrol.value = "disable"
}

function focus_control()
{
	if(document.ini.clickcontrol.value == "disable")
		openwin.focus();
}

$(document).ready(function() {
	enable_click();
    //focus_control();
});
</script>


<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  if (restore) selObj.selectedIndex=0;
}
//-->
</script>
</head>
<form name=ini method=post action="/inicis/inicisResult.do" > 
<input type="hidden" name="gopaymethod" value="${iniDto.gopaymethod}">
<input type=hidden name=goodname value="${iniDto.goodname}">
<input type=hidden name=buyername size=20 value="${iniDto.buyername}">
<input type=hidden name=buyeremail size=20 value="${iniDto.buyeremail}">
<input type=hidden name=buyertel size=20 value="${iniDto.buyertel}">
<input type="hidden" name="rn" value="${iniDto.rn}">
<input type="hidden" name="price" value="${iniDto.price}"> 
<input type="hidden" name="enctype" value="${iniDto.enctype}"> 

<input type=hidden name=acceptmethod value="SKIN(ORIGINAL):HPP(1)">
<input type=hidden name=currency value="WON">
<input type=hidden name=oid size=40 value="${iniDto.oid}">

<input type="hidden" name="ini_encfield" value="${iniDto.encfield}">
<input type="hidden" name="ini_certid" value="${iniDto.certid}">
<input type="hidden" name="quotainterest" value="">
<input type="hidden" name="paymethod" value=""> 
<input type="hidden" name="cardcode" value="">
<input type="hidden" name="cardquota" value="">
<input type="hidden" name="rbankcode" value="">
<input type="hidden" name="reqsign" value="DONE">
<input type="hidden" name="encrypted" value="">
<input type="hidden" name="sessionkey" value="">
<input type="hidden" name="uid" value=""> 
<input type="hidden" name="sid" value="">
<input type="hidden" name="version" value=5000>
<input type="hidden" name="clickcontrol" value="">
</form>
</body>
</html>

