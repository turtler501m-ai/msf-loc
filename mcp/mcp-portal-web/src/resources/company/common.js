var isLcationTel = false;
var isUrlChange = false;

function fn_mobileCheck() {
    if( navigator.userAgent.match(/Android/i)
             || navigator.userAgent.match(/webOS/i)
             || navigator.userAgent.match(/iPhone/i)
             || navigator.userAgent.match(/iPad/i)
             || navigator.userAgent.match(/iPod/i)
             || navigator.userAgent.match(/BlackBerry/i)
             || navigator.userAgent.match(/BB/i)
             || navigator.userAgent.match(/Windows Phone/i)
    ){
        return true;
    } else {
        return false;
    }
}

function getInternetExplorerVersion() {
    var rv = -1; // Return value assumes failure.
    if (navigator.appName == 'Microsoft Internet Explorer') {
         var ua = navigator.userAgent;
         var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
         if (re.exec(ua) != null)
             rv = parseFloat(RegExp.$1);
        }
    return rv;
}


function fn_btnSwitch(obj,target,cnt,prefix,subfix,changObj,changeClass,changObj2,changeClass2){

	var buttonTxt = $(obj).text(),
        resultTxt;

    if ($(obj).hasClass('btn_dropdown_image_open')) {
        resultTxt = buttonTxt.replace('열기', '닫기');
    } else {
        resultTxt = buttonTxt.replace('닫기', '열기');
    }

    $(obj).find('.hidden').text(resultTxt);


	//$(this).attr('title','닫기');
    if($('#'+target+cnt).is(':visible')){
        if(changObj != undefined && changObj != null) $("#"+changObj+cnt).removeClass(changeClass);
        if(changObj2 != undefined && changObj2 != null) $("#"+changObj2+cnt).removeClass(changeClass2);
    }else{
        if(changObj != undefined && changObj != null) $("#"+changObj+cnt).addClass(changeClass);
        if(changObj2 != undefined && changObj2 != null) $("#"+changObj2+cnt).addClass(changeClass2);
    }

    fn_displaySwitch(obj,target+cnt,prefix,subfix)
}
function fn_displaySwitch(obj,target,prefix,subfix){
    if(prefix != undefined){
        if($('#'+target).is(':visible')){
            $(obj).attr('class',$(obj).attr('class').replace(subfix,prefix));
        }else{
            $(obj).attr('class',$(obj).attr('class').replace(prefix,subfix));
        }
    }
    $('#'+target).toggle();
}

function fn_tabMode(obj, cnt){
    if(cnt == undefined || cnt.length < 1){ cnt = 0;}
    $("[id^="+obj+"]").each(function(index, value){
        if(index == cnt){
            $("#"+value.id).show();
        }else{
            $("#"+value.id).hide();
        }
    });



}

function fn_tabStatus(obj,target,prefix,subfix){
    if(obj.type == 'radio'){
        $(obj).prop('checked');
    }

    $(obj).parent().find('div').removeClass('active');
    $(obj).addClass('active');
    if(target != undefined && target != null && target != ''){
        $(obj).parent().find('div').each(function(index, value){
            $(this).find(target).attr('src',$(this).find(target).attr('src').replace(subfix,prefix));
        });
        $(obj).find(target).attr('src',$(obj).find(target).attr('src').replace(prefix,subfix));
    }
}


function fn_tabReStatus(obj,target,prefix,subfix){
    $(obj).parent().find('div').removeClass('active');
    $(obj).find('div').addClass('active');

    if(target != undefined && target != null && target != ''){

        $(obj).parent().find('div').each(function(index, value){
            $(this).find(target).attr('src',$(this).find(target).attr('src').replace(subfix,prefix));
        });
        $(obj).find(target).attr('src',$(obj).find(target).attr('src').replace(prefix,subfix));
    }
}

function fn_tabUpStatus(obj,target,prefix,subfix){
    var $parents = $(obj).parent().parent();
    $parents.find('div').removeClass('active');
    $(obj).parent().addClass('active');

    if(target != undefined && target != null && target != ''){

        $parents.find('div').each(function(index, value){
            $(this).find(target).attr('src',$(this).find(target).attr('src').replace(subfix,prefix));
        });
        $(obj).find(target).attr('src',$(obj).find(target).attr('src').replace(prefix,subfix));
    }
}


