/*
INDEX

-편의점 찾기 팝업창의 검색 버튼 클릭 이벤트
-현재위치 수집
-현재위치 수집 에러
-위치정보 이용약관 동의버튼 클릭 직후 현재위치 수집
-매장 상세보기 팝업
-편의점 찾기 팝업 탭 클릭 이벤트
-현재위치 수집 약관 동의 버튼 이벤트
-시/도 주소 onchange 이벤트
-더보기 클릭 이벤트
-팝업 페이지 상단 편의점 회사 선택 이벤트
-공지사항
-faq 호출 데이터 div태그로 감싸는 함수
-faq 데이터 조회
-faq 검색버튼 클릭이벤트
-faq 조회수 증가 이벤트
-faq 카테고리 클릭 이벤트
*/
 var modalScrollY = 0;
var moveScrollY = 0;
var map;


function initSearchStore(){
	$("#pageNo").val('1');
	searchStore();
}

// 편의점 찾기 1차 팝업 검색 버튼 클릭 이벤트
function searchStore() {
	var pageNo = $('#pageNo').val();
	var varData = ajaxCommon.getSerializedData({
		schDivStr : $('#schDivStr').val(),	// 도 단위 지역
		searchStr : $('#searchStr').val(),	// 주소 또는 매장명
		storDiv : $('#storDiv').val(),	// 매장 코드
		subAddr : $('#subAddr').val(),	// 시 단위 지역
		orderType : $('#orderType').val(),	// 탭 선택상태
		lotateVal : $('#lotateVal').val(), // 위,경도 합
		latitVal : $("#latitVal").val(), // 위도 값
		lngitVal : $("#lngitVal").val(),	// 경도 값
		pageNo : pageNo
	});
	$
			.ajax({
				url : '/direct/selectStoreListAjax.do',
				type : 'GET',
				dataType : 'json',
				data : varData,
				async : false,
				success : function(jsonObj) {
					//if(jsonObj.RESULT_CODE == "0001"){
					if (jsonObj.total > 0) {
						list = jsonObj.resultList;
						var chgTotal = jsonObj.total;
						var chgListCount = jsonObj.listCount;

						/*$('#pageNo').val(jsonObj.pageNo);*/
						$('#maxPage').html(jsonObj.endPage);
						var innerHTML = '';
						$('#list-count').html(
								chgListCount.toLocaleString('ko-KR')
								);
						$("#total-count").html(
								chgTotal.toLocaleString('ko-KR')
								);
						if (jsonObj.endPage == $("#pageNo").val()) {
							$("#searchNext").hide();
						} else {
							$("#searchNext").show();
						}
						$("#totalCount").html(chgTotal.toLocaleString('ko-KR'));

						var html = "";
						if (pageNo == 1) {
							if($('#orderType').val() == 'NORMAL'){
								html += '<h3 class="c-heading c-heading--fs17 c-heading--regular u-mt--20">검색결과</h3>';
								html += '<p class="c-text c-text--fs15 u-mt--8">'
										+ $('#schDivStr').val();
								html += '<b class="u-co-point-4">총 ' + chgTotal.toLocaleString('ko-KR')
										+ '건</b>';
								html += '  	검색되었습니다.';
								html += '</p>';
								html += '<hr class="c-hr c-hr--title">';
							}
							html += '<ul class="store-list" id="store-list">';
							html += '</ul>';
							$('#searchArea').html(html);
						}
						html = "";
						for (var i = 0; i < list.length; i++) {
							html += '<li class="store-list__item">';
							html += '<dl class="store-list__info">';
							html += '<dt>';
							html += '<i class="c-icon '
							switch (list[i].storDiv) {
							case '3':
								html += 'c-icon--cspace" aria-hidden="true"></i>';
								html += '<span class="c-hidden">cspace</span>';
								break;
							case '4':
								html += 'c-icon--ministop" aria-hidden="true"></i>';
								html += '<span class="c-hidden">ministop</span>';
								break;
							case '5':
								html += 'c-icon--gs25" aria-hidden="true"></i>';
								html += '<span class="c-hidden">gs25</span>';
								break;
							case '6':
								html += 'c-icon--sevenelleven" aria-hidden="true"></i>';
								html += '<span class="c-hidden">sevenelleven</span>';
								break;
							case '7':
								html += 'c-icon--storyway" aria-hidden="true"></i>';
								html += '<span class="c-hidden">storyway</span>';
								break;
							case '8':
								html += 'c-icon--cu" aria-hidden="true"></i>';
								html += '<span class="c-hidden">cu</span>';
								break;
							case '9':
								html += 'c-icon--emart" aria-hidden="true"></i>';
								html += '<span class="c-hidden">emart</span>';
								break;
							}
							html += list[i].storNm;
							html += '</dt>';
							html += '<dd>';
							html += '    <p>'+ list[i].storRoadnAdr + '</p>';
							html += '    <p>'+ list[i].telNo + '</p>';
							html += '  </dd>';
							html += '</dl>';
							html += '<dl class="store-list__sub">';
							html += '  <dt>남은 수량</dt>';
							html += '  <dd>';
							html += '    <span>일반 '
									+ list[i].nfcDisableVal.toLocaleString('ko-KR')
									+ '</span>';
							html += '    <span>NFC '
									+ list[i].nfcAbleVal.toLocaleString('ko-KR')
									+ '</span>';
							html += '  </dd>';
							html += '</dl>';
							html += '<button class="c-button c-button--xsm c-button--white c-button-round--xsm" type="button" onclick="selectStoreDetail(\''
									+ list[i].storNm
									+ '\',\''
									+ list[i].storRoadnAdr
									+ '\',\''
									+ list[i].telNo
									+ '\',\''
									+ list[i].nfcDisableVal
									+ '\',\''
									+ list[i].nfcAbleVal
									+ '\',\''
									+ list[i].latitVal
									+ '\',\''
									+ list[i].lngitVal
									+ '\',\''
									+ list[i].storDiv
									+ '\')">';
							html += '  <i class="c-icon c-icon--map" aria-hidden="true"></i>';
							html += '지도보기';
							html += '</button>';
							html += '</li>';
						}
						if (pageNo == 1) {
							$('#store-list').html(html);
						} else {
							$('#store-list').append(html);
						}
					}else{
						html = "";
						html += '<h3 class="c-heading c-heading--fs17 c-heading--regular u-mt--20">검색결과</h3>';
						html += '<hr class="c-hr c-hr--title">';
						html += '<div class="c-nodata">';
						html += '<i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
						html += '<p class="c-nodata__text">검색 결과가 없습니다.</p>';
						html += '</div>';

						$('#searchArea').html(html);
						$("#searchNext").hide();
					}
				}
			});

};

