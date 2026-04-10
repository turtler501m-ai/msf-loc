var v_ctgList; // xml 카테고리 목록
var v_proList; // xml 상품 목록


var pageObj = {
    rateGdncSeq:""
    ,rateAdsvcCtgCd:""
}


$(document).ready(function (){
    $("#modalArs").addClass("rate-detail-view");

    rateResSearch($("#ncn option:selected").val());
    //loadXmlData();

    $("#ncn").on("change",function(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/m/mypage/farPricePlanView.do"
        });
        var ncn = $("#ncn option:selected").val();
        ajaxCommon.attachHiddenElement("ncn",ncn);
        ajaxCommon.formSubmit();
    });
});

function loadXmlData(){
    //로딩시 카테고리 조회
    var html ='';
    var appHtml = '';
    $("#1depthList").html('');
    $("#tabChk").val('');
    initXmlData().then(function(){
        $.each(v_ctgList, function(idx, item) {
            if(item.ctgOutputCd == $("#ctgOutputCd").val()){
                if(item.depthKey == 1 && item.rateAdsvcDivCd == $("#rateAdsvcDivCd").val()) {
                    html = '<button class="c-tabs__button" data-tab-header="#tabA1-panel_'+idx+'" href="javascript:void(0);" onclick="mainTabClick(\''+item.rateAdsvcCtgCd+'\',\''+idx+'\');">'+item.rateAdsvcCtgNm+'</button>';
                    appHtml = '<div class="c-tabs__panel" id="tabA1-panel_'+idx+'" style="display: none;">';
                    appHtml +='</div>';
                    $(".tab_view").append(appHtml);
                    MCP.addSingleTab('#1depthList', '.c-tabs__button', html, depth1TabCallback);
                }
           }
        });
    }).catch();
}

// 요금제 변경 예약 현황조회
function rateResSearch(contractNum){

    var varData = ajaxCommon.getSerializedData({
              ncn : contractNum
             ,prvRateCd :$("#prvRateCd").val()
    });

     ajaxCommon.getItem({
            id:'myRateResvViewAjax'
            ,cache:false
            ,url:'/m/mypage/myRateResvViewAjax.do'
            ,data: varData
            ,dataType:"json"
     }
     ,function(jsonObj){
         var myPriceInfo=  jsonObj.myPriceInfo;
         if(jsonObj.RESULT_CODE == "S"){
            $("#prvRateGrpNm").text(jsonObj.prvRateGrpNm);

            $("#rateAdsvcLteDesc").text(jsonObj.rateAdsvcLteDesc);
            $("#rateAdsvcCallDesc").text(jsonObj.rateAdsvcCallDesc);
            $("#rateAdsvcSmsDesc").text(jsonObj.rateAdsvcSmsDesc);
            $("#prvRateGdncSeq").val(jsonObj.prvRateGdncSeq); // 이용중인 요금제 안내 일련번호

            if(myPriceInfo != null){
                var appStartDd = myPriceInfo.appStartDd;

                if(appStartDd.length >= 8){
                    $("#appStartDdDate").html("신청일 : "+ appStartDd.substring(0,4)+"."+ appStartDd.substring(4,6)+"."+appStartDd.substring(6,8));
                }

                if(myPriceInfo.promotionDcAmt > 0){
                    $("#baseVatAmt").html(numberWithCommas(parseInt(myPriceInfo.baseVatAmt)) + "원");
                }

                if(myPriceInfo.rsrvPrdcCd != ""){ //예약된 요금조회
                    $("#rsrvPrdcNm").html(myPriceInfo.rsrvPrdcNm);
                    var rsrvBasicAmt =  numberWithCommas(parseInt(myPriceInfo.rsrvBasicAmt));
                    $("#totalMony").html(rsrvBasicAmt);
                    $("#rsrvPrdcChk").val("Y");
                    $(".rsrvPrdcSelData").show();
                } else{
                    $(".rsrvPrdcSelData").hide();
                }

                $("#prvRateCd").val(myPriceInfo.prvRateCd);

                var html = '';

                if(jsonObj.total == 0){
                    $(".tab_view").html('');
                    html += '<div class="c-nodata">';
                    html += '<i class="c-icon c-icon--nodata" aria-hidden="true" ></i>';
                    html += '<p class="c-noadat__text">변경 가능한 요금제가 없습니다.</p>';
                    html += '</div>';
                    $(".tab_view").html(html);
                    //$("#btn_farChg").hide();
                } else{
                    $(".tab_view").html('');
                    html += ' <div class="c-tabs__list c-expand" id="1depthList" data-ignore="true">';
                    html += '</div>';
                    $(".tab_view").html(html);
                    //$("#btn_farChg").show();
                     loadXmlData();
                }

            }
         }
     });
}

