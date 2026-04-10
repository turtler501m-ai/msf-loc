<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	
<div id="GROUP1">
	<div id="TABBAR1"></div>  
		<div id="TABBAR1_a1" class="section-search" style="margin-top: 20px">
			<div id="FORM2"></div>
		</div>  
		<!-- <div id="TABBAR1_a2"  >
			<div id="FORM3" class="section-search" style="margin-top: 20px"></div> 
			<div id="GRID1"></div>  
		</div> 	 -->
		<div id="TABBAR1_a3" class="section-search" style="margin-top: 20px">
			<div id="FORM4"></div>
		</div> 
</div>
<style>
	.dhxform_obj_dhx_skyblue .dhx_file_uploader.dhx_file_uploader_title div.dhx_upload_controls div.dhx_file_uploader_button.button_info{left: 105px;padding-top:3px;}
	.dhxform_obj_dhx_skyblue .dhx_file_uploader div.dhx_upload_files div.dhx_file_param.dhx_file_name{text-indent: 85px;}
</style>
<!-- Script -->
<script type="text/javascript">

function setExcelCnt(){
}

function getMsgByte(msg_str,form){
	var stringByteLength = 0;
	var chkLength = 0;

	for(var i=0; i<msg_str.length; i++)
	{
		if(escape(msg_str.charAt(i)).length >= 4)
			stringByteLength += 2;
		else if(escape(msg_str.charAt(i)) == "%A7")
			stringByteLength += 2;
		else
			if(escape(msg_str.charAt(i)) != "%0D")
				stringByteLength++;
	}
	
	form.setItemLabel("msgByte", stringByteLength + " byte");
	return;
}

