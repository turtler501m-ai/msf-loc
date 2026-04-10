    var pageInfo = {
        initFlag:true
        ,dataTypeVal:""
        ,prdtIdVal:""
        ,operTypeVal:""
        ,agrmTrmVal:""
        ,chargelist:[]
        ,usimPriceBase:""
        ,usimPrice5G:""
        ,tempInnerHtml:""
        ,pageNo: 1
        ,pageSize: 5
        ,cprtPageNo: 1
        ,cprtPageSize: 2
        , bestRateArr:[]
        , tabButIndex:"0"
        , orderEnum:"RECOMM_ROW"
        , badgeList:[]
        , selRateCtg:"X"
        , initBestRate:"0"
        , isGetQuestion:false
        , ktTripleDcAmt:0
        , ktTripleDcRateCd:""
        , settlYn:"N"
        , instAmt :0
        , agrmTrmList:["12","24","30","36"] //단말기 할부 가능 한것들...
        , asAgrmTrmVal:""
    }
    var dummyRateInfo;
    var setRateType = '';
    var defaultRateYn = 'N';
    var initRateCdChoice = 'N';
    var sliderInitYn = 'N';
    var compareCnt = 0;
    var confirmChk = 'N';
    var compareCheck = function (obj, id){

        if($(obj).prop('checked')){
            if(compareCnt > 1){
                MCP.alert('비교함에는 최대 2개의 요금제만 담을 수 있습니다.', function (){
                    $(obj).removeProp('checked');
                });
                return;
            }else{
                addCompareCard(obj, id);
                compareCnt++;
            }
        }else{
            removeCompareCard(id);
        }

        if(compareCnt != 2){
            $('#noCompareLI').show();
        }else{
            $('#noCompareLI').hide();
        }
        $('.in-box').empty();
        $('.in-box').append('<span class="c-hidden">' + compareCnt + '</span><i class="c-icon c-icon--num' + compareCnt + '" aria-hidden="true"></i>');

    }

    var removeCompareCard = function(id){
        compareCnt--;
        $('.in-box').empty();
        $('.in-box').append('<span class="c-hidden">' + compareCnt + '</span><i class="c-icon c-icon--num' + compareCnt + '" aria-hidden="true"></i>');
        $('#compareLI' + id).remove();
        $('#noCompareLI').show();
    }

    var aniIns = [];
    function lottieAnimation() {
        var animations = document.querySelectorAll('.survey__actor-ani');
        [].forEach.call(animations, function(content) {
            var url = content.getAttribute('data-ani-url');
            var ani = lottie.loadAnimation({
                container: content,
                renderer: 'svg',
                loop: true,
                autoplay: true,
                path: url,
            });

            ani.pause();
            aniIns.push(ani);
            content.style.display = 'none';
        });
    };

    // 설문 애니메이션 play(해당 설문지에 해당하는 애니메이션만 show)
    function playAnimation(index) {
        for (var i = 0; i < aniIns.length; i++) {
            if (i === index) {
                aniIns[index].wrapper.style.display = 'block';
                setTimeout(function() {
                    aniIns[index].play();
                }, 0);
            } else {
                aniIns[i].stop();
                aniIns[i].wrapper.style.display = 'none';
            }
        }
    }

var addCompareCard = function (obj, id){
    var isRateArr = false ;
    //추천요금제 적용
    var bestRateArr = '';
    if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
        var best = '';
        //bestRateArr = $("[name=prdtId]:checked").attr('bestRate').split(',');
        $('input[name=prdtId]').each(function (){
            if(best == ''){
                best = $(this).attr('bestRate');
            }else{
                best = best + ',' + $(this).attr('bestRate');
            }
            bestRateArr = best.split(',');
        });
    }else{
        bestRateArr = $("#recommendRateforChk").text().split(',');

    }

    var rateCdSp =$(obj).val() + "#"+ $(obj).attr('sprtTp');

    if(bestRateArr && bestRateArr.length > 0){
        for(var j=0; j<bestRateArr.length; j++){
            if(bestRateArr[j] == rateCdSp) {
                isRateArr = true ;
                break;
            }
        }
    }

    var compareHtml = '';
    compareHtml += '<li class="c-card c-card--type2 compareLI" id="compareLI' + id + '">';
    compareHtml += '    <div class="c-card__box">';
    if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
        compareHtml += '<input class="c-checkbox c-checkbox--box" id="chkBadgeB' + id + '" type="radio" name="chkPayment" rateNm ="'+$(obj).attr('rateNm')
            +'" rateAdsvcNm="' + $(obj).attr('rateAdsvcNm')
            +'" baseAmt="'+$(obj).attr('baseAmt')
            +'" dcAmt="'+$(obj).attr('dcAmt')
            +'" addDcAmt="'+$(obj).attr('addDcAmt')
            +'" promotionDcAmt="'+$(obj).attr('promotionDcAmt')
            +'" promotionAmtVatDesc="'+$(obj).attr('promotionAmtVatDesc')
            +'" mmBasAmtVatDesc="'+$(obj).attr('mmBasAmtVatDesc')
            +'" payMnthChargeAmt="' + $(obj).attr('payMnthChargeAmt')
            +'" dataCnt="' + $(obj).attr('dataCnt')
            +'" callCnt="' + $(obj).attr('callCnt')
            +'" smsCnt="' + $(obj).attr('smsCnt')
            +'" freeDataCnt="' + $(obj).attr('freeDataCnt')
            +'" freeCallCnt="' + $(obj).attr('freeCallCnt')
            +'" freeSmsCnt="' + $(obj).attr('freeSmsCnt')
            +'" payMnthAmt="' + $(obj).attr('payMnthAmt')
            +'" hndstAmt="' + $(obj).attr('hndstAmt')
            +'" subsdAmt="' + $(obj).attr('subsdAmt')
            +'" agncySubsdAmt="' + $(obj).attr('agncySubsdAmt')
            +'" finstAmt="' + $(obj).attr('finstAmt')
            +'" totalFinstCmsn="' + $(obj).attr('totalFinstCmsn')
            +'" hndstPayAmt="' + $(obj).attr('hndstPayAmt')
            +'" finalAmt="' + $(obj).attr('finalAmt')
            +'" value="' + $(obj).val() + '" onclick="$(\'#compareBtn\').removeProp(\'disabled\');"> ';
    }else{
        compareHtml += '        <input class="c-checkbox c-checkbox--box" id="chkBadgeB' + id + '" type="radio" name="chkPayment" rateNm ="'+$(obj).attr('rateNm')
            +'" rateAdsvcNm="' + $(obj).attr('rateAdsvcNm')
            + '" baseAmt="'+$(obj).attr('baseAmt')
            +'" dcAmt="'+$(obj).attr('dcAmt')
            +'" addDcAmt="'+$(obj).attr('addDcAmt')
            +'" promotionDcAmt="'+$(obj).attr('promotionDcAmt')
            +'" promotionAmtVatDesc="'+$(obj).attr('promotionAmtVatDesc')
            +'" mmBasAmtVatDesc="'+$(obj).attr('mmBasAmtVatDesc')
            +'" payMnthChargeAmt="' + $(obj).attr('payMnthChargeAmt')
            +'" dataCnt="' + $(obj).attr('dataCnt')
            +'" callCnt="' + $(obj).attr('callCnt')
            +'" smsCnt="' + $(obj).attr('smsCnt')
            +'" freeDataCnt="' + $(obj).attr('freeDataCnt')
            +'" freeCallCnt="' + $(obj).attr('freeCallCnt')
            +'" freeSmsCnt="' + $(obj).attr('freeSmsCnt')
            +'" finalAmt="' + $(obj).attr('finalAmt')
            +'" value="' + $(obj).val() + '" onclick="$(\'#compareBtn\').removeProp(\'disabled\');"> ';

    }
    compareHtml += '        <label class="c-card__box-label" for="chkBadgeB' + id + '"></label>';
    compareHtml += '        <div class="c-card__top">';
    //추천요금제 적용
    if (isRateArr) {
        compareHtml += '        <span class="c-text-label c-text-label--mint-type1">추천</span>';
    }else{
        compareHtml += '        <span class="c-text-label"></span>';
    }
    compareHtml += '            <div class="c-card__badge" onclick="removeCompareCard(\'' + id + '\')">';
    compareHtml += '                <button class="c-button c-button-sm">';
    compareHtml += '                    <i class="c-icon c-icon--trash" aria-hidden="true"></i>';
    compareHtml += '                </button>';
    compareHtml += '            </div>';
    //compareHtml += '            <a class="c-button c-button--sm" href="javascript:void(0);" onclick="openPage(\'pullPopup\', \'/m/rate/rateLayer.do?rateAdsvcGdncSeq='+ $(obj).attr('rateAdsvcGdncSeq') +'&rateAdsvcCd=' + $(obj).attr('rateCd') + '&btnDisplayYn=N\', \'\');"> ';
    compareHtml += '              <a class="c-button c-button--sm _rateDetailPop" href="javascript:void(0);" rateAdsvcGdncSeq="'+ $(obj).attr('rateAdsvcGdncSeq') +'"  rateAdsvcCd="' + $(obj).attr('rateCd') +'"  > ';
    compareHtml += '                <i class="c-icon c-icon--arrow-gray" aria-hidden="true"></i>';
    compareHtml += '            </a>';
    compareHtml += '        </div>';
    compareHtml += '        <div class="c-card__title">';
    if($(obj).attr('rateAdsvcNm') != undefined && $(obj).attr('rateAdsvcNm') != ''){
        compareHtml += '        <b>' + $(obj).attr('rateAdsvcNm') + '</b>';
    }else{
        compareHtml += '        <b>' + $(obj).attr('rateNm') + '</b>';
    }
    compareHtml += '            <p class="c-card__sub">';
    if($(obj).attr('dataCnt') != undefined && $(obj).attr('dataCnt') != ''){
        compareHtml += '                <span>데이터 ' + $(obj).attr('dataCnt');
    }else{
        compareHtml += '                <span>데이터 ' + $(obj).attr('freeDataCnt');
    }
    if($(obj).attr('rateAdsvcPromData') != undefined && $(obj).attr('rateAdsvcPromData') != ''){
        compareHtml += '                (' + $(obj).attr('rateAdsvcPromData') + ')';
    }
    compareHtml += '                </span>';

    if($(obj).attr('callCnt') != undefined && $(obj).attr('callCnt') != ''){
        compareHtml += '                <span>음성 ' + $(obj).attr('callCnt');
    }else{
        compareHtml += '                <span>음성 ' + $(obj).attr('freeCallCnt');
    }

    if($(obj).attr('rateAdsvcPromCall') != undefined && $(obj).attr('rateAdsvcPromCall') != ''){
        compareHtml += '                (' + $(obj).attr('rateAdsvcPromCall') + ')';
    }
    compareHtml += '				</span>';

    if($(obj).attr('smsCnt') != undefined && $(obj).attr('smsCnt') != ''){
        compareHtml += '                <span>문자 ' + $(obj).attr('smsCnt');
    }else{
        compareHtml += '                <span>문자 ' + $(obj).attr('freeSmsCnt');
    }

    if($(obj).attr('rateAdsvcPromSms') != undefined && $(obj).attr('rateAdsvcPromSms') != ''){
        compareHtml += '                (' + $(obj).attr('rateAdsvcPromSms') + ')';
    }
    compareHtml += '				</span>';
    compareHtml += '            </p>';
    compareHtml += '        </div>';


    if(($(obj).attr('rateAdsvcBnfit') != undefined && $(obj).attr('rateAdsvcBnfit') != '') || ($(obj).attr('rateAdsvcAllianceBnfit') != undefined && $(obj).attr('rateAdsvcAllianceBnfit') != '')){
        compareHtml += '        <div class="c-card__detail-info">';
        compareHtml += '            <div class="c-hr c-hr--type3"></div>';
        compareHtml += '            <ul class="c-text-list c-bullet c-bullet--hyphen">';
        if($(obj).attr('rateAdsvcBnfit') != undefined && $(obj).attr('rateAdsvcBnfit') != ''){
            compareHtml += '            <li class="c-text-list__item u-co-gray">' + $(obj).attr('rateAdsvcBnfit') + '</li>';
        }

        if($(obj).attr('rateAdsvcAllianceBnfit') != undefined && $(obj).attr('rateAdsvcAllianceBnfit') != ''){
            compareHtml += '            <li class="c-text-list__item u-co-gray">' + $(obj).attr('rateAdsvcAllianceBnfit') + '</li>';
        }
        compareHtml += '            </ul>';
        compareHtml += '        </div>';
    }


    if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
        var dummyPrice = Number($(obj).attr('payMnthChargeAmt'));
        if($("#agrmTrm").val() != '0'){
            dummyPrice = Number($(obj).attr('payMnthChargeAmt')) + Number($(obj).attr('payMnthAmt'));
        }

        compareHtml +='<div class="c-card__price-box--type2">';
        compareHtml +='    <p class="c-card__price-info">';
        compareHtml +='        월 단말요금 <b>' + numberWithCommas($(obj).attr('payMnthAmt')) + '</b> 원<em>+</em>월 통신요금 <b>' + numberWithCommas($(obj).attr('payMnthChargeAmt')) + '</b> 원';
        compareHtml +='    </p>';
        compareHtml +='    <span class="u-mr--11">';
        compareHtml +='        월 납부금액(VAT 포함)';
        compareHtml +='    </span>';
        compareHtml +='    <span>';
        compareHtml +='        <b>' + numberWithCommas(dummyPrice) + '</b> 원';
        compareHtml +='    </span>';
        compareHtml +='</div>';

    } else {
        compareHtml += '        <div class="c-card__price-box u-co-mint">';
        compareHtml += '            <span class="c-text c-text--type4">월 기본료(VAT 포함)</span> ';
        if($(obj).attr('mmBasAmtVatDesc') != undefined && $(obj).attr('mmBasAmtVatDesc') != ''){
            compareHtml += '        <span class="c-text u-td-line-through u-ml--auto u-co-gray">'+numberWithCommas($(obj).attr('mmBasAmtVatDesc'))+'</span>';
        }

        if($(obj).attr('promotionAmtVatDesc') != undefined && $(obj).attr('promotionAmtVatDesc') != ''){
            compareHtml += '        <span> <b>'+numberWithCommas($(obj).attr('promotionAmtVatDesc'))+'</b> 원</span>';
        } else {
            if($(obj).attr('mmBasAmtVatDesc') != undefined && $(obj).attr('mmBasAmtVatDesc') != ''){
                compareHtml += '            <span><b>'+numberWithCommas($(obj).attr('mmBasAmtVatDesc'))+'</b>원</span>';
            } else {
                compareHtml += '            <span><b>'+numberWithCommas($(obj).attr('finalAmt'))+'</b>원</span>';
            }
        }
        compareHtml += '        </div>';

    }


    compareHtml += '    </div>';
    compareHtml += '</li>';

    $('#noCompareLI').before(compareHtml);
    //MCP.addAccordion2('.compareAccordion');
    //MCP.addAccordion2('#compareAccordion' + id);


}


var selfTimeChk = function (){
    if($('input[name=radOpenType]:checked').attr('id') == 'radOpenType1'){
        var simpleApplyObj;
        ajaxCommon.getItemNoLoading({
            id:'isSimpleApply'
            ,cache:false
            ,url:'/appForm/isSimpleApplyAjax.do'
            ,data: ""
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            simpleApplyObj = jsonObj ;
        });
        var operType = $('input[name=operType]:checked').val();

        if(!simpleApplyObj.IsMnpTime){
            $('input[name=operType][operName="번호이동"]').attr('disabled','disabled');
        }

        if(operType == undefined || "MNP3" == operType){
            if(!simpleApplyObj.IsMnpTime){
                MCP.alert("셀프 개통 가능한 시간은 신규(08:00~21:50),</br>번호이동(10:00~19:50) 입니다.", function (){
                    confirmChk = 'Y';
                    $('input[name=operType][operName="신규가입"]').trigger('click');
                    //$('#operType1').attr('disabled', 'disabled');
                    //$('#operType2').trigger('click');
                });
                return false;
            }else{
                return true;
            }
        } else {

            if (("MNP3" == operType && !simpleApplyObj.IsMnpTime) || ("NAC3" == operType && !simpleApplyObj.IsMacTime )) {
                MCP.alert("셀프 개통 가능한 시간은 신규(08:00~21:50),</br>번호이동(10:00~19:50) 입니다.", function (){
                    confirmChk = 'Y';
                    $('input[name=operType][operName="신규가입"]').trigger('click');
                    //$('#operType1').attr('disabled', 'disabled');
                    //$('#operType2').trigger('click');
                    //$('#movTxt').hide();
                    //$('#newTxt').show();
                });
                return false;
            } else {
                return true;
            }
        }
    } else {
        return true;
    }
}


// 신규(유심)가입 제한 IP 확인
var selfIpChk = function(){

    var operType = $(':radio[name="operType"]:checked').val();
    var usimKindsCd = $('#prdtIndCd').val();

    if(operType != 'NAC3') return true;
    if(usimKindsCd == '10' || usimKindsCd == '11') return true;

    var ipChkResult = false;

    ajaxCommon.getItemNoLoading({
        id:'selfIpChk'
        ,cache:false
        ,url:'/appForm/selfIpChk.do'
        ,data: ""
        ,dataType:"json"
        ,async:false
    },function(jsonObj){
        ipChkResult = (jsonObj.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS);
    });

    if(!ipChkResult){
        // 상담사 개통으로 진행
        MCP.alert("상담사 개통으로만 신청 가능합니다.<br>상담사 개통 신청으로 진행합니다.", function(){
            $('#radOpenType2').focus();
            $('#radOpenType2').trigger('click');
            $('#cstmrType1').trigger('click');
        });
    }

    return ipChkResult;
}

var fnSelfClick = function (){

    var cstmrType = $('[name=cstmrType]:checked').val();
    if($('input[name=cstmrType]:checked').val() == undefined)  {
        MCP.alert("고객 유형을 선택 하시기 바랍니다.", function (){
            $('input[name=cstmrType]').eq(0).focus();
        });
        return ;
    }

    var usimKindsCd = $('#prdtIndCd').val();
    if(usimKindsCd == '10' ) {
        var uploadPhoneSrlNo = Number(ajaxCommon.isNullNvl(pageObj.uploadPhoneSrlNo,0));
        if( uploadPhoneSrlNo < 1 ){
            MCP.alert(`이미지를 등록하신 후 EID / IMEI / IMEI2<br/> 값을 확인해 주세요.`, function (){
                $('#image').focus();
            });
            return ;
        }
    };

    var dataType = $('[name=dataType]:checked').val();
    if ("LTE" != dataType && "5G" != dataType) {
        MCP.alert("유심유형을 LTE , 5G 중에 선택 하시기 바랍니다.", function (){
            $('input[name=dataType]').eq(0).focus();
        });
        return ;
    }

    var operType = $(':radio[name="operType"]:checked').val()
    if ("MNP3" != operType && "NAC3" != operType) {
        MCP.alert("가입유형을 번호이동 , 신규 중에 선택 하시기 바랍니다. ", function (){
            $('input[name=operType]').eq(0).focus();
        });
        return ;
    }

    var agrmTrm = $(':radio[name=agrmTrm]:checked').val()
    if ("0" != agrmTrm) {
        MCP.alert("약정기간을 무약정으로 선택 하시기 바랍니다. ");
        return ;
    }

    if ($('#socCode').val() == '' || $('#socCode').val() == undefined || !$('#radPayTypeB').is(':visible')){
        MCP.alert("요금제를 선택 하시기 바랍니다.", function (){
            $('.paymentPop').focus();
        });
        return ;
    }

    if($.trim($("#evntCdPrmt").val()) != "" && $("#isEvntCdPrmtAuth").val() != "1"){
        MCP.alert("입력하신 이벤트 코드의 사용가능 여부를 확인 바랍니다.", function(){
            $("#evntCdPrmt").focus();
        });
        return;
    }

    // 셀프개통 시간 체크 + 셀프개통 신규(유심) IP 체크
    if(selfTimeChk() && selfIpChk()){
        $("onOffType").val("7");

        var varData = ajaxCommon.getSerializedData({grpCd: "mSimpleAuth"});
        ajaxCommon.getItemNoLoading({
                id: 'getAuthCodeListAjax'
                ,cache: false
                ,url: '/getCodeListAjax.do'
                ,data: varData
                ,dataType: "json"
                ,async: false
            }
            ,function(objList){
                var authList= [];
                if(objList != null){
                    for(var i=0; i<objList.length; i++){
                        authList.push(objList[i].dtlCdNm);
                    }
                    $("#authListDesc").html(authList.join(", "));
                }else{
                    $("#authListDesc").html("선택 가능한 본인인증 방법이 없습니다");
                }

                $('#chkModalPop').trigger('click');
            });
        /*
        ajaxCommon.createForm({
            method:"post"
            ,action:"/m/appForm/appSimpleForm.do"
         });

        ajaxCommon.attachHiddenElement("preUrlType","1");
        ajaxCommon.attachHiddenElement("operType",operType);
        ajaxCommon.attachHiddenElement("rateCd",$('#socCode').val());
        ajaxCommon.attachHiddenElement("prdtSctnCd",dataType);
        ajaxCommon.attachHiddenElement("indcOdrg",$("#rdoBestRate").val());

        ajaxCommon.formSubmit();
        */
    }
}

