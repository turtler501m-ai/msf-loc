
var interval ;
var onlyMine = false;
var key = "";
var reviewNo = 100;
var searchVal = '';
var day = new Date();

$(document).ready(function(){
    var pageNo = $("#pageNo").val();
    getBannerList();
    if(!pageNo){pageNo = 1;}


    // 이벤트 공유 링크 타고 온경우
    if($("#proMoYn").val() == "Y"){
        $("#selEventCd").val($("#proMoNum").val());
    }

    if($("#eventDetailFalg").val() != 'Y'){
        //url 초기화
        history.replaceState({}, null, location.pathname);
    }

    setTimeout(function(){
        if($("#accordionReview").length == 1){
            initreViewData(pageNo);
        }
    }, 500);


    // 약관 동의 전체선택 or 전체해제
    $('#btnStplAllCheck').on('click', function (){

         if($(priAdAgree).prop('checked')){
             if($(this).prop('checked')){
                    $('input[name=terms]').prop('checked', 'checked');
                }else{
                    $('input[name=terms]').removeProp('checked');
                }
         }else{
             if($(this).prop('checked')){
                 $('input[name=terms]').prop('checked', 'checked');
                 MCP.alert("귀하가 " +day.format("yyyy-MM-dd")+ " 요청하신 수신동의가<br> 정상적으로 처리 되었습니다.<br>수신동의 철회(무료) : ARS 080-870-9812");
             }else{
                 $('input[name=terms]').removeProp('checked');
             }
         }

        setChkbox();
    });



    // 조회 버튼 선택
// $(document).on("click", "#selectEventReview", function(){
// $("#reviewBoard").html("");
// $("#pageNo").val(1);
// initreViewData(1);
// });

  // 이벤트 리스트 선택
    $(document).on("change", "#selEventCd", function(){
        $("#reviewBoard").html("");
        $("#pageNo").val(1);
        initreViewData(1);
    });


    // 대표 회선 선택
    $("#selectA").on("change", function() {
        initreViewData(1);
    });

    // 내 게시글만 보기 체크박스 선택
    $("#chkMyReview").on("change", function(){
        if($("#userlogin").val() == 'N'){
            if($("#successFlag").val() == 'N'){
                if($('#chkMyReview').is(":checked")){
                     $("#eventFlag").val("myContent");
                     $('#chkMyReview').attr('checked', false);
                     selfIdent();
                }else{
                    onlyMine = false;
                    initreViewData(1);
                }

            }else{
                $("#eventFlag").val("myContent");
                $("#reviewBoard").html("");
                $("#pageNo").val(1);
                searchVal = '';
                if($(this).is(":checked")){
                    filter.forEach(el => el.checked = false);
                    onlyMine = true;

                    initreViewData(1);
                }else{
                    onlyMine = false;
                    initreViewData(1);
                }
            }
       }else{
           $("#eventFlag").val("myContent");
           $("#reviewBoard").html("");
           $("#pageNo").val(1);
           searchVal = '';
           if($(this).is(":checked")){
               filter.forEach(el => el.checked = false);
               onlyMine = true;

               initreViewData(1);
           }else{
               onlyMine = false;
               initreViewData(1);
           }
       }
    });

    // 더보기
    $("#moreBtn").click(function(){
        var reViewCurrent = Number($(".review_fold").length);
        var reViewTotal = Number($("#reViewTotal").text());
        if(reViewCurrent >= reViewTotal){
            alert("마지막 페이지 입니다.");
            return false;
        } else {
            var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),"1"));
            initreViewData(pageNo+1);
            $("#pageNo").val(pageNo+1);
            $(window).scrollTop($("#moreBtn").offset().top-800);
        }
    });

    $(document).on("click", ".c-paging a", function(){

        if($(this).attr("pageNo")){
            window.scrollTo(0,800);
            initreViewData($(this).attr("pageNo"), searchVal);
        }
    });

    // 내 게시글 보기 제어
    var filter = document.getElementsByName("reviewFilter");
    filter.forEach(el => el.addEventListener("click", function (data) {
        var forCheck = data.target.checked;
        filter.forEach(el => el.checked = false);
        $('#chkMyReview').attr('checked', false);
        data.target.checked = forCheck;

        if (forCheck) {
            searchVal = data.target.id;
            onlyMine = false;
            initreViewData(1, data.target.id);
        } else {
            searchVal = '';
            onlyMine = false;
            initreViewData(1);
        }
    }))
});

