;


let ansNo = "";

var pageObj = {
    rateScndCtgList:[]
    , usimListCnt:1
}


document.addEventListener("DOMContentLoaded", function () {


    document.body.addEventListener("click", function(e) {
        if (e.target.matches("#mainUsimAcc .c-accordion__button")) {
            const accordion = document.querySelector("#mainUsimAcc");
            const buttons = accordion.querySelectorAll("#mainUsimAcc .c-accordion__button");

            const button = e.target;
            const targetPanelId = button.getAttribute("aria-controls");
            const targetPanel = document.getElementById(targetPanelId);
            const isExpanded = button.getAttribute("aria-expanded") === "true";

            // 모든 버튼과 패널 닫기
            buttons.forEach(btn => {
                const panelId = btn.getAttribute("aria-controls");
                const panel = document.getElementById(panelId);

                btn.classList.remove("is-active");
                btn.setAttribute("aria-expanded", "false");

                panel.classList.remove("expanded");
                panel.setAttribute("aria-hidden", "true");

                panel.style.height = panel.scrollHeight + "px";
                requestAnimationFrame(() => {
                    panel.style.height = "0px";
                });
            });

            // 클릭한 게 닫혀있던 경우에만 열기
            if (!isExpanded) {
                button.classList.add("is-active");
                button.setAttribute("aria-expanded", "true");

                targetPanel.classList.add("expanded");
                targetPanel.setAttribute("aria-hidden", "false");

                // 슬라이드 다운
                const scrollHeight = targetPanel.scrollHeight;

                // 초기 높이 0 설정 (닫힌 상태에서 시작)
                targetPanel.style.height = "0px";

                // 리플로우 강제 트리거
                requestAnimationFrame(() => {
                    targetPanel.style.height = scrollHeight + "px";
                });

                // transition 끝나면 height 값을 auto로 복귀
                const onTransitionEnd = function () {
                    targetPanel.style.height = "auto";
                    targetPanel.removeEventListener("transitionend", onTransitionEnd);
                };

                targetPanel.addEventListener("transitionend", onTransitionEnd, { once: true });

            }
        }
    });



});

