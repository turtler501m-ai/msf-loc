function authCfrm(){
		
	if(certifySucess != 'Y'){
		MCP.alert("본인인증이 필요합니다.");
		return false;
	}
	
//	if(!$("#chkB1").is(':checked')){
//		MCP.alert("개인정보 수집이용 동의가 필요합니다.");
//		return false;
//	}
	
	$("#cfrmYn").val('Y');	
	
	searchOrderTempList();
	
}

function searchOrderTempList(selPageNo) {
    var pageNo = $('#orderTempPageNo').val();
	if (typeof selPageNo !== 'undefined') {
	  	pageNo = selPageNo;
	}else{
		pageNo = $('#orderTempPageNo').val();	
	}
	
    var varData = ajaxCommon.getSerializedData({	
		pageNo : pageNo	
	});

	ajaxCommon.getItem({
	    id:'orderTempListAjax'
	    ,cache:false
	    ,url:'/m/order/orderTempxListAajx.do'
	    ,data: varData
	    ,dataType:"json"
	},function(data){

		if( data.RESULT_CODE == "00000" ){
			$("#divJoinArea").hide();
			$("#divAgreeArea").hide();		
			
			//var totalCount = data.totalCount;
			//$("#tempTotalCount").text(totalCount);
			
				
			if(data.resultList && data.resultList.length > 0){
				makeOrderTempList(data.resultList);
				//makeOrderTempList(testData.resultList);
			}else{
				var noDataHtml = '';
				noDataHtml += '<div class="c-nodata">';
				noDataHtml += '<i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
				noDataHtml += '<p class="c-noadat__text">조회 결과가 없습니다.</p>';
				noDataHtml += '</div>';
				$("#tabA1-panel").html(noDataHtml);
			}
			
		}
			
	}
  );
    
}

//약관동의
function btnRegDtl(param){
	$('#targetTerms').val('Y');
	openPage('termsPop','/termsPop.do',param);
}