// 이벤트 참여 리스트 로딩
var initreViewData = function(pageNo, targetId){
    var promotionCode = "";
    var dupInfo = "";
    var mobileNo = "";

    if($("#eventDetailFalg").val() == 'Y'){
        promotionCode = $("#selEventVal").val();
    }else{
        promotionCode = $("#selEventCd").val();
    }

    if(onlyMine == true){
        dupInfo = $("#niceDupInfo").val();
        if($("#niceMobileNo").val() == null || $("#niceMobileNo").val() == ''){
            mobileNo = $("#selectA").val();
        }else{
            mobileNo = $("#niceMobileNo").val();
        }
    }else{
        dupInfo = "";
        mobileNo = "";
    }

    var varData = ajaxCommon.getSerializedData({
        pageNo : pageNo
        ,promotionCode : promotionCode
        , onlyMine : onlyMine
        , searchCategory: targetId
        , dupInfo : dupInfo
        , mobileNo : mobileNo
    });

    ajaxCommon.getItem({
        id:'eventJoinDataAjax'
        ,cache:false
        ,url:'/event/eventJoinDataAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    },function(jsonObj){

        var total = jsonObj.total;
        var eventJoinDtoList = jsonObj.eventJoinDtoList;
        var conNumList = jsonObj.conNumList;
        var isLogin = jsonObj.isLogin;
        var eventTotal = jsonObj.eventTotal;
        var eventList = jsonObj.eventList;
        var shareTotal = jsonObj.shareTotal;
        var selectHtml = "";
        var sessNiceRes= jsonObj.sessNiceRes;

        ajaxCommon.setPortalPaging($("#paging"), jsonObj.pageInfoBean);
        $("#selEventCd").html("");
       /*
         * if((!eventList || eventList.length == 0)&&onlyMine){ selectHtml += "<option
         * value=''>전체보기(0)</option>"; }else{ selectHtml += "<option
         * value=''>전체보기(" + (eventTotal*1).toLocaleString("ko-KR") + ")</option>"; }
         */
        for(var i = 0; i < eventList.length; i ++){
            // if (eventList[i].expnsnStrVal1 == 'Y') {
                if((!eventList || eventList.length == 0)&&onlyMine){
                    selectHtml += "<option value='" + eventList[i].dtlCd + "'>" + eventList[i].dtlCdNm + "(0)</option>";
                }else{
                    selectHtml += "<option value='" + eventList[i].dtlCd + "'>" + eventList[i].dtlCdNm + "(" + (eventList[i].eventCdCtn*1).toLocaleString("ko-KR") +")</option>";
                }
            // }
        }
        $("#selEventCd").append(selectHtml);
        $("#commentCnt").text('댓글 ' + total +  ' · 공유 ' + Number(shareTotal));
        viewDesc(total,eventJoinDtoList, conNumList, isLogin,eventList,promotionCode);
        $("#selEventCd option[value='" + promotionCode + "']").prop("selected", true);

        scrollMove();
    });
}


