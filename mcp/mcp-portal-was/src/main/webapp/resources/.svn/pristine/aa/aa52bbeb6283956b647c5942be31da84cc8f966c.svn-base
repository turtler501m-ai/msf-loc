var pageObj = {
    niceType:""
    , initAddition:0
    , applDataForm:{}
    , niceHistSeq:0
    , authObj:{}
    , authPassObj:{}
    , insrNiceHistSeq:0
    , niceHistInsrProdSeq:0
    , startTime:0
    , eventId:""
    , step:1
    , clauseJehuRateCount:0
    , clauseFinanceRateCount:0
    , additionBetaList:[]
    , additionKeyList:[]
    , additionTempKeyList:[]
    , reqAddition:[]
    , reqAdditionPrice:0
    , insrProdList:[]
    , insrProdCd:""      //안심보험 선택 코드값
    , insrProdObj:null   //{}
    , addrFlag:""
    , requestKey:0       //서식지 등록후 key값
    , resNo:0            //서식지 등록후 예약번호
    , niceResSmsObj:{}   //셀프개통 신규 인증한 SMS인증 정보
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증
    , checkCnt:0         //callback 호출 건수
    , prdtNm:""          //상품명
    , rateNm:""          //요금제명
    , modelSalePolicyCode : "" //init 정책정보
    , modelId:""
    , telAdvice:false   //전화상담 여부
    , prmtIdList:[]     // 사은품 프로모션 코드
    , prdtIdList:[]    // 사은품 제품ID
    , phoneObj:null
    , giftList:[]
    , custPoint:null
    , cardDcCd:""
    , rateObj:null
    , fnReqPreCheckCount:0
    , priceLimitObj:null
    , usimOrgnId:""
    , maxDcAmtInt:0
    , cid:""
    , checkCbCnt: 0
    , isCallbackIng: false
}

