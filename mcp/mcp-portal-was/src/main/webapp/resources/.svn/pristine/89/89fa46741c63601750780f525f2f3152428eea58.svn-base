var usimData;

function goOrderSearch(selPageNo){
	var rDate = $("#range").val();
	var rDateS = rDate.split("~");
	var sDate = rDateS[0].trim().replace(/\./gi, "");
	var eDate = rDateS[1].trim().replace(/\./gi, "");
	$("#orderStdt").val(sDate);
	$("#orderEddt").val(eDate);
	searchOrder(selPageNo);
}

function goSelfSearch(selPageNo){
	var rDate = $("#range_2").val();
	var rDateS = rDate.split("~");
	var sDate = rDateS[0].trim().replace(/\./gi, "");
	var eDate = rDateS[1].trim().replace(/\./gi, "");
	$("#selfStdt").val(sDate);
	$("#selfEddt").val(eDate);
	searchSelfOrder(selPageNo);
}

function searchOrder(selPageNo) {
	var pageNo = $('#orderUsimPageNo').val();
	if (typeof selPageNo !== 'undefined') {
	  	pageNo = selPageNo;
	}else{
		pageNo = $('#orderUsimPageNo').val();
	}
	var orderStdt = $("#orderStdt").val();
	var orderEddt = $("#orderEddt").val();

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo
		,searchStart : orderStdt
		,searchEnd : orderEddt
	});

	$.ajax({
			url : '/order/orderPageGroupListAjax.do',
			type : 'post',
			dataType : 'json',
			data : varData,
			async : false,
			success : function(data) {
				var resultCode = data.RESULT_CODE;
				var totalCount = data.totalCount;

				if(totalCount > 0 && resultCode == "00000") {
					$('#orderNoDataDiv').hide();

					var listData = data.resultList;
					var pageInfoBean = data.pageInfoBean;
					makeStateHtml(data.stateMap , 'olOrderstepperArea');
					$('#olOrderstepperArea').show();
					//makeOrderHtml(listData);
					makeOrderHtml_group(listData);
					if(totalCount > 10){
						makePaging(pageInfoBean,'orderPaging','searchOrder');
						$("#orderPaging").show();
					}else{
						$("#orderPaging").hide();
					}
				}else{
					$('#orderUlArea').hide();
					$('#olOrderstepperArea').hide();
					$('#orderNoDataDiv').show();
					$('#orderPaging').html('');
		      		$('#orderPaging').hide();
				}
			}
		});

}

