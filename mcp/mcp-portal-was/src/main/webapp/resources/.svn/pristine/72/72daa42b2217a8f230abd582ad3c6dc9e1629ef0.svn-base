var pageObj = {
     niceType : ""
    ,cstmrType : ""
    ,niceResLogObj:{}
    ,authObj:{}
    ,startTime: 0
    ,niceHistRwdProdSeq:0
    ,eventId : ""
}

$(document).ready(function (){

    // 신청상태에 따라 화면 세팅
    getRwdRegStatus($("#contractNum").val());

    // 회선 변경 이벤트
    $("#ctn").change(function(){
        select();
    })

    // 본인인증 전 유효성 검사 (선언은 niceAuth.js에서 진행)
    simpleAuthvalidation= function(){

        validator.config = {};
        validator.config['cstmrName'] = 'isNonEmpty';

        // 고객유형 체크
        var idNum = $.trim($("#cstmrNativeRrn02").val()).substring(0,1);
        if(idNum == ""){
            validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
            pageObj.cstmrType = CSTMR_TYPE.NA;
        }else if(idNum == '5' || idNum == '6' || idNum == '7' || idNum == '8'){ //외국인
            validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isFngno';
            pageObj.cstmrType = CSTMR_TYPE.FN;
        }else{
            var age = fn_getAge($.trim($("#cstmrNativeRrn01").val()),idNum);

            if (age < 19) { // 청소년
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isTeen';
                pageObj.cstmrType = CSTMR_TYPE.NM;
            } else { // 내국인
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
                pageObj.cstmrType = CSTMR_TYPE.NA;
            }
        }

        if (validator.validate()) { // 유효성검사 통과 시 본인인증 진행
            return true;
        }else{ // 유효성 검사 불충족시 팝업 표출 후 포커스
            var errId = validator.getErrorId();
            showErrorPop(errId, validator.getErrorMsg());
            return false;
        }
    }

    // 자급제 핸드폰 구입가 입력 이벤트
    $("input[name=rwdBuyPric]").on("keyup change", function(){

        // 에러 문구 제거
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        // 입력값 확인 (숫자만 허용)
        var inputPrice= $(this).val().replace(/[^0-9]/gi,"");

        if(inputPrice.length > 0){ // 입력값이 존재하는 경우
            var commaPrice= numberWithCommas(inputPrice);
            $(this).val(commaPrice);
        }else{ // 입력값이 존재하지 않은 경우
            $(this).val("");
        }
        isValidateNonEmptyApply(); // 자급제 보상서비스 버튼 활성화 상태 변경
    });

    // 단말기 정보값 (IMEI) 이미지 등록 이벤트
    $("#rwdImeiImage").change(function(){
        var imeiImage = $(this).val();

        if(imeiImage == ""){
            MCP.alert("이미지 파일을 선택해 주세요.", function(){isValidateNonEmptyApply();});
            return false;
        }

        // 확장자 체크
        var ext = imeiImage.split(".").pop().toLowerCase();
        if($.inArray(ext, ["jpg", "jpeg", "png", "tif","tiff", "bmp"]) == -1) {
            $("#rwdImeiImage").val("");
            MCP.alert("첨부파일은 이미지 파일만 등록 가능합니다.", function(){isValidateNonEmptyApply();});
            return false;
        }

        // 이미지 파일명 체크
        var pattern =  /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
        var fileName = imeiImage.split('\\').pop().toLowerCase();
        if(pattern.test(fileName) ){
            $("#rwdImeiImage").val("");
            MCP.alert('파일명에 특수문자가 포함되어 있습니다.', function(){isValidateNonEmptyApply();});
            return false;
        }

        // 이미지 내의 imei1 , imei2 값 추출
        var formData = new FormData();
        formData.append("image",  $("#rwdImeiImage")[0].files[0]);

        KTM.LoadingSpinner.show();
        ajaxCommon.getItemFileUp({
             id : 'getOcrImgReadyAjax'
            ,cache : false
            ,async : false
            ,url : '/getOcrImgReadyAjax.do'
            ,data : formData
            ,dataType : "json"
        }
        ,function(result){
            KTM.LoadingSpinner.hide();
                imeiClear();  // imei관련 내용 초기화

            var retCode= (!result || !result.retCode) ? "99" :result.retCode;
                var retMsg= (!result || !result.retMsg) ? "이미지 파일을 확인해주세요." : result.retMsg;

            var imei1 = "";
            var imei2 = "";

            if(retCode != '0000'){
                if(retCode == "0002"){ // 문구 바꿔치기
                    retMsg= "자급제 보상서비스 가입을 위해서는 IMEI1값 또는 IMEI2값이 필요합니다.<br/>자급제 보상서비스 가입가능한 기기 여부를 확인 바랍니다.";
                }
                $("#imeiErrTxt").html(retMsg).show();
            } else {
                $("#imeiErrTxt").html("").hide();
                imei1 = result.imei1;
                imei2 = result.imei2;

                $("#imei1Txt").val(imei1);
                $("#imei2Txt").val(imei2);
            }

            // imei image register popup open
            var el = document.querySelector('#imei-check-modal');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        });
    });

    // 단말기 정보값 (IMEI) 이미지 다시 등록하기 버튼 클릭 이벤트
    $("#imeiReSetBtn").click(function(){
        imeiClear();
        $("#rwdImeiImage").trigger("click");
    });

    // 단말기 정보값 (IMEI) 이미지 확인 버튼 클릭 이벤트
    $("#imeiSetBtn").click(function() {

        var imei1 = ajaxCommon.isNullNvl($.trim($("#imei1Txt").val()), "");
        var imei2 = ajaxCommon.isNullNvl($.trim($("#imei2Txt").val()), "");

        // IMEI 자릿수 확인_IMEI 한개는 필수
        if(imei1 == "" && imei2 == ""){ // imei를 한개도 입력하지 않은 경우
            MCP.alert("IMEI 정보값을 입력해 주세요.", function () {
                $("#imei1Txt").val("");
                $("#imei2Txt").val("");
                $("#imei1Txt").focus();
            });
            return false;
        }

        if (imei1 != "" && imei1.length != 15) { // imei1을 입력한 경우 자릿수 체크
            MCP.alert("imei1 자릿수가 일치하는지 확인해 주세요.", function () {
                $("#imei1Txt").focus();
            });
            return false;
        }

        if (imei2 != "" && imei2.length != 15) { // imei2를 입력한 경우 자릿수 체크
            MCP.alert("imei2 자릿수가 일치하는지 확인해 주세요.", function () {
                $("#imei2Txt").focus();
            });
            return false;
        }

        // imei 사용가능 여부 체크
        var varData = ajaxCommon.getSerializedData({
            imei: imei1, imeiTwo: imei2
        });

        ajaxCommon.getItem({
            id:'checkImeiForRwd'
            ,cache:false
            ,url:'/mypage/checkImeiForRwd.do'
            ,data:varData
            ,dataType:"json"
        }
        ,function(jsonObj){

            var resultCd= (!jsonObj || !jsonObj.RESULT_CODE) ? "FAIL" : jsonObj.RESULT_CODE;
                var resultMsg= (!jsonObj || !jsonObj.RESULT_MSG) ? "사용 불가능한 단말기 정보값입니다." : jsonObj.RESULT_MSG;

            if(resultCd == "FAIL"){
                MCP.alert(resultMsg);
                return false;
            }

            var possibleYn= (!jsonObj || !jsonObj.possibleYn) ? "N" : jsonObj.possibleYn;
            if(possibleYn == "Y"){

                    // 에러 문구 제거
                    $(".c-form__text").remove();
                    $(".has-error").removeClass("has-error");

                // imei image register popup close
                var el = document.querySelector('#imei-check-modal');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.close();

                // 입력한 IMEI값 화면에 뿌리기
                $("#imei1Txt, #imei2Txt").attr("readonly", true);

                    if(imei1 != "") $("#rwdImei1").val(imei1).parent().addClass("has-value");
                    if(imei2 != "") $("#rwdImei2").val(imei2).parent().addClass("has-value");

                // 자급제 보상서비스 신청 버튼 활성화/비활성화 처리
                isValidateNonEmptyApply();
            }else{
                MCP.alert("사용불가한 단말기 정보값입니다.<br/>다른 단말기 정보값을 입력 바랍니다.");
                return false;
            }
        });
    });

    // 구매영수증 등록 클릭 이벤트
    $(".file-label").click(function(){

        // 파일등록 창 open
        if($(".staged_img-list .staged-image").length < 1){
            $("#rwdFile1").trigger("click");
            return true;
        }

        // 증빙서류가 등록되어 있는 경우 alert
        MCP.alert("등록된 첨부파일이 존재합니다.<br>등록된 항목 삭제 후 재 등록 바랍니다.");
    });

    // 자급제 보상서비스 증빙서류 등록 클릭 이벤트_사용자가 강제로 파일 등록 호출하는 경우
    $("#rwdFile1").click(function(){
        // 증빙서류가 이미 등록되어 있는 경우
        if($(".staged_img-list .staged-image").length >= 1){
            MCP.alert("등록된 첨부파일이 존재합니다.<br>등록된 항목 삭제 후 재 등록 바랍니다.");
            return false;
        }
        return true;
    });

    // 약관 동의 > 전체 약관 동의 버튼 클릭 이벤트
    $("#clauseRwdFlag").click(function(){
        if ($(this).is(':checked')) {
            KTM.Confirm('모든 약관/동의에<br/>동의 하시겠습니까?'
                ,function () {
                    // 전체약관 동의
                    $("._rwdAgree").prop("checked", "checked");
                    this.close();
                    isValidateNonEmptyApply(); // 자급제 보상서비스 버튼 활성화 상태 변경
                }
                ,function(){
                    // 선택 약관은 없으므로 취소 클릭 시 개별 약관 기존 체크 상태 유지
                    $("#clauseRwdFlag").prop("checked", false);
                    isValidateNonEmptyApply(); // 자급제 보상서비스 버튼 활성화 상태 변경
                });
        } else {
            // 전체 약관 동의 체크 해제
            $("._rwdAgree").prop("checked", false);
            isValidateNonEmptyApply(); // 자급제 보상서비스 버튼 활성화 상태 변경
        }
    });

    // 약관 동의 > 개별 약관 동의 버튼 클릭 이벤트
    $("._rwdAgree").click(function(){
        rwdTermsCheckCbFn();
    });

    // 휴대폰 인증 버튼 클릭 이벤트
    $("#btnSmsAuthRwd").click(function(){
        if ($("#isAuthRwd").val() == "1") {
            MCP.alert("휴대폰 인증을 완료 하였습니다.");
            return false;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        // 휴대폰 인증 전 유효성 검사 (본인인증)
        validator.config['isAuth'] = 'isNonEmpty';
        // 휴대폰 인증 전 유효성 검사 (파일등록)
        validator.config['rwdFile1']='isImg';
        // 휴대폰 인증 전 유효성 검사 (필수동의)
        validator.config['clauseRwdRegFlag'] = 'isNonEmpty';            // 서비스 가입 동의
        validator.config['clauseRwdPaymentFlag'] = 'isNonEmpty';        // 지급기준 이행 동의
        validator.config['clauseRwdRatingFlag'] = 'isNonEmpty';         // 평가 기준 동의
        validator.config['clauseRwdPrivacyInfoFlag'] = 'isNonEmpty';    // 개인정보 수집ㆍ이용에 대한 동의
        validator.config['clauseRwdTrustFlag'] = 'isNonEmpty';          // 개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서

        if (validator.validate()) {
            // 휴대폰 인증요청 시간 세팅
            ajaxCommon.getItem({
                id:'getTimeInMillisAjax'
                ,cache:false
                ,url:'/nice/getTimeInMillisAjax.do'
                ,data: ""
                ,dataType:"json"
            }
            ,function(startTime){
                pageObj.startTime = startTime ;
            });

            // 휴대폰 인증 창 open
            var data = {width:'500', height:'700'};
            openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data);
            pageObj.niceType = NICE_TYPE.RWD_PROD;
            return;
        } else {
            var errId = validator.getErrorId();
            showErrorPop(errId, validator.getErrorMsg());  // 에러 alert
        }
    });

}); // end of ready-----------------------------

