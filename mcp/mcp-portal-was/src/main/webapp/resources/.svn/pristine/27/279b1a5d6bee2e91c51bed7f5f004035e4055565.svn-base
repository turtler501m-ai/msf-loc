;

var v_proList; // xml 상품 목록
var initFlag = true;

$(document).ready(function(){

	var swiperCprtCard = new Swiper("#swiperCprtCard .swiper-container", {
        width: 940,
        slidesPerView: 6,
        observer: true,
        observerParents: true,
        navigation: {
            nextEl: "#swiperCprtCard .swiper-button-next",
            prevEl: "#swiperCprtCard .swiper-button-prev",
        },
    });

	setTimeout(function(){
		var active = $("#mainTabList .c-tabs__button").index( $("#mainTabList .c-tabs__button.is-active") );
		var slidemove = active-5;
		if (active >= 6) {
			swiperCprtCard.slideTo(slidemove, 10, false);
		}else{
			swiperCprtCard.slideTo(0, 10, false);
		}
	},300);


	initProdXmlData().then(function(){
		console.log("initProdXmlData")
		var v_cprtCardCtgCd = "";
		var html = "";

		$.each(v_proList, function(idx, item) {

			if(v_cprtCardCtgCd != item.cprtCardCtgCd && isMiddleDate(item.pstngStartDate, item.pstngEndDate)) {

				html += '<div class="swiper-slide"><button class="c-tabs__button" role="tab" data-cprtCardCtgCd="'+item.cprtCardCtgCd+'" onclick="mainTabClick(\''+item.cprtCardGdncSeq+'\',\''+item.cprtCardCtgCd+'\');">'+item.cprtCardCtgNm+'</button></div>';

				v_cprtCardCtgCd = item.cprtCardCtgCd;
			}
		});

		$("#subbody_loading").remove();
		MCP.addSingleTab('#mainTabList', '.c-tabs__button', html, documentReady);

		$("#swiperCprtCard").each( function() {
			var btn = $("#swiperCprtCard .swiper-button-next, #swiperCprtCard .swiper-button-prev");
			var align = $("#swiperCprtCard .swiper-wrapper");

			if (v_proList.length >= 7) {
				btn.show();
			}else{
				btn.hide();
				align.css("justify-content","center");
			}
		});

	}).catch(
		//
	);

    KTM.initialize();
});

// xml 데이터 초기화
var initProdXmlData = function() {
	return new Promise(function(resolve, reject) {
		$.ajax({
			type : 'GET'
			, url : '/event/cprtCardXmlListAjax.do'
			, dataType : 'json'
			, success : function(data) {
				v_proList = data.item.proList.item;

				resolve();
			}
		});
	});
}

// 첫번째 메인탭 클릭
function documentReady() {
	$('#mainTabList  button').on("click", function(){
		$('#mainTabList  button').removeAttr("aria-hidden");
		$('#mainTabList  button').attr('aria-selected',"false");
		$(this).attr('aria-selected',"true");
	});

	// 메인탭 클릭 이벤트 실행, 'cprtCardCtgCd' 의 파라미터 값이 있을경우, 없을경우 처리
	var cprtCardCtgCd = ajaxCommon.isNullNvl(v_paramCprtCardCtgCd, "");
	if(initFlag == true && cprtCardCtgCd != "") {
		$("#mainTabList  button").each(function(index, item) {
	  		var v_cprtCardCtgCd = $(this).attr("data-cprtCardCtgCd");
		  	if(v_cprtCardCtgCd == cprtCardCtgCd) {
				$(this).click();
				$(this).focus();
				return false;
			}
		});
		initFlag = false;
	} else {
		$($('.c-tabs__button')[0]).click();
	}
}

// 메인탭 클릭 시
function mainTabClick(cprtCardGdncSeq, cprtCardCtgCd){
	var varData = ajaxCommon.getSerializedData({
 		cprtCardGdncSeq : cprtCardGdncSeq
 	   ,cprtCardCtgCd : cprtCardCtgCd
	});
   	url = "/event/cprtCardPanel.do";
    //페이지 호출
 	MCP.ajaxGet(url, varData, 'text', mainTabClickCallback);
}

