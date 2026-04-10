var recent = 0;
var last = 0;


var pageObj = {
    startDate:""
    ,endDate:""
}

$(document).ready(function (){

    $(".btnPrint").click(function(){

        var startDate = "";
        var endDate = "";
        var listCnt = $(this).attr("listCnt");
        if (listCnt == "0") { // 올해
            if(recent==0){
                MCP.alert("오른쪽의 화살표를 클릭하여,<br>최근 납부내역을 조회해 주세요.");
                return;
            }

            var today = new Date();
            endDate = dateFormat(today);
            today.setFullYear(today.getFullYear(),0,1); //  (= 2011년 1월 1일)
            startDate = dateFormat(today);

        } else { // 지난해
            if(last==0){
                MCP.alert("오른쪽의 화살표를 클릭하여,<br>작년 납부내역을 조회해 주세요.");
                return;
            }
            var beforeDay = new Date();
            beforeDay.setFullYear(beforeDay.getFullYear()-1,0,1);
            startDate = dateFormat(beforeDay);
            beforeDay.setFullYear(beforeDay.getFullYear(),11,31);
            endDate = dateFormat(beforeDay);
        }

        pageObj.startDate = startDate;
		pageObj.endDate = endDate;


        var parameterData = ajaxCommon.getSerializedData({
            menuType : 'chargePrint'
            , ncn : $("#ctn option:selected").val()
        });
        openPage('pullPopupByPost','/sms/smsAuthToNcnInfoPop.do',parameterData);

        /*var parameterData = ajaxCommon.getSerializedData({
            ncn : $("#ctn option:selected").val()
            ,startDate : startDate
            ,endDate : endDate
        });
        openPage('pullPopup','/mypage/chargeMonPrint.do',parameterData,"1");*/

    });




    $(".btnExcel").click(function(){
        var listCnt = $(this).attr("listCnt");
        var startDate = "";
        var endDate = "";
        if (listCnt == "0") { // 최근납부
            if(recent==0){
                MCP.alert("오른쪽의 화살표를 클릭하여,<br>최근 납부내역을 조회해 주세요.");
                return;
            }
            var today = new Date();
            endDate = dateFormat(today);
            today.setFullYear(today.getFullYear(),0,1); //  (= 2011년 1월 1일)
            startDate = dateFormat(today);

        } else { // 지난 납부

            if(last==0){
                MCP.alert("오른쪽의 화살표를 클릭하여,<br>작년 납부내역을 조회해 주세요.");
                return;
            }
            var beforeDay = new Date();
            beforeDay.setFullYear(beforeDay.getFullYear()-1,0,1);
            startDate = dateFormat(beforeDay);
            beforeDay.setFullYear(beforeDay.getFullYear(),11,31);
            endDate = dateFormat(beforeDay);
        }

        ajaxCommon.createForm({
            method:"post"
            ,action:"/mypage/chargeMonthListExcelDownload.do"
        });

        ajaxCommon.attachHiddenElement("ncn",$("#ctn option:selected").val());
        ajaxCommon.attachHiddenElement("startDate",startDate);
        ajaxCommon.attachHiddenElement("endDate",endDate);
        ajaxCommon.formSubmit();
        KTM.LoadingSpinner.hide(true);
    });

    $(".realSrch").click(function(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/mypage/chargeView03.do"
        });
        ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
        ajaxCommon.formSubmit();
    });

    $("#acc_header_a1,#acc_header_a2").click(function(){

        var id = $(this).attr("id");
        var startDate = "";
        var endDate = "";
        var target = "";
        if(id=="acc_header_a1"){ // 최근
            if(recent> 0){
                return;
            }
            var today = new Date();
            endDate = dateFormat(today);
            today.setFullYear(today.getFullYear(),0,1); //  (= 2011년 1월 1일)
            startDate = dateFormat(today);
            target = "recent";
            recent++;
        } else { // 작년
            if(last> 0){
                return;
            }
            var beforeDay = new Date();
            beforeDay.setFullYear(beforeDay.getFullYear()-1,0,1);
            startDate = dateFormat(beforeDay);
            beforeDay.setFullYear(beforeDay.getFullYear(),11,31);
            endDate = dateFormat(beforeDay);
            target = "last";
            last++;
        }
        var varData = ajaxCommon.getSerializedData({
            ncn : $("#ctn option:selected").val()
            ,startDate : startDate
            ,endDate : endDate
        });

        ajaxCommon.getItem({
            id:'recentPaymentAjax'
            ,cache:false
            ,url:'/mypage/recentPaymentAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }

        ,function(jsonObj){
            var returnCode = jsonObj.returnCode;
            var message = jsonObj.message;
            var itemPay = jsonObj.itemPay;
            if(returnCode !="00"){
                MCP.alert(message);
                recentView(null,target);
                return;
            } else {
                recentView(itemPay,target);
            }
        });

    });

});


