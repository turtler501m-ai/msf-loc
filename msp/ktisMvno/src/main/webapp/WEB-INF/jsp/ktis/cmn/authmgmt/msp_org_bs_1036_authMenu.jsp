<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1036.jsp
	 * @Description : 권한별메뉴등록
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
		<div id="FORM1" class="c5">
			<div id="FORM11" class="section-search"></div>
			<div id="FORM12" >
				<div id="GRID11" ></div>
			</div>
		</div>
		<div id="FORM2" class="c5" >
			<div id="FORM21" class="section-search" style="left:20px;"></div>
			<div id="GRID21"></div>
		</div>
	</div>
	
	<div id="GRID1"></div>
	<div id="FORM3" class="section-box"></div>
	
	<!-- Script -->
    <script type="text/javascript">
    
    var DHX = {
    	FORM11 : {
    		title : "권한",
	    	items :  [
  				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
  		
  				// 권한
  				,{type:"block", list:[
  						{type:"input", label:"권한명", width:100, offsetLeft:10, name:"searchAuthNm", maxLength:100}
  						,{type:"newcolumn"}
  						,{type:"select", label:"사용여부", name:"searchUsgYn", width:70, offsetLeft:20//, userdata:{lov:"CLS0005"}}
  							, options:[{value:"Y", text:"사용", selected:true}, {value:"N", text:"미사용"}]
  						}
  				]},

  				// 조회 버튼
  				,{type:"newcolumn", offset:60}
  				,{type:"button", value:"조회", className:"btn-search1", name:"btnSearch"}/* btn-search(열갯수1~4) */
  				],
  				
			onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId){
				case "btnSearch" :
					
					var url = ROOT + "/cmn/authmgmt/authList.json"; 
					PAGE.GRID11.list(url, form);
					
					// 권한 조회 버튼 누르면 GRID1, FORM3 권한 ID 초기화 
					PAGE.GRID1.clearAll();
					PAGE.FORM3.setItemValue("AUTH_ID", "");
					
					break;
				}
			},
    
    	},
    	
    	GRID11 : {
				css : {
					height : "190px",
					top : 0,
					bottom : 0,
					left : 0,
					right : 0
				},

				header : "권한ID,권한명,권한설명,사용", 
				columnIds : "AUTH_ID,AUTH_NM,AUTH_DSC,USG_YN",
				widths : "120,120,168,30",
				colAlign : "left,left,left,center",
				colTypes : "ro,ro,ro,ro",
				colSorting : "str,str,str,str",
				
				//-----------------------------------------------
				// click list -> show detail list
				//-----------------------------------------------
				onRowSelect : function(rowId, celInd) {
					selectedData = PAGE.GRID11.getSelectedRowData();
					console.log("selectedData:" + selectedData);
					
					var url = ROOT + "/cmn/authmgmt/authMenuList.json";
					PAGE.GRID1.list(url, selectedData);
					
					PAGE.FORM3.setItemValue("SRCH_AUTH", "Y");
					PAGE.FORM3.setItemValue("RVISN_AUTH", "Y");
					PAGE.FORM3.setItemValue("DEL_AUTH", "Y");
					PAGE.FORM3.setItemValue("EXEL_ABABLE_YN", "Y");
					PAGE.FORM3.setItemValue("PRNT_ABABLE_YN", "Y");
					PAGE.FORM3.setItemValue("CREAT_ABABLE_YN", "Y");

					// form으로 권한ID, 권한 이름 넘김.
					PAGE.FORM3.setItemValue("AUTH_ID", selectedData.AUTH_ID);	
					PAGE.FORM3.setItemValue("AUTH_NM", selectedData.AUTH_NM);
				}
	    },
	    
	    FORM21 : {
	    	title : "메뉴",
	    	items :  [
  				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
  		
  				// 메뉴명
  				,{type:"block", list:[
  						{type:"input", label:"메뉴명", width:100, offsetLeft:10, name:"searchMenuNm", maxLength:50}
  						,{type:"newcolumn"}
  						,{type:"input", label:"메뉴레벨", width:50, offsetLeft:20, name:"searchMenuLvl", validate: "ValidInteger", maxLength:1}
  				]},

  				// 조회 버튼
  				,{type:"newcolumn", offset:30}
  				,{type:"button", value:"조회", className:"btn-search1", name:"btnSearch"}/* btn-search(열갯수1~4) */
  				],
  		
			onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId){
				case "btnSearch" :
						
					// 메뉴 리스트
//					var url = ROOT + "/cmn/authmgmt/menuList.json"; 
//					PAGE.GRID21.list(url, form , {  callback: gridOnLoad });
					
					// 메뉴 리스트( 트리 구조)
					PAGE.GRID21.list4tree(ROOT + "/cmn/authmgmt/treeMenuList.json", form, { callback: gridOnLoad });
					
					// 메뉴 조회 버튼 누르면 GRID1 의 selection과 FORM3 의 메뉴 관련 항목 초기화
//					PAGE.GRID1.clearSelection();
//					PAGE.FORM3.setItemValue("MENU_ID", "");
//					PAGE.FORM3.setItemValue("MENU_NM", "");
//					PAGE.FORM3.setItemValue("MENU_LVL", "");
					
					break;
				}
			},
    	},
    	
    	GRID21 : {
				css : {
					height : "190px",
					top : 0,
					bottom : 0,
					left : 0,
					right : 0
				},

//				header : "메뉴ID,메뉴명,레벨,메뉴설명,메뉴명", 
//				columnIds : "MENU_ID,MENU_NM_HGHR,MENU_LVL,MENU_DSC,MENU_NM",
//				widths : "120,120,30,168,120",				  
//				colAlign : "center,left,center,left,left",
//				colTypes : "ro,ro,ro,ro,ro",
//				colSorting : "str,str,str,str,str",
//				hiddenColumns : "4",  
					
				header : "메뉴ID,메뉴명,레벨,메뉴설명,사용",//,메뉴명", 
				columnIds : "menuId,menuNm,menuLvl,menuDsc,usgYn",
				widths : "230,170,30,230,30",				
				colAlign : "left,left,center,left,center",
				colTypes : "tree,ro,ro,ro,ro,ro",//,ro",
				treeId: "menuId",
				colSorting : "str,str,str,str,str",//,str",
				
				//-----------------------------------------------
				// 더블클릭 : onRowSelect 호출 ->btnNew 호출
				//-----------------------------------------------
				checkSelectedButtons : ["btnReg"],
//				onRowDblClicked : function(rowId, celInd) {
//					// 이미 존재하는 메뉴 걸러냄
//					this.callEvent('onRowSelect');
//					
//					// 중복 아니면 btnReg 호출
//					if (PAGE.GRID21.getSelectedRowData() == null || PAGE.GRID21.getSelectedRowData() == '' )
//					{
//						return;
//					}
//					else
//					{
//						PAGE.FORM3.callEvent('onButtonClick', ['btnReg']);
//					}
//					
//				},

				onRowSelect : function(rowId, celInd) {
					
					selectedData = PAGE.GRID21.getSelectedRowData();
					console.log("selectedData:" + selectedData);
					
					//-----------------------------------------------
					// 권한-메뉴(GRID1)에 이미 존재하는 메뉴는 선택 안되게 함.
					//-----------------------------------------------
					
					for (var inx=0; inx<PAGE.GRID1.getRowsNum(); inx++)
					{
//						if ( PAGE.GRID1.cells(PAGE.GRID1.getRowId(inx), 1).getValue() == selectedData.MENU_ID)
						if ( PAGE.GRID1.cells(PAGE.GRID1.getRowId(inx), 1).getValue() == selectedData.menuId)
						{
							PAGE.GRID21.clearSelection();
							return;
						}else
						{
							PAGE.GRID1.clearSelection();
						}
					}

					// FORM3 에 메뉴 ID, 메뉴명, 메뉴 레벨 세팅
					mvno.cmn.ajax(	ROOT + "/cmn/authmgmt/menuDetail.json", 
//									selectedData,
									{MENU_ID:selectedData.menuId},
									function(result) {
										console.log("result.result.data" + result.result.data);

										PAGE.FORM3.setItemValue("MENU_ID", result.result.data.MENU_ID);
										PAGE.FORM3.setItemValue("MENU_NM", result.result.data.MENU_NM);
										PAGE.FORM3.setItemValue("MENU_LVL", result.result.data.MENU_LVL);
									});
					
					// FORM3 에 YN 세팅
					PAGE.FORM3.setItemValue("SRCH_AUTH", "Y");
					PAGE.FORM3.setItemValue("RVISN_AUTH", "Y");
					PAGE.FORM3.setItemValue("DEL_AUTH", "Y");
					PAGE.FORM3.setItemValue("EXEL_ABABLE_YN", "Y");
					PAGE.FORM3.setItemValue("PRNT_ABABLE_YN", "Y");
					PAGE.FORM3.setItemValue("CREAT_ABABLE_YN", "Y");
					
					// 수정, 삭제 버튼 비활성화
				}
	    },
	    
	    GRID1 : {
	    	title : "권한-메뉴",
			css : {
				height : "220px"
			},
	
			header : "권한ID,메뉴ID,메뉴명,검색,등록,변경,삭제,엑셀,출력", 
			//메뉴명, 전체 확인 필요
			columnIds : "AUTH_ID,MENU_ID,MENU_NM,SRCH_AUTH,CREAT_ABABLE_YN,RVISN_AUTH,DEL_AUTH,EXEL_ABABLE_YN,PRNT_ABABLE_YN",
			widths : "120,120,270,70,70,70,70,70,70",		
			colAlign : "left,left,left,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str",
			
			//-----------------------------------------------
			// 더블클릭 : btnDel
			//-----------------------------------------------
			checkSelectedButtons : ["btnDel"],
//			onRowDblClicked : function(rowId, celInd) {
//					PAGE.FORM3.callEvent('onButtonClick', ['btnDel']);
//
//			},
			
			onRowSelect : function(rowId, celInd) {
				
				// 권한-메뉴 선택하면 메뉴(GRID21) 비활성화
				PAGE.GRID21.clearSelection();
				
				selectedData = PAGE.GRID1.getSelectedRowData();
				console.log("selectedData:" + selectedData);

				// 권한 ID, 메뉴 ID, Y/N 가져 옴
				mvno.cmn.ajax(	ROOT + "/cmn/authmgmt/authMenuDetail.json", 
								selectedData,
								function(result) {
									console.log("result.result.data:" + result.result.data);
									PAGE.FORM3.setFormData(result.result.data);

								}
							);
				
				// 메뉴 ID, 메뉴명, 메뉴 레벨
				mvno.cmn.ajax(	ROOT + "/cmn/authmgmt/menuDetail.json", 
								selectedData,
								function(result) {
									console.log("result.result.data" + result.result.data);

									PAGE.FORM3.setItemValue("MENU_ID", result.result.data.MENU_ID);
									PAGE.FORM3.setItemValue("MENU_NM", result.result.data.MENU_NM);
									PAGE.FORM3.setItemValue("MENU_LVL", result.result.data.MENU_LVL);
								}
							);
				// 등록 버튼 비활성화
			}
	    },
	    
	    FORM3 : {
    	
	    	title : "권한-메뉴 상세",
			items :  [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0, offsetTop:2}
		
				// 1열
				,{type:"block", list:[
						{type:"input", label:"권한ID", offsetLeft:10, width:150, name:"AUTH_ID", disabled:true}
						,{type:"newcolumn"}
						,{type:"input", label:"메뉴ID", offsetLeft:40, width:150, name:"MENU_ID", disabled:true}
						,{type:"newcolumn"}
						,{type:"input", label:"메뉴명", offsetLeft:40, width:150, name:"MENU_NM", disabled:true}
						,{type:"newcolumn"}
						,{type:"input", label:"메뉴레벨", offsetLeft:40, width:80, labelWidth:60, name:"MENU_LVL", disabled:true}
						
				]},
				
				// 2열
				{type:"block", list:[
						{type:"select", label:"검색", name:"SRCH_AUTH", width:60, offsetLeft:10, labelWidth:40
							, options:[{value:"Y", text:"Y", selected:true},{value:"N", text:"N"}]}
						,{type:"newcolumn"}
						,{type:"select", label:"등록", name:"CREAT_ABABLE_YN", width:60, offsetLeft:45, labelWidth:40
							, options:[{value:"Y", text:"Y", selected:true},{value:"N", text:"N"}]}
						,{type:"newcolumn"}
						,{type:"select", label:"변경", name:"RVISN_AUTH", width:60, offsetLeft:45, labelWidth:40
							, options:[{value:"Y", text:"Y", selected:true},{value:"N", text:"N"}]}
						,{type:"newcolumn"}
						,{type:"select", label:"삭제", name:"DEL_AUTH", width:60, offsetLeft:45, labelWidth:40
							, options:[{value:"Y", text:"Y", selected:true},{value:"N", text:"N"}]}
						,{type:"newcolumn"}
						,{type:"select", label:"엑셀", name:"EXEL_ABABLE_YN", width:60, offsetLeft:45, labelWidth:40
							, options:[{value:"Y", text:"Y", selected:true},{value:"N", text:"N"}]}
						,{type:"newcolumn"}
						,{type:"select", label:"출력", name:"PRNT_ABABLE_YN", width:60, offsetLeft:50, labelWidth:40
							, options:[{value:"Y", text:"Y", selected:true},{value:"N", text:"N"}]}
				]},
				 
				
				
			],
			
			buttons : {
				right : {
					btnReg : { label : "메뉴추가" },
					btnMod : { label : "수정" },
					btnDel : { label : "삭제" }
				}
			},
						
	    	onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {
					case "btnReg":
						//  권한 먼저 선택하도록.
						if (PAGE.GRID11.getSelectedRowId() == null)
						{
							mvno.ui.alert("ICMN0102");
							return;
						}
						else if (PAGE.GRID21.getSelectedRowId() == null)
						{
							mvno.ui.alert("ICMN0103");
							return;
						}
							
						mvno.cmn.ajax(ROOT + "/cmn/authmgmt/authMenuInsert.json", form, function(result) {
							mvno.ui.notify("NCMN0001");
							PAGE.GRID1.refresh();
// 							PAGE.GRID21.refresh();
						});
						break;			
					case "btnMod":
						if ( PAGE.GRID1.getSelectedRowId() == null )
						{
							mvno.ui.alert("ICMN0104");  // 권한을 먼저 선택하세요
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/cmn/authmgmt/authMenuUpdate.json", form, function(result) {
							mvno.ui.notify("NCMN0002");
							PAGE.GRID1.refresh();
						});
						break;
						
					case "btnDel":
						if ( PAGE.GRID1.getSelectedRowId() == null )
						{
							mvno.ui.alert("ICMN0104");  // 권한을 먼저 선택하세요
							return;
						}
						mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/cmn/authmgmt/authMenuDelete.json", form, function(result) {
							mvno.ui.notify("NCMN0003");
							
							// GRID1, FORM3 refresh, clear
							PAGE.GRID1.refresh();
							
							PAGE.FORM3.clear();
							selectedData = PAGE.GRID11.getSelectedRowData();
							PAGE.FORM3.setItemValue("AUTH_ID", selectedData.AUTH_ID);	
						});
						break;
				}
			}
	    },
    };
    
    var PAGE = {
    	title : "${breadCrumb.title}",
    	breadcrumb : " ${breadCrumb.breadCrumb}",


		buttonAuth: ${buttonAuth.jsonString},
		
    	onOpen : function(){

    		mvno.ui.createForm("FORM11");
    		mvno.ui.createGrid("GRID11");
    		mvno.ui.createForm("FORM21");
    		mvno.ui.createGrid("GRID21");
    		mvno.ui.createGrid("GRID1");
    		mvno.ui.createForm("FORM3");

	        var url1 = ROOT + "/cmn/authmgmt/authMenuList.json";
//            PAGE.GRID1.list(url1, form );

    	}
    };
    
    function gridOnLoad  ()
	{
		PAGE.GRID21.forEachRow(function(id){
			if (PAGE.GRID21.cellById(id,4).getValue()=="N") 
			{
				PAGE.GRID21.setRowTextStyle(id,"color: red");
//				PAGE.GRID21.setCellTextStyle(id,5,"color: red");
			}
		});
	};  
    
    

	</script>

    
