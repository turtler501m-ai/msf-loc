var onlyMine = false;
var searchVal = '';
var reviewNo = 100;

$(document).ready(function() {

    $(".ly-footer").hide();
    var pageNo = $("#pageNo").val();
    if(!pageNo){pageNo = 1;}

    const promises = [getBannerList(), setReviewList(pageNo), bestReviewList()];

    Promise.allSettled(promises).then(() => {
        var filter = document.getElementsByName("reviewFilter");

        // 리뷰 이벤트 조회
        $("#selectEventReview").click(function () {
            $("#mcashReviewBoard").html("");
            $("#pageNo").val(1);

            setReviewList(1, getSelectedReviewFilter());
        });

        // 내 후기 보기
        $("#chkMyReview").change(function () {
            $("#mcashReviewBoard").html("");
            searchVal = '';

            if ($(this).is(":checked")) {
                filter.forEach(el => el.checked = false);
                onlyMine = true;
                setReviewList(1);
            } else {
                onlyMine = false;
                setReviewList(1);
            }
        });

        // 우수 후기 및 SNS 공유 후기 보기
        filter.forEach(el => el.addEventListener("click", function (data) {
            var isCheck = data.target.checked;
            filter.forEach(el => el.checked = false);
            $("#chkMyReview").attr('checked', false);
            data.target.checked = isCheck;

            if (isCheck) {
                searchVal = data.target.id;
                onlyMine = false;
                setReviewList(1, data.target.id);
            } else {
                searchVal = '';
                onlyMine = false;
                setReviewList(1);
            }
        }));

        $("#mcashReviewFormTab").click(function () {
            goReviewForm();
        });

        $(document).on("click", ".c-paging a", function () {
            if ($(this).attr("pageNo")) {
                $(window).scrollTop($("#accordionReview").offset().top - $("#mcashReviewTab").height());
                setReviewList($(this).attr("pageNo"), searchVal);
            }
        });

        // 메인 사용후기 타고 온경우
        var moveIndex = $("#reviewVal").val();
        var moveReviewInterval = setInterval((moveIndex) => {
            if (moveReview(moveIndex)) {
                clearInterval(moveReviewInterval);
            }
        }, 300, moveIndex);
        setTimeout(() => clearInterval(moveReviewInterval), 10000);
        history.replaceState({}, null, location.pathname);
    });
});

function goReviewForm() {
    var formUrl = "/mcash/review/mcashReviewForm.do";
    ajaxCommon.getItem({
        id: 'checkWriteAuthAjax',
        url: '/mcash/review/checkWriteAuthAjax.do',
        data: "",
        dataType: "json",
        cache: false
    }, function(jsonObj) {
        if (jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS) {
            location.href = formUrl;
        } else if (jsonObj.RESULT_CODE == '0001') {
            MCP.alert("M쇼핑할인 사용후기는 로그인 후 작성 가능합니다.", function() {
                location.href='/loginForm.do?redirectUrl='+encodeURIComponent(formUrl);
            });
        } else if (jsonObj.RESULT_CODE == '0002') {
            MCP.alert("M쇼핑할인에 가입해야<br/>사용후기 작성이 가능합니다.<br/>M쇼핑할인 가입을 부탁드립니다.");
        } else {
            MCP.alert(jsonObj.RESULT_MSG);
        }
    });
}

