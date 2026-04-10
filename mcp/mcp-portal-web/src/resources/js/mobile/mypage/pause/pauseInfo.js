
var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
}

$(document).ready(function (){

	$("#btnSuspenHis").click(function(){
		var parameterData = ajaxCommon.getSerializedData({
			strNcn : $("#ctn option:selected").val()
		});
		openPage('pullPopupByPost','/m/mypage/suspenPosHisPop.do',parameterData);
	});


	/* 모바일 인증 클릭 */
	$("#auth_phone").on("click", function() {
		validatorCheck("phone");
	});

	/* 아이핀 인증 클릭 */
	$("#auth_ipin").on("click", function() {
		validatorCheck("ipin");
	});

	/* 신용카드 인증 클릭 */
	$("#auth_credit").on("click", function() {
		validatorCheck("credit");
	});

});

var validatorCheck = function (auth) {

	if(!$("#chkA").is(":checked")) {
		MCP.alert("본인인증 절차 진행에 동의해야 합니다.");
		$("#chkA").focus();
		return false;
	}

	//일시정지가능여부조회
	var varData = ajaxCommon.getSerializedData({
		strNcn : $("#ctn option:selected").val()
    });

	var isTrue = false;

    ajaxCommon.getItem({
        id:'getSuspenPosInfoAjax'
        ,cache:false
        ,url:'/mypage/getSuspenPosInfoAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    }
    ,function(jsonObj){
    	var subStatus = $("#subStatus").val();
        if (jsonObj.RESULT_CODE ="00000") {
        	isTrue = true;
        	var ctnStatus = jsonObj.ctnStatus;  //계약상태
        	var rsltInd = jsonObj.rsltInd;  //일시정지상태
        	var runMode = jsonObj.runMode; // 분실신고 상태
        	$("#ctnStatus").val(ctnStatus);
        	$("#rsltInd").val(rsltInd);
       		if (ctnStatus == "A" && rsltInd !="Y" ) {
       			MCP.alert("고객님께서는 일시 정지 신청이 불가합니다. \n고객센터(1899-5000)로 문의를 부탁 드립니다.");
       			isTrue = false;
       		}

       		if(runMode =="U"){
    			MCP.alert("분실신고 상태에서는 일시정지 신청/해제가 불가합니다.\n고객센터(1899-5000)로 문의를 부탁드립니다.");
    			isTrue = false;
    		}

        } else {
        	isTrue = false;
        	MCP.alert("시스템에 이상이 있습니다. 다음에 다시 시도 하여 주시기 바랍니다.");
            $("#ctnStatus").val("");
        	$("#rsltInd").val("");
        }

        if(isTrue){
        	if(auth=="phone"){
        		pop_nice();
        	} else if(auth=="ipin") {
        		pop_ipin();
        	} else if(auth=="credit") {
        		pop_credit();
        	}
        } else {
        	return false;
        }

    });

}


function select(){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/mypage/suspendView01.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}


var isBlankZero = function(input) {

    if (input == undefined || $.trim(input) == "" ) {
        return "0";
    } else {
        return input;
    }
};

var formatDate = function(inputVal) {
	if (inputVal.length >=  8 ) {
		if (inputVal == "99991231") {
			return "-";
		} else {
			return inputVal.substring(0,4)+"."+inputVal.substring(4,6)+"."+inputVal.substring(6,8);  //str.substring(7, 13);
		}
	} else {
		return inputVal;
	}

}

// 인증시작
function pop_nice() {
	ajaxCommon.getItemNoLoading({
			id:'getTimeInMillisAjax'
			,cache:false
			,async:false
			,url:'/nice/getTimeInMillisAjax.do'
			,data: ""
			,dataType:"json"
		}
		,function(startTime){
			pageObj.startTime = startTime ;
		});
	pageObj.niceType = NICE_TYPE.CUST_AUTH ;
	openPage('outLink','/nice/popNiceAuth.do?sAuthType=M&mType=Mobile','');
}


function pop_ipin() {
	ajaxCommon.getItemNoLoading({
			id:'getTimeInMillisAjax'
			,cache:false
			,async:false
			,url:'/nice/getTimeInMillisAjax.do'
			,data: ""
			,dataType:"json"
		}
		,function(startTime){
			pageObj.startTime = startTime ;
		});
	pageObj.niceType = NICE_TYPE.CUST_AUTH ;
	openPage('outLink','/nice/popNiceIpinAuth.do','');
}

function pop_credit() {
	ajaxCommon.getItemNoLoading({
			id:'getTimeInMillisAjax'
			,cache:false
			,async:false
			,url:'/nice/getTimeInMillisAjax.do'
			,data: ""
			,dataType:"json"
		}
		,function(startTime){
			pageObj.startTime = startTime ;
		});
	pageObj.niceType = NICE_TYPE.CUST_AUTH ;
	openPage('outLink','/nice/popNiceAuth.do?sAuthType=C&mType=Mobile','');
}

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


function niceAuthResult(prarObj){

	var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
	pageObj.niceResLogObj = prarObj;

	var authInfoJson ={contractNum:$("#contractNum").val()};
	var isAuthDone = diAuthCallback(authInfoJson);

	if(isAuthDone){
		var subStatus = $("#subStatus").val();
		var url = "";
		if (subStatus == "A") {
			url = "/m/mypage/suspendView02.do";
		} else {
			url = "/m/mypage/suspendCnl.do";
		}
		ajaxCommon.createForm({
			method : "post"
			,action : url
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
		return null;
	}else{

		strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
		MCP.alert(strMsg);
		return null;
	}
}
//인증 끝