// 현재위치 수집
function locationLoadSuccess(pos) {
	// 현재 위치 받아오기

	var latitude = pos.coords.latitude
	var longitude = pos.coords.longitude
	var lotateVal = latitude + longitude;

	$("#latitVal").val(latitude);
	$("#lngitVal").val(longitude);
	$('#lotateVal').val(lotateVal);
	$('#searchStr').val('');
	$('#pageNo').val('1');
	$('#storDiv').val('');
	$('#schDivStr').val('전국').prop('selected',true);
	$('#subAddr').val('');
	$('#subAddr').prop('disabled',true);
	$('#searchDiv').css('display','none');
	$("#searchDiv").removeClass("u-block");
	$('#allArea').click();
	$("#tabC2panel").hide();
	$('#tabC1panel').show();
	//selectStor($('#allArea'));
	searchStore();
};

// 현재위치 수집 에러
function locationLoadError(pos) {

	MCP.alert('위치 정보를 가져오는데 실패했습니다.', function(){
		$("#mainTab1").trigger("click");
	});

};

// 위치정보 이용약관 동의버튼 클릭 직후 현재위치 수집
function selectMyArea() {
	navigator.geolocation.getCurrentPosition(locationLoadSuccess,
			locationLoadError);

	/*		navigator.geolocation.getCurrentPosition(function(){return false;},
			locationLoadError);*/
}

//판매처 찾기 팝업
function searchStorePop(){
	openPage('pullPopup','/direct/searchStoreHtml.do','', '1');
	$(".c-modal--xlg").addClass("c-modal--md");
	$(".c-modal--md").removeClass("c-modal--xlg");



}