//서식지로 이동
var goAppFrom = function() {
    $("#onOffType").val("3");

    if($('input[name=cstmrType]:checked').val() == undefined){
        MCP.alert('고객 유형을 선택해 주세요.', function (){
            $('input[name=cstmrType]').eq(0).focus();
        });
        return false;
    }

    if($('input[name=radOpenType]:checked').val() == undefined){
        MCP.alert('개통 유형을 선택해 주세요.', function (){
            $('input[name=radOpenType]').eq(0).focus();
        });
        return false;
    }

    if($('input[name=operType]:checked').val() == undefined){
        MCP.alert('가입 유형을 선택해 주세요.', function (){
            $('input[name=operType]').eq(0).focus();
        });
        return false;
    }

    if(($('#prodId').val() == '' || $('#prodCtgType').val() == '03') && $('input[name=dataType]:checked').val() == undefined){
        MCP.alert('서비스 유형을 선택해 주세요.', function (){
            $('input[name=dataType]').eq(0).focus();
        });
        return false;
    }

    if ($('#socCode').val() == '' || $('#socCode').val() == undefined || !$('#radPayTypeB').is(':visible')){
        MCP.alert("요금제를 선택 하시기 바랍니다.");
        return ;
    }

    if($.trim($("#evntCdPrmt").val()) != "" && $("#isEvntCdPrmtAuth").val() != "1"){
        MCP.alert("입력하신 이벤트 코드의 사용가능 여부를 확인 바랍니다.", function(){
            $("#evntCdPrmt").focus();
        });
        return;
    }

    if($('input[name=operType]:checked').val() == 'NAC3' && pageInfo.settlYn =="Y"  ){
        //즉시결제... 체크 하기 않음
    } else {
        if($('input[name=instNom]:checked').val() != '0' && $('input[name=instNom]:checked').val() != $('#agrmTrm').val() && $('#agrmTrm').val() != '0'){
            MCP.alert('약정기간과 할부기간이 일치하지 않습니다.');
            return ;
        }
    }


    $("#authListDesc").html("신용카드, 네이버 인증서, 카카오 본인인증, PASS 인증서, toss 인증서");
    $('#chkModalPop').trigger('click');

    /*
    ajaxCommon.createForm({
        method:"post"
        ,action:"/m/appForm/appFormNe.do"
     });

    //ajaxCommon.attachHiddenElement("cstmrType",cstmrType);  TOBE - 고객 타입 안받음
    ajaxCommon.attachHiddenElement("prdtSctnCd",$("input:radio[name=dataType]:checked").val());
    ajaxCommon.attachHiddenElement("operType",$("input[name='operType']:checked").val());
    ajaxCommon.attachHiddenElement("reqBuyType","UU");
    ajaxCommon.attachHiddenElement("prdtId",$("input:radio[name=prdtId]:checked").val());
    ajaxCommon.attachHiddenElement("modelMonthly",$("input[name='agrmTrm']:checked").val());
    ajaxCommon.attachHiddenElement("serviceType","PO");	//PP: 선불 PO : 후불
    ajaxCommon.attachHiddenElement("socCode",$('#socCode').val());

    ajaxCommon.formSubmit();
    */

}

var confirmNextStep = function (type){

    //임시저장 처리
    var varData = {};
    var onOffType = $('input[name=radOpenType]:checked').val();
    var sesplsYn = 'N';
    var sesplsProdId = '';
    var cardDcCd = '';
    var prodId = '';
    var cntpntShopId = $('#initOrgnId').val();
    var cstmrType = $('input:radio[name=cstmrType]:checked').val();

    if($('#prodCtgType').val() == '03'){
        onOffType = '3';
        sesplsYn = 'Y';
        cardDcCd = $('#cardDcCd').val();
        sesplsProdId = $('#hndsetModelId').val();
        prodId = $('#prodId').val();
    }

    if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
        var uploadPhoneSrlNo = $('#uploadPhoneSrlNo').val()== '' ? '0': $('#uploadPhoneSrlNo').val();
        var usimKindsCd = $('#prdtIndCd').val() ;

        if (usimKindsCd == '10' || usimKindsCd == '11') {
            usimKindsCd = '09';
        }

        varData = ajaxCommon.getSerializedData({
            preUrlType : '1'
            , operType : $('input[name=operType]:checked').val()		//가입유형(번이,신규)
            , cntpntShopId : cntpntShopId                               //접점코드
            , rateCd : $('#socCode').val()								//요금제코드
            , prdtSctnCd : $('input[name=dataType]:checked').val()		//서비스유형(LTE,3G,5G)
            , indcOdrg : $('#rdoBestRate').val()						//0:추천, 1:전체
            , reqBuyType : 'UU'											//UU:유심단독구매 MM:단말구매
            , modelId : $('input[name=prdtId]:checked').val()			//모델코드(ex:K7006037) prdtId ==> modelId
            , prodId : prodId
            , modelMonthly : $("input[name='agrmTrm']:checked").val()	//약정유형
            , serviceType : 'PO'										//PP: 선불, PO : 후불
            , socCode : $('#socCode').val()								//요금제코드(ex:PL20B9416)
            , modelSalePolicyCode : $('#salePlcyCd').val()				//정책코드
            , onOffType : onOffType				                        //구분코드 7 모바일(셀프개통) 3 모바일
            , tmpStep : '0'												//임시저장 단계 0 : 요금제설계 --> 가입신청중에 표시 생략, 1 : 본인확인 및 약관동의, 2 : 유심정보 및 신분증 확인, 3 : 가입신청 정보, 4 : 부가서비스 정보, 5 : 납부정보 및 가입정보 확인
            , settlAmt : $('#totalPrice').text().replace(/,/gi, '')		//임시 최종 결제 금액
            , prodCtgType : $('#prodCtgType').val()
            , sesplsYn : sesplsYn
            , sesplsProdId : sesplsProdId
            , cardDcCd : cardDcCd
            , usimKindsCd : usimKindsCd
            , uploadPhoneSrlNo : uploadPhoneSrlNo
            , cstmrType : cstmrType
            , evntCdPrmt : $.trim($("#evntCdSeq").val())
        });

    } else {
        var prdtSctnCd = '5G';
        if ($('#prdtSctnCd').val() == '04') {
            prdtSctnCd = 'LTE';
        } else if ($('#prdtSctnCd').val() == '03') {
            prdtSctnCd = '3G';
        } else if ($('#prdtSctnCd').val() == '45') {
            prdtSctnCd = $('input[name=dataType]:checked').val();
        }

        //일시 납부
        var settlWayCd = "";
        if($('input[name=operType]:checked').val() == 'NAC3' && pageInfo.settlYn =="Y" && pageInfo.instAmt > 0 ){
            settlWayCd = "01";
        }

        varData = ajaxCommon.getSerializedData({
            prodId : $('#prodId').val()								//상품아이디(ex:3628)
            , operType : $('input[name=operType]:checked').val()		//가입유형(번이,신규)
            , cntpntShopId : cntpntShopId                               //접점코드
            , modelId : $('select[name="hndsetModelId"]').val()	//모델코드(ex:K7006037) prdtId ==> modelId
            , modelMonthly : $('#agrmTrm').val()						//단말기할부기간
            , sprtTp : (!$('input[name=saleTy]:checked').val()) ? '' : $('input[name=saleTy]:checked').val()			//지원금유형 (단말할인:KD , 요금할인:PM)
            , serviceType : 'PO'										//PP: 선불, PO : 후불
            , socCode : $('#socCode').val()								//요금제코드(ex:PL20B9416)
            , enggMnthCnt : $("input[name='instNom']:checked").val()	//약정
            , reqBuyType : 'MM'											//UU:유심단독구매 MM:단말구매
            , onOffType : onOffType
            , tmpStep : '0'												//임시저장 단계 0 : 요금제설계 --> 가입신청중에 표시 생략, 1 : 본인확인 및 약관동의, 2 : 유심정보 및 신분증 확인, 3 : 가입신청 정보, 4 : 부가서비스 정보, 5 : 납부정보 및 가입정보 확인
            , settlAmt : $('#totalPrice').text().replace(/,/gi, '')		//임시 최종 결제 금액
            , modelSalePolicyCode : $('#salePlcyCd').val()				//정책코드
            , prdtSctnCd : prdtSctnCd									//03:3G, 04:LTE, 08:5G
            , sesplsYn : sesplsYn
            , settlWayCd : settlWayCd
            , cstmrType : cstmrType
            , evntCdPrmt : $.trim($("#evntCdSeq").val())
        });
    }

    ajaxCommon.getItem({
            id:'insertAppFormTempSave'
            ,cache:false
            ,url:'/m/appForm/insertAppFormTempSave.do'
            ,data: varData
            ,dataType:"json"
            ,async:false

        }
        ,function(data){
            if(data.resultCd == '0000'){
                var url = '/m/appForm/appForm.do';
                if(type == 'noMember' || type == 'member'){
                    ajaxCommon.createForm({
                        method : 'post'
                        ,action : url
                    });

                    ajaxCommon.attachHiddenElement("requestKey",data.requestTempKey);
                    ajaxCommon.formSubmit();

                }else {
                    $('#uri').val(url+'?requestKey=' + data.requestTempKey);
                    $('#frm').submit();
                }
            }else{
                MCP.alert(data.msg);
            }

        });

}

//유심 기본요금 정보 가져오기
var usimInfoAjax = function(isInit){
    var dataType = (!$(':radio[name="dataType"]:checked').val()) ?  'LTE' : $(':radio[name="dataType"]:checked').val();
    var prdtId = (!$(':radio[name="prdtId"]:checked').val()) ?  '' : $(':radio[name="prdtId"]:checked').val();
    prdtId = (isInit) ? '' : prdtId;

    var operType = (!$(':radio[name="operType"]:checked').val()) ?  '' : $(':radio[name="operType"]:checked').val();
    var orderEnum = (!$('[name=orderEnum]:checked').val()) ? '' :$('[name=orderEnum]:checked').val();
    var agrmTrm = (!$(':radio[name="agrmTrm"]:checked').val()) ?  '' : $(':radio[name="agrmTrm"]:checked').val();
    if(agrmTrm == ''){
        agrmTrm = (!$(':radio[name="agrmTrm"]:checked').val()) ?  '' : $(':radio[name="agrmTrm"]:checked').val();
    }
    agrmTrm = (isInit) ? '' : agrmTrm;

    var noArgmYn = (agrmTrm != '0') ? 'Y' : 'N';
    var salePlcyCd =(!$(':radio[name=agrmTrm]:checked').attr('salePlcyCd'))?  '' : $(':radio[name=agrmTrm]:checked').attr('salePlcyCd');
    salePlcyCd = (isInit) ? '' : salePlcyCd;

    var rtnVal = true ;
    var initPrdtId = $("#initPrdtId").val();
    var prdtIndCd = $("#prdtIndCd").val();

    if (isInit && prdtId == "") {
        prdtId = initPrdtId;
    }

    var orgnId = $('#initOrgnId').val();
    if($('#prodCtgType').val() == '03'){
        orgnId = 'V000019481';	//자급제
    }

    var varData = ajaxCommon.getSerializedData({
        salePlcyCd : salePlcyCd
        , payClCd:'PO'
        , prdtSctnCd: dataType
        , prdtId : prdtId
        , operType : operType
        , orderEnum : orderEnum
        , agrmTrm : agrmTrm
        , noArgmYn : noArgmYn
        , orgnId : orgnId
        , prdtIndUsimCd : prdtIndCd
    });

    ajaxCommon.getItemNoLoading({
        id:'getUsimInfo'
        ,cache:false
        ,url:'/m/usim/selectUsimBasShopListAllAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    },function(jsonObj){
        if ('0001' ==  jsonObj.RESULT_CODE) {
            var inputDto = jsonObj.inputDto;
            var innerHTML = '';

            //usim 이미지세팅 && 모델 세팅
            var usimBasList = jsonObj.usimBasList;
            if(usimBasList){
                //var imgPath = usimBasList[0].imgPath;
                //var usimDesc = usimBasList[0].usimDesc;
                //$("#usim_image").html(usimDesc);

                if ($("input[name=prdtId]:checked").length == 0) {
                    var checkedVal = '';
                    for(var i=0; i<usimBasList.length; i++) {
                        if(initPrdtId == usimBasList[i].prdtId){
                            checkedVal = ' checked="checked" ';
                        }else{
                            checkedVal = '';
                        }
                        innerHTML +='<label style="width: 30%"><input type="radio" bestRate="'+usimBasList[i].bestRate+'" name="prdtId" prdtNm="'+usimBasList[i].prdtNm+'" value="'+usimBasList[i].prdtId+'" ' + checkedVal + '> '+usimBasList[i].prdtNm+'</label>';
                    }
                    $('#modelList').html(innerHTML);
                    if ($("input[name=prdtId]:checked").length == 0) {
                        $('input[name=prdtId]:eq(0)').prop('checked',true);
                    }
                }

                //$('.thumbnail_main').html('<span class="helper"></span><img src="'+imgPath+'" alt="'+usimBasList[0].prdtNm+' 상품">');

            }

            //가입 유형
            var regiTypeList = jsonObj.selectPlcyOperTypeList;
            if(regiTypeList){
                innerHTML = '';

                var initOperType = "";
                if (isInit) {
                    initOperType = $("#initOperType").val();
                } else {
                    initOperType = $("[name=operType]:checked").val();
                }

                var checkedVal = '';
                for(var i=0; i<regiTypeList.length; i++){

                    if(initOperType == regiTypeList[i].operType){
                        checkedVal = ' checked="checked" ';
                    }else{
                        checkedVal = '';
                    }
                    innerHTML += '<div class="c-chk-wrap operType'+i+'">';
                    innerHTML += '	<input class="c-radio c-radio--button operType'+i+'" id="operType'+i+'" type="radio" name="operType" operName="'+regiTypeList[i].operName+'" value="'+regiTypeList[i].operType+'" ' + checkedVal + ' >';
                    innerHTML += '	<label class="c-label operType'+i+'" for="operType'+i+'"><span class="app-form-view__lable"> '+regiTypeList[i].operName+'<br><span class="u-fs-13">'+(regiTypeList[i].operName === '번호이동' ? '(쓰던번호 그대로)' : '(새로운 번호로)')+'</span></span></label>';
                    innerHTML += '</div>';
                }

                $('#regiTypeList').html(innerHTML);

                if ('11' == $('#prdtIndCd').val() ) {
                    $('input[name=operType][operName="번호이동"]').attr('disabled','disabled');
                    setTimeout(function() {
                        $('input[name=operType][operName="신규가입"]').trigger('click');
                    }, 1000)
                } else if ($(":radio[name=operType]:checked").length == 0) {
                    var onOffType = (!$('input[name=radOpenType]:checked').val()) ? '' : $('input[name=radOpenType]:checked').val();
                    if(onOffType == '7' && !pageInfo.simpleApplyObj.IsMnpTime ){
                        $('input[name=operType][operName="번호이동"]').attr('disabled','disabled');
                        setTimeout(function() {
                            $('input[name=operType][operName="신규가입"]').trigger('click');
                        }, 1000)

                    } else {
                        $('input[name=operType][operName="번호이동"]').trigger('click');
                    }
                }



                /*
                if($('input[name=operType]:checked').val() == 'MNP3'){
                    $('#movTxt').show();
                    $('#newTxt').hide();
                }else{
                    $('#movTxt').hide();
                    $('#newTxt').show();
                }
                */


                if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
                    $('input[name=operType]').each(function (){
                        if($(this).val() == 'HDN3' || $(this).val() == 'HCN3'){
                            $('.' + $(this).attr('id')).hide();
                        }
                    });
                }
            }else{
                $('#regiTypeList').html('해당 되는 가입유형이 없습니다.');
            }

            //약정할인
            var selectUsimSalePlcyCdList = jsonObj.selectUsimSalePlcyCdList;
            if(selectUsimSalePlcyCdList){
                innerHTML = '';
                var instString ='';
                var initAgrmTrm = "";
                if (isInit) {
                    initAgrmTrm = $("#initAgrmTrm").val();
                } else {
                    initAgrmTrm =  $("[name=agrmTrm]:checked").val();
                }

                for(var i=0; i<selectUsimSalePlcyCdList.length; i++){
                    instString = (selectUsimSalePlcyCdList[i].agrmTrm>0) ? selectUsimSalePlcyCdList[i].agrmTrm+'개월' : '무약정';

                    if(initAgrmTrm == selectUsimSalePlcyCdList[i].agrmTrm){
                        checkedVal = ' checked="checked" ';
                    }else{
                        checkedVal = '';
                    }

                    innerHTML +='<label style="width: 30%"><input type="radio" name="agrmTrm" salePlcyCd="'+selectUsimSalePlcyCdList[i].salePlcyCd+'" value="'+selectUsimSalePlcyCdList[i].agrmTrm+'" ' + checkedVal + '> '+instString+'</label>';

                }
                $('#selAgreeAmt').html(innerHTML);

                if ($(":radio[name=agrmTrm]:checked").length == 0) {
                    $('input[name=agrmTrm]:eq(0)').trigger('click');
                }

                //getChargelist();
                if (operType != "") {
                    $("#initFirst").val("noFirst");
                }
            }
        } else {
            rtnVal = false ;
            MCP.alert(jsonObj.RESULT_MSG, function (){
                var initFirst = $("#initFirst").val();
                if (initFirst == "first") {
                    history.back();
                } else {
                    location.reload();
                }
            });
        }

    });

    return rtnVal ;
};

var setChargeInit = function() {
    $('#payMnthAmt').text('0');

    $('#hndstAmt').text('0 원')

    $('#subsdAmt').text('0 원')

    $('#agncySubsdAmt').text('0 원')

    $('#finstAmt').text('0 원')

    $('#totalFinstCmsn').text('0 원')

    $('#hndstPayAmt').text('0 원')
};

var setChargeInfo = function(obj) {

    //월 단말요금
    var finalAmt = $(obj).attr('finalAmt');
    $("#finalAmt").text(finalAmt);

    //단말기 실구매가
    var fhndstPayAmt = $(obj).attr('fhndstPayAmt');
    $("#fhndstPayAmt").text(fhndstPayAmt);

    //단말기 출고가
    var fhndstAmt = $(obj).attr('fhndstAmt');
    $("#fhndstAmt").text(fhndstAmt);

    //공시지원금
    var fsubsdAmt = $(obj).attr('fsubsdAmt');
    $("#fsubsdAmt").text(fsubsdAmt);

    //추가지원금
    var fagncySubsdAmt = $(obj).attr('fagncySubsdAmt');
    $("#fagncySubsdAmt").text(fagncySubsdAmt);

    //할부수수료
    var finstCmsn = $(obj).attr('finstCmsn');
    $("#finstCmsn").text(finstCmsn);

    //총할부수수료
    var totalFinstCmsn = $(obj).attr('totalFinstCmsn');
    $("#totalFinstCmsn").text(totalFinstCmsn);

    //할부원금
    var finstAmt = $(obj).attr('finstAmt');
    $("#finstAmt").text(finstAmt);
    $("div.select_table").find("tbody").find("tr:eq(3)").find("span.red").text(finstAmt);

    //단말기 실구매가
    var fhndstPayAmt = $(obj).attr('fhndstPayAmt');
    $("#fhndstPayAmt").text(fhndstPayAmt);

    //월단말요금
    var fpayMnthAmt = $(obj).attr('fpayMnthAmt');
    $("#fpayMnthAmt").text(fpayMnthAmt.replace("",""));

    //월 통신요금
    var fpayMnthChargeAmt = $(obj).attr('fpayMnthChargeAmt');
    $("#fpayMnthChargeAmt").text(fpayMnthChargeAmt.replace("",""));

    //기본요금
    var fbaseAmt = $(obj).attr('fbaseAmt');
    $("#fbaseAmt").text(fbaseAmt);

    //약정할인 (기본할인)
    var fdcAmt = $(obj).attr('fdcAmt');
    $("#fdcAmt").text(fdcAmt);

    //요금할인(스폰서할인)
    var faddDcAmt = $(obj).attr('faddDcAmt');
    $("#faddDcAmt").text(faddDcAmt);

    //프로모션 할인
    var promotionDcAmt = $("input[name='selectRate']:checked").attr("promotionDcAmt");
    $("#promotionDcAmtTxt").text(promotionDcAmt + ' 원');


    //요금제명
    var frateNm = $(obj).attr('right_border").children("div.frateNm');
    var fprodNm = $("#fprodNm").text();
    var fSntyNm = $("select[name='hndsetModelId'] option:selected").text();
    $("#infoLabel").text(fprodNm +"/"+ fSntyNm + "/" + frateNm);

};

