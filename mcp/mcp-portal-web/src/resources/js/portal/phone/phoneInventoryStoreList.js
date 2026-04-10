var modalScrollY = 0;
var moveScrollY = 0;
var map;

function searchStore(selPageNo) {
	if (typeof selPageNo !== 'undefined') {
		if(selPageNo == 1){
			$('#schDivStr').val($('#selSchDivStr').val());
			$('#searchStr').val($('#selSearchStr').val());
			$('#subAddr').val($('#selSubAddr').val())
		}
		$('#storePageNo').val(selPageNo);


	}

	var pageNo = $('#storePageNo').val();

	var varData = ajaxCommon.getSerializedData({
		schDivStr : $('#schDivStr').val(),
		searchStr : $('#searchStr').val(),
		storDiv : '',
		subAddr : $('#subAddr').val(),
		orderType : '',
		lotateVal : '',
		prodId :$("#fprodId").val(),
		pageNo : pageNo
	});
	$.ajax({
				url : '/m/direct/selectStoreListAjax.do',
				type : 'post',
				dataType : 'json',
				data : varData,
				async : false,
				success : function(jsonObj) {
					//if(jsonObj.RESULT_CODE == "0001"){
					if (jsonObj.total > 0) {
						list = jsonObj.resultList;
						var listCount = jsonObj.listCount;
						var total = jsonObj.total;

						/*
						if (jsonObj.endPage == $('#storePageNo').val()) {
							$("#searchNext").hide();
						} else {
							$("#searchNext").show();
						}
						*/
						$('#maxPageNo').val(jsonObj.endPage);
						$("#totalCount").val(jsonObj.total);

						var html = "";
						if (pageNo == 1) {
							$('#store_result_b_cnt').text('총 '+ numberWithCommas(jsonObj.total)+'건');
						}
						html = "";

						for (var i = 0; i < list.length; i++) {
							html += '<li class="store-list__item">';
							html += '<dl class="store-list__info">';
							html += '<dt>' + list[i].storNm
									+ '</dt>';
							html += '<dd>';
							html += '    <p class="store-list__text">'
									+ emptyToTyphoon(list[i].storRoadnAdr) + '</p>';
							html += '    <p class="store-list__text">'
									+ emptyToTyphoon(list[i].telNo) + '</p>';
							html += '  </dd>';
							html += '</dl>';
							html += '<dl class="store-list__sub">';
			                html += '  <dt>'+$("#fprodNm").val()+'</dt>';
			                html += '  <dd>';
			                var mapProdDesc = '';
			                if(list[i].invCnt != null && list[i].invCnt > 0 ){
								mapProdDesc = '재고 보유';
							}else{
			                	mapProdDesc = '재고가 없습니다';
			                }
			                html += '    <span>'+mapProdDesc+'</span>';
			                html += '  </dd>';
			                html += '</dl>';
			                if(list[i].latitVal != null && list[i].lngitVal != null){
								html += '<button class="c-button c-button--xsm c-button--white c-button-round--xsm" type="button" href="javascript:void(0);" onclick="selectStoreDetail(\''
										+ list[i].storNm
										+ '\',\''
										+ list[i].storRoadnAdr
										+ '\',\''
										+ list[i].telNo
										+ '\',\''
										+ mapProdDesc
										+ '\',\''
										+ list[i].latitVal
										+ '\',\''
										+ list[i].lngitVal
										+ '\')">';
								html += '  <i class="c-icon c-icon--map" aria-hidden="true"></i>';
								html += '  <span>지도보기</span>';
								html += '</button>';
							}
							html += '</li>';
						}

						$("#store_result_div").show();
						$("#store_result_empty_div").hide();
						$('#store-list').html(html);
						if(jsonObj.total > 10){
							initStorePagingHtml(listCount, total);
							$("#phoneIventoryPaging").show();
						}else{
							$("#phoneIventoryPaging").hide();
						}

					}else{
						$("#phoneIventoryPaging").html('');
						$('#store-list').html('');
						$("#store_result_div").hide();
						$("#store_result_empty_div").show();

					}
				}
			});

};

function selectStoreDetail(storNm, storRoadnAdr, telNo, mapProdDesc, latitVal,lngitVal) {
	var el = document.querySelector("#find-cvs-modal");
	var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
	modal.open();
	$('#mapStoreName').html(storNm);
	$('#mapAddr').html(storRoadnAdr);
	$('#mapPhone').html(telNo);
	$('#mapProdNm').text($('#fprodNm').val());
	$('#mapProdDesc').text(mapProdDesc);
	$(document).ready(function() {
	map = mapInit(latitVal,lngitVal);
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

	});

}