// 매장 상세보기 팝업
function selectStoreDetail(storNm, storRoadnAdr, telNo, nfcDisableVal, nfcAbleVal,latitVal,lngitVal, storDiv) {

	openPage('pullPopup2nd','/direct/mapHtml.do','', '1');
	/*$("#mainTab1").removeClass("is-active");*/
	$(".c-modal--xlg").addClass("c-modal--md");
	$(".c-modal--md").removeClass("c-modal--xlg");
	$('#mapStoreName').html(storNm);
	$('#mapAddr').html(storRoadnAdr);
	$('#mapPhone').html(telNo);
	nfcDisableVal = nfcDisableVal*1;
	nfcAbleVal = nfcAbleVal*1;
	$('#mapNormal').html('일반 ' + nfcDisableVal.toLocaleString('ko-KR'));
	$('#mapNfc').html('NFC' + nfcAbleVal.toLocaleString('ko-KR'));
	switch (storDiv) {
	case '3':
		$(".stor-icon").addClass("c-icon--cspace");
		break;
	case '4':
		$(".stor-icon").addClass("c-icon--ministop");
		break;
	case '5':
		$(".stor-icon").addClass("c-icon--gs25");
		break;
	case '6':
		$(".stor-icon").addClass("c-icon--sevenelleven");
		break;
	case '7':
		$(".stor-icon").addClass("c-icon--storyway");
		break;
	case '8':
		$(".stor-icon").addClass("c-icon--cu");
		break;
	case '9':
		$(".stor-icon").addClass("c-icon--emart");
		break;
	}
	$(document).ready(function() {

		if(!isEmpty(latitVal) && !isEmpty(lngitVal) && latitVal != 'null' && lngitVal != 'null'){
			$(".map").show();
			//map = mapInit(makerName,latLngX, latLngY);
			map = mapInit(latitVal, lngitVal);
			//var coords = new daum.maps.LatLng(latLngY.y,latLngX);
			var coords = new daum.maps.LatLng(latitVal,lngitVal);
			marker = new daum.maps.Marker({
				map : map,
				position : coords
			});
			infowindow = new daum.maps.InfoWindow({
				content : '<div align="center" style="padding:5px;">'+storNm+'</div>'
			});
			infowindow.open(map,marker);
			// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
			map.setCenter(coords);
		}else{
			$(".map").hide();
		}
	});

}

// 매장별/지역별, 현재위치 탭 클릭 이벤트
function setModalScroll(type) {


	modalScrollY = $('.c-modal__body ').scrollTop();
	setTimeout(function() {
		$('.c-modal__body ').scrollTop(moveScrollY);
		moveScrollY = modalScrollY;
	}, 1);

	if (type == 1) {
		$('#orderType').val('NORMAL');
		$.ajax({
			url : '/direct/searchStoreHtml.do',
			type : 'GET',
			dataType : 'text',
			async : false,
			success : function(resp) {
				var respCont = $(resp).find('.store-modal').html();

				$('.store-modal').html(respCont);
			}
		});
		$(".c-modal__footer").remove();
	} else {

		$('#orderType').val('AREA');
		$('#pageNo').val('1');
		var isCheckTerms = $('#isCheckTerms').val();
		if(isCheckTerms == 'false'){
			var html = "";
			var termDat = ajaxCommon.getSerializedData({
			cdGroupId1:'TERMSNEA'
			,cdGroupId2: 'TERMSNEA01'
	    });

	ajaxCommon.getItem({
	        id:'storeInfoTerms'
	        ,cache:false
	        ,url:'/termsPop.do'
	        ,data: termDat
	        ,dataType:"json"
	    }
	    ,function(data){

			html+='<h3 class="c-heading c-heading--fs20 u-mt--48">위치정보 이용약관</h3>';
			html+='<p class="c-text c-text--fs17 u-co-gray u-mt--12">kt M모바일 편의점 매장찾기는 위치기반 서비스를 제공함에 있어 개인 위치정보주체자(이하: 이용자)에게 다음과 같은 내용을 고지하고 개인위치정보 이용에 대한 사전 동의를 요청합니다.</p>';
			html+=data.docContent;
			html+='<div class="c-form">';
			html+='<input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">';
			html+='<label class="c-label" for="chkAgree">가까운 매장 찾기를 위한 위치정보 이용약관에 동의합니다.</label>';
			html+='</div>';

			$('#tabC2panel').html(html);
			$('#tabC1panel').hide();
			$('#tabC2panel').show();
	});

			var butHtml = "";
			butHtml += '<div class="c-modal__footer">';
			butHtml += '<div class="c-button-wrap">';
			butHtml += '<button class="c-button c-button--lg c-button--gray u-width--220" onclick="setModalScroll(1)">동의 안 함</button>';
			butHtml += '<button class="c-button c-button--lg c-button--primary u-width--220" onclick="checkTerms()">동의 함</button>';
			butHtml += '</div>';
			butHtml += '</div>';
			$(".c-modal__body").after(butHtml);
		}else{

			selectMyArea();
		}

	}

	$('.c-tabs__button').removeClass('is-active');
	$('#mainTab' + type).addClass('is-active');

}

