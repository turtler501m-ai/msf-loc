<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>MCP Redirect Page</title>
<link href="/resources/css/mobile/styles.css" rel="stylesheet" />
<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript">

   //fn_showLoading("처리 중 입니다.");
window.addEventListener("load", function(e) {
    var successMsg		= '${responseSuccessDto.successMsg}';
    var redirectUrl		= '${responseSuccessDto.redirectUrl}';

    //alert("successMsg:"+successMsg);
    //alert("redirectUrl:"+redirectUrl);
    
    var paraArray = [
        ["ncn","${MyPageSearchDto.ncn}"],
	    ["prodCtgId","${CommonSearchDto.prodCtgId}"],
	    ["makrCd","${CommonSearchDto.makrCd}"],
	    ["listOrderEnum","${ListOrderEnum}"]
    ];
    
    if(successMsg != '' && successMsg != null){
       if(successMsg == 'AppLogOut'){
           if($('#phoneOs').val() == 'I'){
        	   window.webkit.messageHandlers.logOutApp.postMessage('{"returnFunc" : ""}');
           }else{
        	   window.ktmmobile.logOutApp();
           }
       }else{
	       successMsg = successMsg.replace(/(<\/br>|<br\/>|<br \/>)/g, '\r\n');
	       alert(successMsg);
           
       }
    }

    if (redirectUrl == null || redirectUrl == '') {
        if(history.length > 1){
           history.go(-1);
        }
    } else {
        //href 처리를 위한 함수호출
        var resultUrl= addURL();
    	setTimeout(function(){
        	location.href=resultUrl;
        }, 1000);
    }
    
    function addURL(){
    	var division ="?";
    	var parameter="";
    	for(var i in paraArray){
    		if(paraArray[i][1]!=""){
    			parameter+=division+paraArray[i][0]+"="+paraArray[i][1];
    			division="&";
    		}
    	}
    	return redirectUrl+parameter; 
    }  
});
</script>
</head>
<body>
    <form name="frmObj" method="get" action="${responseSuccessDto.redirectUrl}" >
        <input type="hidden" name="ncn" value="${MyPageSearchDto.ncn}"/>
        <input type="hidden" name="prodCtgId" value="${CommonSearchDto.prodCtgId}"/>
        <input type="hidden" name="makrCd" value="${CommonSearchDto.makrCd}"/>
        <input type="hidden" name="listOrderEnum" value="${ListOrderEnum}"/>
    </form>
    <input type="hidden" id="phoneOs" value="${nmcp:getPhoneOs()}"/>
    <script type="text/javascript" src="/resources/js/mobile/ktm.ui.js"></script>
	<script type="text/javascript" src="/resources/js/nmcpCommon.js"></script>
	<script type="text/javascript" src="/resources/js/mobile/nmcpCommonComponent.js"></script>
	<script>
	  let MCP = new NmcpCommonComponent();
	</script>
</body>
</html>


