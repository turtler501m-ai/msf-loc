
var interval ;

$(document).ready(function(){

	$("#file1,#file2").change(function(){
		var iSize = 0;
		var maxSize = 2*1024*1024;
		if ($.browser.msie) {

			var objFSO = new ActiveXObject("Scripting.FileSystemObject");
			var sPath = $(this)[0].value;
			var objFile = objFSO.getFile(sPath);
			var iSize = objFile.size;
		}else {
			iSize = $(this)[0].files[0].size;
		}
		if(iSize > maxSize){
			alert("첨부파일 사이즈는 2MB 이내로 등록 가능합니다.");
			$(this).val("");
			return false;
		}

	});

	// 최초 진입할때 이벤트 url 변경
	var url = $("#eventCd option:selected").attr("url");
	$("#eventCdUrl").attr("href",url);
	// 최초 진입할때 이벤트 url 변경 끝

	$("#eventCd").change(function(){
		var url = $("#eventCd option:selected").attr("url");
		$("#eventCdUrl").attr("href",url);
	});

	clearCert();
	var pageNo = ajaxCommon.isNullNvl($("#pageNo").val(),"1");
	initreViewData(pageNo);
	// 숫자만 입력가능1
	$(".numOnly").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});

	// 숫자만 입력가능2
	$(".numOnly").blur(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});


	// 등록버튼
	$("#regBtn").click(function(){
		var reviewSbst = ajaxCommon.isNullNvl($("#reviewSbst").val(),"");
		if(reviewSbst==""){
			alert("사용 후기를 작성해 주세요.");
			$("#reviewSbst").focus();
			return false;
		}
		if( reviewSbst.replace(/ /gi, "").length < 40 ){
			alert("40자 이상작성해 주세요.");
			$("#reviewSbst").focus();
			return false;
		}

		fn_mobile_layerOpen('confrmLayer');
	});

	// 삭제버튼
	$(document).on("click", ".delBtn", function(){
		clearCert1();
		var contractNum = $(this).attr("contractNum");
		$("#contractNum").val(contractNum);
		fn_mobile_layerOpen('delLayer');
	});

	// sms 인증버튼
	$("#sms").click(function(){
		fn_mobile_layerClose('confrmLayer');
		fn_mobile_layerOpen('joinLayer');
		clearCert();
	});

	// 좋아요 나빠요
	$("#good").click(function(){
		$(this).addClass("on");
		$("#bad").removeClass("on");
		$("#commendYn").val("1");
	});

	$("#bad").click(function(){
		$(this).addClass("on");
		$("#good").removeClass("on");
		$("#commendYn").val("0");
	});

	// // 숫자만 입력가능1
	$("#reviewSbst").keyup(function(){
		var content = $(this).val();
		if(content.length > 800) {
			alert("글자수는 800자로 이내로 제한됩니다.");
			$(this).val($(this).val().substring(0, 800));
		}
        $("#reviewSbstSize").html(content.length + '/800자');
	});

	$(document).on("click",".viewMore",function(){
		var foldArea = $(this).parents(".foldArea");
		var orgnArea = foldArea.next(".orgnArea");
		foldArea.hide();
		orgnArea.show();
	});

	$(document).on("click",".viewFold",function(){
		var orgnArea = $(this).parents(".orgnArea");
		var foldArea = orgnArea.prev(".foldArea");
		foldArea.show();
		orgnArea.hide();
	});

	$("#selEventCd").change(function(){
		$("#requestReview_board").html("");
		$("#pageNo").val(1);
		initreViewData(1);
	});


	$("#certifyCallBtn").click(function(){

		var name = ajaxCommon.isNullNvl($("#name").val(),"");
		var phone = ajaxCommon.isNullNvl($("#phone").val(),"");
		var certCheck = $("input:checkbox[name=certCheck]").is(":checked");
		if(!certCheck){
			alert("개인정보 수집 및 이용에 대한 동의를 확인해 주세요.");
			return false;
		}

		if( name=="" ){
			$("#name").focus();
			alert("이름을 입력해 주세요.");
			return false;
		}

		if( phone=="" ){
			$("#phone").focus();
			alert("휴대폰 번호를 입력해 주세요.");
			return false;
		}
		$("#certifyCallBtn").text("인증번호 요청");

		var varData = ajaxCommon.getSerializedData({
			name:$.trim(name)
			,phone:$.trim(phone)
	    });

	    ajaxCommon.getItem({
	        id:'getAuthSms'
	        ,cache:false
	        ,url:'/requestReView/userSmsAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){
	    	if (AJAX_RESULT_CODE.SUCCESS) {
	    		if( data.RESULT_CODE == "00000" ){

	    			alert("해당 번호로 인증번호를 발송하였습니다.");
					$("#countdown").css("display","");
					$("#certifyCallBtn").text("인증번호 재전송");
					$(".btn_intable").css("color","#333");
					$("#certify").val("Y");

					// 배포전 꼭 삭제하기
					var authNum = data.authNum;
					$("#certifySms").val(authNum);
					// 배포전 꼭 삭제하기

	    		} else if( data.RESULT_CODE == "0001" ){
	    			alert("인자값 오류가 발생 하였습니다.");
	    			clearCert();
	    			return false;

	    		} else if( data.RESULT_CODE == "0002" ){
	    			alert("현재 kt M모바일을 사용중인 고객만 사용후기 작성이 가능합니다.");
	    			clearCert();
	    			return false;

	    		} else if( data.RESULT_CODE == "0003" ){
	    			alert("선불요금제 사용자는 사용후기 작성을 하실 수 없습니다.");
	    			clearCert();
	    			return false;

	    		} else if( data.RESULT_CODE == "0004" ){
	    			alert("직영대리점을 통해 개통하신 고객만 사용후기 작성이 가능합니다.");
	    			clearCert();
	    			return false;

	    		} else if( data.RESULT_CODE == "0005" ){
	    			alert("가입하신지 90일 이내의 고객만 사용후기 작성이 가능합니다.");
	    			clearCert();
	    			return false;

	    		} else if( data.RESULT_CODE == "0006" ){
	    			alert("한 회선당 하나의 사용후기만 작성이 가능합니다. ");
	    			clearCert();
	    			return false;

	    		} else if( data.RESULT_CODE == "0007" ){
	    			alert("인증 번호 발송에 실패했습니다.");
	    			clearCert();
	    			return false;
	    		}

	        } else {
	        	$("#countdown").css("display","none");
	        	alert("인증 번호 발송에 실패했습니다.");
	        	clearCert();
    			return false;
	        }
	    });

	});

	$("#regularCertBtn").click(function(){
		var certify = $("#certify").val();
		if(certify!="Y"){
			alert("인증번호를 받으세요.");
			$("#certifyCallBtn").focus();
			return false;
		}

		var phone = ajaxCommon.isNullNvl($("#phone").val(),"");
		var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
		if(certifySms==""){
			alert("인증번호를 입력해 주세요");
			return false;
		}

		var varData = ajaxCommon.getSerializedData({
			certifySms:$.trim(certifySms)
			,phone:$.trim(phone)
	    });

	    ajaxCommon.getItem({
	        id:'userSmsCheckAjax'
	        ,cache:false
	        ,url:'/requestReView/userSmsCheckAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){
	    	if (AJAX_RESULT_CODE.SUCCESS){
	    		if( data.RESULT_CODE =="00000" ){
	    			alert("인증이 완료되었습니다.");
	    			$("#certifyYn").val("Y");
	    			$("#reviewBtn").show();
	    		} else if( data.RESULT_CODE =="00001" ){
	    			alert("인자값 오류가 발생 하였습니다.");
	    			return false;
	    		} else if(data.RESULT_CODE =="00002"){
	    			alert(data.MSG);
	    			return false;
	    		}
	    	}else{
	    		alert("인증에 실패했습니다.");
	    		return false;
	    	}

	    });
	});

	// 삭제 인증
	$("#certifyCallBtn1").click(function(){

		var name = ajaxCommon.isNullNvl($("#name1").val(),"");
		var phone = ajaxCommon.isNullNvl($("#phone1").val(),"");
		var certCheck = $("input:checkbox[name=certCheck1]").is(":checked");
		if(!certCheck){
			alert("개인정보 수집 및 이용에 대한 동의를 확인해 주세요.");
			return false;
		}

		if( name=="" ){
			$("#name1").focus();
			alert("이름을 입력해 주세요.");
			return false;
		}

		if( phone=="" ){
			$("#phone1").focus();
			alert("휴대폰 번호를 입력해 주세요.");
			return false;
		}
		$("#certifyCallBtn1").text("인증번호 요청");
		var contractNum = $("#contractNum").val();

		var varData = ajaxCommon.getSerializedData({
			name:$.trim(name)
			,phone:$.trim(phone)
			,contractNum : contractNum
	    });

	    ajaxCommon.getItem({
	        id:'userDelSmsAjax'
	        ,cache:false
	        ,url:'/requestReView/userDelSmsAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){
	    	if (AJAX_RESULT_CODE.SUCCESS) {
	    		if( data.RESULT_CODE == "00000" ){

	    			alert("해당 번호로 인증번호를 발송하였습니다.");
					$("#certifyCallBtn1").text("인증번호 재전송");
					$("#certify1").val("Y");

					// 배포전 꼭 삭제하기
					var authNum = data.authNum;
					$("#certifySms1").val(authNum);
					// 배포전 꼭 삭제하기

	    		} else if( data.RESULT_CODE == "0001" ){
	    			alert("인자값 오류가 발생 하였습니다.");
	    			clearCert1();
	    			return false;

	    		} else if( data.RESULT_CODE == "0002" ){
	    			alert("본인이 작성한 리뷰만 삭제 가능합니다.");
	    			clearCert1();
	    			return false;

	    		} else if( data.RESULT_CODE == "0003" ){
	    			alert("인증 번호 발송에 실패했습니다.");
	    			clearCert1();
	    			return false;
	    		}

	        } else {
	        	alert("인증 번호 발송에 실패했습니다.");
	        	clearCert1();
    			return false;
	        }
	    });

	});

	$("#regularCertBtn1").click(function(){
		var certify = $("#certify1").val();
		if(certify!="Y"){
			alert("인증번호를 받으세요.");
			$("#certifyCallBtn1").focus();
			return false;
		}

		var phone = ajaxCommon.isNullNvl($("#phone1").val(),"");
		var certifySms = ajaxCommon.isNullNvl($("#certifySms1").val(),"");
		if(certifySms==""){
			alert("인증번호를 입력해 주세요");
			return false;
		}

		var varData = ajaxCommon.getSerializedData({
			certifySms:$.trim(certifySms)
			,phone:$.trim(phone)
	    });

	    ajaxCommon.getItem({
	        id:'userSmsDelCheckAjax'
	        ,cache:false
	        ,url:'/requestReView/userSmsDelCheckAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){
	    	if (AJAX_RESULT_CODE.SUCCESS){
	    		if( data.RESULT_CODE =="00000" ){
	    			alert("인증이 완료되었습니다.");
	    			$("#certifyYn1").val("Y");
	    			$("#reviewBtn1").show();
	    		} else if( data.RESULT_CODE =="00001" ){
	    			alert("인자값 오류가 발생 하였습니다.");
	    			return false;
	    		} else if(data.RESULT_CODE =="00002"){
	    			alert(data.RESULT_DESC);
	    			return false;
	    		}
	    	}else{
	    		alert("인증에 실패했습니다.");
	    		return false;
	    	}
	    });
	});


	$("#reviewBtn").click(function(){

		fn_mobile_layerClose('joinLayer');

		var commendYn = $("#commendYn").val();
		var reviewSbst = ajaxCommon.isNullNvl($("#reviewSbst").val(),"");
		var eventCd = $("#eventCd").val();
		var snsInfo = $("#snsInfo").val();
		if(reviewSbst==""){
			alert("사용 후기를 입력해 주세요.");
			$("#reviewSbst").focus();
			return false;
		}
		if( reviewSbst.replace(/ /gi, "").length < 40 ){
			alert("40자 이상작성해 주세요.");
			$("#reviewSbst").focus();
			return false;
		}
		var phone = $("#phone").val();
		var name = $("#name").val();

		var formData = new FormData();
		if($('#file1').val() !=""){
			formData.append("file", $("#file1")[0].files[0]);
		}
		if($('#file2').val() !=""){
			formData.append("file", $("#file2")[0].files[0]);
		}

		formData.append("commendYn",commendYn);
		formData.append("reviewSbst",reviewSbst);
		formData.append("eventCd",eventCd);
		formData.append("snsInfo", snsInfo);
		formData.append("phone", phone);
		formData.append("name", name);
        ajaxCommon.getItemFileUp({
            id:'reviewInsertSumit'
            ,cache:false
            ,url:"/requestReView/reviewRegAjax.do"
            ,data: formData
         }
         ,function(jsonObj) {
        	 var resultCode = jsonObj.RESULT_CODE;
        	 var msg = "";
        	 var returnUrl = "";

    		 if(resultCode=="00000"){

    			 alert("등록되었습니다.");
    			 location.reload();
    		 } else if(resultCode=="0001"){

        		 alert("인증을 받아주세요.");
        		 return false;
        	 } else if(resultCode=="0003"){

        		 alert("현재 kt M모바일을 사용중인 고객만 사용후기 작성이 가능합니다.");
        		 return false;
        	 } else if(resultCode=="0002"){

        		 alert("파일 사이즈 및 확장자명을 확인해 주세요.");
        		 return false;
        	 } else if(resultCode=="0004"){

        		 alert("등록에 실패했습니다.");
        		 return false;
        	 }

         });

	});

	$("#reviewBtn1").click(function(){

		var contractNum = $("#contractNum").val();
		var phone = ajaxCommon.isNullNvl($("#phone1").val(),"");
		var certifySms = ajaxCommon.isNullNvl($("#certifySms1").val(),"");
		var name = ajaxCommon.isNullNvl($("#name1").val(),"");
		var varData = ajaxCommon.getSerializedData({
			certifySms:$.trim(certifySms)
			,phone:$.trim(phone)
			,contractNum : contractNum
			,name : name
	    });

	    ajaxCommon.getItem({
	        id:'reviewDeleteAjax'
	        ,cache:false
	        ,url:'/m/review/reviewDeleteAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
         ,function(jsonObj) {
        	 var resultCode = jsonObj.RESULT_CODE;
        	 var msg = "";
        	 var returnUrl = "";

    		 if(resultCode=="00000"){

    			 alert("삭제되었습니다.");
    			 location.reload();
    		 } else if(resultCode=="0001"){

        		 alert("인증을 받아주세요.");
        		 return false;
        	 } else if(resultCode=="0002"){

        		 alert("본인이 작성한 리뷰만 삭제 가능합니다.");
        		 return false;
        	 } else {
        		 alert("삭제에 실패했습니다.");
        		 return false;
        	 }

         });

	});

	// 더보기
	$("#moreBtn").click(function(){
		var reViewCurrent = Number($(".review_fold").length);
		var reViewTotal = Number($("#reViewTotal").text());
		if(reViewCurrent >= reViewTotal){
			alert("마지막 페이지 입니다.");
			return false;
		} else {
			var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),"1"));
			initreViewData(pageNo+1);
			$("#pageNo").val(pageNo+1);
			$(window).scrollTop($("#moreBtn").offset().top-800);
		}
	});

});