// 현재위치 수집 약관 동의 버튼 이벤트
function checkTerms(){
	if(!$('#chkAgree').is(':checked')){
		new KTM.Alert("약관에 동의해 주세요.");
		return false;
	}
	$.ajax({
		url : '/direct/checkTerms.do',
		type : 'GET',
		dataType : 'json',
		async : false,
		success : function(jsonObj) {
			if(jsonObj.result == '0001'){
				$(".c-modal__footer").remove();
				$('#isCheckTerms').val('true');
/*				$('#tabC1panel').show();
				$('#tabC2panel').hide();*/
				$('#mainTab2').click();
			}else{
				new KTM.Alert("처리 중 오류가 발생했습니다.");
				return false;
			}
		}
	});
}

// 시/도 주소 onchange 이벤트
function selectSubAddrAjax() {
	var varData = ajaxCommon.getSerializedData({
		schDivStr : $('#schDivStr').val(),
/*		searchStr : $('#searchStr').val(),
		storDiv : $('#storDiv').val()*/
	});

	if($("#schDivStr").val() == '전국'){
		$('#subAddr').html('');
		$('#subAddr').prop('disabled',true);
	}else{
		$.ajax({
			url : '/direct/selectSubAddrAjax.do',
			type : 'GET',
			dataType : 'json',
			data : varData,
			async : false,
			success : function(jsonObj) {
				if (jsonObj.RESULT_CODE == "0001") {

					list = jsonObj.resultList;
					$('#subAddr').html('');
					$('#subAddr').append(
							"<option value='0'>시/군/구</option>");
					if (list.length > 0) {
						$('#subAddr').prop('disabled',false);

						for (var i = 0; i < list.length; i++) {
							if(list[i].subAddr.trim() != '' && list[i] != null){
								$('#subAddr').append(
									"<option value='"+list[i].subAddr+"'>"
											+ list[i].subAddr
											+ "</option>");
							}
						}
					}else{
						$('#subAddr').prop('disabled',true);
					}
				}else{
					$('#subAddr').prop('disabled',true);
				}
			}
		});
	}
}

//더보기
var goNextPage = function() {

	var pNo = $('#pageNo').val();
	var mPage = $('#maxPage').html();

	if (parseInt(pNo) + 1 > parseInt(mPage)) {
		alert('마지막 페이지입니다.');
		return;
	}
	$('#pageNo').val(parseInt(pNo) + 1);

	searchStore();
};

// 페이지 상단 편의점 회사 선택 이벤트
function selectStor(obj) {
	$('.c-button-round--sm').removeClass('is-active');

	$(obj).addClass('is-active');
	$(obj).focus();
	$('#storDiv').val($(obj).attr('storId'));
	var orderType = $('#orderType').val();
	var searchStr = $('#searchStr').val();
	var schDivStr = $('#schDivStr').val();
	var listCnt = $('.store-list__item').length;
	var isResult = $('#resultVal').length;
	if(orderType == 'NORMAL' && searchStr == '' && schDivStr == '전국' && listCnt == 0 && isResult == 0) {
		return false;
	}else{
		$('#pageNo').val('1');
		if($("#mainTab2").hasClass("is-active")){
			searchStore();
		}
	}
}

// 공지사항
var goNextNoticePage = function() {
	$('#noticePageNo').val(parseInt($('#noticePageNo').val()) + 1);

	goPageAjax();
};

var count = 10;

/*faq 호출 데이터 div태그로 감싸는 함수*/
function toHTML(sourceString) {
	var div = document.createElement("div");
	div.innerHTML = sourceString.trim();
	return div.firstChild;
}

