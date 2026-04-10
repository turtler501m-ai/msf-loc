<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : msp_org_bs_1030.jsp
     * @Description : 사용자 정보 수정 화면
     * @
     * @ 수정일        수정자 수정내용
     * @ ---------- ------ -----------------------------
     * @ 2014.10.7 고은정 최초생성
     * @ 
     * @author : 고은정
     * @Create Date : 2014. 10. 7.
     */
%>
<!-- header -->
<div class="page-header">
    <h1>사용자 정보 수정 팝업</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
var DHX = {
		//사용자수정화면
        FORM1 : {
            title : "",
            items : [
                 {type: 'settings', position: 'label-left', labelWidth: '100', inputWidth: 'auto'},
                 {type: "block", blockOffset: 0, offsetLeft: 10,list: [
                     {type: 'input', name: 'usrId', label: '사용자ID',  width: 150,  value: '' , disabled:true},
                     {type: "newcolumn"},
                     {type: 'input', name: 'usrNm', label: '사용자명',  width: 150, offsetLeft: 50, value: '' , maxLength:10, required: true},   
                 ]},
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                     {type: 'input', name: 'presBiz', label: '사번',  width: 150,  value: '' , maxLength:20,validate: 'ValidAplhaNumeric', disabled:true},
                     {type: "newcolumn"},
                     {type: 'input', name: 'email', label: '이메일',  width: 150, offsetLeft: 50, value: '' , maxLength:50, validate:"ValidEmail"}, 
                 ]},
//                  {type: "block", blockOffset: 0,offsetLeft: 30, list: [
//                      {type: 'select', name: 'attcSctnCd', label: '소속구분',  width: 150,  userdata:{lov: 'CMN0003'}, disabled:true },
//                      {type: "newcolumn"},
//                      {type: 'select', name: 'dept', label: '부서/업체명',  width: 150, offsetLeft: 50, userdata:{lov: 'CMN0006'}, disabled:true},    
//                  ]},
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                     {type: 'input', name: 'attcSctnCdNm', label: '소속구분',  width: 150,  value:'', disabled:true },
                     {type: "newcolumn"},
                     {type: 'input', name: 'deptNm', label: '부서/업체명',  width: 150, offsetLeft: 50, value:'', disabled:true},    
                 ]},
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                     {type: 'input', name: 'pos', label: '직위',  width: 150,  value: '' , maxLength:30, disabled:true},
                     {type: "newcolumn"},
                     {type: 'input', name: 'odty', label: '직책',  width: 150, offsetLeft: 50, value: '' , maxLength:30, disabled:true},  
                 ]},   
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                     {type: 'password', name: 'pass', label: '비밀번호',  width: 150,  value: '' , maxLength:20, required: true},
                     {type: "newcolumn"},
                     {type: 'password', name: 'passChk', label: '비밀번호확인',  width: 150, offsetLeft: 50, value: '' , maxLength:20, required: true},
                 ]},                   
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                    {type: "block", blockOffset: 0, list: [
                         {type: 'select', name: 'telNum1', label: '전화번호',  width: 62, userdata:{lov: 'CMN0014'}},
                         {type: "newcolumn"},
                         {type: 'input', name: 'telNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
                         {type: "newcolumn"},
                         {type: 'input', name: 'telNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
                     ]},
                     {type: "newcolumn"},
                     {type: "block", blockOffset: 0, list: [
                         {type: 'select', name: 'mblphnNum1', label: '핸드폰번호',  offsetLeft: 50, width: 62, userdata:{lov: 'CMN0013'}, required: true},
                         {type: "newcolumn"},
                         {type: 'input', name: 'mblphnNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
                         {type: "newcolumn"},
                         {type: 'input', name: 'mblphnNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
                     ]},
                 ]},  
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                    {type: "block", blockOffset: 0, list: [
                         {type: 'select', name: 'fax1', label: 'FAX',  width: 62, userdata:{lov: 'CMN0014'}},
                         {type: "newcolumn"},
                         {type: 'input', name: 'fax2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
                         {type: "newcolumn"},
                         {type: 'input', name: 'fax3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
                         {type: "newcolumn"},
                         {type: 'password', name: 'oldPass', label: '현재비밀번호',  width: 150, offsetLeft: 50, value: '' , maxLength:20, required: true}
                     ]}
                 ]},
             ],

            buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnClose : { label: "닫기" }
                }
            },

            onButtonClick : function(btnId) {

                var form = this; // 혼란방지용으로 미리 설정 (권고)
                
                switch (btnId) {

                    case "btnSave":
                        mvno.cmn.ajax(ROOT + "/org/userinfomgmt/updateUserInfoMgmt.json", form, function(result) {
                        	if(result.result.code == "OK") {
                        		$.ajax({
                                    url: ROOT + "/cmn/login/logout.do"
                                  , dataType: 'json'
                                  , success: function(dataFromServer) {
                                      
                                      var result = dataFromServer.result;
                                      var returnCode = result.code;
                                      
//                                    if(returnCode == "SESSION_FINISH") {
//                                        mvno.ui.alert("로그아웃 되었습니다.");
                                          alert("사용자 정보가 변경되었습니다. \n다시 로그인 하세요.");
//                                    }
                                      
                                      parent.location.href= '/';
                                  }
                                });
                        	} else {
                        		mvno.ui.alert(result.result.msg);
                        		return false;
                        	}
                        	
                       });
                        
                        break;

                    case "btnClose" :
                    	mvno.ui.closeWindow();   
                        break;      
                        
                }

            },
            onValidateMore : function (data){
                
                if(!mvno.etc.validPassword(data.pass)){
                    
                    this.pushError("data.pass","비밀번호","8자리 이상 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
                }
                else if(data.pass != data.passChk){
                    
                    this.pushError("data.pass","비밀번호","일치하지 않습니다.");
                }
                else if(!mvno.etc.checkPackNum(data.telNum1, data.telNum2 , data.telNum3)){
                    
                    this.pushError("data.telNum1","전화번호",["ICMN0011"]);
                } 
                else if(data.telNum1 && !mvno.etc.checkPhoneNumber(data.telNum1+data.telNum2+data.telNum3)){
                    
                    this.pushError("data.telNum1","전화번호",["ICMN0012"]);
                }
                else if(!mvno.etc.checkPackNum(data.mblphnNum1, data.mblphnNum2 , data.mblphnNum3)){
                    
                    this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0001"]);
                }
                else if(!mvno.etc.checkPhoneNumber(data.mblphnNum1+data.mblphnNum2+data.mblphnNum3)){
                    
                    this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0012"]);
                }
                else if(!mvno.etc.checkPackNum(data.fax1, data.fax2 , data.fax3)){
                    
                    this.pushError("data.telNum2","FAX",["ICMN0011"]);
                }
                else if(data.fax1 && !mvno.etc.checkPhoneNumber(data.fax1+data.fax2+data.fax3)){
                    
                    this.pushError("data.telNum1","FAX",["ICMN0012"]);
                }
            }
        }
 };

var PAGE = {
        
    onOpen : function() {

        mvno.ui.createForm("FORM1");

        mvno.cmn.ajax(ROOT + "/org/userinfomgmt/userInfoMgmt.json", {usrId:'${loginInfo.userId}'}, function(resultData) {
        	var result = resultData.result.data;
        	PAGE.FORM1.setFormData(result);
        	
        	setInputDataAddHyphen(PAGE.FORM1, result, "mblphnNum", "tel");
            setInputDataAddHyphen(PAGE.FORM1, result, "telNum", "tel");
            setInputDataAddHyphen(PAGE.FORM1, result, "fax", "tel");
            
            PAGE.FORM1.setItemValue("pass");
            
            PAGE.FORM1.clearChanged();
        });
    }

};

function setInputDataAddHyphen(form, data, itemname, type){
    if(!form) return null;
    if(data == null) return null;
    if(!itemname) return data;
     
     var retValue  = "";
     retValue = mvno.etc.setTelNumHyphen(data[itemname]);
    
     var arrStr = retValue.split("-");
     
     var cnt = 1;
     for(i = 0; i < arrStr.length; i++){
    	 form.setItemValue(itemname+cnt,arrStr[i]);
         cnt++;
     }
 }
 
 
</script>