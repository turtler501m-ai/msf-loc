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
        /*
        if ($("select[name='hndsetModelId'] option:selected").data("sdoutYn") == 'Y') {
            alert("선택하신 용량/색상은 품절 되었습니다.");
            $("select[name='hndsetModelId'] option[data-sdoutYn!='Y']").eq(0).prop("selected",true);
            return;
        }
        */
        initColorArea($(this).val());
        callChargeList(true,false);
    });

    $(document).on("change", "input:radio[name='radGA']", function(){

        selSntyColorCd = $(this).val();
        findHndsetModelId($(this).val(),$("#atribValCd2").val());
        callChargeList(true,false);
        $("#colorNm").text($(this).attr("atribValNm1"));
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

    callChargeList(true,true);

});

function goStore(){
    openPage('pullPopup','/m/product/phone/phoneInventoryStoreList.do');
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
            //var colorCd =  $("select[name='hndsetModelId'] option:selected").attr("colorCd");
            //var colorCd =  $("select[name='hndsetModelId'] option:selected").data("colorcd");
            var $slider = $('#main-content > div.phone-detail.c-expand > div > div.swiper-wrapper');
            $slider.empty();
            var sntyColorCdFlag = false;
            for (var imgIdx=0; imgIdx < phoneImgList.length; imgIdx++) {
                if (phoneImgList[imgIdx].sntyColorCd == selSntyColorCd ) {
                    sntyColorCdFlag = true;
                    var imgDetailList = phoneImgList[imgIdx].phoneProdImgDetailDtoList ;
                    for (var i=0; i < imgDetailList.length; i++) {
                        if (imgDetailList[i].imgTypeCd != "04" && !isEmpty(imgDetailList[i].imgPath)) {
                            if (!isEmpty(bgLayoutImg)) {
                                $slider.append("<div class='swiper-slide'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "' />" +
                                    "<img src='" + bgLayoutImg + "' alt='" + fprodNm + "_layout' /></div>");
                            } else {
                                $slider.append("<div class='swiper-slide'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "' /></div>");
                            }

                        }
                    }
                    //$("input:radio[name ='radA']:input[value='"+colorCd+"']").prop("checked", true);
                    break;
                }
             }

             //모든 컬러가 품절인경우..
             if(!sntyColorCdFlag){
                if(phoneImgList && phoneImgList.length > 0){
                    var imgDetailList = phoneImgList[0].phoneProdImgDetailDtoList ;
                        for (var i=0; i < imgDetailList.length; i++) {
                            if (imgDetailList[i].imgTypeCd != "04" && !isEmpty(imgDetailList[i].imgPath)) {
                                $slider.append("<div class='swiper-slide'><img src='" + imgDetailList[i].imgPath + "' alt='" + fprodNm + "_" + imgDetailList[i].imgTypeLabel + "' /></div>");
                            }
                    }

                }
             }

        }

        $("#main-content > div.phone-detail.c-expand > div > div.swiper-wrapper > div > img").each(function(){
        $(this).error(function(){
            $(this).unbind("error");
            $(this).attr("src","/resources/images/mobile/content/img_240_no_phone.png");
            });
        });
        initSwiper();


   });

};

function go_join_page(){

    if($("select[name='atribValCd2'] option:selected").data("stdout") == 'Y'){
        MCP.alert("선택하신 용량/색상은 품절 되었습니다.");
        return;
    }
    document.appForm.hndsetModelId.value = $("#hndsetModelId").val();
    document.appForm.prodCtgType.value = prodCtgType;
    // document.appFrom.rateCd.value = rateCd;
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
            var atribValCd2 = initPhoneData[idx].atribValCd2
            var atribValNm2 = initPhoneData[idx].atribValNm2
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
    $("#colorNm").text("");
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

            radioHtml+='<input class="c-radio" id="radGA'+radANum+'" atribValNm1="'+atribValNm1+'" value="'+atribValCd1+'" ';

            var rgbData = '#192f41';
            $(colorData).each(function() {
                if(this.dtlCd == atribValCd1){
                    rgbData = this.expnsnStrVal1;
                }
            });


            radioHtml+=' type="radio" name="radGA" ';
            if(radANum == 0){
                $("#colorNm").text(atribValNm1);
                radioHtml+=' checked>';
            }else{
                radioHtml+=' >';
            }
            radioHtml+='<label class="c-label" for="radGA'+radANum+'"><span class="c-hidden">'+atribValNm1+'</span><span style="background-color: '+rgbData+'"></span></label>';
            radANum++;
        }
    }

    $("#colorGroupDiv").html(radioHtml);

    findHndsetModelId(frstRadCd,frstVlCd);

}

