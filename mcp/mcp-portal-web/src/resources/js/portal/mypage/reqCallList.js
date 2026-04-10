VALIDATE_NOT_EMPTY_MSG.cstmrName = "성명을 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrNativeRrn01 = "주민등록번호 앞자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrNativeRrn02 = "주민등록번호 뒷자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.chkAgreeAll = "전체 약관에 동의해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrReceiveTelFn = "연락가능 연락처 앞자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrReceiveTelMn = "연락가능 연락처 중간자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrReceiveTelRn = "연락가능 연락처 뒷자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.fax1 = "팩스번호 앞자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.fax2 = "팩스번호 중간자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.fax3 = "팩스번호 뒷자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.startDate = "신청기간 시작일을 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.endDate = "신청기간 종료일을 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.callType01 = "열람항목을 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrPost = "우편번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrAddr = "주소를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrAddrDtl = "상세 주소를 입력해 주세요.";


var pageObj = {
    niceType:""
    , initAddition:0
    , applDataForm:{}
    , niceHistSeq:0
    , authObj:{}
    , authPassObj:{}
    , insrNiceHistSeq:0
    , niceHistInsrProdSeq:0
    , startTime:0
    , eventId:""
    , step:1
    , clauseJehuRateCount:0
    , clauseFinanceRateCount:0
    , additionBetaList:[]
    , additionKeyList:[]
    , additionTempKeyList:[]
    , reqAddition:[]
    , reqAdditionPrice:0
    , insrProdList:[]
    , insrProdCd:""      //안심보험 선택 코드값
    , insrProdObj:null   //{}
    , addrFlag:""
    , requestKey:0       //서식지 등록후 key값
    , resNo:0            //서식지 등록후 예약번호
    , niceResSmsObj:{}   //셀프개통 신규 인증한 SMS인증 정보
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증
    , checkCnt:0         //callback 호출 건수
    , prdtNm:""          //상품명
    , rateNm:""          //요금제명
    , modelSalePolicyCode : "" //init 정책정보
    , modelId:""
    , telAdvice:false   //전화상담 여부
    , prmtIdList:[]     // 사은품 프로모션 코드
    , prdtIdList:[]    // 사은품 제품ID
    , phoneObj:null
    , giftList:[]
    , custPoint:null
    , cardDcCd:""
    , rateObj:null
    , fnReqPreCheckCount:0
    , priceLimitObj:null
    , usimOrgnId:""
    , maxDcAmtInt:0
    , cid:""

 	, onlineAuthType : ""   //인증타입
 	, onlineAuthInfo : ""   //인증값
    , startDate : ""        //신청기간시작
    , endDate : ""          //신청기간종료
    , userId : ""           //신청자아이디
    , cstmrName : ""        //신청자이름
    , mobileNo : ""         //신청번호
    , cstmrNativeRrn : ""   //주민번호
    , reqType : ""          //요청타입
    , cstmrType : ""        //고객타입
    , cretId : ""           //신청자아이디
    , callType : ""         //열람항목
    , reqRsn : ""           //신청사유
    , recvType : ""         //수신방법
    , recvText : ""         //수신메모
    , callNum : ""          //연락가능번호
    , mailAddr : ""         //메일주소
    , etcMemo : ""          //기타입력사항
    , typeVoice : "N"
    , typeText : "N"
    , typeData : "N"
    , typeVoiceText : "N"
    , typeAll : "N"
}

/*history.pushState(null, null,"/mypage/reqCallList.do");
	window.onpopstate = function (event){
    history.go("/mypage/reqCallList.do");
}*/

//날짜선택
document.addEventListener('UILoaded', function() {
	KTM.datePicker('.flatpickr', {
    dateFormat: 'Y.m.d',
  });
});

//우편번호 검색
$("._btnAddr").click(function() {
    openPage('pullPopup', '/addPopup.do','','1')
});

//주고콜백(주소setting)
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
    $("#cstmrPost").val(zipNo);
    $("#cstmrAddr").val(roadAddrPart1);
    $("#cstmrAddrDtl").val(roadAddrPart2 + " " + addrDetail);
    $("#cstmrAddr").parent().addClass('has-value');
}

//취소 클릭시
$("#btnReqCancel").on("click", function(){
	location.href = "/main.do";
});

