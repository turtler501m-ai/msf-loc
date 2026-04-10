<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var curMnth = new Date().format("yyyymmdd");

	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0},
					{type:"block", list:[
						{type:"calendar", label:"발송일자", name:"sendStDt", width:100, value:curMnth, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", offsetLeft:10, required: true},
						{type : "newcolumn"},
						{type : "calendar",label : "~",name : "sendEdDt",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true},
						{type:"newcolumn"},
						{type:"select", label:"고객구분", name:"searchSendDiv", width:100, offsetLeft:20},
						{type:"newcolumn"},
						{type:"select", label:"발송여부", name:"searchSendYn", width:100, offsetLeft:20},
	                	]},
					{type:"block", list: [
						{type:'select', name:'searchCd', label:'검색구분', width:100, offsetLeft:10},
						{type:"newcolumn"},
						{type:"input", name:"searchVal", width:115}
					
					 ]}
					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					                				
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"} 
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :

							PAGE.GRID1.list(ROOT + "/rcp/mrktMgmt/getMarketingSmsSendList.json", form);
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					/* if(name == "sendYm") {
						PAGE.FORM1.setItemValue("sendYm", curMnth);
					} */

				},
				onValidateMore : function (data){
					if( data.searchCd != "" && data.searchVal.trim() == ""){
						this.pushError("searchCd", "검색어", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					} 
					
					if((data.sendStDt && !data.sendEdDt) || (!data.sendStDt && data.sendEdDt)){
						this.pushError("data.sendStDt","발송일자","발송일자를 선택하세요");
					}
					else if(data.sendStDt > data.sendEdDt){
						this.pushError("data.sendStDt","발송일자", "시작월이 종료월보다 큽니다");
					}
					
					if(mvno.validator.dateCompare(data.sendStDt, data.sendEdDt) > 7) this.pushError("data.strtDt", "발송일자", "조회기간은 7일 이내만 가능합니다.");
					
				}
			},
			
			GRID1 : {
				css : {
					height : "550px"
				},
				title : "조회결과",
				header : "발송요청일시,고객구분,계약번호,홈페이지ID,고객명,휴대폰번호,발송여부,발송결과,발송횟수,발송일시,동의일자",	//11
				columnIds : "sendReqDttm,sendDivision,contractNum,portalUserId,custNm,mobileNo,sendYn,resultNm,sendCnt,sendTime,strtDttm",
				widths : "150,80,100,100,100,100,80,120,80,150,120",
				colAlign : "center,center,center,left,left,center,center,left,right,center,center",
				colTypes : "roXdm,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdm,roXdm",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str",
				paging : true,
				pageSize:20,
				onButtonClick : function(btnId) {

				}
				
			},
						
			
	};

	var PAGE = {
			title: "${breadCrumb.title}",
			breadcrumb: "${breadCrumb.breadCrumb}",
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2046"}, PAGE.FORM1, "searchSendDiv"); // 고객구분
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM1, "searchSendYn"); // 발송여부
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2047"}, PAGE.FORM1, "searchCd"); // 검색구분
				
				var sendEdDt = new Date().format('yyyymmdd');
				var time = new Date().getTime();
				time = time - 1000 * 3600 * 24 * 7;
				var sendStDt = new Date(time);

				PAGE.FORM1.setItemValue("sendStDt", sendStDt);
				PAGE.FORM1.setItemValue("sendEdDt", sendEdDt);
				
			}
			
	};
    
</script>
