<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_custmgmt_0070.jsp
   * @Description : 고객정보상세
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2009.02.01            최초 생성
   *
   * author 실행환경 개발팀
   * since 2009.02.01
   *
   * Copyright (C) 2009 by MOPAS  All right reserved.
   */
   %>
	
<div id="GROUP1">
	<div id="FORM2" class="section-search"></div>
	<div id="TABBAR1"></div>  
	
		<div id="TABBAR1_a1" class="section-search" style="margin-top: 20px">
			<div id="FORM4"></div>
		</div>  
		
		<div id="TABBAR1_a2" class="section-search" style="margin-top: 20px">
			<div id="FORM5"></div> 
		</div>  
		
		<div id="TABBAR1_a3">
			<div id="FORM7" class="section-search" style="margin-top: 20px"></div> 
			<div id="GRID4"></div>  
		</div> 
	
		<div id="TABBAR1_a4" style="display:none; padding-top:10px;">
			<div id="GRID5"></div>   
		</div> 
		<div id="TABBAR1_a5" style="display:none; padding-top:10px;">
			<div id="FORM8" class="section-search"></div> 
			<div id="GRID6"></div>   
		</div>
		<div id="TABBAR1_a6" style="display:none; padding-top:10px;">
			<div id="FORM9" class="section-search"></div> 
			<div id="GRID7"></div>   
		</div> 
		<div id="TABBAR1_a7" style="display:none; padding-top:10px;">
			<div id="GRID8"></div>   
		</div>  	  	 	 	  	 	
		<div id="TABBAR1_a8" style="display:none; padding-top:10px;">
			<div id="GRID9"></div>
		</div>
		<div id="TABBAR1_a9" style="display:none; padding-top:10px;">
			<div id="FORM30" class="section-search" style="margin-top: 20px"></div> 
		</div>
		<div id="TABBAR1_a10" style="display:none; padding-top:10px;">
			<div id="GRID10"></div>
		</div>   	
</div>
	
<!-- 상담등록 -->
<div id="FORM10" class="section-search"></div>
<!-- 상담등록 수정 -->
<div id="FORM10_2" class="section-search"></div>
<!-- sms보내기 --> 
<div id="FORM11" class="section-search"></div>
<!-- 엑셀출력 --> 
<div id="FORM12"></div>
<!-- 이미지등록/변경  -->
<div id="FORM15"></div>
<!-- 국적/SMS 언어  -->
<div id="FORM16" class="section-search"></div>    
<!-- 문자알림변경  -->
<div id="FORM17" class="section-search"></div>
<!-- 대리점 일괄변경  -->
<div id="FORM18" class="section-search"></div>
<!-- 신 이미지등록/변경  -->
<div id="FORM22"></div>
<!-- 회원증빙  -->
<div id="FORM23"></div>
<!-- 체류기간 수정  -->
<div id="FORM25" class="section-search"></div>
<FORM name="viewForm" id="viewForm">
	<input type="hidden" name="AGENT_VERSION" value="">
	<input type="hidden" name="SERVER_URL" value="">
	<input type="hidden" name="VIEW_TYPE" value="TVIEW">
	<input type="hidden" name="FILE_PATH" value="">
	<input type="hidden" name="ENCODE_YN" value="">
	<input type="hidden" name="USE_BM" value="">
	<input type="hidden" name="USE_DEL_BM" value="">
