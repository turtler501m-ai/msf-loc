var standardCount = 10; //더보기 기준값
var selectColVal_0 = ''; //폰비교 선택값
var selectColVal_1 = ''; //폰비교 선택값
var compareCnt = 0;
var colorData;

var pageObj = {
    chkProductList:[]
    , phoneList:[]
}

$(document).ready(function(){

    initColorData();


    $("input[name='chkProduct']:checked").each(function (){
        pageObj.chkProductList.push($(this).val());
    });


    $(document).on("change", "input[name='chkProduct']", function(){
        //$("input[name='chkProduct']:checked").change(function(){

        let thidId = $(this).attr("id");

        if ("chkProduct_0" == thidId) {
            //전체선택
            //$(this).html("전체1")
            $("#orderButton").html('<span class="u-mr--4 fs-14">추천순1</span>'+'<i class="c-icon c-icon--sort" aria-hidden="true"></i>');
            if ($(this).is(':checked')) {
                $("#chkProduct_1").prop("checked", true);
                $("#chkProduct_2").prop("checked", true);
                $("#chkProduct_3").prop("checked", true);
            } else {
                $("#chkProduct_1").prop("checked", false);
                $("#chkProduct_2").prop("checked", false);
                $("#chkProduct_3").prop("checked", false);
            }
        } else {
            //전체선택
            if ($(this).is(':checked')) {
                if ($("#chkProduct_1").is(':checked') && $("#chkProduct_2").is(':checked') && $("#chkProduct_3").is(':checked')) {
                    $("#chkProduct_0").prop("checked", true);
                }
            } else {
                $("#chkProduct_0").prop("checked", false);
            }
        }

        pageObj.chkProductList = [];
        $("input[name='chkProduct']:checked").each(function (){
            pageObj.chkProductList.push($(this).val());
        });

        viewPhoneList();
    });

    $("#bsSampleDesc > div >  input[name='radList']").click(function() {

        $("input[name='listOrderEnum']").val($(this).data('value'));
        var selRadioId = $(this).prop('id');
        var selRadioText = $("#bsSampleDesc > div >  label[for='"+selRadioId+"']").text();
        $("#orderButton").html('<span class="u-mr--4 fs-14">'+selRadioText+'</span>'+'<i class="c-icon c-icon--sort" aria-hidden="true"></i>');
        $("#bsSortList_close_button").click();
        callPhoneList();
    });


    //상품 체크박스 선택시
    $(document).on("click", "#product_list > li > div > div > span > input[type='checkbox']", function(){
        allClearCompareItem();
        compareItem($(this));
    });

    //비교상품 폰선택
    $(document).on("change", "select[id^='phoneSelect_']", function(){
        var phoneSelectId = $(this).attr("id");
        var lastIdx = phoneSelectId.lastIndexOf("_");
        var compareIdx = parseInt(phoneSelectId.substring(lastIdx+1,phoneSelectId.length));
        initComparePhoneCheckList(); //비교함을 공용으로 사용해서 동기화필요

        if($(this).val() == ''){
            initCompareItem(compareIdx);
            return;
        }

        rateCd = $("#"+phoneSelectId+" option:selected").data("reprate");
        $("#phone_button_"+compareIdx).prop("classList","c-button c-button--lg c-button--full c-button--red c-button--h48 u-mt--32");
        callCompareAjaxProd($(this).val(),compareIdx,rateCd,"");

    });

    //비교상품 요금제 선택
    $(document).on("change", "select[id^='rateSelect_']", function(){
        var selId = $(this).attr("id");
        var lastIdx = selId.lastIndexOf("_");
        var compareIdx = parseInt(selId.substring(lastIdx+1,selId.length));

        var prodId = $("select[name='phoneSelect_"+compareIdx+"']").val();
        if (prodId ==  null) {
            MCP.alert("핸드폰을 선택해 주세요.");
            $("select[name='rateSelect_"+compareIdx+"']").val("");
            return;
        }

        var selectColVal = $("select[name='colorSelect_"+compareIdx+"']").val();
        if (selectColVal ==  null) {
            selectColVal = "";
        }
        callCompareAjaxProd(prodId,compareIdx,$(this).val(),"");

    });

    //비교상품 컬러/용량 선택
    $(document).on("change", "select[id^='colorSelect_']", function(){
        var selId = $(this).attr("id");
        var lastIdx = selId.lastIndexOf("_");
        var compareIdx = parseInt(selId.substring(lastIdx+1,selId.length));

        var prodId = $("select[name='phoneSelect_"+compareIdx+"']").val();
        if (prodId ==  null) {
            MCP.alert("핸드폰을 선택해 주세요.");
            return;
        }
        var rateCd = $("select[name='rateSelect_"+compareIdx+"']").val();
        if (rateCd ==  null) {
            rateCd = "";
        }
        if(compareIdx == 0){
            selectColVal_0 = $(this).val();
        }else if(compareIdx == 1){
            selectColVal_1 = $(this).val();
        }
        callCompareAjaxProd(prodId,compareIdx,rateCd,$(this).val());
    });

    //가입하기 버튼 클릭
    $(document).on("click", "button[id^='phone_button_']", function(){

        var btnId = $(this).attr("id");
        var lastIdx = btnId.lastIndexOf("_");
        var compareIdx = btnId.substring(lastIdx+1,btnId.length);

        var prodId = $("select[name='phoneSelect_"+compareIdx+"']").val();
        var colorVal = $("select[name='colorSelect_"+compareIdx+"']").val();
        var rateSelectVal = $("select[name='rateSelect_"+compareIdx+"']").val();
        if(prodId == null || prodId == ''){
            MCP.alert("핸드폰을 선택해 주세요.");
            return;
        }

        if(colorVal == null || colorVal == ''){
            MCP.alert("컬러를 선택해 주세요.");
            return;
        }

        var sesplsYn = $("select[name='rateSelect_"+compareIdx+"']").data("sesplsYn");
        if(sesplsYn == 'N' && (rateSelectVal == null || rateSelectVal == '')){
            MCP.alert("요금제를 선택해 주세요.");
            return;
        }
        var colorText = $("select[name='colorSelect_"+compareIdx+"'] option:checked").text();
        if(colorText.indexOf('품절') > -1){
            MCP.alert("선택하신 용량/색상은 품절 되었습니다.");
            return;
        }
        var outprc = $("#outprc_"+compareIdx).data('outprc');
        var prodCtgType = $("#ptype_"+compareIdx).data('ptype');
        document.appForm.prodCtgType.value = prodCtgType;
        document.appForm.outUnitPric.value = outprc;
        document.appForm.selPric.value = outprc;
        document.appForm.rateCd.value = rateSelectVal;
        document.appForm.hndsetModelId.value = colorVal;
        document.appForm.prodId.value = prodId;
        document.appForm.submit();


    });


    //더보기
    $("#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__more > button").click(function() {
        viewMore();
    });

    //비교상품 팝업 선택
    $("#main-content > div.c-side-badge").click(function(){

        if (compareCnt< 1) {
            MCP.alert('비교하실 상품을 선택해 주세요.');
            return;
        }
        callChargeList();

    });








    setTimeout(function() {
        $("#chkProduct_0").prop("checked", true);
        $("#chkProduct_1").prop("checked", true);
        $("#chkProduct_2").prop("checked", true);
        $("#chkProduct_3").prop("checked", true);
        pageObj.chkProductList.push("0141");
        pageObj.chkProductList.push("0222");
        pageObj.chkProductList.push("9999");

        //폰목록 조회
        callPhoneList();
        //휴대폰비교하기 폰 셀렉트박스 초기화
        initSelectPhoneList();

    }, 500)

    //console.log("thidId222==>end");
});

