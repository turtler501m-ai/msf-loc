<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div class="row">
	<div class="c3" >
		<div id="FORM1" class="section-search"></div>
		<div>
			<div id="GRID1" class="section-half"></div>
		</div>
	</div>
	<div class="c7" >
		<div id="FORM2" class="section-search"></div>
		<div>
			<div id="GRID2" class="section-half"></div>
		</div>
	</div>
</div>
   		
<!-- Script -->
<script type="text/javascript">
	
	var fromDt = new Date(new Date().setDate(new Date().getDate() - 6)).format("yyyymmdd");
	var toDt = new Date().format("yyyymmdd");
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"input", label:"부서명", name: "orgnNm", width:90}
					, {type:"newcolumn"}
					, {type:"input", label:"사용자ID", name: "usrId", width:90}
					, {type:"newcolumn"}
					, {type:"input", label:"사용자명", name: "usrNm", width:90}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search3"} 
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/formVrfyMgmt/getFormVrfyUsrList.json", form);
						break;
				}
			}
		}
		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"lstComActvDateF", label:"개통일자", value:fromDt, calendarPosition: "bottom", width:100}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"lstComActvDateT", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", label : "모집경로", width : 100, offsetLeft : 75, name : "onOffType"}
				]}
				, {type:"block", list:[
					, {type:"input", label:"대리점", name: "agntId", width:100}
					, {type:"newcolumn"}
					, {type:"button", value:"검색", name:"btnOrgFind"}
					, {type:"newcolumn"}
					, {type:"input", name: "agntNm", width:120, readonly: true}
					, {type:"newcolumn"}
					, {type:"select", label : "개통유형", width : 100, offsetLeft : 20, name : "operType", options:[{value:"", text:"- 전체 -"}, {value:"NAC", text:"신규"}, {value:"C07", text:"기기변경"}]}
				]}
				, {type:"block", list:[
					, {type:"input", label:"계약번호", name: "contractNum", width:100}
					, {type:"newcolumn"}
					, {type:"label", label:"진행상태", offsetLeft:196}
					, {type:"newcolumn"}
					, {type:"radio", name:"vrfyCd", value: "1", label:"1차", position:"label-right", labelWidth:40, checked:true}
					, {type:"newcolumn"}
					, {type:"radio", name:"vrfyCd", value: "2", label:"2차", position:"label-right", labelWidth:40}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search3"} 
			]
			, onChange:function(name, value){
				var form = this;
				switch(name){
					case "vrfyCd" :
						var targetData = PAGE.GRID1.getSelectedRowData();
						var url = null;
						if(targetData == null) {
							url = "/rcp/formVrfyMgmt/getFormVrfyAsinList.json";
						} else {
							url = "/rcp/formVrfyMgmt/getFormVrfyAsinList.json?vrfyUsrId="+PAGE.GRID1.getSelectedRowData().USR_ID;
						}
						
						$('#GRID2 input:checkbox').prop('checked',false);
						PAGE.GRID2.list(ROOT + url, form);
						
						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":						
						var targetData = PAGE.GRID1.getSelectedRowData();
						var url = null;
						if(targetData == null) {
							url = "/rcp/formVrfyMgmt/getFormVrfyAsinList.json";
						} else {
							url = "/rcp/formVrfyMgmt/getFormVrfyAsinList.json?vrfyUsrId="+PAGE.GRID1.getSelectedRowData().USR_ID;
						}
						
						$('#GRID2 input:checkbox').prop('checked',false);
						PAGE.GRID2.list(ROOT + url, form);
						break;
						
					case "btnOrgFind":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("agntId", result.orgnId);
							form.setItemValue("agntNm", result.orgnNm);
						});
						break;
				}
			}
		}
		, GRID1 : {
			css : {
				height : "540px",
				top : 0,
				bottom :0
			},
			title : "사용자목록",
			header : "부서명,사용자ID,사용자명,usrId",
			columnIds : "ORGN_NM,USR_ID_MSK,USR_NM,USR_ID",
			colAlign : "left,left,left,left",
			widths : "104,70,90,0",
			colTypes : "ro,ro,ro,ro",
			colSorting : "str,str,str,str",
			hiddenColumns : "3",
			paging : true,
			pagingStyle : 1,
			pageSize : 20,
			showPagingAreaOnInit : true,
			onRowSelect : function(rowId, celInd) {
				$('#GRID2 input:checkbox').prop('checked',false);
				var form = PAGE.FORM2;
				PAGE.GRID2.list(ROOT + "/rcp/formVrfyMgmt/getFormVrfyAsinList.json?vrfyUsrId="+PAGE.GRID1.getSelectedRowData().USR_ID, form);
			}
		}
		, GRID2 : {
			css : {
				height : "540px"
			}
			, title : "서식지검증대상목록"
			, header : "#master_checkbox,1차검증자,2차검증자,상품구분,계약번호,고객명,생년월일,나이(만),휴대폰번호,요금제,할인유형,판매정책명,약정개월수,할부개월수,단말모델,단말일련번호,상태,해지사유,모집경로,유입경로,추천인구분,추천인정보,녹취여부,대리점,판매점명,판매자명,판매자ID,기변유형,기변일자,기변모델명,기변대리점,프로모션명,개통유형,할부원금"
			, columnIds : "vrfyUsrId,fstUsrNm,sndUsrNm,prodTypeNm,contractNum,cstmrName,birth,age,subscriberNo,fstRateNm,sprtTpNm,salePlcyNm,enggMnthCnt,instMnthCnt,fstModelNm,fstModelSrlNum,subStatusNm,canRsn,onOffTypeNm,openMarketReferer,recommendFlagNm,recommendInfo,recYn,agentNm,shopNm,shopUsrNm,shopUsrId,dvcOperTypeNm,dvcChgDt,dvcModelNm,dvcAgntNm,promotionNm,operTypeNm,modelInstallment"
			, widths : "30,90,90,80,80,100,60,60,100,200,80,300,80,80,120,120,60,120,60,60,100,70,60,200,200,80,100,80,80,80,100,200,100,100"
			, colAlign : "center,center,center,center,center,center,center,right,center,left,center,left,center,center,left,center,center,left,center,center,center,center,center,left,left,left,left,center,center,left,left,left,center,right"
			, colTypes : "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro,ro"
			, colSorting : "int,str,str,str,int,str,int,int,int,str,str,str,int,int,str,int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,int"
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:100
			, onCheckBox : function(rId, cInd, state){
				var param = "";
				var targetData = PAGE.GRID1.getSelectedRowData();
				if(targetData == null) {
					$('#GRID2 input:checkbox').prop('checked',false);
					PAGE.GRID2.refresh();
					alert("할당 할 사용자를 먼저 선택해 주세요");
					
					false;
				}
				
				param = {
					vrfyUsrId : targetData.USR_ID,
					contractNum : PAGE.GRID2.cellById(rId,4).getValue(),
					asinYn : state,
					vrfyCd : PAGE.FORM2.getItemValue("vrfyCd")
				}
				
				mvno.cmn.ajax(ROOT + "/rcp/formVrfyMgmt/regFormVrfyAsin.json", param, function(resultData){
					if(resultData.result.code != "OK"){
						alert(resultData.result.msg);
					}
				});
			}
		}
		
	}
	
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString}
		, onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createForm("FORM2");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID2");
			
			PAGE.FORM2.disableItem("searchVal");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM2, "onOffType"); // 모집경로
			
		}
	};
</script>
