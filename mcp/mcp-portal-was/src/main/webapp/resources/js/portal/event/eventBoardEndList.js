var pageNo = parseInt($("#pageNo").val(),10);
var totalPageCount = parseInt($("#totalPageCount").val(),10);

$(document).ready(function(){

    $("#tab1").click(function() {
        $("#eventStatus").val('ing');
        if($('#eventBranch').val() == 'E'){
            location.href = "/event/eventBoardList.do";
        }else{
			location.href = "/event/cprtEventBoardList.do";
        }
    });

    $("#tab2").click(function() {
        $("#eventStatus").val('end');
        //getBoardList(pageNo);
    });

    $("#tab3").click(function() {
        $("#eventStatus").val('win');
        if($('#eventBranch').val() == 'E'){
			location.href = "/event/winnerList.do";
        }else{
			location.href = "/event/cprtEventWinnerList.do";
        }
    });

    getBoardList(pageNo);

    $(".btn_show_title").click(function() {
        $thisarea = $(this).parent();
        $thisarea.hide();
        $thisarea.siblings(".event_content").show();
    });

    //페이징 Click
    $(document).on("click",".c-paging__number",function(){
        var pageNo = parseInt($(this).attr("pageNo"),10) ;
        getBoardList(pageNo);
    });

    $(document).on("click",".c-button",function(){
        var pageNo = parseInt($(this).attr("pageNo"),10) ;
        getBoardList(pageNo);
    });

});

// 이번달 이벤트 진행중
function getBoardList(pageNo) {
    $("#pageNo").val(pageNo);
    var varData = "";
    var eventBranch = $('#eventBranch').val();
    var recordCount = 10;

    varData = ajaxCommon.getSerializedData({
        sbstCtg: $("#tabSelect").val(),
        pageNo: pageNo,
        recordCount: recordCount,
        eventStatus : $("#eventStatus").val(),
        eventBranch: eventBranch
    });

    ajaxCommon.getItem({
        id: 'getBoardEndList',
        cache: false,
        url: '/event/eventListAjax.do',
        data: varData,
        dataType: "json",
        async: false
    },
    function(jsonObj) {
        if(jsonObj != 0){
            var innerHtml = '';
            let listData = jsonObj.eventList;
            let pageInfo = jsonObj.pageInfoBean;
            if(listData!=undefined && listData.length > 0){
                for (var i=0 ; i < listData.length; i++) {
                    var eventStartDt = listData[i].comEventStartDt;
                    var eventEndDt = listData[i].comEventEndDt;
                    var ntcartSeq = Number(listData[i].ntcartSeq);
                    innerHtml +='<li class="event-list__item">';
                    innerHtml +='<a class="event-list__anchor" href="javascript:;">';
                    innerHtml +='  <div class="event-list__image">';
                    innerHtml +='    <img src="'+ listData[i].listImg +'" alt="'+ listData[i].imgDesc +'">';
                    innerHtml +='  </div>';
                    innerHtml +='  <span class="event-list__title">';
                    innerHtml +='    <span class="event-list__title__sub u-co-red">[종료]</span>'+ listData[i].ntcartSubject +'';
                    innerHtml +='  </span>';
                    innerHtml +='  <div class="event-list__sub">';
                    innerHtml +='    <span class="event-list__sub__item">';
                    innerHtml +='      <span class="sr-only">기간</span>'+ eventStartDt +'~'+ eventEndDt +'';
                    innerHtml +='    </span>';
                    innerHtml +='    <span class="event-list__sub__item">';
                    innerHtml +='      <span class="event-list__sub__title">조회</span>'+ numberWithCommas(listData[i].ntcartHitCnt) +'';
                    innerHtml +='    </span>';
                    innerHtml +='  </div>';
                    innerHtml +='</a>';
                    if(listData[i].winnerYn != ''){
                        innerHtml +='  <a class="c-button c-button--sm c-button--primary" href="javascript:void(0);" onclick=detailWinOpen(\''+ntcartSeq+'\',\''+ listData[i].sbstCtg+'\',\''+ eventBranch+'\') >';
                        innerHtml +='    <span>당첨자 확인</span>';
                        innerHtml +='    <i class="c-icon c-icon--anchor" aria-hidden="true"></i>';
                        innerHtml +='  </a><!-- //-->';
                    }
                    innerHtml +='</li>';
                }
                $('#listArea2').html(innerHtml);

                ajaxCommon.setPortalPaging($('#paging'), pageInfo);

                if(pageInfo.totalCount <= 10){
                    $('#paging').hide();
                }
            }
            else{
                innerHtml += '<div class="c-nodata">';
                innerHtml += '  <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
                innerHtml += '  <p class="c-nodata__text">조회 결과가 없습니다.</p>';
                innerHtml += '</div>';
                $('#listArea2').remove();
                $('#noDataEnd').html(innerHtml);
            }
        }
	    $('#subbody_loading').hide();
    });
    //$('#subbody_loading').hide();
}

function goWinView(url) {
    ajaxCommon.createForm({
            method:"post"
            ,action: url
    });
    ajaxCommon.formSubmit();
};

function detailWinOpen(ntcartSeq, sbstCtg, eventBranch){
    var action = "";
    if($('#eventBranch').val() == 'E'){
        action = "/event/winnerDetail.do";
    }else{
        action = "/event/cprtEventWinnerDetail.do";
    }

	var url = action + "?ntcartSeq=" + ntcartSeq + "&sbstCtg=" + sbstCtg + "&eventBranch=" + eventBranch;
	location.href = url;
}

var goPage = function(pageNo) {

    ajaxCommon.createForm({
        method:"get"
        ,action:"/event/eventBoardList.do"
     });

    ajaxCommon.attachHiddenElement("pageNo",pageNo);
    ajaxCommon.attachHiddenElement("sbstCtg",$("#sbstCtg").val());
    ajaxCommon.attachHiddenElement("ntcartSbst",$.trim($("#ntcartSbst").val()));
    ajaxCommon.formSubmit();


};
