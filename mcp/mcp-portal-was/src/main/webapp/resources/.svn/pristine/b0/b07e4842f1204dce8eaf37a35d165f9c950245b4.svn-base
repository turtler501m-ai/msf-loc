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
                        var $objList2 = $("#divCombinHistory2 tbody");
                        $objList.empty();
                        $objList2.empty();

                        //아무나결합 건 수
                        let combineWirelessCnt = 0;

                        //아무나가족결합+ 리스트
                        let combineKtList = "";

                        if(jsonObj.RESULT_LIST.length > 0){
                            combineWirelessCnt = jsonObj.RESULT_LIST.filter(item => item.svcContDivNm === "Mobile(KIS)").length;
                            combineKtList = jsonObj.RESULT_LIST.filter(item => item.svcContDivNm !== "Mobile(KIS)");
                        }

                        //아무나가족결합+ 탭 클릭
                        if ($("#tabB1-panel").css("display") == "block") {
                            //아무나가족결합+ 결합 리스트 있을 때
                            if(combineKtList.length > 0){
                                var objList = combineKtList;
                                var arr =[];
                                for(i = 0 ; i < objList.length ; i++) {
                                    arr.push("<tr>\n");
                                    arr.push("    <th scope='row'>"+objList[i].combTypeNm+"</th>\n");
                                    arr.push("    <td>\n");
                                    arr.push("        <p>"+objList[i].svcNo+"</p>\n");
                                    arr.push("        <p class='rate-info'>"+objList[i].prodNm+"</p>\n");
                                    if (objList[i].combEngtExpirDtFormat !=null && objList[i].combEngtExpirDtFormat != "") {
                                        arr.push("        <p class='date-info'>(만료예정일 : "+objList[i].combEngtExpirDtFormat+")</p>\n");
                                    }
                                    arr.push("    </td>\n");
                                    arr.push("</tr>\n");
                                }
                                $objList.append(arr.join(''));
                                $("#divCombinHistory").show();
                            }else{
                                $("#divCombinHistory").hide();
                            }
                            //아무나가족결합+ 결합 진행 현황 리스트 있을 때
                            if(jsonObj.RESULT_LIST2.length > 0){

                                var $objList2 = $("#divCombinHistory2 tbody");
                                $objList2.empty();

                                var objList = jsonObj.RESULT_LIST2;
                                var arr =[];
                                for(i = 0 ; i < objList.length ; i++) {
                                    arr.push("<tr>\n");
                                    arr.push("    <th scope='row'>"+objList[i].combTypeNm+"</th>\n");
                                    arr.push("    <td>\n");
                                    arr.push("        <p>"+objList[i].combSvcNo+"</p>\n");
                                    arr.push("        <span class='progress-info'>"+objList[i].rsltNm+"</span>\n");
                                    arr.push("    </td>\n");
                                    arr.push("</tr>\n");
                                }
                                $objList2.append(arr.join(''));
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
                        if($("#tabB3-panel").css("display") == "block"){
                            //아무나결합 리스트 존재할 때
                            if(combineWirelessCnt > 0){
                                $("#divCombinHistoryData").show();
                                $("#divCombinHistoryNodata2").hide();
                                $("._serviceNm").html(jsonObj.AdsvcNm);
                            }else{
                                $("#divCombinHistoryData").hide();
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
            arr.push("<div class='c-text-box c-text-box--red deco-combine u-mt--32'>\n");
            arr.push("  <p class='c-text c-text--type5 u-co-black'>함께쓰기 결합</p>\n");
            arr.push("  <b class='c-text c-text--type2'>대상 요금제가 아닙니다.</b>\n");
            arr.push("  <img src='/resources/images/mobile/content/img_combine.svg' alt=''>\n");
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
                , url:'/m/content/myShareDataList.do'
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

    $("#ctn").change(function(){
        if($("#tabB1-panel").css("display") == "block" || $("#tabB3-panel").css("display") == "block" || $("#tabB4-panel").css("display") == "block"){
            getCombiSvcList();
        } else {
            getMyShareDataList();
        }
    });

    $("#tab1").click(function(){
        pageObj.tabId = "tab1";
        getCombiSvcList();
    });

    $("#tab2").click(function(){
        pageObj.tabId = "tab2";
        getMyShareDataList();
    });

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