// 1.1

//예약취소
function btn_canCel(){
    var varData = ajaxCommon.getSerializedData({
        contractNum : $("#ncn option:selected").val()
    });

    KTM.Confirm("변경예약을 취소하시겠습니까?", function(){
        ajaxCommon.getItem({
                id:'deleteMyRateResvAjax'
                ,cache:false
                ,url:'/m/mypage/deleteMyRateResvAjax.do'
                ,data: varData
                ,dataType:"json"
        }
        ,function(jsonObj){
            if(jsonObj.RESULT_CODE == 'S'){
                MCP.alert("변경 예약이 취소되었습니다.");
                $("#rsrvPrdcChk").val('');
                rateResSearch($("#ncn option:selected").val());
            }else if(jsonObj.RESULT_CODE == 'E'){
                MCP.alert(jsonObj.message);
            }
       });
       this.close();
    });
}

function get_date_str(date) {
    var sYear = date.getFullYear();
    var sMonth = date.getMonth() + 1;
    var sDate = date.getDate();

    sMonth = sMonth > 9 ? sMonth : "0" + sMonth;
    sDate  = sDate > 9 ? sDate : "0" + sDate;
    return sYear + sMonth + sDate;
}

// 로딩시 카테고리 조회
var initXmlData = function() {
    return new Promise(function(resolve, reject) {
    KTM.LoadingSpinner.show();
        $.ajax({
            type : 'GET'
            , url : '/m/mypage/farPriceCtgTab1Ajax.do'
            , dataType : 'json'
            , success : function(data) {
                v_ctgList = data.item.ctgList.item;
                v_proList = data.item.proList.item;
                resolve();
                KTM.LoadingSpinner.hide();
            }
        });
    });
}

//첫번째 카테고리
function depth1TabCallback(){
    $($('.c-tabs__button')[0]).click();
}

// 메인탭 클릭 시
function mainTabClick(rateAdsvcCtgCd, idx){

    var varData = ajaxCommon.getSerializedData({
            rateAdsvcCtgCd : rateAdsvcCtgCd
          , rateCd :$("#prvRateCd").val()
    });

    url = "/m/mypage/farPriceCtgTab2Ajax.do";
    //페이지 호출
    ajaxCommon.getItem({
            id:'farPriceCtgTab2Ajax'
            ,cache:false
            ,url:url
            ,data: varData
            ,dataType:"json"
    }
    ,function(data){
        mainTabClickCallback(data, idx);
    });
}

