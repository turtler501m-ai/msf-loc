var isLcationTel = false;

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
