var v_ctgList; // xml 카테고리 목록
var v_proList; // xml 상품 목록

var pageObj = {
    rateGdncSeq:""
}


$(document).ready(function (){

    $("#modalArs").addClass("rate-detail-view");
    rateResSearch($("#ncn option:selected").val());
    //loadXmlData();


});

function btn_search(){
    ajaxCommon.createForm({
        method:"post"
        ,action:"/mypage/farPricePlanView.do"
    });
    var ncn = $("#ncn option:selected").val();
    ajaxCommon.attachHiddenElement("ncn",ncn);
    ajaxCommon.formSubmit();

}
function loadXmlData(){
    //로딩시 카테고리 조회
    var html ='';
    $("#1depthList").html('');
    $("#tabChk").val('');


    initXmlData().then(function(){
        $.each(v_ctgList, function(idx, item) {
            var outHtml = '';

            if(item.ctgOutputCd == $("#ctgOutputCd").val()){
                if(item.depthKey == 1 && item.rateAdsvcDivCd == $("#rateAdsvcDivCd").val()) {
                    html = '<button class="c-tabs__button" id="tab'+idx+'" role="tab" onclick="mainTabClick(\''+item.rateAdsvcCtgCd+'\',\''+idx+'\');">'+item.rateAdsvcCtgNm+'</button>';

                    outHtml +='<div class="c-tabs__panel"  id="tabD'+idx+'panel"   tabindex="0" style="display: none;">';
                    outHtml +='<div class="c-tabs c-tabs--type2">';
                    outHtml +='<div class="c-tabs__list" role="tablist" id="tabDtl'+idx+'">';
                    outHtml += '</div>';
                    outHtml +='<div class="c-tabs__panel u-block" id="rateDtl'+item.rateAdsvcDivCd+'" >';
                    outHtml +=' <div class="c-row c-row--lg">';
                    outHtml +=' <div class="c-accordion c-accordion--type3" data-ui-accordion="">';
                    outHtml +='<ul class="c-accordion__box" id="btn_item'+idx+'">';
                    outHtml +='</ul>';
                    outHtml +='</div>';
                    outHtml +='</div>';
                    outHtml +='</div>';
                    outHtml += '</div>';
                    outHtml += '</div>';
                    $(".tab_view").append(outHtml);

                    MCP.addSingleTab('#1depthList', '.c-tabs__button', html,depth1TabCallback);
                }
            }


            $('button[id^=tab]').each(function(){
                $('button[id^=tab]').removeAttr("aria-hidden");
                var hasClass = $(this).hasClass("is-active");
                if(hasClass){
                   $(this).attr('aria-selected',"true");
                }else {
                    $(this).attr('aria-selected',"false");
                }
            });

            $('button[id^=tab]').on("click", function(){
                $('button[id^=tab]').removeClass('is-active');
                $('button[id^=tab]').removeAttr("aria-hidden");
                $('button[id^=tab]').attr('aria-selected',"false");
                $(this).addClass('is-active');
                $(this).attr('aria-selected',"true");
            });


        });

    }).catch(
  );


}

//첫번째 카테고리
function depth1TabCallback(){
    $($('.c-tabs__button')[0]).click();
}


