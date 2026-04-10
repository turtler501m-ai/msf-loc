/**
 *
 */
var infoCnt = 10;
var listIdNo = 1;
var scrollX = undefined;
var scrollY = undefined;
$(document).ready(function(){
    //$(".ly-footer").hide();
    getReviewList();

});


// 사용후기 리스트 호출(더보기 겸용)
function getReviewList(){
        var varData = ajaxCommon.getSerializedData({
        pageNo : $("#pageNo").val()
    });


    ajaxCommon.getItem({
        id:'reViewListAjax'
        ,cache:false
        ,async:true
        ,url:'/mypage/reViewListAjax.do'
        ,data: varData
        ,dataType:'json'
    },function(data){

            $("#moreInfo").remove();
            $("#listBody").html('');
            if(data.pageInfoBean.totalCount > 10){
                $("#paging").show();
                ajaxCommon.setPortalPaging($("#paging"), data.pageInfoBean);
            }else{
                $("#paging").hide();
            }
            //이미지 유무에 따른 분기처리
            if(data.list && data.list.length > 0){
                for(var i = 0; i < data.list.length; i++){
                    var html = "";
                    html = setListHtml(data.list[i],html,listIdNo);

                    $("#listBody").append(html);
                    listIdNo = listIdNo + 1;
                }
/*				var moreHtml = "";
                moreHtml = setMoreHtml(data, infoCnt, moreHtml);
                $(".review-container").append(moreHtml);*/
            }else{
                $("#accordionReview").before(
                        "<div class='c-nodata'>" +
                            "<i class='c-icon c-icon--nodata' aria-hidden='true'></i>" +
                            "<p class='c-noadat__text'>조회 결과가 없습니다.</p>" +
                        "</div>");
            }
            var pgNo = $("#pageNo").val()*1;
            if(pgNo == 0){
                pgNo = 1;
            }

            $("#pageNo").val(pgNo + 1);

            infoCnt = infoCnt + 10;

            $(document).on("click", ".c-paging a", function(){
                if($(this).attr("pageno")){
                    $("#pageNo").val($(this).attr("pageno"));
                    getReviewList();
                }
            });
            window.KTM.initialize();
            if(scrollX && scrollY){
                window.scrollTo(scrollX, scrollY);
                scrollX, scrollY = undefined;
            }else{
                window.scrollTo(0,0);
            }
            $("#subbody_loading").hide();
            $(".ly-footer").show();
    });

}