var viewDesc = function(total,eventJoinDtoList, conNumList, isLogin,eventList,promoNum){
    $("#nodata").remove();
    $("#reviewBoard").html('');


    const promoNumData = eventList.find(v => v.dtlCd === promoNum);

    var htmlArea = "";
    if((!eventJoinDtoList || eventJoinDtoList.length == 0)&& onlyMine){
        $("#moreBtn").hide();
        $("#recommendInfo").hide();
        htmlArea += "<div class='c-nodata' id='nodata'>" +
            "<i class='c-icon c-icon--nodata' aria-hidden='true'></i>" +
            "<p class='c-nodata__text'>작성된 댓글이 없습니다.</p>" +
            "</div>";
        $(".c-row--lg").append(htmlArea);

        $("#paging").hide();
        // MCP.alert("조회된 결과가 없습니다.");

    }else{

        if(Number(promoNum) >= 1){

            // if(eventList[Number(promoNum)-1].expnsnStrVal1 == 'Y'){
            if(promoNumData.expnsnStrVal1 == 'Y'){
                $('#formYn').show();
            }else{
                $('#formYn').hide();
            }
        }else{
            $('#formYn').show();
        }

        if(eventJoinDtoList !=null && eventJoinDtoList.length>0){
            var mkRegNm = "";
            var userDivision = "";
            var promotionCode = "";
            var sysRdateDd = "";
            var reviewContent = "";
            var prizeYn = ""; // 추천,비추천
            var contractNum = "";
            var requestKey ="";
            var dtlCdNm ="";
            var userId ="";
            var mobileNo ="";
            //이벤트 참여 html 삽입은 3개만 노출
            if($("#eventDetailFalg").val() == 'Y'){

            if (eventJoinDtoList.length > 3) {
                  eventJoinDtoList.length = 3;
            }
                for( var i=0; i<eventJoinDtoList.length; i++ ){

                    mkRegNm = eventJoinDtoList[i].mkRegNm;
                    userDivision = eventJoinDtoList[i].userDivision;
                    promotionCode = eventJoinDtoList[i].promotionCode;
                    sysRdateDd = eventJoinDtoList[i].sysRdt;
                    reviewContent = unescapeHtml(eventJoinDtoList[i].reviewContent);
                    prizeYn = eventJoinDtoList[i].prizeYn;
                    contractNum = eventJoinDtoList[i].contractNum;
                    requestKey = eventJoinDtoList[i].reviewPromotionSeq;
                    dtlCdNm = eventJoinDtoList[i].dtlCdNm;
                    userId = eventJoinDtoList[i].userId;
                    mobileNo = eventJoinDtoList[i].mobileNo;
                    upGrpCd = eventJoinDtoList[i].upGrpCd;

                    htmlArea += "<li class='c-accordion__item'>";
                    if (prizeYn < 4 && prizeYn != '') {
                        htmlArea += '<button class="runtime-data-insert c-accordion__button review-accordion is-active" id="acc_header_c'+reviewNo+'" type="button" key="' + requestKey + '" aria-expanded="true" aria-controls="acc_content_c'+reviewNo+'">';
                    } else {
                        htmlArea += '<button class="runtime-data-insert c-accordion__button review-accordion" id="acc_header_c'+reviewNo+'" type="button" key="' + requestKey + '" aria-expanded="false" aria-controls="acc_content_c'+reviewNo+'">';
                    }
                    htmlArea += '<span class="c-hidden">'  + dtlCdNm+ ' 전체보기</span>'
                    htmlArea += '</button>';
                    htmlArea += "<div class='c-accordion__head'>";
                    htmlArea += "<div class='review-wrap'>";
                    htmlArea += '<div class="review__label-wrap" id="acc_label_c'+reviewNo+'">';
                    if(prizeYn =="Y"){
                        htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                    }
                    htmlArea += '</div>'
                    htmlArea += "<strong class='review__title--type2'>" + dtlCdNm+"</strong>";
                    htmlArea += '<span class="c-hidden">' + dtlCdNm+ ' 전체보기</span>'
                    htmlArea += '</div>'

                    htmlArea += '<ul class="review__info">';
                    htmlArea += '<li class="review__info__item">';
                    htmlArea += '<span class="sr-only">작성자</span>'+ mkRegNm ;
                    htmlArea += '</li>';
                    htmlArea += '<li class="review__info__item">';
                    htmlArea += '<span class="sr-only">작성일</span>' + sysRdateDd;
                    htmlArea += '</li>';

                    htmlArea += '</ul>';
                    htmlArea += '</div>';
                    // 펼치기
                    if (prizeYn < 4 && prizeYn != '') {
                        htmlArea += '<div class="c-accordion__panel expand expanded" id="acc_content_c'+reviewNo+'" aria-labelledby="acc_header_c'+reviewNo+'">';
                    } else {
                        htmlArea += '<div class="c-accordion__panel expand" id="acc_content_c'+reviewNo+'" aria-labelledby="acc_header_c'+reviewNo+'">';
                    }

                    htmlArea += '<div class="c-accordion__inside">';
                    htmlArea += '<div>';
                    htmlArea += '<div class="review__content">';

                    if(upGrpCd == 'N'){
                        if(mobileNo == $("#selectA").val() || mobileNo == $("#niceMobileNo").val()){
                             htmlArea += '<pre><p class="review__text">' + reviewContent + '</p></pre>';
                        }else{
                            htmlArea += '<pre><p class="review__text"><i class="c-icon c-icon--lock-type2" aria-hidden="true"></i>비공개 이벤트입니다</p></pre>';
                        }
                    }else{
                        htmlArea += '<pre><p class="review__text">' + reviewContent + '</p></pre>';
                    }

                    htmlArea += '</div>';
                    htmlArea += '</div>';
                    htmlArea += '</div>';
                    htmlArea += '</div>';
                    htmlArea += '</li>';

                    reviewNo ++;
                }
            }else{
                for( var i=0; i<eventJoinDtoList.length; i++ ){

                    mkRegNm = eventJoinDtoList[i].mkRegNm;
                    userDivision = eventJoinDtoList[i].userDivision;
                    promotionCode = eventJoinDtoList[i].promotionCode;
                    sysRdateDd = eventJoinDtoList[i].sysRdt;
                    reviewContent = unescapeHtml(eventJoinDtoList[i].reviewContent);
                    prizeYn = eventJoinDtoList[i].prizeYn;
                    contractNum = eventJoinDtoList[i].contractNum;
                    requestKey = eventJoinDtoList[i].reviewPromotionSeq;
                    dtlCdNm = eventJoinDtoList[i].dtlCdNm;
                    userId = eventJoinDtoList[i].userId;
                    mobileNo = eventJoinDtoList[i].mobileNo;
                    upGrpCd = eventJoinDtoList[i].upGrpCd;

                    htmlArea += "<li class='c-accordion__item'>";
                    if (prizeYn < 4 && prizeYn != '') {
                        htmlArea += '<button class="runtime-data-insert c-accordion__button review-accordion is-active" id="acc_header_c'+reviewNo+'" type="button" key="' + requestKey + '" aria-expanded="true" aria-controls="acc_content_c'+reviewNo+'">';
                    } else {
                        htmlArea += '<button class="runtime-data-insert c-accordion__button review-accordion" id="acc_header_c'+reviewNo+'" type="button" key="' + requestKey + '" aria-expanded="false" aria-controls="acc_content_c'+reviewNo+'">';
                    }
                    htmlArea += '<span class="c-hidden">'  + dtlCdNm+ ' 전체보기</span>'
                    htmlArea += '</button>';
                    htmlArea += "<div class='c-accordion__head'>";
                    htmlArea += "<div class='review-wrap'>";
                    htmlArea += '<div class="review__label-wrap" id="acc_label_c'+reviewNo+'">';
                    if(prizeYn =="Y"){
                        htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                    }
                    htmlArea += '</div>'
                    htmlArea += "<strong class='review__title--type2'>" + dtlCdNm+"</strong>";
                    htmlArea += '<span class="c-hidden">' + dtlCdNm+ ' 전체보기</span>'
                    htmlArea += '</div>'

                    htmlArea += '<ul class="review__info">';
                    htmlArea += '<li class="review__info__item">';
                    htmlArea += '<span class="sr-only">작성자</span>'+ mkRegNm ;
                    htmlArea += '</li>';
                    htmlArea += '<li class="review__info__item">';
                    htmlArea += '<span class="sr-only">작성일</span>' + sysRdateDd;
                    htmlArea += '</li>';

                    if($("#userlogin").val() == 'Y'){
                        if(mobileNo == $("#selectA").val() || mobileNo == $("#niceMobileNo").val()){
                            htmlArea += '<li class="review__info__item">';
                            htmlArea += '<button class="c-button">';
                            htmlArea += '<span class="sr-only">삭제</span>';
                            htmlArea += '<i class="c-icon c-icon--trash delete-button-in" aria-hidden="true" id="' + requestKey + '"></i>';
                            htmlArea += '</button>';
                            htmlArea += '</li>';
                        }
                    }else{
                        htmlArea += '<li class="review__info__item">';
                        htmlArea += '<button class="c-button">';
                        htmlArea += '<span class="sr-only">삭제</span>';
                        htmlArea += '<i class="c-icon c-icon--trash delete-button-in" aria-hidden="true" id="' + requestKey + '"></i>';
                        htmlArea += '</button>';
                        htmlArea += '</li>';
                    }


                    htmlArea += '</ul>';
                    htmlArea += '</div>';
                    // 펼치기
                    if (prizeYn < 4 && prizeYn != '') {
                        htmlArea += '<div class="c-accordion__panel expand expanded" id="acc_content_c'+reviewNo+'" aria-labelledby="acc_header_c'+reviewNo+'">';
                    } else {
                        htmlArea += '<div class="c-accordion__panel expand" id="acc_content_c'+reviewNo+'" aria-labelledby="acc_header_c'+reviewNo+'">';
                    }

                    htmlArea += '<div class="c-accordion__inside">';
                    htmlArea += '<div>';
                    htmlArea += '<div class="review__content">';

                    if(upGrpCd == 'N'){
                        if(mobileNo == $("#selectA").val() || mobileNo == $("#niceMobileNo").val()){
                             htmlArea += '<pre><p class="review__text">' + reviewContent + '</p></pre>';
                        }else{
                            htmlArea += '<pre><p class="review__text"><i class="c-icon c-icon--lock-type2" aria-hidden="true"></i>비공개 이벤트입니다</p></pre>';
                        }
                    }else{
                        htmlArea += '<pre><p class="review__text">' + reviewContent + '</p></pre>';
                    }

                    htmlArea += '</div>';
                    htmlArea += '</div>';
                    htmlArea += '</div>';
                    htmlArea += '</div>';
                    htmlArea += '</li>';

                    reviewNo ++;
                }
            }

            // append
            $("#reviewBoard").append(htmlArea);
            $("#selEventCd").show();
            $("#recommendInfo").show();
            if(total > 20){
                $("#paging").show();
            }else{
                $("#paging").hide();
            }
            window.KTM.initialize();

        } else {
            $("#moreBtn").hide();
            $("#recommendInfo").hide();
            htmlArea += "<div class='c-nodata' id='nodata'>" +
                "<i class='c-icon c-icon--nodata' aria-hidden='true'></i>" +
                "<p class='c-nodata__text'>작성된 댓글이 없습니다.</p>" +
                "</div>";

            if($("#eventDetailFalg").val() == 'Y'){
                $(".flexible .c-row--lg").append(htmlArea);;
            }else{
                $(".c-row--lg").append(htmlArea);
            }
            $("#paging").hide();
            // MCP.alert("조회된 결과가 없습니다.");
            sessionStorage.clear();
        }

    }

    // 더보기
    var reViewCurrent = $(".review__item").length;

    $("#reViewCurrent").text((reViewCurrent*1).toLocaleString('ko-KR')); // 현재
                                                                            // 노출
    $("#reViewTotal").text((total*1).toLocaleString('ko-KR'));
    if(reViewCurrent == total){$("#moreBtn").hide();}
    $(".imgTag").each(function(){
        $(this).error(function(){
            $(this).unbind("error");
            $(this).attr("src","/resources/images/requestReview/noimage.png");
        });
    });

    $(".delete-button-in").on("click", function(){
        key = $(this).attr("id");

        if(confirm("해당 댓글을 삭제 하시겠습니까?")){
             deleteReview(key);
        }

// KTM.Confirm("해당 댓글을 삭제 하시겠습니까?", function (){
// deleteReview(key);
// });

    });

    $("#subbody_loading").hide();
}

