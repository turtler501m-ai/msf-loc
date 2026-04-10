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
					{type:"select", label:"배달상태",name:"orderStatCd", width:110, offsetLeft:80}
				]},
				{type:"block", list:[
					{type:"block", list:[
							{type:"select", label:"검색구분", name:"searchCd", width:90, offsetLeft:10, options:[{value:"", text:"- 선택 -"}, {value:"1", text:"고객명"}, {value:"2", text:"연락처"}, {value:"3", text:"kt주문번호"}]},
							{type:"newcolumn"},
							{type:"input", name:"searchVal", width:125, maxlength:30}
						]},
					{type:"newcolumn"},
					{type:"select", label:"신청경로",name:"channelCd", width:110, offsetLeft:40},
					{type:"newcolumn"},
					{type:"select", label:"결제상태",name:"payCd", width:110, offsetLeft:80}
				]},
				{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
			],
			
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getUsimDirDlvryChnlList.json", form);
						
						break;
				}
			},
			onChange : function(name, value) {//onChange START
				// 검색구분
				if(name == "searchCd") {
					PAGE.FORM1.setItemValue("searchVal", "");

					if(value == null || value == "") {
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
						
						PAGE.FORM1.disableItem("searchVal");
					} else {
						PAGE.FORM1.setItemValue("strtDt", "");
						PAGE.FORM1.setItemValue("endDt", "");
						
						// 신청일자 Disable
						PAGE.FORM1.disableItem("strtDt");
						PAGE.FORM1.disableItem("endDt");
						
						PAGE.FORM1.enableItem("searchVal");
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
			, header : "주문번호,kt주문번호,업체명,고객명,연락처,신청일자,완료일자,진행상태,결제상태,신청경로,배달상태,재접수여부,재배달업체,수정자,수정일자,비고" //16
			, columnIds : "selfDlvryIdx,ktOrdId,bizOrgNm,cstmrName,dlvryTel,sysRdate,cplDate,dlvryStateNm,payState,channelNm,orderStatNm,reAcceptYn,reBizOrgNm,rvisnNm,rvisnDttm,orderStatRsnDesc"
			, widths : "0,150,120,120,120,170,170,100,120,100,100,100,100,100,170,200"
			, colAlign : "center,center,center,left,center,center,center,center,center,center,center,center,left,left,center,center"
			, colTypes : "ro,ro,ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns:[0]
			//, multiCheckbox : true
			, paging : true
			, showPagingAreaOnInit : true
			, pageSize : 20
			, buttons : {
				hright : {
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
// 						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9024"}, PAGE.FORM2, "dlvryStateCd"); // 진행상태
// 						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9027"}, PAGE.FORM2, "payCd"); // 배송상태
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM2, "dlvryTelFn"); // 연락처
// 						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9026"}, PAGE.FORM2, "selfStateCode"); // 신청상태
// 						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"channel"}, PAGE.FORM2, "channelCd"); // 신청경로
// 						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"cStateCode"}, PAGE.FORM2, "orderStatCd"); // 배달상태
						
						PAGE.FORM2.setFormData(grid.getSelectedRowData(), true);
						
						// 진행상태, 배달상태가 같을시 배달상태 숨김
						if(PAGE.FORM2.getItemValue("dlvryStateCode") == "0" + PAGE.FORM2.getItemValue("orderStatCd")) {
							PAGE.FORM2.hideItem("orderStatNm");
						}else{
							PAGE.FORM2.showItem("orderStatNm");
						}
								
						mvno.ui.popupWindowById("FORM2", "상세화면", 790, 480, {
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
							mvno.cmn.download("/rcp/rcpMgmt/getUsimDirDlvryChnlListByExcel.json?menuId=MSP1010036", true, {postData:searchData});
						}
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
							{type:"input", label:"고객명", name:"cstmrName", width:120, readonly:true, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"접수일자", name:"sysRdate", width:130, offsetLeft:10, readonly:true, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"신청경로", name:"channelNm", width:120, offsetLeft:20, disabled:true},
							{type:"hidden", name:"selfDlvryIdx"},
						]}
					]},
					{type: "fieldset", label: "신청정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"진행상태", name:"dlvryStateNm", width:120, readonly:true, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"신청상태", name:"selfStateNm", width:75, offsetLeft:10, disabled:true},
							{type:"newcolumn"},
							{type:"button", value:"접수취소", name :"btnDlvryCancel", disabled:true},
							{type:"newcolumn"},
							{type:"select", label:"결제상태", name:"payCd", width:120, offsetLeft:2, options:[{value:"01", text:"정상"}, {value:"03", text:"결제취소완료"}]}
						]},
// 						{type:"block", list:[
// 							{type:"input", label:"배달희망시간", name:"rsvOrderDt", width:120, readonly:true, disabled:true},
// 							{type:"newcolumn"},
// 							{type:"input", label:"배달예약여부", name:"rsvOrderYn", width:120, offsetLeft:10, readonly:true, disabled:true},
// 							{type:"newcolumn"},
// 							{type:"input", label:"비대면여부", name:"untact", width:120, offsetLeft:30, readonly:true, disabled:true},
// 						]},
						{type:"block", list:[
							{type:"input", label:"메모", name:"selfMemo", width:367, maxLength:100},
							{type:"newcolumn"},
							{type:"input", label:"배달상태", name:"orderStatNm", width:120, readonly:true, disabled:true},
							{type:"hidden", name:"dlvryStateCode"},
							{type:"hidden", name:"orderStatCd"},
						]},
// 						{type:"block", list:[
// 							{type:"input", label:"배달상태", name:"orderStatNm", width:120, readonly:true, disabled:true},
// 							{type:"newcolumn"},
// 							{type:"input", label:"개인정보제공동의여부", name:"custInfoAgreeYn", width:120, labelWidth:140, offsetLeft:186, readonly:true, disabled:true},
// 						]},
					]},
					// 배송정보
					{type: "fieldset", label: "배송정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"받으시는분", name:"dlvryName", width:120, maxLength:80, disabled:true},
							{type:"newcolumn"},
							{type:"select", label:"연락처", name:"dlvryTelFn", width:80, offsetLeft:10, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryTelMn", width:60, maxLength:4, validate:'ValidNumeric' , readonly:true, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryTelRn", width:60, maxLength:4, validate:'ValidNumeric' , readonly:true, disabled:true}
						]},
						{type:"block", list:[ 
							{type:"input", label:"우편번호", name:"dlvryPost", width:75, readonly:true, maxLength:6, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryAddr", width:490, readonly:true, disabled:true},
						]},
						{type:"block", list:[
							{type:"input", label:"상세주소", name:"dlvryAddrDtl", width:570, maxLength:80, disabled:true}
						]},
						{type:"block", list:[ 
							{type:"input", label:"요청사항",name:"dlvryMemo", width:570, maxLength:200, disabled:true}
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
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/setUsimDirDlvryChnlInfo.json", form, function(result) {
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
	}
			
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		 onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9024"}, PAGE.FORM1, "dvryStatCd"); // 진행상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"cStateCode"}, PAGE.FORM1, "orderStatCd"); // 배달상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"channel"}, PAGE.FORM1, "channelCd"); // 신청경로
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9027"}, PAGE.FORM1, "payCd"); // 결제상태
			
		}
	};
</script>