// 메인탭 클릭 콜백
function mainTabClickCallback(resp, idx){
    var buttonXmlList = resp.buttonXmlList;
    $("#tabA1-panel_"+$("#tabChk").val()).hide();
    $("#upRateAdsvcCtgCd").val(resp.upRateAdsvcCtgCd);
    var buttonXmlList = resp.buttonXmlList;
    var html ='';
    html += ' <div class="c-tabs c-tabs--type3" data-ignore="true">';
    html +='<div class="c-tabs__list c-expand" >';
    if(buttonXmlList != ""){
        for(var i=0; i< buttonXmlList.length; i++){
            html+= "<button id='subTab_"+buttonXmlList[i].rateAdsvcCtgCd+"' data-tab-header='#tabB1-panel_"+idx+"' class='c-tabs__button' href='javascript:void(0);' onclick='subTabClick(\""+buttonXmlList[i].rateAdsvcCtgCd+"\",\""+idx+"\")'>"+buttonXmlList[i].rateAdsvcCtgNm+"</button>";
        }
    }
    html += '</div>';
    html += '<div class="c-tabs__panel" id="tabB1-panel_'+idx+'">';
    html += '</div>';
    html += '</div>';


    $("#tabA1-panel_"+idx).html(html);
    $("#tabA1-panel_"+idx).show();
    $("#tabChk").val(idx);
    if(buttonXmlList == ""){
        subTabClick($("#upRateAdsvcCtgCd").val(), idx);
    }

    $('button[id^=subTab_]').on("click", function(){
        $('button[id^=subTab_]').removeClass('is-active');
        $(this).addClass('is-active');
        $('button[id^=subTab_]').attr('aria-hidden',"false");
        $(this).attr('aria-hidden',"true");
    });

    if($("#tabA1-panel_"+idx+" "+"button[id^=subTab_]").length == 0){
        subTabClick($("#upRateAdsvcCtgCd").val(), idx);
    } else {
        $("#tabA1-panel_"+idx+" "+"button[id^=subTab_]")[0].click();
    }
}

// 서브탭 클릭 시
function subTabClick(rateAdsvcCtgCd, idx){
    var varData = ajaxCommon.getSerializedData({
         rateAdsvcCtgCd : rateAdsvcCtgCd
         ,rateCd : $("#prvRateCd").val()
    });

    url = "/m/mypage/farPriceCtgTab3Ajax.do";
    //페이지 호출
    ajaxCommon.getItem({
            id:'farPriceCtgTab2Ajax'
            ,cache:false
            ,url:url
            ,data: varData
            ,dataType:"json"
    }
    ,function(data){
        subTabClickCallback(data, idx);
    });


}

