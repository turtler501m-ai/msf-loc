<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
%>

<!-- header -->
<div class="page-header">
	<h1>판매정책 팝업</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
var orgOnlyFlag = false;		// 조직명 ReadOnly 플래그(조직코드 존재시 true)
var orgReqFlag = false;			// 조직명 required 플래그(대리점인 경우 true)
var orgnId="";					// 조직코드
var orgnNm="";					// 조직명
var hghrOrgnCd = "";			// 상위조직코드
var typeCd = '${param.typeCd}';	// 조직 형태 (typeCd = 10:본사조직, 20:대리점, 30:판매점) 
var cdNm = '';					// 조직코드(orgnId)

// 조직코드 존재시
if ( "${param.sOrgnId}" != '' && '${sOrgnNm}' != '' ) {
	orgOnlyFlag = true;
	orgnId = '${param.sOrgnId}';			// 조직코드
	orgnNm = '${sOrgnNm}';					// 조직명
	cdNm = '${param.sOrgnId}';
	if(typeCd == '30'){
		hghrOrgnCd = '${param.hghrOrgnCd}';	// 상위조직코드
		cdNm = '${param.hghrOrgnCd}';
	}
}

// 조직형태 대리점 체크
if ( typeCd == '20' ) {
	orgReqFlag = true;
}

var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
				{type: 'block', list: [
					{type:"hidden", label:"", name:"orgnId", value:orgnId},
					{type:"hidden", label:"", name:"typeCd", value:typeCd},
					{type:"hidden", label:"", name:"hghrOrgnCd", value:hghrOrgnCd},
					{type:"hidden", label:"", name:"reqInDay"},
					{type:"hidden", label:"", name:"serviceType"},
					{type:"hidden", label:"", name:"plcyTypeCd"},
					{type:'newcolumn'},
					{type:"input", label:"조직명", name:"orgnNm", labelWidth:60, width:180, offsetLeft:10, value:orgnNm, required:orgReqFlag, readonly:orgOnlyFlag},
					{type:'newcolumn'},
// 					{type:"select", label:"구매유형", name:"reqBuyType", labelWidth:60, width:100, offsetLeft:20, userdata:{lov:"RCP0009"}, required:true},
					{type:"select", label:"구매유형", name:"reqBuyType", labelWidth:60, width:100, offsetLeft:20, required:true},
					{type:'newcolumn'},
// 					{type:"select", label:"할인유형", name:"sprtTp", labelWidth:75, width:100, offsetLeft:20, userdata:{lov:"CMN0058"}}
					{type:"select", label:"할인유형", name:"sprtTp", labelWidth:75, width:100, offsetLeft:20}
					
				]},
				{type: 'block', list: [
					{type:"select", label:"단말모델", name:"prdtId", labelWidth:60, width:180, offsetLeft:10, required:true},
					{type:'newcolumn'},
// 					{type:"select", label:"신청유형", name:"operType", labelWidth:60, width:100, offsetLeft:20, userdata:{lov:"RCP0003"}, required:true},
					{type:"select", label:"신청유형", name:"operType", labelWidth:60, width:100, offsetLeft:20, required:true},
					{type:'newcolumn'},
// 					{type:"select", label:"중고여부", name:"oldYn", labelWidth:75, width:100, offsetLeft:20, userdata:{lov:"RCP0026"} }
					{type:"select", label:"중고여부", name:"oldYn", labelWidth:75, width:100, offsetLeft:20}
				]},
				{type: 'block', list: [
					{type:"select", label:"요금제", name:"socCode", labelWidth:60, width:180, offsetLeft:10, required:true},
					{type:'newcolumn'},
					{type:"select", label:"약정기간", name:"agrmTrm", labelWidth:60, width:100, offsetLeft:20, required:true},
					{type:'newcolumn'},
// 					{type:"select", label:"할부기간", name:"instNom", labelWidth:75, width:100, offsetLeft:20, userdata:{lov:"RCP0047"}, required:true}
					{type:"select", label:"할부기간", name:"instNom", labelWidth:75, width:100, offsetLeft:20, required:true}
				]},
				{type : "newcolumn"}, 
				{type : "button", name : "btnSearch", value : "조회", className:"btn-search3"} 
			],
			onChange : function(name) {
				var form=this;
				form1Change(form, name);
			},
			onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpPlc.json", form);
						break;
				}
			}
		},

		// ----------------------------------------
		GRID1 : {
			css : {
				height : "300px"
			},
			title : "조회결과",
			header : "조직명,판매정책코드,판매정책명,할인유형,요금제명,신청유형명,모델코드,모델명,중고여부,약정기간,할부기간,출고가,공시지원금,대리점지원금(MAX),대리점지원금,할부원금,할부수수료,구매유형,신청유형,조직ID,제품ID,요금제코드,할부원금,가입비,유심비,제조사ID,중고여부코드,출고가(VAT포함),할인유형,기본료,기본할인금액,추가할인금액",
			columnIds : "orgnNm,salePlcyCd,salePlcyNm,sprtTpNm,rateNm,operTypeNm,prdtCode,prdtNm,oldYnNm,agrmTrm,instNom,modelPrice,modelDiscount2,maxDiscount3,modelDiscount3,realMdlInstamt,instCmsn,reqBuyType,operType,orgnId,prdtId,rateCd,realMdlInstamt,joinPrice,usimPrice,mnfctId,oldYnNm,hndstAmt,sprtTp,baseAmt,dcAmt,addDcAmt,prdtIndCd",
			widths : "150,150,200,80,200,100,100,150,80,80,80,100,100,150,100,100,100,50,50,50,50,50,50,50,50,50,50,80,50,80,80,80,80",
			colAlign : "left,left,left,center,left,center,center,left,center,center,center,right,right,right,right,right,right,center,center,center,center,center,right,right,right,right,center,right,center,right,right,right,right",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ron,ron,ron,ron,ro,ro,ro,ro,ro,ron,ron,ron,ro,ro,ron,ro,ron,ron,ron,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,int,int,int,int,int,int,str,str,str,str,str,int,int,int,str,str,int,str,int,int,int,str",
			hiddenColumns:[17,18,19,20,21,22,23,24,25,26,27,28,29],
			paging:true,
			pageSize:20,
			buttons : {
				center : {
					btnApply : { label: "확인" },
					btnClose : { label: "닫기" }
				}
			},
			checkSelectedButtons : ["btnApply"],
			onRowDblClicked : function(rowId, celInd) {
				// 선택버튼 누른것과 같이 처리
				this.callEvent('onButtonClick', ['btnApply']);
			},
			onButtonClick : function(btnId, selectedData) {
			
			switch (btnId) {
				case "btnApply":
					mvno.ui.closeWindow(selectedData, true);
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
		
		mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM1, "agrmTrm");
		mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "reqBuyType"); // 구매유형
		mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM1, "sprtTp"); // 할인유형
		mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "operType"); // 신청유형
		mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0011",orderBy:"etc1"}, PAGE.FORM1, "oldYn"); // 중고여부
		mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2005"}, PAGE.FORM1, "instNom"); // 할부기간
		
		// 서비스타입
		if("${param.serviceType}" != ''){
			 PAGE.FORM1.setItemValue("serviceType","${param.serviceType}");
		}
		
		// 신청일자
		if("${param.reqInDay}" != ''){
			PAGE.FORM1.setItemValue("reqInDay","${param.reqInDay}");
		}
		
		// 조직명
		if("${param.sOrgnNm}" != ''){
			// 조직명 한글 깨짐 현상 방지를 위한 방법
			// 넘길 때 코드로 넘기고 받을 때 다시 변환하여 처리함
			var orgnNmTmp = fncDeCode("${param.sOrgnNm}");
			PAGE.FORM1.setItemValue("orgnNm",orgnNmTmp); 
		}
		
		// 단말모델
		if("${param.prdtId}" != ''){
			PAGE.FORM1.setItemValue("prdtId","${param.prdtId}");
		}
		
		// 구매유형
		if("${param.reqBuyType}" != ''){
			PAGE.FORM1.setItemValue("reqBuyType","${param.reqBuyType}");	 
		}
		
		// 신청유형
		if("${param.operType}" != ''){
			PAGE.FORM1.setItemValue("operType","${param.operType}");	 
		}
		
		// 요금제
		if("${param.socCode}" != ''){
			PAGE.FORM1.setItemValue("socCode","${param.socCode}");
		}
		
		// 약정기간
		if("${param.agrmTrm}" != ''){
			PAGE.FORM1.setItemValue("agrmTrm","${param.agrmTrm}");
		}
		
		// 할부기간
		if("${param.instNom}" != ''){
			PAGE.FORM1.setItemValue("instNom","${param.instNom}");
		}
		
		// 할인유형
		if("${param.sprtTp}" != ''){
			PAGE.FORM1.setItemValue("sprtTp","${param.sprtTp}");
		}
		
		form1Change(PAGE.FORM1, "reqBuyType");
		form1Change(PAGE.FORM1, "operType");

		// 다시 값을 세팅
		
		// 단말모델
		if("${param.prdtId}" != ''){
			PAGE.FORM1.setItemValue("prdtId","${param.prdtId}");
		}
		
		// 요금제
		if("${param.socCode}" != ''){
			PAGE.FORM1.setItemValue("socCode","${param.socCode}");
		}
		
		// 구매유형
		if("${param.plcyTypeCd}" != ''){
			PAGE.FORM1.setItemValue("plcyTypeCd","${param.plcyTypeCd}");
		}
	}
};

