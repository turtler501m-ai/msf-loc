
var interval ;
var onlyMine = false;
var key = "";
var reviewNo = 100;
var searchVal = '';

$(document).ready(function(){
    $("#noDataVal").hide();
    $(".ly-footer").hide();
    var pageNo = $("#pageNo").val();
    getBannerList();
    if(!pageNo){pageNo = 1;}
    initreViewData(pageNo);
    reviewAnswerList();
    bestReviewList();

    var tabIndex = $("#questId").val();
    if(tabIndex != "") {
        setTimeout(function(){
            $('button[eventcategory='+tabIndex+']').trigger("click");
        },150);
    }


    // 삭제버튼
    $(document).on("click", ".delBtn", function(){
        clearCert1();
        var contractNum = $(this).attr("contractNum");
        $("#contractNum").val(contractNum);
        fn_mobile_layerOpen('delLayer');
    });

    // sms 인증버튼
    $("#sms").click(function(){
        fn_mobile_layerClose('confrmLayer');
        fn_mobile_layerOpen('joinLayer');
        clearCert();
    });

    // 메인 사용후기 타고 온경우
    setTimeout(function(){
        if($("#reviewVal").val() != '' && $("#reviewVal").val() != null){
            var reviewVal = $("#reviewVal").val();
            var reviewMo = $("#reviewMoves" + reviewVal);
            var reviewMoLink = $(reviewMo).attr("href");
            var reviewMoBtn = $(reviewMoLink).parent().parent().find(".review-accordion");
            setTimeout(function(){
                $(reviewMoBtn).trigger("click");
                $(window).scrollTop($(reviewMoLink).offset().top-95);
                history.replaceState({}, null, location.pathname);
            },100);
        }
    },100);


    //상단 우수 사용후기 선택 후 스크롤 이동
    setTimeout(function(){
        $(".review-rank__item a").click(function(){
            event.preventDefault();
            var reviewLink = $(this).attr("href");
            var reviewBtn = $(reviewLink).parent().parent().find(".review-accordion");
            var hasClass = $(reviewBtn).hasClass("is-active");

            if(hasClass){
                $(window).scrollTop($(reviewLink).offset().top-95);
            } else {
                $(reviewBtn).trigger("click");
                $(window).scrollTop($(reviewLink).offset().top-95);
            }
        });
    },100);

    // 이벤트 선택
    $(document).on("click", "#selectEventReview", function(){
        $("#reviewBoard").html("");
        $("#pageNo").val(1);
        initreViewData(1);
        reviewAnswerList();
        var tabIndex = $("#questId").val();
        $('.review-chart').html('');
        if(tabIndex != "") {
            setTimeout(function(){
                $('button[eventcategory='+tabIndex+']').trigger("click");
            },150);
        }
    });

    // 내 후기만 보기 체크박스 선택
    $("#chkMyReview").on("change", function(){
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
    });

    //탭버튼 클릭이벤트
    $(document).on("click",".c-tabs__button", function(){
        if($(this).attr("id") == 'tab1'){
            $(".ly-footer").hide();
            /*ajaxCommon.createForm({
                    method:"post"
                    ,action:"/requestReView/requestReView.do"
            });

            ajaxCommon.formSubmit(); */
            /*			location.href = '/requestReView/requestReView.do';*/
        }else{
            $(".ly-footer").hide();
            /*ajaxCommon.createForm({
                    method:"post"
                    ,action:"/requestReView/requestReviewForm.do"
            });

            ajaxCommon.formSubmit(); */
            /*location.href = '/requestReView/requestReviewForm.do';*/
        }
    });


    // 리뷰 삭제하기
    $("#reviewBtn1").click(function(){

        var contractNum = $("#contractNum").val();
        var phone = ajaxCommon.isNullNvl($("#phone1").val(),"");
        var certifySms = ajaxCommon.isNullNvl($("#certifySms1").val(),"");
        var name = ajaxCommon.isNullNvl($("#name1").val(),"");
        var varData = ajaxCommon.getSerializedData({
            certifySms:$.trim(certifySms)
            ,phone:$.trim(phone)
            ,contractNum : contractNum
            ,name : name
        });

        ajaxCommon.getItem({
                id:'reviewDeleteAjax'
                ,cache:false
                ,url:'/m/review/reviewDeleteAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj) {
                var resultCode = jsonObj.RESULT_CODE;
                var msg = "";
                var returnUrl = "";

                if(resultCode=="00000"){

                    alert("삭제되었습니다.");
                    location.reload();
                } else if(resultCode=="0001"){

                    alert("인증을 받아주세요.");
                    return false;
                } else if(resultCode=="0002"){

                    alert("본인이 작성한 리뷰만 삭제 가능합니다.");
                    return false;
                } else {
                    alert("삭제에 실패했습니다.");
                    return false;
                }

            });

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

    // 후기 펼침 토글 클릭 이벤트(조회수)
    $(document).on("click", ".review-accordion", function(){

        if($(this).hasClass("is-active")){
            increaseHit($(this).attr("key"),$(this).attr("reviewId"));
        }
    });

    $(document).on("click", ".c-paging a", function(){

        if($(this).attr("pageNo")){
            $(window).scrollTop($("#accordionReview").offset().top-200);
            initreViewData($(this).attr("pageNo"), searchVal);
        }
    });

    // 지난베스트후기 및 SNS공유후기 제어
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


//리뷰 리스트 로딩
var initreViewData = function(pageNo, targetId){
    var eventCd = $("#selEventCd").val();
    var varData = ajaxCommon.getSerializedData({
        pageNo : pageNo
        ,eventCd : eventCd
        , onlyMine : onlyMine
        , searchCategory: targetId
    });

    ajaxCommon.getItem({
        id:'reviewDataAjax'
        ,cache:false
        ,url:'/requestReView/reviewDataAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    },function(jsonObj){

        var total = jsonObj.total;
        var requestReviewDtoList = jsonObj.requestReviewDtoList;
        var conNumList = jsonObj.conNumList;
        var isLogin = jsonObj.isLogin;
        var eventTotal = jsonObj.eventTotal;
        var eventList = jsonObj.eventList;
        var selectHtml = "";

        ajaxCommon.setPortalPaging($("#paging"), jsonObj.pageInfoBean);
        $("#selEventCd").html("");
        if((!conNumList || conNumList.length == 0)&&onlyMine){
            selectHtml += "<option value=''>전체보기(0)</option>";
        }else{
            selectHtml += "<option value=''>전체보기(" + (eventTotal*1).toLocaleString("ko-KR") + ")</option>";
        }
        for(var i = 0; i < eventList.length; i ++){
            if((!conNumList || conNumList.length == 0)&&onlyMine){
                selectHtml += "<option value='" + eventList[i].dtlCd + "'>" + eventList[i].Nm + "(0)</option>";
            }else{
                selectHtml += "<option value='" + eventList[i].dtlCd + "'>" + eventList[i].dtlCdNm + "(" + (eventList[i].eventCdCtn*1).toLocaleString("ko-KR") +")</option>";
            }
        }
        $("#selEventCd").append(selectHtml);
        viewDesc(total,requestReviewDtoList, conNumList, isLogin);
        $("#selEventCd option[value='" + eventCd + "']").prop("selected", true);

        // window.scrollTo(0,0);
        scrollMove();

    });

}


var viewDesc = function(total,requestReviewDtoList, conNumList, isLogin){
    $("#nodata").remove();
    $("#reviewBoard").html('');
    var htmlArea = "";
    if((!conNumList || conNumList.length == 0)&& onlyMine){
        $("#moreBtn").hide();
        /*$("#selEventCd").hide();*/
        $("#recommendInfo").hide();
        htmlArea += "<div class='c-nodata' id='nodata'>" +
            "<i class='c-icon c-icon--nodata' aria-hidden='true'></i>" +
            "<p class='c-nodata__text'>조회 결과가 없습니다.</p>" +
            "</div>";
        $(".c-row--lg").append(htmlArea);

        $("#paging").hide();
        MCP.alert("조회된 결과가 없습니다.");
        sessionStorage.clear();

    }else{


        if(requestReviewDtoList !=null && requestReviewDtoList.length>0){
            var reviewImgList = "";
            var regNm = "";
            var reqBuyTypeNm = "";
            var eventCd = "";
            var eventNm = "";
            var sysRdateDd = "";
            var reviewSbst = "";
            var snsInfo = "";
            var commendYn = ""; // 추천,비추천
            var ntfYn = ""; // best
            var contractNum = "";
            var ranking = 0;
            var requestKey ="";
            var prizeSbst = "";
            var reviewQuestionList = "";
            var reviewId = "";

            for( var i=0; i<requestReviewDtoList.length; i++ ){

                reviewImgList = requestReviewDtoList[i].reviewImgList;
                regNm = requestReviewDtoList[i].mkRegNm;
                reqBuyTypeNm = requestReviewDtoList[i].reqBuyTypeNm;
                eventCd = requestReviewDtoList[i].eventCd;
                eventNm = requestReviewDtoList[i].eventNm;
                sysRdateDd = requestReviewDtoList[i].sysRdateDd;
                reviewSbst = unescapeHtml(requestReviewDtoList[i].reviewSbst);
                snsInfo = ajaxCommon.isNullNvl(requestReviewDtoList[i].snsInfo,"");
                commendYn = requestReviewDtoList[i].commendYn;
                ntfYn = requestReviewDtoList[i].ntfYn;
                contractNum = requestReviewDtoList[i].contractNum;
                ranking = requestReviewDtoList[i].ranking;
                requestKey = requestReviewDtoList[i].requestKey;
                prizeSbst = requestReviewDtoList[i].prizeSbst;
                reviewQuestionList = requestReviewDtoList[i].reviewQuestionList;
                reviewId = requestReviewDtoList[i].reviewId;

                htmlArea += "<li class='c-accordion__item'>";
                htmlArea += "<div class='c-accordion__head'>";
                htmlArea += '<div class="review__label-wrap" id="acc_label_c'+reviewNo+'">';


                if(commendYn =="1"){
                    if(ntfYn == 1){
                        if(prizeSbst < 4 && prizeSbst != null && prizeSbst !=''){
                            htmlArea += "<span class='c-text-label c-text-label--mint-type2'>추천</span>";
                            htmlArea += "<span class='c-text-label c-text-label--rank' id='reviewBoard"+prizeSbst+"'>★" + prizeSbst + "등</span>";

                        }else{
                            htmlArea += "<span class='c-text-label c-text-label--mint-type2'>추천</span>";
                            htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                        }
                    }else{
                        htmlArea += "<span class='c-text-label c-text-label--mint-type2'>추천</span>";
                    }
                }else{
                    if(ntfYn == 1){
                        if(prizeSbst < 4 && prizeSbst != null && prizeSbst !=''){
                            htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                            htmlArea += "<span class='c-text-label c-text-label--rank'>★" + prizeSbst + "등</span>";
                        }else{
                            htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                            htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                        }
                    }else{
                        htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                    }
                }
                htmlArea += '</div>'
                htmlArea += '<ul class="review__info">';
                htmlArea += '<li class="review__info__item">';
                htmlArea += '<span class="sr-only">작성자</span>'+ regNm ;
                htmlArea += '</li>';
                htmlArea += '<li class="review__info__item">';
                htmlArea += '<span class="sr-only">작성일</span>' + sysRdateDd;
                htmlArea += '</li>';



                if(isLogin){
                    for(var j = 0; j < conNumList.length; j ++){
                        if(conNumList[j] == contractNum){
                            htmlArea += '<li class="review__info__item">';
                            htmlArea += '<button class="c-button">';
                            htmlArea += '<span class="sr-only">삭제</span>';
                            htmlArea += '<i class="c-icon c-icon--trash delete-button-in" aria-hidden="true" id="' + requestKey + '" contractNum="' + contractNum + '"reviewId="' + reviewId + '"></i>';
                            htmlArea += '</button>';
                            htmlArea += '</li>';
                        }
                    }
                }else{
                    htmlArea += '<li class="review__info__item">';
                    htmlArea += '<button class="c-button">';
                    htmlArea += '<span class="sr-only">삭제</span>';
                    htmlArea += '<i class="c-icon c-icon--trash delete-button-out" aria-hidden="true" id="' + requestKey + '" contractNum="' + contractNum + '"reviewId="' + reviewId + '"></i>';
                    htmlArea += '</button>';
                    htmlArea += '</li>';
                }
                htmlArea += '</ul>';


                htmlArea += "<strong class='review__title'>" + reqBuyTypeNm+"/"+eventNm + "</strong>";

                htmlArea += '<ul class="review-summary">';

                if(reviewQuestionList !=null && reviewQuestionList.length>0){
                    for(var j = 0; j < reviewQuestionList.length; j ++){
                        htmlArea += '<li class="review-summary__item">';
                        htmlArea += '<div class="review-summary__question">' +reviewQuestionList[j].questionMm +'</div>';
                        htmlArea += '<div class="review-summary__answer">' + reviewQuestionList[j].answerDesc+'</div>';
                        htmlArea += '</li>';

                    }
                }
                htmlArea += '</ul>';

//                if (prizeSbst < 4 && prizeSbst != '') {
//                    htmlArea += '<button class="runtime-data-insert c-accordion__button review-accordion is-active" id="acc_header_c'+reviewNo+'" type="button" key="' + requestKey + '" aria-expanded="true" aria-controls="acc_content_c'+reviewNo+'" >';
//                } else {
//                    htmlArea += '<button class="runtime-data-insert c-accordion__button review-accordion" id="acc_header_c'+reviewNo+'" type="button" key="' + requestKey + '" aria-expanded="false" aria-controls="acc_content_c'+reviewNo+'" >';
//                }

                htmlArea += '<button class="runtime-data-insert c-accordion__button review-accordion" id="acc_header_c'+reviewNo+'" type="button" key="' + requestKey + '" reviewId="' + reviewId + '" aria-expanded="false" aria-controls="acc_content_c'+reviewNo+'" >';

                htmlArea += '<span class="c-hidden">'  + reqBuyTypeNm+"/"+eventNm + ' 전체보기</span>'
                htmlArea += '</button>';
                htmlArea += '</div>';

//                //펼치기
//                if (prizeSbst < 4 && prizeSbst != '') {
//                    htmlArea += '<div class="c-accordion__panel expand expanded" id="acc_content_c'+reviewNo+'" aria-labelledby="acc_header_c'+reviewNo+'">';
//                } else {
//                    htmlArea += '<div class="c-accordion__panel expand" id="acc_content_c'+reviewNo+'" aria-labelledby="acc_header_c'+reviewNo+'">';
//                }

                htmlArea += '<div class="c-accordion__panel expand" id="acc_content_c'+reviewNo+'" aria-labelledby="acc_header_c'+reviewNo+'">';

                htmlArea += '<div class="c-accordion__inside">';
                htmlArea += '<div>';
                htmlArea += '<div class="review__content">';
                if(reviewImgList !=null && reviewImgList.length>0){
                    htmlArea += '<div class="review__preview">';
                    htmlArea += '<img src="' + reviewImgList[0].filePathNm + '" alt="" onerror="this.src=\'/resources/images/portal/content/img_review_noimage.png\'">';

                    if(reviewImgList.length > 1){
                        htmlArea += '<span class="review__image__label">';
                        htmlArea += '<span class="sr-only">썸네일사진 외 추가로 등록된 후기사진 개수</span>+' + (reviewImgList.length - 1);
                        htmlArea += '</span>';
                    }

                    htmlArea += '</div>';
                }

                htmlArea += '<pre><p class="review__text">' + reviewSbst + '</p><pre>';
                if(snsInfo !=""){
                    if(snsInfo.indexOf("http") > -1){
                        htmlArea += '<a class="review__button" href="' + snsInfo + '" target="_blank" title="새창">SNS 공유 사용후기 보기</a>';
                    } else {
                        htmlArea += '<a class="review__button" href="https://' + snsInfo + '" target="_blank" title="새창">SNS 공유 사용후기 보기</a>';
                    }
                }
                if(reviewImgList !=null && reviewImgList.length>0){
                    htmlArea += "<ul class='review__images' id='content_img_" + reviewNo + "' >";
                    for(var k = 0; k < reviewImgList.length; k ++){
                        htmlArea += '<li class="review__images__item">';
                        htmlArea += '<div class="review__image">';
                        htmlArea += '<img src="' + reviewImgList[k].filePathNm + '" alt="' + reviewImgList[k].fileAlt + '"  aria-hidden="true"  onerror="this.src=\'/resources/images/portal/content/img_review_noimage.png\'">';
                        htmlArea += '</div>';
                        if(reviewImgList[k].fileAlt){
                            htmlArea += '<p class="c-bullet c-bullet--dot u-co-gray">' + reviewImgList[k].fileAlt + '</p>';
                        }
                        htmlArea += ' </li>';
                    }
                    htmlArea += "</ul>";
                }


                htmlArea += '</div>';
                htmlArea += '</div>';
                htmlArea += '</div>';
                htmlArea += '</div>';
                htmlArea += '</li>';

                reviewNo ++;
            }
            // append
            $("#reviewBoard").append(htmlArea);
            //$("#moreBtn").show();
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
            /*$("#selEventCd").hide();*/
            $("#recommendInfo").hide();
            htmlArea += "<div class='c-nodata' id='nodata'>" +
                "<i class='c-icon c-icon--nodata' aria-hidden='true'></i>" +
                "<p class='c-nodata__text'>조회 결과가 없습니다.</p>" +
                "</div>";
            $(".c-row--lg").append(htmlArea);
            $("#paging").hide();
            MCP.alert("조회된 결과가 없습니다.");
            sessionStorage.clear();
        }

    }

    // 더보기
    var reViewCurrent = $(".review__item").length;

    $("#reViewCurrent").text((reViewCurrent*1).toLocaleString('ko-KR')); // 현재 노출
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
        reviewId = $(this).attr("reviewId");

        if(confirm("작성한 후기를 삭제하시겠습니까?")){

            deleteReview(key,reviewId);

        }
    });

    $(".delete-button-out").on("click", function(){
        key = $(this).attr("id");
        reviewId = $(this).attr("reviewId");

        var parameterData = ajaxCommon.getSerializedData({
            menuType : 'reviewDel',
            popType : 'nmIncPop',
            buttonType : 'certifyDelBut'
        });

        openPage("smsCertifyPop", "/requestReview/requestReviewCertifyPop.do", parameterData);
        $("#certifyDelBut").on("click",function(){
            if($("#certifyYn").val() == 'Y'){
                if($("#chkAgreeA").is(":checked")){
                    deleteReview(key,reviewId);
                }else{
                    MCP.alert("유의사항에 동의 후 삭제가 가능합니다.");
                    return false;
                }
            }else{
                MCP.alert("가입정보 인증 후 삭제가 가능합니다.");
                return false;
            }
        });
    });

    $("#subbody_loading").hide();
    $(".ly-footer").show();
}

//배너 호출 ajax
var getBannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'E001'
    });

    ajaxCommon.getItem({
            id : 'bannerListAjax'
            , cache : false
            , async : false
            //, url : '/m/event/getM008BannerListAjax.do'
            , url : '/m/bannerListAjax.do'
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
                        eventPopTitle : '사용후기 이벤트'
                    });
                    $("#eventPop").remove();
                    openPage('eventPop', $(this).attr("addr"), parameterData)
                    $("#modalCardEventTitle").text("사용후기 이벤트");
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

