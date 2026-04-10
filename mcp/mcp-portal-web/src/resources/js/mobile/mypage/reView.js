/**
 *
 */
var infoCnt = 10;
var listIdNo = 1;

$(document).ready(function(){

    getReviewList();

});


// 사용후기 리스트 호출(더보기 겸용)
function getReviewList(){
        var varData = ajaxCommon.getSerializedData({
        pageNo : $("#pageNo").val()
    });


    ajaxCommon.getItem({
        id:'myBillInfoViewAjax'
        ,cache:false
        ,url:'/m/mypage/reViewListAjax.do'
        ,data: varData
        ,dataType:'json'
    },function(data){
            $("#moreInfo").remove();
            //이미지 유무에 따른 분기처리
            if(data.list && data.list.length > 0){
                for(var i = 0; i < data.list.length; i++){
                    var html = "";
                    html = setListHtml(data.list[i],html,listIdNo);

                    $("#listBody").append(html);
                    listIdNo = listIdNo + 1;
                }
                var moreHtml = "";
                moreHtml = setMoreHtml(data, infoCnt, moreHtml);
                $(".review-container").append(moreHtml);
            }else{
                $(".review-container").before(
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

            window.KTM.initialize();
    });
}

// 사용후기 list 본문 html 세팅
function setListHtml(data, html, listIdNo){
    var time = new Date(data.sysRdate);

    html +=	"<li class='review__item' id='list" + listIdNo + "'>";
    html +=	"<div class='c-flex c-flex--jfy-between'>";
      html +=	"<div class='label-wrap c-flex__shrink-0'>";
    html +=	"<span class='c-text-label c-text-label--mint-type1'>추천</span>";
    if(data.ntfYn == 1){
        html +=	"<span class='c-text-label c-text-label--red'>BEST</span>";
    }
      html +=	"</div>";
      html +=	"<button class='span c-text-label c-text-label--gray-4 u-co-gray-8' href='javascript:void(0);' onclick='deleteReView(" + listIdNo + "," + data.reviewId + ")'>삭제</button>";
    html += "<input type='hidden' id='contractNum' value='" + data.contractNum + "'/>";
    html += "<input type='hidden' id='requestKey' value='" + data.requestKey + "'/>";
    html +=	"</div>";
    html +=	"<h3 class='review__title'>" + data.reviewTitle + "</h3>";
    html +=	"<div class='review__info'>";



    if (data.mkRegNm  != "") {
        html +=	"<span class='review__user'>" + data.mkRegNm + "</span>";
        html +=	"<span class='review__date'>" + time.getFullYear() +"." + (time.getMonth()+1) +"." + time.getDate() + "</span>";
    } else {
       html +=	"<span class='review__user'>" + time.getFullYear() +"." + (time.getMonth()+1) +"." + time.getDate() + "</span>";
    }


    html +=	"</div>";
    if(data.reviewImgList){
        html += "<div class='review__image' id='content_img_" + listIdNo + "'>";
        if(data.reviewImgList.length > 1){html += "<span class='c-text-label c-text-label--darkgray img-count'>+ " + (data.reviewImgList.length - 1) + "</span>";}
        for(var i = 0; i < data.reviewImgList.length; i ++){
            html += '<div class="review__image__item">';
            html += "<img src='" + data.reviewImgList[i].filePathNm + "' alt='" + data.reviewImgList[i].fileAlt + "' onerror='this.src=\"/resources/images/mobile/content/img_review_noimage.png\"'>";
            html += '</div>';
        }
        html += "</div>";
    }
    html +=	"<div class='review__content fs-13 c-text-ellipsis--type3' id='content_" + listIdNo + "'>" + data.reviewSbst;
    if(data.snsInfo){
        html +=	"<p class='u-mt--8'>";
        html +=	"<a class='review__button' href='" + data.snsInfo + "'>SNS 공유 사용후기 자세히 보기</a>";
        html +=	"</p>";
      }
    html +=	"</div>";
    if(data.reviewImgList){
        html +=	"<div class='review__button-wrap' data-toggle-class='is-active' data-toggle-target='#content_img_" + listIdNo + "|#content_" + listIdNo + "'>";
    }else{
        html +=	"<div class='review__button-wrap' data-toggle-class='is-active' data-toggle-target='#content_" + listIdNo + "'>";
    }
      html +=	"<button type='button' aria-expanded='false'>";
    html +=	"<i class='c-icon c-icon--arrow-down-bold' aria-hidden='true'></i>";
      html +=	"</button>";
    html +=	"</div>";
    //html += "</h3>";
    html +=	"</li>";

    return html;
}

//사용후기 갯수에 따른 더보기 세팅
function setMoreHtml(data, infoCnt, html){
    // 조회된 list 길이 10 이상, 총 갯수 10 이상 && 총 갯수가 누적list 수 보다 많을 때
    if(data.list.length >= 10 && data.total > infoCnt){
            html += "<div class='c-button-wrap u-mt--8' id='moreInfo'>";
            html += "<button class='c-button c-button--full fs-14' href='javascript:void(0);' onclick='moreInfo()'>더보기<span class='c-button__sub u-co-gray-7 u-mr--8' id='infoCnt'>"
            if(data.total >= infoCnt + 10){
                html += (infoCnt*1).toLocaleString('ko-KR') + " / " + (data.total*1).toLocaleString('ko-KR');
            }else{
                html += ((infoCnt + (data.total%10))*1).toLocaleString('ko-KR') + " / " + (data.total*1).toLocaleString('ko-KR');
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
}

// 후기 삭제
function deleteReView(no,reviewId){
    new KTM.Confirm("작성한 후기를 삭제하시겠습니까?", function(){

        var varData = ajaxCommon.getSerializedData({
            requestKey : $("#list" + no + " #requestKey").val(),
            contractNum : $("#list" + no + " #contractNum").val(),
            reviewId : reviewId
        });
        this.close();

        ajaxCommon.getItem({
            id:'reviewDeleteAjax'
            ,cache:false
            ,url:'/m/mypage/reviewDeleteAjax.do'
            ,data: varData
            ,dataType:'json'
        },function(data){

            if(data.RESULT_CODE == "00000"){
                MCP.alert("후기 삭제가 완료되었습니다.");
                $("#list" + no).remove();
            }else if(data.RESULT_CODE == "0001"){
                MCP.alert("내가 작성한 후기만 삭제 가능합니다.");
                return;
            }else{
                MCP.alert("삭제에 실패하였습니다.");
                return
            }
        });
    });
}
