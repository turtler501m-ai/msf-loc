
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
    , onlineAuthType:""
    , onlineAuthInfo:""
}
$(document).ready(function (){

     // 본인인증 전 추가 유효성 검사 > niceAuth.js 참고
    simpleAuthvalidation= function(){

        if (!$('#clauseSimpleOpen').prop('checked')) {
                MCP.alert("본인인증 절차 진행에 동의해야 합니다.");
            return false;
        }

         // 2) 실명 및 본인인증 입력 값 검사
        validator.config={};
        validator.config['cstmrName'] = 'isNonEmpty';

        var idNum = $.trim($("#cstmrNativeRrn02").val()).substring(0,1);
        if (idNum == '5' || idNum == '6' || idNum == '7' || idNum == '8') {  //외국인
            validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isFngno';
            cstmrType = "FN";
        } else {
            var age = fn_getAge($.trim($("#cstmrNativeRrn01").val()),idNum);
            if (age < 19) { // 청소년
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isTeen';
                cstmrType = "NM";
            } else { // 내국인
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
                cstmrType = "NA";
            }
        }

        if(validator.validate()){ // 유효성 검사 통과
            return true;
        }else{ // 유효성 검사 실패

            // 유효성 검사 불충족시 팝업 표출
             var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(),function (){
               $selectObj = $("#"+errId);
               viewAuthErrorMgs($selectObj, validator.getErrorMsg()); // 에러 메세지 표출
               $('#' + validator.id).focus(); // 포커스
            });
            return false;
        }
    }


    //조회요청
    $('#btnReq').click(function(){
        getDataList();
    });
});

var fnNiceCert = function(prarObj) {
    var cstmrNativeRrn ;
    var cstmrName ;

    var strMsg= "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;

    //본인인증
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {

        cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val());
        cstmrName = $.trim($("#cstmrName").val());

        var authInfoJson = {cstmrName: cstmrName, cstmrNativeRrn: cstmrNativeRrn, authType: prarObj.authType};
        var isAuthDone = authCallback(authInfoJson);

        if (isAuthDone) { // 본인인증 성공 시 처리
            pageObj.authObj = prarObj;
            pageObj.onlineAuthType = prarObj.authType;
            MCP.alert("본인인증에 성공 하였습니다.");

            // 고객정보 변경 불가 처리
            $('#cstmrName').prop('readonly', 'readonly');
            $('#cstmrNativeRrn01').prop('readonly', 'readonly');
            $('#cstmrNativeRrn02').prop('readonly', 'readonly');

            $('#divBtnReq').show(); //조회요청 버튼 노출

            pageObj.telAdvice = false;
            return null;
        } else { // 본인인증 실패 시 처리
            MCP.alert(strMsg);
            return null;
        }
    }
}

function getDataList(){
    if ($("#isAuth").val() != "1") {
        MCP.alert("본인 인증이 완료되지 않았습니다. ");
        return false;
    }

    var contentHtml= "";
    $("#dataList").html("");

    var varData = ajaxCommon.getSerializedData({
        cstmrName:$.trim($("#cstmrName").val()),
        cstmrNativeRrn01:$.trim($("#cstmrNativeRrn01").val()),
        cstmrNativeRrn02:$.trim($("#cstmrNativeRrn02").val()),
        onlineAuthType:pageObj.onlineAuthType,
        reqSeq: pageObj.niceResLogObj.reqSeq,
        resSeq: pageObj.niceResLogObj.resSeq
    });

    ajaxCommon.getItem({
        id:'getOwnPhoNumListAjax'
        ,cache:false
        ,url:'/cs/getOwnPhoNumListAjax.do'
        ,data: varData
        ,async:false
        ,dataType:"json"
    }, function(jsonObj){

        if(jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
            var alertMsg= (jsonObj.RESULT_DESC == undefined) ? "서비스 처리 중 오류가 발생했습니다." : jsonObj.RESULT_DESC;
            MCP.alert(alertMsg);
            return;
        }

        var list = jsonObj.list;
        if(list == null || list.length== 0){ // 데이터가 없는 경우
            contentHtml = "고객님께서는 KT 엠모바일에 가입되어 사용중인 회선은 없습니다.";
        }else{ // 데이터가 존재하는 경우

            contentHtml+= '<p>가입된 회선정보</p>'
            contentHtml+= '<div class="c-table u-mt--20">'
            contentHtml+=   '<table>';
            contentHtml+=	  '<colgroup>';
            contentHtml+=		'<col style="width: 60%">';
            contentHtml+=		'<col style="width: 40%">';
            contentHtml+=	  '</colgroup>';
            contentHtml+=	  '<thead>';
            contentHtml+=		'<tr>';
            contentHtml+=		  '<th>핸드폰번호</th>';
            contentHtml+=		  '<th>가입일</th>';
            contentHtml+=		'</tr>';
            contentHtml+=	  '</thead>';
            contentHtml+=	  '<tbody id="dataList">';

            for(var i=0;i<list.length;i++){
                var item= list[i];
                contentHtml+=   '<tr>';
                contentHtml+= 	  '<td>'+item.cntrMobileNo+'</td>';
                contentHtml+= 	  '<td>'+item.lstComActvDate+'</td>';
                contentHtml+=   '</tr>';
            }
            contentHtml+=	  '</tbody>';
            contentHtml+=	'</table>';
            contentHtml+= '</div>';
        }
        MCP.alert(contentHtml);
    })
}

var viewErrorMgs = function($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") ) {
        $thatObj.parent().parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};

function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}