// 조회수 증가
function increaseHit(key,reviewId){
    var varData = ajaxCommon.getSerializedData({
        requestKey : key
      , reviewId : reviewId
    });

    ajaxCommon.getItem({
            id : 'modifyReviewHitAjax'
            , cache : false
            , async : false
            , url : '/requestReView/modifyReviewHitAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(data){
            if(data.RETURN_CODE == '0000'){

            }else{

            }
        });
}


//리뷰삭제
function deleteReview(reqKey,reviewId){
    var varData = ajaxCommon.getSerializedData({
        contractNum : $("#" + reqKey).attr("contractNum")
        ,phone : $("#phoneNum").val()
        ,name : $("#custNm").val()
        ,requestKey : reqKey
        ,reviewId : reviewId
    });

    ajaxCommon.getItem({
            id : 'reviewDeleteAjax'
            , cache : false
            , async : false
            , url : '/requestReView/reviewDeleteAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            if(result.RESULT_CODE == '00000'){
                MCP.alert("후기 삭제가 완료되었습니다.", function(){
                    /*$("#" + key).parent().parent().remove();*/
                    $("#reviewBoard").html("");
                    $(".c-icon--close").trigger("click");
                    $("#pageNo").val(1);
                    initreViewData(1);
                });
            }else if(result.RESULT_CODE == '0001'){
                MCP.alert("로그인 후 이용가능한 서비스입니다.", function(){
                    $(".c-modal, .is-active").find(".c-icon--close").trigger("click");
                });
            }else if(result.RESULT_CODE == '0002'){
                MCP.alert("작성자와 일치하지 않습니다.", function(){
                    $(".c-modal, .is-active").find(".c-icon--close").trigger("click");
                });
            }else if(result.RESULT_CODE == '0003'){
                MCP.alert("회선정보가 일치하지 않습니다.", function(){
                    $(".c-modal, .is-active").find(".c-icon--close").trigger("click");
                });
            }else if(result.RESULT_CODE == 'STEP01' || result.RESULT_CODE == 'STEP02'){ // STEP 오류
                MCP.alert(result.RESULT_MSG, function(){
                    $(".c-modal, .is-active").find(".c-icon--close").trigger("click");
                });
            }else{
                MCP.alert("삭제에 실패했습니다.", function(){
                    $(".c-modal, .is-active").find(".c-icon--close").trigger("click");
                });
            }
        });
}

