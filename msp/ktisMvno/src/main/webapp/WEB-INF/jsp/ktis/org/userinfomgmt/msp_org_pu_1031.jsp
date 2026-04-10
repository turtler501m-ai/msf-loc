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
    <h1>장기간(6개월) 비밀번호를 변경하지 않았습니다. 지금 변경하세요.</h1>
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
                     {type: 'input', name: 'usrNm', label: '사용자명',  width: 150, offsetLeft: 50, value: '' , disabled:true},   
                 ]},
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                     {type: 'password', name: 'pass', label: '비밀번호',  width: 150,  value: '' , maxLength:20, required: true},
                     {type: 'hidden', name: 'oldPass', label: '', value: '', width:100},
                     {type: "newcolumn"},
                     {type: 'password', name: 'passChk', label: '비밀번호확인',  width: 150, offsetLeft: 50, value: '' , maxLength:20, required: true},
                 ]}
             ],

            buttons : {
                center : {
                    btnSave : { label : "저장" }
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
                                          alert("비밀번호가 변경되었습니다. \n다시 로그인 하세요.");
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
            }
        }
 };

var PAGE = {
        
    onOpen : function() {

        mvno.ui.createForm("FORM1");

        mvno.cmn.ajax(ROOT + "/org/userinfomgmt/userInfoMgmt.json", {usrId:'${loginInfo.userId}'}, function(resultData) {
        	var result = resultData.result.data;
        	PAGE.FORM1.setFormData(result);
            
            PAGE.FORM1.setItemValue("pass");
            
            PAGE.FORM1.clearChanged();
        });
    }

};
</script>