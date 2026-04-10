<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
    var lbl="";

    var isCntpShop = false;	//대리점, 판매점 여부
    var hghrOrgnCd = "";

    var sOrgnId = "";
    var sOrgnNm = "";

    var orgId = "${orgnInfo.orgnId}";
    var orgNm = "${orgnInfo.orgnNm}";

    var userId = "${loginInfo.userId}";
    var userName = "${loginInfo.userName}";

    var typeCd = "${orgnInfo.typeCd}";

    //조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
    if (typeCd == "20" || typeCd == "30" ) {
        isCntpShop = true;

        sOrgnId = "${orgnInfo.orgnId}";
        sOrgnNm = "${orgnInfo.orgnNm}";

        if(typeCd == "30"){
            hghrOrgnCd = "${orgnInfo.hghrOrgnCd}";
        }
    }

    if(typeCd == "10") {
        orgType = "K";
    }else if(typeCd == "20") {
        orgType = "D";
    }else if(typeCd == "30") {
        orgType = "S";
    }

    // 삭제권한 ( 본사사용자의 경우 Y )
    var delAuthYn = "N";
    var userOrgnTypeCd = "${loginInfo.userOrgnTypeCd}";

    if(userOrgnTypeCd == "10") {
        delAuthYn = "Y";
    }

    var salePlcyData = "";	//정책선택-combobox
    var modYn = false;		//수정 여부(기기변경 최초 등록 후 수정 시 Validation 체크)

    var typeDtlCd2 = "${orgnInfo.typeDtlCd2}";
    var dvcChgAuthYn = "${dvcChgAuthYn}";	//기기변경 가능여부
    var menuId = "";						//다른 화면에서 들어오는 경우 해당 MenuID

    // 스캔관련
    var blackMakingYn = "Y";					// 블랙마킹사용권한
    var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
    var maskingYn = "${maskingYn}";				// 마스킹권한
    if(maskingYn == "Y") {
        blackMakingFlag = "Y";
    }
    var agentVersion = "${agentVersion}";		// 스캔버전
    var serverUrl = "${serverUrl}";				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
    var scanSearchUrl = "${scanSearchUrl}";		// 스캔호출 url
    var scanDownloadUrl = "${scanDownloadUrl}";	// 스캔파일 download url

	var DHX = {
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					 {type : "calendar",width : 100,label : "조회기간",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true}
					, {type : "newcolumn"}
					, {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",  value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true}
					, {type : "newcolumn"}
					, {type : "select",width : 100 , label : "검색구분",name : "searchGbn" ,  offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"계약번호"}, {value:"2", text:"CTN"}]}
					, {type : "newcolumn"}
					, {type : "input",width : 100,name : "searchName", disabled:true}
					, {type : "newcolumn"}
					, {type : "select", label : "처리결과", width : 100, offsetLeft : 20, name : "successYn" }
					, {type : "hidden", name : "resNo" }
					, {type : "hidden", name : "scanId" }
				]},
					, {type : "button", name: "btnSearch", value:"조회", className:"btn-search1"}	
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
                    case "btnSearch" :
                        PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getNameChgList.json", form);
                        break;
				}
			}
			
			, 	onValidateMore : function(data) {
				if (data.srchStrtDt > data.srchEndDt) {
					this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
				}
				if(data.srchStrtDt != "" && data.srchEndDt == ""){
					this.pushError("srchStrtDt", "조회기간", "조회기간을 입력하세요.");
				}
				if(data.srchEndDt != "" && data.srchStrtDt == ""){
					this.pushError("srchEndDt", "조회기간", "조회기간을 입력하세요.");
				}				
				if( data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
				}
			}
			, onChange : function(name, value) {
				var form = this;
					
				// 검색구분
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == "") {

						var endDate = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var startDate = new Date(time);
						
						PAGE.FORM1.enableItem("srchStrtDt");
						PAGE.FORM1.enableItem("srchEndDt");
						
						PAGE.FORM1.setItemValue("srchStrtDt", startDate);
						PAGE.FORM1.setItemValue("srchEndDt", endDate);
						
						PAGE.FORM1.disableItem("searchName");
					}
					else {
						PAGE.FORM1.setItemValue("srchStrtDt", "");
						PAGE.FORM1.setItemValue("srchEndDt", "");
						
						// 개통일자 Disable
						PAGE.FORM1.disableItem("srchStrtDt");
						PAGE.FORM1.disableItem("srchEndDt");
						
						PAGE.FORM1.enableItem("searchName");
					}
				}
			}
		}
		// ----------------------------------------
		// 명의변경신청
		, GRID1 : {
			css : {
				height : "590px"
			}
			, title : "조회결과"
			, header : "계약번호,명의변경예약번호,고객명,성별,나이(만),개통일,개통 대리점,모집유형,유심접점,신청일시,양도인연락처,양수인연락처,결과,진행상태,시퀀스,메모,수정자,수정일시,orgScanId,resNo" // 20
			, columnIds : "svcContId,mcnResNo,subLinkName,gender,age,lstComActvDate,openAgntNm,onOffTypeNm,usimOrgNm,sysRdate,ctn,ctnGet,procCdNm,mcnStateCodeNm,custReqSeq,clMemo,updtId,sysUdate,orgScanId,resNo" // 20
			, widths : "100,100,80,60,60,90,130,110,160,130,100,100,60,80,1,150,80,130,1,1" // 20
			, colAlign : "center,center,left,center,center,center,center,center,center,center,center,center,center,center,center,center,left,center,center,center" //20
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXp,roXp,ro,ro,ro,ro,ro,ro,ro,ro" //20
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 20
			, hiddenColumns:[14,18,19]
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" },
					btnPaper : { label : "합본 전 서식지보기" },
				},
			},
			onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnDtl']);
			},

			onButtonClick : function(btnId) {
				var grid = this;
				switch (btnId) {
					case "btnExcel":
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} 
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getNameChgListExcel.json?menuId=MSP1010039", true, {postData:searchData}); 
		
					break;
					
					case "btnDtl":
                        var sdata = grid.getSelectedRowData();
                        if(sdata == null){
                            mvno.ui.alert("선택된 데이터가 없습니다");
                            return;
                        }
                        mvno.ui.createForm("FORM2");

                        PAGE.FORM2.clear();
                        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"CL01"}, PAGE.FORM2, "procCd"); // 처리결과
                        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"JehuProdType"}, PAGE.FORM2, "jehuProdType"); // 제휴처
                        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9003"}, PAGE.FORM2, "norAgentTypeCd"); // 양도인 법정대리인 관계
                        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9003"}, PAGE.FORM2, "neeAgentTypeCd"); // 양수인 법정대리인 관계
                        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2006"}, PAGE.FORM2, "minorSelfCertType"); // 양수인 법정대리인 인증방식
                        mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2006"}, PAGE.FORM2, "selfCertType"); // 양수인 법정대리인 인증방식

                        mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getNameChgDtl.json", {"custReqSeq":sdata.custReqSeq}, function(resultData) {
                            var data = resultData.result.data.rows[0];
                            PAGE.FORM2.setFormData(data, true);

                            if (PAGE.FORM2.getItemValue("norCstmrType") == "NM") {
                                PAGE.FORM2.showItem("NORMINOR");
                            } else {
                                PAGE.FORM2.hideItem("NORMINOR");
                            }
                            if (PAGE.FORM2.getItemValue("neeCstmrType") == "NM") {
                                PAGE.FORM2.showItem("NEEMINOR");
                            } else {
                                PAGE.FORM2.hideItem("NEEMINOR");
                            }

                            // 결과가 처리 인 경우
                            if (PAGE.FORM2.getItemValue("procCd") == "CP" ) {
                                PAGE.FORM2.disableItem("procCd");
                                //OSST연동 버튼
                                mvno.ui.hideButton("FORM2" , "btnMC0");
                                mvno.ui.hideButton("FORM2" , "btnMP0");
                                mvno.ui.hideButton("FORM2" , "btnCopy");
                            } else {
                                PAGE.FORM2.enableItem("procCd");
                                //OSST연동 버튼
                                mvno.ui.showButton("FORM2" , "btnMC0");
                                mvno.ui.showButton("FORM2" , "btnMP0");
                                mvno.ui.showButton("FORM2" , "btnCopy");
                            }
                        });
						
						mvno.ui.popupWindowById("FORM2", "상세화면", 850, 740, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
									
						break;
						
					case "btnPaper":
						PAGE.FORM1.setItemValue("resNo",PAGE.GRID1.getSelectedRowData().resNo);
						PAGE.FORM1.setItemValue("scanId",PAGE.GRID1.getSelectedRowData().orgScanId);
						appFormView(PAGE.GRID1.getSelectedRowData().orgScanId); 
							
						if(maskingYn == "Y" || maskingYn == "1"){
							mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"resNo":form.getItemValue("resNo"),"trgtMenuId":"MSP1000014","tabId":"paper"}, function(result){});
						}
					break;
				}
			}
	  },
		FORM2 : {
			items : [
				{type:"block", list:[
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0},
					// 양도인 정보
					{type: "fieldset", label: "양도인 정보", list:[
                        {type:"settings"},
					    {type:"block", list:[
                            {type:"input", label:"고객명", name:"subLinkName", width:90, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", label:"신청일자", name:"sysRdate", width:140, offsetLeft:10, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", label:"전화번호", name:"ctn", width:100, offsetLeft:10, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", label:"고객정보삭제동의", name:"clauseCntrDelFlag", labelWidth:100, width:20, offsetLeft:10, disabled:true},
                            {type:"hidden", name:"norCstmrType"}
                        ]}
                    ]},
                    // 양도인 법정대리인 정보
                    {type: "fieldset", label: "양도인 법정대리인 정보", name:"NORMINOR", list:[
                        {type:"settings"},
                        {type:"block", list:[
                            {type:"input", width:90, label:"대리인성명", labelWidth:60, name:"norAgentName", maxLength:20, disabled:true},
                            {type:"newcolumn"},
                            {type:"select", width:90, label:"관계", labelWidth:30, name:"norAgentTypeCd", offsetLeft:20, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", width:120, label:"대리인주민번호", labelWidth:90, name:"norAgentRrn", offsetLeft:19, maxLength:6, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", width:90, label:"연락처", name:"norAgentTelNo", labelWidth:50, offsetLeft:20, disabled:true}
                        ]},
                        {type:"block", list:[
                            {type:"checkbox", width:130, label:"본인조회동의", name:"norAgentSelfInqryAgrmYn", labelWidth:80, value:"Y", disabled:true}
                        ]}
                    ]},
					// 양수인 정보
					{type: "fieldset", label: "양수인 정보", list:[
                        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0},
					    {type:"block", list:[
					        {type:"input", label:"고객명", name:"cstmrName", width:150, disabled:true},
					        {type:"newcolumn"},
					        {type:"input", label:"이메일", name:"cstmrMail", width:170, offsetLeft:10, disabled:true},
					        {type:"newcolumn"},
					        {type:"input", label:"전화번호", name:"ctnGet", labelWidth:60, width:140, offsetLeft:20, disabled:true},
                            {type:"hidden", name:"neeCstmrType"}
						]},
						{type:"block", list:[
                            {type: "label", label: "고객 혜택 제공을 위한 정보수집 이용동의 및 혜택 광고의 수신 위탁 동의", labelWidth:340, disabled:true},
							{type:"newcolumn"},
                            {type: "label", label: "혜택제공을 위한 제 3자 제공 광고 수신 동의", labelWidth:300, offsetLeft:51,  disabled:true}
						]},
						{type:"block", list:[
							{type:"input", label:"고객 혜택 제공을 위한 개인정보 수집 및 관련 동의", name:"personalInfoCollectAgree", labelWidth:280, offsetLeft:20, width:20, disabled:true},
							{type:"newcolumn"},
                            {type:"input", label:"혜택 제공을 위한 제 3자 제공 동의 : M모바일", name:"othersTrnsAgree", labelWidth:250, width:20, offsetLeft:86, disabled:true}
						]},
                        {type:"block", list:[
                            {type:"input", label:"개인정보 처리 위탁 및 혜택 제공 동의", name:"clausePriAdFlag", labelWidth:280, offsetLeft:20, width:20, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", label:"혜택 제공을 위한 제 3자 제공 동의 : KT", name:"othersTrnsKtAgree", labelWidth:250, width:20, offsetLeft:86, disabled:true}
                        ]},
                        {type:"block", list:[
                            {type:"input", label:"제 3자 제공관련 광고 수신 동의", name:"othersAdReceiveAgree", labelWidth:250, width:20, offsetLeft:412, disabled:true}
                        ]},
						{type:"block", list:[
							{type:"input", label:"고유식별정보 수집이용제공동의", name:"clauseEssCollectFlag", labelWidth:180, width:20, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"개인/신용정보 수집동의", name:"clausePriCollectFlag", offsetLeft:10, labelWidth:200, width:20, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"개인정보 제3자 제공동의", name:"clausePriOfferFlag", offsetLeft:20, labelWidth:180, width:20, disabled:true}
						]},
						{type:"block", list:[
							{type:"input", label:"제휴서비스를 위한동의", name:"clauseJehuFlag", labelWidth:180, width:20, disabled:true},
							{type:"newcolumn"},
							{type:"select", width:220, label:"", name:"jehuProdType", offsetLeft:10, disabled:true}
						]}
					]},
                    // 양수인 법정대리인 정보
                    {type: "fieldset", label: "양수인 법정대리인 정보", name:"NEEMINOR", list:[
                        {type:"settings"},
                        {type:"block", list:[
                            {type:"input", width:90, label:"대리인성명", labelWidth:60, name:"neeAgentName", maxLength:20, disabled:true},
                            {type:"newcolumn"},
                            {type:"select", width:90, label:"관계", labelWidth:30, name:"neeAgentTypeCd", offsetLeft:20, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", width:120, label:"대리인주민번호", labelWidth:90, name:"neeAgentRrn", offsetLeft:19, maxLength:6, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", width:90, label:"연락처", name:"neeAgentTelNo", labelWidth:50, offsetLeft:20, disabled:true}
                        ]},
                        // 부정가입방지조회
                        {type:"block", list:[
                            {type:"checkbox", width:130, label:"본인조회동의", name:"neeAgentSelfInqryAgrmYn", labelWidth:80, value:"Y", disabled:true},
                            {type:"newcolumn"},
                            {type:"select", labelWidth:65, width:90, label:"인증방식", name:"minorSelfCertType", labelAlign:"right", offsetLeft:35, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", label:"발급일자", name:"neeAgentSelfIssuExprDt", labelWidth:54, width:80, offsetLeft:55, maxLength:8, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", label:"발급번호", name:"neeAgentSelfIssuNum", labelWidth:60, width:120, offsetLeft:20, disabled:true}
                            ]}
                        ]},
                    // 안면인증 정보
                    {type: "fieldset", label: "안면인증 정보", list:[
                        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0},
                        {type:"block", list:[
                            {type:"checkbox", label:"안면인증대상여부", width : 50, name : "fathTrgYn", value:"Y", labelWidth : 100, disabled:true},
                            {type:"newcolumn"},
                            {type:"button", value:"조회", name:"btnFT0"},
                            {type:"newcolumn"},
                            {type:"checkbox", width:50, labelWidth:80, label:"안면인증동의", offsetLeft:20, position:"label-right", name:"clauseFathFlag", value:"Y", checked:false},
                            {type:"newcolumn"},
                            {type:"input", label:"안면인증 연락처", name:"smsRcvTelNo", labelWidth:90, width:100, offsetLeft:103, maxLength:11, validate:"ValidNumeric"},
                            {type:"newcolumn"},
                            {type:"button", value:"셀프URL전송", name:"btnFath", offsetLeft:5, width:75}
                        ]},
                        {type:"block", list:[
                            {type:"button", value:"URL요청", name:"btnFS8"},
                            {type:"newcolumn"},
                            {type:"input", label:"URL요청결과", name:"fathTransacId", labelWidth:80, offsetLeft:10, width:213, disabled:true},
                            {type:"newcolumn"},
                            {type:"input", label:"안면인증일자", name:"fathCmpltNtfyDt", labelWidth:90, offsetLeft:22, width:100, disabled:true},
                            {type:"newcolumn"},
                            {type:"button", value:"인증결과확인", name:"btnReqFathTxnRetv", offsetLeft:5, width:75}
                        ]},
                        {type:"block", list:[
                            {type:"button", value:"안면인증SKIP", name:"btnFT1", offsetLeft:605, width:75, hidden:${!isEnabledFT1}},
                        ]},
                    ]},
                    // 양수인 납부 정보
					{type: "fieldset", label: "양수인 납부정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"정보변경여부", name:"reqInfoChgYn", labelWidth:80,width:120, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"요금제", name:"soc", labelWidth:80, width:140, offsetLeft:10, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"명세서유형", name:"cstmrBillSendCode", labelWidth:80, width:120, offsetLeft:20, disabled:true},
						]},
						{type:"block", list:[
							{type:"input", label:"요금납부방법", name:"reqPayType", labelWidth:80,width:120, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"은행", name:"reqBank", labelWidth:80, width:140, offsetLeft:10, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"계좌번호", name:"reqAccountNumber", labelWidth:80, width:120, offsetLeft:20, disabled:true, maxLength:16, validate:"ValidNumeric"},
						]},
						{type:"block", list:[
							{type:"input", label:"카드사", name:"reqCardCompany", labelWidth:80,width:120, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"카드번호", name:"reqCardNo", labelWidth:80, width:140, offsetLeft:10, disabled:true, maxLength:16, validate:"ValidNumeric"},
							{type:"newcolumn"},
							{type:"input", label:"유효년월", name:"reqCardYy", labelWidth:80, width:120, offsetLeft:20, disabled:true},
						]},
						{type:"block", list:[
							{type:"input", label:"우편번호", name:"cstmrPost", labelWidth:80,width:60, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"주소", name:"cstmrAddr", labelWidth:30, width:250, offsetLeft:10, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"상세주소", name:"cstmrAddrDtl", labelWidth:80, width:120, offsetLeft:20, disabled:true},
						]},
						{type:"block", list:[
							{type:"select", label:"신분증 유형", name:"selfCertType", labelWidth:80,width:120, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"발급/만료일자", name:"selfIssuExprDt", labelWidth:80, width:140, offsetLeft:10, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"발급번호", name:"selfIssuNum", labelWidth:80, width:120, offsetLeft:20, disabled:true},
						]},
						{type:"block", list:[
							{type:"input", label:"국적", name:"cstmrFnNm", labelWidth:80,width:356, disabled:true}
						]},
					]},
					{type: "fieldset", label: "처리결과", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"메모", name:"clMemo", labelWidth:80, width:360, maxLength:100},
							{type:"newcolumn"},
							{type:"select", label:"처리결과", name:"procCd", labelWidth:80, offsetLeft:20, width:120},
							{type:"hidden", name:"custReqSeq"},
                          {type:"hidden", name:"appEventCd"},
                          {type:"hidden", name:"mcnResNo"},
                          {type:"hidden", name:"fathUseYn"},
                          {type:"hidden", name:"isFathTrgFlag"},
                          {type:"hidden", name:"cntpntShopId"},
                          {type:"hidden", name:"mngmAgncId"},
                          {type:"hidden", name:"cstmrType"},
                          {type:"hidden", name:"cstmrReceiveTelFn"},
                          {type:"hidden", name:"cstmrReceiveTelMn"},
                          {type:"hidden", name:"cstmrReceiveTelRn"},
                          {type:"hidden", name:"mcnStateCode"}
						]},
					]}
				]}
			],
			buttons : {
                left : {
                    btnMC0 : {label:"사전체크"}
                    , btnMP0 : {label:"명의변경요청"}
                    , btnMSG : {label:"연동결과조회"}
                },
                right : {
                    btnCopy : {label:"신청서복사"}
                    , btnSave : {label:"저장" }
                    , btnClose : {label:"닫기" }
                }
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
				    case "btnSave":
						mvno.cmn.ajax(ROOT + "/stats/statsMgmt/updateName.json", form, function(result) {
							mvno.ui.closeWindowById(PAGE.FORM2, true);
						  mvno.ui.notify("NCMN0001");
						
						  PAGE.GRID1.refresh();
						});
						break;
											
					case "btnClose" :
						mvno.ui.closeWindowById(PAGE.FORM2, true);
						break;

                    case "btnCopy":
                        if(mvno.cmn.isEmpty(form.getItemValue("custReqSeq"))){
                            mvno.ui.alert("시퀀스가 없습니다.");
                            return;
                        }

                        if(PAGE.FORM2.getItemValue("procCd") == "CP"){
                            mvno.ui.alert("명의변경처리 완료된 신청서는 복사할 수 없습니다.");
                            return;
                        }

                        mvno.ui.confirm("신청서 복사 시 기존 신청서는 반려처리 됩니다.<br>복사하시겠습니까?", function() {

                            mvno.cmn.ajax(ROOT + "/stats/statsMgmt/copyMcnRequest.json", form, function (result) {
                                mvno.ui.alert("복사되었습니다.");
                                mvno.ui.closeWindowById(PAGE.FORM2, true);
                                mvno.ui.notify("NCMN0001");

                                PAGE.GRID1.refresh();
                            });
                        });
                        break;

                    case "btnMC0":

                        //validation check
                        if(!validationCheck(PAGE.FORM2)) return;

                        mvno.ui.confirm("명의변경 사전체크 하시겠습니까?", function() {
                            form.setItemValue("appEventCd", "MC0");
                            mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", form, function (resultData) {
                                mvno.ui.alert(resultData.result.msg);
                            });
                        });

                        break;

                    case "btnMP0":

                        if(!validationCheck(PAGE.FORM2)) return;

                        mvno.ui.confirm("명의변경 요청 하시겠습니까?", function() {
                            form.setItemValue("appEventCd", "MP0");
                            mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", form, function (resultData) {
                                mvno.ui.alert(resultData.result.msg);
                            });
                        });

                        break;

                    case "btnMSG":
                        if(mvno.cmn.isEmpty(form.getItemValue("mcnResNo"))){
                            mvno.ui.alert("예약번호가 없습니다.");
                            return;
                        }

                        var params = "?resNo="+ form.getItemValue("mcnResNo");

                        mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpSimpMsgView.do" + params, "연동결과조회", 650, 360,
                            {
                                param : {
                                    callback : function(result) {
                                    }
                                }
                            }
                        );

                        break;

                    //고객 안면인증 대상 여부 조회(FT0)
                    case "btnFT0" :

                        if(PAGE.FORM2.isItemChecked("fathTrgYn")) {
                            mvno.ui.confirm("안면인증을 진행한 이력이 있습니다.<br>새로 진행하시겠습니까?", function() {
                                FT0Send(PAGE.FORM2);
                            });
                        }else {
                            FT0Send(PAGE.FORM2);
                        }

                        break;

                    //고객 안면인증 URL 요청 (FS8)
                    case "btnFS8" :

                        if(!PAGE.FORM2.isItemChecked("fathTrgYn")){
                            mvno.ui.alert("안면인증대상이 아닙니다.");
                            return;
                        }

                        if(!PAGE.FORM2.isItemChecked("clauseFathFlag")){
                            mvno.ui.alert("{안면인증동의}에 동의하여야 합니다.");
                            return;
                        }

                        if(form.getItemValue("smsRcvTelNo") != "") {
                            if(form.getItemValue("smsRcvTelNo").length != 11) {
                                mvno.ui.alert("안면인증 연락처를 바르게 입력해주세요.");
                                return;
                            }
                        }

                        //고객 안면인증 URL 요청 (FS8)
                        mvno.ui.confirm("안면인증 URL을 전송하시겠습니까?", function() {
                            PAGE.FORM2.setItemValue("appEventCd", "FS8");
                            mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", PAGE.FORM2, function(resultData) {
                                if(resultData.result.code == "OK"){
                                    if(resultData.result.resltCd == "00000"){
                                        PAGE.FORM1.setItemValue("fathTransacId", resultData.result.fathTransacId);
                                    }
                                    mvno.ui.alert(resultData.result.resltSbst);
                                } else {
                                    mvno.ui.alert(resultData.result.msg);
                                }
                            });
                        });

                        break;

                    //고객 안면인증 내역 조회 
                    case "btnReqFathTxnRetv" :

                        if(!PAGE.FORM2.isItemChecked("fathTrgYn")){
                            mvno.ui.alert("안면인증대상이 아닙니다.");
                            return;
                        }

                        if(!PAGE.FORM2.isItemChecked("clauseFathFlag")){
                            mvno.ui.alert("{안면인증동의}에 동의하여야 합니다.");
                            return;
                        }

                        
                        mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/reqFathTxnRetv.json", PAGE.FORM2, function(resultData) {
                            if(resultData.result.code == "OK"){

                                PAGE.FORM2.setItemValue("fathCmpltNtfyDt", resultData.result.fathCmpltNtfyDt);

                                if(PAGE.FORM2.getItemValue("cstmrType") == "NM") { //미성년자
                                    PAGE.FORM2.setItemValue("minorSelfCertType", resultData.result.selfCertType);
                                    PAGE.FORM2.setItemValue("neeAgentSelfIssuExprDt", resultData.result.selfIssuExprDt);
                                    PAGE.FORM2.setItemValue("neeAgentSelfIssuNum", resultData.result.driverSelfIssuNum);
                                } else {
                                    PAGE.FORM2.setItemValue("selfCertType", resultData.result.selfCertType);
                                    PAGE.FORM2.setItemValue("selfIssuExprDt", resultData.result.selfIssuExprDt);
                                    PAGE.FORM2.setItemValue("selfIssuNum", resultData.result.driverSelfIssuNum);
                                }
                            }

                            mvno.ui.alert(resultData.result.msg);
                        });

                        break;

                    //고객 셀프안면인증 URL 발급
                    case "btnFath" :

                        if(!PAGE.FORM2.isItemChecked("fathTrgYn")){
                            mvno.ui.alert("안면인증대상이 아닙니다.");
                            return;
                        }

                        if(!PAGE.FORM2.isItemChecked("clauseFathFlag")){
                            mvno.ui.alert("{안면인증동의}에 동의하여야 합니다.");
                            return;
                        }

                        //기본은 양수인 연락처지만 안면인증 연락처를 입력하면 안면인증 연락처로 보냄
                        var telNo = form.getItemValue("cstmrReceiveTelFn") + form.getItemValue("cstmrReceiveTelMn") + form.getItemValue("cstmrReceiveTelRn");
                        if(form.getItemValue("smsRcvTelNo") != "") {
                            if(form.getItemValue("smsRcvTelNo").length != 11) {
                                mvno.ui.alert("안면인증 연락처를 바르게 입력해주세요.");
                                return;
                            }else {
                                telNo = form.getItemValue("smsRcvTelNo");
                            }
                        }



                        var data = {
                            resNo : form.getItemValue("mcnResNo"),
                            smsRcvTelNo : telNo,
                            operType : "MCN",
                            cntpntShopId : form.getItemValue("cntpntShopId")
                        }

                        mvno.ui.confirm("문자를 전송하시겠습니까?", function() {
                            mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/insertFathSelfUrl.json", data, function(resultData) {
                                if (resultData.result.code === "OK") {
                                    mvno.ui.alert("문자 전송이 완료되었습니다.")
                                } else {
                                    mvno.ui.alert("문자 전송에 실패했습니다.");
                                }
                            });
                        });

                        break;

                    case "btnFT1":

                        if(!PAGE.FORM2.isItemChecked("fathTrgYn")){
                            mvno.ui.alert("안면인증대상이 아닙니다.");
                            return;
                        }

                        if(!PAGE.FORM2.isItemChecked("clauseFathFlag")){
                            mvno.ui.alert("{안면인증동의}에 동의하여야 합니다.");
                            return;
                        }

                        if(!PAGE.FORM2.getItemValue("fathTransacId")){
                            mvno.ui.alert("{URL요청}이나 {셀프URL전송}을 먼저 진행해야 합니다.");
                            return;
                        }

                        mvno.ui.confirm("안면인증을 SKIP 하시겠습니까?", function() {
                            mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/requestCustFathTxnSkip.json", PAGE.FORM2, function(resultData) {
                                if(resultData.result.code == "0000"){
                                    mvno.ui.alert("안면인증 SKIP 성공");
                                } else {
                                    mvno.ui.alert(resultData.result.msg);
                                }
                            });
                        });
                        break;
				}
			}
		}
	}
	var PAGE = {
			title: "${breadCrumb.title}"
			, breadcrumb: "${breadCrumb.breadCrumb}"
			, buttonAuth: ${buttonAuth.jsonString}
			, onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"CL01"  }, PAGE.FORM1, "successYn" ); // 성공여부
			}
		};
	
	function appFormView(scanId){
		var DBMSType = "C";
		
		var data = null;
		var modifyFlag = "N";
		data = "AGENT_VERSION="+agentVersion+
			"&SERVER_URL="+serverUrl+
			"&VIEW_TYPE=MCPVIEW"+
			"&USE_DEL="+delAuthYn+
			"&USE_STAT="+modifyFlag+
			"&USE_BM="+blackMakingYn+
			"&USE_DEL_BM="+blackMakingFlag+
			"&RGST_PRSN_ID="+userId+
			"&RGST_PRSN_NM="+userName+
			"&ORG_TYPE="+orgType+
			"&ORG_ID="+orgId+
			"&ORG_NM="+orgNm+
			"&RES_NO="+PAGE.FORM1.getItemValue("resNo")+
			"&PARENT_SCAN_ID="+scanId+
			"&DBMSType="+DBMSType
			;
	
		callViewer(data);

	}
	
	//스캔 호출
	function callViewer(data){
		var url = scanSearchUrl + "?" + encodeURIComponent(data);
		
		// 타임아웃 설정 - 10초이내 응답없으면 서비스 실행상태가 아님.
		var timeOutTimer = window.setTimeout(function (){
			mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
				window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
			});
		}, 10000);
		
		$.ajax({
			type : "GET",
			url : url,
			crossDomain : true,
			dataType : "jsonp",
			success : function(args){
				window.clearTimeout(timeOutTimer);
				if(args.RESULT == "SUCCESS") {
					console.log("SUCCESS");
				} else if(args.RESULT == "ERROR_TYPE2") {
					mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
					window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
				} else {
					mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
				}
			}
		});
	}


    function validationCheck(form) {

        if(form.getItemValue("procCd") != "RQ") {
            mvno.ui.alert("처리결과가 '신청'이어야 합니다.");
            return false;
        }

        return true;
    }

    function FT0Send(form) {
        form.setItemValue("appEventCd", "FT0");
        mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", form, function(resultData) {

            if(resultData.result.code == "OK"){
                if(resultData.result.resltCd == "00000"){
                    PAGE.FORM1.setItemValue("fathTrgYn", "1");
                    PAGE.FORM1.setItemValue("isFathTrgFlag", "1");
                } else if(resultData.result.resltCd == "00001" || resultData.result.resltCd == "00002") {
                    PAGE.FORM1.setItemValue("fathTrgYn", "0");
                    PAGE.FORM1.setItemValue("isFathTrgFlag", "1");
                } else if(resultData.result.resltCd == "00003"){
                    PAGE.FORM1.setItemValue("fathTrgYn", "0");
                }
                mvno.ui.alert(resultData.result.resltSbst);
            } else {
                mvno.ui.alert(resultData.result.msg);
            }
        });
    }
</script>