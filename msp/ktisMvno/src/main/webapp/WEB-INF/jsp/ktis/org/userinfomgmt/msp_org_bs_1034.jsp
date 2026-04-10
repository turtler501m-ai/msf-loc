<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1034.jsp
	 * @Description : 사용자조회 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.10.17 권성광 최초생성
	 * @author : 권성광
	 * @Create Date : 2018. 10. 27.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
	 <div id="FORM2" class="section-box"></div>
	 <div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
	 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			 items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
						{type: 'block', list: [
						{type: 'input', name: 'usrId', label: '사용자ID', value: '', maxLength:10,width:100, offsetLeft:10},
						{type: 'newcolumn'},
						{type: 'input', name: 'usrNm', label: '사용자명', value: '', maxLength:10,width:100,offsetLeft:40},
						{type: 'newcolumn'},
						{type: 'select', name: 'usgYn', label: '상태' ,width:100,offsetLeft:20},
						{type: 'newcolumn'},
						{type: 'select', name: 'orgType', label: '소속구분',width:100, offsetLeft:20}
					]},
					{type: 'block', list: [
						{type:"input", name:"orgnId", label:"조직", value: "", width:100, offsetLeft:10},
						{type:"newcolumn", offsetLeft:3},
						{type:"button", value:"검색", name:"btnOrgFind"},
						{type:"newcolumn", offsetLeft:3},
						{type:"input", name:"orgnNm", value:"", width:145, readonly: true}
					]},
					{type: 'newcolumn', offset:150},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"},
			],
			onButtonClick : function(btnId) {
			
				var form = this;
				
				switch (btnId) {
				
					case "btnSearch":
							PAGE.GRID1.list(ROOT + "/org/userinfomgmt/salesUserInfoMgmtList.json", form);
							break;

					case "btnOrgFind":
						mvno.ui.codefinder("ORGSALES", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
						});
						break;
				}
			},
			onChange : function(name, value) {
				var form = this;
					
				// 검색구분
				if(name == "orgType") {
					PAGE.FORM1.setItemValue("orgnId", "");
					PAGE.FORM1.setItemValue("orgnNm", "");
					
					if(value == "15") {
						PAGE.FORM1.disableItem("orgnId");
						PAGE.FORM1.disableItem("orgnNm");
						PAGE.FORM1.disableItem("btnOrgFind");
					}
					else {
						PAGE.FORM1.enableItem("orgnId");
						PAGE.FORM1.enableItem("orgnNm");
						PAGE.FORM1.enableItem("btnOrgFind");
					}
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
			header : "사용자ID,사용자명,상태,조직명,소속구분,핸드폰번호,최종로그인일시,등록자,등록일시,수정자,수정일시",
			columnIds : "usrId,usrNm,usgYn,orgnNm,orgTypeNm,mblphnNum,lastLoginDt,regstNm,regstDttm,rvisnNm,rvisnDttm",
			colAlign : "center,left,center,left,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,roXp,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str",
			widths : "80,150,80,150,80,120,120,80,120,80,120",
			paging: true,
			pageSize:20,
			buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" }
				}
			},
			onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					case "btnExcel" :
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/org/userinfomgmt/salesUserInfoMgmtListExcel.json?menuId=MSP2000113", true, {postData:searchData}); 
						
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
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0100",orderBy:"etc1"}, PAGE.FORM1, "usgYn"); // 사용여부
			 //mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0003",etc2:"sales"}, PAGE.FORM1, "orgType"); // 소속구분
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0115"}, PAGE.FORM1, "orgType"); // 소속구분
		 }
		
	};

</script>