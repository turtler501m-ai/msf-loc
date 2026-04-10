var pageNo = parseInt($("#pageNo").val(),10);

$(document).ready(function(){

    pageNo = parseInt($("#pageNo").val(),10);

    // 팝업 안의 아코디언 기능
    $(document).on('click','.c-accordion__button',function() {
		var hasClass = $(this).hasClass('is-active');
		if(hasClass){
			$(this).removeClass('is-active');
		    $(this).parent().parent().find('.c-accordion__panel').stop().slideUp();
		} else {
			$(this).addClass('is-active');
			$(this).parent().siblings('.c-accordion__panel').stop().slideDown();
		};
	});

    $("#tab1").click(function() {
        $("#eventStatus").val('ing');
    });

    var tabIndex = $("#category").val();
    if(tabIndex != "") {
        setTimeout(function(){
            $('button[eventcategory='+tabIndex+']').trigger("click");
        },300);
    }

    $(document).on("click","._goDetail",function() {

        var ntcartSeq  = $(this).attr("ntcartSeq");
        var linkTarget = $(this).attr("linkTarget");
        var linkUrlAdr  = $.trim($(this).attr("linkUrlAdr"));

        if (linkUrlAdr != "") {
            if(linkTarget == 'Y'){
                window.open('about:blank').location.href=linkUrlAdr;
                KTM.LoadingSpinner.hide();
            } else {
                location.href = linkUrlAdr;
            }
        } else {
            ajaxCommon.createForm({
                method:"get"
                ,action:"/event/eventDetailAdminView.do"
            });
            ajaxCommon.attachHiddenElement("ntcartSeq",ntcartSeq);
            ajaxCommon.attachHiddenElement("sbstCtg",$('#eventBranch').val());
            ajaxCommon.attachHiddenElement("eventBranch",$('#eventBranch').val());
            ajaxCommon.attachHiddenElement("adminEventDate",$('#adminEventDate').val());
            ajaxCommon.formSubmit();
        }
    });

    if(paramValid(pageNo) == false || isNaN(pageNo)){
        pageNo = 1;
    }
    getBoardList(pageNo);
});

// 이번달 이벤트 진행중
function getBoardList(pageNo) {

    if(paramValid(pageNo) == false || isNaN(pageNo)){
        pageNo = 1;
    }

    $("#pageNo").val(pageNo);
    var varData = "";
    var eventBranch = $('#eventBranch').val();
    var recordCount = 10;

    varData = ajaxCommon.getSerializedData({
        sbstCtg: $("#tabSelect").val(),
        pageNo: pageNo,
        recordCount: recordCount,
        eventStatus : $("#eventStatus").val(),
        eventBranch: eventBranch,
        adminEventDate: $('#adminEventDate').val()
    });

    ajaxCommon.getItem({
        id: 'getBoardList',
        cache: false,
        url: '/event/eventListAdminViewAjax.do',
        data: varData,
        dataType: "json",
        async: false
    },
    function(jsonObj) {
        if(jsonObj != 0){
            var innerHtml = '';
            let listData = jsonObj.eventList;
            if(listData!=undefined && listData.length > 0){
                for (var i=0 ; i < listData.length; i++) {
                    var eventStartDt = listData[i].comEventStartDt;
                    var eventEndDt = listData[i].comEventEndDt;
                    var ntcartSeq = Number(listData[i].ntcartSeq);
                    innerHtml +='<li class="event-list__item" eventCategory="'+listData[i].eventCategory+'">';
                    if (listData[i].imgDesc.indexOf("_프로모션") > -1) {
                        innerHtml +='<a class="event-list__anchor _goModalPop" href="'+listData[i].linkUrlAdr+'" ntcartSeq="'+listData[i].ntcartSeq+'" linkTarget="'+listData[i].linkTarget+'" linkUrlAdr="'+listData[i].linkUrlAdr+'"  >';
                    } else {
                        innerHtml +='<a class="event-list__anchor _goDetail" href="javascript:void(0);" ntcartSeq="'+listData[i].ntcartSeq+'" linkTarget="'+listData[i].linkTarget+'" linkUrlAdr="'+listData[i].linkUrlAdr+'"  >';
                    }
                    innerHtml +='  <div class="event-list__image">';
                    innerHtml +='    <img src="'+ listData[i].listImg +'"  alt="'+listData[i].imgDesc+'" >';
                    innerHtml +='  </div>';
                    innerHtml +='    <span class="event-list__title">';
                    innerHtml +='      <span class="event-list__title__sub">'+ listData[i].ntcartSubject +' </span>';
                    innerHtml +='    </span>';
                    innerHtml +='    <div class="event-list__sub">';
                    innerHtml +='      <span class="event-list__sub__item">';
                    innerHtml +='        <span class="sr-only">기간</span>'+ eventStartDt +'~'+ eventEndDt +'';
                    innerHtml +='      </span>';
                    innerHtml +='      <span class="event-list__sub__item">';
                    innerHtml +='        <span class="event-list__sub__title">조회</span> '+ numberWithCommas(listData[i].ntcartHitCnt) +'';
                    innerHtml +='      </span>';
                    innerHtml +='    </div>';
                    innerHtml +='</a>';
                    innerHtml +='</li>';
                }
                $('#listArea1').html(innerHtml);
            }
            else{
                innerHtml += '<div class="c-nodata">';
                innerHtml += '    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
                innerHtml += '    <p class="c-nodata__text">조회 결과가 없습니다.</p>';
                innerHtml += '</div>';
                $('#listArea1').remove();
                $('#noDataIng').html(innerHtml);
            }
        }
        $('#subbody_loading').hide();
    });
    //$('#subbody_loading').hide();

}

//전체 클릭 시
function mainTabAllClick(){
    $('li[class=event-list__item]').show();
    $('#mainTab > button').removeClass('is-active');
    $('#categroyAll').addClass('is-active');
    $('.c-nodata').remove();

    if(!$('li[class=event-list__item]').length) {
        var innerHtml = '';
        innerHtml += '<div class="c-nodata">';
        innerHtml += '    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
        innerHtml += '    <p class="c-nodata__text">조회 결과가 없습니다.</p>';
        innerHtml += '</div>';
        $('#listArea1').hide();
        $('#noDataIng').append(innerHtml);
    } else {
        $('#listArea1').show();
    }
}

//전체 외 클릭 시
function mainTabClick(tab){
    $('#mainTab > button').removeClass('is-active');
    $(tab).addClass('is-active');

    var eventCategory = $(tab).attr("eventCategory");

    if(!$('li[class="event-list__item"][eventcategory='+eventCategory+']').length){
        $('.c-nodata').remove();
        var innerHtml = '';
        innerHtml += '<div class="c-nodata">';
        innerHtml += '    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
        innerHtml += '    <p class="c-nodata__text">조회 결과가 없습니다.</p>';
        innerHtml += '</div>';
        $('#listArea1').hide();
        $('#noDataIng').append(innerHtml);
    }else{
        $('.c-nodata').remove();
        $('#listArea1').show();
        $('li[class="event-list__item"][eventcategory!='+eventCategory+']').hide();
        $('li[class="event-list__item"][eventcategory='+eventCategory+']').show();
    }

}
