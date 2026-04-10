<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes,maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link href="/resources/css/mobile/styles.css" rel="stylesheet" />
	<script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>
	<script type="text/javascript">
		var dummySessionChk = function (url){
			var goUrl = url;
			var varData = {};
			KTM.LoadingSpinner.show();
			ajaxCommon.getItemNoLoading({
		        id:'getDummySession'
		        ,cache:false
		        ,url:'/m/getDummySession.do'
		        ,data: varData
		        ,dataType:"json"
		    }
		    ,function(data){
				if (data.resultCd == '0000') {
					if(goUrl == 'updatePwChgInfoNoShowAjax'){
						updatePwChgInfoNoShowAjax();
					}else{

						if('${pwRedirectUrl}' == undefined || '${pwRedirectUrl}' == ''){
							location.href = goUrl;
					    }else{
					    	if(goUrl == '/m/mypage/userInfoView.do'){
								location.href = goUrl;
					    	}else{
								location.href='${pwRedirectUrl}';
						    }
						}
					}
				} else {
					KTM.LoadingSpinner.hide(true);
					MCP.alert('유효하지 않은 접근입니다.', function (){
						location.href = '/m/loginForm.do';
					});
				}
		    });
		}

		var updatePwChgInfoNoShowAjax = function (){
			var varData = {};

			ajaxCommon.getItemNoLoading({
		        id:'updatePwChgInfoNoShowAjax'
		        ,cache:false
		        ,url:'/updatePwChgInfoNoShowAjax.do'
		        ,data: varData
		        ,dataType:"json"
		    }
		    ,function(data){
		    	if(data.resultCd == '0000'){
			    	if('${pwRedirectUrl}' != undefined && '${pwRedirectUrl}' != ''){
						location.href='${pwRedirectUrl}';
				    }else{
						location.href='/m/main.do';
					}
				}else if(data.resultCd == '-1'){
					location.href = '/m/loginForm.do';
				}else{
					KTM.LoadingSpinner.hide(true);
					MCP.alert(data.msg);
				}
		    });
		}

		window.addEventListener("load", function(e) {
			$('#upPwdBtn').trigger('click');
		});
	</script>
	<title>kt M모바일</title>
</head>

<button class="c-button c-button--full c-button--white" data-dialog-trigger="#pw-change-dialog" id="upPwdBtn" style="display:none;">비밀번호 변경안내 popup 호출</button>
<div class="c-modal" id="pw-change-dialog" role="dialog" tabindex="-1" aria-describedby="#pw-change-title">
    <div class="c-modal__dialog c-modal__dialog--full" role="document">
		<div class="c-modal__content">
			<div class="c-modal__header">
				<h1 class="c-modal__title" id="pw-change-title">비밀번호 변경안내</h1>
				<!-- <button class="c-button" data-dialog-close onclick="location.href='/m/main.do';">
					<i class="c-icon c-icon--close" aria-hidden="true"></i>
					<span class="c-hidden">팝업닫기</span>
				</button> -->
			</div>
			<div class="c-modal__body">
				<div class="complete complete--lock">
					<div class="c-icon c-icon--lock2" aria-hidden="true">
						<span></span>
					</div>
					<p class="complete__heading">
						고객님께서는 &nbsp;<b>3개월 동안</b> <br>비밀번호를 변경하지 않으셨습니다!
					</p>
					<p class="complete__text">
						개인정보 보호를 위해 3개월마다 <br>비밀번호를 변경해주세요.
					</p>
				</div>
				<div class="c-button-wrap u-mt--48">
					<a class="c-button c-button--full c-button--gray" onclick="dummySessionChk('/m/main.do');">나중에 변경하기</a>
					<a class="c-button c-button--full c-button--primary" href="javascript:void(0);" onclick="dummySessionChk('/m/mypage/userInfoView.do');">지금 변경하기</a>
				</div>
				<div class="c-button-wrap c-button-wrap--column u-mt--24">
					<a class="c-button c-button--sm c-button--underline" href="javascript:void(0);" onclick="dummySessionChk('updatePwChgInfoNoShowAjax');">2주동안 안보이기</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="/resources/js/mobile/ktm.ui.js"></script>
<script type="text/javascript" src="/resources/js/nmcpCommon.js"></script>
<script type="text/javascript" src="/resources/js/mobile/nmcpCommonComponent.js"></script>
<script>
  let MCP = new NmcpCommonComponent();

</script>