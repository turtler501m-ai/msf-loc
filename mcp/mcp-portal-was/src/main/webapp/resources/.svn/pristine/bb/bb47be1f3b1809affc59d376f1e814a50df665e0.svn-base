VALIDATE_NOT_EMPTY_MSG.faxNum = "팩스번호를 입력해 주세요.";
VALIDATE_COMPARE_MSG.faxNum = "팩스번호가 형식에 맞지 않습니다.";

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

history.pushState(null, null,"/m/mypage/reqJoinForm.do");
window.onpopstate = function (event){   	   				
	history.go("/m/mypage/reqJoinForm.do");
}

$(document).ready(function (){

	//수신방법-팩스 선택시
	$("#rdFx").on("click", function(){
	    $("#fax").show();
	    $("#Address").hide();
	    $("#cstmrPost").val("");
	    $("#cstmrAddr").val("");
	    $("#cstmrAddrDtl").val("");
	});
	
	//수신방법-등기 선택시
	$("#rdPt").on("click", function(){
	    $("#fax").hide();
	    $("#Address").show();
	    $("#faxNum").val("");
	});
    
    //우편번호 검색
	$("._btnAddr").click(function() {    
	    openPage('pullPopup', '/m/addPopup.do','','1')
	});
	
	//신청버튼 클릭
	$("#btnRequest").click(function(){
		
		if ($("#isAuth").val() != "1") {
            MCP.alert("본인 인증이 완료되지 않았습니다.");
            return false;
        }
       	
		// 에러 문구 제거 
		$(".c-form__text").remove();
    	$(".has-error").removeClass("has-error");
    	
    	validator.config = {};
    	//수신방법
		if($("input:radio[name=rdRecvType]:checked").val() == "FX"){
			validator.config['faxNum'] = 'isNonEmpty';
			validator.config['faxNum'] = 'isTel';
		}else {
			validator.config['cstmrPost'] = 'isNonEmpty';
	        validator.config['cstmrAddr'] = 'isNonEmpty';
	        validator.config['cstmrAddrDtl'] = 'isNonEmpty';
		}
		
		//유효성 검사 성공하면
		if(validator.validate()){

			pageObj.applDataForm["reqType"] = "JF";
			pageObj.applDataForm["cstmrType"] = pageObj.cstmrType;
			pageObj.applDataForm["onlineAuthType"] = pageObj.onlineAuthType;
			//pageObj.applDataForm["onlineAuthInfo"] = pageObj.onlineAuthInfo;
			pageObj.applDataForm["reqSeq"] = pageObj.niceResLogObj.reqSeq;
			pageObj.applDataForm["resSeq"] = pageObj.niceResLogObj.resSeq;
			pageObj.applDataForm["faxNo"] = $.trim($("#faxNum").val());
			pageObj.applDataForm["cstmrPost"] = $.trim($("#cstmrPost").val());
			pageObj.applDataForm["cstmrAddr"] = $.trim($("#cstmrAddr").val());
			pageObj.applDataForm["cstmrAddrDtl"] = $.trim($("#cstmrAddrDtl").val());
			pageObj.applDataForm["recvType"] = $("input:radio[name=rdRecvType]:checked").val();
			pageObj.applDataForm["contractNum"] = $("#contractNum").val();

			KTM.Confirm('가입신청서 출력요청을 하시겠습니까?', function() {
				
				KTM.Dialog.closeAll(); //모달닫기
				
				var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);
				
				ajaxCommon.getItem({
		            id:'custRequestJoinForm'
		            ,cache:false
		            ,url:'/mypage/custRequestAjax.do'
		            ,data: varData
		            ,dataType:"json"
			        ,async:false
		         }
		         ,function(data){
		        	 if(data.RESULT_CODE == 'SUCCESS'){
						MCP.alert("신청이 완료되었습니다.", function (){
			        		location.href = "/m/main.do";
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
			pageObj.authObj = prarObj; //현재 페이지의 authObj에 prarObj 넣기
			pageObj.onlineAuthType = prarObj.authType; //인증타입
			MCP.alert("본인인증에 성공 하였습니다."); //메시지 띄우기
			$("#requestForm").show(); //입력폼 보이기
			$("#btnRequest").prop("disabled", false); //신청버튼 활성화
			return null;
		// 실패
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

	
//주소콜백(주소setting)
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
    $("#cstmrPost").val(zipNo);
    $("#cstmrAddr").val(roadAddrPart1);
    $("#cstmrAddrDtl").val(roadAddrPart2 + " " + addrDetail);
    $("#cstmrAddr").parent().addClass('has-value');
}

//회선 조회
function select(){
	document.selfFrm.ncn.value = $("#ctn").val();
	document.selfFrm.submit();
}

//포커스 자동옮기기
function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

//에러메시지 보여주기
var viewErrorMgs = function($thatObj, msg) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};