function makeOrderTempList(listData){
	
	var pHtml = '<p class="c-bullet c-bullet--dot u-co-gray">최근 7일 까지의 내역을 보실 수 있습니다. 단, 당사 사정 에 의해 할인, 정책 등이 변경되어 일부 저장 내용이 달라 질 수 있습니다.</p>';
		
	var hrHtmlFirst = '<hr class="c-hr c-hr--type1 c-expand">';
	var hrHtml = '<hr class="c-hr c-hr--type3 c-expand">';
	
	var loopHtml = '';
	loopHtml += pHtml;
	
	var loopHeadHtml = '';
	var loopBodyHtml = '';
	var loopFooterHtml = '';
	
	var preReqKey = '';
	var loopIndex = 0;
	
	$(listData).each(function(index) {
		
		if(index != 0 && preReqKey != this.requestKey){
								
			loopHtml += loopHeadHtml + loopBodyHtml + loopFooterHtml;
			loopHeadHtml = '';
			loopBodyHtml = '';
			loopFooterHtml = '';
		
		}
		
		if(preReqKey != this.requestKey){
		
			if(loopIndex < standardCount){
				loopHeadHtml += '<span id="tempSpanLoop_'+loopIndex+'" >';
			}else{
				loopHeadHtml += '<span id="tempSpanLoop_'+loopIndex+'" style="display:none;">';
			}
			if(index == 0){
				loopHeadHtml += hrHtmlFirst;
			}else{
				loopHeadHtml += hrHtml;
			}
			loopHeadHtml += '<div class="c-item c-item--type2">';
			loopHeadHtml += '<div class="c-flex c-flex--jfy-between">';
			loopHeadHtml += '<div class="c-item__sub">';
			loopHeadHtml += '<span class="u-fw--medium">'+this.prodNm+'</span>';
			
			if(this.reqBuyType == 'UU'){ 
				if(this.onOffType == '5' ){
					loopHeadHtml += '<span class="u-co-gray-7">온라인(셀프개통)</span>';
				}else if(this.onOffType == '7'){
					loopHeadHtml += '<span class="u-co-gray-7">모바일(셀프개통)</span>';
				}else if(this.onOffType == '9'){
					loopHeadHtml += '<span class="u-co-gray-7">오프라인(셀프개통)</span>';
				}else{
					loopHeadHtml += '<span class="u-co-gray-7">셀프개통</span>';
				}
			}else if(this.reqBuyType == 'MM'){
				loopHeadHtml += '<span class="u-co-gray-7">'+this.phoneCtgLabel+'</span>';
			}
			
			loopHeadHtml += '</div>';
			loopHeadHtml += '<span class="c-text c-text--type4 u-co-gray-7">'+emptyToRplce(this.sysRdate,'-',false)+'</span>';
			loopHeadHtml += '</div>';
			loopHeadHtml += '<div class="c-item__title">';
		
		}
		
		if(this.reqBuyType == 'UU'){ 
			loopBodyHtml += '<p>';
			loopBodyHtml += '<i class="c-icon c-icon--data" aria-hidden="true"></i>';
			loopBodyHtml += '<span class="u-ml--8">'+this.rateNm+'</span>';
			loopBodyHtml += '</p>';
		}else if(this.reqBuyType == 'MM'){
			loopBodyHtml += '<p>';
			loopBodyHtml += '<i class="c-icon c-icon--data" aria-hidden="true"></i>';
			loopBodyHtml += '<span class="u-ml--8">'+this.rateNm+'</span>';
			loopBodyHtml += '</p>';
			loopBodyHtml += '<p>';
            loopBodyHtml += '<i class="c-icon c-icon--call-gray" aria-hidden="true"></i>';
            loopBodyHtml += '<span class="u-ml--8">'+this.prodNm+' / '+this.atribValNmOne+' / '+this.atribValNmTwo+'</span>';
            loopBodyHtml += '</p>';
		}
		
		if(preReqKey != this.requestKey){
		
			loopFooterHtml += '</div>';
			loopFooterHtml += '<dl class="c-item__amount">';
			loopFooterHtml += '<dt class="u-co-gray">월 납부금액</dt>';
			loopFooterHtml += '<dd class="u-ml--8">';
			loopFooterHtml += '<b>'+numberWithCommas(this.settlAmt)+'원</b>';
			loopFooterHtml += '</dd>';
			loopFooterHtml += '</dl>';
			var stepNo = emptyToRplce(this.tmpStep,'0',true); 		
			loopFooterHtml += '<div class="c-indicator c-indicator--type1 u-mt--16">';
			loopFooterHtml += '<span class="mark-red" style="width: '+parseInt(stepNo)*20+'%"></span>';
			loopFooterHtml += '</div>';
			loopFooterHtml += '<div class="c-item__info">';
			loopFooterHtml += '<span class="u-co-gray">진행단계</span>';
			loopFooterHtml += '<span class="u-co-point-4">('+stepNo+'/5)</span>';
			if(stepNo == 0){
				loopFooterHtml += '<p class="u-mt--4"></p>';
			}else if(stepNo == 1){
				loopFooterHtml += '<p class="u-mt--4">본인 확인 및 약관동의</p>';
			}else if(stepNo == 2){
				loopFooterHtml += '<p class="u-mt--4">유심정보 및 신분증 확인</p>';
			}else if(stepNo == 3){
				loopFooterHtml += '<p class="u-mt--4">가입신청 정보</p>';
			}else if(stepNo == 4){
				loopFooterHtml += '<p class="u-mt--4">부가서비스 정보</p>';
			}else if(stepNo == 5){
				loopFooterHtml += '<p class="u-mt--4">납부정보 및 가입정보 확인</p>';
			}
			loopFooterHtml += '</div>';
			loopFooterHtml += '<a class="c-button c-button--sm c-button--white" href="#" onClick="go_join_page(\''+this.requestKey+'\')" >이어하기</a>';
			loopFooterHtml += '</div>';
			loopFooterHtml += '</span>';
			loopIndex ++;
		}
		
		preReqKey = this.requestKey;
		
	});
	
	loopHtml += loopHeadHtml + loopBodyHtml + loopFooterHtml;
	
	if(listData.length > standardCount){
		loopHtml += '<div id="tempMoreDivArea" class="c-button-wrap u-mt--8">';
        loopHtml += '    <button type="button" class="c-button c-button--full" id="searchTempNext" ';
        loopHtml += ' href="javascript:void(0);" onclick="viewTempMore();">더보기<span class="c-button__sub" id="tempPageNav"><span id="tempCurrentViewCount">1</span>/<span id="tempTotalCount">'+listData.length+'</span></span>';
        loopHtml += '      <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>';
        loopHtml += '    </button>';
        loopHtml += '</div>';
		
	}

	$("#tempTotalCount").text(loopIndex);
	$("#tabA1-panel").html(loopHtml);		
	
	initTempMore();
	
}

