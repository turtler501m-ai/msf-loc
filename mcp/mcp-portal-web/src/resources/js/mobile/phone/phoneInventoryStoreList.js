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
						//일단 퍼블에 페이징(더보기) 없어서 주석 나중에 페이징 요청하면 처리



						$('#pageNav').html(
								numberWithCommas(jsonObj.listCount) + "/"
										+ numberWithCommas(jsonObj.total));

						if (jsonObj.endPage == $('#storePageNo').val()) {
							$("#searchNext").hide();
						} else {
							$("#searchNext").show();
						}
						$('#maxPageNo').val(jsonObj.endPage);
						$("#totalCount").val(jsonObj.total);

						var html = "";
						if (pageNo == 1) {
							if($('#orderType').val() == 'NORMAL'){
								html += '<strong class="store-result__heading">구매가능 매장</strong>';
						        html += '   <p class="store-result__text">';
						        html += '     <span>총 '+ numberWithCommas(jsonObj.total)+'건</span>';
						        html += '     검색되었습니다.';
						        html += '   </p>';
								html += '   <hr class="c-hr c-hr--type2">';
							}
							html += '<ul class="store-list" id="store-list">';
							html += '</ul>';
							$('#searchArea').html(html);
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
								html += '<button class="c-button c-button--sm" type="button" href="javascript:void(0);" onclick="selectStoreDetail(\''
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

						if (pageNo == 1) {
							$('#store-list').html(html);
						} else {
							$('#store-list').append(html);
						}

					}else{
						html = '';
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

function selectStoreDetail(storNm, storRoadnAdr, telNo, mapProdDesc, latitVal,lngitVal) {
	MCP.openPopup("#find-store-map-dialog");
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

//더보기
var goNextPage = function() {

	var pNo = $('#storePageNo').val();
	var mPage = $('#maxPageNo').val();

	if (parseInt(pNo) + 1 > parseInt(mPage)) {
		alert('마지막 페이지입니다.');
		return;
	}
	$('#storePageNo').val(parseInt(pNo) + 1);

	searchStore();
};

function emptyToTyphoon(value){

	if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
		return '-';
	} else{
		return value;
	}

}