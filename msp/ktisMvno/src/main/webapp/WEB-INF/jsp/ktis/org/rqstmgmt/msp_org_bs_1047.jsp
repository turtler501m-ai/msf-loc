<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1047.jsp
	 * @Description : 번호이동 내역 조회 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.10.13	 임지혜	최초생성
	 * @author : 임지혜
	 * @Create Date : 2014.10.13 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
   <div id="FORM2" class="section-box"></div>
   <div id="FORM3" class="section-box"></div>
   
<!-- Script -->
<script type="text/javascript">
	 var DHX = {
             //------------------------------------------------------------
             FORM1 : {
                 items : [
					{type:"block", labelWidth:20, list:[ 
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '검색일자', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10},
					{type: 'newcolumn'},
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					,{type: 'newcolumn'}
// 					,{type:"select", label:"업무 구분", name:"infoTypeCd", width:150, offsetLeft:60, userdata:{lov:"CMN0038"} }
					,{type:"select", label:"업무 구분", name:"infoTypeCd", width:150, offsetLeft:60 }
					,{type: 'newcolumn'}
// 					,{type:"select", label:"번호이동 구분", name:"mnpTypeCd", width:120, offsetLeft:60, userdata:{lov:"CMN0039"} }
					,{type:"select", label:"번호이동 구분", name:"mnpTypeCd", width:120, offsetLeft:60 }
					]}
					,{type: 'newcolumn', offset:150}
                    ,{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
                ],
				onButtonClick : function(btnId) {

                    var form = this;

                    switch (btnId) {

                        case "btnSearch":
                            PAGE.GRID1.list(ROOT + "/org/rqstmgmt/getMnpList.json", form);
                            break;
                            
                    }

                },
                onValidateMore : function (data){
			         if((data.searchStartDt && !data.searchEndDt) || (!data.searchStartDt && data.searchEndDt)){
			             this.pushError("data.searchStartDt","검색일자","일자를 선택하세요");
			         }
			         else if(data.searchStartDt > data.searchEndDt){
			             
			             this.pushError("data.searchStartDt","검색일자",["ICMN0004"]);
			         }
			     }
            },	

         // ----------------------------------------
         //사용자리스트
            GRID1 : {
	            css : {
	                height : "600px"
	            },
	            title : "조회결과",
//				header : "MNP_SEQ_NUM,FILE_NM,INFO_TYPE_CD,MNP_DATE,MNP_TYPE_CD,CMNC_CMPN_CD,STTS_YM,MNP_CNT,MNP_AMNT,PAY_GARNT_DATE,CRDT_CARD_CMSN_AMNT,HNDSET_BILL_AMNT,RVISN_DTTM,SLS_CMPN_CD",
//				columnIds : "mnpSeqNum,fileNm,infoTypeCd,mnpDate,mnpTypeCd,cmncCmpnCd,sttsYm,mnpCnt,mnpAmnt,payGarntDate,crdtCardCmsnAmnt,hndsetBillAmnt,rvisnDttm,slsCmpnCd",
				header : "번호이동일자,통신회사코드,번호이동구분,업무구분,정산월,정산건수,정산금액,신용카드수수료금액,단말기금액,지불보증지급일자",
				columnIds : "mnpDate,cmncCmpnCd,mnpType,infoType,sttsYm,mnpCnt,mnpAmnt,crdtCardCmsnAmnt,hndsetBillAmnt,payGarntDate", 
 				colAlign : "center,center,center,left,center,right,right,right,right,center",
                colTypes : "roXd,ro,ro,ro,roXdm,ron,ron,ron,ron,roXd",
                colSorting : "str,str,str,str,str,str,str,str,str,str",
                widths : "120,100,120,130,80,80,120,120,120,120",
                paging: true,
                pageSize:20,
                
                buttons : {
	                hright : {
	                     btnDnExcel : { label : "엑셀다운로드" }
	                }
	           },
	           
	           onButtonClick : function(btnId, selectedData) {
	  				var form = this;
	  				switch (btnId) {
	                  case "btnDnExcel":
	                	  
		                	  if(PAGE.GRID1.getRowsNum()== 0)
		                	  {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
		                	  }
		                	  else
		                	  {
		                		  mvno.cmn.download('/org/rqstmgmt/excelDownProc3.json?searchStartDt=' + PAGE.FORM1.getItemValue("searchStartDt",true) +"&searchEndDt=" + PAGE.FORM1.getItemValue("searchEndDt",true) + "&mnpTypeCd=" + PAGE.FORM1.getItemValue("mnpTypeCd")+ "&infoTypeCd=" + PAGE.FORM1.getItemValue("infoTypeCd")+"&menuId=MSP1000008",true);
		                		  
			                      break;   
		                	  }
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

 			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0038"}, PAGE.FORM1, "infoTypeCd"); // 업무구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0039"}, PAGE.FORM1, "mnpTypeCd"); // 번호이동구분
         }

     };

	</script>