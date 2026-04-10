﻿;

var wireProdCd = "";
var compareSeqArray = [];

var joinProdDtlSeq = "";
var joinProdCd = "";
var joinProdCorp = "";

$(document).ready(function () {

	wireProdCd = $("#wireProdCd").val();

	/* 슬라이드 */
	$(function(){
		window.directMainSwipe = new Swipe(document.getElementById('slider'), {
            startSlide: 0,
            speed: 400,
            auto: 5000,
            continuous: true,
            disableScroll: false,
            stopPropagation: false,
            callback: function (index, elem) {
            	var pos = directMainSwipe.getPos() + 1;
                // 슬라이드가 2개일 경우
                var maxIdx = $('.banner_index .btn_index').length;
                if (maxIdx == 2) {
                    if (pos == 3) {
                        pos = 1;
                    } else if (pos == 4) {
                        pos = 2;
                    }
                }
                // //슬라이드가 2개일 경우
                $("#slider").find(".banner_index button").removeClass("active");
		        $("#slider").find(".banner_index button:nth-child(" + pos + ")").addClass("active");
		    },
		    transitionEnd: function (index, elem) {
		    	 // 현재 슬라이드만 탭이동이 되도록 함
	            $('.swipe-wrap .clri a').attr('tabindex', '-1');
	            $('.swipe-wrap .clri').eq(index).find('a').removeAttr('tabindex');
	            // //현재 슬라이더만 탭이동이 되도록 함
	        }
        });

		 // 슬라이드 탭이동 기능 초기화
	    $('.swipe-wrap .clri a').attr('tabindex', '-1');
	    $('.swipe-wrap .clri').eq(0).find('a').removeAttr('tabindex');
	    // //슬라이드 탭이동 기능 초기화

		$('.btn_index').click(function () {
	    	$('.btn_stop').css("display","none");
	    	$('.btn_start').css("display","");
	        var id = $(this).attr("id");
	        var index = id.split("_")[1];
	        index = parseInt(index) - 1;
	        directMainSwipe.slide(index, 400);
	    });

		$('.btn_stop').click(function () {
			$('.btn_stop').css("display","none");
			$('.btn_start').css("display","");
			directMainSwipe.stop();
		});

		$('.btn_start').click(function () {
			$('.btn_start').css("display","none");
			$('.btn_stop').css("display","");
			directMainSwipe.begin();
		});
	});
	/* 슬라이드 */

	//사업자 구분
	$(".tabMenu > a").click(function() {
		var sortGubun = $(this).attr("corpGubun");
		$(".tabMenu > a").removeClass("on");
		$(this).addClass("on");

		initFn();
		getList(wireProdCd, sortGubun);
	});

	//정렬
	$(".listSort > a").click(function() {
		var sortGubun = $(this).attr("sortGubun");
		$(".listSort > a").removeClass("on");
		$(this).addClass("on");
		sortFn(sortGubun);
	});

	//비교하기
	$("#pdtCompare").change(function() {

		var checkCnt = $(".compare:checked").length;

		if (checkCnt == 0) {
			alert("비교 상품을 선택해 주세요.");
			$(this).prop("checked", false);
			return;
		}

	 	if ($(this).prop("checked")) {
			$("#prodList > li").hide();
    	 	$(".compare").each(function(index) {
    	 		if ($(this).prop("checked")) {
    	 			compareSeqArray.push($(this).parents().parents().parents().data("wireProdDtlSeq"));
    	 			$(this).parents().parents().parents().show();
    			}
    		});
		} else {
			compareSeqArray = [];
			$("#prodList > li").show();
		}
	 	$("#prodList > li").removeClass("lastCld");
	 	$("#prodList > li:visible").last().addClass("lastCld");

	});

	//전체상품 한눈에 보기
	$(".btnAllView").click(function() {
		$(".dim-layer").removeClass('blkPopup').addClass('redPopup');
		fn_layerPop('allViewLayer', 880, 400);
	});

	$(document).on("click", ".joinBtn", function() {
		joinProdCorp = $(this).parents().parents().parents().data("wireProdCorp");
		joinProdCd = $(this).parents().parents().parents().data("wireProdCd");
		joinProdDtlSeq = $(this).parents().parents().parents().data("wireProdDtlSeq");

		window.open("","wireProdJoinForm","width=740,height=820");

        ajaxCommon.createForm({
            method:"post"
           ,action:"/wire/wireProdJoinForm.do"
           ,target :"wireProdJoinForm"
         });

        ajaxCommon.attachHiddenElement("joinProdCorp",joinProdCorp);
        ajaxCommon.attachHiddenElement("joinProdCd",joinProdCd);
        ajaxCommon.attachHiddenElement("joinProdDtlSeq",joinProdDtlSeq);
        ajaxCommon.attachHiddenElement("wireProdCd",wireProdCd);
        ajaxCommon.formSubmit();
	});

	//상세보기
	$(document).on("click", ".detailViewBtn", function() {

		var wireProdDtlSeq = $(this).parents().parents().parents().data("wireProdDtlSeq");
		wireProdCd = $(this).parents().parents().parents().data("wireProdCd");

	    var varData = ajaxCommon.getSerializedData({
      			wireProdCd : wireProdCd,
      			wireProdDtlSeq : wireProdDtlSeq
       	});

      		ajaxCommon.getItem({
       	    id:'wireProdDtlAjax'
           	,cache:false
           	,url:"/wire/wireProdDtlAjax.do"
           	,data: varData
           	,dataType:"json"
           	,loading:false
     		}
			,function(jsonObj){

				var detailProdTitle = "";
				var detailProdCorpTitle = "";
				if (wireProdCd == "IT01") {
					detailTitle = "인터넷 단독상품";
				} else if (wireProdCd == "TV01") {
					detailTitle = "TV 단독상품";
				} else if (wireProdCd == "CB01") {
					detailTitle = "TV+인터넷 결합상품";
				} else if (wireProdCd == "AD01") {
					detailTitle = "부가 서비스";
				} else if (wireProdCd == "CCTV01") {
					detailTitle = "CCTV";
				} else if (wireProdCd == "ITTV01") {
					detailTitle = "인터넷/TV 상품";
				}

				if (jsonObj.wireProdDtlDto.wireProdCorp == "KT") {
					detailProdCorpTitle = "KT";
				} else if (jsonObj.wireProdDtlDto.wireProdCorp == "SKY") {
					detailProdCorpTitle = "skylife";
				} else if (jsonObj.wireProdDtlDto.wireProdCorp == "KTT") {
					detailProdCorpTitle = "kt telecop";
				}

				$("#detailViewLeftTitle").text(detailProdCorpTitle);
				$("#detailViewRightTitle").text(detailTitle);
				$("#detailViewContent").html(jsonObj.wireProdDtlDto.wireProdPcSbst);
				$(".dim-layer").removeClass('blkPopup').addClass('redPopup');
				fn_layerPop('detailViewLayer', 800, 600);
	        });

	});

	//상품 유형별 정렬 버튼 노출 셋팅
	if (wireProdCd == "IT01" || wireProdCd == "IT02") {
		$(".listSort > [sortGubun='chn']").remove();
	} else if (wireProdCd == "TV01" || wireProdCd == "TV02") {
		$(".listSort > [sortGubun='spd']").remove();
	}  else if (wireProdCd == "AD01" || wireProdCd == "CCTV01") {
		$(".listSort > [sortGubun='spd']").remove();
		$(".listSort > [sortGubun='chn']").remove();
	}
	$(".listSort > a").last().addClass("last");

	//상품 목록 조회
	getList(wireProdCd, '');

});

