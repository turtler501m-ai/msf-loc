function authCfrm(){

    if(certifySucess != 'Y'){
        MCP.alert("본인인증이 필요합니다.");
        return false;
    }

    if(!$("#chkAgree").is(':checked')){
        MCP.alert("개인정보 수집이용 동의가 필요합니다.");
        return false;
    }

    $("#cfrmYn").val('Y');

    location.href = '/order/orderTempList.do';
    //searchOrderTempList();

}

function searchOrderTempList(selPageNo) {
    var pageNo = $('#orderTempPageNo').val();
    if (typeof selPageNo !== 'undefined') {
          pageNo = selPageNo;
    }else{
        pageNo = $('#orderTempPageNo').val();
    }

    var varData = ajaxCommon.getSerializedData({
        pageNo : pageNo
    });

    ajaxCommon.getItem({
        id:'orderTempPageGroupListAjax'
        ,cache:false
        ,url:'/order/orderTempPageGroupListAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(data){

        if( data.RESULT_CODE == "00000" ){
            $("#divJoinArea").hide();

            var pageInfoBean = data.pageInfoBean;


            if(data.resultList && data.resultList.length > 0){
                $("#divOrderTempArea").hide();
                makeOrderTempList(data.resultList);
                //makeOrderTempList(testData.resultList);
                if(pageInfoBean.totalCount > 10){
                    makePaging(pageInfoBean,'orderTempPaging','searchOrderTempList');
                    $("#orderTempPaging").show();
                }else{
                    $("#orderTempPaging").hide();
                }
            }else{
                var noDataHtml = '';
                //noDataHtml += '<div class="c-nodata u-bt-gray-3 u-mt--48">';
                noDataHtml += '<div class="c-nodata u-mt--48">';
                noDataHtml += '<i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
                noDataHtml += '<p class="c-noadat__text">조회 결과가 없습니다.</p>';
                noDataHtml += '</div>';
                $("#divOrderTempArea").html(noDataHtml);
                $("#divOrderTempArea").show();
            }

        }else{
            var strMsg= "신청조회에 실패했습니다.";
            strMsg= (data.RESULT_MSG == undefined) ? strMsg : data.RESULT_MSG;
            MCP.alert(strMsg);
            return;
        }

    }
  );

}

//약관동의
function btnRegDtl(param){
    $('#targetTerms').val('Y');
    openPage('termsPop','/termsPop.do',param ,0);
}