// 배너 호출 ajax
var getBannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'E004'
    });

    ajaxCommon.getItem({
            id : 'bannerListAjax'
            , cache : false
            , async : false
            , url : '/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){

            if(result.result && result.result.length > 0){
                for(var i = 0; i < result.result.length; i ++){
                    var html = "";
                    html += "<div class='swiper-slide' addr='" + result.result[i].linkUrlAdr + "' style='background-color:" + result.result[i].bgColor + "'>";
                    html += '<a class="swiper-banner__anchor" href="#" onclick="insertBannAccess(\'' + result.result[i].bannSeq + '\',\'' + result.result[i].bannCtg + '\')" target="';
                    if(result.result[i].linkTarget == 'Y'){
                        html += '_blank" title="새창열림">';
                    }else{
                        html += '_self">';
                    }

                    html += ' <img src="' + result.result[i].bannImg + '" alt="' + result.result[i].imgDesc + '">';
                    html += '</a>';
                    html += '</div>';
                    html += "<input type='hidden' value='" + result.result[i].bannSeq + "'>";

                    $("#swiperArea").append(html);

                }

                $(".swiper-slide").on("click", function(){
                    var parameterData = ajaxCommon.getSerializedData({
                        eventPopTitle : '댓글 이벤트'
                    });
                    $("#eventPop").remove();
                    openPage('eventPop', $(this).attr("addr"), parameterData)
                    $("#modalCardEventTitle").text("댓글 이벤트");
                    KTM.initialize();
                });
                if(result.result.length == 1){
                    $("#swiperReviewBanner").find("button").hide();
                }else{
                    $("#swiperReviewBanner").find("button").show();
                }

                KTM.swiperA11y("#swiperReviewBanner .swiper-container", {
                    width: 940,
                    spaceBetween: 20,
                    navigation: {
                        nextEl: "#swiperReviewBanner .swiper-button-next",
                        prevEl: "#swiperReviewBanner .swiper-button-prev",
                    },
                    observer: true,
                    observerParents: true,
                });

            }else{
                $("#swiperReviewBanner").hide();
            }
            //
        });
}

