var scrollPosY = "";
var nowUrl = "";
var beforSpaUrl = "";
var mapContainer = "";
var map = "";
var mapOptions = "";
/**페이지 이동 관련 */
function openPage(type,url,data, size){
    switch(type){

        case 'pullPopup':
            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'text',
                data: data,
                async: false,
                success: function(resp) {
                    $('#pullModalContents').html(resp);
                    KTM.initialize();
                }
            });
            //new KTM.Dialog(document.querySelector("#modalArs")).open();


            if(size != undefined){
                $('#modalArs').removeClass('c-modal--xlg');
                $('#modalArs').removeClass('c-modal--md');
                $('#modalArs').removeClass('c-modal--sm');
                $('#modalArs').removeClass('c-modal--mstorage');
                if(size == '0'){
                    $('#modalArs').addClass('c-modal--xlg');
                }else if(size == '1'){
                    $('#modalArs').addClass('c-modal--md');
                }else if(size == '2'){
                    $('#modalArs').addClass('c-modal--sm');
                }else{
                    $('#modalArs').addClass('c-modal--mstorage');
                }
            }else{
                $('#modalTerms').removeClass('c-modal--xlg');
                $('#modalTerms').removeClass('c-modal--md');
                $('#modalTerms').removeClass('c-modal--sm');
                $('#modalTerms').removeClass('c-modal--mstorage');
                $('#modalTerms').addClass('c-modal--md');
            }

            var el = document.querySelector("#modalArs");
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            modal.redraw();
            break;
        case 'pullPopupNoOpen': //표현 하지 않음 기존에 js사용하기 위해
            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'text',
                data: data,
                async: false,
                success: function(resp) {
                    $('#pullModalContents').html(resp);
                    KTM.initialize();
                }
            });

            break;
        case 'pullPopup2nd':
            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'text',
                data: data,
                async: false,
                success: function(resp) {
                    $('#pullModal2ndContents').html(resp);
                    KTM.initialize();
                }
            });
            var el = document.querySelector("#modalArs2nd");
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            modal.redraw();
            break;
        case 'pullPopupByPost':
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'text',
                data: data,
                async: false,
                success: function(resp) {
                    $('#pullModalContents').html(resp);
                    KTM.initialize();
                }
            });

            if(size){
                $('#modalArs').removeClass('c-modal--xlg');
                $('#modalArs').removeClass('c-modal--md');
                $('#modalArs').removeClass('c-modal--sm');
                $('#modalArs').removeClass('c-modal--mstorage');
                if(size == '0'){
                    $('#modalArs').addClass('c-modal--xlg');
                }else if(size == '1'){
                    $('#modalArs').addClass('c-modal--md');
                }else if(size == '2'){
                    $('#modalArs').addClass('c-modal--sm');
                }else{
                    $('#modalArs').addClass('c-modal--mstorage');
                }
            }

            var el = document.querySelector("#modalArs");
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            modal.redraw();
            break;
        case 'link':
            //url+='?utm_medium=&utm_source=&utm_campaign=';
            window.location.href=url;
            break;
        case 'outLink' :
            //var winPop = window.open(url);
            //winPop.focus();
            var targetName = 'certPop' + Date.now();
            var winPop = window.open('', targetName);
            var outLinkForm = $('<form></form>');
            outLinkForm.attr('target', targetName);
            outLinkForm.attr('method','post');
            outLinkForm.attr('action',url);
            outLinkForm.appendTo('body');
            outLinkForm.submit();
            break;
        case 'outLink2' :
            if (data.top == undefined ){
                data.top = 100;
            }

            if (data.left == undefined ){
                data.left = 100;
            }

            //window.open(url, "", "width=" + data.width + ", height=" + data.height+"top=" + data.top+", left=" + data.left+"");
            var targetName = 'certPop' + Date.now();
            var winPop = window.open('', targetName, "width=" + data.width + ", height=" + data.height+"top=" + data.top+", left=" + data.left+"");
            var outLink2Form = $('<form></form>');
            outLink2Form.attr('target', targetName);
            outLink2Form.attr('method','post');
            outLink2Form.attr('action',url);
            outLink2Form.appendTo('body');
            outLink2Form.submit();
            break;
        case 'outLink3' :
            if (data.top == undefined ){
                data.top = 100;
            }

            if (data.left == undefined ){
                data.left = 100;
            }

            var winPop = window.open(url, '', "width=" + data.width + ", height=" + data.height+"top=" + data.top+", left=" + data.left+"");
            break;
        case 'spaLink' :
            //KTM.LoadingSpinner.show();
            if($('#contentLayer').length){
                if(beforSpaUrl == ""){
                    beforSpaUrl = window.location.href;
                }
                window.history.pushState({
                    page: "spaViewLayer"}, "", url);
                $.ajax({
                    url: url,
                    type: 'GET',
                    dataType: 'text',
                    data: data,
                    async: false,
                    success: function(resp) {
                        var respHtml = $(resp).find('.ly-content').html();
                        $('#spaViewLayer').html(respHtml);
                        $('#spaViewLayer').find('.ly-page-sticky').css('display','none');
                        //KTM.LoadingSpinner.hide();
                        $('#contentLayer').css("display","none");
                        $('#spaViewLayer').css("display","block");
                        window.scrollTo(0, 0);
                    }
                });
            }else{
                openPage('link',url,'');
            };
            break;
        case 'termsPop' :

            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'json',
                data: data,
                async: false,
                success: function(data) {
                    $('#terms-title').html(data.dtlCdNm);
                    var inHtml = unescapeHtml(data.docContent);
                    $('#termsModalContents').html(inHtml);
                    KTM.initialize();
                }
            });

            if(size != undefined){
                $('#modalTerms').removeClass('c-modal--xlg');
                $('#modalTerms').removeClass('c-modal--md');
                $('#modalTerms').removeClass('c-modal--sm');
                $('#modalTerms').removeClass('c-modal--mstorage');
                if(size == '0'){
                    $('#modalTerms').addClass('c-modal--xlg');
                }else if(size == '1'){
                    $('#modalTerms').addClass('c-modal--md');
                }else if(size == '2'){
                    $('#modalTerms').addClass('c-modal--sm');
                }else{
                    $('#modalTerms').addClass('c-modal--mstorage');
                }
            }else{
                $('#modalTerms').removeClass('c-modal--xlg');
                $('#modalTerms').removeClass('c-modal--md');
                $('#modalTerms').removeClass('c-modal--sm');
                $('#modalTerms').removeClass('c-modal--mstorage');
                $('#modalTerms').addClass('c-modal--md');
            }

            var el = document.querySelector('#modalTerms');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            modal.redraw();
            break;

        case 'termsInfoPop' :
            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'json',
                data: data,
                async: false,
                success: function(data) {
                    $('#content-title').html(data.dtlCdNm);
                    var inHtml = unescapeHtml(data.docContent);
                    $('#contentmModalContents').html(inHtml);
                    KTM.initialize();
                }
            });
            var el = document.querySelector('#modalInfoTerms');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            modal.redraw();
            break;

        case 'eventPop' :

            var summaryModalEl = document.querySelector("#eventSummaryModal");
            if (summaryModalEl != null) {
                summaryModalEl.remove();
            }

            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'text',
                data: data,
                async: false,
                success: function(data) {
                    $("#main-content").append(data);
                    KTM.initialize();
                }
            });

            var el = document.querySelector("#eventPop");
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            modal.redraw();
            break;

        case 'multiPhonePop' :
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'text',
                data: data,
                async: false,
                success: function(dat) {
                    $("#main-content").after(dat);
                    KTM.initialize();
                }
            });
            var el = document.querySelector("#pw_confirm-dialog");
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            modal.redraw();
            break;

        case 'smsCertifyPop' :
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'text',
                data: data,
                async: false,
                success: function(dat) {
                    $("#main-content").after(dat);
                    KTM.initialize();
                }
            });
            var el = document.querySelector("#sms-dialog");
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();

            break;

        case 'snsSharePop' :
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'text',
                data: data,
                async: false,
                success: function(dat) {
                    $("#main-content").after(dat);
                    KTM.initialize();
                }
            });
            var el = document.querySelector("#sns-share_dialog");
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();

            break;


        case 'userPromotionPop' :
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'text',
                data: data,
                async: false,
                success: function(dat) {
                    $("#main-content").after(dat);
                    KTM.initialize();
                }
            });
            var el = document.querySelector("#userPromotionModal");
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();

            break;


        case 'dimRemovePopup':
            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'text',
                data: data,
                async: false,
                success: function(resp) {
                    $('#pullModalContents').html(resp);
                    KTM.initialize();
                }
            });
            //new KTM.Dialog(document.querySelector("#modalArs")).open();


            if(size != undefined){
                $('#modalArs').removeClass('c-modal--xlg');
                $('#modalArs').removeClass('c-modal--md');
                $('#modalArs').removeClass('c-modal--sm');
                $('#modalArs').removeClass('c-modal--mstorage');
                if(size == '0'){
                    $('#modalArs').addClass('c-modal--xlg');
                }else if(size == '1'){
                    $('#modalArs').addClass('c-modal--md');
                }else if(size == '2'){
                    $('#modalArs').addClass('c-modal--sm');
                }else{
                    $('#modalArs').addClass('c-modal--mstorage');
                }
            }else{
                $('#modalTerms').removeClass('c-modal--xlg');
                $('#modalTerms').removeClass('c-modal--md');
                $('#modalTerms').removeClass('c-modal--sm');
                $('#modalTerms').removeClass('c-modal--mstorage');
                $('#modalTerms').addClass('c-modal--md');
            }

            var el = document.querySelector("#modalArs");
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            modal.redraw();
            if($(".fadeIn").length == 2){
                $(".fadeIn").eq(1).remove();
                $(".c-modal").not(".c-modal--alert").removeClass("is-active");
            }
            break;

        case 'mktTermsPop' :

            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'json',
                data: data,
                async: false,
                success: function(data) {
                    $('#mkt-terms-title').html(data.dtlCdNm);
                    var inHtml = unescapeHtml(data.docContent);
                    $('#mktTermsModalContents').html(inHtml);
                    KTM.initialize();
                }
            });

            if(size != undefined){
                $('#mktModalTerms').removeClass('c-modal--xlg');
                $('#mktModalTerms').removeClass('c-modal--md');
                $('#mktModalTerms').removeClass('c-modal--sm');
                $('#mktModalTerms').removeClass('c-modal--mstorage');
                if(size == '0'){
                    $('#mktModalTerms').addClass('c-modal--xlg');
                }else if(size == '1'){
                    $('#mktModalTerms').addClass('c-modal--md');
                }else if(size == '2'){
                    $('#mktModalTerms').addClass('c-modal--sm');
                }else{
                    $('#mktModalTerms').addClass('c-modal--mstorage');
                }
            }else{
                $('#mktModalTerms').removeClass('c-modal--xlg');
                $('#mktModalTerms').removeClass('c-modal--md');
                $('#mktModalTerms').removeClass('c-modal--sm');
                $('#mktModalTerms').removeClass('c-modal--mstorage');
                $('#mktModalTerms').addClass('c-modal--md');
            }

            var el = document.querySelector('#mktModalTerms');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            modal.redraw();
            break;
    }
    $(document).ready(function (){
        KTM.initialize();
    });
}

