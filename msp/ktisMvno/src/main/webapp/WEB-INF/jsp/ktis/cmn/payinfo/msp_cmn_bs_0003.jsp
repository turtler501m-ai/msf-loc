<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : msp_cmn_bs_0003.jsp
     * @Description : CMS 연동 관리 마스터
     * @
     * @ 수정일     수정자 수정내용
     * @ ------------ -------- -----------------------------
     * @ 2016.07.12 장익준 최초생성
     * @
     * @author : 장익준
     * @Create Date : 2016. 07. 12.
     */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

function goFileInfoData(kftcMstSeq)
{
	var url = "/cmn/payinfo/payInfoDtl.do";
	var title = "자동이체동의서상세"
	var menuId = "MSP1020210";

	var myTabbar = window.parent.mainTabbar;

       if (myTabbar.tabs(url)) {
       	myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
       }

       myTabbar.addTab(url, title, null, null, true);
       myTabbar.tabs(url).attachURL(url + "?menuId=" + menuId+"&kftcMstSeq="+ kftcMstSeq);

       // 잘안보여서.. 일단 살짝 Delay 처리 
       setTimeout(function() {
           //mainLayout.cells("b").progressOff();
       }, 100);
}
	
	
    var DHX = {
            //------------------------------------------------------------
            FORM1 : {
                items : [
                         	{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
                         	, {type:"block", list:[
                         	                       {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '등록일자', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10, labelWidth:60}
                         	                       , {type: 'newcolumn'}
                         	                       , {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
                         	                       , {type: 'newcolumn'}
                         	                       , {type:"select", width:100, offsetLeft:20, label:"승인여부",name:"approvalCd", labelWidth:50}
                         	                       , {type: 'newcolumn'}
                         	                       , {type:"select", width:150, offsetLeft:20, label:"상태",name:"stateCd", labelWidth:30}                         	                       
						                           ]
                         	},
                         	
            				{type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
            				{type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
            				{type : "newcolumn",offset : 50},
            				
                         	{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
               ],
               onButtonClick : function(btnId) {

                   var form = this;

                   switch (btnId) {
                       case "btnSearch":
                    	   
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							if (!this.validate()) return;							
							
							var url = ROOT + "/cmn/payinfo/payInfoMstList.json";
							
							PAGE.GRID1.list(url, form, {
								callback : function () {
								//	PAGE.FORM2.clear();
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});		
                    	   
						// 20161021 기존소스
						// PAGE.GRID1.list(ROOT + "/cmn/payinfo/payInfoMstList.json", form);
						break;
					}
				},
               
				onValidateMore : function (data) {
					if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
						this.pushError("searchStartDt", "등록일자", "종료일자가 시작일보다 클수 없습니다.");
						this.resetValidateCss();
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
				}
           },
        // ----------------------------------------
        //KT로 수신한 자료 마스터정보
           GRID1 : {
               css : {
                   height : "560px"
               },
               title : "조회결과",
               header : "순번,등록일자,종료일자,연동파일명,상태,수신건수,송신건수,승인여부,승인자,승인일시,사유", //8
               columnIds : "kftcMstSeqStr,startDt,endDt,kftcFileNm,stateNm,receiveCnt,sendCnt,approvalNm,approvalIdNm,approvalDt,approvalMsg",//8
               colAlign : "right,center,center,left,center,right,right,center,center,center,left",//8
               colTypes : "link,roXd,roXd,ro,ro,ron,ron,ro,ro,roXdt,ro",
               colSorting : "str,str,str,str,str,str,str,str,str,str,str",
               widths : "50,80,80,270,120,80,80,80,100,120,400",
               paging:true,
               pageSize:20,
			   buttons : {
					right : {
						btnReg: {label: "승인"}
					},
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					},
					left : {
						btnSyn: {label: "동의서현행화"}
					}
               },
               onButtonClick : function(btnId) {
                   switch (btnId) {
                       case "btnSearch":
                           PAGE.GRID1.list(ROOT + "/cmn/payinfo/orgpayinfo.json", form);
                           break;
                       
                       case "btnSyn":
                    	   if(PAGE.GRID1.getSelectedRowData() == null) {
                               mvno.ui.alert("ECMN0002");
                               break;
                           };
							mvno.ui.confirm("CCMN0018", function(){
								mvno.cmn.ajax(ROOT + "/cmn/payinfo/setPayinfoDataSyn.json", PAGE.GRID1.getSelectedRowData(), function(result) {
									if(result.result.code == "OK") {
										mvno.ui.alert(result.result.msg);
									} else {
										mvno.ui.alert(result.result.msg);
									}
									PAGE.GRID1.refresh();
								});   
							});
							break;

                       case "btnReg":

                           if(PAGE.GRID1.getSelectedRowData() == null)
                           {
                               mvno.ui.alert("ECMN0002");
                               break;
                           }         
                            
                           mvno.ui.createForm("FORM2");
                           PAGE.FORM2.clear();
                           mvno.cmn.ajax4lov( ROOT + "/org/grpcdmgmt/listCmnCd.json", {code:"CMN0209"}, PAGE.FORM2, "approvalCd");
                            
                           var data = PAGE.GRID1.getSelectedRowData();
                           PAGE.FORM2.setFormData(data);
                            
                           mvno.ui.popupWindowById("FORM2", "승인처리", 750, 200);
                           break;      
                           
                           
                       case "btnDnExcel":
                    	   
	   						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						
							if(PAGE.GRID1.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/cmn/payinfo/selectPayinfoMstEx.json?menuId=MSP1020210", true, {postData:searchData});
							break;
                   }
               }
           },
           FORM2 : {
				items : [
					{type: "fieldset", label: "기본정보", inputWidth:690, list:[
						{type:"settings", position:"label-left", labelWidth:60},
						{type: "block", list: [
							   {type: "input", name: "kftcFileNm", label: "파일명", width: 250, disabled:true,offsetLeft:10}
							 , {type: 'newcolumn'}
							 , {type: "input", name: "startDtStr", label: "등록일자", width: 70, disabled:true,offsetLeft:20}
                             , {type: 'newcolumn'}
//                              , {type: "select", name: "approvalCd", label: "승인여부", width: 80, userdata:{lov:"CMN0111"}, offsetLeft:20, required: true}
                             , {type: "select", name: "approvalCd", label: "승인여부", width: 80, offsetLeft:20, required: true}
					]},
						{type: "block", list: [
							   {type: "input", name: "approvalMsg", label: "사유", width: 569, offsetLeft:10, required: true}
					]},						
				]}],
				buttons : {
			    	center : {
						btnSave : { label : "저장" },
			            btnClose : { label: "닫기" }
			        }
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)
					var fileLimitCnt = 1;

					switch (btnId) {
					case "btnSave":
                        mvno.cmn.ajax(ROOT + "/cmn/payinfo/updateApprovalYn.json", form, function(result) {
                            mvno.ui.closeWindowById(form, true);  
                            mvno.ui.notify("NCMN0015");
                            PAGE.FORM2.clear();
                            PAGE.GRID1.refresh();
                        });
                        break;
                    case "btnClose" :
                        mvno.ui.closeWindowById(form);   
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
        
        mvno.cmn.ajax4lov( ROOT + "/org/grpcdmgmt/listCmnCd.json", {code:"CMN0204"}, PAGE.FORM1, "stateCd");
        mvno.cmn.ajax4lov( ROOT + "/org/grpcdmgmt/listCmnCd.json", {code:"CMN0209"}, PAGE.FORM1, "approvalCd");
    }
};
</script>