// 우수 사용후기 목록 조회
function bestReviewList() {
    var appendHtml = "";
    var varData = ajaxCommon.getSerializedData({
        reviewType : 'MCASH'
    });

    return ajaxCommon.getItem({
        id: 'bestReviewListAjax',
        url: '/review/bestReviewListAjax.do',
        data: varData,
        dataType: "json",
        cache: false
    }, function(jsonObj) {
        var reviewDtoList = jsonObj.bestReviewDtoList;
        var reviewImgList = "";
        var regstNm = "";
        var regstDt = "";
        var reviewContent = "";
        var snsInfo = "";
        var notiYn = "";
        var contractNum = "";
        var reviewSeq = "";
        var prizeRnk = "";

        if (reviewDtoList.length < 3) {
            return false;
        }

        reviewDtoList = reviewDtoList.slice(0, 3);

        var reviewListSize = reviewDtoList.length;

        var bestReviewHtml = "";
        bestReviewHtml += `<div class="review-rank" style="background-color: #ebeff8;">`;
        bestReviewHtml += `  <div class="review-rank-wrap">`;
        bestReviewHtml += `	<h3 class="review-rank__title" style="justify-content: space-between; font-family: inherit; align-items: center">`;
        bestReviewHtml += `    <div style="min-width: 30%;"></div>`;
        bestReviewHtml += `    <img src="/resources/images/portal/product/mcash_review_rank_title.png" alt="M쇼핑할인 우수 사용후기" style="height: min-content;">`;
        bestReviewHtml += `    <div style="min-width: 30%;">`;
        bestReviewHtml += `        <a class="link-all" href="/mcash/mcashView.do" title="M쇼핑할인 페이지로 이동" style="float: right;">`;
        bestReviewHtml += `            M쇼핑할인 이용하기<i class="c-icon c-icon--arrow-link" aria-hidden="true"></i>`;
        bestReviewHtml += `        </a>`;
        bestReviewHtml += `    </div>`;
        bestReviewHtml += `	</h3>`;
        bestReviewHtml += `	<ul class="review-rank__list">`;
        bestReviewHtml += `	</ul>`;
        bestReviewHtml += `  </div>`;
        bestReviewHtml += `</div>`;

        $("#tabs").after(bestReviewHtml);

        for (var i = 0; i < reviewListSize; i++) {
            reviewImgList = reviewDtoList[i].reviewImgList;
            regstNm = reviewDtoList[i].mkRegstNm;
            regstDt = reviewDtoList[i].regstDt;
            reviewContent = unescapeHtml(reviewDtoList[i].reviewContent);
            snsInfo = ajaxCommon.isNullNvl(reviewDtoList[i].snsInfo, "");
            notiYn = reviewDtoList[i].notiYn;
            contractNum = reviewDtoList[i].contractNum;
            reviewSeq = reviewDtoList[i].reviewSeq;
            prizeRnk = reviewDtoList[i].prizeRnk;

            appendHtml += "<li class='review-rank__item'>";
            appendHtml += "<a id='reviewMoves" + prizeRnk + "' href='javascript:void(0);' onclick='moveReview(" + prizeRnk + ");' title='사용후기 게시글 이동'>";
            appendHtml += "<span class='review-rank__label' style='background-color: #002060;'>★"+prizeRnk+"등</span>";
            appendHtml += "<pre><p class='review-rank__text'>" + reviewContent + " </p></pre>";
            appendHtml += "<br/>";

            if (notiYn == 'Y') {
                if (prizeRnk < 4 && prizeRnk != null && prizeRnk != "") {
                    if (reviewImgList.length > 0) {
                        var firstImage = reviewImgList[0];
                        var filePathNm = firstImage.filePathNm;
                        var fileAlt = ajaxCommon.isNullNvl(firstImage.fileAlt, "");

                        appendHtml += "  <div class='review-rank__img'>";
                        appendHtml += "      <img src='"+filePathNm+"' alt='"+fileAlt+"' onerror='noImage(this);'>";
                        if (reviewImgList.length > 1) {
                            appendHtml += "      <span class='review__image__label'>+"+(reviewImgList.length - 1)+"</span>";
                        }
                        appendHtml += "  </div>";
                    } else {
                        appendHtml += "	<div class='review-rank__img'>";
                        appendHtml += "		<img src='/resources/images/portal/content/img_review_noimage.png' alt='no-image'>";
                        appendHtml += "	</div>";
                    }

                    appendHtml += '</a>';
                    appendHtml += '</li>';
                }
            }
        }
        $(".review-rank__list").append(appendHtml);
    });
}

