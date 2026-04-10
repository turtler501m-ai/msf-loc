$(document).ready(function(){
	alert("");
	initView();
	var pageNo = $("#pageNo").val();

	var isHist = $("#isHist").val();
	if(isHist=="Y"){
		inquiryListAjax(pageNo);
	}

	// 더보기
	$("#moreBtn").click(function(){
		var listCount = Number($("#listCount").text());
		var totalCount = Number($("#totalCount").text());
		if(listCount >= totalCount){
			alert("마지막 페이지 입니다.");
			return false;
		} else {
			var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),"1"));
			inquiryListAjax(pageNo+1);
			$("#pageNo").val(pageNo+1);
		}
	});

});

//약관동의
function btnRegDtl(param){
	openPage('termsPop','/termsPop.do',param);
}

function targetTermsAgree() {
	$("#chkA1").prop("checked",true);
}


var inquiryListAjax = function(pageNo) {

	$("#pageNo").val(pageNo);
	var pageNo = pageNo;

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo
	});

	ajaxCommon.getItem({
		id:'selectQnaListAjax'
		,cache:false
		,url:'/m/mypage/myCsInquiryList.do'
		,data: varData
		,dataType:"json"
		,async:false
	},function(jsonObj){

		var totalCount = jsonObj.totalCount; // 총갯수
		var inquiryList = jsonObj.inquiryList; // 조회데이터
		var listCount = jsonObj.listCount; // 페이징포함 곱한 갯수

		viewDesc(totalCount,inquiryList,listCount);
	});
};


var viewDesc = function(totalCount,inquiryList,listCount){

	var htmlArea = "";
	if(inquiryList !=null && inquiryList.length>0 ){

		var ansStatVal =  "";
		var ansClass = "";
		var anstitle = "";

		var qnaSubject = "";
		var qnaContent = "";
		var qnaSubCtgNm = "";
		var amdDT = "";
		var ansSubject = "";
		var ansContent = "";
		var mobileNo = "";

		for(var i=0; i<inquiryList.length; i++){

			ansStatVal =  inquiryList[i].ansStatVal;
			if(ansStatVal=="2"){
				ansClass = "is-answered";
				anstitle = "답변완료";
			} else {
				ansClass = "is-registerd";
				anstitle = "접수중";
			}
			qnaSubject = inquiryList[i].qnaSubject;
			qnaContent =  inquiryList[i].qnaContent;
			qnaSubCtgNm = inquiryList[i].qnaSubCtgNm;
			cretDT =  inquiryList[i].cretDT;
			ansSubject =  inquiryList[i].ansSubject;
			ansContent = inquiryList[i].ansContent;
			mobileNo = inquiryList[i].mobileNo;

			htmlArea += "	<li class='c-accordion__item'>";
			htmlArea += "		<div class='c-accordion__head "+ansClass+"' data-acc-header>";
			htmlArea += "			<span class='p-prefix'>"+anstitle+"</span>";
			htmlArea += "			<button class='c-accordion__button' type='button' aria-expanded='false'>";
			htmlArea += "				<div class='c-accordion__title'>";
			htmlArea += "					<div class='c-text-ellipsis'>"+qnaSubject+"</div>";
			htmlArea += "					<div class='info-category'>"+qnaSubCtgNm+"</div>";
			htmlArea += "					<div class='info-line'>";
			htmlArea += "						<span class='info-item'>";
			htmlArea += "							<span class='c-hidden'>등록일</span>";
			htmlArea += "							<span>"+cretDT+"</span>";
			htmlArea += "						</span>";
			htmlArea += "						<span class='info-item'>";
			htmlArea += "							<span>번호</span>";
			htmlArea += "							<span class='with-div'>"+mobileNo+"</span>";
			htmlArea += "						</span>";
			htmlArea += "					</div>";
			htmlArea += "				</div>";
			htmlArea += "			</button>";
			htmlArea += "		</div>";
			htmlArea += "		<div class='c-accordion__panel c-expand expand'>";
			htmlArea += "			<div class='c-accordion__inside box-question'>";
			htmlArea += "				<div class='box-prefix box-prefix--type1'>질문</div>";
			htmlArea += "				<div class='box-content'>"+qnaContent+"</div>";
			htmlArea += "			</div>";
			htmlArea += "			<div class='c-accordion__inside box-answer'>";
			htmlArea += "				<div class='box-prefix box-prefix--type1'>A</div>";
			htmlArea += "				<div class='box-content'>"+ansContent+"</div>";
			htmlArea += "			</div>";
			htmlArea += "		</div>";
			htmlArea += "	</li>";
		}
		$("#liDataArea").append(htmlArea);
		$("#dataArea").show();
		$(".c-nodata").hide();
		$("#listCount").text(listCount); // 현재 노출
		$("#totalCount").text(totalCount);
		if(listCount < totalCount){
			$("#addBtnArea").show();
		} else {
			$("#addBtnArea").hide();
		}
	} else {
		initView();
	}

}

function initView(){

	$("#dataArea").hide();
	$("#liDataArea").html("");
	$(".c-nodata").show();
	$("#listCount").text(0); // 현재 노출
	$("#totalCount").text(0);
	$("#addBtnArea").hide();
}