/**
 * 회선 조회 이벤트
 */
var select= function(){
    document.frm.ncn.value = $("#ctn").val();
    document.frm.submit();
}

/**
 * 자급제 보상서비스 신청 상태 조회 및 화면 세팅
 */
var getRwdRegStatus= function(contractNum){

    var varData = ajaxCommon.getSerializedData({contractNum: contractNum});

    ajaxCommon.getItem({
            id:'selectRwdRegStatus'
            ,cache:false
            ,url:'/mypage/selectRwdRegStatus.do'
            ,data:varData
            ,dataType:"json"
        }
        ,function(jsonObj){

            var viewCd= (!jsonObj || !jsonObj.rwdRegStatusCd) ? "CALL" : jsonObj.rwdRegStatusCd;

            // 가입 가능
            if(viewCd == "POSSIBLE"){
                $("#registerForm").show();
                $("#registerFormAfter").hide();
                $("#notRegisterForm").hide();
                getRwdProdList(); // 자급제 보상서비스 리스트 세팅
                return;
            }

            // 가입 불가능
            var notAcceptReason= "고객님께서는 고객센터(114/무료)를 통해 자급제 보상 서비스 가입 가능 여부를 확인 부탁드립니다.";
            switch (viewCd){
                case "IMPOSSIBLE":
                    notAcceptReason= "고객님께서는 자급제 보상 서비스 가입 대상이 아닙니다.";
                    break;
                case "ING":
                    notAcceptReason= "고객님께서는 이미 자급제 보상 서비스 가입 신청중입니다.";
                    break;
                case "EXIST":
                    notAcceptReason= "고객님께서는 자급제 보상 서비스에 가입 중이므로 가입 대상이 아닙니다.";
                    break;
                case "CALL" :
                    notAcceptReason= "고객님께서는 고객센터(114/무료)를 통해 자급제 보상 서비스 가입 가능 여부를 확인 부탁드립니다.";
                    break;
            }

            $("#notRegisterForm span").html(notAcceptReason);
            $("#notRegisterForm").show();
            $("#registerForm").hide();
            $("#registerFormAfter").hide();
        });
}