function fn_tabAStatus(obj,target,prefix,subfix){
    $(obj).parent().find('a').removeClass('active');
    $(obj).addClass('active');

    if(target != undefined && target != null && target != ''){

        $(obj).parent().find('a').each(function(index, value){
            $(this).find(target).attr('src',$(this).find(target).attr('src').replace(subfix,prefix));
        });
        $(obj).find(target).attr('src',$(obj).find(target).attr('src').replace(prefix,subfix));
    }
}

function fn_tabBStatus(obj,target,prefix,subfix){
    $(obj).parent().siblings().find('a').removeClass('active');
    $(obj).addClass('active');

    if(target != undefined && target != null && target != ''){

        $(obj).parent().siblings().find('a').each(function(index, value){
            $(this).find(target).attr('src',$(this).find(target).attr('src').replace(subfix,prefix));
        });
        $(obj).find(target).attr('src',$(obj).find(target).attr('src').replace(prefix,subfix));
    }
    return false;
}


function fn_imgSwitch(obj,prefix,subfix){
    $(obj).find('img').each(function(index, value){
        if($(this).attr('src').indexOf(subfix) < 0)
            $(this).attr('src',$(this).attr('src').replace(prefix,subfix));
        else
            $(this).attr('src',$(this).attr('src').replace(subfix,prefix));
    });
}

function fn_choiceToChangeBK(target1,target2,target3){
    var _choice = "";
    var obj="#"+target1+" "+target2;
    $(obj).find(target3).find('[type=radio]').each(function(index, value){
        if($(this).prop('checked')) _choice = index;
    });
    $(obj).find(target3).removeClass('background_color_f9f9f9');
    $(obj).find(target3).eq(_choice).addClass('background_color_f9f9f9');
}

function fn_layerPop(el,_width,_height,$baseDom) {


    $('.dim-layer .pop-layer_red .popup_content .summary .icon ul li:nth-child(1) img').attr({src:"/resources/images/front/fee_pop_icon_01.png"});
    $('.dim-layer .pop-layer_red .popup_content .summary .icon ul li:nth-child(2) img').attr({src:"/resources/images/front/fee_pop_icon_02.png"});
    $('.dim-layer .pop-layer_red .popup_content .summary .icon ul li:nth-child(3) img').attr({src:"/resources/images/front/fee_pop_icon_03.png"});
    $('.dim-layer .pop-layer_red .popup_content .summary .icon ul li:nth-child(4) img').attr({src:"/resources/images/front/fee_pop_icon_04.png"});


    var layout_width = '500';
    var layout_height = '630';
    if(_width != undefined && _width != null && _width != ''){
        layout_width = _width
    }
    if(_height != undefined && _height != null && _height != ''){
        layout_height = _height
    }
    $('.popup_top').attr('style','width:'+layout_width+'px');
    $('.popup_content').css({width:layout_width+"px"});
    $('.popup_content').css({maxHeight:$(window).height()-300});

    $('.dim-layer').children().each(function(index, value){
        $('#'+value.id).hide();
    });

    var $el = $('#'+el);        //레이어의 id를 $el 변수에 저장
    var isDim = $('.dim-layer').children().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수
    $el.show();


    isDim ? $('.dim-layer').css("display", "flex").fadeIn() : $el.css("display", "flex").fadeIn();

    var $elWidth = ~~($el.outerWidth()),
        $elHeight = ~~($el.outerHeight()),
        docWidth = $(document).width(),
        docHeight = $(document).height();




    $($el).css({left:"50%"});
    $($el).css({top:"50%"});
    $($el).css({marginTop:-$($el).height()/2});
    $($el).css({marginLeft:-$($el).width()/2});



    $(window).resize(function() {
        $($el).css({top:"0"});
        $($el).css({marginTop:"50px"});
    });
    if($(window).height()<200){
        $($el).css({marginTop:"0"});
        $($el).css({top:"0%"});
    }

    var refFocusEl = null;
    $el.find('.popup_title:eq(0)').attr('tabindex','0');
    $el.find('.popup_title:eq(0)').show().focus();
    if ($baseDom != undefined) {
        refFocusEl = $baseDom;
    }

    $el.find('.popup_cancel').click(function () {
        var objDim = isDim ? $('.dim-layer') : $el; // 닫기 버튼을 클릭하면 레이어가 닫힌다.
        fn_layoutClose(objDim);

        if(refFocusEl) refFocusEl.focus();
        refFocusEl = null;

        return false;
    });

    $(".dim-layer .pop-layer_red .red_top .popup_cancel img").keypress(function(e) {
        if(e.which == 13) {
            fn_layoutClose($('.dim-layer'));
        }
    });


    $(document).on('keydown', 'a.popup_cancel', function (e) {
        var isShift = window.event.shiftKey ? true : false;

        if(isShift && (e.keyCode == 9)){
            return;
        }else if(event.keyCode == 9){
            $('.popup_title').focus();
            $('.red_title').focus();
            return false;
        } //end if
    });

    $(document).on("mouseleave",".gnb_2depth",function(){
        $('.gnb_2depth').hide();
        $('#mainMenu ul li img').hide();
    });

    $('.layer .dimBg').click(function () {
        fn_layoutClose($('.dim-layer'));
        return false;
    });

    $(document).find('body').css({
        'overflow': 'hidden'
    });


    function fn_layoutClose(obj){
        obj.fadeOut();
        $(document).find('html, body').css({
            'overflow': '',
            'height': 'auto'
        });
    }

    return {
        fn_layoutClose: function(){
            var objDim = isDim ? $('.dim-layer') : $el;
            fn_layoutClose(objDim);
        }
    }

}




