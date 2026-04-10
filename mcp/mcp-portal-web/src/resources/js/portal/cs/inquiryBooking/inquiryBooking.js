
$(document).ready(function(){

	// 등록 버튼 상태(활성화, 비활성화) 변경
	$("#qnaSubCtg,#svcCntrNo").change(function(){
		btnChk();
	});
	$("#qnaSubject,#qnaContent,#svcCntrNo").keyup(function(){
		btnChk();
	});


	// 문의 유형 변경 이벤트
	$("#qnaCtg").change(function(){

		var etc2 = $(this).val();
		if(etc2==""){  // 문의 유형을 선택하지 않은 경우
			$("#qnaSubCtg").html('<option value="">세부 유형을 선택해주세요</option>');
			btnChk();
			return false;
		}

		var varData = ajaxCommon.getSerializedData({
			grpId : "VOC0028",
			etc2 : etc2
		});

		// 세부유형 가져오기
		ajaxCommon.getItem({
				id:'getBookingCdAjax'
				,cache:false
				,url:'/cs/getBookingCdAjax.do'
				,data: varData
				,dataType:"json"
				,async:false
			}
			,function(jsonObj){
				var ctgHtml = "<option value=''>세부 유형을 선택해주세요</option>";
				if(jsonObj != null && jsonObj.rtnList != null){
					for(var i=0; i<jsonObj.rtnList.length; i++){
						ctgHtml +="<option value='"+jsonObj.rtnList[i].cdVal+"'>"+jsonObj.rtnList[i].cdDsc+"</option>";
					}
				}
				$("#qnaSubCtg").html(ctgHtml);
				btnChk();
			});
	});


	// 문의 내용 글자수 제한
	$("#qnaContent").keyup(function(){
		var content = $(this).val();
		if(content.length > 1000) {
			alert("글자수는 1,000자로 이내로 제한됩니다.");
			$(this).val($(this).val().substring(0, 1000));
		}
		$("#textAreaSbstSize").html($(this).val().length + '/1,000자');
	});


	// 상담 예약일 변경 이벤트
	$("#bookingDate").change(function (){

		// 상담 예약시간 초기화
		$('#timeChk').html('상담시간 선택');
		$('input[name=timeList]').attr("checked", false);
		$('#hiddenTimeChk').val('');

		// 올바르게 예약 일시 선택한 경우 상담시간 선택 영역 show
		$('#timeChk').show();
		btnChk();
		return true;
	});


	// 문의 등록하기 버튼
	$("#saveBtn").click(function(){

		var disabledTarget= btnChk();
		if(disabledTarget != ""){

			var alertMsg= "필수값을 입력해 주시기 바랍니다.";
			switch (disabledTarget) {
				case "svcCntrNo":
					alertMsg= "문의 번호를 선택해 주세요.";
					break;
				case "bookingDate":
					alertMsg= "상담 날짜를 선택해 주세요.";
					break;
				case "timeChk":
					alertMsg= "상담 시간을 선택해 주세요.";
					break;
				case "qnaCtg":
					alertMsg= "문의 유형을 선택해 주세요.";
					break;
				case "qnaSubCtg":
					alertMsg= "세부 유형을 선택해 주세요.";
					break;
				case "qnaSubject":
					alertMsg= "문의 제목을 입력해 주세요.";
					break;
				case "qnaContent":
					alertMsg= "문의 내용을 입력해 주세요.";
					break;
			}

			MCP.alert(alertMsg, function(){
				$("#"+disabledTarget).focus();  // 포커스 부여
			})

			return false;
		}

		// 예약 프로세스 시작
		KTM.Confirm('예약하시겠습니까?' ,function () {
			$("#saveBtn").prop("disabled",true); // 버튼 다중클릭 방지
			this.close(); // confirm 팝업창 닫기

			var varData = ajaxCommon.getSerializedData({
				csResDt : $('#bookingDate').val().replace(/[^0-9]/g,''),
				csResTm : $('#hiddenTimeChk').val(),
				csSubject : $.trim($("#qnaSubject").val()),
				csContent : $.trim($("#qnaContent").val()),
				vocDtl : $("#qnaSubCtg option:selected").val(),
				vocThi : $("#qnaCtg option:selected").val(),
				contractNum : $("#svcCntrNo").val()
			});

			ajaxCommon.getItem({
				id:'insertInquiryBooking'
				,cache:false
				,url:'/cs/insertInquiryBooking.do'
				,data: varData
				,dataType:"json"
				,async:false
				,errorCall : function () {
					$("#saveBtn").prop("disabled",false);
				}
			},function(jsonObj){
				$("#saveBtn").prop("disabled",false);

				var resultCd= (!jsonObj || !jsonObj.RESULT_CODE) ? "FAIL" :jsonObj.RESULT_CODE;
				var resultMsg= (!jsonObj || !jsonObj.RESULT_MSG) ? "상담예약에 실패했습니다." : jsonObj.RESULT_MSG;

				if(resultCd=="0002"){ // 로그인 페이지로 이동
					MCP.alert(resultMsg,function (){location.href='/loginForm.do';});
					return false;
				}

				if(resultCd == '0003'){ // 정회원 인증 페이지로 이동
					MCP.alert(resultMsg, function (){location.href = '/mypage/updateForm.do';});
					return false;
				}

				if(resultCd != AJAX_RESULT_CODE.SUCCESS){ // 이외의 오류 발생
					MCP.alert(resultMsg);
					return false;
				}

				// 예약 성공 시 상담예약 결과보기 페이지로 이동
				MCP.alert("고객님의 상담 예약 신청이 정상적으로 접수되었습니다.\n상담 신청에 대한 결과는 [상담 예약 결과보기]에서 확인하실 수 있습니다.",function(){
					location.href='/cs/csInquiryBookingHist.do';
				});
			});
		});
	});

});

