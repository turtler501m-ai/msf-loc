;

var path = "${pageContext.request.contextPath}";
let ansNo = "";

var pageObj = {
    rateScndCtgList:[]
    ,rateListMap:{}
    , usimListCnt:1
}


$(document).ready(function(){


    try {
        // 메인 상단 빅배너 조회 실행
        displayM001BannerList();
        // 메인 주요메뉴 실행
        displayM002BannerList();
        // 메인 시선집중(1) 실행
        displayM003BannerList();
        // 메인 시선집중(2) 실행
        displayM004BannerList();
        // 메인 혜택안내 실행
        displayM005BannerList();
        // 메인 시선집중(3) 실행
        displayM006BannerList();
        // 메인 퀵메뉴 실행
        displayM007BannerList();
        // 메인 상단 띠배너 실행
        displayM008BannerList();

        setTimeout(function(){}, 300);

        // 유심요금제 추천 실행
        displayInqr();
        // 사용후기 조회 실행
        displayRequestReviewList();
        // 공지 조회 실행
        displayNotiList();
        // 유심요금제 조회 실행
        displayRateList();
        // 휴대폰요금제 조회 실행
        displayPhoneList();

        // 팝업 : 메인(배너모아보기) 실행
        displayMB01BannerList();



    } catch(error) {
        //alert(error.message) ;
    }
    setInterval(function(){
        if($(".c-loader--circle").length > 0){
            KTM.LoadingSpinner.hide();
        }
    }, 2000);
    /*history.pushState(null, null,"/m/main.do");
    window.onpopstate = function (event){
        history.go("/m/main.do");
    }*/

    $(document).on('click', '.c-radio--button', function () {
        var checkVal = [];
        $("input[name^=cho]:checked").each(function () {
            checkVal.push($(this).val());
        });
        $('.c-button--next' + checkVal.length).trigger("click");
    });

    $(document).on('click', '.reset', function () {
        $("input[name^=cho]:checked").each(function () {
            // $(this).checked = false;
            $(this).context.checked = false;
        });
    })

});

// 메인 상단 빅배너 조회 (M001)
var displayM001BannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'M001'
    });

    ajaxCommon.getItem({
            id : 'getM001BannerListAjax'
            , cache : false
            , async : true
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let bannerList = result.result;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannDesc = ajaxCommon.isNullNvl(value.bannDesc, "");
                        let v_bannApdDesc = ajaxCommon.isNullNvl(value.bannApdDesc, "");
                        let v_bannBgColor = ajaxCommon.isNullNvl(value.bgColor, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        let v_textColor1 = ajaxCommon.isNullNvl(value.textColor1, "");
                        let v_textColor2 = ajaxCommon.isNullNvl(value.textColor2, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_mobileLinkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide' data-background='"+v_bannBgColor+"'>";
                        bannerHtml += "    <a href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        if(v_bannDesc != "") {
                            bannerHtml += "        <p class='event-date' style='color: "+v_textColor1+"'>"+v_bannDesc+"</p>";
                        }

                        bannerHtml += "    </a>";
                        bannerHtml += "</div>";

                        if(index == 19) {
                            return false;
                        }
                    });
                    $("#sec_M001").show();
                    $("#M001").html(bannerHtml);
                    document.querySelector(".main-swiper .swiper-container").swiper.update();
                } else {
                    $("#sec_M001").hide();
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 메인 주요메뉴 (M002)
var displayM002BannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'M002'
    });

    ajaxCommon.getItem({
            id : 'getM002BannerListAjax'
            , cache : false
            , async : true
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let bannerList = result.result;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannDesc = ajaxCommon.isNullNvl(value.bannDesc, "");
                        let v_bannApdDesc = ajaxCommon.isNullNvl(value.bannApdDesc, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_mobileLinkUrl
                            , linkTarget : v_linkTarget
                        };

                        if(index % 2 == 0) {
                            bannerHtml += "<div class='c-item-col'>";
                        }

                        bannerHtml += "<a href ='javascript:;' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "    <div class='c-box c-box--circle c-box-gray'>";
                        if(v_bannDesc != "") {
                            bannerHtml += "        <div class='c-balloon c-balloon--center c-balloon--sm'>"+v_bannDesc+"</div>";
                        }
                        bannerHtml += "		   <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "    </div>";
                        bannerHtml += "    <span class='c-text c-text--type6 c-in--title'>"+v_bannNm+"</span>";
                        bannerHtml += "</a>";

                        if(index % 2 == 1) {
                            bannerHtml += "</div>";
                        }

                        if(index == 98) {
                            return false;
                        }
                    });
                    $("#sec_M002").show();
                    $("#M002").html(bannerHtml);
                } else {
                    $("#sec_M002").hide();
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 메인 시선집중(1) (M003)
var displayM003BannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'M003'
    });

    ajaxCommon.getItem({
            id : 'getM003BannerListAjax'
            , cache : false
            , async : true
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let bannerList = result.result;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_mobileLinkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide'>";
                        bannerHtml += "    <a href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</div>";

                        if(index == 2) {
                            return false;
                        }
                    });
                    $("#sec_M003").show();
                    $("#M003").html(bannerHtml);
                    window.mainFunctions.aniBannerSwiper1();
                } else {
                    $("#sec_M003").hide();
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 메인 시선집중(2) (M004)
var displayM004BannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'M004'
    });

    ajaxCommon.getItem({
            id : 'getM004BannerListAjax'
            , cache : false
            , async : true
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let bannerList = result.result;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_mobileLinkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide'>";
                        bannerHtml += "    <a href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</div>";

                        if(index == 2) {
                            return false;
                        }
                    });
                    $("#sec_M004").show();
                    $("#M004").html(bannerHtml);
                    window.mainFunctions.aniBannerSwiper2();
                } else {
                    $("#sec_M004").hide();
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 메인 혜택안내 (M005)
var displayM005BannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'M005'
    });

    ajaxCommon.getItem({
            id : 'getM005BannerListAjax'
            , cache : false
            , async : true
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let bannerList = result.result;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_mobileLinkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<li>";
                        bannerHtml += "    <a href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</li>";

                        if(index == 98) {
                            return false;
                        }
                    });
                    $("#sec_M005").show();
                    $("#M005").html(bannerHtml);

                } else {
                    $("#sec_M005").hide();
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 메인 시선집중(3) (M006)
var displayM006BannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'M006'
    });

    ajaxCommon.getItem({
            id : 'getM006BannerListAjax'
            , cache : false
            , async : true
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let bannerList = result.result;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_mobileLinkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide'>";
                        bannerHtml += "    <a href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</div>";

                        if(index == 14) {
                            return false;
                        }
                    });
                    $("#sec_M006").show();
                    $("#M006").html(bannerHtml);
                    window.mainFunctions.aniBannerSwiper3();
                } else {
                    $("#sec_M006").hide();
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 메인 퀵메뉴 (M007)
var displayM007BannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'M007'
    });

    ajaxCommon.getItem({
            id : 'getM007BannerListAjax'
            , cache : false
            , async : true
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let bannerList = result.result;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_mobileLinkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<li>";
                        bannerHtml += "    <a href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "        <strong><img src='"+v_bannImg+"' alt='"+v_imgDesc+"'></strong>";
                        bannerHtml += "		   <span>"+v_bannNm+"</span>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</li>";

                        if(index == 3) {
                            return false;
                        }
                    });
                    $("#sec_M007").show();
                    $("#M007").html(bannerHtml);
                } else {
                    $("#sec_M007").hide();
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 메인 상단 띠배너 (M008)
var displayM008BannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'M008'
    });

    ajaxCommon.getItem({
            id : 'getM008BannerListAjax'
            , cache : false
            , async : true
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let bannerList = result.result;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_bannBgColor = ajaxCommon.isNullNvl(value.bgColor, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide'>";
                        bannerHtml += "    <a class='swiper-top-banner__item' href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</div>";

                        if(index == 98) {
                            return false;
                        }
                    });
                    $("#sec_M008").show();
                    $("#M008").html(bannerHtml);
                    document.querySelector(".top-banner-swiper").swiper.update();
                } else {
                    $("#sec_M008").hide();
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 팝업 : 메인(배너모아보기) (MB01)
var displayMB01BannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'MB01'
    });

    ajaxCommon.getItem({
            id : 'getMB01BannerListAjax'
            , cache : false
            , async : true
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let bannerList = result.result;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_mobileLinkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<a href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");' class='u-block u-mt--20'>";
                        bannerHtml += "    <div class='u-img-full' data-move-fadein='data-move-fadein'>";
                        bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "    </div>";
                        bannerHtml += "</a>";

                        if(index == 98) {
                            return false;
                        }
                    });

                    $("#MB01").html(bannerHtml);
                }
            } else {
                MCP.alert(message);
            }
        });
}