//댓글 작성하고 선물 받기 버튼
$("#validationChk").on("click", function(){

    if(!$("textarea[name='reviewSbst']").val() || $("textarea[name='reviewSbst']").val() == ''){
        MCP.alert("이벤트 댓글을 작성해 주세요.");
        return false;
    }

    if(!$("#btnStplAllCheck").is(':checked')){
        MCP.alert("이벤트 참여를 위해 미동의 항목에<br>동의 해 주시기 바랍니다.");
        return false;
    }

});


// 휴대폰 인증 버튼
$(document).on("click", "#certifyPopBut", function(){

    var promotionCode = "";

    if($("#eventDetailFalg").val() == 'Y'){
        promotionCode = $("#selEventVal").val();
    }else{
        promotionCode = $("#selEventCd").val();
    }


    if($("#successFlag").val() == 'N'){
        $("#eventFlag").val("save");
        selfIdent();
    }else{
        $("#eventFlag").val("save");



        var varData = ajaxCommon.getSerializedData({
            promotionCode : promotionCode,
            reviewContent : $("textarea[name='reviewSbst']").val(),
            name :  $("#niceName").val(),
            mobileNo : $("#niceMobileNo").val(),
            reqSeq : $("#niceReqSeq").val(),
            resSeq :    $("#niceResSeq").val(),
            useTelCode : $("#niceUseTelCode").val(),
            dupInfo : $("#niceDupInfo").val(),
            isLogin : $("#isLogin").val()
        });

        ajaxCommon.getItem({
            id : 'eventJoinRegAjax'
            , cache : false
            , async : false
            , url : '/event/eventJoinRegAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            var resultCode = result.RESULT_CODE;
            if(resultCode=="00000"){
                MCP.alert("등록되었습니다.", function(){
                    $("textarea[name='reviewSbst']").val("");
                    $("#chkAgree").prop("checked", false);
                    initreViewData(1);
                });
            }else if(resultCode=="0004"){
                 MCP.alert("등록에 실패했습니다.");
                 return false;
             }else if(resultCode=="0006"){
                MCP.alert("정지된 회선은 후기등록이 불가합니다.");
                return false;
            }else if(resultCode=="0007"){
                MCP.alert("이미 해당 이벤트의 댓글을 작성하셨습니다.");
                return false;
            }else if(resultCode=="9999"){
                MCP.alert(result.RESULT_MSG);
                return false;
            }
        });
    }

});


