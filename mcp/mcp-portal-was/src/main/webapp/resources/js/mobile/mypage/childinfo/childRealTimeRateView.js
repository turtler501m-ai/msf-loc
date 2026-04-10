$(document).ready(function() {
    initChildRealTimeRate(getPopupChildNcn());

    $("#tab1").click(function() {
       openDetailPopupByUrl("/m/mypage/childChargeView.do", getPopupChildNcn());
    });
});

function initChildRealTimeRate(childNcn) {
    KTM.LoadingSpinner.show();
    Promise
        .all([
            initRealTimePayInfo(childNcn)])
        .finally(() => {
            KTM.LoadingSpinner.hide(true);
        });
}

function initRealTimePayInfo(childNcn) {
    return new Promise((resolve) => {
        var varData = ajaxCommon.getSerializedData({
            childNcn: childNcn
        });

        ajaxCommon.getItem({
            id: 'getRealTimePayInfoAjax',
            url: '/mypage/getRealTimePayInfoAjax.do',
            data: varData,
            dataType: "json",
            cache: false
        }, function (jsonObj) {
            if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
                MCP.alert(jsonObj.RESULT_MSG);
                resolve();
            }

            var sumAmt = ajaxCommon.isNullNvl(jsonObj.sumAmt, 0);
            var realFareVOList = jsonObj.realFareVOList;

            var sumAmtHtml = '';
            var useDayHtml = '';
            var grapWidthHtml = '';
            var searchTimeHtml = '';
            var realFareVOListHtml = '';

            sumAmtHtml = `<span class="u-ml--8">${sumAmt} 원</span>`;

            useDayHtml += `<span class="u-co-mint">${jsonObj.useDay}일</span>/${jsonObj.useTotalDay}일`;

            grapWidthHtml += `<span class="mark-mint" style="width:${jsonObj.grapWidth}%"></span>`;

            searchTimeHtml += `<span class="c-text c-text--type4 u-co-gray-7 u-ml--auto" id="searchTime">${jsonObj.searchTime}</span>`;

            if (!realFareVOList) {
                $(".c-nodata").show();
                $("#realFareVOList").hide();
            } else {
                realFareVOList.forEach(function (item, index) {
                    realFareVOListHtml += `<dl class="pay-detail__item">`;
                    realFareVOListHtml += `    <dt>${item.gubun}</dt>`;
                    realFareVOListHtml += `    <dd>${item.payment} 원</dd>`;
                    realFareVOListHtml += `</dl>`;
                });
            }
            $("#sumAmt").append(sumAmtHtml);
            $("#useDay").append(useDayHtml);
            $("#grapWidth").append(grapWidthHtml);
            $("#searchTime").append(searchTimeHtml);
            $("#realFareVOList").append(realFareVOListHtml);
            resolve();
        });
    });
}

function selectDetail() {
    openDetailPopupByUrl("/m/mypage/childRealTimeRateView.do", getPopupChildNcn());
}