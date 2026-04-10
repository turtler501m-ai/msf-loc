<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
  * @Class Name : msp_stat_bs_4007.jsp
  * @Description : 부가서비스가입자조회
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2017.03.22    강무성     최초 생성
  *
  */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="GRID2"></div>

<!-- Script -->
<script type="text/javascript">
	var strtDt;
	var endDt;

	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", width:100,label:"조회기간", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", offsetLeft:10}
					,{type:"newcolumn"}
					,{type:"calendar", label:"~", name:"endDt", labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, offsetLeft:5, width:100}
					,{type:"newcolumn"}
					,{type:"checkbox",width:5, label:"전체기간",name:"allDt", position:"label-left", labelAlign:"right",checked:false}
					,{type:"newcolumn"}
// 					,{type:"select", width:120, offsetLeft:50, label:"검색구분", name:"searchGbn", userdata:{lov:"CMN0115"}}
					,{type:"select", width:120, offsetLeft:50, label:"검색구분", name:"searchGbn"}
					,{type:"newcolumn"}
					,{type:"input", width:150,name:"searchName", maxLength:20}
					,{type : 'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유
				]}
				, {type:"newcolumn"}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
			]
			, onValidateMore:function (data) {
				if( data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", "");
				}

				if(!PAGE.FORM1.isItemChecked("allDt")){
					if( data.searchGbn == null || data.searchGbn == ""){
						if(this.getItemValue("strtDt") == null || this.getItemValue("endDt") == ""){
							this.pushError("strtDt","조회기간","시작일자를 선택하세요");
						}
						
						if(this.getItemValue("strtDt") == null || this.getItemValue("endDt") == ""){
							this.pushError("endDt","조회기간","종료일자를 선택하세요");
						}
						
						if(this.getItemValue("strtDt") != null && this.getItemValue("strtDt") != ""
							&& this.getItemValue("endDt") != null && this.getItemValue("endDt") != ""){
							if(this.getItemValue("strtDt", true) > this.getItemValue("endDt", true)){
								this.pushError("strtDt", "조회기간", "종료일자가 시작일자보다 클수 없습니다.");
								this.resetValidateCss();
							}
						}
					}
				}else{
					if(data.searchGbn == null || data.searchGbn == "") {
						this.pushError("searchGbn","검색구분","전체기간 조회시 검색구분을 설정해야 합니다.");
					}
				}
			}
			, onChange:function(name, value) {
				var form = this;

				switch(name){
					case "searchGbn" : 
					// 검색구분
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == "") {
						/* PAGE.FORM1.enableItem("strtDt");
						PAGE.FORM1.enableItem("endDt");
						
						PAGE.FORM1.setItemValue("strtDt", strtDt);
						PAGE.FORM1.setItemValue("endDt", endDt); */
						
						PAGE.FORM1.disableItem("searchName");
					} else {
						/* PAGE.FORM1.setItemValue("strtDt", "");
						PAGE.FORM1.setItemValue("endDt", "");
						
						PAGE.FORM1.disableItem("strtDt");
						PAGE.FORM1.disableItem("endDt"); */
						
						PAGE.FORM1.enableItem("searchName");
					}
	
				case "allDt" :
					if(form.isItemChecked(name)){
						PAGE.FORM1.setItemValue("strtDt", "");
						PAGE.FORM1.setItemValue("endDt", "");
						
						PAGE.FORM1.disableItem("strtDt");
						PAGE.FORM1.disableItem("endDt");
					}else{
						PAGE.FORM1.enableItem("strtDt");
						PAGE.FORM1.enableItem("endDt");
						
						PAGE.FORM1.setItemValue("strtDt", strtDt);
						PAGE.FORM1.setItemValue("endDt", endDt);
					}
				}
			}
			, onButtonClick:function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch" :
						if (! this.validate())  return;
						
						PAGE.GRID2.clearAll();
						
						var url = ROOT + "/rsk/statMgmt/getAddProdList.json";
						PAGE.GRID1.list(url, form);
						break;
				}
			}
		}, //FORM1 End
		
		GRID1:{
			css : {height : "330px"},
			title : "조회결과",
			header : "부가서비스코드,부가서비스명,가입일자,부가서비스해지,계약번호,개통일자,현재요금제,최초요금제,할인유형,판매정책명,단말모델,대리점",
			columnIds : "addProd,addProdNm,strtDt,endDt,contractNum,openDt,lstRateNm,fstRateNm,dcTypeNm,salePlcyNm,fstModelNm,openAgntNm",
			widths : "120,180,120,120,100,120,190,190,80,200,150,170",
			colAlign : "left,left,center,center,center,center,left,left,center,left,left,left",
			colTypes : "ro,ro,roXd,roXd,ro,roXd,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize : 20,
			showPagingAreaOnInit: true,
			buttons : {
				hright : {
					//btnDnExcel : { label : "엑셀다운로드" }
					btnDnExcel : { label : "자료생성" }
				}
			},
			onRowSelect:function(rId, cId){
				
				var sdata = this.getSelectedRowData();
				
				PAGE.GRID2.list(ROOT + "/rsk/statMgmt/getAddProdHistList.json", {contractNum:sdata.contractNum});
			},
			onButtonClick : function (btnId, selectedData){
				
				var form = this;
					
				switch (btnId){
					case "btnDnExcel":
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}else{
							mvno.ui.prompt("다운로드 사유",  function(result) {
								PAGE.FORM1.setItemValue("DWNLD_RSN",result);
								mvno.cmn.ajax(ROOT + "/rsk/statMgmt/getAddProdListExcel.json?menuId=MSP5003010", PAGE.FORM1.getFormData(true), function(result){
									mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
								});
							}, { lines: 5,  maxLength : 80, minLength : 10 } );
						}
						
						break;
				}
			}
		}, //GRID1 End
		
		GRID2 : {
			css : {height : "200px"},
			title : "이력조회",
			header : "부가서비스코드,부가서비스명,기본료,가입일자,해지일자",
			columnIds : "addProd,addProdNm,baseAmt,strtDt,endDt",
			widths : "150,250,135,200,200",
			colAlign : "left,left,right,center,center",
			colTypes : "ro,ro,ron,roXd,roXd",
			colSorting : "str,str,int,str,str",
			paging : false
		}, //GRID2 End
	}; //DHX End
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			buttonAuth:${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createGrid("GRID2");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2028"}, PAGE.FORM1, "searchGbn"); // 검색구분
				
				endDt = new Date().format('yyyymmdd');
				var time = new Date().getTime();
				time = time - 1000 * 3600 * 24 * 7;	// 조회기간 디폴트 7일 설정
				strtDt = new Date(time);
				
				PAGE.FORM1.setItemValue("strtDt", strtDt);
				PAGE.FORM1.setItemValue("endDt", endDt);
				
				PAGE.FORM1.disableItem("searchName");
			}
		};
</script>