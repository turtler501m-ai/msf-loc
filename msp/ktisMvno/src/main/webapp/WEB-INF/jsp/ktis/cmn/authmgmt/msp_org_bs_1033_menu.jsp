<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1033.jsp
	 * @Description : 메뉴 관리(등록, 수정) 화면
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
	<div id="GRID1" style=" margin:0px" ></div>
	<div id="FORM1" class="section-box" ></div>
	
    <!-- Script -->
    <script type="text/javascript">
    
	var vRules = {
			NotEmpty: {rule: "NotEmpty", note: "NotEmpty validation is set"}
	}
    
    var DHX = {

		GRID1 : {
			css : {
				height : "655px"
			},

			title : "메뉴",
			header : "메뉴ID,메뉴명,메뉴레벨,메뉴설명,메뉴순서,사용,OTHER,메뉴명", 
			columnIds : "menuId,menuNm,menuLvl,menuDsc,algnSeq,usgYn,ibsheetYn,menuNmHghr",
			colAlign : "left,left,center,left,center,center,center,left",
			colTypes : "tree,ro,ro,ro,ro,ro,ro,ro,ro",
            treeId: "menuId",
			widths : "210,170,60,340,60,50,60,70",
			colSorting : "str,str,str,str,str,str,str,str",
			hiddenColumns : "7",
			
			buttons : {
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				}
			},
// 			onRowDblClicked : function(rowId, celInd) {
// 				this.callEvent('onButtonClick', ['btnMod']);
//             },
			checkSelectedButtons : [ "btnMod"],
			onButtonClick : function(btnId, selectedData) {
                   
                   switch (btnId) {

						
                       case "btnReg":
                    	   //--------------------------------
                    	   // 메뉴가 선택되었을 경우만 신규등록 허용
                    	   //--------------------------------
                    	   if (PAGE.GRID1.getSelectedRowId() == null)
                    	   {
                    		   mvno.ui.alert("ICMN0103");
                    		   return;
                    	   }
                    	   
                           mvno.ui.createForm("FORM1");
                           PAGE.FORM1.setFormData(selectedData, true);
                           
                           PAGE.FORM1.setItemValue('hghrMenuId', PAGE.GRID1.getSelectedRowData().menuId);
                           PAGE.FORM1.setItemValue('menuLvl', 1 + parseInt(PAGE.GRID1.getSelectedRowData().menuLvl ));
                           PAGE.FORM1.setItemValue('inputMod', '등록' );
                           PAGE.FORM1.enableItem("menuId"); 
                           PAGE.FORM1.disableItem("hghrMenuId");
                           PAGE.FORM1.disableItem("menuLvl");

                           PAGE.FORM1.clearChanged();
                           
                           mvno.ui.popupWindowById("FORM1", "메뉴", 800, 330, {
                               onClose: function(win) {
                                   if (! PAGE.FORM1.clearChanged()) return true;
                                   mvno.ui.confirm("CCMN0005", function(){
                                       win.closeForce();
                                   });
                               }
                           });                         
                           
                           break;
                           
                       case "btnMod":

                           mvno.ui.createForm("FORM1");

                           PAGE.FORM1.setFormData(selectedData);
                           PAGE.FORM1.setItemValue('inputMod', '수정' );
                           PAGE.FORM1.disableItem("menuId");
                           PAGE.FORM1.enableItem("hghrMenuId");
                           PAGE.FORM1.enableItem("menuLvl");
                           PAGE.FORM1.clearChanged();
                           
                           mvno.ui.popupWindowById("FORM1", "메뉴", 800, 330, {
                               onClose: function(win) {
                                   if (! PAGE.FORM1.clearChanged()) return true;
                                   mvno.ui.confirm("CCMN0005", function(){
                                       win.closeForce();
                                   });
                               }
                           });                         
                           
                           break;
                   }
               }
		},

		FORM1 : {
			title : "상세정보",
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
				
				,{type:"input", label:"등록/수정", width:210, name:"inputMod", disabled:true, maxLength:10}
				,{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
				
				// 1
				,{type:"block", list:[
					{type:"input", label:"상위메뉴", width:210, name:"hghrMenuId",  required: true, validate: 'NotEmpty', maxLength:10}
					,{type:"newcolumn"}
					,{type:"input", offsetLeft:40, label:"메뉴레벨", width:210, name:"menuLvl", required: true, validate: 'NotEmpty,ValidInteger', maxLength:1}
				]}

				// 2
				,{type:"block", list:[
					{type:"input", label:"메뉴ID", width:210, name:"menuId", required: true, validate: 'NotEmpty,ValidAplhaNumeric,mvno.vxRule.excludeKorean', maxLength:10}
					,{type:"newcolumn"}
					,{type:"input", offsetLeft:40, label:"메뉴명", width:210, name:"menuNm", required: true, validate: 'NotEmpty', maxLength:50}
				]}
				
				// 3
				,{type:"block", list:[
					{type:"input", label:"메뉴순서", width:210, name:"algnSeq", required: true, validate: 'NotEmpty,ValidNumeric', maxLength:10}
					,{type:"newcolumn"}
					,{type:"select", width:60, offsetLeft:40, label:"사용여부", name:"usgYn"
						 ,options:[{value:"Y", text:"사용", selected:true},{value:"N", text:"미사용"}], required:true, maxLength:10}
					,{type:"newcolumn"}
					,{type:"select", width:60, offsetLeft:20, label:"OTHER", name:"ibsheetYn", labelWidth:66
						 ,options:[{value:"Y", text:"Y"},{value:"N", text:"N", selected:true}], required:true, maxLength:10}
				]}

				// 4
				,{type:"block", list:[
					{type:"input", label:"메뉴설명", width:554, name:"menuDsc", maxLength:250}
				]}

				],
				
				
				buttons : {
	                center : {
						btnSave : { label : "저장" },
	                    btnClose : { label: "닫기" }
	                }
				},
				
				onKeyUp : function(itemId,key) {
					var form = this;
					switch (itemId.name) {

						case "hghrMenuId":
							
							form.setItemValue(itemId.name, form.getItemValue(itemId.name).replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ) );
							form.setItemValue(itemId.name, form.getItemValue(itemId.name).toUpperCase() );
							break;	
						
						case "menuId":
							
							form.setItemValue(itemId.name, form.getItemValue(itemId.name).replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ) );
							form.setItemValue(itemId.name, form.getItemValue(itemId.name).toUpperCase() );
							break;	
					}
				
				},	
				
				
				onButtonClick : function(btnId) {

					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {

						case "btnSave":
							
//							if (form.isNew()) { // 등록 처리
							if ( form.getItemValue('inputMod') == "등록"){
	                            mvno.cmn.ajax(ROOT + "/cmn/authmgmt/menuInsert.json", form, function(result) {
	                                mvno.ui.closeWindowById(form, true);  
	                                mvno.ui.notify("NCMN0001");
	                                PAGE.GRID1.refresh4tree();
	                                
	                            });
	                        }
							else if ( form.getItemValue('inputMod') == "수정") { // 수정 처리
	                            mvno.cmn.ajax(ROOT + "/cmn/authmgmt/menuUpdate.json", form, function(result) {
	                                mvno.ui.closeWindowById(form, true);  
	                                mvno.ui.notify("NCMN0002");
	                                PAGE.GRID1.refresh4tree();
	                            });
	                            
	                        }
							break;	
							
						case "btnClose":
							
							mvno.ui.closeWindowById(form);
							break;
					}
				},
				
		}    		
	};

	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		
		buttonAuth: ${buttonAuth.jsonString},
	
	    onOpen : function() {
	        
	        mvno.ui.createGrid("GRID1");

            //detph별로 가져오기
//             PAGE.GRID1.list4tree(ROOT + "/cmn/authmgmt/treeMenuList.json", {id:'',MenuId:'0',xmlkids:false}, { callback: gridOnLoad });
            
            //트리 전체 가져오기
            PAGE.GRID1.list4tree(ROOT + "/cmn/authmgmt/treeMenuAllList.json", "", { callback: gridOnLoad });
            
	    }
	};

	function gridOnLoad  ()
	{
        var rowId = PAGE.GRID1.getRowId(0);
        PAGE.GRID1.openItem(rowId);
        
		PAGE.GRID1.forEachRow(function(id){
			if (PAGE.GRID1.cellById(id,5).getValue()=="N") 
			{
				PAGE.GRID1.setRowTextStyle(id,"color: red");
//				PAGE.GRID1.setCellTextStyle(id,6,"color: red");
			}
		});
	};
	
    </script>

	
	
