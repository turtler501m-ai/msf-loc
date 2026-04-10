<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>

<!-- Script -->
<script type="text/javascript">
    var DHX = {
        FORM1 : {
            items : [
                {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 60, blockOffset: 0}
                ,{type: "block", list: [
                        {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchStrtDt", label: "참여기간", value: "${srchStrtDt}", calendarPosition: "bottom", readonly: true, width: 100, offsetLeft: 10}
                        ,{type: "newcolumn"}
                        ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchEndDt", label: "~", value: "${srchEndDt}", calendarPosition: "bottom", readonly: true, labelAlign: "center", labelWidth: 10, offsetLeft: 5, width:100}
                        ,{type: "newcolumn"}
                        ,{type: "select", label: "프로모션명", name: "promotionList", width: 300, offsetLeft: 75}
                    ]}
                ,{type:"block", list: [
                        {type: "select", label: "검색구분", name: "pSearchGbn", options:[{value:"", text:"- 전체 -", selected:true},{value:"customerName", text:"고객명"},{value:"contractNum", text:"계약번호"},{value:"mobileNo", text:"휴대폰번호"}], width: 100, offsetLeft: 10}
                        ,{type: "newcolumn"}
                        ,{type: "input", name: "pSearchName", disabled: true, width: 150}
                        ,{type: "newcolumn"}
                        ,{type: "select", label: "당첨결과", name: "prizeList", width: 300, offsetLeft: 40}
                    ]}
                ,{type: "button", name: "btnSearch", value: "조회", className: "btn-search2"}
            ]
            ,onValidateMore: function(data){
                var form = this;

                if(data.srchStrtDt > data.srchEndDt){
                    this.pushError("srchEndDt", "참여기간", "시작일자가 종료일자보다 클 수 없습니다.");
                }

                if( data.pSearchGbn != "" && data.pSearchName.trim() == "") {
                    this.pushError("pSearchName", "검색어", "검색어를 입력하세요.");
                }
            }
            ,onButtonClick: function(btnId){

                var form = this;

                switch (btnId) {
                    case "btnSearch" :
                        PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getEventPromotionList.json", form);
                        break;
                }
            }
            ,onChange: function(name, value){

                var form = this;

                switch (name){
                    case "pSearchGbn" :
                        form.setItemValue("pSearchName", "");
                        if(value != ""){
                            form.disableItem("srchStrtDt");
                            form.disableItem("srchEndDt");
                            PAGE.FORM1.setItemValue("srchStrtDt", "");
                            PAGE.FORM1.setItemValue("srchEndDt", "");
                            form.enableItem("pSearchName");
                        }else{
                            //조회기간 세팅
                            var endDate = new Date().format('yyyymmdd');
                            var time = new Date().getTime();
                            time = time - 1000 * 3600 * 24 * 7;
                            var startDate = new Date(time);
                            PAGE.FORM1.setItemValue("srchStrtDt", startDate);
                            PAGE.FORM1.setItemValue("srchEndDt", endDate);
                            form.enableItem("srchStrtDt");
                            form.enableItem("srchEndDt");
                            form.disableItem("pSearchName");
                        }
                        break;

                    case "promotionList" :

                        mvno.cmn.ajax4lov( ROOT + "/stats/statsMgmt/getPrizeComboList.json", {promotionId: form.getItemValue("promotionList")}, PAGE.FORM1, "prizeList"); //당첨결과

                        break;
                }

            }
        } // end of FORM1 ------------------------------------
        ,GRID1 : {
            css: {height : "550px"}
            ,title: "조회결과"
            ,header: "참여일시,계약번호,프로모션명,당첨결과,본인인증 수단,본인인증(이름),본인인증(통신사),휴대폰번호,생년월일,성별,개인정보 수집 이용동의,광고성 정보 수신 동의,단말모델명,이동전통신사,신청일자,개통일자,회선상태,최초요금제코드,최초요금제,현재요금제코드,현재요금제,유심접점,대리점,판매점" // 24
            ,columnIds: "participateDate,contractNum,promotionName,prizeName,certType,customerName,certCompany,ctn,birthDate,gender,personalInfoCollectYn,marketingYn,modelName,moveCompany,reqInDay,lstComActvDate,subStatus,fstRateCd,fstRateNm,lstRateCd,lstRateNm,usimOrgNm,openAgntNm,shopNm" // 24
            ,widths: "150,100,150,150,100,100,100,100,100,100,130,130,100,100,100,100,80,100,150,100,150,150,150,200" // 24
            ,colAlign: "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"  // 24
            ,colTypes: "ro,ro,ro,ro,ro,ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" // 24
            ,colSorting: "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 24
            ,paging: true
            ,pageSize: 20
            ,showPagingAreaOnInit: true
            ,buttons: {
                hright: {
                    btnExcel: {label: "엑셀다운로드"}
                }
            }
            ,onButtonClick: function(btnId){
                var grid = this;

                switch (btnId) {

                    case "btnExcel" : // 엑셀다운로드

                        if(grid.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                            return;
                        }

                        var searchData = PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/stats/statsMgmt/getEventPromotionListExcel.json", true, {postData: searchData});

                        break;
                }
            }
        } // end of GRID1 ------------------------------------
    }; // end of DHX -------------------------------------------------------------

    var PAGE = {
        title : "${breadCrumb.title}"
        ,breadcrumb : "${breadCrumb.breadCrumb}"
        ,buttonAuth: ${buttonAuth.jsonString}
        ,onOpen : function(){

            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");

            mvno.cmn.ajax4lov( ROOT + "/stats/statsMgmt/getPromotionComboList.json", "", PAGE.FORM1, "promotionList"); //프로모션명
            mvno.cmn.ajax4lov( ROOT + "/stats/statsMgmt/getPrizeComboList.json", "", PAGE.FORM1, "prizeList"); //당첨결과

        }
    }

</script>