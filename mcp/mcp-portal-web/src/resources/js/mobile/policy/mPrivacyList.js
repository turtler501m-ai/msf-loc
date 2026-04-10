$(document).ready(function() {

	initView();
	var pageNo = $("#pageNo").val();
	mPrivacyListAjax(pageNo);

	$("#moreBtn").click(function(){
		var listCount = Number($("#listCount").text());
		var totalCount = Number($("#totalCount").text());
		if(listCount < totalCount){
			var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),"1"));
			mPrivacyListAjax(pageNo+1);
			$("#pageNo").val(pageNo+1);
		}
	});

});


var mPrivacyListAjax = function(pageNo) {

	var pageNo = pageNo;
	var stpltCtgCd = $("#stpltCtgCd").val();

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo,
		stpltCtgCd : stpltCtgCd
	});

	ajaxCommon.getItem({
		id:'mPrivacyListAjax'
		,cache:false
		,url:'/policy/mPrivacyListAjax.do'
		,data: varData
		,dataType:"json"
		,async:false
	},function(jsonObj){

		var totalCount = jsonObj.totalCount; // 총갯수
		var policyDtoList = jsonObj.policyDtoList; // 조회데이터
		var listCount = jsonObj.listCount; // 페이징포함 곱한 갯수
		var pageNo = jsonObj.pageNo;
		$("#pageNo").val(pageNo);
		viewDesc(totalCount,policyDtoList,listCount);
	});


};

var viewDesc = function(totalCount,policyDtoList,listCount){

	var htmlArea = "";
	if(policyDtoList !=null && policyDtoList.length>0){

		var stpltSeq =  "";
		var stpltCtgNm =  "";
		var stpltTitle =  "";
		var cretDt =  "";

		for( var i=0; i<policyDtoList.length; i++ ){

			stpltSeq = policyDtoList[i].stpltSeq;
			stpltCtgNm = policyDtoList[i].stpltCtgNm;
			stpltTitle = policyDtoList[i].stpltTitle;
			cretDt = policyDtoList[i].cretDt;

			htmlArea += "<li class='c-list__item'>";
			htmlArea += "	<a class='c-list__anchor' href='javascript:goDetail("+stpltSeq+")'>";
			htmlArea += "		<span class='c-list__desc c-text-ellipsis u-mt--0'>"+stpltCtgNm+"</span>";
			htmlArea += "		<strong class='c-list__title c-text-ellipsis u-mt--8'>"+stpltTitle+"</strong>";
			htmlArea += "		<span class='c-list__sub'>"+cretDt+"</span>";
			htmlArea += "	</a>";
			htmlArea += "</li>";
		}
		$("#moreBtn").show();
		$("#listCount").text(listCount); // 현재 노출
		$("#totalCount").text(totalCount);
		if(totalCount > listCount){
			$("#moreBtn").show();
		} else {
			$("#moreBtn").hide();
		}
	} else {
		$("#moreBtn").hide();
		htmlArea += "<div class='c-nodata'>";
		htmlArea += "	<i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
		htmlArea += "	<p class='c-nodata__text'>등록된 게시물이 없습니다.</p>";
		htmlArea += "</div>";
	}

	// append
	$("#dataArea").append(htmlArea);

}

function initView(){
	$("#dataArea").html("");
	$("#listCount").text("0"); // 현재 노출
	$("#totalCount").text("0");
	$("#moreBtn").hide();
}


function goDetail(stpltSeq) {

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/policy/mPrivacyView.do"
	});
	var pageNo = $("#pageNo").val();
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("stpltSeq",stpltSeq);
	ajaxCommon.formSubmit();

};

window.onpopstate = function(event) {
	event.preventDefault();
	closeView();
}