;

var v_ctgList; // xml 카테고리 목록
var v_proList; // xml 상품 목록
var v_rateAdsvcDivCd = "ADDSV";
var v_roamCtgCd = 137;

$(document).ready(function(){
    // xml 데이터 초기화
    initXmlData().then(function(){

        let html = "";
        html += '<button id="mainTab_all" class="c-button c-button--sm c-button--white c-tabs__main mainTab mainTabTop" role="tab" onclick="mainTabAllClick();" aria-selected="false" >전체</button>'

        $("#subbody_loading").remove();
        MCP.addSingleTab('#mainTab', '', html, documentReady);

        $.each(v_ctgList, function(idx, item) {
            if(item.depthKey == 2 && item.rateAdsvcDivCd == v_rateAdsvcDivCd
                && isMiddleDate(item.pstngStartDate, item.pstngEndDate)
                && item.ctgOutputCd == "CO00130001"
                && item.upRateAdsvcCtgCd == v_roamCtgCd) {
                html = "";
                if(item.rateAdsvcCtgNm.startsWith("Top")) {
                    html += '<button id="mainTab_top" class="c-button c-button--sm c-button--white c-tabs__main mainTab mainTabTop" role="tab" onclick="mainTabClick(this);" rateAdsvcCtgCd='+item.rateAdsvcCtgCd+' aria-selected="false">'
                    html += item.rateAdsvcCtgNm
                    html += '</button>';
                } else {
                    html += '<button id="mainTab_'+idx+'" class="c-button c-button--sm c-button--white c-tabs__main mainTab mainTabSub" role="tab" onclick="mainTabClick(this);" rateAdsvcCtgCd='+item.rateAdsvcCtgCd+' aria-selected="false">'
                    html += item.rateAdsvcCtgNm
                    html += '</button>';
                }

                $("#subbody_loading").remove();
                MCP.addSingleTab('#mainTab', null, html, null);
            }
        });
    }).catch(
        //
    );


    //셀프가입 가능 체크박스 선택, 해제 시
    $(".c-checkbox").change(function() {
        if($("#mainTab_all").is(".is-active")) {
            mainTabAllClick();
        } else {
            mainTabClick(null);
        }
    });

});

// xml 데이터 초기화
var initXmlData = function() {
    return new Promise(function(resolve, reject) {
        $.ajax({
            type : 'GET'
            , url : '/m/rate/roamingListAjax.do'
            , dataType : 'json'
            , success : function(data) {
                v_ctgList = data.item.ctgList.item;
                v_proList = data.item.proList.item;

                resolve();
            }
        });
    });
}

// 첫번째 메인탭 클릭
function documentReady(tabId) {
    $('#mainTab_all').on("click", function(){
        $('#mainTab > button').removeClass('is-active');
        $(this).addClass('is-active');
        $('#mainTab > button').removeAttr("aria-hidden");
        $('#mainTab > button').attr('aria-selected',"false");
        $(this).attr('aria-selected',"true");
    });

    // 외부에서 url 호출할때 사용
    var paramRateAdsvcCtgCd = $("#paramRateAdsvcCtgCd").val();
    if(paramRateAdsvcCtgCd !=null && paramRateAdsvcCtgCd !=""){
        $(".mainTab").each(function(idx){
            var rateAdsvcCtgCd = $(this).attr("rateAdsvcCtgCd");
            if(paramRateAdsvcCtgCd==rateAdsvcCtgCd){
                $($('.c-tabs__main')[idx]).click();
                return false;
            }
        });
    } else {
        // 메인탭 클릭 이벤트 실행
        $($('.c-tabs__main')[0]).click();
    }

    var paramRateAdsvcGdncSeq = $("#paramRateAdsvcGdncSeq").val();
    if(paramRateAdsvcGdncSeq !=null && paramRateAdsvcGdncSeq !="" && paramRateAdsvcGdncSeq != 0) {
        $(".c-accordion__button").each(function (idx) {
            var rateAdsvcGdncSeq = $(this).data("seq");
            if (paramRateAdsvcGdncSeq == rateAdsvcGdncSeq) {
                $(this).click();
                setTimeout(function () {
                    $("#serviceJoin" + paramRateAdsvcGdncSeq).focus();
                    $("#serviceJoin" + paramRateAdsvcGdncSeq).click();
                    return false;
                }, 100);
            }
        });
    }
}









//메인탭 전체 클릭시
function mainTabAllClick(){
    let selfYn = $("#chkSelf").is(":checked")? "Y" : "N";

    var varData = ajaxCommon.getSerializedData({
        selfYn : selfYn
    });

    url = "/m/rate/roamingAllListPanel.do";
    //페이지 호출
    MCP.ajaxGet(url, varData, 'text', function(resp) {
        mainTabClickCallback(resp);
    });
}

