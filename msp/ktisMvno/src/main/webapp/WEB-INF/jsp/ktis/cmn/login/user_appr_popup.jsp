<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : user_appr_popup.jsp
     * @Description : 회원가입신청 팝업
     * @
     * @ 수정일        수정자 수정내용
     * @ ---------- ------ -----------------------------
     * @ 2019.06.19 권오승 최초생성
     * @ 
     * @author : 권오승
     * @Create Date : 2019.06.19.
     */
%>
<style>
	.dhxform_obj_dhx_skyblue .dhx_file_uploader.dhx_file_uploader_title div.dhx_upload_controls div.dhx_file_uploader_button.button_info{left: 125px;padding-top:3px;}
	.dhxform_obj_dhx_skyblue .dhx_file_uploader div.dhx_upload_files div.dhx_file_param.dhx_file_name{text-indent: 105px;}
</style> 

<!-- header -->
<div class="page-header">
    <h1>대리점계정신청 팝업</h1>
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
            	{type: 'settings', position: 'label-left', labelWidth: '120', inputWidth: 'auto'},
				 {type: "block", blockOffset: 0, offsetLeft: 30, list: [
					 {type: 'input', name: 'usrId', label: '사용자ID',  width: 150,  value: '' , required: true, maxLength:10 ,validate: 'ValidAplhaNumeric'},
					 {type: "newcolumn"},
					 {type:"button", name: 'btnIdChk', value:"중복"},
					 {type: "newcolumn"},
					 {type: 'input', name: 'usrNm', label: '사용자명',  width: 150, offsetLeft: 40, value: '' , maxLength:15, required: true}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'orgnId', label: '대리점ID',  width: 150, offsetLeft: 0,  value: '' , disabled:true},
					{type: "newcolumn"},
					{type:"button", name: 'btnOrgFind', value:"찾기"},
					{type: "newcolumn"},
					{type: 'input', name: 'orgnNm', label: '대리점명',  width: 150, offsetLeft: 40, value: '' , disabled:true, required: true,validate: 'NotEmpty'},
				 ]},
				 {type: "block", blockOffset: 0, offsetLeft: 30, list: [
					 {type: 'select', name: 'mblphnNum1', label: '핸드폰번호',  offsetLeft: 0, width: 62, required: true},
					 {type: "newcolumn"},
					 {type: 'input', name: 'mblphnNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
					 {type: "newcolumn"},
					 {type: 'input', name: 'mblphnNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
				 ]}
					,{type: "block", blockOffset: 0, offsetLeft: 30, list: [
						{type: "upload", label:"파일업로드" , offsetLeft: 0, name: "file_upload1", width:535, url: "/cmn/login/fileUpLoad.do" , autoStart: false, userdata: { limitCount : 2, limitsize: 1000, delUrl:"/cmn/login/deleteFile2.json"}}
					]}
					,{type:"block", list:[
	                    {type:"newcolumn", offset:150}	
	                    ,{type:"template", label:"",value:"* 파일 최대 갯수는 2개이며 사이즈는 최대 1000KByte 입니다.",position:"label-left",align: "center",height:30}
	                	]
					}
            ],
            buttons : {
				center : {
					btnSave : { label : "신청" },
					btnClose : { label: "닫기" }
				}
            },
            onFocus : function(id , value) {
            	var usrId = PAGE.FORM1.getItemValue("usrId");
            	switch(id){
            		case "usrNm" :
            			PAGE.FORM1.setItemValue( "usrId" , usrId.toUpperCase());
            		break;
            		case "mblphnNum1" :
            			PAGE.FORM1.setItemValue( "usrId" , usrId.toUpperCase());
            		break;
            		case "mblphnNum2" :
            			PAGE.FORM1.setItemValue( "usrId" , usrId.toUpperCase());
            		break;
            		case "mblphnNum3" :
            			PAGE.FORM1.setItemValue( "usrId" , usrId.toUpperCase());
            		break;
            		case "btnIdChk" :
            			PAGE.FORM1.setItemValue( "usrId" , usrId.toUpperCase());
            		break;
            		case "file_upload1" :
            			PAGE.FORM1.setItemValue( "usrId" , usrId.toUpperCase());
            		break;
            	}
            },
     
            onButtonClick : function(btnId) {
            	var usrId = PAGE.FORM1.getItemValue("usrId");
                var form = this; // 혼란방지용으로 미리 설정 (권고)
                
                switch (btnId) {
                    case "btnSave":
                    	PAGE.FORM1.setItemValue( "usrId" , usrId.toUpperCase());
                    	mvno.cmn.ajax(ROOT + "/cmn/login/isExistUsrId.json", {usrId:PAGE.FORM1.getItemValue("usrId")}, function(resultData) {
							if(resultData.result.resultCnt > 0){
								mvno.ui.alert("ICMN0008");
							} else {
								mvno.cmn.ajax(ROOT + "/cmn/login/insertUserInfoMgmt.json", form, function(result) {
									if (result.result.code == "OK") {
											mvno.ui.alert("대리점계정 신청이 완료되었습니다.",'', function(){
												mvno.ui.closeWindow();
										});
											
									} else {
										mvno.ui.alert(result.result.msg);
									}
								});
							}
						});
                       break;
                    case "btnClose" :
                    	mvno.ui.closeWindow();
        				break;
        			
                    case "btnIdChk" : //ID중복체크
                    	
                    	PAGE.FORM1.setItemValue( "usrId" , usrId.toUpperCase());
                    	if(PAGE.FORM1.getItemValue("usrId") == ""){
                    		mvno.ui.alert("[사용자ID]를 입력하세요");	
                    		return;
        				}
                    	mvno.cmn.ajax(ROOT + "/cmn/login/getIdCheck.json", {usrId:PAGE.FORM1.getItemValue("usrId")}, function(resultData) {
							if(resultData.result.result == "P"){
								mvno.ui.alert("승인이 필요한 ID 입니다.");
							}else if (resultData.result.result == "A"){
								mvno.ui.alert("신청 할 수 없는 ID입니다.");		
							}else {
								mvno.ui.alert("신청가능한 ID입니다.");	
							}
						});
                    	break;
                    	case "btnOrgFind":
    						//상위조직검색
    						PAGE.FORM1.setItemValue( "usrId" , usrId.toUpperCase());
    						mvno.ui.codefinder("APPRORGAGENCY", function(result) {
    							form.setItemValue("orgnId", result.orgnId);
    							form.setItemValue("orgnNm", result.orgnNm);
    							form.setItemValue("attcSctnCd",result.typeCd);
    							form.disableItem("attcSctnCd");
    						});
    						break;
                }
            
            },
            onValidateMore : function (data){
            	if(!data.orgnId){
					this.pushError("data.orgnId","조직ID",["ORGN0006"]);
				}else if(!mvno.etc.checkPackNum(data.mblphnNum1, data.mblphnNum2 , data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0001"]);
				}
				else if(!mvno.etc.checkPhoneNumber(data.mblphnNum1+data.mblphnNum2+data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0012"]);
				}
            }
        }
 };

var PAGE = {
		
    onOpen : function() {
        mvno.ui.createForm("FORM1");
        mvno.cmn.ajax4lov( ROOT + "/cmn/login/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "mblphnNum1"); // 핸드폰번호
        var fileLimitCnt = 2;
        
        //mvno.cmn.ajax2
        PAGE.FORM1.disableItem("fileDown1");
        PAGE.FORM1.disableItem("fileDel1");
        PAGE.FORM1.disableItem("fileDown2");
        PAGE.FORM1.disableItem("fileDel2");
        
        PAGE.FORM1.clearChanged();
        
        var uploader = PAGE.FORM1.getUploader("file_upload1");
        uploader.revive();
        
        PAGE.FORM1.showItem("file_upload1");
        PAGE.FORM1.setUserData("file_upload1","limitCount",fileLimitCnt);
        PAGE.FORM1.setUserData("file_upload1","limitsize",1000);
        PAGE.FORM1.setUserData("file_upload1","delUrl","/cmn/login/deleteFile2.json");
    }
};
 
 
</script>