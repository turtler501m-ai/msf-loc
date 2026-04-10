<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<div class="page-header">
	<h1>문자발송</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

var tlphNo;
var custNm;
var userName;

	var DHX = {
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
				{type: 'block', list: [
					{type:"input", label:"템플릿", name:"searchVal", width:100, offsetLeft:0}
					,{type:"hidden", name:"operType"}
				]},
				{type : "newcolumn"},
				{type : "button", name : "btnSearch", value : "조회", className:"btn-search1"} 
			],
			onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpSmsTemplateList.json", form);
						break;
				}
			}
		},
		GRID1 : {
			css : {
				height : "230px"
			},
			title : "템플릿",
			header : "템플릿명,CALLBACK,TEMPLATE_ID,MSG_TYPE,EXPIRE_TIME,SUBJECT",
			columnIds : "templateNm,callback,templateId,msgType,expireTime,subject",
			colAlign : "left,left,left,left,left,left",
			colTypes : "ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str",
			widths : "430,10,10,10,10,10",
			hiddenColumns: [1,2,3,4,5],
			//paging: true,
			//pageSize:20,
			onRowSelect : function(rowId, celInd) {
				var data = this.getRowData(rowId);
				
				PAGE.FORM2.setItemValue("subject", data.subject);
				PAGE.FORM2.setItemValue("text", fn_replaceTextarea(data.text));
				PAGE.FORM2.setItemValue("msgType", data.msgType);
				PAGE.FORM2.setItemValue("templateId", data.templateId);
				PAGE.FORM2.setItemValue("expireTime", data.expireTime);
				PAGE.FORM2.setItemValue("callback", data.callback);
				
			}
		},
		//------------------------------------------------------------
		FORM2 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0}
				, {type: 'block', list: [
					{type:"input", label:"수신자번호", name:"dstaddr", width:100, offsetLeft:10, validate:"ValidNumeric", required:true}
					,{type:"newcolumn"}
					,{type:"select", label:"발신자번호", name:"callback", width:100, offsetLeft:20, required:true}
				]}
				, {type: 'block', list: [
					{type:"label", label:"문자내용", offsetLeft:10}
				]}
				, {type: 'block', list: [
					{type:"input", name:"text", rows:14, width:420, maxLength:1300}
				]}
				, {type:"hidden", name:"msgType"}
				, {type:"hidden", name:"subject"}
				, {type:"hidden", name:"tlphNo"}
				, {type:"hidden", name:"templateId"}
				, {type:"hidden", name:"expireTime"}
				, {type:"hidden", name:"userName"}
				, {type:"hidden", name:"resNo"}
			],
			buttons : {
				center : {
					 btnSave : { label : "전송" }
					,btnClose : { label : "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSave":
						if(form.getItemValue("dstaddr").length < 11 || form.getItemValue("dstaddr").length > 12){
							mvno.ui.alert("수신번호의 길이를 확인하세요.");
							return;
						}
						
						var firstNo = form.getItemValue("dstaddr").substring(0, 3);
						console.log("firstNo=" + firstNo);
						
						if(firstNo != "010" && firstNo != "011" && firstNo != "016" && firstNo != "017" && firstNo != "018" && firstNo != "019" && firstNo != "070"){
							mvno.ui.alert("수신번호의 국번을 확인하세요.");
							return;
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("text"))){
							mvno.ui.alert("문자내용을 입력하세요.");
							return;
						}
						
						var inputSize = ($('textarea[name="text"]').val()).length;
						var maxLength = $('textarea[name="text"]').attr('maxLength');
						if (inputSize > 0 && inputSize > maxLength) {
							mvno.ui.alert("문자내용은 "+maxLength+"자까지 입력가능합니다.");
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/setSmsSend.json", form, function(result) {
							mvno.ui.notify("NCMN0001");
							mvno.ui.closeWindow();
						});
						
						break;
						
					case "btnClose":
						mvno.ui.closeWindow();
						
						break;
				}
			}
		}
		, 
	};

var PAGE = {
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createForm("FORM2");
		
		if("${param.operType}" != "") PAGE.FORM1.setItemValue("operType", "${param.operType}");
		if("${param.dstaddr}" != "") PAGE.FORM2.setItemValue("dstaddr", "${param.dstaddr}");
		if("${param.tlphNo}" != "") {
			tlphNo = "${param.tlphNo}";
		}else{
			tlphNo = "010-0000-0000";
		}
		if("${param.custNm}" != "") custNm = fncDeCode("${param.custNm}");
		if("${param.userName}" != "") userName = fncDeCode("${param.userName}");
		if("${param.resNo}" != "") PAGE.FORM2.setItemValue("resNo", "${param.resNo}");
		
		// callback
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0070",orderBy:"etc1"}, PAGE.FORM2, "callback");
		
		// 템플릿목록조회
		PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpSmsTemplateList.json", PAGE.FORM1);
		
	}
};

function fn_replaceTextarea(s) { //textarea 에서 &가 &amp;로 자동 변경되어 수정함.
	var s = new String(s);
	s = s.replace(/&amp;/ig, "&");
	s = s.replace(/&lt;/ig, "<");
	s = s.replace(/&gt;/ig, ">");
	s = s.replace(/&quot;/ig, "\"");
	s = s.replace(/&#39;/ig, "'");
	s = s.replace(/<br>/ig, "\r\n");
	if(s.search("#tlphNo#") > 0) s = s.replace("#tlphNo#", tlphNo);
	if(s.search("#custNm#") > 0) s = s.replace("#custNm#", custNm);
	if(s.search("#userNm#") > 0) s = s.replace("#userNm#", userName);
	return s;
}

function fncDeCode(param){
	var sb = '';
	var pos = 0;
	var flg = true;

	if(param != null)
	{
		if(param.length>1)
		{
			while(flg)
			{
				var sLen = param.substring(pos,++pos);
				var nLen = 0;
				
				try
				{
					nLen = parseInt(sLen);
				}
				catch(e)
				{
					nLen = 0;
				}
				
				var code = '';
				
				if((pos+nLen)>param.length)
					code = param.substring(pos);
				else
					code = param.substring(pos,(pos+nLen));
				
				pos  += nLen;
				sb += String.fromCharCode(code);
				
				if(pos >= param.length)
					flg = false;
			}
		}
	}
	
	return sb;
}
</script>