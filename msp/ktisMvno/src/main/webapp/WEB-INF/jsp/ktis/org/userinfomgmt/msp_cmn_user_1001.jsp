<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_cmn_user_1001.jsp
	 * @Description : 사용자정지 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.08.14 권오승 최초생성
	 * @Create Date : 2016. 06. 01.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
 <!-- Script -->
<script type="text/javascript">
	 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			 items : [
				 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
				 , {type:"block", list:[
					/* 	{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'startDate', label: '정지일자', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10}
						, {type: 'newcolumn'}
						, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'endDate', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
						, {type: 'newcolumn'} */
						 {type:"input", width:100,  label:"사용자ID",name:"usrId",  value: '', maxLength:10,width:100, offsetLeft:10}
						, {type: 'newcolumn'}
						, {type:"input", width:100, label:"사용자명",name:"usrNm", value: '', maxLength:10,width:100,offsetLeft:20}
						, {type: 'newcolumn'}
						,{type: 'select', name: 'orgType', label: '조직유형',width:100,offsetLeft:20}
					]},
					{type: 'block', list: [
						{type:"input", name:"orgnId", label:"조직", value: "", width:100, offsetLeft:10}
						,{type:"newcolumn", offsetLeft:3}
						,{type:"button", value:"검색", name:"btnOrgFind"}
						,{type: 'newcolumn'}
						,{type:"input", name:"orgnNm", value:"", width:126, readonly: true}
						,{type: 'newcolumn'}
						,{type: 'select', name: 'stopYn', label: '정지상태',width:100,offsetLeft:20}
					]},
					{type: 'newcolumn', offset:150},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"},
				],
				onButtonClick : function(btnId) {
				
					var form = this;
					
					switch (btnId) {
					
						case "btnSearch":
						
								PAGE.GRID1.list(ROOT + "/org/userinfomgmt/getUserStopStatus.json", form);
								break;
								
						case "btnOrgFind":
							mvno.ui.codefinder("ORG", function(result) {
								form.setItemValue("orgnId", result.orgnId);
								form.setItemValue("orgnNm", result.orgnNm);
							});
							break;
				}
			}
		},
		// ----------------------------------------
		//사용자리스트
		GRID1 : {
			css : {
					height : "530px"
			},
			title : "조회결과",
/* 			header : "선택,정지일자,사용자ID,사용자명,상태,조직유형,조직명,처리자,처리일자,조직ID",
			columnIds : "chk,stopDt,usrId,usrNm,stopCdName,attcSctnNm,orgnNm,procNm,procDt,orgnId",
			colAlign : "center,center,left,left,center,center,left,left,center,center",
			colTypes : "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str",
			widths : "30,110,110,145,80,90,200,110,110,100", */
			header : "정지일자,사용자ID,사용자명,상태,조직유형,조직명,처리자,처리일자,조직ID,usrId",
			columnIds : "stopDt,usrIdMsk,usrNm,stopCdName,attcSctnNm,orgnNm,procNm,procDt,orgnId,usrId",
			colAlign : "center,left,left,center,center,left,left,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str",
			widths : "107,100,150,50,70,200,100,108,100,0",
			hiddenColumns:[8,9],
			multiCheckbox : true,
			paging: true,
			pageSize:20,
			buttons : {
				right : {
					btnReg : { label : "상태변경" }
				},
				hright : {
					btnExcel : { label : "엑셀다운로드" }
				}
			},
			onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					case "btnReg":
						
						var rowIds = this.getCheckedRows(0); // 선택된 checkbox 의 rowId 를 가져온다.
						if (! rowIds) {
							mvno.ui.alert("ECMN0003");
							return;
						}

						var arrData = PAGE.GRID1.getCheckedRowData();
						for(var j=0; j<arrData.length; j++) {
							if(arrData[j].stopCdName != '정지'){
								mvno.ui.alert("정지상태만 변경 가능합니다.");
								return;
							}
						}
						
						var checkedData = PAGE.GRID1.classifyRowsFromIds(rowIds, "usrId,stopDt");
												
						var url = ROOT + "/org/userinfomgmt/updateUserStatusChg.json";
						mvno.ui.confirm("CCMN0015", function() {
							mvno.cmn.ajax4json(url, checkedData, function() {
								mvno.ui.notify("NCMN0006");
								PAGE.GRID1.refresh();
							});
						});

						break;
						case "btnExcel" :
							if(PAGE.GRID1.getRowsNum() == 0){
								mvno.ui.alert("데이터가 존재하지 않습니다");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/org/userinfomgmt/getStopUserDataListExcel.json?menuId=MSP2002003", true, {postData:searchData}); 
							
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
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0102"}, PAGE.FORM1, "orgType"); // 사용여부
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0101"}, PAGE.FORM1, "stopYn"); // 소속구분
		 }
	};

</script>