/**
 * 자급제 보상서비스 리스트 가져오기
 */
var getRwdProdList = function(){

    // 이미 가져온 리스트가 있으면 즉시리턴
    if($("#rwdProdList input[name=rwdProdCD]").length > 0) return false;

    ajaxCommon.getItem({
            id:'selectRwdProdList'
            ,cache:false
            ,url:'/mypage/selectRwdProdListAjax.do'
            ,dataType:"json"
    }
    ,function(jsonObj){

        $("#rwdProdList").empty();

        if(jsonObj== null || jsonObj.length <= 0){ // 데이터가 존재하지 않는 경우

            var nodataHtml = '<i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
            nodataHtml+= '<br>';
            nodataHtml+= '<span>신청 가능한 자급제 보상 서비스가 없습니다.</span>';
            $("#notRegisterForm span").html(nodataHtml);
            $("#notRegisterForm").show();
            $("#registerForm").hide();
            $("#registerFormAfter").hide();

        }else{  // 데이터가 존재하는 경우
            for(var i = 0 ; i < jsonObj.length ; i++) {
                $("#rwdProdList").append(getRowTemplateRwdProd(jsonObj[i]));
            }

                // 자급제 보상서비스 선택 이벤트
            $("input[name=rwdProdCD]").change(function(){
                isValidateNonEmptyApply(); // 자급제 보상서비스 신청 버튼 활성화/비활성화 처리
            });
        }
    });
}

