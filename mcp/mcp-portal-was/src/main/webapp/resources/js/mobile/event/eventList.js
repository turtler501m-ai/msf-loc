var pageObj = {
    $listArea1: null,
    $listArea2: null,
    $listArea3: null,
    $moreViewDiv: null,
    $listCnt: null,
    $totalCount: null,
    pageNo: 1,
    pageInfo: null
}

var pageNo = parseInt($("#pageNo").val(),10);
var totalPageCount = parseInt($("#totalPageCount").val(),10);

$(document).ready(function() {

    pageNo = parseInt($("#pageNo").val(),10);
    totalPageCount = parseInt($("#totalPageCount").val(),10);

    $("#tab1").click(function() {
        $("#eventStatus").val('ing');
        //getBoardList(pageNo);
    });

    $("#tab2").click(function() {
        $("#eventStatus").val('end');
        if($('#eventBranch').val() == 'E'){
            location.href = "/m/event/eventBoardEndList.do";
        }else{
            location.href = "/m/event/cprtEventBoardEndList.do";
        }
    });

    $("#tab3").click(function() {
        $("#eventStatus").val('win');
/*        if($('#eventBranch').val() == 'E'){
            location.href = "/m/event/winnerList.do";
        }else{
            location.href = "/m/event/cprtEventWinnerList.do";
        }*/
        location.href = "/m/event/winnerList.do";

    });

    var tabIndex = $("#category").val();
    if(tabIndex != "") {
        setTimeout(function(){
            $('button[eventcategory='+tabIndex+']').trigger("click");
        },300);
    }

    $('#more_view').click(function(){
        pageNo++;
    })

    $(document).on('click', '.openlink', function() {
        var $thisarea = $(this);
        $thisarea.hide();
        $thisarea.siblings(".tag_winner_box").show();
    });

    $(document).on('click', '.closelink', function() {
        var $thisarea = $(this).parent();
        $thisarea.hide();
        $thisarea.siblings(".openlink").show();
    });

    $(document).on("click","._goDetail",function() {

        var ntcartSeq  = $(this).attr("ntcartSeq");
        var linkTarget = $(this).attr("linkTarget");
        var linkUrlAdr  = $.trim($(this).attr("linkUrlAdr"));


        if (linkUrlAdr != "") {
            if(linkTarget == 'Y'){
                window.open("", "popupWin");
                ajaxCommon.createForm({
                    target:"popupWin"
                    , method:"post"
                    , action:linkUrlAdr
                });

                ajaxCommon.formSubmit();
                KTM.LoadingSpinner.hide();
            } else {
                location.href = linkUrlAdr;
            }
        } else {
            ajaxCommon.createForm({
                method:"get"
                ,action:"/m/event/eventDetail.do"
            });
            ajaxCommon.attachHiddenElement("ntcartSeq",ntcartSeq);
            ajaxCommon.attachHiddenElement("sbstCtg",$('#eventBranch').val());
            ajaxCommon.attachHiddenElement("eventBranch",$('#eventBranch').val());
            ajaxCommon.formSubmit();
        }


    });

    getBoardList(1);
    window.onpopstate = function(event) {
        event.preventDefault();
        closeView();
    }

    // 모달 팝업으로 띄웠을때 조회수 증가
    $(document).on("click","._goModalPop",function() {
        var ntcartSeq  = $(this).attr("ntcartSeq");
        var varData = ajaxCommon.getSerializedData({
            ntcartSeq : ntcartSeq
        });
        jQuery.ajax({
            id : 'updateHit',
            type : 'GET',
            cache : false,
            url : '/m/event/updateHit.do',
            data : varData,
            dataType : "json",
            success : function() {
                getBoardList(1);
            }
        });
    });

})
// 이번달 이벤트 진행중
function getBoardList(pageNo) {

    if(paramValid(pageNo) == false || isNaN(pageNo)){
        pageNo = 1;
    }

    $("#pageNo").val(pageNo);
    var varData = "";
    var eventStatus = $("#eventStatus").val();
    var eventBranch = $('#eventBranch').val();
    var recordCount = 10;

    if (pageNo == 1 && $("#initPageNo").val() != "") {
       recordCount = parseInt($("#initPageNo").val(),10) * 10 ;
       $("#initPageNo").val(1);
    }

    varData = ajaxCommon.getSerializedData({
        sbstCtg: $("#tabSelect").val(),
        eventStatus: $("#eventStatus").val(),
        pageNo: pageNo,
        recordCount: recordCount,
        eventBranch: $('#eventBranch').val()
    });

    ajaxCommon.getItem({
        id: 'getBoardList',
        cache: false,
        url: '/m/event/eventListAjax.do',
        data: varData,
        dataType: "json",
        async: false
    },
    function(jsonObj) {
        if(jsonObj != 0){
            const templateHtml =`<% _.each(listData, function(list){ %>
                                    <li class='event-list__item' eventCategory="<%- list.eventCategory %>">
                                        <div class='event-list__inside'>
                                            <div class='event-list__image'>
                                                <img src="<%- list.listImg %>" alt="<%- list.imgDesc %>" class="_goDetail" href="javascript:void(0);" ntcartSeq="<%- list.ntcartSeq %>" linkTarget="<%- list.linkTargetMo %>" linkUrlAdr="<%- list.linkUrlAdrMo %>" >
                                            </div>
                                        <% if (list.imgDesc.indexOf("_프로모션") > -1) { %>
                                            <a class="event-list__anchor c-text-ellipsis--type2 _goModalPop" href="<%- list.linkUrlAdrMo %>" ntcartSeq="<%- list.ntcartSeq %>" linkTarget="<%- list.linkTargetMo %>" linkUrlAdr="<%- list.linkUrlAdrMo %>" >
                                        <% } else { %>
                                            <a class="event-list__anchor c-text-ellipsis--type2 _goDetail" href="javascript:void(0);" ntcartSeq="<%- list.ntcartSeq %>" linkTarget="<%- list.linkTargetMo %>" linkUrlAdr="<%- list.linkUrlAdrMo %>" >
                                        <% } %>
                                                <span><%- list.ntcartSubject %></span>
                                            </a>
                                        </div>
                                        <div class='event-list__sub'>
                                            <span><%- list.comEventStartDt %> ~ <%- list.comEventEndDt %><span>
                                            <span class='tit'>조회</span>
                                            <span><%- replaceToStr(list.ntcartHitCnt) %></span>
                                        </div>
                                    </li>
                                <% }); %>
                                `;
            const compileTemplate = _.template(templateHtml);
            let listData = jsonObj.eventList;
            let pageInfo = jsonObj.pageInfoBean;

            if(listData.length == 0){
                var innerHtml = '';
                innerHtml+='<div class="c-nodata">';
                innerHtml+='    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
                innerHtml+='    <p class="c-noadat__text">검색 결과가 없습니다.</p>';
                innerHtml+='</div>';

                $('#listArea1').html(innerHtml);
            }else{
                $('#listArea1').html(compileTemplate({
                    'listData' : listData
                }));
            }
        }
    })


}

