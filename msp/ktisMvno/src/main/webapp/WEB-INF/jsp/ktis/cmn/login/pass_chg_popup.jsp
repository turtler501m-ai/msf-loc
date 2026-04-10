<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : pass_chg_popup.jsp
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
    <h1>패스워드 변경 팝업</h1>
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
                 	{type: 'label', label: '사용자ID'},
                 	{type : "newcolumn"},
                 	{type: 'label', label: '<%=request.getAttribute("usrId")%>' } 
                 ]},
                 {type: "block", blockOffset: 0, offsetLeft: 10,list: [
                    {type: 'hidden', name: 'usrId', label: '사용자ID',  width: 150,  value: '<%=request.getAttribute("usrId")%>', maxLength:10, required: true}
                 ]},
                 {type: "block", blockOffset: 0, offsetLeft: 10,list: [
                 	{type: "block", blockOffset: 0, list: [
                    	{type: 'password', name: 'oldPass', label: '현재비밀번호',  width: 150, value: '' , maxLength:20, required: true}
                    ]}
                 ]},
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                 	{type: 'password', name: 'pass', label: '새로운비밀번호', width: 150, value: '' , maxLength:20, required: true}, 
                 ]},
                 {type: "block", blockOffset: 0,offsetLeft: 10, list: [
                 	{type: 'password', name: 'passChk', label: '비밀번호확인', width: 150, value: '' , maxLength:20, required: true},
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
                        mvno.cmn.ajax(ROOT + "/cmn/login/updatePassInfo.json", form, function(result) {
                        	if(result.result.code == "OK") {
                        		$.ajax({
                        			  url: ROOT + "/cmn/login/logout.do"
                                  	, dataType: 'json'
                                  	, success: function(dataFromServer) {
                                  		var result = dataFromServer.result;
                                  		var returnCode = result.code;
                                  		
                                  		alert("패스워드가 변경되었습니다. \n다시 로그인 하세요.");
                                      
                                      	parent.location.href= '/';
                                  	}
                                });
                        	} else {
                        		mvno.ui.alert(result.result.msg);
                        		return false;
                        	}
                       });
                        
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
    }

};
 
 
</script>