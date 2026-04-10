<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes,maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<meta http-equiv="Expires" content="-1">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="No-Cache">
	<link href="/resources/css/mobile/styles.css" rel="stylesheet" />
	<script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>
	<script type="text/javascript">

		window.addEventListener("load", function(e) {
			if('${resultCd}' != '0000'){
				if('${msg}' != ''){
					if('${resultCd}' == '-6'){
						if(opener != undefined){
							opener.aleadySns('${msg}', '${returnUrl}');
						}else{
							aleadySns('${msg}', '${returnUrl}');
						}
						self.close();
					}else{
						MCP.alert('${msg}', function (){
							if(opener != undefined){
								opener.location.href = '${returnUrl}';
							}else{
								location.href = '${returnUrl}';
							}
							self.close();
						});
					}

				}else{
					if(opener != undefined){
						opener.location.href = '${returnUrl}';
					}else{
						location.href = '${returnUrl}';
					}
					self.close();
				}
			}else{
				if(opener != undefined){
					if($(opener.document).find('#platFormCd').val() == 'A' && $(opener.document).find('#uuid').val() != '' && opener.document.location.pathname == '/m/loginForm.do'){
						var varData = ajaxCommon.getSerializedData({
							uuid : $(opener.document).find('#uuid').val()
						});

						ajaxCommon.getItem({
									id:'getAppUuidInfoSns'
									,cache:false
									,url:'/m/app/getUuidAppInfoSns.do'
									,data: varData
									,dataType:"json"
									,async:false
								}
								,function(json){
									if($(opener.document).find('#phoneOs').val() == 'I'){
										window.webkit.messageHandlers.onLoginResult.postMessage(JSON.stringify(json));
									}else if($(opener.document).find('#phoneOs').val() == 'A'){
										window.ktmmobile.onLoginResult(JSON.stringify(json));
									}
									setTimeout(function() {}, 200);
									opener.location.href = '${returnUrl}';
								});
					}else{
						opener.location.href = '${returnUrl}';
					}
				}else{
					location.href = '${returnUrl}';
				}
				self.close();
			}
		});
		var aleadySns = function (msg, rtnUrl){
			MCP.alert(msg, function (){
				location.href = rtnUrl;
			});
		}
	</script>
	<script type="text/javascript" src="/resources/js/mobile/ktm.ui.js"></script>
	<script type="text/javascript" src="/resources/js/nmcpCommon.js"></script>
	<script type="text/javascript"
			src="/resources/js/mobile/nmcpCommonComponent.js"></script>
	<script>
		let MCP = new NmcpCommonComponent();
	</script>
	<title>kt M모바일</title>
</head>
<body>
</body>
</html>