// 리뷰 리스트
function setReviewList(pageNo, targetId) {
    var reviewTypeDtl = ajaxCommon.isNullNvl($("select[name=selReviewType]").val(), "");
    var varData = ajaxCommon.getSerializedData({
        pageNo: pageNo,
        reviewType: 'MCASH',
        reviewTypeDtl: reviewTypeDtl,
        onlyMine: onlyMine,
        searchCategory: targetId
    });

    return ajaxCommon.getItem({
        id: 'reviewListAjax',
        url: '/review/reviewListAjax.do',
        data: varData,
        dataType: "json",
        cache: false
    }, function(jsonObj) {
        var total = jsonObj.total;
        var reviewDtoList = jsonObj.reviewDtoList;
        var contractNumList = jsonObj.contractNumList;
        var reviewTypeTotal = jsonObj.reviewTypeTotal;
        var reviewEventCdList = jsonObj.reviewEventCdList;
        var appendHtml = "";

        ajaxCommon.setPortalPaging($("#paging"), jsonObj.pageInfoBean);

        $("#selReviewType").html("");
        appendHtml += "<option value=''>전체보기(" + (reviewTypeTotal*1).toLocaleString("ko-KR") + ")</option>";
        for (var i = 0; i < reviewEventCdList.length; i++) {
            appendHtml += "<option value='" + reviewEventCdList[i].dtlCd + "'>" + reviewEventCdList[i].dtlCdNm + "(" + (reviewEventCdList[i].eventCdCtn*1).toLocaleString("ko-KR") +")</option>";
        }
        $("#selReviewType").append(appendHtml);

        reviewDesc(total, reviewDtoList, contractNumList);
        $("#selReviewType option[value='" + reviewTypeDtl + "']").prop("selected", true);
    });
}

function reviewDesc(total, reviewDtoList, contractNumList) {
    $("#mcashReviewBoard").html("");
    $("#nodata").remove();
    var appendHtml = "";

    if ((!contractNumList || contractNumList.length == 0) && onlyMine) {
        appendHtml += "<div class='c-nodata' id='nodata'>";
        appendHtml += "   <i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
        appendHtml += "   <p class='c-nodata__text'>조회 결과가 없습니다.</p>";
        appendHtml += "</div>";
        $(".c-row--lg").append(appendHtml);

        $("#paging").hide();
        MCP.alert("조회된 결과가 없습니다.");
        sessionStorage.clear();
    } else {
        if (reviewDtoList != null && reviewDtoList.length > 0) {

            reviewDtoList.forEach(function(reviewDto, index) {
                appendHtml += reviewListForm(reviewDto);
            });

            $("#mcashReviewBoard").append(appendHtml);
            if (total > 20) {
                $("#paging").show();
            } else {
                $("#paging").hide();
            }
            window.KTM.initialize();
        } else {
            appendHtml += "<div class='c-nodata' id='nodata'>";
            appendHtml += "   <i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
            appendHtml += "   <p class='c-nodata__text'>조회 결과가 없습니다.</p>";
            appendHtml += "</div>";

            $(".c-row--lg").append(appendHtml);
            $("#paging").hide();
            MCP.alert("조회된 결과가 없습니다.");
            sessionStorage.clear();
        }
    }

    $(".delete-button-in").click(function() {
        var reviewSeq = $(this).attr("reviewSeq");

        if (confirm("작성한 후기를 삭제하시겠습니까?")) {
            deleteMcashReview(reviewSeq);
        }
    });

    $("#subbody_loading").hide();
    $(".ly-footer").show();

}

