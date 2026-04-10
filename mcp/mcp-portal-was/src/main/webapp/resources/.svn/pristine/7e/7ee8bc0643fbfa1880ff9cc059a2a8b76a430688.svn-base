var onlyMine = false;
var reviewNo = 0;
var initPhoneData;
var selSntyColorCd;
var colorData;

$(document).ready(function() {

    initColorData();

    for (var i = 0; i < document.getElementsByClassName('flexible').length; i++) {
        var id = document.getElementsByClassName('flexible')[i].id;
        flexibleComponent(id);
    }


    pageNo = $("#pageNo").val();
    initreViewData(pageNo);

    $(document).on("change", "select[name='atribValCd2']", function(){

        initColorArea($(this).val());
        callChargeList(true,false);
    });

    $(document).on("change", "input:radio[name='radGA']", function(){

        selSntyColorCd = $(this).val();
        findHndsetModelId($(this).val(),$("#atribValCd2").val());
        callChargeList(true,false);
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

    $("#main-content > div:nth-child(9) > div > div.c-tabs__list > button").first().prop("classList",'c-tabs__button is-active');

    $("#main-content > div:nth-child(9) > div > div.c-tabs__list > button").click(function(){

        $("#main-content > div:nth-child(9) > div > div.c-tabs__list > button").each(function(){
            $(this).prop("classList",'c-tabs__button');
        });

        $(this).prop("classList",'c-tabs__button is-active');

    });


    // 탭 - 하단 상품정보
    $('button[id^=tab]').each(function(){
        $('button[id^=tab]').removeAttr("tabindex");
        $('button[id^=tab]').removeAttr("aria-controls");
        setTimeout(function(){
            $('button[id^=tab]').first().trigger("click");
        },10);
        var hasClass = $(this).hasClass("is-active");
        if(hasClass){
            $(this).attr('aria-selected',"true");
        }else {
            $(this).attr('aria-selected',"false");
        }
    });

    $('button[id^=tab]').on("click", function(){
        $('button[id^=tab]').removeClass('is-active');
        $('button[id^=tab]').attr('aria-selected',"false");
        $(this).addClass('is-active');
        $(this).attr('aria-selected',"true");
        var tabId = $(this).attr('id');
        $('.c-tabs__panel').removeClass('u-block');
        $('.c-tabs__panel[aria-labelledby =' + tabId + ']').addClass('u-block');
    });










    callChargeList(true,true);

});

function goStore(){
    openPage('pullPopup','/product/phone/phoneInventoryStoreList.do','',1);
}


/**
   요금제 리스트 조회 하는 Ajax호출함수
   결과요금제 리스트로 요금제 목록테이블을 rebuild 한다.
*/
var callChargeList = function(isSetThumbnail,isInit) {
    var varData = ajaxCommon.getSerializedData({
         prodId:$("#fprodId").val()
        ,hndsetModelId:$("#hndsetModelId").val()
        ,onOffType:"3" //모바일
   });
   ajaxCommon.getItem({
        id:'getChargeList'
       ,cache:false
       ,url:'/m/product/phone/phoneViewDataAjax.do'
       ,data: varData
       ,dataType:"json"
   }
   ,function(jsonObj) {

        if(isInit){
            initPhoneData = jsonObj.phoneProdBas.phoneSntyBasDtosList;
            initAtribArea();
        }


        if (isSetThumbnail) {

            var phoneImgList = jsonObj.phoneProdBas.phoneProdImgDtoList ;
            var bgLayoutImg = jsonObj.phoneProdBas.layerYn == 'Y' ? jsonObj.phoneProdBas.layerImageUrl : '';
            var fprodNm = $("#fprodNm").val();
            var $mainSlider = $('#swiperDetail > div > div');
            var $subSlider = $('#swiperThumbs  > div > div');
            $mainSlider.empty();
            $subSlider.empty();
            var sntyColorCdFlag = false;
            for (var imgIdx=0; imgIdx < phoneImgList.length; imgIdx++) {
                if (phoneImgList[imgIdx].sntyColorCd == selSntyColorCd ) {
                    sntyColorCdFlag = true;
                    var imgDetailList = phoneImgList[imgIdx].phoneProdImgDetailDtoList ;
                    for (var i=0; i < imgDetailList.length; i++) {
                        if (imgDetailList[i].imgTypeCd != "04" && !isEmpty(imgDetailList[i].imgPath)) {
                            if (!isEmpty(bgLayoutImg)) {
                                $mainSlider.append("<div class='swiper-slide'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "(큰화면)' />" +
                                    "<img src='" + bgLayoutImg + "' alt='" + fprodNm + "_laout' /></div>");
                            } else {
                                $mainSlider.append("<div class='swiper-slide'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "(큰화면)' /></div>");
                            }
                            $subSlider.append("<a class='swiper-slide' role='button' href='#n'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "' /></a>");
                        }
                    }

                    break;
                }
             }

             //모든 컬러가 품절인경우..
             if(!sntyColorCdFlag){
                if(phoneImgList && phoneImgList.length > 0){
                    var imgDetailList = phoneImgList[0].phoneProdImgDetailDtoList ;
                        for (var i=0; i < imgDetailList.length; i++) {
                            if (imgDetailList[i].imgTypeCd != "04" && !isEmpty(imgDetailList[i].imgPath)) {
                                $mainSlider.append("<div class='swiper-slide'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "(큰화면)' /></div>");
                                $subSlider.append("<a class='swiper-slide' role='button' href='#n'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "' /></a>");
                            }
                    }

                }
             }

        }
        $("#swiperDetail > div > div > div > img").each(function(){
        $(this).error(function(){
            $(this).unbind("error");
            $(this).attr("src","/resources/images/portal/content/img_phone_noimage.png");
            var altText = $(this).attr("alt");
            $(this).attr("alt",altText += '(사진없음)');
            });
        });
        $("#swiperThumbs > div > div > a > img").each(function(){
            $(this).error(function(){
            $(this).unbind("error");
            var altText = $(this).attr("alt");
            $(this).attr("alt",altText += '(사진없음)');
            $(this).attr("src","/resources/images/portal/content/img_phone_noimage.png");
            });
        });
        initSwiper();
        $("#swiperThumbs > button.swiper-button-next.swiper-button-disabled").prop("classList","swiper-button-next swiper-button");

   });

};

function go_join_page(){

    if($("select[name='atribValCd2'] option:selected").data("stdout") == 'Y'){
        MCP.alert("선택하신 용량/색상은 품절 되었습니다.");
        return;
    }

    document.appForm.hndsetModelId.value = $("#hndsetModelId").val();
    document.appForm.prodCtgType.value = prodCtgType;
    // document.appForm.rateCd.value = rateCd;
    document.appForm.submit();

}

//용량 색상 정보 세팅
var initAtribArea = function(){

    var frstVlCd = '';
    var frstRadCd = '';
    var volumeCd = '';
    var valCd = new Array;
    var valNm = new Array;
    var dataList = new Array;
    var sdoutYnArr = new Array;
    if(initPhoneData !=null){

        $.each(initPhoneData,function(idx,val){
            var atribValCd2 = initPhoneData[idx].atribValCd2;
            var atribValNm2 = initPhoneData[idx].atribValNm2;
            var sdoutYn = initPhoneData[idx].sdoutYn;
            dataList.push({'atribValCd2':atribValCd2,'sdoutYn':sdoutYn});

            if(idx==0){
                frstVlCd = atribValCd2;
            }
            if(valCd.indexOf(atribValCd2) == -1){
                valCd.push(atribValCd2);
                valNm.push(atribValNm2);
            }
        });

        // 품절 여부 판단.동일 용량의 모든제품이 품절이여야 품절 노출시킴
        // 중복제거 후 컬러배열
        $.each(valCd,function(idx,val){
            var voulnm = valCd[idx];
            var totCnt = 0;
            var soldOutCnt = 0;
            $.each(dataList,function(i,v){ // 용량 등록한 모든데이터

                var atribValCd3 = dataList[i].atribValCd2;
                var sdoutYn1 = dataList[i].sdoutYn;
                if(voulnm==atribValCd3){
                    totCnt++;
                    if(sdoutYn1=="Y"){
                        soldOutCnt++;
                    }
                }
            });

            if(totCnt > 0 && totCnt==soldOutCnt){
                sdoutYnArr.push("[품절]");
            } else {
                sdoutYnArr.push("");
            }
        });
        // 품절 여부 판단.동일 용량의 모든제품이 품절이여야 품절 노출시킴 끝

        for(var i =0; i < valCd.length; i++){
            $("#atribValCd2").append('<option label="'+valNm[i]+sdoutYnArr[i]+'" value="'+valCd[i]+'">'+valNm[i]+'</option>');
        }
    }

    var radANum = 0;
    var radioHtml = '';

    $("#colorGroupDiv").empty();

    for (var colorIdx= 0; colorIdx < initPhoneData.length; colorIdx++) {

        var atribValCd1 = initPhoneData[colorIdx].atribValCd1;
        var atribValCd2 = initPhoneData[colorIdx].atribValCd2;
        var stdOutYn = initPhoneData[colorIdx].sdoutYn;
        var atribValNm1 = initPhoneData[colorIdx].atribValNm1;


        if(frstVlCd == atribValCd2 && stdOutYn != 'Y'){
            if(radANum == 0){
                frstRadCd = atribValCd1;
                selSntyColorCd = atribValCd1;
            }
            radioHtml+='<li>';
            radioHtml+='	<input class="c-radio" name="radGA" id="radGA'+radANum+'" type="radio" value="'+atribValCd1+'" ';

            var rgbData = '#192f41';
            $(colorData).each(function() {
                if(this.dtlCd == atribValCd1){
                    rgbData = this.expnsnStrVal1;
                }
            });

            if(radANum == 0){
                radioHtml+=' checked>';
            }else{
                radioHtml+=' >';
            }

            radioHtml+='	<label class="c-label" for="radGA'+radANum+'" role="presentation" aria-describedby="color_tooltip_radGA'+radANum+'">';
            radioHtml+='		<div class="c-tooltip-box c-tooltip-box--auto" id="color_tooltip_radGA'+radANum+'" role="tooltip">'+atribValNm1+'</div>';
            radioHtml+='		<span style="background-color: '+rgbData+'"></span>';
            radioHtml+='	</label>';
            radioHtml+='</li>';

            radANum++;
        }
    }

    $("#colorGroupDiv").html(radioHtml);

    findHndsetModelId(frstRadCd,frstVlCd);

}

//용량 색상 정보 세팅
var initVolumeArea = function(){

    $("#atribValCd2").html("");
    var volumeCd = '';

    for (var dataIdx= 0; dataIdx < initPhoneData.length; dataIdx++) {

        var atribValCd2 = initPhoneData[dataIdx].atribValCd2;
        var atribValNm2 = initPhoneData[dataIdx].atribValNm2;
        if(dataIdx == 0){
            frstVlCd = atribValCd2;
        }
        if(volumeCd != atribValCd2){
            $("#atribValCd2").append('<option value="'+atribValCd2+'">'+atribValNm2+'</option>');
            volumeCd = atribValCd2
        }

    }

}

//용량 색상 정보 세팅
var initColorArea = function(selVlCd){

    var radANum = 0;
    var radioHtml = '';

    $("#colorGroupDiv").empty();
    if(initPhoneData !=null){

        $.each(initPhoneData,function(idx,val){
            var atribValCd1 = initPhoneData[idx].atribValCd1
            var atribValCd2 = initPhoneData[idx].atribValCd2
            var stdOutYn = initPhoneData[idx].sdoutYn;
            var atribValNm1 = initPhoneData[idx].atribValNm1;

            if(atribValCd2 == selVlCd && stdOutYn != 'Y'){

                radioHtml+='<li>';
                radioHtml+='	<input class="c-radio" name="radGA" id="radGA'+radANum+'" type="radio" value="'+atribValCd1+'" ';

                var rgbData = '#192f41';
                $(colorData).each(function() {
                    if(this.dtlCd == atribValCd1){
                        rgbData = this.expnsnStrVal1;
                    }
                });

                if(radANum == 0){
                    radioHtml+=' checked>';
                }else{
                    radioHtml+=' >';
                }
                radioHtml+='	<label class="c-label" for="radGA'+radANum+'" role="presentation" aria-describedby="color_tooltip_radGA'+radANum+'">';
                radioHtml+='		<div class="c-tooltip-box c-tooltip-box--auto" id="color_tooltip_radGA'+radANum+'" role="tooltip">'+atribValNm1+'</div>';
                radioHtml+='		<span style="background-color: '+rgbData+'"></span>';
                radioHtml+='	</label>';
                radioHtml+='</li>';
                radANum++;
            }
        });
    }

    $("#colorGroupDiv").html(radioHtml);

}

var findHndsetModelId = function(atribValCd1, atribValCd2){
    for (var dataIdx= 0; dataIdx < initPhoneData.length; dataIdx++) {

        if(atribValCd1 == initPhoneData[dataIdx].atribValCd1 && atribValCd2 == initPhoneData[dataIdx].atribValCd2){
            $("#hndsetModelId").val(initPhoneData[dataIdx].hndsetModelId);
            break;
        }
    }
}

function goSubmit(pageNo){
    initreViewData(pageNo);
}

var initreViewData = function(pageNo){
    var eventCd = '';
    var varData = ajaxCommon.getSerializedData({
        pageNo : pageNo
        ,eventCd : eventCd
        , onlyMine : onlyMine
        , prodId : $("#fprodId").val()
        , recordCount : 10
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
        var pageInfoBean = jsonObj.pageInfoBean;
        viewDesc(total,requestReviewDtoList, conNumList, isLogin);
        if(total > 10){
            initPagingHtml(pageInfoBean);
            $(".c-paging").show();
        }else{
            $(".c-paging").hide();
        }
        // 후기 펼침 토글 클릭 이벤트(조회수)
        $(".toggle-button").on("click", function(){
            if($(this).hasClass("is-active")){
                increaseHit($(this).attr("id"));
            }
        });

    });

}



var viewDesc = function(total,requestReviewDtoList, conNumList, isLogin){
    $("#nodata").remove();

    var htmlArea = "";
    if((!conNumList || conNumList.length == 0)&& onlyMine){
            htmlArea += "<div class='c-nodata' id='nodata'>" +
                  "<i class='c-icon c-icon--nodata' aria-hidden='true'></i>" +
                  "<p class='c-nodata__text'>조회 결과가 없습니다.</p>" +
                "</div>";
                $("#reviewBody").prepend(htmlArea);
    }else{


        if(requestReviewDtoList !=null && requestReviewDtoList.length>0){
            $("#phoneViewReviewNodataDiv").hide();
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
            var reviewQuestionList = "";

            for( var i=0; i<requestReviewDtoList.length; i++ ){

                reviewImgList = requestReviewDtoList[i].reviewImgList;
                regNm = requestReviewDtoList[i].mkRegNm;
                reqBuyTypeNm = requestReviewDtoList[i].reqBuyTypeNm;
                eventCd = requestReviewDtoList[i].eventCd;
                eventNm = requestReviewDtoList[i].eventNm;
                sysRdateDd = requestReviewDtoList[i].sysRdateDd;
                reviewSbst = requestReviewDtoList[i].reviewSbst;
                snsInfo = ajaxCommon.isNullNvl(requestReviewDtoList[i].snsInfo,"");
                commendYn = requestReviewDtoList[i].commendYn;
                ntfYn = requestReviewDtoList[i].ntfYn;
                contractNum = requestReviewDtoList[i].contractNum;
                ranking = requestReviewDtoList[i].ranking;
                requestKey = requestReviewDtoList[i].requestKey;
                reviewQuestionList = requestReviewDtoList[i].reviewQuestionList;


                    htmlArea += "<li class='c-accordion__item'>";
                    htmlArea += '<div class="c-accordion__head">';
                    htmlArea += "<div class='review__label-wrap'>";
                    if(commendYn =="1"){
                        if(ranking < 4){
                            if(ntfYn == 1){
                                htmlArea += "<span class='c-text-label c-text-label--mint-type2'>추천</span>";
                                htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                            }else{
                                htmlArea += "<span class='c-text-label c-text-label--mint-type2'>추천</span>";
                            }
                        }else{
                            if(ntfYn == 1){
                                htmlArea += "<span class='c-text-label c-text-label--mint-type2'>추천</span>";
                                htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                            }else{
                                htmlArea += "<span class='c-text-label c-text-label--mint-type1'>추천</span>";
                            }
                        }
                    }else{
                        if(ranking < 4){
                            if(ntfYn == 1){
                                htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                                htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                            }else{
                                htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                            }
                        }else{
                            if(ntfYn == 1){
                                htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                                htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                            }else{
                                htmlArea += "<span class='c-text-label c-text-label--gray-type1'>비추천</span>";
                            }
                        }
                    }
                    htmlArea += '</div>';
                    htmlArea += '<ul class="review__info">';
                    htmlArea += '<li class="review__info__item">';
                    htmlArea += "<span class='sr-only'>작성자</span>" + regNm;
                    htmlArea += '</li>';
                    htmlArea += '<li class="review__info__item">';
                    htmlArea += "<span class='sr-only'>작성일</span>" + sysRdateDd;
                    htmlArea += '</li>';
                    htmlArea += "</ul>";
                    htmlArea += '<strong class="review__title">' + reqBuyTypeNm+'/'+eventNm + '</strong>';

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

                    htmlArea += '<button class="runtime-data-insert c-accordion__button" id="acc_header_c1" type="button" aria-expanded="false" aria-controls="acc_content_c'+i+'">';
                    htmlArea += '<span class="c-hidden">' + reqBuyTypeNm+'/'+eventNm + ' 전체보기</span>';
                    htmlArea += '</button>';
                    htmlArea += '</div>';



                    htmlArea += '<div class="c-accordion__panel expand" id="acc_content_c'+i+'" aria-labelledby="acc_header_c'+i+'">';
                    htmlArea += '<div class="c-accordion__inside">';
                    htmlArea += '<div>';
                    htmlArea += '<div class="review__content">';
                    htmlArea += '<!-- 데이터 있을 경우 노출-->';
                    if(reviewImgList !=null && reviewImgList.length>0){
                        htmlArea += '<div class="review__preview">';
                        htmlArea += '<img src="'+reviewImgList[0].filePathNm+'" alt="'+reviewImgList[0].fileAlt+'" title="'+reviewImgList[0].fileAlt+'">';
                        if(reviewImgList.length > 1){
                            htmlArea += '<span class="review__image__label">';
                            htmlArea += '<span class="sr-only">썸네일사진 외 추가로 등록된 후기사진 개수</span>+'+ (reviewImgList.length-1);
                            htmlArea += '</span>';
                        }
                        htmlArea += '</div>';

                    }

                    htmlArea += '<pre><p class="review__text">'+reviewSbst+'</p><pre>';
                    if(snsInfo !=""){
                        if(snsInfo.indexOf("http") > -1){
                            htmlArea += '<a class="review__button" href="'+snsInfo+'" target="_blank" title="새창">SNS 공유 사용후기 보기</a>';
                        } else {
                            htmlArea += '<a class="review__button" href=https://"'+snsInfo+'" target="_blank" title="새창">SNS 공유 사용후기 보기</a>';
                        }
                    }

                    htmlArea += '<ul class="review__images">';

                    if(reviewImgList !=null && reviewImgList.length>0){
                        for(var k = 0; k < reviewImgList.length; k ++){
                            htmlArea += '<li class="review__images__item">';
                            htmlArea += '<div class="review__image">';
                            htmlArea += '<img src="'+ reviewImgList[k].filePathNm +'" alt="'+reviewImgList[k].fileAlt+'" aria-hidden="true">';
                            htmlArea += '</div>';
                            htmlArea += '<p class="c-bullet c-bullet--dot u-co-gray">'+reviewImgList[k].fileAlt+'</p>';
                            htmlArea += '</li>';
                        }
                    }

                    htmlArea += '</ul>';

                    htmlArea += '</div>';
                    htmlArea += '</div>';
                    htmlArea += '</div>';
                    htmlArea += '</div>';
                    htmlArea += '</li>';




                reviewNo ++;
            }
            $("#reviewBoard").html(htmlArea);
            window.KTM.initialize();

        }else{
            $("#phoneViewReviewNodataDiv").show();
        }

    }

    $(".imgTag").each(function(){
        $(this).error(function(){
            $(this).unbind("error");
            $(this).attr("src","/resources/images/requestReview/noimage.png");
        });
    });

}

var initPagingHtml = function(pageInfoBean){
    var totalCount = pageInfoBean.totalCount;
    var currentPage = pageInfoBean.pageNo;
    var countPerPage = pageInfoBean.pageSize;
    var totalPage = parseInt(totalCount / countPerPage);
    if((totalCount % countPerPage) > 0) {
        totalPage = totalPage + 1;
    }
    $(".c-paging").empty();
    var pageSize = 10 ; //페이지 사이즈
    var firstPageNoOnPageList = parseInt((currentPage - 1) / pageSize ) * pageSize + 1;
    var lastPageNoOnPageList = (firstPageNoOnPageList + pageSize - 1);
    if (lastPageNoOnPageList > totalPage) {
        lastPageNoOnPageList = totalPage;
    }

    var pageFirst = '';
    var pageLeft = '';

    if(currentPage > 1 ) {
        if (firstPageNoOnPageList > pageSize) {
            pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
            pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+ (firstPageNoOnPageList-1) +');"><span>이전</span></a>';
        } else {
            pageFirst = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit(1);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
            pageLeft = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(currentPage-1)+');"><span>이전</span></a>';
        }
    } else {
        pageFirst = '<a class="c-button is-disabled" href="javascript:void(0);"><i class="c-icon c-icon--triangle-start" aria-hidden="true"></i><span class="c-hidden">처음</span></a>';
        pageLeft = '<a class="c-button is-disabled" href="javascript:void(0);"><span>이전</span></a>';
    }

    var pageStr = ""
    for (var i = firstPageNoOnPageList; i <= lastPageNoOnPageList; ++i) {
        if (i == currentPage) {
            pageStr += '<a class="c-paging__anchor c-paging__anchor--current c-paging__number" href="javascript:void(0);"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
        } else {
            pageStr += '<a class="c-paging__anchor c-paging__number" href="javascript:void(0);" onclick="goSubmit(' + i + ')"><span class="c-hidden">현재 페이지</span>' + i + '</a>';
        }
    }

    var pageLast = '';
    var pageRight = '';

    if(totalPage > currentPage ){
        if (lastPageNoOnPageList < totalPage) {
            pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(firstPageNoOnPageList + pageSize)+');"><span>다음</span></a>';
            pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
        } else {
            pageRight = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+(currentPage+1)+');"><span>다음</span></a>';
            pageLast = '<a class="c-button" href="javascript:void(0);" onclick="goSubmit('+totalPage+');"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
        }
    } else {
        pageRight = '<a class="c-button is-disabled" href="javascript:void(0);"><span>다음</span></a>';
        pageLast = '<a class="c-button is-disabled" href="javascript:void(0);"><i class="c-icon c-icon--triangle-end" aria-hidden="true"></i><span class="c-hidden">마지막</span></a>';
    }

    var pagingHtml = "";
    pagingHtml += pageFirst;
    pagingHtml += pageLeft;
    pagingHtml += pageStr;
    pagingHtml += pageRight;
    pagingHtml += pageLast;
    $(".c-paging").html(pagingHtml);

}


var initColorData = function(){
    var varData = ajaxCommon.getSerializedData({
        grpCd : 'GD0008'
    });

    ajaxCommon.getItem({
        id:'getComCodeListAjax'
        ,cache:false
        ,url:'/m/getComCodeListAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    },function(data){
        colorData = data.result;

    });

}

function initSwiper(){


    // 활성화 처리
      function activeSelectedClass(slides, index) {
        for (var i = 0; i < slides.length; i++) {
          if (index === i) {
            slides[i].classList.add('is-selected');
          } else {
            slides[i].classList.remove('is-selected');
          }
        }
      }

      function pagerControl() {
        swiper - button - disabled;
      }

      var swiperDetail = detailSwiper();
      var swiperThumbs = thumbSwiper();

      function detailSwiper(containerName) {
        var swiperWrap = document.querySelector('#swiperDetail .swiper-container');
        var prevButton = document.querySelector('#swiperThumbs .swiper-button-prev');
        var nextButton = document.querySelector('#swiperThumbs .swiper-button-next');
        var swiper = null;
        return KTM.swiperA11y(swiperWrap, {
          allowTouchMove: false,
          init: function() {
            swiper = this;
            nextButton.addEventListener('click', function(e) {
              swiper.slideNext();
            });
            prevButton.addEventListener('click', function(e) {
              swiper.slidePrev();
            });
            prevButton.classList.add('swiper-button-disabled');
          },
          on: {
            slideChange: function() {
              if (swiperThumbs) {
                swiperThumbs.slideTo(this.activeIndex);
                activeSelectedClass(swiperThumbs.slides, this.activeIndex);
              }

              prevButton.classList.remove('swiper-button-disabled');
              nextButton.classList.remove('swiper-button-disabled');

              if (swiper.isBeginning) {
                prevButton.classList.add('swiper-button-disabled');
              }
              if (swiper.isEnd) {
                nextButton.classList.add('swiper-button-disabled');
              }
            },
          },
        });
      }
      $('#swiperDetail .swiper-container .swiper-slide').removeAttr('tabindex');

      // 썸네일 스와이퍼
      function thumbSwiper(containerName) {
        var swiperWrap = document.querySelector('#swiperThumbs .swiper-container');
        return new Swiper(swiperWrap, {
          allowTouchMove: false,
          slidesPerView: 'auto',
          slidesPerGroup: 3,
          on: {
            init: function() {
              [].forEach.call(this.slides, function(el, i) {
                /*el.setAttribute('tabindex', -1);*/
                el.setAttribute('aria-hidden', true);
                el.setAttribute('data-index', i);
                el.addEventListener('click', function(e) {
                  e.preventDefault();
                  var currentIndex = e.currentTarget.getAttribute('data-index');
                  if (swiperDetail) {
                    swiperDetail.slideTo(currentIndex, 400);
                  }
                });
              });
              activeSelectedClass(this.slides, 0);
            },
          },
        });
        return;
      }


}

var isEmpty = function(value){
     if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
        return true
    }else{
        return false
    }
};

function flexibleComponent(componentId) {

    ajaxCommon.getItem({
            url: "/termsPop.do",
            type: "GET",
            dataType: "json",
            data: "cdGroupId1=INFOPRMT&cdGroupId2=" + componentId,
            async: false
        }
        ,function(data) {
            if (data.docContent != null) {
                var inHtml = unescapeHtml(data.docContent);
                $('#' + componentId).html(inHtml);
            }
        }
    );
}
