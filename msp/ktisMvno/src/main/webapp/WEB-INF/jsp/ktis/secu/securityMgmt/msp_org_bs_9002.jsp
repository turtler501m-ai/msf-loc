<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.*" %>

<%
  /**
  * @Class Name : msp_org_bs_9002.jsp
  * @Description : 이력관리 - 접속실패자 파일다운
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2019.01.17              최초 생성
  * 
  * author  
  * since 
  *
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>

    <!-- 화면 배치 -->
    <div id="FORM1" class="section-search"></div>
    <div id="GRID1" class="section-full" ></div>
    
 
    <!-- Script -->
    <script type="text/javascript">

        var DHX = { 

            // ----------------------------------------
            FORM1 : { 

//                title : "조회조건",
                items : [ 
                    { type : "settings", position : "label-left", labelAlign : "left", labelWidth : 60, blockOffset : 0 },                    
                    { type : "block", labelWidth : 30 , list : [
	                    { type : "calendar" , name:"searchStrtDt", label:"조회기간", id:"searchStrtDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth : 80, width:100 },
						{ type : "newcolumn" },
						{ type : "calendar" , name:"searchEndDt", id:"searchEndDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"~", labelWidth:30, labelAlign:"center", width:100 },
					]},
					{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],

                onButtonClick : function(btnId) {

                    var form = this;

                    
                    switch (btnId) {
                        case "btnSearch" :

                        	var url = ROOT + "/secu/securityMgmt/accessFailFileDownList.json";
                            PAGE.GRID1.list(url, form);

                            break;
                    }

                }
            },

            // ----------------------------------------
            GRID1 : {

            	css : { 
                    height : "600px"  
                }, 
                title : "접속실패자 파일다운",
                header : "파일다운로드일시,사용자ID,사용자명,조직ID,조직명,사유,메뉴명,파일명",
                columnIds : "FILE_DN_DT,USR_ID,USR_NM,ORGN_ID,ORGN_NM,DWNLD_RSN,MENU_NM,FILE_NM",
                widths : "140,120,120,120,120,160,120,160",
                colAlign : "center,center,center,center,center,left,left,left",
                colTypes : "ro,ro,ro,ro,ro,ro,ro,ro",
                colSorting : "str,str,str,str,str,str,str,str",
                paging : true,
                pageSize : 20,
                buttons : {
    				hright : {
    					btnExcel : { label : "엑셀다운로드" }
    				}
    			},
    			onButtonClick : function(btnId, selectedData) {
    				switch (btnId) {
    					case "btnExcel" :
    						if(PAGE.GRID1.getRowsNum() == 0){
    							mvno.ui.alert("데이터가 존재하지 않습니다");
    							return;
    						}
    						
    						var searchData =  PAGE.FORM1.getFormData(true);
    						mvno.cmn.download(ROOT + "/secu/securityMgmt/accessFailFileDownListExcel.json?menuId=MSP9001002", true, {postData:searchData}); 
    						
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

                mvno.ui.createForm("FORM1");
                mvno.ui.createGrid("GRID1");
				
                var today = new Date();
                var preMonth = new Date();
                preMonth.setMonth(preMonth.getMonth() -1);
                
                PAGE.FORM1.setItemValue("searchStrtDt", "" + preMonth.getFullYear() + ('0' + (preMonth.getMonth() + 1)).slice(-2)  + ('0' + preMonth.getDate()).slice(-2) );
                PAGE.FORM1.setItemValue("searchEndDt", "" + today.getFullYear() + ('0' + (today.getMonth() + 1)).slice(-2)  + ('0' + today.getDate()).slice(-2) );

            }

        };

        
    </script>


