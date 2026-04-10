
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<script src="/resources/company/common.js"></script>
<script type="text/javascript">
    $(document).ready(function(){

    	$("#regularCertBtn").removeAttr("onClick");
    	$("#regularCertBtn").attr("onClick","newRegularCertBtn();");

    }); 

	function newRegularCertBtn(){
		var certify = $("#certify").val();
    	if(certify!="Y"){
    		MCP.alert("인증번호를 받으세요.");
    		$("#certifyCallBtn").focus();
    		return false;
    	}

    	var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
    	var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
    	if(certifySms==""){
    		MCP.alert("인증번호를 입력해 주세요");
    		return false;
    	}

    	var birthDay = $("#inpBirthDate").val().replaceAll("-", "");

    	var timer = $("#timer").text();
    	if(timer == ""){
    		MCP.alert("입력시간이 초과되었습니다. 인증번호를 다시 받아주세요.");
    		return false;
    	}
    	var varData = ajaxCommon.getSerializedData({
    		certifySms:$.trim(certifySms)
    		,phone:$.trim(phoneNum)
    		,menuType : 'simple'
    		,svcCntrNo : $("#svcCntrNo").val()
    		,name:$("#custNm").val()
    		,mobileNo:$.trim(phoneNum)
    		,birthday: birthDay
        });


    	ajaxCommon.getItem({
    	    id:'checkCertSmsAjax'
    	    ,cache:false
    	    ,url:'/m/sms/checkCertSmsAjax.do'
    	    ,data: varData
    	    ,dataType:"json"
    	},function(data){

    		if(data.returnCode == "00"){
    				MCP.alert("인증이 완료되었습니다.");
    				certifySucess = 'Y';
        			$("#certifyYn").val("Y");
    				$("#combiChkYn").val("N");
        			$("#timeover").hide();
    				$("#countdown").hide();
    				$("#countdownTime").hide();
    				$("#countdown1").hide();

    				$("#inpBirthDate").prop('disabled',true);
    				$("#certifySms").prop('readonly',true);
    				$("#custNm").prop('readonly',true);
    				$("#phoneNum").prop('readonly',true);
    				$("#id_cfrm_btn").prop('disabled',false);
    				
        			clearInterval(interval);
    			} else{
    				alert(data.message);
    			}
    	}
      );

    }   

    function authCfrm(){

    	var inpBirthDate = ajaxCommon.isNullNvl($("#inpBirthDate").val(),"");
		if(inpBirthDate==""){
			MCP.alert("생년월일을 입력해주세요.",function(){
				$("#inpBirthDate").focus()
			});
			return false;
		}


		var now = new Date(+new Date() + 3240 * 10000).toISOString().split("T")[0];
		if(inpBirthDate > now){
			MCP.alert("사용하실 수 없는 생년월일을 입력하셨습니다. ",function(){
				$("#inpBirthDate").focus();
			});
			return false;
		}

		var phoneNumStatus = $("#phoneNum").prop("readonly");
    	if(phoneNumStatus === false) {
    		MCP.alert("간편조회 인증을 받으시지 않으셨습니다. ");
    		return false;
     	}
    	
    	$("#cfrmYn").val('Y');
		
		$("#certSmsNomemberView").hide();
		$("#simpleMyInfoView").show();
		
		//
		var phoneNum = $("#phoneNum").val();
		
		ajaxCommon.getItem({
			id: 'myPointInfoAjax',
			url: '/m/simple/myPointInfoAjax.do',
			type: 'post',
			data: '',
			dataType: "json",
			async: false
	    }, function(resp) {
	    	myPointCallback(resp);
	    });
		
		function myPointCallback(resp){
	    	var myPoint = "0P";
	    	var simpleName = "";
	    	var simpleTelNo = "";
	    	var formatedDate = "";
			if(resp.result == "00000"){
				myPoint = numberComma(Number(resp.custPoint.totRemainPoint))+"P";
			}
			simpleName = resp.mcpUserCntrMngDto.subLinkName;
			simpleTelNo = resp.mcpUserCntrMngDto.cntrMobileNo;
			formatedDate = resp.formatedNow;

			$('#remainPoint').html(myPoint);
			$('#simpleName').html(simpleName);
			$('#simpleTelNo').html(simpleTelNo);
			$('#formatedDate').html(formatedDate + " 기준");
		}
		function numberComma(comma) {
	        return comma.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	    }

		ajaxCommon.getItem({
			id: 'couponListAjax',
			url: '/m/simple/couponListAjax.do',
			type: 'GET',
			data: '',
			dataType: "text",
			async: false
	    }, function(resp) {
	    	this._respValue = resp;
			$('#contents').append(resp);
	    });
		ajaxCommon.getItem({
			id: 'myGiftListAjax',
			url: '/m/simple/myGiftList.do',
			type: 'GET',
			data: '',
			dataType: "text",
			async: false
	    }, function(resp) {
	    	this._respValue = resp;
			$('#giftContents').append(resp);
	    });
    }
    </script>
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="easy-check1-title">간편조회</h1>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
								class="c-hidden">팝업닫기</span>
						</button>
					</div>
 					<div class="c-modal__body" id="certSmsNomemberView">
						<p class="c-text c-text--type2 u-mt--32">kt M모바일에서는 간편조회를 통해
							포인트, 쿠폰, 신청 사은품 현황을 확인할 수 있습니다.</p>
						<p class="c-bullet c-bullet--dot u-co-gray">생년월일과 휴대폰 인증으로 확인이
							가능합니다.</p>
						<div class="c-form u-mt--8">
							<span class="c-form__title" id="inpG">생년월일</span>
							<div class="c-input-wrap u-mt--0">
								<!---->
								<!-- [2022-02-16] input type 및 title 변경 (date -> text)  -->
								<input class="c-input date-input" type="text" id="inpBirthDate"
									placeholder="YYYY.MM.DD" title="생년월일 입력" readonly>
								<button class="c-button c-button--calendar">
									<i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
								</button>
							</div>
						</div>
						<div id="divJoinArea" class="c-form u-mt--8"
							>
							<%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
						</div>
						<div class="c-button-wrap">
							<button id="id_cfrm_btn" class="c-button c-button--full c-button--primary" onclick="authCfrm();" disabled>간편조회</button>
						</div>
					</div>



					<div class="c-modal__body"style="display: none;" id="simpleMyInfoView">
						<h3 class="c-heading c-heading--type7 u-mt--32">
							<b><span id="simpleName">simpleName</span>(<span id="simpleTelNo">simpleTelNo</span>)</b>
							<span class="c-text c-text--type2">님의</span>
						</h3>
						<p class="c-text c-text--type2 u-mt--24">포인트·쿠폰 현황과
            				<br>사은품 신청 내역입니다.</p>
						<p class="c-bullet c-bullet--dot u-co-gray">
							포인트와 쿠폰은 &nbsp;<a class="c-text u-td-underline" href="/m/mypage/myPointView.do">로그인</a>&nbsp;
							후 사용하실 수 있습니다.
						</p>
						<div class="c-item u-mt--40">
							<div class="c-item__title flex-type--between">
								가입정보<span class="c-text c-text--type4 u-co-gray-7" id="formatedDate">formatedNow 기준</span>
							</div>
							<div class="banner-balloon u-mt--12">
								<p>
									<span class="c-text c-text--type5">My 포인트</span> <br>
									<b class="c-text c-text--type7" id="remainPoint">myPoint</b>
									<img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_05.png" alt="">
								</p>
							</div>
						</div>
						<h3 class="c-heading c-heading--type2 u-mt--64 u-mb--12">사용 가능 쿠폰</h3>

						<div id="contents"></div>

						<div class="c-table c-table--plr-8 u-mt--40" id="giftContents">
							
						</div>
						
						<hr class="c-hr c-hr--type3 u-mt--24">
						<p class="c-bullet c-bullet--dot u-co-gray u-mt--24">간편조회는
							인증하신 회선에 대한 정보입니다.</p>
						<div class="c-button-wrap">
							<button class="c-button c-button--full c-button--gray" data-dialog-close>닫기</button>
							<a class="c-button c-button--full c-button--primary" href="/m/mypage/myPointView.do">포인트·쿠폰 사용</a>
						</div>
					</div>



				</div>

