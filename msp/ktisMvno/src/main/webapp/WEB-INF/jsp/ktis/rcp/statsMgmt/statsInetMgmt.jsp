<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="GRID2" class="section"></div>


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
					, {type:"select", label:"계약상태", name:"subStatus", width:80, offsetLeft:57}
					, {type:"newcolumn"}
					, {type:"select", label:"결합여부", name:"combYn", width:80, offsetLeft:57, options:[{value:"",text:"- 전체 -"},{value:"Y",text:"Y"},{value:"N",text:"N"}]}
				]},
				{type : "block",
					list : [
						, {type : "select",width : 114 , label : "검색구분",name : "searchGbn" ,  labelWidth:80}
						, {type : "newcolumn"}
						, {type : "input",width : 100,name : "searchName"}
					]
				},
				  {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getStatsInetList.json", form);
					break;
				}
			}
			, onValidateMore : function(data){
				if(data.searchGbn == "" ){
					if(data.srchStrtDt == ''){
						this.pushError("srchStrtDt", "개통시작일자", "시작일자는 필수 입력 항목입니다.");
						
					}
					if(data.srchEndDt == ''){
						this.pushError("srchEndDt", "개통종료일자", "종료일자는 필수 입력 항목입니다.");
						
					}
					if(data.srchEndDt > data.srchEndDt){
						this.pushError("srchStrtDt", "개통시작일자", "종료일자보다 시작일자 값이 큽니다.");
					}
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
				height : "380px"
			}
			, title : "조회결과"
			, header : "고객명,생년월일,성별,연락처,서비스계약ID,고객ID,상품코드,상품명,상태,접수일,개통일자(설치완료),해지일자,모집경로,인터넷ID,결합여부,svcCntrId" //15
			, columnIds : "custNm,birthDt,gender,ctn,svcCntrIdMsk,custId,prodCd,prodNm,status,regDt,openDt,canDt,regType,internetId,combYn,svcCntrId" //15
			, widths : "80,80,50,100,100,100,70,90,90,110,110,110,100,120,50,0" //15
			, colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"//15
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			/* , hiddenColumns : [18,19,20,21,22,23,24] */
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀 다운로드" }
				}
			}
			, onRowSelect:function(rId, cId){
				PAGE.GRID2.clearAll();
				PAGE.GRID2.list(ROOT + "/stats/statsMgmt/getStatsInetHistList.json", this.getSelectedRowData());
			}
			,onButtonClick : function(btnId , selectedData) {
				var form = this;
				switch (btnId) {
					case "btnExcel":
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}

						var searchData =  PAGE.FORM1.getFormData(true);
						
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getStatsInetListExcel.json?menuId=MSP1010051", true, {postData:searchData});

						break;
				}
			}
		},
		GRID2 : {
			css : {
				height : "130px"
			}
			, title : "변경이력"
			, header : "고객명,생년월일,성별,연락처,서비스계약ID,고객ID,상품코드,상품명,상태,접수일,개통일자(설치완료),해지일자,모집경로,인터넷ID,결합여부,FTP 파일명" //15
			, columnIds : "custNm,birthDt,gender,ctn,svcCntrId,custId,prodCd,prodNm,status,regDt,openDt,canDt,regType,internetId,combYn,fileNm" //15
			, widths : "80,80,50,100,100,100,70,90,90,110,110,110,100,120,50,190" //15
			, colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"//15
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			
		}
	};
	
	var PAGE = {
		title: "${breadCrumb.title}"
		, breadcrumb: "${breadCrumb.breadCrumb}"
		, buttonAuth: ${buttonAuth.jsonString}
		, onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID2");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2061", orderBy:"etc1"}, PAGE.FORM1, "subStatus"); // 계약상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2062"}, PAGE.FORM1, "searchGbn"); // 검색구분
			
		}
	};
</script>