;


var SIMPLE_FQC_DLT_MSG = {
    MSN_01_RCODE_00: {
        MSG: "<b class='u-fs-18'>고객님께서는 아직 개통회선이 없거나,<br/>정회원 인증을 하지 않으셨습니다!</b><br/><br/>개통을 완료했지만 5천원 미만 요금제를<br/> 사용중이시라면, <br/>개통완료 스탬프는 적립이 불가합니다.<br/><br/>개통 전이시라면<br/><span class='u-co-red'><b>[kt M모바일 홈페이지]에서 요금제 가입,</b></span><br/>정회원 인증 전이라면 <span class='u-co-red'><b>[마이페이지]에서<br/> 정회원 인증</b></span>을 해주세요!",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_01_RCODE_00"
    },
    MSN_01_RCODE_01: {
        MSG: "<b class='u-fs-18'>고객님께서는 kt M모바일 홈페이지를 통해 가입하지 않아,<br/>스탬프 적립이 되지 않습니다!</b><br/><br/>*스탬프를 받으시려면kt M모바일 홈페이지에서<br/> 추가 회선을 가입 후 대표회선으로 변경해주세요!",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_01_RCODE_01"
    },
    MSN_01_RCODE_02: {
        MSG: "스탬프 적립이 완료되었습니다.",
        OPEN_TYPE: "nothing"
    },
    MSN_02_RCODE_00: {
        MSG: "미션 정책이 없습니다. 관리자에서 문의 하세요!",
        OPEN_TYPE: "alert"
    },
    MSN_02_RCODE_01: {
        MSG: "<b class='u-fs-18'>이벤트 공유하기</b><br/><br/>친구에게 이벤트를 공유해 보세요<br/>바로 스탬프 적립해 드려요!<br/><br/><i class='c-icon c-icon--kakao' aria-hidden='true'></i>",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_02_RCODE_02"
    },
    MSN_02_RCODE_02: {
        MSG: "스탬프 적립이 완료되었습니다.",
        OPEN_TYPE: "nothing"
    },
    MSN_03_RCODE_00: {
        MSG: "미션 정책이 없습니다. 관리자에서 문의 하세요!",
        OPEN_TYPE: "alert"
    },
    MSN_03_RCODE_01: {
        MSG: "<b class='u-fs-18'>마케팅 정보 수신에 동의하고 <br/>kt M모바일만의 혜택을 <br/>놓치지 마세요!</b><br/><br/>* 정보수정 선택 후 정보/광고 수신 동의에 동의하셔야 스템프가 적립됩니다.",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_03_RCODE_01"
    },
    MSN_03_RCODE_02: {
        MSG: "스탬프 적립이 완료되었습니다.",
        OPEN_TYPE: "nothing"
    },
    MSN_04_RCODE_00: {
        MSG: "<b class='u-fs-18'>고객님께서는 아직 개통회선이 없거나,<br/>정회원 인증을 하지 않으셨습니다!</b><br/><br/>개통 전이시라면<br/><span class='u-co-red'><b>[kt M모바일 홈페이지]에서 요금제 가입,</b></span><br/>정회원 인증 전이라면 <span class='u-co-red'><b>[마이페이지]에서<br/> 정회원 인증</b></span>을 해주세요!",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_01_RCODE_00"
    },
    MSN_04_RCODE_01: {
        MSG: "<b class='u-fs-18'>요금제 사용 리뷰를 남기고<br/>스탬프를 받아보세요.<br/>리뷰만 써도 미션 달성!</b>",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_04_RCODE_01"
    },
    MSN_04_RCODE_02: {
        MSG: "스탬프 적립이 완료되었습니다.",
        OPEN_TYPE: "nothing"
    },
    MSN_05_RCODE_00: {
        MSG: "<b class='u-fs-18'>고객님께서는 아직 개통회선이 없거나,<br/>정회원 인증을 하지 않으셨습니다!</b><br/><br/>개통 전이시라면<br/><span class='u-co-red'><b>[kt M모바일 홈페이지]에서 요금제 가입,</b></span><br/>정회원 인증 전이라면 <span class='u-co-red'><b>[마이페이지]에서<br/> 정회원 인증</b></span>을 해주세요!",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_01_RCODE_00"
    },
    MSN_05_RCODE_01: {
        MSG: "<b class='u-fs-18'>친구를 초대하고<br/>스탬프를 받아보세요.<br/><br/>공유한ID로 친구가 가입하면<br/>스탬프를 받을 수 있어요.</b>",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_05_RCODE_01"
    },
    MSN_05_RCODE_02: {
        MSG: "스탬프 적립이 완료되었습니다.",
        OPEN_TYPE: "nothing"
    },
    MSN_06_RCODE_00: {
        MSG: "<b class='u-fs-18'>고객님께서는 아직 개통회선이 없거나,<br/>정회원 인증을 하지 않으셨습니다!</b><br/><br/>개통 전이시라면<br/><span class='u-co-red'><b>[kt M모바일 홈페이지]에서 요금제 가입,</b></span><br/>정회원 인증 전이라면 <span class='u-co-red'><b>[마이페이지]에서<br/> 정회원 인증</b></span>을 해주세요!",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_01_RCODE_00"
    },
    MSN_06_RCODE_01: {
        MSG: "<b class='u-fs-18-22'>친구를 초대하고<br/>스탬프를 받아보세요.<br/><br/>공유한ID로 친구가 가입하면<br/>스탬프를 받을 수 있어요.</b>",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_05_RCODE_01"
    },
    MSN_06_RCODE_02: {
        MSG: "스탬프 적립이 완료되었습니다.",
        OPEN_TYPE: "nothing"
    },
    MSN_07_RCODE_00: {
        MSG: "<b class='u-fs-18'>고객님께서는 아직 개통회선이 없거나,<br/>정회원 인증을 하지 않으셨습니다!</b><br/><br/>개통 전이시라면<br/><span class='u-co-red'><b>[kt M모바일 홈페이지]에서 요금제 가입,</b></span><br/>정회원 인증 전이라면 <span class='u-co-red'><b>[마이페이지]에서<br/> 정회원 인증</b></span>을 해주세요!",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_01_RCODE_00"
    },
    MSN_07_RCODE_01: {
        MSG: "<b class='u-fs-22'>트리플할인 미션에<br/>참여해 보세요.<br/><br/>트리플할인 페이지를 통해<br/>인터넷 가입 시 미션 달성!<br/></b>",
        OPEN_TYPE: "simpleDialog",
        SHOW_BUTTOM:"divMSN_07_RCODE_01"
    },
    MSN_07_RCODE_02: {
        MSG: "스탬프 적립이 완료되었습니다.",
        OPEN_TYPE: "nothing"
    }
};