function comapareSessionUpdate(){

    sessionProc('','','','','removeAll',0);
    var sessionIdx  = 0;
    for(var i = 0 ; i < 2 ; i++){
        var prodId = $("select[name='phoneSelect_"+i+"']").val();
        if (prodId !=  null && prodId != '') {
            var rateCd = $("select[name='rateSelect_"+i+"']").val();
            if (rateCd ==  null) {
                rateCd = "";
            }
            var selectColVal = $("select[name='colorSelect_"+i+"']").val();
            if (selectColVal ==  null) {
                selectColVal = "";
            }
            sessionProc(prodId,rateCd,selectColVal,'','add',sessionIdx);
            sessionIdx ++;
        }
    }

}

/**
 요금제 비교 폰변경시 비교대상 체크박스 선택을 동기화한다.
 */
var initComparePhoneCheckList = function() {
    var frstSelVal = $("select[name='phoneSelect_0']").val();
    var scndSelVal = $("select[name='phoneSelect_1']").val();

    $("#product_list > li > div > div > span > input[type='checkbox']").each(function(){
        if(frstSelVal == $(this).data("prodid") || scndSelVal == $(this).data("prodid")){
            $(this).prop("checked",true);
            //chechedCnt++;
        }else{
            $(this).prop("checked",false);
        }
    });

};

var callChargeList = function() {

    allClearCompareItem();

    //세션에 저장되어있는 목록 조회
    sessionProc('','','','','search',0);

};

/**
 비교할 상품의 해당되는 요금제의 가격정보를 가져온다.
 @param prodId : 상품 아이디
 @param idx    : 순서
 @param rateCd : 요금제코드
 */
