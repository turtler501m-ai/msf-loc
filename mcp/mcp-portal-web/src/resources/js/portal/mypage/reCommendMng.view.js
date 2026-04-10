;


$(document).ready(function() {

    $("#btnReCommendState").click(function(){
        location.href='/mypage/reCommendState.do';
    });


    $("#btnReCommendMng").click(function(){
        location.href='/mypage/reCommendMng.do';
    });


    $("#btnfrndUpdateMypage").click(function(){
        validator.config={};
        validator.config['chkAgreeA'] = 'isNonEmpty';
        validator.config['chkMstoreAgree'] = 'isNonEmpty';

        var linkTypeCd = $('input[name="linkTypeCd"]:checked').val() || "";
        if ("99" === linkTypeCd) {
            validator.config['openMthdCd_01'] = 'isNonEmpty';
            validator.config['rateComparison1&rateComparison2&rateComparison3'] = 'isIsNotEqualsTriple';
        }
        //name="linkTypeCd"

        if (validator.validate()) {
            if (pageObj.commendId != "") {
                KTM.Confirm('친구초대 정보를<br/> 변경하시겠습니까?'
                    ,function () {
                        this.close();
                        frndRecommendPrdMypage();
                    }
                    ,function(){
                        this.close();
                    }
                );
            } else {
                KTM.Confirm('친구초대 정보를<br/> 저장하시겠습니까?'
                    ,function () {
                        this.close();
                        frndRecommendPrdMypage();
                    }
                    ,function(){
                        this.close();
                    }
                );
            }


        } else {
            MCP.alert(validator.getErrorMsg());
        }
    });


    var frndRecommendPrdMypage = function() {
        var linkTypeCd = $('input[name="linkTypeCd"]:checked').val() || "";
        var openMthdCd,commendSocCode01, commendSocCode02,commendSocCode03;

        if ("01" === linkTypeCd) {
            openMthdCd ="";
            commendSocCode01 = "";
            commendSocCode02 = "";
            commendSocCode03 = "";
        } else {
            openMthdCd =$('input[name="openMthdCd"]:checked').val() || "";
            commendSocCode01 = $('#rateComparison1').val();
            commendSocCode02 = $('#rateComparison2').val();
            commendSocCode03 = $('#rateComparison3').val();
        }
        var varData = ajaxCommon.getSerializedData({
            mstoreTermAgree:$("#chkMstoreAgree").is(":checked") ? "Y" : "N"
            ,linkTypeCd:linkTypeCd
            ,openMthdCd:openMthdCd
            ,commendSocCode01:commendSocCode01
            ,commendSocCode02:commendSocCode02
            ,commendSocCode03:commendSocCode03
        });

        ajaxCommon.getItem({
            id:'frndSendAjax'
            ,cache:false
            ,url:'/mypage/frndRecommendPrdAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(data){
            fn_hideLoading();

            if(data.RESULT_CODE =="STEP01" || data.RESULT_CODE =="STEP02"){  // STEP 오류
                $("input[name='recommend']").val(data.RESULT_MSG);
                return false;
            }

            if( data.RESULT_CODE =="00000" ){
                $("#divRecommend").show();
                const linkTypeCd = data.COMMEND_OBJ?.linkTypeCd;
                if ( linkTypeCd === "99") {
                    $("input[name='linkTypeCd'][value='01']").prop("disabled", true);
                }
                $("input[name='recommend']").val(data.LINK_URL);
                pageObj.commendId = data.COMMEND_OBJ.commendId;

            } else if(data.RESULT_CODE =="00001"){
                MCP.alert("휴대폰 번호가 다릅니다. 다시 인증해 주세요.");
                return false;
            } else if(data.RESULT_CODE =="00002"){
                MCP.alert("다시 인증해 주세요.");
                return false;
            } else if(data.RESULT_CODE =="00003"){
                MCP.alert("친구추천 이벤트는 kt M모바일 고객인 경우에만 이용 가능합니다.");
                return false;
            } else if(data.RESULT_CODE =="00004"){
                MCP.alert("요청하신 번호는 이벤트 참여가 불가합니다.");
                return false;
            } else if(data.RESULT_CODE =="00005"){
                MCP.alert("추천 아이디 생성에 실패 했습니다.");
                return false;
            } else if(data.RESULT_CODE =="00009"){
                MCP.alert("개인정보 제 3자 제공에 동의해주세요.");
                return false;
            } else if(data.RESULT_CODE =="STEP01" || data.RESULT_CODE =="STEP02"){ // STEP 오류
                MCP.alert(data.RESULT_MSG);
                return false;
            } else {
                MCP.alert("문제가 발생 하였습니다.<br>잠시후 다시 시도 하시기 바랍니다.");
                return false;
            }
        });
    };


    pageObj.rateReady.then(() => {
        // allRateList 준비 이후
        getFrndRecommendMypage();
    });




});


var getFrndRecommendMypage = function() {
    var varData = ajaxCommon.getSerializedData({
        ncn:$("#ctn").val()
    });


    ajaxCommon.getItemNoLoading({
            id:'getFrndRecommendAjax'
            ,cache:false
            ,url:'/mypage/getFrndRecommendAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonData){
            if (jsonData.RESULT_CODE === "00000") {
                $("#divRecommend").show();
                const linkTypeCd = jsonData.COMMEND_OBJ?.linkTypeCd;
                if ( linkTypeCd === "99") {
                    $("input[name='linkTypeCd'][value='01']").prop("disabled", true);
                }
                pageObj.commendId = jsonData.COMMEND_OBJ.commendId;
                pageObj.commendNm = jsonData.COMMEND_NM;
                pageObj.commendObj = jsonData.COMMEND_OBJ;
                $(`input[name='linkTypeCd'][value='${jsonData.COMMEND_OBJ.linkTypeCd}']`).prop("checked", true).trigger("change"); // 필요하면
                $(`input[name='openMthdCd'][value='${jsonData.COMMEND_OBJ.openMthdCd}']`).prop("checked", true);
                $("#rateComparison1").val(jsonData.COMMEND_OBJ.commendSocCode01);
                $("#rateComparison2").val(jsonData.COMMEND_OBJ.commendSocCode02);
                $("#rateComparison3").val(jsonData.COMMEND_OBJ.commendSocCode03);
                $("#recommend").val(jsonData.LINK_URL);
            } else {
                $("#divRecommend").hide();
                $("input[name='linkTypeCd'][value='99']").prop("checked", true).trigger("change");
                pageObj.commendId = "";
                pageObj.commendNm = "";
                pageObj.commendObj = {};
                $("#rateComparison1").val("");
                $("#rateComparison2").val("");
                $("#rateComparison3").val("");
                $("#recommend").val("");
            }

        });
};


function select() {
    getFrndRecommendMypage();
}