let pageObj = {
    msnCnt:0
    ,bnfCntMapList:{}
    ,bnfLvlMapList:{}
}





$(document).ready(function(){



    $('[id^="btnFqcMsnTp"]').on('click', function() {
        let msnTpCd = $(this).data("msnTpCd");

        var varData = ajaxCommon.getSerializedData({
            msnTpCd: msnTpCd
        });

        ajaxCommon.getItem({
                id:'setFqcDltAjax'
                ,cache:false
                ,async:false
                ,url:'/fqc/setFqcDltAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    let fqcObj = jsonObj.RESULT_OBJ;
                    let stateCode = fqcObj.stateCode ;

                    if (stateCode =="02") {
                        pageObj.msnCnt++;
                        $("#btnFqcMsnTp" + msnTpCd).addClass("is-stamped");
                        setMsnCnt();
                    }

                    if (SIMPLE_FQC_DLT_MSG["MSN_"+msnTpCd+"_RCODE_"+stateCode ] != undefined) {
                        let openType = SIMPLE_FQC_DLT_MSG["MSN_"+msnTpCd+"_RCODE_"+stateCode ].OPEN_TYPE;
                        if (openType != undefined && openType == "simpleDialog" ) {
                            $("#simpleDialog ._detail").html(SIMPLE_FQC_DLT_MSG["MSN_"+msnTpCd+"_RCODE_"+stateCode ].MSG);
                            $("._simpleDialogButton").hide();
                            $("#"+SIMPLE_FQC_DLT_MSG["MSN_"+msnTpCd+"_RCODE_"+stateCode ].SHOW_BUTTOM).show();

                            var el = document.querySelector('#simpleDialog');
                            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                            modal.open();
                        } else if (openType != undefined && openType == "nothing" ) {
                            //아무것도 ...
                        } else {
                            MCP.alert(SIMPLE_FQC_DLT_MSG["MSN_"+msnTpCd+"_RCODE_"+stateCode ].MSG);
                        }
                    } else {
                        if (stateCode =="02") {
                            MCP.alert("스탬프 적립이 완료되었습니다.");
                        } else if (stateCode =="01") {
                            MCP.alert("성공적으로 참여되었습니다!");
                        } else {
                            MCP.alert("정회원 인증 후 참여 가능합니다.");
                        }
                    }
                }
            });
    });


    let setMsnCnt = function() {
        let userName = $("#userName").val();
        //pageObj.msnCnt = pageObj.msnCnt +3;

        let targetWidth = $("#divProgress .freq-progress-stamp").width() / 7;


  //      pageObj.msnCnt = 7;
//        console.log('[animate] pageObj.msnCnt:', pageObj.msnCnt, ', targetWidth:', targetWidth);

        if (pageObj.msnCnt == 0) {
            $("#divProgress p").html("현재  <span>0</span> M탬프 달성");
            $("#divProgress .freq-progress-status").html("<span>0</span>/<span>7</span>");
            $("#divBanner .freq-banner-complete").html("<p><span>" + userName + "</span>님, <b>아직 적립된 스탬프가 없습니다.</p>");
            $("#divBanner .freq-banner-gift").html("");
        } else if (pageObj.msnCnt == 7) {
            targetWidth  = targetWidth * pageObj.msnCnt +2;
            $("#divProgress p").html("현재  <span>" + pageObj.msnCnt + "</span> M탬프 달성");
            $("#divProgress .freq-progress-status").html("<span>" + pageObj.msnCnt + "</span>/<span>7</span>");
            $("#divProgress .mark").animate({ width: targetWidth + "px" }, 1000);

            $("#divBanner .freq-banner-complete").html("<p><span>" + userName + "</span>님, <b>" + pageObj.msnCnt + "</b>개 스탬프를 달성했어요</p>");
            if (pageObj.bnfCntMapList[7] != undefined) {
                $("#divBanner .freq-banner-gift").html("모든 스탬프를 달성했어요! "+pageObj.bnfCntMapList[7].bnfNm+"을 받아보세요!");
                $("#divComplete").addClass("is-completed ");
            }
        } else {
            targetWidth  = targetWidth * pageObj.msnCnt +2;
            $("#divProgress p").html("현재  <span>" + pageObj.msnCnt + "</span> M탬프 달성");
            $("#divProgress .freq-progress-status").html("<span>" + pageObj.msnCnt + "</span>/<span>7</span>");
            $("#divProgress .mark").animate({ width: targetWidth  + "px" }, 1000);
            $("#divBanner .freq-banner-complete").html("<p><span>" + userName + "</span>님, <b>" + pageObj.msnCnt + "</b>개 스탬프를 달성했어요</p>");

            if (pageObj.bnfCntMapList[pageObj.msnCnt] != undefined ) {
                let nowBnf = pageObj.bnfCntMapList[pageObj.msnCnt];
                let nextLvl = ++nowBnf.bnfLvl ;
                let nextBnf= pageObj.bnfLvlMapList[nextLvl] ;
                if (nextBnf != undefined) {
                    let cntDiff = nextBnf.msnCnt - nowBnf.msnCnt;
                    if (cntDiff > 0) {
                        $("#divBanner .freq-banner-gift").html(cntDiff + "개 더 달성하면 "+nextBnf.bnfNm+" 받아요!");
                    }
                }
            } else {
                let nextBnf= pageObj.bnfLvlMapList[1] ;
                if (nextBnf != undefined) {
                    let cntDiff = nextBnf.msnCnt - pageObj.msnCnt;
                    if (cntDiff > 0) {
                        $("#divBanner .freq-banner-gift").html(cntDiff + "개 더 달성하면 "+nextBnf.bnfNm+" 받아요!");
                    }
                }
            }
        }
    }

    let getFqcInfo = function () {
        ajaxCommon.getItem({
            id: 'getFqcInfo'
            , cache: false
            , url: '/fqc/getFqcProAjax.do'
            , data: ''
            , dataType: "json"
            , async: false
        }, function (jsonObj) {
            if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                let fqcObj = jsonObj.FQC_OBJ;
                pageObj.msnCnt = jsonObj.MSN_CNT;
                if (jsonObj.FQC_PLCY_BNF_CNT_LIST != null) {
                    pageObj.bnfCntMapList = jsonObj.FQC_PLCY_BNF_CNT_LIST;
                    pageObj.bnfLvlMapList = jsonObj.FQC_PLCY_BNF_LVL_LIST;
                } else {
                    pageObj.bnfCntMapList = {};
                    pageObj.bnfLvlMapList = {};
                }

                for (const msnTpObj of Object.values(fqcObj)) {
                    //msnTpObj.dtlCd
                    if (msnTpObj.useYn == "02") {
                        $("#btnFqcMsnTp" + msnTpObj.dtlCd).addClass("is-stamped");
                    }
                }
                setMsnCnt();
                //console.log(value);
            } else if ("-3" == jsonObj.RESULT_CODE) {
                var el = document.querySelector('#notiDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();
            } else {
                let msg ;
                if (jsonObj.DESC == null ) {
                    msg ="오류 발생 했습니다. "
                } else {
                    msg = jsonObj.DESC;
                }
                MCP.alert(msg);

            }
        });
    };


    // 카카오톡 공유하기
    $("#btnKakaoShare").click(function () {
        var eventImg =  $("#eventImg").val();       ///   resources/upload/fileBoard/202504242313210031631V9.png
        let ntcartSeq = $('#eventSeq').val();
        var ntcartSubject = $("#eventSubject").val();  //초대ID로 가입하면? 4/30까지만 14만원 혜택
        var mobileLink = "/m/event/eventDetail.do?ntcartSeq=" + ntcartSeq;
        var webLink = "/event/eventDetail.do?ntcartSeq=" + ntcartSeq;

        if (ntcartSeq == "" ||ntcartSubject == "" ||$("#eventImg").val() == "") {
            MCP.alert("이벤트 정보가 없습니다. ");
            return;
        }

        var varData = ajaxCommon.getSerializedData({
            trtmRsltSmst: ntcartSeq
            , prcsSbst: webLink
        });
        ajaxCommon.getItem({
                id: 'frndSendAjax'
                , cache: false
                , url: '/event/setKaKaoShareEventAjax.do'
                , data: varData
                , dataType: "json"
            }
            , function (jsonObj) {
                let uetSeq = jsonObj.UET_SEQ;
                var v_link = "https://www.ktmmobile.com";
                var v_img = v_link + eventImg;
                var v_mobileLink = v_link + mobileLink;
                var v_webLink = v_link + webLink;

                let paraObj = {
                    objectType: 'feed',
                    content: {
                        title: ntcartSubject,
                        description: '',
                        imageUrl: v_img,
                        link: {
                            mobileWebUrl: v_mobileLink,
                            webUrl: v_webLink,
                        },
                    },
                    buttons: [
                        {
                            title: '자세히 보기',
                            link: {
                                mobileWebUrl: v_mobileLink,
                                webUrl: v_webLink,
                            },
                        },
                    ],
                    serverCallbackArgs: {
                        strUetSeq: uetSeq + '', // 사용자 정의 파라미터 설정
                    },
                }

                // 사용할 앱의 JavaScript 키 설정 ,본인 자바스크립트 API KEY 입력
                if (!Kakao.isInitialized()) {
                    Kakao.init('e302056d1b213cb21f683504be594f25'); // 운영
                }
                //Kakao.Share.createDefaultButton(obj);
                Kakao.Share.sendDefault(paraObj);

                var el = document.querySelector('#simpleDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.close();

            });


    });

    $("#btnReload").click(function() {
        $("#divProgress .mark").animate({ width:  "0px" }, 1000);

        $(".freq-stamp__item-button").each(function(){
            $(this).removeClass("is-stamped");
        });
        getFqcInfo();
    });

    getFqcInfo();

});
