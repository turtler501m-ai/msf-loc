

$(document).ready(function (){

	window.onpopstate = function (event){
		var ncn = $("#ncn").val();
		var state = { 'ncn': ncn};
		history.pushState(state, null,location.href);
		history.go("/mypage/suspendView01.do");
	}

	$("#completeBtn").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/mypage/suspendView01.do"
		});
		var ncn = $("#ncn").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();

	});

});
