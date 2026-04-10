$(document).ready(function() {
    initChildCall(getPopupChildNcn());
});

function arcProgress() {
    var progressBars = document.querySelectorAll('.c-indicator-arc');
    if (!!progressBars) {
        progressBars.forEach(function(arcProgress) {
            var bar = arcProgress.querySelector('.arc');
            var val = bar.dataset.percent;
            var perc = parseInt(val, 10);
            var bar_text = arcProgress.querySelector('.indicator-text');
            //ie 11 경우 체크
            var agent = navigator.userAgent.toLowerCase();
            if ((navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || agent.indexOf('msie') != -1) {
                // ie일 경우
                bar.style['msTransform'] = 'rotate(' + (45 + perc * 1.8) + 'deg)';
            } else {
                // ie가 아닐 경우
                bar.style['WebkitTransform'] = 'rotate(' + (45 + perc * 1.8) + 'deg)';
            }

            function showData() {
                //알맞은 percent 값을 계산하여 해당 정보에 뿌려주세요.
            }
        });
    }
}

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
                            if (index % 3 == 0) {
                                dataHtml += `<ul class="usage-chart u-mt--32">`
                            }

                            dataHtml += `<li class="usage-chart__item">`;
                            dataHtml += `    <div class="c-indicator-arc">`;
                            dataHtml += `        <div class="arcOverflow">`;
                            dataHtml += `            <div class="arc arc-mint" data-percent="${Math.round(item.percent)}"></div>`;
                            dataHtml += `        </div>`;
                            dataHtml += `        <span class="indicator-state">${item.strSvcName} <br>`;
                            dataHtml += `            <i class="c-icon c-icon--data" aria-hidden="true"></i>`;
                            dataHtml += `        </span>`;
                            dataHtml += `        <div class="indicator-text">`;
                            dataHtml += `            <span>`;
                            dataHtml += `                <span class="c-hidden">${item.strFreeMinUse}</span>사용 ${item.strFreeMinUse}`;
                            dataHtml += `            </span>`;
                            dataHtml += `            <strong>`;
                            dataHtml += `                <span class="c-hidden">${item.strFreeMinRemain}</span> / 잔여 ${item.strFreeMinRemain}`;
                            dataHtml += `            </strong>`;
                            dataHtml += `        </div>`;
                            dataHtml += `    </div>`;
                            dataHtml += `</li>`;

                            if ((index % 3 == 2) || (index + 1 == dataList.length)) {
                                dataHtml += `</ul>`;
                            }
                        });

                        $("#dataInfo").append(dataHtml);
                    }

                    if (voiceList) {
                        voiceList.forEach(function (item, index) {
                            if (index % 3 == 0) {
                                voiceHtml += `<ul class="usage-chart u-mt--32">`
                            }

                            voiceHtml += `<li class="usage-chart__item">`;
                            voiceHtml += `    <div class="c-indicator-arc">`;
                            voiceHtml += `        <div class="arcOverflow">`;
                            voiceHtml += `            <div class="arc arc-blue" data-percent="${item.percent}"></div>`;
                            voiceHtml += `        </div>`;
                            voiceHtml += `        <span class="indicator-state">${item.strSvcName} <br>`;
                            voiceHtml += `            <i class="c-icon c-icon--call-gray" aria-hidden="true"></i>`;
                            voiceHtml += `        </span>`;
                            voiceHtml += `        <div class="indicator-text">`;
                            voiceHtml += `            <span>`;
                            voiceHtml += `                <span class="c-hidden">${item.strFreeMinUse}${item.bunGunNm}</span>사용 ${item.strFreeMinUse}${item.bunGunNm}`;
                            voiceHtml += `            </span>`;
                            voiceHtml += `            <strong>`;
                            voiceHtml += `                <span class="c-hidden">${item.strFreeMinRemain}${item.bunGunNm}</span> / 잔여 ${item.strFreeMinRemain}${item.bunGunNm}`;
                            voiceHtml += `            </strong>`;
                            voiceHtml += `        </div>`;
                            voiceHtml += `    </div>`;
                            voiceHtml += `</li>`;

                            if ((index % 3 == 2) || (index + 1 == voiceList.length)) {
                                voiceHtml += `</ul>`;
                            }
                        });

                        $("#voiceInfo").append(voiceHtml);
                    }

                    if (smsList) {
                        smsList.forEach(function (item, index) {
                            if (index % 3 == 0) {
                                smsHtml += `<ul class="usage-chart u-mt--32">`
                            }

                            smsHtml += `<li class="usage-chart__item">`;
                            smsHtml += `     <div class="c-indicator-arc">`;
                            smsHtml += `        <div class="arcOverflow">`;
                            smsHtml += `            <div class="arc arc-green" data-percent="${item.percent}"></div>`;
                            smsHtml += `        </div>`;
                            smsHtml += `        <span class="indicator-state">${item.strSvcName} <br>`;
                            smsHtml += `            <i class="c-icon c-icon--msg" aria-hidden="true"></i>`;
                            smsHtml += `        </span>`;
                            smsHtml += `        <div class="indicator-text">`;
                            smsHtml += `            <span>`;
                            smsHtml += `                <span class="c-hidden">${item.strFreeMinUse}${item.bunGunNm}</span>사용 ${item.strFreeMinUse}${item.bunGunNm}`;
                            smsHtml += `            </span>`;
                            smsHtml += `            <strong>`;
                            smsHtml += `                <span class="c-hidden">${item.strFreeMinRemain}${item.bunGunNm}</span> / 잔여 ${item.strFreeMinRemain}${item.bunGunNm}`;
                            smsHtml += `            </strong>`;
                            smsHtml += `        </div>`;
                            smsHtml += `    </div>`;
                            smsHtml += `</li>`;

                            if ((index % 3 == 2) || (index + 1 == smsList.length)) {
                                smsHtml += `</ul>`;
                            }
                        });

                        $("#smsInfo").append(smsHtml);
                    }
                }
            }
            arcProgress();
            resolve();
        });
    });
}

document.addEventListener('DOMContentLoaded', function() {
    arcProgress();
});

function selectDetail() {
    openDetailPopupByUrl("/mypage/childCallView.do", getPopupChildNcn());
}

function btn_search() {
    var parameterData = ajaxCommon.getSerializedData({
        childNcn: getPopupChildNcn(),
        useMonth: $("#datalist option:selected").val()
    });

    openPage('pullPopupByPost', '/mypage/childCallView.do', parameterData);
}