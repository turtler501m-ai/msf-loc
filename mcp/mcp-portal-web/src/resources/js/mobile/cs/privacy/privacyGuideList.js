$(document).ready(function(){


	privacyEtcBoardListAjax($(".midTab").eq(0).attr("dtlCd"),1);
	privacyEtcBoardListAjax($(".midTab").eq(1).attr("dtlCd"),2);
	privacyEtcBoardListAjax($(".midTab").eq(2).attr("dtlCd"),3);
	privacyEtcBoardListAjax($(".midTab").eq(3).attr("dtlCd"),4);

	$("#tab1").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/cs/privacyBoardList.do"
		});
		ajaxCommon.formSubmit();
	});
});

var privacyEtcBoardListAjax = function(sbstCtg,tab) {


	var varData = ajaxCommon.getSerializedData({
		sbstCtg : sbstCtg,
		rownum : tab
	}); // 있는 필드 쓰자

	ajaxCommon.getItem({
		id:'privacyEtcListAjax'
		,cache:false
		,url:'/m/cs/privacyGuideHtml.do'
		,data: varData
		,dataType:"html"
		,async:false
	},function(jsonObj){

		if(tab=="1"){
			$("#tabB1-panel").html(jsonObj);
		} else if(tab=="2"){
			$("#tabB2-panel").html(jsonObj);
		} else if(tab=="3"){
			$("#tabB3-panel").html(jsonObj);
		} else if(tab=="4"){
			$("#tabB4-panel").html(jsonObj);
		}

	});

};