// 리뷰 리스트 Form
function reviewListForm(reviewDto) {
    var appendHtml = "";

    var mcashReviewImgList = reviewDto.reviewImgList;
    var reviewSeq = reviewDto.reviewSeq;
    var contractNum = reviewDto.contractNum;
    var reviewTypeNm = reviewDto.reviewTypeNm;
    var regstNm = reviewDto.mkRegstNm;
    var regstDt = reviewDto.regstDt;
    var reviewContent = unescapeHtml(reviewDto.reviewContent);
    var prizeRnk = reviewDto.prizeRnk;
    var snsInfo = ajaxCommon.isNullNvl(reviewDto.snsInfo, "");
    var notiYn = reviewDto.notiYn;
    var bestYn = reviewDto.bestYn;
    var isOwn = reviewDto.isOwn;

    appendHtml += "<li class='c-accordion__item'>";
    appendHtml += "<div class='c-accordion__head'>";
    appendHtml += "<div class='review__label-wrap' id='acc_label_c"+reviewNo+"'>";

    if (notiYn == 'Y') {
        if (prizeRnk < 4 && prizeRnk != null && prizeRnk != "") {
            appendHtml += "<span class='c-text-label c-text-label--rank' id='reviewBoard"+prizeRnk+"' style='background-color: #002060;'>★" + prizeRnk + "등</span>";
        } else if (bestYn == 'Y') {
            appendHtml += "<span class='c-text-label c-text-label--red'>BEST</span>";
        } else {
            appendHtml += "<span class='c-text-label c-text-label--mint-type2'>후기</span>";
        }
    } else if (bestYn == 'Y') {
        appendHtml += "<span class='c-text-label c-text-label--red'>BEST</span>";
    } else {
        appendHtml += "<span class='c-text-label c-text-label--mint-type2'>후기</span>";
    }

    appendHtml += "</div>";
    appendHtml += "<ul class='review__info'>";
    appendHtml += "<li class='review__info__item'>";
    appendHtml += "<li class='review__info__item'>";
    appendHtml += "<span class='sr-only'>작성자</span>"+regstNm;
    appendHtml += "</li>";
    appendHtml += "<li class='review__info__item'>";
    appendHtml += "<span class='sr-only'>작성일</span>"+regstDt;
    appendHtml += "</li>";

    if (isOwn) {
        appendHtml += "<li class='review__info__item'>";
        appendHtml += "<button class='c-button'>";
        appendHtml += "<span class='sr-only'>삭제</span>";
        appendHtml += "<i class='c-icon c-icon--trash delete-button-in' aria-hidden='true'  contractNum='" + contractNum + "' reviewSeq='" + reviewSeq + "'></i>";
        appendHtml += "</button>";
        appendHtml += "</li>";
    }
    appendHtml += "</ul>";

    appendHtml += "<strong class='review__title'>" + reviewTypeNm + "</strong>";

    appendHtml += "<button class='runtime-data-insert c-accordion__button review-accordion' id='acc_header_"+reviewNo+"' type='button' reviewSeq='" + reviewSeq + "' aria-expanded='false' aria-controls='acc_content_c"+reviewNo+"' >";
    appendHtml += "<span class='c-hidden'>" + reviewTypeNm + " + 전체보기</span>";
    appendHtml += "</button>";
    appendHtml += "</div>";

    appendHtml += "<div class='c-accordion__panel expand' id='acc_content_c"+reviewNo+"' aria-labelledby='acc_header_c"+reviewNo+"'>";

    appendHtml += "<div class='c-accordion__inside'>";
    appendHtml += "<div>";
    appendHtml += "<div class='review__content'>";

    if (mcashReviewImgList != null && mcashReviewImgList.length > 0) {
        appendHtml += "<div class='review__preview'>";
        appendHtml += "<img src='"+ mcashReviewImgList[0].filePathNm +"' alt='' onerror='this.src=\"/resources/images/portal/content/img_review_noimage.png\"'>";

        if (mcashReviewImgList.length > 1) {
            appendHtml += "<span class='review__image__label'>";
            appendHtml += "<span class='sr-only'>썸네일사진 외 추가로 등록된 후기사진 개수</span>+" + (mcashReviewImgList.length - 1);
            appendHtml += "</span>";
        }
        appendHtml += "</div>";
    }

    appendHtml += "<pre><p class='review__text'>" + reviewContent + "</p><pre>";
    if (snsInfo != "") {
        var snsLink = "";
        if (snsInfo.indexOf("http") < 0) {
            snsLink = 'https://' + snsInfo;
        } else {
            snsLink = snsInfo;
        }
        appendHtml += "<a class='review__button' href='" + snsLink + "' target='_blank' title='새창'>SNS 공유 사용후기 보기</a>";
    }

    if (mcashReviewImgList != null && mcashReviewImgList.length > 0) {
        appendHtml += "<ul class='review__images2' id='content_img_" + reviewNo + "'>";
        for (var k = 0; k < mcashReviewImgList.length; k++) {
            appendHtml += "<li class='review__images__item'>";
            appendHtml += "<div class='review__image'>";
            appendHtml += "<img src='" + mcashReviewImgList[k].filePathNm + "' alt='" + mcashReviewImgList[k].fileAlt + "'  aria-hidden='true'  onerror='this.src=\"/resources/images/portal/content/img_review_noimage.png\"'>";
            appendHtml += "</div>";
            if (mcashReviewImgList[k].fileAlt) {
                appendHtml += "<p class='c-bullet c-bullet--dot u-co-gray'>" + mcashReviewImgList[k].fileAlt + "</p>";
            }
            appendHtml += "</li>";
        }
        appendHtml += "</ul>";
    }

    appendHtml += "</div>";
    appendHtml += "</div>";
    appendHtml += "</div>";
    appendHtml += "</div>";
    appendHtml += "</li>";

    reviewNo++;
    return appendHtml;
}

