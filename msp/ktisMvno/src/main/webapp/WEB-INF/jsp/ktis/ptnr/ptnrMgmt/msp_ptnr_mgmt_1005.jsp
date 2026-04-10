<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_ptnr_mgmt_1005.jsp
	 * @Description : 채널별요금할인관리
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.06.12 강무성 최초작성
	 * @author : 
	 * @Create Date : 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	
	var toMonth = new Date().format("yyyymm");
	var toDay   = new Date().format("yyyymmdd");
	
	var DHX = {
		// 검색
		FORM1 : {
			items : [ 
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 60, blockOffset : 0 }
				, { type : "block", labelWidth : 30 , list : [
					{type: 'calendar', width:80, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', name: 'baseDate', label: '연동년월', calendarPosition: 'bottom', readonly:true }
					, {type: 'newcolumn'}
					, {type:"select", width:100, labelWidth : 50, label:"제휴사", name:"ptnrId", offsetLeft:30}
					, {type: 'newcolumn'}
					, {type:"select", width:80, label:"요청상태", name:"sendFlag", offsetLeft:30}
					, {type: 'newcolumn'}
					, {type:"select", width:80, label:"결과상태", name:"sendResult", offsetLeft:30}
				]}
				, { type : "button",name : "btnSearch",value : "조회", className:"btn-search1" } 
			]
			, onButtonClick : function(btnId) {
				
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						
						PAGE.GRID1.list(ROOT + "/ptnr/ptnrmgmt/getPtnrLinkLstInfo.json", form);
						
					break;
				}
			}
		}	//FORM1 End
		
		, GRID1 : {
			css : { 
				height : "600px"
			}
			, title : "제휴연동결과내역"
			, header : "연동년월,제휴사,연동FILE,요청,#cspan,#cspan,결과,#cspan,#cspan,제휴ID,요청상태코드,결과상태코드"
			, header2 : "#rspan,#rspan,#rspan,상태,일자,재전송,상태,일자,재처리,#rspan,#rspan,#rspan"
			, columnIds : "baseDate,ptnrNm,fileNm,sendFlagNm,regstDttm,재전송,sendResultNm,reusltDate,재처리,ptnrId,sendFlag,sendResult"
			, widths : "80,120,220,80,100,80,80,100,80,10,10,10"
			, colAlign : "center,center,left,center,center,center,center,center,center,center,center,center"
			, colTypes : "roXdm,ro,ro,ro,roXd,bt,ro,roXd,bt,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns : "9,10,11"
			, paging : true
			, pageSize : 20
			, onRowSelect : function (rId, cId) {
				
				if( cId != 5 && cId !=  8 ) {
					return;
				}
				
				var rData = this.getSelectedRowData();
				
				
				if('googleplay' != rData.ptnrId && rData.baseDate != toMonth) {
					mvno.ui.alert("현재월인 경우만 가능합니다.");
					return;
				} else if ('googleplay' == rData.ptnrId && rData.regstDttm != toDay) {
					console.info(rData);
					console.info(toDay);
					mvno.ui.alert("오늘자인 경우만 가능합니다.");
					return;
					
				}
				
				if('dongbu' == rData.ptnrId || 'goodlife' == rData.ptnrId){
					mvno.ui.alert("처리가 불가능한 제휴사 입니다.");
					return;
				}
				
				var msg = "";
				var url = "";
				
				var sData = {
						partnerId : rData.ptnrId
						,fileNm : rData.fileNm
				};
				
				if(cId == 5){
					msg = "연동파일을 재전송하시겠습니까?";
					url = "/ptnr/ptnrmgmt/uploadPointFile.json";
				}else if(cId == 8){
					msg = "연동파일을 재수신하시겠습니까?";
					url = "/ptnr/ptnrmgmt/downloadPointFile.json";
				}
				
				mvno.cmn.ajax4confirm(msg, ROOT + url, sData, function(result) {
					PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
				});
			}
		}	//GRID1 End
	}
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : "${breadCrumb.breadCrumb}", 
		buttonAuth: ${buttonAuth.jsonString},
		onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			PAGE.FORM1.setItemValue("baseDate", toMonth);
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0073",orderBy:"etc1"}, PAGE.FORM1, "ptnrId");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0244",orderBy:"etc6"}, PAGE.FORM1, "sendFlag");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0245",orderBy:"etc6"}, PAGE.FORM1, "sendResult");
		}
	};
</script>