/**
 * 자급제 보상서비스 리스트 그리기 (one row)
 */
var getRowTemplateRwdProd = function(rwdObj){
    var arr =[];

    arr.push("<div class='c-card c-card--type2'>\n");

    arr.push("  <input class='c-radio c-radio--type4' id='rwdProdCD_"+rwdObj.rwdProdCd+"' value='"+rwdObj.rwdProdCd+"' type='radio' name='rwdProdCD'>\n");
    arr.push("  <label class='c-card__label' for='rwdProdCD_"+rwdObj.rwdProdCd+"'>\n");
    arr.push("      <span class='c-hidden'>선택</span>\n");
    arr.push("  </label>\n");

    var rwdContentHtml= RWD_PROD_CONTENT[rwdObj.rwdProdCd];
    if (!!rwdContentHtml) {
        arr.push(rwdContentHtml);
    } else if(rwdObj.rwdProdNm != null && rwdObj.rwdProdNm != ""){
        arr.push("<div class='c-card__title'>\n");
        arr.push("  <strong>"+rwdObj.rwdProdNm+"</strong>\n");
        arr.push("</div>\n");
    }else{
        // 내용물 또는 서비스명이 존재하지 않는 경우 화면에 표출하지 않음
        return '';
    }

    arr.push("</div>\n");

    return arr.join('');
}