//수신방법-팩스 선택시
$("#rdFx").on("click", function(){
    $("#fax").show();
    $("#Address").hide();
});

//수신방법-등기 선택시
$("#rdPt").on("click", function(){
    $("#fax").hide();
    $("#Address").show();
});

//열람항목 종류 선택시
$("input:radio[name=rdType]").on("click", function(){
	$("#callType01").prop("checked", true);
});

//열람항목 - 국내 선택시
$("#callType01").on("click", function(){
	if ($(this).is(':checked')) {
		$("#rdType01").prop("checked", true);
	}else {
		$("input:radio[name=rdType]").prop("checked", false);
	}
});

//열람항목 - 국제 선택시
$("#callType02").on("click", function(){
	if ($(this).is(":checked")) {
		$("#rdType06").prop("checked", true);
	}else {
		$("#rdType06").prop("checked", false);
	}
});

//열람항목 - 국제전화 선택시
$("#rdType06").on("click", function(){
	if ($(this).is(":checked")) {
		$("#callType02").prop("checked", true);
	}else {
		$("#callType02").prop("checked", false);
	}
});

//전체약관동의 클릭시
$("#chkAgreeAll").on("click", function(){
	if ($(this).is(":checked")) {
		$("._agree").prop("checked", true);
	}
	else {
		$("._agree").prop("checked", false);
	}
});

//약관동의 각각 클릭시
/*function fnCheckAgree(){
	if($("#chkAgree1").is(":checked") && $("#chkAgree2").is(":checked") && $("#chkAgree3").is(":checked")){
		$("#chkAgreeAll").prop("checked", true);
	}else{
		$("#chkAgreeAll").prop("checked", false);
	}
}*/

function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

