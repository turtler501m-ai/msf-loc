$(document).ready(function (){

    //캘린더 및 날짜 초기값 설정
    initDateTime();

    // 로밍 하루종일 ON 투게더(대표) 상품은 서브회선 최소 하나는 필수
    if($("#rateAdsvcCd").val() == 'PL2079777') {
        $("#reqLineCnt").val('1');
    }

    //lineCnt개수에 맞는 추가번호 등록창
    setSubLine();


    var lineType = $("#lineType").val();

    //lineType에 따라 대표번호/서브번호 출력
    if(lineType == 'G'){
        $(".addPhoneTitle").hide();
        $(".addPhoneDesc").hide();
    }

    if(lineType == 'M'){
        var subRoamNameHtml = "";
        var rateAdsvcNm = $("#rateAdsvcNm").val();

        subRoamNameHtml += "추가 등록된 고객님도 '";
        subRoamNameHtml += rateAdsvcNm.substr(0, rateAdsvcNm.length - 4);
        subRoamNameHtml += "(서브)'를 별도 가입하셔야합니다.";


        $("#subRoamName").html(subRoamNameHtml);
    }

    //버튼 disabled 체크
    btnChk();

});



//캘린더 및 날짜 초기값 설정
function initDateTime(){
    var dateType = $("#dateType").val();
    var usePrd = ($("#usePrd").val() ? $("#usePrd").val() : 0);
    var  romingRateCd = $("#rateAdsvcCd").val();

    var today = new Date();

    //시작일 설정
    var useStartDate = getUseDate(today,0);

    //종료일 설정(이용기간이 있는 경우에만)
    var useEndDate = '';
    if((dateType == '1' || romingRateCd == 'ITGSAFE3G') && usePrd > 1) {
        useEndDate = getUseDate(today, usePrd - 1);
    }

    //시작일(최대 2개월까지), 종료일(최대 6개월까지) 달력 날짜 설정
    var strtMax = new Date(today.getFullYear(), today.getMonth() + 2, today.getDate());
    var endMax = new Date(today.getFullYear(), today.getMonth() + 6, today.getDate());

    KTM.datePicker('#useStartDate', {
        dateFormat: 'Y-m-d',
        minDate: today,
        maxDate: strtMax
    });

    KTM.datePicker('#useEndDate', {
        dateFormat: 'Y-m-d',
        minDate: today,
        maxDate: endMax
    });

    $("#useStartDate").val(useStartDate);
    $("#useEndDate").val(useEndDate); // 초기값을 ''로 설정 > 이용기간이 설정되어 있지 않으면 빈값 들어감

    //dateType에 따라 시간선택/종료일 선택 금지
    if (dateType == '1') {
        $("#startTime").prop("disabled", true);
        $("#startTime").val("00");
        $("#useEndDate").prop("disabled", true);
    }
    if (dateType == '2') {
        $(".useEndDate").hide();
    }

    if(dateType == '3' &&  romingRateCd == 'ITGSAFE3G'){
        $("#useEndDate").prop("disabled", true);
    }

    if (dateType == '4') {
        $("#startTime").prop("disabled", true);
        $("#startTime").val("");
    }
}


//lineCnt개수에 맞는 추가번호 등록창
function setSubLine() {
    var htmlArea = "";
    var cnt = $("#lineCnt").val();
    var lineType = $("#lineType").val();
    var reqLineCnt = $("#reqLineCnt").val();

    if(lineType == 'S'){
        cnt = 1;
    }

    if (cnt > 0) {
        for (var i = 1; i <= cnt; i++) {
            htmlArea += '<div class="c-form__input-group addPhoneGrp">'
            htmlArea += '	<div class="c-form__input c-form__input-division addPhoneGrp2" name="addPhone">'
            htmlArea += '		<input class="c-input--div3 addPhone onlyNum" id="addPhone' + i + '_1" type="text" pattern="[0-9]" placeholder="숫자입력" title="연락처 앞자리 입력" maxLength="3" onKeyUp="nextFocus(this, 3, \'addPhone'+i+'_2\');">'
            htmlArea += '		<span>-</span>'
            htmlArea += '		<input class="c-input--div3 addPhone onlyNum" id="addPhone' + i + '_2" type="text" pattern="[0-9]" placeholder="숫자입력" title="연락처 중간자리 입력" maxLength="4" onKeyUp="nextFocus(this, 4, \'addPhone'+i+'_3\');">'
            htmlArea += '		<span>-</span>'
            htmlArea += '		<input class="c-input--div3 addPhone onlyNum" id="addPhone' + i + '_3" type="text" pattern="[0-9]" placeholder="숫자입력" title="연락처 뒷자리 입력" maxLength="4">'
            htmlArea += '		<label class="c-form__label" for="addPhone' + i + '_1">휴대폰 번호'
            if(lineType == 'M') {
                if(reqLineCnt > 0) {
                    htmlArea += '(필수)'
                    reqLineCnt--;
                } else {
                    htmlArea += '(선택)'
                }
            }
            htmlArea += '</label>'
            htmlArea += '	</div>'
            htmlArea += '</div>'
        }
        $("#addPhone").append(htmlArea);
    }
}

