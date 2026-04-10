<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.*" %>

<%
  /**
  * @Class Name : msp_org_bs_9005.jsp
  * @Description : 이력관리 - 등록계정
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
				onValidateMore : function(data){
					if(data.searchStrtDt == "") this.pushError("data.searchStrtDt", "조회기간", "시작일자를 입력하세요");
					else if(data.searchEndDt == "") this.pushError("data.searchEndDt", "조회기간", "종료일자를 입력하세요");
					else {
						if(data.searchStrtDt > data.searchEndDt) this.pushError("data.searchStrtDt","조회기간",["ICMN0004"]);
					}
					
					//조회기간은 한달 이내 validation 체크
		/* 			var year = data.searchEndDt.substring(0 , 4);
					var month = data.searchEndDt.substring(4 , 6);
					var date = data.searchEndDt.substring(6 , 8);
					var valDate = new Date(year , month-1 , date);
					valDate.setMonth(valDate.getMonth() -1);
					var preDate = valDate.getFullYear() + ('0' + (valDate.getMonth() + 1)).slice(-2)  + ('0' + valDate.getDate()).slice(-2);
					if(data.searchStrtDt < preDate ) this.pushError("data.searchEndDt", "조회기간", "조회기간은 한달이내 입니다."); */
					
				},
                onButtonClick : function(btnId) {

                    var form = this;
                    switch (btnId) {
                        case "btnSearch" :
                      	
                        	PAGE.GRID1.list(ROOT + "/secu/securityMgmt/mspUsrMgmtList.json", form);
                            break;
                    }

                }
            },

            // ----------------------------------------
            GRID1 : {

            	css : { 
                    height : "600px"  
                }, 
                title : "등록계정",
                header : "조직ID,조직명,사용자ID,사용자명,계정구분,계정생성일,마지막로그인일자,계정수정일,삭제여부,사용여부",
                columnIds : "ORGN_ID,ORGN_NM,USR_ID,USR_NM,ATTC_SCTN,REG_DATE,LST_DATE,RVS_DATE,DEL_YN,USG_YN",
                widths : "100,141,100,140,80,127,127,127,70,70",
                colAlign : "left,left,left,left,center,center,center,center,center,center",
                colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
                colSorting : "str,str,str,str,str,str,str,str,str,str",
                paging : true,
                pageSize : 20,
           
            	 buttons : {
					hright : {
						btnExcel : { label : "엑셀다운로드" }
					}
				}
	            , onButtonClick : function(btnId){
					var form = this;
					switch(btnId){
						case "btnExcel" :
							if(PAGE.GRID1.getRowsNum() == 0){
								mvno.ui.alert("데이터가 존재하지 않습니다");
								return;
							}
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/secu/securityMgmt/mspUsrMgmtListExcel.json?menuId=MSP9001005", true, {postData:searchData}); 
							
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