var callCompareAjaxProd = function(prodId,idx,rateCd,hndsetModelId) {
    var varData = ajaxCommon.getSerializedData({
        prodId : prodId
        ,rateCd : rateCd
        ,hndsetModelId : hndsetModelId
    });
    ajaxCommon.getItem({
        id : 'phoneListCompareAjax',
        cache : false,
        url : '/m/product/phone/phoneListCompareAjax.do',
        data : varData,
        async : false,
        dataType : "json"
    }, function(jsonObj) {
        if (typeof jsonObj.RESULT_MSG != "undefined") {
            alert(jsonObj.RESULT_MSG);
            return false;
        }

        //휴대폰 선택
        $("#phoneSelect_"+idx).val(prodId);

        var sesplsYn = jsonObj.phoneProdBas.sesplsYn; //자급제여부
        var prodCtgId = jsonObj.phoneProdBas.prodCtgId;
        var agrmTrmBase = jsonObj.phoneProdBas.agrmTrmBase ? jsonObj.phoneProdBas.agrmTrmBase : 24;

        var prodCtgType = '01';
        if(sesplsYn == 'Y'){
            prodCtgType = '03';
        }else if(prodCtgId == '04'){
            prodCtgType = '02';
        }

        $("#rateSelect_"+idx+" > option").remove();
        $("#rateSelect_"+idx).append('<option label="요금제 선택" disabled>요금제 선택</option>');
        var mspRateCd = '';
        var firtRateFlag = true;
        $.each(jsonObj.chargeList,function(chargeListIdx,data) {

            //$.each(jsonObj.baseChargeList,function(baseChargeListIdx,baseChargedata) {

                //if(baseChargedata.rateCd == data.rateCd){
                    if(firtRateFlag){
                        mspRateCd = data.rateCd;
                        firtRateFlag = false;
                    }
                    /*var optionHtml = '<option value="'+data.rateCd+'">'+data.rateNm+'</option>';
                    $("#rateSelect_"+idx).append(optionHtml);*/
                    if(agrmTrmBase == data.agrmTrm){
                        let selected = '';
                        if(rateCd != '' && rateCd == data.rateCd){
                            selected += ' selected';
                        }
                        let optionHtml = '<option value="' + data.rateCd + '"' + selected + '>' +
                        (data.rateAdsvcNm ? data.rateAdsvcNm : data.rateNm) + '</option>';
                        $("#rateSelect_"+idx).append(optionHtml);
                    }
                //}

            //});


        });

        if(rateCd != ''){
            mspRateCd = rateCd;
        }

        if(sesplsYn == 'Y'){

            $("#rateSelect_"+idx).val("");
            $("#rateSelect_"+idx).prop("disabled",true);
            $("#rateSelect_"+idx).data("sesplsYn","Y");

            $("#phone_dl_"+idx).html('');
            var dtHtml = '';
            dtHtml = '<dl class="u-mt--24"></dl>';
            $("#phone_dl_"+idx).html(dtHtml);

            $("#phone_li_"+idx).html('');
            var phoneLiHtml = '';
            phoneLiHtml += '<dl class="phone-comparison__info">';
            phoneLiHtml += '<dt>출고가</dt>';
            phoneLiHtml += '<dd id="outprc_+'+idx+'" >'+numberWithCommas(jsonObj.phoneProdBas.inUnitPric)+'원</dd>';
            phoneLiHtml += '</dl>';
            phoneLiHtml += '<dl class="phone-comparison__info">';
            phoneLiHtml += '<dt>판매가</dt>';
            phoneLiHtml += '<dd>'+numberWithCommas(jsonObj.phoneProdBas.outUnitPric)+'원</dd>';
            phoneLiHtml += '</dl>';
            phoneLiHtml += '<dl class="phone-comparison__info">';
            phoneLiHtml += '<dt>-</dt>';
            phoneLiHtml += '<dd>-</dd>';
            phoneLiHtml += '</dl>';
            phoneLiHtml += '<dl class="phone-comparison__info">';
            phoneLiHtml += '<dt>-</dt>';
            phoneLiHtml += '<dd>-</dd>';
            phoneLiHtml += '</dl>';
            $("#phone_li_"+idx).html(phoneLiHtml);

        }else{

            $.each(jsonObj.chargeList,function(chargeListIdx,data) {
                if (mspRateCd == data.rateCd && agrmTrmBase == data.agrmTrm) {
                    if (data.sprtTp === 'KD') {
                        setphoneLiData(data,idx);
                        return false;
                    }

                    if (data.sprtTp === 'PM') {
                        setphoneLiData(data,idx);
                        return false;
                    }

                }
            });

        }

        var phoneCompLiHtml = '';
        $("#phone_comp_li_"+idx).html('');
        phoneCompLiHtml += '<dl id="ptype_'+idx+'" data-ptype="'+prodCtgType+'" class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>네트워크</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.prodCtgIdLabel)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl id="outprc_'+idx+'" data-outprc="'+jsonObj.phoneProdBas.outUnitPric+'" class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>디스플레이</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyDisp)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>크기</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntySize)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>무게</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyWeight)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>메모리</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyMemr)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>배터리</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyBtry)+'</dd>';
        phoneCompLiHtml += '</dl>';
        phoneCompLiHtml += '<dl class="phone-comparison__info">';
        phoneCompLiHtml += '<dt>카메라</dt>';
        phoneCompLiHtml += '<dd>'+emptyToTyphoon(jsonObj.phoneProdBas.sntyCam)+'</dd>';
        phoneCompLiHtml += '</dl>';
        $("#phone_comp_li_"+idx).html(phoneCompLiHtml);


        var sntyCd = '';
        var atribValNm2 = '';
        $("#colorSelect_"+idx+" > option").remove();
        $.each(jsonObj.phoneProdBas.phoneSntyBasDtosList,function(phoneSntyBasDtosListIdx,data) {
            var sdLbel = "";
            if (data.sdoutYn == 'Y') {
                sdLbel = "[품절]";
            }

            if (phoneSntyBasDtosListIdx == 0){
                sntyCd = data.atribValCd1;
                atribValNm2 = data.atribValNm2;

            }

            var optionHtml = '<option value="'+data.hndsetModelId+'"';

            if(idx == 0 && selectColVal_0 != '' && selectColVal_0 == data.hndsetModelId){
                optionHtml += 'selected >';
                sntyCd = data.atribValCd1;
                atribValNm2 = data.atribValNm2;
            }else if(idx == 1 && selectColVal_1 != '' && selectColVal_1 == data.hndsetModelId){
                optionHtml += 'selected >';
                sntyCd = data.atribValCd1;
                atribValNm2 = data.atribValNm2;
            }else{
                optionHtml += '>';
            }
            optionHtml += data.atribValNm2+'/'+data.atribValNm1+sdLbel+'</option>';

            $("#colorSelect_"+idx).append(optionHtml);
        });

        $.each(jsonObj.phoneProdBas.phoneProdImgDtoList,function(phoneProdImgDtoDataIdx,phoneProdImgDtoData) {
            if(phoneProdImgDtoData.sntyColorCd == sntyCd){
                $("#phone_img_div_"+idx).html('');
                var imgHtml = '';
                imgHtml += '<div class="phone-box u-mt--40">';
                imgHtml += '<img class="phone-img" src="'+phoneProdImgDtoData.phoneProdImgDetailDtoList[0].imgPath+'"  alt="">';
                imgHtml += '<strong class="c-text c-text--type2 u-mt--24 c-text-ellipsis--type2">';
                imgHtml += '<b>'+jsonObj.phoneProdBas.prodNm+'</b>';
                imgHtml += '</strong>';
                imgHtml += '<p class="c-text c-text--type3 u-mt--8">'+atribValNm2+'</p>';
                imgHtml += '<div class="color-select u-mt--16">';
                imgHtml += '<span class="color-dot" style="background: #232227"></span>';
                imgHtml += '<span class="c-text c-text--type5 u-co-gray">'+phoneProdImgDtoData.atribVal+'</span>';
                imgHtml += '</div>';
                imgHtml += '</div>';
                $("#phone_img_div_"+idx).html(imgHtml);
            }
        });

        for(var i = 0 ; i < 2 ; i++){
            if($("#rateSelect_"+i).data("sesplsYn") != 'Y'){

                if(emptyToTyphoon($("#phoneSelect_"+i).val()) != '-' && emptyToTyphoon($("#colorSelect_"+i).val()) != '-' && emptyToTyphoon($("#rateSelect_"+i).val()) != '-'){
                    $("#phone_button_"+i).prop("disabled",false);
                }else{
                    $("#phone_button_"+i).prop("disabled",true);
                }

                if(emptyToTyphoon($("#phoneSelect_"+i).val()) != '-'){
                    $("#rateSelect_"+i).prop("disabled",false);
                }else{
                    $("#rateSelect_"+i).prop("disabled",true);
                }

            }else{
                if(emptyToTyphoon($("#phoneSelect_"+i).val()) != '-' && emptyToTyphoon($("#colorSelect_"+i).val()) != '-'){
                    $("#phone_button_"+i).prop("disabled",false);
                }else{
                    $("#phone_button_"+i).prop("disabled",true);
                }
            }

        }
        $("span[id^='phone_img_div_'] > div > img").each(function(){
            $(this).error(function(){
                $(this).unbind("error");
                $(this).attr("src","/resources/images/mobile/content/img_240_no_phone.png");
            });
        });

        comapareSessionUpdate();
        MCP.openPopup("#phone-compare-dialog");

    });
};