//시작날짜 변경 유효성 검사 및 종료일자 set
$("#useStartDate").change(function (){
    $("#checkBtn").prop("disabled", true);

    var dateType = $("#dateType").val();
    var usePrd = ($("#usePrd").val() ? $("#usePrd").val() : 0);


    var today = new Date();
    var useToday = getUseDate(today, 0).replace(/[^0-9]/g, '');
    var useTodayVal = getUseDate(today, 0);

    var strtMax = new Date(today.getFullYear(), today.getMonth() + 2, today.getDate());
    var useStrtMax = getUseDate(strtMax, 0).replace(/[^0-9]/g, '');

    var useStartDate = $('#useStartDate').val().replace(/[^0-9]/g, '');
    var useEndDate = ($('#useEndDate').val() ? $('#useEndDate').val() : '9999.12.31').replace(/[^0-9]/g, '');

    var lineType = $("#lineType").val();
    var  romingRateCd = $("#rateAdsvcCd").val();

    //시작일자가 오늘보다 이전이면 false
    if(useStartDate < useToday){
        $('#useStartDate').val(useTodayVal);
        MCP.alert("시작일자는 오늘 날짜보다 이전일 수 없습니다.",function (){
            $('#useStartDate').focus();
        });
    }
    //시작일자가 오늘보다 2개월 이후면 false
    if (useStartDate > useStrtMax){
        $('#useStartDate').val(useTodayVal);
        MCP.alert("시작일자는 최대 2개월까지만 선택 가능합니다.",function (){
            $('#useStartDate').focus();
        });
    }
    //시작일자가 종료일자보다 크다면 false
    if(dateType == '3' || dateType == '4') {
        if(romingRateCd !='ITGSAFE3G'){
            if(useStartDate > useEndDate) {
                $('#useStartDate').val(useTodayVal);
                MCP.alert("시작일자는 종료일자 이전이어야 합니다.",function (){
                    $('#useStartDate').focus();
                });
            }
        }
    }
    //202309-wooki 추가
    if(lineType == 'M' || lineType == 'G'){
        //시작일자와 종료일 같으면 false
        if (useStartDate == useEndDate) {
            if(romingRateCd !='ITGSAFE3G'){
                    $('#useStartDate').val(useTodayVal);
                    MCP.alert("시작일자는 종료일자 이전이어야 합니다.", function () {
                        $('#useStartDate').focus();
                    });
            }
        }
    }

    //시작일자에 따라 종료일 설정
    if(dateType == '1' && usePrd > 1) {
        var newEndDate = getUseDate($("#useStartDate").val(), usePrd - 1);
        $('#useEndDate').val(newEndDate);
    }else if(dateType =='3' && usePrd > 1 && romingRateCd =='ITGSAFE3G' ){
        var newEndDate = getUseDate($("#useStartDate").val(), usePrd - 1);
        $('#useEndDate').val(newEndDate);
    }

    btnChk();

});



//종료 날짜 변경함수
$("#useEndDate").change(function (){
    $("#checkBtn").prop("disabled", true);

    var today = new Date();
    var endMax = new Date(today.getFullYear(), today.getMonth()+6, today.getDate());
    var useEndMax = getUseDate(endMax, 0).replace(/[^0-9]/g, '');

    var useStartDate = $("#useStartDate").val().replace(/[^0-9]/g, '');
    var useEndDate = $("#useEndDate").val().replace(/[^0-9]/g, '');

    var lineType = $("#lineType").val();

    //종료일자가 6개월보다 이후라면 false
    if (useEndDate > useEndMax){
        $('#useEndDate').val('');
        MCP.alert("종료일자는 최대 6개월까지만 선택 가능합니다.",function (){
            $('#useEndDate').focus();
        });
        return false;
    }

    //종료일자가 시작일자보다 이전이면 false
    if(useStartDate > useEndDate) {
        $('#useEndDate').val('');
        MCP.alert("종료일자는 시작일자 이후여야 합니다.",function (){
            $('#useEndDate').focus();
        });
        return false;
    }

    //202309-wooki 추가
    if(lineType == 'M' || lineType == 'G'){
        //시작일자와 종료일 같으면 리턴false
        if (useStartDate == useEndDate) {
            $('#useEndDate').val('');
            MCP.alert("종료일자는 시작일자 이후여야 합니다.", function () {
                $('#useEndDate').focus();
            });
            return false;
        }
    }

    btnChk();

});