</FORM>

	
<!-- Script -->
<script type="text/javascript">
	var stayExpirFlag = false;
 
	// 스캔관련
	var blackMakingYn = "Y";					// 블랙마킹사용권한
	var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
	var maskingYn = '${maskingYn}';				// 마스킹권한
	if(maskingYn == "Y") {
		blackMakingFlag = "Y";
	}	
	var agentVersion = '${agentVersion}';		// 스캔버전
	var serverUrl = '${serverUrl}';				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
	var scanSearchUrl = '${scanSearchUrl}';		// 스캔호출 url
	var scanDownloadUrl = '${scanDownloadUrl}';	// 스캔파일 download url
	
	var paper_old_url = "";
	var payinfo_old_url = "";
	
	function alignCenterStatus()
	{
		var w = screen.width / 2;
		var h = screen.height / 2;
	
		var LeftPosition = (screen.width) ? (screen.width-w)/2 : 0;
		var TopPosition = (screen.height) ? (screen.height-h)/2 : 0;
	
		var status = "width=" + w + ",height=" + h + ",top=" + TopPosition + ",left=" + LeftPosition;
		
		return status;
	}

	function getParams(key){
	    var _parammap = {};
	    document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {
	        function decode(s) {
	            return decodeURIComponent(s.split("+").join(" "));
	        }
	
	        _parammap[decode(arguments[1])] = decode(arguments[2]);
	    });
	
	    return _parammap[key];
	};

	function fnGoPpsCustomerDetail(url, title, menuId, contractNum) {
    	
		var myTabbar = window.parent.mainTabbar;

		if (myTabbar.tabs(url)) {
        	myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
        }

        myTabbar.addTab(url, title, null, null, true);
        myTabbar.tabs(url).attachURL(url + "?menuId=" + menuId+"&contractNum="+contractNum);

        // 잘안보여서.. 일단 살짝 Delay 처리 
        setTimeout(function() {
            //mainLayout.cells("b").progressOff();
        }, 100);


    }

	//충전가능/불가 변경
	function href(name,value){

		var retValue = '';
		
		if(name == "rechargeFlags"){
			
			if(PAGE.FORM2 != null)
			{
				var valueStr = PAGE.FORM2.getItemValue("rechargeFlag");
				
				if(valueStr != null && valueStr == 'Y'){
					retValue = "<div><a href='javascript:rechargeFlagChg(\"N\")' style='font-size:12px; text-decoration:none; paddin-top:10px;'>충전가능</a></div>";
				}else if(valueStr != null && valueStr == 'N'){
					retValue = "<div><a href='javascript:rechargeFlagChg(\"Y\")' style='font-size:12px;  text-decoration:none;'>충전불가</a></div>";
				}	
			}
		}
		else if(name == 'smsFlags'){

			if(PAGE.FORM2 != null)
			{
				var valueStr = PAGE.FORM2.getItemValue("smsFlag");
				
				if(valueStr != null && valueStr == 'Y'){
					retValue = "<div><a href='javascript:smsFlagChg(\"N\")' style='font-size:12px; text-decoration:none;'>문자전송</a></div>";
				}else if(valueStr != null && valueStr == 'N'){
					retValue = "<div><a href='javascript:smsFlagChg(\"Y\")' style='font-size:12px; text-decoration:none;'>문자미전송</a></div>";
				}	
			}
		}
		else if(name == "smsPhone"){

			if(PAGE.FORM2 != null)
			{
				var smsPhoneNumberStr = PAGE.FORM2.getItemValue("smsPhoneNumber");
				if(smsPhoneNumberStr != null && smsPhoneNumberStr.length > 0)
				{
					retValue = "<div><a href='javascript:smsNumChg()' style='font-size:12px; text-decoration:none;'>"+smsPhoneNumberStr+"</a></div>";

				}
				else if(smsPhoneNumberStr == null || smsPhoneNumberStr =='')
				{
					retValue = "<div><a href='javascript:smsNumChg()' style='font-size:12px; text-decoration:none;'>발송 번호 등록</a></div>";
				}
			}
		}
		else if(name == "vizaFlags"){

			if(PAGE.FORM2 != null)
			{
				var valueStr = PAGE.FORM2.getItemValue("vizaFlag");
				if(valueStr != null && valueStr == 'Y'){
					retValue = "<div><a href='javascript:vizaFlagChg(\"N\")' style='font-size:12px; text-decoration:none;'>접수</a></div>";
				}else if(valueStr != null && valueStr == 'N'){
					retValue = "<div><a href='javascript:vizaFlagChg(\"Y\")' style='font-size:12px; text-decoration:none;'>미접수</a></div>";
				}	
			}
		}else if(name == "topupRcgAmtStr"){
			
			if(PAGE.FORM2 != null)
			{
				var valueStr = PAGE.FORM2.getItemValue("topupRcgAmt");
				
				if(valueStr != 0){
					retValue = "<div><font color='red'>요금제:"+valueStr+"</font></div>";
				}else{
					retValue = "";
				}	
			}
		}
		
		return retValue;
	}
	
	function rechargeFlagChg(chgFlag){

		//console.log("ROOT : ", ROOT);
		
		var contractNum = PAGE.FORM2.getItemValue("contractNum");
		if(chgFlag == 'N'){
			var msg = "충전불가로 변경하시겠습니까?";
		}else if(chgFlag == 'Y'){
			var msg = "충전가능으로 변경하시겠습니까?";
		}
		
		mvno.ui.prompt("변경사유", function(result){
			mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/custmgmt/PpsCusRcgFlagChg.json", {
				contractNum: contractNum,
				rcgFlag: chgFlag,
				resBody : result
			}, function(results) {
				
				if(results.result.oRetCd == "000"){
					mvno.ui.notify("변경되었습니다");
					
					PAGE.FORM2.setItemValue("rechargeFlag",results.result.rcgFlag);

					var rechargeFlagStr = href("rechargeFlags" ,results.result.rcgFlag);
					PAGE.FORM2.setItemValue("rechargeFlags",rechargeFlagStr);
					
					setForm2Title(contractNum, results.result.subscriberNoMsk, results.result.subStatusNm, results.result.subStatusDate, results.result.rechargeStr, results.result.warFlagStr);
				}else{
					mvno.ui.notify(results.result.oRetMsg);
				}
				

			});
		} , { required:true,lines:1,maxLength : 80 });
		
		
	}

	function smsFlagChg(chgFlag){
		var contractNum = PAGE.FORM2.getItemValue("contractNum");
		if(chgFlag == 'N'){
			var msg = "문자미알림으로 변경하시겠습니까?";
		}else if(chgFlag == 'Y'){
			var msg = "문자알림으로 변경하시겠습니까?";
		}
		mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/custmgmt/PpsCusSmsFlagChg.json", {
			contractNum: contractNum,
			smsFlag: chgFlag
		}, function(results) {
			//mvno.ui.alert(results.result.oRetMsg);

			PAGE.FORM2.setItemValue("smsFlag",results.result.smsFlag);
			
			var smsFlag = href("smsFlags",results.result.smsFlag);
			PAGE.FORM2.setItemValue("smsFlags",smsFlag);

		});
	}
	
	function vizaFlagChg(chgFlag){
		var contractNum = PAGE.FORM2.getItemValue("contractNum");
		if(chgFlag == 'N'){
			var msg = "미접수로 변경하시겠습니까?";
		}else if(chgFlag == 'Y'){
			var msg = "접수로 변경하시겠습니까?";
		}
		mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/custmgmt/PpsCusVizaFlagChg.json", {
			contractNum: contractNum,
			vizaFlag: chgFlag
		}, function(results) {
			//mvno.ui.alert(results.result.oRetMsg);

			PAGE.FORM2.setItemValue("vizaFlag",results.result.vizaFlag);
			
			var vizaFlags = href("vizaFlags",results.result.vizaFlag);
			PAGE.FORM2.setItemValue("vizaFlags",vizaFlags);

		});
	}

	function smsNumChg(){
		mvno.ui.createForm("FORM17");
		PAGE.FORM17.setFormData({}); // 등록(btnReg) 인 경우에는 selectedData 는 empty(undefined)!!
		//PAGE.FORM17.enableLiveValidation(true);
		
		var smsPhone = PAGE.FORM2.getItemValue("smsPhoneNumber");

		var contractNum = PAGE.FORM2.getItemValue("contractNum");
		PAGE.FORM17.setItemValue("contractNum", contractNum);
		PAGE.FORM17.setItemValue("smsPhone", smsPhone);
        mvno.ui.popupWindowById("FORM17", "대리점수정", 340, 200, {
            onClose: function(win) {
            	mvno.ui.closeWindowById("FORM17", true);
            }
        });
	}

	function setCurrentMonthFirstDay(form, name)
	{
		var today = new Date();

		var mm = today.getMonth(); //January is 0!
		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,mm,01));
	}

	function setCurrentDate(form, name)
	{
		form.setItemValue(name, new Date());
	}

	function vacFormRefresh()
	{
		var contract_Num =PAGE.FORM2.getItemValue("contractNum");

		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM5, "virBankNm");
		mvno.cmn.ajax(
				ROOT + "/pps/hdofc/custmgmt/PpsVacDataTab.json",
				{
					contractNum :contract_Num
				},
				function(result) {
					
					PAGE.FORM5.setFormData(result.result.data)
					
					var data = result.result.data;

					PAGE.FORM5.setItemValue("vacId", data.vacId);
					PAGE.FORM5.setItemValue("vacNm", data.vacNm);

					PAGE.FORM5.setItemValue("virBankNm", data.vacBankCd);

					if(data.vacId == ""){
						PAGE.FORM5.showItem("btnVacRequest");
						PAGE.FORM5.hideItem("btnVacCollect");
						PAGE.FORM5.hideItem("btnVacChange");
					}else{
						PAGE.FORM5.hideItem("btnVacRequest");
						PAGE.FORM5.showItem("btnVacCollect");
						PAGE.FORM5.showItem("btnVacChange");
					}
					
					
					
				},
				null
				);	
	}

	function cmsFormRefresh(contractNumStr)
	{
		mvno.cmn.ajax(
				ROOT + " /pps/hdofc/custmgmt/PpsUserCmsInfo.json",
				{
					contractNum : contractNumStr
				}
				, function(result) {

					var result = result.result;
					var data = result.data;
				
					PAGE.FORM7.setItemValue("cmsBankCode",data.cmsBankCode);
					
					PAGE.FORM7.setItemValue("cmsAccount",data.cmsAccount);
					
					if(data.cmsChargeType == "1" || data.cmsChargeType == "2"){
						PAGE.FORM7.setItemValue("cmsChargeType",data.cmsChargeType);
					}else{
						PAGE.FORM7.setItemValue("cmsChargeType","0");
					}
					
					PAGE.FORM7.setItemValue("cmsTryRemains",data.cmsTryRemains);
					PAGE.FORM7.setItemValue("cmsChargeSelect",data.cmsCharge);
					PAGE.FORM7.setItemValue("cmsCharge",data.cmsCharge);
					PAGE.FORM7.setItemValue("cmsChargeDate",data.cmsChargeDate);
					PAGE.FORM7.setItemValue("cmsChargeMonth",data.cmsChargeMonth);
					PAGE.FORM7.setItemValue("cmsChargeMonthSelect",data.cmsChargeMonth);
					
					PAGE.FORM7.setItemValue("nowCharge","");

					var form2Data = PAGE.FORM2.getFormData(true);
					PAGE.FORM7.setItemValue("cmsUserNameMsk",data.cmsUserName);
					PAGE.FORM7.setItemValue("cmsUserSsnMsk",data.cmsUserSsn);	

					PAGE.FORM7.hideItem("blockCmsType0");
					PAGE.FORM7.hideItem("blockCmsType1");
					PAGE.FORM7.hideItem("blockCmsType2");
					PAGE.FORM7.hideItem("blockCmsType3");
					
					switch (data.cmsChargeType) {

					case "1":
						PAGE.FORM7.hideItem("blockCmsType0");
						PAGE.FORM7.showItem("blockCmsType1");
						PAGE.FORM7.hideItem("blockCmsType2");
						PAGE.FORM7.hideItem("blockCmsType3");
						break;
					case "2":
						PAGE.FORM7.hideItem("blockCmsType0");
						PAGE.FORM7.hideItem("blockCmsType1");
						PAGE.FORM7.showItem("blockCmsType2");
						PAGE.FORM7.hideItem("blockCmsType3");
						break;
					case "3":
						PAGE.FORM7.hideItem("blockCmsType0");
						PAGE.FORM7.hideItem("blockCmsType1");
						PAGE.FORM7.hideItem("blockCmsType2");
						PAGE.FORM7.showItem("blockCmsType3");
						break;
						
					default:
						PAGE.FORM7.showItem("blockCmsType0");
						PAGE.FORM7.hideItem("blockCmsType1");
						PAGE.FORM7.hideItem("blockCmsType2");
						PAGE.FORM7.hideItem("blockCmsType3");
						break;
					}

					
				});
		
		mvno.cmn.ajax(
			ROOT + " /pps/hdofc/custmgmt/PpsRealPayInfoInfo.json",
			{
				contractNum : contractNumStr
			}
			, function(result) {
				var result = result.result;
				var data = result.data;
				var payinfoFlagStr = "";
				if(data.total == '0'){
					payinfoFlagStr = "증빙없음";
				}else{
					payinfoFlagStr = "증빙있음";
					PAGE.FORM7.setItemValue("payinfoFlag","Y");
					PAGE.FORM7.setItemValue("regType",data.rows[0].regTypeNm);
					PAGE.FORM7.setItemValue("fileType",data.rows[0].fileType);
					PAGE.FORM7.setItemValue("fileSize",Math.ceil(data.rows[0].fileLen/1024)+" KByte");
				}

				PAGE.FORM7.setItemValue("payinfoFlagStr",payinfoFlagStr);
			});
		
	}

	function dpRcgFormRefresh(contractNumStr)
	{

		

		mvno.cmn.ajax(
				ROOT + " /pps/hdofc/custmgmt/PpsUserDpRcgInfo.json",
				{
					contractNum : contractNumStr
				}
				, function(result) {

					var result = result.result;
					var data = result.data;
					  
				    //console.log("rcgData",data);
					PAGE.FORM30.setItemValue("agntDpRcgFlag",data.agntDpRcgFlag);
					
					PAGE.FORM30.setItemValue("agntDpRcgRemains",data.agntDpRcgRemains);
					PAGE.FORM30.setItemValue("agntDpRcgCharge",data.agntDpRcgCharge);
					PAGE.FORM30.setItemValue("agntDpRcgCnt",data.agntDpRcgCnt);
					PAGE.FORM30.setItemLabel("agntDpRcgNowCnt","현재충전수 : " + data.agntDpRcgNowCnt);
										
		
					PAGE.FORM30.hideItem("blockDpRcgType0");
					PAGE.FORM30.hideItem("blockDpRcgType1");

					switch (data.agntDpRcgFlag) {
					
					case "N":
						PAGE.FORM30.showItem("blockDpRcgType0");
						PAGE.FORM30.hideItem("blockDpRcgType1");
						PAGE.FORM30.setItemValue("dpRcgRemains","");
						PAGE.FORM30.setItemValue("dpRcgCharge","");
						break;
					case "Y":
						PAGE.FORM30.hideItem("blockDpRcgType0");
						PAGE.FORM30.showItem("blockDpRcgType1");
						break;
					default:
						PAGE.FORM30.showItem("blockDpRcgType0");
						PAGE.FORM30.hideItem("blockDpRcgType1");
						PAGE.FORM30.setItemValue("dpRcgRemains","");
						PAGE.FORM30.setItemValue("dpRcgCharge","");
						break;
					

					}

					
				});
	}
	
	function setForm2Title(contractNum, subscriberNo, subStatusNm, subStatusDate, rechargeStr, warFlagStr){
		var scrNum = subscriberNo.replace(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?([0-9]{3,4})-?([0-9]{4})$/, "$1-$2-$3");
		var statusDate = subStatusDate;
		if(subStatusDate != null)
		{
			statusDate = statusDate.replace(/-/g,"").replace(/(\d{4})(\d{2})(\d{2})/g, "$1-$2-$3");
		}
		
		var title ="가입자 정보 - 계약번호:"+contractNum+" CTN:"+scrNum+"  상태:"+subStatusNm+"("+statusDate+")"+"  충전:"+rechargeStr+"&nbsp;&nbsp;&nbsp;&nbsp;"+warFlagStr;
		mvno.ui.updateSection("FORM2", "title", title);
	}

	function customerInfoData(contractNum)
	{
		mvno.ui.createForm("FORM2");
		mvno.ui.createTabbar("TABBAR1");

		PAGE.FORM2.setFormData({});
		
		mvno.cmn.ajax4lov(
				ROOT + "/pps/hdofc/custmgmt/PpsMyFeatureAddtionCodeList.json"
				, {
					contractNum : contractNum
					}
				 , PAGE.FORM2, "boardType");

		mvno.cmn.ajax(
				ROOT + "/pps/hdofc/custmgmt/selectHdofcCustMgmtInfo.json",
				{
					contractNum : contractNum
				},
				function(result) {
					var data = result.result.data;

					var retCode = data.retCode;
					var retMsg = data.retMsg;
					
					var banAddr =  "("+data.banAdrZip1+"-"+data.banAdrZip2+") "+  data.banAdrPrimaryLn+" "+data.banAdrSecondaryLn;
					var banAddrMsk =  "("+data.banAdrZip1+"-"+data.banAdrZip2+") "+  data.banAdrPrimaryLn+" "+data.banAdrSecondaryLnMsk;
					
					if(retCode == "000")
					{
						setForm2Title(contractNum, data.subscriberNoMsk, data.subStatusNm, data.subStatusDate, data.rechargeStr, data.warFlagStr);

						PAGE.FORM2.setItemValue("contractNum",contractNum);

						PAGE.FORM2.setItemValue("subscriberNo",data.subscriberNo);
						PAGE.FORM2.setItemValue("subscriberNoMsk",data.subscriberNoMsk);						
						PAGE.FORM2.setItemValue("customerLinkNm",data.customerLinkName);			
						
						//PAGE.FORM2.setFormData(data);
						PAGE.FORM2.setItemValue("customerId",data.customerId);
						PAGE.FORM2.setItemValue("customerLinkName",data.customerLinkName);
						PAGE.FORM2.setItemValue("customerLinkNameMsk",data.customerLinkNameMsk);
						PAGE.FORM2.setItemValue("banAddr",banAddr);
						PAGE.FORM2.setItemValue("banAddrMsk",banAddrMsk);

						var oversInfos = data.overVoice + " 원(초)/ "+data.overSms+" 원(건)/ "+data.overData+" 원(MB)";
						PAGE.FORM2.setItemValue("oversInfos",oversInfos);
						//PAGE.FORM2.setItemValue("overVoice",data.overVoice);
						//PAGE.FORM2.setItemValue("overSms",data.overSms);
						//PAGE.FORM2.setItemValue("overData",data.overData);

						var serviceNameBasic = data.serviceName + " ( " +data.serviceBasic + " 원/일)";
						
						PAGE.FORM2.setItemValue("serviceNameBasic",serviceNameBasic);
						//PAGE.FORM2.setItemValue("serviceName",data.serviceName);
						//PAGE.FORM2.setItemValue("serviceBasic",data.serviceBasic);

						PAGE.FORM2.setItemValue("basicExpire",data.basicExpire);

						var dataRegNmModeNm = data.dataRegNm + "/" + data.dataModeNm;
						PAGE.FORM2.setItemValue("dataRegNmModeNm",dataRegNmModeNm);
						//PAGE.FORM2.setItemValue("dataRegNm",data.dataRegNm);
						//PAGE.FORM2.setItemValue("dataModeNm",data.dataModeNm);
						var dataQuotaRemainsExpire = data.dataQuotaRemains + " byte/ " + data.dataExpire;
						PAGE.FORM2.setItemValue("dataQuotaRemainsExpire",dataQuotaRemainsExpire);
						//PAGE.FORM2.setItemValue("dataQuotaRemains",data.dataQuotaRemains);
						//PAGE.FORM2.setItemValue("dataExpire",data.dataExpire);
						
						var rcgCntSumAmount = data.rcgCnt + "회/" + data.sumAmount + "원";
						PAGE.FORM2.setItemValue("rcgCntSumAmount",rcgCntSumAmount);
						//PAGE.FORM2.setItemValue("rcgCnt",data.rcgCnt);
						//PAGE.FORM2.setItemValue("sumAmount",data.sumAmount);
						
						PAGE.FORM2.setItemValue("subLinkName",data.subLinkName);
						PAGE.FORM2.setItemValue("subLinkNameMsk",data.subLinkNameMsk);

						if(data.userSsn != null && data.userSsn !='' && data.userSsn.length > 6)
						{
							var subStrUserSsn = data.userSsn.substring(0,6);
							PAGE.FORM2.setItemValue("userSsn",subStrUserSsn);
						}
						else
						{
							PAGE.FORM2.setItemValue("userSsn",data.userSsn);	
						}
						PAGE.FORM2.setItemValue("banAddr2", banAddr);
						PAGE.FORM2.setItemValue("banAddrMsk2", banAddrMsk);

						var modelInfos = data.modelName + " / " + data.iccId + " / " + data.imei + " / " +data.intmSrlNo;
						var modelInfosMsk = data.modelName + " / " + data.iccIdMsk + " / " + data.imei + " / " +data.intmSrlNoMsk;
						PAGE.FORM2.setItemValue("modelInfos",modelInfos);
						PAGE.FORM2.setItemValue("modelInfosMsk",modelInfosMsk);
						//PAGE.FORM2.setItemValue("modelName",data.modelName);
						//PAGE.FORM2.setItemValue("iccId",data.iccId);
						//PAGE.FORM2.setItemValue("imei",data.imei);
						//PAGE.FORM2.setItemValue("intmSrlNo",data.intmSrlNo);
						
						var wwwRegFlagNmDt = data.wwwRegFlagNm + " / " + data.wwwRegDt;
						PAGE.FORM2.setItemValue("wwwRegFlagNmDt",wwwRegFlagNmDt);
						//PAGE.FORM2.setItemValue("wwwRegFlagNm",data.wwwRegFlagNm);
						//PAGE.FORM2.setItemValue("wwwRegDt",data.wwwRegDt);

						PAGE.FORM2.setItemValue("srlIfId",data.srlIfId);
						var adNationNmLangCodeNm = data.adNationNm + " / " + data.langCodeNm;
						PAGE.FORM2.setItemValue("adNationNmLangCodeNm",adNationNmLangCodeNm);
						//PAGE.FORM2.setItemValue("adNationNm",data.adNationNm);
						//PAGE.FORM2.setItemValue("langCodeNm",data.langCodeNm);
						PAGE.FORM2.setItemValue("adNation",data.adNation);
						PAGE.FORM2.setItemValue("langCode",data.langCode);

						PAGE.FORM2.setItemValue("customerType",data.customerTypeNm + " / " + data.mnpIndCdNm);

						if(data.customerSsn != null && data.customerSsn !='' && data.customerSsn.length > 6 )
						{
							var subStrUserSsn = data.customerSsn.substring(0,6);
							var ssnInfos = subStrUserSsn + " / " + data.taxId + " / "+ data.driverLicnsNo;
							var ssnInfosMsk = subStrUserSsn + " / " + data.taxId + " / "+ data.driverLicnsNoMsk;
							PAGE.FORM2.setItemValue("ssnInfos",ssnInfos);
							PAGE.FORM2.setItemValue("ssnInfosMsk",ssnInfosMsk);
							
						}
						else
						{
							var ssnInfos = data.customerSsn + " / " + data.taxId + " / "+ data.driverLicnsNo;
							var ssnInfosMsk = data.customerSsn + " / " + data.taxId + " / "+ data.driverLicnsNoMsk;
							PAGE.FORM2.setItemValue("ssnInfos",ssnInfos);						
							PAGE.FORM2.setItemValue("ssnInfosMsk",ssnInfosMsk);						
						}
						
						//PAGE.FORM2.setItemValue("ssnInfos",ssnInfos);
						PAGE.FORM2.setItemValue("customerSsn",data.customerSsn);
						PAGE.FORM2.setItemValue("customerSsnMsk",data.customerSsnMsk);
						//PAGE.FORM2.setItemValue("taxId",data.taxId);
						//PAGE.FORM2.setItemValue("driverLicnsNo",data.driverLicnsNo);
						PAGE.FORM2.setItemValue("basicRemains",data.basicRemains + " 원");
						//PAGE.FORM2.setItemValue("point",data.point);
						
						PAGE.FORM2.setItemValue("lastBasicChgDt",data.lastBasicChgDt);
						//PAGE.FORM2.setItemLabel("serviceDataAmount",data.serviceDataAmount);
						PAGE.FORM2.setItemValue("lastDataChgDt",data.lastDataChgDt);

						var usLcInfos = data.usLcVoice + " 분/ "+ data.usLcSms + " 건/ "+ data.usLcData + " KByte";
						PAGE.FORM2.setItemValue("usLcInfos",usLcInfos);
						//PAGE.FORM2.setItemValue("usLcVoice",data.usLcVoice);
						//PAGE.FORM2.setItemValue("usLcSms",data.usLcSms);
						//PAGE.FORM2.setItemValue("usLcData",data.usLcData);
						
						//var rentalInfos = data.rentalStatusNm + " / "+ data.rentalDt;
						//PAGE.FORM2.setItemValue("rentalInfos",rentalInfos);
						//PAGE.FORM2.setItemValue("rentalStatusNm",data.rentalStatusNm);
						//PAGE.FORM2.setItemValue("rentalDt",data.rentalDt);
						
						PAGE.FORM2.setItemValue("adSsn",data.adSsn);

						if(data.adSsn != null && data.adSsn !='' && data.adSsn.length > 6)
						{
							var subStrAdSsn = data.userSsn.substring(0,6);
							PAGE.FORM2.setItemValue("adSsn",subStrAdSsn);
						}
						else
						{
							PAGE.FORM2.setItemValue("adSsn",data.adSsn);	
						}
						
						PAGE.FORM2.setItemValue("lastKtSynDt",data.lastKtSynDt);
				
						PAGE.FORM2.setItemValue("smsPhoneNumber",data.smsPhone);

						PAGE.FORM2.setItemValue("smsFlag",data.smsFlag);
						PAGE.FORM2.setItemValue("rechargeFlag",data.rechargeFlag);
						PAGE.FORM2.setItemValue("vizaFlag",data.vizaFlag);

						var rechargeFlagStr = href("rechargeFlags" ,data.rechargeFlag);
						PAGE.FORM2.setItemValue("rechargeFlags",rechargeFlagStr);
												
						var smsFlagStr = href("smsFlags",data.smsFlag);
						PAGE.FORM2.setItemValue("smsFlags",smsFlagStr);

						var smsPhoneLink = href("smsPhone",data.smsPhone);
						//PAGE.FORM2.setItemValue("smsPhone","");
						PAGE.FORM2.setItemValue("smsPhone",smsPhoneLink);

						var vizaFlagStr = href("vizaFlags",data.vizaFlag);
						PAGE.FORM2.setItemValue("vizaFlags",vizaFlagStr);

						//var agentNm = href("agentNm",data.agentNm);
						PAGE.FORM2.setItemValue("agentNmOrg",data.agentNm);
						
						agentNm = data.agentNm + " / " + data.agentSaleNm;
						PAGE.FORM2.setItemValue("agentNm",agentNm);
						//PAGE.FORM2.setItemLabel("agentNm",data.agentNm);
						
					
						
						PAGE.FORM2.setItemValue("agentId",data.agentId);
						
						PAGE.FORM2.setItemValue("paperImage",data.paperImg+ " 개");

						PAGE.FORM2.setItemValue("stayExpirDtStr",data.stayExpirDt);
						PAGE.FORM2.setItemValue("custIdntNoIndCdNm",data.custIdntNoIndCdNm);
						PAGE.FORM2.setItemValue("topupRcgAmt",data.topupRcgAmt);
						
						//체규기간 수정가능기간여부 확인
						mvno.cmn.ajax(
							ROOT + "/pps/hdofc/custmgmt/PpsCusStayExpirUpdatePop.json",
							{
								opCode : "CHK",
								contractNum : contractNum
							},
							function(result) {
								var result = result.result;
								var code = result.code;
								var msg = result.oRetMsg;
								if(code == "OK" && result.oRetCd == '0000') {
									stayExpirFlag = true;
								}
							},
								
							null
						);
							
					}else{
							mvno.ui.notify(retMsg);
							return ;
					}
				},
				null
				);

	}

	function getDiaryDetailFromDiaryId(diaryId)
	{
		
		mvno.cmn.ajax(
				ROOT + "/pps/hdofc/custmgmt/getPpsCustomerDiaryTabDetail.json",
				{
					diaryId : diaryId 
					
				},
				function(result) {
					var result = result.result;
					var data = result.data;
					//console.log("D",data);
					var code = result.code;	
					
					if(code=="OK")
					{
						if(data.diaryId != null && data.diaryId != '')
						{
							mvno.ui.createForm("FORM10_2");
							PAGE.FORM10_2.setFormData(data);

							PAGE.FORM10_2.disableItem("reqUserGubun");
							PAGE.FORM10_2.disableItem("reqUserName");
							PAGE.FORM10_2.disableItem("reqUserNameCheck");
							PAGE.FORM10_2.disableItem("reqType");
							PAGE.FORM10_2.disableItem("reqBody"); 

							PAGE.FORM10_2.setValidation("resBody", "NotEmpty");

							var dataReqUserGubun = data.reqUserGubun;
							if(dataReqUserGubun == "1")
							{
								PAGE.FORM10_2.setItemValue("reqUserNameCheck",true);	
							}
							else
							{
								PAGE.FORM10_2.setItemValue("reqUserNameCheck",false);	
							}
							
							var data = PAGE.FORM2.getFormData(true);

							

							var title = "상담등록 - 고객명 : "+data.subLinkNameMsk+" CTN : "+data.subscriberNoMsk;
							mvno.ui.updateSection("FORM10_2", "title", title);

							PAGE.FORM10_2.setItemValue("contractNumber", data.contractNum);
											
				            mvno.ui.popupWindowById("FORM10_2", "고객상담등록화면", 580, 460, {
				                onClose: function(win) {
				                	if (! PAGE.FORM10_2.isChanged()) return true;
				                	mvno.ui.confirm("CCMN0005", function(){
				                		win.closeForce();
				                	});
				                }
				            });
			            }
					}
					else
					{
						mvno.ui.notify(retMsg);
					}
				},
				null
				);
	}

 	function userPaperImageView(contractNum,pNum,pSize,pIdx){
		
		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		for (var i=startSize; i<endSize; i++) {
			var imgSeqStr = PAGE.GRID10.cells(PAGE.GRID10.getRowId(i), PAGE.GRID10.getColIndexById("imgSeq")).getValue();
			if(pNum == imgSeqStr){
				rowId = i;
				break;
			} 
		 }
		//var rowId = linenum.substring(0,linenum.length-1);
		var pImageStr = "";
		var grid10 = PAGE.GRID10;
		var fileName="";
		var filePath="";
		
		var filePath="";
		var encodeYn="";
		filePath = grid10.cells(grid10.getRowId(rowId),grid10.getColIndexById("imgPath" ) ).getValue();
		encodeYn = grid10.cells(grid10.getRowId(rowId),grid10.getColIndexById("encFlag" ) ).getValue();
		
		mvno.cmn.ajax(
		ROOT + "/pps/filemgmt/ppsCheckFile.json",
		{
			filePath : filePath
		},
		function(results) {

			var width = 750;
			var height = 650;
			var top  = 10;
			var left = 10;
			
			var result = results.result;
            var retCode = result.retCode;
            var msg = result.retMsg;

			if(retCode == "0000"){
			
				//확장자 체크	
				var thumbext = filePath.slice(filePath.indexOf(".") + 1).toLowerCase(); //파일 확장자를 잘라내고, 비교를 위해 소문자로 만듭니다.
				
				if(thumbext == "jpg" || thumbext == "jpeg" ||  thumbext == "gif" ||  thumbext == "tif" ||  thumbext == "tiff"){ //확장자를 확인합니다.
/* 					
 					document.viewForm.FILE_PATH.value = filePath;
					document.viewForm.ENCODE_YN.value = encodeYn;
					
					var popupTitle = "이미지검색";

					var popupUrl = "https://scn.ktmmobile.com/webscan/ui/callImageViewer.jsp"; // 실서버 
					//var popupUrl = "http://scndev.ktmmobile.com/webscan/ui/callImageViewer.jsp"; // 개발서버  
				
					var status = alignCenterStatus();
					status += ", scrollbars=no";
					
					window.opener=null;

					var browserPopup = window.open("", popupTitle, status);
				
					document.viewForm.target = popupTitle;
					document.viewForm.action = popupUrl;
					document.viewForm.method = "post";
					document.viewForm.submit(); */

					document.viewForm.FILE_PATH.value = filePath;
					document.viewForm.ENCODE_YN.value = encodeYn;
					document.viewForm.AGENT_VERSION.value = agentVersion;
					document.viewForm.SERVER_URL.value = serverUrl;
					document.viewForm.USE_BM.value = blackMakingYn;	
					document.viewForm.USE_DEL_BM.value = blackMakingFlag;					

 					var data = $("#viewForm").serialize();
					var url = scanSearchUrl + "?" + encodeURIComponent(data);
								
					// 타임아웃 설정 - 10초이내 응답없으면 서비스 실행상태가 아님.
					var timeOutTimer = window.setTimeout(function (){
						mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
							window.open(scanDownloadUrl, "", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);         // 설치페이지로 이동
						});
					}, 10000);
					 
					$.ajax({     
						type : "GET", 
						url : url,     
						crossDomain : true,
						dataType : 'jsonp',
						success : function(args){
							window.clearTimeout(timeOutTimer);
							if(args.RESULT == "SUCCESS") {
								console.log("SUCCESS");
							} else if(args.RESULT == "ERROR_TYPE2") {
								mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
								window.open(scanDownloadUrl, "", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);         // 설치페이지로 이동
							} else {
								mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
							}
						}
					});
					
					
				}else{
					
					mvno.cmn.download('/pps/filemgmt/downFile.json?path=' + filePath);
					
				}
			
			
			}else{
				
				alert(msg);
			}
			
		},
		null
		);	
		

	}
	
	