var wireProdDtlDtoList = null; //전체 목록

var getList = function(wireProdCd, wireProdCorp) {

	var varData = ajaxCommon.getSerializedData({
   		 wireProdCd : wireProdCd,
   		wireProdCorp : wireProdCorp
    });

   	ajaxCommon.getItem({
        id:'wireProdListAjax'
        ,cache:false
        ,url:"/wire/wireProdListAjax.do"
        ,data: varData
        ,dataType:"json"
        ,loading:false
    }
	,function(jsonObj){
		wireProdDtlDtoList = jsonObj.nmcpWireProdDtlDtoList
		listView(wireProdDtlDtoList);
    });
}

var listView = function(obj) {
	$("#prodList").html("");

	//부가서비스 상품 구분
	var additionFlag = false;
	if (wireProdCd != "AD01") {
		additionFlag = true;
	}
	if (obj.length == 0) {
		var html = "";
		html += "<li style='text-align:center;padding-top:70px;padding-bottom:50px;font-weight: bold;'>";
		html += "상품 준비중입니다."
		html += "</li>";
		$("#prodList").append(html);
	};

	$.each(obj, function(i, item) {
		var html = "";
		html += "<li>";
		html += "	<div class='imgFgr'>";
		html += "		<img src='"+item.wireProdImg+"' alt='"+item.wireProdImgDesc+"'>";
		html += "	</div>";
		html += "	<div class='pdtInfo'>";
		//html += "		<a href='#/' class='infoLnk'>";
		html += "			<strong class='titleBox'>";
		html += "				<span class='sticker'>";

		if (item.wireProdSticker.indexOf('K') > -1) {
			html += "					<img src='/resources/images/wire/icon_sticker_01.png' alt='KT'>";
		}
		if (item.wireProdSticker.indexOf('S') > -1) {
			html += "					<img src='/resources/images/wire/icon_sticker_03.png' alt='skylife'>";
		}
		if (item.wireProdSticker.indexOf('F') > -1) {
			html += "					<img src='/resources/images/wire/icon_sticker_04.png' alt='사은품'>";
		}
		if (item.wireProdSticker.indexOf('T') > -1) {
			html += "					<img src='/resources/images/wire/icon_sticker_05.png' alt='kt telecop'>";
		}
		if (item.wireProdRecomYn == "Y") {
			html += "					<img src='/resources/images/wire/icon_sticker_02.png' alt='추천'>";
		}

		html += "				</span>";
		html += "				<b class='titleTxt'>"+item.wireProdName+"</b>";
		html += "			</strong>";
		html += "			<span class='txtBox'>"+item.wireProdText+"</span>";

		//html += "		</a>";

		html += "	</div>";

		html += "	<div class='priceTxt'>";

		if (item.wireProdAmtType == "M") {
    		html += "		<p class='priceNm'>월 <b>"+$.number(item.wireProdAmt)+"</b>원</p>";
    		html += "		<p class='vatTxt'>(VAT 포함)</p>";
		} else {
			html += "		<p class='priceNm'><b>"+item.wireProdAmtText+"</b></p>";
		}

		html += "	</div>";
		html += "	<div class='utilSection'>";

		if (additionFlag) {
			html += "		<label for='comp_"+item.wireProdDtlSeq+"'>비교</label>";
			html += "		<span class='check'>";
			var compCheck = "";
			if ($("#pdtCompare").prop("checked")) {
				if (compareSeqArray.indexOf(item.wireProdDtlSeq) > -1) {
					compCheck = "checked";
	 			};
			}
			html += "			<input type='checkbox' class='compare' id='comp_"+item.wireProdDtlSeq+"' "+ compCheck +">";
			html += "		</span>";
			html += "		<br>";
		}

		html += "		<div class='btnGrp'>";
		html += "			<a href='javascript:void(0)' class='btnRed detailViewBtn' >상세보기</a>";

		if (additionFlag) {
			html += "			<a href='javascript:void(0)' class='btnBlue joinBtn'>신청하기</a>";
		}

		html += "		</div>";
		html += "	</div>";
		html += "</li>";
		$("#prodList").append(html);
		$("#prodList > li").last().data(item);
	});

	$("#prodList > li").removeClass("lastCld");
 	$("#prodList > li").last().addClass("lastCld");

 	if ($("#pdtCompare").prop("checked")) {
 		$("#pdtCompare").trigger("change");
 	};

}