function searchSelfOrder(selPageNo) {
	var pageNo = $('#orderSelfPageNo').val();
	if (typeof selPageNo !== 'undefined') {
	  	pageNo = selPageNo;
	}else{
		pageNo = $('#orderSelfPageNo').val();
	}
	var selfStdt = $("#selfStdt").val();
	var selfEddt = $("#selfEddt").val();

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo
		,reqBuyType : 'UU'
		,sysRdateS : selfStdt
		,sysRdateE : selfEddt
	});

	$.ajax({
			url : '/order/reqSelfDlvryListPageAjax.do',
			type : 'post',
			dataType : 'json',
			data : varData,
			async : false,
			success : function(data) {
				var resultCode = data.RESULT_CODE;
				var totalCount = data.total;
				if(totalCount > 0 && resultCode == "00000") {
					$('#orderNoDataDiv_2').hide();

					var listData = data.resultList;
					var pageInfoBean = data.pageInfoBean;
					//makeStateHtml(data.stateCountList , 'olSelfStepperArea');
					makeStateSelfHtml(data.stateCountList ,data.stateNowCountList, 'olSelfStepperArea');
					$('#olSelfStepperArea').show();
					makeSelfDlvHtml(listData);
					if(totalCount > 10){
						makePaging(pageInfoBean,'orderPaging_2','searchSelfOrder');
						$("#orderPaging_2").show();
					}else{
						$("#orderPaging_2").hide();
					}
				}else{
					$('#selfUlArea').hide();
					$('#olSelfStepperArea').hide();
					$('#orderNoDataDiv_2').show();
		      		$('#orderPaging_2').html('');
		      		$('#orderPaging_2').hide();
				}
			}
		});

}
function makeStateHtml(listData,tgtId){
	$("#"+tgtId).empty();
	var statHtml = '';
	var dlvCnt_1 = 0;
	var dlvCnt_2 = 0;
	var dlvCnt_3 = 0;
	var dlvCnt_4 = 0;
	var dlvCnt_5 = 0;

	if(tgtId == 'olOrderstepperArea'){

		dlvCnt_1 = listData.PSTATE01;
		dlvCnt_2 = listData.PSTATE02;
		dlvCnt_3 = listData.PSTATE03;
		dlvCnt_4 = listData.PSTATE04;
		dlvCnt_5 = listData.PSTATE05;

		var spanClass  = '';
	    spanClass = dlvCnt_1 > 0 ? 'c-stepper__item is-active':'c-stepper__item';
	    statHtml += '<li class="'+spanClass+'">';
	    statHtml += '<span class="c-stepper__title">접수대기</span>';
	    statHtml += '<span class="c-stepper__num">'+dlvCnt_1+'</span>';
	    statHtml += '</li>';
	    spanClass = dlvCnt_2 > 0 ? 'c-stepper__item is-active':'c-stepper__item';
	    statHtml += '<li class="'+spanClass+'">';
	    statHtml += '<span class="c-stepper__title">배송대기</span>';
	    statHtml += '<span class="c-stepper__num">'+dlvCnt_2+'</span>';
	    statHtml += '</li>';
	    spanClass = dlvCnt_3 > 0 ? 'c-stepper__item is-active':'c-stepper__item';
	    statHtml += '<li class="'+spanClass+'">';
	    statHtml += '<span class="c-stepper__title">배송중</span>';
	    statHtml += '<span class="c-stepper__num">'+dlvCnt_3+'</span>';
	    statHtml += '</li>';
	    spanClass = dlvCnt_4 > 0 ? 'c-stepper__item is-active':'c-stepper__item';
	    statHtml += '<li class="'+spanClass+'">';
	    statHtml += '<span class="c-stepper__title">개통대기</span>';
	    statHtml += '<span class="c-stepper__num">'+dlvCnt_4+'</span>';
	    statHtml += '</li>';
	    spanClass = dlvCnt_5 > 0 ? 'c-stepper__item is-active':'c-stepper__item';
	    statHtml += '<li class="'+spanClass+'">';
	    statHtml += '<span class="c-stepper__title">개통완료</span>';
	    statHtml += '<span class="c-stepper__num">'+dlvCnt_5+'</span>';
	    statHtml += '</li>';

 	}

    $("#"+tgtId).html(statHtml);


}

