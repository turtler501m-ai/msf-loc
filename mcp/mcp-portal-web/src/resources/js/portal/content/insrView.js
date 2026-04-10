/*
history.pushState(null, null, location.href);
window.onpopstate = function (event){
	location.href = "/content/insrView.do";
}*/

$(document).ready(function (){

	//휴대폰 안심보험 소개 html
	ajaxCommon.getItem({
	    url: "/termsPop.do",
	    type: "GET",
	    dataType: "json",
	    data: "cdGroupId1=INFO&cdGroupId2=COMB00004",
	    async: false
	}
    ,function(data){
		var inHtml = unescapeHtml(data.docContent);
		$('#main-content').html(inHtml);

		//로그인 여부로 신청하기 버튼 문구 변경 (20230524)
		if($("input[name=loginYn]").val() === "Y"){
			$("#goReqInsr a").html("휴대폰 안심보험 가입하기");
		}

		//안심보험 청구방법 html
		ajaxCommon.getItem({
		    url: "/termsPop.do",
		    type: "GET",
		    dataType: "json",
		    data: "cdGroupId1=INFO&cdGroupId2=COMB00006",
		    async: false
		}
	    ,function(data){
			var inHtml = unescapeHtml(data.docContent);
			$('#insrGuide').html(inHtml);
			window.KTM.initialize(); //innerHtml이 먼저 로딩되어 KTM.ui.js를 한번 더 호출
		});


	});
});

var fileDownCallBack = function(boardSeq) {

    var varData = ajaxCommon.getSerializedData({
        boardSeq : boardSeq
    });
    ajaxCommon.getItem({
        id:'reqFormDownloadCountUpdate'
        ,cache:false
        ,url:'/cs/reqFormDownloadCountUpdateAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(){});
};