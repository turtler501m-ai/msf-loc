$(document).ready(function() {
//	var window = $(window);
//	window.onpageshow = function(event) {
//		alert("asd");
//	    if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
//	    	history.go(-1);
//	    }
//	}

	$(".selMenu").each(function(){
		var hasClass = $(this).hasClass("is-active");
		if(hasClass){
			$(this).append("<span class='c-hidden'>선택됨</span>");
		} else {
			$(this).children("span").remove();
		}
	});

	// 메뉴선택
	$(".selMenu").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/cs/faqList.do"
		});

		var searchNm = $("#searchNm").val().trim();
		var sbstCtg = $(this).attr("sbstCtg");

		ajaxCommon.attachHiddenElement("searchNm",searchNm);
		ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
		ajaxCommon.attachHiddenElement("pageNo",1);
		ajaxCommon.formSubmit();
	});

	// 조회 카운트 증가
	$(document).on("click",".faq",function(){

		var boardSeq = $(this).attr("boardSeq");
		var doActive = $(this).hasClass("is-active"); // 펼침 버튼일때만
		var $faqTopCtn = $(this).prev().children(".faqTopCtn");
		if(doActive){
			var varData = ajaxCommon.getSerializedData({
				boardSeq:boardSeq
            });

            ajaxCommon.getItem({
                id:'updateFaqHitAjax'
                ,cache:false
                ,url:'/m/cs/updateFaqHitAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
            	var updateResult = jsonObj.updateResult;
            	var boardHitCnt = jsonObj.boardHitCnt;
            	if(updateResult> 0){
            		$faqTopCtn.text(boardHitCnt);
            	}

            });
		}
	});

});


function searchBtn() {
	goPaging(1);
}

function goPaging(pageNo){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/cs/faqList.do"
	});

	var sbstCtg = "00";
	$(".selMenu").each(function(){
		if($(this).hasClass("is-active")){
			sbstCtg = $(this).attr("sbstCtg");
		}
	});
	var searchNm = $("#searchNm").val().trim();
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
	ajaxCommon.attachHiddenElement("pageNo",pageNo);

	ajaxCommon.formSubmit();

}