function makeStateSelfHtml(listSelfData,listNowData,tgtId){

	$("#"+tgtId).empty();
	var statHtml = '';
	var dlvCnt_1 = 0;
	var dlvCnt_2 = 0;
	var dlvCnt_3 = 0;
	var dlvCnt_4 = 0;


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

	$(listSelfData).each(function(index) {
    	var rsCode = this.state;
		if(rsCode == '01'){
			dlvCnt_1 += this.stateCount;
		}else if(rsCode == '02'){
			dlvCnt_3 += this.stateCount;
		}else if(rsCode == '03' || rsCode == '04'){
			dlvCnt_4 += this.stateCount;
		}
    });

	$(listNowData).each(function(index) {
    	var rsCode = this.state;
		if(rsCode == '01' || rsCode == '06'){
			dlvCnt_1 += this.stateCount;
		}else if(rsCode == '03'){
			dlvCnt_2 += this.stateCount;
		}else if(rsCode == '02'){
			dlvCnt_3 += this.stateCount;
		}else if(rsCode == '04'){
			dlvCnt_4 += this.stateCount;
		}
    });

    var spanClass  = '';
    spanClass = dlvCnt_1 > 0 ? 'c-stepper__item is-active':'c-stepper__item';
    statHtml += '<li class="'+spanClass+'">';
    statHtml += '<span class="c-stepper__title">접수대기</span>';
    statHtml += '<span class="c-stepper__num">'+dlvCnt_1+'</span>';
    statHtml += '</li>';
    spanClass = dlvCnt_2 > 0 ? 'c-stepper__item is-active':'c-stepper__item';
    statHtml += '<li class="'+spanClass+'">';
    statHtml += '<span class="c-stepper__title">배송대기</span>';
    statHtml += '<span class="c-stepper__num">'+dlvCnt_2+'</span>';
    statHtml += '</li>';
    spanClass = dlvCnt_3 > 0 ? 'c-stepper__item is-active':'c-stepper__item';
    statHtml += '<li class="'+spanClass+'">';
    statHtml += '<span class="c-stepper__title">배송중</span>';
    statHtml += '<span class="c-stepper__num">'+dlvCnt_3+'</span>';
    statHtml += '</li>';
    spanClass = dlvCnt_4 > 0 ? 'c-stepper__item is-active':'c-stepper__item';
    statHtml += '<li class="'+spanClass+'">';
    statHtml += '<span class="c-stepper__title">배송완료</span>';
    statHtml += '<span class="c-stepper__num">'+dlvCnt_4+'</span>';
    statHtml += '</li>';

	$("#"+tgtId).html(statHtml);
}


function makeOrderHtml(listData){
	var html = "";
	$(listData).each(function(index) {

		html += '<li class="c-list__item">';
		html += '<a class="c-list__head c-list__anchor" href="#" onClick="orderView(\''+this.requestKey+'\')">';
		html += '<div class="c-list__title">';
		html += '<span class="c-hidden">주문번호</span>';
		//html += '<b>'+this.requestKey+'</b>';
		html += '<b>'+this.resNo+'</b>';
		html += '<div class="c-list__sub">';
		html += '<span class="c-hidden">주문일자</span>'+this.sysRdate;
		html += '</div>';
		html += '</div>';
		html += '<span class="c-hidden">상세보기</span>';
		html += '</a>';
		html += '<div class="c-list__body">';

		html += '<div class="detail">';
		html += '<div class="detail__image">';
		html += '<img src="'+this.imgPath+'" alt="" aria-hidden="true">';
		html += '</div>';
		html += '<div class="detail__title">';
		html += '<span class="detail__sub-title c-hidden">상품명/금액</span>';
		if(this.reqBuyType == 'UU'){
			html += '<b>유심</b>';
            html += '<span class="detail__sub">'+emptyToTyphoon(this.rateNm)+'</span>';
		}else if(this.reqBuyType == 'MM'){
			html += '<b>'+emptyToTyphoon(this.prodNm)+'</b>';
            html += '<span class="detail__sub">'+emptyToTyphoon(this.atribValNmOne)+'/'+emptyToTyphoon(this.atribValNmTwo)+'</span>';
		}
		if(this.reqBuyType == 'UU'){
			//html += '<b>'+numberWithCommas(this.settlAmt)+'원</b>';
			//html += '<b>'+numberWithCommas(parseInt(this.baseAmtInt + this.baseAmtInt*0.1))+'원</b>';
			html += '<b>'+numberWithCommas(this.mspSaleSubsdMstDto.payMnthChargeAmt)+'원</b>';
		}else if(this.reqBuyType == 'MM'){
			//html += '<b>'+numberWithCommas(this.modelPriceInt + this.modelPriceInt*0.1)+'원</b>';
			html += '<b>'+numberWithCommas(this.mspSaleSubsdMstDto.payMnthAmt+this.mspSaleSubsdMstDto.payMnthChargeAmt)+'원</b>';
		}

		html += '</div>';
		html += '<div class="detail__item">';
		html += '<span class="detail__sub-title c-hidden">배송상태</span>';

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
			html += '<b class="u-co-point-4">접수대기</b>';
		}else if(rsCode == 'PSTATE02'){
			html += '<b class="u-co-point-4">배송대기</b>';
		}else if(rsCode == 'PSTATE03'){
			html += '<b class="u-co-point-4">배송중</b>';
		}else if(rsCode == 'PSTATE04'){
			html += '<b class="u-co-point-4">개통대기</b>';
		}else if(rsCode == 'PSTATE05'){
			html += '<b class="u-co-point-4">개통완료</b>';
		}else if(rsCode == 'PSTATE06'){
			html += '<b class="u-co-point-4">신청취소</b>';
		}

		html += '</div>';

		html += '<div class="c-button-wrap">';
		//if(emptyToTyphoon(this.percelUrl) != '-'){
		if(rsCode == 'PSTATE03' || rsCode == 'PSTATE04' || rsCode == 'PSTATE05'){
			if(emptyToTyphoon(this.percelUrl) != '-' && emptyToTyphoon(this.dlvryNo) != '-'){
				html += '<button type="button" class="c-button c-button-round--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')">배송조회</button>';
			}else{
				html += '<button type="button" class="c-button c-button-round--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
			}
		}else{
			html += '<button type="button" class="c-button c-button-round--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
		}
		html += '</div>';
		html += '</div>';
		html += '</div>';
		html += '</li>';

	});
	$('#orderUlArea').html(html);
	$('#orderUlArea').show();
}

