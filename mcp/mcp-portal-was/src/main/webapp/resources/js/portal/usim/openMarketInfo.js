
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