function goDeleteFileData(seq,pSize,pIdx){
	 
	 var startSize = ((pIdx-1)*pSize);
	var endSize = pIdx*pSize;
	var rowId = "";
	
	for (var i=startSize; i<endSize; i++) {
		var imgSeqStr = PAGE.GRID10.cells(PAGE.GRID10.getRowId(i), PAGE.GRID10.getColIndexById("imgSeq")).getValue();
		
		if(imgSeqStr == seq){
			rowId = i;
			break;
		}
	 }
	
	var grid10 = PAGE.GRID10;
		
   var filePath="";
	var contractNum="";
	var opCode ="DEL";
	var status="D";
	var msg = "파일을 삭제하시겠습니까?";
	
	filePath = grid10.cells(grid10.getRowId(rowId),grid10.getColIndexById("imgPath" ) ).getValue();
	contractNum = grid10.cells(grid10.getRowId(rowId),grid10.getColIndexById("contractNum" ) ).getValue();
	
	 var searchData =  PAGE.FORM2.getFormData(true);
	
	mvno.cmn.ajax4confirm(msg, ROOT + "/pps/filemgmt/ppsDeleteFile.json", {
		opCode: opCode,
		imgSeq : seq,
		contractNum : contractNum,
		status: status,
		imgPath: filePath,
		contractNum: contractNum
	}, function(results) {
		var result = results.result;
		var retCode = result.oRetCode;
		var retMsg = result.oRetMsg;
		
		mvno.ui.notify(retMsg);
		if(retCode == "0000"){
			grid10.clearAll();
			var imgCnt = parseInt(PAGE.FORM2.getItemValue("paperImage").replace("개", "") - 1);
			PAGE.FORM2.setItemValue("paperImage", imgCnt + "개");
			PAGE.GRID10.list(ROOT + "/pps/hdofc/custmgmt/imgFileInfoMgmtTabList.json", searchData);
			
			
		}
		
	});
	
	
}

	dhtmlxValidation.isMin4=function(a){
		return a.length>=4;
	}
	dhtmlxValidation.isMax20=function(a){
		return a.length<=20;
	}

<%@ include file="../../hdofcCustDetail.formitems" %>