//최종 지불해야할 명세서 구하기
var selectUsimBasJoinPriceAjax = function(obj, isInit){
    var thisData = obj.data();
    var rateNm = thisData.rateNm ;
    var baseAmt = thisData.baseVatAmt; // 20230807 부가세포함 가격으로 변경
    var totalVatPrice = 0;
    var promotionDcAmt = thisData.promotionDcAmt;
    var promotionAmtVatDesc = thisData.promotionAmtVatDesc ;
    var payMnthChargeAmt = 0;

    if (isInit == undefined ) {
        isInit = true ;
    }

    if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
        var totalVatPrice = Number(thisData.payMnthChargeAmt);
        if($("#agrmTrm").val() != '0'){
            totalVatPrice = Number(thisData.payMnthChargeAmt) + Number(thisData.payMnthAmt);
        }
        payMnthChargeAmt = thisData.payMnthChargeAmt;
        totalVatPrice = Math.ceil(totalVatPrice)+'';
    } else {
        if(!isEmpty(promotionAmtVatDesc)){
            totalVatPrice = promotionAmtVatDesc;
        } else {
            if(thisData.mmBasAmtVatDesc  != undefined && thisData.mmBasAmtVatDesc != ''){
                totalVatPrice = thisData.mmBasAmtVatDesc;
            } else {
                totalVatPrice = thisData.payMnthChargeAmt;
            }
        }
    }

    if(typeof totalVatPrice == 'string')	{
        totalVatPrice = totalVatPrice.replace(/,/gi, '');
    }


    var dcAmt = (isEmpty(thisData.dcVatAmt)) ?  0 : thisData.dcVatAmt;
    var addDcAmt = thisData.addDcVatAmt;
    var text = $(":radio[name=prdtId]:checked").attr('prdtNm');

    $("#initRateCd").val(obj.val());
    if (isInit) {
        getJoinUsimPriceInfo(obj.val());
    }


    if (text !="" ) {
        text = " / " +text;
    }

    if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
        $('#bottomTitle').text($('input[name=dataType]:checked').val() + text + ' / ' + $('input[name=operType]:checked').attr('operName') + ' / ' + rateNm);
    }else{

        var prdtSctnVal = '5G';
        if ($('#prdtSctnCd').val() == '03') {
            prdtSctnVal = '3G';
        } else if ($('#prdtSctnCd').val() == '04') { // 요금제 타입 변경
            prdtSctnVal = 'LTE';
        } else if ($('#prdtSctnCd').val() == '45') {
            prdtSctnVal = $('input[name=dataType]:checked').val();
        }
        $('#bottomTitle').text(prdtSctnVal + ' / ' + $('input[name=operType]:checked').next().text() + ' / ' + rateNm);
    }

    if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
        if($('#agrmTrm').val() == '0'){
            $('#payMnthAmt').text(numberWithCommas(thisData.payMnthAmt));
        }else{
            $('#payMnthAmt').text(numberWithCommas(Number(thisData.payMnthAmt)));
        }
        $('#hndstAmt').text(numberWithCommas(thisData.hndstAmt) + ' 원');
        $('#subsdAmt').text('-' + numberWithCommas(thisData.subsdAmt) + ' 원');
        $('#agncySubsdAmt').text('-' + numberWithCommas(thisData.agncySubsdAmt) + ' 원');
        $('#finstAmt').text(numberWithCommas(thisData.instAmt) + ' 원');
        pageInfo.instAmt = thisData.instAmt ; // 단말기 할부원금
        $('#totalFinstCmsn').text(numberWithCommas(thisData.totalInstCmsn) + ' 원');
        $('#hndstPayAmt').text(numberWithCommas(thisData.hndstPayAmt) + ' 원');

        //일시납부
        if($('input[name=operType]:checked').val() == 'NAC3' && pageInfo.settlYn =="Y"  ){
            $("#finstAmt").siblings("dt").text("즉시결제 금액");
            $("#totalFinstCmsn").parent().hide();  //총할부수수료
        } else {
            $("#finstAmt").siblings("dt").text("할부원금");
            $("#totalFinstCmsn").parent().show();  //총할부수수료
        }
    }

    $('#totalPriceBottom').html(numberWithCommas(totalVatPrice) + '<span class="fs-14 fw-normal"> 원</span>');
    $('#totalPrice').html(numberWithCommas(totalVatPrice));
    if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
        $('#totalPrice2').html(numberWithCommas(payMnthChargeAmt));
    }else{
        $('#totalPrice2').html(numberWithCommas(totalVatPrice));
    }
    $('#baseAmt').html(numberWithCommas(baseAmt)+' 원');
    $('#promotionDcAmtTxt').html(numberMinusWithCommas(promotionDcAmt) + ' 원');

    //제휴카드 할인 금액 처리
    let cprtDcAmt = 0;
    if($('input[name=chkCard]:checked').val() != undefined && $('input[name=chkCard]:checked').val() != ''){
        $('input[name="cprtCardDcInfo' + $('input[name=chkCard]:checked').val() + '"]').each(function (){

            if($(this).attr('data-dcFormlCd') == 'PCT'){
                cprtDcAmt = totalVatPrice * (Number($(this).attr('data-dcAmt')) / 100);
                if(cprtDcAmt > Number($(this).attr('data-dcLimitAmt'))){
                    cprtDcAmt = Number($(this).attr('data-dcLimitAmt'));
                }
                cprtDcAmt = Math.floor(cprtDcAmt);
            }else if($(this).attr('data-dcFormlCd') == 'WON'){
                var dcSectionStAmt = Number($(this).attr('data-dcSectionStAmt'));
                var dcSectionEndAmt = Number($(this).attr('data-dcSectionEndAmt'));
                cprtDcAmt = Number($(this).attr('data-dcAmt'));
            }

            if(cprtDcAmt > 0){
                /*
                if(cprtDcAmt >= totalVatPrice){
                    $('#cprtCardPrice').html('0 원');

                } else {
                    $('#cprtCardPrice').html(numberMinusWithCommas(cprtDcAmt) + ' 원');
                }*/
                $('#cprtCardPrice').html(numberMinusWithCommas(cprtDcAmt) + ' 원');
                return false;
            }

        });
    }

    //totalPrice  오른쪽 최종 금액 팝업 레이어
    //totalPrice2 월예상 납부금액  팝업 레이어 닫고 .. 표현 하는것..
    if ($("#ktTripleDcAmt_02").is(':checked') ) {
        cprtDcAmt+= pageInfo.ktTripleDcAmt ;
        $('#ktTripleDcAmtTxt').html(numberMinusWithCommas(pageInfo.ktTripleDcAmt)+ ' 원');
    } else {
        $('#ktTripleDcAmtTxt').html("0 원");
    }

    if(cprtDcAmt > 0){
        if(cprtDcAmt >= totalVatPrice){
            $('#totalPrice').html('0');
            $('#totalPrice2').html('0');
            $('#totalPriceBottom').html('0 <span class="fs-14 fw-normal"> 원</span>');
        }else{

            if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
                $('#totalPrice').html(numberWithCommas(payMnthChargeAmt - cprtDcAmt + Number(thisData.payMnthAmt)));   //$(obj).attr('payMnthAmt')
                $('#totalPrice2').html(numberWithCommas(payMnthChargeAmt));
                $('#totalPriceBottom').html(numberWithCommas(payMnthChargeAmt - cprtDcAmt + Number(thisData.payMnthAmt)) + '<span class="fs-14 fw-normal"> 원</span>');
            }else{
                $('#totalPrice').html(numberWithCommas(totalVatPrice - cprtDcAmt));
                $('#totalPrice2').html(numberWithCommas(totalVatPrice));
                $('#totalPriceBottom').html(numberWithCommas(totalVatPrice - cprtDcAmt) + '<span class="fs-14 fw-normal"> 원</span>');
            }
        }
    }

    $('.bottomTop').show();
    if(dcAmt == '0'){
        $('#bottomDefDc').hide();
    }else{
        $('#bottomDefDc').show();
        $('#dcAmt').html(numberMinusWithCommas(dcAmt)+' 원');
    }

    if(addDcAmt == '0'){
        $('#bottomPriceDc').hide();
    }else{
        $('#bottomPriceDc').show();
        $('#addDcAmt').html(numberMinusWithCommas(addDcAmt)+' 원');
    }

    if (isInit) {
        if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){	//유심
            getGiftList('UU');
        } else {
            getGiftList('MM');
        }
    }
};

var initView = function() {
    //$('#bottomRateType').html("");
    //$('#bottomRateType2').html("");
    $('#bottomTitle').html("");
    $('#searchNm').val('');

    $('#dcAmt').html('0 원');
    $('#addDcAmt').html('0 원');
    $('#baseAmt').html('0 원');
    $('#totalPrice').html("0");
    $('#totalPrice2').html("0");
    $('#totalPriceBottom').html("0" + '<span class="fs-14 fw-normal" id="totalPriceBottom"> 원</span>');
    $('#promotionDcAmtTxt').html("0 원");
    $("#joinPriceTxt").html("0 원");
    $("#usimPriceTxt").html("0 원");

    //kt 트리플 할인 초기화
    $('#ktTripleDcAmtTxt').html("0 원");
    $('#ktTripleDcAmt_01').prop("checked",true);
    pageInfo.ktTripleDcAmt = 0;
    pageInfo.ktTripleDcRateCd = "";
    $('._ktTripleDcCss').hide();
}


//판매 요금제 별 가입비/유심비 면제 여부 조회
var getJoinUsimPriceInfo = function(socCode) {

    var varData = ajaxCommon.getSerializedData({
        dtlCd:socCode
        ,cdGroupId:CD_GROUP_ID_OBJ.JoinUsimPriceInfo
    });

    ajaxCommon.getItem({
            id:'getJoinUsimPriceInfo'
            ,cache:false
            ,url:'/m/getCodeNmAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){
            if (jsonObj != null && jsonObj.expnsnStrVal1 == "Y") {
                $("#joinPriceIsPay").val("Y");
            } else {
                $("#joinPriceIsPay").val("N");
            }

            if (jsonObj != null && jsonObj.expnsnStrVal2 == "Y") {
                $("#usimPriceIsPay").val("Y");
            } else {
                $("#usimPriceIsPay").val("N");
            }
            showJoinUsimPrice();

        });
}

var showJoinUsimPrice = function() {
    var joinPriceIsPay = $("#joinPriceIsPay").val();
    var usimPriceIsPay = $("#usimPriceIsPay").val();
    var usimPrice = $("#usimPrice").val();

    if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){

    }else{
        var operType = $('input[name=operType]:checked').val();
        if ( (operType == OPER_TYPE.MOVE_NUM || operType == OPER_TYPE.NEW) && ($('#prodCtgId').val() == PROD_CTG_ID.LTE) ) {
            usimPrice = numberWithCommas(pageInfo.usimPriceBase);
        } else if( (operType == OPER_TYPE.MOVE_NUM || operType == OPER_TYPE.NEW) && ($('#prodCtgId').val() == PROD_CTG_ID.ID_5G) ){
            usimPrice = numberWithCommas(pageInfo.usimPrice5G);
        }
    }

    //if ($("input[name='selectRate']:checked").length > 0) {
    if ($('#socCode').val() != '' && $('#socCode').val() != undefined) {

        // 접점, 가입유형 , eSIM 경우 에 따라
        // 가입비 , 유심비 확인
        if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){

            var varData = ajaxCommon.getSerializedData({
                orgnId:$('#initOrgnId').val()
                ,operType:$('input[name=operType]:checked').val()
                ,dataType:$(':radio[name="dataType"]:checked').val()
                ,prdtIndCd:$('#prdtIndCd').val()
                ,rateCd:$('#socCode').val()
            });

            ajaxCommon.getItemNoLoading({
                    id:'getSimInfo'
                    ,cache:false
                    ,url:'/usim/getSimInfoAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,async:false
                }
                ,function(jsonObj){
                    $('#joinPrice').val(jsonObj.JOIN_PRICE);
                    usimPrice = numberWithCommas(jsonObj.SIM_PRICE);
                    joinPriceIsPay = jsonObj.JOIN_IS_PAY;
                    usimPriceIsPay  = jsonObj.SIM_IS_PAY;
                });
        }

        var middlePrice = 0;
        var joinPriceVal = 0;
        var usimPriceVal = 0;
        var movePriceVal = 0;
        if (joinPriceIsPay == "Y") {
            joinPriceVal = $('#joinPrice').val();
            $("#joinPriceTxt").html($("#joinPrice").val()+" 원");
        } else {
            //$("#joinPriceTxt").html('<span class="c-text c-text--strike">'+$('#joinPrice').val()+'원</span>(무료)');
            $("#joinPriceTxt").html('<span class="c-text c-text--strike">' + numberWithCommas($("#joinPrice").val()) + ' 원</span>(무료)');
        }

        if (usimPriceIsPay == "Y") {
            usimPriceVal = usimPrice.replace(/,/, '');
            $("#usimPriceTxt").html(usimPrice+" 원");
        } else {
            $("#usimPriceTxt").html('<span class="c-text c-text--strike">'+usimPrice+' 원</span>(무료)');
        }

        if($('#moveYn').val() == 'Y'){
            movePriceVal = 800;
        }

        if(!$('#move_02').is(':visible') && joinPriceIsPay != 'Y' && usimPriceIsPay != 'Y'){
            $('#bottomMiddle').hide();
        }else{
            $('.bottomMiddle').show();
        }
    }
}


//multi slider 작동 설정
function rangeSliderSetup(wrapper) {
    var arr = {};
    var rangeValue = wrapper.getAttribute("data-range-value");
    rangeValue = rangeValue.split(",");
    for (var i = 0; i < rangeValue.length; i++) {
        arr[rangeValue[i].trim()] = i;
    }
    var rangeSliderEl = wrapper.querySelector(".c-range.multi");
    var marks = wrapper.querySelectorAll(".ruler-mark");
    rangeSliderEl.addEventListener("ui.rsliders.change", function(e) {
        var min = e.value[0];
        var max = e.value[1];
        [].forEach.call(marks, function(el) {
            el.classList.remove("is-point");
        });
        marks[arr[min]].classList.add("is-point");
        marks[arr[max]].classList.add("is-point");
    });
}

document.addEventListener("DOMContentLoaded", function() {
    var sliderWrappers = document.querySelectorAll(".c-range__wrap");
    [].forEach.call(sliderWrappers, function(wrapper) {
        rangeSliderSetup(wrapper);
    });
    //accordState();
});

var checkIsBtnDisabled = function (){
    if($('input[name=radOpenType]:checked').val() == undefined ){         return false;        }
    if($('input[name=operType]:checked').val() == undefined ){            return false;        }
    if($('input[name=dataType]:checked').val() == undefined ){            return false;        }
    if(!$('#radPayTypeB').is(':visible')){            return false;        }

    if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
        if($.trim($("#evntCdPrmt").val()) != "" && $("#isEvntCdPrmtAuth").val() != "1"){
            return false;
        }
    }

    //eSIM
    var usimKindsCd = $('#prdtIndCd').val();
    if(usimKindsCd == '10' ) {
        var uploadPhoneSrlNo = Number(ajaxCommon.isNullNvl(pageObj.uploadPhoneSrlNo,0));
        if( uploadPhoneSrlNo < 1 ){
            return false;
        }
    };
    return true;
}
var nextStepBtnChk = function (){

    if (checkIsBtnDisabled()) {
        $('#nextStepBtn').removeClass('is-disabled');
    } else {
        $('#nextStepBtn').addClass('is-disabled');
    }
}

var priceBtnChoice = function (){
    $('#paymentSelectBtn').removeClass('is-disabled');
}