function unescapeHtml(str) {

    if (str == null) {
        return "";

    }
    return str.replace(/&amp;/g, '&')
        .replace(/&lt;/g, '<')
        .replace(/&gt;/g, '>')
        .replace(/&quot;/g, '"')
        .replace(/&#039;/g, "'")
        .replace(/&#39;/g, "'");

}
function saveScroll() {
    scrollPosY = window.scrollY;
}

function restoreScroll() {
    window.scrollTo(0, scrollPosY);
}



function tempAxios(callback) {
    setTimeout(callback, 100);
}

function closeView(){
    if(beforSpaUrl == window.location.href){
        $('#contentLayer').css("display","block");
        $('#spaViewLayer').css("display","none");
        restoreScroll();
    }else{
        $.ajax({
            url: window.location.href,
            type: 'GET',
            dataType: 'text',
            async: false,
            success: function(resp) {
                var respHtml = $(resp).find('.ly-content').html();
                $('#spaViewLayer').html(respHtml);
                $('#spaViewLayer').find('.ly-page-sticky').css('display','none');
                $('#contentLayer').css("display","none");
                $('#spaViewLayer').css("display","block");
                window.scrollTo(0, 0);
            }
        });
    }
}

function showList(url) {
    if($('#contentLayer').length){
        //$('#contentLayer').css("display","block");
        //$('#spaViewLayer').css("display","none");
        //restoreScroll();
        history.back(-1);
    }else{
        openPage('link',url,'');
    }
}





function getCookie(name){
    var nameOfCookie = name + "=";
    var x = 0;
    while (x <= document.cookie.length){
        var y = (x + nameOfCookie.length);
        if (document.cookie.substring(x, y) == nameOfCookie){
            if ((endOfCookie = document.cookie.indexOf(";", y)) == -1){
                endOfCookie = document.cookie.length;
            }
            return unescape (document.cookie.substring(y, endOfCookie));
        }
        x = document.cookie.indexOf (" ", x) + 1;
        if (x == 0) break;
    }
    return "";
}


/* 지도 관련 */
function mapInit(latit, ingit){
    mapContainer = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    mapOptions = { //지도를 생성할 때 필요한 기본 옵션
        center : new kakao.maps.LatLng(latit, ingit), //지도의 중심좌표.
        level : 3//지도의 레벨(확대, 축소 정도)
    };
    map = new kakao.maps.Map(mapContainer, mapOptions); //지도 생성 및 객체 리턴

    return map;
}

function paramValid(param){
    if(param == null || typeof param == 'undefined' || param == '' ){
        return false;
    }
    return true;
}

function insertBannAccess(bannSeq, bannCtg) {

    if(paramValid(bannSeq) == false){
        return;
    }

    if(paramValid(bannCtg) == false){
        return;
    }

    var varData = ajaxCommon.getSerializedData({
        bannSeq:bannSeq,
        bannCtg:bannCtg
    });

    ajaxCommon.getItemNoLoading({
            id:'insertBannAccess'
            ,cache:false
            ,url:'/insertBannAccessAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            //alert("result:"+jsonObj);
        });
}

function goMain() {
    var curUri = window.location.pathname;
    var toMain = "/m/main.do";
    if(curUri.indexOf("/m/") == -1) {
        toMain = "/main.do";
    }
    window.location.href = toMain;
}

function goPcView(){
    var curUri = window.location.pathname;
    if(curUri.indexOf("/m/") != -1) {
        curUri = curUri.replace("/m/","/");
    }
    window.location.href = curUri+"?PCTURN=P";
}

function familyOpen(){
    var url = $("#select001 option:selected").val();
    if(url.indexOf("http") != -1) {
        var win = window.open(url);
        win.onbeforeunload = function(){
        }
    }
}

//세자리수 콤마찍기
function replaceToStr(number){
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}




/*
$(document).ajaxStart(function() {
    KTM.LoadingSpinner.show();
}).ajaxSend(function() {
    KTM.LoadingSpinner.show();
}).ajaxSuccess(function() {
    KTM.LoadingSpinner.hide();
}).ajaxError(function() {
    KTM.LoadingSpinner.hide();
}).ajaxComplete(function(){
    KTM.LoadingSpinner.hide();
});

$('a').click(function(){
    alert("a click");

    KTM.LoadingSpinner.show();
    return true;
});
*/