//용량 색상 정보 세팅
var initVolumeArea = function(){

    var volumeCd = '';
    $("#atribValCd2").html("");
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
    $("#colorNm").text("");
    if(initPhoneData !=null){

        $.each(initPhoneData,function(idx,val){
            var atribValCd1 = initPhoneData[idx].atribValCd1
            var atribValCd2 = initPhoneData[idx].atribValCd2
            var stdOutYn = initPhoneData[idx].sdoutYn
            var atribValNm1 = initPhoneData[idx].atribValNm1;

            if(atribValCd2 == selVlCd && stdOutYn != 'Y'){

                radioHtml+='<input class="c-radio" id="radGA'+radANum+'" atribValNm1="'+atribValNm1+'" value="'+atribValCd1+'" ';

                var rgbData = '#192f41';
                $(colorData).each(function() {
                    if(this.dtlCd == atribValCd1){
                        rgbData = this.expnsnStrVal1;
                    }
                });
                radioHtml+=' type="radio" name="radGA" ';
                if(radANum == 0){
                    $("#colorNm").text(atribValNm1);
                    radioHtml+=' checked>';
                }else{
                    radioHtml+=' >';
                }
                radioHtml+='<label class="c-label" for="radGA'+radANum+'"><span class="c-hidden">'+atribValNm1+'</span><span style="background-color: '+rgbData+'"></span></label>';
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
        ,url:'/m/requestReView/reviewDataAjax.do'
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

        //$("#selEventCd").html("");
        //selectHtml += "<option value=''>전체보기(" + eventTotal + ")</option>";
        //for(var i = 0; i < eventList.length; i ++){
        //	selectHtml += "<option value='" + eventList[i].dtlCd + "'>" + eventList[i].dtlCdNm + "(" + eventList[i].eventCdCtn +")</option>";
        //}
        //$("#selEventCd").append(selectHtml);
        viewDesc(total,requestReviewDtoList, conNumList, isLogin);

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
        $("#moreBtn").hide();
        $("#selEventCd").hide();
        $("#recommendInfo").hide();
            htmlArea += "<div class='c-nodata' id='nodata'>" +
                  "<i class='c-icon c-icon--nodata' aria-hidden='true'></i>" +
                  "<p class='c-nodata__text'>조회 결과가 없습니다.</p>" +
                "</div>";
                $("#reviewBody").prepend(htmlArea);
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


                    htmlArea += "<li class='review__item'>";
                    htmlArea += "<div class='label-wrap'>";
                    if(commendYn =="1"){
                        if(ranking < 4){
                            if(ntfYn == 1){
                                htmlArea += "<span class='c-text-label c-text-label--mint-type1'>추천</span>";
                                htmlArea += "<span class='c-text-label c-text-label--red'>BEST</span>";
                            }else{
                                htmlArea += "<span class='c-text-label c-text-label--mint-type1'>추천</span>";
                            }
                        }else{
                            if(ntfYn == 1){
                                htmlArea += "<span class='c-text-label c-text-label--mint-type1'>추천</span>";
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
                    /*
                    if(isLogin){
                        for(var j = 0; j < conNumList.length; j ++){
                            if(conNumList[j] == contractNum){
                                htmlArea += "<span class='c-text-label c-text-label--gray-type1 delete-button-in' id='" + requestKey + "' contractNum='" + contractNum +"' style='float:right;'>삭제</span>";
                            }
                        }
                    }else{
                        htmlArea += "<span class='c-text-label c-text-label--gray-type1 delete-button-out' id='" + requestKey + "' contractNum='" + contractNum +"' style='float:right;'>삭제</span>";
                    }
                    */
                    htmlArea += "</div>";
                    htmlArea += "<h3 class='review__title'>" + reqBuyTypeNm+"/"+eventNm + "</h3>";
                    htmlArea += "<div class='review__info'>";


                    if (regNm != "") {
                        htmlArea += "<span class='review__user'>" + regNm+"</span>";
                        htmlArea += "<span class='review__date'>" + sysRdateDd + "</span>";
                    } else {
                       htmlArea += "<span class='review__user'>" + sysRdateDd + "</span>";
                    }

                    htmlArea += "</div>";

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

                    htmlArea += "<div class='review__content fs-13 c-text-ellipsis--type3' id='content_" + reviewNo + "'>";
                    htmlArea += "<pre>"+reviewSbst+"<pre>";
                    if(snsInfo !=""){
                        htmlArea += "			<p class='u-mt--8'>";
                        if(snsInfo.indexOf("http") > -1){

                            if(platFormCd == 'A'){
                                htmlArea += "		<a class='review__button'  href=\"javascript:appOutSideOpen('"+snsInfo+"');\" class='snslink'>SNS 공유 사용후기 보기</a></p>";
                            }else{
                                htmlArea += "		<a class='review__button'  href='"+snsInfo+"' target='_blank' class='snslink'>SNS 공유 사용후기 보기</a></p>";
                            }


                        } else {

                            if(platFormCd == 'A'){
                                htmlArea += "		<a class='review__button'  href=\"javascript:appOutSideOpen('https://"+snsInfo+"');\" class='snslink'>SNS 공유 사용후기 보기</a></p>";
                            }else{
                                htmlArea += "		<a class='review__button' href='https://"+snsInfo+"' target='_blank' class='snslink'>SNS 공유 사용후기 보기</a></p>";
                            }



                        }
                    }
                    htmlArea += "		</div>";

                    if(reviewImgList !=null && reviewImgList.length>0){
                        htmlArea += "<div class='review__image' id='content_img_" + reviewNo + "'>";
                        if(reviewImgList.length > 1){
                            htmlArea += "<span class='c-text-label c-text-label--darkgray img-count'>+"+ (reviewImgList.length-1) +"</span>"
                        }
                        for(var k = 0; k < reviewImgList.length; k ++){
                            htmlArea += '<div class="review__image__item">';
                            htmlArea += "<img src='" + reviewImgList[k].filePathNm + "' alt='" + reviewImgList[k].filaAlt + "' onerror='this.src=\"/resources/images/mobile/content/img_review_noimage.png\"'>";
                            htmlArea += '</div>';
                        }
                        htmlArea += "</div>";
                    }

                    if(reviewImgList !=null && reviewImgList.length>0){
                        htmlArea += "<div class='review__button-wrap' id='" + requestKey + "' data-toggle-class='is-active' data-toggle-target='#content_img_" + reviewNo + "|#content_" + reviewNo + "'>";
                    }else{
                        htmlArea += "<div class='review__button-wrap' id='" + requestKey + "' data-toggle-class='is-active' data-toggle-target='#content_" + reviewNo + "'>";
                    }
                    htmlArea += "<button type='button' aria-expanded='false'>";
                    htmlArea += "<i class='c-icon c-icon--arrow-down-bold' aria-hidden='true'></i>";
                    htmlArea += "</button>";
                    htmlArea += "</div>";
                      htmlArea += "</li>";


                reviewNo ++;
            }
            // append
            $("#reviewBoard").append(htmlArea);
            $("#moreBtn").show();
            $("#selEventCd").show();
            $("#recommendInfo").show();
            window.KTM.initialize();
        } else {
            $("#moreBtn").hide();
            $("#selEventCd").hide();
            $("#recommendInfo").hide();
            htmlArea += "<div class='c-nodata' id='nodata'>" +
                  "<i class='c-icon c-icon--nodata' aria-hidden='true'></i>" +
                  "<p class='c-nodata__text'>조회 결과가 없습니다.</p>" +
                "</div>";
                $("#reviewBody").prepend(htmlArea);
        }

    }

    // 더보기
    var reViewCurrent = $(".review__item").length;

    $("#reViewCurrent").text(reViewCurrent); // 현재 노출
    $("#reViewTotal").text(total);
    if(reViewCurrent == total){$("#moreBtn").hide();}
    $(".imgTag").each(function(){
        $(this).error(function(){
            $(this).unbind("error");
            $(this).attr("src","/resources/images/requestReview/noimage.png");
        });
    });

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

var swipeGuideWrap = new Swiper('#phoneSwiper', {
              navigation: {
                nextEl: '.swiper-next',
                prevEl: '.swiper-prev',
              },
              pagination: {
                el: '.swiper-pagination',
              },
            });

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