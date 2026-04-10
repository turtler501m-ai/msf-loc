<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.*" %>

<%
  /**
  * @Class Name : msp_org_bs_9006.jsp
  * @Description : 이력관리 - 마스킹 권한자
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2019.01.21 kos              최초 생성
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
						{type: 'input', name: 'usrId', label: '사용자ID', value: '', maxLength:10,width:90, offsetLeft:20},
						{type: 'newcolumn'},
						{type: 'input', name: 'usrNm', label: '사용자명', value: '', maxLength:10,width:90, offsetLeft:20}
					]},
					{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
				onValidateMore : function(data){
				},
                onButtonClick : function(btnId) {

                    var form = this;
                    switch (btnId) {
                        case "btnSearch" :
                      	
                        	PAGE.GRID1.list(ROOT + "/secu/securityMgmt/mspUsrMaskList.json", form);
                            break;
                    }

                }
            },

            // ----------------------------------------
            GRID1 : {

            	css : { 
                    height : "600px"  
                }, 
                title : "마스킹권한자",
                header : "사용자ID,사용자명,조직명",
                columnIds : "USR_ID,USR_NM,ORGN_NM",
                widths : "120,150,150",
                colAlign : "left,left,left",
                colTypes : "ro,ro,ro",
                colSorting : "str,str,str",
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
							mvno.cmn.download(ROOT + "/secu/securityMgmt/mspUsrMaskListExcel.json?menuId=MSP9001006", true, {postData:searchData}); 
							
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
            }

        };
               
    </script>