//휴대폰번호 자동 입력
function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}



// 휴대폰번호 유효성
$(".addPhone").on('input keyup',function() {
    $("#checkBtn").prop("disabled", true);

    var addPhoneGrp = $(this).closest('.addPhoneGrp');

    if(addPhoneGrp.next().hasClass('c-form__text')) {
        addPhoneGrp.next().remove();
    }
    addPhoneGrp.removeClass("has-error");

    var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    var lineType = $("#lineType").val();

    var addPhone = '';
    addPhoneGrp.find('.addPhone').each(function() {
        addPhone += $(this).val();
    });

    //서브 상품인 경우
    if(lineType == 'S') {
        //11자리 입력이 아닌 경우 false
        if(addPhone.length != 11) {
            return false;
        }
        //올바른 번호 형태가 아닌 경우
        if(!regPhone.test(addPhone)) {
            if(!addPhoneGrp.next().hasClass('c-form__text')) {
                viewErrorMgs(addPhoneGrp.find('input').first(), "유효한 번호가 아닙니다.<br>확인 후 다시 입력해주세요.")
            }
            return false;
        }
    }

    //대표 상품인 경우
    if(lineType == 'M') {
        //11자리 입력, 미입력이 아닌 경우 false
        if(addPhone.length != 11 && addPhone.length != 0) {
            return false;
        }
        //11자리인 경우 유효한 번호 형태가 아니면 false
        if(addPhone.length == 11) {
            if(!regPhone.test(addPhone)) {
                if(!addPhoneGrp.next().hasClass('c-form__text')) {
                    viewErrorMgs(addPhoneGrp.find('input').first(), "유효한 번호가 아닙니다.<br>확인 후 다시 입력해주세요.")
                    return false;
                }
            }
        }
    }
    btnChk();
});

//dateType이 2일때, 시간 선택 하면 disabled버튼 처리
$("#startTime").change(function (){
    btnChk();
});




// 선택 버튼 상태 변경 (활성, 비활성)
function btnChk() {
    $("#checkBtn").prop("disabled", true);

    var dateType = $("#dateType").val();
    var useStartDate = $('#useStartDate').val();
    var startTime = $('#startTime').val();
    var useEndDate = $('#useEndDate').val();

    //시작일,시작시간,종료일 유효성 검사
    if(dateType != '9' && dateType != null && dateType != ""){
        if (!useStartDate) {
            return false;
        }
    }
    if (dateType == '2' || dateType == '3') {
        if (!startTime) {
            return false;
        }
    }
    if (dateType == '3' || dateType == '4') {
        if (!useEndDate) {
            return false;
        }
    }

    //폰번호 유효성 검사
    var lineType = $("#lineType").val();
    var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    var valiChk = true;
    var addPhoneList = [];

    $('.addPhoneGrp2').each(function () {
        var addPhone = '';

        $(this).children('input').each(function () {
            addPhone += $(this).val();
        });

        //서브 상품인 경우
        if (lineType == 'S') {
            if (addPhone.length != 11) {
                valiChk = false;
                return false;
            }
            if (!regPhone.test(addPhone)) {
                valiChk = false;
                return false;
            }
        }

        //대표 상품인 경우
        if (lineType == 'M') {
            //11자리 입력, 미입력이 아닌 경우 false
            if (addPhone.length != 11 && addPhone.length != 0) {
                valiChk = false;
                return false;
            }
            //11자리인 경우 유효한 번호 형태가 아니면 false
            if (addPhone.length == 11) {
                if (!regPhone.test(addPhone)) {
                    valiChk = false;
                    return false;
                }
            }
        }

        if(addPhone && addPhone.length != 0) {
            addPhoneList.push(addPhone);
        }
    });

    //필수 입력 회선 수 확인
    if(lineType == 'M') {
        if(addPhoneList.length < $("#reqLineCnt").val()) {
            return false;
        }
    }

    if (valiChk) {
        $("#checkBtn").prop("disabled", false);
    }
};

//취소 버튼 클릭
function btn_cancel() {
    $('.c-icon--close').trigger('click');
}