function setphoneLiData(data,idx){
    var desc = data.mspRateMstDto.rmk;
    desc = desc.replace(/<br>/g, "");
    $("#rateSelect_" + idx).data("sesplsYn", "N");
    $("#rateSelect_" + idx).val(data.mspRateMstDto.rateCd);

    $("#phone_dl_" + idx).html('');
    var dtHtml = '';
    dtHtml += '<dl class="charge-box">';
    dtHtml += '<dt class="charge-box__title">' + data.mspRateMstDto.rateNm + '</dt>';
    dtHtml += '<dd class="charge-box__text">';
    dtHtml += '  <span>음성: ' + data.freeCallCntByMobile + '</span>';
    dtHtml += '  <span>문자: ' + data.freeSmsCntByMobile + '</span>';
    dtHtml += '  <span>데이터: ' + data.freeDataCntByMobile + '</span>';
    dtHtml += '</dd>';
    dtHtml += '</dl>';
    $("#phone_dl_" + idx).html(dtHtml);

    $("#phone_li_" + idx).html('');
    var phoneLiHtml = '';
    phoneLiHtml += '<dl class="phone-comparison__info">';
    phoneLiHtml += '<dt>출고가</dt>';
    phoneLiHtml += '<dd>' + numberWithCommas(data.hndstAmt) + '원</dd>';
    phoneLiHtml += '</dl>';
    phoneLiHtml += '<dl class="phone-comparison__info">';
    phoneLiHtml += '<dt>기본요금</dt>';
    phoneLiHtml += '<dd>월 ' + numberWithCommas(data.payMnthChargeAmt) + '원</dd>';
    phoneLiHtml += '</dl>';
    phoneLiHtml += '<dl class="phone-comparison__info">';
    phoneLiHtml += '<dt>할부원금</dt>';
    phoneLiHtml += '<dd>' + numberWithCommas(data.instAmt) + '원</dd>';
    phoneLiHtml += '</dl>';
    phoneLiHtml += '<dl class="phone-comparison__info">';
    phoneLiHtml += '<dt> 월 납부금액';
    phoneLiHtml += '<br>(월 단말요금 + 월 통신요금)</dt>';
    phoneLiHtml += '<dd> 단말 할인 시 ';
    phoneLiHtml += ' 월 ' + numberWithCommas(data.payMnthChargeAmt + data.payMnthAmt) + '원';
    phoneLiHtml += '</dd>';
    phoneLiHtml += '</dl>';
    $("#phone_li_" + idx).html(phoneLiHtml);
}

