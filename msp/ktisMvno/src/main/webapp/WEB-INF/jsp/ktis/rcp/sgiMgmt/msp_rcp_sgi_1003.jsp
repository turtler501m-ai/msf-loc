<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_rcp_sgi_1003.jsp
	 * @Description : 청구취소조회 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.06.04 장익준 최초생성
	 * @
	 * @author : 장익준
	 * @Create Date : 2018. 6. 04.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">

 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
 				,{type:"block", list:[
					{type : 'calendar', dateFormat: '%Y-%m', serverDateFormat: '%Y%m', name: 'baseDate', enabelDay:false, label: '연동월', value: "${currentMonth}", calendarPosition: 'bottom', readonly:true, width:100, offsetLeft:10, required:true}
				]}
				,{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
			]
				,onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.list(ROOT + "/rcp/sgiMgmt/getSgiCanList.json", form);
						break;
					}
				}
			},

		//-------------------------------------------------------------
           GRID1 : {
			css : {
				height : "598px"
			},
			title : "조회결과",
			header : "보상청구의뢰일,보상청구의뢰번호,고객명,주민번호,보증보험관리번호,서비스계약번호,이동전화번호,취소사유설명,취소금액,취소사유코드", //10
			columnIds : "compensationBillDate,compensationBillNo,cstmrName,userSsn,grntInsrMngmNo,svcCntrNo,subscriberNo,canRsn,billCanAmt,canRsnCd",
			widths : "90,100,100,80,100,100,100,120,70,80",
			colAlign : "center,center,left,center,center,center,center,left,right,center",
			colTypes : "roXd,ro,ro,ro,ro,ro,ro,ro,ron,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize:20,
			buttons : {
                hright : {
                     btnDnExcel : { label : "엑셀다운로드" }
                }
           },
   			onButtonClick : function(btnId, selectedData) {
  				var form = this;
  				switch (btnId) {
                  case "btnDnExcel":
                	  
                	  if(PAGE.GRID1.getRowsNum()== 0)
                	  {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
						return;
                	  }
                	  else {
                		  mvno.cmn.download('/rcp/sgimgmt/getSgiCanListEx.json?baseDate=' + PAGE.FORM1.getItemValue("baseDate",true) + "&menuId=MSP1040004",true);
                	  }
	                      break;   
                	  }
  				}
   			}
       };
			
var PAGE = {
    title: "${breadCrumb.title}",
    breadcrumb: "${breadCrumb.breadCrumb}",

    buttonAuth: ${buttonAuth.jsonString},
    
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");

	}

};

</script>