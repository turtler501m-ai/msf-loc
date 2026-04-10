
var pageNo = parseInt(1,10);
var totalPageCount = parseInt($("#totalPageCount").val(),10);
var totalCount = parseInt($("#totalCount").val(),10);

$(document).ready(function() {
    getBoardList(pageNo);

    $("#tab1").click(function() {
        $("#eventStatus").val('ing');
        if($('#eventBranch').val() == 'E'){
			location.href = "/m/event/eventList.do";
        }else{
			location.href = "/m/event/cprtEventBoardList.do";
        }
    });

    $("#tab2").click(function() {
        $("#eventStatus").val('end');
        //getBoardList(pageNo);
    });

    $("#tab3").click(function() {
        $("#eventStatus").val('win');
        if($('#eventBranch').val() == 'E'){
            location.href = "/m/event/winnerList.do";
        }else{
			location.href = "/m/event/cprtEventWinnerList.do";
        }

    });

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

})
// 이번달 이벤트 진행중/종료
function getBoardList(pageNo) {
    var varData = "";
    var eventStatus = $("#eventStatus").val();
    var recordCount = 10;

    varData = ajaxCommon.getSerializedData({
        sbstCtg: $("#tabSelect").val(),
        eventStatus: $("#eventStatus").val(),
        pageNo: pageNo,
        recordCount: recordCount,
        eventBranch: $('#eventBranch').val()
    });

    ajaxCommon.getItem({
        id: 'getBoardEndList',
        cache: false,
        url: '/m/event/eventListAjax.do',
        data: varData,
        dataType: "json",
        async: false
    },
    function(jsonObj) {
        if(jsonObj != 0){
            const templateHtml =`<% _.each(listData, function(list){ %>
                                    <li class='event-list__item'>
                                        <div class='event-list__inside'>
                                            <div class='event-list__image'>
                                                <img src=../..<%- list.listImg %> alt="<%- list.imgDesc %>" >
                                                <% if (eventBranch == 'E' && list.winnerYn != ''){ %>
                                                    <a class="c-button c-button--sm c-button--black" href="javascript:void(0);" onclick="detailWinOpen('<%- list.ntcartSeq %>', '<%-list.sbstCtg%>', '<%-eventBranch%>')">
                                                        <span>당첨자 확인</span>
                                                    </a>
                                                <% } %>
                                                <% if (eventBranch == 'J' && list.winnerYn != ''){ %>
                                                    <a class="c-button c-button--sm c-button--black" href="javascript:void(0);" onclick="detailWinOpen('<%- list.ntcartSeq %>', '<%-list.sbstCtg%>', '<%-eventBranch%>')">
                                                        <span>당첨자 확인</span>
                                                    </a>
                                                <% } %>
                                            </div>
                                            <% if (eventStatus == 'end'){ %>
                                                <a class="event-list__anchor c-text-ellipsis--type2">
                                                    <span class="u-co-red">[종료]</span>
                                                    <%- list.ntcartSubject %>
                                                </a>
                                            <% } %>
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
            totalPageCount = jsonObj.pageInfoBean.totalPageCount;
            totalCount = jsonObj.pageInfoBean.totalCount;

            if(listData.length == 0){
                var innerHtml = '';
                innerHtml+='<div class="c-nodata">';
                innerHtml+='    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
                innerHtml+='    <p class="c-noadat__text">검색 결과가 없습니다.</p>';
                innerHtml+='</div>';

                $('#listArea2').html(innerHtml);
                $("#more_viewDiv2").hide();
            }else{
                if(pageNo >= 1){
                    for(i = 0; i < 1; i++){
                        $('#listArea2').html(compileTemplate({
                            'listData' : listData,
                            'eventStatus' : eventStatus,
                            'eventBranch' :$('#eventBranch').val()
                        }));
                    }
                }
                $("#more_viewDiv2").css('visibility','visible');
                $("#total_page2").html(replaceToStr(totalCount));
                $("#current_page2").html(replaceToStr($("#listArea2 .event-list__item").length));
                if (totalCount == $("#listArea2 .event-list__item").length) {
                    $("#more_viewDiv2").hide();
                }
            }
        }
    })
}

function goWinView(url) {
    ajaxCommon.createForm({
            method:"post"
            ,action: url
    });
    ajaxCommon.formSubmit();
};

function detailWinOpen(ntcartSeq, sbstCtg, eventBranch){
	var url = "/m/event/winnerDetail.do?ntcartSeq=" + ntcartSeq + "&sbstCtg=" + sbstCtg + "&eventBranch=" + eventBranch;
	location.href = url;
}

var bntMormView = function () {
    pageNo++;
    console.log(pageNo);
    if (totalPageCount >= pageNo ) {
        getBoardList(pageNo);
    }
}