<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_cmn_user_1003.jsp
	 * @Description : 패스워드 초기화 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2024.08.19 이승국 최초생성
	 * @Create Date : 2024. 08. 19.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
 <!-- Script -->
<script type="text/javascript">
var orgId = '';
var orgNm = '';
//CS 기획팀일 경우 조직명 고정
if ('${orgnInfo.orgnId}' == 'A401225') {
	orgId = 'A30040999';
	orgNm = '엠모바일고객센터';
}

	 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			 items : [
				 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
				 , {type:"block", list:[
						 {type:"input", width:100,  label:"사용자ID",name:"usrId",  value: '', maxLength:10,width:100, offsetLeft:10}
						, {type: 'newcolumn'}
						, {type:"input", width:100, label:"사용자명",name:"usrNm", value: '', maxLength:10,width:100,offsetLeft:20}
						, {type: 'newcolumn'}
						,{type: 'select', name: 'orgType', label: '조직유형',width:100,offsetLeft:20, options:[{value:"20",text:"대리점"}]}
					]},
					{type: 'block', list: [
						{type:"input", name:"orgnId", label:"조직", value:orgId, width:100, offsetLeft:10}
						,{type:"newcolumn", offsetLeft:3}
						,{type:"button", value:"검색", name:"btnOrgFind",disabled:false}
						,{type: 'newcolumn'}
						,{type:"input", name:"orgnNm", value:orgNm, width:126, readonly: true}
						,{type: 'newcolumn'}
						,{type: 'select', name: 'status', label: '상태',width:100,offsetLeft:20}
					]},
					{type: 'newcolumn', offset:150},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"},
				],
				onButtonClick : function(btnId) {
				
					var form = this;
					
					switch (btnId) {
					
						case "btnSearch":
						
								PAGE.GRID1.list(ROOT + "/org/userinfomgmt/getUserPwdReset.json", form);
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
			header : "신청일자,사용자ID,사용자명,상태,조직유형,조직명,처리자,처리일자,조직ID,usrId",
			columnIds : "aplDt,usrIdMsk,usrNm,statusNm,attcSctnNm,orgnNm,procNm,procDt,orgnId,usrId",
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
					btnCansel : { label : "취소" },
					btnReset : { label : "패스워드 초기화" }
				},
			},
			onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					
					case "btnCansel":
						
						var rowIds = this.getCheckedRows(0); // 선택된 checkbox 의 rowId 를 가져온다.
						if (! rowIds) {
							mvno.ui.alert("ECMN0003");
							return;
						}

						var arrData = PAGE.GRID1.getCheckedRowData();
						for(var j=0; j<arrData.length; j++) {
							if(arrData[j].statusNm != '대기'){
								mvno.ui.alert("대기상태만 변경 가능합니다.");
								return;
							}
						}
						
						var checkedData = PAGE.GRID1.classifyRowsFromIds(rowIds, "usrId,aplDt");
												
						var url = ROOT + "/org/userinfomgmt/updateUserPwdCansel.json";
						mvno.ui.confirm("CCMN0015", function() {
							mvno.cmn.ajax4json(url, checkedData, function() {
								mvno.ui.notify("NCMN0006");
								PAGE.GRID1.refresh();
							});
						});

						break;
					case "btnReset":
						
						var rowIds = this.getCheckedRows(0); // 선택된 checkbox 의 rowId 를 가져온다.
						if (! rowIds) {
							mvno.ui.alert("ECMN0003");
							return;
						}

						var arrData = PAGE.GRID1.getCheckedRowData();
						for(var j=0; j<arrData.length; j++) {
							if(arrData[j].statusNm != '대기'){
								mvno.ui.alert("대기상태만 변경 가능합니다.");
								return;
							}
						}
						
						var checkedData = PAGE.GRID1.classifyRowsFromIds(rowIds, "usrId,aplDt");
												
						var url = ROOT + "/org/userinfomgmt/updateUserPwdReset.json";
						mvno.ui.confirm("CCMN0015", function() {
							mvno.cmn.ajax4json(url, checkedData, function() {
								mvno.ui.notify("NCMN0006");
								PAGE.GRID1.refresh();
							});
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
			//유통영업팀일 경우 조직유형 대리점으로 고정
			if ('${orgnInfo.orgnId}' != 'A401230') {
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0003",etc1:"Y"}, PAGE.FORM1, "orgType"); // 조직유형
			}
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0114", orderBy:"etc1"}, PAGE.FORM1, "status"); // 상태
			if ('${orgnInfo.orgnId}' == 'A401225') {
				PAGE.FORM1.disableItem("btnOrgFind");
				PAGE.FORM1.setReadonly("orgnId",true);
			}
		 }
	};

</script>