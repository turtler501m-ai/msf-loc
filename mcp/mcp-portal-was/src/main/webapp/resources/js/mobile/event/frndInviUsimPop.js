
VALIDATE_NOT_EMPTY_MSG.name = "이름을 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.phone = "연락처를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.commendId = "추천ID 를 입력해주세요.";
VALIDATE_NOT_EMPTY_MSG.frndName = "피추천인 성명을 입력해주세요.";
VALIDATE_NOT_EMPTY_MSG.frndPhone = "피추천인 연락처를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.frndPost = "피추천인 배송지를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.frndAddr = "피추천인 배송지를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.frndAddrDtl = "피추천인 배송지를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.agree1 = "필수 동의사항에 체크해주세요.";
VALIDATE_NOT_EMPTY_MSG.agree2 = "필수 동의사항에 체크해주세요.";
VALIDATE_COMPARE_MSG.phone = "휴대폰 형식이 일치 하지 않습니다.";
VALIDATE_COMPARE_MSG.frndPhone = "휴대폰 형식이 일치 하지 않습니다.";

var objPopup;

$(document).ready(function(){

    $(".board_detail_table").css("margin-bottom","10px");

	// 숫자만 입력가능1
	$(".numOnly").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});

	// 숫자만 입력가능2
	$(".numOnly").blur(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});

	//주소찾기
    $("._btnAddr").click(function() {
        openPage('pullPopup2nd', '/m/addPopup.do');
    });

	$("#btnApply").click(function(){

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

		var name = $.trim(ajaxCommon.isNullNvl($("#name").val(),""));
        var phone = $.trim(ajaxCommon.isNullNvl($("#phone").val(),""));
        var commendId = $.trim(ajaxCommon.isNullNvl($("#commendId").val(),""));

		validator.config={};
        validator.config['name'] = 'isNonEmpty';
        validator.config['phone'] = 'isNonEmpty';
        validator.config['phone'] = 'isPhone';
        validator.config['commendId'] = 'isNonEmpty';
        validator.config['frndName'] = 'isNonEmpty';
        validator.config['frndPhone'] = 'isNonEmpty';
        validator.config['frndPhone'] = 'isPhone';
        validator.config['frndPost'] = 'isNonEmpty';
        validator.config['frndAddr'] = 'isNonEmpty';
        validator.config['frndAddrDtl'] = 'isNonEmpty';
        validator.config['agree1'] = 'isNonEmpty';
        validator.config['agree2'] = 'isNonEmpty';

        if (validator.validate()) {
	    	var varData = ajaxCommon.getSerializedData({
		   		name : name,
		   		phone : phone,
		   		commendId : commendId
		    });

		    ajaxCommon.getItem({
		        id:'cstmrInfoChkAjax'
		        ,cache:false
		        ,url:'/event/cstmrInfoChkAjax.do'
		        ,data: varData
		        ,dataType:"json"
		        ,async:false
		    }
		    ,function(jsonObj){
	            KTM.LoadingSpinner.hide();
		    	var resultCode = jsonObj.resultCode;
		    	if (resultCode=="S") {
			        frndInviUsimReg();
		    	} else {
		    		MCP.alert("추천인정보가 일치하지 않습니다.");
		    		return false;
		    	}
		    });
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
	})
	
	$("#btnCancel").click(function(){
    	KTM.Dialog.closeAll(); //모달닫기
	});
});

function frndInviUsimReg(){
	var name = $("#name").val();
 	var phone = $("#phone").val();
 	var commendId = $("#commendId").val();
 	var agree = "Y";
 	var frndName = $("#frndName").val();
 	var frndPhone = $("#frndPhone").val();
 	var frndPost = $("#frndPost").val();
 	var frndAddr = $("#frndAddr").val();
 	var frndAddrDtl = $("#frndAddrDtl").val();

   	var varData = ajaxCommon.getSerializedData({
   		name : name
   		, phone : phone
   		, commendId : commendId
   		, agree : agree
   		, frndName : frndName
   		, frndPhone : frndPhone
   		, frndPost : frndPost
   		, frndAddr : frndAddr
   		, frndAddrDtl : frndAddrDtl
    });

    ajaxCommon.getItem({
        id:'frndInviUsimRegAjax'
        ,cache:false
        ,url:'/event/frndInviUsimRegAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    }
    ,function(jsonObj){
        KTM.LoadingSpinner.hide();
    	var resultCode = jsonObj.resultCode;
    	if (resultCode=="S") {
			KTM.Confirm('신청이 완료 되었습니다. 신청화면을 닫으시겠습니까?' ,function () {
    			KTM.Dialog.closeAll(); //모달닫기
			});
    	} else if (resultCode=="D") {
    		MCP.alert("월 1회만 신청 가능 합니다.");
    		return false;
    	} else {
    		MCP.alert("신청이 실패 되었습니다.");
    		return false;
    	}
    });

}

var viewErrorMgs = function($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") ) {
        $thatObj.parent().parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};

/* 주소 setting */
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
    $("#frndPost").val(zipNo);
    $("#frndAddr").val(roadAddrPart1);
    $("#frndAddrDtl").val(roadAddrPart2 + " " + addrDetail);
}