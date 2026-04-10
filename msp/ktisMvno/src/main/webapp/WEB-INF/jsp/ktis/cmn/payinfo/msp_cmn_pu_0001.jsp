<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_cmn_pu_0001.jsp
	 * @Description : 개통 고객 찾기 팝업
	 * @
     * @ 수정일     수정자 수정내용
     * @ ------------ -------- -----------------------------
     * @ 2016.07.11 장익준 최초생성
     * @
     * @author : 장익준
     * @Create Date : 2016. 07. 11.
     */
%>

<!-- header -->
<div class="page-header">
	<h1>개통 고객 찾기</h1>
</div>
<!-- //header -->

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

var DHX = {

	// 검색 조건
	FORM1 : {
		items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
					
                   	, {type:"block", list:[
                   	                       {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '등록일자', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10}
                   	                       , {type: 'newcolumn'}
                   	                       , {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
                   	                       , {type: 'newcolumn'}
                                           , {type:"select", width:100, label:"검색구분",name:"searchGbn", userdata:{lov:"CMN0108"}, offsetLeft:20}
                                           , {type:"newcolumn"}
                                           , {type:"input", width:100, name:"searchName"}
                           ]
                   	},
					{type:"newcolumn", offset:21},
					{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
			],
		onButtonClick : function(btnId) {
			var form = this;
			switch (btnId) {
				case "btnSearch":
					PAGE.GRID1.list(ROOT + "/cmn/payinfo/custmgmtLists.json", form);
					break;
			}
		}
	},

	// 조직 리스트
	GRID1 : {

		css : {
			height : "330px"
		},
		title : "조회결과",
		header : "계약번호,전화번호,고객명,고객상태,가입일자,대리점명", //6
		columnIds : "contractNum,subscriberNo,subLinkName,subStatusNm,lstComActvDate,openAgntNm", //6
		colAlign : "center,center,left,center,center,left",
		colTypes : "ro,roXp,ro,ro,roXd,ro",
		colSorting : "str,str,str,str,str,str",
        widths : "100,100,150,100,100,200",
	    paging:true,
              buttons : {
                  center : {
               	   btnApply : { label : "확인" },
                      btnClose : { label: "닫기" }
                  }
              },
              checkSelectedButtons : ["btnApply"],
              onRowDblClicked : function(rowId, celInd) {
                  // 선택버튼 누른것과 같이 처리
                  this.callEvent('onButtonClick', ['btnApply']);
              },
              onButtonClick : function(btnId, selectedData) {
                  
                  switch (btnId) {
                      case "btnApply":

						   if(PAGE.GRID1.getRowsNum() == 0) {
							   mvno.ui.alert("데이터가 존재하지 않습니다.");
							   return;
						    }
						   
                     	  mvno.cmn.ajax(ROOT + "/cmn/payinfo/insertOffline.json", {contractNum : PAGE.GRID1.getSelectedRowData().contractNum}, function(result) {
                     		  var result = result.result;
				              var rCode = result.code;
				              var msg = result.msg;
				              if(rCode == "OK") {
				            	  mvno.ui.closeWindow(selectedData, true);
				              }
				              else {
				                  mvno.ui.notify(msg);
				              }                     		  
						  });                    	  
                          break; 
                      case "btnClose" :
                          mvno.ui.closeWindow();   
                          break
                  }
              }
	}
};
var PAGE = {
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
		}
	};
</script>