// 메인탭 클릭 콜백
function mainTabClickCallback(resp){
	$('#mainTabPanel').html(resp);
	MCP.init();

	// 이벤트 배너 조회
	cardBannerList();
}

// 이벤트 배너 조회 (E003)
var cardBannerList = function() {
	var varData = ajaxCommon.getSerializedData({
 		bannCtg : 'E003'
	});

	ajaxCommon.getItem({
        id : 'cardBannerListAjax'
        , cache : false
        , async : false
        , url : '/event/cardBannerListAjax.do'
        , data : varData
        , dataType : "json"
    }
    ,function(result){
        let bannerHtml = "";
        let returnCode = result.returnCode;
        let message = result.returnCode;
        let bannerList = result.result;

        if(returnCode == "00") {
			if(bannerList.length > 0) {
				$.each(bannerList, function(index, value) {
					let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
					let v_bannApdDesc = ajaxCommon.isNullNvl(value.bannApdDesc, "");
					let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
					let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
					let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
					let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
					let v_linkUrlAdr = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
					let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

					var linkData = {
						bannSeq : v_bannSeq
						, bannCtg : v_bannCtg
				 		, linkUrl : v_linkUrlAdr
				 		, linkTarget : v_linkTarget
					};

					bannerHtml += "<div class='swiper-slide'>";
					bannerHtml += "    <a class='swiper-banner__anchor' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
					bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
					bannerHtml += "    </a>";
					bannerHtml += "</div>";
				});

				$("#eventBannerList").html(bannerHtml);

				// swiper 초기화
				KTM.swiperA11y("#swiperCardEvent .swiper-container", {
			      width: 940,
			      spaceBetween: 20,
			      navigation: {
			        nextEl: "#swiperCardEvent .swiper-button-next",
			        prevEl: "#swiperCardEvent .swiper-button-prev",
			      },
			      observer: true,
			      observerParents: true,
			    });
			} else {
				$("#swiperCardEvent").hide();
			}
		} else {
			alert(message);
		}
    });
}

// 배너조회 로그 추가
function addBannAccess(obj) {
	if(obj.bannSeq != "" && obj.bannCtg != "") {
		insertBannAccess(obj.bannSeq, obj.bannCtg);
	}
}

// 배너 링크
function bannerLink(obj){
	if(obj.linkUrl == "") {
		return;
	} else {
		var parameterData = ajaxCommon.getSerializedData({
			eventPopTitle : '제휴카드 이벤트'
		});
		$.ajax({
            url: obj.linkUrl,
            type: 'POST',
            dataType: 'text',
            data: parameterData,
            async: false,
            success: function(data) {
				var elLength = document.querySelector("#eventPop");
				if(elLength != null) {
					$("#eventPop").remove();
				}
                $("#main-content").append(data);
                KTM.initialize();
            }
        });
        var el = document.querySelector("#eventPop");
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();
        modal.redraw();
	}
}

function cprtCardEventLayer(url) {
	var win = window.open(url);
	win.onbeforeunload = function(){
	}
}

// 현재일이 이벤트 시작일과 종료일 사이인지 체크
var isMiddleDate = function(startDate, endDate){
	var sDate=exportNumber(startDate);
	var eDate=exportNumber(endDate);
	var cDate=exportNumber(getDate());

	if(sDate!='' && eDate!=''){
		if(parseInt(sDate)<=parseInt(cDate) && parseInt(eDate)>=parseInt(cDate)){
			return true;
		}
	}
	return false;
};

// 선택한 카드 클릭처리
function initCprtCard(cprtCardCtgCd) {
	var selectedCard = null;
	// 선택된 카드 메뉴 클릭
	$("#mainTabList  button").each(function(index, item) {
	  	var v_cprtCardCtgCd = $(this).attr("data-cprtCardCtgCd");
	  	if(v_cprtCardCtgCd == cprtCardCtgCd) {
			selectedCard = $(this);
			$(this).click();
			return;
		}
	});
	// Layer 닫기
	var modalEl = $("#modalArs")[0];
	modalEl.addEventListener(KTM.Dialog.EVENT.CLOSED, function closed() {
		modalEl.removeEventListener(KTM.Dialog.EVENT.CLOSED, closed);
		setTimeout(function() {
			if(selectedCard) {
				selectedCard.focus();
			}
		}, 400);
	});
	$("#modalArs button.c-button").click();

}

