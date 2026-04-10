<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="GRID2" class="section"></div>

<div id="POPUP2" >
	<div id="FORM3"  class="section-box"></div>
	<div id="GRID3" class="section-full"></div>
	<div id="FORM4"  class="section-box"></div>
	<div id="FORM3btn"  class="section-full"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	
	var today = new Date().format("yyyymmdd");
	// 주소 고객정보, 배송정보 구분
	var jusoGubun = "";
	
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0}
					, {type:"block", list:[
						{type:"calendar", label:"가입신청일", name:"frDate", width:100, dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', calendarPosition:"bottom",readonly: true, labelWidth:70, offsetLeft:20 }
						, {type:"newcolumn"}
						, {type:"calendar", label:"~", name:"toDate", width:100, dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', calendarPosition:"bottom",readonly: true, labelWidth:10, offsetLeft:10 }
						, {type:"newcolumn"}
						, {type:"select", label:"검색구분", name:"searchGbn", width:100, labelWidth:70, offsetLeft:20 }
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"searchTxt", width:200, labelWidth:50, offsetLeft:0}
	                ]}
					, {type:"block", list:[
						, {type:"calendar", label:"정산기준월", name:"billDt", width:100, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', value:"", calendarPosition:"bottom", labelWidth:70, offsetLeft:20}
						, {type: 'newcolumn'}
						, {type: "select",label: "주관부서",  name: "prmtOrgnId", width:100, labelWidth : 70, offsetLeft:146 }
						, {type: "newcolumn", offset:15}
						, {type:"select", width:100, label:"노출여부", name:"showYn", options:[{value:"", text:"- 전체 -", selected:true},{value:"Y", text:"노출"},{value:"N", text:"비노출"}], labelWidth : 60, offsetLeft:25}
					  ]}

					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					, {type : 'hidden', name: "DWNLD_RSN", value:""}//엑셀다운로드 사유  추가					                				
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							

							PAGE.GRID1.list(ROOT + "/gift/custgmt/custMngList.json", form, {callback : function() {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}});

							PAGE.GRID2.clearAll();
							
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					
				},
				onValidateMore : function (data){
					if(mvno.validator.dateCompare(data.frDate, data.toDate) > 15) {
						 this.pushError("data.frDate", "가입신청일", "조회기간은 15일 이내만 가능합니다.");
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						    PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
					
				}
			},
			
			GRID1 : {
				css : {
					height : "300px"
				},
				title : "고객 사은품 정보",
				header : "프로모션ID,프로모션명,계약번호,프로모션 유형,노출여부,정산기준일,주관부서,고객명,CTN,받는분고객명,받는분 연락처1,받는분 연락처2,우편번호,주소,상세주소,사은품명,수량,합계금액,제한금액,신청일,개통일,사은품신청일,고객최초요금제,고객최초요금제명,현재요금제명,대리점,최초유심접점,현요금제변경일시,현상태",	//20
				columnIds : "prmtId,prmtNm,contractNum,prmtTypeNm,showYnNm,billDt,prmtOrgnNm,subLinkName,subscriberNo,maskMngNm,tel1,tel2,post,addr,maskAddrDtl,prdtNm,quantity,sumAmount,amountLimit,reqInDay,lstComActvDate,giftRegstDate,fstRateCd,fstRateNm,lstRateNm,openAgntNm,fstUsimOrgNm,lstRateDt,subStatusNm",         
				widths : "100,100,100,90,70,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,300,300,150,150,120,80",
				colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,Right,right,right,center,center,center,center,center,center,left,left,center,center",
				colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ron,roXd,roXd,roXd,ro,ro,ro,ro,ro,roXd,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
				hiddenColumns:[20],
				paging : true,
				pageSize:20,
				buttons : {
					right : {
						//btnReg : { label : "등록" },
						btnMod : { label : "수정" }
					},
					hright : {
						btnDnExcel : { label : "자료생성" }// 20221129 해당 기능을 배치로 변경해야함 
					   ,btnDnExcelPrdt : { label : "제품자료생성" }// 20221129 해당 기능을 배치로 변경해야함
					}
				},
				checkSelectedButtons : ["btnMod"],
				onRowDblClicked : function(rowId, celInd) {
					// 수정버튼 누른것과 같이 처리?
					this.callEvent('onButtonClick', ['btnMod']);
				},
				onRowSelect : function (rowId, celInd) {
					if (this.getRowData(rowId).prmtType == 'C') {
						mvno.ui.updateSection("GRID2", "title", "사은품 (총 선택 개수 제한: " + this.getRowData(rowId).choiceLimit + "개)");
					} else {
						mvno.ui.updateSection("GRID2", "title", "사은품");
					}
					PAGE.GRID2.list(ROOT + "/gift/custgmt/custGiftList.json", this.getRowData(rowId));
					
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						
						case "btnMod":
							
							mvno.ui.createForm("FORM3");
							mvno.ui.createGrid("GRID3");	// 사은품제품
							mvno.ui.createForm("FORM4");
							mvno.ui.createForm("FORM3btn");

							if (selectedData.prmtType == 'C') {
								mvno.ui.updateSection("GRID3", "title", "사은품 (총 선택 개수 제한: " + selectedData.choiceLimit + "개)");
							} else {
								mvno.ui.updateSection("GRID3", "title", "사은품");
							}
							
							PAGE.FORM3.clear();
							PAGE.FORM4.clear();
							PAGE.GRID3.clearAll();

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM3, "telFn1");// 전화번호 지역번호
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM3, "telFn2");// 전화번호 지역번호
							
							PAGE.GRID3.list(ROOT + "/gift/custgmt/custGiftPrmtPrdtList.json", selectedData);

							var data = selectedData;
							PAGE.FORM3.setFormData(data); 
							
							
							PAGE.FORM3.clearChanged();
							
							mvno.ui.popupWindowById("POPUP2", "수정화면", 460, 770, {  //350, 330
								onClose: function(win) {
									//if (! PAGE.FORM3.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
			                           	    win.closeForce();
									});
								}
							
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
							}else{
								/*
								var searchData =  PAGE.FORM1.getFormData(true);
								mvno.cmn.download(ROOT + "/gift/custgmt/custGiftMngListEx.json?menuId=GIFT100031", true, {postData:searchData});							
								*/
								mvno.cmn.downloadCallback(function(result) {
									PAGE.FORM1.setItemValue("DWNLD_RSN",result);
									mvno.cmn.ajax(ROOT + "/gift/custgmt/getCustGiftMngListExcelToBatch.json?menuId=GIFT100031", PAGE.FORM1.getFormData(true), function(result){
										mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다."); 
									});
								});

							}
							
							break;							
							
						case "btnDnExcelPrdt":
							var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
								return; //버튼 최초 클릭일때만 조회가능하도록
							}
							
							if(PAGE.GRID1.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
							}else{
								mvno.cmn.downloadCallback(function(result) {
									PAGE.FORM1.setItemValue("DWNLD_RSN",result);
									mvno.cmn.ajax(ROOT + "/gift/custgmt/custGiftMngProdListExcelToBatch.json?menuId=GIFT100031", PAGE.FORM1.getFormData(true), function(result){
										mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다."); 
									});
								});
							}
							
							//var searchData =  PAGE.FORM1.getFormData(true);
							//mvno.cmn.download(ROOT + "/gift/custgmt/custGiftMngListEx2.json?menuId=GIFT100031", true, {postData:searchData});
							break;							
	
					}
				
				}
			},
			
			// --------------------------------------------------------------------------
			//수정화면
			FORM3 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0, list: [
						{type:'input', name:'contractNum', label:'계약번호', width:160, disabled:true, required: true, maxLength:10},
						{type:'input', name:'subLinkName', label:'고객명', width:160, disabled:true, required: true, maxLength:60},

						{type:'input', name:'subscriberNo', label:'전화번호', width:160, disabled:true, required: true, maxLength:13},
						
						{type:'input', name:'mngNm', label:'받는분 고객명', width:160, required: true, maxLength:50},
					 ]},

					 {type:"block", list:[
						{type:"select", label:"연락처1", name:"telFn1", width:75, required: true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telMn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4, validate:"ValidInteger"},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telRn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4, validate:"ValidInteger"}
					 ]},

					 {type:"block", list:[
						{type:"select", label:"연락처2", name:"telFn2", width:75},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telMn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4, validate:"ValidInteger"},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telRn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4, validate:"ValidInteger"}
						
					 ]},
					 {type:"block", list:[
						{type:"input", label:"주소", name:"post", width:75, readonly:true, maxLength:6},
						{type:"newcolumn"},
						{type:"button", value:"우편번호찾기", name:"btnPostFind"},
						{type:"newcolumn"},
						{type:"input", label:" ", name:"addr", labelWidth:100, width:270, readonly:true, maxLength:83},
						{type:"newcolumn"}
					]},
					{type:"block", list:[
						 {type:"input", label:"상세주소", name:"addrDtl", width:270, maxLength:83},
						 {type:'input', label:"프로모션", name:"prmtNm", width:270, disabled:true, required: true, style:"background-color:rgb(253,211,198)"},
						 {type:'input', label:"프로모션 유형", name:"prmtTypeNm", width:160, disabled:true, style:"background-color:rgb(253,211,198)"},
						 {type : 'hidden', name: "itemData", value:""},
						 {type : 'hidden', name: "prmtId", value:""},
						 {type : 'hidden', name: "contractNum", value:""}  
					]}
				],
				buttons : {
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
 					}
				},
				onValidateMore : function (data){

					if(data.telFn1.length < 2){

						this.pushError("telFn1", "지역번호", "지역번호(통신사번호)를 입력하세요");
					}
					
					if(data.telMn1.length < 3){

						this.pushError("telMn1", "국번호", "국번호를 입력하세요");
					}
					
					if(data.telRn1.length < 4){

						this.pushError("telRn1", "번호", "번호를 입력하세요");
					}
					
					/*

					if(data.telFn2.length > 0 && data.telFn2.length < 3){

						this.pushError("telFn2", "통신사번호", "통신사번호를 입력하세요");
					}
					
					if(data.telMn2.length > 0 && data.telMn2.length < 3){

						this.pushError("telMn2", "번호", "국번호를 입력하세요");
					}
					
					if(data.telRn2.length > 0 && data.telRn2.length < 4){

						this.pushError("telRn2", "번호", "번호를 입력하세요");
					}
					*/
				}
			},	

			GRID3 : {
				css : {
					height : "200px"
					/* ,width : "275px" */
				}
				,title : "사은품"
				,header : "선택,사은품명,가격,제품ID,중복여부,수량"
				,columnIds : "rowCheck,prdtNm,outUnitPric,prdtId,dupYn,quantity"
				,colAlign : "center,left,right,center,center,right"
				,colTypes : "ch,coro,ron,ro,ro,edn"
				,colSorting : "str,str,str,str,str,str"
				,widths : "50,180,60,10,10,100"
				,paging: false
				,hiddenColumns : [3,4]
				,buttons : {

				}

				,onValidateMore : function (data){
					
				}
				,onButtonClick : function(btnId) {
					
					var grid = this;
				
				}
				
			},	//GRID3 End
			
			FORM3btn : {
				buttons : {
					center : {
						btnSave : { label : "저장" },
						btnClose : { label: "닫기" }
					}
				},
				
				onButtonClick : function(btnId) {
					var form = PAGE.FORM3; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":

							// 사은품제품
							var arrVas = [];

							
							var arrVasData = PAGE.GRID3.getCheckedRowData();
							var totalQuantity = 0;
							for(var idx=0; idx<arrVasData.length; idx++) {
								
								var string = arrVasData[idx].quantity;
								var no=string.replace(/[^0-9]/g,'');
								arrVasData[idx].quantity = parseInt(no);

								
								
								if(arrVasData[idx].quantity == 0 || isNaN(arrVasData[idx].quantity)){
									mvno.ui.alert("수량을 입력해야 합니다.");
									return;
								} else if (arrVasData[idx].quantity > 999999999) {
									mvno.ui.alert("수량은 9자리 이하로 입력해야 합니다.");
									return;
								}
								totalQuantity += parseInt(arrVasData[idx].quantity);
								arrVas.push(arrVasData[idx]);
							}
							// 사은품 data, 수정 사유(결과 이력)
							var itemData = JSON.stringify({'ALL':arrVas, 'rvisnRsn': PAGE.FORM4.getItemValue('rvisnRsn')});
							form.setItemValue("itemData",itemData);
							
							// 선택 사은품일 경우 총 선택 개수 보다 수량을 크게 입력했을 경우 confirm 창을 띄운다.
							if (form.getFormData().prmtType == 'C' && form.getFormData().choiceLimit < totalQuantity) {
								
								var confirmMsg = "총 선택 개수 제한은 " + form.getFormData().choiceLimit + "개입니다.<br/>현재 " + totalQuantity + "개가 선택되었습니다. 저장하시겠습니까?";
								
								mvno.cmn.ajax4confirm(confirmMsg, ROOT + "/gift/custgmt/custGiftInsert.json", form, function(result) {
									if (result.result.code == "OK") {
										mvno.ui.closeWindowById(form, true);
											mvno.ui.notify("NCMN0002");
											PAGE.GRID1.refresh();
											PAGE.GRID2.clearAll();
									} else {
										mvno.ui.alert(result.result.msg);
									}
								});
							} else {
								mvno.cmn.ajax(ROOT + "/gift/custgmt/custGiftInsert.json", form, function(result) {
									if (result.result.code == "OK") {
										mvno.ui.closeWindowById(form, true);
											mvno.ui.notify("NCMN0002");
											PAGE.GRID1.refresh();
											PAGE.GRID2.clearAll();
									} else {
										mvno.ui.alert(result.result.msg);
									}
								});
							}
							
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;

					}
				},
				onValidateMore : function (data){

				}
			},	
						
			GRID2 : {
				css : {
					height : "150px"
				},
				title : "사은품 정보",
				header : "제품ID,제품명,제조사,단가,수량,합계금액",	//5
				columnIds : "prdtId,prdtNm,maker,outUnitPric,quantity,total",
				widths : "180,200,120,180,70,180",
				colAlign : "center,,center,right,right,right",
				colTypes : "ro,ro,ro,ron,ron,ron",
				colSorting : "str,,str,str,str,str",
				paging : true,
				pageSize:20,
				buttons : {

				},
				onButtonClick : function(btnId, selectedData) {

				}	
			},

			FORM4 : {
				title : "수정 사유",
				items : [
					 {type:'settings', inputWidth:'auto'},
					 {type:"block", blockOffset:0, list: [
						{type:'input', name:'rvisnRsn', width:384, maxLength:300}
					 ]}
				]
			}

	};

	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createGrid("GRID2");

				var time = new Date().getTime();
				time = time - 1000 * 3600 * 24 * 7;
				var frDay = new Date(time).format("yyyymmdd");

				PAGE.FORM1.setItemValue("frDate",frDay);
				PAGE.FORM1.setItemValue("toDate",today);

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"GIFT001", orderBy:"CD_VAL"}, PAGE.FORM1, "searchGbn"); // 사용여부
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0268"}, PAGE.FORM1, "prmtOrgnId");	
				
			}
			
	};
	
	function myCallBack( state, fileName , fileSize){
		console.log("------------myCallBack---------");
		
	}
	

	/* 주소 setting */
	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
		
		if (jusoGubun == "Mod"){
			PAGE.FORM3.setItemValue("post",zipNo);
			PAGE.FORM3.setItemValue("addr",roadAddrPart1);
			PAGE.FORM3.setItemValue("addrDtl",roadAddrPart2 + addrDetail);
		}
		
		jusoGubun = "";
		
		
		
	}
</script>
