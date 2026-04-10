$(document).ready(function() {
    initChildInfo(getChildNcn());

    $(".loding").click(function(){
        KTM.LoadingSpinner.show();
    });
});

function openNewDetailPopup(el) {
    var url = $(el).attr("url");
    openDetailPopupByUrl(url, getChildNcn());
}

function changeDetailPopup(el) {
    var url = $(el).attr("url");
    openDetailPopupByUrl(url, getPopupChildNcn());
}

function openDetailPopupByUrl(url, childNcn) {
    var parameterData = ajaxCommon.getSerializedData({
        childNcn: childNcn
    });
    openPage('pullPopupByPost', url, parameterData);
}

function initChildInfo(childNcn) {
    KTM.LoadingSpinner.show();
    Promise
        .all([
            initThisMonthPayInfo(childNcn),
            initRealTimePayInfo(childNcn),
            initPayData(childNcn),
            initAddSvcInfo(childNcn),
            getUseAddSvcList(childNcn)])
        .finally(() => {
            KTM.LoadingSpinner.hide(true);
    });
}

function initThisMonthPayInfo(childNcn) {
    return new Promise((resolve) => {
        var varData = ajaxCommon.getSerializedData({
            childNcn: childNcn
        });

        ajaxCommon.getItem({
            id: 'getThisMonthPayInfoAjax',
            url: '/mypage/getThisMonthPayInfoAjax.do',
            data: varData,
            dataType: "json",
            cache: false
        }, function(jsonObj) {
            if ( jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS ) {
                MCP.alert(jsonObj.RESULT_MSG);
                resolve();
            }

            var mMonthpaymentdto = jsonObj.mMonthpaymentdto;
            var titleSpan = '';
            var infoHtml = '';

            if ( !mMonthpaymentdto ) {
                $("#billInfo .month_bill__nodata").show();
                $("#billInfo .month_bill__item").hide();
            } else {
                titleSpan = `<span>${mMonthpaymentdto.billStartDate}~${mMonthpaymentdto.billEndDate}</span>`;

                infoHtml += `<div class="month_bill__info">`;
                infoHtml += `    <p>${mMonthpaymentdto.billMonth}월 청구요금</p>`;
                infoHtml += `    <b>${mMonthpaymentdto.totalDueAmt}</b><span>원</span>`;
                infoHtml += `</div>`;
                infoHtml += `<div class="month_bill__btn">`;
                infoHtml += `    <a href="javascript:;" url="/mypage/childChargeView.do" title="당월 청구요금 내역보기 이동하기" onclick="openNewDetailPopup(this);">내역보기</a>`;
                infoHtml += `</div>`;

                $("#billTitle").append(titleSpan);
                $("#thisMonthInfo").html(infoHtml);
            }
            resolve();
        });
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
            var infoHtml = '';

            infoHtml += `<div class="month_bill__info">`;
            infoHtml += `    <p>실시간 요금</p>`;
            infoHtml += `    <b class="u-co-blue-2">${sumAmt}</b><span class="u-co-blue-2">원</span>`;
            infoHtml += `</div>`;
            infoHtml += `<div class="month_bill__btn">`;
            infoHtml += `    <a href="javascript:;" url="/mypage/childRealTimeRateView.do" title="실시간 요금 내역보기 이동하기" onclick="openNewDetailPopup(this);">내역보기</a>`;
            infoHtml += `</div>`;

            $("#realTimeInfo").html(infoHtml);
            resolve();
        });
    });
}

function initPayData(childNcn) {
    return new Promise((resolve) => {
        var varData = ajaxCommon.getSerializedData({
            childNcn: childNcn
        });

        ajaxCommon.getItem({
            id: 'getPayDataAjax',
            url: '/mypage/getPayDataAjax.do',
            data: varData,
            dataType: "json",
            cache: false
        }, function (jsonObj) {
            if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
                MCP.alert(jsonObj.RESULT_MSG);
                resolve();
            }

            // 1. 개통일자
            var payData = jsonObj.payData;
            var initActivationDate = payData.initActivationDate;
            $("#initActivationDate").text(initActivationDate);

            // 2. 납부정보
            payArea(payData);
            resolve();
        });
    });
}

function initAddSvcInfo(childNcn) {
    return new Promise((resolve) => {
        var varData = ajaxCommon.getSerializedData({
            childNcn: childNcn
        });

        ajaxCommon.getItem({
            id: 'getAddSvcInfoAjax',
            url: '/mypage/getAddSvcInfoAjax.do',
            data: varData,
            dataType: "json",
            cache: false
        }, function (jsonObj) {
            if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
                MCP.alert(jsonObj.RESULT_MSG);
                resolve();
            }

            $("#payCtn").text(jsonObj.payCtn);
            $("#freeCtn").text(jsonObj.freeCtn);
            resolve();
        });
    });
}

