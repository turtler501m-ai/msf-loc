
var checkDtls = [];
window.onpageshow = function (evt){
	if(evt.persisted || window.performance.navigation.type == 2){
		location.href = '/loginForm.do';
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

	$('#checkAll').on('click', function (){
		if($(this).prop('checked')){
			$('input[name=terms]').prop('checked', 'checked');
			$('input[name=terms]').addClass('btn_agree_active');
			$('input[name=terms]').removeClass('btn_agree');
		}else{
			$('input[name=terms]').removeProp('checked');
			$('input[name=terms]').addClass('btn_agree');
			$('input[name=terms]').removeClass('btn_agree_active');
		}
		setChkbox();
	});

	initVal();

	/* 모바일 인증 클릭 */
	$("#auth_phone").on("click", function() {
		if(!validationChk()) return;
		pop_nice();
	});

	/* 아이핀 인증 클릭 */
	$("#auth_ipin").on("click", function() {
		if(!validationChk()) return;
		pop_ipin();
	});

	/* 모바일 인증 클릭 */
	$("#auth_credit").on("click", function() {
		if(!validationChk()) return;
		pop_credit();
	});

});

function setChkbox(data) {
	var cnt = 0;
	if (data != undefined ) {
		if ($(data).attr('inheritance') != '' && $(data).attr('inheritance') != undefined) {
			document.getElementsByName('terms').forEach((param) => {
				if ($(param).attr('data-dtl-cd') == $(data).attr('inheritance') && !$('#'+param.id).is(':checked')) {
					MCP.alert("정보/광고 수신 동의를 위해서는 먼저 고객 혜택 제공을 위한 개인정보 수집 및 이용 동의에 동의하여야 합니다.\n");
					data.checked = false;
					return;
				}
			})
		}
		if ($(data).attr('inheritance') == '' || $(data).attr('inheritance') == undefined) {
			if (checkDtls.indexOf($(data).attr('data-dtl-cd')) != -1) {
				document.getElementsByName('terms').forEach((param) => {
					if (checkDtls.indexOf($(param).attr('inheritance')) != -1) {
						param.checked = false;
					}
				});
			}
		}
	}

	$('input[name=terms]').each(function (){
		if($(this).prop('checked')){
			cnt++;
		}
	});

	if(cnt == $('input[name=terms]').length){
		$('#checkAll').prop('checked', 'checked');
	}else{
		$('#checkAll').removeProp('checked');
	}
}

/* validation check - 약관 동의 여부 확인 */
var validationChk = function (){
	var cnt = 0;
	$('input[name=terms]').each(function (){
		if($(this).attr('data-mand-yn') == 'Y' && !$(this).is(':checked')){
			MCP.alert('필수 이용약관을 모두 동의하셔야 합니다.', function (){
				$(this).focus();
				$('input[name=radCertification]').removeProp('checked');
				$('input[name=radCertification]').blur();
			});
			cnt++;
			return false;
		}
	});
	if(cnt == 0){
		var clauseYn = 'N';
		var clauseYn04 = 'N';
		var clauseYn05 = 'N';
		$('input[name=terms]').each(function (){
			if($(this).attr('data-dtl-cd') == 'TERMSMEM03' && $(this).is(':checked')){
				clauseYn = 'Y';
			}
			if($(this).attr('data-dtl-cd') == 'TERMSMEM04' && $(this).is(':checked')){
				clauseYn04 = 'Y';
			}
			if($(this).attr('data-dtl-cd') == 'TERMSMEM05' && $(this).is(':checked')){
				clauseYn05 = 'Y';
			}
		});
		$('#clausePriAdFlag').val(clauseYn);
		$('#personalInfoCollectAgree').val(clauseYn04);
		$('#othersTrnsAgree').val(clauseYn05);
		return true;

	}else{
		return false;

	}
};

var pageObj = {
	niceType:""
	, authObj:{}
	, niceHistSeq:0
	, startTime:0
}

function niceAuthResult(prarObj){

	var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
	pageObj.niceResLogObj = prarObj;

	var authInfoJson ={contractNum: "", ncType: "0"};
	var isAuthDone = diAuthCallback(authInfoJson);

	if(isAuthDone){
		pageObj.niceHistSeq = diAuthObj.resAuthOjb.NICE_HIST_SEQ ;
		$('#frm').submit();
	}else{

		strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
		MCP.alert(strMsg);
		return null;
	}
}


/* 아이핀 인증 성공 후 객체값 post방식으로 넘김 / 다음 페이지 이동 */
function fnNiceIpinCert(obj) {
	niceAuthResult(obj);
}

function fnNiceCert(obj) {
	niceAuthResult(obj);
}


function fnNiceCertErr() {
	return false;
}

function fnNiceIpinCertErr() {
	return false;
}

function pop_nice() {
	pageObj.niceType = NICE_TYPE.CUST_AUTH ;
	var data = {width:'500', height:'700'};
	openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data)
}


function pop_ipin() {
	pageObj.niceType = NICE_TYPE.CUST_AUTH ;
	var data = {width:'500', height:'700'};
	openPage('outLink2', '/nice/popNiceIpinAuth.do', data)
}

function pop_credit() {
	pageObj.niceType = NICE_TYPE.CUST_AUTH ;
	var data = {width:'500', height:'700'};
	openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=C', data)
}

function viewTerms(targetId, param) {
	$('#targetTerms').val(targetId);
	if(param == 'cdGroupId1=TERMSMEM&cdGroupId2=TERMSMEM02'){
		// openPage('termsPop','/termsPop.do',param, '0');
		openPage('termsPop','/termsPop.do',param, 0);
	}else{
		openPage('termsPop','/termsPop.do',param, 0);
	}
}

function targetTermsAgree() {
	if (checkDtls.indexOf($('#'+$('#targetTerms').val()).attr('inheritance')) != -1) {
		var checkPossible = true;
		document.getElementsByName('terms').forEach((param) => {
			if (checkDtls.indexOf($(param).attr('data-dtl-cd')) != -1) {
				checkPossible = $(param).is(':checked');
			}
		})
		if (!checkPossible) {
			MCP.alert("정보/광고 수신 동의를 위해서는 먼저 고객 혜택 제공을 위한 개인정보 수집 및 이용 동의에 동의하여야 합니다.\n");
			return;
		}
	}
	$('#' + $('#targetTerms').val()).prop('checked', 'checked');
}

var initVal = function () {

	document.getElementsByName('terms').forEach((param) => {
		if ($(param).attr('inheritance') != '' && $(param).attr('inheritance') != undefined ) {
			if (checkDtls.indexOf($(param).attr('inheritance')) == -1) {
				checkDtls.push($(param).attr('inheritance'));
			}
		}
	})
}