function selectSubAddrAjax() {
	if($('#selSchDivStr').val() == '전국'){
		$('#selSubAddr').html('');
		$('#selSubAddr').append("<option value='0'>전체</option>");
		$('#selSubAddr').attr('disabled','""');
		return;
	}
	var varData = ajaxCommon.getSerializedData({
		schDivStr : $('#selSchDivStr').val(),
		searchStr : $('#selSearchStr').val(),
		storDiv : $('#storDiv').val()
	});
	$.ajax({
		url : '/m/direct/selectSubAddrAjax.do',
		type : 'post',
		dataType : 'json',
		data : varData,
		async : false,
		success : function(jsonObj) {
			if (jsonObj.RESULT_CODE == "0001") {

				list = jsonObj.resultList;
				$('#selSubAddr').html('');
				$('#selSubAddr').append(
						"<option value='0'>전체</option>");
				if (list.length > 0) {
					$('#selSubAddr').removeAttr('disabled');

					for (var i = 0; i < list.length; i++) {

							$('#selSubAddr').append(
								"<option value='"+list[i].subAddr+"'>"
										+ list[i].subAddr
										+ "</option>");
					}
				}else{
					$('#selSubAddr').attr('disabled','""');
				}
			}else{
				$('#selSubAddr').attr('disabled','""');
			}
		}
	});
}

function emptyToTyphoon(value){

	if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
		return '-';
	} else{
		return value;
	}

}

var initStorePagingHtml = function(listCount, total){
	var pageSize = 10 ; //페이지 사이즈
	var totalCount = total;
	var currentPage = Math.ceil(listCount/pageSize);
	var countPerPage = pageSize;
	var totalPage = parseInt(total / countPerPage);
	if((totalCount % countPerPage) > 0) {
		totalPage = totalPage + 1;
	}
	$("#phoneIventoryPaging").empty();
	var firstPageNoOnPageList = parseInt((currentPage - 1) / pageSize ) * pageSize + 1;
	var lastPageNoOnPageList = (firstPageNoOnPageList + pageSize - 1);
    if (lastPageNoOnPageList > totalPage) {
    	lastPageNoOnPageList = totalPage;
    }

    var pageFirst = '';
    var pageLeft = '';

    if(currentPage > 1 ) {
        if (firstPageNoOnPageList > pageSize) {
        	pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goStoreSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
        	pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goStoreSubmit('+ (firstPageNoOnPageList-1) +');"><span>이전</span></a>';
        } else {
        	pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goStoreSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
        	pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goStoreSubmit('+(currentPage-1)+');"><span>이전</span></a>';
        }
    } else {
    	pageFirst = '<a class="c-button is-disabled" href="javascript:void(0);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
    	pageLeft = '<a class="c-button is-disabled" href="javascript:void(0);"><span>이전</span></a>';
    }

    var pageStr = ""
	for (var i = firstPageNoOnPageList; i <= lastPageNoOnPageList; ++i) {
    	if (i == currentPage) {
    		pageStr += '<a class="c-paging__anchor c-paging__anchor--current c-paging__number" href="javascript:void(0);"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
        } else {
    		pageStr += '<a class="c-paging__anchor c-paging__number" href="javascript:void(0);" onclick="goStoreSubmit(' + i + ')"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
        }
    }

    var pageLast = '';
	var pageRight = '';

	if(totalPage > currentPage ){
        if (lastPageNoOnPageList < totalPage) {
        	pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goStoreSubmit('+(firstPageNoOnPageList + pageSize)+');"><span>다음</span></a>';
        	pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goStoreSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
        } else {
        	pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goStoreSubmit('+(currentPage+1)+');"><span>다음</span></a>';
        	pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goStoreSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
        }
    } else {
    	pageRight = '<a class="c-button is-disabled" href="javascript:void(0);"><span>다음</span></a>';
    	pageLast = '<a class="c-button is-disabled" href="javascript:void(0);"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
    }

	var pagingHtml = "";
	pagingHtml += pageFirst;
	pagingHtml += pageLeft;
	pagingHtml += pageStr;
	pagingHtml += pageRight;
	pagingHtml += pageLast;
	$("#phoneIventoryPaging").html(pagingHtml);

}

function goStoreSubmit(pageNo){
	searchStore(pageNo);
}