//$(document).ready(function (){
$(window).load(function (){


    $("#modalArs").addClass("rate-detail-view");

    $(document).on('change','[name=agrmTrm]',function() {
        var socCode = $('#socCode').val();
        pageInfo.asAgrmTrmVal=$(this).val() ;

        compareCnt = 0;
        $('.compareLI').remove();
        $('.in-box').empty();
        $('.in-box').append('<span class="c-hidden">0</span><i class="c-icon c-icon--num0" aria-hidden="true"></i>');
        $('#socCode').val('');
        $('#radPayTypeA').show();
        $('#radPayTypeB').hide();
        $('#divGift').hide();
        initEvntCdPrmt();
        $('#choicePay').text('');
        $('#choicePayTxt').text('');
        $('.giftInfoListHr').hide();
        $('#bottomTitle').text('');

        if(socCode != ""){
            initRateCdChoice = 'Y';
            $('#initRateCd').val(socCode);
            getChargelist();
        }

        return  ;
    });

    $(document).on('change','[name=operType]',function() {
        if($('#radOpenType1').is(':checked') ){
            if(!selfTimeChk()) {
                return;
            }
        }
        if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){	//유심
            getJoinUsimPrice();
            getGiftList('UU');
        } else {	//휴대폰
            if($('input[name=operType]:checked').val() == 'NAC3' && pageInfo.settlYn =="Y"){
                MCP.alert('신규가입 시 단말기 구매는 즉시결제만<br/> 가능 합니다.');
            }
            getGiftList('MM');
        }
        nextStepBtnChk();
        setAgrmTrm();
        getChargelist();
    });

    $('#filterSearchBtn').on('click', function (){
        $('#filterAllYn').val('N');
        getChargelist();
    });

    $('#filterSearchAllBtn').on('click', function (){
        $('#filterAllYn').val('Y');
        getChargelist();
    });

    //버튼 ajax 이벤트 세팅
    $(document).on('change','[name=prdtId]',function() {
        if (usimInfoAjax(false)) {
            //요금제 바뀔경우 이전체크 초기화
            $('#compareOnly').prop('checked',false) ;
            getChargelist();
            getJoinUsimPrice();
            pageInfo.prdtIdVal = $("[name=prdtId]:checked").val();
            pageInfo.operTypeVal = $("[name=operType]:checked").val();
            pageInfo.agrmTrmVal = $("[name=agrmTrm]:checked").val();
        } else {
            $("[name=prdtId][value='"+pageInfo.prdtIdVal+"']").prop("checked","checked");
            $("[name=operType][value='"+pageInfo.operTypeVal+"']").prop("checked","checked");
            $("[name=agrmTrm][value='"+pageInfo.agrmTrmVal+"']").prop("checked","checked");
        }

        return  ;
    });

    //버튼 ajax 이벤트 세팅(최상단)
    $("input[name=dataType]").on('change',function() {
        if ($('#prodId').val() && $('#prodCtgType').val() != '03') { // 단말
            var socCode = $('#socCode').val();

            compareCnt = 0;
            $('.compareLI').remove();
            $('.in-box').empty();
            $('.in-box').append('<span class="c-hidden">0</span><i class="c-icon c-icon--num0" aria-hidden="true"></i>');
            $('#socCode').val('');
            $('#radPayTypeA').show();
            $('#radPayTypeB').hide();
            $('#divGift').hide();
            initEvntCdPrmt();
            $('#choicePay').text('');
            $('#choicePayTxt').text('');
            $('.giftInfoListHr').hide();
            $('#bottomTitle').text('');

            if(socCode != ""){
                initRateCdChoice = 'Y';
                $('#initRateCd').val(socCode);
            }
            getChargelist();
            $('#bottomTitle1').text($('input[name=dataType]:checked').val());

        } else { // 유심


            $('#socCode').val("");
            initView();

            //유심형태 값을 초기화 한다.
            $('input[name=prdtId]:checked').prop('checked',false);
            $('input[name=agrmTrm]:checked').prop('checked',false);
            $('input[name=chkPayment]:checked').prop('checked', false);
            $('input[name=selectRate]:checked').prop('checked', false);
            if (usimInfoAjax(false)) {
                //요금제 바뀔경우 이전체크 초기화
                pageInfo.dataTypeVal = $(this).val();
                //console.log("[WOO][WOO][WOO]===>"+ initRateCdChoice);
                //getChargelist();
                if(initRateCdChoice == 'Y'){
                    getChargelist();
                }
                getJoinUsimPrice();
            } else {
                $("[name=dataType][value='"+pageInfo.dataTypeVal+"']").prop("checked","checked");
            }

            compareCnt = 0;
            $('.compareLI').remove();
            $('.in-box').empty();
            $('.in-box').append('<span class="c-hidden">0</span><i class="c-icon c-icon--num0" aria-hidden="true"></i>');
            $('#socCode').val('');
            $('#radPayTypeA').show();
            $('#radPayTypeB').hide();
            $('#divGift').hide();
            initEvntCdPrmt();
            $('#choicePay').text('');
            $('#choicePayTxt').text('');
            $('.giftInfoListHr').hide();
            $('#bottomTitle').text('');

            //2022.10.18 wooki
            //셀프개통 시간을 체크
            var simpleApplyObj;
            ajaxCommon.getItemNoLoading({
                id:'isSimpleApply'
                ,cache:false
                ,url:'/appForm/isSimpleApplyAjax.do'
                ,data: ""
                ,dataType:"json"
                ,async:false
            },function(jsonObj){
                simpleApplyObj = jsonObj ;
            });

            //셀프개통이 선택되어 있고 번호이동이 불가능한 시간이면 번호이동은 disabled 처리
            if($('#radOpenType1').is(':checked') && !simpleApplyObj.IsMnpTime){
                $('input[name=operType][operName="번호이동"]').attr('disabled','disabled');
            }
        }
    });

    var getJoinUsimPrice = function() {

        var operType = $(':radio[name="operType"]:checked').val() ;
        if(operType == undefined || operType == ''){
            operType = $('#initOperType').val()
        }
        //가입비조회
        var varData = ajaxCommon.getSerializedData({
            dataType: $(':radio[name="dataType"]:checked').val()
            , operType : operType
            ,rateCd : ''
        });

        ajaxCommon.getItem({
            id:'getUsimInfo'
            ,cache:false
            ,url:'/m/usim/selectUsimBasJoinPriceAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            if ('0001' ==  jsonObj.RESULT_CODE) {
                //번호이동 수수료
                if(operType==OPER_TYPE.MOVE_NUM){
                    $('#move_01').show();
                    $('#move_02').show();
                    $('#moveYn').val('Y');
                }else{
                    $('#move_01').hide();
                    $('#move_02').hide();
                    $('#moveYn').val('N');
                }

                var joinUsimPrice = jsonObj.selectJoinUsimPrice;

                if (joinUsimPrice != undefined && joinUsimPrice.length > 0) {
                    $("#joinPrice").val(numberWithCommas(joinUsimPrice[0].joinPrice));
                    $("#usimPrice").val(numberWithCommas(joinUsimPrice[0].usimPrice));
                }
                showJoinUsimPrice();

            }
        });
    }

    var zeroRemoveMinu = function(input) {
        if (input == 0) {
            return input;
        } else {
            return "-" + numberWithCommas(input);
        }
    };

    var getCprtCardList = function(){

        var varData = {};

        ajaxCommon.getItem({
                id:'getCprtCardList'
                ,cache:false
                ,url:'/m/cprt/cprtCardXmlViewAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
            }
            ,function(jsonObj){
                var innerHTML = '';
                if(jsonObj != undefined && jsonObj.length > 0){
                    for (var i = 0; i < jsonObj.length; i++) {
                        if(i == 0){
                            innerHTML += '<div class="c-chk-wrap">';
                            innerHTML += '    <input class="c-radio c-radio--button" id="chkCardX" type="radio" name="chkCard" checked onclick="cprtPriceSet();"> ';
                            innerHTML += '    <label class="c-label u-ta-left" for="chkCardX">';
                            innerHTML += '        <div class="icon-prefix">';
                            innerHTML += '            <img src="/resources/images/mobile/common/ico-card-no.svg" alt="noimg">';
                            innerHTML += '        </div>';
                            innerHTML += '        <div class="c-label--title">';
                            innerHTML += '            <p class="c-text c-text--type2">선택 안 함</p>';
                            innerHTML += '        </div>';
                            innerHTML += '    </label>';
                            innerHTML += '</div>';

                        }
                        innerHTML += '<div class="c-chk-wrap" id="cprtCard' + i + '" style="display:none;">';
                        innerHTML += '    <input class="c-radio c-radio--button" id="chkCard'+ i +'" type="radio" name="chkCard" value="' + i + '" onclick="cprtPriceSet();">';
                        innerHTML += '    <label class="c-label u-ta-left" for="chkCard'+ i +'">';
                        innerHTML += '        <div class="icon-prefix">';
                        innerHTML += '            <img src="' + jsonObj[i].cprtCardThumbImgNm + '" alt="' + jsonObj[i].cprtCardGdncNm + ' 실물이미지" onerror="this.src=\'/resources/images/mobile/common/ico-card-no.svg\'">';
                        innerHTML += '        </div>';
                        innerHTML += '        <div class="c-label--title">';
                        innerHTML += '            <p class="c-text c-text--type2">' + jsonObj[i].cprtCardGdncNm + '</p>';
                        //innerHTML += '            <p class="c-text c-text--type3 u-co-gray">' + jsonObj[i].cprtCardBasDesc + '</p>';
                        if(jsonObj[i].cprtCardItemNm != ''){
                            innerHTML += '            <p class="c-text c-text--type3 u-co-gray">' + jsonObj[i].cprtCardItemNm + '</p>';
                        }
                        if(jsonObj[i].cprtCardItemDesc != ''){
                            innerHTML += '            <p class="c-text c-text--type3 u-co-gray">' + jsonObj[i].cprtCardItemDesc + '</p>';
                        }

                        innerHTML += '        </div>';
                        innerHTML += '    </label>';

                        if (jsonObj[i].alliCardDcAmtDtoList.length > 0) {
                            innerHTML += '<input type="hidden" name="cprtCardDcInfo' + i + '" data-dcFormlCd="' + jsonObj[i].alliCardDcAmtDtoList[0].dcFormlCd + '" data-dcAmt="'+ jsonObj[i].alliCardDcAmtDtoList[0].dcAmt +'" data-dcLimitAmt="'+ jsonObj[i].alliCardDcAmtDtoList[0].dcLimitAmt +'"  data-dcSectionEndAmt="'+ jsonObj[i].alliCardDcAmtDtoList[0].dcSectionEndAmt +'"  data-dcSectionStAmt="'+ jsonObj[i].alliCardDcAmtDtoList[0].dcSectionStAmt +'"/>';
                        }
                        innerHTML += '</div>';

                    }

                    $('#cprtDiv').html(innerHTML);
                    $('#cprtForm').show();
                    $('#cprtCardSize').val(jsonObj.length);
                }

                setMoreBtn();
            });

        $('input[name=chkCard]').on('click', function (){
            if($('#choicePay').text() != '' && $('#choicePayTxt').text() != ''){
                if(setRateType == 'selectRate'){
                    setRateInfo($('input[name=selectRate]:checked'));
                }else if(setRateType == 'chkPayment'){
                    setRateInfo($('input[name=chkPayment]:checked'));
                }
            }
        });
    }();

    $('#moreBtnDiv').on('click', function (){
        pageInfo.cprtPageNo++;
        setMoreBtn();
    });

    var getUsimPriceBase = function(){
        var varData = ajaxCommon.getSerializedData({
            cdGroupId:CD_GROUP_ID_OBJ.DirectUsimPrice
            ,dtlCd:DTL_CD_OBJ.DirectUsimPriceDtlCd
        });

        ajaxCommon.getItem({
                id:'getUsimPriceBase'
                ,cache:false
                ,url:'/m/getCodeNmAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
            }
            ,function(jsonObj){
                pageInfo.usimPriceBase = jsonObj.dtlCdNm ;

            });
    }();

    var getUsimPrice5G = function(){
        var varData = ajaxCommon.getSerializedData({
            cdGroupId:CD_GROUP_ID_OBJ.DirectUsimPrice
            ,dtlCd:PRDT_SCTN_CD.FIVE_G_FOR_MSP
        });

        ajaxCommon.getItem({
                id:'getUsimPriceBase'
                ,cache:false
                ,url:'/m/getCodeNmAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
            }
            ,function(jsonObj){
                pageInfo.usimPrice5G = jsonObj.dtlCdNm ;

            });
    }();


    $('input[name=orderEnum]').on('change', function (){
        $('.fs-md').html($(this).next().text() + '<i class="c-icon c-icon--sort" aria-hidden="true"></i>');
        $('#bsSample').find('.c-button').trigger('click');

        getChargelist();
    })

    $('#recommMoreDiv,#findMoreDiv').on('click', function (){
        pageInfo.pageNo++;
        setCardMoreBtn();
    });

    $('#paymentSelectBtn').on('click', function (){
        if($('input:radio[name=selectRate]:checked').length == 0){
            MCP.alert('요금제를 선택해 주세요.');
            return;
        }else{
            setRateType = 'selectRate';
            setRateInfo($('input[name=selectRate]:checked'));
        }

    });

    $('#compareBtn').on('click', function (){
        if($('input[name=chkPayment]:checked').length == 0){
            MCP.alert('요금제를 선택해 주세요.');
            return;
        }else{
            setRateType = 'chkPayment';
            $('input[name=selectRate]').removeProp('checked');
            setRateInfo($('input[name=chkPayment]:checked'));
        }
    });


    $('input[name=saleTy]').on('change', function (){
        setChargeInit();
        //defaultRateYn = 'Y'
        getChargelist();

        compareCnt = 0;
        $('.compareLI').remove();
        $('.in-box').empty();
        $('.in-box').append('<span class="c-hidden">0</span><i class="c-icon c-icon--num0" aria-hidden="true"></i>');
        $('#socCode').val('');
        $('#radPayTypeA').show();
        $('#radPayTypeB').hide();
        $('#divGift').hide();
        initEvntCdPrmt();
        $('#choicePay').text('');
        $('#choicePayTxt').text('');
        $('.giftInfoListHr').hide();
        $('#bottomTitle').text('');
    });

    $('input[name=instNom]').on('change', function (){
        setAgrmTrm();

        if($('input[name=instNom]:checked').val() == '0'){
            $('input[name=saleTy]').removeProp('checked');
            $('input[name=saleTy]').attr('disabled', 'disabled');

        }else{
            $('input[name=saleTy]').removeAttr('disabled');
            $('#chkDiscountType1').prop('checked', 'checked');
        }
        setChargeInit();
        //defaultRateYn = 'Y'


        var socCode = $('#socCode').val();

        compareCnt = 0;
        $('.compareLI').remove();
        $('.in-box').empty();
        $('.in-box').append('<span class="c-hidden">0</span><i class="c-icon c-icon--num0" aria-hidden="true"></i>');
        $('#socCode').val('');
        $('#radPayTypeA').show();
        $('#radPayTypeB').hide();
        $('#divGift').hide();
        initEvntCdPrmt();
        $('#choicePay').text('');
        $('#choicePayTxt').text('');
        $('.giftInfoListHr').hide();
        $('#bottomTitle').text('');
        if(socCode != ""){
            initRateCdChoice = 'Y';
            $('#initRateCd').val(socCode);
        }

        getChargelist();

    });



    $('input[name=radOpenType]').on('change', function (){


        if($(this).attr('id') == 'radOpenType1'){
            if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
                $('input[name=dataType]').each(function (){
                    if($(this).val() == '3G'){
                        $(this).removeProp('checked').attr('disabled', 'disabled');
                    }
                });
            }
            $("._noSelf").hide();
            $('#nextStepBtn').attr('data-step','10');
        }else{
            $('input[name=operType][operName="번호이동"]').removeAttr('disabled');
            $('input[name=operType][operName="신규가입"]').removeAttr('disabled');
            if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
                $('input[name=dataType]').each(function (){
                    if($(this).val() == '3G'){
                        $(this).removeAttr('disabled');
                    }
                });
            }
            $("._noSelf").show();
            $('#nextStepBtn').attr('data-step','30');
        }


        //$('input[name=operType]').removeProp('checked');

        if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
            $('input[name=operType]').each(function (){
                if($(this).val() == 'HDN3' || $(this).val() == 'HCN3'){
                    $('.' + $(this).attr('id')).hide();
                }
            });
        }
        $('input[name=operType]:checked').trigger('change');
        nextStepBtnChk();
    });

    var viewRateCtgGroup = function (type) {
        if (type == "1") {
            $('._rateCtg').each(function (){
                let tempVal = $(this).data("rateCtgGroup1");
                if (tempVal =="Y") {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            })
        } else {
            $('._rateCtg').each(function (){
                let tempVal = $(this).data("rateCtgGroup2");
                if (tempVal =="Y") {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            })
        }
    }

    $('.paymentPop').on('click', function (){
        if($('input[name=operType]:checked').val() == undefined || $('input[name=operType]:checked').val() == ''){
            MCP.alert('가입 유형을 선택해주세요.', function (){
                $('#operType0').focus();
            });
            return false;
        }

        sliderInitYn = 'Y';
        $('#paymentSelectBtn').addClass('is-disabled');
        //버튼 초기화
        $('._rateTabM1').removeClass('is-active');

        //var bestRate = $('#rdoBestRate').val();
        var bestRate = pageInfo.initBestRate;
        $('#rdoBestRate').val(pageInfo.initBestRate);
        if (bestRate == "1") {
            $('._rateTabM1').eq(0).addClass('is-active');
            pageInfo.tabButIndex = "1";
        } else {
            $('._rateTabM1').eq(1).addClass('is-active');
            pageInfo.tabButIndex = "0";
        }

        pageInfo.badgeList =[];//추천 요금제 초기화
        pageInfo.orderEnum="RECOMM_ROW";
        $(".btn-sort").removeClass('is-active');
        $(".btn-sort").removeClass('sort');

        //요금제 그룹 버튼 표현 여부 처리
        //- 유심 , 단말기(무약정)    공통코드 1번 표현
        //- 단말기(약정)            공통고트 2번 표현
        if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
            // 단말기
            var instNom = $('input[name="instNom"]:checked').val();
            if (instNom == "0") {
                viewRateCtgGroup(1);
            } else {
                viewRateCtgGroup(2);
            }
        } else {
            //유심
            viewRateCtgGroup(1);
        }

        //요금제 레이어 팝어 오픈
        var el = document.querySelector('#paymentSelect');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();

        //요금제 리스트 레이어 표현
        $("#ulIsEmpty").hide();
        $("#rateContentCategory").show();

        //요금제 그룹 확대 버튼 보기 초기화
        $('.btn-category__expand').addClass('is-active');
        $('.btn-category__list').removeClass('short');
        $('.btn-category__list').removeClass('is-active');
        $('#btnCateroryView').attr('aria-expanded','false');

        //요금제 그룹 확대 버튼 보기 표현 여부
        setTimeout(function() {
            var Width1 = $('.btn-category__inner').width();
            var Width2 = $('.btn-category__group').width();

            if (Width1 > Width2) {
                $('.btn-category__expand').removeClass('is-active')
                $('.btn-category__list').addClass('short')
            }
        },100);

        getChargelist();

    });



    $("#radPayTypeBtn").click(function(){
        if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
            var instNom = $('input[name="instNom"]:checked').val();
            if (instNom == undefined || instNom == "") {
                MCP.alert("약정기간을  선택 하시기 바랍니다. ");
                return ;
            }
        }

        var el = document.querySelector('#paymentSelect');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();

        $('.paymentPop').trigger('click');
    })

    //전체 , 추천 클릭
    $('._rateTabM1').on('click', function (){
        $('._rateTabM1').removeClass('is-active');
        $(this).addClass('is-active');
        $('#searchNm').val('');

        let bestRate =$(this).data("bestRate");
        pageInfo.pageNo = 1;
        pageInfo.tabButIndex = bestRate ;

        initQuestionDom();

        //charegListHtml();
        if (bestRate == "0") {
            //추천요금제 true
            $('.rate-content__item').hide();
            $('.rate-content__item[data-is-rate-best="true"]').show();
            $('#rdoBestRate').val(bestRate);
            $(".c-card__badge-label").removeClass("compare");
        } else if (bestRate == "1") {
            //전체요금제
            $('.rate-content__item').show();
            $('#rdoBestRate').val(bestRate);
            $(".c-card__badge-label").removeClass("compare");
        } else if (bestRate == "-2") {
            var selRateCtg = $(this).data("rateCtg");
            pageInfo.selRateCtg = selRateCtg ;
            $(".c-card__badge-label").removeClass("compare");
            $('#rateContentCategory > .rate-content__item').each(function (){
                var gropRateCtg = $(this).find(".c-checkbox--box").data("expnsnStrVal1");
                if(isEmpty(gropRateCtg)){
                    gropRateCtg = "";
                }

                if (gropRateCtg.indexOf(selRateCtg) > -1) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            })

            if ($('#rateContentCategory > .rate-content__item:visible').length <1) {
                $("#ulIsEmpty").show();
                $('#ulIsEmpty > .rate-content__item').show();
                $("#rateContentCategory").hide();
            }
        } else {
            //비교하기
            $checkBadge = $("input:checkbox[id^=chkBadgeAA_]:checked");
            var checkCnt = $checkBadge.length ;
            $(".c-card__badge-label").addClass("compare");
            if (checkCnt > 0) {
                $('.rate-content__item').each(function (){
                    var isBadge ;
                    isBadge = $(this).data("isBadge");

                    if (isBadge=="ture") {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                })
            } else {
                MCP.alert('요금제 선택 영역에서 비교하고 싶은 요금제를 담아주세요.');
                var bestRate2 = $('#rdoBestRate').val();
                if (bestRate2 == "1") {
                    $('._rateTabM1').eq(0).trigger('click');
                } else {
                    $('._rateTabM1').eq(1).trigger('click');
                }
            }
        }
    });

    $('#evntCdPrmt').on('keydown', function(e) {
        if (e.key === 'Enter' || e.keyCode === 13) {
            e.preventDefault();
            $('#evntCdPrmtBtn').click();
        }
    });

    $('#searchNm').on('keydown', function(e) {
        if (e.key === 'Enter' || e.keyCode === 13) {
            e.preventDefault();
            $('#searchRatePlan').click();
        }
    });

    $('#searchClear').on('click', function (){
        $('#searchNm').val('');
        $('#searchRatePlan').click();
    });

    $('#searchRatePlan').on('click', function(){
        let keyword = $('#searchNm').val().trim().replace(/\s+/g, '').toLowerCase();

        // 무조건 전체 탭으로 전환
        $('._rateTabM1').removeClass('is-active');
        $('._rateTabM1[data-best-rate="1"]').addClass('is-active');

        // 기타 UI 초기화
        $('#filterAllYn').val('N');
        pageInfo.pageNo = 1;
        pageInfo.tabButIndex = 1;
        $(".section-survey").hide();
        $("._survey__content2").hide();
        $('._question').hide();
        $("#ulIsEmpty").hide();
        $("#rateContentCategory").show();
        $(".btn-sort-wrap").show();
        $("#rateBtnMyrate").removeClass('is-active');

        // 전체 항목 보이기 + 필터링
        let matchCount = 0;

        $('#rateContentCategory > .rate-content__item').each(function () {
            let rateName = $(this).find('.rate-info__title strong').text().trim().replace(/\s+/g, '').toLowerCase();

            if (rateName.indexOf(keyword) !== -1) {
                $(this).show();
                matchCount++;
            } else {
                $(this).hide();
            }
        });

        // 결과 없을 경우
        if (matchCount === 0) {
            $('#ulIsEmpty').show();
            $('#rateContentCategory').hide();
        } else {
            $('#ulIsEmpty').hide();
            $('#rateContentCategory').show();
        }
    });


    var setQuestionTitle = function(type) {
        var arr =[];
        if (type == 1) {
            arr.push("<p class='c-text c-text--type5'>내게 맞는 상품찾기</p>\n");
            arr.push("<p class='c-text c-text--type7 u-fw--bold'>\n");
            arr.push("    <span class='u-co-mint'>유심요금제&nbsp;</span>고민이라면?\n");
            arr.push("</p>\n");
        } else {
            arr.push("<p class='c-text c-text--type5'>고객님께 딱 맞는</p>\n");
            arr.push("<p class='c-text c-text--type7 u-fw--bold'>\n");
            arr.push("    <span class='u-co-mint'>유심요금제&nbsp;</span>추천합니다!\n");
            arr.push("</p>\n");
        }
        $("#divQuestionTitle").html(arr.join(""));

    }
    var getQuestion = function() {
        if (!pageInfo.isGetQuestion) {
            ajaxCommon.getItem({
                id : 'rateRecommendInqrBasListAjax'
                , cache : false
                , async : true
                , url : '/m/rateRecommendInqrBasListAjax.do'
                , dataType : "json"
            } ,function(result){
                pageInfo.isGetQuestion = true;
                let inqrHtml = "";
                let returnCode = result.returnCode;
                let message = result.message;
                let inqrBasList = result.result;
                let inqrBasLength = inqrBasList.length;

                if(returnCode == "00") {
                    if(inqrBasList.length > 0) {
                        let i = 0;
                        $.each(inqrBasList, function(index, value) {
                            let v_val = 1;
                            let v_rateRecommendInqrSbst = ajaxCommon.isNullNvl(value.rateRecommendInqrSbst, "");
                            let v_ansSbst1 = ajaxCommon.isNullNvl(value.ansSbst1, "");
                            let v_ansSbst2 = ajaxCommon.isNullNvl(value.ansSbst2, "");
                            let v_ansSbst3 = ajaxCommon.isNullNvl(value.ansSbst3, "");
                            let v_ansSbst4 = ajaxCommon.isNullNvl(value.ansSbst4, "");

                            var arr =[];
                            arr.push("<div class='u-fs-30 u-co-mint u-fw--bold'>\n");

                            arr.push("<div class='u-po-rel' >\n");
                            arr.push("    <span class='u-co-sub-4'>Q"+(index+1)+".</span>\n");
                            arr.push("    <span class='u-fs-14 u-co-black u-fw--medium' id='radio_header_"+index+"'>"+v_rateRecommendInqrSbst+"</span>\n");
                            arr.push("</div >\n");

                            arr.push("<div class='survey__inner'>\n");
                            arr.push("    <div class='c-form-wrap'>\n");
                            arr.push("        <div class='c-form-group' role='group' aira-labelledby='radio_header_"+index+"'>\n");
                            if(v_ansSbst1 != "") {
                                arr.push("        <input class='c-radio c-radio--button' id='radQuestion_"+i+"' type='radio' name='cho_"+index+"' value='"+v_val+"'  />\n");
                                arr.push("        <label class='c-label' for='radQuestion_"+i+"'>"+v_ansSbst1+"</label>\n");
                                i += 1;
                                v_val += 1;
                            }
                            if(v_ansSbst2 != "") {
                                arr.push("        <input class='c-radio c-radio--button' id='radQuestion_"+i+"' type='radio' name='cho_"+index+"' value='"+v_val+"'  />\n");
                                arr.push("        <label class='c-label' for='radQuestion_"+i+"'>"+v_ansSbst2+"</label>\n");
                                i += 1;
                                v_val += 1;
                            }
                            if(v_ansSbst3 != "") {
                                arr.push("        <input class='c-radio c-radio--button' id='radQuestion_"+i+"' type='radio' name='cho_"+index+"' value='"+v_val+"'  />\n");
                                arr.push("        <label class='c-label' for='radQuestion_"+i+"'>"+v_ansSbst3+"</label>\n");
                                i += 1;
                                v_val += 1;
                            }
                            if(v_ansSbst4 != "") {
                                arr.push("        <input class='c-radio c-radio--button' id='radQuestion_"+i+"' type='radio' name='cho_"+index+"' value='"+v_val+"'  />\n");
                                arr.push("        <label class='c-label' for='radQuestion_"+i+"'>"+v_ansSbst4+"</label>\n");
                                i += 1;
                                v_val += 1;
                            }
                            arr.push("        </div>\n");
                            arr.push("    </div>\n");
                            arr.push("</div>\n");
                            arr.push("</div>\n");

                            if(index == 2) {
                                return false;
                            }
                            $("._survey__content2").eq(index).html(arr.join(""));
                        });
                    }

                } else {
                    MCP.alert(message);
                }
            });
        }
        $("._survey__content2").eq(0).show();
    }

    var getQuestionResult = function(questionCode) {
        var varData = ajaxCommon.getSerializedData({
            ansNo : questionCode
        });

        ajaxCommon.getItem({
                id : 'rateRecommendInqrRelAjax'
                , cache : false
                , async : true
                , url : '/m/rateRecommendInqrRelAjax.do'
                , data : varData
                , dataType : "json"
            }
            ,function(result){

                let inqrResultList = result.result;
                let rateList = pageInfo.chargelist;
                let cardCnt = 0;
                $('#rateQuestionResult').empty();


                $.each(inqrResultList, function(index, temResultObj) {
                    for (var i = 0; i < rateList.length; i++) {
                        if (temResultObj.rateCd == rateList[i].rateCd) {
                            $('#rateQuestionResult').append(getRowTemplateQuResult(rateList[i],cardCnt) );
                            $('#cbResult_'+cardCnt).data(rateList[i]);
                            cardCnt++;
                            break;
                        }
                    }
                });
                setQuestionTitle(2);
                $('._question').show();


            });
    }

    $("#rateBtnMyrate").click(function(){
        $(".btn-sort-wrap").hide();  //정렬 기준 숨김
        $('._rateTabM1').removeClass('is-active');
        $(this).addClass('is-active');

        $(".survey").show();

        $("#ulIsEmpty").hide();
        $("#rateContentCategory").hide();
        $('._question').hide();
        $("._survey__content2").hide();
        setQuestionTitle(1);
        getQuestion();
        playAnimation(0);

        $("input:radio[id^=radQuestion_]:checked").each(function () {
            $(this).removeProp('checked');
        });

        $(".section-survey").show();

        //$(".survey__content").hide();
        //$(".section-survey").show();

    });



    $("#btnRetryMyrat").click(function(){
        $('#rateBtnMyrate').trigger('click');
    })

    //내게 맞는 요금제 선택
    $(document).on('click', 'input:radio[id^=radQuestion_]', function () {
        $("._survey__content2").hide();
        var checkVal = [];
        $("input:radio[id^=radQuestion_]:checked").each(function () {
            checkVal.push($(this).val());
        });
        $("._survey__content2").eq(checkVal.length).show();

        if (checkVal.length == 2) {

            //console.log("checkVal===>"+ checkVal.join(""));
            getQuestionResult(checkVal.join(""));
            playAnimation(1);
        }
    });

    $(document).on('click','._rateDetailPop',function() {
        var rateAdsvcGdncSeq = $(this).attr("rateAdsvcGdncSeq");
        var rateAdsvcCd = $(this).attr("rateAdsvcCd");

        if (rateAdsvcGdncSeq == "0") {
            MCP.alert("해당 요금제의 상세정보는 준비중입니다.");
        } else {
            openPage('pullPopup', '/m/rate/rateLayer.do?rateAdsvcGdncSeq='+rateAdsvcGdncSeq+'&rateAdsvcCd='+rateAdsvcCd+'&btnDisplayYn=N','');
        }
    });

    $('.btn-sort').click(function(){
        $(this).siblings().removeClass('is-active');
        $(this).siblings().removeClass('sort');
        $(this).toggleClass('is-active');
        $(this).addClass('sort');

        var orderValue =  $(this).data("orderValue");

        if ($(this).hasClass("is-active")) {
            orderValue += "_HIGH";
        } else {
            orderValue += "_ROW";
        }

        pageInfo.orderEnum = orderValue;
        //console.log("orderValue===>" + orderValue);//VOICE_ROW
        // -- CHARGE_ROW get PayMnthChargeAmt   월 납부금액 낮은 순
        //   getXmlPayMnthAmt  XML_CHARGE_ROW
        // --  getRateNm 이름 순
        // XML_DATA  getXmlDataCnt  String c1 = StringUtils.defaultIfEmpty(o1.getMspRateMstDto().getXmlDataCnt(), "0");
        //VOICE     String c1 = StringUtils.defaultIfEmpty(o1.getMspRateMstDto().getFreeCallCnt(), "0");

        if ("CHARGE_ROW" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                var c1 = a.payMnthChargeAmt + a.payMnthAmt ;
                var c2 = b.payMnthChargeAmt + b.payMnthAmt ;

                if (c1 > c2) return 1;
                else if (c2  > c1)return -1;
                else return 0;
            });
        } else if("CHARGE_HIGH" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                var c1 = a.payMnthChargeAmt + a.payMnthAmt ;
                var c2 = b.payMnthChargeAmt + b.payMnthAmt ;

                if (c1 > c2) return -1;
                else if (c2  > c1) return 1;
                else return 0;
            });
        } else if("XML_DATA_ROW" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                var c1 = a.mspRateMstDto.xmlDataCnt ;
                var c2 = b.mspRateMstDto.xmlDataCnt ;
                if (c1 == undefined || c1 == "") { c1 = "0";    }
                if (c2 == undefined || c2 == "") { c2 = "0";    }
                var intC1 = 0 ;
                var intC2 = 0;

                if (!isNaN(c1)) {
                    intC1 =parseInt(c1);
                }

                if (!isNaN(c2)) {
                    intC2 = parseInt(c2);
                }
                if (intC1   > intC2) return 1;
                else if (intC2 > intC1) return -1;
                else {
                    var c3 = a.mspRateMstDto.xmlQosCnt ;
                    var c4 = b.mspRateMstDto.xmlQosCnt ;
                    if (c3 == undefined || c3 == "") { c3 = "0";    }
                    if (c4 == undefined || c4 == "") { c4 = "0";    }
                    var intC3 = 0 ;
                    var intC4 = 0;
                    if (!isNaN(c3)) {
                        intC3 =parseInt(c3);
                    }
                    if (!isNaN(c4)) {
                        intC4 = parseInt(c4);
                    }
                    if (intC3   > intC4) return 1;
                    else if (intC4 > intC3) return -1;
                    else return 0;
                }
            });
        } else if("XML_DATA_HIGH" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                var c1 = a.mspRateMstDto.xmlDataCnt ;
                var c2 = b.mspRateMstDto.xmlDataCnt ;
                if (c1 == undefined || c1 == "") { c1 = "0";    }
                if (c2 == undefined || c2 == "") { c2 = "0";    }
                var intC1 = 0 ;
                var intC2 = 0;

                if (!isNaN(c1)) {
                    intC1 =parseInt(c1);
                }

                if (!isNaN(c2)) {
                    intC2 = parseInt(c2);
                }
                if (intC1   > intC2) return -1;
                else if (intC2 > intC1) return 1;
                else {
                    var c3 = a.mspRateMstDto.xmlQosCnt ;
                    var c4 = b.mspRateMstDto.xmlQosCnt ;
                    if (c3 == undefined || c3 == "") { c3 = "0";    }
                    if (c4 == undefined || c4 == "") { c4 = "0";    }
                    var intC3 = 0 ;
                    var intC4 = 0;
                    if (!isNaN(c3)) {
                        intC3 =parseInt(c3);
                    }
                    if (!isNaN(c4)) {
                        intC4 = parseInt(c4);
                    }
                    if (intC3   > intC4) return -1;
                    else if (intC4 > intC3) return 1;
                    else return 0;
                }
            });
        } else if("VOICE_ROW" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                var c1 = a.mspRateMstDto.freeCallCnt ;
                var c2 = b.mspRateMstDto.freeCallCnt ;
                var intC1 = 0 ;
                var intC2 = 0;

                if (isNaN(c1)) {
                    if (c1.indexOf("기본") > -1 || c1.indexOf("무제한") > -1 ) {
                        intC1 =  999999;
                    } else {
                        intC1 =  0;
                    }
                } else {
                    intC1 =parseInt(c1);
                }

                if (isNaN(c2)) {
                    if (c2.indexOf("기본") > -1 || c2.indexOf("무제한") > -1 ) {
                        intC2 =  999999;
                    } else {
                        intC2 =  0;
                    }
                } else {
                    intC2 = parseInt(c2);
                }

                if (intC1   > intC2) return 1;
                else if (intC2 > intC1) return -1;
                else return 0;
            });
        } else if("VOICE_HIGH" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                var c1 = a.mspRateMstDto.freeCallCnt ;
                var c2 = b.mspRateMstDto.freeCallCnt ;
                var intC1 = 0 ;
                var intC2 = 0 ;

                if (isNaN(c1)) {
                    if (c1.indexOf("기본") > -1 || c1.indexOf("무제한") > -1 ) {
                        intC1 =  999999;
                    } else {
                        intC1 =  0;
                    }
                } else {
                    intC1 =parseInt(c1);
                }

                if (isNaN(c2)) {
                    if (c2.indexOf("기본") > -1 || c2.indexOf("무제한") > -1 ) {
                        intC2 =  999999;
                    } else {
                        intC2 =  0;
                    }
                }else {
                    intC2 = parseInt(c2);
                }

                if (intC1   > intC2) return -1;
                else if (intC2 > intC1) return 1;
                else return 0;
            });
        }  else if("CHARGE_NM_ROW" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                if (a.rateNm   > b.rateNm) return 1;
                else if (b.rateNm > a.rateNm) return -1;
                else return 0;
            });
        } else if("CHARGE_NM_HIGH" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                if (a.rateNm   > b.rateNm) return -1;
                else if (b.rateNm > a.rateNm) return 1;
                else return 0;
            });
        } else if("XML_CHARGE_ROW_ROW" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                if (a.xmlPayMnthAmt   > b.xmlPayMnthAmt) return 1;
                else if (b.xmlPayMnthAmt > a.xmlPayMnthAmt) return -1;
                else return 0;
            });
        } else if("XML_CHARGE_ROW_HIGH" == orderValue) {
            pageInfo.chargelist.sort(function (a, b) {
                if (a.xmlPayMnthAmt   > b.xmlPayMnthAmt) return -1;
                else if (b.xmlPayMnthAmt > a.xmlPayMnthAmt) return 1;
                else return 0;
            });
        }

        charegListView();
    });

    //비교함 담기
    $(document).on('click', 'input:checkbox[id^=chkBadgeAA_]', function () {
        var checkCnt = $("input:checkbox[id^=chkBadgeAA_]:checked").length ;
        if (checkCnt > 20) {
            MCP.alert('비교함에는 최대 20개의 요금제만 담을 수 있습니다.');
            $(this).removeProp('checked');
        } else {

            //$('#ckBox').is(':checked');
            if ($(this).is(':checked')) {
                $(this).parent().parent().data("is-badge","ture");
                //console.dir($(this).parent().parent().data());
            } else {
                checkCnt = checkCnt -1 ;
                $(this).parent().parent().data("is-badge","false");
                if(pageInfo.tabButIndex == "-1") {  //비교함
                    $(this).parent().parent().hide();
                    if($("input:checkbox[id^=chkBadgeAA_]:checked").length < 1) {
                        var bestRate = $('#rdoBestRate').val();
                        if (bestRate == "1") {
                            $('._rateTabM1').eq(0).trigger('click');
                        } else {
                            $('._rateTabM1').eq(1).trigger('click');
                        }

                    }
                }
            }

        }
        if (checkCnt < 0) {
            checkCnt = 0;
        }

        pageInfo.badgeList =[];
        $("input:checkbox[id^=chkBadgeAA_]:checked").each(function (){
            var thisValue = $(this).val()+"_" + $(this).data("rateAdsvcCd");
            pageInfo.badgeList.push(thisValue);
        })
        $("#rateBtnCompar .rate-btn__cnt").html($("input:checkbox[id^=chkBadgeAA_]:checked").length);
    });

    $(document).on('click','[name=selectRate]',function() {
        var thisData = $(this).data();
        //console.dir(thisData);
        $('#paymentSelectBtn').removeClass('is-disabled');
    });


    $('#btnCateroryView').click(function(){
        $(this).parent().toggleClass('is-active')
        var hasClass = $('.btn-category__list').hasClass('is-active');
        if(hasClass){
            $(this).attr('aria-expanded','true');
        }else {
            $(this).attr('aria-expanded','false');
        }
    });

    $("input:radio[name=ktTripleDcAmt]").change(function(){
        selectUsimBasJoinPriceAjax(dummyRateInfo,false);
    });

    /// ON LOAD init
    lottieAnimation();
    playAnimation(0);

    //서식지 상세 배너 조회
    displayO001BannerList();



    pageInfo.dataTypeVal = $("[name=dataType]:checked").val();
    pageInfo.prdtIdVal = $("[name=prdtId]:checked").val();
    pageInfo.operTypeVal = $("[name=operType]:checked").val();
    pageInfo.agrmTrmVal = $("[name=agrmTrm]:checked").val();



    //셀프개통 가능 여부 확인
    ajaxCommon.getItemNoLoading({
        id:'isSimpleApply'
        ,cache:false
        ,url:'/appForm/isSimpleApplyAjax.do'
        ,data: ""
        ,dataType:"json"
        ,async:false
    },function(jsonObj){
        pageInfo.simpleApplyObj = jsonObj ;
    });

    if (!pageInfo.simpleApplyObj.IsMacTime && !pageInfo.simpleApplyObj.IsMnpTime) {
        $('#radOpenType1').attr('disabled', 'disabled');
    }


    if(document.referrer.indexOf('appSimpleInfo.do') > -1 || document.referrer.indexOf('openMarketInfo.do') > -1 || document.referrer.indexOf('storeInfo.do') > -1
        || document.referrer.indexOf('emart24.do') > -1 || document.referrer.indexOf('cu.do') > -1 || document.referrer.indexOf('cspace.do') > -1
        || document.referrer.indexOf('ministop.do') > -1 || document.referrer.indexOf('7-11.do') > -1 || document.referrer.indexOf('gs25.do') > -1
        || document.referrer.indexOf('storyway.do') > -1	|| $('#initOnOffType').val() == '7'){
        $('#cstmrType1').trigger('click');

        if ( $('#initOnOffType').val() == '3' ) {
            $('#radOpenType2').trigger('click');
        } else {
            if (pageInfo.simpleApplyObj.IsMacTime || pageInfo.simpleApplyObj.IsMnpTime) {
                $('#radOpenType1').trigger('click');
            } else {
                $('#radOpenType2').trigger('click');
            }

        }
    } else {
        $('#cstmrType1').trigger('click');
        $('#radOpenType2').trigger('click');
        confirmChk = 'Y';
    }

    //eSIM
    if ('10' == $('#prdtIndCd').val() || '11' == $('#prdtIndCd').val() ) {
        var radOpenSelf = $('#radOpenSelf').val();
        var radOpenOff  = $('#radOpenOff').val();
        var ePhoneRstCd = $('#ePhoneRstCd').val();

        if ("Y" !=  radOpenSelf) {
            $('#radOpenType1').attr('disabled', 'disabled');
        } else {
            if ('11' == $('#prdtIndCd').val() && "S" != ePhoneRstCd) {
                $('#radOpenType1').attr('disabled', 'disabled');
            } else {
                $('#cstmrType1').trigger('click');
                if (pageInfo.simpleApplyObj.IsMacTime || pageInfo.simpleApplyObj.IsMacTime) {
                    $('#radOpenType1').trigger('click');
                } else {
                    $('#radOpenType2').trigger('click');
                }
            }
        }

        if ("Y" !=  radOpenOff) {
            $('#radOpenType2').attr('disabled', 'disabled');
        }
    }

    if($('#initDataType').val() != undefined && $('#initDataType').val() != ''){
        $('input[name=dataType]').each(function (){
            if($(this).val() == $('#initDataType').val()){
                $(this).trigger('click');
            }
        });
    }


    if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){	//유심
        if($('#prodCtgType').val() == '03'){
            $('#radOpenType1').attr('disabled', 'disabled');
            $('#radOpenType2').trigger('click');
            $('#cstmrType1').trigger('click');
        }
        //페이지 로딩시 ajax 트리거
        usimInfoAjax(true);

        //요금제 정보
        getJoinUsimPrice();

    } else {	//휴대폰
        if($('input[name=instNom]:checked').val() != undefined){
            setAgrmTrm();
            if($('input[name=instNom]:checked').val() == '0'){
                $('input[name=saleTy]').removeProp('checked');
                $('input[name=saleTy]').attr('disabled', 'disabled');

            }else{
                $('input[name=saleTy]').removeAttr('disabled');
                $('#chkDiscountType1').prop('checked', 'checked');
            }
        }

        if($('select[name=hndsetModelId] option:selected').attr('sdoutYn') == 'Y'){
            MCP.alert('선택하신 용량/색상은 품절 되었습니다.', function (){
                location.href= '/m/product/phone/phoneList.do';
            });

        }
    }


    if($('#initDataType').val() != ''){

    } else {
        if($('#prodId').val() != '' && $('#prodCtgType').val() == '03'){
            $(':radio[name=dataType]').attr('disabled', 'disabled');
            $(':radio[name=dataType]').each(function (){
                if($('#prodCtgId').val() == PROD_CTG_ID.LTE){
                    $(':radio[name=dataType]:eq(0)').removeAttr('disabled', '');
                    $('input[name=dataType]:eq(0)').trigger('click');
                }else if($('#prodCtgId').val() == PROD_CTG_ID.ID_5G){
                    $(':radio[name=dataType]:eq(2)').removeAttr('disabled', '');
                    $('input[name=dataType]:eq(2)').trigger('click');
                }else{
                    $(':radio[name=dataType]:eq(1)').removeAttr('disabled', '');
                    $('input[name=dataType]:eq(1)').trigger('click');
                }
            })
        }else{
            if ($(":radio[name=dataType]:checked").length == 0) {
                $('input[name=dataType]:eq(0)').trigger('click');
            }
        }
    }


    if($('#initDataType').val() != undefined && $('#initDataType').val() != ''){
        $('input[name=dataType]').each(function (){
            if($(this).val() == $('#initDataType').val()){
                $(this).trigger('click');
            }
        });
    }

    if ($(":radio[name=dataType]:checked").length == 0) {
        $('input[name=dataType]:eq(0)').trigger('click');
    }


    if($('#initRateCd').val() != ''){
        initRateCdChoice = 'Y';
        //단말기 유심 여부
        // if(!$('input[name=dataType]').is(':visible')){
        getChargelist();
        // }
    }

    /* 공유하기 URL, requestKey  로 들어올때*/
    if($('#shareYn').val() == 'Y' || $('#requestKey').val() != ''){
        var onOffType = $('#initOnOffType').val();
        var operType = $('#initOperType').val();
        var modelId	= $('#initPrdtId').val();
        var modelMonthly = $('#initAgrmTrm').val();
        var prdtSctnCd = $('#initDataType').val();
        var rateCd = $('#initRateCd').val();

        $('input[name=dataType]').each(function (){
            if($(this).val() == prdtSctnCd){
                $(this).trigger('click');
            }
        });

        $('#cstmrType1').trigger('click');

        if(onOffType == '7'){
            $('#radOpenType1').trigger('click');
        }else if(onOffType == '3'){
            $('#radOpenType2').trigger('click');
        }

        $('input[name=operType]').each(function (){
            if($(this).val() == operType){
                $(this).trigger('click');
            }
        });

        $('input[name=prdtId]').each(function (){
            if($(this).val() == modelId){
                $(this).trigger('click');
            }
        });

        $('input[name=agrmTrm]').each(function (){
            if($(this).val() == modelMonthly){
                $(this).trigger('click');
            }
        });
    }

    if($('#initIndcOdrg').val() == '-1' || $('#initRateCd').val() != ''){
        $('#rdoBestRate').val('1');
        $('._rateTabM1').eq(0).addClass('is-active');
        $('._rateTabM1').eq(1).removeClass('is-active');
        pageInfo.initBestRate = "1";
    }else{
        $('#rdoBestRate').val('0');
        $('._rateTabM1').eq(0).removeClass('is-active');
        $('._rateTabM1').eq(1).addClass('is-active');
        pageInfo.initBestRate = "0";
    }



    if($('input[name=radOpenType]:checked').attr('id') == 'radOpenType1'){
        if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
            $('input[name=dataType]').each(function (){
                if($(this).val() == '3G'){
                    $(this).removeProp('checked').attr('disabled', 'disabled');
                }
            });
        }
    }
    nextStepBtnChk();

    /* 요금제 혜택 아코디언 */
    $(document)
    .off('click', '.c-accordion__item > .c-accordion__head .c-accordion__button')
    .on('click', '.c-accordion__item > .c-accordion__head .c-accordion__button', function() {
        var $btn = $(this);
        var $panel = $btn.closest('.c-accordion__item').children('.c-accordion__panel');
        var isActive = $btn.hasClass('is-active');

        $btn.toggleClass('is-active', !isActive);
        $btn.attr('aria-expanded', !isActive);

        $panel.stop()[isActive ? 'slideUp' : 'slideDown']();
        $panel.attr('aria-hidden', isActive);
    });

})

