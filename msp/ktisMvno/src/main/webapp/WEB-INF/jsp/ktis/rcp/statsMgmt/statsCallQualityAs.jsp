<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 고객 > 현황자료 > 통화품질불량 접수 -->
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="FORM2" class="section-box"></div>


<!-- Script -->
<script type="text/javascript">

	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
				{type:"block", list:[
					 {type : "calendar",width : 100,label : "신청기간",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true}
					, {type : "newcolumn"}
					, {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",  value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true}
					, {type : "newcolumn"}
					, {type:"select", label:"결과", labelWidth:40, name:"successYn", width:100, offsetLeft:40}
					, {type:"newcolumn"}
					, {type:"select", label:"증상", labelWidth:40, name:"errorCd", width:200, offsetLeft:80
						,options:[{value:"", text:"- 전체 -"}, {value:"errorVoice", text:"통화품질 불량"}, {value:"errorCall", text:"통화 수/발신 불가"},
							{value:"errorSms", text:"문자 수/발신 불가"},{value:"errorWifi", text:"WIFI 이용 불가"},{value:"errorData", text:"무선데이터 이용 불가"}]}
				]},
				{type:"block", list:[
					{type:"block", list:[
							{type:"select", label:"검색구분", name:"searchGbn", width:90, offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"계약번호"}, {value:"2", text:"CTN"}]}
							, {type:"newcolumn"}
							, {type:"input", name:"searchName", width:125, maxlength:30}
						]}
				]},
				{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
				case "btnSearch" :
					PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getCallQualityAsList.json", form);
					break;
				}
			}
			
			, 	onValidateMore : function(data) {
				if (data.srchStrtDt > data.srchEndDt) {
					this.pushError("srchStrtDt", "신청기간", "시작일자가 종료일자보다 클 수 없습니다.");
				}
				if(data.srchStrtDt != "" && data.srchEndDt == ""){
					this.pushError("srchStrtDt", "신청기간", "신청기간을 입력하세요.");
				}
				if(data.srchEndDt != "" && data.srchStrtDt == ""){
					this.pushError("srchEndDt", "신청기간", "신청기간을 입력하세요.");
				}				
				if( data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
				}
			}
			, onChange : function(name, value) {
				var form = this;
					
				// 검색구분
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == "") {

						var endDate = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var startDate = new Date(time);
						
						PAGE.FORM1.enableItem("srchStrtDt");
						PAGE.FORM1.enableItem("srchEndDt");
						
						PAGE.FORM1.setItemValue("srchStrtDt", startDate);
						PAGE.FORM1.setItemValue("srchEndDt", endDate);
						
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
		// ----------------------------------------
		// 유심변경
		, GRID1 : {
			css : {
				height : "570px"
			}
			, title : "조회결과"
			, header : "계약번호,고객명,전화번호,신청일시,개통일,개통 대리점,신청인,연락가능한 연락처,증상,증상 발생기간,발생현상,지역,상담내용,결과,시퀀스" // 14
			, columnIds : "contractNum,cstmrName,mobileNo,regDt,lstComActvDate,openAgntNm,regNm,regMobileNo,errorCd,errorDate,errorTypeCd,errorLocTypeNm,counselDetail,counselRsltCdNm,reqSeq" // 14 
			, widths : "100,100,110,150,110,150,100,120,250,180,100,100,250,70,10" // 14
			, colAlign : "center,left,center,center,center,left,left,center,left,center,center,center,left,center,center" // 14
			, colTypes : "ro,ro,roXp,ro,ro,ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro" // 14
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 14
			, hiddenColumns:[14]
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" }
				}
			},
			
			onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnDtl']);
			},

			onButtonClick : function(btnId) {
				
				var grid = this;
				
				switch (btnId) {
				
					case "btnExcel":
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} 
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getCallQualityAsListExcel.json?menuId=MSP1010044", true, {postData:searchData}); 
		
					break;
					
					case "btnDtl":
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();		             
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9035"}, PAGE.FORM2, "counselRsltCd");
						PAGE.FORM2.setFormData(grid.getSelectedRowData(), true);
						
						
						if(PAGE.FORM2.getItemValue("counselRsltCd") == "C" || PAGE.FORM2.getItemValue("counselRsltCd") == "B" ) {
							PAGE.FORM2.disableItem("counselRsltCd");
							PAGE.FORM2.disableItem("counselDetail");
							mvno.ui.hideButton("FORM2" , "btnSave");
						}else{
							PAGE.FORM2.enableItem("counselRsltCd");
							PAGE.FORM2.enableItem("counselDetail");
							mvno.ui.showButton("FORM2" , "btnSave");
						}
						
						
						if(PAGE.FORM2.getItemValue("errorLocTypeCd") == "02" ) {
							PAGE.FORM2.showItem("cstmrAddr");
							PAGE.FORM2.disableItem("cstmrAddr");
						}else{
							PAGE.FORM2.hideItem("cstmrAddr");
							
						}
						
						
						mvno.ui.popupWindowById("FORM2", "상세화면", 820, 360, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
									
						break;				
				}
			}
		  },
		  
			FORM2 : {
				items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0},
						// 고객정보
						{type: "fieldset", label: "고객정보", list:[
							{type:"settings"},
							{type:"block", list:[
								{type:"input", label:"고객명", name:"cstmrName", width:100, readonly:true, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"전화번호",labelWidth:90, name:"mobileNo", width:130, offsetLeft:40, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"신청일자", name:"regDt", width:150, offsetLeft:0, readonly:true, disabled:true},
							]},
							{type:"block", list:[
								{type:"input", label:"개통일", name:"lstComActvDate", width:100, readonly:true, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"개통 대리점", name:"openAgntNm", width:130, offsetLeft:50, disabled:true},		
							]},
							{type:"block", list:[
								{type:"input", label:"신청인", name:"regNm", width:100, readonly:true, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"연락가능 연락처",labelWidth:100, name:"regMobileNo", width:130, offsetLeft:30, disabled:true},		
							]},
							{type:"block", list:[
								{type:"input", label:"증상", name:"errorCd", width:599, readonly:true, disabled:true},
							]},
							{type:"block", list:[
								{type:"input", label:"증상 발생기간", name:"errorDate", width:200, readonly:true, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"발생현상", name:"errorTypeCd", width:69, offsetLeft:10, disabled:true},		
								{type:"newcolumn"},
								{type:"input", label:"지역", name:"errorLocTypeNm", width:118, offsetLeft:30, disabled:true},
							]},
							{type:"block", list:[
								{type:"input", label:"주소지", name:"cstmrAddr", width:599, readonly:true, disabled:true},									
							]},
							{type:"block", list:[
								{type:"input", label:"상담 내용", name:"counselDetail", width:365},
								{type:"newcolumn"},
								{type:"select", label:"결과", name:"counselRsltCd", width:118, offsetLeft:30},	
								{type:"hidden", name:"custReqSeq"},
								{type:"hidden", name:"errorLocTypeCd"}
							]},					
						]}
					]}
				],
				buttons : {
					center : {
						btnSave : {label : "저장" },
						btnClose : { label: "닫기" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;

					switch (btnId) {
					    case "btnSave":
							mvno.cmn.ajax(ROOT + "/stats/statsMgmt/updateCallQualityAs.json", form, function(result) {
							mvno.ui.closeWindowById(PAGE.FORM2, true);
							mvno.ui.notify("NCMN0001");
							
							PAGE.GRID1.refresh();
							});
							break;
												
						case "btnClose" :
							mvno.ui.closeWindowById(PAGE.FORM2, true);
							break;
					}
				}
			}
		  
		  
	  };
	
	var PAGE = {
			title: "${breadCrumb.title}"
			, breadcrumb: "${breadCrumb.breadCrumb}"
			, buttonAuth: ${buttonAuth.jsonString}
			, onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9035"}, PAGE.FORM1, "successYn"); // 결제상태
			}
		};
</script>
