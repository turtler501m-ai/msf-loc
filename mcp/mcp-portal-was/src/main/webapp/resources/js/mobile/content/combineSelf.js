;



VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.chkAgree = "결합을 위한 필수사항에 동의하시기 바랍니다. ";




var pageObj = {
    eventId:""
    ,combineList:[]
    ,isComBin :false
    ,serviceNm:""
}


///RESULT_COMBINE_LIST
$(document).ready(function (){


    ajaxCommon.getItemNoLoading({
        id:'getNotice'
        ,cache:false
        ,url:'/termsPop.do'
        ,data: "cdGroupId1=TERMSCOM&cdGroupId2=combineSoloNotice"
        ,dataType:"json"
    } ,function(jsonObj){
        var inHtml = unescapeHtml(jsonObj.docContent);
        $('#combineSolo').html(inHtml);
    });

    getBannerList();

    var combineSelfGuideSwiper = new Swiper('#combineSelfGuide .swiper-container', {
        pagination : {
            el : '.swiper-pagination',
            type: 'fraction',
        },
    });

    //아무나솔로 결합 대상 요금제
    ajaxCommon.getItem({
            url: "/termsPop.do",
            type: "GET",
            dataType: "json",
            data: "cdGroupId1=INFO&cdGroupId2=COMB00016",
            async: false
        }
        ,function(data){
            var inHtml = unescapeHtml(data.docContent);
            $('#combineSelfTable').html(inHtml);
        }
    );

  //아무나솔로 결합 가능 요금제
    ajaxCommon.getItem({
            url: "/termsPop.do",
            type: "GET",
            dataType: "json",
            data: "cdGroupId1=INFO&cdGroupId2=COMB00017",
            async: false
        }
        ,function(data){
            var inHtml = unescapeHtml(data.docContent);
            $('#combineSelfPlan').html(inHtml);
        }
    );

    $("#btnLogin").click(function(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/m/loginForm.do"
        });

        ajaxCommon.attachHiddenElement("uri","/m/content/combineSelf.do#combineSelf");
        ajaxCommon.formSubmit();
    });


    //가입정보 확인
    $("#btnOk").click(function(){
        if($("#certify").val() != 'Y'){
            MCP.alert("인증을 받아주세요.");
            return;
        } else {
            var varData = ajaxCommon.getSerializedData({
                menu:$("#menuType").val()
            });

            ajaxCommon.getItemNoLoading({
                    id:'accountCheck'
                    ,cache:false
                    ,url:'/content/getNoLoginRateInfoAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        var dataObj = jsonObj.DATA_OBJ
                        $("#ncn").empty();
                        $("#ncn").append("<option  value='"+dataObj.svcCntrNo+"'>"+dataObj.ctn+"</option>");
                        $("#ncn").parent().addClass("has-value");
                        $("#divRate").show();
                        $("#spMoCtn").html("1");
                        $("#rateNm").val(dataObj.rateNm);
                        $("#divCheckSession").hide();
                        $("#divResult").show();
                        checkCombineSelf();

                        $("#btnSelectCTN").hide();
                        $(".masking-badge").hide();
                        $("#headTxt").html("&nbsp;");
                    } else {
                        var resultMsg = jsonObj.RESULT_MSG;
                        if (resultMsg!= null) {
                            MCP.alert(resultMsg);
                        } else {
                            MCP.alert("가입정보 확인 실패 하였습니다.");
                        }
                    }
                });
        }
    });

    $("#ncn").change(function(){
        initDom();
        getRateInfo();
    });

    var getRateInfo = function() {
        var varData = ajaxCommon.getSerializedData({
            ncn:$("#ncn").val()
            ,menuPara:"combineSelf"
        });

        ajaxCommon.getItemNoLoading({
                id:'getRateInfo'
                ,cache:false
                ,url:'/content/getRateInfoAjax.do'
                ,data: varData
                ,dataType:"json"
                , async:false
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    $("#rateNm").val(jsonObj.RESULT_RATE_NM);
                    $("#divRate").show();
                    checkCombineSelf();

                } else {
                    MCP.alert("요금제 정보 조회 실패 하였습니다.");
                }
            });
    }

    var initDom = function() {
        $("#chkAgree").prop("checked",false);
        $("#divReg").hide();
        $("#divRate").hide();
        $("#divResultInfo").empty();
        isValidateNonEmptyStep1();
    }


    var checkCombineSelf = function() {
        var varData = ajaxCommon.getSerializedData({
            menu:$("#menuType").val()
            ,ncn:$("#ncn").val()
        });
        $("#divResultInfo").empty();
        pageObj.combineList = [];

        ajaxCommon.getItem({
                id:'checkCombineSelf'
                ,cache:false
                ,url:'/content/checkCombineSelfAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                var arr =[];

                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    if (jsonObj.RESULT_COMBINE_LIST) {
                        pageObj.combineList = jsonObj.RESULT_COMBINE_LIST;
                    }

                    pageObj.isComBin = jsonObj.IS_COMBIN;
                    pageObj.serviceNm = jsonObj.RESULT_SERVICE_NM;
                    if (jsonObj.IS_COMBIN) {
                        arr.push("<div class='combine-self-success'>\n");
                        arr.push("	<p>\n");
                        arr.push("		결합은 가능하나<br /><b class='u-co-black'>결합 혜택이 추가로 제공되지 않습니다.</b>\n");
                        arr.push("	</p>\n");
                        arr.push("	<p class='combine-self-success__subtxt'>“아무나 결합/아무나 가족결합”의 데이터제공 혜택은<br />중복으로 제공이 불가합니다.</p>\n");
                        arr.push("</div>\n");
                    } else {
                        arr.push("<div class='combine-self-success'>\n");
                        arr.push("	<p>\n");
                        arr.push("		아무나 SOLO 결합을 신청하실 경우<br /><b>“<em>" + jsonObj.RESULT_SERVICE_NM + "</em>”이 제공됩니다.</b>\n");
                        arr.push("	</p>\n");
                        arr.push("</div>\n");
                    }

                    $("#divResultInfo").html(arr.join(''));
                    $("#divReg").show();

                    var el = document.querySelector('#divCertifySms');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    if( modal._isOpen ){
                        modal.close();
                    }

                } else if ("0001" == jsonObj.RESULT_CODE) {
                    arr.push("	<div class='combine-self-fail'>\n");
                    arr.push("		<p>\n");
                    arr.push("			<b>이미 신청 이력이 존재합니다.</b>\n");
                    arr.push("		</p>\n");
                    arr.push("		<p class='combine-self-fail__subtxt'>결합 내역은 마이페이지에서 확인이 가능합니다.</p>\n");
                    arr.push("	</div>\n");
                    arr.push("	<div class='c-button-wrap'>\n");
                    arr.push("		<a class='c-button c-button--full c-button--primary' href='/mypage/myCombineList.do' title='결합 내역 조회 페이지 바로가기'>결합 내역 조회</a>\n");
                    arr.push("	</div>\n");

                    $("#divResultInfo").html(arr.join(''));

                    var el = document.querySelector('#divCertifySms');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    if( modal._isOpen ){
                        modal.close();
                    }
                } else if ("0004" == jsonObj.RESULT_CODE) {
                    arr.push("	<div class='combine-self-fail'>\n");
                    arr.push("		<p>\n");
                    arr.push("			현재 사용하시는 요금제는<br /><b>아무나 SOLO 결합 대상 요금제가 아닙니다.</b>\n");
                    arr.push("		</p>\n");
                    arr.push("		<p class='combine-self-fail__subtxt'>아무나 SOLO 결합을 원하실 경우 대상요금제로 변경 후<br />다시 시도 해 주세요.</p>\n");
                    arr.push("	</div>\n");
                    arr.push("	<div class='c-button-wrap'>\n");
                    arr.push("		<a class='c-button c-button--full c-button--primary' href='/m/mypage/farPricePlanView.do' title='요금제 변경 페이지 바로가기'>요금제 변경 바로가기</a>\n");
                    arr.push("	</div>\n");

                    $("#divResultInfo").html(arr.join(''));

                    var el = document.querySelector('#divCertifySms');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    if( modal._isOpen ){
                        modal.close();
                    }
                } else {
                    var msg = jsonObj.RESULT_MSG;
                    if (msg != null && msg != "") {
                        MCP.alert(msg);
                    } else {
                        MCP.alert("결합 대상 여부 확인 실패 하였습니다.");
                    }

                    $("#divResultInfo").html('');

                }
            });
    }


    $("#btnRegCombin").click(function () {
        validator.config = {};
        validator.config['chkAgree'] = 'isNonEmpty';

        if (validator.validate()) {
            KTM.Confirm('아무나 SOLO 결합을 신청하시겠습니까? ', function () {

                var varData = ajaxCommon.getSerializedData({
                    menu: $("#menuType").val()
                    ,ncn:$("#ncn").val()
                });

                this.close();
                ajaxCommon.getItem({
                        id:'regCombine'
                        ,cache:false
                        ,url:'/content/regCombineSelfAjax.do'
                        ,data: varData
                        ,dataType:"json"
                    }
                    ,function(jsonObj){

                        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                            var isInternet = false ;
                            if (null != pageObj.combineList && pageObj.combineList.length > 0 ) {
                                pageObj.combineList.forEach((item, index) => {
                                    console.log("["+index+"]" +  item.svcContDivNm );
                                    if (item.svcContDivNm.includes("Internet")) {
                                        isInternet = true;
                                    }
                                });
                            }
                            var el = null;
                            if(isInternet) {
                                el = document.querySelector('#combinComplete');
                            } else {
                                el = document.querySelector('#combinCompleteTriple');
                            }
                            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                            modal.open();
                        } else if ("9999" ==  jsonObj.RESULT_CODE ) {
                            var alertMessage = jsonObj.RESULT_MSG ? jsonObj.RESULT_MSG : "결합 신청에 실패하였습니다.";
                            MCP.alert(alertMessage );
                        } else {
                            var alertMessage = jsonObj.RESULT_MSG ? jsonObj.RESULT_MSG : "결합 신청에 실패하였습니다.";
                            if ("1999" == jsonObj.RESULT_CODE) {
                                alertMessage = "결합이 지연되고 있습니다.";
                            }
                            MCP.alert(alertMessage + "<br/>다시 시도해 주시기 바랍니다.", function() {
                                //location.reload();
                            });
                        }
                    });
                return ;
            });
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg());
        }
    });


    $("._complete").click(function(){
        location.reload();
       /* $("#divReg").hide();
        var arr =[];
        if (pageObj.isComBin) {
            arr.push("<div class='combine-self-success'>\n");
            arr.push("	<p>\n");
            arr.push("		결합을 완료 하였습니다.<br /><b class='u-co-black'>결합 혜택이 추가로 제공되지 않습니다.</b>\n");
            arr.push("	</p>\n");
            arr.push("	<p class='combine-self-success__subtxt'>“아무나 결합/아무나 가족결합”의 데이터제공 혜택은<br />중복으로 제공이 불가합니다.</p>\n");
            arr.push("</div>\n");
        } else {
            arr.push("<div class='combine-self-success'>\n");
            arr.push("	<p>\n");
            arr.push("		아무나 SOLO 결합을 완료 하였습니다<br /><b>“<em>" + pageObj.serviceNm  + "</em>”이 제공됩니다.</b>\n");
            arr.push("	</p>\n");
            arr.push("</div>\n");
        }

        $("#divResultInfo").html(arr.join(''));*/
    });




    $("#chkAgree").click(function(){
        isValidateNonEmptyStep1();
    });

    if ($("#ncn").val() !=null) {
        getRateInfo();
    }
});

