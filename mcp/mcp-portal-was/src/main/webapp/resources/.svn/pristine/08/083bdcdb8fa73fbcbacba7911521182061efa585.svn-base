/**
 *
 */
$(document).ready(function(){

	$("#Fbill02").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/mypage/billWayChgView.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});

	$(".pop").click(function(){
		var thisMonth = $(this).attr("thisMonth");
		var parameterData = ajaxCommon.getSerializedData({
			ncn : $("#ctn option:selected").val(),
			thisMonth : thisMonth
		});
		openPage('pullPopupByPost','/mypage/billWayReSendPop.do',parameterData);
	});


});

function select(){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/mypage/billWayReSend.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}
