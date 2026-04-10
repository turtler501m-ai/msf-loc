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
  * author  xxxsss
  * since 
  *
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>
<%
//HashMap buttonAuth=(HashMap)request.getAttribute("buttonAuth");
//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>buttonAuth:" + buttonAuth);
//request.setAttribute("_crud", buttonAuth.get("_crud"));
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
						{ type : "input", name : "orgnId", label : "조직", width : 100, offsetLeft : 10 },
						{ type : "newcolumn" },
						{ type : "button", value : "검색", name : "btnOrgFind" },
						{ type : "newcolumn" },
						{ type : "input", name : "orgnNm", width : 150, readonly : true },
	                    { type : "newcolumn", offset:50 }, 
	                    { type : "calendar" , name:"searchStrtDt", label:"조회기간", id:"searchStrtDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth : 80, width:100 },
						{ type : "newcolumn" },
						{ type : "calendar" , name:"searchEndDt", id:"searchEndDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"~", labelWidth:30, labelAlign:"center", width:100 },
					]},
                    { type : "block", list:[
  	                    { type : "input", label : "사용자", name : "searchUsrNm", width : 100, offsetLeft : 10, disabled : true}, 
  	                    { type : "newcolumn" }, 
  	                    { type : "input", label : "", name : "searchUsrId" , width : 60, disabled:true},
  	                    { type : "newcolumn" },
  	                    { type : "button", value : "찾기", name : "btnUseFind" },
   					]},
					{type : "button",name : "btnSearch",value : "조회", className:"btn-search2"} 
				],

                onButtonClick : function(btnId) {

                    var form = this;

                    
                    switch (btnId) {

							
						case "btnUseFind" :

							mvno.ui.codefinder("USER", function(result) {
								form.setItemValue("searchUsrNm", result.usrNm);
						        form.setItemValue("searchUsrId", result.usrId);
							}); 
							
	
	
							
//						       mvno.ui.popupWindow(ROOT + "/org/userinfomgmt/searchUserInfo.do", "사용자찾기", 790, 500, {
//						        param : {
//						         callback : function(result) {
//						          form.setItemValue("searchUsrNm", result.usrNm);
//						          form.setItemValue("searchUsrId", result.usrId);
//						         }
//						        }
//						       });
						   break;  
                        case "btnSearch" :

                        	var url1 = ROOT + "/cmn/accesslogsrch/selecList.json";
                            PAGE.GRID1.list(url1, form);

//                        	var url1 = ROOT + "/cmn/filedownload/fileUpLoadUsingService.do";
//                        	this.encType = "multipart/form-data";
//                            this.send(url1, "post");

                            //                            self.location= "/cmn/authmgmt/menu.do";
                            break;
                            
                        case "btnOrgFind" :
                        	
							mvno.ui.codefinder("ORG", function(result) {
								form.setItemValue("orgnId", result.orgnId);
								form.setItemValue("orgnNm", result.orgnNm);
							});
							break;

                    }

                }
            },

            // ----------------------------------------
            GRID1 : {

            	css : { 
                    height : "573px"  
                }, 
                title : "사용자별 접속",
                header : "사용자ID,사용자명,조직ID,조직명,접속일시,성공여부",
                columnIds : "USR_ID,USR_NM,ORGN_ID,ORGN_NM,LOG_DTTM,SUCC_YN",
                widths : "140,170,170,180,150,120",
                colAlign : "left,left,left,left,center,center",
                colTypes : "ro,ro,ro,ro,ro,ro",
                colSorting : "str,str,str,str,str,str",
                paging : true,
                pageSize : 20,
//                buttons: {
//                    left: {
//                        btn01: { label: "테스트1" },
//                        btn02: { label: "테스트2", auth: "baSampleAuth" }
//                    },
//                    right: {
//                        btnReg: { label: "등록" },
//                        btnMod: { label: "수정" }
//                    }
//                },
                

                
//    			onButtonClick : function(btnId, selectedData) {
//                    switch (btnId) {
//
//                        case "btnReg":
//                        	mvno.cmn.download( "/cmn/filedown/fileDown.do?fileName=images.jpg&fileNamePc=images1.jpg&filePath=/CMN&writeFileUpDownLog=Y&menuId=" + PAGE.buttonAuth.menuId  , true);
//                   	                             
//                            
//                            break;
//                    }
//                }
                
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
                PAGE.FORM1.setItemValue("searchStrtDt", "" + today.getFullYear() + ('0' + (today.getMonth() + 1)).slice(-2)  + ('0' + today.getDate()).slice(-2) );
                PAGE.FORM1.setItemValue("searchEndDt", "" + today.getFullYear() + ('0' + (today.getMonth() + 1)).slice(-2)  + ('0' + today.getDate()).slice(-2) );

                
//                mvno.cmn.ajax4lov(ROOT + "/cmn/codefind/cmnMenuMst.json", null, PAGE.FORM1, "searchMenuList");
                
            }

        };

        
    </script>


    <script>
    function goFileDown()
    {
	     mvno.cmn.download( "/cmn/filedown/fileDown.do?fileName=images.jpg&fileNamePc=images1.jpg&filePath=/CMN&writeFileUpDownLog=Y&menuId=XXX0001"  , true);
    }
    </script>

<!-- a href="javascript:goFileDown('');"> file down load </a-->


