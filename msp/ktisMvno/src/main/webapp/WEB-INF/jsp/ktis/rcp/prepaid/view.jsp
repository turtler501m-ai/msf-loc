<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : view.jsp
	 * @Description : 청소년 요금제 조회화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2015.10.29     노태기           최초 생성
	 * @
	 * @author : 노태기
	 * @Create Date : 2015. 10. 29.
	 */
%>

	<!-- 화면 배치 -->
	<div id="FORM0" class="section-search"></div>
	<div id="GRID0"></div>
	
    <!-- Script -->
    <script type="text/javascript">
    var DHX = {
    		FORM0 : {
        		items : [ 
    				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
    				,{type:"block", labelWidth:30 , list:[    
    					{type : "calendar",width : 100,label : "요청일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "sysRdateS", offsetLeft:10}
    					,{type : "newcolumn"}
    					,{type : "calendar",label : "~",name : "sysRdateE",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100}
    					,{type : "newcolumn"}
    					,{type:'select', name:'searchCd', label:'검색구분', width:100, offsetLeft:20}
    					,{type:"newcolumn"},
    					,{type:"input", name:"searchVal", width:100}					
    				]}
    				,{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
    			],
    		
    		  onButtonClick : function(btnId) {
    		
    		      var form = this;
    		
    		      switch (btnId) {
    		          case "btnSearch":
    		        	var url = ROOT + "/rcp/prepaid/list.json";
    		  	          PAGE.GRID0.list(url, form, {
    							callback : function () {
    								//PAGE.FORM0.clear();
    							}
    						});
    		  			
    		  		}
    			},
    			onChange : function(name, value) {
    				var form = this;
    						
    				// 검색구분
    				if(name == "searchCd") {
    					PAGE.FORM0.setItemValue("searchVal", "");
    					if(value == null || value == "" ){
    						var searchEndDt = new Date().format('yyyymmdd');
    						var time = new Date().getTime();
    						time = time - 1000 * 3600 * 24 * 7;
    						var searchStartDt = new Date(time);
    						
    						// 요청일자 Enable
    						PAGE.FORM0.enableItem("sysRdateS");
    						PAGE.FORM0.enableItem("sysRdateE");
    						
    						PAGE.FORM0.setItemValue("sysRdateS", searchStartDt);
    						PAGE.FORM0.setItemValue("sysRdateE", searchEndDt);
    						
    					} else {
    						// 요청일자 Disable
    						PAGE.FORM0.disableItem("sysRdateS");
    						PAGE.FORM0.disableItem("sysRdateE");
    						
    						PAGE.FORM0.setItemValue("sysRdateS", "");
    						PAGE.FORM0.setItemValue("sysRdateE", "");
    					}
    				}
    			},
    			onValidateMore : function (data){
    				if (this.getItemValue("sysRdateS", true) > this.getItemValue("sysRdateE", true)) {
    					this.pushError("sysRdateE", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
    					this.resetValidateCss();
    				}
    				if( data.searchCd != "" && data.searchVal.trim() == ""){
    					this.pushError("searchCd", "검색어", "검색어를 입력하세요");
    					PAGE.FORM0.setItemValue("searchVal", ""); // 검색어 초기화
    				}
    			}
    		},
		GRID0 : {
			css : {
				height : "575px"
			},

			title : "충전내역리스트",
			header : " 요청일자,계약번호,전화번호,요금제,충전대리점,충전금액,충전성공여부,충전결과,최대충전가능금액,기본알,충전알,데이터알,문자알", 
			columnIds : "reqDate,contractNum,subscriberNo,lstRateNm,rechargeAgent,recharge,retCodeNm,retMsg,oTesChargeMax,oTesBaser,oTesChgr,oTesMagicr,oTesFsmsr",
			colAlign : "center,center,center,center,center,right,center,left,right,right,right,right,right",
			colTypes : "roXd,ro,roXp,ro,ro,ron,ro,ro,ron,ron,ron,ron,ron",
			widths : "80,80,110,130,80,80,100,220,110,70,70,70,70",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize : 20,
			buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" }
				}
			},
			onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					case "btnExcel":
						if(PAGE.GRID0.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
						
						var searchData =  PAGE.FORM0.getFormData(true);
						mvno.cmn.download(ROOT + "/rcp/prepaid/listExcel.json?menuId=MSP100011", true, {postData:searchData}); 
						
						break;	
				}
			}
		}
		
	};

	var PAGE = {
		title: "${breadCrumb.title}",
		breadcrumb: "${breadCrumb.breadCrumb}",
		onOpen : function() {

			mvno.ui.createForm("FORM0");
			mvno.ui.createGrid("GRID0");
			
			var sysRdateE = new Date().format('yyyymmdd');
			var time = new Date().getTime();
			time = time - 1000 * 3600 * 24 * 7;
			var sysRdateS = new Date(time);

			PAGE.FORM0.setItemValue("sysRdateS", sysRdateS);
			PAGE.FORM0.setItemValue("sysRdateE", sysRdateE);
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2050"}, PAGE.FORM0, "searchCd"); // 검색구분

	    }
	};
	
	</script>