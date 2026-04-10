<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : pass_reset_popup.jsp
     * @Description : 패스워드 초기화
     * @
     * @ 수정일        수정자 수정내용
     * @ ---------- ------ -----------------------------
     * @ 2024.08.12 이승국 최초생성
     * @ 
     * @author : 이승국
     * @Create Date : 2024.08.12.
     */
%>
<!-- header -->
<div class="page-header">
    <h1>패스워드 초기화</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
var oTimer;
var DHX = {
		//사용자수정화면
        FORM1 : {
            title : "",
            items : [
                 {type: 'settings', position: 'label-left', labelWidth: '100', inputWidth: 'auto'},
                 {type: "block", blockOffset: 0, offsetLeft: 10,list: [
                   	{type: 'input', name: 'usrId', label: '사용자ID',  width: 150, value: '' , maxLength:20, required: true}
                 ]},
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                 	{type: 'input', name: 'ctn', label: '전화번호', width: 150, value: '' , maxLength:20, required: true}, 
                 ]},
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                  	{type: 'input', name: 'otp', label: 'OTP <span id="wTimer" style="display:none; color: #000000; font-family: 돋움; font-weight:bold;">(180초)</span>', width: 100, value: '' , maxLength:20, required: true, disabled:true},
                  	{type:"newcolumn"},
                  	{type:"button", value:"인증", name: "btnOtp1", offsetLeft:0},
                  	{type:"button", value:"확인", name: "btnOtp2", offsetLeft:0}
                  ]},
                 , {type : 'hidden', name: "otpYn", value:"${otpYn}"}
                 , {type : 'hidden', name: "otpUse", value:"${otpUse}"}
                 , {type : 'hidden', name: "otpChkYn", value:"N"} //OTP 시작시 Y변경
                 , {type : 'hidden', name: "otpComp", value:"N"} //OTP 완료시 Y변경
            ],
            buttons : {
                center : {
                    btnSave : {label : "신청" } //otp 인증 완료되기전까지 숨기기
                    ,btnClose : {label : "닫기" }
                }
            },

            onButtonClick : function(btnId) {

                var form = this; // 혼란방지용으로 미리 설정 (권고)
                
                switch (btnId) {
	                case "btnOtp1":
						var formData =  PAGE.FORM1.getFormData(true);
						if (formData.otpYn == 'Y' && formData.otpUse == 'Y') {
	    					fnOtp();
	                	}else{
	                		mvno.ui.alert("패스워드 초기화 기능을 사용할 수 없습니다.");
						}
    					break;
	                case "btnOtp2":
	                	var formData =  PAGE.FORM1.getFormData(true);
	                	fnOtpChk();
    					break;
                    case "btnSave":
                    	var formData =  PAGE.FORM1.getFormData(true);
                    	if(formData.otpComp =='Y'){
                        	fnSave();
                        }else{
                        	mvno.ui.alert("OTP인증을 완료하셔야 합니다.");
                        }
                       break;
					case "btnClose" :
                    	mvno.ui.closeWindow();
        				break;
                }
            },
            onValidateMore : function (data){
            	if( data.usrId != "" && data.usrId.trim() == ""){
                    this.pushError("userId","사용자ID","사용자ID를 입력하세요");
                }
            	if( data.ctn != "" && data.ctn.trim() == ""){
                    this.pushError("ctn","전화번호","전화번호를 입력하세요");
                }
            }
        }
 };

function fnOtp() {
    var f = PAGE.FORM1.getFormData(true);
    if( f.usrId == "" ) {
        mvno.ui.alert("아이디를 입력해주세요.");
        return;
    }

    if( f.ctn == "" ) {
        mvno.ui.alert("전화번호를 입력해주세요.");
        return;
    }
    var usrId = f.usrId.toUpperCase();    // 실제 데이터는 대문자화해서 보내야!!
    var ctn = f.ctn;
	mvno.cmn.ajax(ROOT + "/cmn/login/usrOtpProc.json", {usrId:usrId, ctn:ctn}, function(resultData) {
        if(mvno.cmn.isEmpty(resultData) || ! resultData.result || resultData.result.code == 'NOK') {
        	mvno.ui.alert(resultData.result.msg);
            return;
        } else {
			PAGE.FORM1.enableItem("otp");
			PAGE.FORM1.setItemValue("otpChkYn","Y");
			PAGE.FORM1.setItemValue("btnOtp","확인");
			startSmsTimer();
		}
	}, { resultTypeCheck: false });
}

