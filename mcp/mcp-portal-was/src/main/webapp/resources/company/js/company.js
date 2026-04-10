VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.reportName = "제보자 성명을 입력하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.reportMobileMn = "제보자 휴대폰 번호를 입력하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.reportMobileRn = "제보자 휴대폰 번호를 입력하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.reportTitle = "제목을 입력하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.reportContent = "내용을 입력하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.reportMail01 = "이메일 정보를 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.reportMail02 = "도메인 정보를 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.searchName = "성명을 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.searchCertNum = "인증문자를 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.certCheck = "개인정보 수집/이용에 동의하여 주시기 바랍니다.";

VALIDATE_FIX_MSG={};
VALIDATE_FIX_MSG.reportMobileMn = "제보자 휴대폰 번호 중간자리를 3자리 이상 숫자로  입력하세요.";
VALIDATE_FIX_MSG.reportMobileRn = "제보자 휴대폰 번호 뒷자리를 4자리 이상 숫자로 입력하세요.";

VALIDATE_NUMBER_MSG ={};
VALIDATE_NUMBER_MSG.reportMobileMn="제보자 휴대폰 번호 중간자리를 숫자로 입력하세요.";
VALIDATE_NUMBER_MSG.reportMobileRn="제보자 휴대폰 번호 뒷자리를 숫자로 입력하세요.";

VALIDATE_COMPARE_MSG = {};
VALIDATE_COMPARE_MSG.reportMail01 = "이메일 형식이 일치 하지 않습니다.";