/**
 * 자급제 보상서비스 신청 버튼 활성화/비활성화 처리
 */
var isValidateNonEmptyApply= function(){

    var activeFlag= true;
    var disabledTarget= "";

    validator.config={};

    // 3. 구입가 체크
    validator.config['rwdBuyPric']='isNonEmpty';

    // 4. 단말기 정보값 체크
    var imei1Len= $("#rwdImei1").val().length;
    var imei2Len= $("#rwdImei2").val().length;
    if(imei1Len > 0 && imei2Len > 0){ // imei1과 imei2 모두 입력한 경우_두개 다 유효성검사 대상
        validator.config['rwdImei1']='isNumFix15';
        validator.config['rwdImei2']='isNumFix15';
    }
    else if(imei1Len > 0) validator.config['rwdImei1']='isNumFix15'; // imei1만 입력한 경우_imei1만 유효성검사 대상
    else if(imei2Len > 0) validator.config['rwdImei2']='isNumFix15'; // imei2만 입력한 경우_imei2만 유효성검사 대상
    else validator.config['rwdImei1']='isNumFix15'; // 아무것도 입력하지 않은 경우_입력 필요

    // 5. 구매영수증 첨부 여부
    validator.config['rwdFile1']='isImg';

    // 6. 약관동의 체크
    validator.config['clauseRwdRegFlag'] = 'isNonEmpty';            // 서비스 가입 동의
    validator.config['clauseRwdPaymentFlag'] = 'isNonEmpty';        // 지급기준 이행 동의
    validator.config['clauseRwdRatingFlag'] = 'isNonEmpty';         // 평가 기준 동의
    validator.config['clauseRwdPrivacyInfoFlag'] = 'isNonEmpty';    // 개인정보 수집ㆍ이용에 대한 동의
    validator.config['clauseRwdTrustFlag'] = 'isNonEmpty';          // 개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서

    // 7. 휴대폰 인증 여부
    validator.config['isAuthRwd'] = 'isNonEmpty';

    if (!validator.validate(true)){
        disabledTarget= validator.getErrorId();
        activeFlag= false;
    }

    // 2. 자급제 보상서비스 선택 체크
    if($("input[name=rwdProdCD]:checked").length <= 0){
        disabledTarget= "rwdProdCD";
        activeFlag= false;
    }

    // 1. 본인인증 체크
    validator.config={};
    validator.config['cstmrName']='isNonEmpty';
    validator.config['cstmrNativeRrn01']='isNonEmpty';
    validator.config['cstmrNativeRrn02']='isNonEmpty';
    validator.config['isAuth'] = 'isNonEmpty';

    if (!validator.validate(true)){
        disabledTarget= validator.getErrorId();
        activeFlag= false;
    }

    // 활성화/비활성화 처리
    if(activeFlag) $("#btnApplyRwd").removeClass('is-disabled');
    else $("#btnApplyRwd").addClass('is-disabled');

    return disabledTarget;
}


/**
 * 포커스 이동 처리
 */