$(document).ready(function() {

	// 특수문자 제거
	$("#etcMemo").keyup(function(){
		$(this).val($(this).val().replace(/[?&=]/gi,''));
		strCheck();
	});

	// 특수문자 제거
	$("etcMemo").blur(function(){
		$(this).val($(this).val().replace(/[?&=]/gi,''));
		strCheck();
	});

    // 본인인증 전 유효성 검사
    simpleAuthvalidation = function(){

		validator.config = {};
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';

        var idNum = $.trim($("#cstmrNativeRrn02").val()).substring(0,1);
		if (idNum == '5' || idNum == '6' || idNum == '7' || idNum == '8') {  //외국인
	        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isFngno';
	        pageObj.cstmrType = "FN";
	    } else {
        	var age = fn_getAge($.trim($("#cstmrNativeRrn01").val()),idNum);
			if (age < 19) { // 청소년
		        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
		        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isTeen';
		        pageObj.cstmrType = "NM";
			} else { // 내국인
        		validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
		        pageObj.cstmrType = "NA";
			}
		}

        if (validator.validate()) { // 유효성검사 통과 시
			return true;
		}else{
			// 유효성 검사 불충족시 팝업 표출
		 	var errId = validator.getErrorId();
	        MCP.alert(validator.getErrorMsg(),function (){
	           $selectObj = $("#"+errId);
	           viewAuthErrorMgs($selectObj, validator.getErrorMsg());
	           $('#' + validator.id).focus(); // 포커스
	        });
			return false;
		}
	} // end of simpleAuthvalidation----------------------------------


    //최종적으로 통화내역열람 신청 버튼 클릭
    $('#btnReqCall').click(function(){

        if ($("#isAuth").val() != "1") {
            MCP.alert("본인 인증이 완료되지 않았습니다.");
            return false;
        }

		// 에러 문구 제거
		$(".c-form__text").remove();
    	$(".has-error").removeClass("has-error");

		validator.config={};
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn02'] = 'isNonEmpty';
        validator.config['startDate'] = 'isNonEmpty';
        validator.config['endDate'] = 'isNonEmpty';

    	//국내, 국제 둘다 선택되지 않았다면
    	if(!$("#callType01").is(":checked") && !$("#callType02").is(":checked")){
			validator.config['callType01'] = 'isNonEmpty';
		}

		//수신방법
		if($("input:radio[name=rdRecvType]:checked").val() == "FX"){
			validator.config['fax1'] = 'isNonEmpty';
	        validator.config['fax2'] = 'isNonEmpty';
	        validator.config['fax3'] = 'isNonEmpty';
		}else {
			validator.config['cstmrPost'] = 'isNonEmpty';
	        validator.config['cstmrAddr'] = 'isNonEmpty';
	        validator.config['cstmrAddrDtl'] = 'isNonEmpty';
		}

        validator.config['cstmrReceiveTelFn'] = 'isNonEmpty';
        validator.config['cstmrReceiveTelMn'] = 'isNonEmpty';
        validator.config['cstmrReceiveTelRn'] = 'isNonEmpty';

		//선택 날짜 체크
		if($.trim($("#startDate").val()) != "" && $.trim($("#endDate").val()) != ""){
			var diff = fn_getDateDiff($.trim($("#startDate").val().replaceAll('.', '')) + "-" + $.trim($("#endDate").val().replaceAll('.', '')));

			if(diff == "fail"){
				MCP.alert("시작일자가 종료일자보다 클 수 없습니다.",function(){
	                viewErrorMgs($("#startDate"), "시작일자가 종료일자보다 클 수 없습니다.");
	                $("#startDate").focus(); // 포커스
	            });
	            return false;
			}

			if(diff > 365){
				MCP.alert("신청기간은 최대 12개월 입니다.",function(){
	                viewErrorMgs($("#endDate"), "신청기간은 최대 12개월 입니다.");
	                $("#endDate").focus(); // 포커스
	            });
	            return false;
			}
		}

        if(validator.validate()){

			//팩스번호, 연락가능전화번호 체크
			var strTelChk = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/;
			var strPhoneChk = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;

			//수신방법
			if($("input:radio[name=rdRecvType]:checked").val() == "FX"){
				if($.trim($("#fax1").val()).length < 2){
					MCP.alert("팩스번호의 지역번호를 올바르게 입력해 주세요.",function(){
		                viewErrorMgs($("#fax1"), "팩스번호의 지역번호를 올바르게 입력해 주세요.");
		                $("#fax1").focus(); // 포커스
		            });
	            	return false;
		        }

		        if($.trim($("#fax2").val()).length < 3){
					MCP.alert("팩스번호를 올바르게 입력해 주세요.",function(){
		                viewErrorMgs($("#fax2"), "팩스번호를 올바르게 입력해 주세요.");
		                $("#fax2").focus(); // 포커스
		            });
	            	return false;
		        }

		        if($.trim($("#fax3").val()).length < 4){
					MCP.alert("팩스번호를 올바르게 입력해 주세요.",function(){
		                viewErrorMgs($("#fax3"), "팩스번호를 올바르게 입력해 주세요.");
		                $("#fax3").focus(); // 포커스
		            });
	            	return false;
		        }

				pageObj.recvText = $.trim($("#fax1").val()) + "" + $.trim($("#fax2").val()) + "" + $.trim($("#fax3").val());

				if(!strTelChk.test(pageObj.recvText)){
					MCP.alert("팩스번호를 올바르게 입력해 주세요.",function(){
		                viewErrorMgs($("#fax1"), "팩스번호를 올바르게 입력해 주세요.");
		                $("#fax1").focus(); // 포커스
		            });
	            	return false;
		        }
			}else {
				pageObj.recvText = $.trim($("#cstmrPost").val()) + " " + $.trim($("#cstmrAddr").val()) + " " + $.trim($("#cstmrAddrDtl").val());
			}

			//연락가능연락처
			pageObj.callNum = $.trim($("#cstmrReceiveTelFn").val() + "" + $("#cstmrReceiveTelMn").val() + "" + $("#cstmrReceiveTelRn").val());
			if(!strPhoneChk.test(pageObj.callNum)){
				MCP.alert("연락가능 연락처를 올바르게 입력해 주세요.",function(){
	                viewErrorMgs($("#cstmrReceiveTelFn"), "연락가능 연락처를 올바르게 입력해 주세요.");
	                $("#cstmrReceiveTelFn").focus(); // 포커스
	            });
            	return false;
	        }

			//전체약관동의 체크
			if(!$("#chkAgreeAll").is(":checked")){
				MCP.alert("약관에 동의해 주세요.",function(){
	                viewErrorMgs($("#chkAgreeAll"), "약관에 동의해 주세요.");
	                $("#chkAgreeAll").focus(); // 포커스
	            });
	            return false;
			}

			//열람항목 타입 set
			var typeVal = $("input:radio[name=rdType]:checked").val();
			if(typeVal != null){
				fn_getTypeVal(typeVal);
			}

			//열람항목(국제/국내) 선택
			//국내 선택 & 국제 선택 안했을때
			if($("#callType01").is(":checked") && !$("#callType02").is(":checked")){
				pageObj.callType = "NA";
			//국제가 선택되어 있다면, 국내, 국제 둘다 선택해도 무조건 IN
			}else if($("#callType02").is(":checked")){
				pageObj.callType = "IN";
			}

			//열람항목 종류(음성, 문자, 데이터, 음성+문자, 음성+문자+데이터)
			pageObj.applDataForm['typeVoice'] = pageObj.typeVoice;
			pageObj.applDataForm['typeText'] = pageObj.typeText;
			pageObj.applDataForm['typeData'] = pageObj.typeData;
			pageObj.applDataForm['typeVoiceText'] = pageObj.typeVoiceText;
			pageObj.applDataForm['typeAll'] = pageObj.typeAll;
			//
			pageObj.applDataForm['cstmrName'] = $.trim($("#cstmrName").val());
			pageObj.applDataForm['cstmrNativeRrn'] = $.trim($("#cstmrNativeRrn01").val() + "" + $("#cstmrNativeRrn02").val());
			pageObj.applDataForm['reqType'] = "CL";
			pageObj.applDataForm['callType'] = pageObj.callType;
			pageObj.applDataForm['cstmrType'] = pageObj.cstmrType;
			pageObj.applDataForm['onlineAuthType'] = pageObj.onlineAuthType;
			//pageObj.applDataForm['onlineAuthInfo'] = pageObj.onlineAuthInfo;
			pageObj.applDataForm.reqSeq = pageObj.niceResLogObj.reqSeq;
			pageObj.applDataForm.resSeq = pageObj.niceResLogObj.resSeq;
			pageObj.applDataForm['startDate'] = $.trim($("#startDate").val());
			pageObj.applDataForm['endDate'] = $.trim($("#endDate").val());
			pageObj.applDataForm['reqRsn'] = $("input:radio[name=rdReason]:checked").val();
			pageObj.applDataForm['recvType'] = $("input:radio[name=rdRecvType]:checked").val();
			pageObj.applDataForm['recvText'] = pageObj.recvText;
			pageObj.applDataForm['callNum'] = $.trim($("#cstmrReceiveTelFn").val() + "" + $("#cstmrReceiveTelMn").val() + "" + $("#cstmrReceiveTelRn").val());
			pageObj.applDataForm['mailAddr'] = "";
			pageObj.applDataForm['etcMemo'] = $("#etcMemo").val();
			pageObj.applDataForm['contractNum'] = $("#contractNum").val(); //계약번호

			KTM.Confirm('통화내역 열람을 신청하시겠습니까?', function() {

				var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);

	    		KTM.Dialog.closeAll(); //모달닫기
	    		KTM.LoadingSpinner.show();

			    ajaxCommon.getItem({
			        id:'custRequestAjax'
			        ,cache:false
			        ,url:'/mypage/custRequestAjax.do'
			        ,data: varData
			        ,dataType:"json"
			        ,async:false
			    }
			    ,function(data){
					KTM.LoadingSpinner.hide(true);

					if(data.RESULT_CODE == 'SUCCESS'){
						MCP.alert("신청이 완료되었습니다.(영업일 기준 D+1일 이내에 통화내역열람 신청내용 확인 안내전화를 드립니다.)", function (){
			        		location.href="/main.do";
			            });
					}else if(data.RESULT_CODE == 'AUTH01'){ // AUTH 오류
						MCP.alert(data.RESULT_MSG);
					}else if(data.RESULT_CODE == 'STEP01' || data.RESULT_CODE == 'STEP02'){ // STEP 오류
						MCP.alert(data.RESULT_MSG);
					}else {
						MCP.alert(data.RESULT_CODE + ' ' + data.RESULT_MSG);
					}
			    });
			});
		}else {
        	var errId = validator.getErrorId();
        	MCP.alert(validator.getErrorMsg(),function (){
            	$selectObj = $("#"+errId);
            	viewErrorMgs($selectObj, validator.getErrorMsg());
            	$('#' + validator.id).focus(); // 포커스
        	});
        	$(this).prop("checked", "");
    	}
    })

})

