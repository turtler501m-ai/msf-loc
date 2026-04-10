$(document).ready(function($) {
    // ajax 실행 및 완료시 'Loading 이미지'의 동작을 컨트롤하자.
    fn_setAjaxShowLoading();


    // location.href 가 바뀌기 전에 동작
    fn_setBeforeUnload();

    $(window).resize(function(){
        fn_setPositionLoading();

        $('.popup_content').css({maxHeight:$(window).height()-200});
        $('.pop-layer').css({left:"50%"});
        $('.pop-layer').css({marginLeft:-$('.pop-layer:visible').width()/2});
        $('.pop-layer').css({top:"50%"});
        $('.pop-layer').css({marginTop:-$('.pop-layer:visible').height()/2});

    });





    $("a[href^='javascript:']").mousedown (function() {
        setTimeout(function(){
            fn_hideLoading();
        }, 500);
    });


    $("a[href^='javascript:']").keydown(function (key) {
        if (key.keyCode == 13) {
            setTimeout(function(){
                fn_hideLoading();
            }, 500);
        }
    });






    $(window).scroll(function(){
        fn_setPositionLoading();
    });

    if (navigator.userAgent.indexOf('MSIE') != -1)
        var detectIEregexp = /MSIE (\d+\.\d+);/; //test for MSIE x.x
    else // if no "MSIE" string in userAgent
        var detectIEregexp = /Trident.*rv[ :]*(\d+\.\d+)/; //test for rv:x.x or rv x.x where Trident string exists


    $('input, text').placeholder();
    /*$('input').iCheck({
        checkboxClass: 'icheckbox_square-red',
        radioClass: 'iradio_square-red',
    });*/
});