var nextFocus= function(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

/**
 * 유효성 검사 실패 시 에러 alert표출과 에러 문구 표출
 */
var showErrorPop= function(errorId, errorMsg){
    MCP.alert(errorMsg,function (){
        viewAuthErrorMgs($("#"+errorId), errorMsg);
    });
}

/**
 * 본인인증 / 핸드폰인증 완료 콜백
 */
var fnNiceCert= function(prarObj){

    var cstmrNativeRrn;
    var cstmrName;
    var contractNum;
    var strMsg= "고객 정보와 본인인증한 정보가 틀립니다.";

    // 인증 response
    pageObj.niceResLogObj = prarObj;

    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) { // 본인인증

        cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val());
        cstmrName = $.trim($("#cstmrName").val());
        contractNum = $("#contractNum").val();

        cstmrName= cstmrName.toUpperCase();

        // 본인인증 결과 확인
        var authInfoJson= {cstmrName: cstmrName, cstmrNativeRrn: cstmrNativeRrn, contractNum: contractNum, authType: prarObj.authType};
        var isAuthDone = mypageAuthCallback(authInfoJson);

        if(isAuthDone){ // 본인인증 최종 성공
            pageObj.authObj= prarObj;
            $('#cstmrName').prop('readonly', 'readonly');
            $('#cstmrNativeRrn01').prop('readonly', 'readonly');
            $('#cstmrNativeRrn02').prop('readonly', 'readonly');

            // 자급제 보상서비스 신청 폼 표출
            MCP.alert("본인인증에 성공 하였습니다.", function(){
                $("#registerFormAfter").show();
            });
        }else{ // 본인인증 최종 실패

            var resultCd= niceAuthObj.resAuthOjb.RESULT_CODE;
            var errorMsg= niceAuthObj.resAuthOjb.RESULT_MSG;

            if (resultCd == "LOGIN") {
                MCP.alert(errorMsg, function () {
                    location.href = '/loginForm.do';
                });
                return null;
            }

            strMsg= (errorMsg == undefined) ? strMsg : errorMsg;
            MCP.alert(strMsg);
        }
    }else if(pageObj.niceType == NICE_TYPE.RWD_PROD){ // 핸드폰인증

        // 본인인증에 사용한 정보와 고객정보 비교
        var authInfoJson= {cstmrName: "rwdProdNm", cstmrNativeRrn: "rwdProdRrn", contractNum: "", authType: prarObj.authType, isCommonPrd:false};
        var isAuthDone = authCallback(authInfoJson);

        if(isAuthDone){ // 본인인증 최종 성공
            $("#isAuthRwd").val("1");
            $('#btnSmsAuthRwd').addClass('is-complete').html("휴대폰 인증 완료");
        }else{ // 본인인증 최종 실패

            var rwdErrMsg= "본인인증 시 고객정보와 휴대폰 인증 고객정보가 일치하지 않습니다.";
            if(niceAuthObj.resAuthOjb.RESULT_CODE == "STEP01"){ // STEP 오류
                rwdErrMsg= niceAuthObj.resAuthOjb.RESULT_MSG;
            }

            MCP.alert(rwdErrMsg);
        }
    }

    isValidateNonEmptyApply(); // 자급제 보상서비스 버튼 활성화 상태 변경
    return null;
}


/**
 * imei 관련 내용 초기화
 */
var imeiClear= function(){
    $("#rwdImeiImage").val("");
    $("#imeiErrTxt").html("").hide();
    $("#imei1Txt, #imei2Txt").val("").removeAttr("readonly");
    $("#rwdImei1, #rwdImei2").val("").parent().removeClass("has-value");

    isValidateNonEmptyApply(); // 자급제 보상서비스 버튼 활성화 상태 변경
}

/**
 * 자급제 보상서비스 증빙서류 변경 이벤트
 */
var setThumb= function(){

    var target= $("#rwdFile1")[0];
    var fileImage= $("#rwdFile1").val();
    var fileBox = '';  // 등록 파일 html

    if(fileImage == ""){
        MCP.alert("첨부파일을 선택해 주세요.", function(){isValidateNonEmptyApply();});
        return false;
    }

    // 파일 확장자 체크
    var ext = fileImage.split(".").pop().toLowerCase();
    if($.inArray(ext, ["jpg", "jpeg", "png", ,"gif"]) == -1) {
        $("#rwdFile1").val("");
        MCP.alert("첨부파일 확장자를 확인해주세요.<br/>가능한 파일 확장자는 jpg, gif, png입니다.",function(){
            isValidateNonEmptyApply();
        });
        return false;
    }

    // 파일 크기 체크
    if(target.files[0].size/1024/1024 > 2){
        $("#rwdFile1").val("");
        MCP.alert("업로드 가능한 파일용량은 2MB 이내입니다.", function(){isValidateNonEmptyApply();});
        return false;
    }

    // 이미지 파일명 체크
    var pattern =  /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
    var fileName = fileImage.split('\\').pop().toLowerCase();
    if(pattern.test(fileName) ){
        $("#rwdFile1").val("");
        MCP.alert('파일명에 특수문자가 포함되어 있습니다.', function(){isValidateNonEmptyApply();});
        return false;
    }

    // 신규 등록 파일 html 그리기
    fileBox += '<div class="c-form staged-image">';
    fileBox += '    <div class="c-file-image__item" style="width:220px;height:167px;border:1px solid #ddd;border-radius:0.5rem;background:rgba(0,0,0,0);left:16rem;">';
    fileBox += '        <div>등록완료</div>';
    fileBox += '        <button class="c-button">';
    fileBox += '            <span class="sr-only">삭제</span>';
    fileBox += '            <i class="c-icon c-icon--delete" aria-hidden="true"></i>';
    fileBox += '        </button>';
    fileBox += '    </div>';
    fileBox += '</div>';

    $(".staged_img-list").html(fileBox);

    // 자급제 보상서비스 증빙서류 삭제 클릭 이벤트
    $(".c-icon--delete").click(function(){
        $(this).parent().parent().parent().remove();
        $(".c-label__sub").text("첨부파일 0/1");
        $("#rwdFile1").val("");
        isValidateNonEmptyApply();
    });

    if($(".staged_img-list .staged-image").length == 1){
       $(".c-label__sub").text("첨부파일 1/1");
    }

    isValidateNonEmptyApply();
}

