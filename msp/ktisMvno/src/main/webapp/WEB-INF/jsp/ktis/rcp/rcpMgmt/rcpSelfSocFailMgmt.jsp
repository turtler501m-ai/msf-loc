<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : rcpSelfSocFailMgmt.jsp
	 * @Description : 요금제변경실패고객관리
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2021.11.16 권오승 최초생성
	 * @Create Date : 2021.11.16.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div><!-- 메인 -->
<div id="GRID2" class="section-full"></div><!-- 상세 -->
 <!-- Script -->
<script type="text/javascript">
	 var DHX = {
		 //------------------------------------------------------------
		 FORM1: {
			 items: [
				 {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 55, blockOffset: 0},
				 {type: "block", list: [
						 {type: "calendar", width: 100, label: "조회기간", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "strDate", offsetLeft: 10, readonly: true},
						 {type: "newcolumn"},
						 {type: "calendar", label: "~", name: "endDate", labelAlign: "center", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", labelWidth: 10, offsetLeft: 5, width: 100, readonly: true},
						 {type: 'newcolumn'},
						 {type: 'select', name: 'succYn', label: '성공여부', width: 100, offsetLeft: 20},
						 {type: 'newcolumn'},
						 {type: 'select', name: 'procYn', label: '처리여부', width: 100, offsetLeft: 20},
						 {type: 'newcolumn'},
						 {type: "input",width: 100,label: "계약번호",name: "contractNum",value: '',maxLength: 10,width: 110,offsetLeft: 20}
					 ]
				 },
				 {type: 'newcolumn', offset: 150},
				 {type: 'button', value: '조회', name: 'btnSearch', className: "btn-search1"},
			 ],

			 onValidateMore: function (data) {
				 if (data.strDate > data.endDate) {
					 this.pushError("strDate", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
				 }
				 if (data.strDate != "" && data.strDate == "") {
					 this.pushError("strDate", "조회기간", "조회기간을 입력하세요.");
				 }
				 if (data.strDate != "" && data.strDate == "") {
					 this.pushError("endDate", "조회기간", "조회기간을 입력하세요.");
				 }
			 },
			 onButtonClick: function (btnId) {
				 var form = this;

				 switch (btnId) {
					 case "btnSearch":
						 PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpSelfSocFailList.json", form);
						 PAGE.GRID2.clearAll();
						 break;
				 }
			 }
		 },
		 // ----------------------------------------
		 //사용자리스트
		 GRID1: {
			 css: {
				 height: "240px"
			 },
			 title: "조회결과",
			 header: "처리모듈,성공여부,요금제변경요청일,구분,변경전요금제,변경희망요금제명,성공여부,계약번호,고객명,처리여부,처리일시,처리자ID,처리메모",
			 columnIds: "prcsMdlInd,succYn,sysRdate,chgTypeNm,aSocNm,tSocNm,succNm,contractNum,custNm,procYn,procDate,procId,procMemo",
			 colAlign: "center,center,center,center,left,left,center,center,left,center,center,left,left",
			 colTypes: "ro,ro,roXdt,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,ro",
			 colSorting: "str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			 widths: "160,150,140,100,170,170,80,100,120,70,140,100,300",
			 hiddenColumns: [0, 1],
			 paging: true,
			 pageSize: 20,
			 buttons: {
				 right: {
					 btnMod: {label: "처리상태변경"}
				 },
				 hright: {
					 btnExcel: {label: "엑셀다운로드"}
				 }
			 },
			 onRowSelect: function (rId, cId) {
				 var grid = this;

				 var sdata = grid.getSelectedRowData();
				 var data = {
					 prcsMdlInd: sdata.prcsMdlInd,
					 contractNum: sdata.contractNum
				 }
				 PAGE.GRID2.list(ROOT + "/rcp/rcpMgmt/getRcpSelfSocFailDetail.json", data);
			 },
			 onButtonClick: function (btnId, selectedData) {
				 var grid = this;
				 switch (btnId) {
					 case "btnMod":
						 var sdata = grid.getSelectedRowData();
						 if (sdata == null) {
							 mvno.ui.alert("선택된 데이터가 없습니다. 데이터를 선택하고 처리해주시기 바랍니다.");
							 return;
						 }
						 if (sdata.succYn == "Y") {
							 mvno.ui.alert("요금제 변경이 성공한 건은 처리상태 변경을 할 수 없습니다.");
							 return;
						 } else {
							 if (sdata.procYn == "Y") {
								 mvno.ui.alert("이미 처리된 건은 처리상태 변경을 할 수 없습니다.");
								 return;
							 } else {
								 mvno.ui.prompt("처리메모", function (result) {
									 var url = "/rcp/rcpMgmt/updateSelfSocFailProc.json";

									 var params = {
										 "prcsMdlInd": sdata.prcsMdlInd
										 , "contractNum": sdata.contractNum
										 , "procMemo": result
										 , "procYn": "Y"
									 };

									 mvno.cmn.ajax(url, params, function (result) {
										 mvno.ui.notify("NCMN0001");
										 //PAGE.GRID1.refresh();
										 mvno.ui.alert("처리가 완료되었습니다.");
										 PAGE.GRID1.refresh();
										 PAGE.GRID2.clearAll();
									 });

								 }, {lines: 10, maxLength: 3000})
							 };
						 }
						 break;
					 case "btnExcel" :
						 if (PAGE.GRID1.getRowsNum() == 0) {
							 mvno.ui.alert("데이터가 존재하지 않습니다");
							 return;
						 }
						 var searchData = PAGE.FORM1.getFormData(true);
						 mvno.cmn.download(ROOT + "/rcp/rcpMgmt/getRcpSelfSocFailListExcel.json?menuId=MSP1010032", true, {postData: searchData});
						 break;
				 }
			 }
		 },
		 GRID2: {
			 css: {
				 height: "250px"
			 },
			 title: "요금제변경처리이력",
			 header: "처리모듈구분,서비스이력 등록일,이벤트코드,성공여부코드,시퀀스,계약번호,서비스신청일자,서비스명,요금제 코드,대상상품,성공여부,실패사유,처리자 ID,처리자 이름", //14
			 columnIds: "prcsMdlInd,sysDt,eventCode,succYn,seq,svcCntrNo,sysRdate,trtmRsltSmst,tSocCode,tSocNm,succYnNm,failRsNm,procId,procNm", //14
			 colAlign: "center,center,center,center,center,center,center,left,center,left,center,left,center,center", //14
			 colTypes: "ro,ro,ro,ro,ro,ro,roXdt,ro,ro,ro,ro,ro,ro,ro", //14
			 colSorting: "str,str,str,str,str,str,str,str,str,str,str,str,str,str", //14
			 widths: "100,100,100,100,40,100,150,150,100,210,70,400,100,80", //14
			 hiddenColumns: [0,1,2,3,4],
			 paging: true,
			 pageSize: 20,
			 buttons: {
				 right: {
					 btnRty: {label: "부가서비스 재처리"}
				 }
			},
			 onRowSelect: function (rId, cId) {
				 var sdata1 = PAGE.GRID1.getSelectedRowData();
				 var sdata2 = this.getSelectedRowData();

				 var diableBtn = false;

				 if(sdata2.succYn == "0000" || sdata2.eventCode != "X21") diableBtn = true;
				 if(sdata1 != null && (sdata1.succYn =="Y" || sdata1.procYn=="Y")) diableBtn = true;

				 if(diableBtn)  mvno.ui.disableButton("GRID2", "btnRty");
				 else mvno.ui.enableButton("GRID2", "btnRty");
			 },
			 onButtonClick: function (btnId, selectedData) {
				 switch (btnId) {
					 case "btnRty":
						 var grid = this;
						 var sdata = grid.getSelectedRowData();

						 if(sdata == null){
							 mvno.ui.alert("선택된 데이터가 없습니다.<br> 데이터를 선택하고 처리해주시기 바랍니다.");
							 return;
						 }

						 mvno.ui.confirm("재처리 이후 수정할 수 없습니다. <br> 재처리하시겠습니까?",function (){

							 var data = {
								 prcsMdlInd: sdata.prcsMdlInd
								 ,seq: sdata.seq
								 ,sysDt: sdata.sysDt
								 ,ncn : sdata.svcCntrNo
							 }

							 mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/retrySelfSocFail.json", data, function (resultData) {
								 var rData = resultData.result;
								 var alertMsg = "";

								 if(rData.code == 'NOK'){
									 alertMsg = rData.msg
								 }else{
									 if(rData.resultCd == "Y") alertMsg = "재처리가 완료 되었습니다.";
									 else alertMsg = "재처리 실패 했습니다." ;
								 }
								 mvno.ui.alert(alertMsg);
								 PAGE.GRID2.refresh();
						 	},{async:false, dimmed:true});
						 });
						 break;
				 }
			 }
		 }
	 };

	var PAGE = {
		 title : "${breadCrumb.title}",
		 breadcrumb : " ${breadCrumb.breadCrumb}",
		 buttonAuth: ${buttonAuth.jsonString},
		 onOpen : function() {

			 mvno.ui.createForm("FORM1");
			 mvno.ui.createGrid("GRID1");
			 mvno.ui.createGrid("GRID2");
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005" ,orderBy:"etc1" }, PAGE.FORM1, "procYn" ); // 사용여부
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9033" ,orderBy:"etc1" }, PAGE.FORM1, "succYn" ); // 성공여부


			var endDate = new Date().format('yyyymmdd');
			var time = new Date().getTime();
			time = time - 1000 * 3600 * 24 * 7;
			var startDate = new Date(time);

			PAGE.FORM1.setItemValue("strDate", startDate);
			PAGE.FORM1.setItemValue("endDate", endDate);

		 }
	};

</script>