<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : 매각채권수납내역서
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">

var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
				{type:"block", labelWidth:40 , list:[
					{type:"select", name:"bondSeqNo", label:"판매회차", width:100, offsetLeft:10}
					,{type:"newcolumn"}
					,{type:"select", name:"billYm", label:"기준월", width:100, offsetLeft:20}
					, {type : 'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유
				]}
				,{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
			]
			,onValidateMore : function (data) {
				if(data.bondSeqNo == null || data.bondSeqNo == "") this.pushError("bondSeqNo", "판매회차", "판매회차를 선택하세요");
				if(data.billYm == null || data.billYm == "") this.pushError("billYm", "기준월", "기준월을 선택하세요");
			}
			,onChange : function(name, value){
				var form = this;
				if(name == "bondSeqNo"){
					mvno.cmn.ajax4lov(ROOT+"/bnd/bondsalemgmt/getBillYm.json", form, PAGE.FORM1, "billYm");
				}
			}
			,onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch" :
						PAGE.GRID1.list(ROOT + "/bond/bondsalemgmt/selectSoldReceiptByMonthSum.json", form, {callback: function() {
							PAGE.GRID2.list(ROOT + "/bond/bondsalemgmt/selectSoldReceiptByMonth.json", form);
						}});
						break;
				}
			}
		}
		//-------------------------------------------------------------
		,GRID1 : {
			css : {
				height : "80px"
			},
			title : "매각채권수납내역",
			header : "월초채권잔액,청구금액,청구상환,연체상환,미납금액,미납금액(누적),조기상환(완납),조기상환(부분납),감액,연체이자청구,연체이자수납,채권감소금액,수납금액,월말채권잔액",
			columnIds : "prvYetPymnAmnt,billAmnt,pymnAmnt,unpayAmnt,toUnpayArrsAmnt,unpayArrsAmnt,fullPrfpayAmnt,midPrfpayAmnt,grntInsrAmnt,adjsAmnt,arrsFeeInvAmnt,arrsFeeAmnt,dscAmnt,realPymnAmnt,yetPymnAmnt",
			widths : "120,100,100,100,100,120,100,120,100,120,120,120,120,120",
			colAlign : "right,right,right,right,right,right,right,right,right,right,right,right,right,right",
			colTypes : "ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron",
			colSorting : "int,int,int,int,int,int,int,int,int,int,int,int,int,int",
			buttons : {
				hright : {
					btnExcel : { label : "엑셀저장" }
				}
			}
			,onButtonClick : function(btnId, selectedData) {
				var grid = this;
				switch (btnId) {
					case "btnExcel":
						if(this.getRowsNum()== 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}else{
                            mvno.cmn.downloadCallback(function(result) {
								PAGE.FORM1.setItemValue("DWNLD_RSN", result);
								mvno.cmn.ajax(ROOT + "/bond/bondsalemgmt/selectSoldReceiptByMonthExcel.json?menuId=BON2001009", PAGE.FORM1.getFormData(true), function(result){});
								mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
							});
						}
						
						break;   
				}
			}
		}
		//-------------------------------------------------------------
		,GRID2 : {
			css : {
				height : "490px"
			},
			//title : "매각채권수납내역",
			header : "보증보험관리번호,월초채권잔액,할부회차,청구금액,청구상환,연체상환,미납금액,미납금액(누적),미납회차,조기상환(완납),조기상환(부분납),감액,연체이자청구,연체이자수납,채권감소금액,수납금액,월말채권잔액",
			columnIds : "grntInsrMngmNo,prvYetPymnAmnt,instCnt,billAmnt,pymnAmnt,unpayAmnt,toUnpayArrsAmnt,unpayArrsAmnt,unpayArrsTmscnt,fullPrfpayAmnt,midPrfpayAmnt,grntInsrAmnt,adjsAmnt,arrsFeeInvAmnt,arrsFeeAmnt,dscAmnt,realPymnAmnt,yetPymnAmnt",
			widths : "120,120,100,100,100,100,120,120,80,120,120,100,120,120,120,120,120",
			colAlign : "center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right",
			colTypes : "ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron",
			colSorting : "str,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int",
			paging : true,
			pageSize:20,
			showPagingAreaOnInit: true
		}
};
		
var PAGE = {
   title : "${breadCrumb.title}",
   breadcrumb : "${breadCrumb.breadCrumb}", 
	
	buttonAuth: ${buttonAuth.jsonString},
	
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createGrid("GRID2");
		
		mvno.cmn.ajax4lov(ROOT+"/bnd/bondmgmt/getSaleNumCombo.json", {"pSearchGbn":"A"}, PAGE.FORM1, "bondSeqNo");
	}
};

</script>