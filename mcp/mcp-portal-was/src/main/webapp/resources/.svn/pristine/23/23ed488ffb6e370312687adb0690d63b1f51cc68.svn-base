var pageObj = {
    niceType:""
    , applDataForm:{}
    , niceHistSeq:0
    , authObj:{}
    , authPassObj:{}
    , insrNiceHistSeq:0
    , niceHistInsrProdSeq:0
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증
    
    , onlineAuthType : ""   //인증타입
 	, onlineAuthInfo : ""   //인증값
 	, cstmrType : ""        //고객타입
}

history.pushState(null, null,"/m/mypage/reqUsimPuk.do");
window.onpopstate = function (event){   	   				
	history.go("/m/mypage/reqUsimPuk.do");
}

$(document).ready(function (){

	//신청버튼 클릭
	$("#btnRequest").click(function(){
		
		if ($("#isAuth").val() != "1") {
            MCP.alert("본인 인증이 완료되지 않았습니다.");
            return false;
        }
       	
		// 에러 문구 제거 
		$(".c-form__text").remove();
    	$(".has-error").removeClass("has-error");
    	
		pageObj.applDataForm["reqType"] = "UP";
		pageObj.applDataForm["onlineAuthType"] = pageObj.onlineAuthType;
		//pageObj.applDataForm["onlineAuthInfo"] = pageObj.onlineAuthInfo;
		pageObj.applDataForm.reqSeq = pageObj.niceResLogObj.reqSeq;
		pageObj.applDataForm.resSeq = pageObj.niceResLogObj.resSeq;
		pageObj.applDataForm["contractNum"] = $("#contractNum").val(); //계약번호

		KTM.Confirm('열람신청 하시겠습니까?', function() {
			
			KTM.Dialog.closeAll(); //모달닫기
			
			var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);
			
			ajaxCommon.getItem({
	            id:'custRequestUsimPuk'
	            ,cache:false
	            ,url:'/mypage/custRequestAjax.do'
	            ,data: varData
	            ,dataType:"json"
		        ,async:false
	         }
	         ,function(data){
	        	 if(data.RESULT_CODE == "SUCCESS"){
					//PUK번호 보여주기
					MCP.alert("고객님의 PUK번호는 " + data.USIMPUK + " 입니다.");
				 }else if(data.RESULT_CODE == 'AUTH01'){ // AUTH 오류
					 MCP.alert(data.RESULT_MSG);
				 }else if(data.RESULT_CODE == 'STEP01' || data.RESULT_CODE == 'STEP02'){ // STEP 오류
					 MCP.alert(data.RESULT_MSG);
				 }else {
					MCP.alert(data.RESULT_CODE + " " + data.RESULT_MSG);	
				 }
	    	 });
    	});
	});
});

//본인인증 콜백
var fnNiceCert = function(prarObj) {

    var strMsg= "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;

    //본인인증
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {            
        
		var authInfoJson= {contractNum: $("#contractNum").val(), authType: prarObj.authType};
		var isAuthDone = mypageAuthCallback(authInfoJson);

		// 성공
		if(isAuthDone){
			//현재 페이지의 authObj에 prarObj 넣기
			pageObj.authObj = prarObj;
			//인증타입
			pageObj.onlineAuthType = prarObj.authType;
			//메시지 띄우기
			MCP.alert("본인인증에 성공 하였습니다.");
			//신청버튼 활성화
			$("#btnRequest").prop("disabled", false);
			return null;
		//실패
		}else{

			var resultCd= niceAuthObj.resAuthOjb.RESULT_CODE;
			var errorMsg= niceAuthObj.resAuthOjb.RESULT_MSG;

			if (resultCd == "LOGIN") {
				MCP.alert(errorMsg, function () {
					location.href = '/m/loginForm.do';
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
var viewErrorMgs = function($thatObj, msg) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};