//요금제변경
function btn_farChg(seq, rateAdsvcCtgCd, chkRadion){

    //var isChkYn = true;
    //var chk = "";
    //var seq = "";

    if($("#rsrvPrdcChk").val() == "Y" ){
        MCP.alert("등록된 요금제 예약변경 취소 후 요금제 변경이 가능합니다.");
        return;
    }

    if( $("#compareView"+rateAdsvcCtgCd+" div.c-card").length <=0 || !seq){
        MCP.alert("가입하실 요금제를 선택해 주세요.");
        return;
    }

    var baseAmt = '';
    if($("#promotionAmtVatDesc"+seq).val() != null && $("#promotionAmtVatDesc"+seq).val() != ''){
        if($("#promotionAmtVatDesc"+seq).val().indexOf("원") == -1){
            baseAmt = $("#promotionAmtVatDesc"+seq).val()+"원";
        } else {
            baseAmt = $("#promotionAmtVatDesc"+seq).val();
        }
    } else{
        if($("#mmBasAmtVat"+seq).text().indexOf("원") == -1){
            baseAmt = $("#mmBasAmtVat"+seq).text()+"원";
        } else {
            baseAmt = $("#mmBasAmtVat"+seq).text();
        }
    }

    var varData = ajaxCommon.getSerializedData({ncn:$("#ncn option:selected").val()});

    ajaxCommon.getItem({
        id:'ssibleState'
        ,cache:false
        ,url:'/m/mypage/possibleStateCheckAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(jsonObj){
        var arr =[];
        var outHtml = '';
        if (jsonObj.RESULT_CODE == "S") {

            var parameterData = ajaxCommon.getSerializedData({
                contractNum : $("#ncn option:selected").val()
                ,instAmt : $("#instAmt").text()
                ,prvRateGrpNm:$("#prvRateGrpNm").text()
                ,rateNm : $("#radA"+seq).text()
                ,baseAmt : baseAmt
                ,rateAdsvcCd : $("#rateAdsvcCd"+seq).val()
                ,nxtRateGdncSeq : seq
                ,prvRateGdncSeq : $("#prvRateGdncSeq").val()
                ,isActivatedThisMonth : jsonObj.isActivatedThisMonth //이번달 개통여부
                ,isFarPriceThisMonth : jsonObj.isFarPriceThisMonth //이번달 요금제 변경여부
                ,chkRadion : chkRadion
            });


            openPage('pullPopupByPost','/m/mypage/regServicePop.do',parameterData);

        } else if (jsonObj.RESULT_CODE == "001" || jsonObj.RESULT_CODE == "002" || jsonObj.RESULT_CODE == "004" || jsonObj.RESULT_CODE == "005" ) {
            outHtml+= "고객님께서는 고객센터(고객센터 114/무료) 를 통해 요금제 변경신청 가능하십니다.<br>";
            outHtml+= "감사합니다.<br><br>";
            outHtml+= "[고객센터를 통해서만 변경이 가능한 고객님]<br>";
            outHtml+= "단말/유심상품으로 (재)약정 가입고객, <br>";
            outHtml+= "미성년자, 법인, 개통 후 실사용일 90일이하, 일부 대리점을 통해 유심상품 가입고객";
        } else if (jsonObj.RESULT_CODE == "003"  ) {
            outHtml+="요금제는 ‘월‘ 1회만 변경가능하십니다.<br>";
            outHtml+="다음달부터 홈페이지, 어플, 고객센터를 통해 .<br>변경 가능하십니다.<br><br> 많은 도움드리지 못해 죄송합니다.<br>";
        } else if (jsonObj.RESULT_CODE == "006"  ) {
            outHtml+="해당 시간은 요금제 변경이 불가 합니다. <br>(23:30분 ~ 익일 00:30분, 1시간)<br>";
            outHtml+="위 시간 이후 요금제 변경 진행 바랍니다.<br>";
            outHtml+="감사합니다.<br>";
        } else if (jsonObj.RESULT_CODE == "007"  ) {
            outHtml+= "결합중인 회선은 마이페이지에서<br> 요금제 변경이 불가합니다.<br><br> 고객센터를 통해 요금제 변경 부탁드립니다.<br> (고객센터 : 114)<br>";
        } else if (jsonObj.RESULT_CODE == "008"  ) {
            outHtml+= "고객님께서는 고객센터(고객센터 114/무료) 를 통해 요금제 변경신청 가능하십니다.<br>";
            outHtml+= "감사합니다."
        } else {
            outHtml+="시스템에 문제가 발생 하였습니다.<br>";
            outHtml+="잠시후에 다시 시도 하시기 바랍니다.<br>";
            outHtml+="도움드리지 못해 죄송합니다.<br>";
        }

        if(outHtml != ''){
            MCP.alert(outHtml);
        }
    });
}


// 서브탭 클릭  콜백
function subTabClickCallback(resp, idx){

    var data = resp.ctgXmlList;
    var html ="";
    $("#strRateAdsvcCtgCd").val('');

    if(data.length > 0) {
        const arrDup = data;
        const arrUniqueList = arrDup.filter((character, idx, arr)=>{
            return arr.findIndex((item) => item.rateAdsvcCtgCd === character.rateAdsvcCtgCd) === idx
        });

        for(var i=0; i<arrUniqueList.length; i++){
            $("#strRateAdsvcCtgCd").val(arrUniqueList[i].rateAdsvcCtgCd);
            html += '<div class="c-accordion c-accordion--type1">';
            html += ' <ul class="c-accordion__box" id="accordionB">';
            html += ' <li class="c-accordion__item forIdView'+arrUniqueList[i].rateAdsvcCtgCd+'"   id="forId'+arrUniqueList[i].rateAdsvcCtgCd+'" >';
            html += '<div class="c-accordion__head" data-acc-header="#compareCSP'+arrUniqueList[i].rateAdsvcCtgCd+'">';
            html += '<button class="c-accordion__button" type="button" aria-expanded="false" href="javascript:void(0);" onclick="showUsimAccordion('+arrUniqueList[i].rateAdsvcCtgCd+', '+idx+', this)">'+arrUniqueList[i].rateAdsvcCtgNm+'<p class="c-text c-text--type4 u-co-gray-7">'+arrUniqueList[i].rateAdsvcCtgBasDesc+'</p>';
            html += '</button>';
            html += '</div>';
            html += '<div class="c-accordion__panel expand c-expand" id="compareCSP' + arrUniqueList[i].rateAdsvcCtgCd+ '">';
            html += '<div class="c-accordion__inside" id="compareView'+arrUniqueList[i].rateAdsvcCtgCd+'">';
            html += '</div>';
            html += '</div>';
            html += '</li>';
            html += '</ul>';
            html += '</div>';
        }
        $("#tabB1-panel_"+idx).html(html);

        KTM.initialize(); // 아코디언을 새로 생성했으므로 동적 ui init
    } else {
        html += '<div class="c-nodata">';
        html += '<i class="c-icon c-icon--nodata" aria-hidden="true" ></i>';
        html += '<p class="c-noadat__text">변경 가능한 요금제가 없습니다.</p>';
        html += '</div>';
        $("#tabB1-panel_"+idx).html(html);
    }
}


