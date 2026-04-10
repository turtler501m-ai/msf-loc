<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : rateGroupRelMgmtPopup.jsp
	 * @Description : 요금그룹관계관리 팝업
	 * @
	 * @  수정일	  	수정자			  수정내용
	 * @ ---------   ---------   -------------------------------
	 * @ 2018.06.01  	JUNA		  최초생성
	 * @ 
	 * @author : JUNA
	 * @Create Date : 2018.06.01
	 */
%>
<div class="row">
	<div id="GRID1"></div>
</div>

<script type="text/javascript">
	var DHX = {
			GRID1 : {
				css : { height : "580px"},
				title : "요금변경관계조회",
				header : "변경전요금,#cspan,#cspan,#cspan,#cspan,#cspan,변경후요금,#cspan,#cspan,#cspan,#cspan,#cspan", 
				header2 : "그룹,그룹유형,상품,제휴여부,선후불,데이터유형,그룹,그룹유형,상품,제휴여부,선후불,데이터유형", 
				columnIds : "prvRateGrpNm,prvRateGrpTypeNm,prvRateNm,prvPtrnRateYn,prvPayClNm,prvDataType,nxtRateGrpNm,nxtRateGrpTypeNm,nxtRateNm,nxtPtrnRateYn,nxtPayClNm,nxtDataType",
				colAlign : "left,center,left,center,center,center,left,center,left,center,center,center",
				colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
				widths : "100,60,120,60,50,70,100,60,120,60,50,70",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
				buttons : {
					hright : {
						btnDnExecl: { label : "엑셀다운로드" }
					},
					center : {
						btnClose : {label : "닫기"}
					}
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnDnExecl" :
							mvno.cmn.download(ROOT+"/sale/rateMgmt/getGroupByRateReListEx.json",true);

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
				mvno.ui.createGrid("GRID1");
				PAGE.GRID1.attachHeader("#text_filter,#select_filter,#text_filter,#select_filter,#select_filter,#select_filter,#text_filter,#select_filter,#text_filter,#select_filter,#select_filter,#select_filter");
				PAGE.GRID1.list(ROOT + "/sale/rateMgmt/getGroupByRateReList.json");
				
			}
		
	};
		
</script>