//선택 버튼 클릭
$("#checkBtn").click(function () {
    // 에러 문구 제거
    $(".c-form__text").remove();
    $(".has-error").removeClass("has-error");
    var msg = '';

    var dateType = $("#dateType").val();

    var today = new Date();
    var useToday = getUseDate(today, 0).replace(/[^0-9]/g, '');
    var hours = today.getHours();

    var strtMax = new Date(today.getFullYear(), today.getMonth() + 2, today.getDate());
    var useStrtMax = getUseDate(strtMax, 0).replace(/[^0-9]/g, '');
    var endMax = new Date(today.getFullYear(), today.getMonth() + 6, today.getDate());
    var useEndMax = getUseDate(endMax, 0).replace(/[^0-9]/g, '');

    var useStartDate = ajaxCommon.isNullNvl($('#useStartDate').val(), "").replace(/[^0-9]/g, '');
    var startTime = ajaxCommon.isNullNvl($('#startTime').val(), "");
    var useEndDate = ajaxCommon.isNullNvl($('#useEndDate').val(), "").replace(/[^0-9]/g, '');

    var lineType = $("#lineType").val();

    //시작일, 시작시간, 종료일 체크
    if(dateType != '9' && dateType != null && dateType != ""){
        if (!useStartDate) {
            MCP.alert("시작일을 선택해주세요.", function () {
                $('#useStartDate').focus();
            });
            return false;
        }
    }
    if (dateType == '2' || dateType == '3') {
        if (!startTime) {
            MCP.alert("시작시간을 선택해주세요.", function () {
                $('#startTime').focus();
            });
            return false;
        }else if (useToday == useStartDate && startTime < hours){
            MCP.alert("현재 시간보다 이전 시간은 선택할 수 없습니다.", function () {
                $('#startTime').focus();
            });
            return false;
        }
    }
    if (dateType == '3' || dateType == '4') {
        if (!useEndDate) {
            MCP.alert("종료일을 선택해주세요.", function () {
                $('#useEndDate').focus();
            });
            return false;
        }
    }

    //시작일자가 오늘보다 이전이면 false
    if(dateType != '9' && dateType != null && dateType != ""){
        if (useStartDate < useToday) {
            MCP.alert("시작일자는 오늘 날짜보다 이전일 수 없습니다.", function () {
                $('#useStartDate').focus();
            });
            return false;
        }
        //시작일자가 오늘보다 2개월 이후면 false
        if (useStartDate > useStrtMax) {
            MCP.alert("시작일자는 최대 2개월까지만 선택 가능합니다.", function () {
                $('#useStartDate').focus();
            });
            return false;
        }
        //시작일자가 종료일자보다 이전이 아니면 false
        if (dateType == '3' || dateType == '4') {
            if (useStartDate > useEndDate) {
                MCP.alert("종료일자는 시작일자 이후여야합니다.", function () {
                    $('#useEndDate').focus();
                });
                return false;
            }

            //종료일자가 오늘보다 6개월 이후면 false
            if (useEndDate > useEndMax) {
                MCP.alert("종료일자는 최대 6개월까지만 선택 가능합니다.", function () {
                    $('#useEndDate').focus();
                });
                return false;
            }
        }
    }

    //202309-wooki 추가
    if(dateType != '9' && dateType != null && dateType != ""){
        if(lineType == 'M' || lineType == 'G'){
            //시작일자와 종료일 같으면 리턴false
            if (useStartDate == useEndDate) {
                MCP.alert("종료일자는 시작일자보다 커야 합니다.", function () {
                    $('#useEndDate').focus();
                });
                return false;
            }
        }
    }

    var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    var valiChk = true;
    var addPhoneList = [];
    $('.addPhoneGrp2').each(function () {
        var addPhone = '';
        $(this).children('input').each(function () {
            addPhone += $(this).val();
        });

        //서브 상품인 경우
        if (lineType == 'S') {
            if (addPhone.length != 11) {
                valiChk = false;
                msg = "유효한 번호가 아닙니다.<br>확인 후 다시 입력해주세요.";
                MCP.alert(msg);
                viewErrorMgs($(this).find('input').first(), msg);
                $(this).find('input').first().focus();
                return false;
            }
            if (!regPhone.test(addPhone)) {
                valiChk = false;
                msg = "유효한 번호가 아닙니다.<br>확인 후 다시 입력해주세요.";
                MCP.alert(msg);
                viewErrorMgs($(this).find('input').first(), msg);
                $(this).find('input').first().focus();
                return false;
            }
        }

        //대표 상품인 경우
        if (lineType == 'M') {
            //11자리 입력, 미입력이 아닌 경우 false
            if (addPhone.length != 11 && addPhone.length != 0) {
                valiChk = false;
                msg = "유효한 번호가 아닙니다.<br>확인 후 다시 입력해주세요.";
                MCP.alert(msg);
                viewErrorMgs($(this).find('input').first(), msg);
                $(this).find('input').first().focus();
                return false;
            }
            //11자리인 경우 유효한 번호 형태가 아니면 false
            if (addPhone.length == 11) {
                if (!regPhone.test(addPhone)) {
                    valiChk = false;
                    msg = "유효한 번호가 아닙니다.<br>확인 후 다시 입력해주세요.";
                    MCP.alert(msg);
                    viewErrorMgs($(this).find('input').first(), msg);
                    $(this).find('input').first().focus();
                    return false;
                }
            }
        }

        if (addPhone && addPhone.length != 0) {
            addPhoneList.push(addPhone);
        }
    });

    // 필수 입력 회선 수 확인
    if(lineType == 'M') {
        var reqLineCnt = $("#reqLineCnt").val();
        if(addPhoneList.length < reqLineCnt) {
            msg = reqLineCnt + "개의 회선을 필수로 입력해주세요.";
            MCP.alert(msg);
            viewErrorMgs($('.addPhoneGrp2').find('input').first(), msg);
            $('.addPhoneGrp2').find('input').first().focus();
            return false;
        }
    }

    if (!valiChk) {
        return false;
    }

    //휴대폰 번호 담기
    var paramPhone = [];
    if (lineType == 'S') {
        paramPhone.push(addPhoneList[0]);
    } else if (lineType == 'M') {
        addPhoneList.forEach(function (item) {
            paramPhone.push(item);
        });
    }

    roamingCom(paramPhone);
});

