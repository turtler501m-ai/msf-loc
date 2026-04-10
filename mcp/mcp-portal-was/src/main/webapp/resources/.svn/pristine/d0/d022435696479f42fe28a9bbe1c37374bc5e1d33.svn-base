history.pushState(null, null, location.href);
window.onpopstate = function (event) {
    location.href = "/m/coupon/couponMain.do";
}

$(document).ready(function () {
    categoryAll();

});

//체크박스 초기화
window.addEventListener('pageshow', function (event) {
    $('input[type="checkbox"]').prop('checked', false);
});

//
function fn_go_banner(linkUrl, bannSeq, bannCtg, tgt) {
    insertBannAccess(bannSeq, bannCtg);
    if (tgt.trim() == '_blank') {
        window.open(linkUrl);
    } else {
        window.location.href = linkUrl;
    }
}

//엠버십 상세보기
function showDtl(coupnCtgCd) {
    var parameterData = ajaxCommon.getSerializedData({
        coupnCtgCd: coupnCtgCd
    });
    openPage('pullPopup', '/m/coupon/couponDetailPop.do', parameterData, '0');
}

//엠버십 쿠폰받기
function checkDown(couponCtgCd) {

    var coupnCtgCdList = [];

    if (couponCtgCd != null) {
        coupnCtgCdList.push(couponCtgCd);
    } else { //쿠폰 리스트 팝업
        var checkedCoupons = document.querySelectorAll('input[name=couponCheckbox]:checked');
        if (checkedCoupons.length === 0) {
            MCP.alert('선택된 쿠폰이 없습니다.');
            return;
        }
        checkedCoupons.forEach((param) => {
            coupnCtgCdList.push(param.id);
        })
    }

    //세션에 저장되어 있는 로그인 정보(이름, 생년월일) 가져오기
    ajaxCommon.getItem({
            id: "checkLogin"
            , cache: false
            , url: "/m/coupon/checkLogin.do"
            , data: ""
            , dataType: "json"
            , async: false
        }
        , function (data) {

            if (data.isLogin == false) {
                KTM.Confirm('M모바일을 사용하시는<br>회원 전용 서비스 입니다.<br><br>로그인 후 이용 하시겠습니까?', function () {
                    $('#frm').submit();
                });
            } else {
                var parameterData = ajaxCommon.getSerializedData({
                    coupnCtgCdList: coupnCtgCdList
                });
                openPage('pullPopup', '/m/coupon/couponDownPop.do', parameterData, '1');
            }
        });
}

//전체 클릭 시
function categoryAll() {
    // 모든 쿠폰 항목을 보이게 함
    const allItems = $(".mbership-item");

    const count = allItems.length;
    $('#categoryCnt').html('총 <span class="u-co-red">'+count+'</span>개');

    if (allItems.length > 0) {
        allItems.show();
        $(".c-nodata").hide();
    } else {
        $(".c-nodata").show();
    }

    $(".mbership-category__button").removeClass("is-active");
    $("#categoryAll").addClass("is-active");

    $('input[name="couponCheckbox"]').prop('checked', false); //전체선택 초기화

}

//전체 외 클릭 시
function categoryOther(categoryId) {
    $(".mbership-item").hide();
    // 해당 카테고리의 쿠폰만 표시
    const filteredItems = $(".mbership-item[couponCategory='" + categoryId + "']");
    filteredItems.show();
    const count = filteredItems.length;
    $('#categoryCnt').text('총 '+count+'개');

    if (filteredItems.length == 0) {
        $(".c-nodata").show();
    } else {
        $(".c-nodata").hide();
    }

    $(".mbership-category__button").removeClass("is-active");
    $(event.target).closest(".mbership-category__button").addClass("is-active");

    $('input[name="couponCheckbox"]').prop('checked', false); //전체선택 초기화

}

function allCheck(){
    $('.mbership-item:visible').each(function() {
        const $checkbox = $(this).find('input[name="couponCheckbox"]');
        if ($checkbox.length > 0) {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
        }
    });
}