function makeOrderTempList_group(listData){
	
	var pHtml = '<p class="c-bullet c-bullet--dot u-co-gray">최근 7일 까지의 내역을 보실 수 있습니다. 단, 당사 사정 에 의해 할인, 정책 등이 변경되어 일부 저장 내용이 달라 질 수 있습니다.</p>';
		
	var hrHtmlFirst = '<hr class="c-hr c-hr--type1 c-expand">';
	var hrHtml = '<hr class="c-hr c-hr--type3 c-expand">';
	
	var loopHtml = '';
	loopHtml += pHtml;
	
	var loopHeadHtml = '';
	var loopBodyHtml = '';
	var loopFooterHtml = '';
	
	var preReqKey = '';
	var loopIndex = 0;
	
	$(listData).each(function(index) {
		
		if(index != 0 && preReqKey != this.requestKey){
								
			loopHtml += loopHeadHtml + loopBodyHtml + loopFooterHtml;
			loopHeadHtml = '';
			loopBodyHtml = '';
			loopFooterHtml = '';
		
		}
		
		if(preReqKey != this.requestKey){
		
			if(loopIndex < standardCount){
				loopHeadHtml += '<span id="tempSpanLoop_'+loopIndex+'" >';
			}else{
				loopHeadHtml += '<span id="tempSpanLoop_'+loopIndex+'" style="display:none;">';
			}
			if(index == 0){
				loopHeadHtml += hrHtmlFirst;
			}else{
				loopHeadHtml += hrHtml;
			}
			loopHeadHtml += '<div class="c-item c-item--type2">';
			loopHeadHtml += '<div class="c-flex c-flex--jfy-between">';
			loopHeadHtml += '<div class="c-item__sub">';
			loopHeadHtml += '<span class="u-fw--medium">'+this.prodNm+'</span>';
			if(this.onOffType == '5' ){
				loopHeadHtml += '<span class="u-co-gray-7">온라인(셀프개통)</span>';
			}else if(this.onOffType == '7'){
				loopHeadHtml += '<span class="u-co-gray-7">모바일(셀프개통)</span>';
			}else if(this.onOffType == '9'){
				loopHeadHtml += '<span class="u-co-gray-7">오프라인(셀프개통)</span>';
			}else{
				loopHeadHtml += '<span class="u-co-gray-7">셀프개통</span>';
			}
			loopHeadHtml += '</div>';
			loopHeadHtml += '<span class="c-text c-text--type4 u-co-gray-7">'+emptyToRplce(this.sysRdate,'-',false)+'</span>';
			loopHeadHtml += '</div>';
			loopHeadHtml += '<div class="c-item__title">';
		
		}
		
		if(this.reqBuyType == 'UU'){ 
			loopBodyHtml += '<p>';
			loopBodyHtml += '<i class="c-icon c-icon--data" aria-hidden="true"></i>';
			loopBodyHtml += '<span class="u-ml--8">'+this.rateNm+'</span>';
			loopBodyHtml += '</p>';
		}else if(this.reqBuyType == 'MM'){
			loopBodyHtml += '<p>';
            loopBodyHtml += '<i class="c-icon c-icon--call-gray" aria-hidden="true"></i>';
            loopBodyHtml += '<span class="u-ml--8">'+this.prodNm+' / '+this.atribValNmOne+' / '+this.atribValNmTwo+'</span>';
            loopBodyHtml += '</p>';
		}
		
		if(preReqKey != this.requestKey){
		
			loopFooterHtml += '</div>';
			loopFooterHtml += '<dl class="c-item__amount">';
			loopFooterHtml += '<dt class="u-co-gray">월 납부금액</dt>';
			loopFooterHtml += '<dd class="u-ml--8">';
			loopFooterHtml += '<b>'+numberWithCommas(this.settlAmt)+'원</b>';
			loopFooterHtml += '</dd>';
			loopFooterHtml += '</dl>';
			var stepNo = emptyToRplce(this.tmpStep,'0',true); 		
			loopFooterHtml += '<div class="c-indicator c-indicator--type1 u-mt--16">';
			loopFooterHtml += '<span class="mark-red" style="width: '+parseInt(stepNo)*20+'%"></span>';
			loopFooterHtml += '</div>';
			loopFooterHtml += '<div class="c-item__info">';
			loopFooterHtml += '<span class="u-co-gray">진행단계</span>';
			loopFooterHtml += '<span class="u-co-point-4">('+stepNo+'/5)</span>';
			if(stepNo == 0){
				loopFooterHtml += '<p class="u-mt--4"></p>';
			}else if(stepNo == 1){
				loopFooterHtml += '<p class="u-mt--4">본인 확인 및 약관동의</p>';
			}else if(stepNo == 2){
				loopFooterHtml += '<p class="u-mt--4">유심정보 및 신분증 확인</p>';
			}else if(stepNo == 3){
				loopFooterHtml += '<p class="u-mt--4">가입신청 정보</p>';
			}else if(stepNo == 4){
				loopFooterHtml += '<p class="u-mt--4">부가서비스 정보</p>';
			}else if(stepNo == 5){
				loopFooterHtml += '<p class="u-mt--4">납부정보 및 가입정보 확인</p>';
			}
			loopFooterHtml += '</div>';
			loopFooterHtml += '<a class="c-button c-button--sm c-button--white" href="#" onClick="go_join_page(\''+this.requestKey+'\')" >이어하기</a>';
			loopFooterHtml += '</div>';
			loopFooterHtml += '</span>';
			loopIndex ++;
		}
		
		preReqKey = this.requestKey;
		
	});
	
	loopHtml += loopHeadHtml + loopBodyHtml + loopFooterHtml;
	
	if(listData.length > standardCount){
		loopHtml += '<div id="tempMoreDivArea" class="c-button-wrap u-mt--8">';
        loopHtml += '    <button type="button" class="c-button c-button--full" id="searchTempNext" ';
        loopHtml += ' href="javascript:void(0);" onclick="viewTempMore();">더보기<span class="c-button__sub" id="tempPageNav"><span id="tempCurrentViewCount">1</span>/<span id="tempTotalCount">'+listData.length+'</span></span>';
        loopHtml += '      <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>';
        loopHtml += '    </button>';
        loopHtml += '</div>';
		
	}

	$("#tempTotalCount").text(loopIndex);
	$("#tabA1-panel").html(loopHtml);		
	
	initTempMore();
	
}

