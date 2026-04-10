<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : rcpPreAuthList.jsp
     * @Description : 사전 인증 조회
     * @
     * @ 수정일     수정자 수정내용
     * @ ------------ -------- -----------------------------
     * @ 2016.08.10 장익준 최초생성
     * @
     * @author : 장익준
     * @Create Date : 2016. 08. 10.
     */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

    var DHX = {
            //------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
				{type:"block", list:[
					{type:"input", width:90, label:"예약번호", name:"resNo", maxLength:30, offsetLeft:10}
				]},
				{type:'button', value:'조회', name:'btnSearch', className:"btn-search1"}
			],
			
			onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/selectPreAuthList.json", form);
						
						break;  	
				}
			},
			
			onValidateMore : function (data) {
				if( data.resNo == "" && data.resNo.trim() == ""){
					this.pushError("resNo", "예약번호", "검색할 값을 입력하세요");
					
				} 
			},
			
			onChange : function(name, value) {
				
			}
    		
		},
		
		FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelWidth:80},
				{type: "block", list: [
					{type:"input", width:450, label:"메모사항",name:"requestMemo", maxLength:300},
					{type:"hidden",name:"resNo"}
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
					case "btnClose" :
						mvno.ui.closeWindowById(form);   
					break;
					
					case "btnSave" :
						resNo = PAGE.FORM1.getItemValue("resNo");
						
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/updatePreAuthMemo.json", form, function(result) {
							if (result.result.code == "OK") {
								mvno.ui.closeWindowById(form, true);
								mvno.ui.notify("NCMN0002");
								PAGE.GRID1.refresh();
							} else {
								mvno.ui.alert(result.result.msg);
							}
					});
					break;
                	}
			},
		},
		
        // ----------------------------------------
		GRID1 : {
			css : {
				height : "200px"
			},
			title : "조회결과",
			header : "고객명,주민번호,이동번호,이동전통신사,인증유형,인증값,메모사항,예약번호", //7
			columnIds : "cstmrName,cstmrNativeRrn,moveMobile,moveCompany,moveAuthType,moveAuthNumber,requestMemo,resNo",//7
			colAlign : "center,center,center,center,center,center,left,center",//7
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro",//7
			colSorting : "str,str,str,str,str,str,str,str",//7
			widths : "100,120,90,150,130,70,293,0",//7
			hiddenColumns:[7],
			//paging:true,
			//pageSize:20,
			buttons : {
				right : {
					btnDtl : { label : "메모수정" }
				}						   
			},			

            onRowDblClicked : function(rowId, celInd) {
           		this.callEvent('onButtonClick', ['btnDtl']); // 읽기 폼 호출
           	},

            onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
              		
				switch (btnId) {

					case "btnDtl":
						var data = PAGE.GRID1.getSelectedRowData();
												
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();						
						PAGE.FORM2.setFormData(data);						
						mvno.ui.popupWindowById("FORM2", "메모수정", 600, 150);
						
						break;
				}
				
            }
		}
    };
    
	var PAGE = {
	        title: "${breadCrumb.title}",
	        breadcrumb: "${breadCrumb.breadCrumb}",
	        buttonAuth: ${buttonAuth.jsonString},
	        onOpen : function() {
		        mvno.ui.createForm("FORM1");
		        mvno.ui.createGrid("GRID1");
	    	}
	};

</script>