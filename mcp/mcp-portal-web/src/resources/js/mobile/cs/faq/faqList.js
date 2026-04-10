$(document).ready(function() {

	initView();
	faqListAjax(1);

	$("#moreBtn").click(function(){
		var listCount = Number($("#listCount").text());
		var totalCount = Number($("#totalCount").text());
		if(listCount < totalCount){
			var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),1));
			faqListAjax(pageNo+1);
			$("#pageNo").val(pageNo+1);
		}
	});

	// 메뉴선택
	$(".selMenu").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/cs/faqList.do"
		});

		var sbstCtg = $(this).attr("sbstCtg");
		var searchNm = $("#searchNm").val().trim();

		ajaxCommon.attachHiddenElement("searchNm",searchNm);
		ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
		ajaxCommon.attachHiddenElement("pageNo",1);
		ajaxCommon.formSubmit();
	});

	// 조회 카운트 증가
	$(document).on("click",".faq",function(){

		var boardSeq = $(this).attr("boardSeq");
		var doActive = $(this).parent().hasClass("is-active"); // 펼침 버튼일때만
		var $faqTopCtn = $(this).find(".faqTopCtn");
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

var faqListAjax = function(pageNo) {

	$("#pageNo").val(pageNo);
	var searchNm = $('#searchNm').val();
	var sbstCtg = "00";
	var title = "전체";
	$(".selMenu").each(function(){
		if($(this).hasClass("is-active")){
			sbstCtg = $(this).attr("sbstCtg");
			title = $(this).text();
		}
	});
	$("#title").text(title);

	var pageNo = pageNo;
	if(sbstCtg=="100"){
		var len = ajaxCommon.isNullNvl($(".top10").length,0);
		if(len == 0){
			$(".c-nodata").show();
		}
		return false;
	}

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo,
		searchNm : searchNm,
		sbstCtg : sbstCtg
	});

	ajaxCommon.getItem({
		id:'faqListAjax'
		,cache:false
		,url:'/m/cs/faqListAjax.do'
		,data: varData
		,dataType:"json"
		,async:false
	},function(jsonObj){

		var totalCount = jsonObj.totalCount; // 총갯수
		var faqList = jsonObj.faqList; // 조회데이터
		var listCount = jsonObj.listCount; // 페이징포함 곱한 갯수
		viewDesc(totalCount,faqList,listCount,sbstCtg);
//		MCP.addAccordion2('#liDataArea');
		var el = document.querySelector('#liDataArea');
		var instance = KTM.Accordion.getInstance(el) ? KTM.Accordion.getInstance(el) : new KTM.Accordion(el);
		instance.update();
	});
};


var viewDesc = function(totalCount,faqList,listCount,sbstCtg){

	var htmlArea = "";
	if(faqList !=null && faqList.length>0 ){

		var boardSeq =  "";
		var sbstCtg =  "";
		var boardSubject =  "";
		var boardHitCnt =  "";
		var boardContents = "";
		var htmlArea = "";

		for(var i=0; i<faqList.length; i++){

			boardSeq =  faqList[i].boardSeq;
			sbstCtg =  faqList[i].sbstCtg;;
			boardSubject =  faqList[i].boardSubject;;
			boardHitCnt =  faqList[i].boardHitCnt;;
			boardContents = faqList[i].boardContents;
			rownum = faqList[i].rownum;

			htmlArea += "<li class='c-accordion__item' >";
			htmlArea += "	<div class='c-accordion__head is-qtype' data-acc-header>";
			htmlArea += "		<span class='p-prefix'>Q</span>";
			htmlArea += "		<button class='c-accordion__button faq' type='button' aria-expanded='false' boardSeq='"+boardSeq+"' >";
			htmlArea += "			<div class='c-accordion__title'>"+boardSubject;
			htmlArea += "				<div class='info-line'>";
			htmlArea += "					<span class='info-item'>";
			htmlArea += "						<span>조회</span>";
			htmlArea += "						<span class='with-div faqTopCtn'>"+boardHitCnt+"</span>";
			htmlArea += "					</span>";
			htmlArea += "				</div>";
			htmlArea += "			</div>";
			htmlArea += "		</button>";
			htmlArea += "	</div>";
			htmlArea += "	<div class='c-accordion__panel c-expand expand'>";
			htmlArea += "		<div class='c-accordion__inside box-answer'>";
			htmlArea += "			<div class='box-prefix'>A</div>";
			htmlArea += "			<div class='box-content'>"+boardContents+"</div>";
			htmlArea += "		</div>";
			htmlArea += "	</div>";
			htmlArea += "</li>";
		}

		$("#liDataArea").append(htmlArea);
		$("#dataArea").show();
		$(".c-nodata").hide();
		$("#listCount").text(numberWithCommas(listCount));
		$("#totalCount").text(numberWithCommas(totalCount));

		if(totalCount > listCount){
			$("#addBtnArea").show();
		} else {
			$("#addBtnArea").hide();
		}
	} else {

		$("#liDataArea").html("");
		$("#dataArea").hide();
		$("#addBtnArea").hide();
		var len = ajaxCommon.isNullNvl($(".top10").length,0);
		if(len == 0){
			$(".c-nodata").show();
		}
		$("#listCount").text(len);
		$("#totalCount").text(len);
	}

}


function initView(){

	$("#liDataArea").html("");
	$("#dataArea").hide();
	$("#addBtnArea").hide();
	$(".c-nodata").hide();
	$("#listCount").text("0");
	$("#totalCount").text("0");
	$("#title").text("");
}

function searchBtn() {

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/cs/faqList.do"
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
	ajaxCommon.attachHiddenElement("pageNo",1);
	ajaxCommon.formSubmit();
}

window.onpopstate = function(event) {
	event.preventDefault();
	closeView();
}
