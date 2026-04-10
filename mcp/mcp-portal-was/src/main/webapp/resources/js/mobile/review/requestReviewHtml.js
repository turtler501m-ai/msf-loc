;

$(document).ready(function(){

	var pageNo = ajaxCommon.isNullNvl($("#reViewPageNo").val(),"1");
	initreViewData(pageNo);

	$(document).on("click",".viewMore",function(){
		var foldArea = $(this).parents(".foldArea");
		var orgnArea = foldArea.next(".orgnArea");
		foldArea.hide();
		orgnArea.show();
	});

	$(document).on("click",".viewFold",function(){
		var orgnArea = $(this).parents(".orgnArea");
		var foldArea = orgnArea.prev(".foldArea");
		foldArea.show();
		orgnArea.hide();
	});

	// 더보기
	$(document).on("click","#moreBtn",function(){

		var reViewCurrent = Number($(".review_fold").length);
		var reViewTotal = Number($("#reViewTotal").text());
		if(reViewCurrent >= reViewTotal){
			alert("마지막 페이지 입니다.");
			return false;
		} else {
			var pageNo = Number(ajaxCommon.isNullNvl($("#reViewPageNo").val(),"1"));
			initreViewData(pageNo+1);
			$("#reViewPageNo").val(pageNo+1);
			$(window).scrollTop($("#moreBtn").offset().top-800);
		}
	});


});


var initreViewData = function(pageNo){
	var reqBuyType = $("#reqBuyType").val();
	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo,
		reqBuyType : reqBuyType
	});

	ajaxCommon.getItem({
		id:'reviewDataAjax'
		,cache:false
		,url:'/m/requestReView/reviewDataAjax.do'
		,data: varData
		,dataType:"json"
		,async:false
	},function(jsonObj){

		var total = jsonObj.total;
		var requestReviewDtoList = jsonObj.requestReviewDtoList;
		viewDesc(total,requestReviewDtoList);
	});

}


var viewDesc = function(total,requestReviewDtoList){

	var htmlArea = "";
	if(requestReviewDtoList !=null && requestReviewDtoList.length>0){
		var reviewImgList = "";
		var regNm = "";
		var reqBuyTypeNm = "";
		var eventCd = "";
		var eventNm = "";
		var sysRdateDd = "";
		var reviewSbst = "";
		var snsInfo = "";
		var commendYn = ""; // 추천,비추천
		var ntfYn = ""; // best

		for( var i=0; i<requestReviewDtoList.length; i++ ){
			reviewImgList = requestReviewDtoList[i].reviewImgList;
			regNm = requestReviewDtoList[i].regNm;
			reqBuyTypeNm = requestReviewDtoList[i].reqBuyTypeNm;
			eventCd = requestReviewDtoList[i].eventCd;
			eventNm = requestReviewDtoList[i].eventNm;
			sysRdateDd = requestReviewDtoList[i].sysRdateDd;
			reviewSbst = requestReviewDtoList[i].reviewSbst;
			snsInfo = ajaxCommon.isNullNvl(requestReviewDtoList[i].snsInfo,"");
			commendYn = requestReviewDtoList[i].commendYn;
			ntfYn = requestReviewDtoList[i].ntfYn;

			htmlArea += "<div class='review_fold foldArea'>";
			htmlArea += "	<div class='review_text'>";
			htmlArea += "		<div class='top'>";
			if(commendYn =="1"){
				htmlArea += "			<img src='/resources/images/requestReview/icon_recommend.jpg' class='up' alt=''>";
			} else {
				htmlArea += "			<img src='/resources/images/requestReview/icon_non_recommend.jpg' class='down' alt=''>";
			}
			if(ntfYn =="1"){
				htmlArea += "			<img src='/resources/images/requestReview/icon_best.jpg' class='margin_right_10 up' alt=''>";
			}
			htmlArea += "		</div>";
			htmlArea += "		<p>";
			htmlArea += 			regNm+" 님/" +reqBuyTypeNm+"/"+eventNm+"/"+sysRdateDd;
			htmlArea += "		</p>";
			htmlArea += "		<div class='cont ellipsis2'>"+reviewSbst+"</div>";
			htmlArea += "		<a href='javascript:;' class='viewMore'><span class='moreBtn'>더보기</span></a>";
			htmlArea += "	</div>";
			htmlArea += "	<div class='review_photo'>";
			if(reviewImgList !=null && reviewImgList.length>0){
				htmlArea += "			<a href='javascript:;' class='viewMore'>";
				htmlArea += "				<span>"+reviewImgList.length+"</span>";
				htmlArea += "				<img src='"+reviewImgList[0].filePathNm+"' class='imgTag' alt=''>";
				htmlArea += "			</a>";
			}
			htmlArea += "	</div>";
			htmlArea += "</div>";

			htmlArea += "<div class='review_orgn orgnArea' style='display:none;'>";
			htmlArea += "	<div class='review_text'>";
			htmlArea += "		<div class='top'>";
			if(commendYn =="1"){
				htmlArea += "			<img src='/resources/images/requestReview/icon_recommend.jpg' class='up' alt=''>";
			} else {
				htmlArea += "			<img src='/resources/images/requestReview/icon_non_recommend.jpg' class='down' alt=''>";
			}
			if(ntfYn =="1"){
				htmlArea += "			<img src='/resources/images/requestReview/icon_best.jpg' class='margin_right_10 up' alt=''>";
			}
			htmlArea += "		</div>";
			htmlArea += "		<p>";
			htmlArea += 			regNm+" 님/" +reqBuyTypeNm+"/"+eventNm+"/"+sysRdateDd;
			htmlArea += "		</p>";
			htmlArea += "		<div class='cont'>"+reviewSbst+"</div>";
			if(snsInfo !=""){
				htmlArea += "		<div class='sns'>";
				htmlArea += "			<span>SNS 공유 URL</span>";
				if(snsInfo.indexOf("http") > -1){
					htmlArea += "		<a href='"+snsInfo+"' target='_black' class='snslink'>"+snsInfo+"</a>";
				} else {
					htmlArea += "		<a href='https://"+snsInfo+"' target='_black' class='snslink'>"+snsInfo+"</a>";
				}
				htmlArea += "		</div>";
			}
			htmlArea += "		<a href='javascript:;' class='viewFold'><span class='fold'>접기</span></a>";
			htmlArea += "	</div>";

			htmlArea += "	<div class='review_photo'>";
			if(reviewImgList !=null && reviewImgList.length>0){
				for(var j=0; j<reviewImgList.length; j++){
					htmlArea += "<img src='"+reviewImgList[j].filePathNm+"' class='imgTag' alt=''>";
				}
			}
			htmlArea += "	</div>";
			htmlArea += "</div>";
		}
		$("#moreBtn,#space").show();
	} else {
		$("#moreBtn,#space").hide();
		htmlArea += "<div class='default'>아직 작성된 사용후기가 없습니다.</div>";
	}
	// append
	$("#requestReview_board").append(htmlArea);

	// 더보기
	var reViewCurrent = $(".review_fold").length;
	$("#reViewCurrent").text(reViewCurrent); // 현재 노출
	$("#reViewTotal").text(total);

	$(".imgTag").each(function(){
		$(this).error(function(){
			$(this).unbind("error");
			$(this).attr("src","/resources/images/requestReview/noimage.png");
		});
	});

}