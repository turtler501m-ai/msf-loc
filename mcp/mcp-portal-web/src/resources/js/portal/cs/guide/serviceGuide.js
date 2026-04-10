$(document).ready(function(){

	// 자주묻는 질문 클릭 시 카운트 증가
	$(document).on("click",".faqTop",function(){

		var boardSeq = $(this).attr("boardSeq");
		var doActive = $(this).hasClass("is-active"); // 펼침 버튼일때만
		var $faqTopCtn = $(this).prev().children(".faqTopCtn");

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
            	var boardHitCnt = jsonObj.boardHitCnt;
            	if(cnt > 0){
            		$faqTopCtn.text(boardHitCnt);
            	}
            });
		}
	});

	$(".loding").click(function(){
		KTM.LoadingSpinner.show();
	});


});