var setAgrmTrm = function (){
    if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
        //단말기 경우....

        $("#agrmTrm option").remove();

        //신규?? 결제 여부 ??
        if($('input[name=operType]:checked').val() == 'NAC3' && pageInfo.settlYn =="Y"  ) {
            $("#agrmTrm").append("<option label='일시납부' value='0'>일시납부</option>");
        } else {

            //무약정 여부
            let instNomVal =  $('input[name=instNom]:checked').val() ;
            if (instNomVal == '0') {
                pageInfo.agrmTrmList.forEach( agrmTrmListVal => {
                    $('input[name=instNom]').each(function (x, obj){
                        if (agrmTrmListVal == $(obj).val()) {
                            $("#agrmTrm").append("<option value='"+$(obj).val()+"'>"+$(obj).val()+"개월</option>");
                        }

                    });
                });
                if (pageInfo.asAgrmTrmVal !=null && "" == pageInfo.asAgrmTrmVal) {
                    $('#agrmTrm').val('24');
                } else {
                    $('#agrmTrm').val(pageInfo.asAgrmTrmVal);
                }
                $('#discountTxtDiv').hide();
            } else {
                $("#agrmTrm").append("<option value='"+instNomVal+"'>"+instNomVal+"개월</option>");
                $('#discountTxtDiv').show();
            }

        }
    }

}

