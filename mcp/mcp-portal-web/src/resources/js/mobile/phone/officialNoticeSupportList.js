var prdtIndCdTemp;
$(document).ready(function(){
	rateFilter();
	$("#schbtn").click(function() {
		//검색시에만 검색데이터 세팅
		setSchFormData();
        goSubmit();
    });

	$("#sortSpt").click(function() {
    	var sortSptVal = $(this).data('value');
		if(sortSptVal == 'sptH'){
			$(this).data('value','sptL');
			$("#sortSptText").text('지원금 낮은 순');
			$("#sortType").val('sptL');
		}else{
			$(this).data('value','sptH');
			$("#sortSptText").text('지원금 높은 순');
			$("#sortType").val('sptH');
		}

		setSchFormData();
        goSubmit();

    });

	$("#sortPrc").click(function() {
		var sortPrc = $(this).data('value');
		if(sortPrc == 'prcH'){
			$(this).data('value','prcL');
			$("#sortPrcText").text('판매가 낮은 순');
			$("#sortType").val('prcL');
		}else{
			$(this).data('value','prcH');
			$("#sortPrcText").text('판매가 높은 순');
			$("#sortType").val('prcH');
		}

		setSchFormData();
        goSubmit();

    });

	goSubmit();

});

function setSchFormData(){

	$("#rateCd").val($("#selRateCd").val());
	$("#prdtIndCd").val($("input[name='radPrdtIndCd']:checked").val());
	$("#prdtNm").val($("#inpBankNum").val());
	/*
	var sortSpt = $("#sortSpt").data('value');
	var sortPrc = $("#sortPrc").data('value');
	var sortType = sortSpt+sortPrc;
	$("#sortType").val(sortType);
	*/

}


