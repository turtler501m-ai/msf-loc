<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.*" %>

<%
  /**
  * @Class Name : msp_cls_bs_4001.jsp
  * @Description : 여신관리 - 대리점 여신정보를 조회하는 화면
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2009.02.01            최초 생성
  * 
  * author  
  * since 
  *
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>


    <!-- 화면 배치 -->
    <div id="FORM1" class="section-search"></div>
    <div id="GRID1"></div>
    <div id="GRID2"></div>
    
 
    <!-- Script -->
    <script type="text/javascript">

        var DHX = { 

            // ----------------------------------------
            FORM1 : { 

//                title : "조회조건",
                items : [ 
                         {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
                         
                         {type:"block", labelWidth:30 , list:[     
		                    { type : "input", label : "사용자", name : "searchUsrNm", width : 100, offsetLeft : 10,  disabled:true}, 
		                    { type : "newcolumn" }, 
		                    { type : "input", label : "", name : "searchUsrId" , width : 60, disabled:true}, 
		                    { type : "newcolumn" }, 
		                    {type:"button", value:"찾기", name:"btnUseFind"},
		                    { type : "newcolumn" , offset:30}, 
							]},
							{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
						],
                onButtonClick : function(btnId) {

                    var form = this;

                    
                    switch (btnId) {

							
						case "btnUseFind":
							mvno.ui.codefinder("USER", function(result) {
								form.setItemValue("searchUsrNm", result.usrNm);
						        form.setItemValue("searchUsrId", result.usrId);
						        
//						        this.callEvent('onButtonClick', ['btnSearch']);
						        var url1 = ROOT + "/cmn/authmgmt/usrAuthReportList.json";
	                            PAGE.GRID1.list(url1, form, {  callback : gridOnLoad });
	                            gridOnLoad(); 
							}); 
							
						   break;  
                        case "btnSearch":

                        	var url1 = ROOT + "/cmn/authmgmt/usrAuthReportList.json";
                            PAGE.GRID1.list(url1, form, {  callback : gridOnLoad });
                            gridOnLoad(); 
                            
                            
                            break; 

                    }

                }
            },

            // ----------------------------------------
            GRID1 : {

            	css : { 
                    height : "620px"  
                }, 
                title : "사용자 권한",
                header  : "번호,메뉴ID,메뉴명,권한타입,메뉴사용,그룹ID,그룹명,그룹사용,권한ID,권한명,권한사용,프로그램타입,프로그램명,등록,삭제,수정,검색,엑셀,출력,메인화면경로,경로",
                columnIds : "ROW_INDEX,MENU_ID,MENU_NM_HGHR,AUTH_TYPE,MENU_USG_YN,GRP_ID,GRP_NM,GRP_USG_YN,AUTH_ID,AUTH_NM,AUTH_USG_YN,PRGM_TYPE,PRGM_NM,CREAT_ABABLE_YN,DEL_AUTH,RVISN_AUTH,SRCH_AUTH,EXEL_ABABLE_YN,PRNT_ABABLE_YN,PRGM_URL_MAIN,PRGM_URL",
                //columnIds : "USR_ID,USR_NM,MENU_ID,MENU_NM,DUTY_NM,IP_INFO,CONN_DT",
                widths : "30,100,170,70,60,100,120,60,100,120,60,100,120,40,40,40,40,40,40,180,180",
                colAlign : "center,left,left,center,center,left,left,center,left,left,center,center,center,center,center,center,center,center,center,left,left",
                colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
                colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
//                paging : true,
                //pageSize : 15,
                
                //onRowSelect : function(rowID, celInd) {
                //    console.log("Row Selected ..." + rowID + ':' + celInd);
                    // 아마도 Popup Window 처리...(다양한 Pattern 처리)
                //},
                
                
            }

            


        };

        var PAGE = {
        	title : "${breadCrumb.title}",
        	breadcrumb : " ${breadCrumb.breadCrumb}",
    		
    		buttonAuth: ${buttonAuth.jsonString},
   		
            onOpen : function() {

                mvno.ui.createForm("FORM1");
                mvno.ui.createGrid("GRID1");

//                mvno.cmn.ajax4lov(ROOT + "/cmn/codefind/cmnMenuMst.json", null, PAGE.FORM1, "searchMenuList");
                
            }

        };

        function gridOnLoad()
        {
        	PAGE.GRID1.forEachRow(function(id){
        		if (PAGE.GRID1.cellById(id,4).getValue()=="N") 
        		{
        			PAGE.GRID1.setCellTextStyle(id,4,"color: red");
        		}
        		if (PAGE.GRID1.cellById(id,7).getValue()=="N") 
        		{
        			PAGE.GRID1.setCellTextStyle(id,6,"color: red");
        		}
        		if (PAGE.GRID1.cellById(id,10).getValue()=="N") 
        		{
        			PAGE.GRID1.setCellTextStyle(id,8,"color: red");
        		}
        		for( var inx = 13; inx <= 18; inx++)
        		{
	        		if (PAGE.GRID1.cellById(id,inx).getValue()=="N") 
	        		{
	        			PAGE.GRID1.setCellTextStyle(id,inx,"color: red");
	        		}
        		}
        	});
        }
    </script>