function rateResSearch(contractNum){
    var varData = ajaxCommon.getSerializedData({
              ncn : contractNum
            // ,prvRateCd :$("#prvRateCd").val()
    });
     ajaxCommon.getItem({
            id:'myRateResvViewAjax'
            ,cache:false
            ,url:'/mypage/myRateResvViewAjax.do'
            ,data: varData
            ,dataType:"json"
     }
     ,function(jsonObj){
        var myPriceInfo=  jsonObj.myPriceInfo;
        if(jsonObj.RESULT_CODE == "S"){

            $("#prvRateGrpNm").html("이용중인 요금제는<br><b>"+jsonObj.prvRateGrpNm+"</b>입니다.");
            $("#rateAdsvcLteDesc").text(jsonObj.rateAdsvcLteDesc);
            $("#rateAdsvcCallDesc").text(jsonObj.rateAdsvcCallDesc);
            $("#rateAdsvcSmsDesc").text(jsonObj.rateAdsvcSmsDesc);
            $("#prvRateNm").val(jsonObj.prvRateGrpNm);
            $("#prvRateGdncSeq").val(jsonObj.prvRateGdncSeq); // 이용중인 요금제 안내 일련번호

            if(myPriceInfo != null){

                var appStartDd = myPriceInfo.appStartDd;
                if(appStartDd.length >= 8){
                    $("#appStartDdDate").html("신청일 : "+ appStartDd.substring(0,4)+"."+ appStartDd.substring(4,6)+"."+appStartDd.substring(6,8));
                }

                /*if(myPriceInfo.instMnthAmt != null){
                    $("#instAmt").html(numberWithCommas(parseInt(myPriceInfo.instMnthAmt))+ "원");
                }*/


                var outHtml = '';
                if(myPriceInfo.rsrvPrdcCd != ""){ //예약된 요금조회
                    outHtml +=' <tr>';
                    outHtml +=' <td>'+myPriceInfo.rsrvPrdcNm+'</td>';
                    outHtml +=' <td> 월'+numberWithCommas(parseInt(myPriceInfo.rsrvBasicAmt))+'원 </td>';
                    outHtml += '<td>';
                    outHtml += ' <button class="c-button c-button--underline c-button--sm u-co-sub-4"  onclick="btn_canCel();">취소</button>';
                    outHtml += '</td>'
                    outHtml+=' </tr>';
                    $("#rsrvPrdcView").html(outHtml);
                    $("#rsrvPrdcChk").val("Y");
                    $(".resYn").show();
                } else {
                    $(".resYn").hide();
                }


                $("#prvRateCd").val(myPriceInfo.prvRateCd);

                var html = '';

                if(jsonObj.total == 0){
                    $(".tab_view").html('');
                    html += '<div class="c-nodata">';
                    html += '<i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
                    html += ' <p class="c-nodata__text">변경 가능한 요금제 존재하지 않습니다.</p>';
                    html += '</div>';
                    $(".tab_view").html(html);
                }else {

                    $(".tab_view").html('');
                    html += '<div class="c-tabs c-tabs--type1">';
                    html += '<div class="c-tabs__list u-width--100p" role="tablist" id="1depthList">';
                    html += '</div>';
                    html += ' </div>';
                    $(".tab_view").html(html);

                    loadXmlData();
                }

            }




        }




    });
}

function get_date_str(date)
{
    var sYear = date.getFullYear();
    var sMonth = date.getMonth() + 1;
    var sDate = date.getDate();

    sMonth = sMonth > 9 ? sMonth : "0" + sMonth;
    sDate  = sDate > 9 ? sDate : "0" + sDate;
    return sYear + sMonth + sDate;
}


