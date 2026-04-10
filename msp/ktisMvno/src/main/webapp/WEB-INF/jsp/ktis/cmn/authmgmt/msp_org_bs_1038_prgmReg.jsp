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
    <div id="FORM1" class="section-search"></div>
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
					,{type : "newcolumn" , offset:20} 
					,{type:"select", label:"프로그램 타입", width:80, name:"PRGM_TYPE"
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
					,{type:"select", label:"사용여부", width:80, name:"USG_YN"
// 						, options:[{value:"", text:"전체", selected:true} , {value:"Y", text:"사용"} , {value:"N", text:"미사용"}] 
					}
				]}
				,{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
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
    		title : "프로그램",
			css : {
					height : "550px"
			},
	
			header : "프로그램ID,프로그램명,타입,설명,URL,사용,메뉴ID", 
			columnIds : "PRGM_ID,PRGM_NM,PRGM_TYPE_STRING,PRGM_DESC,PRGM_URL,USG_YN,MENU_ID",
			colAlign : "left,left,center,left,left,center,center",
			widths : "150,150,80,250,250,70,50",
			colTypes : "ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str",
			paging : true,
			pageSize: 20,
			hiddenColumns: "6",
			
			buttons : {
                right : {
					btnReg : { label : "등록" },
					btnMod : { label: "수정" }
                }
			},
			checkSelectedButtons : ["btnMod", "btnDel"],
			onRowDblClicked : function(rowId, celInd) {
                // 수정버튼 누른것과 같이 처리?
                this.callEvent('onButtonClick', ['btnMod']);
            },
			
			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnReg":
						
						this.clearSelection();
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						PAGE.FORM2.enableItem("PRGM_ID");
						PAGE.FORM2.setItemValue('inputMod', '0' );

						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1007",orderBy:"etc6"}, PAGE.FORM2, "PRGM_TYPE"); // 프로그램타입
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM2, "USG_YN"); // 사용여부
						
						mvno.ui.popupWindowById("FORM2", "프로그램", 650, 240, {
                            onClose: function(win) {
                                if (! PAGE.FORM2.clearChanged()) return true;
                                mvno.ui.confirm("CCMN0005", function(){
                                    win.closeForce();
                                });
                            }
                        });
						
						break;
						
					case "btnMod":
						
						if (PAGE.GRID1.getSelectedRowId() == null)
						{
							mvno.ui.alert("ICMN0103");
							return;
						}
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1007",orderBy:"etc6"}, PAGE.FORM2, "PRGM_TYPE"); // 프로그램타입
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM2, "USG_YN"); // 사용여부
						PAGE.FORM2.setFormData(PAGE.GRID1.getSelectedRowData());
						PAGE.FORM2.disableItem("PRGM_ID");
						PAGE.FORM2.setItemValue('inputMod', '1' );

						
                 	   mvno.ui.popupWindowById("FORM2", "프로그램", 650, 240, {
                            onClose: function(win) {
                                if (! PAGE.FORM2.clearChanged()) return true;
                                mvno.ui.confirm("CCMN0005", function(){
                                    win.closeForce();
                                });
                            }
                        });
						
						break;
				}
			}
		},
		
		FORM2 : {
			items : [
//			         {type:"input", label:"등록/수정", width:150, name:"inputMod", disabled:true }
				{type:"hidden", name:"inputMod"}
				,{type:"hidden", name:"MENU_ID"}
				,{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
				
				// 프로그램
				,{type:"block", list:[
					{type:"input", label:"프로그램ID", width:150, name:"PRGM_ID", required:true, validate: "NotEmpty,ValidAplhaNumeric", maxLength:10 }
					,{type:"newcolumn"}
					,{type:"input", label:"프로그램명", width:150, offsetLeft:30, name:"PRGM_NM", required:true, validate: "NotEmpty", maxLength:50 }
				]}

				// 메뉴, 타입
				,{type:"block", list:[
//					{type:"input", label:"메뉴", width:150, name:"MENU_ID" , disabled:true, maxLength:10 }
//					,{type:"newcolumn"}
					{type:"select", label:"프로그램 타입", width:150, name:"PRGM_TYPE"
// 						, options:[{value:"MAIN", text:"메인", selected:true}
// 									, {value:"SRCH", text:"검색"}
// 									, {value:"CREAT", text:"등록"}
// 									, {value:"RVISN", text:"변경"}
// 									, {value:"DEL", text:"삭제"}
// 									, {value:"EXEL", text:"엑셀"}
// 									, {value:"PRNT", text:"출력"}
// 									, {value:"POPUP", text:"팝업"}
// 									, {value:"ETC", text:"기타"}
// 									]
						, maxLength:10, required:true }
					,{type:"newcolumn"}
					,{type:"select", label:"사용여부", width:150, offsetLeft:30, name:"USG_YN", required:true
// 						, options:[{value:"Y", text:"사용", selected:true}, {value:"N", text:"미사용"}] 
					}
				]}
				
				// 프로그램설명
				,{type:"block", list:[
					{type:"input", label:"프로그램설명", width:425, name:"PRGM_DESC", maxLength:100}
				]}

				// 페이지 URL
				,{type:"block", list:[
					{type:"input", label:"페이지URL", width:425, name:"PRGM_URL", required:true, validate: "NotEmpty", maxLength:100}
				]}
			],

			buttons : {
                center : {
                    btnSave : { label : "저장" },
                    btnClose : { label: "닫기" }
                }
			},
			
			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnSave":
//						console.log('@@@@'+form.getItemValue('inputMod'));
						//if (form.isNew()) { // 등록 처리
						if ( form.getItemValue('inputMod') == "0"){
							/*insert2는 메뉴 ID 필요 없음. 자동으로 'REG'등록*/
                            mvno.cmn.ajax(ROOT + "/cmn/authmgmt/prgmInsert2.json", form, function(result) {
                                mvno.ui.closeWindowById(form, true);  
                                mvno.ui.notify("NCMN0001");
                                PAGE.GRID1.refresh();
                            });
                        }
                        else if ( form.getItemValue('inputMod') == "1") { // 수정 처리
//                        	console.log('@@@@');
                            mvno.cmn.ajax(ROOT + "/cmn/authmgmt/prgmUpdate.json", form, function(result) {
                                mvno.ui.closeWindowById(form, true);  
                                mvno.ui.notify("NCMN0002");
                                PAGE.GRID1.refresh();
                            });
                            
                        }
						break;	
						
					case "btnClose":
						
						mvno.ui.closeWindowById(form);
						break;

				}
			}
//			,
//			
//            onValidateMore: function(data) {
//				  validateMaxLength(this);
//            }
		}
			

	};

	var PAGE = {
			
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		
		buttonAuth: ${buttonAuth.jsonString},
		
	    onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
//			mvno.ui.createForm("FORM3");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1007",orderBy:"etc6"}, PAGE.FORM1, "PRGM_TYPE"); // 프로그램타입
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM1, "USG_YN"); // 사용여부
	    }
	};

    </script>


