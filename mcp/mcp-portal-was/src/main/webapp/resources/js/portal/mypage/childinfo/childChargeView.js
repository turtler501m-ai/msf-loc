var recent = 0;
var last = 0;

var pageObj = {
    startDate : "",
    endDate : ""
}

$(document).ready(function() {
    initChildCharge(getPopupChildNcn());
    //
    // $(".btnPrint").click(function() {
    //     var listType = $(this).closest('.c-accordion__item').data('type');
    //
    //     if ( !isCompletedView(listType) ) {
    //         alertPayView(listType);
    //         return;
    //     }
    //
    //     pageObj.startDate = getStartDate(listType);
    //     pageObj.endDate = getEndDate(listType);
    //
    //     var parameterData = ajaxCommon.getSerializedData({
    //         menuType : 'chargePrint',
    //         ncn : $("#cmodalbody").find("#minorAgentNcn").val()
    //     });
    //
    //     openPage('pullPopup2nd','/sms/smsAuthToNcnInfoPop.do', parameterData);
    // });
    //
    // // 엑셀다운로드
    // $(".btnExcel").click(function() {
    //     var listType = $(this).closest('.c-accordion__item').data('type');
    //
    //     if ( !isCompletedView(listType) ) {
    //         alertPayView(listType)
    //         return;
    //     }
    //
    //     ajaxCommon.createForm({
    //        method: "post",
    //        action:  "/mypage/childChargeMonthListExcelDownload.do"
    //     });
    //
    //     ajaxCommon.attachHiddenElement("childNcn", getPopupChildNcn());
    //     ajaxCommon.attachHiddenElement("startDate", getStartDate(listType));
    //     ajaxCommon.attachHiddenElement("endDate", getEndDate(listType));
    //     ajaxCommon.formSubmit();
    //     KTM.LoadingSpinner.hide(true);
    // });

    // 실시간요금 조회 화면 이동
    $(".realSrch").click(function() {
        openDetailPopupByUrl('/mypage/childRealTimeRateView.do', getPopupChildNcn());
    });

    // 납부내역 상세
    $("#acc_header_a1, #acc_header_a2").click(function() {
        var listType = $(this).closest('.c-accordion__item').data('type');

        if ( isCompletedView(listType) ) return;
        if ( listType == "recent" ) recent++;
        if ( listType == "last" )   last++;

        var varData = ajaxCommon.getSerializedData({
           childNcn: getPopupChildNcn(),
           startDate: getStartDate(listType),
           endDate: getEndDate(listType)
        });

        ajaxCommon.getItem({
          id: 'childRecentPaymentAjax',
          url: '/mypage/childRecentPaymentAjax.do',
          data: varData,
          dataType: "json",
          cache: false,
          async: false
        }, function(jsonObj) {
           if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
               MCP.alert(jsonObj.RESULT_MSG);
               recentView(null, listType);
               return;
           } else {
               recentView(jsonObj.itemPay, listType);
           }
        });
    });
});

function initChildCharge(childNcn) {
    KTM.LoadingSpinner.show();
    Promise
        .all([
            initMonthBillInfo(childNcn)])
        .finally(() => {
            KTM.LoadingSpinner.hide(true);
        });
}

