<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
  * @Class Name : ptnr_point_mgmt.jsp
  * @Description :  제휴포인트관리
  * @Modification Information
  *
  *   수정일		수정자		수정내용
  *  ------------------------------------------
  *  2018.06.04		권오승		최초 생성
  *
  */
%>
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="GRID3"></div>
<div id="FORM2"></div>
<div id="GRID2"></div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date();
	var thisMonth = today.getFullYear()+""+fnMonth(today.getMonth());
	var dongbuYn = false;
	var DHX = {
			//------------------------------------------------------------
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					, {type:"block", list:[
						{type:"calendar", label:"지급년월", name:"billYm", value:thisMonth, width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10, required:true},
						{type: "newcolumn"},
						{type: "select", label: "제휴사", name: "partnerId", width:110, offsetLeft:30, value:"", labelWidth:60 , required:true},
						{type: "newcolumn"},
						{type: "input", label: "요금제명", name: "rateNm", width:100, offsetLeft:20, value:"", labelWidth:60},
						{type: "newcolumn"},
						{type: "select", label: "완납여부", name: "fullPayYn", width:80, offsetLeft:20, value:"", labelWidth:60}
						
					]},
					{type:"block", list:[
						{type: "select",width : 80,offsetLeft : 10,label : "검색구분",name : "searchCd"},
						{type: "newcolumn"},
						{type: "input",width : 200,name : "searchVal"},
						{type: "newcolumn"},
						{type: "select", label: "지급대상", name: "sendFlag", width:100, offsetLeft:20, value:"", labelWidth:60},
						{type: "newcolumn"},
						{type: "select", label: "지급결과", name: "payResult", width:80, offsetLeft:20, value:"", labelWidth:60}
					]},
					//조회 버튼
					, {type:"newcolumn"}
					, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}
				]
	
				,onChange: function (name, value) {
					var form = this;
					
					//제휴사가 동부일때 다른 그리드 display
					if(name == "partnerId") {
						PAGE.GRID1.clearAll();
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						PAGE.FORM2.clear();
						
						if(value == "dongbu") {
							$("#GRID1").hide();
							$("#GRID3").show();
							PAGE.FORM1.setItemValue("payResult", "");
							PAGE.FORM1.disableItem("payResult");
						}else {
							$("#GRID3").hide();
							$("#GRID1").show();
							PAGE.FORM1.enableItem("payResult");
						}
						
						PAGE.FORM1.setItemValue("sendFlag" , "");
					}
					// 검색어 초기화
					if(name == "searchCd"){
						//$("#searchVal").val("");
						PAGE.FORM1.setItemValue("searchVal", "");
					}
					
				}
				, onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							if (! this.validate())  return;
							
							//PAGE.FORM2.setItemValue("reqPoint", "0");
							//PAGE.FORM2.setItemValue("payPoint", "0");
							PAGE.FORM2.clear();
							PAGE.GRID2.clearAll();
							
							if(PAGE.FORM1.getItemValue("partnerId" , true) == "dongbu"){
								PAGE.GRID3.clearAll();
								PAGE.GRID3.list(ROOT + "/ptnr/ptnrMgmt/getPointMgmtList.json", form, {callback: function(resultData) {
									var data = resultData.result.data;
									if(data.total > 0){
										PAGE.FORM2.setItemValue("reqPoint", data.rows[0].reqPointSum);
										PAGE.FORM2.setItemValue("payPoint", data.rows[0].payPointSum);
									}
								}});
							}else{
								PAGE.GRID1.clearAll();
								PAGE.GRID1.list(ROOT + "/ptnr/ptnrMgmt/getPointMgmtList.json", form, {callback: function(resultData) {
									var data = resultData.result.data;
									if(data.total > 0){
										PAGE.FORM2.setItemValue("reqPoint", data.rows[0].reqPointSum);
										PAGE.FORM2.setItemValue("payPoint", data.rows[0].payPointSum);
									}
								}});
							}
							
							break;
					}
				}
				,onValidateMore : function (data) {
					//검색어 구분에 값이 있는데 검색어가 없을 경우 return
					if(data.searchCd != ""  && data.searchVal.trim() == ""){
						this.pushError("searchVal", "검색어", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
					}
				}
			},

			//--------------------------------------------------
			// 제휴사 정산
			//--------------------------------------------------
			GRID1 : {
				css : {
					height : "300px"
					, width : "100%"
				}
				, title : "제휴포인트관리"
				, header : "계약번호,#cspan,고객정보,#cspan,기초정보,#cspan,#cspan,요청내용,#cspan,#cspan,완납여부,지급결과내역,#cspan,#cspan"
				, header2 : "가입,서비스,ID,고객명,사용년월,지급년월,요금제/부가서비스/프로모션,지급유형,사용일수,요청포인트,#rspan,결과,지급포인트"
				, columnIds : "contractNum,svcCntrNo,custId,custNm,usgYm,payYm,rateNm,pointType,usgDtCnt,reqPoint,fullPayYn,payResult,payPoint"
				, widths : "80,80,80,80,80,80,200,80,80,90,80,80,100"
				, colAlign : "center,center,center,center,center,center,left,center,right,right,center,center,right"
				, colTypes : "ro,ro,ro,ro,roXd,roXd,ro,ro,ro,ron,ro,ro,ron,ro"
				, colSorting : "str,str,str,str,str,str,str,str,str,int,str,str,int"
				, paging : true
				, pageSize:20
				, buttons : {
					hright : {
						btnExcel : { label : "엑셀다운로드" }
					}
				}
				, onButtonClick : function(btnId , selectedData){
					var form = this;
					switch(btnId){
						case "btnExcel" :
							if(PAGE.GRID1.getRowsNum() == 0){
								mvno.ui.alert("데이터가 존재하지 않습니다");
								return;
							}
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/ptnr/ptnrMgmt/getPtnrPointListExcel.json?menuId=MSP6000114", true, {postData:searchData}); 
							break;
					}
				}
				, onRowSelect : function(rowId) {
					var rowData = this.getSelectedRowData();
					PAGE.GRID2.clearAll();
					PAGE.GRID2.list(ROOT + "/ptnr/ptnrMgmt/getPtnrRateUsgHstInfo.json", rowData);
				}
			},
			//--------------------------------------------------
			// 제휴요금사용이력 GRID
			//--------------------------------------------------
			GRID2 : {
				css : {
					height : "140px"
					, width : "100%"
				},
				title : "제휴요금사용이력",
				header : "청구년월,사용년월,제휴사,제휴요금제,가입계약번호,서비스계약번호,계약상태,사용일수,사용시작일자,사용종료일자", //10
				columnIds : "billYm,usgYm,partnerNm,rateNm,contractNum,svcCntrNo,subStatus,usgDtCnt,strtDt,endDt",
				widths : "100,100,100,200,100,100,80,80,100,100",
				colAlign : "center,center,center,left,center,center,center,right,center,center",
				colTypes : "roXd,roXd,ro,ro,ro,ro,ro,ro,roXd,roXd",
				colSorting : "str,str,str,str,str,str,str,str,str,str"
			},
			//--------------------------------------------------
			// 합계금액
			//--------------------------------------------------
			FORM2 : {
				title : "포인트합계",
				items : [
					{type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0}
					, {type:"block", blockOffset:0, list: [
						{type:"input", label:"요청포인트", name: "reqPoint", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:5, labelWidth: 80, readOnly:true}
						,{type:"newcolumn"}
						,{type:"input", label:"지급포인트", name: "payPoint", numberFormat:["0,000"], userdata: {align: "right"}, width:70, offsetLeft:25, labelWidth: 80, readOnly:true}
					]}
				]
			},
			//--------------------------------------------------
			// 동부화재 정산
			//--------------------------------------------------
			GRID3 : {
				css : {
					height : "300px"
					, width : "100%"
				}
				, title : "제휴포인트관리"
				, header : "계약번호,#cspan,기초정보,#cspan,#cspan,고객정보,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,정산내역,#cspan,완납여부,지급대상,최종보험료정산"  //16
				, header2 : "가입,서비스,사용년월,청구년월,지급년월,고객명,계약상태,연령(만),최초개통일,가입일,요금제,보험명,사용일수,보험료계산,#rspan,#rspan,#rspan"
				, columnIds : "contractNum,svcCntrNo,usgYm,billYm,payYm,custNm,subStatus,custAge,openDt,joinDt,rateNm,insrNm,usgDtCnt,reqPoint,fullPayYn,ptnrTrgt,calPoint"
				, widths : "100,100,80,80,80,80,80,80,80,90,200,120,80,100,80,80,100"
				, colAlign : "center,center,center,center,center,center,center,right,center,center,left,left,right,right,center,center,right"
				, colTypes : "ro,ro,roXd,roXd,roXd,ro,ro,ro,roXd,roXd,ro,ro,ro,ron,ro,ro,ron,ro"
				, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,int,str,str,int"
				, paging : true
				, pageSize:20
				, buttons : {
					hright : {
						btnExcel : { label : "엑셀다운로드" }
					}
				}
				, onButtonClick : function(btnId){
					var form = this;
					switch(btnId){
						case "btnExcel" :
							if(PAGE.GRID3.getRowsNum() == 0){
								mvno.ui.alert("데이터가 존재하지 않습니다");
								return;
							}
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/ptnr/ptnrMgmt/getPtnrPointListExcel.json?menuId=MSP6000114", true, {postData:searchData});  
							break;
					}
				}
				, onRowSelect : function(rowId) {
					var rowData = this.getSelectedRowData();
					PAGE.GRID2.clearAll();
					PAGE.GRID2.list(ROOT + "/ptnr/ptnrMgmt/getPtnrRateUsgHstInfo.json", rowData);
				}
			}
	};
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : "${breadCrumb.breadCrumb}", 
		buttonAuth:${buttonAuth.jsonString},
		
		onOpen : function() {
			 
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID3");
			mvno.ui.createForm("FORM2");
			mvno.ui.createGrid("GRID2");

			$("#GRID3").hide();
			
			//조회조건 제휴사  
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json" , {grpId:"CMN0073" ,orderBy:"etc1"} , PAGE.FORM1, "partnerId");
			
			//조회조건 검색조건 세팅
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");
			
			//완납여부
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json" , {grpId:"PTNR10002"}, PAGE.FORM1, "fullPayYn");
			
			// 제휴포인트지급대상
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json" , {grpId:"CMN0065", orderBy:"etc6"}, PAGE.FORM1, "sendFlag");
			
			// 제휴포인트지급결과
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json" , {grpId:"CMN0066", orderBy:"etc6"}, PAGE.FORM1, "payResult");
		}
		
	};
	
	
	function fnMonth(mon) {
		var month;
		mon = Number(mon);
		mon++;
		
		if(Number(mon) < 10) {
			month = "0"+mon;
		} else {
			month = mon;
		}
		
		return month;
	}
	
</script>