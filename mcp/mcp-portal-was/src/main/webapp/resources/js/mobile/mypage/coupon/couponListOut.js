$(document).ready(function(){

	$("#ctnRegBtn").click(function(){
		var ncn = $("#ctn option:selected").val();
		var parameterData = ajaxCommon.getSerializedData({
			ncn : ncn
		});
		openPage('pullPopupByPost','/m/mypage/couponRegPop.do',parameterData);
	});

	$("#regPop").click(function(){
		var radio = $("input:radio[name=coupSerialNo]");
		var coupSerialNo = "";
		radio.each(function(){
			var chk = $(this).prop("checked");
			if(chk){
				coupSerialNo = $(this).val();
			}
		});
		var ncn = $("#ctn option:selected").val();
		coupSerialNo = coupSerialNo.replace(/\-/g,'').toUpperCase();
		if(coupSerialNo==""){
			MCP.alert("사용하실 쿠폰을 선택해 주세요.");
			return false;
		}
		var parameterData = ajaxCommon.getSerializedData({
			coupSerialNo : coupSerialNo,
			ncn : ncn
		});
		openPage('pullPopupByPost','/m/mypage/couponDtlRegPop.do',parameterData);
	});

	$("#tabM").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/mypage/couponList.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});
	
	$("#tab2").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/mypage/couponUsedDataList.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});


});

function select(){
	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/mypage/couponListOut.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}