function clearCert(){
	$("#name").val("");
	$("#phone").val("");
	$("#certifySms").val("");
	$("#certCheck").prop("checked",false);
	$("#certifyYn").val("N");
	$("#certify").val("N");
	$("#certifyCallBtn").text("인증번호 요청");
	$("#reviewBtn").hide();
}

function clearCert1(){
	$("#name1").val("");
	$("#phone1").val("");
	$("#certifySms1").val("");
	$("#reviewBtn1").hide();
	$("#certCheck1").prop("checked",false);
	$("#certifyYn1").val("N");
	$("#certify1").val("N");
	$("#certifyCallBtn1").text("인증번호 요청");
	$("#contractNum").val("");
}


var initreViewData = function(pageNo){
	var eventCd = $("#selEventCd").val();
	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo
		,eventCd : eventCd
	});

	ajaxCommon.getItem({
		id:'reviewDataAjax'
		,cache:false
		,url:'/m/requestReView/reviewDataAjax.do'
		,data: varData
		,dataType:"json"
		,async:false
	},function(jsonObj){

		var total = jsonObj.total;
		var requestReviewDtoList = jsonObj.requestReviewDtoList;
		viewDesc(total,requestReviewDtoList);
	});

}


var viewDesc = function(total,requestReviewDtoList){

	var htmlArea = "";
	if(requestReviewDtoList !=null && requestReviewDtoList.length>0){
		var reviewImgList = "";
		var regNm = "";
		var reqBuyTypeNm = "";
		var eventCd = "";
		var eventNm = "";
		var sysRdateDd = "";
		var reviewSbst = "";
		var snsInfo = "";
		var commendYn = ""; // 추천,비추천
		var ntfYn = ""; // best
		var contractNum = "";

		for( var i=0; i<requestReviewDtoList.length; i++ ){
			reviewImgList = requestReviewDtoList[i].reviewImgList;
			regNm = requestReviewDtoList[i].regNm;
			reqBuyTypeNm = requestReviewDtoList[i].reqBuyTypeNm;
			eventCd = requestReviewDtoList[i].eventCd;
			eventNm = requestReviewDtoList[i].eventNm;
			sysRdateDd = requestReviewDtoList[i].sysRdateDd;
			reviewSbst = requestReviewDtoList[i].reviewSbst;
			snsInfo = ajaxCommon.isNullNvl(requestReviewDtoList[i].snsInfo,"");
			commendYn = requestReviewDtoList[i].commendYn;
			ntfYn = requestReviewDtoList[i].ntfYn;
			contractNum = requestReviewDtoList[i].contractNum;

			htmlArea += "<div class='review_fold foldArea'>";
			htmlArea += "	<div class='review_text'>";
			htmlArea += "		<div class='top'>";
			if(commendYn =="1"){
				htmlArea += "			<img src='/resources/images/requestReview/icon_recommend.jpg' class='up' alt=''>";
			} else {
				htmlArea += "			<img src='/resources/images/requestReview/icon_non_recommend.jpg' class='down' alt=''>";
			}
			if(ntfYn =="1"){
				htmlArea += "			<img src='/resources/images/requestReview/icon_best.jpg' class='margin_right_10 up' alt=''>";
			}
			htmlArea += "		</div>";
			htmlArea += "		<p>";
			htmlArea += 			regNm+" 님/" +reqBuyTypeNm+"/"+eventNm+"/"+sysRdateDd;
			htmlArea += "		</p>";
			htmlArea += "		<div class='cont ellipsis2'>"+reviewSbst+"</div>";
			htmlArea += "		<a href='javascript:;' class='viewMore'><span class='moreBtn'>더보기</span></a>";
			htmlArea += "	</div>";
			htmlArea += "	<div class='review_photo'>";
			if(reviewImgList !=null && reviewImgList.length>0){
				htmlArea += "			<a href='javascript:;' class='viewMore'>";
				htmlArea += "				<span>"+reviewImgList.length+"</span>";
				htmlArea += "				<img src='"+reviewImgList[0].filePathNm+"' class='imgTag' alt=''>";
				htmlArea += "			</a>";
			}
			htmlArea += "	</div>";
			htmlArea += "</div>";

			htmlArea += "<div class='review_orgn orgnArea' style='display:none;'>";
			htmlArea += "	<div class='review_text'>";
			htmlArea += "		<div class='top'>";
			if(commendYn =="1"){
				htmlArea += "			<img src='/resources/images/requestReview/icon_recommend.jpg' class='up'>";
			} else {
				htmlArea += "			<img src='/resources/images/requestReview/icon_non_recommend.jpg' class='down'>";
			}
			if(ntfYn =="1"){
				htmlArea += "			<img src='/resources/images/requestReview/icon_best.jpg' class='margin_right_10 up'>";
			}
			htmlArea += "		</div>";
			htmlArea += "		<p>";
			htmlArea += 			regNm+" 님/" +reqBuyTypeNm+"/"+eventNm+"/"+sysRdateDd;
			htmlArea += "		</p>";
			htmlArea += "		<div class='cont'>"+reviewSbst+"</div>";

			if(snsInfo !=""){
				htmlArea += "		<div class='sns'>";
				htmlArea += "			<span>SNS 공유 URL</span>";
				if(snsInfo.indexOf("http") > -1){
					htmlArea += "		<a href='"+snsInfo+"' target='_black' class='snslink'>"+snsInfo+"</a>";
				} else {
					htmlArea += "		<a href='https://"+snsInfo+"' target='_black' class='snslink'>"+snsInfo+"</a>";
				}
				htmlArea += "		</div>";
			}
			htmlArea += "		<div class='del'>";
			htmlArea += "			<a href='javascript:;' class='btn_small_lightgrey delBtn' contractNum='"+contractNum+"'>삭제</a>";
			htmlArea += "		</div>";
			htmlArea += "		<a href='javascript:;' class='viewFold'><span class='fold'>접기</span></a>";
			htmlArea += "	</div>";

			htmlArea += "	<div class='review_photo'>";
			if(reviewImgList !=null && reviewImgList.length>0){
				for(var j=0; j<reviewImgList.length; j++){
					htmlArea += "<img src='"+reviewImgList[j].filePathNm+"' class='imgTag'>";
				}
			}
			htmlArea += "	</div>";
			htmlArea += "</div>";
		}
		$("#moreBtn").show();
	} else {
		$("#moreBtn").hide();
		htmlArea += "<div class='default'>아직 작성된 사용후기가 없습니다.</div>";
	}

	// append
	$("#requestReview_board").append(htmlArea);

	// 더보기
	var reViewCurrent = $(".review_fold").length;
	$("#reViewCurrent").text(reViewCurrent); // 현재 노출
	$("#reViewTotal").text(total);

	$(".imgTag").each(function(){
		$(this).error(function(){
			$(this).unbind("error");
			$(this).attr("src","/resources/images/requestReview/noimage.png");
		});
	});

}




















