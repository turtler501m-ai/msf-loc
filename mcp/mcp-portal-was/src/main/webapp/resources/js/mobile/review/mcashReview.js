var onlyMine = false;
var searchVal = '';
var reviewNo = 100;

$(document).ready(function() {
    var pageNo = $("#pageNo").val();
    if(!pageNo){pageNo = 1;}
    const promises = [getBannerList(), setReviewList(pageNo), bestReviewList()];

    Promise.allSettled(promises).then(() => {
        var filter = document.getElementsByName("reviewFilter");

        // 리뷰 이벤트 조회
        $("#selReviewType").change(function() {
            $("#mcashReviewBoard").html("");
            $("#pageNo").val(1);

            setReviewList(1, getSelectedReviewFilter());
        });

        // 내 후기만 보기 체크박스 선택
        $("#chkMyReview").change(function() {
            $("#mcashReviewBoard").html("");
            $("#pageNo").val(1);
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
        var filter = document.getElementsByName("reviewFilter");
        filter.forEach(el => el.addEventListener("click", function(data) {
            $("#mcashReviewBoard").html("");
            var isCheck = data.target.checked;
            filter.forEach(el => el.checked = false);
            $("#chkMyReview").attr("checked", false);
            data.target.checked = isCheck;

            if (isCheck) {
                searchVal = data.target.id;
                onlyMine = false;
                setReviewList(1, data.target.id);
            } else {
                searchVal = "";
                onlyMine = false;
                setReviewList(1);
            }
        }));

        // 더보기
        $("#moreBtn").click(function() {
            var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(), "1"));
            setReviewList(pageNo+1);
            $("#pageNo").val(pageNo+1);
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
    var formUrl = "/m/mcash/review/mcashReviewForm.do";
    ajaxCommon.getItem({
        id: 'checkWriteAuthAjax',
        url: '/mcash/review/checkWriteAuthAjax.do',
        data: "",
        dataType: "json",
        cache: false,
        async: false
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

// 리뷰 목록 조회
var setReviewList = function(pageNo, targetId) {
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
        cache: false,
    }, function(jsonObj) {
        var total = jsonObj.total;
        var reviewDtoList =  jsonObj.reviewDtoList;
        var contractNumList = jsonObj.contractNumList;
        var reviewTypeTotal = jsonObj.reviewTypeTotal;
        var reviewEventCdList = jsonObj.reviewEventCdList;
        var appendHtml = "";

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
    $("#nodata").remove();
    var appendHtml = "";

    if ((!contractNumList || contractNumList.length == 0) && onlyMine) {
        $("#moreBtn").hide();
        appendHtml += "<div class='c-nodata' id='nodata'>";
        appendHtml += "   <i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
        appendHtml += "   <p class='c-nodata__text'>조회 결과가 없습니다.</p>";
        appendHtml += "</div>";
        $("#mcashReviewBoard").prepend(appendHtml);
        MCP.alert("조회된 결과가 없습니다.");
        sessionStorage.clear();
    } else {
        if (reviewDtoList != null && reviewDtoList.length > 0) {

            reviewDtoList.forEach(function(reviewDto) {
                appendHtml += reviewListForm(reviewDto);
            });

            $("#mcashReviewBoard").append(appendHtml);
            if (reviewDtoList.length >= 20) {
                $("#moreBtn").show();
            } else {
                $("#moreBtn").hide();
            }
            window.KTM.initialize();
        } else {
            $("#moreBtn").hide();
            appendHtml += "<div class='c-nodata' id='nodata'>";
            appendHtml += "   <i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
            appendHtml += "   <p class='c-nodata__text'>조회 결과가 없습니다.</p>";
            appendHtml += "</div>";
            $("#reviewBody").prepend(appendHtml);
            MCP.alert("조회된 결과가 없습니다.");
        }
    }

    // 더보기
    var reviewCurrent = $(".review__item").length;
    $("#reviewCurrent").text((reviewCurrent*1).toLocaleString('ko-KR')); // 현재

    $("#reviewTotal").text((total*1).toLocaleString('ko-KR')); // 노출
    if (reviewCurrent == total) {
        $("#moreBtn").hide();
    }

    // 리뷰 삭제하기
    $(".delete-button-in").click(function() {
        var reviewSeq = $(this).attr("reviewSeq");

        if (confirm("작성한 후기를 삭제하시겠습니까?")) {
           deleteReview(reviewSeq);
        }
    });
}

function reviewListForm(reviewDto) {
    var appendHtml = "";

    var mcashReviewImgList = reviewDto.reviewImgList;
    var reviewSeq = reviewDto.reviewSeq;
    var contractNum = reviewDto.contractNum;
    var reviewTypeNm = reviewDto.reviewTypeNm;
    var regstNm = reviewDto.mkRegstNm;
    var regstDt = reviewDto.regstDt;
    var reviewContent = unescapeHtml(reviewDto.reviewContent);
    var prizeRnk = reviewDto.prizeRnk ;
    var snsInfo = ajaxCommon.isNullNvl(reviewDto.snsInfo, "");
    var notiYn = reviewDto.notiYn;
    var bestYn = reviewDto.bestYn;
    var isOwn = reviewDto.isOwn;

    appendHtml += "<li class='review__item' id=acc_label_c"+reviewNo+">";
    appendHtml += "<div class='label-wrap'>";
    if (notiYn == "Y") {
        if (prizeRnk < 4 && prizeRnk != null && prizeRnk != "") {
            appendHtml += "<span class='c-text-label c-text-label--rank' id='reviewBoard"+prizeRnk+"' style='background-color: #002060'>★" + prizeRnk + "등</span>";
        } else if (bestYn == 'Y') {
            appendHtml += "<span class='c-text-label c-text-label--red'>BEST</span>";
        } else {
            appendHtml += "<span class='c-text-label c-text-label--mint-type1'>후기</span>";
        }
    } else if (bestYn == 'Y') {
        appendHtml += "<span class='c-text-label c-text-label--red'>BEST</span>";
    } else {
        appendHtml += "<span class='c-text-label c-text-label--mint-type1'>후기</span>";
    }

    if (isOwn) {
        appendHtml += "<span class='c-text-label c-text-label--gray-type1 delete-button-in' contractNum='" + contractNum +"' reviewSeq='" + reviewSeq +"' style='float:right;'>삭제</span>";
    }
    appendHtml += "</div>";
    appendHtml += "<h3 class='review__title'>" + reviewTypeNm + "</h3>";
    appendHtml += "<div class='review__info'>";

    if (regstNm != "") {
        appendHtml += "<span class='review__user'>" + regstNm +"</span>";
        appendHtml += "<span class='review__date'>" + regstDt + "</span>";
    } else {
        appendHtml += "<span class='review__user'>" + regstDt + "</span>";
    }
    appendHtml += "</div>";

    appendHtml += "<div class='review__content fs-13 c-text-ellipsis--type3' id='content_" + reviewNo + "'>";

    appendHtml += "<pre>" + reviewContent +"</pre>";
    if (snsInfo != "") {
        var snsLink = "";
        if (snsInfo.indexOf("http") < 0) {
            snsLink = 'https://' + snsInfo;
        } else {
            snsLink = snsInfo;
        }
        if ($("#platFormCd").val() == "A") {
            appendHtml += "<a class='review__button' href='javascript:appOutSideOpen('" + snsLink + "');'>SNS 공유 사용후기 보기</a></p>"
        } else {
            appendHtml += "<a class='review__button' href='" + snsLink + "' target='_blank'>SNS 공유 사용후기 보기</a></p>";
        }
    }
    appendHtml += "</div>";


    if (mcashReviewImgList != null && mcashReviewImgList.length > 0) {
        appendHtml += "<div class='review__image' id='content_img_" + reviewNo + "'>";

        for (var i = 0; i < mcashReviewImgList.length; i++) {
            appendHtml += "<div class='review__image__item full-width'>";
            appendHtml += " <img src='" + mcashReviewImgList[i].filePathNm + "' alt='" + mcashReviewImgList[i].filaAlt + "' onerror='this.src=\"/resources/images/mobile/content/img_review_noimage.png\"'>";
            appendHtml += "<p class='c-bullet c-bullet--dot u-co-gray'>" + mcashReviewImgList[i].fileAlt + "</p>";

            if (i == 0 && mcashReviewImgList.length > 1) {
                appendHtml += "<span class='review__image__label'>+" + (mcashReviewImgList.length-1) + "</span>";
            }
            appendHtml += "</div>";
        }

        appendHtml += "</div>";
    }

    if (mcashReviewImgList != null && mcashReviewImgList.length > 0) {
        appendHtml += "<div class='review__button-wrap'  reviewSeq ='" + reviewSeq + "' data-toggle-class='is-active' data-toggle-target='#content_img_" + reviewNo + "|#content_" + reviewNo + "'>";
    } else {
        appendHtml += "<div class='review__button-wrap' reviewSeq='" + reviewSeq + "' data-toggle-class='is-active' data-toggle-target='#content_" + reviewNo + "'>";
    }

    appendHtml += "<button type='button' aria-expanded='false'>";
    appendHtml += " <i class='c-icon c-icon--arrow-down-bold' aria-hidden='true'></i>";
    appendHtml += "</button>";
    appendHtml += "</div>";
    appendHtml += "</li>";

    reviewNo++;
    return appendHtml;
}

// 리뷰 삭제
function deleteReview(reviewSeq) {
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

// 우수 사용 후기 목록 조회
var bestReviewList = function() {
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
        var regstNm = ""
        var regstDt = "";
        var reviewContent = "";
        var snsInfo = "";
        var notiYn = "";
        var contractNum = "";
        var prizeRnk = "";

        if (reviewDtoList.length < 3) {
            return false;
        }

        reviewDtoList = reviewDtoList.slice(0, 3);

        var reviewListSize = reviewDtoList.length;

        var bestReviewHtml = "";
        bestReviewHtml += `<div class="review-rank" style="background-color: #ebeff8">`;
        bestReviewHtml += `	<div class="review-rank-wrap">`;
        bestReviewHtml += `	    <div class="c-flex--between u-plr--16">`;
        bestReviewHtml += `		    <h3 class="review-rank__title">`;
        bestReviewHtml += `			    <img src="/resources/images/mobile/product/mcash_review_rank_title.png" alt="M쇼핑할인 사용후기">`;
        bestReviewHtml += `		    </h3>`;
        bestReviewHtml += `         <div>`;
        bestReviewHtml += `            <a class="mcash-btn u-pr--10" href="/m/mcash/mcashView.do" title="M쇼핑할인 페이지로 이동">`;
        bestReviewHtml += `                M쇼핑할인 <i class="c-icon c-icon--arrow-link" aria-hidden="true"></i>`;
        bestReviewHtml += `            </a>`;
        bestReviewHtml += `         </div>`;
        bestReviewHtml += `	    </div>`;
        bestReviewHtml += `		<ul class="review-rank__list">`;
        bestReviewHtml += `		</ul>`;
        bestReviewHtml += `	</div>`;
        bestReviewHtml += `</div>`;
        $("#tabA1-panel").prepend(bestReviewHtml);

        for (var i = 0; i < reviewListSize; i++) {
            reviewImgList = reviewDtoList[i].reviewImgList;
            regstNm = reviewDtoList[i].mkRegstNm;
            regstDt = reviewDtoList[i].regstDt;
            reviewContent = reviewDtoList[i].reviewContent;
            snsInfo = reviewDtoList[i].snsInfo;
            notiYn = reviewDtoList[i].notiYn;
            contractNum = reviewDtoList[i].contractNum;
            prizeRnk = reviewDtoList[i].prizeRnk;

            if (prizeRnk < 4 && prizeRnk != null && prizeRnk != "") {
                appendHtml += "<li class='review-rank__item'>";
                appendHtml += "<a id='reviewMoves" + prizeRnk + "' href='javascript:void(0);' onclick='moveReview(" + prizeRnk + ");' title='사용후기 게시글 이동'>";
                appendHtml += "<span class='review-rank__label' style='background-color: #002060;'>★" + prizeRnk+ "등</span>";
                appendHtml += "<pre><p class='review-rank__text'>" + reviewContent + " </p></pre>";
                appendHtml += "<br/>";
            }

            if (notiYn == "Y") {
                if (prizeRnk < 4 && prizeRnk != null && prizeRnk != "") {
                    if (reviewImgList.length > 0) {
                        var firstImage = reviewImgList[0];
                        let filePathNm = firstImage.filePathNm;
                        let fileAlt = ajaxCommon.isNullNvl(firstImage.fileAlt, "");
                        appendHtml += " <div class='review-rank__img' alt=''>";

                        if (reviewImgList.length > 1) {
                            appendHtml += "<span class='review__image__label'>+"+(reviewImgList.length - 1)+"</span>";
                        }
                        appendHtml += "    <img src='"+filePathNm+"' alt='"+fileAlt+"' onerror='noImage(this);'>";
                        appendHtml += " </div>";
                    } else {
                        appendHtml += " <div class='review-rank__img' alt=''>";
                        appendHtml += "     <img src='/resources/images/mobile/content/img_review_noimage.png' alt='no-image'>";
                        appendHtml += " </div>";
                    }

                    appendHtml += "</a>";
                    appendHtml += "</li>";
                }
            }
        }

        $(".review-rank__list").append(appendHtml);
    });
}

// 배너 호출 ajax
var getBannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'C005'
    });

    return ajaxCommon.getItem({
        id : 'bannerListAjax'
        , cache : false
        , url : '/m/bannerListAjax.do'
        , data : varData
        , dataType : "json"
    }
    ,function(result){
        var bannerList = result.result;

        if(bannerList && bannerList.length > 0){
            var html = "";
            $.each(bannerList, function(index, banner) {
                html += `<div class='swiper-slide' href='javascript:void(0);' onclick='insertBannAccess("${banner.bannSeq}", "${banner.bannCtg}")' addr='${banner.mobileLinkUrl}' style='background-color: ${banner.bgColor}'>`;
                html += `<button class="swiper-event-banner__button">`;
                html += `<div class="swiper-event-banner__item">`;
                html += `<img src='${banner.mobileBannImgNm}' alt='${banner.imgDesc}'>`;
                html += `</div>`;
                html += `</button>`;
                html += `</div>`;
                html += `<input type='hidden' value='${banner.bannSeq}'>`;
            });

            $(".swiper-wrapper").append(html);

            var swiperCardBanner = new Swiper("#swiperCardBanner", {
                spaceBetween: 10,
            });

            $(".swiper-slide").on("click", function(){
                var parameterData = ajaxCommon.getSerializedData({
                    eventPopTitle : 'M쇼핑할인 사용후기 이벤트'
                });
                $("#eventPop").remove();
                openPage('eventPop', $(this).attr("addr"), parameterData)
                $("#invite-event-title").text("M쇼핑할인 사용후기 이벤트");
            });
            $("#swiperCardBanner").show();
        } else{
            $("#swiperCardBanner").hide();
        }
    });
}

// 이미지가 없을경우
function noImage(image) {
    image.onerror = "";
    image.src = "/resources/images/mobile/content/img_review_noimage.png";
    return true;
}

function getSelectedReviewFilter() {
    var targetId = '';
    $.each($("input[name=reviewFilter]"), function (index, item) {
        var checkbox = $(item);
        if (checkbox.is(':checked')) {
            targetId = checkbox.attr('id');
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
    var reviewBtn = reviewWrap.find(".review__button-wrap");

    if (!reviewBoard || !reviewWrap || !reviewWrap.offset() || !reviewBtn) {
        return false;
    }

    if (!reviewBtn.hasClass('is-active')) {
        reviewBtn.trigger('click');
    }

    var scrollIndex = reviewWrap.offset().top - 120;
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