function roamingCom(paramPhone) {

    if ($("#flag").val() == "Y") {
        confirm = "해외 로밍 상품을 변경하시겠습니까?";
    } else {
        confirm = "해외 로밍 상품을 신청하시겠습니까?";
    }


    KTM.Confirm(confirm, function () {
        var varData2 = ajaxCommon.getSerializedData({
            ncn: $("#contractNum").val()
            , rateAdsvcGdncSeq: $("#rateAdsvcGdncSeq").val()
            , soc: $("#rateAdsvcCd").val()
            , strtDt : ajaxCommon.isNullNvl($("#useStartDate").val(), "").replace(/[^0-9]/g, '')
            , strtTm : ajaxCommon.isNullNvl($("#startTime option:selected").val(),"")
            , endDt : ajaxCommon.isNullNvl($("#useEndDate").val(), "").replace(/[^0-9]/g, '')
            , addPhone: paramPhone
            , flag: $("#flag").val()
            , prodHstSeq : $("#prodHstSeq").val()
        });
        this.close();
        KTM.LoadingSpinner.show();
        $.ajax({
            id: 'roamingJoinAjax'
            , cache: false
            , url: '/rate/roamingJoinAjax.do'
            , data: varData2
            , async: true
            , dataType: "json"
            , success: function (jsonObj) {
                if (jsonObj.returnCode == "00") {
                    var msg = "";
                    if ($("#flag").val() == "Y") {
                        msg = "해외로밍 변경이 완료되었습니다.";
                    } else {
                        msg = "해외로밍 신청이 완료되었습니다.";
                    }
                    MCP.alert(msg, function () {
                        $('.c-icon--close').trigger('click');
                        location.href = '/mypage/roamingView.do';
                    });
                } else {
                    if (jsonObj.RESULT_MSG) {
                        MCP.alert(jsonObj.RESULT_MSG);
                    } else if (jsonObj.message) {
                        MCP.alert(jsonObj.message);
                    }
                }
                KTM.LoadingSpinner.hide();
            }, error: function () {
                alert("오류가 발생했습니다.다시 시도해주세요.");
                KTM.LoadingSpinner.hide();
            }
        });

    });
}


//에러메시지 보여주기
var viewErrorMgs = function($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
}

// date 일자에 period 만큼 더한 값 datePicker format으로 리턴
function getUseDate(paramDate, period) {
    var date = new Date(paramDate);
    var useDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() + parseInt(period));
    var useMonth = useDate.getMonth() + 1;
    useMonth = useMonth >= 10 ? useMonth : '0' + useMonth;
    var useDay = useDate.getDate();
    useDay = useDay >= 10 ? useDay : '0' + useDay;
    useDate = useDate.getFullYear() + '-' + useMonth + '-' + useDay;

    return useDate;
}
