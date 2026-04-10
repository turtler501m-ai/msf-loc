$(document).ready(function() {
    initChildCall(getPopupChildNcn());

    $("#datalist").on("change", function() {
        var parameterData = ajaxCommon.getSerializedData({
            childNcn: getPopupChildNcn(),
            useMonth: $("#datalist option:selected").val()
        });

        openPage('pullPopupByPost', '/m/mypage/childCallView.do', parameterData);
    });
});

function initChildCall(childNcn) {
    KTM.LoadingSpinner.show();
    Promise
        .all([
            initTelTotalUseTimeAjax(childNcn)])
        .finally(() => {
            KTM.LoadingSpinner.hide(true);
        });
}

function initTelTotalUseTimeAjax(childNcn) {
    return new Promise((resolve) => {
        var varData = ajaxCommon.getSerializedData({
            childNcn: childNcn
        });

        ajaxCommon.getItem({
            id: 'getTelTotalUseTimeAjax',
            url: '/mypage/getTelTotalUseTimeAjax.do',
            data: varData,
            dataType: "json",
            cache: false
        }, function (jsonObj) {
            if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
                MCP.alert(jsonObj.RESULT_MSG);
                resolve();
            }

            var vo = jsonObj.vo;
            var dataHtml = '';
            var voiceHtml = '';
            var smsHtml = '';
            var dataList = vo.itemTelVOListData;
            var voiceList = vo.itemTelVOListVoice;
            var smsList = vo.itemTelVOListSms;

            if (vo.useTimeSvcLimit) {
                $("#useTimeSvcLimit").show();
                $("#callInfo").hide();
            } else {
                if ((!dataList || dataList.length == 0) && (!voiceList || voiceList.length == 0) && (!smsList || smsList.length == 0)) {
                    $(".c-nodata").show();
                    $("#callInfo").hide();
                } else {
                    if (dataList) {
                        dataList.forEach(function (item, index) {
                            dataHtml += `<div class="c-item c-item--type1 u-mt--24 u-mb--40">`;
                            dataHtml += `    <div class="c-item__title flex-type--between">`;
                            dataHtml += `        <span class="data-title--short">`;
                            dataHtml += `            <i class="c-icon c-icon--data" aria-hidden="true"></i>`;
                            dataHtml += `            <span class="u-ml--4">${item.strSvcName}</span>`;
                            dataHtml += `        </span>`;
                            dataHtml += `        <span class="u-ml--auto c-text c-text--type4">`;
                            dataHtml += `            <span class="u-co-gray-8 u-fw--regular">사용 ${item.strFreeMinUse}</span> / 잔여 ${item.strFreeMinRemain}`;
                            dataHtml += `        </span>`;
                            dataHtml += `    </div>`;
                            dataHtml += `    <div class="c-indicator c-indicator--type1 u-mt--12">`;
                            dataHtml += `        <span class="mark-mint" style="width:${item.percent}%"></span>`;
                            dataHtml += `    </div>`;
                            dataHtml += `</div>`;
                        });

                        $("#dataInfo").append(dataHtml);
                    }

                    if (voiceList) {
                        voiceList.forEach(function (item, index) {
                            voiceHtml += `<div class="c-item c-item--type1 u-mt--24 u-mb--40">`;
                            voiceHtml += `    <div class="c-item__title flex-type--between">`;
                            voiceHtml += `        <span class="data-title--short">`;
                            voiceHtml += `            <i class="c-icon c-icon--call-gray" aria-hidden="true"></i>`;
                            voiceHtml += `            <sapn class="u-ml--4">${item.strSvcName}</sapn>`;
                            voiceHtml += `        </span>`;
                            voiceHtml += `        <span class="right c-text c-text--type4">`;
                            voiceHtml += `            <span class="u-co-gray-8 u-fw--regular">사용 ${item.strFreeMinUse}${item.bunGunNm}</span> / 잔여 ${item.strFreeMinRemain}${item.bunGunNm}`;
                            voiceHtml += `        </span>`;
                            voiceHtml += `    </div>`;
                            voiceHtml += `    <div class="c-indicator c-indicator--type1 u-mt--12">`;
                            voiceHtml += `        <span class="mark-blue" style="width:${item.percent}%"></span>`;
                            voiceHtml += `    </div>`;
                            voiceHtml += `</div>`;
                        });

                        $("#voiceInfo").append(voiceHtml);
                    }

                    if (smsList) {
                        smsList.forEach(function (item, index) {
                            smsHtml += `<div class="c-item c-item--type1 u-mt--24 u-mb--40">`;
                            smsHtml += `    <div class="c-item__title flex-type--between">`;
                            smsHtml += `        <span class="data-title--short">`;
                            smsHtml += `            <i class="c-icon c-icon--msg" aria-hidden="true"></i>`;
                            smsHtml += `            <span class="u-ml--4">${item.strSvcName}</span>`;
                            smsHtml += `        </span>`;
                            smsHtml += `        <span class="right u-ml--auto c-text c-text--type4">`;
                            smsHtml += `            <span class="u-co-gray-8 u-fw--regular">사용 ${item.strFreeMinUse}${item.bunGunNm}</span> / 잔여 ${item.strFreeMinRemain}${item.bunGunNm}`;
                            smsHtml += `        </span>`;
                            smsHtml += `    </div>`;
                            smsHtml += `    <div class="c-indicator c-indicator--type1 u-mt--12">`;
                            smsHtml += `        <span class="mark-green" style="width:${item.percent}%"></span>`;
                            smsHtml += `    </div>`;
                            smsHtml += `</div>`;
                        });

                        $("#smsInfo").append(smsHtml);
                    }
                }
            }
            resolve();
        });
    });
}

function selectDetail() {
    openDetailPopupByUrl("/m/mypage/childCallView.do", getPopupChildNcn())
}