/**
 비교하기 상품 레이어 영역에 모든값을 초기화 처리시킨다.
 */
var allClearCompareItem = function() {

    for(var idx = 0 ; idx < 2 ; idx++){

        $("#phoneSelect_"+idx).val("");

        noPhoneHtml = '';
        noPhoneHtml += '<div class="c-box c-box--dotted u-mt--40">';
        noPhoneHtml += '<div class="c-box__inner">';
        noPhoneHtml += '<i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>';
        noPhoneHtml += '<p class="c-text c-text--type3 u-mt--16 u-co-gray">휴대폰을';
        noPhoneHtml += '<br>선택해 주세요.';
        noPhoneHtml += '</p>';
        noPhoneHtml += '</div>';
        noPhoneHtml += '</div>';
        $("#phone_img_div_"+idx).html(noPhoneHtml);
        noRateHtml = '';
        noRateHtml += '<div class="c-box c-box--dotted u-mt--24">';
        noRateHtml += '<div class="c-box__inner">';
        noRateHtml += '<i class="c-icon c-icon--compare-fee" aria-hidden="true"></i>';
        noRateHtml += '<p class="c-text c-text--type3 u-mt--16 u-co-gray">요금제를';
        noRateHtml += '<br>선택해 주세요.';
        noRateHtml += '</p>';
        noRateHtml += '</div>';
        noRateHtml += '</div>';

        $("#rateSelect_"+idx+" > option").remove();
        $("#rateSelect_"+idx).append('<option label="요금제 선택" disabled>요금제 선택</option>');
        $("#colorSelect_"+idx+" > option ").remove();
        $("#colorSelect_"+idx).append('<option value="">용량/색상</option>');
        $("#phone_dl_"+idx).html(noRateHtml);
        $("#phone_li_"+idx).html('');
        $("#phone_comp_li_"+idx).html('');
        $("#phone_button_"+idx).prop("classList","c-button c-button--lg c-button--full c-button--red c-button--h48 u-mt--32");
        $("#phone_button_"+idx).prop("disabled",false);

    }

};

/**
 비교하기 휴대폰영역을 초기화.
 */
var initSelectPhoneList = function() {

    try {

        var varData = ajaxCommon.getSerializedData({
        });
        $.ajax({
            type:"post",
            url : "/m/product/phone/phoneListAllAjax.do",
            data : varData,
            dataType : "json",
            error: function(jqXHR, textStatus, errorThrown){
                return;
            },
            complete: function(){

            },
            success:	function(data){
                var resultCode = data.RESULT_CODE;
                var totalCount = data.totalCount;
                $("#phoneSelect_0 > option").remove();
                $("#phoneSelect_1 > option").remove();
                $("#phoneSelect_0").append('<option value="">휴대폰 선택</option>');
                $("#phoneSelect_1").append('<option value="">휴대폰 선택</option>');
                if(totalCount > 0 && resultCode == "00000") {
                    var listData = data.phoneListLte;
                    $(listData).each(function() {
                        var otpionHtml = '<option value="'+this.prodId+'" data-reprate="'+this.repRate+'">'+this.prodNm+'</option>';
                        $("#phoneSelect_0").append(otpionHtml);
                        $("#phoneSelect_1").append(otpionHtml);
                    });
                }

            }

        });

    }
    catch(e){
        alert(e.message);
    }
    finally {
    }

};

/**
 비교하기 영역을 초기화 처리시킨다.
 */
