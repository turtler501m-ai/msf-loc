var usimData;
var standardCount = 10; //더보기 기준값
var listCount = 0;
function searchOrder_basic(selPageNo) {
	var pageNo = $('#orderUsimPageNo').val();
	if (typeof selPageNo !== 'undefined') {
	  	pageNo = selPageNo;
	}else{
		pageNo = $('#orderUsimPageNo').val();
	}

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo
	});

	$.ajax({
			//url : '/m/order/orderListAjax.do',
			url : '/m/order/orderPageGroupListAjax.do',
			type : 'post',
			dataType : 'json',
			data : varData,
			async : false,
			success : function(data) {
				var resultCode = data.RESULT_CODE;
				var totalCount = data.totalCount;
				$("#totalCount").text(totalCount);

				if(totalCount > 0 && resultCode == "00000") {
					$('#noDataDivArea').hide();
					var listData = data.resultList;
					//listData = testData.resultList; //테스트용
					var html = "";
					var dlvCnt_1 = 0;
					var dlvCnt_2 = 0;
					var dlvCnt_3 = 0;
					var dlvCnt_4 = 0;
					var dlvCnt_5 = 0;

					var loopIndex = 0;

					$(listData).each(function(index) {

						if(loopIndex < standardCount){
							html += '<div id="divLoop_'+loopIndex+'" class="c-item c-item--type3">';
						}else{
							html += '<div id="divLoop_'+loopIndex+'" class="c-item c-item--type3" style="display:none;">';
						}

						html += '<div class="c-flex c-flex--jfy-between">';
						html += '<div class="c-item__sub">';
						html += '<span class="u-co-black u-fw--medium">'+this.resNo+'</span>';
						//html += '<span class="u-co-black u-fw--medium">'+this.requestKey+'</span>';
						html += '<span class="u-co-gray-7">'+this.sysRdate+'</span>';
						html += '</div>';
						html += '<div class="c-button-wrap c-button-wrap--right u-mt--0">';
						html += '<button type="button" class="c-button c-button--xsm u-pr--0" href="javascript:void(0);" onClick="orderView(\''+this.requestKey+'\')">';
						html += '<span class="c-hidden">상세보기</span>';
						html += '<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>';
						html += '</button>';
						html += '</div>';
						html += '</div>';

						loopIndex ++;


						html += '<div class="c-item__list">';
						html += '<div class="c-item__image">';

						if(this.reqBuyType == 'UU'){
							html += '<img src="/resources/images/mobile/phone/img_product_02.png" alt="">';
						}else{
							html += '<img src="'+this.imgPath+'" alt="">';
						}

						html += '</div>';
						html += '<div class="c-item__item">';

						var requestStateCode = this.requestStateCode;
						var rsCode = '';

						if(requestStateCode == '00'){
							rsCode = 'PSTATE01';
						}else if(requestStateCode == '01' || requestStateCode == '02' || requestStateCode == '09' || requestStateCode == '07' || requestStateCode == '08'){
							rsCode = 'PSTATE02';
						}else if(requestStateCode == '03' || requestStateCode == '04' || requestStateCode == '10'){
							rsCode = 'PSTATE03';
						}else if(requestStateCode == '20' || requestStateCode == '11' || requestStateCode == '13'){
							rsCode = 'PSTATE04';
						}else if(requestStateCode == '21'){
							rsCode = 'PSTATE05';
						}else if(requestStateCode == '30' || requestStateCode == '31'){
							rsCode = 'PSTATE06';
						}

						if(rsCode == 'PSTATE01'){
							html += '<span class="c-text c-text--type4 u-co-point-4">접수대기</span>';
						}else if(rsCode == 'PSTATE02'){
							html += '<span class="c-text c-text--type4 u-co-point-4">배송대기</span>';
						}else if(rsCode == 'PSTATE03'){
							html += '<span class="c-text c-text--type4 u-co-point-4">배송중</span>';
						}else if(rsCode == 'PSTATE04'){
							html += '<span class="c-text c-text--type4 u-co-point-4">개통대기</span>';
						}else if(rsCode == 'PSTATE05'){
							html += '<span class="c-text c-text--type4 u-co-point-4">개통완료</span>';
						}else if(rsCode == 'PSTATE06'){
							html += '<span class="c-text c-text--type4 u-co-point-4">신청취소</span>';
						}

						html += '<div class="c-item__title">';
						html += '<strong>'+emptyToTyphoon(this.prodNm)+'</strong>';
						html += '</div>';
						html += '<div class="c-item__info">';
						if(this.reqBuyType == 'UU'){
							html += '<span class="c-text c-text--type3 u-co-gray">'+emptyToTyphoon(this.rateNm)+'</span>';
						}else if(this.reqBuyType == 'MM'){
							html += '<span class="c-text c-text--type3 u-co-gray">'+emptyToTyphoon(this.atribValNmOne)+'/'+emptyToTyphoon(this.atribValNmTwo)+'</span>';
						}

						if(this.reqBuyType == 'UU'){
							//html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(parseInt(this.baseAmtInt + this.baseAmtInt*0.1))+'원</span>';
							html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(this.mspSaleSubsdMstDto.payMnthChargeAmt)+'원</span>';
						}else if(this.reqBuyType == 'MM'){
							//html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(this.modelPriceInt + this.modelPriceInt*0.1)+'원</span>';
							if(this.selfYn == 'Y'){
								html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(this.settlAmt)+'원</span>';
							}else{
								html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(this.mspSaleSubsdMstDto.payMnthAmt+this.mspSaleSubsdMstDto.payMnthChargeAmt)+'원</span>';
							}
						}

						html += '</div>';
						html += '</div>';
						if(emptyToTyphoon(this.percelUrl) != '-'){
							html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')">배송조회</button>';
						}else{
							html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
						}
						html += '</div>';
						html += '</div>';

					});

					dlvCnt_1 = data.stateMap.PSTATE01;
					dlvCnt_2 = data.stateMap.PSTATE02;
					dlvCnt_3 = data.stateMap.PSTATE03;
					dlvCnt_4 = data.stateMap.PSTATE04;
					dlvCnt_5 = data.stateMap.PSTATE05;

					html += '</div>';

					var olHtml = "";
					olHtml += '<li';
					if(dlvCnt_1 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_1+'</b>';
					olHtml += '<!--[2022-01-19] 배송조회 수정 ==> 접수대기-->';
					olHtml += '<span>접수대기</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_2 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_2+'</b>';
					olHtml += '<span>배송대기</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_3 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_3+'</b>';
					olHtml += '<span>배송중</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_4 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_4+'</b>';
					olHtml += '<span>개통대기</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_5 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_5+'</b>';
					olHtml += '<span>개통완료</span>';
					olHtml += '</li>';
					$("#hdTotalCount").val(loopIndex);
					$("#totalCount").text(numberWithCommas(loopIndex));
					$('#orderOlArea').html(olHtml);
					$('#dataLoopArea').html(html);

					$("div > div.c-item__list > div.c-item__image > img").each(function(){
						$(this).error(function(){
							$(this).unbind("error");
							$(this).attr("src","/resources/images/mobile/content/img_noimage.png");
						});
					});

					initMore();

				}else{
					$('#orderOlArea').hide();
					$('#noDataDivArea').show();
		      		$('#dataLoopArea').html('');
					$("#moreDivArea").hide();
				}
			}
		});

}

