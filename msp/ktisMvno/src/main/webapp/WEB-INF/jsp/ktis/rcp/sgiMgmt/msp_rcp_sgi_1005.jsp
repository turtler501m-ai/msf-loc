<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_rcp_sgi_1005.jsp
	 * @Description : 불능처리조회 화면
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
							PAGE.GRID1.list(ROOT + "/rcp/sgiMgmt/getSgiRejectList.json", form);
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
			header : "회사코드,보험청구의뢰번호,보험관리번호,기각사유,보증사청구요청일,지불인ID,이동전화,고객구분,주민/법인번호,고객번호,보험개시일,직권해지일,증권번호,보험가입금액,청구금액,계약자명,상위영업팀명,대리점코드,대리점명,홈지사명,관리신용팀명", //21
			columnIds : "cmpnCd,insrBillReqNo,insrMngmNo,rejectRsn,grntBillReqDate,payerId,subscriberNo,cstmrCd,userSsn,cstmrNo,insrStrtDate,officioRevocationDate,securitiesNo,insrJoinAmt,billAmt,cstmrName,topSalesTeamName,agntCd,agntName,homeBranchName,mngmCreditTeamName",
			widths : "80,110,100,80,110,80,80,80,100,80,80,80,130,80,80,80,110,80,110,110,110",
			colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,right,right,left,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro,roXd,roXd,ro,ron,ron,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
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
                		  mvno.cmn.download('/rcp/sgimgmt/getSgiRejectListEx.json?baseDate=' + PAGE.FORM1.getItemValue("baseDate",true) + "&menuId=MSP1040006",true);
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