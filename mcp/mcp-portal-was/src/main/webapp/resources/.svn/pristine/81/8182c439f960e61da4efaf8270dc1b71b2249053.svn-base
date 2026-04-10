$(document).ready(function(){

	// 자주묻는 질문 클릭 시 카운트 증가
	$(document).on("click",".faqTop",function(){

		var boardSeq = $(this).attr("boardSeq");
		var doActive = $(this).children(".c-accordion__head").hasClass("is-active"); // 펼침 버튼일때만
		if(doActive){
			var varData = ajaxCommon.getSerializedData({
				boardSeq:boardSeq
            });

            ajaxCommon.getItem({
                id:'serviceGuideCntAjax'
                ,cache:false
                ,url:'/cs/serviceGuideCntAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
            	var cnt = jsonObj.cnt;
            });
		}
	});

	$(".loding").click(function(){
		KTM.LoadingSpinner.show();
	});

});

