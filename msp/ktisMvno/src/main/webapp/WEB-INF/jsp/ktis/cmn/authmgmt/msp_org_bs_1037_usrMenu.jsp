<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1037.jsp
	 * @Description : 특정 사용자 메뉴 권한 관리 화면
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
				<div id="GRID11"></div>
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
    		
    		title : "사용자",
	    	items :  [
  				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
  				
//  				// 1열
//  				,{type:"block", list:[
//  						,{type:"select", label:"소속구분", name:"ATTC_SCTN_CD", width:100, offsetLeft:10 
//  							,userdata:{lov: 'CMN0003'}},
//  						,{type:"newcolumn"}
//  						,{type:"select", label:"부서/업체", name:"ORGN_ID", width:100, offsetLeft:20
//  							, userdata:{lov: 'CMN0006'}}
//  				]},
  				
  				// 2열
  				,{type:"block", list:[
  						{type:"input", label:"사용자ID", name:"USR_ID", width:100, offsetLeft:10, maxLength:10}
  						,{type:"newcolumn"}
  						,{type:"input", label:"사용자명", name:"USR_NM", width:100, offsetLeft:20, maxLength:50}
  				]}
  				
//  				,{type:"newcolumn", offset:30}
//  				,{type:"button", value:"조회", name:"btnSearch2", offsetTop:10}/* btn-search(열갯수1~4) */
  				,{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
  			],
  		
  			onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId){
					case "btnSearch" :
						
	//					if (! this.validate())  return;
						
						// 사용자 검색					
//						var url = ROOT + "/org/userinfomgmt/userInfoMgmtList.json"; 
						var url = ROOT + "/cmn/authmgmt/userInfoMgmtList.json";
						PAGE.GRID11.list(url, form);
						
						// 사용자 조회 버튼 누르면 GRID1, FORM3 권한 ID 초기화 
						PAGE.GRID1.clearAll();
	//					PAGE.FORM3.clear();
						PAGE.FORM3.setItemValue("AUTH_ID", "");
						
						break;
					}
			}
			
    	},
    	
    	GRID11 : {
			
			css : {
				height : "190px"
			},
			
			// 사용자 정보
			header : "사용자ID,사용자명,부서,소속구분,사번,전화번호,핸드폰번호,이메일,등록일,USR_ID",
			columnIds : "USR_ID_MSK,USR_NM,DEPT_NM,ATTC_SCTN_CD_NM,PRES_BIZ,TEL_NUM,MBLPHN_NUM,EMAIL,REGST_DTTM,USR_ID",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			widths : "100,100,90,90,80,100,100,120,100,0",
			colAlign : "left,left,left,left,center,center,center,left,center,center",
			hiddenColumns:[9],
//			paging : true,
//            pageSize : 10,
			//-----------------------------------------------
			// click list -> show detail list (GRID1)
			//-----------------------------------------------
			onRowSelect : function(rowId, celInd) {

				selectedData = PAGE.GRID11.getSelectedRowData();
				console.log("selectedData:" + selectedData);
				
				var url = ROOT + "/cmn/authmgmt/usrMenuAsgnList.json"; 
				PAGE.GRID1.list(url, selectedData);

//				PAGE.FORM3.clear();
				
				PAGE.FORM3.setItemValue("SRCH_AUTH", "Y");
				PAGE.FORM3.setItemValue("RVISN_AUTH", "Y");
				PAGE.FORM3.setItemValue("DEL_AUTH", "Y");
				PAGE.FORM3.setItemValue("EXEL_ABABLE_YN", "Y");
				PAGE.FORM3.setItemValue("PRNT_ABABLE_YN", "Y");
				PAGE.FORM3.setItemValue("CREAT_ABABLE_YN", "Y");

				PAGE.FORM3.setItemValue("USR_ID_MSK", selectedData.USR_ID_MSK);	
				PAGE.FORM3.setItemValue("USR_ID", selectedData.USR_ID);	
				PAGE.FORM3.setItemValue("USR_NM", selectedData.USR_NM);
				
			}
	    },
	    
	    FORM21 : {
	    	title : "메뉴",
	    	items :  [
  				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
//  				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0, offsetTop:10}
  		
  				// 메뉴명
  				,{type:"block", list:[
  						{type:"input", label:"메뉴명", width:100, offsetLeft:10, name:"searchMenuNm", maxLength:50}
  						,{type:"newcolumn"}
  						,{type:"input", label:"메뉴레벨", width:50, offsetLeft:40, name:"searchMenuLvl", validate: "ValidInteger", maxLength:1}
  				]},
  			
  				// 조회 버튼
  				,{type:"newcolumn", offset:30}
  				,{type:"button", value:"조회", className:"btn-search1", name:"btnSearch"}/* btn-search(열갯수1~4) */
  				],
  				
			onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId){
				case "btnSearch" :
					
//					if (! this.validate())  return;

					// 메뉴 리스트
//					var url = ROOT + "/cmn/authmgmt/menuList2.json"; 
//					PAGE.GRID21.list(url, form , {  callback: gridOnLoad });
					
					// 메뉴 리스트(트리 구조)
					PAGE.GRID21.list4tree(ROOT + "/cmn/authmgmt/treeMenuList.json", form, { callback: gridOnLoad });
					
					// 사용자 조회 버튼 누르면 GRID1 의 selection과 FORM3 의 메뉴 관련 항목 초기화
//					PAGE.GRID1.clearSelection();
//					PAGE.FORM3.setItemValue("MENU_ID", "");
//					PAGE.FORM3.setItemValue("MENU_NM", "");
//					PAGE.FORM3.setItemValue("MENU_LVL", "");
					
//		            PAGE.GRID1.loadXML("/cmn/authmgmt/menuHghrListXml.json");
					break;
				}
			}	
    	},
    	
    	GRID21 : {
				css : {
					height : "190px"
				},

				header : "메뉴ID,메뉴명,레벨,메뉴설명,사용",//,메뉴명", 
//				columnIds : "MENU_ID,MENU_NM_HGHR,MENU_LVL,MENU_DSC,USG_YN",//,MENU_NM",
				columnIds : "menuId,menuNm,menuLvl,menuDsc,usgYn",
				widths : "230,170,30,230,30",				
				colAlign : "left,left,center,left,center",
				colTypes : "tree,ro,ro,ro,ro,ro",//,ro",
				treeId: "menuId",
				colSorting : "str,str,str,str,str",//,str",
				
				
				//-----------------------------------------------
				// 더블클릭 : onRowSelect 호출 ->btnReg 호출
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
					// 사용자-메뉴(GRID1)에 이미 존재하는 메뉴는 선택 안되게 함.
					//-----------------------------------------------
					
					for (var inx =0; inx<PAGE.GRID1.getRowsNum(); inx++)
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
						
					// FORM3 에 메뉴 ID, 메뉴명 세팅
					mvno.cmn.ajax(	ROOT + "/cmn/authmgmt/menuDetail.json", 
//							selectedData,
							{MENU_ID:selectedData.menuId},
							function(result) {
								console.log("result.result.data : " + result.result.data);
								
								PAGE.FORM3.setItemValue("MENU_ID", result.result.data.MENU_ID);
								PAGE.FORM3.setItemValue("MENU_NM", result.result.data.MENU_NM);
							}
						);
					
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
	    	title : "사용자-메뉴",
			css : {
				height : "220px"
			},
	
			header : "사용자ID,메뉴ID,메뉴명,검색,등록,변경,삭제,엑셀,출력,USR_ID", 
			columnIds : "USR_ID_MSK,MENU_ID,MENU_NM,SRCH_AUTH,CREAT_ABABLE_YN,RVISN_AUTH,DEL_AUTH,EXEL_ABABLE_YN,PRNT_ABABLE_YN,USR_ID",
			widths : "120,120,270,70,70,70,70,70,70,0",			
			colAlign : "center,center,left,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str",
			hiddenColumns:[9],
			
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

				// 사용자 ID, 사용자이름, 메뉴ID
				mvno.cmn.ajax(	ROOT + "/cmn/authmgmt/usrMenuAsgnDetail.json", 
								selectedData,
								function(result) {
									console.log("result.result.data : " + result.result.data);
									PAGE.FORM3.setFormData(result.result.data);
								}
							);
				
				//메뉴명
				mvno.cmn.ajax(	ROOT + "/cmn/authmgmt/menuDetail.json", 
						selectedData,
						function(result) {
							console.log("result.result.data : " + result.result.data);
//							PAGE.FORM3.setItemValue("MENU_ID", result.result.data.MENU_ID);
							PAGE.FORM3.setItemValue("MENU_NM", result.result.data.MENU_NM);
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
						{type:"input", label:"사용자 ID", labelWidth:60, offsetLeft:10, width:150, name:"USR_ID_MSK", disabled:true}
						,{type:"hidden", name:"USR_ID"}
						,{type:"newcolumn"}
						,{type:"input", label:"사용자 이름", labelWidth:70, offsetLeft:40, width:150, name:"USR_NM", disabled:true}
						,{type:"newcolumn"}
						,{type:"input", label:"메뉴ID", offsetLeft:40, width:150, name:"MENU_ID", disabled:true}
						,{type:"newcolumn"}
						,{type:"input", label:"메뉴명" ,offsetLeft:40, width:80, name:"MENU_NM", disabled:true}
						
				]},

				// 2열
				,{type:"block", list:[
						{type:"select", label:"검색", name:"SRCH_AUTH", width:60, offsetLeft:10, labelWidth:60
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
						,{type:"select", label:"출력", name:"PRNT_ABABLE_YN", width:60, offsetLeft:50, labelWidth:42
							, options:[{value:"Y", text:"Y", selected:true},{value:"N", text:"N"}]}
						,{type:"newcolumn"}
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
						// 사용자 먼저 선택하도록.
						if (PAGE.GRID11.getSelectedRowId() == null)
						{
							mvno.ui.alert("ICMN0105");
							return;
						}
						else if (PAGE.GRID21.getSelectedRowId() == null)
						{
							mvno.ui.alert("ICMN0103");
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/cmn/authmgmt/usrMenuAsgnInsert.json", form, function(result) {
							mvno.ui.notify("NCMN0001");
							PAGE.GRID1.refresh();
						});
						break;			
					case "btnMod":
						// 수정될 메뉴선택 확인
						if (PAGE.GRID1.getSelectedRowId() == null)
						{
							mvno.ui.alert("수정할 사용자-메뉴를 선택해주세요.");
							return;
						}
						mvno.cmn.ajax(ROOT + "/cmn/authmgmt/usrMenuAsgnUpdate.json", form, function(result) {
							mvno.ui.notify("NCMN0002");
							PAGE.GRID1.refresh();
						});
						break;
					case "btnDel":
						if ( PAGE.GRID1.getSelectedRowId() == null )
						{
							mvno.ui.alert("ICMN0106");  // 사용자-메뉴를 먼저 선택하세요
							return;
						}
						
						mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/cmn/authmgmt/usrMenuAsgnDelete.json", form, function(result) {
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
//    		mvno.ui.createForm("FORM4");
    		
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

    