function fn_mobile_layerOpen(el, myScroll) {
    var handler = function(e){e.preventDefault();}
    var $el = $('#'+el);

    $el.fadeIn();
    $(document).find('html, body').css({
        'overflow': 'hidden'
    });

    $el.find('.btn_cancel').click(function () {
        $(document).find('html, body').css({
            'overflow': 'auto'
        });
        fn_mobile_layerClose(el);
        return false;
    });
    $el.find('.popup_cancel').click(function () {
        $(document).find('html, body').css({
            'overflow': 'auto'
        });
        fn_mobile_layerClose(el);
        return false;
    });

    $(window).on('touchmove', handler);

    var scroll;
    if(myScroll != undefined && myScroll != null && myScroll != ''){
        scroll = myScroll;
        for (i=1; i < arguments.length; i++) {
            //var myScroll = new iScroll(arguments[i],{snap:false,hScroll:true,vScroll:true,hScrollbar:true, vScrollbar:true});
            scroll.refresh();

        }
    }


}

function fn_mobile_layerClose(el){
    var handler = function(e){e.preventDefault();}
    var $el = $('#'+el);
    $el.fadeOut();
    $(document).find('html, body').css({
        'overflow': 'auto'
    });
    $(window).off('touchmove');
}

function openPopup(url, width, height, target, top, left) {
    window.open(url, target, 'statusbar=no,menubar=no,scrollbars=yes,resizable=yes,top='+top+',left='+left+',width='+width+',height='+height);
}


var g_beforeUnload = '불러오는 중입니다';
var g_ajaxLoading = '처리 중입니다';
var g_loadingDiv = '<div id="viewLoading" style="display:none"><img src="/resources/images/webLoading.gif" width="160" height="160" alt="loding"/><br/><span id="g_loadingText"></span></div>';
function fn_setAjaxShowLoading() {
    if($('#viewLoading').html() == null) {
        $('body').append(g_loadingDiv);
    }

    $(document)
    .ajaxStart(function()
    {
        fn_showLoading(g_ajaxLoading);
    })
    .ajaxSuccess(function()
    {
        fn_hideLoading();
    })
    .ajaxError(function()
    {
        fn_hideLoading();
    });
//    .ajaxComplete(function()
//    {
//    	if(!isUrlChange) {
//    		fn_hideLoading();
//    	}
//    });




}

