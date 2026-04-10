function dormancyRelease() {
	$.ajax({
        type : 'POST',
        url : '/m/updateDormantUserChgAjax.do',
        data : '',
        dataType : 'json',
        contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
        async : false,
        success : function(data) {
            if(data.resltCd == '0000') {
				MCP.alert('휴면회원 해제가 완료되었습니다.', function (){
					location.href = '/m/loginForm.do';
				});
	            return;
            } else {

                MCP.alert(data.msg, function(){
                    location.reload();  // 새로고침
                });
            }
        },
        error : function() {
            MCP.alert('처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.', function(){
                location.reload();  // 새로고침
            });
            return;
        }

    });
}




var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
}

function niceAuthResult(prarObj){

    var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;

    var authInfoJson ={contractNum: ""};
    var isAuthDone = diAuthCallback(authInfoJson);

    if(isAuthDone){
        pageObj.niceHistSeq = diAuthObj.resAuthOjb.NICE_HIST_SEQ;
        dormancyRelease();
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