/*faq 데이터 조회*/
function goPageAjax() {
	var pageNo = $('#noticePageNo').val();
	var sbstCtg = $('#sbstCtg').val();
	var searchNm = $('#searchNm').val();


	$('#noticeListTBody').html('');

	var varData = ajaxCommon.getSerializedData({
		pageNo : pageNo,
		searchNm : searchNm,
		searchYn : 'Y',
		sbstCtg : sbstCtg,
		dstnctKey : 'faq'
	});
	jQuery
			.ajax({
				id : 'paging',
				type : 'POST',
				cache : false,
				url : '/m/cs/faqListAjax.do',
				data : varData,
				dataType : "json",
				success : function(jsonObj) {
					var resultList = jsonObj.faqList;
					var html = "";
/*						$('#noticePageNo').val(jsonObj.pageNo);
						$('#maxPage').html(jsonObj.pageInfoBean.totalPageCount);*/
						var innerHTML = '';
						if (resultList) {

							/* 공지사항 내용이 없을시 text 추가 Start */
							if (resultList.length == 0) {
								/*데이터 없음 안내 노출*/
								$('.c-nodata').css('display','block');
								/*페이징 숨김*/
								$('#faq-paging').hide();
							} else {
								/*데이터 없음 안내 숨김*/
								$('.c-nodata').css('display','none');
								/*페이징 노출*/
								$('#faq-paging').show();
								var accEl = document
										.querySelector("#accordion_runtime_update");
								var accContainer = accEl
										.querySelector("#noticeListTBody");

								var instance = KTM.Accordion
										.getInstance(accEl);
								var pageNoBas = (jsonObj.pageInfoBean.pageNo - 1)*10 + 1;
								for (var i = 0; i < resultList.length; i++) {
									innerHTML = '<li class="c-accordion__item">';
									innerHTML += '<div class="c-accordion__head is-qtype">';
									innerHTML += '<span class="question-label">' + (pageNoBas + i) + '<span class="c-hidden">질문</span></span>';
									innerHTML += '<strong class="c-accordion__title">' + resultList[i].boardSubject + '</strong>';
									innerHTML += '<div class="c-accordion__info">';
									innerHTML += '<span>조회</span>';
									innerHTML += '<span id="faqCnt_' + resultList[i].boardSeq + '">' + resultList[i].boardHitCnt + '</span>';
									innerHTML += '</div>';
									innerHTML += '<button class="faqheader_' + resultList[i].boardSeq + ' runtime-data-insert c-accordion__button"  id="faqIdNum_'+resultList[i].boardSeq+'" onclick="updateHit(' + resultList[i].boardSeq + ')" type="button" aria-expanded="false" aria-controls="faqContent_' + resultList[i].boardSeq + '">';
/*									innerHTML += '<span class="c-hidden">' + resultList[i].boardSubject + ' 상세열기</span>';*/
									innerHTML += '</button>';
									innerHTML += '</div>';
									innerHTML += '<div class="c-accordion__panel expand" id="faqContent_' + resultList[i].boardSeq + '" aria-labelledby="faqheader_' + resultList[i].boardSeq + '">';
									innerHTML += '<div class="c-accordion__inside">';
									innerHTML += '<span class="box-prefix">A</span>';
									innerHTML += '<div class="box-content">'+unescapeHtml(resultList[i].boardContents)+'</div>';
									innerHTML += '</div>';
									innerHTML += '</div>';
									innerHTML += '</li>';
									innerHTML += '';

									/*innerHTML += '        <span class="info-item">';
									innerHTML += '          <span class="c-hidden">조회수</span>';
									innerHTML += '          <i class="c-icon c-icon--eye" aria-hidden="true"></i>';
									innerHTML += '          <span class="count-info" id="faqCnt_'+resultList[i].boardSeq+'">'
											+ resultList[i].boardHitCnt
											+ '</span>';
									innerHTML += '        </span>';
									innerHTML += '      </div>';
									innerHTML += '    </div>';
									innerHTML += '  </button>';
									innerHTML += '</div>';
									innerHTML += '<div class="c-accordion__panel c-expand expand">';
									innerHTML += '	<div class="c-accordion__inside box-answer">';
									innerHTML += '      <div class="box-prefix">A</div>';
									innerHTML += '      <div class="box-content">'+unescapeHtml(resultList[i].boardContents)+'</div>';
									innerHTML += '   </div>';
									innerHTML += '</div>';
									innerHTML += '</li>';*/
									accContainer
											.appendChild(toHTML(innerHTML));
									instance.update();
									count += 1;
								}
								/* 문의 내용없을시 text 추가 END */
								if(jsonObj.pageInfoBean.totalCount > 10){
									ajaxCommon.setPortalPaging($('#faq-paging'), jsonObj.pageInfoBean);
								}

							}

						/*	$("#totalCount").html(
									jsonObj.totalCount);
*/
						}

				}
			});
};