function searchOrder(selPageNo) {
	var pageNo = $('#orderUsimPageNo').val();
	if (typeof selPageNo !== 'undefined') {
	  	pageNo = selPageNo;
	}else{
		pageNo = $('#orderUsimPageNo').val();
	}

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo
	});

	var preReqKey = '';

	$.ajax({
			//url : '/m/order/orderListAjax.do',
			url : '/m/order/orderPageGroupListAjax.do',
			type : 'post',
			dataType : 'json',
			data : varData,
			async : false,
			success : function(data) {
				var resultCode = data.RESULT_CODE;
				var totalCount = data.totalCount;
				$("#totalCount").text(totalCount);

				if(totalCount > 0 && resultCode == "00000") {
					$('#noDataDivArea').hide();
					var listData = data.resultList;
					//listData = testData.resultList; //테스트용
					var html = "";
					var dlvCnt_1 = 0;
					var dlvCnt_2 = 0;
					var dlvCnt_3 = 0;
					var dlvCnt_4 = 0;
					var dlvCnt_5 = 0;

					var loopIndex = 0;

					$(listData).each(function(index) {

						if(preReqKey != this.requestKey){

							if(index != 0){
								html += '</div>';
							}

							if(loopIndex < standardCount){
								html += '<div id="divLoop_'+loopIndex+'" class="c-item c-item--type3">';
							}else{
								html += '<div id="divLoop_'+loopIndex+'" class="c-item c-item--type3" style="display:none;">';
							}

							html += '<div class="c-flex c-flex--jfy-between">';
							html += '<div class="c-item__sub">';
							html += '<span class="u-co-black u-fw--medium">'+this.resNo+'</span>';
							//html += '<span class="u-co-black u-fw--medium">'+this.requestKey+'</span>';
							html += '<span class="u-co-gray-7">'+this.sysRdate+'</span>';
							html += '</div>';
							html += '<div class="c-button-wrap c-button-wrap--right u-mt--0">';
							html += '<button type="button" class="c-button c-button--xsm u-pr--0" href="javascript:void(0);" onClick="orderView(\''+this.requestKey+'\')">';
							html += '<span class="c-hidden">상세보기</span>';
							html += '<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>';
							html += '</button>';
							html += '</div>';
							html += '</div>';

							loopIndex ++;
						}

						html += '<div class="c-item__list">';
						html += '<div class="c-item__image">';
						if(this.reqBuyType == 'UU'){
							html += '<img src="/resources/images/mobile/phone/img_product_02.png" alt="">';
						}else{
							html += '<img src="'+this.imgPath+'" alt="">';
						}
						html += '</div>';
						html += '<div class="c-item__item">';

						var requestStateCode = this.requestStateCode;
						var rsCode = '';

						if(requestStateCode == '00'){
							rsCode = 'PSTATE01';
						}else if(requestStateCode == '01' || requestStateCode == '02' || requestStateCode == '09' || requestStateCode == '07' || requestStateCode == '08'){
							rsCode = 'PSTATE02';
						}else if(requestStateCode == '03' || requestStateCode == '04' || requestStateCode == '10'){
							rsCode = 'PSTATE03';
						}else if(requestStateCode == '20' || requestStateCode == '11' || requestStateCode == '13'){
							rsCode = 'PSTATE04';
						}else if(requestStateCode == '21'){
							rsCode = 'PSTATE05';
						}else if(requestStateCode == '30' || requestStateCode == '31'){
							rsCode = 'PSTATE06';
						}

						if(rsCode == 'PSTATE01'){
							html += '<span class="c-text c-text--type4 u-co-point-4">접수대기</span>';
						}else if(rsCode == 'PSTATE02'){
							html += '<span class="c-text c-text--type4 u-co-point-4">배송대기</span>';
						}else if(rsCode == 'PSTATE03'){
							html += '<span class="c-text c-text--type4 u-co-point-4">배송중</span>';
						}else if(rsCode == 'PSTATE04'){
							html += '<span class="c-text c-text--type4 u-co-point-4">개통대기</span>';
						}else if(rsCode == 'PSTATE05'){
							html += '<span class="c-text c-text--type4 u-co-point-4">개통완료</span>';
						}else if(rsCode == 'PSTATE06'){
							html += '<span class="c-text c-text--type4 u-co-point-4">신청취소</span>';
						}


						html += '<div class="c-item__title">';
						html += '<strong>'+emptyToTyphoon(this.prodNm)+'</strong>';
						html += '</div>';
						html += '<div class="c-item__info">';
						if(this.reqBuyType == 'UU'){
							html += '<span class="c-text c-text--type3 u-co-gray">'+emptyToTyphoon(this.rateNm)+'</span>';
						}else if(this.reqBuyType == 'MM'){
							html += '<span class="c-text c-text--type3 u-co-gray">'+emptyToTyphoon(this.atribValNmOne)+'/'+emptyToTyphoon(this.atribValNmTwo)+'</span>';
						}
						if(this.reqBuyType == 'UU'){
							//html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(parseInt(this.baseAmtInt + this.baseAmtInt*0.1))+'원</span>';
							html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(this.mspSaleSubsdMstDto.payMnthChargeAmt)+'원</span>';
						}else if(this.reqBuyType == 'MM'){
							//html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(this.modelPriceInt + this.modelPriceInt*0.1)+'원</span>';
							if(this.selfYn == 'Y'){
								html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(this.settlAmt)+'원</span>';
							}else{
								html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(this.mspSaleSubsdMstDto.payMnthAmt+this.mspSaleSubsdMstDto.payMnthChargeAmt)+'원</span>';
							}
						}
						html += '</div>';
						html += '</div>';
						//if(emptyToTyphoon(this.percelUrl) != '-'){
						if(rsCode == 'PSTATE03' || rsCode == 'PSTATE04' || rsCode == 'PSTATE05'){

							if(emptyToTyphoon(this.percelUrl) != '-' && emptyToTyphoon(this.dlvryNo) != '-'){
								html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')">배송조회</button>';
							}else{
								html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
							}

						}else{
							html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
						}
						html += '</div>';

						preReqKey = this.requestKey;

					});

						dlvCnt_1 = data.stateMap.PSTATE01;
						dlvCnt_2 = data.stateMap.PSTATE02;
						dlvCnt_3 = data.stateMap.PSTATE03;
						dlvCnt_4 = data.stateMap.PSTATE04;
						dlvCnt_5 = data.stateMap.PSTATE05;

					html += '</div>';

					var olHtml = "";
					olHtml += '<li';
					if(dlvCnt_1 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_1+'</b>';
					olHtml += '<!--[2022-01-19] 배송조회 수정 ==> 접수대기-->';
					olHtml += '<span>접수대기</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_2 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_2+'</b>';
					olHtml += '<span>배송대기</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_3 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_3+'</b>';
					olHtml += '<span>배송중</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_4 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_4+'</b>';
					olHtml += '<span>개통대기</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_5 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_5+'</b>';
					olHtml += '<span>개통완료</span>';
					olHtml += '</li>';
					$("#hdTotalCount").val(loopIndex);
					$("#totalCount").text(numberWithCommas(loopIndex));
					$('#orderOlArea').html(olHtml);
					$('#dataLoopArea').html(html);

					$("div > div.c-item__list > div.c-item__image > img").each(function(){
						$(this).error(function(){
							$(this).unbind("error");
							$(this).attr("src","/resources/images/mobile/content/img_noimage.png");
						});
					});

					initMore();

				}else{
					$('#orderOlArea').hide();
					$('#noDataDivArea').show();
		      		$('#dataLoopArea').html('');
					$("#moreDivArea").hide();
				}
			}
		});

}