var DHX = {

	FORM2 : {
		title : "가입자 정보"
		,items: _FORMITEMS_['FORM2']
		,buttons : {
			right : {
				btnSmsWrite : { label : "문자작성" }, 
				btnDiary : { label : "상담등록" }
			}
		}
		,onButtonClick : function(btnId) {

			var data = PAGE.FORM2.getFormData(true);
	
			switch (btnId) {
				case "btnSmsWrite":	//문자작성
					mvno.ui.createForm("FORM11");
					PAGE.FORM11.setFormData({});
					PAGE.FORM11.setItemValue("event","BONSA_ADMIN_MSG");
					PAGE.FORM11.setItemValue("contractNum",data.contractNum);
	
					mvno.ui.popupWindowById("FORM11", "문자작성", 400, 250, {
		                onClose: function(win) {
		                	if (! PAGE.FORM2.isChanged()) return true;
		                	mvno.ui.confirm("CCMN0005", function(){
		                		win.closeForce();
		                	});
		                }
	                });		
	                
					break;
				case "btnDiary":	//상담등록
					mvno.ui.createForm("FORM10");
					PAGE.FORM10.setFormData({}); 

					var title = "상담등록 - 고객명 : "+data.subLinkNameMsk+" CTN : "+data.subscriberNoMsk;
					mvno.ui.updateSection("FORM10", "title", title);

					PAGE.FORM10.setItemValue("contractNumber", data.contractNum);
					PAGE.FORM10.setItemValue("contractNum", data.contractNum);
					PAGE.FORM10.setItemValue("subLinkName", data.subLinkName);
					PAGE.FORM10.setItemValue("agentId", data.agentId);

					PAGE.FORM10.disableItem("resBody");
										
		            mvno.ui.popupWindowById("FORM10", "고객상담등록화면", 580, 460, {
		                onClose: function(win) {
		                	if (! PAGE.FORM10.isChanged()) return true;
		                	mvno.ui.confirm("CCMN0005", function(){
		                		win.closeForce();
		                	});
		                }
	                });			
	                break;
				case "btnSmsRemains": //잔액문자전송
					var contractNum = PAGE.FORM2.getItemValue("contractNum");
					mvno.cmn.ajax4confirm("잔액문자전송 하시겠습니까??(최신잔액은 잔액갱신후 가능합니다.)", ROOT + "/pps/hdofc/custmgmt/PpsCusRemainsSms.json", {
						contractNum: contractNum
					}, function(results) {
						mvno.ui.alert(results.result.oRetMsg);
	
					});
					break;							
				case "btnRemainsUp": //잔액갱신
					var contractNum = PAGE.FORM2.getItemValue("contractNum");
					var subscriberNo = PAGE.FORM2.getItemValue("subscriberNo");
					mvno.cmn.ajax(ROOT + "/pps/hdofc/custmgmt/PpsCusDtlRemains.json",{
						contractNum: contractNum,
						subscriberNo: subscriberNo
					},function(results) {
						mvno.ui.alert(results.result.oResCodeNm);
	                             //mvno.ui.notify(result.oResCodeNm);
	                             //PAGE.FORM2.refresh();
								var currentRemains = results.result.currentRemains;
								PAGE.FORM2.setItemValue("basicRemains",currentRemains + " 원");

								//customerInfoData(contractNum);
								
	                         });
					
					break;
				case "paperUpdate": //서류수정
  			     
				
  			       var fileLimitCnt = 1;
  			       
  			       mvno.ui.createForm("FORM22");
                   PAGE.FORM22.clear();
                   
					var data = PAGE.FORM2.getFormData(true);
                   
                   PAGE.FORM22.setFormData(data);
                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM22, "imgGubun");
                   var contractNum = PAGE.FORM2.getItemValue("contractNum");
                   PAGE.FORM22.setItemValue("contractNum", contractNum);
                   var uploader = PAGE.FORM22.getUploader("file_upload1");
                   
                   if(paper_old_url == ""){
                	   paper_old_url = uploader._url;
                   }
                   
                   var url = paper_old_url + "?contractNum=" + contractNum;
	               uploader._url = url;   
	               uploader._swf_upolad_url = url;
                   
                   var imgGubun = PAGE.FORM22.getItemValue("imgGubun");
                   
                   uploader.revive();
                   
                   PAGE.FORM22.attachEvent("onBeforeFileAdd",function(realName, size, file){
                	      
                	   	  var pathpoint = realName.lastIndexOf('.'); 
	                      var filepoint = realName.substring(pathpoint+1,realName.length); 
	                      var filetype = filepoint.toLowerCase(); 
	                      
	                      uploader._url = paper_old_url; // url 초기화 contract_num이 계속붙음.
	                      
	                      if (filetype == 'gif'|| filetype == 'jpg' || filetype == 'jpeg'
	                      	|| filetype == 'tiff' || filetype == 'tif'){

	                      }
	                      else
	                      {
								alert('지원하지 않는 파일 형식 입니다.');
	                          return false;
	                      }
	                      
	                      if(parseInt(size) > 209715200){
	                    	  mvno.ui.alert("이미지는 최대 200M까지 업로드가능합니다.");
	                    	  return false;
	                      }
	                      
	                      return true; 
                   });
                   
                   PAGE.FORM22.attachEvent("onUploadFile",function(realName, serverName){
						console.log("PAGE.FORM22.attachEvent-->onUploadFile");
						
						if(realName == null || realName == '')
                    	{
							alert('필수입력 입니다.');
                        	return false;
                        }
						
						return true; 
	                      
					});
					PAGE.FORM22.attachEvent("onUploadComplete",function(realName, serverName,result){
					    var form22 = PAGE.FORM22;
						var data3 = form22.getFormData(true);
						
						
						
						
				   });

                   mvno.ui.popupWindowById("FORM22", "이미지등록", 600, 220, {
                	    onClose2: function(win) {
                	        uploader.reset();
                	      
                	    }
                   });	
                  
	                break;
				case"btnPortPolioView":
					alert('서식지');
					break;
				case "arsUpdate": // 국적/SMS언어
					mvno.ui.createForm("FORM16");
	
					var contract_Num =PAGE.FORM2.getItemValue("contractNum");
					var form2Data = PAGE.FORM2.getFormData(true);
					
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsCustomerNationInfo.json", null, PAGE.FORM16, "adNation");
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsCustomerLanguageInfo.json", null, PAGE.FORM16, "langCode");
	
					mvno.ui.popupWindowById("FORM16", "국적/SMS 언어수정", 400, 200, {
		                onClose: function(win) {
		                	if (! PAGE.FORM16.isChanged()) return true;
		                	mvno.ui.confirm("CCMN0005", function(){
		                		win.closeForce();
		                	});
		                }
	                });

					PAGE.FORM16.setItemValue("contractNum", contract_Num);
					PAGE.FORM16.setItemValue("adNation", form2Data.adNation);
					PAGE.FORM16.setItemValue("langCode", form2Data.langCode);
	
					break;
					
				case "stayExpirDtUpdate": // 체류기간 수정
					if(!stayExpirFlag){
						mvno.ui.notify("해당고객은 체류기간 설정이 불가능합니다.");
						return;
					}
				
					mvno.ui.createForm("FORM25");
					var contract_Num =PAGE.FORM2.getItemValue("contractNum");
					var form2Data = PAGE.FORM2.getFormData(true);
					
					mvno.ui.popupWindowById("FORM25", "체류기간 수정", 400, 200, {
		                onClose: function(win) {
		                	if (! PAGE.FORM25.isChanged()) return true;
		                	mvno.ui.confirm("CCMN0005", function(){
		                		win.closeForce();
		                	});
		                }
	                });

					PAGE.FORM25.setItemValue("contractNum", contract_Num);
					PAGE.FORM25.setItemValue("custIdntNoIndCdNm", form2Data.custIdntNoIndCdNm);
					
					if(form2Data.stayExpirDtStr != ''){
						var stayExpirDtStr = form2Data.stayExpirDtStr.replace(/-/g,"");
						PAGE.FORM25.setItemValue("stayExpirDt", stayExpirDtStr);
					}
					
					break;
				
			}
		}
	}// FORM end..
	,TABBAR1: {

		css : { height : "300px" }
		,tabs: [
		       { id: "a1", text: "선불충전" },
			    { id: "a2", text: "가상계좌" },
			    { id: "a3", text: "실시간이체" },
			    { id: "a4", text: "충전내역" },
			    { id: "a5", text: "통화내역" },
			    { id: "a6", text: "일사용내역" },
			    { id: "a7", text: "문자내역" },
			    { id: "a8", text: "상담내역" },
			    { id: "a9", text: "예치금충전" },
			    { id: "a10", text: "서식지관리" }
		]	
	    ,onSelect: function (id, lastId, isFirst) {

						
			var contract_Num =PAGE.FORM2.getItemValue("contractNum");
			var subscriber_Num =PAGE.FORM2.getItemValue("subscriberNo");
			var nowYear = new Date().format('yyyy');
			

			switch (id) {

				case "a1":
					mvno.ui.createForm("FORM4");
					//mvno.ui.createForm("FORM14");
					//if (rowData) PAGE.FORM4.setFormData(rowData);
					
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0060"}, PAGE.FORM4, "freePrice");
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0060"}, PAGE.FORM4, "cashPrice");
					
		    		break;

				case "a2":
					
		    		mvno.ui.createForm("FORM5");

		    		PAGE.FORM5.setItemValue("subscriberNo", subscriber_Num);
		    		
		    		
		    		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM5, "virBankNm");
		    		mvno.cmn.ajax(
							ROOT + "/pps/hdofc/custmgmt/PpsVacDataTab.json",
							{
								contractNum :contract_Num
							},
							function(result) {
								
								PAGE.FORM5.setFormData(result.result.data)
								
								var data = result.result.data;

								PAGE.FORM5.setItemValue("vacId", data.vacId);
								PAGE.FORM5.setItemValue("vacNm", data.vacNm);

								PAGE.FORM5.setItemValue("virBankNm", data.vacBankCd);
								
								if(data.vacId == ""){
									PAGE.FORM5.showItem("btnVacRequest");
									PAGE.FORM5.hideItem("btnVacCollect");
									PAGE.FORM5.hideItem("btnVacChange");
								}else{
									PAGE.FORM5.hideItem("btnVacRequest");
									PAGE.FORM5.showItem("btnVacCollect");
									PAGE.FORM5.showItem("btnVacChange");
								}
							},
							null
							);
		    		
		    		break;

				case "a3":

					mvno.ui.createForm("FORM7");
					mvno.ui.createGrid("GRID4");

					var form2Data = PAGE.FORM2.getFormData(true);

					var contractNum = PAGE.FORM2.getItemValue("contractNum");
					var subscriberNo =PAGE.FORM2.getItemValue("subscriberNo");

					PAGE.FORM7.setItemValue("subscriberNo",subscriberNo);
					PAGE.FORM7.setItemValue("contractNum",contractNum);

					PAGE.FORM7.hideItem("blockCmsType0");
					PAGE.FORM7.hideItem("blockCmsType1");
					PAGE.FORM7.hideItem("blockCmsType2");
					PAGE.FORM7.hideItem("blockCmsType3");

		    		PAGE.GRID4.list(
							ROOT + " /pps/hdofc/custmgmt/PpsRcgRealCmsTabList.json",
							{
								contractNum : contract_Num
							}
					);

		    		var selectChargeDtArr = [];
		    		selectChargeDtArr.push({text:"- 전체 -", value:""});
		    		
		    		var rcgAmtData = [];
		    		rcgAmtData.push({text:"- 전체 -", value:""});
		    		
		    		if(PAGE.FORM2.getItemValue("topupRcgAmt") == "" || PAGE.FORM2.getItemValue("topupRcgAmt") == null || PAGE.FORM2.getItemValue("topupRcgAmt") <= 0){
		    			//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0059"}, PAGE.FORM7, "cmsChargeSelect");
		    			//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0059"}, PAGE.FORM7, "cmsChargeMonthSelect");
		    		}else{
		    			rcgAmtData.push({text:PAGE.FORM2.getItemValue("topupRcgAmt"), value:PAGE.FORM2.getItemValue("topupRcgAmt")});
						PAGE.FORM7.reloadOptions("cmsChargeSelect", rcgAmtData);
						PAGE.FORM7.reloadOptions("cmsChargeMonthSelect", rcgAmtData);
						
						selectChargeDtArr.push({text:"만료일 3일전", value:-3});
		    		}
		    		
		    		for(var i=1; i<32; i++){
						selectChargeDtArr.push({text:i, value:(i < 10)?"0"+i:i});
					}
		    		
		    		PAGE.FORM7.reloadOptions("cmsChargeDate", selectChargeDtArr);
		    		
		    		rcgAmtData.push({text:"10,000", value:"10000"});
		    		rcgAmtData.push({text:"20,000", value:"20000"});
		    		rcgAmtData.push({text:"30,000", value:"30000"});
		    		rcgAmtData.push({text:"40,000", value:"40000"});
		    		rcgAmtData.push({text:"50,000", value:"50000"});
		    		
		    		PAGE.FORM7.reloadOptions("cmsChargeSelect", rcgAmtData);
		    		PAGE.FORM7.reloadOptions("cmsChargeMonthSelect", rcgAmtData);

		    		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0058"}, PAGE.FORM7, "cmsTryRemains");

					//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0053"}, PAGE.FORM7, "cmsChargeDate");

					mvno.cmn.ajax4lov( ROOT + "/pps/hdofc/custmgmt/PpsRcgCodeList.json", null, PAGE.FORM7, "cmsBankCode");
					
					cmsFormRefresh(contract_Num);
		    		
		    		break;

				case "a4":
					mvno.ui.createGrid("GRID5");

					PAGE.GRID5.list(
							ROOT + " /pps/hdofc/custmgmt/PpsRcgTabList.json",
							{
								contractNum : contract_Num
							}
					);

					
					
		    		break;

				case "a5":
					mvno.ui.createForm("FORM8");
					mvno.ui.createGrid("GRID6");

					//setCurrentMonthFirstDay(PAGE.FORM8, "startDt");
					setCurrentDate(PAGE.FORM8, "startDt");
					setCurrentDate(PAGE.FORM8, "endDt");

					var data = PAGE.FORM8.getFormData(true);
					var tempContractNum = PAGE.FORM2.getItemValue("contractNum");
					data.contractNum = tempContractNum;
					
					PAGE.GRID6.list(
							ROOT + " /pps/hdofc/custmgmt/PpsCdrPpTabList.json",data	);
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0050"}, PAGE.FORM8, "searchCallGubun");
					
		    		break;
		    		
				case "a6":
					mvno.ui.createForm("FORM9");
					mvno.ui.createGrid("GRID7");

					//setCurrentMonthFirstDay(PAGE.FORM9, "startDt");
					setCurrentDate(PAGE.FORM9, "startDt");
					setCurrentDate(PAGE.FORM9, "endDt");

					var data = PAGE.FORM9.getFormData(true);
					var tempContractNum = PAGE.FORM2.getItemValue("contractNum");
					data.contractNum = tempContractNum;

					PAGE.GRID7.list(
							ROOT + " /pps/hdofc/custmgmt/PpsCdrPktTabList.json", data );

					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0051"}, PAGE.FORM9, "callGubun");
					
		    		break;

				case "a7":

					mvno.ui.createGrid("GRID8");

					PAGE.GRID8.list(
							ROOT + " /pps/hdofc/custmgmt/PpsSmsTabList.json",
							{
								contractNum : contract_Num
							}
					);
					
		    		break;

				case "a8":
					mvno.ui.createGrid("GRID9");

					PAGE.GRID9.list(
							ROOT + " /pps/hdofc/custmgmt/getPpsCustomerDiaryTabList.json",
							{
								contractNum : contract_Num
							}
					);
					break;
				case "a9":


					var form2Data = PAGE.FORM2.getFormData(true);

					var contractNum = PAGE.FORM2.getItemValue("contractNum");
					var agentNmOrgStr = PAGE.FORM2.getItemValue("agentNmOrg");
					var agentNmStr = PAGE.FORM2.getItemValue("agentNm");

					if(agentNmOrgStr != null && agentNmOrgStr != '' && agentNmOrgStr != 'null')
					{
						mvno.ui.createForm("FORM30");
						
						PAGE.FORM30.setItemValue("contractNum",contractNum);
						PAGE.FORM30.setItemValue("agentNm",agentNmStr);

						//PAGE.FORM30.setItemValue("dpRcgFlag",form2Data.dpRcgFlag);
						//PAGE.FORM30.setItemValue("dpRcgRemains",form2Data.dpRcgRemains);
						//PAGE.FORM30.setItemValue("dpRcgCharge",form2Data.dpRcgCharge);
						
						
						
						PAGE.FORM30.hideItem("blockDpRcgType0");
						PAGE.FORM30.hideItem("blockDpRcgType1");
						PAGE.FORM30.setItemValue("dpRcgRemains","");
						PAGE.FORM30.setItemValue("dpRcgCharge","");

						dpRcgFormRefresh(contract_Num);
					}
					else
					{
						alert('대리점 등록후 이용 할수 있습니다.');
					}

					

					
					break;
					
				case "a10":
					mvno.ui.createGrid("GRID10");
					
					
					
					PAGE.GRID10.list(
							ROOT + "/pps/hdofc/custmgmt/imgFileInfoMgmtTabList.json",
							{
								contractNum : contract_Num
							}
					);
					
					PAGE.GRID10.setColumnHidden(PAGE.GRID10.getColIndexById("contractNum" ) ,true);
					PAGE.GRID10.setColumnHidden(PAGE.GRID10.getColIndexById("imgSeq" ) ,true);
					PAGE.GRID10.setColumnHidden(PAGE.GRID10.getColIndexById("imgPath" ) ,true);
					PAGE.GRID10.setColumnHidden(PAGE.GRID10.getColIndexById("encFlag" ) ,true);
					
			    break;
			    
				
			}

	    }

	}// FORM end..
	,FORM30: {
		items: _FORMITEMS_['FORM30']
		,onButtonClick : function(btnId) {
			
			var form = this;

			var contractNum = PAGE.FORM2.getItemValue("contractNum");
			
			switch (btnId) {

				case "btnDpRcgDisableApply":
					//alert('미출금설정 버튼');
					
					PAGE.FORM30.setItemValue("agentDpRcgRemains","");
					PAGE.FORM30.setItemValue("agentDpRcgCharge","");
					PAGE.FORM30.setItemValue("agentDpRcgCnt","");
					var data = this.getFormData(true);
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsCustomerDpRcgSetting.json",
		    				PAGE.FORM30
							, function(result) {

								//console.log(result);
								var result = result.result;
								var retCode = result.retCode;
								var msg = result.retMsg;

								if(retCode == "000") {

                            		  mvno.ui.notify(msg);

                            		  dpRcgFormRefresh(contractNum);
                            		  
                                  }
                                  else {
                                	  mvno.ui.notify(msg);
                                     // mvno.ui.notify("ECMN0001");
                                  }
							});						
					break;
				case "btnDpRcgApply":
					//alert('잔액부족시 출금 버튼');
					var data = this.getFormData(true);
					if(!PAGE.FORM30.validate()) return;
					var formData =  PAGE.FORM30.getFormData(true);
					//console.log("formData",formData);
					if( formData.agntDpRcgRemains == null || formData.agntDpRcgRemains == '' )
					{
						form.pushError("agntDpRcgRemains", "임계잔액", "은 필수입력입니다..");
						mvno.ui.notify("임계잔액은 필수입력입니다.");
						return;
						 
					}

					if( formData.agntDpRcgCharge == null || formData.agntDpRcgCharge == '' )
					{
						form.pushError(["agntDpRcgCharge"], "충전금액", "은 필수입력입니다.");
						mvno.ui.notify("충전금액은 필수입력입니다.");
						return;
						
					}
					
					if(formData.agntDpRcgCnt == null || formData.agntDpRcgCnt == ''||formData.agntDpRcgCnt.length==0)
					{
						PAGE.FORM30.pushError(["agntDpRcgCnt"], "충전회수", "은 필수입력입니다.");
						mvno.ui.notify("충전회수 필수입력입니다.");
						return;
						
					}
					
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsCustomerDpRcgSetting.json",
		    				PAGE.FORM30
							, function(result) {
								//console.log(result);
								var result = result.result;
								var retCode = result.retCode;
								var msg = result.retMsg;

								if(retCode == "000") {

                            		  mvno.ui.notify(msg);
                            		  dpRcgFormRefresh(contractNum);
                            		  
                                  }
                                  else {
                                	  mvno.ui.notify(msg);
                                     // mvno.ui.notify("ECMN0001");
                                  }
							});								
					break;
			
			}
		}
		,onChange : function(name, value) {
			
			switch(name){
				case "agntDpRcgFlag":
					
					PAGE.FORM30.hideItem("blockDpRcgType0");
					PAGE.FORM30.hideItem("blockDpRcgType1");
					
					PAGE.FORM30.setItemValue("agntDpRcgRemains","");
					PAGE.FORM30.setItemValue("agntDpRcgCharge","");
					PAGE.FORM30.setItemValue("agntDpRcgCnt","");
					//PAGE.FORM30.setItemValue("agntDpRcgNowCnt","현재충전수 : ");
					
					switch (value) {
					
					case "N":
						PAGE.FORM30.showItem("blockDpRcgType0");
						PAGE.FORM30.hideItem("blockDpRcgType1");
						break;
					case "Y":
						PAGE.FORM30.hideItem("blockDpRcgType0");
						PAGE.FORM30.showItem("blockDpRcgType1");
						break;
					default:
						PAGE.FORM30.showItem("blockDpRcgType0");
						PAGE.FORM30.hideItem("blockDpRcgType1");
						break;
					}
				break;
			}
		}
		,onValidateMore : function(data)
		{	
			
			var formData =  PAGE.FORM30.getFormData(true);
			//console.log("formData",formData);
			
			switch(formData.dpRcgFlag){
				case "N":
					break;
				case "Y":
					
					
				/* 	if( formData.agntDpRcgRemains == null || formData.agntDpRcgRemains == '' )
					{
						this.pushError(["agntDpRcgRemains"], "임계잔액", "이 없습니다.");
						 
					}
					
					if( formData.agntDpRcgCharge == null || formData.agntDpRcgCharge == '' )
					{
						this.pushError(["agntDpRcgCharge"], "충전금액", "가 없습니다.");
						
					}
					
					if( formData.agntDpRcgCnt == null || formData.agntDpRcgCnt == '' )
					{
						this.pushError(["agntDpRcgCnt"], "충전회수", "필수입력입니다.");
						
					} */
					
					
					
					
			}
		}
		
	}// FORM end..
	,FORM4 : {
		title: "선불 충전"
		,items: _FORMITEMS_['FORM4']
		,onButtonClick : function(btnId) {
	
			var form = this;
	
			switch (btnId) {
	
				case "btnChargeFree":
					PAGE.FORM4.setItemLabel("labelRechargeResult","");
					//alert('무료충전');	
					var data = this.getFormData(true);
					var contract_Num =PAGE.FORM2.getItemValue("contractNum");
					var subscriber_Num =PAGE.FORM2.getItemValue("subscriberNo");

				    var reqType_str="BS_RCG_VOICE_FREE";
				    var op_code_str = "RCG";
				    var rechargeVal="";
				    var  freePrice = data.freePrice;
				    var  freeDirectPrice = data.freeDirectPrice;
				    
				    if(freePrice != "")
					{
				    	rechargeVal = freePrice;
				    }
				    if(freeDirectPrice!="")
					{
				    	rechargeVal = 	freeDirectPrice;
					}

					PAGE.FORM4.setItemValue("contractNum", contract_Num);
					PAGE.FORM4.setItemValue("subscriberNo", subscriber_Num);
					PAGE.FORM4.setItemValue("reqType", reqType_str);
					PAGE.FORM4.setItemValue("opCode", op_code_str);
					PAGE.FORM4.setItemValue("recharge", rechargeVal);
					PAGE.FORM4.setItemValue("form4BtnType", "1");
					
					
				    /*
				    if( (freePrice == '') && (freeDirectPrice == ''))
					{
						alert("충전금액을 설정하셔야합니다");
						return;
					}
				    if( (freePrice != '') && (freeDirectPrice != ''))
					{
				    	alert("무료충전금액이 중복선택되었습니다.");
	
				    	return;
					}
					*/
					
				    
				    var url = ROOT + "/pps/hdofc/custmgmt/PpsRcgProcs.json";
					mvno.cmn.ajax(
							url, 
							PAGE.FORM4
							, function(results) {
								 var result = results.result;
	                                 var retCode = result.oResCode;
	                                 var msg = result.oResCodeNm;

									var resultStr = retCode + " : "+msg;
	                                 PAGE.FORM4.setItemLabel("labelRechargeResult",resultStr);
	                                 
	                                 if(retCode == "0000") {
	
	                           		  mvno.ui.alert(msg);
	                           		  //mvno.ui.notify("NCMN0002");
	                                     //부모창 그리드 refresh
	                                     //닫나?
	                                     Page.FORM4.refresh();
	                                 }
	                                 else {
	                               	  mvno.ui.alert(msg);
	                                    // mvno.ui.notify("ECMN0001");
	                                 }
							});					
					break;
				case "btnChargeSetting":
					//alert('무료충전조정');
					PAGE.FORM4.setItemLabel("labelRechargeResult","");
					var data = this.getFormData(true);
					var contract_Num =PAGE.FORM2.getItemValue("contractNum");
					var subscriber_Num =PAGE.FORM2.getItemValue("subscriberNo");
				    var reqType_str="BS_REMAINS_EDIT";
				    var op_code_str = "RCG";
				    var rechargeVal="";
				    var  freePrice = data.freePrice;
				    var  freeDirectPrice = data.freeDirectPrice;


				    if(freePrice != "")
					{
				    	rechargeVal = freePrice;
				    }
				    if(freeDirectPrice!="")
					{
				    	rechargeVal = 	freeDirectPrice;
					}

					PAGE.FORM4.setItemValue("contractNum", contract_Num);
					PAGE.FORM4.setItemValue("subscriberNo", subscriber_Num);
					PAGE.FORM4.setItemValue("reqType", reqType_str);
					PAGE.FORM4.setItemValue("opCode", op_code_str);
					PAGE.FORM4.setItemValue("recharge", rechargeVal);
					PAGE.FORM4.setItemValue("form4BtnType", "1");


					/*
				    if( (freePrice == '') && (freeDirectPrice == ''))
					{
						alert("조정금액을 설정하셔야합니다");
						return;
					}
				    if( (freePrice != '') && (freeDirectPrice != ''))
					{
				    	alert("무료조정금액이 중복선택되었습니다.");
	
				    	return;
					}
					*/
				    
				    var url = ROOT + "/pps/hdofc/custmgmt/PpsRcgProcs.json";
					mvno.cmn.ajax(
							url, 
							PAGE.FORM4
							, function(results) {
								 var result = results.result;
                                 var retCode = result.oResCode;
                                 var msg = result.oResCodeNm;

								var resultStr = retCode + " : "+msg;
                                 PAGE.FORM4.setItemLabel("labelRechargeResult",resultStr);
	                                 if(retCode == "0000") {
	
	                           		  mvno.ui.alert(msg);
	                           		  //mvno.ui.notify("NCMN0002");
	                                     //부모창 그리드 refresh
	                                     //닫나?
	                                     Page.FORM4.refresh();
	                                 }
	                                 else {
	                               	  mvno.ui.alert(msg);
	                                    // mvno.ui.notify("ECMN0001");
	                                 }
							});														
					break;
				case "btnChargeCash":
					PAGE.FORM4.setItemLabel("labelRechargeResult","");
					var data = this.getFormData(true);
					var contract_Num =PAGE.FORM2.getItemValue("contractNum");
					var subscriber_Num =PAGE.FORM2.getItemValue("subscriberNo");
				    var reqType_str="BS_RCG_VOICE_CASH";
				    var op_code_str = "RCG";
				    var rechargeVal="";
				    var  cashPrice = data.cashPrice;
				    var  cashDirectPrice = data.cashDirectPrice;
				    /*
				    if( (cashPrice == '') && (cashDirectPrice == ''))
					{
						alert("충전금액을 설정하셔야합니다");
						return;
					}
				    if( (cashPrice != '') && (cashDirectPrice != ''))
					{
				    	alert("충전금액이 중복선택되었습니다.");
				    	return;
					}
					*/
					
				    if(cashPrice != "")
					{
				    	rechargeVal = cashPrice;
				    }
				    if(cashDirectPrice!="")
					{
				    	rechargeVal = 	cashDirectPrice;
					}

				    PAGE.FORM4.setItemValue("contractNum", contract_Num);
					PAGE.FORM4.setItemValue("subscriberNo", subscriber_Num);
					PAGE.FORM4.setItemValue("reqType", reqType_str);
					PAGE.FORM4.setItemValue("opCode", op_code_str);
					PAGE.FORM4.setItemValue("recharge", rechargeVal);
					PAGE.FORM4.setItemValue("form4BtnType", "2");
					
				    var url = ROOT + "/pps/hdofc/custmgmt/PpsRcgProcs.json";
					mvno.cmn.ajax(
							url, 
							PAGE.FORM4
							, function(results) {
								 var result = results.result;
                                 var retCode = result.oResCode;
                                 var msg = result.oResCodeNm;

								var resultStr = retCode + " : "+msg;
                                 PAGE.FORM4.setItemLabel("labelRechargeResult",resultStr);
	                                 if(retCode == "0000") {
	
	                           		  mvno.ui.alert(msg);
	                           		  //mvno.ui.notify("NCMN0002");
	                                     //부모창 그리드 refresh
	                                     //닫나?
	                                     Page.FORM4.refresh();
	                                 }
	                                 else {
	                               	  mvno.ui.alert(msg);
	                                    // mvno.ui.notify("ECMN0001");
	                                 }
							});
				   
					
					break;
				case "btnChargePinFind":
					
					var data = this.getFormData(true);
					PAGE.FORM4.setItemValue("pinPrice", "");
					PAGE.FORM4.setItemLabel("pinNumberSearchResult", "");
					PAGE.FORM4.setItemValue("form4BtnType", "3");
					
					var url = ROOT + "/pps/hdofc/custmgmt/PpsPinInfo.json";
					mvno.cmn.ajax(
							url 
							,PAGE.FORM4
							,function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								if(retCode == "0000") {
									mvno.ui.notify(msg);
									PAGE.FORM4.setItemValue("pinPrice", result.nowPrice);
									PAGE.FORM4.setItemLabel("pinNumberSearchResult", msg);
								}
								else {
									PAGE.FORM4.setItemValue("pinPrice", "0");
									mvno.ui.notify(msg);
									PAGE.FORM4.setItemLabel("pinNumberSearchResult", msg);
								}
							});							
					break;
				case "btnChargePinRecharge":

					PAGE.FORM4.setItemLabel("pinRechargeResult", "");
						
					var data = this.getFormData(true);
					var contract_Num =PAGE.FORM2.getItemValue("contractNum");
					var subscriber_Num =PAGE.FORM2.getItemValue("subscriberNo");
				    var reqType_str="BS_RCG_VOICE_PIN";
				    var op_code_str = "RCG";
				    var rechargeVal=data.pinPrice;
					var recharge_Info = data.pinNumber;

					PAGE.FORM4.setItemValue("contractNum", contract_Num);
					PAGE.FORM4.setItemValue("subscriberNo", subscriber_Num);
					PAGE.FORM4.setItemValue("reqType", reqType_str);
					PAGE.FORM4.setItemValue("opCode", op_code_str);
					PAGE.FORM4.setItemValue("recharge", rechargeVal);
					PAGE.FORM4.setItemValue("rechargeInfo", recharge_Info);
					PAGE.FORM4.setItemValue("form4BtnType", "4");

					
			
					var url = ROOT + "/pps/hdofc/custmgmt/PpsRcgProcs.json";
					mvno.cmn.ajax(
							url 
							,PAGE.FORM4
							, function(results) {
								var result = results.result;
								var retCode = result.oResCode;
								var retCodeNm = result.oResCodeNm;
								var msg = result.retMsg;

								var codeAndName = retCode + " : " + retCodeNm; 								
								PAGE.FORM4.setItemLabel("pinRechargeResult", codeAndName);
								
								if(retCode == "0000") {
									mvno.ui.notify(msg);
									Page.FORM4.refresh();
								}
								else {
									mvno.ui.notify(msg);
								}
							});
					break;
	
			}
	
		}
		,onKeyUp : function(inp, ev, name, value)
		{
			switch(name)
			{
				case "pinNumber":
	
					var inputValues = PAGE.FORM4.getItemValue("pinNumber");
					if(inputValues == null || inputValues == '')
					{
						PAGE.FORM4.setItemValue("pinPrice", "");
						PAGE.FORM4.setItemLabel("pinNumberSearchResult", "");
						PAGE.FORM4.setItemLabel("pinRechargeResult", "");
					}
					break;
				case "freeDirectPrice":
					
					var inputValues = PAGE.FORM4.getItemValue("freeDirectPrice");
					if(inputValues == null || inputValues == '')
					{
						PAGE.FORM4.setItemLabel("labelRechargeResult","");
					}
					break;
				case "cashDirectPrice":
					
					var inputValues = PAGE.FORM4.getItemValue("cashDirectPrice");
					if(inputValues == null || inputValues == '')
					{
						PAGE.FORM4.setItemLabel("labelRechargeResult","");
					}
					break;
			}
		}
		,onChange : function(name, value) 
		{
			switch(name)
			{
				case"cashPrice":
					PAGE.FORM4.setItemLabel("labelRechargeResult","");				
					break;
				case"freePrice":
					PAGE.FORM4.setItemLabel("labelRechargeResult","");				
					break;
			}
		}
		,onValidateMore : function(data)
		{

			switch(data.form4BtnType)
			{
				case "1":
					if(( data.freePrice == null || data.freePrice == '' ) && (data.freeDirectPrice == null || data.freeDirectPrice == '' ))
					{
						 this.pushError(["freePrice","freeDirectPrice"], "무료 충전 금액", "을 선택해주세요");							
					}
					else if(( data.freePrice != null && data.freePrice != '' ) && (data.freeDirectPrice != null && data.freeDirectPrice != '' ))
					{
						 this.pushError(["freePrice","freeDirectPrice"], "무료 충전 금액", "이 중복되었습니다.");							
					}
					break;
				case "2":
					if(( data.cashPrice == null || data.cashPrice == '' ) && (data.cashDirectPrice == null || data.cashDirectPrice == '' ))
					{
						 this.pushError(["cashPrice","cashDirectPrice"], "현금 충전 금액", "을 선택해주세요");							
					}
					else if(( data.cashPrice != null && data.cashPrice != '' ) && (data.cashDirectPrice != null && data.cashDirectPrice != '' ))
					{
						 this.pushError(["cashPrice","cashDirectPrice"], "현금 충전 금액", "이 중복되었습니다.");							
					}
					break;
				case "3":
					if( data.pinNumber == null || data.pinNumber == '' )
					{
						this.pushError(["pinNumber"], "PIN번호", "를 입력해주세요");	
					}
					break;
				case "4":
					if( data.pinNumber == null || data.pinNumber == '' )
					{
						this.pushError(["pinNumber"], "PIN번호", "를 입력해주세요");	
					}
					if( data.pinPrice == null || data.pinPrice == '' )
					{
						this.pushError(["pinPrice"], "충전 금액(잔액)", "를 조회 후 충전하십시오.");	
					}
					break;
			}
			
		}
	}// FORM end..
	,FORM5 : {
		title: "가상 계좌"
		,items: _FORMITEMS_['FORM5']	
		,onButtonClick : function(btnId) {
			var form = this;
	
			switch (btnId) {
	
				case "btnVacSendSms":
					//alert('문자발송');	
					var contract_num_str = PAGE.FORM2.getItemValue("contractNum");
					var comment = '가상계좌 정보를 문자로 발송하시겠습니까?';
					
					PAGE.FORM5.setItemValue("event","VAC_INFORM");
					PAGE.FORM5.setItemValue("form5BtnType","1");
					PAGE.FORM5.setItemValue("contractNum",contract_num_str);
					
					if(confirm(comment))
					{
						var url = ROOT + "/pps/hdofc/custmgmt/PpsSmsProcs.json";
						mvno.cmn.ajax(
								url, 
								PAGE.FORM5
								, function(result) {
									 var result = result.result;
	                                  var retCode = result.oRetCd;
	                                  var msg = result.oRetMsg;
	
	                                  if(retCode == "0000") {
	                            		  mvno.ui.notify(msg);
	                                  }
	                                  else {
	                                	  mvno.ui.notify(msg);
	                                  }
								});	
					}
					break;
				case "btnVacRequest":
	
					var data = this.getFormData(true);
					var op_code_str ="GIVE"; 
					var op_mode_str = 'B';
					var user_type_str = 'U';
					var user_number_str=PAGE.FORM2.getItemValue("contractNum");
	
					var req_bank_name_str = data.virBankNm;
					var req_bank_Code_str = data.virBankNm;

					PAGE.FORM5.setItemValue("form5BtnType","2");
					PAGE.FORM5.setItemValue("opCode",op_code_str);
					PAGE.FORM5.setItemValue("opMode",op_mode_str);
					PAGE.FORM5.setItemValue("userType",user_type_str);
					PAGE.FORM5.setItemValue("userNumber",user_number_str);
					PAGE.FORM5.setItemValue("reqBankName",req_bank_name_str);
					PAGE.FORM5.setItemValue("reqBankCode",req_bank_Code_str);
					
					var comment = '선택한 은행으로 가상계좌를 부여 하시겠습니까?';
					
					if(confirm(comment))
					{
						  var url = ROOT + "/pps/hdofc/custmgmt/PpsVacProcs.json";
							mvno.cmn.ajax(
									url, 
									PAGE.FORM5
									, function(results) {
										 var result = results.result;
		                                  var retCode = result.retCode;
		                                  var msg = result.retMsg;
		                                  if(retCode == "0000") {
	
		                            		  mvno.ui.notify(msg);
		                            		  vacFormRefresh();
		                                  }
		                                  else {
		                                	  mvno.ui.notify(msg);
		                                     // mvno.ui.notify("ECMN0001");
		                                  }
									});
	
					}	
					break;
				case "btnVacCollect":
					var data = this.getFormData(true);
					var op_code_str ="BACK"; 
					var op_mode_str = 'B';
					var user_type_str = 'U';
					var user_number_str=PAGE.FORM2.getItemValue("contractNum");
	
					var req_bank_name_str = data.virBankNm;
					var req_bank_Code_str = data.virBankNm;

					PAGE.FORM5.setItemValue("form5BtnType","3");
					PAGE.FORM5.setItemValue("opCode",op_code_str);
					PAGE.FORM5.setItemValue("opMode",op_mode_str);
					PAGE.FORM5.setItemValue("userType",user_type_str);
					PAGE.FORM5.setItemValue("userNumber",user_number_str);
					PAGE.FORM5.setItemValue("reqBankName",req_bank_name_str);
					PAGE.FORM5.setItemValue("reqBankCode",req_bank_Code_str);
					
					var comment = '현재 사용중인 가상계좌를 회수 하시겠습니까?';
					
					if(confirm(comment))
					{
						  var url = ROOT + "/pps/hdofc/custmgmt/PpsVacProcs.json";
							mvno.cmn.ajax(
									url, 
									PAGE.FORM5
									, function(results) {
										 var result = results.result;
		                                  var retCode = result.retCode;
		                                  var msg = result.retMsg;
		                                  if(retCode == "0000") {
	
		                            		  mvno.ui.notify(msg);
		                            		  PAGE.FORM5.setItemValue("vacId", "");
		                  					  PAGE.FORM5.setItemValue("vacNm", "");
		                  					  PAGE.FORM5.setItemValue("virBankNm", "");
			                  				  PAGE.FORM5.showItem("btnVacRequest");
			            					  PAGE.FORM5.hideItem("btnVacCollect");
			            					  PAGE.FORM5.hideItem("btnVacChange");
		                                  }
		                                  else {
		                                	  mvno.ui.notify(msg);
		                                     // mvno.ui.notify("ECMN0001");
		                                  }
									});
	
					}	
					break;
				case "btnVacChange":
					//alert('가상계좌변경');
					var data = this.getFormData(true);
					var op_code_str ="CHANGE"; 
					var op_mode_str = 'B';
					var user_type_str = 'U';
					var user_number_str=PAGE.FORM2.getItemValue("contractNum");
	
					var req_bank_name_str = data.virBankNm;
					var req_bank_Code_str = data.virBankNm;

					PAGE.FORM5.setItemValue("form5BtnType","4");
					PAGE.FORM5.setItemValue("opCode",op_code_str);
					PAGE.FORM5.setItemValue("opMode",op_mode_str);
					PAGE.FORM5.setItemValue("userType",user_type_str);
					PAGE.FORM5.setItemValue("userNumber",user_number_str);
					PAGE.FORM5.setItemValue("reqBankName",req_bank_name_str);
					PAGE.FORM5.setItemValue("reqBankCode",req_bank_Code_str);
	
					var comment = '선택한 은행으로 가상계좌를 부여 하시겠습니까?';
					if(confirm(comment))
					{
						  var url = ROOT + "/pps/hdofc/custmgmt/PpsVacProcs.json";
							mvno.cmn.ajax(
									url, 
									PAGE.FORM5
									, function(results) {
										 var result = results.result;
		                                  var retCode = result.retCode;
		                                  var msg = result.retMsg;
		                                  if(retCode == "0000") {
	
		                            		  mvno.ui.notify(msg);
		                            		  vacFormRefresh();
		                                  }
		                                  else {
		                                	  mvno.ui.notify(msg);
		                                     // mvno.ui.notify("ECMN0001");
		                                  }
									});
	
					}	
					break;
	
			}
	
			
	
		}
		,onValidateMore : function(data)
		{
			switch(data.form5BtnType)
			{
				case "1":
					if( ( data.vacNm == null || data.vacNm == '' ) && ( data.vacId == null || data.vacId == '' ) )
					{
						this.pushError(["vacNm,vacId"], "계좌 문자 전송", "전송할 계좌가 없습니다.");	
					}
					break;
				case "2":
					if( data.virBankNm == null || data.virBankNm == '' )
					{
						this.pushError(["virBankNm"], "은행", "을 선택해주세요");	
					}
					break;
				case "3":
					if( ( data.vacNm == null || data.vacNm == '' ) && ( data.vacId == null || data.vacId == '' ) )
					{
						this.pushError(["vacNm,vacId"], "은행 계좌 회수", "회수할 계좌가 없습니다.");	
					}
					break;
				case "4":
					if( data.virBankNm == null || data.virBankNm == '' )
					{
						this.pushError(["virBankNm"], "은행", "을 선택해주세요");	
					}
					break;
			}
		}
	}// FORM end..
	,FORM7: {
		items: _FORMITEMS_['FORM7']
		,onButtonClick : function(btnId) {
			
			var form = this;

			var contractNum = PAGE.FORM2.getItemValue("contractNum");

			switch (btnId) {

				case "btnCmsDisableApply":
					//alert('미출금설정 버튼');
					
					PAGE.FORM7.setItemValue("cmsAgreeFlag","N");


					PAGE.FORM7.setItemValue("cmsTryRemains","");
					PAGE.FORM7.setItemValue("cmsCharge","");
					PAGE.FORM7.setItemValue("cmsChargeDate","");
					PAGE.FORM7.setItemValue("cmsChargeMonth","");
					PAGE.FORM7.setItemValue("cmsChargeMonthSelect","");
					PAGE.FORM7.setItemValue("nowCharge","");
					
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsCustomerRealCmsSetting.json",
		    				PAGE.FORM7
							, function(result) {

								//console.log(result);
								var result = result.result;
								var retCode = result.retCode;
								var msg = result.retMsg;

								if(retCode == "000") {

                            		  mvno.ui.notify(msg);

                            		  cmsFormRefresh(contractNum);
                            		  
                                  }
                                  else {
                                	  mvno.ui.notify(msg);
                                     // mvno.ui.notify("ECMN0001");
                                  }
							});						
					break;
				case "btnLastRemainsApply":
					//alert('잔액부족시 출금 버튼');
					
					if(PAGE.FORM7.getItemValue("payinfoFlag") != 'Y'){
						mvno.ui.alert("회원증빙 후 설정가능합니다.");
						return;
					}
					
					PAGE.FORM7.setItemValue("cmsAgreeFlag","Y");

					PAGE.FORM7.setItemValue("cmsChargeDate","");
					PAGE.FORM7.setItemValue("cmsChargeMonth","");
					PAGE.FORM7.setItemValue("cmsChargeMonthSelect","");
					PAGE.FORM7.setItemValue("nowCharge","");
					
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsCustomerRealCmsSetting.json",
		    				PAGE.FORM7
							, function(result) {
								//console.log(result);
								var result = result.result;
								var retCode = result.retCode;
								var msg = result.retMsg;

								if(retCode == "000") {

                            		  mvno.ui.notify(msg);
                            		  cmsFormRefresh(contractNum);
                            		  
                                  }
                                  else {
                                	  mvno.ui.notify(msg);
                                     // mvno.ui.notify("ECMN0001");
                                  }
							});								
					break;
				case "btnCmsScheduleApply":
					
					if(PAGE.FORM7.getItemValue("payinfoFlag") != 'Y'){
						mvno.ui.alert("회원증빙 후 설정가능합니다.");
						return;
					}
					
					PAGE.FORM7.setItemValue("cmsAgreeFlag","Y");

					PAGE.FORM7.setItemValue("cmsTryRemains","");
					PAGE.FORM7.setItemValue("cmsCharge","");
					PAGE.FORM7.setItemValue("nowCharge","");
				
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsCustomerRealCmsSetting.json",
		    				PAGE.FORM7
							,function(result) {
								//console.log(result);
								var result = result.result;
								var retCode = result.retCode;
								var msg = result.retMsg;

								if(retCode == "000") {

                            		  mvno.ui.notify(msg);
                            		  cmsFormRefresh(contractNum);
                            		  
                                  }
                                  else {
                                	  mvno.ui.notify(msg);
                                     // mvno.ui.notify("ECMN0001");
                                  }
							});					
					break;
				case "btnCmsNowChargeApply":
					//alert('즉시출금 버튼');
					if(PAGE.FORM7.getItemValue("payinfoFlag") != 'Y'){
						mvno.ui.alert("회원증빙 후 설정가능합니다.");
						return;
					}
					
					PAGE.FORM7.setItemValue("cmsAgreeFlag","Y");

					PAGE.FORM7.setItemValue("cmsTryRemains","");
					PAGE.FORM7.setItemValue("cmsCharge","");
					PAGE.FORM7.setItemValue("cmsChargeDate","");
					PAGE.FORM7.setItemValue("cmsChargeMonth","");
					PAGE.FORM7.setItemValue("cmsChargeMonthSelect","");
				
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsCustomerRealCmsSetting.json",
		    				PAGE.FORM7
		    				,function(result) {
								//console.log(result);
								var result = result.result;
								var retCode = result.retCode;
								var msg = result.retMsg;

								if(retCode == "000") {

                            		  mvno.ui.notify(msg);
                            		  cmsFormRefresh(contractNum);

                            		  alert("즉시출금을 요청하였습니다.");
                            		  
                                  }
                                  else {
                                	  mvno.ui.notify(msg);
                                     // mvno.ui.notify("ECMN0001");
                                  }
							});						
					break;
					
				case "payinfoUpdate":
					//alert('증빙하기 버튼');
					 var fileLimitCnt = 1;
  			       
  			       mvno.ui.createForm("FORM23");
                   PAGE.FORM23.clear();
                   
					//var data = PAGE.FORM2.getFormData(true);
					var data7 = PAGE.FORM7.getFormData(true);

                   //PAGE.FORM23.setFormData(data);
                   PAGE.FORM23.setFormData(data7);
                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0071"}, PAGE.FORM23, "regTypePop");
                   var contractNum = PAGE.FORM2.getItemValue("contractNum");
                   PAGE.FORM23.setItemValue("contractNum", contractNum);
                   var subscriberNo = PAGE.FORM2.getItemValue("subscriberNo");
                   var subscriberNoMsk = PAGE.FORM2.getItemValue("subscriberNoMsk");
                   PAGE.FORM23.setItemValue("subscriberNo", subscriberNo);
                   PAGE.FORM23.setItemValue("subscriberNoMsk", subscriberNoMsk);
                 
                   var uploader = PAGE.FORM23.getUploader("file_upload1");
                   
                   if(payinfo_old_url == ""){
                	   payinfo_old_url = uploader._url;
                   }
                   
				   var url = payinfo_old_url + "?contractNum=" + contractNum;
	               uploader._url = url;   
	               uploader._swf_upolad_url = url;
  
                   uploader.revive();
                   
                   PAGE.FORM23.attachEvent("onBeforeFileAdd",function(realName, size, file){
                	      var regTypePop = PAGE.FORM23.getItemValue("regTypePop");

                	   	  var pathpoint = realName.lastIndexOf('.'); 
	                      var filepoint = realName.substring(pathpoint+1,realName.length); 
	                      var extention = filepoint.toLowerCase(); 
	                      
	                      uploader._url = payinfo_old_url; // url 초기화 contract_num이 계속붙음.
	                      
	                      if(regTypePop == '01'){
	                    	  if (extention == 'gif'|| extention == 'jpg'){

	  	                      }
	  	                      else
	  	                      {
	  								mvno.ui.alert('서면은 gif, jpg만 가능합니다.');
	  	                          return false;
	  	                      }
	                    	  
	                    	  if(parseInt(size) > 307200){
		                    	  mvno.ui.alert("서면은 최대 300KB까지 업로드가능합니다.");
		                    	  return false;
		                      }
	                      }else if(regTypePop == '02'){
	                    	  if (extention == 'mp3'|| extention == 'wmv'){

	  	                      }
	  	                      else
	  	                      {
	  								mvno.ui.alert('녹취는 mp3, wmv만 가능합니다.');
	  	                          return false;
	  	                      }
	                    	  
	                    	  if(parseInt(size) > 204800){
		                    	  mvno.ui.alert("녹취는 최대 200KB까지 업로드가능합니다.");
		                    	  return false;
		                      }
	                      }else{
	                    	  mvno.ui.alert('증빙서류를 선택하세요.');
	                    	  return false;
	                      }
	                      
	                      return true; 
                   });
                   
                   PAGE.FORM23.attachEvent("onUploadFile",function(realName, serverName){
						
						if(realName == null || realName == '')
                    	{
							alert('필수입력 입니다.');
                        	return false;
                        }
						
						return true; 
	                      
					});
					PAGE.FORM23.attachEvent("onUploadComplete",function(realName, serverName,result){
					    var form23 = PAGE.FORM23;
						var data3 = form23.getFormData(true);
				   });

                   mvno.ui.popupWindowById("FORM23", "회원증빙", 600, 300, {
                	    onClose2: function(win) {
                	        uploader.reset();
                	      
                	    }
                   });		
					
					
					/*
					PAGE.FORM7.setItemValue("cmsAgreeFlag","Y");

					PAGE.FORM7.setItemValue("cmsTryRemains","");
					PAGE.FORM7.setItemValue("cmsCharge","");
					PAGE.FORM7.setItemValue("cmsChargeDate","");
					PAGE.FORM7.setItemValue("cmsChargeMonth","");
				
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsCustomerRealCmsSetting.json",
		    				PAGE.FORM7
		    				,function(result) {
								//console.log(result);
								var result = result.result;
								var retCode = result.retCode;
								var msg = result.retMsg;

								if(retCode == "000") {

                            		  mvno.ui.notify(msg);
                            		  cmsFormRefresh(contractNum);

                            		  alert("즉시출금을 요청하였습니다.");
                            		  
                                  }
                                  else {
                                	  mvno.ui.notify(msg);
                                     // mvno.ui.notify("ECMN0001");
                                  }
							});	
					*/
					break;

			}
		}
		,onChange : function(name, value) {
			
			switch(name){
				case "cmsChargeType":
					PAGE.FORM7.hideItem("blockCmsType0");
					PAGE.FORM7.hideItem("blockCmsType1");
					PAGE.FORM7.hideItem("blockCmsType2");
					PAGE.FORM7.hideItem("blockCmsType3");

					switch (value) {
					
					case "0":
						PAGE.FORM7.showItem("blockCmsType0");
						PAGE.FORM7.hideItem("blockCmsType1");
						PAGE.FORM7.hideItem("blockCmsType2");
						PAGE.FORM7.hideItem("blockCmsType3");
						break;
					case "1":
						PAGE.FORM7.hideItem("blockCmsType0");
						PAGE.FORM7.showItem("blockCmsType1");
						PAGE.FORM7.hideItem("blockCmsType2");
						PAGE.FORM7.hideItem("blockCmsType3");
						break;
					case "2":
						PAGE.FORM7.hideItem("blockCmsType0");
						PAGE.FORM7.hideItem("blockCmsType1");
						PAGE.FORM7.showItem("blockCmsType2");
						PAGE.FORM7.hideItem("blockCmsType3");
						break;
					case "3":
						PAGE.FORM7.hideItem("blockCmsType0");
						PAGE.FORM7.hideItem("blockCmsType1");
						PAGE.FORM7.hideItem("blockCmsType2");
						PAGE.FORM7.showItem("blockCmsType3");
						break;

					}
					break;
				case "cmsChargeMonthSelect":
					PAGE.FORM7.setItemValue("cmsChargeMonth", value);
					break;
				case "cmsChargeSelect":
					PAGE.FORM7.setItemValue("cmsCharge", value);
					break;
			}
		}
		,onValidateMore : function(data)
		{
			switch(data.cmsChargeType){
				case "0":
					break;
				case "1":
					if( data.cmsBankCode == null || data.cmsBankCode == '' )
					{
						this.pushError(["cmsBankCode"], "은행", "을 선택해주세요");	
					}
					if( data.cmsAccount == null || data.cmsAccount == '' )
					{
						this.pushError(["cmsAccount"], "계좌번호", "를 입력해주세요");	
					}
					if(data.cmsAccount.match(/^\d+$/ig) == null)
					{
						this.pushError("cmsAccount","계좌번호","숫자만 입력하세요");
					}
					if( data.cmsTryRemains == null || data.cmsTryRemains == '' )
					{
						this.pushError(["cmsTryRemains"], "남은 잔액", "을 선택해주세요");	
					}
					if( data.cmsCharge == null || data.cmsCharge == '' )
					{
						this.pushError(["cmsCharge"], "출금 금액", "을 선택해주세요");	
					}
					break;
				case "2":
					if( data.cmsBankCode == null || data.cmsBankCode == '' )
					{
						this.pushError(["cmsBankCode"], "은행", "을 선택해주세요");	
					}
					if( data.cmsAccount == null || data.cmsAccount == '' )
					{
						this.pushError(["cmsAccount"], "계좌번호", "를 입력해주세요");	
					}
					if(data.cmsAccount.match(/^\d+$/ig) == null)
					{
						this.pushError("cmsAccount","계좌번호","숫자만 입력하세요");
					}
					if( data.cmsChargeDate == null || data.cmsChargeDate == '' )
					{
						this.pushError(["cmsChargeDate"], "출금 일", "을 선택해주세요");	
					}
					if( data.cmsChargeMonth == null || data.cmsChargeMonth == '' )
					{
						this.pushError(["cmsChargeMonth"], "출금 금액", "을 입력해주세요");	
					}
					break;
				case "3":
					if( data.cmsBankCode == null || data.cmsBankCode == '' )
					{
						this.pushError(["cmsBankCode"], "은행", "을 선택해주세요");	
					}
					if( data.cmsAccount == null || data.cmsAccount == '' )
					{
						this.pushError(["cmsAccount"], "계좌번호", "를 입력해주세요");	
					}
					if(data.cmsAccount.match(/^\d+$/ig) == null)
					{
						this.pushError("cmsAccount","계좌번호","숫자만 입력하세요");
					}
					if( data.nowCharge == null || data.nowCharge == '' )
					{
						this.pushError(["nowCharge"], "즉시 출금액", "을 입력해주세요");	
					}
					if(data.nowCharge.match(/^\d+$/ig) == null)
					{
						this.pushError("nowCharge","즉시 출금액","숫자만 입력하세요");
					}
					break;
			}
		}
		
	}// FORM end..
	,FORM8: {
		items: _FORMITEMS_['FORM8']
		,onButtonClick : function(btnId) {

			switch (btnId) {
				case "btnSearchPpsCdr":
					var btnCount2 = parseInt(PAGE.FORM8.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM8.setItemValue("btnCount1", btnCount2)
					if (PAGE.FORM8.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
					
					var tempContractNum = PAGE.FORM2.getItemValue("contractNum");
					PAGE.FORM8.setItemValue("contractNum", tempContractNum);
					
					PAGE.GRID6.list(ROOT + "/pps/hdofc/custmgmt/PpsCdrPpTabList.json", PAGE.FORM8, {
						callback : function () {
							PAGE.FORM8.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM8.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						}
					});
					
					break;
			}
		}
		,onValidateMore : function(data){
	
			 if(data.startDt > data.endDt){
				 PAGE.FORM8.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				 PAGE.FORM8.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
			 }
		}
	}// FORM end..
	,FORM9: {
		items: _FORMITEMS_['FORM9']
		,onButtonClick : function(btnId) {

			switch (btnId) {

				case "btnSearchPpsCdrDaily":
					var btnCount2 = parseInt(PAGE.FORM9.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM9.setItemValue("btnCount1", btnCount2)
					if (PAGE.FORM9.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
					
					var tempContractNum = PAGE.FORM2.getItemValue("contractNum");
					PAGE.FORM9.setItemValue("contractNum", tempContractNum);
					
					PAGE.GRID7.list(ROOT + "/pps/hdofc/custmgmt/PpsCdrPktTabList.json", PAGE.FORM9, {
						callback : function () {
							PAGE.FORM9.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM9.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						}
					});

					break;

			}
		}
		,onValidateMore : function(data){
			
			 if(data.startDt > data.endDt){
				 PAGE.FORM9.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				 PAGE.FORM9.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
			 }
		}
	}// FORM end..
	,FORM10: {
		title : "상담등록"
		,items: _FORMITEMS_['FORM10']
	    ,buttons: {
	    	center : {
				btnReg : {	label : "저장" },
				btnClose : { label: "닫기" }
			}
	    }
	   ,onButtonClick : function(btnId) {

			//var data = PAGE.FORM10.getFormData(true);
			var form = this; // 혼란방지용으로 미리 설정 (권고)

			switch (btnId) {
				case "btnReg":

					PAGE.FORM10.setItemValue("opCode","ADD");
					//alert('test');
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsCustomerDiaryWriteChange.json"
							, PAGE.FORM10
							, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.notify(msg);
                                if(retCode == "0000") {
									if(PAGE.GRID9)
									{
										PAGE.GRID9.refresh();	
									}
                                    mvno.ui.closeWindowById(form,true);
                                }
								
							});
					
					break;
				case "btnClose":
					mvno.ui.closeWindowById(this);
					break;

			}
		}
	   ,onChange : function(name, value) {
		   var subLinkName = PAGE.FORM10.getFormData(true).subLinkName;
			switch(name){
				case "reqUserGubun":
					switch (value) {
					case "1":
						PAGE.FORM10.setItemValue("reqUserName",subLinkName);
						PAGE.FORM10.setItemValue("reqUserNameCheck", true);
						break;
					default :
						PAGE.FORM10.setItemValue("reqUserName","");
						PAGE.FORM10.setItemValue("reqUserNameCheck", false);
						break;
					}
				break;
				case "reqUserNameCheck":
					var isReqUserNameChecked = PAGE.FORM10.isItemChecked("reqUserNameCheck")
					if(isReqUserNameChecked)
					{
						PAGE.FORM10.setItemValue("reqUserName",subLinkName);
						PAGE.FORM10.setItemValue("reqUserGubun","1");	
					}
					else
					{
						PAGE.FORM10.setItemValue("reqUserName","");
						PAGE.FORM10.setItemValue("reqUserGubun","");	
					}
					
				break;
			}
		}
	}// FORM end..
	,FORM10_2: {
		title : "상담등록"
		,items: _FORMITEMS_['FORM10']
	    ,buttons: {
	    	center : {
				btnMod : {	label : "수정" },
				btnClose : { label: "닫기" }
			}
	    }
	   ,onButtonClick : function(btnId) {

			var data = PAGE.FORM10_2.getFormData(true);
			var form = this; // 혼란방지용으로 미리 설정 (권고)

			switch (btnId) {
				case "btnMod":

					PAGE.FORM10_2.setItemValue("opCode","EDT");
					//alert('test');
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsCustomerDiaryWriteChange.json"
							, PAGE.FORM10_2
							, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.notify(msg);

								if(retCode == "0000") {
									if(PAGE.GRID9)
									{
										PAGE.GRID9.refresh();	
									}
									mvno.ui.closeWindowById(PAGE.FORM10_2, true);
								}
							});
					
					break;
				case "btnClose":
					//alert('test');
					mvno.ui.closeWindowById(this);
					break;

			}
		}
	   ,onChange : function(name, value) {
		   var subLinkName = PAGE.FORM10_2.getFormData(true).subLinkName;
			switch(name){
				case "reqUserGubun":
					switch (value) {
					case "1":
						PAGE.FORM10_2.setItemValue("reqUserName",subLinkName);
						PAGE.FORM10_2.setItemCheck("reqUserNameCheck", true);
						break;
					default :
						PAGE.FORM10_2.setItemValue("reqUserName","");
						PAGE.FORM10_2.setItemCheck("reqUserNameCheck", false);
						break;
					}
				break;
				case "reqUserNameCheck":
					var isReqUserNameChecked = PAGE.FORM10.isItemChecked("reqUserNameCheck")
					if(isReqUserNameChecked)
					{
						PAGE.FORM10_2.setItemValue("reqUserName",subLinkName);
						PAGE.FORM10_2.setItemValue("reqUserGubun","1");	
					}
					else
					{
						PAGE.FORM10_2.setItemValue("reqUserName","");
						PAGE.FORM10_2.setItemValue("reqUserGubun","");	
					}
					
				break;
			}
		}
	}// FORM end..
	,FORM11: {
		title:"문자작성"
		,items: _FORMITEMS_['FORM11']
	    ,buttons: {
	    	center : {
	    		btnSmsSend : {	label : "전송" },
				btnSmsClose : { label: "닫기" }
			}
	    }
		,onButtonClick : function(btnId) {
	    	
	    	var form = this;
	    	
			switch (btnId) {
				case "btnSmsSend":
					//alert('SMS 전송하기');
					mvno.cmn.ajax(
		    				ROOT + " /pps/hdofc/custmgmt/PpsSmsProcs.json"
							,PAGE.FORM11
							, function(results) {
								var result = results.result;
								var retCode = result.oRetCd;
								var msg = result.oRetMsg;
								mvno.ui.notify(msg);

								if(retCode == "0000") {
									
									if(PAGE.GRID8)
									{
										PAGE.GRID8.refresh();
									}
									mvno.ui.closeWindowById(form, true);
								}
							});
					break;
				case "btnSmsClose":
					mvno.ui.closeWindowById(this);
					break;

			}
	    }
	}// FORM end..
	,FORM15:	{
		items: _FORMITEMS_['FORM15'],
        buttons: {
            center: {
                btnClose: { label: "닫기" }
            }
        },
        onChange : function (name, value){
            var form = this;
        },
        onButtonClick : function(btnId) {

            var form = this; // 혼란방지용으로 미리 설정 (권고)
            var fileLimitCnt = 5;

            var contractNum = PAGE.FORM2.getItemValue("contractNum");
            
            switch (btnId) {
                case "btnClose" :
                    mvno.ui.closeWindowById(form, true);
                    break;
              
                case "fileDel1" :
                     mvno.cmn.ajax(ROOT + "/pps/filemgmt/deleteFile.json",  
                             {paperImage:PAGE.FORM15.getItemValue("fileName1"),contractNum:contractNum} , function(resultData) {
                        mvno.ui.notify("NCMN0014");
                        PAGE.FORM15.setItemValue("fileName1", "");
                        PAGE.FORM15.disableItem("fileDel1");
                        PAGE.FORM15.showItem("file_upload1");

                        customerInfoData(contractNum);
                        
                     });
                        break;
                case "fileDel2" :
                	mvno.cmn.ajax(ROOT + "/pps/filemgmt/deleteFile.json",  {paperImage:PAGE.FORM15.getItemValue("fileName2"),contractNum:contractNum} , function(resultData) {
                        mvno.ui.notify("NCMN0014");
                        PAGE.FORM15.setItemValue("fileName2", "");
                        PAGE.FORM15.disableItem("fileDel2");
                        PAGE.FORM15.showItem("file_upload1");

                        customerInfoData(contractNum);
                        
                     });
                    break;
                case "fileDel3" :
                	mvno.cmn.ajax(ROOT + "/pps/filemgmt/deleteFile.json",  {paperImage:PAGE.FORM15.getItemValue("fileName3"),contractNum:contractNum} , function(resultData) {
                        mvno.ui.notify("NCMN0014");
                        PAGE.FORM15.setItemValue("fileName3", "");
                        PAGE.FORM15.disableItem("fileDel3");
                        PAGE.FORM15.showItem("file_upload1");

                        customerInfoData(contractNum);
                        
                     });
                    break;
                case "fileDel4" :
                	mvno.cmn.ajax(ROOT + "/pps/filemgmt/deleteFile.json",  {paperImage:PAGE.FORM15.getItemValue("fileName4"),contractNum:contractNum} , function(resultData) {
                        mvno.ui.notify("NCMN0014");
                        PAGE.FORM15.setItemValue("fileName4", "");
                        PAGE.FORM15.disableItem("fileDel4");
                        PAGE.FORM15.showItem("file_upload1");

                        customerInfoData(contractNum);
                        
                     });
                    break;
                case "fileDel5" :
                	mvno.cmn.ajax(ROOT + "/pps/filemgmt/deleteFile.json",  {paperImage:PAGE.FORM15.getItemValue("fileName5"),contractNum:contractNum} , function(resultData) {
                        mvno.ui.notify("NCMN0014");
                        PAGE.FORM15.setItemValue("fileName5", "");
                        PAGE.FORM15.disableItem("fileDel5");
                        PAGE.FORM15.showItem("file_upload1");

                        customerInfoData(contractNum);
                        
                     });
                    break;
            }
        },
        onValidateMore : function (data){
        	//
        }
	}// FORM end..
	,FORM16 : {
		items: _FORMITEMS_['FORM16']
		,buttons: {
			center : {
				btnNationEdit : { label: "수정" },
				btnNationClose : { label: "닫기" }
			}
		}
		,onButtonClick : function(btnId) {
				var form = this;
	
				switch (btnId) {
	
					case "btnNationEdit":
					var data = this.getFormData(true);
					var url = ROOT + "/pps/hdofc/custmgmt/PpsCusLangUpdatePop.json";

					mvno.cmn.ajax(
								url, 
								{
									contractNum : data.contractNum,
									adNation : data.adNation,
									langCode : data.langCode
								}
								, function(results) {
									var result = results.result;
									var code = result.code;
									var msg = result.oRetMsg;
									if(code == "OK") {
										mvno.ui.notify(msg);
										mvno.ui.closeWindowById(PAGE.FORM16);

										mvno.cmn.ajax(
												ROOT + "/pps/hdofc/custmgmt/selectHdofcCustMgmtInfo.json",
												{
													contractNum : data.contractNum
												},
												function(result) {
													var data = result.result.data;
													var retCode = data.retCode;
													var retMsg = data.retMsg;
													if(retCode == "000")
													{
														var adNationNmLangCodeNm = data.adNationNm + " / " + data.langCodeNm;
														PAGE.FORM2.setItemValue("adNationNmLangCodeNm",adNationNmLangCodeNm);
														//PAGE.FORM2.setItemValue("adNationNm",data.adNationNm);
														//PAGE.FORM2.setItemValue("langCodeNm",data.langCodeNm);
														PAGE.FORM2.setItemValue("adNation", data.adNation);
														PAGE.FORM2.setItemValue("langCode", data.langCode);
													}
												}
												,null);
													
									}
									else {
										mvno.ui.notify(msg);
									}
			                                  
								});		
							break;
						case "btnNationClose":
							mvno.ui.closeWindowById(this);
							break;
	
				}
			}
		}// FORM end..
		,FORM17: {
			title : "문자알림번호 변경"
			,items: _FORMITEMS_['FORM17']
			,buttons: {
				center : {
					btnSmsNumChg : { label : "수정" }, 
					btnSmsNumChgClose : { label : "닫기" }
				}
			}
			/*
			,onKeyUp : function(inp, ev, name, value)
			{
				switch(name)
				{
					case"smsPhone":
						var inputValues = PAGE.FORM17.getItemValue("smsPhone");
						if(inputValues != null && inputValues != '')
						{
							var inputValLength = inputValues.length;
							if(inputValLength > 20)
							{
								var tempStr = inputValues.substring(0,20);
								PAGE.FORM17.setItemValue("smsPhone", tempStr);		
							}
						}
					
						break;
				}
			}
			*/
			,onButtonClick : function(btnId) {
						switch (btnId) {
							case "btnSmsNumChg": //문자번호등록
						
							var smsPhone = PAGE.FORM17.getItemValue("smsPhone");
							var contractNum = PAGE.FORM2.getItemValue("contractNum");

							mvno.cmn.ajax(
									ROOT + "/pps/hdofc/custmgmt/PpsCusSmsPhoneUp.json"
									,PAGE.FORM17
									,function(results) {

									var reCode = results.result.code;
									var reNumber = results.result.smsPhone;
	                                mvno.ui.notify(reCode);

	                                if(reCode = "OK")
		                            {
										PAGE.FORM2.setItemValue("smsPhoneNumber",reNumber);
	                                	var smsPhoneStr = href("smsPhone", reNumber);
										PAGE.FORM2.setItemValue("smsPhone",smsPhoneStr);
										mvno.ui.closeWindowById("FORM17", true);
		                            }
	                            });			                
						break;
					case "btnSmsNumChgClose":	
						mvno.ui.closeWindowById("FORM17", true);
		                break;
				}
			}
			
		}// FORM end..
		,FORM18: {
			title : "대리점일괄변경"
		    ,items: _FORMITEMS_['FORM18']
			,buttons: {
			   	center : {
		    		btnAllAgentChg : { label : "수정" }, 
		    		btnAllAgentClose : { label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				    	
						switch (btnId) {
							case "btnAllAgentChg": //대리점일괄변경
								var whereQuery = this.whereQuery;
								var agentId = PAGE.FORM18.getItemValue("searchAgentId");
								var agentSaleId = PAGE.FORM18.getItemValue("searchAgentSaleId");
								var changeReason = PAGE.FORM18.getItemValue("changeReason");

								whereQuery.setItemValue("agentId",agentId);
								whereQuery.setItemValue("agentSaleId",agentSaleId);
								whereQuery.setItemValue("changeReason",changeReason);
								
								mvno.ui.confirm("해당고객의 대리점 및 판매점을 변경하시겠습니까?", function(){
									mvno.cmn.ajax(ROOT + "/pps/hdofc/custmgmt/PpsAllAgentChg.json", whereQuery, function(dataResult) {
										mvno.ui.notify("변경되었습니다.");
										PAGE.GRID1.refresh();
		                                mvno.ui.closeWindowById("FORM18", true);
									})
								});
                
								break;
							case "btnAllAgentClose":	
								mvno.ui.closeWindowById("FORM18", false);
				                break;
						}
			}
			,onKeyUp : function(inp, ev, name, value){

				if(ev.keyCode == 13) {
					switch(name)
					{
						case "searchAgentSaleNm":
							var agentSaleStr  = this.getItemValue("searchAgentSaleNm");
							mvno.cmn.ajax4lov(
									ROOT + "/pps/hdofc/commonmgmt/ppsAgentSaleSelect.json",
									{ 
										searchAgentSaleNm : agentSaleStr
									} 
						      		,PAGE.FORM18
						      		,"searchAgentSaleId");

							var agentOption =  this.getOptions("searchAgentSaleId");
					        
							if(agentOption.length==1)
							{
							}
							else if(agentOption.length==2)
							{
								var selValue = agentOption[1].value;
								this.setItemValue("searchAgentSaleId",selValue);
							}
							break;
							
					}	
				}
			}
			
		}, // FORM end..
		FORM25 : {
			items: _FORMITEMS_['FORM25']
			,buttons: {
				center : {
					btnStayExpirEdit : { label: "수정" },
					btnStayExpirClose : { label: "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				var form = this;
	
				switch (btnId) {
	
					case "btnStayExpirEdit":
						var data = this.getFormData(true);
						var url = ROOT + "/pps/hdofc/custmgmt/PpsCusStayExpirUpdatePop.json";
	
						mvno.cmn.ajax(
								url,
							{
								opCode : "MOD",
								contractNum : data.contractNum,
								stayExpirDt : data.stayExpirDt
							},
							function(result) {
								var result = result.result;
								var code = result.code;
								var msg = result.oRetMsg;
								if(code == "OK") {
									mvno.ui.notify(result.oRetMsg);
									mvno.ui.closeWindowById(PAGE.FORM25);
									
									if(result.oRetCd == '0000'){
										var stayExpirDt = "";
										if(data.stayExpirDt != ""){
											stayExpirDt = data.stayExpirDt.substr(0, 4) + "-" + 
														  data.stayExpirDt.substr(4, 2) + "-" + 
														  data.stayExpirDt.substr(6, 2);
										}
										PAGE.FORM2.setItemValue("stayExpirDtStr", stayExpirDt);
										
										setForm2Title(data.contractNum, result.subscriberNo, result.subStatusNm, result.subStatusDate, result.rechargeStr, result.warFlagStr);
									}
									
								}else {
									mvno.ui.notify(msg);
								}
							},
								
							null
						);	
						break;
					case "btnStayExpirClose":
						mvno.ui.closeWindowById(this);
						break;
	
				}
			}
		}, // FORM end..
		
		//---------------------------------------
		GRID3: {
		    css: {
				height: "150px"
		    },
		    title: "조회결과",
		    header: "사번,이름,성별,나이",
		    columnIds: "usr_id,usr_nm,sex,age",
		    //widthsP : "10,10,10,*",
		    colAlign: "left,left,center,left",
		    colTypes: "ro,ro,ro,ro",
		    colSorting: "str,str,str,str,",
		    multiSelect: true,
		    paging : true,
		    buttons: {
				left: {
			    	btn01: { label: "테스트1" },
			    	btn02: { label: "테스트2" }
				},
				right: {
			    	btn11: { label: "테스트11" },
			    	btn12: { label: "테스트12" }
				}
		    }
		},
		//---------------------------------------
		GRID4: {
		    css: {
				height: "150px"
		    },
		   // title: "조회결과",
		    header: "CMS번호,요청일자,출금요청금액,출금결과코드,충전결과코드,출금구분,충전결과",
		    columnIds: "cmsSeq,recordDate,reqAmount,resCodeName,ktResCd,cmsType,ktResMsg",
		    widths : "90,100,100,100,100,100,328",
		    colAlign: "right,center,right,center,center,left,left",
		    colTypes: "ro,ro,ron,ro,ro,ro,ro",
		    colSorting: "str,str,int,str,str,str,str",
		    paging : true,
			buttons: {
				center: {
					btn41: { label: "맨앞" },
				   	btn42: { label: "이전" },
				   	btn43: { label: "다음" },
				   	btn44: { label: "맨뒤" }
				}
			}
		},
		//---------------------------------------
		GRID5: {
		    css: {
				height: "150px"
		    },
		   // title: "조회결과",
		    header: "번호,충전요청, 충전구분, 충전정보, 결제방식, 충전금액, 실입금액, 충전결과, 충전일자, 충전후 잔액, 충전후만료일, 취소여부, 취소일자,선불카드",
		    columnIds: "seq,reqSrc,reqType,rechargeInfo,rechargeMethod,amount,inAmount,ktResCode,ktResDt,basicRemains,basicExpire,cancelFlag,cancelDt,freeFlagNm",
		    widths : "50,70,150,100,70,70,70,70,80,80,90,70,80,80",
		    colAlign: "left,left,left,left,left,right,right,left,left,right,left,left,left,center",
		    colTypes: "ro,ro,ro,ro,ro,ron,ron,ro,ro,ron,ro,ro,ro,ro",
		    colSorting: "str,str,str,str,str,int,int,str,str,int,str,str,str,str",
		    paging : true,
		  
		    
		},
		//---------------------------------------
		GRID6: {
		    css: {
				height: "150px"
		    },
		   // title: "조회결과",
		    header: "통화구분,계약번호,착신번호,발신시간,사용시간,과금액,통화종류,국제사업자,잔액,국가코드",
		    columnIds: "callGubunName,contractNum,calledNum,startTime,usedTime,charge,callType,telcoPx,remains,korName",
		    widths : "80,80,100,140,70,60,80,100,80,112",
		    colAlign: "left,center,left,left,right,right,left,left,right,left",
		    colTypes: "ro,ro,roXp,ro,ron,ron,ro,ro,ron,ro",
		    colSorting: "str,str,str,str,int,int,str,str,int,str",
		    paging : true
		    ,buttons: {
		    	right : {
			   		btnDnExcel : { label : "엑셀다운로드" }
				}
			}
			,onButtonClick : function(btnId) {
				    	
				switch (btnId) {
					case "btnDnExcel": 
						var btnExcelCount2 = parseInt(PAGE.FORM8.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM8.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM8.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						
						var totCnt = PAGE.GRID6.getRowsNum();
						if(totCnt <= 0){
							mvno.ui.notify("출력건수가 없습니다.");
						   return;
						}
						//alert(totCnt);
						if(totCnt>5000)
						{
							mvno.ui.notify("엑셀출력건수가 5,000건이상이면 시간(수분~수십분)이 걸릴수 있습니다.\n 잠시 기다려주시기 바랍니다.");
						   
						}
						
						var url = "/pps/hdofc/custmgmt/PpsCdrPpTabListExcel.json";
						var searchData =  PAGE.FORM8.getFormData(true);
											
						 mvno.cmn.download(url+"?menuId=PPS1100007",true,{postData:searchData});
						 
						break;
				}
			}
		  
		    
		},
		GRID7: {
		    css: {
				height: "150px"
		    },
		   // title: "조회결과",
		    header : "통화구분,계약번호,사용일자,시작시간,종료시간,누적사용량(K),과금액,기존잔액,데이터잔량,데이터만료일,소속대리점",
		    columnIds : "callGubunName,contractNum,accessDt,startTime,endTime,pktkbyte,charge,remains,dataRemains,dataExpire,agentIdNm",
			widths : "80,80,80,80,80,80,80,80,80,80,*",
			colAlign : "center,center,center,center,center,right,right,right,right,center,left",
			colTypes : "ro,ro,ro,ro,ro,ron,ron,ron,ron,ro,ro",
			colSorting : "str,str,str,str,str,str,int,int,int,int,str",
			paging : true
			,buttons: {
				right : {
			   		btnDnExcel : { label : "엑셀다운로드"}
				}
			}
			,onButtonClick : function(btnId) {
				    	
				switch (btnId) {
					case "btnDnExcel": 
						var btnExcelCount2 = parseInt(PAGE.FORM9.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM9.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM9.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						
						var totCnt = PAGE.GRID7.getRowsNum();
						if(totCnt <= 0){
							mvno.ui.notify("출력건수가 없습니다.");
						   return;
						}
						//alert(totCnt);
						if(totCnt>5000)
						{
							mvno.ui.notify("엑셀출력건수가 5,000건이상이면 시간(수분~수십분)이 걸릴수 있습니다.\n 잠시 기다려주시기 바랍니다.");
						   
						}
						
						var url = "/pps/hdofc/custmgmt/PpsCdrPktTabListExcel.json";
						var searchData =  PAGE.FORM9.getFormData(true);
											
						mvno.cmn.download(url+"?menuId=PPS1100007",true,{postData:searchData});
						 
						break;
				}
			}
		   
		},
		//---------------------------------------
		GRID8: {
		    css: {
				height: "150px"
		    },
		   // title: "조회결과",
		    header: "일련번호,발신번호,착신번호,전송구분,전송결과,전송일자,전송내용",
		    columnIds: "smsSeq,callingNumber,calledNumber,smsResultCode,smsResultNm,smsSendDate,smsMsg",
		    widths : "100,100,100,70,70,150,*",
		    colAlign: "right,left,left,center,left,center,left",
		    colTypes: "ro,roXp,roXp,ro,ro,ro,ro",
		    colSorting: "int,str,str,str,str,str,str",
		    paging : true
		   
		},
		//-------------------------------------------------------------------------------------
		GRID9: {
		    css: {
				height: "150px"
		    },
		   // title: "조회결과",
		    header : "번호,요청구분,요청자,처리구분,처리상태,요청일자,요청내용,처리내용,접수자,처리자,처리일자,계약번호",
			columnIds : "diaryId,reqTypeNm,reqUserName,resStatusNm,statusNm,regDate,reqBodyShow,resBodyShow,regAdminNm,resAdminNm,resEndDate,contractNum",
			widths : "50,70,70,70,70,75,115,115,70,70,75,*",
			colAlign : "right,center,left,center,center,center,left,left,left,left,center,center",
			colTypes : "link,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
		    multiSelect: true,
		    paging : true,
			/*
		    buttons: {
				
				right: {
					btnReg: { label: "상담등록" },
			    	btnDel: { label: "상담삭제" }
				}
		    },
		    */
		    onRowSelect : function(rowId, celInd) {
				//var rowData = this.getRowData(rowId);
			},
/*
			onRowDblClicked : function(rowId, celInd) {
	            var rowData = this.getRowData(rowId);

	            mvno.ui.createForm("FORM10_2");
				PAGE.FORM10_2.setFormData(rowData);

				PAGE.FORM10_2.disableItem("reqUserGubun");
				PAGE.FORM10_2.disableItem("reqUserName");
				PAGE.FORM10_2.disableItem("reqUserNameCheck");
				PAGE.FORM10_2.disableItem("reqType");
				PAGE.FORM10_2.disableItem("reqBody");

				PAGE.FORM10_2.setValidation("resBody", "NotEmpty"); 
				
				var data = PAGE.FORM2.getFormData(true);

				var title = "상담등록 - 고객명 : "+data.subLinkName+" CTN : "+data.subscriberNo;
				mvno.ui.updateSection("FORM10_2", "title", title);

				PAGE.FORM10_2.setItemValue("contractNumber", data.contractNum);
								
	            mvno.ui.popupWindowById("FORM10_2", "고객상담등록화면", 620, 420, {
	                onClose: function(win) {
	                	if (! PAGE.FORM10_2.isChanged()) return true;
	                	mvno.ui.confirm("CCMN0005", function(){
	                		win.closeForce();
	                	});
	                }
                });
	    	},
	    	*/
		 	onButtonClick : function(btnId) {

				switch (btnId) {

					case "btnReg":
						mvno.ui.createForm("FORM10");
						PAGE.FORM10.setFormData(); 

						var data = PAGE.FORM2.getFormData(true);

						var title = "상담등록 - 고객명 : "+data.subLinkNameMsk+" CTN : "+data.subscriberNoMsk;
						mvno.ui.updateSection("FORM10", "title", title);

						PAGE.FORM10.setItemValue("contractNumber", data.contractNum);
						PAGE.FORM10.setItemValue("contractNum", data.contractNum);
						PAGE.FORM10.setItemValue("subLinkName", data.subLinkName);
						PAGE.FORM10.setItemValue("agentId", data.agentId);

						PAGE.FORM10.disableItem("resBody");
											
			            mvno.ui.popupWindowById("FORM10", "고객상담등록화면", 620, 460, {
			                onClose: function(win) {
			                	if (! PAGE.FORM10.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });			
						break;
					case "btnDel":

						var rowData = PAGE.GRID9.getSelectedRowData();

						mvno.cmn.ajax4confirm(
								"CCMN0003", 
								ROOT + "/pps/hdofc/custmgmt/PpsCustomerDiaryWriteChange.json",
								{
									diaryId : rowData[0].diaryId, 
									opCode: "DEL"
	                       		}, 
	                       		function(results) {
		                            //mvno.ui.notify("NCMN0003");
		                            var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
	
									if(retCode == "0000") {

										if(PAGE.GRID9)
										{
											PAGE.GRID9.refresh();	
										}
									}
	                       		});
						
						break;

					}
				}
		    
		},
		//-------------------------------------------------------------------------------------
		GRID10: {
		    css: {
				height: "150px"
		    },
		   // title: "조회결과",
		    header: "서식지종류,파일명,등록일자,처리자,삭제,메모,계약번호히든,일련번호히든,경로히든,암호화여부히든",
		    columnIds: "imgGubunNm,imgFileStr,recDt,recIdNm,delBtn,memo,contractNum,imgSeq,imgPath,encFlag",
		    widths : "120,150,120,100,70,*,0,0,0,0",
		    colAlign: "left,left,left,left,center,left,center,center,left,center",
		    colTypes: "ro,link,roXd,ro,link,ro,ro,ro,ro,ro",
		    colSorting: "str,str,str,str,str,str,str,str,str,str",
		    paging : true
		   
		}
		//-------------------------------------------------------------------------------------
		,FORM22:	{
			items: _FORMITEMS_['FORM22'],
	        buttons: {
	            center: {
	            	 btnSave: { label: "확인" },
	                btnClose: { label: "닫기" }
	            }
	        },
	        onChange : function (name, value){
	            var form = this;
	        },
	        onButtonClick : function(btnId) {
	
	            var form = this; // 혼란방지용으로 미리 설정 (권고)
	            var fileLimitCnt = 1;
	            PAGE.FORM22.setItemValue("opCode","REG");
	
	            var contractNum = PAGE.FORM2.getItemValue("contractNum");
	            
	            switch (btnId) {
	               
	                case "btnSave":
	                	
	                	var data22 = PAGE.FORM22.getFormData(true);
	                	
	                	var url = ROOT + "/pps/filemgmt/procPpsCustomerImgeInsertUpdate.json";
	                     //console.log("data22",data22);
	                	  mvno.cmn.ajax(url, form, function(results) {
	                		  var result = results.result;
								var retCode = result.oRetCode;
								var msg = result.oRetMsg;
								mvno.ui.notify(msg);
	
								if(retCode == "0000") {
	
									 mvno.ui.closeWindowById(form, true);  
				                     mvno.ui.notify("NCMN0015");
				                     PAGE.FORM22.clear();
				                     customerInfoData(contractNum);
								}
	                		 
	                         
	                      });
	
	                break;	
	               
	                case "btnClose" :  
	                    mvno.ui.closeWindowById(form, true);
	                            
	                    
	                    
	                    break;
	              
	               
	            }
	        },
	        onValidateMore : function (data){
	        	//
	        	
	        	var uploaders = this.getUploader("file_upload1");
	        	//console.log(uploaders);
	        	if(!data.imgGubun){
	        		this.pushError("data.imgGubun","이미지 구분",["ICMN0001"]);
	
	        	}
	        	
	        	else if(!data.file_upload1_count&&data.file_upload1_count==0){
	        		
	        		this.pushError("data.upload1","파일",["ICMN0001"]);
	        	}
	        	
	        }
		}// FORM end..
		,FORM23:	{
			items: _FORMITEMS_['FORM23'],
	        buttons: {
	            center: {
	            	 btnSave: { label: "저장" },
	                btnClose: { label: "닫기" }
	            }
	        },
	        onChange : function (name, value){
	            var form = this;
	        },
	        onButtonClick : function(btnId) {
	
	            var form = this; // 혼란방지용으로 미리 설정 (권고)
	            var fileLimitCnt = 1;
	            PAGE.FORM23.setItemValue("opCode","REG");
	
	            var contractNum = PAGE.FORM2.getItemValue("contractNum");
	            
	            switch (btnId) {
	               
	                case "btnSave":
	                	
	                	var data23 = PAGE.FORM23.getFormData(true);
	                	
	                	var url = ROOT + "/pps/filemgmt/procPpsRealPayInfoUpdate.json";
	                     //console.log("data22",data22);
	                	  mvno.cmn.ajax(url, form, function(results) {
	                		  var result = results.result;
								var retCode = result.oRetCode;
								var msg = result.oRetMsg;

								if(retCode == "0000") {
									 cmsFormRefresh(contractNum);
									 mvno.ui.closeWindowById(form, true);  
				                     mvno.ui.alert("회원증빙이 완료되었습니다.");
				                     PAGE.FORM23.clear(); 
								}else{
									mvno.ui.alert(msg);
								}
	                		 
	                         
	                      });
	
	                break;	
	               
	                case "btnClose" :  
	                    mvno.ui.closeWindowById(form, true);
	                            
	                    
	                    
	                    break;
	              
	               
	            }
	        },
	        onValidateMore : function (data){
	        	//
	        	
	        	var uploaders = this.getUploader("file_upload1");
	        	//console.log(uploaders);
	        	if(!data.regTypePop){
	        		this.pushError("data.regTypePop","증빙서류",["ICMN0001"]);
	
	        	}
	        	
	        	else if(!data.file_upload1_count&&data.file_upload1_count==0){
	        		
	        		this.pushError("data.upload1","파일",["ICMN0001"]);
	        	}
	        	
	        }
		}// FORM end..
	
	
	};

	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			
		onOpen : function() {

			var contractNum = getParams("contractNum");
			customerInfoData(contractNum);

		}

	};
		
</script>