$(document).on("click", ".c-paging a", function(){
	$("#noticePageNo").val($(this).attr("pageNo"));
	if($(this).attr("pageNo")){
		goPageAjax();
	}
});

/*검색버튼 클릭이벤트*/
function faqSearch() {
	goPageAjax();
}

/*조회수 증가 이벤트*/
function updateHit(boardSeq) {
	if (!$('#faqIdNum_' + boardSeq).hasClass('is-active')) {
		var varData = ajaxCommon.getSerializedData({
			boardSeq : boardSeq
		});
		jQuery.ajax({
			id : 'hit',
			type : 'GET',
			cache : false,
			url : '/m/cs/updateFaqHitAjax.do',
			data : varData,
			dataType : "json",
			success : function(data) {
				if (data.updateResult == 1) {
					$('#faqCnt_'+boardSeq).html(
							parseInt($('#faqCnt_'+boardSeq)
									.html()) + 1);
				}
			}
		});
	}
}

// 자주묻는질문 카테고리 클릭 이벤트
function selectNoticeCtg(sbstCtg, obj) {

	$('#sbstCtg').val(sbstCtg);
	$('#searchNm').val('');
	$('#noticePageNo').val("1");
	$('.c-button-round--sm .c-hidden').remove();
	$('.c-button-round--sm').removeClass('is-active');
	$(obj).append('<span class="c-hidden">선택됨</span>');
	$(obj).addClass('is-active');
	goPageAjax();
}


$(document).ready(function (){
    goPageAjax();

    $(document).on("click", ".c-filter--accordion__button", function(){
		if($(this).attr("aria-expanded") == 'false'){
			$(this).parent().addClass("is-expanded");
			$(this).attr("aria-expanded","true");
		}else{
			$(this).parent().removeClass("is-expanded");
			$(this).attr("aria-expanded","false");
		}
	});

});

var selfTimeChk = function(orgnId){

	ajaxCommon.getItem({
		id:'isSimpleApply'
		,cache:false
		,url:'/appForm/isSimpleApplyAjax.do'
		,data: ""
		,dataType:"json"
		,async:false
	},function(jsonObj){

		if (!jsonObj.IsMacTime && !jsonObj.IsMnpTime) {
			MCP.alert('셀프개통이 불가능한 시간입니다.<br/><br/>셀프개통 가능한 시간은<br/>신규(08:00~21:50)<br/>번호이동(10:00~19:50) 입니다.');
		} else if (!jsonObj.IsMnpTime) {
			MCP.alert('신규 가입만 가능한 시간입니다.<br/><br/>셀프개통 가능한 시간은<br/>신규(08:00~21:50)<br/>번호이동(10:00~19:50) 입니다.', function (){
				location.href='/appForm/appFormDesignView.do?orgnId=' + orgnId;
			});
		} else {
			location.href='/appForm/appFormDesignView.do?orgnId=' + orgnId;
		}
	});



}

//null 체크
var isEmpty = function(data) {
	    if(typeof(data) === 'object'){
	        if(JSON.stringify(data) === '{}' || JSON.stringify(data) === '[]'){
	            return true;
	        }else if(!data){
	            return true;
	        }
	        return false;
	    }else if(typeof(data) === 'string'){
	        if(!data.trim()){
	            return true;
	        }
	        return false;
	    }else if(typeof(data) === 'undefined'){
	        return true;
	    }else if(isNaN(data) == true){ // 신규 NaN 처리
	    	return true;
		}else if(data === 0){ // 신규 0 처리
			return true;
		}else{
	        return false;
	    }
	}