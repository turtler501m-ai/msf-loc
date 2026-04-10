<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>


<!-- Script -->
<script type="text/javascript">

	var DHX = {
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					 {type : "calendar",width : 100,label : "조회기간",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true}
					, {type : "newcolumn"}
					, {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",  value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true}
					, {type : "newcolumn"}
					, {type:"select", width:110, label:"검색구분", name:"searchGbn", offsetLeft:50, options:[{text: "- 선택 -", value: ""},{text: "계약번호", value: "1"}, {text: "서비스계약번호", value: "2"}]}
					, {type:"newcolumn"}
					, {type:"input", width:120, name:"searchName", disabled:true}
				]}
				, {type : 'hidden', name: "DWNLD_RSN", value:""}
				, {type : "button", name: "btnSearch", value:"조회", className:"btn-search1"}	
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
				case "btnSearch" :
					PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getUsimChgHst.json", form);
					break;
				}
			}
	
			, onChange : function(name, value) {//onChange START
				// 검색구분
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == "") {
						var searchEndDt = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var searchStartDt = new Date(time);
		
						// 신청일자 Enable
						PAGE.FORM1.enableItem("srchStrtDt");
						PAGE.FORM1.enableItem("srchEndDt");
		
						PAGE.FORM1.setItemValue("searchName", "");
						PAGE.FORM1.disableItem("searchName");
						PAGE.FORM1.setItemValue("srchStrtDt", searchStartDt);
						PAGE.FORM1.setItemValue("srchEndDt", searchEndDt);
						
					} else {
						PAGE.FORM1.setItemValue("srchStrtDt", "");
						PAGE.FORM1.setItemValue("srchEndDt", "");
						
						// 신청일자 Disable
						PAGE.FORM1.disableItem("srchStrtDt");
						PAGE.FORM1.disableItem("srchEndDt");
		
						PAGE.FORM1.enableItem("searchName");
					}
		
				}
			}
			, onValidateMore : function(data) {
				if( data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
				}
				
				if(data.searchGbn == ""){
					if(this.getItemValue("srchStrtDt", true) == null || this.getItemValue("srchStrtDt", true) == ""){
						this.pushError("srchStrtDt","신청일자","시작일자를 선택하세요");
					}
					if(this.getItemValue("srchEndDt", true) == null || this.getItemValue("srchEndDt", true) == ""){
						this.pushError("srchEndDt","신청일자","종료일자를 선택하세요");
					}
					if (data.srchStrtDt > data.srchEndDt) {
						this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
					}
					if (data.srchStrtDt < '20251105'){
						this.pushError("srchStrtDt", "조회기간", "2025년 11월 5일 이전일자는 조회 할 수 없습니다.");
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
			, header : "개통일자,계약번호,서비스계약번호,상태,USIM모델명,USIM일련번호,USIM일련번호변경일시" // 7
			, columnIds : "lstComActvDate,contractNum,svcCntrNo,subStatus,usimModnm,usimNo,usimChgDate" // 7
			, widths : "120,120,120,100,150,160,160" // 7
			, colAlign : "center,center,center,center,center,center,center" // 7
			, colTypes : "ro,ro,ro,ro,ro,ro,ro" // 7
			, colSorting : "str,str,str,str,str,str,str" // 7
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnExcel : { label : "자료생성" }
				}
			},

			onButtonClick : function(btnId , selectedData) {
				var form = this;
				switch (btnId) {

					case "btnExcel" :

			            if(PAGE.GRID1.getRowsNum() == 0){
			              mvno.ui.alert("데이터가 존재하지 않습니다");
			              return;
			            }
			            mvno.cmn.downloadCallback(function(result) {
			                PAGE.FORM1.setItemValue("DWNLD_RSN",result);
			                mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getUsimChgHstExcel.json?menuId=MSP1010059", PAGE.FORM1.getFormData(true), function(result){
			                    mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
			                });
			            });
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
			}
		};
</script>