var scrollMove = function () {

    if (sessionStorage.bestBoardOrder != null && sessionStorage.bestBoardOrder < 3) {
        var targetId = "acc_label_c"+ (100 + parseInt(sessionStorage.bestBoardOrder));
        var targetTop = $("#" + targetId).offset().top - 60;
        window.scroll(0, targetTop);
        sessionStorage.clear();
    }
}

//이벤트 팝업 내용의 아코디언 기능
$(document).on('click','.flexible .c-accordion__button',function() {
    $(this).parent().parent().find('.c-accordion__panel').toggleClass('expanded');
    $(this).toggleClass('is-active');
});


$("[name='reviewBtn']").click(function() {

    var isClass = $(this).hasClass("is-active");
    if(!isClass){
        $("[name='reviewBtn']").each(function(){
            $(this).removeClass("is-active");
        });
        $(this).addClass("is-active");
    }
});


//사용후기  review 차트
function reviewAnswerList(){

   var htmlArea = "";
   var questId = $('#questId').val();

   if(questId != undefined && questId != null && questId != ""){
       var questionId = questId.slice(6);

       var varData = ajaxCommon.getSerializedData({
            eventCd : $('#selEventCd').val()
           ,questionId : questionId
       });

       ajaxCommon.getItem({
               id : 'reviewAnswerAjax'
               , cache : false
               , async : false
               , url : '/requestReView/reviewAnswerAjax.do'
               , data : varData
               , dataType : "json"
           }
           ,function(result){

               if(result.RESULT_CODE == '00000'){

                var reviewAnswerList = result.reviewAnswerList;

                if(reviewAnswerList !=null && reviewAnswerList.length>0){

                    var questionId = "";
                    var answerDesc = "";
                    var reviewPerCent = "";

                    for(var i=0; i<reviewAnswerList.length; i++){

                        questionId = reviewAnswerList[i].questionId;
                        answerDesc = reviewAnswerList[i].answerDesc;
                        reviewPerCent = Math.round(reviewAnswerList[i].answerCnt / reviewAnswerList[0].totalCnt * 100);


                        htmlArea += '<li class="review-chart__item" eventCategory="review'+reviewAnswerList[i].questionId+'">';
                        htmlArea += '<div class="review-chart__title">'+answerDesc+'</div>';
                        htmlArea += '<div class="c-indicator">';
                        htmlArea += '<span class="mark" style="width:'+reviewPerCent+'%"></span>';
                        htmlArea += '</div>'
                        htmlArea += '<div class="review-chart__percent">'+reviewPerCent+'%</div>'
                        htmlArea += '</li>'
                    }

                    $(".review-chart").append(htmlArea);

                }
               }else if(result.RESULT_CODE == '9999'){

               }
           });
   }

}


