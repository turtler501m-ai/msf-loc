$(document).ready(function() {
    initChildCharge(getPopupChildNcn());

    $(".realSrch").click(function() {
        openDetailPopupByUrl("/m/mypage/childRealTimeRateView.do", getPopupChildNcn());
    });

    $("#billMonth").on("change", function() {
        var parameterData = ajaxCommon.getSerializedData({
            childNcn: getPopupChildNcn(),
            billMonth: $("#billMonth option:selected").val()
        });
        openPage('pullPopupByPost', "/m/mypage/childChargeView.do", parameterData);
    });

    // 납부내역 상세
    $("#acc_header_a1").click(function() {
        var startDate = "";
        var endDate = "";

        var varData = ajaxCommon.getSerializedData({
            childNcn: getPopupChildNcn(),
            startDate: startDate,
            endDate: endDate
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
                recentView(null);
                return;
            } else {
                recentView(jsonObj.itemPay);
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
                $(".billHeader").hide();
                $("#billBody").hide();
            } else {
                // 1. set billHeader
                monthList.forEach(function (month, index, array) {
                    $("#billMonth").append(`<option value=${month.billMonth}>` + month.billMonth.substring(0, 4) + `년` + month.billMonth.substring(4, 6) + `월</option>`);
                });
                $("#billMonth option:eq(0)").prop('selected', true);
                $("#billMonth").children().each(function () {
                    if ($(this).val() == $("#selectedMonth").val()) {
                        $(this).prop('selected', true);
                    }
                });

                $("#monthInfo").text(`${monthPay.month}월 청구요금`);
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

                var listHtml = "";
                itemList.forEach(function (item, index, array) {
                    var splitDescription = item.splitDescription;
                    var messageLine = item.messageLine;
                    var billSeqNo = item.billSeqNo;
                    var amount = parseInt(item.actvAmt).toLocaleString('ko-KR') + "원";

                    if (splitDescription == "당월 요금" || splitDescription == "미납요금") {
                        listHtml += `<li class="c-accordion__item">`;
                        listHtml += `	<div class="c-flex c-flex--jfy-end c-flex--jfy-between">`;
                        listHtml += `		<span>${splitDescription}</span>`;
                        listHtml += `		<span class="fs-13 u-co-gray-7">${amount}</span>`;
                        listHtml += `	</div>`;
                        listHtml += `</li>`;
                    }
                    if ((messageLine && messageLine != "DISCBYSVC") || splitDescription == "할인요금") {
                        listHtml += `<li class="c-accordion__item">`;
                        listHtml += `	<div class="c-accordion__head" data-acc-header="#parent${index}">`;
                        listHtml += `		<button class="c-accordion__button inner--charge" type="button" aria-expanded="false" onclick="detailView('parentView${index}','${messageLine}');">`;
                        listHtml += `			<span>${splitDescription}`;
                        listHtml += `				<input type="hidden" value="${messageLine}" id="mlId${index}" name="messageLineVal" />`;
                        listHtml += `				<input type="hidden" value="${billSeqNo}" id="bs${index}" name="billSeqNoTmp" />`;
                        listHtml += `			</span>`;
                        listHtml += `			<span class="fs-13 u-co-gray-7">${amount}</span>`;
                        listHtml += `		</button>`;
                        listHtml += `	</div>`;
                        listHtml += `	<div class="c-accordion__panel expand c-expand" id="parent${index}">`;
                        listHtml += `		<div class="c-accordion__inside">`;
                        listHtml += `			<div class="fee-list-wrap">`;
                        listHtml += `				<dl class="fee-item--type1">`;
                        listHtml += `					<dd class="fee-subs">`;
                        listHtml += `						<ul id="parentView${index}"></ul>`;
                        listHtml += `					</dd>`;
                        listHtml += `				</dl>`;
                        listHtml += `			</div>`;
                        listHtml += `		</div>`;
                        listHtml += `	</div>`;
                        listHtml += `</li>`;
                    }
                });
                itemList.forEach(function (item, index, array) {
                    var splitDescription = item.splitDescription;
                    var messageLine = item.messageLine;
                    var amount = parseInt(item.actvAmt).toLocaleString('ko-KR') + "원";

                    if (splitDescription == "당월요금계" || !messageLine) {
                        listHtml += `<li class="c-accordion__item">`;
                        listHtml += `	<div class="c-flex c-flex--jfy-end c-flex--jfy-between">`;
                        listHtml += `		<span>${splitDescription}</span>`;
                        listHtml += `		<span class="fs-13 u-co-gray-7">${amount}</span>`;
                        listHtml += `	</div>`;
                        listHtml += `</li>`;
                    }
                });
                $("#accordionB").prepend(listHtml);
                var instance = KTM.Accordion.getInstance(document.querySelector('.c-accordion--type1'));
                instance.update();
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

function recentView(itemPay) {
    var addHtml = "";

    if (itemPay != null && itemPay.length > 0) {
        for (var i = 0; i < itemPay.length; i++) {
            var payMentDate = itemPay[i].payMentDate.substring(0, 8);
            if (payMentDate != null && payMentDate.length > 7) {
                payMentDate = payMentDate.substring(0, 4) + "." + payMentDate.substring(4, 6) + "." + payMentDate.substring(6, 8);
            }
            var payMentMethod = itemPay[i].payMentMethod;
            var payMentMoney = itemPay[i].payMentMoney;

            addHtml += "<tr>";
            addHtml += "  <td>"+payMentDate+"</td>";
            addHtml += "  <td>"+payMentMethod+"</td>";
            addHtml += "  <td class='u-ta-right'>"+numberWithCommas(payMentMoney)+"원</td>";
            addHtml += "</tr>";
        }

        $("#nodata").hide();
        $(".payMent").show();
    } else {
        $("#nodata").show();
        $(".payMent").hide();
    }

    $("#payMentDetail").html(addHtml);

}

function selectDetail() {
    openDetailPopupByUrl("/m/mypage/childChargeView.do", getPopupChildNcn());
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
                    var amt = numberWithCommas(list[i].amt);
                    contents += '<li class="fee-sub-item">';
                    contents += '<span class="fee-sub-title">'+list[i].descr+'</span>';
                    contents += '<span class="fee-sub-charge c-black">'+amt+'원</span>';
                    contents += '</li>';
                }
                $("#"+id).html(contents);
            }
        } else {
            MCP.alert(jsonObj.RESULT_MSG);
        }
    });
}