/** 이용중인 부가서비스 조회 */
function getUseAddSvcList(childNcn){
    return new Promise((resolve) => {
        var varData = ajaxCommon.getSerializedData({
            childNcn: childNcn
        });

        ajaxCommon.getItem({
            id: 'useAddSvcListAjax'
            , cache: false
            , url: '/mypage/useAddSvcListAjax.do'
            , data: varData
            , dataType: "json"
        }, function (jsonObj) {
            if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
                MCP.alert(jsonObj.RESULT_MSG);
                resolve();
            }
            var data = jsonObj.outList;

            if (data.length > 0) {
                for (var index = 0; index < data.length; index++) {
                    var html = '';
                    if (data[index].socRateVat >= 0) {
                        html += '<tr>';
                        html += '<td>' + data[index].socDescription + '</td>';
                        if (data[index].socRateValue == 'Free') {
                            html += '<td>무료</td>';
                        } else if (data[index].rateCd == data[index].vatYn) {
                            html += '<td>' + numberWithCommas(data[index].socRateVatValue) + ' 원 / 1일</td>';
                        } else {
                            html += '<td>월 ' + numberWithCommas(data[index].socRateVatValue) + ' 원</td>';
                        }
                        html += '</tr>';
                    }

                    if (data[index].socRateValue == 'Free') {
                        $("#freeAddSvcList").append(html);
                    } else {
                        $("#payAddSvcList").append(html);
                    }
                }
            }

            if ($("#payAddSvcList tr").length == 0) {
                $("#payAddSvcHeader").hide();
            }
            if ($("#freeAddSvcList tr").length == 0) {
                $("#freeAddSvcHeader").hide();
            }
            resolve();
        });
    });
}

function selectMain(){

    ajaxCommon.createForm({
        method:"post",
        action:"/mypage/childInfoView.do"
    });

    ajaxCommon.attachHiddenElement("childNcn", getChildNcn());
    ajaxCommon.formSubmit();
}

function payArea(payData){

    var reqType = payData.reqType;
    var reqTypeNm = payData.reqTypeNm;
    var blaAddr = payData.blaAddr;
    var payMethod = payData.payMethod;
    var blBankAcctNo = payData.blBankAcctNo;
    var billTypeCd = payData.billTypeCd;
    var billCycleDueDay = payData.billCycleDueDay;
    var prevCardNo = payData.prevCardNo;
    var prevExpirDt = payData.prevExpirDt;

    var addHtml = "";
    if(payMethod=="자동이체"){

        addHtml += "<tr>";
        addHtml += "	<th>납부방법</th>";
        addHtml += "	<td>"+payMethod+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>납부계정정보</th>";
        addHtml += "	<td>"+blBankAcctNo+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>명세서유형</th>";
        addHtml += "	<td>"+reqType+"</td>";
        addHtml += "</tr>";
        if(billTypeCd != "MB"){
            addHtml += "<tr>";
            addHtml += "	<th>"+reqTypeNm+"</th>";
            addHtml += "	<td>"+blaAddr+"</td>";
            addHtml += "</tr>";
        }
        addHtml += "<tr>";
        addHtml += "	<th>납부기준일</th>";
        addHtml += "	<td>"+billCycleDueDay+"</td>";
        addHtml += "</tr>";

    } else if(payMethod=="지로"){

        addHtml += "<tr>";
        addHtml += "	<th>납부방법</th>";
        addHtml += "	<td>"+payMethod+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>납부일자</th>";
        addHtml += "	<td>"+billCycleDueDay+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>명세서유형</th>";
        addHtml += "	<td>"+reqType+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>"+reqTypeNm+"</th>";
        addHtml += "	<td>"+blaAddr+"</td>";
        addHtml += "</tr>";

    } else if(payMethod=="신용카드"){

        addHtml += "<tr>";
        addHtml += "	<th>납부방법</th>";
        addHtml += "	<td>"+payMethod+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>카드번호</th>";
        addHtml += "	<td>"+prevCardNo+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>카드만료기간</th>";
        addHtml += "	<td>"+prevExpirDt+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>명세서유형</th>";
        addHtml += "	<td>"+reqType+"</td>";
        addHtml += "</tr>";
        if(billTypeCd != "MB"){
            addHtml += "<tr>";
            addHtml += "	<th>"+reqTypeNm+"</th>";
            addHtml += "	<td>"+blaAddr+"</td>";
            addHtml += "</tr>";
        }
        addHtml += "<tr>";
        addHtml += "	<th>납부기준일</th>";
        addHtml += "	<td>"+billCycleDueDay+"</td>";
        addHtml += "</tr>";

    } else if(payMethod=="간편결제"){

        addHtml += "<tr>";
        addHtml += "	<th>납부방법</th>";
        addHtml += "	<td>"+payMethod+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>납부계정정보</th>";
        addHtml += "	<td>"+billCycleDueDay+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>명세서유형</th>";
        addHtml += "	<td>"+reqType+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>"+reqTypeNm+"</th>";
        addHtml += "	<td>"+blaAddr+"</td>";
        addHtml += "</tr>";

    } else {
        addHtml += "<tr>";
        addHtml += "	<td colspan='2' class='u-ta-center u-height--80'>조회결과가 없습니다.</td>";
        addHtml += "</tr>";
    }
    $("#payArea").html(addHtml);
}

function getChildNcn() {
    return $("#main-content").find('#ctn').val();
}

function getPopupChildNcn() {
    return $("#cmodalbody").find('#ctn').val();
}