var targetObj;
// 아코디언 클릭
function showUsimAccordion(rateAdsvcCtgCd, id, obj){

    //MCP.addAccordion2('#forId'+rateAdsvcCtgCd);

    // 아코디언을 닫는 경우 서버에 갔다올 필요 없음...
    // 아코디언을 동작시키기 위해서 true return
    if($("#forId"+rateAdsvcCtgCd+" div.c-accordion__head").hasClass("is-active")) return true;

    var varData = ajaxCommon.getSerializedData({
          rateAdsvcCtgCd : rateAdsvcCtgCd
         ,rateCd : $("#prvRateCd").val()
    });

    var url = "/m/mypage/farPriceContent.do";
    //페이지 호출
    ajaxCommon.getItem({
            id:'farPriceContent'
            ,cache:false
            ,url:url
            ,data: varData
            ,dataType:"json"
    }
    ,function(data){
        AccordionClickCallback(data,id, rateAdsvcCtgCd);
    });

}

function AccordionClickCallback(data, id, rateAdsvcCtgCd){

    var data = data.prodXmlList;
    var html ="";

    if(data.length > 0){
        for(var idx=0; idx<data.length; idx++){
            html += '<div class="c-card c-card--type2">';
            html += '<div class="c-card__box">';

            html += '<div class="c-card__title">';
            html += '  <div class="c-radio__box u-pt--20 c-flex">';
            html += '    <div class="u-width--60p">';
            html += '      <span class="c-card__subtitle" id="radA'+data[idx].rateAdsvcGdncSeq+'">'+data[idx].rateAdsvcNm+'</span>';
            html += '      <input id="rateAdsvcCd'+data[idx].rateAdsvcGdncSeq+'" type="hidden"  value='+data[idx].rateAdsvcCd+'>';
            html += '    </div>';
            html += '    <div>';
            html+= '       <button class="c-button c-button--xsm c-button--primary" onclick="btn_ratePop(\''+data[idx].rateAdsvcGdncSeq+'\',\''+data[idx].rateAdsvcCd+'\');">상세</button>';
            html+= '       <button class="c-button c-button--xsm c-button--primary u-ml--4" onclick="openFarChgModal(\''+data[idx].rateAdsvcGdncSeq+'\',\''+rateAdsvcCtgCd+'\');">변경</button>';
            html += '    </div>';
            html += '  </div>';
            html += '  <hr class="c-hr c-hr--type3 u-mt--20">';
            html += '</div>';

            html += '<div class="c-card__price-box u-co-mint">';
            html += '<input id="promotionAmtVatDesc'+data[idx].rateAdsvcGdncSeq+'" type="hidden"  value='+data[idx].promotionAmtVatDesc+'>';

            if(data[idx].promotionAmtVatDesc != null && data[idx].promotionAmtVatDesc != ""){
                html += '<span class="c-text c-text--type4">월 기본료(VAT 포함)</span>';
                html += '<span class="c-text u-td-line-through u-ml--auto" id="mmBasAmtVat'+data[idx].rateAdsvcGdncSeq+'">'+data[idx].mmBasAmtVatDesc+'</span>';
                html += '<span>';
                html += '<b>'+data[idx].promotionAmtVatDesc+'</b>원';
                html += '</span>';
            }else{
                html += '<span class="c-text c-text--type3">월 기본료(VAT 포함)</span>';
                html += '<span id="mmBasAmtVat'+data[idx].rateAdsvcGdncSeq+'">';
                html += '<b >'+data[idx].mmBasAmtVatDesc+'</b>원';
                html += '</span>';
            }
            html += '</div>';
            html += '</div>';
            html += '</div>';
        }

        $("#compareView"+rateAdsvcCtgCd).html(html);
        KTM.initialize ();

    } else {
        $("#compareView"+rateAdsvcCtgCd).html('-');

    }


}