var initCompareItem = function(idx) {

    noPhoneHtml = '';
    noPhoneHtml += '<div class="c-box c-box--dotted u-mt--40">';
    noPhoneHtml += '<div class="c-box__inner">';
    noPhoneHtml += '<i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>';
    noPhoneHtml += '<p class="c-text c-text--type3 u-mt--16 u-co-gray">휴대폰을';
    noPhoneHtml += '<br>선택해 주세요.';
    noPhoneHtml += '</p>';
    noPhoneHtml += '</div>';
    noPhoneHtml += '</div>';
    $("#phone_img_div_"+idx).html(noPhoneHtml);
    noRateHtml = '';
    noRateHtml += '<div class="c-box c-box--dotted u-mt--24">';
    noRateHtml += '<div class="c-box__inner">';
    noRateHtml += '<i class="c-icon c-icon--compare-fee" aria-hidden="true"></i>';
    noRateHtml += '<p class="c-text c-text--type3 u-mt--16 u-co-gray">요금제를';
    noRateHtml += '<br>선택해 주세요.';
    noRateHtml += '</p>';
    noRateHtml += '</div>';
    noRateHtml += '</div>';
    $("#colorSelect_"+idx+" > option ").remove();
    $("#colorSelect_"+idx).append('<option value="">용량/색상</option>');
    $("#phone_dl_"+idx).html(noRateHtml);
    $("#phone_li_"+idx).html('');
    $("#phone_comp_li_"+idx).html('');
    $("#phone_button_"+idx).prop("classList","c-button c-button--lg c-button--full c-button--red c-button--h48 u-mt--32 is-disabled");
    $("#rateSelect_"+idx).val("");
    $("#rateSelect_"+idx).prop("disabled",true);
    sessionProc('','','','','remove',idx);

};

/**
 비교팝업 호출
 */
var callPhoneCompareList = function(){

    MCP.openPopup("#phone-compare-dialog");

}

/**
 폭목록 조회
 */
