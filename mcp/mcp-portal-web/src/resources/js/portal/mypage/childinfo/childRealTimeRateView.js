$(document).ready(function() {
    initChildRealTimeRate(getPopupChildNcn());

    $("#tab1").click(function() {
        openDetailPopupByUrl('/mypage/childChargeView.do', getPopupChildNcn());
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
            var useTotalDayHtml = '';
            var searchTimeHtml = '';
            var realFareVOListHtml = '';

            useDayHtml += `<span class="mark mark-red" id="dateViewStyle" style="width:${jsonObj.grapWidth}%">`;
            useDayHtml += `    <span class="c-balloon c-balloon--center">${jsonObj.useDay} 일</span>`;
            useDayHtml += `</span>`;

            useTotalDayHtml += `<b class="end-date">${jsonObj.useTotalDay} 일</b>`;

            sumAmtHtml += `<b class="u-fs-26">${sumAmt} </b>원`;

            searchTimeHtml += `<span class="c-text c-text--fs14 u-co-gray">${jsonObj.searchTime}</span>`;

            if (!realFareVOList) {
                $(".c-nodata").show();
                $("#realFareVOList").show();
            } else {
                realFareVOList.forEach(function (item, index) {
                    realFareVOListHtml += `<dl class="pay-detail__item">`;
                    realFareVOListHtml += `    <dt class="u-co-gray u-fw--regular">${item.gubun}</dt>`;
                    realFareVOListHtml += `    <dd>${item.payment} 원</dd>`;
                    realFareVOListHtml += `</dl>`;
                });
            }

            $("#useDay").append(useDayHtml);
            $("#useTotalDay").append(useTotalDayHtml);
            $("#sumAmt").append(sumAmtHtml);
            $("#searchTime").append(searchTimeHtml);
            $("#realFareVOList").append(realFareVOListHtml);
            resolve();
        });
    });
}

function selectDetail() {
    openDetailPopupByUrl("/mypage/childRealTimeRateView.do", getPopupChildNcn());
}