//예약취소
function btn_canCel(){
    var varData = ajaxCommon.getSerializedData({
              contractNum : $("#ncn option:selected").val()
    });
    KTM.Confirm("변경예약을 취소하시겠습니까?", function (){
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

// 로딩시 카테고리 조회
var initXmlData = function() {
    return new Promise(function(resolve, reject) {
    KTM.LoadingSpinner.show();

    $.ajax({
            type : 'GET'
            , url : '/mypage/farPriceCtgTab1Ajax.do'
            , dataType : 'json'
            , success : function(data) {
                v_ctgList = data.item.ctgList.item;
                v_proList = data.item.proList.item;
                resolve();
                KTM.LoadingSpinner.hide();
            } , error : function(){
                KTM.LoadingSpinner.hide();
            }
        });
    });
}

// 메인탭 클릭 시
function mainTabClick(rateAdsvcCtgCd, idx){


    var varData = ajaxCommon.getSerializedData({
         rateAdsvcCtgCd : rateAdsvcCtgCd
       , rateCd :$("#prvRateCd").val()
    });
       url = "/mypage/farPriceCtgTab2Ajax.do";
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
    $("#upRateAdsvcCtgCd").val(resp.upRateAdsvcCtgCd);
    $("#tabD"+$("#tabChk").val()+"panel").hide();
    var buttonXmlList = resp.buttonXmlList;
    var outHtml = '';

    if(buttonXmlList != ""){
        for(var i=0; i< buttonXmlList.length; i++){
            outHtml +='<button class="c-tabs__button"  id="subTab_'+buttonXmlList[i].rateAdsvcCtgCd+'" role="tab"  aria-selected="false" tabindex="0" onclick="subTabClick(\''+buttonXmlList[i].rateAdsvcCtgCd+'\',\''+idx+'\')">'+buttonXmlList[i].rateAdsvcCtgNm+'</button>';
        }

    }

    $("#tabDtl"+idx).html(outHtml);
    $("#tabD"+idx+"panel").show();
    $("#tabChk").val(idx);

    if(buttonXmlList == ""){
        subTabClick($("#upRateAdsvcCtgCd").val(), idx);
    }


    $('button[id^=subTab_]').on("click", function(){
        $('button[id^=subTab_]').removeClass('is-active');
        $('button[id^=subTab_]').attr('aria-selected',"false");
        $(this).addClass('is-active');
        $(this).attr('aria-selected',"true");


    //	$('button[id^=subTab_]').attr('aria-hidden',"false");
    //	$(this).attr('aria-hidden',"true");

    //  $('#mainTabList  button').removeAttr("aria-hidden");
    //	$('#mainTabList  button').attr('aria-selected',"false");
    // 	$(this).attr('aria-selected',"true");
    });

    if($("#tabDtl"+idx+" "+"button[id^=subTab_]").length == 0){
            subTabClick($("#upRateAdsvcCtgCd").val(), idx);
    } else{
        $("#tabD"+idx+"panel").find("button[id^=subTab_]")[0].click();
    }

}

// 서브탭 클릭 시
function subTabClick(rateAdsvcCtgCd, idx){


    //$("#tabD"+idx+"panel").append(html);
    //$("#rateDtl"+rateAdsvcCtgCd).show();

    var varData = ajaxCommon.getSerializedData({
         rateAdsvcCtgCd : rateAdsvcCtgCd
         ,rateCd : $("#prvRateCd").val()
    });


    url = "/mypage/farPriceCtgTab3Ajax.do";
    //페이지 호출
    ajaxCommon.getItem({
            id:'farPriceCtgTab3Ajax'
            ,cache:false
            ,url:url
            ,data: varData
            ,dataType:"json"
    }
    ,function(data){
        subTabClickCallback(data,rateAdsvcCtgCd, idx);
    });
}

// 서브탭 클릭  콜백
function subTabClickCallback(resp,rateAdsvcCtgCd, idx){


    var data = resp.ctgXmlList;
    var html ="";

      $("#strRateAdsvcCtgCd").val('');
    if(data.length > 0){
        const arrDup = data;
        const arrUniqueList = arrDup.filter((character, idx, arr)=>{
            return arr.findIndex((item) => item.rateAdsvcCtgCd === character.rateAdsvcCtgCd) === idx
        });

        for(var i=0; i<arrUniqueList.length; i++){
            $("#strRateAdsvcCtgCd").val(arrUniqueList[i].rateAdsvcCtgCd);
            html += '<li class="c-accordion__item"  id="forId'+arrUniqueList[i].rateAdsvcCtgCd+'">';
            html += '<div class="c-accordion__head">';
            html += '<div class="product__title-wrap">';
            html += '<strong class="product__title">'+arrUniqueList[i].rateAdsvcCtgNm+'</strong>';
            html += '<span class="product__sub">'+arrUniqueList[i].rateAdsvcCtgBasDesc+'</span>';
            html += '</div>';
            html += '<button class="runtime-data-insert c-accordion__button" id="acc_header_a'+arrUniqueList[i].rateAdsvcCtgCd+'" type="button" aria-expanded="false" aria-controls="acc_content_a'+arrUniqueList[i].rateAdsvcCtgCd+'" onclick="showUsimAccordion('+arrUniqueList[i].rateAdsvcCtgCd+', '+idx+', this)">';
            html += '<span class="c-hidden">'+arrUniqueList[i].rateAdsvcCtgNm+' 정보 펼쳐보기</span>';
            html += '</button>';
            html += '</div>';
            html += '<div class="c-accordion__panel expand" id="acc_content_a'+arrUniqueList[i].rateAdsvcCtgCd+'" aria-labelledby="acc_header_a'+arrUniqueList[i].rateAdsvcCtgCd+'">';
            html += '<div class="c-accordion__inside">';
            html += ' <div class="c-table c-table--product u-mt--16" id="content_view'+arrUniqueList[i].rateAdsvcCtgCd+'">';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</li>';
        }

        $("#btn_item"+idx).html(html);


        $(".runtime-data-insert").click(function(){
            var hasClass = $(this).hasClass("is-active");
            var title = $(this).siblings().find(".product__title").text();

            if(hasClass){
                $(this).children().remove();
                $(this).append("<span class='c-hidden'>" + title + " 정보 펼쳐보기</span>");
            } else {
                $(this).children().remove();
                $(this).append("<span class='c-hidden'>" + title + " 정보 접기</span>");
            }
        });





    } else {
        html += '<div class="c-nodata">';
        html += '<i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
        html += ' <p class="c-nodata__text">변경 가능한 요금제 존재하지 않습니다.</p>';
        html += '</div>';

        $("#btn_item"+idx).html(html);
    }

    KTM.initialize();

}

function showUsimAccordion(rateAdsvcCtgCd, id){

    var varData = ajaxCommon.getSerializedData({
          rateAdsvcCtgCd : rateAdsvcCtgCd
        ,rateCd : $("#prvRateCd").val()
    });

    var url = "/mypage/farPriceContent.do";
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
        html+= '<table>'
        html+= '<caption>요금제 선택, 요금제, 기본료, 상세보기로 구성된 변경 가능한 요금제 정보</caption>'
        html+= '<colgroup>';
        html+= '<col style="width: 365px">'; // 요금제
        html+= '<col style="width: 330px">'; // 기본료
        html+= '<col style="width: 90px">';  // 상세보기
        html+= '<col>';                      // 변경하기
        html+= '</colgroup>';
        html+= '<thead>';
        html+= '<th scope="col">요금제</th>';
        html+= '<th scope="col">기본료</th>';
        html+= '<th scope="col">상세보기</th>';
        html+= '<th scope="col">변경하기</th>';
        html+= '</thead>';
        html+= '<tbody class="paymentView">';
        for(var idx=0; idx<data.length; idx++){

            html+= '<tr >';

            // 요금제 (column 1)
            html+= '<td id="rateAdsvcNm'+data[idx].rateAdsvcGdncSeq+'">'+data[idx].rateAdsvcNm+'</td>';

            // 기본료 (column 2)
            if(data[idx].promotionAmtVatDesc != null && data[idx].promotionAmtVatDesc != "" && data[idx].promotionAmtVatDesc != "0"){
                html+= '<td>';
                html+= '  <span class="u-td-line-through c-text--fs15">월'+data[idx].mmBasAmtVatDesc+' 원</span>';
                html+= '  <span class="u-co-mint c-text--fs18 u-ml--12 u-fw--medium">';
                html+= '    <span class="c-hidden">프로모션 요금</span>월'+data[idx].promotionAmtVatDesc+' 원';
                html+= '  </span>';
                html+= '</td>';
            } else {
                html+= '<td> 월 ' +data[idx].mmBasAmtVatDesc+'원 </td>';
            }

            // 상세보기 (column 3)
            html+= '<td>';
            html+= '  <button class="c-button c-button--underline c-button--sm u-co-sub-4" onclick="btn_ratePop(\''+data[idx].rateAdsvcGdncSeq+'\',\''+data[idx].rateAdsvcCd+'\');"> 보기<span class="c-hidden">요금제 상세보기 팝업</span></button>';
            html+= '</td>';

            // 변경하기 (column 4)
            html+= '<td>';
            html+= '  <button class="c-button c-button--underline c-button--sm u-co-sub-4" onclick="openFarChgModal(\''+data[idx].rateAdsvcGdncSeq+'\')">변경<span class="c-hidden">요금제 변경하기 팝업</span></button>';
            html+= '  <input id="rateAdsvcCd'+data[idx].rateAdsvcGdncSeq+'" type="hidden"  value='+data[idx].rateAdsvcCd+'>';
            html+= '  <input id="mmBasAmtVatDesc'+data[idx].rateAdsvcGdncSeq+'" type="hidden"  value='+data[idx].mmBasAmtVatDesc+'>';
            html+= '  <input id="promotionAmtVatDesc'+data[idx].rateAdsvcGdncSeq+'" type="hidden"  value='+data[idx].promotionAmtVatDesc+'>';
            html+= '</td>';

            html+= '</tr>';
        }
        html+= '</tbody>';
        html+= ' </table>';
        $("#content_view"+rateAdsvcCtgCd).html(html);

    } else{
        $("#content_view"+rateAdsvcCtgCd).html('');
    }


}

//요금제변경
function btn_farChg(seq,chkRadion){

    if($("#rsrvPrdcChk").val() == "Y" ){
        MCP.alert("등록된 요금제 예약변경 취소 후 요금제 변경이 가능합니다.");
        return;
    }
    if($(".paymentView").length == 0 || !seq){
        MCP.alert("가입하실 요금제를 선택해 주세요.");
        return;
    }

    var varData = ajaxCommon.getSerializedData({
        ncn:$("#ncn option:selected").val()
    });

    ajaxCommon.getItem({
        id:'ssibleState'
        ,cache:false
        ,url:'/mypage/possibleStateCheckAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(jsonObj){
        var arr =[];
        var outHtml = '';
        if (jsonObj.RESULT_CODE == "S") {

            var baseAmt = '';

            if($("#promotionAmtVatDesc"+seq).val() != null && $("#promotionAmtVatDesc"+seq).val() != ''){
                baseAmt = $("#promotionAmtVatDesc"+seq).val();
            }else{
                baseAmt = $("#mmBasAmtVatDesc"+seq).val();
            }

            var parameterData = ajaxCommon.getSerializedData({
                contractNum : $("#ncn option:selected").val()
                ,instAmt : $("#instAmt").text()
                ,prvRateGrpNm:$("#prvRateNm").val()
                ,rateNm : $("#rateAdsvcNm"+seq).text()
                ,baseAmt : baseAmt
                ,rateAdsvcCd : $("#rateAdsvcCd"+seq).val()
                ,nxtRateGdncSeq : seq
                ,prvRateGdncSeq : $("#prvRateGdncSeq").val()
                ,isActivatedThisMonth : jsonObj.isActivatedThisMonth //이번달 개통여부
                ,isFarPriceThisMonth : jsonObj.isFarPriceThisMonth //이번달 요금제 변경여부
                ,chkRadion : chkRadion
            });


            openPage('pullPopupByPost','/mypage/regServicePop.do',parameterData);

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
//요금제 변경 상세팝업
function btn_ratePop(rateAdsvcGdncSeq,rateAdsvcCd ){

     openPage('pullPopup', '/rate/rateLayer.do?rateAdsvcGdncSeq='+rateAdsvcGdncSeq+'&rateAdsvcCd='+rateAdsvcCd+'','');
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
function openFarChgModal(seq) {
    pageObj.rateGdncSeq = seq ;
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
    btn_farChg(pageObj.rateGdncSeq,'S');
});

//두 번째 모달 버튼 이벤트
$('#btnFarChgReserve').off('click').on('click', function() {
    closeModal('#farChgWarningModal');
    btn_farChg(pageObj.rateGdncSeq,'P');
});

$('#btnFarChgImmediate').off('click').on('click', function() {
    closeModal('#farChgWarningModal');
    btn_farChg(pageObj.rateGdncSeq,'S');
});


$('#btnFarOverChgN').on('click', function() {
    closeModal('#farChgOvertModal');
    openModal('#farChgOvertModal2');
});


$('#btnFarOverChgY').on('click', function() {
    closeModal('#farChgOvertModal');
    btn_farChg(pageObj.rateGdncSeq,'P');
});


$('#btnAgree').on('click', function() {
    closeModal('#farChgOvertModal2');
    btn_farChg(pageObj.rateGdncSeq,'S');
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