$(document).ready(function(){

	fn_setAjaxShowLoading();

	//제보하기 안내
    $("#reportBtn").click(function() {
    	fn_layerPop('reportFormInfo', 1040, 1800);

    	$('#reportFrm')[0].reset();
    	$('#certCheck').prop("checked", false);

		var refFocusEl = null;
        $('.dim-layer').find('.popup_title_h4').attr('tabindex','0');
        $('.popup_title_h4').show().focus();
            refFocusEl = this;

        $('.popup_cancel').click(function(){
            if(refFocusEl) refFocusEl.focus();
            refFocusEl = null;
            return false;
        });
    });

  	//제보입력화면
    $("#reportFormBtn").click(function() {

        fn_layerPop('reportForm', 1040, 1500);

		var refFocusEl = null;
        $('.dim-layer').find('.popup_title_h4').attr('tabindex','0');
        $('.popup_title_h4').show().focus();
            refFocusEl = this;

        const modal = document.getElementById("reportForm");
        const closeButton = document.getElementById("closeModal2");
        let lastFocusedElement = null; // 모달 닫힐 때 복구할 포커스
        modal.style.display = "block";
        modal.setAttribute("aria-hidden", "false");
        modal.focus();
        closeButton.focus(); // 닫기 버튼에 포커스 이동

        // 모달 내 모든 포커스 가능한 요소 가져오기
        const focusableElements = () => {
          return modal.querySelectorAll("button, a, input, textarea, select");
        };
        // 키보드 트랩 설정 (Tab 키를 모달 내부에서만 이동)
        function trapTabKey(event) {
          const focusable = Array.from(focusableElements());
          const firstElement = focusable[0];
          const lastElement = focusable[focusable.length - 1];

          if (event.key === "Tab") {
            if (event.shiftKey && document.activeElement === firstElement) {
              event.preventDefault();
              lastElement.focus();
            } else if (!event.shiftKey && document.activeElement === lastElement) {
              event.preventDefault();
              firstElement.focus();
            }
          }
        }
        // 키보드 이벤트 리스너 (Tab 키만 제어)
        document.addEventListener("keydown", (event) => {
          // 각각의 모달에 대해 Tab 키 트랩
          trapTabKey(event, modal);
        });

        $('.popup_cancel').click(function(){
        	const modal = document.getElementById("reportForm");
        	const closeButton = document.getElementById("closeModal2");
        	let lastFocusedElement = null; // 모달 닫힐 때 복구할 포커스
        	modal.style.display = "none";
        	modal.setAttribute("aria-hidden", "true");
        	if (lastFocusedElement) lastFocusedElement.focus(); // 원래 포커스 복귀

            if(refFocusEl) refFocusEl.focus();
            refFocusEl = null;
            return false;
        });

    });

  	//제보결과 확인
    $("#reportSearchBtn").click(function() {

    	fn_layerPop('reportResultsSch', 1040, 800);

    	$("#resultDiv_01").show();
    	$("#resultDiv_02").hide();
    	$("#searchCertNum").val("");
    	$("#searchName").val("");

    	var refFocusEl = null;
        $('.dim-layer').find('.popup_title_h4').attr('tabindex','0');
        $('.popup_title_h4').show().focus();
            refFocusEl = this;

        const modal = document.getElementById("reportResultsSch");
        const closeButton = document.getElementById("closeModal4");
        let lastFocusedElement = null; // 모달 닫힐 때 복구할 포커스
        modal.style.display = "block";
        modal.setAttribute("aria-hidden", "false");
        modal.focus();
        closeButton.focus(); // 닫기 버튼에 포커스 이동

        // 모달 내 모든 포커스 가능한 요소 가져오기
        const focusableElements = () => {
          return modal.querySelectorAll("button, a, input, textarea, select");
        };
        // 키보드 트랩 설정 (Tab 키를 모달 내부에서만 이동)
        function trapTabKey(event) {
          const focusable = Array.from(focusableElements());
          const firstElement = focusable[0];
          const lastElement = focusable[focusable.length - 1];

          if (event.key === "Tab") {
            if (event.shiftKey && document.activeElement === firstElement) {
              event.preventDefault();
              lastElement.focus();
            } else if (!event.shiftKey && document.activeElement === lastElement) {
              event.preventDefault();
              firstElement.focus();
            }
          }
        }
        // 키보드 이벤트 리스너 (Tab 키만 제어)
        document.addEventListener("keydown", (event) => {
          // 각각의 모달에 대해 Tab 키 트랩
          trapTabKey(event, modal);
        });

        $('.popup_cancel').click(function(){
        	const modal = document.getElementById("reportResultsSch");
        	const closeButton = document.getElementById("closeModal4");
        	let lastFocusedElement = null; // 모달 닫힐 때 복구할 포커스
        	modal.style.display = "none";
        	modal.setAttribute("aria-hidden", "true");
        	if (lastFocusedElement) lastFocusedElement.focus(); // 원래 포커스 복귀

        	if(refFocusEl) refFocusEl.focus();
            refFocusEl = null;
            return false;
        });

    });

  	//확인 닫기
  	$(".layerConfirmBtn").click(function() {
  		$(".popup_cancel").trigger("click");
  	});

	$('.real_menu').mouseover(function(){
		$('.sub_menu').slideDown();

	});
	$('.sub_menu').mouseleave(function(){

		$('.sub_menu').slideUp();
	});
	$('.gnb_mobile .menu').click(function(){


		$('.sub_menu_mobile').slideToggle();
	});

	$('#tab1').click(function(){
		$('.tab2').hide();
		$('.tab1').show();
		$('#tab1').addClass("selected");
		$('#tab2').removeClass("selected");

	});
	$('#tab2').click(function(){
		$('.tab1').hide();
		$('.tab2').show();
		$('#tab2').addClass("selected");
		$('#tab1').removeClass("selected");

	});


    //모바일 제보입력화면
    $("#m_reportFormBtn").click(function() {
	    fn_mobile_layerOpen('m_reportForm',m_reportFormScroll);
    });

    //제보하기 등록
    var runFlag = false;
    $("#submitBtn").click(function() {

    	if (runFlag == true) {
    		alert("등록중 입니다. 잠시만 기다려주세요.");
    		return;
    	}

    	runFlag = true;

    	validator.config={};
        validator.config['reportName'] = 'isNonEmpty';
        validator.config['reportMobileMn'] = 'isNumBetterFixN3';
        validator.config['reportMobileRn'] = 'isNumFix4';
        validator.config['reportMail01&reportMail02'] = 'isNonMailMulti';
        validator.config['reportTitle'] = 'isNonEmpty';
        validator.config['reportContent'] = 'isNonEmpty';
        validator.config['certCheck'] = 'isNonEmpty';

        //제보내용 자릿수 체크
        if (!maxByteCheck($("#reportContent"), 2000)) {
        	return;
        }

        if (validator.validate()) {

        	var data = new FormData($('#reportFrm')[0]);
	      	data.append('reportEmail', $("#reportMail01").val() + "@" + $("#reportMail02").val());
	      	data.append('reportMobileNo', $("#reportMobileFn").val()+$("#reportMobileMn").val()+$("#reportMobileRn").val());

	        ajaxCommon.getItemFileUp({
                id:'reportSumit'
                ,cache:false
                ,url:'/company/reportSubmitAjax.do'
                ,data: data
             }
             ,function(jsonObj) {
            	runFlag = false;
            	if (jsonObj.RESULT_CODE ==  "00000") {
                	$("#viewCertNumber").text(jsonObj.certNumber);
                	$("#reportForm").hide();
                	fn_layerPop('reportCmpl', 1040, 800);
            	} else {
            		 alert(jsonObj.RESULT_MSG);
            	}
             });

        } else {
        	runFlag = false;
        	alert(validator.getErrorMsg());
        }

    });

    //이메일 도메인 선택
    $("#selEmail").change(function() {
    	$("#reportMail02").val($(this).val());
    	if ($(this).val() == "") {
    		$("#reportMail02").prop("readonly", false);
    	} else {
    		$("#reportMail02").prop("readonly", true);
    	}
    });

    //윤리 위반 신고 접수결과 확인
    $("#reportResultsSchBtn").click(function() {

    	validator.config={};
        validator.config['searchCertNum'] = 'isNonEmpty';
        validator.config['searchName'] = 'isNonEmpty';

        if (validator.validate()) {
	    	var varData = ajaxCommon.getSerializedData({
	    		reportCertNumber : $.trim($("#searchCertNum").val())
           		,reportName : $.trim($("#searchName").val())
           	});

	    	ajaxCommon.getItem({
                id:'reportSearchAjax'
                ,url:'/company/reportSearchAjax.do'
                ,data: varData
             }
             ,function(jsonObj) {

            	 if (jsonObj.RESULT_CODE == "00000") {

            		 if (jsonObj.reportResultDto != "") {
                		 $("#viewReportName").text(jsonObj.reportResultDto.reportName);
                		 $("#viewReportDt").text(jsonObj.reportResultDto.reportRegDt.substring(0, 19));
                		 $("#viewReportTitle").text(jsonObj.reportResultDto.reportTitle);
                		 $("#viewReportStatus").text(jsonObj.reportResultDto.reportStatusText);
                		 $("#viewReportRcvCpltDt").text(jsonObj.reportResultDto.reportRcvCpltDt.substring(0, 19));
                		 $("#viewReportAnswerDt").text(jsonObj.reportResultDto.reportAnswerDt.substring(0, 19));
                		 $("#viewReportAnswer").text(jsonObj.reportResultDto.reportAnswer);

                		 $("#resultDiv_01").hide();
 				    	 $("#resultDiv_02").show();
                	 } else {
                		 alert("일치하는 접수내역이 없습니다.");
                	 }

            	 } else {
            		 alert(jsonObj.RESULT_MSG);
            	 }

             });
        } else {
        	alert(validator.getErrorMsg());
        }

    });

});


