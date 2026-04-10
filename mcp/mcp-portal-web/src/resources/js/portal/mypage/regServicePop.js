var pageObj = {
    eventId : ""     // 팝업 이벤트 코드 구분용
}

$(document).ready(function (){

     // 제휴 약관
     $("#chkAgree1").click(function(){
        pageObj.eventId= "chkAgree1";

        if($('#targetTerms1').val() != "Y"){
            var data = {cdGroupId1:'FORMREQUIRED',
                cdGroupId2:'clauseJehuFlag',
                docType:'02',
                expnsnStrVal:$("#jehuProdType").val(),
                name:$("#jehuProdName").val()
            };
            openPage('termsPop','/termsPop.do',data,0);

            // 약관 동의후 닫기 버튼을 클릭하지 않고, 닫기 버튼을 누른경우 체크되는 것을 방지하기 위해 추가..
            $('#chkAgree1').prop("checked",false);
            var el = document.querySelector('#modalTerms');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            $('#modalTerms').addClass('c-modal--xlg');
        } else{
            $('#chkAgree1').prop("checked",false);
            $('#targetTerms1').val("");
        }

     });

     $('#chkAgreeA2').click(function(){

        if($('#targetTerms2').val() != "Y"){ // 체크
            $('#targetTerms2').val("Y");
        } else{ // 체크 해제
            $('#targetTerms2').val("");
        }
    });

     // 내 사용량 펼쳐보기 클릭 이벤트 (클릭이벤트 실행 후 아코디언 open 순서)
     $("#realTimeDataHeader").click(function(){

         // 이미 열려있는 상태라면 즉시 리턴
         if($(this).hasClass("is-active")) return true;

         // 내 사용량 조회
         searchUseageData();
     }); // end of event-----------------------------------
});

/**
 * 실시간 이용량 조회 (당월)
 * @param useageData
 * @param useageVoice
 * @param useageSms
 */
var searchUseageData= function(){

    // 내 이용량 조회
    var varData = ajaxCommon.getSerializedData({
        ncn:$("#ncn option:selected").val()
    });

    ajaxCommon.getItem({
            id:'selectRealTimeUseageAjax'
            ,cache:false
            ,url:'/mypage/selectRealTimeUseageAjax.do'
            ,data : varData
            ,dataType:"json"
        }
    ,function(jsonObj){

        var resultCd= (!jsonObj || !jsonObj.RESULT_CODE) ? "0006" : jsonObj.RESULT_CODE;
        var resultMsg= (!jsonObj || !jsonObj.RESULT_MSG) ? "서비스 처리중 오류가 발생 하였습니다." : jsonObj.RESULT_MSG;

        // 중복요청으로 데이터를 안가져온 경우 현재 상태 그대로 유지
        if(resultCd == '0001') return false;

        // 로그인 페이지로 이동
        if(resultCd == '0002'){
            MCP.alert(resultMsg, function (){location.href = '/loginForm.do';});
            return false;
        }

        // 정회원 인증 페이지로 이동
        if(resultCd == '0003'){
            MCP.alert(resultMsg, function (){location.href = '/mypage/updateForm.do';});
            return false;
        }

        // 조회 오류로 아무 값도 가져오기 못한 경우_내 사용량 펼쳐보기 닫기
        if(resultCd == "0006"){
            MCP.alert("서비스 처리중 오류가 발생 하였습니다.", function (){$("#realTimeDataHeader").trigger('click')});
            return false;
        }

        if(resultCd == AJAX_RESULT_CODE.SUCCESS){ // 조회 성공
            if(jsonObj.useTimeSvcLimit){ // 조회 횟수 초과_조회 횟수 초과 문구 표출
                $("._realTimeDataView").hide();
                $("#realTimeDataView01").show();
            }else{
                var useageData= jsonObj.useageData || "";
                var useageVoice= jsonObj.useageVoice || "";
                var useageSms= jsonObj.useageSms || "";

                drawUseageData(useageData, useageVoice, useageSms);  // 실시간 이용량 그리기
            }
        }else{ // 조회 오류_오류 문구 표출
            $("._realTimeDataView").hide();
            $("#realTimeDataView03").show();
        }
    }); // end of ajax-----------------------------------
}