// 이벤트 댓글 삭제
function deleteReview(reqKey){

    $("#reqKey").val(reqKey);

    if($("#userlogin").val() == 'N'){
        if($("#successFlag").val() == 'N'){
            $("#eventFlag").val("delete");
            selfIdent();
        }else{
            $("#eventFlag").val("delete");

            var varData = ajaxCommon.getSerializedData({
                dupInfo : $("#niceDupInfo").val()
               ,reviewPromotionSeq : reqKey
           });

       ajaxCommon.getItem({
               id : 'eventJoinDeleteAjax'
               , cache : false
               , async : false
               , url : '/event/eventJoinDeleteAjax.do'
               , data : varData
               , dataType : "json"
           }
           ,function(result){
               if(result.RESULT_CODE == '00000'){
                   MCP.alert("후기 삭제가 완료되었습니다.", function(){
                       $("#reviewBoard").html("");
                       $(".c-icon--close").trigger("click");
                       $("textarea[name='reviewSbst']").val("");
                       $("#chkAgree").prop("checked", false);
                       $("#pageNo").val(1);
                       initreViewData(1);
                   });
               }else if(result.RESULT_CODE == '0002'){
                   MCP.alert("직접 작성하신 댓글만 삭제가 가능합니다.", function(){
                       $(".c-modal, .is-active").find(".c-icon--close").trigger("click");
                   });
               }
           });
        }
    }else{
        var varData = ajaxCommon.getSerializedData({
            dupInfo : $("#niceDupInfo").val()
           ,reviewPromotionSeq : reqKey
       });

       ajaxCommon.getItem({
               id : 'eventJoinDeleteAjax'
               , cache : false
               , async : false
               , url : '/event/eventJoinDeleteAjax.do'
               , data : varData
               , dataType : "json"
           }
           ,function(result){
               if(result.RESULT_CODE == '00000'){
                   MCP.alert("댓글 삭제가 완료되었습니다.", function(){
                       $("#reviewBoard").html("");
                       $("textarea[name='reviewSbst']").val("");
                       $("#chkAgree").prop("checked", false);
                       $(".c-icon--close").trigger("click");
                       $("#pageNo").val(1);

                       initreViewData(1);
                   });
               }else if(result.RESULT_CODE == '0002'){
                   MCP.alert("직접 작성하신 댓글만 삭제가 가능합니다.", function(){
                       $(".c-modal, .is-active").find(".c-icon--close").trigger("click");
                   });
               }
           });

    }
}

var scrollMove = function () {

    if (sessionStorage.bestBoardOrder != null && sessionStorage.bestBoardOrder < 3) {
        var targetId = "acc_label_c"+ (100 + parseInt(sessionStorage.bestBoardOrder));
        var targetTop = $("#" + targetId).offset().top - 60;
        window.scroll(0, targetTop);
        sessionStorage.clear();
    }

    // 이벤트 댓글 작성 텍스트 count
    $("textarea[name='reviewSbst']").on("keyup", function(){

        var inputText = $("textarea[name='reviewSbst']").val(); // textarea의 값을
                                                                // 가져옴
        var forbiddenWord = word.check(inputText); // 금칙어 확인

        if (forbiddenWord) {
            var censoredText = inputText.replace(new RegExp(forbiddenWord, 'g'), '*'.repeat(forbiddenWord.length+1)); // 금칙어를
                                                                                                                        // 글자
                                                                                                                        // 수만큼
                                                                                                                        // *로
                                                                                                                        // 변경
            alert("부적절한 단어가 포함되어 있습니다.");
            $("textarea[name='reviewSbst']").val(censoredText); // 변경된 값을
                                                                // textarea에 설정
        }

// var censoredText = $("textarea[name='reviewSbst']").val().replace()
// $("textarea[name='reviewSbst']").val("");


        $("#txtCnt").text($(this).val().length);
        if($(this).val().length > 800){
            $("textarea[name='reviewSbst']").val(
                $("textarea[name='reviewSbst']").val().substring(0,800)
            )
        }
    });
}

// 본인인증 팝업 띄우기
function selfIdent(){

     var data = {width:'500', height:'700'};
        openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data)

}

