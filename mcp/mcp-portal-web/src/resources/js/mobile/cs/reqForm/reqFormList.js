$(document).ready(function() {

	initView();
	var pageNo = $("#pageNo").val();
	reqFormListAjax(pageNo);

	$("#moreBtn").click(function(){
		var listCount = Number($("#listCount").text());
		var totalCount = Number($("#totalCount").text());
		if(listCount < totalCount){
			var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),"1"));
			reqFormListAjax(pageNo+1);
			$("#pageNo").val(pageNo+1);
		}
	});

});

var reqFormListAjax = function(pageNo) {

	$("#pageNo").val(pageNo);
	var searchNm = $('#searchNm').val();
	var pageNo = pageNo;

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo,
		searchNm : searchNm
	});

	ajaxCommon.getItem({
		id:'faqListAjax'
		,cache:false
		,url:'/cs/reqFormListAjax.do'
		,data: varData
		,dataType:"json"
		,async:false
	},function(jsonObj){

		var totalCount = jsonObj.totalCount; // 총갯수
		var reqFormList = jsonObj.reqFormList; // 조회데이터
		var listCount = jsonObj.listCount; // 페이징포함 곱한 갯수

		viewDesc(totalCount,reqFormList,listCount);
	});


};

var viewDesc = function(totalCount,reqFormList,listCount){

	var htmlArea = "";

	// top 10
	if(reqFormList !=null && reqFormList.length>0 ){

		var boardSubject =  "";
		var boardContents =  "";
		var boardWriteDt =  "";
		var attSeq =  "";
		var boardSeq = "";
		var htmlArea = "";

		for(var i=0; i<reqFormList.length; i++){

			boardSubject =  reqFormList[i].boardSubject;
			boardContents =  reqFormList[i].boardContents;;
			cretDt =  reqFormList[i].cretDt;;
			attSeq =  reqFormList[i].attSeq;;
			boardSeq = reqFormList[i].boardSeq;
			filePathNM = reqFormList[i].filePathNM;
			filePathNM = "https://www.ktmmobile.com"+filePathNM;
			htmlArea +="<li class='c-list__item'>";
			htmlArea +="	<strong class='c-list__title c-text-ellipsis'>"+boardSubject+"</strong>";
			htmlArea +="	<span class='c-list__desc c-text-ellipsis'>"+boardContents+"</span>";
			htmlArea +="	<span class='c-list__sub'>"+cretDt+"</span>";
			htmlArea +='	<a class="c-button"	href="javascript:fileDownLoad(\''+attSeq +'\',\''+1 +'\',fileDownCallBack,\''+boardSeq +'\');">';
			htmlArea +="		<i class='c-icon c-icon--download' aria-hidden='true'></i>";
			htmlArea +="	</a>";
			htmlArea +="</li>";
		}
		$("#liDataArea").append(htmlArea);
		$(".c-nodata").hide();
		$("#listCount").text(listCount); // 현재 노출
		$("#totalCount").text(totalCount);
		$("#liDataArea").show();
		if(listCount < totalCount){
			$("#addBtnArea").show();
		} else {
			$("#addBtnArea").hide();
		}

	} else {
		$(".c-nodata").show();
		$("#addBtnArea,#liDataArea").hide();
		$("#liDataArea").html("");
	}

}

function initView(){

	$(".c-nodata").hide();
	$("#liDataArea").html("");
	$("#listCount").text("0"); // 현재 노출
	$("#totalCount").text("0");
	$("#addBtnArea,#liDataArea").hide();
}

function searchBtn() {
	var searchNm = $("#searchNm").val();
	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/cs/reqFormList.do"
	});

	ajaxCommon.attachHiddenElement("pageNo",1);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.formSubmit();
}

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

window.onpopstate = function(event) {
	event.preventDefault();
	closeView();
}
