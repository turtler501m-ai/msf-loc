<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_rcp_sgi_1001.jsp
	 * @Description : 단말계약조회 화면
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
							PAGE.GRID1.list(ROOT + "/rcp/sgiMgmt/getSgiDmndList.json", form);
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
			header : "업무구분,회사구분,보험청구의뢰번호,결과코드,오류결과내용,증권번호,보험관리번호,서비스번호,고객구분,주민번호,계약자명,사업자번호,지불인ID,최종단말모델명,최종단말연번,이동전화번호,직권해지일,분납해지여부,명의변경일,이동전화가입일,보험개시일,보험종료일,할부총횟수,할부납입횟수,보험가입금액,청구일자,청구금액,할부이자율,최초연체일,최초연체회차,거주지전화번호,거주지우편번호,청구지전화번호,청구지우편번호,거주지주소구분코드,거주지주소,청구지주소구분코드,청구지주소,담당지점코드,미성년자성명,미성년자주민번호,개인회생여부,개인회생단계,개인회생변제여부,개인회생변제금액,파산면책여부", //46
			columnIds : "dutySctn,cmpnSctn,insrBillReqNo,resultCd,errResultCntt,securitiesNo,insrMngmNo,svcNo,cstmrCd,userSsn,cstmrName,indvEntrNo,payerId,lstModelName,lstModelSerialNo,subscriberNo,officioRevocationDate,partPayRevocationYn,nameChangeDate,phoneJoinDate,insrStrtDate,insrTrmnDate,instCnt,instPayNum,insrJoinAmt,billDate,billAmt,instRate,fstUnpayDate,fstUnpayNum,residenceSubscriberNo,residencePost,billAddrSubscriberNo,billAddrPost,residenceCd,residenceAddr,billAddrCd,billAddr,branchCd,minorName,minorSsn,indvRevivalYn,indvRevivalStep,indvRevivalRepayYn,indvRevivalRepayAmt,bankruptcyExemptionYn",
			widths : "140,80,120,60,150,130,110,110,80,100,110,80,80,100,80,80,80,80,80,95,80,80,80,80,80,80,80,80,80,80,95,100,100,100,120,480,120,480,90,100,120,100,100,120,120,100",
			colAlign : "center,center,center,center,center,center,center,center,center,center,left,center,center,left,center,center,center,center,center,center,center,center,center,center,right,center,right,center,center,center,center,center,center,center,left,left,left,left,center,left,center,center,center,center,right,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,ro,roXd,roXd,roXd,roXd,ro,ro,ron,roXd,ron,ro,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
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
                		  mvno.cmn.download('/rcp/sgimgmt/getSgiDmndListEx.json?baseDate=' + PAGE.FORM1.getItemValue("baseDate",true) + "&menuId=MSP1040002",true);
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