// 문의 등록하기 버튼 상태 변경 (활성, 비활성)
// 비활성화인 경우, 비활성화된 원인 아이디 리턴
function btnChk(){
	var disabledBt = false;
	var disabledTarget= "";

	// 5. 문의 내용
	var qnaContent =  $.trim($("#qnaContent").val());
	if(qnaContent == ""){
		disabledBt = "Y";
		disabledTarget= "qnaContent";
	}

	// 4. 문의 제목
	var qnaSubject = $.trim($("#qnaSubject").val());
	if(qnaSubject == ""){
		disabledBt = true;
		disabledTarget= "qnaSubject";
	}

	// 3. 문의 유형
	var qnaCtg = ajaxCommon.isNullNvl($("#qnaCtg option:selected").val(),"");
	var qnaSubCtg = ajaxCommon.isNullNvl($("#qnaSubCtg option:selected").val(),"");
	if(qnaCtg == ""){
		disabledBt = true;
		disabledTarget= "qnaCtg";
	}else if(qnaSubCtg == ""){
		disabledBt = true;
		disabledTarget= "qnaSubCtg";
	}

	// 2. 상담 요청일시
	var bookingDate = ajaxCommon.isNullNvl($('#bookingDate').val(),"");
	bookingDate= bookingDate.replace(/[^0-9]/g,'');
	var timeChk = $('#hiddenTimeChk').val();
	if(bookingDate == ""){
		disabledBt = true;
		disabledTarget= "bookingDate";
	}else if(timeChk == ""){
		disabledBt = true;
		disabledTarget= "timeChk";
	}

	// 1. 문의번호
	var svcCntrNo = $("#svcCntrNo").val();
	if(svcCntrNo == ""){
		disabledBt = true;
		disabledTarget= "svcCntrNo";
	}

	//필수값이 없다면 버튼 disabled
	if(disabledBt) $("#saveBtn").prop("disabled",true);
	else $("#saveBtn").prop("disabled",false);

	return disabledTarget;
}

// 시간 선택 팝업 호출
function bookingTime(){

	// 상담 예약일 체크
	var bookingDate = $('#bookingDate').val();
	bookingDate= bookingDate.replace(/[^0-9]/g,'');

	var startDate= new Date(today.getFullYear(), today.getMonth(), today.getDate()); // 예약 가능 시작일 (today는 전역으로 선언)
	var bookingDt= new Date(bookingDate.substring(0,4), bookingDate.substring(4,6)-1, bookingDate.substring(6,8));

	if(bookingDate==null || bookingDate== '') { // 날짜 미선택
		MCP.alert("상담 예약일을 선택해 주세요", function(){btnChk()});
		return false;
	} else if(bookingDt <= startDate){ // 이전 날짜 선택
		MCP.alert("당일 포함한 지난 날짜는 예약할 수 없습니다.", function(){btnChk()});
		return false;
	} else if(bookingDt > endDate){   // 익월 이후 날짜 선택 (endDate는 전역으로 선언)
		MCP.alert("익월까지만 예약이 가능합니다.", function(){btnChk()});
		return false;
	}

	// 해당 날짜 예약 가능여부 체크
	var varData = ajaxCommon.getSerializedData({
		csResDt : bookingDate
	});

	ajaxCommon.getItem({
			id:'checkBookingAvailableByDt'
			,cache:false
			,url:'/cs/checkBookingAvailableByDt.do'
			,data: varData
			,dataType:"json"
			,async:false
		}
		,function(jsonObj){

			var resultCd= (!jsonObj || !jsonObj.remainYn) ? "N" :jsonObj.remainYn;

			if(resultCd == "N"){ // 해당 일시에 예약가능 인원 수 없음
				MCP.alert("선택하신 날짜에 예약이 모두 완료되었습니다.<br/>다른 날짜로 선택 부탁드립니다.");
				return false;
			}else if(resultCd == "F"){ // 해당 일시에 예약가능 인원 수 없음
				MCP.alert("당일 포함 이전 날짜는 예약이 불가하며,</br>익월까지만 예약 가능합니다.");
				return false;
			}

			// 해당 일시에 예약가능 인원 수 존재
			openPage('pullPopupByPost','/bookingTimePop.do',varData);
		});
}







