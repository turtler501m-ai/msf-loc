
var pageObj = {
    niceType:""
    , initAddition:0
    , applDataForm:{}
    , niceHistSeq:0
    , niceHistSeq2:0
    , authObj:{}
    , authObj2:{}
    , authPassObj:{}
    , insrNiceHistSeq:0
    , niceHistInsrProdSeq:0
    , startTime:0
    , eventId:""
    , step:1
    , clauseJehuRateCount:0
    , clauseFinanceRateCount:0
    , additionBetaList:[]
    , additionKeyList:[]
    , additionTempKeyList:[]
    , reqAddition:[]
    , reqAdditionPrice:0
    , insrProdList:[]
    , insrProdCd:""      //안심보험 선택 코드값
    , insrProdObj:null   //{}
    , addrFlag:""
    , requestKey:0       //서식지 등록후 key값
    , resNo:0            //서식지 등록후 예약번호
    , niceResSmsObj:{}   //셀프개통 신규 인증한 SMS인증 정보
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증
    , niceResLogObj2:{}   //로그을 저장 하기 위한 인증
    , checkCnt:0         //callback 호출 건수
    , prdtNm:""          //상품명
    , rateNm:""          //요금제명
    , modelSalePolicyCode : "" //init 정책정보
    , modelId:""
    , telAdvice:false   //전화상담 여부
    , prmtIdList:[]     // 사은품 프로모션 코드
    , prdtIdList:[]    // 사은품 제품ID
    , phoneObj:null
    , giftList:[]
    , custPoint:null
    , cardDcCd:""
    , rateObj:null
    , fnReqPreCheckCount:0
    , priceLimitObj:null
    , usimOrgnId:""
    , maxDcAmtInt:0
    , cid:""
}
$(document).ready(function (){

     // 본인인증 전 추가 유효성 검사 > niceAuth.js 참고
    simpleAuthvalidation= function(){

        if (!$('#clauseSimpleOpen').prop('checked')) {
                MCP.alert("본인인증 절차 진행에 동의해야 합니다.");
            return false;
        }

//        var cstmrType = $("#selSvcCntrNo option:selected").attr("data-cstmr_type");
//        if (cstmrType != "I") {
//            MCP.alert("지원하지 않는 고객유형 입니다.");
//            return false;
//        }

        return true;
    }


    //조회요청
    $('#btnRequest').click(function(){
        if ($("#isAuth").val() != "1") {
            MCP.alert("본인 인증이 완료되지 않았습니다. ");
            return false;
        }

        var varData = ajaxCommon.getSerializedData({
            contractNum:$("#contractNum").val()
        });

        ajaxCommon.getItem({
            id:'insertPrvCommDataAjax'
            ,cache:false
            ,url:'/mypage/insertPrvCommDataAjax.do'
            ,data: varData
            ,async:false
            ,dataType:"json"
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                MCP.alert("수사기관에 고객님의 정보제공여부 사실조회 요청이 접수되었습니다. 10일이내 고객님께 문자로 결과를 안내드리며 결과 확인 후 세부내용은 홈페이지를 통해 확인 가능하십니다.");
                getDataList(); //조회 리스트
                $('#divBtnReq').hide(); //조회요청 버튼 숨김
            } else if (jsonObj.RESULT_CODE == "AUTH01"){ // AUTH 오류
                MCP.alert(jsonObj.RESULT_MSG);
            } else if (jsonObj.RESULT_CODE == "STEP01" || jsonObj.RESULT_CODE == "STEP02"){ // STEP 오류
                MCP.alert(jsonObj.RESULT_MSG);
            } else {
                MCP.alert("조회요청에 실패하였습니다. 자세한 사항은 고객센터(114/무료)로 문의 부탁드립니다.");
            }
        })
    });
});

var fnNiceCert = function(prarObj) {

    var strMsg= "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;

    //본인인증
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {

        var authInfoJson= {contractNum: $("#contractNum").val(), authType: prarObj.authType};
        var isAuthDone = mypageAuthCallback(authInfoJson);

        if(isAuthDone){ // 본인인증 성공 시 처리
            pageObj.authObj= prarObj ;
            MCP.alert("본인인증에 성공 하였습니다.");

            $('#divBtnReq').show(); //조회요청 버튼 노출
            $('#divDataList').show(); //통신자료 제공내역 조회결과 노출
            getDataList(); //조회 리스트

            pageObj.telAdvice = false;
            return null;
        }else{ // 본인인증 실패 시 처리

            var resultCd= niceAuthObj.resAuthOjb.RESULT_CODE;
            var errorMsg= niceAuthObj.resAuthOjb.RESULT_MSG;

            if (resultCd == "LOGIN") {
                MCP.alert(errorMsg, function () {
                    location.href = '/m/loginForm.do';
                });
                return null;
            }

            strMsg= (errorMsg == undefined) ? strMsg : errorMsg;
            MCP.alert(strMsg);
            return null;
        }
    }
}

function getDataList(){

    var contentHtml= "";
    $("#dataList").html("");

    var varData = ajaxCommon.getSerializedData({
        contractNum:$("#contractNum").val()
    });

    ajaxCommon.getItem({
        id:'pvcCommDataListAjax'
        ,cache:false
        ,url:'/mypage/pvcCommDataListAjax.do'
        ,data: varData
        ,async:false
        ,dataType:"json"
    }, function(jsonObj){

        var resultCd= jsonObj.RESULT_CODE;
        var list = jsonObj.dataList;

        var htmlContent= "내역이 존재하지 않습니다.";
        if(resultCd == "STEP01") htmlContent= "[STEP] 본인 인증 정보가 일치하지 않습니다.";

        if(list == null || list == undefined || list.length== 0){ // 데이터가 없는 경우
            contentHtml= '<tr><td colspan="4">'+htmlContent+'</td></tr>';
        }else{ // 데이터가 존재하는 경우

            for(var i=0;i<list.length;i++){
                var item= list[i];
                contentHtml+= '<tr>';
                contentHtml+= 	'<td>'+item.rnum+'</td>';
                contentHtml+= 	'<td>'+item.apyDt+'</td>';
                contentHtml+= 	'<td>'+item.resultYn+'</td>';
                contentHtml+= 	'<td>'+item.isInvstProcNm+'</td>';
                contentHtml+= '</tr>';
            }
        }
        $("#dataList").html(contentHtml);
    })
}


function select(){
    ajaxCommon.createForm({
        method:"post"
        ,action:"/m/mypage/prvCommData.do"
    });

    ajaxCommon.attachHiddenElement("ncn", $("#ctn").val());
    ajaxCommon.formSubmit();
}


