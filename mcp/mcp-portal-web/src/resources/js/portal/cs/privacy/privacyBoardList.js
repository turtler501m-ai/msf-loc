$(document).ready(function() {

    privacyEtcBoardListAjax($(".midTab").eq(1).attr("dtlCd"),1);
    privacyEtcBoardListAjax($(".midTab").eq(2).attr("dtlCd"),2);
    privacyEtcBoardListAjax($(".midTab").eq(3).attr("dtlCd"),3);

    var tabIndex = $("#tabIndex").val();
    if(tabIndex != "") {
        setTimeout(function(){
            $("#tabE"+ tabIndex).trigger("click");
        },300);
    }

    $('button[id^=tabE]').each(function(){
    	$('button[id^=tabE]').removeAttr("tabindex");
    	$('button[id^=tabE]').removeAttr("aria-controls");
    	setTimeout(function(){
    		$('button[id^=tabE]').first().trigger("click");
    	},10);
    	var hasClass = $(this).hasClass("is-active");
	    if(hasClass){
	    	$(this).attr('aria-selected',"true");
	    }else {
	    	$(this).attr('aria-selected',"false");
	    }
    });

    $('button[id^=tabE]').on("click", function(){
        $('button[id^=tabE]').removeClass('is-active');
        $('button[id^=tabE]').attr('aria-selected',"false");
        $(this).addClass('is-active');
        $(this).attr('aria-selected',"true");
        var tabId = $(this).attr('id');
        $('.c-tabs__panel .c-tabs__panel').removeClass('u-block');
    	$('.c-tabs__panel .c-tabs__panel[aria-labelledby =' + tabId + ']').addClass('u-block');
    });


});


function goPaging(pageNo){

    var searchNm = $("#searchNm").val();
    var sbstCtg = $("#sbstCtg").val();

    ajaxCommon.createForm({
        method:"post"
        ,action:"/cs/privacyBoardList.do"
    });

    ajaxCommon.attachHiddenElement("pageNo",pageNo);
    ajaxCommon.attachHiddenElement("searchNm",searchNm);
    ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
    ajaxCommon.formSubmit();
}

function searchBtn() {
    goPaging(1);
}

function goDetail(boardSeq) {

    ajaxCommon.createForm({
        method:"post"
        ,action:"/cs/privacyView.do"
    });
    var pageNo = $("#pageNo").val();
    var searchNm = $("#searchNm").val().trim();
    var sbstCtg = $("#sbstCtg").val();

    ajaxCommon.attachHiddenElement("pageNo",pageNo);
    ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
    ajaxCommon.attachHiddenElement("searchNm",searchNm);
    ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
    ajaxCommon.formSubmit();
};

var privacyEtcBoardListAjax = function(sbstCtg,tab) {

    var varData = ajaxCommon.getSerializedData({
        sbstCtg : sbstCtg,
        rownum : tab
    }); // 있는 필드 쓰자

    ajaxCommon.getItem({
        id:'privacyEtcListAjax'
        ,cache:false
        ,url:'/cs/privacyEtcHtml.do'
        ,data: varData
        ,dataType:"html"
        ,async:false
    },function(jsonObj){

        if(tab=="1"){
            $("#tabF2panel").html(jsonObj);
        } else if(tab=="2"){
            $("#tabF3panel").html(jsonObj);
        } else if(tab=="3"){
            $("#tabF4panel").html(jsonObj);
        }
        $('#subbody_loading').hide();
    });

};