$(document).ready(function(){


    <!-- 아코디언 스크립트 -->

    <!-- 아코디언 스크립트 끝 -->

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


    $(document).on('click', 'input:radio[id^=radio]', function () {
        $('.survey .c-button-wrap .c-button.survey-button').addClass('is-active');
    });
    $(document).on('click', '.survey .c-button-wrap .c-button', function () {
        $('.survey .c-button-wrap .c-button.survey-button').removeClass('is-active');
        var checkVal = [];
        $("input[name^=cho]:checked").each(function () {
            checkVal.push($(this).val());
        });
        var sugRate = document.querySelectorAll('.sug__rate');
        sugRate[checkVal.length].style.cssText = 'disPlay: none;'
        sugRate[checkVal.length + 1].style.cssText = 'disPlay: block;'
        if (checkVal.length == 2) {
            getResultInqr(null, checkVal.toString().replace(',',''));
            document.querySelectorAll('.c-radio--button').forEach(function (ele) {
                ele.checked = false;
            })
            checkVal = [];
        }
    });

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
            //, url : '/m/bannerListAjax.do'
            , url : '/bigBannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            let bannerHtml = "";
            let bannerThumbHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            //let bannerList = result.result;
            let bannerList = result.result.bannerList;
            let bannerApdList = result.result.bannerApdList;

            if(returnCode == "00") {
                if(bannerList.length > 0) {
                    $.each(bannerList, function(index, value) {
                        let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                        let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                        let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                        let v_bannDesc = ajaxCommon.isNullNvl(value.bannDesc, "");
                        let v_bannApdDesc = ajaxCommon.isNullNvl(value.bannApdDesc, "");
                        let v_bannBgColor = ajaxCommon.isNullNvl(value.bgColor, "");
                        let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");


                        let v_textColor1 = ajaxCommon.isNullNvl(value.textColor1, "");
                        let v_textColor2 = ajaxCommon.isNullNvl(value.textColor2, "");


                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide' style='background-color: "+v_bannBgColor+"'>";
                        bannerHtml += "    <a class='swiper__anchor' href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        if(v_bannDesc != "") {
                            bannerHtml += "        <p class='event-date'>";
                            bannerHtml += "            <span class='c-text--underline' style='color: "+v_textColor1+"'>"+v_bannDesc+"</span>";
                            bannerHtml += "        </p>";
                        }

                        bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</div>";

                        if(index == 19) {
                            return false;
                        }
                    });

                    $('#main_loading').hide();

                    // 배너
                    $("#sec_M001").show();
                    $("#M001").html(bannerHtml);

                    if(bannerApdList.length > 0) {
                        $.each(bannerApdList, function(idx, item) {
                            let v_imgNm = ajaxCommon.isNullNvl(item.imgNm, "");
                            let v_linkNm = ajaxCommon.isNullNvl(item.linkNm, "");

                            bannerThumbHtml += "<a class='swiper-slide' href='#n' role='button'>";
                            bannerThumbHtml += "    <p>"+v_linkNm+"</p>";
                            bannerThumbHtml += "	<img src='"+v_imgNm+"' alt=''>";
                            bannerThumbHtml += "</a>";
                        });
                    }
                    // 배너 썸네일
                    $("#M001_thumb").html(bannerThumbHtml);
                    window.mainFunctions.mainFullBanner();

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
                        let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide'>";
                        bannerHtml += "    <a class='service-icon' href='javascript:;' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        if(v_bannDesc != "") {
                            bannerHtml += "        <div class='c-balloon c-balloon--center c-balloon--sm'>"+v_bannDesc+"</div>";
                        }
                        bannerHtml += "        <img src='"+v_bannImg+"' alt=''>";
                        bannerHtml += "        <p class='u-mt--12 c-text c-text--fs16 u-ta-center'>"+v_bannNm+"</p>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</div>";

                        if(index == 98) {
                            return false;
                        }
                    });
                    $("#sec_M002").show();
                    $("#M002").html(bannerHtml);
                    window.mainFunctions.iconScrollContainer();
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
                        let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
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
                    window.mainFunctions.bannerSwiper1();
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
                        let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide' role='group' aira-lebel='1/6'>";
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
                    window.mainFunctions.bannerSwiper2();
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
                        let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide' role='group' aira-lebel='"+(index+1)+"/"+bannerList.length+"'>";
                        bannerHtml += "    <a href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</div>";

                        if(index == 98) {
                            return false;
                        }
                    });
                    $("#sec_M005").show();
                    $("#M005").append(bannerHtml);
                    window.mainFunctions.bannerSwiper3();
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
                        let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
                        let v_bannBgColor = ajaxCommon.isNullNvl(value.bgColor, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide' style='background-color: "+v_bannBgColor+"'>";
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
                    window.mainFunctions.bannerSwiper4();
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
                        let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<li class='link-item'>";
                        bannerHtml += "    <a href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "        <img src='"+v_bannImg+"' alt=''>";
                        bannerHtml += "        <span>"+v_bannNm+"<i class='c-icon c-icon--arrow-link' aria-hidden='true'><span class='sr-only'>바로가기</span></i></span>";
                        bannerHtml += "    </a>";
                        bannerHtml += "</li>";

                        if(index == 3) {
                            return false;
                        }
                    });
                    $("#M007").show();
                    $("#M007").html(bannerHtml);
                } else {
                    //
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
                        let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_bannBgColor = ajaxCommon.isNullNvl(value.bgColor, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<div class='swiper-slide'>";
                        bannerHtml += "    <div class='top-event__image' style='background-color: "+v_bannBgColor+"'>";
                        bannerHtml += "        <a class='top-event__anchor' href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "            <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "		   </a>";
                        bannerHtml += "    </div>";
                        bannerHtml += "</div>";

                        if(index == 98) {
                            return false;
                        }
                    });
                    $("#sec_M008").show();
                    $("#M008").html(bannerHtml);
                    window.mainFunctions.topEvent();
                    $("#sec_M008 .swiper-slide").removeAttr("tabindex");
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
                        let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                        let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                        let v_linkUrl = ajaxCommon.isNullNvl(value.linkUrlAdr, "");
                        let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

                        var linkData = {
                            bannSeq : v_bannSeq
                            , bannCtg : v_bannCtg
                            , linkUrl : v_linkUrl
                            , linkTarget : v_linkTarget
                        };

                        bannerHtml += "<li class='all-event-list__item'>";
                        bannerHtml += "	<a class='all-event-list__anchor' href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
                        bannerHtml += "		<img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
                        bannerHtml += "	</a>";
                        bannerHtml += "</li>";

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
            let noticeHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let workNotiList = result.result;

            if(returnCode == "00") {
                if(workNotiList.length > 0) {
                    $.each(workNotiList, function(index, value) {
                        let v_boardSeq = ajaxCommon.isNullNvl(value.boardSeq, "");
                        let v_boardSubject = ajaxCommon.isNullNvl(value.boardSubject, "-");
                        let v_boardContents = ajaxCommon.isNullNvl(value.boardContents, "-");
                        let v_boardWriteDt = ajaxCommon.isNullNvl(value.boardWriteDt, "-");
                        let v_url = "/cs/boardView.do?backStep=1&boardSeq="+v_boardSeq;

                        noticeHtml += "<div class='left-box'>";
                        noticeHtml += "    <h3 class='notice-title'>공지</h3>";
                        noticeHtml += "    <a href='"+v_url+"' id='workNoti'>";
                        noticeHtml += "        <span id='workNotiHeadline' class='notice-headline'>"+v_boardSubject+"</span>";
                        noticeHtml += "        <span class='notice-text'>"+removeHtml(v_boardContents)+"</span>";
                        noticeHtml += "    </a>";
                        noticeHtml += "</div>";
                        noticeHtml += "<div class='right-box'>";
                        noticeHtml += "    <span  id='workNotiDate'class='notice-date'>"+v_boardWriteDt+"</span>";
                        noticeHtml += "    <a class='notice-more' href='/cs/csNoticeList.do'>더보기";
                        noticeHtml += "        <i class='c-icon c-icon--arrow-link' aria-hidden='true'></i>";
                        noticeHtml += "    </a>";
                        noticeHtml += "</div>";

                        if(index == 0) {
                            return false;
                        }
                    });
                    $("#div_notice").show();
                    $("#div_notice").html(noticeHtml);
                }
            } else {
                MCP.alert(message);
            }
        });
}