function form1Change(form, name){

	switch(name){
		
		case 'reqBuyType' :

			// 단말모델 콤보박스 적용
			form2Polcy(form, "PRDT", "prdtId");
			
			// 요금제 적용
			form2Polcy(form, "RATE", "socCode");

			break;

		case 'operType' :

			// 단말모델 콤보박스 적용
			form2Polcy(form, "PRDT", "prdtId");
			
			// 요금제 적용
			form2Polcy(form, "RATE", "socCode");

			break;
	}

}

function form2Polcy(form, gubun, name){
	if(gubun == "PRDT") {
		// 단말모델 콤보박스 적용
		mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpSimPrdtInfo.json", 
							{
								grpId:gubun
								,orgnId:orgnId
								,reqBuyType:form.getItemValue("reqBuyType")
								,reqInDay:form.getItemValue("reqInDay")
							}, 
							form, 
							name);
	}

	if(gubun == "RATE") {
		// 요금제 콤보박스 적용
		mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json", 
							{
								grpId:gubun
								,reqBuyType:form.getItemValue("reqBuyType")
								,serviceType:form.getItemValue("serviceType")
								,reqInDay:form.getItemValue("reqInDay")
							}, 
							form, 
							name);
	}
}

function fncDeCode(param)
{
	var sb = '';
	var pos = 0;
	var flg = true;

	if(param != null)
	{
		if(param.length>1)
		{
			while(flg)
			{
				var sLen = param.substring(pos,++pos);
				var nLen = 0;
				
				try
				{
					nLen = parseInt(sLen);
				}
				catch(e)
				{
					nLen = 0;
				}
				
				var code = '';
				
				if((pos+nLen)>param.length)
					code = param.substring(pos);
				else
					code = param.substring(pos,(pos+nLen));
				
				pos  += nLen;
				sb += String.fromCharCode(code);
				
				if(pos >= param.length)
					flg = false;
			}
		}
	}
	
	return sb;
}
</script>