function detailOpen(ntcartSeq, sbstCtg, eventBranch, linkTarget){
    if(linkTarget == 'Y'){
        linkTarget = "_blank";

        ajaxCommon.createForm({
                method:"post"
                ,action:"/m/event/eventDetail.do"
                ,target: linkTarget
        });
        ajaxCommon.attachHiddenElement("ntcartSeq",ntcartSeq);
        ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
        ajaxCommon.attachHiddenElement("eventBranch",eventBranch);
        ajaxCommon.formSubmit();
    }else{
        var url = "/m/event/eventDetail.do?ntcartSeq=" + ntcartSeq + "&sbstCtg=" + sbstCtg + "&eventBranch=" + eventBranch;
        location.href = url;
    }
}


function goWinView(url) {
    ajaxCommon.createForm({
            method:"post"
            ,action: url
    });
    ajaxCommon.formSubmit();
};

var bntMormView = function () {
    if (totalPageCount < pageNo ) {
        alert("마지막 페이지 입니다.");
    } else {
        getBoardList(pageNo);
    }
}

//전체 클릭 시
function mainTabAllClick(){
    $('li[class=event-list__item]').show();
    $('#mainTab > button').removeClass('is-active');
    $('#categroyAll').addClass('is-active');
    $('#listArea1').show();
}

//전체 외 클릭 시
function mainTabClick(tab){
    $('#mainTab > button').removeClass('is-active');
    $(tab).addClass('is-active');

    var eventCategory = $(tab).attr("eventCategory");

    if(!$('li[class="event-list__item"][eventcategory='+eventCategory+']').length){
        $('.c-nodata').remove();
        var innerHtml = '';
        innerHtml+='<div class="c-nodata">';
        innerHtml+='    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
        innerHtml+='    <p class="c-noadat__text">검색 결과가 없습니다.</p>';
        innerHtml+='</div>';
        $('.event-list__item').hide();
        $('#listArea1').append(innerHtml);
    }else{
        $('.c-nodata').remove();
        $('#listArea1').show();
        $('li[class="event-list__item"][eventcategory!='+eventCategory+']').hide();
        $('li[class="event-list__item"][eventcategory='+eventCategory+']').show();
    }

}
