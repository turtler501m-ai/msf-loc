<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link href="/resources/css/portal/styles.css" rel="stylesheet" />
	<script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/resources/js/jqueryCommon.js"></script>
	<script type="text/javascript">
		var dummySessionChk = function (url){
			var goUrl = url;
			var varData = {};

			ajaxCommon.getItem({
		        id:'getDummySession'
		        ,cache:false
		        ,url:'/getDummySession.do'
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
					    	if(goUrl == '/mypage/userInfoView.do'){
								location.href = goUrl;
					    	}else{
								location.href='${pwRedirectUrl}';
						    }
						}
					}
				} else {
					MCP.alert('유효하지 않은 접근입니다.', function (){
						location.href = '/loginForm.do';
					});
				}
		    });
		}	

		var updatePwChgInfoNoShowAjax = function (){
			var varData = {};
			
			ajaxCommon.getItem({
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
						location.href='/main.do';
					}
				}else if(data.resultCd == '-1'){
					location.href = '/loginForm.do';
				}else{
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
<div class="c-modal c-modal--md" id="pw-change-dialog" role="dialog" aria-labelledby="#term-notice-modal__title">
	<div class="c-modal__dialog" role="document">
		<div class="c-modal__content">
			<div class="c-modal__header">
				<h2 class="c-modal__title" id="find-cvs-modal__title">비밀번호 변경안내</h2>
				<!--<button class="c-button" data-dialog-close onclick="location.href='/main.do';">
					<i class="c-icon c-icon--close" aria-hidden="true"></i> 
					<span class="c-hidden">팝업닫기</span>
				</button>-->
			</div>
			<div class="c-modal__body">
				<div class="complete u-mb--64 u-ta-center">
					<i class="c-icon c-icon--lock" aria-hidden="true"></i> 
					<strong class="c-text c-text--fs24 u-block u-mt--32">고객님께서는 <b>3개월동안</b> <br>비밀번호를 변경하지 않으셨습니다!
					</strong>
					<p class="c-text c-text--fs20 u-co-gray u-mt--16">
						개인정보 보호를 위해 3개월마다 <br>비밀번호를 변경해주세요.
					</p>
				</div>
				<div class="c-button-wrap">
					<a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:void(0);" onclick="dummySessionChk('/main.do');" title="메인화면으로 이동">나중에 변경하기</a> 
					<a class="c-button c-button--lg c-button--primary u-width--220" href="javascript:void(0);" onclick="dummySessionChk('/mypage/userInfoView.do');" title="정보수정 페이지로 이동">지금 변경하기</a>
				</div>
				<div class="u-mt--40 u-ta-center">
					<a class="c-button c-button--underline c-button--fs15"  href="javascript:void(0);" onclick="dummySessionChk('updatePwChgInfoNoShowAjax');" title="메인화면으로 이동">2주동안 안보이기</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="/resources/js/portal/ktm.ui.js"></script>
<script type="text/javascript" src="/resources/js/nmcpCommon.js"></script>
<script type="text/javascript" src="/resources/js/portal/nmcpCommonComponent.js"></script>
<script>
  let MCP = new NmcpCommonComponent();
  
</script>