var initMore = function(){

	var mCount = $("#hdTotalCount").val();
	if(mCount <= standardCount){
		$("#hdCurrentViewCount").val(mCount);
		$("#currentViewCount").text(numberWithCommas(mCount));
	}else{
		$("#hdCurrentViewCount").val(standardCount);
		$("#currentViewCount").text(numberWithCommas(standardCount));
	}
	var cCount = $("#hdCurrentViewCount").val();
	if(cCount == mCount){
		$("#moreDivArea").hide();
	}

};

/**
 * 더보기 처리기능
 */
var viewMore = function() {

    var cCount = $("#hdCurrentViewCount").val();
    var mCount = $("#hdTotalCount").val();

    if (cCount == mCount) {
		MCP.alert('마지막 페이지입니다.');
        return;
    }
    //숨김 처리 되있는 요금제 리스트만 가져온다.
	var $divList =$("#dataLoopArea").find("div[id^='divLoop_'][style*=none]");
    $.each($divList,function(idx) {	//10개까지만 보이게 처리
        if (idx == standardCount) {
            return false;
        }
        $(this).css("display","");
    });
    setCurrentCount();	//더보기 버튼의 숫자 카운트 변경
};

/**
 * 더보기 버튼의 숫자 변경처리
 */
var setCurrentCount = function() {
	var currentViewCount = $("#dataLoopArea").find("div[id^='divLoop_']:visible").length;
	var totalCount =  $("#dataLoopArea").find("div[id^='divLoop_']").length;;
    $("#currentViewCount").text(numberWithCommas(currentViewCount));
    $("#totalCount").text(numberWithCommas(totalCount));
    $("#hdCurrentViewCount").val(currentViewCount);
    $("#hdTotalCount").val(totalCount);
	if(currentViewCount == totalCount){
		$("#moreDivArea").hide();
	}

};


