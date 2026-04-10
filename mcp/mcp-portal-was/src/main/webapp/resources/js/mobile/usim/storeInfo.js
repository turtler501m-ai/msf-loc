/**
 *
 */
 var modalScrollY = 0;
var moveScrollY = 0;
var map;
var key = false;
var iosLoc = false;
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
		orderType : $('#orderType').val(),
		lotateVal : $('#lotateVal').val(),
		latitVal : $("#latitVal").val(), // 위도 값
		lngitVal : $("#lngitVal").val(),	// 경도 값
		pageNo : pageNo
	});
	$
			.ajax({
				url : '/m/direct/selectStoreListAjax.do',
				type : 'GET',
				dataType : 'json',
				data : varData,
				async : false,
				success : function(jsonObj) {

					if (jsonObj.total > 0) {
						list = jsonObj.resultList;
						var chgTotal = jsonObj.total;
						var chgListCount = jsonObj.listCount;
						/*$('#pageNo').val(jsonObj.pageNo);*/
						$('#maxPage').html(jsonObj.endPage);
						var innerHTML = '';
						$('#pageNav').html(
								chgListCount.toLocaleString('ko-KR') + "/"
										+ chgTotal.toLocaleString('ko-KR'));

						if (jsonObj.endPage == jsonObj.pageNo) {
							$("#searchNext").hide();
						} else {
							$("#searchNext").show();
						}
						$("#totalCount").html(chgTotal.toLocaleString('ko-KR'));

						var html = "";
						if (pageNo == 1) {
							if($('#orderType').val() == 'NORMAL'){
								html += '<strong class="store-result__heading">검색결과</strong>';
								html += '<p class="store-result__text"> '
										+ $('#schDivStr').val();
								html += '  <span>총 ' + chgTotal.toLocaleString('ko-KR')
										+ '건</span>';
								html += '  	검색되었습니다.';
								html += '</p>';
								html += '<hr class="c-hr c-hr--type2">';
							}
							html += '<ul class="store-list" id="store-list">';
							html += '</ul>';
							$('#searchArea').html(html);
						}
						html = "";
						for (var i = 0; i < list.length; i++) {
							html += '<li class="store-list__item">';
							html += '<i class="c-icon '
							switch (list[i].storDiv) {
							case '3':
								html += 'c-icon--cspace"';
								break;
							case '4':
								html += 'c-icon--ministop"';
								break;
							case '5':
								html += 'c-icon--gs25"';
								break;
							case '6':
								html += 'c-icon--sevenElleven"';
								break;
							case '7':
								html += 'c-icon--storyway"';
								break;
							case '8':
								html += 'c-icon--cu"';
								break;
							case '9':
								html += 'c-icon--emart"';
								break;
							}
							html += 'aria-hidden="true"></i>';
							html += '<dl class="store-list__info">';
							html += '<dt>' + list[i].storNm
									+ '</dt>';
							html += '<dd>';
							html += '    <p class="store-list__text">'
									+ list[i].storRoadnAdr + '</p>';
							html += '    <p class="store-list__text">'
									+ list[i].telNo + '</p>';
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
							html += '<button class="c-button c-button--sm" type="button" href="javascript:void(0);" onclick="selectStoreDetail(\''
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
							html += '  <span>지도보기</span>';
							html += '</button>';
							html += '</li>';
						}
						if (pageNo == 1) {
							$('#store-list').html(html);
						} else {
							$('#store-list').append(html);
						}
						if(chgTotal == chgListCount){
							$("#searchNext").hide();
						}else{
							$("#searchNext").show();
						}
					}else{
						html = "";
						html+='<strong class="store-result__heading" id="resultVal">검색결과</strong>';
						html+='<hr class="c-hr c-hr--type2">';
						html+='<div class="store-result__cont">검색 결과가 없습니다.</div>';
						$('#searchArea').html(html);
						$("#searchNext").hide();
					}
					KTM.LoadingSpinner.hide();
				}

			});

};

function getLocation(lat, lon){

		// 현재 위치 받아오기
		var latitude = lat;
		var longitude = lon;
		var lotateVal = lat + lon;

		//위치데이터 배치
		$("#latitVal").val(latitude);
		$("#lngitVal").val(longitude);
		$('#lotateVal').val(lotateVal);
		//검색조건 초기화
		$('#searchStr').val('');
		$('#pageNo').val('1');
		$('#storDiv').val('');
		$('#schDivStr').val('전국').attr('selected','selected');
		$('#subAddr').val('');
		$('#subAddr').attr('disabled','""');
		$('#searchDiv').css('display','none');
		$('#allArea').click();
		$("#locationFooter").remove();

		setTimeout(()=>{
			searchStore();
		},1000);
}

// 현재위치 수집
function locationLoadSuccess(pos) {

	getLocation(pos.coords.latitude,pos.coords.longitude);

};

// 현재위치 수집 에러
function locationLoadError(pos) {
	alert('위치 정보를 가져오는데 실패했습니다.');
	$("#mainTab1").trigger("click");
	KTM.LoadingSpinner.hide();
};