$(document).ready(function() {    
    
    if ($("#isAuth").val() == "1") {
		$('#btnUsimChg').show();
    } else {
		$('#btnUsimChg').hide();
	}

 	// 본인인증 전 추가 유효성 검사 > niceAuth.js 참고
	simpleAuthvalidation= function(){
		if ($("#isUsimNumberCheck").val() != "1") {
        	MCP.alert("유심번호 유효성 체크하여 주시기 바랍니다. ");
            return false;
        }

        if (!$('#clauseSimpleOpen').prop('checked')) {
       	 	MCP.alert("본인인증 절차 진행에 동의해야 합니다.");
            return false;
        }
        
        var cstmrType = $("#selSvcCntrNo option:selected").attr("data-cstmr_type");
        if (cstmrType != "I") {
            MCP.alert("지원하지 않는 고객유형 입니다.");
            return false;
        }
        
        return true;
	}

	// 유심번호 유효성 체크
    $('#btnUsimNumberCheck').click(function(){
        if ($("#isUsimNumberCheck").val() == "1") {
            MCP.alert("유심번호 유효성 체크 하였습니다. ");
            return ;
        }
        if ($("#selSvcCntrNo option:selected").attr("data-age") == -1) {
            MCP.alert("해지고객은 해당서비스를 이용할수 없습니다. <br>고객센터(1899-5000)로 문의 바랍니다.");
            return ;
        }
        if ($("#selSvcCntrNo option:selected").attr("data-age") < 19) {
            MCP.alert("만 19세 이상 성인 고객만 셀프 변경이 가능합니다. <br>유심 변경을 원하실 경우 고객센터(1899-5000)로 문의 바랍니다.");
            return ;
        }

        validator.config={};
        validator.config['reqUsimSn'] = 'isNumFix19';

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if (validator.validate()) {
           var varData = ajaxCommon.getSerializedData({
               iccId:$.trim($("#reqUsimSn").val())
            });

            ajaxCommon.getItem({
               id:'moscIntmMgmtAjax'
               ,cache:false
               ,url:'/msp/moscIntmMgmtAjax.do'
               ,data: varData
               ,dataType:"json"
               ,errorCall : function () {
                  MCP.alert("유심번호 유효성 체크가 불가능합니다. <br>잠시 후 다시 시도하시기 바랍니다. ")
              }
            }
           ,function(jsonObj){
              if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE ) {
                var usimOrgnId = jsonObj.USIM_ORGN_ID ;

                if (usimOrgnId == undefined || usimOrgnId == "" ) {
                    usimOrgnId = $("#cntpntShopId").val();
                }
                pageObj.usimOrgnId = usimOrgnId ;
                $("#isUsimNumberCheck").val("1");

                MCP.alert("입력하신 유심번호 사용 가능합니다. ",function (){
                    $("#reqUsimSn").prop("disabled", true);
                    $('#btnUsimNumberCheck').addClass('is-complete').html("유심번호 유효성 체크 완료");
                    $("#reqUsimSn").parent().addClass('has-safe').after("<p class='c-form__text'>유심번호 유효성 검증에 성공하였습니다.</p>");
                });

              } else {
                  var strMsg = "사용이 불가한 유심입니다. <br>다른 유심으로 시도 바랍니다.";
                  if (jsonObj.RESULT_MSG != null) {
                    strMsg = jsonObj.RESULT_MSG;
                  }
                  MCP.alert(strMsg,function (){
                    $selectObj = $("#reqUsimSn");
                    viewErrorMgs($selectObj, strMsg);
                  });
              }
           });

       } else {
        var errId = validator.getErrorId();
        MCP.alert(validator.getErrorMsg() ,function (){
            //console.log("errId==>" + errId);
            $selectObj = $("#"+errId);
            viewErrorMgs($selectObj, validator.getErrorMsg());
        });
       }

    });


    //유심변경
    $('#btnUsimChg').click(function(){	
        if ($("#isAuth").val() != "1") {
            MCP.alert("본인 인증이 완료되지 않았습니다. ");
            return false;
        }
        if ($("#isUsimNumberCheck").val() != "1") {
            MCP.alert("유심번호 유효성 체크하여 주시기 바랍니다. ");
            return ;
        }
	
        var varData = ajaxCommon.getSerializedData({
              svcContId:$("#selSvcCntrNo option:selected").val()
            , iccId:$.trim($("#reqUsimSn").val())
            , cntpntCd:$("#selSvcCntrNo option:selected").attr("data-cntpnt_cd")
        });

        ajaxCommon.getItem({
            id:'usimSelfChgAjax'
            ,cache:false
            ,url:'/mypage/usimSelfChgAjax.do'
            ,data: varData
            ,async:false
            ,dataType:"json"
        }, function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
              MCP.alert("유심 변경을 처리 중입니다.<br>전산처리에 1~2분 정도 걸릴 수 있으니, 화면을 닫지 말고 잠시만 기다려 주세요.", function(){
                if(pageObj.isCallbackIng) KTM.LoadingSpinner.show();
              });
              pageObj.checkCbCnt = 0;
              fnSelfUsimChgCallBack(jsonObj.MVNO_ORD_NO);
            }else if(jsonObj.RESULT_CODE == "AUTH01" || jsonObj.RESULT_CODE == "AUTH02"){  // AUTH 오류
                MCP.alert(jsonObj.ERROR_NE_MSG);
            }else if(jsonObj.RESULT_CODE == "STEP01" || jsonObj.RESULT_CODE == "STEP02"){ // STEP 오류
                MCP.alert(jsonObj.ERROR_NE_MSG);
            }else if(jsonObj.OSST_RESULT_CODE == "MCG_BIZ_E0422" || jsonObj.OSST_RESULT_CODE == "MCG_BIZ_E0421"){
              MCP.alert(jsonObj.ERROR_NE_MSG); // MP 호출건수 제한
            }else {
                MCP.alert("유심 변경에 실패하였으며 자세한 사항은 고객센터(114/무료)로 문의 부탁드립니다.");
            }
        })
    })
    
    // PASS 2단계(계좌인증) 중 첫번째
	$('#btnAutAccount').click(function(){
        validator.config={};
        validator.config['isPassAuth'] = 'isNonEmpty';
        validator.config['reqBankAut'] = 'isNonEmpty';
        validator.config['reqAccountAut'] = 'isNonEmpty';

        var cstmrName = $("#cstmrNameTemp").val();
        if (validator.validate()) {
           var varData = ajaxCommon.getSerializedData({
               service:"2"
               ,svcGbn:"2"
               ,bankCode:$("#reqBankAut").val()
               ,accountNo:$.trim($("#reqAccountAut").val())
               ,name:cstmrName
           });

          ajaxCommon.getItem({
               id:'niceAccountOtpNameAjax'
               ,cache:false
               ,url:'/nice/niceAccountOtpNameAjax.do'
               ,data: varData
               ,dataType:"json"
           }
           ,function(jsonObj){
               if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                   /* 이부분이 HIDDEN으로 존재해야 합니다.  */
                   $("#requestNo").val(jsonObj.request_no);
                   $("#resUniqId").val(jsonObj.res_uniq_id);

                   KTM.Dialog.closeAll();

                   setTimeout(function(){
                       var el = document.querySelector('#bankAutDialog2');
                       var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                       modal.open();
                   }, 500);


              } else {
                  MCP.alert("입력하신 계좌 정보로 인증이 불가합니다. 입력 정보를 확인 후 다시 시도해 주시기 바랍니다.");
              }
           });

       } else {
        var errId = validator.getErrorId();
        MCP.alert(validator.getErrorMsg() ,function (){
            $selectObj = $("#"+errId);
            viewErrorMgs($selectObj, validator.getErrorMsg());
        });
       }
    }); // end of event----------------------------
    
    // PASS 2단계(계좌인증) 중 두번째
	$('#btnCheckAccountOtpConfirm').click(function(){
        validator.config={};
        validator.config['isPassAuth'] = 'isNonEmpty';
        validator.config['reqBankAut'] = 'isNonEmpty';
        validator.config['reqAccountAut'] = 'isNonEmpty';
        validator.config['requestNo'] = 'isNonEmpty';
        validator.config['resUniqId'] = 'isNonEmpty';
        validator.config['textOpt'] = 'isNumFix6';

        var cstmrName = $("#cstmrNameTemp").val();

       if (validator.validate()) {
           var varData = ajaxCommon.getSerializedData({
               bankCode:$("#reqBankAut").val()
               ,accountNo:$.trim($("#reqAccountAut").val())
               ,name:cstmrName
               ,requestNo:$.trim($("#requestNo").val())
               ,resUniqId:$.trim($("#resUniqId").val())
               ,otp:$.trim($("#textOpt").val())
           });

          ajaxCommon.getItem({
               id:'niceAccountOtpNameAjax'
               ,cache:false
               ,url:'/nice/niceAccountOtpConfirmAjax.do'
               ,data: varData
               ,dataType:"json"
           }
           ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                   pageObj.niceResLogObj.authType = "A"; //PASS인증으로 저장        
                   insertNiceLog();
				   $("#isAuth").val("1");
				   
				   KTM.Dialog.closeAll();
				    
				   setTimeout(function(){
                       var el = document.querySelector('#bankAutDialog3');
                       var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                       modal.open();
                   }, 500);

				   $('#btnNiceAuth4').addClass('is-complete').html("2단계: 계좌 인증 완료");
			       $('#btnUsimChg').show();
		           pageObj.telAdvice = false;
		           return null;
				   
                } else {
                   MCP.alert("인증번호가 일치하지 않습니다.");
                }
           });

       } else {
        var errId = validator.getErrorId();
        MCP.alert(validator.getErrorMsg() ,function (){
            $selectObj = $("#"+errId);
            viewErrorMgs($selectObj, validator.getErrorMsg());
        });
       }
    });

})