var DHX = {

	FORM1 : {
		
	}// FORM end..
	,TABBAR1: {

		css : { height : "700px" }
		,tabs: [
		       { id: "a1", text: "단문전송" },
			    /* { id: "a2", text: "다량문자전송" }, */
			    { id: "a3", text: "MMS전송" }
			    
		]	
	    ,onSelect: function (id, lastId, isFirst) {

			switch (id) {

				case "a1":
					mvno.ui.createForm("FORM2");
					
					mvno.cmn.ajax4lov(ROOT + "/pps/sms/smsmgmt/ppsCallingNumList.json",
					null 
			      	, PAGE.FORM2, "callingNumber");
					
					PAGE.FORM2.setItemValue("smsTitle","");
           		 	PAGE.FORM2.setItemValue("callingNumber","");
           		 	PAGE.FORM2.setItemValue("calledNumber","");
           		 	PAGE.FORM2.setItemValue("smsMsg","");
           		 	PAGE.FORM2.setItemLabel("msgByte", "0 byte");
					
		    		break;

				/* case "a2":
					
		    		mvno.ui.createForm("FORM3");
		    		mvno.ui.createGrid("GRID1");
		    		
		    		PAGE.FORM3.setItemValue("excelCnt", "");
					PAGE.GRID1.clearAll();
					PAGE.FORM3.setItemValue("file_upload1", "");
					PAGE.FORM3.setItemValue("smsTitle", "");

		    		var uploader = PAGE.FORM3.getUploader("file_upload1");

				 	uploader.revive();
				 	
				 	PAGE.FORM3.attachEvent("onBeforeFileAdd",function(realName, size, file){
				 		if(realName.substr(realName.length-3, realName.length) != 'xls' && realName.substr(realName.length-3, realName.length) != 'XLS'){
				 			mvno.ui.alert("다량문자전송은 xls파일만 가능합니다.");
				 			return false;
				 		}
                	    return true;
			       });
				 	
				 	PAGE.FORM3.attachEvent("onFileAdd",function(file){
				 		PAGE.FORM3.setItemValue("fileName", file);
                	   
			       });
		    		
					PAGE.FORM3.attachEvent("onUploadComplete",function(count){
						var fileName = PAGE.FORM3.getItemValue("fileName");
						var data = {"fileName":fileName};
						PAGE.GRID1.list(ROOT + "/pps/smsmgmt/readSmsExcelFile.do", data, {callback : function() {
							PAGE.FORM3.setItemValue("excelCnt", "총 전송가능수 : " + PAGE.GRID1.getRowsNum());
						}});
				   });
					
					PAGE.FORM3.attachEvent("onBeforeFileRemove",function(realName, serverName){
						PAGE.FORM3.setItemValue("excelCnt", "");
						PAGE.GRID1.clearAll();
						PAGE.FORM3.setItemValue("smsTitle", "");
                	    return true;
			       });

		    		break; */

		    		
				case "a3":
					mvno.ui.createForm("FORM4");
					
					mvno.cmn.ajax4lov(ROOT + "/pps/sms/smsmgmt/ppsCallingNumList.json",
					null 
			      	, PAGE.FORM4, "callingNumber");
					
					PAGE.FORM4.setItemValue("mmsTitle","");
           		 	PAGE.FORM4.setItemValue("callingNumber","");
           		 	PAGE.FORM4.setItemValue("calledNumber","");
           		 	PAGE.FORM4.setItemValue("mmsMsg","");
           		 	PAGE.FORM4.setItemLabel("msgByte", "0 byte");
           		 	PAGE.FORM4.getUploader("file_upload1").revive();
					
		    		break;
		    		
			}

	    }

	}// FORM end..
	,FORM2 : {
		title: "단문 전송"
		,buttons : {
			center : {
				btnReg : { label : "문자전송" }
			}
		}
		,items:[
				{type: 'hidden', name: 'opCode', value: 'REG'},
				{type: "block", list:[
					{type: 'settings', position: 'label-left', labelWidth: 100, inputWidth: 'left', labelAlign:"left"},
					{type:"block", list:[
											{type:"newcolumn", offset:100}
						                   	,{type:"input", label:"제목", name : "smsTitle", width:500, required:true}
						                   	
                 						]
	                 }
	                 ,{type:"block", list:[
											{type:"newcolumn", offset:100}
						                   	,{type:"select", label:"발신번호", name : "callingNumber", width:120, required:true}
                 						]
                 	}
                 	,{type:"block", list:[
											{type:"newcolumn", offset:100}
											,{type:"input", label:"수신번호", rows:3, name : "calledNumber", maxLength:119, width:500, required:true}
                 						]
                 	}
                 	,{type:"block", list:[
											{type:"newcolumn", offset:100}
											,{type:"input", label:"내용", rows:5, name : "smsMsg", width:500, required:true}
											,{type:"newcolumn", offset:5}
											,{type:"template", label:"",value:"",name:"msgByte",position:"label-left",align: "center", valign:"bottom",height:30}
	               						]
	               	}
                 	,{type:"block", list:[
											{type:"newcolumn", offset:200}	
											,{type:"template", label:"",value:"* 받는사람의 수신번호는 쉼표(,)를 기준으로 10개까지 전송가능합니다.",position:"label-left",align: "center", height:30}
	               						]
	               	}
                 	,{type:"block", list:[
											{type:"newcolumn", offset:200}	
											,{type:"template", label:"",value:"* ex) 01012345678,01023456789,....",position:"label-left",align: "center",height:30}
	               						]
	               	}
                 ]}
		]
		,onButtonClick : function(btnId) {
	
			var form = this;
	
			switch (btnId) {
	
				case "btnReg":
					mvno.ui.confirm("문자전송 하시겠습니까?", function() {
						//var data = this.getFormData(true);

						if(isNaN(PAGE.FORM2.getItemValue("calledNumber").split(",").join(""))){
							mvno.ui.alert("쉼표(,)를 제외한 숫자만 입력해 주세요.");
							return;
						}
						
					    var url = ROOT + "/pps/sms/smsmgmt/SmsDumpShortSmsProc.json";
						mvno.cmn.ajax(
								url, 
								PAGE.FORM2
								, function(results) {
									 var result = results.result;
		                                 var retCode = result.oRetCd;
		                                 var msg = result.oRetMsg;
		                                 if(retCode == "0000") {
		                           		  	mvno.ui.alert("문자전송 요청을 성공하였습니다.");
		                           		 	PAGE.FORM2.setItemValue("smsTitle","");
		                           		 	PAGE.FORM2.setItemValue("callingNumber","");
		                           		 	PAGE.FORM2.setItemValue("calledNumber","");
		                           		 	PAGE.FORM2.setItemValue("smsMsg","");
		                           		 	PAGE.FORM2.setItemLabel("msgByte", "0 byte");
		                                 }
		                                 else {
		                               	  	mvno.ui.alert(msg);
		                                 }
								});	
					});
					
					break;
					
				case "btnSmsSendCancel":
					break;
			}
	
		},
		onKeyUp : function(itemId,key) {
			var form = this;
			switch (itemId.name) {

				case "smsMsg":
					
					var smsMsg = PAGE.FORM2.getItemValue("smsMsg");
					getMsgByte(smsMsg,form);
					
					break;	
			}
		
		}
		
	}// FORM end..
	/* ,FORM3 : {
		title: "다량문자전송"
		,items: [	
		 		{type: 'hidden', name: 'opCode', value: 'REG'},
		 		{type: 'hidden', name: 'fileName', value: ''},
                 {type: "block", list:[
					{type: 'settings', position: 'label-left', labelWidth: 85, inputWidth: 'left', labelAlign:"left"},
					{type:"block", list:[
											{type:"newcolumn", offset:100}
						                   	,{type:"input", label:"용도", name : "smsTitle", width:500}
						                   	
              						]
	                 },
					
                 ]},
		 		{type: "block", list:[
					{type: 'settings', position: 'label-left', labelWidth: 100, inputWidth: 'left', labelAlign:"left"},
					{type:"block", list:[
											{type:"newcolumn", offset:100}
											,{type: "upload", label:"문자전송엑셀파일" 
											   	,name: "file_upload1"
											   	,inputWidth: 500 
												,url: "/pps/smsmgmt/uploadSmsExcelFile.do" 
											    ,userdata:{limitSize:1000} 
											   //	,swfPath: "uploader.swf"
											   	,autoStart: true 
											   // ,swfUrl: "/lgs/prdtsrlmgmt/fileUpUsingExcel.do"
												}
											,{type:"newcolumn", offset:5}
											,{type:"button", name: "excelSample", value:"샘플"}
						                   	
                 						]
	                 }
					,{type:"block", list:[
											{type:"newcolumn", offset:95}	
											,{type:"template", name:"excelCnt", label:"",value:"",position:"label-left",align: "center",height:30}
	               						]
	               	}
                 ]}
				
			]	
		,onButtonClick : function(btnId) {
			var form = this;
			
			if(btnId == 'excelSample'){
				var url = "/pps/sms/smsmgmt/SmsDumpMgmtExcelSample.json";
				 mvno.cmn.download(url, false, null);
			}

		}
		
	},// FORM end.. */

	,FORM4 : {
		title: "MMS 전송"
		,buttons : {
			center : {
				btnReg : { label : "MMS 전송" }
			}
		}
		,items:[
				{type: 'hidden', name: 'opCode', value: 'REG'},
				{type: "block", list:[
					{type: 'settings', position: 'label-left', labelWidth: 100, inputWidth: 'left', labelAlign:"left"}
					,{type:"block", list:[
											{type:"newcolumn", offset:100}
						                   	,{type:"input", label:"제목", name : "mmsTitle", width:500, required:true}
						                   	
                 						]
	                }
	                ,{type:"block", list:[
											{type:"newcolumn", offset:100}
						                   	,{type:"select", label:"발신번호", name : "callingNumber", width:120, required:true}
                 						]
                 	}
	                ,{type:"block", list:[
										,{type:"newcolumn", offset:200}
					                   	,{type:"button", name : "btnPhoneGrpCall", width:100, value:"그룹번호 추가"}
										]
					}
                 	,{type:"block", list:[
											{type:"newcolumn", offset:100}
											,{type:"input", label:"수신번호", rows:8, name : "calledNumber", maxLength:1000, width:500, required:true}
                 						]
                 	}
                 	,{type:"block", list:[
											{type:"newcolumn", offset:100}
											,{type:"input", label:"내용", rows:10, name : "mmsMsg", width:500, required:true}
											,{type:"newcolumn", offset:5}
											,{type:"template", label:"",value:"",name:"msgByte",position:"label-left",align: "center", valign:"bottom",height:30}
	               						]
	               	}
					,{type: "block", list: [
											{type:"newcolumn", offset:100}
											,{type: "upload", label:"파일업로드", name: "file_upload1", width:600, url: "/pps/sms/smsmgmt/fileUpLoad.do" ,autoStart: false, userdata: { limitCount : 3, limitsize: 1000, delUrl:"/org/common/deleteFile2.json"} }
					]}			

                 	,{type:"block", list:[
						                    {type:"newcolumn", offset:200}	
						                    ,{type:"template", label:"",value:"* 한글명 파일은 전송되지 않습니다.",position:"label-left",align: "center",height:30}
						                ]
                 	}
                 	,{type:"block", list:[
						                    {type:"newcolumn", offset:200}	
						                    ,{type:"template", label:"",value:"* 파일 최대 갯수는 3개이며 사이즈는 각각 최대 300kb미만에 용량 1500 x 1440 미만으로 가능합니다.",position:"label-left",align: "center",height:30}
						                ]
                 	}
                 	,{type:"block", list:[
											{type:"newcolumn", offset:200}	
											,{type:"template", label:"",value:"* ex) 01012345678,01023456789,....",position:"label-left",align: "center",height:30}
	               						]
	               	}
                ]}
		]
		,onButtonClick : function(btnId) {
	
			var form = this;
	
			switch (btnId) {
	
				case "btnReg":
					mvno.ui.confirm("문자전송 하시겠습니까?", function() {
						//var data = this.getFormData(true);

						if(isNaN(PAGE.FORM4.getItemValue("calledNumber").split(",").join(""))){
							mvno.ui.alert("쉼표(,)를 제외한 숫자만 입력해 주세요.");
							return;
						}
						
					    var url = ROOT + "/pps/sms/smsmgmt/smsDumpMmsSendNew.json";
						mvno.cmn.ajax(
								url, 
								PAGE.FORM4
								, function(results) {
									 var result = results.result;
		                                 var retCode = result.oRetCd;
		                                 var msg = result.oRetMsg;
		                                 if(retCode == "0000") {
		                           		  	mvno.ui.alert("문자전송 요청을 성공하였습니다.");
		                           		 	PAGE.FORM4.setItemValue("mmsTitle","");
		                           		 	PAGE.FORM4.setItemValue("callingNumber","");
		                           		 	PAGE.FORM4.setItemValue("calledNumber","");
		                           		 	PAGE.FORM4.setItemValue("mmsMsg","");
		                           		 	PAGE.FORM4.setItemLabel("msgByte", "0 byte");
		                           		 	PAGE.FORM4.getUploader("file_upload1").reset();
// 											PAGE.FORM4.refresh();
		                                 }
		                                 else {
		                               	  	mvno.ui.alert(msg);
		                                 }
		                                 
								});	
					});
					
					break;
					
				case "btnPhoneGrpCall":
					mvno.ui.createGrid("GRID2");
					
					var data = {
						useYn : "Y"
					}
					
					PAGE.GRID2.list(ROOT + "/cmn/smsmgmt/smsSendPhoneGrpList.json", data);
					
					mvno.ui.popupWindowById("GRID2", "전화번호그룹", 310, 500, {
						onClose: function(win) {
							$('#GRID2 input:checkbox').prop('checked',false);
							win.closeForce();
						}
					});
					
					break;
					
				case "btnSmsSendCancel":
					break;
			}
	
		},
		onKeyUp : function(itemId,key) {
			var form = this;
			switch (itemId.name) {

				case "mmsMsg":
					
					var mmsMsg = PAGE.FORM4.getItemValue("mmsMsg");
					getMsgByte(mmsMsg,form);
					
					break;	
			}
		
		}
		
	},// FORM4 end..
	
	//---------------------------------------
	GRID1: {
	    css: {
			height: "400px"
	    },
	    header: "발신번호,수신번호,제목,전송내용",
	    columnIds: "callingNumber,calledNumber,lmsTitle,smsMsg",
	    widths : "150,150,200,500",
	    colAlign: "center,center,left,left",
	    colTypes: "ro,ro,ro,ro",
	    colSorting: "str,str,str,str",
	    paging : false,
		pageSize :1000,
		pagingStyle :3,
		multiCheckbox : true,
	    buttons: {
			right: {
				rowDel: { label: "선택삭제" },
				btnMod: { label: "문자전송" }
			}
	    }
	    ,onButtonClick : function(btnId) {		
	    	var grid = this;
			switch (btnId) {
				case "rowDel":
					var rowIds = grid.getCheckedRows(0);
					if(rowIds == ''){
						mvno.ui.alert("체크된 리스트가 없습니다.");
						return;
					}
					
					var checkedData = grid.classifyRowsFromIds(rowIds, "smsTitle");
					grid.deleteRowByIds(rowIds);
					PAGE.FORM3.setItemValue("excelCnt", "총 전송가능수 : " + parseInt(PAGE.GRID1.getRowsNum()));
					break;
				case "btnMod":
					if(parseInt(PAGE.GRID1.getRowsNum()) == 0){
						mvno.ui.alert("전송요청할 리스트가 없습니다.");
						return;
					}
					
					if(PAGE.FORM3.getItemValue("smsTitle") == ''){
						mvno.ui.alert("용도을 입력해 주세요.");
						return;
					}
					
					mvno.ui.confirm("문자요청건수가 많을 경우 <br>1분정도의 시간이 소요됩니다.<br>문자전송 하시겠습니까?", function() {
						var rowIds = grid.getAllRowIds();

						var changedData = grid.classifyRowsFromIds(rowIds, "callingNumber,calledNumber,lmsTitle,smsMsg");
						mvno.cmn.ajax4json(ROOT + "/pps/sms/smsmgmt/SmsDumpExcelSmsProc.json", {allData: changedData.ALL, smsTitle: PAGE.FORM3.getItemValue("smsTitle") }, function(results) {
							var result = results.result;
                               var retCode = result.oRetCd;
                               var msg = result.oRetMsg;

                               if(retCode == "0000") {
                         		  	mvno.ui.alert("문자전송 요청을 성공하였습니다.");
                         		  	PAGE.FORM3.setItemValue("excelCnt", "");
         							PAGE.GRID1.clearAll();
         							PAGE.FORM3.setItemValue("file_upload1", "");
         							PAGE.FORM3.setItemValue("smsTitle", "");
         							
                               }
                               else {
                             	  	mvno.ui.alert(msg);
                               }
					    },{timeout:60000});
					});
					
					break;
			}
		}
	    
	},// GRID1 end..
	
	// --------------------------------------------------------------------------
	//전화번호그룹
	GRID2 : {
		css : {
			height : "350px"
			, width : "250px"
		},
		title:"전화번호그룹",
		header : "그룹코드,그룹명,건수",
		columnIds : "grpId,grpNm,cnt",
		colAlign : "left,left,left",
		colTypes : "ro,ro,ro",
		colSorting : "str,str,str",
		widths : "100,150,50",
		paging: false,
		hiddenColumns : [0],
		multiCheckbox : true,
		buttons : {
			center : {
				btnSave : { label : "추가" },
				btnClose : { label : "닫기" }
			}
		},
		onButtonClick:function(btnId) {
			var form = this;
			
			switch(btnId) {
			
				case "btnSave" :
					var rowIds = this.getCheckedRows(0);
					
					if(!rowIds) {
						mvno.ui.alert("ECMN0003");
						return;
					} else {
						
						var sdata = this.classifyRowsFromIds(rowIds);
						mvno.ui.confirm("전화번호를 추가하시겠습니까?", function() {
							PAGE.FORM4.setItemValue("calledNumber","");
							mvno.cmn.ajax4json(ROOT + "/cmn/smsmgmt/getSmsPhoneGrpDtlList.json", sdata, function(result) {
								var bfCalledNumber = PAGE.FORM4.getItemValue("calledNumber");
								var afCalledNumber = "";
								
								if(bfCalledNumber == "" || bfCalledNumber == null){
									afCalledNumber = bfCalledNumber + result.result.phoneList;
								} else {
									afCalledNumber = bfCalledNumber + "," + result.result.phoneList;
								}
								
								PAGE.FORM4.setItemValue("calledNumber",afCalledNumber);

								mvno.ui.closeWindowById("GRID2");
								mvno.ui.notify("전화번호를 추가했습니다.");
							});
						});
					}
					break;
					
				case "btnClose" :
					mvno.ui.closeWindowById("GRID2");
					break;
			
			}
			
		}
	}
									
};

var PAGE = {
	    title : "${breadCrumb.title}",
	    breadcrumb : "${breadCrumb.breadCrumb}",  
		buttonAuth: ${buttonAuth.jsonString},
		
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createTabbar("TABBAR1");
	}

};
		
</script>