/**
 * 약관 팝업 클릭 시 eventId 세팅
 */
var fnSetEventId = function(eventId){
    pageObj.eventId = eventId;
};

/**
 * 약관 동의 후 닫기
 */
var targetTermsAgree= function() {
    $('#' + pageObj.eventId).prop('checked', 'checked');
    rwdTermsCheckCbFn();
};

/**
 * 개별 약관 클릭 이벤트
 */
var rwdTermsCheckCbFn= function(){
    var isAllCheck= true;
    $("._rwdAgree").each(function(){
        if (!$(this).is(':checked')) isAllCheck= false;
    });

    if (isAllCheck) $("#clauseRwdFlag").prop("checked", "checked");
    else $("#clauseRwdFlag").prop("checked", false);

    isValidateNonEmptyApply(); // 자급제 보상서비스 버튼 활성화 상태 변경
}

/**
 *  자급제 보상서비스 신청버튼 상태변경
 *  신청버튼 클릭 시 클릭이벤트 제거, 신청이 끝난 후 클릭이벤트 재설정
 */
var applyRwdStatusChg= function(isActive){
    if(isActive) $("#btnApplyRwd").attr("disabled", false);
    else $("#btnApplyRwd").attr("disabled", true);
}

/**
 *  자급제 보상서비스 신청 (최종 신청)
*/
var applyRwd= function(){

    // 에러 문구 제거
    $(".c-form__text").remove();
    $(".has-error").removeClass("has-error");

    // 신청버튼 중복클릭 방지
    applyRwdStatusChg(false);

    // 필수값 유효성 검사
    var disabledTarget= isValidateNonEmptyApply();
    var alertMsg= "";

    switch(disabledTarget){
        case "cstmrName":
            alertMsg= "성명 입력해 주세요.";
            break;
        case "cstmrNativeRrn01":
            alertMsg= "주민등록번호 앞자리를 입력해 주세요.";
            break;
        case "cstmrNativeRrn02":
            alertMsg= "주민등록번호 뒷자리를 입력해 주세요.";
            break;
        case "isAuth":
            alertMsg= "본인인증 하여 주시기 바랍니다.";
            break;
        case "rwdProdCD":
            alertMsg= "자급제 보상 서비스를 선택하여 주시기 바랍니다.";
            break;
        case "rwdBuyPric":
            alertMsg= "자급제 핸드폰 구입가를 입력하여 주시기 바랍니다.";
            break;
        case "rwdImei1": case "rwdImei2":
            alertMsg= "단말기 정보값을 입력하여 주시기 바랍니다.";
            break;
        case "rwdFile1":
            alertMsg= "구매영수증을 첨부하여 주시기 바랍니다.";
            break;
        case "clauseRwdRegFlag": case "clauseRwdPaymentFlag": case "clauseRwdRatingFlag": case "clauseRwdPrivacyInfoFlag": case "clauseRwdTrustFlag":
            alertMsg= "약관동의 하여 주시기 바랍니다.";
            break;
        case "isAuthRwd":
            alertMsg= "휴대폰 인증을 진행하여 주시기 바랍니다.";
            break;
    }

    if(alertMsg != ""){
        showErrorPop(disabledTarget, alertMsg);
        applyRwdStatusChg(true);
        return false;
    }

    // 필수값 유효성 추가 검사(본인인증)
    if ($("#isAuth").val() != "1" || Object.keys(pageObj.authObj).length == 0){
        MCP.alert("본인인증 하여 주시기 바랍니다.");
        applyRwdStatusChg(true);
        return false;
    }

    // 필수값 유효성 추가 검사(구입가)
    var priceVal= $("input[name=rwdBuyPric]").val();
    if(priceVal.match(/[^0-9\,]/gi)){ // 숫자와 콤마 이외의 값을 포함하는 경우
        MCP.alert("올바른 구입가를 입력하여 주시기 바랍니다.", function(){
            $("#rwdBuyPric").focus();
        });
        applyRwdStatusChg(true);
        return false;
    }

    /// 필수값 유효성 추가 검사(휴대폰 인증)
    if ($("#isAuthRwd").val() != "1"){
        MCP.alert("휴대폰 인증 하여 주시기 바랍니다.", function(){
            $("#btnSmsAuthRwd").focus();
        });
        applyRwdStatusChg(true);
        return false;
    }

    // 자급제 보상서비스 신청
    KTM.Confirm("자급제 보상 서비스를 신청하시겠습니까?", function (){
        var formData = new FormData();
        formData.append("rwdProdCd", $("input[name=rwdProdCD]:checked").val());
        formData.append("imei",$("#rwdImei1").val());
        formData.append("imeiTwo", $("#rwdImei2").val());
        formData.append("buyPric", $("#rwdBuyPric").val().replace(/[^0-9]/gi,""));
        formData.append("contractNum", $("#contractNum").val());
        formData.append("file", $("input[name=rwdFile1]")[0].files[0]);
        formData.append("onlineAuthType", $('input:radio[name=onlineAuthType]:checked').val());
        formData.append("authCstmrName", $.trim($("#cstmrName").val()));
        formData.append("authBirthDate", $.trim($("#cstmrNativeRrn01").val()));
        formData.append("clauseRwdRegFlag",$("#clauseRwdRegFlag").is(":checked") ? "Y":"N");
        formData.append("clauseRwdPaymentFlag", $("#clauseRwdPaymentFlag").is(":checked") ? "Y":"N");
        formData.append("clauseRwdRatingFlag",  $("#clauseRwdRatingFlag").is(":checked") ? "Y":"N");
        formData.append("clauseRwdPrivacyInfoFlag",  $("#clauseRwdPrivacyInfoFlag").is(":checked") ? "Y":"N");
        formData.append("clauseRwdTrustFlag",  $("#clauseRwdTrustFlag").is(":checked") ? "Y":"N");

        this.close(); // confirm 확인 창 닫은 후 로딩바 표출
        KTM.LoadingSpinner.show();

        ajaxCommon.getItemFileWithAsync({
              id : 'rwdRegAjax'
            , cache : false
            , async : true
            , url : '/mypage/rwdRegAjax.do'
            , data : formData
            , dataType : "json"
            ,errorCall : function (e) {
                KTM.LoadingSpinner.hide();
                if(e.status != '0') alert('처리 중 오류가 발생하였습니다.');
                applyRwdStatusChg(true);
                }
            },function(jsonObj){

                    KTM.LoadingSpinner.hide();
                    applyRwdStatusChg(true);

                    var resultCd= (!jsonObj || !jsonObj.RESULT_CODE) ? "FAIL" :jsonObj.RESULT_CODE;
                    var resultMsg= (!jsonObj || !jsonObj.RESULT_MSG) ? "자급제 보상서비스 신청에 실패했습니다." : jsonObj.RESULT_MSG;

                    // 로그인 페이지로 이동
                    if(resultCd == "0001"){
                        MCP.alert(resultMsg, function (){location.href = '/loginForm.do';});
                        return false;
                    }

                    // 신청에 실패한 경우, 각 경우에 맞는 resultMsg 표출
                    if(resultCd != AJAX_RESULT_CODE.SUCCESS){
                        MCP.alert(resultMsg);
                        return false;
                    }

                    // 신청 최종 성공 (메인 화면으로 이동)
                    MCP.alert(resultMsg, function(){location.href="/main.do";});
                });
        },function() { // 신청 취소버튼 클릭
                applyRwdStatusChg(true);
                return;
            });
        }