var setMoreBtn = function (){
    var totalCnt = Number($('#cprtCardSize').val());
    var totalPage = parseInt(totalCnt / pageInfo.cprtPageSize);
    if(totalCnt % pageInfo.cprtPageSize){
        totalPage++;
    }

    var dispCnt = 0;
    for(var i = 0; i < totalCnt; i++){
        if(i < pageInfo.cprtPageNo * pageInfo.cprtPageSize){
            $('#cprtCard' + i).show();
            dispCnt = i + 1;
        }
    }

    $('#moreBtnTxt').text(numberWithCommas(dispCnt) + ' / ' + numberWithCommas(totalCnt));

    if(pageInfo.cprtPageNo == totalPage){
        $('#moreBtnDiv').hide();
    }else{
        $('#moreBtnDiv').show();
    }
}

var setCardMoreBtn = function (){
    var cardCnt = Number($('#cardTotalCnt').val());
    for(var i = 0; i < cardCnt; i++){
        $('#cardResult' + i).show();
    }

}

var wrap = document.querySelector('.ly-wrap');
wrap.classList.add('use-bs');

// 진행중 이벤트 스와이퍼
var swiperCardBanner = new Swiper("#swiperCardBanner", {
    spaceBetween : 10,
    observer : true,
    observeParents : true,
});

var nextStep = function (){
    if($('input[name=radOpenType]:checked').attr('id') == 'radOpenType1'){
        fnSelfClick();
    }else{
        goAppFrom();
    }
}

var getShareUrl = function (){

    if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
        var operType			= $('input[name=operType]:checked').val() != undefined ? $('input[name=operType]:checked').val() : '';	//가입유형(번이,신규)
        var prdtSctnCd			= $('input[name=dataType]:checked').val() != undefined ? $('input[name=dataType]:checked').val() : '';	//서비스유형(LTE,3G,5G)
        var modelId				= $('input[name=prdtId]:checked').val() != undefined ? $('input[name=prdtId]:checked').val() : '';		//모델코드(ex:K7006037)
        var modelMonthly		= $('input[name=agrmTrm]:checked').val() != undefined ? $('input[name=agrmTrm]:checked').val() : '';	//약정유형
        var rateCd				= $('#socCode').val() != undefined ? $('#socCode').val() : '';											//요금제코드(ex:PL20B9416)
        var onOffType			= $('input[name=radOpenType]:checked').attr('id') == 'radOpenType1' ? '7' : '3';
        var prodId				= '';
        var shareUrl = location.pathname + '?shareYn=Y&operType=' + operType + '&prdtSctnCd=' + prdtSctnCd + '&modelId=' + modelId + '&modelMonthly=' + modelMonthly + '&rateCd=' + rateCd + '&onOffType=' + onOffType;
        if($('#prodCtgType').val() == '03'){
            onOffType = '3';
            prodId = $('#prodId').val() != undefined ? $('#prodId').val() : '';
            modelId			= $('select[name="hndsetModelId"]').val() != undefined ? $('select[name="hndsetModelId"]').val() : '';

            shareUrl = location.pathname + '?shareYn=Y&prodId=' + prodId + '&operType=' + operType + '&modelId=' + modelId + '&prdtSctnCd=' + prdtSctnCd + '&modelId=' + modelId + '&modelMonthly=' + modelMonthly + '&rateCd=' + rateCd + '&onOffType=' + onOffType;
        }

        //eSIM
        var usimKindsCd = $('#prdtIndCd').val();
        if(usimKindsCd == '10' ) {
            shareUrl = location.pathname + '?shareYn=Y&prdtIndCd=10&operType=' + operType + '&prdtSctnCd=' + prdtSctnCd + '&modelId=' + modelId + '&modelMonthly=' + modelMonthly + '&rateCd=' + rateCd + '&onOffType=' + onOffType;
        }
        return shareUrl;

        return shareUrl;
    }else{
        var prodId			= $('#prodId').val() != undefined ? $('#prodId').val() : '';
        var operType		= $('input[name=operType]:checked').val() != undefined ? $('input[name=operType]:checked').val() : '';	//가입유형(번이,신규)
        var modelId			= $('select[name="hndsetModelId"]').val() != undefined ? $('select[name="hndsetModelId"]').val() : '';	//모델코드(ex:K7006037) prdtId ==> modelId
        var modelMonthly	= $('input[name="instNom"]:checked').val()	!= undefined ? $('input[name="instNom"]:checked').val() : ''; //약정유형
        var sprtTp			= $("input[name='sprtTp']:checked").val() != undefined ? $('input[name="sprtTp"]:checked').val() : '';								//지원금유형 (단말할인:KD , 요금할인:PM)
        var rateCd			= $('#socCode').val() != undefined ? $('#socCode').val() : '';											//요금제코드(ex:PL20B9416)
        var enggMnthCnt		= $('#agrmTrm').val() != undefined ? $('#agrmTrm').val() : '';
        var onOffType		= '3';						//구분코드 7 모바일(셀프개통) 3 모바일
        var shareUrl = location.pathname + '?shareYn=Y&prodId=' + prodId + '&operType=' + operType + '&modelId=' + modelId + '&modelMonthly=' + modelMonthly + '&sprtTp=' + sprtTp + '&enggMnthCnt=' + enggMnthCnt + '&rateCd=' + rateCd + '&onOffType=' + onOffType;

        return shareUrl;
    }
}

// facebook 공유하기
function facebookShare(){
    //facebook_share(document.location.href);
    $("#meta_og_url").attr("content", location.origin + getShareUrl());
    facebook_share(getShareUrl());
}
// kakaostory 공유하기
function kakaoShare(){
    //kakaostory_share(document.location.href);
    //kakao_share($('title').text(), getShareUrl());
    $("#meta_og_url").attr("content", location.origin + getShareUrl());
    kakao_share('/resources/images/portal/common/share.png', getShareUrl(), getShareUrl());
}
// Line 공유하기
function lineShare(){
    //line_share(document.location.href);
    //line_share(getShareUrl());
    $("#meta_og_url").attr("content", location.origin + getShareUrl());
    line_share($('title').text(), getShareUrl());
}