// 리뷰 삭제
function deleteMcashReview(reviewSeq) {
    var varData = ajaxCommon.getSerializedData({
        reviewSeq: reviewSeq
    });

    ajaxCommon.getItem({
        id: 'deleteReviewAjax',
        url: '/review/deleteReviewAjax.do',
        data: varData,
        dataType: "json",
        cache: false,
        async: false
    }, function(jsonObj) {
        if (jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS) {
            MCP.alert("후기 삭제가 완료되었습니다.", function() {
                $("#mcashReviewBoard").html("");
                $("#pageNo").val(1);
                setReviewList(1);
            });
        } else {
            MCP.alert("후기 삭제에 실패하였습니다. 다시 시도하여 주시기바랍니다.");
        }
    });
}

// 배너 호출 ajax
function getBannerList() {
    var varData = ajaxCommon.getSerializedData({
       bannCtg: 'C005'
    });

    return ajaxCommon.getItem({
        id: 'bannerListAjax',
        url: '/bannerListAjax.do',
        data: varData,
        dataType: "json",
        cache: false,
        async: false
    }, function(result) {
        var bannerList = result.result;

        if (bannerList && bannerList.length > 0) {
            var html = "";

            $.each(bannerList, function(index, banner) {
                html += `<div class='swiper-slide' addr='${banner.linkUrlAdr}' style='background-color: ${banner.bgColor}'>`;
                html += `    <a class="swiper-banner__anchor" href="#" onclick="insertBannAccess('${banner.bannSeq}', '${banner.bannCtg}');" target="`;
                if(banner.linkTarget == 'Y'){
                    html += '_blank" title="새창열림">';
                }else{
                    html += '_self">';
                }
                html += `        <img src="${banner.bannImg}" alt="${banner.imgDesc}">`;
                html += `    </a>`;
                html += `</div>`;
                html += `<input type='hidden' value='${banner.bannSeq}'>`;
            });

            $("#swiperArea").append(html);

            $(".swiper-slide").on("click", function(){
                var parameterData = ajaxCommon.getSerializedData({
                    eventPopTitle : 'M쇼핑할인 사용후기 이벤트'
                });
                $("#eventPop").remove();
                openPage('eventPop', $(this).attr("addr"), parameterData)
                $("#modalCardEventTitle").text("M쇼핑할인 사용후기 이벤트");
                KTM.initialize();
            });

            if (result.result.length == 1) {
                $("#swiperReviewBanner").find("button").hide();
            } else {
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

        } else {
            $("#swiperReviewBanner").hide();
        }

    });
}

// M쇼핑할인 사용후기 이미지가 없을 경우
function noImage(img) {
    img.onerror = "";
    img.src = "/resources/images/portal/content/img_review_noimage.png";

    return true;
}

function getSelectedReviewFilter() {
    var targetId = '';
    $.each($("input[name=reviewFilter]"), function(index, item) {
       var checkbox = $(item);
       if (checkbox.is(":checked")) {
           targetId = checkbox.attr("id");
           return false;
       }
    });
    return targetId;
}

function moveReview(moveIndex) {
    if(!moveIndex || moveIndex < 1){
        return false;
    }

    var reviewBoard = $("#reviewBoard" + moveIndex);
    var reviewWrap = reviewBoard.parent().parent();
    var reviewBtn = reviewWrap.find(".review-accordion");

    if (!reviewBoard || !reviewWrap || !reviewWrap.offset() || !reviewBtn) {
        return false;
    }

    if (!reviewBtn.hasClass('is-active')) {
        reviewBtn.trigger('click');
    }

    var scrollIndex = reviewWrap.offset().top - $("#mcashReviewTab").height();
    $(window).scrollTop(scrollIndex);
    return true;
}

function fn_go_banner(linkUrl, bannSeq, bannCtg, tgt){
    if(!linkUrl) return;
    insertBannAccess(bannSeq, bannCtg);

    if(tgt.trim() == 'Y'){
        window.open(linkUrl);
    }else{
        window.location.href = linkUrl;
    }
}
