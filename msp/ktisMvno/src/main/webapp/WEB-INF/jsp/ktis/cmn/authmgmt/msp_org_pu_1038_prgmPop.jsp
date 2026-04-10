<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_pu_1038_prgmPop.jsp
	 * @Description : 프로그램 리스트 팝업용 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.10.14     임지혜           최초 생성
	 * @
	 * @author : 임지혜
	 * @Create Date : 2014. 10. 14.
	 */
%>
	<!-- header -->
	<div class="page-header">
	    <h1>프로그램 찾기 팝업</h1>
	</div>

    <!-- 화면 배치 -->
    <div id="FORM1" class="section-search" ></div>
	<div id="GRID1" class="section-full"> </div>
		
    <!-- Script -->
    <script type="text/javascript">
    
    var DHX = {
    		
		FORM1 : {
    		items : [
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
				,{type:"block", labelWidth:30 , list:[    
					{type : "input", label : "프로그램ID", name : "PRGM_ID" , offsetLeft:10, offsetLeft : 10, maxLength:10}
					,{ type : "newcolumn" , offset:20} 
					,{type : "input", label : "프로그램명", name : "PRGM_NM", offsetLeft:10 , offsetLeft : 10, maxLength:10} 
				]}
				,{type:"block", labelWidth:30 , list:[    
					{type:"select", label:"프로그램 타입", labelWidth:110, width:83, offsetLeft:10, name:"PRGM_TYPE"
// 						, options:[{value:"", text:"전체", selected:true}
// 									, {value:"MAIN", text:"메인"}
// 									, {value:"SRCH", text:"검색"}
// 									, {value:"CREAT", text:"등록"}
// 									, {value:"RVISN", text:"변경"}
// 									, {value:"DEL", text:"삭제"}
// 									, {value:"EXEL", text:"엑셀"}
// 									, {value:"PRNT", text:"출력"}
// 									, {value:"POPUP", text:"팝업"}
// 									, {value:"ETC", text:"기타"}
// 									]
						, maxLength:10 }
					,{type : "newcolumn" , offset:20}
					,{type:"select", label:"사용여부", width:83, offsetLeft:22, name:"USG_YN"
// 						, options:[{value:"", text:"전체", selected:true} , {value:"Y", text:"사용"} , {value:"N", text:"미사용"}] 
					}
					]}
					,{type : "button",name : "btnSearch",value : "조회", className:"btn-search2"}
				                                      
  			],
		
		  onButtonClick : function(btnId) {
		
		      var form = this;
		
		      switch (btnId) {
		
		          case "btnSearch":
		        	  
					var url1 = ROOT + "/cmn/authmgmt/prgmList2.json";
					PAGE.GRID1.list(url1, form);
					break; 
		
				}
			}
		},
    
    	GRID1 : {
			css : {
					width : "620px"
					,height : "460px"
			},

	        title : "조회결과",
			header : "프로그램ID,프로그램명,타입,설명,URL,사용,메뉴ID", 
			columnIds : "PRGM_ID,PRGM_NM,PRGM_TYPE_STRING,PRGM_DESC,PRGM_URL,USG_YN,MENU_ID",
			colAlign : "left,left,center,left,left,center,center",
			widths : "150,150,80,250,250,70,50",
			colTypes : "ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str",
			paging : true,
			pagingStyle : 2,
			pageSize: 15,
			hiddenColumns: "6",
			
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

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1007",orderBy:"etc6"}, PAGE.FORM1, "PRGM_TYPE"); // 프로그램타입
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM1, "USG_YN"); // 사용여부
	    }
	};

    </script>


