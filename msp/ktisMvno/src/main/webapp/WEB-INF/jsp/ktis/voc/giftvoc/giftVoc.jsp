<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
  var maskingYn = '${maskingYn}';
  var startDate = '${srchStrtDt}';
  var endDate = '${srchEndDt}';

  var DHX = {
    FORM1 : {
      items : [
        {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 60, blockOffset: 0}
        ,{type: "block", list: [
          {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchStrtDt", label: "접수일자", value: startDate, calendarPosition: "bottom", width: 100, offsetLeft: 10}
          ,{type: "newcolumn"}
          ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchEndDt", label: "~", value: endDate, calendarPosition: "bottom", labelAlign: "center", labelWidth: 10, offsetLeft: 5, width:100}
          ,{type: "newcolumn"}
          ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "procStrtDt", label: "처리일자", calendarPosition: "bottom", width: 100, offsetLeft: 50}
          ,{type: "newcolumn"}
          ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "procEndDt", label: "~", calendarPosition: "bottom", labelAlign: "center", labelWidth: 10, offsetLeft: 5, width: 100}
        ]}
        ,{type: "block", list: [
          {type: "calendar", dateFormat: "%Y-%m", serverDateFormat: "%Y%m", name: "planStrtDt", label: "지급예정월", calendarPosition: "bottom", width: 100, offsetLeft: 10}
          ,{type: "newcolumn"}
          ,{type: "calendar", dateFormat: "%Y-%m", serverDateFormat: "%Y%m", name: "planEndDt", label: "~", calendarPosition: "bottom", labelAlign: "center", labelWidth: 10, offsetLeft: 5, width:100}
          ,{type: "newcolumn"}
          ,{type: "select", label: "검색구분", name: "searchGbn", width: 100, offsetLeft: 50}
          ,{type: "newcolumn"}
          ,{type: "input", name: "searchName", disabled: true, width: 115}
        ]}
        ,{type: "block", list: [
          {type: "select", label: "담당부서", name: "vocMngOrgn", width: 120, offsetLeft: 10}
          ,{type: "newcolumn"}
          ,{type: "select", label: "답변상태", name: "ansStat", width: 120, offsetLeft: 30}
          ,{type: "newcolumn"}
          ,{type: "select", label: "VOC구분", name: "vocType", width: 120, offsetLeft: 30}
        ]}
        ,{type: "hidden", name: "DWNLD_RSN", value:""} //엑셀다운로드 사유
        ,{type: "hidden", name: "btnCount1", value: "0"}       // 조회 버튼 다중클릭 방지
        ,{type: "hidden", name: "btnExcelCount1", value: "0"}  // 다운로드 버튼 다중클릭 방지
        ,{type: "button", name: "btnSearch", value: "조회", className: "btn-search3"}
      ]
      ,onValidateMore: function(data){
        var form = this;

        if(data.srchStrtDt > data.srchEndDt){
          this.pushError("srchEndDt", "접수일자", "시작일자가 종료일자보다 클 수 없습니다.");
        }

        if(data.procStrtDt > data.procEndDt){
          this.pushError("procEndDt", "처리일자", "시작일자가 종료일자보다 클 수 없습니다.");
        }

        if(data.planStrtDt > data.planEndDt){
            this.pushError("planEndDt", "지급예정월", "시작일자가 종료일자보다 클 수 없습니다.");
        }

        if(data.searchGbn != "" && data.searchName.trim() == ""){
          this.pushError("searchName", "검색어", "검색어를 입력하세요.");
        }

        form.setItemValue("btnCount1", 0);      // 초기화
        form.setItemValue("btnExcelCount1", 0); // 초기화
	    }
      ,onButtonClick: function(btnId){
        var form = this;

        switch(btnId){

          case "btnSearch" : // 조회

            var btnCount2 = parseInt(form.getItemValue("btnCount1", "")) + 1;
            form.setItemValue("btnCount1", btnCount2);
            if (form.getItemValue("btnCount1", "") != "1") return;

            PAGE.GRID1.list(ROOT + "/voc/giftvoc/giftVocList.json", form, {
              callback: function () {
                form.resetValidateCss();
                PAGE.FORM2.clear();
              }
            });
			      break;
        }
	    }
      ,onChange: function(name, value){
        var form = this;

        switch(name) {
          case "searchGbn" : // 검색구분
            form.setItemValue("searchName", "");
            if (mvno.cmn.isEmpty(value)) {
              form.setItemValue("srchStrtDt", startDate);
              form.setItemValue("srchEndDt", endDate);
              form.enableItem("srchStrtDt");
              form.enableItem("srchEndDt");
              
              form.disableItem("searchName");
            } else {
              form.disableItem("srchStrtDt");
              form.disableItem("srchEndDt");
              form.setItemValue("srchStrtDt", "");
              form.setItemValue("srchEndDt", "");
              
              form.enableItem("searchName");
            }
            break;
        }
      }
    } // end of FORM1 ------------------------------------
    ,GRID1 : {
      css: {height : "200px"}
      ,title: "VOC 목록"
      ,header: "No,대외기관언급여부,등록일,접수상태,VOC 구분,계약번호,고객명,CTN,개통일,최초 요금제,현재 요금제,답변상태,접수자,추천직원번호,담당부서,지급예정월,수정일,수정자,시퀀스" // 19
      ,columnIds: "rnum,extnlOrgnRefYn,regstDttm,vocStat,vocType,contractNum,subLinkName,subscriberNo,lstComActvDate,fstRateNm,lstRateNm,ansStat,regstNm,recommendInfo,vocMngOrgn,ansPayPlanMon,rvisnDttm,rvisnNm,giftVocSeq" // 19
      ,widths: "50,120,100,100,100,100,180,100,100,200,200,100,100,100,100,100,100,100,0" // 19
	    ,colAlign: "center,center,center,center,center,center,left,center,center,left,left,center,center,center,center,center,center,center,center" // 19
      ,colTypes: "ro,ro,roXd,ro,ro,ro,ro,roXp,roXd,ro,ro,ro,ro,ro,ro,roXdm,roXd,ro,ro" // 19
      ,colSorting: "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 19
      ,hiddenColumns: [18]
      ,paging: true
      ,pageSize: 20
      ,buttons: {
        hright: {
          btnExcel: {label: "자료생성"}
        }
      }
      ,checkSelectedButtons: ["btnHist", "btnExcelDetail"]
      ,onRowSelect: function(rId, cId){
        var grid = this;
        var sdata = grid.getSelectedRowData();

        // 더블클릭 시 rowSelect 두번 호출 방지
        if(sdata.giftVocSeq == PAGE.FORM2.getItemValue("giftVocSeq")){
            return;
        }

        mvno.cmn.ajax(ROOT + "/voc/giftvoc/giftVocDtl.json", {giftVocSeq : sdata.giftVocSeq}, function(resultData) {
          PAGE.FORM2.clear();
          var vocInfo = resultData.result.giftVocVo;
          if(vocInfo != null){
            PAGE.FORM2.setItemValue("vocDesc", vocInfo.vocDesc);
            PAGE.FORM2.setItemValue("custNm", vocInfo.custNm);
            PAGE.FORM2.setItemValue("vocCtn", vocInfo.vocCtn);
            PAGE.FORM2.setItemValue("promNm", vocInfo.promNm);
            PAGE.FORM2.setItemValue("ansStat", vocInfo.ansStat);
            PAGE.FORM2.setItemValue("extnlOrgnRefYn", vocInfo.extnlOrgnRefYn);
            PAGE.FORM2.setItemValue("ansPayRsn", vocInfo.ansPayRsn);
            PAGE.FORM2.setItemValue("ansPayAmt", vocInfo.ansPayAmt);
            PAGE.FORM2.setItemValue("ansPayPlanMon", vocInfo.ansPayPlanMon);
            PAGE.FORM2.setItemValue("ansRmk", vocInfo.ansRmk);
            PAGE.FORM2.setItemValue("giftVocSeq", vocInfo.giftVocSeq);
          }
        });
      }
      ,onButtonClick: function(btnId){
        var grid = this;

        switch (btnId) {

          case "btnExcel" : // 엑셀다운로드

            if(grid.getRowsNum() == 0) {
                mvno.ui.alert("데이터가 존재하지 않습니다.");
                return;
            }

            var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
            PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
            if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록

            mvno.cmn.downloadCallback(function(result) {
              PAGE.FORM1.setItemValue("DWNLD_RSN",result);
              mvno.cmn.ajax(ROOT + "/voc/giftvoc/getGiftVocListExcelDownload.json?menuId=VOC1001080", PAGE.FORM1.getFormData(true), function(result){
                mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
              });
            });

            PAGE.FORM1.setItemValue("btnCount1", 0);      // 초기화
            PAGE.FORM1.setItemValue("btnExcelCount1", 0); // 초기화

            break;

        }
	    }
	  } // end of GRID1 ------------------------------------
    ,FORM2 : {
      title: "VOC 상세내역"
      ,items: [
        {type: 'settings', position: 'label-left', labelAlign: "left", labelWidth: 50}
        ,{type: "block", list: [
          {type: 'input', name: 'vocDesc', label: 'VOC내용', rows: '8', width: 900, offsetLeft: 10, disabled: true, position: 'label-top'}
        ]}
        ,{type: "block", list: [
          {type: 'input', name: 'custNm', label: '민원인', width: 100, offsetLeft: 10, disabled: true}
          ,{type: "newcolumn"}
          ,{type: 'input', name: 'vocCtn', label: 'CTN', width: 100, offsetLeft: 15, disabled: true, labelWidth: 30}
          ,{type: "newcolumn"}
          ,{type: 'input', name: 'promNm', label: '프로모션명', width: 200, offsetLeft: 15, disabled: true, labelWidth: 60}
          ,{type: "newcolumn"}
          ,{type: 'checkbox', name: 'extnlOrgnRefYn', label: '대외기관언급여부', width: 20, offsetLeft: 15, disabled: true, checked:false, labelWidth: 100}
        ]}
        ,{type: "block", list: [
          {type: 'input', name: 'ansPayRsn', label: '지급/미지급 사유', width: 200, offsetLeft: 10, disabled: true, labelWidth: 100}
          ,{type: "newcolumn"}
          ,{type: 'select', name: 'ansStat', label: '답변상태', width: 100, offsetLeft: 15, disabled: true}
          ,{type: "newcolumn"}
          ,{type: 'input', name: 'ansPayAmt', label: '금액', width: 100, offsetLeft: 15, disabled: true, labelWidth: 30, validate:"ValidInteger", numberFormat: "0,000,000,000"}
          ,{type: "newcolumn"}
          ,{type: "calendar", dateFormat: "%Y-%m", serverDateFormat: "%Y%m", name: 'ansPayPlanMon', label: '지급예정월', width: 100, offsetLeft: 15, disabled: true, labelWidth: 60}
        ]}
        ,{type: "block", list: [
          {type: 'input', name: 'ansRmk', label: '비고', width: 300, offsetLeft: 10, disabled: true}
        ]}
        ,{type: 'hidden', name: 'giftVocSeq'}
      ]
      ,buttons: {
        right: {
         btnModVoc: {label: "VOC정보 수정"}
         ,btnModAns: {label: "답변 생성"}
        }
      }
      ,onRowDblClicked: function(rowId, celInd){
          // this.callEvent('onButtonClick', ['btnModVoc']);
      }
      ,onButtonClick: function(btnId){
        var form = this;

        switch(btnId) {
          case "btnModVoc" : // VOC정보 수정
            var sdata = PAGE.GRID1.getSelectedRowData();
            if(!sdata){
                mvno.ui.alert("선택한 데이터가 없습니다.");
                return;
            }

            mvno.ui.createForm("FORM3");
            PAGE.FORM3.clear();
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0034",orderBy:"etc6"}, PAGE.FORM3, "vocStat"); // 접수상태
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0030",orderBy:"etc6"}, PAGE.FORM3, "vocMngOrgn"); // 담당부서
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0033",orderBy:"etc6"}, PAGE.FORM3, "vocType"); // VOC구분

            mvno.cmn.ajax(ROOT + "/voc/giftvoc/giftVocDtl.json", {giftVocSeq : sdata.giftVocSeq}, function(resultData) {
              var vocInfo = resultData.result.giftVocVo;
              if(vocInfo != null){
                PAGE.FORM3.setItemValue("subLinkName", vocInfo.subLinkName);
                PAGE.FORM3.setItemValue("contractNum", vocInfo.contractNum);
                PAGE.FORM3.setItemValue("subscriberNo", vocInfo.subscriberNo);
                PAGE.FORM3.setItemValue("lstComActvDate", vocInfo.lstComActvDate);
                PAGE.FORM3.setItemValue("reqInDay", vocInfo.reqInDay);
                PAGE.FORM3.setItemValue("fstRateNm", vocInfo.fstRateNm);
                PAGE.FORM3.setItemValue("lstRateNm", vocInfo.lstRateNm);
                PAGE.FORM3.setItemValue("subStatus", vocInfo.subStatus);
                PAGE.FORM3.setItemValue("recommendInfo", vocInfo.recommendInfo);
                PAGE.FORM3.setItemValue("onOffType", vocInfo.onOffType);
                PAGE.FORM3.setItemValue("openMarketReferer", vocInfo.openMarketReferer);

                PAGE.FORM3.setItemValue("custNm", vocInfo.custNm);
                PAGE.FORM3.setItemValue("promNm", vocInfo.promNm);
                PAGE.FORM3.setItemValue("giftType", vocInfo.giftType);
                PAGE.FORM3.setItemValue("giftAmt", vocInfo.giftAmt);
                PAGE.FORM3.setItemValue("vocStat", vocInfo.vocStat);
                PAGE.FORM3.setItemValue("vocCtn", vocInfo.vocCtn);
                PAGE.FORM3.setItemValue("divisn", vocInfo.divisn);
                PAGE.FORM3.setItemValue("rmk", vocInfo.rmk);
                PAGE.FORM3.setItemValue("vocMngOrgn", vocInfo.vocMngOrgn);
                PAGE.FORM3.setItemValue("vocType", vocInfo.vocType);
                PAGE.FORM3.setItemValue("vocReqDt", vocInfo.vocReqDt);
                PAGE.FORM3.setItemValue("userId", vocInfo.userId);
                PAGE.FORM3.setItemValue("recommId", vocInfo.recommId);
                PAGE.FORM3.setItemValue("recommContractNum", vocInfo.recommContractNum);
                PAGE.FORM3.setItemValue("extnlOrgnRefYn", vocInfo.extnlOrgnRefYn);
                PAGE.FORM3.setItemValue("vocDesc", vocInfo.vocDesc);
                PAGE.FORM3.setItemValue("giftVocSeq", vocInfo.giftVocSeq);


                // 답변 상태가 답변완료, 지급불가, 지급예정 인 경우 수정 불가
                if (vocInfo.ansStat === 'D' || vocInfo.ansStat === 'E2' || vocInfo.ansStat === 'E3') {
                  PAGE.FORM3.disableItem("promNm");
                  PAGE.FORM3.disableItem("giftType");
                  PAGE.FORM3.disableItem("giftAmt");
                  PAGE.FORM3.disableItem("vocStat");
                  PAGE.FORM3.disableItem("divisn");
                  PAGE.FORM3.disableItem("rmk");
                  PAGE.FORM3.disableItem("vocMngOrgn");
                  PAGE.FORM3.disableItem("vocType");
                  PAGE.FORM3.disableItem("vocReqDt");
                  PAGE.FORM3.disableItem("recommContractNum");
                  PAGE.FORM3.disableItem("extnlOrgnRefYn");
                  PAGE.FORM3.disableItem("vocDesc");
                  PAGE.FORM3.disableItem("custNm");
                  PAGE.FORM3.disableItem("vocCtn");
                  PAGE.FORM3.disableItem("userId");
                  PAGE.FORM3.disableItem("recommId");
                } else {
                  PAGE.FORM3.enableItem("promNm");
                  PAGE.FORM3.enableItem("giftType");
                  PAGE.FORM3.enableItem("giftAmt");
                  PAGE.FORM3.enableItem("vocStat");
                  PAGE.FORM3.enableItem("divisn");
                  PAGE.FORM3.enableItem("rmk");
                  PAGE.FORM3.enableItem("vocMngOrgn");
                  PAGE.FORM3.enableItem("vocType");
                  PAGE.FORM3.enableItem("vocReqDt");
                  PAGE.FORM3.enableItem("recommContractNum");
                  PAGE.FORM3.enableItem("extnlOrgnRefYn");
                  PAGE.FORM3.enableItem("vocDesc");

                  // 마스킹권한자만 민감정보 수정 가능
                  if (maskingYn === 'Y') {
                    PAGE.FORM3.enableItem("custNm");
                    PAGE.FORM3.enableItem("vocCtn");
                    PAGE.FORM3.enableItem("userId");
                    PAGE.FORM3.enableItem("recommId");
                  } else {
                    PAGE.FORM3.disableItem("custNm");
                    PAGE.FORM3.disableItem("vocCtn");
                    PAGE.FORM3.disableItem("userId");
                    PAGE.FORM3.disableItem("recommId");
                  }
                }
              }
            });

            mvno.ui.popupWindowById("FORM3", "VOC 등록", 800, 500, {
              onClose: function(win) {
                if (! PAGE.FORM3.isChanged()) return true;
                mvno.ui.confirm("CCMN0005", function(){
                  win.closeForce();
                });
              }
            });

            break;

          case "btnModAns" : // 답변 생성
            var sdata = PAGE.GRID1.getSelectedRowData();
            if(!sdata){
                mvno.ui.alert("선택한 데이터가 없습니다.");
                return;
            }

            mvno.ui.createForm("FORM4");
            PAGE.FORM4.clear();
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0034",orderBy:"etc6"}, PAGE.FORM4, "vocStat"); // 접수상태
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0030",orderBy:"etc6"}, PAGE.FORM4, "vocMngOrgn"); // 담당부서
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0033",orderBy:"etc6"}, PAGE.FORM4, "vocType"); // VOC구분
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0032",orderBy:"etc6"}, PAGE.FORM4, "ansStat"); // 답변상태

            mvno.cmn.ajax(ROOT + "/voc/giftvoc/giftVocDtl.json", {giftVocSeq : sdata.giftVocSeq}, function(resultData) {
              var vocInfo = resultData.result.giftVocVo;
              if(vocInfo != null){
                PAGE.FORM4.setItemValue("subLinkName", vocInfo.subLinkName);
                PAGE.FORM4.setItemValue("contractNum", vocInfo.contractNum);
                PAGE.FORM4.setItemValue("subscriberNo", vocInfo.subscriberNo);
                PAGE.FORM4.setItemValue("lstComActvDate", vocInfo.lstComActvDate);
                PAGE.FORM4.setItemValue("reqInDay", vocInfo.reqInDay);
                PAGE.FORM4.setItemValue("fstRateNm", vocInfo.fstRateNm);
                PAGE.FORM4.setItemValue("lstRateNm", vocInfo.lstRateNm);
                PAGE.FORM4.setItemValue("subStatus", vocInfo.subStatus);
                PAGE.FORM4.setItemValue("recommendInfo", vocInfo.recommendInfo);
                PAGE.FORM4.setItemValue("onOffType", vocInfo.onOffType);
                PAGE.FORM4.setItemValue("openMarketReferer", vocInfo.openMarketReferer);

                PAGE.FORM4.setItemValue("custNm", vocInfo.custNm);
                PAGE.FORM4.setItemValue("promNm", vocInfo.promNm);
                PAGE.FORM4.setItemValue("giftType", vocInfo.giftType);
                PAGE.FORM4.setItemValue("giftAmt", vocInfo.giftAmt);
                PAGE.FORM4.setItemValue("vocStat", vocInfo.vocStat);
                PAGE.FORM4.setItemValue("vocCtn", vocInfo.vocCtn);
                PAGE.FORM4.setItemValue("divisn", vocInfo.divisn);
                PAGE.FORM4.setItemValue("rmk", vocInfo.rmk);
                PAGE.FORM4.setItemValue("vocMngOrgn", vocInfo.vocMngOrgn);
                PAGE.FORM4.setItemValue("vocType", vocInfo.vocType);
                PAGE.FORM4.setItemValue("vocReqDt", vocInfo.vocReqDt);
                PAGE.FORM4.setItemValue("userId", vocInfo.userId);
                PAGE.FORM4.setItemValue("recommId", vocInfo.recommId);
                PAGE.FORM4.setItemValue("recommContractNum", vocInfo.recommContractNum);
                PAGE.FORM4.setItemValue("extnlOrgnRefYn", vocInfo.extnlOrgnRefYn);
                PAGE.FORM4.setItemValue("vocDesc", vocInfo.vocDesc);
                PAGE.FORM4.setItemValue("giftVocSeq", vocInfo.giftVocSeq);

                PAGE.FORM4.setItemValue("ansRmk", vocInfo.ansRmk);
                PAGE.FORM4.setItemValue("ansPayRsn", vocInfo.ansPayRsn);
                PAGE.FORM4.setItemValue("ansPayItem", vocInfo.ansPayItem);
                PAGE.FORM4.setItemValue("ansPayAmt", vocInfo.ansPayAmt);
                PAGE.FORM4.setItemValue("ansPayPlanMon", vocInfo.ansPayPlanMon);
                PAGE.FORM4.setItemValue("ansStat", vocInfo.ansStat);
              }
            });

            mvno.ui.popupWindowById("FORM4", "답변 등록", 800, 600, {
              onClose: function(win) {
                if (! PAGE.FORM4.isChanged()) return true;
                mvno.ui.confirm("CCMN0005", function(){
                  win.closeForce();
                });
              }
            });

            break;
        }
      }
    } // end of FORM2 ------------------------------------
    ,FORM3 : {
      items: [
        {type: "block", list: [
          {type: "fieldset", label: "고객정보", inputWidth:750, list:[
            {type: 'settings', position: 'label-left', labelAlign: "left", labelWidth: 60}
            ,{type:"block", list:[
              {type: 'input', label: '고객명', name: 'subLinkName', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '계약번호', name: 'contractNum', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: 'CTN', name: 'subscriberNo', width:100, offsetLeft:15, disabled:true}
            ]}
            ,{type:"block", list:[
              {type: 'input', label: '개통일자', name: 'lstComActvDate', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '개통신청일', name: 'reqInDay', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '최초요금제', name: 'fstRateNm', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '현재요금제', name: 'lstRateNm', width:100, offsetLeft:15, disabled:true}
            ]}
            ,{type:"block", list:[
              {type: 'input', label: '상태', name: 'subStatus', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '추천인정보', name: 'recommendInfo', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '모집경로', name: 'onOffType', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '유입경로', name: 'openMarketReferer', width:100, offsetLeft:15, disabled:true}
            ]}
          ]},
          {type: "fieldset", label: "민원정보", inputWidth:750, list:[
            {type: 'settings', position: 'label-left', labelAlign: "left", labelWidth: 60}
            ,{type:"block", list:[
              {type: 'input', label: '민원인', name: 'custNm', width:100, maxLength:20, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '프로모션명', name: 'promNm', width:100, offsetLeft:15, maxLength:80, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '상품권종류', name: 'giftType', width:100, offsetLeft:15, maxLength:20, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '금액', name: 'giftAmt', width:100, offsetLeft:15, maxLength:7, disabled:true, validate:"ValidInteger", numberFormat: "0,000,000,000"}
            ]}
            ,{type:"block", list:[
              {type: 'select', label: '접수상태', name: 'vocStat', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '인입CTN', name: 'vocCtn', width:100, offsetLeft:15, maxLength:11, disabled:true, validate:"ValidInteger"}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '구분', name: 'divisn', width:100, offsetLeft:15, maxLength:30, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '비고', name: 'rmk', width:100, offsetLeft:15, maxLength:100, disabled:true}
            ]}
            ,{type:"block", list:[
              {type: 'select', label: '담당부서', name: 'vocMngOrgn', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'select', label: 'VOC구분', name: 'vocType', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'calendar', dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", label: '신청일자', name: 'vocReqDt', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'checkbox', label: '대외기관 언급여부', name: 'extnlOrgnRefYn', width:20, offsetLeft:15, disabled:true, checked:false, labelWidth: 100}
            ]}
            ,{type:"block", list:[
              {type: 'input', label: '회원ID', name: 'userId', width:100, maxLength:20, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '추천인ID', name: 'recommId', width:100, offsetLeft:15, maxLength:20, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '추천인 계약번호', name: 'recommContractNum', width:100, offsetLeft:15, maxLength:11, disabled:true, labelWidth: 100, validate:"ValidInteger"}
            ]}
            ,{type:"block", list:[
              {type: 'input', label: 'VOC내용', name: 'vocDesc', rows: '8', width: 600, maxLength:500, disabled:true}
            ]}
          ]},
        ]}
        ,{type: 'hidden', name: 'giftVocSeq'}
      ]
      ,buttons: {
        center: {
          btnSave: {label: "저장"}
          ,btnCancel: {label: "닫기"}
        }
      }
      ,onButtonClick: function(btnId){
        var form = this;

        switch(btnId) {
          case "btnSave" :
            if (!this.validate()) return;
            var idata = form.getFormData(true);
            mvno.cmn.ajax(ROOT + "/voc/giftvoc/updateGiftVoc.json", idata, function(result) {
              if (result.result.code == "OK") {
                mvno.ui.closeWindowById(form, true);
                mvno.ui.notify("NCMN0001");
                PAGE.GRID1.refresh();
              } else {
                mvno.ui.alert(result.result.msg);
              }
            });
            break;

          case "btnCancel" :
            mvno.ui.closeWindowById(form, true);
            break;
        }
      }
    } // end of FORM3 ------------------------------------
    ,FORM4 : {
      items: [
        {type: "block", list: [
          {type: "fieldset", label: "고객정보", inputWidth:750, list:[
            {type: 'settings', position: 'label-left', labelAlign: "left", labelWidth: 60}
            ,{type:"block", list:[
              {type: 'input', label: '고객명', name: 'subLinkName', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '계약번호', name: 'contractNum', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: 'CTN', name: 'subscriberNo', width:100, offsetLeft:15, disabled:true}
            ]}
            ,{type:"block", list:[
              {type: 'input', label: '개통일자', name: 'lstComActvDate', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '신청일자', name: 'reqInDay', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '최초요금제', name: 'fstRateNm', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '현재요금제', name: 'lstRateNm', width:100, offsetLeft:15, disabled:true}
            ]}
            ,{type:"block", list:[
              {type: 'input', label: '상태', name: 'subStatus', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '추천인정보', name: 'recommendInfo', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '모집경로', name: 'onOffType', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '유입경로', name: 'openMarketReferer', width:100, offsetLeft:15, disabled:true}
            ]}
          ]},
          {type: "fieldset", label: "민원정보", inputWidth:750, list:[
            {type: 'settings', position: 'label-left', labelAlign: "left", labelWidth: 60}
            ,{type:"block", list:[
              {type: 'input', label: '민원인', name: 'custNm', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '프로모션명', name: 'promNm', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '상품권종류', name: 'giftType', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '금액', name: 'giftAmt', width:100, offsetLeft:15, disabled:true, validate:"ValidInteger", numberFormat: "0,000,000,000"}
            ]}
            ,{type:"block", list:[
              {type: 'select', label: '접수상태', name: 'vocStat', width:100, disabled:true, required:true, validate:"NotEmpty"}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '인입CTN', name: 'vocCtn', width:100, offsetLeft:15, disabled:true, validate:"ValidInteger"}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '구분', name: 'divisn', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '비고', name: 'rmk', width:100, offsetLeft:15, disabled:true}
            ]}
            ,{type:"block", list:[
              {type: 'select', label: '담당부서', name: 'vocMngOrgn', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'select', label: 'VOC구분', name: 'vocType', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'calendar', dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", label: '신청일자', name: 'vocReqDt', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'checkbox', label: '대외기관 언급여부', name: 'extnlOrgnRefYn', width:20, offsetLeft:15, disabled:true, checked:false, labelWidth: 100}
            ]}
            ,{type:"block", list:[
              {type: 'input', label: '회원ID', name: 'userId', width:100, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '추천인ID', name: 'recommId', width:100, offsetLeft:15, disabled:true}
              ,{type: "newcolumn"}
              ,{type: 'input', label: '추천인 계약번호', name: 'recommContractNum', width:100, offsetLeft:15, disabled:true, labelWidth: 100}
            ]}
            ,{type:"block", list:[
              {type: 'input', label: 'VOC내용', name: 'vocDesc', rows: '8', width: 600, disabled:true}
            ]}
          ]},
          {type: "fieldset", label: "답변내용", inputWidth:750, list:[
            {type: 'settings', position: 'label-left', labelAlign: "left", labelWidth: 60}
            ,{type:"block", list:[
                {type: 'input', label: '기타 특이사항', name: 'ansRmk', width:200, maxLength:100, labelWidth: 95}
                ,{type: "newcolumn"}
                ,{type: 'input', label: '지급/미지급사유', name: 'ansPayRsn', width:200, offsetLeft:15, maxLength:100, labelWidth: 100}
            ]}
            ,{type:"block", list:[
                {type: 'input', label: '지급품목', name: 'ansPayItem', width:100, maxLength:30}
                ,{type: "newcolumn"}
                ,{type: 'input', label: '지금금액', name: 'ansPayAmt', width:100, offsetLeft:15, maxLength:7, validate:"ValidInteger", numberFormat: "0,000,000,000"}
                ,{type: "newcolumn"}
                ,{type: 'calendar', dateFormat: "%Y-%m", serverDateFormat: "%Y%m", label: '지급예정월', name: 'ansPayPlanMon', width:100, offsetLeft:10}
                ,{type: "newcolumn"}
                ,{type: 'select', label: '답변상태', name: 'ansStat', width:100, offsetLeft:15}
            ]}
          ]},
        ]}
        ,{type: 'hidden', name: 'giftVocSeq'}
      ]
      ,buttons: {
        center: {
          btnSave2: {label: "저장"}
          ,btnCancel2: {label: "닫기"}
        }
      }
      ,onButtonClick: function(btnId){
        var form = this;

        switch(btnId) {
          case "btnSave2" :
            if (!this.validate()) return;
            var idata = form.getFormData(true);
            mvno.cmn.ajax(ROOT + "/voc/giftvoc/updateGiftVocAns.json", idata, function(result) {
              if (result.result.code == "OK") {
                mvno.ui.closeWindowById(form, true);
                mvno.ui.notify("NCMN0001");
                PAGE.GRID1.refresh();
              } else {
                mvno.ui.alert(result.result.msg);
              }
            });
            break;

          case "btnCancel2" :
            mvno.ui.closeWindowById(form, true);
            break;
        }
      }
    } // end of FORM4 ------------------------------------
  }; // end of DHX -------------------------------------------------------------

  var PAGE = {
     title : "${breadCrumb.title}"
    ,breadcrumb : "${breadCrumb.breadCrumb}"
    ,buttonAuth: ${buttonAuth.jsonString}
    ,onOpen : function(){

       mvno.ui.createForm("FORM1");
       mvno.ui.createGrid("GRID1");
       mvno.ui.createForm("FORM2");

       mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0030",orderBy:"etc6"}, PAGE.FORM1, "vocMngOrgn");     // 담당부서
       mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0031",orderBy:"etc6"}, PAGE.FORM1, "searchGbn");  // 검색구분
       mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0032",orderBy:"etc6"}, PAGE.FORM1, "ansStat");  // 답변상태
       mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0033",orderBy:"etc6"}, PAGE.FORM1, "vocType");  // VOC 구분

       mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0032",orderBy:"etc6"}, PAGE.FORM2, "ansStat"); // 답변상태

    }
  }

</script>