function copyUrl(){
    var url = location.origin + getShareUrl();
    var input = document.createElement("input");
    document.body.appendChild(input);
    input.value = url;
    input.select();
    document.execCommand("copy");
    document.body.removeChild(input);
    MCP.alert('URL이 복사되었습니다.');
}


    // 서식지 설계 배너 조회 (O001)
    var displayO001BannerList = function() {
        var varData = ajaxCommon.getSerializedData({
            bannCtg : 'O001'
        });

        ajaxCommon.getItem({
                id : 'getO001BannerListAjax'
                , cache : false
                , async : false
                , url : '/m/bannerListAjax.do'
                , data : varData
                , dataType : "json"
            }
            ,function(result){
                let bannerHtml = "";
                let returnCode = result.returnCode;
                let message = result.returnCode;
                let bannerList = result.result;

                if(returnCode == "00") {
                    if(bannerList.length > 0) {
                        $.each(bannerList, function(index, value) {
                            let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                            let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                            let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                            let v_bannDesc = ajaxCommon.isNullNvl(value.bannDesc, "");
                            let v_bannApdDesc = ajaxCommon.isNullNvl(value.bannApdDesc, "");
                            let v_bannBgColor = ajaxCommon.isNullNvl(value.bannBgColor, "");
                            let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                            let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                            let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");

                            let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");
                            let v_linkUrlAdr = ajaxCommon.isNullNvl(value.linkUrlAdr, "");

                            if((v_linkTarget == 'Y' || v_linkTarget == 'P' ) && v_linkUrlAdr != ''){
                                if($('#platFormCd').val() == 'A'){
                                    bannerHtml += '<div class="swiper-slide" onclick="javascript:insertBannAccess(\'' + v_bannSeq + '\',\'' + v_bannCtg + '\');appOutSideOpen(\'' + v_linkUrlAdr + '\');">';
                                }else{
                                    bannerHtml += '<div class="swiper-slide" onclick="javascript:insertBannAccess(\'' + v_bannSeq + '\',\'' + v_bannCtg + '\');window.open(\'about:blank\').location.href=\'' + v_linkUrlAdr + '\'";>';
                                }
                            }else if(v_linkTarget == 'N' && v_linkUrlAdr != ''){
                                bannerHtml += '<div class="swiper-slide" onclick="javascript:insertBannAccess(\'' + v_bannSeq + '\',\'' + v_bannCtg + '\');location.href=\'' + v_linkUrlAdr + '\'";>';
                            }else{
                                bannerHtml += '<div class="swiper-slide" onclick="javascript:insertBannAccess(\'' + v_bannSeq + '\',\'' + v_bannCtg + '\');>';
                            }

                            bannerHtml += '    		<button class="swiper-event-banner__button">';
                            bannerHtml += '    			<div class="swiper-event-banner__item">';
                            if($('#serverName').val() == 'LOCAL'){
                                bannerHtml += '    				<img src="/resources/images/mobile/content/img_event_banner1.png" alt="' + v_bannNm + '">';
                            }else{
                                bannerHtml += '    				<img src="' + v_bannImg + '" alt="' + v_bannNm + '">';
                            }
                            bannerHtml += '        		</div>';
                            bannerHtml += '    		</button>';
                            bannerHtml += '		</div>';

                            if(index == 19) {
                                return false;
                            }
                        });

                        $("#O001").html(bannerHtml);
                    }else{
                        $('.top-sticky-banner').hide();
                    }
                } else {
                    MCP.alert(message);
                }
            });
    }

    var getGiftList = function (buyType){
        var onOffType = (!$('input[name=radOpenType]:checked').val()) ? '' : $('input[name=radOpenType]:checked').val();
        var operType = (!$('input[name=operType]:checked').val()) ? '' : $('input[name=operType]:checked').val();
        var reqBuyType = buyType;
        var agrmTrm = (!$('#agrmTrm').val()) ? '' : $('#agrmTrm').val();
        if(agrmTrm == ''){
            agrmTrm = (!$(':radio[name="agrmTrm"]:checked').val()) ?  '' : $(':radio[name="agrmTrm"]:checked').val();
        }
        var rateCd = (!$('#socCode').val()) ? '' : $('#socCode').val();
        var orgnId = (!$('#initOrgnId').val()) ? '1100011741' : $('#initOrgnId').val();
        if($('#prodCtgType').val() == '03'){
            orgnId = 'V000019481';	//자급제
        }
        var modelId = '';
        if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
            modelId = (!$('input[name=prdtId]:checked').val()) ? '' : $('input[name=prdtId]:checked').val();
        } else {
            modelId = (!$('select[name="hndsetModelId"]').val()) ? '' : $('select[name="hndsetModelId"]').val();
        }
        if(onOffType == '' || operType == '' || reqBuyType == '' || agrmTrm == '' || rateCd == '' || modelId == '') { return false; }
        if(rateCd == $("#evntCdPrmtSoc").val() && $("#isEvntCdPrmtAuth").val() == "1") return false;
        var varData = ajaxCommon.getSerializedData({
            onOffType : onOffType
            , operType : operType
            , reqBuyType : reqBuyType
            , agrmTrm : agrmTrm
            , rateCd : rateCd
            , orgnId : orgnId
            , modelId : modelId
        });

        ajaxCommon.getItem({
                id : 'getGiftList'
                , cache : false
                , async : false
                , url : '/benefit/giftBasListWithEvntCdAjax.do'
                , data : varData
                , dataType : "json"
            }
            ,function(jsonObj){

              var $divGift = $('#divGift');
              var $divGift2 = $('#divGift2');
              $divGift2.empty();

              // 이벤트 코드 프로모션 처리
              if(rateCd != $("#evntCdPrmtSoc").val()){
                  initEvntCdPrmt();
                  $("#evntCdPrmtSoc").val(rateCd);
                  if(jsonObj.evntCdPrmtYn == "Y") $("#evntCdPrmtWrap").show();
              }

              // 혜택요약 관련 정보
              var giftPrmtList = jsonObj.rateGiftPrmtList;
              var giftPrmtWireList = jsonObj.wireRateGiftPrmtList;
              var giftPrmtFreeList = jsonObj.freeRateGiftPrmtList;
              var mainGiftPrmtList = jsonObj.mainGiftPrmtList;

              //기존 혜택요약(모바일)
              var giftTotalPrice = jsonObj.totalPrice;

              //인터넷 혜택요약
              var totalWirePrice = jsonObj.totalWirePrice || 0;

              //무료 혜택요약
              var totalFreeCount = jsonObj.totalFreeCount || 0;

              // 요금제 혜택요약 미존재
              if(mainGiftPrmtList == null || mainGiftPrmtList.length <= 0){
                  $divGift.hide();
                  //$('.giftInfoListHr').hide();
                  //$('.giftInfoTxt').hide();
                  return;
              }

              // 요금제 혜택요약 존재
              var innerHtml = "";
              var totalMainPrice = jsonObj.totalMainPrice;
              var totalMainCount = jsonObj.totalMainCount;
              var fallbackUrl = "/resources/images/portal/content/img_phone_noimage.png";

              innerHtml += `<div id="eventCdDiv" style="display: none;">\n`
              innerHtml += `<p>이벤트 코드 <span class="u-co-red">"<em id="eventCdName"></em>"</span>이/가 적용되었습니다.</p>\n`;
              innerHtml +=`<p class="u-fs-18 u-fw--bold" id="eventExposedText"></p>\n`;
              innerHtml +=`<hr class="c-hr c-hr--type2 c-hr c-hr--type2 u-mt--16 u-mb--16">\n`;
              innerHtml += `</div>\n`

              if(0 < totalMainPrice){
                  innerHtml += `<p class="summary-badge-list__title">최대 <span class="u-co-sub-4"><em>${totalMainPrice / 10000}</em>만원</span> 가입 혜택</p>\n`;
              }else{
                  innerHtml += `<p class="summary-badge-list__title">최대 <span class="u-co-sub-4"><em>${totalMainCount}</em>개</span> 가입 혜택</p>\n`;
              }

              innerHtml += '<div class="c-accordion--summary sub-category">\n';
              innerHtml += '  <div class="c-accordion__box">\n';

              // 모바일 혜택
              if (giftPrmtList.length > 0) {
                  innerHtml += '                <div class="c-accordion__item mobile">\n';
                  innerHtml += '                  <div class="c-accordion__head">\n';
                  innerHtml += '                    <strong class="c-accordion__title">\n';
                  innerHtml += `                      <p><span class="rate-banner__benefit-label"><em>모바일 혜택</em></span>최대 <em>${giftTotalPrice / 10000}</em>만원</p>\n`;
                  innerHtml += '                    </strong>\n';
                  innerHtml += '                    <button class="c-accordion__button is-active" type="button" aria-expanded="true">\n';
                  innerHtml += '                      <span class="c-hidden">펼쳐보기</span>\n';
                  innerHtml += '                    </button>\n';
                  innerHtml += '                  </div>\n';
                  innerHtml += '                  <div class="c-accordion__panel expand" aria-hidden="false" style="display: block;">\n';
                  innerHtml += '                    <div class="c-accordion__inside">\n';
                  innerHtml += '                      <ul class="summary-badge-list">\n';

                  giftPrmtList.forEach(item => {
                      const popupUrlMo = item.popupUrlMo || '';
                      innerHtml += '                        <li class="summary-badge-list__item">\n';
                      innerHtml += '                          <div class="summary-badge-list__item-img">\n';
                      innerHtml += `                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`;
                      innerHtml += '                          </div>\n';
                      if (popupUrlMo) {
                          innerHtml += `    					<p onclick="event.stopPropagation();window.open('${popupUrlMo}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`;
                      } else {
                          innerHtml += `    					<p>${item.giftText}</p>\n`;
                      }
                      innerHtml += '                        </li>\n';
                  });

                  innerHtml += '                      </ul>\n';
                  innerHtml += '                    </div>\n';
                  innerHtml += '                  </div>\n';
                  innerHtml += '                </div>\n';
              }

              // 인터넷 혜택
              if (giftPrmtWireList.length > 0) {
                  innerHtml += '                <div class="c-accordion__item">\n';
                  innerHtml += '                  <div class="c-accordion__head">\n';
                  innerHtml += '                    <strong class="c-accordion__title">\n';
                  if (totalWirePrice > 0) {
                      innerHtml += `                	<p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${totalWirePrice / 10000}</em>만원</p>\n`;
                  } else {
                      innerHtml += `                	<p><span class="rate-banner__benefit-label"><em>인터넷 혜택</em></span>최대 <em>${giftPrmtWireList.length}</em>개</p>\n`;
                  }
                  innerHtml += '                    </strong>\n';
                  innerHtml += '                    <button class="c-accordion__button" type="button" aria-expanded="false">\n';
                  innerHtml += '                      <span class="c-hidden">펼쳐보기</span>\n';
                  innerHtml += '                    </button>\n';
                  innerHtml += '                  </div>\n';
                  innerHtml += '                  <div class="c-accordion__panel expand" aria-hidden="true">\n';
                  innerHtml += '                    <div class="c-accordion__inside">\n';
                  innerHtml += '                      <ul class="summary-badge-list">\n';

                  giftPrmtWireList.forEach(item => {
                      const popupUrlMo = item.popupUrlMo || '';
                      innerHtml += '                        <li class="summary-badge-list__item">\n';
                      innerHtml += '                          <div class="summary-badge-list__item-img">\n';
                      innerHtml += `                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`;
                      innerHtml += '                          </div>\n';
                      if (popupUrlMo) {
                          innerHtml += `    					<p onclick="event.stopPropagation();window.open('${popupUrlMo}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`;
                      } else {
                          innerHtml += `    					<p>${item.giftText}</p>\n`;
                      }
                      innerHtml += '                        </li>\n';
                  });

                  innerHtml += '                      </ul>\n';
                  innerHtml += '                    </div>\n';
                  innerHtml += '                  </div>\n';
                  innerHtml += '                </div>\n';
              }

              // 무료 혜택
              if (giftPrmtFreeList.length > 0) {
                  innerHtml += '                <div class="c-accordion__item">\n';
                  innerHtml += '                  <div class="c-accordion__head">\n';
                  innerHtml += '                    <strong class="c-accordion__title">\n';
                  innerHtml += '                      <p><span class="rate-banner__benefit-label"><em>&thinsp;&nbsp;&nbsp;추가 혜택&thinsp;&nbsp;</em></span><em>엠모바일 고객 한정</em></p>\n';
                  innerHtml += '                    </strong>\n';
                  innerHtml += '                    <button class="c-accordion__button" type="button" aria-expanded="false">\n';
                  innerHtml += '                      <span class="c-hidden">펼쳐보기</span>\n';
                  innerHtml += '                    </button>\n';
                  innerHtml += '                  </div>\n';
                  innerHtml += '                  <div class="c-accordion__panel expand" aria-hidden="true">\n';
                  innerHtml += '                    <div class="c-accordion__inside">\n';
                  innerHtml += '                      <ul class="summary-badge-list">\n';

                  giftPrmtFreeList.forEach(item => {
                      const popupUrlMo = item.popupUrlMo || '';
                      innerHtml += '                        <li class="summary-badge-list__item">\n';
                      innerHtml += '                          <div class="summary-badge-list__item-img">\n';
                      innerHtml += `                            <img src="${item.mainPrdtImgUrl}" alt="${item.mainPrdtNm}" onerror="this.src='${fallbackUrl}';">\n`;
                      innerHtml += '                          </div>\n';
                      if (popupUrlMo) {
                          innerHtml += `    					<p onclick="event.stopPropagation();window.open('${popupUrlMo}', '_blank');" style="cursor: pointer">${item.giftText}</p>\n`;
                      } else {
                          innerHtml += `    					<p>${item.giftText}</p>\n`;
                      }
                      innerHtml += '                        </li>\n';
                  });

                  innerHtml += '                      </ul>\n';
                  innerHtml += '                    </div>\n';
                  innerHtml += '                  </div>\n';
                  innerHtml += '                </div>\n';
              }

              innerHtml += '  </div>\n';
              innerHtml += '</div>\n';

              $divGift2.html(innerHtml);
              $divGift.show();
              //$('.giftInfoListHr').show();
              //$('.giftInfoTxt').show();
              MCP.init();
            });
    }

    //2026.03 제휴카드 안내사항 확인
    $("#cautionCheck").click(function() {
        if(!$('#cautionFlag').is(':checked')){
            MCP.alert("유의사항 이해여부 확인 바랍니다.");
            return false;
        }else {
            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.close();
        }
    });

    var cprtPriceSet = function (){
        //2026.03 제휴카드 선택 시 안내사항 팝업
        if(!$('#chkCardX').is(':checked')){
            $("._simpleDialogButton").hide();
            $("#cautionFlag").prop("checked", false);
            $("#divCheckCaution").show();

            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        }

        if($('#chkCardX').is(':checked')){
            $('#cprtCardPrice').html('0 원');
        }else{
            $('input[name="cprtCardDcInfo' + $('input[name=chkCard]:checked').val() + '"]').each(function (){
                $('#cprtCardPrice').html(numberMinusWithCommas($(this).attr('data-dcAmt')) + ' 원');
            });
        }

        if(dummyRateInfo != undefined){
            selectUsimBasJoinPriceAjax(dummyRateInfo);
        }

    };


    var getChargelist = function(isInit) {
        pageInfo.pageNo = 1;

        if (isInit == undefined ) {
            isInit = false ;
        }

        $('.filterLTE').hide();
        $('.filter3G').hide();
        $('.filter5G').hide();

        initQuestionDom();

        var orderEnum = pageInfo.orderEnum ;


        if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){	//유심
            var salePlcyCd =  $(':radio[name=agrmTrm]:checked').attr('salePlcyCd') ;
            var prdtId = '';
            $('input[name=prdtId]').each(function (){
                if(prdtId.indexOf($(this).val()) == -1){
                    if(prdtId != ''){
                        prdtId = prdtId + ',' + $(this).val();
                    }else{
                        prdtId = $(this).val();
                    }
                }
            });
            var dataType = $(':radio[name="dataType"]:checked').val();
            var operType = $(':radio[name="operType"]:checked').val();
            var agrmTrm = $(':radio[name="agrmTrm"]:checked').val();
            var noArgmYn = 'Y';
            var onOffType = $('#onOffType').val();


            ////번호 이동 셀프개통 제한 시간에 오류 발생 .. 신규로.. 설정 처리
            if(operType == undefined || operType == ''){
                operType = "NAC3";
            }



            if(onOffType == undefined || onOffType == ''){
                if($('input[name=radOpenType]:checked').attr('id') == 'radOpenType1'){
                    onOffType = '7';
                }else{
                    onOffType = '3';
                }
            }
            var orgnId = $('#initOrgnId').val();
            if($('#prodCtgType').val() == '03'){
                orgnId = 'V000019481';	//자급제
            }else{
                if(orgnId != ''){
                    salePlcyCd = '';
                }

            }
            if(agrmTrm != undefined && agrmTrm != ''){
                var varData = ajaxCommon.getSerializedData({
                    salePlcyCd:salePlcyCd
                    , payClCd:'PO'
                    , prdtSctnCd: dataType
                    , prdtId : prdtId
                    , operType : operType
                    , orderEnum : orderEnum
                    , agrmTrm : agrmTrm
                    , noArgmYn : noArgmYn
                    , plcySctnCd : FOR_MSP.USIM
                    , onOffType:onOffType //온라인
                    , orgnId : orgnId //편의점용유심 / 자급제+유심
                });

                ajaxCommon.getItem({
                    id:'listCharge'
                    ,cache:false
                    ,url:'/m/usim/getUsimChargeListAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,async:false
                },function(data){
                    if(sliderInitYn == 'Y'){
                        sliderInitYn = 'N';
                    }
                    pageInfo.chargelist = data;
                    charegListHtml(isInit);
                });

                if(dataType == '3G'){
                    $('.filter3G').show();
                }else if(dataType == '5G'){
                    $('.filter5G').show();
                }else{
                    $('.filterLTE').show();
                }
            }

        } else {

            var varData = ajaxCommon.getSerializedData({
                prodId:$('#prodId').val()
                ,operType:$(':radio[name="operType"]:checked').val()
                ,instNom:$('input[name="instNom"]:checked').val()
                ,modelMonthly : $('#agrmTrm').val()
                ,hndsetModelId:$('select[name="hndsetModelId"]').val()
                ,orderEnum:orderEnum
                ,onOffType:'3' //모바일
                ,noAgrmYn : 'N'
                ,plcySctnCd : FOR_MSP.PHONE
            });


            ajaxCommon.getItem(
                {
                    id:'getChargeList'
                    ,cache:false
                    ,url:'/m/product/phone/phoneViewAjax.do'
                    ,data: varData
                    ,dataType:'json'
                    ,async:false
                }
                , function(jsonObj) {
                    if(sliderInitYn == 'Y'){
                        sliderInitYn = 'N';
                    }
                    pageInfo.chargelist = jsonObj.chargeXmlList;
                    pageInfo.settlYn = jsonObj.phoneProdBas.settlYn;
                    charegListHtml();
                    optionCharge();
                });
        }

        $("#rateBtnCompar .rate-btn__cnt").html("0");
    };

    var isEmpty = function(obj) {
        if (obj == null || obj == undefined || obj == "") {
            return true;
        } else {
            return false;
        }
    }

    var getRowTemplate = function(rateObj ,cardCnt ,isRateArr){
        var arr =[];
        var rdoBestRate = $("#rdoBestRate").val();
        let tempStyle= "";
        let isBadge = "false";
        let chkStr = rateObj.rateAdsvcGdncSeq + "_" + rateObj.rateCd ;  //rateObj.rateAdsvcGdncSeq 값이 없는 것이 존재함 오류 발생

        if (pageInfo.tabButIndex == "-1" ) {

            if (pageInfo.badgeList.includes(chkStr)) {
                tempStyle= "";
            } else {
                tempStyle= "display:none;"
            }
        } else if(rdoBestRate == "0" && !isRateArr) {
            // 추천 요금제 View
            tempStyle= "display:none;"
        } if (pageInfo.tabButIndex == "-2" ) {
            var gropRateCtg = rateObj.expnsnStrVal1;
            if(isEmpty(gropRateCtg)){
                gropRateCtg = "";
            }

            if (gropRateCtg.indexOf(pageInfo.selRateCtg) > -1) {
                tempStyle= "";
            } else {
                tempStyle= "display:none;"
            }
        }

        if (pageInfo.badgeList.includes(chkStr)) {
            isBadge = "ture";
        }


        arr.push('<li class="rate-content__item" style="'+tempStyle+'" data-is-rate-best="'+isRateArr+'" data-is-badge="'+isBadge+'"  >\n');
        arr.push('    <input class="c-checkbox c-checkbox--box" id="chkBoxAA_'+cardCnt+'" type="radio" name="selectRate" value="'+rateObj.rateCd +'">\n');
        arr.push('        <label class="c-card__box-label" for="chkBoxAA_'+cardCnt+'"></label>\n');
        arr.push('        <div class="rate-info__wrap">\n');
        arr.push('            <!--요금제 타이틀 영역 -->\n');
        arr.push('            <div class="rate-info__title">\n');
        arr.push('                <div class="rate-sticker-wrap">\n');
        if (isRateArr) {
            arr.push('                <span class="c-text-label c-text-label--mint-type1">추천</span>\n');
        }
        if ( rateObj.sprtTp == 'KD'){
            arr.push('                 <span class="c-text-label c-text-label--blue-type1">단말할인</span>\n');
        } else if( rateObj.sprtTp == 'PM'){
            arr.push('                 <span class="c-text-label c-text-label--blue-type1">요금할인</span>\n');
        }
        arr.push('                </div>\n');
        if (!isEmpty(rateObj.rateAdsvcNm)){
            arr.push('            <strong>'+rateObj.rateAdsvcNm +'</strong>\n');
            //arr.push('            <p >'+rateObj.rateAdsvcData +'(xmlDataCnt:'+rateObj.mspRateMstDto.xmlDataCnt+'#xmlQosCnt:'+rateObj.mspRateMstDto.xmlQosCnt+') </p>\n');
        }else{
            arr.push('            <strong>'+rateObj.rateNm +'</strong>\n');
            //arr.push('            <p >'+rateObj.rateAdsvcData +'(xmlDataCnt:'+rateObj.mspRateMstDto.xmlDataCnt+'#xmlQosCnt:'+rateObj.mspRateMstDto.xmlQosCnt+') </p>\n');
        }

        var rateNm = rateObj.rateAdsvcNm;
        if(isEmpty(rateNm)){
            rateNm = rateObj.rateNm;
        }

        arr.push('                <a class="_rateDetailPop" href="javascript:void(0);" title="'+rateNm+' 상세내용 팝업 열기" rateAdsvcGdncSeq="'+ rateObj.rateAdsvcGdncSeq  +'"  rateAdsvcCd="' + rateObj.rateCd  +'"  >\n');
        arr.push('                    <i class="c-icon c-icon--arrow-gray4" aria-hidden="true"></i>\n');
        arr.push('                </a>\n');
        arr.push('            </div>\n');
        arr.push('            <!--데이터, 음성, 문자 영역 -->\n');
        arr.push('            <div class="rate-detail">\n');
        arr.push('                <ul class="rate-detail__list">\n');
        arr.push('                    <li class="rate-detail__item">\n');
        arr.push('                        <span><i class="c-icon c-icon--data" aria-hidden="true"></i></span>\n');
        arr.push('                        <span class="c-hidden">데이터</span>\n');
        arr.push('                        <div class="rate-detail__info">\n');
        if(!isEmpty(rateObj.rateAdsvcData)){
            arr.push('                        <p> '+rateObj.rateAdsvcData +'</p>\n');
        }else{
            arr.push('                        <p>'+rateObj.mspRateMstDto.freeDataCntWithSize +'</p>\n');
        }
        if(!isEmpty(rateObj.rateAdsvcPromData)){
            arr.push('                        <p>'+rateObj.rateAdsvcPromData +'</p>\n');
        }
        // arr.push('                            <p>+5GB (아무나결합)</p>\n');
        // arr.push('                            <p>+100GB (데이득)</p>\n');
        arr.push('                        </div>\n');
        arr.push('                    </li>\n');
        arr.push('                    <li class="rate-detail__item">\n');
        arr.push('                        <span><i class="c-icon c-icon--call-gray" aria-hidden="true"></i></span>\n');
        arr.push('                        <span class="c-hidden">음성</span>\n');
        arr.push('                        <div class="rate-detail__info ">\n');
        if(!isEmpty(rateObj.rateAdsvcCall)){
            arr.push('                        <p>'+rateObj.rateAdsvcCall +'</p>\n');
        }else{
            arr.push('                        <p>'+rateObj.mspRateMstDto.freeCallCntWithSize +'</p>\n');
        }
        if(!isEmpty(rateObj.rateAdsvcPromCall)){
            arr.push('                        <p>'+rateObj.rateAdsvcPromCall +'</p>\n');
        }
        arr.push('                        </div>\n');
        arr.push('                    </li>\n');
        arr.push('                    <li class="rate-detail__item">\n');
        arr.push('                        <span><i class="c-icon c-icon--msg" aria-hidden="true"></i></span>\n');
        arr.push('                        <span class="c-hidden">문자</span>\n');
        arr.push('                        <div class="rate-detail__info">\n');
        if(!isEmpty(rateObj.rateAdsvcSms)){
            arr.push('                        <p>'+rateObj.rateAdsvcSms +'</p>\n');
        }else{
            arr.push('                        <p>'+rateObj.mspRateMstDto.freeSmsCntWithSize +'</p>\n');
        }
        if(!isEmpty(rateObj.rateAdsvcPromSms)){
            arr.push('                        <p>'+rateObj.rateAdsvcPromSms +'</p>\n');
        }
        arr.push('                        </div>\n');
        arr.push('                    </li>\n');
        arr.push('                </ul>\n');
        arr.push('            </div>\n');
        arr.push('            <!--제공 아이템 영역 -->\n');
        arr.push('            <div class="rate-info__gift">\n');
        if (!isEmpty(rateObj.rateAdsvcAllianceBnfit) ){
            arr.push('            <!--제휴 혜택 노출 문구 -->\n');
            arr.push('            <p class="rate-info__gift-info">'+ rateObj.rateAdsvcAllianceBnfit.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'').replace(/(<([^>]+)>)/ig,'') +'</p>\n');
        }

        if (!isEmpty(rateObj.rateAdsvcBnfit) ){
            arr.push('            <!--혜택 안내 노출 문구 -->\n');
            arr.push('             <p class="rate-info__present-info-info">'+ rateObj.rateAdsvcBnfit.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'').replace(/(<([^>]+)>)/ig,'') +'</p>\n');
        }

        arr.push('            </div>\n');

        arr.push('            <!--가격 영역-->\n');
        arr.push('            <div class="rate-price">\n');
        arr.push('                <div class="rate-price-wrap">\n');

        if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
            var dummyPrice = Number(rateObj.payMnthChargeAmt);
            if($("#agrmTrm").val() != '0'){
                dummyPrice = Number(rateObj.payMnthChargeAmt) + Number(rateObj.payMnthAmt);
            }


            // arr.push('              <!--약정시 기본료 -->\n');
            // arr.push('              <div class="rate-price__item">\n');
            // arr.push('                  <p class="rate-price__title">\n');
            // arr.push('                      약정시 기본료\n');
            // arr.push('                  </p>\n');
            // arr.push('                  <p class="rate-price__info">\n');
            // arr.push('                      <b>19,800</b> 원\n');
            // arr.push('                  </p>\n');
            // arr.push('              </div>\n');
            // arr.push('              <!--요금할인시 기본료 -->\n');
            // arr.push('              <div class="rate-price__item">\n');
            // arr.push('                  <p class="rate-price__title">\n');
            // arr.push('                      요금할인시 기본료\n');
            // arr.push('                  </p>\n');
            // arr.push('                  <p class="rate-price__info">\n');
            // arr.push('                      <b>19,800</b> 원\n');
            // arr.push('                  </p>\n');
            // arr.push('              </div>\n');
            arr.push('                 <!--단말 요금제 -->\n');
            arr.push('                 <div class="rate-price__item">\n');
            arr.push('                     <p class="rate-price__title product">\n');
            arr.push('                         월 단말요금 <b>' + numberWithCommas(rateObj.payMnthAmt) + '</b> 원 + 월 통신요금 <b>' + numberWithCommas(rateObj.payMnthChargeAmt) + '</b> 원\n');
            arr.push('                     </p>\n');
            arr.push('                     <p class="rate-price__info">\n');
            arr.push('                         <b>' + numberWithCommas(dummyPrice) + '</b> 원\n');
            arr.push('                     </p>\n');
            arr.push('                 </div>\n');
        } else {
            arr.push('              <!--월 기본료 -->\n');
            arr.push('              <div class="rate-price__item">\n');
            arr.push('                  <p class="rate-price__title through">\n');
            arr.push('                      <span class="c-hidden">월 기본료(VAT 포함)</span>\n');
            if(!isEmpty(rateObj.mmBasAmtVatDesc)){
                arr.push('                    <b>' + numberWithCommas(rateObj.mmBasAmtVatDesc) + '</b> 원\n');
            } else {
                arr.push('                    <b>&nbsp;</b>\n');
            }
            arr.push('                  </p>\n');

            var tempPrice = "";
            if(isEmpty(rateObj.promotionAmtVatDesc) ){
                if(isEmpty(rateObj.mmBasAmtVatDesc)  ){
                    tempPrice = rateObj.mmBasAmtVatDesc ;
                } else if(isEmpty(rateObj.payMnthChargeAmt)){
                    tempPrice = rateObj.payMnthChargeAmt ;
                } else {
                    tempPrice = "0";
                }
            } else {
                tempPrice = rateObj.promotionAmtVatDesc;
            }
            arr.push('                  <p class="rate-price__info">\n');
            arr.push('                      <b>' + numberWithCommas(tempPrice) + '</b> 원\n');
            arr.push('                  </p>\n');
            arr.push('              </div>\n');
        }
        arr.push('                </div>\n');
        arr.push('            </div>\n');



        arr.push('        </div>\n');
        arr.push('        <!-- 비교함 영역 -->\n');
        arr.push('        <div class="rate-compare">\n');
        if (isBadge == "ture") {
            arr.push('        <input type="checkbox" class="c-checkbox" id="chkBadgeAA_'+cardCnt+'" value="'+ rateObj.rateAdsvcGdncSeq  +'" data-rate-adsvc-cd="' + rateObj.rateCd  +'"  checked  >\n');
        } else {
            arr.push('        <input type="checkbox" class="c-checkbox" id="chkBadgeAA_'+cardCnt+'" value="'+ rateObj.rateAdsvcGdncSeq  +'" data-rate-adsvc-cd="' + rateObj.rateCd  +'"  >\n');
        }
        if (pageInfo.tabButIndex == "-1" ) {
            arr.push('        <label class="c-card__badge-label compare" for="chkBadgeAA_'+cardCnt+'">\n');
            arr.push('            <span class="c-hidden">'+rateObj.rateNm +' 비교하기</span>\n');
            arr.push('        </label>\n');
        } else {
            arr.push('        <label class="c-card__badge-label" for="chkBadgeAA_'+cardCnt+'">\n');
            arr.push('            <span class="c-hidden">'+rateObj.rateNm +' 비교하기</span>\n');
            arr.push('        </label>\n');
        }
        arr.push('        </div>\n');
        arr.push('</li>\n');
        return arr.join('');
    }

    var getRowTemplateQuResult = function(rateObj ,cardCnt ){
        var arr =[];
        var rdoBestRate = $("#rdoBestRate").val();
        let chkStr = rateObj.rateAdsvcGdncSeq + "_" + rateObj.rateCd ;  //rateObj.rateAdsvcGdncSeq 값이 없는 것이 존재함 오류 발생


        arr.push('<li class="rate-content__item"  >\n');
        arr.push('    <input class="c-checkbox c-checkbox--box" id="cbResult_'+cardCnt+'" type="radio" name="selectRate" value="'+rateObj.rateCd +'">\n');
        arr.push('        <label class="c-card__box-label" for="cbResult_'+cardCnt+'"></label>\n');
        arr.push('        <div class="rate-info__wrap">\n');
        arr.push('            <!--요금제 타이틀 영역 -->\n');
        arr.push('            <div class="rate-info__title">\n');
        arr.push('                <div class="rate-sticker-wrap">\n');
        if ( rateObj.sprtTp == 'KD'){
            arr.push('                 <span class="c-text-label c-text-label--blue-type1">단말할인</span>\n');
        } else if( rateObj.sprtTp == 'PM'){
            arr.push('                 <span class="c-text-label c-text-label--blue-type1">요금할인</span>\n');
        }
        arr.push('                </div>\n');
        if (!isEmpty(rateObj.rateAdsvcNm)){
            arr.push('            <strong>'+rateObj.rateAdsvcNm +'</strong>\n');
        }else{
            arr.push('            <strong>'+rateObj.rateNm +'</strong>\n');
        }

        var rateNm = rateObj.rateAdsvcNm;
        if(isEmpty(rateNm)){
            rateNm = rateObj.rateNm;
        }

        arr.push('                <a class="_rateDetailPop" href="javascript:void(0);" title="'+rateNm+' 상세내용 팝업 열기" rateAdsvcGdncSeq="'+ rateObj.rateAdsvcGdncSeq  +'"  rateAdsvcCd="' + rateObj.rateCd  +'"  >\n');
        arr.push('                    <i class="c-icon c-icon--arrow-gray4" aria-hidden="true"></i>\n');
        arr.push('                </a>\n');
        arr.push('            </div>\n');
        arr.push('            <!--데이터, 음성, 문자 영역 -->\n');
        arr.push('            <div class="rate-detail">\n');
        arr.push('                <ul class="rate-detail__list">\n');
        arr.push('                    <li class="rate-detail__item">\n');
        arr.push('                        <span><i class="c-icon c-icon--data" aria-hidden="true"></i></span>\n');
        arr.push('                        <span class="c-hidden">데이터</span>\n');
        arr.push('                        <div class="rate-detail__info">\n');
        if(!isEmpty(rateObj.rateAdsvcData)){
            arr.push('                        <p>'+rateObj.rateAdsvcData +'</p>\n');
        }else{
            arr.push('                        <p>'+rateObj.mspRateMstDto.freeDataCntWithSize +'</p>\n');
        }
        if(!isEmpty(rateObj.rateAdsvcPromData)){
            arr.push('                        <p>'+rateObj.rateAdsvcPromData +'</p>\n');
        }
        // arr.push('                            <p>+5GB (아무나결합)</p>\n');
        // arr.push('                            <p>+100GB (데이득)</p>\n');
        arr.push('                        </div>\n');
        arr.push('                    </li>\n');
        arr.push('                    <li class="rate-detail__item">\n');
        arr.push('                        <span><i class="c-icon c-icon--call-gray" aria-hidden="true"></i></span>\n');
        arr.push('                        <span class="c-hidden">음성</span>\n');
        arr.push('                        <div class="rate-detail__info ">\n');
        if(!isEmpty(rateObj.rateAdsvcCall)){
            arr.push('                        <p>'+rateObj.rateAdsvcCall +'</p>\n');
        }else{
            arr.push('                        <p>'+rateObj.mspRateMstDto.freeCallCntWithSize +'</p>\n');
        }
        if(!isEmpty(rateObj.rateAdsvcPromCall)){
            arr.push('                        <p>'+rateObj.rateAdsvcPromCall +'</p>\n');
        }
        arr.push('                        </div>\n');
        arr.push('                    </li>\n');
        arr.push('                    <li class="rate-detail__item">\n');
        arr.push('                        <span><i class="c-icon c-icon--msg" aria-hidden="true"></i></span>\n');
        arr.push('                        <span class="c-hidden">문자</span>\n');
        arr.push('                        <div class="rate-detail__info">\n');
        if(!isEmpty(rateObj.rateAdsvcSms)){
            arr.push('                        <p>'+rateObj.rateAdsvcSms +'</p>\n');
        }else{
            arr.push('                        <p>'+rateObj.mspRateMstDto.freeSmsCntWithSize +'</p>\n');
        }
        if(!isEmpty(rateObj.rateAdsvcPromSms)){
            arr.push('                        <p>'+rateObj.rateAdsvcPromSms +'</p>\n');
        }
        arr.push('                        </div>\n');
        arr.push('                    </li>\n');
        arr.push('                </ul>\n');
        arr.push('            </div>\n');
        arr.push('            <!--제공 아이템 영역 -->\n');
        arr.push('            <div class="rate-info__gift">\n');
        if (!isEmpty(rateObj.rateAdsvcAllianceBnfit) ){
            arr.push('            <!--제휴 혜택 노출 문구 -->\n');
            arr.push('            <p class="rate-info__gift-info">'+ rateObj.rateAdsvcAllianceBnfit.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'').replace(/(<([^>]+)>)/ig,'') +'</p>\n');
        }

        if (!isEmpty(rateObj.rateAdsvcBnfit) ){
            arr.push('            <!--혜택 안내 노출 문구 -->\n');
            arr.push('             <p class="rate-info__present-info-info">'+ rateObj.rateAdsvcBnfit.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/'/gi, '\'').replace(/(<([^>]+)>)/ig,'') +'</p>\n');
        }

        arr.push('            </div>\n');
        arr.push('            <!--가격 영역-->\n');
        arr.push('            <div class="rate-price">\n');
        arr.push('                <div class="rate-price-wrap">\n');

        if($('#prodId').val() != '' && $('#prodCtgType').val() != '03'){
            var dummyPrice = Number(rateObj.payMnthChargeAmt);
            if($("#agrmTrm").val() != '0'){
                dummyPrice = Number(rateObj.payMnthChargeAmt) + Number(rateObj.payMnthAmt);
            }
            arr.push('                 <!--단말 요금제 -->\n');
            arr.push('                 <div class="rate-price__item">\n');
            arr.push('                     <p class="rate-price__title product">\n');
            arr.push('                         월 단말요금 <b>' + numberWithCommas(rateObj.payMnthAmt) + '</b> 원 + 월 통신요금 <b>' + numberWithCommas(rateObj.payMnthChargeAmt) + '</b> 원\n');
            arr.push('                     </p>\n');
            arr.push('                     <p class="rate-price__info">\n');
            arr.push('                         <b>' + numberWithCommas(dummyPrice) + '</b> 원\n');
            arr.push('                     </p>\n');
            arr.push('                 </div>\n');
        } else {
            arr.push('              <!--월 기본료 -->\n');
            arr.push('              <div class="rate-price__item">\n');
            arr.push('                  <p class="rate-price__title through">\n');
            arr.push('                      <span class="c-hidden">월 기본료(VAT 포함)</span>\n');
            if(!isEmpty(rateObj.mmBasAmtVatDesc)){
                arr.push('                    <b>' + numberWithCommas(rateObj.mmBasAmtVatDesc) + '</b> 원\n');
            } else {
                arr.push('                    <b>&nbsp;</b>\n');
            }
            arr.push('                  </p>\n');

            var tempPrice = "";
            if(isEmpty(rateObj.promotionAmtVatDesc) ){
                if(isEmpty(rateObj.mmBasAmtVatDesc)  ){
                    tempPrice = rateObj.mmBasAmtVatDesc ;
                } else if(isEmpty(rateObj.payMnthChargeAmt)){
                    tempPrice = rateObj.payMnthChargeAmt ;
                } else {
                    tempPrice = "0";
                }
            } else {
                tempPrice = rateObj.promotionAmtVatDesc;
            }
            arr.push('                  <p class="rate-price__info">\n');
            arr.push('                      <b>' + numberWithCommas(tempPrice) + '</b> 원\n');
            arr.push('                  </p>\n');
            arr.push('              </div>\n');
        }
        arr.push('                </div>\n');
        arr.push('            </div>\n');
        arr.push('        </div>\n');
        arr.push('</li>\n');
        return arr.join('');
    }

    var initQuestionDom = function() {
        $('#rateBtnMyrate').removeClass('is-active');
        $(".survey").hide();
        $("._survey__content2").hide();
        $('._question').hide();
        $("#ulIsEmpty").hide();
        $("#rateContentCategory").show();
    }

