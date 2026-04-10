<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 고객 > 현황자료 > 가입번호조회 -->
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
					, {type : "select",width : 100 , label : "검색구분",name : "searchGbn" ,  offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"고객명"}]}
					, {type : "newcolumn"}
					, {type : "input",width : 100,name : "searchName", disabled:true}
				]},
					, {type : "button", name: "btnSearch", value:"조회", className:"btn-search1"}	
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
				case "btnSearch" :
					PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getOwnPhoNumReqList.json", form);
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
			, header : "조회일,고객명,생년월일,성별,본인인증유형,회선수" // 6
			, columnIds : "sysRdate,cstmrName,birth,gender,onlineAuthTypeNm,lineCnt" // 6 
			, widths : "150,150,150,150,150,150" // 6
			, colAlign : "center,left,center,center,center,center" // 6
			, colTypes : "ro,ro,ro,ro,ro,ro" // 6
			, colSorting : "str,str,str,str,str,str" // 6
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
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getOwnPhoNumReqListExcel.json?menuId=MSP1010043", true, {postData:searchData}); 
		
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