// sms 인증 완료 후 수행(nice 리턴)
function fnNiceCert(rtnObj){


    var promotionCode = "";

    if($("#eventDetailFalg").val() == 'Y'){
        promotionCode = $("#selEventVal").val();
    }else{
        promotionCode = $("#selEventCd").val();
    }

    ajaxCommon.getItem({
        id:'getTimeInMillisAjax'
        ,cache:false
        ,url:'/nice/getTimeInMillisAjax.do'
        ,data: ""
        ,dataType:"json"
    }
    ,function(startTime){
         var eventFlag = $("#eventFlag").val();
            var varData = ajaxCommon.getSerializedData({
                  cstmrName: 'cstmrName'
                , cstmrNativeRrn : '111111'
                , reqSeq:rtnObj.reqSeq
                , resSeq:rtnObj.resSeq
                , paramR3:'baseAuth'
                , startTime:startTime
            });

            ajaxCommon.getItemNoLoading({
                id:'getContractAuth'
                ,cache:false
                ,async:false
                ,url:'/auth/getReqAuthAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (jsonObj.RESULT_CODE == "00000") {
                     if(eventFlag == 'save'){
                            var varData = ajaxCommon.getSerializedData({
                                promotionCode : promotionCode,
                                reviewContent : $("textarea[name='reviewSbst']").val(),
                                isLogin : $("#isLogin").val()
                            });

                            ajaxCommon.getItem({
                                id : 'eventJoinRegAjax'
                                , cache : false
                                , async : false
                                , url : '/event/eventJoinRegAjax.do'
                                , data : varData
                                , dataType : "json"
                            }
                            ,function(result){
                                var resultCode = result.RESULT_CODE;
                                var resultNice = result.SESSNICE_RES
                                if(resultCode=="00000"){
                                    MCP.alert("등록되었습니다.", function(){
                                        $("#successFlag").val("Y");
                                        $("textarea[name='reviewSbst']").val("");
                                        $("#chkAgree").prop("checked", false);
                                        $("#niceName").val(resultNice.name);
                                        $("#niceMobileNo").val(resultNice.sMobileNo);
                                        $("#niceReqSeq").val(resultNice.reqSeq);
                                        $("#niceResSeq").val(resultNice.resSeq);
                                        $("#niceUseTelCode").val(resultNice.sMobileCo);
                                        $("#niceDupInfo").val(resultNice.dupInfo);
                                        initreViewData(1);
                                    });
                                }else if(resultCode=="0004"){
                                     MCP.alert("등록에 실패했습니다.");
                                     $("#eventFlag").val("save");
                                     return false;
                                 }else if(resultCode=="0006"){
                                    MCP.alert("정지된 회선은 후기등록이 불가합니다.");
                                    return false;
                                }else if(resultCode=="0007"){
                                    MCP.alert("이미 해당 이벤트의 댓글을 작성하셨습니다.", function(){
                                        $("#successFlag").val("Y");
                                        $("#niceName").val(resultNice.name);
                                        $("#niceMobileNo").val(resultNice.sMobileNo);
                                        $("#niceReqSeq").val(resultNice.reqSeq);
                                        $("#niceResSeq").val(resultNice.resSeq);
                                        $("#niceUseTelCode").val(resultNice.sMobileCo);
                                        $("#niceDupInfo").val(resultNice.dupInfo);
                                    });
                                }else if(resultCode=="9999"){
                                    MCP.alert(result.RESULT_MSG);
                                    return false;
                                }
                            });
                        }else if(eventFlag == 'delete'){

                            var varData = ajaxCommon.getSerializedData({
                                reviewPromotionSeq : $("#reqKey").val()
                            });

                            ajaxCommon.getItem({
                                    id : 'eventJoinDeleteAjax'
                                    , cache : false
                                    , async : false
                                    , url : '/event/eventJoinDeleteAjax.do'
                                    , data : varData
                                    , dataType : "json"
                                }
                                ,function(result){
                                    var resultNice = result.SESSNICE_RES
                                    if(result.RESULT_CODE == '00000'){
                                        MCP.alert("댓글 삭제가 완료되었습니다.", function(){
                                            $("#niceName").val(resultNice.name);
                                            $("#niceMobileNo").val(resultNice.sMobileNo);
                                            $("#niceReqSeq").val(resultNice.reqSeq);
                                            $("#niceResSeq").val(resultNice.resSeq);
                                            $("#niceUseTelCode").val(resultNice.sMobileCo);
                                            $("#niceDupInfo").val(resultNice.dupInfo);
                                            $("#successFlag").val("Y");
                                            $("#reviewBoard").html("");
                                            $("textarea[name='reviewSbst']").val("");
                                            $("#chkAgree").prop("checked", false);
                                            $(".c-icon--close").trigger("click");
                                            $("#pageNo").val(1);
                                            initreViewData(1);
                                        });
                                    }else if(result.RESULT_CODE == '0002'){
                                        MCP.alert("직접 작성하신 댓글만 삭제가 가능합니다.", function(){
                                            $("#successFlag").val("Y");
                                            $("#niceName").val(resultNice.name);
                                            $("#niceMobileNo").val(resultNice.sMobileNo);
                                            $("#niceReqSeq").val(resultNice.reqSeq);
                                            $("#niceResSeq").val(resultNice.resSeq);
                                            $("#niceUseTelCode").val(resultNice.sMobileCo);
                                            $("#niceDupInfo").val(resultNice.dupInfo);
                                            $(".c-modal, .is-active").find(".c-icon--close").trigger("click");
                                        });
                                    }
                                });

                        }else if(eventFlag == 'myContent'){

                            ajaxCommon.getItem({
                                 id : 'eventMyPostAjax'
                                 , cache : false
                                 , async : false
                                 , url : '/event/eventMyPostAjax.do'
                                 , data : ""
                                 , dataType : "json"
                             }
                             ,function(result){
                                 var resultCode = result.RESULT_CODE;
                                 var resultNice = result.SESSNICE_RES
                                 if(resultCode=="00000"){
                                     $("#niceName").val(resultNice.name);
                                     $("#niceMobileNo").val(resultNice.sMobileNo);
                                     $("#niceReqSeq").val(resultNice.reqSeq);
                                     $("#niceResSeq").val(resultNice.resSeq);
                                     $("#niceUseTelCode").val(resultNice.sMobileCo);
                                     $("#niceDupInfo").val(resultNice.dupInfo);
                                     $("#successFlag").val("Y");
                                     onlyMine = true;
                                     $("#chkFlag").val("Y");
                                     initreViewData(1);
                                     $('#chkMyReview').prop('checked', true);
                                 }else if(resultCode=="9999"){
                                     MCP.alert(result.RESULT_MSG);
                                     return false;
                                 }
                             });
                       }
                } else{
                    MCP.alert(jsonObj.RESULT_DESC);
                    return null;
                }
            });
    });
}


