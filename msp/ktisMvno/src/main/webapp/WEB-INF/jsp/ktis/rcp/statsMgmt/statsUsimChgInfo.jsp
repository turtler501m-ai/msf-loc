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
					, {type : "select",width : 100 , label : "검색구분",name : "searchGbn" ,  offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"계약번호"}, {value:"2", text:"CTN"}]}
					, {type : "newcolumn"}
					, {type : "input",width : 100,name : "searchName", disabled:true}
					, {type : "newcolumn"}
					, {type : "select", label : "처리결과", width : 100, offsetLeft : 20, name : "successYn" }
				]},
					, {type : "button", name: "btnSearch", value:"조회", className:"btn-search1"}	
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
				case "btnSearch" :
					PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getUsimChgList.json", form);
					break;
				}
			}
			
			, 	onValidateMore : function(data) {
				if (data.srchStrtDt > data.srchEndDt) {
					this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
				}
				if(data.srchStrtDt != "" && data.srchEndDt == ""){
					this.pushError("srchStrtDt", "조회기간", "조회기간을 입력하세요.");
				}
				if(data.srchEndDt != "" && data.srchStrtDt == ""){
					this.pushError("srchEndDt", "조회기간", "조회기간을 입력하세요.");
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
			, header : "MVNO오더ID,계약번호,고객명,전화번호,성별,나이(만),개통일,개통 대리점,모집유형,신청일시,처리상태,유심일련번호,사전체크결과,사전체크 불가내용,사전체크 안내내용,결과코드,결과메시지" // 16
			, columnIds : "mvnoOrdNo,svcContId,subLinkName,ctn,gender,age,lstComActvDate,openAgntNm,onOffType,rdt,prgrStatCd,iccId,trgtAtribSbst,trgtFaluMsg,trgtInsurMsg,rsltCd,rsltMsg" // 16 
			, widths : "110,100,80,100,60,60,90,130,130,140,100,140,100,200,200,60,200" // 16
			, colAlign : "center,center,left,center,center,center,center,center,center,center,center,center,center,left,left,center,left" //16
			, colTypes : "ro,ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" //16
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 16
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" }
				}
			},

			onButtonClick : function(btnId , selectedData) {
				var form = this;
				switch (btnId) {
					case "btnExcel":
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} 
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getUsimChgListExcel.json?menuId=MSP1010037", true, {postData:searchData}); 
		
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
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9033" ,orderBy:"etc1" }, PAGE.FORM1, "successYn" ); // 성공여부
			}
		};
</script>