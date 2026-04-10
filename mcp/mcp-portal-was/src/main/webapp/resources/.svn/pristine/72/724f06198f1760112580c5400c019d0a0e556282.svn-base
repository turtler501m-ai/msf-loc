;

var pageObj = {
    tabId:""
}


$(document).ready(function (){


    var getCombiSvcList = function() {
        KTM.LoadingSpinner.show();

        var  menuParaVal = "combine";

        if (pageObj.tabId == "tab4") {
            menuParaVal = "myCombineSelf";
        } else {
            menuParaVal = "combine";
        }



        //회선 정보 조회
        var varData = ajaxCommon.getSerializedData({
            ncn:$("#ctn").val()
            ,menuPara:menuParaVal
        });

        ajaxCommon.getItemNoLoading({
            id:'getRateInfo'
            , cache:false
            , url:'/content/getRateInfoAjax.do'
            , data: varData
            , dataType:"json"
            , async:false
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                $("._rate").html(jsonObj.RESULT_RATE_NM);
                $("._ctn").html(jsonObj.RESULT_PHONE_NUM);
                $("._userNm").html(jsonObj.RESULT_NM);
            } else {
                MCP.alert("요금제 정보 조회 실패 하였습니다.");
            }
        });



        ajaxCommon.getItemNoLoading({
                id:'combiSvcList'
                ,cache:false
                ,url:'/content/getCombiSvcListAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    if ("myCombineSelf" == menuParaVal) {
                        //아무나 솔로
                        if (jsonObj.IS_SOLO_SERVICE) {
                            $("._soloInfo").show();
                            $("#divSoloNone").hide();
                            $("#tdServiceNm").html(jsonObj.AdsvcNm);
                        } else {
                            $("._soloInfo").hide();
                            $("#divSoloNone").show();
                        }

                    } else {

                        var $objList = $("#divCombinHistory tbody");
                        var $objList2 = $("#combinePrint .combine-info-list tbody");
                        var $objList3 = $("#divCombinHistory2 tbody");

                        $objList.empty();
                        $objList2.empty();
                        $objList3.empty();

                        //아무나결합 건 수
                        let combineWirelessCnt = 0;

                        //아무나가족결합+ 리스트
                        let combineKtList = "";

                        if(jsonObj.RESULT_LIST != null && jsonObj.RESULT_LIST.length > 0){
                            combineWirelessCnt = jsonObj.RESULT_LIST.filter(item => item.svcContDivNm === "Mobile(KIS)").length;
                            combineKtList = jsonObj.RESULT_LIST.filter(item => item.svcContDivNm !== "Mobile(KIS)");
                        }

                        //아무나가족결합+ 탭 클릭
                        if ($("#tabB1panel").css("display") == "block") {
                            //아무나가족결합+ 결합 리스트 존재 할 때
                            if(combineKtList.length > 0){
                                var objList = combineKtList;
                                var arr =[];
                                var arr2 =[];
                                for(i = 0 ; i < objList.length ; i++) {
                                    arr.push("<tr>\n");
                                    arr.push("    <th scope='row'>"+objList[i].combTypeNm+"</th>\n");
                                    arr.push("    <td>\n");
                                    arr.push("        <p>"+objList[i].svcNo+"</p>\n");
                                    arr.push("        <p class='rate-info'>"+objList[i].prodNm+"</p>\n");
                                    arr.push("    </td>\n");
                                    arr.push("    <th scope='row'>만료예정일</th>\n");
                                    arr.push("    <td>"+objList[i].combEngtExpirDtFormat+"</td>\n");
                                    arr.push("</tr>\n");

                                    arr2.push("<tr>\n");
                                    arr2.push("    <td>"+objList[i].combTypeSmNm+"</td>\n");
                                    arr2.push("    <td>"+objList[i].combTypeSmNm2+"</td>\n");
                                    arr2.push("    <td>"+objList[i].svcNo+"</td>\n");
                                    arr2.push("    <td>"+objList[i].combEngtPerdMonsNum+"</td>\n");
                                    arr2.push("    <td>"+objList[i].combEngtExpirDtFormat2+"</td>\n");
                                    arr2.push("</tr>\n");

                                }
                                $objList.append(arr.join(''));
                                $objList2.append(arr2.join(''));

                                $("#divCombinHistory").show();
                                $("#btnPrint").show();
                            }else{
                                $("#divCombinHistory").hide();
                                $("#btnPrint").hide();
                            }

                            //아무나가족결합+ 결합진행 현황 리스트 존재 할 때
                            if(jsonObj.RESULT_LIST2.length > 0){
                                var $objList3 = $("#divCombinHistory2 tbody");
                                $objList3.empty();

                                var objList = jsonObj.RESULT_LIST2;
                                var arr =[];
                                for(i = 0 ; i < objList.length ; i++) {
                                    arr.push("<tr>\n");
                                    arr.push("    <th scope='row'>" + objList[i].combTypeNm + "</th>\n");
                                    arr.push("    <td>"+objList[i].combSvcNo+"</td>\n");
                                    arr.push("    <th scope='row'>진행현황</th>\n");
                                    arr.push("    <td><span class='progress-info'>"+objList[i].rsltNm+"</span></td>\n");
                                    arr.push("</tr>\n");
                                }
                                $objList3.append(arr.join(''));
                                $("#divCombinHistory2").show();
                            }else{
                                $("#divCombinHistory2").hide();
                            }

                            //아무나가족결합+ 리스트, 결합진행 현황 둘다 없을 경우
                            if(combineKtList.length < 1 && jsonObj.RESULT_LIST2.length < 1){
                                $("#divCombinHistoryNodata").show();
                                $("._serviceNm").html("-");
                            }else{
                                $("._serviceNm").html(jsonObj.AdsvcNm);
                                $("#divCombinHistoryNodata").hide();
                            }
                        }

                        //아무나결합 탭 클릭
                        if ($("#tabB3panel").css("display") == "block"){
                            if(combineWirelessCnt > 0){
                                $("#divCombinHistoryData").show();
                                $("#btnPrint2").show();
                                $("#divCombinHistoryNodata2").hide();
                                $("._serviceNm").html(jsonObj.AdsvcNm);
                            }else{
                                $("#divCombinHistoryData").hide();
                                $("#btnPrint2").hide();
                                $("#divCombinHistoryNodata2").show();
                                $("._serviceNm").html("-");
                            }
                        }

                    }


                    KTM.LoadingSpinner.hide(true);

                } else {
                    KTM.LoadingSpinner.hide(true);
                    var resultMsg = jsonObj.RESULT_MSG;
                    if (resultMsg!= null) {
                        MCP.alert(resultMsg);
                    } else {
                        MCP.alert("결합내역 조회 실패 하였습니다.");
                    }
                }
            });



    }

    var getMyShareDataList = function(){

        let retvGubunCd = "";

        //회선 정보 조회
        var varData2 = ajaxCommon.getSerializedData({
            ncn:$("#ctn").val()
        });

        ajaxCommon.getItemNoLoading({
            id:'getRateInfo'
            , cache:false
            , url:'/content/getRateInfoAjax.do'
            , data: varData2
            , dataType:"json"
            , async:false
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                retvGubunCd = jsonObj.RETV_GUBNU_CD ;
            } else {
                MCP.alert("요금제 정보 조회 실패 하였습니다.");
            }
        });


        if (retvGubunCd == ""){
            var arr =[];
            arr.push("<div class='c-text-box c-text-box--red c-text-box--lg u-mt--64' >\n");
            arr.push("    <p class='c-text-box__text'>\n");
            arr.push("        <b class='u-co-red'>함께쓰기 결합 대상 요금제가 아닙니다.</b>\n");
            arr.push("    </p>\n");
            arr.push("    <img class='c-text-box__image' src='/resources/images/portal/content/img_impossible.png' alt='' aria-hidden='true'>\n");
            arr.push("</div>\n");

            $("#tabB2Body").html(arr.join(''));
        } else {
            //회선 정보 조회
            var varData = ajaxCommon.getSerializedData({
                ncn:$("#ctn").val()
                ,retvGubunCd :retvGubunCd
            });

            ajaxCommon.getItemNoLoading({
                id:'getMyShareDataList'
                , cache:false
                , url:'/content/myShareDataList.do'
                , data: varData
                , dataType:"html"
                , async:false
            }
            ,function(dataHtml){
                var tabHtml = $(".c-modal__body" ,dataHtml );
                $("#tabB2Body").html(tabHtml);
            });
        }

    }







    $("#btnSelectCTN").click(function(){
        if($("#tabB1panel").css("display") == "block" || $("#tabB3panel").css("display") == "block" || $("#tabB4panel").css("display") == "block"){
            getCombiSvcList();
        } else {
            getMyShareDataList();
        }
    });

    //아무나가족결합+
    $("#btnPrint").click(function(){
        KTM.LoadingSpinner.hide(true);
        var el = document.querySelector('#combinePrint');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();
    });

    //아무나결합
    $("#btnPrint2").click(function(){
        KTM.LoadingSpinner.hide(true);
        var el = document.querySelector('#combinePrint2');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();
    });

    //아무나가족결합+
    $("#tab1").click(function(){
        pageObj.tabId = "tab1";
        getCombiSvcList();
    });

    //함께쓰기
    $("#tab2").click(function(){
        pageObj.tabId = "tab2";
        getMyShareDataList();
    });

    //아무나결합
    $("#tab3").click(function(){
        pageObj.tabId = "tab3";
        getCombiSvcList();
    });


    $("#tab4").click(function(){
        pageObj.tabId = "tab4";
        getCombiSvcList();
    });


    //getCombiSvcList();

    $(".c-tabs__button").eq(0).trigger("click")

});



function select(){
    //nothing
}


document.addEventListener('UILoaded', function() {
    var PrintButton = document.querySelector('.print-btn');
    var PrintButton2 = document.querySelector('.print-btn2');
    PrintButton.addEventListener('click', function() {
        window.print();
    });
    PrintButton2.addEventListener('click', function() {
        window.print();
    });
});