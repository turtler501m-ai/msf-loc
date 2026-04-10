<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date().format("yyyymmdd");
	// 주소 고객정보, 배송정보 구분
	var jusoGubun = "";
	var maskingYn = "${maskingYn}";	

	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blackOffset:0}
					, {type:"block", list:[
										{type:"select", label:"발주사", name:"orgnId", width:100, labelWidth:40, offsetLeft:20}
					                	]}

					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
					
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							

							PAGE.GRID1.list(ROOT + "/m2m/usimOrdAddr/usimOrdAddrList.json", form, {callback : function() {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}});
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					
				},
				onValidateMore : function (data){

				}
			},
			
			GRID1 : {
				css : {
					height : "550px"
				},
				title : "발주사 배송지 관리",
				header : "발주사,배송지명,배송지담당자,유선전화번호,핸드폰전화번호,우편번호,주소,상세주소,사용여부,등록자",	//10
				columnIds : "orgnNm,areaNm,maskMngNm,mngPhn,mngMblphn,post,addr,maskAddrDtl,useYn,usrNm",
				widths : "100,190,190,80,100,100,100,100,100,100",
				colAlign : "center,center,center,center,center,center,center,center,center,center",
				colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str",
				hiddenColumns:[],
				paging : true,
				pageSize:20,
				buttons : {
					right : {
						btnReg : { label : "등록" },
						btnMod : { label : "수정" },
						btnDel : { label : "삭제" }
					},
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				checkSelectedButtons : ["btnMod","btnDel"],
				onRowDblClicked : function(rowId, celInd) {
					// 수정버튼 누른것과 같이 처리?
					if (maskingYn == "Y") {
						this.callEvent('onButtonClick', ['btnMod']);
					}
				},
				onRowSelect : function (rowId, celInd) {
					
					
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnReg":
							mvno.ui.createForm("FORM2");
							
							PAGE.FORM2.clear();

							mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdAddr/getOrgIdComboList.json", "", PAGE.FORM2, "orgnId"); // 발주사
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0242", orderBy:"etc1"}, PAGE.FORM2, "useYn"); // 사용여부

							//PAGE.FORM2.setItemValue("pointVal",0);
							//PAGE.FORM2.setItemValue("strtDate",today);
							//PAGE.FORM2.setItemValue("endDate",today);
							//PAGE.FORM2.setItemValue("prvdCycl",2);
							//PAGE.FORM2.setItemValue("prmtNm","프로모션1");
							

							//mvno.cmn.ajax4lov( ROOT + "/ptnr/ptnrAddSvc/getRateComboList.json", PAGE.FORM2, PAGE.FORM2, "rateCd"); //요금제

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM2, "telFn1");// 전화번호 지역번호
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM2, "telFn2");// 전화번호 지역번호
							
							
							/*

							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", PAGE.FORM2, function(result) {
								if(result.result.data.total !=0){
									PAGE.FORM2.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
								}
							});
							*/
							mvno.ui.popupWindowById("FORM2", "등록화면", 370, 350, {  
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
									});
								}
							});
							
							break;
							
						case "btnMod":
							mvno.ui.createForm("FORM3");

							PAGE.FORM3.clear();

							mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdAddr/getOrgIdComboList.json", "", PAGE.FORM3, "orgnId"); // 발주사
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0242", orderBy:"etc1"}, PAGE.FORM3, "useYn"); // 사용여부
							
							//mvno.cmn.ajax4lov( ROOT + "/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM3, "rateCd"); //요금제

							//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM3, "onOffType");// 모집경로

							var data = selectedData;
							/*
							
							data.strtDate = data.strtDate.replace(/-/gi,"");
							data.endDate = data.endDate.replace(/-/gi,"");
							*/
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM3, "telFn1");// 전화번호 지역번호
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM3, "telFn2");// 전화번호 지역번호
							
							PAGE.FORM3.setFormData(data); 

							/*
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", PAGE.FORM3, function(result) {
								if(result.result.data.total !=0){
									PAGE.FORM3.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
								}
							});
							*/
							
							PAGE.FORM3.clearChanged();
	
							mvno.ui.popupWindowById("FORM3", "수정화면", 370, 350, {
								onClose: function(win) {
									if (! PAGE.FORM3.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
									});
								}
							});
							
							break;

						case "btnDel":
							
							var data = selectedData;

							msg = "삭제하시겠습니까?";
							mvno.ui.confirm(msg, function() {
								mvno.cmn.ajax(ROOT + "/m2m/usimOrdAddr/usimOrdAddrDelete.json", data, function(result) {
									if (result.result.code == "OK") {
										mvno.ui.notify("NCMN0003");
										PAGE.GRID1.refresh();
									} else {
										mvno.ui.alert(result.result.msg);
									}
								});
							});
							
							break;
							
						case "btnDnExcel":
							
							var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
								return; //버튼 최초 클릭일때만 조회가능하도록
							}
							
							if(PAGE.GRID1.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/m2m/usimOrdAddr/usimOrdAddrListEx.json?menuId=M2M1000021", true, {postData:searchData});
							break;	

					}
				
				}
			},
			
			// --------------------------------------------------------------------------
			// 등록화면
			FORM2 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0,  list: [
						{type:'select', name:'orgnId', label:'발주사', width:150, required: true},
						{type:'input', name:'areaNm', label:'배송지명', width:150, required: true},
						{type:'input', name:'mngNm', label:'배송지 담당명', width:150, required: true}
					 ]},

					 {type:"block", list:[
						{type:"select", label:"유선전화번호", name:"telFn1", width:75},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telMn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telRn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
					 ]},

					 {type:"block", list:[
						{type:"select", label:"핸드폰번호", name:"telFn2", width:75},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telMn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telRn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
						
					 ]},
					 {type:"block", list:[
						{type:"input", label:"주소", name:"post", width:75, readonly:true, maxLength:6},
						{type:"newcolumn"},
						{type:"button", value:"우편번호찾기", name:"btnPostFind"},
						{type:"newcolumn"},
						{type:"input", label:" ", name:"addr", labelWidth:100, width:200, readonly:true, maxLength:83},
						{type:"newcolumn"}
					]},
					{type:"block", list:[
						 {type:"input", label:"상세주소", name:"addrDtl", width:200, maxLength:83},
						 {type:'select', name:'useYn', label:'사용여부', width:75, required: true}
					]}

				],
				buttons : {
					center : {
						btnSave : { label : "저장" },
						btnClose : { label: "닫기" }
					}
				},
				onChange : function(name, value) {//onChange START
					var form = this;

				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnPostFind":
							jusoGubun = "Reg";
							mvno.ui.codefinder4ZipCd("FORM2", "post", "addr", "addrDtl");
							PAGE.FORM2.enableItem("addrDtl");
							
							break;
						
						case "btnSave":
							
							/*
							var strtDate = PAGE.FORM2.getItemValue("strtDate","");
							var endDate = PAGE.FORM2.getItemValue("endDate","");
							
							if(strtDate > endDate){
								mvno.ui.alert("적용종료일자가 더 커야 합니다.");
			                     return;
							}
							*/
							mvno.cmn.ajax(ROOT + "/m2m/usimOrdAddr/usimOrdAddrInsert.json", form, function(result) {
								if (result.result.code == "OK") {
									mvno.ui.closeWindowById(form, true);
										mvno.ui.notify("NCMN0001");
										PAGE.GRID1.refresh();
								} else {
									mvno.ui.alert(result.result.msg);
								}
							});
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;
						/*
						case "btnOrgFind" :
							mvno.ui.codefinder("APPRORGAGENCY", function(result) {
								form.setItemValue("orgnId", result.orgnId);
//								form.setItemValue("orgnNm", result.orgnNm);
							});
							
							break;
							
							
						case "btnUsimOrgFind" :
							mvno.ui.codefinder("APPRORGAGENCY", function(result) {
								form.setItemValue("usimOrgnId", result.orgnId);
//								form.setItemValue("orgnNm", result.orgnNm);
							});
							
							break;
							*/
							
					}
				},
				onValidateMore : function (data){

				}
			},	
			
			
			// --------------------------------------------------------------------------
			//수정화면
			FORM3 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0,  list: [
							{type:'select', name:'orgnId', label:'발주사', width:150, required: true},
							{type:'input', name:'areaNm', label:'배송지명', width:150, required: true},
							{type:'input', name:'mngNm', label:'배송지 담당명', width:150, required: true}
						 ]},

						 {type:"block", list:[
							{type:"select", label:"유선전화번호", name:"telFn1", width:75},
							{type:"newcolumn"},
							{type:"input", label:"-", name:"telMn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4},
							{type:"newcolumn"},
							{type:"input", label:"-", name:"telRn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
						 ]},

						 {type:"block", list:[
							{type:"select", label:"핸드폰번호", name:"telFn2", width:75},
							{type:"newcolumn"},
							{type:"input", label:"-", name:"telMn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4},
							{type:"newcolumn"},
							{type:"input", label:"-", name:"telRn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
							
						 ]},
						 {type:"block", list:[
							{type:"input", label:"주소", name:"post", width:75, readonly:true, maxLength:6},
							{type:"newcolumn"},
							{type:"button", value:"우편번호찾기", name:"btnPostFind"},
							{type:"newcolumn"},
							{type:"input", label:" ", name:"addr", labelWidth:100, width:200, readonly:true, maxLength:83},
							{type:"newcolumn"}
						]},
						{type:"block", list:[
							 {type:"input", label:"상세주소", name:"addrDtl", width:200, maxLength:83},
							 {type:'select', name:'useYn', label:'사용여부', width:75, required: true}
						]}

				],
				buttons : {
					center : {
						btnSave : { label : "수정" },
						btnClose : { label: "닫기" }
					}
				},
				onChange : function(name, value) {//onChange START
					var form = this;

				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnPostFind":
							jusoGubun = "Mod";
							mvno.ui.codefinder4ZipCd("FORM3", "post", "addr", "addrDtl");
							PAGE.FORM3.enableItem("addrDtl");
							
							break;
						
						case "btnSave":
							
							/*
							var strtDate = PAGE.FORM3.getItemValue("strtDate","");
							var endDate = PAGE.FORM3.getItemValue("endDate","");
							
							if(strtDate > endDate){
								mvno.ui.alert("적용종료일자가 더 커야 합니다.");
			                     return;
							}
							*/
							
							
							mvno.cmn.ajax(ROOT + "/m2m/usimOrdAddr/usimOrdAddrUpdate.json", form, function(result) {
								if (result.result.code == "OK") {
									mvno.ui.closeWindowById(form, true);
										mvno.ui.notify("NCMN0002");
										PAGE.GRID1.refresh();
								} else {
									mvno.ui.alert(result.result.msg);
								}
							});
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;
/*
						case "btnOrgFind" :
							mvno.ui.codefinder("APPRORGAGENCY", function(result) {
								form.setItemValue("orgnId", result.orgnId);
//								form.setItemValue("orgnNm", result.orgnNm);
							});
							
							break;
							
							
						case "btnUsimOrgFind" :
							mvno.ui.codefinder("APPRORGAGENCY", function(result) {
								form.setItemValue("usimOrgnId", result.orgnId);
//								form.setItemValue("orgnNm", result.orgnNm);
							});
							
							break;
*/							
					}
				},
				onValidateMore : function (data){

				}
			},	
			
	};


	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
		        if (maskingYn != "Y") {
		            mvno.ui.disableButton("GRID1", "btnMod");
		        }

				
				mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdAddr/getOrgIdComboList.json", "", PAGE.FORM1, "orgnId"); // 발주사
			}
			
	};
	

	/* 주소 setting */
	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
		
		if(jusoGubun == "Reg"){
			PAGE.FORM2.setItemValue("post",zipNo);
			PAGE.FORM2.setItemValue("addr",roadAddrPart1);
			PAGE.FORM2.setItemValue("addrDtl",addrDetail);
		}else if(jusoGubun == "Mod"){
			PAGE.FORM3.setItemValue("post",zipNo);
			PAGE.FORM3.setItemValue("addr",roadAddrPart1);
			PAGE.FORM3.setItemValue("addrDtl",addrDetail);
		}
		
		jusoGubun = "";
		
		
		
	}
			
	
	
	
</script>
