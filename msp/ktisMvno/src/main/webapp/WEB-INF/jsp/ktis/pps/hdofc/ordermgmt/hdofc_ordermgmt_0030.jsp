<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : hdofc_ordermgmt_0030.jsp
 * @Description : 주문관리
 * @Modification Information
 *
 *   수정일 			수정자				 수정내용
 *  -------		--------		---------------------------
 *  2017.05.01	김 웅				최초 생성
 *
 * author 김 웅
 * since 2017.05.01
 */
 %>
	<!-- 화면 배치 -->
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1" class="section-full"></div>
	<div id="FORM11" class="section-search"></div>
	<div id="FORM12" class="section-search"></div>  
	<div id="FORM13" class="section-search"></div>
	<div id="FORM14" class="section-search"></div>           
			
	<!-- Script -->
	<script type="text/javascript">
	
	var old_url = "";
	
	function setCurrentMonthFirstDay(form, name)
	{
		var today = new Date();

		var mm = today.getMonth(); //January is 0!
		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,mm,01));
	}
	
	function setCurrentDate(form, name)
	{
		form.setItemValue(name, new Date());
	}
	
	function setAgentDlv(agentId){

		var url = ROOT + "/pps/hdofc/ordermgmt/orderAgentInfo.json";
		if(agentId != ""){
			mvno.cmn.ajax(url, {agentId : agentId}, function(results) {
				if(results.result.data.rows.length == 0){
					PAGE.FORM11.setItemValue("dlvAddr", "");
					PAGE.FORM11.setItemValue("dlvName", "");
					PAGE.FORM11.setItemValue("dlvPhone", "");
				}else{
					if(results.result.data.rows[0].agentAddr == "()"){
						PAGE.FORM11.setItemValue("dlvAddr", "");	
					}else{
						PAGE.FORM11.setItemValue("dlvAddr", results.result.data.rows[0].agentAddr);	
					}
					
					PAGE.FORM11.setItemValue("dlvName", results.result.data.rows[0].rprsenNm);
					PAGE.FORM11.setItemValue("dlvPhone", results.result.data.rows[0].telnum);
				}
			});
		}
	}
	
	function form11SetChangeValue(name, type, showFlag){
		if(name == "TYPE"){
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/orderGoodsCd.json",{searchType : type, searchShowFlag : showFlag, cdFlag : "Y"} , PAGE.FORM11, "cd");
		}
		
		var url = ROOT + "/pps/hdofc/ordermgmt/orderGoodsList.json";
		var cd = PAGE.FORM11.getItemValue("cd");
		mvno.cmn.ajax(url, {searchType : type, searchPopCd : cd}, function(results) {
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP1"} , PAGE.FORM11, "op1");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP2"} , PAGE.FORM11, "op2");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP3"} , PAGE.FORM11, "op3");
			
			if(results.result.data.rows.length == 0){
				PAGE.FORM11.setItemValue("cdNm", "");
				PAGE.FORM11.setItemValue("op1", "");
				PAGE.FORM11.setItemValue("op2", "");
				PAGE.FORM11.setItemValue("op3", "");
				PAGE.FORM11.setItemValue("saleAmt", "");
				
			}else{
				PAGE.FORM11.setItemValue("cdNm", results.result.data.rows[0].cdNm);
				PAGE.FORM11.setItemValue("op1", results.result.data.rows[0].op1);
				PAGE.FORM11.setItemValue("op2", results.result.data.rows[0].op2);
				PAGE.FORM11.setItemValue("op3", results.result.data.rows[0].op3);
				PAGE.FORM11.setItemValue("saleAmt", results.result.data.rows[0].saleAmt+"");
			}
			
		});
		
	}
	
	function setForm13Data(orderNo){		
		mvno.cmn.ajax(ROOT + "/pps/hdofc/ordermgmt/PpsOrderRefreshList.json", {orderNo : orderNo}, function(results) {
			if(results.result.data.rows[0].sendOrgFile == "" || results.result.data.rows[0].sendOrgFile == null){
				PAGE.FORM13.setItemLabel("sendOrgFileLabel", "");
			}else{
				PAGE.FORM13.setItemLabel("sendOrgFileLabel", results.result.data.rows[0].sendOrgFile);
			}
			PAGE.FORM13.showItem("btnFileDown");
			PAGE.FORM13.setItemValue("sendFileFlag", "Y");
			PAGE.FORM13.setItemValue("remarkOld", results.result.data.rows[0].remark);
			PAGE.FORM13.setItemValue("remark", "");
			PAGE.FORM13.setItemValue("reqRemarkOld", results.result.data.rows[0].reqRemark);
			PAGE.FORM13.setItemValue("reqRemark", "");
		});
	}
	
	function setForm14Data(orderNo){
		mvno.cmn.ajax(ROOT + "/pps/hdofc/ordermgmt/PpsOrderRefreshList.json", {orderNo : orderNo}, function(results) {
			PAGE.FORM14.setItemValue("remarkOld", results.result.data.rows[0].remark);
			PAGE.FORM14.setItemValue("remark", "");
			PAGE.FORM14.setItemValue("reqRemarkOld", results.result.data.rows[0].reqRemark);
			PAGE.FORM14.setItemValue("reqRemark", "");
		});
	}
	
	var DHX = {
		FORM1 : {
			title : "",
			items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "주문일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""}
							,{type : "newcolumn", offset:3}
							,{type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: "~"}
		                   	]}	 
		                    ,{type : "newcolumn", offset:3} 
							,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""}
							,{type : "newcolumn", offset:10} 
							,{type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20}
							,{type : "newcolumn", offset:3}
							,{type : "select",name : "searchAgentId",width:167}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "상태",name : "searchStatus",width:100, labelWidth:60}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							,{type : "select",label:"검색",name : "searchItem",width:100} 	
		                   	,{type : "newcolumn", offset:3}
		                   	,{type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: ":"}
		                 	 ]}	 
		                   	,{type : "newcolumn", offset:3}
	 			            ,{type : "input",name : "searchSValue", width:100, maxLength:20}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "품명",name : "searchType",width:101, labelWidth:60}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "배송방법",name : "searchDlvMethod",width:101, labelWidth:60}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "결제방법",name : "searchPayMethod",width:100, labelWidth:60}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select",label : "결제여부",name : "searchPayFlag",width:100, labelWidth:60}
						 
					 ]},// 1row
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search3"} 
				],
	
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearchPps":
						
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/ordermgmt/orderInfoList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
	
				}
	
			},
			 onValidateMore : function(data){
				 null;
			 },	
			 onKeyUp : function(inp, ev, name, value)
			{
				 if(ev.keyCode == 13)
				{
					switch(name)
					{
						case "searchAgentNm":
							var agentStr  = this.getItemValue("searchAgentNm");
							var selType ="popPin";
							//alert(agentStr);
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
									{ 
									  selectType : selType , 
								      searchAgentStr : agentStr
									} 
						      , PAGE.FORM1, "searchAgentId");
							var agentOption =  this.getOptions("searchAgentId");
					        
					        
					           if(agentOption.length==1)
						       {
					        	   //alert("ㄷ대리점이 존재하지 않습니다.");
					           }else if(agentOption.length==2)
						        {

							        var selValue = agentOption[1].value;

					        	   this.setItemValue("searchAgentId",selValue);
					           }
														      
						      
							break;
							
						default :
							if (this.getItemType(name) == 'input'){
								//대리점검색시 엔터클릭시 검색안되도록
								this.setItemFocus("btnSearchPps");	// input focus 가 남아있으면.. Enter 키가 계속먹음.
								this.callEvent("onButtonClick", ["btnSearchPps"]);		// 조회버튼 이름은 'btnSearch' 로!
							}
						
							break;
					
					}	
				}
			}
		}, // FORM1 End
		GRID1 : {

			css : {
				height : "510px"
			},
			title : "조회결과",
			header : "주문번호,주문일자,상태,대리점명,품명,상품코드,상품명,선택1,선택2,주문수량,주문단가,배정수량,배정금액,총청구액(VAT포함),배송방법,배정일자,파일등록여부,결제여부,결제방법,결제일자,결제관리자,인수확인일자",
			columnIds : "orderNo,orderDt,statusNm,agentNm,typeNm,cd,cdNm,op1Nm,op2Nm,reqOrderCnt,reqSaleAmt,sendCnt,sendAmt,sendTotAmt,dlvMethodNm,sendDt,sendFileFlag,payFlag,payMethodNm,payDt,payAdminNm,endDt",
			widths : "160,130,80,120,80,80,110,80,80,80,80,80,80,120,90,100,90,90,90,100,120,110", //총 1500
			colAlign : "center,center,center,center,center,center,center,center,center,right,right,right,right,right,center,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ron,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,int,int,int,int,int,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize :15,
			pagingStyle :2,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnReg :{label :"주문등록"},
					btnDel :{label :"주문취소"},
					btnMod :{label :"주문배정"},
					btnDtl :{label :"주문상세"}
				}
			},
			onButtonClick : function(btnId) {

				switch (btnId) {
					case "btnDnExcel":
						
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						
						var totCnt = PAGE.GRID1.getRowsNum();
						if(totCnt <= 0){
							mvno.ui.notify("출력건수가 없습니다.");
						   return;
						}
						//alert(totCnt);
						if(totCnt>5000)
						{
							mvno.ui.notify("엑셀출력건수가 5,000건이상이면 시간(수분~수십분)이 걸릴수 있습니다.\n 잠시 기다려주시기 바랍니다.");
						   
						}
						var url = "/pps/hdofc/ordermgmt/orderInfoListExcel.json";

						 var searchData =  PAGE.FORM1.getFormData(true);

							 console.log("searchData",searchData);
							
						  mvno.cmn.download(url+"?menuId=PPS1920003",true,{postData:searchData});
						  
						break;
					case "btnReg":
						
						mvno.ui.createForm("FORM11");
						PAGE.FORM11.clear();
						
						var selType ="popPin";
						mvno.cmn.ajax4lov(
								ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
								, {
									selectType : selType
									}
								 , PAGE.FORM11, "agentId");
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM11, "dlvMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM11, "type");
						
						var type = PAGE.FORM11.getItemValue("type");
						
						form11SetChangeValue("TYPE", type, "Y");
						
						PAGE.FORM11.disableItem("op1");
						PAGE.FORM11.disableItem("op2");
						PAGE.FORM11.disableItem("op3");
						PAGE.FORM11.disableItem("saleAmt");
						PAGE.FORM11.disableItem("totSaleAmt");
						
						PAGE.FORM11.clearChanged();
			            mvno.ui.popupWindowById("FORM11", "주문등록", 700, 500, {
			                onClose: function(win) {
			                	if (! PAGE.FORM11.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });
					
						break;
						
					case "btnDel":

						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						if(rowData.status == "09"){
							mvno.ui.alert("이미 주문취소상태 입니다.");
							return;
						}
						
						mvno.ui.createForm("FORM12");
						PAGE.FORM12.clear();
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0121" } , PAGE.FORM12, "payMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM12, "payFlag");
						
						PAGE.FORM12.setItemValue("payMethod", rowData.payMethod);
						PAGE.FORM12.setItemValue("payFlag", rowData.payFlag);
						PAGE.FORM12.setItemValue("agentId", rowData.agentId);
						PAGE.FORM12.setItemValue("orderNo", rowData.orderNo);
						
						PAGE.FORM12.disableItem("payMethod");
						PAGE.FORM12.disableItem("payFlag");
						
						if(rowData.payFlag == "Y" && rowData.payMethod == "DEP"){
							PAGE.FORM12.showItem("btnDepCan");
						}else{
							PAGE.FORM12.hideItem("btnDepCan");
						}
						
						PAGE.FORM12.clearChanged();
			            mvno.ui.popupWindowById("FORM12", "주문취소", 700, 300, {
			                onClose: function(win) {
			                	if (! PAGE.FORM12.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });
			            
						break;
						
					case "btnMod":
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						if(rowData.status != "01" && rowData.status != "05" && rowData.status != "10" ){
							mvno.ui.alert("주문등록/주문배정/주문확정상태가 아닙니다");
							return;
						}
						
						mvno.ui.createForm("FORM13");
						PAGE.FORM13.clear();
						
						var selType ="popPin";
						mvno.cmn.ajax4lov(
								ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
								, {
									selectType : selType
									}
								 , PAGE.FORM13, "agentId");
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM13, "dlvMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM13, "type");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0121" } , PAGE.FORM13, "payMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM13, "payFlag");
						
						var type = PAGE.FORM13.getItemValue("type");
						
						PAGE.FORM13.setItemValue("agentId", rowData.agentId);
						PAGE.FORM13.setItemValue("type", rowData.type);
						PAGE.FORM13.setItemValue("cd", rowData.cd);
						PAGE.FORM13.setItemValue("cdNm", rowData.cdNm);
						PAGE.FORM13.setItemValue("op1", rowData.op1Nm);
						PAGE.FORM13.setItemValue("op2", rowData.op2Nm);
						PAGE.FORM13.setItemValue("op3", rowData.op3Nm);
						PAGE.FORM13.setItemValue("reqOrderCnt", rowData.reqOrderCnt);
						PAGE.FORM13.setItemValue("saleAmt", rowData.reqSaleAmt);
						PAGE.FORM13.setItemValue("dlvAddr", rowData.dlvAddr);
						PAGE.FORM13.setItemValue("dlvMethod", rowData.dlvMethod);
						if(rowData.dlvReqDt == null){
							PAGE.FORM13.setItemValue("dlvReqDt", "");
						}else{
							PAGE.FORM13.setItemValue("dlvReqDt", new Date(rowData.dlvReqDt));
						}
						PAGE.FORM13.setItemValue("dlvAddr", rowData.dlvAddr);
						PAGE.FORM13.setItemValue("dlvName", rowData.dlvName);
						PAGE.FORM13.setItemValue("dlvPhone", rowData.dlvPhone);
						PAGE.FORM13.setItemValue("reqRemarkOld", rowData.reqRemark);
						PAGE.FORM13.setItemValue("remarkOld", rowData.remark);
						PAGE.FORM13.setItemValue("payMethod", rowData.payMethod);
						PAGE.FORM13.setItemValue("payFlag", rowData.payFlag);
						PAGE.FORM13.setItemValue("agentId", rowData.agentId);
						PAGE.FORM13.setItemValue("orderNo", rowData.orderNo);
						PAGE.FORM13.setItemValue("sendFileFlag", rowData.sendFileFlag);
						
						if(rowData.sendOrgFile == "" || rowData.sendOrgFile == null){
							PAGE.FORM13.setItemLabel("sendOrgFileLabel", "");
						}else{
							PAGE.FORM13.setItemLabel("sendOrgFileLabel", rowData.sendOrgFile);
						}
						
						if(rowData.sendCnt == "" || rowData.sendCnt == "0"){
							PAGE.FORM13.setItemValue("sendAmt", rowData.reqSaleAmt);
							PAGE.FORM13.setItemValue("sendCnt", rowData.reqOrderCnt);
							var sendTotAmt = PAGE.FORM13.getItemValue("sendAmt") * PAGE.FORM13.getItemValue("sendCnt") * 1.1;
							sendTotAmt = Math.floor(sendTotAmt);
							PAGE.FORM13.setItemValue("sendTotAmt", sendTotAmt);
						}else{
							PAGE.FORM13.setItemValue("sendAmt", rowData.sendAmt);
							PAGE.FORM13.setItemValue("sendCnt", rowData.sendCnt);
							PAGE.FORM13.setItemValue("sendTotAmt", rowData.sendTotAmt);
						}
						
						var sendTotAmtOld = PAGE.FORM13.getItemValue("sendAmt") * PAGE.FORM13.getItemValue("sendCnt");
						PAGE.FORM13.setItemValue("sendTotAmtOld", sendTotAmtOld);	
						
						if(rowData.payFlag == "Y" && rowData.payMethod == "DEP"){
							PAGE.FORM13.disableItem("payMethod");
							PAGE.FORM13.disableItem("payFlag");
							PAGE.FORM13.disableItem("sendAmt");
							PAGE.FORM13.disableItem("sendCnt");
							PAGE.FORM13.setItemLabel("btnDepRcg", "예치금결제취소");
							
						}else if(rowData.payFlag == "N"){
							PAGE.FORM13.setItemValue("payMethod", "BASIC");
							PAGE.FORM13.enableItem("payMethod");
							PAGE.FORM13.enableItem("payFlag");
							PAGE.FORM13.enableItem("sendAmt");
							PAGE.FORM13.enableItem("sendCnt");
							PAGE.FORM13.setItemLabel("btnDepRcg", "예치금결제");
						}else{
							PAGE.FORM13.enableItem("payMethod");
							PAGE.FORM13.enableItem("payFlag");
							PAGE.FORM13.enableItem("sendAmt");
							PAGE.FORM13.enableItem("sendCnt");
							PAGE.FORM13.setItemLabel("btnDepRcg", "예치금결제");
						}
						
						if(rowData.sendFileFlag == "Y"){
							PAGE.FORM13.showItem("btnFileDown");
						}else{
							PAGE.FORM13.hideItem("btnFileDown");
						}
						
						PAGE.FORM13.disableItem("agentId");
						PAGE.FORM13.disableItem("type");
						PAGE.FORM13.disableItem("cd");
						PAGE.FORM13.disableItem("cdNm");
						PAGE.FORM13.disableItem("op1");
						PAGE.FORM13.disableItem("op2");
						PAGE.FORM13.disableItem("op3");
						PAGE.FORM13.disableItem("reqOrderCnt");
						PAGE.FORM13.disableItem("dlvMethod");
						PAGE.FORM13.disableItem("dlvReqDt");
						PAGE.FORM13.disableItem("saleAmt");
						PAGE.FORM13.disableItem("dlvAddr");
						PAGE.FORM13.disableItem("dlvName");
						PAGE.FORM13.disableItem("dlvPhone");
						PAGE.FORM13.disableItem("sendTotAmt");
						PAGE.FORM13.disableItem("sendTotAmtOld");
						
						var uploader = PAGE.FORM13.getUploader("file_upload1");
		               
						if(old_url == ""){
							old_url = uploader._url;
						}
						
	                    var url = old_url + "?agentId=" + rowData.agentId;
		                uploader._url = url;   
		                uploader._swf_upolad_url = url;
		               
	                    uploader.revive();
	                    
	                    PAGE.FORM13.attachEvent("onBeforeFileAdd",function(realName, size, file){
		                      uploader._url = old_url; 
		                      return true; 
	                    });
						
						PAGE.FORM13.clearChanged();
			            mvno.ui.popupWindowById("FORM13", "주문배정", 700, 820, {
			                onClose: function(win) {
			                	if (! PAGE.FORM13.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                },
				            onClose2: function(win) {
	                	        uploader.reset();
	                	      
	                	    }
		                });
					
						break;
						
					case "btnDtl":

						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						mvno.ui.createForm("FORM14");
						PAGE.FORM14.clear();
						
						var selType ="popPin";
						mvno.cmn.ajax4lov(
								ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
								, {
									selectType : selType
									}
								 , PAGE.FORM14, "agentId");
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM14, "dlvMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM14, "type");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0121" } , PAGE.FORM14, "payMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM14, "payFlag");
						
						var type = PAGE.FORM14.getItemValue("type");
						
						PAGE.FORM14.setItemValue("agentId", rowData.agentId);
						PAGE.FORM14.setItemValue("type", rowData.type);
						PAGE.FORM14.setItemValue("cd", rowData.cd);
						PAGE.FORM14.setItemValue("cdNm", rowData.cdNm);
						PAGE.FORM14.setItemValue("op1", rowData.op1Nm);
						PAGE.FORM14.setItemValue("op2", rowData.op2Nm);
						PAGE.FORM14.setItemValue("op3", rowData.op3Nm);
						PAGE.FORM14.setItemValue("dlvAddr", rowData.dlvAddr);
						PAGE.FORM14.setItemValue("dlvMethod", rowData.dlvMethod);
						if(rowData.dlvReqDt == null){
							PAGE.FORM14.setItemValue("dlvReqDt", "");
						}else{
							PAGE.FORM14.setItemValue("dlvReqDt", new Date(rowData.dlvReqDt));
						}
						PAGE.FORM14.setItemValue("dlvAddr", rowData.dlvAddr);
						PAGE.FORM14.setItemValue("dlvName", rowData.dlvName);
						PAGE.FORM14.setItemValue("dlvPhone", rowData.dlvPhone);
						PAGE.FORM14.setItemValue("dlvNo", rowData.dlvNo);
						PAGE.FORM14.setItemValue("reqRemarkOld", rowData.reqRemark);
						PAGE.FORM14.setItemValue("remarkOld", rowData.remark);
						PAGE.FORM14.setItemValue("payMethod", rowData.payMethod);
						PAGE.FORM14.setItemValue("payFlag", rowData.payFlag);
						PAGE.FORM14.setItemValue("agentId", rowData.agentId);
						PAGE.FORM14.setItemValue("orderNo", rowData.orderNo);
						PAGE.FORM14.setItemValue("sendFileFlag", rowData.sendFileFlag);
						PAGE.FORM14.setItemValue("reqOrderCnt", rowData.reqOrderCnt);
						PAGE.FORM14.setItemValue("reqSaleAmt", rowData.reqSaleAmt);
						
						if(rowData.sendOrgFile == "" || rowData.sendOrgFile == null){
							PAGE.FORM14.setItemLabel("sendOrgFileLabel", "");
						}else{
							PAGE.FORM14.setItemLabel("sendOrgFileLabel", rowData.sendOrgFile);
						}
						
						if(rowData.sendCnt == "" || rowData.sendCnt == "0"){
							PAGE.FORM14.setItemValue("sendAmt", rowData.reqSaleAmt);
							PAGE.FORM14.setItemValue("sendCnt", rowData.reqOrderCnt);
							var sendTotAmt = PAGE.FORM14.getItemValue("sendAmt") * PAGE.FORM14.getItemValue("sendCnt") * 1.1;
							sendTotAmt = Math.floor(sendTotAmt);
							PAGE.FORM14.setItemValue("sendTotAmt", sendTotAmt);	
						}else{
							PAGE.FORM14.setItemValue("sendAmt", rowData.sendAmt);
							PAGE.FORM14.setItemValue("sendCnt", rowData.sendCnt);
							PAGE.FORM14.setItemValue("sendTotAmt", rowData.sendTotAmt);
						}
						
						var sendTotAmtOld = PAGE.FORM14.getItemValue("sendAmt") * PAGE.FORM14.getItemValue("sendCnt");
						PAGE.FORM14.setItemValue("sendTotAmtOld", sendTotAmtOld);
						
						if(rowData.sendFileFlag == "Y"){
							PAGE.FORM14.showItem("btnFileDown");
						}else{
							PAGE.FORM14.hideItem("btnFileDown");
						}
						
						PAGE.FORM14.disableItem("payMethod");
						PAGE.FORM14.disableItem("payFlag");
						PAGE.FORM14.disableItem("sendAmt");
						PAGE.FORM14.disableItem("sendCnt");
						PAGE.FORM14.disableItem("agentId");
						PAGE.FORM14.disableItem("type");
						PAGE.FORM14.disableItem("cd");
						PAGE.FORM14.disableItem("cdNm");
						PAGE.FORM14.disableItem("op1");
						PAGE.FORM14.disableItem("op2");
						PAGE.FORM14.disableItem("op3");
						PAGE.FORM14.disableItem("reqOrderCnt");
						PAGE.FORM14.disableItem("saleAmt");
						PAGE.FORM14.disableItem("sendTotAmt");
						PAGE.FORM14.disableItem("sendTotAmtOld");
						PAGE.FORM14.disableItem("dlvReqDt");
						PAGE.FORM14.disableItem("dlvMethod");
						PAGE.FORM14.disableItem("dlvAddr");
						PAGE.FORM14.disableItem("dlvName");
						PAGE.FORM14.disableItem("dlvPhone");
						PAGE.FORM14.disableItem("dlvNo");
						PAGE.FORM14.disableItem("reqOrderCnt");
						PAGE.FORM14.disableItem("reqSaleAmt");
						
						PAGE.FORM14.clearChanged();
			            mvno.ui.popupWindowById("FORM14", "주문상세", 700, 820, {
			                onClose: function(win) {
			                	if (! PAGE.FORM14.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });
					
						break;
				}
			}
		}, // GRID1 End
		FORM11: {
			title : "주문등록"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label:"대리점",name : "searchAgentStr",width:140, labelWidth:100}
	  	                 ,{type : "newcolumn", offset:3}
	  	                 ,{type:"block", list:[
	  							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	  							,{type: "label", label: ":"}
	  	                 ]}
	  	                 ,{type : "newcolumn", offset:3},
	  	                 ,{type : "select",name : "agentId", width:200}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "품목", name:"type", width:140, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "상품코드", name:"cd", width:140, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:12}
						 ,{type:"input", label:"", name: "cdNm", width:200, readonly:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택1", name:"op1", width:140, labelWidth:100, required:false}
					 ]},// 4row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택2", name:"op2", width:140, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택3", name:"op3", width:140, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "판매단가",name : "saleAmt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", required:true, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "수량",name : "reqOrderCnt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, required:true, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "금액(VAT미포함)",name : "totSaleAmt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "배송방법", name:"dlvMethod", width:140, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "dlvReqDt",label : "희망배송일자", value : "",enableTime : false, calendarPosition : "right",inputWidth : 140, labelWidth:110}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "배송주소",name : "dlvAddr",width:455 ,labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "수취인명",name : "dlvName", width:140, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "연락처",name : "dlvPhone", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "주문요청사항",name : "reqRemark",width:455 ,labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "관리자메모",name : "remark",width:455 ,labelWidth:100}
					 ]}
				]
			,buttons: {
			   	center : {
		    		btnInoutReg : { label : "등록" },
		    		btnPopClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
				if(ev.keyCode == 13)
				{
					switch(name)
					{
						case "searchAgentStr":
							var agentStr  = this.getItemValue("searchAgentStr");
							var selType ="popPin";
							//alert(agentStr);
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
									{ 
									  selectType : selType , 
								      searchAgentStr : agentStr
									} 
						      , PAGE.FORM11, "agentId");
							  var agentOption =  this.getOptions("agentId");
						        
						        
					           if(agentOption.length==1)
						       {
					        	   //alert("ㄷ대리점이 존재하지 않습니다.");
					           }else if(agentOption.length==2)
						        {
	
							        var selValue = agentOption[1].value;
	
					        	   this.setItemValue("agentId",selValue);
					           }	
					           
					        var agentId = this.getItemValue("agentId");
					        setAgentDlv(agentId);
							      
							break;

					}	
				}
				
			}
			,onInputChange : function(name,val,inp) {
				
				switch(name)
				{
					case "reqOrderCnt":
						var saleAmt  = this.getItemValue("saleAmt");
						
						this.setItemValue("totSaleAmt", val * saleAmt);
						break;

				}
			}
			,onChange : function (name, value)
			{
				switch (name) {
					case "type":	
						form11SetChangeValue("TYPE", value, "Y");
						break;
					case "cd":	
						var type = PAGE.FORM11.getItemValue("type");
						form11SetChangeValue("CD", type, "Y");
						break;
					case "agentId":	
						var agentId = PAGE.FORM11.getItemValue("agentId");
						setAgentDlv(agentId);
						break;
				}
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnInoutReg":
							var data = PAGE.FORM11.getFormData(true);
							
							data.opCode = "ORDER_ADD";
							
							if(data.agentId == ""){
								mvno.ui.alert("대리점은 필수입니다");
								return;
							}
							
							if(data.cd == "" || data.cd == "-1"){
								mvno.ui.alert("주문 가능한 상품이 없습니다");
								return;
							}
						
							if(data.reqOrderCnt == ""){
								mvno.ui.alert("수량은 필수입니다.");
								return;
							}
							
							
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							mvno.cmn.ajax(url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									//mvno.ui.notify("NCMN0015");
									PAGE.FORM11.clear();
									PAGE.GRID1.refresh();
								}
								
							});
							
							break;
							
						case "btnPopClose":	
							mvno.ui.closeWindowById("FORM11", true);
			                break;
						}
			}
			
		},
		FORM12: {
			title : "주문취소"
			,items : [ 	
					 {type: 'hidden', name: 'agentId', value: ''}
					 ,{type: 'hidden', name: 'orderNo', value: ''}
					 ,{type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "결제방법", name:"payMethod", width:140, labelWidth:100, required : false}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "결제상태", name:"payFlag", width:140, labelWidth:100, required : true}
						 ,{type:"newcolumn", offset:3}
						 ,{type:"button", value:"결제취소", name:"btnDepCan"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "취소사유",name : "remark",width:455 ,labelWidth:100, required : true}
					 ]}
				]
			,buttons: {
			   	center : {
		    		btnInfoCan : { label : "주문취소" },
		    		btnPopClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
				
			}
			,onInputChange : function(name,val,inp) {
				
			}
			,onChange : function (name, value)
			{
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnDepCan":
							var data = PAGE.FORM12.getFormData(true);
							
							data.opCode = "DEP_CAN";
							
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "결제를 취소하시겠습니까?";
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									//mvno.ui.closeWindowById(form, true);  
									//PAGE.FORM12.clear();
									PAGE.GRID1.refresh();
									PAGE.FORM12.setItemValue("payMethod", "");
									PAGE.FORM12.setItemValue("payFlag", "N");
									PAGE.FORM12.hideItem("btnDepCan");
								}
								
							});
							
							break;
							
						case "btnInfoCan":
							var data = PAGE.FORM12.getFormData(true);
							
							data.opCode = "ORDER_CAN";
							
							if(data.remark == ""){
								mvno.ui.alert("취소사유는 필수입니다.");
								return;
							}
							
							
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "주문취소 하시겠습니까?";
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									//mvno.ui.notify("NCMN0015");
									PAGE.FORM12.clear();
									PAGE.GRID1.refresh();
								}
								
							});
							
							break;
							
						case "btnPopClose":	
							mvno.ui.closeWindowById("FORM12", true);
			                break;
						}
			}
			
		},
		FORM13: {
			title : "주문배정"
			,items : [ 
					 {type: 'hidden', name: 'orderNo', value: ''}
					 ,{type: 'hidden', name: 'sendFileFlag', value: ''}
					 ,{type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
	  	                 ,{type : "select",name : "agentId",label:"대리점", width:300, labelWidth:100}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "품목", name:"type", width:140, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"상품코드", name: "cd", width:140, labelWidth:100, readonly:true}
						 ,{type:"newcolumn", offset:12}
						 ,{type:"input", label:"", name: "cdNm", width:140, readonly:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"선택1", name: "op1", width:140, labelWidth:100, readonly:true}
					 ]},// 4row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"선택2", name: "op2", width:140, labelWidth:100, readonly:true}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"선택3", name: "op3", width:140, labelWidth:100, readonly:true}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "판매단가",name : "saleAmt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", required:true, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "주문수량",name : "reqOrderCnt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, required:true, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "배송방법", name:"dlvMethod", width:140, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "dlvReqDt",label : "희망배송일자", value : "",enableTime : false, calendarPosition : "right",inputWidth : 140, labelWidth:110}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "배송주소",name : "dlvAddr",width:455 ,labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "수취인명",name : "dlvName", width:140, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "연락처",name : "dlvPhone", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "주문요청사항",name : "reqRemarkOld",width:455, rows:5 ,labelWidth:100, readonly:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "추가",name : "reqRemark",width:400 ,labelWidth:100}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"저장", name:"btnReqRemarkSave"}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "배정수량",name : "sendCnt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, required:true, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "배정단가",name : "sendAmt", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "배정금액",name : "sendTotAmtOld", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "총청구액(VAT포함)",name : "sendTotAmt", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "select", label : "결제방법", name:"payMethod", width:140, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"예치금결제", name:"btnDepRcg"}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "결제상태", name:"payFlag", width:140, labelWidth:100, required:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"block", blockOffset: 0, list: [	
         					   	{type: "upload", label:"첨부파일"
         					   	,name: "file_upload1"
         					   	,width:280
         					   	,inputWidth: 330 
         						,url: "/pps/ordermgmt/ppsFileEncUpLoad.do" 
         					    ,userdata:{limitSize:10000} 
         					   //	,swfPath: "uploader.swf"
         					   	,autoStart: true 
         					   // ,swfUrl: "/lgs/prdtsrlmgmt/fileUpUsingExcel.do"
         						}
         					]}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"저장", name:"btnFileSave"}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"다운", name:"btnFileDown"}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 ,{type:"label", label:"파일명", name:"", labelWidth:100}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"label", label:"", name:"sendOrgFileLabel", labelWidth:400}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "관리자메모",name : "remarkOld",width:455, rows:5 ,labelWidth:100, readonly:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "추가",name : "remark",width:400 ,labelWidth:100}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"저장", name:"btnRemarkSave"}
					 ]}
				]
			,buttons: {
			   	center : {
			   		btnInoutMod : { label : "주문배정" },
		    		btnPopClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
				
			}
			,onInputChange : function(name,val,inp) {
				
				switch(name)
				{
					case "sendCnt":
						var sendAmt  = this.getItemValue("sendAmt");
						
						this.setItemValue("sendTotAmtOld", val * sendAmt);
						
						var sendTotAmt = val * sendAmt * 1.1;
						sendTotAmt = Math.floor(sendTotAmt);
						this.setItemValue("sendTotAmt", sendTotAmt);
						break;
						
					case "sendAmt":
						var sendCnt  = this.getItemValue("sendCnt");
						
						this.setItemValue("sendTotAmtOld", val * sendCnt);
						
						var sendTotAmt = val * sendCnt * 1.1;
						sendTotAmt = Math.floor(sendTotAmt);
						this.setItemValue("sendTotAmt", sendTotAmt);
						break;

				}
			}
			,onChange : function (name, value)
			{
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnInoutMod":
							var data = PAGE.FORM13.getFormData(true);
							
							data.opCode = "ORDER_SEND";
							
							if(data.sendCnt == "" || data.sendCnt == "0"){
								mvno.ui.alert("수량을 입력해주세요");
								return;
							}
							
							if(data.sendAmt == ""){
								mvno.ui.alert("배정단가를 입력해주세요");
								return;
							}
							
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							mvno.cmn.ajax(url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									//mvno.ui.notify("NCMN0015");
									PAGE.FORM13.clear();
									PAGE.GRID1.refresh();
								}
								
							});
							
							break;
							
						case "btnFileSave":
							var data = PAGE.FORM13.getFormData(true);
							data.opCode = "FILE_SAVE";
							
							if(data.file_upload1_count == 0){
								mvno.ui.alert("파일을 등록해 주세요");
								return;
							}

							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "첨부파일을 등록 하시겠습니까?";
							var orderNo = data.orderNo;
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								//console.log(results);
								if(retCode == "0000") {
									PAGE.GRID1.refresh();
									setForm13Data(orderNo);
								}
								
								PAGE.FORM13.setItemValue("file_upload1");
								
							});
							
							break;
							
						case "btnRemarkSave":
							
							var data = PAGE.FORM13.getFormData(true);
							
							data.opCode = "ORDER_RPY";
							
							if(data.remark == ""){
								mvno.ui.alert("관리자메모를 입력해주세요");
								return;
							}
							
							data.reqRemark = "";

							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "관리자메모를 등록 하시겠습니까?";
							var orderNo = data.orderNo;
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									PAGE.GRID1.refresh();
									setForm13Data(orderNo);
								}
								
							});
							
							break;
							
						case "btnReqRemarkSave":
							var data = PAGE.FORM13.getFormData(true);
							
							data.opCode = "ORDER_RPY";
							
							if(data.reqRemark == ""){
								mvno.ui.alert("주문요청사항을 입력해주세요");
								return;
							}
							
							data.remark = "";

							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "주문요청사항을 등록 하시겠습니까?";
							var orderNo = data.orderNo;
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									PAGE.GRID1.refresh();
									setForm13Data(orderNo);
								}
								
							});
							
							break;
							
						case "btnDepRcg":
							var data = PAGE.FORM13.getFormData(true);
							var rowData = PAGE.GRID1.getSelectedRowData();
							var msg = "";
							
							if(PAGE.FORM13.getItemValue("payFlag") == "Y" && PAGE.FORM13.getItemValue("payMethod") == "DEP"){
								data.opCode = "DEP_CAN";
								msg = "예치금 결제를 취소하시겠습니까?";
							}else{	
								if(PAGE.FORM13.getItemValue("payMethod") != "DEP"){
									mvno.ui.alert("결제방법을 예치금으로 선택해주세요");
									return;
								}
								
								data.opCode = "DEP_RCG";
								msg = "해당 대리점의 예치금에서 " + data.sendTotAmt + "원을 차감 하시겠습니까?";
							}
							
							if(data.sendTotAmt == "" || data.sendTotAmt == "0"){
								mvno.ui.alert("총청구액(VAT포함)이 없습니다");
								return;
							}
						
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									//mvno.ui.closeWindowById(form, true);  
									//mvno.ui.notify("NCMN0015");
									//PAGE.FORM13.clear();
									PAGE.GRID1.refresh();
									
									if(data.opCode == "DEP_RCG"){
										PAGE.FORM13.setItemValue("payMethod", "DEP");
										PAGE.FORM13.setItemValue("payFlag", "Y");
										PAGE.FORM13.setItemLabel("btnDepRcg","예치금결제취소");
										PAGE.FORM13.disableItem("payMethod");
										PAGE.FORM13.disableItem("payFlag");
										PAGE.FORM13.disableItem("sendCnt");
										PAGE.FORM13.disableItem("sendAmt");
									}else{
										PAGE.FORM13.setItemValue("payMethod", "BASIC");
										PAGE.FORM13.setItemValue("payFlag", "N");
										PAGE.FORM13.setItemLabel("btnDepRcg","예치금결제");
										PAGE.FORM13.enableItem("payMethod");
										PAGE.FORM13.enableItem("payFlag");
										PAGE.FORM13.enableItem("sendCnt");
										PAGE.FORM13.enableItem("sendAmt");
									}
									
								}
								
							});
							
							break;
							
						case "btnPopClose":	
							mvno.ui.closeWindowById("FORM13", true);
			                break;
			                
						case "btnFileDown":	
							var data = PAGE.FORM13.getFormData(true);
							
							if(data.sendFileFlag == "Y") {
								var url = "/pps/hdofc/ordermgmt/orderFileDown.json";
								mvno.cmn.download(url+"?orderNo="+data.orderNo+"&agentId=BONSA");
							}else{
								mvno.ui.alert("등록된 파일이 없습니다");
							}
							
			                break;
			                
						}
			}
			
		},
		FORM14: {
			title : "주문상세"
			,items : [ 	
					 {type: 'hidden', name: 'orderNo', value: ''}
					 ,{type: 'hidden', name: 'sendFileFlag', value: ''}
					 ,{type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
	  	                 ,{type : "select",name : "agentId",label:"대리점", width:300, labelWidth:100}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "품목", name:"type", width:140, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"상품코드", name: "cd", width:140, labelWidth:100, readonly:true}
						 ,{type:"newcolumn", offset:12}
						 ,{type:"input", label:"", name: "cdNm", width:200, readonly:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"선택1", name: "op1", width:140, labelWidth:100, readonly:true}
					 ]},// 4row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"선택2", name: "op2", width:140, labelWidth:100, readonly:true}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"선택3", name: "op3", width:140, labelWidth:100, readonly:true}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "배송방법", name:"dlvMethod", width:140, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "dlvReqDt",label : "희망배송일자", value : "",enableTime : false, calendarPosition : "right",inputWidth : 140, labelWidth:110}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "배송주소",name : "dlvAddr",width:455 ,labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "수취인명",name : "dlvName", width:140, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "연락처",name : "dlvPhone", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "송장번호",name : "dlvNo", width:140, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "주문요청사항",name : "reqRemarkOld",width:455, rows:5 ,labelWidth:100, readonly:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "추가",name : "reqRemark",width:400 ,labelWidth:100}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"저장", name:"btnReqRemarkSave"}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "요청수량",name : "reqOrderCnt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, required:true, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "판매단가",name : "reqSaleAmt", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "배정수량",name : "sendCnt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, required:true, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "배정단가",name : "sendAmt", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "배정금액",name : "sendTotAmtOld", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "총청구액(VAT포함)",name : "sendTotAmt", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "select", label : "결제방법", name:"payMethod", width:140, labelWidth:100, required:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "결제상태", name:"payFlag", width:140, labelWidth:100, required:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "label", label : "첨부파일", name:"sendFileLabel", width:140, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:0}
						 ,{type:"button", value:"다운", name:"btnFileDown"}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 ,{type:"label", label:"파일명", name:"", labelWidth:100}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"label", label:"", name:"sendOrgFileLabel", labelWidth:400}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "관리자메모",name : "remarkOld",width:455, rows:5 ,labelWidth:100, readonly:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "추가",name : "remark",width:400 ,labelWidth:100}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"저장", name:"btnRemarkSave"}
					 ]}
				]
			,buttons: {
			   	center : {
		    		btnInfoReg : { label : "메모추가" },
		    		btnPopClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
				
			}
			,onInputChange : function(name,val,inp) {
				
			}
			,onChange : function (name, value)
			{
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnInfoReg":
							var data = PAGE.FORM14.getFormData(true);
							
							data.opCode = "ORDER_RPY";
							
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "요청사항 및 관리자메모를 추가하시겠습니까?";
							
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									//mvno.ui.notify("NCMN0015");
									PAGE.FORM14.clear();
									PAGE.GRID1.refresh();
								}
							});
							
							break;
						
						case "btnFileDown":	
							var data = PAGE.FORM14.getFormData(true);
							
							if(data.sendFileFlag == "Y") {
								var url = "/pps/hdofc/ordermgmt/orderFileDown.json";
								mvno.cmn.download(url+"?orderNo="+data.orderNo+"&agentId=BONSA");
							}else{
								mvno.ui.alert("등록된 파일이 없습니다");
							}
							
			                break;
			                
						case "btnRemarkSave":
							
							var data = PAGE.FORM14.getFormData(true);
							
							data.opCode = "ORDER_RPY";
							
							if(data.remark == ""){
								mvno.ui.alert("관리자메모를 입력해주세요");
								return;
							}
							
							data.reqRemark = "";

							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "관리자메모를 등록 하시겠습니까?";
							var orderNo = data.orderNo;
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									setForm14Data(orderNo);
									PAGE.GRID1.refresh();
								}
								
							});
							
							break;
							
						case "btnReqRemarkSave":
							var data = PAGE.FORM14.getFormData(true);
							
							data.opCode = "ORDER_RPY";
							
							if(data.reqRemark == ""){
								mvno.ui.alert("주문요청사항을 입력해주세요");
								return;
							}
							
							data.remark = "";

							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "주문요청사항을 등록 하시겠습니까?";
							var orderNo = data.orderNo;
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									setForm14Data(orderNo);
									PAGE.GRID1.refresh();
								}
								
							});
							
							break;
							
						case "btnPopClose":	
							mvno.ui.closeWindowById("FORM14", true);
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
				mvno.ui.createGrid("GRID1");
				
				setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "endDt");
				
				var selType ="popPin";
				mvno.cmn.ajax4lov(
						ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
						, {
							selectType : selType
							}
						 , PAGE.FORM1, "searchAgentId");
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM1, "searchType");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0119" } , PAGE.FORM1, "searchStatus");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0122" } , PAGE.FORM1, "searchItem");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM1, "searchDlvMethod");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0121" } , PAGE.FORM1, "searchPayMethod");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM1, "searchPayFlag");
				
				PAGE.FORM1.setItemValue("searchStatus", "ORDER");
				
			}

		};
		
		
	</script>