var charegListHtml = function (isInit) {
    var rateList = pageInfo.chargelist;

    if (isInit == undefined ) {
        isInit = false ;
    }

    initView();

    var cardCnt = 0;

    if(rateList != null && rateList != '' && rateList.length > 0) {
        var dataType = $(':radio[name="dataType"]:checked').val();
        var filterShow = false;
        var rdoBestRate = $("#rdoBestRate").val();
        var innerHTML = '';
        var filterCnt = 0;

        $('#rateContentCategory').empty();
        //추천요금제 적용
        if($('#prodId').val() == '' || $('#prodCtgType').val() == '03'){
            var best = '';
            //bestRateArr = $("[name=prdtId]:checked").attr('bestRate').split(',');
            $('input[name=prdtId]').each(function (){
                if(best == ''){
                    best = $(this).attr('bestRate');
                }else{
                    best = best + ',' + $(this).attr('bestRate');
                }
                bestRateArr = best.split(',');
            });
        } else {
            var instNom = $('input[name="instNom"]:checked').val();
            if (instNom != "0") {
                bestRateArr = $("#recommendRateforChk").text().split(',');
            } else {
                //무약정 추천 요금제
                bestRateArr = $("#recommendRateforNoargmChk").text().split(',');
            }
        }
        pageInfo.bestRateArr = bestRateArr ;

        for (var i = 0; i < rateList.length; i++) {

            var isRateArr = false ;
            //추천요금제 적용
            var rateCdSp =rateList[i].rateCd + "#"+ rateList[i].sprtTp;
            if(bestRateArr && bestRateArr.length > 0){
                for(var j=0; j<bestRateArr.length; j++){
                    if(bestRateArr[j] == rateCdSp) {
                        isRateArr = true ;
                        break;
                    }
                }
            }
                        var saleTyYn = false;
            if($('#chkDiscountType1:checked').val() == rateList[i].sprtTp){
                saleTyYn = true;
            }
            if($('#chkDiscountType2:checked').val() == rateList[i].sprtTp){
                saleTyYn = true;
            }

            if(($('#chkDiscountType1').is(':checked') || $('#chkDiscountType2').is(':checked')) && !saleTyYn){
                continue;
            }

            if($('#prodId').val() != '' && $('#prodCtgType').val() != '03' && rateList[i].agrmTrm != $('input[name=instNom]:checked').val()){
                continue;
            }

            //데이터 쉐어링 미노출 처리
            if( rateList[i].rateCd == 'KISOPMDSB'){
                continue;
            }

            //단말이면서 정책이 LTE5G(45)일때 dataType에 따른 요금제 노출
            if ($('#prodId').val() && $('#prodCtgType').val() != '03' && $('#prdtSctnCd').val() == '45') {
                if (rateList[i].mspRateMstDto.dataType != dataType) {
                    continue;
                }
            }

            $('#rateContentCategory').append(getRowTemplate(rateList[i],cardCnt,isRateArr) );
            $('#chkBoxAA_'+cardCnt).data(rateList[i]);
            cardCnt++;
        }
        $('#cardTotalCnt').val(cardCnt);

        if(initRateCdChoice == 'Y'){
            $('input[name=selectRate]').each(function (){
                if($(this).val() == $('#initRateCd').val()){
                    $(this).trigger('click');
                    $('#paymentSelectBtn').trigger('click');
                }
            });
            initRateCdChoice = 'N';
        }

        /*
        if(defaultRateYn == 'Y'){
            defaultRateYn = 'N';
            $('input[name=selectRate]:eq(0)').trigger('click');
            $('#paymentSelectBtn').trigger('click');
        }
        */

        $('.chargeAccordion').each(function (){
            //MCP.addAccordion2('#' + $(this).attr('id'));
        });

        if($('#rdoBestRate').val() == '1' && filterCnt == 0){
            $('#findNoData').show();
            $('#findMoreDiv').hide();
        }else{
            setCardMoreBtn();
        }

        if(cardCnt == 0){
            if($('#rdoBestRate').val() == '0'){
                $('#recommNoData').show();
                $('#recommMoreDiv').hide();
            }else{
                $('#findNoData').show();
                $('#findMoreDiv').hide();
            }
        }

    } else {
        $('#tabType1Card').empty();
        $('#tabType2Card').empty();

        if(cardCnt == 0){
            if($('#rdoBestRate').val() == '0'){
                $('#recommNoData').show();
                $('#recommMoreDiv').hide();
            }else{
                $('#findNoData').show();
                $('#findMoreDiv').hide();
            }
        }
    }

    if($('#socCode').val() != undefined && $('#socCode').val() != ''){
        $('input[name=selectRate]').each(function (){
            if($(this).val() == $('#initRateCd').val()){
                $(this).trigger('click');
                setRateInfo($('input[name=selectRate]:checked'), 'Y');
            }
        });
    }

    nextStepBtnChk();
}


var charegListView = function (isInit) {
    var rateList = pageInfo.chargelist;
    var cardCnt = 0;

    if(rateList != null && rateList != '' && rateList.length > 0) {
        var dataType = $(':radio[name="dataType"]:checked').val();
        var filterShow = false;
        var rdoBestRate = $("#rdoBestRate").val();
        var innerHTML = '';
        var filterCnt = 0;
        $('#rateContentCategory').empty();

        for (var i = 0; i < rateList.length; i++) {
            var isRateArr = false ;
            //추천요금제 적용
            var rateCdSp =rateList[i].rateCd + "#"+ rateList[i].sprtTp;
            if(bestRateArr && bestRateArr.length > 0){
                for(var j=0; j<bestRateArr.length; j++){
                    if(bestRateArr[j] == rateCdSp) {
                        isRateArr = true ;
                        break;
                    }
                }
            }

            var saleTyYn = false;
            if($('#chkDiscountType1:checked').val() == rateList[i].sprtTp){
                saleTyYn = true;
            }

            if($('#chkDiscountType2:checked').val() == rateList[i].sprtTp){
                saleTyYn = true;
            }

            if(($('#chkDiscountType1').is(':checked') || $('#chkDiscountType2').is(':checked')) && !saleTyYn){
                continue;
            }

            if($('#prodId').val() != '' && $('#prodCtgType').val() != '03' && rateList[i].agrmTrm != $('input[name=instNom]:checked').val()){
                continue;
            }

            //데이터 쉐어링 미노출 처리
            if( rateList[i].rateCd == 'KISOPMDSB'){
                continue;
            }

            //단말이면서 정책이 LTE5G(45)일때 dataType에 따른 요금제 노출
            if ($('#prodId').val() && $('#prodCtgType').val() != '03' && $('#prdtSctnCd').val() == '45') {
                if (rateList[i].mspRateMstDto.dataType != dataType) {
                    continue;
                }
            }

            $('#rateContentCategory').append(getRowTemplate(rateList[i],cardCnt,isRateArr) );
            $('#chkBoxAA_'+cardCnt).data(rateList[i]);
            cardCnt++;
        }
    }
}

var setRateInfo = function (obj, noCloseYn){
    dummyRateInfo = obj;
    var thisData = obj.data();

    var rateAdsvcNm = thisData.rateAdsvcNm;
    if(isEmpty(rateAdsvcNm)){
        rateAdsvcNm = thisData.rateNm;
    }

    var dataCnt = thisData.rateAdsvcData;
    var callCnt = thisData.rateAdsvcCall;
    var smsCnt = thisData.rateAdsvcSms;
    var dataProm = isEmpty(thisData.rateAdsvcPromData)  ? '' :  thisData.rateAdsvcPromData;
    var callProm = isEmpty(thisData.rateAdsvcPromCall)  ? '' : thisData.rateAdsvcPromCall;
    var smsProm = isEmpty(thisData.rateAdsvcPromSms)  ? '' : thisData.rateAdsvcPromSms;
    var salePlcyCd = isEmpty(thisData.salePlcyCd)  ? '' :  thisData.salePlcyCd ;
    var rateBnfit = thisData.rateAdsvcAllianceBnfit;
    var maxDataDelivery = thisData.maxDataDelivery;

    //KT인터넷 트리플할인 할인금액 확인
    if (obj.val() != pageInfo.ktTripleDcRateCd) {
        pageInfo.ktTripleDcRateCd =  obj.val();
        //2번 호출 방지
        var varData = ajaxCommon.getSerializedData({
            rateCd : obj.val()
        });

        ajaxCommon.getItemNoLoading({
            id:'getKtTripleDcAmt'
            ,cache:false
            ,url:'/msp/getKtTripleDcAmtAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        },function(ktTripleDcAmt){
            pageInfo.ktTripleDcAmt = ktTripleDcAmt;
            if (0 < ktTripleDcAmt) {
                $('._ktTripleDcCss').show();
                $('#laKtTripleDcAmt_02').html("요금할인 24개월(-"+numberWithCommas(pageInfo.ktTripleDcAmt)+"원)");

            } else {
                $('._ktTripleDcCss').hide();
                $('#laKtTripleDcAmt_02').html("요금할인 24개월(0원)");
            }
        });
    }


    $('#choicePay').text(rateAdsvcNm);

    if($("input:radio[name=cstmrType]:checked").val() == 'NM'){
            if ($('#choicePay').text().indexOf('시니어') != -1) {
            MCP.alert("만 65세 이상만 신청가능한 요금제 입니다.");
            $('#radPayTypeA').show();
            $('#radPayTypeB').hide();
            $('#radPayTypeC').hide();
            $('#divGift').hide();
            initEvntCdPrmt();
            return;
        }else{

               $('#choicePayTxt1').text();
               $('#choicePayTxt2').text();
               $('#choicePayTxt3').text();
               $('#choicePayTxt4').text();
               $('#choicePayTxt5').text();
               $('#choicePayTxt6').text();
               $('#choicePayTxt7').text();
               $('#choicePayTxt8').text();
               $('#choicePayTxt10').text();

               $('#choicePayTxt1').text(dataCnt);
               $('#choicePayTxt2').text(dataProm);
               $('#choicePayTxt3').text(callCnt);
               $('#choicePayTxt4').text(callProm);
               $('#choicePayTxt5').text(smsCnt);
               $('#choicePayTxt6').text(smsProm);
               $('#choicePayTxt7').text(rateBnfit);
               $('#choicePayTxt8').text("[추가혜택]");
               $('#choicePayTxt10').text(maxDataDelivery);

               if(rateBnfit == ""){
                   $('#choicePayTxt9').hide();
               }else{
                    $('#choicePayTxt9').show();
               }

               if(maxDataDelivery == ""){
                   $('#choicePayTxt10').hide();
               }else{
                    $('#choicePayTxt10').show();
               }

            $('#radPayTypeA').hide();
            $('#radPayTypeB').show();
            $('#radPayTypeC').show();
        }
    }else{

        $('#choicePayTxt1').text();
       $('#choicePayTxt2').text();
       $('#choicePayTxt3').text();
       $('#choicePayTxt4').text();
       $('#choicePayTxt5').text();
       $('#choicePayTxt6').text();
       $('#choicePayTxt7').text();
       $('#choicePayTxt8').text();
       $('#choicePayTxt10').text();

       $('#choicePayTxt1').text(dataCnt);
       $('#choicePayTxt2').text(dataProm);
       $('#choicePayTxt3').text(callCnt);
       $('#choicePayTxt4').text(callProm);
       $('#choicePayTxt5').text(smsCnt);
       $('#choicePayTxt6').text(smsProm);
       $('#choicePayTxt7').text(rateBnfit);
       $('#choicePayTxt8').text("[추가혜택]");
       $('#choicePayTxt10').text(maxDataDelivery);

       if(rateBnfit == ""){
           $('#choicePayTxt9').hide();
       }else{
            $('#choicePayTxt9').show();
       }

       if(maxDataDelivery == ""){
           $('#choicePayTxt10').hide();
       }else{
            $('#choicePayTxt10').show();
       }

        $('#radPayTypeA').hide();
        $('#radPayTypeB').show();
        $('#radPayTypeC').show();
    }

    if(noCloseYn != 'Y'){
        $('.c-icon--close').trigger('click');
    }

    $('#socCode').val(obj.val());
    $('#salePlcyCd').val(salePlcyCd);

    selectUsimBasJoinPriceAjax(obj);
    nextStepBtnChk();
    setAgrmTrm();
}



/**
 가입비/유심/번호이동수수료등의 금액 제어 처리 함수
 */
var optionCharge = function() {
    var operType = $(':radio[name="operType"]:checked').val();
    var prdtSctnVal = "";
    if ($('#prdtSctnCd').val() == '03') {
        $('.filter3G').show();
        prdtSctnVal = '3G';
    } else if($('#prdtSctnCd').val() == '04') {
        $('.filterLTE').show();
        prdtSctnVal = 'LTE';
    } else if ($('#prdtSctnCd').val() == '45') {
        prdtSctnVal = $('input[name=dataType]:checked').val();
    } else {
        $('.filter5G').show();
        prdtSctnVal = '5G';
    }

    var varData = ajaxCommon.getSerializedData({
        operType : operType,
        prdtSctnCd : prdtSctnVal
    });

    ajaxCommon.getItem({
        id : 'getUsimChargeAjax',
        cache : false,
        url : '/m/product/phone/getUsimChargeAjax.do',
        data : varData,
        dataType : 'json'
    }, function(jsonObj) {
        if(operType==OPER_TYPE.MOVE_NUM){
            $('#move_01').show();
            $('#move_02').show();
        }else{
            $('#move_01').hide();
            $('#move_02').hide();
        }

        $("#joinPrice").val(numberWithCommas(jsonObj.joinPrice));
        $("#usimPrice").val(numberWithCommas(jsonObj.usimPrice));
        showJoinUsimPrice();
    });
};

var compareCntChk = function (){
    if($('#comparePopBtn').find('.c-hidden').text() == '0'){
        MCP.alert('비교하고 싶은 요금제를 먼저 선택해주세요.');
        return;
    }else{
        $('#comparePopTrigger').trigger('click');
    }
}

$("input:radio[name=cstmrType]").click(function(){
    var thisVal = $(this).val();

     if (thisVal == CSTMR_TYPE.NM) {
         $("._isTeen").show();
         $("._isForeigner").hide();
         $("._isLocal").hide();
         $('#radOpenType1').attr('disabled', 'disabled');
         $('#radOpenType2').trigger('click');

         if($("input:radio[name=cstmrType]:checked").val() == 'NM'){
             if ($('#choicePay').text().indexOf('시니어') != -1) {
                 $('#radPayTypeA').show();
                 $('#radPayTypeB').hide();
                 $('#radPayTypeC').hide();
                 $('#divGift').hide();
                initEvntCdPrmt();
                 return;
             }
         }

     }else if (thisVal == CSTMR_TYPE.FN) {
           $("._isTeen").hide();
           $("._isForeigner").show();
           $("._isLocal").hide();
           $('#radOpenType1').attr('disabled', 'disabled');
           $('#radOpenType2').trigger('click');
     }else{
         $("._isTeen").hide();
         $("._isForeigner").hide();
         $("._isLocal").show();
         $('#radOpenType1').removeAttr('disabled');
     }

});

// 이벤트코드 검증
var checkEvntCdPrmt = function(){

    var rateCd = ajaxCommon.isNullNvl($('#socCode').val(), "");
    if(rateCd == ""){
        MCP.alert("요금제를 먼저 선택해 주세요.");
        return false;
    }

    if($("#isEvntCdPrmtAuth").val() == "1"){
        MCP.alert("이미 이벤트 코드가 확인 되었습니다.");
        return false;
    }

    var evntCdPrmt = $.trim($("#evntCdPrmt").val());
    if(evntCdPrmt == ""){
        MCP.alert("이벤트 코드를 입력해 주세요.");
        return false;
    }

    var varData = ajaxCommon.getSerializedData({
         evntCdPrmt : evntCdPrmt
        ,rateCd : rateCd
    });

    ajaxCommon.getItem({
        id : 'checkEvntCdAjax',
        cache : false,
        url : '/benefit/checkEvntCdAjax.do',
        data : varData,
        dataType : 'json'
    },function(jsonObj) {

        if(jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){
            MCP.alert("이벤트 코드가 확인 되었습니다.");
            $("#evntCdPrmt").prop("disabled", true);
            $('#evntCdPrmtBtn').addClass('is-disabled');
            $("#isEvntCdPrmtAuth").val("1");
            $("#eventCdName").text(evntCdPrmt);
            $("#eventCdDiv").show();
            $("#eventExposedText").text(jsonObj.getEventVal.exposedText);
            $("#evntCdSeq").val(jsonObj.getEventVal.ecpSeq);
            $('#evntCdTryBtn').removeClass('is-disabled');
            nextStepBtnChk();
        }else{
            MCP.alert("이벤트 코드가 일치하지 않습니다.<br>확인 후 정확하게 입력해 주세요.");
        }
    });
}

// 이벤트코드 프로모션 관련 내용 초기화
function initEvntCdPrmt(){
    $("#evntCdPrmtWrap").hide();
    $("#evntCdPrmt").val("");
    $("#evntCdPrmt").parent().removeClass("has-value");
    $("#evntCdPrmt").prop("disabled", false);
    $("#evntCdPrmtBtn").removeClass('is-disabled');
    $("#isEvntCdPrmtAuth").val("");
    $("#evntCdPrmtSoc").val("");
}

//이벤트코드 재입력
function tryEvntCdPrmt(){
    $("#evntCdPrmt").prop("disabled", false);
    $("#isEvntCdPrmtAuth").val("");
    $("#evntCdPrmt").val("");
    $("#eventCdDiv").hide();
    $("#evntCdTryBtn").addClass('is-disabled');
    $("#evntCdPrmtBtn").removeClass('is-disabled');
}