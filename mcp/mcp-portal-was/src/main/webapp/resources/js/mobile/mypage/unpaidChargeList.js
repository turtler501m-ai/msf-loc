var confirm = "자동납부를 이용하시는 경우, </br> 당월 청구금액 납부 시 이중 납부가 될 수 있으니"+ 
	"</br> 납부일 및  계좌/카드 거래내역 확인 후 이용하시기 바랍니다.</br> 요금을 납부하시겠습니까?";
$(document).ready(function (){

	searchChg($("#ctn").val());
	
	// 숫자만 입력가능1
	$(".numOnly").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});
	
	$("#pay").keyup(function(){
		$(this).val(numberWithCommas($(this).val()));
	});

	/* 아이핀 인증 클릭 */
	$("#auth_ipin").on("click", function() {
		if(!payCertify()) {
			return;
		}

		KTM.Confirm(confirm, function (){
			pop_ipin();
			this.close();
		});
	});

	/* 카드 인증 클릭 */
	$("#auth_credit").on("click", function() {

		if(!payCertify()) {
			return;
		}
		KTM.Confirm(confirm, function (){
			pop_credit();
			this.close();
		});
	});

	/* 핸드폰 번호*/
	$("#auth_phone").on("click", function() {

		if(!payCertify()) {
			return;
		}
		
		KTM.Confirm(confirm, function (){
			pop_nice();
			this.close();
		});
	});

	//전액납부버튼
	$("#chkAll").on("change",function(){
		var chk = $("input[name=chkAll]:checked").val();
		var totalCntReal=$("#totalCntReal").val();

		if(totalCntReal == 0){
			MCP.alert('납부하실 금액이 없습니다.', function (){
				$("#pay").focus();
			}); 
			return false;
		} else{

			if(chk){
				$("#pay").prop("disabled" , true);
				$("#pay").val(numberWithCommas($("#totalCntReal").val()));
			}else{
				$("#pay").prop("disabled" , false);
				$("#pay").val('');
			}
		}


	});

});

// 회선 변경 이벤트
function select(){
	ajaxCommon.createForm({
		method:"post"
		,action:"/m/mypage/unpaidChargeList.do"
	});
	ajaxCommon.attachHiddenElement("ncn", $("#ctn").val());
	ajaxCommon.formSubmit();
}

//납부금액 조회
function searchChg(ncn){

	var varData = ajaxCommon.getSerializedData({
    	ncn : ncn
    });

	ajaxCommon.getItem({
            id:'unpaidChargeListAjax'
            ,cache:false
            ,url:'/m/mypage/unpaidChargeListAjax.do'
            ,data: varData
            ,dataType:"json"
     }
     ,function(jsonObj){
		
		var reHtml = '';
		$("#pay").val("");
		$("#chkAll").prop("checked",false);
			
		if(jsonObj.returnCode == "S"){
			if(jsonObj.resultMap.payTgtAmt == '' || jsonObj.resultMap.payTgtAmt == 0){
				$(".chargeView1").hide();
				$("#totalCnt").html("<b>"+jsonObj.resultMap.payTgtAmt+"</b>원");
				$(".unpaidNoDataView").show();
			
			}else {
				var payTgtAmt = jsonObj.resultMap.payTgtAmt;
				var currMthNpayAmt = jsonObj.resultMap.currMthNpayAmt;
				var totNpayAmt = jsonObj.resultMap.totNpayAmt;

				if(currMthNpayAmt != 0){
					reHtml += '<tr>';
					reHtml += '<td>당월금액</td>';
					reHtml += '<td class="u-ta-right">'+numberWithCommas(currMthNpayAmt)+'원</td>';
					reHtml += '</tr>';
				}
				
				if(totNpayAmt != 0){
					reHtml += '<tr>';
					reHtml += '<td>미납금액</td>';
					reHtml += '<td class="u-ta-right">'+numberWithCommas(totNpayAmt)+'원</td>';
					reHtml += '</tr>';
				}
				
			
				$("#realtimePayView").html(reHtml);
				$("#realtimePayHtml").show();
				$("#totalCnt").html("<b>"+numberWithCommas(payTgtAmt) +"</b>원");
				$("#totalCntReal").val(payTgtAmt);
				$(".chargeView1").show();
				$(".unpaidNoDataView").hide();
			}
		
			$("#totalCntView1").show();
			$("#totalCntView2").show();
			$("#customerTypeNoData").hide();

		} else if(jsonObj.returnCode == "01"){
			$("#customerTypeNoData").show();
			$("#realtimePayHtml").hide();
			$(".unpaidNoDataView").hide();
			$(".chargeView1").hide();
			$("#totalCntView1").hide();
			$("#totalCntView2").hide();
		}
 	});
}