// 메인탭 클릭 시
function mainTabClick(tab){
    if($(tab).is(".mainTabTop") === true) {
        $('#mainTab > button').removeClass('is-active');
        $(tab).addClass('is-active');
        $('#mainTab > button').removeAttr("aria-hidden");
        $('#mainTab > button').attr('aria-selected',"false");
        $(tab).attr('aria-selected',"true");
    } else if($(tab).is(".mainTabSub") === true) {
        if($(tab).attr('aria-selected') === "true") {
            $(tab).removeClass('is-active');
            $(tab).attr('aria-selected',"false");
        } else if($(tab).attr('aria-selected') === "false") {
            $(tab).addClass('is-active');
            $(tab).attr('aria-selected',"true");
        }

        $('.mainTabTop').removeClass('is-active');
        $('.mainTabTop').attr('aria-selected',"false");
        $('#mainTab > button').removeAttr("aria-hidden");
    }

    //CtgCd array에 담아 ajax 전송하는 코드
    let ctgCdList = [];
    $("#mainTab > .is-active").each(function(idx, item) {
        ctgCdList.push($(item).attr("rateAdsvcCtgCd"));
    })

    let selfYn = $("#chkSelf").is(":checked")? "Y" : "N";

    var varData = ajaxCommon.getSerializedData({
        ctgCdList : ctgCdList,
        selfYn : selfYn
    });

    url = "/m/rate/roamingListPanel.do";
    //페이지 호출
    MCP.ajaxGet(url, varData, 'text', function(resp) {
        mainTabClickCallback(resp);
    });
}


// 메인탭 클릭 콜백
function mainTabClickCallback(resp){
    $('#listPanel').html(resp);
    $('#totalNum').html('전체 개수 : ' + $('#listPanel2 > li').length + '개');
}



/* 목록 아코디언 이벤트 */
var targetObj;
// 아코디언 클릭 시
function showUsimAccordion(rateAdsvcGdncSeq, id, obj){
    targetObj = $(obj).parent().parent().find('.c-accordion__inside');
    var varData = ajaxCommon.getSerializedData({
        subTabId : 'sub'+id
        , rateAdsvcGdncSeq : rateAdsvcGdncSeq
    });
    url = "/m/rate/roamingContent.do";
    // ajax 콜
    MCP.ajaxGet(url, varData, 'text', AccordionClickCallback);
}
// 목록 아코디언 이벤트 콜백
function AccordionClickCallback(resp){
    $(targetObj).html(resp);
    MCP.init();
}


// 현재일이 이벤트 시작일과 종료일 사이인지 체크
var isMiddleDate = function(startDate, endDate){
    var sDate = exportNumber(startDate);
    var eDate = exportNumber(endDate);
    var cDate = exportNumber(getDate());

    if(sDate!='' && eDate!=''){
        if(parseInt(sDate)<=parseInt(cDate) && parseInt(eDate)>=parseInt(cDate)){
            return true;
        }
    }
    return false;
};
//
// 현재 날자 구하기
function getDate() {
    var today = new Date();

    var year = today.getFullYear();
    var month = ("0" + (today.getMonth()+1)).slice(-2);
    var day = ("0" + today.getDate()).slice(-2);
    var hours = ('0' + today.getHours()).slice(-2);
    var minutes = ('0' + today.getMinutes()).slice(-2);
    var seconds = ('0' + today.getSeconds()).slice(-2);

    var dateString = year + '-' + month  + '-' + day +' '+ hours + ':' + minutes  + ':' + seconds;

    return dateString;
}

//서비스 가입하기
function serviceJoin(rateAdsvcGdncSeq) {

    //정회원 체크
    ajaxCommon.getItem({
            id: "checkLogin"
            , cache: false
            , url: "/m/rate/checkUser.do"
            , data: ""
            , dataType: "json"
            , async: false
        }
        , function (data) {

            if (data.isLogin == false) {
                KTM.Confirm('M모바일을 사용하시는<br>회원 전용 서비스 입니다.<br><br>로그인 후 이용 하시겠습니까?', function() {
                    var redirectUrl = $(location).attr('href') + '?rateAdsvcGdncSeq=' + rateAdsvcGdncSeq;
                    location.href = '/loginForm.do?redirectUrl=' + redirectUrl;
                });
            } else {
                if (data.userDivision == "00") {
                    location.href = '/mypage/multiPhoneLine.do';
                } else if (data.userDivision == "01") {
                    if(data.underAge == true){
                        MCP.alert("미성년자의 경우 고객센터를 통해서만 가입이 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.",function (){
                            return false;
                        });
                    }else{
                    var parameterData = ajaxCommon.getSerializedData({
                        rateAdsvcGdncSeq: rateAdsvcGdncSeq
                    });

                    //팝업 띄우기
                    openPage('pullPopup', '/m/rate/roamingPop.do', parameterData, 1);
                  }
               }
            }
        });
}
