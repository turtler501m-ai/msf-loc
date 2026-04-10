<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1035.jsp
	 * @Description : 그룹 찾기 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.09.01     임지혜           최초 생성
	 * @
	 * @author : 임지혜
	 * @Create Date : 2014. 9. 1.
	 */
%>

	
	<!-- 화면 배치 -->
	<div id="FORM1" class="section-search"></div><!-- 상단 검색폼 class="section-search" 삽입 -->
	<div id="GRID1" class="section-full" ></div>
	<div id="FORM2"></div>
	
	<!-- Script -->
	<script type="text/javascript">
	
	var DHX = {
		
			FORM1 : {
			 items :  [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
		
				// 그룹명
//				,{type:"block", list:[
//						{type:"select", label:"그룹명", width:100, name:"GRP_NM", connector:"/cmn/authmgmt/getGrpListXml.do" // 화면에 안보임. 왜?
//							, maxLength:50 }
//				]},
				
				// 그룹ID
				,{type:"newcolumn", offset:30}
				,{type:"input", label:"그룹ID", width:80, name:"GRP_ID", maxLength:10 }
				
				// 그룹명
				,{type:"newcolumn", offset:30}
				,{type:"input", label:"그룹명", width:80, name:"GRP_NM", maxLength:10 } 
		
				// 등록자
				,{type:"newcolumn", offset:30}
				,{type:"input", label:"등록자ID", width:80, name:"REGST_ID", maxLength:10 } // , maxLength:50 } // usr_nm : 50, usr_id : 10
		
				// 조회 버튼
				,{type:"newcolumn", offset:30}
				,{type:"button", value:"조회", className:"btn-search1", name:"btnSearch"}/* btn-search(열갯수1~4) */
				],
		
			onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId){
				case "btnSearch" :
					
//					if (! this.validate())  return;
					
					var url = ROOT + "/cmn/authmgmt/usrGrpList.json";
					PAGE.GRID1.list(url, form);
//					PAGE.GRID1.list(url);
					
					break;
				}
			}
		},
		
		GRID1 : {
				css : {
					height : "305px"
				},
				
				header : "그룹ID,그룹명,그룹설명,등록자,등록일",
				columnIds : "GRP_ID,GRP_NM,GRP_DSC,REGST_ID,REGST_DTTM",
				widths : "120,120,150,120,125",
				colAlign : "left,left,left,center,center",
				colTypes : "ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str",	
				paging : true,
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
                           mvno.ui.closeWindow(selectedData, true);                           
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