/**
 * 실시간 이용량 화면에 그리기
 * @param useageData
 * @param useageVoice
 * @param useageSms
 */
var drawUseageData= function(useageData, useageVoice, useageSms){

    // 이용량 조회결과가 존재하지 않는 경우_조회결과 없음 화면 표출
    if( (useageData== "" || useageData.length == 0) && (useageVoice== "" || useageVoice.length == 0) && (useageSms== "" || useageSms.length == 0)){
        $("._realTimeDataView").hide();
        $("#realTimeDataView02").show();
        return false;
    }

    // 조회결과가 있는 경우 화면 표출
    var drawHtml= "";
    var drawDataList= [useageData, useageVoice, useageSms];

    for(var i=0;i<drawDataList.length;i++){
        if(drawDataList[i]== "") continue;

        drawDataList[i].forEach(function(item){
            var rtnHtml= drawUseageDataOne(item);
            drawHtml+=rtnHtml;
        });
    }

    $("#realTimeDataViewContent").html(drawHtml);
    $("._realTimeDataView").hide();
    $("#realTimeDataView04").show();
    $("#realTimeDataViewAddDesc").show();
}

/**
 * 실시간 이용량 테이블 행 그리기(1개)
 * @param dataObj
 */
var drawUseageDataOne= function(dataObj){

    var rtnHtml= '';

    // null체크
    if(!dataObj) return rtnHtml;

    rtnHtml+= '<tr>\n';
    rtnHtml+= '  <td class="u-pt--12 u-pb--12">'+dataObj.strSvcName+'</td>\n';
    rtnHtml+= '  <td class="u-pt--12 u-pb--12">'+dataObj.strFreeMinUse+'</td>\n';
    rtnHtml+= '  <td class="u-pt--12 u-pb--12">'+dataObj.strFreeMinRemain+'</td>\n';
    rtnHtml+= '</tr>\n';

    return rtnHtml;
}

/**
 * 약관 팝업 동의 후 닫기 이벤트
 */
function targetTermsAgree() {

    $('#' + pageObj.eventId).prop('checked', 'checked');

    if(pageObj.eventId == "chkAgree1")$('#targetTerms1').val("Y");
    else if(pageObj.eventId == "chkAgreeA2") $('#targetTerms2').val("Y");
    else return false;
}