// 현재 url 복사하기(공유)
function clipNew() {

    var varData = ajaxCommon.getSerializedData({
        promotionCode : $("#selEventCd").val()
      , mobileNo : $("#selectA").val()
    });

    ajaxCommon.getItem({
        id : 'eventJoinShareAjax'
        , cache : false
        , async : false
        , url : '/event/eventJoinShareAjax.do'
        , data : varData
        , dataType : "json"
    }
    ,function(result){
        var resultCode = result.RESULT_CODE;
    });

    var url = '';
    var proMoNum= $("#selEventCd").val();
    var textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    url = window.document.location.href;
    var endPoint = url.indexOf("do");
    url = url.substring(0,(endPoint+2));
    url += "?proMoNum="+encodeURIComponent(proMoNum);
    var param = $("#param").val();
    if(param){
        url = url + param;
    }
    textarea.value = url;
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);
    MCP.alert("해당 이벤트 URL이 복사되었습니다.");
}

function kakaoShare() {
    //var eventImg = $("#listImg").val();
    var eventImg = "https://www.ktmmobile.com/ktMmobile_og.png";
    var mobileLink = "/m/event/eventJoin.do?proMoNum=" + $('#selEventCd').val();
    var webLink = "/event/eventJoin.do?proMoNum=" + $('#selEventCd').val();

    kakao_share(eventImg, mobileLink, webLink);
}

// 댓글 펼치기
$(document).on("click", ".runtime-data-insert", function(){
    var hasClass = $(this).hasClass("is-active");
    var title = $(this).siblings().find(".review__title--type2").text();
    if(hasClass){
        $(this).children().remove();
        $(this).append("<span class='c-hidden'>" + title + " 접기</span>");
    } else {
        $(this).children().remove();
        $(this).append("<span class='c-hidden'>" + title + " 펼쳐보기</span>");
    }
});

// 약관동의 제어
function setChkbox(data) {
    var cnt = 0;

    var agreeId = $(data).attr("id");

    $('input[name=terms]').each(function (){
        if($(this).prop('checked')){
            cnt++;
        }
    });

    if(cnt == $('input[name=terms]').length){
        $('#btnStplAllCheck').prop('checked', 'checked');
    }else{
        $('#btnStplAllCheck').removeProp('checked');
    }

    if(agreeId=='priAdAgree'){
        if($(priAdAgree).prop('checked')){
            MCP.alert("귀하가 " +day.format("yyyy-MM-dd")+ " 요청하신 수신동의가<br> 정상적으로 처리 되었습니다.<br>수신동의 철회(무료) : ARS 080-870-9812");
        }
    }
}

//개인정보 수집 동의(동의 후 닫기)
function agreeClose(data){

    if(data == '1'){
        $("#priAgree").prop("checked", true);
    }else if(data == '2'){
        $("#priEventAgree").prop("checked", true);
    }else if(data == '3'){
        $("#priMarketingAgree").prop("checked", true);
    }else if(data == '4'){
        if(!$(priAdAgree).prop('checked')){
          $("#priAdAgree").prop("checked", true);
          MCP.alert("귀하가 " +day.format("yyyy-MM-dd")+ " 요청하신 수신동의가<br> 정상적으로 처리 되었습니다.<br>수신동의 철회(무료) : ARS 080-870-9812");
        }
    }

    setChkbox();
}