function initMonthBillInfo(childNcn) {
    return new Promise((resolve) => {
        var varData = ajaxCommon.getSerializedData({
            childNcn: getPopupChildNcn(),
            billMonth: $("#selectedMonth").val()
        });

        ajaxCommon.getItem({
            id: 'getMonthBillListAjax',
            url: '/mypage/getMonthBillListAjax.do',
            data: varData,
            dataType: "json",
            cache: false
        }, function (jsonObj) {
            if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
                MCP.alert(jsonObj.RESULT_MSG);
                resolve();
            }

            var monthList = jsonObj.monthList;
            var monthPay = jsonObj.monthPay;
            var itemList = jsonObj.itemList;

            if (itemList.length < 1) {
                $("#billNoData").show();
                $("#billBody").hide();
                $("#billHeader").hide();
            } else {
                // 1. set billHeader
                monthList.forEach(function (month, index, array) {
                    $("#billMonth").append(`<option value=${month.billMonth}>` + month.billMonth.substring(4, 6) + `월 청구요금</option>`);
                });
                $("#billMonth option:eq(0)").prop('selected', true);
                $("#billMonth").children().each(function () {
                    if ($(this).val() == $("#selectedMonth").val()) {
                        $(this).prop('selected', true);
                    }
                });

                var billStartDate = monthPay.billStartDate;
                var billEndDate = monthPay.billEndDate;
                var billMonthPeriod = billStartDate.substring(0, 4) + "." + billStartDate.substring(4, 6) + "." + billStartDate.substring(6, 8)
                    + " ~ " + billEndDate.substring(0, 4) + "." + billEndDate.substring(4, 6) + "." + billEndDate.substring(6, 8);
                $("#billMonthPeriod").text(billMonthPeriod);
                // 1. set billHeader

                // 2. set billBody
                $(".totalDueAmt").each(function () {
                    var innerText = parseInt(monthPay.totalDueAmt).toLocaleString('ko-KR') + $(this).text();
                    $(this).text(innerText);
                });

                itemList.forEach(function (item, index, array) {
                    var splitDescription = item.splitDescription;
                    var messageLine = item.messageLine;
                    var billSeqNo = item.billSeqNo;
                    var amount = parseInt(item.actvAmt).toLocaleString('ko-KR') + "원";

                    var listHtml = "";
                    if (splitDescription == "당월 요금" || splitDescription == "미납요금") {
                        listHtml += `<li class="c-accordion__item">`;
                        listHtml += `	<div class="c-accordion__head">`;
                        listHtml += `		<strong class="c-accordion__title">${splitDescription}</strong>`;
                        listHtml += `		<span class="c-accordion__amount">${amount}</span>`;
                        listHtml += `	</div>`;
                        listHtml += `</li>`;
                    }
                    if ((messageLine && messageLine != "DISCBYSVC") || splitDescription == "할인요금") {
                        listHtml += `<li class="c-accordion__item" id="parent${index}" adDetail="F">`;
                        listHtml += `	<div class="c-accordion__head">`;
                        listHtml += `		<strong class="c-accordion__title">${splitDescription}`;
                        listHtml += `		<input type="hidden" value="${messageLine}" id="mlId${index}" name="messageLineVal" />`;
                        listHtml += `		<input type="hidden" value="${billSeqNo}" id="bs${index}" name="billSeqNoTmp" />`;
                        listHtml += `		</strong>`;
                        listHtml += `		<button class="runtime-data-insert c-accordion__button"`;
                        listHtml += `		id="acc_header_c${index}" aria-expanded="false" aria-controls="acc_content_c${index}"`;
                        listHtml += `		type="button" onclick="detailView('parentView${index}','${messageLine}');">`;
                        listHtml += `			<span class="c-hidden">${splitDescription}상세열기</span>`;
                        listHtml += `		</button>`;
                        listHtml += `		<span class="c-accordion__amount">${amount}</span>`;
                        listHtml += `	</div>`;
                        listHtml += `	<div class="c-accordion__panel expand" id="acc_content_c${index}" aria-labelledby="acc_header_c${index}">`;
                        listHtml += `		<div class="c-accordion__inside">`;
                        listHtml += `			<div class="pay-detail">`;
                        listHtml += `				<dl class="pay-detail__item" id="parentView${index}"></dl>`;
                        listHtml += `			</div>`;
                        listHtml += `		</div>`;
                        listHtml += `	</div>`;
                        listHtml += `</li>`;
                    }
                    $("#billDetailList").append(listHtml);
                });
                itemList.forEach(function (item, index, array) {
                    var splitDescription = item.splitDescription;
                    var messageLine = item.messageLine;
                    var amount = parseInt(item.actvAmt).toLocaleString('ko-KR') + "원";

                    var listHtml = "";
                    if (splitDescription == "당월요금계" || !messageLine) {
                        listHtml += `<li class="c-accordion__item">`;
                        listHtml += `	<div class="c-accordion__head">`;
                        listHtml += `		<strong class="c-accordion__title">${splitDescription}</strong>`;
                        listHtml += `		<span class="c-accordion__amount">${amount}</span>`;
                        listHtml += `	</div>`;
                        listHtml += `</li>`;
                    }
                    $("#billDetailList").append(listHtml);
                });
                // 2. set billBody

                // 3. set installmentInfo
                var infoHtml = "";
                var installmentAmt = jsonObj.installmentAmt;
                if (installmentAmt == "0" || jsonObj.installmentYN != 'Y') {
                    infoHtml += `<tr>`;
                    infoHtml += `	<td colspan="3">휴대폰 할부 정보가 없습니다.</td>`;
                    infoHtml += `</tr>`;
                } else {
                    var instAmount = parseInt(installmentAmt).toLocaleString('ko-KR') + "원";
                    infoHtml += `<tr>`;
                    infoHtml += `	<td>${instAmount}</td>`;
                    infoHtml += `	<td>${jsonObj.totalNoOfInstall}</td>`;
                    infoHtml += `	<td>${jsonObj.lastInstallMonth}</td>`;
                    infoHtml += `</tr>`;
                }
                $("#installmentInfo").html(infoHtml);
                // 3. set installmentInfo
            }
            resolve();
        });
    });
}

function isCompletedView(listType) {
    return (listType == "recent" && recent > 0) || (listType == "last" && last > 0)
}

function alertPayView(listType) {
    if ( listType == "recent" ) {
        MCP.alert("오른쪽의 화살표를 클릭하여,<br>최근 납부내역을 조회해 주세요.");
    }
    if ( listType == "last" ) {
        MCP.alert("오른쪽의 화살표를 클릭하여,<br>작년 납부내역을 조회해 주세요.");
    }
}