var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
}


//납부하기 버튼

var payCertify = function (){
	var totalCntReal=$("#totalCntReal").val();
	var pay = $("#pay").val().replaceAll(",","");

	$("#auth_phone").prop("checked",false);
	$("#auth_credit").prop("checked",false);
	$("#auth_ipin").prop("checked",false);


	if(pay == "" || pay == '0'){
		MCP.alert('납부하실 금액을 입력해 주세요.', function (){
			$("#pay").focus();
		}); 
		return false;
	}


	if(parseFloat(pay) > parseFloat(totalCntReal)){
		MCP.alert('입력한 금액이 납부 가능 금액보다 큽니다.', function (){
			$("#pay").focus();
		}); 
		return false;
	}


	
	return true;
}

function pop_ipin() {
	pageObj.niceType = NICE_TYPE.CUST_AUTH ;
	openPage('outLink','/nice/popNiceIpinAuth.do','');

}

function pop_credit() {
	pageObj.niceType = NICE_TYPE.CUST_AUTH ;
	openPage('outLink','/nice/popNiceAuth.do?sAuthType=C&mType=Mobile','');
}

function pop_nice() {
	pageObj.niceType = NICE_TYPE.CUST_AUTH ;	
	openPage('outLink','/nice/popNiceAuth.do?sAuthType=M&mType=Mobile','');
}

function fnNiceCert(prarObj) {
    niceAuthResult(prarObj);
}


function fnNiceCertErr() {
	return false;
}

function fnNiceIpinCertErr() {
	return false;
}

/* 아이핀 인증 성공 후 객체값 post방식으로 넘김 / 다음 페이지 이동 */
function fnNiceIpinCert(prarObj) {
	niceAuthResult(prarObj);
}


function niceAuthResult(prarObj){

	var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
	pageObj.niceResLogObj = prarObj;

	var authInfoJson ={contractNum:$("#contractNum").val()};
	var isAuthDone = diAuthCallback(authInfoJson);

	if(isAuthDone){
		certSuccessFn();
	}else{
		strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
		MCP.alert(strMsg);
		return null;
	}
}


function certSuccessFn(){

	var strMsg = "고객정보와 본인인증한 정보가 일치하지 않습니다<br>다시 시도 바랍니다.";

	ajaxCommon.getItem({
	   	id:'certSuccessFn'
	    ,cache:false
	    ,url:'/checkNiceCertAjax.do'
	    ,data: ""
	    ,dataType:"json"
	}
    ,function(jsonObj) {
			if (jsonObj.RESULT_CODE == 'S') {
				$("#isAuth").val("1");
				ajaxCommon.createForm({
					method: "post"
					, action: "/m/mypage/realTimePaymentView.do"
				});

				ajaxCommon.attachHiddenElement("payMentMoney", $("#pay").val());
				ajaxCommon.attachHiddenElement("contractNum", $("#contractNum").val());
				ajaxCommon.formSubmit();
			} else {
				MCP.alert(strMsg, function () {
					location.reload();
				});
			}
		});
}

