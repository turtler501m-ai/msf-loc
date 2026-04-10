<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<div class="page-header">
	<h1>고객 찾기</h1>
</div>
<!-- //header -->

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var inputList = ["contractNum", "svcCntrNo", "telNo"];

	var DHX = {

		// 검색 조건
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
				, {type:"block", list:[
					{type: "input", label: "계약번호", name: "contractNum", width: 120, labelWidth: 70, offsetLeft: 8, maxLength: 16, userdata: {align: "left"}},
					, {type:"newcolumn"}
					, {type: "input", label: "서비스계약번호", name: "svcCntrNo", width: 120, labelWidth: 90, offsetLeft: 30, maxLength: 16, userdata: {align: "left"}},
				]},
				, {type:"block", list:[
					{type: "input", label: "휴대폰번호", name: "telNo", width: 120, labelWidth: 70, offsetLeft: 8, maxLength: 16, userdata: {align: "left"}},
				]},
				{type:"newcolumn", offset:21},
				{type : "button",name : "btnSearch",value : "조회", className:"btn-search2"}
			]
			, onKeyUp : function(inp, ev, name, value) {
				var form = this;
				var val = form.getItemValue(name);

				if ( val.length < 1 ) {
					return;
				}

				inputList.forEach(function(input, index) {
					if ( input == name ) {
						return;
					}

					form.setItemValue(input, "");
				});
			}
			, onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					case "btnSearch":
						var isEmpty = true;
						inputList.forEach(function(input, index) {
							var val = form.getItemValue(input);
							if ( val == null || val.trim() == "" ) {
								return;
							}
							isEmpty = false;
						});

						if ( isEmpty ) {
							mvno.ui.alert("계약번호, 서비스계약번호, 휴대폰번호 중 하나를 입력해주세요.");
							return;
						}

						PAGE.GRID1.list(ROOT + "/cmn/payinfo/findCustomer.json", form);
						break;
				}
			}
		},

		// 조직 리스트
		GRID1 : {
			css : {
				height : "170px"
			},
			title : "조회결과",
			header : "계약번호,서비스계약번호,상태,상태코드,고객명,생년월일,생년월일(8),나이(만),휴대폰번호,개통일자", // 8
			columnIds : "contractNum,svcCntrNo,subStatusNm,subStatus,subLinkName,birthDt,birth,age,subscriberNo,openDt", // 8
			colAlign : "center,center,center,center,center,center,center,center,center,center", // 8
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro", // 8
			colSorting : "str,str,str,str,str,str,str,str,str,str", // 8
			widths : "120,120,70,70,70,100,100,100,100,100",
			hiddenColumns : [0, 3, 6],
			paging : true,
			pageSize : 20,
			showPagingAreaOnInit : true,
			buttons : {
				center : {
					btnApply : { label : "확인" },
					btnClose : { label : "닫기" }
				}
			},
			onRowDblClicked : function(rowId, celInd) {
				// 선택버튼 누른것과 같이 처리
				this.callEvent('onButtonClick', ['btnApply']);
			},
			onButtonClick : function(btnId) {
				var grid = this;
				var rowData = grid.getSelectedRowData();
				switch (btnId) {
					case "btnApply":
						if(rowData == null) {
							mvno.ui.alert("먼저 고객을 선택해주세요.");
							return;
						}

						mvno.ui.closeWindow(rowData, true);
						break;
					case "btnClose" :
						mvno.ui.closeWindow();
						break
				}
			}
		}
	};
	var PAGE = {
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
		}
	};
</script>