function makeOrderHtml_group(listData){
	var html = "";
	var preReqKey = '';
	$(listData).each(function(index) {
		if(preReqKey != this.requestKey){

			if(index != 0){
				html += '</div>';
				html += '</li>';
			}


			html += '<li class="c-list__item">';
			html += '<a class="c-list__head c-list__anchor" href="#" onClick="orderView(\''+this.requestKey+'\')">';
			html += '<div class="c-list__title">';
			html += '<span class="c-hidden">주문번호</span>';
			//html += '<b>'+this.requestKey+'</b>';
			html += '<b>'+this.resNo+'</b>';
			html += '<div class="c-list__sub">';
			html += '<span class="c-hidden">주문일자</span>'+this.sysRdate;
			html += '</div>';
			html += '</div>';
			html += '<span class="c-hidden">상세보기</span>';
			html += '</a>';
			html += '<div class="c-list__body">';
		}
		html += '<div class="detail">';
		html += '<div class="detail__image">';
		if(this.reqBuyType == 'UU'){
			html += '<img src="/resources/images/portal/content/temp_usim.png" alt="유심 실물 사진" aria-hidden="true">';
		}else{
			html += '<img src="'+this.imgPath+'" alt="'+emptyToTyphoon(this.prodNm)+' 실물 사진" aria-hidden="true">';
		}
		html += '</div>';
		html += '<div class="detail__title">';
		html += '<span class="detail__sub-title c-hidden">상품명/금액</span>';
		if(this.reqBuyType == 'UU'){
			html += '<b>유심</b>';
            html += '<span class="detail__sub">'+emptyToTyphoon(this.rateNm)+'</span>';
		}else if(this.reqBuyType == 'MM'){
			html += '<b>'+emptyToTyphoon(this.prodNm)+'</b>';
            html += '<span class="detail__sub">'+emptyToTyphoon(this.atribValNmOne)+'/'+emptyToTyphoon(this.atribValNmTwo)+'</span>';
		}
		if(this.reqBuyType == 'UU'){
			//html += '<b>'+numberWithCommas(this.settlAmt)+'원</b>';
			//html += '<b>'+numberWithCommas(parseInt(this.baseAmtInt + this.baseAmtInt*0.1))+'원</b>';
			html += '<b>'+numberWithCommas(this.mspSaleSubsdMstDto.payMnthChargeAmt)+'원</b>';
		}else if(this.reqBuyType == 'MM'){
			//html += '<b>'+numberWithCommas(this.modelPriceInt + this.modelPriceInt*0.1)+'원</b>';
			if(this.selfYn == 'Y'){
				html += '<b>'+numberWithCommas(this.settlAmt)+'원</b>';
			}else{
				html += '<b>'+numberWithCommas(this.mspSaleSubsdMstDto.payMnthAmt+this.mspSaleSubsdMstDto.payMnthChargeAmt)+'원</b>';
			}
		}

		html += '</div>';
		html += '<div class="detail__item">';
		html += '<span class="detail__sub-title c-hidden">배송상태</span>';
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
			html += '<b class="u-co-point-4">접수대기</b>';
		}else if(rsCode == 'PSTATE02'){
			html += '<b class="u-co-point-4">배송대기</b>';
		}else if(rsCode == 'PSTATE03'){
			html += '<b class="u-co-point-4">배송중</b>';
		}else if(rsCode == 'PSTATE04'){
			html += '<b class="u-co-point-4">개통대기</b>';
		}else if(rsCode == 'PSTATE05'){
			html += '<b class="u-co-point-4">개통완료</b>';
		}else if(rsCode == 'PSTATE06'){
			html += '<b class="u-co-point-4">신청취소</b>';
		}

		html += '</div>';

		html += '<div class="c-button-wrap">';
		//if(emptyToTyphoon(this.percelUrl) != '-'){
		if(rsCode == 'PSTATE03' || rsCode == 'PSTATE04' || rsCode == 'PSTATE05'){
			if(emptyToTyphoon(this.percelUrl) != '-' && emptyToTyphoon(this.dlvryNo) != '-'){
				html += '<button type="button" class="c-button c-button-round--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')">배송조회</button>';
			}else{
				html += '<button type="button" class="c-button c-button-round--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
			}
		}else{
			html += '<button type="button" class="c-button c-button-round--sm c-button--white" href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
		}
		html += '</div>';
		html += '</div>';

		preReqKey = this.requestKey;

	});
	$('#orderUlArea').html(html);

	$("#orderUlArea > li > div > div > div.detail__image > img").each(function(){
	$(this).error(function(){
		$(this).unbind("error");
		$(this).attr("src","/resources/images/portal/content/img_phone_noimage.png");
		});
	});


	$('#orderUlArea').show();
}