function startSmsTimer() {
    mvno.ui.alert("OTP 인증 번호가 발송 되었습니다. 3분 내로 인증번호를 입력해 주세요.");
    
    otpEventOn(true);

    clearInterval(oTimer);

    var waitMax = 180;

    oTimer = setInterval(function() {
        if(waitMax < 0) {
            clearInterval(oTimer);
            otpEventOn(false);
            mvno.ui.alert("인증번호 입력 시간이 초과하였습니다.<br>처음부터 다시 진행해 주시기 바랍니다.");
        }

        $('#wTimer').html('('+waitMax+'초)');
        waitMax --;
    }, 1000);
}

function otpEventOn(type) {
    if (type) {
    	$('#wTimer').css('display','');
    	PAGE.FORM1.disableItem("usrId");
    	PAGE.FORM1.disableItem("ctn");
    	PAGE.FORM1.enableItem("otp");
    	PAGE.FORM1.hideItem("btnOtp1");
	    PAGE.FORM1.showItem("btnOtp2");
    } else {
    	PAGE.FORM1.setItemValue("usrId","");
        PAGE.FORM1.setItemValue("ctn","");
        PAGE.FORM1.setItemValue("otp","");
        PAGE.FORM1.setItemValue("otpChkYn","N");
        
    	$('#wTimer').css('display','none');
    	PAGE.FORM1.enableItem("usrId");
    	PAGE.FORM1.enableItem("ctn");
    	PAGE.FORM1.disableItem("otp");
    	PAGE.FORM1.showItem("btnOtp1");
	    PAGE.FORM1.hideItem("btnOtp2");
    }
}

function fnOtpChk(){
	var f = PAGE.FORM1.getFormData(true);
    
	if( f.usrId == "" ) {
        mvno.ui.alert("아이디를 입력해주세요.");
        return;
    }

    if( f.ctn == "" ) {
        mvno.ui.alert("전화번호를 입력해주세요.");
        return;
    }
    
    if( f.otp == "" ) {
        mvno.ui.alert("OTP를 입력해주세요.");
        return;
    }
    
	var usrId = f.usrId.toUpperCase();    // 실제 데이터는 대문자화해서 보내야!!
	var ctn = f.ctn;
	var otp = f.otp;
    
	mvno.cmn.ajax(ROOT + "/cmn/login/usrOtpChk.json", {usrId:usrId, ctn:ctn, otp:otp}, function(resultData) {
		if(resultData.result.code == "OK") {
			mvno.ui.alert(resultData.result.msg);
			PAGE.FORM1.setItemValue("otpComp","Y");
			PAGE.FORM1.disableItem("btnOtp");
			PAGE.FORM1.disableItem("otp");
			clearInterval(oTimer);
			$('#wTimer').css('display','none');
			PAGE.FORM1.hideItem("btnOtp2");
		} else if(resultData.result.code != "OK" && resultData.result.type == "OTP"){
			mvno.ui.alert(resultData.result.msg);
		} else{
			clearInterval(oTimer);
			otpEventOn(false);
			mvno.ui.alert(resultData.result.msg);
		}
	}, { resultTypeCheck: false });
}

function fnSave(){
	var f = PAGE.FORM1.getFormData(true);
	var usrId = f.usrId.toUpperCase();    // 실제 데이터는 대문자화해서 보내야!!
	mvno.cmn.ajax(ROOT + "/cmn/login/usrPwdResetSave.json", {usrId:usrId}, function(resultData) {
		if(resultData.result.code == "OK") {
			mvno.ui.alert(resultData.result.msg);
			otpEventOn(false);
			PAGE.FORM1.setItemValue("otpComp","N");
			PAGE.FORM1.enableItem("btnOtp");
		}else{
			mvno.ui.alert(resultData.result.msg);
			false;
		}
	},{ resultTypeCheck: false });
}
 
var PAGE = {
    onOpen : function() {
        mvno.ui.createForm("FORM1");
        PAGE.FORM1.hideItem("btnOtp2");
    }
};
</script>