<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

<div id="POPUP1">
	<div id="GRID2"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
				{type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"신청일자", value:"${strtDt}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10, required:true},
					{type:"newcolumn"},
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:"${endDt}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
					{type:'newcolumn'},
					{type:"select", label:"진행상태", name:"dvryStatCd", width:110, offsetLeft:40},
					{type:"newcolumn"},
					{type:"select", label:"신청상태",name:"selfStateCode", width:110, offsetLeft:80}
				]},
				{type:"block", list:[
					{type:"block", list:[
							{type:"select", label:"검색구분", name:"searchCd", width:90, offsetLeft:10, options:[{value:"", text:"- 선택 -"}, {value:"1", text:"고객명"}, {value:"2", text:"연락처"}, {value:"3", text:"업체명"}, {value:"4", text:"주문번호"}, {value:"5", text:"kt주문번호"},{value:"6", text:"예약번호"}]},
							{type:"newcolumn"},
							{type:"input", name:"searchVal", width:125, maxlength:30}
						]},
					{type:"newcolumn"},
					{type:"select", label:"연동결과",name:"svcRsltCd", width:110, offsetLeft:40},
					{type:"newcolumn"},
					{type:"select", label:"결제상태",name:"payCd", width:110, offsetLeft:80}
				]},
				{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
			],
			
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getUsimDirDlvryList.json", form);
						
						break;
				}
			},
			onChange : function(name, value) {//onChange START
				// 검색구분
				if(name == "searchCd") {
					PAGE.FORM1.setItemValue("searchVal", "");

					if(value == null || value == "" || value == "3") {
						var searchEndDt = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var searchStartDt = new Date(time);

						// 신청일자 Enable
						PAGE.FORM1.enableItem("strtDt");
						PAGE.FORM1.enableItem("endDt");

						PAGE.FORM1.setItemValue("searchVal", "");

						PAGE.FORM1.setItemValue("strtDt", searchStartDt);
						PAGE.FORM1.setItemValue("endDt", searchEndDt);
						
						if(value == "3"){
							PAGE.FORM1.enableItem("searchVal");	
						}
						else{
							PAGE.FORM1.disableItem("searchVal");
						}						
					}
					else {
						PAGE.FORM1.setItemValue("strtDt", "");
						PAGE.FORM1.setItemValue("endDt", "");
						
						// 신청일자 Disable
						PAGE.FORM1.disableItem("strtDt");
						PAGE.FORM1.disableItem("endDt");
						
						PAGE.FORM1.enableItem("searchVal");
					}	
				}			
			},	
			/*	2022-04-12 주문번호는 DB상으로 type이 NUMBER 값을 가지고 있어서 string을 넣고 조회 버튼을 누르면 "자료처리오류입니다"라는 메세지가 뜸 
				이를 방지하기 위해 아래 코드 추가 */
			onKeyUp : function (inp, ev, name, value){
			
				var searchCd = PAGE.FORM1.getItemValue("searchCd");
		
				if(name == "searchVal"){
					if(searchCd == "4") {	
						var val = PAGE.FORM1.getItemValue("searchVal");
						PAGE.FORM1.setItemValue("searchVal",val.replace(/[^0-9]/g,""));
					}
				}
			},
			onValidateMore : function (data){
				
				if(data.strtDt > data.endDt){
					this.pushError("data.searchStartDt","신청일자",["ICMN0004"]);
				}
				
				if( data.searchCd != "" && data.searchVal.trim() == ""){
					this.pushError("searchVal", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "560px"
			}
			, title : "조회결과"
			, header : "주문번호,kt주문번호,업체명,고객명,연락처,유심종류,신청일자,완료일자,진행상태,신청상태,연동결과,결제상태,신청경로,예약번호,재접수여부,재배달업체,법정대리인,수정자,수정일자,비고,결제취소상태,결제취소실패사유,결제취소자,결제취소일" //24
			, columnIds : "selfDlvryIdx,ktOrdId,bizOrgNm,cstmrName,dlvryTel,usimProdNm,sysRdate,cplDate,dlvryStateNm,selfStateNm,svcRslt,payState,reqTypeNm,resNo,reAcceptYn,reBizOrgNm,minorAgentName,rvisnNm,rvisnDttm,orderStatRsnDesc,cnclYnStat,cnclFailRsn,cnclNm,cnclDt" //24
			, widths : "100,150,80,100,105,100,170,170,75,75,75,75,120,100,100,100,120,120,170,0,100,200,100,170" //24
			, colAlign : "center,center,center,left,center,center,center,center,center,center,center,center,center,center,center,center,left,left,center,left,center,left,center,center" //24
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" //24
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" //24
			, hiddenColumns:[20]
			//, multiCheckbox : true
			, paging : true
			, showPagingAreaOnInit : true
			, pageSize : 20
			, buttons : {
				hright : {
					btnHst : { label : "이력보기"},
					btnDnExcel : { label : "엑셀다운로드"}
				},
			},
			//checkSelectedButtons : ["btnDtl"],
			onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnDtl']);
			},
			
			onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
				
					case "btnDtl":
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
					
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();		
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9027"}, PAGE.FORM2, "payCd"); // 배송상태
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM2, "dlvryTelFn"); // 연락처
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9026"}, PAGE.FORM2, "selfStateCode"); // 신청상태
						
						PAGE.FORM2.setFormData(grid.getSelectedRowData(), true);	

						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"selFDlvryIdx" : grid.getSelectedRowData().selfDlvryIdx, "resNo":grid.getSelectedRowData().resNo, "trgtMenuId":"MSP1010031"}, function(result){});
						
						PAGE.FORM2.disableItem("cstmrName");
						PAGE.FORM2.disableItem("minorAgentName");
						PAGE.FORM2.disableItem("sysRdate");
						PAGE.FORM2.disableItem("selfDlvryIdx");
						PAGE.FORM2.disableItem("dlvryStateNm");
						PAGE.FORM2.disableItem("selfStateCode");
						PAGE.FORM2.disableItem("dlvryName"); 
						PAGE.FORM2.disableItem("dlvryTelFn");
						PAGE.FORM2.disableItem("dlvryTelMn");
						PAGE.FORM2.disableItem("dlvryTelRn");
						PAGE.FORM2.disableItem("dlvryPost");
						PAGE.FORM2.disableItem("dlvryAddr");
						PAGE.FORM2.disableItem("dlvryAddrDtl");
						PAGE.FORM2.disableItem("dlvryMemo");
						PAGE.FORM2.disableItem("resNo");

						//2021sus 10월 05일 신청경로 추가
						PAGE.FORM2.disableItem("reqTypeNm");
					
						//2022년 04월 01일 바로배송 신청 상세 내 배송취소사유 연동 텍스트 노출 추가
						if(PAGE.FORM2.getItemValue("dlvryStateNm") == "접수취소" ) {
							PAGE.FORM2.showItem("orderStatRsnDesc");
						}else{
							PAGE.FORM2.hideItem("orderStatRsnDesc");
						}

						//2024년 01월09일 바로배송 신청 상세 내 유심종류 연동 텍스트 노출 추가
						PAGE.FORM2.disableItem("usimProdNm");
								
						mvno.ui.popupWindowById("FORM2", "상세화면", 790, 500, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
									
						break;
						
					case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download("/rcp/rcpMgmt/getUsimDirDlvryListByExcel.json?menuId=MSP1010031", true, {postData:searchData});
						}
						break;
				
					case 'btnHst' : //이력보기
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
					
						mvno.ui.createGrid("GRID2");
						
						PAGE.GRID2.clearAll();

						var selectData = grid.getSelectedRowData();
						
						PAGE.GRID2.list(ROOT + "/rcp/rcpMgmt/getUsimDirDlvryHst.json", selectData);
						
						mvno.ui.popupWindowById("POPUP1", "상담이력", 800, 500, {
							onClose: function(win) {
								win.closeForce();
							}
						});
						
						
						break;
				}
			}
		},
		
		FORM2 : {
			items : [
				{type:"block", list:[
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0},
					// 고객정보
					{type: "fieldset", label: "고객정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"접수일자", name:"sysRdate", width:130, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"주문번호", name:"selfDlvryIdx", width:130, offsetLeft:20, readonly:true}
						]},
						{type:"block", list:[
							{type:"input", label:"고객명", name:"cstmrName", width:130, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"법정대리인", name:"minorAgentName", width:130, offsetLeft:20, readonly:true}
						]},
					]},
					{type: "fieldset", label: "신청정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"진행상태", name:"dlvryStateNm", width:120, readonly:true},
							{type:"newcolumn"},
							{type:"select", label:"신청상태", name:"selfStateCode", width:80, offsetLeft:10},
							{type:"newcolumn"},
							{type:"button", value:"접수취소", name :"btnDlvryCancel"},
							{type:"newcolumn"},
							{type:"select", label:"결제상태", name:"payCd", width:118},
							{type:"hidden", label:"접수가능시간", name:"acceptTime"}
						]},
						{type:"block", list:[
							{type:"input", label:"비고", name:"orderStatRsnDesc", width:572, maxLength:200, disabled:true},
						]},
						{type:"block", list:[
							{type:"input", label:"메모", name:"selfMemo", width:368, maxLength:100},
							{type:"newcolumn"},
							{type:"input", label:"신청경로", name:"reqTypeNm", width:118, readonly:true}
						]},
						{type:"block", list:[
							{type:"input", label:"예약번호", name:"resNo", width:120, maxLength:100, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"유심종류", name:"usimProdNm", width:120, offsetLeft:10, readonly:true},
						]}
					]},
					// 배송정보
					{type: "fieldset", label: "배송정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"받으시는분", name:"dlvryName", width:120, maxLength:80},
							{type:"newcolumn"},
							{type:"select", label:"연락처", name:"dlvryTelFn", width:80, offsetLeft:10},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryTelMn", width:60, maxLength:4, validate:'ValidNumeric' , readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryTelRn", width:60, maxLength:4, validate:'ValidNumeric' , readonly:true}
						]},
						{type:"block", list:[ 
							{type:"input", label:"우편번호", name:"dlvryPost", width:75, readonly:true, maxLength:6},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryAddr", width:490, readonly:true},
						]},
						{type:"block", list:[
							{type:"input", label:"상세주소", name:"dlvryAddrDtl", width:570, maxLength:80}
						]},
						{type:"block", list:[ 
							{type:"input", label:"요청사항",name:"dlvryMemo", width:570, maxLength:200}
						]}
					]}
				]}
			],
			buttons : {
				center : {
					btnSave : {label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onValidateMore : function (data){

			},
			onChange : function(name, value) {//onChange START
				// 검색구분
			
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					
				// 주소찾기 기능 여부 미정
					//case "btnDlvryPostFind": 
					//	mvno.ui.codefinder4ZipCd("FORM2", "dlvryPost", "dlvryAddr", "dlvryAddrDtl");
					//	PAGE.FORM2.enableItem("dlvryAddrDtl");
					//	break;
					
					case "btnDlvryCancel" : 
						if(PAGE.FORM2.getItemValue("selfStateCode") == "02"){
							mvno.ui.alert("신청상태가 접수 상태가 아닙니다.");
						}else{
						    mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/dvryServiceCall.json", form, function(resultData) {
						    	mvno.ui.alert(resultData.result.msg);
								if(resultData.result.code == "OK"){
									PAGE.FORM2.setItemValue("selfStateCode", resultData.result.selfStateCode);
								} 
							});
						}
					break;
					
				    case "btnSave":
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/setUsimDirDlvryInfo.json", form, function(result) {
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
		, GRID2 : {
			css : {
				height : "320px"
				, width : "742px"
			}
			, title : "상담이력"
			, header : "NO.,주문번호,진행상태,신청상태,결제상태,메모,비고,수정자,수정일자,고객명"
			, columnIds : "nowDlvryHstSeq,selfDlvryIdx,dlvryStateNm,selfStateNm,payState,selfMemo,orderStatRsnDesc,rvisnNm,rvisnDttm,cstmrName"
			, widths : "80,80,80,80,80,200,200,80,150,0"
			, colAlign : "center,center,center,center,center,left,left,center,center,center"
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str"
			//, multiCheckbox : true
			, hiddenColumns:[9]
			, paging : true
			, showPagingAreaOnInit : true
			, pageSize : 10
			, buttons : {
				center : {
					btnClose : { label: "닫기" }
				}
			}			
			, onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					case "btnClose" :
						mvno.ui.closeWindowById("POPUP1");
						break;
				}
			}
		}
	
	}
			
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		 onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9024"}, PAGE.FORM1, "dvryStatCd"); // 진행상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9026"}, PAGE.FORM1, "selfStateCode"); // 신청상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9028"}, PAGE.FORM1, "svcRsltCd"); // 연동결과
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9027"}, PAGE.FORM1, "payCd"); // 결제상태
			
		}
	};
</script>