//본인인증 콜백
var fnNiceCert = function(prarObj) {
    var cstmrNativeRrn ;
    var cstmrName ;

    var strMsg = "고객 정보와 본인인증한 정보가 틀립니다." ;
    pageObj.niceResLogObj = prarObj;

    //본인인증
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {

        cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val()); //인증생년월일
        cstmrName = $.trim($("#cstmrName").val());	//인증이름

        var authInfoJson= {cstmrName: cstmrName , cstmrNativeRrn: cstmrNativeRrn, contractNum: $("#contractNum").val(), authType: prarObj.authType};
		var isAuthDone = mypageAuthCallback(authInfoJson);

		// 성공
		if(isAuthDone){
			//현재 페이지의 authObj에 prarObj 넣기
            pageObj.authObj = prarObj;
            //인증타입
            pageObj.onlineAuthType = prarObj.authType;
            //메시지 띄우기
		    MCP.alert("본인인증에 성공 하였습니다.");
		    //입력폼 보이기
            $("#requestForm").show();
            //신청버튼 활성화
		    $("#btnReqCall").prop("disabled", false);
            //이름, 주민번호 readonly
            $("#cstmrName").prop("readonly", "readonly");
            $("#cstmrNativeRrn01").prop("readonly", "readonly");
            $("#cstmrNativeRrn02").prop("readonly", "readonly");
            return null;
        //실패
		}else{

			var resultCd= niceAuthObj.resAuthOjb.RESULT_CODE;
			var errorMsg= niceAuthObj.resAuthOjb.RESULT_MSG;

			if (resultCd == "LOGIN") {
				MCP.alert(errorMsg, function () {
					location.href = '/loginForm.do';
				});
				return null;
			}

			strMsg= (errorMsg == undefined) ? strMsg : errorMsg;
			MCP.alert(strMsg);
            return null;
		}
    }
}


