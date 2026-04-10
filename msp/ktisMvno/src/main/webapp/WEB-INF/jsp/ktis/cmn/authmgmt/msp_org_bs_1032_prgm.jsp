<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1032.jsp
	 * @Description : 프로그램 관리(등록, 수정) 화면
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
	<div class="row">
		<div id="GRID1" class="c4"></div>
		<div id="GRID2" class="c6"></div>
	</div>
	
    <!-- Script -->
    <script type="text/javascript">
    
    var DHX = {
    	GRID1 : {
    		title : "메뉴",
			css : {
					height : "655px"
			},
	
//			header : "메뉴ID,메뉴명,사용", 
//			columnIds : "MENU_ID,MENU_NM_HGHR,USG_YN",
//			colAlign : "center,left,center",
//			widths : "120,193,30",
//			colTypes : "ro,ro,ro",
//			colSorting : "str,str,str",
			
			header : "메뉴ID,메뉴명,사용", 
			columnIds : "menuId,menuNm,usgYn",
			colAlign : "left,left,center",
			widths : "180,130,50",
			colTypes : "tree,ro,ro,ro",
			treeId: "menuId",
			colSorting : "str,str,str",
			
			
			
			
			//-----------------------------------------------
			// click list -> show detail
			//-----------------------------------------------
			onRowSelect : function(rowId, celInd) {
				
				var url1 = ROOT + "/cmn/authmgmt/prgmList.json";
	            PAGE.GRID2.list(url1, {MENU_ID:this.getSelectedRowData().menuId});
			}
		},

		GRID2 : {
			title : "프로그램",
			css : {
				height : "655px"
			},

			
			header : "메뉴ID,프로그램ID,프로그램명,타입,설명,URL,메뉴명",
			//,프로그램순서,화면넓이,사용여부,전체,등록,변경,삭제,엑셀,출력
			columnIds : "MENU_ID,PRGM_ID,PRGM_NM,PRGM_TYPE_STRING,PRGM_DESC,PRGM_URL,MENU_NM_HGHR",
			colAlign : "left,left,left,center,left,left,left",
			widths : "90,90,100,50,110,112,80",
			colTypes : "ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str",
			hiddenColumns : "6",
			
			buttons : {
				right : {
					btnReg : { label : "등록" }
				}
			},
			
			checkSelectedButtons : ["btnMod"],
			onButtonClick : function(btnId) {
                   
                   switch (btnId) {
                   /* ====================================*/
                   /* 프로그램 등록 화면 추가하면서 기존 부분 주석처리   */
                   /* ====================================*/

                       case "btnReg":
                    	   //--------------------------------
                    	   // 메뉴가 선택되었을 경우만 신규등록 허용
                    	   //--------------------------------
	                	   if ( PAGE.GRID1.getSelectedRowId() == null )
	   						{
	                    		mvno.ui.alert("ICMN0103");  // 메뉴 먼저 선택하세요
	   							return;
	   						}
	           				
	                	   selectedData = PAGE.GRID1.getSelectedRowData();
	                	   console.log("selectedData:" + selectedData);
	                	   
	                	   // 프로그램이 처음 등록되면 메뉴 ID가 REG로 생성 됨.
	                	   mvno.ui.popupWindow(ROOT + "/cmn/authmgmt/prgmPop.do?MENU_ID=" + selectedData.menuId, "프로그램찾기", 680, 700, {

	                		   param : {
		                            callback : function(result) {
																	mvno.cmn.ajax(ROOT + "/cmn/authmgmt/prgmUpdate.json"
																	, {"MENU_ID":PAGE.GRID1.getSelectedRowData().menuId
																		, "PRGM_ID": result.PRGM_ID
																		, "PRGM_NM": result.PRGM_NM
																		, "PRGM_DESC": result.PRGM_DESC
																		, "PRGM_URL": result.PRGM_URL
																		, "USG_YN": result.USG_YN
																		, "PRGM_TYPE": result.PRGM_TYPE
																		,"UPPR_PRGM_ID":result.PRGM_ID.toUpperCase()
																		} 
																	, function(result) {
																		mvno.ui.notify("NCMN0001");
																		PAGE.GRID2.refresh();     
																	});
																}							
								}
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
	        
	        mvno.ui.createGrid("GRID1");
	        mvno.ui.createGrid("GRID2");
	        
//	        var url1 = ROOT + "/cmn/authmgmt/menuList.json";
//            PAGE.GRID1.list("/cmn/authmgmt/menuList.json", null , {  callback : gridOnLoad });
            
	          // 기존 HOME 메뉴 하나 가져 옴
//            PAGE.GRID1.list4tree(ROOT + "/cmn/authmgmt/treeMenuList.json", {id:'',menuId:'0',xmlkids:false}, { callback: gridOnLoad });

	      //트리 전체 가져오기
            PAGE.GRID1.list4tree(ROOT + "/cmn/authmgmt/treeMenuAllList.json", "", { callback: gridOnLoad });
            
	    }
	};

	function gridOnLoad  ()
	{
		var rowId = PAGE.GRID1.getRowId(0);
		PAGE.GRID1.openItem(rowId);
	        
		PAGE.GRID1.forEachRow(function(id){
			if (PAGE.GRID1.cellById(id,2).getValue()=="N") 
			{
				PAGE.GRID1.setRowTextStyle(id,"color: red");
//				PAGE.GRID1.setCellTextStyle(id,2,"color: red");
			}
		});
	};
	

    </script>


