<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script src="/resources/js/portal/vendor/flatpickr.min.js"></script>
<script src="/resources/company/common.js"></script>
<script>
	var today = new Date();
	KTM.datePicker('.flatpickr-input', {
		dateFormat: 'Y.m.d',
		maxDate: today,
	});
</script>
<script type="text/javascript">
	$(document).ready(function (){


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

    	var birthDay = $("#inpBirthDate").val().replaceAll(".", "");
    	
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
    	    ,url:'/sms/checkCertSmsAjax.do'
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
    	}//birthDay
      );

    }   

    function targetTermsAgree(){
    	$("#chkAgree").prop("checked",true);    	
    }

    function authCfrm(){
    	
		var inpBirthDate = ajaxCommon.isNullNvl($("#inpBirthDate").val(),"");
		if(inpBirthDate==""){
			MCP.alert("생년월일을 입력해주세요.",function(){
				$("#inpBirthDate").focus()
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
		$("#searchBtn").hide();
		$("#simpleMyInfoView").show();
		$("#useBtn").show();

		ajaxCommon.getItem({
	        id : 'myPointInfoAjax',
			url: '/simple/myPointInfoAjax.do',
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
	    	var birthday = "";

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
			url: '/simple/couponListAjax.do',
			type: 'GET',
			data: '',
			dataType: "text",
			async: false
	    }, function(resp) {
	    	this._respValue = resp;
			$('#couponContents').append(resp);
	    });

		ajaxCommon.getItem({
			id: 'myGiftListAjax',
			url: '/simple/myGiftList.do',
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
						<h2 class="c-modal__title" id="find-cvs-modal__title">간편조회</h2>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
								class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body" id="certSmsNomemberView">
						<p class="c-text c-text--fs20">kt M모바일에서는 간편조회를 통해 포인트, 쿠폰, 신청
							사은품 현황을 확인할 수 있습니다.</p>
						<p class="c-text c-text--fs17 u-co-gray u-mt--12">비대면 가입신청서 작성
							시 본인인증과 납부정보 확인이 진행됩니다.</p>
						<div class="c-form-wrap">
							<div class="c-form-group" role="group"
								aria-labelledby="inpSimpleInquiryHead">
								<h3 class="c-group--head c-hidden" id="inpSimpleInquiryHead">간편조회</h3>
								<div class="c-form-field c-form-field--datepicker u-mt--48">
									<div class="c-form__input">
										<input class="c-input flatpickr-input" id="inpBirthDate"
											type="text" placeholder="YYYY.MM.DD" readonly> <label
											class="c-form__label" for="inpBirthDate">생년월일</label> <span
											class="c-button c-button--icon"> <span
											class="c-hidden">날짜선택</span> <i
											class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
										</span>
									</div>
								</div>
								<%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
							</div>
						</div>
					</div>
					<div class="c-modal__footer" id="searchBtn">
						<div class="c-button-wrap">
							<button id="id_cfrm_btn" class="c-button c-button--lg c-button--primary u-width--460" onclick="authCfrm();" disabled>간편조회</button>
						</div>
					</div>
					<div class="c-modal__body" style="display: none;" id="simpleMyInfoView">


			<p class="c-text c-text--fs20">
            <b><span id="simpleName">simpleName</span>(<span id="simpleTelNo"></span>)</b>님의
            <br>포인트·쿠폰 현황과 사은품 신청 내역입니다.
          </p>
          <p class="c-bullet c-bullet--dot u-co-gray"> 포인트와 쿠폰은 &nbsp;<a class="c-text u-td-underline" href="/mypage/myPointView.do">로그인</a>&nbsp; 후 사용하실 수 있습니다.</p>
          <div class="u-flex-between u-mt--48">
            <h3 class="c-heading c-heading--fs20 c-heading--regular">보유 포인트</h3>
            <span class="c-text c-text--fs13 u-co-gray" id="formatedDate">formatedDate</span>
          </div>
          <div class="point-box">
            <span class="point-box__title">
              <!-- [2022-02-08] 아이콘 변경-->
              <i class="c-icon c-icon--my-point" aria-hidden="true"></i>My 포인트
            </span>
            <span class="point-box__text u-co-sub-4" id="remainPoint">myPoint</span>
          </div>
          <h4 class="c-heading c-heading--fs16">사용 가능 쿠폰</h4>
          <div id="couponContents">
          
          </div>
          
          <h4 class="c-heading c-heading--fs16 u-mt--48">사은품 신청 내역</h4>
          <div class="c-table u-mt--16" id="giftContents">
          
          </div>
          <div class="c-button-wrap">
            <button class="c-button c-button--more" style="display: none;">
              <i class="c-icon c-icon--more" aria-hidden="true"></i>
              <span>더보기</span>
              <span class="c-button__sub">
                <span class="c-hidden">현재 노출된 항목</span>10
              </span>
              <span class="c-button__sub">
                <span class="c-hidden">전체 항목</span>20
              </span>
            </button>
          </div>
          <hr class="c-hr c-hr--basic u-mt--56">
          <p class="c-bullet c-bullet--dot u-co-gray u-mt--24">간편조회는 인증하신 회선에 대한 정보입니다.</p>



					</div>
					<div class="c-modal__footer"style="display: none;" id="useBtn">
						<div class="c-button-wrap">
							<button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close>닫기</button>
							<a class="c-button c-button--lg c-button--primary u-width--220" href="/mypage/myPointView.do">포인트·쿠폰 사용</a>
						</div>
					</div>
				</div>