function getStartDate(type) {
    var today = new Date();
    if ( type == "recent" ) {
        today.setFullYear(today.getFullYear(), 0, 1); // (= 2024년 1월 1일)
        return dateFormat(today);
    }
    if ( type == "last" ) {
        today.setFullYear(today.getFullYear() - 1, 0, 1);
        return dateFormat(today);
    }
}

function getEndDate(type) {
    var today = new Date();
    if ( type == "recent" ) {
        return dateFormat(today);
    }
    if ( type == "last" ) {
        today.setFullYear(today.getFullYear() - 1, 11, 31);
        return dateFormat(today);
    }
}

function recentView(itemPay, target) {
    var addHtml = "";

    if (itemPay != null && itemPay.length > 0) {
        for (var i = 0; i < itemPay.length; i++) {
            var payMentDate = itemPay[i].payMentDate;
            if (payMentDate != null && payMentDate.length > 7) {
                payMentDate = payMentDate.substring(0, 4) + "." + payMentDate.substring(4, 6) + "." + payMentDate.substring(6, 8);
            }
            var payMentMethod = itemPay[i].payMentMethod;
            var payMentMoney = itemPay[i].payMentMoney;

            addHtml += "<tr>";
            addHtml += "    <td>"+payMentDate+"</td>";
            addHtml += "    <td>"+payMentMethod+"</td>";
            addHtml += "    <td class='u-ta-right'>"+numberWithCommas(payMentMoney)+"원</td>";
            addHtml += "</tr>";
        }
    } else {
        addHtml += "<tr>";
        addHtml += "    <td colspan='3'>";
        addHtml += "        <div class='c-nodata'>";
        addHtml += "            <i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
        addHtml += "            <p class='c-nodata__text'>납부내역이 없습니다.</p>";
        addHtml += "        </div>";
        addHtml += "    </td>";
        addHtml += "</tr>";

        if(target == "recent"){
            $(".btnPrint").eq(0).prop("disabled",true);
            $(".btnExcel").eq(0).prop("disabled",true);
        } else {
            $(".btnPrint").eq(1).prop("disabled",true);
            $(".btnExcel").eq(1).prop("disabled",true);
        }
    }

    if (target == "recent") {
        $("#recentArea").html(addHtml);
    } else {
        $("#lastArea").html(addHtml);
    }

}

function detailView(id, messageLine) {

    var varData = ajaxCommon.getSerializedData({
        messageLine: messageLine,
        childNcn: getPopupChildNcn(),
        billMonth: $("#billMonth option:selected").val()
    });

    ajaxCommon.getItem({
        id: 'getPriceInfoDetailItemAjax',
        url: '/mypage/getPriceInfoDetailItemAjax.do',
        data: varData,
        dataType: "json",
        cache: false
    }, function(jsonObj) {
        if (jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS) {
            var list = jsonObj.result;
            var contents = '';
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    contents += ' <dt>'+list[i].descr+'</dt> ';
                    contents += ' <dd>'+numberWithCommas(list[i].amt)+'원</dd> ';
                }
                $("#"+id).html(contents);
                KTM.initialize();
            }
        } else {
            MCP.alert(jsonObj.RESULT_MSG);
        }
    });
    KTM.initialize();
}

function dateFormat(parDate) {
    var yyyy = parDate.getFullYear();
    var mm = parDate.getMonth()+1;
    mm = mm >= 10 ? mm : '0'+mm;
    var dd = parDate.getDate();
    dd = dd >= 10 ? dd : '0'+dd;

    return ""+yyyy+mm+dd;
}

// function excelDownload() {
//     $("#childNcn").val(getPopupChildNcn());
//     $("#selectedMonth").val($("#billMonth option:selected").val());
//     $('#frm').attr('target', "tmpFrm");
//     $("#frm").attr("action", "/mypage/childChargeViewExcelDownload.do").submit();
// }

// function printPopup() {
//     var parameterData = ajaxCommon.getSerializedData({
//         childNcn : getPopupChildNcn(),
//         billMonth : $("#billMonth option:selected").val()
//     });
//
//     openPage('pullPopup2nd', "/mypage/childChargeViewPrint.do", parameterData);
// }

function selectDetail() {
    openDetailPopupByUrl("/mypage/childChargeView.do", getPopupChildNcn());
}

function btn_search() {
    var parameterData = ajaxCommon.getSerializedData({
        childNcn: getPopupChildNcn(),
        billMonth: $("#billMonth option:selected").val()
    });

    openPage('pullPopupByPost', '/mypage/childChargeView.do', parameterData);
}

// function btn_reg() {
//     var parameterData = ajaxCommon.getSerializedData({
//        childNcn: getPopupChildNcn(),
//        startDate: pageObj.startDate,
//        endDate: pageObj.endDate
//     });
//
//     openPage('pullPopup2nd', '/mypage/childChargeMonPrint.do', parameterData, "1");
// }