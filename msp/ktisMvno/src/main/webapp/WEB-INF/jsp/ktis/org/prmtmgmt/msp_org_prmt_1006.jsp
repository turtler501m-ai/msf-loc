<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

/**
 * @Class Name : msp_org_prmt_1006.jsp
 * @Description : 평생할인 프로모션 등록
 * @author : 김동혁
 * @Create Date : 2023.10.05 
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>

<div class="row">
	<div id="GRID1" class="c2"></div>
	<div id="GRID2" class="c4"></div>
	<div id="GRID3" class="c4"></div>
</div>

<div id="GROUP1" style="display: none;">
	<div id="FORM10" class="section-search"></div>
	<div class="row">
		<div id="GRID11" class="c5"></div>
		<div id="GRID12" class="c5"></div>
	</div>
</div>

<div id="GROUP2" style="display: none;">
	<div id="FORM20" class="section-search"></div>
	<div class="row">
		<div id="GRID21" class="c5"></div>
		<div id="GRID22" class="c5"></div>
	</div>
</div>

<div id="GROUP3" style="display: none;">
	<div id="FORM30" class="section-search"></div>
	<div class="row">
		<div id="GRID31" class="c5"></div>
		<div id="GRID32" class="c5"></div>
	</div>
</div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date().format("yyyymmdd");

	var endDt = mvno.cmn.getAddDay(today, 30);
	var DHX = {
		//검색
		FORM1 : {
			items : [
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 80, blockOffset : 0 }
				,{ type : "block", name: "INFO", labelWidth : 30 , list : [
					{type:"select", label:"채널유형", name:"chnlTp", value: "${result.chnlTp}",required: true, width:165}
					, {type: "newcolumn", offset:15}
					, {type:"input",  label:"프로모션명", name:"prmtId", value : "${result.prmtId}" ,width:120 , disabled : true}
					, {type: 'newcolumn'}
					, {type:"input",  label:"", name: "prmtNm", value : "${result.prmtNm}", width:432}
				]}
				,{ type : "block", labelWidth : 30 , list : [
					  {type: 'calendar',label:'프로모션시작', name: 'strtDt',value: "${result.strtDt}", dateFormat: '%Y-%m-%d'  , serverDateFormat: '%Y%m%d',  calendarPosition: 'bottom', width:100}
					, {type: 'newcolumn'}
					, {type: "input", 	label:""		, name: "strtTm",value: "${result.strtTm}", validate: "ValidInteger", maxLength:6, width: 60}
					, {type: 'newcolumn'}
					, {type: 'calendar',label:'프로모션종료', name: 'endDt', value: "${result.endDt}" , dateFormat: '%Y-%m-%d'  , serverDateFormat: '%Y%m%d',  calendarPosition: 'bottom', width:120, labelWidth:80,  offsetLeft:14}
					, {type: 'newcolumn'}
					, {type: "input", 	label:""		, name: "endTm", value: "${result.endTm}",  validate: "ValidInteger", maxLength:6, width: 60}
					, {type: 'newcolumn'}
					, {type:"select",   label:"업무구분"   , name:"evntCd", value: "${result.evntCd}",  labelWidth:60, offsetLeft:10, required: true,width:85}
					, {type: 'newcolumn'}
					, {type:"select",   label:"가입유형"   , name:"slsTp",  value: "${result.slsTp}",labelWidth:60, offsetLeft:10, required: true, width:135}
				]}
				,{ type : "block", name: "JOIN", labelWidth : 30, list : [
					 {type:"select", width:165, label:"개통단말기", name:"intmYn", value : "${result.intmYn}", required: true, 
						 options:[{value:"N", text:"전체"}, {value:"Y", text:"특정단말"}]}
					, {type: 'newcolumn'}
					, {type:"block", name:"modelSearch", list: [
						{type : "input",label : "대표모델ID",name : "modelId" ,width:110, value:"${result.modelId}" , offsetLeft:15, labelWidth:80, maxLength:10}
						, {type: 'newcolumn'}
						, {type: "button", name: 'btnModelFind', value:"검색"}
						, {type: 'newcolumn'}
						, {type:"input", name:"modelNm", value:"${result.modelNm}", width:150, readonly: true}
					]}
				]}
				,{ type : "block", name: "ENGG", labelWidth : 30 , list : [
					{type: "label", label: "약정기간" ,required: true}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "전체",  name: "enggCntAll", width:20, labelWidth : 40, offsetLeft:10}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "무약정",  name: "enggCnt0",   checked : "${result.enggCnt0}" =='Y' ? true : false , width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "12개월",  name: "enggCnt12", checked : "${result.enggCnt12}" =='Y' ? true : false , width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "18개월",  name: "enggCnt18", checked : "${result.enggCnt18}" =='Y' ? true : false , width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "24개월",  name: "enggCnt24", checked : "${result.enggCnt24}" =='Y' ? true : false , width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "30개월",  name: "enggCnt30", checked : "${result.enggCnt30}" =='Y' ? true : false , width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "36개월",  name: "enggCnt36", checked : "${result.enggCnt36}" =='Y' ? true : false , width:20, labelWidth : 50, offsetLeft:20}
					, {type:"hidden", label:"사용여부", name:"usgYn", value: "${result.usgYn}", width:10}
				]}
			]
			,onChange : function(name, value) {
				var form = this;
				
				form1Change(form, name);
			}
			,onButtonClick : function(btnId) {
				
				var form = this;

				switch (btnId) {
					case "btnModelFind" :	 //대표모델 찾기 팝업
						mvno.ui.codefinder("RPRSMLDSET", function(result) {
							if(result.prdtTypeCd == "02"){
								mvno.ui.alert("USIM은 선택할수 없습니다");
								return;
							}
							// 유심정보가 넘어올경우 set하지 않고 alert 띄우기
							
							form.setItemValue("modelId", result.rprsPrdtId);
							form.setItemValue("modelNm", result.prdtNm);
						});
					
					break;
				}
			}
		}//FORM1 End
		, GRID1 : {
			css : {
				height : "300px"
				,width : "190px" 
			}
			,title : "대상조직"
			,header : "조직ID,조직명,조직유형,조직상태"
			,columnIds : "orgnId,orgnNm,typeNm,statNm"
			,colAlign : "center,left,left,center"
			,colTypes : "ro,ro,ro,ro"
			,colSorting : "str,str,str,str"
			,widths : "120,170,100,80"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [0,2,3]
			,buttons : {
				hright : {
					btnSearch : { label : "찾기" }
				}
			},
			onButtonClick : function(btnId) {

				var grid = this;
				
				switch(btnId){
					case "btnSearch" :

						mvno.ui.createForm("FORM10");
						mvno.ui.createGrid("GRID11");
						mvno.ui.createGrid("GRID12");
						
						mvno.ui.popupWindowById("GROUP1", "조직 검색", 1000, 600, {
								onClose: function(win) {
									if (!PAGE.FORM10.isChanged() || !PAGE.GRID11.isChanged() || !PAGE.GRID12.isChanged()){
										return true;
									}
									mvno.ui.confirm("CCMN0005", function() {
										win.closeForce();
									});
								}
						});
						PAGE.FORM10.clear();
						PAGE.GRID11.clearAll();
						PAGE.GRID12.clearAll();
						
						PAGE.GRID11.list(ROOT + "/org/prmtmgmt/getDisPrmtOrgnList.json", "", { callback : function (){
							for(var i=0; i < grid.getRowsNum(); i++){
								var gridData=grid.getRowData(grid.getRowId(i));
								PAGE.GRID12.appendRow({rowCheck: 1, orgnId : gridData.orgnId, orgnNm : gridData.orgnNm, typeNm : gridData.typeNm, statNm : gridData.statNm });
							}
						}});
					break;
				}
			}
		}//GRID1 End
		, GRID2 : {
			css : {
				height : "300px"
				,width : "360px" 
			}
			,title : "대상요금제"
			,header : "요금제코드,요금제명,기본료"
			,columnIds : "rateCd,rateNm,baseAmt"
			,colAlign : "center,left,right"
			,colTypes : "ro,ro,ron"
			,colSorting : "str,str,int"
			,widths : "120,270,70"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [0]
			,buttons : {
				hright : {
					btnSearch : { label : "찾기" }
				}
			},
			onButtonClick : function(btnId) {

				var grid = this;
							
				switch(btnId){
					case "btnSearch" :
						
						mvno.ui.createForm("FORM20");
						mvno.ui.createGrid("GRID21");
						mvno.ui.createGrid("GRID22");
						
						mvno.ui.popupWindowById("GROUP2", "요금제 검색", 1000, 600, {
								onClose: function(win) {
									if (!PAGE.FORM20.isChanged() || !PAGE.GRID21.isChanged() || !PAGE.GRID22.isChanged()){
										return true;
									}
									mvno.ui.confirm("CCMN0005", function() {
										win.closeForce();
									});
								}
						});
						PAGE.FORM20.clear();
						PAGE.GRID21.clearAll();
						PAGE.GRID22.clearAll();
						
						PAGE.GRID21.list(ROOT + "/org/prmtmgmt/getDisPrmtSocList.json", "", { callback : function (){
							for(var i=0; i < grid.getRowsNum(); i++){
								var gridData=grid.getRowData(grid.getRowId(i));
								PAGE.GRID22.appendRow({rowCheck: 1, rateCd : gridData.rateCd, rateNm : gridData.rateNm, baseAmt : gridData.baseAmt });
							}
						}});
						
					
					break;
				}
			}
		}//GRID2 End
		, GRID3 : {
			
			css : {
				height : "300px"
				,width : "360px" 
			}
			,title : "대상부가서비스"
			,header : "부가서비스명,기본료,부가서비스"
			,columnIds : "addNm,disAmt,soc"
			,colAlign : "left,right,center"
			,colTypes : "coro,ron,ro"
			,colSorting : "str,str,str"
			,widths : "270,70,10"
			,paging: false
			,hiddenColumns : [2]
			,buttons : {
				hright : {
					btnSearch : { label : "찾기" }
				}
				,right : {
					btnReg : { label : "저장" }
				}
			}
			,onButtonClick : function(btnId) {
			
				var grid = this;
				
				switch(btnId){
				
					case "btnSearch" :
						
						mvno.ui.createForm("FORM30");
						mvno.ui.createGrid("GRID31");
						mvno.ui.createGrid("GRID32");
						
						mvno.ui.popupWindowById("GROUP3", "부가서비스 검색", 1000, 600, {
								onClose: function(win) {
									if (!PAGE.FORM30.isChanged() || !PAGE.GRID31.isChanged() || !PAGE.GRID32.isChanged()){
										return true;
									}
									mvno.ui.confirm("CCMN0005", function() {
										win.closeForce();
									});
								}
						});
						PAGE.FORM30.clear();
						PAGE.GRID31.clearAll();
						PAGE.GRID32.clearAll();
						
						PAGE.GRID31.list(ROOT + "/org/prmtmgmt/getDisPrmtAddList.json", "", { callback : function (){
							for(var i = 0; i < grid.getRowsNum(); i++){
								var gridData=grid.getRowData(grid.getRowId(i));
								PAGE.GRID32.appendRow({rowCheck: 1, addNm : gridData.addNm, disAmt : gridData.disAmt, soc : gridData.soc });
							}
						}});
						
						
					break;
					
					case "btnReg" :
						
						var form = PAGE.FORM1.getFormData();
						// 1. prmtNm 체크
						if(mvno.cmn.isEmpty(form.prmtNm)){
							mvno.ui.alert("프로모션명을 입력하세요.");
							return;
						}
						
						var now = new Date().format("yyyymmddhhmiss");

						// 시작일자 유효성
						if(form.strtTm.length < 6 || form.endTm.length < 6 ){
							mvno.ui.alert("시작/종료 시간은 6자리로 입력해주세요.");
							return;
						}

						if(form.strtDt == null || form.strtDt== ''){
							mvno.ui.alert("시작일자를 입력해주세요");
							return;
						}

						if(form.strtTm == null || form.strtTm== '' || form.strtTm.length < 1){
							mvno.ui.alert("시작시간(시분초)을 입력해주세요");
							return;
						}

						// 종료일자 유효성
						if(form.endDt == null || form.endDt== ''){
							mvno.ui.alert("종료일자를 입력해주세요");
							return;
						}

						if(form.endTm == null || form.endTm== '' || form.endTm.length < 1){
							mvno.ui.alert("종료시간(시분초)을 입력해주세요");
							return;
						}

						var strtDttm = form.strtDt.format("yyyymmdd")+form.strtTm;
						var endDttm = form.endDt.format("yyyymmdd")+form.endTm;
						
						// 2. strtDttm, endDttm 체크
						if(mvno.cmn.isEmpty(strtDttm) || mvno.cmn.isEmpty(endDttm)){
							mvno.ui.alert("프로모션 시작일시, 종료일시를 입력하세요.");
							return;
						}
						
						if(Number(form.strtTm) <0 || Number(form.strtTm) > 235959) {
							mvno.ui.alert("정확한 시작시간(시분초) 을 입력하세요");
							return;
						}
						
						if(Number(form.endTm) <0 || Number(form.endTm) > 235959) {
							mvno.ui.alert("정확한 종료시간(시분초) 을 입력하세요");
							return;
						}
						
						if(strtDttm < now) {
							mvno.ui.alert("시작일시 ["  +strtDttm+ "] 는 현재["  +now+ "]  이전을 선택할 수 없습니다.");
							return;
						}
						if(strtDttm > endDttm) {
							mvno.ui.alert("종료일시 [" +endDttm+ "] 가 시작일시["  +strtDttm+ "] 전입니다.종료일시를 변경하세요.");
							return;
						}
						// 3. chnlTp 체크
						if(mvno.cmn.isEmpty(form.chnlTp)){
							mvno.ui.alert("채널유형을 선택하세요.");
							return;
						}
						// 4. evntCd 체크
						if(mvno.cmn.isEmpty(form.evntCd)){
							mvno.ui.alert("업무구분을 선택하세요.");
							return;
						}
						
						// 5. slsTp 체크
						if(mvno.cmn.isEmpty(form.slsTp)){
							mvno.ui.alert("가입유형을 선택하세요.");
							return;
						}
						
						// 6. intmYn 체크
						if(mvno.cmn.isEmpty(form.intmYn)){
							mvno.ui.alert("개통단말을 선택하세요.");
							return;
						}
						
						// 7. modelId 체크
						if(form.intmYn == "Y" && mvno.cmn.isEmpty(form.modelId )) {
							mvno.ui.alert("특정단말을 선택해 주십시오.");
							return;
						}
						
						// 8. enggCnt 체크
						if(form.enggCnt0 == 0 && form.enggCnt12 == 0
							&& form.enggCnt18 == 0 && form.enggCnt24 == 0
							&& form.enggCnt30 == 0 && form.enggCnt36 == 0){
							mvno.ui.alert("약정기간을 선택해 주십시오.");
							return;
						}
						
						if(PAGE.GRID1.getRowsNum() < 1){
							mvno.ui.alert("대상조직을 선택하세요.");
							return;
						}
						
						if(PAGE.GRID2.getRowsNum() < 1){
							mvno.ui.alert("대상요금제를 선택하세요.");
							return;
						}
						
						if(PAGE.GRID3.getRowsNum() < 1){
							mvno.ui.alert("대상부가서비스를 선택하세요.");
							return;
						}
						
						mvno.ui.confirm("프로모션을 저장하시겠습니까?", function() {
							// 조직
							var arrOrgn = [];
							
							var rowOrgn = PAGE.GRID1.getAllRowIds();
							var arrOrgnData = PAGE.GRID1.getRowData(rowOrgn, true);
							for(var idx = 0; idx < arrOrgnData.length; idx++) {
								arrOrgn.push(arrOrgnData[idx].orgnId);
							}
							// 요금제
							var arrSoc = [];
							
							var rowSoc = PAGE.GRID2.getAllRowIds();
							
							var arrSocData = PAGE.GRID2.getRowData(rowSoc, true);
							
							for(var idx = 0; idx < arrSocData.length; idx++) {
								arrSoc.push(arrSocData[idx].rateCd);
							}
							
							// 부가서비스
							var arrAdd = [];
							
							var rowAdd = PAGE.GRID3.getAllRowIds();
			
							var arrAddData = PAGE.GRID3.getRowData(rowAdd, true);
							
							for(var idx = 0; idx < arrAddData.length; idx++) {
								arrAdd.push(arrAddData[idx].soc);
							}
							
							var sData = {
									prmtNm : form.prmtNm
									, strtDttm : strtDttm
									, endDttm : endDttm
									, chnlTp : form.chnlTp
									, evntCd : form.evntCd
									, slsTp : form.slsTp
									, intmYn : form.intmYn
									, modelId : form.modelId
									, modelNm : form.modelNm
									, enggCnt0 : form.enggCnt0
									, enggCnt12 : form.enggCnt12
									, enggCnt18 : form.enggCnt18
									, enggCnt24 : form.enggCnt24
									, enggCnt30 : form.enggCnt30
									, enggCnt36 : form.enggCnt36
									, orgnList :arrOrgn
									, socList :arrSoc
									, addList :arrAdd
							};
																					
							mvno.cmn.ajax4json(ROOT + "/org/prmtMgmt/regDisPrmtInfo.json", sData, function(result) {
								if(result.result.code =="OK"){
									mvno.ui.alert("NCMN0004","",function(){
										var url = "/org/prmtMgmt/getDisPrmtNew.do";
										var myTabbar = window.parent.mainTabbar;
										myTabbar.tabs(url).close();
									});
								}
							});
						});
						
					break;
				}
			}
		}	//GRID3 End
		, GRID4 : {
			css : {
				width : "950px"
				,height : "150px"
			}
			,title : "상세정보"
			,header : "조직ID,조직명,요금제코드,요금제명,부가서비스코드,부가서비스명,기본료"
			,columnIds : "orgnId,orgnNm,rateCd,rateNm,soc,addNm,disAmt"
			,colAlign : "center,left,center,left,center,left,right"
			,colTypes : "ro,ro,ro,ro,ro,ro,ron"
			,colSorting : "str,str,str,str,str,str,str"
			,widths : "75,150,75,270,90,200,70"
			,paging: false
// 			,hiddenColumns : [7,8]
			,buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				}
				,right : {
					btnEndDt : { label : "종료일시변경" }
					,btnEnd : { label : "삭제" }
				}
			}
			,onButtonClick : function(btnId) {
				
				var grid = this;
				var form = PAGE.FORM1.getFormData();
				
				switch(btnId){
					
					case "btnDnExcel":
					
						if(PAGE.GRID4.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
	
						var searchData = PAGE.FORM1.getFormData(true);
	
						mvno.cmn.download(ROOT + "/org/prmtmgmt/getDisPrmtDtlListExcel.json?menuId=MSP2002610", true, {postData:searchData});

					break;
						
					case "btnEndDt" :

						if(mvno.cmn.isEmpty(form.prmtId)){
							mvno.ui.alert("대상 프로모션이 존재하지 않습니다.");
							return;
						}

						var now = new Date().format("yyyymmddhhmiss");



						// 종료일시 유효성 검사
						if(form.endTm.length < 6){
							mvno.ui.alert("변경종료 시간은 6자리로 입력해주세요.");
							return;
						}

						if(form.endDt == null || form.endDt== ''){
							mvno.ui.alert("종료일자를 입력해주세요");
							return;
						}

						if(form.endTm == null || form.endTm== '' || form.endTm.length < 1){
							mvno.ui.alert("종료시간(시분초)을 입력해주세요");
							return;
						}

						var strtDttm = form.strtDt.format("yyyymmdd")+ form.strtTm;
						var endDttm =  form.endDt.format("yyyymmdd")+ form.endTm;
			
						if(endDttm < now){
							mvno.ui.alert("이 프로모션은 이미 종료된 프로모션으로 종료일을 변경할수 없습니다.");
							return;
						}
						if(Number(form.strtTm) < 0 || Number(form.strtTm) > 235959) {
							mvno.ui.alert("정확한 시작시간(시분초) 을 입력하세요");
							return;
						}
						if(Number(form.endTm) < 0 || Number( form.endTm) > 235959) {
							mvno.ui.alert("정확한 종료시간(시분초) 을 입력하세요");
							return;
						}
						if(strtDttm > endDttm) {
							mvno.ui.alert("종료일시 [" +endDttm+ "] 가 시작일시["  +strtDttm+ "] 전입니다. 종료일시을 변경하세요.");
							return;
						}
						if(endDttm < now) {
							mvno.ui.alert("종료일시 [" +endDttm+ "] 를 현재 ["  +now+ "] 이전으로 변경 할 수 없습니다.");
							return;
						}
						
						var sData = {
								prmtId :  form.prmtId
								,endDttmMod : endDttm
								,typeCd : 'U'
						};
						
						mvno.cmn.ajax4confirm("해당 프로모션의 종료일시를 변경 하시겠습니까?", ROOT + "/org/prmtmgmt/updDisPrmtByInfo.json", sData, function(result) {
							if(result.result.code =="OK"){
								mvno.ui.alert("NCMN0002","",function(){
									var url = "/org/prmtMgmt/getDisPrmtNew.do";
									var myTabbar = window.parent.mainTabbar;
									myTabbar.tabs(url).close();
								});
							}
						});
						
					break;
						
					case "btnEnd" :
						
						if(mvno.cmn.isEmpty(form.prmtId)){
							mvno.ui.alert("대상 프로모션이 존재하지 않습니다.");
							return;
						}
						var sData = {
								prmtId : form.prmtId
								,typeCd : 'D'
						};
						
						mvno.cmn.ajax4confirm("해당 프로모션을 삭제 하시겠습니까?", ROOT + "/org/prmtmgmt/updDisPrmtByInfo.json", sData, function(result) {
							if(result.result.code =="OK"){
								mvno.ui.alert("NCMN0003","",function(){
									var url = "/org/prmtMgmt/getDisPrmtNew.do";
									var myTabbar = window.parent.mainTabbar;
									myTabbar.tabs(url).close();
								});
							}
						});
						
					break;
				}
			}
		}	//GRID4 End
		,FORM10 : {
		 	 items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
				,{type:"block", list:[
					{type : "input",label : "조직 명",name : "orgnNm", width:100, offsetLeft:10} 
					, {type:"newcolumn", offset:30},
				]},
					{type:"newcolumn", offset:21}
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
			]
			,onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
				
					case "btnSearch":
						  						
						PAGE.GRID11.list(ROOT + "/org/prmtmgmt/getDisPrmtOrgnList.json", form);
							
					break;
				}
			}
		} // FORM10 End
		// 조직 리스트
		,GRID11 : {
			css : {
				height : "300px"
			},
			title : "조회결과",
			header : "선택,조직ID,조직명,조직유형,조직상태",
			columnIds : "rowCheck,orgnId,orgnNm,typeNm,statNm",
			colAlign : "center,left,left,left,center",
			colTypes : "ch,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str",
			widths : "40,90,140,90,70",
// 		    paging:true,
            buttons : {
           	   left : {
           			btnReset : { label : "초기화"}
           	   },
			   right : {
			  	 	btnMove : { label : "▶" }
			   }
            }
			// 그리드 행 선택시 checkbox도 선택되도록
			,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			}
            ,onButtonClick : function(btnId) {
	                  
                switch (btnId) {
	                  
                  case "btnReset":
                	  
                	  PAGE.FORM10.clear();
                	  PAGE.GRID12.clearAll();
                	  
              		  PAGE.GRID11.list(ROOT + "/org/prmtmgmt/getDisPrmtOrgnList.json", "");
                	  
                	
                	  
               	  break; 
                
                  case "btnMove":  	
                	  
	            	  var grid = this;          
	            	  var chkData=grid.getCheckedRowData();

	            	  if(chkData.length == 0){
	      				mvno.ui.alert("ECMN0003");
	      				return;
	      			  }
                
                	  var flag;
                	  var rownum = PAGE.GRID12.getRowsNum();
                	  
                	  outer:
                   	  for(var i = 0; i < chkData.length; i++){ //추가하려는  a b c
           				  for(var j = 0; j < rownum; j++){ 
                   			  if(chkData[i].orgnId == PAGE.GRID12.cells(PAGE.GRID12.getRowId(j), PAGE.GRID12.getColIndexById("orgnId")).getValue()){
									continue outer;	 
                   			  }
                   		  }
   						  PAGE.GRID12.appendRow({rowCheck: 0, orgnId : chkData[i].orgnId, orgnNm : chkData[i].orgnNm, typeNm : chkData[i].typeNm, statNm : chkData[i].statNm });
                      }
                	  
                	  
                	 
	                	  
                   break; 
                }
            }
		}
		//조직 선택 리스트
		,GRID12 : {
			css : {
				height : "300px"
			},
			title : "선택결과",
			header : "선택,조직ID,조직명,조직유형,조직상태",
			columnIds : "rowCheck,orgnId,orgnNm,typeNm,statNm",
			colAlign : "center,left,left,left,center",
			colTypes : "ch,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str",
			widths : "40,90,140,90,70",
// 		    paging:true,
            buttons : {
                center : {
             	   btnApply : { label : "확인" },
                    btnClose : { label: "닫기" }
                }
				 ,left : {
					   btnMove : { label : "◀" }
				  }
            }
			// 그리드 행 선택시 checkbox도 선택되도록
			,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			}
            ,onButtonClick : function(btnId, selectedData) {
                
          	  var grid = this;         
          	  var chkData = grid.getCheckedRowData();
          	  var rowIds = grid.getCheckedRows(0).split(",");
          	
                switch (btnId) {
                    case "btnApply":
                  	  
                  	  PAGE.GRID1.clearAll();
                  	  
                  	  for(var i = 0; i < grid.getRowsNum(); i++){
							var gridData=grid.getRowData(grid.getRowId(i));
							PAGE.GRID1.appendRow({rowCheck: 1, orgnId : gridData.orgnId, orgnNm  :gridData.orgnNm, typeNm : gridData.typeNm, statNm : gridData.statNm });
						  }
                  	  
                  	  mvno.ui.closeWindowById("GROUP1");
                  	  
                    break; 
                        
                    case "btnClose" :
                        
                  	  mvno.ui.closeWindowById("GROUP1");
                  	  
                    break;       
                       
                    case "btnMove" :
				 for(var i = 0; i < chkData.length; i++){
					 grid.deleteRow(rowIds[i]); 
				 }
				 
                    break;       
                };
            }
		},
		FORM20 : {
			items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
					,{type:"block", list:[
						{type : "input",label : "요금제 명",name : "rateNm", width:100, offsetLeft:10}
						, {type:"newcolumn", offset:30},
					]},
						{type:"newcolumn", offset:21}
						, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
			]
			,onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						
						PAGE.GRID21.list(ROOT + "/org/prmtmgmt/getDisPrmtSocList.json", form);
						
						break;
				}
			}
		}	//FORM 20 End
		// 요금제 리스트
		,	GRID21 : {
		       css : {
					height : "300px"
				},
			   title : "조회결과",
			   header : "선택,요금제코드,요금제명,기본료",
			   columnIds : "rowCheck,rateCd,rateNm,baseAmt",
			   colAlign : "center,center,left,right",
			   colTypes : "ch,ro,ro,ron",
			   colSorting : "str,str,str,int",
			   widths : "50,100,200,60",
// 		       paging:true,
               buttons : {
            	  left : {
	            		btnReset : { label : "초기화"}
	            	  }
				  ,
				  right : {
				  		btnMove : { label : "▶" }
				  }
              }
			  // 그리드 행 선택시 checkbox도 선택되도록
				,onRowSelect : function(rowId){
					var cell = this.cells(rowId,0);
					if(!cell.isChecked()) cell.setValue(1);
					else cell.setValue(0);
				}
              ,onButtonClick : function(btnId) {
                  switch (btnId) {
                  
                  case "btnReset":
		                	  
                	  PAGE.FORM20.clear();
                	  PAGE.GRID22.clearAll();  
                	  
                	  PAGE.GRID21.list(ROOT + "/org/prmtmgmt/getDisPrmtSocList.json", "");
                	 
               	  break; 
		                  
                  case "btnMove":  	 
                	  
                	  var grid = this;          
	            	  var chkData=grid.getCheckedRowData();

	            	  if(chkData.length == 0){
	      				mvno.ui.alert("ECMN0003");
	      				return;
	      			  }
	            	  
	            	  var rownum = PAGE.GRID22.getRowsNum();
	            	  outer:
                	  for(var i = 0; i < chkData.length; i++){ //추가하려는  a b c
               			  for(var j = 0; j < rownum; j++){ // 선택결과  b 
                   			  if(chkData[i].rateCd == PAGE.GRID22.cells(PAGE.GRID22.getRowId(j), PAGE.GRID22.getColIndexById("rateCd")).getValue()){
									continue outer;	 
                   			  }
                   		  }
						  PAGE.GRID22.appendRow({rowCheck: 0, rateCd : chkData[i].rateCd, rateNm : chkData[i].rateNm, baseAmt : chkData[i].baseAmt });
                      }
	            	  
	            	  
                	  
		                	  
               	  break; 
		                  
                  }
              }
		}
		//조직 선택 리스트
		,GRID22 : {
			css : {
					height : "300px"
				},
			title : "선택결과",
			header : "선택,요금제코드,요금제명,기본료",
			columnIds : "rowCheck,rateCd,rateNm,baseAmt",
			colAlign : "center,center,left,right",
			colTypes : "ch,ro,ro,ron",
			colSorting : "str,str,str,int",
			widths : "50,100,200,60",
// 			paging:true,
            buttons : {
                 center : {
              	   	 btnApply : { label : "확인" },
                     btnClose : { label: "닫기" }
                 },
				 left : {
				   	 btnMove : { label : "◀" }
			  }
            }
			// 그리드 행 선택시 checkbox도 선택되도록
			,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			}
            ,onButtonClick : function(btnId, selectedData) {
                
          	  var grid = this;         
          	  var chkData=grid.getCheckedRowData();
          	  var rowIds = grid.getCheckedRows(0).split(",");
	            	
                  switch (btnId) {
                      case "btnApply":
                    	  
                    	  PAGE.GRID2.clearAll();
                    	  
	                  	  for(var i = 0; i < grid.getRowsNum(); i++){
                                 var gridData=grid.getRowData(grid.getRowId(i));
							  PAGE.GRID2.appendRow({rowCheck: 1, rateCd : gridData.rateCd, rateNm : gridData.rateNm, baseAmt : gridData.baseAmt });
						  }
                    	  
                    	  mvno.ui.closeWindowById("GROUP2");
                    	  
                      break; 
                          
                      case "btnClose" :
                          
                    	  mvno.ui.closeWindowById("GROUP2");
                    	  
                      break;       
                         
                          
                      case "btnMove" :
						  for(var i = 0; i < chkData.length; i++){
						     grid.deleteRow(rowIds[i]); 
						  }
							 
                      break;       
                  };
              }
		},
		FORM30 : {
		    items : [
                {type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
                , {type:"block", list:[
	                    {type : "input",label : "부가서비스 명",name : "addNm", width:200, offsetLeft:10}
	                    , {type:"newcolumn", offset:30},
             	 ]},
		                {type:"newcolumn", offset:21}
		                , {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
	        ]
			,onButtonClick : function(btnId) {
		        var form = this;
		        switch (btnId) {
		            case "btnSearch":
		                
		                PAGE.GRID31.list(ROOT + "/org/prmtmgmt/getDisPrmtAddList.json", form);
		                
		                break;
		        }
		    }
		},
		// 부가서비스 리스트
		GRID31 : {
		    css : {
		        height : "300px"
		    },
		    title : "조회결과",
		    header : "선택,부가서비스명,기본료,부가서비스코드",
		    columnIds : "rowCheck,addNm,disAmt,soc",
		    colAlign : "center,left,right,center",
		    colTypes : "ch,coro,ron,ro",
		    colSorting : "str,str,str,str",
		    widths : "50,320,60,10",
		    hiddenColumns : [3],
// 			paging:true,
            buttons : {
        	   left : {
            			btnReset : { label : "초기화"}
               },
               right : {
               			btnMove : { label : "▶" }
               }
            }
			 // 그리드 행 선택시 checkbox도 선택되도록
		    ,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			}
            ,onButtonClick : function(btnId) {
				         	 
	              switch (btnId) {
	              
	              case "btnReset":
                	  
                	  PAGE.FORM30.clear();
                	  PAGE.GRID32.clearAll();
                	  
                	  PAGE.GRID31.list(ROOT + "/org/prmtmgmt/getDisPrmtAddList.json", "");
                	  
               	  break; 
				              
	              case "btnMove":  	 
	            	  
	            	  var grid = this;    
		              var chkData=grid.getCheckedRowData();

		              if(chkData.length == 0){
		                  mvno.ui.alert("ECMN0003");
		                  return;
		                }
	            
	                
	                  var rownum = PAGE.GRID32.getRowsNum();
	                  outer:
                	  for(var i = 0; i < chkData.length; i++){ //추가하려는  a b c
               			  for(var j = 0; j < rownum; j++){ // 선택결과  b 
                   			  if(chkData[i].soc == PAGE.GRID32.cells(PAGE.GRID32.getRowId(j), PAGE.GRID32.getColIndexById("soc")).getValue()){
									continue outer;	 
                   			  }
                   		  }
						  PAGE.GRID32.appendRow({rowCheck: 0, addNm : chkData[i].addNm, disAmt : chkData[i].disAmt, soc : chkData[i].soc  });
                      }
	                  
	                  
          		  break; 
             	}
       	    }
		},
		//조직 선택 리스트
		GRID32 : {
		    css : {
		        height : "300px"
		    },
		    title : "선택결과",
		    header : "선택,부가서비스명,기본료,부가서비스코드",
		    columnIds : "rowCheck,addNm,disAmt,soc",
		    colAlign : "center,left,right,center",
		    colTypes : "ch,coro,ron,ro",
		    colSorting : "str,str,str,str",
		    widths : "50,320,60,10",
		    hiddenColumns : [3],
// 				    paging:true,
            buttons : {
	              center : {
	                  btnApply : { label : "확인" },
	                  btnClose : { label: "닫기" }
	              }
	             ,left : {
	                   btnMove : { label : "◀" }
	              }
            }
			// 그리드 행 선택시 checkbox도 선택되도록
		    ,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			}
            ,onButtonClick : function(btnId, selectedData) {
              
              var grid = this;      
              var chkData=grid.getCheckedRowData();
           	  var rowIds = grid.getCheckedRows(0).split(",");
           	  
              switch (btnId) {
                  case "btnApply":
                      
                	  PAGE.GRID3.clearAll();
                      				                      
                      	  for(var i = 0; i < grid.getRowsNum(); i++){
                             var gridData=grid.getRowData(grid.getRowId(i));
					      PAGE.GRID3.appendRow({rowCheck: 1, addNm : gridData.addNm, disAmt : gridData.disAmt, soc : gridData.soc });
					   }

                      mvno.ui.closeWindowById("GROUP3");
                      
                  break; 
                  
                  case "btnClose" :
                      
                      mvno.ui.closeWindowById("GROUP3");
                      
                     break;       
                     
                     case "btnMove" :
					 for(var i = 0; i < chkData.length; i++){
						 grid.deleteRow(rowIds[i]); 
					 }
						 
                  break;       
              };
          }
       }
	};
	var PAGE = {
		title : " ",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString},
		onOpen:function() {
			form1Init();	
			form1Change(PAGE.FORM1, "intmYn");
		}
	};
	
	function form1Init() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createGrid("GRID2");
		mvno.ui.createGrid("GRID3"); 
		
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0090", etc3:"Y"}, PAGE.FORM1, "chnlTp", {callback: function(){
			if("${result.chnlTp}" != "") PAGE.FORM1.setItemValue("chnlTp", "${result.chnlTp}");
		}});
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0091", orderBy:"etc3"}, PAGE.FORM1, "evntCd", {callback: function(){
			if("${result.evntCd}" != "") PAGE.FORM1.setItemValue("evntCd", "${result.evntCd}");
		}});
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0093"}, PAGE.FORM1, "slsTp", {callback: function(){
				if("${result.slsTp}" != "") PAGE.FORM1.setItemValue("slsTp", "${result.slsTp}");
		}});
		
		if("${result.prmtId}" != ""){
			if("${result.usgYn}" !="Y")	{
				PAGE.FORM1.setItemValue("prmtId", "");
				form1RegInit();  // 이미 삭제된 프로모션의 상세 페이지 진입시
			}
			else if("${param.cate}"=="COPY") {
				form1CopyInit(); 	// 복사 폼 초기화
			}
			else if("${param.cate}"=="MOD") {
				form1ModInit(); // 수정 폼 초기화
			}
		}
		else{
			form1RegInit(); // 등록 폼 초기화
		}
 	}
	
	function form1RegInit(){
		mvno.ui.setTitle("프로모션 등록");
				
		PAGE.FORM1.setItemValue("strtDt", today);
		PAGE.FORM1.setItemValue("strtTm", "000000");
		PAGE.FORM1.setItemValue("endDt", endDt);
		PAGE.FORM1.setItemValue("endTm", "235959");
		
		PAGE.GRID1.setEditable(true);
		PAGE.GRID2.setEditable(true);
		PAGE.GRID3.setEditable(true);
			
		mvno.ui.showButton("GRID3" , 'btnReg');

		mvno.ui.hideButton("GRID4" , 'btnDnExcel');
		mvno.ui.hideButton("GRID4" , 'btnEndDt');
		mvno.ui.hideButton("GRID4" , 'btnEnd');
		
		//이미 삭제된 프로모션을 복사/수정시 
		if("${result.prmtId}" != "" && "${result.usgYn}" !="Y"){
			mvno.ui.alert( "["+ '${result.prmtId}' + "] 삭제된 프로모션입니다 새로 등록 바랍니다.");
			
		}
		
		PAGE.FORM1.clearChanged();
	}
	
	function form1CopyInit(){
		form1RegInit()
		
		mvno.ui.setTitle("프로모션 복사");
		
		PAGE.GRID1.list(ROOT + "/org/prmtmgmt/getDisPrmtDtlOrgnList.json", {prmtId:PAGE.FORM1.getItemValue("prmtId"), searchType: "COPY"});
 		PAGE.GRID2.list(ROOT + "/org/prmtmgmt/getDisPrmtDtlSocList.json", {prmtId:PAGE.FORM1.getItemValue("prmtId"),  searchType: "COPY"});
		PAGE.GRID3.list(ROOT + "/org/prmtmgmt/getDisPrmtDtlAddList.json", {prmtId:PAGE.FORM1.getItemValue("prmtId"),  searchType: "COPY"});
		PAGE.FORM1.setItemValue("prmtNm", "${result.prmtNm}" + " 복사본");
		PAGE.FORM1.setItemValue("prmtId", "");
		
		PAGE.FORM1.clearChanged();
	}
	
	function form1ModInit(){
		mvno.ui.setTitle("프로모션 수정");
		
		mvno.ui.createGrid("GRID4");
	
		PAGE.GRID1.list(ROOT + "/org/prmtmgmt/getDisPrmtDtlOrgnList.json", {prmtId:PAGE.FORM1.getItemValue("prmtId"),searchType: "MOD"});
 		PAGE.GRID2.list(ROOT + "/org/prmtmgmt/getDisPrmtDtlSocList.json", {prmtId:PAGE.FORM1.getItemValue("prmtId"),searchType: "MOD"});
		PAGE.GRID3.list(ROOT + "/org/prmtmgmt/getDisPrmtDtlAddList.json", {prmtId:PAGE.FORM1.getItemValue("prmtId"),searchType: "MOD"});
		PAGE.GRID4.list(ROOT + "/org/prmtmgmt/getDisPrmtDtlList.json", {prmtId:PAGE.FORM1.getItemValue("prmtId")});  
		
		PAGE.FORM1.disableItem("INFO");
		PAGE.FORM1.disableItem("JOIN");
		PAGE.FORM1.disableItem("ENGG");
		PAGE.FORM1.disableItem("strtDt");
		PAGE.FORM1.disableItem("strtTm");
		PAGE.FORM1.disableItem("evntCd");
		PAGE.FORM1.disableItem("chnlTp");
		PAGE.FORM1.disableItem("slsTp");
		
		mvno.ui.hideButton("GRID1" , 'btnSearch');
		mvno.ui.hideButton("GRID2" , 'btnSearch');
		mvno.ui.hideButton("GRID3" , 'btnSearch');
		mvno.ui.hideButton("GRID3" , 'btnReg');
		
		mvno.ui.showButton("GRID3" , 'btnDtl');
		mvno.ui.showButton("GRID3" , 'btnEndDt');
		mvno.ui.showButton("GRID3" , 'btnEnd');
		
		PAGE.FORM1.clearChanged();
	}
	
	function form1Change(form, name){
		
		switch(name){
		
		case "slsTp" :
			
			var value = form.getItemValue(name);
			if(value == "MM1" || value == "MM2"){
				form.enableItem("intmYn");
			}
			else{
				form.setItemValue("intmYn","N");
				form.hideItem("modelSearch");
 				form.setItemValue("modelId","");
 				form.setItemValue("modelNm","");
				form.disableItem("intmYn");
			}
			
			break;
			
		
		case "intmYn" :

			var value = form.getItemValue(name);	
			if(value == "Y"){
				form.showItem("modelSearch");
			}
			else {
				form.hideItem("modelSearch");
 				form.setItemValue("modelId","");
 				form.setItemValue("modelNm","");
			}	
			break;
	
		case "enggCntAll" :
			if(form.isItemChecked(name)){
				form.setItemValue("enggCnt0", 1);
				form.setItemValue("enggCnt12", 1);
				form.setItemValue("enggCnt18", 1);
				form.setItemValue("enggCnt24", 1);
				form.setItemValue("enggCnt30", 1);
				form.setItemValue("enggCnt36", 1);
				
				form.disableItem("enggCnt0");
				form.disableItem("enggCnt12");
				form.disableItem("enggCnt18");
				form.disableItem("enggCnt24");
				form.disableItem("enggCnt30");
				form.disableItem("enggCnt36");
			}			
			else{
				form.setItemValue("enggCnt0", 0);
				form.setItemValue("enggCnt12", 0);
				form.setItemValue("enggCnt18", 0);
				form.setItemValue("enggCnt24", 0);
				form.setItemValue("enggCnt30", 0);
				form.setItemValue("enggCnt36", 0);
				
				form.enableItem("enggCnt0");
				form.enableItem("enggCnt12");
				form.enableItem("enggCnt18");
				form.enableItem("enggCnt24");
				form.enableItem("enggCnt30");
				form.enableItem("enggCnt36");
			}
			
		break;
							
		case "enggCnt0" :
			if(form.isItemChecked(name)){
				if(form.getItemValue("enggCnt12") == 1 &&
				   form.getItemValue("enggCnt18") == 1 &&
				   form.getItemValue("enggCnt24") == 1 &&
				   form.getItemValue("enggCnt30") == 1 &&
				   form.getItemValue("enggCnt36") == 1 ){
				   form.setItemValue("enggCntAll", 1);

				}
			}
			else{
				  form.setItemValue("enggCntAll", 0);
			}
			break;

		case "enggCnt12" :

			if(form.isItemChecked(name)){
				if(form.getItemValue("enggCnt0") == 1 &&
				   form.getItemValue("enggCnt18") == 1 &&
				   form.getItemValue("enggCnt24") == 1 &&
				   form.getItemValue("enggCnt30") == 1 &&
				   form.getItemValue("enggCnt36") == 1 ){
				   form.setItemValue("enggCntAll", 1);
				}
			}
			else{
				  form.setItemValue("enggCntAll", 0);
			}
			break;

		case "enggCnt18" :

			if(form.isItemChecked(name)){
				if(form.getItemValue("enggCnt0") == 1 &&
				   form.getItemValue("enggCnt12") == 1 &&
				   form.getItemValue("enggCnt24") == 1 &&
				   form.getItemValue("enggCnt30") == 1 &&
				   form.getItemValue("enggCnt36") == 1 ){
				   form.setItemValue("enggCntAll", 1);
				}
			}
			else{
				  form.setItemValue("enggCntAll", 0);
			}
			break;

		case "enggCnt24" :

			if(form.isItemChecked(name)){
				if(form.getItemValue("enggCnt0") == 1 &&
				   form.getItemValue("enggCnt12") == 1 &&
				   form.getItemValue("enggCnt18") == 1 &&
				   form.getItemValue("enggCnt30") == 1 &&
				   form.getItemValue("enggCnt36") == 1 ){
				   form.setItemValue("enggCntAll", 1);
				}
			}
			else{
				  form.setItemValue("enggCntAll", 0);
			}
			break;

		case "enggCnt30" :

			if(form.isItemChecked(name)){
				if(form.getItemValue("enggCnt0") == 1 &&
				   form.getItemValue("enggCnt12") == 1 &&
				   form.getItemValue("enggCnt18") == 1 &&
				   form.getItemValue("enggCnt24") == 1 &&
				   form.getItemValue("enggCnt36") == 1 ){
				   form.setItemValue("enggCntAll", 1);
				}
			}
			else{
				  form.setItemValue("enggCntAll", 0);
			}
			break;

		case "enggCnt36" :

			if(form.isItemChecked(name)){
				if(form.getItemValue("enggCnt0") == 1 &&
				   form.getItemValue("enggCnt12") == 1 &&
				   form.getItemValue("enggCnt18") == 1 &&
				   form.getItemValue("enggCnt24") == 1 &&
				   form.getItemValue("enggCnt30") == 1 ){
				   form.setItemValue("enggCntAll", 1);
				}
			}
			else{
				  form.setItemValue("enggCntAll", 0);
			}
			break;

		case "strtDt":
			PAGE.FORM1.setItemValue("strtTm", "000000");
			break;
			
		case "endDt":
			PAGE.FORM1.setItemValue("endTm", "235959");
			break;
		}
	}
</script>