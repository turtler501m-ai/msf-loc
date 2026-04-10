<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
				{type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"처리일자", value:"${strtDt}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10, required:true},
					{type:"newcolumn"},
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:"${endDt}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
					{type:'newcolumn'},
					{type:"select", label:"검색구분", name:"searchCd", width:90, offsetLeft:40, options:[{value:"", text:"- 선택 -"}, {value:"1", text:"아이디"}, {value:"2", text:"주문번호"}, {value:"3", text:"계약번호"}]},
					{type:"newcolumn"},
					{type:"input", name:"searchVal", width:125, maxlength:30}
				]},
				{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
			],
			
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpCustPointList.json", form);
						
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
			onKeyUp : function (inp, ev, name, value){
			
				var searchCd = PAGE.FORM1.getItemValue("searchCd");
		
				if(name == "searchVal"){
					if(searchCd == "2" || searchCd == "3") {	
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
			, title : "조회결과" // 21
			, header : "포인트내역일련번호,포인트고객일련번호,아이디,주문번호,계약번호,누적적립포인트,누적사용포인트,잔여포인트,소멸포인트,포인트집계기준일자,포인트명,포인트내역,포인트,시점잔여포인트,포인트사용가능시작일,포인트사용가능종료일,포인트내역사유,포인트처리상세사유,포인트처리일시,포인트처리메모,생성일시"
			, columnIds : "pointTxnSeq,pointCustSeq,userId,requestKey,svcCntrNo,totAcuPoint,totUsePoint,totRemainPoint,totExtinctionPoint,pointSumBaseDate,pointDivCd,pointTrtCd,point,totRemainPointTxn,pointUsePosblStDate,pointUsePosblEndDate,pointTxnRsnCd,pointTxnDtlRsnDesc,pointTrtDt,pointTrtMemo,cretDt"
			, widths : "120,120,100,100,100,100,100,100,100,150,100,100,100,100,150,150,200,200,150,200,150"
			, colAlign : "right,right,left,right,center,right,right,right,right,center,center,center,right,right,center,center,left,left,center,left,center"
			, colTypes : "ro,ro,ro,ro,ro,ron,ron,ron,ron,ro,ro,ro,ron,ron,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,int,int,int,int,str,str,str,int,int,str,str,str,str,str,str,str"
			//, multiCheckbox : true
			, paging : true
			, showPagingAreaOnInit : true
			, pageSize : 20
			, buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드"}
				},
			},
			onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
					case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download("/rcp/rcpMgmt/getRcpCustPointListByExcel.json?menuId=MSP1010034", true, {postData:searchData});
						}
						break;
				}
			}
		},
	
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