var callPhoneList = function() {

    try {

        var varData = $("#phonelistfrm").serialize();

        $.ajax({
            type:"post",
            url : "/m/product/phone/phoneAgrListAjax.do",
            data : varData,
            dataType : "json",
            error: function(jqXHR, textStatus, errorThrown){
                return;
            },
            complete: function(){

            },
            success:	function(data){
                var htmlStr = "";
                var resultCode = data.RESULT_CODE;

                //var totalCount = data.totalCount;

                $('#product_list').empty();
                pageObj.phoneList = [];

                if(data.totalCount > 0 && resultCode == "00000") {
                    var listData = data.phoneListLte;

                    pageObj.phoneList = listData;
                }

                viewPhoneList();

                //initMore();

                //휴대폰비교하기 초기화
                sessionProc('','','','','',0);

            }

        });

    }
    catch(e){
        alert(e.message);
    }
    finally {
    }

};


    var viewPhoneList = function() {
        $('#product_list').empty();
        let totalCount = 0;
        let viewIndex = 0;
        $(pageObj.phoneList).each(function(index) {

            if (pageObj.chkProductList.includes(this.makrCdEtc)) {
                let htmlStr = "";
                htmlStr += '<li>';
                htmlStr += '		<div class="c-card__box">';
                htmlStr += '			<div class="c-flex">';
                htmlStr += '				<div class="labels">';
                var stckTypeTop = emptyToTyphoon(this.stckTypeTop);
                if(stckTypeTop.indexOf('01') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type1">사은품</span>'; }
                if(stckTypeTop.indexOf('02') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type2">NEW</span>';	}
                if(stckTypeTop.indexOf('12') > -1){	htmlStr += '<span class="c-sticker c-sticker--type12">신규출시</span>';	}
                if(stckTypeTop.indexOf('03') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type3">최고인기</span>';	}
                if(stckTypeTop.indexOf('04') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type4">인기</span>';	}
                if(stckTypeTop.indexOf('05') > -1){	htmlStr += '	<span class="c-sticker c-sticker--type5">일시품절</span>';	}
                if(stckTypeTop.indexOf('06') > -1){	htmlStr += '<span class="c-sticker c-sticker--type6">품절임박</span>';	}
                if(stckTypeTop.indexOf('07') > -1){	htmlStr += '<span class="c-sticker c-sticker--type7">공시확대</span>';		}
                if(stckTypeTop.indexOf('08') > -1){	htmlStr += '<span class="c-sticker c-sticker--type8">출고가인하</span>';		}
                if(stckTypeTop.indexOf('09') > -1){	htmlStr += '<span class="c-sticker c-sticker--type9">단독판매</span>';	}
                if(stckTypeTop.indexOf('10') > -1){	htmlStr += '<span class="c-sticker c-sticker--type10">리퍼폰</span>';	}
                if(stckTypeTop.indexOf('11') > -1){	htmlStr += '<span class="c-sticker c-sticker--type11">전시폰</span>';	}
                if(stckTypeTop.indexOf('13') > -1){	htmlStr += '<span class="c-sticker c-sticker--type13">한정판매</span>';	}

                htmlStr += '				</div>';

                htmlStr += '				<span class="c-select-badge">';
                var recommendRateArr = '';
                var recommendRate = '';
                if(this.sesplsYn != 'Y'){
                    recommendRate = 'KSLTEMSTD#KD'; //추천요금제 없으면 일단..LTE표준으로 임시처리
                    if(this.recommendRate != null){
                        recommendRateArr = this.recommendRate.split(',');
                        recommendRate = recommendRateArr[0];
                    }
                }
                htmlStr += '					<input class="c-checkbox c-checkbox--badge" data-value="'+this.prodCtgId+'"  data-prodId="'+this.prodId+'" data-rateCd="'+this.rateCd+'" data-recrate="'+recommendRate+'" id="chk0'+index+'" type="checkbox">';
                htmlStr += '						<label class="c-label" for="chk0'+index+'"></label>';
                htmlStr += '				</span>';
                htmlStr += '			</div>';

                htmlStr += '			<a href="javascript:saveScroll();openPage(\'spaLink\', \'/m/product/phone/phoneView.do?prodId='+this.prodId+'&rateCd='+this.rateCd+'\', \'\');">';
                htmlStr += '				<div class="phone__img">';
                htmlStr += '					<img src="'+this.imgPath+'" alt="">';
                if (this.layerYn == 'Y') {
                    htmlStr += '                    <img src="'+ this.layerImageUrl +'" alt="imgLayout">';
                }
                htmlStr += '				</div>';

                htmlStr += '				<div class="phone__info-2">';


                if(this.sesplsYn == 'Y'){
                    htmlStr += '  				<span class="c-label--Network">'+emptyToTyphoon(this.prodCtgIdLabel)+'</span>';
                    htmlStr += '					<h4 class="phone__txt phone__txt--title">'+this.prodNm+'</h4>';
                    htmlStr += '					<div class="phone__txt phone__txt--month-price2">'+numberWithCommas(this.inUnitPric + this.inUnitPric * 0.1)+'원</div>';
                    htmlStr += '					<div class="phone__txt phone__txt--discount-price">';
                    htmlStr += '						<b>'+numberWithCommas(this.outUnitPric + this.outUnitPric * 0.1)+'</b>원';
                    htmlStr += ' 				</div>';
                    htmlStr += '					<div class="phone__txt phone__txt--total-price">(월 통신요금 '+numberWithCommas(this.payMnthAmt)+'원 부터)</div>';
                }else{
                    htmlStr += '  				<span class="c-label--Network">'+emptyToTyphoon(this.prodCtgIdLabel)+'</span>';
                    htmlStr += '					<h4 class="phone__txt phone__txt--title-2">'+this.prodNm+'</h4>';
                    htmlStr += '					<p class="phone-list__infotxt">' + this.repRateNm +'</p>';
                    htmlStr += '					<div class="phone-list__priceinfo">';
                    htmlStr += '	                	<p>월 단말요금 <b>'+ numberWithCommas(this.payMnthAmt) +'</b> 원</p>';
                    htmlStr += '	                    <p>월 통신요금 <b>'+ numberWithCommas(this.baseAmt) + '</b> 원</p>';
                    htmlStr += '	                </div>';
                    htmlStr += '					<div class="phone-list__price"><span> 월<b>'+ numberWithCommas(this.payMnthAmt + this.baseAmt)+'</b>원</span></div>';
                }

                htmlStr += '						<div class="phone__colors">';
                var rgbData = '#d3beac';
                var arrColor = new Array;
                if(this.colorCodes != null){
                    var colorCodes = this.colorCodes.split(',');
                    // 중복제거
                    $.each(colorCodes,function(idx,val){
                        var atribValCd1 = colorCodes[idx];
                        if(arrColor.indexOf(atribValCd1) == -1){
                            arrColor.push(atribValCd1);
                        }
                    });
                    colorCodes = arrColor;
                    // 중복제거 끝
                    $(colorCodes).each(function() {

                        var atribValCd1 = this;
                        $(colorData).each(function() {
                            if(this.dtlCd == atribValCd1.trim()){
                                rgbData = this.expnsnStrVal1;
                            }
                        });
                        htmlStr += '						<span class="phone__dot" style="background-color: '+rgbData+'"></span>';
                    });
                }
                htmlStr += '							</div>';
                htmlStr += '					</div>';
                htmlStr += '				</a>';
                htmlStr += '			</div>';
                htmlStr += '		</li>';

                //요금제 가격정보가 존재 해야 한다.
                let chargeList = this.mspSaleSubsdMstListForLowPrice ;  //mspSaleSubsdMstListForLowPrice
                if (chargeList != null && chargeList.length > 0 ) {
                    $('#product_list').append(htmlStr);
                    totalCount++;
                    viewIndex++;
                }
            }
        });

        if ($('#product_list').html() == "") {
            $('#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__sort').hide();
        } else {
            $('#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__sort').show();
        }

        $("#product_list > li > div > a > div.phone__img > img").each(function(){
            $(this).error(function(){
                $(this).unbind("error");
                $(this).attr("src","/resources/images/mobile/content/img_240_no_phone.png");
            });
        });
    }
/**
 * 요금제 더보기 초기화
 */
var initMore = function(){
    var mCount = $("#totalCount").text();
    if(mCount <= standardCount){
        $("#currentViewCount").text(mCount);
    }else{
        $("#currentViewCount").text(standardCount);
    }
    var cCount = $("#currentViewCount").text();
    var mCount = $("#totalCount").text();
    if(cCount == mCount){
        $("#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__more").hide();
    }

};

/**
 * 요금제 더보기 처리기능
 */
var viewMore = function() {

    var cCount = $("#currentViewCount").text();
    var mCount = $("#totalCount").text();

    if (cCount == mCount) {
        MCP.alert('마지막 페이지입니다.');
        return;
    }
    //숨김 처리 되있는 요금제 리스트만 가져온다.

    var $li =$("#product_list").find("li[style*=none]");
    $.each($li,function(idx) {	//10개까지만 보이게 처리
        if (idx == standardCount) {
            return false;
        }
        $(this).css("display","");
    });
    setCurrentCount();	//더보기 버튼의 숫자 카운트 변경
};

/**
 * 더보기 버튼의 숫자 변경처리
 */
var setCurrentCount = function() {
    var currentViewCount = $("#product_list").find("li:visible").length
    var totalCount = $("#product_list").find("li").length;
    $("#currentViewCount").text(currentViewCount);
    $("#totalCount").text(totalCount);
    if(currentViewCount == totalCount){
        $("#main-content > div.phone > div.phone__item-wrap.c-expand > div.phone__more").hide();
    }

};

/**
 체크된 상품이 1개일경우 해당 상품의 해당하는
 정책의 요금제 정보 SELECT option값들을 생성한다.
 */
var makeRateListAjax = function() {

    var varData = ajaxCommon.getSerializedData({

    });
    ajaxCommon.getItem(
        {
            id : 'phoneListCompareAjax',
            cache : false,
            url : '/m/product/phone/phoneRateListAjax.do',
            data : varData,
            dataType : "json"
        },

        function(jsonObj) {
            $("#rateSelect_0 > option").remove();
            $("#rateSelect_1 > option").remove();
            $("#rateSelect_0").append('<option value="">선 택</option>');
            $("#rateSelect_1").append('<option value="">선 택</option>');

            $.each(jsonObj.mspRateMstList,function(idx,data) {
                var optionHtml = '<option value="'+data.rateCd+'">'+data.rateNm+'</option>';
                $("#rateSelect_0").append(optionHtml);
                $("#rateSelect_1").append(optionHtml);
            });
        }
    );

};

//휴대폰 비교 체크 여부에 따라 세션에 정보수정
var compareItem = function(data) {

    if (data.is(':checked') && compareCnt >= 2) {
        MCP.alert('휴대폰 비교는 2개까지만 가능합니다.');
        data.prop("checked",false);
        return;
    }
    if(data.is(':checked')){
        sessionProc(data.data("prodid"),data.data("ratecd"),'','','add',compareCnt);
    }else{
        sessionProc(data.data("prodid"),'','','','remove',compareCnt);
    }



};

//휴대폰 비교정보 세션에 정보수정
function sessionProc(prodId,rateCd,hndsetModelId,sesplsYn,proc,idx) {

    try {

        var varData = ajaxCommon.getSerializedData({
            prodId : prodId
            ,rateCd : rateCd
            ,hndsetModelId : hndsetModelId
            ,sesplsYn : sesplsYn
            ,process : proc
            ,idx : idx
        });

        $.ajax({
            type:"post",
            url : "/m/product/phone/phoneCompareProcAjax.do",
            data : varData,
            async : false,
            dataType : "json",
            error: function(jqXHR, textStatus, errorThrown){
                return;
            },
            complete: function(){

            },
            success:	function(jsonObj){
                compareCnt = jsonObj.phoneCompareSaveCnt;
                var compareCntHtml = '';
                compareCntHtml += '<span class="c-hidden">'+jsonObj.phoneCompareSaveCnt+'</span>';
                compareCntHtml += '<i class="c-icon c-icon--num'+jsonObj.phoneCompareSaveCnt+'" aria-hidden="true"></i>';
                $("#main-content > div.c-side-badge > span ").html(compareCntHtml);
                if(jsonObj.phoneComapreData != null){
                    $.each(jsonObj.phoneComapreData,function(idx,data) {
                        if(data != null && data.prodId != ''){
                            $("#product_list > li > div > div > span > input[type='checkbox']").each(function(){
                                if(data.prodId == $(this).data("prodid")){
                                    $(this).prop("checked",true);
                                }
                            });
                        }
                        if(proc == 'search'){
                            callCompareAjaxProd(data.prodId,idx,data.rateCd,"");
                        }
                    });
                }
            }
        });

    }
    catch(e){
        alert(e.message);
    }
    finally {
    }
}

function emptyToTyphoon(value){

    if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
        return '-';
    } else{
        return value;
    }

}

function fn_go_banner(linkUrl,bannSeq, bannCtg ,tgt){
    insertBannAccess(bannSeq,bannCtg);
    if(tgt.trim() == '_blank'){
        window.open(linkUrl);
    }else{
        window.location.href  = linkUrl;
    }

}

var initColorData = function(){
    var varData = ajaxCommon.getSerializedData({
        grpCd : 'GD0008'
    });

    ajaxCommon.getItemNoLoading({
        id:'getComCodeColroListAjax'
        ,cache:false
        ,url:'/m/getComCodeListAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    },function(data){
        colorData = data.result;

    });

}