// 공지 조회
var displayNotiList = function() {
    var varData = ajaxCommon.getSerializedData({
        boardCtgSeq : 51
    });

    ajaxCommon.getItem({
            id : 'boardListAjax'
            , cache : false
            , async : true
            , url : '/m/boardListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let popupHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let workNotiList = result.result;

            if(returnCode == "00") {
                if(workNotiList.length > 0) {
                    $.each(workNotiList, function(index, value) {
                        let v_boardSeq = ajaxCommon.isNullNvl(value.boardSeq, "");
                        let v_boardSubject = ajaxCommon.isNullNvl(value.boardSubject, "");
                        let v_url = "/m/cs/boardView.do?backStep=1&boardSeq="+v_boardSeq;

                        $("#workNoti").attr("href", v_url);
                        $("#workNotiSpan").html(v_boardSubject);

                        if(index == 0) {
                            return false;
                        }
                    });
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 요금제추천 질문 표시
var displayInqr = function() {
    //var varData = ajaxCommon.getSerializedData({
    //});

    ajaxCommon.getItem({
            id : 'rateRecommendInqrBasListAjax'
            , cache : false
            , async : true
            , url : '/m/rateRecommendInqrBasListAjax.do'
            //, data : varData
            , dataType : "json"
        }
        ,function(result){
            let inqrHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let inqrBasList = result.result;
            let inqrBasLength = inqrBasList.length;

            if(returnCode == "00") {
                if(inqrBasList.length > 0) {
                    let i = 0;
                    $.each(inqrBasList, function(index, value) {
                        let v_val = 1;
                        let v_rateRecommendInqrSbst = ajaxCommon.isNullNvl(value.rateRecommendInqrSbst, "");
                        let v_ansSbst1 = ajaxCommon.isNullNvl(value.ansSbst1, "");
                        let v_ansSbst2 = ajaxCommon.isNullNvl(value.ansSbst2, "");
                        let v_ansSbst3 = ajaxCommon.isNullNvl(value.ansSbst3, "");
                        let v_ansSbst4 = ajaxCommon.isNullNvl(value.ansSbst4, "");

                        inqrHtml += "<div class='swiper-slide'>";
                        inqrHtml += "	<div class='survey-list__item'>";
                        inqrHtml += "		<p class='c-text c-text--type3 u-fw--medium u-mb--26'>"+v_rateRecommendInqrSbst+"</p>";
                        inqrHtml += "		<div class='c-check-wrap'>";
                        if(v_ansSbst1 != "") {
                            inqrHtml += "			<div class='c-chk-wrap'>";
                            inqrHtml += "				<input type='radio' name='cho"+index+"' value='"+v_val+"' class='c-radio c-radio--button center' id='r"+i+"'>";
                            inqrHtml += "				<label class='c-label' for='r"+i+"'>"+v_ansSbst1+"</label>";
                            inqrHtml += "			</div>";
                            i += 1;
                            v_val += 1;
                        }
                        if(v_ansSbst2 != "") {
                            inqrHtml += "			<div class='c-chk-wrap'>";
                            inqrHtml += "				<input type='radio' name='cho"+index+"' value='"+v_val+"' class='c-radio c-radio--button center' id='r"+i+"'>";
                            inqrHtml += "				<label class='c-label' for='r"+i+"'>"+v_ansSbst2+"</label>";
                            inqrHtml += "			</div>";
                            i += 1;
                            v_val += 1;
                        }
                        if(v_ansSbst3 != "") {
                            inqrHtml += "			<div class='c-chk-wrap'>";
                            inqrHtml += "				<input type='radio' name='cho"+index+"' value='"+v_val+"' class='c-radio c-radio--button center' id='r"+i+"'>";
                            inqrHtml += "				<label class='c-label' for='r"+i+"'>"+v_ansSbst3+"</label>";
                            inqrHtml += "			</div>";
                            i += 1;
                            v_val += 1;
                        }
                        if(v_ansSbst4 != "") {
                            inqrHtml += "			<div class='c-chk-wrap'>";
                            inqrHtml += "				<input type='radio' name='cho"+index+"' value='"+v_val+"' class='c-radio c-radio--button center' id='r"+i+"'>";
                            inqrHtml += "				<label class='c-label' for='r"+i+"'>"+v_ansSbst4+"</label>";
                            inqrHtml += "			</div>";
                            i += 1;
                            v_val += 1;
                        }
                        inqrHtml += "		</div>";
                        inqrHtml += "		<div class='c-chk-wrap u-mt--16 c-hidden'>";

                        if(inqrBasLength == (index+1)) {
                            inqrHtml += "		<button class='c-button c-button--full c-button--white c-button--next2 u-bc--black' data-next-btn='result'>결과확인</button>";
                        } else {
                            inqrHtml += "		<button class='c-button c-button--full c-button--white c-button--next1 u-bc--black' data-next-btn>다음</button>";
                        }
                        inqrHtml += "		</div>";
                        inqrHtml += "	</div>";
                        inqrHtml += "</div>";

                        if(index == 3) {
                            return false;
                        }
                    });

                    inqrHtml += "<div class='swiper-slide'>";
                    inqrHtml += "	<div class='survey-list__item'>";
                    inqrHtml += "		<div class='survey__success swiper-container'>";
                    inqrHtml += "			<div id='inqrRel' class='swiper-wrapper'>";

                    inqrHtml += "			</div>";
                    inqrHtml += "		</div>";
                    inqrHtml += "		<div class='swiper-pagination'></div>";
                    inqrHtml += "		<div class='c-chk-wrap u-mt--56'>";
                    inqrHtml += "			<button class='reset c-button c-button--full c-button--white u-bc--black' data-next-btn='reset'>다시하기</button>";
                    inqrHtml += "		</div>";
                    inqrHtml += "	</div>";
                    inqrHtml += "</div>";

                    $("#sec_survey").show();
                    $("#inqrBasList").html(inqrHtml);
                    window.mainFunctions.surveyBox();
                }

                $("input[name^=cho]:checked").each(function () {
                    $(this).checked = false;
                });
            } else {
                MCP.alert(message);
            }
        });
}

// 요금제추천 값 검증
var ansNoValidate = function(ele) {
    let v_chkedAnsNo = $(ele).parent().parent().find("input[name^='cho']:checked").val();
    return v_chkedAnsNo;
}

// 요금제추천 결과 표시
var getResultInqr = function(ele) {

    ansNoValidate(ele);

    var varData = ajaxCommon.getSerializedData({
        ansNo : ansNo
    });

    ajaxCommon.getItem({
            id : 'rateRecommendInqrRelAjax'
            , cache : false
            , async : true
            , url : '/m/rateRecommendInqrRelAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let inqrRelHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            //let inqrResult = result.result;
            let inqrResultList = result.result;

            $("#inqrRel").empty();

            if(returnCode == "00") {
                if(inqrResultList.length > 0) {
                    $.each(inqrResultList, function(index, value) {
                        let v_rateAdsvcNm = ajaxCommon.isNullNvl(value.rateAdsvcNm, "");
                        let v_rateCd = ajaxCommon.isNullNvl(value.rateCd, "");
                        let v_rateAdsvcBasDesc = ajaxCommon.isNullNvl(value.rateAdsvcBasDesc, "");
                        let v_bnfitData = ajaxCommon.isNullNvl(value.bnfitData, "");
                        let v_bnfitVoice = ajaxCommon.isNullNvl(value.bnfitVoice, "");
                        let v_bnfitSms = ajaxCommon.isNullNvl(value.bnfitSms, "");
                        let v_promotionBnfitData = ajaxCommon.isNullNvl(value.promotionBnfitData, "");
                        let v_promotionBnfitVoice = ajaxCommon.isNullNvl(value.promotionBnfitVoice, "");
                        let v_promotionBnfitSms = ajaxCommon.isNullNvl(value.promotionBnfitSms, "");
                        let v_mmBasAmtVatDesc = ajaxCommon.isNullNvl(value.mmBasAmtVatDesc, "");
                        let v_promotionAmtVatDesc = ajaxCommon.isNullNvl(value.promotionAmtVatDesc, "");
                        let v_recommendYn = ajaxCommon.isNullNvl(value.recommendYn, "");

                        if(v_promotionBnfitData != "") {
                            v_bnfitData = v_bnfitData + "(" + v_promotionBnfitData + ")";
                        }
                        if(v_promotionBnfitVoice != "") {
                            v_bnfitVoice = v_bnfitVoice + "(" + v_promotionBnfitVoice + ")";
                        }
                        if(v_promotionBnfitSms != "") {
                            v_bnfitSms = v_bnfitSms + "(" + v_promotionBnfitSms + ")";
                        }
                        inqrRelHtml += "<div class='swiper-slide'>";
                        inqrRelHtml += "    <div class='c-main-card c-main-card--type1'>";
                        inqrRelHtml += "		<a class='c-main-card__anchor' href='/m/appForm/appFormDesignView.do?onOffType=0&operType=MNP3&indcOdrg=1&prdtSctnCd=LTE&prdtId=K7006048&rateCd="+v_rateCd+"'>";
                        //inqrRelHtml += "        <a class='c-main-card__anchor' href='#'>";
                        inqrRelHtml += "            <div class='c-main-card__panel'>";
                        inqrRelHtml += "                <strong class='c-main-card__title'>"+v_rateAdsvcNm+"</strong>";
                        inqrRelHtml += "                <p class='c-main-card__text'>"+v_rateAdsvcBasDesc+"</p>";
                        inqrRelHtml += "                <ul class='c-main-card__info'>";
                        inqrRelHtml += "                    <li class='c-main-card__info-list'>";
                        inqrRelHtml += "                        <div class='c-main-card__prefix'>";
                        inqrRelHtml += "                            <i class='c-icon c-icon--data' aria-hidden='true'></i>";
                        inqrRelHtml += "                            <span class='c-hidden'>데이터</span>";
                        inqrRelHtml += "                        </div>";
                        inqrRelHtml += "                        <div class='c-main-card__sub'>"+v_bnfitData+"</div>";
                        inqrRelHtml += "                    </li>";
                        inqrRelHtml += "                    <li class='c-main-card__info-list'>";
                        inqrRelHtml += "                        <div class='c-main-card__prefix'>";
                        inqrRelHtml += "                            <i class='c-icon c-icon--call-gray' aria-hidden='true'></i>";
                        inqrRelHtml += "                            <span class='c-hidden'>음성</span>";
                        inqrRelHtml += "                        </div>";
                        inqrRelHtml += "                        <div class='c-main-card__sub'>"+v_bnfitVoice+"</div>";
                        inqrRelHtml += "                    </li>";
                        inqrRelHtml += "                    <li class='c-main-card__info-list'>";
                        inqrRelHtml += "                        <div class='c-main-card__prefix'>";
                        inqrRelHtml += "                            <i class='c-icon c-icon--msg' aria-hidden='true'></i>";
                        inqrRelHtml += "                            <span class='c-hidden'>문자</span>";
                        inqrRelHtml += "                        </div>";
                        inqrRelHtml += "                        <div class='c-main-card__sub'>"+v_bnfitSms+"</div>";
                        inqrRelHtml += "                    </li>";
                        inqrRelHtml += "                </ul>";
                        inqrRelHtml += "                <div class='c-main-card__price'>";
                        inqrRelHtml += "                    <span class='u-td-line-through'>";
                        if(v_mmBasAmtVatDesc != "" && v_promotionAmtVatDesc != "") {
                            inqrRelHtml += "                        <span class='c-hidden'>기본요금 월</span>"+v_mmBasAmtVatDesc+" 원";
                        }
                        inqrRelHtml += "                    </span>";
                        if(v_mmBasAmtVatDesc != "" && v_promotionAmtVatDesc != "") {
                            inqrRelHtml += "                    <span class='c-text c-text--type3'>월<b>"+v_promotionAmtVatDesc+"</b>원</span>";
                        } else {
                            inqrRelHtml += "                    <span class='c-text c-text--type3'>월<b>"+v_mmBasAmtVatDesc+"</b>원</span>";
                        }
                        if(v_recommendYn == "Y") {
                            inqrRelHtml += "                    <span class='c-main-card__label c-main-card__label--balloon'>";
                            inqrRelHtml += "                        <i class='c-icon c-icon--card-balloon' aria-hidden='true'></i>";
                            inqrRelHtml += "                        <span class='c-hidden'>추천</span>";
                            inqrRelHtml += "                    </span>";
                        }
                        inqrRelHtml += "                </div>";
                        inqrRelHtml += "            </div>";
                        inqrRelHtml += "        </a>";
                        inqrRelHtml += "    </div>";
                        inqrRelHtml += "</div>";

                        $("#inqrRel").html(inqrRelHtml);
                        KTM.resizeDispatcher();
                    });
                }

                // 초기화
                $("input[name^=cho]:checked").each(function () {
                    $(this).check = false;
                });
            } else {
                MCP.alert(message);
            }
        });
}

// 유심요금제 목록 조회
var displayRateList = function() {
    var varData = ajaxCommon.getSerializedData({
        upRecommendProdCtgCd : 'REC0000001'
        ,prodDivCd:'02'
    });

    ajaxCommon.getItem({
            id : 'rateScndList'
            , cache : false
            , async : true
            , url : '/m/rateScndList.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let cardCnt = 0;
            let rateScndCtgHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let rateScndCtgList = result.result;

            if(returnCode == "00") {
                pageObj.rateScndCtgList = rateScndCtgList;
                if(rateScndCtgList.length > 0) {
                    $("#sec_rate").show();
                    showRateList();
                }
            } else {
                MCP.alert(message);
            }
        });
}


var showRateList = function() {
    let stIndex =  (pageObj.usimListCnt -1) * 5;
    let enIndex = (pageObj.usimListCnt ) * 5;
    if (enIndex > pageObj.rateScndCtgList.length ) {
        enIndex =  pageObj.rateScndCtgList.length;
    }



    for (let index = stIndex; index < enIndex; index++) {
        var rateScndCtgObj = pageObj.rateScndCtgList[index];
        var arr = [];

        arr.push(`<li class="main-usim__item">`);
        if ("Y" == rateScndCtgObj.linkUseYn) {
            arr.push(`    <a  href="${rateScndCtgObj.linkUrlMo}"  >`);
        } else {
            arr.push(`    <a class="_rateCtgView" href="javascript:void(0);"  data-recommend-prod-ctg-cd="${rateScndCtgObj.recommendProdCtgCd}" >`);
        }

        arr.push(`        <div class="main-usim__img">`);
        if (rateScndCtgObj.recommendProdCtgImgPath != "") {
            arr.push(`            <img src="${rateScndCtgObj.recommendProdCtgImgPath}" alt="">`);
        }
        arr.push(`        </div>`);
        arr.push(`        <div class="main-usim__info">`);
        arr.push(`            <strong class="main-usim__info-title">${rateScndCtgObj.recommendProdCtgNm}</strong>`);
        arr.push(`            <p class="main-usim__info-sub">${rateScndCtgObj.recommendProdCtgBasDesc}</p>`);
        arr.push(`        </div>`);
        arr.push(`    </a>`);
        arr.push(`</li>`);

        pageObj.rateListMap[rateScndCtgObj.recommendProdCtgCd] = rateScndCtgObj.lists;

        $("#ulRateCtgList").append(arr.join(''));
    }

    let itemLength = $("#ulRateCtgList > li.main-usim__item").length;
    if (itemLength >= pageObj.rateScndCtgList.length ) {
        $("#btnMoreUsimList").hide();
    } else {
        $(this).prop("disabled", false);
        $("#btnMoreUsimList").show();
    }
}

$('#btnMoreUsimList').click(function () {
    $(this).prop("disabled", true);
    pageObj.usimListCnt++;
    showRateList ();
});

$(document).on('click', '._rateCtgView', function(event) {
    let recommendProdCtgCd = $(this).data("recommendProdCtgCd");
    let recommendRageList = pageObj.rateListMap[recommendProdCtgCd];

    if (recommendRageList.length > 0) {
        var arr = [];
        $.each(recommendRageList, function(idx, item) {
            let v_rateAdsvcNm = ajaxCommon.isNullNvl(item.rateAdsvcNm, "");
            let v_rateCd = ajaxCommon.isNullNvl(item.rateCd, "");
            let v_rateAdsvcBasDesc = ajaxCommon.isNullNvl(item.rateAdsvcBasDesc, "");
            let v_bnfitData = ajaxCommon.isNullNvl(item.bnfitData, "-");
            let v_bnfitVoice = ajaxCommon.isNullNvl(item.bnfitVoice, "-");
            let v_bnfitSms = ajaxCommon.isNullNvl(item.bnfitSms, "-");
            let v_promotionBnfitData = ajaxCommon.isNullNvl(item.promotionBnfitData, "");
            let v_promotionBnfitVoice = ajaxCommon.isNullNvl(item.promotionBnfitVoice, "");
            let v_promotionBnfitSms = ajaxCommon.isNullNvl(item.promotionBnfitSms, "");
            let v_mmBasAmtVatDesc = ajaxCommon.isNullNvl(item.mmBasAmtVatDesc, "");
            let v_promotionAmtVatDesc = ajaxCommon.isNullNvl(item.promotionAmtVatDesc, "");
            let v_recommendYn = ajaxCommon.isNullNvl(item.recommendYn, "");
            let labelCode = ajaxCommon.isNullNvl(item.labelCode, "");
            let v_maxDataDelivery = ajaxCommon.isNullNvl(item.maxDataDelivery, "");

            arr.push(`<li class="rate-content__item">`);
            arr.push(`    <a class="rate-info__wrap" href="/m/appForm/appFormDesignView.do?onOffType=7&operType=MNP3&indcOdrg=1&prdtSctnCd=LTE&prdtId=K7006048&rateCd=${v_rateCd}" title="요금제 이름 페이지 이동">`);

            arr.push(`        <div class="rate-info__title">`);
            if (labelCode != "") {
                arr.push(`        <div class="rate-sticker-wrap">`);
                if ("01" == labelCode) {
                    arr.push(`        <span class="rate-sticker orange">★1등</span>`);
                } else if ("02" == labelCode) {
                    arr.push(`        <span class="rate-sticker orange">★2등</span>`);
                } else if ("03" == labelCode) {
                    arr.push(`        <span class="rate-sticker orange">★3등</span>`);
                } else if ("91" == labelCode) {
                    arr.push(`        <span class="rate-sticker bluegreen">NEW</span>`);
                }else if ("92" == labelCode) {
                    arr.push(`        <span class="rate-sticker red">BEST</span> `);
                }
                arr.push(`        </div>`);
            }
            arr.push(`            <strong>${v_rateAdsvcNm}</strong>`);
            arr.push(`        </div>`);
            arr.push(`        <div class="rate-info__gift">`);
            arr.push(`            <p class="rate-info__gift-info">${v_rateAdsvcBasDesc}</p>`);
            //arr.push(`            <p class="rate-info__present-info">아무나 SOLO 결합 시 매월 평생 데이터 5GB 제공</p>`);
            arr.push(`        </div>`);
            arr.push(`        <div class="rate-detail">`);
            arr.push(`            <ul class="rate-detail__list">`);
            arr.push(`                <li class="rate-detail__item">`);
            arr.push(`                    <span>`);
            arr.push(`                        <i class="c-icon c-icon--data" aria-hidden="true"></i>`);
            arr.push(`                    </span>`);
            arr.push(`                    <span class="c-hidden">데이터</span>`);
            arr.push(`                    <div class="rate-detail__info">`);
            arr.push(`                                        <span class="max-data-label">`);
            if(v_maxDataDelivery != "") {
                arr.push(`                                        <em>${v_maxDataDelivery}</em>`);
            }
            arr.push(`                                        </span>`);
            arr.push(`                        <p>${v_bnfitData}</p>`);
            if(v_promotionBnfitData != "") {
                arr.push(`                    <p>${v_promotionBnfitData}</p>`);
            }
            arr.push(`                    </div>`);


            arr.push(`                </li>`);
            arr.push(`                <li class="rate-detail__item">`);
            arr.push(`                    <span><i class="c-icon c-icon--call-gray" aria-hidden="true"></i></span>`);
            arr.push(`                    <span class="c-hidden">음성</span>`);
            arr.push(`                    <div class="rate-detail__info ">`);
            arr.push(`                        <p>${v_bnfitVoice}</p>`);
            if(v_promotionBnfitVoice != "") {
                arr.push(`                    <p>${v_promotionBnfitVoice}</p>`);
            }
            arr.push(`                    </div>`);
            arr.push(`                </li>`);
            arr.push(`                <li class="rate-detail__item">`);
            arr.push(`                    <span>`);
            arr.push(`                        <i class="c-icon c-icon--msg" aria-hidden="true"></i>`);
            arr.push(`                    </span>`);
            arr.push(`                    <span class="c-hidden">문자</span>`);
            arr.push(`                    <div class="rate-detail__info">`);
            arr.push(`                        <p>${v_bnfitSms}</p>`);
            if(v_promotionBnfitSms != "") {
                arr.push(`                    <p>${v_promotionBnfitSms}</p>`);
            }
            arr.push(`                    </div>`);
            arr.push(`                </li>`);
            arr.push(`            </ul>`);
            arr.push(`        </div>`);
            arr.push(`        <div class="rate-price">`);
            arr.push(`            <div class="rate-price-wrap">`);
            arr.push(`                <div class="rate-price__item">`);


            if(v_mmBasAmtVatDesc != "" && v_promotionAmtVatDesc != "") {
                arr.push(`                <p class="rate-price__title through">`);
                arr.push(`                    <span class="c-hidden">월 기본료(VAT 포함)</span><b>${v_mmBasAmtVatDesc}</b> 원`);
                arr.push(`                </p>`);
                arr.push(`                <p class="rate-price__info">월 <b>${v_promotionAmtVatDesc}</b> 원</p>`);
            } else {
                arr.push(`                <p class="rate-price__info">월 <b>${v_mmBasAmtVatDesc}</b> 원</p>`);;
            }


            arr.push(`                </div>`);
            arr.push(`            </div>`);
            arr.push(`        </div>`);
            arr.push(`    </a>`);
            arr.push(`</li>`);
        })


        $("#mainUsimModal1 .rate-content__list").html(arr.join(''));

        var el = document.querySelector('#mainUsimModal1');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();

        setTimeout(function () {
          $('#mainUsimModal1 .c-modal__body').scrollTop(0);
        }, 50);

    } else {
        MCP.alert("추천 요금제 리스트 정보가 없습니다. ");
    }


    //


});

// 휴대폰요금제 목록 조회
var displayPhoneList = function() {
    var varData = ajaxCommon.getSerializedData({
        upRecommendProdCtgCd : 'REC0000002'
        ,prodDivCd:'01'
    });

    ajaxCommon.getItem({
            id : 'rateAdsvcScndList'
            , cache : false
            , async : true
            , url : '/m/rateAdsvcScndList.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let rateScndCtgHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let rateAdsvcScndCtgList = result.result;

            if(returnCode == "00") {
                if(rateAdsvcScndCtgList.length > 0) {
                    $("#sec_phone").show();
                    $.each(rateAdsvcScndCtgList, function(index, value) {
                        let rateHtml = "";
                        let isActive = "";
                        let rateAdsvcList = value.lists;

                        if(index == 0) {
                            isActive = "is-active";
                        }

                        // 카테고리 목록 표시
                        rateScndCtgHtml += "<button class='c-tabs__button "+isActive+"' data-swiper-active='"+index+"'>";
                        rateScndCtgHtml +=      value.recommendProdCtgNm
                        rateScndCtgHtml += "</button>";
                        // 휴대폰 요금제 목록 표시
                        rateHtml += "<div class='swiper-slide'>";
                        rateHtml += "	<div class='c-tabs__panel'>";
                        rateHtml += "		<ul class='c-main-card-list' id='main-card-list_1"+index+"'>";

                        let cardCnt = 0;
                        let minusCnt = 0; // 정책만료된 요금제 제거하기 위함
                        $.each(rateAdsvcList, function(idx, item) {
                            // 정책 만료된 요금제 미노출
                            if (!item.phoneProdBasDto) {
                                minusCnt--;
                                return true;
                            }
                            cardCnt = idx + minusCnt;

                            let v_imgPath = ajaxCommon.isNullNvl(item.phoneProdBasDto.imgPath, "");
                            let v_sntyProdNm = ajaxCommon.isNullNvl(item.phoneProdBasDto.prodNm, "-");
                            let v_recommendProdBasDesc = ajaxCommon.isNullNvl(item.recommendProdBasDesc, "");
                            let v_recommendProdDtlDesc = ajaxCommon.isNullNvl(item.recommendProdDtlDesc, "");
                            let v_baseAmt = ajaxCommon.isNullNvl(item.phoneProdBasDto.rentalBaseAmt, "-");
                            let v_instNom = ajaxCommon.isNullNvl(item.phoneProdBasDto.agrmTrmBase, "0");
                            let v_newYn = ajaxCommon.isNullNvl(item.newYn, "-");
                            let v_prodId = ajaxCommon.isNullNvl(item.prodId, "");
                            let v_prdtId = ajaxCommon.isNullNvl(item.prdtId, "");
                            let v_rateCd = ajaxCommon.isNullNvl(item.rateCd, "");

                            // 추천 요금제 변수
                            let v_baseVatAmt = ajaxCommon.isNullNvl(item.phoneProdBasDto.baseAmt, 0);
                            let v_payMnthAmt = ajaxCommon.isNullNvl(item.phoneProdBasDto.payMnthAmt, 0);
                            let v_rateNm = item.phoneProdBasDto.rateNm;

                            if(v_baseAmt != "-" && fnIsNumeric(v_baseAmt)) {
                                v_baseAmt = parseInt(Number(v_baseAmt) * 1.1);
                            }

                            // 휴대폰 설계하기 화면으로 이동
                            if(cardCnt < 3) {
                                rateHtml += "			<li class='c-main-card c-main-card--type1'>";
                            } else {
                                rateHtml += "			<li class='c-main-card c-main-card--type1' style='display: none;'>";
                            }



                            if ("0" == v_instNom) {
                                rateHtml += "				<a class='c-main-card__anchor' href='/m/appForm/appFormDesignView.do?prodId="+v_prodId+"&hndsetModelId="+v_prdtId+"&rateCd="+v_rateCd+"&instNom=0' >";
                            } else {
                                rateHtml += "				<a class='c-main-card__anchor' href='/m/appForm/appFormDesignView.do?prodId="+v_prodId+"&hndsetModelId="+v_prdtId+"&rateCd="+v_rateCd+"&instNom="+v_instNom+"' >";
                            }
                            rateHtml += "					<div class='c-main-card__image-2'>";
                            rateHtml += "						<img src='"+v_imgPath+"' alt='"+v_sntyProdNm+"' onerror='noImage(this);'>";
                            rateHtml += "					</div>";
                            rateHtml += "					<div class='c-main-card__panel'>";
                            rateHtml += "						<strong class='c-main-card__title'>"+v_sntyProdNm+"</strong>";
                            // rateHtml += "						<p class='c-main-card__text-info'>" + v_recommendProdDtlDesc + "</p>";
                            rateHtml += "						<p class='c-main-card__text-info'>" + v_rateNm + "</p>"; //test
                            rateHtml += "						<div class='c-main-card__priceinfo'>";
                            rateHtml += "							<p>월 단말요금 <b>" + numberWithCommas(v_payMnthAmt) + "</b> 원</p>";
                            rateHtml += "							<p>월 통신요금 <b>"+ numberWithCommas(v_baseVatAmt) +"</b> 원</p>";
                            rateHtml += "						</div>";
                            rateHtml += "						<div class='c-main-card__pricesum'>";
                            rateHtml += "							<span class='c-text c-text--type3'> 월<b>"+numberWithCommas(v_payMnthAmt + v_baseVatAmt)+"</b>원</span>";
                            rateHtml += "						</div>";
                            rateHtml += "					</div>";
                            // '신규' 표시
                            if(v_newYn == "Y") {
                                rateHtml += "					<span class='c-main-card__label c-main-card__label--new'>";
                                rateHtml += "						<i class='c-icon c-icon--card-new' aria-hidden='true'></i>";
                                rateHtml += "						<span class='c-hidden'>new</span>";
                                rateHtml += "					</span>";
                            }
                            rateHtml += "				</a>";
                            rateHtml += "			</li>";
                        });
                        rateHtml += "		</ul>";
                        if(cardCnt > 2) {
                            rateHtml += "	    <div class='c-button-wrap c-button-wrap--center u-mt--16' data-more='#main-card-list_1"+index+"'>";
                            rateHtml += "	        <button class='c-button c-button--sm c-button--white fs-12'>더보기<i class='c-icon c-icon--arrow-down-gray' aria-hidden='true'></i></button>";
                            rateHtml += "	    </div>";
                        }
                        rateHtml += "	</div>";
                        rateHtml += "</div>";

                        $("#rateAdsvcList").append(rateHtml);
                    });

                    $("#rateAdsvcScndCtgList").html(rateScndCtgHtml);
                    window.mainFunctions.productSwiper2();
                }
            } else {
                MCP.alert(message);
            }
        });
}



// 휴대폰요금제 목록 조회
var displayPhoneListNe = function() {
    var varData = ajaxCommon.getSerializedData({
        upRecommendProdCtgCd : 'REC0000002'
        ,prodDivCd:'01'
    });

    ajaxCommon.getItem({
            id : 'rateAdsvcScndList'
            , cache : false
            , async : true
            , url : '/rateAdsvcScndNeList.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let cardCnt = 0;
            let rateScndCtgHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let rateAdsvcScndCtgList = result.result;

            if(returnCode == "00") {
                if(rateAdsvcScndCtgList.length > 0) {
                    $("#sec_phone").show();
                    $.each(rateAdsvcScndCtgList, function(index, value) {
                        let rateHtml = "";
                        let isActive = "";
                        let rateAdsvcList = value.lists;

                        if(index == 0) {
                            isActive = "is-active";
                        }

                        // 카테고리 목록 표시
                        rateScndCtgHtml += "<button class='c-tabs__button "+isActive+"' data-swiper-active='"+index+"'>";
                        rateScndCtgHtml +=      value.recommendProdCtgNm
                        rateScndCtgHtml += "</button>";
                        // 휴대폰 요금제 목록 표시
                        rateHtml += "<div class='swiper-slide'>";
                        rateHtml += "	<div class='c-tabs__panel'>";
                        rateHtml += "		<ul class='c-main-card-list' id='main-card-list_1"+index+"'>";

                        //$.each(rateAdsvcList, function(idx, item) {
                        for(let idx =0 ; idx < rateAdsvcList.length ; idx++ ) {
                            let item = rateAdsvcList[idx];
                            cardCnt = idx;

                            //let v_imgPath = ajaxCommon.isNullNvl(item.phoneProdBasDto.imgPath, "");
                            //let v_imgPath = "https://www.ktmmobile.com/" +ajaxCommon.isNullNvl(item.phoneProdBasDto.rprtPhoneProdImgDto.phoneProdImgDetailDtoList[0].imgPath, "");
                            let v_imgPath = ajaxCommon.isNullNvl(item.phoneProdBasDto.rprtPhoneProdImgDto.phoneProdImgDetailDtoList[0].imgPath, "");

                            let v_sntyProdNm = ajaxCommon.isNullNvl(item.phoneProdBasDto.prodNm, "-");
                            let v_recommendProdBasDesc = ajaxCommon.isNullNvl(item.recommendProdBasDesc, "");
                            let v_recommendProdDtlDesc = ajaxCommon.isNullNvl(item.recommendProdDtlDesc, "");
                            let v_baseAmt = ajaxCommon.isNullNvl(item.phoneProdBasDto.rentalBaseAmt, "-");

                            let v_newYn = ajaxCommon.isNullNvl(item.newYn, "-");

                            let chargeList = item.phoneProdBasDto.mspSaleSubsdMstListForLowPrice ;
                            if (chargeList == null || chargeList.length < 1 ) {
                                continue;
                            }
                            let v_prodId = ajaxCommon.isNullNvl(item.prodId, "");
                            let v_prdtId = ajaxCommon.isNullNvl(chargeList[0].prdtId, "");
                            //let v_prdtId = ajaxCommon.isNullNvl(item.prdtId, "");

                            let v_rateCd = ajaxCommon.isNullNvl(chargeList[0].rateCd, "");
                            let v_instNom = ajaxCommon.isNullNvl(chargeList[0].agrmTrm, "0");

                            // 추천 요금제 변수
                            let v_baseVatAmt = ajaxCommon.isNullNvl(chargeList[0].payMnthChargeAmt, 0);
                            let v_payMnthAmt = ajaxCommon.isNullNvl(chargeList[0].payMnthAmt, 0);
                            let v_rateNm = chargeList[0].rateNm;


                            console.log(v_rateNm + "==>v_prdtId==>" + v_prdtId);


                            // 휴대폰 설계하기 화면으로 이동
                            if(idx < 3) {
                                rateHtml += "			<li class='c-main-card c-main-card--type1'>";
                            } else {
                                rateHtml += "			<li class='c-main-card c-main-card--type1' style='display: none;'>";
                            }

                            if ("0" == v_instNom) {
                                rateHtml += "				<a class='c-main-card__anchor' href='/m/appForm/appFormDesignView.do?prodId="+v_prodId+"&rateCd="+v_rateCd+"&instNom=0' >";
                            } else {
                                rateHtml += "				<a class='c-main-card__anchor' href='/m/appForm/appFormDesignView.do?prodId="+v_prodId+"&rateCd="+v_rateCd+"&instNom="+v_instNom+"' >";
                            }




                            rateHtml += "					<div class='c-main-card__image-2'>";
                            rateHtml += "						<img src='"+v_imgPath+"' alt='"+v_sntyProdNm+"' onerror='noImage(this);'>";
                            rateHtml += "					</div>";
                            rateHtml += "					<div class='c-main-card__panel'>";
                            rateHtml += "						<strong class='c-main-card__title'>"+v_sntyProdNm+"</strong>";
                            // rateHtml += "						<p class='c-main-card__text-info'>" + v_recommendProdDtlDesc + "</p>";
                            rateHtml += "						<p class='c-main-card__text-info'>" + v_rateNm + "</p>"; //test
                            rateHtml += "						<div class='c-main-card__priceinfo'>";
                            rateHtml += "							<p>월 단말요금 <b>" + numberWithCommas(v_payMnthAmt) + "</b> 원</p>";
                            rateHtml += "							<p>월 통신요금 <b>"+ numberWithCommas(v_baseVatAmt) +"</b> 원</p>";
                            rateHtml += "						</div>";
                            rateHtml += "						<div class='c-main-card__pricesum'>";
                            rateHtml += "							<span class='c-text c-text--type3'> 월<b>"+numberWithCommas(v_payMnthAmt + v_baseVatAmt)+"</b>원</span>";
                            rateHtml += "						</div>";
                            rateHtml += "					</div>";
                            // '신규' 표시
                            if(v_newYn == "Y") {
                                rateHtml += "					<span class='c-main-card__label c-main-card__label--new'>";
                                rateHtml += "						<i class='c-icon c-icon--card-new' aria-hidden='true'></i>";
                                rateHtml += "						<span class='c-hidden'>new</span>";
                                rateHtml += "					</span>";
                            }
                            rateHtml += "				</a>";
                            rateHtml += "			</li>";
                        };
                        rateHtml += "		</ul>";
                        if(cardCnt > 2) {
                            rateHtml += "	    <div class='c-button-wrap c-button-wrap--center u-mt--16' data-more='#main-card-list_1"+index+"'>";
                            rateHtml += "	        <button class='c-button c-button--sm c-button--white fs-12'>더보기<i class='c-icon c-icon--arrow-down-gray' aria-hidden='true'></i></button>";
                            rateHtml += "	    </div>";
                        }
                        rateHtml += "	</div>";
                        rateHtml += "</div>";

                        $("#rateAdsvcList").append(rateHtml);
                    });

                    $("#rateAdsvcScndCtgList").html(rateScndCtgHtml);
                    window.mainFunctions.productSwiper2();
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 사용후기 목록 조회
var displayRequestReviewList = function() {
    var varData = ajaxCommon.getSerializedData({
    });

    ajaxCommon.getItem({
            id : 'requestReviewListAjax'
            , cache : false
            , async : true
            , url : '/m/requestReviewListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let requestReviewHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let requestReviewList = result.result;

            if(returnCode == "00") {
                if(requestReviewList.length > 0) {
                    $.each(requestReviewList, function(index, value) {

                        let v_reviewImgList = value.reviewImgList;
                        let v_eventNm = ajaxCommon.isNullNvl(value.eventNm, "");
                        let v_regNm = ajaxCommon.isNullNvl(value.regNm, "");
                        let v_sysRdateDd = ajaxCommon.isNullNvl(value.sysRdateDd, "");
                        let v_reviewSbst = ajaxCommon.isNullNvl(value.reviewSbst, "");
                        let v_reqBuyType = ajaxCommon.isNullNvl(value.reqBuyType, "");
                        let reviewQuestionList = value.reviewQuestionList;
                        let prizeSbst = value.prizeSbst;

                        if(v_reqBuyType == "UU") {
                            v_reqBuyType = "유심";
                        } else if(v_reqBuyType == "MM") {
                            v_reqBuyType = "휴대폰";
                        } else {
                            v_reqBuyType = "";
                        }

                        if(prizeSbst < 4 && prizeSbst != null && prizeSbst !=''){
                            requestReviewHtml += "<li class='reivew-item' onclick='reviewMove("+index+")' style='cursor: pointer'>";
                        }else{
                            requestReviewHtml += "<li class='reivew-item' onclick='reviewMove()' style='cursor: pointer'>";
                        }
                        requestReviewHtml += "	<h4 class='review-item__title'>"+v_eventNm+"</h4>";
                        requestReviewHtml += "	<p class='review-item__content ellipsis u-co-gray9'>"+v_reviewSbst+"</p>";

                        requestReviewHtml += '<ul class="review-summary">';
                        if(reviewQuestionList !=null && reviewQuestionList.length>0){
                            for(var j = 0; j < reviewQuestionList.length; j ++){
                                requestReviewHtml += '<li class="review-summary__item">';
                                requestReviewHtml += '<div class="review-summary__question">'+reviewQuestionList[j].questionMm +'<span class="review-summary__answer">' + reviewQuestionList[j].answerDesc+'</span></div>';
                                requestReviewHtml += '</li>';

                            }

                        }

                        requestReviewHtml += '</ul>';

                        if(v_reviewImgList.length > 0) {
                            $.each(v_reviewImgList, function(idx, item) {
                                let v_filePathNm = item.filePathNm;
                                let v_fileAlt = ajaxCommon.isNullNvl(item.fileAlt, "");
                                requestReviewHtml += "  <div class='reivew-item__img' alt=''>";
                                if(v_reviewImgList.length > 1) {
                                    requestReviewHtml += "	<span class='c-text-label c-text-label--darkgray img-count'>+"+(v_reviewImgList.length-1)+"</span>";
                                }
                                requestReviewHtml += "      <img src='"+v_filePathNm+"' alt='"+v_fileAlt+"' onerror='noImage(this);'>";
                                requestReviewHtml += "  </div>";
                                if(idx == 0) {
                                    return false;
                                }
                            });
                        } else {
                            requestReviewHtml += "  <div class='reivew-item__img' alt=''>";
                            requestReviewHtml += "      <img src='/resources/images/mobile/content/img_review_noimage.png' alt='no-image'>";
                            requestReviewHtml += "  </div>";
                        }
                        requestReviewHtml += "	<div class='review-item__info'>";
                        requestReviewHtml += "		<p class='u-co-gray-8'>"+v_regNm+"님</p>";
                        requestReviewHtml += "		<p class='u-co-gray-7'>"+v_sysRdateDd+"</p>";
                        requestReviewHtml += "	</div>";
                        requestReviewHtml += "</li>";

                        if(index == 2) {
                            return false;
                        }
                    });
                }
                $("#sec_requestReview").show();
                $("#requestReviewList").html(requestReviewHtml);
            }else {
                MCP.alert(message);
            }
        });
}

// 이미지가 없을경우
function noImage(image) {
    image.onerror = "";
    image.src = "/resources/images/mobile/content/img_review_noimage.png";
    return true;
}

// 배너조회 로그 추가
function addBannAccess(obj) {
    try{
        if(obj.bannSeq != "" && obj.bannCtg != "") {
            insertBannAccess(obj.bannSeq, obj.bannCtg);
        }
    }catch(e){}
}

// 배너 링크
function bannerLink(obj){

    try{
        // 페이지 이동
        if(obj.linkUrl == "") {
            return;
        } else {
            var mobileAppChk = $('#mobileAppChk').val();
            if(mobileAppChk == 'A'){
                window.location.href = obj.linkUrl;
            } else {
                if(obj.linkTarget == "Y") {
                    var win = window.open(obj.linkUrl);
                    win.onbeforeunload = function(){
                    }
                } else {
                    KTM.LoadingSpinner.show();
                    window.location.href = obj.linkUrl;
                }
            }
        }
    }catch(e){
        return;
    }
}


//사용후기 이동
function reviewMove(val) {
    if(val == undefined){
        location.href = "/m/requestReView/requestReView.do";
    }else{
        location.href = "/m/requestReView/requestReView.do?choice="+val;
    }
}