function searchSelfOrder(selPageNo) {
	var pageNo = $('#orderSelfPageNo').val();
	if (typeof selPageNo !== 'undefined') {
	  	pageNo = selPageNo;
	}else{
		pageNo = $('#orderSelfPageNo').val();
	}

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo
		,reqBuyType : 'UU'
	});

	var preReqKey = '';

	$.ajax({
			//url : '/m/appForm/reqSelfDlvryListAjax.do',
			url : '/m/order/reqSelfDlvryListPageAjax.do',
			type : 'post',
			dataType : 'json',
			data : varData,
			async : false,
			success : function(data) {
				var resultCode = data.RESULT_CODE;
				var totalCount = data.total;
				if(totalCount > 0 && resultCode == "00000") {
					$('#noDataDivArea_2').hide();
					var listData = data.resultList;
					var html = "";
					var dlvCnt_1 = 0;
					var dlvCnt_2 = 0;
					var dlvCnt_3 = 0;
					var dlvCnt_4 = 0;
					var dlvCnt_5 = 0;

					var loopIndex = 0;
					//listData = testData.resultList; //테스트용
					$(listData).each(function(index) {
						if(preReqKey != this.selfDlvryIdx){

							if(index != 0){
								html += '</div>';
							}

							if(loopIndex < standardCount){
								html += '<div id="divSelfLoop_'+loopIndex+'" class="c-item c-item--type3">';
							}else{
								html += '<div id="divSelfLoop_'+loopIndex+'" class="c-item c-item--type3" style="display:none;">';
							}

							html += '<div class="c-flex c-flex--jfy-between">';
							html += '<div class="c-item__sub">';
							html += '<span class="u-co-black u-fw--medium">'+this.selfDlvryIdx+'</span>';
							html += '<span class="u-co-gray-7">'+relpceTimestamp(this.sysRdate)+'</span>';
							html += '</div>';
							html += '<div class="c-button-wrap c-button-wrap--right u-mt--0">';
							html += '<button type="button" class="c-button c-button--xsm u-pr--0" href="javascript:void(0);" onClick="selfOrderView(\''+this.selfDlvryIdx+'\')">';
							html += '<span class="c-hidden">상세보기</span>';
							html += '<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>';
							html += '</button>';
							html += '</div>';
							html += '</div>';

							loopIndex ++;
						}
						html += '<div class="c-item__list">';
						html += '<div class="c-item__image">';
						html += '<img src="../../resources/images/mobile/phone/img_product_02.png" alt="">';
						html += '</div>';
						html += '<div class="c-item__item">';

						/* dlvryKind 01일경우
						01 접수대기	접수대기
						02 배송중	배송중
						03 배송완료	배송완료
						04 개통완료	배송완료
						*/

						/* dlvryKind 02일경우
						01      접수	=> 접수대기
						02      운행	=> 배송중
						03      픽업	=> 배송대기
						04      완료	=> 배송완료
						05      접수취소 =>	신청취소
						06      대기	=> 접수대기
						07      배송취소 => 신청취소
						*/



						var rsCode = this.dlvryStateCode;
						var dlvryKind = this.dlvryKind;
						var selfStateCode = this.selfStateCode;

						if(dlvryKind == '01'){
							if(selfStateCode == '01'){
								if(rsCode == '01'){
									html += '<span class="c-text c-text--type4 u-co-point-4">접수대기</span>';
									dlvCnt_1++;
								}else if(rsCode == '02'){
									html += '<span class="c-text c-text--type4 u-co-point-4">배송중</span>';
									dlvCnt_3++;
								}else if(rsCode == '03' || rsCode == '04'){
									html += '<span class="c-text c-text--type4 u-co-point-4">배송완료</span>';
									dlvCnt_4++;
								}
							}else{
								html += '<span class="c-text c-text--type4 u-co-point-4">신청취소</span>';
							}

						}else if(dlvryKind == '02'){

							if(rsCode == '01' || rsCode == '06'){
								html += '<span class="c-text c-text--type4 u-co-point-4">접수대기</span>';
								dlvCnt_1++;
							}else if(rsCode == '03'){
								html += '<span class="c-text c-text--type4 u-co-point-4">배송대기</span>';
								dlvCnt_2++;
							}else if(rsCode == '02'){
								html += '<span class="c-text c-text--type4 u-co-point-4">배송중</span>';
								dlvCnt_3++;
							}else if(rsCode == '04'){
								html += '<span class="c-text c-text--type4 u-co-point-4">배송완료</span>';
								dlvCnt_4++;
							}else if(rsCode == '05' || rsCode == '07'){
								html += '<span class="c-text c-text--type4 u-co-point-4">신청취소</span>';
							}

						}

						html += '<div class="c-item__title">';

						var usimProdId = this.usimProdId;
						var usimDataObj;
							$(usimData).each(function() {
								if(this.dtlCd == usimProdId.trim()){
									usimDataObj = this;
								}
							});

						html += '<strong>'+emptyToTyphoon(usimDataObj.dtlCdNm)+'</strong>';
						html += '</div>';
						html += '<div class="c-item__info">';
						//html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(usimDataObj.expnsnStrVal1)+'원</span>';
						html += '<span class="c-text c-text--type3 u-co-gray">'+numberWithCommas(emptyToTyphoon(this.usimPrice))+'원</span>';
						html += '</div>';
						html += '</div>';
						//if(emptyToTyphoon(this.dlvryNo) != '-'){
						if(dlvryKind == '01'){
							if(rsCode == '02' || rsCode == '03' || rsCode == '04'){

								if(emptyToTyphoon(this.percelUrl) != '-' && emptyToTyphoon(this.dlvryNo) != '-'){
									html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')">배송조회</button>';
								}else{
									html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
								}

							}else{
								html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
							}
						}else if(dlvryKind == '02'){
							if(rsCode == '02' || rsCode == '04'){

								if(emptyToTyphoon(this.percelUrl) != '-' && emptyToTyphoon(this.dlvryNo) != '-'){
									html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')">배송조회</button>';
								}else{
									html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
								}

							}else{
								html += '<button type="button" class="c-button c-button--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
							}
						}
						html += '</div>';

						preReqKey = this.selfDlvryIdx;

					});

					html += '</div>';

					var olHtml = "";
					olHtml += '<li';
					if(dlvCnt_1 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_1+'</b>';
					olHtml += '<span>접수대기</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_2 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_2+'</b>';
					olHtml += '<span>배송대기</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_3 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_3+'</b>';
					olHtml += '<span>배송중</span>';
					olHtml += '</li>';
					olHtml += '<li';
					if(dlvCnt_4 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_4+'</b>';
					olHtml += '<span>배송완료</span>';
					olHtml += '</li>';
					olHtml += '<li';
					/*
					if(dlvCnt_5 > 0){
					olHtml += ' class="is-active" ';
					}
					olHtml += '>';
					olHtml += '<b>'+dlvCnt_5+'</b>';
					olHtml += '<span>개통완료</span>';
					olHtml += '</li>';
					*/

					$("#totalCount_2").text(numberWithCommas(loopIndex));
					$("#hdTotalCount_2").val(loopIndex);
					$('#orderOlArea_2').html(olHtml);
					$('#dataLoopArea_2').html(html);

					initMore_2();

				}else{
					$('#orderOlArea_2').hide();
					$('#noDataDivArea_2').show();
		      		$('#dataLoopArea_2').html('');
					$("#moreDivArea_2").hide();
				}
			}
		});

}

var initMore_2 = function(){
	var mCount = $("#hdTotalCount_2").val();
	if(mCount <= standardCount){
		$("#currentViewCount_2").text(numberWithCommas(mCount));
		$("#hdCurrentViewCount_2").val(mCount);
	}else{
		$("#currentViewCount_2").text(numberWithCommas(standardCount));
		$("#hdCurrentViewCount_2").val(standardCount);
	}
	var cCount = $("#hdCurrentViewCount_2").val();
	if(cCount == mCount){
		$("#moreDivArea_2").hide();
	}

};

/**
 * 더보기 처리기능
 */
var viewMore_2 = function() {

    var cCount = $("#hdCurrentViewCount_2").val();
    var mCount = $("#hdTotalCount_2").val();

    if (cCount == mCount) {
		MCP.alert('마지막 페이지입니다.');
        return;
    }
    //숨김 처리 되있는 요금제 리스트만 가져온다.
	var $divList =$("#dataLoopArea_2").find("div[id^='divSelfLoop_'][style*=none]");
    $.each($divList,function(idx) {	//10개까지만 보이게 처리
        if (idx == standardCount) {
            return false;
        }
        $(this).css("display","");
    });
    setCurrentCount_2();	//더보기 버튼의 숫자 카운트 변경
};

/**
 * 더보기 버튼의 숫자 변경처리
 */
var setCurrentCount_2 = function() {
	var currentViewCount = $("#dataLoopArea_2").find("div[id^='divSelfLoop_']:visible").length;
	var totalCount =  $("#dataLoopArea_2").find("div[id^='divSelfLoop_']").length;
    $("#currentViewCount_2").text(numberWithCommas(currentViewCount));
    $("#totalCount_2").text(numberWithCommas(totalCount));
    $("#hdCurrentViewCount_2").val(currentViewCount);
    $("#hdTotalCount_2").val(totalCount);
	if(currentViewCount == totalCount){
		$("#moreDivArea_2").hide();
	}

};





function emptyToTyphoon(value){

	if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
		return '-';
	} else{
		return value;
	}

}

function orderView(key){

	var parameterData = ajaxCommon.getSerializedData({
		 requestKey : key
	});
	openPage('pullPopupByPost','/m/orderView.do',parameterData);


}

function selfOrderView(key){

	var parameterData = ajaxCommon.getSerializedData({
		 selfDlvryIdx : key
	});
	openPage('pullPopupByPost','/m/order/reqSelfDlvryView.do',parameterData);


}

function goDlvPage(goUrl){
	//앱일경우
	if(platFormCd == 'A'){
		appOutSideOpen(goUrl);
	}else{
		window.open(goUrl);
	}

}

function initUsimData(){
	var varData = ajaxCommon.getSerializedData({
		grpCd : 'usimProdInfo'
	});

	$.ajax({
		url : '/m/getComCodeListAjax.do',
		type : 'post',
		dataType : 'json',
		data : varData,
		async : false,
		success : function(data) {
			usimData = data.result;

		}
	});

}

function relpceTimestamp(t){
    var date = new Date();
    var year = date.getFullYear();
    var month = "0" + (date.getMonth()+1);
    var day = "0" + date.getDate();
    return year + "." + month.substr(-2) + "." + day.substr(-2);
}