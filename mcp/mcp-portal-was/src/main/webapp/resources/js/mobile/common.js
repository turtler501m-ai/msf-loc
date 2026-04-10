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

function fn_layerPop(el,_width,_height,$baseDom) {


    $('.dim-layer .pop-layer_red .popup_content .summary .icon ul li:nth-child(1) img').attr({src:"/resources/images_bak/front/fee_pop_icon_01.png"});
    $('.dim-layer .pop-layer_red .popup_content .summary .icon ul li:nth-child(2) img').attr({src:"/resources/images_bak/front/fee_pop_icon_02.png"});
    $('.dim-layer .pop-layer_red .popup_content .summary .icon ul li:nth-child(3) img').attr({src:"/resources/images_bak/front/fee_pop_icon_03.png"});
    $('.dim-layer .pop-layer_red .popup_content .summary .icon ul li:nth-child(4) img').attr({src:"/resources/images_bak/front/fee_pop_icon_04.png"});


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
    $('.popup_content').css({maxHeight:$(window).height()-200});

    $('.dim-layer').children().each(function(index, value){
        $('#'+value.id).hide();
    });

    var $el = $('#'+el);        //레이어의 id를 $el 변수에 저장
    var isDim = $('.dim-layer').children().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수
    $el.show();

    isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

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

    $(document).find('html, body').css({
        'overflow': 'hidden'
    });


    function fn_layoutClose(obj){
        obj.fadeOut();
        $(document).find('html, body').css({
            'overflow': 'auto',
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

function fn_hideLoading() {
	KTM.LoadingSpinner.hide();
}

function locationTel(tel) {
    isLcationTel = true;
    location.href = tel;
}

//숫자 백단위 콤바 표시
function fn_numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}



/*
var menuVer = new Date().getTime();

document.write('<script type="text/javascript" src="/resources/upload/js/dataMenuConfig.js?version=' + menuVer + '" charset="utf-8"></script>');

$(document).ready(function(){

 // LNB
	var conH = $('.n_lnb_con').height();
	$('.btn_lnb').click(function(){
		$('.n_lnb_con').animate({left:0});
		$('html, body').css('height',conH).css('overflow-y','hidden');
	});
	$('.n_lnb .btn_close').click(function(){
		$('.n_lnb_con').animate({left:'-100%'});
		$('html, body').css('height','auto').css('overflow-y','auto');
	});
	// LNB -  고객센터
	$('.n_lnb .btn_cust').click(function(){
		$('.n_lnb_cust_con').css('display','block');
	});
	$('.n_lnb_cust_con .btn_prev').click(function(){
		$('.n_lnb_cust_con').css('display','none');
	});

	$('._isSub').click(function(){
        var subClass= $(this).attr("subClass");

        if ($(this).parent().hasClass('icon_cust')) {
            $("."+subClass).show();
            $(this).parent().removeClass("icon_cust");
        } else {
            $("."+subClass).hide();
            $(this).parent().addClass("icon_cust");
        }
    });

    var gnbMenuCode = "";
    var subMenuCode = "";
    var thirdMenuCode = "";
    var $menuSubNav = null ;

    if ( typeof menuCode != "undefined" && menuCode.length >= 4)  {
        gnbMenuCode = menuCode.substring(0, 4) ;
        $('#gnb li[menuCode="'+gnbMenuCode+'"]').addClass("active");

        if (gnbMenuCode != "SM09") {
            $('#nav01').show();
            $('#nav02').hide();
            $("#nav01 ul li[menuCode="+gnbMenuCode+"]").addClass("active");
            $menuSubNav = $("#divMenuSub nav[menuCode="+gnbMenuCode+"]");
            $menuSubNav.show();

            if ( typeof menuCode != "undefined" && menuCode.length >= 6)  {
                subMenuCode = menuCode.substring(0, 6) ;
                $menuSubNav.find("li[menuCode="+subMenuCode+"]").addClass("active");
            }

        } else {

            $('#nav01').hide();
            $('#nav02').show();
            if ( typeof menuCode != "undefined" && menuCode.length >= 6)  {
                subMenuCode = menuCode.substring(0, 6) ;
                $("#nav02 ul li[menuCode="+subMenuCode+"]").addClass("active");

                $menuSubNav = $("#divMenuSub nav[menuCode="+subMenuCode+"]");
                $menuSubNav.show();


                if ( typeof menuCode != "undefined" && menuCode.length >= 8)  {
                    thirdMenuCode = menuCode.substring(0, 8) ;
                    $menuSubNav.find("li[menuCode="+thirdMenuCode+"]").addClass("active");
                }
            }
        }

        var $navOjb = $('.gnb nav ul').children(".active");

        if ($navOjb.length > 0 ) {
             $('.gnb nav').animate({scrollLeft:$('.gnb nav ul').children(".active").position().left},function(){

                 if ($('.snb nav ul').children(".active").length > 0) {
                     $('.snb nav').scrollLeft($('.snb nav ul').children(".active").position().left);
                 }

             });
        }

    }

});

*/