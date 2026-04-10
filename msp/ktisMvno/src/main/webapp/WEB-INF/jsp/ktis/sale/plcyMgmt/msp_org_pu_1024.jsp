<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_pu_1024.jsp
	 * @Description : 할부 개월수 팝업 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.08.14 장익준 최초생성
	 * @ 
	 * @author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
%>
<!-- header -->
<!-- 	<div class="page-header"> -->
<!-- 		<h1>할부 개월 팝업</h1> -->
<!-- 	</div> -->
	
<!-- 화면 배치 -->
<div id="FORM1"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
				{type:'hidden', name:'salePlcyCd', value:'${param.salePlcyCd}', label:'정책코드'},
			]
		},
		//------------------------------------------------------------
		GRID1 : {
		css : {
				height : "140px"
			},
			title : "할부개월수",
			header : "선택,할부개월수",
			columnIds : "useYn,instNom",
			widths : "50,*",
			colAlign : "center,center",
			colTypes : "ch,ro",
			colSorting : "str,str",
// 			multiCheckbox: true,
			buttons : {
				center : {
					btnMSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			checkSelectedButtons : ["btnApply"],
			onRowDblClicked : function(rowId, celInd) {
				// 선택버튼 누른것과 같이 처리
				this.callEvent('onButtonClick', ['btnApply']);
			},
			onButtonClick : function(btnId, selectedData) {
				var form = this;
				
				switch (btnId) {
					case "btnMSave" ://일괄저장
						var arrData = form.getCheckedRowData();
					   var sData = {
                            'salePlcyCd'  : PAGE.FORM1.getItemValue("salePlcyCd")
                     };
						
						if(arrData != null && arrData != '') {
							sData.items = arrData;
						}
						
						mvno.ui.confirm("CCMN0012", function(){
                        mvno.cmn.ajax4json(ROOT + "/sale/plcyMgmt/insertInstNom.json", sData, function(result) {
                        mvno.ui.closeWindow("OK");
                        mvno.ui.notify("NCMN0001");
                     });
                  });

						break;
					case "btnClose" :
						mvno.ui.closeWindow();   
						break;
				}
			}
		}
};
var PAGE = {
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		PAGE.GRID1.list(ROOT + "/sale/plcyMgmt/listInstNomPu.json",PAGE.FORM1);
	}
};
</script>