function Handlechange(target,target2) {
	var fileinput = $('#'+target);
	var textinput = $('#'+target2);
	textinput.val(fileinput.val());
}


//input byte 체크(UTF-8)
var maxByteCheck = function($obj, maxlength) {

	if(maxlength > 0){
	var lsStr=$obj.val();               //이벤트가 일어난 컨트롤의 value값
      var lsStrLen=lsStr.length;          //전체길이
      var maxLen= $obj.attr('maxlength');                    //제한할 글자수 크기
      var liByte=0;                       //한글일 경우는 2 그밖에는 1을 더함
      var liLen=0;                        //substring하기위해사용
      var lsOneChar="";                   //한글자씩 검사한다.
      var lsStr2="";                      //글자수를 초과하면 제한할 수 글자전까지만 보여준다.

      for(var i=0;i<lsStrLen;i++)
      {
          //한글자추출
          lsOneChar=lsStr.charAt(i);
          //한글이면 2를 더한다.
          if(escape(lsOneChar).length>4)
          {
              liByte=liByte+3;
          }
          else if((lsOneChar =='\r' && lsStr.charAt(i+1) =='\n') || lsOneChar=='\n')
          {
              liByte=liByte+2;
          }
          else
          {
              liByte++;
          }

          //전체 크기가 maxLen을 넘지않으면
          if(liByte<=maxLen)
          {
          	lsStr2+=lsOneChar;
              liLen=i+1;
          }

      }

      //전체길이를 초과하면
      if(liByte>maxLen)
      {
      	$obj.val(lsStr2);
      	alert('제보내용 입력 글자 수를 초과하였습니다.\n한글 '+parseInt(maxLen/2, 10)+'자 영문 '+maxLen+'자 까지만 입력해주세요');
      	$obj.focus();
          return false;
      }

      return true;
	}

  return true;
};