function goSubmit(param) {
	if(param) {
		$('#pageNo').val(param);
		$('#currentPage').val(param);
	} else {
		$('#pageNo').val(1);
		$('#currentPage').val(1);
	}

	try {

		var varData = $("#form").serialize();

        $.ajax({
			type:"post",
		    url : "/m/product/phone/officialNoticeSupportListAjax.do",
		    data : varData,
		    dataType : "json",
			error: function(jqXHR, textStatus, errorThrown){
				return;
			},
			complete: function(){

			},
			success:	function(data){
				var pageInfoBean = data.pageInfoBean;
				var resultCode = data.RESULT_CODE;
				var totalCount = pageInfoBean.totalCount;
				var currentPage = pageInfoBean.pageNo;
				var countPerPage = pageInfoBean.pageSize;
				var totalPage = parseInt(totalCount / countPerPage);
				if((totalCount % countPerPage) > 0) {
					totalPage = totalPage + 1;
				}

				//$('.u-co-mint').html('&nbsp;('+totalCount+'건)');

				var htmlStr = "";
				$('#officialNoticeSupportTbody').empty();
				if(totalCount > 0 && resultCode == "00000") {
					var listData = data.mspOfficialNoticeSupportList;

					//$('#officialNoticeSupportTbody').empty();
					$(listData).each(function() {
						htmlStr +='<div class="official-notice-wrap">';
						htmlStr +='	<div class="official-notice__box">';
						htmlStr +='		<div class="official-notice__title">';
						htmlStr +='			<p>'+this.prdtNm+'</p>';
						htmlStr +='			<span>공시일 : '+regularityToday(this.applStrtDt)+'</span>';
						htmlStr +='		</div>';
						htmlStr +='		<div class="official-notice__list">';
						htmlStr +='			<p>요금제</p>';
						htmlStr +='			<p>'+this.rateNm+'</p>';
						htmlStr +='		</div>';
						htmlStr +='		<div class="official-notice__list">';
						htmlStr +='			<p>출고가</p>';
						htmlStr +='			<p>'+numberWithCommas(this.outUnitPric)+'</p>';
						htmlStr +='		</div>';
						htmlStr +='		<div class="official-notice__list">';
						htmlStr +='			<p>공통지원금</p>';
						htmlStr +='			<p>'+numberWithCommas(this.subsdAmt)+'</p>';
						htmlStr +='		</div>';
						htmlStr +='		<div class="official-notice__list">';
						htmlStr +='			<p>판매가</p>';
						htmlStr +='			<p>'+numberWithCommas(this.pricAmt)+'</p>';
						htmlStr +='		</div>';
						htmlStr +='	</div>';
						htmlStr +='</div>';

						// htmlStr += '<tr>';
		                // htmlStr += '<td scope="row">'+this.prdtNm+'</td>';
		                // htmlStr += '<td>'+this.rateNm+'</td>';
		                // htmlStr += '<td class="u-ta-right">'+numberWithCommas(this.subsdAmt)+'</td>';
		                // htmlStr += '<td class="u-ta-right">'+numberWithCommas(this.pricAmt)+'</td>';
		                // htmlStr += '</tr>';
					});
					$("#officialNoticeSupportTbody").html(htmlStr);

				}
				$(".c-paging").empty();
				if(totalCount > 0 && resultCode == "00000" && totalCount > 10 ) {
					$(".c-paging").html('');
					var pageSize = 5 ; //페이지 사이즈
					var firstPageNoOnPageList = parseInt((currentPage - 1) / pageSize ) * pageSize + 1;
					var lastPageNoOnPageList = (firstPageNoOnPageList + pageSize - 1);
				    if (lastPageNoOnPageList > totalPage) {
				    	lastPageNoOnPageList = totalPage;
				    }

				    var pageFirst = '';
				    var pageLeft = '';



				    if(currentPage > 1 ) {
			            if (firstPageNoOnPageList > pageSize) {
			            	pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
			            	pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+ (firstPageNoOnPageList-1) +');"><span>이전</span></a>';
			            } else {
			            	pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
			            	pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(currentPage-1)+');"><span>이전</span></a>';
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
			        		pageStr += '<a class="c-paging__anchor c-paging__number" href="javascript:void(0);" onclick="goSubmit(' + i + ')"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
			            }
				    }

				    var pageLast = '';
					var pageRight = '';

					if(totalPage > currentPage ){
			            if (lastPageNoOnPageList < totalPage) {
			            	pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(firstPageNoOnPageList + pageSize)+');"><span>다음</span></a>';
			            	pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
			            } else {
			            	pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(currentPage+1)+');"><span>다음</span></a>';
			            	pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
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
					$(".c-paging").html(pagingHtml);

				}

				if(totalCount > 0){
					$("#main-content > div.payment-result.c-expand.u-mb--m32.u-pt--0.u-pb--64 > div.c-nodata").hide();
					$("#main-content > div.payment-result.c-expand.u-mb--m32.u-pt--0.u-pb--64 > div.c-table.c-table--plr-8.u-mt--24").show();
					$("#main-content > div.payment-result.c-expand.u-mb--m32.u-pt--0.u-pb--64 > nav").show();
				}else{
					$("#main-content > div.payment-result.c-expand.u-mb--m32.u-pt--0.u-pb--64 > div.c-nodata").show();
					$("#main-content > div.payment-result.c-expand.u-mb--m32.u-pt--0.u-pb--64 > div.c-table.c-table--plr-8.u-mt--24").hide();
					$("#main-content > div.payment-result.c-expand.u-mb--m32.u-pt--0.u-pb--64 > nav").hide();
				}
			}
		});

	}
	catch(e){
	    alert(e.message);
	}
	finally {
	}
	var regularityToday = function (data) {
		return data.replace(/(\d{4})(\d{2})(\d{2})/g,'$1.$2.$3');
	}
}

var rateFilter = function() {
	var prdtIndCd = $("input[name='radPrdtIndCd']:checked").val();
	if ((prdtIndCdTemp != null && prdtIndCdTemp != prdtIndCd) || prdtIndCdTemp == null) {

		$('#selRateCd').val($('#selRateCd').siblings('label').text());
		$('#selRateCd').parent().removeClass('has-value');

		prdtIndCdTemp = prdtIndCd;
		var prdtIndNm;
		switch (prdtIndCd) {
			case "45" :
				prdtIndNm = "5G"
				break;
			case "04" :
				prdtIndNm = "LTE"
				break;
			case "03" :
				prdtIndNm = "3G"
				break;
		}
		$('#selRateCd option').filter(function (index) {
			if (prdtIndNm != $(this).attr('value2') && $(this).val() != '') {
			// if (prdtIndNm != $(this).attr('value2')) {
				$(this).hide();
			} else {
				$(this).show();
			}
		});
	}
}