//확인
function btn_farReg(){
    var check1 = $('input:checkbox[id="chkAgree1"]').is(":checked");
    var check2 = $('input:checkbox[id="chkAgreeA2"]').is(":checked");
    var chkRadion =  $("input[name=chkRadion]:checked").val();

    if($("#chkAgree1").length != 0){
        if(!check1){
            MCP.alert("제휴요금제 변경에 따른 제휴사 정보 제공에 동의해야합니다.", function(){
                $("#chkAgree1").focus();
            });
            return false;
        }
    }

    if(!check2){
        MCP.alert("요금제 변경을 위한 동의를 하셔야 요금제변경이 됩니다.", function(){
            $("#chkAgreeA2").focus();
        });
        return false;
    }

    var varData = '';
    if(chkRadion == 'S'){	//즉시변경

        //이번달 개통자가 즉시변경 시
        if($("#isActivatedThisMonth").val() == "Y"){
            MCP.alert("개통 당월에는 요금제 즉시 변경이 불가합니다.<br>예약변경을 이용하시거나 다음달에 요금제 변경을 진행해 주세요.", function(){
                $("#radDateType1").focus();
            });
            return false;
        }

        //이번달에 요금제 이미 변경한 사람이 즉시변경 시
        if($("#isFarPriceThisMonth").val() == "Y"){
            MCP.alert("요금제는 월 1회만 변경 가능합니다.<br>예약변경을 이용하시거나 다음달에 요금제 변경을 진행해 주세요.", function(){
                $("#radDateType1").focus();
            });
            return false;
        }

        KTM.Confirm("요금제 즉시변경 하시겠습니까?", function (){
            KTM.LoadingSpinner.show();
            var aSocAmnt = '';
            if($("#aSocAmnt1").val() != null){
                aSocAmnt = $("#aSocAmnt1").val().replaceAll("원","");
            }
            varData = ajaxCommon.getSerializedData({
                  ncn : $("#contractNum").val()
                ,toSocCode : $("#rateAdsvcCd").val()
                ,aSocAmnt : aSocAmnt
                ,tSocAmnt : $("#tSocAmnt").val()
            });

            var url = "/mypage/farPricePlanChgAjax.do";
            //페이지 호출
             $.ajax({
                    id:'farPricePlanChgAjax'
                    ,cache:false
                    ,url:url
                    ,data: varData
                    ,dataType:"json"
                      , success : function(data) {

                        if (data.RESULT_CODE== "00000") {
                            MCP.alert("요금제 변경이 완료되었습니다.",function(){
                                ajaxCommon.createForm({
                                        method:"post"
                                        ,action:"/mypage/farPricePlanView.do"
                                });

                                ajaxCommon.attachHiddenElement("ncn",$("#contractNum").val());
                                ajaxCommon.formSubmit();
                            });

                        } else {
                            if(data.RESULT_MSG != ""){
                                  MCP.alert(data.RESULT_MSG);
                              }
                        }

                   KTM.LoadingSpinner.hide();

                },error:function(){
                    alert("오류가 발생했습니다.다시 시도해주세요.");
                    KTM.LoadingSpinner.hide();
                }

            });

            this.close();

        });



    } else if(chkRadion == 'P'){ //예약변경

        KTM.Confirm("요금제 예약변경 하시겠습니까?", function (){
            KTM.LoadingSpinner.show();
            varData = ajaxCommon.getSerializedData({
                contractNum : $("#contractNum").val()
                ,soc : $("#rateAdsvcCd").val()
                ,befChgRateCd : $("#prvRateCd").val()
                ,befChgRateAmnt : $("#instMnthAmt").val()
            });

            var url = "/mypage/farPricePlanResChgAjax.do";
            //페이지 호출
             $.ajax({
                    id:'farPricePlanResChgAjax'
                    ,cache:false
                    ,url:url
                    ,data: varData
                    ,dataType:"json"
                    , success : function(data) {
                        if(data.resultCode == "00"){
                            if(data.RESULT_CODE == 'S'){
                                if(data.message != ""){
                                    MCP.alert(data.message);
                                } else{
                                    MCP.alert("요금제  예약 변경이 완료되었습니다.\n\n 익월 1일부터 변경된 요금제로 반영됩니다",function(){
                                        $('.c-icon--close').trigger('click');
                                            ajaxCommon.createForm({
                                                method:"post"
                                                ,action:"/mypage/farPricePlanView.do"
                                        });

                                        ajaxCommon.attachHiddenElement("ncn",$("#contractNum").val());
                                        ajaxCommon.formSubmit();
                                    });

                                }

                                KTM.LoadingSpinner.hide();

                            } else {

                                var message = "요금제 예약에 실패했습니다." ;
                                if (data.message2 != null && data.message2 != "") {
                                    message = data.message2 ;
                                }
                                MCP.alert(message);
                                KTM.LoadingSpinner.hide();
                            }

                        }else{
                            MCP.alert(data.message);
                            KTM.LoadingSpinner.hide();
                        }
                    },error:function(){
                        alert("오류가 발생했습니다.다시 시도해주세요.");
                        KTM.LoadingSpinner.hide();
                    }
            });
            this.close();
        });
    }
}