// 위치정보 이용약관 동의버튼 클릭 직후 현재위치 수집
function selectMyArea() {
	KTM.LoadingSpinner.show();
	navigator.geolocation.getCurrentPosition(locationLoadSuccess,
			locationLoadError);
}

// 매장 상세보기 팝업
function selectStoreDetail(storNm, storRoadnAdr, telNo, nfcDisableVal, nfcAbleVal,latitVal,lngitVal, storDiv) {

	openPage('pullPopup2nd','/m/direct/mapHtml.do','');

	$('#mapStoreName').html(storNm);
	$('#mapAddr').html(storRoadnAdr);
	$('#mapPhone').html(telNo);
	nfcDisableVal = nfcDisableVal*1;
	nfcAbleVal = nfcAbleVal*1;
	$('#mapNormal').html('일반 ' + nfcDisableVal.toLocaleString('ko-KR'));
	$('#mapNfc').html('NFC ' + nfcAbleVal.toLocaleString('ko-KR'));

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
		$(".stor-icon").addClass("c-icon--sevenElleven");
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

	//map = mapInit(makerName,latLngX, latLngY);
	if(!isEmpty(latitVal) && !isEmpty(lngitVal) && latitVal != 'null' && lngitVal != 'null'){

		$(".map").show();
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

}

function setModalScroll(type) {

	$('.c-tabs__button').removeClass('is-active');
	$('#mainTab' + type).addClass('is-active');
	modalScrollY = $('.c-modal__body ').scrollTop();
	setTimeout(function() {
		$('.c-modal__body ').scrollTop(moveScrollY);
		moveScrollY = modalScrollY;
	}, 1);

	if (type == 1) {
		$('#orderType').val('NORMAL');
		$.ajax({
			url : '/m/direct/searchStoreHtml.do',
			type : 'GET',
			dataType : 'text',
			async : false,
			success : function(resp) {
				var respCont = $(resp).find('.c-modal__body').html();
				$('.c-modal__body').html(respCont);
				$("#locationFooter").remove();
			}
		});
	} else {

		$('#orderType').val('AREA');
		$('#pageNo').val('1');
		var isCheckTerms = $('#isCheckTerms').val();
		if(isCheckTerms == 'false'){
			var html = "";
			var htmlFooter = "";
			html+='<div class="term-localinfo">';
			html+='    <h3 class="c-heading c-heading--type1">위치정보 이용약관</h3>';

			html+='    <p class="c-text c-text--type2"> kt M모바일 편의점 매장찾기는 위치기반 서비스를 제공함에 있어';
			html+=' 개인 위치정보주체자(이하: 이용자)에게 다음과 같은 내용을 고지하고';
			html+=' 개인위치정보 이용에 대한 사전 동의를 요청합니다.</p><!-- [2021-11-29] 이용약관 box 수정(디자인 변경 적용) 시작=====-->';
			$.ajax({
				url : '/m/direct/getFormDescAjax.do',
				type : 'POST',
				dataType : 'json',
				async : false,
				success : function(resp) {

					html += resp.locationTerm;
				}
			});
			html+='    <div class="c-form">';
			html+='      <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">';
			html+='      <label class="c-label" for="chkAgree">가까운 매장 찾기를 위한 위치정보 이용약관에 동의합니다.</label>';
			html+='    </div><!-- [2021-11-29] 버튼 클래스 추가(디자인 변경 적용) 시작=====-->';
			html+='  </div>';

			htmlFooter+='<div class="c-modal__footer" id="locationFooter">';
			htmlFooter+='    <div class="c-button-wrap">';
			htmlFooter+='      <button class="c-button c-button--gray c-button--lg u-width--120" type="button" href="javascript:void(0);" onclick="setModalScroll(1)">동의안함</button>';
			htmlFooter+='      <button class="c-button c-button--full c-button--primary" type="button" href="javascript:void(0);" onclick="checkTerms();">동의</button>';
			htmlFooter+='    </div><!-- [$2021-11-29] 버튼 클래스 추가(디자인 변경 적용) 끝=====-->';
			htmlFooter+='  </div>';
			$('#tabCVS2-panel').html(html);
			$('#tabCVS1-panel').hide();
			$('#tabCVS2-panel').show();
			$("#locationFooter").remove();
			$(".c-modal__body").after(htmlFooter);
			return false;
		}
		//location access right check
		// success => selectMyArea();
		var mobileAppChk = $("#mobileAppChk").val();
		if (mobileAppChk == 'A') {
				var result = requestPermission('LOC', 'store');
		}else{
			selectMyArea();
		}

	}

}

function store(){
	if(!key){
		MCP.alert("위치정보수집에 동의하셔야 조회가능합니다.", function(){
			$("#mainTab1").trigger("click");
		});
	}else{

		selectMyArea();
	}

}

// 현재위치 수집 약관 동의 버튼 이벤트
function checkTerms(){
	if($('#chkAgree').attr('checked') != 'checked'){
		new KTM.Alert("약관에 동의해 주세요");
		return false;
	}
	$.ajax({
		url : '/m/direct/checkTerms.do',
		type : 'GET',
		dataType : 'json',
		async : false,
		success : function(jsonObj) {
			if(jsonObj.result == '0001'){
				$('#isCheckTerms').val('true');
				$('#tabCVS1-panel').show();
				$('#tabCVS2-panel').hide();
				$(".c-modal__footer").remove();
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
			url : '/m/direct/selectSubAddrAjax.do',
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
						$('#subAddr').removeAttr('disabled');

						for (var i = 0; i < list.length; i++) {
							if(list[i].subAddr.trim() != '' && list[i] != null){
								$('#subAddr').append(
									"<option value='"+list[i].subAddr+"'>"
											+ list[i].subAddr
											+ "</option>");
							}
						}
					}else{
						$('#subAddr').attr('disabled','""');
					}
				}else{
					$('#subAddr').attr('disabled','""');
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
	$('.c-button--sm').removeClass('is-active');

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

function toHTML(sourceString) {
	var div = document.createElement("div");
	div.innerHTML = sourceString.trim();
	return div.firstChild;
}

function goPageAjax() {
	var pageNo = $('#noticePageNo').val();
	var sbstCtg = $('#sbstCtg').val();
	var searchNm = $('#searchNm').val();

	if (pageNo == "1") {
		$('#noticeListTBody').html('');
	}
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
						$('#noticePageNo').val(jsonObj.pageNo);
						$('#maxPage').html(jsonObj.pageInfoBean.totalPageCount);
						var innerHTML = '';
						$('#noticePageNav').html(
								jsonObj.listCount + "/"
										+ jsonObj.totalCount);
						if (resultList) {
							/* list size < 10 경우 더보기 버튼 hide 처리   Start*/
							if (jsonObj.pageInfoBean.totalPageCount == jsonObj.pageNo) {
								$("#c-button-wrap").hide();
							} else {
								$("#c-button-wrap").show();
							}
							/* 공지사항 내용이 없을시 text 추가 Start */
							if (resultList.length == 0) {
								$('.c-nodata').css('display',
										'block');
							} else {
								$('.c-nodata').css('display',
										'none');

								var accEl = document
										.querySelector("#accordion_runtime_update");
								var accContainer = accEl
										.querySelector("#noticeListTBody");

								var instance = KTM.Accordion
										.getInstance(accEl);

								for (var i = 0; i < resultList.length; i++) {
									innerHTML = '<li class="c-accordion__item" id="faqIdNum_'+resultList[i].boardSeq+'">';
									innerHTML += '<div class="c-accordion__head is-qtype" data-acc-header>';
									innerHTML += '<span class="p-prefix">Q</span>';
									innerHTML += '  <button class="c-accordion__button" type="button" aria-expanded="false" href="javascript:void(0);" onclick="updateHit('+resultList[i].boardSeq+')">';
									innerHTML += '    <div class="c-accordion__title">'
											+ resultList[i].boardSubject
											+ '<div class="info-line">';
									innerHTML += '        <span class="info-item">';
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
									innerHTML += '</li>';
									accContainer
											.appendChild(toHTML(innerHTML));
									instance.update();
									count += 1;
								}
								/* 문의 내용없을시 text 추가 END */
							}
							if(jsonObj.totalCount > 10){
								$("#c-button-wrap").show();
								$("#totalCount").html(
									jsonObj.totalCount);
							}else{
								$("#c-button-wrap").hide();
							}

						}

				}
			});
};
function faqSearch() {
	goPageAjax();
}
function updateHit(boardSeq) {
	if (!$('#faqIdNum_' + boardSeq).find(
			'.c-accordion__head').hasClass('is-active')) {
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

function selectNoticeCtg(sbstCtg, obj) {

	$('#sbstCtg').val(sbstCtg);
	$('#searchNm').val('');
	$('#noticePageNo').val("1");
	$('.c-button--sm .c-hidden').remove();
	$('.c-button--sm').removeClass('is-active');
	$(obj).append('<span class="c-hidden">선택됨</span>');
	$(obj).addClass('is-active');
	goPageAjax();
}


$(document).ready(function (){



	var swiper = new Swiper(".c-swiper", {
      loop: true,
      pagination: {
        el: ".swiper-pagination",
        type: "fraction",
      },
      autoplay: {
        delay: 3000,
        disableOnInteraction: false,
      },
      on: {
        init: function() {
          var swiper = this;
          if($("#topBanLength").val() > 1){
	          var autoPlayButton = this.el.querySelector(".swiper-play-button");
	          autoPlayButton.addEventListener("click", function() {
	            if (autoPlayButton.classList.contains("play")) {
	              autoPlayButton.classList.remove("play");
	              autoPlayButton.classList.add("stop");

	              swiper.autoplay.start();
	            } else {
	              autoPlayButton.classList.remove("stop");
	              autoPlayButton.classList.add("play");

	              swiper.autoplay.stop();
	            }
	          });
          }
        },
      },
    });

    goPageAjax();
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
				location.href='/m/appForm/appFormDesignView.do?orgnId=' + orgnId;
			});
		} else {
			location.href='/m/appForm/appFormDesignView.do?orgnId=' + orgnId;
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