function makeOrderTempList(listData){
    var html = '';
    $(listData).each(function(index) {
        var headHtml = '';
        var loopHtml = '';
        var footHtml = '';

        headHtml += '<li class="c-list__item">';
        headHtml += '<div class="c-list__head">';
        headHtml += '<div class="c-list__title">';
        headHtml += '<span class="c-hidden">분류</span>';
        //headHtml += '<b>'+this.prodNm+'</b>';
        if(this.tempType == 'SELF'){
            headHtml += '<b>'+emptyToTyphoon(this.selfProdNm)+'</b>';
            headHtml += '<div class="c-list__sub">자급제</div>';
        }else if(this.reqBuyType == 'UU'){
            if ("09" == this.usimKindsCd) {
                headHtml += '<b>eSIM</b>';
            } else {
                headHtml += '<b>'+this.prodNm+'</b>';
            }
            if(this.onOffType == '5' || this.onOffType == '7' || this.onOffType == '9'){
                headHtml += '<div class="c-list__sub">셀프개통</div>';
            }else{
                headHtml += '<div class="c-list__sub">온라인서식지</div>';
            }
        }else if(this.reqBuyType == 'MM'){
            headHtml += '<b>'+this.prodNm+'</b>';
            headHtml += '<div class="c-list__sub">'+this.phoneCtgLabel+'</div>';
        }
        headHtml += '</div>';
        headHtml += '<div class="c-list__sub">'+emptyToRplce(this.sysRdate,'-',false)+'</div>';
        headHtml += '</div>';
        headHtml += '<div class="c-list__body">';
        headHtml += '<div class="detail">';
        headHtml += '<ul class="product-info">';





        if(this.tempType == 'SELF'){
            loopHtml += '<li class="product-info__item">';
            loopHtml += '<i class="c-icon c-icon--phone-24" aria-hidden="true"></i>';
            loopHtml += '<span class="detail__sub-title c-hidden">휴대폰명</span>'+emptyToTyphoon(this.selfProdNm)+' / '+emptyToTyphoon(this.atribValNmOne)+' / '+emptyToTyphoon(this.atribValNmTwo);
            loopHtml += '</li>';
            loopHtml += '<li class="product-info__item">';
            loopHtml += '<i class="c-icon c-icon--payment-24" aria-hidden="true"></i>';
            loopHtml += '<span class="detail__sub-title c-hidden">요금제명</span>'+this.rateNm;
            loopHtml += '</li>';
        }else if(this.reqBuyType == 'UU'){
            loopHtml += '<li class="product-info__item">';
            loopHtml += '<i class="c-icon c-icon--payment-24" aria-hidden="true"></i>';
            loopHtml += '<span class="detail__sub-title c-hidden">요금제명</span>'+this.rateNm;
            loopHtml += '</li>';
        }else if(this.reqBuyType == 'MM'){
            loopHtml += '<li class="product-info__item">';
            loopHtml += '<i class="c-icon c-icon--phone-24" aria-hidden="true"></i>';
            loopHtml += '<span class="detail__sub-title c-hidden">휴대폰명</span>'+emptyToTyphoon(this.prodNm)+' / '+emptyToTyphoon(this.atribValNmOne)+' / '+emptyToTyphoon(this.atribValNmTwo);
            loopHtml += '</li>';
            loopHtml += '<li class="product-info__item">';
            loopHtml += '<i class="c-icon c-icon--payment-24" aria-hidden="true"></i>';
            loopHtml += '<span class="detail__sub-title c-hidden">요금제명</span>'+this.rateNm;
            loopHtml += '</li>';
        }





        footHtml += '</ul>';
        footHtml += '<div class="detail__item">';
        footHtml += '<span class="detail__sub-title">월 납부금액</span>';
        footHtml += '<b>'+numberWithCommas(this.settlAmt)+'원</b>';
        footHtml += '</div>';
        var stepNo = emptyToRplce(this.tmpStep,'0',true);
        footHtml += '<div class="detail__item">';
        footHtml += '<span class="detail__sub-title">진행단계<span class="u-co-black">('+parseInt(stepNo)+'/5)</span>';
        footHtml += '</span>';
        if(stepNo == 0){
            footHtml += '<b class="u-co-point-4"></b>';
        }else if(stepNo == 1){
            footHtml += '<b class="u-co-point-4">본인 확인 및 약관동의</b>';
        }else if(stepNo == 2){
            footHtml += '<b class="u-co-point-4">유심정보 및 신분증 확인</b>';
        }else if(stepNo == 3){
            footHtml += '<b class="u-co-point-4">가입신청 정보</b>';
        }else if(stepNo == 4){
            footHtml += '<b class="u-co-point-4">부가서비스 정보</b>';
        }else if(stepNo == 5){
            footHtml += '<b class="u-co-point-4">납부정보 및 가입정보 확인</b>';
        }
        footHtml += '</div>';
        footHtml += '<div class="c-button-wrap">';
        //if(stepNo > 0){
            footHtml += '<a id="join_'+this.requestKey+'" data-buytype="'+this.reqBuyType+'" data-saleyn="'+this.phoneSaleYn+'" class="c-button c-button-round--sm c-button--white" href="javascript:go_join_page(\''+this.requestKey+'\')"  >이어하기</a>';
        //}
        footHtml += '</div>';
        footHtml += '</div>';
        footHtml += '</div>';
        footHtml += '</li>';

        html += headHtml + loopHtml + footHtml;
    });

    $("#orderTempUlArea").html(html);
    $("#divOrderTempArea").show();

}