function dateFormat(parDate){
    var yyyy = parDate.getFullYear();
    var mm = parDate.getMonth()+1;
    mm = mm >= 10 ? mm : '0'+mm;
    var dd = parDate.getDate();
    dd = dd >= 10 ? dd : '0'+dd;
    return ""+yyyy+mm+dd;
}


function recentView(itemPay,target){

    var addHtml = "";

    if(itemPay !=null && itemPay.length > 0){

        for(var i=0; i<itemPay.length; i++){
            var payMentDate = itemPay[i].payMentDate;
            if(payMentDate !=null && payMentDate.length > 7){
                payMentDate = payMentDate.substring(0,4)+"."+payMentDate.substring(4,6)+"."+payMentDate.substring(6,8);
            }
            var payMentMethod = itemPay[i].payMentMethod;
            var payMentMoney = itemPay[i].payMentMoney;

            addHtml += "<tr>";
            addHtml += "    <td>"+payMentDate+"</td>";
            addHtml += "    <td>"+payMentMethod+"</td>";
            addHtml += "    <td class='u-ta-right'>"+numberWithCommas(payMentMoney)+"원</td>";
            addHtml += "</tr>";
        }

    } else {
        addHtml += "<tr>";
        addHtml += "    <td colspan='3'>";
        addHtml += "        <div class='c-nodata'>";
        addHtml += "            <i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
        addHtml += "            <p class='c-nodata__text'>납부내역이 없습니다.</p>";
        addHtml += "        </div>";
        addHtml += "    </td>";
        addHtml += "</tr>";

        if(target=="recent"){
            $(".btnPrint").eq(0).prop("disabled",true);
            $(".btnExcel").eq(0).prop("disabled",true);
        } else {
            $(".btnPrint").eq(1).prop("disabled",true);
            $(".btnExcel").eq(1).prop("disabled",true);
        }

    }

    if(target=="recent"){
        $("#recentArea").html(addHtml);
    } else {
        $("#lastArea").html(addHtml);
    }
}

function detailView(id, messageLine){

    var varData = ajaxCommon.getSerializedData({
        messageLine : messageLine
        , ncn : $("#ctn option:selected").val()
        , billMonth :$("#billMonth option:selected").val()
    });

    ajaxCommon.getItem({
        id:'chargeDetailItemAjax'
        ,cache:false
        ,url:'/mypage/chargeDetailItemAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(data){

        if (data.returnCode=="00"){
            var list = data.result;
            var contents = '';
            if (list.length > 0){
                for ( var i = 0; i < list.length; i++) {
                    contents += ' <dt>'+list[i].descr+'</dt> ';
                    contents += ' <dd>'+numberWithCommas(list[i].amt)+'원</dd> ';
                }
                $("#"+id).html(contents);
                KTM.initialize();
            }

        }else{
            MCP.alert(data.message);
        }
    });
    KTM.initialize();
}

function excelDownload(){
    $("#ncn").val($("#ctn option:selected").val());
    //$("#billMonth").val($("#billMonth option:selected").val());
    $('#frm').attr('target', "tmpFrm");
    $("#frm").attr("action","/mypage/chargeView01ExcelDownload.do").submit();
}

function printPopup(){
    var parameterData = ajaxCommon.getSerializedData({
        ncn : $("#ctn option:selected").val()
        ,billMonth :$("#billMonth option:selected").val()
    });

    openPage('pullPopup','/mypage/chargeViewPrint.do',parameterData,"1");
}

function select(){

    ajaxCommon.createForm({
        method:"post"
        ,action:"/mypage/chargeView01.do"
    });
    ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
    ajaxCommon.formSubmit();
}

function btn_search(){

    ajaxCommon.createForm({
        method:"post"
            ,action:"/mypage/chargeView01.do"
    });
    ajaxCommon.attachHiddenElement("billMonth", $("#billMonth option:selected").val());
    ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
    ajaxCommon.formSubmit();
}

    function btn_reg(){
	    var parameterData = ajaxCommon.getSerializedData({
            ncn : $("#ctn option:selected").val()
            ,startDate : pageObj.startDate
            ,endDate : pageObj.endDate
        });
        openPage('pullPopup','/mypage/chargeMonPrint.do',parameterData,"1");
    }

