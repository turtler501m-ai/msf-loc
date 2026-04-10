<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<t:insertDefinition name="mlayoutDefault">   
	<t:putAttribute name="scriptHeaderAttr">
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.placeholder.js"></script>
		
		<script type="text/javascript">
			window.onpageshow = function (evt){
				if(evt.persisted || window.performance.navigation.type == 2){
					location.href = '/m/loginForm.do';
					/*
			    	ajaxCommon.createForm({
			            method:'post'
			            ,action:'/loginForm.do'
			         });
			        ajaxCommon.formSubmit();
			        */
					
			    }
			}
		
		    $(document).ready(function () {
		
		        $('input, text').placeholder();
	
				$(".btn_complete").on("click", function() {
					location.href="${pageContext.request.contextPath}/m/loginForm.do";
				});
				
				if($('#aleadyMbrYn').val() == 'Y'){
					$('.case1').hide();
					$('.case2').show();
					$('.case3').show();
				}else{
					$('.case1').show();
					$('.case2').hide();
					$('.case3').show();
					if($('#mbrSnsAddYn').val() == 'Y'){
						$('.case1_1').show();
						$('.case1_2').show();
					}else{
						$('.case1_1').show();
						$('.case1_2').hide();
						
					}
				}
				
				$('#snsAddBtn').on('click', function (){
					if($.trim($('#snsCd').val()) != '' && $.trim($('#snsId').val())){
						var varData = ajaxCommon.getSerializedData({
						   snsCd : $('#snsCd').val()
						   , snsId : $('#snsId').val()
						   , encUserId : $('#encUserId').val()
						   , name : $('#userName').val()
						   , clausePriAdFlag : $('#clausePriAdFlag').val()
					    });
						
						ajaxCommon.getItem({
					        id:'snsAdd'
					        ,cache:false
					        ,url:'/m/join/snsAdd.do'
					        ,data: varData
					        ,dataType:"json"
					    }
					    ,function(jsonObj){
					    	var resultCode = jsonObj.resltCd;
					    	if(resultCode=="0000"){
					    		$('.case1').show();
								$('.case2').hide();
								$('.case3').show();
								$('.case1_1').hide();
								$('.case1_2').show();
					    	} else {
					    		MCP.alert("처리중 오류가 발생하였습니다. 잠시후 다시 이용해주세요.");
					    		return false;
					    	}
					    });
						
					}else{
						MCP.alert('유효하지 않은 접근입니다. SNS 인증을 다시 받아주세요.');
						location.href = '/m/loginForm.do';
					}
				});
				if($("#regularYn").val() == 'Y'){
					MCP.alert("고객님은 정회원으로 확인되었습니다.");
				}
		    });
		</script>
	    <!-- 카카오 광고 분석  -->
	    <script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
	    <script type="text/javascript">
	          kakaoPixel('5981604067138488988').pageView();
	          kakaoPixel('5981604067138488988').signUp();
	    </script>
	    <!-- 카카오 광고 분석 end-->
	</t:putAttribute>
	<t:putAttribute name="contentAttr">
	
		<div class="ly-content" id="main-content">
			<input type="hidden" name="mbrSnsAddYn" id="mbrSnsAddYn" value="${mbrSnsAddYn}" /> 
			<input type="hidden" name="clausePriAdFlag" id="clausePriAdFlag" value="${getIpinInfo.clausePriAdFlag}" /> 
			<input type="hidden" name="encUserId" id="encUserId" value="${getIpinInfo.encUserId}" />
			<input type="hidden" name="snsCd" id="snsCd" value="${getIpinInfo.snsCd}" /> 
			<input type="hidden" name="snsId" id="snsId" value="${getIpinInfo.snsId}" /> 
			<input type="hidden" name="userName" id="userName" value="${getIpinInfo.name}" /> 
			<input type="hidden" name="aleadyMbrYn" id="aleadyMbrYn" value="${aleadyMbrYn}" />
			<input type="hidden" name="regularYn" id="regularYn" value="${regularYn}" />
	
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					회원가입
					<button class="header-clone__gnb"></button>
				</h2>
				<div class="c-indicator">
					<span style="width: 100%"></span>
				</div>
			</div>
			<!-- case1 : 가입완료-->
			<div class="complete case1" style="display: none;">
				<div class="c-icon c-icon--complete" aria-hidden="true">
					<span></span>
				</div>
				<p class="complete__heading">
					<b>${getIpinInfo.name}님!</b>
				</p>
				<!-- 일반회원 가입완료-->
				<p class="complete__text case1_1">kt M모바일 회원가입이 완료되었습니다.</p>
				<!-- sns회원 가입완료-->
				<p class="complete__text case1_2">SNS 아이디 추가가 완료되었습니다.</p>
				<img src="/resources/images/mobile/content/img_sign_complete_01.png" alt="">
				<p class="complete__sub">국내 1위 알뜰폰 kt M모바일의 요금제를 <br>지금 바로 만나보세요!</p>
			</div>
			<!-- //-->
			<!-- case2 : 소셜 아이디 연동-->
			<div class="complete case2" style="display: none;">
				<div class="c-icon c-icon--notice" aria-hidden="true">
					<span></span>
				</div>
				<p class="complete__heading">
					<b>${getIpinInfo.name}님!</b>
				</p>
				<p class="complete__text">이미 가입된 아이디가 있습니다.</p>
				<img src="/resources/images/mobile/content/img_sign_complete_02.png" alt="">
				<p class="complete__heading">
					<b>[${getIpinInfo.userId}]</b>
				</p>
				<p class="complete__sub">@${getIpinInfo.snsCd}로 연결하여 간편하게 로그인하세요.</p>
			</div>
			<!-- //-->
			<div class="c-button-wrap c-button-wrap--column case3">
				<c:choose>
					<c:when test="${getIpinInfo.snsCd eq 'NAVER'}">
						<a class="c-button c-button--full c-button--naver login-sns case2" href="javascript:void(0);" id="snsAddBtn">
							<i class="c-icon c-icon--naver-login" aria-hidden="true"></i> <span>네이버로 계속하기</span>
						</a> 
					</c:when>
					<c:when test="${getIpinInfo.snsCd eq 'FACEBOOK'}">
						<a class="c-button c-button--full c-button--facebook login-sns case2" href="javascript:void(0);" id="snsAddBtn"> 
							<i class="c-icon c-icon--facebook-login" aria-hidden="true"></i> <span>페이스북으로 계속하기</span>
						</a> 
					</c:when>
					<c:when test="${getIpinInfo.snsCd eq 'KAKAO'}">
						<a class="c-button c-button--full c-button--kakao login-sns case2" href="javascript:void(0);" id="snsAddBtn"> 
							<i class="c-icon c-icon--kakao-login" aria-hidden="true"></i> <span>카카오로 계속하기</span>
						</a> 
					</c:when>
					<c:otherwise>
						<a class="c-button c-button--full c-button--apple login-sns case2" href="javascript:void(0);" id="snsAddBtn"> 
							<i class="c-icon c-icon--apple-login" aria-hidden="true"></i> <span>Apple로 계속하기</span>
						</a> 
					</c:otherwise>
				</c:choose>
				<a class="c-button c-button--full c-button--primary u-mt--32" href="/m/loginForm.do">로그인</a>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>