var fnNiceCert = function(prarObj) {

    var strMsg= "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;

    //본인인증
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {
        cstmrType = $("#selSvcCntrNo option:selected").attr("data-cstmr_type");
        if (cstmrType == "I") {
            //개인고객
            strMsg = "고객 정보와 본인인증한 정보가 틀립니다." ;
        } else {
            MCP.alert("지원하지 않는 고객유형 입니다.");
            return ;
        }

        var authInfoJson= {contractNum: $("#contractNum").val(), authType: prarObj.authType};
        var isAuthDone = mypageAuthCallback(authInfoJson);

		if(isAuthDone){ // 본인인증 최종 성공
			pageObj.authObj= prarObj;
			MCP.alert("본인인증에 성공 하였습니다.");
            $('#btnUsimChg').show();
            pageObj.telAdvice = false;
            return null;
		}else{

            var resultCd= niceAuthObj.resAuthOjb.RESULT_CODE;
            var errorMsg= niceAuthObj.resAuthOjb.RESULT_MSG;

            if (resultCd == "LOGIN") {
                MCP.alert(errorMsg, function () {
                    location.href = '/loginForm.do';
                });
                return null;
            }

            strMsg= (errorMsg == undefined) ? strMsg : errorMsg;
			MCP.alert(strMsg);
            return null;
		}
    }
}


