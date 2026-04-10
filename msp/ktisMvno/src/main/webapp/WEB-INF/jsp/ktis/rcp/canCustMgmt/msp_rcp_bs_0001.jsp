<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : msp_rcp_bs_0001.jsp
     * @Description : 해지 고객 조회 화면
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

var isCntpShop = false;

var cntpntShopId = '';
var cntpntShopNm = '';

var typeCd = '${orgnInfo.typeCd}';
if (typeCd == '20' || typeCd == '30' ) {
	isCntpShop = true;
	if(typeCd == '20'){
		cntpntShopId = '${orgnInfo.orgnId}';
		cntpntShopNm = '${orgnInfo.orgnNm}';
	}else{
		cntpntShopId = '${orgnInfo.hghrOrgnCd}';
		cntpntShopNm = '${orgnInfo.hghrOrgnNm}';
	}
}

    var DHX = {
            //------------------------------------------------------------
            FORM1 : {
                items : [
                         	{type:"block", list:[
								{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', label: '해지일자', name: 'searchStartDt', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:90,offsetLeft:10, labelWidth:55}
								, {type: 'newcolumn'}
								, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:90}
								, {type:"newcolumn"}
								, {type:"input", label:"대리점", name: "cntpntShopId", width:80,readonly: isCntpShop,value:cntpntShopId, offsetLeft:15, labelWidth:45}
								, {type:"newcolumn"},{type:"button", value:"찾기", name:"btnOrgFind", disabled:isCntpShop }
								, {type:"newcolumn"},{type:"input", name:"cntpntShopNm", width:100,readonly:isCntpShop, value:cntpntShopNm}
								, {type:"newcolumn"}
								, {type:"select", width:90, label:"검색구분",name:"searchGbn", offsetLeft:15, labelWidth:55}
								, {type:"newcolumn"}
								, {type:"input", width:90, name:"searchName",maxLength:30}
							]},
                         	{type : 'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유 2017-03-15 박준성
            				{type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수
            				{type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수
                         	{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"},
               ],
               onButtonClick : function(btnId) {

                   var form = this;

                   switch (btnId) {

						case "btnSearch":
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;

							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return;//버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
							
							PAGE.GRID1.list(ROOT + "/rcp/canCustMgmt/selectCanCustList.json", form,{callback: function(resultData) {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}})
                           	break;
                           	
						case "btnOrgFind":
							mvno.ui.codefinder("ORG", function(result) {
								form.setItemValue("cntpntShopId", result.orgnId);
								form.setItemValue("cntpntShopNm", result.orgnNm);
							});
							break;   	
					}
			},
			onValidateMore : function (data) {
				if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
					this.pushError("searchStartDt", "해지일자", "종료일자가 시작일보다 클수 없습니다.");
					this.resetValidateCss();
				}
				if( data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색구분", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
				} 
			},
			onChange : function(name, value) {
				var form = this;
						
				// 검색구분
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");
					if(value == null || value == "" ){
						var searchEndDt = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var searchStartDt = new Date(time);
						
						// 개통일자 Enable
						PAGE.FORM1.enableItem("searchStartDt");
						PAGE.FORM1.enableItem("searchEndDt");
						
						PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
						PAGE.FORM1.setItemValue("searchEndDt", searchEndDt);
						
					} else {
						// 개통일자 Disable
						PAGE.FORM1.disableItem("searchStartDt");
						PAGE.FORM1.disableItem("searchEndDt");
						
						PAGE.FORM1.setItemValue("searchStartDt", "");
						PAGE.FORM1.setItemValue("searchEndDt", "");
						
					}
				}
			}
    		
		},
		GRID1 : {
			css : {
				height : "600px"
			},
			title : "조회결과",
			header : "해지일자,계약번호,구매유형,최초요금제명,판매정책,최초개통단말,상태,해지사유,모집경로,대리점명,개통일자,할인유형,최초eSIM여부", //12
			columnIds : "canDt,contractNum,reqBuyTypeNm,fstRateNm,salePlcyNm,fstModelNm,subStatusNm,canRsn,onOffTypeNm,agentNm,openDt,sprtTpNm,fstEsimYn",//12
			colAlign : "center,center,center,left,left,left,center,left,left,left,center,center,center",//12
			colTypes : "roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,ro,ro",//12
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str",//12
			widths : "100,100,100,200,250,120,80,120,100,150,100,100,100",//12
			paging:true,
			pageSize:20,
			buttons : {
					hright : {
						btnDnExcel : { label : "자료생성" }
					},				   
			},
            onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
              		
				switch (btnId) {
                    case "btnDnExcel":
                        var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
                        PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
                        if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록

                        if (PAGE.GRID1.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                            return;
                        } else {
                            mvno.cmn.downloadCallback(function(result) {
                                PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                                mvno.cmn.ajax(ROOT + "/rcp/canCustMgmt/getCanCustListExcelDwonload.json?menuId=MSP1000321", PAGE.FORM1.getFormData(true), function(result){
                                    mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                                });
                            });
                        }
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

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9013"}, PAGE.FORM1, "searchGbn"); // 검색구분
	    	}
	};
	
function getDateDiff(date1, date2){
	
	var getDate1 = new Date(date1.substr(0,4), date1.substr(4,2)-1, date1.substr(6,2));
	var getDate2 = new Date(date2.substr(0,4), date2.substr(4,2)-1, date2.substr(6,2));
	
	var getDiffTime = getDate2.getTime() - getDate1.getTime();
	
	return Math.floor(getDiffTime / (1000 * 60 * 60 * 24));
}
</script>