//요금제 변경 상세팝업
function btn_ratePop(rateAdsvcGdncSeq,rateAdsvcCd ){
     openPage('pullPopup', '/m/rate/rateLayer.do?rateAdsvcGdncSeq='+rateAdsvcGdncSeq+'&rateAdsvcCd='+rateAdsvcCd+'','');
}


function buildOverUsageText(result) {
    const map = {
        isOverUsageDatCharge: "데이터",
        isOverUsageVoiCharge: "통화",
        isOverUsageSmsCharge: "문자"
    };

    // true 값만 필터링 → 한글명 배열 생성
    const list = Object.keys(map)
        .filter(key => result[key] === true)
        .map(key => map[key]);

    return list.length > 0 ? `[${list.join(" / ")}]` : "";
}

//요금제 변경 시작,안내 팝업
function openFarChgModal(seq,rateAdsvcCtgCd) {
    pageObj.rateGdncSeq = seq ;
    pageObj.rateAdsvcCtgCd = rateAdsvcCtgCd ;

    var varData = ajaxCommon.getSerializedData({
        ncn : $("#ncn option:selected").val()
    });
    ajaxCommon.getItem({
        id : 'isOverUsageCharge'
        , cache : false
        , async : false
        , url : '/mypage/isOverUsageChargeAjax.do'
        , data : varData
        , dataType : "json"
    },function(jsonObj){
        var resultCode = jsonObj.RESULT_CODE;
        if(resultCode=="00000"){
            if (jsonObj.isOverUsageCharge) {
                const overText = buildOverUsageText(jsonObj);
                $("#chgInfoTxt").text(overText);
                openModal('#farChgOvertModal');
                //openModal('#farChgStartModal');
            } else {
                //첫 번째 모달 오픈
                openModal('#farChgStartModal');
            }
        } else if (resultCode== "0002") {
            MCP.alert("로그인이 필요 합니다..");
        } else {
            MCP.alert("잠시후 다시 이용해주세요.");
            return false;
        }
    });
}


//첫 번째 모달 버튼 이벤트
$('#btnFarChgY').off('click').on('click', function() {
    closeModal('#farChgStartModal');
    openModal('#farChgWarningModal');
});

$('#btnFarChgN').off('click').on('click', function() {
    closeModal('#farChgStartModal');
    btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'S');
});

//두 번째 모달 버튼 이벤트
$('#btnFarChgReserve').off('click').on('click', function() {
    closeModal('#farChgWarningModal');
    btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'P');
});

$('#btnFarChgImmediate').off('click').on('click', function() {
    closeModal('#farChgWarningModal');
    btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'S');
});


$('#btnFarOverChgN').on('click', function() {
    closeModal('#farChgOvertModal');
    openModal('#farChgOvertModal2');
});


$('#btnFarOverChgY').on('click', function() {
    closeModal('#farChgOvertModal');
    btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'P');
});


$('#btnAgree').on('click', function() {
    closeModal('#farChgOvertModal2');
    btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'S');
});

function openModal(selector) {
    const el = $(selector)[0];
    const modal = KTM.Dialog.getInstance(el) || new KTM.Dialog(el);
    modal.open();
}

function closeModal(selector) {
    const el = $(selector)[0];
    const modal = KTM.Dialog.getInstance(el);
    if (modal) modal.close();
}
