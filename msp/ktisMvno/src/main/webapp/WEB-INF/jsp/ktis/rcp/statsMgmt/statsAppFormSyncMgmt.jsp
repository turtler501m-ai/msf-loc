<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div><!-- 메인 -->
<!-- Script -->
<script type="text/javascript">
    var DHX = {
        //------------------------------------------------------------
        FORM1 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
                , {type:"block", list:[
                        {type : "calendar",width : 100,label : "조회기간", labelWidth:60, dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "srchStrtDt", offsetLeft:10, readonly:true},
                        {type : "newcolumn"},
                        {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 0,width : 100, readonly:true},
                        {type : "newcolumn"},
                        {type : "select",width : 80, offsetLeft:290, label : "성공 이력",labelWidth:65, labelAlign: "left", name : "srchSuccYn", options:[{value:"", text:"- 전체 -"}, {value:"Y", text:"존재"}, {value:"N", text:"미존재"}]},
                    ]},
                {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
                {type:"block", list:[
                        {type : "select",width : 100, offsetLeft:10, label : "검색구분", labelWidth: 60,name : "srchGubun", offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"schContractNum", text:"계약번호"}, {value:"schItemId", text:"서식지 아이디"}]},
                        {type : "newcolumn"},
                        {type : "input", width : 300, name : "srchVal", offsetLeft:0, labelAlign:"center", disabled:true},
                        {type : "newcolumn"},
                        {type : "select",width : 80, offsetLeft:100, label : "최근 이력", labelWidth:65, labelAlign: "left", name : "srchSuccRecentYn", options:[{value:"", text:"- 전체 -"}, {value:"Y", text:"성공"}, {value:"N", text:"실패"}, {value:"E", text:"미존재"}]},
                    ]},
                {type: 'newcolumn', offset:150},
                {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"},
            ],
            onChange:function(name, value){
                var form = this;
                switch(name){
                    case "srchGubun" :
                        if(value != ""){
                            form.disableItem("srchStrtDt");
                            form.disableItem("srchEndDt");
                            PAGE.FORM1.setItemValue("srchStrtDt", "");
                            PAGE.FORM1.setItemValue("srchEndDt", "");
                            form.disableItem("srchItemId");
                            form.enableItem("srchVal");
                        } else {
                            //조회기간 세팅
                            var endDate = new Date().format('yyyymmdd');
                            var time = new Date().getTime();
                            time = time - 1000 * 3600 * 24 * 7;
                            var startDate = new Date(time);
                            PAGE.FORM1.setItemValue("srchStrtDt", startDate);
                            PAGE.FORM1.setItemValue("srchEndDt", endDate);

                            form.enableItem("srchStrtDt");
                            form.enableItem("srchEndDt");
                            form.disableItem("srchVal");
                        }
                        PAGE.FORM1.setItemValue("srchVal", "");
                        break;
                }
            },
            onValidateMore : function(data) {
                if (data.srchStrtDt > data.srchEndDt) {
                    this.pushError("srchStrtDt", "신청일자", "시작일자가 종료일자보다 클 수 없습니다.");
                }
                if(data.srchStrtDt != "" && data.srchStrtDt == ""){
                    this.pushError("srchStrtDt", "신청일자", "조회기간을 입력하세요.");
                }
                if(data.srchEndDt != "" && data.srchEndDt == ""){
                    this.pushError("srchEndDt", "신청일자", "조회기간을 입력하세요.");
                }
                if(data.srchGubun != "" && data.srchVal == ""){
                    this.pushError("srchVal", "검색어 입력", "검색값을 입력하세요.");
                }

            },
            onButtonClick : function(btnId) {

                var form = this;

                switch (btnId) {

                    case "btnSearch":
                        PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getAppFormSyncList.json", form);
                        PAGE.GRID2.clearAll();
                        break;
                }

            },
        },
        // ----------------------------------------
        //사용자리스트
        GRID1 : {
            css : {
                height : "300px"
            },
            title : "조회결과",
            header : "개통일자, 계약번호, 서식지 아이디, 성공 이력, 최근 이력", //5
            columnIds : "lstComActvDateVal,contractNum,itemId,succYn,succRecentYn", // 5
            colAlign : "center,center,center,center,center", //5
            colTypes : "ro,ro,ro,ro,ro", //5
            colSorting : "str,str,str,str,str", // 5
            widths : "100,100,330,200,200", // 5
            paging: true,
            showPagingAreaOnInit: true,
            pageSize:20,
            buttons : {
                hright : {
                    btnExcel : { label : "엑셀다운로드" }
                }
            },
            onRowSelect : function (rowId, celInd) {
                PAGE.GRID2.list(ROOT + "/stats/statsMgmt/getAppFormSyncDetailList.json", this.getRowData(rowId));
            },
            onButtonClick : function(btnId, selectedData) {
                var grid = this;
                switch (btnId) {
                    case "btnExcel" :
                        if(PAGE.GRID1.getRowsNum() == 0){
                            mvno.ui.alert("데이터가 존재하지 않습니다");
                            return;
                        }

                        var searchData =  PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/stats/statsMgmt/getAppFormSyncListExcel.json?menuId=MSP1010053", true, {postData:searchData});

                        break;
                }
            }
        },
        GRID2 : {
            css : {
                height : "200px"
            },
            title : "상세 이력",
            header : "처리일시,서식지 아이디,처리여부,작업 구분,처리 코드,처리명,처리 실패 상세,파일명(CSV),파일명(이미지),이미지 개수(CSV),실제 이미지 개수", //10
            columnIds : "regstDttm,itemId,succYn,oprtMod,rsltCd,rsltCdNm,msg,csvFileNm,imgFileNm,csvImgCount,realImgCount", // 10
            colAlign : "center,center,center,center,center,center,left,center,left,center,center", //10
            colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro", //10
            colSorting : "str,str,str,str,str,str,str,str,str,str,str", // 10
            widths : "200,300,90,60,90,250,300,400,300,120, 100", // 10
            paging: true,
            showPagingAreaOnInit: true,
            pageSize:20,
            onButtonClick : function(btnId, selectedData) {
                var grid = this;
                switch (btnId) {
                    case "btnExcel" :
                        if(PAGE.GRID1.getRowsNum() == 0){
                            mvno.ui.alert("데이터가 존재하지 않습니다");
                            return;
                        }

                        var searchData =  PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/stats/statsMgmt/getAppFormSyncListExcel.json?menuId=MSP1010053", true, {postData:searchData});

                        break;
                }
            }
        }
    };

    var PAGE = {
        title : "${breadCrumb.title}",
        breadcrumb : " ${breadCrumb.breadCrumb}",
        buttonAuth: ${buttonAuth.jsonString},
        onOpen : function() {

            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");
            mvno.ui.createGrid("GRID2");


            var endDate = new Date().format('yyyymmdd');
            var time = new Date().getTime();
            time = time - 1000 * 3600 * 24 * 7;
            var startDate = new Date(time);

            PAGE.FORM1.setItemValue("srchStrtDt", startDate);
            PAGE.FORM1.setItemValue("srchEndDt", endDate);

        }
    };

</script>