function fn_showLoading(param) {
    if($('#viewLoading').html() == null) {
        $('body').append(g_loadingDiv);
    }

    $('#g_loadingText').text(param);
    // 로딩이미지의 위치 및 크기조절
    $('#viewLoading').css('position', 'absolute');
    $('#viewLoading').css('left', $('body').offset().left);
    $('#viewLoading').css('top', $('body').offset().top);
    $('#viewLoading').css('width', $('body').width());
    $('#viewLoading').css('height', $('body').height());
    var pt = $(window).height() / 2;
    var pp = $(window).height() / 10;
    var ppt = pt - pp + $(window).scrollTop();
    $('#viewLoading').css('padding-top', ppt);
    $('#viewLoading').css('text-align', 'center');
    $('#viewLoading').css('background', '#FEFEFE');
    $('#viewLoading').css('opacity', '0.7');
    $('#viewLoading').css('z-index', '50000');

    $('#viewLoading').show();
}

function fn_hideLoading() {
    if($('#viewLoading').html() == null) {
        $('body').append(g_loadingDiv);
    }
    $('#viewLoading').hide();
}

function fn_setATagClickLoading() {
    if($('#viewLoading').html() == null) {
        $('body').append(g_loadingDiv);
    }
    $('a').click(function(){
        alert("a click");

        fn_showLoading(g_beforeUnload);
        return true;
    });
}

function fn_setFormSubmitLoading() {
    if($('#viewLoading').html() == null) {
        $('body').append(g_loadingDiv);
    }
    $('form').submit(function() {
        fn_showLoading(g_ajaxLoading);
        return true;
    });
}

function fn_setBeforeUnload() {
    window.onbeforeunload = function(e) {



        if (isLcationTel) { // 전화번호 Link 면 미실행
            isLcationTel = false;
            return;
        }
        fn_showLoading(g_beforeUnload);
        isUrlChange = true;
    };
}

function fn_setPositionLoading(){
    $('#viewLoading').css('position', 'absolute');
    $('#viewLoading').css('left', $('body').offset().left);
    $('#viewLoading').css('top', $('body').offset().top);
    $('#viewLoading').css('width', $('body').width());
    $('#viewLoading').css('height', $('body').height());
    var pt = $(window).height() / 2;
    var pp = $(window).height() / 10;
    var ppt = pt - pp + $(window).scrollTop();
    $('#viewLoading').css('padding-top', ppt);
    $('#viewLoading').css('text-align', 'center');
    $('#viewLoading').css('background', '#FEFEFE');
    $('#viewLoading').css('opacity', '0.7');
    $('#viewLoading').css('z-index', '50000');
}

function fn_stringToHtml (str) {
    if(str == null)
        return null;

    var returnStr = str;
    returnStr = returnStr.replace(/\n/gi, "");
    returnStr = returnStr.replace(/<br>/gi, "\n");
    returnStr = returnStr.replace(/&gt;/gi, ">");
    returnStr = returnStr.replace(/&lt;/gi, "<");
    returnStr = returnStr.replace(/&quot;/gi, "\"");
    returnStr = returnStr.replace(/&nbsp;/gi, " ");
    returnStr = returnStr.replace(/&amp;/gi, "&");
    returnStr = returnStr.replace(/\"/gi, "&#34;");
    returnStr = returnStr.replace(/&#34;/gi, "\"");

    return returnStr;
}

function locationTel(tel) {
    isLcationTel = true;
    location.href = tel;
}

//숫자 백단위 콤바 표시
function fn_numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//메인 제휴 슬라이드 배너 포커스
$(document).on('focusin', '.partnetship .owl-item.cloned:first() a', function() {
    $('.partnetship .owl-item.active').eq(0).find('a').focus();
});

$(document).on('focusin', '.partnetship .owl-item.cloned:eq(3) a', function() {
    var $stopButton = $('.lifestyle_banner_index button').last();

    if ($stopButton.is(':hidden')) {
        $stopButton.prev().focus();
    } else {
        $stopButton.focus();
    }
});



