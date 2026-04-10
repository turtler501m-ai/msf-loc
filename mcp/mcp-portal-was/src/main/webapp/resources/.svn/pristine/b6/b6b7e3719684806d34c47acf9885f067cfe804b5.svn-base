$(document).ready(function () {

    window.myMainBannerSwipe = new Swipe(document.getElementById('main_banner_slider'), {
        startSlide: 0,
        speed: 800,
        auto: 5000,
        continuous: true,
        disableScroll: false,
        stopPropagation: false,
        callback: function (index, elem) {
            var pos = myMainBannerSwipe.getPos() + 1;
            $(".main_banner_index").removeClass("active");
            $(".main_banner_index:nth-child(" + pos + ")").addClass("active");
        },
        transitionEnd: function (index, elem) {
            // 현재 슬라이드만 탭이동이 되도록 함
            $('#main_banner_img div a').attr('tabindex', '-1');
            $('#main_banner_img div').eq(index).find('a').removeAttr('tabindex');
            // //현재 슬라이더만 탭이동이 되도록 함
        }
    });


    $('.main_banner_index').click(function () {
        var id = $(this).attr("id");
        var index = id.split("_")[1];
        index = parseInt(index) - 1;
        myMainBannerSwipe.slide(index, 400);
        $('#mainBannerStop').css("display","none");
        $('#mainBannerStart').css("display","");
        myMainBannerSwipe.stop();
    });

    $('#mainBannerStop').click(function () {
        $('#mainBannerStop').css("display","none");
        $('#mainBannerStart').css("display","").focus();
        myMainBannerSwipe.stop();
    });

    $('#mainBannerStart').click(function () {
        $('#mainBannerStart').css("display","none");
        $('#mainBannerStop').css("display","").focus();
        myMainBannerSwipe.begin();
    });

    $('#main_banner_img div a').focusin(function () {
    	$('#mainBannerStop').css("display","none");
        $('#mainBannerStart').css("display","");
        myMainBannerSwipe.stop();
    });

    window.myBannerSwipe = new Swipe(document.getElementById('banner_slider'), {
        startSlide: 0,
        speed: 400,
        auto: 5000,
        continuous: true,
        disableScroll: false,
        stopPropagation: false,
        callback: function (index, elem) {
            var pos = myBannerSwipe.getPos() + 1;

            // 슬라이드가 2개일 경우
            var maxIdx = $('.banner_index .btn_index').length;

            if (maxIdx == 2) {
                if (pos == 3) {
                    pos = 1;
                } else if (pos == 4) {
                    pos = 2;
                }
            }
            // //슬라이드가 2개일 경우

            $(".banner_index .btn_index").removeClass("active");
            $(".banner_index .btn_index:nth-child(" + pos + ")").addClass("active");
        },
        transitionEnd: function (index, elem) {

            // 현재 슬라이드만 탭이동이 되도록 함
            $('.swipe-wrap .clri a').attr('tabindex', '-1');
            $('.swipe-wrap .clri').eq(index).find('a').removeAttr('tabindex');
            // //현재 슬라이더만 탭이동이 되도록 함
        }
    });

    // 슬라이드 탭이동 기능 초기화
    $('.swipe-wrap .clri a').attr('tabindex', '-1');
    $('.swipe-wrap .clri').eq(0).find('a').removeAttr('tabindex');
    // //슬라이드 탭이동 기능 초기화

    $('.btn_index').click(function () {
    	$('.btn_stop').css("display","none");
    	$('.btn_start').css("display","");
        var id = $(this).attr("id");
        var index = id.split("_")[1];
        index = parseInt(index) - 1;
        myBannerSwipe.slide(index, 400);
    });

    /*$('.btn_stop').click(function () {
    	$('.btn_stop').css("display","none");
    	$('.btn_start').css("display","");
        myBannerSwipe.stop();
    });

    $('.btn_start').click(function () {
    	$('.btn_start').css("display","none");
    	$('.btn_stop').css("display","");
        myBannerSwipe.begin();
    });*/

    $('.arrow_right').click(function () {
    	$('.btn_stop').css("display","none");
    	$('.btn_start').css("display","");
    	myBannerSwipe.next();
    });

    $('.arrow_left').click(function () {
    	$('.btn_stop').css("display","none");
    	$('.btn_start').css("display","");
    	myBannerSwipe.prev();
    });

    $('.swipe-wrap .clri a').focusin(function () {
        var idx = $(this).parent().attr("data-index");

        $('.btn_main_index').eq(idx).trigger('click');
        $('.btn_main_start').trigger('click');
    });

    //$('#snbSubInfo').height($(window).height()-100);


});