//회선 조회
function select(){
	document.selfFrm.ncn.value = $("#ctn").val();
	document.selfFrm.submit();
}

//에러메시지 보여주기
var viewErrorMgs = function($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};

//열람항목 타입
function fn_getTypeVal(typeVal){
	switch (typeVal) {
	    case '01':
	        pageObj.typeVoice = "Y";
	        pageObj.typeText = "N";
	        pageObj.typeData = "N";
	        pageObj.typeVoiceText = "N";
	        pageObj.typeAll = "N";
	        break;
	    case '02':
	    	pageObj.typeVoice = "N";
	        pageObj.typeText = "Y";
	        pageObj.typeData = "N";
	        pageObj.typeVoiceText = "N";
	        pageObj.typeAll = "N";
	        break;
	    case '03':
	    	pageObj.typeVoice = "N";
	        pageObj.typeText = "N";
	        pageObj.typeData = "Y";
	        pageObj.typeVoiceText = "N";
	        pageObj.typeAll = "N";
	        break;
	    case '04':
	    	pageObj.typeVoice = "N";
	        pageObj.typeText = "N";
	        pageObj.typeData = "N";
	        pageObj.typeVoiceText = "Y";
	        pageObj.typeAll = "N";
	        break;
	    case '05':
	    	pageObj.typeVoice = "N";
	        pageObj.typeText = "N";
	        pageObj.typeData = "N";
	        pageObj.typeVoiceText = "N";
	        pageObj.typeAll = "Y";
	        break;
	  }
}

//날짜비교계산
function fn_getDateDiff(id){
	var ids = id.split('-');

	var v1 = ids[0];
	var vDate1 = new Date(v1.substring(0,4), v1.substring(4,6) - 1, v1.substring(6,8));

	var v2 = ids[1];
	var vDate2 = new Date(v2.substring(0,4), v2.substring(4,6) - 1, v2.substring(6,8));

	if (vDate1 > vDate2) {
	    return "fail";
	}

	var diff = Math.abs(vDate2.getTime() - vDate1.getTime());
	diff = Math.ceil(diff / (1000 * 3600 * 24));

	return diff;
}

//etcMemo 300자 제한
var strCheck = function (){
	var content = $('#etcMemo').val();
	$("#textAreaSbstSize").text(content.length + "/300자");
	if (content.length > 300) {
		$('#etcMemo').val(content.substring(0, 300));
		$('#textAreaSbstSize').text("300/300자");
	}
}