function makeSelfDlvHtml(listData){
	var html = "";

	$(listData).each(function(index) {

		html += '<li class="c-list__item">';
		html += '<a class="c-list__head c-list__anchor" href="#" onClick="selfOrderView(\''+this.selfDlvryIdx+'\')" >';
		html += '<div class="c-list__title">';
		html += '<span class="c-hidden">주문번호</span>';
		html += '<b>'+this.selfDlvryIdx+'</b>';
		html += '<div class="c-list__sub">';
		html += '<span class="c-hidden">주문일자</span>'+relpceTimestamp(this.sysRdate);
		html += '</div>';
		html += '</div>';
		html += '<span class="c-hidden">상세보기</span>';
		html += '</a>';
		html += '<div class="c-list__body">';
		html += '<div class="detail">';
		html += '<div class="detail__image">';
		html += '<img src="../../resources/images/portal/content/temp_usim.png" alt="유심 실물 사진" aria-hidden="true">';
		html += '</div>';
		html += '<div class="detail__title">';

		var usimProdId = this.usimProdId;
		var usimDataObj;
	    $(usimData).each(function() {
	      if(this.dtlCd == usimProdId.trim()){
	        usimDataObj = this;
	      }
	    });

		html += '<span class="detail__sub-title c-hidden">상품명/금액</span>';
		html += '<b>'+emptyToTyphoon(usimDataObj.dtlCdNm)+'</b>';
		//html += '<b>'+numberWithCommas(usimDataObj.expnsnStrVal1)+'원</b>';
		html += '<b>'+numberWithCommas(emptyToTyphoon(this.usimPrice))+'원</b>';
		html += '</div>';
		html += '<div class="detail__item">';
		html += '<span class="detail__sub-title c-hidden">배송상태</span>';

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
					html += '<b class="u-co-point-4">접수대기</b>';
				}else if(rsCode == '02'){
					html += '<b class="u-co-point-4">배송중</b>';
				}else if(rsCode == '03' || rsCode == '04'){
					html += '<b class="u-co-point-4">배송완료</b>';
				}
			}else{
				html += '<b class="u-co-point-4">신청취소</b>';
			}

		}else if(dlvryKind == '02'){

			if(rsCode == '01' || rsCode == '06'){
				html += '<b class="u-co-point-4">접수대기</b>';
			}else if(rsCode == '03'){
				html += '<b class="u-co-point-4">배송대기</b>';
			}else if(rsCode == '02'){
				html += '<b class="u-co-point-4">배송중</b>';
			}else if(rsCode == '04'){
				html += '<b class="u-co-point-4">배송완료</b>';
			}else if(rsCode == '05' || rsCode == '07'){
				html += '<b class="u-co-point-4">신청취소</b>';
			}

		}

		html += '</div>';
		html += '<div class="c-button-wrap">';
		if(dlvryKind == '01'){
			if(rsCode == '02' || rsCode == '03' || rsCode == '04'){
				if(emptyToTyphoon(this.percelUrl) != '-' && emptyToTyphoon(this.dlvryNo) != '-'){
					html += '<button class="c-button c-button-round--sm c-button--white" type="button"  href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')">배송조회</button>';
				}else{
				html += '<button class="c-button c-button-round--sm c-button--white" type="button"  href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
				}
			}else{
				html += '<button class="c-button c-button-round--sm c-button--white" type="button"  href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
			}
		}else if(dlvryKind == '02'){
			if(rsCode == '02' || rsCode == '04'){
				if(emptyToTyphoon(this.percelUrl) != '-' && emptyToTyphoon(this.dlvryNo) != '-'){
					html += '<button class="c-button c-button-round--sm c-button--white" type="button"  href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')">배송조회</button>';
				}else{
					html += '<button class="c-button c-button-round--sm c-button--white" type="button"  href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
				}
			}else{
				html += '<button class="c-button c-button-round--sm c-button--white" type="button"  href="javascript:void(0);" onClick="goDlvPage(\''+this.percelUrl+this.dlvryNo+'\')" disabled>배송조회</button>';
			}
		}
		html += '</div>';
		html += '</div>';
		html += '</div>';
		html += '</li>';

	});
	$('#selfUlArea').html(html);
	$('#selfUlArea').show();
}

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

	$('#modalArs').removeClass('c-modal--xlg');
	$('#modalArs').addClass('c-modal--md');

	openPage('pullPopupByPost','/orderView.do',parameterData);


}

function selfOrderView(key){

	var parameterData = ajaxCommon.getSerializedData({
		 selfDlvryIdx : key
	});

	$('#modalArs').removeClass('c-modal--xlg');
	$('#modalArs').addClass('c-modal--md');

	openPage('pullPopupByPost','/order/reqSelfDlvryView.do',parameterData);


}

function goDlvPage(goUrl){
	window.open(goUrl);
	//window.location.href  = goUrl;
}

function initUsimData(){
	var varData = ajaxCommon.getSerializedData({
		grpCd : 'usimProdInfo'
	});

	$.ajax({
		url : '/getComCodeListAjax.do',
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
    var date = new Date(t);
    var year = date.getFullYear();
    var month = "0" + (date.getMonth()+1);
    var day = "0" + date.getDate();
    return year + "." + month.substr(-2) + "." + day.substr(-2);
}