function initFn() {
	compareSeqArray = [];
	$("#pdtCompare").prop("checked", false);
	$(".listSort > a").removeClass("on");
	$(".listSort > a:eq(0)").addClass("on");
}

function sortFn(gubun) {

	var wireProdDtlDtoSortTmp = wireProdDtlDtoList.slice(0);

	if (gubun == "amtL") {
		wireProdDtlDtoSortTmp.sort(amtAscSort);
	} if (gubun == "amtH") {
		wireProdDtlDtoSortTmp.sort(amtDescSort);
	} else if (gubun == "chn") {
		wireProdDtlDtoSortTmp.sort(channelDescSort);
	} else if (gubun == "spd") {
		wireProdDtlDtoSortTmp.sort(speedDescSort);
	} else if (gubun == "rcm") {
		listView(wireProdDtlDtoSortTmp);
	}
	listView(wireProdDtlDtoSortTmp);
}



	//금액 오름차순
function amtAscSort(a, b) {
	if (Number(a.wireProdAmt) == Number(b.wireProdAmt)) {
		return 0
	}
	return Number(a.wireProdAmt) > Number(b.wireProdAmt) ? 1 : -1;
}

//금액 내림차순
function amtDescSort(a, b) {
	if (Number(a.wireProdAmt) == Number(b.wireProdAmt)) {
		return 0
	}
	return Number(a.wireProdAmt) < Number(b.wireProdAmt) ? 1 : -1;
}

//채널 내림차순
function channelDescSort(a, b) {
	if (Number(a.wireProdChannel) == Number(b.wireProdChannel)) {
		return 0
	}
	return Number(a.wireProdChannel)< Number(b.wireProdChannel) ? 1 : -1;
}

//채널 내림차순
function speedDescSort(a, b) {
	if (Number(a.wireProdSpeed) == Number(b.wireProdSpeed)) {
		return 0
	}
	return Number(a.wireProdSpeed)< Number(b.wireProdSpeed) ? 1 : -1;
}

//추천순
function rcmSort(a, b) {
	if (a.wireProdRecomYn == b.wireProdRecomYn) {
		return 0
	}
	return a.wireProdRecomYn < b.wireProdRecomYn ? 1 : -1;
}