// 선택된 카드 메뉴 클릭
function cprtCartClick(cprtCardCtgCd) {
	$("#mainTabList button").each(function(index, item) {
	  	var v_cprtCardCtgCd = $(this).attr("data-cprtCardCtgCd");
	  	if(v_cprtCardCtgCd == cprtCardCtgCd) {
			$(this).click();
			return;
		}
	});
}

// 현재 날자 구하기
function getDate() {
	var today = new Date();

	var year = today.getFullYear();
	var month = ("0" + (today.getMonth()+1)).slice(-2);
	var day = ("0" + today.getDate()).slice(-2);
	var hours = ('0' + today.getHours()).slice(-2);
	var minutes = ('0' + today.getMinutes()).slice(-2);
	var seconds = ('0' + today.getSeconds()).slice(-2);

	var dateString = year + '-' + month  + '-' + day;

	return dateString;
}

// 새창 링크
function pageLink(page){
	var win = window.open(page);
	win.onbeforeunload = function(){
	}
}

// 선택된 mainTab의 cprtCardCtgCd값 조회
function mainTabCd() {
	var v_cprtCardCtgCd = "";
	$("#mainTabList button").each(function(index, item) {
		if($(this).hasClass("is-active")) {
			v_cprtCardCtgCd = $(this).attr("data-cprtCardCtgCd");
			return false;
		}
	});
	return v_cprtCardCtgCd;
}

// 선택된 mainTab의 내용 조회
function mainTabTxt() {
	var v_cprtCardCtgCd = "";
	$("#mainTabList button").each(function(index, item) {
		if($(this).hasClass("is-active")) {
			v_cprtCardCtgCd = $(this).text();
			return false;
		}
	});
	return v_cprtCardCtgCd;
}

// OG 태그 초기화
var setMetaOg = function() {
	var v_cprtCardCtgCd = mainTabCd();
	var v_cprtCardTxt = mainTabTxt();
	var v_host = $(location).attr('protocol') +"//"+$(location).attr('host');

	var v_title = v_cprtCardTxt + " | 제휴카드 | kt M모바일 공식 다이렉트몰";
	var v_link = v_host + "/m/event/cprtCardList.do?cprtCardCtgCd="+v_cprtCardCtgCd;
	var v_image = v_host + $("#cardImg").attr("src");

	$("#meta_og_title").attr("content", v_title);
	$("#meta_og_url").attr("content", v_link);
	$("#meta_og_image").attr("content", v_image);
}

// facebook 공유하기
function facebookShare(){
	var v_cprtCardCtgCd = mainTabCd();
	var v_host = $(location).attr('protocol') +"//"+$(location).attr('host');
	var v_link = "/event/cprtCardList.do?cprtCardCtgCd="+v_cprtCardCtgCd;

    facebook_share(v_link);
}
// Line 공유하기
function lineShare(){
	var v_cprtCardCtgCd = mainTabCd();
	var v_cprtCardTxt = mainTabTxt();
	var txt = v_cprtCardTxt + " | 제휴카드 | kt M모바일 공식 다이렉트몰";

    var link = "/event/cprtCardList.do?cprtCardCtgCd="+v_cprtCardCtgCd;

    line_share(txt, link);
}
// 카카오톡 공유하기
function kakaoShare() {
	var v_cprtCardCtgCd = mainTabCd();

	var cardImg = "/" + $("#cardImg").attr("src");
	var mobileLink = "/m/event/cprtCardList.do?cprtCardCtgCd="+v_cprtCardCtgCd;
	var webLink = "/event/cprtCardList.do?cprtCardCtgCd="+v_cprtCardCtgCd;

    kakao_share(cardImg, mobileLink, webLink);
}
