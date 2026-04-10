VALIDATE_NOT_EMPTY_MSG.txtPhoneF = "휴대폰번호 앞자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.txtPhoneM = "휴대폰번호 중간자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.txtPhoneL = "휴대폰번호 뒷자리를 입력해 주세요.";

$(document).ready(function (){

    //약관동의
    $("#chkAgreeAll").click(function(){
        if($(this).is(':checked')){
            $("._grAgree").prop("checked", true);
        }else {
            $("._grAgree").prop("checked", false);
        }
    });

    //my쿠폰 가기
    $("#btnMyCoupon").click(function(){
        location.href = "/mypage/couponList.do";
        //this.close();
    });

    //쿠폰받기 클릭
    $('#btnDown').click(function(){

        // 에러 문구 제거
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};

        validator.config['txtPhoneF'] = 'isNonEmpty';
        validator.config['txtPhoneM'] = 'isNonEmpty';
        validator.config['txtPhoneL'] = 'isNonEmpty';

        if(validator.validate()){

            //휴대폰번호 체크
            var strPhoneChk = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;

            //입력한 휴대폰번호
            var phoneNum = $.trim($("#txtPhoneF").val() + "" + $("#txtPhoneM").val() + "" + $("#txtPhoneL").val());
            if(!strPhoneChk.test(phoneNum)){
                MCP.alert("휴대폰 번호가 형식에 맞지 않습니다.",function(){
                    viewErrorMgs($("#txtPhoneF"), "휴대폰 번호가 형식에 맞지 않습니다.");
                    $("#txtPhoneF").focus(); // 포커스
                });
                return false;
            }

            //약관동의 체크
            /*if(!$("#notice1").is(":checked")){
                MCP.alert("약관에 동의해 주세요.",function(){
                    viewErrorMgs($("#notice1"), "약관에 동의해 주세요.");
                    $("#notice1").focus(); // 포커스
                });
                return false;
            }*/
            var coupnCtgCdList = [];
            var smsSndPosblYnList = [];
            var coupnCtgNmList = [];
            var counponInfos = document.querySelectorAll('input[name=counponInfo]');
            counponInfos.forEach((param) => {
                coupnCtgCdList.push(param.dataset.couponCtgCd);
                coupnCtgNmList.push(param.dataset.couponCtgNm);
                smsSndPosblYnList.push(param.dataset.smsSndPosblYn);
            });

            var parameterData ={
                smsRcvNo : phoneNum,
                coupnCtgCdList : coupnCtgCdList,
                coupnCtgNmList :  coupnCtgNmList,
                smsSndPosblYnList : smsSndPosblYnList
            };

            ajaxCommon.getItem({
                id:'downCoupon'
                ,cache:false
                ,url:'/coupon/downCoupon.do'
                ,data: JSON.stringify(parameterData)
                ,dataType:"json"
                ,async:false
                ,contentType:"application/json"
            }
            ,function(data){
                if(data.RESULT_CODE == "SUCCESS"){
                    var endMsg = "일부 쿠폰 발행에 실패하였습니다. <br/> 발행된 쿠폰은 마이페이지에서 확인이 가능합니다. <br/>";
                    var result = "Y";
                    data.COUPON_LIST.forEach((param) => {
                        if(param.RESULT_CODE != 'SUCCESS'){
                            result = "N";
                            endMsg += "<br/>" + param.coupnCtgNm + " <br/> (사유 : " + param.RESULT_MSG + ")";
                        }
                    });
                    if(result === "Y") {
                        endMsg = "쿠폰 발행이 완료되었습니다. <br/> 발행된 쿠폰은 마이페이지에서 확인이 가능합니다.";
                    } else {
                        $("#iconBox").addClass("c-icon--notice2");
                    }
                    $("#endMsg").html(endMsg);
                    $("#divFooterOne").hide();
                    $("#divFooterTwo").show();
                    $("#divMainOne").hide();
                    $("#divMainTwo").show();
                } else {
                    MCP.alert(data.RESULT_MSG);
                }
            });
        }else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(),function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
                $('#' + validator.id).focus(); // 포커스
            });
            $(this).prop("checked", "");
        }

    });

});

//에러메시지 보여주기
var viewErrorMgs = function($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};

//포커스이동
function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

//모달닫기
function popClose(close){
    $('.c-icon--close').trigger('click');
    if(close !== undefined) {
        location.reload();
    }
}