// 사용후기 list 본문 html 세팅
function setListHtml(data, html, listIdNo){
    var time = new Date(data.sysRdate);

    html += '<li class="c-accordion__item" id="list' + listIdNo + '">';
    html += '<div class="c-accordion__head">';
    html += '<div class="review__label-wrap">';
    if(data.commendYn == 1){
        html += '<span class="c-text-label c-text-label--mint-type2">추천</span>';
    }else{
        html += '<span class="c-text-label c-text-label--gray-type1">비추천</span>';
    }
    if(data.ntfYn == 1){
        html +=	'<span class="c-text-label c-text-label--red">BEST</span>';
    }
    html += '</div>';
    html += '<ul class="review__info">';
    html += '<li class="review__info__item">';
    html += '<span class="sr-only">작성자</span>' + data.mkRegNm ;
    html += "<input type='hidden' id='contractNum' value='" + data.contractNum + "'/>";
    html += "<input type='hidden' id='requestKey' value='" + data.requestKey + "'/>";
    html += '</li>';
    html += '<li class="review__info__item">';
    html += '<span class="sr-only">작성일</span>' + time.getFullYear() +"." + (time.getMonth()+1) +".";
    if(time.getDate() > 9){
        html += time.getDate();
    }else{
        html += '0' + time.getDate();
    }
    html += '</li>';
    html += '<li class="review__info__item">';
    html += '<button class="c-button">';
    html += '<span class="sr-only">삭제</span>';
    html += '<i class="c-icon c-icon--trash" aria-hidden="true" href="javascript:void(0);" onclick="deleteReView(' + listIdNo + ',' + data.reviewId + ')"></i>';
    html += '</button>';
    html += '</li>';
    html += '</ul>';
    html += '<strong class="review__title">' + data.reviewTitle + '</strong>';
    html += '<button class="runtime-data-insert c-accordion__button" id="acc_header_c' + listIdNo + '" type="button" aria-expanded="false" aria-controls="acc_content_c' + listIdNo + '" >';
    html += '<span class="c-hidden">유심 후기/바로배송 유심 후기 이벤트 내용 전체보기</span>';
    html += '</button>';
    html += '</div>';
    html += '<div class="c-accordion__panel expand" id="acc_content_c' + listIdNo + '" aria-labelledby="acc_header_c' + listIdNo + '">';
    html += '<div class="c-accordion__inside">';
    html += '<div>';
    html += '<div class="review__content">';
    if(data.reviewImgList && data.reviewImgList.length > 0){
        html += '<div class="review__preview">';
        html += '<img src="' + data.reviewImgList[0].filePathNm + '" alt="' + data.reviewImgList[0].fileAlt + '" onerror="this.src=\'/resources/images/portal/content/img_review_noimage.png\'">';

        if(data.reviewImgList.length > 1){
            html += '<span class="review__image__label">';
            html += '<span class="sr-only">썸네일사진 외 추가로 등록된 후기사진 개수</span>+' + (data.reviewImgList.length - 1);
            html += '</span>';
        }

        html += '</div>';
    }
    html += '<p class="review__text">' + data.reviewSbst + '</p><!-- 데이터 있을 경우 노출-->';
    if(data.snsInfo && data.snsInfo != ''){
        html += '<a class="review__button" href="' + data.snsInfo + '" target="_blank" title="새창">SNS 공유 사용후기 자세히 보기</a>';
    }
    if(data.reviewImgList && data.reviewImgList.length > 0){
        html += '<ul class="review__images">';
        for(var i = 0; i < data.reviewImgList.length; i ++){
            html += '<li class="review__images__item">';
            html += '<div class="review__image">';
            html += '<img src="' + data.reviewImgList[i].filePathNm + '" alt="' + data.reviewImgList[i].fileAlt + '" aria-hidden="true" onerror="this.src=\'/resources/images/portal/content/img_review_noimage.png\'">';
            html += '</div>';
            if(data.reviewImgList[i].fileAlt){
                html += '<p class="c-bullet c-bullet--dot u-co-gray">' + data.reviewImgList[i].fileAlt + '</p>';
            }
            html += '</li>';
        }
        html += '</ul>';
    }
    html += '</div>';
    html += '</div>';
    html += '</div>';
    html += '</div>';
    html += '</li>';
    html += '';
    html += '';
    html += '';


    return html;
}
/*
//사용후기 갯수에 따른 더보기 세팅
function setMoreHtml(data, infoCnt, html){
    // 조회된 list 길이 10 이상, 총 갯수 10 이상 && 총 갯수가 누적list 수 보다 많을 때
    if(data.list.length >= 10 && data.total > infoCnt){
            html += "<div class='c-button-wrap u-mt--8' id='moreInfo'>";
            html += "<button class='c-button c-button--full fs-14' onclick='moreInfo()'>더보기<span class='c-button__sub u-co-gray-7 u-mr--8' id='infoCnt'>"
            if(data.total >= infoCnt + 10){
                html += (infoCnt + 10) + " / " + data.total;
            }else{
                html += (infoCnt + (data.total%10)) + " / " + data.total;
            }
            html += "</span>";
            html += "<i class='c-icon c-icon--arrow-bottom' aria-hidden='true'></i>";
            html += "</button>";
            html += "</div>";
    }

    return html;
}
//더보기 버튼 클릭 이벤트 함수
function moreInfo(){
    getReviewList();
}*/

// 후기 삭제
function deleteReView(no,reviewId){
    new KTM.Confirm("작성한 후기를 삭제하시겠습니까?", function(){

        var varData = ajaxCommon.getSerializedData({
            requestKey : $("#list" + no + " #requestKey").val(),
            contractNum : $("#list" + no + " #contractNum").val(),
            reviewId : reviewId
        });


        ajaxCommon.getItem({
            id:'reviewDeleteAjax'
            ,cache:false
            ,url:'/mypage/reviewDeleteAjax.do'
            ,data: varData
            ,dataType:'json'
        },function(data){
            if(data.RESULT_CODE == "00000"){
                MCP.alert("후기 삭제가 완료되었습니다.", function(){
                    var pageNo;
                    if($(".c-paging__anchor--current").attr("pageno")){
                        pageNo = $(".c-paging__anchor--current").attr("pageno");
                    }else{
                        pageNo = 1;
                    }
                    $("#pageNo").val(pageNo);
                    scrollX = window.scrollX;
                    scrollY = window.scrollY;
                    getReviewList();
                });

            }else if(data.RESULT_CODE == "0001"){
                MCP.alert("내가 작성한 후기만 삭제 가능합니다.");
                return;
            }else{
                MCP.alert("삭제에 실패하였습니다.");
                return
            }
        });
        this.close();
    });
}
