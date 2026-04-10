var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
}

$(document).ready(function() {
   for (var i = 0; i < document.getElementsByClassName('flexible').length; i++) {
        var id = document.getElementsByClassName('flexible')[i].id;
        flexibleComponent(id);
    }


   if($('#promoFinish').val() == 'promoFinish'){

       let today = new Date();
       let year = today.getFullYear(); // 년도
       let month = ('0' + (today.getMonth() + 1)).slice(-2);  // 월
       let date = ('0' + today.getDate()).slice(-2);  // 날짜
       $('#agreeDate').html(year + '.' + month + '.' + date);

       setTimeout(function(){
           $('#promoFinishBtn').trigger('click');
           history.replaceState({}, null, location.pathname);
       }, 200);
   }


});


function flexibleComponent(componentId) {

    ajaxCommon.getItem({
            url: "/termsPop.do",
            type: "GET",
            dataType: "json",
            data: "cdGroupId1=INFOPRMT&cdGroupId2=" + componentId,
            async: false
        }
        ,function(data) {
            if (data.docContent != null) {
                var inHtml = unescapeHtml(data.docContent);
                $('#' + componentId).html(inHtml);
            }
        }
    );
}

$("button[name=userPromoBtn]").on("click", function(){

    var codeEndDd = $('#codeEndDd').val();

    if(codeEndDd == 'N'){
        MCP.alert("이벤트 진행기간이 아닙니다.");
        return false;
    }

    var userlogin = $('#userlogin').val();

    if(userlogin == 'Y'){
         MCP.alert("본 이벤트는 신규 가입 회원만 참여 가능합니다.");
         return false;
    }

});

// 휴대폰 인증 팝업 호출
function pop_nice() {
    pageObj.niceType = NICE_TYPE.CUST_AUTH ;
    openPage('outLink','/nice/popNiceAuth.do?sAuthType=M&mType=Mobile','');
}


//nice 인증 완료 후 return

function fnNiceCert(obj) {
    niceAuthResult(obj)
}


function niceAuthResult(prarObj){

    var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;

    var reqSeq = prarObj.reqSeq;
    var resSeq = prarObj.resSeq;

    var authInfoJson ={contractNum: "", ncType: "0"};
    var isAuthDone = diAuthCallback(authInfoJson);

    if(isAuthDone){
        pageObj.niceHistSeq = diAuthObj.resAuthOjb.NICE_HIST_SEQ ;
        var data = {resSeq:resSeq, reqSeq:reqSeq};

        // 회원가입 팝업
        openPage("userPromotionPop", "/m/event/userPromotionPop.do", data)

    }else{

        strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
        MCP.alert(strMsg);
        return null;
    }
}


function promoMstore(variable) {
    if(variable == 'uMstrore'){
        location.href = '/m/point/mstoreView.do';
    }else if(variable == 'uPrize'){
        location.href = '/m/mypage/myPromotionList.do';
    }
}