// 요금제추천 질문 표시
var displayInqr = function() {

    ajaxCommon.getItem({
            id : 'rateRecommendInqrBasListAjax'
            , cache : false
            , async : true
            , url : '/m/rateRecommendInqrBasListAjax.do'
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

                        inqrHtml += "<div class='survey__content sug__rate'>";
                        inqrHtml += "	<div class='u-fs-12 u-co-gray-9 u-mt--8'>";
                        inqrHtml += "		내게 맞는 상품찾기";
                        inqrHtml += "		<span class='u-fs-18 u-co-mint u-fw--bold u-block'>";
                        inqrHtml += "			유심요금제&nbsp";
                        inqrHtml += "			<span class='u-co-black u-mr--8'>고민이라면?</span>";
                        inqrHtml += "			<span class='u-fs-14 u-fw--regular u-co-black'>고객님 성향에 맞춘 추천 요금제를 확인해보세요</span>";
                        inqrHtml += "		</span>";
                        inqrHtml += "	</div>";
                        inqrHtml += "	<div class='u-fs-30 u-co-mint u-fw--bold u-mt--8'>";
                        inqrHtml += "		<span class='u-fs-18 u-letter-spacing--0'>Q"+(index+1)+".</span>";
                        inqrHtml += "		<span class='u-fs-16 u-co-black u-fw--normal' id='radio_header"+index+"'>"+v_rateRecommendInqrSbst+"</span>";
                        inqrHtml += "		<div class='survey__inner'>";
                        inqrHtml += "			<div class='c-form-wrap'>";
                        inqrHtml += "				<div class='c-form-group' role='group' aira-labelledby='radio_header1'>";
                        inqrHtml += "					<div class='c-checktype-row c-col2'>";
                        if(v_ansSbst1 != "") {
                            inqrHtml += "						<input class='c-radio c-radio--button' id='radio00"+i+"' type='radio' name='cho"+index+"' value='"+v_val+"' >";
                            inqrHtml += "						<label class='c-label' for='radio00"+i+"'>"+v_ansSbst1+"</label> ";
                            i += 1;
                            v_val += 1;
                        }
                        if(v_ansSbst2 != "") {
                            inqrHtml += "						<input class='c-radio c-radio--button' id='radio00"+i+"' type='radio'  name='cho"+index+"' value='"+v_val+"'>";
                            inqrHtml += "						<label class='c-label' for='radio00"+i+"'>"+v_ansSbst2+"</label>";
                            i += 1;
                            v_val += 1;
                        }
                        if(v_ansSbst3 != "") {
                            inqrHtml += "						<input class='c-radio c-radio--button' id='radio00"+i+"' type='radio'  name='cho"+index+"' value='"+v_val+"'>";
                            inqrHtml += "						<label class='c-label' for='radio00"+i+"'>"+v_ansSbst3+"</label>";
                            i += 1;
                            v_val += 1;
                        }
                        if(v_ansSbst4 != "") {
                            inqrHtml += "						<input class='c-radio c-radio--button' id='radio00"+i+"' type='radio'  name='cho"+index+"' value='"+v_val+"'>";
                            inqrHtml += "						<label class='c-label' for='radio00"+i+"'>"+v_ansSbst4+"</label>";
                            i += 1;
                            v_val += 1;
                        }
                        inqrHtml += "					</div>";
                        inqrHtml += "				</div>";
                        inqrHtml += "			</div>";
                        inqrHtml += "		</div>";
                        inqrHtml += "		<div class='c-button-wrap'>";
                        if(inqrBasLength == (index+1)) {
                            inqrHtml += "			<button class='result c-button survey-button'><span class='c-hidden'>결과확인</span></button>";
                        } else {
                             inqrHtml += "			<button class='c-button survey-button'><span class='c-hidden'>다음</span></button>";
                        }
                        inqrHtml += "		</div>";
                        inqrHtml += "	</div>";
                        inqrHtml += "</div>";

                        if(index == 3) {
                            return false;
                        }
                    });

                    inqrHtml += "<div class='survey__content sug__rate'>";
                    inqrHtml += "	<div class='u-fs-12 u-co-gray-9 u-mt--8'>";
                    inqrHtml += "		고객님께 딱 맞는";
                    inqrHtml += "		<span class='u-fs-18 u-co-mint u-fw--bold u-block'>";
                    inqrHtml += "			유심요금제&nbsp";
                    inqrHtml += "			<span class='u-co-black u-mr--8'>추천합니다</span>";
                    inqrHtml += "			<span class='u-fs-14 u-fw--regular u-co-black'>고객님 성향에 맞춘 추천 요금제를 확인해보세요</span>";
                    inqrHtml += "		</span>";
                    inqrHtml += "	</div>";
                    inqrHtml += "	<div class='survey-swiper swiper-container'>";
                    inqrHtml += "		<div id='inqrRel' class='swiper-wrapper'>";

                    inqrHtml += "		</div>";
                    inqrHtml += "	</div>";
                    inqrHtml += "	<button class='survey-pager circle swiper-button-next u-mr--80' type='button'></button>";
                    inqrHtml += "	<button class='survey-pager circle swiper-button-prev u-ml--80' type='button'></button>";
                    inqrHtml += "	<div class='c-button-wrap'>";
                    inqrHtml += "		<button class='restart c-button u-width--130 c-button--h48 c-button-round c-button--white u-mt--30'>다시하기</button>";
                    inqrHtml += "	</div>";
                    inqrHtml += "</div>";

                    $("#sec_survey").show();
                    $("#inqrBasList").after(inqrHtml);
                    window.mainFunctions.initSurveyContent();
                }
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
var getResultInqr = function(ele, no) {

    if (no.length == 0) {
        ansNoValidate(ele);
    } else {
        ansNo = no;
    }


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
                        let v_promotionBnfitData = ajaxCommon.isNullNvl(value.promotionBnfitData, "");
                        let v_promotionBnfitVoice = ajaxCommon.isNullNvl(value.promotionBnfitVoice, "");
                        let v_promotionBnfitSms = ajaxCommon.isNullNvl(value.promotionBnfitSms, "");
                        let v_bnfitSms = ajaxCommon.isNullNvl(value.bnfitSms, "");
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
                        inqrRelHtml += "	<div class='c-main-card'>";
                        inqrRelHtml += "		<a class='c-main-card__anchor' href='/appForm/appFormDesignView.do?onOffType=0&operType=MNP3&indcOdrg=1&prdtSctnCd=LTE&prdtId=K7006048&rateCd="+v_rateCd+"'>";
                        //inqrRelHtml += "		<a class='c-main-card__anchor' href='#'>";
                        inqrRelHtml += "			<div class='c-main-card__panel'>";
                        inqrRelHtml += "				<strong class='c-main-card__title'>"+v_rateAdsvcNm+"</strong>";
                        inqrRelHtml += "				<p class='c-main-card__text'>"+v_rateAdsvcBasDesc+"</p>";
                        inqrRelHtml += "				<ul class='c-main-card__info'>";
                        inqrRelHtml += "					<li class='c-main-card__info-list'>";
                        inqrRelHtml += "						<div class='c-main-card__info-wrap'>";
                        inqrRelHtml += "							<div class='c-main-card__prefix'>";
                        inqrRelHtml += "								<i class='c-icon c-icon--data' aria-hidden='true'></i>";
                        inqrRelHtml += "								<span class='c-hidden'>데이터</span>";
                        inqrRelHtml += "							</div>";
                        inqrRelHtml += "							<div class='c-main-card__sub'>"+v_bnfitData+"</div>";
                        inqrRelHtml += "						</div>";
                        inqrRelHtml += "					</li>";
                        inqrRelHtml += "					<li class='c-main-card__info-list'>";
                        inqrRelHtml += "						<div class='c-main-card__info-wrap'>";
                        inqrRelHtml += "							<div class='c-main-card__prefix'>";
                        inqrRelHtml += "								<i class='c-icon c-icon--call-gray' aria-hidden='true'></i>";
                        inqrRelHtml += "								<span class='c-hidden'>음성</span>";
                        inqrRelHtml += "							</div>";
                        inqrRelHtml += "							<div class='c-main-card__sub'>"+v_bnfitVoice+"</div>";
                        inqrRelHtml += "						</div>";
                        inqrRelHtml += "					</li>";
                        inqrRelHtml += "					<li class='c-main-card__info-list'>";
                        inqrRelHtml += "						<div class='c-main-card__info-wrap'>";
                        inqrRelHtml += "							<div class='c-main-card__prefix'>";
                        inqrRelHtml += "								<i class='c-icon c-icon--msg' aria-hidden='true'></i>";
                        inqrRelHtml += "								<span class='c-hidden'>문자메시지</span>";
                        inqrRelHtml += "							</div>";
                        inqrRelHtml += "							<div class='c-main-card__sub'>"+v_bnfitSms+"</div>";
                        inqrRelHtml += "						</div>";
                        inqrRelHtml += "					</li>";
                        inqrRelHtml += "				</ul>";
                        inqrRelHtml += "				<div class='c-main-card__price'>";
                        if(v_mmBasAmtVatDesc != "" && v_promotionAmtVatDesc != "") {
                            inqrRelHtml += "									<span class='u-td-line-through'>";
                            inqrRelHtml += "										<span class='c-hidden'>기본요금 월</span>"+v_mmBasAmtVatDesc+" 원</span>";
                            inqrRelHtml += "										<span class='c-text c-text--type3'>월<b>"+v_promotionAmtVatDesc+"</b>원";
                            inqrRelHtml += "									</span> ";
                        } else {
                            inqrRelHtml += "									<span>";
                            inqrRelHtml += "										<span class='c-text c-text--type3'>월<b>"+v_mmBasAmtVatDesc+"</b>원";
                            inqrRelHtml += "									</span> ";
                        }
                        inqrRelHtml += "				</div>";
                        inqrRelHtml += "			</div>";
                        inqrRelHtml += "		</a>";
                        inqrRelHtml += "	</div>";
                        inqrRelHtml += "</div>";

                        $("#inqrRel").html(inqrRelHtml);
                    });

                    window.mainFunctions.initSurveySlide();

                }
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
            let returnCode = result.returnCode;
            let message = result.message;
            let rateScndCtgList = result.result;
            if(returnCode == "00") {
                pageObj.rateScndCtgList = rateScndCtgList;

                if(rateScndCtgList.length > 0) {
                    $("#sec_rate").show();
                    showRateList();
                }

                //console.log($("#mainUsimAcc > ul.c-accordion__box li.c-accordion__item").length);

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
        if ("Y" == rateScndCtgObj.linkUseYn) {
            arr.push(` <li class="c-accordion__item" onclick="location.href='${rateScndCtgObj.linkUrlPc}'" style="cursor: pointer">`);
        } else {
            arr.push(` <li class="c-accordion__item">`);
        }

        arr.push(`    <div class="c-accordion__head">`);
        arr.push(`        <div class="product__title-wrap">`);
        arr.push(`            <div class="product__title-img">`);
        if (rateScndCtgObj.recommendProdCtgImgPath != "") {
            arr.push(`                <img src="${rateScndCtgObj.recommendProdCtgImgPath}" alt="">`);
        }
        arr.push(`            </div>`);
        arr.push(`            <div class="product__title-info">`);
        arr.push(`                <strong class="product__title">${rateScndCtgObj.recommendProdCtgNm}</strong>`);
        arr.push(`                <p class="product__sub">${rateScndCtgObj.recommendProdCtgBasDesc}</p>`);
        arr.push(`            </div>`);
        arr.push(`        </div>`);

        if ("Y" != rateScndCtgObj.linkUseYn) {
            arr.push(`    <button class="runtime-data-insert c-accordion__button" id="mainUsimAcc_header_${index}" aria-controls="mainUsimAcc_content_${index}" aria-expanded="false">`);
            arr.push(`        <span class="c-hidden">${rateScndCtgObj.recommendProdCtgNm} 펼쳐보기</span>`);
            arr.push(`    </button>`)
        }
        arr.push(`    </div>`);

        if ("Y" != rateScndCtgObj.linkUseYn) {
            arr.push(`    <div class="c-accordion__panel" id="mainUsimAcc_content_${index}" aria-labelledby="mainUsimAcc_header_${index}" aria-hidden="true" style="height: 0px;">`);
            arr.push(`        <div class="c-accordion__inside">`);
            arr.push(`            <div class="rate-content">`);
            arr.push(`                <ul class="rate-content__list">`);


            let rateList = rateScndCtgObj.lists;
            $.each(rateList, function(idx, item) {
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

                arr.push(`                    <li class="rate-content__item">`);
                arr.push(`                        <a class="rate-info__wrap" href="/appForm/appFormDesignView.do?onOffType=5&operType=MNP3&indcOdrg=1&prdtSctnCd=LTE&prdtId=K7006048&rateCd=${v_rateCd}" title="요금제 이름 페이지 이동">`);
                arr.push(`                            <div class="rate-info__title">`);
                if (labelCode != "") {
                    arr.push(`                                <div class="rate-sticker-wrap">`);
                    if ("01" == labelCode) {
                        arr.push(`                                <span class="rate-sticker orange">★1등</span>`);
                    } else if ("02" == labelCode) {
                        arr.push(`                                <span class="rate-sticker orange">★2등</span>`);
                    } else if ("03" == labelCode) {
                        arr.push(`                                <span class="rate-sticker orange">★3등</span>`);
                    } else if ("91" == labelCode) {
                        arr.push(`                                <span class="rate-sticker bluegreen">NEW</span>`);
                    }else if ("92" == labelCode) {
                        arr.push(`                                <span class="rate-sticker red">BEST</span> `);
                    }
                    arr.push(`                                </div>`);
                }

                arr.push(`                                <strong>${v_rateAdsvcNm}</strong>`);
                arr.push(`                                <p class="rate-info__gift-info">${v_rateAdsvcBasDesc}</p>`);
                // arr.push(`                                <p class="rate-info__present-info">아무나 SOLO 결합 시 매월 평생 데이터 5GB 제공</p>`);
                arr.push(`                            </div>`);
                arr.push(`                            <div class="rate-detail">`);
                arr.push(`                                <ul class="rate-detail__list">`);
                arr.push(`                                    <li class="rate-detail__item">`);
                arr.push(`                                        <span>`);
                arr.push(`                                            <i class="c-icon c-icon--data" aria-hidden="true"></i>`);
                arr.push(`                                        </span>`);
                arr.push(`                                        <span class="c-hidden">데이터</span>`);
                arr.push(`                                        <div class="rate-detail__info">`);
                arr.push(`                                        <span class="max-data-label">`);
                if(v_maxDataDelivery != "") {
                    arr.push(`                                        <em>${v_maxDataDelivery}</em>`);
                }
                arr.push(`                                        </span>`);
                arr.push(`                                            <p>${v_bnfitData}</p>`);
                if(v_promotionBnfitData != "") {
                    arr.push(`                                        <p>${v_promotionBnfitData}</p>`);
                }
                arr.push(`                                        </div>`);
                arr.push(`                                    </li>`);
                arr.push(`                                    <li class="rate-detail__item">`);
                arr.push(`                                        <span>`);
                arr.push(`                                            <i class="c-icon c-icon--call" aria-hidden="true"></i>`);
                arr.push(`                                        </span>`);
                arr.push(`                                        <span class="c-hidden">음성</span>`);
                arr.push(`                                        <div class="rate-detail__info ">`);
                arr.push(`                                            <p>${v_bnfitVoice}</p>`);
                if(v_promotionBnfitVoice != "") {
                    arr.push(`                                        <p>${v_promotionBnfitVoice}</p>`);
                }

                arr.push(`                                        </div>`);
                arr.push(`                                    </li>`);
                arr.push(`                                    <li class="rate-detail__item">`);
                arr.push(`                                        <span>`);
                arr.push(`                                            <i class="c-icon c-icon--msg" aria-hidden="true"></i>`);
                arr.push(`                                        </span>`);
                arr.push(`                                        <span class="c-hidden">문자</span>`);
                arr.push(`                                        <div class="rate-detail__info ">`);
                arr.push(`                                            <p>${v_bnfitSms}</p>`);
                if(v_promotionBnfitSms != "") {
                    arr.push(`                                        <p>${v_promotionBnfitSms}</p>`);
                }
                arr.push(`                                        </div>`);
                arr.push(`                                    </li>`);
                arr.push(`                                </ul>`);
                arr.push(`                            </div>`);
                arr.push(`                            <div class="rate-price">`);
                arr.push(`                                <div class="rate-price-wrap">`);
                arr.push(`                                    <div class="rate-price__item">`);


                if(v_mmBasAmtVatDesc != "" && v_promotionAmtVatDesc != "") {
                    arr.push(`                                    <p class="rate-price__title through">`);
                    arr.push(`                                        <span class="c-hidden">월 기본료(VAT 포함)</span><b>${v_mmBasAmtVatDesc}</b> 원`);
                    arr.push(`                                    </p>`);
                    arr.push(`                                    <p class="rate-price__info">월 <b>${v_promotionAmtVatDesc}</b> 원</p>`);
                } else {
                    arr.push(`                                    <p class="rate-price__info">월 <b>${v_mmBasAmtVatDesc}</b> 원</p>`);;
                }

                arr.push(`                                    </div>`);
                arr.push(`                                </div>`);
                arr.push(`                            </div>`);
                arr.push(`                        </a>`);
                arr.push(`                    </li>`);
            })

            arr.push(`            </div>`);
            arr.push(`        </div>`);
            arr.push(`    </div>`);
        }
        arr.push(`</li>`);

        $("#mainUsimAcc > ul.c-accordion__box").append(arr.join(''));
    }

    let itemLength = $("#mainUsimAcc > ul.c-accordion__box li.c-accordion__item").length;

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
                    $("#rate_2").show();
                    $.each(rateAdsvcScndCtgList, function(index, value) {
                        let rateHtml = "";
                        let isActive = "";
                        let rateAdsvcList = value.lists;

                        if(index == 0) {
                            isActive = "is-active";
                        }

                        // 카테고리 목록 표시
                        rateScndCtgHtml += "<button class='c-tabs__button "+isActive+"' id='phone_tab"+(index+1)+"' role='tab' aria-controls='phone_panel"+(index+1)+"' aria-selected='false' tabindex='-1'>";
                        rateScndCtgHtml +=     value.recommendProdCtgNm;
                        rateScndCtgHtml += "</button>";

                        // 휴대폰 요금제 목록 표시
                        rateHtml += "<div class='c-tabs__panel' id='phone_panel"+(index+1)+"' role='tabpanel' aria-labelledby='phone_tab"+(index+1)+"' tabindex='-1'>";
                        rateHtml += "	<div class='phone-swiper-wrap'>";
                        rateHtml += "		<div class='phone-swiper swiper-container'>";
                        rateHtml += "			<div class='swiper-wrapper'>";

                        $.each(rateAdsvcList, function(idx, item) {
                            // 정책 만료된 요금제 미노출
                            if (!item.phoneProdBasDto) {
                                return true;
                            }

                            let v_recommendProdBasDesc = ajaxCommon.isNullNvl(item.recommendProdBasDesc, "");
                            let v_recommendProdDtlDesc = ajaxCommon.isNullNvl(item.recommendProdDtlDesc, "");
                            let v_imgPath = ajaxCommon.isNullNvl(item.phoneProdBasDto.imgPath, "");
                            let v_sntyProdNm = ajaxCommon.isNullNvl(item.phoneProdBasDto.prodNm, "-");
                            let v_baseAmt = ajaxCommon.isNullNvl(item.phoneProdBasDto.rentalBaseAmt, "-");
                            let v_instNom = ajaxCommon.isNullNvl(item.phoneProdBasDto.agrmTrmBase, "0");
                            let v_newYn = ajaxCommon.isNullNvl(item.newYn, "");
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

                            rateHtml += "				<div class='swiper-slide'>";
                            rateHtml += "					<div class='c-main-phone__item'>";
                            if ("0" == v_instNom) {
                                rateHtml += "						<a class='c-main-phone__anchor' href='/appForm/appFormDesignView.do?prodId="+v_prodId+"&hndsetModelId="+v_prdtId+"&rateCd="+v_rateCd+"&instNom=0' >";
                            } else {
                                rateHtml += "						<a class='c-main-phone__anchor' href='/appForm/appFormDesignView.do?prodId="+v_prodId+"&hndsetModelId="+v_prdtId+"&rateCd="+v_rateCd+"&instNom="+v_instNom+"' >";
                            }
                            // '신규' 표시
                            if(v_newYn == "Y") {
                                rateHtml += "                           <div class='c-main-phone__label'>";
                                rateHtml += "                               <i class='c-icon c-icon--card-new' aria-hidden='true'></i>";
                                rateHtml += "                               <span class='c-hidden'>NEW</span>";
                                rateHtml += "                           </div>";
                            }
                            rateHtml += "							<div class='c-main-phone__img'>";
                            rateHtml += "								<img src='"+v_imgPath+"' alt='"+v_sntyProdNm+"' onerror='phoneNoImage(this);'>";
                            rateHtml += "							</div>";
                            rateHtml += "							<div class='c-main-phone__panel'>";
                            rateHtml += "								<p class='c-main-phone__title'>"+v_sntyProdNm+"</p>";
                            // rateHtml += "								<p class='c-main-phone__text-2'>" + v_recommendProdDtlDesc + "</p>";
                            rateHtml += "								<p class='c-main-phone__text-2'>" + v_rateNm + "</p>"; // test
                            rateHtml += "								<div class='c-main-phone-infobox'>";
                            rateHtml += "									<dl class='c-main-phone-infobox__item'>";
                            rateHtml += "										<dt>월 단말요금</dt>";
                            rateHtml += "										<dd><b>" + numberWithCommas(v_payMnthAmt) + "</b> 원</dd>";
                            rateHtml += "									</dl>";
                            rateHtml += "									<dl class='c-main-phone-infobox__item'>";
                            rateHtml += "										<dt>월 통신요금</dt>";
                            rateHtml += "										<dd><b>" + numberWithCommas(v_baseVatAmt) + "</b> 원</dd>";
                            rateHtml += "									</dl>";
                            rateHtml += "								</div>";
                            rateHtml += "								<div class='c-main-phone__price-2'>";
                            rateHtml += "									월<strong>" +numberWithCommas(v_payMnthAmt + v_baseVatAmt)+ "</strong>원";
                            rateHtml += "								</div>";
                            rateHtml += "							</div>";
                            rateHtml += "						</a>";
                            rateHtml += "					</div>";
                            rateHtml += "				</div>";
                        });
                        rateHtml += "			</div>";
                        rateHtml += "		</div>";
                        rateHtml += "		<button class='phone-pager circle swiper-button-next u-mr--80' type='button'></button>";
                        rateHtml += "		<button class='phone-pager circle swiper-button-prev u-ml--80' type='button'></button>";
                        rateHtml += "	</div>";
                        rateHtml += "</div>";

                        $("#phone_tab").after(rateHtml);
                    });

                    $("#rateAdsvcScndCtgList").html(rateScndCtgHtml);
                    $("#phone_tab").attr("data-ui-tab", "");
                    KTM.initialize();
                    window.mainFunctions.phoneContent();
                }
            } else {
                MCP.alert(message);
            }
        });
}


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
            let rateScndCtgHtml = "";
            let returnCode = result.returnCode;
            let message = result.message;
            let rateAdsvcScndCtgList = result.result;

            if(returnCode == "00") {
                if(rateAdsvcScndCtgList.length > 0) {
                    $("#rate_2").show();
                    $.each(rateAdsvcScndCtgList, function(index, value) {
                        let rateHtml = "";
                        let isActive = "";
                        let rateAdsvcList = value.lists;

                        if(index == 0) {
                            isActive = "is-active";
                        }

                        // 카테고리 목록 표시
                        rateScndCtgHtml += "<button class='c-tabs__button "+isActive+"' id='phone_tab"+(index+1)+"' role='tab' aria-controls='phone_panel"+(index+1)+"' aria-selected='false' tabindex='-1'>";
                        rateScndCtgHtml +=     value.recommendProdCtgNm;
                        rateScndCtgHtml += "</button>";

                        // 휴대폰 요금제 목록 표시
                        rateHtml += "<div class='c-tabs__panel' id='phone_panel"+(index+1)+"' role='tabpanel' aria-labelledby='phone_tab"+(index+1)+"' tabindex='-1'>";
                        rateHtml += "	<div class='phone-swiper-wrap'>";
                        rateHtml += "		<div class='phone-swiper swiper-container'>";
                        rateHtml += "			<div class='swiper-wrapper'>";

                        //$.each(rateAdsvcList, function(idx, item) {
                        for(let idx =0 ; idx < rateAdsvcList.length ; idx++ ) {
                            let item = rateAdsvcList[idx];
                            let v_recommendProdBasDesc = ajaxCommon.isNullNvl(item.recommendProdBasDesc, "");
                            let v_recommendProdDtlDesc = ajaxCommon.isNullNvl(item.recommendProdDtlDesc, "");
                            //let v_imgPath = "https://www.ktmmobile.com/" +ajaxCommon.isNullNvl(item.phoneProdBasDto.rprtPhoneProdImgDto.phoneProdImgDetailDtoList[0].imgPath, "");
                            let v_imgPath = ajaxCommon.isNullNvl(item.phoneProdBasDto.rprtPhoneProdImgDto.phoneProdImgDetailDtoList[0].imgPath, "");
                            let v_sntyProdNm = ajaxCommon.isNullNvl(item.phoneProdBasDto.prodNm, "-");

                            let v_newYn = ajaxCommon.isNullNvl(item.newYn, "");
                            let chargeList = item.phoneProdBasDto.mspSaleSubsdMstListForLowPrice ;
                            if (chargeList == null || chargeList.length < 1 ) {
                                continue;
                            }

                            let v_prodId = ajaxCommon.isNullNvl(item.prodId, "");
                            let v_prdtId = ajaxCommon.isNullNvl(chargeList[0].prdtId, "");
                            let v_rateCd = ajaxCommon.isNullNvl(chargeList[0].rateCd, "");
                            let v_instNom = ajaxCommon.isNullNvl(chargeList[0].agrmTrm, "0");
                            // 추천 요금제 변수
                            let v_baseVatAmt = ajaxCommon.isNullNvl(chargeList[0].payMnthChargeAmt, 0);
                            let v_payMnthAmt = ajaxCommon.isNullNvl(chargeList[0].payMnthAmt, 0);  //(objList[0].payMnthAmt)
                            let v_rateNm = chargeList[0].rateNm;

                            rateHtml += "				<div class='swiper-slide'>";
                            rateHtml += "					<div class='c-main-phone__item'>";
                            if ("0" == v_instNom) {
                                rateHtml += "						<a class='c-main-phone__anchor' href='/appForm/appFormDesignView.do?prodId="+v_prodId+"&rateCd="+v_rateCd+"&instNom=0' >";
                            } else {
                                rateHtml += "						<a class='c-main-phone__anchor' href='/appForm/appFormDesignView.do?prodId="+v_prodId+"&rateCd="+v_rateCd+"&instNom="+v_instNom+"' >";
                            }
                            // '신규' 표시
                            if(v_newYn == "Y") {
                                rateHtml += "                           <div class='c-main-phone__label'>";
                                rateHtml += "                               <i class='c-icon c-icon--card-new' aria-hidden='true'></i>";
                                rateHtml += "                               <span class='c-hidden'>NEW</span>";
                                rateHtml += "                           </div>";
                            }
                            rateHtml += "							<div class='c-main-phone__img'>";
                            rateHtml += "								<img src='"+v_imgPath+"' alt='"+v_sntyProdNm+"' onerror='phoneNoImage(this);'>";
                            rateHtml += "							</div>";
                            rateHtml += "							<div class='c-main-phone__panel'>";
                            rateHtml += "								<p class='c-main-phone__title'>"+v_sntyProdNm+"</p>";
                            rateHtml += "								<p class='c-main-phone__text-2'>" + v_rateNm + "</p>"; // test
                            rateHtml += "								<div class='c-main-phone-infobox'>";
                            rateHtml += "									<dl class='c-main-phone-infobox__item'>";
                            rateHtml += "										<dt>월 단말요금</dt>";
                            rateHtml += "										<dd><b>" + numberWithCommas(v_payMnthAmt) + "</b> 원</dd>";
                            rateHtml += "									</dl>";
                            rateHtml += "									<dl class='c-main-phone-infobox__item'>";
                            rateHtml += "										<dt>월 통신요금</dt>";
                            rateHtml += "										<dd><b>" + numberWithCommas(v_baseVatAmt) + "</b> 원</dd>";
                            rateHtml += "									</dl>";
                            rateHtml += "								</div>";
                            rateHtml += "								<div class='c-main-phone__price-2'>";
                            rateHtml += "									월<strong>" +numberWithCommas(v_payMnthAmt + v_baseVatAmt)+ "</strong>원";
                            rateHtml += "								</div>";
                            rateHtml += "							</div>";
                            rateHtml += "						</a>";
                            rateHtml += "					</div>";
                            rateHtml += "				</div>";
                        }

                        rateHtml += "			</div>";
                        rateHtml += "		</div>";
                        rateHtml += "		<button class='phone-pager circle swiper-button-next u-mr--80' type='button'></button>";
                        rateHtml += "		<button class='phone-pager circle swiper-button-prev u-ml--80' type='button'></button>";
                        rateHtml += "	</div>";
                        rateHtml += "</div>";

                        $("#phone_tab").after(rateHtml);
                    });

                    $("#rateAdsvcScndCtgList").html(rateScndCtgHtml);
                    $("#phone_tab").attr("data-ui-tab", "");
                    KTM.initialize();
                    window.mainFunctions.phoneContent();
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
                            requestReviewHtml += "<li class='review-item'>";
                            requestReviewHtml += "<a role='button' href='javascript:void(0);' onclick='reviewMove("+index+")'>";
                        }else{
                            requestReviewHtml += "<li class='review-item'>";
                            requestReviewHtml += "<a role='button' href='javascript:void(0);' onclick='reviewMove("+index+")'>";
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

                                requestReviewHtml += "	<div class='review-img'>";
                                requestReviewHtml += "		<img class='reivew-item__img' src='"+v_filePathNm+"' alt='"+v_fileAlt+"' onerror='noImage(this);'>";
                                if(v_reviewImgList.length > 1) {
                                    requestReviewHtml += "		<span class='img-count'>+"+(v_reviewImgList.length-1)+"</span>";
                                }
                                requestReviewHtml += "	</div>";
                                if(idx == 0) {
                                    return false;
                                }
                            });
                        } else {
                            requestReviewHtml += "	<div class='review-img'>";
                            requestReviewHtml += "		<img class='reivew-item__img' src='/resources/images/portal/content/img_review_noimage.png' alt='no-image'>";
                            requestReviewHtml += "	</div>";
                        }

                        requestReviewHtml += "	<div class='review-item__info'>";
                        requestReviewHtml += "		<span class='name'>"+v_regNm+"님</span>";
                        requestReviewHtml += "		<span class='date'>"+v_sysRdateDd+"</span>";
                        requestReviewHtml += "	</div>";
                        requestReviewHtml += "</a>";
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



