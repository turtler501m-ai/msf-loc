<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>



<!-- Script -->
<script type="text/javascript">

var maskingYn = "${maskingYn}";				// 마스킹권한

	var DHX = {
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"srchStrtDt", label:"약정시작일자", labelWidth:80, calendarPosition:"bottom", inputWidth : 100, value: "${startDate}"} 
					, {type:"newcolumn", offset:3}
					, {type: "label", label:"~", labelWidth:10, labelAlign:"center"}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"srchEndDt", calendarPosition:"bottom", inputWidth:100, value: "${endDate}"}
					, {type:"newcolumn"}
					, {type:"select", label:"계약상태", name:"subStatus", width:80, offsetLeft:57}
					, {type:"newcolumn"}
					, {type:"select", label:"재약정경로", name:"orderType", width:80, offsetLeft:57, options:[{text: "- 전체 -", value: ""},{text: "홈페이지", value: "01"},{text: "SMS", value: "02"}]}
				]},
				{type : "block",
					list : [
						, {type : "select",width : 114 , label : "검색구분",name : "searchGbn" ,  labelWidth:80}
						, {type : "newcolumn"}
						, {type : "input",width : 100,name : "searchName"}
						, {type : "newcolumn"}
						, {type : "select", label : "모집경로", width : 80, offsetLeft : 57, name : "onOffType"}
					]
				},
				  {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}, 
				  {type : 'hidden', name: "DWNLD_RSN", value:""}
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getStatsSimList.json", form);
					break;
				}
			}
			, onValidateMore : function(data){
				if(data.searchGbn == "" ){
					if(data.srchStrtDt == ''){
						this.pushError("srchStrtDt", "약정시작일자", "시작일자는 필수 입력 항목입니다.");
						
					}
					if(data.srchEndDt == ''){
						this.pushError("srchEndDt", "약정시작일자", "종료일자는 필수 입력 항목입니다.");
						
					}
					if(data.srchEndDt > data.srchEndDt){
						this.pushError("srchStrtDt", "약정시작일자", "종료일자보다 시작일자 값이 큽니다.");
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
				height : "570px"
			}
			, title : "조회결과"
			, header : "계약번호,고객명,개통일자,해지일자,최초개통단말,최초요금제,가입대리점,나이(만),성별,데이터유형,구매유형,계약상태,심플약정시작일자,심플약정상태,심플약정기간,심플약정종료일자,모집경로,사은품,,,,,,," //24
			, columnIds : "contractNum,cstmrNm,lstComActvDate,tmntDt,fstModelInfo,fstRateNm,openAgntNm,age,sexNm,dataType,reqBuyType,subStatusNm,simStrtDt,simStatNm,simEnggCnt,simEndDt,onOffType,presentNm,dlvryName,phonCtn,dlvryPost,dlvryAddr,dlvryAddrDtl,agrmMemo,seq"
			, widths : "100,100,100,100,150,160,150,70,60,90,100,90,110,110,100,110,80,200,0,0,0,0,0,0,0" //24
			, colAlign : "center,Left,center,center,Left,Left,Left,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns : [18,19,20,21,22,23,24]
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnDnExcel : { label : "자료생성" }
				}
			},
			checkSelectedButtons : ["btnMod"],
			onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리?
				this.callEvent('onButtonClick', ['btnMod']);
			},
			onButtonClick : function(btnId , selectedData) {
				var form = this;
				switch (btnId) {
					case "btnDnExcel":
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							var srchStrtDt = searchData.srchStrtDt;
							var srchEndDt = searchData.srchEndDt;
							var searchGbn = searchData.searchGbn;
							var searchName = searchData.searchName;
							if((srchStrtDt != null && srchStrtDt != '' && srchEndDt != null && srchEndDt != '') || (searchGbn != null && searchGbn != '' && searchName != null && searchName != '')){
                                mvno.cmn.downloadCallback(function(result) {
									PAGE.FORM1.setItemValue("DWNLD_RSN",result);
									mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getStatsSimListExcel.json?menuId=MSP1000109", PAGE.FORM1.getFormData(true), function(result){
										mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
									});
								});
							}else{
								mvno.ui.alert("검색조건이 맞지 않습니다.");
								return;
							}
						}
						
					break;
					
					case "btnMod":
						
						if(selectedData.seq == null || selectedData.seq == ""){
							mvno.ui.alert("배송정보를 수정할 수 없는 고객입니다.");
							return;
						}
						
						var today = new Date().format("yyyymmdd");
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.setFormData(selectedData);
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM2, "phonCtn1"); // 전화번호
						
						if(maskingYn == "0"){
							PAGE.FORM2.disableItem("dlvryName");
							PAGE.FORM2.disableItem("phonCtn1");
							PAGE.FORM2.disableItem("phonCtn2");
							PAGE.FORM2.disableItem("phonCtn3");
							PAGE.FORM2.disableItem("dlvryPost");
							PAGE.FORM2.disableItem("btnDlvryPostFind");
							PAGE.FORM2.disableItem("dlvryAddr");
							PAGE.FORM2.disableItem("dlvryAddrDtl");
							PAGE.FORM2.disableItem("agrmMemo");
							mvno.ui.hideButton("FORM2", "btnSave");
							mvno.ui.hideButton("FORM2", "btnClose");
														
						}else{
							PAGE.FORM2.enableItem("dlvryName");
							PAGE.FORM2.enableItem("phonCtn1");
							PAGE.FORM2.enableItem("phonCtn2");
							PAGE.FORM2.enableItem("phonCtn3");
							PAGE.FORM2.enableItem("dlvryPost");
							PAGE.FORM2.enableItem("btnDlvryPostFind");
							PAGE.FORM2.enableItem("dlvryAddr");
							PAGE.FORM2.enableItem("dlvryAddrDtl");
							PAGE.FORM2.enableItem("agrmMemo");
							mvno.ui.showButton("FORM2", "btnSave");
							mvno.ui.showButton("FORM2", "btnClose");
						}
						
						
						var data = selectedData;												
						setInputDataAddHyphen(PAGE.FORM2, data, "phonCtn", "tel");
						
						 PAGE.FORM2.clearChanged();
						mvno.ui.popupWindowById("FORM2", "상세화면", 710, 250, {
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
			title : "",
			items : [
				{type: 'settings', position: 'label-left', labelWidth: '100', inputWidth: 'auto'},
				{type:"block", list:[
				,{type: "fieldset", label: "배송정보", inputWidth:660, list:[
					{type: "block", blockOffset: 0, offsetLeft: 30,list: [
						{type: 'input', name: 'dlvryName', label: '받으시는 분',  width: 150,  value: '' , labelWidth:70}
						,{type: "newcolumn"}
						,{type:"select", width:75, label:"휴대폰", name:"phonCtn1",offsetLeft:112 , labelWidth:70}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"",name:"phonCtn2",maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"",name:"phonCtn3",maxLength:4}
					   ]},
						{type: "block", blockOffset: 0,offsetLeft: 30, list: [
							{type:"input", label:"우편번호", name:"dlvryPost", labelWidth:70, width:75, readonly:true, maxLength:6}
							,{type:"newcolumn"}
							,{type:"button", value: "주소찾기", name :"btnDlvryPostFind"}
							,{type:"newcolumn"}
							,{type:"input", label:"", name:"dlvryAddr", width:347, readonly:true, maxLength:80}
						]},
						{type: "block", blockOffset: 0,offsetLeft: 30, list: [
							{type:"input", label:"상세주소", name:"dlvryAddrDtl", labelWidth:70, width:500, maxLength:50}
						]},
						{type: "block", blockOffset: 0,offsetLeft: 30, list: [
							{type:"input", label:"메모", name:"agrmMemo", labelWidth:70, width:500, maxLength:50}
						]}
					]}
				]}
				, {type:"hidden", name:"seq"}
				, {type:"hidden", name:"contractNum"}
			],
			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
					}
				},
				
				 onButtonClick : function(btnId) {

	                 var form = this; // 혼란방지용으로 미리 설정 (권고)
	                 
	                 switch (btnId) {

	                     case "btnSave":
	                         mvno.cmn.ajax(ROOT + "/stats/statsMgmt/updateDlvryInfo.json", form, function(result) {
	                             mvno.ui.closeWindowById(form, true);  
	                             mvno.ui.notify("NCMN0002");
	                             PAGE.GRID1.refresh();
	                         });
	                         break;

	                     case "btnDlvryPostFind":
	                    	 jusoGubun = "MOD";
	                         mvno.ui.codefinder4ZipCd("FORM2", "dlvryPost", "dlvryAddr", "dlvryAddrDtl");
	                         break;  
	                         
	                     case "btnClose" :
	                         
	                         mvno.ui.closeWindowById(form);   
	                         break;                          

	                 }

	             },
	             onValidateMore : function (data){
	            	 if(!mvno.etc.checkPackNum(data.phonCtn1, data.phonCtn2 , data.phonCtn3)){
	                     
	                     this.pushError("data.phonCtn2","전화번호",["ICMN0011"]);
	                 } 
	            	 else if(data.phonCtn1 && !mvno.etc.checkPhoneNumber(data.phonCtn1+data.phonCtn2+data.phonCtn3)){
	                     
	                     this.pushError("data.phonCtn3","전화번호",["ICMN0012"]);
	                 }
	             }
				
	 		}
		}
	;
	
	var PAGE = {
		title: "${breadCrumb.title}"
		, breadcrumb: "${breadCrumb.breadCrumb}"
		, buttonAuth: ${buttonAuth.jsonString}
		, onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0037"}, PAGE.FORM1, "subStatus"); // 계약상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2021"}, PAGE.FORM1, "searchGbn"); // 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType"); // 모집경로
			
		}
	};
	
	 function setInputDataAddHyphen(form, data, itemname, type){
		  
	      if(!form) return null;
	      if(data == null) return null;
	      if(!itemname) return data;
	       
	       var retValue  = "";
	       if(type == "tel"){
	          retValue = mvno.etc.setTelNumHyphen(data[itemname]);
	          
	      }
	       var arrStr = retValue.split("-");
	       var cnt = 1;
	       for(i = 0; i < arrStr.length; i++){
	           form.setItemValue(itemname+cnt,arrStr[i]);
	           cnt++;
	       }
	   }
	  
		/* 주소 setting */
		function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
			 if(jusoGubun == "MOD"){
				PAGE.FORM2.setItemValue("dlvryPost",zipNo);
				PAGE.FORM2.setItemValue("dlvryAddr",roadAddrPart1);
				PAGE.FORM2.setItemValue("dlvryAddrDtl",addrDetail);
			}		
			jusoGubun = "";
		}
</script>