function go_join_page(key){
	
	document.appForm.requestKey.value = key;
	document.appForm.submit();
	
}

function emptyToRplce(value,rplChr,isStep){
	
	if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){ 
		return rplChr; 
	} else{ 
		if(isStep && value.length > 1){
			value = value.substring(1,2);
		} 
		
		return value; 
	} 
		
}

var initTempMore = function(){
	var mCount = $("#tempTotalCount").text();
	if(mCount <= standardCount){
		$("#tempCurrentViewCount").text(mCount);
	}else{
		$("#tempCurrentViewCount").text(standardCount);
	}
	var cCount = $("#tempCurrentViewCount").text();
    var mCount = $("#tempTotalCount").text();
	if(cCount == mCount){
		$("#tempMoreDivArea").hide();
	}
	
};

/**
 * 더보기 처리기능
 */
var viewTempMore = function() {

    var cCount = $("#tempCurrentViewCount").text();
    var mCount = $("#tempTotalCount").text();

    if (cCount == mCount) {	
		MCP.alert('마지막 페이지입니다.');
        return;
    }
    //숨김 처리 되있는 요금제 리스트만 가져온다.
	var $divList =$("#tabA1-panel").find("span[id^='tempSpanLoop_'][style*=none]");
    $.each($divList,function(idx) {	//10개까지만 보이게 처리
        if (idx == standardCount) {
            return false;
        }
        $(this).css("display","");
    });
    setTempCurrentCount();	//더보기 버튼의 숫자 카운트 변경
};

/**
 * 더보기 버튼의 숫자 변경처리
 */
var setTempCurrentCount = function() {
	var hiddenCount = $("#tabA1-panel").find("span[id^='tempSpanLoop_'][style*=none]").length;
	var totalCount =  $("#tabA1-panel").find("span[id^='tempSpanLoop_']").length;
	var currentViewCount = totalCount - hiddenCount;
    $("#tempCurrentViewCount").text(currentViewCount);
    $("#tempTotalCount").text(totalCount);
	if(currentViewCount == totalCount){
		$("#tempMoreDivArea").hide();
	}

};