// 사용후기 이미지가 없을경우
function noImage(image) {
    image.onerror = "";
    image.src = "/resources/images/portal/content/img_review_noimage.png";
    return true;
}
// 휴대폰요금제 이미지가 없을경우
function phoneNoImage(image) {
    image.onerror = "";
    image.src = "/resources/images/portal/content/img_phone_noimage.png";
    return true;
}

// 배너조회 로그 추가
function addBannAccess(obj) {
    if(obj.bannSeq != "" && obj.bannCtg != "") {
        insertBannAccess(obj.bannSeq, obj.bannCtg);
    }
}

// 배너 링크
function bannerLink(obj){
    // 페이지 이동
    if(obj.linkUrl == "") {
        return;
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

// html 태그제거
function removeHtml(text) {
    text = text.replace(/<br>/ig, "\n"); // <br>을 엔터로 변경
    text = text.replace(/&nbsp;/ig, " "); // 공백
    // HTML 태그제거
    text = text.replace(/<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/ig, "");
    text = text.replace(/<(no)?script[^>]*>.*?<\/(no)?script>/ig, "");
    text = text.replace(/<style[^>]*>.*<\/style>/ig, "");
    text = text.replace(/<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>/ig, "");
    text = text.replace(/<\\w+\\s+[^<]*\\s*>/ig, "");
    text = text.replace(/&[^;]+;/ig, "");
    text = text.replace(/\\s\\s+/ig, "");

    return text;
}

var moveBestBoard = function (key, order) {
    sessionStorage.setItem("bestBoardKey", key);
    sessionStorage.setItem("bestBoardOrder", order != undefined ? parseInt(order) -1  : null);
    location.href = $('#mobileAppChk').val() != 'M' ? "/requestReView/requestReView.do" : "/m/requestReView/requestReView.do" ;
}

//사용후기 이동
function reviewMove(val) {
    if(val == undefined){
        location.href = "/requestReView/requestReView.do";
    }else{
        location.href = "/requestReView/requestReView.do?choice="+val;
    }
}