function targetTermsAgree() {
    $('#' + pageObj.eventId).prop('checked', 'checked');
    isValidateNonEmptyStep1();
};
var fnSetEventId = function(eventId){
    pageObj.eventId = eventId;
};

var isValidateNonEmptyStep1 = function() {
    validator.config={};
    validator.config['chkAgree'] = 'isNonEmpty';

    if (validator.validate(true)) {
        $('#btnRegCombin').removeClass('is-disabled');
    } else {
        $('#btnRegCombin').addClass('is-disabled');
    }
} ;

function getBannerList() {
    var varData = ajaxCommon.getSerializedData({
         bannCtg : 'E005'
    });

    ajaxCommon.getItem({
        id : 'bannerListAjax'
        , cache : false
        , async : false
        , url : '/m/bannerListAjax.do'
        , data : varData
        , dataType : "json"
    }
    ,function(result){
        if(result.result.length > 0){
            for(var i = 0; i < result.result.length; i ++){
                var html = "";

                html += "<div class='swiper-slide' href='javascript:void(0);' onclick='insertBannAccess(\"" + result.result[i].bannSeq + "\", \"" + result.result[i].bannCtg + "\")' addr='" + result.result[i].mobileLinkUrl + "' style='background-color:" + result.result[i].bgColor + "'>";
                html+= '<button class="swiper-event-banner__image">';
                html += "<img src='" + result.result[i].mobileBannImgNm + "' alt='" + result.result[i].imgDesc + "'>";
                html += "</button>";
                html += "</div>";
                html += "<input type='hidden' value='" + result.result[i].bannSeq + "'>";

                $(".swiper-wrapper").append(html);

                $("#swiperCombineSelf").show();
            }
            var swiperInvitBanner = new Swiper("#swiperCombineSelf", {
                spaceBetween : 10,
                slidesPerView : 1,
            });
        }else{
            $("#swiperCombineSelf").hide();
        }
            $(".swiper-slide").on('click', function(){
                var parameterData = ajaxCommon.getSerializedData({
                    eventPopTitle : '아무나 SOLO 이벤트'
                });
                $("#eventPop").remove();
                openPage('eventPop', $(this).attr("addr"), parameterData)
            });
    });
}
