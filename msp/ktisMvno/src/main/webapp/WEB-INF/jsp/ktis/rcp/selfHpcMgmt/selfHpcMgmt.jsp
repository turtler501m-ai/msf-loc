<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="FORM2" class="section-box"></div>


<!-- Script -->
<script type="text/javascript">

	var DHX = {
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type : "calendar",width : 100,label : "개통일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true}
					, {type : "newcolumn"}
					, {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",  value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true}
					, {type : "newcolumn"}
					, {type:"select", label:"해피콜여부", name:"srchHpcStat", width:120, offsetLeft:95, labelWidth:70}
				]}
				, {type:"block", list:[
					{type:"select", label:"검색구분", name: "searchGbn", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchName", width:165, offsetLeft:0, maxLength:60}
                    , {type:"newcolumn"}
                    , {type:"select", label:"해피콜결과", name: "srchHpcRst", width:120, offsetLeft:45, labelWidth:70}
                    , {type:"newcolumn"}
                    , {type:"select", label:"A'Cen 결과", name: "srchAcenRst", width:120, offsetLeft:35, labelWidth:70}
				]}
				, {type : "button", name: "btnSearch", value:"조회", className:"btn-search2"}	

				//버튼 여러번 클릭 막기 변수
				, {type : 'hidden', name: "btnCount1", value:"0"} 
				, {type : 'hidden', name: "btnExcelCount1", value:"0"}
				, {type : 'hidden', name: "DWNLD_RSN", value:""}//엑셀다운로드 사유  추가
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
				case "btnSearch" :
					var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnCount1", btnCount2)
					if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han

					PAGE.GRID1.list(ROOT + "/rcp/selfHpcMgmt/getSelfHpcList.json", form, {callback : function() {
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}});

					break;
				}
			}
			
			, onValidateMore : function(data) {
				if (data.srchStrtDt > data.srchEndDt) {
					this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}
				if(data.srchStrtDt != "" && data.srchEndDt == ""){
					this.pushError("srchStrtDt", "조회기간", "조회기간을 입력하세요.");
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}
				if(data.srchEndDt != "" && data.srchStrtDt == ""){
					this.pushError("srchEndDt", "조회기간", "조회기간을 입력하세요.");
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}				
				if( data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
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
		, GRID1 : {
			css : {
				height : "570px"
			}
			, title : "조회결과"
			, header : "계약번호,개통일자,고객명,고객전화번호,대리점명,NICE인증 번호,NICE인증 통신사,NICE인증 일시,나이,개통요금제,성별,주소지,유심접점,최초유심접점,해피콜여부,1차해피콜결과,1차해피콜일시,2차해피콜결과,2차해피콜일시,3차해피콜결과,3차해피콜일시,결과,A'Cen 요청상태, A'Cen 결과"  // 24
			, columnIds : "contractNum,lstComActvDate,subLinkName,subscriberNo,openAgntNm,mobileNo,mobileCo,authTime,age,fstRateNm,gender,addr,usimOrgNm,fstUsimOrgNm,hpcStatNm,fstHpcRstNm,fstHpcDttm,sndHpcRstNm,sndHpcDttm,thdHpcRstNm,thdHpcDttm,hpcRstNm,acenReqStatusNm,acenRstNm" // 24
			, widths : "100,100,100,120,130,120,120,150,100,180,100,300,180,180,100,100,150,100,150,100,150,100,100,100" // 24
			, colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center" // 24
			, colTypes : "ro,ro,ro,roXp,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" // 24
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 24
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnExcel : { label : "자료생성" }
				}
			}
			, onRowDblClicked : function(rowId, celInd) {
				PAGE.GRID1.callEvent("onButtonClick", ["btnDtl"]);
			}

			, onButtonClick : function(btnId , selectedData) {
				var grid = this;
				switch (btnId) {
					case "btnExcel":
						
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
							return; //버튼 최초 클릭일때만 조회가능하도록
						}
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} else {
                            mvno.cmn.downloadCallback(function(result) {
								PAGE.FORM1.setItemValue("DWNLD_RSN",result);
								mvno.cmn.ajax(ROOT + "/rcp/selfHpcMgmt/getSelfHpcListByExcel.json?menuId=MSP1030113", PAGE.FORM1.getFormData(true), function(result){
									mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다."); 
								});
							});
						}

						break;
					
					case "btnDtl":
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0066"}, PAGE.FORM2, "fstHpcRst");	// 1차해피콜결과
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0066"}, PAGE.FORM2, "sndHpcRst");	// 2차해피콜결과
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0066"}, PAGE.FORM2, "thdHpcRst");	// 3차해피콜결과
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9040",orderBy:"etc6"}, PAGE.FORM2, "hpcStat");		// 해피콜여부
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9041"}, PAGE.FORM2, "hpcRst");		// 결과
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"AcenSelfRslt"}, PAGE.FORM2, "acenRst");	    // A'Cen 결과

						var rowData = grid.getSelectedRowData();
						PAGE.FORM2.setFormData(rowData, true);	

						PAGE.FORM2.disableItem("OPENINFO");
						PAGE.FORM2.disableItem("AUTHINFO");
						PAGE.FORM2.disableItem("FSTHPC");
						PAGE.FORM2.disableItem("SNDHPC");
						PAGE.FORM2.disableItem("THDHPC");
                        PAGE.FORM2.disableItem("ACENSTAT");

						if (rowData.hpcStat == "COMP") {
							PAGE.FORM2.enableItem("hpcRst");
						} else {
							PAGE.FORM2.disableItem("hpcRst");
						}
						
						if (rowData.fstHpcDttm == "") {
							PAGE.FORM2.enableItem("FSTHPC");
							PAGE.FORM2.disableItem("SNDHPC");
							PAGE.FORM2.disableItem("THDHPC");
							PAGE.FORM2.setItemValue("hpcGbn", "FST");
						} else {
							if (rowData.sndHpcDttm == "") {
								PAGE.FORM2.disableItem("FSTHPC");
								PAGE.FORM2.enableItem("SNDHPC");
								PAGE.FORM2.disableItem("THDHPC");
								PAGE.FORM2.setItemValue("hpcGbn", "SND");
							} else {
								if (rowData.thdHpcDttm == "") {
									PAGE.FORM2.disableItem("FSTHPC");
									PAGE.FORM2.disableItem("SNDHPC");
									PAGE.FORM2.enableItem("THDHPC");
									PAGE.FORM2.setItemValue("hpcGbn", "THD");
								}
							}
						}						
						
						mvno.ui.popupWindowById("FORM2", "상세화면", 900, 610, {
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
		}

		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:100, blockOffset:0}
				,{type: "fieldset", label: "개통정보", name:"OPENINFO", inputWidth:810, list:[
					{type:"block", list:[
						{type:"input", label:"계약번호", name:"contractNum", width:140, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"개통일자", name:"lstComActvDate", width:140, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"고객명", name:"subLinkName", width:140, offsetLeft:10, readonly:true}
					]}
					, {type:"block", list:[
						{type:"input", label:"나이", name:"age", width:140, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"개통요금제", name:"fstRateNm", width:140, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"성별", name:"gender", width:140, offsetLeft:10, readonly:true}
					]}
					, {type:"block", list:[
						{type:"input", label:"주소지", name:"addr", width:140, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"유심접점", name:"usimOrgNm", width:140, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"최초유심접점", name:"fstUsimOrgNm", width:140, offsetLeft:10, readonly:true}
					]}
				]}
				,{type: "fieldset", label: "NICE 인증정보", name:"AUTHINFO", inputWidth:810, list:[
					{type:"block", list:[
						{type:"input", label:"인증 전화번호", name:"mobileNo", width:140, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"인증 통신사", name:"mobileCo", width:140, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"인증일시", name:"authTime", width:140, offsetLeft:10, readonly:true}
					]}
				]}
				,{type: "fieldset", label: "해피콜상태", name:"HPCSTAT", inputWidth:810, list:[
					{type:"block", list:[
						{type:"select", label:"해피콜여부", name:"hpcStat", width:140, offsetLeft:10, required:true, validate:"NotEmpty"}
						, {type:"newcolumn"}
						, {type:"select", label:"결과", name:"hpcRst", width:140, offsetLeft:10}
						, {type:"hidden", name:"hpcGbn"}
						, {type:"hidden", name:"requestKey"}
					]}
				]}
        ,{type: "fieldset", label: "A'Cen 상태", name:"ACENSTAT", inputWidth:810, list:[
            {type:"block", list:[
                {type:"input", label:"A'Cen 요청상태", name:"acenReqStatusNm", width:140, offsetLeft:10, readonly:true}
                , {type:"newcolumn"}
                , {type:"select", label:"A'Cen 결과", name:"acenRst", width:140, offsetLeft:10, disabled:true}
            ]}
        ]}
				,{type:"newcolumn"}
				,{type: "fieldset", label: "1차 해피콜", name:"FSTHPC", inputWidth:264, list:[
					{type:"block", list:[
						{type:"select", label:"결과", name:"fstHpcRst", width:80, offsetLeft:10, labelWidth:50}
						, {type:"newcolumn"}
						, {type:"input", label:"비고", name:"fstHpcRmk", width:150, offsetLeft:10, rows:7, maxLength:1000, labelWidth:50}
					]}
				]}
				,{type:"newcolumn"}
				,{type: "fieldset", label: "2차 해피콜", name:"SNDHPC", inputWidth:264, offsetLeft:8, list:[
					{type:"block", list:[
						{type:"select", label:"결과", name:"sndHpcRst", width:80, offsetLeft:10, labelWidth:50}
						, {type:"newcolumn"}
						, {type:"input", label:"비고", name:"sndHpcRmk", width:150, offsetLeft:10, rows:7, maxLength:1000, labelWidth:50}
					]}
				]}
				,{type:"newcolumn"}
				,{type: "fieldset", label: "3차 해피콜", name:"THDHPC", inputWidth:264, offsetLeft:8, list:[
					{type:"block", list:[
						{type:"select", label:"결과", name:"thdHpcRst", width:80, offsetLeft:10, labelWidth:50}
						, {type:"newcolumn"}
						, {type:"input", label:"비고", name:"thdHpcRmk", width:150, offsetLeft:10, rows:7, maxLength:1000, labelWidth:50}
					]}
				]}
			],
			buttons : {
				center : {
					btnSave : {label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onChange: function (name, value) {
				var form = this;
				
				switch(name) {
					case "hpcStat":
						if (value == "COMP") {
							PAGE.FORM2.enableItem("hpcRst");
						} else {
							PAGE.FORM2.disableItem("hpcRst");
							PAGE.FORM2.setItemValue("hpcRst","");
						}
						
						break;
				}
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					case "btnSave":
						if (form.getItemValue("hpcStat") == "COMP") {
							if (form.getItemValue("hpcRst") == "") {
								mvno.ui.alert("해피콜결과를 선택해주세요.");
								return;
							}
						}
						console.log(form.getItemValue("hpcGbn"));
						if (form.getItemValue("hpcGbn") == "FST") {
							if (form.getItemValue("fstHpcRst") == "" || form.getItemValue("fstHpcRmk") == "") {
								mvno.ui.alert("1차 해피콜 결과, 비고를 입력해주세요.");
								return;
							}
						}
						if (form.getItemValue("hpcGbn") == "SND") {
							if (form.getItemValue("sndHpcRst") == "" || form.getItemValue("sndHpcRmk") == "") {
								mvno.ui.alert("2차 해피콜 결과, 비고를 입력해주세요.");
								return;
							}
						}
						if (form.getItemValue("hpcGbn") == "THD") {
							if (form.getItemValue("thdHpcRst") == "" || form.getItemValue("thdHpcRmk") == "") {
								mvno.ui.alert("3차 해피콜 결과, 비고를 입력해주세요.");
								return;
							}
						}

						mvno.cmn.ajax(ROOT + "/rcp/selfHpcMgmt/updateHpcRst.json", form, function(result) {
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
			PAGE.FORM1.disableItem("searchName");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9013"}, PAGE.FORM1, "searchGbn");		// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9040",orderBy:"etc6"}, PAGE.FORM1, "srchHpcStat");		// 해피콜여부
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9041"}, PAGE.FORM1, "srchHpcRst");		// 결과
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"AcenSelfRslt"}, PAGE.FORM1, "srchAcenRst");	// A'Cen 결과
		}
	};
</script>