//사용후기 질문 클릭
function reviewTabClick(tab){
    var noDataHtml = "";
    $('.review-chart').html('');
    var eventCategory = $(tab).attr("eventCategory");
    $("#questId").val(eventCategory);
    reviewAnswerList();

    $('li[class="review-chart__item"][eventCategory!='+eventCategory+']').hide();
    $('li[class="review-chart__item"][eventCategory='+eventCategory+']').show();

    setTimeout(function(){
     if($('li[class="review-chart__item"][eventCategory='+eventCategory+']').length == 0){
            $("#noDataVal").show();
        }else{
            $("#noDataVal").hide();
        }
    },50);

}


//이번달 우수 사용후기 목록 조회
var bestReviewList = function() {
    var goodReview = "";
    ajaxCommon.getItem({
            id : 'bestReviewAjax'
            , cache : false
            , async : false
            , url : '/requestReView/bestReviewAjax.do'
            , data : ""
            , dataType : "json"
        }
        ,function(result){
            var requestReviewDtoList = result.requestReviewDtoList;
            var reviewImgList = "";
            var regNm = "";
            var reqBuyTypeNm = "";
            var eventCd = "";
            var eventNm = "";
            var sysRdateDd = "";
            var reviewSbst = "";
            var snsInfo = "";
            var commendYn = ""; // 추천,비추천
            var ntfYn = ""; // best
            var contractNum = "";
            var ranking = 0;
            var requestKey ="";
            var prizeSbst = "";
            var reviewQuestionList = "";

            var reviewListSize = requestReviewDtoList.length;
            if(reviewListSize > 3){
                reviewListSize = 3;
            }

            for( var i=0; i<reviewListSize; i++ ){

                reviewImgList = requestReviewDtoList[i].reviewImgList;
                regNm = requestReviewDtoList[i].mkRegNm;
                reqBuyTypeNm = requestReviewDtoList[i].reqBuyTypeNm;
                eventCd = requestReviewDtoList[i].eventCd;
                eventNm = requestReviewDtoList[i].eventNm;
                sysRdateDd = requestReviewDtoList[i].sysRdateDd;
                reviewSbst = unescapeHtml(requestReviewDtoList[i].reviewSbst);
                snsInfo = ajaxCommon.isNullNvl(requestReviewDtoList[i].snsInfo,"");
                commendYn = requestReviewDtoList[i].commendYn;
                ntfYn = requestReviewDtoList[i].ntfYn;
                contractNum = requestReviewDtoList[i].contractNum;
                ranking = requestReviewDtoList[i].ranking;
                requestKey = requestReviewDtoList[i].requestKey;
                prizeSbst = requestReviewDtoList[i].prizeSbst;
                reviewQuestionList = requestReviewDtoList[i].reviewQuestionList;

                goodReview += "<li class='review-rank__item'>";
                goodReview += "<a id='reviewMoves"+prizeSbst+"' href='#reviewBoard"+prizeSbst+"' title='사용후기 게시글 이동'>";

                goodReview += "<span class='review-rank__label'>★" + prizeSbst+"등</span>"
                goodReview += "<pre><p class='review-rank__text'>" + reviewSbst + " </p></pre>";
                goodReview += "<ul class='review-summary'>";


                if(reviewQuestionList !=null && reviewQuestionList.length>0){
                    for(var j = 0; j < reviewQuestionList.length; j ++){
                        if(ntfYn == 1){
                            if(prizeSbst < 4 && prizeSbst != null && prizeSbst !=''){
                                goodReview += '<li class="review-summary__item">';
                                goodReview += '<div class="review-summary__question">'+reviewQuestionList[j].questionMm +'<span class="review-summary__answer">' + reviewQuestionList[j].answerDesc+'</span></div>';
                                goodReview += '</li>';

                            }
                         }

                    }
                }

                if(ntfYn == 1){
                    if(prizeSbst < 4 && prizeSbst != null && prizeSbst !=''){
                        goodReview += '</ul>';


                        if(reviewImgList.length > 0) {
                            $.each(reviewImgList, function(idx, item) {
                                let v_filePathNm = item.filePathNm;
                                let v_fileAlt = ajaxCommon.isNullNvl(item.fileAlt, "");

                                goodReview += "	<div class='review-rank__img'>";
                                goodReview += "		<img src='"+v_filePathNm+"' alt='"+v_fileAlt+"' onerror='noImage(this);'>";
                                if(reviewImgList.length > 1) {
                                    goodReview += "		<span class='img-count'>+"+(reviewImgList.length-1)+"</span>";
                                }
                                goodReview += "	</div>";
                                if(idx == 0) {
                                    return false;
                                }
                            });
                        } else {
                            goodReview += "	<div class='review-rank__img'>";
                            goodReview += "		<img src='/resources/images/portal/content/img_review_noimage.png' alt='no-image'>";
                            goodReview += "	</div>";
                        }


                        goodReview += '</a>';
                        goodReview += '</li>';

                    }
                 }

            }

            $(".review-rank__list").append(goodReview);

        });
}