function makeOrderTempList_group(listData){
    var html = '';
    var headHtml = '';
    var loopHtml = '';
    var footHtml = '';
    var preReqKey = '';
    $(listData).each(function(index) {

        if(preReqKey != this.requestKey){

            if(index > 0){
                html += headHtml + loopHtml + footHtml;
                headHtml = '';
                loopHtml = '';
                footHtml = '';
            }

            headHtml += '<li class="c-list__item">';
            headHtml += '<div class="c-list__head">';
            headHtml += '<div class="c-list__title">';
            headHtml += '<span class="c-hidden">분류</span>';
            headHtml += '<b>'+this.prodNm+'</b>';

            if(this.onOffType == '5' ){
                headHtml += '<div class="c-list__sub">온라인(셀프개통)</div>';
            }else if(this.onOffType == '7'){
                headHtml += '<div class="c-list__sub">모바일(셀프개통)</div>';
            }else if(this.onOffType == '9'){
                headHtml += '<div class="c-list__sub">오프라인(셀프개통)</div>';
            }else{
                headHtml += '<div class="c-list__sub">셀프개통</div>';
            }
            headHtml += '</div>';
            headHtml += '<div class="c-list__sub">'+emptyToRplce(this.sysRdate,'-',false)+'</div>';
            headHtml += '</div>';
            headHtml += '<div class="c-list__body">';
            headHtml += '<div class="detail">';
            headHtml += '<ul class="product-info">';

        }

        loopHtml += '<li class="product-info__item">';


        if(this.reqBuyType == 'UU'){
            loopHtml += '<i class="c-icon c-icon--payment-24" aria-hidden="true"></i>';
            loopHtml += '<span class="detail__sub-title c-hidden">요금제명</span>'+this.rateNm;
        }else if(this.reqBuyType == 'MM'){
            loopHtml += '<i class="c-icon c-icon--phone-24" aria-hidden="true"></i>';
            loopHtml += '<span class="detail__sub-title c-hidden">휴대폰명</span>'+this.prodNm+' / '+this.atribValNmOne+' / '+this.atribValNmTwo;
        }

        loopHtml += '</li>';

        if(preReqKey != this.requestKey){

            footHtml += '</ul>';
            footHtml += '<div class="detail__item">';
            footHtml += '<span class="detail__sub-title">월 납부금액</span>';
            footHtml += '<b>'+numberWithCommas(this.settlAmt)+'원</b>';
            footHtml += '</div>';
            var stepNo = emptyToRplce(this.tmpStep,'0',true);
            footHtml += '<div class="detail__item">';
            footHtml += '<span class="detail__sub-title">진행단계<span class="u-co-black">('+parseInt(stepNo)+'/5)</span>';
            footHtml += '</span>';
            if(stepNo == 0){
                footHtml += '<b class="u-co-point-4"></b>';
            }else if(stepNo == 1){
                footHtml += '<b class="u-co-point-4">본인 확인 및 약관동의</b>';
            }else if(stepNo == 2){
                footHtml += '<b class="u-co-point-4">유심정보 및 신분증 확인</b>';
            }else if(stepNo == 3){
                footHtml += '<b class="u-co-point-4">가입신청 정보</b>';
            }else if(stepNo == 4){
                footHtml += '<b class="u-co-point-4">부가서비스 정보</b>';
            }else if(stepNo == 5){
                footHtml += '<b class="u-co-point-4">납부정보 및 가입정보 확인</b>';
            }
            footHtml += '</div>';
            footHtml += '<div class="c-button-wrap">';
            footHtml += '<a id="join_'+this.requestKey+'" data-buytype="'+this.reqBuyType+'" data-saleyn="'+this.phoneSaleYn+'" class="c-button c-button-round--sm c-button--white" href="#" onClick="go_join_page(\''+this.requestKey+'\')" >이어하기</a>';
            footHtml += '</div>';
            footHtml += '</div>';
            footHtml += '</div>';
            footHtml += '</li>';

        }
        preReqKey = this.requestKey;

    });

    if(listData.length == 1){
        html += headHtml + loopHtml + footHtml;
    }

    $("#orderTempUlArea").html(html);
    $("#divOrderTempArea").show();

}

function go_join_page(key){
    var buyType = $("#join_"+key).data('buytype');

    if(buyType != 'UU'){
        var saleYn = $("#join_"+key).data('saleyn');

        if(saleYn != 'Y'){
            MCP.alert("휴대폰 재고가 없습니다.");
            return;
        }
    }
    document.appForm.requestKey.value = key;
    document.appForm.submit();

}

function emptyToRplce(value,rplChr,isStep){

    if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
        return rplChr;
    } else{
        if(isStep && value.length > 1){
            value = value.substring(1,2);
        }

        return value;
    }

}

var makePaging = function(pageInfoBean, navId, fncNm){
    var totalCount = pageInfoBean.totalCount;
    var currentPage = pageInfoBean.pageNo;
    var countPerPage = pageInfoBean.pageSize;
    var totalPage = parseInt(totalCount / countPerPage);
    if((totalCount % countPerPage) > 0) {
        totalPage = totalPage + 1;
    }
    $("#"+navId).empty();
    var pageSize = 10 ; //페이지 사이즈
    var firstPageNoOnPageList = parseInt((currentPage - 1) / pageSize ) * pageSize + 1;
    var lastPageNoOnPageList = (firstPageNoOnPageList + pageSize - 1);
    if (lastPageNoOnPageList > totalPage) {
        lastPageNoOnPageList = totalPage;
    }

    var pageFirst = '';
    var pageLeft = '';

    if(currentPage > 1 ) {
        if (firstPageNoOnPageList > pageSize) {
            pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="'+fncNm+'(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
            pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="'+fncNm+'('+ (firstPageNoOnPageList-1) +');"><span>이전</span></a>';
        } else {
            pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="'+fncNm+'(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
            pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="'+fncNm+'('+(currentPage-1)+');"><span>이전</span></a>';
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
            pageStr += '<a class="c-paging__anchor c-paging__number" href="javascript:void(0);" onclick="'+fncNm+'(' + i + ')"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
        }
    }

    var pageLast = '';
    var pageRight = '';

    if(totalPage > currentPage ){
        if (lastPageNoOnPageList < totalPage) {
            pageRight = '<a class="c-button" href="javascript:void(0);" onclick="'+fncNm+'('+(firstPageNoOnPageList + pageSize)+');"><span>다음</span></a>';
            pageLast = '<a class="c-button" href="javascript:void(0);" onclick="'+fncNm+'('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
        } else {
            pageRight = '<a class="c-button" href="javascript:void(0);" onclick="'+fncNm+'('+(currentPage+1)+');"><span>다음</span></a>';
            pageLast = '<a class="c-button" href="javascript:void(0);" onclick="'+fncNm+'('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
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
    $("#"+navId).html(pagingHtml);

}