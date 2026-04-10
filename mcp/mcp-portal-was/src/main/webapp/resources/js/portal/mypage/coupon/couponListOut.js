$(document).ready(function(){

	$("#ctnRegBtn").click(function(){
		var ncn = $("#ctn option:selected").val();
		var parameterData = ajaxCommon.getSerializedData({
			ncn : ncn
		});
		openPage('pullPopupByPost','/mypage/couponRegPop.do',parameterData);
	});

	$(".regPop").click(function(){
		var coupSerialNo = $(this).parent().prevAll().children(".coupSerialNo").find("option:selected").val();
		coupSerialNo = coupSerialNo.replace(/\-/g,'').toUpperCase();
		var ncn = $("#ctn option:selected").val();
		if(coupSerialNo==""){
			MCP.alert("사용가능한 쿠폰번호가 없습니다.");
			return false;
		}
		var parameterData = ajaxCommon.getSerializedData({
			coupSerialNo : coupSerialNo,
			ncn : ncn
		});
		openPage('pullPopupByPost','/mypage/couponDtlRegPop.do',parameterData);
	});

	$("#tab2").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/mypage/couponUsedDataList.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});


});

function select(){
	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/mypage/couponListOut.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}