function searchSvcCntrNo(){
	document.selfFrm.ncn.value = $("#selSvcCntrNo").val();
	document.selfFrm.submit();

}

var viewErrorMgs = function($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};

function fnSelfUsimChgCallBack(mvnoOrdNo) {

  pageObj.isCallbackIng = true;

  var varData = ajaxCommon.getSerializedData({
    mvnoOrdNo: mvnoOrdNo
  });

  ajaxCommon.getItemNoLoading({
    id: 'usimChgChkAjax'
    ,cache: false
    ,url: '/mypage/usimChgChkAjax.do'
    ,data: varData
    ,dataType: "json"
    ,errorCall: function () {
      pageObj.isCallbackIng = false;
      KTM.LoadingSpinner.hide(true);
      KTM.Dialog.closeAll();
      MCP.alert("시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
    }
  }, function(jsonObj){

    if("0000" ==  jsonObj.RESULT_CODE) {
      pageObj.isCallbackIng = false;
      KTM.LoadingSpinner.hide(true);
      KTM.Dialog.closeAll();

      MCP.alert('<p class="u-mt--12"><strong class="self-opening__highlight">유심변경이 완료되었습니다.</strong></p><p class="u-mt--12">고객님 유심변경이 완료되어 음성, 문자 등 휴대폰 사용이 중단됩니다.</p><p class="u-mt--12">신속히 새로운 유심을 단말기에 장착하여 1~3회 전원을 껐다 켜면 사용가능합니다.</p>', function(){
        location.href= "/main.do";
      });
    }else if("9999" == jsonObj.RESULT_CODE){
      if(pageObj.checkCbCnt < 21){
        ++pageObj.checkCbCnt;
        setTimeout(function(){
          fnSelfUsimChgCallBack(mvnoOrdNo);
        }, 5000);
      }else{
        pageObj.isCallbackIng = false;
        KTM.LoadingSpinner.hide(true);
        KTM.Dialog.closeAll();

        MCP.alert("아직 유심 변경 결과가 확인되지 않습니다.<br>전산처리에 1~2분 정도 걸릴 수 있으니, 화면을 닫지 말고 잠시만 기다려 주세요.", function(){
          KTM.LoadingSpinner.show();
          pageObj.checkCbCnt = 0;
          fnSelfUsimChgCallBack(mvnoOrdNo);
        });
      }
    }else{
      pageObj.isCallbackIng = false;
      KTM.LoadingSpinner.hide(true);
      KTM.Dialog.closeAll();
      MCP.alert("유심 변경에 실패하였으며 자세한 사항은 고객센터(114/무료)로 문의 부탁드립니다.");
    }
  });
}





