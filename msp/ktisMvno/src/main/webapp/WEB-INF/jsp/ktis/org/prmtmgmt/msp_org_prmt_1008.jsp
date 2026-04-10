<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<!-- 직접등록 -->
<div id="FORM2" class="section-box"></div>	 

<!-- Script -->
<script type="text/javascript">
var maskingYn = "${maskingYn}";				// 마스킹권한

	var DHX = {
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"srchStrtDt", label:"접수일", labelWidth:80, calendarPosition:"bottom", inputWidth : 100, value: "${startDate}"} 
					, {type:"newcolumn", offset:3}
					, {type: "label", label:"~", labelWidth:10, labelAlign:"center"}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"srchEndDt", calendarPosition:"bottom", inputWidth:100, value: "${endDate}"}
					, {type:"newcolumn"}
					, {type:"select", label:"신청경로", name:"appRoute", width:80, offsetLeft:57}
				]},
				{type : "block",
					list : [
						, {type : "select",width : 114 , label : "검색구분",name : "searchGbn" ,  labelWidth:80}
						, {type : "newcolumn"}
						, {type : "input",width : 100,name : "searchName"}
						, {type:"newcolumn"}
						, {type:"select", label:"성공여부", name:"succYn", width:80, offsetLeft:57, options:[{value:"",text:"- 전체 -"},{value:"Y",text:"성공"},{value:"N",text:"실패"}]}
						, {type:"newcolumn"}
						, {type:"select", label:"처리여부", name:"procYn", width:80, offsetLeft:57, options:[{value:"",text:"- 전체 -"},{value:"Y",text:"Y"},{value:"N",text:"N"}]}
					]
				},
				  {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}
			]
			, onValidateMore : function(data){
				if (data.srchStrtDt > data.srchEndDt) {
					this.pushError("srchStrtDt", "전송일자", "시작일자가 종료일자보다 클 수 없습니다.");
				}
				if(data.srchStrtDt != "" && data.srchEndDt == ""){
					this.pushError("srchStrtDt", "전송일자", "조회기간을 입력하세요.");
				}
				if(data.srchEndDt != "" && data.srchStrtDt == ""){
					this.pushError("srchEndDt", "전송일자", "조회기간을 입력하세요.");
				}
				if(!mvno.cmn.isEmpty(data.searchGbn) && mvno.cmn.isEmpty(data.searchName)) {
					this.pushError(["searchName"],"검색구분","검색어를 입력하십시오.");
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/org/prmtmgmt/getTripleHistMgmtList.json", form)
							
					break;
				}
			}
			, onChange : function(name, value) {
				var form = this;
					
				// 검색구분
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == "") {
						var srchEndDt = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var srchStrtDt = new Date(time);
						
						// 개통일자 Enable
						PAGE.FORM1.enableItem("srchStrtDt");
						PAGE.FORM1.enableItem("srchEndDt");

						PAGE.FORM1.setItemValue("srchStrtDt", srchStrtDt);
						PAGE.FORM1.setItemValue("srchEndDt", srchEndDt);
						
						PAGE.FORM1.disableItem("searchName");
					}
					else {
						PAGE.FORM1.setItemValue("srchStrtDt", "");
						PAGE.FORM1.setItemValue("srchEndDt", "");
						
						// 개통일자 Disable
						PAGE.FORM1.disableItem("srchStrtDt");
						PAGE.FORM1.disableItem("srchEndDt");
						
						PAGE.FORM1.enableItem("searchName");
					}
				}
			}
		}
		, GRID1 : {
			css : {
				height : "530px"
			}
			, title : "조회결과"
			, header : "계약번호,고객명,휴대폰번호,생년월일,신청 시 요금제,현요금제,혜택(부가서비스),개통일,KT인터넷ID,인터넷설치일자,신청경로,신청일자,최초 성공여부,실패사유,처리여부,처리일시,처리자,rateCd,additionCd" //19
			, columnIds : "contractNum,subLinkName,subscriberNo,userSsn,rateNm,lstRateNm,additionNm,lstComActvDate,ktInternetId,installDd,appRoute,cretDt,succYn,reasonFail,procYn,procDt,procNm,rateCd,additionCd" //19
			, widths : "70,100,110,70,250,250,250,70,90,90,70,120,80,300,50,120,70,0,0"
			, colAlign : "center,left,center,center,left,left,left,center,center,center,center,center,center,left,center,center,left,center,center"
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			/* , hiddenColumns:[18,19] */
			, paging : true
			, showPagingAreaOnInit : true
			, pageSize : 20
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드"}
				},
				right : {
					btnSave : { label : "직접등록"}
					,btnStatus : { label : "처리상태변경"}
				}
			}
			,onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnDtl']);
			}
			, onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
					
					case "btnExcel":
	
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
	
						var searchData =  PAGE.FORM1.getFormData(true);
	
						mvno.cmn.download(ROOT + "/org/prmtmgmt/getTripleHistMgmtListExcel.json?menuId=MSP2002810", true, {postData:searchData});
	
						break;

					case "btnSave":
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						fnItemSet("INSERT");

						mvno.ui.popupWindowById("FORM2", "직접등록", 650, 360, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){		
									win.closeForce();
								});
							}
						});
						break;
					case "btnDtl":
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();

						var data = PAGE.GRID1.getSelectedRowData();
						fnItemSet("UPDATE",data);

						mvno.ui.popupWindowById("FORM2", "직접등록", 650, 360, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){		
									win.closeForce();
								});
							}
						});
						break;

					case "btnStatus":
						var data = PAGE.GRID1.getSelectedRowData();

						if (!PAGE.GRID1.getSelectedRowData()) {
							mvno.ui.alert("데이터를 먼저 선택하세요.");
							break;
						}
						
						if(data.succYn == "성공"){
							mvno.ui.alert("성공한 건은 처리상태 변경을 할 수 없습니다.");
							return;
						}else if(data.procYn == "Y"){
							mvno.ui.alert("이미 처리완료된 상태입니다.");
							return;
						}
						
						mvno.ui.confirm("변경하시겠습니까?", function(){
							mvno.cmn.ajax(ROOT + "/org/prmtmgmt/updateTripleHistStatus.json", data, function(resultData) {
								PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);	
							});
						});
					break;
				}
			}
		}
		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
				{type: "fieldset", label:"계약번호",inputWidth:580, list:[
					{type:"settings"},
					{type:"block", labelWidth:40, list:[
						    {type:"input", name:"contractNum", label:"계약번호", labelWidth:60, width:100, value:""}
						  , {type : "newcolumn"}
						  , {type : "button", name:"btnSearchNum", value:"조회"}
					]}
				]},
				{type: "fieldset", label:"상세정보" , inputWidth:580,list:[
					{type:"settings"},
					{type:"block", labelWidth:40, list:[
						{type:"input", name:"subLinkName", label:"고객명", labelWidth:63, width:100, value:"", readonly:true}
						, {type : "newcolumn"}
						, {type:"input", name:"ctn", label:"휴대폰번호", offsetLeft:25, width:100, value:"", readonly:true}
						, {type : "newcolumn"}
						, {type:"input", name:"userSsn", label:"생년월일", offsetLeft:25,width:100, value:"", readonly:true}
					]},  
					{type: "block", labelWidth:40, list: [
						{type:"input", name:"rateCd", label:"요금제", labelWidth:63, width:100, value:""}
						, {type : "newcolumn"}
						, {type:"button", name:"btnRateCd", value:"조회"}
						, {type : "newcolumn"}
						, {type:"input", name:"rateNm", offsetLeft:36, width:230, readonly:true, disabled:true}
					]},
					{type:"block", labelWidth:40, list:[
						{type:"input", name:"additionCd", label:"부가서비스", labelWidth:63, width:100, value:""}
						, {type : "newcolumn"}
						, {type:"button", name:"btnAdditionCd", value:"조회"}
						, {type : "newcolumn"}
						, {type:"input", name:"additionNm", offsetLeft:36, width:230, readonly:true, disabled:true}
					]},	
					{type:"block", labelWidth:40, list:[
						{type:"input", name:"nKtInternetId", label:"KT인터넷ID", labelWidth:63, width:100, value:""}
						, {type : "newcolumn"}
						, {type : "calendar",width : 170,name: "installDd",label : "KT인터넷가입일", offsetLeft:25,value:"",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",calendarPosition:"bottom"}	
					]},
				]},
				{type:"hidden", name:"viewType", value:"INSERT"},
				{type:"hidden", name:"ktInternetId", value:""}
			]	
			, buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			, onButtonClick : function(btnId) {
				
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {
					case "btnSearchNum" :
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("contractNum"))){
							mvno.ui.alert("계약번호를 입력하세요.");
							return;
						}

						var sdata = {contractNum : form.getItemValue("contractNum")};
						
						mvno.cmn.ajax(ROOT + "/org/prmtmgmt/getTripleHistContractNum.json", sdata, function(resultData) {
							var data = resultData.result.data.rows[0];
							if(data != null){
								form.setItemValue("contractNum",	data.contractNum);
								form.setItemValue("subLinkName",	data.subLinkName);
								form.setItemValue("ctn",				data.ctn);
								form.setItemValue("userSsn",			data.userSsn);
								form.setItemValue("rateCd",			data.lstRateCd);
								form.setItemValue("rateNm",			data.lstRateNm);
								form.setItemValue("additionCd",		data.additionCd);
								form.setItemValue("additionNm",	data.additionNm);
							} else {
								mvno.ui.alert("계약정보가 존재하지 않습니다.");
								return;
							}
						});
					break;	

					case "btnRateCd" :
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("rateCd"))){
							mvno.ui.alert("요금제 코드를 입력하세요.");
							return;
						}

						var sdata = {rateCd : form.getItemValue("rateCd")};
						
						mvno.cmn.ajax(ROOT + "/org/prmtmgmt/getTripleHistRate.json", sdata, function(resultData) {
							var data = resultData.result.data.rows[0];
							if(data != null){
								form.setItemValue("additionCd",	"");
								form.setItemValue("additionNm",	"");
								form.setItemValue("rateCd",	data.rateCd);
								form.setItemValue("rateNm",	data.rateNm);
							} else {
								mvno.ui.alert("요금제 정보가 존재하지 않습니다.");
								form.setItemValue("rateNm",	"");
								form.setItemValue("additionCd",	"");
								form.setItemValue("additionNm",	"");
								return;
							}
						});
					break;	

					case "btnAdditionCd" :
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("rateCd"))){
							mvno.ui.alert("요금제 코드를 입력하세요.");
							return;
						}
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("additionCd"))){
							mvno.ui.alert("부가서비스 코드를 입력하세요.");
							return;
						}

						var sdata = {additionCd : form.getItemValue("additionCd")
										,rateCd : form.getItemValue("rateCd")
								};
						
						mvno.cmn.ajax(ROOT + "/org/prmtmgmt/getTripleHistAddition.json", sdata, function(resultData) {
							var data = resultData.result.data.rows[0];
							if(data != null){
								form.setItemValue("additionCd",	 data.addSvcCd);
								form.setItemValue("additionNm", data.addSvcNm);
							} else {
								mvno.ui.alert("부가서비스 정보가 존재하지 않습니다.");
								form.setItemValue("additionNm", "");
								return;
							}
						});
					break;	
					case "btnSave" :
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("contractNum"))){
							mvno.ui.alert("계약번호를 입력하세요.");
							return;
						}

						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("rateCd"))){
							mvno.ui.alert("요금제 코드를 입력하세요.");
							return;
						}

						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("additionCd"))){
							mvno.ui.alert("부가서비스 코드를 입력하세요.");
							return;
						}
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("nKtInternetId"))){
							mvno.ui.alert("KT인터넷ID를 입력하세요.");
							return;
						}
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("installDd"))){
							mvno.ui.alert("KT인터넷 가입일를 입력하세요.");
							return;
						}
						
					
					 	mvno.cmn.ajax(ROOT + "/org/prmtmgmt/insertTripleHist.json", form, function(resultData) {					
							if(resultData.result.code == "NOK") {
								mvno.ui.alert(resultData.result.msg);
			                    return;
							}			
							mvno.ui.notify("NCMN0004"); 			
							PAGE.FORM2.clear();
 							mvno.ui.closeWindowById("FORM2",true);
 							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);	
	 					});
							 	
					break;
						
					case "btnClose":
						mvno.ui.closeWindowById("FORM2");
						PAGE.FORM2.clear();
					break;
				}
			}
		}
	}
			
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		 onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0079"}, PAGE.FORM1, "searchGbn");		// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0080"}, PAGE.FORM1, "appRoute");		// 신청경로
		}
	};

	function fnItemSet(viewType,data){
		if(viewType == "UPDATE"){
			PAGE.FORM2.hideItem("btnSearchNum");
			PAGE.FORM2.hideItem("btnRateCd");
			PAGE.FORM2.hideItem("btnAdditionCd");

			PAGE.FORM2.disableItem("contractNum");
			PAGE.FORM2.disableItem("rateCd");
			PAGE.FORM2.disableItem("additionCd");
			PAGE.FORM2.disableItem("subLinkName");
			PAGE.FORM2.disableItem("ctn");
			PAGE.FORM2.disableItem("userSsn");
			PAGE.FORM2.disableItem("installDd");
			PAGE.FORM2.disableItem("nKtInternetId");
			
			PAGE.FORM2.setItemValue("viewType","UPDATE");
			PAGE.FORM2.setItemValue("contractNum",data.contractNum);
			PAGE.FORM2.setItemValue("subLinkName",data.subLinkName);
			PAGE.FORM2.setItemValue("ctn",data.subscriberNo);
			PAGE.FORM2.setItemValue("userSsn",data.userSsn);
			PAGE.FORM2.setItemValue("rateCd",data.rateCd);
			PAGE.FORM2.setItemValue("rateNm",data.rateNm);
			PAGE.FORM2.setItemValue("additionCd",data.additionCd);
			PAGE.FORM2.setItemValue("additionNm",data.additionNm);
			PAGE.FORM2.setItemValue("nKtInternetId",data.ktInternetId);
			PAGE.FORM2.setItemValue("installDd",data.installDd);
			PAGE.FORM2.setItemValue("ktInternetId",data.ktInternetId);

			mvno.ui.hideButton("FORM2" , "btnSave");
		}else{
			PAGE.FORM2.showItem("btnSearchNum");
			PAGE.FORM2.showItem("btnRateCd");
			PAGE.FORM2.showItem("btnAdditionCd");

			PAGE.FORM2.enableItem("contractNum");
			PAGE.FORM2.enableItem("rateCd");
			PAGE.FORM2.enableItem("additionCd");
			PAGE.FORM2.enableItem("subLinkName");
			PAGE.FORM2.enableItem("ctn");
			PAGE.FORM2.enableItem("userSsn");
			PAGE.FORM2.enableItem("installDd");
			PAGE.FORM2.enableItem("nKtInternetId");
			
			PAGE.FORM2.setItemValue("viewType","INSERT");

			mvno.ui.showButton("FORM2" , "btnSave");
		}
	}
	
</script>
