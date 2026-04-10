
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
    goPageAjax();
});

